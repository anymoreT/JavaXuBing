package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class EnterpriseRegist {
    public static EnterpriseRegistRequest getEnterpriseRegistRequest(String enterpriseName, String enterpriseWebsite, String cityId, String adminRealName, String adminMobile, String adminEmail, String adminPassword, String provinceId) {
        EnterpriseRegistRequest enterpriseRegistRequest = new EnterpriseRegistRequest();
        enterpriseRegistRequest.enterpriseName = enterpriseName;
        enterpriseRegistRequest.enterpriseWebsite = enterpriseWebsite;
        enterpriseRegistRequest.cityId = cityId;
        enterpriseRegistRequest.adminRealName = adminRealName;
        enterpriseRegistRequest.adminMobile = adminMobile;
        enterpriseRegistRequest.adminEmail = adminEmail;
        enterpriseRegistRequest.adminPassword = adminPassword;
        enterpriseRegistRequest.provinceId = provinceId;
        return enterpriseRegistRequest;
    }

    public static EnterpriseRegistResponse postEnterpriseRegistRequest(String enterpriseName, String enterpriseWebsite, String cityId, String adminRealName, String adminMobile, String adminEmail, String adminPassword, String provinceId) {
        EnterpriseRegistRequest enterpriseRegistRequest = getEnterpriseRegistRequest(enterpriseName, enterpriseWebsite, cityId, adminRealName, adminMobile, adminEmail, adminPassword, provinceId);
        enterpriseRegistRequest.post();
        return enterpriseRegistRequest.getLastResponseAsObj();
    }

}
