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
 * �o�C���h�@�\�����������e�L�X�g�G���A�ł��B
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
 * �^�u�����̓��͉ې�����������Ă��܂��B
 * </p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see AbstractVRTextArea
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see InputSubset
 * @see Format
 * @see VRFormatEventListener
 * @see VRCharType
 * @see AbstractVRTextDocument
 */
public class VRTextArea extends AbstractVRTextArea {

    public VRTextArea() {
        super();
    }

    public VRTextArea(Document doc, String text, int rows, int columns) {
        super(doc, text, rows, columns);
    }

    public VRTextArea(int rows, int columns) {
        super(rows, columns);
    }

    public VRTextArea(String text) {
        super(text);
    }

    public VRTextArea(String text, int rows, int columns) {
        super(text, rows, columns);
    }
}