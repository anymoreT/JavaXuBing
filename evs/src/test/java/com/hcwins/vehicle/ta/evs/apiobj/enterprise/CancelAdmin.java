package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

/**
 * Created by wenji on 13/04/15.
 */
public class CancelAdmin {
    public static CancelAdminRequest getCancelAdminRequest(String mobile,String password) {
        CancelAdminRequest cancelAdminRequest = new CancelAdminRequest();
        cancelAdminRequest.mobile = mobile;
        cancelAdminRequest.password = password;
        return cancelAdminRequest;
    }

    public static CancelAdminResponse postCancelAdminRequest(String mobile,String password) {
        CancelAdminRequest cancelAdminRequest = getCancelAdminRequest(mobile, password);
        cancelAdminRequest.post();
        return cancelAdminRequest.getLastResponseAsObj();
    }




}
