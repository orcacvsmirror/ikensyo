package jp.nichicom.ac.filechooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * ���e����g���q�𕡐��w��\�ȃt�@�C���t�B���^�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */

public class ACFileFilter extends FileFilter {
    private String description;
    private String[] fileExtensions;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACFileFilter() {
    }

    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }

            String ext = getFileExtension(f);
            if (ext != null) {
                for (int i = 0; i < fileExtensions.length; i++) {
                    if ((ext.equals(fileExtensions[i]))) {
                        return true;
                    }
                }
            }
        }
        return false; // �ǂ�ɂ����Ă͂܂�Ȃ����false
    }

    /**
     * �f�t�H���g�̊g���q���擾���܂��B
     * 
     * @return �f�t�H���g�̊g���q(�u.�v�͊܂܂Ȃ�)
     */
    public String getDefaultFileExtension() {
        return fileExtensions[0];
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * ���e����g���q�z���Ԃ��܂��B
     * 
     * @return ���e����g���q�z��
     */
    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    /**
     * �g���q�t���t�@�C���t�@�C�������擾���܂��B
     * 
     * @param fileName �t�@�C����
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName) {
        return getFilePathWithExtension(fileName, 0, fileExtensions);
    }

    /**
     * �g���q�t���t�@�C���t�@�C�������擾���܂��B
     * 
     * @param fileName �t�@�C����
     * @param defaultExtensionIndex �g���q���t���Ă��Ȃ��ꍇ�A�g���q���̉��Ԗڂ̊g���q��t�^���邩
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName,
            int defaultExtensionIndex) {
        return getFilePathWithExtension(fileName, defaultExtensionIndex,
                fileExtensions);
    }

    /**
     * �g���q�t���t�@�C�������擾���܂��B
     * 
     * @param fileName �t�@�C����
     * @param defaultExtensionIndex �g���q���t���Ă��Ȃ��ꍇ�A�g���q���̉��Ԗڂ̊g���q��t�^���邩
     * @param extensions �g���q���
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName,
            int defaultExtensionIndex, String[] extensions) {
        if (fileName == null) {
            return "";
        }

        int pos = fileName.lastIndexOf('.'); // �Ō�̃s���I�h�ʒu���擾
        if (pos == -1) {
            // �g���q��ǉ����ĕԂ�
            return fileName + "." + extensions[defaultExtensionIndex];
        }

        if ((pos > 0) && (pos < (fileName.length() - 1))) {
            String ext = fileName.substring(pos + 1).toLowerCase(); // �Ō�̃s���I�h����̕�������������ŕԂ�
            for (int i = 0; i < fileExtensions.length; i++) {
                if (ext.equals(fileExtensions[i])) {
                    return fileName;
                }
            }
            return fileName + "." + extensions[defaultExtensionIndex];
        } else {
            return "";
        }
    }

    /**
     * �t�B���^�̐�����ݒ肵�܂��B
     * 
     * @param description �t�B���^�̐���
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * ���e����g���q�z���ݒ肵�܂��B
     * 
     * @param fileExtensions ���e����g���q�z��
     */
    public void setFileExtensions(String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    /**
     * �t�@�C���̊g���q���������ŕԂ��B�擾�ł��Ȃ����null��Ԃ��B
     * 
     * @param �t�@�C�� File�̃C���X�^���X
     */
    private String getFileExtension(File file) {
        if (file == null) {
            return null;
        }

        String fileNm = file.getName();
        int i = fileNm.lastIndexOf('.'); // �Ō�̃s���I�h�ʒu���擾
        if (i == -1) {
            return null;
        }

        if ((i > 0) && (i < (fileNm.length() - 1))) {
            return fileNm.substring(i + 1).toLowerCase(); // �Ō�̃s���I�h����̕�������������ŕԂ�
        } else {
            return null;
        }
    }
}
