package jp.nichicom.ac.sql;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 * ��ʃf�[�^��ΏۂƂ���e�[�u���������������ĊǗ�����N���X�ł��B
 * <p>
 * �N��N�x�P�ʂŃe�[�u���𕪊���������ۂɎg�p���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACSeparateTablar
 */
public class ACSeparateTableManager {
    /**
     * �N���P�ʂ̊Ǘ���\�����t�`�F�b�N���[�h�萔�ł��B
     */
    public static final int CHECK_DATE_SPAN_MONTH = 1;

    /**
     * �N�P�ʂ̊Ǘ���\�����t�`�F�b�N���[�h�萔�ł��B
     */
    public static final int CHECK_DATE_SPAN_YEAR = 0;

    private int checkDateSpan = ACSeparateTableManager.CHECK_DATE_SPAN_YEAR;
    private boolean fiscalYear;
    private String managedTableNameFieldName = "TABLE_NAME";
    private HashMap managedTables;
    private String manageTableName;
    private String maximumModifierFieldName = "MAXIMUM_YEAR";
    private String minimumModifierFieldName = "MINIMUM_YEAR";
    private String timeStampFieldName = "LAST_TIME";
    private boolean useManagementTable;
    private boolean useTimestampField;

    /**
     * �����Ώۂ̃e�[�u���W����ǉ����܂��B
     * 
     * @param tableID �����̃e�[�u���W����\��ID
     * @param tables �����Ώۂ̃e�[�u���W��
     */
    public void addSeparateTable(String tableID, List tables) {
        HashMap map = getManagedTables();
        if (map == null) {
            map = new HashMap();
            setManagedTables(map);
        }
        map.put(tableID, tables);
    }

    /**
     * �����Ώۂ̃e�[�u���W����ǉ����܂��B
     * 
     * @param tableID �����̃e�[�u���W����\��ID
     * @param tables �����Ώۂ̃e�[�u���W��
     */
    public void addSeparateTable(String tableID, ACSeparateTablar[] tables) {
        addSeparateTable(tableID, java.util.Arrays.asList(tables));
    }

    /**
     * ���t�`�F�b�N���[�h ��Ԃ��܂��B
     * <p>
     * <code>
     * CHECK_DATE_SPAN_YEAR : �N�P��
     * CHECK_DATE_SPAN_MONTH : ���P��
     * </code>
     * </p>
     * 
     * @return ���t�`�F�b�N���[�h
     */
    public int getCheckDateSpan() {
        return checkDateSpan;
    }

    /**
     * �Ǘ��Ώۃe�[�u�����t�B�[���h�� ��Ԃ��܂��B
     * 
     * @return �Ǘ��Ώۃe�[�u�����t�B�[���h��
     */
    public String getManagedTableNameFieldName() {
        return managedTableNameFieldName;
    }

    /**
     * �Ǘ��Ώۂ̃e�[�u���W�� ��Ԃ��܂��B
     * 
     * @return �Ǘ��Ώۂ̃e�[�u���W��
     */
    public HashMap getManagedTables() {
        return managedTables;
    }

    /**
     * �Ǘ��e�[�u���� ��Ԃ��܂��B
     * 
     * @return �Ǘ��e�[�u����
     */
    public String getManageTableName() {
        return manageTableName;
    }

    /**
     * �o�^�ς݂̍ł��傫�ȏC����t�B�[���h�� ��Ԃ��܂��B
     * 
     * @return �o�^�ς݂̍ł��傫�ȏC����t�B�[���h��
     */
    public String getMaximumModifierFieldName() {
        return maximumModifierFieldName;
    }

    /**
     * �o�^�ς݂̍ł������ȏC����t�B�[���h�� ��Ԃ��܂��B
     * 
     * @return �o�^�ς݂̍ł������ȏC����t�B�[���h��
     */
    public String getMinimumModifierFieldName() {
        return minimumModifierFieldName;
    }

    /**
     * �����Ώۂ̃e�[�u���W����Ԃ��܂��B
     * 
     * @param tableID �e�[�u���W����\��ID
     * @return �����Ώۂ̃e�[�u���W��
     */
    public ArrayList getSeparateTable(String tableID) {
        HashMap map = getManagedTables();
        if (map == null) {
            return null;
        }
        return (ArrayList) map.get(tableID);

    }

    /**
     * ���t����ɃA�N�Z�X���ׂ������e�[�u���̏C�����Ԃ��܂��B
     * <p>
     * �����e�[�u���Ǘ��e�[�u���̒l���m�F���A�����Ώۂ̃e�[�u�����쐬����Ă��Ȃ��ꍇ�͍쐬���܂��B�Ǘ��e�[�u���̍X�V���s�Ȃ��܂��̂ŁA�K�v�ł���ΌĂяo���O��Ƀg�����U�N�V�����̊J�n�ƃR�~�b�g�����s���Ă��������B
     * </p>
     * <p>
     * ���s�����ꍇ�͋󕶎����Ԃ�܂��B
     * </p>
     * 
     * @param dbm DBManager
     * @param tableID �����e�[�u���̃L�[
     * @param targetDate �Ώ۔N����
     * @return �T�t�B�b�N�X�B"_"+�N�x
     * @throws Exception ������O
     */
    public int getTableModifyFromDate(ACDBManager dbm, String tableID,
            Calendar targetDate) throws Exception {

        if (isFiscalYear()) {
            // 3���Z���邱�ƂŁA2005/4��200/1�Ƃ݂Ȃ��A�N�x���擾�ł���
            targetDate.add(Calendar.MONTH, -3);
        }
        int date;

        switch (getCheckDateSpan()) {
        case CHECK_DATE_SPAN_YEAR:
            date = targetDate.get(Calendar.YEAR);
            break;
        case CHECK_DATE_SPAN_MONTH:
            date = targetDate.get(Calendar.YEAR) * 10
                    + (targetDate.get(Calendar.MONTH) + 1);
            break;
        default:
            throw new IllegalStateException("�s���ȓ��t�`�F�b�N���[�h�ł��B");
        }

        if (!updateManagementTable(dbm, tableID, date)) {
            return -1;
        }

        if (!checkSeparateTable(dbm, tableID, date)) {
            return -1;
        }

        return date;
    }

    /**
     * ���t����ɃA�N�Z�X���ׂ������e�[�u���̏C�����Ԃ��܂��B
     * <p>
     * �����e�[�u���Ǘ��e�[�u���̒l���m�F���A�����Ώۂ̃e�[�u�����쐬����Ă��Ȃ��ꍇ�͍쐬���܂��B�Ǘ��e�[�u���̍X�V���s�Ȃ��܂��̂ŁA�K�v�ł���ΌĂяo���O��Ƀg�����U�N�V�����̊J�n�ƃR�~�b�g�����s���Ă��������B
     * </p>
     * <p>
     * ���s�����ꍇ�͋󕶎����Ԃ�܂��B
     * </p>
     * 
     * @param dbm DBManager
     * @param tableID �����e�[�u���̃L�[
     * @param targetDate �Ώ۔N����
     * @return �T�t�B�b�N�X�B"_"+�N�x
     * @throws Exception ������O
     */
    public int getTableModifyFromDate(ACDBManager dbm, String tableID,
            Date targetDate) throws Exception {
        Calendar cal = Calendar.getInstance();
        cal.setTime(targetDate);
        return getTableModifyFromDate(dbm, tableID, cal);
    }

    /**
     * �X�V�������Ǘ�����t�B�[���h�� ��Ԃ��܂��B
     * 
     * @return �X�V�������Ǘ�����t�B�[���h��
     */
    public String getTimeStampFieldName() {
        return timeStampFieldName;
    }

    /**
     * �N�ł͂Ȃ��N�x�Ŕ��f���邩 ��Ԃ��܂��B
     * 
     * @return �N�ł͂Ȃ��N�x�Ŕ��f���邩
     */
    public boolean isFiscalYear() {
        return fiscalYear;
    }

    /**
     * �Ǘ��e�[�u�����g�p���邩 ��Ԃ��܂��B
     * 
     * @return �Ǘ��e�[�u�����g�p���邩
     */
    public boolean isUseManagementTable() {
        return useManagementTable;
    }

    /**
     * �X�V�������Ǘ����邩 ��Ԃ��܂��B
     * 
     * @return �X�V�������Ǘ����邩��
     */
    public boolean isUseTimestampField() {
        return useTimestampField;
    }

    /**
     * ���t�`�F�b�N���[�h ��ݒ肵�܂��B
     * <p>
     * <code>
     * CHECK_DATE_SPAN_YEAR : �N�P��
     * CHECK_DATE_SPAN_MONTH : ���P��
     * </code>
     * </p>
     * 
     * @param checkDateSpan ���t�`�F�b�N���[�h
     */
    public void setCheckDateSpan(int checkDateSpan) {
        this.checkDateSpan = checkDateSpan;
    }

    /**
     * �N�ł͂Ȃ��N�x�Ŕ��f���邩 ��ݒ肵�܂��B
     * 
     * @param fiscalYear �N�ł͂Ȃ��N�x�Ŕ��f���邩
     */
    public void setFiscalYear(boolean fiscalYear) {
        this.fiscalYear = fiscalYear;
    }

    /**
     * �Ǘ��Ώۃe�[�u�����t�B�[���h�� ��ݒ肵�܂��B
     * 
     * @param managedTableNameFieldName �Ǘ��Ώۃe�[�u�����t�B�[���h��
     */
    public void setManagedTableNameFieldName(String managedTableNameFieldName) {
        this.managedTableNameFieldName = managedTableNameFieldName;
    }

    /**
     * �Ǘ��Ώۂ̃e�[�u���W�� ��ݒ肵�܂��B
     * 
     * @param managedTables �Ǘ��Ώۂ̃e�[�u���W��
     */
    public void setManagedTables(HashMap managedTables) {
        this.managedTables = managedTables;
    }

    /**
     * �Ǘ��e�[�u���� ��ݒ肵�܂��B
     * 
     * @param manageTableName �Ǘ��e�[�u����
     */
    public void setManageTableName(String manageTableName) {
        this.manageTableName = manageTableName;
    }

    /**
     * �o�^�ς݂̍ł��傫�ȏC����t�B�[���h�� ��ݒ肵�܂��B
     * 
     * @param maximumModifierFieldName �o�^�ς݂̍ł��傫�ȏC����t�B�[���h��
     */
    public void setMaximumModifierFieldName(String maximumModifierFieldName) {
        this.maximumModifierFieldName = maximumModifierFieldName;
    }

    /**
     * �o�^�ς݂̍ł������ȏC����t�B�[���h�� ��ݒ肵�܂��B
     * 
     * @param minimumModifierFieldName �o�^�ς݂̍ł������ȏC����t�B�[���h��
     */
    public void setMinimumModifierFieldName(String minimumModifierFieldName) {
        this.minimumModifierFieldName = minimumModifierFieldName;
    }

    /**
     * �X�V�������Ǘ�����t�B�[���h�� ��ݒ肵�܂��B
     * 
     * @param timeStampFieldName �X�V�������Ǘ�����t�B�[���h��
     */
    public void setTimeStampFieldName(String timeStampFieldName) {
        this.timeStampFieldName = timeStampFieldName;
    }

    /**
     * �Ǘ��e�[�u�����g�p���邩 ��ݒ肵�܂��B
     * 
     * @param useManagementTable �Ǘ��e�[�u�����g�p���邩
     */
    public void setUseManagementTable(boolean useManagementTable) {
        this.useManagementTable = useManagementTable;
    }

    /**
     * �X�V�������Ǘ����邩 ��ݒ肵�܂��B
     * 
     * @param useTimestampField �X�V�������Ǘ����邩
     */
    public void setUseTimestampField(boolean useTimestampField) {
        this.useTimestampField = useTimestampField;
    }

    /**
     * �e�[�u���𕪊����ׂ����`�F�b�N���܂��B
     * 
     * @param dbm DBManager
     * @param tableID �����e�[�u���̃L�[
     * @param newValue �Ǘ��l
     * @return �����ɐ���������
     * @throws SQLException ������O
     */
    protected boolean checkSeparateTable(ACDBManager dbm, String tableID,
            int newValue) throws SQLException {
        Object obj = getManagedTables().get(tableID);
        if (!(obj instanceof List)) {
            throw new IllegalStateException("�e�[�u��ID�ɑΉ�����e�[�u���W����������܂���B");
        }
        List detailTables = (List) obj;

        // �Ώۃe�[�u���̑��݂��m�F����
        int end = detailTables.size();
        for (int i = 0; i < end; i++) {
            obj = detailTables.get(i);
            if (!(obj instanceof ACSeparateTablar)) {
                return false;
            }
            ACSeparateTablar table = (ACSeparateTablar) obj;
            if(!table.isExistTable(dbm, newValue)){
                // �e�[�u�������݂��Ȃ�
                table.createTable(dbm, newValue);
            }
        }
        return true;
    }

    /**
     * �Ǘ��e�[�u�����X�V���܂��B
     * 
     * @param dbm DBManager
     * @param tableID �����e�[�u���̃L�[
     * @param newValue �Ǘ��l
     * @return ����������
     * @throws Exception
     */
    protected boolean updateManagementTable(ACDBManager dbm,
            String tableID, int newValue) throws Exception {
        if (isUseManagementTable()) {
            // �Ǘ��e�[�u�����g�p����ꍇ
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT ");
            sb.append(getManageTableName() + "."
                    + getManagedTableNameFieldName());
            sb.append("," + getManageTableName() + "."
                    + getMinimumModifierFieldName());
            sb.append("," + getManageTableName() + "."
                    + getMaximumModifierFieldName());
            sb.append(" FROM ");
            sb.append(getManageTableName());
            sb.append(" WHERE");
            sb.append(" (" + getManageTableName() + "."
                    + getManagedTableNameFieldName() + " = '" + tableID + "')");
            VRList ret = dbm.executeQuery(sb.toString());
            if (ret.getDataSize() <= 0) {
                // �Y�����郌�R�[�h���Ȃ�
                sb = new StringBuffer();
                sb.append("INSERT INTO ");
                sb.append(getManageTableName() + "( ");
                sb.append(getManagedTableNameFieldName());
                sb.append("," + getMinimumModifierFieldName());
                sb.append("," + getMaximumModifierFieldName());
                if (isUseTimestampField()) {
                    sb.append("," + getTimeStampFieldName());
                }
                sb.append(" )VALUES( ");
                sb.append("'" + tableID + "'");
                sb.append("," + newValue);
                sb.append("," + newValue);
                if (isUseTimestampField()) {
                    sb.append(",CURRENT_TIMESTAMP");
                }
                sb.append(")");
                if (dbm.executeUpdate(sb.toString()) <= 0) {
                    // INSERT���s
                    return false;
                }
            } else {
                VRMap row = (VRMap) ret.getData();
                int min = Integer.parseInt(String.valueOf(VRBindPathParser.get(
                        getMinimumModifierFieldName(), row)));
                int max = Integer.parseInt(String.valueOf(VRBindPathParser.get(
                        getMaximumModifierFieldName(), row)));
                if (newValue < min) {
                    // �쐬�ςݍŏ��N�x��菬����
                    sb = new StringBuffer();
                    sb.append("UPDATE ");
                    sb.append(getManageTableName());
                    sb.append(" SET ");
                    sb.append(getMinimumModifierFieldName() + " = " + newValue);
                    if (isUseTimestampField()) {
                        sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                    }
                    sb.append(" WHERE");
                    sb.append(" (TABLE_NAME = '" + tableID + "')");
                    if (dbm.executeUpdate(sb.toString()) <= 0) {
                        return false;
                    }
                } else if (newValue > max) {
                    // �쐬�ςݍő�N�x���傫��
                    sb = new StringBuffer();
                    sb.append("UPDATE ");
                    sb.append(getManageTableName());
                    sb.append(" SET ");
                    sb.append(getMaximumModifierFieldName() + " = " + newValue);
                    if (isUseTimestampField()) {
                        sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                    }
                    sb.append(" WHERE");
                    sb.append(" (TABLE_NAME = '" + tableID + "')");
                    if (dbm.executeUpdate(sb.toString()) <= 0) {
                        return false;
                    }
                }
            }

        }
        return true;
    }
}