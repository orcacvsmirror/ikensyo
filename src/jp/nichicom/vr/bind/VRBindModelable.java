/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import java.text.ParseException;

import jp.nichicom.vr.bind.event.VRBindModelEventListener;

/**
 * ���f���o�C���h�@�\�C���^�[�t�F�[�X�ł��B
 * <p>
 * ���f���o�C���h�@�\�ɑΉ������R���g���[�����������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindPathParser
 * @see VRBindSource
 * @see VRBindModelEventListener
 */
public interface VRBindModelable {
    /**
     * ���f���o�C���h�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener ���f���o�C���h�C�x���g���X�i
     */
    public void addBindModelEventListener(VRBindModelEventListener listener);

    /**
     * �R���g���[���̃��f���̒l�����f���Q�Ɛ�\�[�X�ɓK�p���܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void applyModelSource() throws ParseException;

    /**
     * ���f���Q�Ɛ�\�[�X�̒l���R���g���[���̃��f���ɗ������݂܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void bindModelSource() throws ParseException;

    /**
     * �f�t�H���g�f�[�^���i�[�����\�[�X�C���X�^���X�𐶐����܂��B
     * 
     * @return �q�v�f�C���X�^���X
     */
    public Object createModelSource();

    /**
     * ���f���o�C���h�p�X��Ԃ��܂��B
     * 
     * @return ���f���o�C���h�p�X
     */
    public String getModelBindPath();

    /**
     * ���f���Q�Ɛ�\�[�X��Ԃ��܂��B
     * 
     * @return ���f���Q�Ɛ�\�[�X
     */
    public VRBindSource getModelSource();

    /**
     * ���f���o�C���h�C�x���g���X�i���폜���܂��B
     * 
     * @param listener ���f���o�C���h�C�x���g���X�i
     */
    public void removeBindModelEventListener(VRBindModelEventListener listener);


    /**
     * ���f���o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param modelBindPath ���f���o�C���h�p�X
     */
    public void setModelBindPath(String modelBindPath);
    
    /**
     * ���f���Q�Ɛ�\�[�X��ݒ肵�܂��B
     * 
     * @param modelSource ���f���Q�Ɛ�\�[�X
     */
    public void setModelSource(VRBindSource modelSource);
}