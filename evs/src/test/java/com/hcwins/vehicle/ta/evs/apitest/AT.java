package com.hcwins.vehicle.ta.evs.apitest;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.Login;
import com.hcwins.vehicle.ta.evs.apiobj.trip.GetVehicles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * Created by tommy on 4/22/15.
 */
public class AT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(AT.class);

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();

        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test
    public void dummy() {
        Login.postLoginRepuest("15283837023", "123456", true);
        logger.debug(EVSUtil.getEnterpriseToken("15283837023"));
        logger.debug(EVSUtil.getCurrentEnterpriseToken());
        logger.debug(EVSUtil.getCurrentToken());
        GetVehicles.postGetVehiclesRequest();
    }
}
