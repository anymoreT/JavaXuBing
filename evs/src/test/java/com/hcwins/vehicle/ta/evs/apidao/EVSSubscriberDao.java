package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by wenji on 28/04/15.
 */
@RegisterMapper(EVSSubscriber.Mapper.class)
public abstract class EVSSubscriberDao extends BaseDao {
    public EVSSubscriber findById(Long id) {
        return super.findById(EVSSubscriber.class, id);
    }

    @SqlQuery("select * from EVS_Subscriber where mobile=:mobile and status=:status")
    public abstract List<EVSSubscriber> findSubscriberByMobileAndStatus(
            @Bind("mobile") String mobile,
            @Bind("status") String status
    );

    @SqlQuery("select * from EVS_Subscriber where status=:status")
    public abstract List<EVSSubscriber> findSubscriberByStatus(
            @Bind("status") String status
    );

    @SqlQuery("select count(*) from EVS_Subscriber where status=:status")
    public abstract int countSubscriberByStatus(
            @Bind("status") EVSSubscriber.SubscriberStatus status
    );
}
