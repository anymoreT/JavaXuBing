package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by wenji on 13/04/15.
 */
@RegisterMapper(EVSEnterpriseAdmin.Mapper.class)
public abstract class EVSEnterpriseAdminDao extends BaseDao {
    public EVSEnterpriseAdmin findById(Long id) {
        return super.findById(EVSEnterpriseAdmin.class, id);
    }

    @SqlQuery("select * from EVS_EnterpriseAdmin where mobile=:mobile")
    public abstract List<EVSEnterpriseAdmin> findEnterpriseAdminByMobile(
            @Bind("mobile") String mobile

    );

    @SqlUpdate("update EVS_EnterpriseAdmin set email=:email where mobile=:mobile")
    public abstract int updateEmailByMobile(
        @Bind("mobile") String mobile,
        @Bind("email") String email
    );

    @SqlUpdate("update EVS_EnterpriseAdmin set mobile=:mobile where email=:email")
    public abstract int updateMobileByEmail(
        @Bind("email") String email,
        @Bind("mobile") String mobile
    );
}
