package jp.nichicom.vr.util.adapter;

import jp.nichicom.vr.bind.VRBindSource;

/**
 * �o�C���h�\�[�X�����b�v����@�\�����������A�_�v�^�C���^�[�t�F�[�X�ł��B
 * <p>
 * <code>VRBindSource</code> �`���̃I�u�W�F�N�g�����b�v���ē��ߓI�Ɉ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRBindSource
 */
public interface VRBindSourceAdapter {
    /**
     * �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X��Ԃ��܂��B
     * 
     * @return �A�_�v�e�B�[
     */
    public VRBindSource getAdaptee();

}
