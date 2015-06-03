package com.hcwins.vehicle.ta.evs.apitest.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterpriseAdmin;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterpriseAdminCredential;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.*;
import com.hcwins.vehicle.ta.evs.apiobj.trip.GetVehicles;
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

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;

/**
 * Created by wenji on 08/05/15.
 */
public class CancelAdminAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(CancelAdminAT.class);
    private EnterpriseAdminData adminData0, adminData1;
    private EnterpriseRegionData regionData0, regionData1;
    private EnterpriseData enterpriseData0, enterpriseData1;

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        enterpriseData0 = getDataSet().getEnterprises().get(0);
        regionData0 = getDataSet().getEnterpriseRegions().get(0);
        adminData0 = getDataSet().getEnterpriseAdmins().get(0);

        enterpriseData1 = getDataSet().getEnterprises().get(1);
        regionData1 = getDataSet().getEnterpriseRegions().get(1);
        adminData1 = getDataSet().getEnterpriseAdmins().get(1);

        EnterpriseAdminData.cleanRegistEnv(adminData0.getMobile(), adminData0.getEmail());

        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(adminData0.getMobile(), CaptchaRegist.postAndGetCaptchas(adminData0.getMobile()).get(0).getCaptcha());
        Regist.postRegistRequest(enterpriseData0, regionData0, adminData0);
        LoginResponse lgResponse0=Login.postLoginRepuest(adminData0.getMobile(), adminData0.getPassword(), true);

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

    @Test(description = "验证企业管理员忘记我成功")
    public void testCancelAdminSuccess() {
        List<EVSEnterpriseAdmin> enterpriseAdmin = EVSEnterpriseAdmin.dao.findEnterpriseAdminByMobile(adminData0.getMobile());
        long enterpriseAdminId = enterpriseAdmin.get(0).getId();
        CancelAdminResponse cancelAdminResponse = CancelAdmin.postCancelAdminRequest(adminData0.getMobile(), adminData0.getPassword());
        assertThat(cancelAdminResponse.getResult().getCode(), equalTo(0));
        assertThat(EVSEnterpriseAdminCredential.dao.countEnterpriseAdminCredentialByEnterpriseAdminId(enterpriseAdminId), equalTo(0));
    }

    @DataProvider
    public static Object[][] genVerifyCancelAdminErrorCodeTestData() {
        return new Object[][]{
                {"", "polly123", 203} // 手机号码为空
                , {" ", "polly123", 203} // 手机号码为空
                , {"1588110000a", "polly123", 205} // 手机号码格式错误
                , {"1588110000", "polly123", 205} // 手机号码格式错误
                , {"1588110000 ", "polly123", 205} // 手机号码格式错误
                , {"158811000001", "polly123", 205} // 手机号码格式错误
                , {"15881100002", "123456", 206} // 手机号码与密码不匹配
                , {"13648087441", "polly123", 207} // 手机号未注册
                , {"15881100002", "", 210} // 密码为空
                , {"15881100002", "12345", 217} // 密码长度不足
                , {"15881100002", "12345673432243524354353454544363463565656556565", 227} // 密码长度超长
        };
    }

    @Test(description = "验证忘记我的相关ErrorCode",
            dataProvider = "genVerifyCancelAdminErrorCodeTestData")
    public void testVerifyCancelAdminErrorCode(String mobile, String password, int code) {
        CancelAdminResponse cancelAdminResponse = CancelAdmin.postCancelAdminRequest(mobile, password);
        assertThat(cancelAdminResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "验证企业管理员忘记我失败当账户未登录")
    public void testCancelAdminFailedWhenUserNotLogin() {
        EnterpriseAdminData.cleanRegistEnv("13648087442", "wenji084@163.com");
        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest("13648087442", CaptchaRegist.postAndGetCaptchas("13648087442").get(0).getCaptcha());
        Regist.postRegistRequest(enterpriseData1.getEnterpriseName(), enterpriseData1.getWebsite(), regionData1.getCityId(), adminData1.getRealName(), "13648087442", "wenji084@163.com", adminData1.getPassword(), regionData1.getProvinceId());
        List<EVSEnterpriseAdmin> enterpriseAdmin = EVSEnterpriseAdmin.dao.findEnterpriseAdminByMobile("13648087442");
        long enterpriseAdminId = enterpriseAdmin.get(0).getId();

        CancelAdminResponse cancelAdminResponse = CancelAdmin.postCancelAdminRequest("13648087442", adminData1.getPassword(), null);
        assertThat(cancelAdminResponse.getResult().getCode(), equalTo(219));
        assertThat(EVSEnterpriseAdminCredential.dao.countEnterpriseAdminCredentialByEnterpriseAdminId(enterpriseAdminId), greaterThan(0));
    }
}

