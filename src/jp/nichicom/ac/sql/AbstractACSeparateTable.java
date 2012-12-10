package jp.nichicom.ac.sql;

/**
 * ��������������e�[�u����`�ł��B
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
 * @see ACSeparateTablar
 */
public abstract class AbstractACSeparateTable implements ACSeparateTablar {
    private String baseTableName;

    /**
     * �R���X�g���N�^�ł��B
     */
    public AbstractACSeparateTable() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param baseTableName �C������܂܂Ȃ��Ώۃe�[�u����
     */
    public AbstractACSeparateTable(String baseTableName) {
        super();
        setBaseTableName(baseTableName);
    }

    /**
     * �C������܂܂Ȃ��Ώۃe�[�u���� ��Ԃ��܂��B
     * 
     * @return �C������܂܂Ȃ��Ώۃe�[�u����
     */
    public String getBaseTableName() {
        return baseTableName;
    }

    /**
     * �C������܂܂Ȃ��Ώۃe�[�u���� ��ݒ肵�܂��B
     * 
     * @param baseTableName �C������܂܂Ȃ��Ώۃe�[�u����
     */
    public void setBaseTableName(String baseTableName) {
        this.baseTableName = baseTableName;
    }

    /**
     * �C�����t�������e�[�u������Ԃ��܂��B
     * <p>
     * ���N���X�ł̓T�t�B�b�N�X�\�L�ƂȂ邽�߁A�ύX����ꍇ��override���g�p���܂��B
     * </p>
     * 
     * @param modifier �C����
     * @return �C�����t�������e�[�u����
     */
    public String getModifiedTableName(int modifier) {
        return getBaseTableName() + "_" + modifier;
    }

}
