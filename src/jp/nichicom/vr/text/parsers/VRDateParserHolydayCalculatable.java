/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Calendar;
import java.util.List;


/**
 * VRDateParserが使用する祝祭日特定インターフェースです。
 * <p>
 * 春分・秋分の日のように計算によって特定する祝祭日を定義する際に用います。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 */
public interface VRDateParserHolydayCalculatable {
    /**
     * 指定日に該当する祝日定義集合を返します。
     * 
     * @param cal 検査対象日
     * @param holydays 追加先の該当済みの休日集合
     * @param parameter 循環参照防止等に利用する自由領域。通常はnullを指定
     */
    void stockHolyday(Calendar cal, List holydays, Object parameter);
}