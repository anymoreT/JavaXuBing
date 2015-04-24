package com.hcwins.vehicle.ta.evs.apiobj.trip;

/**
 * Created by tommy on 4/24/15.
 */
public class GetVehicles {
    public static GetVehiclesRequest getGetVehiclesRequest() {
        GetVehiclesRequest getVehiclesRequest = new GetVehiclesRequest();
        return getVehiclesRequest;
    }

    public static GetVehiclesResponse postGetVehiclesRequest() {
        GetVehiclesRequest getVehiclesRequest = getGetVehiclesRequest();
        getVehiclesRequest.post();
        return getVehiclesRequest.getLastResponseAsObj();
    }
}
