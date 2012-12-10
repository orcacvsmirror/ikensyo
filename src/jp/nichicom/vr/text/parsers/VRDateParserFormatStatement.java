/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;


/**
 * VRDateParser���g�p����\���N���X�ł��B
 * <p>
 * ��͑Ώۂ̍\����ނ��K�肵�܂��B
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
     * ��������\���\�萔���ł��B
     */
    public static final char ERA_TEXT = 'g';

    /**
     * �����N����\���\�萔���ł��B
     */
    public static final char ERA_YEAR = 'e';

    /**
     * �j�Փ�����\���\�萔���ł��B
     */
    public static final char HOLYDAY = 'N';

    /**
     * ���e�����������\���\�萔���ł��B
     */
    public static final char LITERAL = 'L';

    private int length;

    private String text;

    private char type;

    /**
     * �R���X�g���N�^
     * 
     * @param type �`��
     * @param length ������
     */
    public VRDateParserFormatStatement(char type, int length) {
        setType(type);
        setLength(length);
    }

    /**
     * �����񒷂�Ԃ��܂��B
     * 
     * @return ������
     */
    public int getLength() {
        return length;
    }

    /**
     * ����Ԃ��܂��B
     * 
     * @return ��
     */
    public String getText() {
        return text;
    }

    /**
     * �`����Ԃ��܂��B
     * 
     * @return �`��
     */
    public char getType() {
        return type;
    }

    /**
     * �����񒷂�ݒ肵�܂��B
     * 
     * @param length ������
     */
    public void setLength(int length) {
        if (length <= 0) {
            return;
        }
        this.length = length;
    }

    /**
     * ����ݒ肵�܂��B
     * 
     * @param text ��
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * �`����ݒ肵�܂��B
     * 
     * @param type �`��
     */
    public void setType(char type) {
        this.type = type;
    }
}