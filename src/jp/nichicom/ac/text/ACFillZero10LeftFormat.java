package jp.nichicom.ac.text;

/**
 * 10���̍�0�l�߂��s�Ȃ��t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACFillCharFormat
 */

public class ACFillZero10LeftFormat extends ACFillCharFormat {
    protected static ACFillZero10LeftFormat singleton;

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected ACFillZero10LeftFormat() {
        super("0", 10);
    }

    /**
     * �C���X�^���X���擾���܂��B
     */
    public static ACFillZero10LeftFormat getInstance() {
        if (singleton == null) {
            singleton = new ACFillZero10LeftFormat();
        }
        return singleton;
    }
}
