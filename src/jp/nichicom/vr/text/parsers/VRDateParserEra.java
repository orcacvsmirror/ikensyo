/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;


/**
 * VRDateParserが使用する元号クラスです。
 * <p>
 * 元号の範囲や文字"g"の個数で特定される略称を定義できます。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see Locale
 */
public class VRDateParserEra {
    private static final String NAME_ABBREVIATION = "ggg";

    private static final int NAME_ABBREVIATION_TYPE = NAME_ABBREVIATION
            .length();

    private HashMap abbreviation;

    private Calendar begin;

    private Calendar end;

    /**
     * コンストラクタ
     * @param locale ロケール
     */
    public VRDateParserEra(Locale locale) {
        this.begin = Calendar.getInstance(locale);
        this.begin.set(1, 0, 1, 0, 0, 0);
        this.begin.set(Calendar.MILLISECOND, 0);
        this.end = Calendar.getInstance(locale);
        this.end.set(9999, 11, 31, 23, 59, 59);
        this.end.set(Calendar.MILLISECOND, 0);
        this.abbreviation = new HashMap();
    }

    /**
     * 略称を返します。
     * 
     * @param length 対応書式文字数
     */
    public String getAbbreviation(int length) {
        return (String) abbreviation.get(new Integer(length));
    }

    /**
     * 開始日を返します。
     * 
     * @return 開始日
     */
    public Calendar getBegin() {
        return begin;
    }

    /**
     * 終了日を返します。
     * 
     * @return 終了日
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * 略称を設定します。
     * 
     * @param type 対応書式
     * @param value 略称
     */
    public void setAbbreviation(String type, String value) {
        abbreviation.put(new Integer(type.length()), value);
    }

    /**
     * 開始日を設定します。
     * 
     * @param begin 開始日
     */
    public void setBegin(Calendar begin) {
        this.begin.set(begin.get(Calendar.YEAR), begin.get(Calendar.MONTH),
                begin.get(Calendar.DATE), 0, 0, 0);
    }

    /**
     * 終了日を設定します。
     * 
     * @param end 終了日
     */
    public void setEnd(Calendar end) {
        this.end.set(end.get(Calendar.YEAR), end.get(Calendar.MONTH), end
                .get(Calendar.DATE), 23, 59, 59);
    }

}