package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by wenji on 13/04/15.
 */
public class CancelAdminRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getCancelAdmin();

    private String mobile;
    private String password;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public CancelAdminResponse getLastResponseAsObj() {
        return getLastResponseAsObj(CancelAdminResponse.class);
    }
}



