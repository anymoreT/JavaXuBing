package com.hcwins.vehicle.ta.evs.apiobj.user;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseSessionRelatedRequest;

/**
 * Created by wenji on 28/05/15.
 */
public class CompleteInfoSessionRequest extends BaseSessionRelatedRequest {
    protected final static String api = EVSUtil.getAPISet().getUser().getCompleteInfo();
    private Long enterpriseId;
    private String realName;
    private String email;
    private Long cityId;
    private Long provinceId;

    @Override
    public String getAPI() {return api;}

    public Long getEnterpriseId() {
        return enterpriseId;
    }

    public void setEnterpriseId(Long enterpriseId) {
        this.enterpriseId = enterpriseId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Override
    public CompleteInfoResponse getLastResponseAsObj() {
        return getLastResponseAsObj(CompleteInfoResponse.class);
    }
}
