/** TODO <HEAD> */
package jp.nichicom.vr.util.adapter;

import javax.swing.AbstractListModel;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEvent;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * �o�C���h�\�[�X�@�\����������ListModel�̃A�_�v�^�N���X�ł��B
 * <p>
 * <code>VRBindSource</code> �`���̃I�u�W�F�N�g��ListModel�Ƃ��ē��ߓI�Ɉ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractListModel
 * @see VRBindSource
 * @see VRBindSourceEventListener
 * @see VRBindSourceAdapter
 */
public class VRListModelAdapter extends AbstractListModel implements
        VRBindSource, VRBindSourceEventListener, VRBindSourceAdapter {
    protected VRBindSource adaptee;

    /**
     * �R���X�g���N�^�ł��B
     */
    public VRListModelAdapter() {
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
     */
    public VRListModelAdapter(VRBindSource adaptee) {
        setAdaptee(adaptee);
    }

    public void addBindSourceEventListener(VRBindSourceEventListener listener) {
        getAdaptee().addBindSourceEventListener(listener);
    }

    public void addData(Object data) {
        getAdaptee().addData(data);
    }

    public void addSource(VRBindSourceEvent e) {
//        int index = e.getIndex();
        int size = getSize();
        super.fireIntervalAdded(this, size-1, size);
    }

    public void changeSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        super.fireContentsChanged(this, index - 1, index);
    }

    public void clearData() {
        getAdaptee().clearData();
    }

    public void clearSource(VRBindSourceEvent e) {
        super.fireIntervalRemoved(this, 0, 0);
    }

    /**
     * �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X��Ԃ��܂��B
     * 
     * @return �A�_�v�e�B�[
     */
    public VRBindSource getAdaptee() {
        return adaptee;
    }

    public Object getData() {
        return getAdaptee().getData();
    }

    public Object getData(int index) {
        return getAdaptee().getData(index);
    }

    public Object getData(Object key) {
        return getAdaptee().getData(key);
    }

    public int getDataSize() {
        return getAdaptee().getDataSize();
    }

    public Object getElementAt(int index) {
        return getData(index);
    }

    public int getSize() {
        return getDataSize();
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

    public void removeSource(VRBindSourceEvent e) {
        int index = e.getIndex();
        if (index >= 0) {
            super.fireIntervalRemoved(this, index, index);
        }
    }

    public void setData(int index, Object data) {
        getAdaptee().setData(index, data);
    }

    public void setData(Object data) {
        getAdaptee().setData(data);
    }

    public void setData(Object key, Object data) {
        setData(parseKey(key), data);
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
     * @param adaptee �A�_�v�e�B�[
     */
    public void setAdaptee(VRBindSource adaptee) {
        if (this.adaptee != null) {
            //���g���\�[�X�̃��X�i�Ƃ��ēo�^
            this.adaptee.removeBindSourceEventListener(this);
        }
        this.adaptee = adaptee;
        if (adaptee != null) {
            //���g���\�[�X�̃��X�i�Ƃ��ēo�^
            adaptee.addBindSourceEventListener(this);
        }
    }
}