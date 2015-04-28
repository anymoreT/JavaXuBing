package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by xiangzhai on 27/04/15.
 */
@Annotations.Entity(table = "EVS_City", mapper = EVSCity.Mapper.class)
public class EVSCity extends BaseEntity {
    /*
    Table: EVS_City
    Columns:
    id bigint not null auto_increment,
    createTime datetime,
    updateTime datetime,
    name varchar(30) not null,
    provinceId bigint,
    primary key (id)
    */

    protected String name;
    protected Long provinceId;

    public EVSCity(Long id, Date createTime, Date updateTime, String name, Long provinceId) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.name = name;
        this.provinceId = provinceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    public static class Mapper implements ResultSetMapper<EVSCity> {
        @Override
        public EVSCity map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSCity(
                    r.getLong("id"),
                    r.getDate("createTime"),
                    r.getDate("updateTime"),
                    r.getString("name"),
                    r.getLong("provinceId")
            );
        }
    }

    @Override
    public String toString() {
        return "EVSCity{" +
                "name='" + name + '\'' +
                ", provinceId=" + provinceId +
                '}';
    }
    public static EVSCityDao dao = EVSUtil.getDAO(EVSCityDao.class);
}
