package jp.nichicom.ac.sql;

import java.sql.SQLException;

/**
 * ������������e�[�u����`�C���^�[�t�F�[�X�ł��B
 * <p>
 * �N��N�x�P�ʂŃe�[�u���𕪊���������ۂɎg�p���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACSeparateTableManager
 * @see ACSeparateTable
 */
public interface ACSeparateTablar {
    /**
     * �w��̏C����ɑΉ�����e�[�u�����쐬���܂��B
     * 
     * @param dbm DBManager
     * @param modifier �C����
     * @throws SQLException ������O
     * @return �w��̏C����ɑΉ�����e�[�u���̍쐬�ɐ���������
     */
    boolean createTable(ACDBManager dbm, int modifier) throws SQLException;

    /**
     * �w��̏C����ɑΉ�����e�[�u�������݂��邩 ��Ԃ��܂��B
     * 
     * @param dbm DBManager
     * @param modifier �C����
     * @throws SQLException ������O
     * @return �w��̏C����ɑΉ�����e�[�u�������݂��邩
     */
    boolean isExistTable(ACDBManager dbm, int modifier) throws SQLException;

    /**
     * �C�����t�������e�[�u������Ԃ��܂��B
     * 
     * @param modifier �C����
     * @return �C�����t�������e�[�u����
     */
    String getModifiedTableName(int modifier);
}
