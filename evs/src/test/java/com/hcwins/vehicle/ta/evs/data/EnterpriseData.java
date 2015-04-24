package com.hcwins.vehicle.ta.evs.data;

/**
 * Created by tommy on 3/24/15.
 */
public class EnterpriseData {
    private String enterpriseName;
    private String enterpriseWebsite;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getEnterpriseWebsite() {
        return enterpriseWebsite;
    }

    public void setEnterpriseWebsite(String enterpriseWebsite) {
        this.enterpriseWebsite = enterpriseWebsite;
    }

    @Override
    public String toString() {
        return "EnterpriseData{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", enterpriseWebsite='" + enterpriseWebsite + '\'' +
                '}';
    }
}
