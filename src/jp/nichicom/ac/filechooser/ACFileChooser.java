package jp.nichicom.ac.filechooser;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.core.ACFrame;

/**
 * �t�@�C���I���_�C�A���O�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */

public class ACFileChooser extends JFileChooser {
    /**
     * �J�����[�h��\���萔�ł��B
     */
    public static final int MODE_FILE_LOAD = 1;
    /**
     * �ۑ����[�h��\���萔�ł��B
     */
    public static final int MODE_FILE_SAVE = 2;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACFileChooser() {
    }

    /**
     * �����̃t�@�C���Ɋg���q��⊮�����t�@�C����Ԃ��܂��B
     * 
     * @param src �ϊ���
     * @return �ϊ�����
     */
    public File getFilePathWithExtension(File src) {

        if (src == null) {
            return null;
        }
        // �g���q��⊮
        return new File(src.getParent()
                + ACConstants.FILE_SEPARATOR
                + ((ACFileFilter) getFileFilter()).getFilePathWithExtension(src
                        .getName()));
    }

    /**
     * �t�@�C���I���_�C�A���O��\�����܂��B
     * <p>
     * ���ׂẴI�v�V�������w�肵��mode�ɉ������_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * mode�͈ȉ��̒l���Ƃ�܂��B<br />
     * MODE_FILE_LOAD : �J���_�C�A���O<br />
     * MODE_FILE_SAVE : �ۑ��_�C�A���O
     * </code>
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @param currentFile �����I���t�@�C��
     * @param title �_�C�A���O�^�C�g��
     * @param fileFilter �t�@�C���t�B���^
     * @param mode �_�C�A���O���[�h
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showDialog(String currentDirectory, String currentFile,
            String title, FileFilter fileFilter, int mode) {
        // ���̓`�F�b�N�E�����I���f�B���N�g��
        if (ACCommon.getInstance().isNullText(currentDirectory)) {
            currentDirectory = "";
        }
        // ���̓`�F�b�N�E�����I���t�@�C��
        if (ACCommon.getInstance().isNullText(currentFile)) {
            currentFile = "";
        }
        // ���̓`�F�b�N�E�^�C�g��
        if (ACCommon.getInstance().isNullText(title)) {
            switch (mode) {
            case MODE_FILE_LOAD:
                title = "�t�@�C�����J��";
                break;
            case MODE_FILE_SAVE:
                title = "���O��t���ĕۑ�";
                break;
            default:
                title = "�t�@�C����I��";
                break;
            }
        }

        // �_�C�A���O
        this.setFileFilter(fileFilter); // �t�B���^��ݒ�
        this.setCurrentDirectory(new File(currentDirectory)); // �����I���f�B���N�g��
        this.setSelectedFile(new File(currentFile)); // �����I���t�@�C����
        this.setDialogTitle(title); // �_�C�A���O�^�C�g��

        switch (mode) {
        case MODE_FILE_LOAD:
            if (this.showOpenDialog(ACFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                return this.getSelectedFile();
            }
            break;
        case MODE_FILE_SAVE:
            if (this.showSaveDialog(ACFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                return this.getSelectedFile();
            }
            break;
        default:
            break;
        }

        return null;
    }

    /**
     * �t�@�C���I���_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐�����mode�ɉ������_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * mode�͈ȉ��̒l���Ƃ�܂��B<br />
     * MODE_FILE_LOAD : �J���_�C�A���O<br />
     * MODE_FILE_SAVE : �ۑ��_�C�A���O
     * </code>
     * </p>
     * <p>
     * ���e����g���q��z��Ŏw�肵�܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showDialog("c:\\", "c:\\file.txt", "�^�C�g��", new String[]{"txt","bat"}, "�e�L�X�g�t�@�C��(*.txt, *.bat)", MODE_FILE_LOAD)
     * </code>
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @param currentFile �����I���t�@�C��
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtensions �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @param mode �_�C�A���O���[�h
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showDialog(String currentDirectory, String currentFile,
            String title, String[] fileExtensions,
            String fileExtensionsDescription, int mode) {
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(fileExtensions);
        filter.setDescription(fileExtensionsDescription);
        return showDialog(currentDirectory, currentFile, title, filter, mode);
    }

    /**
     * �t�@�C���I���_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐�����mode�ɉ������_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * mode�͈ȉ��̒l���Ƃ�܂��B<br />
     * MODE_FILE_LOAD : �J���_�C�A���O<br />
     * MODE_FILE_SAVE : �ۑ��_�C�A���O
     * </code>
     * </p>
     * <p>
     * ���e����g���q��z��Ŏw�肵�܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showDialog("�^�C�g��", new String[]{"txt","bat"}, "�e�L�X�g�t�@�C��(*.txt, *.bat)", MODE_FILE_LOAD)
     * </code>
     * </p>
     * 
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtensions �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @param mode �_�C�A���O���[�h
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showDialog(String title, String[] fileExtensions,
            String fileExtensionsDescription, int mode) {
        return showDialog("", "", title, fileExtensions,
                fileExtensionsDescription, mode);
    }

    /**
     * �t�@�C���ۑ��_�C�A���O��\�����܂��B
     * <p>
     * ���e����t�@�C����FileFilter�Ŏw�肵�܂��B
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @param currentFile �����I���t�@�C��
     * @param title �_�C�A���O�^�C�g��
     * @param fileFilter �t�@�C���t�B���^
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showSaveDialog(String currentDirectory, String currentFile,
            String title, FileFilter fileFilter) {
        return showDialog(currentDirectory, currentFile, title, fileFilter,
                MODE_FILE_SAVE);
    }

    /**
     * �t�@�C���ۑ��_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * ���e����g���q��z��Ŏw�肵�܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("c:\\", "c:\\file.txt", "�^�C�g��", new String[]{"txt","bat"}, "�e�L�X�g�t�@�C��(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @param currentFile �����I���t�@�C��
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtensions �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showSaveDialog(String currentDirectory, String currentFile,
            String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(currentDirectory, currentFile, title, fileExtensions,
                fileExtensionsDescription, MODE_FILE_SAVE);
    }

    /**
     * �t�@�C���ۑ��_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * ���e����g���q��z��Ŏw�肵�܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("�^�C�g��", new String[]{"txt","bat"}, "�e�L�X�g�t�@�C��(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtensions �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showSaveDialog(String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(title, fileExtensions, fileExtensionsDescription,
                MODE_FILE_SAVE);
    }

    /**
     * �t�@�C���ۑ��_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("�^�C�g��", "txt", "�e�L�X�g�t�@�C��(*.txt)")
     * </code>
     * </p>
     * 
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtension �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showSaveDialog(String title, String fileExtension,
            String fileExtensionsDescription) {
        return showDialog(title, new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_SAVE);
    }

    /**
     * �t�@�C���ۑ��_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("txt", "�e�L�X�g�t�@�C��(*.txt)")
     * </code>
     * </p>
     * <p>
     * �_�C�A���O�^�C�g�����u�t�@�C����ۑ��v�Ƃ��ĕ\�����܂��B
     * </p>
     * 
     * @param fileExtension �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showSaveDialog(String fileExtension,
            String fileExtensionsDescription) {
        return showDialog("�t�@�C����ۑ�", new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_SAVE);
    }

    /**
     * �t�@�C�����J���_�C�A���O��\�����܂��B
     * <p>
     * ���e����t�@�C����FileFilter�Ŏw�肵�܂��B
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @param currentFile �����I���t�@�C��
     * @param title �_�C�A���O�^�C�g��
     * @param fileFilter �t�@�C���t�B���^
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showOpenDialog(String currentDirectory, String currentFile,
            String title, FileFilter fileFilter) {
        return showDialog(currentDirectory, currentFile, title, fileFilter,
                MODE_FILE_LOAD);
    }

    /**
     * �t�@�C�����J���_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * ���e����g���q��z��Ŏw�肵�܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("c:\\", "c:\\file.txt", "�^�C�g��", new String[]{"txt", "bat"}, "�e�L�X�g�t�@�C��(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @param currentFile �����I���t�@�C��
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtensions �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showOpenDialog(String currentDirectory, String currentFile,
            String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(currentDirectory, currentFile, title, fileExtensions,
                fileExtensionsDescription, MODE_FILE_LOAD);
    }

    /**
     * �t�@�C�����J���_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * ���e����g���q��z��Ŏw�肵�܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("�^�C�g��", new String[]{"txt", "bat"}, "�e�L�X�g�t�@�C��(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtensions �t�B���^�W��
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showOpenDialog(String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(title, fileExtensions, fileExtensionsDescription,
                MODE_FILE_LOAD);
    }

    /**
     * �t�@�C�����J���_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("�^�C�g��", "txt", "�e�L�X�g�t�@�C��(*.txt)")
     * </code>
     * </p>
     * 
     * @param title �_�C�A���O�^�C�g��
     * @param fileExtension �t�B���^
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showOpenDialog(String title, String fileExtension,
            String fileExtensionsDescription) {
        return showDialog(title, new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_LOAD);
    }

    /**
     * �t�@�C�����J���_�C�A���O��\�����܂��B
     * <p>
     * ������FileFilter�𐶐����ă_�C�A���O���J���܂��B
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("txt", "�e�L�X�g�t�@�C��(*.txt)")
     * </code>
     * </p>
     * <p>
     * �_�C�A���O�^�C�g�����u�t�@�C�����J���v�Ƃ��ĕ\�����܂��B
     * </p>
     * 
     * @param fileExtension �t�B���^
     * @param fileExtensionsDescription �t�@�C���t�B���^�̐���
     * @return �w�肳�ꂽ�t�@�C��
     */
    public File showOpenDialog(String fileExtension,
            String fileExtensionsDescription) {
        return showDialog("�t�@�C�����J��", new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_LOAD);
    }

    /**
     * �t�H���_���J���_�C�A���O��\�����܂��B
     * <p>
     * <code>
     * ex:<br />
     * showDirectorySelectDaialog("C:\\")
     * </code>
     * </p>
     * <p>
     * �_�C�A���O�^�C�g�����u�t�H���_���J���v�Ƃ��ĕ\�����܂��B
     * </p>
     * 
     * @return �w�肳�ꂽ�t�H���_
     */
    public File showDirectorySelectDaialog() {
        return showDirectorySelectDaialog(null, null);
    }

    /**
     * �t�H���_���J���_�C�A���O��\�����܂��B
     * <p>
     * <code>
     * ex:<br />
     * showDirectorySelectDaialog("C:\\")
     * </code>
     * </p>
     * <p>
     * �_�C�A���O�^�C�g�����u�t�H���_���J���v�Ƃ��ĕ\�����܂��B
     * </p>
     * 
     * @param currentDirectory �����\���t�H���_
     * @return �w�肳�ꂽ�t�H���_
     */
    public File showDirectorySelectDaialog(String currentDirectory) {
        return showDirectorySelectDaialog("�t�H���_���J��", currentDirectory);
    }

    /**
     * �t�H���_���J���_�C�A���O��\�����܂��B
     * <p>
     * <code>
     * ex:<br />
     * showDirectorySelectDaialog("�t�H���_���J��", "C:\\")
     * </code>
     * </p>
     * 
     * @param title �_�C�A���O�^�C�g��
     * @param currentDirectory �����\���t�H���_
     * @return �w�肳�ꂽ�t�H���_
     */
    public File showDirectorySelectDaialog(String title, String currentDirectory) {
        if ((title != null)&&(!"".equals(title))) {
            title = "�t�H���_���J��";
        }
        this.setDialogTitle(title); // �_�C�A���O�^�C�g��
        if ((currentDirectory != null)&&(!"".equals(currentDirectory))) {
            this.setCurrentDirectory(new File(currentDirectory)); // �����I���f�B���N�g��
        }
        setFileSelectionMode(ACFileChooser.DIRECTORIES_ONLY);

        if (this.showOpenDialog(ACFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            return this.getSelectedFile();
        }
        return null;
    }
}
