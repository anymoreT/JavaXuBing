package com.hcwins.vehicle.ta.evs.apitest.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.enterprise.Regist;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.RegistResponse;
import com.hcwins.vehicle.ta.evs.apitest.EVSTestBase;
import com.hcwins.vehicle.ta.evs.data.EnterpriseAdminData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseRegionData;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterprise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by wenji on 08/05/15.
 */
public class RegistAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(RegistAT.class);
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

        EnterpriseAdminData.cleanRegistEnv(adminData1.getMobile(), adminData1.getEmail());
        EnterpriseAdminData.cleanRegistEnv(adminData0.getMobile(), adminData0.getEmail());
        EnterpriseData.cleanEnterpriseEnv(enterpriseData0.getEnterpriseName());
        //TODO:
    }

    @AfterClass
    public void afterClass() {
        //TODO:

        super.afterClass();
    }

    @Test(description = "企业注册接口测试: 验证全部参数正确时注册成功")
    public void testEnterpriseRegistSuccess() {
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseData0, regionData0, adminData0);
        assertThat(registResponse.getResult().getCode(), equalTo(0));
        assertThat(registResponse.getEnterpriseStatus(), equalTo(0));
        String enterpriseName1 = enterpriseData0.getEnterpriseName();
        System.out.println("enterpriseName1 =" + enterpriseName1);
        EVSEnterprise enterprise1 = EVSEnterprise.dao.findEnterpriseByName(enterpriseName1).get(0);
        assertThat(enterprise1.getEnterpriseName(), equalTo(enterpriseData0.getEnterpriseName()));
        assertThat(enterprise1.getWebsite(), equalTo(enterpriseData0.getWebsite()));
        assertThat(enterprise1.getCityId(), equalTo(regionData0.getCityId()));
        assertThat(enterprise1.getStatus(), equalTo(EVSEnterprise.Status.UNAUDITED));
    }

    @DataProvider
    public static Object[][] genEnterpriseRegistErrorCodeTestData() {
        return new Object[][]{
                {"", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 401}        //企业名称为空
                , {"try", "www.hcwins.cn", null, "hanmeimei", "15881100001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 500}                   //城市为空
                , {"try", "www.hcwins.cn", Long.valueOf(987654), "李lei", "15881100001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 503}   //城市不存在
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.li@hcwins.com", "123456", null, 501}                   //省份为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.li@hcwins.com", "123456", Long.valueOf(987654), 502}   //省份不存在
                , {"try", "www.hcwins.cn", Long.valueOf(140100), "李lei", "15881100001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 504}   //此省份下没有此城市
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "1588110001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 205}    //手机号格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "158811000001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 205}  //手机号格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "1588110000a", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 205}   //手机号格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.li@hcwins.com", "", Long.valueOf(510000), 210}         //密码为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "", "15881100001", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 213}            //管理员姓名为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.lihcwins.com", "123456", Long.valueOf(510000), 215}   //管理员邮件格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", " ", "123456", Long.valueOf(510000), 215}                       //管理员邮件格式错误
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.li@hcwins.com", "01", Long.valueOf(510000), 217}       //密码长度不足
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "lei.li@hcwins.com", "1234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890", Long.valueOf(510000), 227}   //密码长度超长
//                ,{" ", "www.hcwins.cn", Long.valueOf(510100), "hanmeimei", "15881100000", "lei.li@hcwins.com", "123456", Long.valueOf(510000), 401}     //企业名称为空
                , {"try", "www.hcwins.cn", Long.valueOf(510100), "李lei", "15881100001", "", "123456", Long.valueOf(510000), 212}                         //管理员邮件为空
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
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseData1.getEnterpriseName(), enterpriseData1.getWebsite(), regionData1.getCityId(), adminData1.getRealName(), adminData0.getMobile(), adminData1.getEmail(), adminData1.getPassword(), regionData1.getProvinceId());
        assertThat(registResponse.getResult().getCode(), equalTo(204));
    }

    @Test(description = "企业注册接口测试: 验证管理员邮件已注册",
            dependsOnMethods = {"testAdminMobileHaveRegisted"})
    public void testAdminMailHaveRegisted() {
        RegistResponse registResponse = Regist.postRegistRequest(enterpriseData1.getEnterpriseName(), enterpriseData1.getWebsite(), regionData1.getCityId(), adminData1.getRealName(), adminData1.getMobile(), adminData0.getEmail(), adminData1.getPassword(), regionData1.getProvinceId());
        assertThat(registResponse.getResult().getCode(), equalTo(214));
    }
}
