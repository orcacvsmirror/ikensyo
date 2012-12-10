package jp.nichicom.ac.bind;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Iterator;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * �o�C���h�֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRBindable
 * @see VRBindSource
 */
public class ACBindUtilities {
    private static ACBindUtilities singleton;

    /**
     * �C���X�^���X���擾���܂��B
     * @deprecated ����static���\�b�h���Ă�ł��������B
     * @return �C���X�^���X
     */
    public static ACBindUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACBindUtilities();
        }
        return singleton;
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACBindUtilities() {
    }

    /**
     * �o�C���h�p�XfromKey�̒l���o�C���h�p�XtoKey�̒l�Ƃ��ē]�L���܂��B
     * 
     * @param array ����Ώ�
     * @param fromKey ���ʌ��̃o���h�p�X
     * @param toKey �]�L��̃o�C���h�p�X
     * @throws Exception ������O
     */
    public static void copyBindPath(VRList array, String fromKey, String toKey)
            throws Exception {
        Iterator it = array.iterator();
        while (it.hasNext()) {
            VRMap row = (VRMap) it.next();
            VRBindPathParser
                    .set(toKey, row, VRBindPathParser.get(fromKey, row));
        }
    }

    /**
     * �o�C���h�p�XfromKey�̒l���o�C���h�p�XtoKey�̒l�Ƃ��ē]�L���܂��B
     * <p>
     * �ړ���Ƀo�C���h�p�XfromKey�̃L�[����ђl�͏�������܂��B
     * </p>
     * 
     * @param array ����Ώ�
     * @param fromKeys �ړ����̃o���h�p�X�z��
     * @param toKeys �ړ���̃o�C���h�p�X�z��
     * @throws Exception ������O
     */
    public static void copyBindPath(VRList array, String[] fromKeys, String[] toKeys)
            throws Exception {
        int end = fromKeys.length;
        for (int i = 0; i < end; i++) {
            copyBindPath(array, fromKeys[i], toKeys[i]);
        }
    }

    /**
     * �w��L�[�t�B�[���h����v����s�}�b�v��Ԃ��܂��B
     * 
     * @param array �f�[�^�W��
     * @param fieldName ��r�t�B�[���h��
     * @param source ��r���\�[�X
     * @throws ParseException ��͗�O
     * @return �w��L�[�t�B�[���h����v����s�}�b�v
     */
    public static VRMap getMatchRowFromMap(VRList array, String fieldName, VRMap source)
            throws ParseException {
        if (VRBindPathParser.has(fieldName, source)) {
            return getMatchRowFromValue(array, fieldName, VRBindPathParser.get(
                    fieldName, source));
        }
        return null;
    }

    /**
     * �w��L�[�t�B�[���h����v����s�}�b�v��Ԃ��܂��B
     * 
     * @param array �f�[�^�W��
     * @param fieldName ��r�t�B�[���h��
     * @param source ��r���\�[�X
     * @throws ParseException ��͗�O
     * @return �w��L�[�t�B�[���h����v����s�}�b�v
     */
    public static VRMap getMatchRowFromValue(VRList array, String fieldName,
            Object source) throws ParseException {
        int index = getMatchIndexFromValue(array, fieldName, source);
        if (index < 0) {
            return null;
        } else {
            return (VRMap) array.getData(index);
        }
    }

    /**
     * �w��L�[�t�B�[���h����v����s�ԍ���Ԃ��܂��B
     * 
     * @param array �f�[�^�W��
     * @param fieldName ��r�t�B�[���h��
     * @param source ��r���\�[�X
     * @throws ParseException ��͗�O
     * @return �w��L�[�t�B�[���h����v����s�ԍ�
     */
    public static int getMatchIndexFromMap(VRList array, String fieldName, VRMap source)
            throws ParseException {
        if (VRBindPathParser.has(fieldName, source)) {
            return getMatchIndexFromValue(array, fieldName, VRBindPathParser
                    .get(fieldName, source));
        }
        return -1;
    }

    /**
     * �w��L�[�t�B�[���h����v����s�ԍ���Ԃ��܂��B
     * 
     * @param array �f�[�^�W��
     * @param fieldName ��r�t�B�[���h��
     * @param source ��r���\�[�X
     * @throws ParseException ��͗�O
     * @return �w��L�[�t�B�[���h����v����s�ԍ�
     */
    public static int getMatchIndexFromValue(VRList array, String fieldName,
            Object source) throws ParseException {
        int end = array.size();

        if (source == null) {
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) array.getData(i);
                if (VRBindPathParser.get(fieldName, row) == null) {
                    return i;
                }
            }

        } else {
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) array.getData(i);
                if (source.equals(VRBindPathParser.get(fieldName, row))) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * �L�[�ƒl�̊֌W�ɂ���2�̗������Map���W�߂ĕ��񉻂��ꂽList�ɑ΂��A�L�[��̒l���L�[�ɒl��̒l��l�Ƃ��Ē��񉻂���Map�̏W����Ԃ��܂��B
     * 
     * @param src �ϊ���
     * @param groupingConditionFieldNames �O���[�v��������
     * @param keyFieldName �L�[�ɂ�����f�[�^���i�[�����
     * @param valueFieldName �l�ɂ�����f�[�^���i�[�����
     * @param keyFieldValues �L�[��̒l�Ƃ��đz�肳���A���񉻑Ώۂ̗�
     * @param valueFieldDefaultValue ���񉻑Ώۂ̗�ɓK�p���鏉���l
     * @throws Exception ������O
     * @return �ϊ�����
     */
    public static VRList getSeriesedKeyList(VRList src,
            final String[] groupingConditionFieldNames,
            final String keyFieldName, final String valueFieldName,
            final String[] keyFieldValues, final Object valueFieldDefaultValue)
            throws Exception {
        VRArrayList result = new VRArrayList();

        HashMap cache = new HashMap();
        int whereFieldsLength = groupingConditionFieldNames.length;
        int keyFieldValuesLength = keyFieldValues.length;
        Iterator it = src.iterator();
        while (it.hasNext()) {
            VRMap row = (VRMap) it.next();

            VRMap adder;
            // �O���[�v�������t�B�[���h�̒l�𒊏o����
            Object[] where = new Object[whereFieldsLength];
            for (int i = 0; i < whereFieldsLength; i++) {
                where[i] = VRBindPathParser.get(groupingConditionFieldNames[i],
                        row);
            }
            // �O���[�v�������t�B�[���h�Ŋ��o���`�F�b�N
            Object map = cache.get(where);
            if (map == null) {
                // �V�K�ł���ΐV���ȕԋp��𐶐�����
                adder = new VRHashMap();
                result.add(adder);
                cache.put(where, adder);

                // �����l�ŏ�����
                for (int i = 0; i < keyFieldValuesLength; i++) {
                    VRBindPathParser.set(keyFieldValues[i], adder,
                            valueFieldDefaultValue);
                }
            } else {
                // �����ł���΃L���b�V���Ŕ����������̂��g�p����
                adder = (VRMap) map;
            }

            // ����
            VRBindPathParser.set(String.valueOf(VRBindPathParser.get(
                    keyFieldName, row)), adder, VRBindPathParser.get(
                    valueFieldName, row));
        }
        return result;
    }

    /**
     * �L�[�ƒl�̊֌W�Œ��񉻂��ꂽ�������Map����Ȃ�List�ɑ΂��A�L�[��ƒl��ɕ��񉻂���Map�̏W����Ԃ��܂��B
     * 
     * @param src �ϊ���
     * @param groupingConditionFieldNames �O���[�v��������
     * @param keyFieldName �L�[�ɂ�����f�[�^���i�[�����
     * @param valueFieldName �l�ɂ�����f�[�^���i�[�����
     * @param keyFieldValues �L�[��̒l�Ƃ��đz�肳���A���񉻑Ώۂ̗�
     * @param valueFieldDefaultValue ���񉻑Ώۂ̗�ɓK�p���鏉���l
     * @throws Exception ������O
     * @return �ϊ�����
     */
    public static VRList getParalleledKeyList(VRList src,
            final String[] groupingConditionFieldNames,
            final String keyFieldName, final String valueFieldName,
            final String[] keyFieldValues, final Object valueFieldDefaultValue)
            throws Exception {
        VRArrayList result = new VRArrayList();

        int whereFieldsLength = groupingConditionFieldNames.length;
        int keyFieldValuesLength = keyFieldValues.length;
        Iterator it = src.iterator();
        while (it.hasNext()) {
            VRMap row = (VRMap) it.next();
            for (int i = 0; i < keyFieldValuesLength; i++) {
                Object valColValue = VRBindPathParser.get(keyFieldValues[i],
                        row);
                if (valueFieldDefaultValue == null) {
                    if (valColValue == null) {
                        // �f�t�H���g�l�Ɠ����ꍇ�͐V���ȗ���쐬���Ȃ�
                        continue;
                    }
                } else if (valueFieldDefaultValue.equals(valColValue)) {
                    // �f�t�H���g�l�Ɠ����ꍇ�͐V���ȗ���쐬���Ȃ�
                    continue;
                }

                // �V���ȕ��񉻗�𐶐�����
                VRMap adder = new VRHashMap();
                for (int j = 0; j < whereFieldsLength; j++) {
                    // �L�[��ȊO��]��
                    VRBindPathParser.set(groupingConditionFieldNames[j], adder,
                            VRBindPathParser.get(
                                    groupingConditionFieldNames[j], row));
                }
                VRBindPathParser.set(keyFieldName, adder, keyFieldValues[i]);
                VRBindPathParser.set(valueFieldName, adder, valColValue);

                result.add(adder);

            }
        }
        return result;
    }

    /**
     * �o�C���h�p�XfromKey�̒l���o�C���h�p�XtoKey�̒l�Ƃ��Ĉړ����܂��B
     * <p>
     * �ړ���Ƀo�C���h�p�XfromKey�̃L�[����ђl�͏�������܂��B
     * </p>
     * 
     * @param array ����Ώ�
     * @param fromKey �ړ����̃o���h�p�X
     * @param toKey �ړ���̃o�C���h�p�X
     * @throws Exception ������O
     */
    public static void moveBindPath(VRList array, String fromKey, String toKey)
            throws Exception {
        Iterator it = array.iterator();
        while (it.hasNext()) {
            VRMap row = (VRMap) it.next();
            VRBindPathParser
                    .set(toKey, row, VRBindPathParser.get(fromKey, row));
            row.removeData(fromKey);
        }
    }

    /**
     * �o�C���h�p�XfromKey�̒l���o�C���h�p�XtoKey�̒l�Ƃ��Ĉړ����܂��B
     * <p>
     * �ړ���Ƀo�C���h�p�XfromKey�̃L�[����ђl�͏�������܂��B
     * </p>
     * 
     * @param array ����Ώ�
     * @param fromKeys �ړ����̃o���h�p�X�z��
     * @param toKeys �ړ���̃o�C���h�p�X�z��
     * @throws Exception ������O
     */
    public static void moveBindPath(VRList array, String[] fromKeys, String[] toKeys)
            throws Exception {
        int end = fromKeys.length;
        for (int i = 0; i < end; i++) {
            moveBindPath(array, fromKeys[i], toKeys[i]);
        }
    }

    /**
     * DB����擾�����z��(ResultSet)���A[�L�[:key, �l:���R�[�h]�ɂ���HashMap�Ƃ��Đݒ肵�܂��B
     * 
     * @param src DB����擾�����z��(ResultSet)
     * @param dest �ݒ���Map
     * @param key �L�[�ƂȂ�t�B�[���h��
     * @throws Exception ������O
     */
    public static void setMapFromArray(VRList src, VRMap dest, String key)
            throws Exception {
        Iterator it = src.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof VRMap) {
                dest.put(VRBindPathParser.get(key, (VRMap) obj), obj);
            }
        }
    }

    /**
     * DB����擾�����z��(ResultSet)���A[�L�[:srcKey,
     * �l:���R�[�h��destKey�t�B�[���h�l]�ɂ���HashMap�Ƃ��Đݒ肵�܂��B
     * 
     * @param src DB����擾�����z��(ResultSet)
     * @param dest �ݒ���Map
     * @param srckey �L�[�ƂȂ�t�B�[���h��
     * @param destKey �����o���t�B�[���h��
     * @throws Exception ������O
     */
    public static void setMapFromArray(VRList src, VRMap dest, String srckey,
            String destKey) throws Exception {
        Iterator it = src.iterator();
        while (it.hasNext()) {
            Object obj = it.next();
            if (obj instanceof VRMap) {
                dest.put(VRBindPathParser.get(srckey, (VRMap) obj),
                        VRBindPathParser.get(destKey, (VRMap) obj));
            }
        }
    }

}
