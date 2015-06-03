package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.BaseResponse;

import java.util.ArrayList;

/**
 * Created by wenji on 28/04/15.
 */
public class GetUnauditUsersResponse extends BaseResponse{
    private ArrayList<Subscriber> subscriberList;

    public ArrayList<Subscriber> getSubscriberList() {
        return subscriberList;
    }

    public void setSubscriberList(ArrayList<Subscriber> subscriberList) {
        this.subscriberList = subscriberList;
    }
}




