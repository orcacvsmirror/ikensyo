package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * �w�蕶���ŕ����l�߂��s�Ȃ��t�H�[�}�b�g�ł��B
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
   * �R���X�g���N�^�ł��B
   */
  public ACFillCharFormat() {
    this("0", 10, true, false, true, true);
  }

  /**
   * �R���X�g���N�^�ł��B
   * @param fillText �⊮�ɗp���镶����
   * @param maxLength �ő�⊮������
   */
  public ACFillCharFormat(String fillText, int maxLength) {
    this(fillText, maxLength, true, false, true, true);
  }

  /**
   * �R���X�g���N�^�ł��B
   * @param fillText �⊮�ɗp���镶����
   * @param maxLength �ő�⊮������
   * @param leftFill �����߂ŕ⊮���邩
   */
  public ACFillCharFormat(String fillText, int maxLength, boolean leftFill) {
    this(fillText, maxLength, leftFill, true, false, true);
  }

  /**
   * �R���X�g���N�^�ł��B
   * @param fillText �⊮�ɗp���镶����
   * @param maxLength �ő�⊮������
   * @param leftFill �����߂ŕ⊮���邩
   * @param formating �⊮�t�H�[�}�b�g�����s���邩
   * @param parsing �t�⊮��͂����s���邩
   * @param allowedBlank ���͂��󕶎��Ȃ�Ε⊮���s�Ȃ�Ȃ���
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
   * �⊮�����̎��s���ʂ�Ԃ��܂��B
   * @param source �⊮�Ώ�
   * @return �⊮�����̎��s����
   */
  public String toFilled(String source){
    if((getFillText() != null) && (getFillText().length() > 0)) {
      int srcLength = source.length();
      if (srcLength < maxLength) {
        //�ő�⊮�����������͂����Ȃ���Ε⊮����
        if ( (srcLength > 0) || (!isAllowedBlank())) {
          //1�����ȏ���͂���Ă��邩����͂����e���Ȃ��Ȃ�Ε⊮����

          //�⊮������𐶐�
          int appendLength = maxLength - srcLength;
          char[] append = new char[appendLength];
          char[] fills = getFillText().toCharArray();
          int fillLength = fills.length;
          for (int i = 0; i < appendLength; i += fillLength) {
            System.arraycopy(fills, 0, append, i, fillLength);
          }
          //��]���͖��܂�Ƃ���܂Ŗ��߂�
          int mod = appendLength % fillLength;
          if (mod > 0) {
            System.arraycopy(fills, 0, append, appendLength - mod, mod);
          }
          String appendText = new String(append);

          if (isLeftFill()) {
            //���⊮
            source = appendText + source;
          }
          else {
            //�E�⊮
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
//          //���⊮
//          //������{�����čŌ�̕⊮�����ʒu����肷��
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
//                //���������ʒu+1���؂�o���J�n�ʒu
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
//          //�E�⊮
//          //�E����{�����čŌ�̕⊮�����ʒu����肷��
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
//                //���������ʒu���؂�o���I���ʒu
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
   * �⊮�ɗp���镶�����Ԃ��܂��B
   * @return �⊮�ɗp���镶����
   */
  public String getFillText() {
    return fillText;
  }

  /**
   * �⊮�ɗp���镶�����ݒ肵�܂��B
   * @param fillText �⊮�ɗp���镶����
   */
  public void setFillText(String fillText) {
    this.fillText = fillText;
  }

  /**
   * �⊮�t�H�[�}�b�g�����s���邩��Ԃ��܂��B
   * @return �⊮�t�H�[�}�b�g�����s���邩
   */
  public boolean isFormating() {
    return formating;
  }

  /**
   * �⊮�t�H�[�}�b�g�����s���邩��ݒ肵�܂��B
   * @param formating �⊮�t�H�[�}�b�g�����s���邩
   */
  public void setFormating(boolean formating) {
    this.formating = formating;
  }

  /**
   * �����߂ŕ⊮���邩��Ԃ��܂��B
   * @return �����߂ŕ⊮���邩
   */
  public boolean isLeftFill() {
    return leftFill;
  }

  /**
   * �����߂ŕ⊮���邩��ݒ肵�܂��B
   * @param leftFill �����߂ŕ⊮���邩
   */
  public void setLeftFill(boolean leftFill) {
    this.leftFill = leftFill;
  }

  /**
   * �ő�⊮��������Ԃ��܂��B
   * @return �ő�⊮������
   */
  public int getMaxLength() {
    return maxLength;
  }

  /**
   * �ő�⊮��������ݒ肵�܂��B
   * @param maxLength �ő�⊮������
   */
  public void setMaxLength(int maxLength) {
    this.maxLength = maxLength;
  }

  /**
   * �t�⊮��͂����s���邩��Ԃ��܂��B
   * @return �t�⊮��͂����s���邩
   */
  public boolean isParsing() {
    return parsing;
  }

  /**
   * �t�⊮��͂����s���邩��ݒ肵�܂��B
   * @param parsing �t�⊮��͂����s���邩
   */
  public void setParsing(boolean parsing) {
    this.parsing = parsing;
  }

  /**
   * ���͂��󕶎��Ȃ�Ε⊮���s�Ȃ�Ȃ�����Ԃ��܂��B
   * @return ���͂��󕶎��Ȃ�Ε⊮���s�Ȃ�Ȃ���
   */
  public boolean isAllowedBlank() {
    return allowedBlank;
  }

  /**
   * ���͂��󕶎��Ȃ�Ε⊮���s�Ȃ�Ȃ�����ݒ肵�܂��B
   * @param allowedBlank ���͂��󕶎��Ȃ�Ε⊮���s�Ȃ�Ȃ���
   */
  public void setAllowedBlank(boolean allowedBlank) {
    this.allowedBlank = allowedBlank;
  }

}
