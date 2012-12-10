package jp.nichicom.ac.sql;

import java.sql.SQLException;

import jp.nichicom.vr.util.VRList;

/**
 * DB�A�N�Z�X�𒊏ۉ�����C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACDBManager {
    /**
     * �g�����U�N�V�������J�n���܂��B
     * 
     * @throws SQLException SQL��O
     */
    public void beginTransaction() throws SQLException;

    /**
     * SELECT ���̎��s���s���܂��B
     * 
     * @param sql ���s���� SELECT ��
     * @return ���s����
     * @throws SQLException SELECT �����s����O
     */
    public VRList executeQuery(String sql) throws SQLException;

    /**
     * UPDATE ���̎��s���s���܂��B
     * 
     * @param sql ���s���� SQL ��
     * @return int ���s�ɂ���ĉe�����󂯂���
     * @throws SQLException UPDATE ���s����O
     */
    public int executeUpdate(String sql) throws SQLException;

    /**
     * �g�����U�N�V���������[���o�b�N���܂��B
     * 
     * @throws SQLException SQL��O
     */
    public void rollbackTransaction() throws SQLException;

    /**
     * �g�����U�N�V�������R�~�b�g���܂��B
     * 
     * @throws SQLException SQL��O
     */
    void commitTransaction() throws SQLException;
}