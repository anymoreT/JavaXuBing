package com.hcwins.vehicle.ta.evs;

import com.hcwins.vehicle.ta.evs.data.EnterpriseAdminData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseData;
import com.hcwins.vehicle.ta.evs.data.EnterpriseRegionData;

import java.util.List;

/**
 * Created by tommy on 3/23/15.
 */
public class DataSet {
    public List<EnterpriseData> enterprises;
    public List<EnterpriseAdminData> enterpriseAdmins;
    public List<EnterpriseRegionData> enterpriseRegionDatas;

    @Override
    public String toString() {
        return "DataSet{" +
                "enterprises=" + enterprises +
                ", enterpriseAdmins=" + enterpriseAdmins +
                '}';
    }
}
