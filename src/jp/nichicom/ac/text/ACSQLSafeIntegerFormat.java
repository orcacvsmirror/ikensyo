package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * SQLの構築を想定した整数フォーマットです。
 * <p>
 * データがnullのときは"NULL"を出力します。<br />
 * データがnullでなければ、数値を文字列化して出力します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see Format
 */
public class ACSQLSafeIntegerFormat extends Format {
    protected static ACSQLSafeIntegerFormat singleton;

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ACSQLSafeIntegerFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACSQLSafeIntegerFormat getInstance() {
        if (singleton == null) {
            singleton = new ACSQLSafeIntegerFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        return source;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if ((obj == null) || ("".equals(obj))) {
            toAppendTo.append("NULL");
        } else {
            toAppendTo.append(String.valueOf(obj));
        }
        return toAppendTo;

    }
}
