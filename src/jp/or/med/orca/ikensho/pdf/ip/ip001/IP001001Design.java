
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
 * 作成日: 2006/04/27  日本コンピューター株式会社 田中　統蔵 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム 帳票 (P)
 * プロセス 簡易帳票カスタマイズツール (001)
 * プログラム 帳票選択 (IP001001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.*;
import java.awt.event.*;
import java.awt.im.*;
import java.io.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import jp.nichicom.ac.*;
import jp.nichicom.ac.bind.*;
import jp.nichicom.ac.component.*;
import jp.nichicom.ac.component.dnd.*;
import jp.nichicom.ac.component.dnd.event.*;
import jp.nichicom.ac.component.event.*;
import jp.nichicom.ac.component.mainmenu.*;
import jp.nichicom.ac.component.table.*;
import jp.nichicom.ac.component.table.event.*;
import jp.nichicom.ac.container.*;
import jp.nichicom.ac.core.*;
import jp.nichicom.ac.filechooser.*;
import jp.nichicom.ac.io.*;
import jp.nichicom.ac.lang.*;
import jp.nichicom.ac.pdf.*;
import jp.nichicom.ac.sql.*;
import jp.nichicom.ac.text.*;
import jp.nichicom.ac.util.*;
import jp.nichicom.ac.util.adapter.*;
import jp.nichicom.vr.*;
import jp.nichicom.vr.bind.*;
import jp.nichicom.vr.bind.event.*;
import jp.nichicom.vr.border.*;
import jp.nichicom.vr.component.*;
import jp.nichicom.vr.component.event.*;
import jp.nichicom.vr.component.table.*;
import jp.nichicom.vr.container.*;
import jp.nichicom.vr.focus.*;
import jp.nichicom.vr.image.*;
import jp.nichicom.vr.io.*;
import jp.nichicom.vr.layout.*;
import jp.nichicom.vr.text.*;
import jp.nichicom.vr.text.parsers.*;
import jp.nichicom.vr.util.*;
import jp.nichicom.vr.util.adapter.*;
import jp.nichicom.vr.util.logging.*;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
/**
 * 帳票選択画面項目デザイン(IP001001) 
 */
public class IP001001Design extends ACAffairDialog {
  //GUIコンポーネント

  private ACGroupBox contents;

  private ACComboBox formats;

  private ACLabelContainer formatsContainer;

  private ACComboBoxModelAdapter formatsModel;

  private ACListItem formatNewIkensho1;

  private ACListItem formatNewIkensho2;

  private ACListItem formatShijisho;

  private ACListItem formatShijishoB;

  private ACListItem formatSeikyuIkenshoIchiran;

  private ACListItem formatSeikyuIchiranTotal;

  private ACListItem formatSoukatusho;

  private ACListItem formatIkenshoMeisai;

  private ACListItem formatPatientList;

  private ACListItem formatSeikyuIchiran;

  private ACListItem formatCSVFileOutputPatientList;

  private ACPanel buttons;

  private ACButton ok;

  private ACButton cancel;

  //getter

  /**
   * 帳票の選択を取得します。
   * @return 帳票の選択
   */
  public ACGroupBox getContents(){
    if(contents==null){

      contents = new ACGroupBox();

      contents.setText("帳票の選択");

      addContents();
    }
    return contents;

  }

  /**
   * 帳票を取得します。
   * @return 帳票
   */
  public ACComboBox getFormats(){
    if(formats==null){

      formats = new ACComboBox();

      getFormatsContainer().setText("帳票");

      formats.setBindPath("FORMAT");

      formats.setEditable(false);

      formats.setRenderBindPath("FORMAT_TEXT");

      formats.setModel(getFormatsModel());

      addFormats();
    }
    return formats;

  }

  /**
   * 帳票コンテナを取得します。
   * @return 帳票コンテナ
   */
  protected ACLabelContainer getFormatsContainer(){
    if(formatsContainer==null){
      formatsContainer = new ACLabelContainer();
      formatsContainer.setFollowChildEnabled(true);
      formatsContainer.setVAlignment(VRLayout.CENTER);
      formatsContainer.add(getFormats(), VRLayout.CLIENT);
    }
    return formatsContainer;
  }

  /**
   * 帳票モデルを取得します。
   * @return 帳票モデル
   */
  protected ACComboBoxModelAdapter getFormatsModel(){
    if(formatsModel==null){
      formatsModel = new ACComboBoxModelAdapter();
      addFormatsModel();
    }
    return formatsModel;
  }

  /**
   * 主治医意見書1ページ目を取得します。
   * @return 主治医意見書1ページ目
   */
  public ACListItem getFormatNewIkensho1(){
    if(formatNewIkensho1==null){

      formatNewIkensho1 = new ACListItem();

      formatNewIkensho1.setText("主治医意見書1ページ目");

      formatNewIkensho1.setSimpleValueMode(false);
      formatNewIkensho1.put(getFormats().getRenderBindPath(), "主治医意見書1ページ目");
      formatNewIkensho1.put(getFormats().getBindPath(), new Integer(0));

      addFormatNewIkensho1();
    }
    return formatNewIkensho1;

  }

  /**
   * 主治医意見書2ページ目を取得します。
   * @return 主治医意見書2ページ目
   */
  public ACListItem getFormatNewIkensho2(){
    if(formatNewIkensho2==null){

      formatNewIkensho2 = new ACListItem();

      formatNewIkensho2.setText("主治医意見書2ページ目");

      formatNewIkensho2.setSimpleValueMode(false);
      formatNewIkensho2.put(getFormats().getRenderBindPath(), "主治医意見書2ページ目");
      formatNewIkensho2.put(getFormats().getBindPath(), new Integer(1));

      addFormatNewIkensho2();
    }
    return formatNewIkensho2;

  }

  /**
   * 訪問看護指示書（医療機関）を取得します。
   * @return 訪問看護指示書（医療機関）
   */
  public ACListItem getFormatShijisho(){
    if(formatShijisho==null){

      formatShijisho = new ACListItem();

      formatShijisho.setText("訪問看護指示書（医療機関）");

      formatShijisho.setSimpleValueMode(false);
      formatShijisho.put(getFormats().getRenderBindPath(), "訪問看護指示書（医療機関）");
      formatShijisho.put(getFormats().getBindPath(), new Integer(2));

      addFormatShijisho();
    }
    return formatShijisho;

  }

  /**
   * 訪問看護指示書（介護老人保健施設）を取得します。
   * @return 訪問看護指示書（介護老人保健施設）
   */
  public ACListItem getFormatShijishoB(){
    if(formatShijishoB==null){

      formatShijishoB = new ACListItem();

      formatShijishoB.setText("訪問看護指示書（介護老人保健施設）");

      formatShijishoB.setSimpleValueMode(false);
      formatShijishoB.put(getFormats().getRenderBindPath(), "訪問看護指示書（介護老人保健施設）");
      formatShijishoB.put(getFormats().getBindPath(), new Integer(3));

      addFormatShijishoB();
    }
    return formatShijishoB;

  }

  /**
   * 請求書一覧を取得します。
   * @return 請求書一覧
   */
  public ACListItem getFormatSeikyuIkenshoIchiran(){
    if(formatSeikyuIkenshoIchiran==null){

      formatSeikyuIkenshoIchiran = new ACListItem();

      formatSeikyuIkenshoIchiran.setText("請求書一覧");

      formatSeikyuIkenshoIchiran.setSimpleValueMode(false);
      formatSeikyuIkenshoIchiran.put(getFormats().getRenderBindPath(), "請求書一覧");
      formatSeikyuIkenshoIchiran.put(getFormats().getBindPath(), new Integer(4));

      addFormatSeikyuIkenshoIchiran();
    }
    return formatSeikyuIkenshoIchiran;

  }

  /**
   * 請求書合計を取得します。
   * @return 請求書合計
   */
  public ACListItem getFormatSeikyuIchiranTotal(){
    if(formatSeikyuIchiranTotal==null){

      formatSeikyuIchiranTotal = new ACListItem();

      formatSeikyuIchiranTotal.setText("請求書合計");

      formatSeikyuIchiranTotal.setSimpleValueMode(false);
      formatSeikyuIchiranTotal.put(getFormats().getRenderBindPath(), "請求書合計");
      formatSeikyuIchiranTotal.put(getFormats().getBindPath(), new Integer(5));

      addFormatSeikyuIchiranTotal();
    }
    return formatSeikyuIchiranTotal;

  }

  /**
   * 主治医意見書作成料・検査料請求(総括)書を取得します。
   * @return 主治医意見書作成料・検査料請求(総括)書
   */
  public ACListItem getFormatSoukatusho(){
    if(formatSoukatusho==null){

      formatSoukatusho = new ACListItem();

      formatSoukatusho.setText("主治医意見書作成料・検査料請求(総括)書");

      formatSoukatusho.setSimpleValueMode(false);
      formatSoukatusho.put(getFormats().getRenderBindPath(), "主治医意見書作成料・検査料請求(総括)書");
      formatSoukatusho.put(getFormats().getBindPath(), new Integer(6));

      addFormatSoukatusho();
    }
    return formatSoukatusho;

  }

  /**
   * 主治医意見書作成料請求(明細)書を取得します。
   * @return 主治医意見書作成料請求(明細)書
   */
  public ACListItem getFormatIkenshoMeisai(){
    if(formatIkenshoMeisai==null){

      formatIkenshoMeisai = new ACListItem();

      formatIkenshoMeisai.setText("主治医意見書作成料請求(明細)書");

      formatIkenshoMeisai.setSimpleValueMode(false);
      formatIkenshoMeisai.put(getFormats().getRenderBindPath(), "主治医意見書作成料請求(明細)書");
      formatIkenshoMeisai.put(getFormats().getBindPath(), new Integer(7));

      addFormatIkenshoMeisai();
    }
    return formatIkenshoMeisai;

  }

  /**
   * 登録患者一覧を取得します。
   * @return 登録患者一覧
   */
  public ACListItem getFormatPatientList(){
    if(formatPatientList==null){

      formatPatientList = new ACListItem();

      formatPatientList.setText("登録患者一覧");

      formatPatientList.setSimpleValueMode(false);
      formatPatientList.put(getFormats().getRenderBindPath(), "登録患者一覧");
      formatPatientList.put(getFormats().getBindPath(), new Integer(8));

      addFormatPatientList();
    }
    return formatPatientList;

  }

  /**
   * 請求対象意見書一覧を取得します。
   * @return 請求対象意見書一覧
   */
  public ACListItem getFormatSeikyuIchiran(){
    if(formatSeikyuIchiran==null){

      formatSeikyuIchiran = new ACListItem();

      formatSeikyuIchiran.setText("請求対象意見書一覧");

      formatSeikyuIchiran.setSimpleValueMode(false);
      formatSeikyuIchiran.put(getFormats().getRenderBindPath(), "請求対象意見書一覧");
      formatSeikyuIchiran.put(getFormats().getBindPath(), new Integer(9));

      addFormatSeikyuIchiran();
    }
    return formatSeikyuIchiran;

  }

  /**
   * ＣＳＶファイル提出患者一覧を取得します。
   * @return ＣＳＶファイル提出患者一覧
   */
  public ACListItem getFormatCSVFileOutputPatientList(){
    if(formatCSVFileOutputPatientList==null){

      formatCSVFileOutputPatientList = new ACListItem();

      formatCSVFileOutputPatientList.setText("ＣＳＶファイル提出患者一覧");

      formatCSVFileOutputPatientList.setSimpleValueMode(false);
      formatCSVFileOutputPatientList.put(getFormats().getRenderBindPath(), "ＣＳＶファイル提出患者一覧");
      formatCSVFileOutputPatientList.put(getFormats().getBindPath(), new Integer(10));

      addFormatCSVFileOutputPatientList();
    }
    return formatCSVFileOutputPatientList;

  }

  /**
   * ボタン領域を取得します。
   * @return ボタン領域
   */
  public ACPanel getButtons(){
    if(buttons==null){

      buttons = new ACPanel();

      addButtons();
    }
    return buttons;

  }

  /**
   * OKを取得します。
   * @return OK
   */
  public ACButton getOk(){
    if(ok==null){

      ok = new ACButton();

      ok.setText("OK");

      ok.setToolTipText("選択した帳票を開きます。");

      ok.setMnemonic('O');

      ok.setIconPath(ACConstants.ICON_PATH_YES_24);

      addOk();
    }
    return ok;

  }

  /**
   * キャンセルを取得します。
   * @return キャンセル
   */
  public ACButton getCancel(){
    if(cancel==null){

      cancel = new ACButton();

      cancel.setText("キャンセル(C)");

      cancel.setToolTipText("処理を取りやめます。");

      cancel.setMnemonic('C');

      cancel.setIconPath(ACConstants.ICON_PATH_CANCEL_24);

      addCancel();
    }
    return cancel;

  }

  /**
   * コンストラクタです。
   */
  public IP001001Design() {

    super(ACFrame.getInstance(), true);
    this.getContentPane().setLayout(new VRLayout());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    try {
      initialize();

      pack();

      // ウィンドウを中央に配置
      Point pos;
      try{
          pos= ACFrame.getInstance().getLocationOnScreen();
      }catch(Exception ex){
          pos = new Point(0,0);
      }
      Dimension screenSize = ACFrame.getInstance().getSize();
      Dimension frameSize = this.getSize();
      if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
      }
      this.setLocation((int)(pos.getX()+(screenSize.width - frameSize.width) / 2),
              (int)(pos.getY()+(screenSize.height - frameSize.height) / 2));

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 自身の設定を行ないます。
   */
  protected void initThis(){
  }

  /**
   * thisに内部項目を追加します。
   */
  protected void addThis(){

    this.getContentPane().add(getContents(), VRLayout.CLIENT);

    this.getContentPane().add(getButtons(), VRLayout.SOUTH);
  }

  /**
   * 帳票の選択に内部項目を追加します。
   */
  protected void addContents(){

    contents.add(getFormatsContainer(), VRLayout.EAST);
  }

  /**
   * 帳票に内部項目を追加します。
   */
  protected void addFormats(){

  }

  /**
   * 帳票モデルに内部項目を追加します。
   */
  protected void addFormatsModel(){

    getFormatsModel().add(getFormatNewIkensho1());

    getFormatsModel().add(getFormatNewIkensho2());

    getFormatsModel().add(getFormatShijisho());

    getFormatsModel().add(getFormatShijishoB());

    getFormatsModel().add(getFormatSeikyuIkenshoIchiran());

    getFormatsModel().add(getFormatSeikyuIchiranTotal());

    getFormatsModel().add(getFormatSoukatusho());

    getFormatsModel().add(getFormatIkenshoMeisai());

    getFormatsModel().add(getFormatPatientList());

    getFormatsModel().add(getFormatSeikyuIchiran());

    getFormatsModel().add(getFormatCSVFileOutputPatientList());

  }

  /**
   * 主治医意見書1ページ目に内部項目を追加します。
   */
  protected void addFormatNewIkensho1(){

  }

  /**
   * 主治医意見書2ページ目に内部項目を追加します。
   */
  protected void addFormatNewIkensho2(){

  }

  /**
   * 訪問看護指示書（医療機関）に内部項目を追加します。
   */
  protected void addFormatShijisho(){

  }

  /**
   * 訪問看護指示書（介護老人保健施設）に内部項目を追加します。
   */
  protected void addFormatShijishoB(){

  }

  /**
   * 請求書一覧に内部項目を追加します。
   */
  protected void addFormatSeikyuIkenshoIchiran(){

  }

  /**
   * 請求書合計に内部項目を追加します。
   */
  protected void addFormatSeikyuIchiranTotal(){

  }

  /**
   * 主治医意見書作成料・検査料請求(総括)書に内部項目を追加します。
   */
  protected void addFormatSoukatusho(){

  }

  /**
   * 主治医意見書作成料請求(明細)書に内部項目を追加します。
   */
  protected void addFormatIkenshoMeisai(){

  }

  /**
   * 登録患者一覧に内部項目を追加します。
   */
  protected void addFormatPatientList(){

  }

  /**
   * 請求対象意見書一覧に内部項目を追加します。
   */
  protected void addFormatSeikyuIchiran(){

  }

  /**
   * ＣＳＶファイル提出患者一覧に内部項目を追加します。
   */
  protected void addFormatCSVFileOutputPatientList(){

  }

  /**
   * ボタン領域に内部項目を追加します。
   */
  protected void addButtons(){

    buttons.add(getCancel(), VRLayout.EAST);
    buttons.add(getOk(), VRLayout.EAST);
  }

  /**
   * OKに内部項目を追加します。
   */
  protected void addOk(){

  }

  /**
   * キャンセルに内部項目を追加します。
   */
  protected void addCancel(){

  }

  /**
   * コンポーネントを初期化します。
   * @throws Exception 初期化例外
   */
  private void initialize() throws Exception {
    initThis();
    addThis();
  }
  public boolean canBack(VRMap parameters) throws Exception {
    return true;
  }
  public Component getFirstFocusComponent() {

    return null;

  }
  public void initAffair(ACAffairInfo affair) throws Exception {
  }

  public void setVisible(boolean visible){
    if(visible){
      try{
        initAffair(null);
      }catch(Throwable ex){
        ACCommon.getInstance().showExceptionMessage(ex);
      }
    }
    super.setVisible(visible);
  }
  public static void main(String[] args) {
    //デフォルトデバッグ起動
    try {
      ACFrame.setVRLookAndFeel();
      ACFrame.getInstance().setFrameEventProcesser(new IkenshoFrameEventProcesser());
      new IP001001Design().setVisible(true);
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 自身を返します。
   */
  protected IP001001Design getThis() {
    return this;
  }
}
