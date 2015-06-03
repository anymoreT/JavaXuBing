package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by wenji on 13/04/15.
 */
@Annotations.Entity(table = "EVS_EnterpriseAdmin", mapper = EVSEnterpriseAdmin.Mapper.class)
public class EVSEnterpriseAdmin extends BaseEntity {
    /*Table: EVS_EnterpriseAdmin
Columns:
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
    protected Long enterpriseId;

    public static enum Status {
        BINDED,
        UNBINDED
    }

    public EVSEnterpriseAdmin(Long id, Date createTime, Date updateTime, String email, Boolean isLocked, Integer loginFailureCount, String mobile, String realName, Status status, Date unLockTime, Long enterpriseId) {
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

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
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

    public String getRealName() {
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

    public void setUnLockTime(Date unLockTime) {
        this.unLockTime = unLockTime;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

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
                "email='" + email + '\'' +
                ", isLocked=" + isLocked +
                ", loginFailureCount=" + loginFailureCount +
                ", mobile='" + mobile + '\'' +
                ", realName='" + realName + '\'' +
                ", status=" + status +
                ", unLockTime=" + unLockTime +
                ", enterpriseId=" + enterpriseId +
                "} " + super.toString();
    }

    public static EVSEnterpriseAdminDao dao = EVSUtil.getDAO(EVSEnterpriseAdminDao.class);
}







