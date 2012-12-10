package jp.nichicom.ac.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.vr.component.AbstractVRTextArea;
import jp.nichicom.vr.text.VRTextAreaDocument;

/**
 * テキストエリア用のドキュメントクラスです。
 * <p>
 * 最大行数チェックを実装しています。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTextAreaDocument
 */

public class ACTextAreaDocument extends VRTextAreaDocument {

  /**
   * コンストラクタです。
   * @param textArea 検査対象のテキストフィールド
   */
  public ACTextAreaDocument(AbstractVRTextArea textArea) {
      super(textArea);
  }

  /**
   * 文字列挿入処理です。
  * @param offset オフセット
   * @param str 文字列
   * @param attr 属性
   * @throws BadLocationException 処理例外
   */
  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (isInsertProcessDisabled(offset, str, attr)) {
          return;
      }

      AbstractVRTextArea area = getTextArea();
      if (area instanceof AbstractVRTextArea) {

        //最大文字数チェック
        str = getMaxLengthCheckedText(str, area.getMaxLength(), area.getText(),
                                      area.isByteMaxLength());
        if (str == null) {
          return;
        }

        if (area instanceof ACRowMaximumableTextArea) {
          //最大行数チェック
          ACRowMaximumableTextArea rowArea = (ACRowMaximumableTextArea) area;
          if ((rowArea.getColumns() > 0) && (rowArea.getMaxRows()>0)) {
            //行数オーバーなら1文字ずつ減らしてみる
            String newStr = null;
            int len = str.length();
            for (int i = len; i > 0; i--) {
              //入るようになったらbreak;
              if (getLineFeedCount(getNewString(offset, str.substring(0, i),
                                                rowArea.getText())) <=
                  rowArea.getMaxRows()) {
                newStr = str.substring(0, i);
                break;
              }
            }
            str = newStr;
          }
        }

        //文字種別チェック
        if (isErrorCharType(offset, str, area.getCharType(), area.getText())) {
            return;
        }
      }

      // 2006/02/08[Tozo Tanaka] : add begin
      //VT(垂直タブ)は除去
      if(str!=null){
          str = str.replaceAll("\u000b", "");
      }
      // 2006/02/08[Tozo Tanaka] : add end
      super.insertString(offset, str, attr);
  }
  protected String getMaxLengthCheckedText(String str, int len, String inputedText, boolean isByteMaxLength){
      //nullチェック
      if((inputedText!=null)&&(len>0)){
        inputedText = inputedText.replaceAll("\n", "");
      }
      return super.getMaxLengthCheckedText(str, len, inputedText, isByteMaxLength);
  }

  /**
   * 入力確定後の文字列を取得します。
   * @param offset int
   * @param str String
   * @param inputedText String
   * @return String
   */
  protected String getNewString(int offset, String str, String inputedText) {
      return inputedText.substring(0, offset) + str + inputedText.substring(offset);
  }

  /**
   * 文字列の行数を取得します。
   * @param str 行数をチェックする文字列
   * @return 行数
   */
  protected int getLineFeedCount(String str) {
    int linefeed = 1; //行数

    if(getTextArea() instanceof ACRowMaximumableTextArea){
      //最大行数チェック
      ACRowMaximumableTextArea area = (ACRowMaximumableTextArea) getTextArea();

      int len = str.length(); //文字列長
      int cnt = 0; //カウンタ

      for (int i = 0; i < len; i++, cnt++) {
        String charTgt = str.substring(i, i + 1);

        //幅制限なら行数インクリメント
        int maxColumns = area.getColumns();
        int bias = 0;
        if (charTgt.getBytes().length >= 2) {
          cnt++;
          bias++;
        }
        //改行文字列なら行数インクリメント
        if (charTgt.equals("\n")) {
          linefeed++;
          cnt = -1;
        }
        else if (cnt >= maxColumns) {
          linefeed++;
          cnt = bias;
        }

      }
    }
      return linefeed;
  }
}
