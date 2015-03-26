package com.hcwins.vehicle.evs.ta.apidao;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by tommy on 3/25/15.
 */
public class EVSCaptcha {
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

    public enum Status {
        NEW,
        VERIFIED,
        EXPIRED
    }

    public enum Module {
        USER_REGIST, //个人用户注册
        ENTEPRISE_REGISTER, //企业管理员注册
        ENTEPRISE_PASS, //企业管理员忘记密码
    }

    public long id;
    public Date createTime;
    public Date updateTime;
    public String captcha;
    public Date lastAccessTime;
    public long maxInactiveInternal;
    public String mobile;
    public Module module;
    public Status status;

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
                    Status.valueOf(r.getString("status")));
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
}
