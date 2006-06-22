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
public class IkenshoBankAccountTypeFormat extends Format {
  protected static IkenshoBankAccountTypeFormat singleton;
  protected static final Integer CONST_1 = new Integer(1);
  protected static final Integer CONST_0 = new Integer(0);
  /**
   * コンストラクタです。<br />
   * Singleton Pattern
   */
  protected IkenshoBankAccountTypeFormat(){
    super();
  }
  /**
   * インスタンスを取得します。
   */
  public static IkenshoBankAccountTypeFormat getInstance(){
    if(singleton==null){
      singleton = new IkenshoBankAccountTypeFormat();
    }
    return singleton;
  }
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    if (CONST_1.equals(obj)) {
      toAppendTo.append("普通");
    }
    else if (CONST_0.equals(obj)) {
      toAppendTo.append("当座");
    }
    return toAppendTo;
  }
  public Object parseObject(String source, ParsePosition pos) {
    if("普通".equals(source)){
      return new Integer(1);
    }else if("当座".equals(source)){
      return new Integer(0);
    }else{
      return new Integer(2);
    }
  }


}
