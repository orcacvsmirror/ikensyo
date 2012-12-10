package jp.nichicom.ac.text;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.ParserConfigurationException;

import jp.nichicom.vr.text.VRDateFormat;

import org.xml.sax.SAXException;

/**
 * SQL�̍\�z��z�肵�����t�t�H�[�}�b�g�ł��B
 * <p>
 * �f�[�^��null�̂Ƃ���"NULL"���o�͂��܂��B<br />
 * �f�[�^��null�łȂ���΁A'�������������t'���o�͂��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACSQLSafeDateFormat extends VRDateFormat {
    /**
     * �R���X�g���N�^�ł��B
     */
    public ACSQLSafeDateFormat() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param format �o�͏���
     */
    public ACSQLSafeDateFormat(String format) {
        super(format);
    }

    public String format(Date date) throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        StringBuffer sb = new StringBuffer();
        if ((date == null)||"".equals(date)) {
            sb.append("NULL");
        } else {
            sb.append("'");
            sb.append(super.format(date));
            sb.append("'");
        }
        return sb.toString();
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * <p>
     * �f�[�^��null�ł����"NULL"��Ԃ��܂��B
     * </p>
     * 
     * @param date �ϊ��Ώ�
     * @param format ����
     * @return �t�H�[�}�b�g�ςݕ�����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public String format(Object date, String format) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        StringBuffer sb = new StringBuffer();
        if ((date == null)||"".equals(date)) {
            sb.append("NULL");
        } else {
            sb.append("'");
            sb.append(super.format((Date) date, format));
            sb.append("'");
        }
        return sb.toString();
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if ((obj == null)||"".equals(obj)) {
            toAppendTo.append("NULL");
            return toAppendTo;
        }
        return super.format(obj, toAppendTo, pos);
    }
}
