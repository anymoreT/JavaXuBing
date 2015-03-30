package com.hcwins.vehicle.evs.ta.apidao;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by tommy on 3/27/15.
 */
public class Annotations {
    @Target({ElementType.TYPE})
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Entity {
        String table();

        Class mapper();
    }

    public static <K extends BaseEntity> String getTable(Class<K> clazz) {
        return clazz.isAnnotationPresent(Entity.class) ? clazz.getAnnotation(Entity.class).table() : null;
    }

    public static <K extends BaseEntity> Class getMapper(Class<K> clazz) {
        return clazz.isAnnotationPresent(Entity.class) ? clazz.getAnnotation(Entity.class).mapper() : null;
    }
}