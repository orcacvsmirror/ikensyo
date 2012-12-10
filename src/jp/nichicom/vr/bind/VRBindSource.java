/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindSourceEventListener;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;

/**
 * �o�C���h�p�̃f�[�^�C���^�[�t�F�[�X�ł��B
 * <p>
 * �o�C���h�@�\�ɂ�����f�[�^�i�[�N���X���������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindPathParser
 * @see VRBindEventListener
 * @see VRArrayList
 * @see VRHashMap
 */
public interface VRBindSource {

    /**
     * �o�C���h�\�[�X�C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener �o�C���h�\�[�X�C�x���g���X�i
     */
    public void addBindSourceEventListener(VRBindSourceEventListener listener);

    /**
     * �v�f��ǉ����܂��B
     * 
     * @param data �v�f
     */
    public void addData(Object data);

    /**
     * �ێ��v�f�W�����N���A���܂��B
     */
    public void clearData();

    /**
     * ��v�f��Ԃ��܂��B
     * 
     * @return �v�f
     */
    public Object getData();

    /**
     * �w��ԍ��̗v�f��Ԃ��܂��B
     * 
     * @param index �v�f�ԍ�
     * @return �v�f
     */
    public Object getData(int index);

    /**
     * �w��L�[�̗v�f��Ԃ��܂��B
     * 
     * @param key �v�f�L�[
     * @return �v�f
     */
    public Object getData(Object key);

    /**
     * �ێ��v�f����Ԃ��܂��B
     * 
     * @return �ێ��v�f��
     */
    public int getDataSize();

    /**
     * �o�C���h�\�[�X�C�x���g���X�i���폜���܂��B
     * 
     * @param listener �o�C���h�\�[�X�C�x���g���X�i
     */
    public void removeBindSourceEventListener(VRBindSourceEventListener listener);

    /**
     * �w��ԍ��̗v�f���폜���܂��B
     * 
     * @param index �폜�Ώۂ̗v�f�ԍ�
     */
    public void removeData(int index);

    /**
     * �w��L�[�̗v�f���폜���܂��B
     * 
     * @param key �폜�Ώۂ̗v�f�L�[
     */
    public void removeData(Object key);

    /**
     * �w��ԍ��̗v�f��ݒ肵�܂��B
     * 
     * @param index �v�f�ԍ�
     * @param data �v�f
     */
    public void setData(int index, Object data);

    /**
     * ��v�f��ݒ肵�܂��B
     * 
     * @param data �v�f
     */
    public void setData(Object data);

    /**
     * �w��L�[�̗v�f��ݒ肵�܂��B
     * 
     * @param key �v�f�L�[
     * @param data �v�f
     */
    public void setData(Object key, Object data);

}