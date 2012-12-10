/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


/**
 * VRDateParserHolydaysが使用する祝祭日条件定義クラスです。
 * <p>
 * 祝祭日の条件を期間で定義します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 * @see VRDateParserHolydays
 * @see VRDateParserHolyday
 */
public class VRDateParserHolydayTerm implements VRDateParserHolydayCalculatable {
    private static final ArrayList EMPTY_ARRAY = new ArrayList();

    private Calendar begin;

    private Calendar end;

    private VRDateParserHolyday holyday;

    /**
     * コンストラクタ
     */
    public VRDateParserHolydayTerm() {
        begin = Calendar.getInstance();
        begin.setTimeInMillis(Long.MIN_VALUE);
        end = Calendar.getInstance();
        end.setTimeInMillis(Long.MAX_VALUE);
        holyday = new VRDateParserHolyday();
    }

    /**
     * 有効期限の開始日 を返します。
     * 
     * @return 有効期限の開始日
     */
    public Calendar getBegin() {
        return begin;
    }

    /**
     * 有効期限の終了日 を返します。
     * 
     * @return 有効期限の終了日
     */
    public Calendar getEnd() {
        return end;
    }

    /**
     * 祝祭日定義 を返します。
     * 
     * @return 祝祭日定義
     */
    public VRDateParserHolyday getHolyday() {
        return holyday;
    }

    /**
     * 有効期限の開始日 を設定します。
     * 
     * @param begin 有効期限の開始日
     */
    public void setBegin(Calendar begin) {
        this.begin = begin;
    }

    /**
     * 有効期限の終了日 を設定します。
     * 
     * @param end 有効期限の終了日
     */
    public void setEnd(Calendar end) {
        this.end = end;
    }

    /**
     * 祝祭日定義 を設定します。
     * 
     * @param holyday 祝祭日定義
     */
    public void setHolyday(VRDateParserHolyday holyday) {
        this.holyday = holyday;
    }

    public void stockHolyday(Calendar cal, List holydays, Object parameter) {
        if (cal != null) {
            //期限内であれば有効
            if (cal.before(getEnd()) && cal.after(getBegin())) {
                holydays.add(holyday);
            }
        }
    }
}