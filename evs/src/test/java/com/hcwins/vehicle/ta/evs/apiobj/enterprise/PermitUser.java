package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import java.util.Map;

/**
 * Created by wenji on 28/04/15.
 */
public class PermitUser {
    public static PermitUserRequest getPermitUserRequest(long subscriberId, Boolean manager) {
        PermitUserRequest permitUserRequest = new PermitUserRequest();
        permitUserRequest.setSubscriberId(subscriberId);
        permitUserRequest.setManager(manager);
        return permitUserRequest;
    }

    public static PermitUserResponse postPermitUserResponse(long subscriberId, Boolean manager, Map head) {
        PermitUserRequest permitUserRequest = getPermitUserRequest(subscriberId, manager);
        permitUserRequest.post(head);
        return permitUserRequest.getLastResponseAsObj();
    }
}
