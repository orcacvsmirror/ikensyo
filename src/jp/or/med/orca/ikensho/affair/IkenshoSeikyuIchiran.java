package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRCheckBox;
import jp.nichicom.vr.component.VRComboBox;
import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.table.IkenshoSeikyushoHakkouKubunTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoSeikyushoHakkouKubunReserveFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** <HEAD_IKENSYO> */
public class IkenshoSeikyuIchiran extends IkenshoAffairContainer implements
        ACAffairable {

    // 意見書ボタンバー
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    // 更新ボタン
    private ACAffairButton update = new ACAffairButton();
    // 検索ボタン
    private ACAffairButton find = new ACAffairButton();
    // 請求書印刷ボタン
    private ACAffairButton print = new ACAffairButton();
    // 一覧印刷ボタン
    private ACAffairButton printList = new ACAffairButton();
    // 検索条件、テーブル設定用パネル
    private VRPanel client = new VRPanel();
    // 結果表示用テーブル
    private ACTable table = new ACTable();

    // //検索条件・検索結果の変更ボタンパネル
    // private VRPanel searchPnl = new VRPanel();
    // 検索条件パネル
    private ACGroupBox searchGrp = new ACGroupBox();
    // 検索結果変更ボタンパネル
    private VRPanel searchButtonPnl = new VRPanel();

    // 保険者名称コンボボックス
    private VRComboBox hokenjyaCombo = new VRComboBox();
    private ACLabelContainer hokenjyaContainer = new ACLabelContainer();
    // 医師名称コンボボックス
    private VRComboBox doctorCombo = new VRComboBox();
    private ACLabelContainer doctorContainer = new ACLabelContainer();
    // 期間指定コンボボックス
    private VRComboBox taisyoDayCombo = new VRComboBox();
    private IkenshoEraDateTextField taisyoKikanFrom = new IkenshoEraDateTextField();
    private JLabel taisyoKikanFromLabel = new JLabel();
    private IkenshoEraDateTextField taisyoKikanTo = new IkenshoEraDateTextField();
    private JLabel taisyoKikanToLabel = new JLabel();
    private ACLabelContainer taisyoDayContainer = new ACLabelContainer();
    // 日付消去ボタン
    private ACButton clearDate = new ACButton();
    // 発行済みも含めるチェックボックス
    private VRCheckBox hakkozumiCheck = new VRCheckBox();

    // 未発行ボタン
    private ACButton mihakko = new ACButton();
    // 保留ボタン
    private ACButton horyu = new ACButton();
    // 発行済みボタン
    private ACButton hakkozumi = new ACButton();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem mihakkoMenu = new JMenuItem();
    private JMenuItem horyuMenu = new JMenuItem();
    private JMenuItem hakkozumiMenu = new JMenuItem();

    // テーブル表示用データオブジェクト
    private VRArrayList data;
    // 保険者リスト
    private VRArrayList hokenjyaList;
    // 医師名称リスト
    private VRArrayList doctorList;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO", "EDA_NO" }, new Format[] {
                    null, null }, "LAST_TIME", "LAST_TIME");

    private ACTableModelAdapter tableModel;
    private IkenshoSeikyushoHakkouKubunTableCellRenderer tableCellRenderer = new IkenshoSeikyushoHakkouKubunTableCellRenderer(
            "HAKKOU_KBN", "HAKKOU_KBN_ORIGIN");

    private static String separator = System.getProperty("file.separator");
    private static DecimalFormat dayFormat = new DecimalFormat("00");
    private static NumberFormat costFormat = NumberFormat.getInstance();

    // 意見書作成定数
    public static final int IKEN_PRINT = 1;
    // 指示書作成定数
    public static final int KENSA_PRINT = 2;
    // 意見書・指示書作成定数
    public static final int BOTH_PRINT = 3;

    /**
     * コンストラクタです。
     */
    public IkenshoSeikyuIchiran() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        clearDate
                .addActionListener(new IkenSeikyuIchiran_clearDate_actionAdapter(
                        this));
        hakkozumi
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        horyu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        mihakko
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        hakkozumiMenu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        horyuMenu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        mihakkoMenu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));

        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        // -----ボタン設定
        buttons.setTitle("請求対象意見書一覧");
        // 更新ボタン
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);
        update.setMnemonic('S');
        update.setText("更新(S)");
        update.setToolTipText("本画面で変更された印刷状況を保存します。");
        // 検索ボタン
        find.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_FIND);
        find.setMnemonic('V');
        find.setText("検索(V)");
        find.setToolTipText("現在入力されている条件により、一覧を表示します。");
        // 請求書ボタン
        print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_PRINT);
        print.setMnemonic('P');
        print.setText("請求書(P)");
        print.setToolTipText("リストに表示されている意見書について「請求書」を印刷します。");
        // 一覧印刷ボタン
        printList.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_TABLE_PRINT);
        printList.setMnemonic('L');
        printList.setText("一覧印刷(L)");
        printList.setToolTipText("請求対象意見書一覧表を印刷します。");

        buttons.add(printList, VRLayout.EAST);
        buttons.add(print, VRLayout.EAST);
        buttons.add(find, VRLayout.EAST);
        buttons.add(update, VRLayout.EAST);
        // ---ボタン設定

        // --表示領域設定
        client.setLayout(new VRLayout());
        client.add(searchGrp, VRLayout.NORTH);
        client.add(searchButtonPnl, VRLayout.NORTH);
        client.add(table, VRLayout.CLIENT);
        // --表示領域設定

        // ---検索条件設定
        searchGrp.setText("表示条件");
        VRLayout searchGrpLayout = new VRLayout();
        searchGrpLayout.setHgap(0);
        // searchGrpLayout.setFitHLast(true);
        searchGrp.setLayout(searchGrpLayout);
        // 保険者選択コンボボックス
        hokenjyaContainer.setText("依頼元（保険者）");
        hokenjyaCombo.setEditable(false);
        hokenjyaCombo.setPreferredSize(new Dimension(250, 20));
        hokenjyaCombo.setBindPath("hokenjya");
        hokenjyaContainer.add(hokenjyaCombo, null);
        // 医師氏名コンボボックス
        doctorContainer.setText("医師氏名");
        doctorCombo.setEditable(false);
        doctorCombo.setPreferredSize(new Dimension(200, 20));
        doctorCombo.setBindPath("doctroName");
        doctorContainer.add(doctorCombo, null);

        // 期間指定
        ACLabelContainer panel1 = new ACLabelContainer();
        VRLayout layout1 = new VRLayout();
        layout1.setHgap(0);
        layout1.setVgap(0);
        panel1.setLayout(layout1);
        panel1.setText("指定対象");
        taisyoDayCombo.setEditable(false);
        taisyoDayCombo.addItem("記入日");
        taisyoDayCombo.addItem("送付日");
        taisyoDayCombo.setBindPath("taisyoDay");
        panel1.add(taisyoDayCombo, null);

        ACLabelContainer panel2 = new ACLabelContainer();
        VRLayout layout2 = new VRLayout();
        layout2.setHgap(0);
        layout2.setVgap(0);
        panel2.setLayout(layout2);
        panel2.setText(" 開始日付 ");
        // panel2.add(taisyoKikanFromLabel, VRLayout.FLOW);
        taisyoKikanFrom.setAgeVisible(false);
        taisyoKikanFrom.setAllowedFutureDate(true);
        taisyoKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoKikanFrom.setEra("平成");
        taisyoKikanFrom.setBindPath("taisyoKikanFrom");
        panel2.add(taisyoKikanFrom, null);

        ACLabelContainer panel3 = new ACLabelContainer();
        VRLayout layout3 = new VRLayout();
        layout3.setHgap(0);
        layout3.setVgap(0);
        panel3.setLayout(layout3);
        panel3.setText("終了日付 ");
        taisyoKikanToLabel.setText(" ～ ");
        // panel3.add(taisyoKikanToLabel, VRLayout.FLOW);
        taisyoKikanTo.setAgeVisible(false);
        taisyoKikanTo.setAllowedFutureDate(true);
        taisyoKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoKikanTo.setEra("平成");
        taisyoKikanTo.setBindPath("taisyoKikanTo");
        panel3.add(taisyoKikanTo, null);

        VRLayout taisyoDayContainerLayout = new VRLayout();
        taisyoDayContainerLayout.setAutoWrap(false);
        taisyoDayContainerLayout.setHgap(0);
        taisyoDayContainerLayout.setVgap(0);
        taisyoDayContainerLayout.setVAlignment(VRLayout.CENTER);
        taisyoDayContainer.setLayout(taisyoDayContainerLayout);
        taisyoDayContainer.add(panel1, VRLayout.FLOW);
        taisyoDayContainer.add(panel2, VRLayout.FLOW);
        taisyoDayContainer.add(taisyoKikanToLabel, VRLayout.FLOW);
        taisyoDayContainer.add(panel3, VRLayout.FLOW);

        clearDate.setText("日付消去(E)");
        clearDate.setToolTipText("入力された日付を消去します。");
        clearDate.setMnemonic('E');
        // clearDate.setPreferredSize(new Dimension(110, 22));

        taisyoDayContainer
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        taisyoDayContainer
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        taisyoDayContainer.setContentAreaFilled(true);

        taisyoDayContainer.add(clearDate, VRLayout.FLOW);

        hakkozumiCheck.setText("請求書発行済みの「意見書」も表示する。");
        hakkozumiCheck.setBindPath("hakkozumiCheck");
        ACLabelContainer panelCheck = new ACLabelContainer();
        panelCheck.add(hakkozumiCheck, null);

        searchGrp.add(hokenjyaContainer, VRLayout.FLOW_INSETLINE);
        searchGrp.add(doctorContainer, VRLayout.FLOW_RETURN);
        searchGrp.add(taisyoDayContainer, VRLayout.FLOW_INSETLINE_RETURN);
        searchGrp.add(panelCheck, VRLayout.FLOW_INSETLINE_RETURN);
        // ---検索条件設定

        // ---発行状態変更ボタン
        searchButtonPnl.setLayout(new VRLayout());
        // 発行済ボタン
        hakkozumi.setMnemonic('T');
        hakkozumi.setText("●発行済(T)");
        hakkozumi.setToolTipText("選択された請求書の印刷状況を「発行済」にします。");
        searchButtonPnl.add(hakkozumi, VRLayout.EAST);
        // 保留ボタン
        horyu.setMnemonic('H');
        horyu.setText("保留(H)");
        horyu.setToolTipText("選択された請求書の印刷状況を「保留」にします。");
        searchButtonPnl.add(horyu, VRLayout.EAST);
        // 未発行ボタン
        mihakko.setMnemonic('F');
        mihakko.setText("未発行(F)");
        mihakko.setToolTipText("選択された請求書の印刷状況を「未発行」にします。");
        searchButtonPnl.add(mihakko, VRLayout.EAST);

        // メニュー
        mihakkoMenu.setToolTipText(mihakko.getToolTipText());
        mihakkoMenu.setMnemonic(mihakko.getMnemonic());
        mihakkoMenu.setText(mihakko.getText());
        horyuMenu.setToolTipText(horyu.getToolTipText());
        horyuMenu.setMnemonic(horyu.getMnemonic());
        horyuMenu.setText(horyu.getText());
        hakkozumiMenu.setToolTipText(hakkozumi.getToolTipText());
        hakkozumiMenu.setMnemonic(hakkozumi.getMnemonic());
        hakkozumiMenu.setText("発行済(T)");

        popup.add(mihakkoMenu);
        popup.add(horyuMenu);
        popup.add(hakkozumiMenu);
        table.setPopupMenu(popup);
        table.setPopupMenuEnabled(true);

        // ---発行状態変更ボタン
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // ---メニューバーのボタンに対応するトリガーの設定
        // 更新ボタンの関連付け
        addUpdateTrigger(update);
        // 検索ボタンの関連付け
        addFindTrigger(find);
        // 印刷ボタンの関連付け
        addPrintTrigger(print);
        // 一覧印刷ボタンの関連付け
        addPrintTableTrigger(printList);

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

        // ---コンボボックス内データの初期化
        // 保険者コンボボックス
        hokenjyaList = (VRArrayList) dbm
                .executeQuery("SELECT INSURER_NO,INSURER_NM FROM INSURER ORDER BY INSURER_NM");
        setComboModel(hokenjyaCombo, hokenjyaList, "INSURER_NM");
        if (hokenjyaCombo.getItemCount() == 1) {
            hokenjyaCombo.setSelectedIndex(0);
        }
        // 医師名コンボボックス
        doctorList = (VRArrayList) dbm
                .executeQuery("SELECT DISTINCT DR_NM FROM DOCTOR ORDER BY DR_NM");
        // 空白行を追加
        VRMap spaceMap = new VRHashMap();
        spaceMap.setData("DR_NM", "");
        doctorList.add(0, spaceMap);
        setComboModel(doctorCombo, doctorList, "DR_NM");

        data = new VRArrayList();
        tableModel = new ACTableModelAdapter(data, new String[] { "HAKKOU_KBN",
                "PATIENT_NM", "AGE", "INSURED_NO", "DR_NM", "REQ_DT",
                "KINYU_DT", "SEND_DT" });

        // テーブルの生成
        table.setModel(tableModel);

        // ColumnModelの生成
        table
                .setColumnModel(new VRTableColumnModel(
                        new ACTableColumn[] {
                                new ACTableColumn(0, 50, "発行済",
                                        SwingConstants.CENTER,
                                        IkenshoSeikyushoHakkouKubunReserveFormat
                                                .getInstance()),
                                new ACTableColumn(1, 180, "氏名",
                                        tableCellRenderer, null),
                                new ACTableColumn(2, 50, "年齢",
                                        SwingConstants.RIGHT,
                                        tableCellRenderer, null),
                                new ACTableColumn(3, 120, "被保険者番号",
                                        tableCellRenderer, null),
                                new ACTableColumn(4, 150, "医師氏名",
                                        tableCellRenderer, null),
                                new ACTableColumn(5, 120, "作成依頼日",
                                        IkenshoConstants.FORMAT_ERA_YMD,
                                        tableCellRenderer, null),
                                new ACTableColumn(6, 120, "意見書記入日",
                                        IkenshoConstants.FORMAT_ERA_YMD,
                                        tableCellRenderer, null),
                                new ACTableColumn(7, 120, "意見書送付日",
                                        IkenshoConstants.FORMAT_ERA_YMD,
                                        tableCellRenderer, null) }));

        TableColumnModel colMdl = table.getColumnModel();
        // VRTableColumnModel colMdl = table.getColumnModel();
        for (int i = 0; i < colMdl.getColumnCount(); i++) {
            colMdl.getColumn(i).setCellRenderer(null);
        }

        table.setDefaultRenderer(Object.class, tableCellRenderer);
        // table.getTable().setDefaultRenderer(Object.class, tableCellRenderer);

        setButtonsEnabled();
        // ステータスバー
        setStatusText("請求対象意見書一覧");

        // スナップショット対象設定
        IkenshoSnapshot.getInstance().setRootContainer(searchGrp);
    }

    /**
     * VRArrayListの任意の列の値をComboBoxに設定します。
     *
     * @param combo VRComboBox
     * @param tbl VRArrayList
     * @param field String
     */
    private void setComboModel(JComboBox combo, VRArrayList tbl, String field) {
        try {
            if (tbl.size() > 0) {
                combo.setModel(new ACComboBoxModelAdapter(
                        new VRHashMapArrayToConstKeyArrayAdapter(tbl, field)));
            }
        } catch (Exception e) {
            IkenshoCommon.showExceptionMessage(e);
        }
    }

    public boolean canBack(VRMap parameters) throws Exception {
        // 更新ボタンが押下不可であれば、処理を行わず戻る。
        if (!update.isEnabled()) {
            return true;
        }
        int result = ACMessageBox.showYesNoCancel("変更されています。保存しますか？",
                "更新して戻る(S)", 'S', "破棄して戻る(R)", 'R');

        // DLG処理
        // 保存して戻る選択時
        if (result == ACMessageBox.RESULT_YES) {
            return doUpdate(); // DB更新成功:true, 失敗:false
            // 保存しないで戻る選択時
        } else if (result == ACMessageBox.RESULT_NO) {
            return true;
            // キャンセル選択時
        } else {
            return false;
        }
    }

    public boolean canClose() throws Exception {
        if (update.isEnabled()) {
            return ACMessageBox.show("更新された内容は破棄されます。\n終了してもよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK;
        }
        return true;
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return table;
    }

    /**
     * 「検索」押下時のイベントです。
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void findActionPerformed(ActionEvent e) throws Exception {
        // 検索項目入力の妥当性チェック
        if (!isValidInput()) {
            return;
        }
        // 検索実行
        doSelect(true);
    }

    /**
     * 「更新」押下時のイベントです
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (ACMessageBox.show("更新します。よろしいですか？", ACMessageBox.BUTTON_OKCANCEL,
                ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK) == ACMessageBox.RESULT_CANCEL) {
            return;
        }
        // 更新
        if (doUpdate()) {
            // 再検索
            doSelect(true);
        }
    }

    /**
     * 一覧印刷押下時のイベントです。
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        // 検索条件が変更された場合、メッセージを表示し、印刷を中断する
        if (IkenshoSnapshot.getInstance().isModified()) {
            ACMessageBox.show("検索条件が変更されました。\n条件を元に戻すか、再度検索をして下さい。");
            return;
        }

        // データの変更チェック
        if (isChangeData()) {
            if (ACMessageBox.show("変更されています。保存してもよろしいですか",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_OK) {
                // 更新実行
                if (doUpdate()) {
                    // 再検索
                    doSelect(true);
                } else {
                    return;
                }
            } else {
                return;
            }
        }

        if (ACMessageBox.showOkCancel("請求対象意見書一覧の印刷",
                "一覧表を出力してもよろしいですか？\n（被保険者番号順に印刷されます）", "印刷(O)", 'O') != ACMessageBox.RESULT_OK) {
            return;
        }
        // 印刷実行
        doPrintList();
    }

    private void doPrintList() throws Exception {
        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        int endRow = data.getDataSize();
        // ページ数のカウント
        int page = (int) Math.ceil((double) endRow / 21);
        int row = 1;

        // 印刷データ開始設定宣言
        pd.beginPrintEdit();

        String path = ACFrame.getInstance().getExeFolderPath() + separator
                + "format" + separator + "SeikyuIkenshoIchiran.xml";
//        pd.addFormat("daicho", path);
        ACChotarouXMLUtilities.addFormat(pd, "daicho", "SeikyuIkenshoIchiran.xml");

        for (int i = 0; i < page; i++) {
            pd.beginPageEdit("daicho");
            // 各ページ毎の設定
            // 保険者
            IkenshoCommon.addString(pd, "hokensya", getSelectedCboData(
                    hokenjyaCombo, hokenjyaList, "INSURER_NM")
                    + "　("
                    + getSelectedCboData(hokenjyaCombo, hokenjyaList,
                            "INSURER_NO") + ")");
            // 出力分類
            IkenshoCommon.addString(pd, "dateRange.data.title", taisyoDayCombo
                    .getSelectedItem().toString()
                    + "：");
            // 日付期間開始
            if (isTaisyoKikanInput(taisyoKikanFrom)) {
                Date date = taisyoKikanFrom.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.fromY",
                        VRDateParser.format(date, "ggg　ee"));
                IkenshoCommon.addString(pd, "dateRange.data.fromM",
                        VRDateParser.format(date, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.fromD",
                        VRDateParser.format(date, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.fromY",
                // taisyoKikanFrom.getEra() + " " +
                // dayFormat.format(Long.parseLong(taisyoKikanFrom.getYear())));
                // IkenshoCommon.addString(pd, "dateRange.data.fromM",
                // dayFormat.format(Long.parseLong(taisyoKikanFrom.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.fromD",
                // dayFormat.format(Long.parseLong(taisyoKikanFrom.getDay())));
            }
            // 日付期間終了
            if (isTaisyoKikanInput(taisyoKikanTo)) {
                Date date = taisyoKikanTo.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.toY", VRDateParser
                        .format(date, "ggg　ee"));
                IkenshoCommon.addString(pd, "dateRange.data.toM", VRDateParser
                        .format(date, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.toD", VRDateParser
                        .format(date, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.toY",
                // taisyoKikanTo.getEra() + " " +
                // dayFormat.format(Long.parseLong(taisyoKikanTo.getYear())));
                // IkenshoCommon.addString(pd, "dateRange.data.toM",
                // dayFormat.format(Long.parseLong(taisyoKikanTo.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.toD",
                // dayFormat.format(Long.parseLong(taisyoKikanTo.getDay())));
            }

            for (int j = 0; (j < 21) && (row < endRow + 1); j++, row++) {
                VRMap map = (VRMap) data.getData(row - 1);
                // 請求書
                switch (Integer.parseInt(VRBindPathParser
                        .get("HAKKOU_KBN", map).toString())) {
                case 1:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "未出力");
                    break;
                case 2:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "出力済");
                    break;
                case 3:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "保留");
                    break;
                default:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "-");
                    break;
                }
                // 氏名
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".PATIENT_NM",
                        getString("PATIENT_NM", map));
                // ふりがな
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".PATIENT_KN",
                        getString("PATIENT_KN", map));
                // 性別
                switch (((Integer) VRBindPathParser.get("SEX", map)).intValue()) {
                case 1:
                    IkenshoCommon
                            .addString(pd, getHeader(j + 1) + ".SEX", "男性");
                    break;
                case 2:
                    IkenshoCommon
                            .addString(pd, getHeader(j + 1) + ".SEX", "女性");
                    break;
                }
                // 年齢
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".AGE",
                        getString("AGE", map));
                // 生年月日
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".BIRTHDAY",
                        IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                                .get("BIRTHDAY", map)));
                // 被保険者番号
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".INSURED_NO",
                        getString("INSURED_NO", map));
                // 医師名
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".DR_NM",
                        getString("DR_NM", map));

                Object irai = VRBindPathParser.get("REQ_DT", map);
                if (irai != null) {
                    IkenshoCommon.addString(pd, getHeader(j + 1) + ".REQ_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(irai));
                }
                // 記入日
                Object kinyu = VRBindPathParser.get("KINYU_DT", map);
                if (kinyu != null) {
                    IkenshoCommon.addString(pd, getHeader(j + 1) + ".KINYU_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(kinyu));
                }
                // 最新指示書記入日
                Object soufu = VRBindPathParser.get("SEND_DT", map);
                if (soufu != null) {
                    IkenshoCommon.addString(pd, getHeader(j + 1) + ".SEND_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(soufu));
                }
            }
            pd.endPageEdit();
        }

        pd.endPrintEdit();

        // writePDF(pd);
        // openPDF();
        openPDF(pd);

    }

    private static String getString(String key, VRMap map) throws Exception {
        Object obj = VRBindPathParser.get(key, map);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    private String getHeader(int row) {
        return "table.h" + Integer.toString(row);
    }

    private String getSelectedCboData(VRComboBox combo, VRArrayList ary,
            String key) {
        return ((VRMap) ary.getData(combo.getSelectedIndex())).getData(key)
                .toString();
    }

    /**
     * 請求書印刷実行
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void printActionPerformed(ActionEvent e) throws Exception {
        IkenshoSeikyuPrintSetting seikyuPrtSet = new IkenshoSeikyuPrintSetting();
        // 検索条件が変更された場合、メッセージを表示し、印刷を中断する
        if (IkenshoSnapshot.getInstance().isModified()) {
            ACMessageBox.show("検索条件が変更されました。\n条件を元に戻すか、再度検索をして下さい。");
            return;
        }
        // データの変更チェック
        if (isChangeData()) {
            if (ACMessageBox.show("変更されています。保存してもよろしいですか",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_OK) {
                // 更新実行
                if (doUpdate()) {
                    // 再検索
                    doSelect(true);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        seikyuPrtSet.setModal(true);
        seikyuPrtSet.setResizable(false);
        // 印刷設定ダイアログを表示
        if (seikyuPrtSet.showDialog(getSelectedCboData(hokenjyaCombo,
                hokenjyaList, "INSURER_NO")) == IkenshoSeikyuPrintSetting.BUTTON_CANCEL) {
            // キャンセルが押下された場合は、処理を停止
            return;
        }
        // データ変更の確認
        if (ACMessageBox.show("印刷を行うと、該当「意見書」の請求書は「発行済み」に変更されます。\nよろしいですか？",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
            // キャンセルが押下された場合は、処理を停止
            return;
        }
        // 印刷フラグの更新と、PDF作成処理(１トランザクション)
        if (!doSeikyuPrint(seikyuPrtSet)) {
            ACMessageBox.showExclamation("印刷に失敗しました。");
        }

    }

    /**
     * 請求書のの発行を行います。
     *
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @return boolean
     * @exception Exception
     */
    private boolean doSeikyuPrint(IkenshoSeikyuPrintSetting seikyuPrtSet)
            throws Exception {
        // 印刷用データ保持オブジェクト
        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        // 医療機関データ保持オブジェクト
        VRMap doctorData = null;
        // 請求データ
        VRArrayList seikyuData = new VRArrayList();
        // 金額データ
        HashMap costData = null;
        VRMap map = null;
        IkenshoFirebirdDBManager dbm = null;

        int out_mode = 0;
        int sokatu_select = 0;
        boolean sokatu_check = false;
        int meisai_select = 0;
        boolean meisai_check = false;

        try {

            // 条件のアップデート
            dbm = doUpdateAll();
            if (dbm == null) {
                return false;
            }
            // PDFファイルの作成
            // 医療機関取得
            doctorData = getDefaultDoctor(dbm, getSelectedCboData(
                    hokenjyaCombo, hokenjyaList, "INSURER_NO"));
            if (doctorData == null) {
                dbm.rollbackTransaction();
                return false;
            }

            // 請求書発行対象データを抽出
            for (int i = 0; i < data.getDataSize(); i++) {
                map = (VRMap) data.getData(i);
                if (map.getData("HAKKOU_KBN").toString().equals("1")) {
                    seikyuData.add(map);
                }
            }

            // 合計金額などを取得
            costData = getCostData(seikyuData);

            // 印刷開始宣言
            pd.beginPrintEdit();

            // 各定義体を設定
            String dir = ACFrame.getInstance().getExeFolderPath() + separator
                    + "format" + separator;
            ACChotarouXMLUtilities.addFormat(pd, "seikyu", "Soukatusho.xml");
            ACChotarouXMLUtilities.addFormat(pd, "ichiran", "SeikyuIchiran.xml");
            ACChotarouXMLUtilities.addFormat(pd, "ichiranTotal", "SeikyuIchiranTotal.xml");
            ACChotarouXMLUtilities.addFormat(pd, "syosai", "IkenshoMeisai.xml");
//            pd.addFormat("seikyu", dir + "Soukatusho.xml");
//            pd.addFormat("ichiran", dir + "SeikyuIchiran.xml");
//            pd.addFormat("ichiranTotal", dir + "SeikyuIchiranTotal.xml");
//            pd.addFormat("syosai", dir + "IkenshoMeisai.xml");

            out_mode = IKEN_PRINT;
            switch (seikyuPrtSet.getOutputPattern()) {
            case 1:
                out_mode = BOTH_PRINT;
            // no break;
            case 2:
            // no break;
            case 3:
                sokatu_select = seikyuPrtSet.createSummaryRadio
                        .getSelectedIndex();
                sokatu_check = seikyuPrtSet.createSummaryPrint.isSelected();
                meisai_select = seikyuPrtSet.createDetailsRadio
                        .getSelectedIndex();
                meisai_check = seikyuPrtSet.createDetailsPrint.isSelected();
                break;
            case 4:
                out_mode = KENSA_PRINT;
                sokatu_select = seikyuPrtSet.inspectionSummaryRadio
                        .getSelectedIndex();
                sokatu_check = seikyuPrtSet.inspectionSummaryPrint.isSelected();
                meisai_select = seikyuPrtSet.inspectionDetailsRadio
                        .getSelectedIndex();
                meisai_check = seikyuPrtSet.inspectionDetailsPrint.isSelected();
                break;
            }
            // 検査料出力設定で、検査の件数が0件であれば、処理を行わない。
            if ((seikyuPrtSet.getOutputPattern() != 4)
                    || (!costData.get("KENSA_COUNT").toString().equals("0"))) {
                // 総括
                if (sokatu_select == 1) {
                    setSokatuPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, out_mode, sokatu_check);
                }
                // 一覧
                if ((meisai_select & 2) == 2) {
                    setIchiranPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, out_mode, meisai_check);
                }
                // 明細
                if ((meisai_select & 1) == 1) {
                    setSyosaiPrtDataAll(pd, seikyuData, seikyuPrtSet, out_mode,
                            meisai_check);
                }
            }
            //
            if ((seikyuPrtSet.getOutputPattern() == 2)
                    && (!costData.get("KENSA_COUNT").toString().equals("0"))) {
                // 総括
                if (seikyuPrtSet.inspectionSummaryRadio.getSelectedIndex() == 1) {
                    setSokatuPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, KENSA_PRINT,
                            seikyuPrtSet.inspectionSummaryPrint.isSelected());
                }
                // 一覧
                if ((seikyuPrtSet.inspectionDetailsRadio.getSelectedIndex() & 2) == 2) {
                    setIchiranPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, KENSA_PRINT, meisai_check);
                }
                // 明細
                if ((seikyuPrtSet.inspectionDetailsRadio.getSelectedIndex() & 1) == 1) {
                    setSyosaiPrtDataAll(pd, seikyuData, seikyuPrtSet,
                            KENSA_PRINT, meisai_check);
                }
            }

            pd.endPrintEdit();

            if (!costData.get("KENSA_COUNT").toString().equals("0")
                    || (seikyuPrtSet.getOutputPattern() != 4)) {
                // writePDF(pd);
                // openPDF();
                openPDF(pd);
            }

            dbm.commitTransaction();
        } catch (Exception e) {
            dbm.rollbackTransaction();
            throw e;
        }

        if (costData.get("KENSA_COUNT").toString().equals("0")) {
            // 検査料のみの帳票が出力される可能性がある場合
            // 2:意見書(1枚) 検査料(1枚)
            // 4:検査料のみ
            if (seikyuPrtSet.getOutputPattern() == 2
                    || seikyuPrtSet.getOutputPattern() == 4) {
                ACMessageBox.show("診察・検査費用請求対象となる意見書がありませんでした。");
            }
        }

        doSelect(false);
        return true;
    }

    /**
     * 意見書作成料総括の印刷データを作成します。 <BR>
     *
     * @param pd PrintData
     * @param seikyuData VRArrayList
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @param doctorData VRHashMap
     * @param costData HashMap
     * @param printMode int
     * @param bankCheck boolean
     * @return boolean
     * @throws Exception
     */
    private boolean setSokatuPrtData(ACChotarouXMLWriter pd,
            VRArrayList seikyuData, IkenshoSeikyuPrintSetting seikyuPrtSet,
            VRMap doctorData, HashMap costData, int printMode,
            boolean bankCheck) throws Exception {

        // 印刷データ開始設定宣言
        pd.beginPageEdit("seikyu");
        // 期間開始
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeFrom)) {
            IkenshoCommon.addString(pd, "dateRange.h1.fromY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeFrom));
        }
        // 期間終了
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeTo)) {
            IkenshoCommon.addString(pd, "dateRange.h1.toY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeTo));
        }
        // 出力日
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDate)) {
            IkenshoCommon.addString(pd, "prtDate.h1.era",
                    seikyuPrtSet.billPrintDate.getEra());
            IkenshoCommon.addString(pd, "prtDate.h1.y",
                    getDayFormat(seikyuPrtSet.billPrintDate.getYear()));
            IkenshoCommon.addString(pd, "prtDate.h1.m",
                    getDayFormatMonth(seikyuPrtSet.billPrintDate));
            IkenshoCommon.addString(pd, "prtDate.h1.d",
                    getDayFormatDay(seikyuPrtSet.billPrintDate));
        }
        // 宛名
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCreateCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCreateCost.getText() + "　様");
        } else if (printMode == KENSA_PRINT) {
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCheckCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCheckCost.getText() + "　様");
        }

        // 医療機関住所
        IkenshoCommon.addString(pd, "hospAdd.address.w2", getString(
                "MI_ADDRESS", doctorData));
        // 医療機関名称
        IkenshoCommon.addString(pd, "hospAdd.name.w2", getString("MI_NM",
                doctorData));
        // 開設者氏名
        IkenshoCommon.addString(pd, "hospAdd.kaisetuname.w2", getString(
                "KAISETUSHA_NM", doctorData));
        // 電話
        StringBuffer strTemp = new StringBuffer();
        if (!getString("MI_TEL1", doctorData).equals("")) {
            strTemp.append(getString("MI_TEL1", doctorData));
            strTemp.append("-");
        }
        IkenshoCommon.addString(pd, "hospAdd.tel.w2", strTemp.toString()
                + getString("MI_TEL2", doctorData));
        // 保険医療機関コード
        IkenshoCommon.addString(pd, "hospCd.h2.w2", getString("JIGYOUSHA_NO",
                doctorData));

        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // 意見書作成件数
            IkenshoCommon.addString(pd, "ikenSeikyuTotal.h2.w1", Integer
                    .toString(seikyuData.getDataSize()));
            // 意見書請求金額
            IkenshoCommon.addString(pd, "ikenCost.h2.w1", costFormat
                    .format(costData.get("IKENSYO_COST_TOTAL")));
        }

        if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
            // 検査費用請求件数
            IkenshoCommon.addString(pd, "kensaSeikyuTotal.h2.w1", costFormat
                    .format(costData.get("KENSA_COUNT")));
            // 検査費用請求金額
            IkenshoCommon.addString(pd, "kensaCost.h2.w1", costFormat
                    .format(costData.get("KENSA_COST_TOTAL")));
        }

        // 消費税金額合計
        // 合計金額
        switch (printMode) {
        // 意見書のみ
        case IKEN_PRINT:
            IkenshoCommon.addString(pd, "tax.h2.w1", costFormat.format(costData
                    .get("IKENSYO_TAX_TOTAL")));
            IkenshoCommon.addString(pd, "seikyuTotal.h2.w1", costFormat
                    .format(costData.get("IKENSYO_TOTAL")));
            break;
        // 請求書のみ
        case KENSA_PRINT:
            IkenshoCommon.addString(pd, "tax.h2.w1", costFormat.format(costData
                    .get("KENSA_TAX_TOTAL")));
            IkenshoCommon.addString(pd, "seikyuTotal.h2.w1", costFormat
                    .format(costData.get("KENSA_TOTAL")));
            break;
        // 両方
        case BOTH_PRINT:
            IkenshoCommon.addString(pd, "tax.h2.w1", costFormat.format(costData
                    .get("TAX_TOTAL")));
            IkenshoCommon.addString(pd, "seikyuTotal.h2.w1", costFormat
                    .format(costData.get("TOTAL")));
            break;
        }

        // 振込先口座出力にチェックがある場合
        if (bankCheck) {
            // 金融機関名
            IkenshoCommon.addString(pd, "bank.h3.w2", getString("BANK_NM",
                    doctorData));
            // 支店名
            IkenshoCommon.addString(pd, "bank.h4.w2", getString(
                    "BANK_SITEN_NM", doctorData));
            // 口座種類
            IkenshoCommon.addString(pd, "bank.h5.w2", getKozaKind(getString(
                    "BANK_KOUZA_KIND", doctorData)));
            // 口座番号
            IkenshoCommon.addString(pd, "bank.h6.w2", getString(
                    "BANK_KOUZA_NO", doctorData));
            // 名義人
            IkenshoCommon.addString(pd, "bank.h7.w2", getString(
                    "FURIKOMI_MEIGI", doctorData));
            // 出力の指定がない場合は、表示を消す
        } else {
            // 振込先金融機関
            IkenshoCommon.addString(pd, "bank.h1.w1", "");
            // 金融機関名
            IkenshoCommon.addString(pd, "bank.h3.w1", "");
            // 支店名
            IkenshoCommon.addString(pd, "bank.h4.w1", "");
            // 口座種類
            IkenshoCommon.addString(pd, "bank.h5.w1", "");
            // 口座番号
            IkenshoCommon.addString(pd, "bank.h6.w1", "");
            // 名義人
            IkenshoCommon.addString(pd, "bank.h7.w1", "");
        }
        pd.endPageEdit();

        return true;
    }

    /**
     * 意見書作成料の一覧表の印刷データを作成します。 <BR>
     *
     * @param pd PrintData
     * @param seikyuData VRArrayList
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @param doctorData VRHashMap
     * @param costData HashMap
     * @param printMode int
     * @param bankCheck boolean
     * @return boolean
     * @throws Exception
     */
    private boolean setIchiranPrtData(ACChotarouXMLWriter pd,
            VRArrayList seikyuDataTemp, IkenshoSeikyuPrintSetting seikyuPrtSet,
            VRMap doctorData, HashMap costData, int printMode,
            boolean bankCheck) throws Exception {

        VRArrayList seikyuData = new VRArrayList();
        // 検査料のみ出力の場合
        if (printMode == KENSA_PRINT) {
            for (int i = 0; i < seikyuDataTemp.getDataSize(); i++) {
                VRMap map = (VRMap) seikyuDataTemp.getData(i);
                // 検査料が0点でなければ値を設定
                // if (getKensaTotal(map) != 0) {
                if (getKensaTotal(map).compareTo(new BigDecimal("0")) != 0) {
                    seikyuData.add(map);
                }
            }
            if (seikyuData.getDataSize() == 0) {
                return true;
            }

            // それ以外は引数の値をそのまま設定
        } else {
            seikyuData = seikyuDataTemp;
        }

        int endRow = seikyuData.getDataSize();
        // ページ数のカウント
        int page = (int) Math.ceil((double) endRow / 12);
        int row = 1;
        int pageRow = 0;

        for (int i = 0; i < page; i++) {

            // 印刷データ開始設定宣言
            pd.beginPageEdit("ichiran");
            // 期間開始
            if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeFrom)) {
                IkenshoCommon.addString(pd, "dateRange.h1.fromY",
                        getDayFormatYear(seikyuPrtSet.billPrintDateRangeFrom));
                IkenshoCommon.addString(pd, "dateRange.h1.fromM",
                        getDayFormatMonth(seikyuPrtSet.billPrintDateRangeFrom));
                IkenshoCommon.addString(pd, "dateRange.h1.fromD",
                        getDayFormatDay(seikyuPrtSet.billPrintDateRangeFrom));
            }
            // 期間終了
            if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeTo)) {
                IkenshoCommon.addString(pd, "dateRange.h1.toY",
                        getDayFormatYear(seikyuPrtSet.billPrintDateRangeTo));
                IkenshoCommon.addString(pd, "dateRange.h1.toM",
                        getDayFormatMonth(seikyuPrtSet.billPrintDateRangeTo));
                IkenshoCommon.addString(pd, "dateRange.h1.toD",
                        getDayFormatDay(seikyuPrtSet.billPrintDateRangeTo));
            }

            // 宛名
            if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
                // IkenshoCommon.addString(pd, "atena.h1.name",
                // seikyuPrtSet.toCreateCost.getText());
                IkenshoCommon.addString(pd, "atena.h1.name",
                        seikyuPrtSet.toCreateCost.getText() + "　様");
            } else if (printMode == KENSA_PRINT) {
                // IkenshoCommon.addString(pd, "atena.h1.name",
                // seikyuPrtSet.toCheckCost.getText());
                IkenshoCommon.addString(pd, "atena.h1.name",
                        seikyuPrtSet.toCheckCost.getText() + "　様");
            }

            for (pageRow = 0; (pageRow < 12) && (row < endRow + 1); pageRow++, row++) {
                VRMap map = (VRMap) seikyuData.getData(row - 1);
                // 被保険者番号
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 1) + ".w3",
                        getString("INSURED_NO", map));
                // ふりがな
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 4) + ".w3",
                        getString("PATIENT_KN", map));
                // 被保険者氏名
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 5) + ".w3",
                        getString("PATIENT_NM", map));
                // 意見書作成日
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 1) + ".w5",
                        IkenshoConstants.FORMAT_ERA_YMD.format(map
                                .get("KINYU_DT")));
                // 意見書発送日
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 2) + ".w5",
                        IkenshoConstants.FORMAT_ERA_YMD.format(map
                                .get("SEND_DT")));

                // 在宅・施設
                switch (Integer.parseInt(getString("IKN_CHARGE", map))) {
                // 在宅新規
                case 0:
                    pd.addAttribute("sisetu" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("keizoku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                // 在宅継続
                case 1:
                    pd.addAttribute("sisetu" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("sinki" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                // 施設新規
                case 2:
                    pd.addAttribute("zaitaku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("keizoku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                // 施設継続
                case 3:
                    pd.addAttribute("zaitaku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("sinki" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                }

                // 意見書作成
                if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
                    // 意見書作成料
                    IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 5)
                            + ".w5", costFormat.format(Double
                            .parseDouble(getIkenshoCost(map))));
                }

                // 診察・検査費用
                if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
                    // 診察・検査費用
                    if (getKensaTotal(map).compareTo(new BigDecimal("0")) > 0) {
                        IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 6)
                                + ".w5", costFormat.format((getKensaTotal(map)
                                .multiply(new BigDecimal("10"))).longValue()));
                    }
                }
            }
            for (; pageRow < 12; pageRow++) {
                // 不要なマークを消す
                // 在宅・施設
                pd.addAttribute("sisetu" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
                pd.addAttribute("zaitaku" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
                // 新規・継続
                pd.addAttribute("keizoku" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
                pd.addAttribute("sinki" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
            }
            // ページ番号
            IkenshoCommon.addString(pd, "pageNo", Integer.toString(i + 1));
            pd.endPageEdit();
        }
        // 印刷データ開始設定宣言
        pd.beginPageEdit("ichiranTotal");

        // 期間開始
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeFrom)) {
            IkenshoCommon.addString(pd, "dateRange.h1.fromY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeFrom));
        }
        // 期間終了
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeTo)) {
            IkenshoCommon.addString(pd, "dateRange.h1.toY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeTo));
        }

        // 意見書
        if (printMode == IKEN_PRINT) {
            // 宛名
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCreateCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCreateCost.getText() + "　様");
            // 請求件数
            IkenshoCommon.addString(pd, "totalCount.h1.count", costData.get(
                    "IKENSYO_COUNT").toString());
            // 意見書作成料
            IkenshoCommon.addString(pd, "totalCost.h1.cost", costFormat
                    .format(costData.get("IKENSYO_COST_TOTAL")));
            // 消費税の総額
            IkenshoCommon.addString(pd, "totalCost.h3.cost", costFormat
                    .format(costData.get("IKENSYO_TAX_TOTAL")));
            // 合計金額
            IkenshoCommon.addString(pd, "totalCost.h4.cost", costFormat
                    .format(costData.get("IKENSYO_TOTAL")));
            // 検査料
        } else if (printMode == KENSA_PRINT) {
            // 宛名
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCheckCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCheckCost.getText() + "　様");
            // 請求件数
            IkenshoCommon.addString(pd, "totalCount.h1.count", costData.get(
                    "KENSA_COUNT").toString());
            // 診察・検査費用
            IkenshoCommon.addString(pd, "totalCost.h2.cost", costFormat
                    .format(costData.get("KENSA_COST_TOTAL")));
            // 消費税の総額
            IkenshoCommon.addString(pd, "totalCost.h3.cost", costFormat
                    .format(costData.get("KENSA_TAX_TOTAL")));
            // 合計金額
            IkenshoCommon.addString(pd, "totalCost.h4.cost", costFormat
                    .format(costData.get("KENSA_TOTAL")));
            // 両方
        } else if (printMode == BOTH_PRINT) {
            // 宛名
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCreateCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCreateCost.getText() + "　様");
            // 請求件数
            IkenshoCommon.addString(pd, "totalCount.h1.count", costData.get(
                    "IKENSYO_COUNT").toString());
            // 意見書作成料
            IkenshoCommon.addString(pd, "totalCost.h1.cost", costFormat
                    .format(costData.get("IKENSYO_COST_TOTAL")));
            // 診察・検査費用
            IkenshoCommon.addString(pd, "totalCost.h2.cost", costFormat
                    .format(costData.get("KENSA_COST_TOTAL")));
            // 消費税の総額
            IkenshoCommon.addString(pd, "totalCost.h3.cost", costFormat
                    .format(costData.get("TAX_TOTAL")));
            // 合計金額
            IkenshoCommon.addString(pd, "totalCost.h4.cost", costFormat
                    .format(costData.get("TOTAL")));
        }

        // 振込先口座出力にチェックがある場合
        if (bankCheck) {
            // 金融機関名
            IkenshoCommon.addString(pd, "bank.h2.w3", getString("BANK_NM",
                    doctorData));
            // 支店名
            IkenshoCommon.addString(pd, "bank.h3.w3", getString(
                    "BANK_SITEN_NM", doctorData));
            // 口座種類
            IkenshoCommon.addString(pd, "bank.h4.w3", getKozaKind(getString(
                    "BANK_KOUZA_KIND", doctorData)));
            // 口座番号
            IkenshoCommon.addString(pd, "bank.h5.w3", getString(
                    "BANK_KOUZA_NO", doctorData));
            // 名義人
            IkenshoCommon.addString(pd, "bank.h6.w3", getString(
                    "FURIKOMI_MEIGI", doctorData));
            // 出力の指定がない場合は、表示を消す
        } else {
            // 振込先金融機関
            IkenshoCommon.addString(pd, "bank.h1.w1", "");
            // 金融機関名
            IkenshoCommon.addString(pd, "bank.h2.w2", "");
            // 支店名
            IkenshoCommon.addString(pd, "bank.h3.w2", "");
            // 口座種類
            IkenshoCommon.addString(pd, "bank.h4.w2", "");
            // 口座番号
            IkenshoCommon.addString(pd, "bank.h5.w2", "");
            // 名義人
            IkenshoCommon.addString(pd, "bank.h6.w2", "");
        }
        IkenshoCommon.addString(pd, "pageNo", Integer.toString(page + 1));
        pd.endPageEdit();

        return true;
    }

    /**
     * 請求書明細を全て出力する。
     *
     * @param pd PrintData
     * @param seikyuData VRArrayList
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @param printMode int
     * @param bankCheck boolean
     * @return boolean
     * @throws Exception
     */
    private boolean setSyosaiPrtDataAll(ACChotarouXMLWriter pd,
            VRArrayList seikyuData, IkenshoSeikyuPrintSetting seikyuPrtSet,
            int printMode, boolean bankCheck) throws Exception {

        VRMap map = null;
        // 請求書出力日付が入力されているか
        boolean outputFlg = isTaisyoKikanInput(seikyuPrtSet.billPrintDate);
        // 何月分が入力されているか
        boolean targetFlg = isTaisyoKikanInput(seikyuPrtSet.billDetailPrintDate);
        String outputEra = "";
        String outputYear = "";
        String outputMonth = "";
        String outputDay = "";

        if (outputFlg) {
            Date outputDate = seikyuPrtSet.billPrintDate.getDate();
            outputEra = VRDateParser.format(outputDate, "ggg");
            outputYear = VRDateParser.format(outputDate, "e");
            outputMonth = VRDateParser.format(outputDate, "M");
            outputDay = VRDateParser.format(outputDate, "d");
        }
        String targetEra = "";
        String targetYear = "";
        String targetMonth = "";
        if (targetFlg) {
            Date targeDate = seikyuPrtSet.billDetailPrintDate.getDate();
            targetEra = VRDateParser.format(targeDate, "ggg");
            targetYear = VRDateParser.format(targeDate, "e");
            targetMonth = VRDateParser.format(targeDate, "M");
        }

        String hokensyaNo = getSelectedCboData(hokenjyaCombo, hokenjyaList,
                "INSURER_NO");
        String atena = "";
        // 宛名
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            atena = seikyuPrtSet.toCreateCost.getText();
        } else if (printMode == KENSA_PRINT) {
            atena = seikyuPrtSet.toCheckCost.getText();
        }

        for (int i = 0; i < seikyuData.getDataSize(); i++) {
            map = (VRMap) seikyuData.getData(i);

            // 検査料のみの出力の場合
            if (printMode == KENSA_PRINT) {
                // 検査料が0点なら処理を行わない。
                if (getKensaTotal(map).compareTo(new BigDecimal("0")) == 0) {
                    continue;
                }
            }

            setSyosaiPrtData(pd, map, printMode, bankCheck, outputEra,
                    outputYear, outputMonth, outputDay, outputFlg, targetEra,
                    targetYear, targetMonth, targetFlg, false, atena,
                    hokensyaNo);

        }
        return true;
    }

    /**
     * 請求詳細の印刷データを作成します。
     *
     * @param pd PrintData
     * @param map VRHashMap 設定する請求書データ
     * @param printMode int 出力モード IKEN_PRINT : 意見書請求のみ KENSA_PRINT ： 検査料のみ
     *            BOTH_PRINT ： 両方
     * @param bankCheck boolean 振込先口座出力の有無
     * @param outputEra String 出力年号
     * @param outputYear String 出力年
     * @param outputMonth String 出力月
     * @param outputDay String 出力日
     * @param outputFlg boolean 出力日表示の有無
     * @param targetEra String 対象年号
     * @param targetYear String 対象年
     * @param targetMonth String 対象月
     * @param targetFlg boolean 対象日付の出力の有無
     * @param atena String 請求書の宛名
     * @return boolean
     * @throws Exception
     */
    public static boolean setSyosaiPrtData(ACChotarouXMLWriter pd, VRMap map,
            int printMode, boolean bankCheck, String outputEra,
            String outputYear, String outputMonth, String outputDay,
            boolean outputFlg, String targetEra, String targetYear,
            String targetMonth, boolean targetFlg, String hokensyaNo)
            throws Exception {

        String atena = "";
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // 宛名
            atena = getString("ISS_INSURER_NM", map);
        } else if (printMode == KENSA_PRINT) {
            // 宛名
            atena = getString("SKS_INSURER_NM", map);
        }

        return setSyosaiPrtData(pd, map, printMode, bankCheck, outputEra,
                outputYear, outputMonth, outputDay, outputFlg, targetEra,
                targetYear, targetMonth, targetFlg, true, atena, hokensyaNo);

    }

    /**
     * 請求詳細の印刷データを作成します。
     *
     * @param pd PrintData
     * @param map VRHashMap 設定する請求書データ
     * @param printMode int 出力モード IKEN_PRINT : 意見書請求のみ KENSA_PRINT ： 検査料のみ
     *            BOTH_PRINT ： 両方
     * @param bankCheck boolean 振込先口座出力の有無
     * @param outputEra String 出力年号
     * @param outputYear String 出力年
     * @param outputMonth String 出力月
     * @param outputDay String 出力日
     * @param outputFlg boolean 出力日表示の有無
     * @param targetEra String 対象年号
     * @param targetYear String 対象年
     * @param targetMonth String 対象月
     * @param targetFlg boolean 対象日付の出力の有無
     * @param formatFlg String 保険者番号
     * @return boolean
     * @throws Exception
     */
    private static boolean setSyosaiPrtData(ACChotarouXMLWriter pd,
            VRMap map, int printMode, boolean bankCheck, String outputEra,
            String outputYear, String outputMonth, String outputDay,
            boolean outputFlg, String targetEra, String targetYear,
            String targetMonth, boolean targetFlg, boolean formatFlg,
            String atena, String hokensyaNo) throws Exception {

        if (formatFlg) {
            // 各定義体を設定
            String dir = ACFrame.getInstance().getExeFolderPath() + separator
                    + "format" + separator;
            ACChotarouXMLUtilities.addFormat(pd, "syosai", "IkenshoMeisai.xml");
//            pd.addFormat("syosai", dir + "IkenshoMeisai.xml");
        }

        BigDecimal point = new BigDecimal("0");
        BigDecimal totalPoint = new BigDecimal("0");
        BigDecimal ikenCost = new BigDecimal("0");
        double tax = 0;

        // 印刷データ開始設定宣言
        pd.beginPageEdit("syosai");
        // 宛名
        // IkenshoCommon.addString(pd, "atena.h1.name", atena);
        IkenshoCommon.addString(pd, "atena.h1.name", atena + "　様");

        // 出力日付
        if (outputFlg) {
            IkenshoCommon.addString(pd, "printData.h1.Y", outputEra + " "
                    + dayFormat.format(Integer.parseInt(outputYear)));
            IkenshoCommon.addString(pd, "printData.h1.M", dayFormat
                    .format(Integer.parseInt(outputMonth)));
            IkenshoCommon.addString(pd, "printData.h1.D", dayFormat
                    .format(Integer.parseInt(outputDay)));
        }

        // ---被保険者hiyokensya
        // 被保険者番号
        IkenshoCommon.addString(pd, "hiyokensya.h1.w4", getString("INSURED_NO",
                map));
        // ふりがな
        IkenshoCommon.addString(pd, "hiyokensya.h3.w4", getString("PATIENT_KN",
                map));
        // 氏名
        IkenshoCommon.addString(pd, "hiyokensya.h4.w4", getString("PATIENT_NM",
                map));
        // 生年月日年号BIRTHDAY
        String birthDay = IkenshoConstants.FORMAT_ERA_YMD.format(map
                .get("BIRTHDAY"));
        // 年号
        if (birthDay.indexOf("明治") != -1) {
            pd.addAttribute("taisyo", "Visible", "FALSE");
            pd.addAttribute("syowa", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("明治", "");
        } else if (birthDay.indexOf("大正") != -1) {
            pd.addAttribute("meiji", "Visible", "FALSE");
            pd.addAttribute("syowa", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("大正", "");
        } else if (birthDay.indexOf("昭和") != -1) {
            pd.addAttribute("meiji", "Visible", "FALSE");
            pd.addAttribute("taisyo", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("昭和", "");
        } else {
            pd.addAttribute("meiji", "Visible", "FALSE");
            pd.addAttribute("taisyo", "Visible", "FALSE");
            pd.addAttribute("syowa", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("平成", "");
        }
        IkenshoCommon.addString(pd, "hiyokensya.h6.w3", birthDay);

        // 性別
        if (getString("SEX", map).equals("1")) {
            // 男性
            pd.addAttribute("woman", "Visible", "FALSE");
        } else if (getString("SEX", map).equals("2")) {
            // 女性
            pd.addAttribute("man", "Visible", "FALSE");
        } else {
            pd.addAttribute("woman", "Visible", "FALSE");
            pd.addAttribute("man", "Visible", "FALSE");
        }

        // ---保険者番号hokensyaNo
        // 対象月
        if (targetFlg) {
            IkenshoCommon.addString(pd, "hokensyaNo.h1.w1", targetEra + " "
                    + dayFormat.format(Integer.parseInt(targetYear)) + " 年 "
                    + dayFormat.format(Integer.parseInt(targetMonth)) + " 月分");
        } else {
            IkenshoCommon.addString(pd, "hokensyaNo.h1.w1", "　　    年    月分");
        }

        // 保険者番号
        // if ( (printMode & IKEN_PRINT) == IKEN_PRINT) {
        // IkenshoCommon.addString(pd, "hokensyaNo.h2.w2",
        // getString("ISS_INSURER_NO", map));
        // }
        // else if (printMode == KENSA_PRINT) {
        // IkenshoCommon.addString(pd, "hokensyaNo.h2.w2",
        // getString("SKS_INSURER_NO", map));
        // }
        IkenshoCommon.addString(pd, "hokensyaNo.h2.w2", hokensyaNo);

        // ---請求医療機関iryoKikan
        // 事業者番号
        IkenshoCommon.addString(pd, "iryoKikan.h1.w3", getString(
                "JIGYOUSHA_NO", map));
        // 事業者名称
        IkenshoCommon.addString(pd, "iryoKikan.h2.w3", getString("MI_NM", map));
        // 所在地郵便番号
        IkenshoCommon.addString(pd, "iryoKikan.h3.w4", getString("MI_POST_CD",
                map));
        // 所在地住所
        IkenshoCommon.addString(pd, "iryoKikan.h4.w3", getString("MI_ADDRESS",
                map));
        // 電話番号
        if (getString("MI_TEL1", map).equals("")) {
            IkenshoCommon.addString(pd, "iryoKikan.h5.w5", getString("MI_TEL2",
                    map));
        } else {
            IkenshoCommon.addString(pd, "iryoKikan.h5.w5", getString("MI_TEL1",
                    map)
                    + "-" + getString("MI_TEL2", map));
        }
        // ---依頼日付等 dateList
        // 作成依頼日
        // IkenshoConstants.FORMAT_ERA_YMD
        if (!getString("REQ_DT", map).equals("")) {
            IkenshoCommon.addString(pd, "dateList.h1.w2",
                    IkenshoConstants.FORMAT_ERA_YMD.format(map.get("REQ_DT")));
        }
        // 意見書作成日
        if (!getString("KINYU_DT", map).equals("")) {
            IkenshoCommon
                    .addString(pd, "dateList.h2.w2",
                            IkenshoConstants.FORMAT_ERA_YMD.format(map
                                    .get("KINYU_DT")));
        }
        // 依頼番号
        IkenshoCommon.addString(pd, "dateList.h1.w4", getString("REQ_NO", map));
        // 意見書送付日
        if (!getString("SEND_DT", map).equals("")) {
            IkenshoCommon.addString(pd, "dateList.h2.w4",
                    IkenshoConstants.FORMAT_ERA_YMD.format(map.get("SEND_DT")));
        }

        // ---意見書作成料 sakuseiCost
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // 在宅・施設
            switch (Integer.parseInt(getString("IKN_CHARGE", map))) {
            // 在宅新規
            case 0:
                pd.addAttribute("sisetu", "Visible", "FALSE");
                pd.addAttribute("keizoku", "Visible", "FALSE");
                break;
            // 在宅継続
            case 1:
                pd.addAttribute("sisetu", "Visible", "FALSE");
                pd.addAttribute("sinki", "Visible", "FALSE");
                break;
            // 施設新規
            case 2:
                pd.addAttribute("zaitaku", "Visible", "FALSE");
                pd.addAttribute("keizoku", "Visible", "FALSE");
                break;
            // 施設継続
            case 3:
                pd.addAttribute("zaitaku", "Visible", "FALSE");
                pd.addAttribute("sinki", "Visible", "FALSE");
                break;
            }

            // 意見書作成料
            ikenCost = new BigDecimal(getIkenshoCost(map));
            IkenshoCommon.addString(pd, "sakuseiCost.h1.w6", costFormat
                    .format(Double.parseDouble(getIkenshoCost(map))));
            // 検査料のみ出力時は○を表示しない
        } else {
            pd.addAttribute("zaitaku", "Visible", "FALSE");
            pd.addAttribute("sisetu", "Visible", "FALSE");
            pd.addAttribute("sinki", "Visible", "FALSE");
            pd.addAttribute("keizoku", "Visible", "FALSE");
        }

        totalPoint = new BigDecimal("0");
        point = new BigDecimal("0");

        if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
            // ---診察・検査費用
            // 初診点数
            if (getString("SHOSIN_TAISHOU", map).equals("1")) {
                switch (Integer.parseInt(getString("SHOSIN", map))) {
                // 診療所
                case 1:
                    totalPoint = totalPoint.add(new BigDecimal(getString(
                            "SHOSIN_SINRYOUJO", map)));
                    IkenshoCommon.addString(pd, "costList1.shosin.point",
                            getString("SHOSIN_SINRYOUJO", map).replaceAll(
                                    "\\.0", ""));
                    break;
                // 病院
                case 2:
                    totalPoint = totalPoint.add(new BigDecimal(getString(
                            "SHOSIN_HOSPITAL", map)));
                    IkenshoCommon.addString(pd, "costList1.shosin.point",
                            getString("SHOSIN_HOSPITAL", map).replaceAll(
                                    "\\.0", ""));
                    break;
                // その他
                case 3:
                    totalPoint = totalPoint.add(new BigDecimal(getString(
                            "SHOSIN_OTHER", map)));
                    IkenshoCommon.addString(pd, "costList1.shosin.point",
                            getString("SHOSIN_OTHER", map).replaceAll("\\.0",
                                    ""));
                    break;
                }
            }
            // 摘要
            IkenshoCommon.addString(pd, "costList1.shosin.tekiyo", getString(
                    "SHOSIN_TEKIYOU", map));

            // 胸部単純X線撮影
            if (getString("XRAY_TANJUN_SATUEI", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_XRAY_TS", map)));
            }
            if (getString("XRAY_SHASIN_SINDAN", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_XRAY_SS", map)));
            }
            if (getString("XRAY_FILM", map).equals("1")) {
                point = point.add(new BigDecimal(
                        getString("EXP_XRAY_FILM", map)));
            }
            if (point.compareTo(new BigDecimal("0")) != 0) {
                // 点数
                IkenshoCommon.addString(pd, "costList1.xray.point", point
                        .toString().replaceAll("\\.0", ""));
            }
            // 摘要
            IkenshoCommon.addString(pd, "costList1.xray.tekiyo", getString(
                    "XRAY_TEKIYOU", map));
            totalPoint = totalPoint.add(point);
            point = new BigDecimal("0");
            // 血液一般検査
            if (getString("BLD_SAISHU", map).equals("1")) {
                point = point.add(new BigDecimal(getString("EXP_KS", map)));
            }
            if (getString("BLD_IPPAN_MASHOU_KETUEKI", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KIK_MKI", map)));
            }
            if (getString("BLD_IPPAN_EKIKAGAKUTEKIKENSA", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KIK_KEKK", map)));
            }
            if (point.compareTo(new BigDecimal("0")) != 0) {
                // 点数
                IkenshoCommon.addString(pd, "costList1.bld_ippan.point", point
                        .toString().replaceAll("\\.0", ""));
            }
            // 摘要
            IkenshoCommon.addString(pd, "costList1.bld_ippan.tekiyo",
                    getString("BLD_IPPAN_TEKIYOU", map));
            totalPoint = totalPoint.add(point);
            point = new BigDecimal("0");
            // 血液化学検査
            if (getString("BLD_KAGAKU_KETUEKIKAGAKUKENSA", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KKK_KKK", map)));
            }
            if (getString("BLD_KAGAKU_SEIKAGAKUTEKIKENSA", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KKK_SKK", map)));
            }
            if (point.compareTo(new BigDecimal("0")) != 0) {
                // 点数
                IkenshoCommon.addString(pd, "costList1.bld_kagaku.point", point
                        .toString().replaceAll("\\.0", ""));
            }
            // 摘要
            IkenshoCommon.addString(pd, "costList1.bld_kagaku.tekiyo",
                    getString("BLD_KAGAKU_TEKIYOU", map));
            totalPoint = totalPoint.add(point);
            point = new BigDecimal("0");
            // 尿中一般物質定性半定量検査
            if (getString("NYO_KENSA", map).equals("1")) {
                totalPoint = totalPoint.add(new BigDecimal(getString(
                        "EXP_NITK", map)));
                // 点数
                IkenshoCommon.addString(pd, "costList1.nyo.point", getString(
                        "EXP_NITK", map).replaceAll("\\.0", ""));
            }
            // 摘要
            IkenshoCommon.addString(pd, "costList1.nyo.tekiyo", getString(
                    "NYO_KENSA_TEKIYOU", map));

            if (totalPoint.compareTo(new BigDecimal("0")) != 0) {
                // 合計点数
                IkenshoCommon.addString(pd, "costList1.total.point", totalPoint
                        .toString().replaceAll("\\.0", ""));
            }
            // 合計点数×10
            IkenshoCommon.addString(pd, "costList1.total.w7", costFormat
                    .format(totalPoint.multiply(new BigDecimal("10"))
                            .doubleValue()));

        }
        // 消費税
        tax = Double.parseDouble(getString("TAX", map));
        tax = tax / 100d;
        int totalCost = 0;
        // 請求額
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // 意見書作成料costList2
            IkenshoCommon.addString(pd, "costList2.h1.cost", costFormat
                    .format(ikenCost));
            totalCost += ikenCost.intValue();
        }
        if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
            // 診察・検査費用
            IkenshoCommon.addString(pd, "costList2.h2.cost", costFormat
                    .format(totalPoint.multiply(new BigDecimal("10"))));
            totalCost += totalPoint.multiply(new BigDecimal("10")).intValue();
        }
        // 消費税総額
        IkenshoCommon.addString(pd, "costList2.h3.cost", costFormat
                .format(Math.floor((ikenCost.add(totalPoint
                        .multiply(new BigDecimal("10")))).doubleValue()
                        * tax)));
        totalCost += Math.floor(Math.floor((ikenCost.add(totalPoint
                .multiply(new BigDecimal("10")))).doubleValue()
                * tax));
        // 合計
        IkenshoCommon.addString(pd, "costList2.h4.cost", costFormat
                .format((double) totalCost));

        // 振込先
        if (bankCheck) {
            IkenshoCommon
                    .addString(pd, "bank.h3.w2", getString("BANK_NM", map));
            IkenshoCommon.addString(pd, "bank.h4.w2", getString(
                    "BANK_SITEN_NM", map));
            IkenshoCommon.addString(pd, "bank.h5.w2", getKozaKind(getString(
                    "KOUZA_KIND", map)));
            IkenshoCommon.addString(pd, "bank.h6.w2",
                    getString("KOUZA_NO", map));
            IkenshoCommon.addString(pd, "bank.h7.w2", getString("KOUZA_MEIGI",
                    map));
        } else {
            IkenshoCommon.addString(pd, "bank.h1.w1", "");
            IkenshoCommon.addString(pd, "bank.h3.w1", "");
            IkenshoCommon.addString(pd, "bank.h4.w1", "");
            IkenshoCommon.addString(pd, "bank.h5.w1", "");
            IkenshoCommon.addString(pd, "bank.h6.w1", "");
            IkenshoCommon.addString(pd, "bank.h7.w1", "");
        }
        pd.endPageEdit();
        return true;
    }

    /**
     * 金額の計算
     *
     * @param seikyuData VRArrayList
     * @return HashMap
     * @throws Exception
     */
    private HashMap getCostData(VRArrayList seikyuData) throws Exception {
        HashMap costData = new HashMap();
        // 意見書請求金額
        int ikenshoCostTotal = 0;
        // 検査料作成件数
        int kensaCount = 0;
        // 検査料請求金額
        int kensaCostTotal = 0;
        BigDecimal kensaCostTotalTemp = new BigDecimal("0");
        // 意見書消費税額
        int ikenshoTaxTotal = 0;
        // 検査料消費税額
        int kensaTaxTotal = 0;
        // 意見書・検査料合算消費税額
        int taxTotal = 0;
        // 消費税率
        // double tax = 0d;
        BigDecimal tax = new BigDecimal("0.000");
        VRMap map = null;

        for (int i = 0; i < seikyuData.getDataSize(); i++) {
            BigDecimal taxTemp = new BigDecimal("0");
            map = (VRMap) seikyuData.getData(i);
            // tax = Double.parseDouble(getString("TAX", map)) / 100d;
            tax = new BigDecimal(getString("TAX", map) + "0000");
            tax = tax.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
            // 意見書作成料と消費税金額
            ikenshoCostTotal += Integer.parseInt(getIkenshoCost(map));
            // ikenshoTaxTotal += (int)Math.floor(new
            // BigDecimal(getIkenshoCost(map)).multiply(new
            // BigDecimal(tax)).doubleValue());
            ikenshoTaxTotal += (int) Math.floor(new BigDecimal(
                    getIkenshoCost(map)).multiply(tax).doubleValue());
            taxTemp = new BigDecimal(getIkenshoCost(map));
            // 検査費用
            kensaCostTotalTemp = getKensaTotal(map);
            if (kensaCostTotalTemp.compareTo(new BigDecimal("0")) > 0) {
                kensaCount++;
                kensaCostTotal += kensaCostTotalTemp.multiply(
                        new BigDecimal(10)).intValue();
                // kensaTaxTotal += (int)
                // Math.floor(kensaCostTotalTemp.multiply(new
                // BigDecimal("10")).multiply(new
                // BigDecimal(tax)).doubleValue());
                kensaTaxTotal += (int) Math.floor(kensaCostTotalTemp.multiply(
                        new BigDecimal("10")).multiply(tax).doubleValue());
                taxTemp = taxTemp.add(kensaCostTotalTemp
                        .multiply(new BigDecimal("10")));
            }
            // taxTotal += (int)Math.floor(taxTemp.multiply(new
            // BigDecimal(tax)).doubleValue());
            taxTotal += (int) Math.floor(taxTemp.multiply(tax).doubleValue());
        }
        // 取得した値をHashMapに設定する。
        costData.put("IKENSYO_COUNT", new Integer(seikyuData.getDataSize()));
        costData.put("IKENSYO_COST_TOTAL", new Integer(ikenshoCostTotal));
        costData.put("KENSA_COUNT", new Integer(kensaCount));
        costData.put("KENSA_COST_TOTAL", new Integer(kensaCostTotal));
        costData.put("IKENSYO_TAX_TOTAL", new Integer(ikenshoTaxTotal));
        costData.put("KENSA_TAX_TOTAL", new Integer(kensaTaxTotal));
        costData.put("IKENSYO_TOTAL", new Integer(ikenshoCostTotal
                + ikenshoTaxTotal));
        costData
                .put("KENSA_TOTAL", new Integer(kensaCostTotal + kensaTaxTotal));

        costData.put("TAX_TOTAL", new Integer(taxTotal));
        costData.put("TOTAL", new Integer(ikenshoCostTotal + kensaCostTotal
                + taxTotal));

        return costData;
    }

    private BigDecimal getKensaTotal(VRMap map) throws Exception {
        BigDecimal total = new BigDecimal("0");
        // 初診点数
        if (getString("SHOSIN_TAISHOU", map).equals("1")) {
            switch (Integer.parseInt(getString("SHOSIN", map))) {
            case 1:
                total = total.add(new BigDecimal(getString("SHOSIN_SINRYOUJO",
                        map)));
                break;
            case 2:
                total = total.add(new BigDecimal(getString("SHOSIN_HOSPITAL",
                        map)));
                break;
            case 3:
                total = total
                        .add(new BigDecimal(getString("SHOSIN_OTHER", map)));
                break;
            }
        }

        if (getString("BLD_SAISHU", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KS", map)));
        }
        if (getString("BLD_IPPAN_MASHOU_KETUEKI", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KIK_MKI", map)));
        }
        if (getString("BLD_IPPAN_EKIKAGAKUTEKIKENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KIK_KEKK", map)));
        }
        if (getString("BLD_KAGAKU_KETUEKIKAGAKUKENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KKK_KKK", map)));
        }
        if (getString("BLD_KAGAKU_SEIKAGAKUTEKIKENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KKK_SKK", map)));
        }
        if (getString("NYO_KENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_NITK", map)));
        }
        if (getString("XRAY_TANJUN_SATUEI", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_XRAY_TS", map)));
        }
        if (getString("XRAY_SHASIN_SINDAN", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_XRAY_SS", map)));
        }
        if (getString("XRAY_FILM", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_XRAY_FILM", map)));
        }
        return total;
    }

    /**
     * 意見書作成料の取得を行います。
     *
     * @param map VRHashMap データ
     * @return String 意見書作成料
     * @throws Exception 実行時例外
     */
    private static String getIkenshoCost(VRMap map) throws Exception {
        String cost = "0";

        switch (Integer.parseInt(getString("IKN_CHARGE", map))) {
        case 0:
            cost = getString("ZAITAKU_SINKI_CHARGE", map);
            break;
        case 1:
            cost = getString("ZAITAKU_KEIZOKU_CHARGE", map);
            break;
        case 2:
            cost = getString("SISETU_SINKI_CHARGE", map);
            break;
        case 3:
            cost = getString("SISETU_KEIZOKU_CHARGE", map);
            break;
        }
        return cost;
    }

    private String getDayFormatYear(IkenshoEraDateTextField date) {
        Date val = date.getDate();
        try {
            return VRDateParser.format(val, "ggg ee");
        } catch (Exception ex) {
            return "";
        }
        // return date.getEra() + " " + getDayFormat(date.getYear());
    }

    private String getDayFormatMonth(IkenshoEraDateTextField date) {
        return getDayFormat(date.getMonth());
    }

    private String getDayFormatDay(IkenshoEraDateTextField date) {
        return getDayFormat(date.getDay());
    }

    private String getDayFormat(String str) {
        return dayFormat.format(Long.parseLong(str));
    }

    private static String getKozaKind(String kozaKindCode) {
        if (kozaKindCode.equals("0")) {
            return "当座";
        } else if (kozaKindCode.equals("1")) {
            return "普通";
        }
        return "";
    }

    /**
     * DBを更新します。
     *
     * @return boolean
     * @throws Exception
     */
    private IkenshoFirebirdDBManager doUpdateAll() throws Exception {
        ArrayList updateData = new ArrayList();
        // update
        IkenshoFirebirdDBManager dbm = null;
        try {
            // パッシブチェック / トランザクション開始
            clearPassiveTask();
            for (int i = 0; i < data.size(); i++) {
                // 未発行のデータをパッシブチェックの対象とする
                if (((VRMap) data.get(i)).getData("HAKKOU_KBN").toString()
                        .equals("1")) {
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, i);
                    updateData.add(new String[] {
                            ((VRMap) data.get(i)).getData("PATIENT_NO")
                                    .toString(),
                            ((VRMap) data.get(i)).getData("EDA_NO")
                                    .toString() });
                }
            }

            dbm = getPassiveCheckedDBManager();
            if (dbm == null) {
                ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                return null;
            }

            for (int i = 0; i < updateData.size(); i++) {
                String[] data = (String[]) updateData.get(i);
                // SQL文を生成
                StringBuffer sb = new StringBuffer();
                sb.append(" UPDATE");
                sb.append(" IKN_BILL");
                sb.append(" SET");
                sb.append(" HAKKOU_KBN = 2");
                sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                sb.append(" WHERE");
                sb.append(" (PATIENT_NO = " + data[0] + ")");
                sb.append(" AND (EDA_NO = " + data[1] + ")");
                // 更新用SQL実行
                dbm.executeUpdate(sb.toString());
            }
        } catch (Exception ex) {
            // ロールバック
            dbm.rollbackTransaction();
            dbm = null;
            throw new Exception(ex);
        }
        return dbm;
    }

    /**
     * 入力チェック制御
     *
     * @return boolean
     * @throws Exception
     */
    private boolean isValidInput() throws Exception {
        boolean taisyoKikanFromInput = false;
        boolean taisyoKikanToInput = false;
        // ---検索前条件チェック
        // 保険者選択チェック
        if (hokenjyaCombo.getSelectedIndex() == -1) {
            ACMessageBox.showExclamation("依頼元(保険者)を選択してください。");
            hokenjyaCombo.requestFocus();
            return false;
        }
        // 検索日付エラーチェック
        if (isTaisyoKikanInput(taisyoKikanFrom)) {
            taisyoKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoKikanFrom.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("検索開始日付の入力に誤りがあります。");
                taisyoKikanFrom.transferFocus();
                taisyoKikanFrom
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            taisyoKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            taisyoKikanFromInput = true;
        }
        if (isTaisyoKikanInput(taisyoKikanTo)) {
            taisyoKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoKikanTo.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("検索終了日付の入力に誤りがあります。");
                taisyoKikanTo.transferFocus();
                taisyoKikanTo
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            taisyoKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            taisyoKikanToInput = true;
        }
        // 日付の前後関係チェック
        if (taisyoKikanFromInput && taisyoKikanToInput) {
            if (taisyoKikanFrom.getDate().after(taisyoKikanTo.getDate())) {
                ACMessageBox.showExclamation("開始日付と終了日付が逆転しています。");
                taisyoKikanFrom.transferFocus();
                return false;
            }
        }
        if (isChangeData()) {
            int result = ACMessageBox.showYesNoCancel("変更されています。保存してもよろしいですか？",
                    "はい(Y)", 'Y', "いいえ(N)", 'N');

            // 保存して検索
            if (result == ACMessageBox.RESULT_YES) {
                // 更新実行
                if (!doUpdate()) {
                    return false;
                }
                // 保存せず検索
            } else if (result == ACMessageBox.RESULT_NO) {
                // 何もしない
                // 検索実行キャンセル
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * DBからデータを取得します。
     *
     * @param msgFlg boolean
     * @throws Exception
     */
    private void doSelect(boolean msgFlg) throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();

        // 検索用SQL
        sb.append(" SELECT");
        // 画面表示用データ
        sb.append(" COMMON_IKN_SIS.PATIENT_NO");
        sb.append(" ,COMMON_IKN_SIS.EDA_NO");
        sb.append(" ,IKN_BILL.HAKKOU_KBN");
        sb.append(" ,IKN_BILL.HAKKOU_KBN AS HAKKOU_KBN_ORIGIN");
        sb.append(" ,COMMON_IKN_SIS.PATIENT_NM");
        sb.append(" ,COMMON_IKN_SIS.PATIENT_KN");
        sb.append(" ,COMMON_IKN_SIS.SEX");
        sb.append(" ,COMMON_IKN_SIS.BIRTHDAY");
        sb.append(" ,IKN_ORIGIN.INSURED_NO");
        sb.append(" ,COMMON_IKN_SIS.AGE");
        sb.append(" ,IKN_ORIGIN.INSURED_NO");
        sb.append(" ,COMMON_IKN_SIS.DR_NM");
        sb.append(" ,IKN_ORIGIN.REQ_DT");
        sb.append(" ,IKN_ORIGIN.KINYU_DT");
        sb.append(" ,IKN_ORIGIN.SEND_DT");
        sb.append(" ,IKN_BILL.LAST_TIME");
        // 請求書発行に必要なデータ
        sb.append(" ,IKN_ORIGIN.IKN_CREATE_CNT");

        sb.append(" ,IKN_BILL.BANK_NM");
        sb.append(" ,IKN_BILL.BANK_SITEN_NM");
        sb.append(" ,IKN_BILL.KOUZA_NO");
        sb.append(" ,IKN_BILL.KOUZA_KIND");
        sb.append(" ,IKN_BILL.KOUZA_MEIGI");
        sb.append(" ,IKN_BILL.ZAITAKU_SINKI_CHARGE");
        sb.append(" ,IKN_BILL.ZAITAKU_KEIZOKU_CHARGE");
        sb.append(" ,IKN_BILL.SISETU_SINKI_CHARGE");
        sb.append(" ,IKN_BILL.SISETU_KEIZOKU_CHARGE");
        sb.append(" ,IKN_BILL.TAX");
        sb.append(" ,IKN_BILL.IKN_CHARGE");
        sb.append(" ,IKN_BILL.SHOSIN_TAISHOU");
        sb.append(" ,IKN_BILL.SHOSIN");
        sb.append(" ,IKN_BILL.SHOSIN_SINRYOUJO");
        sb.append(" ,IKN_BILL.SHOSIN_HOSPITAL");
        sb.append(" ,IKN_BILL.SHOSIN_OTHER");
        sb.append(" ,IKN_BILL.SHOSIN_TEKIYOU");
        sb.append(" ,IKN_BILL.BLD_SAISHU");
        sb.append(" ,IKN_BILL.BLD_IPPAN_MASHOU_KETUEKI");
        sb.append(" ,IKN_BILL.BLD_IPPAN_EKIKAGAKUTEKIKENSA");
        sb.append(" ,IKN_BILL.BLD_IPPAN_TEKIYOU");
        sb.append(" ,IKN_BILL.BLD_KAGAKU_KETUEKIKAGAKUKENSA");
        sb.append(" ,IKN_BILL.BLD_KAGAKU_SEIKAGAKUTEKIKENSA");
        sb.append(" ,IKN_BILL.BLD_KAGAKU_TEKIYOU");
        sb.append(" ,IKN_BILL.NYO_KENSA");
        sb.append(" ,IKN_BILL.NYO_KENSA_TEKIYOU");
        sb.append(" ,IKN_BILL.XRAY_TANJUN_SATUEI");
        sb.append(" ,IKN_BILL.XRAY_SHASIN_SINDAN");
        sb.append(" ,IKN_BILL.XRAY_FILM");
        sb.append(" ,IKN_BILL.XRAY_TEKIYOU");
        sb.append(" ,IKN_BILL.EXP_KS");
        sb.append(" ,IKN_BILL.EXP_KIK_MKI");
        sb.append(" ,IKN_BILL.EXP_KIK_KEKK");
        sb.append(" ,IKN_BILL.EXP_KKK_KKK");
        sb.append(" ,IKN_BILL.EXP_KKK_SKK");
        sb.append(" ,IKN_BILL.EXP_NITK");
        sb.append(" ,IKN_BILL.EXP_XRAY_TS");
        sb.append(" ,IKN_BILL.EXP_XRAY_SS");
        sb.append(" ,IKN_BILL.EXP_XRAY_FILM");
        sb.append(" ,IKN_BILL.ISS_INSURER_NO");
        sb.append(" ,IKN_BILL.ISS_INSURER_NM");
        sb.append(" ,IKN_BILL.SKS_INSURER_NM");
        sb.append(" ,IKN_BILL.SKS_INSURER_NO");
        sb.append(" ,IKN_BILL.JIGYOUSHA_NO");

        sb.append(" ,IKN_ORIGIN.KIND");
        sb.append(" ,IKN_ORIGIN.REQ_NO");

        sb.append(" ,COMMON_IKN_SIS.MI_NM");
        sb.append(" ,COMMON_IKN_SIS.MI_POST_CD");
        sb.append(" ,COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(" ,COMMON_IKN_SIS.MI_TEL1");
        sb.append(" ,COMMON_IKN_SIS.MI_TEL2");

        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,");
        sb.append(" IKN_ORIGIN,");
        sb.append(" IKN_BILL");
        sb.append(" WHERE");
        // 前提条件
        sb.append(" (COMMON_IKN_SIS.PATIENT_NO = IKN_ORIGIN.PATIENT_NO)");
        sb.append(" AND (COMMON_IKN_SIS.EDA_NO = IKN_ORIGIN.EDA_NO)");
        sb.append(" AND (COMMON_IKN_SIS.DOC_KBN = 1)");
        sb.append(" AND (IKN_ORIGIN.PATIENT_NO = IKN_BILL.PATIENT_NO)");
        sb.append(" AND (IKN_ORIGIN.EDA_NO = IKN_BILL.EDA_NO)");
        // 保険者(必須)
        //2005-12-24 edit sta fujihara shin シングルクォーテーションを付加
//        sb.append(" AND (IKN_ORIGIN.INSURER_NO = "
//                + getSelectedCboData(hokenjyaCombo, hokenjyaList, "INSURER_NO")
//                + ")");
        sb.append(" AND (IKN_ORIGIN.INSURER_NO = '"
                + getSelectedCboData(hokenjyaCombo, hokenjyaList, "INSURER_NO")
                + "')");
        //2005-12-24 edit end
        //2006-2-10 edit sta fujihara shin 意見書作成料が必ず0円になる(データ移行直後のデータ)は除く
        sb.append(" AND ((IKN_BILL.ZAITAKU_SINKI_CHARGE + IKN_BILL.ZAITAKU_KEIZOKU_CHARGE + IKN_BILL.SISETU_SINKI_CHARGE + IKN_BILL.SISETU_KEIZOKU_CHARGE) <> 0)");
        //2006-2-10 edit end

        // 医師名
        if (doctorCombo.getSelectedIndex() > 0) {
            sb.append(" AND (COMMON_IKN_SIS.DR_NM = '"
                    + getSelectedCboData(doctorCombo, doctorList, "DR_NM")
                            .replaceAll("'", "''") + "')");
        }
        // 期間開始日
        if (isTaisyoKikanInput(taisyoKikanFrom)) {
            // 記入日
            if (taisyoDayCombo.getSelectedIndex() == 0) {
                sb.append(" AND (IKN_ORIGIN.KINYU_DT >= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanFrom
                                .getDate()) + "')");
                // 依頼日
                // 2005.10.12 送付日の誤り
            } else if (taisyoDayCombo.getSelectedIndex() == 1) {
                // sb.append(" AND (IKN_ORIGIN.REQ_DT >= '" +
                // IkenshoConstants.FORMAT_YMD.format(taisyoKikanFrom.getDate())
                // + "')");
                sb.append(" AND (IKN_ORIGIN.SEND_DT >= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanFrom
                                .getDate()) + "')");
            }
        }
        // 期間終了日
        if (isTaisyoKikanInput(taisyoKikanTo)) {
            // 記入日
            if (taisyoDayCombo.getSelectedIndex() == 0) {
                sb.append(" AND (IKN_ORIGIN.KINYU_DT <= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanTo
                                .getDate()) + "')");
                // 依頼日
                // 2005.10.12 送付日の誤り
            } else if (taisyoDayCombo.getSelectedIndex() == 1) {
                // sb.append(" AND (IKN_ORIGIN.REQ_DT <= '" +
                // IkenshoConstants.FORMAT_YMD.format(taisyoKikanTo.getDate()) +
                // "')");
                sb.append(" AND (IKN_ORIGIN.SEND_DT <= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanTo
                                .getDate()) + "')");
            }
        }

        // 発行済みの表示
        if (hakkozumiCheck.isSelected()) {
            sb.append(" AND (IKN_BILL.HAKKOU_KBN IN (1,2,3))");
        } else {
            sb.append(" AND (IKN_BILL.HAKKOU_KBN IN (1,3))");
        }
        sb.append(" ORDER BY");
        sb.append(" IKN_ORIGIN.INSURED_NO");
        // sb.append(" ,IKN_BILL.LAST_TIME");
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            // パッシブチェック
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }
        tableCellRenderer.setData(data);
        // テーブル再描画
        tableModel.setAdaptee(data);
        // ソートを再設定
        table.resort();
        // table.getTable().resort();
        // メニューボタンのEnabled設定
        setButtonsEnabled();
        // ステータスバーの設定
        setStatus();

        // 件数0件時のメッセージを表示
        if (msgFlg && table.getRowCount() == 0) {
            ACMessageBox.show("条件に合致する「意見書」がありませんでした。");
        } else {
            table.setSelectedModelRow(((VRSortableTableModelar) table
                    .getModel()).getReverseTranslateIndex(0));
            table.requestFocus();
            // table.getTable().requestFocus();
        }
        // 検索キーのスナップショットを作成
        IkenshoSnapshot.getInstance().snapshot();
    }

    /**
     * DBを更新します。
     *
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdate() throws Exception {
        ArrayList updateData = new ArrayList();

        // update
        IkenshoFirebirdDBManager dbm = null;
        try {
            // パッシブチェック / トランザクション開始
            clearPassiveTask();
            for (int i = 0; i < data.size(); i++) {
                // データが変更されていれば、パッシブチェックと更新対象に含める
                if (isChangeData(i)) {
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, i);
                    updateData.add(new String[] {
                            ((VRMap) data.get(i)).getData("PATIENT_NO")
                                    .toString(),
                            ((VRMap) data.get(i)).getData("EDA_NO")
                                    .toString(),
                            ((VRMap) data.get(i)).getData("HAKKOU_KBN")
                                    .toString() });
                }
            }

            dbm = getPassiveCheckedDBManager();
            if (dbm == null) {
                ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                return false;
            }

            for (int i = 0; i < updateData.size(); i++) {
                String[] data = (String[]) updateData.get(i);
                // SQL文を生成
                StringBuffer sb = new StringBuffer();
                sb.append(" UPDATE");
                sb.append(" IKN_BILL");
                sb.append(" SET");
                sb.append(" HAKKOU_KBN = " + data[2]);
                sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                sb.append(" WHERE");
                sb.append(" (PATIENT_NO = " + data[0] + ")");
                sb.append(" AND (EDA_NO = " + data[1] + ")");
                // 更新用SQL実行
                dbm.executeUpdate(sb.toString());
            }
            // コミット
            dbm.commitTransaction();
        } catch (Exception ex) {
            // ロールバック
            dbm.rollbackTransaction();
            throw new Exception(ex);
        }
        // if(commitMessageShow){
        // //処理完了通知Msg表示
        // IkenshoMessageBox.show("更新されました。");
        // }
        return true;
    }

    private VRMap getDefaultDoctor(IkenshoFirebirdDBManager dbm,
            String insurer_no) throws Exception {
        StringBuffer sb = new StringBuffer();

        // del mac 対応(unionを使用しないよう変更)
        // sb.append(" SELECT");
        // sb.append(" DOCTOR.MI_ADDRESS,");
        // sb.append(" DOCTOR.MI_NM,");
        // sb.append(" DOCTOR.KAISETUSHA_NM,");
        // sb.append(" DOCTOR.MI_TEL1,");
        // sb.append(" DOCTOR.MI_TEL2,");
        // sb.append(" JIGYOUSHA.JIGYOUSHA_NO,");
        // sb.append(" DOCTOR.BANK_NM,");
        // sb.append(" DOCTOR.BANK_SITEN_NM,");
        // sb.append(" DOCTOR.BANK_KOUZA_KIND,");
        // sb.append(" DOCTOR.BANK_KOUZA_NO,");
        // sb.append(" DOCTOR.FURIKOMI_MEIGI,");
        // sb.append(" DOCTOR.MI_DEFAULT,");
        // sb.append(" DOCTOR.DR_CD");
        // sb.append(" FROM ");
        // sb.append(" DOCTOR ");
        // sb.append(" LEFT OUTER JOIN ");
        // sb.append(" JIGYOUSHA ");
        // sb.append(" ON DOCTOR.DR_CD = JIGYOUSHA.DR_CD ");
        // //add
        // sb.append(" AND JIGYOUSHA.INSURER_NO = '" + insurer_no + "'");
        // //add
        // sb.append(" UNION");
        // sb.append(" SELECT");
        // sb.append(" CAST('' AS VARCHAR(300)) AS MI_ADDRESS,");
        // sb.append(" CAST('' AS VARCHAR(180)) AS MI_NM,");
        // sb.append(" CAST('' AS VARCHAR(90)) AS KAISETUSHA_NM,");
        // sb.append(" CAST('' AS VARCHAR(30)) AS MI_TEL1,");
        // sb.append(" CAST('' AS VARCHAR(60)) AS MI_TEL2,");
        // sb.append(" CAST('' AS VARCHAR(60)) AS JIGYOUSHA_NO,");
        // sb.append(" CAST('' AS VARCHAR(150)) AS BANK_NM,");
        // sb.append(" CAST('' AS VARCHAR(150)) AS BANK_SITEN_NM,");
        // sb.append(" CAST(2 AS INTEGER) AS BANK_KOUZA_KIND,");
        // sb.append(" CAST('' AS VARCHAR(60)) AS BANK_KOUZA_NO,");
        // sb.append(" CAST('' AS VARCHAR(90)) AS FURIKOMI_MEIGI,");
        // sb.append(" CAST(-1 AS INTEGER) AS MI_DEFAULT,");
        // sb.append(" CAST(-1 AS INTEGER) AS DR_CD");
        // sb.append(" FROM ");
        // sb.append(" RDB$DATABASE ");
        // sb.append(" ORDER BY");
        // sb.append(" 12 DESC,");
        // sb.append(" 13");
        // del end

        sb.append(" SELECT");
        sb.append(" DOCTOR.MI_ADDRESS,");
        sb.append(" DOCTOR.MI_NM,");
        sb.append(" DOCTOR.KAISETUSHA_NM,");
        sb.append(" DOCTOR.MI_TEL1,");
        sb.append(" DOCTOR.MI_TEL2,");
        sb.append(" JIGYOUSHA.JIGYOUSHA_NO,");
        sb.append(" DOCTOR.BANK_NM,");
        sb.append(" DOCTOR.BANK_SITEN_NM,");
        sb.append(" DOCTOR.BANK_KOUZA_KIND,");
        sb.append(" DOCTOR.BANK_KOUZA_NO,");
        sb.append(" DOCTOR.FURIKOMI_MEIGI,");
        sb.append(" DOCTOR.MI_DEFAULT,");
        sb.append(" DOCTOR.DR_CD");
        sb.append(" FROM ");
        sb.append(" DOCTOR ");
        sb.append(" LEFT OUTER JOIN ");
        sb.append(" JIGYOUSHA ");
        sb.append(" ON DOCTOR.DR_CD = JIGYOUSHA.DR_CD ");
        sb.append(" AND  JIGYOUSHA.INSURER_NO = '" + insurer_no + "'");
        sb.append(" ORDER BY");
        sb.append(" DOCTOR.MI_DEFAULT DESC,");
        sb.append(" DOCTOR.DR_CD");
        VRArrayList ary = (VRArrayList) dbm.executeQuery(sb.toString());

        VRMap result = null;
        // 医師名称の取得が行えた場合は、一番先頭のデータを採用する。
        if (ary.getDataSize() > 0) {
            result = (VRMap) ary.get(0);

            // 医師名称の取得が行えない場合、空のHashMapを作成する。
        } else {
            result = new VRHashMap();
            result.put("MI_ADDRESS", "");
            result.put("MI_NM", "");
            result.put("KAISETUSHA_NM", "");
            result.put("MI_TEL1", "");
            result.put("MI_TEL2", "");
            result.put("JIGYOUSHA_NO", "");
            result.put("BANK_NM", "");
            result.put("BANK_SITEN_NM", "");
            result.put("BANK_KOUZA_KIND", "2");
            result.put("BANK_KOUZA_NO", "");
            result.put("FURIKOMI_MEIGI", "");
            result.put("MI_DEFAULT", "-1");
            result.put("DR_CD", "-1");
        }

        // return (VRHashMap)ary.get(0);
        return result;
    }

    /**
     * ツールボタン、メニューの有効状態を設定します。
     */
    private void setButtonsEnabled() {
        boolean enabled = false;
        if (table.getRowCount() > 0) {
            enabled = true;
        }
        // 条件付けを追加していく
        update.setEnabled(isChangeData());
        print.setEnabled(isPrintData());
        printList.setEnabled(enabled);
        mihakko.setEnabled(enabled);
        hakkozumi.setEnabled(enabled);
        horyu.setEnabled(enabled);
    }

    private boolean isChangeData() {
        for (int i = 0; i < data.size(); i++) {
            if (isChangeData(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isChangeData(int i) {
        return !((VRMap) data.getData(i)).getData("HAKKOU_KBN").toString()
                .equals(
                        ((VRMap) data.getData(i)).getData(
                                "HAKKOU_KBN_ORIGIN").toString());
    }

    private boolean isPrintData() {
        try {
            for (int i = 0; i < data.size(); i++) {
                if (getString("HAKKOU_KBN", (VRMap) data.get(i))
                        .equals("1")) {
                    return true;
                }
            }
        } catch (Exception e) {
            VRLogger.warning(e.toString());
        }
        return false;
    }

    private void setStatus() {
        int mihakko = 0;
        for (int i = 0; i < data.size(); i++) {
            if (((VRMap) data.getData(i)).getData("HAKKOU_KBN").toString()
                    .equals("1")) {
                mihakko++;
            }
        }
        // ステータスバー
        setStatusText(Integer.toString(table.getRowCount()) + " 件抽出されました。(内 "
                + Integer.toString(mihakko) + " 件が未発行です)");

    }

    private boolean isTaisyoKikanInput(IkenshoEraDateTextField date) {
        return (!date.getEra().equals("") && !date.getYear().equals(""));
    }

    /**
     * 「日付消去」ボタン押下時
     *
     * @param e ActionEvent
     */
    public void doClearDate_actionPerformed(ActionEvent e) {
        taisyoKikanFrom.clear();
        taisyoKikanFrom.setEra("平成");
        taisyoKikanTo.clear();
        taisyoKikanTo.setEra("平成");
    }

    /**
     * 「未発行」「保留」「発行済」ボタン押下時
     *
     * @param e ActionEvent
     */
    public void changeStatus_actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedModelRow();
        // グリッドが選択されていない場合は、処理を行わない
        if (selectedRow < 0) {
            return;
        }
        // 未発行ボタン押下時
        if (e.getSource().equals(mihakko) || e.getSource().equals(mihakkoMenu)) {
            ((VRMap) data.getData(selectedRow)).setData("HAKKOU_KBN",
                    new Integer(1));
            // 保留ボタン押下時
        } else if (e.getSource().equals(horyu)
                || e.getSource().equals(horyuMenu)) {
            ((VRMap) data.getData(selectedRow)).setData("HAKKOU_KBN",
                    new Integer(3));
            // 発行済ボタン押下時
        } else if (e.getSource().equals(hakkozumi)
                || e.getSource().equals(hakkozumiMenu)) {
            ((VRMap) data.getData(selectedRow)).setData("HAKKOU_KBN",
                    new Integer(2));
        }
        // テーブル再描画
        tableModel.setAdaptee(data);
        table.setSelectedModelRow(selectedRow);
        // table.setModelSelectedRow(((VRTableModel)table.getModel()).getReverseTranslateIndex(selectedRow));
        // table.getTable().requestFocus();
        table.requestFocus();

        // table.setSelectedRow(((VRTableModel)table.getModel()).getTranslateIndex(selectedRow));
        // メニューボタンのEnabled設定
        setButtonsEnabled();
        // ステータスバーの設定
        setStatus();
    }
}

class IkenSeikyuIchiran_clearDate_actionAdapter implements ActionListener {
    private IkenshoSeikyuIchiran adaptee;

    IkenSeikyuIchiran_clearDate_actionAdapter(IkenshoSeikyuIchiran adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.doClearDate_actionPerformed(e);
    }
}

class IkenSeikyuIchiran_changeStatus_actionAdapter implements ActionListener {
    private IkenshoSeikyuIchiran adaptee;

    IkenSeikyuIchiran_changeStatus_actionAdapter(IkenshoSeikyuIchiran adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.changeStatus_actionPerformed(e);
    }
}
