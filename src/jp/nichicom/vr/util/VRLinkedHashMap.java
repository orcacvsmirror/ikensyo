package jp.nichicom.vr.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.event.VRBindSourceEvent;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;

/**
 * �o�C���h�\�[�X�@�\����������LinkedHashMap�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see LinkedHashMap
 * @see VRMap
 * @see VRBindSource
 * @see VRBindSourceEventListener
 */
public class VRLinkedHashMap extends LinkedHashMap implements VRMap {
    protected ArrayList listeners = new ArrayList();

    /**
     * Constructs an empty <tt>HashMap</tt> with the default initial capacity
     * (16) and the default load factor (0.75).
     */
    public VRLinkedHashMap() {
        super();
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and the default load factor (0.75).
     * 
     * @param initialCapacity the initial capacity.
     * @throws IllegalArgumentException if the initial capacity is negative.
     */
    public VRLinkedHashMap(int initialCapacity) {
        super(initialCapacity);
    }

    /**
     * Constructs an empty <tt>HashMap</tt> with the specified initial
     * capacity and load factor.
     * 
     * @param initialCapacity The initial capacity.
     * @param loadFactor The load factor.
     * @throws IllegalArgumentException if the initial capacity is negative or
     *             the load factor is nonpositive.
     */
    public VRLinkedHashMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
    }

    /**
     * Constructs a new <tt>HashMap</tt> with the same mappings as the
     * specified <tt>Map</tt>. The <tt>HashMap</tt> is created with default
     * load factor (0.75) and an initial capacity sufficient to hold the
     * mappings in the specified <tt>Map</tt>.
     * 
     * @param m the map whose mappings are to be placed in this map.
     * @throws NullPointerException if the specified map is null.
     */
    public VRLinkedHashMap(Map m) {
        super(m);
    }

    public void addBindSourceEventListener(VRBindSourceEventListener listener) {
        if ((listener instanceof VRBindSourceEventListener)
                && (listener != this)) {
            listeners.add(listener);
        }
    }

    public void addData(Object data) {
        throw new ArrayIndexOutOfBoundsException();

    }

    public void addSource(VRBindSourceEvent e) {
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).addSource(e);
        }
    }

    public void changeSource(VRBindSourceEvent e) {
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).changeSource(e);
        }
    }

    public void clear() {
        removeElementListener(this);
        super.clear();
        fireClearSource();
    }

    public void clearData() {
        clear();
    }

    public void clearSource(VRBindSourceEvent e) {
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).clearSource(e);
        }
    }

    public Object getData() {
        return getData(null);
    }

    public Object getData(int index) {
        return parseIndex(index).getValue();
    }

    public Object getData(Object key) {
        return super.get(key);
    }

    public int getDataSize() {
        return super.size();
    }

    public Object put(Object key, Object value) {
        Object ret;
        if (super.containsKey(key)) {
            // ����
            ret = super.put(key, value);
            fireChangeSource(-1, key);
        } else {
            // �V�K
            if (value instanceof VRBindSource) {
                addElementListener(value);
            }
            ret = super.put(key, value);
            fireAddSource();
        }
        return ret;
    }

    public Object remove(Object key) {
        Object ret = removeElementListener(super.remove(key));
        fireRemoveSource(-1, key);
        return ret;
    }

    public void removeBindSourceEventListener(VRBindSourceEventListener listener) {
        listeners.remove(listener);
    }

    public void removeData(int index) {
        removeData(parseIndex(index).getKey());
    }

    public void removeData(Object key) {
        remove(key);
    }

    public void removeSource(VRBindSourceEvent e) {
        Iterator it = listeners.iterator();
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).removeSource(e);
        }
    }

    public void setData(int index, Object data) {
        setData(parseIndex(index).getKey(), data);
    }

    public void setData(Object data) {
        setData(null, data);
    }

    public void setData(Object key, Object data) {
        put(key, data);
    }

    /**
     * �v�f��VRBindSource�̏ꍇ�͎��g�����X�i�Ƃ��ēo�^���܂��B
     * 
     * @param o �v�f
     */
    protected void addElementListener(Object o) {
        if (o instanceof VRBindSource) {
            ((VRBindSource) o).addBindSourceEventListener(this);
        }
    }

    /**
     * �t�@�C�i���C�U�ł��B
     */
    protected void finalize() {
        removeElementListener(this);
    }

    /**
     * �o�C���h�\�[�X�̒ǉ������X�i�ɒʒm���܂��B
     */
    protected void fireAddSource() {
        Iterator it = listeners.iterator();
        VRBindSourceEvent e = new VRBindSourceEvent(this, getDataSize() - 1,
                null);
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).addSource(e);
        }
    }

    /**
     * �o�C���h�\�[�X�̕ύX�����X�i�ɒʒm���܂��B
     * 
     * @param index �v�f�ԍ�
     * @param key �v�f�L�[
     */
    protected void fireChangeSource(int index, Object key) {
        Iterator it = listeners.iterator();
        VRBindSourceEvent e = new VRBindSourceEvent(this, index, key);
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).changeSource(e);
        }
    }

    /**
     * �o�C���h�\�[�X�W���̏����������X�i�ɒʒm���܂��B
     */
    protected void fireClearSource() {
        Iterator it = listeners.iterator();
        VRBindSourceEvent e = new VRBindSourceEvent(this, -1, null);
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).clearSource(e);
        }
    }

    /**
     * �o�C���h�\�[�X�̍폜�����X�i�ɒʒm���܂��B
     * 
     * @param index �v�f�ԍ�
     * @param key �v�f�L�[
     */
    protected void fireRemoveSource(int index, Object key) {
        Iterator it = listeners.iterator();
        VRBindSourceEvent e = new VRBindSourceEvent(this, index, key);
        while (it.hasNext()) {
            ((VRBindSourceEventListener) it.next()).removeSource(e);
        }
    }

    /**
     * �n�b�V���}�b�v�̃G���g���C�e���[�^��Ԃ��܂��B
     * 
     * @return �G���g���C�e���[�^
     */
    protected Iterator iterator() {
        return super.entrySet().iterator();
    }

    /**
     * �w��ԖڂɈʒu����G���g����Ԃ��܂��B
     * 
     * @param index �ԍ�
     * @return �G���g��
     */
    protected Map.Entry parseIndex(int index) {
        int i = 0;
        Iterator it = iterator();
        while (it.hasNext()) {
            if (i == index) {
                return (Map.Entry) it.next();
            }
            it.next();
            i++;
        }
        throw new ArrayIndexOutOfBoundsException(index);
    }

    /**
     * �W�����̗v�f�̃��X�i���玩�g�����O���܂��B
     * 
     * @param map �W��
     */
    protected void removeElementListener(Map map) {
        Iterator it = map.entrySet().iterator();
        while (it.hasNext()) {
            removeElementListener(((Map.Entry) it.next()).getValue());
        }
    }

    /**
     * �v�f�̃��X�i���玩�g�����O���܂��B
     * 
     * @param o �v�f
     */
    protected Object removeElementListener(Object o) {
        if (o instanceof VRBindSource) {
            ((VRBindSource) o).removeBindSourceEventListener(this);
        }
        return o;
    }
}
