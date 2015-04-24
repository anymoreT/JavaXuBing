package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;

/**
 * Created by tommy on 4/22/15.
 */
public class Login {
    public static LoginRequest getLoginRequest(String account, String password) {
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setAccount(account);
        loginRequest.setPassword(password);
        return loginRequest;
    }

    public static LoginResponse postLoginRepuest(String account, String password) {
        return postLoginRepuest(account, password, false);
    }

    public static LoginResponse postLoginRepuest(String account, String password, boolean cacheIt) {
        LoginRequest loginRequest = getLoginRequest(account, password);
        loginRequest.post();

        LoginResponse loginResponse = loginRequest.getLastResponseAsObj();

        if (cacheIt) {
            EVSUtil.cacheEnterpriseSession(loginRequest, loginResponse);
        }

        return loginResponse;
    }
}
