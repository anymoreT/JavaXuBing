package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by xiangzhai on 27/04/15.
 */
@RegisterMapper(EVSCity.Mapper.class)
public abstract class EVSCityDao extends BaseDao {
    @SqlQuery("select * from EVS_City where name=:name")
    public abstract List<EVSCity> findCityIdByName(@Bind("name") String name);
}
