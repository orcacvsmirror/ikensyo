package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
/**
 * 意見書区分フォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/07/10
 */
public class IkenshoFormatTypeFormat extends Format{
    protected static IkenshoFormatTypeFormat singleton;
    protected static final String TYPE_NONE = "0";
    protected static final String TYPE_SHUJII_IKENSHO = "1";
    protected static final String TYPE_ISHI_IKENSHO = "2";
    protected static final String TEXT_NONE = "";
    protected static final String TEXT_SHUJII_IKENSHO = "主治医意見書";
    protected static final String TEXT_ISHI_IKENSHO = "医師意見書";

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected IkenshoFormatTypeFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     */
    public static IkenshoFormatTypeFormat getInstance() {
        if (singleton == null) {
            singleton = new IkenshoFormatTypeFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (TEXT_ISHI_IKENSHO.equals(source)) {
            return new Integer(TYPE_ISHI_IKENSHO);
        } else {
            return new Integer(TYPE_SHUJII_IKENSHO);
        }

    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        String val = String.valueOf(obj);

        if (TYPE_ISHI_IKENSHO.equals(val)) {
            toAppendTo.append(TEXT_ISHI_IKENSHO);
        } else {
            toAppendTo.append(TEXT_SHUJII_IKENSHO);
        }
        return toAppendTo;

    }
}
