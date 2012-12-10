/** TODO <HEAD> */
package jp.nichicom.vr.bind.event;

import java.util.EventObject;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * �o�C���h�p�̃f�[�^�Ɋ֘A����C�x���g�I�u�W�F�N�g�ł��B
 * <p>
 * �o�C���h�p�̃f�[�^�ɑ΂��čs�Ȃ�������ɑΉ�����C�x���g�����i�[���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindSource
 * @see VRBindSourceEventListener
 */
public class VRBindSourceEvent extends EventObject {
    protected int index;

    protected Object key;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param source �C�x���g������
     * @param index �v�f�ԍ�
     * @param key �v�f�L�[
     */
    public VRBindSourceEvent(Object source, int index, Object key) {
        super(source);

    }

    /**
     * �v�f�ԍ���Ԃ��܂��B
     * 
     * @return �v�f�ԍ�
     */
    public int getIndex() {
        return index;
    }

    /**
     * �v�f�L�[��Ԃ��܂��B
     * 
     * @return �v�f�L�[
     */
    public Object getKey() {
        return key;
    }

    /**
     * �v�f�ԍ���ݒ肵�܂��B
     * 
     * @param index �v�f�ԍ�
     */
    protected void setIndex(int index) {
        this.index = index;
    }

    /**
     * �v�f�L�[��ݒ肵�܂��B
     * 
     * @param key �v�f�L�[
     */
    protected void setKey(Object key) {
        this.key = key;
    }

    /**
     * �C�x���g��������ݒ肵�܂��B
     * 
     * @param source �C�x���g������
     */
    public void setSource(Object source) {
        this.source = source;
    }
}