package com.hcwins.vehicle.ta.evs.apitest;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.*;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.*;
import org.hamcrest.Matchers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

/**
 * Created by tommy on 3/20/15.
 */
public class EnterpriseRegistAT extends EVSTestBase {
    private static final Logger logger = LoggerFactory.getLogger(EnterpriseRegistAT.class);

    private String enterpriseName0, enterpriseName1;
    private String enterprisewebsite0, enterprisewebsite1;
    private String adminRealName0, adminRealName1;
    private String mobile0, mobile1;
    private String email0, email1;
    private String password0, password1;
    private String provinceName0,getProvinceName1;
    private Long provinceId0, provinceId1;
    private Long cityId0, cityId1;
    private String cityName0, cityName1;
    private Map<String, String> head, head0;
    @BeforeClass
    public void beforeClass() {
        super.beforeClass();

        enterpriseName0 = getDataSet().getEnterprises().get(0).getEnterpriseName();
        enterprisewebsite0 = getDataSet().getEnterprises().get(0).getEnterpriseWebsite();
        adminRealName0 = getDataSet().getEnterpriseAdmins().get(0).getRealName();
        mobile0 = getDataSet().getEnterpriseAdmins().get(0).getMobile();
        email0 = getDataSet().getEnterpriseAdmins().get(0).getEmail();
        password0 = getDataSet().getEnterpriseAdmins().get(0).getPassword();
        provinceName0 = getDataSet().getEnterpriseRegions().get(0).getProvinceName();
        provinceId0 = EVSProvince.dao.getProvinceIdByName(provinceName0).get(0).getId();
        cityName0 = getDataSet().getEnterpriseRegions().get(0).getCityName();
        cityId0 = EVSCity.dao.findCityIdByName(cityName0).get(0).getId();

        enterpriseName1 = getDataSet().getEnterprises().get(1).getEnterpriseName();
        enterprisewebsite1 = getDataSet().getEnterprises().get(1).getEnterpriseWebsite();
        adminRealName1 = getDataSet().getEnterpriseAdmins().get(1).getRealName();
        mobile1 = getDataSet().getEnterpriseAdmins().get(1).getMobile();
        email1 = getDataSet().getEnterpriseAdmins().get(1).getEmail();
        password1 = getDataSet().getEnterpriseAdmins().get(1).getPassword();
        getProvinceName1 = getDataSet().getEnterpriseRegions().get(1).getProvinceName();
        provinceId1 = EVSProvince.dao.getProvinceIdByName(getProvinceName1).get(0).getId();
        cityName1 = getDataSet().getEnterpriseRegions().get(1).getCityName();
        cityId1 = EVSCity.dao.findCityIdByName(cityName1).get(0).getId();

        //TODO:clean the env of last
        String timestamp = Long.toString(new Date().getTime());
        String newemail = email0 + timestamp;
        String newmobile = newemail.substring(newemail.length()-11,newemail.length());
        EVSEnterpriseAdmin.dao.updateEmailByMobile(mobile0,newemail);
        EVSEnterpriseAdmin.dao.updateMobileByEmail(newemail, newmobile);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(email0,newemail);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(mobile0,newmobile);

        //setup
        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile0, CaptchaRegist.postAndGetCaptchas(mobile0).get(0).getCaptcha());
        Regist.postRegistRequest(enterpriseName0, enterprisewebsite0, cityId0, adminRealName0, mobile0, email0, password0, provinceId0);
        LoginResponse lgResponse=Login.postLoginRepuest(mobile0, password0, true);
        String token = "TOKEN=" + lgResponse.getToken();
        System.out.println(token);
        head = new HashMap<String, String>();
        head.put("Cookie",token);

        EVSEnterpriseAdmin.dao.updatStatusBymobile(EVSEnterpriseAdmin.Status.BINDED, mobile0);
        logger.debug("&&&&&&&&&&&&&&&&&&&&&&&&&");
        logger.debug("$$$$$$$$$$$$$$$$$$$$$$$$$"+EVSEnterpriseAdmin.dao.findEnterpriseIdByMobile(mobile0).size());
        Long enterpriseId = EVSEnterpriseAdmin.dao.findEnterpriseIdByMobile(mobile0).get(0).getEnterpriseId();
        EVSEnterprise.dao.updateEnterpriseStatusById(EVSEnterprise.Status.AVAILABLE, enterpriseId);
    }

    @AfterClass
    public void afterClass() {
        super.afterClass();
    }

    @Test(description = "验证成功获取验证码")
    public void testCaptchaRegistSuccess() {
        //TODO: change to get timestamp from server
        Long startTime = new Date().getTime();
        EVSUtil.sleep("slow down the execution for different create time", 5);

        CaptchaRegistResponse captchaRegistResponse = CaptchaRegist.postCaptchaRegistRequest(mobile0);
        assertThat(captchaRegistResponse.getResult().getCode(), equalTo(0));

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
        assertThat(captchaRegistResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "企业注册接口测试: 验证全部参数正确时注册成功",
            dependsOnMethods = {"testEnterpriseRegistErrorCode"}, alwaysRun = true)
    public void testEnterpriseRegistSuccess() {
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseName0, enterprisewebsite0, cityId0, adminRealName0, mobile0, email0, password0, provinceId0);
        assertThat(registResponse.getResult().getCode(), equalTo(0));
        assertThat(registResponse.getEnterpriseStatus(), equalTo(0));
        EVSEnterprise enterprise1 = EVSEnterprise.dao.findEnterpriseByName(enterpriseName0).get(0);
        assertThat(enterprise1.getEnterpriseName(), equalTo(enterpriseName0));
        assertThat(enterprise1.getWebsite(), equalTo(enterprisewebsite0));
        assertThat(enterprise1.getCityId(), equalTo(cityId0));
        assertThat(enterprise1.getStatus(), equalTo(EVSEnterprise.Status.UNAUDITED));
    }

    @DataProvider
    public static Object[][] genEnterpriseRegistErrorCodeTestData() {
        return new Object[][]{
                {"", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 401}        //企业名称为空
                , {"try", "www.hcwins.cn", null, "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 500}                   //城市为空
                , {"try", "www.hcwins.cn", Long.valueOf(987654), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 503}   //城市不存在
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", null, 501}                   //省份为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(987654), 502}   //省份不存在
                , {"try", "www.hcwins.cn", Long.valueOf(140100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 504}   //此省份下没有此城市
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "1588110000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 205}    //手机号格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "158811000001", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 205}  //手机号格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "1588110000a", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 205}   //手机号格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "", Long.valueOf(510000), 210}         //密码为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 213}            //管理员姓名为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han&hcwins*bom", "123456", Long.valueOf(510000), 215}   //管理员邮件格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", " ", "123456", Long.valueOf(510000), 215}                       //管理员邮件格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "01", Long.valueOf(510000), 217}       //密码长度不足
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", Long.valueOf(510000), 227}   //密码长度超长
                //,{" ", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "meimei.han@hcwins.com", "123456", Long.valueOf(510000), 401}     //企业名称为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "", "123456", Long.valueOf(510000), 212}                         //管理员邮件为空
        };
    }

    @Test(description = "企业注册接口测试: 验证企业注册的错误码",
            dataProvider = "genEnterpriseRegistErrorCodeTestData")
    public void testEnterpriseRegistErrorCode(String enterpriseName, String enterprisewebsite, Long cityId, String adminRealName, String mobile, String email, String password, Long provinceId, int code) {
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseName, enterprisewebsite, cityId, adminRealName, mobile, email, password, provinceId);
        assertThat(registResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "企业注册接口测试: 验证管理员手机号已注册",
            dependsOnMethods = {"testEnterpriseRegistSuccess"})
    public void testAdminMobileHaveRegisted() {
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseName1, enterprisewebsite1, cityId1, adminRealName1, mobile0, email1, password1, provinceId1);
        assertThat(registResponse.getResult().getCode(), equalTo(204));
    }

    @Test(description = "企业注册接口测试: 验证管理员邮件已注册",
            dependsOnMethods = {"testAdminMobileHaveRegisted"})
    public void testAdminMailHaveRegisted() {
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseName1, enterprisewebsite1, cityId1, adminRealName1, mobile1, email0, password1, provinceId1);
        assertThat(registResponse.getResult().getCode(), equalTo(214));
    }

    /**
     * Verify mobile and catcha.
     */
    @Test(description = "验证手机号与验证码校验成功")
    public void testVerifyMobileAndCaptchaSuccess() {
        Long startTime = new Date().getTime();
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);

        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile0, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(0));
    }

    @Test(description = "验证手机号与验证码校验的手机号码相关ErrorCode",
            dataProvider = "genCaptchaRegistErrorCodeTestData")
    public void testVerifyMobileAndCaptchaErrorCodeWhenMobileInvalid(String mobile, int code) {
        EVSUtil.sleep("slow down the execution for different create time", 5);
        EVSCaptcha captcha = CaptchaRegist.postAndGetCaptchas(mobile0).get(0);
        String StrCaptcha = captcha.getCaptcha();
        VerifyMobileAndCaptchaResponse VerifyMobileAndCaptchaResponse = VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile, StrCaptcha);
        assertThat(VerifyMobileAndCaptchaResponse.getResult().getCode(), equalTo(code));
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
    /**
     * Cancel Admin Api tests:
     */

    @Test(description = "验证企业管理员忘记我成功")
    public void testCancelAdminSuccess() {
        List<EVSEnterpriseAdmin> enterpriseAdmin = EVSEnterpriseAdmin.dao.findEnterpriseAdminByMobile(mobile0);
        long enterpriseAdminId = enterpriseAdmin.get(0).getId();
        CancelAdminResponse cancelAdminResponse = CancelAdmin.postCancelAdminRequest(mobile0, password0, head);
        assertThat(cancelAdminResponse.getResult().getCode(), equalTo(0));
        assertThat(EVSEnterpriseAdminCredential.dao.countEnterpriseAdminCredentialByEnterpriseAdminId(enterpriseAdminId), equalTo(0));
    }

    @DataProvider
    public static Object[][] genVerifyCancelAdminErrorCodeTestData() {
        return new Object[][]{
                {"", "123456", 203} // 手机号码为空
                , {" ","123456", 205} // 手机号码为空
                , {"1588110000a","123456", 205} // 手机号码格式错误
                , {"1588110000", "123456", 205} // 手机号码格式错误
                , {"1588110000 ","123456", 205} // 手机号码格式错误
                , {"158811000001","123456", 205} // 手机号码格式错误
                , {"15881100000","654321", 206} // 手机号码与密码不匹配
                , {"13648087441","123456", 207} // 手机号未注册
                , {"15881100000","", 210} // 密码为空
                , {"15881100000","12345", 217} // 密码长度不足
                , {"15881100000","12345673432243524354353454544363463565656556565", 227} // 密码长度超长
        };
    }

   @Test(description = "验证忘记我的相关ErrorCode",
            dataProvider = "genVerifyCancelAdminErrorCodeTestData")
    public void testVerifyCancelAdminErrorCode(String mobile, String passord, int code) {
       Login.postLoginRepuest("15283837023", "123456", true);
       CancelAdminResponse cancelAdminResponse = CancelAdmin.postCancelAdminRequest(mobile, passord,head);
       assertThat(cancelAdminResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "验证企业管理员忘记我失败当账户未登录")
    public void testCancelAdminFailedWhenUserNotLogin() {
        List<EVSEnterpriseAdmin> enterpriseAdmin = EVSEnterpriseAdmin.dao.findEnterpriseAdminByMobile(mobile0);
        long enterpriseAdminId = enterpriseAdmin.get(0).getId();
        CancelAdminResponse cancelAdminResponse = CancelAdmin.postCancelAdminRequest(mobile0, password0, null);
        assertThat(cancelAdminResponse.getResult().getCode(), equalTo(219));
        assertThat(EVSEnterpriseAdminCredential.dao.countEnterpriseAdminCredentialByEnterpriseAdminId(enterpriseAdminId), greaterThan(0));
    }

    /**
     * 企业管理员查看待审批的用户列表接口
     * getUnauditUsers Api Tests:
     */

    @Test(description = "验证企业管理员查看待审批的用户列表成功")
    public void testGetUnauditUsersSuccess() {
        int pageSize = 10;
        int pageNo = 1;
        GetUnauditUsersResponse getUnauditUsersResponse = GetUnauditUsers.postGetUnauditUsersRequest(pageSize,pageNo,head);
        assertThat(getUnauditUsersResponse.getResult().getCode(), equalTo(0));
        assertThat(getUnauditUsersResponse.getSubscribers().size(),equalTo(EVSSubscriber.dao.countSubscriberByStatus(EVSSubscriber.SubscriberStatus.UNAUDITED)));
        int[] status = new int[getUnauditUsersResponse.getSubscribers().size()];
        for (int i=0; i< getUnauditUsersResponse.getSubscribers().size()-1; i++){
            status[i] = getUnauditUsersResponse.getSubscribers().size();
            assertThat(status[i],equalTo(0));
        }
    }

    @Test(description = "验证企业当管理员不存在时查看待审批的用户列表失败")
    public void testGetUnauditUsersFailedWithNotExistAdmin() {
        int pageSize = 10;
        int pageNo = 1;
        head0 = new HashMap<String, String>();
        head0.put("Cookie","TOKEN=dacc16f618674b0ca9108de4a6e5b632");
        GetUnauditUsersResponse getUnauditUsersResponse = GetUnauditUsers.postGetUnauditUsersRequest(pageSize,pageNo,head0);
        assertThat(getUnauditUsersResponse.getResult().getCode(), equalTo(219));
    }

    @Test(description = "验证企业当管理员未登录时查看待审批的用户列表失败")
    public void testGetUnauditUsersFailedWhenNotLogin() {
        int pageSize = 10;
        int pageNo = 1;
        GetUnauditUsersResponse getUnauditUsersResponse = GetUnauditUsers.postGetUnauditUsersRequest(pageSize,pageNo,null);
        assertThat(getUnauditUsersResponse.getResult().getCode(), equalTo(219));
    }

    /**
     * 企业管理员查看待审批的用户详细信息接口
     * getUnauditUsers Api Tests:
     */

    @Test(description = "验证企业管理员查看待审批的用户详细信息成功")
    public void testGetUnauditUsersInfoSuccess() {
        Long subscriberId0 = EVSSubscriber.dao.findSubscriberByStatus("UNAUDITED").get(0).getId();
        GetUnauditUserInfoResponse getUnauditUserInfosResponse = GetUnauditUserInfo.postGetUnauditUserInfoRequest(subscriberId0, head);
        assertThat(getUnauditUserInfosResponse.getResult().getCode(), equalTo(0));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getSubscirberId(), equalTo(subscriberId0));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getRealName(), equalTo(EVSSubscriber.dao.findById(subscriberId0).getRealName()));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getMobile(), equalTo(EVSSubscriber.dao.findById(subscriberId0).getMobile()));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getStatus(), equalTo(0));
        assertThat(EVSSubscriber.dao.findById(subscriberId0).getStatus(),equalTo(EVSSubscriber.SubscriberStatus.UNAUDITED));
    }

    @Test(description = "验证未选择要查看的待审核用户时查看待审批的用户详细信息失败")
    public void testGetUnauditUsersInfoFailedWithMissSubscriberId() {
        GetUnauditUserInfoResponse getUnauditUserInfosResponse = GetUnauditUserInfo.postGetUnauditUserInfoRequest(null, head);
        assertThat(getUnauditUserInfosResponse.getResult().getCode(), equalTo(221));
    }

    @Test(description = "验证验证企业当管理员未登录时查看待审批的用户详细信息失败")
    public void testGetUnauditUsersInfoFailedWhenAdminNotLogin() {
        Long subscriberId0 = EVSSubscriber.dao.findSubscriberByStatus("UNAUDITED").get(0).getId();
        GetUnauditUserInfoResponse getUnauditUserInfosResponse = GetUnauditUserInfo.postGetUnauditUserInfoRequest(subscriberId0, null);
        assertThat(getUnauditUserInfosResponse.getResult().getCode(), equalTo(219));
    }




    
}
