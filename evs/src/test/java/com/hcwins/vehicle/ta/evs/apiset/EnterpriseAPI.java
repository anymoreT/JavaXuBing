package com.hcwins.vehicle.ta.evs.apiset;

/**
 * Created by tommy on 3/23/15.
 */
public class EnterpriseAPI {
    private String captchaRegist;
    private String verifyMobileAndCaptcha;
    private String regist;

    private String login;

    private String cancelAdmin;

    private String getUnauditUsers;
    private String getUnauditUserInfo;
    private String permitUser;

    public String getCaptchaRegist() {
        return captchaRegist;
    }

    public void setCaptchaRegist(String captchaRegist) {
        this.captchaRegist = captchaRegist;
    }

    public String getVerifyMobileAndCaptcha() {
        return verifyMobileAndCaptcha;
    }

    public void setVerifyMobileAndCaptcha(String verifyMobileAndCaptcha) {
        this.verifyMobileAndCaptcha = verifyMobileAndCaptcha;
    }

    public String getRegist() {
        return regist;
    }

    public void setRegist(String regist) {
        this.regist = regist;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getCancelAdmin() {
        return cancelAdmin;
    }

    public void setCancelAdmin(String cancelAdmin) {
        this.cancelAdmin = cancelAdmin;
    }

    public String getGetUnauditUsers() {
        return getUnauditUsers;
    }

    public void setGetUnauditUsers(String getUnauditUsers) {
        this.getUnauditUsers = getUnauditUsers;
    }

    public String getGetUnauditUserInfo() {return getUnauditUserInfo;}

    public void setGetUnauditUserInfo(String getUnauditUserInfo) {
        this.getUnauditUserInfo = getUnauditUserInfo;
    }

    public String getPermitUser() {
        return permitUser;
    }

    public void setPermitUser(String permitUser) {
        this.permitUser = permitUser;
    }
}
