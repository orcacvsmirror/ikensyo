/** TODO <HEAD> */
package jp.nichicom.vr.text;

import java.util.regex.Pattern;

/**
 * ���͉\�ȕ�����ʂ��K�肷��N���X�ł��B
 * <p>
 * ������ʂ͐��K�\���Ŏw�肵�܂��B
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
     * �p�����^�萔�ł��B
     * <p>
     * ���p�p�����݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_ALNUM = new VRCharType("ONLY_ASCII",
            "^[0-9A-Za-z]*$");
    /**
     * ���p�p�����J�i�^�ł��B
     * <p>
     * ���p�p�����J�i�݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_ALNUM_HALF_KANA = new VRCharType(
            "ONLY_ALNUM_HALF_KANA", "^[0-9A-Za-z\\uff61-\\uff9f]*$");
    /**
     * �p���^�萔�ł��B
     * <p>
     * ���p�p���݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_ALPHABET = new VRCharType(
            "ONLY_ALPHABET", "^[A-Za-z]*$");

    /**
     * ASCII�^�萔�ł��B
     * <p>
     * ���pASCII�����݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_ASCII = new VRCharType("ONLY_ASCII",
            "^[ -~]*$");

    /**
     * �����^�ł��B
     * <p>
     * �A���r�A�����݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_DIGIT = new VRCharType("ONLY_DIGIT",
            "^[0-9]*$");

    /**
     * �����^�ł��B
     * <p>
     * �����݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_FLOAT = new VRCharType("ONLY_FLOAT",
            "^(\\d+)|(\\d+\\.\\d*)$");

    /**
     * �S�p�^�ł��B
     * <p>
     * ���p�p�����Ɣ��p�J�i�ȊO�����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_FULL_CHAR = new VRCharType(
            "ONLY_FULL_CHAR", "^[^\\u0020-\\u007e\\uff61-\\uff9f]*$");

    /**
     * �S�p�Ђ炪�Ȍ^�ł��B
     * <p>
     * �S�p�Ђ炪�Ȃ݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_FULL_HIRAGANA = new VRCharType(
            "ONLY_FULL_HIRAGANA", "^[��-��]*$");

    /**
     * �S�p�J�^�J�i�^�ł��B
     * <p>
     * �S�p�J�^�J�i�݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_FULL_KATAKANA = new VRCharType(
            "ONLY_FULL_KATAKANA", "^[�@-��]*$");
    /**
     * ���p�^�ł��B
     * <p>
     * ���p�����݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_HALF_CHAR = new VRCharType(
            "ONLY_HALF_CHAR", "^[\\u0020-\\u007e\\uff61-\\uff9f]*$");
    /**
     * ���p�J�i�^�ł��B
     * <p>
     * ���p�J�i�݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_HALF_KANA = new VRCharType(
            "ONLY_HALF_KANA", "^[\\uff61-\\uff9f]*$");

    /**
     * �����^�ł��B
     * <p>
     * ������"-"�݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_NUMBER = new VRCharType("ONLY_NUMBER",
            "^[-]?[0-9]*$");
    /**
     * �L���^�萔�ł��B
     * <p>
     * ���p�L���݂̂����e���܂��B
     * </p>
     */
    public static final VRCharType ONLY_PUNCT = new VRCharType("ONLY_PUNCT",
            "^[\\u0020-\\u002f\\u003a-\\u0040\\u005b-\\u0060\\u007b-\\u007e]*$");

    private String name;

    private Pattern pattern;

    private String reguler;

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * <code>reguler</code>�ɂ͓��͂������镶����`���𐳋K�\���Ŏw�肵�܂��B<br />
     * ��Ƃ��āA1�`3�����̐����̂݋��e����ꍇ�A<br />
     * <code>new VRCharType("MAX_THREE_DIGIT", "^[0-9]{1,3}$")</code><br />
     * �ȂǂƎw�肵�܂��B
     * </p>
     * 
     * @param name ����
     * @param reguler ���K�\��
     */
    public VRCharType(String name, String reguler) {
        this.name = name;
        this.reguler = reguler;
    }

    /**
     * ���������񂪒�`�^�Ƃ��ėL���ł��邩��Ԃ��܂��B
     * <p>
     * Strategy Pattern
     * </p>
     * 
     * @param src ����������
     * @return �L���Ȃ��true
     */
    public boolean isMatch(String src) {
        if (pattern == null) {
            pattern = Pattern.compile(reguler);
        }
        return pattern.matcher(src).matches();
    }

    /**
     * �����񉻂��܂��B
     * 
     * @return �����񉻌���
     */
    public String toString() {
        return "Name:" + name + ", Reguler:" + reguler;
    }

}