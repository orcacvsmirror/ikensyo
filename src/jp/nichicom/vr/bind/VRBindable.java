/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import java.text.ParseException;

import jp.nichicom.vr.bind.event.VRBindEventListener;

/**
 * �o�C���h�@�\�C���^�[�t�F�[�X�ł��B
 * <p>
 * �o�C���h�@�\�ɑΉ������R���g���[�����������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindPathParser
 * @see VRBindSource
 * @see VRBindEventListener
 */
public interface VRBindable {
    /**
     * �o�C���h�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �o�C���h�C�x���g���X�i
     */
    public void addBindEventListener(VRBindEventListener listener);

    /**
     * �R���g���[���̒l���Q�Ɛ�\�[�X�ɓK�p���܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void applySource() throws ParseException;

    /**
     * �Q�Ɛ�\�[�X�̒l���R���g���[���ɗ������݂܂��B
     * 
     * @throws ParseException ��͗�O
     */
    public void bindSource() throws ParseException;

    /**
     * �f�t�H���g�f�[�^���i�[�����\�[�X�C���X�^���X�𐶐����܂��B
     * 
     * @return �q�v�f�C���X�^���X
     */
    public Object createSource();

    /**
     * �o�C���h�p�X��Ԃ��܂��B
     * 
     * @return �o�C���h�p�X
     */
    public String getBindPath();

    /**
     * �Q�Ɛ�\�[�X��Ԃ��܂��B
     * 
     * @return �Q�Ɛ�\�[�X
     */
    public VRBindSource getSource();

    /**
     * �����K�p���邩��Ԃ��܂��B
     * 
     * @return �����K�p���邩
     */
    public boolean isAutoApplySource();

    /**
     * �o�C���h�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �o�C���h�C�x���g���X�i
     */
    public void removeBindEventListener(VRBindEventListener listener);

    /**
     * �����K�p���邩��ݒ肵�܂��B
     * 
     * @param autoApplySource �����K�p���邩
     */
    public void setAutoApplySource(boolean autoApplySource);

    /**
     * �o�C���h�p�X��ݒ肵�܂��B
     * 
     * @param bindPath �o�C���h�p�X
     */
    public void setBindPath(String bindPath);

    /**
     * �Q�Ɛ�\�[�X��ݒ肵�܂��B
     * 
     * @param source �Q�Ɛ�\�[�X
     */
    public void setSource(VRBindSource source);
}