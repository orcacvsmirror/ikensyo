package jp.nichicom.ac.sql;

import java.text.Format;

import jp.nichicom.ac.text.ACSQLSafeStringFormat;

/**
 * パッシブタスク用のデータ特定キーです。
 * <p>
 * このキー情報から、パッシブチェック用のWHERE句を構成します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACPassiveKey {
    protected String table;
    protected String[] fields;
    protected Format[] formats;
    protected String clientPassiveTimeField;
    protected String serverPassiveTimeField;

    /**
     * コンストラクタです。
     * 
     * @param table 主テーブル名
     * @param fields 主キー名
     * @param formats キー値文字列化フォーマット
     * @param clientPassiveTimeField 退避データにおけるパッシブチェック用日時キー名
     * @param serverPassiveTimeField 比較先テーブルにおけるパッシブチェック用日時キー名
     */
    public ACPassiveKey(String table, String[] fields, Format[] formats,
            String clientPassiveTimeField, String serverPassiveTimeField) {
        this.table = table;
        this.fields = fields;
        this.formats = formats;
        this.clientPassiveTimeField = clientPassiveTimeField;
        this.serverPassiveTimeField = serverPassiveTimeField;
    }

    /**
     * コンストラクタです。
     * 
     * @param table 主テーブル名
     * @param fields 主キー名
     * @param quotes キー値は''で括るべき文字列であるか
     * @param clientPassiveTimeField 退避データにおけるパッシブチェック用日時キー名
     * @param serverPassiveTimeField 比較先テーブルにおけるパッシブチェック用日時キー名
     */
    public ACPassiveKey(String table, String[] fields, boolean[] quotes,
            String clientPassiveTimeField, String serverPassiveTimeField) {
        int end = quotes.length;
        Format[] f = new Format[end];
        for (int i = 0; i < end; i++) {
            f[i] = quotes[i] ? ACSQLSafeStringFormat.getInstance() : null;
        }
        this.table = table;
        this.fields = fields;
        this.formats = f;
        this.clientPassiveTimeField = clientPassiveTimeField;
        this.serverPassiveTimeField = serverPassiveTimeField;
    }

    /**
     * コンストラクタです。
     * 
     * @param table 主テーブル名
     * @param fields 主キー名
     * @param clientPassiveTimeField 退避データにおけるパッシブチェック用日時キー名
     * @param serverPassiveTimeField 比較先テーブルにおけるパッシブチェック用日時キー名
     */
    public ACPassiveKey(String table, String[] fields,
            String clientPassiveTimeField, String serverPassiveTimeField) {
        this(table, fields, (Format[]) null, clientPassiveTimeField,
                serverPassiveTimeField);
    }

    /**
     * コンストラクタです。
     */
    public ACPassiveKey() {
    }

    public boolean equals(Object o) {
        if (o instanceof ACPassiveKey) {
            ACPassiveKey x = (ACPassiveKey) o;
            if (table.equals(x.table) && fields.equals(x.fields)) {
                if (formats == null) {
                    return x.formats == null;
                } else {
                    return formats.equals(x.formats);
                }
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("([table=");
        sb.append(table);
        sb.append("][fields=(");
        int end = fields.length;
        if (end > 0) {
            sb.append(fields[0]);
            for (int i = 1; i < end; i++) {
                sb.append(",");
                sb.append(fields[i]);
            }
        }
        sb.append(")][formats=(");
        end = formats.length;
        if (end > 0) {
            sb.append(formats[0]);
            for (int i = 1; i < end; i++) {
                sb.append(",");
                sb.append(formats[i]);
            }
        }
        sb.append(")])");
        return sb.toString();
    }

    /**
     * 主キー名を返します。
     * 
     * @return 主キー名
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * キー値文字列化フォーマットを返します。
     * 
     * @return キー値文字列化フォーマット
     */
    public Format[] getFormats() {
        return formats;
    }

    /**
     * 主テーブル名を返します。
     * 
     * @return 主テーブル名
     */
    public String getTable() {
        return table;
    }

    /**
     * 比較先テーブルにおけるパッシブチェック用日時キー名を返します。
     * 
     * @return 退比較先テーブルにおけるパッシブチェック用日時キー名
     */
    public String getServerPassiveTimeField() {
        return serverPassiveTimeField;
    }

    /**
     * 退避データにおけるパッシブチェック用日時キー名を返します。
     * 
     * @return 退避データにおけるパッシブチェック用日時キー名
     */
    public String getClientPassiveTimeField() {
        return clientPassiveTimeField;
    }
}
