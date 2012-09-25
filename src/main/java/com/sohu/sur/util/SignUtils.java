package com.sohu.sur.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SignUtils {
	private static Logger logger = LoggerFactory.getLogger(SignUtils.class);
    private SignUtils() {
    }

    /**
     * 生成签名
     *
     * @param src  要签名的字符串
     * @param validationCode 校验码
     * @return 签名字符串
     */
    public static String generateSign(StringBuffer src, String validationCode) {
        return DigestUtils.md5Hex(src.append(validationCode).toString());
    }

    /**
     * 验证接口的调用签名
     * @deprecated
     * @param src     原始值
     * @param validationCode 校验码
     * @param sign           签名
          * @return true if valid, otherwise false
     */
    @Deprecated
    public static boolean isValidSign(StringBuffer src, String validationCode, String sign) {
        return generateSign(src, validationCode).equals(sign);
    }

    /**
     * 验证积分变更接口的调用签名
     * @param uid 用户passport
     * @param actionCode 动作代码
     * @param inputValue 输入分值
     * @param validationCode  动作验证码
     * @param sign   调用签名
     * @return true 签名有效 or false 签名无效
     */
    public static boolean isValidSignForChangeBonus(String uid,
                                                    String actionCode,
                                                    int inputValue,
                                                    String validationCode,
                                                    String sign) {

        if (StringUtils.trimToNull(sign) == null) {
            return false;
        }

        StringBuffer buf = new StringBuffer(uid);
        buf.append(actionCode)
            .append(inputValue);

        String signStr = generateSign(buf, validationCode);

        logger.info("uid={},actioncode={},inputvalue={},validationcode={},sign={},signStr={}",new Object[]{uid,actionCode,inputValue,validationCode,sign,signStr});
        
        return signStr.equals(sign);

    }
}