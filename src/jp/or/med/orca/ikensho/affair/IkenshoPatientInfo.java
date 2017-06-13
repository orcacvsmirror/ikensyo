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
            "◎", "");
    private ACSingleSelectFormat sijishoNewFormat = new ACSingleSelectFormat(
            "◎", "");
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
            // 画面遷移キャッシュデータがある場合は、パラメタを置き換える
            params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
            previewData = params;
        }

        if (VRBindPathParser.has("PATIENT_NO", params)) {
            // 患者番号あり
            setNowMode(IkenshoConstants.AFFAIR_MODE_UPDATE);
            setPatientNo(String.valueOf(VRBindPathParser.get("PATIENT_NO",
                    params)));
        } else {
            // 患者番号なし
            setNowMode(IkenshoConstants.AFFAIR_MODE_INSERT);

        }

        doSelect();

        if (previewData != null) {
            patientData.putAll(previewData);
            editor.bindSource();
        }

    }

    /**
     * 意見書情報を検索します。
     * 
     * @param dbm DBManager
     * @throws SQLException 処理例外
     */
    protected void doSelectIkensho(IkenshoFirebirdDBManager dbm)
            throws SQLException {
        // 意見書
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
     * 指示書情報を検索します。
     * 
     * @param dbm DBManager
     * @throws SQLException 処理例外
     */
    protected void doSelectSijisho(IkenshoFirebirdDBManager dbm)
            throws SQLException {
        // 指示書
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" SIS_ORIGIN.EDA_NO");
        sb.append(",SIS_ORIGIN.KINYU_DT");
        sb.append(",SIS_ORIGIN.CREATE_DT");
        sb.append(",SIS_ORIGIN.PATIENT_NO");
        sb.append(",SIS_ORIGIN.LAST_TIME");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sb.append(",SIS_ORIGIN.FORMAT_KBN");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
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
     * 選択処理を行います。
     * 
     * @throws Exception 処理例外
     */
    private void doSelect() throws Exception {
        clearReservedPassive();

        if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
            // removeInsertTrigger(update);
            addUpdateTrigger(update);
            update.setText("更新(S)");
            update.setToolTipText("現在の内容を更新します。");

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

            // 意見書
            doSelectIkensho(dbm);

            if (ikenshoTable.getRowCount() > 0) {
                ikenshoTable.setSelectedModelRow(0);
            }

            // 指示書
            doSelectSijisho(dbm);

            if (sijishoTable.getRowCount() > 0) {
                sijishoTable.setSelectedModelRow(0);
            }

            dbm.finalize();

            // 最新マークをつける
            checkNewDocumentMark();
        } else if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
            addInsertTrigger(update);
            update.setText("登録(S)");
            update.setToolTipText("現在の内容を登録します。");

            ikenshoArray = new VRArrayList();
            sijishoArray = new VRArrayList();
            patientArray = new VRArrayList();
            patientData = (VRMap) editor.createSource();
            
            // 2007/10/16 [Masahiko Higuchi] Addition - begin
            // 登録モードの場合はチェックをつけておく
            patientData.setData("SHOW_FLAG",new Integer(1));
            // 2007/10/16 [Masahiko Higuchi] Addition - end
            
            patientArray.addData(patientData);

            editor.setSource(patientData);
            editor.bindSource();
            setStatusText("患者最新基本情報");

            reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, ikenshoArray);
            reservedPassive(PASSIVE_CHECK_BILL_KEY, ikenshoArray);
            reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, sijishoArray);
        }
        // パッシブチェック予約
        reservedPassive(PASSIVE_CHECK_KEY, patientArray);

        checkDocumentButtonsEnabled();

        IkenshoSnapshot.getInstance().snapshot();

    }

    public boolean canBack(VRMap parameters) throws Exception {
        boolean result = true;
        if (IkenshoSnapshot.getInstance().isModified()) {
            if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getNowMode())) {
                // 追加モードの場合
                switch (ACMessageBox.showYesNoCancel("登録内容を保存しますか？",
                        "登録して戻る(S)", 'S', "破棄して戻る(R)", 'R')) {
                case ACMessageBox.RESULT_YES:

                    // 登録して戻る
                    result = doInsert();
                    break;
                case ACMessageBox.RESULT_CANCEL:
                    result = false;
                    break;
                }
            } else if (IkenshoConstants.AFFAIR_MODE_UPDATE.equals(getNowMode())) {
                // 更新モードの場合
                switch (ACMessageBox.showYesNoCancel("変更されています。保存しますか？",
                        "更新して戻る(S)", 'S', "破棄して戻る(R)", 'R')) {
                case ACMessageBox.RESULT_YES:

                    // 更新して戻る
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
            return ACMessageBox.show("更新された内容は破棄されます。\n終了してもよろしいですか？",
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
                message = "選択されている意見書は最新の情報です。\n削除すると最新患者情報は1つ前の版に戻ります。\n削除してもよろしいですか？";
            } else {
                message = "選択されている意見書を削除してもよろしいですか？";
            }
            if (ACMessageBox.show(message, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {

                IkenshoFirebirdDBManager dbm = null;
                try {
                    // パッシブチェック開始
                    clearPassiveTask();
                    addPassiveDeleteTask(PASSIVE_CHECK_IKENSHO_KEY, row);
                    addPassiveDeleteTask(PASSIVE_CHECK_BILL_KEY, row);

                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return;
                    }
                    // パッシブチェック終了

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
                message = "選択されている指示書は最新の情報です。\n削除すると最新患者情報は1つ前の版に戻ります。\n削除してもよろしいですか？";
            } else {
                message = "選択されている指示書を削除してもよろしいですか？";
            }
            if (ACMessageBox.show(message, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {

                IkenshoFirebirdDBManager dbm = null;
                try {
                    // パッシブチェック開始
                    clearPassiveTask();
                    addPassiveDeleteTask(PASSIVE_CHECK_SIJISHO_KEY, row);

                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return;
                    }
                    // パッシブチェック終了

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
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start 精神科訪問看護指示書の追加対応
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
     * 更新処理可能かを返します。
     * 
     * @return 更新処理可能か
     */
    private boolean canUpdate() {

        // エラーチェック
        switch (birth.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            birth.requestChildFocus();
            return false;
        default:
            ACMessageBox.showExclamation("日付に誤りがあります。");
            birth.requestChildFocus();
            birth.setParentColor(false);
            return false;
        }

        if (isNullText(name.getText())) {
            ACMessageBox.showExclamation("氏名を入力してください。");
            name.requestFocus();
            return false;
        }

        if (sex.getSelectedIndex() == sex.getNoSelectIndex()) {
            ACMessageBox.showExclamation("性別を選択してください。");
            sex.requestChildFocus();
            return false;
        }

        return true;

    }

    /**
     * 追加処理を行います。
     * 
     * @throws Exception 処理例外
     * @return boolean 追加処理に成功したか
     */
    private boolean doInsert() throws Exception {

        // エラーチェック
        if (!canUpdate()) {
            return false;
        }

        IkenshoFirebirdDBManager dbm = null;
        try {
            editor.applySource();
            patientData.setData("AGE", birth.getAge());

            // 同姓同名チェック
            if (IkenshoCommon.hasSameName(patientData)) {
                if (ACMessageBox.show("同一と思われる患者が既に登録されています。\n登録しますか？",
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
            // Addition 2007/10/02 [Masahiko Higuchi] begin 一覧に表示チェック追加
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
            // Addition 2007/10/02 [Masahiko Higuchi] begin 一覧に表示チェック追加
            sb.append(",");
            sb.append(getDBSafeNumberNullToZero("SHOW_FLAG",patientData));
            // Addition 2007/10/02 [Masahiko Higuchi] end
            sb.append(")");

            dbm = new IkenshoFirebirdDBManager();
            dbm.beginTransaction();
            dbm.executeUpdate(sb.toString());

            // 追加された患者番号を取得
            sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" GEN_ID(GEN_PATIENT,0)");
            sb.append(" FROM");
            sb.append(" RDB$DATABASE");
            VRArrayList patientNoArray = (VRArrayList) dbm.executeQuery(sb
                    .toString());

            dbm.commitTransaction();
            dbm.finalize();

            ACMessageBox.show("登録されました。");

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
     * 次に作成する枝番に当たる、文書集合に含まれる最大の枝番号+1をマップに追加します。
     * 
     * @param array 文書集合
     * @param map 追加先
     * @throws ParseException 解析例外
     */
    protected void addNextEdaNo(VRArrayList array, VRMap map)
            throws ParseException {
        if (array.getDataSize() > 0) {
            map.setData("EDA_NO", new Integer(((Integer) VRBindPathParser.get(
                    "EDA_NO", (VRMap) array.getData())).intValue() + 1));
        }
    }

    /**
     * 復帰用パラメタマップを生成して返します。
     * 
     * @return 復帰用パラメタマップ
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

            //意見書区分を選択
            int createDocType;
            switch (ACMessageBox.showYesNoCancel("作成する意見書の種類を選択してください。",
                    "主治医意見書(S)", 'S', "医師意見書(I)", 'I')) {
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
                            //同一の意見書区分でCSV出力対象が存在すれば新規作成禁止
                            ACMessageBox.show("CSV出力対象の意見書があるので新規作成できません。");
                            return;
                        }
                    }
                }
            }

            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show("患者基本情報が更新されています。\n更新しますか？",
                        ACMessageBox.BUTTON_YESNOCANCEL,
                        ACMessageBox.ICON_QUESTION);
                if (result == ACMessageBox.RESULT_YES) {
                    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
                        // 挿入
                        if (!doInsert()) {
                            return;
                        }
                    } else if (IkenshoConstants.AFFAIR_MODE_UPDATE
                            .equals(nowMode)) {
                        // 更新
                        if (!doUpdate()) {
                            return;
                        }
                    }
                } else if (result == ACMessageBox.RESULT_NO) {
                    editor.applySource();
                } else {
                    // キャンセル扱い
                    return;
                }
            }

            int flag = IkenshoCommon.checkInsurerDoctorCheck();
            if ((flag & IkenshoCommon.CHECK_INSURER_NOTHING) > 0) {
                if (ACMessageBox.show("保険者が登録されていません。今登録しますか？",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(IkenshoHokenshaShousai.class
                                    .getName(), createPreviewData(), "保険者詳細"));
                    return;
                }
            }
            if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
                if (ACMessageBox.show("医療機関情報が登録されていません。今登録しますか？",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(
                                    IkenshoIryouKikanJouhouShousai.class
                                            .getName(), createPreviewData(),
                                    "医療機関情報詳細"));
                    return;
                }
            }

            VRMap param = new VRHashMap(patientData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);
            // 常に最新基本情報の状態を引き継がせる
            param.setData("OVERRIDE_PATIENT", patientData);
            addNextEdaNo(ikenshoArray, param);

            // switch (NCMessageBox.showYesNoCancel("作成する意見書の対応年度を選択してください。",
            // "平成17年度(7)", '7', "平成18年度(8)",
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
                int result = ACMessageBox.show("患者基本情報が更新されています。\n更新しますか？",
                        ACMessageBox.BUTTON_YESNOCANCEL,
                        ACMessageBox.ICON_QUESTION);
                if (result == ACMessageBox.RESULT_YES) {
                    if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(nowMode)) {
                        // 挿入
                        if (!doInsert()) {
                            return;
                        }
                    } else if (IkenshoConstants.AFFAIR_MODE_UPDATE
                            .equals(nowMode)) {
                        // 更新
                        if (!doUpdate()) {
                            return;
                        }
                    }
                } else if (result == ACMessageBox.RESULT_NO) {
                    editor.applySource();
                } else {
                    // キャンセル扱い
                    return;
                }
            }

            int flag = IkenshoCommon.checkInsurerDoctorCheck();
            if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
                if (ACMessageBox.show("医療機関情報が登録されていません。今登録しますか？",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(
                                    IkenshoIryouKikanJouhouShousai.class
                                            .getName(), createPreviewData(),
                                    "医療機関情報詳細"));
                    return;
                }
            }

            VRMap param = new VRHashMap(patientData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);
            // 常に最新基本情報の状態を引き継がせる
            param.setData("OVERRIDE_PATIENT", patientData);
            addNextEdaNo(sijishoArray, param);
            
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//            ACFrame.getInstance().next(
//                    new ACAffairInfo(
//                            IkenshoHoumonKangoShijisho.class.getName(), param,
//                            "訪問看護指示書"));
            ACAffairInfo affair = null;
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start 精神科訪問看護指示書の追加対応
//            switch (ACMessageBox.showYesNoCancel("作成する指示書の種類を選択してください。",
//                   "訪問看護指示書(H)", 'H', "特別訪問看護指示書(T)", 'T')) {
//            case ACMessageBox.RESULT_YES:
//                affair = new ACAffairInfo(
//                      IkenshoHoumonKangoShijisho.class.getName(), param,
//                      "訪問看護指示書");
//                break;
//            case ACMessageBox.RESULT_NO:
//                affair = new ACAffairInfo(
//                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param,
//                        "特別訪問看護指示書");
//                break;
            switch (ACMessageBox.showMessage(
                    "作成する指示書の種類を選択してください。",
                    "訪問看護指示書(H) ", 'H',
                    "特別訪問看護指示書(T)", 'T',
                    "精神科訪問看護指示書(I)", 'I',
                    "精神科特別訪問看護指示書(U)", 'U')) {
            case ACMessageBoxDialogPlus.RESULT_SELECT1:
                affair = new ACAffairInfo(
                        IkenshoHoumonKangoShijisho.class.getName(), param, "訪問看護指示書");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT2:
                affair = new ACAffairInfo(
                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param, "特別訪問看護指示書");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT3:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaHoumonKangoShijisho.class.getName(), param, "精神科訪問看護指示書");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT4:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaTokubetsuHoumonKangoShijisho.class.getName(), param, "精神科特別訪問看護指示書");
                break;
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
            default:
                return;
            }
            ACFrame.getInstance().next(affair);
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

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
                switch (ACMessageBox.show("患者基本情報が更新されています。\n更新しますか？",
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

            // 処理クラスの振り分け
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

            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//            ACAffairInfo affair = new ACAffairInfo(
//                    IkenshoHoumonKangoShijisho.class.getName(), param,
//                    "訪問看護指示書");
            // 処理クラスの振り分け
            VRMap rowData = (VRMap) sijishoArray.getData(row);
            ACAffairInfo affair = null;
            switch (ACCastUtilities.toInt(VRBindPathParser.get("FORMAT_KBN", rowData), 0)) {
            case 0:
                affair = new ACAffairInfo(
                      IkenshoHoumonKangoShijisho.class.getName(), param,
                      "訪問看護指示書");
                break;
            case 1:
                affair = new ACAffairInfo(
                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param,
                        "特別訪問看護指示書");
                break;
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
            case 2:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaHoumonKangoShijisho.class.getName(), param,
                        "精神科訪問看護指示書");
                break;
            case 3:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaTokubetsuHoumonKangoShijisho.class.getName(), param,
                        "精神科特別訪問看護指示書");
                break;
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
            default:
                return;
            }
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
            
            ACFrame.getInstance().next(affair);
        }

    }

    /**
     * 更新処理を行います。
     * 
     * @throws Exception 処理例外
     * @return boolean 更新処理に成功したか
     */
    private boolean doUpdate() throws Exception {
        // エラーチェック
        if (!canUpdate()) {
            return false;
        }

        IkenshoFirebirdDBManager dbm = null;
        try {
            editor.applySource();
            patientData.setData("AGE", birth.getAge());

            // パッシブチェック開始
            clearPassiveTask();
            addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);

            dbm = getPassiveCheckedDBManager();
            if (dbm == null) {
                ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                return false;
            }
            // パッシブチェック終了

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
            // Addition 2007/10/02 [Masahiko Higuchi] begin 一覧に表示チェック追加
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

            ACMessageBox.show("更新されました。");

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
     * テーブルの選択状況にもとづき、ボタンをEnabled制御する。
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
     * 最新文書にチェックをつけます。
     * 
     * @throws Exception 解析例外
     */
    private void checkNewDocumentMark() throws Exception {
        int useDoc = IkenshoCommon.getNewDocumentStatus(ikenshoArray, 0,
                "CREATE_DT", sijishoArray, 0, "CREATE_DT");

        switch (useDoc) {
        case IkenshoCommon.NEW_DOC_NONE:
            ikenshoNewFormat.setSelectedData(null);
            sijishoNewFormat.setSelectedData(null);

            setStatusText("書類新規作成には新規に作成・登録された書類のデータが反映されます。");
            break;
        case IkenshoCommon.NEW_DOC_IKENSHO:
            ikenshoNewFormat.setSelectedData(VRBindPathParser.get("EDA_NO",
                    (VRBindSource) ikenshoArray.getData()));
            sijishoNewFormat.setSelectedData(null);

            setStatusText("書類新規作成には「"+IkenshoFormatTypeFormat.getInstance().format(VRBindPathParser
                            .get("FORMAT_KBN", (VRBindSource) ikenshoArray
                                    .getData())) +"（"
                    + IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                            .get("KINYU_DT", (VRBindSource) ikenshoArray
                                    .getData())) + "記入）」のデータが反映されます。");
            break;
        case IkenshoCommon.NEW_DOC_SIJISHO:
            ikenshoNewFormat.setSelectedData(null);
            sijishoNewFormat.setSelectedData(VRBindPathParser.get("EDA_NO",
                    (VRBindSource) sijishoArray.getData()));

            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//            setStatusText("書類新規作成には「訪問看護指示書（"
//                    + IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
//                            .get("KINYU_DT", (VRBindSource) sijishoArray
//                                    .getData())) + "記入）」のデータが反映されます。");
            setStatusText("書類新規作成には「"+ShijishoFormatTypeFormat.getInstance().format(VRBindPathParser
                            .get("FORMAT_KBN", (VRBindSource) sijishoArray
                                    .getData())) +"（"
                    + IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                            .get("KINYU_DT", (VRBindSource) sijishoArray
                                    .getData())) + "記入）」のデータが反映されます。");
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
            break;
        }
        newDocumentStatus = useDoc;

        ikenshoTable.repaint();
        sijishoTable.repaint();
    }

    /**
     * コンストラクタです。
     */
    public IkenshoPatientInfo() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        ikenshoTable.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
//                new VRTableColumn(0, 30, "最新", SwingConstants.CENTER,
//                        ikenshoNewFormat),
//                new VRTableColumn(1, 50, "作成No.", SwingConstants.RIGHT),
//                new VRTableColumn(2, 100, "区分", IkenshoFormatTypeFormat.getInstance()),
//                new VRTableColumn(3, 120, "記入日",
                new VRTableColumn(0, 50, "最新", SwingConstants.CENTER,
                        ikenshoNewFormat),
                new VRTableColumn(1, 90, "作成No.", SwingConstants.RIGHT),
                new VRTableColumn(2, 160, "区分", IkenshoFormatTypeFormat.getInstance()),
                new VRTableColumn(3, 200, "記入日",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new VRTableColumn(4, 70, "請求", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_HAKKOU),
                new VRTableColumn(5, 70, "CSV出力", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_FD_OUTPUT), }));

//        sijishoTable.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
//                new VRTableColumn(0, 30, "最新", SwingConstants.CENTER,
//                        sijishoNewFormat),
//                // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//                new VRTableColumn(2, 120, "区分", ShijishoFormatTypeFormat.getInstance()),
//                // [ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//                new VRTableColumn(1, 320, "記入日",
//                        IkenshoConstants.FORMAT_ERA_YMD), }));
        sijishoTable.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
                new VRTableColumn(0, 50, "最新", SwingConstants.CENTER,
                        sijishoNewFormat),
                new VRTableColumn(2, 160, "区分", ShijishoFormatTypeFormat.getInstance()), 
                new VRTableColumn(1, 320, "記入日",
                        IkenshoConstants.FORMAT_ERA_YMD), }));

        ikenshoTableModel = new ACTableModelAdapter(new VRArrayList(),
                new String[] { "EDA_NO", "EDA_NO", "FORMAT_KBN", "KINYU_DT", "HAKKOU_KBN",
                        "FD_OUTPUT_KBN" });
        ikenshoTable.setModel(ikenshoTableModel);

        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        sijishoTableModel = new ACTableModelAdapter(new VRArrayList(),
//                new String[] { "EDA_NO", "KINYU_DT", });
        sijishoTableModel = new ACTableModelAdapter(new VRArrayList(),
                new String[] { "EDA_NO", "KINYU_DT", "FORMAT_KBN" });
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        sijishoTable.setModel(sijishoTableModel);

    }

    /**
     * コンポーネントを初期化します。
     * 
     * @throws Exception 初期化例外
     */
    private void jbInit() throws Exception {
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);
        update.setMnemonic('S');
//        update.setText("更新(S)");
        update.setText("　更新(S)　");
        buttons.setTitle("患者最新基本情報");
        sijishoDetail.setMnemonic('J');
        sijishoDetail.setText("編集(J)");
        sijisho.setForeground(IkenshoConstants.COLOR_BORDER_TEXT_FOREGROUND);
        ikenshoDetail.setMnemonic('K');
        ikenshoDetail.setText("編集(K)");
        names.setText("氏名");
        sexs.setText("性別");
        kanas.setText("ふりがな");
        births.setText("生年月日");
        zips.setText("郵便番号");
        addresss.setText("住所");
        tels.setText("連絡先（TEL）");
        ids.setText("患者ID");
        id.setColumns(20);
        name.setColumns(30);
        name.setBindPath("PATIENT_NM");
        name.setMaxLength(15);
        name.setKanaField(kana);
        ikenshoNew.setMnemonic('A');
        ikenshoNew.setText("新規作成(A)");
        sijishoNew.setMnemonic('B');
        sijishoNew.setText("新規作成(B)");
        ikenshoDelete.setMnemonic('D');
        ikenshoDelete.setText("削除(D)");
        sijishoDelete.setText("削除(E)");
        sijishoDelete.setMnemonic('E');
        sex.setToolTipText("");
        sex.setBindPath("SEX");
        sex.setClearButtonToolTipText("「性別」の全項目の選択を解除します。");
        sex.setUseClearButton(false);
        kana.setColumns(30);
        kana.setBindPath("PATIENT_KN");
        kana.setMaxLength(30);
        id.setBindPath("CHART_NO");
        id.setMaxLength(20);
        address.setColumns(50);
        address.setBindPath("ADDRESS");
        address.setMaxLength(50);
        idIndomation.setText("帳票印刷時の必須項目ではありません。");
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
        // フローレイアウトに設定するために削除
        //editor.add(names, VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/09/18 [Masahiko Higuchi] Delete - end
        // 2007/09/18 [Masahiko Higuchi] Addition - begin
        // フローレイアウト
        editor.add(names, VRLayout.FLOW_INSETLINE);
        // 一覧表示チェック：フロー改行レイアウト
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
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start 精神科訪問看護指示書の追加対応
//        document.add(ikensho, VRLayout.CLIENT);
        document.add(ikensho, VRLayout.NORTH);
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
        ikensho.setLayout(new BorderLayout());
        ikensho.add(ikenshoButtons, BorderLayout.NORTH);
        ikenshoButtons.add(ikenshoDetail, null);
        ikensho.add(ikenshoTable, BorderLayout.CENTER);
        ikensho.setText("主治医意見書・医師意見書");
        ikensho.setForeground(IkenshoConstants.COLOR_BORDER_TEXT_FOREGROUND);
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start 精神科訪問看護指示書の追加対応
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
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
        //sijisho.setText("訪問看護指示書");
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start 精神科訪問看護指示書の追加対応
//        sijisho.setText("訪問看護指示書・特別訪問看護指示書");
        sijisho.setText("訪問看護指示書・特別訪問看護指示書・精神科訪問看護指示書・精神科特別訪問看護指示書");
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
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
                .asList(new String[] { "男", "女" }))));
        name.setIMEMode(InputSubset.KANJI);
        kana.setIMEMode(InputSubset.KANJI);
        address.setIMEMode(InputSubset.KANJI);

    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    /**
     * 現在の処理モードを返します。
     * 
     * @return 処理モード
     */
    private String getNowMode() {
        return nowMode;
    }

    /**
     * 現在の処理モードを設定します。
     * 
     * @param nowMode 処理モード
     */
    private void setNowMode(String nowMode) {
        this.nowMode = nowMode;
    }

    /**
     * 患者番号を返します。
     * 
     * @return 患者番号
     */
    private String getPatientNo() {
        return patientNo;
    }

    /**
     * 患者番号を設定します。
     * 
     * @param patientNo 患者番号
     */
    private void setPatientNo(String patientNo) {
        this.patientNo = patientNo;
    }

    /**
     * 一覧に表示するチェックボックスを取得します。
     * 
     * @return 「一覧に表示する」チェックボックス
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACIntegerCheckBox getShowCheck() {
        if(showCheck == null){
            showCheck = new ACIntegerCheckBox();
            showCheck.setText("一覧に表示する");
            showCheck.setBindPath("SHOW_FLAG");
        }
        return showCheck;
    }

    /**
     * 一覧に表示するコンテナを取得します。
     * 
     * @return 「一覧に表示する」コンテナ
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
