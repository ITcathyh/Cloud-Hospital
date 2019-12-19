package top.itcat.controller.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.cache.annotation.LoadingCache;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.entity.Patient;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.diagnose.SchedulePlan;
import top.itcat.entity.diagnose.ScheduleRule;
import top.itcat.entity.medical.*;
import top.itcat.entity.user.OutpatientDoctor;
import top.itcat.rpc.client.*;
import top.itcat.rpc.service.diagnose.GetScheduleRuleRequest;
import top.itcat.rpc.service.diagnose.GetScheduleRuleResponse;
import top.itcat.rpc.service.hospital.GetDepartmentRequest;
import top.itcat.rpc.service.hospital.GetDepartmentResponse;
import top.itcat.rpc.service.medical.GetMedicineRequest;
import top.itcat.rpc.service.medical.GetMedicineResponse;
import top.itcat.rpc.service.model.CatalogEnum;
import top.itcat.rpc.service.model.ChargeItemStatusEnum;
import top.itcat.rpc.service.model.Department;
import top.itcat.rpc.service.order.GetChargeItemRequest;
import top.itcat.rpc.service.order.GetChargeItemResponse;
import top.itcat.rpc.service.order.GetChargeSubjectRequest;
import top.itcat.rpc.service.user.MGetDoctorRequest;
import top.itcat.rpc.service.user.MGetDoctorResponse;
import top.itcat.rpc.service.user.MGetPatientRequest;
import top.itcat.rpc.service.user.MGetPatientResponse;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Controller通用打包服务
 */
@Service
@Slf4j
public class ControllerHelper {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private MedicalServiceClient medicalServiceClient;
    private final ExecutorService threadPool = CommonThreadPoolFactory.getDefaultThreadPool();

    /**
     * 获取并打包收费项目
     *
     * @param rpcList 通过RPC服务获得的账单信息
     * @return 打包好患者信息和科室信息的账单
     */
    public List<ChargeItem> getChargeItem(List<top.itcat.rpc.service.model.ChargeItem> rpcList) {
        return getChargeItem(rpcList, false);
    }

    /**
     * 获取并打包收费项目
     *
     * @param rpcList      通过RPC服务获得的账单信息
     * @param withReversed 是否需要红冲账单
     * @return 打包好患者信息和科室信息的账单
     */
    public List<ChargeItem> getChargeItem(List<top.itcat.rpc.service.model.ChargeItem> rpcList, boolean withReversed) {
        Stream<top.itcat.rpc.service.model.ChargeItem> stream = rpcList.parallelStream();

        if (!withReversed) {
            // 过滤掉红冲账单
            stream = stream.filter(i -> i.getStatus() != ChargeItemStatusEnum.Reverse);
        }

        // 将RPC Bean转换为对外数据结构
        // 起到过滤敏感数据、枚举转数值等作用
        List<ChargeItem> chargeItems = stream.map(ChargeItem::convert)
                .collect(Collectors.toList());
        CountDownLatch countDownLatch = new CountDownLatch(2);

        // 添加线程任务，获取患者信息
        threadPool.submit(() -> {
            try {
                MGetPatientRequest getPatientRequest = new MGetPatientRequest();
                // 将病历号进行过滤，避免重复发送请求
                List<String> searchKeys = chargeItems.parallelStream()
                        .map(ChargeItem::getMedicalRecordNo)
                        .map(String::valueOf)
                        .distinct()
                        .collect(Collectors.toList());
                Map<Long, Patient> patientMap = new HashMap<>(searchKeys.size());

                // 将病人信息打包
                for (String str : searchKeys) {
                    getPatientRequest.setSearchKey(str);
                    MGetPatientResponse getPatientResponse = userServiceClient.mGetPatient(getPatientRequest);

                    if (getPatientResponse != null && getPatientResponse.getUsersSize() > 0) {
                        patientMap.put(Long.valueOf(str), Patient.convertPatient(getPatientResponse.getUsers().get(0)));
                    }

                    for (ChargeItem chargeItem : chargeItems) {
                        chargeItem.setPatient(patientMap.get(chargeItem.getMedicalRecordNo()));
                    }
                }
            } catch (Exception e) {
                log.error("getChargeItem err:", e);
            } finally {
                // 保证不会出现无限等待
                countDownLatch.countDown();
            }
        });

        // 添加线程任务，获取科室信息
        threadPool.submit(() -> {
            try {
                // 将科室ID合并为集合，仅需发送一次请求，减少网络消耗时间
                List<Long> departIds = chargeItems
                        .parallelStream()
                        .map(ChargeItem::getDepartmentId)
                        .distinct()
                        .collect(Collectors.toList());
                GetDepartmentRequest getDepartmentRequest = new GetDepartmentRequest();
                getDepartmentRequest.setIds(departIds);
                GetDepartmentResponse getDepartmentResponse = hospitalServiceClient.getDepartment(getDepartmentRequest);

                // 打包科室信息
                if (getDepartmentResponse != null && getDepartmentResponse.getBeanListSize() != 0) {
                    Map<Long, String> departmentMap = getDepartmentResponse.getBeanList()
                            .parallelStream()
                            .collect(Collectors.toMap(Department::getId, Department::getName));

                    for (ChargeItem chargeItem : chargeItems) {
                        chargeItem.setDepartName(departmentMap.get(chargeItem.getDepartmentId()));
                    }
                }
            } catch (Exception e) {
                log.error("getChargeItem err:", e);
            } finally {
                countDownLatch.countDown();
            }
        });

        try {
            // 主线程阻塞，避免忙等浪费资源
            // 同时避免长时间无响应，设置最大4秒阻塞时间，然后返回异常
            countDownLatch.await();
        } catch (InterruptedException e) {
            log.error("getChargeItem err:", e);
            return null;
        }

        return chargeItems;
    }


    @LoadingCache(prefix = "we_char_sub_id",
            expireTime = 60,
            localExpireTime = 30,
            cacheOperation = LoadingCache.CacheOperation.QUERY)
    public Long getWestrenChargeSubjectId() {
        GetChargeSubjectRequest req = new GetChargeSubjectRequest();
        req.setCatelog(CatalogEnum.Medical);
        return orderServiceClient.getChargeSubject(req).getBeanList().get(0).getId();
    }

    @LoadingCache(prefix = "chin_char_sub_id",
            expireTime = 60,
            localExpireTime = 30,
            cacheOperation = LoadingCache.CacheOperation.QUERY)
    public Long getChinsesChargeSubjectId() {
        GetChargeSubjectRequest req = new GetChargeSubjectRequest();
        req.setCatelog(CatalogEnum.ChineseMedicine);
        return orderServiceClient.getChargeSubject(req).getBeanList().get(0).getId();
    }

    public List<SchedulePlan> packSchedulePlan(List<top.itcat.rpc.service.model.SchedulePlan> rpcList) {
        List<SchedulePlan> schedulePlans = rpcList.parallelStream()
                .map(SchedulePlan::convert)
                .collect(Collectors.toList());
        Set<Long> ruleIds = new HashSet<>(schedulePlans.size());

        for (SchedulePlan schedulePlan : schedulePlans) {
            ruleIds.add(schedulePlan.getScheduleId());
        }

        GetScheduleRuleRequest getScheduleRuleRequest = new GetScheduleRuleRequest();
        getScheduleRuleRequest.setIds(new ArrayList<>(ruleIds));
        GetScheduleRuleResponse getScheduleRuleResponse = diagnoseServiceClient.getScheduleRule(getScheduleRuleRequest);

        if (getScheduleRuleResponse == null || getScheduleRuleResponse.getBeanListSize() == 0) {
            return schedulePlans;
        }

        Map<Long, ScheduleRule> scheduleRuleMap = getScheduleRuleResponse.getBeanList()
                .parallelStream()
                .map(ScheduleRule::convert)
                .collect(Collectors.toMap(ScheduleRule::getId, i -> i));
        Set<Long> doctorIds = new HashSet<>(schedulePlans.size());
        Set<Long> departmentIds = new HashSet<>(schedulePlans.size());

        for (SchedulePlan plan : schedulePlans) {
            ScheduleRule rule = scheduleRuleMap.get(plan.getScheduleId());

            if (rule != null) {
                plan.setScheduleRule(rule);
                departmentIds.add(rule.getDepartmentId());
                doctorIds.add(rule.getDoctorId());
            }
        }

        CountDownLatch countDownLatch = new CountDownLatch(2);

        threadPool.submit(() -> {
            MGetDoctorRequest getDoctorRequest = new MGetDoctorRequest();
            getDoctorRequest.setUids(new ArrayList<>(doctorIds));
            MGetDoctorResponse getDoctorResponse = userServiceClient.mGetDoctor(getDoctorRequest);

            if (getDoctorResponse != null && getDoctorResponse.getUsersSize() != 0) {
                Map<Long, OutpatientDoctor> doctorMap = new HashMap<>(getDoctorResponse.getUsersSize());

                for (top.itcat.rpc.service.model.OutpatientDoctor doctor : getDoctorResponse.getUsers()) {
                    doctorMap.put(doctor.getUser().getId(), OutpatientDoctor.convertOutpatientDoctor(doctor));
                }

                for (ScheduleRule rule : scheduleRuleMap.values()) {
                    OutpatientDoctor doctor = doctorMap.get(rule.getDoctorId());

                    rule.setDoctorName(doctor.getRealname());
                    rule.setDoctor(doctor);
                }
            }

            countDownLatch.countDown();
        });

        threadPool.submit(() -> {
            GetDepartmentRequest getDepartmentRequest = new GetDepartmentRequest();
            getDepartmentRequest.setIds(new ArrayList<>(departmentIds));
            GetDepartmentResponse getDepartmentResponse = hospitalServiceClient.getDepartment(getDepartmentRequest);

            if (getDepartmentResponse != null && getDepartmentResponse.getBeanListSize() != 0) {
                Map<Long, String> departMap = getDepartmentResponse.getBeanList().parallelStream()
                        .collect(Collectors.toMap(Department::getId, Department::getName));

                for (ScheduleRule rule : scheduleRuleMap.values()) {
                    rule.setDepartName(departMap.get(rule.getDepartmentId()));
                }
            }

            countDownLatch.countDown();
        });

        try {
            countDownLatch.await(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("packSchedulePlan err", e);
        }

        return schedulePlans;
    }

    public void packPrescription(List<Prescription> prescriptions,
                                 Set<Long> medicineIds,
                                 Set<Long> chargeItemIds) {
        int num = 0;
        num += medicineIds.isEmpty() ? 0 : 1;
        num += chargeItemIds.isEmpty() ? 0 : 1;

        if (num == 0) {
            return;
        }

        CountDownLatch countDownLatch = new CountDownLatch(num);

        if (!medicineIds.isEmpty()) {
            threadPool.submit(() -> {
                try {
                    if (medicineIds.size() > 0) {
                        GetMedicineRequest getMedicineRequest = new GetMedicineRequest();
                        getMedicineRequest.setIds(new ArrayList<>(medicineIds));
                        GetMedicineResponse getMedicineResponse = medicalServiceClient.getMedicine(getMedicineRequest);

                        if (getMedicineResponse == null) {
                            return;
                        }

                        Map<Long, Medicine> medicineMap = getMedicineResponse.getBeanList()
                                .parallelStream()
                                .map(Medicine::convert)
                                .collect(Collectors.toMap(Medicine::getId,
                                        item -> item));

                        for (Prescription prescription : prescriptions) {
                            if (prescription.getItems() != null) {
                                for (PrescriptionItem item : prescription.getItems()) {
                                    item.setMedicine(medicineMap.get(item.getItemId()));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("packPrescription err:", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        if (!chargeItemIds.isEmpty()) {
            threadPool.submit(() -> {
                try {
                    if (chargeItemIds.size() > 0) {
                        GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
//                        getChargeItemRequest.setChargeSubjectId(getWestrenChargeSubjectId());
                        getChargeItemRequest.setIds(new ArrayList<>(chargeItemIds));
                        GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);

                        if (getChargeItemResponse == null) {
                            return;
                        }

                        Map<Long, ChargeItem> chargeItemMap = getChargeItem(getChargeItemResponse.getBeanList())
                                .parallelStream()
                                .collect(Collectors.toMap(ChargeItem::getId, i -> i, (i1, i2) -> i1));

                        for (Prescription prescription : prescriptions) {
                            if (prescription.getItems() != null) {
                                for (PrescriptionItem item : prescription.getItems()) {
                                    ChargeItem chargeItem = chargeItemMap.get(item.getChargeItemId());

                                    if (chargeItem == null) {
                                        log.warn("empty chargeItem, PrescriptionItem:{}", item);
                                        continue;
                                    }

                                    item.setNum(chargeItem.getAmount());
                                    item.setChargeItem(chargeItem);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("packPrescription err:", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("packRegistrations err:", e);
        }
    }

    public void packPrescriptionHerb(List<PrescriptionHerb> prescriptions,
                                     Set<Long> medicineIds,
                                     Set<Long> chargeItemIds) {
        int num = 0;
        num += medicineIds.isEmpty() ? 0 : 1;
        num += chargeItemIds.isEmpty() ? 0 : 1;

        if (num == 0) {
            return;
        }

        CountDownLatch countDownLatch = new CountDownLatch(num);

        if (!medicineIds.isEmpty()) {
            threadPool.submit(() -> {
                try {
                    if (medicineIds.size() > 0) {
                        GetMedicineRequest getMedicineRequest = new GetMedicineRequest();
                        getMedicineRequest.setIds(new ArrayList<>(medicineIds));
                        GetMedicineResponse getMedicineResponse = medicalServiceClient.getMedicine(getMedicineRequest);

                        if (getMedicineResponse == null) {
                            return;
                        }

                        Map<Long, Medicine> medicineMap = getMedicineResponse.getBeanList()
                                .parallelStream()
                                .map(Medicine::convert)
                                .collect(Collectors.toMap(Medicine::getId,
                                        item -> item));

                        for (PrescriptionHerb prescription : prescriptions) {
                            if (prescription.getItems() != null) {
                                for (PrescriptionHerbItem item : prescription.getItems()) {
                                    item.setMedicine(medicineMap.get(item.getItemId()));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("packPrescriptionHerb err:", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        if (!chargeItemIds.isEmpty()) {
            threadPool.submit(() -> {
                try {
                    if (chargeItemIds.size() > 0) {
                        GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
//                        getChargeItemRequest.setChargeSubjectId(getWestrenChargeSubjectId());
                        getChargeItemRequest.setIds(new ArrayList<>(chargeItemIds));
                        GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);

                        if (getChargeItemResponse == null) {
                            return;
                        }

                        Map<Long, ChargeItem> chargeItemMap = getChargeItem(getChargeItemResponse.getBeanList())
                                .parallelStream()
                                .collect(Collectors.toMap(ChargeItem::getId, i -> i, (i1, i2) -> i1));

                        for (PrescriptionHerb prescription : prescriptions) {
                            if (prescription.getItems() != null) {
                                for (PrescriptionHerbItem item : prescription.getItems()) {
                                    item.setChargeItem(chargeItemMap.get(item.getChargeItemId()));
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    log.error("packPrescriptionHerb err:", e);
                } finally {
                    countDownLatch.countDown();
                }
            });
        }

        try {
            countDownLatch.await(4, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("packRegistrations err:", e);
        }
    }

    public ExecutorService getThreadPool() {
        return threadPool;
    }
}
