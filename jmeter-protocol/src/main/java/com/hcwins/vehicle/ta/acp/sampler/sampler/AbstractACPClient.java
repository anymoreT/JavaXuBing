package com.hcwins.vehicle.ta.acp.sampler.sampler;

import java.nio.charset.Charset;

public abstract class AbstractACPClient implements ACPClient {
    protected ACPSampler acpSampler;

    public AbstractACPClient() {
        //
    }

    public AbstractACPClient(ACPSampler acpSampler) {
        this.acpSampler = acpSampler;
    }

    @Override
    public void setUp() {
    }

    @Override
    public void tearDown() {
    }

    @Override
    public String getCharset() {
        return Charset.defaultCharset().name();
    }
}
