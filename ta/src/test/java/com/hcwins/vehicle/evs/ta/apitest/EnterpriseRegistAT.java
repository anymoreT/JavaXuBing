package com.hcwins.vehicle.evs.ta.apitest;

import com.hcwins.vehicle.evs.ta.EVSUtil;
import com.hcwins.vehicle.evs.ta.apidao.EVSCaptcha;
import com.hcwins.vehicle.evs.ta.apiobj.enterprise.CaptchaRegist;
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
import static org.hamcrest.Matchers.*;

/**
 * Created by tommy on 3/20/15.
 */
public class EnterpriseRegistAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(EnterpriseRegistAT.class);

    String mobile0;

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        mobile0 = dataSet.enterpriseAdmins.get(0).mobile;
        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:
        super.afterClass();
    }

    @Test(description = "验证成功获取验证码")
    public void testCaptchaRegistSuccess() {
        //TODO: change to get timestamp from server
        Long startTime = new Date().getTime();
        EVSUtil.sleep("slow down the execution for different create time", 5);

        CaptchaRegistResponse captchaRegistResponse = CaptchaRegist.postCaptchaRegistRequest(mobile0);
        assertThat(captchaRegistResponse.result.code, equalTo(0));

        List<EVSCaptcha> captchas = CaptchaRegist.getCaptchaRegists(mobile0);
        assertThat(captchas.size(), equalTo(1));

        EVSCaptcha captcha = captchas.get(0);
        assertThat(captcha.getCreateTime().getTime(), greaterThanOrEqualTo(startTime));
        assertThat(captcha.getMaxInactiveInternal(), equalTo(EVSCaptcha.Conf_MaxInactiveInternal));
        assertThat(captcha.getCreateTime().getTime() + captcha.getMaxInactiveInternal(),
                lessThanOrEqualTo(new Date().getTime() + EVSCaptcha.Conf_MaxInactiveInternal));
    }

    @Test(description = "验证连续3次获取验证码，只有最后一次的有效，其余的为过期")
    public void testCaptchaRegistExistentNewBeSetToExpiredAfterGettingNewOne() {
        List<EVSCaptcha> captchas;
        EVSCaptcha latestCaptcha = null;
        for (int i = 0; i < 3; i++) {
            captchas = CaptchaRegist.postAndGetCaptchas(mobile0);
            assertThat(captchas.size(), equalTo(1));
            if (i == 0) {
                latestCaptcha = captchas.get(0);
            } else {
                assertThat(captchas.get(0).getId(), greaterThan(latestCaptcha.getId()));
                assertThat(EVSCaptcha.dao.findById(latestCaptcha.getId()).getStatus(), equalTo(EVSCaptcha.Status.EXPIRED));
            }
        }
    }

    @Test(description = "验证获取验证码时的数据更新mobile、module隔离")
    public void testCaptchaRegistExistentNewBeSetToExpiredAfterGettingNewOneIsMobileAndModuleIsolated() {
        EVSCaptcha captcha1 = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        assertThat(captcha1.getStatus(), equalTo(EVSCaptcha.Status.NEW));
        captcha1.setModule(EVSCaptcha.Module.USER_REGIST);
        assertThat(EVSCaptcha.dao.updateModule(captcha1), equalTo(1));
        captcha1 = EVSCaptcha.dao.findById(captcha1.getId());
        assertThat(captcha1.getModule(), equalTo(EVSCaptcha.Module.USER_REGIST));

        EVSCaptcha captcha2 = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        assertThat(captcha2.getStatus(), equalTo(EVSCaptcha.Status.NEW));
        captcha2.setStatus(EVSCaptcha.Status.VERIFIED);
        assertThat(EVSCaptcha.dao.updateStatus(captcha2), equalTo(1));
        captcha2 = EVSCaptcha.dao.findById(captcha2.getId());
        assertThat(captcha2.getStatus(), equalTo(EVSCaptcha.Status.VERIFIED));

        EVSCaptcha captcha3 = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        assertThat(captcha3.getStatus(), equalTo(EVSCaptcha.Status.NEW));

        captcha1 = EVSCaptcha.dao.findById(captcha1.getId());
        assertThat(captcha1.getStatus(), equalTo(EVSCaptcha.Status.NEW));

        captcha2 = EVSCaptcha.dao.findById(captcha2.getId());
        assertThat(captcha2.getStatus(), equalTo(EVSCaptcha.Status.VERIFIED));
    }

    @DataProvider
    public static Object[][] genCaptchaRegistErrorCodeTestData() {
        return new Object[][]{
                {"", 203} // 手机号码为空
                //TODO: 204 need a registered mobile // 手机号码已注册
                , {" ", 205} // 手机号码为空
                , {"1588110000a", 205} // 手机号码格式错误
                , {"1588110000", 205} // 手机号码格式错误
                , {"1588110000 ", 205} // 手机号码格式错误
                , {"158811000001", 205} // 手机号码格式错误
        };
    }

    @Test(description = "验证获取验证码的ErrorCode",
            dataProvider = "genCaptchaRegistErrorCodeTestData")
    public void testCaptchaRegistErrorCode(String mobile, int code) {
        CaptchaRegistResponse captchaRegistResponse = CaptchaRegist.postCaptchaRegistRequest(mobile);
        assertThat(captchaRegistResponse.result.code, equalTo(code));
    }
}
