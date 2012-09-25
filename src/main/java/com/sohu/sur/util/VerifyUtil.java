package com.sohu.sur.util;

import java.security.Key;
import java.security.Security;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UGC删除行为加密解密工具类
 * 
 * @author xuewuhao
 * 
 */
public class VerifyUtil {
	private static final Logger logger = LoggerFactory.getLogger(VerifyUtil.class);
	private static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss sss";
	private static String strDefaultKey = "haoxw";
	private Cipher encryptCipher = null;
	private Cipher decryptCipher = null;

	/**
	 * 将byte数组转换为表示16进制值的字符串
	 * 
	 * @param arrB
	 *            需要转换的byte数组
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception {
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0) {
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16) {
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组
	 * 
	 * @param strIn
	 *            需要转换的字符串
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception {
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 默认构造方法，使用默认密钥
	 * 
	 * @throws Exception
	 */
	public VerifyUtil() throws Exception {
		this(strDefaultKey);
	}

	/**
	 * 指定密钥构造方法
	 * 
	 * @param strKey
	 *            指定的密钥
	 * @throws Exception
	 */
	public VerifyUtil(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 加密字节数组
	 * 
	 * @param arrB
	 *            需加密的字节数组
	 * @return 加密后的字节数组
	 * @throws Exception
	 */
	public byte[] encrypt(byte[] arrB) throws Exception {
		return encryptCipher.doFinal(arrB);
	}

	/**
	 * 加密字符串
	 * 
	 * @param strIn
	 *            需加密的字符串
	 * @return 加密后的字符串
	 * @throws Exception
	 */
	public String encrypt(String strIn) throws Exception {
		logger.info("encrypt instring=" + strIn);
		String enstr = byteArr2HexStr(encrypt(strIn.getBytes()));
		logger.info("encrypt outstring=" + enstr);
		return enstr;
	}

	/**
	 * 解密字节数组
	 * 
	 * @param arrB
	 *            需解密的字节数组
	 * @return 解密后的字节数组
	 * @throws Exception
	 */
	public byte[] decrypt(byte[] arrB) throws Exception {
		return decryptCipher.doFinal(arrB);
	}

	/**
	 * 解密字符串
	 * 
	 * @param strIn
	 *            需解密的字符串
	 * @return 解密后的字符串
	 * @throws Exception
	 */
	public String decrypt(String strIn) throws Exception {
		logger.info("decrypt instring=" + strIn);
		String destr = new String(decrypt(hexStr2ByteArr(strIn)));
		logger.info("decrypt outstring=" + destr);
		return destr;
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * 
	 * @param arrBTmp
	 *            构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws java.lang.Exception
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}

	/**
	 * UGC构造 生成加密串
	 * 
	 * @param passport
	 * @param actionCode
	 * @param date
	 * @return
	 */
	public String createStr(String passport, String actionCode, Date date) throws Exception {
		String str = passport + "|" + actionCode + "|" + date2Str(date);
		return encrypt(str.trim());
	}

	/**
	 * UGC解密 判断
	 * 
	 * @param instr
	 * @param cookiesUid
	 * @return
	 * @throws Exception
	 */
	public boolean checkStr(String instr,String cookiesUid) throws Exception{
		String str = decrypt(instr.trim());
		String tmp[] = str.split("[|]");
		//判断是否是同一用户
		if(!tmp[0].equals(cookiesUid.trim())){
			logger.info("user not match");
			return false;
		}
		Date d = new Date();
		double sec = (d.getTime()-str2Date(tmp[2].toString()).getTime())/1000.0;
		//如果请求时间比当前时间小1分钟 则认为是非法重复请求
		if(sec>60){
			logger.info("time out");
			return false;
		}
		logger.info(sec+"success");
		return true;
	}

	/**
	 * 将日期改为字符串
	 * 
	 * @param date
	 * @return
	 */
	public String date2Str(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		return sdf.format(date);
	}

	/**
	 * 将字符串类型转换为时间类型
	 * 
	 * @return
	 */
	public Date str2Date(String str) {
		Date d = null;
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_PATTERN);
		try {
			d = sdf.parse(str);
		} catch (Exception e) {
			logger.error("将字符串类型转换为时间类型异常：", e);
		}
		return d;
	}
	
	public static void main(String aaa[]) {
		try {
			VerifyUtil a = new VerifyUtil();
			String jiami = a.createStr("haoxw@sohu.com","newblog",new Date());
			a.checkStr(jiami,"haoxw@sohu.com ");
		} catch (Exception e) {

		}
	}
}
