package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by tommy on 3/24/15.
 */
public class CaptchaRegistRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getCaptchaRegist();

    private String mobile;

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

    @Override
    public CaptchaRegistResponse getLastResponseAsObj() {
        return getLastResponseAsObj(CaptchaRegistResponse.class);
    }
}
