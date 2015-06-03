package com.hcwins.vehicle.ta.evs.data;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterprise;

/**
 * Created by tommy on 3/24/15.
 */
public class EnterpriseData {
    private String enterpriseName;
    private String website;

    public String getEnterpriseName() {
        return enterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        this.enterpriseName = enterpriseName;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public static void cleanEnterpriseEnv(String enterpriseName){
        String newEnterpriseName = EVSUtil.getUniqValue(EVSEnterprise.dao.count(), "AT000");
        String newWebsite = EVSUtil.getUniqValue(EVSEnterprise.dao.count(), "15968");
        EVSEnterprise.dao.updateWebsiteByEnterpriseName(enterpriseName, newWebsite);
        EVSEnterprise.dao.updatEnterpriseNameByWebsite(newWebsite, newEnterpriseName);

    }

    @Override
    public String toString() {
        return "EnterpriseData{" +
                "enterpriseName='" + enterpriseName + '\'' +
                ", website='" + website + '\'' +
                '}';
    }
}
