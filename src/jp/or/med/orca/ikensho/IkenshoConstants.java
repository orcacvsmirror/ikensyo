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
package jp.or.med.orca.ikensho;

import java.awt.Color;
import java.awt.Font;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACHashMapFormat;
import jp.nichicom.ac.text.ACNowAgeFormat;
import jp.nichicom.ac.text.ACNullToBlankFormat;
import jp.nichicom.ac.text.ACOneDecimalDoubleFormat;
import jp.nichicom.ac.text.ACSQLSafeDateFormat;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.bridge.pdf.BridgeSimplePDF;
import jp.nichicom.vr.text.VRCharType;
import jp.or.med.orca.ikensho.lib.IkenshoBankAccountTypeFormat;
import jp.or.med.orca.ikensho.lib.IkenshoCSVOutputCancelFormat;
import jp.or.med.orca.ikensho.lib.IkenshoFDOutputStatusFormat;
import jp.or.med.orca.ikensho.lib.IkenshoFDOutputUmuFormat;
import jp.or.med.orca.ikensho.lib.IkenshoSeikyushoHakkouKubunFormat;
import jp.or.med.orca.ikensho.lib.IkenshoSeikyushoOutputPatternFormat;
import jp.or.med.orca.ikensho.lib.IkenshoShinryoujyoTypeFormat;

/**
 *意見書定数一覧です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface IkenshoConstants {
  public static final Color FRAME_BACKGROUND = new java.awt.Color(0, 51, 153);

  public static final String AFFAIR_MODE_UPDATE = "U";
  public static final String AFFAIR_MODE_INSERT = "I";
  public static final String DOC_KBN_IKENSHO = "1";
  public static final String DOC_KBN_SIJISHO = "2";

  public static final int IKENSHO_LOW_DEFAULT = 0;
  public static final int IKENSHO_LOW_H18 = 1;
  public static final int IKENSHO_LOW_ISHI_IKENSHO = 2;

  public static final String BUTTON_IMAGE_PATH_BACK =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_40.png";
  public static final String BUTTON_IMAGE_PATH_DELETE =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_57.png";
  public static final String BUTTON_IMAGE_PATH_FIND =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_83.png";
  public static final String BUTTON_IMAGE_PATH_UPDATE =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_29.png";
  public static final String BUTTON_IMAGE_PATH_TABLE_PRINT =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_61.png";
  public static final String BUTTON_IMAGE_PATH_PRINT =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_46.png";
  public static final String BUTTON_IMAGE_PATH_DETAIL =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_43.png";
  public static final String BUTTON_IMAGE_PATH_NEW =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_13.png";
  public static final String BUTTON_IMAGE_PATH_COPY =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_34.png";
  public static final String BUTTON_IMAGE_PATH_OPEN =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_31.png";
  public static final String BUTTON_IMAGE_PATH_EXPORT =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_32.png";
  public static final String BUTTON_IMAGE_PATH_IKENSYO =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_84.png";
  public static final String BUTTON_IMAGE_PATH_SIJISYO =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_85.png";
  public static final String BUTTON_IMAGE_PATH_LEFT =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_01.png";
  public static final String BUTTON_IMAGE_PATH_RIGHT =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_02.png";
  public static final String BUTTON_IMAGE_PATH_BACK_TO_MAIN_MENU =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_101.png"; //メインメニューに戻る //2009/01/06 [Mizuki Tsutsumi] : add

  public static final String BUTTON_IMAGE_PATH_OK =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_27.png";
  public static final String BUTTON_IMAGE_PATH_CANCEL =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_26.png";
  public static final String BUTTON_IMAGE_PATH_YES = BUTTON_IMAGE_PATH_OK;
  public static final String BUTTON_IMAGE_PATH_NO =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_28.png";

  public static final String MESSAGE_BUTTON_IMAGE_PATH_INFORMATION =
      "jp/or/med/orca/ikensho/images/icon/pix48/btn_71.png";
  public static final String MESSAGE_BUTTON_IMAGE_PATH_QUESTION =
      "jp/or/med/orca/ikensho/images/icon/pix48/btn_09.png";
  public static final String MESSAGE_BUTTON_IMAGE_PATH_EXCLAMATION =
      "jp/or/med/orca/ikensho/images/icon/pix48/btn_08.png";

  public static final String IMAGE_PATH_HINT =
      "jp/or/med/orca/ikensho/images/icon/pix24/btn_08.png";
  public static final String IMAGE_PATH_HINT_BUTTON =
      "jp/or/med/orca/ikensho/images/icon/pix16/btn_09.png";

  public static final String BODY_IMAGE_PATH_BODY =
      "jp/or/med/orca/ikensho/images/body/body.png";
  public static final String BODY_IMAGE_PATH_AREA =
      "jp/or/med/orca/ikensho/images/body/area.png";
  public static final String BODY_IMAGE_PATH_DOT =
      "jp/or/med/orca/ikensho/images/body/dot.png";
  public static final String BODY_IMAGE_PATH_DOWN_LINE =
      "jp/or/med/orca/ikensho/images/body/downline.png";
  public static final String BODY_IMAGE_PATH_ELLIPSE =
      "jp/or/med/orca/ikensho/images/body/ellipse.png";
  public static final String BODY_IMAGE_PATH_FILL_DOWN_LINE =
      "jp/or/med/orca/ikensho/images/body/filldownline.png";
  public static final String BODY_IMAGE_PATH_FILL_RISE_LINE =
      "jp/or/med/orca/ikensho/images/body/fillriseline.png";
  public static final String BODY_IMAGE_PATH_HORIZONTAL_LINE =
      "jp/or/med/orca/ikensho/images/body/horizontalline.png";
  public static final String BODY_IMAGE_PATH_LEGEND_DASH =
      "jp/or/med/orca/ikensho/images/body/legenddash.png";
  public static final String BODY_IMAGE_PATH_LEGEND_DOWN =
      "jp/or/med/orca/ikensho/images/body/legenddown.png";
  public static final String BODY_IMAGE_PATH_LEGEND_FILL =
      "jp/or/med/orca/ikensho/images/body/legendfill.png";
  public static final String BODY_IMAGE_PATH_LEGEND_LINE =
      "jp/or/med/orca/ikensho/images/body/legendline.png";
  public static final String BODY_IMAGE_PATH_LEGEND_RISE =
      "jp/or/med/orca/ikensho/images/body/legendrise.png";
  public static final String BODY_IMAGE_PATH_RISE_LINE =
      "jp/or/med/orca/ikensho/images/body/riseline.png";
  public static final String BODY_IMAGE_PATH_TEXT =
      "jp/or/med/orca/ikensho/images/body/text.png";
  public static final String BODY_IMAGE_PATH_VERTICAL_LINE =
      "jp/or/med/orca/ikensho/images/body/verticalline.png";


  public static final Font FONT_TITLE = new java.awt.Font("Dialog",
      java.awt.Font.PLAIN, 20);
  public static final Color COLOR_TITLE_FOREGROUND = java.awt.Color.white;
  public static final Color COLOR_TOOL_BUTTON_FOREGROUND = java.awt.Color.white;
  public static final Color COLOR_TOOL_BUTTON_BACKGROUND = new java.awt.Color(0, 51,
      153);

  public static final Color COLOR_BUTTON_YELLOW_FOREGROUND = new java.awt.Color(255,
      204, 102);
  public static final Color COLOR_BUTTON_ORANGE_FOREGROUND = new java.awt.Color(255,
      153, 102);
  public static final Color COLOR_BUTTON_GREEN_FOREGROUND = new java.awt.Color(153,
      204, 0);

  public static final Color COLOR_BORDER_TEXT_FOREGROUND = new java.awt.Color(49, 83,
      152);
  public static final Color COLOR_MESSAGE_TEXT_FOREGROUND = new java.awt.Color(49, 83,
      255);
  public static final Color COLOR_MESSAGE_ALART_TEXT_FOREGROUND = new java.awt.Color(255, 0,
      0);
  public static final Color COLOR_MESSAGE_WARNING_TEXT_FOREGROUND = new java.awt.Color(160, 0,
      0);
  // 2008/02/29 [Masahiko_Higuchi] edit - begin version3.0.7 背景色の変更 
  public static final Color COLOR_RANGE_PANEL_BACKGROUND = new java.awt.Color(255, 255, 150);
  // 2008/02/29 [Masahiko_Higuchi] edit - end

  public static final Color COLOR_BACK_PANEL_BACKGROUND = new java.awt.Color(204, 204, 255);
  public static final Color COLOR_BACK_PANEL_FOREGROUND = Color.black;

  public static final Color COLOR_DOUBLE_BACK_PANEL_BACKGROUND = new Color(
      0xC5EDC5);


  public static final Color CONTAINER_DEFAULT_FOREGROUND = Color.black;
  public static final Color CONTAINER_ERROR_FOREGROUND = Color.white;
  public static final Color CONTAINER_WARNING_FOREGROUND = Color.black;

  public static final Color CONTAINER_DEFAULT_BACKGROUND = Color.black;
  public static final Color CONTAINER_ERROR_BACKGROUND = Color.red;
  public static final Color CONTAINER_WARNING_BACKGROUND = Color.yellow;

  public static final Color COLOR_PANEL_TITLE_FOREGROUND = java.awt.Color.white;
  public static final Color COLOR_PANEL_TITLE_BACKGROUND =new java.awt.Color(0, 51,
      153);



  public static final ACDateFormat FORMAT_YMD_HMS = new ACDateFormat(
      "yyyy/M/d HH:mm:ss");
  public static final ACDateFormat FORMAT_YMD = new ACDateFormat(
      "yyyy/M/d");
  public static final ACDateFormat FORMAT_YM = new ACDateFormat(
      "yyyy/M");
  public static final ACDateFormat FORMAT_ERA_YMD = new ACDateFormat(
      "gggee年MM月dd日");
  public static final ACDateFormat FORMAT_ERA_HMS = new ACDateFormat(
      "gggee年MM月dd日 HH:mm:ss");
  public static final ACHashMapFormat/**NCSexFormat*/ FORMAT_SEX = new ACHashMapFormat(new String[]{"男","女"}, new Integer[]{new Integer(1), new Integer(2), });
//      NCSexFormat.      getInstance();
  public static final IkenshoSeikyushoHakkouKubunFormat FORMAT_HAKKOU = IkenshoSeikyushoHakkouKubunFormat.
      getInstance();
  public static final IkenshoFDOutputStatusFormat FORMAT_FD_OUTPUT =
      IkenshoFDOutputStatusFormat.getInstance();
  public static final ACNowAgeFormat FORMAT_NOW_AGE = new ACNowAgeFormat();
  public static final ACNullToBlankFormat FORMAT_NOT_NULL =
      ACNullToBlankFormat.getInstance();
  public static final IkenshoFDOutputUmuFormat FORMAT_UMU =
      IkenshoFDOutputUmuFormat.getInstance();
  public static final IkenshoBankAccountTypeFormat FORMAT_BANK_ACCOUNT_TYPE =
      IkenshoBankAccountTypeFormat.getInstance();
  public static final IkenshoSeikyushoOutputPatternFormat FORMAT_SEIKYUSHO_OUTPUT_PATTERN =
      IkenshoSeikyushoOutputPatternFormat.getInstance();
  public static final ACOneDecimalDoubleFormat FORMAT_DOUBLE1 =
      ACOneDecimalDoubleFormat.getInstance();
  public static final IkenshoShinryoujyoTypeFormat FORMAT_SHINRYOUJYO_TYPE =
      IkenshoShinryoujyoTypeFormat.getInstance();
  public static final VRCharType CHAR_TYPE_DOUBLE1 = new VRCharType("CHAR_TYPE_DOUBLE1", "(\\d+)|((\\d+)(\\.\\d{0,1}))");

  public static final IkenshoCSVOutputCancelFormat FORMAT_CANCEL = IkenshoCSVOutputCancelFormat.getInstance();

  public static final BridgeSimplePDF PDF_WRITER = new BridgeSimplePDF();

  public static final ACSQLSafeDateFormat FORMAT_PASSIVE_YMD_HMS = new
      ACSQLSafeDateFormat("yyyy/M/d HH:mm:ss");
  public static final ACSQLSafeDateFormat FORMAT_PASSIVE_YMD = new
      ACSQLSafeDateFormat("yyyy/M/d");
  public static final ACSQLSafeStringFormat FORMAT_PASSIVE_STRING = ACSQLSafeStringFormat.getInstance();

  public static final String PASSIVE_CHECK_ERROR_MESSAGE =
      "偶然にも他の端末からデータが登録されていました。\n妥当性確保の為、処理を中止します。";

  public static final String FILE_SEPARATOR = System.getProperty("file.separator");
  public static final String LINE_SEPARATOR = System.getProperty("line.separator");
  public static final String PRINT_PDF_PATH = ACFrame.getInstance().getExeFolderPath() + IkenshoConstants.FILE_SEPARATOR + "printPDF.pdf";
  public static final String PRINT_PDF_DIRECTORY = ACFrame.getInstance().getExeFolderPath() + IkenshoConstants.FILE_SEPARATOR + "pdf" + IkenshoConstants.FILE_SEPARATOR;


}
