package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * SQLの構築を想定した文字列フォーマットです。
 * <p>
 * データがnullのときは"NULL"を出力します。<br />
 * データがnullでなければ、'データ'を出力します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACSQLSafeStringFormat extends Format {
    protected static ACSQLSafeStringFormat singleton;

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ACSQLSafeStringFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACSQLSafeStringFormat getInstance() {
        if (singleton == null) {
            singleton = new ACSQLSafeStringFormat();
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
            toAppendTo.append("'");
            toAppendTo.append(String.valueOf(obj).replaceAll("'", "''"));
            toAppendTo.append("'");
        }
        return toAppendTo;

    }
}
