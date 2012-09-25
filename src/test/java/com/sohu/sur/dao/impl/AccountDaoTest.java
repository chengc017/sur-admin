package com.sohu.sur.dao.impl;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.sohu.sur.dao.AccountDao;
import com.sohu.sur.dao.VirtualItemDao;
import com.sohu.sur.dao.VirtualOverviewDao;
import com.sohu.sur.spring.SurDomainConfig;

public class AccountDaoTest {
	public static void main(String[] args){
		AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(SurDomainConfig.class);
		AccountDao accountDao = (AccountDao) ctx.getBean("actionDao");
		System.out.println(accountDao);
		
	}
}
