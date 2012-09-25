package com.sohu.sur.service;

import java.util.List;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import com.sohu.sur.model.MallItem;
import com.sohu.sur.test.SpringTests;

public class MallItemServiceTest extends SpringTests {
	private MallItemService mallItemService;

	@Before
	public void setUp() throws Exception {
		mallItemService = (MallItemService) ctx.getBean("mallItemService");
	}

	@Test
	public void testGetAllMallItem() {
		List<MallItem> mallItems = mallItemService.getAllMallItem();
		Assert.assertTrue(mallItems.size() > 0);
		for (MallItem mallItem : mallItems) {
			System.out.println(mallItem.getClass() + "##"
					+ mallItem.getItemType() + "##" + mallItem.getMarketValue()
					+ "##" + mallItem.getShowValue());
		}

	}

}
