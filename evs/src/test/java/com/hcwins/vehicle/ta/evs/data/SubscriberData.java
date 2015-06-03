package com.hcwins.vehicle.ta.evs.data;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSSubscriber;
import com.hcwins.vehicle.ta.evs.apidao.EVSSubscriberCredential;

import java.util.Arrays;

/**
 * Created by wenji on 21/05/15.
 */
public class SubscriberData {
    private String realName;
    private String mobile;
    private String email;
    private String password;
    private String drivingLicenseNumber;
    private byte[] drivingLicensePic;
    private Integer drivingYears;
    private String identityNo;
    private byte[] identityPic;
    private Boolean isSupervisor;

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

    public String getPassword() {return password;}

    public void setPassword(String password) {this.password = password;}

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

    public Boolean getIsSupervisor() {
        return isSupervisor;
    }

    public void setIsSupervisor(Boolean isSupervisor) {
        this.isSupervisor = isSupervisor;
    }

    @Override
    public String toString() {
        return "SubscriberData{" +
                "realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", drivingLicenseNumber='" + drivingLicenseNumber + '\'' +
                ", drivingLicensePic=" + Arrays.toString(drivingLicensePic) +
                ", drivingYears=" + drivingYears +
                ", identityNo='" + identityNo + '\'' +
                ", identityPic=" + Arrays.toString(identityPic) +
                ", isSupervisor=" + isSupervisor +
                '}';
    }

    public static void cleanRegistEnv(String mobile, String email){
        String newemail = EVSUtil.getUniqValue(EVSSubscriber.dao.count(), "AT000");
        String newmobile = EVSUtil.getUniqValue(EVSSubscriber.dao.count(), "15968");
        EVSSubscriber.dao.updateEmailByMobile(mobile, newemail);
        EVSSubscriber.dao.updateMobileByEmail(newemail, newmobile);
        EVSSubscriberCredential.dao.updateSubscriberCredentialNameByEmailOrMobile(email, newemail);
        EVSSubscriberCredential.dao.updateSubscriberCredentialNameByEmailOrMobile(mobile, newmobile);
    }
}


