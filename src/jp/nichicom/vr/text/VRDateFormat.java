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
 * 年月日の表現形式をカスタマイズ可能な日付フォーマットクラスです。
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
     * コンストラクタ
     */
    public VRDateFormat() {

    }

    /**
     * コンストラクタ
     * 
     * @param format 出力書式
     */
    public VRDateFormat(String format) {
        setFormat(format);
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param date 変換対象
     * @return フォーマット済み文字列
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     */
    public String format(Date date) throws SAXException, IOException,
            ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.format(date, getFormat());
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param date 変換対象
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
    public String format(Date date, Locale locale) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.format(date, getFormat(), locale);
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param date 変換対象
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
    public String format(Date date, String format) throws SAXException,
            IOException, ParserConfigurationException, ParseException,
            InstantiationException, IllegalAccessException,
            ClassNotFoundException {
        return VRDateParser.format(date, format);
    }

    /**
     * 日付を指定の書式にフォーマットして返します。
     * 
     * @param obj 変換対象
     * @param toAppendTo 追加先
     * @param pos 解析位置
     * @return 解析結果
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
     * 出力書式を返します。
     * 
     * @return 出力書式
     */
    public String getFormat() {
        return format;
    }

    /**
     * 文字列を解析して日付として返します。
     * 
     * @param text 解析対象
     * @return 日付
     * @throws ParseException 解析失敗
     * @throws ClassNotFoundException 祝日計算クラスの生成に失敗
     * @throws IllegalAccessException 祝日計算クラスの生成に失敗
     * @throws InstantiationException 祝日計算クラスの生成に失敗
     * @throws ParserConfigurationException 解析準備失敗
     * @throws SAXException 定義ファイルのSAX解析失敗
     * @throws IOException 定義ファイルの入力失敗
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
     * 文字列を解析して日付として返します。
     * 
     * @param text 解析対象
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
            // 末尾まで成功扱い
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
     * 出力書式を設定します。
     * 
     * @param format 出力書式
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