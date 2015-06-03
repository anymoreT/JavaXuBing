package com.hcwins.vehicle.ta.evs.apitest.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.enterprise.*;
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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by wenji on 22/05/15.
 */
public class LoginAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(LoginAT.class);
    private static EnterpriseAdminData adminData0;
    private EnterpriseAdminData adminData1;
    private EnterpriseRegionData regionData0, regionData1;
    private EnterpriseData enterpriseData0, enterpriseData1;


    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        enterpriseData0 = getDataSet().getEnterprises().get(0);
        regionData0 = getDataSet().getEnterpriseRegions().get(0);
        adminData0 = getDataSet().getEnterpriseAdmins().get(0);

        EnterpriseAdminData.cleanRegistEnv(adminData0.getMobile(), adminData0.getEmail());
        EnterpriseData.cleanEnterpriseEnv(enterpriseData0.getEnterpriseName());
        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(adminData0.getMobile(), CaptchaRegist.postAndGetCaptchas(adminData0.getMobile()).get(0).getCaptcha());
        Regist.postRegistRequest(enterpriseData0, regionData0, adminData0);

        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "企业管理员登录接口: 验证全部参数正确时登录成功")
    public void testEnterpriseLoginSuccess() {
        LoginResponse loginResponse = Login.postLoginRepuest(adminData0.getMobile(), adminData0.getPassword());
        assertThat(loginResponse.getResult().getCode(), equalTo(0));
    }

    @DataProvider
    public static Object[][] genEnterpriseLoginFiledErrorCodeData() {
        return new Object[][]{
                {"13648087443","123456" ,201} // 账号不存在
                , {"136480874QA","123456" ,201} // 账号不存在
                , {"1364808744","123456" ,201} // 账号不存在
                , {"136480874410","123456" ,201} // 账号不存在
                , {adminData0.getMobile(),"123432" ,206} // 账号与密码不匹配
                , {"","123456" ,209} // 登录账号为空
                , {" ","123456" ,201} // 账号不存在
                , {"15881100000","" ,210} // 登录密码为空
                , {"15881100000","  " ,206} // 账号与密码不匹配
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
