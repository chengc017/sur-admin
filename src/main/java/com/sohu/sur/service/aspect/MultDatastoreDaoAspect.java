package com.sohu.sur.service.aspect;

import java.util.Map;

import javax.xml.crypto.Data;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.google.code.morphia.Datastore;

@Aspect
public class MultDatastoreDaoAspect {
	private final static Logger logger = LoggerFactory.getLogger(MultDatastoreDaoAspect.class);
	@Autowired
	private Map<String,Datastore> serverDatastoreMap;

	public MultDatastoreDaoAspect(){
		
	}

	/**
	 * 
	 * @param pjp
	 * @return
	 */
	 @Around("execution(* com.sohu.sur.dao.impl.AccountDaoMorphiaImpl.*(..)) &&"
	            + " args(uid,..)")
	public Object setDatastore(ProceedingJoinPoint pjp) {
		 Object obj=null;
		 try {
			obj=pjp.proceed();
			logger.info("<----------------------------------------------->");
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 return obj;
	}
}
