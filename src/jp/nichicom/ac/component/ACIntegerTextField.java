package jp.nichicom.ac.component;

import javax.swing.text.Document;

import jp.nichicom.ac.text.ACSimpleIntegerFormat;
import jp.nichicom.vr.text.VRCharType;

/**
 * �l��Integer�ŊǗ�����e�L�X�g�t�B�[���h�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACTextField
 */
public class ACIntegerTextField extends ACTextField {
  private int createSourceValue;
  public ACIntegerTextField() {
  }

  public ACIntegerTextField(String text, int columns) {
    super(text, columns);
  }

  public ACIntegerTextField(String text) {
    super(text);
  }

  public ACIntegerTextField(int columns) {
    super(columns);
  }

  public ACIntegerTextField(Document doc, String text, int columns) {
    super(doc, text, columns);
  }
  protected void initComponent() {
    super.initComponent();

    setCharType(VRCharType.ONLY_NUMBER);
    setFormat(new ACSimpleIntegerFormat());
  }

  public Object createSource() {
      return new Integer(createSourceValue);
  }
  /**
   * createSource�ŕԋp���鏉���l��ݒ肵�܂��B
   * @return createSource�ŕԋp���鏉���l
   */
  public int getCreateSourceValue() {
    return createSourceValue;
  }
  /**
   * createSource�ŕԋp���鏉���l��ݒ肵�܂��B
   * @param createSourceValue createSource�ŕԋp���鏉���l
   */
  public void setCreateSourceValue(int createSourceValue) {
    this.createSourceValue = createSourceValue;
  }
}
