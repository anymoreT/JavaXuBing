package com.hcwins.vehicle.ta.evs.apitest.enterprise;

import com.hcwins.vehicle.ta.evs.EVSUtil;
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
 * Created by wenji on 21/05/15.
 */
public class GetUnauditUserInfoAT extends EVSTestBase {
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
        LoginResponse lgResponse0= Login.postLoginRepuest(adminData0.getMobile(), adminData0.getPassword(), true);
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

    @Test(description = "验证企业管理员查看待审批的用户详细信息成功")
    public void testGetUnauditUsersInfoSuccess() {
        Long subscriberId0 = EVSSubscriber.dao.findSubscriberByStatus("UNAUDITED").get(0).getId();
        System.out.println(subscriberId0);
        GetUnauditUserInfoResponse getUnauditUserInfosResponse = GetUnauditUserInfo.postGetUnauditUserInfoRequest(subscriberId0);
        assertThat(getUnauditUserInfosResponse.getResult().getCode(), equalTo(0));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getSubscirberId(), equalTo(subscriberId0));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getRealName(), equalTo(EVSSubscriber.dao.findById(subscriberId0).getRealName()));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getMobile(), equalTo(EVSSubscriber.dao.findById(subscriberId0).getMobile()));
        assertThat(getUnauditUserInfosResponse.getApiSubscriber().getStatus(), equalTo(0));
        assertThat(EVSSubscriber.dao.findById(subscriberId0).getStatus(), equalTo(EVSSubscriber.SubscriberStatus.UNAUDITED));
    }
    @Test(description = "验证未选择要查看的待审核用户时查看待审批的用户详细信息失败")
    public void testGetUnauditUsersInfoFailedWithMissSubscriberId() {
        GetUnauditUserInfoResponse getUnauditUserInfosResponse = GetUnauditUserInfo.postGetUnauditUserInfoRequest(null);
        assertThat(getUnauditUserInfosResponse.getResult().getCode(), equalTo(221));
    }

    @Test(description = "验证当管理员未登录时查看待审批的用户详细信息失败")
    public void testGetUnauditUsersInfoFailedWhenAdminNotLogin() {
        Long subscriberId0 = EVSSubscriber.dao.findSubscriberByStatus("UNAUDITED").get(0).getId();
        GetUnauditUserInfoResponse getUnauditUserInfosResponse = GetUnauditUserInfo.postGetUnauditUserInfoRequest(subscriberId0, null);
        assertThat(getUnauditUserInfosResponse.getResult().getCode(), equalTo(219));
    }

}