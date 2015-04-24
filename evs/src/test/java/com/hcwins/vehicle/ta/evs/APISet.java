package com.hcwins.vehicle.ta.evs;

import com.hcwins.vehicle.ta.evs.apiset.EnterpriseAPI;
import com.hcwins.vehicle.ta.evs.apiset.TripAPI;
import com.hcwins.vehicle.ta.evs.apiset.UserAPI;

public class APISet {
    private EnterpriseAPI enterprise;
    private UserAPI user;
    private TripAPI trip;

    public EnterpriseAPI getEnterprise() {
        return enterprise;
    }

    public void setEnterprise(EnterpriseAPI enterprise) {
        this.enterprise = enterprise;
    }

    public UserAPI getUser() {
        return user;
    }

    public void setUser(UserAPI user) {
        this.user = user;
    }

    public TripAPI getTrip() {
        return trip;
    }

    public void setTrip(TripAPI trip) {
        this.trip = trip;
    }

    @Override
    public String toString() {
        return "APISet{" +
                "enterprise=" + enterprise +
                ", user=" + user +
                ", trip=" + trip +
                '}';
    }
}
