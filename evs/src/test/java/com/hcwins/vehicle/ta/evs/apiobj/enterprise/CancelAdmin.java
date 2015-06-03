package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;

/**
 * Created by wenji on 13/04/15.
 */
public class CancelAdmin {
    public static CancelAdminRequest getCancelAdminRequest(String mobile, String password) {
        CancelAdminRequest cancelAdminRequest = new CancelAdminRequest();
        cancelAdminRequest.setMobile(mobile);
        cancelAdminRequest.setPassword(password);
        return cancelAdminRequest;
    }

    public static CancelAdminResponse postCancelAdminRequest(String mobile, String password) {
        CancelAdminRequest cancelAdminRequest = getCancelAdminRequest(mobile, password);
        cancelAdminRequest.post(EVSUtil.getEnterpriseToken(mobile));
        return cancelAdminRequest.getLastResponseAsObj();
    }

    public static CancelAdminResponse postCancelAdminRequest(String mobile, String password, String token) {
        CancelAdminRequest cancelAdminRequest = getCancelAdminRequest(mobile, password);
        cancelAdminRequest.post(token);
        return cancelAdminRequest.getLastResponseAsObj();
    }
}

