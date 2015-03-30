package com.hcwins.vehicle.evs.ta;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tommy on 3/23/15.
 */
public class TestBed {
    static final Logger logger = LoggerFactory.getLogger(TestBed.class);

    public String name;
    public String host;
    public String apiBaseUrl;
    public String jdbcConnectionString;

    @Override
    public String toString() {
        return "TestBed{" +
                "name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", apiBaseUrl='" + apiBaseUrl + '\'' +
                ", jdbcConnectionString='" + jdbcConnectionString + '\'' +
                '}';
    }

    public boolean setUp() {
        logger.debug("trying to setup testbed");
        //TODO:
        return true;
    }

    public boolean tearDown() {
        logger.debug("trying to cleanup testbed");
        //TODO:
        return true;
    }
}
