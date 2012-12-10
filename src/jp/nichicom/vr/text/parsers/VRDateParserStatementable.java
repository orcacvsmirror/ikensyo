/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;



/**
 * VRDateParserが使用する暦構文インターフェースです。
 * <p>
 * 文字列を暦形式の構文として解釈し、解釈結果を設定します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserStatementParseOption
 */
public interface VRDateParserStatementable {
    /**
     * 対象文字列の指定位置から構文パターンが始まっているとして、構文解釈を行ないます。
     * @param option 解釈条件および結果代入先
     * @return 構文解釈に成功したか
     */
    boolean isMatched(VRDateParserStatementParseOption option);
    
}