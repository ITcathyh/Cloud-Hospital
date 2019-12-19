package top.itcat.task;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import org.joda.time.DateTime;
import top.itcat.action.TransactionalAction;
import top.itcat.entity.registrantion.ScheduleRule;
import top.itcat.service.ScheduleRuleService;
import top.itcat.task.bean.DepartSchedualTaskResult;

import java.util.List;
import java.util.concurrent.Callable;

public class CurWeekDepartScheduleTask implements Callable<DepartSchedualTaskResult> {
    private ScheduleRuleService scheduleRuleService;
    private TransactionalAction transactionalAction;
    private long departId;

    public CurWeekDepartScheduleTask(ScheduleRuleService scheduleRuleService,
                              TransactionalAction transactionalAction, long departId) {
        this.scheduleRuleService = scheduleRuleService;
        this.transactionalAction = transactionalAction;
        this.departId = departId;
    }

    @Override
    public DepartSchedualTaskResult call() throws Exception {
        long cur = System.currentTimeMillis();
        DateTime time = new DateTime().plusWeeks(1).withMillisOfDay(0);
        long starTime = time.getMillis();
        DepartSchedualTaskResult result = new DepartSchedualTaskResult();
        List<ScheduleRule> rules = scheduleRuleService.selectList(new EntityWrapper<ScheduleRule>()
                .eq("department_id", departId)
                .le("start_time", starTime)
                .ge("end_time", cur)
                .eq("valid", 1));
        result.setDone(true);

        for (ScheduleRule rule : rules) {
            try {
                if (!transactionalAction.generateSchedulePlan(rule)) {
                    result.setDone(false);
                }
            } catch (Exception e) {
                result.setDone(false);
            }
        }

        return result;
    }
}
