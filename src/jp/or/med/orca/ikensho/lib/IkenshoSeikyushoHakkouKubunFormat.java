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
public class IkenshoSeikyushoHakkouKubunFormat
    extends Format {
  protected static IkenshoSeikyushoHakkouKubunFormat singleton;
  protected static final Integer CONST_1 = new Integer(1);
  protected static final Integer CONST_2 = new Integer(2);
  protected static final Integer CONST_3 = new Integer(3);

  /**
   * コンストラクタです。<br />
   * Singleton Pattern
   */
  protected IkenshoSeikyushoHakkouKubunFormat() {
    super();
  }

  /**
   * インスタンスを取得します。
   */
  public static IkenshoSeikyushoHakkouKubunFormat getInstance() {
    if (singleton == null) {
      singleton = new IkenshoSeikyushoHakkouKubunFormat();
    }
    return singleton;
  }

  public StringBuffer format(Object obj, StringBuffer toAppendTo,
                             FieldPosition pos) {
    if (CONST_1.equals(obj)) {
      toAppendTo.append("未発行");
    }
    else if (CONST_2.equals(obj)) {
      toAppendTo.append("発行済");
    }
    else if (CONST_3.equals(obj)) {
      toAppendTo.append("保留");
    }else{
      toAppendTo.append("−");
    }
    return toAppendTo;
  }

  public Object parseObject(String source, ParsePosition pos) {
    if ("未発行".equals(source)) {
      return new Integer(1);
    }
    else if ("発行済".equals(source)) {
      return new Integer(2);
    }
    else if ("保留".equals(source)) {
      return new Integer(3);
    }else{
      return new Integer(0);
    }
  }

}
