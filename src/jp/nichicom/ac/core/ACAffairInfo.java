package jp.nichicom.ac.core;

import jp.nichicom.vr.util.VRMap;

/**
 * �Ɩ����ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACAffairInfo {
    protected String className;
    protected VRMap parameters;
    protected String title;
    protected boolean splashed;

    /**
     * �Ɩ���ʃ^�C�g�� ��Ԃ��܂��B
     * 
     * @return �Ɩ���ʃ^�C�g��
     */
    public String getTitle() {
        return title;
    }

    /**
     * �Ɩ���ʃ^�C�g�� ��ݒ肵�܂��B
     * 
     * @param title �Ɩ���ʃ^�C�g��
     */
    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * �p�b�P�[�W���܂ދƖ��N���X�� ��Ԃ��܂��B
     * 
     * @return �p�b�P�[�W���܂ދƖ��N���X��
     */
    public String getClassName() {
        return className;
    }

    /**
     * �p�b�P�[�W���܂ދƖ��N���X�� ��ݒ肵�܂��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     */
    protected void setClassName(String className) {
        this.className = className;
    }

    /**
     * ��ʑJ�ڂɂ�����J�ڃp�����[�^ ��Ԃ��܂��B
     * 
     * @return ��ʑJ�ڂɂ�����J�ڃp�����[�^
     */
    public VRMap getParameters() {
        return parameters;
    }

    /**
     * ��ʑJ�ڂɂ�����J�ڃp�����[�^ ��ݒ肵�܂��B
     * 
     * @param parameters ��ʑJ�ڂɂ�����J�ڃp�����[�^
     */
    public void setParameters(VRMap parameters) {
        this.parameters = parameters;
    }

    /**
     * �J�ڎ��ɃX�v���b�V����ʂ�\�����邩 ��Ԃ��܂��B
     * 
     * @return �J�ڎ��ɃX�v���b�V����ʂ�\�����邩
     */
    public boolean isSplashed() {
        return splashed;
    }

    /**
     * �J�ڎ��ɃX�v���b�V����ʂ�\�����邩 ��ݒ肵�܂��B
     * 
     * @param splashed �J�ڎ��ɃX�v���b�V����ʂ�\�����邩
     */
    public void setSplashed(boolean splashed) {
        this.splashed = splashed;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     */
    public ACAffairInfo(String className) {
        this(className, null, "", false);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     * @param parameters ��ʑJ�ڂɂ�����J�ڃp�����[�^
     */
    public ACAffairInfo(String className, VRMap parameters) {
        this(className, parameters, "", false);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     * @param parameters ��ʑJ�ڂɂ�����J�ڃp�����[�^
     * @param title �^�C�g��
     */
    public ACAffairInfo(String className, VRMap parameters, String title) {
        this(className, parameters, title, false);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     * @param splashed ��ʑJ�ڎ��ɃX�v���b�V����ʂ�\�����邩
     */
    public ACAffairInfo(String className, boolean splashed) {
        this(className, null, "", splashed);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     * @param parameters ��ʑJ�ڂɂ�����J�ڃp�����[�^
     * @param splashed ��ʑJ�ڎ��ɃX�v���b�V����ʂ�\�����邩
     */
    public ACAffairInfo(String className, VRMap parameters, boolean splashed) {
        this(className, parameters, "", splashed);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param className �p�b�P�[�W���܂ދƖ��N���X��
     * @param parameters ��ʑJ�ڂɂ�����J�ڃp�����[�^
     * @param title �^�C�g��
     * @param splashed ��ʑJ�ڎ��ɃX�v���b�V����ʂ�\�����邩
     */
    public ACAffairInfo(String className, VRMap parameters, String title,
            boolean splashed) {
        setClassName(className);
        setParameters(parameters);
        setTitle(title);
        setSplashed(splashed);
    }
}
