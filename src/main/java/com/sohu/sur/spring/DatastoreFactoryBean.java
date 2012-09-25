package com.sohu.sur.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.util.Assert;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.mongodb.Mongo;

/**
 * User: 郭勇
 * Date: 2011-3-14 15:56:28
 */
public class DatastoreFactoryBean implements FactoryBean<Datastore>, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(DatastoreFactoryBean.class);

    private Mongo mongo;

    private String dbName;
    private String user;
    private char[] password;
    private String mappingPackage;

    @Override
    public Datastore getObject() throws Exception {

        Assert.notNull(mongo);
        Assert.notNull(mappingPackage);
        Assert.notNull(dbName);

        try {
            MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
        } catch (RuntimeException ignore) {
            // ignore it.    
        }

        Morphia morphia = new Morphia();
        morphia.mapPackage(mappingPackage);

        Datastore datastore = morphia.createDatastore(mongo, dbName, user, password);
        datastore.ensureIndexes();
        return datastore;
    }

    @Override
    public Class<? extends Datastore> getObjectType() {
        return Datastore.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Required
    public void setMongo(Mongo mongo) {
        this.mongo = mongo;
    }

    @Required
    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    @Required
    public void setMappingPackage(String mappingPackage) {
        this.mappingPackage = mappingPackage;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        logger.info("Initializing Morphia Datastore.");

    }
}
