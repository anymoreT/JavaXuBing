package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;

/**
 * Created by wenji on 28/04/15.
 */
@Annotations.Entity(table = "EVS_EnterpriseAdmin", mapper = EVSEnterpriseAdmin.Mapper.class)
public class EVSSubscriber extends BaseEntity {
    /*
     id bigint not null auto_increment,
        createTime datetime,
        updateTime datetime,
        drivingLicenseNumber varchar(255),
        drivingLicensePic longblob,
        drivingYears integer,
        email varchar(255),
        identityNo varchar(255),
        identityPic longblob,
        isLocked bit not null,
        isSupervisor bit not null,
        loginFailureCount integer not null,
        mobile varchar(11) not null,
        name varchar(255),
        realName varchar(255),
        status varchar(255) not null,
        unLockTime datetime,
        vehicleAptitude varchar(255),
        cityId bigint,
        enterpriseId bigint,
        provinceId bigint,
     */

    protected String drivingLicenseNumber;
    protected byte[] drivingLicensePic;
    protected Integer drivingYears;
    protected String email;
    protected String identityNo;
    protected byte[] identityPic;
    protected Boolean isLocked;
    protected Boolean isSupervisor;
    protected String mobile;
    protected String name;
    protected String realName;
    protected SubscriberStatus status;
    protected Date unLockTime;
    protected VehicleAptitude vehicleAptitude;
    protected Long cityId;
    protected Long enterpriseId;
    protected Long provinceId;

    public enum SubscriberStatus {
        UNAUDITED, REFUSED, AVAILABLE, UNAVAILABLE;
    }

    public enum VehicleAptitude{
        NOAPPLY,APPLYING,HASAPTITUDE,NOAPTITUDE;
    }

    public EVSSubscriber(String drivingLicenseNumber, byte[] drivingLicensePic, Integer drivingYears, String email, String identityNo, byte[] identityPic, Boolean isLocked, Boolean isSupervisor, String name, String mobile, String realName, SubscriberStatus status, Date unLockTime, VehicleAptitude vehicleAptitude, Long enterpriseId, Long cityId, Long provinceId) {
        this.drivingLicenseNumber = drivingLicenseNumber;
        this.drivingLicensePic = drivingLicensePic;
        this.drivingYears = drivingYears;
        this.email = email;
        this.identityNo = identityNo;
        this.identityPic = identityPic;
        this.isLocked = isLocked;
        this.isSupervisor = isSupervisor;
        this.name = name;
        this.mobile = mobile;
        this.realName = realName;
        this.status = status;
        this.unLockTime = unLockTime;
        this.vehicleAptitude = vehicleAptitude;
        this.enterpriseId = enterpriseId;
        this.cityId = cityId;
        this.provinceId = provinceId;
    }

    public EVSSubscriber(long id, Timestamp createTime, Timestamp updateTime, String drivingLicenseNumber, String drivingLicensePic, int drivingYears, String email, String identityNo, String identityPic, boolean isLocked, boolean isSupervisor, String mobile, String name, String realName, int status, java.sql.Date unLockTime, int vehicleAptitude, long cityId, long enterpriseId, long provinceId) {

    }

    public String getDrivingLicenseNumber() {
        return drivingLicenseNumber;
    }

    public void setDrivingLicenseNumber(String drivingLicenseNumber) {
        this.drivingLicenseNumber = drivingLicenseNumber;
    }

    public byte[] getDrivingLicensePic() {
        return drivingLicensePic;
    }

    public void setDrivingLicensePic(byte[] drivingLicensePic) {
        this.drivingLicensePic = drivingLicensePic;
    }

    public Integer getDrivingYears() {
        return drivingYears;
    }

    public void setDrivingYears(Integer drivingYears) {
        this.drivingYears = drivingYears;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getIdentityNo() {
        return identityNo;
    }

    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
    }

    public byte[] getIdentityPic() {
        return identityPic;
    }

    public void setIdentityPic(byte[] identityPic) {
        this.identityPic = identityPic;
    }

    public Boolean getIsLocked() {
        return isLocked;
    }

    public void setIsLocked(Boolean isLocked) {
        this.isLocked = isLocked;
    }

    public Boolean getIsSupervisor() {
        return isSupervisor;
    }

    public void setIsSupervisor(Boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public SubscriberStatus getStatus() {
        return status;
    }

    public void setStatus(SubscriberStatus status) {
        this.status = status;
    }

    public Date getUnLockTime() {
        return unLockTime;
    }

    public void setUnLockTime(Date unLockTime) {
        this.unLockTime = unLockTime;
    }

    public VehicleAptitude getVehicleAptitude() {
        return vehicleAptitude;
    }

    public void setVehicleAptitude(VehicleAptitude vehicleAptitude) {
        this.vehicleAptitude = vehicleAptitude;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public static class Mapper implements ResultSetMapper<EVSSubscriber> {
        public EVSSubscriber map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSSubscriber(
                    r.getLong("id"),
                    r.getTimestamp("createTime"),
                    r.getTimestamp("updateTime"),
                    r.getString("drivingLicenseNumber"),
                    r.getString("drivingLicensePic"),
                    r.getInt("drivingYears"),
                    r.getString("email"),
                    r.getString("identityNo"),
                    r.getString("identityPic"),
                    r.getBoolean("isLocked"),
                    r.getBoolean("isSupervisor"),
                    r.getString("mobile"),
                    r.getString("name"),
                    r.getString("realName"),
                    r.getInt("status"),
                    r.getDate("unLockTime"),
                    r.getInt("vehicleAptitude"),
                    r.getLong("cityId"),
                    r.getLong("enterpriseId"),
                    r.getLong("provinceId")
            );
        }
    }
    @Override
    public String toString() {
        return "EVSSubscriber{" +
                "drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", drivingLicensePic=" + Arrays.toString(drivingLicensePic) +
                ", drivingYears=" + drivingYears +
                ", email='" + email + '\'' +
                ", identityNo='" + identityNo + '\'' +
                ", identityPic=" + Arrays.toString(identityPic) +
                ", isLocked=" + isLocked +
                ", isSupervisor=" + isSupervisor +
                ", mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", realName='" + realName + '\'' +
                ", status=" + status +
                ", unLockTime=" + unLockTime +
                ", vehicleAptitude=" + vehicleAptitude +
                ", cityId=" + cityId +
                ", enterpriseId=" + enterpriseId +
                ", provinceId=" + provinceId +
                '}';
    }
    public static EVSSubscriberDao dao = EVSUtil.getDAO(EVSSubscriberDao.class);
}
