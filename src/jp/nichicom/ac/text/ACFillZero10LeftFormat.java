package jp.nichicom.ac.text;

/**
 * 10桁の左0詰めを行なうフォーマットです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACFillCharFormat
 */

public class ACFillZero10LeftFormat extends ACFillCharFormat {
    protected static ACFillZero10LeftFormat singleton;

    /**
     * コンストラクタです。<br />
     * Singleton Pattern
     */
    protected ACFillZero10LeftFormat() {
        super("0", 10);
    }

    /**
     * インスタンスを取得します。
     */
    public static ACFillZero10LeftFormat getInstance() {
        if (singleton == null) {
            singleton = new ACFillZero10LeftFormat();
        }
        return singleton;
    }
}
