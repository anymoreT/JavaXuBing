package com.hcwins.vehicle.ta.evs.apitest.user;

import com.hcwins.vehicle.ta.evs.apidao.EVSCity;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterprise;
import com.hcwins.vehicle.ta.evs.apidao.EVSProvince;
import com.hcwins.vehicle.ta.evs.apidao.EVSSubscriber;
import com.hcwins.vehicle.ta.evs.apiobj.user.*;
import com.hcwins.vehicle.ta.evs.apitest.EVSTestBase;
import com.hcwins.vehicle.ta.evs.data.EnterpriseData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseRegionData;
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
 * Created by wenji on 28/05/15.
 */
public class UserCompleteInfoAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(UserCompleteInfoAT.class);
    private static SubscriberData subscriber0;
    private EnterpriseRegionData regionData0;
    private EnterpriseData enterpriseData1;
    private Long cityId0, provinceId0, enterpriseId0;

    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        subscriber0 = getDataSet().getSubscriberData().get(0);
        enterpriseData1 = getDataSet().getEnterprises().get(1);
        regionData0 = getDataSet().getEnterpriseRegions().get(0);
        cityId0 = EVSCity.dao.findCityIdByName(regionData0.getCityName()).get(0).getId();
        provinceId0 = EVSProvince.dao.getProvinceIdByName(regionData0.getProvinceName()).get(0).getId();
        enterpriseId0 = EVSEnterprise.dao.findEnterpriseByName(enterpriseData1.getEnterpriseName()).get(0).getId();

        SubscriberData.cleanRegistEnv(subscriber0.getMobile(), subscriber0.getEmail());

        VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(subscriber0.getMobile(), CaptchaRegist.postAndGetCaptchas(subscriber0.getMobile()).get(0).getCaptcha());
        Regist.postRegistRequest(subscriber0.getMobile(), subscriber0.getPassword());
        Login.postLoginRepuest(subscriber0.getMobile(), subscriber0.getPassword(),true);
        //TODO: Add email for subscriber0
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "完善资料接口: 完善资料成功")
    public void testUserCompleteInfoSuccess() {
        CompleteInfoResponse completeInfoResponse = CompleteInfo.postCompleteInfoRequest(enterpriseId0, subscriber0.getRealName(), subscriber0.getEmail(), cityId0, provinceId0);
        assertThat(completeInfoResponse.getResult().getCode(), equalTo(0));
        assertThat(EVSSubscriber.dao.findSubscriberByMobile(subscriber0.getMobile()).get(0).getEnterpriseId(), equalTo(enterpriseId0));
        assertThat(EVSSubscriber.dao.findSubscriberByMobile(subscriber0.getMobile()).get(0).getRealName(), equalTo(subscriber0.getRealName()));
        assertThat(EVSSubscriber.dao.findSubscriberByMobile(subscriber0.getMobile()).get(0).getEmail(), equalTo(subscriber0.getEmail()));
        assertThat(EVSSubscriber.dao.findSubscriberByMobile(subscriber0.getMobile()).get(0).getCityId(), equalTo(cityId0));
        assertThat(EVSSubscriber.dao.findSubscriberByMobile(subscriber0.getMobile()).get(0).getProvinceId(), equalTo(provinceId0));
    }

    @DataProvider
    public static Object[][] genUserCompleteInfoErrorCodeTestData() {
        return new Object[][]{
                  {Long.valueOf(000), "张晓月", "zhangxiaoyue@163.com", Long.valueOf(510100), Long.valueOf(510000), 403}        //企业不存在
                , {Long.valueOf(212),  "张晓月", "zhangxiaoyue@163.com", Long.valueOf(510100), Long.valueOf(510000), 404}     //企业不可用
                , {Long.valueOf(208),  "张晓月", "wanglili@163.com", Long.valueOf(510100), Long.valueOf(510000), 214}         //用户邮件已注册
                , {Long.valueOf(208),  "张晓月", "zhangxiaoyue", Long.valueOf(510100), Long.valueOf(510000), 215}             //用户邮件格式错误
                , {Long.valueOf(208),  "张晓月", "12233@", Long.valueOf(510100), Long.valueOf(510000), 215}                   //用户邮件格式错误
                , {Long.valueOf(208),  "张晓月", "zhangxiaoyue@163.com", Long.valueOf(140100), Long.valueOf(510000), 504}     //此省份下没有此城市
                , {Long.valueOf(208),  "张晓月", "zhangxiaoyue@163.com", Long.valueOf(510100), Long.valueOf(212222), 502}     //省份不存在
                , {Long.valueOf(208),  "张晓月", "zhangxiaoyue@163.com", Long.valueOf(000001), Long.valueOf(510000), 503}     //city不存在
//                , {null,"","", null, null, 229}    //请求中无数据
//                , {null, "张晓月", "zhangxiaoyue@163.com", Long.valueOf(510100), Long.valueOf(510000),213}              //TODO 企业ID为空完善资料成功?
                , {Long.valueOf(208),  "", "zhangxiaoyue@163.com", Long.valueOf(510100), Long.valueOf(510000),213}     //真实姓名为空
                , {Long.valueOf(208),  "张晓月", "", Long.valueOf(510100), Long.valueOf(510000),212}                   //邮箱为空
                , {Long.valueOf(208),  "张晓月", "zhangxiaoyue@163.com", null, Long.valueOf(510000),500}               //城市ID为空
                , {Long.valueOf(208),  "张晓月", "zhangxiaoyue@163.com", Long.valueOf(510100), null,501}               //省份ID为空
                // TODO 用户未找到 201
        };
    }

    @Test(description = "完善资料接口: 验证错误码",
            dependsOnMethods = {"testUserCompleteInfoSuccess"},
            dataProvider = "genUserCompleteInfoErrorCodeTestData")
    public void testUserCompleteInfoErrorCode(Long enterpriseId, String realName, String email, Long cityId, Long provinceId, int code) {
        CompleteInfoResponse completeInfoResponse = CompleteInfo.postCompleteInfoRequest(enterpriseId, realName, email, cityId, provinceId);
        assertThat(completeInfoResponse.getResult().getCode(), equalTo(code));
    }

    @Test(description = "完善资料接口:失败当user未登录")
    public void testUserCompleteInfoNotLogin() {
        CompleteInfoResponse completeInfoResponse = CompleteInfo.postCompleteInfoRequest(enterpriseId0, subscriber0.getRealName(), subscriber0.getEmail(), cityId0, provinceId0,null);
        assertThat(completeInfoResponse.getResult().getCode(), equalTo(219));
    }

}
