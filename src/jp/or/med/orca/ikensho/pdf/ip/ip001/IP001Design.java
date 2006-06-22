
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
 * 作成日: 2006/05/01  日本コンピューター株式会社 田中　統蔵 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム 帳票 (P)
 * プロセス 簡易帳票カスタマイズツール (001)
 * プログラム 簡易帳票カスタマイズツール (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
/**
 * 簡易帳票カスタマイズツール画面項目デザイン(IP001) 
 */
public class IP001Design extends ACAffairContainer implements ACAffairable {
  //GUIコンポーネント

  private ACAffairButtonBar buttons;

  private ACAffairButton open;

  private ACAffairButton print;

  private ACAffairButton save;

  private ACAffairButton close;

  private ACPanel contents;

  private ACTable formats;

  private VRTableColumnModel formatsColumnModel;

  private ACTableColumn formatNo;

  private ACTableColumn formatID;

  private ACTableColumn formatType;

  private ACTableColumn formatX;

  private ACTableColumn formatY;

  private ACTableColumn formatWidth;

  private ACTableColumn formatHeight;

  private ACTableColumn formatSize;

  private ACTableColumn formatBorderWidth;

  private ACTableColumn formatText;

  //getter

  /**
   * 業務ボタンバーを取得します。
   * @return 業務ボタンバー
   */
  public ACAffairButtonBar getButtons(){
    if(buttons==null){

      buttons = new ACAffairButtonBar();

      buttons.setText("医見書　簡易帳票カスタマイズツール");

      buttons.setBackVisible(false);

      addButtons();
    }
    return buttons;

  }

  /**
   * 開くを取得します。
   * @return 開く
   */
  public ACAffairButton getOpen(){
    if(open==null){

      open = new ACAffairButton();

      open.setText("開く(O)");

      open.setToolTipText("編集する帳票を開きます。");

      open.setMnemonic('O');

      open.setIconPath(ACConstants.ICON_PATH_OPEN_24);

      addOpen();
    }
    return open;

  }

  /**
   * 印刷を取得します。
   * @return 印刷
   */
  public ACAffairButton getPrint(){
    if(print==null){

      print = new ACAffairButton();

      print.setText("印刷(P)");

      print.setEnabled(false);

      print.setToolTipText("現在の帳票情報からPDFを作成します。");

      print.setMnemonic('P');

      print.setIconPath(ACConstants.ICON_PATH_PRINT_24);

      addPrint();
    }
    return print;

  }

  /**
   * 保存を取得します。
   * @return 保存
   */
  public ACAffairButton getSave(){
    if(save==null){

      save = new ACAffairButton();

      save.setText("保存(S)");

      save.setEnabled(false);

      save.setToolTipText("編集した帳票を保存します。");

      save.setMnemonic('S');

      save.setIconPath(ACConstants.ICON_PATH_UPDATE_24);

      addSave();
    }
    return save;

  }

  /**
   * 終了を取得します。
   * @return 終了
   */
  public ACAffairButton getClose(){
    if(close==null){

      close = new ACAffairButton();

      close.setText("終了(X)");

      close.setToolTipText("終了します。");

      close.setMnemonic('X');

      close.setIconPath(ACConstants.ICON_PATH_EXIT_24);

      addClose();
    }
    return close;

  }

  /**
   * 情報領域を取得します。
   * @return 情報領域
   */
  public ACPanel getContents(){
    if(contents==null){

      contents = new ACPanel();

      addContents();
    }
    return contents;

  }

  /**
   * 定義体項目一覧を取得します。
   * @return 定義体項目一覧
   */
  public ACTable getFormats(){
    if(formats==null){

      formats = new ACTable();

      formats.setColumnModel(getFormatsColumnModel());

      addFormats();
    }
    return formats;

  }

  /**
   * 定義体項目一覧カラムモデルを取得します。
   * @return 定義体項目一覧カラムモデル
   */
  protected VRTableColumnModel getFormatsColumnModel(){
    if(formatsColumnModel==null){
      formatsColumnModel = new VRTableColumnModel(new TableColumn[]{});
      addFormatsColumnModel();
    }
    return formatsColumnModel;
  }

  /**
   * No.を取得します。
   * @return No.
   */
  public ACTableColumn getFormatNo(){
    if(formatNo==null){

      formatNo = new ACTableColumn();

      formatNo.setHeaderValue("No.");

      formatNo.setColumnName("Type");

      formatNo.setColumns(3);

      formatNo.setHorizontalAlignment(SwingConstants.RIGHT);

      formatNo.setRendererType(ACTableCellViewer.RENDERER_TYPE_SERIAL_NO);

      formatNo.setSortable(false);

      addFormatNo();
    }
    return formatNo;

  }

  /**
   * IDを取得します。
   * @return ID
   */
  public ACTableColumn getFormatID(){
    if(formatID==null){

      formatID = new ACTableColumn();

      formatID.setHeaderValue("ID");

      formatID.setColumnName("Id");

      formatID.setColumns(16);

      addFormatID();
    }
    return formatID;

  }

  /**
   * 種類を取得します。
   * @return 種類
   */
  public ACTableColumn getFormatType(){
    if(formatType==null){

      formatType = new ACTableColumn();

      formatType.setHeaderValue("種類");

      formatType.setColumnName("Type");

      formatType.setColumns(3);

      addFormatType();
    }
    return formatType;

  }

  /**
   * X座標を取得します。
   * @return X座標
   */
  public ACTableColumn getFormatX(){
    if(formatX==null){

      formatX = new ACTableColumn();

      formatX.setHeaderValue("X座標");

      formatX.setColumnName("X");

      formatX.setEditable(true);

      formatX.setColumns(4);

      formatX.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatX();
    }
    return formatX;

  }

  /**
   * Y座標を取得します。
   * @return Y座標
   */
  public ACTableColumn getFormatY(){
    if(formatY==null){

      formatY = new ACTableColumn();

      formatY.setHeaderValue("Y座標");

      formatY.setColumnName("Y");

      formatY.setEditable(true);

      formatY.setColumns(4);

      formatY.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatY();
    }
    return formatY;

  }

  /**
   * 幅を取得します。
   * @return 幅
   */
  public ACTableColumn getFormatWidth(){
    if(formatWidth==null){

      formatWidth = new ACTableColumn();

      formatWidth.setHeaderValue("幅");

      formatWidth.setColumnName("Width");

      formatWidth.setEditable(true);

      formatWidth.setColumns(3);

      formatWidth.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatWidth();
    }
    return formatWidth;

  }

  /**
   * 高さを取得します。
   * @return 高さ
   */
  public ACTableColumn getFormatHeight(){
    if(formatHeight==null){

      formatHeight = new ACTableColumn();

      formatHeight.setHeaderValue("高さ");

      formatHeight.setColumnName("Height");

      formatHeight.setEditable(true);

      formatHeight.setColumns(3);

      formatHeight.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatHeight();
    }
    return formatHeight;

  }

  /**
   * 文字サイズを取得します。
   * @return 文字サイズ
   */
  public ACTableColumn getFormatSize(){
    if(formatSize==null){

      formatSize = new ACTableColumn();

      formatSize.setHeaderValue("文字サイズ");

      formatSize.setColumnName("FontSize");

      formatSize.setEditable(true);

      formatSize.setColumns(6);

      formatSize.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatSize();
    }
    return formatSize;

  }

  /**
   * 線の太さを取得します。
   * @return 線の太さ
   */
  public ACTableColumn getFormatBorderWidth(){
    if(formatBorderWidth==null){

      formatBorderWidth = new ACTableColumn();

      formatBorderWidth.setHeaderValue("線の太さ");

      formatBorderWidth.setColumnName("BorderWidth");

      formatBorderWidth.setEditable(true);

      formatBorderWidth.setColumns(5);

      formatBorderWidth.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatBorderWidth();
    }
    return formatBorderWidth;

  }

  /**
   * 文言を取得します。
   * @return 文言
   */
  public ACTableColumn getFormatText(){
    if(formatText==null){

      formatText = new ACTableColumn();

      formatText.setHeaderValue("文言");

      formatText.setColumnName("Text");

      formatText.setEditable(true);

      formatText.setColumns(8);

      formatText.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatText();
    }
    return formatText;

  }

  /**
   * コンストラクタです。
   */
  public IP001Design() {

    try {
      initialize();

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

    this.add(getButtons(), VRLayout.NORTH);

    this.add(getContents(), VRLayout.CLIENT);

  }

  /**
   * 業務ボタンバーに内部項目を追加します。
   */
  protected void addButtons(){

    buttons.add(getClose(), VRLayout.EAST);
    buttons.add(getSave(), VRLayout.EAST);
    buttons.add(getPrint(), VRLayout.EAST);
    buttons.add(getOpen(), VRLayout.EAST);
  }

  /**
   * 開くに内部項目を追加します。
   */
  protected void addOpen(){

  }

  /**
   * 印刷に内部項目を追加します。
   */
  protected void addPrint(){

  }

  /**
   * 保存に内部項目を追加します。
   */
  protected void addSave(){

  }

  /**
   * 終了に内部項目を追加します。
   */
  protected void addClose(){

  }

  /**
   * 情報領域に内部項目を追加します。
   */
  protected void addContents(){

    contents.add(getFormats(), VRLayout.CLIENT);

  }

  /**
   * 定義体項目一覧に内部項目を追加します。
   */
  protected void addFormats(){

  }

  /**
   * 定義体項目一覧カラムモデルに内部項目を追加します。
   */
  protected void addFormatsColumnModel(){

    getFormatsColumnModel().addColumn(getFormatNo());

    getFormatsColumnModel().addColumn(getFormatID());

    getFormatsColumnModel().addColumn(getFormatType());

    getFormatsColumnModel().addColumn(getFormatX());

    getFormatsColumnModel().addColumn(getFormatY());

    getFormatsColumnModel().addColumn(getFormatWidth());

    getFormatsColumnModel().addColumn(getFormatHeight());

    getFormatsColumnModel().addColumn(getFormatSize());

    getFormatsColumnModel().addColumn(getFormatBorderWidth());

    getFormatsColumnModel().addColumn(getFormatText());

  }

  /**
   * No.に内部項目を追加します。
   */
  protected void addFormatNo(){

  }

  /**
   * IDに内部項目を追加します。
   */
  protected void addFormatID(){

  }

  /**
   * 種類に内部項目を追加します。
   */
  protected void addFormatType(){

  }

  /**
   * X座標に内部項目を追加します。
   */
  protected void addFormatX(){

  }

  /**
   * Y座標に内部項目を追加します。
   */
  protected void addFormatY(){

  }

  /**
   * 幅に内部項目を追加します。
   */
  protected void addFormatWidth(){

  }

  /**
   * 高さに内部項目を追加します。
   */
  protected void addFormatHeight(){

  }

  /**
   * 文字サイズに内部項目を追加します。
   */
  protected void addFormatSize(){

  }

  /**
   * 線の太さに内部項目を追加します。
   */
  protected void addFormatBorderWidth(){

  }

  /**
   * 文言に内部項目を追加します。
   */
  protected void addFormatText(){

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

  public static void main(String[] args) {
    //デフォルトデバッグ起動
    try {
      ACFrame.getInstance().setFrameEventProcesser(new IkenshoFrameEventProcesser());
      ACFrame.debugStart(new ACAffairInfo(IP001Design.class.getName()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 自身を返します。
   */
  protected IP001Design getThis() {
    return this;
  }
}
