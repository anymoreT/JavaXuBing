package com.hcwins.vehicle.evs.ta;

import com.hcwins.vehicle.evs.ta.data.Enterprise;
import com.hcwins.vehicle.evs.ta.data.EnterpriseAdmin;

import java.util.List;

/**
 * Created by tommy on 3/23/15.
 */
public class DataSet {
    public List<Enterprise> enterprises;
    public List<EnterpriseAdmin> enterpriseAdmins;

    @Override
    public String toString() {
        return "DataSet{" +
                "enterprises=" + enterprises +
                ", enterpriseAdmins=" + enterpriseAdmins +
                '}';
    }
}
