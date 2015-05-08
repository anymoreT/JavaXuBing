package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import java.util.Arrays;

/**
 * Created by wenji on 28/04/15.
 */
public class Subscriber {

    /*
    "subscirberId": ,
      "realName": ,
      "mobile":,
      "email": ,
      "cityName":,
      "provinceName": ",
      "status": ,
       "identityPic": ,
        "vehicleAptitude":
     */
    private Long subscirberId;
    private String realName;
    private String mobile;
    private String email;
    private String cityName;
    private String provinceName;
    private int status;
    private byte[] identityPic;
    private int vehicleAptitude;


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

    @Override
    public String toString() {
        return "subscriberList{" +
                "subscirberId=" + subscirberId +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", cityName='" + cityName + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", status=" + status +
                ", identityPic=" + Arrays.toString(identityPic) +
                ", vehicleAptitude=" + vehicleAptitude +
                '}';
    }
}
