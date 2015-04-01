package com.hcwins.vehicle.ta.evs.apidao;

import com.hcwins.vehicle.ta.evs.EVSUtil;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

/**
 * Created by tommy on 3/27/15.
 */
public class BaseDao {
    public <EntityType extends BaseEntity> EntityType findById(Class<EntityType> entityType, long id) {
        EntityType ret = null;
        try {
            ret = EVSUtil.getDBHandle()
                    .createQuery(String.format("select * from %s where id=:id", Annotations.getTable(entityType)))
                    .bind("id", id)
                    .map((ResultSetMapper<EntityType>) Annotations.getMapper(entityType).newInstance())
                    .first();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return ret;
    }

    public <EntityType extends BaseEntity> int update(EntityType entity, String sql) {
        return EVSUtil.getDBHandle().createStatement(sql).bindFromProperties(entity).execute();
    }
}
