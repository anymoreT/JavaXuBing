package com.hcwins.vehicle.ta.evs.apitest.user;

import com.hcwins.vehicle.ta.evs.apiobj.user.*;
import com.hcwins.vehicle.ta.evs.apitest.EVSTestBase;
import com.hcwins.vehicle.ta.evs.data.SubscriberData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by wenji on 22/05/15.
 */
public class UserLoginAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(UserLoginAT.class);
    private static SubscriberData subscriber0;

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        subscriber0 = getDataSet().getSubscriberData().get(0);

        SubscriberData.cleanRegistEnv(subscriber0.getMobile(), subscriber0.getEmail());
        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(subscriber0.getMobile(), CaptchaRegist.postAndGetCaptchas(subscriber0.getMobile()).get(0).getCaptcha());
        Regist.postRegistRequest(subscriber0.getMobile(), subscriber0.getPassword());

        //TODO: Add email for subscriber0
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "企业管理员登录接口: 验证用手机号码登录成功")
    public void testUserLoginSuccessByMobie() {
        LoginResponse loginResponse = Login.postLoginRepuest(subscriber0.getMobile(), subscriber0.getPassword());
        assertThat(loginResponse.getResult().getCode(), equalTo(0));
    }

    @Test(description = "企业管理员登录接口: 验证用邮箱登录成功")
    public void testUserLoginSuccessByEmail() {
        LoginResponse loginResponse = Login.postLoginRepuest(subscriber0.getEmail(), subscriber0.getPassword());
        assertThat(loginResponse.getResult().getCode(), equalTo(0));
    }

    @DataProvider
    public static Object[][] genEnterpriseLoginFiledErrorCodeData() {
        return new Object[][]{
                {"13648087443","123456" ,201} // 账号不存在
                , {"136480874QA","123456" ,201} // 账号不存在
                , {"1364808744","123456" ,201} // 账号不存在
                , {"136480874410","123456" ,201} // 账号不存在
                , {subscriber0.getMobile(),"123432" ,206} // 账号与密码不匹配
                , {subscriber0.getEmail(),"123432" ,206} // 账号与密码不匹配
                , {"","123456" ,209} // 登录账号为空
                , {" ","123456" ,209} // 账号不存在
                , {subscriber0.getMobile(),"" ,210} // 登录密码为空
                , {subscriber0.getMobile(),"  " ,206} // 账号与密码不匹配
                , {subscriber0.getEmail(),"" ,210} // 登录密码为空
                , {subscriber0.getEmail(),"  " ,206} // 账号与密码不匹配
                //, {"158811000001","123456" ,211} // TODO: 账号被锁定
        };
    }

    @Test(description = "企业管理员登录接口: 验证登录失败ErrorCode",
            dataProvider = "genEnterpriseLoginFiledErrorCodeData")
    public void testEnterpriseLoginFiledErrorCode(String account, String password, int code) {
        LoginResponse loginResponse = Login.postLoginRepuest(account, password);
        assertThat(loginResponse.getResult().getCode(), equalTo(code));
    }
















}
