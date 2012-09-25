package com.sohu.sur.service;

import com.sohu.sur.model.Action;

/**
 * 用户积分变更接口
 *
 * User: 郭勇
 * Date: 2011-3-8 16:28:55
 */
public interface ChangeBonusService {

    /**
     * 变更用户积分/经验值
     *
     *
     * @param uid  用户passport
     * @param action  变更动作
     * @param productCode
     * @param value 变更值
     * @param desc   变更动作描述
     * @param sign 接口调用签名 sign=md5(actionCode, validationCode)
     * @return ChangeExpServiceResult
     */
     ChangeBonusServiceResult changeBonus(String uid, Action action, String productCode, int value, String desc, String sign);
}
