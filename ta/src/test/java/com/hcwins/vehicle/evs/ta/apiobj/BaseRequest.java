package com.hcwins.vehicle.evs.ta.apiobj;

import com.hcwins.vehicle.evs.ta.EVSUtil;
import com.jayway.restassured.response.Response;

import java.util.Map;

/**
 * Created by tommy on 3/24/15.
 */
public abstract class BaseRequest {
    protected String loggerId = EVSUtil.getLoggerId();
    public String token;

    protected Response lastResponse;

    public abstract String getAPI();

    public String getJson() {
        return EVSUtil.getGson().toJson(this);
    }

    public Response post(Map<String, String> header, int expectedHttpStatusCode) {
        lastResponse = EVSUtil.callPostJson(getAPI(), getJson(), header, expectedHttpStatusCode);
        return lastResponse;
    }

    public Response post(Map<String, String> header) {
        return post(header, 200);
    }

    public Response post(int expectedHttpStatusCode) {
        return post(null, expectedHttpStatusCode);
    }

    public Response post() {
        return post(200);
    }

    public Response getLastResponse() {
        return lastResponse;
    }

    public <ResponseType> ResponseType getLastResponseAsObj(Class<ResponseType> responseClass) {
        return EVSUtil.getGson().fromJson(getLastResponse().asString(), responseClass);
    }

    public abstract <BaseResponse> BaseResponse getLastResponseAsObj();
}
