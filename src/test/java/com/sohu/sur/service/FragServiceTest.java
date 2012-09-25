package com.sohu.sur.service;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.sohu.sur.test.SpringTests;

public class FragServiceTest extends SpringTests {
	private FragService remoteFragService;

	@Before
	public void setUp() throws Exception {
		remoteFragService = (FragService) ctx.getBean("remoteFragService");
	}

	@Test
	public void testGetFragByKey() {
		String frag = remoteFragService.getFragByKey("http://gift.sohu.com/service/product/jifen-recommend.html");
		System.out.println(frag);
	}

}
