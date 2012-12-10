package jp.nichicom.ac.sql;

import java.sql.SQLException;

import jp.nichicom.vr.util.VRList;

/**
 * DBアクセスを抽象化するインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACDBManager {
    /**
     * トランザクションを開始します。
     * 
     * @throws SQLException SQL例外
     */
    public void beginTransaction() throws SQLException;

    /**
     * SELECT 文の実行を行います。
     * 
     * @param sql 実行する SELECT 文
     * @return 実行結果
     * @throws SQLException SELECT 文実行時例外
     */
    public VRList executeQuery(String sql) throws SQLException;

    /**
     * UPDATE 文の実行を行います。
     * 
     * @param sql 実行する SQL 文
     * @return int 実行によって影響を受けた列数
     * @throws SQLException UPDATE 実行時例外
     */
    public int executeUpdate(String sql) throws SQLException;

    /**
     * トランザクションをロールバックします。
     * 
     * @throws SQLException SQL例外
     */
    public void rollbackTransaction() throws SQLException;

    /**
     * トランザクションをコミットします。
     * 
     * @throws SQLException SQL例外
     */
    void commitTransaction() throws SQLException;
}