package com.hcwins.vehicle.evs.ta.apitest;

import com.hcwins.vehicle.evs.ta.apidao.EVSCaptcha;
import com.hcwins.vehicle.evs.ta.apidao.EVSCaptchaDao;
import com.hcwins.vehicle.evs.ta.apiobj.enterprise.CaptchaRegistRequest;
import com.hcwins.vehicle.evs.ta.apiobj.enterprise.CaptchaRegistResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

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
        Long startTime = new Date().getTime();

        CaptchaRegistRequest captchaRegistRequest = new CaptchaRegistRequest();
        captchaRegistRequest.mobile = dataSet.enterpriseAdmins.get(0).mobile;
        captchaRegistRequest.post();
        CaptchaRegistResponse captchaRegistResponse = captchaRegistRequest.getLastResponseAsObj();
        assertThat(captchaRegistResponse.result.code, equalTo(0));

        EVSCaptchaDao dao = handle.attach(EVSCaptchaDao.class);
        List<EVSCaptcha> captchas = dao.findCaptchaByMobileModuleStatus(
                captchaRegistRequest.mobile,
                EVSCaptcha.Module.ENTEPRISE_REGISTER.name(),
                EVSCaptcha.Status.NEW.name());
        assertThat(captchas.size(), equalTo(1));

        EVSCaptcha captcha = captchas.get(0);
        assertThat(captcha.createTime.getTime(), greaterThanOrEqualTo(startTime));
        assertThat(captcha.maxInactiveInternal, equalTo(EVSCaptcha.Conf_MaxInactiveInternal));
        assertThat(captcha.createTime.getTime() + captcha.maxInactiveInternal,
                lessThanOrEqualTo(new Date().getTime() + EVSCaptcha.Conf_MaxInactiveInternal));
    }

    @Test
    public void testCaptchaIsSetToExpiredAfterCalling() {
        CaptchaRegistRequest captchaRegistRequest = new CaptchaRegistRequest();
        captchaRegistRequest.mobile = dataSet.enterpriseAdmins.get(0).mobile;
        captchaRegistRequest.post();

    }

    @DataProvider
    public static Object[][] genCaptchaRegistNTD() {
        return new Object[][]{
                {"", 203}
                // 204 need a registered mobile
                , {"1588110000a", 205}
                , {"a------", 205}
                , {"1", 205}
                , {"158811000001", 205}};
    }

    @Test(dataProvider = "genCaptchaRegistNTD")
    public void testCaptchaRegistErrorCode(String mobile, int code) {
        CaptchaRegistRequest captchaRegistRequest = new CaptchaRegistRequest();
        captchaRegistRequest.mobile = mobile;
        captchaRegistRequest.post();
        CaptchaRegistResponse captchaRegistResponse = captchaRegistRequest.getLastResponseAsObj();
        assertThat(captchaRegistResponse.result.code, equalTo(code));
    }
}
