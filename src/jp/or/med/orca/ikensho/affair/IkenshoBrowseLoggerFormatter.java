package jp.or.med.orca.ikensho.affair;

import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.core.ACAffairInfo;

/**
 * 業務起動ログ出力クラスで使用する、ログレコードのフォーマットクラス
 * <p>
 * Copyright (c) 2012 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2012/09/18
 */
public class IkenshoBrowseLoggerFormatter extends Formatter {
	
	private Date dat = new Date();
	private final static String format = "yyyy/MM/dd HH:mm:ss";
	private SimpleDateFormat formatter;
	
	private static String user = System.getProperty("user.name");
	
	//個人情報を持つプログラム
	public static final Set AFFAIR_PERSONAL = Collections.unmodifiableSet( new HashSet(){{
		add("IkenshoPatientInfo");					//患者最新基本情報
		add("IkenshoIkenshoInfoH18");				//主治医意見書
		add("IkenshoIshiIkenshoInfo");				//医師意見書
		add("IkenshoHoumonKangoShijisho");			//訪問看護指示書
		add("IkenshoTokubetsuHoumonKangoShijisho");	//特別訪問看護指示書
	}});
	
	
	public synchronized String format(LogRecord record) {
		
		StringBuffer sb = new StringBuffer();
		dat.setTime(record.getMillis());
		
		if (formatter == null) {
			formatter = new SimpleDateFormat(format);
		}
		
		sb.append(formatter.format(dat));
		sb.append(" ");
		
		//カスタムメッセージを表示
		ACAffairInfo info = (ACAffairInfo)record.getParameters()[0];
		
		sb.append(user);
		sb.append(" ");
		
		String classFullName = info.getClassName();
		String className = classFullName;
		int lastIndex = classFullName.lastIndexOf('.');
		if (lastIndex != -1) {
			className = classFullName.substring(lastIndex + 1);
		}
		
		sb.append(className);
		
		//個人情報を持つプログラムの場合は、idをログに出力
		if (AFFAIR_PERSONAL.contains(className)) {
			sb.append(" ");
			sb.append(info.getParameters().get("PATIENT_NO"));
		}
		
		sb.append(ACConstants.LINE_SEPARATOR);
		
		return sb.toString();
	}

}
