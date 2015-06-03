package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by wenji on 26/05/15.
 */
@RegisterMapper(EVSSubscriberCredential.Mapper.class)
public abstract class EVSSubscriberCredentialDao extends BaseDao {
    public EVSSubscriberCredential findById(Long id) {
        return super.findById(EVSSubscriberCredential.class, id);
    }


    @SqlQuery("select * from EVS_SubscriberCredential where subscriberId=:subscriberId")
    public abstract List<EVSSubscriberCredential> findSubscriberCredentialBySubscriberId(
            @Bind("subscriberId") Long subscriberId
    );

    @SqlUpdate("update EVS_SubscriberCredential set credentialName=:presentValue where credentialName=:originalValue")
    public abstract int updateSubscriberCredentialNameByEmailOrMobile(
            @Bind("originalValue") String originalValue,
            @Bind("presentValue") String presentValue
    );
}