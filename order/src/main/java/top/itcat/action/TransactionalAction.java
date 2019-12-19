package top.itcat.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.itcat.entity.ChargeItem;
import top.itcat.entity.DayKnot;
import top.itcat.entity.DayKnotItem;
import top.itcat.exception.CommonException;
import top.itcat.service.ChargeItemService;
import top.itcat.service.DayKnotItemService;
import top.itcat.service.DayKnotService;

import java.util.List;

@Service
@Slf4j
public class TransactionalAction {
    @Autowired
    private ChargeItemService chargeItemService;
    @Autowired
    private DayKnotService dayKnotService;
    @Autowired
    private DayKnotItemService dayKnotItemService;

    @Transactional
    public void cancelCharge(List<ChargeItem> chargeItems,
                             List<ChargeItem> copiedItems) {
        chargeItemService.insertBatch(copiedItems);
        chargeItemService.updateBatchById(chargeItems);
    }

    @Transactional
    public boolean settleCharge(List<DayKnotItem> dayKnotItems,
                                DayKnot dayKnot,
                                List<ChargeItem> chargeItems) throws Exception {
        if (!dayKnotService.insert(dayKnot)) {
            return false;
        }

        dayKnotItems.parallelStream()
                .forEach(dayKnotItem -> dayKnotItem.setDayKnotId(dayKnot.getId()));

        if (!dayKnotItemService.insertBatch(dayKnotItems)) {
            throw new CommonException("回滚", 0);
        }

        if (!chargeItemService.updateBatchById(chargeItems)) {
            throw new CommonException("回滚", 0);
        }

        return true;
    }
}
