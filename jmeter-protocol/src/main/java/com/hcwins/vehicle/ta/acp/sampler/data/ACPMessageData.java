package com.hcwins.vehicle.ta.acp.sampler.data;

/**
 * Created by tommy on 4/20/15.
 */
public interface ACPMessageData {
    public byte[] generateMessageData();

    public byte[] getCurrentMessageData();

    public String getCurrentMessageDataAsByteString();

    public String getCurrentMessageDataAsReadableString();
}
