package com.hcwins.vehicle.ta.evs.apiobj.user;

import com.hcwins.vehicle.ta.evs.apiobj.BaseResponse;

/**
 * Created by xiangzhai on 10/04/15.
 */
public class RegistResponse extends BaseResponse {
    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
