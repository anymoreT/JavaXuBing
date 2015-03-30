package com.hcwins.vehicle.evs.ta.apidao;

import java.util.Date;

/**
 * Created by tommy on 3/27/15.
 */
public class BaseEntity {
    protected long id;
    protected Date createTime;
    protected Date updateTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
