package com.sohu.sur.model;

import org.junit.Assert;
import org.junit.Test;

public class EnumTest {
	@Test
	public void testEnum(){
		System.out.println(BonusCalculationMethod.valueOf(1));
		System.out.println(BonusCalculationMethod.valueOf(10));
		System.out.println(BonusCalculationMethod.valueOf(220));
		System.out.println(BonusCalculationMethod.valueOf(-1));
        try {
            System.out.println("with empty string");
		    System.out.println(BonusCalculationMethod.valueOf(""));
            Assert.fail();
        } catch (Exception ignore) {

        }
        try {
            System.out.println("with null string");
		    System.out.println(BonusCalculationMethod.valueOf(null));
            Assert.fail();
        } catch (Exception ignore) {

        }

	}
}
