/*
 * Project code name "ORCA"
 * �厡��ӌ����쐬�\�t�g ITACHI�iJMA IKENSYO software�j
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * �A�v��: ITACHI
 * �J����: �c������
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �c������ �V�K�쐬
 * �X�V��: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.util;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;

public class IkenshoSnapshot {
    private static IkenshoSnapshot singleton;

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @return �C���X�^���X
     */
    public static IkenshoSnapshot getInstance() {
        if (singleton == null) {
            singleton = new IkenshoSnapshot();
        }
        return singleton;
    }

    protected List exclusions;

    protected Map mement;

    protected Container rootContainer;

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected IkenshoSnapshot() {
    }

    /**
     * ���������܂��B
     */
    public void clear() {
        exclusions = null;
        rootContainer = null;
        mement = null;
    }

    /**
     * �X�i�b�v�V���b�g�̏��O�Ώۂ�Ԃ��܂��B
     * 
     * @return �X�i�b�v�V���b�g�̏��O�Ώ�
     */
    public List getExclusions() {
        return exclusions;
    }

    /**
     * �X�i�b�v�V���b�g��r���̒l��Ԃ��܂��B
     * 
     * @return �X�i�b�v�V���b�g��r���̒l
     */
    public Map getMemento() {
        return mement;
    }

    /**
     * �X�i�b�v�V���b�g�Ώۂ̊��p�l����Ԃ��܂��B
     * 
     * @return �X�i�b�v�V���b�g�Ώۂ̊��p�l��
     */
    public Container getRootContainer() {
        return rootContainer;
    }

    /**
     * �ߋ��̃X�i�b�v�V���b�g�ƌ��݂̒l���r���āA�ύX�����邩��Ԃ��܂��B
     * 
     * @return �ύX�����邩
     * @throws ParseException
     */
    public boolean isModified() throws ParseException {
        if (mement == null) {
            // ���X�i�b�v�V���b�g
            return true;
        }
        if (rootContainer == null) {
            // �R���e�i���ݒ�
            return true;
        }

        HashMap newMement = new HashMap();
        snapshotComponent(rootContainer, newMement);

        return isModifiedEnum(newMement, mement);
    }

    /**
     * �X�i�b�v�V���b�g��K�p���܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void restore() throws ParseException {
        if (rootContainer == null) {
            return;
        }
        restoreComponent(rootContainer, mement);
    }

    /**
     * �X�i�b�v�V���b�g�̏��O�Ώۂ�ݒ肵�܂��B
     * <p>
     * ���O�������R���|�[�l���g�����X�g�����Ďw�肵�܂��B
     * </p>
     * 
     * @param exclusions �X�i�b�v�V���b�g�̏��O�Ώ�
     */
    public void setExclusions(List exclusions) {
        this.exclusions = exclusions;
    }

    /**
     * �X�i�b�v�V���b�g��r���̒l��Ԃ��܂��B
     * 
     * @param mement �X�i�b�v�V���b�g��r���̒l
     */
    public void setMemento(Map mement) {
        this.mement = mement;
    }

    /**
     * �X�i�b�v�V���b�g�Ώۂ̊��p�l����ݒ肵�܂��B
     * 
     * @param rootContainer �X�i�b�v�V���b�g�Ώۂ̊��p�l��
     */
    public void setRootContainer(Container rootContainer) {
        this.rootContainer = rootContainer;
    }

    /**
     * �X�i�b�v�V���b�g���擾���܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void snapshot() throws ParseException {
        if (rootContainer == null) {
            return;
        }
        mement = new HashMap();
        snapshotComponent(rootContainer, mement);
    }

    /**
     * �ċA�I�ɓ�̃I�u�W�F�N�g���r���A���������邩��Ԃ��܂��B
     * 
     * @param x ��r�Ώ�1
     * @param y ��r�Ώ�2
     * @return ���������邩
     */
    protected boolean isModifiedEnum(Object x, Object y) {
        if (x instanceof Map) {
            if (!(y instanceof Map)) {
                return true;
            }

            Map xM = (Map) x;
            Map yM = (Map) y;
            if (xM.size() != yM.size()) {
                return true;
            }
            Iterator it = xM.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry entry = (Map.Entry) it.next();
                if (!yM.containsKey(entry.getKey())) {
                    return true;
                }
                if (isModifiedEnum(entry.getValue(), yM.get(entry.getKey()))) {
                    return true;
                }
            }
        } else if (x instanceof List) {
            if (!(y instanceof List)) {
                return true;
            }
            List xL = (List) x;
            List yL = (List) y;
            int end = xL.size();
            if (end != yL.size()) {
                return true;
            }
            for (int i = 0; i < end; i++) {
                if (isModifiedEnum(xL.get(i), yL.get(i))) {
                    return true;
                }
            }
        } else {
            if (x == null) {
                return y != null;
            }
            return !x.equals(y);
        }
        return false;

    }

    /**
     * �ċA�I�ɃX�i�b�v�V���b�g��K�p���܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param map �X�i�b�v�V���b�g�f�[�^�擾��
     * @throws ParseException ��͗�O
     */
    protected void restoreComponent(Component comp, Map map)
            throws ParseException {
        if (getExclusions().contains(comp)) {
            return;
        }

        if (comp instanceof VRBindable) {
            VRBindable bind = (VRBindable) comp;
            VRBindSource oldSource = bind.getSource();
            bind.setSource((VRBindSource) map.get(comp));
            bind.bindSource();
            bind.setSource(oldSource);
            return;
        }
        if (comp instanceof Container) {
            restoreContainer((Container) comp, map);
        }
    }

    /**
     * �ċA�I�ɃX�i�b�v�V���b�g��K�p���܂��B
     * 
     * @param container �e�R���e�i
     * @param map �X�i�b�v�V���b�g�f�[�^�擾��
     * @throws ParseException ��͗�O
     */
    protected void restoreContainer(Container container, Map map)
            throws ParseException {

        int end = container.getComponentCount();
        for (int i = 0; i < end; i++) {
            restoreComponent(container.getComponent(i), map);
        }
    }

    /**
     * �ċA�I�ɃX�i�b�v�V���b�g���擾���܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param map �X�i�b�v�V���b�g�f�[�^�i�[��
     * @throws ParseException ��͗�O
     */
    protected void snapshotComponent(Component comp, Map map)
            throws ParseException {
        if ((getExclusions() != null) && getExclusions().contains(comp)) {
            return;
        }

        if (comp instanceof VRBindable) {
            VRBindable bind = (VRBindable) comp;
            VRBindSource oldSource = bind.getSource();
            VRBindSource newSource;
            Object obj = bind.createSource();
            if (obj instanceof VRBindSource) {
                newSource = (VRBindSource) obj;
            } else {
                newSource = new VRHashMap();
            }
            bind.setSource(newSource);
            bind.applySource();
            bind.setSource(oldSource);
            map.put(bind, newSource);
            return;
        }
        if (comp instanceof Container) {
            snapshotContainer((Container) comp, map);
        }
    }

    /**
     * �ċA�I�ɃX�i�b�v�V���b�g���擾���܂��B
     * 
     * @param container �e�R���e�i
     * @param map �X�i�b�v�V���b�g�f�[�^�i�[��
     * @throws ParseException ��͗�O
     */
    protected void snapshotContainer(Container container, Map map)
            throws ParseException {

        int end = container.getComponentCount();
        for (int i = 0; i < end; i++) {
            snapshotComponent(container.getComponent(i), map);
        }
    }
}
