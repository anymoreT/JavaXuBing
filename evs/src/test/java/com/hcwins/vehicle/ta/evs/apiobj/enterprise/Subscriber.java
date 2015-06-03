package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by wenji on 28/04/15.
 */
public class Subscriber {

    private Long subscirberId;
    private String realName;
    private String mobile;
    private String email;
    private String cityName;
    private String provinceName;
    private int status;
    private byte[] identityPic;
    private int vehicleAptitude;
//    private byte[] drivingLicensePic;


    public Subscriber(long subscirberId, String realName, String mobile, String email, String cityName, String provinceName, int status, byte[] identityPic, int vehicleAptitude) {
        this.subscirberId = subscirberId;
        this.realName = realName;
        this.mobile = mobile;
        this.email = email;
        this.cityName = cityName;
        this.provinceName = provinceName;
        this.status = status;
        this.identityPic = identityPic;
        this.vehicleAptitude = vehicleAptitude;
    }
    public Long getSubscirberId() {
        return subscirberId;
    }

    public void setSubscirberId(Long subscirberId) {
        this.subscirberId = subscirberId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public byte[] getIdentityPic() {
        return identityPic;
    }

    public void setIdentityPic(byte[] identityPic) {
        this.identityPic = identityPic;
    }

    public int getVehicleAptitude() {
        return vehicleAptitude;
    }

    public void setVehicleAptitude(int vehicleAptitude) {
        this.vehicleAptitude = vehicleAptitude;
    }

//    public byte[] getDrivingLicensePic() {
//        return drivingLicensePic;
//    }
//
//    public void setDrivingLicensePic(byte[] drivingLicensePic) {
//        this.drivingLicensePic = drivingLicensePic;
//    }

    public static class Mapper implements ResultSetMapper<Subscriber> {
        public Subscriber map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new Subscriber(
                    r.getLong("subscirberId"),
                    r.getString("realName"),
                    r.getString("mobile"),
                    r.getString("email"),
                    r.getString("cityName"),
                    r.getString("provinceName"),
                    r.getInt("status"),
                    r.getBytes("identityPic"),
                    r.getInt("vehicleAptitude")
            );
        }
    }

    @Override
    public String toString() {
        return "Subscriber{" +
                "subscirberId=" + subscirberId +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", cityName='" + cityName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", status=" + status +
                ", identityPic=" + Arrays.toString(identityPic) +
                ", vehicleAptitude=" + vehicleAptitude +
//                ", drivingLicensePic=" + Arrays.toString(drivingLicensePic) +
                '}';
    }
}
