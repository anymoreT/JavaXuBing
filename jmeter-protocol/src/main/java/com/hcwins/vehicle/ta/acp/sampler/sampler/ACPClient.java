package com.hcwins.vehicle.ta.acp.sampler.sampler;

import org.apache.jmeter.samplers.SampleResult;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ACPClient {
    public String getDefaultRequestData();

    public void setUp();

    public void tearDown();

    public void write(OutputStream os, SampleResult sr) throws IOException;

    public String read(InputStream is) throws ACPException, IOException;

    public String getCharset();
}
