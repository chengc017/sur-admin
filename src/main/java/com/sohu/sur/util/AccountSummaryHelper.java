package com.sohu.sur.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AllProductMedalStars;
import com.sohu.sur.model.MallItem;
import com.sohu.sur.model.Medal;
import com.sohu.sur.model.Product;
import com.sohu.sur.model.ProductMedalStars;

public class AccountSummaryHelper {
	/**
	 * 从所有产品中选择指定产品
	 * 
	 * @param products
	 * @param productId
	 * @return
	 */
	public static final Product getProduct(List<Product> products,
			ObjectId productId) {
		if (null != products) {
			for (Product product : products) {
				if (product.getId().equals(productId)) {
					return product;
				}
			}
		}
		return null;
	}

	/**
	 * 从所有名人堂数据中选择指定产品的排行
	 * 
	 * @param allProductMedalStars
	 * @param productCode
	 * @return
	 */
	public static final ProductMedalStars getProductMedalStars(
			AllProductMedalStars allProductMedalStars, String productCode) {
		if (null != allProductMedalStars) {
			List<ProductMedalStars> productMedalStarsList = allProductMedalStars
					.getProductMedalStarsList();
			for (ProductMedalStars productMedalStars : productMedalStarsList) {
				if (productMedalStars.getProductCode().equals(productCode)) {
					return productMedalStars;
				}
			}
		}
		return null;
	}

	/**
	 * 取得指定用户的勋章状态，medals[0]：当前勋章；medals[1]：下一级勋章
	 * 
	 * @param medals
	 * @param activity
	 * @return
	 */
	public static final Medal[] getEarnedMedals(Set<Medal> medals,
			AccountActivity activity) {
		Medal[] bothMedals = new Medal[2];
		if (null != medals && null != activity) {
			for (Medal medal : medals) {
				int activeDays = activity.getContActiveDays();
				if (activeDays >= medal.getMinActiveDays()) {
					if (null == bothMedals[0]
							|| bothMedals[0].getMinActiveDays() < medal
									.getMinActiveDays()) {
						bothMedals[0] = medal;
					}
				} else {
					if (null == bothMedals[1]
							|| bothMedals[1].getMinActiveDays() > medal
									.getMinActiveDays()) {
						bothMedals[1] = medal;
					}
				}
			}
		}
		return bothMedals;
	}

	/**
	 * 根据勋章代码获取对应的商品信息
	 * 
	 * @param allMallItems
	 * @param medalCode
	 * @return
	 */
	public static final List<MallItem> getMallItemsByMedalCode(
			List<MallItem> allMallItems, String medalCode, Set<Medal> medals) {
		List<MallItem> mallItems = new LinkedList<MallItem>();
		if (null != allMallItems) {
			List<String> lowerMedalCodes = new LinkedList<String>();
			for (Medal medal : medals) {
				if (medalCode == null
						|| medal.getCode().compareTo(medalCode) <= 0) {
					lowerMedalCodes.add(medal.getCode());
				}
			}
			Collections.sort(lowerMedalCodes, new Comparator<String>() {
				@Override
				public int compare(String o1, String o2) {
					return -o1.compareTo(o2);
				}
			});
			for (String tmpMedalCode : lowerMedalCodes) {
				for (MallItem mallItem : allMallItems) {
					if (null != mallItem.getMedals()
							&& ("," + mallItem.getMedals() + ",").contains(","
									+ tmpMedalCode + ",")) {
						if (!mallItems.contains(mallItem)) {
							mallItems.add(mallItem);
						}
					}
				}
			}
			for (MallItem mallItem : allMallItems) {
				if (StringUtils.isEmpty(mallItem.getMedals())
						&& !mallItems.contains(mallItem)) {
					mallItems.add(mallItem);
				}
			}
		}

		return mallItems;
	}

	/**
	 * 根据产品id获得用户对应 的活动值数据
	 * 
	 * @param accountActivities
	 * @param productId
	 * @return
	 */
	public static final AccountActivity findAccountActivityByProductId(
			List<AccountActivity> accountActivities, ObjectId productId) {
		if (null != accountActivities) {
			for (AccountActivity accountActivity : accountActivities) {
				if (accountActivity.getProductId().equals(productId)) {
					return accountActivity;
				}
			}
		}
		return null;
	}

	/**
	 * 按产品代码将勋章分组
	 * 
	 * @param allMedals
	 * @return
	 */
	public static final Map<String, Set<Medal>> getMedalMap(
			List<Medal> allMedals) {
		Map<String, Set<Medal>> medalMap = new HashMap<String, Set<Medal>>();
		for (Medal medal : allMedals) {
			String key = medal.getProductCode();
			Set<Medal> medals = medalMap.get(key);
			if (null == medals) {
				medals = new HashSet<Medal>();
				medalMap.put(key, medals);
			}
			medals.add(medal);
		}
		return medalMap;
	}

	/**
	 * 取得用户以获取的勋章
	 * 
	 * @param allMedals
	 * @param activities
	 * @return
	 */
	public static List<Medal> getEarnedMedals(List<Medal> allMedals,
			List<AccountActivity> activities) {
		List<Medal> earnedMedals = new LinkedList<Medal>();
		for (AccountActivity activity : activities) {
			for (Medal medal : allMedals) {
				if (medal.getForProduct().equals(activity.getProductId())
						&& activity.getContActiveDays() >= medal
								.getMinActiveDays()
						&& activity.getContActiveDays() <= medal
								.getMaxActiveDays()) {
					earnedMedals.add(medal);
				}
			}
		}
		return earnedMedals;
	}
}
