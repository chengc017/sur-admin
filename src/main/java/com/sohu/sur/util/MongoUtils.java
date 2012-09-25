package com.sohu.sur.util;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mongodb.Mongo;
import com.mongodb.MongoOptions;
import com.mongodb.ServerAddress;
import com.sohu.sur.cache.exception.MongoServerConfigException;

public class MongoUtils {
	private static final Logger logger=LoggerFactory.getLogger(MongoUtils.class);
	public static Map<String,Mongo> getMongoServerMap(
		   String dbHosts,String dbPorts,MongoOptions options,String replicaSetSeeds,boolean slaveOk
			) throws Exception{
		Map<String,Mongo> serverMap=new HashMap<String,Mongo>();
		if(!StringUtils.isEmpty(dbPorts)&&!StringUtils.isEmpty(dbHosts)&&!StringUtils.isEmpty(replicaSetSeeds)){
			String[] dbHostsArray=dbHosts.split("#");
			String[] dbPortsArray=dbPorts.split("#");
			String[] replicaSetSeedsArray=replicaSetSeeds.split("#");
			if(dbHostsArray.length==dbPortsArray.length&&dbPortsArray.length>0){
				int	hostSize=dbHostsArray.length;
				for(int i=0;i<hostSize;i++){
					
					Mongo mongo=getMongo(dbHostsArray[i],dbPortsArray[i],options,replicaSetSeedsArray[i],slaveOk);
					serverMap.put(dbHostsArray[i], mongo);
				}
			}else{
				if(logger.isDebugEnabled()){
					logger.debug("the config error the sur.mongodb.hosts size not == sur.mongodb.ports!");

				}
				throw new MongoServerConfigException("the config error the sur.mongodb.hosts size not == sur.mongodb.ports!");
			
			}
		}else{
			throw new MongoServerConfigException("the config error ");
			
		}
		
		
	
		return serverMap;
	}
	private static Mongo getMongo(String dbHost, String dbPort,MongoOptions options, String replicaSetSeeds, boolean slaveOk) throws MongoServerConfigException, UnknownHostException {
		int dbPortInt;
		if(dbPort!=null&&!dbPort.equals("")){
			dbPortInt=Integer.parseInt(dbPort);
		}else{
			throw new MongoServerConfigException("the config error ,the dbport is empty!");
		}
		
		ServerAddress address = new ServerAddress(dbHost, dbPortInt);

		
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
}
