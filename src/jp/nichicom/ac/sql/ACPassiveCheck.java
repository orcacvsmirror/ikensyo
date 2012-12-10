package jp.nichicom.ac.sql;

import java.text.Format;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * �p�b�V�u�`�F�b�N�N���X�ł��B
 * <p>
 * �p�b�V�u�`�F�b�N�Ƃ́A���鎞�_�őޔ����Ă������f�[�^�ƌ��݂�DB�̓��e���r����
 * ���e�ɕω����������ꍇ�́u���҂���������ł����v�ƌ��Ȃ��A�f�[�^�̐������ێ��̂��߂ɏ����𒆎~�����邱�Ƃ�ړI���čs�Ȃ��`�F�b�N�ł��B
 * </p>
 * <p>
 * ���p�菇�̊T�v�͈ȉ��̒ʂ�ł��B<br />
 * <code>
 * �ySELECT���s��z<br />
 * reservedPassive���\�b�h�Ŕ�r�p�̃f�[�^��ޔ��B<br />
 * ���̂Ƃ��A�ޔ�������r�f�[�^��\���p�b�V�u�L�[���o�^���܂��B<br />
 * �yUPDATE�O�z<br />
 * clearPassiveTask���\�b�h�Ŕ�r�������N���A�B<br />
 * �`�F�b�N����SQL�̎�ނɉ�����addPassive�`Task���\�b�h�����s�B<br />
 * ���s����p�b�V�u�^�X�N�ɂ́A�Ή������r�f�[�^��\���p�b�V�u�L�[�����킹�Đݒ肵�܂��B<br />
 * ���ׂẴp�b�V�u�^�X�N��o�^������A�g�����U�N�V�������J�n����DBManager��������passive���\�b�h�����s�B<br />
 * �E�p�b�V�u�`�F�b�N�ɐ�������΁Atrue���Ԃ�܂��B<br />
 * �E�p�b�V�u�`�F�b�N�Ɏ��s����΁Afalse���Ԃ�܂��B
 * </code>
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACPassiveKey
 * @see ACPassiveTask
 */

public class ACPassiveCheck {

    protected HashMap passiveSpace = new HashMap();
    protected ArrayList passiveTasks = new ArrayList();

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACPassiveCheck() {
    }

    /**
     * �S���폜�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     */
    public void addPassiveDeleteTask(ACPassiveKey key) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_DELETE, key));
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
     * �폜�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param targetValue �Ώےl
     */
    public void addPassiveDeleteTask(ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_DELETE, key,
                targetBindPath, targetValue));
    }

    /**
     * �S���ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @deprecated #addPassiveInsertTask(ACPassiveKey key, VRList
     *             reserveData)���g�p���Ă��������B
     * @param key ��r�L�[
     */
    public void addPassiveInsertTask(ACPassiveKey key) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @deprecated #addPassiveInsertTask(ACPassiveKey key, int row, VRList
     *             reserveData)���g�p���Ă��������B
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     * @param reserveData �ǉ����悤�Ƃ��Ă���f�[�^
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row,
            VRList reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     * @param reserveData �ǉ����悤�Ƃ��Ă���f�[�^
     */
    public void addPassiveInsertTask(ACPassiveKey key, int row,
            VRMap reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                row));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @deprecated #addPassiveInsertTask(ACPassiveKey key, String
     *             targetBindPath, Object targetValue, VRList
     *             reserveData)���g�p���Ă��������B
     * @param key ��r�L�[
     * @param targetValue �Ώےl
     */
    public void addPassiveInsertTask(ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                targetBindPath, targetValue));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param targetValue �Ώےl
     * @param reserveData �ǉ����悤�Ƃ��Ă���f�[�^
     */
    public void addPassiveInsertTask(ACPassiveKey key, String targetBindPath,
            Object targetValue, VRList reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                targetBindPath, targetValue));
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param targetValue �Ώےl
     * @param reserveData �ǉ����悤�Ƃ��Ă���f�[�^
     */
    public void addPassiveInsertTask(ACPassiveKey key, String targetBindPath,
            Object targetValue, VRMap reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key,
                targetBindPath, targetValue));
    }

    /**
     * �S���ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param reserveData �ǉ����悤�Ƃ��Ă���f�[�^
     */
    public void addPassiveInsertTask(ACPassiveKey key, VRList reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key));
    }

    /**
     * �S���ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param reserveData �ǉ����悤�Ƃ��Ă���f�[�^
     */
    public void addPassiveInsertTask(ACPassiveKey key, VRMap reserveData) {
        reservedPassive(key, reserveData);
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_INSERT, key));
    }

    /**
     * �S���X�V�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     */
    public void addPassiveUpdateTask(ACPassiveKey key) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_UPDATE, key));
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
     * �X�V�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param targetValue �Ώےl
     */
    public void addPassiveUpdateTask(ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        passiveTasks.add(new ACPassiveTask(ACPassiveTask.PASSIVE_UPDATE, key,
                targetBindPath, targetValue));
    }

    /**
     * �p�b�V�u�`�F�b�N�^�X�N�����������܂��B
     */
    public void clearPassiveTask() {
        passiveTasks.clear();
    }

    /**
     * �p�b�V�u�`�F�b�N�p�̌������ʂ����������܂��B
     */
    public void clearReservedPassive() {
        passiveSpace.clear();
    }

    // /**
    // * �p�b�V�u�`�F�b�N�����s���܂��B
    // * <p>
    // * ���������ꍇ�̓g�����U�N�V�������J�n����DBManager��Ԃ��܂��B
    // * </p>
    // * <p>
    // * ���s�����ꍇ��null��Ԃ��܂��B
    // * </p>
    // *
    // * @throws Exception ������O
    // * @return �g�����U�N�V�������J�n����DBManager
    // * @deprecated ��������܂���BpassiveCheck(NCDBManagerable dbm)���g�p���Ă��������B
    // */
    // public NCDBManagerable getPassiveCheckedDBManager() throws Exception {
    // NCDBManagerable dbm = getTransactionBeginedDBManager();
    // if (dbm != null) {
    // if (passiveCheck(dbm)) {
    // return dbm;
    // }
    // dbm.commitTransaction();
    // }
    // return null;
    // }

    /**
     * �ޔ��f�[�^����A�w��̒l���܂ލs�ԍ���Ԃ��܂��B
     * <p>
     * �Y�����Ȃ��ꍇ��-1��Ԃ��܂��B
     * </p>
     * 
     * @param key ��r�L�[
     * @param targetBindPath �Ώۍs�����߂邽�߂̒l��
     * @param targetValue �Ώۍs�����߂邽�߂̒l
     * @return �Y���s�ԍ�
     * @throws Exception ������O
     */
    public int getResevedRowIndex(ACPassiveKey key, String targetBindPath,
            Object targetValue) throws Exception {
        Object passiveDataObj = matchPassiveKey(key);
        if (!(passiveDataObj instanceof VRList)) {
            return -1;
        }
        return getResevedRowIndex((VRList) passiveDataObj, targetBindPath,
                targetValue);
    }

    /**
     * �p�b�V�u�`�F�b�N�����s���܂��B
     * <p>
     * ���������ꍇ��true���A���s�����ꍇ��false��Ԃ��܂��B
     * </p>
     * 
     * @param dbm �g�����U�N�V�������J�n����DBManager
     * @throws Exception ������O
     * @return ����������
     */
    public boolean passiveCheck(ACDBManager dbm) throws Exception {
        if (dbm == null) {
            // Null�`�F�b�N
            return false;
        }

        if (passiveTasks.size() == 0) {
            // �p�b�V�u�^�X�N��������Ζ������ŋ���
            return true;
        }

        Iterator it = passiveTasks.iterator();
        while (it.hasNext()) {
            // �˗����ꂽ�p�b�V�u�^�X�N�S�Ă���������
            ACPassiveTask task = (ACPassiveTask) it.next();
            ACPassiveKey taskKey = task.getKey();
            Object passiveDataObj = matchPassiveKey(taskKey);
            if (!(passiveDataObj instanceof VRList)) {
                throw new IllegalArgumentException("���O�Ɍ������ʂ�ޔ����Ă��Ȃ��^�X�N["
                        + task.toString() + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
            }

            VRList passiveArray = (VRList) passiveDataObj;

            VRMap[] passiveMaps;
            switch (task.getCheckTargetType()) {
            case ACPassiveTask.CHECK_TARGET_ALL: {
                // �S���`�F�b�N�p�Ƀf�[�^����������
                int end = passiveArray.size();
                passiveMaps = new VRHashMap[end];
                for (int i = 0; i < end; i++) {
                    passiveDataObj = passiveArray.getData(i);
                    if (!(passiveDataObj instanceof VRMap)) {
                        throw new IllegalArgumentException(
                                "�ޔ��ς݃f�[�^��VRList<VRMap>�`���łȂ��^�X�N["
                                        + task.toString()
                                        + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                    }
                    passiveMaps[i] = (VRMap) passiveDataObj;
                }
                break;
            }
            case ACPassiveTask.CHECK_TARGET_ROW: {
                // �s�P���`�F�b�N�p�Ƀf�[�^�𒊏o����
                passiveMaps = new VRHashMap[1];
                int row = task.getTargetRow();

                if (passiveArray.getDataSize() <= row) {
                    throw new IllegalArgumentException(
                            "�ޔ��ς݃f�[�^���𒴂���s���w�肪�Ȃ��ꂽ�^�X�N[" + task.toString()
                                    + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                }
                passiveDataObj = passiveArray.getData(row);
                if (!(passiveDataObj instanceof VRMap)) {
                    throw new IllegalArgumentException(
                            "�ޔ��ς݃f�[�^��VRList<VRMap>�`���łȂ��^�X�N[" + task.toString()
                                    + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                }
                passiveMaps[0] = (VRMap) passiveDataObj;
                break;
            }
            case ACPassiveTask.CHECK_TARGET_VALUE: {
                // �f�[�^�P���`�F�b�N�p�Ƀf�[�^�𒊏o����
                passiveMaps = new VRHashMap[1];
                int row = getResevedRowIndex(passiveArray, task
                        .getTargetBindPath(), task.getTargetValue());
                if (row < 0) {
                    throw new IllegalArgumentException("�^�X�N[" + task.toString()
                            + "]�ɊY������f�[�^���ޔ�����Ă��܂���B");
                }
                if (passiveArray.getDataSize() <= row) {
                    throw new IllegalArgumentException(
                            "�ޔ��ς݃f�[�^���𒴂���s���w�肪�Ȃ��ꂽ�^�X�N[" + task.toString()
                                    + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                }
                passiveDataObj = passiveArray.getData(row);
                if (!(passiveDataObj instanceof VRMap)) {
                    throw new IllegalArgumentException(
                            "�ޔ��ς݃f�[�^��VRList<VRMap>�`���łȂ��^�X�N[" + task.toString()
                                    + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                }
                passiveMaps[0] = (VRMap) passiveDataObj;
                break;
            }
            default:
                throw new IllegalArgumentException("��Ή��̃^�X�N[" + task.toString()
                        + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");

            }

            try {
                int end = passiveMaps.length;
                for (int i = 0; i < end; i++) {
                    // �p�b�V�u�`�F�b�N�p��SQL�����\�z����
                    VRMap passiveMap = passiveMaps[i];

                    StringBuffer sb = new StringBuffer();
                    sb.append("SELECT");
                    sb.append(" " + taskKey.getServerPassiveTimeField());
                    sb.append(" FROM ");
                    sb.append(taskKey.getTable());
                    sb.append(" WHERE");

                    Format[] formats = taskKey.getFormats();
                    int size = taskKey.getFields().length;
                    for (int j = 0; j < size; j++) {
                        String field = taskKey.getFields()[j];
                        Object data = VRBindPathParser.get(field, passiveMap);
                        if (j == 0) {
                            sb.append(" (");
                        } else {
                            sb.append(" AND (");
                        }
                        sb.append(field);
                        sb.append("=");
                        if (formats != null) {
                            // �t�H�[�}�b�g�z���null�`�F�b�N
                            Format format = formats[j];
                            if (format != null) {
                                // �t�H�[�}�b�g��null�`�F�b�N
                                sb.append(format.format(data));
                            } else {
                                // �t�H�[�}�b�g���������ɏo��
                                sb.append(data);
                            }
                        } else {
                            // �t�H�[�}�b�g���������ɏo��
                            sb.append(data);
                        }
                        sb.append(")");
                    }

                    String sql = sb.toString();

                    VRList result = dbm.executeQuery(sql);

                    switch (task.getCommandType()) {
                    case ACPassiveTask.PASSIVE_DELETE:
                    case ACPassiveTask.PASSIVE_UPDATE: {
                        // �폜�E�X�V�^�X�N���X�V�`�F�b�N
                        if (result.getDataSize() == 0) {
                            return false;
                        }
                        Object old = VRBindPathParser.get(taskKey
                                .getClientPassiveTimeField(), passiveMap);
                        Object now = VRBindPathParser.get(taskKey
                                .getServerPassiveTimeField(), (VRMap) result
                                .getData());
                        if (!old.equals(now)) {
                            return false;
                        }
                        break;
                    }
                    case ACPassiveTask.PASSIVE_INSERT:

                        // �ǉ��^�X�N���s�݃`�F�b�N
                        if (result.getDataSize() != 0) {
                            return false;
                        }
                        break;
                    default:
                        throw new IllegalArgumentException("��Ή��̃^�X�N["
                                + task.toString() + "]���p�b�V�u�`�F�b�N�ΏۂɎw�肳��܂����B");
                    }
                }
            } catch (Exception ex) {
                if (dbm != null) {
                    dbm.rollbackTransaction();
                    // dbm.finalize();
                }
                return false;
            }

        }

        return true;
    }

    /**
     * �p�b�V�u�`�F�b�N�p�̌������ʂ�ޔ����܂��B
     * 
     * @param key ��r�L�[
     * @param data ��������
     */
    public void reservedPassive(ACPassiveKey key, VRList data) {
        VRList clone = new VRArrayList();
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
     * 1���R�[�h���̃p�b�V�u�`�F�b�N�p�̌������ʂ�ޔ����܂��B
     * 
     * @param key ��r�L�[
     * @param data ��������
     */
    public void reservedPassive(ACPassiveKey key, VRMap data) {
        VRList list = new VRArrayList();
        list.add(data);
        reservedPassive(key, list);
    }

    // /**
    // * �g�����U�N�V�������J�n����DBManager��Ԃ��܂��B
    // *
    // * @return DBManager
    // * @throws Exception ������O
    // * @deprecated �V�X�e���K���DBManager�Ɍ��肳��邽�߁A��������܂���B
    // */
    // protected NCDBManagerable getTransactionBeginedDBManager() throws
    // Exception {
    // NCFrameEventProcessable processer = NCFrame.getInstance()
    // .getFrameEventProcesser();
    // if (processer == null) {
    // return null;
    // }
    // NCDBManagerable dbm = processer.getDBManager();
    // if (dbm == null) {
    // return null;
    // }
    // dbm.beginTransaction();
    // return dbm;
    // }

    /**
     * �ޔ��f�[�^����A�w��̒l���܂ލs�ԍ���Ԃ��܂��B
     * <p>
     * �Y�����Ȃ��ꍇ��-1��Ԃ��܂��B
     * </p>
     * 
     * @param list ��r��
     * @param targetBindPath �Ώۍs�����߂邽�߂̒l��
     * @param targetValue �Ώۍs�����߂邽�߂̒l
     * @return �Y���s�ԍ�
     * @throws Exception ������O
     */
    protected int getResevedRowIndex(VRList list, String targetBindPath,
            Object targetValue) throws Exception {
        // NCCommon.getInstance().getMa
        int i = 0;
        Iterator it = list.iterator();
        if (targetValue == null) {
            while (it.hasNext()) {
                VRMap row = (VRMap) it.next();
                if (VRBindPathParser.get(targetBindPath, row) == null) {
                    return i;
                }
                i++;
            }

        } else {
            while (it.hasNext()) {
                VRMap row = (VRMap) it.next();
                if (targetValue.equals(VRBindPathParser
                        .get(targetBindPath, row))) {
                    return i;
                }
                i++;
            }
        }
        return -1;

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

}