package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
/**
 * 訪問看護指示書区分フォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2009/09/07
 */
public class ShijishoFormatTypeFormat extends Format {
    protected static ShijishoFormatTypeFormat singleton;
    protected static final String TYPE_HOUMONKANGO_SHIJISHO = "0";
    protected static final String TYPE_TOKUBETSU_HOUMONKANGO_SHIJISHO = "1";
    protected static final String TEXT_HOUMONKANGO_SHIJISHO = "訪問看護指示書";
    protected static final String TEXT_TOKUBETSU_HOUMONKANGO_SHIJISHO = "特別訪問看護指示書";

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ShijishoFormatTypeFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     */
    public static ShijishoFormatTypeFormat getInstance() {
        if (singleton == null) {
            singleton = new ShijishoFormatTypeFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (TEXT_TOKUBETSU_HOUMONKANGO_SHIJISHO.equals(source)) {
            return new Integer(TYPE_TOKUBETSU_HOUMONKANGO_SHIJISHO);
        } else {
            return new Integer(TYPE_HOUMONKANGO_SHIJISHO);
        }

    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        String val = String.valueOf(obj);

        if (TYPE_TOKUBETSU_HOUMONKANGO_SHIJISHO.equals(val)) {
            toAppendTo.append(TEXT_TOKUBETSU_HOUMONKANGO_SHIJISHO);
        } else {
            toAppendTo.append(TEXT_HOUMONKANGO_SHIJISHO);
        }
        return toAppendTo;

    }
}
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
