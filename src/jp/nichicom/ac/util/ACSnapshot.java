package jp.nichicom.ac.util;

import java.awt.Component;
import java.awt.Container;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;

/**
 * �X�i�b�v�V���b�g�N���X�ł��B
 * <p>
 * �X�i�b�v�V���b�g�Ƃ́A����^�C�~���O�̉�ʏ�Ԃ�ޔ����Ă����A�ޔ������f�[�^�Ɣ�r���� ���݂̉�ʏ�Ԃ͍X�V����Ă��邩�𒲂ׂ鏈���ł��B
 * </p>
 * <p>
 * ��r�E�ޔ�Ώۂ́A�ȉ��̏��������ׂĖ������Ă���R���|�[�l���g�̃f�[�^�Ɍ��肳��܂��B<br />
 * �EsetRootContainer���\�b�h�Ŏw�肵���R���e�i�ȉ��ɑ�����R���|�[�l���g�B<br />
 * �E�X�i�b�v�V���b�g�̏��O�ΏۂɎw�肵���R���e�i�ȉ��ɂ͑����Ȃ��R���|�[�l���g�B<br />
 * �E�o�C���h�p�X���w�肵�Ă���R���|�[�l���g�B
 * </p>
 * <p>
 * ���p�菇�̊T�v�͈ȉ��̒ʂ�ł��B<br />
 * <code>
 * �y�����ݒ芮����z<br />
 * setRootContainer���\�b�h�őޔ�Ώۂ̃R���e�i��ݒ�B<br />
 * ���̂Ƃ��A���O�������R���e�i������ꍇ��setExclusions���\�b�h�ŏ��O�Ώۂ��w�肵�܂��B<br />
 * snapshot���\�b�h�Ō��݂̏�Ԃ�ޔ��B<br />
 * �y�����I���O�z<br />
 * isModified���\�b�h�ŕω��������������m�F�B<br />
 * �E�ω����������ꍇ��true���Ԃ邽�߁A�ۑ����ĕ��邩�̊m�F���s�Ȃ��ȂǁA�K�X���p���Ă��������B
 * </code>
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACSnapshot {

    private List exclusions;

    private Map memento;

    private Container rootContainer;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACSnapshot() {
    }

    /**
     * ���������܂��B
     */
    public void clear() {
        exclusions = null;
        rootContainer = null;
        memento = null;
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
    protected Map getMemento() {
        return memento;
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
        if (memento == null) {
            // ���X�i�b�v�V���b�g
            return true;
        }
        if (rootContainer == null) {
            // �R���e�i���ݒ�
            return true;
        }

        HashMap newMement = new HashMap();
        snapshotComponent(rootContainer, newMement);

        // �s�v�ȃL�[�����O����
        removeExclusionKeys(newMement, createExclutionKeys(getExclusions()));

        return isModifiedEnum(newMement, memento);
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
        restoreComponent(rootContainer, memento);
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
     * �X�i�b�v�V���b�g�̏��O�Ώۂ�ݒ肵�܂��B
     * <p>
     * ���O�������R���|�[�l���g�����X�g�����Ďw�肵�܂��B
     * </p>
     * 
     * @param exclusions �X�i�b�v�V���b�g�̏��O�Ώ�
     */
    public void setExclusions(Component[] exclusions) {
        setExclusions(Arrays.asList(exclusions));
    }

    /**
     * �X�i�b�v�V���b�g��r���̒l��Ԃ��܂��B
     * 
     * @param memento �X�i�b�v�V���b�g��r���̒l
     */
    protected void setMemento(Map memento) {
        this.memento = memento;
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
     * �X�i�b�v�V���b�g�Ώۂ̊��p�l����ݒ肵�܂��B
     * <p>
     * ���O�������R���|�[�l���g�����X�g�����Ďw�肵�܂��B
     * </p>
     * 
     * @param rootContainer �X�i�b�v�V���b�g�Ώۂ̊��p�l��
     * @param exclusions �X�i�b�v�V���b�g�̏��O�Ώ�
     */
    public void setRootContainer(Container rootContainer, List exclusions) {
        setRootContainer(rootContainer);
        setExclusions(exclusions);
    }

    /**
     * �X�i�b�v�V���b�g�Ώۂ̊��p�l����ݒ肵�܂��B
     * <p>
     * ���O�������R���|�[�l���g�����X�g�����Ďw�肵�܂��B
     * </p>
     * 
     * @param rootContainer �X�i�b�v�V���b�g�Ώۂ̊��p�l��
     * @param exclusions �X�i�b�v�V���b�g�̏��O�Ώ�
     */
    public void setRootContainer(Container rootContainer, Component[] exclusions) {
        setRootContainer(rootContainer);
        setExclusions(exclusions);
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
        memento = new HashMap();
        snapshotComponent(rootContainer, memento);

        // �s�v�ȃL�[�����O����
        removeExclusionKeys(memento, createExclutionKeys(getExclusions()));
    }

    /**
     * ���O�L�[���폜���܂��B
     * 
     * @param map �L�[���O�Ώ�
     * @param keys ���O�L�[�W��
     */
    protected void removeExclusionKeys(Map map, List keys) {
        if (keys.size() == 0) {
            // ���O�L�[�����݂��Ȃ���Ή������Ȃ�
            return;
        }

        // ���O�Ώۂ̃������g��Map{�L�[=Component, �l=Object}�ł���B
        // ���̒l��S��������B
        Iterator mapIt = map.values().iterator();
        while (mapIt.hasNext()) {
            Object obj = mapIt.next();
            if (obj instanceof Map) {
                // �L�[���p�l���Ȃ�Βl��Map�ł���A����Map�̃L�[�����O����B
                Map target = (Map) obj;
                Iterator it = keys.iterator();
                while (it.hasNext()) {
                    target.remove(it.next());
                }
            }
        }
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
        if ((getExclusions() != null) && getExclusions().contains(comp)) {
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
     * �ċA�I�ɏ��O�L�[���������ĕԂ��܂��B
     * 
     * @param exclusions ���O�L�[�����Ώۂ̃R���|�[�l���g�W��
     * @return ���O�L�[�W��
     * @throws ParseException ��͗�O
     */
    protected List createExclutionKeys(List exclusions) throws ParseException {
        List list = new ArrayList();
        if (exclusions != null) {
            Iterator it = exclusions.iterator();
            while (it.hasNext()) {
                Component cmp = (Component) it.next();
                addExclutionKeysComponent(cmp, list);
            }
        }
        return list;
    }

    /**
     * �ċA�I�ɏ��O�L�[��ǉ����܂��B
     * 
     * @param container �e�R���e�i
     * @param adder ���O�L�[�ǉ���
     * @throws ParseException ��͗�O
     */
    protected void addExclutionKeysContainer(Container container, List adder)
            throws ParseException {
        int end = container.getComponentCount();
        for (int i = 0; i < end; i++) {
            addExclutionKeysComponent(container.getComponent(i), adder);
        }
    }

    /**
     * �ċA�I�ɏ��O�L�[��ǉ����܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param adder ���O�L�[�ǉ���
     * @throws ParseException ��͗�O
     */
    protected void addExclutionKeysComponent(Component comp, List adder)
            throws ParseException {
        if (comp instanceof VRBindable) {
            VRBindable bind = (VRBindable) comp;
            String bindPath = bind.getBindPath();
            if ((bindPath != null) && (!"".equals(bindPath))) {
                adder.add(bindPath);

                Object obj = bind.createSource();
                if (obj instanceof Map) {
                    adder.addAll(Arrays.asList(((Map) obj).keySet().toArray()));
                }
                return;
            }
        }
        if (comp instanceof Container) {
            addExclutionKeysContainer((Container) comp, adder);
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
