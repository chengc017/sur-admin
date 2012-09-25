package com.sohu.sur.test;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.Morphia;
import com.google.code.morphia.logging.MorphiaLoggerFactory;
import com.google.code.morphia.logging.slf4j.SLF4JLogrImplFactory;
import com.mongodb.Mongo;

/**
 * User: 郭勇
 * Date: 2011-3-10 17:32:56
 */
@Deprecated
public class MongoDBHelper {

    private static MongoDBHelper helper;

    private Morphia morphia;
    private Mongo mongo;
    private Datastore datastore;

    final String dbName = "sur-dev";

    private MongoDBHelper() throws Exception {

        MorphiaLoggerFactory.registerLogger(SLF4JLogrImplFactory.class);
        morphia = new Morphia();

        String mongoDB = "10.1.53.155";
//        String mongoDB = "192.168.106.61";
        mongo = new Mongo(mongoDB);

        cleanDB();

        morphia.mapPackage("com.sohu.sur.model");

        datastore = morphia.createDatastore(mongo, dbName);
        datastore.ensureIndexes();
    }

    public Morphia getMorphia() {
        return morphia;
    }

    public Mongo getMongo() {
        return mongo;
    }

    public Datastore getDatastore() {
        return datastore;
    }

    public static MongoDBHelper getInstance() throws Exception {
        if (helper == null) {
            helper = new MongoDBHelper();
        }
        return helper;
    }

    public void cleanDB() {
        this.mongo.dropDatabase(dbName);
    }
}
