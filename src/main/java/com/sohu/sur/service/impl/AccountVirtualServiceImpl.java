package com.sohu.sur.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sohu.suc.platform.utils.BeanUtils;
import com.sohu.sur.cache.ObjectCache;
import com.sohu.sur.dao.AccountVirtualDao;
import com.sohu.sur.dao.AccountVirtualLogDao;
import com.sohu.sur.model.AccountVirtual;
import com.sohu.sur.model.AccountVirtualLog;
import com.sohu.sur.service.AccountVirtualService;
import com.sohu.sur.util.Page;

/**
 * 虚拟商品接口实现类
 * 
 * @author xuewuhao
 * 
 */
public class AccountVirtualServiceImpl implements AccountVirtualService {
	private static final Logger logger = LoggerFactory.getLogger(AccountVirtualServiceImpl.class);
	private AccountVirtualDao accountVirtualDao;
	private AccountVirtualLogDao accountVirtualLogDao;

	public AccountVirtualServiceImpl() {

	}

	public AccountVirtualServiceImpl(AccountVirtualDao accountVirtualDao, AccountVirtualLogDao accountVirtualLogDao) {
		this.accountVirtualDao = accountVirtualDao;
		this.accountVirtualLogDao = accountVirtualLogDao;
	}

	
	public AccountVirtual findAccountVirtualGift(int  giftId){
		return accountVirtualDao.findAccountVirtualOneGift(giftId);
	}
	
	@Override
	public List<AccountVirtual> virtualList(int actType, String owner) {
		// TODO Auto-generated method stub
		List<AccountVirtual> lav = accountVirtualDao.findAccountVirtualList(owner, actType);
		return lav;
	}

	@Override
	public int incBuyOrGiveAccountVirtual(AccountVirtual av,int numparams) {
		int result = 1;
		// TODO Auto-generated method stub
		try {
			AccountVirtual avTmp = accountVirtualDao.findAccountVirtual(av.getOwner(), av.getActType(),
					av.getGiftId());
			// 不存在则插入
			if (avTmp == null) {
				avTmp = av;
				avTmp.setCdate(new Date());
				avTmp.setCoinSum(avTmp.getCoin());
				avTmp.setNum(numparams);
				accountVirtualDao.insert(avTmp);
			}
			// 更新
			else {
				avTmp.setCdate(new Date());
				avTmp.setCoinSum(avTmp.getCoinSum() + av.getCoin());
				avTmp.setNum(avTmp.getNum() + numparams);
				avTmp.setUrl(av.getUrl());
				avTmp.setLogo(av.getLogo());
				avTmp.setCoin(av.getCoin());
				accountVirtualDao.update(avTmp);
			}
			result = 0;
			logger.info("增加购买/被赠送者虚拟商品数量"+av.getOwner()+"|"+av.getActType()+"|"+av.getGiftId()+"|"+av.getGiftName()+"|"+av.getNum()+"|ok");
		} catch (Exception e) {
			logger.error("incBuyOrGiveAccountVirtual", e);
		}
		return result;
	}

	
	@Override
	public int incGiveOutAccountVirtual(AccountVirtual avFrom) {
		int result = 1;
		// TODO Auto-generated method stub
		try {
			AccountVirtual avTmp = accountVirtualDao.findAccountVirtual(avFrom.getOwner(), avFrom.getActType(),
					avFrom.getGiftId());
			// 不存在则插入
			if (avTmp == null) {
				avTmp = avFrom;
				avTmp.setCdate(new Date());
				avTmp.setCoinSum(avTmp.getCoin());
				avTmp.setNum(1);
				accountVirtualDao.insert(avTmp);
			}
			// 更新
			else {
				avTmp.setCdate(new Date());
				avTmp.setCoinSum(avTmp.getCoinSum() + avTmp.getCoin());
				avTmp.setNum(avTmp.getNum() + avFrom.getNum());
				avTmp.setUrl(avFrom.getUrl());
				accountVirtualDao.update(avTmp);
			}
			result = 0;
			logger.info("增加赠送者送出虚拟商品数量"+avFrom.getOwner()+"|"+avFrom.getActType()+"|"+avFrom.getGiftId()+"|"+avFrom.getGiftName()+"|"+avFrom.getNum()+"|ok");
		} catch (Exception e) {
			logger.error("incGiveOutAccountVirtual", e);
		}
		return result;
	}
	
	@Override
	public int decreaseBuyAccountVirtual(AccountVirtual av) {
		// TODO Auto-generated method stub
		int result = 2;
		AccountVirtual avTmp = accountVirtualDao
				.findAccountVirtual(av.getOwner(), av.getActType(),
						av.getGiftId());
		// 不存在/数量为0
		try {
			if (avTmp == null || avTmp.getNum() == 0) {
				result = 1;
				logger.info(avTmp.getOwner()+"|"+avTmp.getGiftId()+"|"+avTmp.getGiftName()+"|"+avTmp.getNum()+"|记录为空或者数量为0 ");
			}
			// 更新
			else {
				avTmp.setCdate(new Date());
				avTmp.setCoinSum(avTmp.getCoinSum() - avTmp.getCoin());
				avTmp.setNum(avTmp.getNum() - av.getNum());
				accountVirtualDao.update(avTmp);
				result = 0;
				logger.info("减少赠送者购买虚拟商品数量"+avTmp.getOwner()+"|"+avTmp.getActType()+"|"+avTmp.getGiftId()+"|"+avTmp.getGiftName()+"|"+avTmp.getNum()+"|ok");
			}
			
		} catch (Exception e) {
			logger.error("decreaseBuyAccountVirtual", e);
		}
		return result;
	}

	@Override
	public int giveAccountVirtual(AccountVirtual avTo, String fromUser, String info,ObjectCache objectCache) {
		// TODO Auto-generated method stub
		int result = 1;
		try {
			//1 减少赠送者已购买商品数
			int a = decreaseBuyAccountVirtual(createAccountVirtualFrom(avTo,fromUser,0,1));
			if (a == 0) {
				// 2 增加受赠者收到商品数
				incBuyOrGiveAccountVirtual(avTo,1);
				// 3 增加赠送者送出商品数
				incGiveOutAccountVirtual(createAccountVirtualFrom(avTo,fromUser,2,1));
				// 4 赠送者赠送历史记录
				accountVirtualLogDao.insert(AccountVirtualLog.createLog(2,info, fromUser, avTo));
				// 5 受赠者接收历史记录
				accountVirtualLogDao.insert(AccountVirtualLog.createLog(1,info, fromUser, avTo));
				
				//set 赠送者购买商品聚合 memcache
				objectCache.setAccountVirtual(fromUser, 0, this.virtualList(0, fromUser));
				//set 赠送者送出商品聚合 memcache
				objectCache.setAccountVirtual(fromUser, 2, this.virtualList(2, fromUser));
				//set 赠送者送出礼物list memcache
				objectCache.setVirtualLogs(fromUser, 2, accountVirtualLogDao.findAccountVirtualLogAll(fromUser, 2));
				//set 受赠者收到商品聚合 memcache
				objectCache.setAccountVirtual(avTo.getOwner(), 1, this.virtualList(1, avTo.getOwner()));
				//set 受赠者最近收到的礼物memcache
				objectCache.setLastReceiveVirtual(avTo.getOwner(), accountVirtualLogDao.findAccountVirtualLogList(avTo.getOwner(), 1, new Page(6, 1)));
				//set 受赠者收到礼物list memcache
				objectCache.setVirtualLogs(avTo.getOwner(), 1, accountVirtualLogDao.findAccountVirtualLogAll(avTo.getOwner(), 1));
				
				logger.info("set 受赠者最近收到的礼物 memcache ok");
				result = 0;
			}else{
				result = a;
			}
		} catch (Exception e) {
			logger.error("giveAccountVirtual", e);
		}
		return result;
	}

	public int patchGiveAccountVirtual(AccountVirtual av, String[] toUsers, String fromUser, String info,
			ObjectCache objectCache) {

		// TODO Auto-generated method stub
		int result = 1;
		try {
			// 1 减少赠送者已购买商品数
			int a = decreaseBuyAccountVirtual(createAccountVirtualFrom(av, fromUser, 0, toUsers.length));
			// 2 增加赠送者送出商品数
			incGiveOutAccountVirtual(createAccountVirtualFrom(av, fromUser, 2, toUsers.length));
			StringBuffer receiverS = new StringBuffer();
			if (a == 0) {
				for (String toUserTemp : toUsers) {
					av.setOwner(toUserTemp);
					// 3 增加受赠者收到商品数
					incBuyOrGiveAccountVirtual(av, 1);

					receiverS.append(toUserTemp + ",");
					// accountVirtualLogDao.insert(AccountVirtualLog.createLog(2, info, fromUser, av));

					// 4 增加受赠者接收历史记录
					accountVirtualLogDao.insert(AccountVirtualLog.createLog(1, info, fromUser, av));
					// set 受赠者收到商品聚合 memcache
					objectCache.setAccountVirtual(av.getOwner(), 1, this.virtualList(1, av.getOwner()));
					// set 受赠者最近收到的礼物memcache
					objectCache.setLastReceiveVirtual(av.getOwner(),
							accountVirtualLogDao.findAccountVirtualLogList(av.getOwner(), 1, new Page(6, 1)));
					// set 受赠者收到礼物list memcache
					objectCache.setVirtualLogs(av.getOwner(), 1,
							accountVirtualLogDao.findAccountVirtualLogAll(av.getOwner(), 1));
				}

				// 5 增加赠送者赠送历史记录setOwner=log.setTOuser
				av.setOwner(receiverS.toString());
				accountVirtualLogDao.insert(AccountVirtualLog.createLog(2, info, fromUser, av));
				// set 赠送者购买商品聚合 memcache
				objectCache.setAccountVirtual(fromUser, 0, this.virtualList(0, fromUser));
				// set 赠送者送出商品聚合 memcache
				objectCache.setAccountVirtual(fromUser, 2, this.virtualList(2, fromUser));
				// set 赠送者送出礼物记录list memcache
				objectCache.setVirtualLogs(fromUser, 2, accountVirtualLogDao.findAccountVirtualLogAll(fromUser, 2));
				result = 0;
			} else {
				result = a;
			}
		} catch (Exception e) {
			logger.error("patchGiveAccountVirtual", e);
		}
		return result;

	}
	
	
	/**
	 * 构造赠送者XXX对象
	 * @param avTo
	 * @param fromUser
	 * @param actType 0 已购买  2已送出
	 * @param num 数量
	 * @return
	 */
	public AccountVirtual createAccountVirtualFrom(AccountVirtual avTo, String fromUser,int actType,int num){
		AccountVirtual av = new AccountVirtual();
		av.setActType(actType);
		av.setCdate(new Date());
		av.setCoin(avTo.getCoin());
		av.setGiftId(avTo.getGiftId());
		av.setGiftName(avTo.getGiftName());
		av.setLogo(avTo.getLogo());
		av.setNum(num);
		av.setUrl(avTo.getUrl());
		av.setOwner(fromUser);
		return av;
	}

	@Override
	public AccountVirtual findAccountVirtualGiftByOwner(int giftId, String owner) {
		// TODO Auto-generated method stub
		return accountVirtualDao.findAccountVirtualOneGiftByOwner(giftId,owner);
	}
}
