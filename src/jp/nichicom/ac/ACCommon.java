package jp.nichicom.ac;

import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;

import jp.nichicom.ac.bind.ACBindUtilities;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.sql.ACSQLUtilities;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;

/**
 * AC��Ղ̋��ʊ֐��ł��B
 * <p>
 * �e�@�\���N���X�P�ʂɕ����������p���N���X�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public class ACCommon {
    private static ACCommon singleton;

    /**
     * �C���X�^���X���擾���܂��B
     * 
     * @return �C���X�^���X
     */
    public static ACCommon getInstance() {
        if (singleton == null) {
            singleton = new ACCommon();
        }
        return singleton;
    }

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected ACCommon() {
    }

    /**
     * �`�F�b�N���ڂ�XML�o�͂��܂��B
     * 
     * @param pd �o�̓N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param target �^�O��
     * @param checkValue �`�F�b�N�Ƃ݂Ȃ��l
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public boolean addCheck(ACChotarouXMLWriter pd, VRMap data, String key,
            String target, int checkValue) throws Exception {
        return ACChotarouXMLUtilities.addCheck(pd, data, key, target,
                checkValue);
    }

    /**
     * �w��������Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z����
     * @return ���Z����
     */
    public Date addDay(Date x, int count) {
        return ACDateUtilities.getInstance().addDay(x, count);
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
    public void addFollowCheckNumberInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckNumberInsert(sb, map, checkNumberKey,
                followNumberKeys, addCheckValue);
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
    public void addFollowCheckNumberUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followNumberKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckNumberUpdate(sb, map, checkNumberKey,
                followNumberKeys, addCheckValue);
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
    public void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextInsert(sb, map, checkNumberKey,
                followTextKeys, addCheckValue);
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
    public void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextInsert(sb, map, checkNumberKey,
                followTextKeys, selectedIndex, addCheckValue);
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
    public void addFollowCheckTextInsert(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextInsert(sb, map, checkNumberKey,
                followTextKeys, followNumberKeys, textToNumber, addCheckValue);
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
    public void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextUpdate(sb, map, checkNumberKey,
                followTextKeys, addCheckValue);
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
    public void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys, int selectedIndex,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextUpdate(sb, map, checkNumberKey,
                followTextKeys, selectedIndex, addCheckValue);
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
    public void addFollowCheckTextUpdate(StringBuffer sb, VRMap map,
            String checkNumberKey, String[] followTextKeys,
            String[] followNumberKeys, boolean textToNumber,
            boolean addCheckValue) throws ParseException {
        ACSQLUtilities.addFollowCheckTextUpdate(sb, map, checkNumberKey,
                followTextKeys, followNumberKeys, textToNumber, addCheckValue);
    }

    /**
     * �w�莞�Ԑ����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z���Ԑ�
     * @return ���Z����
     */
    public Date addHourOfDay(Date x, int count) {
        return ACDateUtilities.getInstance().addHour(x, count);
    }

    /**
     * �w�蕪�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z����
     * @return ���Z����
     */
    public Date addMinute(Date x, int count) {
        return ACDateUtilities.getInstance().addMinute(x, count);
    }

    /**
     * �w�茎�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z����
     * @return ���Z����
     */
    public Date addMonth(Date x, int count) {
        return ACDateUtilities.getInstance().addMonth(x, count);
    }

    /**
     * �w��b�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z�b��
     * @return ���Z����
     */
    public Date addSecond(Date x, int count) {
        return ACDateUtilities.getInstance().addSecond(x, count);
    }

    /**
     * �w��N�����Z�������t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param count ���Z�N��
     * @return ���Z����
     */
    public Date addYear(Date x, int count) {
        return ACDateUtilities.getInstance().addYear(x, count);
    }

    /**
     * ���t����P�ʂŔ�r���܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ��r����
     */
    public int compareOnDay(Date x, Date y) {
        return ACDateUtilities.getInstance().compareOnDay(x, y);
    }

    /**
     * ���t�����P�ʂŔ�r���܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ��r����
     */
    public int compareOnMonth(Date x, Date y) {
        return ACDateUtilities.getInstance().compareOnMonth(x, y);
    }

    /**
     * ���t��N�P�ʂŔ�r���܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ��r����
     */
    public int compareOnYear(Date x, Date y) {
        return ACDateUtilities.getInstance().compareOnYear(x, y);
    }

    /**
     * �o�C���h�p�XfromKey�̒l���o�C���h�p�XtoKey�̒l�Ƃ��ē]�L���܂��B
     * 
     * @param array ����Ώ�
     * @param fromKey ���ʌ��̃o���h�p�X
     * @param toKey �]�L��̃o�C���h�p�X
     * @throws Exception ������O
     */
    public void copyBindPath(VRList array, String fromKey, String toKey)
            throws Exception {
        ACBindUtilities.copyBindPath(array, fromKey, toKey);
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
    public void copyBindPath(VRList array, String[] fromKeys, String[] toKeys)
            throws Exception {
        ACBindUtilities.copyBindPath(array, fromKeys, toKeys);
    }

    /**
     * �w��N�̓��t�𐶐����܂��B
     * <p>
     * 1��1���t���Ő�������܂��B
     * </p>
     * 
     * @param year �N
     * @return ���t
     */
    public Date createDate(int year) {
        return ACDateUtilities.getInstance().createDate(year);
    }

    /**
     * �w��N���̓��t�𐶐����܂��B
     * <p>
     * 1���t���Ő�������܂��B
     * </p>
     * 
     * @param year �N
     * @param month ��
     * @return ���t
     */
    public Date createDate(int year, int month) {
        return ACDateUtilities.getInstance().createDate(year, month);
    }

    /**
     * �w��N�����̓��t�𐶐����܂��B
     * <p>
     * Java�W����Month��0��1���ł����A���̈����ł�1��1���Ƃ݂Ȃ��܂��B
     * </p>
     * 
     * @param year �N
     * @param month ��
     * @param day ��
     * @return ���t
     */
    public Date createDate(int year, int month, int day) {
        return ACDateUtilities.getInstance().createDate(year, month, day);
    }

    /**
     * �w�莞���̓��t�𐶐����܂��B
     * <p>
     * �N�����͕ۏ؂���܂���B
     * </p>
     * 
     * @param hour ��
     * @param minute ��
     * @return ���t
     */
    public Date createTime(int hour, int minute) {
        return ACDateUtilities.getInstance().createTime(hour, minute);
    }

    /**
     * �w�莞���̓��t�𐶐����܂��B
     * <p>
     * �N�����͕ۏ؂���܂���B
     * </p>
     * 
     * @param hour ��
     * @param minute ��
     * @param second �b
     * @return ���t
     */
    public Date createTime(int hour, int minute, int second) {
        return ACDateUtilities.getInstance().createTime(hour, minute, second);
    }

    /**
     * �w��t�B�[���h�ƈ�v����C���f�b�N�X�f�[�^���R���{�ɐݒ肵�܂��B
     * <p>
     * �\���f�[�^�����j�[�N�L�[�ł͂Ȃ��A���f�[�^�ƕ\���f�[�^����v���Ȃ��R���{�̃f�[�^���蓖�ĂɎg�p���܂��B
     * </p>
     * 
     * @param array �R���{�Ɋ֘A�t����ꂽ�f�[�^�W��
     * @param field �`�F�b�N�t�B�[���h��
     * @param source ���݂̃f�[�^��
     * @param combo �R���{
     * @throws ParseException ��͗�O
     * @return �Y������f�[�^�������������B
     */
    public boolean followComboIndex(VRList array, String field, VRMap source,
            JComboBox combo) throws ParseException {
        if (array != null) {
            Object nowNo = VRBindPathParser.get(field, source);
            if (!isNullText(nowNo)) {
                int end = array.getDataSize();
                for (int i = 0; i < end; i++) {
                    VRMap row = (VRMap) array.getData(i);
                    Object no = VRBindPathParser.get(field, row);
                    if (nowNo.equals(no)) {
                        if (combo.getSelectedItem() != row) {
                            combo.setSelectedItem(row);
                        }
                        return true;
                    }
                }
            }
        }
        combo.setSelectedItem(null);
        return false;
    }

    /**
     * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒l��VRHashMapArrayToConstKeyArrayAdapter�Ƃ��ĕԂ��܂��B
     * 
     * @param dbm DMManager
     * @param field �Ώۂ̃t�B�[���h��
     * @param table �Ώۂ̃e�[�u����
     * @param codeField WHERE��ɗp����t�B�[���h��
     * @param code WHERE��ɗp����t�B�[���h�l
     * @param orderField �\�[�g��ƂȂ�t�B�[���h��
     * @return VRHashMapArrayToConstKeyArrayAdapter
     * @throws SQLException SQL��O
     */
    public VRHashMapArrayToConstKeyArrayAdapter getArrayAdapter(
            ACDBManager dbm, String field, String table, String codeField,
            int code, String orderField) throws SQLException {
        return getFieldRows(dbm, field, table, codeField, code, orderField);
    }

    /**
     * �������̓��ɂ���Ԃ��܂��B
     * 
     * @param x ���t
     * @return �������̓��ɂ�
     */
    public int getDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().getDayOfMonth(x);
    }

    /**
     * �j����Ԃ��܂��B
     * <p>
     * ���j�F<code>Calendar.SUNDAY</code></br> ���j�F<code>Calendar.MONDAY</code></br>
     * �Ηj�F<code>Calendar.TUESDAY</code></br> ���j�F<code>Calendar.WEDNESDAY</code></br>
     * �ؗj�F<code>Calendar.THURSDAY</code></br> ���j�F<code>Calendar.FRIDAY</code></br>
     * �y�j�F<code>Calendar.SATURDAY</code></br>
     * </p>
     * 
     * @param x ���t
     * @return �j��
     */
    public int getDayOfWeek(Date x) {
        return ACDateUtilities.getInstance().getDayOfWeek(x);
    }

    /**
     * �ȗ������̗j����Ԃ��܂��B
     * <p>
     * "���j��"��"���j��"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @return �ȗ������̗j��
     */
    public String getDayOfWeekFull(Date x) {
        return ACDateUtilities.getInstance().getDayOfWeekFull(x);
    }

    /**
     * �ȗ��\�L�̗j����Ԃ��܂��B
     * <p>
     * "��"��"��"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @return �ȗ��\�L�̗j��
     */
    public String getDayOfWeekShort(Date x) {
        return ACDateUtilities.getInstance().getDayOfWeekShort(x);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ��t������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public String getDBSafeDate(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeDate(key, source);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�Ȑ��l������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public String getDBSafeNumber(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeNumber(key, source);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȕ�����Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public String getDBSafeString(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeString(key, source);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ���������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    public String getDBSafeTime(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getDBSafeTime(key, source);
    }

    /**
     * ���t�̍�����P�ʂŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/13, 2000/10/10) �� 50203<br />
     * (2000/02/11, 2000/02/22) �� -11<br />
     * (2000/02/11, 2010/02/22) �� -100011
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ����
     */
    public int getDifferenceOnDay(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnDay(x, y);
    }

    /**
     * ���t�̍���N���P�ʂŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/23, 2000/10/12) �� 502<br />
     * (2000/02/11, 2000/02/22) �� 0<br />
     * (2000/02/11, 2010/02/22) �� -1000
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ����
     */
    public int getDifferenceOnMonth(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnMonth(x, y);
    }

    /**
     * ���t�̍��������ȑ������ŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/13, 2000/10/10) �� 1890<br />
     * (2000/02/11, 2000/02/22) �� -11<br />
     * (2000/02/11, 2010/02/22) �� -3662
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return ���t��
     */
    public int getDifferenceOnTotalDay(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnTotalDay(x, y);
    }

    /**
     * ���t�̍���N�P�ʂŕԂ��܂��B
     * <p>
     * �Ԃ�l�F<br />
     * ���t1 < ���t2 �� 0��菬�����l<br />
     * ���t1 > ���t2 �� 0���傫���l<br />
     * ���t1 = ���t2 �� 0
     * </p>
     * <p>
     * ex:<br />
     * (2005/12/23, 2000/10/12) �� 5<br />
     * (2000/02/11, 2000/02/22) �� 0<br />
     * (2000/02/11, 2010/02/22) �� -10
     * </p>
     * 
     * @param x ���t1
     * @param y ���t2
     * @return �N��
     */
    public int getDifferenceOnYear(Date x, Date y) {
        return ACDateUtilities.getInstance().getDifferenceOnYear(x, y);
    }

    /**
     * �A���t�@�x�b�g�ȗ��\�L�̌�����Ԃ��܂��B
     * <p>
     * "H"��"S"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �A���t�@�x�b�g�ȗ��\�L�̌���
     */
    public String getEraAlphabet(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraAlphabet(x);
    }

    /**
     * �ȗ������̌�����Ԃ��܂��B
     * <p>
     * "����"��"���a"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �ȗ������̌���
     */
    public String getEraFull(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraFull(x);
    }

    /**
     * �ȗ��\�L�̌�����Ԃ��܂��B
     * <p>
     * "��"��"��"�Ȃǂł��B
     * </p>
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �ȗ��\�L�̌���
     */
    public String getEraShort(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraShort(x);
    }

    /**
     * �a��N��Ԃ��܂��B
     * 
     * @param x ���t
     * @throws Exception ������O
     * @return �a��N
     */
    public int getEraYear(Date x) throws Exception {
        return ACDateUtilities.getInstance().getEraYear(x);
    }

    /**
     * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒l�W����Ԃ��܂��B
     * 
     * @param dbm DMManager
     * @param field �Ώۂ̃t�B�[���h��
     * @param table �Ώۂ̃e�[�u����
     * @param codeField WHERE��ɗp����t�B�[���h��
     * @param code WHERE��ɗp����t�B�[���h�l
     * @param orderField �\�[�g��ƂȂ�t�B�[���h��
     * @throws SQLException SQL��O
     * @return �C�ӂ̗�̒l�W��
     */
    public VRHashMapArrayToConstKeyArrayAdapter getFieldRows(
            ACDBManager dbm, String field, String table, String codeField,
            int code, String orderField) throws SQLException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(field);
        sb.append(" FROM ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(codeField);
        sb.append("=");
        sb.append(code);
        if ((orderField != null) && (!"".equals(orderField))) {
            sb.append(" ORDER BY ");
            sb.append(orderField);
        }

        return new VRHashMapArrayToConstKeyArrayAdapter(dbm.executeQuery(sb
                .toString()), field);
    }

    /**
     * ���̌��̏���(1��)��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �������̓��ɂ�
     */
    public int getFirstDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().getFirstDayOfMonth(x);
    }

    /**
     * ���̌��̖�����Ԃ��܂��B
     * 
     * @param x ���t
     * @return �������̓��ɂ�
     */
    public int getLastDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().getLastDayOfMonth(x);
    }

    /**
     * �}�X�^�f�[�^���ꊇ���Ď擾���܂��B
     * 
     * @param dbm DBManager
     * @param dest ���ʑ����
     * @param nameField �Ώۂ̃t�B�[���h��
     * @param table �Ώۂ̃e�[�u����
     * @param codeField WHERE��ɗp����t�B�[���h��
     * @param orderField ORDER BY��ɗp����t�B�[���h��
     * @throws SQLException SQL��O
     * @throws ParseException ��͗�O
     */
    public void getMasterTable(ACDBManager dbm, HashMap dest,
            String nameField, String table, String codeField, String orderField)
            throws SQLException, ParseException {

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(" *");
        sb.append(" FROM ");
        sb.append(table);
        sb.append(" ORDER BY ");
        sb.append(orderField);

        VRList fullArray = dbm.executeQuery(sb.toString());
        int end = fullArray.size();
        if (end > 0) {
            HashMap arrayMap = new HashMap();
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) fullArray.getData(i);
                Integer code = (Integer) VRBindPathParser.get(codeField, row);
                Object obj = arrayMap.get(code);
                if (obj instanceof VRList) {
                    ((VRList) obj).addData(row);
                } else {
                    VRList array = new VRArrayList();
                    array.addData(row);
                    arrayMap.put(code, array);
                }
            }

            Iterator it = arrayMap.entrySet().iterator();
            while (it.hasNext()) {
                Object obj = it.next();
                dest.put(((Map.Entry) obj).getKey(),
                        new VRHashMapArrayToConstKeyArrayAdapter(
                                (VRList) (((Map.Entry) obj).getValue()),
                                nameField));
            }
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
    public int getMatchIndexFromMap(VRList array, String fieldName, VRMap source)
            throws ParseException {
        return ACBindUtilities.getMatchIndexFromMap(array, fieldName, source);
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
    public int getMatchIndexFromValue(VRList array, String fieldName,
            Object source) throws ParseException {
        return ACBindUtilities.getMatchIndexFromValue(array, fieldName, source);
    }

    // /**
    // * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒lComboBox�ɐݒ肵�܂��B
    // *
    // * @param combo VRComboBox
    // * @param sql String
    // * @param field String
    // */
    // public void setComboModel(JComboBox combo, String sql, String field) {
    // try {
    // NCFrameEventProcessable processer = NCFrame.getInstance()
    // .getFrameEventProcesser();
    // if (processer instanceof NCFrameEventProcessable) {
    // NCDBManagerable dbm = processer.getDBManager();
    // if (dbm instanceof NCDBManagerable) {
    // VRList tbl = (VRList) dbm.executeQuery(sql);
    // if (tbl.size() > 0) {
    // applyComboModel(combo,
    // new VRHashMapArrayToConstKeyArrayAdapter(tbl,
    // field));
    // }
    // }
    // }
    // } catch (Exception e) {
    // e.printStackTrace();
    // }
    // }
    //
    // /**
    // * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒lComboBox�ɐݒ肵�܂��B
    // *
    // * @param combo �Ώۂ̃R���{
    // * @param field �Ώۂ̃t�B�[���h��
    // * @param table �Ώۂ̃e�[�u����
    // * @param codeField WHERE��ɗp����t�B�[���h��
    // * @param code WHERE��ɗp����t�B�[���h�l
    // * @param orderField �\�[�g��ƂȂ�t�B�[���h��
    // * @throws Exception ������O
    // */
    // public void setComboModel(JComboBox combo, String field, String table,
    // String codeField, int code, String orderField) throws Exception {
    // NCFrameEventProcessable processer = NCFrame.getInstance()
    // .getFrameEventProcesser();
    // if (processer instanceof NCFrameEventProcessable) {
    // NCDBManagerable dbm = processer.getDBManager();
    // if (dbm instanceof NCDBManagerable) {
    // setComboModel(dbm, combo, field, table, codeField, code,
    // orderField);
    // }
    // }
    //
    // }
    //
    // /**
    // * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒lComboBox�ɐݒ肵�܂��B
    // *
    // * @param dbm DMManager
    // * @param combo �Ώۂ̃R���{
    // * @param field �Ώۂ̃t�B�[���h��
    // * @param table �Ώۂ̃e�[�u����
    // * @param codeField WHERE��ɗp����t�B�[���h��
    // * @param code WHERE��ɗp����t�B�[���h�l
    // * @param orderField �\�[�g��ƂȂ�t�B�[���h��
    // * @throws SQLException SQL��O
    // */
    // public void setComboModel(NCDBManagerable dbm, JComboBox combo,
    // String field, String table, String codeField, int code,
    // String orderField) throws SQLException {
    // VRHashMapArrayToConstKeyArrayAdapter adapter = geArrayAdapter(dbm,
    // field, table, codeField, code, orderField);
    // if ((adapter != null) && (adapter.getDataSize() > 0)) {
    // applyComboModel(combo, adapter);
    // }
    // }

    /**
     * �w��L�[�t�B�[���h����v����s�}�b�v��Ԃ��܂��B
     * 
     * @param array �f�[�^�W��
     * @param fieldName ��r�t�B�[���h��
     * @param source ��r���\�[�X
     * @throws ParseException ��͗�O
     * @return �w��L�[�t�B�[���h����v����s�}�b�v
     */
    public VRMap getMatchRowFromMap(VRList array, String fieldName, VRMap source)
            throws ParseException {
        return ACBindUtilities.getMatchRowFromMap(array, fieldName, source);
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
    public VRMap getMatchRowFromValue(VRList array, String fieldName,
            Object source) throws ParseException {
        return ACBindUtilities.getMatchRowFromValue(array, fieldName, source);
    }

    /**
     * ����Ԃ��܂��B
     * <p>
     * Java��Date��1����0�ŕ\�����܂����A���̊֐��̖߂�l��1����1�Ƃ��܂��B
     * </p>
     * 
     * @param x ���t
     * @return ��
     */
    public int getMonth(Date x) {
        return ACDateUtilities.getInstance().getMonth(x);
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
    public VRList getParalleledKeyList(VRList src,
            final String[] groupingConditionFieldNames,
            final String keyFieldName, final String valueFieldName,
            final String[] keyFieldValues, final Object valueFieldDefaultValue)
            throws Exception {
        return ACBindUtilities.getParalleledKeyList(src,
                groupingConditionFieldNames, keyFieldName, valueFieldName,
                keyFieldValues, valueFieldDefaultValue);
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
    public VRList getSeriesedKeyList(VRList src,
            final String[] groupingConditionFieldNames,
            final String keyFieldName, final String valueFieldName,
            final String[] keyFieldValues, final Object valueFieldDefaultValue)
            throws Exception {
        return ACBindUtilities.getSeriesedKeyList(src,
                groupingConditionFieldNames, keyFieldName, valueFieldName,
                keyFieldValues, valueFieldDefaultValue);
    }

    /**
     * �N��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �N
     */
    public int getYear(Date x) {
        return ACDateUtilities.getInstance().getYear(x);
    }

    /**
     * �j�Փ��ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �j�Փ��ł��邩
     */
    public boolean isHolyday(Date x) {
        return ACDateUtilities.getInstance().isHolyday(x);
    }

    /**
     * ������Null�܂��͋󕶎��ł��邩��Ԃ��܂��B
     * 
     * @param obj �]��������
     * @return ������Null�܂��͋󕶎��ł��邩
     */
    public boolean isNullText(Object obj) {
        return ACTextUtilities.isNullText(obj);
    }

    /**
     * �y�j���ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return �y�j���ł��邩
     */
    public boolean isSaturday(Date x) {
        return ACDateUtilities.getInstance().isSaturday(x);
    }

    /**
     * ���j���ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return ���j���ł��邩
     */
    public boolean isSunday(Date x) {
        return ACDateUtilities.getInstance().isSunday(x);
    }

    /**
     * ���j�������͏j�Փ��ł��邩��Ԃ��܂��B
     * 
     * @param x ���t
     * @return ���j�������͏j�Փ��ł��邩
     */
    public boolean isSundayOrHolyday(Date x) {
        return ACDateUtilities.getInstance().isSundayOrHolyday(x);
    }

    /**
     * �����P�ʂŁA���s�����Ɛ܂�Ԃ�����������ɐ؂蕪�����z���Ԃ��܂��B
     * 
     * @param text �ϊ��Ώ�
     * @param columns �܂�Ԃ�������
     * @return �؂蕪�����z��
     */
    public String[] lineWrapOnChar(String text, int columns) {

        return ACTextUtilities.separateLineWrapOnChar(text, columns);
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
    public void moveBindPath(VRList array, String fromKey, String toKey)
            throws Exception {
        ACBindUtilities.moveBindPath(array, fromKey, toKey);
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
    public void moveBindPath(VRList array, String[] fromKeys, String[] toKeys)
            throws Exception {
        ACBindUtilities.moveBindPath(array, fromKeys, toKeys);
    }

    /**
     * PDF�t�@�C���𐶐����A��������PDF�t�@�C�����J���܂��B
     * 
     * @param pd ����f�[�^
     * @throws Exception
     */
    public void openPDF(ACChotarouXMLWriter pd) throws Exception {
        ACChotarouXMLUtilities.openPDF(pd);
    }

    /**
     * �w����ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ��
     * @return �ݒ茋��
     */
    public Date setDayOfMonth(Date x, int value) {
        return ACDateUtilities.getInstance().setDayOfMonth(x, value);
    }

    /**
     * �w�莞�Ԃɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ莞��
     * @return �ݒ茋��
     */
    public Date setHourOfDay(Date x, int value) {
        return ACDateUtilities.getInstance().setHourOfDay(x, value);
    }

    /**
     * ���[��`�΍R�ڂ��\���ɂ��܂��B
     * 
     * @param pd �o�̓N���X
     * @param shape Visible����Ώ�
     * @throws Exception ������O
     */
    public void setInvisible(ACChotarouXMLWriter pd, String shape)
            throws Exception {
        ACChotarouXMLUtilities.setInvisible(pd, shape);
    }

    /**
     * DB����擾�����z��(ResultSet)���A[�L�[:key, �l:���R�[�h]�ɂ���HashMap�Ƃ��Đݒ肵�܂��B
     * 
     * @param src DB����擾�����z��(ResultSet)
     * @param dest �ݒ���Map
     * @param key �L�[�ƂȂ�t�B�[���h��
     * @throws Exception ������O
     */
    public void setMapFromArray(VRList src, VRMap dest, String key)
            throws Exception {
        ACBindUtilities.setMapFromArray(src, dest, key);
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
    public void setMapFromArray(VRList src, VRMap dest, String srckey,
            String destKey) throws Exception {
        ACBindUtilities.setMapFromArray(src, dest, srckey, destKey);
    }

    /**
     * �w�蕪�ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ蕪
     * @return �ݒ茋��
     */
    public Date setMinute(Date x, int value) {
        return ACDateUtilities.getInstance().setMinute(x, value);
    }

    /**
     * �w�茎�ɐݒ肵�����t��Ԃ��܂��B
     * <p>
     * Java�W����Month��0��1���ł����A���̈����ł�1��1���Ƃ݂Ȃ��܂��B
     * </p>
     * 
     * @param x ���t
     * @param value �ݒ茎
     * @return �ݒ茋��
     */
    public Date setMonth(Date x, int value) {
        return ACDateUtilities.getInstance().setMonth(x, value);
    }

    /**
     * �w��b�ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ�b
     * @return �ݒ茋��
     */
    public Date setSecond(Date x, int value) {
        return ACDateUtilities.getInstance().setSecond(x, value);
    }

    /**
     * �I���^���ڂ�XML�o�͂��܂��B
     * 
     * @param pd �o�̓N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param shapes Visible����ΏیQ
     * @param offset �l�Ɣz��Y�����Ƃ̃I�t�Z�b�g
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public boolean setSelection(ACChotarouXMLWriter pd, VRMap data, String key,
            String[] shapes, int offset) throws Exception {
        return ACChotarouXMLUtilities.setSelection(pd, data, key, shapes,
                offset);
    }

    /**
     * �I���^���ڂ�XML�o�͂��܂��B
     * 
     * @param pd �o�̓N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param shapes Visible����ΏیQ
     * @param offset �l�Ɣz��Y�����Ƃ̃I�t�Z�b�g
     * @param optionKey �A�����ďo�͂��镶����L�[
     * @param optionTarget �A�����ďo�͂��镶����o�͈ʒu
     * @param useOptionIndex �A�����ďo�͂��镶����̏o�͏����ƂȂ�I��ԍ�
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public boolean setSelection(ACChotarouXMLWriter pd, VRMap data, String key,
            String[] shapes, int offset, String optionKey, String optionTarget,
            int useOptionIndex) throws Exception {
        return ACChotarouXMLUtilities.setSelection(pd, data, key, shapes,
                offset, optionKey, optionTarget, useOptionIndex);
    }

    /**
     * �������ڂ�XML�o�͂��܂��B
     * 
     * @param pd �o�̓N���X
     * @param target �^�O��
     * @param value �o�͒l
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public boolean setValue(ACChotarouXMLWriter pd, String target, Object value)
            throws Exception {
        return ACChotarouXMLUtilities.setValue(pd, target, value);
    }

    /**
     * �������ڂ�XML�o�͂��܂��B
     * 
     * @param pd �o�̓N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param target �^�O��
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public boolean setValue(ACChotarouXMLWriter pd, VRMap data, String key,
            String target) throws Exception {
        return ACChotarouXMLUtilities.setValue(pd, data, key, target);
    }

    /**
     * �������ڂ�XML�o�͂��܂��B
     * 
     * @param pd �o�̓N���X
     * @param data �f�[�^�\�[�X
     * @param key �f�[�^�L�[
     * @param target �^�O��
     * @param head �������ڒl�̑O�ɘA�����ďo�͂��镶����
     * @throws Exception ������O
     * @return �o�͂�����
     */
    public boolean setValue(ACChotarouXMLWriter pd, VRMap data, String key,
            String target, String head) throws Exception {
        return ACChotarouXMLUtilities.setValue(pd, data, key, target, head);
    }

    /**
     * �w��N�ɐݒ肵�����t��Ԃ��܂��B
     * 
     * @param x ���t
     * @param value �ݒ�N
     * @return �ݒ茋��
     */
    public Date setYear(Date x, int value) {
        return ACDateUtilities.getInstance().setYear(x, value);
    }

    /**
     * ��O���b�Z�[�W��\�����܂��B
     * 
     * @param ex ��O
     */
    public void showExceptionMessage(Throwable ex) {
        ACFrame.getInstance().showExceptionMessage(ex);
    }
    
    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public Date toDate(Calendar obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public Date toDate(Date obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * <p>
     * 20041105 �� Date(2004/11/05)
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public Date toDate(int obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public Date toDate(Object obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * Date�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public Date toDate(String obj) throws Exception {
        return ACCastUtilities.toDate(obj);
    }

    /**
     * double�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public double toDouble(Object obj) throws Exception {
        return ACCastUtilities.toDouble(obj);
    }

    /**
     * ���ɂ����������̌���1���ɂ��ĕԂ��܂��B
     * <p>
     * (2005/2/8) �� 2005/2/8<br />
     * (2005/3/1) �� 2005/3/1
     * </p>
     * 
     * @param x ���t
     * @return 1���t���ɕϊ��������t
     */
    public Date toFirstDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().toFirstDayOfMonth(x);
    }

    /**
     * float�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public float toFloat(Object obj) throws Exception {
        return ACCastUtilities.toFloat(obj);
    }

    /**
     * /** Date�^�ɃL���X�g���܂��B
     * <p>
     * Date(2004/11/05) �� 20041105
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public int toInt(Date obj) throws Exception {
        return ACCastUtilities.toInt(obj);
    }

    /**
     * int�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public int toInt(Object obj) throws Exception {
        return ACCastUtilities.toInt(obj);
    }

    /**
     * ���ɂ����������̌��̖����ɂ��ĕԂ��܂��B
     * <p>
     * (2005/2/8) �� 2005/2/28<br />
     * (2005/3/1) �� 2005/3/31
     * </p>
     * 
     * @param x ���t
     * @return 1���t���ɕϊ��������t
     */
    public Date toLastDayOfMonth(Date x) {
        return ACDateUtilities.getInstance().toLastDayOfMonth(x);
    }

    /**
     * long�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public long toLong(Object obj) throws Exception {
        return ACCastUtilities.toLong(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public String toString(double obj) throws Exception {
        return ACCastUtilities.toString(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public String toString(int obj) throws Exception {
        return ACCastUtilities.toString(obj);
    }

    /**
     * String�^�ɃL���X�g���܂��B
     * <p>
     * null�̏ꍇ�͋󕶎�("")��Ԃ��܂��B
     * </p>
     * 
     * @param obj �ϊ���
     * @return �ϊ�����
     * @throws Exception ������O
     */
    public String toString(Object obj) throws Exception {
        return ACCastUtilities.toString(obj);
    }
}
