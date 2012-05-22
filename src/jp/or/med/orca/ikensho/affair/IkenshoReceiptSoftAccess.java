package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.SwingConstants;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.text.ACKanaConvert;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.ac.util.splash.ACStopButtonSplash;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellEditor;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoReceiptSoftDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */

// 2011/10 Shin.Fujihara �R�����g�𐸍�
public class IkenshoReceiptSoftAccess extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACTable patients = new ACTable();
    private ACGroupBox accessSettings = new ACGroupBox();
    private ACLabelContainer hostNames = new ACLabelContainer();
    private ACTextField hostName = new ACTextField();
    private ACTextField userName = new ACTextField();
    private ACLabelContainer userNames = new ACLabelContainer();
    private ACTextField port = new ACTextField();
    private ACLabelContainer ports = new ACLabelContainer();
    private ACTextField password = new ACTextField();
    private ACLabelContainer passwords = new ACLabelContainer();
    private ACAffairButton receiptAccess = new ACAffairButton();
    private IkenshoCheckBoxTableCellEditor deleteCheckEditor = new IkenshoCheckBoxTableCellEditor();
    private VRArrayList importPatients = new VRArrayList();
    private ACTableModelAdapter patientsModelAdapter;
    private ACAffairButton patinetImport = new ACAffairButton();
    private ACTextField dbsVersion = new ACTextField();
    private ACLabelContainer dbsVersions = new ACLabelContainer();
    private VRList patientsData;
    private ACGroupBox filterSettings = new ACGroupBox();
    private ACButton nextPage = new ACButton();
    private ACButton previewPage = new ACButton();
    private ACLabelContainer hospitalIDContainer = new ACLabelContainer();
    private ACTextField hospitalID = new ACTextField();
    private ACLabelContainer patientIDContainer = new ACLabelContainer();
    private ACTextField patientID = new ACTextField();
    private ACLabel foundCountValue = new ACLabel();
    private ACLabel foundCountUnit = new ACLabel();
    private ACLabel viewIndexBegin = new ACLabel();
    private ACLabel viewIndexBeginUnit = new ACLabel();
    private ACLabel viewIndexEnd = new ACLabel();
    private ACLabel viewIndexEndUnit = new ACLabel();
    private ACCheckBox useKanaConvert = new ACCheckBox();
    private ACCheckBox usePatientIDFilter = new ACCheckBox();
    private ACBackLabelContainer patientIDFilters = new ACBackLabelContainer();
    private ACComboBox checkEditMethod = new ACComboBox();
    private ACButton checkEdit = new ACButton();
    private ACLabelContainer checkEditMethodContainer = new ACLabelContainer();
    private int foundCount = 0;
    private int pageBegin = 0;
    // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
    private ACComboBox versionCombo = null;
    private ACLabelContainer versionContainer = null;
    // �����\�����̃��Z�v�g�\�t�g�o�[�W�����R���{�̐ݒ�l
    private final int DEFAULT_RECEIPT_VERSION_INDEX = 1;
    // 2007/11/26 [Masahiko Higuchi] add - end

    // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
    private ACGroupBox findSettings;
    private ACBackLabelContainer ageBackContainer;
    private ACLabelContainer ageStartContainer;
    private ACLabelContainer ageEndContainer;
    private ACTextField ageStartText;
    private ACTextField ageEndText;
    private ACLabel ageLabel;
    private ACLabel ageKaraLabel;
    private ACCheckBox deduplicationCheck;
    private ACCheckBox allPageCheck;
    private ACPanel checkContentsPanel;
    private ACPanel filterRightPanel;
    // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end

    /**
     * 1�y�[�W������̕\�������ł��B
     */
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
    // private final int PAGE_COUNT = 100;
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
    // �\����500���ɕύX
    private final int PAGE_COUNT = 500;
    
    // �����Z����f�[�^���擾�����Ƃ��̏�����ޔ�(���PTNUM�擾�Ɏg�p)
    private Map receiptSetting = new HashMap();
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

    public void initAffair(ACAffairInfo affair) {
        setStatusText("����W�����Z�v�g�\�t�g�A�g");
        String ip = "127.0.0.1";
        String dbsVer = "1.2.2";
        String port = "8013";
        String user = "ormaster";
        String pass = "ormaster";
        // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
        int receiptVersion = DEFAULT_RECEIPT_VERSION_INDEX;
        // 2007/11/26 [Masahiko Higuchi] add - end

        try {
            // �ߋ��̒ʐM�ݒ��ǂݍ���
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/IP")) {
                ip = ACFrame.getInstance().getProperty("ReceiptAccess/IP");
            }
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/DBSVersion")) {
                dbsVer = ACFrame.getInstance().getProperty(
                        "ReceiptAccess/DBSVersion");
            }
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/Port")) {
                port = ACFrame.getInstance().getProperty("ReceiptAccess/Port");
            }
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/UserName")) {
                user = ACFrame.getInstance().getProperty(
                        "ReceiptAccess/UserName");
            }
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/Password")) {
                pass = ACFrame.getInstance().getProperty(
                        "ReceiptAccess/Password");
            }
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/HospitalID")) {
                hospitalID.setText(ACFrame.getInstance().getProperty(
                        "ReceiptAccess/HospitalID"));
            }
            if (ACFrame.getInstance().hasProperty("ReceiptAccess/KanaConvert")) {
                useKanaConvert.setSelected("true".equals(ACFrame.getInstance()
                        .getProperty("ReceiptAccess/KanaConvert")));
            }
            // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
            if (ACFrame.getInstance().hasProperty(
                    "ReceiptAccess/ReceiptSoftVersion")) {
                receiptVersion = ACCastUtilities.toInt(ACFrame.getInstance()
                        .getProperty("ReceiptAccess/ReceiptSoftVersion"),
                        DEFAULT_RECEIPT_VERSION_INDEX);
            }
            // 2007/11/26 [Masahiko Higuchi] add - end
        } catch (Exception ex) {

        }
        dbsVersion.setText(dbsVer);
        hostName.setText(ip);
        userName.setText(user);
        password.setText(pass);
        this.port.setText(port);
        // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
        getVersionCombo().setSelectedIndex(receiptVersion);
        // 2007/11/26 [Masahiko Higuchi] add - end

        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        // �擾�����̏����l�ݒ�
        // �N���65�`
        getAgeStartText().setText("65");
        // �d�����O�`�F�b�N�I��
        getDeduplicationCheck().setSelected(true);
        // ��荞�ݐݒ�̏����ݒ�
        getAllPageCheck().setSelected(true);

        // �ޔ�p�e�[�u���̏�����
        initReceiptAccessSpace();
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
    }

    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
    // �ʐM�Ŏ擾�����f�[�^�̑ޔ�̈�m��
    // �e�[�u�����C�A�E�g�ύX�ɔ����AIkenshoReceiptSoftDBManager�ōs���Ă���CREATE TABLE������
    // �Ɩ��N�����ɍs���悤�C��
    private void initReceiptAccessSpace() {

        try {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sql = null;

            // RECEIPT_ACCESS_SPACE�����݂��邩�A�K�v�ȃt�B�[���h�����݂��邩�m�F
            // ��������t�B�[���h�́A
            // LOCAL_IP(���o�[�W�����ł�����)
            // HOSPNUM(����̃o�[�W�����Œǉ�)
            sql = new StringBuffer();
            sql.append("SELECT COUNT(*) AS F_EXIST FROM RDB$RELATION_FIELDS");
            sql.append(" WHERE");
            sql.append(" RDB$FIELD_NAME IN ('LOCAL_IP', 'HOSPNUM')");
            sql.append(" AND (RDB$RELATION_NAME = 'RECEIPT_ACCESS_SPACE')");

            VRList list = dbm.executeQuery(sql.toString());
            int count = ACCastUtilities.toInt(
                    ((VRMap) list.getData()).get("F_EXIST"), 0);

            switch (count) {
            // ��������0�̏ꍇ�́A���������e�[�u�������݂��Ȃ�
            case 0:

                sql = new StringBuffer();

                sql.append("CREATE TABLE");
                sql.append(" RECEIPT_ACCESS_SPACE");
                sql.append(" (");
                sql.append(" LOCAL_IP VARCHAR(30) NOT NULL");
                sql.append(",SERIAL_ID INTEGER NOT NULL");
                sql.append(",HOSPNUM INTEGER NOT NULL");
                sql.append(",CHECKED INTEGER NOT NULL");
                sql.append(",PTID VARCHAR(10)");
                sql.append(",NAME VARCHAR(100)");
                sql.append(",KANANAME VARCHAR(100)");
                sql.append(",SEX CHAR(1)");
                sql.append(",BIRTHDAY CHAR(8)");
                sql.append(",HOME_POST VARCHAR(7)");
                sql.append(",HOME_ADRS VARCHAR(200)");
                sql.append(",HOME_BANTI VARCHAR(200)");
                sql.append(",HOME_TEL1 VARCHAR(15)");
                sql.append(",LAST_TIME TIMESTAMP");
                sql.append(",PRIMARY KEY (");
                sql.append(" LOCAL_IP");
                sql.append(",SERIAL_ID");
                sql.append(" )");
                sql.append(")");

                dbm.executeUpdate(sql.toString());
                dbm.commitTransaction();
                break;

            // ��������1�̏ꍇ�́A�e�[�u���͑��݂��邪�A����ǉ�����HOSPNUM�ACHECKED�̃t�B�[���h�����݂��Ȃ�
            case 1:

                // ��̒ǉ����s
                dbm.executeUpdate("ALTER TABLE RECEIPT_ACCESS_SPACE ADD HOSPNUM INTEGER NOT NULL");
                dbm.executeUpdate("ALTER TABLE RECEIPT_ACCESS_SPACE ADD CHECKED INTEGER NOT NULL");
                dbm.commitTransaction();
                break;

            // �������ʂ�2���A���Ă��Ă���΁A�␳�ς̂��߁A�����I��
            default:
                return;
            }

        } catch (Exception e) {
            ACMessageBox.showExclamation("�ޔ�̈�̊m�ۂɎ��s���܂����B");
            VRLogger.severe(e.getMessage());
        }

    }

    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoReceiptSoftAccess() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addFindTrigger(receiptAccess);
        addInsertTrigger(patinetImport);

        //2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
//        patients.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
//                new ACTableColumn(8, 22, "�@",
//                        new IkenshoCheckBoxTableCellRenderer(),
//                        deleteCheckEditor),
//                new ACTableColumn(9, 32, "�d��", SwingConstants.CENTER),
//                // 2011/10 Shin.Fujihara�v�]�ɂ��A����ID�̕\���𕜊�
//                new ACTableColumn(0, 60, "����ID", SwingConstants.RIGHT),
//                new ACTableColumn(1, 100, "����"),
//                new ACTableColumn(2, 120, "�ӂ肪��"),
//                new ACTableColumn(3, 32, "����", SwingConstants.CENTER,
//                        IkenshoConstants.FORMAT_SEX),
//                new ACTableColumn(4, 32, "�N��", SwingConstants.RIGHT,
//                        IkenshoConstants.FORMAT_NOW_AGE),
//                new ACTableColumn(4, 110, "���N����",
//                        IkenshoConstants.FORMAT_ERA_YMD),
//                new ACTableColumn(5, 63, "�X�֔ԍ�"),
//                new ACTableColumn(6, 280, "�Z��"),
//                new ACTableColumn(7, 120, "�A����iTEL�j"), }));
//
//        patientsModelAdapter = new ACTableModelAdapter(importPatients,
//                new String[] { "CHART_NO", "PATIENT_NM", "PATIENT_KN", "SEX",
//                        "BIRTHDAY", "POST_CD", "ADDRESS", "TEL", "IMPORT_FLAG",
//                        "BATTING_FLAG" });
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        patients.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(0, 36, "No.", SwingConstants.RIGHT),
                new ACTableColumn(8, 22, "�@",
                        new IkenshoCheckBoxTableCellRenderer(),
                        deleteCheckEditor),
                new ACTableColumn(9, 32, "�d��", SwingConstants.CENTER),
                new ACTableColumn(1, 100, "����"),
                new ACTableColumn(2, 120, "�ӂ肪��"),
                new ACTableColumn(3, 32, "����", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_SEX),
                new ACTableColumn(4, 32, "�N��", SwingConstants.RIGHT,
                        IkenshoConstants.FORMAT_NOW_AGE),
                new ACTableColumn(4, 110, "���N����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new ACTableColumn(5, 63, "�X�֔ԍ�"),
                new ACTableColumn(6, 280, "�Z��"),
                new ACTableColumn(7, 120, "�A����iTEL�j"), }));

        patientsModelAdapter = new ACTableModelAdapter(importPatients,
                new String[] { "SERIAL_ID", "PATIENT_NM", "PATIENT_KN", "SEX",
                        "BIRTHDAY", "POST_CD", "ADDRESS", "TEL", "IMPORT_FLAG",
                        "BATTING_FLAG" });
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        patients.setModel(patientsModelAdapter);

    }

    /**
     * �c�[���{�^���̗L����Ԃ�ݒ肵�܂��B
     * 
     * @throws ParseException
     */
    private void checkButtonsEnabled() throws ParseException {
        boolean enabled = getImportCheckedRows().size() > 0;
        patinetImport.setEnabled(enabled);
    }

    /**
     * ���������̊��҂����݂��邩���`�F�b�N���܂��B
     * 
     * @param destPatients ��r
     * @throws Exception ������O
     */
    protected void checkSameName(VRList destPatients) throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" PATIENT_NM");
        sb.append(",BIRTHDAY");
        sb.append(" FROM");
        sb.append(" PATIENT");
        VRArrayList nowPatients = (VRArrayList) dbm.executeQuery(sb.toString());
        dbm.finalize();

        int destEnd = destPatients.size();
        int nowEnd = nowPatients.size();
        for (int i = 0; i < nowEnd; i++) {
            VRMap nowRow = (VRMap) nowPatients.getData(i);
            String nowName = String.valueOf(VRBindPathParser.get("PATIENT_NM",
                    nowRow));
            String nowBirth = "";
            Object nowObj = VRBindPathParser.get("BIRTHDAY", nowRow);
            if (nowObj instanceof Date) {
                nowBirth = VRDateParser.format((Date) nowObj, "yyyyMMdd");
            }
            for (int j = 0; j < destEnd; j++) {
                VRMap destRow = (VRMap) destPatients.getData(j);
                String destName = String.valueOf(VRBindPathParser.get(
                        "PATIENT_NM", destRow));
                if (nowName.equals(destName)) {
                    String destBirth = "";
                    Object destObj = VRBindPathParser.get("BIRTHDAY", destRow);
                    if (destObj instanceof Date) {
                        destBirth = VRDateParser.format((Date) destObj,
                                "yyyyMMdd");
                    }
                    if (nowBirth.equals(destBirth)) {
                        VRBindPathParser.set("BATTING_FLAG", destRow, "����");
                        // destRow.setData("IMPORT_FLAG", new VRBoolean());
                        VRBindPathParser.set("IMPORT_FLAG", destRow,
                                new Boolean(false));
                    }
                }
            }
        }
    }

    protected VRList innerFind(String where) throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" RECEIPT_ACCESS_SPACE.SERIAL_ID");
        sb.append(",RECEIPT_ACCESS_SPACE.PTID");
        sb.append(",RECEIPT_ACCESS_SPACE.NAME");
        sb.append(",RECEIPT_ACCESS_SPACE.KANANAME");
        sb.append(",RECEIPT_ACCESS_SPACE.SEX");
        sb.append(",RECEIPT_ACCESS_SPACE.BIRTHDAY");
        sb.append(",RECEIPT_ACCESS_SPACE.HOME_POST");
        sb.append(",RECEIPT_ACCESS_SPACE.HOME_ADRS");
        sb.append(",RECEIPT_ACCESS_SPACE.HOME_BANTI");
        sb.append(",RECEIPT_ACCESS_SPACE.HOME_TEL1");
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        sb.append(",RECEIPT_ACCESS_SPACE.HOSPNUM");
        sb.append(",RECEIPT_ACCESS_SPACE.CHECKED");
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
        sb.append(" FROM");
        sb.append(" RECEIPT_ACCESS_SPACE");
        sb.append(where);
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        //���҂h�c��ǉ��ɔ����A���҂h�c���ɕ\������悤�ύX
        sb.append(" ORDER BY");
        sb.append(" SERIAL_ID");
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
        VRList result = dbm.executeQuery(sb.toString());
        // �����Z�`������ӌ����`���֕ϊ�
        toIkenshoData(result);

        // ���������`�F�b�N
        checkSameName(result);

        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        // �`�F�b�N��Ԃ𕜌�
        for (int i = 0; i < result.size(); i++) {
            VRMap row = (VRMap) result.getData(i);

            if (row.containsKey("CHECKED")) {
                if (ACCastUtilities.toInt(row.get("CHECKED"), 0) == 1) {
                    VRBindPathParser.set("IMPORT_FLAG", row, new Boolean(true));
                } else {
                    VRBindPathParser
                            .set("IMPORT_FLAG", row, new Boolean(false));
                }
            }
        }
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        patients.clearSelection();
        patientsData = result;
        patientsModelAdapter.setAdaptee(result);
        return result;
    }

    /**
     * �ŏ��̈ꗗ�y�[�W��\�����܂��B
     * 
     * @throws Exception ������O
     */
    protected void viewFirstPage() throws Exception {
        pageBegin = 1;
        readFromAccessSpace(1, PAGE_COUNT);
    }

    /**
     * �ꎞ�̈悩�犳�҂��擾���܂��B
     * 
     * @param begin �J�n�ԍ�
     * @param count ����
     * @throws Exception ������O
     */
    protected void readFromAccessSpace(int begin, int count) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append(" WHERE");
        sb.append(" (RECEIPT_ACCESS_SPACE.SERIAL_ID>=");
        sb.append(begin);
        sb.append(")AND(RECEIPT_ACCESS_SPACE.SERIAL_ID<");
        sb.append(begin + count);
        sb.append(")");
        
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        //������Q�Ή�
        sb.append(" AND(RECEIPT_ACCESS_SPACE.LOCAL_IP = '");
        sb.append(IkenshoReceiptSoftDBManager.getLocalIP());
        sb.append("')");
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
        
        VRList result = innerFind(sb.toString());

        viewIndexBegin.setText(String.valueOf(begin));
        viewIndexEnd.setText(String.valueOf(begin
                + Math.min(count, result.size()) - 1));
        nextPage.setEnabled(begin + count < foundCount);
        previewPage.setEnabled(begin - count > 0);
    }

    /**
     * ���Z�v�g�\�t�g������󂵂��f�[�^���ӌ����`���ɖ|�󂵂܂��B
     * 
     * @param src �����
     * @throws ParseException ������O
     */
    protected void toIkenshoData(VRList src) throws ParseException {
        int size = src.size();
        if (size <= 0) {
            return;
        }

        boolean encode = false;
        String osName = System.getProperty("os.name");
        // Mac�ȊO�ł���Ε����R�[�h�ϊ����s���B
        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
            encode = true;
        }

        VRMap checkRow = (VRMap) src.getData(0);
        if (VRBindPathParser.has("PTID", checkRow)) {
            // ����ID
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("PTID", row);
                if (val.length() > 20) {
                    // 20��������
                    val = val.substring(0, 20);
                }
                VRBindPathParser.set("CHART_NO", row,
                        getORCADecodeString(val, encode));
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("CHART_NO", row, "");
            }
        }
        if (VRBindPathParser.has("NAME", checkRow)) {
            // ����
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("NAME", row);
                if (val.length() > 15) {
                    val = val.substring(0, 15);
                }
                VRBindPathParser.set("PATIENT_NM", row,
                        getORCADecodeString(val, encode));
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("PATIENT_NM", row, "");
            }
        }
        if (VRBindPathParser.has("KANANAME", checkRow)) {
            // ���Ȏ���
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("KANANAME", row);
                if (val.length() > 30) {
                    val = val.substring(0, 30);
                }
                VRBindPathParser.set("PATIENT_KN", row,
                        getORCADecodeString(val, encode));
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("PATIENT_KN", row, "");
            }
        }
        if (VRBindPathParser.has("SEX", checkRow)) {
            // ����
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("SEX", row,
                        Integer.valueOf(getNotNullString("SEX", row)));
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("SEX", row, new Integer(0));
            }
        }
        if (VRBindPathParser.has("BIRTHDAY", checkRow)) {
            // �N��
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                Object obj = VRBindPathParser.get("BIRTHDAY", row);
                Calendar target;
                if (obj instanceof Date) {
                    target = Calendar.getInstance();
                    target.setTime((Date) obj);
                } else {
                    try {
                        target = Calendar.getInstance();
                        target.setTime(VRDateParser.parse(String.valueOf(obj)));
                    } catch (Exception ex) {
                        VRBindPathParser.set("AGE", row, new Integer(0));
                        VRBindPathParser.set("BIRTHDAY", row, null);
                        continue;
                    }
                }

                Calendar now = Calendar.getInstance();
                now.setLenient(true);
                now.add(Calendar.YEAR, 10);
                now.add(Calendar.DAY_OF_YEAR,
                        -target.get(Calendar.DAY_OF_YEAR) + 1);
                now.add(Calendar.YEAR, -target.get(Calendar.YEAR));

                VRBindPathParser.set("BIRTHDAY", row, target.getTime());
                VRBindPathParser.set("AGE", row,
                        new Integer(now.get(Calendar.YEAR) - 10));
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("AGE", row, "");
            }
        }

        if (VRBindPathParser.has("HOME_POST", checkRow)) {
            // �X�֔ԍ�
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("HOME_POST", row);
                if (val.length() < 7) {
                    if (val.length() >= 3) {
                        val = val.substring(0, 3) + "-" + val.substring(3);
                    }
                } else {
                    val = val.substring(0, 3) + "-" + val.substring(3, 7);
                }
                VRBindPathParser.set("POST_CD", row, val);
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("POST_CD", row, "");
            }
        }

        if (VRBindPathParser.has("HOME_ADRS", checkRow)) {
            // �Z��
            if (VRBindPathParser.has("HOME_BANTI", checkRow)) {
                for (int i = 0; i < size; i++) {
                    VRMap row = (VRMap) src.getData(i);
                    String val = getNotNullString("HOME_ADRS", row)
                            + getNotNullString("HOME_BANTI", row);
                    if (val.length() > 50) {
                        val = val.substring(0, 50);
                    }
                    VRBindPathParser.set("ADDRESS", row,
                            getORCADecodeString(val, encode));
                }
            } else {
                for (int i = 0; i < size; i++) {
                    VRMap row = (VRMap) src.getData(i);
                    String val = getNotNullString("HOME_ADRS", row);
                    if (val.length() > 50) {
                        val = val.substring(0, 50);
                    }
                    VRBindPathParser.set("ADDRESS", row,
                            getORCADecodeString(val, encode));
                }
            }
        } else if (VRBindPathParser.has("HOME_BANTI", checkRow)) {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("HOME_BANTI", row);
                if (val.length() > 50) {
                    val = val.substring(0, 50);
                }
                VRBindPathParser.set("ADDRESS", row,
                        getORCADecodeString(val, encode));
            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("ADDRESS", row, "");
            }
        }

        if (VRBindPathParser.has("HOME_TEL1", checkRow)) {
            // �d�b�ԍ�
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("HOME_TEL1", row);
                val = toNotTelCharReplace(val);
                String[] tels = val.split("-");
                if (tels.length == 0) {
                    VRBindPathParser.set("TEL1", row, "");
                    VRBindPathParser.set("TEL2", row, "");
                    VRBindPathParser.set("TEL", row, "");
                    continue;
                }
                if (tels[0].length() > 5) {
                    String[] telSrc;
                    if (tels.length < 2) {
                        telSrc = new String[2];
                    } else {
                        telSrc = tels;
                    }
                    telSrc[0] = tels[0].substring(0, 5);
                    if (tels[0].length() > 9) {
                        telSrc[1] = tels[0].substring(5, 9) + "-"
                                + tels[0].substring(9, tels[0].length());
                    } else {
                        telSrc[1] = tels[0].substring(5, tels[0].length())
                                + "-";
                    }
                    tels = telSrc;
                }

                StringBuffer telSB = new StringBuffer();
                if (!"".equals(tels[0])) {
                    telSB.append(tels[0]);
                    telSB.append("-");
                }

                VRBindPathParser.set("TEL1", row, tels[0]);
                switch (tels.length) {
                case 0:
                case 1:
                    VRBindPathParser.set("TEL2", row, "");
                    break;
                case 2:
                    VRBindPathParser.set("TEL2", row, tels[1]);
                    if (!"".equals(tels[1])) {
                        if (tels[1].length() > 5) {
                            telSB.append(tels[1].substring(0, 5));
                            telSB.append("-");

                            if (tels[1].length() > 9) {
                                telSB.append(tels[1].substring(5, 9));
                            } else {
                                telSB.append(tels[1].substring(5));
                            }
                        } else {
                            telSB.append(tels[1]);
                        }
                    }
                    break;
                case 3:
                    VRBindPathParser.set("TEL2", row, tels[1] + "-" + tels[2]);
                    if (!"".equals(tels[1])) {
                        telSB.append(tels[1]);
                    }
                    telSB.append("-");
                    if (!"".equals(tels[2])) {
                        telSB.append(tels[2]);
                    }
                    break;
                default:
                    VRBindPathParser.set("TEL2", row, tels[1] + "-" + tels[2]);
                    if (!"".equals(tels[1])) {
                        telSB.append(tels[1]);
                    }
                    telSB.append("-");
                    if (!"".equals(tels[2])) {
                        telSB.append(tels[2]);
                    }
                    break;
                }

                VRBindPathParser.set("TEL", row, telSB.toString());

            }
        } else {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                VRBindPathParser.set("TEL1", row, "");
                VRBindPathParser.set("TEL2", row, "");
                VRBindPathParser.set("TEL", row, "");
            }
        }
        for (int i = 0; i < size; i++) {
            VRMap row = (VRMap) src.getData(i);
            VRBindPathParser.set("IMPORT_FLAG", row, new Boolean(true));
            VRBindPathParser.set("BATTING_FLAG", row, "");
        }
    }

    /**
     * �����R�[�h�ϊ����ʂ�Ԃ��܂��B
     * 
     * @param data �ϊ���
     * @param encode �ϊ������s���邩
     * @return �ϊ�����
     */
    private String getORCADecodeString(String data, boolean encode) {
        String result = data;
        // 2006/02/11[Tozo Tanaka] : delete end
        // if (encode) {
        // try{
        // result = new String(data.getBytes("Shift_JIS"),"MS932");
        // } catch(Exception e){
        // VRLogger.warning("�����R�[�h�̕ϊ��Ɏ��s���܂����B");
        // }
        // }
        // 2006/02/11[Tozo Tanaka] : delete end
        return result;
    }

    protected void findActionPerformed(ActionEvent e) throws Exception {
        ACSplashable splash = null;

        try {
            // �ʐM�ݒ�̕ۑ�
            String port = this.port.getText();
            String ip = hostName.getText();
            String user = userName.getText();
            String pass = password.getText();
            String dbsVer = dbsVersion.getText();
            String hospID = hospitalID.getText();
            // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
            String receiptVersion = ACCastUtilities.toString(getVersionCombo()
                    .getSelectedIndex(), ACCastUtilities
                    .toString(DEFAULT_RECEIPT_VERSION_INDEX));
            // 2007/11/26 [Masahiko Higuchi] add - end
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/IP", ip);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/DBSVersion", dbsVer);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/Port", port);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/UserName", user);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/Password", pass);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/HospitalID", hospID);
            if (useKanaConvert.isSelected()) {
                ACFrame.getInstance().getPropertyXML()
                        .setForceValueAt("ReceiptAccess/KanaConvert", "true");
            } else {
                ACFrame.getInstance().getPropertyXML()
                        .setForceValueAt("ReceiptAccess/KanaConvert", "false");
            }
            // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
            ACFrame.getInstance()
                    .getPropertyXML()
                    .setForceValueAt("ReceiptAccess/ReceiptSoftVersion",
                            receiptVersion);
            // �p�X��null�łȂ����O�̂��߃`�F�b�N����
            if (pass != null) {
                // �󔒂��p�X���[�h�ɐݒ肳��Ă���ꍇ
                if (pass.indexOf(" ") != -1 || pass.indexOf("�@") != -1) {
                    ACMessageBox.showExclamation("���͂��ꂽ�p�X���[�h�ɋ󔒕������܂܂�Ă��܂��B"
                            + ACConstants.LINE_SEPARATOR
                            + "�󔒕������܂񂾃p�X���[�h�͎g�p�ł��܂���B");
                    // �����I��
                    return;
                }
            }

            // 2007/11/26 [Masahiko Higuchi] add - end
            // 2006/02/12[Tozo Tanaka] : replace begin
            // ACFrame.getInstance().getProperityXML().write();
            if (!ACFrame.getInstance().getPropertyXML().writeWithCheck()) {
                return;
            }
            // 2006/02/12[Tozo Tanaka] : replace end

            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
            if (!checkReceiptFindKey()) {
                return;
            }
            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

            IkenshoReceiptSoftDBManager dbm;
            try {
                dbm = new IkenshoReceiptSoftDBManager(ip,
                        Integer.parseInt(port), user, pass, dbsVer);
            } catch (Exception ex) {
                ACMessageBox
                        .showExclamation("�ڑ���̐ݒ肪�s���ł��B�ڑ���z�X�g��|�[�g�ԍ����������Ă��������B");
                return;
            }

            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
            // �i�荞�ݏ������擾
            VRMap refiners = new VRHashMap();
            refiners.put("AGE_START", getAgeStartText().getText());
            refiners.put("AGE_END", getAgeEndText().getText());
            refiners.put("DEDUPLICATION", new Boolean(getDeduplicationCheck()
                    .isSelected()));
            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

            // tbl_ptinf�擾�X�g�A�h�v���V�[�W��
            int count = 0;
            try {

                // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
                if (getVersionCombo().getSelectedIndex() != DEFAULT_RECEIPT_VERSION_INDEX) {
                    // 2007/11/26 [Masahiko Higuchi] add - end

                    String key = "all";
                    VRMap param = null;
                    if (usePatientIDFilter.isSelected()) {
                        if ("".equals(hospID)) {
                            ACMessageBox.showExclamation("��Ë@��ID�������͂ł��B"
                                    + ACConstants.LINE_SEPARATOR
                                    + "����W�����Z�v�g�\�t�g�́u�V�X�e���Ǘ��}�X�^�v���Q�Ƃ��邩�A"
                                    + ACConstants.LINE_SEPARATOR
                                    + "�T�|�[�g�x���_�ɂ��₢���킹���������B");
                            return;
                        }
                        String id = patientID.getText();
                        if ("".equals(id)) {
                            ACMessageBox.showExclamation("���҃J�i�����������͂ł��B");
                            return;
                        }

                        param = new VRHashMap();
                        param.put("tbl_ptinf.HOSPID", hospID);
                        param.put("tbl_ptinf.KANANAME", id + "%");
                        key = "key3";
                    } else {
                        if (ACMessageBox.showOkCancel("���ׂĂ̊��҂��������܂��B��낵���ł����H"
                                + ACConstants.LINE_SEPARATOR
                                + "��100��������5�b���x������܂��B",
                                ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                            return;
                        }
                    }

                    // �X�v���b�V���̏���
                    ACFrameEventProcesser processer = ACFrame.getInstance()
                            .getFrameEventProcesser();
                    if (processer instanceof IkenshoFrameEventProcesser) {
                        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit -
                        // begin
                        String osName = System.getProperty("os.name");
                        // Mac�ȊO�ł���΃X�g�b�v�X�v���b�V�����g��
                        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
                            splash = new ACStopButtonSplash();
                        } else {
                            splash = new ACSplash();
                        }
                        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit -
                        // end
                        ((ACSplash) splash)
                                .setIconPathes(((IkenshoFrameEventProcesser) processer)
                                        .getSplashFilePathes());
                        Dimension d = ((IkenshoFrameEventProcesser) processer)
                                .getSplashWindowSize();
                        if (d != null) {
                            d = new Dimension((int) d.getWidth(),
                                    (int) d.getHeight() + 20);
                        }

                        ((ACSplash) splash).refreshSize(d);
                        if (!((ACSplash) splash).isVisible()) {
                            ((ACSplash) splash).showModaless("�f�[�^�ʐM");
                        }

                    }

                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
                    // count = dbm.executeQuery("tbl_ptinf", key, param,
                    // splash);
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition -
                    // begin
                    count = dbm.executeQuery("tbl_ptinf", key, param, splash,
                            refiners);
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                    // result = dbm.executeQueryProcedure("all");

                    // 2007/11/26 [Masahiko Higuchi] edit - begin �����Z�A�g�Ή� v3.0.4
                } else {
                    // �����Z�V�o�[�W�����̏ꍇ
                    if (ACMessageBox.showOkCancel(
                            "���ׂĂ̊��҂��������܂��B��낵���ł����H"
                                    + ACConstants.LINE_SEPARATOR
                                    + "��100��������5�b���x������܂��B",
                            ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return;
                    }
                    // �X�v���b�V���̏���
                    ACFrameEventProcesser processer = ACFrame.getInstance()
                            .getFrameEventProcesser();
                    if (processer instanceof IkenshoFrameEventProcesser) {
                        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit -
                        // begin
                        String osName = System.getProperty("os.name");
                        // Mac�ȊO�ł���΃X�g�b�v�X�v���b�V�����g��
                        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
                            splash = new ACStopButtonSplash();
                        } else {
                            splash = new ACSplash();
                        }
                        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit -
                        // end

                        ((ACSplash) splash)
                                .setIconPathes(((IkenshoFrameEventProcesser) processer)
                                        .getSplashFilePathes());
                        Dimension d = ((IkenshoFrameEventProcesser) processer)
                                .getSplashWindowSize();
                        if (d != null) {
                            d = new Dimension((int) d.getWidth(),
                                    (int) d.getHeight() + 20);
                        }

                        ((ACSplash) splash).refreshSize(d);
                        if (!((ACSplash) splash).isVisible()) {
                            ((ACSplash) splash).showModaless("�f�[�^�ʐM");
                        }

                    }
                    // HOSPNUM�擾�p�̊֐�����`
                    String key = "key";
                    VRMap initialParam = new VRHashMap();
                    HashMap hospNumResult = new HashMap();
                    initialParam.put("tbl_sysuser.USERID", user);

                    // �ʐM����
                    dbm.executeSetUp();

                    // ��UHOSPNUM���擾����B
                    hospNumResult = dbm.executeQueryData("tbl_sysuser", key,
                            initialParam);

                    // COMMIT
                    dbm.commitTransaction();

                    // ���ҏ��擾�p�̊֐�����`
                    key = "all";
                    // �ϐ�����
                    VRMap findParam = new VRHashMap();
                    // HOSPNUM�̎擾
                    Integer hospNum = ACCastUtilities.toInteger(
                            hospNumResult.get("tbl_sysuser.HOSPNUM"), 0);
                    // �����L�[�ݒ�
                    findParam.put("tbl_ptinf.HOSPNUM", hospNum);
                    // ���ҏ��擾
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
                    // count = dbm.executeQuery("tbl_ptinf", key, findParam,
                    // splash);
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition -
                    // begin
                    count = dbm.executeQuery("tbl_ptinf", key, findParam,
                            splash, refiners);
                    
                    //�����Z�Ƃ̐ڑ��ݒ��ޔ����Ă���
                    receiptSetting.put("IP", ip);
                    receiptSetting.put("PORT", port);
                    receiptSetting.put("USER", user);
                    receiptSetting.put("PASS", pass);
                    receiptSetting.put("DBSVER", dbsVer);
                    receiptSetting.put("HOSPNUM", hospNum);
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                    // �I������
                    dbm.close();

                }
                // 2007/11/26 [Masahiko Higuchi] edit - end
            } catch (Exception ex) {
                splash = closeSplash(splash);
                Throwable cause = ex.getCause();
                if (cause instanceof java.net.ConnectException) {
                    ACMessageBox.showExclamation("�ڑ��Ɏ��s���܂����B");
                    return;
                } else if (cause instanceof UnknownHostException) {
                    ACMessageBox
                            .showExclamation("�ڑ��Ɏ��s���܂����B�ڑ���z�X�g�̐ݒ���������Ă��������B");
                    return;
                } else if (cause instanceof SocketException) {
                    ACMessageBox
                            .showExclamation("�ڑ��Ɏ��s���܂����B�ʐM��ؒf���ꂽ�\������܂��B�ēx���s���Ă��������B");
                    return;
                } else if (ex.getMessage().indexOf("invalid version") >= 0) {
                    ACMessageBox.showExclamation("DBS�̃o�[�W�������قȂ�܂��B");
                    return;
                } else if (ex.getMessage().indexOf("authentication error") >= 0) {
                    ACMessageBox
                            .showExclamation("�F�؂Ɏ��s���܂����B���[�U�[������уp�X���[�h���s���ł��B");
                    return;
                } else if (ex.getMessage().indexOf("other error.[-7]") >= 0) {
                    String id = patientID.getText();
                    if ((id.length() > 9) && (Long.parseLong(id) > 2147483647)) {
                        ACMessageBox
                                .showExclamation("2147483647�𒴂��銳��ID�̎�M�Ɏ��s���܂����B"
                                        + ACConstants.LINE_SEPARATOR
                                        + "�ڑ���DBS�̃T�|�[�g�Z���^�[�ɂ��A�����������B");
                        return;
                    }

                    // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
                } else if ("Connection reset".equals(ex.getMessage())) {
                    // �����Z�̃o�[�W�����I���Ƀ~�X�̉\���L��
                    ACMessageBox.showExclamation("�ڑ��Ɏ��s���܂����B"
                            + ACConstants.LINE_SEPARATOR
                            + "�ʐM�̐ؒf�A�������͓���W�����Z�v�g�\�t�g�̃o�[�W�������قȂ�\��������܂��B"
                            + ACConstants.LINE_SEPARATOR + "�ڑ��ݒ���m�F���Ă��������B");
                    dbm.close();
                    return;
                }
                // 2007/11/26 [Masahiko Higuchi] add - end
                throw ex;
            }

            if (count <= 0) {
                splash = closeSplash(splash);
                ACMessageBox.show("���ҏ�񂪑��݂��܂���B");
                return;
            }

            setStatusText(count + "���擾���܂����B");
            foundCount = count;
            foundCountValue.setText(String.valueOf(count));

            // �ŏ���PAGE_COUNT�����\��
            viewFirstPage();
            splash = closeSplash(splash);

            ACMessageBox.show("���ҏ����擾���܂����B");

            patinetImport.setEnabled(true);
        } finally {
            splash = closeSplash(splash);
        }
    }

    public boolean canClose() throws Exception {
        try {
            // ���ԃe�[�u����j��
            IkenshoReceiptSoftDBManager
                    .clearAccessSpace(new IkenshoFirebirdDBManager());
        } catch (Exception ex) {
        }
        return super.canClose();
    }

    /**
     * �X�v���b�V���������܂��B
     * 
     * @param splash �����Ώۂ̃X�v���b�V��
     * @return �đ�����ׂ��X�v���b�V��
     */
    protected ACSplashable closeSplash(ACSplashable splash) {
        if (splash != null) {
            splash.close();
        }
        return null;
    }

    /**
     * ���������̃`�F�b�N���s���܂��B
     * 
     * @return
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @throws Exception
     */
    private boolean checkReceiptFindKey() throws Exception {

        String ageStartString = getAgeStartText().getText();
        String ageEndString = getAgeEndText().getText();
        int ageStart = ACCastUtilities.toInt(ageStartString, 0);
        int ageEnd = ACCastUtilities.toInt(ageEndString, 0);

        if (!"".equals(ageStartString) && !"".equals(ageEndString)) {
            if (ageStart > ageEnd) {
                ACMessageBox.showExclamation("�N��̊J�n�ƏI���̓��͂��t�]���Ă��܂��B");
                getAgeStartContainer().transferFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * �����ȊO�����������������Ԃ��܂��B
     * 
     * @param src �ϊ���
     * @return �ϊ�����
     */
    protected String toNotTelCharReplace(String src) {
        StringBuffer sb = new StringBuffer();
        int end = src.length();
        for (int i = 0; i < end; i++) {
            char c = src.charAt(i);
            if ((c == '-') || ((c >= '0') && (c <= '9'))) {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * �w��o�C���h�p�X�̒l�𕶎���Ƃ��ĕԂ��܂��BNull�Ȃ�΋󕶎���Ԃ��܂��B
     * 
     * @param path �o�C���h�p�X
     * @param row ���o��
     * @throws ParseException ������O
     * @return �ϊ�����
     */
    protected String getNotNullString(String path, VRMap row)
            throws ParseException {
        Object obj = VRBindPathParser.get(path, row);
        if (obj == null) {
            return "";
        }
        return String.valueOf(obj);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
        patients.stopCellEditing("IMPORT_FLAG");

        if (doInsert()) {
            if (patientsData != null) {
                checkSameName(patientsData);
                patients.invalidate();
                patients.repaint();
            }

            ACMessageBox.show("���ҏ�����荞�݂܂����B");
        }
    }

    /**
     * �ǉ��������s���܂��B
     * 
     * @throws Exception ������O
     * @return boolean �ǉ������ɐ���������
     */
    private boolean doInsert() throws Exception {
        
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        ACSplashable splash = ACFrame.getInstance().getFrameEventProcesser().createSplash("�����Z�f�[�^");
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        IkenshoFirebirdDBManager dbm = null;
        try {
            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();

            VRArrayList importPatients = getImportCheckedRows();
            int end = importPatients.getDataSize();
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) importPatients.getData(i);

                StringBuffer sb = new StringBuffer();
                sb.append("INSERT INTO");
                sb.append(" PATIENT");
                sb.append(" (");
                sb.append(" PATIENT_NM");
                // 2011/10/31[Shin Fujihara]�v�]�ɂ�蕜��
                sb.append(",CHART_NO");
                sb.append(",PATIENT_KN");
                sb.append(",SEX");
                sb.append(",BIRTHDAY");
                sb.append(",AGE");
                sb.append(",POST_CD");
                sb.append(",ADDRESS");
                sb.append(",TEL1");
                sb.append(",TEL2");
                sb.append(",KOUSIN_DT");
                sb.append(",LAST_TIME");
                // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
                sb.append(",PTID");
                sb.append(",HOSPNUM");
                // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                sb.append(" )");
                sb.append(" VALUES");
                sb.append(" (");
                sb.append(getDBSafeString("PATIENT_NM", row));
                sb.append(",");
                // 2006/02/09[Tozo Tanaka] : replace begin
                // sb.append(getDBSafeString("PATIENT_KN", row));
                // 2011/10/31[Shin Fujihara]�v�]�ɂ�蕜��
                sb.append(getDBSafeString("CHART_NO", row));
                sb.append(",");
                String kana = getDBSafeString("PATIENT_KN", row);
                if (useKanaConvert.isSelected()) {
                    kana = ACKanaConvert.toHiragana(kana);
                }
                sb.append(kana);
                // 2006/02/09[Tozo Tanaka] : replace end
                sb.append(",");
                sb.append(getDBSafeNumber("SEX", row));
                sb.append(",");
                sb.append(getDBSafeDate("BIRTHDAY", row));
                sb.append(",");
                sb.append(getDBSafeString("AGE", row));
                sb.append(",");
                sb.append(getDBSafeString("POST_CD", row));
                sb.append(",");
                sb.append(getDBSafeString("ADDRESS", row));
                sb.append(",");
                sb.append(getDBSafeString("TEL1", row));
                sb.append(",");
                sb.append(getDBSafeString("TEL2", row));
                sb.append(",CURRENT_TIMESTAMP");
                sb.append(",CURRENT_TIMESTAMP");
                // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
                sb.append(",");
                sb.append(getDBSafeNumber("PTID", row));
                sb.append(",");
                sb.append(getDBSafeNumber("HOSPNUM", row));
                // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                sb.append(")");

                dbm.executeUpdate(sb.toString());

            }
            
            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
            // 500���ڈȍ~����荞�݂����s�����ꍇ�́A�f�[�^�x�[�X��̃t���O�����`�F�b�N�ɕύX����
            if (getAllPageCheck().isSelected()) {
                StringBuffer sb = new StringBuffer();
                sb.append("UPDATE");
                sb.append(" RECEIPT_ACCESS_SPACE");
                sb.append(" SET CHECKED = 0");
                sb.append(" WHERE");
                sb.append(" (LOCAL_IP = '");
                sb.append(IkenshoReceiptSoftDBManager.getLocalIP());
                sb.append("')");
                sb.append(" AND(CHECKED = 1)");
                
                dbm.executeUpdate(sb.toString());
            }
            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

            dbm.commitTransaction();
            dbm.finalize();

        } catch (Exception ex) {
            if (dbm != null) {
                dbm.rollbackTransaction();
                dbm.finalize();
            }
            throw ex;
        }
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        finally {
            if(splash!=null){
                splash.close();
                splash = null;
            }
        }
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        return true;
    }

    /**
     * ��荞�݃`�F�b�N�̂����s�f�[�^�W����Ԃ��܂��B
     * 
     * @throws ParseException ��͗�O
     * @return ArrayList �s�f�[�^�W��
     */
    private VRArrayList getImportCheckedRows() throws ParseException {
        VRArrayList rows = new VRArrayList();

        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
        /*
         * int end = patientsData.getDataSize(); for (int i = 0; i < end; i++) {
         * VRMap row = (VRMap) patientsData.getData(i); Object obj =
         * row.getData("IMPORT_FLAG"); if (obj instanceof Boolean) { Boolean val
         * = (Boolean) obj; if (val.booleanValue()) { rows.add(row); } } }
         */
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        // �S����ΏۂƂ�����荞��
        if (getAllPageCheck().isSelected()) {
            try {
                // ��U��Ԃ�ۑ�
                saveCheckState();

                IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                StringBuffer sql = new StringBuffer();
                sql.append("SELECT");
                sql.append(" RECEIPT_ACCESS_SPACE.SERIAL_ID");
                sql.append(",RECEIPT_ACCESS_SPACE.PTID");
                sql.append(",RECEIPT_ACCESS_SPACE.NAME");
                sql.append(",RECEIPT_ACCESS_SPACE.KANANAME");
                sql.append(",RECEIPT_ACCESS_SPACE.SEX");
                sql.append(",RECEIPT_ACCESS_SPACE.BIRTHDAY");
                sql.append(",RECEIPT_ACCESS_SPACE.HOME_POST");
                sql.append(",RECEIPT_ACCESS_SPACE.HOME_ADRS");
                sql.append(",RECEIPT_ACCESS_SPACE.HOME_BANTI");
                sql.append(",RECEIPT_ACCESS_SPACE.HOME_TEL1");
                sql.append(",RECEIPT_ACCESS_SPACE.HOSPNUM");
                sql.append(",RECEIPT_ACCESS_SPACE.CHECKED");
                sql.append(" FROM");
                sql.append(" RECEIPT_ACCESS_SPACE");
                sql.append(" WHERE");
                sql.append(" (RECEIPT_ACCESS_SPACE.LOCAL_IP = '");
                sql.append(IkenshoReceiptSoftDBManager.getLocalIP());
                sql.append("')");
                sql.append(" AND(RECEIPT_ACCESS_SPACE.CHECKED = 1)");
                rows = (VRArrayList) dbm.executeQuery(sql.toString());
                toIkenshoData(rows);

            } catch (Exception e) {
            }

            // �\�����Ă���f�[�^��Ώۂ̎�荞��(�����ʂ�)
        } else {
            int end = patientsData.getDataSize();
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) patientsData.getData(i);
                Object obj = row.getData("IMPORT_FLAG");
                if (obj instanceof Boolean) {
                    Boolean val = (Boolean) obj;
                    if (val.booleanValue()) {
                        rows.add(row);
                    }
                }
            }
        }
        
        // �����Z4.0.0�����Ƃ̘A�g���͂��̂܂�
        if (getVersionCombo().getSelectedIndex() != DEFAULT_RECEIPT_VERSION_INDEX) {
            return rows;
        }
        
        //�����Z4.0.0�ȏ�̏ꍇ�́A�J���e�ԍ����擾����
        IkenshoReceiptSoftDBManager receiptDbm = null;
        Integer hospNum = null;
        try {
            String ip = ACCastUtilities.toString(receiptSetting.get("IP"));
            int port = ACCastUtilities.toInt(receiptSetting.get("PORT"));
            String user = ACCastUtilities.toString(receiptSetting.get("USER"));
            String pass = ACCastUtilities.toString(receiptSetting.get("PASS"));
            String dbsVer = ACCastUtilities.toString(receiptSetting.get("DBSVER"));
            hospNum = ACCastUtilities.toInteger(receiptSetting.get("HOSPNUM"));
            
            
            receiptDbm = new IkenshoReceiptSoftDBManager(ip, port, user, pass, dbsVer);
            
        } catch (Exception e){
            VRLogger.warning("HOSPNUM�擾���A�����Z�[���Ƃ̐ڑ��Ɏ��s");
            return rows;
        }
        
        VRMap findParam = new VRHashMap();
        findParam.put("tbl_ptnum.HOSPNUM", hospNum);
        
        int count = rows.getDataSize();
        for (int i = 0; i < count; i++) {
            VRMap row = (VRMap)rows.get(i);
            
            String patientCode = ACCastUtilities.toString(row.get("PTID"), null);
            if (patientCode == null) {
                continue;
            }
            
            findParam.put("tbl_ptnum.PTID", patientCode);
            try {
                
                // �ʐM����
                receiptDbm.executeSetUp();
                Map sqlResult = receiptDbm.executeQueryData("tbl_ptnum", "key", findParam);
                // �g�����U�N�V�����̏I��
                receiptDbm.commitTransaction();
                
                patientCode = ACTextUtilities.trim(ACCastUtilities.toString(sqlResult.get("tbl_ptnum.PTNUM"), ""));
                row.put("CHART_NO", patientCode);
                
            } catch (Exception e) {
                VRLogger.warning(e);
            }
        }
        
        try {
            // DBClose
            receiptDbm.close();
            // �O�̂��ߖ����I�ɏ�����
            receiptDbm = null;
            
        } catch (Exception e) {
            VRLogger.warning(e);
        }
        
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        return rows;
    }

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ��������O
     */
    private void jbInit() throws Exception {
        accessSettings.setLayout(new VRLayout());
        hostNames.setText("�ڑ���z�X�g");
        hostName.setColumns(15);
        hostName.setMaxLength(255);
        hostName.setText("127.0.0.1");
        userName.setMaxLength(255);
        userName.setText("ormaster");
        userName.setColumns(15);
        userNames.setText("���[�U�[��");
        port.setMaxLength(5);
        port.setText("8013");
        port.setColumns(5);
        ports.setText("�|�[�g�ԍ�");
        port.setCharType(VRCharType.ONLY_NUMBER);
        password.setColumns(15);
        password.setMaxLength(255);
        password.setText("ormaster");
        passwords.setText("�p�X���[�h");

        accessSettings.setText("�ڑ��ݒ�");
        // 2006/02/11[Tozo Tanaka] : replace begin
        // buttons.setText("����W�����Z�v�g�\�t�g�A�W");
        buttons.setText("����W�����Z�v�g�\�t�g�A�g");
        // 2006/02/11[Tozo Tanaka] : replace end
        receiptAccess.setIconPath(ACConstants.ICON_PATH_OPEN_24);
        receiptAccess.setMnemonic('A');
        receiptAccess.setText("�ʐM(A)");
        receiptAccess.setToolTipText("��M�������ҏ�����荞�݂܂��B");

        patinetImport.setIconPath(ACConstants.ICON_PATH_UPDATE_24);
        patinetImport.setEnabled(false);
        patinetImport.setMnemonic('I');
        patinetImport.setText("��荞��(I)");
        patinetImport.setToolTipText("����W�����Z�v�g�\�t�g�ƒʐM���s�Ȃ��܂��B");
        dbsVersion.setColumns(10);
        dbsVersion.setMaxLength(10);
        dbsVersion.setText("1.2.2");
        dbsVersions.setText("DBS�o�[�W����");
        dbsVersions.add(dbsVersion, null);
        accessSettings.add(hostNames, VRLayout.FLOW_INSETLINE);
        accessSettings.add(ports, VRLayout.FLOW_INSETLINE);
        accessSettings.add(dbsVersions, VRLayout.FLOW_INSETLINE_RETURN);
        accessSettings.add(userNames, VRLayout.FLOW_INSETLINE);
        accessSettings.add(passwords, VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
        accessSettings.add(getVersionContainer(),
                VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/11/26 [Masahiko Higuchi] add - end
        passwords.add(password, null);
        hostNames.add(hostName, null);
        ports.add(port, null);
        userNames.add(userName, null);
        this.add(buttons, VRLayout.NORTH);
        buttons.add(patinetImport, VRLayout.EAST);
        buttons.add(receiptAccess, VRLayout.EAST);
        this.add(accessSettings, VRLayout.NORTH);
        // 2011/09/27 [ID:0000655][Masahiko Higuchi] add - begin �����Z�A�g�d�l�C��
        dbsVersions.setFollowChildEnabled(true);
        this.add(getFindSettings(), VRLayout.NORTH);
        // 2011/09/27 [ID:0000655][Masahiko Higuchi] add - end
        this.add(patients, VRLayout.CLIENT);

        // 2006/02/09[Tozo Tanaka] : add begin
        ((VRLayout) accessSettings.getLayout()).setAutoWrap(false);
        this.add(filterSettings, VRLayout.NORTH);
        // usePatientIDFilter.setText("ID�ōi�荞��");
        usePatientIDFilter.setText("�J�i�����ōi�荞��");
        patientIDFilters.setLabelFilled(true);
        patientIDFilters.setBackground(patientIDFilters.getFocusBackground());
        patientIDFilters.add(usePatientIDFilter, VRLayout.FLOW);
        patientIDFilters.add(patientIDContainer, VRLayout.FLOW);
        patientIDFilters.add(hospitalIDContainer, VRLayout.FLOW);
        // 2006/02/11[Tozo Tanaka] : delete begin
        // �w�茟����s�\�ɂ���
        // accessSettings.add(patientIDFilters, VRLayout.FLOW_DOUBLEINSETLINE);
        // 2006/02/11[Tozo Tanaka] : delete end
        filterSettings.setHgap(0);
        filterSettings.add(checkEditMethodContainer, VRLayout.FLOW);
        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
        getCheckContentsPanel().add(useKanaConvert, VRLayout.FLOW_RETURN);
        getCheckContentsPanel().add(getAllPageCheck(), VRLayout.FLOW_RETURN);
        filterSettings.add(getCheckContentsPanel(), VRLayout.FLOW);
        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end
        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit - begin
        getFilterRightPanel().add(previewPage, VRLayout.FLOW);
        String osName = System.getProperty("os.name");
        // Mac�ȊO�ł���Ή��ɕ��ׂ�
        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
            getFilterRightPanel().add(nextPage, VRLayout.FLOW);
        } else {
            getFilterRightPanel().add(nextPage, VRLayout.FLOW_RETURN);
        }
        getFilterRightPanel().add(foundCountValue, VRLayout.FLOW);
        getFilterRightPanel().add(foundCountUnit, VRLayout.FLOW);
        getFilterRightPanel().add(viewIndexBegin, VRLayout.FLOW);
        getFilterRightPanel().add(viewIndexBeginUnit, VRLayout.FLOW);
        getFilterRightPanel().add(viewIndexEnd, VRLayout.FLOW);
        getFilterRightPanel().add(viewIndexEndUnit, VRLayout.FLOW);
        filterSettings.add(getFilterRightPanel(), VRLayout.FLOW);
        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit - end
        checkEditMethodContainer.setText("�`�F�b�N");
        checkEditMethod.setEditable(false);
        checkEditMethod.setModel(new String[] { "���ׂđI��", "���ׂď��O", "�I�𔽓]",
                "�d�������O" });
        checkEditMethod.setSelectedIndex(0);
        checkEditMethod.setColumns(6);
        checkEdit.setText("���s");
        checkEditMethodContainer.add(checkEditMethod, VRLayout.FLOW);
        checkEditMethodContainer.add(checkEdit, VRLayout.FLOW);
        nextPage.setText("����");
        nextPage.setToolTipText("����" + PAGE_COUNT + "����\�����܂��B");
        nextPage.setEnabled(false);
        previewPage.setText("�O��");
        previewPage.setToolTipText("�O��" + PAGE_COUNT + "����\�����܂��B");
        previewPage.setEnabled(false);
        patientIDContainer.add(patientID, VRLayout.FLOW);
        patientIDContainer.setText("�J�i����");
        // patientIDContainer.setText("����ID");
        patientIDContainer.setEnabled(false);
        patientID.setColumns(10);
        patientID.setMaxLength(10);
        // 2006/02/11[Tozo Tanaka] : replace begin
        // patientID.setCharType(VRCharType.ONLY_ALNUM);
        patientID.setIMEMode(InputSubset.KANJI);
        // 2006/02/11[Tozo Tanaka] : replace end
        patientID.setEnabled(false);
        hospitalIDContainer.add(hospitalID, VRLayout.FLOW);
        hospitalIDContainer.setText("��Ë@��ID(JPN�`�Ȃ�)");
        hospitalIDContainer.setEnabled(false);
        hospitalID.setColumns(15);
        hospitalID.setMaxLength(15);
        hospitalID.setCharType(VRCharType.ONLY_ALNUM);
        hospitalID.setEnabled(false);
        filterSettings.setText("��荞�ݐݒ�");
        useKanaConvert.setText("�J�i�������Ђ炪�Ȃɕϊ�");
        foundCountValue.setText("0");
        foundCountValue.setFormat(NumberFormat.getNumberInstance());
        foundCountUnit.setText(" ���� ");
        viewIndexBegin.setText("0");
        viewIndexBegin.setFormat(NumberFormat.getNumberInstance());
        viewIndexBeginUnit.setText(" - ");
        viewIndexEnd.setText("0");
        viewIndexEnd.setFormat(NumberFormat.getNumberInstance());
        viewIndexEndUnit.setText(" ����");
        usePatientIDFilter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                boolean ena = usePatientIDFilter.isSelected();
                patientID.setEnabled(ena);
                patientIDContainer.setEnabled(ena);
                hospitalID.setEnabled(ena);
                hospitalIDContainer.setEnabled(ena);
            }
        });
        previewPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition -
                    // begin
                    saveCheckState();
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                    pageBegin -= PAGE_COUNT;
                    readFromAccessSpace(pageBegin, PAGE_COUNT);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });
        nextPage.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition -
                    // begin
                    saveCheckState();
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                    pageBegin += PAGE_COUNT;
                    readFromAccessSpace(pageBegin, PAGE_COUNT);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });
        checkEdit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (patientsData == null) {
                    return;
                }
                // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
                patients.stopCellEditing("IMPORT_FLAG");

                Iterator it = patientsData.iterator();
                switch (checkEditMethod.getSelectedIndex()) {
                case 0:// ���ׂđI��
                    while (it.hasNext()) {
                        ((Map) it.next()).put("IMPORT_FLAG", Boolean.TRUE);
                    }
                    break;
                case 1:// ���ׂď��O
                    while (it.hasNext()) {
                        ((Map) it.next()).put("IMPORT_FLAG", Boolean.FALSE);
                    }
                    break;
                case 2:// �I�𔽓]
                    while (it.hasNext()) {
                        Map row = (Map) it.next();
                        Boolean val;
                        if (Boolean.TRUE.equals(row.get("IMPORT_FLAG"))) {
                            val = Boolean.FALSE;
                        } else {
                            val = Boolean.TRUE;
                        }
                        row.put("IMPORT_FLAG", val);
                    }
                    break;
                case 3:// �d�����O
                    while (it.hasNext()) {
                        Map row = (Map) it.next();
                        if ("����".equals(row.get("BATTING_FLAG"))) {
                            row.put("IMPORT_FLAG", Boolean.FALSE);
                        }
                    }
                    break;
                }
                patients.invalidate();
                patients.repaint();
            }
        });

        // 2006/02/09[Tozo Tanaka] : add end

    }

    public boolean canBack(VRMap parameters) {
        // 2006/02/09[Tozo Tanaka] : add begin
        try {
            // ���ԃe�[�u����j��
            IkenshoReceiptSoftDBManager
                    .clearAccessSpace(new IkenshoFirebirdDBManager());
        } catch (Exception ex) {
        }
        // 2006/02/09[Tozo Tanaka] : add end
        return true;
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return hostName;
    }

    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
    // ��ʏ�̃`�F�b�N��Ԃ�ۑ�
    private void saveCheckState() throws Exception {
        if (patientsData == null) {
            return;
        }

        List checked = new ArrayList();
        List unchecked = new ArrayList();
        
        for (int i = 0; i < patientsData.size(); i++) {
            VRMap row = (VRMap) patientsData.getData(i);
            if (ACCastUtilities.toBoolean(row.get("IMPORT_FLAG"))) {
                checked.add(row.get("SERIAL_ID"));
            } else {
                unchecked.add(row.get("SERIAL_ID"));
            }
        }
        
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        
        if (!checked.isEmpty()) {
            saveCheckStateDetail(dbm, "1", checked);
        }
        
        if (!unchecked.isEmpty()) {
            saveCheckStateDetail(dbm, "0", unchecked);
        }

    }
    
    private void saveCheckStateDetail(IkenshoFirebirdDBManager dbm, String checkState, List list) throws Exception {
        
        StringBuffer sql = new StringBuffer();
        sql.append("UPDATE RECEIPT_ACCESS_SPACE SET CHECKED = ");
        sql.append(checkState);
        sql.append(" WHERE");
        sql.append(" (RECEIPT_ACCESS_SPACE.LOCAL_IP = '");
        sql.append(IkenshoReceiptSoftDBManager.getLocalIP());
        sql.append("')");
        sql.append(" AND (SERIAL_ID IN(");
        sql.append(list.get(0));
        for (int i = 1; i < list.size(); i++) {
            sql.append(",");
            sql.append(list.get(i));
        }
        sql.append("))");
        dbm.executeUpdate(sql.toString());
    }

    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

    /**
     * �����Z�o�[�W�������R���{���擾���܂��B
     * 
     * @return �R���{�{�b�N�X
     * @since v3.0.4
     * @author Masahiko Higuchi
     */
    public ACComboBox getVersionCombo() {
        if (versionCombo == null) {
            versionCombo = new ACComboBox();
            versionCombo.setEditable(false);
            versionCombo.setModel(new String[] { "version 4.0.0����",
                    "version 4.0.0�ȏ�" });
            versionCombo.setBlankable(false);
        }
        return versionCombo;
    }

    /**
     * ����W�����Z�v�g�\�t�g��񃉃x���R���e�i���擾���܂��B
     * 
     * @return ���x���R���e�i
     * @since v3.0.4
     * @author Masahiko Higuchi
     */
    public ACLabelContainer getVersionContainer() {
        if (versionContainer == null) {
            versionContainer = new ACLabelContainer();
            versionContainer.add(getVersionCombo());
            versionContainer.setText("����W�����Z�v�g�\�t�g�o�[�W����");
        }
        return versionContainer;
    }

    /**
     * �N�� �w�ʃR���e�i���擾���܂��B
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACBackLabelContainer getAgeBackContainer() {
        if (ageBackContainer == null) {
            ageBackContainer = new ACBackLabelContainer();
            ageBackContainer.add(getAgeStartContainer());
            ageBackContainer.add(getAgeKaraLabel());
            ageBackContainer.add(getAgeEndContainer());
        }

        return ageBackContainer;
    }

    /**
     * �N��R���e�i �J�n
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACLabelContainer getAgeStartContainer() {
        if (ageStartContainer == null) {
            ageStartContainer = new ACLabelContainer();
            ageStartContainer.add(getAgeStartText(), VRLayout.FLOW);
            ageStartContainer.add(getAgeLabel(), VRLayout.FLOW);
            ageStartContainer.setText("�N��");
        }
        return ageStartContainer;
    }

    /**
     * 
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACLabelContainer getAgeEndContainer() {
        if (ageEndContainer == null) {
            ageEndContainer = new ACLabelContainer();
            ageEndContainer.add(getAgeEndText(), VRLayout.FLOW);
            ageEndContainer.add(getAgeLabel(), VRLayout.FLOW);
        }
        return ageEndContainer;
    }

    /**
     * �N��e�L�X�g �J�n���擾���܂�
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACTextField getAgeStartText() {
        if (ageStartText == null) {
            ageStartText = new ACTextField();
            ageStartText.setMaxLength(3);
            ageStartText.setColumns(3);
            ageStartText.setBindPath("AGE_START");
        }
        return ageStartText;
    }

    /**
     * �N��e�L�X�g �I�����擾���܂�
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACTextField getAgeEndText() {
        if (ageEndText == null) {
            ageEndText = new ACTextField();
            ageEndText.setMaxLength(3);
            ageEndText.setColumns(3);
            ageEndText.setBindPath("AGE_END");
        }
        return ageEndText;
    }

    /**
     * �΃��x��
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACLabel getAgeLabel() {
        if (ageLabel == null) {
            ageLabel = new ACLabel();
            ageLabel.setText("��");
        }
        return ageLabel;
    }

    /**
     * �N�� ��؂� ���x��
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACLabel getAgeKaraLabel() {
        if (ageKaraLabel == null) {
            ageKaraLabel = new ACLabel();
            ageKaraLabel.setText("�`");
        }
        return ageKaraLabel;
    }

    /**
     * �d�����O�`�F�b�N���擾���܂�
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACCheckBox getDeduplicationCheck() {
        if (deduplicationCheck == null) {
            deduplicationCheck = new ACCheckBox();
            deduplicationCheck.setText("�o�^�ς݂̊��҂����O");
            deduplicationCheck.setBindPath("DEDUPLICATION");
        }
        return deduplicationCheck;
    }

    /**
     * ��荞�ݏ����O���[�v���擾���܂��B
     * 
     * @return ��荞�ݏ����O���[�v
     * @since 3.1.5
     * @author Masahiko.Higuchi
     */
    public ACGroupBox getFindSettings() {
        if (findSettings == null) {
            findSettings = new ACGroupBox();
            findSettings.setLayout(new VRLayout());
            findSettings.setHgap(0);
            findSettings.setVgap(0);
            findSettings.setText("�擾����");
            findSettings.add(getAgeBackContainer(), VRLayout.FLOW);
            findSettings.add(getDeduplicationCheck(), VRLayout.FLOW);
        }
        return findSettings;
    }

    /**
     * @return allPageCheck
     * @since
     * @author Masahiko.Higuchi
     */
    public ACCheckBox getAllPageCheck() {
        if (allPageCheck == null) {
            allPageCheck = new ACCheckBox();
            allPageCheck.setText(PAGE_COUNT + "���ڈȍ~�̊��҂���荞��");
            allPageCheck.setBindPath("ALLPAGE");
        }
        return allPageCheck;
    }

    /**
     * @return checkContentsPanel
     * @since 3.1.5
     * @author Masahiko.Higuchi
     */
    public ACPanel getCheckContentsPanel() {
        if (checkContentsPanel == null) {
            checkContentsPanel = new ACPanel();
            checkContentsPanel.setHgap(0);
            checkContentsPanel.setVgap(0);
        }
        return checkContentsPanel;
    }

    /**
     * 
     * @return
     * @since 3.1.5
     * @author Masahiko.Higuchi
     */
    public ACPanel getFilterRightPanel() {
        if (filterRightPanel == null) {
            filterRightPanel = new ACPanel();
            filterRightPanel.setVgap(0);
            filterRightPanel.setHgap(0);
            filterRightPanel.setAutoWrap(false);
        }
        return filterRightPanel;
    }

}