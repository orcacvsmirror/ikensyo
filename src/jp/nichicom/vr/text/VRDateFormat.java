/** TODO <HEAD> */
package jp.nichicom.vr.text;

import java.io.IOException;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import jp.nichicom.vr.text.parsers.VRDateParser;

import org.xml.sax.SAXException;

/**
 * �N�����̕\���`�����J�X�^�}�C�Y�\�ȓ��t�t�H�[�}�b�g�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see Format
 * @see VRDateParser
 */
public class VRDateFormat extends Format {
    private String format;

    /**
     * �R���X�g���N�^
     */
    public VRDateFormat() {

    }

    /**
     * �R���X�g���N�^
     * 
     * @param format �o�͏���
     */
    public VRDateFormat(String format) {
        setFormat(format);
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param date �ϊ��Ώ�
     * @return �t�H�[�}�b�g�ςݕ�����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public String format(Date date) throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.format(date, getFormat());
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param date �ϊ��Ώ�
     * @param locale �Ώۃ��P�[��
     * @return �t�H�[�}�b�g�ςݕ�����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public String format(Date date, Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.format(date, getFormat(), locale);
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
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
    public String format(Date date, String format) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.format(date, format);
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param obj �ϊ��Ώ�
     * @param toAppendTo �ǉ���
     * @param pos ��͈ʒu
     * @return ��͌���
     */
    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj instanceof String) {
            try {
                obj = parse((String)obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (obj instanceof Calendar) {
            try {
                toAppendTo.append(format(((Calendar) obj).getTime()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj instanceof Date) {
            try {
                toAppendTo.append(format((Date) obj));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj instanceof Number) {
            try {
                toAppendTo.append(format(new Date(((Number) obj).longValue())));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj instanceof String) {
            try {
                toAppendTo.append((String) obj);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (obj != null) {
            throw new IllegalArgumentException(
                    "Cannot format given Object as a Date");
        }
        return toAppendTo;
    }

    // public String format(Date date, String format, Locale locale)
    // throws SAXException, IOException, ParserConfigurationException,
    // ParseException, InstantiationException, IllegalAccessException,
    // ClassNotFoundException {
    // return VRDateParser.format(date, format, locale);
    // }

    /**
     * �o�͏�����Ԃ��܂��B
     * 
     * @return �o�͏���
     */
    public String getFormat() {
        return format;
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param text ��͑Ώ�
     * @return ���t
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     * @throws ParserConfigurationException ��͏������s
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     */
    public Date parse(String text) throws ParseException, SAXException,
            IOException, ParserConfigurationException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        if ("".endsWith(text)) {
            return null;
        }
        return VRDateParser.parse(text);
    }

    // public Date parse(String text) throws ParseException {
    // try {
    // return VRDateParser.parse(text).getTime();
    // } catch (ParseException e) {
    // throw e;
    // } catch (Exception e) {
    // throw new ParseException(e.toString(), 0);
    // }
    // }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param text ��͑Ώ�
     * @param locale �Ώۃ��P�[��
     * @return ���t
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public Date parse(String text, Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.parse(text, locale);
        // return VRDateParser.parse(text, locale).getTime();
    }

    public Object parseObject(String source, ParsePosition pos) {
        try {
            Object ret = parse(source);
            // �����܂Ő�������
            if (source.length() == 0) {
                pos.setIndex(1);
            } else {
                pos.setIndex(source.length());
            }
            return ret;
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     * �o�͏�����ݒ肵�܂��B
     * 
     * @param format �o�͏���
     */
    public void setFormat(String format) {
        this.format = format;
    }

    // public Date parse(String source, ParsePosition pos) {
    // try {
    // Date d = parse(source);
    // pos.setIndex(source.length());
    // return d;
    // } catch (ParseException e) {
    // pos.setErrorIndex(e.getErrorOffset());
    // return null;
    // }
    // }

    // public StringBuffer format(Date date, StringBuffer toAppendTo,
    // FieldPosition fieldPosition) {
    // try {
    // toAppendTo.append(VRDateParser.format(date, getFormat()));
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // return toAppendTo;
    // }
}