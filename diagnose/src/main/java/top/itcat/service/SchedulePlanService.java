package top.itcat.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import top.itcat.entity.registrantion.SchedulePlan;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-25
 */
public interface SchedulePlanService extends IService<SchedulePlan> {
    public Page<SchedulePlan> selectUsingRule(Page<SchedulePlan> page, Long doctorId, Long departId,
                                              boolean needExpired, Long curTime,
                                              Long startTime, Long endTime);

    public boolean decrRemain(Long id);

    public boolean addRemain(Long id);
}
