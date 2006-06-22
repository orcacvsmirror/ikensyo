package jp.or.med.orca.ikensho.affair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.text.VRDateFormat;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoFDBFileFilter;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** <HEAD_IKENSYO> */
public class IkenshoOtherBackupRestore {
    private final int STATE_SUCCESS = 0;
    private final int STATE_CANCEL = 1;
    private final int STATE_ERROR = 2;

    private String dbServer;
    private String dbPath;

    /**
     * �R���X�g���N�^
     * @param dbServer DB�T�[�o��
     * @param dbPath DB�t�@�C���p�X
     */
    public IkenshoOtherBackupRestore(String dbServer, String dbPath) {
        this.dbServer = dbServer;
        this.dbPath = dbPath;
    }

    /**
     * DB�̃o�b�N�A�b�v�������s���܂��B�i�Ƃ�܂Ƃ߁j
     * @throws Exception
     */
    public void doBackUp() throws Exception {
        switch (dataBackUp()) {
            case STATE_SUCCESS:
                ACMessageBox.show(
                    "�f�[�^�̑ޔ�(�o�b�N�A�b�v)���I�����܂����B",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                break;

            case STATE_CANCEL:
                break;

            case STATE_ERROR:
                ACMessageBox.show(
                    "�f�[�^�̑ޔ�(�o�b�N�A�b�v)�Ɏ��s���܂����B",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                break;
        }
    }

    /**
     * DB�̃o�b�N�A�b�v�������s���܂��B�i�����j
     * @return int
     * @throws Exception
     */
    private int dataBackUp() throws Exception {
      //�T�[�o�`�F�b�N
      if (!checkLocalHost(dbServer)) {
        return STATE_CANCEL;
      }

      //�ޔ��t�@�C���擾
      File file = getBackUpFile();
      if (file == null) {
        return STATE_CANCEL;
      }

      //�o�b�N�A�b�v�J�n�m�FMSG
      int result = ACMessageBox.show(
        "�f�[�^�̑ޔ�(�o�b�N�A�b�v)�������J�n���܂��B\n���΂炭���Ԃ�������܂��B��낵���ł����H",
        ACMessageBox.BUTTON_OKCANCEL,
        ACMessageBox.ICON_QUESTION,
        ACMessageBox.FOCUS_OK);
      if (result == ACMessageBox.RESULT_CANCEL) {
        return STATE_CANCEL;
      }

      //�o�b�N�A�b�v����
      try {
        fileCpy(dbPath, file.getPath());
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return STATE_ERROR;
      }
      return STATE_SUCCESS;
    }

    /**
     * DB�̂�X�g�A�������s���܂��B(�Ƃ�܂Ƃ�)
     * @throws Exception
     */
    public boolean doRestore() throws Exception {
        switch (dataRestore()) {
            case STATE_SUCCESS:
                ACMessageBox.show(
                    "�f�[�^�̕���(���X�g�A)���I�����܂����B",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                return true;

            case STATE_CANCEL:
                break;

            case STATE_ERROR:
                ACMessageBox.show(
                    "�f�[�^�̕���(���X�g�A)�Ɏ��s���܂����B",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                break;
        }
        return false;
    }

    /**
     * DB�̂�X�g�A�������s���܂��B�i����)
     * @return int
     * @throws Exception
     */
    private int dataRestore() throws Exception {
        //�T�[�o�`�F�b�N
        if (!checkLocalHost(dbServer)) {
            return STATE_CANCEL;
        }

        //�������t�@�C�����擾
        File restoreFile = getRestoreFile();
        if (restoreFile == null) {
            return STATE_CANCEL;
        }

        //�ޔ��t�@�C�����擾
        File bkupFile = getBackUpFileForRestore(restoreFile);
        if (bkupFile == null) {
            return STATE_CANCEL;
        }

        //���X�g�A�J�n�m�FMSG
        int result = ACMessageBox.show(
            "�f�[�^�̕���(���X�g�A)�������J�n���܂��B\n���΂炭���Ԃ�������܂��B��낵���ł����H",
            ACMessageBox.BUTTON_OKCANCEL,
            ACMessageBox.ICON_QUESTION,
            ACMessageBox.FOCUS_OK);
        if (result == ACMessageBox.RESULT_CANCEL) {
            return STATE_CANCEL;
        }

        // ���X�g�A����
        try {
            // ��DB��ޔ�
            fileCpy(dbPath, bkupFile.getPath());

            // ���X�g�A
             fileCpy(restoreFile.getPath(), dbPath);
        } catch (Exception ex) {
            ex.printStackTrace();
            return STATE_ERROR;
        }
        return STATE_SUCCESS;
    }

    /**
     * �t�@�C���̃R�s�[���s���܂��B
     * @param orgFile �R�s�[���t�@�C���p�X
     * @param newFile �R�s�[��t�@�C���p�X
     * @throws Exception
     */
    private void fileCpy(String orgFile, String newFile) throws Exception {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(orgFile));
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(newFile));
        byte buf[] = new byte[256];
        int len;
        while ( (len = input.read(buf)) != -1) {
            output.write(buf, 0, len);
        }
        output.flush();
        output.close();
        input.close();
    }

    /**
     * DB�T�[�o��LocalHost���ǂ����`�F�b�N���܂��B
     * @param server �T�[�o��
     * @return boolean
     */
    private boolean checkLocalHost(String server) {
        if (!server.equals("localhost") &&
            !server.equals("127.0.0.1")) {
            ACMessageBox.show(
                "�ʂ̃R���s���[�^�̃f�[�^�x�[�X���g�p���Ă���ꍇ�́A���̋@�\�𗘗p���邱�Ƃ͂ł��܂���B",
                ACMessageBox.BUTTON_OK, ACMessageBox.ICON_INFOMATION);
            return false;
        }
        return true;
    }

    /**
     * �ޔ��t�@�C�����擾���܂��B
     * @return �ޔ��t�@�C��
     */
    private File getBackUpFile() {
        File file;
        boolean loopFlg;

        do {
            ACFileChooser fileChooser = new ACFileChooser();
//            fileChooser.setFileFilter(new IkenshoDBFileFilter());

            //���[�v�t���O�����Z�b�g
            loopFlg = false;

            //�t�@�C���I���_�C�A���O
            file = fileChooser.showSaveDialog(
                dbPath, getDefaultFileName("old"),
                "�f�[�^�x�[�X�̃o�b�N�A�b�v�t�@�C���̕ۑ��ꏊ���w�肵�ĉ������B",
                new IkenshoFDBFileFilter());

            //�L�����Z�����ꂽ�ꍇ�͏I��
            if (file == null) {
                return null;
            }

            //�g���q��⊮����
            file = new File(
                file.getParent(),
                ((IkenshoFDBFileFilter)fileChooser.getFileFilter()).getFilePathWithExtension(file.getName(), 0));

            
            // �ޔ�悪����DB�łȂ������`�F�b�N
            // 2006/02/03[Tozo Tanaka] : replace begin
            // if (file.getPath().toLowerCase().equals(dbPath.toLowerCase())) {
            if (file.getPath().toLowerCase().equals(
                    new File(dbPath).getPath().toLowerCase())) {
                // 2006/02/03[Tozo Tanaka] : replace end
                ACMessageBox.show("�����ȃp�X���ł��B\n"
                        + "(�o�b�N�A�b�v��t�@�C�������݂̃f�[�^�x�[�X�t�@�C���Ɠ����ł�)",
                        ACMessageBox.BUTTON_OK, ACMessageBox.ICON_EXCLAMATION);
                loopFlg = true; // �t�@�C���I������蒼��
                continue;
            }

            //�����t�@�C�����݃`�F�b�N�E�㏑���m�F
            if (file.exists()) {
                int result = ACMessageBox.show(
                    "�������O�̃t�@�C�������݂��Ă��܂��B\n�㏑�����Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_CANCEL) {
                    loopFlg = true; //�t�@�C���I������蒼��
                    continue;
                }
            }
        } while (loopFlg);

        return file;
    }

    /**
     * �������t�@�C�����擾���܂��B
     * @return �������t�@�C��
     */
    private File getRestoreFile() {
        File file;
        boolean loopFlg;
        do {
            ACFileChooser fileChooser = new ACFileChooser();
//            fileChooser.setFileFilter(new IkenshoDBFileFilter());

            //���[�v�t���O�����Z�b�g
            loopFlg = false;

            //�t�@�C���I���_�C�A���O
            file = fileChooser.showOpenDialog(
                dbPath, "",
                "�������̃f�[�^�x�[�X�t�@�C���̊i�[�ꏊ���w�肵�ĉ������B",
                new IkenshoFDBFileFilter());

            //�L�����Z�����ꂽ�ꍇ�͏I��
            if (file == null) {
                return null;
            }

            // �g���q��⊮����
            // 2006/12/03[Tozo Tanaka] : replace begin
            // file = new File(
            // file.getParent(),
            // ((IkenshoFDBFileFilter)fileChooser.getFileFilter()).getFilePathWithExtension(file.getName(),
            // 1));
            file = new File(file.getParent(), getFilePathWithExtension(file
                    .getName(), 1, fileExtensions));
            // 2006/12/03[Tozo Tanaka] : replace end

            // ������������DB�łȂ������`�F�b�N
            // 2006/02/03[Tozo Tanaka] : replace begin
            // if (file.getPath().toLowerCase().equals(dbPath.toLowerCase())) {
            if (file.getPath().toLowerCase().equals(
                    new File(dbPath).getPath().toLowerCase())) {
                // 2006/02/03[Tozo Tanaka] : replace end
                ACMessageBox.show("�����ȃp�X���ł��B\n"
                        + "(�������t�@�C�������݂̃f�[�^�x�[�X�t�@�C���Ɠ����ł�)",
                        ACMessageBox.BUTTON_OK, ACMessageBox.ICON_EXCLAMATION);
                loopFlg = true; // �t�@�C���I������蒼��
                continue;
            }

            //�t�@�C�����݃`�F�b�N
            if (!file.exists()) {
                //2006/12/03[Tozo Tanaka] : replace begin 
//                ACMessageBox.show(
//                    "�t�@�C�����w�肵�ĉ������B",
//                    ACMessageBox.BUTTON_OK,
//                    ACMessageBox.ICON_EXCLAMATION);
                ACMessageBox.show(
                        "Firebird��DB�t�@�C�����w�肵�ĉ������B",
                        ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                //2006/12/03[Tozo Tanaka] : replace end
                loopFlg = true; //�t�@�C���I������蒼��
                continue;
            }
        } while (loopFlg);

        return file;
    }

    private final String[] fileExtensions = {"old", "fdb"};
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
     * �������̌���DB�ޔ��t�@�C�����擾���܂��B
     * @param restoreFile File
     * @return File
     */
    private File getBackUpFileForRestore(File restoreFile) {
        File bkupFile = new File(dbPath);
        bkupFile = new File(restoreFile.getParent(), "ikenold.fdb");
        boolean loopFlg;
        int result;

        //��DB�ޔ��m�FMSG
        result = ACMessageBox.show(
            "�I�����ꂽ�t�@�C��(" + restoreFile.getName() + ")����S�f�[�^�̕��������s���܂��B\n" +
            "���݂̃f�[�^�͕ʃt�@�C��(" + bkupFile.getName() + ")�ɕۑ�����܂��B\n" +
            "���s���Ă�낵���ł����H",
            ACMessageBox.BUTTON_OKCANCEL,
            ACMessageBox.ICON_QUESTION,
            ACMessageBox.FOCUS_CANCEL);
        if (result == ACMessageBox.RESULT_CANCEL) {
            return null;
        }

        do {
            //���[�v�t���O�����Z�b�g
            loopFlg = false;

            //�����t�@�C�����݃`�F�b�N�E�㏑���m�F
            if (bkupFile.exists()) {
                result = ACMessageBox.show(
                    "���݂̃f�[�^�x�[�X�̕ۑ���ɓ����t�@�C�����̃t�@�C�������݂��Ă��܂��B\n" +
                    "�㏑�����Ă�낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_OK) {
                    return bkupFile;
                }
            }
            else {
                break;
            }

            //�ޔ��t�@�C�����擾
            boolean loopFlg2;
            do {
                ACFileChooser fileChooser = new ACFileChooser();
                fileChooser.setFileFilter(new IkenshoFDBFileFilter());

                //���[�v�t���O�����Z�b�g
                loopFlg = true;
                loopFlg2 = false;

                //�t�@�C���I���_�C�A���O
                bkupFile = fileChooser.showSaveDialog(
                    dbPath, getDefaultFileName("fdb"),
                    "���݃f�[�^�x�[�X�̕ۑ��ꏊ���w�肵�ĉ������B",
                    new IkenshoFDBFileFilter());

                //�L�����Z�����ꂽ�ꍇ�͏I��
                if (bkupFile == null) {
                    return null;
                }

                //�g���q��⊮����
                bkupFile = new File(
                    bkupFile.getParent(),
                    ((IkenshoFDBFileFilter)fileChooser.getFileFilter()).getFilePathWithExtension(bkupFile.getName(), 1));

                //�ޔ�悪����DB�łȂ������`�F�b�N
                if (bkupFile.getPath().toLowerCase().equals(dbPath.toLowerCase())) {
                    ACMessageBox.show(
                        "�����ȃp�X���ł��B\n" +
                        "(�o�b�N�A�b�v��t�@�C�������݂̃f�[�^�x�[�X�t�@�C���Ɠ����ł�)",
                        ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                    loopFlg2 = true; //�t�@�C���I������蒼��
                    continue;
                }

                //�ޔ�悪�ޔ����łȂ������`�F�b�N
                if (bkupFile.getPath().toLowerCase().equals(restoreFile.getPath().toLowerCase())) {
                    ACMessageBox.show(
                        "�����ȃp�X���ł��B\n" +
                        "(�o�b�N�A�b�v��t�@�C�����������t�@�C���Ɠ����ł�)",
                        ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                    loopFlg2 = true; //�t�@�C���I������蒼��
                    continue;
                }
            }
            while (loopFlg2);
        }
        while (loopFlg);

        return bkupFile;
    }

    /**
     * �f�t�H���g�̃t�@�C�������擾���܂��B
     * @param fileExtension �g���q
     * @return �t�@�C����
     */
    public String getDefaultFileName(String fileExtension) {
        String today = "";
        try {
            Calendar cal = Calendar.getInstance();
            VRDateFormat vrdf = new VRDateFormat("yyMMdd");
            today = vrdf.format(cal.getTime());
        }
        catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return "iken" + today + "." + fileExtension;
    }
}
