package com.hcwins.vehicle.ta.evs.apiobj.user;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class RegistRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().getUser().getRegist();

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
    public RegistResponse getLastResponseAsObj() {
        return getLastResponseAsObj(RegistResponse.class);
    }
}
