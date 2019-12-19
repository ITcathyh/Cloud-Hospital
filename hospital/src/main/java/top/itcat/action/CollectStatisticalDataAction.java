package top.itcat.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.action.task.CollectDepartStatisticalDataTask;
import top.itcat.action.task.CollectDoctorStatisticalDataTask;
import top.itcat.action.task.bean.CollectDepartStatisticalDataResult;
import top.itcat.action.task.bean.CollectDoctorStatisticalDataResult;
import top.itcat.cache.annotation.LoadingCache;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.model.CatalogEnum;
import top.itcat.rpc.service.model.ChargeSubject;
import top.itcat.rpc.service.model.DepartStatisticalData;
import top.itcat.rpc.service.model.DoctorStatisticalData;
import top.itcat.rpc.service.order.GetChargeSubjectRequest;
import top.itcat.rpc.service.order.GetChargeSubjectResponse;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

/**
 * 统计工作量
 * 使用ThreadPool、FutureTask、CountDownLatch和LoadingCache完成工作量的高效统计
 *
 * @author ITcathyh
 */
@Service
@Slf4j
public class CollectStatisticalDataAction {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    private ExecutorService threadPool = CommonThreadPoolFactory.newThreadPool(5);

    /**
     * 统计医生工作量
     * 使用ThreadPool、FutureTask、CountDownLatch和LoadingCache完成工作量的高效统计
     *
     * @param doctorIds 医生ID集合
     * @param startTime 开始时间
     * @param endTime   终止时间
     * @param isMedical 是否为医技医生
     * @return 统计数据
     */
    public List<DoctorStatisticalData> collectDoctorStatisticalData(List<Long> doctorIds,
                                                                    long startTime,
                                                                    long endTime,
                                                                    boolean isMedical) {
        List<Long> chargeSubjectIds;

        try {
            // 获取费用科目ID
            // 获取成功后加入缓存，利于下次快速取用
            chargeSubjectIds = getChargeSubjectIds();
        } catch (Exception e) {
            return null;
        }

        if (chargeSubjectIds == null) {
            return null;
        }

        List<DoctorStatisticalData> doctorStatisticalDatas = new ArrayList<>(doctorIds.size());
        Map<Long, Boolean> doctorIdsMap = new HashMap<>(doctorIds.size());

        for (Long id : doctorIds) {
            doctorIdsMap.put(id, true);
        }

        List<Future<CollectDoctorStatisticalDataResult>> futureList = new ArrayList<>(doctorIds.size());

        // 循环执行，当有任务执行失败时继续执行
        // 避免高强度任务持续执行错误，浪费资源
        while (!doctorIdsMap.isEmpty()) {
            // 使用CountDownLatch进行线程同步，将资源让与其它线程，避免主线程空转浪费资源
            CountDownLatch countDownLatch = new CountDownLatch(doctorIdsMap.size());

            // 通过线程池执行子任务，提高执行效率
            for (long doctorId : doctorIdsMap.keySet()) {
                futureList.add(threadPool.submit
                        (new CollectDoctorStatisticalDataTask(diagnoseServiceClient,
                                orderServiceClient,
                                chargeSubjectIds,
                                doctorId,
                                startTime,
                                endTime,
                                countDownLatch,
                                isMedical)));
            }

            try {
                countDownLatch.await();
            } catch (Exception e) {
                log.error("collectDoctorStatisticalData err:", e);
                return null;
            }

            // 对结果进行校验
            // 若任务执行完毕且返回正确，则子任务数据统计完成，存入list
            // 否则重新启动该任务
            for (Future<CollectDoctorStatisticalDataResult> future : futureList) {
                try {
                    CollectDoctorStatisticalDataResult result = future.get();

                    if (future.isDone() && result.isDone()) {
                        doctorIdsMap.remove(result.getDoctorId());
                        doctorStatisticalDatas.add(result.getDoctorStatisticalData());
                    }
                } catch (Exception e) {
                    log.error("get CollectDoctorStatisticalDataResult err:", e);
                }
            }
        }

        return doctorStatisticalDatas;
    }

    public List<DepartStatisticalData> CollectDepartStatisticalData(List<Long> departIds,
                                                                    long startTime,
                                                                    long endTime) {
        List<Long> chargeSubjectIds;

        try {
            chargeSubjectIds = getChargeSubjectIds();
        } catch (Exception e) {
            return null;
        }

        if (chargeSubjectIds == null) {
            return null;
        }

        List<DepartStatisticalData> departStatisticalDatas = new ArrayList<>(departIds.size());
        List<Future<CollectDepartStatisticalDataResult>> futureList = new ArrayList<>(departIds.size());

        for (long departId : departIds) {
            futureList.add(threadPool.submit
                    (new CollectDepartStatisticalDataTask(diagnoseServiceClient,
                            orderServiceClient,
                            chargeSubjectIds,
                            departId,
                            startTime,
                            endTime)));
        }

        int completeCount;
        int size;

        do {
            completeCount = 0;
            size = futureList.size();

            for (ListIterator<Future<CollectDepartStatisticalDataResult>> it = futureList.listIterator(); it.hasNext(); ) {
                Future<CollectDepartStatisticalDataResult> result = it.next();

                if (result.isDone()) {
                    try {
                        if (result.get().isDone()) {
                            if (result.get().getDepartStatisticalData().getChargeDetailSize() > 0) {
                                departStatisticalDatas.add(result.get().getDepartStatisticalData());
                            }

                            ++completeCount;
                        } else {
                            log.error("CollectDepartStatisticalDataResult err, departId:{}",
                                    result.get().getDepartId());
                            it.add(threadPool.submit
                                    (new CollectDepartStatisticalDataTask(diagnoseServiceClient,
                                            orderServiceClient,
                                            chargeSubjectIds,
                                            result.get().getDepartId(),
                                            startTime,
                                            endTime)));
                        }

                        it.remove();
                    } catch (Exception e) {
                        log.error("CollectDepartStatisticalDataTask err:", e);
                    }
                }
            }

            if (completeCount < size) {
                Thread.yield();
            }
        } while (completeCount < size);

        return departStatisticalDatas;
    }

    /**
     * 获取所有费用科目ID
     * 用于分析数据
     * 同时使用LoadingCache提高效率
     *
     * @return List<Long>
     */
    @LoadingCache(prefix = "all_charge_subject_ids",
            cacheOperation = LoadingCache.CacheOperation.QUERY,
            localExpireTime = 15,
            expireTime = 15)
    public List<Long> getChargeSubjectIds() {
        GetChargeSubjectRequest getChargeSubjectRequest = new GetChargeSubjectRequest();
        GetChargeSubjectResponse getChargeSubjectResponse = orderServiceClient.getChargeSubject(getChargeSubjectRequest);

        if (getChargeSubjectResponse == null
                || !getChargeSubjectResponse.isSetBeanList()
                || getChargeSubjectResponse.getBeanListSize() == 0) {
            throw new RuntimeException();
        }

        return getChargeSubjectResponse.getBeanList()
                .parallelStream()
                .map(ChargeSubject::getId)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有费用科目ID
     * 用于分析数据
     * 同时使用LoadingCache提高效率
     *
     * @return List<Long>
     */
    @LoadingCache(prefix = "all_charge_subject_ids_exclude_regi",
            cacheOperation = LoadingCache.CacheOperation.QUERY,
            localExpireTime = 15,
            expireTime = 15)
    public List<Long> getChargeSubjectIdsExcludeRegi() {
        GetChargeSubjectRequest getChargeSubjectRequest = new GetChargeSubjectRequest();
        GetChargeSubjectResponse getChargeSubjectResponse = orderServiceClient.getChargeSubject(getChargeSubjectRequest);

        if (getChargeSubjectResponse == null
                || !getChargeSubjectResponse.isSetBeanList()
                || getChargeSubjectResponse.getBeanListSize() == 0) {
            throw new RuntimeException();
        }

        return getChargeSubjectResponse.getBeanList()
                .parallelStream()
                .filter(i -> i.getCatalog() != CatalogEnum.Registration)
                .map(ChargeSubject::getId)
                .collect(Collectors.toList());
    }
}
