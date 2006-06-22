
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
 * 開発者: 田中　統蔵
 * 作成日: 2006/04/25  日本コンピューター株式会社 田中　統蔵 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム 帳票 (P)
 * プロセス 簡易帳票カスタマイズツール (001)
 * プログラム 簡易帳票カスタマイズツール (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
/**
 * 簡易帳票カスタマイズツール状態定義(IP001) 
 */
public class IP001State extends IP001Design {
  /**
   * コンストラクタです。
   */
  public IP001State(){
  }

  /**
   * 「ファイル読み込み済み」の状態に設定します。
   * @throws Exception 処理例外
   */
  public void setState_FILE_OPENED() throws Exception {

        getPrint().setEnabled(true);

        getSave().setEnabled(true);

  }

  /**
   * 「ファイル読み込み待ち」の状態に設定します。
   * @throws Exception 処理例外
   */
  public void setState_FILE_CLOSED() throws Exception {

        getPrint().setEnabled(false);

        getSave().setEnabled(false);

  }

}
