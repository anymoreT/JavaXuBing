package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import java.util.Map;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUserInfo {
    public static GetUnauditUserInfoRequest getGetUnauditUserInfoRequest(Long subscriberId) {
        GetUnauditUserInfoRequest getUnauditUserInfoRequest = new GetUnauditUserInfoRequest();
        getUnauditUserInfoRequest.setSubscriberId(subscriberId);
        return getUnauditUserInfoRequest;
    }

    public static GetUnauditUserInfoResponse postGetUnauditUserInfoRequest(Long subscriberId,Map head) {
        GetUnauditUserInfoRequest getUnauditUserInfoRequest = getGetUnauditUserInfoRequest(subscriberId);
        getUnauditUserInfoRequest.post(head);
        return getUnauditUserInfoRequest.getLastResponseAsObj();
    }
}
