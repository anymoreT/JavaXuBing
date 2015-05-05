package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import java.util.Map;
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

    public static CancelAdminResponse postCancelAdminRequest(String mobile, String password,Map head) {
        CancelAdminRequest cancelAdminRequest = getCancelAdminRequest(mobile, password);
        cancelAdminRequest.post(head);
        return cancelAdminRequest.getLastResponseAsObj();
    }
}
