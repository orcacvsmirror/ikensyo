package jp.nichicom.ac;

/**
 * OSの情報を提供(Windows/Mac等)
 * <p>
 * Copyright (c) 2014 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Masahiko Higuchi
 * @version 1.0 2014/3/3
 */
public class ACOSInfo {
	
	private ACOSInfo() {
	}
	
	
	/**
	 * Macで起動中であるか
	 * @return true:Mac起動 false:Mac以外で起動
	 */
	public static boolean isMac() {
		String osName = System.getProperty("os.name");
		return (osName != null && osName.indexOf("Mac") != -1);
	}
	
}
