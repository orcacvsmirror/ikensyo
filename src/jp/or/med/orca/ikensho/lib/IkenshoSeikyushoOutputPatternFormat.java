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
 * 開発者: 田中統蔵
 * 作成日: 2005/12/01  日本コンピュータ株式会社 田中統蔵 新規作成
 * 更新日: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSeikyushoOutputPatternFormat extends Format {
    protected static IkenshoSeikyushoOutputPatternFormat singleton;
    protected static final Integer CONST_0 = new Integer(0);
    protected static final Integer CONST_1 = new Integer(1);
    protected static final Integer CONST_2 = new Integer(2);
    protected static final Integer CONST_3 = new Integer(3);
    protected static final Integer CONST_4 = new Integer(4);
    protected static final String Pattern0 = "";
    protected static final String Pattern1 = "意見書作成料・検査料(1枚)";
    protected static final String Pattern2 = "意見書作成料・検査料(2枚)";
    protected static final String Pattern3 = "意見書作成料のみ";
    protected static final String Pattern4 = "検査料のみ";

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected IkenshoSeikyushoOutputPatternFormat(){
      super();
    }
    /**
     * インスタンスを取得します。
     */
    public static IkenshoSeikyushoOutputPatternFormat getInstance(){
      if(singleton==null){
        singleton = new IkenshoSeikyushoOutputPatternFormat();
      }
      return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (Pattern0.equals(source)) {
            return new Integer(0);
        }else if (Pattern1.equals(source)) {
            return new Integer(1);
        }else if (Pattern2.equals(source)) {
            return new Integer(2);
        } else if (Pattern3.equals(source)) {
            return new Integer(3);
        } else if (Pattern4.equals(source)) {
            return new Integer(4);
        }
        return null;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if (CONST_0.equals(obj)) {
            toAppendTo.append(Pattern0);
        } else if (CONST_1.equals(obj)) {
            toAppendTo.append(Pattern1);
        } else if (CONST_2.equals(obj)) {
            toAppendTo.append(Pattern2);
        } else if (CONST_3.equals(obj)) {
            toAppendTo.append(Pattern3);
        } else if (CONST_4.equals(obj)) {
            toAppendTo.append(Pattern4);
        }
        return toAppendTo;
    }
}
