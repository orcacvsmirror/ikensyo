package jp.nichicom.ac.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * �t�@�C���̃T�t�B�b�N�X�Ŕ��f����t�@�C�����t�B���^�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/07/09
 */
public class ACSuffixFilenameFilter implements FilenameFilter {

    private String[] suffixes;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACSuffixFilenameFilter() {
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param suffix ���e����T�t�B�b�N�X
     */
    public ACSuffixFilenameFilter(String suffix) {
        this(new String[] { suffix });
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param suffixes ���e����T�t�B�b�N�X�z��
     */
    public ACSuffixFilenameFilter(String[] suffixes) {
        setSuffixes(suffixes);
    }

    /**
     * ���e����T�t�B�b�N�X�z�� ��Ԃ��܂��B
     * 
     * @return ���e����T�t�B�b�N�X�z��
     */
    public String[] getSuffixes() {
        return suffixes;
    }

    /**
     * ���e����T�t�B�b�N�X�z�� ��ݒ肵�܂��B
     * 
     * @param suffixes ���e����T�t�B�b�N�X�z��
     */
    public void setSuffixes(String[] suffixes) {
        this.suffixes = suffixes;
    }

    public boolean accept(File dir, String name) {
        String[] s = getSuffixes();
        if (s != null) {
            int end = s.length;
            for (int i = 0; i < end; i++) {
                if (name.endsWith(s[i])) {
                    return true;
                }
            }
        }
        return false;
    }

}
