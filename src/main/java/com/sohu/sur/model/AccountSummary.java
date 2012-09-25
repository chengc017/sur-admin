package com.sohu.sur.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.sohu.sur.util.AccountSummaryHelper;

/**
 * 用户概况信息，包含等级、升级所需经验值、已获得的勋章、收到的虚拟商品
 * @author xuewuhao
 *
 */
public class AccountSummary implements Serializable{

	private Account account;

	private List<MedalExtend> totalMedalExtends = new ArrayList<MedalExtend>();
	private List<MedalExtend> earnedMedalExtends = new ArrayList<MedalExtend>();
	private List<MedalExtend> unEarnedMedalExtends = new ArrayList<MedalExtend>();
	private List<AccountVirtual> receiveAccountVirtuals = new ArrayList<AccountVirtual>();
	private String virtualInfo = "暂无礼物！";
	private Rank rank;

	public AccountSummary(Account account, List<Rank> allRanks,
			List<Product> allProducts, List<AccountActivity> activities,
			List<Medal> allMedals, AllProductMedalStars allProductMedalStars,
			List<MallItem> allMallItems,List<AccountVirtual> receiveAccountVirtuals) {

		this.account = account;

		int exp = account.getExp();

		for (Rank r : allRanks) {
			if (exp >= r.getMinQualifiedExp() && exp <= r.getMaxQualifiedExp()) {
				this.rank = r;
				break;
			}
		}

		Map<String, Set<Medal>> medalMap = AccountSummaryHelper
				.getMedalMap(allMedals);

		for (Product product : allProducts) {
			MedalExtend medalExtend = new MedalExtend();
			medalExtend.setProduct(product);
			Set<Medal> medals = medalMap.get(product.getCode());
			if (null == medals) {
				continue;
			}
			AccountActivity activity = AccountSummaryHelper
					.findAccountActivityByProductId(activities, product.getId());
			if (null == activity) {
				activity = new AccountActivity();
				activity.setProductId(product.getId());
				activity.setProductCode(product.getCode());
			}
			medalExtend.setAccountActivity(activity);
			Medal[] bothMedals = AccountSummaryHelper.getEarnedMedals(medals,
					activity);
			medalExtend.setCurMedal(bothMedals[0]);
			medalExtend.setNextMedal(bothMedals[1]);
			medalExtend.setProductMedalStars(AccountSummaryHelper
					.getProductMedalStars(allProductMedalStars,
							product.getCode()));
			if (null != bothMedals[0]) {
				medalExtend.setMallItems(AccountSummaryHelper
						.getMallItemsByMedalCode(allMallItems,
								bothMedals[0].getCode(), medals));
				this.earnedMedalExtends.add(medalExtend);
			} else {
				medalExtend.setMallItems(AccountSummaryHelper
						.getMallItemsByMedalCode(allMallItems, null, medals));
				this.unEarnedMedalExtends.add(medalExtend);
			}
			this.totalMedalExtends.add(medalExtend);
		}
		//虚拟商品
		this.receiveAccountVirtuals = receiveAccountVirtuals; 
		
	}

	/**
	 * 增加虚拟商品处理
	 */
	public AccountSummary(Account account, List<Rank> allRanks,
			List<Product> allProducts, List<AccountActivity> activities,
			List<Medal> allMedals,List<AccountVirtual> lav) {

		this.account = account;

		int exp = account.getExp();

		for (Rank r : allRanks) {
			if (exp >= r.getMinQualifiedExp() && exp <= r.getMaxQualifiedExp()) {
				this.rank = r;
				break;
			}
		}

		Map<String, Set<Medal>> medalMap = AccountSummaryHelper
				.getMedalMap(allMedals);

		for (Product product : allProducts) {
			MedalExtend medalExtend = new MedalExtend();
			medalExtend.setProduct(product);
			Set<Medal> medals = medalMap.get(product.getCode());
			if (null == medals) {
				continue;
			}
			AccountActivity activity = AccountSummaryHelper
					.findAccountActivityByProductId(activities, product.getId());
			if (null == activity) {
				activity = new AccountActivity();
				activity.setProductId(product.getId());
				activity.setProductCode(product.getCode());
			}
			medalExtend.setAccountActivity(activity);
			Medal[] bothMedals = AccountSummaryHelper.getEarnedMedals(medals,
					activity);
			medalExtend.setCurMedal(bothMedals[0]);
			medalExtend.setNextMedal(bothMedals[1]);
			if (null != bothMedals[0]) {
				this.earnedMedalExtends.add(medalExtend);
			} else {
				this.unEarnedMedalExtends.add(medalExtend);
			}
			this.totalMedalExtends.add(medalExtend);
		}
		//虚拟商品
		if (lav != null && lav.size() > 0) {
			int meili_value = 0;
			int receive_num = 0;
			for (AccountVirtual av : lav) {
				meili_value += av.getCoinSum();
				receive_num += av.getNum();
			}
			virtualInfo = receive_num+"个礼物  魅力"+meili_value;
		}
	}

	public Account getAccount() {
		return account;
	}

	public List<MedalExtend> getTotalMedalExtends() {
		return totalMedalExtends;
	}

	public List<MedalExtend> getEarnedMedalExtends() {
		return earnedMedalExtends;
	}

	public List<MedalExtend> getUnEarnedMedalExtends() {
		return unEarnedMedalExtends;
	}

	public String getVirtualInfo() {
		return virtualInfo;
	}

	public Rank getRank() {
		return rank;
	}

	public List<AccountVirtual> getReceiveAccountVirtuals() {
		return receiveAccountVirtuals;
	}
}
