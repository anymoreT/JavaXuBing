package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class RegistRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().getEnterprise().getRegist();

    private String enterpriseName;
    private String enterpriseWebsite;
    private Long cityId;
    private String adminRealName;
    private String adminMobile;
    private String adminEmail;
    private String adminPassword;
    private Long provinceId;

    @Override
    public String getAPI() {
        return api;
    }

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getAdminRealName() {
        return adminRealName;
    }

    public void setAdminRealName(String adminRealName) {
        this.adminRealName = adminRealName;
    }

    public String getAdminMobile() {
        return adminMobile;
    }

    public void setAdminMobile(String adminMobile) {
        this.adminMobile = adminMobile;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail;
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public RegistResponse getLastResponseAsObj() {
        return getLastResponseAsObj(RegistResponse.class);
    }
}
