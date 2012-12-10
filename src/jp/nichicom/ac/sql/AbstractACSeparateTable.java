package jp.nichicom.ac.sql;

/**
 * 自動分割する基底テーブル定義です。
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
public abstract class AbstractACSeparateTable implements ACSeparateTablar {
    private String baseTableName;

    /**
     * コンストラクタです。
     */
    public AbstractACSeparateTable() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param baseTableName 修飾語を含まない対象テーブル名
     */
    public AbstractACSeparateTable(String baseTableName) {
        super();
        setBaseTableName(baseTableName);
    }

    /**
     * 修飾語を含まない対象テーブル名 を返します。
     * 
     * @return 修飾語を含まない対象テーブル名
     */
    public String getBaseTableName() {
        return baseTableName;
    }

    /**
     * 修飾語を含まない対象テーブル名 を設定します。
     * 
     * @param baseTableName 修飾語を含まない対象テーブル名
     */
    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

    /**
     * 修飾語を付加したテーブル名を返します。
     * <p>
     * 基底クラスではサフィックス表記となるため、変更する場合はoverrideを使用します。
     * </p>
     * 
     * @param modifier 修飾語
     * @return 修飾語を付加したテーブル名
     */
    public String getModifiedTableName(int modifier) {
        return getBaseTableName() + "_" + modifier;
    }

}
