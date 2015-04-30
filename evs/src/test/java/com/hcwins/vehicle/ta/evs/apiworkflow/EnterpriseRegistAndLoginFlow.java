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
public class EnterpriseRegistAndLoginFlow {
    private final Logger logger = LoggerFactory.getLogger(EnterpriseRegistAndLoginFlow.class);

    private DataSet dataSet = EVSUtil.getDataSet();
    private String token;
    private static EnterpriseRegistAndLoginFlow loginFlow = new EnterpriseRegistAndLoginFlow();

    private EnterpriseRegistAndLoginFlow() {
        Map<String, Object> actInfo = getAccountInfo();
        this.cleanEnv(actInfo);
        this.doVerifyMobileAndCaptcha(actInfo, doCaptchaRegist(actInfo));
        this.doEnterpriseRegist(actInfo);
        token = this.doEnterpriseLogin(actInfo);
    }

    public static EnterpriseRegistAndLoginFlow getInstance() {
        return loginFlow;
    }

    public Map<String, Object> getAccountInfo() {
        Map<String, Object> paramMap = new HashMap<String, Object>();

        String provinceName = dataSet.getEnterpriseRegions().get(0).getProvinceName();
        String cityName = dataSet.getEnterpriseRegions().get(0).getCityName();
        Long provinceId = EVSProvince.dao.getProvinceIdByName(provinceName).get(0).getId();
        Long cityId = EVSCity.dao.findCityIdByName(cityName).get(0).getId();
        String enterpriseName = dataSet.getEnterprises().get(2).getEnterpriseName();
        String enterpriseWebSite = dataSet.getEnterprises().get(2).getEnterpriseWebsite();
        String mobile = dataSet.getEnterpriseAdmins().get(2).getMobile();
        String realName = dataSet.getEnterpriseAdmins().get(2).getRealName();
        String email = dataSet.getEnterpriseAdmins().get(2).getEmail();
        String password = dataSet.getEnterpriseAdmins().get(2).getPassword();

        paramMap.put("epname", enterpriseName);
        paramMap.put("epwebsite", enterpriseWebSite);
        paramMap.put("mobile", mobile);
        paramMap.put("rName", realName);
        paramMap.put("email", email);
        paramMap.put("pwd", password);
        paramMap.put("pvid", provinceId);
        paramMap.put("cyid", cityId);

        return paramMap;
    }

    private String doCaptchaRegist(Map map) {
        if (CaptchaRegist.postAndGetCaptchas(map.get("mobile").toString()).size() > 0) {
            logger.debug("use mobile:{} to get captcha success", map.get("mobile"));
            return CaptchaRegist.postAndGetCaptchas(map.get("mobile").toString()).get(0).getCaptcha();
        } else {
            logger.debug("use mobile:{} to get captcha failed", map.get("mobile"));
            return null;
        }
    }

    private void doVerifyMobileAndCaptcha(Map map, String captcha) {
        if (0 != VerifyMobileAndCaptcha.postVerifyMobileAndCaptchaRequest(map.get("mobile").toString(), captcha).getResult().getCode()) {
            logger.warn("use mobile:{} captcha:{} to verify failed", map.get("mobile"), captcha);
        } else {
            logger.debug("use mobile:{} captcha:{} to verify success", map.get("mobile"), captcha);
        }
    }

    private void doEnterpriseRegist(Map map) {
        RegistResponse response = Regist.postRegistRequest(map.get("epname").toString(), map.get("epwebsite").toString(), map.get("cyid"), map.get("rName").toString(), map.get("mobile").toString(), map.get("email").toString(), map.get("pwd").toString(), map.get("pvid"));
        if (0 != response.getResult().getCode()) {
            logger.warn("enterprise regist failed");
        } else {
            logger.debug("enterprise regist success");
        }
    }

    private String doEnterpriseLogin(Map map) {
        if (0 != Login.postLoginRepuest(map.get("mobile").toString(), map.get("pwd").toString(), true).getResult().getCode()) {
            logger.warn("enterprise login failed");
            return null;
        } else {
            logger.debug("enterprise login success");
            return Login.postLoginRepuest(map.get("mobile").toString(), map.get("pwd").toString(), true).getToken();
        }
    }

    public void cleanEnv(Map map) {
        String newemail = EVSUtil.getUniqValue(EVSEnterpriseAdmin.dao.findCounts(), 11, false);
        String newmobile = EVSUtil.getUniqValue(EVSEnterpriseAdmin.dao.findCounts(), 11, true);
        EVSEnterpriseAdmin.dao.updateEmailByMobile(map.get("mobile").toString(), newemail);
        EVSEnterpriseAdmin.dao.updateMobileByEmail(newemail, newmobile);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(map.get("email").toString(), newemail);
        EVSEnterpriseAdminCredential.dao.updateCredentialNameByEmailOrMobile(map.get("mobile").toString(), newmobile);
    }

    public String getToken() {
        return token;
    }
}
