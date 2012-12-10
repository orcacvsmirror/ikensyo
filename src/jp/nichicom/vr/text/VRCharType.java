/** TODO <HEAD> */
package jp.nichicom.vr.text;

import java.util.regex.Pattern;

/**
 * 入力可能な文字種別を規定するクラスです。
 * <p>
 * 文字種別は正規表現で指定します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see Pattern
 */
public class VRCharType {
    /**
     * 英数字型定数です。
     * <p>
     * 半角英数字のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_ALNUM = new VRCharType("ONLY_ASCII",
            "^[0-9A-Za-z]*$");
    /**
     * 半角英数字カナ型です。
     * <p>
     * 半角英数字カナのみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_ALNUM_HALF_KANA = new VRCharType(
            "ONLY_ALNUM_HALF_KANA", "^[0-9A-Za-z\\uff61-\\uff9f]*$");
    /**
     * 英字型定数です。
     * <p>
     * 半角英字のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_ALPHABET = new VRCharType(
            "ONLY_ALPHABET", "^[A-Za-z]*$");

    /**
     * ASCII型定数です。
     * <p>
     * 半角ASCII文字のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_ASCII = new VRCharType("ONLY_ASCII",
            "^[ -~]*$");

    /**
     * 数字型です。
     * <p>
     * アラビア数字のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_DIGIT = new VRCharType("ONLY_DIGIT",
            "^[0-9]*$");

    /**
     * 少数型です。
     * <p>
     * 数字のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_FLOAT = new VRCharType("ONLY_FLOAT",
            "^(\\d+)|(\\d+\\.\\d*)$");

    /**
     * 全角型です。
     * <p>
     * 半角英数字と半角カナ以外を許容します。
     * </p>
     */
    public static final VRCharType ONLY_FULL_CHAR = new VRCharType(
            "ONLY_FULL_CHAR", "^[^\\u0020-\\u007e\\uff61-\\uff9f]*$");

    /**
     * 全角ひらがな型です。
     * <p>
     * 全角ひらがなのみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_FULL_HIRAGANA = new VRCharType(
            "ONLY_FULL_HIRAGANA", "^[ぁ-ん]*$");

    /**
     * 全角カタカナ型です。
     * <p>
     * 全角カタカナのみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_FULL_KATAKANA = new VRCharType(
            "ONLY_FULL_KATAKANA", "^[ァ-ヶ]*$");
    /**
     * 半角型です。
     * <p>
     * 半角文字のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_HALF_CHAR = new VRCharType(
            "ONLY_HALF_CHAR", "^[\\u0020-\\u007e\\uff61-\\uff9f]*$");
    /**
     * 半角カナ型です。
     * <p>
     * 半角カナのみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_HALF_KANA = new VRCharType(
            "ONLY_HALF_KANA", "^[\\uff61-\\uff9f]*$");

    /**
     * 整数型です。
     * <p>
     * 数字と"-"のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_NUMBER = new VRCharType("ONLY_NUMBER",
            "^[-]?[0-9]*$");
    /**
     * 記号型定数です。
     * <p>
     * 半角記号のみを許容します。
     * </p>
     */
    public static final VRCharType ONLY_PUNCT = new VRCharType("ONLY_PUNCT",
            "^[\\u0020-\\u002f\\u003a-\\u0040\\u005b-\\u0060\\u007b-\\u007e]*$");

    private String name;

    private Pattern pattern;

    private String reguler;

    /**
     * コンストラクタです。
     * <p>
     * <code>reguler</code>には入力を許可する文字列形式を正規表現で指定します。<br />
     * 例として、1～3文字の数字のみ許容する場合、<br />
     * <code>new VRCharType("MAX_THREE_DIGIT", "^[0-9]{1,3}$")</code><br />
     * などと指定します。
     * </p>
     * 
     * @param name 名称
     * @param reguler 正規表現
     */
    public VRCharType(String name, String reguler) {
        this.name = name;
        this.reguler = reguler;
    }

    /**
     * 検査文字列が定義型として有効であるかを返します。
     * <p>
     * Strategy Pattern
     * </p>
     * 
     * @param src 検査文字列
     * @return 有効ならばtrue
     */
    public boolean isMatch(String src) {
        if (pattern == null) {
            pattern = Pattern.compile(reguler);
        }
        return pattern.matcher(src).matches();
    }

    /**
     * 文字列化します。
     * 
     * @return 文字列化結果
     */
    public String toString() {
        return "Name:" + name + ", Reguler:" + reguler;
    }

}