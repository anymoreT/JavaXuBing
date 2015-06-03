package com.hcwins.vehicle.ta.evs.apitest.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterprise;
import com.hcwins.vehicle.ta.evs.apidao.EVSSubscriber;
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
import org.testng.annotations.Test;

import static com.hcwins.vehicle.ta.evs.EVSUtil.getEnterpriseToken;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

/**
 * Created by wenji on 08/05/15.
 */
public class GetUnauditUserAT extends EVSTestBase {
    static final Logger logger = LoggerFactory.getLogger(GetUnauditUserAT.class);
    private EnterpriseAdminData adminData0;
    private EnterpriseRegionData regionData0;
    private EnterpriseData enterpriseData0;
    @BeforeClass
    public void beforeClass() {
        super.beforeClass();
        enterpriseData0 = getDataSet().getEnterprises().get(0);
        regionData0 = getDataSet().getEnterpriseRegions().get(0);
        adminData0 = getDataSet().getEnterpriseAdmins().get(0);

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
        logger.debug(getEnterpriseToken("15283837023"));
        logger.debug(EVSUtil.getCurrentEnterpriseToken());
        logger.debug(EVSUtil.getCurrentToken());
        GetVehicles.postGetVehiclesRequest();
    }

     @Test(description = "验证企业管理员查看待审批的用户列表成功")
    public void testGetUnauditUsersSuccess() {
        int pageSize = 10;
        int pageNo = 1;
        GetUnauditUsersResponse getUnauditUsersResponse = GetUnauditUsers.postGetUnauditUsersRequest(pageSize, pageNo);
        assertThat(getUnauditUsersResponse.getResult().getCode(), equalTo(0));
        String enterpriseName0 = enterpriseData0.getEnterpriseName();
        Long enterpriseId0 = EVSEnterprise.dao.findEnterpriseByName(enterpriseName0).get(0).getId();
        assertThat(getUnauditUsersResponse.getSubscriberList().size(), equalTo(EVSSubscriber.dao.countSubscriberByStatusAndEnterpriseId(EVSSubscriber.SubscriberStatus.UNAUDITED, enterpriseId0)));
        int[] status = new int[getUnauditUsersResponse.getSubscriberList().size()];
        for (int i=0; i< getUnauditUsersResponse.getSubscriberList().size()-1; i++){
            status[i] = getUnauditUsersResponse.getSubscriberList().size();
            assertThat(status[i],equalTo(0));
        }
    }

    @Test(description = "验证企业当管理员不存在时查看待审批的用户列表失败")
    public void testGetUnauditUsersFailedWithNotExistAdmin() {
        int pageSize = 10;
        int pageNo = 1;
        String account="13648087441";
        GetUnauditUsersResponse getUnauditUsersResponse = GetUnauditUsers.postGetUnauditUsersRequest(pageSize, pageNo, getEnterpriseToken(account));//TODO 不存在的管理员
        assertThat(getUnauditUsersResponse.getResult().getCode(), equalTo(223));
    }

    @Test(description = "验证企业当管理员未登录时查看待审批的用户列表失败")
    public void testGetUnauditUsersFailedWhenNotLogin() {
        int pageSize = 10;
        int pageNo = 1;
        GetUnauditUsersResponse getUnauditUsersResponse = GetUnauditUsers.postGetUnauditUsersRequest(pageSize, pageNo, null);
        assertThat(getUnauditUsersResponse.getResult().getCode(), equalTo(219));
    }
}

