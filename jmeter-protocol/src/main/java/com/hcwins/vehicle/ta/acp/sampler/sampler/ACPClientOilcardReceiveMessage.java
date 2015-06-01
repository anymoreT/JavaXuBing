package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPOilcardReceiveMessage;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created by xiangzhai on 29/05/15.
 */
public class ACPClientOilcardReceiveMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    public ACPClientOilcardReceiveMessage() {

    }

    public ACPClientOilcardReceiveMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);
    }

    @Override
    public String getDefaultRequestData() {
        return ACPOilcardReceiveMessage.getDefaultRequestData();
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
        setACPMessage(getGson().fromJson(getRequestData(), ACPOilcardReceiveMessage.class));
    }
}
