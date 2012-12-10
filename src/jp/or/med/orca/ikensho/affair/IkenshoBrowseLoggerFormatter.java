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
 * �Ɩ��N�����O�o�̓N���X�Ŏg�p����A���O���R�[�h�̃t�H�[�}�b�g�N���X
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
	
	//�l�������v���O����
	public static final Set AFFAIR_PERSONAL = Collections.unmodifiableSet( new HashSet(){{
		add("IkenshoPatientInfo");					//���ҍŐV��{���
		add("IkenshoIkenshoInfoH18");				//�厡��ӌ���
		add("IkenshoIshiIkenshoInfo");				//��t�ӌ���
		add("IkenshoHoumonKangoShijisho");			//�K��Ō�w����
		add("IkenshoTokubetsuHoumonKangoShijisho");	//���ʖK��Ō�w����
	}});
	
	
	public synchronized String format(LogRecord record) {
		
		StringBuffer sb = new StringBuffer();
		dat.setTime(record.getMillis());
		
		if (formatter == null) {
			formatter = new SimpleDateFormat(format);
		}
		
		sb.append(formatter.format(dat));
		sb.append(" ");
		
		//�J�X�^�����b�Z�[�W��\��
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
		
		//�l�������v���O�����̏ꍇ�́Aid�����O�ɏo��
		if (AFFAIR_PERSONAL.contains(className)) {
			sb.append(" ");
			sb.append(info.getParameters().get("PATIENT_NO"));
		}
		
		sb.append(ACConstants.LINE_SEPARATOR);
		
		return sb.toString();
	}

}
