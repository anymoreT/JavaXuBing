package com.hcwins.vehicle.evs.ta.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by tommy on 3/25/15.
 */
@RegisterMapper(EVSCaptcha.Mapper.class)
public abstract class EVSCaptchaDao extends BaseDao {
    public EVSCaptcha findById(long id) {
        return super.findById(EVSCaptcha.class, id);
    }

    @SqlQuery("select * from EVS_Captcha where mobile=:mobile and module=:module")
    public abstract List<EVSCaptcha> findCaptchasByMobileModule(
            @Bind("mobile") String mobile,
            @Bind("module") String module
    );

    @SqlQuery("select * from EVS_Captcha where mobile=:mobile and module=:module and status=:status")
    public abstract List<EVSCaptcha> findCaptchasByMobileModuleStatus(
            @Bind("mobile") String mobile,
            @Bind("module") String module,
            @Bind("status") String status
    );

    @SqlUpdate("update EVS_Captcha set module=:module where id=:id")
    public abstract int updateModule(@BindBean EVSCaptcha evsCaptcha);

    @SqlUpdate("update EVS_Captcha set status=:status where id=:id")
    public abstract int updateStatus(@BindBean EVSCaptcha evsCaptcha);
}
