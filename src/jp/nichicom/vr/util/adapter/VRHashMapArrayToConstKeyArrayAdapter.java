/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * �o�C���h�\�[�X�@�\����������HashMap��ArrayList���A�_�v�^�N���X�ł��B
 * <p>
 * <code>VRHshMap</code> �`���̃I�u�W�F�N�g���Œ�L�[�Ɋ�Â� <code>VRArrayList</code>
 * �Ƃ��ē��ߓI�Ɉ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 * @see VRBindSourceEventListener
 * @see VRBindSourceAdapter
 */
public class VRHashMapArrayToConstKeyArrayAdapter implements VRBindSource,
        VRBindSourceAdapter {

    protected VRBindSource adaptee;

    protected Object key;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
     */
    public VRHashMapArrayToConstKeyArrayAdapter(VRBindSource adaptee, Object key) {
        setAdaptee(adaptee);
        setKey(key);
    }

    public void addBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().addBindSourceEventListener(listener);
    }

    public void addData(Object data) {
        getAdaptee().addData(data);
    }

    public void clearData() {
        getAdaptee().clearData();
    }

    /**
     * �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X��Ԃ��܂��B
     * 
     * @return adaptee
     */
    public VRBindSource getAdaptee() {
        return adaptee;
    }

    public Object getData() {
        return ((VRBindSource) getAdaptee().getData()).getData(getKey());
    }

    public Object getData(int index) {
        return ((VRBindSource) getAdaptee().getData(index)).getData(getKey());
    }

    public Object getData(Object key) {
        return ((VRBindSource) getAdaptee().getData(key)).getData(getKey());
    }

    public int getDataSize() {
        return getAdaptee().getDataSize();
    }

    public Object getKey() {
        return key;
    }

    public void removeBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().removeBindSourceEventListener(listener);
    }

    public void removeData(int index) {
        getAdaptee().removeData(index);
    }

    public void removeData(Object key) {
        removeData(parseKey(key));
    }

    public void setData(int index, Object data) {
        ((VRBindSource) getAdaptee().getData(index)).setData(getKey(), data);
    }

    public void setData(Object data) {
        ((VRBindSource) getAdaptee().getData()).setData(getKey(), data);
    }

    public void setData(Object key, Object data) {
        ((VRBindSource) getAdaptee().getData(parseKey(key))).setData(getKey(),
                data);
    }

    public void setKey(Object key) {
        this.key = key;
    }

    /**
     * �L�[��int�Ƃ��ĉ��߂��ĕԂ��܂��B
     * 
     * @param key �L�[
     * @return ���ߌ���
     */
    protected int parseKey(Object key) {
        if (key instanceof Integer) {
            return ((Integer) key).intValue();
        } else {
            return Integer.parseInt(String.valueOf(key));
        }
    }

    /**
     * �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X��ݒ肵�܂��B
     * 
     * @param adaptee adaptee
     */
    protected void setAdaptee(VRBindSource adaptee) {
        this.adaptee = adaptee;
    }
}