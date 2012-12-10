package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * 指定文字で文字詰めを行なうフォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */
public class ACFillCharFormat
    extends Format {
  private boolean leftFill;
  private boolean parsing;
  private boolean formating;
  private String fillText;
  private int maxLength;
  private boolean allowedBlank;

  /**
   * コンストラクタです。
   */
  public ACFillCharFormat() {
    this("0", 10, true, false, true, true);
  }

  /**
   * コンストラクタです。
   * @param fillText 補完に用いる文字列
   * @param maxLength 最大補完文字数
   */
  public ACFillCharFormat(String fillText, int maxLength) {
    this(fillText, maxLength, true, false, true, true);
  }

  /**
   * コンストラクタです。
   * @param fillText 補完に用いる文字列
   * @param maxLength 最大補完文字数
   * @param leftFill 左埋めで補完するか
   */
  public ACFillCharFormat(String fillText, int maxLength, boolean leftFill) {
    this(fillText, maxLength, leftFill, true, false, true);
  }

  /**
   * コンストラクタです。
   * @param fillText 補完に用いる文字列
   * @param maxLength 最大補完文字数
   * @param leftFill 左埋めで補完するか
   * @param formating 補完フォーマットを実行するか
   * @param parsing 逆補完解析を実行するか
   * @param allowedBlank 入力が空文字ならば補完を行なわないか
   */
  public ACFillCharFormat(String fillText, int maxLength, boolean leftFill,
                          boolean parsing, boolean formating,
                          boolean allowedBlank) {
    setFillText(fillText);
    setMaxLength(maxLength);
    setLeftFill(leftFill);
    setParsing(parsing);
    setFormating(formating);
    setAllowedBlank(allowedBlank);
  }

  public Object parseObject(String source, ParsePosition pos) {
    int srcLength = source.length();
    if (isParsing()) {
      source = toFilled(source);
    }
    pos.setIndex(srcLength);
    return source;
  }

  /**
   * 補完処理の実行結果を返します。
   * @param source 補完対象
   * @return 補完処理の実行結果
   */
  public String toFilled(String source){
    if((getFillText() != null) && (getFillText().length() > 0)) {
      int srcLength = source.length();
      if (srcLength < maxLength) {
        //最大補完文字数より入力が少なければ補完する
        if ( (srcLength > 0) || (!isAllowedBlank())) {
          //1文字以上入力されているか空入力を許容しないならば補完する

          //補完文字列を生成
          int appendLength = maxLength - srcLength;
          char[] append = new char[appendLength];
          char[] fills = getFillText().toCharArray();
          int fillLength = fills.length;
          for (int i = 0; i < appendLength; i += fillLength) {
            System.arraycopy(fills, 0, append, i, fillLength);
          }
          //剰余分は埋まるところまで埋める
          int mod = appendLength % fillLength;
          if (mod > 0) {
            System.arraycopy(fills, 0, append, appendLength - mod, mod);
          }
          String appendText = new String(append);

          if (isLeftFill()) {
            //左補完
            source = appendText + source;
          }
          else {
            //右補完
            source = source + appendText;
          }
        }
      }
    }

    return source;
  }

  public StringBuffer format(Object obj, StringBuffer toAppendTo,
                             FieldPosition pos) {
    if (obj != null) {
      String source = String.valueOf(obj);
      if (isFormating()) {
        source = toFilled(source);
//        char[] srcs = source.toCharArray();
//        char[] cmps = getFillText().toCharArray();
//        int srcLength = srcs.length;
//        int cmpLength = cmps.length;
//        if (isLeftFill()) {
//          //左補完
//          //左から捜査して最後の補完文字位置を特定する
//          int subStart = 0;
//          int end = srcLength - cmpLength;
//          if (end >= 0) {
//            for (int i = 0; i < end; i++) {
//              boolean match = true;
//              for (int j = 0; j < cmpLength; j++) {
//                if (srcs[i] != cmps[i]) {
//                  match = false;
//                  break;
//                }
//              }
//              if (match) {
//                //発見した位置+1が切り出し開始位置
//                subStart = i + 1;
//              }
//            }
//          }
//
//          if (subStart > 0) {
//            source = source.substring(subStart);
//          }
//        }
//        else {
//          //右補完
//          //右から捜査して最後の補完文字位置を特定する
//          int subEnd = srcLength;
//          int start = srcLength - cmpLength;
//          if (start >= 0) {
//            for (int i = start; i >= 0; i--) {
//              boolean match = true;
//              for (int j = 0; j < cmpLength; j++) {
//                if (srcs[i] != cmps[i]) {
//                  match = false;
//                  break;
//                }
//              }
//              if (match) {
//                //発見した位置が切り出し終了位置
//                subEnd = i;
//              }
//            }
//          }
//
//          if (subEnd < srcLength) {
//            source = source.substring(0, subEnd);
//          }
//        }
      }
      toAppendTo.append(source);
    }

    return toAppendTo;
  }

  /**
   * 補完に用いる文字列を返します。
   * @return 補完に用いる文字列
   */
  public String getFillText() {
    return fillText;
  }

  /**
   * 補完に用いる文字列を設定します。
   * @param fillText 補完に用いる文字列
   */
  public void setFillText(String fillText) {
    this.fillText = fillText;
  }

  /**
   * 補完フォーマットを実行するかを返します。
   * @return 補完フォーマットを実行するか
   */
  public boolean isFormating() {
    return formating;
  }

  /**
   * 補完フォーマットを実行するかを設定します。
   * @param formating 補完フォーマットを実行するか
   */
  public void setFormating(boolean formating) {
    this.formating = formating;
  }

  /**
   * 左埋めで補完するかを返します。
   * @return 左埋めで補完するか
   */
  public boolean isLeftFill() {
    return leftFill;
  }

  /**
   * 左埋めで補完するかを設定します。
   * @param leftFill 左埋めで補完するか
   */
  public void setLeftFill(boolean leftFill) {
    this.leftFill = leftFill;
  }

  /**
   * 最大補完文字数を返します。
   * @return 最大補完文字数
   */
  public int getMaxLength() {
    return maxLength;
  }

  /**
   * 最大補完文字数を設定します。
   * @param maxLength 最大補完文字数
   */
  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  /**
   * 逆補完解析を実行するかを返します。
   * @return 逆補完解析を実行するか
   */
  public boolean isParsing() {
    return parsing;
  }

  /**
   * 逆補完解析を実行するかを設定します。
   * @param parsing 逆補完解析を実行するか
   */
  public void setParsing(boolean parsing) {
    this.parsing = parsing;
  }

  /**
   * 入力が空文字ならば補完を行なわないかを返します。
   * @return 入力が空文字ならば補完を行なわないか
   */
  public boolean isAllowedBlank() {
    return allowedBlank;
  }

  /**
   * 入力が空文字ならば補完を行なわないかを設定します。
   * @param allowedBlank 入力が空文字ならば補完を行なわないか
   */
  public void setAllowedBlank(boolean allowedBlank) {
    this.allowedBlank = allowedBlank;
  }

}
