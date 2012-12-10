package jp.nichicom.ac.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRLinkedHashMap;
import jp.nichicom.vr.util.VRList;

/**
 * JDBCのResultSetをVR機構を想定したデータ構造へ変換するライブラリです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2005/12/01
 */

public class ACResultSetForwardBindSource {

    /**
     * java.sql.ResultSet から VRArrayList への転送処理を行います。
     * <p>
     * 返り値はフィールド名：値の関係からなる VRHashMap を列挙した VRArrayList です。
     * </p>
     * 
     * @param rs SQL 文の実行結果
     * @return 転送後のオブジェクト
     * @throws SQLException 転送実行時の例外
     */
    public static VRList forward(ResultSet rs) throws SQLException {
        VRArrayList tbl = new VRArrayList();
        VRBindSource row = null;

        ResultSetMetaData rsmeta = rs.getMetaData();
        int colCount = rsmeta.getColumnCount();
        String[] fields = new String[colCount];

        // ResultSet の列名とタイプを取得
        for (int i = 0; i < colCount; i++) {
            fields[i] = rsmeta.getColumnName(i + 1);
        }

        // ResultSet の内容を転送
        while (rs.next()) {

            row = new VRLinkedHashMap();

            // 取得した列分ループし、結果を VRHashMap に設定する。
            for (int i = 0; i < colCount; i++) {
                row.setData(fields[i], rs.getObject(fields[i]));
            }
            // 列データを設定した VRHashMap を VRArrayList に設定する。
            tbl.addData(row);
        }

        return tbl;
    }
}