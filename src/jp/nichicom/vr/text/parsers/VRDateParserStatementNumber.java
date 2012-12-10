/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * VRDateParserが使用する年月日等における数値用の暦構文クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserStatementable
 * @see VRDateParserStatementParseOption
 */
public class VRDateParserStatementNumber implements VRDateParserStatementable {
    public static final char DAY = 'd';

    public static final char ERA = 'e';

    public static final char MONTH = 'M';

    public static final char YEAR = 'y';

    public static final char HOUR = 'h';

    public static final char MINUTE = 'm';

    public static final char SECOND = 's';

    private int length; //桁(0なら無限)

    private char type; //y,M,d,e

    private static Pattern variableLegthNumPattern;

    /**
     * 数値として有効な形式を判断する正規表現パターンを返します。
     * 
     * @return 数値として有効な形式を判断する正規表現パターン
     */
    public Pattern getVariableLegthNumPattern() {
        if (variableLegthNumPattern == null) {
            //遅延生成
            variableLegthNumPattern = Pattern.compile("[0-9]+");
        }
        return variableLegthNumPattern;
    }

    /**
     * コンストラクタ
     * 
     * @param len 文字数
     */
    public VRDateParserStatementNumber(char type, int len) {
        setType(type);
        setLength(len);
    }

    /**
     * 文字数を返します。
     * 
     * @return 文字数
     */
    public int getLength() {
        return length;
    }

    /**
     * 形式を返します。
     * 
     * @return 形式
     */
    public char getType() {
        return type;
    }

    /**
     * 文字数を設定します。
     * 
     * @param length 文字数
     */
    public void setLength(int length) {
        if (length <= 0) {
            return;
        }
        this.length = length;
    }

    /**
     * 形式を設定します。
     * 
     * @param type 形式
     */
    public void setType(char type) {
        this.type = type;
    }

    public boolean isMatched(VRDateParserStatementParseOption option) {
        String target = option.getTarget();
        int i = option.getParseBeginIndex();
        int len = target.length();

        int txtLen = this.getLength();
        if (i + txtLen > len) {
            //桁数指定分の長さが残っていない
            return false;
        }

        Matcher matcher;
        if (txtLen > 1) {
            //桁数指定
            matcher = Pattern.compile("[0-9]{1," + txtLen + "}").matcher(
                    target.substring(i, i + txtLen));
        } else {
            //桁数未定
            matcher = getVariableLegthNumPattern().matcher(target.substring(i));
        }
        if ((!matcher.lookingAt())) {
            //マッチしなかった
            return false;
        }
        String find = matcher.group();
        int findData = Integer.parseInt(find);

        switch (this.getType()) {
        case VRDateParserStatementNumber.YEAR:
            if (txtLen > 1) {
                //桁数指定
                int digit = (int) Math.pow(10, txtLen);
                option.setYear(option.getNow().get(Calendar.YEAR) / digit * digit
                        + findData);
            } else {
                //桁数未定
                option.setYear(findData);
            }
            option.setUseYear(true);
            break;
        case VRDateParserStatementNumber.MONTH:
            option.setMonth(findData);
            option.setUseMonth(true);
            break;
        case VRDateParserStatementNumber.DAY:
            option.setDay(findData);
            option.setUseDay(true);
            break;
        case VRDateParserStatementNumber.ERA:
            option.setEra(findData);
            option.setUseEra(true);
            break;
        case VRDateParserStatementNumber.HOUR:
            option.setHour(findData);
            option.setUseHour(true);
            break;
        case VRDateParserStatementNumber.MINUTE:
            option.setMinute(findData);
            option.setUseMinute(true);
            break;
        case VRDateParserStatementNumber.SECOND:
            option.setSecond(findData);
            option.setUseSecond(true);
            break;
        }

        i += find.length();

        option.setParseEndIndex(i);
        return true;
    }
}