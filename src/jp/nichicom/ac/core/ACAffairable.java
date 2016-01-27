package jp.nichicom.ac.core;

import java.awt.Component;

import jp.nichicom.vr.util.VRMap;

/**
 * ��Ղɂ���đJ�ډ\�ȋƖ��C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACAffairInfo
 */

public interface ACAffairable {

    /**
     * �Ɩ��������������������܂��B
     * <p>
     * ��ʑJ�ڒ���ɌĂ΂�܂��B
     * </p>
     * <p>
     * �O��ʂ���̃p�����[�^��<code>affair.getParameters()</code>�Ŏ擾���Ă��������B
     * </p>
     * 
     * @param affair �Ɩ����
     * @throws Exception ������O
     */
    void initAffair(ACAffairInfo affair) throws Exception;

    /**
     * �O��ʂɑJ�ڂ��Ă��ǂ�����Ԃ��܂��B
     * <p>
     * �O��ʂɎ󂯓n���p�����[�^�́A<code>parameters</code>�Ɋi�[���Ă��������B
     * </p>
     * 
     * @param parameters �p�����[�^
     * @throws Exception ������O
     * @return �O��ʂɑJ�ڂ��Ă��ǂ���
     */
    boolean canBack(VRMap parameters) throws Exception;

    /**
     * �V�X�e�����I�����Ă��ǂ�����Ԃ��܂��B
     * 
     * @return �V�X�e�����I�����Ă��ǂ���
     * @throws Exception ������O
     */
    boolean canClose() throws Exception;

    /**
     * �Ɩ��J�n���Ƀt�H�[�J�X���Z�b�g����R���|�[�l���g��Ԃ��܂��B
     * 
     * @return �Ɩ��J�n���Ƀt�H�[�J�X���Z�b�g����R���|�[�l���g
     */
    Component getFirstFocusComponent();
}
