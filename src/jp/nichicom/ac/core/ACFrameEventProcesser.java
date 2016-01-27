package jp.nichicom.ac.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;

import jp.nichicom.ac.io.ACPropertyXML;
import jp.nichicom.ac.util.splash.ACSplashable;

/**
 * �V�X�e���C�x���g�����C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACFrameEventProcesser {
    /**
     * �X�v���b�V���_�C�A���O�𐶐����ĕԂ��܂��B
     * 
     * @return �X�v���b�V���_�C�A���O
     */
    public ACSplashable createSplash(String title) throws Exception;

    /**
     * �Ɩ��u���N���X��Ԃ��܂��B
     * 
     * @return �Ɩ��u���N���X
     */
    public ACAffairReplacable getAffairReplacer();

    /**
     * �R���e�i�̃f�t�H���g�w�i�F��Ԃ��܂��B
     * 
     * @return �R���e�i�̃f�t�H���g�w�i�F
     */
    public Color getContainerDefaultBackground();

    /**
     * �R���e�i�̃f�t�H���g�O�i�F��Ԃ��܂��B
     * 
     * @return �R���e�i�̃f�t�H���g�O�i�F
     */
    public Color getContainerDefaultForeground();

    /**
     * �R���e�i�̃G���[�w�i�F��Ԃ��܂��B
     * 
     * @return �R���e�i�̃G���[�w�i�F
     */
    public Color getContainerErrorBackground();

    /**
     * �R���e�i�̃G���[�O�i�F��Ԃ��܂��B
     * 
     * @return �R���e�i�̃G���[�O�i�F
     */
    public Color getContainerErrorForeground();

    /**
     * �R���e�i�̃��[�j���O�w�i�F��Ԃ��܂��B
     * 
     * @return �R���e�i�̃��[�j���O�w�i�F
     */
    public Color getContainerWarningBackground();

    /**
     * �R���e�i�̃��[�j���O�O�i�F��Ԃ��܂��B
     * 
     * @return �R���e�i�̃��[�j���O�O�i�F
     */
    public Color getContainerWarningForeground();

    /**
     * ���b�Z�[�W�{�b�N�X�̃f�t�H���g�E�B���h�E�^�C�g����Ԃ��܂��B
     * 
     * @return ���b�Z�[�W�{�b�N�X�̃f�t�H���g�E�B���h�E�^�C�g��
     */
    public String getDefaultMessageBoxTitle();

    /**
     * ������ʃT�C�Y��Ԃ��܂��B
     * 
     * @return ������ʃT�C�Y
     */
    public Dimension getDefaultWindowSize();

    /**
     * �ŏ���ʃT�C�Y��Ԃ��܂��B
     * 
     * @return �ŏ���ʃT�C�Y
     */
    public Dimension getMinimumWindowSize();

    /**
     * �ݒ�t�@�C����Ԃ��܂��B
     * 
     * @throws Exception ������O
     * @return �ݒ�t�@�C��
     */
    public ACPropertyXML getPropertyXML() throws Exception;

    /**
     * �V�X�e�������ݒ���������܂��B
     * 
     * @throws Exception ������O
     */
    public void initSystem() throws Exception;

    /**
     * ��O���b�Z�[�W��\�����܂��B
     * 
     * @param ex ��O
     */
    public void showExceptionMessage(Throwable ex);

    /**
     * ���C���t���[���p�̃A�C�R����Ԃ��܂��B
     * <p>
     * �ύX���Ȃ��ꍇ��null�������͋󕶎���Ԃ��܂��B
     * </p>
     * 
     * @return ���C���t���[���p�̃A�C�R��
     */
    public Image getFrameIcon();

}