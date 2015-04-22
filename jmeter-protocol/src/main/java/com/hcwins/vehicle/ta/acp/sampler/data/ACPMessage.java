package com.hcwins.vehicle.ta.acp.sampler.data;

/**
 * Created by tommy on 4/20/15.
 */
public interface ACPMessage {
    public byte[] generateMessage();

    public byte[] getCurrentMessage();

    public String getCurrentMessageAsByteString();

    public String getCurrentMessageAsReadableString();
}
