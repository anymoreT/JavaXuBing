package com.hcwins.vehicle.ta.evs.apiobj;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.jayway.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * Created by wenji on 08/05/15.
 */
public abstract class BsonRequest extends BaseRequest {
    private static final Logger logger = LoggerFactory.getLogger(BsonRequest.class);

    public byte[] getBson() {
        try {
            return EVSUtil.getBsonMapper().writeValueAsBytes(this);
        } catch (JsonProcessingException e) {
            logger.error("failed to get binary json", e);
            return null;
        }
    }

    public Response post(Map<String, String> header, int expectedHttpStatusCode) {
        lastResponse = EVSUtil.callPostJson(getAPI(), getBson(), header, expectedHttpStatusCode);
        return lastResponse;
    }

    public <ResponseType> ResponseType getLastResponseAsObj(Class<ResponseType> responseClass) {
        try {
            return EVSUtil.getBsonMapper().readValue(lastResponse.getBody().asByteArray(), responseClass);
        } catch (IOException e) {
            logger.error("failed to parse binary json", e);
            return null;
        }
    }
}
