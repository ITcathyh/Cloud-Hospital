package top.itcat.mq.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import top.itcat.util.DefaultSerializable;

@Builder
@Getter
@ToString
public class AppointmentMsg implements DefaultSerializable {
    private long appointmentId;
    private long userId;
    private long time;
    private String orderId;
}
