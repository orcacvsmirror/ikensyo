/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.Calendar;
import java.util.List;


/**
 * VRDateParser���g�p����j�Փ�����C���^�[�t�F�[�X�ł��B
 * <p>
 * �t���E�H���̓��̂悤�Ɍv�Z�ɂ���ē��肷��j�Փ����`����ۂɗp���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 */
public interface VRDateParserHolydayCalculatable {
    /**
     * �w����ɊY������j����`�W����Ԃ��܂��B
     * 
     * @param cal �����Ώۓ�
     * @param holydays �ǉ���̊Y���ς݂̋x���W��
     * @param parameter �z�Q�Ɩh�~���ɗ��p���鎩�R�̈�B�ʏ��null���w��
     */
    void stockHolyday(Calendar cal, List holydays, Object parameter);
}