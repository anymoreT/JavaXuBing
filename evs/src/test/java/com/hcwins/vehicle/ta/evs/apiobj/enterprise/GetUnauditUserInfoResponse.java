package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.BaseResponse;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUserInfoResponse extends BaseResponse {
    private Subscriber apiSubscriber;

    public Subscriber getApiSubscriber() {
        return apiSubscriber;
    }

    public void setApiSubscriber(Subscriber apiSubscriber) {
        this.apiSubscriber = apiSubscriber;
    }
}
