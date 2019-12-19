package top.itcat.action.task.bean;

import lombok.Getter;
import lombok.Setter;
import top.itcat.rpc.service.model.DoctorStatisticalData;

@Getter
@Setter
public class CollectDoctorStatisticalDataResult {
    private DoctorStatisticalData doctorStatisticalData;
    private boolean done;
    private long doctorId;
}
