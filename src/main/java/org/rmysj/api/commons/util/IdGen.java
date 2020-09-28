package org.rmysj.api.commons.util;

import org.springframework.stereotype.Service;

import javax.ws.rs.ext.ParamConverter.Lazy;
import java.security.SecureRandom;
import java.util.UUID;

@Service
@Lazy
public abstract class IdGen {

	@SuppressWarnings("unused")
	private static SecureRandom random = new SecureRandom();

	private static IdWorker idWorker = new IdWorker(-1, -1);


	public static String uuid(){
		return UUID.randomUUID().toString().replace("-", "");
	}

	/**
	 * 使用SecureRandom随机生成Long.
	 */
	public static long randomLong() {
		return Math.abs(random.nextLong());
	}

	/**
	 * 基于Base62编码的SecureRandom随机生成bytes.
	 */
	public static String randomBase62(int length) {
		byte[] randomBytes = new byte[length];
		random.nextBytes(randomBytes);
		return EncodeUtils.encodeBase62(randomBytes);
	}

	/**
	 * 使用SecureRandom随机生成指定范围的Integer.
	 */
	public static int randomInt(int min, int max) {
		return random.nextInt(max) % (max - min + 1) + min;
	}

	/**
	 * 获取新唯一编号（18为数值）
	 * 来自于twitter项目snowflake的id产生方案，全局唯一，时间有序。
	 * 64位ID (42(毫秒)+5(机器ID)+5(业务编码)+12(重复累加))
	 */
	public static String nextId() {
		return String.valueOf(idWorker.nextId());
	}

	/**
	 * 获取新代码编号
	 */
	public static String nextCode(String code){
		if (code != null){
			String str = code.trim();
			int lastNotNumIndex = -1;
			int len = str.length() - 1;
			for (int i = len; i >= 0; i--) {
				if (str.charAt(i) >= '0' && str.charAt(i) <= '9') {
					lastNotNumIndex = i;
				}else{
					break;
				}
			}
			String prefix = str;
			String prevNum = "000";
			if (lastNotNumIndex != -1){
				prefix = str.substring(0, lastNotNumIndex);
				prevNum = str.substring(lastNotNumIndex, str.length());
			}
			String nextNum = String.valueOf(Long.valueOf(prevNum) + 1);
			str = prefix + StringUtils.leftPad(nextNum, prevNum.length(), "0");
			return str;
		}
		return null;
	}
}
