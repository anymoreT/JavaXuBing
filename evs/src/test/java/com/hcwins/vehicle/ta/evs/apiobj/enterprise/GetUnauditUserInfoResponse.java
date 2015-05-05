package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountResponse;

import java.util.ArrayList;

/**
 * Created by wenji on 28/04/15.
 */
    public class GetUnauditUserInfoResponse extends BaseAccountResponse {
        private subscriber apiSubscriber;

    public subscriber getApiSubscriber() {
        return apiSubscriber;
    }

    public void setApiSubscriber(subscriber apiSubscriber) {
        this.apiSubscriber = apiSubscriber;
    }
}
