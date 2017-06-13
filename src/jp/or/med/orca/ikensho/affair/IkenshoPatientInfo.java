package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.im.InputSubset;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.util.Arrays;

import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACKanaSendTextField;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACSingleSelectFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.ACMessageBoxDialogPlus;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoFormatTypeFormat;
import jp.or.med.orca.ikensho.lib.ShijishoFormatTypeFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoPatientInfo extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel contents = new VRPanel();
    private VRPanel editor = new VRPanel();
    private ACGroupBox ikensho = new ACGroupBox();
    private ACGroupBox sijisho = new ACGroupBox();
    private ACButton sijishoDetail = new ACButton();
    private VRPanel document = new VRPanel();
    private ACTable sijishoTable = new ACTable();
    private VRPanel sijishoButtons = new VRPanel();
    private ACTable ikenshoTable = new ACTable();
    private ACButton ikenshoDetail = new ACButton();
    private VRPanel ikenshoButtons = new VRPanel();
    private ACLabelContainer names = new ACLabelContainer();
    private ACClearableRadioButtonGroup sex = new ACClearableRadioButtonGroup();
    private ACLabelContainer sexs = new ACLabelContainer();
    private ACKanaSendTextField name = new ACKanaSendTextField();
    private ACLabelContainer kanas = new ACLabelContainer();
    private ACTextField kana = new ACTextField();
    private IkenshoEraDateTextField birth = new IkenshoEraDateTextField();
    private ACLabelContainer births = new ACLabelContainer();
    private IkenshoZipTextField zip = new IkenshoZipTextField();
    private ACLabelContainer zips = new ACLabelContainer();
    private ACLabelContainer addresss = new ACLabelContainer();
    private ACTextField address = new ACTextField();
    private ACLabelContainer tels = new ACLabelContainer();
    private ACTextField id = new ACTextField();
    private ACLabelContainer ids = new ACLabelContainer();
    private ACButton ikenshoNew = new ACButton();
    private ACButton sijishoNew = new ACButton();
    private ACButton ikenshoDelete = new ACButton();
    private ACButton sijishoDelete = new ACButton();
    private VRLabel idIndomation = new VRLabel();
    private IkenshoTelTextField tel = new IkenshoTelTextField();
    private VRLayout editorLayout = new VRLayout();

    private ACSingleSelectFormat ikenshoNewFormat = new ACSingleSelectFormat(
            "��", "");
    private ACSingleSelectFormat sijishoNewFormat = new ACSingleSelectFormat(
            "��", "");
    private VRMap patientData;
    private VRArrayList ikenshoArray;
    private VRArrayList sijishoArray;
    private VRArrayList patientArray;
    private String nowMode = IkenshoConstants.AFFAIR_MODE_INSERT;
    private int newDocumentStatus = IkenshoCommon.NEW_DOC_NONE;
    private String patientNo;
    private ACTableModelAdapter ikenshoTableModel;
    private ACTableModelAdapter sijishoTableModel;

    // 2007/09/18 [Masahiko Higuchi] Addition - begin
    private ACLabelContainer showContainer;
    private ACIntegerCheckBox showCheck;
    // 2007/09/18 [Masahiko Higuchi] Addition - end
    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "PATIENT", new String[] { "PATIENT_NO" }, new Format[] { null },
            "LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_COMMON_KEY = new ACPassiveKey(
            "COMMON_IKN_SIS",
            new String[] { "PATIENT_NO", "EDA_NO", "DOC_KBN" }, new Format[] {
                    null, null, null }, "COMMON_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_IKENSHO_KEY = new ACPassiveKey(
            "IKN_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO" },
            new Format[] { null, null }, "IKN_ORIGIN_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_BILL_KEY = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO", "EDA_NO" }, new Format[] {
                    null, null }, "IKN_BILL_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_SIJISHO_KEY = new ACPassiveKey(
            "SIS_ORIGIN", new String[] { "PATIENT_NO", "EDA_NO" },
            new Format[] { null, null }, "LAST_TIME", "LAST_TIME");

    public void initAffair(ACAffairInfo affair) throws Exception {
        addDetailTrigger(ikenshoDetail);
        addDetailTrigger(sijishoDetail);
        addInsertTrigger(ikenshoNew);
        addInsertTrigger(sijishoNew);
        addDeleteTrigger(ikenshoDelete);
        addDeleteTrigger(sijishoDelete);
        addTableSelectedTrigger(ikenshoTable);
        addTableSelectedTrigger(sijishoTable);
        addTableDoubleClickedTrigger(ikenshoTable);
        addTableDoubleClickedTrigger(sijishoTable);

        IkenshoSnapshot.getInstance().setRootContainer(editor);

        VRMap previewData = null;
        VRMap params = affair.getParameters();
        if (VRBindPathParser.has("PREV_DATA", params)) {
            // ��ʑJ�ڃL���b�V���f�[�^������ꍇ�́A�p�����^��u��������
            params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
            previewData = params;
        }

        if (VRBindPathParser.has("PATIENT_NO", params)) {
            // ���Ҕԍ�����
            setNowMode(IkenshoConstants.AFFAIR_MODE_UPDATE);
            setPatientNo(String.valueOf(VRBindPathParser.get("PATIENT_NO",
                    params)));
        } else {
            // ���Ҕԍ��Ȃ�
            setNowMode(IkenshoConstants.AFFAIR_MODE_INSERT);

        }

        doSelect();

        if (previewData != null) {
            patientData.putAll(previewData);
            editor.bindSource();
        }

    }

    /**
     * �ӌ��������������܂��B
     * 
     * @param dbm DBManager
     * @throws SQLException ������O
     */
    protected void doSelectIkensho(IkenshoFirebirdDBManager dbm)
            throws SQLException {
        // �ӌ���
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_ORIGIN.FORMAT_KBN");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",IKN_ORIGIN.CREATE_DT");
        sb.append(",IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.LAST_TIME AS IKN_ORIGIN_LAST_TIME");
        sb.append(",IKN_BILL.LAST_TIME AS IKN_BILL_LAST_TIME");
        sb.append(",IKN_BILL.HAKKOU_KBN");
        sb.append(",IKN_BILL.FD_OUTPUT_KBN");
        sb.append(" FROM");
        sb.append(" IKN_ORIGIN");
        sb.append(" LEFT OUTER JOIN IKN_BILL");
        sb.append(" ON");
        sb.append(" (IKN_ORIGIN.PATIENT_NO=IKN_BILL.PATIENT_NO)");
        sb.append(" AND(IKN_ORIGIN.EDA_NO=IKN_BILL.EDA_NO)");
        sb.append(" WHERE");
        sb.append(" (IKN_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" ORDER BY");
        sb.append(" IKN_ORIGIN.EDA_NO DESC");

        ikenshoArray = (VRArrayList) dbm.executeQuery(sb.toString());
        ikenshoTableModel.setAdaptee(ikenshoArray);

        reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, ikenshoArray);
        reservedPassive(PASSIVE_CHECK_BILL_KEY, ikenshoArray);
    }

    /**
     * �w���������������܂��B
     * 
     * @param dbm DBManager
     * @throws SQLException ������O
     */
    protected void doSelectSijisho(IkenshoFirebirdDBManager dbm)
            throws SQLException {
        // �w����
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" SIS_ORIGIN.EDA_NO");
        sb.append(",SIS_ORIGIN.KINYU_DT");
        sb.append(",SIS_ORIGIN.CREATE_DT");
        sb.append(",SIS_ORIGIN.PATIENT_NO");
        sb.append(",SIS_ORIGIN.LAST_TIME");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(",SIS_ORIGIN.FORMAT_KBN");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(" FROM");
        sb.append(" SIS_ORIGIN");
        sb.append(" WHERE");
        sb.append(" (SIS_ORIGIN.PATIENT_NO=");
        sb.append(getPatientNo());
        sb.append(")");
        sb.append(" ORDER BY");
        sb.append(" SIS_ORIGIN.EDA_NO DESC");

        sijishoArray = (VRArrayList) dbm.executeQuery(sb.toString());
        sijishoTableModel.setAdaptee(sijishoArray);
        reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, sijishoArray);
    }

    /**
     * �I���������s���܂��B
     * 
     * @throws Exception ������O
     */
    private void doSelect() throws Exception {
        clearReservedPassive();

        if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
            // removeInsertTrigger(update);
            addUpdateTrigger(update);
            update.setText("�X�V(S)");
            update.setToolTipText("���݂̓��e���X�V���܂��B");

            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" *");
            sb.append(" FROM");
            sb.append(" PATIENT");
            sb.append(" WHERE");
            sb.append(" (PATIENT.PATIENT_NO=");
            sb.append(getPatientNo());
            sb.append(")");

            patientArray = (VRArrayList) dbm.executeQuery(sb.toString());

            if (patientArray.getDataSize() > 0) {
                patientData = (VRMap) patientArray.getData();
                editor.setSource(patientData);
                editor.bindSource();
                
                //2006/02/11[Tozo Tanaka] : add begin
                //TODO canEdit?
            }else{
                removeUpdateTrigger(update);
                setNowMode(IkenshoConstants.AFFAIR_MODE_INSERT);
                doSelect();
                return;
                //2006/02/11[Tozo Tanaka] : add end
            }

            // �ӌ���
            doSelectIkensho(dbm);

            if (ikenshoTable.getRowCount() > 0) {
                ikenshoTable.setSelectedModelRow(0);
            }

            // �w����
            doSelectSijisho(dbm);

            if (sijishoTable.getRowCount() > 0) {
                sijishoTable.setSelectedModelRow(0);
            }

            dbm.finalize();

            // �ŐV�}�[�N������
            checkNewDocumentMark();
        } else if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
            addInsertTrigger(update);
            update.setText("�o�^(S)");
            update.setToolTipText("���݂̓��e��o�^���܂��B");

            ikenshoArray = new VRArrayList();
            sijishoArray = new VRArrayList();
            patientArray = new VRArrayList();
            patientData = (VRMap) editor.createSource();
            
            // 2007/10/16 [Masahiko Higuchi] Addition - begin
            // �o�^���[�h�̏ꍇ�̓`�F�b�N�����Ă���
            patientData.setData("SHOW_FLAG",new Integer(1));
            // 2007/10/16 [Masahiko Higuchi] Addition - end
            
            patientArray.addData(patientData);

            editor.setSource(patientData);
            editor.bindSource();
            setStatusText("���ҍŐV��{���");

            reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, ikenshoArray);
            reservedPassive(PASSIVE_CHECK_BILL_KEY, ikenshoArray);
            reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, sijishoArray);
        }
        // �p�b�V�u�`�F�b�N�\��
        reservedPassive(PASSIVE_CHECK_KEY, patientArray);

        checkDocumentButtonsEnabled();

        IkenshoSnapshot.getInstance().snapshot();

    }

    public boolean canBack(VRMap parameters) throws Exception {
        boolean result = true;
        if (IkenshoSnapshot.getInstance().isModified()) {
            if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
                // �ǉ����[�h�̏ꍇ
                switch (ACMessageBox.showYesNoCancel("�o�^���e��ۑ����܂����H",
                        "�o�^���Ė߂�(S)", 'S', "�j�����Ė߂�(R)", 'R')) {
                case ACMessageBox.RESULT_YES:

                    // �o�^���Ė߂�
                    result = doInsert();
                    break;
                case ACMessageBox.RESULT_CANCEL:
                    result = false;
                    break;
                }
            } else if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
                // �X�V���[�h�̏ꍇ
                switch (ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�ۑ����܂����H",
                        "�X�V���Ė߂�(S)", 'S', "�j�����Ė߂�(R)", 'R')) {
                case ACMessageBox.RESULT_YES:

                    // �X�V���Ė߂�
                    result = doUpdate();
                    break;
                case ACMessageBox.RESULT_CANCEL:
                    result = false;
                    break;
                }
            }

        }

        //2006/02/11[Tozo Tanaka] : replace begin
        //TODO canEdit?
//        parameters.putAll(patientData);
        if((parameters!=null)&&(patientData!=null)){
            parameters.putAll(patientData);
        }
        //2006/02/11[Tozo Tanaka] : replace end
        return result;
    }

    public boolean canClose() throws Exception {
        if (IkenshoSnapshot.getInstance().isModified()) {
            return ACMessageBox.show("�X�V���ꂽ���e�͔j������܂��B\n�I�����Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK;
        }
        return true;
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {

        Object source = e.getSource();
        if (source == ikenshoDelete) {
            int row = ikenshoTable.getSelectedModelRow();
            if (row < 0) {
                return;
            }

            String message;
            if ((newDocumentStatus == IkenshoCommon.NEW_DOC_IKENSHO)
                    && (row == 0)) {
                message = "�I������Ă���ӌ����͍ŐV�̏��ł��B\n�폜����ƍŐV���ҏ���1�O�̔łɖ߂�܂��B\n�폜���Ă���낵���ł����H";
            } else {
                message = "�I������Ă���ӌ������폜���Ă���낵���ł����H";
            }
            if (ACMessageBox.show(message, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {

                IkenshoFirebirdDBManager dbm = null;
                try {
                    // �p�b�V�u�`�F�b�N�J�n
                    clearPassiveTask();
                    addPassiveDeleteTask(PASSIVE_CHECK_IKENSHO_KEY, row);
                    addPassiveDeleteTask(PASSIVE_CHECK_BILL_KEY, row);

                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return;
                    }
                    // �p�b�V�u�`�F�b�N�I��

                    StringBuffer sb;
                    sb = new StringBuffer();
                    sb.append(" WHERE");
                    sb.append(" (PATIENT_NO = ");
                    sb.append(getPatientNo());
                    sb.append(")");
                    sb.append(" AND (EDA_NO = ");
                    sb.append(getDBSafeNumber("EDA_NO",
                            (VRMap) ikenshoArray.getData(row)));
                    sb.append(")");
                    String whereStatement = sb.toString();

                    sb = new StringBuffer();
                    sb.append("DELETE");
                    sb.append(" FROM");
                    sb.append(" COMMON_IKN_SIS");
                    sb.append(whereStatement);
                    sb.append(" AND (DOC_KBN = ");
                    sb.append(IkenshoConstants.DOC_KBN_IKENSHO);
                    sb.append(")");
                    dbm.executeUpdate(sb.toString());

                    sb = new StringBuffer();
                    sb.append("DELETE");
                    sb.append(" FROM");
                    sb.append(" IKN_ORIGIN");
                    sb.append(whereStatement);
                    dbm.executeUpdate(sb.toString());

                    sb = new StringBuffer();
                    sb.append("DELETE");
                    sb.append(" FROM");
                    sb.append(" IKN_BILL");
                    sb.append(whereStatement);
                    dbm.executeUpdate(sb.toString());

                    dbm.commitTransaction();

                    int lastSelectedIndex = ikenshoTable.getSelectedSortedRow();
                    doSelectIkensho(dbm);
                    ikenshoTable
                            .setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

                    dbm.finalize();

                    checkNewDocumentMark();
                    checkDocumentButtonsEnabled();
                } catch (Exception ex) {
                    if (dbm != null) {
                        dbm.rollbackTransaction();
                        dbm.finalize();
                    }
                    throw ex;
                }

            }
        } else if (source == sijishoDelete) {
            int row = sijishoTable.getSelectedModelRow();
            if (row < 0) {
                return;
            }

            String message;
            if ((newDocumentStatus == IkenshoCommon.NEW_DOC_SIJISHO)
                    && (row == 0)) {
                message = "�I������Ă���w�����͍ŐV�̏��ł��B\n�폜����ƍŐV���ҏ���1�O�̔łɖ߂�܂��B\n�폜���Ă���낵���ł����H";
            } else {
                message = "�I������Ă���w�������폜���Ă���낵���ł����H";
            }
            if (ACMessageBox.show(message, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {

                IkenshoFirebirdDBManager dbm = null;
                try {
                    // �p�b�V�u�`�F�b�N�J�n
                    clearPassiveTask();
                    addPassiveDeleteTask(PASSIVE_CHECK_SIJISHO_KEY, row);

                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return;
                    }
                    // �p�b�V�u�`�F�b�N�I��

                    StringBuffer sb;
                    sb = new StringBuffer();
                    sb.append(" WHERE");
                    sb.append(" (PATIENT_NO = ");
                    sb.append(getPatientNo());
                    sb.append(")");
                    sb.append(" AND (EDA_NO = ");
                    sb.append(getDBSafeNumber("EDA_NO",
                            (VRMap) sijishoArray.getData(row)));
                    sb.append(")");
                    String whereStatement = sb.toString();

                    sb = new StringBuffer();
                    sb.append("DELETE");
                    sb.append(" FROM");
                    sb.append(" COMMON_IKN_SIS");
                    sb.append(whereStatement);
                    sb.append(" AND (DOC_KBN = ");
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//                    sb.append(IkenshoConstants.DOC_KBN_SIJISHO);
                    String kbn = getDBSafeNumber("FORMAT_KBN", (VRMap) sijishoArray.getData(row));
                    if (kbn.equals("0") || kbn.equals("1"))
                    {
                        sb.append(IkenshoConstants.DOC_KBN_SIJISHO);
                    }
                    else
                    {
                        sb.append(IkenshoConstants.DOC_KBN_SEISHIN);
                    }
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
                    sb.append(")");
                    dbm.executeUpdate(sb.toString());

                    sb = new StringBuffer();
                    sb.append("DELETE");
                    sb.append(" FROM");
                    sb.append(" SIS_ORIGIN");
                    sb.append(whereStatement);
                    dbm.executeUpdate(sb.toString());

                    dbm.commitTransaction();

                    int lastSelectedIndex = sijishoTable.getSelectedSortedRow();
                    doSelectSijisho(dbm);
                    sijishoTable
                            .setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

                    dbm.finalize();

                    checkNewDocumentMark();
                    checkDocumentButtonsEnabled();
                } catch (Exception ex) {
                    if (dbm != null) {
                        dbm.rollbackTransaction();
                        dbm.finalize();
                    }
                    throw ex;
                }

            }
        }
    }

    public Component getFirstFocusComponent() {
        return name;
    }

    /**
     * �X�V�����\����Ԃ��܂��B
     * 
     * @return �X�V�����\��
     */
    private boolean canUpdate() {

        // �G���[�`�F�b�N
        switch (birth.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("�����̓��t�ł��B");
            birth.requestChildFocus();
            return false;
        default:
            ACMessageBox.showExclamation("���t�Ɍ�肪����܂��B");
            birth.requestChildFocus();
            birth.setParentColor(false);
            return false;
        }

        if (isNullText(name.getText())) {
            ACMessageBox.showExclamation("��������͂��Ă��������B");
            name.requestFocus();
            return false;
        }

        if (sex.getSelectedIndex() == sex.getNoSelectIndex()) {
            ACMessageBox.showExclamation("���ʂ�I�����Ă��������B");
            sex.requestChildFocus();
            return false;
        }

        return true;

    }

    /**
     * �ǉ��������s���܂��B
     * 
     * @throws Exception ������O
     * @return boolean �ǉ������ɐ���������
     */
    private boolean doInsert() throws Exception {

        // �G���[�`�F�b�N
        if (!canUpdate()) {
            return false;
        }

        IkenshoFirebirdDBManager dbm = null;
        try {
            editor.applySource();
            patientData.setData("AGE", birth.getAge());

            // ���������`�F�b�N
            if (IkenshoCommon.hasSameName(patientData)) {
                if (ACMessageBox.show("����Ǝv���銳�҂����ɓo�^����Ă��܂��B\n�o�^���܂����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                    return false;
                }
            }

            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO");
            sb.append(" PATIENT");
            sb.append(" (");
            sb.append(" PATIENT_NM");
            sb.append(",PATIENT_KN");
            sb.append(",SEX");
            sb.append(",BIRTHDAY");
            sb.append(",AGE");
            sb.append(",POST_CD");
            sb.append(",ADDRESS");
            sb.append(",TEL1");
            sb.append(",TEL2");
            sb.append(",CHART_NO");
            sb.append(",KOUSIN_DT");
            sb.append(",LAST_TIME");
            // Addition 2007/10/02 [Masahiko Higuchi] begin �ꗗ�ɕ\���`�F�b�N�ǉ�
            sb.append(",SHOW_FLAG");
            // Addition 2007/10/02 [Masahiko Higuchi] end
            sb.append(" )");
            sb.append(" VALUES");
            sb.append(" (");
            sb.append(getDBSafeString("PATIENT_NM", patientData));
            sb.append(",");
            sb.append(getDBSafeString("PATIENT_KN", patientData));
            sb.append(",");
            sb.append(getDBSafeNumber("SEX", patientData));
            sb.append(",");
            sb.append(getDBSafeDate("BIRTHDAY", patientData));
            sb.append(",");
            sb.append(getDBSafeString("AGE", patientData));
            sb.append(",");
            sb.append(getDBSafeString("POST_CD", patientData));
            sb.append(",");
            sb.append(getDBSafeString("ADDRESS", patientData));
            sb.append(",");
            sb.append(getDBSafeString("TEL1", patientData));
            sb.append(",");
            sb.append(getDBSafeString("TEL2", patientData));
            sb.append(",");
            sb.append(getDBSafeString("CHART_NO", patientData));
            sb.append(",CURRENT_TIMESTAMP");
            sb.append(",CURRENT_TIMESTAMP");
            // Addition 2007/10/02 [Masahiko Higuchi] begin �ꗗ�ɕ\���`�F�b�N�ǉ�
            sb.append(",");
            sb.append(getDBSafeNumberNullToZero("SHOW_FLAG",patientData));
            // Addition 2007/10/02 [Masahiko Higuchi] end
            sb.append(")");

            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();
            dbm.executeUpdate(sb.toString());

            // �ǉ����ꂽ���Ҕԍ����擾
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" GEN_ID(GEN_PATIENT,0)");
            sb.append(" FROM");
            sb.append(" RDB$DATABASE");
            VRArrayList patientNoArray = (VRArrayList) dbm.executeQuery(sb
                    .toString());

            dbm.commitTransaction();
            dbm.finalize();

            ACMessageBox.show("�o�^����܂����B");

            setNowMode(IkenshoConstants.AFFAIR_MODE_UPDATE);
            setPatientNo(String.valueOf(VRBindPathParser.get("GEN_ID",
                    (VRMap) patientNoArray.getData())));
            doSelect();
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
     * ���ɍ쐬����}�Ԃɓ�����A�����W���Ɋ܂܂��ő�̎}�ԍ�+1���}�b�v�ɒǉ����܂��B
     * 
     * @param array �����W��
     * @param map �ǉ���
     * @throws ParseException ��͗�O
     */
    protected void addNextEdaNo(VRArrayList array, VRMap map)
            throws ParseException {
        if (array.getDataSize() > 0) {
            map.setData("EDA_NO", new Integer(((Integer) VRBindPathParser.get(
                    "EDA_NO", (VRMap) array.getData())).intValue() + 1));
        }
    }

    /**
     * ���A�p�p�����^�}�b�v�𐶐����ĕԂ��܂��B
     * 
     * @return ���A�p�p�����^�}�b�v
     */
    protected VRMap createPreviewData() {
        VRMap param = new VRHashMap();
        VRMap bs = new VRHashMap();
        bs.setData("AFFAIR_MODE", nowMode);
        bs.putAll(patientData);

        param.setData("PREV_DATA", bs);
        return param;
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {

        if (e.getSource() == update) {
            if (doInsert()) {
                ACFrame.getInstance().back();
                return;
            }

        } else if (e.getSource() == ikenshoNew) {

            //�ӌ����敪��I��
            int createDocType;
            switch (ACMessageBox.showYesNoCancel("�쐬����ӌ����̎�ނ�I�����Ă��������B",
                    "�厡��ӌ���(S)", 'S', "��t�ӌ���(I)", 'I')) {
            case ACMessageBox.RESULT_YES:
                createDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case ACMessageBox.RESULT_NO:
                createDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            default:
                return;
            }

            
            int end = ikenshoArray.getDataSize();
            for (int i = 0; i < end; i++) {
                VRMap row = (VRMap) ikenshoArray.getData(i);
                Object obj = VRBindPathParser.get("FD_OUTPUT_KBN",row);
                if (obj instanceof Integer) {
                    if (((Integer) obj).intValue() == 1) {
                        if (ACCastUtilities.toInt(VRBindPathParser.get(
                                "FORMAT_KBN", row), -1) == createDocType) {
                            //����̈ӌ����敪��CSV�o�͑Ώۂ����݂���ΐV�K�쐬�֎~
                            ACMessageBox.show("CSV�o�͑Ώۂ̈ӌ���������̂ŐV�K�쐬�ł��܂���B");
                            return;
                        }
                    }
                }
            }

            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show("���Ҋ�{��񂪍X�V����Ă��܂��B\n�X�V���܂����H",
                        ACMessageBox.BUTTON_YESNOCANCEL,
                        ACMessageBox.ICON_QUESTION);
                if (result == ACMessageBox.RESULT_YES) {
                    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
                        // �}��
                        if (!doInsert()) {
                            return;
                        }
                    } else if (IkenshoConstants.AFFAIR_MODE_UPDATE
                            .equals(nowMode)) {
                        // �X�V
                        if (!doUpdate()) {
                            return;
                        }
                    }
                } else if (result == ACMessageBox.RESULT_NO) {
                    editor.applySource();
                } else {
                    // �L�����Z������
                    return;
                }
            }

            int flag = IkenshoCommon.checkInsurerDoctorCheck();
            if ((flag & IkenshoCommon.CHECK_INSURER_NOTHING) > 0) {
                if (ACMessageBox.show("�ی��҂��o�^����Ă��܂���B���o�^���܂����H",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(IkenshoHokenshaShousai.class
                                    .getName(), createPreviewData(), "�ی��ҏڍ�"));
                    return;
                }
            }
            if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
                if (ACMessageBox.show("��Ë@�֏�񂪓o�^����Ă��܂���B���o�^���܂����H",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(
                                    IkenshoIryouKikanJouhouShousai.class
                                            .getName(), createPreviewData(),
                                    "��Ë@�֏��ڍ�"));
                    return;
                }
            }

            VRMap param = new VRHashMap(patientData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);
            // ��ɍŐV��{���̏�Ԃ������p������
            param.setData("OVERRIDE_PATIENT", patientData);
            addNextEdaNo(ikenshoArray, param);

            // switch (NCMessageBox.showYesNoCancel("�쐬����ӌ����̑Ή��N�x��I�����Ă��������B",
            // "����17�N�x(7)", '7', "����18�N�x(8)",
            // '8')) {
            // case NCMessageBox.RESULT_YES:
            // IkenshoIkenshoInfo.goIkensho(IkenshoConstants.IKENSHO_LOW_DEFAULT,
            // param);
            // break;
            // case NCMessageBox.RESULT_NO:
            IkenshoIkenshoInfo.goIkensho(createDocType, param);
            // break;
            // default:
            // return;
            // }

        } else if (e.getSource() == sijishoNew) {
            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show("���Ҋ�{��񂪍X�V����Ă��܂��B\n�X�V���܂����H",
                        ACMessageBox.BUTTON_YESNOCANCEL,
                        ACMessageBox.ICON_QUESTION);
                if (result == ACMessageBox.RESULT_YES) {
                    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
                        // �}��
                        if (!doInsert()) {
                            return;
                        }
                    } else if (IkenshoConstants.AFFAIR_MODE_UPDATE
                            .equals(nowMode)) {
                        // �X�V
                        if (!doUpdate()) {
                            return;
                        }
                    }
                } else if (result == ACMessageBox.RESULT_NO) {
                    editor.applySource();
                } else {
                    // �L�����Z������
                    return;
                }
            }

            int flag = IkenshoCommon.checkInsurerDoctorCheck();
            if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
                if (ACMessageBox.show("��Ë@�֏�񂪓o�^����Ă��܂���B���o�^���܂����H",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(
                                    IkenshoIryouKikanJouhouShousai.class
                                            .getName(), createPreviewData(),
                                    "��Ë@�֏��ڍ�"));
                    return;
                }
            }

            VRMap param = new VRHashMap(patientData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);
            // ��ɍŐV��{���̏�Ԃ������p������
            param.setData("OVERRIDE_PATIENT", patientData);
            addNextEdaNo(sijishoArray, param);
            
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//            ACFrame.getInstance().next(
//                    new ACAffairInfo(
//                            IkenshoHoumonKangoShijisho.class.getName(), param,
//                            "�K��Ō�w����"));
            ACAffairInfo affair = null;
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//            switch (ACMessageBox.showYesNoCancel("�쐬����w�����̎�ނ�I�����Ă��������B",
//                   "�K��Ō�w����(H)", 'H', "���ʖK��Ō�w����(T)", 'T')) {
//            case ACMessageBox.RESULT_YES:
//                affair = new ACAffairInfo(
//                      IkenshoHoumonKangoShijisho.class.getName(), param,
//                      "�K��Ō�w����");
//                break;
//            case ACMessageBox.RESULT_NO:
//                affair = new ACAffairInfo(
//                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param,
//                        "���ʖK��Ō�w����");
//                break;
            switch (ACMessageBox.showMessage(
                    "�쐬����w�����̎�ނ�I�����Ă��������B",
                    "�K��Ō�w����(H) ", 'H',
                    "���ʖK��Ō�w����(T)", 'T',
                    "���_�ȖK��Ō�w����(I)", 'I',
                    "���_�ȓ��ʖK��Ō�w����(U)", 'U')) {
            case ACMessageBoxDialogPlus.RESULT_SELECT1:
                affair = new ACAffairInfo(
                        IkenshoHoumonKangoShijisho.class.getName(), param, "�K��Ō�w����");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT2:
                affair = new ACAffairInfo(
                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param, "���ʖK��Ō�w����");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT3:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaHoumonKangoShijisho.class.getName(), param, "���_�ȖK��Ō�w����");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT4:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaTokubetsuHoumonKangoShijisho.class.getName(), param, "���_�ȓ��ʖK��Ō�w����");
                break;
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
            default:
                return;
            }
            ACFrame.getInstance().next(affair);
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  

        }

    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        checkDocumentButtonsEnabled();
    }

    protected void tableDoubleClicked(MouseEvent e) throws Exception {
        if (e.getSource() == ikenshoTable) {
            // if (e.getSource() == ikenshoTable.getTable()) {
            detailActionPerformed(new ActionEvent(ikenshoDetail, 0, ""));
        } else if (e.getSource() == sijishoTable) {
            // else if (e.getSource() == sijishoTable.getTable()) {
            detailActionPerformed(new ActionEvent(sijishoDetail, 0, ""));
        }
    }

    protected void detailActionPerformed(ActionEvent e) throws Exception {
        if (e.getSource() == ikenshoDetail) {
            int row = ikenshoTable.getSelectedModelRow();
            if (row < 0) {
                return;
            }

            if (IkenshoSnapshot.getInstance().isModified()) {
                switch (ACMessageBox.show("���Ҋ�{��񂪍X�V����Ă��܂��B\n�X�V���܂����H",
                        ACMessageBox.BUTTON_YESNOCANCEL)) {
                case ACMessageBox.RESULT_YES:
                    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
                        if (!doInsert()) {
                            return;
                        }
                    } else if (IkenshoConstants.AFFAIR_MODE_UPDATE
                            .equals(nowMode)) {
                        if (!doUpdate()) {
                            return;
                        }
                    }
                    break;
                case ACMessageBox.RESULT_NO:
                    break;
                default:
                    return;
                }

            }
            VRMap rowData = (VRMap) ikenshoArray.getData(row);

            VRMap param = new VRHashMap();
            param.putAll(patientData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_UPDATE);
            param.setData("EDA_NO", new Integer(((Integer) VRBindPathParser
                    .get("EDA_NO", rowData)).intValue()));

            // �����N���X�̐U�蕪��
            int lowVer = IkenshoConstants.IKENSHO_LOW_DEFAULT;
            if (VRBindPathParser.has("FORMAT_KBN", rowData)) {
                Object obj = VRBindPathParser.get("FORMAT_KBN", rowData);
                if (obj instanceof Integer) {
                    switch (((Integer) obj).intValue()) {
                    case 1:
                        lowVer = IkenshoConstants.IKENSHO_LOW_H18;
                        break;
                    case 2:
                        lowVer = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                        break;
                    }
                }
            }

            IkenshoIkenshoInfo.goIkensho(lowVer, param);

        } else if (e.getSource() == sijishoDetail) {
            int row = sijishoTable.getSelectedModelRow();
            if (row < 0) {
                return;
            }

            VRMap param = new VRHashMap();
            param.putAll(patientData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_UPDATE);
            param.setData("EDA_NO", new Integer(((Integer) VRBindPathParser
                    .get("EDA_NO", (VRMap) sijishoArray.getData(row)))
                    .intValue()));

            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//            ACAffairInfo affair = new ACAffairInfo(
//                    IkenshoHoumonKangoShijisho.class.getName(), param,
//                    "�K��Ō�w����");
            // �����N���X�̐U�蕪��
            VRMap rowData = (VRMap) sijishoArray.getData(row);
            ACAffairInfo affair = null;
            switch (ACCastUtilities.toInt(VRBindPathParser.get("FORMAT_KBN", rowData), 0)) {
            case 0:
                affair = new ACAffairInfo(
                      IkenshoHoumonKangoShijisho.class.getName(), param,
                      "�K��Ō�w����");
                break;
            case 1:
                affair = new ACAffairInfo(
                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param,
                        "���ʖK��Ō�w����");
                break;
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
            case 2:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaHoumonKangoShijisho.class.getName(), param,
                        "���_�ȖK��Ō�w����");
                break;
            case 3:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaTokubetsuHoumonKangoShijisho.class.getName(), param,
                        "���_�ȓ��ʖK��Ō�w����");
                break;
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
            default:
                return;
            }
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
            
            ACFrame.getInstance().next(affair);
        }

    }

    /**
     * �X�V�������s���܂��B
     * 
     * @throws Exception ������O
     * @return boolean �X�V�����ɐ���������
     */
    private boolean doUpdate() throws Exception {
        // �G���[�`�F�b�N
        if (!canUpdate()) {
            return false;
        }

        IkenshoFirebirdDBManager dbm = null;
        try {
            editor.applySource();
            patientData.setData("AGE", birth.getAge());

            // �p�b�V�u�`�F�b�N�J�n
            clearPassiveTask();
            addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);

            dbm = getPassiveCheckedDBManager();
            if (dbm == null) {
                ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                return false;
            }
            // �p�b�V�u�`�F�b�N�I��

            StringBuffer sb = new StringBuffer();
            sb.append("UPDATE");
            sb.append(" PATIENT");
            sb.append(" SET");
            sb.append(" PATIENT_NM = ");
            sb.append(getDBSafeString("PATIENT_NM", patientData));
            sb.append(",PATIENT_KN = ");
            sb.append(getDBSafeString("PATIENT_KN", patientData));
            sb.append(",SEX = ");
            sb.append(getDBSafeNumber("SEX", patientData));
            sb.append(",BIRTHDAY = ");
            sb.append(getDBSafeDate("BIRTHDAY", patientData));
            sb.append(",AGE = ");
            sb.append(getDBSafeString("AGE", patientData));
            sb.append(",POST_CD = ");
            sb.append(getDBSafeString("POST_CD", patientData));
            sb.append(",ADDRESS = ");
            sb.append(getDBSafeString("ADDRESS", patientData));
            sb.append(",TEL1 = ");
            sb.append(getDBSafeString("TEL1", patientData));
            sb.append(",TEL2 = ");
            sb.append(getDBSafeString("TEL2", patientData));
            sb.append(",CHART_NO = ");
            sb.append(getDBSafeString("CHART_NO", patientData));
            sb.append(",KOUSIN_DT = CURRENT_TIMESTAMP");
            sb.append(",LAST_TIME = CURRENT_TIMESTAMP");
            // Addition 2007/10/02 [Masahiko Higuchi] begin �ꗗ�ɕ\���`�F�b�N�ǉ�
            sb.append(",SHOW_FLAG = ");
            sb.append(getDBSafeNumberNullToZero("SHOW_FLAG",patientData));
            // Addition 2007/10/02 [Masahiko Higuchi] end
            sb.append(" WHERE");
            sb.append(" (PATIENT.PATIENT_NO = ");
            sb.append(String.valueOf(VRBindPathParser.get("PATIENT_NO",
                    patientData)));
            sb.append(")");

            dbm.executeUpdate(sb.toString());
            dbm.commitTransaction();
            dbm.finalize();

            ACMessageBox.show("�X�V����܂����B");

            doSelect();
        } catch (Exception ex) {
            if (dbm != null) {
                dbm.rollbackTransaction();
                dbm.finalize();
            }
            throw ex;
        }
        return true;
    }

    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (doUpdate()) {
            ACFrame.getInstance().back();
            return;
        }
    }

    /**
     * �e�[�u���̑I���󋵂ɂ��ƂÂ��A�{�^����Enabled���䂷��B
     */
    private void checkDocumentButtonsEnabled() {
        boolean enabled;
        enabled = (ikenshoTable.getSelectedModelRow() >= 0)
                && (ikenshoTable.getSelectedModelRow() < ikenshoTable
                        .getRowCount());
        ikenshoDetail.setEnabled(enabled);
        ikenshoDelete.setEnabled(enabled);

        enabled = (sijishoTable.getSelectedModelRow() >= 0)
                && (sijishoTable.getSelectedModelRow() < sijishoTable
                        .getRowCount());
        sijishoDetail.setEnabled(enabled);
        sijishoDelete.setEnabled(enabled);
    }

    /**
     * �ŐV�����Ƀ`�F�b�N�����܂��B
     * 
     * @throws Exception ��͗�O
     */
    private void checkNewDocumentMark() throws Exception {
        int useDoc = IkenshoCommon.getNewDocumentStatus(ikenshoArray, 0,
                "CREATE_DT", sijishoArray, 0, "CREATE_DT");

        switch (useDoc) {
        case IkenshoCommon.NEW_DOC_NONE:
            ikenshoNewFormat.setSelectedData(null);
            sijishoNewFormat.setSelectedData(null);

            setStatusText("���ސV�K�쐬�ɂ͐V�K�ɍ쐬�E�o�^���ꂽ���ނ̃f�[�^�����f����܂��B");
            break;
        case IkenshoCommon.NEW_DOC_IKENSHO:
            ikenshoNewFormat.setSelectedData(VRBindPathParser.get("EDA_NO",
                    (VRBindSource) ikenshoArray.getData()));
            sijishoNewFormat.setSelectedData(null);

            setStatusText("���ސV�K�쐬�ɂ́u"+IkenshoFormatTypeFormat.getInstance().format(VRBindPathParser
                            .get("FORMAT_KBN", (VRBindSource) ikenshoArray
                                    .getData())) +"�i"
                    + IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                            .get("KINYU_DT", (VRBindSource) ikenshoArray
                                    .getData())) + "�L���j�v�̃f�[�^�����f����܂��B");
            break;
        case IkenshoCommon.NEW_DOC_SIJISHO:
            ikenshoNewFormat.setSelectedData(null);
            sijishoNewFormat.setSelectedData(VRBindPathParser.get("EDA_NO",
                    (VRBindSource) sijishoArray.getData()));

            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//            setStatusText("���ސV�K�쐬�ɂ́u�K��Ō�w�����i"
//                    + IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
//                            .get("KINYU_DT", (VRBindSource) sijishoArray
//                                    .getData())) + "�L���j�v�̃f�[�^�����f����܂��B");
            setStatusText("���ސV�K�쐬�ɂ́u"+ShijishoFormatTypeFormat.getInstance().format(VRBindPathParser
                            .get("FORMAT_KBN", (VRBindSource) sijishoArray
                                    .getData())) +"�i"
                    + IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                            .get("KINYU_DT", (VRBindSource) sijishoArray
                                    .getData())) + "�L���j�v�̃f�[�^�����f����܂��B");
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
            break;
        }
        newDocumentStatus = useDoc;

        ikenshoTable.repaint();
        sijishoTable.repaint();
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoPatientInfo() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ikenshoTable.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
//                new VRTableColumn(0, 30, "�ŐV", SwingConstants.CENTER,
//                        ikenshoNewFormat),
//                new VRTableColumn(1, 50, "�쐬No.", SwingConstants.RIGHT),
//                new VRTableColumn(2, 100, "�敪", IkenshoFormatTypeFormat.getInstance()),
//                new VRTableColumn(3, 120, "�L����",
                new VRTableColumn(0, 50, "�ŐV", SwingConstants.CENTER,
                        ikenshoNewFormat),
                new VRTableColumn(1, 90, "�쐬No.", SwingConstants.RIGHT),
                new VRTableColumn(2, 160, "�敪", IkenshoFormatTypeFormat.getInstance()),
                new VRTableColumn(3, 200, "�L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new VRTableColumn(4, 70, "����", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_HAKKOU),
                new VRTableColumn(5, 70, "CSV�o��", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_FD_OUTPUT), }));

//        sijishoTable.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
//                new VRTableColumn(0, 30, "�ŐV", SwingConstants.CENTER,
//                        sijishoNewFormat),
//                // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//                new VRTableColumn(2, 120, "�敪", ShijishoFormatTypeFormat.getInstance()),
//                // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//                new VRTableColumn(1, 320, "�L����",
//                        IkenshoConstants.FORMAT_ERA_YMD), }));
        sijishoTable.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
                new VRTableColumn(0, 50, "�ŐV", SwingConstants.CENTER,
                        sijishoNewFormat),
                new VRTableColumn(2, 160, "�敪", ShijishoFormatTypeFormat.getInstance()), 
                new VRTableColumn(1, 320, "�L����",
                        IkenshoConstants.FORMAT_ERA_YMD), }));

        ikenshoTableModel = new ACTableModelAdapter(new VRArrayList(),
                new String[] { "EDA_NO", "EDA_NO", "FORMAT_KBN", "KINYU_DT", "HAKKOU_KBN",
                        "FD_OUTPUT_KBN" });
        ikenshoTable.setModel(ikenshoTableModel);

        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        sijishoTableModel = new ACTableModelAdapter(new VRArrayList(),
//                new String[] { "EDA_NO", "KINYU_DT", });
        sijishoTableModel = new ACTableModelAdapter(new VRArrayList(),
                new String[] { "EDA_NO", "KINYU_DT", "FORMAT_KBN" });
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sijishoTable.setModel(sijishoTableModel);

    }

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ��������O
     */
    private void jbInit() throws Exception {
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);
        update.setMnemonic('S');
//        update.setText("�X�V(S)");
        update.setText("�@�X�V(S)�@");
        buttons.setTitle("���ҍŐV��{���");
        sijishoDetail.setMnemonic('J');
        sijishoDetail.setText("�ҏW(J)");
        sijisho.setForeground(IkenshoConstants.COLOR_BORDER_TEXT_FOREGROUND);
        ikenshoDetail.setMnemonic('K');
        ikenshoDetail.setText("�ҏW(K)");
        names.setText("����");
        sexs.setText("����");
        kanas.setText("�ӂ肪��");
        births.setText("���N����");
        zips.setText("�X�֔ԍ�");
        addresss.setText("�Z��");
        tels.setText("�A����iTEL�j");
        ids.setText("����ID");
        id.setColumns(20);
        name.setColumns(30);
        name.setBindPath("PATIENT_NM");
        name.setMaxLength(15);
        name.setKanaField(kana);
        ikenshoNew.setMnemonic('A');
        ikenshoNew.setText("�V�K�쐬(A)");
        sijishoNew.setMnemonic('B');
        sijishoNew.setText("�V�K�쐬(B)");
        ikenshoDelete.setMnemonic('D');
        ikenshoDelete.setText("�폜(D)");
        sijishoDelete.setText("�폜(E)");
        sijishoDelete.setMnemonic('E');
        sex.setToolTipText("");
        sex.setBindPath("SEX");
        sex.setClearButtonToolTipText("�u���ʁv�̑S���ڂ̑I�����������܂��B");
        sex.setUseClearButton(false);
        kana.setColumns(30);
        kana.setBindPath("PATIENT_KN");
        kana.setMaxLength(30);
        id.setBindPath("CHART_NO");
        id.setMaxLength(20);
        address.setColumns(50);
        address.setBindPath("ADDRESS");
        address.setMaxLength(50);
        idIndomation.setText("���[������̕K�{���ڂł͂���܂���B");
        zip.setAddressTextField(address);
        zip.setBindPath("POST_CD");
        birth.setBindPath("BIRTHDAY");
        ikenshoTable.setColumnSort(false);
        sijishoTable.setColumnSort(false);
        ids.add(id, null);
        ids.add(idIndomation, null);
        kanas.add(kana, null);
        this.add(buttons, VRLayout.NORTH);
        buttons.add(update, VRLayout.EAST);
        this.add(contents, VRLayout.CLIENT);
        contents.setLayout(new BorderLayout());
        contents.add(editor, BorderLayout.NORTH);
        document.setLayout(new VRLayout());
        editor.setLayout(editorLayout);
        // 2007/09/18 [Masahiko Higuchi] Delete - begin
        // �t���[���C�A�E�g�ɐݒ肷�邽�߂ɍ폜
        //editor.add(names, VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/09/18 [Masahiko Higuchi] Delete - end
        // 2007/09/18 [Masahiko Higuchi] Addition - begin
        // �t���[���C�A�E�g
        editor.add(names, VRLayout.FLOW_INSETLINE);
        // �ꗗ�\���`�F�b�N�F�t���[���s���C�A�E�g
        editor.add(getShowContainer(),VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/09/18 [Masahiko Higuchi] Addition - end
        
        editor.add(kanas, VRLayout.FLOW_INSETLINE_RETURN);
        editor.add(sexs, VRLayout.FLOW_INSETLINE);
        editor.add(births, VRLayout.FLOW_INSETLINE_RETURN);
        editor.add(zips, VRLayout.FLOW_INSETLINE_RETURN);
        editor.add(addresss, VRLayout.FLOW_INSETLINE_RETURN);
        editor.add(tels, VRLayout.FLOW_INSETLINE_RETURN);
        editor.add(ids, VRLayout.FLOW_INSETLINE_RETURN);
        sexs.add(sex, null);
        names.add(name, null);
        contents.add(document, BorderLayout.CENTER);
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//        document.add(ikensho, VRLayout.CLIENT);
        document.add(ikensho, VRLayout.NORTH);
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
        ikensho.setLayout(new BorderLayout());
        ikensho.add(ikenshoButtons, BorderLayout.NORTH);
        ikenshoButtons.add(ikenshoDetail, null);
        ikensho.add(ikenshoTable, BorderLayout.CENTER);
        ikensho.setText("�厡��ӌ����E��t�ӌ���");
        ikensho.setForeground(IkenshoConstants.COLOR_BORDER_TEXT_FOREGROUND);
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//        document.add(sijisho, VRLayout.EAST);
        document.add(sijisho, VRLayout.CLIENT);
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
        sijisho.setLayout(new BorderLayout());
        sijisho.add(sijishoButtons, BorderLayout.NORTH);
        sijishoButtons.add(sijishoDetail, null);
        sijisho.add(sijishoTable, BorderLayout.CENTER);
        births.add(birth, null);
        zips.add(zip, null);
        addresss.add(address, null);
        tels.add(tel, null);
        ikenshoButtons.add(ikenshoNew, null);
        sijishoButtons.add(sijishoNew, null);
        ikenshoButtons.add(ikenshoDelete, null);
        sijishoButtons.add(sijishoDelete, null);
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        //sijisho.setText("�K��Ō�w����");
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//        sijisho.setText("�K��Ō�w�����E���ʖK��Ō�w����");
        sijisho.setText("�K��Ō�w�����E���ʖK��Ō�w�����E���_�ȖK��Ō�w�����E���_�ȓ��ʖK��Ō�w����");
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        ikenshoDetail
                .setBackground(IkenshoConstants.COLOR_BUTTON_YELLOW_FOREGROUND);
        ikenshoNew
                .setBackground(IkenshoConstants.COLOR_BUTTON_ORANGE_FOREGROUND);
        ikenshoDelete
                .setBackground(IkenshoConstants.COLOR_BUTTON_GREEN_FOREGROUND);
        sijishoDetail
                .setBackground(IkenshoConstants.COLOR_BUTTON_YELLOW_FOREGROUND);
        sijishoNew
                .setBackground(IkenshoConstants.COLOR_BUTTON_ORANGE_FOREGROUND);
        sijishoDelete
                .setBackground(IkenshoConstants.COLOR_BUTTON_GREEN_FOREGROUND);
        idIndomation
                .setForeground(IkenshoConstants.COLOR_BORDER_TEXT_FOREGROUND);
        tel.setBindPath("TEL1", "TEL2");
        sex.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "�j", "��" }))));
        name.setIMEMode(InputSubset.KANJI);
        kana.setIMEMode(InputSubset.KANJI);
        address.setIMEMode(InputSubset.KANJI);

    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    /**
     * ���݂̏������[�h��Ԃ��܂��B
     * 
     * @return �������[�h
     */
    private String getNowMode() {
        return nowMode;
    }

    /**
     * ���݂̏������[�h��ݒ肵�܂��B
     * 
     * @param nowMode �������[�h
     */
    private void setNowMode(String nowMode) {
        this.nowMode = nowMode;
    }

    /**
     * ���Ҕԍ���Ԃ��܂��B
     * 
     * @return ���Ҕԍ�
     */
    private String getPatientNo() {
        return patientNo;
    }

    /**
     * ���Ҕԍ���ݒ肵�܂��B
     * 
     * @param patientNo ���Ҕԍ�
     */
    private void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    /**
     * �ꗗ�ɕ\������`�F�b�N�{�b�N�X���擾���܂��B
     * 
     * @return �u�ꗗ�ɕ\������v�`�F�b�N�{�b�N�X
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACIntegerCheckBox getShowCheck() {
        if(showCheck == null){
            showCheck = new ACIntegerCheckBox();
            showCheck.setText("�ꗗ�ɕ\������");
            showCheck.setBindPath("SHOW_FLAG");
        }
        return showCheck;
    }

    /**
     * �ꗗ�ɕ\������R���e�i���擾���܂��B
     * 
     * @return �u�ꗗ�ɕ\������v�R���e�i
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getShowContainer() {
        if(showContainer == null){
            showContainer = new ACLabelContainer();
            showContainer.add(getShowCheck());
        }
        return showContainer;
    }

}
