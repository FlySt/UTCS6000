package com.ncjk.utcs.common.util;

import java.math.BigDecimal;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;

public class DesUtil
{
	// 默认加密密钥
	private static String strDefaultKey = "NCJK";
	// 加密工具
	private Cipher encryptCipher = null;
	// 解密工具
	private Cipher decryptCipher = null;

	// 报错打印日志

	/**
	 * 构造方法1，使用默认密钥
	 */
	public DesUtil() throws Exception
	{
		this(strDefaultKey);
		 
	}

	/**
	 * 构造方法2，使用指定密钥
	 * @param 指定的密钥
	 */
	public DesUtil(String strKey) throws Exception
	{
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		Key key = getKey(strKey.getBytes());

		encryptCipher = Cipher.getInstance("DES");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key);

		decryptCipher = Cipher.getInstance("DES");
		decryptCipher.init(Cipher.DECRYPT_MODE, key);
	}

	/**
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813
	 * @param 需要转换的byte数组
	 * @return 转换后的字符串
	 */
	public static String byteArr2HexStr(byte[] arrB) throws Exception
	{
		int iLen = arrB.length;
		// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍
		StringBuffer sb = new StringBuffer(iLen * 2);
		for (int i = 0; i < iLen; i++)
		{
			int intTmp = arrB[i];
			// 把负数转换为正数
			while (intTmp < 0)
			{
				intTmp = intTmp + 256;
			}
			// 小于0F的数需要在前面补0
			if (intTmp < 16)
			{
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**
	 * 将表示16进制值的字符串转换为byte数组
	 * @param 需要转换的字符串
	 * @return 转换后的byte数组
	 */
	public static byte[] hexStr2ByteArr(String strIn) throws Exception
	{
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;

		// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2
		byte[] arrOut = new byte[iLen / 2];
		for (int i = 0; i < iLen; i = i + 2)
		{
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**
	 * 加密字节数组
	 * @param 待加密的字节数组
	 * @return 返回加密后的字节数组
	 */
	public byte[] encrypt(byte[] arrSource) throws Exception
	{
		return encryptCipher.doFinal(arrSource);
	}

	/**
	 * 加密字符串
	 * @param 待加密的字符串
	 * @return 返回加密后的字符串
	 */
	public String encrypt(String strSource) throws Exception
	{
		return byteArr2HexStr(encrypt(strSource.getBytes()));
	}

	/**
	 * 解密字节数组
	 * @param 待解密的字节数组
	 * @return 返回解密后的字节数组
	 */
	public byte[] decrypt(byte[] arrSource) throws Exception
	{
		return decryptCipher.doFinal(arrSource);
	}

	/**
	 * 解密字符串
	 * @param 待解密的字符串
	 * @return 返回解密后的字符串
	 */
	public String decrypt(String strSource) throws Exception
	{
		return new String(decrypt(hexStr2ByteArr(strSource)));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 * @param 构成该字符串的字节数组
	 * @return 生成的密钥
	 */
	private Key getKey(byte[] arrBTmp) throws Exception
	{
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++)
		{
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	public static void main(String[] args)
	{
		try
		{
			String test = "Admin_123456";
			DesUtil des = new DesUtil();// 自定义密钥
//			System.out.println("加密后的字符1：" + des.encrypt(client));
			System.out.println("解密后的字符1：" + des.decrypt("7d83719a375cfb675a03d02842e6da26"));
//			BigDecimal f = (new BigDecimal(7.8).multiply(new BigDecimal(3600))).divide(new BigDecimal(40));
//			System.out.println(f);
			DesUtil de = new DesUtil("database");// 自定义密钥
			System.out.println("加密后的字符2：" + de.encrypt("Utcs123457"));
			System.out.println("加密后的字符2：" + de.decrypt("7a7d8860e80c590431af5f35145492d8"));
			String ps = "Admin_123456";
			DesUtil des1 = new DesUtil("RJ@NCJK");// 自定义密钥
			System.out.println("加密后的字符3：" + des1.encrypt(ps));
			System.out.println("解密后的字符3：" + des1.decrypt("fb5241a6a95b1a4feafab2d6d3d486b9a6f6f876cd4c5718"));
			
			//System.out.println((1>1));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}