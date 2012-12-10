package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * SQLの構築を想定した小数値フォーマットです。
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
public class ACSQLSafeNullToZeroDoubleFormat extends Format {
    protected static ACSQLSafeNullToZeroDoubleFormat singleton;

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ACSQLSafeNullToZeroDoubleFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACSQLSafeNullToZeroDoubleFormat getInstance() {
        if (singleton == null) {
            singleton = new ACSQLSafeNullToZeroDoubleFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if ((source == null) || ("".equals(source))) {
            return new Double(0);
        }
        return Double.valueOf(source);
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
