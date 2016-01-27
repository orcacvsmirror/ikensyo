package jp.nichicom.ac.core.version;

import java.util.EventListener;

import jp.nichicom.ac.util.splash.ACSplashable;
/**
 * �o�[�W�������̍��ٕ␳�C�x���g���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/03/28
 */
public interface ACVersionAdjustmentListener extends EventListener{
    /**
     * �L�[�ɑΉ������o�[�W��������Ԃ��܂��B
     * @param key �L�[
     * @return �o�[�W�������
     */
    public String getVersion(String key);
    /**
     * �L�[�ɑΉ������o�[�W��������ݒ肵�܂��B
     * @param key �L�[
     * @param value �o�[�W�������
     * @return �ݒ��F�߂邩
     */
    public boolean setVersion(String key, String value);
    /**
     * �i���\���p�̃X�v���b�V����Ԃ��܂��B
     * @return �i���\���p�̃X�v���b�V��
     */
    public ACSplashable getProgressSplash();
}
