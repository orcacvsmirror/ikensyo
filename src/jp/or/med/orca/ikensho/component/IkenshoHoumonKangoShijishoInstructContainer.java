package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.affair.IkenshoExtraSpecialNoteDialog;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoInstructContainer extends VRPanel {
  private ACTextArea text = new ACTextArea();
  private VRButton showSelect = new VRButton();
//  private JScrollPane textScroll = new JScrollPane();
  private VRPanel buttuns = new VRPanel();
  private VRLabel title = new VRLabel();
  private VRLabel spacer = new VRLabel();
  private VRPanel titles = new VRPanel();
  private ACIntegerCheckBox check = new ACIntegerCheckBox();
  private int code;
  private String caption;
  private int teikeiMaxLength = 50;

  /**
   * 本文を取得します。
   * @return 本文
   */
  public String getText(){
    return text.getText();
  }

  /**
   * 本文を設定します。
   * @param text 本文
   */
  public void setText(String text){
    this.text.setText(text);
  }

  /**
   * 本文の最大入力行数 を返します。
   * @return 本文の最大入力行数
   */
  public int getMaxRows() {
      return text.getMaxRows();
  }
  /**
   * 本文の最大入力行数 を設定します。
   * @param maxRows 本文の最大入力行数
   */
  public void setMaxRows(int maxRows) {
      text.setMaxRows(maxRows);
  }

//  /**
//   * 本文の行数を制限するかを返します。
//   * @return 本文の行数を制限するか
//   */
//  public boolean isUseMaxRows() {
//    return text.isUseMaxRows();
//  }
//  /**
//   * 本文の行数を制限するかを設定します。
//   * @param useMaxRows 本文の行数を制限するか
//   */
//  public void setUseMaxRows(boolean useMaxRows) {
//    text.setUseMaxRows(useMaxRows);
//  }

  /**
   * 本文の行数を取得します。
   * @return 本文の行数
   */
  public int getRows(){
    return text.getRows();
  }

  /**
   * 本文の行数を設定します。
   * @param rows 本文の行数
   */
  public void setRows(int rows){
    this.text.setRows(rows);
  }

  /**
   * 本文の列数を取得します。
   * @return 本文の列数
   */
  public int getColumns(){
    return text.getColumns();
  }

  /**
   * 本文の列数を設定します。
   * @param columns 本文の列数
   */
  public void setColumns(int columns){
    this.text.setColumns(columns);
  }

//  /**
//   * 本文の列数制限を取得します。
//   * @return 本文の列数制限
//   */
//  public int getMaxColumns(){
//    return text.getMaxColumns();
//  }
//
//  /**
//   * 本文の列数制限を設定します。
//   * @param maxColumns 本文の列数制限
//   */
//  public void setMaxColumns(int maxColumns){
//    this.text.setMaxColumns(maxColumns);
//  }

  /**
   * 本文の文字数制限を取得します。
   * @return 本文の文字数制限
   */
  public int getMaxLength(){
    return text.getMaxLength();
  }

  /**
   * 本文の文字数制限を設定します。
   * @param maxLength 本文の文字数制限
   */
  public void setMaxLength(int maxLength){
    this.text.setMaxLength(maxLength);
  }

  /**
   * 本文のバインドパスを取得します。
   * @return 本文のバインドパス
   */
  public String getTextBindPath(){
    return text.getBindPath();
  }

  /**
   * 本文のバインドパスを設定します。
   * @param bindPath 本文のバインドパス
   */
  public void setTextBindPath(String bindPath){
    this.text.setBindPath(bindPath);
  }

  /**
   * タイトルを取得します。
   * @return タイトル
   */
  public String getTitle(){
    return title.getText();
  }
  /**
   * タイトルを設定します。
   * @param title タイトル
   */
  public void setTitle(String title){
    this.title.setText(title);
  }

  /**
   * 選択画面表示ボタンのテキストを取得します。
   * @return 選択画面表示ボタンのテキスト
   */
  public String getShowSelectText(){
    return showSelect.getText();
  }
  /**
   * 選択画面表示ボタンのテキストを設定します。
   * @param showSelectText 選択画面表示ボタンのテキスト
   */
  public void setShowSelectText(String showSelectText){
    this.showSelect.setText(showSelectText);
  }

  /**
   * 選択画面表示ボタンのニーモニックを取得します。
   * @return 選択画面表示ボタンのニーモニック
   */
  public int getShowSelectMnemonic(){
    return showSelect.getMnemonic();
  }

  /**
   * 選択画面表示ボタンのニーモニックを設定します。
   * @param mnemonic 選択画面表示ボタンのニーモニック
   */
  public void setShowSelectMnemonic(int mnemonic){
    this.showSelect.setMnemonic(mnemonic);
  }

  /**
   * チェックのテキストを取得します。
   * @return チェックのテキスト
   */
  public String getCheckText(){
    return check.getText();
  }
  /**
   * チェックのテキストを設定します。
   * @param checkText チェックのテキスト
   */
  public void setCheckText(String checkText){
    this.check.setText(checkText);
  }

  /**
   * チェックのバインドパスを取得します。
   * @return チェックのバインドパス
   */
  public String getCheckBindPath(){
    return check.getBindPath();
  }

  /**
   * チェックのバインドパスを設定します。
   * @param bindPath チェックのバインドパス
   */
  public void setCheckBindPath(String bindPath){
    this.check.setBindPath(bindPath);
  }

  /**
   * チェックのVisibleを取得します。
   * @return チェックのVisible
   */
  public boolean isCheckVisible(){
    return check.isVisible();
  }

  /**
   * チェックのVisibleを設定します。
   * @param visible チェックのVisible
   */
  public void setCheckVisible(boolean visible){
    this.check.setVisible(visible);
    if(!visible){
      text.setEnabled(true);
      showSelect.setEnabled(true);
    }
  }
  /**
   * 選択ダイアログ用の定型文コードを返します。
   * @return 定型文コード
   */
  public int getCode() {
    return code;
  }
  /**
   * 選択ダイアログ用の定型文コードを設定します。
   * @param code 定型文コード
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * 項目名を取得します。
   * @return 項目名
   */
  public String getCaption() {
    return caption;
  }
  /**
   * 項目名を設定します。
   * @param caption 項目名
   */
  public void setCaption(String caption) {
    this.caption = caption;
    this.showSelect.setToolTipText("「" + caption + "」選択画面を表示します。");
  }

  /**
   * コンストラクタです。
   */
  public IkenshoHoumonKangoShijishoInstructContainer() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    showSelect.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        setText(new IkenshoExtraSpecialNoteDialog(getCaption(), getCode(),
                                                  getMaxLength(),
                                                  text.getColumns(),
                                                  text.getRows(),
                                                  teikeiMaxLength).showModal(
            getText()));
      }
    });

    check.addItemListener(new ACFollowDisabledItemListener(new JComponent[] {
        showSelect, text}));

  }

  /**
   * コンポーネントを初期化します。
   * @throws Exception 初期化例外
   */
  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    text.setLineWrap(true);
    text.setMaxLength(120);
//    text.setMaxColumns(84);
    text.setMaxRows(3);
//    text.setUseMaxRows(true);
    text.setRows(3);
    text.setIMEMode(InputSubset.KANJI);
    text.setEnabled(false);
    text.setColumns(104);
    titles.setLayout(new BorderLayout());
    spacer.setText(" 　");
    showSelect.setEnabled(false);
    this.add(titles, VRLayout.NORTH);
    this.add(spacer, VRLayout.WEST);
    this.add(buttuns, VRLayout.EAST);
//    this.add(text, VRLayout.LEFT);
    this.add(text, VRLayout.FLOW);

    //    buttuns.setLayout(new FlowLayout());
    buttuns.add(showSelect, null);
//    textScroll.setViewportView(text);
    titles.add(title, BorderLayout.WEST);
    titles.add(check, BorderLayout.CENTER);
  }
  /**
   * 定型文の最大文字列長を返します。
   * @return 定型文の最大文字列長
   */
  public int getTeikeiMaxLength() {
    return teikeiMaxLength;
  }
  /**
   * 定型文の最大文字列長を設定します。
   * @param teikeiMaxLength 定型文の最大文字列長
   */
  public void setTeikeiMaxLength(int teikeiMaxLength) {
    this.teikeiMaxLength = teikeiMaxLength;
  }
}
