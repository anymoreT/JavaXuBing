package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPLocationReceiveMessage;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ACPClientLocationReceiveMessage extends AbstractACPClient {
    private static final Logger log = LoggingManager.getLoggerForClass();

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
    public void write(OutputStream os, SampleResult sr) throws IOException {
        getACPMessage().generateMessage();
        sr.setSamplerData(getACPMessage().getCurrentMessageAsReadableString());
        if (log.isDebugEnabled()) {
            log.debug(getAcpSampler() + " WriteS: " + getACPMessage().getCurrentMessageAsReadableString());
        }
        os.write(getACPMessage().getCurrentMessage());
        os.flush();
    }

    @Override
    public String read(InputStream is) throws ACPException {
        return "OK - return message is not required for LocationReceiveMessage";
    }
}
