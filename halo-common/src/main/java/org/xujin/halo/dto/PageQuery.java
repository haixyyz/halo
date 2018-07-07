package org.xujin.halo.dto;

import java.util.ArrayList;
import java.util.List;

/**
 * page query
 * <p/>
 * Created by xujin on 2018/6/1.
 */
public abstract class PageQuery extends Query {

    private int pageNum = 1;
    private int pageSize = 10;
    private boolean needTotalCount = true;
    private List<OrderDesc> orderDescs;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public boolean isNeedTotalCount() {
        return needTotalCount;
    }

    public void setNeedTotalCount(boolean needTotalCount) {
        this.needTotalCount = needTotalCount;
    }

    public List<OrderDesc> getOrderDescs() {
        return orderDescs;
    }

    public void addOrderDesc(OrderDesc orderDesc) {
        if (null == orderDescs) {
            orderDescs = new ArrayList<>();
        }
        orderDescs.add(orderDesc);
    }

    public int getOffset() {
        return pageNum > 0 ? (pageNum - 1) * pageSize : 0;
    }
}
