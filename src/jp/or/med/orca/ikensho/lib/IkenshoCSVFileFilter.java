/*
 * Project code name "ORCA"
 * �厡��ӌ����쐬�\�t�g ITACHI�iJMA IKENSYO software�j
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * �A�v��: ITACHI
 * �J����: �琐��
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �琐�� �V�K�쐬
 * �X�V��: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class IkenshoCSVFileFilter extends FileFilter {
    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }

            String ext = getFileExtension(file);
            if (ext.equals("csv")) {
                return true;
            }
        }
        return false; //�ǂ�ɂ����Ă͂܂�Ȃ����false
    }

    /**
     * �t�B���^�̐�������Ԃ�
     * @return String
     */
    public String getDescription() {
        return "CSV�t�@�C��(*.csv)";
    }

    /**
     * �t�@�C���̊g���q���������ŕԂ��B�擾�ł��Ȃ����null��Ԃ��B
     * @param file File
     * @return String
     */
    public String getFileExtension(File file) {
        if (file == null) {
            return "";
        }

        String fileNm = file.getName();
        int i = fileNm.lastIndexOf('.'); //�Ō�̃s���I�h�ʒu���擾
        if (i == -1) {
            return "";
        }

        if ( (i > 0) && (i < (fileNm.length() - 1))) {
            return fileNm.substring(i + 1).toLowerCase(); //�Ō�̃s���I�h����̕�������������ŕԂ�
        }
        else {
            return "";
        }
    }

    /**
     * �g���q�t���t�@�C���t�@�C�������擾���܂��B
     * @param fileName �t�@�C����
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        int pos = fileName.lastIndexOf('.'); //�Ō�̃s���I�h�ʒu���擾
        if (pos == -1) {
            //�g���q��ǉ����ĕԂ�
            return fileName + ".csv";
        }

        if ( (pos > 0) && (pos < (fileName.length() - 1))) {
            String ext = fileName.substring(pos + 1).toLowerCase(); //�Ō�̃s���I�h����̕�������������ŕԂ�
            if (ext.equals("csv")) {
                return fileName;
            }
            return fileName + ".csv";
        }
        else {
            return "";
        }
    }

    /**
     * �f�t�H���g�̊g���q���擾���܂��B
     * @return �f�t�H���g�̊g���q(�u.�v�͊܂܂Ȃ�)
     */
    public String getDefaultFileExtension() {
        return "csv";
    }
}
