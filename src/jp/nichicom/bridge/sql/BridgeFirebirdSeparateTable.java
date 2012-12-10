package jp.nichicom.bridge.sql;

import java.sql.SQLException;

import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.sql.ACSeparateTablar;
import jp.nichicom.ac.sql.ACSeparateTableManager;
import jp.nichicom.ac.sql.AbstractACSeparateTable;

/**
 * 自動分割するFirebird用テーブル定義です。
 * <p>
 * 年や年度単位でテーブルを分割生成する際に使用します。
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
     * テーブルを作成するSQL文の列定義 を返します。
     * 
     * @return テーブルを作成するSQL文の列定義
     */
    public String getCreateSQLRowsStatement() {
        return createSQLRowsStatement;
    }

    /**
     * テーブルを作成するSQL文の列定義 を設定します。
     * <p>
     * CREATE TABLEのテーブル名以降にあたるSQL文を設定します。PRIMARY KEYの指定も含めてかまいません。
     * </p>
     * 
     * @param createSQLRowsStatement テーブルを作成するSQL文の列定義
     */
    public void setCreateSQLRowsStatement(String createSQLRowsStatement) {
        this.createSQLRowsStatement = createSQLRowsStatement;
    }

    /**
     * コンストラクタです。
     */
    public BridgeFirebirdSeparateTable() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param baseTableName 修飾語を含まないテーブル名
     * @param createSQLRowsStatement テーブルを作成するSQL文の列定義
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
