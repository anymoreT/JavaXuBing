package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUserInfo {
    public static GetUnauditUserInfoSessionRelatedRequest getGetUnauditUserInfoRequest(Long subscriberId) {
        GetUnauditUserInfoSessionRelatedRequest getUnauditUserInfoRequest = new GetUnauditUserInfoSessionRelatedRequest();
        getUnauditUserInfoRequest.setSubscriberId(subscriberId);
        return getUnauditUserInfoRequest;
    }

    public static GetUnauditUserInfoResponse postGetUnauditUserInfoRequest(Long subscriberId) {
        GetUnauditUserInfoSessionRelatedRequest getUnauditUserInfoRequest = getGetUnauditUserInfoRequest(subscriberId);
        getUnauditUserInfoRequest.post();
        return getUnauditUserInfoRequest.getLastResponseAsObj();
    }

    public static GetUnauditUserInfoResponse postGetUnauditUserInfoRequest(Long subscriberId, String token) {
        GetUnauditUserInfoSessionRelatedRequest getUnauditUserInfoRequest = getGetUnauditUserInfoRequest(subscriberId);
        getUnauditUserInfoRequest.post(token);
        return getUnauditUserInfoRequest.getLastResponseAsObj();
    }
}
