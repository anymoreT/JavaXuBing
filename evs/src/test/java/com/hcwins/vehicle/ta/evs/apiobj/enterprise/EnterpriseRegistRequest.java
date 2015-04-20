package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.BaseRequest;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class EnterpriseRegistRequest extends BaseRequest {
    protected final static String api = EVSUtil.getAPISet().enterprise.enterpriseRegist;

    public String enterpriseName;

    public String enterpriseWebsite;

    public String cityId;

    public String adminRealName;

    public String adminMobile;

    public String adminEmail;

    public String adminPassword;

    public String provinceId;


    public String getAPI() {
        return api;
    }

    public EnterpriseRegistResponse getLastResponseAsObj() {
        return getLastResponseAsObj(EnterpriseRegistResponse.class);
    }

}
