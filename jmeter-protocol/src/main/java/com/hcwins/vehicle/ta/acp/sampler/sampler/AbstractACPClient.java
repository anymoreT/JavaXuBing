package com.hcwins.vehicle.ta.acp.sampler.sampler;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hcwins.vehicle.ta.acp.sampler.data.AbstractACPMessage;
import org.apache.commons.io.IOUtils;
import org.apache.log.Logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Modifier;
import java.nio.charset.Charset;

public abstract class AbstractACPClient implements ACPClient {
    private ACPSampler acpSampler;
    private String requestData;

    private Gson gson;

    private AbstractACPMessage message;

    public AbstractACPClient() {
        //
    }

    public AbstractACPClient(ACPSampler acpSampler, String requestData) {
        this.acpSampler = acpSampler;
        this.requestData = requestData;

        this.gson = new GsonBuilder().excludeFieldsWithModifiers(Modifier.PROTECTED).create();
    }

    public ACPSampler getAcpSampler() {
        return acpSampler;
    }

    public void setAcpSampler(ACPSampler acpSampler) {
        this.acpSampler = acpSampler;
    }

    public String getRequestData() {
        return requestData;
    }

    public void setRequestData(String requestData) {
        this.requestData = requestData;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    public AbstractACPMessage getACPMessage() {
        return message;
    }

    public void setACPMessage(AbstractACPMessage message) {
        this.message = message;
    }

    @Override
    public void setUp() {
    }

    @Override
    public void tearDown() {
    }

    public abstract Logger getLogger();

    @Override
    public String write(OutputStream os) throws IOException {
        getACPMessage().generateMessage();
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(getAcpSampler() + " WriteS: " + getACPMessage().getCurrentMessageAsReadableString());
        }
        os.write(getACPMessage().getCurrentMessage());
        os.flush();
        return getACPMessage().getCurrentMessageAsReadableString();
    }

    public abstract boolean returnMessageRequired();

    @Override
    public String read(InputStream is) throws IOException, ACPException {
        if (!returnMessageRequired()) {
            return "OK - return message is not required";
        }

        ByteArrayOutputStream w = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int x = 0;
        do {
            x = is.read(buffer);
            w.write(buffer, 0, x);
        } while (is.available() > 0);
        IOUtils.closeQuietly(w);

        String returnMessage = handleReturnMessage(w.toByteArray());
        if (getLogger().isDebugEnabled()) {
            getLogger().debug(getAcpSampler() + " ReadS: " + returnMessage);
        }
        return returnMessage;
    }

    public abstract String handleReturnMessage(byte[] msg) throws ACPException;

    @Override
    public String getCharset() {
        return Charset.defaultCharset().name();
    }
}
