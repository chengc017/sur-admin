package com.sohu.sur.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
/*
 * 使用时，首先得输入servers[]这个服务器数组
 */


/**
 * @author chengchengbj8545
 *
 */
public class ConsistentHash {
	
	private Logger logger=Logger.getLogger(ConsistentHash.class);
	
	private int extendCount=40;//用于扩展server对应的hash值数，使得一个server可以对应多个hash值，使得访问可以更加均匀
	private ConsistentHash consistentHash=null;
	private String servers[];
	private int serverSize;
	private int serverThreadCount=100;
	
	
	private TreeMap<Long, String> buckets=new  TreeMap<Long, String>();

	public synchronized boolean init(){
		if(servers==null||servers.length<1){
			return false;
		}
		serverSize=servers.length;
		for(int i=0;i<serverSize;i++){
			initServer(servers[i]);
		}
		return true;
	}
	public void initServer(String server){
		long factor=getFactor();
		String serverFactor;
		byte[] serverFactorHash;
		for(long i=0;i<factor;i++){
			serverFactor=server+i;
			putHashInBucket(serverFactor,server);
			
		}
		
	}
	public long getFactor(){
		double weight=(double)extendCount/serverSize;
		return (long)weight;
	}
	public void putHashInBucket(String serverFactor,String server){
		
		try {
			if(serverFactor!=null){
				
				MessageDigest md=MessageDigest.getInstance("md5");
				byte[] hashValue= md.digest(serverFactor.getBytes());
				for(int i=0;i<4;i++){
					long key=((long)(hashValue[i*4+0]&0XFF)<<24)|
					((long)(hashValue[i*4+1]&0XFF)<<16)|
					((long)(hashValue[i*4+2]&0XFF)<<8)|
					((long)(hashValue[i*4+3]&0XFF));
					buckets.put(key, server);
				}
				
			}
			
		} catch (NoSuchAlgorithmException e) {
			
			logger.info("无法得到hash算法");
		}
		
	}

	public String getValue(String key){
		Long hashKey=getHashKey(key);
		Long hashKeyServer=buckets.ceilingKey(hashKey);
		if(hashKeyServer==null){
			hashKeyServer=buckets.firstKey();
		}
		String server=buckets.get(hashKeyServer);
		
		
		
		return server;
	}

	public long getHashKey(String key){
		long result=0;
		if(key!=null&&!key.equals("")){
			try {
				MessageDigest md5=MessageDigest.getInstance("md5");
				md5.reset();
				md5.update(key.getBytes());
				byte[] hashKey=md5.digest();
				result=(((long)hashKey[3]&0xff)<<24)|
						((long)(hashKey[2]&0xff)<<16)|
						((long)(hashKey[1]&0xff)<<8)|
						((long)(hashKey[2]&0xff));
			} catch (NoSuchAlgorithmException e) {
				logger.info("无法得到hash算法");
			}
		}
		return result;
	}
	



	public ConsistentHash getConsistentHash() {
		return consistentHash;
	}
	public void setConsistentHash(ConsistentHash consistentHash) {
		this.consistentHash = consistentHash;
	}
	public String[] getServers() {
		return servers;
	}
	public void setServers(String[] servers) {
		this.servers = servers;
	}
	public int getServerThreadCount() {
		return serverThreadCount;
	}
	public void setServerThreadCount(int serverThreadCount) {
		this.serverThreadCount = serverThreadCount;
	}

	public TreeMap<Long, String> getBuckets() {
		return buckets;
	}
	public void setBuckets(TreeMap<Long, String> buckets) {
		this.buckets = buckets;
	}
	
}
