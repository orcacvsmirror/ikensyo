/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;


/**
 * VRDateParserが使用するVRDateParserPatternの管理クラスです。
 * <p>
 * 複数の文字列解析パターンを集合として保持します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserLocale
 * @see VRDateParserPattern
 */
public class VRDateParserPatterns {

    /**
     * VRDateParserPatternインスタンスを生成します。 factory method Pattern
     * 
     * @return インスタンス
     */
    protected static VRDateParserPattern createPattern() {
        return new VRDateParserPattern();
    }

    private ArrayList patterns;

    /**
     * デフォルトコンストラクタ
     */
    public VRDateParserPatterns() {
        patterns = new ArrayList();

    }

    /**
     * パターンを追加します。
     * 
     * @param pattern パターン
     * @throws ParseException 書式の解析失敗
     */
    public void addPattern(String pattern) throws ParseException {
        if ((pattern == null) || ("".equals(pattern))) {
            return;
        }
        VRDateParserPattern patternInstance = createPattern();
        patternInstance.setPattern(pattern);
        patterns.add(patternInstance);
    }

    /**
     * パターン集合を返します。
     * 
     * @return パターン集合
     */
    public ArrayList getPatterns() {
        return patterns;
    }

    /**
     * 文字列を解析して日付として返します。
     * 
     * @param target 解析対象
     * @param locale 対象ロケール
     * @return 日付
     * @throws ParseException 解析失敗
     */
    public Calendar match(String target, VRDateParserLocale locale)
            throws ParseException {

        Iterator it = getPatterns().iterator();
        while (it.hasNext()) {
            Calendar cal = ((VRDateParserPattern) it.next()).match(target,
                    locale.getEras(), locale.getLocale());
            if (cal != null) {
                return cal;
            }
        }
        return null;
    }

    /**
     * パターン集合を設定します。
     * 
     * @param patterns パターン集合
     */
    public void setPatterns(ArrayList patterns) {
        this.patterns = patterns;
    }
}