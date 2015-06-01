package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPTyrePressureReceiveMessage;
import com.hcwins.vehicle.ta.acp.sampler.data.AbstractACPMessage;
import org.apache.jmeter.samplers.Sampler;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created by xiangzhai on 29/05/15.
 */
public class ACPClientTyrePressureReceiveMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    public ACPClientTyrePressureReceiveMessage() {

    }

    public ACPClientTyrePressureReceiveMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);
    }

    @Override
    public String getDefaultRequestData() {
        return ACPTyrePressureReceiveMessage.getDefaultRequestData();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public boolean returnMessageRequired() {
        return true;
    }

    @Override
    public void setUp() {
        super.setUp();
        setACPMessage(getGson().fromJson(getRequestData(), ACPTyrePressureReceiveMessage.class));
    }
}
