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
 * SQLの構築を想定した日付フォーマットです。
 * <p>
 * データがnullのときは"NULL"を出力します。<br />
 * データがnullでなければ、'書式化した日付'を出力します。
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
     * コンストラクタです。
     */
    public ACSQLSafeDateFormat() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param format 出力書式
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
     * 日付を指定の書式にフォーマットして返します。
     * <p>
     * データがnullであれば"NULL"を返します。
     * </p>
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
