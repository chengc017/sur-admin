package com.sohu.sur.spring;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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


public class DatastoreServerMapFactoryBean implements FactoryBean<Map<String,Datastore>>, InitializingBean {

    private final static Logger logger = LoggerFactory.getLogger(DatastoreServerMapFactoryBean.class);

    private Map<String,Mongo> serverMongoMap;

    private String dbName;
    private String user;
    private char[] password;
    private String mappingPackage;

    @Override
    public Map<String,Datastore> getObject() throws Exception {
    	
        Assert.notNull(serverMongoMap);
        Assert.notNull(mappingPackage);
        Assert.notNull(dbName);
        
        try {
            MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
        } catch (RuntimeException ignore) {
            // ignore it.    
        }
        Map<String,Datastore> serverDataSoureMap=new HashMap<String,Datastore>();
        for(Entry<String, Mongo> serverMongo:serverMongoMap.entrySet()){
        	Mongo mongo=serverMongo.getValue();
        	 Morphia morphia = new Morphia();
             morphia.mapPackage(mappingPackage);

             Datastore datastore = morphia.createDatastore(mongo, dbName, user, password);
             datastore.ensureIndexes();
             serverDataSoureMap.put(serverMongo.getKey(), datastore);
    	}
        

       
        return serverDataSoureMap;
    }

    @Override
    public Class<? extends Map> getObjectType() {
        return Map.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Required
    public void setServerMongoMap(Map<String,Mongo> serverMongoMap) {
        this.serverMongoMap = serverMongoMap;
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
        logger.info("Initializing Morphia Map<String,Datastore>.");

    }
}
