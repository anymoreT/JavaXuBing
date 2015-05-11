package com.hcwins.vehicle.ta.acp.sampler.sampler;

import org.apache.commons.io.IOUtils;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
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
    public String read(InputStream is) throws IOException, ACPException {
        ByteArrayOutputStream w = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int x = 0;
        do {
            x = is.read(buffer);
            w.write(buffer, 0, x);
        } while (is.available() > 0);
        IOUtils.closeQuietly(w);

        String returnMessage = w.toString();
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(getAcpSampler() + " ReadS: " + returnMessage);
        }
        return returnMessage;
    }
}
