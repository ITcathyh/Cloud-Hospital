package top.itcat.controller.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.cache.annotation.LoadingCache;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.entity.Patient;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.registration.Registration;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.client.UserServiceClient;
import top.itcat.rpc.service.diagnose.GetRegistrationRequest;
import top.itcat.rpc.service.diagnose.GetRegistrationResponse;
import top.itcat.rpc.service.diagnose.GetSchedulePlanRequest;
import top.itcat.rpc.service.diagnose.GetSchedulePlanResponse;
import top.itcat.rpc.service.model.CatalogEnum;
import top.itcat.rpc.service.order.GetChargeItemRequest;
import top.itcat.rpc.service.order.GetChargeItemResponse;
import top.itcat.rpc.service.order.GetChargeSubjectRequest;
import top.itcat.rpc.service.user.MGetPatientRequest;
import top.itcat.rpc.service.user.MGetPatientResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RegistrationHelper {
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private UserServiceClient userServiceClient;
    @Autowired
    private DiagnoseServiceClient diagnoseServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;
    private final ExecutorService threadPool = CommonThreadPoolFactory.getDefaultThreadPool();

    @LoadingCache(prefix = "get_billing_id",
            fieldKeys = {"#medicalRecordNo"})
    public Long getBillingCategoryId(long medicalRecordNo) {
        GetRegistrationRequest getRegistrationRequest = new GetRegistrationRequest();
        getRegistrationRequest.setSearchKey(String.valueOf(medicalRecordNo));
        GetRegistrationResponse getRegistrationResponse = diagnoseServiceClient.getRegistration(getRegistrationRequest);

        if (getRegistrationResponse == null) {
            throw new RuntimeException();
        }

        return getRegistrationResponse.getBeanList().get(0).getBillingCategoryId();
    }

    public List<Registration> packRegistrations(List<top.itcat.rpc.service.model.Registration> rpcList) {
        List<top.itcat.entity.registration.Registration> registrations = rpcList
                .parallelStream()
                .map(top.itcat.entity.registration.Registration::convert)
                .collect(Collectors.toList());
        List<Long> projectIds = new ArrayList<>(rpcList.size());
        List<Long> planIds = new ArrayList<>(rpcList.size());
        List<String> idNums = new ArrayList<>(rpcList.size());
        CountDownLatch countDownLatch = new CountDownLatch(3);

        for (top.itcat.entity.registration.Registration registration : registrations) {
            projectIds.add(registration.getId());
            planIds.add(registration.getSchedulePlanId());
            idNums.add(registration.getIdentityCardNo());
        }

        threadPool.submit(() -> {
            try {
                MGetPatientRequest getPatientRequest = new MGetPatientRequest();
                getPatientRequest.setIdNums(idNums);
                MGetPatientResponse getPatientResponse = userServiceClient.mGetPatient(getPatientRequest);

                if (getPatientResponse == null || getPatientResponse.getUsersSize() == 0) {
                    log.error("GetPatient Empty, regis:{}", registrations.toString());
                } else {
                    Map<String, Patient> patientMap = getPatientResponse.getUsers()
                            .parallelStream()
                            .map(Patient::convertPatient)
                            .collect(Collectors.toMap(Patient::getIdentityCardNo, i -> i));
                    registrations.forEach(i -> i.setPatient(patientMap.get(i.getIdentityCardNo())));
                }
            } catch (Exception e) {
                log.error("getPatient err:", e);
            } finally {
                countDownLatch.countDown();
            }
        });

        threadPool.submit(() -> {
            GetSchedulePlanRequest getSchedulePlanRequest = new GetSchedulePlanRequest();
            getSchedulePlanRequest.setIds(planIds);
            GetSchedulePlanResponse getSchedulePlanResponse = diagnoseServiceClient.getSchedulePlan(getSchedulePlanRequest);

            try {
                if (getSchedulePlanResponse == null || getSchedulePlanResponse.getBeanListSize() == 0) {
                    log.error("GetSchedulePlan Empty, regis:{}", registrations.toString());
                } else {
                    Map<Long, top.itcat.entity.diagnose.SchedulePlan> schedulePlanMap = controllerHelper.packSchedulePlan(getSchedulePlanResponse.getBeanList())
                            .parallelStream()
                            .collect(Collectors.toMap(top.itcat.entity.diagnose.SchedulePlan::getId, i -> i));
                    registrations.forEach(i -> i.setPlan(schedulePlanMap.get(i.getSchedulePlanId())));
                }
            } catch (Exception e) {
                log.error("getSchedulePlan err:", e);

            } finally {
                countDownLatch.countDown();
            }
        });

        threadPool.submit(() -> {
            GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
            getChargeItemRequest.setChargeSubjectId(getRegiChargeSubjectId());
            getChargeItemRequest.setProjectIds(projectIds);
            GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);

            try {
                if (getChargeItemResponse != null && getChargeItemResponse.getBeanListSize() > 0) {
                    Map<Long, ChargeItem> chargeItemMap = getChargeItemResponse.getBeanList()
                            .parallelStream()
                            .map(ChargeItem::convert)
                            .collect(Collectors.toMap(ChargeItem::getProjectId, i -> i, (i1, i2) -> i1));
                    registrations.forEach(registration -> registration.setChargeItem(chargeItemMap.get(registration.getId())));
                } else {
                    log.warn("GetChargeItem Empty, regis:{}, rsp:{}", registrations.toString(), getChargeItemResponse);
                }
            } catch (Exception e) {
                log.error("packRegistrations err:", e);
            } finally {
                countDownLatch.countDown();
            }
        });

        try {
            countDownLatch.await(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("packRegistrations err:", e);
            return null;
        }

        return registrations;
    }

    @LoadingCache(prefix = "regi_char_sub_id",
            expireTime = 15,
            localExpireTime = 15)
    public Long getRegiChargeSubjectId() {
        GetChargeSubjectRequest getChargeSubjectRequest = new GetChargeSubjectRequest();
        getChargeSubjectRequest.setCatelog(CatalogEnum.Registration);
        return orderServiceClient.getChargeSubject(getChargeSubjectRequest).getBeanList().get(0).getId();
    }
}
