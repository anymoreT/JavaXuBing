package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class Regist {
    public static RegistRequest getRegistRequest(String enterpriseName, String enterpriseWebsite, Object cityId, String adminRealName, String adminMobile, String adminEmail, String adminPassword, Object provinceId) {
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

    public static RegistResponse postRegistRequest(String enterpriseName, String enterpriseWebsite, Object cityId, String adminRealName, String adminMobile, String adminEmail, String adminPassword, Object provinceId) {
        RegistRequest registRequest = getRegistRequest(enterpriseName, enterpriseWebsite, cityId, adminRealName, adminMobile, adminEmail, adminPassword, provinceId);
        registRequest.post();
        return registRequest.getLastResponseAsObj();
    }
}
