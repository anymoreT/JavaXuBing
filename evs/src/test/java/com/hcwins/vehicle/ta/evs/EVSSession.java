package com.hcwins.vehicle.ta.evs;

import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountRequest;
import com.hcwins.vehicle.ta.evs.apiobj.BaseAccountResponse;

/**
 * Created by tommy on 4/24/15.
 */
public class EVSSession {
    private AccountType accountType;
    private BaseAccountRequest loginRequest;
    private BaseAccountResponse loginResponse;

    public static enum AccountType {
        ENTERPRISE,
        USER,
    }

    public EVSSession(AccountType accountType, BaseAccountRequest loginRequest, BaseAccountResponse loginResponse) {
        this.accountType = accountType;
        this.loginRequest = loginRequest;
        this.loginResponse = loginResponse;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public BaseAccountRequest getLoginRequest() {
        return loginRequest;
    }

    public BaseAccountResponse getLoginResponse() {
        return loginResponse;
    }

    public String getKey() {
        return getAccountType().name() + ":" + getLoginRequest().getAccount();
    }
}
