package com.hcwins.vehicle.ta.evs.data;

import com.hcwins.vehicle.ta.evs.apidao.EVSCity;
import com.hcwins.vehicle.ta.evs.apidao.EVSProvince;

/**
 * Created by xiangzhai on 13/04/15.
 */
public class EnterpriseRegionData {
    private String provinceName;
    private String cityName;
    private String countryName;

    private boolean idRetrieved = false;

    private Long provinceId;
    private Long cityId;
    private Long countryId;

    private void retrieve() {
        if (!idRetrieved) {
            //TODO: retrieve id from db or service - missing countryId
            provinceId = EVSProvince.dao.getProvinceIdByName(provinceName).get(0).getId();
            cityId = EVSCity.dao.findCityIdByName(cityName).get(0).getId();
        }
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Long getProvinceId() {
        retrieve();

        return provinceId;
    }

    public Long getCityId() {
        retrieve();

        return cityId;
    }

    public Long getCountryId() {
        retrieve();

        return countryId;
    }

    @Override
    public String toString() {
        return "EnterpriseRegionData{" +
                "provinceName='" + provinceName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", countryName='" + countryName + '\'' +
                '}';
    }
}
