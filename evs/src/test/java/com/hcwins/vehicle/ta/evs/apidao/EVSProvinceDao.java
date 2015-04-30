package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by xiangzhai on 28/04/15.
 */
@RegisterMapper(EVSProvince.Mapper.class)
public abstract class EVSProvinceDao extends BaseDao {
    @SqlQuery("select * from EVS_Province where name=:name")
    public abstract List<EVSProvince> getProvinceIdByName(@Bind("name") String name);
}
