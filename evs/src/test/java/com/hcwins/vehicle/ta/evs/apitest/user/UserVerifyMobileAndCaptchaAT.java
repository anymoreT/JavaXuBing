package com.hcwins.vehicle.ta.evs.apitest.user;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSCaptcha;
import com.hcwins.vehicle.ta.evs.apiobj.user.CaptchaRegist;
import com.hcwins.vehicle.ta.evs.apiobj.user.Regist;
import com.hcwins.vehicle.ta.evs.apiobj.user.VerifyMobileAndCaptcha;
import com.hcwins.vehicle.ta.evs.apiobj.user.VerifyMobileAndCaptchaResponse;
import com.hcwins.vehicle.ta.evs.apitest.EVSTestBase;
import com.hcwins.vehicle.ta.evs.apitest.enterprise.CaptchaRegistAT;
import com.hcwins.vehicle.ta.evs.data.EnterpriseData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseRegionData;
import com.hcwins.vehicle.ta.evs.data.SubscriberData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by wenji on 22/05/15.
 */
public class UserVerifyMobileAndCaptchaAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(CaptchaRegistAT.class);
    private SubscriberData subscriberData0, subscriberData1;
    private EnterpriseRegionData regionData1;
    private EnterpriseData enterpriseData1;
    private String mobile10;

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        enterpriseData1 = getDataSet().getEnterprises().get(1);
        regionData1 = getDataSet().getEnterpriseRegions().get(1);
        subscriberData1 = getDataSet().getSubscriberData().get(1);
        subscriberData0 = getDataSet().getSubscriberData().get(0);
        mobile10 = subscriberData0.getMobile();
        SubscriberData.cleanRegistEnv(subscriberData0.getMobile(), subscriberData0.getEmail());
        SubscriberData.cleanRegistEnv(subscriberData1.getMobile(), subscriberData1.getEmail());
        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "个人手机号与验证码校验接口测试:验证手机号与验证码校验成功")
    public void testVerifyMobileAndCaptchaSuccess() {
        Long startTime = new Date().getTime();
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile10).get(0);

        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile10, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(0));
    }

    @DataProvider
    public static Object[][] genVerifyCaptchaErrorCodeTestMobileData() {
        return new Object[][]{
                {"", 203} // 手机号码为空
//                , {"15881100000", 204} // 手机号码已注册
                , {" ", 203} // 手机号码为空
                , {"1588110000a", 205} // 手机号码格式错误
                , {"1588110000", 205} // 手机号码格式错误
                , {"1588110000 ", 205} // 手机号码格式错误
                , {"158811000001", 205} // 手机号码格式错误
        };
    }
    @Test(description = "个人手机号与验证码校验接口测试:手机号码相关ErrorCode",
            dataProvider = "genVerifyCaptchaErrorCodeTestMobileData")
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileInvalid(String mobile, int code) {
        EVSUtil.sleep("slow down the execution for different create time", 5);
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile10).get(0);
        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "个人手机号与验证码校验接口测试:当手机号已注册ErrorCode")  // TODO:手机号已注册
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileHaveBeenRegisted() {
        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(subscriberData1.getMobile(), CaptchaRegist.postAndGetCaptchas(subscriberData1.getMobile()).get(0).getCaptcha());
        Regist.postRegistRequest(subscriberData1.getMobile(), subscriberData1.getPassword());
        Long startTime = new Date().getTime();
        String captcha = CaptchaRegist.postAndGetCaptchas(subscriberData1.getMobile()).get(0).getCaptcha();

        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(subscriberData1.getMobile(), captcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(204));
    }

    @DataProvider
    public static Object[][] genVerifyCaptchaErrorCodeTestData() {
        return new Object[][]{
                {"", 301} // 验证码为空
                , {" ", 301} // 验证码为空
                , {"12345a", 301} // 验证码格式错误
                , {"12345 ", 301} // 验证码格式错误
                , {"123456", 301} // 验证码错误
        };
    }

    @Test(description = "个人手机号与验证码校验接口测试:验证码相关ErrorCode",
            dataProvider = "genVerifyCaptchaErrorCodeTestData")
    public void testVerifyMobileAndCaptchaErrorCodeWhenCaptchaInvalid(String captcha, int code) {
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile10, captcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "个人手机号与验证码校验接口测试:验证码和手机号码不匹配")
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileAndCaptchaDoNotMatch() {
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile10).get(0);
        String mobile1 = "13648087441";
        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile1, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(301));
    }

    @Test(description = "个人手机号与验证码校验接口测试:验证码过期")
    public void testVerifyMobileAndCaptchaErrorCodeWhenCaptchaExpeired() {
        EVSCaptcha captcha0 = CaptchaRegist.postAndGetCaptchas(mobile10).get(0);
        EVSCaptcha captcha1 = CaptchaRegist.postAndGetCaptchas(mobile10).get(0);
        String StrCaptcha0 = captcha0.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile10, StrCaptcha0);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(301));
    }

}
