package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPLocationReceiveMessage;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

public class ACPClientLocationReceiveMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    public ACPClientLocationReceiveMessage() {
        //
    }

    public ACPClientLocationReceiveMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);

        //
    }

    @Override
    public String getDefaultRequestData() {
        return ACPLocationReceiveMessage.getDefaultRequestData();
    }

    @Override
    public void setUp() {
        super.setUp();

        setACPMessage(getGson().fromJson(getRequestData(), ACPLocationReceiveMessage.class));
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean returnMessageRequired() {
        return false;
    }
}
