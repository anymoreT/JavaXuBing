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
    public EVSEnterpriseAdminCredential findById(Long id) {
        return super.findById(EVSEnterpriseAdminCredential.class, id);
    }

    @SqlQuery("select * from EVS_EnterpriseAdminCredential where enterpriseAdminId=:enterpriseAdminId")
    public abstract List<EVSEnterpriseAdminCredential> findEnterpriseAdminCredentialByEnterpriseAdminId(
            @Bind("enterpriseAdminId") Long enterpriseAdminId
    );

    @SqlQuery("select count * from EVS_EnterpriseAdminCredential where enterpriseAdminId=:enterpriseAdminId")
    public abstract int countEnterpriseAdminCredentialByEnterpriseAdminId(
            @Bind("enterpriseAdminId") Long enterpriseAdminId
    );

    @SqlUpdate("update EVS_EnterpriseAdminCredential set credentialName=:NEW where credentialName=:OLD")
    public abstract int updateCredentialNameByEmailOrMobile(
        @Bind("OLD") String OLD,
        @Bind("NEW") String NEW
    );

    @SqlQuery("select count(*) from EVS_EnterpriseAdminCredential")
    public abstract int findCounts();
}