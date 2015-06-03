package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUsers {
    public static GetUnauditUsersSessionRelatedRequest getGetUnauditUsersRequest(int pageSize, int pageNo) {
        GetUnauditUsersSessionRelatedRequest getUnauditUsersRequest = new GetUnauditUsersSessionRelatedRequest();
        getUnauditUsersRequest.setPageSize(pageSize);
        getUnauditUsersRequest.setPageNo(pageNo);
        return getUnauditUsersRequest;
    }

    public static GetUnauditUsersResponse postGetUnauditUsersRequest(int pageSize, int pageNo) {
        GetUnauditUsersSessionRelatedRequest getUnauditUsersRequest = getGetUnauditUsersRequest(pageSize, pageNo);
        getUnauditUsersRequest.post();
        return getUnauditUsersRequest.getLastResponseAsObj();
    }

    public static GetUnauditUsersResponse postGetUnauditUsersRequest(int pageSize, int pageNo, String token) {
        GetUnauditUsersSessionRelatedRequest getUnauditUsersRequest = getGetUnauditUsersRequest(pageSize, pageNo);
        getUnauditUsersRequest.post(token);
        return getUnauditUsersRequest.getLastResponseAsObj();
    }
}
