package jp.nichicom.ac.sql;

/**
 * パッシブチェック用のタスククラスです。
 * <p>
 * どのようなパッシブチェックを行なうかを規定します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACPassiveTask {
    /**
     * 削除を表すパッシブチェックタイプ定数です。
     */
    public static final int PASSIVE_DELETE = 2;
    /**
     * 追加を表すパッシブチェックタイプ定数です。
     */
    public static final int PASSIVE_INSERT = 1;
    /**
     * 更新を表すパッシブチェックタイプ定数です。
     */
    public static final int PASSIVE_UPDATE = 0;

    /**
     * 全件比較を表すチェック対象タイプ定数です。
     */
    public static final int CHECK_TARGET_ALL = 0;
    /**
     * 指定行番号の単件比較を表すチェック対象タイプ定数です。
     */
    public static final int CHECK_TARGET_ROW = 1;
    /**
     * 指定データを有する行の単件比較を表すチェック対象タイプ定数です。
     */
    public static final int CHECK_TARGET_VALUE = 2;

    protected int checkTargetType;
    protected ACPassiveKey key;
    protected int targetRow;
    protected int commandType;
    protected String targetBindPath;
    protected Object targetValue;

    /**
     * チェック対象タイプ を返します。
     * @return チェック対象タイプ
     */
    public int getCheckTargetType() {
        return checkTargetType;
    }

    /**
     * チェック対象タイプ を設定します。
     * @param targetType チェック対象タイプ
     */
    public void setCheckTargetType(int targetType) {
        this.checkTargetType = targetType;
    }

    /**
     * コンストラクタです。
     * <p>
     * 全件のパッシブタスクとして初期化します。
     * </p>
     * 
     * @param commandType パッシブチェック種別
     * @param key 比較キー
     */
    public ACPassiveTask(int commandType, ACPassiveKey key) {
        setCommandType(commandType);
        setKey(key);
        setTargetRow(0);
        setCheckTargetType(CHECK_TARGET_ALL);
    }

    /**
     * コンストラクタです。
     * <p>
     * 対象行を指定した単件のパッシブタスクとして初期化します。
     * </p>
     * 
     * @param commandType パッシブチェック種別
     * @param key 比較キー
     * @param targetRow 対象行番号
     */
    public ACPassiveTask(int commandType, ACPassiveKey key, int targetRow) {
        setCommandType(commandType);
        setKey(key);
        setTargetRow(targetRow);
        setCheckTargetType(CHECK_TARGET_ROW);
    }

    /**
     * コンストラクタです。
     * <p>
     * 値から対象業を求める単件のパッシブタスクとして初期化します。
     * </p>
     * 
     * @param commandType パッシブチェック種別
     * @param key 比較キー
     * @param targetBindPath 対象業を求めるための値列名
     * @param targetValue 対象業を求めるための値
     */
    public ACPassiveTask(int commandType, ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        setCommandType(commandType);
        setKey(key);
        setTargetBindPath(targetBindPath);
        setTargetValue(targetValue);
        setCheckTargetType(CHECK_TARGET_VALUE);
    }

    /**
     * パッシブキーを返します。
     * 
     * @return パッシブキー
     */
    public ACPassiveKey getKey() {
        return key;
    }

    /**
     * 対象行を返します。
     * 
     * @return 対象行
     */
    public int getTargetRow() {
        return targetRow;
    }

    /**
     * パッシブチェックタイプを返します。
     * 
     * @return パッシブチェックタイプ
     */
    public int getCommandType() {
        return commandType;
    }

    /**
     * パッシブキーを設定します。
     * 
     * @param key パッシブキー
     */
    public void setKey(ACPassiveKey key) {
        this.key = key;
    }

    /**
     * 対象行を設定します。
     * 
     * @param row 対象行
     */
    public void setTargetRow(int row) {
        this.targetRow = row;
    }

    /**
     * パッシブチェックタイプを設定します。
     * 
     * @param type パッシブチェックタイプ
     */
    public void setCommandType(int type) {
        this.commandType = type;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("([type=");
        switch (getCommandType()) {
        case PASSIVE_UPDATE:
            sb.append("PASSIVE_UPDATE");
            break;
        case PASSIVE_INSERT:
            sb.append("PASSIVE_INSERT");
            break;
        case PASSIVE_DELETE:
            sb.append("PASSIVE_DELETE");
            break;
        default:
            sb.append(getCommandType());
            break;
        }
        sb.append("][key=");
        sb.append(getKey().toString());
        switch(getCheckTargetType()){
        case CHECK_TARGET_ROW:
            // 行数指定
            sb.append("][row=");
            sb.append(getTargetRow());
            break;
        case CHECK_TARGET_VALUE:
            // 行データ指定
            sb.append("][rowBindPath=");
            sb.append(getTargetBindPath());
            sb.append("][rowValue=");
            sb.append(getTargetValue());
            break;
        }
        sb.append("])");
        return sb.toString();
    }

    /**
     * 単件のパッシブチェックにおいて、対象行を求めるための値列名 を返します。
     * 
     * @return 対象行を求めるための値列名
     */
    public String getTargetBindPath() {
        return targetBindPath;
    }

    /**
     * 対象行を求めるための値列名 を設定します。
     * 
     * @param targetBindPath 対象行を求めるための値列名
     */
    public void setTargetBindPath(String targetBindPath) {
        this.targetBindPath = targetBindPath;
    }

    /**
     * 単件のパッシブチェックにおいて、対象行を求めるための値 を返します。
     * 
     * @return 対象行を求めるための値
     */
    public Object getTargetValue() {
        return targetValue;
    }

    /**
     * 単件のパッシブチェックにおいて、対象行を求めるための値 を設定します。
     * 
     * @param targetValue 対象行を求めるための値
     */
    public void setTargetValue(Object targetValue) {
        this.targetValue = targetValue;
    }

}