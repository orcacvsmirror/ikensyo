package jp.nichicom.ac.sql;

import java.sql.SQLException;

/**
 * 自動分割するテーブル定義インターフェースです。
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
 * @see ACSeparateTable
 */
public interface ACSeparateTablar {
    /**
     * 指定の修飾語に対応するテーブルを作成します。
     * 
     * @param dbm DBManager
     * @param modifier 修飾語
     * @throws SQLException 処理例外
     * @return 指定の修飾語に対応するテーブルの作成に成功したか
     */
    boolean createTable(ACDBManager dbm, int modifier) throws SQLException;

    /**
     * 指定の修飾語に対応するテーブルが存在するか を返します。
     * 
     * @param dbm DBManager
     * @param modifier 修飾語
     * @throws SQLException 処理例外
     * @return 指定の修飾語に対応するテーブルが存在するか
     */
    boolean isExistTable(ACDBManager dbm, int modifier) throws SQLException;

    /**
     * 修飾語を付加したテーブル名を返します。
     * 
     * @param modifier 修飾語
     * @return 修飾語を付加したテーブル名
     */
    String getModifiedTableName(int modifier);
}
