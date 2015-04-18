package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by wenji on 13/04/15.
 */
public class CancelAdminRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().enterprise.CancelAdmin;

    public String mobile;
    public String password;
    public String getAPI() { return api;}


    public CancelAdminResponse getLastResponseAsObj() {
        return getLastResponseAsObj(CancelAdminResponse.class);
    }
}



