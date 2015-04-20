package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by wenji on 13/04/15.
 */

@RegisterMapper(EVSEnterpriseAdminCredential.Mapper.class)
public abstract class EVSEnterpriseAdminCredentialDao extends BaseDao {
    public EVSEnterpriseAdminCredential findById(long id) {
        return super.findById(EVSEnterpriseAdminCredential.class, id);
    }

    @SqlQuery("select * from EVS_EnterpriseAdminCredential where enterpriseAdminId=:EVS_EnterpriseAdmin.Id")
    public abstract List<EVSEnterpriseAdminCredential> findEnterpriseAdminCredentialByEnterpriseAdminId(
            @Bind("enterpriseAdminId") long enterpriseAdminId
    );

    @SqlQuery("select count * from EVS_EnterpriseAdminCredential where enterpriseAdminId=:EVS_EnterpriseAdmin.Id")
    public abstract List<EVSEnterpriseAdminCredential> countEnterpriseAdminCredentialByEnterpriseAdminId(
            @Bind("enterpriseAdminId") long enterpriseAdminId
    );

    @SqlUpdate("delete from EVS_EnterpriseAdminCredential where enterpriseAdminId=:enterpriseAdminId")
    public abstract int deleteEnterpriseAdminCredentialByEnterpriseAdminId(
            @Bind("enterpriseAdminId") long enterpriseAdminId
    );

}