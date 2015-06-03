package com.hcwins.vehicle.ta.evs.data;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterpriseAdmin;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterpriseAdminCredential;

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

    public static void cleanRegistEnv(String mobile, String email){
        String newemail = EVSUtil.getUniqValue(EVSEnterpriseAdmin.dao.count(), "AT000");
        String newmobile = EVSUtil.getUniqValue(EVSEnterpriseAdmin.dao.count(), "15968");
        EVSEnterpriseAdmin.dao.updateEmailByMobile(mobile, newemail);
        EVSEnterpriseAdmin.dao.updateMobileByEmail(newemail, newmobile);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(email, newemail);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(mobile, newmobile);
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
