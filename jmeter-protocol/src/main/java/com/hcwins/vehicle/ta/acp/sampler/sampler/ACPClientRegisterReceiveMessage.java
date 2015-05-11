package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPRegisterReceiveMessage;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

/**
 * Created by xiangzhai on 07/05/15.
 */
public class ACPClientRegisterReceiveMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    public ACPClientRegisterReceiveMessage() {
        //
    }

    public ACPClientRegisterReceiveMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);

        //
    }

    @Override
    public String getDefaultRequestData() {
        return ACPRegisterReceiveMessage.getDefaultRequestData();
    }

    @Override
    public void setUp() {
        super.setUp();
        setACPMessage(getGson().fromJson(getRequestData(), ACPRegisterReceiveMessage.class));
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
    public String handleReturnMessage(byte[] msg) throws ACPException {
        return "NOK - this is a dummy method, need to handle ack message for registerMessage";
    }
}
