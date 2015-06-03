package com.hcwins.vehicle.ta.evs.apiworkflow;

import com.hcwins.vehicle.ta.evs.DataSet;
import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSCity;
import com.hcwins.vehicle.ta.evs.apidao.EVSProvince;

/**
 * Created by xiangzhai on 30/04/15.
 */
public class BaseFlow {
    private static DataSet dataSet = EVSUtil.getDataSet();

    static String enterpriseName = dataSet.getEnterprises().get(2).getEnterpriseName();
    static String enterpriseWebSite = dataSet.getEnterprises().get(2).getWebsite();
    static String mobile = dataSet.getEnterpriseAdmins().get(2).getMobile();
    static String realName = dataSet.getEnterpriseAdmins().get(2).getRealName();
    static String email = dataSet.getEnterpriseAdmins().get(2).getEmail();
    static String password = dataSet.getEnterpriseAdmins().get(2).getPassword();
    static String provinceName = dataSet.getEnterpriseRegions().get(0).getProvinceName();
    static String cityName = dataSet.getEnterpriseRegions().get(0).getCityName();
    static Long provinceId = EVSProvince.dao.getProvinceIdByName(provinceName).get(0).getId();
    static Long cityId = EVSCity.dao.findCityIdByName(cityName).get(0).getId();

}
