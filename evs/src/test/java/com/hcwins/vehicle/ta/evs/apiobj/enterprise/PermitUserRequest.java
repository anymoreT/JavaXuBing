package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountRequest;

/**
 * Created by wenji on 28/04/15.
 */
public class PermitUserRequest extends BaseAccountRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getPermitUser();
    private long subscriberId;
    private Boolean manager;

    @Override

    public String getAPI() {
        return null;
    }

    public long getSubscriberId() {
        return subscriberId;
    }

    public void setSubscriberId(long subscriberId) {
        this.subscriberId = subscriberId;
    }

    public Boolean getManager() {
        return manager;
    }

    public void setManager(Boolean manager) {
        this.manager = manager;
    }

    public PermitUserResponse getLastResponseAsObj() {
        return getLastResponseAsObj(PermitUserResponse.class);
    }
}

