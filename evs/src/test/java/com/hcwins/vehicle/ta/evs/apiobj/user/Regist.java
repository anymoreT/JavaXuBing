package com.hcwins.vehicle.ta.evs.apiobj.user;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class Regist {
    public static RegistRequest getRegistRequest(String mobile, String password) {
        RegistRequest registRequest = new RegistRequest();
        registRequest.setMobile(mobile);
        registRequest.setPassword(password);
        return registRequest;
    }

    public static RegistResponse postRegistRequest(String mobile, String password) {
        RegistRequest registRequest = getRegistRequest(mobile, password);
        registRequest.post();
        return registRequest.getLastResponseAsObj();
    }

}