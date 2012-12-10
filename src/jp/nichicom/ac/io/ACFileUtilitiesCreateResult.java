package jp.nichicom.ac.io;

import java.io.File;

/**
 * �t�@�C���֘A�̔ėp���\�b�h���W�߂��N���X�ɂ�����A�������s���ʂł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/04/14
 */
public class ACFileUtilitiesCreateResult {
    /**
     * �t�H���_���쐬�s�\�ł��邱�Ƃ�����킷�쐬���s���ʒ萔�ł��B
     */
    public static final int ERROR_OF_DIRECTORY_CREATE = 1;
    /**
     * �t�@�C�����쐬�s�\�ł��邱�Ƃ�����킷�쐬���s���ʒ萔�ł��B
     */
    public static final int ERROR_OF_FILE_CREATE = 2;
    /**
     * �w��̃t�H���_�Ńt�@�C�����쐬�\�ł��邱�Ƃ�����킷�쐬���s���ʒ萔�ł��B
     */
    public static final int SUCCESS_ON_TARGET_DIRECTORY = 0;
    /**
     * �\���t�H���_�Ńt�@�C�����쐬�\�ł��邱�Ƃ�����킷�쐬���s���ʒ萔�ł��B
     */
    public static final int SUCCESS_ON_SPARE_DIRECTORY = -1;

    private File file;
    private int result;
    /**
     * �R���X�g���N�^�ł��B
     */
    public ACFileUtilitiesCreateResult() {

    }

    /**
     * �����\�ȃt�@�C���I�u�W�F�N�g ��Ԃ��܂��B
     * 
     * @return �����\�ȃt�@�C���I�u�W�F�N�g
     */
    public File getFile() {
        return file;
    }

    /**
     * �������s���� ��Ԃ��܂��B
     * 
     * @return �������s����
     */
    public int getResult() {
        return result;
    }

    /**
     * �����\�ȃt�@�C���I�u�W�F�N�g ��ݒ肵�܂��B
     * 
     * @param file �����\�ȃt�@�C���I�u�W�F�N�g
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * �������s���� ��ݒ肵�܂��B
     * 
     * @param result �������s����
     */
    public void setResult(int result) {
        this.result = result;
    }
    /**
     * �t�@�C���쐬�\����Ԃ��܂��B
     * @return �t�@�C���쐬�\��
     */
    public boolean isSuccess(){
        switch(getResult()){
        case SUCCESS_ON_TARGET_DIRECTORY:
        case SUCCESS_ON_SPARE_DIRECTORY:
            return true;
        }
        return false;
    }
}
