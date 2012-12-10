/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Locale;


/**
 * VRDateParser���g�p�����P�[���N���X�ł��B
 * <p>
 * ���P�[�����ƂɈقȂ�j�Փ���`��ێ����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 * @see VRDateParserHolydayTerm
 */
public class VRDateParserLocale {
    private ArrayList eras;

    private VRDateParserHolydays holydays;

    private String id;

    private Locale locale;

    /**
     * �R���X�g���N�^
     * 
     * @param id ����
     * @param language ����
     * @param country ��
     * @param variant �⏕
     */
    public VRDateParserLocale(String id, String language, String country,
            String variant) {
        this.id = id;
        this.locale = new Locale(language, country, variant);
        this.eras = new ArrayList();
        this.holydays = new VRDateParserHolydays();

    }

    /**
     * ������`��ǉ����܂��B
     * 
     * @param era ������`
     */
    public void addEra(VRDateParserEra era) {
        this.getEras().add(era);
    }

    /**
     * ������`�W����Ԃ��܂��B
     * 
     * @return ������`�W��
     */
    public ArrayList getEras() {
        return eras;
    }

    /**
     * �j����`��� ��Ԃ��܂��B
     * 
     * @return �j����`���
     */
    public VRDateParserHolydays getHolydays() {
        return holydays;
    }

    /**
     * ���P�[�����̂�Ԃ��܂��B
     * 
     * @return ���P�[������
     */
    public String getId() {
        return id;
    }

    /**
     * ���P�[����Ԃ��܂��B
     * 
     * @return ���P�[��
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * ������`�W����ݒ肵�܂��B
     * 
     * @param eras ������`�W��
     */
    public void setEras(ArrayList eras) {
        this.eras = eras;
    }

    /**
     * �j����`��� ��ݒ肵�܂��B
     * 
     * @param holydays �j����`���
     */
    public void setHolydays(VRDateParserHolydays holydays) {
        this.holydays = holydays;
    }

    /**
     * ���P�[�����̂�ݒ肵�܂��B
     * 
     * @param id ���P�[������
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * ���P�[����ݒ肵�܂��B
     * 
     * @param locale ���P�[��
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

}