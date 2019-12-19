package top.itcat.action.task;

import lombok.extern.slf4j.Slf4j;
import top.itcat.action.task.bean.CollectDepartStatisticalDataResult;
import top.itcat.action.task.bean.CollectDoctorStatisticalDataResult;
import top.itcat.rpc.client.DiagnoseServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.diagnose.GetRegistrationCountResponse;
import top.itcat.rpc.service.model.DepartStatisticalData;
import top.itcat.rpc.service.model.DoctorStatisticalData;
import top.itcat.rpc.service.order.GetIndividualAmountRequest;
import top.itcat.rpc.service.order.GetIndividualAmountResponse;

import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class CollectDepartStatisticalDataTask implements Callable<CollectDepartStatisticalDataResult> {
    private DiagnoseServiceClient diagnoseServiceClient;
    private OrderServiceClient orderServiceClient;
    private List<Long> chargeSubjectIds;
    private long departId;
    private long startTime;
    private long endTime;

    public CollectDepartStatisticalDataTask(DiagnoseServiceClient diagnoseServiceClient,
                                            OrderServiceClient orderServiceClient,
                                            List<Long> chargeSubjectIds,
                                            long departId,
                                            long startTime,
                                            long endTime) {
        this.diagnoseServiceClient = diagnoseServiceClient;
        this.orderServiceClient = orderServiceClient;
        this.chargeSubjectIds = chargeSubjectIds;
        this.departId = departId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    @Override
    public CollectDepartStatisticalDataResult call() throws Exception {
        CollectDepartStatisticalDataResult result = new CollectDepartStatisticalDataResult();
        result.setDone(false);
        result.setDepartId(departId);

        try {
            GetRegistrationCountResponse getRegistrationCountResponse =
                    diagnoseServiceClient.getRegistrationCount(null, departId,
                            startTime, endTime);

            if (getRegistrationCountResponse == null) {
                return result;
            }

            long regiCount = getRegistrationCountResponse.getCount();

            GetIndividualAmountRequest getIndividualAmountRequest = new GetIndividualAmountRequest();
            getIndividualAmountRequest.setStartTime(startTime);
            getIndividualAmountRequest.setEndTime(endTime);
            getIndividualAmountRequest.setDepartId(departId);
            getIndividualAmountRequest.setChargeSubjectIds(chargeSubjectIds);

            GetIndividualAmountResponse getIndividualAmountResponse = orderServiceClient.getIndividualAmount(getIndividualAmountRequest);

            if (getIndividualAmountResponse == null) {
                return result;
            }

            DepartStatisticalData departStatisticalData = new DepartStatisticalData();
            departStatisticalData.setDepartId(departId);
            departStatisticalData.setRegistrationCount((int) regiCount);
            departStatisticalData.setChargeDetail(getIndividualAmountResponse.getAmountMap());
            result.setDepartStatisticalData(departStatisticalData);
            result.setDone(true);
        } catch (Exception e) {
            log.error("getDoctorStatistical err:", e);
        }

        return result;
    }
}