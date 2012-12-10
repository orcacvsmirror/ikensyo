/** TODO <HEAD> */
package jp.nichicom.vr.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.component.AbstractVRTextField;

/**
 * �e�L�X�g�t�B�[���h�p�̓��͕ҏW�h�L�������g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRTextDocument
 * @see AbstractVRTextField
 */
public class VRTextFieldDocument extends AbstractVRTextDocument {

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param textField �����Ώۂ̃e�L�X�g�t�B�[���h
     */
    public VRTextFieldDocument(AbstractVRTextField textField) {
        super();
        setTextComponent(textField);
    }

    /**
     * �֘A�t�����ꂽ�e�L�X�g�t�B�[���h��Ԃ��܂��B
     * 
     * @return �֘A�t�����ꂽ�e�L�X�g�t�B�[���h
     */
    public AbstractVRTextField getTextField() {
        return (AbstractVRTextField) getTextComponent();
    }

    /**
     * ������}�������ł��B
     * 
     * @param offset �I�t�Z�b�g
     * @param str ������
     * @param attr ����
     */
    public void insertString(int offset, String str, AttributeSet attr)
            throws BadLocationException {

        if (isInsertProcessDisabled(offset, str, attr)) {
            return;
        }

        AbstractVRTextField field = getTextField();
        if (field instanceof AbstractVRTextField) {
            //�ő啶�����`�F�b�N
            str = getMaxLengthCheckedText(str, field.getMaxLength(), field
                    .getText(), field.isByteMaxLength());
            if (str == null) {
                return;
            }

            //������ʃ`�F�b�N
            if (isErrorCharType(offset, str, field.getCharType(), field
                    .getText())) {
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
            if (!getTextField().hasFocus()) {
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
     * @param offset �I�t�Z�b�g
     * @param length ������
     * @return �ŏ������������Ɉᔽ���Ă��邩
     */
    protected boolean isMinimumLengthError(int offset, int length){
        int len = getTextField().getMinLength();
        if ((len > 0) && (len >= getTextField().getText().length())) {
            return true;
        }
        return false;
    }
}