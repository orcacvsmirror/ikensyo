package jp.nichicom.ac.core.debugger;

/**
 * �f�o�b�O�}�N�����s�C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/07/03
 */
public interface ACDebuggerMacro {
    /**
     * �}�N�����������s���܂��B
     * @param arg ����
     */
    public void executeMacro(String arg);
    /**
     * �}�N���������~���܂��B
     */
    public void stopMacro();
}
