package com.hcwins.vehicle.ta.evs;

import com.hcwins.vehicle.ta.evs.data.*;

import java.util.List;

/**
 * Created by tommy on 3/23/15.
 */
public class DataSet {
    private List<EnterpriseData> enterprises;
    private List<EnterpriseAdminData> enterpriseAdmins;
    private List<EnterpriseRegionData> enterpriseRegions;
    private List<SubscriberData> subscriberData;

    public List<EnterpriseData> getEnterprises() {
        return enterprises;
    }

    public void setEnterprises(List<EnterpriseData> enterprises) {
        this.enterprises = enterprises;
    }

    public List<EnterpriseAdminData> getEnterpriseAdmins() {
        return enterpriseAdmins;
    }

    public void setEnterpriseAdmins(List<EnterpriseAdminData> enterpriseAdmins) {
        this.enterpriseAdmins = enterpriseAdmins;
    }

    public List<EnterpriseRegionData> getEnterpriseRegions() {
        return enterpriseRegions;
    }

    public void setEnterpriseRegions(List<EnterpriseRegionData> enterpriseRegions) {
        this.enterpriseRegions = enterpriseRegions;
    }

    public List<SubscriberData> getSubscriberData() {
        return subscriberData;
    }

    public void setSubscriberData(List<SubscriberData> subscriberData) {
        this.subscriberData = subscriberData;
    }

    @Override
    public String toString() {
        return "DataSet{" +
                "enterprises=" + enterprises +
                ", enterpriseAdmins=" + enterpriseAdmins +
                ", enterpriseRegions=" + enterpriseRegions +
                ", subscriberData=" + subscriberData +
                '}';
    }
}
