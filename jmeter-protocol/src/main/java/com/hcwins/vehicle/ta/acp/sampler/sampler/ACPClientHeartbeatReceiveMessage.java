package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPHeartbeatReceiveMessage;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created by xiangzhai on 20/05/15.
 */
public class ACPClientHeartbeatReceiveMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    public ACPClientHeartbeatReceiveMessage() {

    }

    public ACPClientHeartbeatReceiveMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);
    }

    @Override
    public void setUp() {
        super.setUp();
        setACPMessage(getGson().fromJson(getRequestData(), ACPHeartbeatReceiveMessage.class));
    }

    @Override
    public String getDefaultRequestData() {
        return ACPHeartbeatReceiveMessage.getDefaultRequestData();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean returnMessageRequired() {
        return true;
    }
}
