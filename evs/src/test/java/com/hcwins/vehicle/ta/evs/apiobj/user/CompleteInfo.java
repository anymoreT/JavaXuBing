package com.hcwins.vehicle.ta.evs.apiobj.user;

/**
 * Created by wenji on 28/05/15.
 */
public class CompleteInfo {
    public static CompleteInfoSessionRequest getCompleteInfoRequest(Long enterpriseId, String realName, String email, Long cityId, Long provinceId) {
        CompleteInfoSessionRequest completeInfoRequest = new CompleteInfoSessionRequest();
        completeInfoRequest.setEnterpriseId(enterpriseId);
        completeInfoRequest.setRealName(realName);
        completeInfoRequest.setEmail(email);
        completeInfoRequest.setCityId(cityId);
        completeInfoRequest.setProvinceId(provinceId);
        return completeInfoRequest;
    }

    public static CompleteInfoResponse postCompleteInfoRequest(Long enterpriseId, String realName, String email, Long cityId, Long provinceId) {
        CompleteInfoSessionRequest completeInfoRequest = getCompleteInfoRequest(enterpriseId, realName, email, cityId, provinceId);
        completeInfoRequest.post();
        return completeInfoRequest.getLastResponseAsObj();
    }

    public static CompleteInfoResponse postCompleteInfoRequest(long enterpriseId, String realName, String email, long cityId, long provinceId, String token) {
        CompleteInfoSessionRequest completeInfoRequest = getCompleteInfoRequest(enterpriseId, realName, email, cityId, provinceId);
        completeInfoRequest.post(token);
        return completeInfoRequest.getLastResponseAsObj();
    }

}
