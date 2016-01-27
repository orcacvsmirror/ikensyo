package jp.nichicom.ac.core;

import jp.nichicom.ac.pdf.ACChotarouXMLWriter;

/**
 * PDF�o�̓C���^�[�t�F�[�X�ł��B
 * <p>
 * ��Ƃ��āANCFrameEventProcessable�C���^�[�t�F�[�X�Ƃ��킹�ăV�X�e���C�x���g�����N���X�Ɏ������܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACFrameEventProcesser
 */
public interface ACPDFCreatable {
    /**
     * ����\�ȕ�����ɒu�����ĕԂ��܂��B
     * 
     * @param text �u���Ώ�
     * @return �u������
     */
    String toPrintable(String text);

    /**
     * �w��f�[�^��PDF���Œ�p�X�ɏo�͂��܂��B
     * 
     * @param data ���f�[�^
     * @throws Exception ������O
     * @return �o�̓t�@�C���p�X
     */
    String writePDF(ACChotarouXMLWriter data) throws Exception;

    /**
     * �o�͂��ꂽ�C�Ӄp�X��PDF�t�@�C�����J���܂��B
     * 
     * @param pdfPath PDF�t�@�C���̃p�X
     * @throws Exception
     */
    void openPDF(String pdfPath) throws Exception;

    /**
     * PDF�̏o�̓t�H���_��Ԃ��܂��B
     * 
     * @return PDF�̏o�̓t�H���_
     */
    String getPrintPDFDirectory();

    /**
     * ���[��`�̃t�H���_��Ԃ��܂��B
     * 
     * @return ���[��`�̃t�H���_
     */
    String getPrintFormatDirectory();

}
