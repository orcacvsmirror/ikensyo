/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;


import org.w3c.dom.Document;

/**
 * VRDateParser���g�p������`�ւ̃A�N�Z�X�h�L�������g�C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see Document
 * @see VRDateParser
 */
public interface VRDateParserDocumentCreatable {

    /**
     * XML�����I�u�W�F�N�g���擾���܂��B
     * 
     * @return XML�����I�u�W�F�N�g
     */
    public Document getDefinedDocument();
}