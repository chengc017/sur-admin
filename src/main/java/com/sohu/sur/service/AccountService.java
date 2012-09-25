package com.sohu.sur.service;

import java.util.List;

import com.sohu.sur.model.Account;
import com.sohu.sur.model.AccountActivity;
import com.sohu.sur.model.AccountSummary;
import com.sohu.sur.model.AllProductMedalStars;
import com.sohu.sur.model.DailyEarnedBonus;
import com.sohu.sur.model.Medal;
import com.sohu.sur.model.Product;
import com.sohu.sur.model.Rank;
import com.sohu.sur.model.User;
import com.sohu.sur.model.exception.AccountNotFoundException;
import com.sohu.sur.model.exception.InsufficientBonusException;
import com.sohu.sur.util.Page;
import com.sohu.sur.util.po.UserMedalRet;

/**
 * 用户经验值/勋章概况服务 User: guoyong Date: 11-3-17 下午4:43
 */
public interface AccountService {
	
	
	/**
	 * 批量获取用户已有勋章
	 * @param allMedal
	 * @param uids
	 * @return
	 */
	@Deprecated
	public UserMedalRet batchGetUserMedals(List<Medal> allMedal,List<String> uids);
	
	/**
	 * 批量获取用户已有勋章
	 * @param allMedal
	 * @param uids
	 * @return
	 */
	public UserMedalRet batchGetUserMedals(List<Medal> allMedal,String[] uids);
	/**
	 * 去用户的account信息
	 * 
	 * @param uid
	 * @return
	 */
	Account findAccount(String uid);


	/**
	 * 根据用户uid/passport获取经验值/勋章概况
	 * 
	 * @param uid
	 *            用户uid/passport
	 * @return AccountSummary实例
	 * @throws AccountNotFoundException
	 *             uid对应的用户不存在
	 */
	AccountSummary getSummaryWithMedals(String uid) throws AccountNotFoundException;

	/**
	 * 获取用户当前积分
	 * 
	 * @param uid
	 *            用户uid/passport
	 * @return 当前积分 用户不存在返回-1
	 * @throws AccountNotFoundException
	 *             uid对应的用户不存在
	 */
	int getAccountBonus(String uid) throws AccountNotFoundException;

	/**
	 * 检查用户的当前积分是否足够进行兑换
	 * 
	 * @param uid
	 *            用户uid/passport
	 * @param bonus
	 *            要使用的积分值
	 * @return true 可以进行兑换 false 不足以进行兑换
	 * @throws AccountNotFoundException
	 *             uid对应的用户不存在
	 */
	boolean checkBonus(String uid, int bonus) throws AccountNotFoundException;

	/**
	 * 检查用户是否有符合条件的勋章。 如果兑换需要的勋章列表为空， 则从用户已有勋章中选择折扣最高的。
	 * 
	 * @param uid
	 *            用户uid/passport
	 * @param bonus
	 *            要使用的积分值
	 * @param medalCodes
	 *            兑换需要的勋章列表
	 * @param allMedals
	 *            勋章列表           
	 * @return 兑换将要使用的勋章
	 * @throws InsufficientBonusException
	 *             当前积分不够进行兑换
	 */
	Medal checkBonusAndMedal(String uid, int bonus, String[] medalCodes,List<Medal> allMedals) throws AccountNotFoundException,
			InsufficientBonusException;

	/**
	 * 查找某个产品的用户活跃记录排行，按活跃值倒序排列。 如果符合最大活跃值的记录数量大于<code>limit</code>，
	 * 则取全部符合最大活跃值的记录。
	 * 
	 * @param product
	 *            产品
	 * @param limit
	 *            top n
	 * @return 用户活跃记录排行
	 */
	List<AccountActivity> findAccountActivitiesByProduct(Product product, int limit);

	/**
	 * 为用户创建积分账号
	 * 
	 * @param uid
	 * @return
	 */
	void createAccount(String uid);

	/**
	 * 查找指定用户当日获取的基本数量
	 * 
	 * @param uid
	 * @return
	 */
	DailyEarnedBonus getDailyEarnedBonus(String uid) throws AccountNotFoundException;

	List<AccountActivity> findAccountActivitiesByMedal(String medalCode, Page page);

	List<AccountActivity> findAccountActivitiesByMedal(String medalCode);

	void banAccount(String uid);

	void unBanAccount(String uid);

	List<Account> findBannedAccounts(Page page);

	void validAccount(String uid, User user);

	List<Account> findUnValidateAccount(int pageNo, int pageSize);

	public AccountSummary getSummary2(String uid, List<Medal> allMedals, List<Rank> allRanks, List<Product> allProducts,AllProductMedalStars allProductMedalStars)
			throws AccountNotFoundException;
	public AccountSummary getSummary3(String uid, List<Medal> allMedals, List<Rank> allRanks, List<Product> allProducts)throws AccountNotFoundException;
}
