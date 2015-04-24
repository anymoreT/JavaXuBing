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
public abstract class EVSEnterpriseDao extends BaseDao {
    public EVSEnterprise findById(Long id) {
        return super.findById(EVSEnterprise.class, id);
    }

    @SqlQuery("select * from EVS_Enterprise where enterpriseName=:enterpriseName")
    public abstract List<EVSEnterprise> findEnterpriseByName(
            @Bind("enterpriseName") String enterpriseName
    );

    //TODO: refactor the necessary to delete records
    //      update the unique fields based on timestamp as work around
    @SqlUpdate("delete from EVS_Enterprise where id=:id")
    public abstract int deleteEnterpriseById(
            @Bind("id") Long id
    );
}
