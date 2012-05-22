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

// 2011/10 Shin.Fujihara コメントを精査
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
    // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
    private ACComboBox versionCombo = null;
    private ACLabelContainer versionContainer = null;
    // 初期表示時のレセプトソフトバージョンコンボの設定値
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
     * 1ページあたりの表示件数です。
     */
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
    // private final int PAGE_COUNT = 100;
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
    // 表示を500件に変更
    private final int PAGE_COUNT = 500;
    
    // 日レセからデータを取得したときの条件を退避(後にPTNUM取得に使用)
    private Map receiptSetting = new HashMap();
    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

    public void initAffair(ACAffairInfo affair) {
        setStatusText("日医標準レセプトソフト連携");
        String ip = "127.0.0.1";
        String dbsVer = "1.2.2";
        String port = "8013";
        String user = "ormaster";
        String pass = "ormaster";
        // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
        int receiptVersion = DEFAULT_RECEIPT_VERSION_INDEX;
        // 2007/11/26 [Masahiko Higuchi] add - end

        try {
            // 過去の通信設定を読み込む
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
            // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
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
        // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
        getVersionCombo().setSelectedIndex(receiptVersion);
        // 2007/11/26 [Masahiko Higuchi] add - end

        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        // 取得条件の初期値設定
        // 年齢は65～
        getAgeStartText().setText("65");
        // 重複除外チェックオン
        getDeduplicationCheck().setSelected(true);
        // 取り込み設定の初期設定
        getAllPageCheck().setSelected(true);

        // 退避用テーブルの初期化
        initReceiptAccessSpace();
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
    }

    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
    // 通信で取得したデータの退避領域確保
    // テーブルレイアウト変更に伴い、IkenshoReceiptSoftDBManagerで行っていたCREATE TABLE処理を
    // 業務起動時に行うよう修正
    private void initReceiptAccessSpace() {

        try {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sql = null;

            // RECEIPT_ACCESS_SPACEが存在するか、必要なフィールドが存在するか確認
            // 検索するフィールドは、
            // LOCAL_IP(旧バージョンでも存在)
            // HOSPNUM(今回のバージョンで追加)
            sql = new StringBuffer();
            sql.append("SELECT COUNT(*) AS F_EXIST FROM RDB$RELATION_FIELDS");
            sql.append(" WHERE");
            sql.append(" RDB$FIELD_NAME IN ('LOCAL_IP', 'HOSPNUM')");
            sql.append(" AND (RDB$RELATION_NAME = 'RECEIPT_ACCESS_SPACE')");

            VRList list = dbm.executeQuery(sql.toString());
            int count = ACCastUtilities.toInt(
                    ((VRMap) list.getData()).get("F_EXIST"), 0);

            switch (count) {
            // 検索結果0の場合は、そもそもテーブルが存在しない
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

            // 検索結果1の場合は、テーブルは存在するが、今回追加するHOSPNUM、CHECKEDのフィールドが存在しない
            case 1:

                // 列の追加実行
                dbm.executeUpdate("ALTER TABLE RECEIPT_ACCESS_SPACE ADD HOSPNUM INTEGER NOT NULL");
                dbm.executeUpdate("ALTER TABLE RECEIPT_ACCESS_SPACE ADD CHECKED INTEGER NOT NULL");
                dbm.commitTransaction();
                break;

            // 検索結果が2件帰ってきていれば、補正済のため、処理終了
            default:
                return;
            }

        } catch (Exception e) {
            ACMessageBox.showExclamation("退避領域の確保に失敗しました。");
            VRLogger.severe(e.getMessage());
        }

    }

    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

    /**
     * コンストラクタです。
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
//                new ACTableColumn(8, 22, "　",
//                        new IkenshoCheckBoxTableCellRenderer(),
//                        deleteCheckEditor),
//                new ACTableColumn(9, 32, "重複", SwingConstants.CENTER),
//                // 2011/10 Shin.Fujihara要望により、患者IDの表示を復活
//                new ACTableColumn(0, 60, "患者ID", SwingConstants.RIGHT),
//                new ACTableColumn(1, 100, "氏名"),
//                new ACTableColumn(2, 120, "ふりがな"),
//                new ACTableColumn(3, 32, "性別", SwingConstants.CENTER,
//                        IkenshoConstants.FORMAT_SEX),
//                new ACTableColumn(4, 32, "年齢", SwingConstants.RIGHT,
//                        IkenshoConstants.FORMAT_NOW_AGE),
//                new ACTableColumn(4, 110, "生年月日",
//                        IkenshoConstants.FORMAT_ERA_YMD),
//                new ACTableColumn(5, 63, "郵便番号"),
//                new ACTableColumn(6, 280, "住所"),
//                new ACTableColumn(7, 120, "連絡先（TEL）"), }));
//
//        patientsModelAdapter = new ACTableModelAdapter(importPatients,
//                new String[] { "CHART_NO", "PATIENT_NM", "PATIENT_KN", "SEX",
//                        "BIRTHDAY", "POST_CD", "ADDRESS", "TEL", "IMPORT_FLAG",
//                        "BATTING_FLAG" });
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        patients.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(0, 36, "No.", SwingConstants.RIGHT),
                new ACTableColumn(8, 22, "　",
                        new IkenshoCheckBoxTableCellRenderer(),
                        deleteCheckEditor),
                new ACTableColumn(9, 32, "重複", SwingConstants.CENTER),
                new ACTableColumn(1, 100, "氏名"),
                new ACTableColumn(2, 120, "ふりがな"),
                new ACTableColumn(3, 32, "性別", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_SEX),
                new ACTableColumn(4, 32, "年齢", SwingConstants.RIGHT,
                        IkenshoConstants.FORMAT_NOW_AGE),
                new ACTableColumn(4, 110, "生年月日",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new ACTableColumn(5, 63, "郵便番号"),
                new ACTableColumn(6, 280, "住所"),
                new ACTableColumn(7, 120, "連絡先（TEL）"), }));

        patientsModelAdapter = new ACTableModelAdapter(importPatients,
                new String[] { "SERIAL_ID", "PATIENT_NM", "PATIENT_KN", "SEX",
                        "BIRTHDAY", "POST_CD", "ADDRESS", "TEL", "IMPORT_FLAG",
                        "BATTING_FLAG" });
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        patients.setModel(patientsModelAdapter);

    }

    /**
     * ツールボタンの有効状態を設定します。
     * 
     * @throws ParseException
     */
    private void checkButtonsEnabled() throws ParseException {
        boolean enabled = getImportCheckedRows().size() > 0;
        patinetImport.setEnabled(enabled);
    }

    /**
     * 同姓同名の患者が存在するかをチェックします。
     * 
     * @param destPatients 比較
     * @throws Exception 処理例外
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
                        VRBindPathParser.set("BATTING_FLAG", destRow, "あり");
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
        //患者ＩＤ列追加に伴い、患者ＩＤ順に表示するよう変更
        sb.append(" ORDER BY");
        sb.append(" SERIAL_ID");
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
        VRList result = dbm.executeQuery(sb.toString());
        // 日レセ形式から意見書形式へ変換
        toIkenshoData(result);

        // 同姓同名チェック
        checkSameName(result);

        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        // チェック状態を復元
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
     * 最初の一覧ページを表示します。
     * 
     * @throws Exception 処理例外
     */
    protected void viewFirstPage() throws Exception {
        pageBegin = 1;
        readFromAccessSpace(1, PAGE_COUNT);
    }

    /**
     * 一時領域から患者を取得します。
     * 
     * @param begin 開始番号
     * @param count 件数
     * @throws Exception 処理例外
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
        //既存障害対応
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
     * レセプトソフトから授受したデータを意見書形式に翻訳します。
     * 
     * @param src 操作先
     * @throws ParseException 処理例外
     */
    protected void toIkenshoData(VRList src) throws ParseException {
        int size = src.size();
        if (size <= 0) {
            return;
        }

        boolean encode = false;
        String osName = System.getProperty("os.name");
        // Mac以外であれば文字コード変換を行う。
        if ((osName != null) && (osName.indexOf("Mac") < 0)) {
            encode = true;
        }

        VRMap checkRow = (VRMap) src.getData(0);
        if (VRBindPathParser.has("PTID", checkRow)) {
            // 患者ID
            for (int i = 0; i < size; i++) {
                VRMap row = (VRMap) src.getData(i);
                String val = getNotNullString("PTID", row);
                if (val.length() > 20) {
                    // 20文字制限
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
            // 氏名
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
            // かな氏名
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
            // 性別
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
            // 年齢
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
            // 郵便番号
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
            // 住所
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
            // 電話番号
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
     * 文字コード変換結果を返します。
     * 
     * @param data 変換元
     * @param encode 変換を実行するか
     * @return 変換結果
     */
    private String getORCADecodeString(String data, boolean encode) {
        String result = data;
        // 2006/02/11[Tozo Tanaka] : delete end
        // if (encode) {
        // try{
        // result = new String(data.getBytes("Shift_JIS"),"MS932");
        // } catch(Exception e){
        // VRLogger.warning("文字コードの変換に失敗しました。");
        // }
        // }
        // 2006/02/11[Tozo Tanaka] : delete end
        return result;
    }

    protected void findActionPerformed(ActionEvent e) throws Exception {
        ACSplashable splash = null;

        try {
            // 通信設定の保存
            String port = this.port.getText();
            String ip = hostName.getText();
            String user = userName.getText();
            String pass = password.getText();
            String dbsVer = dbsVersion.getText();
            String hospID = hospitalID.getText();
            // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
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
            // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
            ACFrame.getInstance()
                    .getPropertyXML()
                    .setForceValueAt("ReceiptAccess/ReceiptSoftVersion",
                            receiptVersion);
            // パスがnullでないか念のためチェックする
            if (pass != null) {
                // 空白がパスワードに設定されている場合
                if (pass.indexOf(" ") != -1 || pass.indexOf("　") != -1) {
                    ACMessageBox.showExclamation("入力されたパスワードに空白文字が含まれています。"
                            + ACConstants.LINE_SEPARATOR
                            + "空白文字を含んだパスワードは使用できません。");
                    // 処理終了
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
                        .showExclamation("接続先の設定が不正です。接続先ホストやポート番号を見直してください。");
                return;
            }

            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
            // 絞り込み条件を取得
            VRMap refiners = new VRHashMap();
            refiners.put("AGE_START", getAgeStartText().getText());
            refiners.put("AGE_END", getAgeEndText().getText());
            refiners.put("DEDUPLICATION", new Boolean(getDeduplicationCheck()
                    .isSelected()));
            // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

            // tbl_ptinf取得ストアドプロシージャ
            int count = 0;
            try {

                // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
                if (getVersionCombo().getSelectedIndex() != DEFAULT_RECEIPT_VERSION_INDEX) {
                    // 2007/11/26 [Masahiko Higuchi] add - end

                    String key = "all";
                    VRMap param = null;
                    if (usePatientIDFilter.isSelected()) {
                        if ("".equals(hospID)) {
                            ACMessageBox.showExclamation("医療機関IDが未入力です。"
                                    + ACConstants.LINE_SEPARATOR
                                    + "日医標準レセプトソフトの「システム管理マスタ」を参照するか、"
                                    + ACConstants.LINE_SEPARATOR
                                    + "サポートベンダにお問い合わせください。");
                            return;
                        }
                        String id = patientID.getText();
                        if ("".equals(id)) {
                            ACMessageBox.showExclamation("患者カナ氏名が未入力です。");
                            return;
                        }

                        param = new VRHashMap();
                        param.put("tbl_ptinf.HOSPID", hospID);
                        param.put("tbl_ptinf.KANANAME", id + "%");
                        key = "key3";
                    } else {
                        if (ACMessageBox.showOkCancel("すべての患者を検索します。よろしいですか？"
                                + ACConstants.LINE_SEPARATOR
                                + "※100件あたり5秒程度かかります。",
                                ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                            return;
                        }
                    }

                    // スプラッシュの準備
                    ACFrameEventProcesser processer = ACFrame.getInstance()
                            .getFrameEventProcesser();
                    if (processer instanceof IkenshoFrameEventProcesser) {
                        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit -
                        // begin
                        String osName = System.getProperty("os.name");
                        // Mac以外であればストップスプラッシュを使う
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
                            ((ACSplash) splash).showModaless("データ通信");
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

                    // 2007/11/26 [Masahiko Higuchi] edit - begin 日レセ連携対応 v3.0.4
                } else {
                    // 日レセ新バージョンの場合
                    if (ACMessageBox.showOkCancel(
                            "すべての患者を検索します。よろしいですか？"
                                    + ACConstants.LINE_SEPARATOR
                                    + "※100件あたり5秒程度かかります。",
                            ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return;
                    }
                    // スプラッシュの準備
                    ACFrameEventProcesser processer = ACFrame.getInstance()
                            .getFrameEventProcesser();
                    if (processer instanceof IkenshoFrameEventProcesser) {
                        // 2011/10 [MantisID:0000655] [Masahiko.Higuchi] edit -
                        // begin
                        String osName = System.getProperty("os.name");
                        // Mac以外であればストップスプラッシュを使う
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
                            ((ACSplash) splash).showModaless("データ通信");
                        }

                    }
                    // HOSPNUM取得用の関数情報定義
                    String key = "key";
                    VRMap initialParam = new VRHashMap();
                    HashMap hospNumResult = new HashMap();
                    initialParam.put("tbl_sysuser.USERID", user);

                    // 通信準備
                    dbm.executeSetUp();

                    // 一旦HOSPNUMを取得する。
                    hospNumResult = dbm.executeQueryData("tbl_sysuser", key,
                            initialParam);

                    // COMMIT
                    dbm.commitTransaction();

                    // 患者情報取得用の関数情報定義
                    key = "all";
                    // 変数準備
                    VRMap findParam = new VRHashMap();
                    // HOSPNUMの取得
                    Integer hospNum = ACCastUtilities.toInteger(
                            hospNumResult.get("tbl_sysuser.HOSPNUM"), 0);
                    // 検索キー設定
                    findParam.put("tbl_ptinf.HOSPNUM", hospNum);
                    // 患者情報取得
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - begin
                    // count = dbm.executeQuery("tbl_ptinf", key, findParam,
                    // splash);
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Delete - end
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition -
                    // begin
                    count = dbm.executeQuery("tbl_ptinf", key, findParam,
                            splash, refiners);
                    
                    //日レセとの接続設定を退避しておく
                    receiptSetting.put("IP", ip);
                    receiptSetting.put("PORT", port);
                    receiptSetting.put("USER", user);
                    receiptSetting.put("PASS", pass);
                    receiptSetting.put("DBSVER", dbsVer);
                    receiptSetting.put("HOSPNUM", hospNum);
                    // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end
                    // 終了処理
                    dbm.close();

                }
                // 2007/11/26 [Masahiko Higuchi] edit - end
            } catch (Exception ex) {
                splash = closeSplash(splash);
                Throwable cause = ex.getCause();
                if (cause instanceof java.net.ConnectException) {
                    ACMessageBox.showExclamation("接続に失敗しました。");
                    return;
                } else if (cause instanceof UnknownHostException) {
                    ACMessageBox
                            .showExclamation("接続に失敗しました。接続先ホストの設定を見直してください。");
                    return;
                } else if (cause instanceof SocketException) {
                    ACMessageBox
                            .showExclamation("接続に失敗しました。通信を切断された可能性あります。再度試行してください。");
                    return;
                } else if (ex.getMessage().indexOf("invalid version") >= 0) {
                    ACMessageBox.showExclamation("DBSのバージョンが異なります。");
                    return;
                } else if (ex.getMessage().indexOf("authentication error") >= 0) {
                    ACMessageBox
                            .showExclamation("認証に失敗しました。ユーザー名およびパスワードが不正です。");
                    return;
                } else if (ex.getMessage().indexOf("other error.[-7]") >= 0) {
                    String id = patientID.getText();
                    if ((id.length() > 9) && (Long.parseLong(id) > 2147483647)) {
                        ACMessageBox
                                .showExclamation("2147483647を超える患者IDの受信に失敗しました。"
                                        + ACConstants.LINE_SEPARATOR
                                        + "接続先DBSのサポートセンターにご連絡ください。");
                        return;
                    }

                    // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
                } else if ("Connection reset".equals(ex.getMessage())) {
                    // 日レセのバージョン選択にミスの可能性有り
                    ACMessageBox.showExclamation("接続に失敗しました。"
                            + ACConstants.LINE_SEPARATOR
                            + "通信の切断、もしくは日医標準レセプトソフトのバージョンが異なる可能性があります。"
                            + ACConstants.LINE_SEPARATOR + "接続設定を確認してください。");
                    dbm.close();
                    return;
                }
                // 2007/11/26 [Masahiko Higuchi] add - end
                throw ex;
            }

            if (count <= 0) {
                splash = closeSplash(splash);
                ACMessageBox.show("患者情報が存在しません。");
                return;
            }

            setStatusText(count + "件取得しました。");
            foundCount = count;
            foundCountValue.setText(String.valueOf(count));

            // 最初のPAGE_COUNT件分表示
            viewFirstPage();
            splash = closeSplash(splash);

            ACMessageBox.show("患者情報を取得しました。");

            patinetImport.setEnabled(true);
        } finally {
            splash = closeSplash(splash);
        }
    }

    public boolean canClose() throws Exception {
        try {
            // 中間テーブルを破棄
            IkenshoReceiptSoftDBManager
                    .clearAccessSpace(new IkenshoFirebirdDBManager());
        } catch (Exception ex) {
        }
        return super.canClose();
    }

    /**
     * スプラッシュを消します。
     * 
     * @param splash 検査対象のスプラッシュ
     * @return 再代入すべきスプラッシュ
     */
    protected ACSplashable closeSplash(ACSplashable splash) {
        if (splash != null) {
            splash.close();
        }
        return null;
    }

    /**
     * 検索条件のチェックを行います。
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
                ACMessageBox.showExclamation("年齢の開始と終了の入力が逆転しています。");
                getAgeStartContainer().transferFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * 数字以外を除去した文字列を返します。
     * 
     * @param src 変換元
     * @return 変換結果
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
     * 指定バインドパスの値を文字列として返します。Nullならば空文字を返します。
     * 
     * @param path バインドパス
     * @param row 抽出元
     * @throws ParseException 処理例外
     * @return 変換結果
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
        // チェックボックスの編集状態の確定
        patients.stopCellEditing("IMPORT_FLAG");

        if (doInsert()) {
            if (patientsData != null) {
                checkSameName(patientsData);
                patients.invalidate();
                patients.repaint();
            }

            ACMessageBox.show("患者情報を取り込みました。");
        }
    }

    /**
     * 追加処理を行います。
     * 
     * @throws Exception 処理例外
     * @return boolean 追加処理に成功したか
     */
    private boolean doInsert() throws Exception {
        
        //2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - begin
        ACSplashable splash = ACFrame.getInstance().getFrameEventProcesser().createSplash("日レセデータ");
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
                // 2011/10/31[Shin Fujihara]要望により復活
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
                // 2011/10/31[Shin Fujihara]要望により復活
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
            // 500件目以降も取り込みを実行した場合は、データベース上のフラグも未チェックに変更する
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
     * 取り込みチェックのついた行データ集合を返します。
     * 
     * @throws ParseException 解析例外
     * @return ArrayList 行データ集合
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
        // 全件を対象とした取り込み
        if (getAllPageCheck().isSelected()) {
            try {
                // 一旦状態を保存
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

            // 表示しているデータを対象の取り込み(旧来通り)
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
        
        // 日レセ4.0.0未満との連携時はそのまま
        if (getVersionCombo().getSelectedIndex() != DEFAULT_RECEIPT_VERSION_INDEX) {
            return rows;
        }
        
        //日レセ4.0.0以上の場合は、カルテ番号を取得する
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
            VRLogger.warning("HOSPNUM取得時、日レセ端末との接続に失敗");
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
                
                // 通信準備
                receiptDbm.executeSetUp();
                Map sqlResult = receiptDbm.executeQueryData("tbl_ptnum", "key", findParam);
                // トランザクションの終了
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
            // 念のため明示的に初期化
            receiptDbm = null;
            
        } catch (Exception e) {
            VRLogger.warning(e);
        }
        
        // 2011/10 [MantisID:0000655] [Shin.Fujihara] Addition - end

        return rows;
    }

    /**
     * コンポーネントを初期化します。
     * 
     * @throws Exception 初期化例外
     */
    private void jbInit() throws Exception {
        accessSettings.setLayout(new VRLayout());
        hostNames.setText("接続先ホスト");
        hostName.setColumns(15);
        hostName.setMaxLength(255);
        hostName.setText("127.0.0.1");
        userName.setMaxLength(255);
        userName.setText("ormaster");
        userName.setColumns(15);
        userNames.setText("ユーザー名");
        port.setMaxLength(5);
        port.setText("8013");
        port.setColumns(5);
        ports.setText("ポート番号");
        port.setCharType(VRCharType.ONLY_NUMBER);
        password.setColumns(15);
        password.setMaxLength(255);
        password.setText("ormaster");
        passwords.setText("パスワード");

        accessSettings.setText("接続設定");
        // 2006/02/11[Tozo Tanaka] : replace begin
        // buttons.setText("日医標準レセプトソフト連係");
        buttons.setText("日医標準レセプトソフト連携");
        // 2006/02/11[Tozo Tanaka] : replace end
        receiptAccess.setIconPath(ACConstants.ICON_PATH_OPEN_24);
        receiptAccess.setMnemonic('A');
        receiptAccess.setText("通信(A)");
        receiptAccess.setToolTipText("受信した患者情報を取り込みます。");

        patinetImport.setIconPath(ACConstants.ICON_PATH_UPDATE_24);
        patinetImport.setEnabled(false);
        patinetImport.setMnemonic('I');
        patinetImport.setText("取り込み(I)");
        patinetImport.setToolTipText("日医標準レセプトソフトと通信を行ないます。");
        dbsVersion.setColumns(10);
        dbsVersion.setMaxLength(10);
        dbsVersion.setText("1.2.2");
        dbsVersions.setText("DBSバージョン");
        dbsVersions.add(dbsVersion, null);
        accessSettings.add(hostNames, VRLayout.FLOW_INSETLINE);
        accessSettings.add(ports, VRLayout.FLOW_INSETLINE);
        accessSettings.add(dbsVersions, VRLayout.FLOW_INSETLINE_RETURN);
        accessSettings.add(userNames, VRLayout.FLOW_INSETLINE);
        accessSettings.add(passwords, VRLayout.FLOW_INSETLINE_RETURN);
        // 2007/11/26 [Masahiko Higuchi] add - begin 日レセ連携対応 v3.0.4
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
        // 2011/09/27 [ID:0000655][Masahiko Higuchi] add - begin 日レセ連携仕様修正
        dbsVersions.setFollowChildEnabled(true);
        this.add(getFindSettings(), VRLayout.NORTH);
        // 2011/09/27 [ID:0000655][Masahiko Higuchi] add - end
        this.add(patients, VRLayout.CLIENT);

        // 2006/02/09[Tozo Tanaka] : add begin
        ((VRLayout) accessSettings.getLayout()).setAutoWrap(false);
        this.add(filterSettings, VRLayout.NORTH);
        // usePatientIDFilter.setText("IDで絞り込む");
        usePatientIDFilter.setText("カナ氏名で絞り込む");
        patientIDFilters.setLabelFilled(true);
        patientIDFilters.setBackground(patientIDFilters.getFocusBackground());
        patientIDFilters.add(usePatientIDFilter, VRLayout.FLOW);
        patientIDFilters.add(patientIDContainer, VRLayout.FLOW);
        patientIDFilters.add(hospitalIDContainer, VRLayout.FLOW);
        // 2006/02/11[Tozo Tanaka] : delete begin
        // 指定検索を不可能にする
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
        // Mac以外であれば横に並べる
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
        checkEditMethodContainer.setText("チェック");
        checkEditMethod.setEditable(false);
        checkEditMethod.setModel(new String[] { "すべて選択", "すべて除外", "選択反転",
                "重複を除外" });
        checkEditMethod.setSelectedIndex(0);
        checkEditMethod.setColumns(6);
        checkEdit.setText("実行");
        checkEditMethodContainer.add(checkEditMethod, VRLayout.FLOW);
        checkEditMethodContainer.add(checkEdit, VRLayout.FLOW);
        nextPage.setText("次へ");
        nextPage.setToolTipText("次の" + PAGE_COUNT + "件を表示します。");
        nextPage.setEnabled(false);
        previewPage.setText("前へ");
        previewPage.setToolTipText("前の" + PAGE_COUNT + "件を表示します。");
        previewPage.setEnabled(false);
        patientIDContainer.add(patientID, VRLayout.FLOW);
        patientIDContainer.setText("カナ氏名");
        // patientIDContainer.setText("患者ID");
        patientIDContainer.setEnabled(false);
        patientID.setColumns(10);
        patientID.setMaxLength(10);
        // 2006/02/11[Tozo Tanaka] : replace begin
        // patientID.setCharType(VRCharType.ONLY_ALNUM);
        patientID.setIMEMode(InputSubset.KANJI);
        // 2006/02/11[Tozo Tanaka] : replace end
        patientID.setEnabled(false);
        hospitalIDContainer.add(hospitalID, VRLayout.FLOW);
        hospitalIDContainer.setText("医療機関ID(JPN～など)");
        hospitalIDContainer.setEnabled(false);
        hospitalID.setColumns(15);
        hospitalID.setMaxLength(15);
        hospitalID.setCharType(VRCharType.ONLY_ALNUM);
        hospitalID.setEnabled(false);
        filterSettings.setText("取り込み設定");
        useKanaConvert.setText("カナ氏名をひらがなに変換");
        foundCountValue.setText("0");
        foundCountValue.setFormat(NumberFormat.getNumberInstance());
        foundCountUnit.setText(" 件中 ");
        viewIndexBegin.setText("0");
        viewIndexBegin.setFormat(NumberFormat.getNumberInstance());
        viewIndexBeginUnit.setText(" - ");
        viewIndexEnd.setText("0");
        viewIndexEnd.setFormat(NumberFormat.getNumberInstance());
        viewIndexEndUnit.setText(" 件目");
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
                // チェックボックスの編集状態の確定
                patients.stopCellEditing("IMPORT_FLAG");

                Iterator it = patientsData.iterator();
                switch (checkEditMethod.getSelectedIndex()) {
                case 0:// すべて選択
                    while (it.hasNext()) {
                        ((Map) it.next()).put("IMPORT_FLAG", Boolean.TRUE);
                    }
                    break;
                case 1:// すべて除外
                    while (it.hasNext()) {
                        ((Map) it.next()).put("IMPORT_FLAG", Boolean.FALSE);
                    }
                    break;
                case 2:// 選択反転
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
                case 3:// 重複除外
                    while (it.hasNext()) {
                        Map row = (Map) it.next();
                        if ("あり".equals(row.get("BATTING_FLAG"))) {
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
            // 中間テーブルを破棄
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
    // 画面上のチェック状態を保存
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
     * 日レセバージョン情報コンボを取得します。
     * 
     * @return コンボボックス
     * @since v3.0.4
     * @author Masahiko Higuchi
     */
    public ACComboBox getVersionCombo() {
        if (versionCombo == null) {
            versionCombo = new ACComboBox();
            versionCombo.setEditable(false);
            versionCombo.setModel(new String[] { "version 4.0.0未満",
                    "version 4.0.0以上" });
            versionCombo.setBlankable(false);
        }
        return versionCombo;
    }

    /**
     * 日医標準レセプトソフト情報ラベルコンテナを取得します。
     * 
     * @return ラベルコンテナ
     * @since v3.0.4
     * @author Masahiko Higuchi
     */
    public ACLabelContainer getVersionContainer() {
        if (versionContainer == null) {
            versionContainer = new ACLabelContainer();
            versionContainer.add(getVersionCombo());
            versionContainer.setText("日医標準レセプトソフトバージョン");
        }
        return versionContainer;
    }

    /**
     * 年齢 背面コンテナを取得します。
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
     * 年齢コンテナ 開始
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
            ageStartContainer.setText("年齢");
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
     * 年齢テキスト 開始を取得します
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
     * 年齢テキスト 終了を取得します
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
     * 歳ラベル
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACLabel getAgeLabel() {
        if (ageLabel == null) {
            ageLabel = new ACLabel();
            ageLabel.setText("歳");
        }
        return ageLabel;
    }

    /**
     * 年齢 区切り ラベル
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACLabel getAgeKaraLabel() {
        if (ageKaraLabel == null) {
            ageKaraLabel = new ACLabel();
            ageKaraLabel.setText("～");
        }
        return ageKaraLabel;
    }

    /**
     * 重複除外チェックを取得します
     * 
     * @author Masahiko.Higuchi
     * @since 3.1.5
     * @version 1.0 2011/10
     */
    protected ACCheckBox getDeduplicationCheck() {
        if (deduplicationCheck == null) {
            deduplicationCheck = new ACCheckBox();
            deduplicationCheck.setText("登録済みの患者を除外");
            deduplicationCheck.setBindPath("DEDUPLICATION");
        }
        return deduplicationCheck;
    }

    /**
     * 取り込み条件グループを取得します。
     * 
     * @return 取り込み条件グループ
     * @since 3.1.5
     * @author Masahiko.Higuchi
     */
    public ACGroupBox getFindSettings() {
        if (findSettings == null) {
            findSettings = new ACGroupBox();
            findSettings.setLayout(new VRLayout());
            findSettings.setHgap(0);
            findSettings.setVgap(0);
            findSettings.setText("取得条件");
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
            allPageCheck.setText(PAGE_COUNT + "件目以降の患者も取り込む");
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