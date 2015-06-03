package com.hcwins.vehicle.ta.evs.apiobj.user;

import com.hcwins.vehicle.ta.evs.apidao.EVSCaptcha;

import java.util.List;

/**
 * Created by tommy on 3/26/15.
 */
public class CaptchaRegist {
    public static CaptchaRegistRequest getCaptchaRegistRequest(String mobile) {
        CaptchaRegistRequest captchaRegistRequest = new CaptchaRegistRequest();
        captchaRegistRequest.setMobile(mobile);
        return captchaRegistRequest;
    }

    public static CaptchaRegistResponse postCaptchaRegistRequest(String mobile) {
        CaptchaRegistRequest captchaRegistRequest = getCaptchaRegistRequest(mobile);
        captchaRegistRequest.post();
        return captchaRegistRequest.getLastResponseAsObj();
    }

    public static List<EVSCaptcha> getCaptchaRegists(String mobile) {
        return EVSCaptcha.dao.findCaptchasByMobileModuleStatus(
                mobile,
                EVSCaptcha.Module.USER_REGIST.name(),
                EVSCaptcha.Status.NEW.name());
    }

    public static List<EVSCaptcha> postAndGetCaptchas(String mobile) {
        postCaptchaRegistRequest(mobile);
        return getCaptchaRegists(mobile);
    }
}
