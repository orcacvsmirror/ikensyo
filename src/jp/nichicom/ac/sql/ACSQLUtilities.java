package jp.nichicom.ac.sql;

import java.text.ParseException;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRMap;

/**
 * SQL�֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public class ACSQLUtilities {
    private static ACSQLUtilities singleton;

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @deprecated ����static���\�b�h���Ă�ł��������B
     * @return �C���X�^���X
     */
    public static ACSQLUtilities getInstance() {
        if (singleton == null) {
            singleton = new ACSQLUtilities();
        }
        return singleton;
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACSQLUtilities() {

    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followNumberKeys ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckNumberInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followNumberKeys.length;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(getDBSafeNumber(followNumberKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",0");
            }
        }
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followNumberKeys ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckNumberUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(checkNumberKey);
            sb.append(" = ");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followNumberKeys.length;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followNumberKeys[i]);
                sb.append(" = ");
                sb.append(getDBSafeNumber(followNumberKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followNumberKeys[i]);
                sb.append(" = 0");
            }
        }
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followTextKeys ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        addFollowCheckTextInsert(sb, map, checkNumberKey, followTextKeys, 1,
                addCheckValue);
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followTextKeys ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param selectedIndex �I�������Ƃ݂Ȃ��`�F�b�N���ڒl
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followTextKeys.length;
        if ((obj instanceof Integer)
                && (((Integer) obj).intValue() == selectedIndex)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(getDBSafeString(followTextKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",''");
            }
        }
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followTextKeys ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param followNumberKeys ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param textToNumber �e�L�X�g�o�͂��悩
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            if (textToNumber) {
                // �����o�͂���
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
            } else {
                // ���l�o�͂���
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
            }
        } else {
            if (textToNumber) {
                // �����o�͂���
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",''");
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",0");
                }
            } else {
                // ���l�o�͂���
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",0");
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",''");
                }
            }
        }
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followTextKeys ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        addFollowCheckTextUpdate(sb, map, checkNumberKey, followTextKeys, 1,
                addCheckValue);
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followTextKeys ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param selectedIndex �I�������Ƃ݂Ȃ��`�F�b�N���ڒl
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(checkNumberKey);
            sb.append(" = ");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end = followTextKeys.length;
        if ((obj instanceof Integer)
                && (((Integer) obj).intValue() == selectedIndex)) {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followTextKeys[i]);
                sb.append(" = ");
                sb.append(getDBSafeString(followTextKeys[i], map));
            }
        } else {
            for (int i = 0; i < end; i++) {
                sb.append(",");
                sb.append(followTextKeys[i]);
                sb.append(" = ");
                sb.append("''");
            }
        }
    }

    /**
     * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
     * 
     * @param sb �ǉ���o�b�t�@
     * @param map �l�擾��
     * @param checkNumberKey ���l�^�`�F�b�N���ڃL�[
     * @param followTextKeys ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param followNumberKeys ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
     * @param textToNumber �e�L�X�g�o�͂��悩
     * @param addCheckValue �`�F�b�N���ڂ��o�͂��邩
     * @throws ParseException ��͗�O
     */
    public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {

        Object obj = VRBindPathParser.get(checkNumberKey, map);
        if (addCheckValue) {
            sb.append(",");
            sb.append(checkNumberKey);
            sb.append(" = ");
            sb.append(getDBSafeNumber(checkNumberKey, map));
        }

        int end;
        if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
            if (textToNumber) {
                // �����o�͂���
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
            } else {
                // ���l�o�͂���
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeNumber(followNumberKeys[i], map));
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append(getDBSafeString(followTextKeys[i], map));
                }
            }
        } else {
            if (textToNumber) {
                // �����o�͂���
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append("''");
                }
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = 0");
                }
            } else {
                // ���l�o�͂���
                end = followNumberKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followNumberKeys[i]);
                    sb.append(" = 0");
                }
                end = followTextKeys.length;
                for (int i = 0; i < end; i++) {
                    sb.append(",");
                    sb.append(followTextKeys[i]);
                    sb.append(" = ");
                    sb.append("''");
                }
            }
        }
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ��t������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public static String getDBSafeDate(String key, VRBindSource source)
            throws ParseException {
        return ACConstants.FORMAT_SQL_FULL_YMD.format(VRBindPathParser.get(
                key, source));
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�Ȑ��l������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public static String getDBSafeNumber(String key, VRBindSource source)
            throws ParseException {
        Object obj = VRBindPathParser.get(key, source);
        if (obj == null) {
            return "NULL";
        }
        return String.valueOf(obj);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȕ�����Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public static String getDBSafeString(String key, VRBindSource source)
            throws ParseException {
        return ACConstants.FORMAT_SQL_STRING.format(VRBindPathParser.get(
                key, source));
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ���������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public static String getDBSafeTime(String key, VRBindSource source)
            throws ParseException {
        return ACConstants.FORMAT_SQL_FULL_YMD_HMS.format(VRBindPathParser
                .get(key, source));
    }
}
