package com.hcwins.vehicle.ta.acp.sampler.data;

import com.hcwins.vehicle.ta.acp.sampler.sampler.ACPException;

/**
 * Created by tommy on 4/20/15.
 */
public interface ACPMessage {
    public byte[] generateMessage();

    public byte[] getCurrentMessage();

    public String getCurrentMessageAsByteString();

    public String getCurrentMessageAsReadableString();

    public String handleReturnMessage(byte[] msg) throws ACPException;
}
