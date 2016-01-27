package jp.nichicom.ac.core.debugger;

import java.util.logging.Level;

/**
 * デバッグ制御可能なシステムイベント処理クラスであることをあらわすインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public interface ACFrameEventDebugger {
    /**
     * 基本的なDBのログを出力することをあらわすログ範囲定数です。
     */
    public static final int LOG_RANGE_DB_BASIC = 0;
    /**
     * SQLクエリの結果もログ出力することをあらわすログ範囲定数です。
     */
    public static final int LOG_RANGE_DB_QUERY_RESULT = 1;
    
    /**
     * ログ出力レベルを再設定します。
     */
    public void refleshSetting();

    /**
     * DBに関するログ出力レベルを返します。
     * 
     * @return ログ出力レベル
     */
    public Level getDBLogLevel();

    /**
     * 印刷に関するログ出力レベルを返します。
     * 
     * @return ログ出力レベル
     */
    public Level getPrintLogLevel();

    /**
     * 業務遷移に関するログ出力レベルを返します。
     * 
     * @return ログ出力レベル
     */
    public Level getAffairLogLevel();
    
    /**
     * DBに関するログ出力を行なう範囲を返します。
     * @return DBに関するログ出力を行なう範囲
     */
    public int getDBLogRange();
}
