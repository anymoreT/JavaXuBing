package com.hcwins.vehicle.evs.ta.apitest;

import com.hcwins.vehicle.evs.ta.apiobj.enterprise.CaptchaRegistRequest;
import com.hcwins.vehicle.evs.ta.apiobj.enterprise.CaptchaRegistResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by tommy on 3/20/15.
 */
public class EnterpriseRegistAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(EnterpriseRegistAT.class);

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        //TODO
    }

    @AfterClass
    public void afterClass() {
        //TODO
        super.afterClass();
    }

    @Test
    public void testCaptchaRegistSuccess() {
        CaptchaRegistRequest captchaRegistRequest = new CaptchaRegistRequest();
        captchaRegistRequest.mobile = dataSet.enterpriseAdmins.get(0).mobile;
        captchaRegistRequest.post();
        CaptchaRegistResponse captchaRegistResponse = captchaRegistRequest.getLastResponseAsObj();
        assertThat(captchaRegistResponse.result.code, is(equalTo(0)));
    }

    @DataProvider
    public static Object[][] genCaptchaRegistNTD() {
        return new Object[][]{
                {"", 203}
                // 204 need a registered mobile
                , {"1588110000a", 205}};
    }

    @Test(dataProvider = "genCaptchaRegistNTD")
    public void testCaptchaRegistErrorCode(String mobile, int code) {
        CaptchaRegistRequest captchaRegistRequest = new CaptchaRegistRequest();
        captchaRegistRequest.mobile = mobile;
        captchaRegistRequest.post();
        CaptchaRegistResponse captchaRegistResponse = captchaRegistRequest.getLastResponseAsObj();
        assertThat(captchaRegistResponse.result.code, is(equalTo(code)));
    }
}
