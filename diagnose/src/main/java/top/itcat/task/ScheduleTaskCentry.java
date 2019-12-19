package top.itcat.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import top.itcat.action.TransactionalAction;
import top.itcat.cache.util.RedisUtil;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.constant.RedisKey;
import top.itcat.entity.registrantion.SchedulePlan;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.service.hospital.GetDepartmentRequest;
import top.itcat.rpc.service.hospital.GetDepartmentResponse;
import top.itcat.rpc.service.model.Department;
import top.itcat.service.SchedulePlanService;
import top.itcat.service.ScheduleRuleService;
import top.itcat.task.bean.DepartSchedualTaskResult;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 定时任务控制中心
 *
 * @author ITcathyh
 */
@Component("scheduleTaskCentry")
@Slf4j
public class ScheduleTaskCentry {
    @Autowired
    private ScheduleRuleService scheduleRuleService;
    @Autowired
    private SchedulePlanService schedulePlanService;
    @Autowired
    private TransactionalAction transactionalAction;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private ExecutorService threadPool = CommonThreadPoolFactory.newThreadPool();

    /**
     * 每周日1点
     * 根据排班规则定时生成排班计划
     */
    @Scheduled(cron = "0 0 1 ? * L")
    public void generateDoctorSchedual() {
        GetDepartmentResponse rsp = null;

        while (rsp == null) {
            rsp = hospitalServiceClient.getDepartment(new GetDepartmentRequest());

            if (rsp == null) {
                log.error("getDepartment err");

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        List<Long> departIds = rsp.beanList.parallelStream().
                map(Department::getId).collect(Collectors.toList());
        rsp = null;
        generatePlan(departIds);
    }

    /**
     * 每周日1点
     * 根据排班规则定时生成排班计划
     */
    @Scheduled(cron = "0 0 1 * * *")
    public void generateRedisRemain() {
        long time = new DateTime().withMillisOfDay(0).getMillis();
        long expire = (new DateTime().withHourOfDay(23).getMillis() - System.currentTimeMillis()) / 1000;
        List<SchedulePlan> schedulePlans = schedulePlanService.selectList(new EntityWrapper<SchedulePlan>()
                .eq("start_time", time)
                .eq("valid", 1));

        for (SchedulePlan plan : schedulePlans) {
            RedisUtil.setNx(redisTemplate,String.format(RedisKey.SUBMIT_SCHEDULE_SITE_KEY, plan.getId()), plan.getRemain(), expire);
//            redisTemplate.opsForValue().set(String.format(RedisKey.SUBMIT_SCHEDULE_SITE_KEY, plan.getId()), plan.getRemain(),
//                    expire, TimeUnit.SECONDS);
        }
    }

    /**
     * 生成部门下的排班计划
     * 使用线程池高效执行
     *
     * @param departIds 部门ID
     */
    @Async
    public void generatePlan(List<Long> departIds) {
        List<Future<DepartSchedualTaskResult>> futureList = new ArrayList<>(departIds.size());

        for (long id : departIds) {
            futureList.add(threadPool.submit
                    (new DepartScheduleTask(scheduleRuleService,
                            transactionalAction, id)));
        }

        int completeCount;
        int size;

        do {
            completeCount = 0;
            size = futureList.size();

            for (ListIterator<Future<DepartSchedualTaskResult>> it = futureList.listIterator(); it.hasNext(); ) {
                Future<DepartSchedualTaskResult> result = it.next();

                if (result.isDone()) {
                    try {
                        if (result.get().isDone()) {
                            ++completeCount;
                        } else {
                            log.error("generate schedule err, departId:{}",
                                    result.get().getDepartId());
                            it.add(threadPool.submit
                                    (new DepartScheduleTask(scheduleRuleService,
                                            transactionalAction,
                                            result.get().getDepartId())));
                        }

                        it.remove();
                    } catch (Exception e) {
                        log.error("generateDoctorSchedual err:", e);
                    }
                }
            }

            if (completeCount < size) {
                Thread.yield();
            }
        } while (completeCount < size);
    }
}

