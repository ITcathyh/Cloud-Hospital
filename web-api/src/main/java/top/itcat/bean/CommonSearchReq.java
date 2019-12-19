package top.itcat.bean;

import org.springframework.util.StringUtils;

import java.util.List;

public class CommonSearchReq {
    private List<Long> ids = null;
    private String searchKey = null;
    private Integer offset = null;
    private Integer limit = null;

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }

    public String getSearchKey() {
        if (StringUtils.isEmpty(searchKey) || searchKey.trim().length() == 0) {
            return null;
        }

        return searchKey;
    }

    public void setSearchKey(String searchKey) {
        this.searchKey = searchKey;
    }

    public Integer getOffset() {
        return offset;
    }

    public void setOffset(Integer offset) {
        this.offset = offset;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public boolean isSetOffset() {
        return offset != null;
    }

    public boolean isSetLimit() {
        return limit != null;
    }
}
