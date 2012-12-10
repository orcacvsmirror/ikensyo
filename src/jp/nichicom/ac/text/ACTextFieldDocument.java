package jp.nichicom.ac.text;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.text.VRTextFieldDocument;

/**
 * �e�L�X�g�t�B�[���h�p�̃h�L�������g�N���X�ł��B
 * <p>
 * ���s�����������������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTextFieldDocument
 */

public class ACTextFieldDocument extends VRTextFieldDocument {
    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param textField �����Ώۂ̃e�L�X�g�t�B�[���h
     */
    public ACTextFieldDocument(AbstractVRTextField textField) {
        super(textField);
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
        // 2006/02/08[Tozo Tanaka] : replace begin
        // super.insertString(offset, str.replaceAll("\n", ""), attr);
        if(str!=null){
            //VT(�����^�u)�Ɖ��s�͏���
            str = str.replaceAll("\u000b", "").replaceAll(
                    "\n", "");
        }
        super.insertString(offset, str, attr);
        // 2006/02/08[Tozo Tanaka] : replace end
    }

}
