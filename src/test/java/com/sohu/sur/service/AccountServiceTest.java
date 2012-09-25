package com.sohu.sur.service;

import org.junit.Before;
import org.junit.Test;

import com.sohu.sur.model.Account;
import com.sohu.sur.test.SpringTests;

/**
 * AccountService相关接口测试
 * @author xuewuhao
 *
 */
public class AccountServiceTest extends SpringTests {
	private AccountService accountService;

	@Before
	public void setUp() throws Exception {
		System.out.println("1");
		accountService = (AccountService) ctx.getBean("accountSummaryService");
	}

	@Test
	public void testFindAccountByUid() {
		System.out.println("2");
		Account acount = accountService.findAccount("haoxw@sohu.com");
			System.out.println(acount.toString());
	}
}
