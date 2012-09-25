package com.sohu.sur.spring;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.google.code.morphia.Datastore;
import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
@Configuration
@ImportResource("classpath:/sur-domain-config.xml")
public class DataStoreUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(DataStoreUtils.class);
	

	//@Value("#{surAppProperties['sur.mongodb.host']}")
	private String dbHost="10.11.155.18";

	@Value("#{surAppProperties['sur.mongodb.port']}")
	private int dbPort;

	@Value("#{surAppProperties['sur.mongodb.name']}")
	private String dbName = "sur";

	@Value("#{surAppProperties['sur.mongodb.user']}")
	private String user;

	@Value("#{surAppProperties['sur.mongodb.password']}")
	private char[] password;

	//@Value("#{surAppProperties['sur.mongodb.replicaSetSeeds']}")
	private String replicaSetSeeds="10.11.155.18:27017";

	@Value("#{surAppProperties['sur.mongodb.slaveOk']}")
	private boolean slaveOk = false;

	@Value("#{surAppProperties['sur.mongodb.safe']}")
	private boolean safe = true;
	// hxw edit and add
	@Value("#{surAppProperties['sur.mongodb.autoConnectRetry']}")
	private String autoConnectRetry = "true";
	
	@Value("#{surAppProperties['sur.mongodb.maxWaitTime']}")
	private String maxWaitTime = "5000";
	
	@Value("#{surAppProperties['sur.mongodb.threadsAllowedToBlockForConnectionMultiplier']}")
	private String threadsAllowedToBlockForConnectionMultiplier = "5000";
	
	
	@Value("#{surAppProperties['sur.mongodb.connectTimeout']}")
	private String connectTimeout;
	
	@Value("#{surAppProperties['sur.mongodb.socketTimeout']}")
	private String socketTimeout;
	
	@Value("#{surAppProperties['sur.mongodb.connectionsPerHost']}")
	private String connectionsPerHost;

	@Value("#{surAppProperties['sur.mophia.mapping']}")
	private String mappingPackage = "com.sohu.sur.model";

	
	
	@Bean(destroyMethod = "close")
	public Mongo mongo_1() throws UnknownHostException {

		ServerAddress address = new ServerAddress(dbHost, dbPort);

		MongoOptions options = new MongoOptions();
		options.safe = safe;
		options.connectionsPerHost = Integer.parseInt(connectionsPerHost);
		options.autoConnectRetry = Boolean.parseBoolean(autoConnectRetry);
		options.connectTimeout = Integer.parseInt(connectTimeout);
		options.socketTimeout = Integer.parseInt(socketTimeout);
		options.maxWaitTime = Integer.parseInt(maxWaitTime);
		options.threadsAllowedToBlockForConnectionMultiplier = Integer.parseInt(threadsAllowedToBlockForConnectionMultiplier);
		Mongo mongo;

		if (StringUtils.trimToNull(replicaSetSeeds) == null) {

			mongo = new Mongo(address, options);
			logger.info("replicaSetSeeds null");
		} else { // use replica set

			String[] hosts = StringUtils.split(replicaSetSeeds);
			List<ServerAddress> addr = new ArrayList<ServerAddress>();
			for (String host : hosts) {
				addr.add(new ServerAddress(host));
			}

			options.slaveOk = slaveOk;

			mongo = new Mongo(addr, options);
			logger.info("replicaSetSeeds ok");
		}

		logger.info("Mongo options = {}, wc = {}", options.toString(),
				options.getWriteConcern());

		return mongo;
	}

	@Bean
	public Datastore datastore_1() throws Exception {
		
		DatastoreFactoryBean factoryBean = new DatastoreFactoryBean();
		factoryBean.setMongo(mongo_1());
		factoryBean.setDbName(dbName);
		factoryBean.setMappingPackage(mappingPackage);
		factoryBean.setUser(user);
		factoryBean.setPassword(password);

		factoryBean.afterPropertiesSet();

		return factoryBean.getObject();
	}

	
}
