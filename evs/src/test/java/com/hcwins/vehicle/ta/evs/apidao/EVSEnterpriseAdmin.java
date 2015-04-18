package com.hcwins.vehicle.ta.evs.apidao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Created by wenji on 13/04/15.
 */
@Annotations.Entity(table = "EVS_EnterpriseAdmin", mapper = EVSEnterpriseAdmin.Mapper.class)
public class EVSEnterpriseAdmin extends BaseEntity{

/*
        id bigint not null auto_increment,
        createTime datetime,
        updateTime datetime,
        email varchar(50),
        isLocked bit not null,
        loginFailureCount integer not null,
        mobile varchar(11) not null,
        realName varchar(50) not null,
        status varchar(255) not null,
        unLockTime datetime,
        enterpriseId bigint not null,

 */
    protected String email;
    protected Boolean isLocked;
    protected Integer loginFailureCount;
    protected String mobile;
    protected String realName;
    protected Status status;
    protected Date unLockTime;
    protected long enterpriseId;

    public static enum Status {
        BINDED,
        UNBINDED
    }


    public EVSEnterpriseAdmin(long id, Date createTime, Date updateTime, String email, Boolean isLocked, Integer loginFailureCount, String mobile,String realName, Status status, Date unLockTime, long enterpriseId) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.email = email;
        this.isLocked = isLocked;
        this.loginFailureCount = loginFailureCount;
        this.mobile = mobile;
        this.realName = realName;
        this.status = status;
        this.unLockTime = unLockTime;
        this.enterpriseId = enterpriseId;
    }



    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsLoced() {
        return isLocked;
    }

    public void setIsLocked(boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Integer getLoginFailureCount() {
        return loginFailureCount;
    }

    public void setLoginFailureCount(Integer loginFailureCount) {
        this.loginFailureCount = loginFailureCount;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getRealname() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getUnLockTime() {
        return unLockTime;
    }

    public void setUnLockTime(Date unLockTime) {this.unLockTime = unLockTime;}

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(long enterpriseId) {this.enterpriseId = enterpriseId;}

    public static class Mapper implements ResultSetMapper<EVSEnterpriseAdmin> {
        public EVSEnterpriseAdmin map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSEnterpriseAdmin(
                    r.getLong("id"),
                    r.getTimestamp("createTime"),
                    r.getTimestamp("updateTime"),
                    r.getString("email"),
                    r.getBoolean("isLocked"),
                    r.getInt("loginFailureCount"),
                    r.getString("mobile"),
                    r.getString("realName"),
                    Status.valueOf(r.getString("status")),
                    r.getDate("unLockTime"),
                    r.getLong("enterpriseId")
            );
        }
    }


    @Override
    public String toString() {
        return "EVSEnterpriseAdmin{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", email='" + email + '\'' +
                ", isLocked=" + isLocked +
                ", loginFailureCount=" + loginFailureCount +
                ", mobile='" + mobile + '\'' +
                ", realName=" + realName +
                ", unLockTime=" + unLockTime +
                ", enterpriseId=" + enterpriseId +
                ", status=" + status +
                '}';
    }

    public static EVSEnterpriseAdminDao dao = EVSUtil.getDAO(EVSEnterpriseAdminDao.class);
}







