package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by xiangzhai on 13/04/15.
 */
@RegisterMapper(EVSEnterprise.Mapper.class)
public abstract class EVSEnterpriseDao extends BaseDao{
    public EVSEnterprise findById(long id) { return super.findById(EVSEnterprise.class, id); }

    @SqlQuery("select * from EVS_Enterprise where enterpriseName=:enterpriseName")
    public abstract List<EVSEnterprise> findEnterpriseByName(
        @Bind("enterpriseName") String enterpriseName
    );

    @SqlUpdate("delete from EVS_Enterprise where id=:id")
    public abstract int deleteEnterpriseById(
            @Bind("id") long id
    );
}
