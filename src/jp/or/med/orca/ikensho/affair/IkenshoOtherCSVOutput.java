package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.table.ACFollowConditionForegroundTableCellRenderer;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.io.ACBmpWriter;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACFillCharFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.io.VRCSVFile;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRDateFormat;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellEditor;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCSVFileFilter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** <HEAD_IKENSYO> */
public class IkenshoOtherCSVOutput extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private ACAffairButton find = new ACAffairButton();
    private ACAffairButton print = new ACAffairButton();
    private ACAffairButton printTable = new ACAffairButton();
    private ACTable table = new ACTable();
    private ACTableModelAdapter tableModel;
    private ACFollowConditionForegroundTableCellRenderer tableCellRenderer;
    private VRArrayList data;

    private VRPanel client = new VRPanel();
    private ACGroupBox searchGrp = new ACGroupBox();
    private ACLabelContainer insurerContainer = new ACLabelContainer();
    private ACComboBox insurer = new ACComboBox();
    private ACLabelContainer doctorContainer = new ACLabelContainer();
    private ACComboBox doctor = new ACComboBox();
    private ACLabelContainer formatKbnContainer = new ACLabelContainer();
    private ACComboBox formatKbn = new ACComboBox();
    // add begin 2006/08/03 kamitsukasa
    private ACLabelContainer formatKbnIshiContainer = new ACLabelContainer();
    private ACComboBox formatKbnIshi = new ACComboBox();
    // add end 2006/08/03 kamitsukasa
    private ACLabelContainer taisyoContainer = new ACLabelContainer();
    private ACLabelContainer taisyoDayContainer = new ACLabelContainer();
    private ACComboBox taisyoDay = new ACComboBox();
    private ACLabelContainer taisyoFromContainer = new ACLabelContainer();
    private IkenshoEraDateTextField taisyoFrom = new IkenshoEraDateTextField();
    private JLabel taisyoCaption = new JLabel();
    private ACLabelContainer taisyoToContainer = new ACLabelContainer();
    private IkenshoEraDateTextField taisyoTo = new IkenshoEraDateTextField();
    private ACButton taisyoClear = new ACButton();

    private VRPanel btnGrp = new VRPanel();
    private ACButton cancel = new ACButton();
    private ACButton output = new ACButton();
    private ACButton taisyoCancel = new ACButton();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem cancelMenu = new JMenuItem();
    private JMenuItem outputMenu = new JMenuItem();
    private JMenuItem taisyoCancelMenu = new JMenuItem();

    private String bkInsurerNm;
    private int printOutCount = 1;

    private static String separator = System.getProperty("file.separator");

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO", "EDA_NO" }, new Format[] {
                    null, null }, "LAST_TIME", "LAST_TIME");

    private final String h17 = "平成17年度";
    private final String h18 = "平成18年度";
    // add begin 2006/08/03 kamitsukasa
    private final String IKN_NEW = "主治医意見書";
    private final String IKN_ISHI = "医師意見書";
    // add end 2006/08/03 kamitsukasa
    
    // 2006/06/23
    // CRLF - 置換対応
    // Addition - begin [Masahiko Higuchi]
    private static final String VT = String.valueOf('\u000b');
    // Addition - end

    public IkenshoOtherCSVOutput() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        buttons.setTitle("「意見書」CSVファイル出力");
        buttons.add(printTable, VRLayout.EAST);
        buttons.add(print, VRLayout.EAST);
        buttons.add(find, VRLayout.EAST);
        buttons.add(update, VRLayout.EAST);

        client.setLayout(new VRLayout());
        client.add(searchGrp, VRLayout.NORTH);
        client.add(btnGrp, VRLayout.NORTH);
        client.add(table, VRLayout.CLIENT);

        // ボタン
        update.setText("更新(S)");
        update.setMnemonic('S');
        update.setActionCommand("削除(D)");
        update.setToolTipText("[出力対象]の設定を更新します。");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        find.setText("検索(V)");
        find.setMnemonic('V');
        find.setActionCommand("検索(V)");
        find.setToolTipText("現在入力されている条件により、一覧を表示します。");
        find.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_FIND);

        print.setText("ﾌｧｲﾙ作成(F)");
        print.setMnemonic('F');
        print.setActionCommand("ﾌｧｲﾙ作成(F)");
        print.setToolTipText("選択されている意見書をCSVファイルに書き出します。");
        print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_EXPORT);

        printTable.setText("一覧印刷(L)");
        printTable.setMnemonic('L');
        printTable.setActionCommand("一覧印刷(L)");
        printTable.setToolTipText("CSV出力対象患者一覧表を印刷します。");
        printTable.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_TABLE_PRINT);

        // 検索条件
        searchGrp.setText("表示条件");
        VRLayout searchGrpLayout = new VRLayout();
        searchGrpLayout.setAutoWrap(false);
        searchGrp.setLayout(searchGrpLayout);
        searchGrp.add(insurerContainer, VRLayout.FLOW_INSETLINE);
        searchGrp.add(formatKbnContainer, VRLayout.FLOW);
        // add begin 2006/08/04 kamitsukasa
        searchGrp.add(formatKbnIshiContainer, VRLayout.FLOW);
        // add end 2006/08/04 kamitsukasa
        searchGrp.add(doctorContainer, VRLayout.FLOW_RETURN);
        searchGrp.add(taisyoContainer, VRLayout.FLOW_INSETLINE_RETURN);

        // 検索条件・依頼元(保険者)
        insurerContainer.setText("依頼元(保険者)");
        insurerContainer.setLayout(new BorderLayout());
        insurerContainer.add(insurer, null);
        insurer.setPreferredSize(new Dimension(250, 20));
        insurer.setEditable(false);
        insurer.setBindPath("INSURER_NM");

        // 検索条件・医者
        doctorContainer.setText("医師氏名");
        doctorContainer.setLayout(new BorderLayout());
        doctorContainer.add(doctor, null);
        doctor.setPreferredSize(new Dimension(200, 20));
        doctor.setEditable(false);
        doctor.setBindPath("DR_NM");

        // 検索条件・フォーマット区分
        formatKbnContainer.setText("対応年度");
        formatKbnContainer.setLayout(new BorderLayout());
        formatKbnContainer.add(formatKbn, null);
        formatKbn.setEditable(false);
        formatKbn.addItem(h18);
        formatKbn.addItem(h17);
        formatKbn.setBindPath("FORMAT_KBN");
        formatKbnContainer.setVisible(false);

        // add begin 2006/08/03 kamitsukasa
        // 検索条件・フォーマット区分(主治医意見書、医師医見書)
        formatKbnIshiContainer.setText("意見書区分");
        formatKbnIshiContainer.setLayout(new BorderLayout());
        formatKbnIshiContainer.add(formatKbnIshi, null);
        formatKbnIshi.setEditable(false);
        formatKbnIshi.addItem(IKN_NEW);
        formatKbnIshi.addItem(IKN_ISHI);
        formatKbnIshiContainer.setVisible(true);
        // add end 2006/08/03 kamitsukasa

        // 検索条件・対象期間
        VRLayout taisyoContainerLayout = new VRLayout();
        taisyoContainerLayout.setHgap(0);
        taisyoContainerLayout.setVgap(0);
        taisyoContainerLayout.setAutoWrap(false);
        taisyoContainer.setContentAreaFilled(true);
        taisyoContainer
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        taisyoContainer.setLayout(taisyoContainerLayout);
        taisyoContainer.add(taisyoDayContainer, VRLayout.FLOW);
        taisyoContainer.add(taisyoFromContainer, VRLayout.FLOW);
        taisyoContainer.add(taisyoCaption, VRLayout.FLOW);
        taisyoContainer.add(taisyoToContainer, VRLayout.FLOW);
        taisyoContainer.add(taisyoClear, VRLayout.FLOW_RETURN);
        taisyoContainer
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        taisyoContainer
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        taisyoContainer.setContentAreaFilled(true);

        // 検索条件・対象期間・期間
        taisyoDayContainer.setText("指定対象");
        taisyoDayContainer.add(taisyoDay, VRLayout.FLOW);
        taisyoDay.setEditable(false);
        taisyoDay.addItem("記入日");
        taisyoDay.addItem("依頼日");
        taisyoDay.setBindPath("TAISYO_DAY");
        // 検索条件・対象期間・開始
        taisyoFromContainer.setText("開始日付");
        taisyoFromContainer.add(taisyoFrom, VRLayout.FLOW);
        taisyoFrom.setAgeVisible(false);
        taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoFrom.setBindPath("TAISYO_FROM");
        taisyoCaption.setText("～");
        // 検索条件・対象期間・終了
        taisyoToContainer.setText("終了日付");
        taisyoToContainer.add(taisyoTo, VRLayout.FLOW);
        taisyoTo.setAgeVisible(false);
        taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoTo.setBindPath("TAISHO_TO");
        // 検索条件・対象期間・日付消去
        taisyoClear.setText("日付消去(E)");
        taisyoClear.setMnemonic('E');
        taisyoClear.setPreferredSize(new Dimension(110, 22));

        // 下ボタン
        btnGrp.setLayout(new VRLayout());
        btnGrp.add(taisyoCancel, VRLayout.EAST);
        btnGrp.add(output, VRLayout.EAST);
        btnGrp.add(cancel, VRLayout.EAST);
        cancel.setText("取消(C)");
        cancel.setMnemonic('C');
        cancel.setToolTipText("[不要]にされた意見書の[出力対象]を元に戻します。");
        output.setText("レ 出力(T)");
        output.setMnemonic('T');
        output.setToolTipText("選択された意見書を[出力]にします。");
        taisyoCancel.setText("× 対象取消(U)");
        taisyoCancel.setMnemonic('U');
        taisyoCancel.setToolTipText("選択された意見書を[取消]にします。");

        // メニュー
        popup.add(cancelMenu);
        popup.add(outputMenu);
        popup.add(taisyoCancelMenu);

        cancelMenu.setActionCommand(cancel.getActionCommand());
        cancelMenu.setMnemonic(cancel.getMnemonic());
        cancelMenu.setText(cancel.getText());
        cancelMenu.setToolTipText(cancel.getToolTipText());

        outputMenu.setActionCommand(output.getActionCommand());
        outputMenu.setMnemonic(output.getMnemonic());
        outputMenu.setText(output.getText());
        outputMenu.setToolTipText(output.getToolTipText());

        taisyoCancelMenu.setActionCommand(taisyoCancel.getActionCommand());
        taisyoCancelMenu.setMnemonic(taisyoCancel.getMnemonic());
        taisyoCancelMenu.setText(taisyoCancel.getText());
        taisyoCancelMenu.setToolTipText(taisyoCancel.getToolTipText());
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        IkenshoSnapshot.getInstance().setRootContainer(searchGrp); // スナップショット撮影対象
        // メニューバーのボタンに対応するトリガーの設定
        addUpdateTrigger(update);
        addFindTrigger(find);
        addPrintTrigger(print);
        addPrintTableTrigger(printTable);

        // ComboのItemを設定
        setComboItem();

        // テーブルのレンダラ設定
        tableCellRenderer = new ACFollowConditionForegroundTableCellRenderer(
                new String[] { "OUTPUT_FLG", "CANCEL_FLG" }, new String[] {
                        "true", "true" }, new Color[] {
                        new java.awt.Color(49, 83, 255),
                        new Color(236, 148, 32) });
        // テーブル取得(列生成のため)
        doSelect(true);

        // ステータスバー(初画面のみ)
        setStatusText("「主治医意見書」「医師意見書」CSVファイル出力");

        // 期間(DatePanel)の初期化
        initTaisyoDatePanel();

        // 印刷回数初期化
        printOutCount = 1;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        if (update.isEnabled()) { // 更新ボタンのEnabledをスナップショット代わりに使用
            int result = ACMessageBox.showYesNoCancel("変更されています。保存しますか？",
                    "更新して戻る(S)", 'S', "破棄して戻る(R)", 'R');
            if (result == ACMessageBox.RESULT_YES) { // 保存して戻る
                if (doUpdateToDB()) {
                    // ボタンのEnabled設定
                    setButtonsEnabled();

                    // ステータスバー
                    setStatusText(String.valueOf(table.getRowCount())
                            + "件抽出されました。");

                    // 完了通知MSG
                    ACMessageBox.show("更新されました。", ACMessageBox.BUTTON_OK,
                            ACMessageBox.ICON_INFOMATION);
                }
            } else if (result == ACMessageBox.RESULT_NO) { // 保存しないで戻る
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean canClose() throws Exception {
        if (update.isEnabled()) { // 更新ボタンのEnabledをスナップショット代わりに使用
            String msg = "";
            msg = "更新された内容は破棄されます。\n終了してもよろしいですか？";
            int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return insurer;
    }

    private void event() throws Exception {
        // 日付消去
        taisyoClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 期間(DatePanel)の初期化
                initTaisyoDatePanel();
            }
        });

        // 「取消」押下
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCancel();
            }
        });
        // popupMenu:「取消」選択
        cancelMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCancel();
            }
        });

        // 「出力」押下
        output.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectOutput();
            }
        });
        // popupMenu:「出力」押下
        outputMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectOutput();
            }
        });

        // 「対象取消」押下
        taisyoCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectTaisyoCancel();
            }
        });
        // popupMenu:「対象取消」押下
        taisyoCancelMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectTaisyoCancel();
            }
        });

        // テーブル選択時
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showPopup(e);
            }

            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
        });
    }

    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (update.isEnabled()) { // 更新ボタンのEnabledをスナップショット代わりに使用
            int result = ACMessageBox.show("更新されています。保存してもよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
            if (result != ACMessageBox.RESULT_OK) {
                return;
            }
        }

        if (doUpdateToDB()) {
            // ボタンのEnabled設定
            setButtonsEnabled();

            // ステータスバー
            setStatusText(String.valueOf(table.getRowCount()) + "件抽出されました。");

            // 完了通知MSG
            ACMessageBox.show("更新されました。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
        }
    }

    protected void findActionPerformed(ActionEvent e) throws Exception {
        if (isValidInput()) {
            // データ取得
            doSelect(false);
        }
    }

    protected void printActionPerformed(ActionEvent e) throws Exception {
        doOutput();
    }

    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        // 検索条件が変更された場合、メッセージを表示し、印刷を中断する
        if (IkenshoSnapshot.getInstance().isModified()) {
            ACMessageBox.show("検索条件が変更されました。\n条件を元に戻すか、再度検索をして下さい。");
            return;
        }

        // データに「取消」を含む場合、更新を促す
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                int result = ACMessageBox.show("変更されています。保存してもよろしいですか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_CANCEL) {
                    return;
                } else {
                    if (doUpdateToDB()) {
                        // ボタンのEnabled設定
                        setButtonsEnabled();

                        // ステータスバー
                        setStatusText(String.valueOf(table.getRowCount())
                                + "件抽出されました。");
                        break;
                    } else {
                        return;
                    }
                }
            }
        }

        // 印刷
        doPrintTable();
    }

    /**
     * ボタンのEnabledを設定します。
     */
    private void setButtonsEnabled() {
        // 「出力」なRowが1件以上あれば「ファイル作成」が有効になる
        print.setEnabled(false);
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            if (map.getData("OUTPUT_FLG").toString().equals("true")) {
                print.setEnabled(true);
                break;
            }
        }

        // 「取消」なRowが1件以上あれば「更新」が有効になる
        update.setEnabled(false);
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            if (map.getData("CANCEL_FLG").toString().equals("true")) {
                update.setEnabled(true);
                break;
            }
        }

        // 検索結果が1件以上あれば「印刷」が有効になる
        printTable.setEnabled(false);
        if (data.getDataSize() > 0) {
            printTable.setEnabled(true);
        }

        // 取消、出力対象、対象取消
        boolean enabledFlg = false;
        if (data.getDataSize() > 0) {
            enabledFlg = true;
        }
        cancel.setEnabled(enabledFlg);
        output.setEnabled(enabledFlg);
        taisyoCancel.setEnabled(enabledFlg);
    }

    /**
     * ポップアップメニューを表示します。
     *
     * @param e MouseEvent
     */
    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show((Component) e.getSource(), e.getX(), e.getY());
        }
    }

    /**
     * 各種ComboのItemを設定します。
     */
    private void setComboItem() {
        // 依頼元(保険者)
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DISTINCT");
        sb.append(" INSURER_NM");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" ORDER BY");
        sb.append(" INSURER_NM");
        IkenshoCommon.setComboModel(insurer, sb.toString(), "INSURER_NM");

        // 医師氏名
        sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DR_NM");
        sb.append(" FROM");
        sb.append(" DOCTOR");
        sb.append(" GROUP BY");
        sb.append(" DR_NM");
        sb.append(" ORDER BY");
        sb.append(" DR_NM");
        try {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            VRArrayList tbl = (VRArrayList) dbm.executeQuery(sb.toString());
            VRMap map = new VRHashMap();
            map.put("DR_NM", "");
            tbl.add(0, map);
            if (tbl.size() > 0) {
                doctor
                        .setModel(new ACComboBoxModelAdapter(
                                new VRHashMapArrayToConstKeyArrayAdapter(tbl,
                                        "DR_NM")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 期間(DatePanel)の初期化
     */
    private void initTaisyoDatePanel() {
        taisyoDay.setSelectedIndex(0);
        taisyoFrom.clear();
        taisyoFrom.setEra("平成");
        taisyoTo.clear();
        taisyoTo.setEra("平成");
    }

    /**
     * 「取消」選択時の動作です。
     */
    private void selectCancel() {
        // 選択行を取得
        if (table.getSelectedModelRow() < 0) {
            return;
        }
        if (table.getSelectedModelRow() >= table.getRowCount()) {
            return;
        }
        int selectedDataRow = table.getSelectedModelRow();

        // 選択行の「出力」「取消」の変更
        VRMap map = (VRMap) data.getData(selectedDataRow);
        map.setData("OUTPUT_FLG", new Boolean(false));
        map.setData("CANCEL_FLG", new Boolean(false));

        // チェックボックスの編集状態の確定
        table.stopCellEditing("OUTPUT_FLG");

        // ボタンのenabled設定
        setButtonsEnabled();

        // フォーカス
        table.requestFocus();
        // table.getTable().requestFocus();
    }

    /**
     * 「出力」選択時の動作です。
     */
    private void selectOutput() {
        // 選択行を取得
        if (table.getSelectedModelRow() < 0) {
            return;
        }
        if (table.getSelectedModelRow() >= table.getRowCount()) {
            return;
        }
        int selectedDataRow = table.getSelectedModelRow();

        // 選択行の「出力」「取消」の変更
        VRMap map = (VRMap) data.getData(selectedDataRow);
        map.setData("OUTPUT_FLG", new Boolean(true));
        map.setData("CANCEL_FLG", new Boolean(false));

        // チェックボックスの編集状態の確定
        table.stopCellEditing("OUTPUT_FLG");

        // ボタンのenabled設定
        setButtonsEnabled();

        // フォーカス
        table.requestFocus();
        // table.getTable().requestFocus();
    }

    /**
     * 「対象取消」選択時の動作です。
     */
    private void selectTaisyoCancel() {
        // 選択行を取得
        if (table.getSelectedSortedRow() < 0) {
            return;
        }
        if (table.getSelectedSortedRow() >= table.getRowCount()) {
            return;
        }
        // if (table.getTable().getSelectedRow() < 0) {
        // return;
        // }
        // if (table.getTable().getSelectedRow() >=
        // table.getTable().getRowCount()) {
        // return;
        // }
        int selectedTableRow = table.getSelectedSortedRow();
        // int selectedTableRow = table.getTable().getSelectedRow();
        int selectedDataRow = table.getSelectedModelRow();

        // 選択行の「出力」「取消」の変更
        VRMap map = (VRMap) data.getData(selectedDataRow);
        map.setData("OUTPUT_FLG", new Boolean(false));
        map.setData("CANCEL_FLG", new Boolean(true));

        // チェックボックスの編集状態の確定
        table.stopCellEditing("OUTPUT_FLG");

        // ボタンのenabled設定
        setButtonsEnabled();

        // フォーカス
        table.requestFocus();
        // table.getTable().requestFocus();
    }

    /**
     * 検索条件(入力)チェック
     *
     * @return boolean
     */
    private boolean isValidInput() {
        // 保険者
        if (insurer.getSelectedIndex() < 0) {
            ACMessageBox.showExclamation("依頼元(保険者)を選択してください。");
            insurer.requestFocus();
            return false;
        }

        // 日付(開始)
        if (hasDate(taisyoFrom)) {
            taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoFrom.getInputStatus() == IkenshoEraDateTextField.STATE_VALID
                    || taisyoFrom.getInputStatus() == IkenshoEraDateTextField.STATE_EMPTY) {
                taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                taisyoFrom.getInputStatus();
            } else {
                ACMessageBox.showExclamation("検索開始日付の入力に誤りがあります。");
                taisyoFrom.transferFocus();
                taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }

        // 日付(終了)
        if (hasDate(taisyoTo)) {
            taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID
                    || taisyoTo.getInputStatus() == IkenshoEraDateTextField.STATE_EMPTY) {
                taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            } else {
                ACMessageBox.showExclamation("検索終了日付の入力に誤りがあります。");
                taisyoTo.transferFocus();
                taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }

        // 日付(開始と終了の前後チェック)
        if (hasDate(taisyoFrom)) {
            if (hasDate(taisyoTo)) {
                if (taisyoFrom.getDate().after(taisyoTo.getDate())) {
                    ACMessageBox.showExclamation("開始日付と終了日付が逆転しています。");
                    taisyoFrom.transferFocus();
                    taisyoFrom
                            .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 日付項目が検索条件として値が設定されているかどうかを取得します。
     *
     * @param dp DatePanel
     * @return boolean
     */
    private boolean hasDate(IkenshoEraDateTextField dp) {
        if (dp.getEra().equals("")) {
            return false;
        }

        if (dp.getYear().equals("")) {
            return false;
        }

        return true;
    }

    /**
     * テーブルにデータをセットします。
     *
     * @param firstFlg 初画面フラグ
     * @throws Exception
     */
    private void doSelect(boolean firstFlg) throws Exception {
        // DBからデータを取得
        doSelectFromDB();

        // テーブル列生成
        createTableColumns();

        // メッセージ
        if (!firstFlg) { // 画面遷移時でないとき(検索押下時)
            if (data.getDataSize() <= 0) {
                ACMessageBox.show("条件に合致する「意見書」がありませんでした。");

                // フォーカス
                insurer.requestFocus();
            } else {
                // レンダラに与えるデータの再設定
                tableCellRenderer.setData(data);

                // 1行目を選択
                table.setSelectedModelRow(0);

                // フォーカス
                table.requestFocus();
                // table.getTable().requestFocus();
            }

            // 印刷回数の初期化
            printOutCount = 1;

            // ステータスバー
            setStatusText(String.valueOf(table.getRowCount()) + "件抽出されました。");
        }

        // ボタンのEnabled設定
        setButtonsEnabled();

        // スナップショット撮影
        IkenshoSnapshot.getInstance().snapshot();
    }

    /**
     * DBからデータを取得します。
     *
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        // 検索条件・依頼元(保険者)
        String insurerNmSql;
        if (insurer.getSelectedIndex() >= 0) {
            bkInsurerNm = insurer.getSelectedItem().toString().replaceAll("''",
                    "'");
            insurerNmSql = " AND INSURER.INSURER_NM='" + bkInsurerNm + "'";
        } else {
            bkInsurerNm = "";
            insurerNmSql = " AND INSURER.INSURER_NM=''";
        }

        // フォーマット区分
        String formatKbnSql;
        if (formatKbn.getSelectedItem().toString().equals(h18)) {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=1";
        } else {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=0";
        }
        
        // add begin 2006/08/03 kamitsukasa
        // 保険者区分
        String insurerTypeSql;
        if (IKN_NEW.equals(formatKbnIshi.getSelectedItem().toString())) {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=1";
            insurerTypeSql = "AND INSURER.INSURER_TYPE IN (0, 1)";
        } else {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=2";
            insurerTypeSql = "AND INSURER.INSURER_TYPE IN (0, 2)";
        }
        // add end 2006/08/03 kamitsukasa

        // 検索条件・医師氏名
        String drNmSql;
        if (doctor.getSelectedIndex() >= 1) { // 1個目は空白なので。
            drNmSql = " AND COMMON_IKN_SIS.DR_NM='"
                    + doctor.getSelectedItem().toString().replaceFirst("''",
                            "'") + "'";
        } else {
            drNmSql = "";
        }

        StringBuffer sbKikanSql = new StringBuffer();
        ACDateFormat idf = new ACDateFormat("yyyy-MM-dd");
        // 検索条件・期間(開始)
        try {
            if (taisyoFrom.getDate() != null) {
                switch (taisyoDay.getSelectedIndex()) {
                case 0:
                    sbKikanSql.append(" AND IKN_ORIGIN.KINYU_DT>='");
                    break;
                case 1:
                    sbKikanSql.append(" AND IKN_ORIGIN.REQ_DT>='");
                    break;
                }
                sbKikanSql.append(idf.format(taisyoFrom.getDate()));
                sbKikanSql.append("'");
            }
        } catch (Exception ex) {
        }
        // 検索条件・期間(終了)
        try {
            if (taisyoTo.getDate() != null) {
                switch (taisyoDay.getSelectedIndex()) {
                case 0:
                    sbKikanSql.append(" AND IKN_ORIGIN.KINYU_DT<='");
                    break;
                case 1:
                    sbKikanSql.append(" AND IKN_ORIGIN.REQ_DT<='");
                    break;
                }
                sbKikanSql.append(idf.format(taisyoTo.getDate()));
                sbKikanSql.append("'");
            }
        } catch (Exception ex) {
        }

        // データ取得
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" IKN_BILL.DR_NO");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_BILL.LAST_TIME");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL,INSURER");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.INSURER_NO=INSURER.INSURER_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.DOC_KBN=1");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        sb.append(insurerNmSql);
        sb.append(drNmSql);
        sb.append(sbKikanSql.toString());
        sb.append(formatKbnSql);
        // add begin 2006/08/07 kamitsukasa
        sb.append(insurerTypeSql);
        // add end 2006/08/07 kamitsukasa
        sb.append(" ORDER BY INSURED_NO ASC");
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        // フィールドの追加
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            map.put("OUTPUT_FLG", new Boolean(false));
            map.put("CANCEL_FLG", new Boolean(false));
        }

        // パッシブチェック用
        if (data.getDataSize() > 0) {
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }
    }

    /**
     * テーブル列を生成します。
     *
     * @throws Exception
     */
    private void createTableColumns() throws Exception {
        // 表中のCheckBox
        IkenshoCheckBoxTableCellEditor outputCheckEditor = new IkenshoCheckBoxTableCellEditor();
        outputCheckEditor.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // 選択行を取得
                    if (table.getSelectedModelRow() < 0) {
                        return;
                    }
                    if (table.getSelectedModelRow() >= table.getRowCount()) {
                        return;
                    }
                    int selectedRow = table.getSelectedModelRow();

                    // 選択行の「出力」「取消」の変更
                    VRMap map = (VRMap) data.getData(selectedRow);
                    map.setData("CANCEL_FLG", new Boolean(false));

                    // チェックボックス編集状態の確定
                    table.stopCellEditing("OUTPUT_FLG");
                }
                // ボタンのenabled設定
                setButtonsEnabled();
            }
        });

        // テーブルの生成
        tableModel = new ACTableModelAdapter(data, new String[] { "DR_NM",
                "PATIENT_NM", "SEX", "BIRTHDAY", "INSURED_NO", "REQ_DT",
                "KINYU_DT", "OUTPUT_FLG", "CANCEL_FLG" });
        table.setModel(tableModel);

        // ColumnModelの生成
        table.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(7, 22, "　",
                        new IkenshoCheckBoxTableCellRenderer(),
                        outputCheckEditor),
                new ACTableColumn(8, 35, "取消", IkenshoConstants.FORMAT_CANCEL,
                        tableCellRenderer, null),
                new ACTableColumn(0, 150, "医師氏名", tableCellRenderer, null),
                new ACTableColumn(1, 150, "患者氏名", tableCellRenderer, null),
                new ACTableColumn(2, 32, "性別", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_SEX, tableCellRenderer, null),
                new ACTableColumn(3, 32, "年齢", SwingConstants.RIGHT,
                        IkenshoConstants.FORMAT_NOW_AGE, tableCellRenderer,
                        null),
                new ACTableColumn(4, 100, "被保険者番号", tableCellRenderer, null),
                new ACTableColumn(5, 120, "作成依頼日",
                        IkenshoConstants.FORMAT_ERA_YMD, tableCellRenderer,
                        null),
                new ACTableColumn(6, 120, "意見書記入日",
                        IkenshoConstants.FORMAT_ERA_YMD, tableCellRenderer,
                        null) }));

        // レンダラの設定
        // table.getTable().setDefaultRenderer(Object.class, tableCellRenderer);
        table.setDefaultRenderer(Object.class, tableCellRenderer);
    }

    /**
     * DBを更新します。
     *
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        if (data.getDataSize() <= 0) {
            return true;
        }

        // UPDATE(「取消」ならばフラグOFF
        IkenshoFirebirdDBManager dbm = null;
        try {
            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                    // パッシブチェック
                    clearPassiveTask();
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                    // UPDATEクエリ作成
                    String patientNo = String
                            .valueOf(map.getData("PATIENT_NO"));
                    String edaNo = String.valueOf(map.getData("EDA_NO"));
                    StringBuffer sb = new StringBuffer();
                    sb.append(" UPDATE");
                    sb.append(" IKN_BILL");
                    sb.append(" SET");
                    sb.append(" FD_OUTPUT_KBN=0");
                    sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
                    sb.append(" WHERE");
                    sb.append(" PATIENT_NO=" + patientNo);
                    sb.append(" AND");
                    sb.append(" EDA_NO=" + edaNo);

                    // 更新用SQL実行
                    dbm.executeUpdate(sb.toString());
                }
            }
            dbm.commitTransaction();
        } catch (Exception ex) {
            dbm.rollbackTransaction();
            throw new Exception(ex.getMessage());
        }

        // 更新したデータをdataから削除
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                data.remove(i);
            }
        }

        // パッシブチェック用
        if (data.getDataSize() > 0) {
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }

        // テーブル再描画
        tableCellRenderer.setData(data); // レンダラに与えるデータの再設定
        table.setModel(tableModel);
        table.revalidate();
        return true;
    }

    /**
     * CSVファイル出力を行います。
     *
     * @throws Exception
     */
    private void doOutput() throws Exception {
        int result = 0;

        // add sta s-fujihara
        // 被保険者番号・記入日の重複チェック
        HashMap checkMap = new HashMap();
        String key = "";
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            // 出力対象外のデータは読み飛ばす
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false"))
                continue;

            // 被保険者番号+記入日のキーを作成する。
            key = String.valueOf(map.getData("INSURED_NO"))
                    + String.valueOf(map.getData("KINYU_DT"));
            // 既に登録されているキーであれば重複有り
            // エラーメッセージを出力して処理を終了する。
            if (checkMap.containsKey(key)) {
                StringBuffer errMsg = new StringBuffer();
                errMsg.append("以下の患者は被保険者番号と記入日が重複しています。ＣＳＶファイルの出力ができません。\n");
                errMsg.append("　" + checkMap.get(key) + "\n");
                errMsg.append("　" + map.getData("PATIENT_NM"));
                ACMessageBox.show(errMsg.toString(), ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                ACMessageBox.show("ＣＳＶファイルの書き出しは行われませんでした。",
                        ACMessageBox.BUTTON_OK, ACMessageBox.ICON_INFOMATION);
                checkMap = null;
                return;
                // 未登録であれば、患者氏名を値に登録する。
            } else {
                checkMap.put(key, String.valueOf(map.getData("PATIENT_NM")));
            }
        }
        checkMap = null;
        // add end s-fujihara

        // 「通常使う医師」の検索条件「保険者」の「事業者番号」を取得

        // del sta tsutsumi
        // StringBuffer sb = new StringBuffer();
        // sb.append(" SELECT");
        // sb.append(" jigyousha.jigyousha_no");
        // sb.append(" FROM");
        // sb.append(" doctor,jigyousha,insurer");
        // sb.append(" WHERE");
        // sb.append(" doctor.mi_default=1");
        // sb.append(" AND");
        // sb.append(" doctor.dr_cd=jigyousha.dr_cd");
        // sb.append(" AND");
        // sb.append(" jigyousha.insurer_no=insurer.insurer_no");
        // sb.append(" AND");
        // sb.append(" insurer.insurer_nm='" +
        // insurer.getSelectedItem().toString().replaceAll("''", "'") + "'");
        // del end tsutsumi

        // add sta tsutsumi
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" jigyousha.jigyousha_no");
        sb.append(" FROM");
        sb.append(" doctor,jigyousha,insurer");
        sb.append(" WHERE");
        sb.append(" doctor.mi_default=(");
        sb.append(" CASE (SELECT COUNT(*) FROM doctor)");
        sb.append(" WHEN 1 THEN");
        sb.append(" doctor.mi_default");
        sb.append(" ELSE");
        sb.append(" 1");
        sb.append(" END");
        sb.append(" )");
        sb.append(" AND");
        sb.append(" doctor.dr_cd=jigyousha.dr_cd");
        sb.append(" AND");
        sb.append(" jigyousha.insurer_no=insurer.insurer_no");
        sb.append(" AND");
        sb.append(" insurer.insurer_nm='"
                + insurer.getSelectedItem().toString().replaceAll("''", "'")
                + "'");
        // add end tsutsumi

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataJigyousha = (VRArrayList) dbm.executeQuery(sb
                .toString());

        // 初期選択ファイル名を生成
        String fileName;
        if (dataJigyousha.getDataSize() > 0) {
            Date today = Calendar.getInstance().getTime();
            VRDateFormat vrdf = new VRDateFormat("yyyyMMdd");

            VRMap tmp = (VRMap) dataJigyousha.getData(0);
            fileName = tmp.get("JIGYOUSHA_NO").toString()
                    + "_"
                    + vrdf.format(today)
                    + new ACFillCharFormat("0", 2).toFilled(String
                            .valueOf(printOutCount)) + ".csv";
        } else {
            // del sta tsutsumi
            // NCMessageBox.show(
            // "医療機関に事業所番号が設定されていません。\nファイル名を指定して下さい。",
            // NCMessageBox.BUTTON_OK,
            // NCMessageBox.ICON_INFOMATION);
            // del end tsutsumi
            fileName = "";
        }

        // CSVファイル指定DLG
        File CSVFile = null;
        boolean loopFlg = false;
        do {
            loopFlg = false;

            // ファイル指定
            ACFileChooser fileChooser = new ACFileChooser();
            CSVFile = fileChooser.showSaveDialog("", fileName,
                    "CSVファイルを保存する場所を指定して下さい。", new IkenshoCSVFileFilter());
            if (CSVFile == null) {
                return;
            }

            // 拡張子を補完
            CSVFile = new File(CSVFile.getParent()
                    + IkenshoConstants.FILE_SEPARATOR
                    + ((IkenshoCSVFileFilter) fileChooser.getFileFilter())
                            .getFilePathWithExtension(CSVFile.getName()));

            // 同名ファイルチェック
            if (CSVFile.exists()) {
                result = ACMessageBox.show(
                        "同じファイル名のファイルが存在しています。\n上書きしてもよろしいですか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_CANCEL) {
                    loopFlg = true;
                }
            }
        } while (loopFlg);

        // 新旧どちらのフォーマットか
        boolean newFormat = false;
        if (formatKbn.getSelectedItem().equals(h18)) {
            newFormat = true;
        }
        
        // add begin 2006/08/03 kamitsukasa
        String iknFormat = "";
        if (IKN_NEW.equals(formatKbnIshi.getSelectedItem())) {
        	iknFormat = IKN_NEW;
        }else{
        	iknFormat = IKN_ISHI;
        }
		// add end 2006/08/03 kamitsukasa

        // 画像重複チェック
        if (!newFormat) {
            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("OUTPUT_FLG")).equals("true")) {
                    StringBuffer sbBuf = new StringBuffer();
                    sbBuf.append(getDataString(map, "INSURED_NO"));
                    sbBuf.append(formatYYYYMMDD(map.getData("KINYU_DT")));
                    sbBuf.append(".bmp");
                    File tmpFile = new File(CSVFile.getParent(), sbBuf
                            .toString());
                    if (tmpFile.exists()) {
                        ACMessageBox.show(
                                "同じファイル名(被保険者番号+記入日)の全身図ファイルが存在しています。\n\n"
                                        + "CSVファイルの出力が出来ません。\n" + "患者氏名："
                                        + map.getData("PATIENT_NM"),
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);

                        ACMessageBox.show("CSVファイルの書き出しは行われませんでした。",
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_INFOMATION);
                        return;
                    }
                }
            }
        }

        // 最終確認
        result = ACMessageBox.show("CSVファイルを作成します。\nよろしいですか？",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                ACMessageBox.FOCUS_CANCEL);
        if (result != ACMessageBox.RESULT_OK) {
            return;
        }

        // ボタンEnabled処理(固め)
        update.setEnabled(false);
        find.setEnabled(false);
        print.setEnabled(false);
        printTable.setEnabled(false);
        cancel.setEnabled(false);
        output.setEnabled(false);
        taisyoCancel.setEnabled(false);

        // CSVファイルの出力
        boolean writeSuccess;
        if (newFormat) {
        	// replace begin 2006/08/03 kamitsukasa
        	if(IKN_NEW.equals(iknFormat)){
        		writeSuccess = doOutputCSVNew((VRArrayList) data.clone(), CSVFile);
        	}else{
        		writeSuccess = doOutputCSVIshi((VRArrayList) data.clone(), CSVFile);
        	}
        	// replace end 2006/08/03 kamitsukasa
        } else {
            writeSuccess = doOutputCSVOld((VRArrayList) data.clone(), CSVFile);
        }

        if (writeSuccess) {
            // UPDATE(更新したデータのフラグを変更する)
            dbm = null;
            try {
                
                //2006/02/12[Tozo Tanaka] : replace begin                
//              for (int i = 0; i < data.getDataSize(); i++) {
//                  VRMap map = (VRMap) data.getData(i);
//                  if (String.valueOf(map.getData("OUTPUT_FLG"))
//                          .equals("true")) {
//                      // パッシブチェック
//                      clearPassiveTask();
//                      addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
//                      dbm = getPassiveCheckedDBManager();
//                      if (dbm == null) {
//                          ACMessageBox
//                                  .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
//                          // ボタンEnabled再設定
//                          setButtonsEnabled();
//                          find.setEnabled(true);
//                          cancel.setEnabled(true);
//                          output.setEnabled(true);
//                          taisyoCancel.setEnabled(true);
//                          return;
//                      }
//
//                      // UPDATEクエリ作成
//                      String patientNo = String.valueOf(map
//                              .getData("PATIENT_NO"));
//                      String edaNo = String.valueOf(map.getData("EDA_NO"));
//                      sb = new StringBuffer();
//                      sb.append(" UPDATE");
//                      sb.append(" IKN_BILL");
//                      sb.append(" SET");
//                      sb.append(" FD_OUTPUT_KBN=2");
//                      sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
//                      sb.append(" WHERE");
//                      sb.append(" PATIENT_NO=" + patientNo);
//                      sb.append(" AND");
//                      sb.append(" EDA_NO=" + edaNo);
//
//                      // 更新用SQL実行
//                      dbm.executeUpdate(sb.toString());
//                  }
//              }
              
                //パッシブチェックは事前にすべてのタスクを洗い出す
                clearPassiveTask();
                for (int i = 0; i < data.getDataSize(); i++) {
                    VRMap map = (VRMap) data.getData(i);
                    if (String.valueOf(map.getData("OUTPUT_FLG"))
                            .equals("true")) {
                        // パッシブチェック
                        addPassiveUpdateTask(PASSIVE_CHECK_KEY, i);
                    }
                }
                //成功すればトランザクションを開始したDBMが返却される
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox
                            .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    // ボタンEnabled再設定
                    setButtonsEnabled();
                    find.setEnabled(true);
                    cancel.setEnabled(true);
                    output.setEnabled(true);
                    taisyoCancel.setEnabled(true);
                    return;
                }
                
                for (int i = 0; i < data.getDataSize(); i++) {
                    VRMap map = (VRMap) data.getData(i);
                    if (String.valueOf(map.getData("OUTPUT_FLG"))
                            .equals("true")) {
                        // UPDATEクエリ作成
                        String patientNo = String.valueOf(map
                                .getData("PATIENT_NO"));
                        String edaNo = String.valueOf(map.getData("EDA_NO"));
                        sb = new StringBuffer();
                        sb.append(" UPDATE");
                        sb.append(" IKN_BILL");
                        sb.append(" SET");
                        sb.append(" FD_OUTPUT_KBN=2");
                        sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
                        sb.append(" WHERE");
                        sb.append(" PATIENT_NO=" + patientNo);
                        sb.append(" AND");
                        sb.append(" EDA_NO=" + edaNo);

                        // 更新用SQL実行
                        dbm.executeUpdate(sb.toString());
                    }
                }
                //すべてのUPDATEが完了したらコミットする
                //2006/02/12[Tozo Tanaka] : replace end 
                
                dbm.commitTransaction();
            } catch (Exception ex) {
                dbm.rollbackTransaction();
                throw new Exception(ex.getMessage());
            }
            // 更新したデータをdataから削除
            for (int i = data.getDataSize() - 1; i >= 0; i--) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("OUTPUT_FLG")).equals("true")) {
                    data.remove(i);
                }
            }

            // パッシブチェック用
            if (data.getDataSize() > 0) {
                clearReservedPassive();
                reservedPassive(PASSIVE_CHECK_KEY, data);
            }
            // テーブル再描画
            tableCellRenderer.setData(data); // レンダラに与えるデータの再設定
            // table.getTable().tableChanged(new
            // TableModelEvent(table.getModel()));
            table.tableChanged(new TableModelEvent(table.getModel()));

            // 完了通知MSG
            ACMessageBox.show("CSVファイルの書き出しを行いました。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);

            // 印刷回数インクリメント
            printOutCount++;
        } else {
            // 失敗通知MSG
            ACMessageBox.show("CSVファイルの書き出しに失敗しました。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
        }

        // ボタンEnabled再設定
        setButtonsEnabled();
        find.setEnabled(true);
        cancel.setEnabled(true);
        output.setEnabled(true);
        taisyoCancel.setEnabled(true);

    }

    /**
     * CSV出力処理を行います。(法改正前フォーマット)
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     * @return 成功したか
     */
    public boolean doOutputCSVOld(VRArrayList data, File file) throws Exception {
        // プログレス生成
        IkenshoWaitingForm iwf = new IkenshoWaitingForm(ACFrame.getInstance(),
                "CSVファイル出力中");

        // 出力対象でないデータを間引く
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
                data.remove(i);
            }
        }

        // CSV出力用のデータをDBから取得する
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSqlOld());

        // CSV出力処理(下準備)
        int max = data.getDataSize();
        iwf.setMaxCount(max); // プログレスバー最大値
        StringBuffer sbBuf = new StringBuffer();
        String softName = IkenshoCommon.getProperity("Version/SoftName");
        VRCSVFile cvs = new VRCSVFile(file.getPath()); // CSV生成
        iwf.setVisible(true);// プログレスバー表示
        // iwf.show(); //プログレスバー表示

        // CSV出力処理
        for (int i = 0; i < max; i++) {
            VRMap tmp = (VRMap) data.getData(i);
            String patientNo = tmp.getData("PATIENT_NO").toString();
            String edaNo = tmp.getData("EDA_NO").toString();
            int idx = matchingData(dataDB, patientNo, edaNo);
            if (idx < 0) {
                continue;
            }
            VRMap map = (VRMap) dataDB.getData(idx);
            String imgFileName = "";

            // 行生成
            ArrayList row = new ArrayList();
            // 001:FormatVersion
            row.add("1.0");
            // 002:SoftName
            row.add(softName);
            // 003:タイムスタンプ
            row.add(getDataString(map, "INSURED_NO")
                    + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            // 004:保険者番号
            row.add(getDataString(map, "INSURER_NO"));
            // 005:保険者名称
            row.add(getDataString(map, "INSURER_NM"));
            // 006:被保険者番号
            row.add(getDataString(map, "INSURED_NO"));
            // 007:事業所番号
            row.add(getDataString(map, "JIGYOUSHA_NO"));
            // 008:申請日
            row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
            // 009:作成依頼日
            row.add(formatYYYYMMDD(map.getData("REQ_DT")));
            // 010:送付日
            row.add(formatYYYYMMDD(map.getData("SEND_DT")));
            // 011:依頼番号
            row.add(getDataString(map, "REQ_NO"));
            // 012:医師番号
            row.add(getDataString(map, "DR_NO"));
            // 013:種別
            row.add(getDataString(map, "KIND"));
            // 014:記入日
            row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

            // 015:患者名かな
            row.add(getDataString(map, "PATIENT_KN"));
            // 016:患者名
            row.add(getDataString(map, "PATIENT_NM"));
            // 017:患者・生年月日
            row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
            // 018:患者・年齢
            row.add(getDataString(map, "AGE"));
            // 019:患者・性別
            row.add(getDataString(map, "SEX"));
            // 020:患者・郵便番号
            row.add(getDataString(map, "POST_CD"));
            // 021:患者・住民
            row.add(getDataString(map, "ADDRESS"));
            // 022:患者・電話番号
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TEL1"));
            if (getDataString(map, "TEL1").length() > 0) {
                if (getDataString(map, "TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "TEL2"));
            row.add(sbBuf.toString());
            // 023:医師氏名
            row.add(getDataString(map, "DR_NM"));
            // 024:医療機関名
            row.add(getDataString(map, "MI_NM"));
            // 025:医療機関・郵便番号
            row.add(getDataString(map, "MI_POST_CD"));
            // 026:医療機関・所在地
            row.add(getDataString(map, "MI_ADDRESS"));
            // 027:医療機関・連絡先(TEL)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_TEL1"));
            if (getDataString(map, "MI_TEL1").length() > 0) {
                if (getDataString(map, "MI_TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_TEL2"));
            row.add(sbBuf.toString());
            // 028:医療機関・連絡先(FAX)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_FAX1"));
            if (getDataString(map, "MI_FAX1").length() > 0) {
                if (getDataString(map, "MI_FAX2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_FAX2"));
            row.add(sbBuf.toString());
            // 029:医師同意
            row.add(getDataString(map, "DR_CONSENT"));
            // 030:最終診察日
            row.add(formatYYYYMMDD(map.getData("LASTDAY")));
            // 031:作成回数
            row.add(getDataString(map, "IKN_CREATE_CNT"));
            // 032:他科受診の有無
            if (map.getData("TAKA").toString().equals("0")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 033:他科受診・受診項目
            sbBuf = new StringBuffer();
            sbBuf.append(getBitFromRonriwa(getDataString(map, "TAKA"), 12));
            if (isNullText(getDataString(map, "TAKA_OTHER"))) {
                sbBuf.append("0");
            } else {
                sbBuf.append("1");
            }
            row.add(sbBuf.toString());
            // 034:他科受診・受診項目・その他内容
            row.add(getDataString(map, "TAKA_OTHER"));

            // 035:診断名1
            row.add(getDataString(map, "SINDAN_NM1"));
            // 036:発症年月日1
            row.add(formatUnknownDate(map.getData("HASHOU_DT1")));
            // 037:診断名2
            row.add(getDataString(map, "SINDAN_NM2"));
            // 038:発症年月日2
            row.add(formatUnknownDate(map.getData("HASHOU_DT2")));
            // 039:診断名3
            row.add(getDataString(map, "SINDAN_NM3"));
            // 040:発症年月日3
            row.add(formatUnknownDate(map.getData("HASHOU_DT3")));
            // 041:症状としての安定性
            row.add(getDataString(map, "SHJ_ANT"));
            // 042:介護の必要の程度に関する予後の見通し
            row.add(getDataString(map, "YKG_YOGO"));
            // 043:傷病の経過・治療内容
            sbBuf = new StringBuffer();
            if (getDataString(map, "MT_STS").length() > 0) {
                // 2006/06/22
                // CRLF - 置換対応
                // Replace - begin [Masahiko Higuchi]
                sbBuf
                        .append((getDataString(map, "MT_STS").replaceAll("\r\n",VT)).replaceAll("\n",
                                VT)); // 0x0B
                // Replace - end
                sbBuf.append("");
            }
            if ((getDataString(map, "MEDICINE1").length()
                    + getDataString(map, "DOSAGE1").length()
                    + getDataString(map, "UNIT1").length()
                    + getDataString(map, "USAGE1").length()
                    + getDataString(map, "MEDICINE2").length()
                    + getDataString(map, "DOSAGE2").length()
                    + getDataString(map, "UNIT2").length() + getDataString(map,
                    "USAGE2").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE1") + " ");
                sbBuf.append(getDataString(map, "DOSAGE1"));
                sbBuf.append(getDataString(map, "UNIT1") + " ");
                sbBuf.append(getDataString(map, "USAGE1") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE2") + " ");
                sbBuf.append(getDataString(map, "DOSAGE2"));
                sbBuf.append(getDataString(map, "UNIT2") + " ");
                sbBuf.append(getDataString(map, "USAGE2") + "");
            }
            if ((getDataString(map, "MEDICINE3").length()
                    + getDataString(map, "DOSAGE3").length()
                    + getDataString(map, "UNIT3").length()
                    + getDataString(map, "USAGE3").length()
                    + getDataString(map, "MEDICINE4").length()
                    + getDataString(map, "DOSAGE4").length()
                    + getDataString(map, "UNIT4").length() + getDataString(map,
                    "USAGE4").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE3") + " ");
                sbBuf.append(getDataString(map, "DOSAGE3"));
                sbBuf.append(getDataString(map, "UNIT3") + " ");
                sbBuf.append(getDataString(map, "USAGE3") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE4") + " ");
                sbBuf.append(getDataString(map, "DOSAGE4"));
                sbBuf.append(getDataString(map, "UNIT4") + " ");
                sbBuf.append(getDataString(map, "USAGE4") + "");
            }
            if ((getDataString(map, "MEDICINE5").length()
                    + getDataString(map, "DOSAGE5").length()
                    + getDataString(map, "UNIT5").length()
                    + getDataString(map, "USAGE5").length()
                    + getDataString(map, "MEDICINE6").length()
                    + getDataString(map, "DOSAGE6").length()
                    + getDataString(map, "UNIT6").length() + getDataString(map,
                    "USAGE6").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE5") + " ");
                sbBuf.append(getDataString(map, "DOSAGE5"));
                sbBuf.append(getDataString(map, "UNIT5") + " ");
                sbBuf.append(getDataString(map, "USAGE5") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE6") + " ");
                sbBuf.append(getDataString(map, "DOSAGE6"));
                sbBuf.append(getDataString(map, "UNIT6") + " ");
                sbBuf.append(getDataString(map, "USAGE6") + "");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の0x0Bを削除
            }
            row.add(sbBuf.toString());

            // 044:処置内容
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TNT_KNR"));
            sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
            sbBuf.append(getDataString(map, "TOUSEKI"));
            sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
            sbBuf.append(getDataString(map, "OX_RYO"));
            sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
            sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
            sbBuf.append(getDataString(map, "TOUTU"));
            sbBuf.append(getDataString(map, "KEKN_EIYOU"));
            row.add(sbBuf.toString());
            // 045:特別な措置
            row.add(getDataString(map, "MONITOR")
                    + getDataString(map, "JOKUSOU_SHOCHI"));
            // 046:失禁への対応
            row.add(getDataString(map, "RYU_CATHETER"));

            // 047:障害老人の日常生活自立度(寝たきり度)
            row.add(getDataString(map, "NETAKIRI"));
            // 048:痴呆性老人の日常生活自立度
            row.add(getDataString(map, "CHH_STS"));
            // 049:短期記憶
            row.add(getDataString(map, "TANKI_KIOKU"));
            // 050:日常の意思決定を行うための認知能力
            row.add(getDataString(map, "NINCHI"));
            // 051:自分の意思の伝達能力
            row.add(getDataString(map, "DENTATU"));
            // 052:食事
            row.add(getDataString(map, "SHOKUJI"));
            // 053:問題行動有無
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "GNS_GNC"));
            sbBuf.append(getDataString(map, "MOUSOU"));
            sbBuf.append(getDataString(map, "CHUYA"));
            sbBuf.append(getDataString(map, "BOUGEN"));
            sbBuf.append(getDataString(map, "BOUKOU"));
            sbBuf.append(getDataString(map, "TEIKOU"));
            sbBuf.append(getDataString(map, "HAIKAI"));
            sbBuf.append(getDataString(map, "FUSIMATU"));
            sbBuf.append(getDataString(map, "FUKETU"));
            sbBuf.append(getDataString(map, "ISHOKU"));
            sbBuf.append(getDataString(map, "SEITEKI_MONDAI"));
            if (sbBuf.toString().equals("00000000000")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 054:問題行動・有の場合
            row.add(sbBuf.toString());
            // 055:問題行動その他
            row.add(getDataString(map, "MONDAI_OTHER_NM"));
            // 056:精神神経症状・有無
            row.add(getDataString(map, "SEISIN"));
            // 057:精神神経症状・症状名
            row.add(getDataString(map, "SEISIN_NM"));
            // 058:専門医受診・有無
            row.add(getDataString(map, "SENMONI"));
            // 059:専門医受診・詳細
            row.add(getDataString(map, "SENMONI_NM"));
            // 060:利き腕
            row.add(getDataString(map, "KIKIUDE"));
            // 061:体重
            row.add(getDataString(map, "WEIGHT"));
            // 062:身長
            row.add(getDataString(map, "HEIGHT"));
            // 063:四肢欠損
            row.add(getDataString(map, "SISIKESSON"));
            // 064:四肢欠損部位
            row.add(getDataString(map, "SISIKESSON_BUI"));
            // 065:四肢欠損程度
            row.add(getDataString(map, "SISIKESSON_TEIDO"));
            // 066:麻痺
            row.add(getDataString(map, "MAHI"));
            // 067:麻痺部位
            row.add(getDataString(map, "MAHI_BUI"));
            // 068:麻痺程度
            row.add(getDataString(map, "MAHI_TEIDO"));
            // 069:筋力低下
            row.add(getDataString(map, "KINRYOKU_TEIKA"));
            // 070:筋力低下部位
            row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
            // 071:筋力低下程度
            row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
            // 072:褥瘡
            row.add(getDataString(map, "JOKUSOU"));
            // 073:褥瘡部位
            row.add(getDataString(map, "JOKUSOU_BUI"));
            // 074:褥瘡程度
            row.add(getDataString(map, "JOKUSOU_TEIDO"));
            // 075:皮膚疾患
            row.add(getDataString(map, "HIFUSIKKAN"));
            // 076:皮膚疾患部位
            row.add(getDataString(map, "HIFUSIKKAN_BUI"));
            // 077:皮膚疾患程度
            row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));
            // 078:関節の拘縮
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "KATA_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_HIDARI"));
            if (sbBuf.toString().equals("00000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 079:肩関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "KATA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 080:肘関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 081:股関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 082:肩関節
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 083:失調・不随意運動
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            if (sbBuf.toString().equals("000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 084:上肢
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 085:体幹
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 086:下肢
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 087:全身図
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "INSURED_NO"));
            sbBuf.append(formatYYYYMMDD(map.getData("KINYU_DT")));
            imgFileName = sbBuf.toString();
            row.add(sbBuf.toString());

            // 088:現在、発生の可能性が高い病態
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "NYOUSIKKIN"));
            sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
            sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
            sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
            sbBuf.append(getDataString(map, "ENGESEIHAIEN"));
            sbBuf.append(getDataString(map, "CHOUHEISOKU"));
            sbBuf.append(getDataString(map, "EKIKANKANSEN"));
            sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
            sbBuf.append(getDataString(map, "ITAMI"));
            sbBuf.append(getDataString(map, "DASSUI"));
            sbBuf.append(getDataString(map, "BYOUTAITA"));
            row.add(sbBuf.toString());
            // 089:現在、発生の可能性が高い病態・その他
            row.add(getDataString(map, "BYOUTAITA_NM"));
            // 090:発生の可能性が高い病態の対処方針
            sbBuf = new StringBuffer();
            if (getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "TENTOU_KOSSETU_TAISHO_HOUSIN").length() > 0) {
                sbBuf
                        .append(getDataString(map,
                                "TENTOU_KOSSETU_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
                sbBuf
                        .append(getDataString(map,
                                "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map,
                        "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "ENGESEIHAIEN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "ENGESEIHAIEN_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "CHOUHEISOKU_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "CHOUHEISOKU_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map,
                        "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "ITAMI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "ITAMI_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "DASSUI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "DASSUI_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (getDataString(map, "BYOUTAITA_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "BYOUTAITA_TAISHO_HOUSIN"));
                sbBuf.append("、");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の"、"を削除
                sbBuf.append("。"); // "。"を追加する
            }
            row.add(sbBuf.toString());

            // 091:医学的管理の必要性
            sbBuf = new StringBuffer();
            sbBuf
                    .append(getUnderlinedCheckValue(getDataString(map,
                            "HOUMON_SINRYOU"), getDataString(map,
                            "HOUMON_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_KANGO"), getDataString(map, "HOUMON_KANGO_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_REHA"), getDataString(map, "HOUMON_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TUUSHO_REHA"), getDataString(map, "TUUSHO_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU"), getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_SINRYOU"), getDataString(map,
                    "HOUMONSIKA_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_EISEISIDOU"), getDataString(map,
                    "HOUMONSIKA_EISEISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU"), getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU"), getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "IGAKUTEKIKANRI_OTHER"), getDataString(map,
                    "IGAKUTEKIKANRI_OTHER_UL")));
            row.add(sbBuf.toString());
            // 092:医学的管理の必要性・その他
            row.add(getDataString(map, "IGAKUTEKIKANRI_OTHER_NM"));
            // 093:血圧について
            row.add(getDataString(map, "KETUATU"));
            // 094:血圧について・留意事項
            row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
            // 095:嚥下について
            row.add(getDataString(map, "ENGE"));
            // 096:嚥下について・留意事項
            row.add(getDataString(map, "ENGE_RYUIJIKOU"));
            // 097:摂食について
            row.add(getDataString(map, "SESHOKU"));
            // 098:摂食について・留意事項
            row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
            // 099:移動について
            row.add(getDataString(map, "IDOU"));
            // 100:移動について・留意事項
            row.add(getDataString(map, "IDOU_RYUIJIKOU"));
            // 101:その他
            row.add(getDataString(map, "KAIGO_OTHER"));
            // 102:感染症の有無
            row.add(getDataString(map, "KANSENSHOU"));
            // 103:感染症の有無・詳細
            row.add(getDataString(map, "KANSENSHOU_NM"));
            // 104:その他特記すべき事項
            sbBuf = new StringBuffer();
            if (getDataString(map, "HASE_SCORE").length() > 0) {
                sbBuf.append("長谷川式 = ");
                sbBuf.append(getDataString(map, "HASE_SCORE"));
                sbBuf.append("点 (");
                sbBuf.append(getDataString(map, "HASE_SCR_DT"));
                sbBuf.append(")");
            }
            if (getDataString(map, "P_HASE_SCORE").length() > 0) {
                sbBuf.append(" (前回 ");
                sbBuf.append(getDataString(map, "P_HASE_SCORE"));
                sbBuf.append("点 (");
                sbBuf.append(getDataString(map, "P_HASE_SCR_DT"));
                sbBuf.append("))");
            }
            String hase = sbBuf.toString();
            if (hase.length() > 0) {
                hase += "";
            }
            sbBuf = new StringBuffer();
            if (getDataString(map, "INST_SEL_PR1").length() > 0) {
                sbBuf.append("施設選択(優先度) 1. ");
                String tmpPr = getDataString(map, "INST_SEL_PR1");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 16 - tmpPr.length())); // 16文字にする
            }
            if (getDataString(map, "INST_SEL_PR2").length() > 0) {
                sbBuf.append("2. ");
                String tmpPr = getDataString(map, "INST_SEL_PR2");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 15 - tmpPr.length())); // 15文字にする
            }
            String pr = sbBuf.toString();
            if (pr.length() > 0) {
                pr += "";
            }
            // 2006/06/22 TODO
            // CRLF - 置換対応
            // Reeplace - begin [Masahiko Higuchi]
            
            row.add(hase + pr
                    + (getDataString(map, "IKN_TOKKI").replaceAll("\r\n",VT)).replaceAll("\n", VT));
            // Replace - end
            // 1レコード追加
            cvs.addRow(row);

            // 画像出力
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" BODY_FIGURE");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");

            //2005-12-24 edit sta fujihara shin シングルクォーテーション削除
//            sb.append(" (IKN_ORIGIN.PATIENT_NO='");
//            sb.append(patientNo);
//            sb.append("')");
//            sb.append(" AND (IKN_ORIGIN.EDA_NO='");
//            sb.append(edaNo);
//            sb.append("')");
            sb.append(" (IKN_ORIGIN.PATIENT_NO=");
            sb.append(patientNo);
            sb.append(")");
            sb.append(" AND (IKN_ORIGIN.EDA_NO=");
            sb.append(edaNo);
            sb.append(")");
            //2005-12-24 edit end fujihara shin

            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.getDataSize() > 0) {
                // 人体図取得
                VRMap imgMap = (VRMap) array.getData();
                byte[] image = (byte[]) imgMap.getData("BODY_FIGURE");
                File imgFileBmp = new File(file.getParent()
                        + IkenshoConstants.FILE_SEPARATOR + imgFileName
                        + ".bmp");
                writeBmp(imgFileBmp, image);
            }

            // プログレスバー進行処理
            iwf.setProgressValue(i);
        }

        // CSV出力
        try {
            // 書き出し
            cvs.write(true, true);
        } catch (Exception ex) {
            return false;
        }

        iwf.setProgressValue(max);

        return true;
    }

    /**
     * CSV出力処理を行います。(法改正後フォーマット)
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     * @return 成功したか
     */
    public boolean doOutputCSVNew(VRArrayList data, File file) throws Exception {
        // プログレス生成
        IkenshoWaitingForm iwf = new IkenshoWaitingForm(ACFrame.getInstance(),
                "CSVファイル出力中");

        // 出力対象でないデータを間引く
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
                data.remove(i);
            }
        }

        // CSV出力用のデータをDBから取得する
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSqlNew());

        // CSV出力処理(下準備)
        int max = data.getDataSize();
        iwf.setMaxCount(max); // プログレスバー最大値
        StringBuffer sbBuf = new StringBuffer();
        String softName = IkenshoCommon.getProperity("Version/SoftName");
        VRCSVFile cvs = new VRCSVFile(file.getPath()); // CSV生成
        iwf.setVisible(true); // プログレスバー表示

        // CSV出力処理
        for (int i = 0; i < max; i++) {
            VRMap tmp = (VRMap) data.getData(i);
            String insuredNo = convertInsuredNo(tmp.getData("INSURED_NO").toString());
            String patientNo = tmp.getData("PATIENT_NO").toString();
            String edaNo = tmp.getData("EDA_NO").toString();
            int idx = matchingData(dataDB, patientNo, edaNo);
            if (idx < 0) {
                continue;
            }
            VRMap map = (VRMap) dataDB.getData(idx);

            // 行生成
            ArrayList row = new ArrayList();
            // 001:FormatVersion
            row.add("1.1");
            // 002:SoftName
            row.add(softName);
            // 003:タイムスタンプ
//            row.add(getDataString(map, "INSURED_NO")
//                    + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            row.add(insuredNo + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            // 004:保険者番号
            row.add(getDataString(map, "INSURER_NO"));
            // 005:保険者名称
            row.add(getDataString(map, "INSURER_NM"));
            // 006:被保険者番号
//            row.add(getDataString(map, "INSURED_NO"));
            row.add(insuredNo);
            // 007:事業所番号
            row.add(getDataString(map, "JIGYOUSHA_NO"));
            // 008:申請日
            row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
            // 009:作成依頼日
            row.add(formatYYYYMMDD(map.getData("REQ_DT")));
            // 010:送付日
            row.add(formatYYYYMMDD(map.getData("SEND_DT")));
            // 011:依頼番号
            row.add(getDataString(map, "REQ_NO"));
            // 012:医師番号
            row.add(getDataString(map, "DR_NO"));
            // 013:種別
            row.add(getDataString(map, "KIND"));
            // 014:記入日
            row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

            // 015:患者名かな
            row.add(getDataString(map, "PATIENT_KN"));
            // 016:患者名
            row.add(getDataString(map, "PATIENT_NM"));
            // 017:患者・生年月日
            row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
            // 018:患者・年齢
            row.add(getDataString(map, "AGE"));
            // 019:患者・性別
            row.add(getDataString(map, "SEX"));
            // 020:患者・郵便番号
            row.add(getDataString(map, "POST_CD"));
            // 021:患者・住民
            row.add(getDataString(map, "ADDRESS"));
            // 022:患者・電話番号
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TEL1"));
            if (getDataString(map, "TEL1").length() > 0) {
                if (getDataString(map, "TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "TEL2"));
            row.add(sbBuf.toString());
            // 023:医師氏名
            row.add(getDataString(map, "DR_NM"));
            // 024:医療機関名
            row.add(getDataString(map, "MI_NM"));
            // 025:医療機関・郵便番号
            row.add(getDataString(map, "MI_POST_CD"));
            // 026:医療機関・所在地
            row.add(getDataString(map, "MI_ADDRESS"));
            // 027:医療機関・連絡先(TEL)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_TEL1"));
            if (getDataString(map, "MI_TEL1").length() > 0) {
                if (getDataString(map, "MI_TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_TEL2"));
            row.add(sbBuf.toString());
            // 028:医療機関・連絡先(FAX)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_FAX1"));
            if (getDataString(map, "MI_FAX1").length() > 0) {
                if (getDataString(map, "MI_FAX2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_FAX2"));
            row.add(sbBuf.toString());
            // 029:医師同意
            row.add(getDataString(map, "DR_CONSENT"));
            // 030:最終診察日
            row.add(formatYYYYMMDD(map.getData("LASTDAY")));
            // 031:作成回数
            row.add(getDataString(map, "IKN_CREATE_CNT"));
            // 032:他科受診の有無
            // replace begin 2006/08/04 kamitsukasa
            if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA", map)) == 0) {
            	if((getDataString(map, "TAKA_OTHER")).length() <= 0){
                    row.add("2");
            	}else{
            		row.add("1");
            	}
            } else {
                row.add("1");
            }
//            if (map.getData("TAKA").toString().equals("0")) {
//                row.add("2");
//            } else {
//                row.add("1");
//            }
            // replace end 2006/08/04 kamitsukasa
            // 033:他科受診・受診項目
            sbBuf = new StringBuffer();
            sbBuf.append(getBitFromRonriwa(getDataString(map, "TAKA"), 12));
            if (isNullText(getDataString(map, "TAKA_OTHER"))) {
                sbBuf.append("0");
            } else {
                sbBuf.append("1");
            }
            row.add(sbBuf.toString());
            // 034:他科受診・受診項目・その他内容
            row.add(getDataString(map, "TAKA_OTHER"));

            // 035:診断名1
            row.add(getDataString(map, "SINDAN_NM1"));
            // 036:発症年月日1
            row.add(formatUnknownDate(map.getData("HASHOU_DT1")));
            // 037:診断名2
            row.add(getDataString(map, "SINDAN_NM2"));
            // 038:発症年月日2
            row.add(formatUnknownDate(map.getData("HASHOU_DT2")));
            // 039:診断名3
            row.add(getDataString(map, "SINDAN_NM3"));
            // 040:発症年月日3
            row.add(formatUnknownDate(map.getData("HASHOU_DT3")));
            // 041:症状としての安定性
            row.add(getDataString(map, "SHJ_ANT"));
            // 042:症状不安定の具体的状況
            row.add(getDataString(map, "INSECURE_CONDITION"));
            // 043:傷病の経過・治療内容
            sbBuf = new StringBuffer();
            if (getDataString(map, "MT_STS").length() > 0) {
                // 2006/06/23
                // CRLF - 置換対応
                // Replace - begin [Masahiko Higuchi]
                sbBuf.append((getDataString(map, "MT_STS").replaceAll("\r\n",VT)).replaceAll("\n",VT)); // 0x0B
                // Replace - end
                sbBuf.append("");
            }
            if ((getDataString(map, "MEDICINE1").length()
                    + getDataString(map, "DOSAGE1").length()
                    + getDataString(map, "UNIT1").length()
                    + getDataString(map, "USAGE1").length()
                    + getDataString(map, "MEDICINE2").length()
                    + getDataString(map, "DOSAGE2").length()
                    + getDataString(map, "UNIT2").length() + getDataString(map,
                    "USAGE2").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE1") + " ");
                sbBuf.append(getDataString(map, "DOSAGE1"));
                sbBuf.append(getDataString(map, "UNIT1") + " ");
                sbBuf.append(getDataString(map, "USAGE1") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE2") + " ");
                sbBuf.append(getDataString(map, "DOSAGE2"));
                sbBuf.append(getDataString(map, "UNIT2") + " ");
                sbBuf.append(getDataString(map, "USAGE2") + "");
            }
            if ((getDataString(map, "MEDICINE3").length()
                    + getDataString(map, "DOSAGE3").length()
                    + getDataString(map, "UNIT3").length()
                    + getDataString(map, "USAGE3").length()
                    + getDataString(map, "MEDICINE4").length()
                    + getDataString(map, "DOSAGE4").length()
                    + getDataString(map, "UNIT4").length() + getDataString(map,
                    "USAGE4").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE3") + " ");
                sbBuf.append(getDataString(map, "DOSAGE3"));
                sbBuf.append(getDataString(map, "UNIT3") + " ");
                sbBuf.append(getDataString(map, "USAGE3") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE4") + " ");
                sbBuf.append(getDataString(map, "DOSAGE4"));
                sbBuf.append(getDataString(map, "UNIT4") + " ");
                sbBuf.append(getDataString(map, "USAGE4") + "");
            }
            if ((getDataString(map, "MEDICINE5").length()
                    + getDataString(map, "DOSAGE5").length()
                    + getDataString(map, "UNIT5").length()
                    + getDataString(map, "USAGE5").length()
                    + getDataString(map, "MEDICINE6").length()
                    + getDataString(map, "DOSAGE6").length()
                    + getDataString(map, "UNIT6").length() + getDataString(map,
                    "USAGE6").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE5") + " ");
                sbBuf.append(getDataString(map, "DOSAGE5"));
                sbBuf.append(getDataString(map, "UNIT5") + " ");
                sbBuf.append(getDataString(map, "USAGE5") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE6") + " ");
                sbBuf.append(getDataString(map, "DOSAGE6"));
                sbBuf.append(getDataString(map, "UNIT6") + " ");
                sbBuf.append(getDataString(map, "USAGE6") + "");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の0x0Bを削除
            }
            row.add(sbBuf.toString());

            // 044:処置内容
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TNT_KNR"));
            sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
            sbBuf.append(getDataString(map, "TOUSEKI"));
            sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
            sbBuf.append(getDataString(map, "OX_RYO"));
            sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
            sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
            sbBuf.append(getDataString(map, "TOUTU"));
            sbBuf.append(getDataString(map, "KEKN_EIYOU"));
            row.add(sbBuf.toString());
            // 045:特別な措置
            row.add(getDataString(map, "MONITOR")
                    + getDataString(map, "JOKUSOU_SHOCHI"));
            // 046:失禁への対応
            // 2006/03/16[Tozo Tanaka] : replace begin
//            row.add(getDataString(map, "RYU_CATHETER"));
            row.add(getDataString(map, "CATHETER"));
            // 2006/03/16[Tozo Tanaka] : replace end

            // 047:障害高齢者の日常生活自立度(寝たきり度)
            row.add(getDataString(map, "NETAKIRI"));
            // 048:認知症高齢者の日常生活自立度
            row.add(getDataString(map, "CHH_STS"));
            // 049:短期記憶
            row.add(getDataString(map, "TANKI_KIOKU"));
            // 050:日常の意思決定を行うための認知能力
            row.add(getDataString(map, "NINCHI"));
            // 051:自分の意思の伝達能力
            row.add(getDataString(map, "DENTATU"));
            // 052:認知症の周辺症状・有無
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "GNS_GNC"));
            sbBuf.append(getDataString(map, "MOUSOU"));
            sbBuf.append(getDataString(map, "CHUYA"));
            sbBuf.append(getDataString(map, "BOUGEN"));
            sbBuf.append(getDataString(map, "BOUKOU"));
            sbBuf.append(getDataString(map, "TEIKOU"));
            sbBuf.append(getDataString(map, "HAIKAI"));
            sbBuf.append(getDataString(map, "FUSIMATU"));
            sbBuf.append(getDataString(map, "FUKETU"));
            sbBuf.append(getDataString(map, "ISHOKU"));
            sbBuf.append(getDataString(map, "SEITEKI_MONDAI"));
            sbBuf.append(getDataString(map, "MONDAI_OTHER"));
            if (sbBuf.toString().equals("000000000000")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 053:周辺症状・有の場合
            row.add(sbBuf.toString());
            // 054:問題行動その他
            row.add(getDataString(map, "MONDAI_OTHER_NM"));
            // 055:その他の精神・神経症状・有無
            row.add(getDataString(map, "SEISIN"));
            // 056:その他の精神・神経症状・症状名
            row.add(getDataString(map, "SEISIN_NM"));
            // 057:その他の精神・神経症状・専門医受診・有無
            row.add(getDataString(map, "SENMONI"));
            // 058:その他の精神・神経症状・専門医受診・詳細
            row.add(getDataString(map, "SENMONI_NM"));
            // 059:利き腕
            row.add(getDataString(map, "KIKIUDE"));
            // 060:身長
            row.add(getDataString(map, "HEIGHT"));
            // 061:体重
            row.add(getDataString(map, "WEIGHT"));
            // 062:過去6ヶ月の体重の変化
            row.add(getDataString(map, "WEIGHT_CHANGE"));
            // 063:四肢欠損
            row.add(getDataString(map, "SISIKESSON"));
            // 064:四肢欠損部位
            row.add(getDataString(map, "SISIKESSON_BUI"));
            // 065:麻痺(ikn_origin.mahiではない)
            // row.add(getDataString(map, "MAHI"));
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MAHI_LEFTARM"));
            sbBuf.append(getDataString(map, "MAHI_RIGHTARM"));
            sbBuf.append(getDataString(map, "MAHI_LOWERLEFTLIMB"));
            sbBuf.append(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
            sbBuf.append(getDataString(map, "MAHI_ETC"));
            if (sbBuf.toString().equals("00000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 066:麻痺-右上肢
            row.add(getDataString(map, "MAHI_RIGHTARM"));
            // 067:麻痺-右上肢-程度
            row.add(getDataString(map, "MAHI_RIGHTARM_TEIDO"));
            // 068:麻痺-左上肢
            row.add(getDataString(map, "MAHI_LEFTARM"));
            // 069:麻痺-左上肢-程度
            row.add(getDataString(map, "MAHI_LEFTARM_TEIDO"));
            // 070:麻痺-右下肢
            row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
            // 071:麻痺-右下肢-程度
            row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB_TEIDO"));
            // 072:麻痺-左下肢
            row.add(getDataString(map, "MAHI_LOWERLEFTLIMB"));
            // 073:麻痺-左下肢-程度
            row.add(getDataString(map, "MAHI_LOWERLEFTLIMB_TEIDO"));
            // 074:麻痺-その他
            row.add(getDataString(map, "MAHI_ETC"));
            // 075:麻痺-その他-部位
            row.add(getDataString(map, "MAHI_ETC_BUI"));
            // 076:麻痺-その他-程度
            row.add(getDataString(map, "MAHI_ETC_TEIDO"));
            // 077:筋力低下
            row.add(getDataString(map, "KINRYOKU_TEIKA"));
            // 078:筋力低下部位
            row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
            // 079:筋力低下程度
            row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
            // 080:関節の拘縮
            row.add(getDataString(map, "KOUSHU"));
            // 081:関節の拘縮-部位
            row.add(getDataString(map, "KOUSHU_BUI"));
            // 082:関節の拘縮-程度
            row.add(getDataString(map, "KOUSHU_TEIDO"));
            // 083:関節の痛み
            row.add(getDataString(map, "KANSETU_ITAMI"));
            // 084:関節の痛み-部位
            row.add(getDataString(map, "KANSETU_ITAMI_BUI"));
            // 085:関節の痛み-程度
            row.add(getDataString(map, "KANSETU_ITAMI_TEIDO"));
            // 086:失調・不随意運動
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            if (sbBuf.toString().equals("000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 087:上肢
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 088:下肢
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 089:体幹
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 090:褥瘡
            row.add(getDataString(map, "JOKUSOU"));
            // 091:褥瘡-部位
            row.add(getDataString(map, "JOKUSOU_BUI"));
            // 092:褥瘡-程度
            row.add(getDataString(map, "JOKUSOU_TEIDO"));
            // 093:その他皮膚疾患
            row.add(getDataString(map, "HIFUSIKKAN"));
            // 094:その他皮膚疾患-部位
            row.add(getDataString(map, "HIFUSIKKAN_BUI"));
            // 095:その他皮膚疾患-程度
            row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));

            // 096:屋外歩行
            row.add(getDataString(map, "OUTDOOR"));
            // 097:車椅子の使用
            row.add(getDataString(map, "WHEELCHAIR"));
            // 098:歩行補助具・装具の使用
            row.add(getBitFromRonriwa(
                            getDataString(map, "ASSISTANCE_TOOL"), 3));
            // 099:食事行為
            row.add(getDataString(map, "MEAL"));
            // 100:現在の栄養状態
            row.add(getDataString(map, "NOURISHMENT"));
            // 101:栄養･食生活上の留意点
            row.add(getDataString(map, "EATING_RYUIJIKOU"));

            // 102:現在あるかまたは今後発生の可能性の高い状態とその対処方針（状態）
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "NYOUSIKKIN"));
            sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
            sbBuf.append(getDataString(map, "IDOUTEIKA"));
            sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
            sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
            sbBuf.append(getDataString(map, "TOJIKOMORI"));
            sbBuf.append(getDataString(map, "IYOKUTEIKA"));
            sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
            sbBuf.append(getDataString(map, "TEIEIYOU"));
            sbBuf.append(getDataString(map, "SESSYOKUENGE"));
            sbBuf.append(getDataString(map, "DASSUI"));
            sbBuf.append(getDataString(map, "EKIKANKANSEN"));
            sbBuf.append(getDataString(map, "GAN_TOUTU"));
            sbBuf.append(getDataString(map, "BYOUTAITA"));
            row.add(sbBuf.toString());
            // 103:現在あるかまたは今後発生の可能性の高い状態とその対処方針（状態）
            row.add(getDataString(map, "BYOUTAITA_NM"));
            // 104:現在あるかまたは今後発生の可能性の高い状態とその対処方針（対処方針）
            // replace begin 2006/08/07 kamitsukasa
//            sbBuf = new StringBuffer();
//            if (getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "TENTOU_KOSSETU_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                                "TENTOU_KOSSETU_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "IDOUTEIKA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "IDOUTEIKA_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                        "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                        "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "TOJIKOMORI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "TOJIKOMORI_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "IYOKUTEIKA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "IYOKUTEIKA_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                                "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "TEIEIYOU_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "TEIEIYOU_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "SESSYOKUENGE_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "SESSYOKUENGE_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "DASSUI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "DASSUI_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "GAN_TOUTU_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "GAN_TOUTU_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (getDataString(map, "BYOUTAITA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "BYOUTAITA_TAISHO_HOUSIN"));
//                sbBuf.append("、");
//            }
//            if (sbBuf.length() > 0) {
//                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の"、"を削除
//                sbBuf.append("。"); // "。"を追加する
//                //add sta 140文字で切る
//                if (sbBuf.toString().length() > 140) {
//                	String tmpBuf = sbBuf.toString();
//                	sbBuf = new StringBuffer();
//                	sbBuf.append(tmpBuf.substring(0, 140));
//                }
//                //add end
//            }
//            row.add(sbBuf.toString());
            
		    sbBuf = new StringBuffer();
		    VRList words = new VRArrayList();
		    poolString(map, words, new String[] { 
		    		"NYOUSIKKIN_TAISHO_HOUSIN",
					"TENTOU_KOSSETU_TAISHO_HOUSIN",
					"IDOUTEIKA_TAISHO_HOUSIN",
					"JOKUSOU_KANOUSEI_TAISHO_HOUSIN",
					"SINPAIKINOUTEIKA_TAISHO_HOUSIN", 
					"TOJIKOMORI_TAISHO_HOUSIN",
					"IYOKUTEIKA_TAISHO_HOUSIN",
					"HAIKAI_KANOUSEI_TAISHO_HOUSIN", 
					"TEIEIYOU_TAISHO_HOUSIN",
					"SESSYOKUENGE_TAISHO_HOUSIN",
					"DASSUI_TAISHO_HOUSIN",
					"EKIKANKANSEN_TAISHO_HOUSIN", 
					"GAN_TOUTU_TAISHO_HOUSIN",
					"BYOUTAITA_TAISHO_HOUSIN" });
		    
		    if (words.size() > 0) {
				// 対処方針を文字単位で連結して表示可能なところまで。
				final int MAX_LENGTH = 89;

				int inlineSize = 0;
				sbBuf = new StringBuffer();
				int end = words.size() - 1;
				for (int j = 0; j < end; j++) {
					String text = ACCastUtilities.toString(words.get(j));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '。') && (c != '、')) {
						line.append("、");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// 出力可能なところまで追加
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// 行終了チェック
								break;
							}
						}
						break;
					}
					inlineSize += wordSize;
					sbBuf.append(line.toString());
				}
				if (inlineSize <= MAX_LENGTH) {
					// 末尾追加
					String text = ACCastUtilities.toString(words.get(end));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '。') && (c != '、')) {
						line.append("。");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// 出力可能なところまで追加
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// 行終了チェック
								break;
							}
						}
					} else {
						sbBuf.append(line.toString());
					}
				}
			}
			row.add(sbBuf.toString());
            // replace end 2006/08/07 kamitsukasa
            
            
            // //102:介護の必要の程度に関する予後の見通し
            // row.add(getDataString(map, "YKG_YOGO"));
            // //103:介護の必要の程度に関する予後の見通し・改善への寄与が期待できるサービス
            // row.add(getDataString(map, "IMPRO_SERVICE"));
            // 105:サービス利用による生活機能の維持・改善の見通し
            row.add(getDataString(map, "VITAL_FUNCTIONS_OUTLOOK"));

            // 106:医学的管理の必要性
            sbBuf = new StringBuffer();
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                            "HOUMON_SINRYOU"), getDataString(map,
                            "HOUMON_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_KANGO"), getDataString(map, "HOUMON_KANGO_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_REHA"), getDataString(map, "HOUMON_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TUUSHO_REHA"), getDataString(map, "TUUSHO_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU"), getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_SINRYOU"), getDataString(map,
                    "HOUMONSIKA_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_EISEISIDOU"), getDataString(map,
                    "HOUMONSIKA_EISEISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU"), getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU"), getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "IGAKUTEKIKANRI_OTHER"), getDataString(map,
                    "IGAKUTEKIKANRI_OTHER_UL")));
            row.add(sbBuf.toString());
            // 107:医学的管理の必要性・その他
            row.add(getDataString(map, "IGAKUTEKIKANRI_OTHER_NM"));
            // 108:介護血圧
            row.add(getDataString(map, "KETUATU"));
            // 109:介護血圧・留意事項
            row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
            // 110:介護摂食
            row.add(getDataString(map, "SESHOKU"));
            // 111:介護摂食・留意事項
            row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
            // 112:介護嚥下
            row.add(getDataString(map, "ENGE"));
            // 113:介護嚥下・留意事項
            row.add(getDataString(map, "ENGE_RYUIJIKOU"));
            // 114:介護移動
            row.add(getDataString(map, "IDOU"));
            // 115:介護移動・留意事項
            row.add(getDataString(map, "IDOU_RYUIJIKOU"));
            // 116:介護運動
            row.add(getDataString(map, "UNDOU"));
            // 117:介護運動・留意事項
            row.add(getDataString(map, "UNDOU_RYUIJIKOU"));
            // 118:介護その他
            row.add(getDataString(map, "KAIGO_OTHER"));
            // 119:感染症の有無
            row.add(getDataString(map, "KANSENSHOU"));
            // 120:感染症の有無・詳細
            row.add(getDataString(map, "KANSENSHOU_NM"));
            // 121:その他特記すべき事項
            sbBuf = new StringBuffer();
            if (getDataString(map, "HASE_SCORE").length() > 0) {
                sbBuf.append("長谷川式 = ");
                sbBuf.append(getDataString(map, "HASE_SCORE"));
                sbBuf.append("点 (");
                sbBuf.append(getDataString(map, "HASE_SCR_DT"));
                sbBuf.append(")");
            }
            if (getDataString(map, "P_HASE_SCORE").length() > 0) {
                sbBuf.append(" (前回 ");
                sbBuf.append(getDataString(map, "P_HASE_SCORE"));
                sbBuf.append("点 (");
                sbBuf.append(getDataString(map, "P_HASE_SCR_DT"));
                sbBuf.append("))");
            }
            String hase = sbBuf.toString();
            if (hase.length() > 0) {
                hase += "";
            }
            sbBuf = new StringBuffer();
            if (getDataString(map, "INST_SEL_PR1").length() > 0) {
                sbBuf.append("施設選択(優先度) 1. ");
                String tmpPr = getDataString(map, "INST_SEL_PR1");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 16 - tmpPr.length())); // 16文字にする
            }
            if (getDataString(map, "INST_SEL_PR2").length() > 0) {
                sbBuf.append("2. ");
                String tmpPr = getDataString(map, "INST_SEL_PR2");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 15 - tmpPr.length())); // 15文字にする
            }
            String pr = sbBuf.toString();
            if (pr.length() > 0) {
                pr += "";
            }
            
            // 2006/06/22
            // CRLF - 置換対応
            // Replace - begin [Masahiko Higuchi]
            row.add(hase + pr
                    + (getDataString(map, "IKN_TOKKI").replaceAll("\r\n",VT)).replaceAll("\n", VT));
            // Replace - end
            
            // 1レコード追加
            cvs.addRow(row);

            // プログレスバー進行処理
            iwf.setProgressValue(i);
        }

        // CSV出力
        try {
            // 書き出し
            cvs.write(true, true);
        } catch (Exception ex) {
            return false;
        }

        iwf.setProgressValue(max);
        return true;
    }

    /**
     * CSV出力処理を行います。(医師医見書フォーマット：I1.0)
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     * @return 成功したか
     */
    public boolean doOutputCSVIshi(VRArrayList data, File file)
			throws Exception {

		// add begin 2006/08/03 kamitsukasa

		// プログレス生成
		IkenshoWaitingForm iwf = new IkenshoWaitingForm(ACFrame.getInstance(),
				"CSVファイル出力中");

		// 出力対象でないデータを間引く
		for (int i = data.getDataSize() - 1; i >= 0; i--) {
			VRMap map = (VRMap) data.getData(i);
			if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
				data.remove(i);
			}
		}

		 // CSV出力用のデータをDBから取得する
		 IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		 VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSqlIshi());
		 // CSV出力処理(下準備)
		 int max = data.getDataSize();
		 iwf.setMaxCount(max); // プログレスバー最大値
		 StringBuffer sbBuf = new StringBuffer();
		 String softName = IkenshoCommon.getProperity("Version/SoftName");
		 VRCSVFile csv = new VRCSVFile(file.getPath()); // CSV生成
		 iwf.setVisible(true); // プログレスバー表示

//		// debug begin TODO 削除予定
//		// CSV出力用のデータをDBから取得する
//		VRArrayList dataDB = TestPrintClass.makeDataVR("");
//		// CSV出力処理(下準備)
//		int max = dataDB.getDataSize();
//		iwf.setMaxCount(max); // プログレスバー最大値
//		StringBuffer sbBuf = new StringBuffer();
//		String softName = IkenshoCommon.getProperity("Version/SoftName");
//		VRCSVFile csv = new VRCSVFile(file.getPath()); // CSV生成
//		iwf.setVisible(true); // プログレスバー表示
//		// debug end

		// CSV出力処理
		for (int i = 0; i < max; i++) {
			 VRMap tmp = (VRMap) data.getData(i);
			 String insuredNo =
			 convertInsuredNo(ACCastUtilities.toString(tmp.getData("INSURED_NO")));
			 String patientNo =
			 ACCastUtilities.toString(tmp.getData("PATIENT_NO"));
			 String edaNo = ACCastUtilities.toString(tmp.getData("EDA_NO"));
			 int idx = matchingData(dataDB, patientNo, edaNo);
			 if (idx < 0) {
				 continue;
			 }
//			// debug begin TODO 削除予定
//			VRMap tmp = (VRMap) dataDB.getData(i);
//			String insuredNo = convertInsuredNo(ACCastUtilities.toString(tmp
//					.getData("INSURED_NO")));
//			int idx = i;
//			// debug end
			VRMap map = (VRMap) dataDB.getData(idx);

			// 行生成
			ArrayList row = new ArrayList();
			// 001:FormatVersion
			row.add("I1.0");
			// 002:SoftName
			row.add(softName);
			// 003:タイムスタンプ
			row.add(insuredNo + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
			// 004:保険者番号
			row.add(getDataString(map, "INSURER_NO"));
			// 005:保険者名称
			row.add(getDataString(map, "INSURER_NM"));
			// 006:被保険者番号
			row.add(insuredNo);
			// 007:事業所番号
			row.add(getDataString(map, "JIGYOUSHA_NO"));
			// 008:申請日
			row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
			// 009:作成依頼日
			row.add(formatYYYYMMDD(map.getData("REQ_DT")));
			// 010:送付日
			row.add(formatYYYYMMDD(map.getData("SEND_DT")));
			// 011:依頼番号
			row.add(getDataString(map, "REQ_NO"));
			// 012:医師番号
			row.add(getDataString(map, "DR_NO"));
			// 013:種別
			row.add(getDataString(map, "KIND"));
			// 014:記入日
			row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

			// 015:患者‐名かな
			row.add(getDataString(map, "PATIENT_KN"));
			// 016:患者‐名
			row.add(getDataString(map, "PATIENT_NM"));
			// 017:患者‐生年月日
			row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
			// 018:患者‐年齢
			row.add(getDataString(map, "AGE"));
			// 019:患者‐性別
			row.add(getDataString(map, "SEX"));
			// 020:患者‐郵便番号
			row.add(getDataString(map, "POST_CD"));
			// 021:患者‐住所
			row.add(getDataString(map, "ADDRESS"));
			// 022:患者‐連絡先電話番号
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "TEL1"));
			if (getDataString(map, "TEL1").length() > 0) {
				if (getDataString(map, "TEL2").length() > 0) {
					sbBuf.append("-");
				}
			}
			sbBuf.append(getDataString(map, "TEL2"));
			row.add(sbBuf.toString());
			// 023:医師氏名
			row.add(getDataString(map, "DR_NM"));
			// 024:医療機関名
			row.add(getDataString(map, "MI_NM"));
			// 025:医療機関‐郵便番号
			row.add(getDataString(map, "MI_POST_CD"));
			// 026:医療機関‐所在地
			row.add(getDataString(map, "MI_ADDRESS"));
			// 027:医療機関‐電話番号
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "MI_TEL1"));
			if (getDataString(map, "MI_TEL1").length() > 0) {
				if (getDataString(map, "MI_TEL2").length() > 0) {
					sbBuf.append("-");
				}
			}
			sbBuf.append(getDataString(map, "MI_TEL2"));
			row.add(sbBuf.toString());
			// 028:医療機関‐FAX番号
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "MI_FAX1"));
			if (getDataString(map, "MI_FAX1").length() > 0) {
				if (getDataString(map, "MI_FAX2").length() > 0) {
					sbBuf.append("-");
				}
			}
			sbBuf.append(getDataString(map, "MI_FAX2"));
			row.add(sbBuf.toString());
			// 029:医師の同意
			row.add(getDataString(map, "DR_CONSENT"));
			// 030:最終診察日
			row.add(formatYYYYMMDD(map.getData("LASTDAY")));
			// 031:意見書作成回数
			row.add(getDataString(map, "IKN_CREATE_CNT"));
			// 032:他科受診の有無
			if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA", map)) == 0) {
				if ((getDataString(map, "TAKA_OTHER")).length() <= 0) {
					row.add("2");
				} else {
					row.add("1");
				}
			} else {
				row.add("1");
			}
			// 033:他科名
			sbBuf = new StringBuffer();
			sbBuf.append(getBitFromRonriwa(getDataString(map, "TAKA"), 12));
			if (isNullText(getDataString(map, "TAKA_OTHER"))) {
				sbBuf.append("0");
			} else {
				sbBuf.append("1");
			}
			row.add(sbBuf.toString());
			// 034:その他の他科名
			row.add(getDataString(map, "TAKA_OTHER"));

			// 035:診断名1
			row.add(getDataString(map, "SINDAN_NM1"));
			// 036:発症年月日1
			String temp = getDataString(map, "SHUSSEI1");
			if ("1".equals(temp)) {
				row.add("");
			} else {
				row.add(formatUnknownDateCustom(map.getData("HASHOU_DT1")));
			}
			// 037:出生時1
			row.add(temp);
			// 038:診断名2
			row.add(getDataString(map, "SINDAN_NM2"));
			// 039:発症年月日2
			temp = getDataString(map, "SHUSSEI2");
			if ("1".equals(temp)) {
				row.add("");
			} else {
				row.add(formatUnknownDateCustom(map.getData("HASHOU_DT2")));
			}
			// 040:出生時2
			row.add(temp);
			// 041:診断名3
			row.add(getDataString(map, "SINDAN_NM3"));
			// 042:発症年月日3
			temp = getDataString(map, "SHUSSEI3");
			if ("1".equals(temp)) {
				row.add("");
			} else {
				row.add(formatUnknownDateCustom(map.getData("HASHOU_DT3")));
			}
			// 043:出生時3
			row.add(temp);
			// 044:入院暦1開始
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_STA1")));
			// 045:入院暦1終了
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_END1")));
			// 046:入院暦1傷病名
			row.add(getDataString(map, "NYUIN_NM1"));
			// 047:入院暦2開始
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_STA2")));
			// 048:入院暦2終了
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_END2")));
			// 049:入院暦2傷病名
			row.add(getDataString(map, "NYUIN_NM2"));
			// 050:症状としての安定性
			row.add(getDataString(map, "SHJ_ANT"));
			// 051:症状不安定の具体的状況
			row.add(getDataString(map, "INSECURE_CONDITION"));
			// 052:疾病の経過‐治療内容‐治療状態
			sbBuf = new StringBuffer();
			if (getDataString(map, "MT_STS").length() > 0) {
				sbBuf.append((getDataString(map, "MT_STS").replaceAll("\r\n",
						VT)).replaceAll("\n", VT)); // 0x0B
				sbBuf.append("");
			}
			if ((getDataString(map, "MEDICINE1").length()
					+ getDataString(map, "DOSAGE1").length()
					+ getDataString(map, "UNIT1").length()
					+ getDataString(map, "USAGE1").length()
					+ getDataString(map, "MEDICINE2").length()
					+ getDataString(map, "DOSAGE2").length()
					+ getDataString(map, "UNIT2").length() + getDataString(map,
					"USAGE2").length()) > 0) {
				sbBuf.append(getDataString(map, "MEDICINE1") + " ");
				sbBuf.append(getDataString(map, "DOSAGE1"));
				sbBuf.append(getDataString(map, "UNIT1") + " ");
				sbBuf.append(getDataString(map, "USAGE1") + " / ");
				sbBuf.append(getDataString(map, "MEDICINE2") + " ");
				sbBuf.append(getDataString(map, "DOSAGE2"));
				sbBuf.append(getDataString(map, "UNIT2") + " ");
				sbBuf.append(getDataString(map, "USAGE2") + "");
			}
			if ((getDataString(map, "MEDICINE3").length()
					+ getDataString(map, "DOSAGE3").length()
					+ getDataString(map, "UNIT3").length()
					+ getDataString(map, "USAGE3").length()
					+ getDataString(map, "MEDICINE4").length()
					+ getDataString(map, "DOSAGE4").length()
					+ getDataString(map, "UNIT4").length() + getDataString(map,
					"USAGE4").length()) > 0) {
				sbBuf.append(getDataString(map, "MEDICINE3") + " ");
				sbBuf.append(getDataString(map, "DOSAGE3"));
				sbBuf.append(getDataString(map, "UNIT3") + " ");
				sbBuf.append(getDataString(map, "USAGE3") + " / ");
				sbBuf.append(getDataString(map, "MEDICINE4") + " ");
				sbBuf.append(getDataString(map, "DOSAGE4"));
				sbBuf.append(getDataString(map, "UNIT4") + " ");
				sbBuf.append(getDataString(map, "USAGE4") + "");
			}
			if ((getDataString(map, "MEDICINE5").length()
					+ getDataString(map, "DOSAGE5").length()
					+ getDataString(map, "UNIT5").length()
					+ getDataString(map, "USAGE5").length()
					+ getDataString(map, "MEDICINE6").length()
					+ getDataString(map, "DOSAGE6").length()
					+ getDataString(map, "UNIT6").length() + getDataString(map,
					"USAGE6").length()) > 0) {
				sbBuf.append(getDataString(map, "MEDICINE5") + " ");
				sbBuf.append(getDataString(map, "DOSAGE5"));
				sbBuf.append(getDataString(map, "UNIT5") + " ");
				sbBuf.append(getDataString(map, "USAGE5") + " / ");
				sbBuf.append(getDataString(map, "MEDICINE6") + " ");
				sbBuf.append(getDataString(map, "DOSAGE6"));
				sbBuf.append(getDataString(map, "UNIT6") + " ");
				sbBuf.append(getDataString(map, "USAGE6") + "");
			}
			if (sbBuf.length() > 0) {
				sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // 最後の0x0Bを削除
			}
			row.add(sbBuf.toString());

			// 053:処置内容
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "TNT_KNR"));
			sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
			sbBuf.append(getDataString(map, "TOUSEKI"));
			sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
			sbBuf.append(getDataString(map, "OX_RYO"));
			sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
			sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
			sbBuf.append(getDataString(map, "TOUTU"));
			sbBuf.append(getDataString(map, "KEKN_EIYOU"));
			sbBuf.append(getDataString(map, "KYUIN_SHOCHI"));
			row.add(sbBuf.toString());
			// 054:処置内容‐吸引処置回数
			row.add(getDataString(map, "KYUIN_SHOCHI_CNT"));
			// 055:処置内容‐吸引処置時期
			row.add(getDataString(map, "KYUIN_SHOCHI_JIKI"));
			// 056:特別な対応
			row.add(getDataString(map, "MONITOR")
					+ getDataString(map, "JOKUSOU_SHOCHI"));
			// 057:失禁への対応
			row.add(getDataString(map, "CATHETER"));

			// 058:行動上の障害の有無‐有無
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "KS_CHUYA"));
			sbBuf.append(getDataString(map, "KS_BOUGEN"));
			sbBuf.append(getDataString(map, "KS_BOUKOU"));
			sbBuf.append(getDataString(map, "KS_TEIKOU"));
			sbBuf.append(getDataString(map, "KS_HAIKAI"));
			sbBuf.append(getDataString(map, "KS_FUSIMATU"));
			sbBuf.append(getDataString(map, "KS_FUKETU"));
			sbBuf.append(getDataString(map, "KS_ISHOKU"));
			sbBuf.append(getDataString(map, "KS_SEITEKI_MONDAI"));
			sbBuf.append(getDataString(map, "KS_OTHER"));
			if ("0000000000".equals(sbBuf.toString())) {
				row.add("2");
			} else {
				row.add("1");
			}
			// 059:行動上の障害の有無‐詳細
			row.add(sbBuf.toString());
			// 060:行動上の障害の有無‐その他内容
			row.add(getDataString(map, "KS_OTHER_NM"));
			// 061:精神・神経症状の有無‐有無
			row.add(getDataString(map, "SEISIN"));
			// 062:精神・神経症状の有無‐症状名
			row.add(getDataString(map, "SEISIN_NM"));
			// 063:精神・神経症状の有無‐詳細
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "SS_SENMO"));
			sbBuf.append(getDataString(map, "SS_KEMIN_KEIKO"));
			sbBuf.append(getDataString(map, "SS_GNS_GNC"));
			sbBuf.append(getDataString(map, "SS_MOUSOU"));
			sbBuf.append(getDataString(map, "SS_SHIKKEN_TOSHIKI"));
			sbBuf.append(getDataString(map, "SS_SHITUNIN"));
			sbBuf.append(getDataString(map, "SS_SHIKKO"));
			sbBuf.append(getDataString(map, "SS_NINCHI_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_KIOKU_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_CHUI_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_SUIKOU_KINO_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_SHAKAITEKI_KODO_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_OTHER"));
			row.add(sbBuf.toString());
			// 064:精神・神経症状の有無‐記憶障害（短期・長期）
			row.add(getDataString(map, "SS_KIOKU_SHOGAI_TANKI")
					+ getDataString(map, "SS_KIOKU_SHOGAI_CHOUKI"));
			// 065:精神・神経症状の有無‐その他内容
			row.add(getDataString(map, "SS_OTHER_NM"));
			// 066:精神・神経症状の有無‐専門医受診有無
			row.add(getDataString(map, "SENMONI"));
			// 067:精神・神経症状の有無‐専門医受診科名
			row.add(getDataString(map, "SENMONI_NM"));

			// 068:てんかん‐有無
			row.add(getDataString(map, "TENKAN"));
			// 069:てんかん‐頻度
			row.add(getDataString(map, "TENKAN_HINDO"));
			// 070:利き腕
			row.add(getDataString(map, "KIKIUDE"));
			// 071:身長
			row.add(getDataString(map, "HEIGHT"));
			// 072:体重
			row.add(getDataString(map, "WEIGHT"));
			// 073:過去6ヶ月の体重の変化
			row.add(getDataString(map, "WEIGHT_CHANGE"));
			// 074:四肢欠損
			row.add(getDataString(map, "SISIKESSON"));
			// 075:四肢欠損部位
			row.add(getDataString(map, "SISIKESSON_BUI"));
			// 076:四肢欠損程度
			row.add(getDataString(map, "SISIKESSON_TEIDO"));
			// 077:麻痺
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "MAHI_LEFTARM"));
			sbBuf.append(getDataString(map, "MAHI_LOWERLEFTLIMB"));
			sbBuf.append(getDataString(map, "MAHI_RIGHTARM"));
			sbBuf.append(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
			sbBuf.append(getDataString(map, "MAHI_ETC"));
			if ("00000".equals(sbBuf.toString())) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 078:麻痺‐左上肢
			row.add(getDataString(map, "MAHI_LEFTARM"));
			// 079:麻痺‐左上肢‐程度
			row.add(getDataString(map, "MAHI_LEFTARM_TEIDO"));
			// 080:麻痺‐左下肢
			row.add(getDataString(map, "MAHI_LOWERLEFTLIMB"));
			// 081:麻痺‐左下肢‐程度
			row.add(getDataString(map, "MAHI_LOWERLEFTLIMB_TEIDO"));
			// 082:麻痺‐右上肢
			row.add(getDataString(map, "MAHI_RIGHTARM"));
			// 083:麻痺‐右上肢‐程度
			row.add(getDataString(map, "MAHI_RIGHTARM_TEIDO"));
			// 084:麻痺‐右下肢
			row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
			// 085:麻痺‐右下肢‐程度
			row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB_TEIDO"));
			// 086:麻痺‐その他
			row.add(getDataString(map, "MAHI_ETC"));
			// 087:麻痺‐その他‐部位
			row.add(getDataString(map, "MAHI_ETC_BUI"));
			// 088:麻痺‐その他‐程度
			row.add(getDataString(map, "MAHI_ETC_TEIDO"));
			// 089:筋力の低下
			row.add(getDataString(map, "KINRYOKU_TEIKA"));
			// 090:筋力の低下‐部位
			row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
			// 091:筋力の低下‐程度
			row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
			// 092:関節の拘縮
			String tempKata = getDataString(map, "KATA_KOUSHU_MIGI")
					+ getDataString(map, "KATA_KOUSHU_HIDARI");
			String tempMata = getDataString(map, "MATA_KOUSHU_MIGI")
					+ getDataString(map, "MATA_KOUSHU_HIDARI");
			String tempHiji = getDataString(map, "HIJI_KOUSHU_MIGI")
					+ getDataString(map, "HIJI_KOUSHU_HIDARI");
			String tempHiza = getDataString(map, "HIZA_KOUSHU_MIGI")
					+ getDataString(map, "HIZA_KOUSHU_HIDARI");
			if ("00".equals(tempKata) && "00".equals(tempMata)
					&& "00".equals(tempHiji) && "00".equals(tempHiza)
					&& "0".equals(getDataString(map, "KOUSHU_ETC"))) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 093:肩関節拘縮
			if ("00".equals(tempKata)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 094:肩関節拘縮右
			row.add(getDataString(map, "KATA_KOUSHU_MIGI"));
			// 095:肩関節拘縮右程度
			row.add(getDataString(map, "KATA_KOUSHU_MIGI_TEIDO"));
			// 096:肩関節拘縮左
			row.add(getDataString(map, "KATA_KOUSHU_HIDARI"));
			// 097:肩関節拘縮左程度
			row.add(getDataString(map, "KATA_KOUSHU_HIDARI_TEIDO"));
			// 098:股関節拘縮
			if ("00".equals(tempMata)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 099:股関節拘縮右
			row.add(getDataString(map, "MATA_KOUSHU_MIGI"));
			// 100:股関節拘縮右程度
			row.add(getDataString(map, "MATA_KOUSHU_MIGI_TEIDO"));
			// 101:股関節拘縮左
			row.add(getDataString(map, "MATA_KOUSHU_HIDARI"));
			// 102:股関節拘縮左程度
			row.add(getDataString(map, "MATA_KOUSHU_HIDARI_TEIDO"));
			// 103:肘関節拘縮
			if ("00".equals(tempHiji)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 104:肘関節拘縮右
			row.add(getDataString(map, "HIJI_KOUSHU_MIGI"));
			// 105:肘関節拘縮右程度
			row.add(getDataString(map, "HIJI_KOUSHU_MIGI_TEIDO"));
			// 106:肘関節拘縮左
			row.add(getDataString(map, "HIJI_KOUSHU_HIDARI"));
			// 107:肘関節拘縮左程度
			row.add(getDataString(map, "HIJI_KOUSHU_HIDARI_TEIDO"));
			// 108:膝関節拘縮
			if ("00".equals(tempHiza)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 109:膝関節拘縮右
			row.add(getDataString(map, "HIZA_KOUSHU_MIGI"));
			// 110:膝関節拘縮右程度
			row.add(getDataString(map, "HIZA_KOUSHU_MIGI_TEIDO"));
			// 111:膝関節拘縮左
			row.add(getDataString(map, "HIZA_KOUSHU_HIDARI"));
			// 112:膝関節拘縮左程度
			row.add(getDataString(map, "HIZA_KOUSHU_HIDARI_TEIDO"));
			// 113:関節の拘縮その他
			row.add(getDataString(map, "KOUSHU_ETC"));
			// 114:関節の拘縮その他部位
			row.add(getDataString(map, "KOUSHU_ETC_BUI"));
			// 115:関節の痛み
			row.add(getDataString(map, "KANSETU_ITAMI"));
			// 116:関節の痛み‐部位
			row.add(getDataString(map, "KANSETU_ITAMI_BUI"));
			// 117:関節の痛み‐程度
			row.add(getDataString(map, "KANSETU_ITAMI_TEIDO"));
			// 118:失調・不随意運動
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
			sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
			sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
			sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
			sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
			sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
			if ("000000".equals(sbBuf.toString())) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 119:失調・不随意運動・上肢・右
			row.add(getDataString(map, "JOUSI_SICCHOU_MIGI"));
			// 120:失調・不随意運動・上肢・右・程度
			row.add(getDataString(map, "JOUSI_SICCHOU_MIGI_TEIDO"));
			// 121:失調・不随意運動・上肢・左
			row.add(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
			// 122:失調・不随意運動・上肢・左・程度
			row.add(getDataString(map, "JOUSI_SICCHOU_HIDARI_TEIDO"));
			// 123:失調・不随意運動・体幹・右
			row.add(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
			// 124:失調・不随意運動・体幹・右・程度
			row.add(getDataString(map, "TAIKAN_SICCHOU_MIGI_TEIDO"));
			// 125:失調・不随意運動・体幹・左
			row.add(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
			// 126:失調・不随意運動・体幹・左・程度
			row.add(getDataString(map, "TAIKAN_SICCHOU_HIDARI_TEIDO"));
			// 127:失調・不随意運動・下肢・右
			row.add(getDataString(map, "KASI_SICCHOU_MIGI"));
			// 128:失調・不随意運動・下肢・右・程度
			row.add(getDataString(map, "KASI_SICCHOU_MIGI_TEIDO"));
			// 129:失調・不随意運動・下肢・左
			row.add(getDataString(map, "KASI_SICCHOU_HIDARI"));
			// 130:失調・不随意運動・下肢・左・程度
			row.add(getDataString(map, "KASI_SICCHOU_HIDARI_TEIDO"));
			// 131:褥瘡
			row.add(getDataString(map, "JOKUSOU"));
			// 132:褥瘡‐部位
			row.add(getDataString(map, "JOKUSOU_BUI"));
			// 133:褥瘡‐程度
			row.add(getDataString(map, "JOKUSOU_TEIDO"));
			// 134:その他の皮膚疾患
			row.add(getDataString(map, "HIFUSIKKAN"));
			// 135:その他の皮膚疾患‐部位
			row.add(getDataString(map, "HIFUSIKKAN_BUI"));
			// 136:その他の皮膚疾患‐程度
			row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));

			// 137:現在あるかまたは今後発生の高い状態とその対処方針(状態)
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "NYOUSIKKIN"));
			sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
			sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
			sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
			sbBuf.append(getDataString(map, "ENGESEIHAIEN"));
			sbBuf.append(getDataString(map, "CHOUHEISOKU"));
			sbBuf.append(getDataString(map, "EKIKANKANSEN"));
			sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
			sbBuf.append(getDataString(map, "ITAMI"));
			sbBuf.append(getDataString(map, "DASSUI"));
			sbBuf.append(getDataString(map, "BYOUTAITA"));
			row.add(sbBuf.toString());
			// 138:現在あるかまたは今後発生の高い状態とその対処方針(状態)・その他内容
			row.add(getDataString(map, "BYOUTAITA_NM"));
			// 139:現在あるかまたは今後発生の高い状態とその対処方針(対処方針)
		    sbBuf = new StringBuffer();
		    VRList words = new VRArrayList();
		    poolString(map, words, new String[] { 
		    		"NYOUSIKKIN_TAISHO_HOUSIN",
					"TENTOU_KOSSETU_TAISHO_HOUSIN",
					"HAIKAI_KANOUSEI_TAISHO_HOUSIN",
					"JOKUSOU_KANOUSEI_TAISHO_HOUSIN",
					"ENGESEIHAIEN_TAISHO_HOUSIN", 
					"CHOUHEISOKU_TAISHO_HOUSIN",
					"EKIKANKANSEN_TAISHO_HOUSIN",
					"SINPAIKINOUTEIKA_TAISHO_HOUSIN", 
					"ITAMI_TAISHO_HOUSIN",
					"DASSUI_TAISHO_HOUSIN", 
					"BYOUTAITA_TAISHO_HOUSIN" });
		    
		    if (words.size() > 0) {
				// 対処方針を文字単位で連結して表示可能なところまで。
				final int MAX_LENGTH = 89;

				int inlineSize = 0;
				sbBuf = new StringBuffer();
				int end = words.size() - 1;
				for (int j = 0; j < end; j++) {
					String text = ACCastUtilities.toString(words.get(j));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '。') && (c != '、')) {
						line.append("、");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// 出力可能なところまで追加
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// 行終了チェック
								break;
							}
						}
						break;
					}
					inlineSize += wordSize;
					sbBuf.append(line.toString());
				}
				if (inlineSize <= MAX_LENGTH) {
					// 末尾追加
					String text = ACCastUtilities.toString(words.get(end));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '。') && (c != '、')) {
						line.append("。");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// 出力可能なところまで追加
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// 行終了チェック
								break;
							}
						}
					} else {
						sbBuf.append(line.toString());
					}
				}
			}
			row.add(sbBuf.toString());		    
			
			// 140:医学的観点からの留意事項・血圧
			row.add(getDataString(map, "KETUATU"));
			// 141:医学的観点からの留意事項・血圧・留意事項
			row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
			// 142:医学的観点からの留意事項・嚥下
			row.add(getDataString(map, "ENGE"));
			// 143:医学的観点からの留意事項・嚥下・留意事項
			row.add(getDataString(map, "ENGE_RYUIJIKOU"));
			// 144:医学的観点からの留意事項・摂食
			row.add(getDataString(map, "SESHOKU"));
			// 145:医学的観点からの留意事項・摂食・留意事項
			row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
			// 146:医学的観点からの留意事項・移動
			row.add(getDataString(map, "IDOU"));
			// 147:医学的観点からの留意事項・移動・留意事項
			row.add(getDataString(map, "IDOU_RYUIJIKOU"));
			// 148:医学的観点からの留意事項・その他
			row.add(getDataString(map, "KAIGO_OTHER"));
			// 149:感染症の有無
			row.add(getDataString(map, "KANSENSHOU"));
			// 150:感染症の有無・詳細
			row.add(getDataString(map, "KANSENSHOU_NM"));

			// 151:特記事項
			row.add((getDataString(map, "IKN_TOKKI").replaceAll("\r\n", VT))
					.replaceAll("\n", VT));
			// 152:二軸評価:精神症状
			temp = getDataString(map, "SK_NIJIKU_SEISHIN");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 153:二軸評価:能力障害
			temp = getDataString(map, "SK_NIJIKU_NORYOKU");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 154:二軸評価:判定時期
			row.add(formatUnknownDateCustom(map.getData("SK_NIJIKU_DT")));
			// 155:生活障害評価:食事
			temp = getDataString(map, "SK_SEIKATSU_SHOKUJI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 156:生活障害評価:生活リズム
			temp = getDataString(map, "SK_SEIKATSU_RHYTHM");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 157:生活障害評価:保清
			temp = getDataString(map, "SK_SEIKATSU_HOSEI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 158:生活障害評価:金銭管理
			temp = getDataString(map, "SK_SEIKATSU_KINSEN_KANRI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 159:生活障害評価:服薬管理
			temp = getDataString(map, "SK_SEIKATSU_HUKUYAKU_KANRI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 160:生活障害評価:対人関係
			temp = getDataString(map, "SK_SEIKATSU_TAIJIN_KANKEI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 161:生活障害評価:社会的適応を妨げる行動
			temp = getDataString(map, "SK_SEIKATSU_SHAKAI_TEKIOU");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 162:生活障害評価:判断時期
			row.add(formatUnknownDateCustom(map.getData("SK_SEIKATSU_DT")));

			// 1レコード追加
			csv.addRow(row);

			// プログレスバー進行処理
			iwf.setProgressValue(i);

		}

		// CSV出力
		try {
			// 書き出し
			csv.write(true, true);
		} catch (Exception ex) {
			return false;
		}

		iwf.setProgressValue(max);
		return true;

		// add end 2006/08/03 kamitsukasa

	}
    
    /**
     * 複数の文字列をmapから抜き出し、listに格納する関数。
     * @param data データ
     * @param target 格納先list
     * @param keys 文字列のKEY
     */
    private void poolString(VRMap data, VRList target, String[] keys) throws Exception{
    	
    	if(keys == null){
    		return;
    	}
    	
    	for(int i = 0; i < keys.length; i++){
    		String temp = ACCastUtilities.toString(VRBindPathParser.get(keys[i], data));
    		if(temp.length() > 0){
    			target.add(temp);
    		}
    	}
    	
    }
    
    /**
	 * CSV出力に必要なデータを取得するSQL文を取得します。
	 * 
	 * @return String
	 */
    private String getSqlOld() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURER_NM");
        sb.append(",IKN_BILL.JIGYOUSHA_NO");
        sb.append(",IKN_BILL.SINSEI_DT");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.SEND_DT");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_BILL.DR_NO");
        sb.append(",IKN_ORIGIN.KIND");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",COMMON_IKN_SIS.POST_CD");
        sb.append(",COMMON_IKN_SIS.ADDRESS");
        sb.append(",COMMON_IKN_SIS.TEL1");
        sb.append(",COMMON_IKN_SIS.TEL2");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.MI_NM");
        sb.append(",COMMON_IKN_SIS.MI_POST_CD");
        sb.append(",COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(",COMMON_IKN_SIS.MI_TEL1");
        sb.append(",COMMON_IKN_SIS.MI_TEL2");
        sb.append(",COMMON_IKN_SIS.MI_FAX1");
        sb.append(",COMMON_IKN_SIS.MI_FAX2");
        sb.append(",IKN_ORIGIN.DR_CONSENT");
        sb.append(",IKN_ORIGIN.LASTDAY");
        sb.append(",IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(",IKN_ORIGIN.TAKA");
        sb.append(",IKN_ORIGIN.TAKA_OTHER");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(",COMMON_IKN_SIS.SHJ_ANT");
        sb.append(",COMMON_IKN_SIS.YKG_YOGO");
        sb.append(",COMMON_IKN_SIS.MT_STS");
        sb.append(",COMMON_IKN_SIS.MEDICINE1");
        sb.append(",COMMON_IKN_SIS.DOSAGE1");
        sb.append(",COMMON_IKN_SIS.UNIT1");
        sb.append(",COMMON_IKN_SIS.USAGE1");
        sb.append(",COMMON_IKN_SIS.MEDICINE2");
        sb.append(",COMMON_IKN_SIS.DOSAGE2");
        sb.append(",COMMON_IKN_SIS.UNIT2");
        sb.append(",COMMON_IKN_SIS.USAGE2");
        sb.append(",COMMON_IKN_SIS.MEDICINE3");
        sb.append(",COMMON_IKN_SIS.DOSAGE3");
        sb.append(",COMMON_IKN_SIS.UNIT3");
        sb.append(",COMMON_IKN_SIS.USAGE3");
        sb.append(",COMMON_IKN_SIS.MEDICINE4");
        sb.append(",COMMON_IKN_SIS.DOSAGE4");
        sb.append(",COMMON_IKN_SIS.UNIT4");
        sb.append(",COMMON_IKN_SIS.USAGE4");
        sb.append(",COMMON_IKN_SIS.MEDICINE5");
        sb.append(",COMMON_IKN_SIS.DOSAGE5");
        sb.append(",COMMON_IKN_SIS.UNIT5");
        sb.append(",COMMON_IKN_SIS.USAGE5");
        sb.append(",COMMON_IKN_SIS.MEDICINE6");
        sb.append(",COMMON_IKN_SIS.DOSAGE6");
        sb.append(",COMMON_IKN_SIS.UNIT6");
        sb.append(",COMMON_IKN_SIS.USAGE6");
        sb.append(",COMMON_IKN_SIS.TNT_KNR");
        sb.append(",COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(",COMMON_IKN_SIS.TOUSEKI");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(",COMMON_IKN_SIS.OX_RYO");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(",COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(",COMMON_IKN_SIS.TOUTU");
        sb.append(",COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(",COMMON_IKN_SIS.MONITOR");
        sb.append(",COMMON_IKN_SIS.JOKUSOU_SHOCHI");
        sb.append(",COMMON_IKN_SIS.RYU_CATHETER");
        sb.append(",COMMON_IKN_SIS.NETAKIRI");
        sb.append(",COMMON_IKN_SIS.CHH_STS");
        sb.append(",IKN_ORIGIN.TANKI_KIOKU");
        sb.append(",IKN_ORIGIN.NINCHI");
        sb.append(",IKN_ORIGIN.DENTATU");
        sb.append(",IKN_ORIGIN.SHOKUJI");
        sb.append(",IKN_ORIGIN.GNS_GNC");
        sb.append(",IKN_ORIGIN.MOUSOU");
        sb.append(",IKN_ORIGIN.CHUYA");
        sb.append(",IKN_ORIGIN.BOUGEN");
        sb.append(",IKN_ORIGIN.BOUKOU");
        sb.append(",IKN_ORIGIN.TEIKOU");
        sb.append(",IKN_ORIGIN.HAIKAI");
        sb.append(",IKN_ORIGIN.FUSIMATU");
        sb.append(",IKN_ORIGIN.FUKETU");
        sb.append(",IKN_ORIGIN.ISHOKU");
        sb.append(",IKN_ORIGIN.SEITEKI_MONDAI");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER_NM");
        sb.append(",IKN_ORIGIN.SEISIN");
        sb.append(",IKN_ORIGIN.SEISIN_NM");
        sb.append(",IKN_ORIGIN.SENMONI");
        sb.append(",IKN_ORIGIN.SENMONI_NM");
        sb.append(",IKN_ORIGIN.KIKIUDE");
        sb.append(",IKN_ORIGIN.WEIGHT");
        sb.append(",IKN_ORIGIN.HEIGHT");
        sb.append(",IKN_ORIGIN.SISIKESSON");
        sb.append(",IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(",IKN_ORIGIN.SISIKESSON_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI");
        sb.append(",IKN_ORIGIN.MAHI_BUI");
        sb.append(",IKN_ORIGIN.MAHI_TEIDO");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(",IKN_ORIGIN.JOKUSOU");
        sb.append(",IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(",IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(",IKN_ORIGIN.KATA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.KATA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.HIJI_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.HIJI_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.MATA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.MATA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.HIZA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.HIZA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(",IKN_ORIGIN.ENGESEIHAIEN");
        sb.append(",IKN_ORIGIN.CHOUHEISOKU");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(",IKN_ORIGIN.ITAMI");
        sb.append(",IKN_ORIGIN.DASSUI");
        sb.append(",IKN_ORIGIN.BYOUTAITA");
        sb.append(",IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.ITAMI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO_UL");
        sb.append(",IKN_ORIGIN.HOUMON_REHA");
        sb.append(",IKN_ORIGIN.HOUMON_REHA_UL");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA_UL");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_NM");
        sb.append(",IKN_ORIGIN.KETUATU");
        sb.append(",IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.SESHOKU");
        sb.append(",IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.ENGE");
        sb.append(",IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.IDOU");
        sb.append(",IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.KAIGO_OTHER");
        sb.append(",IKN_ORIGIN.KANSENSHOU");
        sb.append(",IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(",IKN_ORIGIN.HASE_SCORE");
        sb.append(",IKN_ORIGIN.HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.P_HASE_SCORE");
        sb.append(",IKN_ORIGIN.P_HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.INST_SEL_PR1");
        sb.append(",IKN_ORIGIN.INST_SEL_PR2");
        sb.append(",IKN_ORIGIN.IKN_TOKKI");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.FORMAT_KBN=0");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        return sb.toString();
    }

    /**
     * CSV出力に必要なデータを取得するSQL文を取得します。
     *
     * @return String
     */
    private String getSqlNew() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURER_NM");
        sb.append(",IKN_BILL.JIGYOUSHA_NO");
        sb.append(",IKN_BILL.SINSEI_DT");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.SEND_DT");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_BILL.DR_NO");
        sb.append(",IKN_ORIGIN.KIND");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",COMMON_IKN_SIS.POST_CD");
        sb.append(",COMMON_IKN_SIS.ADDRESS");
        sb.append(",COMMON_IKN_SIS.TEL1");
        sb.append(",COMMON_IKN_SIS.TEL2");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.MI_NM");
        sb.append(",COMMON_IKN_SIS.MI_POST_CD");
        sb.append(",COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(",COMMON_IKN_SIS.MI_TEL1");
        sb.append(",COMMON_IKN_SIS.MI_TEL2");
        sb.append(",COMMON_IKN_SIS.MI_FAX1");
        sb.append(",COMMON_IKN_SIS.MI_FAX2");
        sb.append(",IKN_ORIGIN.DR_CONSENT");
        sb.append(",IKN_ORIGIN.LASTDAY");
        sb.append(",IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(",IKN_ORIGIN.TAKA");
        sb.append(",IKN_ORIGIN.TAKA_OTHER");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(",COMMON_IKN_SIS.SHJ_ANT");
        sb.append(",IKN_ORIGIN.INSECURE_CONDITION");
        sb.append(",COMMON_IKN_SIS.MT_STS");
        sb.append(",COMMON_IKN_SIS.MEDICINE1");
        sb.append(",COMMON_IKN_SIS.DOSAGE1");
        sb.append(",COMMON_IKN_SIS.UNIT1");
        sb.append(",COMMON_IKN_SIS.USAGE1");
        sb.append(",COMMON_IKN_SIS.MEDICINE2");
        sb.append(",COMMON_IKN_SIS.DOSAGE2");
        sb.append(",COMMON_IKN_SIS.UNIT2");
        sb.append(",COMMON_IKN_SIS.USAGE2");
        sb.append(",COMMON_IKN_SIS.MEDICINE3");
        sb.append(",COMMON_IKN_SIS.DOSAGE3");
        sb.append(",COMMON_IKN_SIS.UNIT3");
        sb.append(",COMMON_IKN_SIS.USAGE3");
        sb.append(",COMMON_IKN_SIS.MEDICINE4");
        sb.append(",COMMON_IKN_SIS.DOSAGE4");
        sb.append(",COMMON_IKN_SIS.UNIT4");
        sb.append(",COMMON_IKN_SIS.USAGE4");
        sb.append(",COMMON_IKN_SIS.MEDICINE5");
        sb.append(",COMMON_IKN_SIS.DOSAGE5");
        sb.append(",COMMON_IKN_SIS.UNIT5");
        sb.append(",COMMON_IKN_SIS.USAGE5");
        sb.append(",COMMON_IKN_SIS.MEDICINE6");
        sb.append(",COMMON_IKN_SIS.DOSAGE6");
        sb.append(",COMMON_IKN_SIS.UNIT6");
        sb.append(",COMMON_IKN_SIS.USAGE6");
        sb.append(",COMMON_IKN_SIS.TNT_KNR");
        sb.append(",COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(",COMMON_IKN_SIS.TOUSEKI");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(",COMMON_IKN_SIS.OX_RYO");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(",COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(",COMMON_IKN_SIS.TOUTU");
        sb.append(",COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(",COMMON_IKN_SIS.MONITOR");
        sb.append(",COMMON_IKN_SIS.JOKUSOU_SHOCHI");
        // 2006/03/16[Tozo Tanaka] : add begin
        sb.append(",COMMON_IKN_SIS.CATHETER");
        // 2006/03/16[Tozo Tanaka] : add end
        sb.append(",COMMON_IKN_SIS.RYU_CATHETER");
        sb.append(",COMMON_IKN_SIS.NETAKIRI");
        sb.append(",COMMON_IKN_SIS.CHH_STS");
        sb.append(",IKN_ORIGIN.TANKI_KIOKU");
        sb.append(",IKN_ORIGIN.NINCHI");
        sb.append(",IKN_ORIGIN.DENTATU");
        sb.append(",IKN_ORIGIN.GNS_GNC");
        sb.append(",IKN_ORIGIN.MOUSOU");
        sb.append(",IKN_ORIGIN.CHUYA");
        sb.append(",IKN_ORIGIN.BOUGEN");
        sb.append(",IKN_ORIGIN.BOUKOU");
        sb.append(",IKN_ORIGIN.TEIKOU");
        sb.append(",IKN_ORIGIN.HAIKAI");
        sb.append(",IKN_ORIGIN.FUSIMATU");
        sb.append(",IKN_ORIGIN.FUKETU");
        sb.append(",IKN_ORIGIN.ISHOKU");
        sb.append(",IKN_ORIGIN.SEITEKI_MONDAI");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER_NM");
        sb.append(",IKN_ORIGIN.SEISIN");
        sb.append(",IKN_ORIGIN.SEISIN_NM");
        sb.append(",IKN_ORIGIN.SENMONI");
        sb.append(",IKN_ORIGIN.SENMONI_NM");
        sb.append(",IKN_ORIGIN.KIKIUDE");
        sb.append(",IKN_ORIGIN.HEIGHT");
        sb.append(",IKN_ORIGIN.WEIGHT");
        sb.append(",IKN_ORIGIN.WEIGHT_CHANGE");
        sb.append(",IKN_ORIGIN.SISIKESSON");
        sb.append(",IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(",IKN_ORIGIN.MAHI");
        sb.append(",IKN_ORIGIN.MAHI_RIGHTARM");
        sb.append(",IKN_ORIGIN.MAHI_RIGHTARM_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_LEFTARM");
        sb.append(",IKN_ORIGIN.MAHI_LEFTARM_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_LOWERRIGHTLIMB");
        sb.append(",IKN_ORIGIN.MAHI_LOWERRIGHTLIMB_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_LOWERLEFTLIMB");
        sb.append(",IKN_ORIGIN.MAHI_LOWERLEFTLIMB_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_ETC");
        sb.append(",IKN_ORIGIN.MAHI_ETC_BUI");
        sb.append(",IKN_ORIGIN.MAHI_ETC_TEIDO");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(",IKN_ORIGIN.KOUSHU");
        sb.append(",IKN_ORIGIN.KOUSHU_BUI");
        sb.append(",IKN_ORIGIN.KOUSHU_TEIDO");
        sb.append(",IKN_ORIGIN.KANSETU_ITAMI");
        sb.append(",IKN_ORIGIN.KANSETU_ITAMI_BUI");
        sb.append(",IKN_ORIGIN.KANSETU_ITAMI_TEIDO");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.JOKUSOU");
        sb.append(",IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(",IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(",IKN_ORIGIN.OUTDOOR");
        sb.append(",IKN_ORIGIN.WHEELCHAIR");
        sb.append(",IKN_ORIGIN.ASSISTANCE_TOOL");
        sb.append(",IKN_ORIGIN.MEAL");
        sb.append(",IKN_ORIGIN.NOURISHMENT");
        sb.append(",IKN_ORIGIN.EATING_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(",IKN_ORIGIN.IDOUTEIKA");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(",IKN_ORIGIN.TOJIKOMORI");
        sb.append(",IKN_ORIGIN.IYOKUTEIKA");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(",IKN_ORIGIN.TEIEIYOU");
        sb.append(",IKN_ORIGIN.SESSYOKUENGE");
        sb.append(",IKN_ORIGIN.DASSUI");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN");
        sb.append(",IKN_ORIGIN.GAN_TOUTU");
        sb.append(",IKN_ORIGIN.BYOUTAITA");
        sb.append(",IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.IDOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TOJIKOMORI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.IYOKUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TEIEIYOU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SESSYOKUENGE_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.GAN_TOUTU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        // sb.append(",COMMON_IKN_SIS.YKG_YOGO");
        // sb.append(",COMMON_IKN_SIS.IMPRO_SERVICE");
        sb.append(",IKN_ORIGIN.VITAL_FUNCTIONS_OUTLOOK");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_REHA");
        sb.append(",IKN_ORIGIN.HOUMON_REHA_UL");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU_UL");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_NM");
        sb.append(",IKN_ORIGIN.KETUATU");
        sb.append(",IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.IDOU");
        sb.append(",IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.SESHOKU");
        sb.append(",IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.UNDOU");
        sb.append(",IKN_ORIGIN.UNDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.ENGE");
        sb.append(",IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.KAIGO_OTHER");
        sb.append(",IKN_ORIGIN.KANSENSHOU");
        sb.append(",IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(",IKN_ORIGIN.HASE_SCORE");
        sb.append(",IKN_ORIGIN.HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.P_HASE_SCORE");
        sb.append(",IKN_ORIGIN.P_HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.INST_SEL_PR1");
        sb.append(",IKN_ORIGIN.INST_SEL_PR2");
        sb.append(",IKN_ORIGIN.IKN_TOKKI");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.FORMAT_KBN=1");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        return sb.toString();
    }

    /**
     * CSV出力に必要なデータを取得するSQL文を取得します。
     *
     * @return String
     */
    private String getSqlIshi() {
   	 
    	// add begin 2006/08/03 kamitsukasa
    
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(", IKN_ORIGIN.EDA_NO");
        sb.append(", IKN_ORIGIN.INSURED_NO");
        sb.append(", IKN_BILL.FD_TIMESTAMP");
        sb.append(", IKN_ORIGIN.INSURER_NO");
        sb.append(", IKN_ORIGIN.INSURER_NM");
        sb.append(", IKN_ORIGIN.INSURED_NO");
        sb.append(", IKN_BILL.JIGYOUSHA_NO");
        sb.append(", IKN_BILL.SINSEI_DT");
        sb.append(", IKN_ORIGIN.REQ_DT");
        sb.append(", IKN_ORIGIN.SEND_DT");
        sb.append(", IKN_ORIGIN.REQ_NO");
        sb.append(", IKN_BILL.DR_NO");
        sb.append(", IKN_ORIGIN.KIND");
        sb.append(", IKN_ORIGIN.KINYU_DT");
        sb.append(", COMMON_IKN_SIS.PATIENT_KN");
        sb.append(", COMMON_IKN_SIS.PATIENT_NM");
        sb.append(", COMMON_IKN_SIS.BIRTHDAY");
        sb.append(", COMMON_IKN_SIS.AGE");
        sb.append(", COMMON_IKN_SIS.SEX");
        sb.append(", COMMON_IKN_SIS.POST_CD");
        sb.append(", COMMON_IKN_SIS.ADDRESS");
        sb.append(", COMMON_IKN_SIS.TEL1");
        sb.append(", COMMON_IKN_SIS.TEL2");
        sb.append(", COMMON_IKN_SIS.DR_NM");
        sb.append(", COMMON_IKN_SIS.MI_NM");
        sb.append(", COMMON_IKN_SIS.MI_POST_CD");
        sb.append(", COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(", COMMON_IKN_SIS.MI_TEL1");
        sb.append(", COMMON_IKN_SIS.MI_TEL2");
        sb.append(", COMMON_IKN_SIS.MI_FAX1");
        sb.append(", COMMON_IKN_SIS.MI_FAX2");
        sb.append(", IKN_ORIGIN.DR_CONSENT");
        sb.append(", IKN_ORIGIN.LASTDAY");
        sb.append(", IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(", IKN_ORIGIN.TAKA");
        sb.append(", IKN_ORIGIN.TAKA_OTHER");
        sb.append(", COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(", COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(", IKN_ORIGIN.SHUSSEI1");
        sb.append(", COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(", COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(", IKN_ORIGIN.SHUSSEI2");
        sb.append(", COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(", COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(", IKN_ORIGIN.SHUSSEI3");
        sb.append(", IKN_ORIGIN.NYUIN_DT_STA1");
        sb.append(", IKN_ORIGIN.NYUIN_DT_END1");
        sb.append(", IKN_ORIGIN.NYUIN_NM1");
        sb.append(", IKN_ORIGIN.NYUIN_DT_STA2");
        sb.append(", IKN_ORIGIN.NYUIN_DT_END2");
        sb.append(", IKN_ORIGIN.NYUIN_NM2");
        sb.append(", COMMON_IKN_SIS.SHJ_ANT");
        sb.append(", IKN_ORIGIN.INSECURE_CONDITION");
        sb.append(", COMMON_IKN_SIS.MT_STS");
        sb.append(", COMMON_IKN_SIS.MEDICINE1");
        sb.append(", COMMON_IKN_SIS.DOSAGE1");
        sb.append(", COMMON_IKN_SIS.UNIT1");
        sb.append(", COMMON_IKN_SIS.USAGE1");
        sb.append(", COMMON_IKN_SIS.MEDICINE2");
        sb.append(", COMMON_IKN_SIS.DOSAGE2");
        sb.append(", COMMON_IKN_SIS.UNIT2");
        sb.append(", COMMON_IKN_SIS.USAGE2");
        sb.append(", COMMON_IKN_SIS.MEDICINE3");
        sb.append(", COMMON_IKN_SIS.DOSAGE3");
        sb.append(", COMMON_IKN_SIS.UNIT3");
        sb.append(", COMMON_IKN_SIS.USAGE3");
        sb.append(", COMMON_IKN_SIS.MEDICINE4");
        sb.append(", COMMON_IKN_SIS.DOSAGE4");
        sb.append(", COMMON_IKN_SIS.UNIT4");
        sb.append(", COMMON_IKN_SIS.USAGE4");
        sb.append(", COMMON_IKN_SIS.MEDICINE5");
        sb.append(", COMMON_IKN_SIS.DOSAGE5");
        sb.append(", COMMON_IKN_SIS.UNIT5");
        sb.append(", COMMON_IKN_SIS.USAGE5");
        sb.append(", COMMON_IKN_SIS.MEDICINE6");
        sb.append(", COMMON_IKN_SIS.DOSAGE6");
        sb.append(", COMMON_IKN_SIS.UNIT6");
        sb.append(", COMMON_IKN_SIS.USAGE6");
        sb.append(", COMMON_IKN_SIS.TNT_KNR");
        sb.append(", COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(", COMMON_IKN_SIS.TOUSEKI");
        sb.append(", COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(", COMMON_IKN_SIS.OX_RYO");
        sb.append(", COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(", COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(", COMMON_IKN_SIS.TOUTU");
        sb.append(", COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(", IKN_ORIGIN.KYUIN_SHOCHI");
        sb.append(", IKN_ORIGIN.KYUIN_SHOCHI_CNT");
        sb.append(", IKN_ORIGIN.KYUIN_SHOCHI_JIKI");
        sb.append(", COMMON_IKN_SIS.MONITOR");
        sb.append(", COMMON_IKN_SIS.JOKUSOU_SHOCHI");
        sb.append(", COMMON_IKN_SIS.CATHETER");
        sb.append(", IKN_ORIGIN.KS_CHUYA");
        sb.append(", IKN_ORIGIN.KS_BOUGEN");
        sb.append(", IKN_ORIGIN.KS_BOUKOU");
        sb.append(", IKN_ORIGIN.KS_TEIKOU");
        sb.append(", IKN_ORIGIN.KS_HAIKAI");
        sb.append(", IKN_ORIGIN.KS_FUSIMATU");
        sb.append(", IKN_ORIGIN.KS_FUKETU");
        sb.append(", IKN_ORIGIN.KS_ISHOKU");
        sb.append(", IKN_ORIGIN.KS_SEITEKI_MONDAI");
        sb.append(", IKN_ORIGIN.KS_OTHER");
        sb.append(", IKN_ORIGIN.KS_OTHER_NM");
        sb.append(", IKN_ORIGIN.SEISIN");
        sb.append(", IKN_ORIGIN.SEISIN_NM");
        sb.append(", IKN_ORIGIN.SS_SENMO");
        sb.append(", IKN_ORIGIN.SS_KEMIN_KEIKO");
        sb.append(", IKN_ORIGIN.SS_GNS_GNC");
        sb.append(", IKN_ORIGIN.SS_MOUSOU");
        sb.append(", IKN_ORIGIN.SS_SHIKKEN_TOSHIKI");
        sb.append(", IKN_ORIGIN.SS_SHITUNIN");
        sb.append(", IKN_ORIGIN.SS_SHIKKO");
        sb.append(", IKN_ORIGIN.SS_NINCHI_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_KIOKU_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_CHUI_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_SUIKOU_KINO_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_SHAKAITEKI_KODO_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_OTHER");
        sb.append(", IKN_ORIGIN.SS_KIOKU_SHOGAI_TANKI");
        sb.append(", IKN_ORIGIN.SS_KIOKU_SHOGAI_CHOUKI");
        sb.append(", IKN_ORIGIN.SS_OTHER_NM");
        sb.append(", IKN_ORIGIN.SENMONI");
        sb.append(", IKN_ORIGIN.SENMONI_NM");
        sb.append(", IKN_ORIGIN.TENKAN");
        sb.append(", IKN_ORIGIN.TENKAN_HINDO");
        sb.append(", IKN_ORIGIN.KIKIUDE");
        sb.append(", IKN_ORIGIN.HEIGHT");
        sb.append(", IKN_ORIGIN.WEIGHT");
        sb.append(", IKN_ORIGIN.WEIGHT_CHANGE");
        sb.append(", IKN_ORIGIN.SISIKESSON");
        sb.append(", IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(", IKN_ORIGIN.SISIKESSON_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_LEFTARM");
        sb.append(", IKN_ORIGIN.MAHI_LEFTARM_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_LOWERLEFTLIMB");
        sb.append(", IKN_ORIGIN.MAHI_LOWERLEFTLIMB_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_RIGHTARM");
        sb.append(", IKN_ORIGIN.MAHI_RIGHTARM_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_LOWERRIGHTLIMB");
        sb.append(", IKN_ORIGIN.MAHI_LOWERRIGHTLIMB_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_ETC");
        sb.append(", IKN_ORIGIN.MAHI_ETC_BUI");
        sb.append(", IKN_ORIGIN.MAHI_ETC_TEIDO");
        sb.append(", IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(", IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(", IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(", IKN_ORIGIN.KOUSHU");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.KOUSHU_ETC");
        sb.append(", IKN_ORIGIN.KOUSHU_ETC_BUI");
        sb.append(", IKN_ORIGIN.KANSETU_ITAMI");
        sb.append(", IKN_ORIGIN.KANSETU_ITAMI_BUI");
        sb.append(", IKN_ORIGIN.KANSETU_ITAMI_TEIDO");
        //sb.append(", IKN_ORIGIN.SHICCHO_FLAG");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.JOKUSOU");
        sb.append(", IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(", IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(", IKN_ORIGIN.HIFUSIKKAN");
        sb.append(", IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(", IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(", IKN_ORIGIN.NYOUSIKKIN");
        sb.append(", IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(", IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(", IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(", IKN_ORIGIN.ENGESEIHAIEN");
        sb.append(", IKN_ORIGIN.CHOUHEISOKU");
        sb.append(", IKN_ORIGIN.EKIKANKANSEN");
        sb.append(", IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(", IKN_ORIGIN.ITAMI");
        sb.append(", IKN_ORIGIN.DASSUI");
        sb.append(", IKN_ORIGIN.BYOUTAITA");
        sb.append(", IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(", IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.ITAMI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.KETUATU");
        sb.append(", IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.ENGE");
        sb.append(", IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.SESHOKU");
        sb.append(", IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.IDOU");
        sb.append(", IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.KAIGO_OTHER");
        sb.append(", IKN_ORIGIN.KANSENSHOU");
        sb.append(", IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(", IKN_ORIGIN.IKN_TOKKI");
        sb.append(", IKN_ORIGIN.SK_NIJIKU_SEISHIN");
        sb.append(", IKN_ORIGIN.SK_NIJIKU_NORYOKU");
        sb.append(", IKN_ORIGIN.SK_NIJIKU_DT");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_SHOKUJI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_RHYTHM");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_HOSEI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_KINSEN_KANRI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_HUKUYAKU_KANRI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_TAIJIN_KANKEI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_SHAKAI_TEKIOU");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_DT");        
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.FORMAT_KBN=2");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        
        return sb.toString();
     
    	// add end 2006/08/03 kamitsukasa
    
    }
    
    /**
     * dataDB中で、条件に合うレコードが何番目にあるのかを取得します。
     *
     * @param dataDB VRArrayList
     * @param patientNo String
     * @param edaNo String
     * @return int
     */
    private int matchingData(VRArrayList dataDB, String patientNo, String edaNo) {
        for (int i = 0; i < dataDB.getDataSize(); i++) {
            VRMap map = (VRMap) dataDB.getData(i);
            if (String.valueOf(map.getData("PATIENT_NO")).equals(patientNo)) {
                if (String.valueOf(map.getData("EDA_NO")).equals(edaNo)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * VRHashMapのデータを文字列で取得します。
     *
     * @param map VRHashMap
     * @param key String
     * @return String
     */
    private String getDataString(VRMap map, String key) {
        Object tmp = null;
        try {
            tmp = VRBindPathParser.get(key, (VRBindSource) map);
            if (tmp == null) {
                return "";
            }
            if (String.valueOf(tmp).equals("null")) {
                return "";
            }
        } catch (Exception ex) {
            return "";
        }
        return String.valueOf(tmp);
    }

    /**
     * 論理輪をビット列に変換します。
     *
     * @param ronriwa 論理輪
     * @param digit 桁数
     * @return ビット列(左から右へ入替済み)
     */
    private String getBitFromRonriwa(String ronriwa, int digit) {
        // ビット列取得
        String bit = "";
        int wa = Integer.parseInt(ronriwa);
        for (int i = 0; i < digit; i++) {
            if ((wa % 2) == 1) {
                bit = "1" + bit;
            } else {
                bit = "0" + bit;
            }
            wa /= 2;
        }

        // 入替
        String value = "";
        for (int i = 0; i < bit.length(); i++) {
            value += bit.substring(bit.length() - i - 1, bit.length() - i);
        }

        return value;
    }

    /**
     * チェック有無、下線有無の2項目を結合し、0:チェック無、1:チェックのみ、2:チェック＋下線に変換します。
     *
     * @param check チェック有無("0", "1")
     * @param underline 下線有無("0", "1")
     * @return "0":チェック無、"1":チェックのみ、"2":チェック＋下線
     */
    private String getUnderlinedCheckValue(String check, String underline) {
        if (check.equals("1")) {
            if (underline.equals("1")) {
                return "2";
            } else {
                return "1";
            }
        } else {
            return "0";
        }
    }

    /**
     * yyyyMMdd形式の文字列に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatYYYYMMDD(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Date) {
            try {
                return VRDateParser.format((Date) obj, "yyyyMMdd");
            } catch (Exception ex) {

            }
        }

        return "";
    }

    /**
     * ddHHmmss形式の文字列に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatDDHHMMSS(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Date) {
            try {
                return VRDateParser.format((Date) obj, "ddHHmmss");
            } catch (Exception ex) {

            }
        }

        return "";
    }

    /**
     * 「不詳」付き不完全日付をCSV出力用形式に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatUnknownDate(Object obj) {
        // 文字列に変換
        if (obj.equals("null")) {
            return "";
        }
        if (obj.equals("null")) {
            return "";
        }
        String date = String.valueOf(obj);

        // 判定
        if (date.substring(0, 2).equals("00")) { // 空
            return "";
        }
        if (date.substring(0, 4).equals("0000")) { // 空
            return "";
        }
        if (date.substring(0, 2).equals("不詳")) { // 不詳
            return "不詳";
        }
        if (date.substring(5, 7).equals("00")) { // 年まで
            return date.substring(0, 5);
//            return date.substring(0, 5) + "頃";
        }
        if (date.substring(8, 10).equals("00")) { // 月まで
            return date.substring(0, 8);
//            return date.substring(0, 8) + "頃";
        }
        return date;
//        return date + "頃";
    }
    
    /**
     * 「不詳」付き不完全日付をCSV出力用形式に変換します。
     *
     * @param obj Object
     * @return String
     */
    private String formatUnknownDateCustom(Object obj) throws Exception {
    	// add begin 2006/08/03 kamitsukasa
    	
        // 文字列に変換
    	if(obj == null || obj.equals("null")){
    		return "";
    	}
        String date = ACCastUtilities.toString(obj);

        // 判定
        if(date.length() >= 2){
	        if (date.substring(0, 2).equals("00")) { // 空
	            return "";
	        }
	        if (date.substring(0, 2).equals("不詳")) { // 不詳
	            return "不詳";
	        }
        }
        if(date.length() >= 4){
	        if (date.substring(0, 4).equals("0000")) { // 空
	            return "";
	        }
        }
        if(date.length() >= 7){
	        if (date.substring(5, 7).equals("00")) { // 年まで
	            return date.substring(0, 5);
	        }
        }
        if(date.length() >= 10){
	        if (date.substring(8, 10).equals("00")) { // 月まで
	            return date.substring(0, 8);
	        }
        }
        return date;
        
    	// add end 2006/08/03 kamitsukasa
    }
    
    /**
     * 全身図BMPを出力します。
     *
     * @param file File
     * @param pix byte[]
     */
    private void writeBmp(File file, byte[] pix) {

        ByteArrayInputStream bais = new ByteArrayInputStream(pix);
        try {
            BufferedImage img = ImageIO.read(bais);
            ACBmpWriter bmp = new ACBmpWriter(file, img, img.getWidth(this),
                    img.getHeight(this));
            bmp.write();
        } catch (IOException ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    /**
     * 一覧印刷を行います。
     *
     * @throws Exception
     */
    private void doPrintTable() throws Exception {
        // ページ数のカウント
        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        int endRow = data.getDataSize();
        int page = (int) Math.ceil((double) endRow / 21);
        int row = 1;

        // 印刷データ開始設定宣言
        pd.beginPrintEdit();

        // 帳票取得
        String path = ACFrame.getInstance().getExeFolderPath() + separator
                + "format" + separator + "CSVFileOutputPatientList.xml";
//        pd.addFormat("daicho", path);
        ACChotarouXMLUtilities.addFormat(pd, "daicho", "CSVFileOutputPatientList.xml");

        VRMap tmp = (VRMap) data.getData(0);
        String bkInsurerNo = tmp.getData("INSURER_NO").toString();
        for (int i = 0; i < page; i++) {
            pd.beginPageEdit("daicho");

            // 保険者
            IkenshoCommon.addString(pd, "hokensya", bkInsurerNm + " ("
                    + bkInsurerNo + ")");
            // 出力分類
            IkenshoCommon.addString(pd, "dateRange.data.title", taisyoDay
                    .getSelectedItem().toString()
                    + "：");
            // 日付期間開始
            if (taisyoFrom.getDate() != null) {
                Date taisyoFromDate = taisyoFrom.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.fromY",
                        VRDateParser.format(taisyoFromDate, "ggg　ee"));
                IkenshoCommon.addString(pd, "dateRange.data.fromM",
                        VRDateParser.format(taisyoFromDate, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.fromD",
                        VRDateParser.format(taisyoFromDate, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.fromY",
                // taisyoFrom.getEra() + " " + taisyoFrom.getYear());
                // IkenshoCommon.addString(pd, "dateRange.data.fromM",
                // dayFormat.format(Long.parseLong(taisyoFrom.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.fromD",
                // dayFormat.format(Long.parseLong(taisyoFrom.getDay())));
            }
            // 日付期間終了
            if (taisyoTo.getDate() != null) {
                Date taisyoToDate = taisyoTo.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.toY", VRDateParser
                        .format(taisyoToDate, "ggg　ee"));
                IkenshoCommon.addString(pd, "dateRange.data.toM", VRDateParser
                        .format(taisyoToDate, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.toD", VRDateParser
                        .format(taisyoToDate, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.toY",
                // taisyoTo.getEra() + " " + taisyoTo.getYear());
                // IkenshoCommon.addString(pd, "dateRange.data.toM",
                // dayFormat.format(Long.parseLong(taisyoTo.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.toD",
                // dayFormat.format(Long.parseLong(taisyoTo.getDay())));
            }
            // フォーマット
            IkenshoCommon.addString(pd, "formatVersion", "("
                    +ACCastUtilities.toString( formatKbn.getSelectedItem()) + "フォーマット)");

            for (int j = 0; (j < 21) && (row < endRow + 1); j++, row++) {
                VRMap map = (VRMap) data.getData(row - 1);
                // 医師名
                IkenshoCommon.addString(pd, getHeader(row) + ".DR_NM", 
                        getString("DR_NM", map));
                // 氏名
                IkenshoCommon.addString(pd, getHeader(row) + ".PATIENT_NM",
                        getString("PATIENT_NM", map));
                // ふりがな
                IkenshoCommon.addString(pd, getHeader(row) + ".PATIENT_KN",
                        getString("PATIENT_KN", map));
                // 性別
                switch (((Integer) VRBindPathParser.get("SEX", map)).intValue()) {
                case 1:
                    IkenshoCommon.addString(pd, getHeader(row) + ".SEX", "男性");
                    break;
                case 2:
                    IkenshoCommon.addString(pd, getHeader(row) + ".SEX", "女性");
                    break;
                }
                // 年齢
                IkenshoCommon.addString(pd, getHeader(row) + ".AGE", getString(
                        "AGE", map));
                // 生年月日
                IkenshoCommon.addString(pd, getHeader(row) + ".BIRTHDAY",
                        IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                                .get("BIRTHDAY", map)));
                // 被保険者番号
                IkenshoCommon.addString(pd, getHeader(row) + ".INSURED_NO",
                        getString("INSURED_NO", map));
                // ﾀｲﾑｽﾀﾝﾌﾟ
                Object fdTimestamp = map.getData("FD_TIMESTAMP");
                if (fdTimestamp != null) {
                    IkenshoCommon.addString(pd, getHeader(row)
                            + ".FD_TIMESTAMP", formatDDHHMMSS(fdTimestamp));
                }
                // 作成依頼日
                Object reqDt = map.getData("REQ_DT");
                if (reqDt != null) {
                    IkenshoCommon.addString(pd, getHeader(row) + ".REQ_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(reqDt));
                }
                // 記入日
                Object kinyuDt = map.getData("KINYU_DT");
                if (kinyuDt != null) {
                    IkenshoCommon.addString(pd, getHeader(row) + ".KINYU_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(kinyuDt));
                }
            }
            pd.endPageEdit();
        }

        pd.endPrintEdit();
        openPDF(pd);
    }

    private String getHeader(int row) {
        return "table.h" + Integer.toString((row - 1) % 21 + 1);
    }

    private static String getString(String key, VRMap map) throws Exception {
        Object obj = VRBindPathParser.get(key, map);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    /**
     * 被保険者番号の0埋を行います。
     * @param insuredNoOrg
     * @return
     */
    private String convertInsuredNo(String insuredNoOrg) {
    	String insuredNo = new String();

    	try {
    		//Hで始まる場合
        	if (insuredNoOrg.replace('h', 'H').charAt(0) == 'H') {
        		StringBuffer sbZero = new StringBuffer();
        		for (int i=0; i<10 - insuredNoOrg.length(); i++) {
        			sbZero.append("0");
        		}
        		insuredNo = insuredNoOrg.substring(0, 1) + sbZero.toString() +
        			insuredNoOrg.substring(1, insuredNoOrg.length());
        	}
        	//Hで始まらない場合
        	else {
                DecimalFormat df = new DecimalFormat("0000000000");
                insuredNo = df.format(Integer.parseInt(insuredNoOrg));
        	}

    	}
    	catch(Exception ex) {
    		return insuredNoOrg;
    	}

    	return insuredNo;
    }
}
