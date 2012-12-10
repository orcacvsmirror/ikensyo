/** TODO <HEAD> */
package jp.nichicom.vr.component;

import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.text.Document;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.text.AbstractVRTextDocument;
import jp.nichicom.vr.text.VRCharType;

/**
 * �o�C���h�@�\�����������e�L�X�g�t�B�[���h�ł��B
 * <p>
 * AbstractVRTextDocument�̓����ɂ���ē��͉\�ȕ�����ʂ�ŏ��E�ő啶���񒷂𐧌�����@�\���������Ă��܂��B
 * </p>
 * <p>
 * InputSubset�w��ɂ��IME���[�h������������Ă��܂��B
 * </p>
 * <p>
 * Format�w��ɂ����͒l�̃t�H�[�}�b�g�ϊ��������������Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractVRTextField
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see InputSubset
 * @see Format
 * @see VRFormatEventListener
 * @see VRCharType
 * @see AbstractVRTextDocument
 */
public class VRTextField extends AbstractVRTextField {

    public VRTextField() {
        super();
    }

    public VRTextField(Document doc, String text, int columns) {
        super(doc, text, columns);
    }

    public VRTextField(int columns) {
        super(columns);
    }

    public VRTextField(String text) {
        super(text);
    }

    public VRTextField(String text, int columns) {
        super(text, columns);
    }
}