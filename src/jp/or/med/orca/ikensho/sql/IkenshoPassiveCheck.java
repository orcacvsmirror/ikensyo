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
package jp.or.med.orca.ikensho.sql;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.sql.ACPassiveTask;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

public class IkenshoPassiveCheck {
    protected HashMap passiveSpace = new HashMap();
    protected ArrayList passiveTasks = new ArrayList();

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoPassiveCheck() {
    }

    /**
     * �p�b�V�u�`�F�b�N�p�̌������ʂ�ޔ����܂��B
     * 
     * @param key ��r�L�[
     * @param data ��������
     */
    public void reservedPassive(ACPassiveKey key, VRArrayList data) {
        VRArrayList clone = new VRArrayList();
        Iterator it = data.iterator();
        while (it.hasNext()) {
            VRMap srcRow = (VRMap) it.next();
            VRMap newRow = new VRHashMap();
            Iterator mit = srcRow.entrySet().iterator();
            while (mit.hasNext()) {
                Entry entry = (Entry) mit.next();
                Object obj = entry.getValue();
                newRow.setData(entry.getKey(), obj);
            }
            clone.addData(newRow);
        }
        passiveSpace.put(key, clone);
    }

    /**
     * �p�b�V�u�`�F�b�N�p�̌������ʂ����������܂��B
     */
    public void clearReservedPassive() {
        passiveSpace.clear();
    }

    /**
     * �p�b�V�u�`�F�b�N�^�X�N�����������܂��B
     */
    public void clearPassiveTask() {
        passiveTasks.clear();
    }

    /**
     * �폜�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    public void addPassiveDeleteTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_DELETE, key,
                row));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * �X�V�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    public void addPassiveUpdateTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_UPDATE, key,
                row));
    }

    /**
     * �p�b�V�u�L�[�}�b�v����Y�����錟�����ʃI�u�W�F�N�g��Ԃ��܂��B
     * 
     * @param key ��r�L�[
     * @return �������ʃI�u�W�F�N�g
     */
    protected Object matchPassiveKey(ACPassiveKey key) {
        Iterator it = passiveSpace.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            Object o = entry.getKey();
            if (o instanceof ACPassiveKey) {
                ACPassiveKey targetKey = (ACPassiveKey) o;
                if (targetKey.equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    /**
     * �p�b�V�u�`�F�b�N�����s���܂��B
     * 
     * @throws Exception ������O
     * @return boolean �`�F�b�N����
     */
    public IkenshoFirebirdDBManager getPassiveCheckedDBManager()
            throws Exception {
        IkenshoFirebirdDBManager dbm = null;

        if (passiveTasks.size() == 0) {
            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();
            return dbm;
        }

        Iterator it = passiveTasks.iterator();
        while (it.hasNext()) {
            ACPassiveTask task = (ACPassiveTask) it.next();
            Object passiveDataObj = matchPassiveKey(task.getKey());
            if (!(passiveDataObj instanceof VRArrayList)) {
                throw new IllegalArgumentException("���O�Ɍ������ʂ�ޔ����Ă��Ȃ��^�X�N["
                        + task.toString() + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
            }
            VRArrayList passiveArray = (VRArrayList) passiveDataObj;
            if (passiveArray.getDataSize() <= task.getTargetRow()) {
                throw new IllegalArgumentException("�ޔ��ς݃f�[�^���𒴂���s���w�肪�Ȃ��ꂽ�^�X�N["
                        + task.toString() + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
            }
            passiveDataObj = passiveArray.getData(task.getTargetRow());
            if (!(passiveDataObj instanceof VRMap)) {
                throw new IllegalArgumentException(
                        "�ޔ��ς݃f�[�^��VRArrayList<VRHashMap>�`���łȂ��^�X�N["
                                + task.toString() + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
            }
            VRMap passiveMap = (VRMap) passiveDataObj;

            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" " + task.getKey().getServerPassiveTimeField());
            sb.append(" FROM ");
            sb.append(task.getKey().getTable());
            sb.append(" WHERE");
            int size = task.getKey().getFields().length;
            for (int i = 0; i < size; i++) {
                String field = task.getKey().getFields()[i];
                Object data = VRBindPathParser.get(field, passiveMap);
                if (i == 0) {
                    sb.append(" (");
                } else {
                    sb.append(" AND (");
                }
                sb.append(field);
                sb.append("=");
                Format format = task.getKey().getFormats()[i];
                if (format == null) {
                    sb.append(data);
                } else {
                    sb.append(format.format(data));
                }
                sb.append(")");
            }
            String sql = sb.toString();

            dbm = new IkenshoFirebirdDBManager();
            try {
                dbm.beginTransaction();
                VRArrayList result = (VRArrayList) dbm.executeQuery(sql);

                switch (task.getCommandType()) {
                case ACPassiveTask.PASSIVE_DELETE:
                case ACPassiveTask.PASSIVE_UPDATE: {
                    // �폜�E�X�V�^�X�N���X�V�`�F�b�N
                    if (result.getDataSize() == 0) {
                        return null;
                    }
                    Object old = VRBindPathParser.get(task.getKey()
                            .getClientPassiveTimeField(), passiveMap);
                    Object now = VRBindPathParser.get(task.getKey()
                            .getServerPassiveTimeField(), (VRMap) result
                            .getData());
                    if (!old.equals(now)) {
                        return null;
                    }
                    break;
                }
                case ACPassiveTask.PASSIVE_INSERT:

                    // �ǉ��^�X�N���s�݃`�F�b�N
                    if (result.getDataSize() != 0) {
                        return null;
                    }
                default:
                    throw new IllegalArgumentException("��Ή��̃^�X�N["
                            + task.toString() + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                }
            } catch (Exception ex) {
                dbm.rollbackTransaction();
                return null;
            }
        }

        return dbm;
    }

}
