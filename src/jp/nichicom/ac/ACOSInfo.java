package jp.nichicom.ac;

/**
 * OS�̏����(Windows/Mac��)
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
	 * Mac�ŋN�����ł��邩
	 * @return true:Mac�N�� false:Mac�ȊO�ŋN��
	 */
	public static boolean isMac() {
		String osName = System.getProperty("os.name");
		return (osName != null && osName.indexOf("Mac") != -1);
	}
	
}
