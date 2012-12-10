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
 * 年月日の表現形式をカスタマイズ可能な日付解析・変換クラスです。
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
     * ロケール指定なしを表す言語ロケール定数です。
     */
    public static Locale FREE_LOCALE = new Locale("", "", "");

    private static VRDateParser singleton;

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param target 変換対象
     * @param format 書式
     * @return フォーマット済み文字列
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static String format(Calendar target, String format)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return format(target, format, Locale.getDefault());
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param target 変換対象
     * @param format 書式
     * @param locale 対象ロケール
     * @return フォーマット済み文字列
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static String format(Calendar target, String format, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().formatImpl(target, format, locale);
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param target 変換対象
     * @param format 書式
     * @return フォーマット済み文字列
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static String format(Date target, String format)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return format(target, format, Locale.getDefault());
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param target 変換対象
     * @param format 書式
     * @param locale 対象ロケール
     * @return フォーマット済み文字列
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * 指定日時が該当するデフォルトロケールにおける元号を返します。
     * 
     * @param date 日時
     * @return 元号
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * 指定日時が該当する指定ロケールにおける元号を返します。
     * 
     * @param date 日時
     * @param locale ロケール
     * @return 元号
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static VRDateParserEra getEra(Calendar date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getEra(date, locale);
    }

    /**
     * 指定日時が該当するデフォルトロケールにおける元号を返します。
     * 
     * @param date 日時
     * @return 元号
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * 指定日時が該当する指定ロケールにおける元号を返します。
     * 
     * @param date 日時
     * @param locale ロケール
     * @return 元号
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * デフォルトロケールの元号集合を返します。
     * 
     * @return 元号集合
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static ArrayList getEras() throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getEras(Locale.getDefault());
    }

    /**
     * 指定ロケールの元号集合を返します。
     * 
     * @param locale ロケール
     * @return 元号集合
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static ArrayList getEras(Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().getErasImpl(locale);
    }

    /**
     * 指定日時が該当する指定ロケールの祝日を集合として返します。
     * 
     * @param locale ロケール
     * @param date 日時
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     * @return 祝日集合
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
     * 指定日時が該当する祝日を集合として返します。
     * 
     * @param date 日時
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     * @return 祝日集合
     */
    public static ArrayList getHolydays(Date date) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getHolydays(date, Locale.getDefault());
    }

    /**
     * 指定日時が該当する祝日を集合として返します。
     * 
     * @param date 日時
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     * @return 祝日集合
     */
    public static ArrayList getHolydays(Calendar date) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getHolydays(date, Locale.getDefault());
    }

    /**
     * 指定日時が該当する指定ロケールの祝日を集合として返します。
     * 
     * @param locale ロケール
     * @param date 日時
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     * @return 祝日集合
     */
    public static ArrayList getHolydays(Calendar date, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return getInstance().getHolydaysImpl(date, locale);
    }

    /**
     * インスタンスを返します。
     * <p>
     * singleton pattern
     * </p>
     * 
     * @return インスタンス
     */
    public static VRDateParser getInstance() {
        if (singleton == null) {
            singleton = new VRDateParser();
        }
        return singleton;
    }

    /**
     * 指定ロケールを含むロケール暦定義を返します。
     * 
     * @param locale 比較ロケール
     * @return ロケール暦定義
     */
    public static VRDateParserLocale getLocale(Locale locale) {
        return getInstance().getLocaleImpl(locale);
    }

    /**
     * 文字列を日付として解析可能かを返します。
     * 
     * @param target 解析対象
     * @return 日付として解釈可能か
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
     * 文字列を解析して日付として返します。
     * 
     * @param target 解析対象
     * @return 日付
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public static Date parse(String target) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return parse(target, Locale.getDefault());
    }

    /**
     * 文字列を解析して日付として返します。
     * 
     * @param target 解析対象
     * @param locale 対象ロケール
     * @return 日付
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * コンストラクタです。
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
     * 定義情報を初期化します。
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
     * XML文書生成器を返します。
     * 
     * @return XML文書生成器
     */
    public VRDateParserDocumentCreatable getDocumentCreator() {
        return documentCreator;
    }

    /**
     * 書式集合を返します。
     * 
     * @return 書式集合
     */
    public HashMap getFormats() {
        return formats;
    }

    /**
     * ロケール暦定義集合を返します。
     * 
     * @return ロケール暦定義集合
     */
    public ArrayList getLocales() {
        return locales;
    }

    /**
     * 定義ファイルを再読み込みします。
     * 
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public void reload() throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        clear();

        VRDateParserDocumentCreatable docC = getDocumentCreator();
        if (docC == null) {
            // 指定されていなければデフォルトクラスで対応
            docC = new VRDateParserDocumentFile();
        }
        Document doc = docC.getDefinedDocument();
        if (doc == null) {
            throw new IOException("XML文書オブジェクトを取得できません。");
        }

        // ルート要素の取得
        // 子要素を含んだノードを列挙
        NodeList nodeChildren = doc.getDocumentElement().getChildNodes();
        parseNode(nodeChildren);
    }

    /**
     * XML文書生成器を設定します。
     * 
     * @param documentCreator XML文書生成器
     */
    public void setDocumentCreator(VRDateParserDocumentCreatable documentCreator) {
        this.documentCreator = documentCreator;
    }

    /**
     * 書式集合を設定します。
     * 
     * @param formats 書式集合
     */
    public void setFormats(HashMap formats) {
        this.formats = formats;
    }

    /**
     * ロケール暦定義集合を設定します。
     * 
     * @param locales ロケール暦定義集合
     */
    public void setLocales(ArrayList locales) {
        this.locales = locales;
    }

    /**
     * VRDateParserPatternsインスタンスを生成します。 <br />
     * factory method Pattern
     * 
     * @return インスタンス
     */
    protected VRDateParserPatterns createPatterns() {
        return new VRDateParserPatterns();
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param target 変換対象
     * @param format 書式
     * @param locale 対象ロケール
     * @return フォーマット済み文字列
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * ノードの属性値を返します。
     * 
     * @param node 処理対象のノード
     * @param attrName 属性名
     * @return ノードの属性値
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
     * 内部解析用のパーサフォーマットを返します。 <br />
     * factory method Pattern
     * 
     * @return パーサフォーマット
     */
    protected SimpleDateFormat getBaseDateFormat() {
        return simpleDateFormat;
    }

    /**
     * 指定ロケール内の指定日が属する元号定義を返します。
     * 
     * @param locale 対象ロケール
     * @param target 対象日
     * @return 元号定義
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
     * 指定日時が該当する指定ロケールの祝日を集合として返します。
     * 
     * @param locale ロケール
     * @param date 日時
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     * @return 祝日集合
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
     * 指定ロケールの元号集合を返します。
     * 
     * @return 元号集合
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    protected ArrayList getErasImpl(Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        if (getFormats().size() == 0) {
            reload();
        }

        if (locale == null) {
            // ロケール指定なし → 空ロケール
            return new ArrayList();
        }

        // ロケール指定あり
        Iterator it = getLocales().iterator();
        while (it.hasNext()) {
            VRDateParserLocale loc = (VRDateParserLocale) it.next();
            Locale locLocale = loc.getLocale();
            if (locLocale.equals(locale)) {
                return loc.getEras();
            }
        }
        throw new ParseException("ロケール未定義のため、解析失敗", 0);

    }

    /**
     * 指定ロケールを含むロケール暦定義を返します。
     * 
     * @param locale 比較ロケール
     * @return ロケール暦定義
     */
    protected VRDateParserLocale getLocaleImpl(Locale locale) {
        if (locale == null) {
            return null;
        }

        // ロケール指定あり
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
     * ノードが持つ最初のテキスト要素を文字列として返します。
     * 
     * @param node 処理対象のノード
     * @return テキスト要素値
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
     * 文字列を解析して日付として返します。
     * 
     * @param target 解析対象
     * @param locale 対象ロケール
     * @return 日付
     * @throws ParseException 元号範囲日付の解析失敗
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
     * eraノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @param locale ロケール
     * @throws ParseException 元号範囲日付の解析失敗
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
     * erasノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @param locale ロケール
     * @throws ParseException 元号範囲日付の解析失敗
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
     * 指定した書式を構文単位に分割して返します。
     * 
     * @param target 変換対象
     * @param format 書式
     * @param locale 対象ロケール
     * @return 構文単位に分割した書式
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    protected ArrayList parseFormat(Calendar target, String format,
            Locale locale) throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        final char QUOTE = '\'';
        // クォートでくくっているか
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
                    // クォート以降が切り出し対象
                    quoteStatus = QUOTING;
                    break;
                case QUOTING:
                    // クォート中のクォート → 直前までが切り出し対象
                    quoteStatus = QUOTED;
                    break;
                case QUOTED:
                    // クォート終了と思ったのに続けてクォート → クォートエスケープ
                    quoteStatus = QUOTING;
                    break;
                }
                // switch後の処理は行わず、次の文字を走査する
                continue;
            }
            if (quoteStatus == QUOTING) {
                // クォート中 → スキップ
                continue;
            }

            VRDateParserFormatStatement nextStatement = null;
            int contCnt = 0;
            switch (format.charAt(i)) {
            case VRDateParserFormatStatement.ERA_TEXT:
                // 元号ラベル
                contCnt = VRDateParserPattern.getContinationCount(format,
                        i + 1, len, VRDateParserFormatStatement.ERA_TEXT);
                nextStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.ERA_TEXT, contCnt);
                useEraText = true;
                break;
            case VRDateParserFormatStatement.ERA_YEAR:
                // 元号年
                contCnt = VRDateParserPattern.getContinationCount(format,
                        i + 1, len, VRDateParserFormatStatement.ERA_YEAR);
                nextStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.ERA_YEAR, contCnt);
                useEraYear = true;
                break;
            case VRDateParserFormatStatement.HOLYDAY:
                // 祝日
                contCnt = VRDateParserPattern.getContinationCount(format,
                        i + 1, len, VRDateParserFormatStatement.HOLYDAY);
                nextStatement = new VRDateParserFormatStatement(
                        VRDateParserFormatStatement.HOLYDAY, contCnt);
                useHolyday = true;
                break;
            default:
                // リテラル
                // switch後の処理は行わず、次の文字を走査する
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
            // リテラル文字で終了した場合
            VRDateParserFormatStatement literalStatement = new VRDateParserFormatStatement(
                    VRDateParserFormatStatement.LITERAL, len - cellBeginPos);
            literalStatement.setText(format.substring(cellBeginPos, len));
            result.add(literalStatement);
        }

        // 元号や祝日を使用している場合
        if (useEraYear || useEraText || useHolyday) {
            // フォーマット読み込み
            if (getFormats().size() == 0) {
                reload();
            }

            VRDateParserLocale targetLocale;
            VRDateParserLocale freeLocale;

            if (locale == null) {
                // ロケール指定なし → 空ロケール
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
                throw new ParseException("ロケール定義が存在しません。" + format + " ]", 0);
            }

            // ここまでの結果をキャッシュして再解析の手間を省いてもよい

            VRDateParserEra targetEra = null;
            String holydayText = "";

            // 元号使用
            if (useEraYear || useEraText) {
                targetEra = getEra(targetLocale, target);
                if (targetEra == null) {
                    if (freeLocale == null) {
                        throw new ParseException("元号を定義したロケールが存在しません。" + format
                                + " ]", 0);
                    }
                    targetEra = getEra(freeLocale, target);
                    if (targetEra == null) {
                        throw new ParseException("指定の日付が元号の範囲内にありません。[ "
                                + format + " ](Target="
                                + getBaseDateFormat().format(target.getTime())
                                + ")", 0);
                    }
                }
            }
            // 祝日使用
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
                        throw new ParseException("指定の元号略称は定義されていません。[ "
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
     * formatノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @throws ParseException 書式の解析失敗
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
     * holydayノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @param locale ロケール
     * @throws ParseException 元号範囲日付の解析失敗
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
            throw new ParseException("祝日の対象月指定が不正です。", 0);
        }
        if (d > 0) {
            // 固定日
            if (d > 31) {
                throw new ParseException("祝日の対象日指定が不正です。", 0);
            }
            locale.getHolydays().addDayHolydayTerm(term, m, d);
        } else {
            // 固定週
            if ((week <= 0) || (week > 5) || (wday <= 0) || (wday > 7)) {
                throw new ParseException("祝日の対象週指定が不正です。", 0);
            }
            locale.getHolydays().addWeekHolydayTerm(term, m, week, wday);
        }

    }

    /**
     * holydaysノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @param locale ロケール
     * @throws ParseException 元号範囲日付の解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
                    VRLogger.info("祝祭日定義型["+getAttributeValue(child, "id")
                            + "]のインスタンス化に失敗しました。");
                }

            }
        }
    }

    /**
     * 文字列を解析して日付として返します。
     * 
     * @param target 解析対象
     * @param locale 対象ロケール
     * @return 日付
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    protected Date parseImpl(String target, Locale locale)
            throws SAXException, IOException, ParserConfigurationException,
            ParseException, InstantiationException, IllegalAccessException,
            ClassNotFoundException {

        if (getFormats().size() == 0) {
            reload();
        }

        if (locale == null) {
            // ロケール指定なし → 空ロケール
            locale = FREE_LOCALE;
        }

        // ロケール指定あり
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
        throw new ParseException("フォーマット未定義のため、解析失敗", 0);
    }

    /**
     * localeノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @throws ParseException 解析に失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * localesノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @throws ParseException 解析に失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
     * 定義ファイルを解析します。
     * 
     * @param rootChildren ドキュメントの子ノード
     * @throws ParseException 解析に失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
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
                // フリーロケールの追加
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
     * patternsノードを解析します。
     * 
     * @param baseNode 基底ノード
     * @throws ParseException 書式の解析失敗
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