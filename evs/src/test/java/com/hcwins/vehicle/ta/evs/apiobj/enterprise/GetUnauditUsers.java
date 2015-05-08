package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import java.util.Map;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUsers {
    public static GetUnauditUsersRequest getGetUnauditUsersRequest(int pageSize, int pageNo) {
        GetUnauditUsersRequest getUnauditUsersRequest = new GetUnauditUsersRequest();
        getUnauditUsersRequest.setPageSize(pageSize);
        getUnauditUsersRequest.setPageNo(pageNo);
        return getUnauditUsersRequest;
    }

    public static GetUnauditUsersResponse postGetUnauditUsersRequest(int pageSize, int pageNo,Map head) {
        GetUnauditUsersRequest getUnauditUsersRequest = getGetUnauditUsersRequest(pageSize, pageNo);
        getUnauditUsersRequest.post(head);
        return getUnauditUsersRequest.getLastResponseAsObj();
    }
}
