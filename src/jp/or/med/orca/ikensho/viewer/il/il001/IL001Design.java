
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
 * 開発者: 藤原　伸
 * 作成日: 2012/09/20  日本コンピューター株式会社 藤原　伸 新規作成
 * 更新日: ----/--/--
 * システム 主治医意見書 (I)
 * サブシステム ログ (L)
 * プロセス ログビューア (001)
 * プログラム ログビューア (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;
import java.awt.Component;

import javax.swing.table.TableColumn;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.affair.IkenshoAffairContainer;
/**
 * ログビューア画面項目デザイン(IL001) 
 */
public class IL001Design extends IkenshoAffairContainer implements ACAffairable {
	private static final long serialVersionUID = 1L;
  //GUIコンポーネント

  private ACAffairButtonBar buttons;

  private ACAffairButton prevButton;

  private ACAffairButton nextButton;

  private ACPanel contents;

  private ACGroupBox fileInfoGroup;

  private ACLabel fileCountLabel;

  private ACLabel fileCount;

  private ACLabel viewFileLavel;

  private ACLabel viewFile;

  private ACTable logDataTable;

  private VRTableColumnModel logDataTableColumnModel;

  private ACTableColumn logDataTableColumn1;

  private ACTableColumn logDataTableColumn2;

  private ACTableColumn logDataTableColumn3;

  private ACTableColumn logDataTableColumn4;

  private ACTableColumn logDataTableColumn5;

  private ACTableColumn logDataTableColumn6;

  //getter

  /**
   * 業務ボタンバーを取得します。
   * @return 業務ボタンバー
   */
  public ACAffairButtonBar getButtons(){
    if(buttons==null){

      buttons = new ACAffairButtonBar();

      buttons.setText("医見書 ログファイルビューア");

      buttons.setBackVisible(false);

      addButtons();
    }
    return buttons;

  }

  /**
   * 戻るボタンを取得します。
   * @return 戻るボタン
   */
  public ACAffairButton getPrevButton(){
    if(prevButton==null){

      prevButton = new ACAffairButton();

      prevButton.setText("戻る(P)");

      prevButton.setMnemonic('P');

      prevButton.setIconPath(ACConstants.ICON_PATH_LEFT_24);

      addPrevButton();
    }
    return prevButton;

  }

  /**
   * 次へボタンを取得します。
   * @return 次へボタン
   */
  public ACAffairButton getNextButton(){
    if(nextButton==null){

      nextButton = new ACAffairButton();

      nextButton.setText("次へ(N)");

      nextButton.setMnemonic('N');

      nextButton.setIconPath(ACConstants.ICON_PATH_RIGHT_24);

      addNextButton();
    }
    return nextButton;

  }

  /**
   * クライアント領域を取得します。
   * @return クライアント領域
   */
  public ACPanel getContents(){
    if(contents==null){

      contents = new ACPanel();

      addContents();
    }
    return contents;

  }

  /**
   * ログファイル情報グループを取得します。
   * @return ログファイル情報グループ
   */
  public ACGroupBox getFileInfoGroup(){
    if(fileInfoGroup==null){

      fileInfoGroup = new ACGroupBox();

      fileInfoGroup.setText("ログファイル情報");

      addFileInfoGroup();
    }
    return fileInfoGroup;

  }

  /**
   * 個数ラベルを取得します。
   * @return 個数ラベル
   */
  public ACLabel getFileCountLabel(){
    if(fileCountLabel==null){

      fileCountLabel = new ACLabel();

      fileCountLabel.setText("個数：");

      addFileCountLabel();
    }
    return fileCountLabel;

  }

  /**
   * ログファイル数を取得します。
   * @return ログファイル数
   */
  public ACLabel getFileCount(){
    if(fileCount==null){

      fileCount = new ACLabel();

      fileCount.setBindPath("FILE_COUNT");

      addFileCount();
    }
    return fileCount;

  }

  /**
   * 表示ログファイルラベルを取得します。
   * @return 表示ログファイルラベル
   */
  public ACLabel getViewFileLavel(){
    if(viewFileLavel==null){

      viewFileLavel = new ACLabel();

      viewFileLavel.setText("表示：");

      addViewFileLavel();
    }
    return viewFileLavel;

  }

  /**
   * 表示ログファイルを取得します。
   * @return 表示ログファイル
   */
  public ACLabel getViewFile(){
    if(viewFile==null){

      viewFile = new ACLabel();

      viewFile.setBindPath("VIEW_FILE_NUMBER");

      addViewFile();
    }
    return viewFile;

  }

  /**
   * ログデータテーブルを取得します。
   * @return ログデータテーブル
   */
  public ACTable getLogDataTable(){
    if(logDataTable==null){

      logDataTable = new ACTable();

      logDataTable.setColumnModel(getLogDataTableColumnModel());

      logDataTable.setColumnSort(true);

      addLogDataTable();
    }
    return logDataTable;

  }

  /**
   * ログデータテーブルカラムモデルを取得します。
   * @return ログデータテーブルカラムモデル
   */
  protected VRTableColumnModel getLogDataTableColumnModel(){
    if(logDataTableColumnModel==null){
      logDataTableColumnModel = new VRTableColumnModel(new TableColumn[]{});
      addLogDataTableColumnModel();
    }
    return logDataTableColumnModel;
  }

  /**
   * 時刻カラムを取得します。
   * @return 時刻カラム
   */
  public ACTableColumn getLogDataTableColumn1(){
    if(logDataTableColumn1==null){

      logDataTableColumn1 = new ACTableColumn();

      logDataTableColumn1.setHeaderValue("時刻");

      logDataTableColumn1.setColumnName("LOG_DATETIME");

      logDataTableColumn1.setEditable(false);

      logDataTableColumn1.setColumns(12);

      logDataTableColumn1.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn1();
    }
    return logDataTableColumn1;

  }

  /**
   * 閲覧者カラムを取得します。
   * @return 閲覧者カラム
   */
  public ACTableColumn getLogDataTableColumn2(){
    if(logDataTableColumn2==null){

      logDataTableColumn2 = new ACTableColumn();

      logDataTableColumn2.setHeaderValue("ユーザー");

      logDataTableColumn2.setColumnName("USER_NAME");

      logDataTableColumn2.setEditable(false);

      logDataTableColumn2.setColumns(8);

      logDataTableColumn2.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn2();
    }
    return logDataTableColumn2;

  }

  /**
   * プログラムIDカラムを取得します。
   * @return プログラムIDカラム
   */
  public ACTableColumn getLogDataTableColumn3(){
    if(logDataTableColumn3==null){

      logDataTableColumn3 = new ACTableColumn();

      logDataTableColumn3.setHeaderValue("プログラムID");

      logDataTableColumn3.setColumnName("AFFAIR_ID");

      logDataTableColumn3.setEditable(false);

      logDataTableColumn3.setColumns(8);

      logDataTableColumn3.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn3();
    }
    return logDataTableColumn3;

  }

  /**
   * プログラム名称カラムを取得します。
   * @return プログラム名称カラム
   */
  public ACTableColumn getLogDataTableColumn4(){
    if(logDataTableColumn4==null){

      logDataTableColumn4 = new ACTableColumn();

      logDataTableColumn4.setHeaderValue("プログラム名");

      logDataTableColumn4.setColumnName("AFFAIR_NAME");

      logDataTableColumn4.setEditable(false);

      logDataTableColumn4.setColumns(28);

      logDataTableColumn4.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn4();
    }
    return logDataTableColumn4;

  }

  /**
   * 閲覧氏名カラムを取得します。
   * @return 閲覧氏名カラム
   */
  public ACTableColumn getLogDataTableColumn5(){
    if(logDataTableColumn5==null){

      logDataTableColumn5 = new ACTableColumn();

      logDataTableColumn5.setHeaderValue("利用者氏名");

      logDataTableColumn5.setColumnName("PATIENT_NAME");

      logDataTableColumn5.setEditable(false);

      logDataTableColumn5.setColumns(12);

      logDataTableColumn5.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn5();
    }
    return logDataTableColumn5;

  }

  /**
   * 閲覧カナ氏名カラムを取得します。
   * @return 閲覧カナ氏名カラム
   */
  public ACTableColumn getLogDataTableColumn6(){
    if(logDataTableColumn6==null){

      logDataTableColumn6 = new ACTableColumn();

      logDataTableColumn6.setHeaderValue("利用者カナ氏名");

      logDataTableColumn6.setColumnName("PATIENT_NAME_KANA");

      logDataTableColumn6.setEditable(false);

      logDataTableColumn6.setColumns(8);

      logDataTableColumn6.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn6();
    }
    return logDataTableColumn6;

  }

  /**
   * コンストラクタです。
   */
  public IL001Design() {

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

    buttons.add(getNextButton(), VRLayout.EAST);
    buttons.add(getPrevButton(), VRLayout.EAST);
  }

  /**
   * 戻るボタンに内部項目を追加します。
   */
  protected void addPrevButton(){

  }

  /**
   * 次へボタンに内部項目を追加します。
   */
  protected void addNextButton(){

  }

  /**
   * クライアント領域に内部項目を追加します。
   */
  protected void addContents(){

    contents.add(getFileInfoGroup(), VRLayout.NORTH);

    contents.add(getLogDataTable(), VRLayout.CLIENT);

  }

  /**
   * ログファイル情報グループに内部項目を追加します。
   */
  protected void addFileInfoGroup(){

    fileInfoGroup.add(getFileCountLabel(), null);

    fileInfoGroup.add(getFileCount(), null);

    fileInfoGroup.add(getViewFileLavel(), null);

    fileInfoGroup.add(getViewFile(), null);

  }

  /**
   * 個数ラベルに内部項目を追加します。
   */
  protected void addFileCountLabel(){

  }

  /**
   * ログファイル数に内部項目を追加します。
   */
  protected void addFileCount(){

  }

  /**
   * 表示ログファイルラベルに内部項目を追加します。
   */
  protected void addViewFileLavel(){

  }

  /**
   * 表示ログファイルに内部項目を追加します。
   */
  protected void addViewFile(){

  }

  /**
   * ログデータテーブルに内部項目を追加します。
   */
  protected void addLogDataTable(){

  }

  /**
   * ログデータテーブルカラムモデルに内部項目を追加します。
   */
  protected void addLogDataTableColumnModel(){

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn1());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn2());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn3());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn4());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn5());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn6());

  }

  /**
   * 時刻カラムに内部項目を追加します。
   */
  protected void addLogDataTableColumn1(){

  }

  /**
   * 閲覧者カラムに内部項目を追加します。
   */
  protected void addLogDataTableColumn2(){

  }

  /**
   * プログラムIDカラムに内部項目を追加します。
   */
  protected void addLogDataTableColumn3(){

  }

  /**
   * プログラム名称カラムに内部項目を追加します。
   */
  protected void addLogDataTableColumn4(){

  }

  /**
   * 閲覧氏名カラムに内部項目を追加します。
   */
  protected void addLogDataTableColumn5(){

  }

  /**
   * 閲覧カナ氏名カラムに内部項目を追加します。
   */
  protected void addLogDataTableColumn6(){

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

  /**
   * 自身を返します。
   */
  protected IL001Design getThis() {
    return this;
  }
}
