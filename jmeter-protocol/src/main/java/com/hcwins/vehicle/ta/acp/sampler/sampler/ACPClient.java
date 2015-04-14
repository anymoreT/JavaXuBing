package com.hcwins.vehicle.ta.acp.sampler.sampler;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface ACPClient {
    void setUp();

    void tearDown();

    void write(OutputStream os, String s) throws IOException;

    String read(InputStream is) throws ACPException;

    String getCharset();
}
