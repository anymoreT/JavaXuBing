package com.hcwins.vehicle.ta.evs.apiworkflow;

import com.hcwins.vehicle.ta.evs.DataSet;
import com.hcwins.vehicle.ta.evs.EVSUtil;
import com.hcwins.vehicle.ta.evs.apidao.EVSCity;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterpriseAdmin;
import com.hcwins.vehicle.ta.evs.apidao.EVSEnterpriseAdminCredential;
import com.hcwins.vehicle.ta.evs.apidao.EVSProvince;
import com.hcwins.vehicle.ta.evs.apiobj.enterprise.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by xiangzhai on 30/04/15.
 */
public class EnterpriseRegistAndLoginFlow extends BaseFlow {
    private final Logger logger = LoggerFactory.getLogger(EnterpriseRegistAndLoginFlow.class);

    private DataSet dataSet = EVSUtil.getDataSet();
    private String token;
    private static EnterpriseRegistAndLoginFlow loginFlow = new EnterpriseRegistAndLoginFlow();

    private EnterpriseRegistAndLoginFlow() {
        this.cleanEnv();
        this.doVerifyMobileAndCaptcha(doCaptchaRegist());
        this.doEnterpriseRegist();
        token = this.doEnterpriseLogin();
    }

    public static EnterpriseRegistAndLoginFlow getInstance() {
        return loginFlow;
    }

    private String doCaptchaRegist() {
        if (CaptchaRegist.postAndGetCaptchas(mobile).size() > 0) {
            logger.debug("use mobile:{} to get captcha success", mobile);
            return CaptchaRegist.postAndGetCaptchas(mobile).get(0).getCaptcha();
        } else {
            logger.debug("use mobile:{} to get captcha failed", mobile);
            return null;
        }
    }

    private void doVerifyMobileAndCaptcha(String captcha) {
        if (0 != VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(mobile, captcha).getResult().getCode()) {
            logger.warn("use mobile:{} captcha:{} to verify failed", mobile, captcha);
        } else {
            logger.debug("use mobile:{} captcha:{} to verify success", mobile, captcha);
        }
    }

    private void doEnterpriseRegist() {
        RegistResponse response = Regist.postRegistRequest(enterpriseName, enterpriseWebSite, cityId, realName, mobile, email, password, provinceId);
        if (0 != response.getResult().getCode()) {
            logger.warn("enterprise regist failed");
        } else {
            logger.debug("enterprise regist success");
        }
    }

    private String doEnterpriseLogin() {
        if (0 != Login.postLoginRepuest(mobile, password, true).getResult().getCode()) {
            logger.warn("enterprise login failed");
            return null;
        } else {
            logger.debug("enterprise login success");
            return Login.postLoginRepuest(mobile, password, true).getToken();
        }
    }

    public void cleanEnv() {
        String newemail = EVSUtil.getUniqValue(EVSEnterpriseAdmin.dao.count(), "AT000");
        String newmobile = EVSUtil.getUniqValue(EVSEnterpriseAdmin.dao.count(), "15986");
        EVSEnterpriseAdmin.dao.updateEmailByMobile(mobile, newemail);
        EVSEnterpriseAdmin.dao.updateMobileByEmail(newemail, newmobile);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(email, newemail);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(mobile, newmobile);
    }

    public String getToken() {
        return token;
    }
}
