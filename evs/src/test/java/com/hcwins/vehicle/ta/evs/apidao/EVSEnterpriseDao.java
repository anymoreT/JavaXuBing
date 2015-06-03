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
    @SqlUpdate("update EVS_Enterprise set mail=:mail where enterpriseName=:enterpriseName")
    public abstract int updateEmailByName(
            @Bind("enterpriseName") String enterpriseName,
            @Bind("mail") String mail
    );

    @SqlUpdate("update EVS_Enterprise set status=:status where Id=:Id")
    public abstract int updateEnterpriseStatusById(
            @Bind("status") EVSEnterprise.Status status,
            @Bind("Id") Long enterpriseId
    );

    @SqlUpdate("update EVS_Enterprise set enterpriseName=:enterpriseName where website=:website")
    public abstract int updatEnterpriseNameByWebsite(
            @Bind("website") String website,
            @Bind("enterpriseName") String enterpriseName

    );

    @SqlUpdate("update EVS_Enterprise set website=:website where enterpriseName=:enterpriseName")
    public abstract int updateWebsiteByEnterpriseName(
            @Bind("enterpriseName") String enterpriseName,
            @Bind("website") String website

    );

    @SqlQuery("select count(*) from EVS_Enterprise")
    public abstract int count();
}
