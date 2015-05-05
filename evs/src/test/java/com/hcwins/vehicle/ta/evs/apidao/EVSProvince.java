package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

/**
 * Created by xiangzhai on 28/04/15.
 */
@Annotations.Entity(table = "EVS_Province", mapper = EVSProvince.Mapper.class)
public class EVSProvince extends BaseEntity {
    /*
    table: EVS_Province
    Columns:
    id bigint not null auto_increment,
    createTime datetime,
    updateTime datetime,
    firstLetter varchar(1),
    name varchar(30) not null,
    primary key (id)
     */

    protected String firstLetter;
    protected String name;

    public EVSProvince(Long id, Date createTime, Date updateTime, String firstLetter, String name) {
        this.id = id;
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.firstLetter = firstLetter;
        this.name = name;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static class Mapper implements ResultSetMapper<EVSProvince> {
        public EVSProvince map(int index, ResultSet r, StatementContext ctx) throws SQLException {
            return new EVSProvince(
                    r.getLong("id"),
                    r.getDate("createTime"),
                    r.getDate("updateTime"),
                    r.getString("firstLetter"),
                    r.getString("name")
            );
        }
    }

    @Override
    public String toString() {
        return "EVSProvince{" +
                "firstLetter='" + firstLetter + '\'' +
                ", name='" + name + '\'' +
                '}' + super.toString();
    }

    public static EVSProvinceDao dao = EVSUtil.getDAO(EVSProvinceDao.class);
}
