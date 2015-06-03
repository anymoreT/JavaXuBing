package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by wenji on 26/05/15.
 */
@Annotations.Entity(table = "EVS_SubscriberCredential", mapper = EVSSubscriberCredential.Mapper.class)
public class EVSSubscriberCredential extends BaseEntity {
     /*Table: EVS_EnterpriseAdminCredential
Columns:
id bigint not null auto_increment,
createTime datetime,
updateTime datetime,
credentialName varchar(255) not null,
password varchar(255) not null,
enterpriseAdminId bigint not null,
      */

    protected String credentialName;
    protected String password;
    protected Long subscriberId;

    public EVSSubscriberCredential(Long id, Date createTime, Date updateTime, String credentialName, String password, Long subscriberId) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.credentialName = credentialName;
        this.password = password;
        this.subscriberId = subscriberId;
    }

    public String getCredentialName() {
        return credentialName;
    }

    public void setCredentialName(String credentialName) {
        this.credentialName = credentialName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public static class Mapper implements ResultSetMapper<EVSSubscriberCredential> {
        public EVSSubscriberCredential map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSSubscriberCredential(
                    r.getLong("id"),
                    r.getTimestamp("createTime"),
                    r.getTimestamp("updateTime"),
                    r.getString("credentialName"),
                    r.getString("password"),
                    r.getLong("subscriberId")
            );
        }
    }

    @Override
    public String toString() {
        return "EVSSubscriberCredential{" +
                "credentialName='" + credentialName + '\'' +
                ", password='" + password + '\'' +
                ", subscriberId=" + subscriberId +
                '}';
    }
    public static EVSSubscriberCredentialDao dao = EVSUtil.getDAO(EVSSubscriberCredentialDao.class);
}
