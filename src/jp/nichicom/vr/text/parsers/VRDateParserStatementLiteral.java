/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;


/**
 * VRDateParserが使用する文字列としてそのまま解釈させるリテラル用の暦構文クラスです。
 * <p>
 * 解析上はリテラルとして扱いますが、最終的にはVRDateParserにてSinmpleDateFormatを通しますので、hh:MM:ssのような時刻は自動解釈されます。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserStatementable
 * @see VRDateParserStatementParseOption
 */
public class VRDateParserStatementLiteral implements VRDateParserStatementable {
    private String text;

    /**
     * コンストラクタ
     * 
     * @param text リテラル文字列
     */
    public VRDateParserStatementLiteral(String text) {
        setText(text);
    }

    /**
     * リテラル文字列を返します。
     * 
     * @return リテラル文字列
     */
    public String getText() {
        return text;
    }

    /**
     * リテラル文字列を設定します。
     * 
     * @param text リテラル文字列
     */
    public void setText(String text) {
        this.text = text;
    }

    public boolean isMatched(VRDateParserStatementParseOption option) {
        String target = option.getTarget();
        int i = option.getParseBeginIndex();
        int len = target.length();

        //リテラル文字列
        String txt = this.getText();
        int txtLen = txt.length();
        if (i + txtLen > len) {
            //リテラル文字列分の長さが残っていない
            return false;
        }
        if (!target.substring(i, i + txtLen).equals(txt)) {
            //リテラル文字列が一致しない
            return false;
        }
        i += txtLen;

        option.setParseEndIndex(i);
        return true;
    }
}