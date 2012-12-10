package jp.nichicom.ac.container;

import jp.nichicom.vr.component.VRContainar;


/**
 * �q�̗L����Ԃ�A������R���e�i�C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRContainar
 */
public interface ACChildEnabledFollowable extends VRContainar {
    /**
     * �p�l����Enabled�ɘA�����ē���ڂ�Enabled��ݒ肷�邩 ��Ԃ��܂��B
     * 
     * @return �p�l����Enabled�ɘA�����ē���ڂ�Enabled��ݒ肷�邩
     */
    public boolean isFollowChildEnabled();
}
