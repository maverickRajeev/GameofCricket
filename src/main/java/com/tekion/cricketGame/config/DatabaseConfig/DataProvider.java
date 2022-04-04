package com.tekion.cricketGame.config.DatabaseConfig;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiFunction;

@Slf4j
@Component
public class DataProvider {

    public static DbType currentEnvironmentType = DbType.SQL;

    private final Map<DbType, Map<String, Object>> typeToClassNameToRepoMap;

    @Autowired
    public DataProvider(List<SqlRepo> sqlRepoList, List<MongoRepo> mongoRepoList){
        Map<DbType, Map<String, Object>> typeToClassNameToRepoMapInternal = Maps.newHashMap();
        for (SqlRepo sqlRepo : sqlRepoList) {
            ClassMapper classMap = getClassMap(sqlRepo.getClass());
            putInMap(typeToClassNameToRepoMapInternal, sqlRepo, classMap , DbType.SQL);
        }
        for (MongoRepo mongoRepo : mongoRepoList) {
            ClassMapper classMap = getClassMap(mongoRepo.getClass());
            putInMap(typeToClassNameToRepoMapInternal, mongoRepo, classMap , DbType.MONGO);
        }
        this.typeToClassNameToRepoMap = typeToClassNameToRepoMapInternal;
    }

    private void putInMap(Map<DbType, Map<String, Object>> typeToClassNameToRepoMap, Object obj, ClassMapper classMap , DbType dbType) {
        typeToClassNameToRepoMap.compute(dbType , new BiFunction<DbType, Map<String, Object>, Map<String, Object>>() {
            @Override
            public Map<String, Object> apply(DbType dbType, Map<String, Object> stringObjectMap) {
                if(Objects.isNull(stringObjectMap)){
                    stringObjectMap = Maps.newHashMap();
                }
                stringObjectMap.put(classMap.getClassRepo().getName(), obj);
                return stringObjectMap;
            }
        });
    }

    public ClassMapper getClassMap(Class<?> beanType){
        if (!beanType.isAnnotationPresent(ClassMapperMetaInfo.class)) {
            log.error("Pls annotate the class with ClassMapperMetaInfo to provide runTimeSwitch metaData");
            throw new RuntimeException("ClassMapperMetaInfo annotation required");
        }

        ClassMapperMetaInfo classBean = beanType.getAnnotation(ClassMapperMetaInfo.class);
        ClassMapper className = classBean.getClassName();
        if(Objects.nonNull(className)){
            return className;
        }
        throw new RuntimeException();
    }


    public <T> T getRepoFile(Class<T> interfaceWhoseObjectToGet){
        if(Objects.nonNull(typeToClassNameToRepoMap.get(currentEnvironmentType)) &&
                Objects.nonNull(typeToClassNameToRepoMap.get(currentEnvironmentType).get(interfaceWhoseObjectToGet.getName()))){
            return (T) typeToClassNameToRepoMap.get(currentEnvironmentType).get(interfaceWhoseObjectToGet.getName());
        }
        throw new RuntimeException();
    }


    public static void runtimeUpdateDbType(DbType dbType){
        if(Objects.nonNull(dbType)){
            currentEnvironmentType = dbType;
        }
    }
}
