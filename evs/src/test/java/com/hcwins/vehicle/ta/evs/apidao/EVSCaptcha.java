package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by tommy on 3/25/15.
 */
@Annotations.Entity(table = "EVS_Captcha", mapper = EVSCaptcha.Mapper.class)
public class EVSCaptcha extends BaseEntity {
    /*
Table: EVS_Captcha
Columns:
id bigint(20) AI PK
createTime datetime
updateTime datetime
captcha varchar(20)
lastAccessTime datetime
maxInactiveInternal bigint(20)
mobile varchar(11)
module varchar(255)
status varchar(255)
    */

    protected String captcha;
    protected Date lastAccessTime;
    protected long maxInactiveInternal;
    protected String mobile;
    protected Module module;
    protected Status status;

    public static enum Status {
        NEW,
        VERIFIED,
        EXPIRED
    }

    public static enum Module {
        USER_REGIST, //个人用户注册
        ENTEPRISE_REGISTER, //企业管理员注册
        ENTEPRISE_PASS, //企业管理员忘记密码
    }

    public static long Conf_MaxInactiveInternal = 30 * 60 * 1000L;

    public EVSCaptcha(long id, Date createTime, Date updateTime, String captcha, Date lastAccessTime, long maxInactiveInternal, String mobile, Module module, Status status) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.captcha = captcha;
        this.lastAccessTime = lastAccessTime;
        this.maxInactiveInternal = maxInactiveInternal;
        this.mobile = mobile;
        this.module = module;
        this.status = status;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public Date getLastAccessTime() {
        return lastAccessTime;
    }

    public void setLastAccessTime(Date lastAccessTime) {
        this.lastAccessTime = lastAccessTime;
    }

    public long getMaxInactiveInternal() {
        return maxInactiveInternal;
    }

    public void setMaxInactiveInternal(long maxInactiveInternal) {
        this.maxInactiveInternal = maxInactiveInternal;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static class Mapper implements ResultSetMapper<EVSCaptcha> {
        public EVSCaptcha map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSCaptcha(
                    r.getLong("id"),
                    r.getTimestamp("createTime"),
                    r.getTimestamp("updateTime"),
                    r.getString("captcha"),
                    r.getTimestamp("lastAccessTime"),
                    r.getLong("maxInactiveInternal"),
                    r.getString("mobile"),
                    Module.valueOf(r.getString("module")),
                    Status.valueOf(r.getString("status"))
            );
        }
    }

    @Override
    public String toString() {
        return "EVSCaptcha{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", captcha='" + captcha + '\'' +
                ", lastAccessTime=" + lastAccessTime +
                ", maxInactiveInternal=" + maxInactiveInternal +
                ", mobile='" + mobile + '\'' +
                ", module=" + module +
                ", status=" + status +
                '}';
    }

    public static EVSCaptchaDao dao = EVSUtil.getDAO(EVSCaptchaDao.class);
}
