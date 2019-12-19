package top.itcat.action.task;

import lombok.extern.slf4j.Slf4j;
import top.itcat.action.task.bean.CollectDoctorStatisticalDataResult;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.diagnose.GetApplyItemRequest;
import top.itcat.rpc.service.diagnose.GetApplyItemResponse;
import top.itcat.rpc.service.diagnose.GetRegistrationCountResponse;
import top.itcat.rpc.service.model.ApplyItem;
import top.itcat.rpc.service.model.ApplyItemStatus;
import top.itcat.rpc.service.model.DoctorStatisticalData;
import top.itcat.rpc.service.order.GetIndividualAmountRequest;
import top.itcat.rpc.service.order.GetIndividualAmountResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;

@Slf4j
public class CollectDoctorStatisticalDataTask implements Callable<CollectDoctorStatisticalDataResult> {
    private DiagnoseServiceClient diagnoseServiceClient;
    private OrderServiceClient orderServiceClient;
    private List<Long> chargeSubjectIds;
    private long doctorId;
    private long startTime;
    private long endTime;
    private CountDownLatch countDownLatch;
    private Boolean isMedical;

    public CollectDoctorStatisticalDataTask(DiagnoseServiceClient diagnoseServiceClient, OrderServiceClient orderServiceClient, List<Long> chargeSubjectIds, long doctorId, long startTime, long endTime, CountDownLatch countDownLatch, boolean isMedical) {
        this.diagnoseServiceClient = diagnoseServiceClient;
        this.orderServiceClient = orderServiceClient;
        this.chargeSubjectIds = chargeSubjectIds;
        this.doctorId = doctorId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.countDownLatch = countDownLatch;
        this.isMedical = isMedical;
    }

    @Override
    public CollectDoctorStatisticalDataResult call() throws Exception {
        CollectDoctorStatisticalDataResult result = new CollectDoctorStatisticalDataResult();
        result.setDone(false);
        result.setDoctorId(doctorId);

        try {
            DoctorStatisticalData doctorStatisticalData = new DoctorStatisticalData();

            if (!isMedical) {
                GetRegistrationCountResponse getRegistrationCountResponse =
                        diagnoseServiceClient.getRegistrationCount(doctorId, null,
                                startTime, endTime);

                if (getRegistrationCountResponse == null) {
                    return result;
                }

                doctorStatisticalData.setRegistrationCount(getRegistrationCountResponse.getCount());
            }

            GetIndividualAmountRequest getIndividualAmountRequest = new GetIndividualAmountRequest();
            getIndividualAmountRequest.setStartTime(startTime);
            getIndividualAmountRequest.setEndTime(endTime);
            getIndividualAmountRequest.setDoctorId(doctorId);
            getIndividualAmountRequest.setChargeSubjectIds(chargeSubjectIds);
            GetIndividualAmountResponse getIndividualAmountResponse = orderServiceClient.getIndividualAmount(getIndividualAmountRequest);

            if (getIndividualAmountResponse == null) {
                return result;
            } else if (getIndividualAmountResponse.getAmountMapSize() == 0) {
                getIndividualAmountResponse.setAmountMap(new HashMap<>(0));
            }

            Map<Long,Double> amountMap = new HashMap<>(getIndividualAmountResponse.getAmountMap());

            if (isMedical) {
                GetApplyItemRequest getApplyItemRequest = new GetApplyItemRequest();
                getApplyItemRequest.setMedicalDoctorId(doctorId);
                getApplyItemRequest.setStartTime(startTime);
                getApplyItemRequest.setEndTime(endTime);
                GetApplyItemResponse getApplyItemResponse = diagnoseServiceClient.getApplyItem(getApplyItemRequest);

                if (getApplyItemResponse == null) {
                    return result;
                } else if (getApplyItemResponse.getBeanListSize() > 0) {
                    getIndividualAmountRequest = new GetIndividualAmountRequest();
                    getIndividualAmountRequest.setStartTime(startTime);
                    getIndividualAmountRequest.setEndTime(endTime);
                    getIndividualAmountRequest.setChargeItemIds(getApplyItemResponse.getBeanList()
                            .parallelStream()
                            .map(ApplyItem::getChargeItemId)
                            .collect(Collectors.toList()));

                    getIndividualAmountResponse = orderServiceClient.getIndividualAmount(getIndividualAmountRequest);

                    if (getIndividualAmountResponse == null) {
                        return result;
                    } else if (getIndividualAmountResponse.getAmountMapSize() == 0) {
                        getIndividualAmountResponse.setAmountMap(new HashMap<>(0));
                    }

                    amountMap.putAll(getIndividualAmountResponse.getAmountMap());
                }

                doctorStatisticalData.setRegistrationCount(getApplyItemResponse.getTotal());
            }

            doctorStatisticalData.setChargeDetail(amountMap);
            doctorStatisticalData.setDoctorId(doctorId);
            result.setDoctorStatisticalData(doctorStatisticalData);
            result.setDone(true);
        } catch (Exception e) {
            log.error("getDoctorStatistical err:", e);
        } finally {
            countDownLatch.countDown();
        }

        return result;
    }
}
