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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
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
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellEditor;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoReceiptSoftDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
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
    // 2006/02/09[Tozo Tanaka] : add begin
//    private VRArrayList patientsData;
    private VRList patientsData;
    // 2006/02/09[Tozo Tanaka] : replace begin
    // 2006/02/09[Tozo Tanaka] : add begin
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
    private ACButton checkEdit= new ACButton();
    private ACLabelContainer checkEditMethodContainer = new ACLabelContainer();
    private int foundCount=0; 
    private int pageBegin=0;
	// 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
    private ACComboBox versionCombo = null;
    private ACLabelContainer versionContainer = null;
    // �����\�����̃��Z�v�g�\�t�g�o�[�W�����R���{�̐ݒ�l
    private final int DEFAULT_RECEIPT_VERSION_INDEX = 1;
	// 2007/11/26 [Masahiko Higuchi] add - end
    
    /**
     * 1�y�[�W������̕\�������ł��B
     */
    private final int PAGE_COUNT = 100;
    // 2006/02/09[Tozo Tanaka] : add end

    public void initAffair(ACAffairInfo affair) {
        // 2006/02/11[Tozo Tanaka] : replace begin
//        setStatusText("����W�����Z�v�g�\�t�g�A�W");
        setStatusText("����W�����Z�v�g�\�t�g�A�g");
        // 2006/02/11[Tozo Tanaka] : replace begin
        
        // 2006/02/09[Tozo Tanaka] : add begin
        String ip = "127.0.0.1";
        String dbsVer = "1.2.2";
        String port = "8013";
        String user = "ormaster";
        String pass = "ormaster";
        // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
        int receiptVersion = DEFAULT_RECEIPT_VERSION_INDEX;
        // 2007/11/26 [Masahiko Higuchi] add - end
        
        try{
            //�ߋ��̒ʐM�ݒ��ǂݍ���
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/IP")){
                ip = ACFrame.getInstance().getProperty("ReceiptAccess/IP");
            }
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/DBSVersion")){
                dbsVer = ACFrame.getInstance().getProperty("ReceiptAccess/DBSVersion");
            }
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/Port")){
                port = ACFrame.getInstance().getProperty("ReceiptAccess/Port");
            }
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/UserName")){
                user = ACFrame.getInstance().getProperty("ReceiptAccess/UserName");
            }
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/Password")){
                pass = ACFrame.getInstance().getProperty("ReceiptAccess/Password");
            }
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/HospitalID")){
                hospitalID.setText(ACFrame.getInstance().getProperty("ReceiptAccess/HospitalID"));
            }
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/KanaConvert")){
                useKanaConvert.setSelected("true".equals(ACFrame.getInstance().getProperty("ReceiptAccess/KanaConvert")));
            }
            // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
            if(ACFrame.getInstance().hasProperty("ReceiptAccess/ReceiptSoftVersion")){
                receiptVersion = ACCastUtilities.toInt(ACFrame.getInstance()
                        .getProperty("ReceiptAccess/ReceiptSoftVersion"),
                        DEFAULT_RECEIPT_VERSION_INDEX);
            }
            // 2007/11/26 [Masahiko Higuchi] add - end
        }catch(Exception ex){
            
        }
        dbsVersion.setText(dbsVer);
        hostName.setText(ip);
        userName.setText(user);
        password.setText(pass);
        this.port.setText(port);
        // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
        getVersionCombo().setSelectedIndex(receiptVersion);
        // 2007/11/26 [Masahiko Higuchi] add - end
        // 2006/02/09[Tozo Tanaka] : add end
    }

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

        // 2006/02/09[Tozo Tanaka] : replace begin
//        patients.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
//                // new NCTableColumn(7, 22, " ", new
//                // NCCheckBoxTableCellRenderer(), editor),
//                new ACTableColumn(7, 22, "�@",
//                        new IkenshoCheckBoxTableCellRenderer(),
//                        deleteCheckEditor),
//                new ACTableColumn(8, 32, "�d��", SwingConstants.CENTER),
//                new ACTableColumn(0, 100, "����"),
//                new ACTableColumn(1, 120, "�ӂ肪��"),
//                new ACTableColumn(2, 32, "����", SwingConstants.CENTER,
//                        IkenshoConstants.FORMAT_SEX),
//                new ACTableColumn(3, 32, "�N��", SwingConstants.RIGHT,
//                        IkenshoConstants.FORMAT_NOW_AGE),
//                new ACTableColumn(3, 110, "���N����",
//                        IkenshoConstants.FORMAT_ERA_YMD),
//                new ACTableColumn(4, 63, "�X�֔ԍ�"),
//                new ACTableColumn(5, 280, "�Z��"),
//                new ACTableColumn(6, 120, "�A����iTEL�j"), }));
//
//        patientsModelAdapter = new ACTableModelAdapter(importPatients,
//                new String[] { "PATIENT_NM", "PATIENT_KN", "SEX", "BIRTHDAY",
//                        "POST_CD", "ADDRESS", "TEL", "IMPORT_FLAG",
//                        "BATTING_FLAG" });
        patients.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(8, 22, "�@",
                        new IkenshoCheckBoxTableCellRenderer(),
                        deleteCheckEditor),
                new ACTableColumn(9, 32, "�d��", SwingConstants.CENTER),
//                new ACTableColumn(0, 60, "����ID", SwingConstants.RIGHT),
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
                new String[] { "CHART_NO", "PATIENT_NM", "PATIENT_KN", "SEX", "BIRTHDAY",
                        "POST_CD", "ADDRESS", "TEL", "IMPORT_FLAG",
                        "BATTING_FLAG" });
        // 2006/02/09[Tozo Tanaka] : replace end

        patients.setModel(patientsModelAdapter);

        // editor.addCellEditorListener(new CellEditorListener(){
        // public void editingCanceled(ChangeEvent e) {
        // try {
        // checkButtonsEnabled();
        // }
        // catch (ParseException ex) {
        // }
        // }
        //
        // public void editingStopped(ChangeEvent e) {
        // try {
        // checkButtonsEnabled();
        // }
        // catch (ParseException ex) {
        // }
        // }
        //
        // });
        // deleteCheckEditor.addItemListener(
        // new ItemListener() {
        //
        // public void itemStateChanged(ItemEvent e) {
        // try {
        // checkButtonsEnabled();
        // }
        // catch (ParseException ex) {
        // }
        // }
        // });
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
    // 2006/02/09[Tozo Tanaka] : add begin
    protected VRList innerFind(String where)throws Exception{
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
        sb.append(" FROM");
        sb.append(" RECEIPT_ACCESS_SPACE");
        sb.append(where);
        VRList result = dbm.executeQuery(sb.toString());
        // �����Z�`������ӌ����`���֕ϊ�
        toIkenshoData(result);

        // ���������`�F�b�N
        checkSameName(result);

        patients.clearSelection();
        patientsData = result;
        patientsModelAdapter.setAdaptee(result);
        return result;
    }

    /**
     * �ŏ��̈ꗗ�y�[�W��\�����܂��B
     * @throws Exception ������O
     */
    protected void viewFirstPage() throws Exception{
        pageBegin = 1;
        readFromAccessSpace(1,PAGE_COUNT);
    }
    
    /**
     * �ꎞ�̈悩�犳�҂��擾���܂��B
     * @param begin �J�n�ԍ�
     * @param count ����
     * @throws Exception ������O
     */
    protected void readFromAccessSpace(int begin, int count)throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append(" WHERE");
        sb.append(" (RECEIPT_ACCESS_SPACE.SERIAL_ID>=");
        sb.append(begin);
        sb.append(")AND(RECEIPT_ACCESS_SPACE.SERIAL_ID<");
        sb.append(begin+count);
        sb.append(")");
        VRList result = innerFind(sb.toString());

        viewIndexBegin.setText(String.valueOf( begin));
        viewIndexEnd.setText(String.valueOf(begin+Math.min(count, result.size())-1));
        nextPage.setEnabled(begin+count<foundCount);
        previewPage.setEnabled(begin-count>0);
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
        //Mac�ȊO�ł���Ε����R�[�h�ϊ����s���B
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
                    //20��������
                    val = val.substring(0, 20);
                }
                VRBindPathParser.set("CHART_NO", row, getORCADecodeString(val, encode));
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
                VRBindPathParser.set("PATIENT_NM", row, getORCADecodeString(val, encode));
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
                VRBindPathParser.set("PATIENT_KN", row, getORCADecodeString(val, encode));
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
                VRBindPathParser.set("SEX", row, Integer
                        .valueOf(getNotNullString("SEX", row)));
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
                VRBindPathParser.set("AGE", row, new Integer(now
                        .get(Calendar.YEAR) - 10));
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
                    VRBindPathParser.set("ADDRESS", row, getORCADecodeString(val, encode));
                }
            } else {
                for (int i = 0; i < size; i++) {
                    VRMap row = (VRMap) src.getData(i);
                    String val = getNotNullString("HOME_ADRS", row);
                    if (val.length() > 50) {
                        val = val.substring(0, 50);
                    }
                    VRBindPathParser.set("ADDRESS", row, getORCADecodeString(val, encode));
                }
            }
        } else if (VRBindPathParser.has("HOME_BANTI", checkRow)) {
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("HOME_BANTI", row);
                if (val.length() > 50) {
                    val = val.substring(0, 50);
                }
                VRBindPathParser.set("ADDRESS", row, getORCADecodeString(val, encode));
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
     * @param data �ϊ���
     * @param encode �ϊ������s���邩
     * @return �ϊ�����
     */
    private String getORCADecodeString(String data, boolean encode){
        String result = data;
        // 2006/02/11[Tozo Tanaka] : delete end
//        if (encode) {
//            try{
//                result = new String(data.getBytes("Shift_JIS"),"MS932");
//            } catch(Exception e){
//                VRLogger.warning("�����R�[�h�̕ϊ��Ɏ��s���܂����B");
//            }
//        }
        // 2006/02/11[Tozo Tanaka] : delete end
        return result;
    }
    
    protected void findActionPerformed(ActionEvent e) throws Exception {
        ACSplashable splash=null;

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
                    .getSelectedIndex(), ACCastUtilities.toString(DEFAULT_RECEIPT_VERSION_INDEX));
            // 2007/11/26 [Masahiko Higuchi] add - end
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/IP", ip);
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/DBSVersion", dbsVer);
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/Port", port);
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/UserName", user);
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/Password", pass);
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/HospitalID", hospID);
            if (useKanaConvert.isSelected()) {
                ACFrame.getInstance().getPropertyXML().setForceValueAt(
                        "ReceiptAccess/KanaConvert", "true");
            } else {
                ACFrame.getInstance().getPropertyXML().setForceValueAt(
                        "ReceiptAccess/KanaConvert", "false");
            }
            // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
            ACFrame.getInstance().getPropertyXML().setForceValueAt(
                    "ReceiptAccess/ReceiptSoftVersion", receiptVersion);
            // �p�X��null�łȂ����O�̂��߃`�F�b�N����
            if(pass != null){
                // �󔒂��p�X���[�h�ɐݒ肳��Ă���ꍇ
                if(pass.indexOf(" ") != -1 || pass.indexOf("�@") != -1 ){
                    ACMessageBox.showExclamation("���͂��ꂽ�p�X���[�h�ɋ󔒕������܂܂�Ă��܂��B"
                            + ACConstants.LINE_SEPARATOR
                            + "�󔒕������܂񂾃p�X���[�h�͎g�p�ł��܂���B");
                    // �����I��
                    return;
                }
            }
            
            
            // 2007/11/26 [Masahiko Higuchi] add - end
            //2006/02/12[Tozo Tanaka] : replace begin
//          ACFrame.getInstance().getProperityXML().write();
          if(!ACFrame.getInstance().getPropertyXML().writeWithCheck()){
              return;
          }
          //2006/02/12[Tozo Tanaka] : replace end

            IkenshoReceiptSoftDBManager dbm;
            try {
                dbm = new IkenshoReceiptSoftDBManager(ip, Integer
                        .parseInt(port), user, pass, dbsVer);
            } catch (Exception ex) {
                ACMessageBox
                        .showExclamation("�ڑ���̐ݒ肪�s���ł��B�ڑ���z�X�g��|�[�g�ԍ����������Ă��������B");
                return;
            }

            // tbl_ptinf�擾�X�g�A�h�v���V�[�W��
            int count = 0;
            try {
                
                // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
                if(getVersionCombo().getSelectedIndex() != DEFAULT_RECEIPT_VERSION_INDEX){
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
    //                        ACMessageBox.showExclamation("����ID�������͂ł��B");
                            ACMessageBox.showExclamation("���҃J�i�����������͂ł��B");
                            return;
                        }
    //                    if(!VRCharType.ONLY_NUMBER.isMatch(id)){
    //                        ACMessageBox
    //                                .showExclamation("����W�����Z�v�g�\�t�g�ɂĕW���\���ȊO�̊��Ҕԍ��\�����g�p���Ă���ꍇ�A"
    //                                        + ACConstants.LINE_SEPARATOR
    //                                        + "����ID���w�肵�������ɂ͑Ή����Ă���܂���B"
    //                                        + ACConstants.LINE_SEPARATOR
    //                                        + "�i�ڂ����͓���W�����Z�v�g�\�t�g�̃}�j���A�����Q�Ƃ��Ă��������j");
    //                        return;
    //                    }
    
                        param = new VRHashMap();
                        param.put("tbl_ptinf.HOSPID", hospID);
    //                    param.put("tbl_ptinf.PTID", id);
    //                    key = "key";
                        param.put("tbl_ptinf.KANANAME", id+"%");
                        key = "key3";
                    }else{
                        if (ACMessageBox.showOkCancel("���ׂĂ̊��҂��������܂��B��낵���ł����H"
                                + ACConstants.LINE_SEPARATOR
                                + "��100��������5�b���x������܂��B",
                                ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                            return;
                        }
                    }
                    
                    //�X�v���b�V���̏���
                    ACFrameEventProcesser processer = ACFrame.getInstance().getFrameEventProcesser();
                    if(processer instanceof IkenshoFrameEventProcesser){
                        splash = new ACStopButtonSplash();
                        ((ACSplash)splash).setIconPathes(((IkenshoFrameEventProcesser)processer).getSplashFilePathes());
                        Dimension d=((IkenshoFrameEventProcesser)processer).getSplashWindowSize();
                        if(d!=null){
                            d= new Dimension((int)d.getWidth(), (int)d.getHeight()+20);
                        }
                        
                        ((ACSplash)splash).refreshSize(d);
                        if (!((ACSplash)splash).isVisible()) {
                            ((ACSplash)splash).showModaless("�f�[�^�ʐM");
                        }
                        
                    }
                    
                    
                    count = dbm.executeQuery("tbl_ptinf", key, param, splash);
                    // result = dbm.executeQueryProcedure("all");
                    
                // 2007/11/26 [Masahiko Higuchi] edit - begin �����Z�A�g�Ή� v3.0.4
                }else{
                    // �����Z�V�o�[�W�����̏ꍇ
                    if (ACMessageBox.showOkCancel("���ׂĂ̊��҂��������܂��B��낵���ł����H"
                            + ACConstants.LINE_SEPARATOR
                            + "��100��������5�b���x������܂��B",
                            ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return;
                    }
                    //�X�v���b�V���̏���
                    ACFrameEventProcesser processer = ACFrame.getInstance().getFrameEventProcesser();
                    if(processer instanceof IkenshoFrameEventProcesser){
                        splash = new ACStopButtonSplash();
                        ((ACSplash)splash).setIconPathes(((IkenshoFrameEventProcesser)processer).getSplashFilePathes());
                        Dimension d=((IkenshoFrameEventProcesser)processer).getSplashWindowSize();
                        if(d!=null){
                            d= new Dimension((int)d.getWidth(), (int)d.getHeight()+20);
                        }
                        
                        ((ACSplash)splash).refreshSize(d);
                        if (!((ACSplash)splash).isVisible()) {
                            ((ACSplash)splash).showModaless("�f�[�^�ʐM");
                        }
                        
                    }
                    // HOSPNUM�擾�p�̊֐�����`
                    String key = "key";
                    VRMap initialParam = new VRHashMap();
                    HashMap hospNumResult = new HashMap();
                    initialParam.put("tbl_sysuser.USERID",user);
                    
                    // �ʐM����
                    dbm.executeSetUp();
                    
                    // ��UHOSPNUM���擾����B
                    hospNumResult = dbm.executeQueryData("tbl_sysuser",key,initialParam);
                    
                    // COMMIT
                    dbm.commitTransaction();
                    
                    // ���ҏ��擾�p�̊֐�����`                    
                    key = "all";
                    // �ϐ�����
                    VRMap findParam = new VRHashMap();
                    // HOSPNUM�̎擾
                    Integer hospNum = ACCastUtilities.toInteger(hospNumResult.get("tbl_sysuser.HOSPNUM"),0);
                    // �����L�[�ݒ�
                    findParam.put("tbl_ptinf.HOSPNUM",hospNum);
                    // ���ҏ��擾
                    count = dbm.executeQuery("tbl_ptinf", key, findParam, splash);
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
                    String id =patientID.getText(); 
                    if((id.length()>9)&&(Long.parseLong(id)>2147483647)){
                        ACMessageBox
                                .showExclamation("2147483647�𒴂��銳��ID�̎�M�Ɏ��s���܂����B"
                                        + ACConstants.LINE_SEPARATOR
                                        + "�ڑ���DBS�̃T�|�[�g�Z���^�[�ɂ��A�����������B");
                        return;
                    }
                    
                // 2007/11/26 [Masahiko Higuchi] add - begin �����Z�A�g�Ή� v3.0.4
                }else if("Connection reset".equals(ex.getMessage())){
                    // �����Z�̃o�[�W�����I���Ƀ~�X�̉\���L��
                    ACMessageBox
                    .showExclamation("�ڑ��Ɏ��s���܂����B"
                            + ACConstants.LINE_SEPARATOR
                            + "�ʐM�̐ؒf�A�������͓���W�����Z�v�g�\�t�g�̃o�[�W�������قȂ�\��������܂��B"
                            + ACConstants.LINE_SEPARATOR
                            + "�ڑ��ݒ���m�F���Ă��������B");
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

            //�ŏ���PAGE_COUNT�����\��
            viewFirstPage();
            splash = closeSplash(splash);

            ACMessageBox.show("���ҏ����擾���܂����B");

            patinetImport.setEnabled(true);
        } finally {
            splash = closeSplash(splash);
        }
    }
    
    public boolean canClose() throws Exception {
        try{
            //���ԃe�[�u����j��
            IkenshoReceiptSoftDBManager.clearAccessSpace(new IkenshoFirebirdDBManager());
            }catch(Exception ex){}
        return super.canClose();
    }
    // 2006/02/09[Tozo Tanaka] : add end
    /**
     * �X�v���b�V���������܂��B
     * @param splash �����Ώۂ̃X�v���b�V��
     * @return �đ�����ׂ��X�v���b�V��
     */
    protected ACSplashable closeSplash(ACSplashable splash) {
        if (splash != null) {
            splash.close();
        }
        return null;
    }
    //2006/02/09[Tozo Tanaka] : delete begin 

//    protected void findActionPerformed(ActionEvent e) throws Exception {
//        ACFrameEventProcesser processer = ACFrame.getInstance()
//                .getFrameEventProcesser();
//        ACSplashable splash = null;
//        if (processer != null) {
//            splash = processer.createSplash(new ACAffairInfo("", null, "�f�[�^�ʐM",
//                    true));
//        }
//
//        try {
//            IkenshoReceiptSoftDBManager dbm = new IkenshoReceiptSoftDBManager(
//                    hostName.getText(), Integer.parseInt(port.getText()),
//                    userName.getText(), password.getText(), dbsVersion
//                            .getText());
//
//            // tbl_ptinf�擾�X�g�A�h�v���V�[�W��
//            VRArrayList result;
//            try {
//                VRMap map = new VRHashMap();
//                result = dbm.executeQuery("tbl_ptinf", "all", null, splash);
//                // result = dbm.executeQueryProcedure("all");
//            } catch (Exception ex) {
//                splash = closeSplash(splash);
//                Throwable cause = ex.getCause();
//                if (cause instanceof java.net.ConnectException) {
//                    ACMessageBox.show("�ڑ��Ɏ��s���܂����B");
//                    return;
//                } else if (ex.getMessage().indexOf("invalid version") >= 0) {
//                    ACMessageBox.show("DBS�̃o�[�W�������قȂ�܂��B");
//                    return;
//                } else if (ex.getMessage().indexOf("authentication error") >= 0) {
//                    ACMessageBox.show("�F�؂Ɏ��s���܂����B���[�U�[������уp�X���[�h���s���ł��B");
//                    return;
//                }
//                throw ex;
//            }
//
//            
//            if (result.getDataSize() <= 0) {
//                splash = closeSplash(splash);
//                ACMessageBox.show("���ҏ�񂪑��݂��܂���B");
//                return;
//            }
//
//            // �����Z�`������ӌ����`���֕ϊ�
//            toIkenshoData(result);
//
//            // ���������`�F�b�N
//            checkSameName(result);
//
//            patients.clearSelection();
//            patientsData = result;
//            patientsModelAdapter.setAdaptee(result);
//            setStatusText(result.size() + "���擾���܂����B");
//
//            splash = closeSplash(splash);
//            ACMessageBox.show("���ҏ����擾���܂����B");
//
//            patinetImport.setEnabled(true);
//        } finally {
//            splash = closeSplash(splash);
//        }
//    }
//    /**
//     * ���Z�v�g�\�t�g������󂵂��f�[�^���ӌ����`���ɖ|�󂵂܂��B
//     * 
//     * @param src �����
//     * @throws ParseException ������O
//     */
//    protected void toIkenshoData(VRArrayList src) throws ParseException {
//        int size = src.size();
//        if (size <= 0) {
//            return;
//        }
//
//        VRMap checkRow = (VRMap) src.getData(0);
//        if (VRBindPathParser.has("tbl_ptinf.NAME", checkRow)) {
//            // ����
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                String val = getNotNullString("tbl_ptinf.NAME", row);
//                if (val.length() > 15) {
//                    val = val.substring(0, 15);
//                }
//                //edit sta s-fujihara 2006.2.2
//                //VRBindPathParser.set("PATIENT_NM", row, val);
//                VRBindPathParser.set("PATIENT_NM", row, getORCADecodeString(val));
//                //edit end s-fujihara 2..6.2.2
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("PATIENT_NM", row, "");
//            }
//        }
//        if (VRBindPathParser.has("tbl_ptinf.KANANAME", checkRow)) {
//            // ���Ȏ���
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                String val = getNotNullString("tbl_ptinf.KANANAME", row);
//                if (val.length() > 30) {
//                    val = val.substring(0, 30);
//                }
//                //edit sta s-fujihara 2006.2.2
//                //VRBindPathParser.set("PATIENT_KN", row, val);
//                VRBindPathParser.set("PATIENT_KN", row, getORCADecodeString(val));
//                //edit end s-fujihara 2..6.2.2
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("PATIENT_KN", row, "");
//            }
//        }
//        if (VRBindPathParser.has("tbl_ptinf.SEX", checkRow)) {
//            // ����
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("SEX", row, Integer
//                        .valueOf(getNotNullString("tbl_ptinf.SEX", row)));
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("SEX", row, new Integer(0));
//            }
//        }
//        if (VRBindPathParser.has("tbl_ptinf.BIRTHDAY", checkRow)) {
//            // �N��
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                Object obj = VRBindPathParser.get("tbl_ptinf.BIRTHDAY", row);
//                Calendar target;
//                if (obj instanceof Date) {
//                    target = Calendar.getInstance();
//                    target.setTime((Date) obj);
//                } else {
//                    try {
//                        target = Calendar.getInstance();
//                        target.setTime(VRDateParser.parse(String.valueOf(obj)));
//                    } catch (Exception ex) {
//                        VRBindPathParser.set("AGE", row, new Integer(0));
//                        VRBindPathParser.set("BIRTHDAY", row, null);
//                        continue;
//                    }
//                }
//
//                Calendar now = Calendar.getInstance();
//                now.setLenient(true);
//                now.add(Calendar.YEAR, 10);
//                now.add(Calendar.DAY_OF_YEAR,
//                        -target.get(Calendar.DAY_OF_YEAR) + 1);
//                now.add(Calendar.YEAR, -target.get(Calendar.YEAR));
//
//                VRBindPathParser.set("BIRTHDAY", row, target.getTime());
//                VRBindPathParser.set("AGE", row, new Integer(now
//                        .get(Calendar.YEAR) - 10));
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("AGE", row, "");
//            }
//        }
//
//        if (VRBindPathParser.has("tbl_ptinf.HOME_POST", checkRow)) {
//            // �X�֔ԍ�
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                String val = getNotNullString("tbl_ptinf.HOME_POST", row);
//                if (val.length() < 7) {
//                    if (val.length() >= 3) {
//                        val = val.substring(0, 3) + "-" + val.substring(3);
//                    }
//                } else {
//                    val = val.substring(0, 3) + "-" + val.substring(3, 7);
//                }
//                VRBindPathParser.set("POST_CD", row, val);
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("POST_CD", row, "");
//            }
//        }
//
//        if (VRBindPathParser.has("tbl_ptinf.HOME_ADRS", checkRow)) {
//            // �Z��
//            if (VRBindPathParser.has("tbl_ptinf.HOME_BANTI", checkRow)) {
//                for (int i = 0; i < size; i++) {
//                    VRMap row = (VRMap) src.getData(i);
//                    String val = getNotNullString("tbl_ptinf.HOME_ADRS", row)
//                            + getNotNullString("tbl_ptinf.HOME_BANTI", row);
//                    if (val.length() > 50) {
//                        val = val.substring(0, 50);
//                    }
//                    //edit sta s-fujihara 2006.2.2
//                    //VRBindPathParser.set("ADDRESS", row, val);
//                    VRBindPathParser.set("ADDRESS", row, getORCADecodeString(val));
//                    //edit end s-fujihara 2006.2.2
//                }
//            } else {
//                for (int i = 0; i < size; i++) {
//                    VRMap row = (VRMap) src.getData(i);
//                    String val = getNotNullString("tbl_ptinf.HOME_ADRS", row);
//                    if (val.length() > 50) {
//                        val = val.substring(0, 50);
//                    }
//                    //edit sta s-fujihara 2006.2.2
//                    //VRBindPathParser.set("ADDRESS", row, val);
//                    VRBindPathParser.set("ADDRESS", row, getORCADecodeString(val));
//                    //edit end s-fujihara 2006.2.2
//                }
//            }
//        } else if (VRBindPathParser.has("tbl_ptinf.HOME_BANTI", checkRow)) {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                String val = getNotNullString("tbl_ptinf.HOME_BANTI", row);
//                if (val.length() > 50) {
//                    val = val.substring(0, 50);
//                }
//                //edit sta s-fujihara 2006.2.2
//                //VRBindPathParser.set("ADDRESS", row, val);
//                VRBindPathParser.set("ADDRESS", row, getORCADecodeString(val));
//                //edit end s-fujihara 2006.2.2
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("ADDRESS", row, "");
//            }
//        }
//
//        if (VRBindPathParser.has("tbl_ptinf.HOME_TEL1", checkRow)) {
//            // �d�b�ԍ�
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                String val = getNotNullString("tbl_ptinf.HOME_TEL1", row);
//                val = toNotTelCharReplace(val);
//                String[] tels = val.split("-");
//                if (tels.length == 0) {
//                    VRBindPathParser.set("TEL1", row, "");
//                    VRBindPathParser.set("TEL2", row, "");
//                    VRBindPathParser.set("TEL", row, "");
//                    continue;
//                }
//                if (tels[0].length() > 5) {
//                    String[] telSrc;
//                    if (tels.length < 2) {
//                        telSrc = new String[2];
//                    } else {
//                        telSrc = tels;
//                    }
//                    telSrc[0] = tels[0].substring(0, 5);
//                    if (tels[0].length() > 9) {
//                        telSrc[1] = tels[0].substring(5, 9) + "-"
//                                + tels[0].substring(9, tels[0].length());
//                    } else {
//                        telSrc[1] = tels[0].substring(5, tels[0].length())
//                                + "-";
//                    }
//                    tels = telSrc;
//                }
//
//                StringBuffer telSB = new StringBuffer();
//                if (!"".equals(tels[0])) {
//                    telSB.append(tels[0]);
//                    telSB.append("-");
//                }
//
//                VRBindPathParser.set("TEL1", row, tels[0]);
//                switch (tels.length) {
//                case 0:
//                case 1:
//                    VRBindPathParser.set("TEL2", row, "");
//                    break;
//                case 2:
//                    VRBindPathParser.set("TEL2", row, tels[1]);
//                    if (!"".equals(tels[1])) {
//                        if (tels[1].length() > 5) {
//                            telSB.append(tels[1].substring(0, 5));
//                            telSB.append("-");
//
//                            if (tels[1].length() > 9) {
//                                telSB.append(tels[1].substring(5, 9));
//                            } else {
//                                telSB.append(tels[1].substring(5));
//                            }
//                        } else {
//                            telSB.append(tels[1]);
//                        }
//                    }
//                    break;
//                case 3:
//                    VRBindPathParser.set("TEL2", row, tels[1] + "-" + tels[2]);
//                    if (!"".equals(tels[1])) {
//                        telSB.append(tels[1]);
//                    }
//                    telSB.append("-");
//                    if (!"".equals(tels[2])) {
//                        telSB.append(tels[2]);
//                    }
//                    break;
//                default:
//                    VRBindPathParser.set("TEL2", row, tels[1] + "-" + tels[2]);
//                    if (!"".equals(tels[1])) {
//                        telSB.append(tels[1]);
//                    }
//                    telSB.append("-");
//                    if (!"".equals(tels[2])) {
//                        telSB.append(tels[2]);
//                    }
//                    break;
//                }
//
//                VRBindPathParser.set("TEL", row, telSB.toString());
//
//            }
//        } else {
//            for (int i = 0; i < size; i++) {
//                VRMap row = (VRMap) src.getData(i);
//                VRBindPathParser.set("TEL1", row, "");
//                VRBindPathParser.set("TEL2", row, "");
//                VRBindPathParser.set("TEL", row, "");
//            }
//        }
//        for (int i = 0; i < size; i++) {
//            VRMap row = (VRMap) src.getData(i);
//            VRBindPathParser.set("IMPORT_FLAG", row, new Boolean(true));
//            VRBindPathParser.set("BATTING_FLAG", row, "");
//        }
//    }
//    //add sta s-fujihara 2006.2.2
//    private String getORCADecodeString(String data){
//        String result = data;
//        
//        String osName = System.getProperty("os.name");
//        //Mac�ȊO�ł���Ε����R�[�h�ϊ����s���B
//        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
//            try{
//                result = new String(data.getBytes("Shift_JIS"),"MS932");
//            } catch(Exception e){
//                VRLogger.warning("�����R�[�h�̕ϊ��Ɏ��s���܂����B");
//            }
//        }
//        return result;
//    }
//    //add end s-fujihara 2006.2.2
    //2006/02/09[Tozo Tanaka] : delete end 

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
            if(patientsData!=null){
                checkSameName(patientsData);
                patients.invalidate();
                patients.repaint();
            }

            ACMessageBox.show("���ҏ�����荞�݂܂����B");
            //2006/02/09[Tozo Tanaka] : delete begin 
//            ACFrame.getInstance().back();
//            return;
            //2006/02/09[Tozo Tanaka] : delete end 
        }
    }

    /**
     * �ǉ��������s���܂��B
     * 
     * @throws Exception ������O
     * @return boolean �ǉ������ɐ���������
     */
    private boolean doInsert() throws Exception {

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
                // 2006/02/09[Tozo Tanaka] : add begin
//                sb.append(",CHART_NO");
                // 2006/02/09[Tozo Tanaka] : add end
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
                sb.append(" )");
                sb.append(" VALUES");
                sb.append(" (");
                sb.append(getDBSafeString("PATIENT_NM", row));
                sb.append(",");
                // 2006/02/09[Tozo Tanaka] : replace begin
//                sb.append(getDBSafeString("PATIENT_KN", row));
//                sb.append(getDBSafeString("CHART_NO", row));
//                sb.append(",");
                String kana= getDBSafeString("PATIENT_KN", row);
                if(useKanaConvert.isSelected()){
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
                sb.append(")");

                dbm.executeUpdate(sb.toString());

            }

            dbm.commitTransaction();
            dbm.finalize();

        } catch (Exception ex) {
            if (dbm != null) {
                dbm.rollbackTransaction();
                dbm.finalize();
            }
            throw ex;
        }

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
        //  2006/02/11[Tozo Tanaka] : replace begin
//        buttons.setText("����W�����Z�v�g�\�t�g�A�W");
        buttons.setText("����W�����Z�v�g�\�t�g�A�g");
        //  2006/02/11[Tozo Tanaka] : replace end
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
        accessSettings.add(getVersionContainer(),VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/11/26 [Masahiko Higuchi] add - end
        passwords.add(password, null);
        hostNames.add(hostName, null);
        ports.add(port, null);
        userNames.add(userName, null);
        this.add(buttons, VRLayout.NORTH);
        buttons.add(patinetImport, VRLayout.EAST);
        buttons.add(receiptAccess, VRLayout.EAST);
        this.add(accessSettings, VRLayout.NORTH);
        this.add(patients, VRLayout.CLIENT);

        
        //  2006/02/09[Tozo Tanaka] : add begin
        ((VRLayout)accessSettings.getLayout()).setAutoWrap(false);
        this.add(filterSettings, VRLayout.NORTH);
//        usePatientIDFilter.setText("ID�ōi�荞��");
        usePatientIDFilter.setText("�J�i�����ōi�荞��");
        patientIDFilters.setLabelFilled(true);
        patientIDFilters.setBackground(patientIDFilters.getFocusBackground());
        patientIDFilters.add(usePatientIDFilter, VRLayout.FLOW);
        patientIDFilters.add(patientIDContainer, VRLayout.FLOW);
        patientIDFilters.add(hospitalIDContainer, VRLayout.FLOW);
        //2006/02/11[Tozo Tanaka] : delete begin
        //�w�茟����s�\�ɂ���
//        accessSettings.add(patientIDFilters, VRLayout.FLOW_DOUBLEINSETLINE);
        //2006/02/11[Tozo Tanaka] : delete end 
        filterSettings.setHgap(0);
        filterSettings.add(checkEditMethodContainer, VRLayout.FLOW);
        filterSettings.add(useKanaConvert, VRLayout.FLOW);
        filterSettings.add(previewPage, VRLayout.FLOW);
        filterSettings.add(nextPage, VRLayout.FLOW);
        filterSettings.add(foundCountValue, VRLayout.FLOW);
        filterSettings.add(foundCountUnit, VRLayout.FLOW);
        filterSettings.add(viewIndexBegin, VRLayout.FLOW);
        filterSettings.add(viewIndexBeginUnit, VRLayout.FLOW);
        filterSettings.add(viewIndexEnd, VRLayout.FLOW);
        filterSettings.add(viewIndexEndUnit, VRLayout.FLOW);
        checkEditMethodContainer.setText("�`�F�b�N");
        checkEditMethod.setEditable(false);
        checkEditMethod.setModel(new String[]{"���ׂđI��","���ׂď��O","�I�𔽓]","�d�������O"});
        checkEditMethod.setSelectedIndex(0);
        checkEditMethod.setColumns(6);
        checkEdit.setText("���s");
        checkEditMethodContainer.add(checkEditMethod, VRLayout.FLOW);
        checkEditMethodContainer.add(checkEdit, VRLayout.FLOW);
        nextPage.setText("����");
        nextPage.setToolTipText("����" + PAGE_COUNT + "����\�����܂��B");
        nextPage.setEnabled(false);
        previewPage.setText("�O��");
        previewPage.setToolTipText("�O��"+PAGE_COUNT+"����\�����܂��B");
        previewPage.setEnabled(false);
        patientIDContainer.add(patientID, VRLayout.FLOW);
        patientIDContainer.setText("�J�i����");  
//        patientIDContainer.setText("����ID");  
        patientIDContainer.setEnabled(false);
        patientID.setColumns(10);
        patientID.setMaxLength(10);
        //2006/02/11[Tozo Tanaka] : replace begin 
//        patientID.setCharType(VRCharType.ONLY_ALNUM);
        patientID.setIMEMode(InputSubset.KANJI);
        //2006/02/11[Tozo Tanaka] : replace end 
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
        usePatientIDFilter.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                boolean ena = usePatientIDFilter.isSelected();
                patientID.setEnabled(ena);
                patientIDContainer.setEnabled(ena);
                hospitalID.setEnabled(ena);
                hospitalIDContainer.setEnabled(ena);
            }
        });
//        patientIDFilterClear.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                try{
//                    viewFirstPage();
//                }catch(Exception ex){
//                    IkenshoCommon.showExceptionMessage(ex);
//                }
//            }
//        });
//        patientIDFiltering.addActionListener(new ActionListener(){
//            public void actionPerformed(ActionEvent e) {
//                String id=patientID.getText(); 
//                try{
//                if("".equals(id)){
//                    viewFirstPage();
//                }else{
//                    readFromAccessSpace(Integer.parseInt(id));
//                }
//                }catch(Exception ex){
//                    IkenshoCommon.showExceptionMessage(ex);
//                }
//            }
//        });
        previewPage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try{
                    pageBegin-=PAGE_COUNT;
                    readFromAccessSpace(pageBegin, PAGE_COUNT);
                }catch(Exception ex){
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });
        nextPage.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                try{
                    pageBegin+=PAGE_COUNT;
                    readFromAccessSpace(pageBegin, PAGE_COUNT);
                }catch(Exception ex){
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });
        checkEdit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                if(patientsData==null){
                    return;
                }
                // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
                patients.stopCellEditing("IMPORT_FLAG");
                
                Iterator it = patientsData.iterator();
                switch(checkEditMethod.getSelectedIndex()){
                case 0://���ׂđI��
                    while(it.hasNext()){
                        ((Map)it.next()).put("IMPORT_FLAG", Boolean.TRUE);
                    }
                    break;
                case 1://���ׂď��O
                    while(it.hasNext()){
                        ((Map)it.next()).put("IMPORT_FLAG", Boolean.FALSE);
                    }
                    break;
                case 2://�I�𔽓]
                    while(it.hasNext()){
                        Map row =(Map)it.next(); 
                        Boolean val; 
                        if(Boolean.TRUE.equals(row.get("IMPORT_FLAG"))){
                            val = Boolean.FALSE; 
                        }else{
                            val = Boolean.TRUE;
                        }
                        row.put("IMPORT_FLAG", val);
                    }
                    break;
                case 3://�d�����O
                    while(it.hasNext()){
                        Map row =(Map)it.next(); 
                        if("����".equals(row.get("BATTING_FLAG"))){
                            row.put("IMPORT_FLAG", Boolean.FALSE);
                        }
                    }
                    break;
                }
                patients.invalidate();
                patients.repaint();
            }
        });

        //  2006/02/09[Tozo Tanaka] : add end
        
    }

    public boolean canBack(VRMap parameters) {
        //  2006/02/09[Tozo Tanaka] : add begin
        try{
            //���ԃe�[�u����j��
        IkenshoReceiptSoftDBManager.clearAccessSpace(new IkenshoFirebirdDBManager());
        }catch(Exception ex){}
        //  2006/02/09[Tozo Tanaka] : add end
        return true;
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return hostName;
    }

    /**
     * �����Z�o�[�W�������R���{���擾���܂��B
     * @return �R���{�{�b�N�X
     * @since v3.0.4
     * @author Masahiko Higuchi
     */
    public ACComboBox getVersionCombo() {
        if(versionCombo == null){
            versionCombo = new ACComboBox();
            versionCombo.setEditable(false);
            versionCombo.setModel(new String[]{"version 4.0.0����","version 4.0.0�ȏ�"});
            versionCombo.setBlankable(false);
        }
        return versionCombo;
    }
    /**
     * ����W�����Z�v�g�\�t�g��񃉃x���R���e�i���擾���܂��B
     * @return ���x���R���e�i
     * @since v3.0.4
     * @author Masahiko Higuchi
     */
    public ACLabelContainer getVersionContainer() {
        if(versionContainer == null){
            versionContainer = new ACLabelContainer();
            versionContainer.add(getVersionCombo());
            versionContainer.setText("����W�����Z�v�g�\�t�g�o�[�W����");
        }
        return versionContainer;
    }

}