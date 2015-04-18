package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by wenji on 13/04/15.
 */
@RegisterMapper(EVSEnterpriseAdmin.Mapper.class)
public abstract class EVSEnterpriseAdminDao extends BaseDao{
    public EVSEnterpriseAdmin findById(long id) {
        return super.findById(EVSEnterpriseAdmin.class, id);
    }

    @SqlQuery("select * from EVS_EnterpriseAdmin where mobile=:mobile and id=:id")
    public abstract List<EVSEnterpriseAdmin> findEnterpriseAdminByMobile(
            @Bind("mobile") String mobile

    );

}
