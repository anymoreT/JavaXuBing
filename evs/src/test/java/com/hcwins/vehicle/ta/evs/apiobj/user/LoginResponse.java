package com.hcwins.vehicle.ta.evs.apiobj.user;

import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountResponse;

/**
 * Created by tommy on 4/22/15.
 */
public class LoginResponse extends BaseAccountResponse {
    private int errorCount;
    private int status;

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
