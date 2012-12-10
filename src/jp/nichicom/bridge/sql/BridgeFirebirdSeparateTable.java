package jp.nichicom.bridge.sql;

import java.sql.SQLException;

import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.sql.ACSeparateTablar;
import jp.nichicom.ac.sql.ACSeparateTableManager;
import jp.nichicom.ac.sql.AbstractACSeparateTable;

/**
 * ������������Firebird�p�e�[�u����`�ł��B
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
 * @see AbstractACSeparateTable
 */
public class BridgeFirebirdSeparateTable extends AbstractACSeparateTable {
    private String createSQLRowsStatement;

    /**
     * �e�[�u�����쐬����SQL���̗��` ��Ԃ��܂��B
     * 
     * @return �e�[�u�����쐬����SQL���̗��`
     */
    public String getCreateSQLRowsStatement() {
        return createSQLRowsStatement;
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
     * �R���X�g���N�^�ł��B
     */
    public BridgeFirebirdSeparateTable() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param baseTableName �C������܂܂Ȃ��e�[�u����
     * @param createSQLRowsStatement �e�[�u�����쐬����SQL���̗��`
     */
    public BridgeFirebirdSeparateTable(String baseTableName,
            String createSQLRowsStatement) {
        super(baseTableName);
        setCreateSQLRowsStatement(createSQLRowsStatement);
    }

    public String getExistCheckSQL(int modifier) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" RDB$RELATION_NAME");
        sb.append(" FROM");
        sb.append(" RDB$RELATIONS");
        sb.append(" WHERE");
        sb.append(" (RDB$SYSTEM_FLAG = 0)");
        sb.append(" AND (RDB$VIEW_BLR IS NULL)");
        sb.append(" AND (RDB$RELATION_NAME = '"
                + getModifiedTableName(modifier) + "')");
        return sb.toString();
    }

    public String getCreateSQL(int modifier) {
        StringBuffer sb = new StringBuffer();
        sb.append("CREATE TABLE ");
        sb.append(getModifiedTableName(modifier));
        sb.append("(");
        sb.append(getCreateSQLRowsStatement());
        sb.append(")");
        return sb.toString();
    }

    public boolean createTable(ACDBManager dbm, int modifier)
            throws SQLException {
        dbm.executeUpdate(getCreateSQL(modifier));
        return true;
    }

    public boolean isExistTable(ACDBManager dbm, int modifier)
            throws SQLException {
        return dbm.executeQuery(getExistCheckSQL(modifier)).size() > 0;
    }
}
