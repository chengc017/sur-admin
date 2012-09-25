package com.sohu.sur.spring;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import com.google.code.morphia.Datastore;
import com.sohu.sur.util.ConsistentHash;

public class ConsistentHashFactoryBean implements FactoryBean<ConsistentHash>, InitializingBean  {
	private static final Logger logger=LoggerFactory.getLogger(ConsistentHash.class);
	private String[] servers;
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info("Initializing the ConsistenHash \n");
		
	}

	@Override
	public ConsistentHash getObject() throws Exception {
		ConsistentHash consistentHash=new ConsistentHash();
		consistentHash.setServers(servers);
		consistentHash.init();
		return consistentHash;
	}

	@Override
	public Class<?extends ConsistentHash> getObjectType() {
		
		return ConsistentHash.class;
	}

	@Override
	public boolean isSingleton() {
	
		return true;
	}

	public void setServers(String[] servers) {
		this.servers = servers;
	}
	
}
