package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountRequest;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUserInfoRequest extends BaseAccountRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getGetUnauditUserInfo();
    private Long subscriberId;

    @Override

    public String getAPI() {
        return null;
    }

    public Long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(Long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public GetUnauditUserInfoResponse getLastResponseAsObj() {
        return getLastResponseAsObj(GetUnauditUserInfoResponse.class);
    }
}
