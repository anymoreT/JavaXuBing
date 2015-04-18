package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPLocationReceiveMessageData;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ACPClientLocationReceiveMessage extends AbstractACPClient {
    private static final Logger log = LoggingManager.getLoggerForClass();

    public ACPClientLocationReceiveMessage() {
        //
    }

    public ACPClientLocationReceiveMessage(ACPSampler acpSampler) {
        super(acpSampler);
    }

    @Override
    public String getDefaultRequestData() {
        return ACPLocationReceiveMessageData.getDefaultRequestData();
    }

    @Override
    public void write(OutputStream os, String s) throws IOException {
        if (log.isDebugEnabled()) {
            log.debug(acpSampler + " WriteS: " + s);
        }
        os.write(s.getBytes(getCharset()));
        os.flush();
    }

    @Override
    public String read(InputStream is) throws ACPException {
        ByteArrayOutputStream w = new ByteArrayOutputStream();
        try {
            byte[] buffer = new byte[4096];
            int x = 0;
            while ((x = is.read(buffer)) > -1) {
                w.write(buffer, 0, x);
                //TODO: Actually return message for GPS packet is not required, here is just to read until timeout since the test server will return some message
                break;
            }
            if (log.isDebugEnabled()) {
                log.debug(acpSampler + " ReadS: " + w.size() + " -> " + w.toString());
            }
            return w.toString();
        } catch (IOException ex) {
            throw new ACPException("Error reading from server, bytes read: " + w.size() + " -> " + w.toString(), ex);
        }
    }
}
