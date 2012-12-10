
/*
 * Project code name "ORCA"
 * 給付管理台帳ソフト QKANCHO（JMA care benefit management software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "QKANCHO (JMA care benefit management software)".
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
 * アプリ: QKANCHO
 * 開発者: ログビューアー
 * 作成日: 2012/09/20  日本コンピューター株式会社 ログビューアー 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム ログ (L)
 * プロセス ログビューアー (001)
 * プログラム ログビューア (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;
import jp.nichicom.ac.text.ACSQLSafeIntegerFormat;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/**
 * ログビューアSQL定義(IL001) 
 */
public class IL001SQL extends IL001State {
	private static final long serialVersionUID = 1L;
	
  /**
   * コンストラクタです。
   */
  public IL001SQL() {
  }

  /**
   * 「患者情報を取得する」のためのSQLを返します。
   * @param sqlParam SQL構築に必要なパラメタを格納したハッシュマップ
   * @throws Exception 処理例外
   * @return SQL文
   */
  public String getSQL_GET_PATIENT_INFO(VRMap sqlParam) throws Exception{
    StringBuffer sb = new StringBuffer();

    sb.append("SELECT");

    sb.append(" PATIENT_NM");

    sb.append(",PATIENT_KN");

    sb.append(" FROM");

    sb.append(" PATIENT");

    sb.append(" WHERE");

    sb.append("(");

    sb.append(" PATIENT_NO");

    sb.append(" =");

    sb.append(ACSQLSafeIntegerFormat.getInstance().format(VRBindPathParser.get("PATIENT_NO", sqlParam)));

    sb.append(")");

    return sb.toString();
  }

}
