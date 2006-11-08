package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * 保険者一覧で使用する保険者区分フォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/07/06
 */
public class IkenshoInsurerTypeFormat extends Format {
    protected static IkenshoInsurerTypeFormat singleton;
    protected static final String TYPE_NONE = "0";
    protected static final String TYPE_ONLY_SHUJII_IKENSHO = "1";
    protected static final String TYPE_ONLY_ISHI_IKENSHO = "2";
    protected static final String TEXT_NONE = "";
    protected static final String TEXT_ONLY_SHUJII_IKENSHO = "主のみ";
    protected static final String TEXT_ONLY_ISHI_IKENSHO = "医のみ";

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected IkenshoInsurerTypeFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     */
    public static IkenshoInsurerTypeFormat getInstance() {
        if (singleton == null) {
            singleton = new IkenshoInsurerTypeFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (TEXT_ONLY_SHUJII_IKENSHO.equals(source)) {
            return new Integer(TYPE_ONLY_SHUJII_IKENSHO);
        } else if (TEXT_ONLY_ISHI_IKENSHO.equals(source)) {
            return new Integer(TYPE_ONLY_ISHI_IKENSHO);
        } else {
            return new Integer(TYPE_NONE);
        }

    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        String val = String.valueOf(obj);

        if (TYPE_ONLY_SHUJII_IKENSHO.equals(val)) {
            toAppendTo.append(TEXT_ONLY_SHUJII_IKENSHO);
        } else if (TYPE_ONLY_ISHI_IKENSHO.equals(val)) {
            toAppendTo.append(TEXT_ONLY_ISHI_IKENSHO);
        } else {
            toAppendTo.append(TEXT_NONE);
        }
        return toAppendTo;

    }
}
