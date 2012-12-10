package jp.nichicom.ac.text;

/**
 * 平仮名、カタカナ変換クラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2006/02/10
 */
public class ACKanaConvert {

    /**
     * 文字列中の全角片仮名を全角平仮名に変換する。<br>
     * <br>
     * 
     * @param katakana 全角片仮名を含む文字列
     * @return 全角平仮名に変換後の文字列
     */
    public static String toHiragana(String katakana) {
        // 引数チェック
        if ((katakana == null) || (katakana.length() < 1))
            return katakana;

        char[] charAry = katakana.toCharArray();
        char temp;
        for (int i = 0; i < charAry.length; i++) {
            temp = charAry[i];
            // カタカナの範囲確認
            if ((0x30a1 <= temp) && (temp <= 0x30f6)) {
                charAry[i] -= 0x60;
            }
        }

        return String.valueOf(charAry);
    }

    /**
     * 文字列中の全角平仮名を全角片仮名に変換する。<br>
     * <br>
     * 
     * @param hiragana 全角平仮名を含む文字列
     * @return 全角片仮名に変換後の文字列
     */
    public static String toKatakana(String hiragana) {
        // 引数チェック
        if ((hiragana == null) || (hiragana.length() < 1))
            return hiragana;

        char[] charAry = hiragana.toCharArray();
        char temp;
        for (int i = 0; i < charAry.length; i++) {
            temp = charAry[i];
            // ひらがなの範囲確認
            if ((0x3041 <= temp) && (temp <= 0x3096)) {
                charAry[i] += 0x60;
            }
        }

        return String.valueOf(charAry);
    }

}