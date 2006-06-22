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
public class IkenshoSexFormat extends Format {
  protected static IkenshoSexFormat singleton;
  protected static final Integer CONST_1 = new Integer(1);
  protected static final Integer CONST_2 = new Integer(2);
  /**
   * コンストラクタです。
   * Singleton Pattern
   */
  protected IkenshoSexFormat(){
    super();
  }
  /**
   * インスタンスを取得します。
   * @return インスタンス
   */
  public static IkenshoSexFormat getInstance(){
    if(singleton==null){
      singleton = new IkenshoSexFormat();
    }
    return singleton;
  }
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    try{
      obj = Integer.valueOf(String.valueOf(obj));
      if(CONST_1.equals(obj)){
        toAppendTo.append("男");
      }else if(CONST_2.equals(obj)){
        toAppendTo.append("女");
      }
    }catch(Exception ex){

    }
    return toAppendTo;
  }
  public Object parseObject(String source, ParsePosition pos) {
    if("男".equals(source)){
      return new Integer(1);
    }else if("女".equals(source)){
      return new Integer(2);
    }
    return null;
  }
}
