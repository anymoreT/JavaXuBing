package com.hcwins.vehicle.ta.evs.apitest.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSCaptcha;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.CaptchaRegist;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.Regist;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.VerifyMobileAndCaptcha;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.VerifyMobileAndCaptchaResponse;
import com.hcwins.vehicle.ta.evs.apitest.EVSTestBase;
import com.hcwins.vehicle.ta.evs.data.EnterpriseAdminData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseRegionData;
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
 * Created by wenji on 08/05/15.
 */
public class VerifyMobileAndCaptchaAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(VerifyMobileAndCaptchaAT.class);
    private EnterpriseAdminData adminData0, adminData1;
    private EnterpriseRegionData regionData1;
    private EnterpriseData enterpriseData1;
    private String mobile0;

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        enterpriseData1 = getDataSet().getEnterprises().get(1);
        regionData1 = getDataSet().getEnterpriseRegions().get(1);
        adminData1 = getDataSet().getEnterpriseAdmins().get(1);
        adminData0 = getDataSet().getEnterpriseAdmins().get(0);
        mobile0 = adminData0.getMobile();
        EnterpriseAdminData.cleanRegistEnv(adminData1.getMobile(), adminData1.getEmail());
        EnterpriseAdminData.cleanRegistEnv(adminData0.getMobile(), adminData0.getEmail());
        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "验证手机号与验证码校验成功")
    public void testVerifyMobileAndCaptchaSuccess() {
        Long startTime = new Date().getTime();
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);

        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile0, StrCaptcha);
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
    @Test(description = "验证手机号与验证码校验的手机号码相关ErrorCode",
            dataProvider = "genVerifyCaptchaErrorCodeTestMobileData")
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileInvalid(String mobile, int code) {
        EVSUtil.sleep("slow down the execution for different create time", 5);
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "验证手机号与验证码当手机号已注册ErrorCode")
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileHaveBeenRegisted() {
        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(adminData1.getMobile(), CaptchaRegist.postAndGetCaptchas(adminData1.getMobile()).get(0).getCaptcha());
        Regist.postRegistRequest(enterpriseData1, regionData1, adminData1);
        Long startTime = new Date().getTime();
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);

        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(adminData1.getMobile(), StrCaptcha);
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

    @Test(description = "验证手机号与验证码校验的验证码相关ErrorCode",
            dataProvider = "genVerifyCaptchaErrorCodeTestData")
    public void testVerifyMobileAndCaptchaErrorCodeWhenCaptchaInvalid(String captcha, int code) {
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile0, captcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "验证手机号与验证码校验当验证码和手机号码不匹配")
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileAndCaptchaDoNotMatch() {
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        String mobile1 = "13648087441";
        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile1, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(301));
    }

    @Test(description = "验证手机号与验证码校验当验证码过期")
    public void testVerifyMobileAndCaptchaErrorCodeWhenCaptchaExpeired() {
        EVSCaptcha captcha0 = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        EVSCaptcha captcha1 = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        String StrCaptcha0 = captcha0.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile0, StrCaptcha0);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(301));
    }


}
