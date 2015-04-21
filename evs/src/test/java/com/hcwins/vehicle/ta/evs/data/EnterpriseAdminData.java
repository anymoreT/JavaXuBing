package com.hcwins.vehicle.ta.evs.data;

/**
 * Created by tommy on 3/24/15.
 */
public class EnterpriseAdminData {
    public String mobile;
    public String realName;
    public String email;
    public String password;

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
