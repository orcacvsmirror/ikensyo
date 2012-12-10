package jp.nichicom.ac.text;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * 小数点第1位までの少数に変換するフォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACOneDecimalDoubleFormat extends Format {
    protected static ACOneDecimalDoubleFormat singleton;
    private boolean underZeroCut = true;

    /**
     * 小数点第1が0の場合、整数部までの表示と を返します。
     * 
     * @return 小数点第1が0の場合、整数部までの表示と
     */
    public boolean isUnderZeroCut() {
        return underZeroCut;
    }

    /**
     * 小数点第1が0の場合、整数部までの表示とするかを設定します。
     * 
     * @param underZeroCut 小数点第1が0の場合、整数部までの表示と
     */
    public void setUnderZeroCut(boolean underZeroCut) {
        this.underZeroCut = underZeroCut;
    }

    /**
     * インスタンスを取得します。
     * 
     * @return インスタンス
     */
    public static ACOneDecimalDoubleFormat getInstance() {
        if (singleton == null) {
            singleton = new ACOneDecimalDoubleFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if ("".equals(source)) {
            pos.setIndex(-1);
            return null;
        }

        pos.setIndex(source.length());
        return Double.valueOf(source);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj == null) {
            return toAppendTo;
        }
        String valText = String.valueOf(obj);
        if ("".equals(valText)) {
            return toAppendTo;
        }

        double dblValue = Double.parseDouble(valText);
        if (isUnderZeroCut()) {
            int intValue = (int) dblValue;
            double dblTmp = Double.parseDouble(String.valueOf(intValue));

            if (dblValue == dblTmp) {
                toAppendTo.append(new DecimalFormat("#").format(dblValue));
            } else {
                toAppendTo.append(new DecimalFormat("#.#").format(dblValue));
            }
        } else {
            toAppendTo.append(new DecimalFormat("#.#").format(dblValue));
        }

        return toAppendTo;
    }
}
