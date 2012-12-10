/** TODO <HEAD> */
package jp.nichicom.vr.component.event;

import java.util.EventObject;

/**
 * �t�H�[�}�b�g�Ɋ֘A����C�x���g�I�u�W�F�N�g�ł��B
 * <p>
 * ���͒l�̃t�H�[�}�b�g����ɑΉ�����C�x���g�����i�[���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRFormatEventListener
 * @see EventObject
 */
public class VRFormatEvent extends EventObject {
    protected Object parseValue;
    protected Object oldModel;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param source �C�x���g������
     * @param oldModel �ύX�O�̃��f���l
     * @param parseValue �ݒ肵�悤�Ƃ����l
     */
    public VRFormatEvent(Object source, Object oldModel, Object parseValue) {
        super(source);
        setOldModel(oldModel);
        setParseValue(parseValue);
    }

    /**
     * �ݒ肵�悤�Ƃ����l ��Ԃ��܂��B
     * 
     * @return �ݒ肵�悤�Ƃ����l
     */
    public Object getParseValue() {
        return parseValue;
    }

    /**
     * �ύX�O�̃��f���l��Ԃ��܂��B
     * 
     * @return �ύX�O�̃��f���l
     */
    public Object getOldModel() {
        return oldModel;
    }

    /**
     * �ݒ肵�悤�Ƃ����l ��ݒ肵�܂��B
     * 
     * @param parseValue �ݒ肵�悤�Ƃ����l
     */
    public void setParseValue(Object parseValue) {
        this.parseValue = parseValue;
    }

    /**
     * �C�x���g��������ݒ肵�܂��B
     * 
     * @param source �C�x���g������
     */
    public void setSource(Object source) {
        this.source = source;
    }

    /**
     * �ύX�O�̃��f���l��ݒ肵�܂��B
     * 
     * @param oldModel �ύX�O�̃��f���l
     */
    protected void setOldModel(Object oldModel) {
        this.oldModel = oldModel;
    }
}