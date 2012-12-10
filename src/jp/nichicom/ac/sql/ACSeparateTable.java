package jp.nichicom.ac.sql;

import java.sql.SQLException;

import jp.nichicom.ac.ACCommon;

/**
 * 自動分割するテーブル定義です。
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
 */
public class ACSeparateTable extends AbstractACSeparateTable {
    private String createSQLRowsStatement;
    private String existCheckSQLRowsStatement;
    private String existCheckSQLWhereStatement;

    /**
     * コンストラクタです。
     */
    public ACSeparateTable() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param baseTableName 修飾語を含まないテーブル名
     * @param existCheckSQLRowsStatement 存在確認に使用するSQL文の列定義
     * @param existCheckSQLWhereStatement 存在確認に使用するSQL文のWhere句定義
     * @param createSQLRowsStatement テーブルを作成するSQL文の列定義
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
     * テーブルを作成するSQL文の列定義 を返します。
     * 
     * @return テーブルを作成するSQL文の列定義
     */
    public String getCreateSQLRowsStatement() {
        return createSQLRowsStatement;
    }

    /**
     * 存在確認に使用するSQL文の列定義 を返します。
     * 
     * @return 存在確認に使用するSQL文の列定義
     */
    public String getExistCheckSQLRowsStatement() {
        return existCheckSQLRowsStatement;
    }

    /**
     * 存在確認に使用するSQL文のWhere句定義 を返します。
     * 
     * @return 存在確認に使用するSQL文のWhere句定義
     */
    public String getExistCheckSQLWhereStatement() {
        return existCheckSQLWhereStatement;
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
     * 存在確認に使用するSQL文の列定義 を設定します。
     * 
     * @param existCheckSQLRowsStatement 存在確認に使用するSQL文の列定義
     */
    public void setExistCheckSQLRowsStatement(String existCheckSQLRowsStatement) {
        this.existCheckSQLRowsStatement = existCheckSQLRowsStatement;
    }

    /**
     * 存在確認に使用するSQL文のWhere句定義 を設定します。
     * <p>
     * WHERE句が必要なければnullもしくは空文字。 必要であれば"WHERE"から構成した構文を設定します。
     * </p>
     * 
     * @param existCheckSQLWhereStatement 存在確認に使用するSQL文のWhere句定義
     */
    public void setExistCheckSQLWhereStatement(
            String existCheckSQLWhereStatement) {
        this.existCheckSQLWhereStatement = existCheckSQLWhereStatement;
    }

    /**
     * 指定の修飾語に対応するテーブルを作成するSQL文を返します。
     * @param modifier 修飾語
     * @return テーブルを作成するSQL文
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
     * 指定の修飾語に対応するテーブルの存在チェックを行なうSQL文を返します。
     * @param modifier 修飾語
     * @return テーブルの存在チェックを行なうSQL文
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
            // テーブルが存在しない
            return false;
        }
    }

}