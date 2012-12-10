package jp.nichicom.ac.sql;

/**
 * �p�b�V�u�`�F�b�N�p�̃^�X�N�N���X�ł��B
 * <p>
 * �ǂ̂悤�ȃp�b�V�u�`�F�b�N���s�Ȃ������K�肵�܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACPassiveTask {
    /**
     * �폜��\���p�b�V�u�`�F�b�N�^�C�v�萔�ł��B
     */
    public static final int PASSIVE_DELETE = 2;
    /**
     * �ǉ���\���p�b�V�u�`�F�b�N�^�C�v�萔�ł��B
     */
    public static final int PASSIVE_INSERT = 1;
    /**
     * �X�V��\���p�b�V�u�`�F�b�N�^�C�v�萔�ł��B
     */
    public static final int PASSIVE_UPDATE = 0;

    /**
     * �S����r��\���`�F�b�N�Ώۃ^�C�v�萔�ł��B
     */
    public static final int CHECK_TARGET_ALL = 0;
    /**
     * �w��s�ԍ��̒P����r��\���`�F�b�N�Ώۃ^�C�v�萔�ł��B
     */
    public static final int CHECK_TARGET_ROW = 1;
    /**
     * �w��f�[�^��L����s�̒P����r��\���`�F�b�N�Ώۃ^�C�v�萔�ł��B
     */
    public static final int CHECK_TARGET_VALUE = 2;

    protected int checkTargetType;
    protected ACPassiveKey key;
    protected int targetRow;
    protected int commandType;
    protected String targetBindPath;
    protected Object targetValue;

    /**
     * �`�F�b�N�Ώۃ^�C�v ��Ԃ��܂��B
     * @return �`�F�b�N�Ώۃ^�C�v
     */
    public int getCheckTargetType() {
        return checkTargetType;
    }

    /**
     * �`�F�b�N�Ώۃ^�C�v ��ݒ肵�܂��B
     * @param targetType �`�F�b�N�Ώۃ^�C�v
     */
    public void setCheckTargetType(int targetType) {
        this.checkTargetType = targetType;
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �S���̃p�b�V�u�^�X�N�Ƃ��ď��������܂��B
     * </p>
     * 
     * @param commandType �p�b�V�u�`�F�b�N���
     * @param key ��r�L�[
     */
    public ACPassiveTask(int commandType, ACPassiveKey key) {
        setCommandType(commandType);
        setKey(key);
        setTargetRow(0);
        setCheckTargetType(CHECK_TARGET_ALL);
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �Ώۍs���w�肵���P���̃p�b�V�u�^�X�N�Ƃ��ď��������܂��B
     * </p>
     * 
     * @param commandType �p�b�V�u�`�F�b�N���
     * @param key ��r�L�[
     * @param targetRow �Ώۍs�ԍ�
     */
    public ACPassiveTask(int commandType, ACPassiveKey key, int targetRow) {
        setCommandType(commandType);
        setKey(key);
        setTargetRow(targetRow);
        setCheckTargetType(CHECK_TARGET_ROW);
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �l����ΏۋƂ����߂�P���̃p�b�V�u�^�X�N�Ƃ��ď��������܂��B
     * </p>
     * 
     * @param commandType �p�b�V�u�`�F�b�N���
     * @param key ��r�L�[
     * @param targetBindPath �ΏۋƂ����߂邽�߂̒l��
     * @param targetValue �ΏۋƂ����߂邽�߂̒l
     */
    public ACPassiveTask(int commandType, ACPassiveKey key, String targetBindPath,
            Object targetValue) {
        setCommandType(commandType);
        setKey(key);
        setTargetBindPath(targetBindPath);
        setTargetValue(targetValue);
        setCheckTargetType(CHECK_TARGET_VALUE);
    }

    /**
     * �p�b�V�u�L�[��Ԃ��܂��B
     * 
     * @return �p�b�V�u�L�[
     */
    public ACPassiveKey getKey() {
        return key;
    }

    /**
     * �Ώۍs��Ԃ��܂��B
     * 
     * @return �Ώۍs
     */
    public int getTargetRow() {
        return targetRow;
    }

    /**
     * �p�b�V�u�`�F�b�N�^�C�v��Ԃ��܂��B
     * 
     * @return �p�b�V�u�`�F�b�N�^�C�v
     */
    public int getCommandType() {
        return commandType;
    }

    /**
     * �p�b�V�u�L�[��ݒ肵�܂��B
     * 
     * @param key �p�b�V�u�L�[
     */
    public void setKey(ACPassiveKey key) {
        this.key = key;
    }

    /**
     * �Ώۍs��ݒ肵�܂��B
     * 
     * @param row �Ώۍs
     */
    public void setTargetRow(int row) {
        this.targetRow = row;
    }

    /**
     * �p�b�V�u�`�F�b�N�^�C�v��ݒ肵�܂��B
     * 
     * @param type �p�b�V�u�`�F�b�N�^�C�v
     */
    public void setCommandType(int type) {
        this.commandType = type;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("([type=");
        switch (getCommandType()) {
        case PASSIVE_UPDATE:
            sb.append("PASSIVE_UPDATE");
            break;
        case PASSIVE_INSERT:
            sb.append("PASSIVE_INSERT");
            break;
        case PASSIVE_DELETE:
            sb.append("PASSIVE_DELETE");
            break;
        default:
            sb.append(getCommandType());
            break;
        }
        sb.append("][key=");
        sb.append(getKey().toString());
        switch(getCheckTargetType()){
        case CHECK_TARGET_ROW:
            // �s���w��
            sb.append("][row=");
            sb.append(getTargetRow());
            break;
        case CHECK_TARGET_VALUE:
            // �s�f�[�^�w��
            sb.append("][rowBindPath=");
            sb.append(getTargetBindPath());
            sb.append("][rowValue=");
            sb.append(getTargetValue());
            break;
        }
        sb.append("])");
        return sb.toString();
    }

    /**
     * �P���̃p�b�V�u�`�F�b�N�ɂ����āA�Ώۍs�����߂邽�߂̒l�� ��Ԃ��܂��B
     * 
     * @return �Ώۍs�����߂邽�߂̒l��
     */
    public String getTargetBindPath() {
        return targetBindPath;
    }

    /**
     * �Ώۍs�����߂邽�߂̒l�� ��ݒ肵�܂��B
     * 
     * @param targetBindPath �Ώۍs�����߂邽�߂̒l��
     */
    public void setTargetBindPath(String targetBindPath) {
        this.targetBindPath = targetBindPath;
    }

    /**
     * �P���̃p�b�V�u�`�F�b�N�ɂ����āA�Ώۍs�����߂邽�߂̒l ��Ԃ��܂��B
     * 
     * @return �Ώۍs�����߂邽�߂̒l
     */
    public Object getTargetValue() {
        return targetValue;
    }

    /**
     * �P���̃p�b�V�u�`�F�b�N�ɂ����āA�Ώۍs�����߂邽�߂̒l ��ݒ肵�܂��B
     * 
     * @param targetValue �Ώۍs�����߂邽�߂̒l
     */
    public void setTargetValue(Object targetValue) {
        this.targetValue = targetValue;
    }

}