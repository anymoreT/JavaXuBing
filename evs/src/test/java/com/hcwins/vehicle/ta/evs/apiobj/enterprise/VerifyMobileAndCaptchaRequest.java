package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by wenji on 10/04/15.
 */
public class VerifyMobileAndCaptchaRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getVerifyMobileAndCaptcha();

    private String mobile;
    private String captcha;

    @Override
    public String getAPI() {
        return api;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public VerifyMobileAndCaptchaResponse getLastResponseAsObj() {
        return getLastResponseAsObj(VerifyMobileAndCaptchaResponse.class);
    }
}
