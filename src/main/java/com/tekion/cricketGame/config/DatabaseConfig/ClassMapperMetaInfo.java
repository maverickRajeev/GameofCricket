package com.tekion.cricketGame.config.DatabaseConfig;

import java.lang.annotation.*;


@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Inherited
@Documented
public @interface ClassMapperMetaInfo {
    ClassMapper getClassName();
}
