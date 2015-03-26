package com.hcwins.vehicle.evs.ta;

import com.hcwins.vehicle.evs.ta.data.EnterpriseAdminData;
import com.hcwins.vehicle.evs.ta.data.EnterpriseData;

import java.util.List;

/**
 * Created by tommy on 3/23/15.
 */
public class DataSet {
    public List<EnterpriseData> enterprises;
    public List<EnterpriseAdminData> enterpriseAdmins;

    @Override
    public String toString() {
        return "DataSet{" +
                "enterprises=" + enterprises +
                ", enterpriseAdmins=" + enterpriseAdmins +
                '}';
    }
}
