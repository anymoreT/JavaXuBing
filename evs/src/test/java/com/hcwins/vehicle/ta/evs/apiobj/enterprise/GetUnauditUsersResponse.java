package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountResponse;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUsersResponse extends BaseAccountResponse {
    private ArrayList<subscriber> subscribers;

    public ArrayList<subscriber> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(ArrayList<subscriber> subscribers) {
        this.subscribers = subscribers;
    }




}
