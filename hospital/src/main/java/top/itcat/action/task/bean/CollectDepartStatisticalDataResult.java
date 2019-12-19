package top.itcat.action.task.bean;

import lombok.Getter;
import lombok.Setter;
import top.itcat.rpc.service.model.DepartStatisticalData;
import top.itcat.rpc.service.model.DoctorStatisticalData;

@Getter
@Setter
public class CollectDepartStatisticalDataResult {
    private DepartStatisticalData departStatisticalData;
    private boolean done;
    private long departId;
}
