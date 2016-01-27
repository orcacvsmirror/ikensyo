package jp.nichicom.ac.core.debugger;

import java.util.logging.Level;

/**
 * �f�o�b�O����\�ȃV�X�e���C�x���g�����N���X�ł��邱�Ƃ�����킷�C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 */
public interface ACFrameEventDebugger {
    /**
     * ��{�I��DB�̃��O���o�͂��邱�Ƃ�����킷���O�͈͒萔�ł��B
     */
    public static final int LOG_RANGE_DB_BASIC = 0;
    /**
     * SQL�N�G���̌��ʂ����O�o�͂��邱�Ƃ�����킷���O�͈͒萔�ł��B
     */
    public static final int LOG_RANGE_DB_QUERY_RESULT = 1;
    
    /**
     * ���O�o�̓��x�����Đݒ肵�܂��B
     */
    public void refleshSetting();

    /**
     * DB�Ɋւ��郍�O�o�̓��x����Ԃ��܂��B
     * 
     * @return ���O�o�̓��x��
     */
    public Level getDBLogLevel();

    /**
     * ����Ɋւ��郍�O�o�̓��x����Ԃ��܂��B
     * 
     * @return ���O�o�̓��x��
     */
    public Level getPrintLogLevel();

    /**
     * �Ɩ��J�ڂɊւ��郍�O�o�̓��x����Ԃ��܂��B
     * 
     * @return ���O�o�̓��x��
     */
    public Level getAffairLogLevel();
    
    /**
     * DB�Ɋւ��郍�O�o�͂��s�Ȃ��͈͂�Ԃ��܂��B
     * @return DB�Ɋւ��郍�O�o�͂��s�Ȃ��͈�
     */
    public int getDBLogRange();
}
