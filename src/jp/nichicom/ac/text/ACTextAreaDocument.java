package jp.nichicom.ac.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.vr.component.AbstractVRTextArea;
import jp.nichicom.vr.text.VRTextAreaDocument;

/**
 * �e�L�X�g�G���A�p�̃h�L�������g�N���X�ł��B
 * <p>
 * �ő�s���`�F�b�N���������Ă��܂��B
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
   * �R���X�g���N�^�ł��B
   * @param textArea �����Ώۂ̃e�L�X�g�t�B�[���h
   */
  public ACTextAreaDocument(AbstractVRTextArea textArea) {
      super(textArea);
  }

  /**
   * ������}�������ł��B
  * @param offset �I�t�Z�b�g
   * @param str ������
   * @param attr ����
   * @throws BadLocationException ������O
   */
  public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
      if (isInsertProcessDisabled(offset, str, attr)) {
          return;
      }

      AbstractVRTextArea area = getTextArea();
      if (area instanceof AbstractVRTextArea) {

        //�ő啶�����`�F�b�N
        str = getMaxLengthCheckedText(str, area.getMaxLength(), area.getText(),
                                      area.isByteMaxLength());
        if (str == null) {
          return;
        }

        if (area instanceof ACRowMaximumableTextArea) {
          //�ő�s���`�F�b�N
          ACRowMaximumableTextArea rowArea = (ACRowMaximumableTextArea) area;
          if ((rowArea.getColumns() > 0) && (rowArea.getMaxRows()>0)) {
            //�s���I�[�o�[�Ȃ�1���������炵�Ă݂�
            String newStr = null;
            int len = str.length();
            for (int i = len; i > 0; i--) {
              //����悤�ɂȂ�����break;
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

        //������ʃ`�F�b�N
        if (isErrorCharType(offset, str, area.getCharType(), area.getText())) {
            return;
        }
      }

      // 2006/02/08[Tozo Tanaka] : add begin
      //VT(�����^�u)�͏���
      if(str!=null){
          str = str.replaceAll("\u000b", "");
      }
      // 2006/02/08[Tozo Tanaka] : add end
      super.insertString(offset, str, attr);
  }
  protected String getMaxLengthCheckedText(String str, int len, String inputedText, boolean isByteMaxLength){
      //null�`�F�b�N
      if((inputedText!=null)&&(len>0)){
        inputedText = inputedText.replaceAll("\n", "");
      }
      return super.getMaxLengthCheckedText(str, len, inputedText, isByteMaxLength);
  }

  /**
   * ���͊m���̕�������擾���܂��B
   * @param offset int
   * @param str String
   * @param inputedText String
   * @return String
   */
  protected String getNewString(int offset, String str, String inputedText) {
      return inputedText.substring(0, offset) + str + inputedText.substring(offset);
  }

  /**
   * ������̍s�����擾���܂��B
   * @param str �s�����`�F�b�N���镶����
   * @return �s��
   */
  protected int getLineFeedCount(String str) {
    int linefeed = 1; //�s��

    if(getTextArea() instanceof ACRowMaximumableTextArea){
      //�ő�s���`�F�b�N
      ACRowMaximumableTextArea area = (ACRowMaximumableTextArea) getTextArea();

      int len = str.length(); //������
      int cnt = 0; //�J�E���^

      for (int i = 0; i < len; i++, cnt++) {
        String charTgt = str.substring(i, i + 1);

        //�������Ȃ�s���C���N�������g
        int maxColumns = area.getColumns();
        int bias = 0;
        if (charTgt.getBytes().length >= 2) {
          cnt++;
          bias++;
        }
        //���s������Ȃ�s���C���N�������g
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
