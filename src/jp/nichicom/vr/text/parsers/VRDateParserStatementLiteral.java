/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;


/**
 * VRDateParser���g�p���镶����Ƃ��Ă��̂܂܉��߂����郊�e�����p�̗�\���N���X�ł��B
 * <p>
 * ��͏�̓��e�����Ƃ��Ĉ����܂����A�ŏI�I�ɂ�VRDateParser�ɂ�SinmpleDateFormat��ʂ��܂��̂ŁAhh:MM:ss�̂悤�Ȏ����͎������߂���܂��B
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
     * �R���X�g���N�^
     * 
     * @param text ���e����������
     */
    public VRDateParserStatementLiteral(String text) {
        setText(text);
    }

    /**
     * ���e�����������Ԃ��܂��B
     * 
     * @return ���e����������
     */
    public String getText() {
        return text;
    }

    /**
     * ���e�����������ݒ肵�܂��B
     * 
     * @param text ���e����������
     */
    public void setText(String text) {
        this.text = text;
    }

    public boolean isMatched(VRDateParserStatementParseOption option) {
        String target = option.getTarget();
        int i = option.getParseBeginIndex();
        int len = target.length();

        //���e����������
        String txt = this.getText();
        int txtLen = txt.length();
        if (i + txtLen > len) {
            //���e���������񕪂̒������c���Ă��Ȃ�
            return false;
        }
        if (!target.substring(i, i + txtLen).equals(txt)) {
            //���e���������񂪈�v���Ȃ�
            return false;
        }
        i += txtLen;

        option.setParseEndIndex(i);
        return true;
    }
}