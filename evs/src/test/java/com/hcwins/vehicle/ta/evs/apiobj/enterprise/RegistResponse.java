package com.hcwins.vehicle.ta.evs.apiobj.enterprise;

import com.hcwins.vehicle.ta.evs.apiobj.BaseResponse;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class RegistResponse extends BaseResponse {
    private int enterpriseStatus;

    public int getEnterpriseStatus() {
        return enterpriseStatus;
    }

    public void setEnterpriseStatus(int enterpriseStatus) {
        this.enterpriseStatus = enterpriseStatus;
    }
}
