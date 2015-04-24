package com.hcwins.vehicle.ta.evs.apidao;

import java.util.Date;

/**
 * Created by tommy on 3/27/15.
 */
public class BaseEntity {
    protected Long id;
    protected Date createTime;
    protected Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
