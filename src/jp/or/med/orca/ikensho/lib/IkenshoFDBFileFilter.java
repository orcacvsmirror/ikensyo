package jp.or.med.orca.ikensho.lib;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class IkenshoFDBFileFilter extends FileFilter {
    private final String[] fileExtensions = {"old", "fdb"};

    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }

            String ext = getFileExtension(file);
            if (ext != null) {
                for (int i=0; i<fileExtensions.length; i++) {
                    if ((ext.equals(fileExtensions[i]))) {
                        return true;
                    }
                }
            }
        }
        return false; //�ǂ�ɂ����Ă͂܂�Ȃ����false
    }

    /**
     * �t�B���^�̐�������Ԃ�
     * @return String
     */
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append("�o�b�N�A�b�v�t�@�C��(");
        for(int i=0; i<fileExtensions.length; i++) {
            sb.append("*." + fileExtensions[i]);
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * �t�@�C���̊g���q���������ŕԂ��B�擾�ł��Ȃ����null��Ԃ��B
     * @param �t�@�C��  File�̃C���X�^���X
     */
    public String getFileExtension(File file) {
        if (file == null) {
            return null;
        }

        String fileNm = file.getName();
        int i = fileNm.lastIndexOf('.'); //�Ō�̃s���I�h�ʒu���擾
        if (i == -1) {
            return null;
        }

        if ( (i > 0) && (i < (fileNm.length() - 1))) {
            return fileNm.substring(i + 1).toLowerCase(); //�Ō�̃s���I�h����̕�������������ŕԂ�
        }
        else {
            return null;
        }
    }

    /**
     * �g���q�t���t�@�C���t�@�C�������擾���܂��B
     * @param fileName �t�@�C����
     * @param defaultExtension �g���q���t���Ă��Ȃ��ꍇ�A�g���q���̉��Ԗڂ̊g���q��t�^���邩
     * @param extensions �g���q���
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName, int defaultExtensionIndex, String[] extensions) {
        if (fileName == null) {
            return "";
        }

        int pos = fileName.lastIndexOf('.'); //�Ō�̃s���I�h�ʒu���擾
        if (pos == -1) {
            //�g���q��ǉ����ĕԂ�
            return fileName + "." + extensions[defaultExtensionIndex];
        }

        if ( (pos > 0) && (pos < (fileName.length() - 1))) {
            String ext = fileName.substring(pos + 1).toLowerCase(); //�Ō�̃s���I�h����̕�������������ŕԂ�
            for (int i=0; i<fileExtensions.length; i++) {
                if (ext.equals(fileExtensions[i])) {
                    return fileName;
                }
            }
            return fileName + "." + extensions[defaultExtensionIndex];
        }
        else {
            return "";
        }
    }

    /**
     * �g���q�t���t�@�C���t�@�C�������擾���܂��B
     * @param fileName �t�@�C����
     * @param defaultExtension �g���q���t���Ă��Ȃ��ꍇ�A�g���q���̉��Ԗڂ̊g���q��t�^���邩
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName, int defaultExtensionIndex) {
        return getFilePathWithExtension(fileName, defaultExtensionIndex, fileExtensions);
    }

    /**
     * �g���q�t���t�@�C���t�@�C�������擾���܂��B
     * @param fileName �t�@�C����
     * @return �g���q�t���t�@�C����
     */
    public String getFilePathWithExtension(String fileName) {
        return getFilePathWithExtension(fileName, 0, fileExtensions);
    }

    /**
     * �f�t�H���g�̊g���q���擾���܂��B
     * @return �f�t�H���g�̊g���q(�u.�v�͊܂܂Ȃ�)
     */
    public String getDefaultFileExtension() {
        return fileExtensions[0];
    }
}
