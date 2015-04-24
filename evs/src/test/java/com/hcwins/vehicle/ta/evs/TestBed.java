package com.hcwins.vehicle.ta.evs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by tommy on 3/23/15.
 */
public class TestBed {
    private static final Logger logger = LoggerFactory.getLogger(TestBed.class);

    private String name;
    private String host;
    private String apiBaseUrl;
    private String jdbcConnectionString;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getApiBaseUrl() {
        return apiBaseUrl;
    }

    public void setApiBaseUrl(String apiBaseUrl) {
        this.apiBaseUrl = apiBaseUrl;
    }

    public String getJdbcConnectionString() {
        return jdbcConnectionString;
    }

    public void setJdbcConnectionString(String jdbcConnectionString) {
        this.jdbcConnectionString = jdbcConnectionString;
    }

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
