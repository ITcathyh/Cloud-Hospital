package top.itcat.bean.user;

import com.fasterxml.jackson.annotation.JsonSetter;
import top.itcat.entity.user.TollCollector;

import java.util.List;

public class AddTollCollectorsReq {
    private List<TollCollector> tollCollectors;

    public List<TollCollector> getTollCollectors() {
        return tollCollectors;
    }

    @JsonSetter("toll_collectors")
    public void setTollCollectors(List<TollCollector> tollCollectors) {
        this.tollCollectors = tollCollectors;
    }
}
