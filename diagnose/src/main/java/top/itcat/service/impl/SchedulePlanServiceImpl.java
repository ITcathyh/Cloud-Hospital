package top.itcat.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import top.itcat.aop.multipledb.BaseServiceImpl;
import top.itcat.constant.RedisKey;
import top.itcat.entity.registrantion.SchedulePlan;
import top.itcat.mapper.SchedulePlanMapper;
import top.itcat.service.SchedulePlanService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ITcathyh
 * @since 2019-05-25
 */
@Service
public class SchedulePlanServiceImpl extends BaseServiceImpl<SchedulePlanMapper, SchedulePlan> implements SchedulePlanService {
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

//    public boolean insert(SchedulePlan entity) {
//        boolean success = super.insert(entity);
//
//
//        redisTemplate.opsForValue().set(RedisKey.SUBMIT_SCHEDULE_SITE_KEY, entity.getRemain());
//        return success;
//    }

    public Page<SchedulePlan> selectUsingRule(Page<SchedulePlan> page, Long doctorId, Long departId,
                                              boolean needExpired, Long curTime,
                                              Long startTime, Long endTime) {
//        page.setRecords(this.baseMapper.selectUsingRule(page, doctorId,
//                departId, needExpired, curTime));
        Map<String, Object> map = new HashMap<>();

        if (doctorId != null) {
            map.put("doctorId", doctorId);
        }

        if (departId != null) {
            map.put("departId", departId);
        }

        if (needExpired) {
            map.put("needExpired", needExpired);
        }

        if (curTime != null) {
            map.put("curTime", curTime);
        }

        if (startTime != null) {
            map.put("startTime", startTime);
        }

        if (endTime != null) {
            map.put("endTime", endTime);
        }

        page.setRecords(this.baseMapper.selectUsingRule(page, map));
        return page;
    }

    @Override
    public boolean decrRemain(Long id) {
        Integer re = this.baseMapper.decrRemain(id);
        return re != null && re >= 1;
    }

    @Override
    public boolean addRemain(Long id) {
        Integer re = this.baseMapper.addRemain(id);
        return re != null && re >= 1;
    }

}
