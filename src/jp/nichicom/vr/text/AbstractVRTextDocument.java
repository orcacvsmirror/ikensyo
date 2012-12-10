/** TODO <HEAD> */
package jp.nichicom.vr.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyleConstants;

/**
 * �e�L�X�g���͕ҏW�h�L�������g�̊��N���X�ł��B
 * <p>
 * ���͉\�ȕ�����ʂ�ő啶���񒷂𐧌�����@�\���������Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRCharType
 * @see DefaultStyledDocument
 */
public abstract class AbstractVRTextDocument extends DefaultStyledDocument {

    private JTextComponent textComponent;
    private boolean absoluteEditable = false;

    /**
     * �֘A�t�����ꂽ�e�L�X�g�R���|�[�l���g ��Ԃ��܂��B
     * 
     * @return �֘A�t�����ꂽ�e�L�X�g�R���|�[�l���g
     */
    protected JTextComponent getTextComponent() {
        return textComponent;
    }

    /**
     * �֘A�t�����ꂽ�e�L�X�g�R���|�[�l���g ��ݒ肵�܂��B
     * 
     * @param textComponent �֘A�t�����ꂽ�e�L�X�g�R���|�[�l���g
     */
    protected void setTextComponent(JTextComponent textComponent) {
        this.textComponent = textComponent;
    }

    /**
     * Document�̓��͐��������𖳎����ĕҏW�����邩 ��Ԃ��܂��B
     * 
     * @return Document�̓��͐��������𖳎����ĕҏW�����邩
     */
    public boolean isAbsoluteEditable() {
        return absoluteEditable;
    }

    /**
     * Document�̓��͐��������𖳎����ĕҏW�����邩 ��ݒ肵�܂��B
     * 
     * @param absoluteEditable Document�̓��͐��������𖳎����ĕҏW�����邩
     */
    public void setAbsoluteEditable(boolean absoluteEditable) {
        this.absoluteEditable = absoluteEditable;
    }

    /**
     * ������̑}���������֎~���Ă��邩��Ԃ��܂��B
     * 
     * @param offset �I�t�Z�b�g
     * @param str ������
     * @param attr ����
     * @return ������̑}���������֎~���Ă��邩
     */
    public boolean isInsertDisabled(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (!isAbsoluteEditable()) {
            //�����񂪓����Ă��Ă��Ȃ���ԂŃ��\�b�h���Ă΂ꂽ�ꍇ�A��������
            if (str == null) {
                return true;
            }

            //�t�H�[�J�X��������ԂŌĂяo���ꂽ�ꍇ�A��������
            if (!textComponent.hasFocus()) {
                return true;
            }

        }
        return false;
    }

    /**
     * �e�N���X�̕�����폜�����ł��B
     * 
     * @param offset �I�t�Z�b�g
     * @param length ������
     */
    public void superRemove(int offset, int length) throws BadLocationException {
        super.remove(offset, length);
    }

    /**
     * ���͕����̏������֎~���邩��Ԃ��܂��B
     * 
     * @param offset �I�t�Z�b�g
     * @param str ������
     * @param attr ����
     * @return ���͕����̏������֎~���邩
     * @throws BadLocationException ������O
     */
    protected boolean isInsertProcessDisabled(int offset, String str,
            AttributeSet attr) throws BadLocationException {
        if (isInsertDisabled(offset, str, attr)) {
            return true;
        }

        //IME�ϊ����`�F�b�N
        if ((attr != null)
                && (attr.isDefined(StyleConstants.ComposedTextAttribute))) {
            super.insertString(offset, str, attr);
            return true;

        }
        return false;
    }

    /**
     * �ő啶�������`�F�b�N�������ʁA�Ԉ����␳���݂̕������Ԃ��܂��B
     * 
     * @param str ������
     * @param len �ő啶����
     * @param inputedText ���͍ς݂̕���
     * @param isByteMaxLength �ő啶���񒷂𕶎����ł͂Ȃ��o�C�g���Ŕ��f���邩
     * @return �Ԉ����␳���݂̕�����B���͍ς݂̕��������łɍő啶�����𒴂��Ă����null�B
     */
    protected String getMaxLengthCheckedText(String str, int len,
            String inputedText, boolean isByteMaxLength) {
        //null�`�F�b�N
        if ((str == null) || (inputedText == null)) {
            return str;
        }

        //�ő啶�����`�F�b�N
        if (len > 0) {
            if (isByteMaxLength) {
                int byteCount = 0;
                int txtLen = inputedText.length();
                for (int i = 0; i < txtLen; i++) {
                    //�o�C�g�P�ʂōő啶���񒷂𒴂���܂ŌJ��Ԃ�
                    byteCount += inputedText.substring(i, i + 1).getBytes().length;
                    if (len < byteCount) {
                        return str.substring(0, i);
                    }
                }
            } else {
                int txtLen = inputedText.length();
                if (len < txtLen) {
                    return null;
                }
                if (len < txtLen + str.length()) {
                    //�Ԉ���
                    return str.substring(0, len - txtLen);
                }
            }
        }
        return str;
    }

    /**
     * ���͋֎~�Ώۂ̕������܂ނ���Ԃ��܂��B
     * 
     * @param offset �I�t�Z�b�g
     * @param str ������
     * @param chrType ���������
     * @param inputedText ���͍ς݂̕���
     * @return ���͋֎~�Ώۂ̕������܂ނ�
     */
    protected boolean isErrorCharType(int offset, String str,
            VRCharType chrType, String inputedText) {
        //������ʃ`�F�b�N
        if (chrType != null) {
            if (!chrType.isMatch(inputedText.substring(0, offset) + str
                    + inputedText.substring(offset))) {
                return true;
            }
        }
        return false;
    }
}