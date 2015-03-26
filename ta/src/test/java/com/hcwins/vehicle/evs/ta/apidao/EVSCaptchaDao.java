package com.hcwins.vehicle.evs.ta.apidao;

import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;

import java.util.List;

/**
 * Created by tommy on 3/25/15.
 */
@RegisterMapper(EVSCaptcha.Mapper.class)
public interface EVSCaptchaDao {
    @SqlQuery("select * from EVS_Captcha where mobile=:mobile and module=:module")
    public List<EVSCaptcha> findCaptchaByMobileModule(
            @Bind("mobile") String mobile,
            @Bind("module") String module
    );

    @SqlQuery("select * from EVS_Captcha where mobile=:mobile and module=:module and status=:status")
    public List<EVSCaptcha> findCaptchaByMobileModuleStatus(
            @Bind("mobile") String mobile,
            @Bind("module") String module,
            @Bind("status") String status
    );
}
