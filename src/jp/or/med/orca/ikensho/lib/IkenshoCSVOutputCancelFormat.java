/*
 * Project code name "ORCA"
 * 主治医意見書作成ソフト ITACHI（JMA IKENSYO software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: ITACHI
 * 開発者: 堤瑞樹
 * 作成日: 2005/12/01  日本コンピュータ株式会社 堤瑞樹 新規作成
 * 更新日: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/** TODO <HEAD_IKENSYO> */
public class IkenshoCSVOutputCancelFormat extends Format {
    protected static IkenshoCSVOutputCancelFormat singleton;
    protected static final Boolean CONST_TRUE = new Boolean(true);
    protected static final Boolean CONST_FALSE = new Boolean(false);

    /**
     * コンストラクタです。
     * Singleton Pattern
     */
    protected IkenshoCSVOutputCancelFormat() {
        super();
    }

    /**
     * インスタンスを取得します。
     * @return インスタンス
     */
    public static IkenshoCSVOutputCancelFormat getInstance() {
        if (singleton == null) {
            singleton = new IkenshoCSVOutputCancelFormat();
        }
        return singleton;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (CONST_TRUE.equals(obj)) {
            toAppendTo.append("取消");
        }
        else if (CONST_FALSE.equals(obj)) {
            toAppendTo.append("");
        }
        return toAppendTo;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if ("取消".equals(source)) {
            return CONST_TRUE;
        }
        else {
            return CONST_FALSE;
        }
    }
}
