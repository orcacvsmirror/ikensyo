/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;



/**
 * VRDateParser���g�p�����\���C���^�[�t�F�[�X�ł��B
 * <p>
 * ��������`���̍\���Ƃ��ĉ��߂��A���ߌ��ʂ�ݒ肵�܂��B
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
     * �Ώە�����̎w��ʒu����\���p�^�[�����n�܂��Ă���Ƃ��āA�\�����߂��s�Ȃ��܂��B
     * @param option ���ߏ�������ь��ʑ����
     * @return �\�����߂ɐ���������
     */
    boolean isMatched(VRDateParserStatementParseOption option);
    
}