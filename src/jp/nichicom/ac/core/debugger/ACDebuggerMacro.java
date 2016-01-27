package jp.nichicom.ac.core.debugger;

/**
 * デバッグマクロ実行インターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/07/03
 */
public interface ACDebuggerMacro {
    /**
     * マクロ処理を実行します。
     * @param arg 引数
     */
    public void executeMacro(String arg);
    /**
     * マクロ処理を停止します。
     */
    public void stopMacro();
}
