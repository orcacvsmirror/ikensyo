/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;


/**
 * VRDateParserが使用する構文クラスです。
 * <p>
 * 解析対象の構文種類を規定します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 */
public class VRDateParserFormatStatement {
    /**
     * 元号名を表す構定数文です。
     */
    public static final char ERA_TEXT = 'g';

    /**
     * 元号年数を表す構定数文です。
     */
    public static final char ERA_YEAR = 'e';

    /**
     * 祝祭日名を表す構定数文です。
     */
    public static final char HOLYDAY = 'N';

    /**
     * リテラル文字列を表す構定数文です。
     */
    public static final char LITERAL = 'L';

    private int length;

    private String text;

    private char type;

    /**
     * コンストラクタ
     * 
     * @param type 形式
     * @param length 文字列長
     */
    public VRDateParserFormatStatement(char type, int length) {
        setType(type);
        setLength(length);
    }

    /**
     * 文字列長を返します。
     * 
     * @return 文字列長
     */
    public int getLength() {
        return length;
    }

    /**
     * 文を返します。
     * 
     * @return 文
     */
    public String getText() {
        return text;
    }

    /**
     * 形式を返します。
     * 
     * @return 形式
     */
    public char getType() {
        return type;
    }

    /**
     * 文字列長を設定します。
     * 
     * @param length 文字列長
     */
    public void setLength(int length) {
        if (length <= 0) {
            return;
        }
        this.length = length;
    }

    /**
     * 文を設定します。
     * 
     * @param text 文
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * 形式を設定します。
     * 
     * @param type 形式
     */
    public void setType(char type) {
        this.type = type;
    }
}