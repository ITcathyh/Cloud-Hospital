package top.itcat.controller.diagnose.apply;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.itcat.concurrent.CommonThreadPoolFactory;
import top.itcat.controller.action.ControllerHelper;
import top.itcat.entity.charge.ChargeItem;
import top.itcat.entity.charge.NonmedicalCharge;
import top.itcat.entity.diagnose.ApplyItem;
import top.itcat.exception.InternalException;
import top.itcat.rpc.client.HospitalServiceClient;
import top.itcat.rpc.client.OrderServiceClient;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeRequest;
import top.itcat.rpc.service.hospital.GetNonmedicalChargeResponse;
import top.itcat.rpc.service.order.GetChargeItemRequest;
import top.itcat.rpc.service.order.GetChargeItemResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ApplyHelper {
    private ExecutorService threadPool = CommonThreadPoolFactory.getDefaultThreadPool();
    @Autowired
    private OrderServiceClient orderServiceClient;
    @Autowired
    private ControllerHelper controllerHelper;
    @Autowired
    private HospitalServiceClient hospitalServiceClient;

    void packApplyItem(final List<ApplyItem> applyItems) {
        List<Long> chargeItemIds = new ArrayList<>(applyItems.size());
        List<Long> nonmedicalChargeIds = new ArrayList<>(applyItems.size());

        for (ApplyItem item : applyItems) {
            chargeItemIds.add(item.getChargeItemId());
            nonmedicalChargeIds.add(item.getNonmedicalChargeId());
        }

        CountDownLatch countDownLatch = new CountDownLatch(2);

        threadPool.submit(() -> {
            try {
                GetChargeItemRequest getChargeItemRequest = new GetChargeItemRequest();
                getChargeItemRequest.setIds(chargeItemIds);
                GetChargeItemResponse getChargeItemResponse = orderServiceClient.getChargeItem(getChargeItemRequest);


                if (getChargeItemResponse != null) {
                    Map<Long, ChargeItem> chargeItemMap = controllerHelper.getChargeItem(getChargeItemResponse.getBeanList())
                            .parallelStream()
                            .collect(Collectors.toMap(ChargeItem::getId, i -> i));
                    applyItems.forEach(i -> i.setChargeItem(chargeItemMap.get(i.getChargeItemId())));

                }
            } catch (Exception e) {
                log.error("getApplyItem err:", e);
            } finally {
                countDownLatch.countDown();
            }
        });

        threadPool.submit(() -> {
            try {
                GetNonmedicalChargeRequest getNonmedicalChargeRequest = new GetNonmedicalChargeRequest();
                getNonmedicalChargeRequest.setIds(nonmedicalChargeIds);
                GetNonmedicalChargeResponse getNonmedicalChargeResponse = hospitalServiceClient.getNonmedicalCharge(getNonmedicalChargeRequest);


                if (getNonmedicalChargeResponse != null) {
                    Map<Long, NonmedicalCharge> nonmedicalChargeMap = getNonmedicalChargeResponse.getBeanList()
                            .parallelStream()
                            .map(NonmedicalCharge::convert)
                            .collect(Collectors.toMap(NonmedicalCharge::getId, i -> i));
                    applyItems.forEach(i -> i.setNonmedicalCharge(nonmedicalChargeMap.get(i.getNonmedicalChargeId())));
                }
            } catch (Exception e) {
                log.error("getApplyItem err:", e);
            } finally {
                countDownLatch.countDown();
            }

        });

        try {
            countDownLatch.await(4, TimeUnit.SECONDS);
        } catch (Exception e) {
            log.error("getApplyItem err:", e);
            throw new InternalException();
        }
    }
}
