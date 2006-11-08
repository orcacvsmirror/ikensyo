package jp.or.med.orca.ikensho.convert;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.io.File;
import java.text.FieldPosition;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACSQLSafeDateFormat;
import jp.nichicom.ac.text.ACSQLSafeIntegerFormat;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.bridge.sql.BridgeFirebirdDBManager;
import jp.nichicom.vr.io.VRCSVFile;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

public class IkenshoShijishoCSVImport extends ACAffairContainer implements
        ACAffairable {

    private ACPanel contents = null;
    private ACLabel message = null;
    private ACPanel buttons = null;
    private ACButton execute = null;
    private ACButton exit = null;
    private ACPanel settings = null;
    private ACTextField sourceDirectory = null;
    private ACButton sourceBrowse = null;
    private ACGroupBox sourceDirectorys = null;

    public IkenshoShijishoCSVImport() throws HeadlessException {
        super();
        initialize();
    }

    /**
     * This method initializes buttons
     * 
     * @return javax.swing.JPanel
     */
    private ACPanel getButtons() {
        if (buttons == null) {
            buttons = new ACPanel();
            buttons.add(getExit(), VRLayout.EAST);
            buttons.add(getExecute(), VRLayout.EAST);
        }
        return buttons;
    }

    /**
     * This method initializes execute
     * 
     * @return javax.swing.JButton
     */
    private ACButton getExecute() {
        if (execute == null) {
            execute = new ACButton();
            execute.setText("���s(E)");
            execute.setMnemonic('E');
            execute.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    executeImport();
                }
            });
        }
        return execute;
    }

    /**
     * �f�[�^�ڍs�����s���܂��B
     */
    protected void executeImport() {
        // �f�[�^�ڍs�����s�\���`�F�b�N���܂��B
        File dir = new File(getSourceDirectory().getText());
        if ((dir == null) || (!dir.isDirectory())) {
            ACMessageBox.showExclamation("�u�ڍs��CSV�t�@�C���̏ꏊ�v���s���ł��B"
                    + ACConstants.LINE_SEPARATOR + "�u�ڍs��CSV�t�@�C���̏ꏊ�v���w�肵�Ă��������B");
            getSourceDirectory().requestFocus();
            return;
        }
        boolean repeated = false;
        File[] files = dir.listFiles();
        shijishoFile = null;
        int end = files.length;
        for (int i = 0; i < end; i++) {
            File f = files[i];
            String name = f.getName();
            if (name == null) {
                name = "";
            } else {
                name = name.toUpperCase();
            }
            if ((name != null) && name.startsWith("SJS")) {
                if (name.endsWith(".CSV")) {
                    // ���p�ҏ��FPATIENT
                    if (shijishoFile != null) {
                        repeated = true;
                    } else {
                        shijishoFile = f;
                    }
                }
            }
        }
        if (repeated) {
            ACMessageBox.showExclamation("2�g�ȏ�̈ڍs���f�[�^�����݂��܂��B"
                    + ACConstants.LINE_SEPARATOR
                    + "�u�ڍs��CSV�t�@�C���̏ꏊ�v�̃t�@�C�����������񂷂ׂč폜���A�ēx�ڍs���f�[�^���o�͂��Ă��������B");
            return;
        }

        // �ڍs����f�[�^���ꗗ��
        boolean fileExists = false;
        StringBuffer sb = new StringBuffer();
        if (shijishoFile != null) {
            sb.append("�@�E�K��Ō�w����");
            sb.append(ACConstants.LINE_SEPARATOR);
            fileExists = true;
        }

        if (!fileExists) {
            ACMessageBox.showExclamation("�ڍs����CSV�t�@�C�������݂��܂���B"
                    + ACConstants.LINE_SEPARATOR
                    + "�u�ڍs��CSV�t�@�C���̏ꏊ�v�̃t�@�C�����������񂷂ׂč폜���A�ēx�ڍs���f�[�^���o�͂��Ă��������B");
            return;
        }

        dbm = null;
        try {
            dbm = new BridgeFirebirdDBManager();
            if (!dbm.isAvailableDBConnection()) {
                throw new Exception("�f�[�^�x�[�X�֐ڑ��ł��܂���B");
            }
        } catch (Exception ex) {
            VRLogger.info(ex.getStackTrace());
            ACMessageBox.showExclamation("�f�[�^�x�[�X�֐ڑ��ł��܂���B"
                    + ACConstants.LINE_SEPARATOR
                    + "�㌩���{�̂��N�����A�f�[�^�x�[�X�̐ݒ���s���Ă��������B");
            return;
        }

        if (ACMessageBox.showOkCancel("�ȉ��̃f�[�^�ڍs���J�n���܂��B��낵���ł����H"
                + ACConstants.LINE_SEPARATOR + ACConstants.LINE_SEPARATOR
                + sb.toString() + ACConstants.LINE_SEPARATOR
                + "�y�����Ӂ��z�f�[�^�ڍs�����͓r���ŃL�����Z���ł��܂���B" + ACConstants.LINE_SEPARATOR
                + "�@�@�@�@�@�@�K�����O�Ƀo�b�N�A�b�v������Ă��������B", ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
            return;
        }

        errorCount = 0;

        getExecute().setEnabled(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    VRCSVFile f = new VRCSVFile("dummy", VRCSVFile.SJIS);
                    // �K��Ō�w����
                    if (shijishoFile != null) {
                        f.setPath(shijishoFile.getAbsolutePath());
                        f.clear();
                        f.read(false);
                        addErrorCount(importHomonResult(dbm, f));
                    }

                    if (errorCount > 0) {
                        ACMessageBox.show("�f�[�^�ڍs���������܂����B"
                                + ACConstants.LINE_SEPARATOR + " " + errorCount
                                + " ���̈ڍs�Ɏ��s���Ă��܂��B" + ACConstants.LINE_SEPARATOR
                                + "�ڍׂ�logs�t�H���_�z���̃��O�t�@�C�����Q�Ƃ��Ă��������B");
                    } else {
                        ACMessageBox.show("�f�[�^�ڍs���������܂����B");
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
                setProgressTitle(INITIAL_MESSAGE);
                setProgress("");
                getExecute().setEnabled(true);
            };
        }).start();
    }

    /**
     * �K��Ō�񍐏����ڍs���܂��B
     * 
     * @param dbm DB�}�l�[�W��
     * @param f CSV�t�@�C��
     * @return �G���[����
     */
    protected int importHomonResult(ACDBManager dbm, VRCSVFile f) {
        setProgressTitle("�@�K��Ō�w�����̈ڍs��..");
        setProgress("[���ҏ��̎擾]");
        int error = 0;
        StringBuffer sb = new StringBuffer();

        Map patientMap = new HashMap();
        try {
            sb.append("SELECT");
            sb.append(" PATIENT_NO");
            sb.append(",PATIENT_NM");
            // sb.append(",SEX");
            sb.append(",BIRTHDAY");
            sb.append(" FROM");
            sb.append(" PATIENT");
            List patients = dbm.executeQuery(sb.toString());
            // ���҈ꗗ���n�b�V����
            List removeKeys = new ArrayList();
            Iterator it = patients.iterator();
            while (it.hasNext()) {
                Map patient = (Map) it.next();
                String key = DATE_FORMAT.format(patient.get("BIRTHDAY"))
                        + STRING_FORMAT.format(patient.get("PATIENT_NM"))
                                .trim();
                if (patientMap.containsKey(key)) {
                    removeKeys.add(key);
                } else {
                    patientMap.put(key, patient.get("PATIENT_NO"));
                }
            }
            // �d���L�[�����O
            it = removeKeys.iterator();
            while (it.hasNext()) {
                patientMap.remove(it.next());
            }
        } catch (Exception ex) {
            VRLogger.info("���ҏ��̎擾���s");
            error += f.getRowCount();
            return error;
        }

        // �ӌ����w�������ʃe�[�u���̃C���T�[�g��
        sb = new StringBuffer();
        sb.append("INSERT INTO COMMON_IKN_SIS(");
        sb.append(" PATIENT_NO");
        sb.append(",EDA_NO");
        sb.append(",DOC_KBN");
        sb.append(",PATIENT_NM");
        sb.append(",PATIENT_KN");
        sb.append(",SEX");
        sb.append(",BIRTHDAY");
        sb.append(",AGE");
        sb.append(",POST_CD");
        sb.append(",ADDRESS");
        sb.append(",TEL1");
        sb.append(",TEL2");
        sb.append(",SINDAN_NM1");
        sb.append(",SINDAN_NM2");
        sb.append(",SINDAN_NM3");
        sb.append(",HASHOU_DT1");
        sb.append(",HASHOU_DT2");
        sb.append(",HASHOU_DT3");
        sb.append(",MT_STS");
        sb.append(",MT_STS_OTHER");
        sb.append(",MEDICINE1");
        sb.append(",DOSAGE1");
        sb.append(",UNIT1");
        sb.append(",USAGE1");
        sb.append(",MEDICINE2");
        sb.append(",DOSAGE2");
        sb.append(",UNIT2");
        sb.append(",USAGE2");
        sb.append(",MEDICINE3");
        sb.append(",DOSAGE3");
        sb.append(",UNIT3");
        sb.append(",USAGE3");
        sb.append(",MEDICINE4");
        sb.append(",DOSAGE4");
        sb.append(",UNIT4");
        sb.append(",USAGE4");
        sb.append(",MEDICINE5");
        sb.append(",DOSAGE5");
        sb.append(",UNIT5");
        sb.append(",USAGE5");
        sb.append(",MEDICINE6");
        sb.append(",DOSAGE6");
        sb.append(",UNIT6");
        sb.append(",USAGE6");
        sb.append(",NETAKIRI");
        sb.append(",CHH_STS");
        sb.append(",SHJ_ANT");
        sb.append(",YKG_YOGO");
        sb.append(",TNT_KNR");
        sb.append(",YUEKI_PUMP");
        sb.append(",CHU_JOU_EIYOU");
        sb.append(",TOUSEKI");
        sb.append(",JD_FUKU");
        sb.append(",TOU_KYOUKYU");
        sb.append(",JINKOU_KOUMON");
        sb.append(",JINKOU_BOUKOU");
        sb.append(",OX_RYO");
        sb.append(",OX_RYO_RYO");
        sb.append(",JINKOU_KOKYU");
        sb.append(",JINKOU_KKY_HOUSIKI");
        sb.append(",JINKOU_KKY_SET");
        sb.append(",CANNULA");
        sb.append(",CANNULA_SIZE");
        sb.append(",KYUINKI");
        sb.append(",KKN_SEK_SHOCHI");
        sb.append(",KEKN_EIYOU");
        sb.append(",KEKN_EIYOU_METHOD");
        sb.append(",KEKN_EIYOU_SIZE");
        sb.append(",KEKN_EIYOU_CHG");
        sb.append(",CATHETER");
        sb.append(",RYU_CATHETER");
        sb.append(",RYU_CAT_SIZE");
        sb.append(",RYU_CAT_CHG");
        sb.append(",DOREN");
        sb.append(",DOREN_BUI");
        sb.append(",MONITOR");
        sb.append(",TOUTU");
        sb.append(",JOKUSOU_SHOCHI");
        sb.append(",SOUCHAKU_OTHER");
        sb.append(",DR_NM");
        sb.append(",MI_NM");
        sb.append(",MI_POST_CD");
        sb.append(",MI_ADDRESS");
        sb.append(",MI_TEL1");
        sb.append(",MI_TEL2");
        sb.append(",MI_FAX1");
        sb.append(",MI_FAX2");
        sb.append(",MI_CEL_TEL1");
        sb.append(",MI_CEL_TEL2");
        sb.append(",LAST_TIME");
        sb.append(")VALUES(");
        String commonHeader = sb.toString();

        // ���ʃe�[�u���̍ő�}�Ԏ擾
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" MAX(EDA_NO) AS NEXT_NO");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS");
        sb.append(" WHERE ");
        sb.append("(DOC_KBN = 2)");
        sb.append("AND(PATIENT_NO = ");
        String maxEdaHeader = sb.toString();

        // �w�����Ǝ��e�[�u���̃C���T�[�g��
        sb = new StringBuffer();
        sb.append("INSERT INTO SIS_ORIGIN(");
        sb.append(" PATIENT_NO");
        sb.append(",EDA_NO");
        sb.append(",KINYU_DT");
        sb.append(",SIJI_CREATE_CNT");
        sb.append(",SIJI_YUKOU_KIKAN");
        sb.append(",SIJI_KANGO_KBN");
        sb.append(",STATION_NM");
        sb.append(",KINKYU_RENRAKU");
        sb.append(",FUZAIJI_TAIOU");
        sb.append(",RSS_RYUIJIKOU");
        sb.append(",REHA_SIJI_UMU");
        sb.append(",REHA_SIJI");
        sb.append(",JOKUSOU_SIJI_UMU");
        sb.append(",JOKUSOU_SIJI");
        sb.append(",SOUCHAKU_SIJI_UMU");
        sb.append(",SOUCHAKU_SIJI");
        sb.append(",RYUI_SIJI_UMU");
        sb.append(",RYUI_SIJI");
        sb.append(",SIJI_TOKKI");
        sb.append(",CREATE_DT");
        sb.append(",KOUSIN_DT");
        sb.append(",SIJI_KIKAN_FROM");
        sb.append(",SIJI_KIKAN_TO");
        sb.append(",YOUKAIGO_JOUKYOU");
        sb.append(",HOUMON_SIJISYO");
        sb.append(",TENTEKI_SIJISYO");
        sb.append(",TENTEKI_FROM");
        sb.append(",TENTEKI_TO");
        sb.append(",TENTEKI_SIJI");
        sb.append(",OTHER_STATION_SIJI");
        sb.append(",OTHER_STATION_NM");
        sb.append(",LAST_TIME");
        sb.append(")VALUES(");
        String originHeader = sb.toString();

        int end = f.getRowCount();
        setProgressTitle("�@�K��Ŏw�����̈ڍs��..[" + end + "����/");
        for (int i = 0; i < end; i++) {
            List list = f.getRow(i);
            try {
                setProgress(i + "����] ���s=" + error + "��");
                if (list.size() > 111) {
                    String birthDay = DATE_FORMAT.format(list.get(6));
                    String patientName = STRING_FORMAT.format(list.get(3));
                    Object patientNo = patientMap.get(birthDay + patientName);
                    if (patientNo == null) {
                        // �Ώێ҂Ȃ�
                        continue;
                    }
                    patientNo = INTEGER_FORMAT.format(patientNo);
                    dbm.beginTransaction();
                    sb = new StringBuffer(commonHeader);
                    // 1 ���Ҕԍ� 1����͂��܂銳�҂��Ƃ̘A�ԁB ����
                    sb.append(patientNo);
                    // 2 �}�ԍ� ���ꊳ�҂ɂ�����1����͂��܂�쐬����ԍ��B�ŏ��ɍ쐬���ꂽ���̂�1�Ƃ���B ����
                    sb.append(",");
                    sb.append("(SELECT");
                    sb.append(" CASE");
                    sb.append(" WHEN (MAX(EDA_NO) IS NULL) THEN 1");
                    sb.append(" ELSE MAX(EDA_NO) + 1");
                    sb.append(" END");
                    sb.append(" FROM");
                    sb.append(" COMMON_IKN_SIS");
                    sb.append(" WHERE ");
                    sb.append("(DOC_KBN = 2)");
                    sb.append("AND(PATIENT_NO = ");
                    sb.append(patientNo);
                    sb.append("))");
                    // ���ދ敪
                    sb.append(",2");
                    // 4 ���Җ� ������ 15
                    sb.append(",");
                    sb.append(cutLength(patientName, 15));
                    // 5 ���Җ����� ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 4, 30));
                    // 6 ���� 0�F���I���A1�F�j�A2�F���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(5)));
                    // 7 ���N���� yyyy-MM-dd �`���B ���t
                    sb.append(",");
                    sb.append(DATE_FORMAT.format(list.get(6)));
                    // 8 �N�� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 7, 5));
                    // 9 �X�֔ԍ� XXX-XXXX �`���B ������ 8
                    sb.append(",");
                    sb.append(cutLength(list, 8, 8));
                    // 10 �Z�� ������ 45
                    sb.append(",");
                    sb.append(cutLength(list, 9, 45));
                    // 11 �d�b�ǔ� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 10, 5));
                    // 12 �d�b XXXX-XXXX �`���B ������ 9
                    sb.append(",");
                    sb.append(cutLength(list, 11, 9));
                    // 13 �f�f���P ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 12, 30));
                    // 14 �f�f���Q ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 13, 30));
                    // 15 �f�f���R ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 14, 30));
                    // 16 ���ǔN�����P ggge�NMM��dd�� �`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 15, 16));
                    // 17 ���ǔN�����Q ggge�NMM��dd�� �`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 16, 16));
                    // 18 ���ǔN�����R ggge�NMM��dd�� �`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 17, 16));
                    // 19 �a�󎡗Ï�� 5�s�ȓ��B ������ 250
                    sb.append(",");
                    sb.append(cutLength(list, 18, 250));
                    //�a�󎡗Ï�ԑ�
                    sb.append(",NULL");
                    // 20 ��ܖ��P ������ 12
                    sb.append(",");
                    sb.append(cutLength(list, 19, 12));
                    // 21 �p�ʂP ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 20, 4));
                    // 22 �P�ʂP ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 21, 4));
                    // 23 �p�@�P ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 22, 10));
                    // 24 ��ܖ��Q ������ 12
                    sb.append(",");
                    sb.append(cutLength(list, 23, 12));
                    // 25 �p�ʂQ ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 24, 4));
                    // 26 �P�ʂQ ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 25, 4));
                    // 27 �p�@�Q ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 26, 10));
                    // 28 ��ܖ��R ������ 12
                    sb.append(",");
                    sb.append(cutLength(list, 27, 12));
                    // 29 �p�ʂR ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 28, 4));
                    // 30 �P�ʂR ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 29, 4));
                    // 31 �p�@�R ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 30, 10));
                    // 32 ��ܖ��S ������ 12
                    sb.append(",");
                    sb.append(cutLength(list, 31, 12));
                    // 33 �p�ʂS ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 32, 4));
                    // 34 �P�ʂS ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 33, 4));
                    // 35 �p�@�S ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 34, 10));
                    // 36 ��ܖ��T ������ 12
                    sb.append(",");
                    sb.append(cutLength(list, 35, 12));
                    // 37 �p�ʂT ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 36, 4));
                    // 38 �P�ʂT ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 37, 4));
                    // 39 �p�@�T ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 38, 10));
                    // 40 ��ܖ��U ������ 12
                    sb.append(",");
                    sb.append(cutLength(list, 39, 12));
                    // 41 �p�ʂU ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 40, 4));
                    // 42 �P�ʂU ������ 4
                    sb.append(",");
                    sb.append(cutLength(list, 41, 4));
                    // 43 �p�@�U ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 42, 10));
                    // 44 ��Q����҂̓��퐶�������x�i�Q������x�j
                    // 0�F���I���A1�F�����A2�FJ1�A3�FJ2�A4�FA1�A5�FA2�A6�FB1�A7�FB2�A8�FC1�A9�FC2�B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(43)));
                    // 45 �F�m�Ǎ���҂̓��퐶�������x
                    // 0�F���I���A1�F�����A2�FI�A3�FIIa�A4�FIIb�A5�FIIIa�A6�FIIIb�A7�FIV�A8�FM�B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(44)));
                    //TODO �Ǐ���萫
                    sb.append(",");
                    sb.append('0');
                    //�v����ԗ\��
                    sb.append(",NULL");                    
                    // 46 �_�H�Ǘ� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(45)));
                    // 47 �A�t�|���v 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(46)));
                    // 48 ���S�Ö��h�{ 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(47)));
                    // 49 ���� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(48)));
                    // 50 ���������󗬑��u 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(49)));
                    // 51 ���͉t�������u 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(50)));
                    // 52 �l�H��� 0�F���I���A1�F�I���B�X�g�[�}�̂��ƁB ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(51)));
                    // 53 �l�H�N�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(52)));
                    // 54 �_�f�Ö@ 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(53)));
                    // 55 �_�f�Ö@�� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 54, 5));
                    // 56 �l�H�ċz�� 0�F���I���A1�F�I���B���X�s���[�^�[�̂��ƁB ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(55)));
                    // 57 �l�H�ċz����� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 56, 5));
                    // 58 �l�H�ċz��ݒ� ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 57, 10));
                    // 59 �C�ǃJ�j���[�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(58)));
                    // 60 �C�ǃJ�j���[���T�C�Y ������ 5
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(59)));
                    // 61 �z���� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(60)));
                    // 62 �C�ǐ؊J���u 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(61)));
                    // 63 �o�ǉh�{ 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(62)));
                    // 64 �o�ǉh�{���@ ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 63, 5));
                    // 65 �o�ǉh�{�T�C�Y ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 64, 5));
                    // 66 �o�ǉh�{���� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 65, 5));
                    // 67 �J�e�[�e�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(66)));
                    // 68 ���u�J�e�[�e�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(67)));
                    // 69 ���u�J�e�[�e���T�C�Y ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 68, 5));
                    // 70 ���u�J�e�[�e������ ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 69, 5));
                    // 71 �h���[�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(70)));
                    // 72 �h���[������ ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 71, 5));
                    // 73 ���j�^�[���� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(72)));
                    // 74 �u�ɊŌ� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(73)));
                    // 75 ��n���u 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(74)));
                    // 76 ������Ë@�푼 ������ 15
                    sb.append(",");
                    sb.append(cutLength(list, 75, 15));
                    // 77 ��t�� ������ 15
                    sb.append(",");
                    sb.append(cutLength(list, 76, 15));
                    // 78 ��Ë@�֖� ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 77, 30));
                    // 79 ��Ë@�֗X�֔ԍ� XXX-XXXX �`���B ������ 10
                    sb.append(",");
                    sb.append(cutLength(list, 78, 10));
                    // 80 ��Ë@�֏Z�� ������ 45
                    sb.append(",");
                    sb.append(cutLength(list, 79, 45));
                    // 81 ��Ë@�֓d�b�ǔ� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 80, 5));
                    // 82 ��Ë@�֓d�b XXXX-XXXX �`���B ������ 9
                    sb.append(",");
                    sb.append(cutLength(list, 81, 9));
                    // 83 ��Ë@�ւe�`�w�ǔ� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 82, 5));
                    // 84 ��Ë@�ւe�`�w XXXX-XXXX �`���B ������ 9
                    sb.append(",");
                    sb.append(cutLength(list, 83, 9));
                    // 85 �g�єԍ��� ������ 5
                    sb.append(",");
                    sb.append(cutLength(list, 84, 5));
                    // 86 �g�єԍ� XXXX-XXXX �`���B ������ 9
                    sb.append(",");
                    sb.append(cutLength(list, 85, 9));
                    // �ŏI�X�V�� LAST_TIME
                    sb.append(",CURRENT_TIMESTAMP)");
                    dbm.executeUpdate(sb.toString());

                    // ���ʃe�[�u���̍ő�}�Ԃ��擾���w�����ŗL�e�[�u���̎}�ԍ��Ƃ���B�A
                    sb = new StringBuffer(maxEdaHeader);
                    sb.append(patientNo);
                    sb.append(")");
                    List edaNos = dbm.executeQuery(sb.toString());
                    if (edaNos.isEmpty()) {
                        throw new Exception("�}�ԍ��擾���s");
                    }
                    String edaNo = INTEGER_FORMAT.format(((Map) edaNos.get(0))
                            .get("NEXT_NO"));

                    sb = new StringBuffer(originHeader);
                    // ���Ҕԍ�
                    sb.append(patientNo);
                    // �}�ԍ�
                    sb.append(",");
                    sb.append(edaNo);
                    // 87 �L���� yyyy-MM-dd �`�� ���t
                    sb.append(",");
                    sb.append(DATE_FORMAT.format(list.get(86)));
                    // �w�����쐬��
                    sb.append(",0");
                    // �w�����L������
                    sb.append(",1");
                    // �w�����Ō�敪
                    sb.append(",2");
                    // 88 �Ō�X�e�[�V������ ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 87, 30));
                    // 89 �ً}���A���� ������ 40
                    sb.append(",");
                    sb.append(cutLength(list, 88, 40));
                    // 90 �s�ݎ��Ή��@ ������ 40
                    sb.append(",");
                    sb.append(cutLength(list, 89, 40));
                    // 91 �×{�����w���㗯�ӎ��� 3�s�ȓ��B ������ 120
                    sb.append(",");
                    sb.append(cutLength(list, 90, 120));
                    // 92 ���n�r���e�[�V�����w���L�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(91)));
                    // 93 ���n�r���e�[�V�����w�� 3�s�ȓ��B ������ 120
                    sb.append(",");
                    sb.append(cutLength(list, 92, 120));
                    // 94 ��ጏ��u�w���L�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(93)));
                    // 95 ��ጏ��u�w�� 3�s�ȓ��B ������ 120
                    sb.append(",");
                    sb.append(cutLength(list, 94, 120));
                    // 96 ������Ë@��w���L�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(95)));
                    // 97 ������Ë@��w�� 3�s�ȓ��B ������ 120
                    sb.append(",");
                    sb.append(cutLength(list, 96, 120));
                    // 98 ���ӎw���������L�� 0�F���I���A1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(97)));
                    // 99 ���ӎw�������� 3�s�ȓ��B ������ 120
                    sb.append(",");
                    sb.append(cutLength(list, 98, 120));
                    // 100 �w�������L���� 4�s�ȓ��B ������ 200
                    sb.append(",");
                    sb.append(cutLength(list, 99, 200));
                    // 101 �V�K�쐬�� yyyy-MM-dd hh:mm:ss�`�� ����
                    sb.append(",");
                    sb.append(TIME_FORMAT.format(list.get(100)));
                    // 102 �X�V�� yyyy-MM-dd hh:mm:ss�`�� ����
                    sb.append(",");
                    sb.append(TIME_FORMAT.format(list.get(101)));
                    // 103 �w������From ggge�NMM��dd���`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 102, 16));
                    // 104 �w������To ggge�NMM��dd���`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 103, 16));
                    // 105 �v���F��̏�
                    // 0�F���I���A1�F�����A11�F�v�x���A21�F�v���1�A22�F�v���2�A23�F�v���3�A24�F�v���4�A25�F�v���5
                    // ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(104)));
                    // 106 �K��Ō�w���� �w�����̎�ނƂ��ĖK��Ō�w�������w�肷�邩�B0�F���I���A-1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(105)));
                    // 107 �_�H���ˎw���� �w�����̎�ނƂ��ē_�H���ˎw�������w�肷�邩�B0�F���I���A-1�F�I���B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(106)));
                    // 108 �_�H���ˎw������From ggge�NMM��dd���`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 107, 16));
                    // 109 �_�H���ˎw������To ggge�NMM��dd���`���B ������ 16
                    sb.append(",");
                    sb.append(cutLength(list, 108, 16));
                    // 110 �_�H���ˎw�� 4�s�ȓ��B ������ 200
                    sb.append(",");
                    sb.append(cutLength(list, 109, 200));
                    // 111 ���X�e�[�V�����w�� 0�F���I���A1�F���A2�F�L�B ����
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(110)));
                    // 112 ���Ō�X�e�[�V������ ������ 30
                    sb.append(",");
                    sb.append(cutLength(list, 111, 30));
                    // �ŏI�X�V�� LAST_TIME
                    sb.append(",CURRENT_TIMESTAMP)");

                    dbm.executeUpdate(sb.toString());

                    dbm.commitTransaction();
                } else {
                    throw new Exception("�s���ȃt�B�[���h��");
                }
            } catch (Exception ex) {
                VRLogger.info("�s����CSV���R�[�h �K��Ō�w����(" + (i + 1) + ") = "
                        + list.toString());
                try {
                    dbm.rollbackTransaction();
                } catch (Exception ex2) {
                    VRLogger.info(ex2);
                }
                error++;
            }
        }
        return error;
    }

    /**
     * ��1�E��2�t�B�[���h����L�[�Ƃ��āA�d�����Ȃ������X�g��Ԃ��܂��B
     * <p>
     * �d�������ꍇ�A�Ō�Ɍ����������R�[�h���D�悳��܂��B
     * </p>
     * 
     * @param f �t�@�C��
     * @return ���o����
     */
    protected List filterRecord(VRCSVFile f) {
        // ��L�[�ł��闘�p��ID+�쐬���̏d�����`�F�b�N�B
        // �d������ꍇ�A�Ō�̃f�[�^��
        int end = f.getRowCount();
        TreeMap repeatCache = new TreeMap();
        for (int i = 0; i < end; i++) {
            List list = f.getRow(i);
            if (list.size() > 2) {
                String repeatKey = INTEGER_FORMAT.format(list.get(0)) + "/"
                        + DATE_FORMAT.format(list.get(1));
                repeatCache.put(repeatKey, list);
            }
        }
        return new ArrayList(repeatCache.values());
    }

    protected BridgeFirebirdDBManager dbm = null;
    protected File shijishoFile = null;
    protected int errorCount = 0;

    /**
     * �G���[���������Z���܂��B
     * 
     * @param add ���Z��
     */
    public void addErrorCount(int add) {
        errorCount += add;
    }

    /**
     * �w�蕶�����Ő؂�l�߂܂��B
     * 
     * @param src ���ؕ���
     * @param max �ő啶����
     * @return �؂�l�ߌ���
     */
    protected String cutLength(List list, int index, int max) {
        // ''�̕�������␳
        return cutLength(STRING_FORMAT.format(list.get(index)), max);
    }

    /**
     * �w�蕶�����Ő؂�l�߂܂��B
     * 
     * @param src ���ؕ���
     * @param max �ő啶����
     * @return �؂�l�ߌ���
     */
    protected String cutLength(String src, int max) {
        // ''�̕�������␳
        max += 2;
        if (src.length() <= max) {
            return src;
        }
        return src.substring(0, max - 1) + "'";
    }

    private String progressTitle = "";

    protected void setProgressTitle(String progressTitle) {
        this.progressTitle = progressTitle;
    }

    protected String getProgressTitle() {
        return progressTitle;
    }

    protected void setProgress(String progress) {
        getMessage().setText(getProgressTitle() + progress);
    }

    protected ACSQLSafeStringFormat STRING_FORMAT = new ACSQLSafeStringFormat() {
        public StringBuffer format(Object obj, StringBuffer toAppendTo,
                FieldPosition pos) {
            if (("NULL".equals(obj))) {
                obj = null;
            }
            return super.format(obj, toAppendTo, pos);

        }
    };

    protected ACSQLSafeIntegerFormat INTEGER_FORMAT = new ACSQLSafeIntegerFormat() {
        public StringBuffer format(Object obj, StringBuffer toAppendTo,
                FieldPosition pos) {
            if (("NULL".equals(obj))) {
                obj = null;
            }
            return super.format(obj, toAppendTo, pos);

        }
    };

    protected ACSQLSafeDateFormat DATE_FORMAT = new ACSQLSafeDateFormat(
            "yyyy-MM-dd") {
        public StringBuffer format(Object obj, StringBuffer toAppendTo,
                FieldPosition pos) {
            if (!(obj instanceof Date)) {
                try {
                    obj = VRDateParser.parse(String.valueOf(obj));
                } catch (Exception ex) {
                    obj = null;
                }
            } else if (("NULL".equals(obj))) {
                obj = null;
            }
            return super.format(obj, toAppendTo, pos);
        }
    };

    protected ACSQLSafeDateFormat TIME_FORMAT = new ACSQLSafeDateFormat(
            "yyyy-MM-dd HH:mm:ss") {
        public StringBuffer format(Object obj, StringBuffer toAppendTo,
                FieldPosition pos) {
            if (!(obj instanceof Date)) {
                try {
                    obj = VRDateParser.parse(String.valueOf(obj));
                } catch (Exception ex) {
                    obj = null;
                }
            } else if (("NULL".equals(obj))) {
                obj = null;
            }
            return super.format(obj, toAppendTo, pos);
        }
    };

    /**
     * �X�VSQL���𔭍s���܂��B
     * 
     * @param dbm DB�}�l�[�W��
     * @param sql SQL��
     */
    protected int executeUpdate(ACDBManager dbm, String sql) {
        try {
            dbm.beginTransaction();
            dbm.executeUpdate(sql);
            dbm.commitTransaction();
        } catch (Exception ex) {
            VRLogger.info(ex);
            try {
                dbm.rollbackTransaction();
            } catch (Exception ex2) {
                VRLogger.info(ex2);
            }
            return 1;
        }
        return 0;
    }

    /**
     * This method initializes exit
     * 
     * @return javax.swing.JButton
     */
    private ACButton getExit() {
        if (exit == null) {
            exit = new ACButton();
            exit.setText("�I��(X)");
            exit.setMnemonic('X');
            exit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (ACMessageBox.showOkCancel("�I�����܂��B��낵���ł����H",
                            ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {
                        System.exit(0);
                    }
                }
            });
        }
        return exit;
    }

    /**
     * This method initializes contents
     * 
     * @return javax.swing.JPanel
     */
    private ACPanel getContents() {
        if (settings == null) {
            settings = new ACPanel();
            settings.add(getDests(), VRLayout.NORTH);
            settings.add(getSourceDirectorys(), VRLayout.NORTH);
            settings.add(getInfomations(), VRLayout.CLIENT);
        }
        return settings;
    }

    /**
     * This method initializes contents
     * 
     * @return javax.swing.JPanel
     */
    private ACGroupBox getDests() {
        if (dests == null) {
            dests = new ACGroupBox();
            dests.setText("�f�[�^�ڍs��");
            dests.add(getDest(), VRLayout.CLIENT);
        }
        return dests;
    }

    /**
     * This method initializes contents
     * 
     * @return javax.swing.JPanel
     */
    private ACLabel getDest() {
        if (dest == null) {
            dest = new ACLabel();
            dest.setText("IP�F unknown" + ACConstants.LINE_SEPARATOR
                    + "�f�[�^�x�[�X�̏ꏊ�F unknown");
        }
        return dest;
    }

    private ACGroupBox dests;
    private ACLabel dest;
    private ACGroupBox infomations;
    private ACTextArea infomation;

    /**
     * This method initializes contents
     * 
     * @return javax.swing.JPanel
     */
    private ACGroupBox getInfomations() {
        if (infomations == null) {
            infomations = new ACGroupBox();
            infomations.setText("�f�[�^�ڍs�Ɋւ��邲����");
            infomations.add(getInfomation(), VRLayout.CLIENT);
        }
        return infomations;
    }

    /**
     * This method initializes contents
     * 
     * @return javax.swing.JPanel
     */
    private ACTextArea getInfomation() {
        if (infomation == null) {
            infomation = new ACTextArea();
            infomation.setRows(10);
            infomation.setLineWrap(true);
            infomation.setEditable(false);
            infomation
                    .setText("�y�f�[�^�ڍs�̑O�Ɂz"
                            + ACConstants.LINE_SEPARATOR
                            + "�@���㌩���{�̂��N�����Ă���ꍇ�A�K���I�������Ă���f�[�^�ڍs���s���Ă��������B"
                            + ACConstants.LINE_SEPARATOR
                            + "�@���f�[�^�ڍs���ύX����ꍇ�A�㌩���{�̂��N�����ăf�[�^�x�[�X�̐ݒ��ύX���Ă��������B"
                            + ACConstants.LINE_SEPARATOR
                            + "�@���ڍs����K��Ō�w�����́A���ׂĐV�K�ɍ쐬�������̂Ƃ��Ēǉ��o�^����܂��B�����̃f�[�^���폜���邱�Ƃ͂���܂��񂪁A�f�[�^�ڍs��2�x�ȏ���s����ƁA�d�������K��Ō�w�������ł���_�ɂ����ӂ��������B"
                            + ACConstants.LINE_SEPARATOR
                            + ACConstants.LINE_SEPARATOR
                            + "�y�ڍs�ł��Ȃ��f�[�^�ɂ��āz"
                            + ACConstants.LINE_SEPARATOR
                            + "�@���ڍs��̈㌩���V�X�e���ɁA�����Ɛ��N�����̈�v���銳�҂��o�^����Ă��Ȃ��K��Ō�w�����͈ڍs����܂���B"
                            + ACConstants.LINE_SEPARATOR
                            + "�@���ڍs��̈㌩���V�X�e���ɁA�����Ɛ��N�����̓������҂��d���o�^����Ă����ꍇ�A���̊��҂Ɋւ���K��Ō�w�����͈ڍs����܂���B"
                            + ACConstants.LINE_SEPARATOR
                            + ACConstants.LINE_SEPARATOR
                            + "�y�ڍs�Ɏ��s�����ꍇ�z"
                            + ACConstants.LINE_SEPARATOR
                            + "�@���u�f�[�^�x�[�X�֐ڑ��ł��܂���B�v�Ƃ����G���[���\�����ꂽ�ꍇ�́A�㌩���{�̂��N�����A�f�[�^��o�^�ł��邩�m�F���Ă��������B"

                    );

        }
        return infomation;
    }

    /**
     * This method initializes directory
     * 
     * @return javax.swing.JTextField
     */
    private ACTextField getSourceDirectory() {
        if (sourceDirectory == null) {
            sourceDirectory = new ACTextField();
            sourceDirectory.setColumns(30);
        }
        return sourceDirectory;
    }

    /**
     * This method initializes browse
     * 
     * @return javax.swing.JButton
     */
    private ACButton getSourceBrowse() {
        if (sourceBrowse == null) {
            sourceBrowse = new ACButton();
            sourceBrowse.setText("�Q��(B)..");
            sourceBrowse.setMnemonic('B');
            sourceBrowse.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    ACFileChooser chooser = new ACFileChooser();
                    File f = chooser.showDirectorySelectDaialog();
                    if (f != null) {
                        getSourceDirectory().setText(f.getAbsolutePath());
                    }
                }
            });
        }
        return sourceBrowse;
    }

    /**
     * This method initializes directorys
     * 
     * @return jp.nichicom.ac.container.ACLabelContainer
     */
    private ACGroupBox getSourceDirectorys() {
        if (sourceDirectorys == null) {
            sourceDirectorys = new ACGroupBox();
            sourceDirectorys.setAutoWrap(false);
            sourceDirectorys.setText("�ڍs��CSV�t�@�C���̏ꏊ");
            sourceDirectorys.add(getSourceDirectory(), null);
            sourceDirectorys.add(getSourceBrowse(), null);
        }
        return sourceDirectorys;
    }

    /**
     * ���܂��B
     * 
     * @param args
     */
    public static void main(String[] args) {
        try {
            ACFrame.setVRLookAndFeel();
            ACFrameEventProcesser processer = new IkenshoFrameEventProcesser() {
                public Dimension getDefaultWindowSize() {
                    return new Dimension(600, 500);
                }

                public Dimension getMinimumWindowSize() {
                    return new Dimension(600, 460);
                }
            };
            ACFrame.getInstance().setFrameEventProcesser(processer);
            ACFrame.getInstance().next(
                    new ACAffairInfo(IkenshoShijishoCSVImport.class.getName()));
            ACFrame.getInstance().setVisible(true);
        } catch (Exception ex) {
            VRLogger.info(ex);
        }

    }

    /**
     * This method initializes this
     * 
     * @return void
     */
    private void initialize() {
        this.add(getJContentPane(), VRLayout.CLIENT);
        this.setTitle("�㌩���V�X�e��Ver.2.5�@Macintosh�p�@�K��Ō�w�����f�[�^�ڍs�c�[��");
    }

    /**
     * This method initializes jContentPane
     * 
     * @return javax.swing.JPanel
     */
    private ACPanel getJContentPane() {
        if (contents == null) {
            contents = new ACPanel();
            contents.add(getMessage(), VRLayout.NORTH);
            contents.add(getButtons(), VRLayout.SOUTH);
            contents.add(getContents(), VRLayout.CLIENT);
        }
        return contents;
    }

    final String INITIAL_MESSAGE = "�@�K��Ō�w������CSV�o�̓c�[�����g���ċ�ver���璊�o����CSV�t�@�C���̕ۑ�����w�肵�A���s�{�^�����������Ă��������B";

    /**
     * This method initializes directorys
     * 
     * @return jp.nichicom.ac.component.ACLabel
     */
    private ACLabel getMessage() {
        if (message == null) {
            message = new ACLabel();
            message.setAutoWrap(true);
            message.setText(INITIAL_MESSAGE);
        }
        return message;
    }

    public void initAffair(ACAffairInfo affair) throws Exception {

        String ip = "unknown";
        String path = "unknown";
        boolean hasError = true;
        try {
            if (ACFrame.getInstance().hasProperty("DBConfig/Server")) {
                ip = ACFrame.getInstance().getProperty("DBConfig/Server");
                if (ACFrame.getInstance().hasProperty("DBConfig/Path")) {
                    path = ACFrame.getInstance().getProperty("DBConfig/Path");
                    hasError = false;
                }
            }
        } catch (Exception ex) {

        }
        StringBuffer sb = new StringBuffer();
        sb.append("IP�F ");
        sb.append(ip);
        sb.append(ACConstants.LINE_SEPARATOR);
        sb.append("�f�[�^�x�[�X�̏ꏊ�F ");
        sb.append(path);
        if (hasError) {
            sb.append(ACConstants.LINE_SEPARATOR);
            sb.append("���ڍs��̃f�[�^�x�[�X�ݒ���擾�ł��܂���B�㌩���{�̂��N�����A�ݒ肵�Ă��������B");
            getExecute().setEnabled(false);
            getDest().setForeground(Color.red);
        }
        getDest().setText(sb.toString());
    }

    public boolean canBack(VRMap parameters) throws Exception {
        return false;
    }

    public Component getFirstFocusComponent() {
        return getSourceDirectory();
    }

}
