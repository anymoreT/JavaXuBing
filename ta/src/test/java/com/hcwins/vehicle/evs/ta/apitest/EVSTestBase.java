package com.hcwins.vehicle.evs.ta.apitest;

import com.hcwins.vehicle.evs.ta.APISet;
import com.hcwins.vehicle.evs.ta.DataSet;
import com.hcwins.vehicle.evs.ta.EVSUtil;
import com.hcwins.vehicle.evs.ta.TestBed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

/**
 * Created by tommy on 3/23/15.
 */
public class EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(EVSTestBase.class);

    EVSUtil evsUtil;
    TestBed testBed;
    APISet apiSet;
    DataSet dataSet;

    @BeforeSuite
    public void beforeSuite() {
        logger.debug("before suite");

        evsUtil = EVSUtil.getInstance();
        testBed = evsUtil.getTestBed();
        apiSet = evsUtil.getAPISet();
        dataSet = evsUtil.getDataSet();
    }

    @AfterSuite
    public void afterSuite() {
        //TODO
        logger.debug("after suite");
    }

    @BeforeClass
    public void beforeClass() {
        logger.debug("before class");
        //TODO
    }

    @AfterClass
    public void afterClass() {
        //TODO
        logger.debug("after class");
    }
}
