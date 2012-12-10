package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * SQLの構築を想定した数値フォーマットです。
 * <p>
 * データがnullまたは空文字のときは"0"を出力します。<br />
 * データがnullでなければ、文字列化した数値を出力します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see Format
 */
public class ACSQLSafeNullToZeroIntegerFormat extends Format {
    protected static ACSQLSafeNullToZeroIntegerFormat singleton;

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ACSQLSafeNullToZeroIntegerFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACSQLSafeNullToZeroIntegerFormat getInstance() {
        if (singleton == null) {
            singleton = new ACSQLSafeNullToZeroIntegerFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if((source==null)||("".equals( source))){
            return new Integer(0);
        }
        return Integer.valueOf(source);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if ((obj == null) || ("".equals(obj))) {
            toAppendTo.append("0");
        } else {
            toAppendTo.append(String.valueOf(obj));
        }
        return toAppendTo;

    }
}
