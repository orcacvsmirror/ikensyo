package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.io.ByteArrayInputStream;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventObject;

import javax.swing.JComponent;
import javax.swing.JTextField;

import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.event.IkenshoDocumentAffairApplicantNameChageListener;
import jp.or.med.orca.ikensho.event.IkenshoWriteDateChangeListener;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfo extends IkenshoTabbableAffairContainer {

    private VRArrayList graphicsCommandArray;
    protected Integer billFDOutputKubun = new Integer(
            IkenshoIkenshoInfoPrintParameter.CSV_OUTPUT_TYPE_NONE);
    protected Integer billHakkouKubun = new Integer(
            IkenshoIkenshoInfoPrintParameter.BILL_HAKKOU_TYPE_NONE);
    protected Date billFDOutputTime = null;

    private static final ACPassiveKey PASSIVE_CHECK_KEY_BILL = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO", "EDA_NO" }, new Format[] {
                    null, null }, "BILL_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_KEY_IKENSYO = new ACPassiveKey(
            "IKN_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO" },
            new Format[] { null, null }, "IKENSYO_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_KEY_COMMAND = new ACPassiveKey(
            "GRAPHICS_COMMAND", new String[] { "PATIENT_NO", "EDA_NO" },
            new Format[] { null, null }, "LAST_TIME", "LAST_TIME");

    protected IkenshoIkenshoInfoApplicant applicant;
    protected IkenshoIkenshoInfoSick sick;
    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin ��ʒ����ɔ�������
    protected IkenshoIkenshoInfoSick sick2;
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    protected IkenshoIkenshoInfoSpecial special;
    protected IkenshoIkenshoInfoCare1 care1;
    protected IkenshoIkenshoInfoCare2 care2;
    protected IkenshoIkenshoInfoMindBody1 mindBody1;
    protected IkenshoIkenshoInfoMindBody2 mindBody2;
    protected IkenshoIkenshoInfoMention mention;
    protected IkenshoIkenshoInfoOrgan organ;
    protected IkenshoIkenshoInfoBill bill;

    
    protected void reselectForPassiveCustom(IkenshoFirebirdDBManager dbm,
            VRMap data) throws Exception {
        StringBuffer sb;
        VRArrayList array;

        // �ӌ����ŗL
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" LAST_TIME AS IKENSYO_LAST_TIME");
        sb.append(",CREATE_DT");
        sb.append(" FROM");
        sb.append(" IKN_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (IKN_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (IKN_ORIGIN.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        array = (VRArrayList) dbm.executeQuery(sb.toString());
        hasOriginalDocument = array.getDataSize() > 0;
        if (hasOriginalDocument) {
            data.putAll((VRMap) array.getData());
        }

        // ������
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" LAST_TIME AS BILL_LAST_TIME");
        sb.append(" FROM");
        sb.append(" IKN_BILL");
        sb.append(" WHERE");
        sb.append(" (IKN_BILL.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(IKN_BILL.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        array = (VRArrayList) dbm.executeQuery(sb.toString());
        if (array.getDataSize() > 0) {
            data.putAll((VRMap) array.getData());
        }

        // �l�̐}�R�}���h
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" GRAPHICS_COMMAND");
        sb.append(" WHERE");
        sb.append(" (GRAPHICS_COMMAND.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (GRAPHICS_COMMAND.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        sb.append(" ORDER BY");
        sb.append(" CMD_NO ASC");
        graphicsCommandArray = (VRArrayList) dbm.executeQuery(sb.toString());

    }
    
    protected void doSelectCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" IKN_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (IKN_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (IKN_ORIGIN.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        
        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        hasOriginalDocument = array.getDataSize() > 0;
        if (hasOriginalDocument) {
            VRMap data = (VRMap) array.getData();
            data.setData("IKENSYO_LAST_TIME", VRBindPathParser.get("LAST_TIME",
                    data));

            originalData.putAll(data);
        }
        // �l�̐}�R�}���h
        doSelectGraphicsCommand(dbm);

        // ������
        doSelectBill(dbm);
        
        
        // 2006/02/11[Tozo Tanaka] : replace begin
        Object insurerNo = originalData.getData("INSURER_NO");
        if ((insurerNo != null) && (!"".equals(insurerNo))) {
            if ("0".equals(String.valueOf(originalData
                    .getData("OUTPUT_PATTERN")))) {
                // �ی��ґI���ς݂��������o�̓p�^�[����0���ȍ~����Ő����f�[�^���s��
                if (new Double(0).equals(originalData.getData("TAX"))) {
                    originalData.setData("TAX", new Double(-1));
                }
            }
        }
        // 2006/02/11[Tozo Tanaka] : replace end
        
    }

    /**
     * �摜�R�}���h�̌����������s���܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    protected void doSelectGraphicsCommand(IkenshoFirebirdDBManager dbm)
            throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" GRAPHICS_COMMAND");
        sb.append(" WHERE");
        sb.append(" (GRAPHICS_COMMAND.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (GRAPHICS_COMMAND.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        sb.append(" ORDER BY");
        sb.append(" CMD_NO ASC");
        graphicsCommandArray = (VRArrayList) dbm.executeQuery(sb.toString());
        originalData.put("GRAPHICS_COMMAND", graphicsCommandArray);
    }

    /**
     * �������̌����������s���܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    protected void doSelectBill(IkenshoFirebirdDBManager dbm) throws Exception {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" IKN_BILL");
        sb.append(" WHERE");
        sb.append(" (IKN_BILL.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(IKN_BILL.EDA_NO=");
        sb.append(getEdaNo());
        sb.append(")");
        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        if (array.getDataSize() > 0) {
            VRMap data = (VRMap) array.getData();
            data.setData("BILL_LAST_TIME", VRBindPathParser.get("LAST_TIME",
                    data));

            // �}�X�^�ύX�ɔ����ē]�L
            data.setData("FURIKOMI_MEIGI", data.getData("KOUZA_MEIGI"));
            data.setData("BANK_KOUZA_NO", data.getData("KOUZA_NO"));
            data.setData("BANK_KOUZA_KIND", data.getData("KOUZA_KIND"));
            data.setData("MI_KBN", data.getData("SHOSIN"));
            data.setData("SEIKYUSHO_OUTPUT_PATTERN", data
                    .getData("OUTPUT_PATTERN"));

            originalData.putAll(data);

            billFDOutputKubun = (Integer) VRBindPathParser.get("FD_OUTPUT_KBN",
                    data);
            billHakkouKubun = (Integer) VRBindPathParser
                    .get("HAKKOU_KBN", data);
            billFDOutputTime = (Date) VRBindPathParser
                    .get("FD_TIMESTAMP", data);

        }
    }
    
    /**
     * �ӌ����쐬�񐔂�Ԃ��܂��B
     * @param dbm
     * @return int �쐬��
     * @throws Exception
     */
    protected int getCreateCount(IkenshoFirebirdDBManager dbm) throws Exception{
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" MAX(");
        sb.append(getCustomDocumentTableName());
        sb.append(".EDA_NO) AS EDA_NO");
        sb.append(" FROM ");
        sb.append(getCustomDocumentTableName());
        sb.append(" WHERE");
        sb.append(" (");
        sb.append(getCustomDocumentTableName());
        sb.append(".PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND ( ");
        sb.append(getCustomDocumentTableName());
        sb.append(".FORMAT_KBN =");
        sb.append(getFormatKubun());
        sb.append(")");
        
        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        if (array.getDataSize() == 0) {
            return 1;
        }
        Object obj = VRBindPathParser.get("EDA_NO", (VRMap) array.getData());
        if (obj instanceof Integer) {
          return 2;
        }
        else {
          return 1;
        }
    }
    

    protected void doSelectBeforeCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {

        int createCount = 1;
        String patientNo = getPatientNo();
        if (patientNo != null) {

            StringBuffer sb;
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");
            sb.append(" (PATIENT_NO = ");
            sb.append(patientNo);
            sb.append(")");
            sb.append(" ORDER BY");
            sb.append(" EDA_NO DESC");
            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.size() > 0) {
                VRMap data = (VRMap) array.getData();
                originalData.putAll(data);

                int nextNo = getNextEdaNo(dbm);
                // ����쐬or2��ڈȍ~
                if (nextNo > 1) {
                    createCount = 2;

                    String oldEdaNo = getEdaNo();
                    setEdaNo(String.valueOf(nextNo - 1));
                    // �l�̐}�R�}���h
                    doSelectGraphicsCommand(dbm);

                    // �������͈����p���Ȃ�

                    setEdaNo(oldEdaNo);

                    // �쐬�˗����A�˗��ԍ��A���t���͈����p���Ȃ�
                    VRBindPathParser.set("REQ_DT", originalData, null);
                    VRBindPathParser.set("REQ_NO", originalData, "");
                    VRBindPathParser.set("SEND_DT", originalData, null);

                    
                    // 2006/02/09[Tozo Tanaka] : add begin
                    //�O��̒��J�쎮
                    if(VRBindPathParser.has("HASE_SCORE", data)){
                        VRBindPathParser.set("P_HASE_SCORE", originalData, VRBindPathParser.get("HASE_SCORE", data));
                        VRBindPathParser.set("HASE_SCORE", originalData, "");
                    }
                    if(VRBindPathParser.has("HASE_SCR_DT", data)){
                        VRBindPathParser.set("P_HASE_SCR_DT", originalData, VRBindPathParser.get("HASE_SCR_DT", data));
                        VRBindPathParser.set("HASE_SCR_DT", originalData, "0000�N00��00��");
                    }
                    // 2006/02/09[Tozo Tanaka] : add end
                    
                }
                
                // 2006/08/07
                // �쐬�񐔂����͎�蒼���Ē��ׂ�
                // Addition - begin [Masahiko Higuchi] 
                createCount = getCreateCount(dbm);
                // Addition - end

                // �L�����͖{��
                VRBindPathParser.set("KINYU_DT", originalData, new Date());
            }
        }
        originalData.setData("IKN_CREATE_CNT", new Integer(createCount));

        originalData.setData("TAX", new Double(-1));
    }

    protected void doSelectDefaultCustomDocument(IkenshoFirebirdDBManager dbm) {
        originalData.setData("IKN_CREATE_CNT", new Integer(1));

        originalData.setData("TAX", new Double(-1));
    }

    protected void doReservedPassiveCustom() throws ParseException {
        reservedPassive(PASSIVE_CHECK_KEY_IKENSYO, originalArray);
        reservedPassive(PASSIVE_CHECK_KEY_BILL, originalArray);
        if (graphicsCommandArray == null) {
            graphicsCommandArray = new VRArrayList();
        }
        reservedPassive(PASSIVE_CHECK_KEY_COMMAND, graphicsCommandArray);
    }

    protected boolean canUpdateCustom() throws Exception {
        if (!getMention().isTestPointCheckWarning()) {
            return false;
        }

        switch (getBillFDOutputKubun().intValue()) {
        case 1: 
            //2006/02/12[Tozo Tanaka] : add begin
        case 2: //CSV�o�͍�
            //2006/02/12[Tozo Tanaka] : add end
        {
            if (ACMessageBox
                    .show(
                            "CSV�t�@�C���o�͑ΏۂɂȂ��Ă��܂��B\n�X�V�����CSV�̏o�͑ΏۂłȂ��Ȃ�܂��B\n�X�V���Ă���낵���ł����H\n�i�X�V����ꍇ�͍ēx������s���A�ŐV�̓��e��CSV�o�͑Ώۂɂ���K�v������܂��j",
                            ACMessageBox.BUTTON_OKCANCEL,
                            ACMessageBox.ICON_QUESTION,
                            ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {
                // setBillFDOutputKubun(new Integer(0));
                // billFDOutputTime = null;
                
                // 2006/08/07
                // �p�b�V�u�`�F�b�N�J�n
                // Addition - begin [Masahiko Higuchi]
                IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                clearPassiveTask();

                addPassiveUpdateTask(PASSIVE_CHECK_KEY_PATIENT, 0);
                addPassiveUpdateTask(PASSIVE_CHECK_KEY_COMMON, 0);
                addPassiveUpdateTaskCustom();
                dbm = getPassiveCheckedDBManager();
                // �p�b�V�u�G���[�������ۑ����Ȃ�
                if (dbm != null) {
                    setBillFDOutputKubun(new Integer(0));
                    billFDOutputTime = null;
                    doUpdateBillPrintKubun();
                }
                //�p�b�V�u�`�F�b�N�I��
                // Addition - end

            } else {
                return false;
            }
        }
        }
        return true;
    }

    /**
     * �ő�}�ԍ��擾�̂��߂�SQL�T�u�N�G����ǉ����܂��B
     * @param sb
     */
    protected void appendSelectMaxEdaNoSubQuery(StringBuffer sb){
        sb.append(" AND ( FORMAT_KBN = ");
        sb.append(getFormatKubun());
        sb.append(" )");
        
    }
    
    protected boolean showPrintDialogCustom() throws Exception {
        IkenshoIkenshoInfoPrintParameter param = new IkenshoIkenshoInfoPrintParameter();
        param.setNowMode(getNowMode());
        // 2006/06/22
        // �X�i�b�v�V���b�g
        // Addition - begin [Masahiko Higuchi]
        param.setNeverSaved(IkenshoSnapshot.getInstance().isModified()
                || IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())||simpleSnap.simpleIsModefield());
        // Addition - end
        param.setCsvOutputType(billFDOutputKubun.intValue());
        param.setCsvOutputTime(billFDOutputTime);
        param
                .setCsvSubmited(billFDOutputKubun.intValue() == IkenshoIkenshoInfoPrintParameter.CSV_OUTPUT_TYPE_OUTPUTED);
        //2006/02/12[Tozo Tanaka] : add begin
        param
        .setCsvTarget(billFDOutputKubun.intValue() == IkenshoIkenshoInfoPrintParameter.CSV_OUTPUT_TYPE_TARGET);
        //2006/02/12[Tozo Tanaka] : add end
        param.setHakkouType(billHakkouKubun.intValue());

        if (IkenshoCommon.isNullText(getEdaNo())) {
            // �o�^�O�̂��ߍŐV
            param.setNotMostNewDocument(false);
        } else {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" MAX(EDA_NO) AS EDA_NO");
            sb.append(" FROM ");
            sb.append(getCustomDocumentTableName());
            sb.append(" WHERE");
            sb.append(" (PATIENT_NO = ");
            sb.append(getPatientNo());
            sb.append(" )"); 
            // 2006/08/02 - ��t�ӌ���CSV�ΏۑΉ� TODO
            // Addition - begin [Masahiko Higuchi]
            appendSelectMaxEdaNoSubQuery(sb);
            // Addition - end
           
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.getDataSize() > 0) {
                param.setNotMostNewDocument(!getEdaNo().equals(
                        String.valueOf(VRBindPathParser.get("EDA_NO",
                                (VRMap) array.getData()))));
            } else {
                // �o�^�O�̂��ߍŐV
                param.setNotMostNewDocument(false);
            }
        }

        // if(getBillPrintPattern()!=null){
        // param.setBillPrintPattern(getBillPrintPattern().intValue());
        // }

        if (!showPrintSetting(param)) {
            return false;
        }

        billFDOutputKubun = new Integer(param.getCsvOutputType());
        billFDOutputTime = param.getCsvOutputTime();
        billHakkouKubun = new Integer(param.getHakkouType());

        if (param.isTypeChanged()) {
            // �X�V�\��
            if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
                doUpdateBillPrintKubun();
            }
        }

        return true;
    }

    /**
     * override���Ĉ����ʎ��s�������`���܂��B
     * 
     * @param param ����p�����[�^
     * @return ���������
     */
    protected boolean showPrintSetting(IkenshoIkenshoInfoPrintParameter param) {
        return new IkenshoIkenshoPrintSetting(originalData, mindBody2
                .getPicture()).showModal(param);
    }

    /**
     * �������̔��s�敪�����CSV�o�͑Ώۋ敪�݂̂��X�V���܂��B
     * 
     * @throws Exception ������O
     */
    public void doUpdateBillPrintKubun() throws Exception {
        // �X�V���[�h�̏ꍇ�A���Ҕԍ��Ǝ}�Ԃ��L�[�Ƀf�[�^���W�߂�
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

        // ������
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE");
        sb.append(" IKN_BILL");
        sb.append(" SET");
        sb.append(" FD_OUTPUT_KBN = ");
        sb.append(billFDOutputKubun.intValue());
        sb.append(",FD_TIMESTAMP = ");
        if (billFDOutputTime == null) {
            sb.append("NULL");
        } else {
            sb.append(IkenshoConstants.FORMAT_PASSIVE_YMD_HMS
                    .format(billFDOutputTime));
        }
        sb.append(",HAKKOU_KBN = ");
        sb.append(billHakkouKubun.intValue());
        // sb.append(",LAST_TIME = CURRENT_TIMESTAMP");
        sb.append(" WHERE");
        sb.append(" (IKN_BILL.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(IKN_BILL.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");

        dbm.executeUpdate(sb.toString());
        dbm.finalize();
    }

    protected String getCustomDocumentTableName() {
        return "IKN_ORIGIN";
    }

    protected String getAnotherDocumentTableName() {
        return "SIS_ORIGIN";
    }

    protected ArrayList getCustomSnapshotExclusions() {
        ArrayList set = new ArrayList();
        set.add(bill);
        return set;
    }

    protected void addPassiveInsertTaskCustom() {
        addPassiveInsertTask(PASSIVE_CHECK_KEY_IKENSYO, 0);
        addPassiveInsertTask(PASSIVE_CHECK_KEY_BILL, 0);
    }

    protected void addPassiveUpdateTaskCustom() {
        addPassiveUpdateTask(PASSIVE_CHECK_KEY_IKENSYO, 0);
        addPassiveUpdateTask(PASSIVE_CHECK_KEY_BILL, 0);
    }

    protected void doInsertCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        doInsertIkensho(dbm);
        doInsertBill(dbm);
        doReplaceGraphicsCommand(dbm);
    }

    protected void doUpdateCustomDocument(IkenshoFirebirdDBManager dbm)
            throws Exception {
        doUpdateIkensho(dbm);
        doUpdateBill(dbm);
        doReplaceGraphicsCommand(dbm);
    }

    protected String getCustomDocumentType() {
        return IkenshoConstants.DOC_KBN_IKENSHO;
    }

    protected String getAnotherDocumentType() {
        return IkenshoConstants.DOC_KBN_SIJISHO;
    }

    /**
     * �ӌ�������ǉ����܂��B
     * 
     * @param dbm DBManager
     * @throws ParseException ��͗�O
     * @throws SQLException SQL��O
     */
    protected void doInsertIkensho(IkenshoFirebirdDBManager dbm)
            throws ParseException, SQLException {
        // �ӌ���
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO");
        sb.append(" IKN_ORIGIN");
        sb.append(" (");
        sb.append(" PATIENT_NO");
        sb.append(",EDA_NO");
        sb.append(",FORMAT_KBN");
        sb.append(",KINYU_DT");
        sb.append(",IKN_CREATE_CNT");
        sb.append(",LASTDAY");
        sb.append(",TAKA");
        sb.append(",TAKA_OTHER");
        sb.append(",TANKI_KIOKU");
        sb.append(",NINCHI");
        sb.append(",DENTATU");
        sb.append(",SHOKUJI");
        sb.append(",GNS_GNC");
        sb.append(",MOUSOU");
        sb.append(",CHUYA");
        sb.append(",BOUGEN");
        sb.append(",BOUKOU");
        sb.append(",TEIKOU");
        sb.append(",HAIKAI");
        sb.append(",FUSIMATU");
        sb.append(",FUKETU");
        sb.append(",ISHOKU");
        sb.append(",SEITEKI_MONDAI");
        sb.append(",MONDAI_OTHER");
        sb.append(",MONDAI_OTHER_NM");
        sb.append(",SEISIN");
        sb.append(",SEISIN_NM");
        sb.append(",SENMONI");
        sb.append(",SENMONI_NM");
        sb.append(",KIKIUDE");
        sb.append(",WEIGHT");
        sb.append(",HEIGHT");
        sb.append(",SISIKESSON");
        sb.append(",SISIKESSON_BUI");
        sb.append(",SISIKESSON_TEIDO");
        sb.append(",MAHI");
        sb.append(",MAHI_BUI");
        sb.append(",MAHI_TEIDO");
        sb.append(",KINRYOKU_TEIKA");
        sb.append(",KINRYOKU_TEIKA_BUI");
        sb.append(",KINRYOKU_TEIKA_TEIDO");
        sb.append(",JOKUSOU");
        sb.append(",JOKUSOU_BUI");
        sb.append(",JOKUSOU_TEIDO");
        sb.append(",HIFUSIKKAN");
        sb.append(",HIFUSIKKAN_BUI");
        sb.append(",HIFUSIKKAN_TEIDO");
        sb.append(",KATA_KOUSHU_MIGI");
        sb.append(",KATA_KOUSHU_HIDARI");
        sb.append(",HIJI_KOUSHU_MIGI");
        sb.append(",HIJI_KOUSHU_HIDARI");
        sb.append(",MATA_KOUSHU_MIGI");
        sb.append(",MATA_KOUSHU_HIDARI");
        sb.append(",HIZA_KOUSHU_MIGI");
        sb.append(",HIZA_KOUSHU_HIDARI");
        sb.append(",JOUSI_SICCHOU_MIGI");
        sb.append(",JOUSI_SICCHOU_HIDARI");
        sb.append(",KASI_SICCHOU_MIGI");
        sb.append(",KASI_SICCHOU_HIDARI");
        sb.append(",TAIKAN_SICCHOU_MIGI");
        sb.append(",TAIKAN_SICCHOU_HIDARI");
        sb.append(",NYOUSIKKIN");
        sb.append(",NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(",TENTOU_KOSSETU");
        sb.append(",TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(",HAIKAI_KANOUSEI");
        sb.append(",HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",JOKUSOU_KANOUSEI");
        sb.append(",JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",ENGESEIHAIEN");
        sb.append(",ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(",CHOUHEISOKU");
        sb.append(",CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(",EKIKANKANSEN");
        sb.append(",EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(",SINPAIKINOUTEIKA");
        sb.append(",SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(",ITAMI");
        sb.append(",ITAMI_TAISHO_HOUSIN");
        sb.append(",DASSUI");
        sb.append(",DASSUI_TAISHO_HOUSIN");
        sb.append(",BYOUTAITA");
        sb.append(",BYOUTAITA_TAISHO_HOUSIN");
        sb.append(",BYOUTAITA_NM");
        sb.append(",HOUMON_SINRYOU");
        sb.append(",HOUMON_SINRYOU_UL");
        sb.append(",HOUMON_KANGO");
        sb.append(",HOUMON_KANGO_UL");
        sb.append(",HOUMON_REHA");
        sb.append(",HOUMON_REHA_UL");
        sb.append(",TUUSHO_REHA");
        sb.append(",TUUSHO_REHA_UL");
        sb.append(",TANKI_NYUSHO_RYOUYOU");
        sb.append(",TANKI_NYUSHO_RYOUYOU_UL");
        // 2009/02/03 [Tozo Tanaka] Add - begin
        sb.append(",HOUMON_SODAN");
        sb.append(",HOUMON_SODAN_UL");
        // 2009/02/03 [Tozo Tanaka] Add - end
        sb.append(",HOUMONSIKA_SINRYOU");
        sb.append(",HOUMONSIKA_SINRYOU_UL");
        sb.append(",HOUMONSIKA_EISEISIDOU");
        sb.append(",HOUMONSIKA_EISEISIDOU_UL");
        sb.append(",HOUMONYAKUZAI_KANRISIDOU");
        sb.append(",HOUMONYAKUZAI_KANRISIDOU_UL");
        sb.append(",HOUMONEIYOU_SHOKUJISIDOU");
        sb.append(",HOUMONEIYOU_SHOKUJISIDOU_UL");
        sb.append(",IGAKUTEKIKANRI_OTHER");
        sb.append(",IGAKUTEKIKANRI_OTHER_UL");
        sb.append(",IGAKUTEKIKANRI_OTHER_NM");
        sb.append(",KETUATU");
        sb.append(",KETUATU_RYUIJIKOU");
        sb.append(",SESHOKU");
        sb.append(",SESHOKU_RYUIJIKOU");
        sb.append(",ENGE");
        sb.append(",ENGE_RYUIJIKOU");
        sb.append(",IDOU");
        sb.append(",IDOU_RYUIJIKOU");
        sb.append(",KAIGO_OTHER");
        sb.append(",KANSENSHOU");
        sb.append(",KANSENSHOU_NM");
        sb.append(",IKN_TOKKI");
        sb.append(",HASE_SCORE");
        sb.append(",HASE_SCR_DT");
        sb.append(",P_HASE_SCORE");
        sb.append(",P_HASE_SCR_DT");
        sb.append(",INST_SEL_PR1");
        sb.append(",INST_SEL_PR2");
        // sb.append(",BODY_FIGURE");
        sb.append(",INSURED_NO");
        sb.append(",REQ_DT");
        sb.append(",REQ_NO");
        sb.append(",SEND_DT");
        sb.append(",KIND");
        sb.append(",INSURER_NO");
        sb.append(",INSURER_NM");
        sb.append(",INSURER_TYPE");
        sb.append(",CREATE_DT");
        sb.append(",KOUSIN_DT");
        sb.append(",LAST_TIME");
        sb.append(",DR_CONSENT");

        appendInsertIkenshoKeys(sb);

        sb.append(" )");
        sb.append(" VALUES");
        sb.append(" (");
        sb.append(getPatientNo());
        sb.append(",");
        sb.append(getEdaNo());
        sb.append(",");
        sb.append(getFormatKubun());
        sb.append(",");
        sb.append(getDBSafeDate("KINYU_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("IKN_CREATE_CNT", originalData));
        sb.append(",");
        sb.append(getDBSafeDate("LASTDAY", originalData));
        if (((Integer) VRBindPathParser.get("TAKA_FLAG", originalData))
                .intValue() == 1) {
            sb.append(",");
            sb.append(getDBSafeNumber("TAKA", originalData));
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "TAKA_OTHER_FLAG", new String[] { "TAKA_OTHER" }, false);
        } else {
            // �ꗥ�����l
            sb.append(",0");
            sb.append(",''");
        }

        sb.append(",");
        sb.append(getDBSafeNumber("TANKI_KIOKU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("NINCHI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("DENTATU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHOKUJI", originalData));
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                "MONDAI_FLAG", new String[] { "GNS_GNC", "MOUSOU", "CHUYA",
                        "BOUGEN", "BOUKOU", "TEIKOU", "HAIKAI", "FUSIMATU",
                        "FUKETU", "ISHOKU", "SEITEKI_MONDAI" }, false);
        if (((Integer) VRBindPathParser.get("MONDAI_FLAG", originalData))
                .intValue() == 1) {
            IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                    "MONDAI_OTHER", new String[] { "MONDAI_OTHER_NM" }, true);
        } else {
            // �ꗥ�����l
            sb.append(",0");
            sb.append(",''");
        }
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SEISIN",
                new String[] { "SEISIN_NM" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SENMONI",
                new String[] { "SENMONI_NM" }, true);
        sb.append(",");
        sb.append(getDBSafeNumber("KIKIUDE", originalData));
        sb.append(",");
        sb.append(getDBSafeString("WEIGHT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("HEIGHT", originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SISIKESSON",
                new String[] { "SISIKESSON_BUI" },
                new String[] { "SISIKESSON_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "MAHI",
                new String[] { "MAHI_BUI" }, new String[] { "MAHI_TEIDO" },
                true, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "KINRYOKU_TEIKA", new String[] { "KINRYOKU_TEIKA_BUI" },
                new String[] { "KINRYOKU_TEIKA_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "JOKUSOU",
                new String[] { "JOKUSOU_BUI" },
                new String[] { "JOKUSOU_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "HIFUSIKKAN",
                new String[] { "HIFUSIKKAN_BUI" },
                new String[] { "HIFUSIKKAN_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                "KOUSHU_FLAG", new String[] { "KATA_KOUSHU_MIGI",
                        "KATA_KOUSHU_HIDARI", "HIJI_KOUSHU_MIGI",
                        "HIJI_KOUSHU_HIDARI", "MATA_KOUSHU_MIGI",
                        "MATA_KOUSHU_HIDARI", "HIZA_KOUSHU_MIGI",
                        "HIZA_KOUSHU_HIDARI" }, false);
        IkenshoCommon.addFollowCheckNumberInsert(sb, originalData,
                "SICCHOU_FLAG", new String[] { "JOUSI_SICCHOU_MIGI",
                        "JOUSI_SICCHOU_HIDARI", "KASI_SICCHOU_MIGI",
                        "KASI_SICCHOU_HIDARI", "TAIKAN_SICCHOU_MIGI",
                        "TAIKAN_SICCHOU_HIDARI" }, false);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "NYOUSIKKIN",
                new String[] { "NYOUSIKKIN_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "TENTOU_KOSSETU",
                new String[] { "TENTOU_KOSSETU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "HAIKAI_KANOUSEI",
                new String[] { "HAIKAI_KANOUSEI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "JOKUSOU_KANOUSEI",
                new String[] { "JOKUSOU_KANOUSEI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "ENGESEIHAIEN", new String[] { "ENGESEIHAIEN_TAISHO_HOUSIN" },
                true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "CHOUHEISOKU",
                new String[] { "CHOUHEISOKU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "EKIKANKANSEN", new String[] { "EKIKANKANSEN_TAISHO_HOUSIN" },
                true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "SINPAIKINOUTEIKA",
                new String[] { "SINPAIKINOUTEIKA_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "ITAMI",
                new String[] { "ITAMI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "DASSUI",
                new String[] { "DASSUI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "BYOUTAITA",
                new String[] { "BYOUTAITA_TAISHO_HOUSIN", "BYOUTAITA_NM" },
                true);
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_SINRYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_SINRYOU_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_KANGO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_KANGO_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_REHA", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_REHA_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("TUUSHO_REHA", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("TUUSHO_REHA_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("TANKI_NYUSHO_RYOUYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("TANKI_NYUSHO_RYOUYOU_UL", originalData));
        // 2009/02/03 [Tozo Tanaka] Add - begin
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_SODAN", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMON_SODAN_UL", originalData));
        // 2009/02/03 [Tozo Tanaka] Add - end
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONSIKA_SINRYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONSIKA_SINRYOU_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONSIKA_EISEISIDOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONSIKA_EISEISIDOU_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONYAKUZAI_KANRISIDOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONYAKUZAI_KANRISIDOU_UL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONEIYOU_SHOKUJISIDOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("HOUMONEIYOU_SHOKUJISIDOU_UL", originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData,
                "IGAKUTEKIKANRI_OTHER",
                new String[] { "IGAKUTEKIKANRI_OTHER_NM" },
                new String[] { "IGAKUTEKIKANRI_OTHER_UL" }, false, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KETUATU",
                new String[] { "KETUATU_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "SESHOKU",
                new String[] { "SESHOKU_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "ENGE",
                new String[] { "ENGE_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "IDOU",
                new String[] { "IDOU_RYUIJIKOU" }, 2, true);
        sb.append(",");
        sb.append(getDBSafeString("KAIGO_OTHER", originalData));
        IkenshoCommon.addFollowCheckTextInsert(sb, originalData, "KANSENSHOU",
                new String[] { "KANSENSHOU_NM" }, true);
        sb.append(",");
        sb.append(getDBSafeString("IKN_TOKKI", originalData));
        sb.append(",");
        sb.append(getDBSafeString("HASE_SCORE", originalData));
        sb.append(",");
        sb.append(getDBSafeString("HASE_SCR_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("P_HASE_SCORE", originalData));
        sb.append(",");
        sb.append(getDBSafeString("P_HASE_SCR_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("INST_SEL_PR1", originalData));
        sb.append(",");
        sb.append(getDBSafeString("INST_SEL_PR2", originalData));
        // sb.append(",");
        // sb.append(String.valueOf(VRBindPathParser.get("BODY_FIGURE",
        // ikenshoData)));
        sb.append(",");
        sb.append(getDBSafeString("INSURED_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeDate("REQ_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeString("REQ_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeDate("SEND_DT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("KIND", originalData));
        sb.append(",");
        sb.append(getDBSafeString("INSURER_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("INSURER_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("INSURER_TYPE", originalData));
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",CURRENT_TIMESTAMP");
        sb.append(",");
        sb.append(String.valueOf(VRBindPathParser.get("DR_CONSENT",
                originalData)));

        appendInsertIkenshoValues(sb);

        sb.append(")");

        dbm.executeUpdate(sb.toString());

        doUpdateHumanPicture(dbm);

        hasOriginalDocument = true;
    }

    /**
     * override���Ĉӌ����}������SQL�L�[���ǉ����܂��B
     * 
     * @param sb �ǉ���
     */
    protected void appendInsertIkenshoKeys(StringBuffer sb) {

    }

    /**
     * override���Ĉӌ����}������SQL�o�����[���ǉ����܂��B
     * 
     * @throws ParseException ��͗�O
     * @param sb �ǉ���
     */
    protected void appendInsertIkenshoValues(StringBuffer sb)
            throws ParseException {

    }

    /**
     * override���Ĉӌ����X�V����SQL�L�[���ǉ����܂��B
     * 
     * @param sb �ǉ���
     * @throws ParseException ��͗�O
     */
    protected void appendUpdateIkenshoStetement(StringBuffer sb)
            throws ParseException {

    }

    protected void appendInsertCommonDocumentKeys(StringBuffer sb) {
        sb.append(",YKG_YOGO");

    }

    protected void appendInsertCommonDocumentValues(StringBuffer sb)
            throws ParseException {
        super.appendInsertCommonDocumentValues(sb);
        sb.append(",");
        sb.append(getDBSafeNumber("YKG_YOGO", originalData));
    }

    protected void appendUpdateCommonDocumentStetement(StringBuffer sb)
            throws ParseException {
        super.appendUpdateCommonDocumentStetement(sb);
        sb.append(",YKG_YOGO = ");
        sb.append(getDBSafeNumber("YKG_YOGO", originalData));
    }

    /**
     * override���Ĉӌ����̃t�H�[�}�b�g(�@�����敪)��Ԃ��������`���܂��B
     * 
     * @return �ӌ����̃t�H�[�}�b�g(�@�����敪)
     */
    protected String getFormatKubun() {
        return "0";
    }

    /**
     * �摜�R�}���h�����X�V���܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    protected void doReplaceGraphicsCommand(IkenshoFirebirdDBManager dbm)
            throws Exception {
        // �S�폜�E�S�o�^
        StringBuffer sb = new StringBuffer();
        sb.append("DELETE FROM");
        sb.append(" GRAPHICS_COMMAND");
        sb.append(" WHERE");
        sb.append(" (GRAPHICS_COMMAND.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" AND (GRAPHICS_COMMAND.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");
        dbm.executeUpdate(sb.toString());

        VRArrayList array = (VRArrayList) VRBindPathParser.get(
                "GRAPHICS_COMMAND", originalData);
        int end = array.size();
        for (int i = 0; i < end; i++) {
            VRMap row = (VRMap) array.get(i);
            sb = new StringBuffer();
            sb.append("INSERT INTO");
            sb.append(" GRAPHICS_COMMAND");
            sb.append(" (");
            sb.append(" PATIENT_NO");
            sb.append(",EDA_NO");
            sb.append(",CMD_NO");
            sb.append(",X");
            sb.append(",Y");
            sb.append(",BUI");
            sb.append(",SX");
            sb.append(",SY");
            sb.append(",STRING");
            sb.append(",LAST_TIME");
            sb.append(") VALUES (");
            sb.append(getPatientNo());
            sb.append(",");
            sb.append(getEdaNo());
            sb.append(",");
            sb.append(i + 1);
            sb.append(",");
            sb.append(getDBSafeNumber("X", row));
            sb.append(",");
            sb.append(getDBSafeNumber("Y", row));
            sb.append(",");
            sb.append(getDBSafeNumber("BUI", row));
            sb.append(",");
            sb.append(getDBSafeNumber("SX", row));
            sb.append(",");
            sb.append(getDBSafeNumber("SY", row));
            sb.append(",");
            sb.append(getDBSafeString("STRING", row));
            sb.append(",CURRENT_TIMESTAMP");
            sb.append(")");
            dbm.executeUpdate(sb.toString());
        }

    }
    
    /**
     * �X�V���̐����f�̗L���Ɋւ���SQL��ύX���܂��B
     */
    protected void doUpdateDifferenceItemSenmoni(StringBuffer sb) throws Exception{
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SENMONI",
                new String[] { "SENMONI_NM" }, true);
    }
    

    /**
     * �ӌ��������X�V���܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    protected void doUpdateIkensho(IkenshoFirebirdDBManager dbm)
            throws Exception {

        // �ӌ���
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE");
        sb.append(" IKN_ORIGIN");
        sb.append(" SET");

        sb.append(" KINYU_DT = ");
        sb.append(getDBSafeDate("KINYU_DT", originalData));
        sb.append(",IKN_CREATE_CNT = ");
        sb.append(getDBSafeNumber("IKN_CREATE_CNT", originalData));
        sb.append(",LASTDAY = ");
        sb.append(getDBSafeDate("LASTDAY", originalData));
        if (((Integer) VRBindPathParser.get("TAKA_FLAG", originalData))
                .intValue() == 1) {
            sb.append(",TAKA = ");
            sb.append(getDBSafeNumber("TAKA", originalData));
            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                    "TAKA_OTHER_FLAG", new String[] { "TAKA_OTHER" }, false);
        } else {
            // �ꗥ�����l
            sb.append(",TAKA = 0");
            sb.append(",TAKA_OTHER = ''");
        }

        sb.append(",TANKI_KIOKU = ");
        sb.append(getDBSafeNumber("TANKI_KIOKU", originalData));
        sb.append(",NINCHI = ");
        sb.append(getDBSafeNumber("NINCHI", originalData));
        sb.append(",DENTATU = ");
        sb.append(getDBSafeNumber("DENTATU", originalData));
        sb.append(",SHOKUJI = ");
        sb.append(getDBSafeNumber("SHOKUJI", originalData));
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "MONDAI_FLAG", new String[] { "GNS_GNC", "MOUSOU", "CHUYA",
                        "BOUGEN", "BOUKOU", "TEIKOU", "HAIKAI", "FUSIMATU",
                        "FUKETU", "ISHOKU", "SEITEKI_MONDAI" }, false);
        if (((Integer) VRBindPathParser.get("MONDAI_FLAG", originalData))
                .intValue() == 1) {
            IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                    "MONDAI_OTHER", new String[] { "MONDAI_OTHER_NM" }, true);
        } else {
            // �ꗥ�����l
            sb.append(",MONDAI_OTHER = 0");
            sb.append(",MONDAI_OTHER_NM = ''");
        }
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SEISIN",
                new String[] { "SEISIN_NM" }, true);
        
        // 2006/08/09
        // ��t�ӌ��� - �����f�̗L���A���ɑΉ�����SQL�ύX
        // Replace - begin [Masahiko Higuchi]
            // IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SENMONI",
            //   new String[] { "SENMONI_NM" }, true);
        doUpdateDifferenceItemSenmoni(sb);
        // Replace - end
        
        sb.append(",KIKIUDE = ");
        sb.append(getDBSafeNumber("KIKIUDE", originalData));
        sb.append(",WEIGHT = ");
        sb.append(getDBSafeString("WEIGHT", originalData));
        sb.append(",HEIGHT = ");
        sb.append(getDBSafeString("HEIGHT", originalData));
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SISIKESSON",
                new String[] { "SISIKESSON_BUI" },
                new String[] { "SISIKESSON_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "MAHI",
                new String[] { "MAHI_BUI" }, new String[] { "MAHI_TEIDO" },
                true, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "KINRYOKU_TEIKA", new String[] { "KINRYOKU_TEIKA_BUI" },
                new String[] { "KINRYOKU_TEIKA_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "JOKUSOU",
                new String[] { "JOKUSOU_BUI" },
                new String[] { "JOKUSOU_TEIDO" }, true, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "HIFUSIKKAN",
                new String[] { "HIFUSIKKAN_BUI" },
                new String[] { "HIFUSIKKAN_TEIDO" }, true, true);
        // 2006/07/28 - ��t�ӌ���
        // Replace - begin [Masahiko Higuchi]
        appendDifferenceUpdateItem(sb);
                // IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                //         "KOUSHU_FLAG", new String[] { "KATA_KOUSHU_MIGI",
                //                 "KATA_KOUSHU_HIDARI", "HIJI_KOUSHU_MIGI",
                //                 "HIJI_KOUSHU_HIDARI", "MATA_KOUSHU_MIGI",
                //                 "MATA_KOUSHU_HIDARI", "HIZA_KOUSHU_MIGI",
                //                 "HIZA_KOUSHU_HIDARI" }, false);
        // Replace - end
        
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "SICCHOU_FLAG", new String[] { "JOUSI_SICCHOU_MIGI",
                        "JOUSI_SICCHOU_HIDARI", "KASI_SICCHOU_MIGI",
                        "KASI_SICCHOU_HIDARI", "TAIKAN_SICCHOU_MIGI",
                        "TAIKAN_SICCHOU_HIDARI" }, false);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "NYOUSIKKIN",
                new String[] { "NYOUSIKKIN_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "TENTOU_KOSSETU",
                new String[] { "TENTOU_KOSSETU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "HAIKAI_KANOUSEI",
                new String[] { "HAIKAI_KANOUSEI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "JOKUSOU_KANOUSEI",
                new String[] { "JOKUSOU_KANOUSEI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "ENGESEIHAIEN", new String[] { "ENGESEIHAIEN_TAISHO_HOUSIN" },
                true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "CHOUHEISOKU",
                new String[] { "CHOUHEISOKU_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "EKIKANKANSEN", new String[] { "EKIKANKANSEN_TAISHO_HOUSIN" },
                true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "SINPAIKINOUTEIKA",
                new String[] { "SINPAIKINOUTEIKA_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "ITAMI",
                new String[] { "ITAMI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "DASSUI",
                new String[] { "DASSUI_TAISHO_HOUSIN" }, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "BYOUTAITA",
                new String[] { "BYOUTAITA_TAISHO_HOUSIN", "BYOUTAITA_NM" },
                true);
        sb.append(",HOUMON_SINRYOU = ");
        sb.append(getDBSafeNumber("HOUMON_SINRYOU", originalData));
        sb.append(",HOUMON_SINRYOU_UL = ");
        sb.append(getDBSafeNumber("HOUMON_SINRYOU_UL", originalData));
        sb.append(",HOUMON_KANGO = ");
        sb.append(getDBSafeNumber("HOUMON_KANGO", originalData));
        sb.append(",HOUMON_KANGO_UL = ");
        sb.append(getDBSafeNumber("HOUMON_KANGO_UL", originalData));
        sb.append(",HOUMON_REHA = ");
        sb.append(getDBSafeNumber("HOUMON_REHA", originalData));
        sb.append(",HOUMON_REHA_UL = ");
        sb.append(getDBSafeNumber("HOUMON_REHA_UL", originalData));
        sb.append(",TUUSHO_REHA = ");
        sb.append(getDBSafeNumber("TUUSHO_REHA", originalData));
        sb.append(",TUUSHO_REHA_UL = ");
        sb.append(getDBSafeNumber("TUUSHO_REHA_UL", originalData));
        sb.append(",TANKI_NYUSHO_RYOUYOU = ");
        sb.append(getDBSafeNumber("TANKI_NYUSHO_RYOUYOU", originalData));
        sb.append(",TANKI_NYUSHO_RYOUYOU_UL = ");
        sb.append(getDBSafeNumber("TANKI_NYUSHO_RYOUYOU_UL", originalData));
        // 2009/02/03 [Tozo Tanaka] Add - begin
        sb.append(",HOUMON_SODAN = ");
        sb.append(getDBSafeNumber("HOUMON_SODAN", originalData));
        sb.append(",HOUMON_SODAN_UL = ");
        sb.append(getDBSafeNumber("HOUMON_SODAN_UL", originalData));
        // 2009/02/03 [Tozo Tanaka] Add - end
        sb.append(",HOUMONSIKA_SINRYOU = ");
        sb.append(getDBSafeNumber("HOUMONSIKA_SINRYOU", originalData));
        sb.append(",HOUMONSIKA_SINRYOU_UL = ");
        sb.append(getDBSafeNumber("HOUMONSIKA_SINRYOU_UL", originalData));
        sb.append(",HOUMONSIKA_EISEISIDOU = ");
        sb.append(getDBSafeNumber("HOUMONSIKA_EISEISIDOU", originalData));
        sb.append(",HOUMONSIKA_EISEISIDOU_UL = ");
        sb.append(getDBSafeNumber("HOUMONSIKA_EISEISIDOU_UL", originalData));
        sb.append(",HOUMONYAKUZAI_KANRISIDOU = ");
        sb.append(getDBSafeNumber("HOUMONYAKUZAI_KANRISIDOU", originalData));
        sb.append(",HOUMONYAKUZAI_KANRISIDOU_UL = ");
        sb.append(getDBSafeNumber("HOUMONYAKUZAI_KANRISIDOU_UL", originalData));
        sb.append(",HOUMONEIYOU_SHOKUJISIDOU = ");
        sb.append(getDBSafeNumber("HOUMONEIYOU_SHOKUJISIDOU", originalData));
        sb.append(",HOUMONEIYOU_SHOKUJISIDOU_UL = ");
        sb.append(getDBSafeNumber("HOUMONEIYOU_SHOKUJISIDOU_UL", originalData));
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData,
                "IGAKUTEKIKANRI_OTHER",
                new String[] { "IGAKUTEKIKANRI_OTHER_NM" },
                new String[] { "IGAKUTEKIKANRI_OTHER_UL" }, false, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "KETUATU",
                new String[] { "KETUATU_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "SESHOKU",
                new String[] { "SESHOKU_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "ENGE",
                new String[] { "ENGE_RYUIJIKOU" }, 2, true);
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "IDOU",
                new String[] { "IDOU_RYUIJIKOU" }, 2, true);
        sb.append(",KAIGO_OTHER = ");
        sb.append(getDBSafeString("KAIGO_OTHER", originalData));
        IkenshoCommon.addFollowCheckTextUpdate(sb, originalData, "KANSENSHOU",
                new String[] { "KANSENSHOU_NM" }, true);
        sb.append(",IKN_TOKKI = ");
        sb.append(getDBSafeString("IKN_TOKKI", originalData));
        sb.append(",HASE_SCORE = ");
        sb.append(getDBSafeString("HASE_SCORE", originalData));
        sb.append(",HASE_SCR_DT = ");
        sb.append(getDBSafeString("HASE_SCR_DT", originalData));
        sb.append(",P_HASE_SCORE = ");
        sb.append(getDBSafeString("P_HASE_SCORE", originalData));
        sb.append(",P_HASE_SCR_DT = ");
        sb.append(getDBSafeString("P_HASE_SCR_DT", originalData));
        sb.append(",INST_SEL_PR1 = ");
        sb.append(getDBSafeString("INST_SEL_PR1", originalData));
        sb.append(",INST_SEL_PR2 = ");
        sb.append(getDBSafeString("INST_SEL_PR2", originalData));

        // sb.append(",BODY_FIGURE = ");
        // sb.append(String.valueOf(VRBindPathParser.get("BODY_FIGURE",
        // ikenshoData)).replaceAll("'", "''"));
        sb.append(",INSURED_NO = ");
        sb.append(getDBSafeString("INSURED_NO", originalData));
        sb.append(",REQ_DT = ");
        sb.append(getDBSafeDate("REQ_DT", originalData));
        sb.append(",REQ_NO = ");
        sb.append(getDBSafeString("REQ_NO", originalData));
        sb.append(",SEND_DT = ");
        sb.append(getDBSafeDate("SEND_DT", originalData));
        sb.append(",KIND = ");
        sb.append(getDBSafeNumber("KIND", originalData));
        sb.append(",INSURER_NO = ");
        sb.append(getDBSafeString("INSURER_NO", originalData));
        sb.append(",INSURER_NM = ");
        sb.append(getDBSafeString("INSURER_NM", originalData));
        sb.append(",INSURER_TYPE = ");
        sb.append(getDBSafeNumber("INSURER_TYPE", originalData));
        // sb.append(",CREATE_DT = ");
        // sb.append(String.valueOf(VRBindPathParser.get("CREATE_DT",
        // ikenshoData)));
        sb.append(",KOUSIN_DT = CURRENT_TIMESTAMP");
        sb.append(",LAST_TIME = CURRENT_TIMESTAMP");
        sb.append(",DR_CONSENT = ");
        sb.append(String.valueOf(VRBindPathParser.get("DR_CONSENT",
                originalData)));

        appendUpdateIkenshoStetement(sb);

        sb.append(" WHERE");
        sb.append(" (IKN_ORIGIN.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(IKN_ORIGIN.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");

        dbm.executeUpdate(sb.toString());

        doUpdateHumanPicture(dbm);
    }

    /**
     * �l�̐}���X�V���܂��B
     * 
     * @param dbm DBManager
     * @throws SQLException ������O
     */
    protected void doUpdateHumanPicture(IkenshoFirebirdDBManager dbm)
            throws SQLException {
        // �l�̐}
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE");
        sb.append(" IKN_ORIGIN");
        sb.append(" SET");
        sb.append(" BODY_FIGURE = ?");
        sb.append(" WHERE");
        sb.append(" (IKN_ORIGIN.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(IKN_ORIGIN.EDA_NO = ");
        sb.append(getEdaNo());
        sb.append(")");
        byte[] image = mindBody2.getPicture().getImageByteArray();
        dbm.executeUpdateBLOB(sb.toString(), new ByteArrayInputStream(image),
                image.length);
    }

    /**
     * �����e�[�u���X�V�����Ɏg�p���鍀�ڂ�ǉ����܂��B
     * @param sb
     */
    protected void appendUpdateBillStatements(StringBuffer sb) throws Exception{
        // 2006/08/02 - �d�q�����Z�X�V�Ή�
        // Addition - begin [Masahiko Higuchi]
        sb.append(" ,DR_ADD_IT = ");
        sb.append(getDBSafeNumberNullToZero("DR_ADD_IT",originalData));
        sb.append(" ,SHOSIN_ADD_IT = ");
        sb.append(getDBSafeNumberNullToZero("SHOSIN_ADD_IT",originalData));
        // ��Ë@�փR�[�h�ǉ�
        sb.append(" ,DR_CD = ");
        sb.append(getDBSafeNumberNullToZero("DR_CD",originalData));
        // Addition - end
        
        // �d�q�����Z�i�厡��ӌ����E�v�۔��ʁj�t���O�ǉ��Ή�
        // 2006/09/07
        // Addition - begin [Masahiko Higuchi]
        sb.append(" ,SHOSIN_ADD_IT_TYPE = ");
        sb.append(getDBSafeNumberNullToZero("SHOSIN_ADD_IT_TYPE",originalData));
        // Addition - end
        
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        sb.append(",XRAY_TANJUN_SATUEI_DIGITAL = ");
        sb.append(getDBSafeNumber("XRAY_TANJUN_SATUEI_DIGITAL", originalData));
        sb.append(",EXP_XRAY_TS_DIGITAL = ");
        sb.append(getDBSafeNumber("EXP_XRAY_TS_DIGITAL", originalData));
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        
    }
    
    
    protected void doUpdateBill(IkenshoFirebirdDBManager dbm) throws Exception {
        // 2006/09/21[Tozo Tanaka] : add begin
        // �O�̂��ߓ]�L�g���b�v
        Object obj = originalData.getData("KOUZA_KIND");
        if (obj == null) {
            originalData.setData("KOUZA_MEIGI", originalData
                    .getData("FURIKOMI_MEIGI"));
            originalData.setData("KOUZA_NO", originalData
                    .getData("BANK_KOUZA_NO"));
            originalData.setData("KOUZA_KIND", originalData
                    .getData("BANK_KOUZA_KIND"));
        }
        // 2006/09/21[Tozo Tanaka] : add begin

        // ������
        StringBuffer sb = new StringBuffer();
        sb.append("UPDATE");
        sb.append(" IKN_BILL");
        sb.append(" SET");
        sb.append(" BANK_NM  = ");
        sb.append(getDBSafeString("BANK_NM", originalData));
        sb.append(",BANK_SITEN_NM  = ");
        sb.append(getDBSafeString("BANK_SITEN_NM", originalData));
        sb.append(",KOUZA_NO  = ");
        sb.append(getDBSafeString("KOUZA_NO", originalData));
        sb.append(",KOUZA_KIND = ");
        sb.append(getDBSafeNumber("KOUZA_KIND", originalData));
        sb.append(",KOUZA_MEIGI  = ");
        sb.append(getDBSafeString("KOUZA_MEIGI", originalData));
        sb.append(",JIGYOUSHA_NO  = ");
        sb.append(getDBSafeString("JIGYOUSHA_NO", originalData));
        sb.append(",KAISETUSHA_NM  = ");
        sb.append(getDBSafeString("KAISETUSHA_NM", originalData));
        sb.append(",DR_NO  = ");
        sb.append(getDBSafeString("DR_NO", originalData));
        sb.append(",IKN_CHARGE = ");
        sb.append(getDBSafeNumber("IKN_CHARGE", originalData));
        sb.append(",SHOSIN_TAISHOU = ");
        sb.append(getDBSafeNumber("SHOSIN_TAISHOU", originalData));
        sb.append(",SHOSIN = ");
        sb.append(getDBSafeNumber("SHOSIN", originalData));
        sb.append(",SHOSIN_TEKIYOU  = ");
        sb.append(getDBSafeString("SHOSIN_TEKIYOU", originalData));
        sb.append(",XRAY_TANJUN_SATUEI = ");
        sb.append(getDBSafeNumber("XRAY_TANJUN_SATUEI", originalData));
        sb.append(",XRAY_SHASIN_SINDAN = ");
        sb.append(getDBSafeNumber("XRAY_SHASIN_SINDAN", originalData));
        sb.append(",XRAY_FILM = ");
        sb.append(getDBSafeNumber("XRAY_FILM", originalData));
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(",XRAY_DIGITAL_MANAGEMENT = ");
        sb.append(getDBSafeNumber("XRAY_DIGITAL_MANAGEMENT", originalData));
        sb.append(",XRAY_DIGITAL_FILM = ");
        sb.append(getDBSafeNumber("XRAY_DIGITAL_FILM", originalData));
        sb.append(",XRAY_DIGITAL_IMAGING = ");
        sb.append(getDBSafeNumber("XRAY_DIGITAL_IMAGING", originalData));
        // 2009/01/09[Tozo Tanaka] : add end
        sb.append(",XRAY_TEKIYOU  = ");
        sb.append(getDBSafeString("XRAY_TEKIYOU", originalData));
        sb.append(",BLD_SAISHU = ");
        sb.append(getDBSafeNumber("BLD_SAISHU", originalData));
        sb.append(",BLD_IPPAN_MASHOU_KETUEKI = ");
        sb.append(getDBSafeNumber("BLD_IPPAN_MASHOU_KETUEKI", originalData));
        sb.append(",BLD_IPPAN_EKIKAGAKUTEKIKENSA = ");
        sb
                .append(getDBSafeNumber("BLD_IPPAN_EKIKAGAKUTEKIKENSA",
                        originalData));
        sb.append(",BLD_IPPAN_TEKIYOU  = ");
        sb.append(getDBSafeString("BLD_IPPAN_TEKIYOU", originalData));
        sb.append(",BLD_KAGAKU_KETUEKIKAGAKUKENSA = ");
        sb
                .append(getDBSafeNumber("BLD_KAGAKU_KETUEKIKAGAKUKENSA",
                        originalData));
        sb.append(",BLD_KAGAKU_SEIKAGAKUTEKIKENSA = ");
        sb
                .append(getDBSafeNumber("BLD_KAGAKU_SEIKAGAKUTEKIKENSA",
                        originalData));
        sb.append(",BLD_KAGAKU_TEKIYOU  = ");
        sb.append(getDBSafeString("BLD_KAGAKU_TEKIYOU", originalData));
        sb.append(",NYO_KENSA = ");
        sb.append(getDBSafeNumber("NYO_KENSA", originalData));
        sb.append(",NYO_KENSA_TEKIYOU  = ");
        sb.append(getDBSafeString("NYO_KENSA_TEKIYOU", originalData));
        sb.append(",ZAITAKU_SINKI_CHARGE = ");
        sb.append(getDBSafeNumber("ZAITAKU_SINKI_CHARGE", originalData));
        sb.append(",ZAITAKU_KEIZOKU_CHARGE = ");
        sb.append(getDBSafeNumber("ZAITAKU_KEIZOKU_CHARGE", originalData));
        sb.append(",SISETU_SINKI_CHARGE = ");
        sb.append(getDBSafeNumber("SISETU_SINKI_CHARGE", originalData));
        sb.append(",SISETU_KEIZOKU_CHARGE = ");
        sb.append(getDBSafeNumber("SISETU_KEIZOKU_CHARGE", originalData));
        sb.append(",SHOSIN_SINRYOUJO = ");
        sb.append(getDBSafeNumber("SHOSIN_SINRYOUJO", originalData));
        sb.append(",SHOSIN_HOSPITAL = ");
        sb.append(getDBSafeNumber("SHOSIN_HOSPITAL", originalData));
        sb.append(",SHOSIN_OTHER = ");
        sb.append(getDBSafeNumber("SHOSIN_OTHER", originalData));
        sb.append(",EXP_KS = ");
        sb.append(getDBSafeNumber("EXP_KS", originalData));
        sb.append(",EXP_KIK_MKI = ");
        sb.append(getDBSafeNumber("EXP_KIK_MKI", originalData));
        sb.append(",EXP_KIK_KEKK = ");
        sb.append(getDBSafeNumber("EXP_KIK_KEKK", originalData));
        sb.append(",EXP_KKK_KKK = ");
        sb.append(getDBSafeNumber("EXP_KKK_KKK", originalData));
        sb.append(",EXP_KKK_SKK = ");
        sb.append(getDBSafeNumber("EXP_KKK_SKK", originalData));
        sb.append(",EXP_NITK = ");
        sb.append(getDBSafeNumber("EXP_NITK", originalData));
        sb.append(",EXP_XRAY_TS = ");
        sb.append(getDBSafeNumber("EXP_XRAY_TS", originalData));
        sb.append(",EXP_XRAY_SS = ");
        sb.append(getDBSafeNumber("EXP_XRAY_SS", originalData));
        sb.append(",EXP_XRAY_FILM = ");
        sb.append(getDBSafeNumber("EXP_XRAY_FILM", originalData));
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(",EXP_XRAY_DIGITAL_MANAGEMENT = ");
        sb.append(getDBSafeNumber("EXP_XRAY_DIGITAL_MANAGEMENT", originalData));
        sb.append(",EXP_XRAY_DIGITAL_FILM = ");
        sb.append(getDBSafeNumber("EXP_XRAY_DIGITAL_FILM", originalData));
        sb.append(",EXP_XRAY_DIGITAL_IMAGING = ");
        sb.append(getDBSafeNumber("EXP_XRAY_DIGITAL_IMAGING", originalData));
        // 2009/01/09[Tozo Tanaka] : add end
        sb.append(",TAX = ");
        sb.append(getDBSafeNumber("TAX", originalData));
        sb.append(",OUTPUT_PATTERN = ");
        sb.append(getDBSafeNumber("OUTPUT_PATTERN", originalData));
        sb.append(",ISS_INSURER_NO  = ");
        sb.append(getDBSafeString("ISS_INSURER_NO", originalData));
        sb.append(",ISS_INSURER_NM  = ");
        sb.append(getDBSafeString("ISS_INSURER_NM", originalData));
        sb.append(",SKS_INSURER_NO  = ");
        sb.append(getDBSafeString("SKS_INSURER_NO", originalData));
        sb.append(",SKS_INSURER_NM  = ");
        sb.append(getDBSafeString("SKS_INSURER_NM", originalData));
        sb.append(",FD_OUTPUT_KBN = ");
        sb.append(billFDOutputKubun.intValue());
        sb.append(",FD_TIMESTAMP = ");
        if (billFDOutputTime == null) {
            sb.append("NULL");
        } else {
            sb.append(IkenshoConstants.FORMAT_PASSIVE_YMD_HMS
                    .format(billFDOutputTime));
        }
        sb.append(",HAKKOU_KBN = ");
        sb.append(billHakkouKubun.intValue());
        sb.append(",SINSEI_DT = ");
        sb.append(getDBSafeDate("SINSEI_DT", originalData));

        sb.append(",LAST_TIME = CURRENT_TIMESTAMP");

        // 2006/08/02 - �d�q�����Z�X�V�Ή�
        // Addition - begin [Masahiko Higuchi]
        appendUpdateBillStatements(sb);
        // Addition - end
        
        sb.append(" WHERE");
        sb.append(" (IKN_BILL.PATIENT_NO = ");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append("AND(IKN_BILL.EDA_NO = ");
        sb.append(getEdaNo());

        sb.append(")");

        dbm.executeUpdate(sb.toString());

    }
    
    /**
     * �����e�[�u���ɒǉ�����L�[��ǉ����܂��B
     */
    protected void appendInsertBillKeys(StringBuffer sb) throws Exception{
        // �d�q�����Z�Ή�
        // Addition -begin [Masahiko Higuchi]
        sb.append(" ,DR_ADD_IT");
        sb.append(" ,SHOSIN_ADD_IT");
        sb.append(" ,DR_CD");
        // Addition - end
        // �d�q�����Z�i�厡��ӌ����E�v�۔��ʁj�t���O�ǉ��Ή�
        // 2006/09/07
        // Addition - begin [Masahiko Higuchi]
        sb.append(" ,SHOSIN_ADD_IT_TYPE");
        // Addition - end
        
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        sb.append(" ,XRAY_TANJUN_SATUEI_DIGITAL");
        sb.append(" ,EXP_XRAY_TS_DIGITAL");
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
    }
    
    /**
     * �����e�[�u���ɒǉ�����l��ݒ肵�܂��B
     * @param sb
     */
    protected void appendInsertBillValues(StringBuffer sb) throws Exception{
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("DR_ADD_IT",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SHOSIN_ADD_IT",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("DR_CD",originalData));
        // �d�q�����Z�i�厡��ӌ����E�v�۔��ʁj�t���O�ǉ��Ή�
        // 2006/09/07
        // Addition - begin [Masahiko Higuchi]
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("SHOSIN_ADD_IT_TYPE",originalData));
        // Addition - end
        
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin �f�Õ�V�P���̕ύX�Ή�
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("XRAY_TANJUN_SATUEI_DIGITAL",originalData));
        sb.append(",");
        sb.append(getDBSafeNumberNullToZero("EXP_XRAY_TS_DIGITAL",originalData));
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        
    }
    

    protected void doInsertBill(IkenshoFirebirdDBManager dbm) throws Exception {
        // �O�̂��ߓ]�L�g���b�v
        Object obj = originalData.getData("KOUZA_KIND");
        if (obj == null) {
            originalData.setData("KOUZA_MEIGI", originalData
                    .getData("FURIKOMI_MEIGI"));
            originalData.setData("KOUZA_NO", originalData
                    .getData("BANK_KOUZA_NO"));
            originalData.setData("KOUZA_KIND", originalData
                    .getData("BANK_KOUZA_KIND"));
        }

        // �ӌ���
        StringBuffer sb = new StringBuffer();
        sb.append("INSERT INTO");
        sb.append(" IKN_BILL");
        sb.append(" (");
        sb.append(" PATIENT_NO");
        sb.append(",EDA_NO");
        sb.append(",BANK_NM");
        sb.append(",BANK_SITEN_NM");
        sb.append(",KOUZA_NO");
        sb.append(",KOUZA_KIND");
        sb.append(",KOUZA_MEIGI");
        sb.append(",JIGYOUSHA_NO");
        sb.append(",KAISETUSHA_NM");
        sb.append(",DR_NO");
        sb.append(",IKN_CHARGE");
        sb.append(",SHOSIN_TAISHOU");
        sb.append(",SHOSIN");
        sb.append(",SHOSIN_TEKIYOU");
        sb.append(",XRAY_TANJUN_SATUEI");
        sb.append(",XRAY_SHASIN_SINDAN");
        sb.append(",XRAY_FILM");
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(",XRAY_DIGITAL_MANAGEMENT");
        sb.append(",XRAY_DIGITAL_FILM");
        sb.append(",XRAY_DIGITAL_IMAGING");
        // 2009/01/09[Tozo Tanaka] : add end
        sb.append(",XRAY_TEKIYOU");
        sb.append(",BLD_SAISHU");
        sb.append(",BLD_IPPAN_MASHOU_KETUEKI");
        sb.append(",BLD_IPPAN_EKIKAGAKUTEKIKENSA");
        sb.append(",BLD_IPPAN_TEKIYOU");
        sb.append(",BLD_KAGAKU_KETUEKIKAGAKUKENSA");
        sb.append(",BLD_KAGAKU_SEIKAGAKUTEKIKENSA");
        sb.append(",BLD_KAGAKU_TEKIYOU");
        sb.append(",NYO_KENSA");
        sb.append(",NYO_KENSA_TEKIYOU");
        sb.append(",ZAITAKU_SINKI_CHARGE");
        sb.append(",ZAITAKU_KEIZOKU_CHARGE");
        sb.append(",SISETU_SINKI_CHARGE");
        sb.append(",SISETU_KEIZOKU_CHARGE");
        sb.append(",SHOSIN_SINRYOUJO");
        sb.append(",SHOSIN_HOSPITAL");
        sb.append(",SHOSIN_OTHER");
        sb.append(",EXP_KS");
        sb.append(",EXP_KIK_MKI");
        sb.append(",EXP_KIK_KEKK");
        sb.append(",EXP_KKK_KKK");
        sb.append(",EXP_KKK_SKK");
        sb.append(",EXP_NITK");
        sb.append(",EXP_XRAY_TS");
        sb.append(",EXP_XRAY_SS");
        sb.append(",EXP_XRAY_FILM");
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(",EXP_XRAY_DIGITAL_MANAGEMENT");
        sb.append(",EXP_XRAY_DIGITAL_FILM");
        sb.append(",EXP_XRAY_DIGITAL_IMAGING");
        // 2009/01/09[Tozo Tanaka] : add end
        sb.append(",TAX");
        sb.append(",OUTPUT_PATTERN");
        sb.append(",ISS_INSURER_NO");
        sb.append(",ISS_INSURER_NM");
        sb.append(",SKS_INSURER_NO");
        sb.append(",SKS_INSURER_NM");
        sb.append(",FD_OUTPUT_KBN");
        sb.append(",FD_TIMESTAMP");
        sb.append(",HAKKOU_KBN");
        sb.append(",SINSEI_DT");
        sb.append(",LAST_TIME");
        
        // 2006/08/02 - ��t�ӌ��� �d�q�����Z�ۑ��Ή�
        // Addition - begin [Masahiko Higuchi]
        appendInsertBillKeys(sb);
        // Addition - end
        
        sb.append(" )");
        sb.append(" VALUES");
        sb.append(" (");
        sb.append(getPatientNo());
        sb.append(",");
        sb.append(getEdaNo());
        sb.append(",");
        sb.append(getDBSafeString("BANK_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("BANK_SITEN_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("KOUZA_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("KOUZA_KIND", originalData));
        sb.append(",");
        sb.append(getDBSafeString("KOUZA_MEIGI", originalData));
        sb.append(",");
        sb.append(getDBSafeString("JIGYOUSHA_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("KAISETUSHA_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("DR_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("IKN_CHARGE", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHOSIN_TAISHOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHOSIN", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SHOSIN_TEKIYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("XRAY_TANJUN_SATUEI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("XRAY_SHASIN_SINDAN", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("XRAY_FILM", originalData));
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(",");
        sb.append(getDBSafeNumber("XRAY_DIGITAL_MANAGEMENT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("XRAY_DIGITAL_FILM", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("XRAY_DIGITAL_IMAGING", originalData));
        // 2009/01/09[Tozo Tanaka] : add end
        sb.append(",");
        sb.append(getDBSafeString("XRAY_TEKIYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("BLD_SAISHU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("BLD_IPPAN_MASHOU_KETUEKI", originalData));
        sb.append(",");
        sb
                .append(getDBSafeNumber("BLD_IPPAN_EKIKAGAKUTEKIKENSA",
                        originalData));
        sb.append(",");
        sb.append(getDBSafeString("BLD_IPPAN_TEKIYOU", originalData));
        sb.append(",");
        sb
                .append(getDBSafeNumber("BLD_KAGAKU_KETUEKIKAGAKUKENSA",
                        originalData));
        sb.append(",");
        sb
                .append(getDBSafeNumber("BLD_KAGAKU_SEIKAGAKUTEKIKENSA",
                        originalData));
        sb.append(",");
        sb.append(getDBSafeString("BLD_KAGAKU_TEKIYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("NYO_KENSA", originalData));
        sb.append(",");
        sb.append(getDBSafeString("NYO_KENSA_TEKIYOU", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("ZAITAKU_SINKI_CHARGE", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("ZAITAKU_KEIZOKU_CHARGE", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SISETU_SINKI_CHARGE", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SISETU_KEIZOKU_CHARGE", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHOSIN_SINRYOUJO", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHOSIN_HOSPITAL", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("SHOSIN_OTHER", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_KS", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_KIK_MKI", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_KIK_KEKK", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_KKK_KKK", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_KKK_SKK", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_NITK", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_XRAY_TS", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_XRAY_SS", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_XRAY_FILM", originalData));
        // 2009/01/09[Tozo Tanaka] : add begin
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_XRAY_DIGITAL_MANAGEMENT", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_XRAY_DIGITAL_FILM", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("EXP_XRAY_DIGITAL_IMAGING", originalData));
        // 2009/01/09[Tozo Tanaka] : add end
        sb.append(",");
        sb.append(getDBSafeNumber("TAX", originalData));
        sb.append(",");
        sb.append(getDBSafeNumber("OUTPUT_PATTERN", originalData));
        sb.append(",");
        sb.append(getDBSafeString("ISS_INSURER_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("ISS_INSURER_NM", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SKS_INSURER_NO", originalData));
        sb.append(",");
        sb.append(getDBSafeString("SKS_INSURER_NM", originalData));
        sb.append(",");
        sb.append(billFDOutputKubun.intValue());
        sb.append(",");
        if (billFDOutputTime == null) {
            sb.append("NULL");
        } else {
            sb.append(IkenshoConstants.FORMAT_PASSIVE_YMD_HMS
                    .format(billFDOutputTime));
        }
        sb.append(",");
        sb.append(getBillHakkouKubun().intValue());
        sb.append(",");
        sb.append(getDBSafeDate("SINSEI_DT", originalData));

        sb.append(",CURRENT_TIMESTAMP");
        
        // 2006/08/02 - �d�q�����Z�ۑ������Ή�
        // Addition - begin [Masahiko Higuchi]
        appendInsertBillValues(sb);
        // Addition - end
        
        sb.append(" )");

        dbm.executeUpdate(sb.toString());

    }

    /**
     * override���ă^�u�ɕK�v�ȃ^�u�N���X��ǉ����܂��B
     */
    protected void addTabs() {
        applicant = new IkenshoIkenshoInfoApplicant();
        sick = new IkenshoIkenshoInfoSick();
        special = new IkenshoIkenshoInfoSpecial();
        care1 = new IkenshoIkenshoInfoCare1();
        care2 = new IkenshoIkenshoInfoCare2();
        mindBody1 = new IkenshoIkenshoInfoMindBody1();
        mindBody2 = new IkenshoIkenshoInfoMindBody2();
        mention = new IkenshoIkenshoInfoMention();
        organ = new IkenshoIkenshoInfoOrgan();
        bill = new IkenshoIkenshoInfoBill();

        // Add
        tabs.addTab("�\����", applicant);
        tabs.addTab("���a", sick);
        tabs.addTab("���ʂȈ��", special);
        tabs.addTab("�S�g�̏�ԂP", mindBody1);
        tabs.addTab("�S�g�̏�ԂQ", mindBody2);
        tabs.addTab("���P", care1);
        tabs.addTab("���Q", care2);
        tabs.addTab("���L�����E����", mention);
        tabs.addTab("��Ë@��", organ);
        tabPanel.add(bill, BorderLayout.SOUTH);

        tabArray.clear();
        tabArray.add(applicant);
        tabArray.add(sick);
        tabArray.add(special);
        tabArray.add(mindBody1);
        tabArray.add(mindBody2);
        tabArray.add(care1);
        tabArray.add(care2);
        tabArray.add(mention);
        tabArray.add(organ);
        tabArray.add(bill);

    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIkenshoInfo() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        setStatusText("�厡��ӌ���");
        buttons.setTitle("�厡��ӌ���");

        if(mindBody1!=null){
            mindBody1.setFollowDisabledComponents(new JComponent[] { tabs, update,
                print, buttons.getBackButton() });
        }
        
        applicant
                .addWriteDateChangeListener(new IkenshoWriteDateChangeListener() {
                    public void writeDataChanged(EventObject e) {
                        try {
                            Date date = ((IkenshoEraDateTextField) e
                                    .getSource()).getDate();
                            if (date != null) {
                                editorDates
                                        .setText(IkenshoConstants.FORMAT_ERA_YMD
                                                .format(date));
                            }
                        } catch (Exception ex) {
                        }
                    }
                });
        applicant
                .addApplicantNameChageListener(new IkenshoDocumentAffairApplicantNameChageListener() {
                    public void nameChanged(EventObject e) {
                        editorName.setText(((JTextField) e.getSource())
                                .getText());
                    }
                });

        bill.setVisible(false);
    }

    private void jbInit() throws Exception {
        addTabs();
    }

    /**
     * ��Ë@�փ^�u��Ԃ��܂��B
     * 
     * @return ��Ë@�փ^�u
     */
    public IkenshoIkenshoInfoOrgan getOrgan() {
        return organ;
    }

    /**
     * ���L�����^�u��Ԃ��܂��B
     * 
     * @return ���L�����^�u
     */
    public IkenshoIkenshoInfoMention getMention() {
        return mention;
    }

    /**
     * �������^�u��Ԃ��܂��B
     * 
     * @return �������^�u
     */
    public IkenshoIkenshoInfoBill getBill() {
        return bill;
    }

    /**
     * �\���҃^�u��Ԃ��܂��B
     * 
     * @return �\���҃^�u
     */
    public IkenshoIkenshoInfoApplicant getApplicant() {
        return applicant;
    }

    /**
     * CSV�t�@�C���̏o�͑Ώۋ敪��Ԃ��܂��B
     * 
     * @return CSV�t�@�C���̏o�͑Ώۋ敪
     */
    public Integer getBillFDOutputKubun() {
        return billFDOutputKubun;
    }

    /**
     * CSV�t�@�C���̏o�͑Ώۋ敪��ݒ肵�܂��B
     * 
     * @param billFDOutputKubun CSV�t�@�C���̏o�͑Ώۋ敪
     */
    public void setBillFDOutputKubun(Integer billFDOutputKubun) {
        this.billFDOutputKubun = billFDOutputKubun;
    }

    /**
     * CSV�t�@�C���̏o�͓�����ݒ肵�܂��B
     * 
     * @param billFDOutputTime CSV�t�@�C���̏o�͓���
     */
    public void setBillFDOutputTime(Date billFDOutputTime) {
        this.billFDOutputTime = billFDOutputTime;
    }

    /**
     * �w��̖@�����敪�ɊY������ӌ�����ʂɑJ�ڂ��܂��B
     * 
     * @param lowVersion �@�����敪(IkenshoConstants.IKENSYO_LOW_DEFAULT /
     *            IkenshoConstants.IKENSYO_LOW_H18)
     * @param param �N���p�����[�^
     * @throws Exception ������O
     */
    public static void goIkensho(int lowVersion, VRMap param)
            throws Exception {
        String className;
        String title;
        switch (lowVersion) {
        case IkenshoConstants.IKENSHO_LOW_DEFAULT:
            className = IkenshoIkenshoInfo.class.getName();
            title = "�厡��ӌ���";
            break;
        case IkenshoConstants.IKENSHO_LOW_H18:
            className = IkenshoIkenshoInfoH18.class.getName();
            title = "�厡��ӌ���";
            break;
        case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
            className = IkenshoIshiIkenshoInfo.class.getName();
            title = "��t�ӌ���";
            break;
        default:
            return;
        }

        ACAffairInfo affair = new ACAffairInfo(className, param, title, true);
        ACFrame.getInstance().next(affair);

    }

    /**
     * �������̔��s�敪��ݒ肵�܂��B
     * 
     * @param billHakkouKubun �������̔��s�敪
     */
    protected void setBillHakkouKubun(Integer billHakkouKubun) {
        this.billHakkouKubun = billHakkouKubun;
    }

    /**
     * �������̔��s�敪��Ԃ��܂��B
     * 
     * @return �������̔��s�敪
     */
    protected Integer getBillHakkouKubun() {
        return billHakkouKubun;
    }

    /**
     * ��t�ӌ����Ƃ̕ύX�_���`���܂��B 
     */
    protected void appendDifferenceUpdateItem(StringBuffer sb) throws Exception{
        // 2006/07/28 - ��t�ӌ���
        // Addition - begin [Masahiko Higuchi]
        IkenshoCommon.addFollowCheckNumberUpdate(sb, originalData,
                "KOUSHU_FLAG", new String[] { "KATA_KOUSHU_MIGI",
                        "KATA_KOUSHU_HIDARI", "HIJI_KOUSHU_MIGI",
                        "HIJI_KOUSHU_HIDARI", "MATA_KOUSHU_MIGI",
                        "MATA_KOUSHU_HIDARI", "HIZA_KOUSHU_MIGI",
                        "HIZA_KOUSHU_HIDARI" }, false);
        // Addition - end
        
    }
    
}
