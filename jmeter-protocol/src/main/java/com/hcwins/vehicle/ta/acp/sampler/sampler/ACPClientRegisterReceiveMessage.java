package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.hcwins.vehicle.ta.acp.sampler.data.ACPRegisterReceiveMessage;
import org.apache.commons.io.IOUtils;
import org.apache.jmeter.samplers.SampleResult;
import org.apache.jorphan.logging.LoggingManager;
import org.apache.jorphan.util.JOrphanUtils;
import org.apache.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by xiangzhai on 07/05/15.
 */
public class ACPClientRegisterReceiveMessage extends AbstractACPClient {
    private static final Logger logger = LoggingManager.getLoggerForClass();
    private StringBuffer sb;

    public ACPClientRegisterReceiveMessage() {

    }

    public ACPClientRegisterReceiveMessage(ACPSampler acpSampler, String requestData) {
        super(acpSampler, requestData);
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
    public void write(OutputStream os, SampleResult sr) throws IOException {
        getACPMessage().generateMessage();
        sr.setSamplerData(getACPMessage().getCurrentMessageAsReadableString());
        if (logger.isDebugEnabled()) {
            logger.debug(getAcpSampler() + " WriteS: " + getACPMessage().getCurrentMessageAsReadableString());
        }
        os.write(getACPMessage().getCurrentMessageAsReadableString().getBytes());
        os.write("\n".getBytes());
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
            }
            IOUtils.closeQuietly(w);
            //TODO: parse the return message
            final String hexString = JOrphanUtils.baToHexString(w.toByteArray());
            if (logger.isDebugEnabled()) {
                logger.debug(getAcpSampler() + " ReadB: " + w.size() + "\n" + hexString);
                logger.debug(getAcpSampler() + " ReadS: " + w.size() + "\n" + hexString); //TODO: log readable message
            }
            return ""; //TODO: return readable message
        } catch (Exception e) {
            throw new ACPException("Fail to get return message for register message", e);
        }
    }
}
