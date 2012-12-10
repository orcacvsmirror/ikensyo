/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.HashMap;


/**
 * VRDateParser���g�p����j�Փ���`�N���X�ł��B
 * <p>
 * �j�Փ�������ID�̑��ɁA���R�ɒ�`�\�ȃp�����^HashMap��ێ����Ă��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 */
public class VRDateParserHolyday {
    private String id;

    private HashMap parameters;

    /**
     * �R���X�g���N�^
     */
    public VRDateParserHolyday() {
        parameters = new HashMap();
    }

    /**
     * �R���X�g���N�^
     */
    public VRDateParserHolyday(String id) {
        this();
        setId(id);
    }

    /**
     * �p�����^�����ׂč폜���܂��B
     */
    public void clearParameter() {
        getParameters().clear();
    }

    /**
     * �����̃p�����^�L�[�ł��邩��Ԃ��܂��B
     * 
     * @param key �L�[
     * @return �����̃p�����^�L�[�ł��邩
     */
    public boolean containsParameter(String key) {
        return getParameters().containsKey(key);
    }

    /**
     * �j���� ��Ԃ��܂��B
     * 
     * @return �j����
     */
    public String getId() {
        return id;
    }

    /**
     * �L�[�ɑΉ�����p�����^��Ԃ��܂��B
     * 
     * @param key �L�[
     * @return �p�����^�B�Y�����Ȃ����null
     */
    public String getParameter(String key) {
        return (String) getParameters().get(key);
    }

    /**
     * �p�����^�W�� ��Ԃ��܂��B
     * 
     * @return �p�����^�W��
     */
    public HashMap getParameters() {
        return parameters;
    }

    /**
     * �L�[�ɊY������p�����^���폜���܂��B
     * 
     * @param key �L�[
     */
    public void removeParameter(String key) {
        getParameters().remove(key);
    }

    /**
     * �j���� ��ݒ肵�܂��B
     * 
     * @param id �j����
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * �p�����^�W�� ��ݒ肵�܂��B
     * 
     * @param parameters �p�����^�W��
     */
    public void setParametars(HashMap parameters) {
        this.parameters = parameters;
    }

    /**
     * �p�����^��ݒ肵�܂��B <br />
     * �����̃L�[���w�肵���ꍇ�A�l���㏑�����܂��B
     * 
     * @param key �L�[
     * @param val �l
     */
    public void setParameter(String key, String val) {
        getParameters().put(key, val);
    }

}