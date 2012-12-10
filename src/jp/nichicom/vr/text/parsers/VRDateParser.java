/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;

import javax.xml.parsers.ParserConfigurationException;

import jp.nichicom.vr.util.logging.VRLogger;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * �N�����̕\���`�����J�X�^�}�C�Y�\�ȓ��t��́E�ϊ��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see Locale
 * @see SimpleDateFormat
 */
public class VRDateParser {
    /**
     * ���P�[���w��Ȃ���\�����ꃍ�P�[���萔�ł��B
     */
    public static Locale FREE_LOCALE = new Locale("", "", "");

    private static VRDateParser singleton;

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param target �ϊ��Ώ�
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
    public static String format(Calendar target, String format)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return format(target, format, Locale.getDefault());
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param target �ϊ��Ώ�
     * @param format ����
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
    public static String format(Calendar target, String format, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().formatImpl(target, format, locale);
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param target �ϊ��Ώ�
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
    public static String format(Date target, String format)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return format(target, format, Locale.getDefault());
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param target �ϊ��Ώ�
     * @param format ����
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
    public static String format(Date target, String format, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(target);
        return format(cal, format, locale);

    }

    /**
     * �w��������Y������f�t�H���g���P�[���ɂ����錳����Ԃ��܂��B
     * 
     * @param date ����
     * @return ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static VRDateParserEra getEra(Calendar date) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        ArrayList eras = getEras();
        Iterator it = eras.iterator();
        while (it.hasNext()) {
            VRDateParserEra era = (VRDateParserEra) it.next();
            Calendar begin = era.getBegin();
            Calendar end = era.getEnd();
            if (!(begin.after(date) || end.before(date))) {
                return era;
            }
        }
        return null;
    }

    /**
     * �w��������Y������w�胍�P�[���ɂ����錳����Ԃ��܂��B
     * 
     * @param date ����
     * @param locale ���P�[��
     * @return ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static VRDateParserEra getEra(Calendar date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getEra(date, locale);
    }

    /**
     * �w��������Y������f�t�H���g���P�[���ɂ����錳����Ԃ��܂��B
     * 
     * @param date ����
     * @return ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static VRDateParserEra getEra(Date date) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return getEra(cal);
    }

    /**
     * �w��������Y������w�胍�P�[���ɂ����錳����Ԃ��܂��B
     * 
     * @param date ����
     * @param locale ���P�[��
     * @return ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static VRDateParserEra getEra(Date date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Calendar cal = Calendar.getInstance(locale);
        cal.setTime(date);
        return getEra(cal, locale);
    }

    /**
     * �f�t�H���g���P�[���̌����W����Ԃ��܂��B
     * 
     * @return �����W��
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static ArrayList getEras() throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getEras(Locale.getDefault());
    }

    /**
     * �w�胍�P�[���̌����W����Ԃ��܂��B
     * 
     * @param locale ���P�[��
     * @return �����W��
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static ArrayList getEras(Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().getErasImpl(locale);
    }

    /**
     * �w��������Y������w�胍�P�[���̏j�����W���Ƃ��ĕԂ��܂��B
     * 
     * @param locale ���P�[��
     * @param date ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     * @return �j���W��
     */
    public static ArrayList getHolydays(Date date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        Calendar cal = Calendar.getInstance(locale);
        cal.setTime(date);
        return getHolydays(cal, locale);
    }

    /**
     * �w��������Y������j�����W���Ƃ��ĕԂ��܂��B
     * 
     * @param date ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     * @return �j���W��
     */
    public static ArrayList getHolydays(Date date) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getHolydays(date, Locale.getDefault());
    }

    /**
     * �w��������Y������j�����W���Ƃ��ĕԂ��܂��B
     * 
     * @param date ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     * @return �j���W��
     */
    public static ArrayList getHolydays(Calendar date) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getHolydays(date, Locale.getDefault());
    }

    /**
     * �w��������Y������w�胍�P�[���̏j�����W���Ƃ��ĕԂ��܂��B
     * 
     * @param locale ���P�[��
     * @param date ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     * @return �j���W��
     */
    public static ArrayList getHolydays(Calendar date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().getHolydaysImpl(date, locale);
    }

    /**
     * �C���X�^���X��Ԃ��܂��B
     * <p>
     * singleton pattern
     * </p>
     * 
     * @return �C���X�^���X
     */
    public static VRDateParser getInstance() {
        if (singleton == null) {
            singleton = new VRDateParser();
        }
        return singleton;
    }

    /**
     * �w�胍�P�[�����܂ރ��P�[�����`��Ԃ��܂��B
     * 
     * @param locale ��r���P�[��
     * @return ���P�[�����`
     */
    public static VRDateParserLocale getLocale(Locale locale) {
        return getInstance().getLocaleImpl(locale);
    }

    /**
     * ���������t�Ƃ��ĉ�͉\����Ԃ��܂��B
     * 
     * @param target ��͑Ώ�
     * @return ���t�Ƃ��ĉ��߉\��
     */
    public static boolean isValid(String target) {
        try {
            parse(target, Locale.getDefault());
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param target ��͑Ώ�
     * @return ���t
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public static Date parse(String target) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return parse(target, Locale.getDefault());
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param target ��͑Ώ�
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
    public static Date parse(String target, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().parseImpl(target, locale);
    }

    private VRDateParserDocumentCreatable documentCreator;

    private HashMap formats;

    private ArrayList locales;

    private SimpleDateFormat simpleDateFormat;

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * singleton pattern
     * </p>
     */
    protected VRDateParser() {
        formats = new HashMap();
        locales = new ArrayList();
        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    }

    /**
     * ��`�������������܂��B
     */
    public void clear() {
        HashMap fm = getFormats();
        if (fm != null) {
            fm.clear();
        }
        ArrayList loc = getLocales();
        if (loc != null) {
            loc.clear();
        }
    }

    /**
     * XML�����������Ԃ��܂��B
     * 
     * @return XML����������
     */
    public VRDateParserDocumentCreatable getDocumentCreator() {
        return documentCreator;
    }

    /**
     * �����W����Ԃ��܂��B
     * 
     * @return �����W��
     */
    public HashMap getFormats() {
        return formats;
    }

    /**
     * ���P�[�����`�W����Ԃ��܂��B
     * 
     * @return ���P�[�����`�W��
     */
    public ArrayList getLocales() {
        return locales;
    }

    /**
     * ��`�t�@�C�����ēǂݍ��݂��܂��B
     * 
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    public void reload() throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        clear();

        VRDateParserDocumentCreatable docC = getDocumentCreator();
        if (docC == null) {
            // �w�肳��Ă��Ȃ���΃f�t�H���g�N���X�őΉ�
            docC = new VRDateParserDocumentFile();
        }
        Document doc = docC.getDefinedDocument();
        if (doc == null) {
            throw new IOException("XML�����I�u�W�F�N�g���擾�ł��܂���B");
        }

        // ���[�g�v�f�̎擾
        // �q�v�f���܂񂾃m�[�h���
        NodeList nodeChildren = doc.getDocumentElement().getChildNodes();
        parseNode(nodeChildren);
    }

    /**
     * XML�����������ݒ肵�܂��B
     * 
     * @param documentCreator XML����������
     */
    public void setDocumentCreator(VRDateParserDocumentCreatable documentCreator) {
        this.documentCreator = documentCreator;
    }

    /**
     * �����W����ݒ肵�܂��B
     * 
     * @param formats �����W��
     */
    public void setFormats(HashMap formats) {
        this.formats = formats;
    }

    /**
     * ���P�[�����`�W����ݒ肵�܂��B
     * 
     * @param locales ���P�[�����`�W��
     */
    public void setLocales(ArrayList locales) {
        this.locales = locales;
    }

    /**
     * VRDateParserPatterns�C���X�^���X�𐶐����܂��B <br />
     * factory method Pattern
     * 
     * @return �C���X�^���X
     */
    protected VRDateParserPatterns createPatterns() {
        return new VRDateParserPatterns();
    }

    /**
     * ���t���w��̏����Ƀt�H�[�}�b�g���ĕԂ��܂��B
     * 
     * @param target �ϊ��Ώ�
     * @param format ����
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
    protected String formatImpl(Calendar target, String format, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        StringBuffer sb = new StringBuffer();
        ArrayList statements = parseFormat(target, format, locale);
        int size = statements.size();
        for (int i = 0; i < size; i++) {
            String txt = ((VRDateParserFormatStatement) statements.get(i))
                    .getText();
            if ((txt != null) && (!"".equals(txt))) {
                sb.append(txt);
            }
        }
        return new SimpleDateFormat(sb.toString()).format(target.getTime());
    }

    /**
     * �m�[�h�̑����l��Ԃ��܂��B
     * 
     * @param node �����Ώۂ̃m�[�h
     * @param attrName ������
     * @return �m�[�h�̑����l
     */
    protected String getAttributeValue(Node node, String attrName) {
        if (node != null) {
            Node attr = node.getAttributes().getNamedItem(attrName);
            if (attr != null) {
                String val = attr.getNodeValue();
                if (val != null) {
                    return val;
                }
            }
        }
        return "";
    }

    /**
     * ������͗p�̃p�[�T�t�H�[�}�b�g��Ԃ��܂��B <br />
     * factory method Pattern
     * 
     * @return �p�[�T�t�H�[�}�b�g
     */
    protected SimpleDateFormat getBaseDateFormat() {
        return simpleDateFormat;
    }

    /**
     * �w�胍�P�[�����̎w����������錳����`��Ԃ��܂��B
     * 
     * @param locale �Ώۃ��P�[��
     * @param target �Ώۓ�
     * @return ������`
     */
    protected VRDateParserEra getEra(VRDateParserLocale locale, Calendar target) {
        Calendar cal = Calendar.getInstance(locale.getLocale());
        cal.set(target.get(Calendar.YEAR), target.get(Calendar.MONTH), target
                .get(Calendar.DATE), 0, 0, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long millis = cal.getTimeInMillis();

        ArrayList eras = locale.getEras();
        int eraSize = eras.size();
        if (eraSize > 0) {
            for (int i = 0; i < eraSize; i++) {
                VRDateParserEra era = (VRDateParserEra) eras.get(i);
                if ((millis >= era.getBegin().getTimeInMillis())
                        && (millis <= era.getEnd().getTimeInMillis())) {
                    return era;
                }
            }
        }
        return null;
    }

    /**
     * �w��������Y������w�胍�P�[���̏j�����W���Ƃ��ĕԂ��܂��B
     * 
     * @param locale ���P�[��
     * @param date ����
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     * @return �j���W��
     */
    protected ArrayList getHolydaysImpl(Calendar date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        if (getFormats().size() == 0) {
            reload();
        }

        ArrayList stock = new ArrayList();
        VRDateParserLocale loc = getLocale(locale);
        if (loc != null) {
            VRDateParserHolydays hs = loc.getHolydays();
            hs.stockHolyday(date, stock);
        }
        return stock;
    }

    /**
     * �w�胍�P�[���̌����W����Ԃ��܂��B
     * 
     * @return �����W��
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    protected ArrayList getErasImpl(Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        if (getFormats().size() == 0) {
            reload();
        }

        if (locale == null) {
            // ���P�[���w��Ȃ� �� �󃍃P�[��
            return new ArrayList();
        }

        // ���P�[���w�肠��
        Iterator it = getLocales().iterator();
        while (it.hasNext()) {
            VRDateParserLocale loc = (VRDateParserLocale) it.next();
            Locale locLocale = loc.getLocale();
            if (locLocale.equals(locale)) {
                return loc.getEras();
            }
        }
        throw new ParseException("���P�[������`�̂��߁A��͎��s", 0);

    }

    /**
     * �w�胍�P�[�����܂ރ��P�[�����`��Ԃ��܂��B
     * 
     * @param locale ��r���P�[��
     * @return ���P�[�����`
     */
    protected VRDateParserLocale getLocaleImpl(Locale locale) {
        if (locale == null) {
            return null;
        }

        // ���P�[���w�肠��
        Iterator it = getLocales().iterator();
        while (it.hasNext()) {
            VRDateParserLocale loc = (VRDateParserLocale) it.next();
            if (locale.equals(loc.getLocale())) {
                return loc;
            }
        }
        return null;
    }

    /**
     * �m�[�h�����ŏ��̃e�L�X�g�v�f�𕶎���Ƃ��ĕԂ��܂��B
     * 
     * @param node �����Ώۂ̃m�[�h
     * @return �e�L�X�g�v�f�l
     */
    protected String getNodeTextValue(Node node) {
        if ((node != null) && (node.hasChildNodes())) {
            Node child = node.getFirstChild();
            if (child != null) {
                String val = child.getNodeValue();
                if (val != null) {
                    return val;
                }
            }
        }
        return "";
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param target ��͑Ώ�
     * @param locale �Ώۃ��P�[��
     * @return ���t
     * @throws ParseException �����͈͓��t�̉�͎��s
     */
    protected Calendar match(String target, VRDateParserLocale locale)
            throws ParseException {
        VRDateParserPatterns patterns = (VRDateParserPatterns) getFormats()
                .get(locale.getId());
        if (patterns != null) {
            Calendar cal = patterns.match(target, locale);
            if (cal != null) {
                return cal;
            }
        }
        return null;
    }

    /**
     * era�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @param locale ���P�[��
     * @throws ParseException �����͈͓��t�̉�͎��s
     */
    protected void parseEraNode(Node baseNode, VRDateParserLocale locale)
            throws ParseException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())
                || (!baseNode.hasChildNodes())) {
            return;
        }
        VRDateParserEra era = new VRDateParserEra(locale.getLocale());
        locale.addEra(era);

        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("abbreviation".equals(nodeName)) {
                era.setAbbreviation(getAttributeValue(child, "type"),
                        getNodeTextValue(child));
            } else if ("begin".equals(nodeName)) {
                Calendar cal = Calendar.getInstance(locale.getLocale());
                cal.setTime(getBaseDateFormat().parse(getNodeTextValue(child)));
                era.setBegin(cal);
            } else if ("end".equals(nodeName)) {
                Calendar cal = Calendar.getInstance(locale.getLocale());
                cal.setTime(getBaseDateFormat().parse(getNodeTextValue(child)));
                era.setEnd(cal);
            }
        }

    }

    /**
     * eras�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @param locale ���P�[��
     * @throws ParseException �����͈͓��t�̉�͎��s
     */
    protected void parseErasNode(Node baseNode, VRDateParserLocale locale)
            throws ParseException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())) {
            return;
        }

        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("era".equals(nodeName)) {
                parseEraNode(child, locale);
            }
        }
    }

    /**
     * �w�肵���������\���P�ʂɕ������ĕԂ��܂��B
     * 
     * @param target �ϊ��Ώ�
     * @param format ����
     * @param locale �Ώۃ��P�[��
     * @return �\���P�ʂɕ�����������
     * @throws SAXException ��`�t�@�C����SAX��͎��s
     * @throws IOException ��`�t�@�C���̓��͎��s
     * @throws ParserConfigurationException ��͏������s
     * @throws ParseException ��͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    protected ArrayList parseFormat(Calendar target, String format,
            Locale locale) throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        final char QUOTE = '\'';
        // �N�H�[�g�ł������Ă��邩
        final int NONE = 0;
        final int QUOTING = 1;
        final int QUOTED = 2;
        int quoteStatus = NONE;

        boolean useHolyday = false;
        boolean useEraYear = false;
        boolean useEraText = false;
        ArrayList result = new ArrayList();
        int cellBeginPos = 0;
        int len = format.length();

        for (int i = 0; i < len; i++) {
            if (format.charAt(i) == QUOTE) {
                switch (quoteStatus) {
                case NONE:
                    // �N�H�[�g�ȍ~���؂�o���Ώ�
                    quoteStatus = QUOTING;
                    break;
                case QUOTING:
                    // �N�H�[�g���̃N�H�[�g �� ���O�܂ł��؂�o���Ώ�
                    quoteStatus = QUOTED;
                    break;
                case QUOTED:
                    // �N�H�[�g�I���Ǝv�����̂ɑ����ăN�H�[�g �� �N�H�[�g�G�X�P�[�v
                    quoteStatus = QUOTING;
                    break;
                }
                // switch��̏����͍s�킸�A���̕����𑖍�����
                continue;
            }
            if (quoteStatus == QUOTING) {
                // �N�H�[�g�� �� �X�L�b�v
                continue;
            }

            VRDateParserFormatStatement nextStatement = null;
            int contCnt = 0;
            switch (format.charAt(i)) {
            case VRDateParserFormatStatement.ERA_TEXT:
                // �������x��
                contCnt = VRDateParserPattern.getContinationCount(format,
                        i + 1, len, VRDateParserFormatStatement.ERA_TEXT);
                nextStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.ERA_TEXT, contCnt);
                useEraText = true;
                break;
            case VRDateParserFormatStatement.ERA_YEAR:
                // �����N
                contCnt = VRDateParserPattern.getContinationCount(format,
                        i + 1, len, VRDateParserFormatStatement.ERA_YEAR);
                nextStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.ERA_YEAR, contCnt);
                useEraYear = true;
                break;
            case VRDateParserFormatStatement.HOLYDAY:
                // �j��
                contCnt = VRDateParserPattern.getContinationCount(format,
                        i + 1, len, VRDateParserFormatStatement.HOLYDAY);
                nextStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.HOLYDAY, contCnt);
                useHolyday = true;
                break;
            default:
                // ���e����
                // switch��̏����͍s�킸�A���̕����𑖍�����
                continue;
            }

            if (i > cellBeginPos) {
                VRDateParserFormatStatement literalStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.LITERAL, i - cellBeginPos);
                literalStatement.setText(format.substring(cellBeginPos, i));
                result.add(literalStatement);
            }

            if (nextStatement != null) {
                result.add(nextStatement);
            }

            i += contCnt - 1;
            cellBeginPos = i + 1;

        }
        if (cellBeginPos < len) {
            // ���e���������ŏI�������ꍇ
            VRDateParserFormatStatement literalStatement = new VRDateParserFormatStatement(
                    VRDateParserFormatStatement.LITERAL, len - cellBeginPos);
            literalStatement.setText(format.substring(cellBeginPos, len));
            result.add(literalStatement);
        }

        // ������j�����g�p���Ă���ꍇ
        if (useEraYear || useEraText || useHolyday) {
            // �t�H�[�}�b�g�ǂݍ���
            if (getFormats().size() == 0) {
                reload();
            }

            VRDateParserLocale targetLocale;
            VRDateParserLocale freeLocale;

            if (locale == null) {
                // ���P�[���w��Ȃ� �� �󃍃P�[��
                targetLocale = getLocale(FREE_LOCALE);
                freeLocale = null;
            } else {
                targetLocale = getLocale(locale);
                if (FREE_LOCALE.equals(locale)) {
                    freeLocale = null;
                } else {
                    freeLocale = getLocale(FREE_LOCALE);
                }
            }

            if (targetLocale == null) {
                throw new ParseException("���P�[����`�����݂��܂���B" + format + " ]", 0);
            }

            // �����܂ł̌��ʂ��L���b�V�����čĉ�͂̎�Ԃ��Ȃ��Ă��悢

            VRDateParserEra targetEra = null;
            String holydayText = "";

            // �����g�p
            if (useEraYear || useEraText) {
                targetEra = getEra(targetLocale, target);
                if (targetEra == null) {
                    if (freeLocale == null) {
                        throw new ParseException("�������`�������P�[�������݂��܂���B" + format
                                + " ]", 0);
                    }
                    targetEra = getEra(freeLocale, target);
                    if (targetEra == null) {
                        throw new ParseException("�w��̓��t�������͈͓̔��ɂ���܂���B[ "
                                + format + " ](Target="
                                + getBaseDateFormat().format(target.getTime())
                                + ")", 0);
                    }
                }
            }
            // �j���g�p
            if (useHolyday) {
                ArrayList holydays = new ArrayList();
                targetLocale.getHolydays().stockHolyday(target, holydays);
                if (freeLocale != null) {
                    freeLocale.getHolydays().stockHolyday(target, holydays);
                }
                StringBuffer sb = new StringBuffer();
                Iterator it = holydays.iterator();
                while (it.hasNext()) {
                    VRDateParserHolyday holyday = (VRDateParserHolyday) it
                            .next();
                    sb.append(holyday.getId() + " ");
                }
                if (sb.length() > 0) {
                    sb.deleteCharAt(sb.length() - 1);
                    holydayText = sb.toString().replaceAll("'", "''");
                }
            }

            int statementSize = result.size();
            for (int i = 0; i < statementSize; i++) {
                VRDateParserFormatStatement statement = (VRDateParserFormatStatement) result
                        .get(i);
                switch (statement.getType()) {
                case VRDateParserFormatStatement.ERA_TEXT: {
                    String eraName = targetEra.getAbbreviation(statement
                            .getLength());
                    if (eraName == null) {
                        throw new ParseException("�w��̌������̂͒�`����Ă��܂���B[ "
                                + format + " ](Target="
                                + getBaseDateFormat().format(target.getTime())
                                + ")", 0);
                    }
                    if (!"".equals(eraName)) {
                        statement.setText("'" + eraName.replaceAll("'", "''")
                                + "'");
                    }
                    break;
                }
                case VRDateParserFormatStatement.ERA_YEAR: {
                    StringBuffer sb = new StringBuffer();
                    int eraLen = statement.getLength();
                    for (int j = 0; j < eraLen; j++) {
                        sb.append("0");
                    }
                    statement.setText(new DecimalFormat(sb.toString())
                            .format(target.get(Calendar.YEAR)
                                    - targetEra.getBegin().get(Calendar.YEAR)
                                    + 1));
                    break;
                }
                case VRDateParserFormatStatement.HOLYDAY: {
                    if (!"".equals(holydayText)) {
                        statement.setText("'" + holydayText + "'");
                    }
                    break;
                }
                }
            }
        }

        return result;
    }

    /**
     * format�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @throws ParseException �����̉�͎��s
     */
    protected void parseFormatNode(Node baseNode) throws ParseException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())) {
            return;
        }
        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("patterns".equals(nodeName)) {
                parsePatternsNode(child);
            }
        }

    }

    /**
     * holyday�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @param locale ���P�[��
     * @throws ParseException �����͈͓��t�̉�͎��s
     */
    protected void parseHolydayNode(Node baseNode, VRDateParserLocale locale)
            throws ParseException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())
                || (!baseNode.hasChildNodes())) {
            return;
        }

        VRDateParserHolyday holyday = new VRDateParserHolyday();
        VRDateParserHolydayTerm term = new VRDateParserHolydayTerm();
        term.setHolyday(holyday);

        int m = 0;
        int d = 0;
        int week = 0;
        int wday = 0;

        NodeList baseChildren = baseNode.getChildNodes();
        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("term".equals(nodeName)) {
                String type = getAttributeValue(child, "type");
                if ("M".equals(type)) {
                    m = Integer.parseInt(getNodeTextValue(child));
                } else if ("d".equals(type)) {
                    d = Integer.parseInt(getNodeTextValue(child));
                } else if ("week".equals(type)) {
                    week = Integer.parseInt(getNodeTextValue(child));
                } else if ("wday".equals(type)) {
                    wday = Integer.parseInt(getNodeTextValue(child));
                }
            } else if ("begin".equals(nodeName)) {
                term.getBegin().setTime(
                        getBaseDateFormat().parse(getNodeTextValue(child)));
            } else if ("end".equals(nodeName)) {
                term.getEnd().setTime(
                        getBaseDateFormat().parse(getNodeTextValue(child)));
            } else if ("parameter".equals(nodeName)) {
                holyday.setParameter(getAttributeValue(child, "key"),
                        getNodeTextValue(child));
            }
        }
        holyday.setId(getAttributeValue(baseNode, "id"));

        if ((m <= 0) || (m > 12)) {
            throw new ParseException("�j���̑Ώی��w�肪�s���ł��B", 0);
        }
        if (d > 0) {
            // �Œ��
            if (d > 31) {
                throw new ParseException("�j���̑Ώۓ��w�肪�s���ł��B", 0);
            }
            locale.getHolydays().addDayHolydayTerm(term, m, d);
        } else {
            // �Œ�T
            if ((week <= 0) || (week > 5) || (wday <= 0) || (wday > 7)) {
                throw new ParseException("�j���̑ΏۏT�w�肪�s���ł��B", 0);
            }
            locale.getHolydays().addWeekHolydayTerm(term, m, week, wday);
        }

    }

    /**
     * holydays�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @param locale ���P�[��
     * @throws ParseException �����͈͓��t�̉�͎��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    protected void parseHolydaysNode(Node baseNode, VRDateParserLocale locale)
            throws ParseException, InstantiationException,
            IllegalAccessException, ClassNotFoundException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())) {
            return;
        }

        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("holyday".equals(nodeName)) {
                parseHolydayNode(child, locale);
            } else if ("calcurate".equals(nodeName)) {
                try {
                    Object obj = Class.forName(getAttributeValue(child, "id"))
                            .newInstance();
                    if (obj instanceof VRDateParserHolydayCalculatable) {
                        locale.getHolydays().addCalcHolyday(
                                (VRDateParserHolydayCalculatable) obj);
                    }
                } catch (Exception ex) {
                    VRLogger.info("�j�Փ���`�^["+getAttributeValue(child, "id")
                            + "]�̃C���X�^���X���Ɏ��s���܂����B");
                }

            }
        }
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param target ��͑Ώ�
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
    protected Date parseImpl(String target, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        if (getFormats().size() == 0) {
            reload();
        }

        if (locale == null) {
            // ���P�[���w��Ȃ� �� �󃍃P�[��
            locale = FREE_LOCALE;
        }

        // ���P�[���w�肠��
        Iterator it = getLocales().iterator();
        while (it.hasNext()) {
            VRDateParserLocale loc = (VRDateParserLocale) it.next();
            Locale locLocale = loc.getLocale();
            if (locLocale.equals(locale) || locLocale.equals(FREE_LOCALE)) {
                Calendar cal = match(target, loc);
                if (cal != null) {
                    return cal.getTime();
                }
            }
        }
        throw new ParseException("�t�H�[�}�b�g����`�̂��߁A��͎��s", 0);
    }

    /**
     * locale�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @throws ParseException ��͂Ɏ��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    protected void parseLocaleNode(Node baseNode) throws ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        if ((baseNode == null) || (!baseNode.hasAttributes())) {
            return;
        }
        VRDateParserLocale locale = new VRDateParserLocale(getAttributeValue(
                baseNode, "id"), getAttributeValue(baseNode, "language"),
                getAttributeValue(baseNode, "country"), getAttributeValue(
                        baseNode, "variant"));
        getLocales().add(locale);

        if (!baseNode.hasChildNodes()) {
            return;
        }

        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("eras".equals(nodeName)) {
                parseErasNode(child, locale);
            } else if ("holydays".equals(nodeName)) {
                parseHolydaysNode(child, locale);
            }
        }
    }

    /**
     * locales�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @throws ParseException ��͂Ɏ��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    protected void parseLocalesNode(Node baseNode) throws ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())) {
            return;
        }
        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("locale".equals(nodeName)) {
                parseLocaleNode(child);
            }
        }
    }

    /**
     * ��`�t�@�C������͂��܂��B
     * 
     * @param rootChildren �h�L�������g�̎q�m�[�h
     * @throws ParseException ��͂Ɏ��s
     * @throws ClassNotFoundException �j���v�Z�N���X�̐����Ɏ��s
     * @throws IllegalAccessException �j���v�Z�N���X�̐����Ɏ��s
     * @throws InstantiationException �j���v�Z�N���X�̐����Ɏ��s
     */
    protected void parseNode(NodeList rootChildren) throws ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        if (rootChildren == null) {
            return;
        }

        int size = rootChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = rootChildren.item(i);
            String nodeName = child.getNodeName();

            if ("locales".equals(nodeName)) {
                if (getLocales() == null) {
                    setLocales(new ArrayList());
                }

                parseLocalesNode(child);
                // �t���[���P�[���̒ǉ�
                VRDateParserLocale locale = new VRDateParserLocale("", "", "",
                        "");
                getLocales().add(locale);
            } else if ("format".equals(nodeName)) {
                if (getFormats() == null) {
                    setFormats(new HashMap());
                }
                parseFormatNode(child);
            }
        }
    }

    /**
     * patterns�m�[�h����͂��܂��B
     * 
     * @param baseNode ���m�[�h
     * @throws ParseException �����̉�͎��s
     */
    protected void parsePatternsNode(Node baseNode) throws ParseException {
        if ((baseNode == null) || (!baseNode.hasChildNodes())) {
            return;
        }
        VRDateParserPatterns patterns = createPatterns();

        getFormats().put(getAttributeValue(baseNode, "locale"), patterns);

        NodeList baseChildren = baseNode.getChildNodes();

        int size = baseChildren.getLength();
        for (int i = 0; i < size; i++) {
            Node child = baseChildren.item(i);
            String nodeName = child.getNodeName();

            if ("pattern".equals(nodeName)) {
                patterns.addPattern(getNodeTextValue(child));
            }
        }
    }

}