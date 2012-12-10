package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 時分形式に対応したフォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */
public class ACTimeFormat extends Format {
    /**
     * Date型をあらわす解析結果定数です。
     */
    public static final int VALUE_TYPE_DATE = 1;
    /**
     * 文字列をあらわす解析結果定数です。
     */
    public static final int VALUE_TYPE_STRING = 0;
    private Date baseDate = new Date();
    private SimpleDateFormat formatBaseFormater;
    private String formatedFormat;
    private String pargedFormat;
    private SimpleDateFormat parseBaseFormater;
    private int parsedValueType = ACTimeFormat.VALUE_TYPE_DATE;

    /**
     * コンストラクタです。
     */
    public ACTimeFormat() {
        this("HH:mm", "HH:mm");
    }

    /**
     * コンストラクタです。
     * 
     * @param formatedFormat 出力フォーマット
     */
    public ACTimeFormat(String formatedFormat) {
        this(formatedFormat, "HH:mm");
    }

    /**
     * コンストラクタです。
     * 
     * @param formatedFormat 出力フォーマット
     * @param parsedFormat 出力フォーマット
     */
    public ACTimeFormat(String formatedFormat, String parsedFormat) {
        super();
        setFormatedFormat(formatedFormat);
        setPargedFormat(parsedFormat);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (formatBaseFormater != null) {
            Date target = null;
            if (obj instanceof Calendar) {
                target = ((Calendar) obj).getTime();
            } else if (obj instanceof Date) {
                target = (Date) obj;
            } else if(obj!=null){
                try {
                    target = toDate(String.valueOf(obj));
                } catch (ParseException e) {
                    // 処理失敗
                    return toAppendTo;
                }
            }
            if (target == null) {
                return toAppendTo;
            }
            return formatBaseFormater.format(target, toAppendTo, pos);
        }
        return toAppendTo;
    }

    /**
     * 解析結果をDate型とする場合に基準となる日付 を返します。
     * 
     * @return 解析結果をDate型とする場合に基準となる日付
     */
    public Date getBaseDate() {
        return baseDate;
    }

    /**
     * 出力書式 を返します。
     * 
     * @return 出力書式
     */
    public String getFormatedFormat() {
        return formatedFormat;
    }

    /**
     * 解析結果を文字列とする場合の出力書式 を返します。
     * 
     * @return 解析結果を文字列とする場合の出力書式
     */
    public String getPargedFormat() {
        return pargedFormat;
    }

    /**
     * 解析結果の型 を返します。
     * <p>
     * VALUE_TYPE_STRING : 文字列<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @return 解析結果の型
     */
    public int getParsedValueType() {
        return parsedValueType;
    }

    public Object parseObject(String source, ParsePosition pos) {
        
        Date target;
        try {
            target = toDate(source);
        } catch (ParseException e) {
            return null;
        }
        if (target != null) {
            pos.setIndex(source.length());
        }else{
            //解析失敗でも空文字ならエラーにしない
            pos.setIndex(1);
        }

        switch (getParsedValueType()) {
        case ACTimeFormat.VALUE_TYPE_DATE:
            return target;
        case ACTimeFormat.VALUE_TYPE_STRING:
            return parseBaseFormater.format(target);
        }
        return null;
    }

    /**
     * 解析結果をDate型とする場合に基準となる日付 を設定します。
     * 
     * @param baseDate 解析結果をDate型とする場合に基準となる日付
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
    }

    /**
     * 出力書式 を設定します。
     * 
     * @param formatedFormat 出力書式
     */
    public void setFormatedFormat(String formatedFormat) {
        this.formatedFormat = formatedFormat;
        formatBaseFormater = new SimpleDateFormat(formatedFormat);
    }

    /**
     * 解析結果を文字列とする場合の出力書式 を設定します。
     * 
     * @param pargedFormat 解析結果を文字列とする場合の出力書式
     */
    public void setPargedFormat(String pargedFormat) {
        this.pargedFormat = pargedFormat;
        parseBaseFormater = new SimpleDateFormat(pargedFormat);
    }

    /**
     * 解析結果の型 を設定します。
     * <p>
     * VALUE_TYPE_STRING : 文字列<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @param parsedValueType 解析結果の型
     */
    public void setParsedValueType(int parsedValueType) {
        this.parsedValueType = parsedValueType;
    }

    /**
     * 時刻形式の文字列をDate型に変換します。
     * 
     * @param source 変換元
     * @return 変換結果
     * @throws ParseException 処理例外
     */
    protected Date toDate(String source) throws ParseException {
        if (source == null) {
            return null;
        }
        int sourcceLength = source.length();
        if (sourcceLength == 0) {
            return null;
        }
        Calendar cal = Calendar.getInstance();

        // "時"を":"に置換し、セパレータを統一する
        source = source.replaceAll("時", ":");

        if (source.charAt(0) == ':') {
            // 先頭が時刻区切りで始まる→分のみ

            // "分"を除去して数値のみ
            source = source.replaceAll("分", "");
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(1)));
            return cal.getTime();
        }

        int minuteUnitPos = source.indexOf("分");
        if (minuteUnitPos == 0) {
            // 先頭に分単位は解釈不能
            throw new ParseException(source, 0);
        }
        boolean useMinuteUnit = minuteUnitPos > 0;

        int hourUnitPos = source.indexOf(":");
        if (hourUnitPos == 0) {
            // 先頭に時単位は解釈不能
            throw new ParseException(source, 0);
        }
        boolean useHourUnit = hourUnitPos > 0;

        if (useHourUnit) {
            // h時*
            // 時分単位の関係が逆の場合、解析例外となる
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(source.substring(0,
                    hourUnitPos)));
            if (useMinuteUnit) {
                // h時m分
                cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(
                        hourUnitPos + 1, minuteUnitPos)));
            } else if (hourUnitPos + 1 < source.length()) {
                // h時m
                cal.set(Calendar.MINUTE, Integer.parseInt(source
                        .substring(hourUnitPos + 1)));
            } else {
                cal.set(Calendar.MINUTE, 0);
            }
            return cal.getTime();
        }
        if (useMinuteUnit) {
            // m分
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(0,
                    minuteUnitPos)));
            return cal.getTime();
        }

        // 時分単位なし
        switch (source.length()) {
        case 1:
        case 2: {
            // h
            int h = Integer.parseInt(source);
            if (h > 23) {
                throw new ParseException(source, 0);
            }
            cal.set(Calendar.HOUR_OF_DAY, h);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
            return cal.getTime();
        }
        case 3:
            // hmm
            cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(source.substring(0, 1)));
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(1)));
            return cal.getTime();
        case 4:
            // hhmm
            int h = Integer.parseInt(source.substring(0, 2));
            if (h > 23) {
                throw new ParseException(source, 0);
            }
            cal.set(Calendar.HOUR_OF_DAY, h);
            cal.set(Calendar.MINUTE, Integer.parseInt(source.substring(2)));
            return cal.getTime();
        }
        return null;
    }

}
