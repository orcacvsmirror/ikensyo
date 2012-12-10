package jp.nichicom.ac.sql;

import java.sql.SQLException;

import jp.nichicom.ac.ACCommon;

/**
 * ������������e�[�u����`�ł��B
 * <p>
 * �N��N�x�P�ʂŃe�[�u���𕪊���������ۂɎg�p���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACSeparateTableManager
 * @see ACSeparateTablar
 */
public class ACSeparateTable extends AbstractACSeparateTable {
    private String createSQLRowsStatement;
    private String existCheckSQLRowsStatement;
    private String existCheckSQLWhereStatement;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACSeparateTable() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param baseTableName �C������܂܂Ȃ��e�[�u����
     * @param existCheckSQLRowsStatement ���݊m�F�Ɏg�p����SQL���̗��`
     * @param existCheckSQLWhereStatement ���݊m�F�Ɏg�p����SQL����Where���`
     * @param createSQLRowsStatement �e�[�u�����쐬����SQL���̗��`
     */
    public ACSeparateTable(String baseTableName,
            String existCheckSQLRowsStatement,
            String existCheckSQLWhereStatement, String createSQLRowsStatement) {
        super(baseTableName);
        setExistCheckSQLRowsStatement(existCheckSQLRowsStatement);
        setExistCheckSQLWhereStatement(existCheckSQLWhereStatement);
        setCreateSQLRowsStatement(createSQLRowsStatement);
    }

    /**
     * �e�[�u�����쐬����SQL���̗��` ��Ԃ��܂��B
     * 
     * @return �e�[�u�����쐬����SQL���̗��`
     */
    public String getCreateSQLRowsStatement() {
        return createSQLRowsStatement;
    }

    /**
     * ���݊m�F�Ɏg�p����SQL���̗��` ��Ԃ��܂��B
     * 
     * @return ���݊m�F�Ɏg�p����SQL���̗��`
     */
    public String getExistCheckSQLRowsStatement() {
        return existCheckSQLRowsStatement;
    }

    /**
     * ���݊m�F�Ɏg�p����SQL����Where���` ��Ԃ��܂��B
     * 
     * @return ���݊m�F�Ɏg�p����SQL����Where���`
     */
    public String getExistCheckSQLWhereStatement() {
        return existCheckSQLWhereStatement;
    }

    /**
     * �e�[�u�����쐬����SQL���̗��` ��ݒ肵�܂��B
     * <p>
     * CREATE TABLE�̃e�[�u�����ȍ~�ɂ�����SQL����ݒ肵�܂��BPRIMARY KEY�̎w����܂߂Ă��܂��܂���B
     * </p>
     * 
     * @param createSQLRowsStatement �e�[�u�����쐬����SQL���̗��`
     */
    public void setCreateSQLRowsStatement(String createSQLRowsStatement) {
        this.createSQLRowsStatement = createSQLRowsStatement;
    }

    /**
     * ���݊m�F�Ɏg�p����SQL���̗��` ��ݒ肵�܂��B
     * 
     * @param existCheckSQLRowsStatement ���݊m�F�Ɏg�p����SQL���̗��`
     */
    public void setExistCheckSQLRowsStatement(String existCheckSQLRowsStatement) {
        this.existCheckSQLRowsStatement = existCheckSQLRowsStatement;
    }

    /**
     * ���݊m�F�Ɏg�p����SQL����Where���` ��ݒ肵�܂��B
     * <p>
     * WHERE�傪�K�v�Ȃ����null�������͋󕶎��B �K�v�ł����"WHERE"����\�������\����ݒ肵�܂��B
     * </p>
     * 
     * @param existCheckSQLWhereStatement ���݊m�F�Ɏg�p����SQL����Where���`
     */
    public void setExistCheckSQLWhereStatement(
            String existCheckSQLWhereStatement) {
        this.existCheckSQLWhereStatement = existCheckSQLWhereStatement;
    }

    /**
     * �w��̏C����ɑΉ�����e�[�u�����쐬����SQL����Ԃ��܂��B
     * @param modifier �C����
     * @return �e�[�u�����쐬����SQL��
     */
    public String getCreateSQL(int modifier) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ");
        sb.append(getModifiedTableName(modifier));
        sb.append("(");
        sb.append(getCreateSQLRowsStatement());
        sb.append(")");
        return sb.toString();
    }

    /**
     * �w��̏C����ɑΉ�����e�[�u���̑��݃`�F�b�N���s�Ȃ�SQL����Ԃ��܂��B
     * @param modifier �C����
     * @return �e�[�u���̑��݃`�F�b�N���s�Ȃ�SQL��
     */
    public String getExistCheckSQL(int modifier) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT ");
        sb.append(getExistCheckSQLRowsStatement());
        sb.append(" FROM ");
        sb.append(getModifiedTableName(modifier));
        String where = getExistCheckSQLWhereStatement();
        if (!ACCommon.getInstance().isNullText(where)) {
            sb.append(where);
        }

        return sb.toString();
    }

    public boolean createTable(ACDBManager dbm, int modifier) throws SQLException {
        dbm.executeUpdate(getCreateSQL(modifier));
        return true;
    }

    public boolean isExistTable(ACDBManager dbm, int modifier) throws SQLException {
        try {
            dbm.executeQuery(getExistCheckSQL(modifier));
            return true;
        } catch (Exception ex) {
            // �e�[�u�������݂��Ȃ�
            return false;
        }
    }

}