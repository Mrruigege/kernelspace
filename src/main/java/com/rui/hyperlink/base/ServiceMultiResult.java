package com.rui.hyperlink.base;

import java.util.List;

/**
 * 服务类通用接口
 * 返回多个对象
 *
 * @author xiaorui
 */
public class ServiceMultiResult<T> {

    private long total;

    private List<T> result;

    public ServiceMultiResult(long total, List<T> result) {
        this.total = total;
        this.result = result;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public int getResultSize() {
        if (this.result == null) {
            return 0;
        }
        return this.result.size();
    }
}
