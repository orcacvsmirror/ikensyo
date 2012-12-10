package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.Calendar;
import java.util.Date;

/**
 * 日時を渡された場合に、現在日時と比較した年齢に変換するフォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACNowAgeFormat extends Format {
    private Date baseDate;

    /**
     * コンストラクタです。
     */
    public ACNowAgeFormat() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param baseDate 現在日時をあらわす基準年月日
     */
    public ACNowAgeFormat(Date baseDate) {
        super();
        setBaseDate(baseDate);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {

        Calendar target;
        if (obj instanceof Date) {
            target = Calendar.getInstance();
            target.setTime((Date) obj);
        } else if (obj instanceof Calendar) {
            target = ((Calendar) obj);
        } else {
            return toAppendTo;
        }

        Calendar now = Calendar.getInstance();
        if (getBaseDate() != null) {
            now.setTime(getBaseDate());
        }
        now.setLenient(true);
        now.add(Calendar.YEAR, 10);
        now.add(Calendar.DAY_OF_YEAR, -target.get(Calendar.DAY_OF_YEAR) + 1);
        now.add(Calendar.YEAR, -target.get(Calendar.YEAR));

        toAppendTo.append(now.get(Calendar.YEAR) - 10);
        return toAppendTo;
    }

    /**
     * 現在日時をあらわす基準年月日 を返します。
     * <p>
     * nullならばリアルタイムに更新される現在日時となります。
     * </p>
     * 
     * @return 現在日時をあらわす基準年月日
     */
    public Date getBaseDate() {
        return baseDate;
    }

    public Object parseObject(String source, ParsePosition pos) {
        Calendar now = Calendar.getInstance();
        if (getBaseDate() != null) {
            now.setTime(getBaseDate());
        }
        now.add(Calendar.YEAR, -Integer.valueOf(source).intValue());
        return now.getTime();
    }

    /**
     * 現在日時をあらわす基準年月日 を設定します。
     * <p>
     * nullならばリアルタイムに更新される現在日時となります。
     * </p>
     * 
     * @param baseDate 現在日時をあらわす基準年月日
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }
}
