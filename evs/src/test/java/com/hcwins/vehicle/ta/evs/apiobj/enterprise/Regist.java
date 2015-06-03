package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.data.EnterpriseAdminData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseRegionData;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class Regist {
    public static RegistRequest getRegistRequest(String enterpriseName, String enterpriseWebsite, Long cityId, String adminRealName, String adminMobile, String adminEmail, String adminPassword, Long provinceId) {
        RegistRequest registRequest = new RegistRequest();
        registRequest.setEnterpriseName(enterpriseName);
        registRequest.setEnterpriseWebsite(enterpriseWebsite);
        registRequest.setCityId(cityId);
        registRequest.setAdminRealName(adminRealName);
        registRequest.setAdminMobile(adminMobile);
        registRequest.setAdminEmail(adminEmail);
        registRequest.setAdminPassword(adminPassword);
        registRequest.setProvinceId(provinceId);
        return registRequest;
    }

    public static RegistResponse postRegistRequest(String enterpriseName, String enterpriseWebsite, Long cityId, String adminRealName, String adminMobile, String adminEmail, String adminPassword, Long provinceId) {
        RegistRequest registRequest = getRegistRequest(enterpriseName, enterpriseWebsite, cityId, adminRealName, adminMobile, adminEmail, adminPassword, provinceId);
        registRequest.post();
        return registRequest.getLastResponseAsObj();
    }

    public static RegistRequest getRegistRequest(EnterpriseData enterpriseData, EnterpriseRegionData regionData, EnterpriseAdminData adminData) {
        RegistRequest registRequest = new RegistRequest();
        registRequest.setEnterpriseName(enterpriseData.getEnterpriseName());
        registRequest.setEnterpriseWebsite(enterpriseData.getWebsite());
        registRequest.setCityId(regionData.getCityId());
        registRequest.setAdminRealName(adminData.getRealName());
        registRequest.setAdminMobile(adminData.getMobile());
        registRequest.setAdminEmail(adminData.getEmail());
        registRequest.setAdminPassword(adminData.getPassword());
        registRequest.setProvinceId(regionData.getProvinceId());
        return registRequest;
    }

    public static RegistResponse postRegistRequest(EnterpriseData enterpriseData, EnterpriseRegionData regionData, EnterpriseAdminData adminData) {
        RegistRequest registRequest = getRegistRequest(enterpriseData, regionData, adminData);
        registRequest.post();
        return registRequest.getLastResponseAsObj();
    }
}
