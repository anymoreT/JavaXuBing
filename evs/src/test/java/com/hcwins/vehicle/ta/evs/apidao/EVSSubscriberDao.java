package com.hcwins.vehicle.ta.evs.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.Date;
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

    @SqlQuery("select * from EVS_Subscriber where mobile=:mobile")
    public abstract List<EVSSubscriber> findSubscriberByMobile(
            @Bind("mobile") String mobile
    );

    @SqlQuery("select count(*) from EVS_Subscriber where status=:status and enterpriseId=:enterpriseId")
    public abstract int countSubscriberByStatusAndEnterpriseId(
            @Bind("status") EVSSubscriber.SubscriberStatus status,
            @Bind("enterpriseId") Long enterpriseId
    );

    @SqlUpdate("insert into EVS_Subscriber (drivingLicenseNumber, drivingYears, email, identityNo, isLocked, isSupervisor,loginFailureCount, name, mobile, realName, status, unLockTime, vehicleAptitude, enterpriseId, cityId, provinceId)" +
            "values (:drivingLicenseNumber, :drivingYears, :email, :identityNo, :isLocked, :isSupervisor, :loginFailureCount, :name, :mobile, :realName, :status, :unLockTime, :vehicleAptitude, :enterpriseId, :cityId, :provinceId)")
    public abstract void insertSubscriber(
            @Bind("drivingLicenseNumber") String drivingLicenseNumber,
            @Bind("drivingYears") Integer drivingYears,
            @Bind("email") String email,
            @Bind("identityNo") String identityNo,
            @Bind("isLocked") Boolean isLocked,
            @Bind("isSupervisor") Boolean isSupervisor,
            @Bind("loginFailureCount") Integer loginFailureCount,
            @Bind("name") String name,
            @Bind("mobile") String mobile,
            @Bind("realName") String realName,
            @Bind("status") EVSSubscriber.SubscriberStatus status,
            @Bind("unLockTime") Date unLockTime,
            @Bind("vehicleAptitude") EVSSubscriber.VehicleAptitude vehicleAptitude,
            @Bind("cityId") Long cityId,
            @Bind("enterpriseId") Long enterpriseId,
            @Bind("provinceId") Long provinceId
    );

    @SqlQuery("select count(*) from EVS_Subscriber")
    public abstract int count();

    @SqlUpdate("update EVS_Subscriber set email=:email where mobile=:mobile")
    public abstract int updateEmailByMobile(
            @Bind("mobile") String mobile,
            @Bind("email") String email
    );

    @SqlUpdate("update EVS_Subscriber set mobile=:mobile where email=:email")
    public abstract int updateMobileByEmail(
            @Bind("email") String email,
            @Bind("mobile") String mobile
    );
}
