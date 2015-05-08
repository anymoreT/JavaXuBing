package com.hcwins.vehicle.ta.evs.apiobj;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.jayway.restassured.response.Cookie;
import com.jayway.restassured.response.Cookies;
import com.jayway.restassured.response.Response;

import java.util.HashMap;

/**
 * Created by tommy on 4/24/15.
 */
public abstract class BaseSessionRelatedRequest extends BaseRequest {
    public String getToken() {
        return EVSUtil.getCurrentToken();
    }

    public Response post(String token, int expectedHttpStatusCode) {
        HashMap<String, String> header = new HashMap<String, String>();
        header.put("Cookie", new Cookies(new Cookie.Builder("TOKEN", token).build()).toString());
        return post(header, expectedHttpStatusCode);
    }

    public Response post(String token) {
        return post(token, 200);
    }

    @Override
    public Response post() {
        return post(getToken(), 200);
    }
}
