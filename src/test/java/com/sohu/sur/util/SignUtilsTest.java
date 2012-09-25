package com.sohu.sur.util;

import org.junit.Test;


public class SignUtilsTest {
	@Test
	public void testSign(){
		String uid = "sur-test0@sohu.com";
		String actionCode = "TRK";
		String productCode = "d58";
		String validationCode = "me50dC";
		String actionValidationCode = "ivAHVK";
		System.out.println(SignUtils.generateSign(new StringBuffer(uid).append(productCode), validationCode));
		
		System.out.println(SignUtils.generateSign(new StringBuffer(uid).append(actionCode).append(0), actionValidationCode));
		
		
	}
}
