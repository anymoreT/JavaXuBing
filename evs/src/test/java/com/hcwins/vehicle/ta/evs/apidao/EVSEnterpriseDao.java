package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

/**
 * Created by xiangzhai on 13/04/15.
 */
@RegisterMapper(EVSEnterprise.Mapper.class)
public abstract class EVSEnterpriseDao extends BaseDao{
    public EVSEnterprise findById(long id) { return super.findById(EVSEnterprise.class, id); }
}
