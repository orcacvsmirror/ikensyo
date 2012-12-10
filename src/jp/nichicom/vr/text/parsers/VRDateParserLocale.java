/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Locale;


/**
 * VRDateParserが使用する暦ロケールクラスです。
 * <p>
 * ロケールごとに異なる祝祭日定義を保持します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 * @see VRDateParserHolydayTerm
 */
public class VRDateParserLocale {
    private ArrayList eras;

    private VRDateParserHolydays holydays;

    private String id;

    private Locale locale;

    /**
     * コンストラクタ
     * 
     * @param id 名称
     * @param language 言語
     * @param country 国
     * @param variant 補助
     */
    public VRDateParserLocale(String id, String language, String country,
            String variant) {
        this.id = id;
        this.locale = new Locale(language, country, variant);
        this.eras = new ArrayList();
        this.holydays = new VRDateParserHolydays();

    }

    /**
     * 元号定義を追加します。
     * 
     * @param era 元号定義
     */
    public void addEra(VRDateParserEra era) {
        this.getEras().add(era);
    }

    /**
     * 元号定義集合を返します。
     * 
     * @return 元号定義集合
     */
    public ArrayList getEras() {
        return eras;
    }

    /**
     * 祝日定義母体 を返します。
     * 
     * @return 祝日定義母体
     */
    public VRDateParserHolydays getHolydays() {
        return holydays;
    }

    /**
     * ロケール名称を返します。
     * 
     * @return ロケール名称
     */
    public String getId() {
        return id;
    }

    /**
     * ロケールを返します。
     * 
     * @return ロケール
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * 元号定義集合を設定します。
     * 
     * @param eras 元号定義集合
     */
    public void setEras(ArrayList eras) {
        this.eras = eras;
    }

    /**
     * 祝日定義母体 を設定します。
     * 
     * @param holydays 祝日定義母体
     */
    public void setHolydays(VRDateParserHolydays holydays) {
        this.holydays = holydays;
    }

    /**
     * ロケール名称を設定します。
     * 
     * @param id ロケール名称
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ロケールを設定します。
     * 
     * @param locale ロケール
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}