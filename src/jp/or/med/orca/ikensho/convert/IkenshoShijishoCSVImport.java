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
            execute.setText("実行(E)");
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
     * データ移行を実行します。
     */
    protected void executeImport() {
        // データ移行を実行可能かチェックします。
        File dir = new File(getSourceDirectory().getText());
        if ((dir == null) || (!dir.isDirectory())) {
            ACMessageBox.showExclamation("「移行元CSVファイルの場所」が不正です。"
                    + ACConstants.LINE_SEPARATOR + "「移行元CSVファイルの場所」を指定してください。");
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
                    // 利用者情報：PATIENT
                    if (shijishoFile != null) {
                        repeated = true;
                    } else {
                        shijishoFile = f;
                    }
                }
            }
        }
        if (repeated) {
            ACMessageBox.showExclamation("2組以上の移行元データが存在します。"
                    + ACConstants.LINE_SEPARATOR
                    + "「移行元CSVファイルの場所」のファイルをいったんすべて削除し、再度移行元データを出力してください。");
            return;
        }

        // 移行するデータを一覧化
        boolean fileExists = false;
        StringBuffer sb = new StringBuffer();
        if (shijishoFile != null) {
            sb.append("　・訪問看護指示書");
            sb.append(ACConstants.LINE_SEPARATOR);
            fileExists = true;
        }

        if (!fileExists) {
            ACMessageBox.showExclamation("移行元のCSVファイルが存在しません。"
                    + ACConstants.LINE_SEPARATOR
                    + "「移行元CSVファイルの場所」のファイルをいったんすべて削除し、再度移行元データを出力してください。");
            return;
        }

        dbm = null;
        try {
            dbm = new BridgeFirebirdDBManager();
            if (!dbm.isAvailableDBConnection()) {
                throw new Exception("データベースへ接続できません。");
            }
        } catch (Exception ex) {
            VRLogger.info(ex.getStackTrace());
            ACMessageBox.showExclamation("データベースへ接続できません。"
                    + ACConstants.LINE_SEPARATOR
                    + "医見書本体を起動し、データベースの設定を行ってください。");
            return;
        }

        if (ACMessageBox.showOkCancel("以下のデータ移行を開始します。よろしいですか？"
                + ACConstants.LINE_SEPARATOR + ACConstants.LINE_SEPARATOR
                + sb.toString() + ACConstants.LINE_SEPARATOR
                + "【※注意※】データ移行処理は途中でキャンセルできません。" + ACConstants.LINE_SEPARATOR
                + "　　　　　　必ず事前にバックアップを取ってください。", ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
            return;
        }

        errorCount = 0;

        getExecute().setEnabled(false);
        new Thread(new Runnable() {
            public void run() {
                try {
                    VRCSVFile f = new VRCSVFile("dummy", VRCSVFile.SJIS);
                    // 訪問看護指示書
                    if (shijishoFile != null) {
                        f.setPath(shijishoFile.getAbsolutePath());
                        f.clear();
                        f.read(false);
                        addErrorCount(importHomonResult(dbm, f));
                    }

                    if (errorCount > 0) {
                        ACMessageBox.show("データ移行を完了しました。"
                                + ACConstants.LINE_SEPARATOR + " " + errorCount
                                + " 件の移行に失敗しています。" + ACConstants.LINE_SEPARATOR
                                + "詳細はlogsフォルダ配下のログファイルを参照してください。");
                    } else {
                        ACMessageBox.show("データ移行を完了しました。");
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
     * 訪問看護報告書を移行します。
     * 
     * @param dbm DBマネージャ
     * @param f CSVファイル
     * @return エラー総数
     */
    protected int importHomonResult(ACDBManager dbm, VRCSVFile f) {
        setProgressTitle("　訪問看護指示書の移行中..");
        setProgress("[患者情報の取得]");
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
            // 患者一覧をハッシュ化
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
            // 重複キーを除外
            it = removeKeys.iterator();
            while (it.hasNext()) {
                patientMap.remove(it.next());
            }
        } catch (Exception ex) {
            VRLogger.info("患者情報の取得失敗");
            error += f.getRowCount();
            return error;
        }

        // 意見書指示書共通テーブルのインサート文
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

        // 共通テーブルの最大枝番取得
        sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" MAX(EDA_NO) AS NEXT_NO");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS");
        sb.append(" WHERE ");
        sb.append("(DOC_KBN = 2)");
        sb.append("AND(PATIENT_NO = ");
        String maxEdaHeader = sb.toString();

        // 指示書独自テーブルのインサート文
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
        setProgressTitle("　訪問看指示書の移行中..[" + end + "件中/");
        for (int i = 0; i < end; i++) {
            List list = f.getRow(i);
            try {
                setProgress(i + "件目] 失敗=" + error + "件");
                if (list.size() > 111) {
                    String birthDay = DATE_FORMAT.format(list.get(6));
                    String patientName = STRING_FORMAT.format(list.get(3));
                    Object patientNo = patientMap.get(birthDay + patientName);
                    if (patientNo == null) {
                        // 対象者なし
                        continue;
                    }
                    patientNo = INTEGER_FORMAT.format(patientNo);
                    dbm.beginTransaction();
                    sb = new StringBuffer(commonHeader);
                    // 1 患者番号 1からはじまる患者ごとの連番。 整数
                    sb.append(patientNo);
                    // 2 枝番号 同一患者における1からはじまる作成履歴番号。最初に作成されたものを1とする。 整数
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
                    // 書類区分
                    sb.append(",2");
                    // 4 患者名 文字列 15
                    sb.append(",");
                    sb.append(cutLength(patientName, 15));
                    // 5 患者名かな 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 4, 30));
                    // 6 性別 0：未選択、1：男、2：女。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(5)));
                    // 7 生年月日 yyyy-MM-dd 形式。 日付
                    sb.append(",");
                    sb.append(DATE_FORMAT.format(list.get(6)));
                    // 8 年齢 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 7, 5));
                    // 9 郵便番号 XXX-XXXX 形式。 文字列 8
                    sb.append(",");
                    sb.append(cutLength(list, 8, 8));
                    // 10 住所 文字列 45
                    sb.append(",");
                    sb.append(cutLength(list, 9, 45));
                    // 11 電話局番 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 10, 5));
                    // 12 電話 XXXX-XXXX 形式。 文字列 9
                    sb.append(",");
                    sb.append(cutLength(list, 11, 9));
                    // 13 診断名１ 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 12, 30));
                    // 14 診断名２ 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 13, 30));
                    // 15 診断名３ 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 14, 30));
                    // 16 発症年月日１ ggge年MM月dd日 形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 15, 16));
                    // 17 発症年月日２ ggge年MM月dd日 形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 16, 16));
                    // 18 発症年月日３ ggge年MM月dd日 形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 17, 16));
                    // 19 病状治療状態 5行以内。 文字列 250
                    sb.append(",");
                    sb.append(cutLength(list, 18, 250));
                    //病状治療状態他
                    sb.append(",NULL");
                    // 20 薬剤名１ 文字列 12
                    sb.append(",");
                    sb.append(cutLength(list, 19, 12));
                    // 21 用量１ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 20, 4));
                    // 22 単位１ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 21, 4));
                    // 23 用法１ 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 22, 10));
                    // 24 薬剤名２ 文字列 12
                    sb.append(",");
                    sb.append(cutLength(list, 23, 12));
                    // 25 用量２ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 24, 4));
                    // 26 単位２ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 25, 4));
                    // 27 用法２ 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 26, 10));
                    // 28 薬剤名３ 文字列 12
                    sb.append(",");
                    sb.append(cutLength(list, 27, 12));
                    // 29 用量３ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 28, 4));
                    // 30 単位３ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 29, 4));
                    // 31 用法３ 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 30, 10));
                    // 32 薬剤名４ 文字列 12
                    sb.append(",");
                    sb.append(cutLength(list, 31, 12));
                    // 33 用量４ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 32, 4));
                    // 34 単位４ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 33, 4));
                    // 35 用法４ 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 34, 10));
                    // 36 薬剤名５ 文字列 12
                    sb.append(",");
                    sb.append(cutLength(list, 35, 12));
                    // 37 用量５ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 36, 4));
                    // 38 単位５ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 37, 4));
                    // 39 用法５ 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 38, 10));
                    // 40 薬剤名６ 文字列 12
                    sb.append(",");
                    sb.append(cutLength(list, 39, 12));
                    // 41 用量６ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 40, 4));
                    // 42 単位６ 文字列 4
                    sb.append(",");
                    sb.append(cutLength(list, 41, 4));
                    // 43 用法６ 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 42, 10));
                    // 44 障害高齢者の日常生活自立度（寝たきり度）
                    // 0：未選択、1：自立、2：J1、3：J2、4：A1、5：A2、6：B1、7：B2、8：C1、9：C2。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(43)));
                    // 45 認知症高齢者の日常生活自立度
                    // 0：未選択、1：自立、2：I、3：IIa、4：IIb、5：IIIa、6：IIIb、7：IV、8：M。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(44)));
                    //TODO 症状安定性
                    sb.append(",");
                    sb.append('0');
                    //要介護状態予後
                    sb.append(",NULL");                    
                    // 46 点滴管理 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(45)));
                    // 47 輸液ポンプ 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(46)));
                    // 48 中心静脈栄養 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(47)));
                    // 49 透析 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(48)));
                    // 50 自動腹膜灌流装置 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(49)));
                    // 51 透析液供給装置 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(50)));
                    // 52 人工肛門 0：未選択、1：選択。ストーマのこと。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(51)));
                    // 53 人工膀胱 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(52)));
                    // 54 酸素療法 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(53)));
                    // 55 酸素療法量 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 54, 5));
                    // 56 人工呼吸器 0：未選択、1：選択。レスピレーターのこと。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(55)));
                    // 57 人工呼吸器方式 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 56, 5));
                    // 58 人工呼吸器設定 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 57, 10));
                    // 59 気管カニューレ 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(58)));
                    // 60 気管カニューレサイズ 文字列 5
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(59)));
                    // 61 吸引器 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(60)));
                    // 62 気管切開処置 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(61)));
                    // 63 経管栄養 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(62)));
                    // 64 経管栄養方法 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 63, 5));
                    // 65 経管栄養サイズ 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 64, 5));
                    // 66 経管栄養交換 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 65, 5));
                    // 67 カテーテル 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(66)));
                    // 68 留置カテーテル 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(67)));
                    // 69 留置カテーテルサイズ 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 68, 5));
                    // 70 留置カテーテル交換 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 69, 5));
                    // 71 ドレーン 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(70)));
                    // 72 ドレーン部位 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 71, 5));
                    // 73 モニター測定 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(72)));
                    // 74 疼痛看護 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(73)));
                    // 75 褥創処置 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(74)));
                    // 76 装着医療機器他 文字列 15
                    sb.append(",");
                    sb.append(cutLength(list, 75, 15));
                    // 77 医師名 文字列 15
                    sb.append(",");
                    sb.append(cutLength(list, 76, 15));
                    // 78 医療機関名 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 77, 30));
                    // 79 医療機関郵便番号 XXX-XXXX 形式。 文字列 10
                    sb.append(",");
                    sb.append(cutLength(list, 78, 10));
                    // 80 医療機関住所 文字列 45
                    sb.append(",");
                    sb.append(cutLength(list, 79, 45));
                    // 81 医療機関電話局番 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 80, 5));
                    // 82 医療機関電話 XXXX-XXXX 形式。 文字列 9
                    sb.append(",");
                    sb.append(cutLength(list, 81, 9));
                    // 83 医療機関ＦＡＸ局番 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 82, 5));
                    // 84 医療機関ＦＡＸ XXXX-XXXX 形式。 文字列 9
                    sb.append(",");
                    sb.append(cutLength(list, 83, 9));
                    // 85 携帯番号上 文字列 5
                    sb.append(",");
                    sb.append(cutLength(list, 84, 5));
                    // 86 携帯番号 XXXX-XXXX 形式。 文字列 9
                    sb.append(",");
                    sb.append(cutLength(list, 85, 9));
                    // 最終更新日 LAST_TIME
                    sb.append(",CURRENT_TIMESTAMP)");
                    dbm.executeUpdate(sb.toString());

                    // 共通テーブルの最大枝番を取得し指示書固有テーブルの枝番号とする。、
                    sb = new StringBuffer(maxEdaHeader);
                    sb.append(patientNo);
                    sb.append(")");
                    List edaNos = dbm.executeQuery(sb.toString());
                    if (edaNos.isEmpty()) {
                        throw new Exception("枝番号取得失敗");
                    }
                    String edaNo = INTEGER_FORMAT.format(((Map) edaNos.get(0))
                            .get("NEXT_NO"));

                    sb = new StringBuffer(originHeader);
                    // 患者番号
                    sb.append(patientNo);
                    // 枝番号
                    sb.append(",");
                    sb.append(edaNo);
                    // 87 記入日 yyyy-MM-dd 形式 日付
                    sb.append(",");
                    sb.append(DATE_FORMAT.format(list.get(86)));
                    // 指示書作成回数
                    sb.append(",0");
                    // 指示書有効期間
                    sb.append(",1");
                    // 指示書看護区分
                    sb.append(",2");
                    // 88 看護ステーション名 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 87, 30));
                    // 89 緊急時連絡先 文字列 40
                    sb.append(",");
                    sb.append(cutLength(list, 88, 40));
                    // 90 不在時対応法 文字列 40
                    sb.append(",");
                    sb.append(cutLength(list, 89, 40));
                    // 91 療養生活指導上留意事項 3行以内。 文字列 120
                    sb.append(",");
                    sb.append(cutLength(list, 90, 120));
                    // 92 リハビリテーション指示有無 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(91)));
                    // 93 リハビリテーション指示 3行以内。 文字列 120
                    sb.append(",");
                    sb.append(cutLength(list, 92, 120));
                    // 94 褥瘡処置指示有無 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(93)));
                    // 95 褥瘡処置指示 3行以内。 文字列 120
                    sb.append(",");
                    sb.append(cutLength(list, 94, 120));
                    // 96 装着医療機器指示有無 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(95)));
                    // 97 装着医療機器指示 3行以内。 文字列 120
                    sb.append(",");
                    sb.append(cutLength(list, 96, 120));
                    // 98 留意指示事項他有無 0：未選択、1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(97)));
                    // 99 留意指示事項他 3行以内。 文字列 120
                    sb.append(",");
                    sb.append(cutLength(list, 98, 120));
                    // 100 指示書特記事項 4行以内。 文字列 200
                    sb.append(",");
                    sb.append(cutLength(list, 99, 200));
                    // 101 新規作成日 yyyy-MM-dd hh:mm:ss形式 日時
                    sb.append(",");
                    sb.append(TIME_FORMAT.format(list.get(100)));
                    // 102 更新日 yyyy-MM-dd hh:mm:ss形式 日時
                    sb.append(",");
                    sb.append(TIME_FORMAT.format(list.get(101)));
                    // 103 指示期間From ggge年MM月dd日形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 102, 16));
                    // 104 指示期間To ggge年MM月dd日形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 103, 16));
                    // 105 要介護認定の状況
                    // 0：未選択、1：自立、11：要支援、21：要介護1、22：要介護2、23：要介護3、24：要介護4、25：要介護5
                    // 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(104)));
                    // 106 訪問看護指示書 指示書の種類として訪問看護指示書を指定するか。0：未選択、-1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(105)));
                    // 107 点滴注射指示書 指示書の種類として点滴注射指示書を指定するか。0：未選択、-1：選択。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(106)));
                    // 108 点滴注射指示期間From ggge年MM月dd日形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 107, 16));
                    // 109 点滴注射指示期間To ggge年MM月dd日形式。 文字列 16
                    sb.append(",");
                    sb.append(cutLength(list, 108, 16));
                    // 110 点滴注射指示 4行以内。 文字列 200
                    sb.append(",");
                    sb.append(cutLength(list, 109, 200));
                    // 111 他ステーション指示 0：未選択、1：無、2：有。 整数
                    sb.append(",");
                    sb.append(INTEGER_FORMAT.format(list.get(110)));
                    // 112 他看護ステーション名 文字列 30
                    sb.append(",");
                    sb.append(cutLength(list, 111, 30));
                    // 最終更新日 LAST_TIME
                    sb.append(",CURRENT_TIMESTAMP)");

                    dbm.executeUpdate(sb.toString());

                    dbm.commitTransaction();
                } else {
                    throw new Exception("不正なフィールド数");
                }
            } catch (Exception ex) {
                VRLogger.info("不正なCSVレコード 訪問看護指示書(" + (i + 1) + ") = "
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
     * 第1・第2フィールドを主キーとして、重複を省いたリストを返します。
     * <p>
     * 重複した場合、最後に見つかったレコードが優先されます。
     * </p>
     * 
     * @param f ファイル
     * @return 抽出結果
     */
    protected List filterRecord(VRCSVFile f) {
        // 主キーである利用者ID+作成日の重複をチェック。
        // 重複する場合、最後のデータが
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
     * エラー総数を加算します。
     * 
     * @param add 加算数
     */
    public void addErrorCount(int add) {
        errorCount += add;
    }

    /**
     * 指定文字数で切り詰めます。
     * 
     * @param src 検証文字
     * @param max 最大文字数
     * @return 切り詰め結果
     */
    protected String cutLength(List list, int index, int max) {
        // ''の文字数を補正
        return cutLength(STRING_FORMAT.format(list.get(index)), max);
    }

    /**
     * 指定文字数で切り詰めます。
     * 
     * @param src 検証文字
     * @param max 最大文字数
     * @return 切り詰め結果
     */
    protected String cutLength(String src, int max) {
        // ''の文字数を補正
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
     * 更新SQL文を発行します。
     * 
     * @param dbm DBマネージャ
     * @param sql SQL文
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
            exit.setText("終了(X)");
            exit.setMnemonic('X');
            exit.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    if (ACMessageBox.showOkCancel("終了します。よろしいですか？",
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
            dests.setText("データ移行先");
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
            dest.setText("IP： unknown" + ACConstants.LINE_SEPARATOR
                    + "データベースの場所： unknown");
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
            infomations.setText("データ移行に関するご注意");
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
                    .setText("【データ移行の前に】"
                            + ACConstants.LINE_SEPARATOR
                            + "　◇医見書本体を起動している場合、必ず終了させてからデータ移行を行ってください。"
                            + ACConstants.LINE_SEPARATOR
                            + "　◇データ移行先を変更する場合、医見書本体を起動してデータベースの設定を変更してください。"
                            + ACConstants.LINE_SEPARATOR
                            + "　◇移行する訪問看護指示書は、すべて新規に作成したものとして追加登録されます。既存のデータを削除することはありませんが、データ移行を2度以上実行すると、重複した訪問看護指示書ができる点にご注意ください。"
                            + ACConstants.LINE_SEPARATOR
                            + ACConstants.LINE_SEPARATOR
                            + "【移行できないデータについて】"
                            + ACConstants.LINE_SEPARATOR
                            + "　◇移行先の医見書システムに、氏名と生年月日の一致する患者が登録されていない訪問看護指示書は移行されません。"
                            + ACConstants.LINE_SEPARATOR
                            + "　◇移行先の医見書システムに、氏名と生年月日の同じ患者が重複登録されていた場合、その患者に関する訪問看護指示書は移行されません。"
                            + ACConstants.LINE_SEPARATOR
                            + ACConstants.LINE_SEPARATOR
                            + "【移行に失敗した場合】"
                            + ACConstants.LINE_SEPARATOR
                            + "　◇「データベースへ接続できません。」というエラーが表示された場合は、医見書本体を起動し、データを登録できるか確認してください。"

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
            sourceBrowse.setText("参照(B)..");
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
            sourceDirectorys.setText("移行元CSVファイルの場所");
            sourceDirectorys.add(getSourceDirectory(), null);
            sourceDirectorys.add(getSourceBrowse(), null);
        }
        return sourceDirectorys;
    }

    /**
     * します。
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
        this.setTitle("医見書システムVer.2.5　Macintosh用　訪問看護指示書データ移行ツール");
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

    final String INITIAL_MESSAGE = "　訪問看護指示書のCSV出力ツールを使って旧verから抽出したCSVファイルの保存先を指定し、実行ボタンを押下してください。";

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
        sb.append("IP： ");
        sb.append(ip);
        sb.append(ACConstants.LINE_SEPARATOR);
        sb.append("データベースの場所： ");
        sb.append(path);
        if (hasError) {
            sb.append(ACConstants.LINE_SEPARATOR);
            sb.append("※移行先のデータベース設定を取得できません。医見書本体を起動し、設定してください。");
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
