package com.hcwins.vehicle.ta.acp.sampler.sampler;

import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.IOException;
import java.io.OutputStream;

public class ACPClientTestMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();

    private int index = 0;

    public ACPClientTestMessage() {
        //
    }

    public ACPClientTestMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);

        //
    }

    @Override
    public String getDefaultRequestData() {
        return "Send a auto increased integer which starts from 1";
    }

    @Override
    public void setUp() {
        super.setUp();
    }

    @Override
    public Logger getLogger() {
        return logger;
    }

    @Override
    public String write(OutputStream os) throws IOException {
        index++;
        String msg = String.valueOf(index + "\n");
        if (logger.isDebugEnabled()) {
            logger.debug(getAcpSampler() + " WriteS: " + msg);
        }
        os.write((msg).getBytes());
        os.flush();
        return msg;
    }

    @Override
    public boolean returnMessageRequired() {
        return true;
    }

    @Override
    public String handleReturnMessage(byte[] msg) throws ACPException {
        return new String(msg);
    }
}
