package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by tommy on 3/24/15.
 */
public class CaptchaRegistRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().enterprise.captchaRegist;

    public String mobile;

    public String getAPI() {
        return api;
    }

    public CaptchaRegistResponse getLastResponseAsObj() {
        return getLastResponseAsObj(CaptchaRegistResponse.class);
    }
}
