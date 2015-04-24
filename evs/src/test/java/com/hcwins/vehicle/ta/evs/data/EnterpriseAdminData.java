package com.hcwins.vehicle.ta.evs.data;

/**
 * Created by tommy on 3/24/15.
 */
public class EnterpriseAdminData {
    private String mobile;
    private String realName;
    private String email;
    private String password;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "EnterpriseAdminData{" +
                "mobile='" + mobile + '\'' +
                ", realName='" + realName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
