package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BsonSessionRelatedRequest;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUsersSessionRelatedRequest extends BsonSessionRelatedRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getGetUnauditUsers();
    private int pageSize;
    private int pageNo;


    @Override
    public String getAPI() {
        return api;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public GetUnauditUsersResponse getLastResponseAsObj() {
             return getLastResponseAsObj(GetUnauditUsersResponse.class);
    }
}

