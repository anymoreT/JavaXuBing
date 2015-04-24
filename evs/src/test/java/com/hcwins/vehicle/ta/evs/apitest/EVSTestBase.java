package com.hcwins.vehicle.ta.evs.apitest;

import com.hcwins.vehicle.ta.evs.APISet;
import com.hcwins.vehicle.ta.evs.DataSet;
import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.TestBed;
import org.skife.jdbi.v2.Handle;
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
    private static final Logger logger = LoggerFactory.getLogger(EVSTestBase.class);

    private TestBed testBed;
    private APISet apiSet;
    private DataSet dataSet;
    private Handle handle;

    public TestBed getTestBed() {
        return testBed;
    }

    public void setTestBed(TestBed testBed) {
        this.testBed = testBed;
    }

    public APISet getApiSet() {
        return apiSet;
    }

    public void setApiSet(APISet apiSet) {
        this.apiSet = apiSet;
    }

    public DataSet getDataSet() {
        return dataSet;
    }

    public void setDataSet(DataSet dataSet) {
        this.dataSet = dataSet;
    }

    public Handle getHandle() {
        return handle;
    }

    public void setHandle(Handle handle) {
        this.handle = handle;
    }

    @BeforeSuite
    public void beforeSuite() {
        logger.debug("before suite");
        testBed = EVSUtil.getTestBed();
        apiSet = EVSUtil.getAPISet();
        dataSet = EVSUtil.getDataSet();
        handle = EVSUtil.getDBHandle();
    }

    @AfterSuite
    public void afterSuite() {
        try {
            handle.close();
        } catch (Exception e) {
            logger.error("failed to close db connection");
        }
        logger.debug("after suite");
    }

    @BeforeClass
    public void beforeClass() {
        logger.debug("before class");

        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        logger.debug("after class");
    }
}
