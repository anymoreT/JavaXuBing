package com.hcwins.vehicle.ta.evs.apiobj;

/**
 * Created by tommy on 4/24/15.
 */
public abstract class BaseAccountRequest extends BaseRequest {
    private String account;
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
