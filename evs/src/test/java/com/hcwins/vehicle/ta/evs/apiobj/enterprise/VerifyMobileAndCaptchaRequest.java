package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by wenji on 10/04/15.
 */
public class VerifyMobileAndCaptchaRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().enterprise.VerifyMobileAndCaptcha;
    public String mobile;
    public String captcha;

    public String getAPI() {
        return api;
    }

    public VerifyMobileAndCaptchaResponse getLastResponseAsObj() {
        return getLastResponseAsObj(VerifyMobileAndCaptchaResponse.class);
    }


}
