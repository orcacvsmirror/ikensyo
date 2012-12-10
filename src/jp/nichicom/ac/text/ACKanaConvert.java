package jp.nichicom.ac.text;

/**
 * �������A�J�^�J�i�ϊ��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2006/02/10
 */
public class ACKanaConvert {

    /**
     * �����񒆂̑S�p�Љ�����S�p�������ɕϊ�����B<br>
     * <br>
     * 
     * @param katakana �S�p�Љ������܂ޕ�����
     * @return �S�p�������ɕϊ���̕�����
     */
    public static String toHiragana(String katakana) {
        // �����`�F�b�N
        if ((katakana == null) || (katakana.length() < 1))
            return katakana;

        char[] charAry = katakana.toCharArray();
        char temp;
        for (int i = 0; i < charAry.length; i++) {
            temp = charAry[i];
            // �J�^�J�i�͈̔͊m�F
            if ((0x30a1 <= temp) && (temp <= 0x30f6)) {
                charAry[i] -= 0x60;
            }
        }

        return String.valueOf(charAry);
    }

    /**
     * �����񒆂̑S�p��������S�p�Љ����ɕϊ�����B<br>
     * <br>
     * 
     * @param hiragana �S�p���������܂ޕ�����
     * @return �S�p�Љ����ɕϊ���̕�����
     */
    public static String toKatakana(String hiragana) {
        // �����`�F�b�N
        if ((hiragana == null) || (hiragana.length() < 1))
            return hiragana;

        char[] charAry = hiragana.toCharArray();
        char temp;
        for (int i = 0; i < charAry.length; i++) {
            temp = charAry[i];
            // �Ђ炪�Ȃ͈̔͊m�F
            if ((0x3041 <= temp) && (temp <= 0x3096)) {
                charAry[i] += 0x60;
            }
        }

        return String.valueOf(charAry);
    }

}