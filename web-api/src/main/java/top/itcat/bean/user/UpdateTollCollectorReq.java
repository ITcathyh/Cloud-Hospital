package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.TollCollector;

public class UpdateTollCollectorReq {
    private TollCollector tollCollector;

    public TollCollector getTollCollector() {
        return tollCollector;
    }

    @JsonSetter("toll_collector")
    public void setTollCollector(TollCollector tollCollector) {
        this.tollCollector = tollCollector;
    }
}
