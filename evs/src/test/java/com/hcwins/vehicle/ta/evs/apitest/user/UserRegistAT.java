package com.hcwins.vehicle.ta.evs.apitest.user;

import com.hcwins.vehicle.ta.evs.apidao.EVSSubscriber;
import com.hcwins.vehicle.ta.evs.apiobj.user.Regist;
import com.hcwins.vehicle.ta.evs.apiobj.user.RegistResponse;
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
public class UserRegistAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(UserRegistAT.class);
    private String mobile10, mobile11, password10, password11;
    private SubscriberData subscriberData0, subscriberData1;


    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        subscriberData0 = getDataSet().getSubscriberData().get(0);
        subscriberData1 = getDataSet().getSubscriberData().get(1);
        mobile10 = subscriberData0.getMobile();
        mobile11 =  subscriberData1.getMobile();
        password10 =  subscriberData0.getPassword();
        password11 =  subscriberData1.getPassword();

        SubscriberData.cleanRegistEnv(mobile10, password10);
        SubscriberData.cleanRegistEnv(mobile11, password11);
        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "个人注册接口测试: 验证全部参数正确时注册成功")
    public void testUserRegistSuccess() {
        RegistResponse registResponse = Regist.postRegistRequest(mobile10, password10);
        assertThat(registResponse.getResult().getCode(), equalTo(0));
        assertThat(registResponse.getStatus(), equalTo(0));
        EVSSubscriber subscriber1 = EVSSubscriber.dao.findSubscriberByMobile(mobile10).get(0);
        System.out.println(subscriber1.toString());
        assertThat(subscriber1.getStatus(), equalTo(EVSSubscriber.SubscriberStatus.UNAUDITED));
    }

    @DataProvider
    public static Object[][] genUserRegistErrorCodeTestData() {
        return new Object[][]{
                {"", "polly123", 203} // 手机号码为空
                , {" ", "polly123", 203} // 手机号码为空
                , {"1588110000a", "polly123", 205} // 手机号码格式错误
                , {"1588110000", "polly123", 205} // 手机号码格式错误
                , {"1588110000 ", "polly123", 205} // 手机号码格式错误
                , {"158811000001", "polly123", 205} // 手机号码格式错误
                , {"15881100002", "", 210} // 密码为空
                , {"15881100002", "12345", 217} // 密码长度不足
                , {"15881100002", "12345673432243524354353454544363463565656556565", 227} // 密码长度超长
        };
    }

    @Test(description = "个人注册接口测试: 验证企业注册的错误码",
            dataProvider = "genUserRegistErrorCodeTestData")
    public void testUserRegistErrorCode( String mobile, String password, int code) {
        RegistResponse registResponse = Regist.postRegistRequest(mobile, password);
        assertThat(registResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "个人注册接口测试: 验证管理员手机号已注册",
            dependsOnMethods = {"testUserRegistSuccess"})
    public void testUserMobileHaveRegisted() {
        RegistResponse registResponse = Regist.postRegistRequest(mobile10, password10);
        assertThat(registResponse.getResult().getCode(), equalTo(204));
    }

}
