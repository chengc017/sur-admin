package com.sohu.sur.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.code.morphia.Datastore;
import com.google.code.morphia.DatastoreImpl;
import com.google.code.morphia.Key;
import com.google.code.morphia.dao.BasicDAO;
import com.google.code.morphia.query.Query;
import com.google.code.morphia.query.UpdateOperations;
import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.model.Account;
import com.sohu.sur.model.BonusChangeType;
import com.sohu.sur.util.ConsistentHash;
import com.sohu.sur.util.Page;

/**
 * User: 郭勇
 * Date: 2011-3-9 16:43:46
 */
public class AccountDaoMorphiaImpl extends BasicDAO<Account, ObjectId> implements AccountDao {
	private static final Logger logger=LoggerFactory.getLogger(AccountDaoMorphiaImpl.class);
	private ConsistentHash consistentHash;
	private Map<String,Datastore> serverDatastoreMap;
	private String ip;
    public AccountDaoMorphiaImpl(Datastore ds,ConsistentHash consistentHash,Map<String,Datastore> serverDatastoreMap) {
        super(ds);
       this.consistentHash=consistentHash;
       this.serverDatastoreMap=serverDatastoreMap;
    }
    private void setDatastore(Datastore ds){
    	super.ds=(DatastoreImpl) ds;
    }
    public void changeDataStore(String uid){
    	Datastore ds=null;
    	ip=consistentHash.getValue(uid);
    	String address=super.ds.getMongo().getAddress().getHost();
    	if(ip!=null&&!ip.equals("")&&!address.equals(ip)){
    		ds=serverDatastoreMap.get(ip);
    		if(ds!=null){
    			
    			setDatastore(ds);
    			logger.info("the dataSoure changed:from [{}] to [{}]",address,ip);
    		}
    	}
    	logger.info("the uid:{} is first select from ip(mongodb):{}",uid,ip);
    	
    }
    
    @Override
    public Account findAccountByUid(String uid) {
    	long startAll=System.nanoTime();
    	changeDataStore(uid);
    	Account account=this.findOne("uid", uid);
    	if(account==null){
    		for(Entry<String, Datastore> datastoreMap:serverDatastoreMap.entrySet()){
        		setDatastore(datastoreMap.getValue());
        		account=this.findOne("uid", uid);
        		if(account!=null){
        			setDatastore(serverDatastoreMap.get(ip));
        			long startSave=System.nanoTime();
        			saveAccount(account);
        			long spentSaveTime=System.nanoTime()-startSave;
        			logger.info("the ip {} save {}",ip,spentSaveTime);
        			logger.info("server ip : {} save {} success!",ip,account.getUid());
        			
        			long startDelete=System.nanoTime();
        			setDatastore(datastoreMap.getValue());
        			delete(account);
        			long spentDeleteTime=System.nanoTime()-startDelete;
        			logger.info("the ip {} del {}",datastoreMap.getKey(),spentDeleteTime);
        			logger.info("server ip : {} delete {} success!",datastoreMap.getKey(),account.getUid());
        			
        		}
        	}
    	}
    	long spentTime=System.nanoTime()-startAll;
    	logger.info("the find {} all spend {}\n\n\n",uid,spentTime/1000000);
        return account;
    }
    public void saveAccount(Account account){
    	this.save(account);
    }
    public void delAccount(Account account){
    	this.deleteById(account.getId());
    }
    @Override
    public void updateExp(Account acct) {
    	changeDataStore(acct.getUid());
        acct.setLastUpdateTime(new Date());
        this.save(acct);
    }

    @Override
    public int getTotalBonus(String uid, BonusChangeType changeType) {
    	changeDataStore(uid);
        Account acct = this.findAccountByUid(uid);

        return acct.findBonusTotal(changeType).getTotal();

    }

    @Override
    public void banAccount(String uid) {
    	changeDataStore(uid);
        Query<Account> query = this.createQuery().field("uid").equal(uid);
        UpdateOperations<Account> updateOperations = this.createUpdateOperations().set("banned", true);
        this.update(query, updateOperations);
    }

    @Override
    public void unBanAccount(String uid) {
    	changeDataStore(uid);
        Query<Account> query = this.createQuery().field("uid").equal(uid);
        UpdateOperations<Account> updateOperations = this.createUpdateOperations().set("banned", false);
        this.update(query, updateOperations);
    }

    @Override
    public List<Account> findBannedAccount(Page page) {
    	
    	 page.setCount(this.createQuery().field("banned").equal(true).countAll());
         Query<Account> query = this.createQuery().field("banned").equal(true)
                 .offset(page.getStart()).limit(page.getSize());
    	
       
       
        return this.find(query).asList();
    }

    @Override
    public void validAccount(String uid, boolean isSpace) {
    	changeDataStore(uid);
        Query<Account> query = this.createQuery().field("uid").equal(uid);
        UpdateOperations<Account> updateOperations = this.createUpdateOperations().set("is_space", isSpace);
        this.update(query, updateOperations);
    }

    @Override
    public List<Account> findUnValidateAccount(int skip, int limit) {
        Query<Account> query = this.createQuery().field("is_space").equal(false)
                .field("banned").equal(false)
                .offset(skip).limit(limit);
        return this.find(query).asList();
    }
    @Override
    public List<Account> findAccount(int bonus) {
    	
    	
       
        
        int serverSize=serverDatastoreMap.size();
        List<Account> list=new ArrayList<Account>();
        List<Account> eveyServerAcounts;
    	for(Entry<String, Datastore> datastoreMap:serverDatastoreMap.entrySet()){
    		setDatastore(datastoreMap.getValue());
    		 Query<Account> query = this.createQuery().filter(" bonus >=", bonus);
    		eveyServerAcounts=this.find(query).asList();
    		logger.info("change datastore to ip:{}",datastoreMap.getValue().getMongo().getAddress().getHost());
    		logger.info("the result size is: {}",eveyServerAcounts.size());
    		list.addAll(eveyServerAcounts);
    	}
       
        return list;
    }
    public List<Account> findAccount(Page page) {
    	setDatastore(serverDatastoreMap.get("10.11.155.18"));
    	page.setCount(this.createQuery().countAll());
        Query<Account> query = this.createQuery()
                .offset(page.getStart()).limit(page.getSize());
   	
      
      
       return this.find(query).asList();
   }

	
}
