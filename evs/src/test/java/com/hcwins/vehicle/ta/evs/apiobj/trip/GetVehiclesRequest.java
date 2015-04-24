package com.hcwins.vehicle.ta.evs.apiobj.trip;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseSessionRelatedRequest;

/**
 * Created by tommy on 4/24/15.
 */
public class GetVehiclesRequest extends BaseSessionRelatedRequest {
    protected final static String api = EVSUtil.getAPISet().getTrip().getGetVehicles();

    @Override
    public String getAPI() {
        return api;
    }

    @Override
    public GetVehiclesResponse getLastResponseAsObj() {
        return getLastResponseAsObj(GetVehiclesResponse.class);
    }
}
