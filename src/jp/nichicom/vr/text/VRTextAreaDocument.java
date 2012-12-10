/** TODO <HEAD> */
package jp.nichicom.vr.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.component.AbstractVRTextArea;

/**
 * �e�L�X�g�G���A�p�̓��͕ҏW�h�L�������g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRTextDocument
 * @see AbstractVRTextArea
 */
public class VRTextAreaDocument extends AbstractVRTextDocument {

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param textArea �����Ώۂ̃e�L�X�g�t�B�[���h
     */
    public VRTextAreaDocument(AbstractVRTextArea textArea) {
        super();
        setTextComponent(textArea);
    }

    /**
     * �֘A�t�����ꂽ�e�L�X�g�t�B�[���h��Ԃ��܂��B
     * 
     * @return �֘A�t�����ꂽ�e�L�X�g�t�B�[���h
     */
    public AbstractVRTextArea getTextArea() {
        return (AbstractVRTextArea) getTextComponent();
    }

    /**
     * ������}�������ł��B
     * 
     * @param offset �I�t�Z�b�g
     * @param str ������
     * @param attr ����
     * @throws BadLocationException ������O
     */
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (isInsertProcessDisabled(offset, str, attr)) {
            return;
        }

        AbstractVRTextArea area = getTextArea();
        if (area instanceof AbstractVRTextArea) {

            //�ő啶�����`�F�b�N
            str = getMaxLengthCheckedText(str, area.getMaxLength(), area
                    .getText(), area.isByteMaxLength());
            if (str == null) {
                return;
            }

            //������ʃ`�F�b�N
            if (isErrorCharType(offset, str, area.getCharType(), area.getText())) {
                return;
            }
        }
        super.insertString(offset, str, attr);
    }

    /**
     * ������폜�����ł��B
     * 
     * @param offset �I�t�Z�b�g
     * @param length ������
     */
    public void remove(int offset, int length) throws BadLocationException {
        if (!isAbsoluteEditable()) {
            if (!getTextArea().hasFocus()) {
                return;
            }
        }

        //�ŏ��������`�F�b�N
        if (isMinimumLengthError(offset, length)) {
            return;
        }
        super.remove(offset, length);
    }

    /**
     * �ŏ������������Ɉᔽ���Ă��邩��Ԃ��܂����܂��B
     * 
     * @param offset �I�t�Z�b�g
     * @param length ������
     * @return �ŏ������������Ɉᔽ���Ă��邩
     */
    protected boolean isMinimumLengthError(int offset, int length) {
        int len = getTextArea().getMinLength();
        if ((len > 0) && (len >= getTextArea().getText().length())) {
            return true;
        }
        return false;
    }

}