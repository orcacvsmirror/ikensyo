package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.im.InputSubset;
import java.util.Arrays;
import java.util.Map;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.VRCheckBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoInsurerTypeFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouShousaiKanren extends
        IkenshoTabbableChildAffairContainer {
    private VRPanel kanrenPnl1 = new VRPanel();
    private VRLabel note = new VRLabel();
    private VRPanel kanrenPnl2 = new VRPanel();
    private VRPanel kanrenPnl3 = new VRPanel();
    private ACLabelContainer kaisetushaNmContainer = new ACLabelContainer();
    private ACTextField kaisetushaNm = new ACTextField();
    private ACGroupBox miKbnGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup miKbn = new ACClearableRadioButtonGroup();
    private ACLabelContainer bankNmContainer = new ACLabelContainer();
    private ACTextField bankNm = new ACTextField();
    private ACLabelContainer bankSitenNmContainer = new ACLabelContainer();
    private ACTextField bankSitenNm = new ACTextField();
    private ACLabelContainer bankKouzaNoContainer = new ACLabelContainer();
    private ACTextField bankKouzaNo = new ACTextField();
    private ACLabelContainer bankKouzaKindContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup bankKouzaKind = new ACClearableRadioButtonGroup();
    private ACLabelContainer furikomiMeigiContainer = new ACLabelContainer();
    private ACTextField furikomiMeigi = new ACTextField();
    private VRPanel drNoPnl = new VRPanel();
    private ACLabelContainer drNoContainer = new ACLabelContainer();
    private ACTextField drNo = new ACTextField();
    private VRLabel drNoCaption1 = new VRLabel();
    private VRLabel drNoCaption2 = new VRLabel();
    private ACGroupBox jigyoushoGrp = new ACGroupBox();
    private ACTable table = new ACTable();
    private VRPanel jigyoushoBtnPnl = new VRPanel();
    private ACButton jigyoushoInsert = new ACButton();
    private ACButton jigyoushoUpdate = new ACButton();
    private ACButton jigyoushoDelete = new ACButton();
    private VRCheckBox jigyoushoTableChangeFlg = new VRCheckBox();
    
    private ACIntegerCheckBox addItCheck = new ACIntegerCheckBox();

    private VRArrayList jigyoushoData = new VRArrayList();

    private final int ACT_INSERT = 0;
    private final int ACT_UPDATE = 1;

    public IkenshoIryouKikanJouhouShousaiKanren() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(new VRLayout());
        this.add(kanrenPnl1, VRLayout.NORTH);
        this.add(kanrenPnl2, VRLayout.NORTH);
        this.add(jigyoushoGrp, VRLayout.CLIENT);

        // キャプション
        kanrenPnl1.setLayout(new VRLayout());
        kanrenPnl1.add(note, VRLayout.FLOW);
        note.setText("★自治体(保険者)によって必要な項目は異なります。");
        note
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);

        // 情報
        VRLayout kanrenPnl2Layout = new VRLayout();
        kanrenPnl2.setLayout(kanrenPnl2Layout);
        kanrenPnl2.add(kanrenPnl3, VRLayout.NORTH);
        kanrenPnl2.add(bankNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(bankSitenNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(bankKouzaNoContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(bankKouzaKindContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(furikomiMeigiContainer, VRLayout.FLOW_INSETLINE);
        kanrenPnl2.add(drNoPnl, VRLayout.FLOW_RETURN);
        kanrenPnl2.add(addItCheck, VRLayout.FLOW_RETURN);

        VRLayout kanrenPnl3Layout = new VRLayout();
        kanrenPnl3Layout.setLabelMargin(122);
        kanrenPnl3.setLayout(kanrenPnl3Layout);
        kanrenPnl3.add(kaisetushaNmContainer, VRLayout.FLOW_INSETLINE);
        kanrenPnl3.add(miKbnGrp, VRLayout.FLOW_RETURN);
        kaisetushaNmContainer.setText("開設者氏名");
        kaisetushaNmContainer.add(kaisetushaNm, null);
        // 2009/01/06 [Mizuki Tsutsumi] : change begin
        // 開設者氏名、振込先名義人文字数拡張
        //kaisetushaNm.setColumns(15);
        //kaisetushaNm.setMaxLength(15);
        kaisetushaNm.setColumns(50);
        kaisetushaNm.setMaxLength(50);
        // 2009/01/06 [Mizuki Tsutsumi] : change end
        kaisetushaNm.setIMEMode(InputSubset.KANJI);
        kaisetushaNm.setBindPath("KAISETUSHA_NM");

        miKbnGrp.setText("診療所・病院区分");
        miKbnGrp.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        miKbnGrp.add(miKbn, null);
        miKbn.setUseClearButton(false);
        miKbn.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "診療所", "病院", "その他の施設" }))));
        miKbn.setBindPath("MI_KBN");

        bankNmContainer.setText("振込先金融機関名");
        bankNmContainer.add(bankNm, null);
        bankNm.setColumns(25);
        bankNm.setMaxLength(25);
        bankNm.setIMEMode(InputSubset.KANJI);
        bankNm.setBindPath("BANK_NM");

        bankSitenNmContainer.setText("振込先金融機関支店名");
        bankSitenNmContainer.add(bankSitenNm, null);
        bankSitenNm.setColumns(25);
        bankSitenNm.setMaxLength(25);
        bankSitenNm.setIMEMode(InputSubset.KANJI);
        bankSitenNm.setBindPath("BANK_SITEN_NM");

        bankKouzaNoContainer.setText("振込先口座番号");
        bankKouzaNoContainer.add(bankKouzaNo, null);
        bankKouzaNo.setColumns(10);
        bankKouzaNo.setMaxLength(10);
        bankKouzaNo.setIMEMode(InputSubset.LATIN_DIGITS);
        bankKouzaNo.setCharType(VRCharType.ONLY_ALNUM);
        // bankKouzaNo.setCharType(VRCharType.ONLY_DIGIT);
        bankKouzaNo.setBindPath("BANK_KOUZA_NO");

        bankKouzaKindContainer.setText("振込先口座種類");
        bankKouzaKindContainer.add(bankKouzaKind, null);
        bankKouzaKind.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "普通", "当座" }))));
        bankKouzaKind.setBindPath("BANK_KOUZA_KIND");

        furikomiMeigiContainer.setText("振込先名義人");
        furikomiMeigiContainer.add(furikomiMeigi, null);
        // 2009/01/06 [Mizuki Tsutsumi] : change begin
        // 開設者氏名、振込先名義人文字数拡張
        //furikomiMeigi.setColumns(15);
        //furikomiMeigi.setMaxLength(15);
        furikomiMeigi.setColumns(50);
        furikomiMeigi.setMaxLength(50);
        // 2009/01/06 [Mizuki Tsutsumi] : change end
        furikomiMeigi.setIMEMode(InputSubset.KANJI);
        furikomiMeigi.setBindPath("FURIKOMI_MEIGI");

        VRLayout drNoPnlLayout = new VRLayout();
        drNoPnlLayout.setHgap(0);
        drNoPnlLayout.setVgap(0);
        drNoPnlLayout.setAutoWrap(false);
        drNoPnl.setLayout(drNoPnlLayout);
        drNoPnl.add(drNoCaption1, VRLayout.FLOW);
        drNoPnl.add(drNoContainer, VRLayout.FLOW);
        drNoPnl.add(drNoCaption2, VRLayout.FLOW_RETURN);
        VRLayout drNoContainerLayout = new VRLayout();
        drNoContainerLayout.setAutoWrap(false);
        drNoContainerLayout.setHgap(0);
        drNoContainerLayout.setVgap(0);
        drNoContainer.setLayout(drNoContainerLayout);
        drNoContainer.setText("医師番号");
        drNoContainer.add(drNo, VRLayout.FLOW);
        drNo.setColumns(10);
        drNo.setMaxLength(10);
        drNo.setIMEMode(InputSubset.LATIN_DIGITS);
        // drNo.setCharType(VRCharType.ONLY_ASCII);
        drNo.setBindPath("DR_NO");
        drNoCaption1.setText("(");
        drNoCaption2.setText(")");

        addItCheck.setBindPath("DR_ADD_IT");
        addItCheck.setText("この医療機関を電子化加算の対象にする。");

        // Grp
        jigyoushoGrp.setText("事業所番号");
        jigyoushoGrp
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        jigyoushoGrp.setLayout(new BorderLayout());
        jigyoushoGrp.add(table, BorderLayout.CENTER);
        jigyoushoGrp.add(jigyoushoBtnPnl, BorderLayout.EAST);

        jigyoushoBtnPnl.setLayout(new VRLayout());
        jigyoushoBtnPnl.add(jigyoushoInsert, VRLayout.FLOW_RETURN);
        jigyoushoBtnPnl.add(jigyoushoUpdate, VRLayout.FLOW_RETURN);
        jigyoushoBtnPnl.add(jigyoushoDelete, VRLayout.FLOW_RETURN);
        jigyoushoBtnPnl.add(jigyoushoTableChangeFlg, VRLayout.FLOW_RETURN);

        jigyoushoInsert.setText("登録(T)");
        jigyoushoInsert.setMnemonic('T');
        jigyoushoUpdate.setText("編集(E)");
        jigyoushoUpdate.setMnemonic('E');
        jigyoushoDelete.setText("削除(D)");
        jigyoushoDelete.setMnemonic('D');
        jigyoushoTableChangeFlg.setVisible(false);
        jigyoushoTableChangeFlg.setSelected(false);
        jigyoushoTableChangeFlg.setBindPath("TABLE_CHANGE_FLG");
    }

    private void createTable() throws Exception {
        // テーブルの生成
        table.setModel(new ACTableModelAdapter(jigyoushoData, new String[] {
                "INSURER_NO", "INSURER_NM", "INSURER_TYPE", "JIGYOUSHA_NO" }));

        // ColumnModelの生成
        table.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
                new VRTableColumn(0, 90, "保険者番号"),
                new VRTableColumn(1, 300, "保険者名称"),
                new VRTableColumn(2, 160, "保険者区分", IkenshoInsurerTypeFormat.getInstance()),
                new VRTableColumn(3, 150, "事業所番号") }));
    }

    private void event() throws Exception {
        // 事業所番号登録
        jigyoushoInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDlg(ACT_INSERT);
            }
        });

        // 事業所番号更新
        jigyoushoUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDlg(ACT_UPDATE);
            }
        });

        // 事業所番号削除
        jigyoushoDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doDelete();
            }
        });

        // テーブル選択時
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setButtonsEnabled();
                if (e.getClickCount() == 2) {
                    showDlg(ACT_UPDATE);
                }
            }
        });
    }

    /**
     * 事業所番号設定ダイアログ表示処理を行います。
     * 
     * @param act String
     */
    private void showDlg(int act) {
        int selRow = 0; // 選択行
        VRMap affair = new VRHashMap(); // DLGに渡すパラメータ
        if (act == ACT_INSERT) {
            // ダイアログに渡すパラメータを準備
            affair.put("ACT", "insert");
        } else if (act == ACT_UPDATE) {
            // 選択行の取得
            selRow = table.getSelectedModelRow();
            if (selRow < 0) {
                return;
            }
            // ダイアログに渡すパラメータを準備
            affair.put("ACT", "update");
            affair.put("SEL_ROW", String.valueOf(selRow));
        }

        // 現在のテーブル上のデータを渡りパラメータに追加
        affair.put("DATA", jigyoushoData);

        // スナップショット退避
        Map snap = IkenshoSnapshot.getInstance().getMemento();
        Container container = IkenshoSnapshot.getInstance().getRootContainer();

        // DLG実行(表示)
        IkenshoJigyoushoBangouSetting dlg = new IkenshoJigyoushoBangouSetting(
                affair);
        dlg.setVisible(true);
        // dlg.show();

        // スナップショット復元
        IkenshoSnapshot.getInstance().setRootContainer(container);
        IkenshoSnapshot.getInstance().setMemento(snap);

        // ダイアログからパラメータを取得
        VRMap params = dlg.getParams();
        String result = String.valueOf(params.getData("ACT"));

        if (result.equals("submit")) {
            // 行の追加(更新)処理を行う
            VRMap newRow = new VRHashMap();
            String insurerNoNew = ACCastUtilities.toString(params.getData("INSURER_NO"),"");
            String insurerTypeNow = ACCastUtilities.toString(params.getData("INSURER_TYPE"),"");
            newRow.setData("INSURER_NO", ACCastUtilities.toString(params.getData("INSURER_NO"),""));
            newRow.setData("INSURER_NM", ACCastUtilities.toString(params.getData("INSURER_NM"),""));
            newRow.setData("JIGYOUSHA_NO", ACCastUtilities.toString(params.getData("JIGYOUSHA_NO"),""));
            newRow.setData("INSURER_TYPE", params.getData("INSURER_TYPE"));
            if (act == ACT_INSERT) {
                jigyoushoData.addData(newRow);
            } else if (act == ACT_UPDATE) {
                jigyoushoData.setData(selRow, newRow);
            }

            // 後処理
            table.revalidate();
            table.sort("INSURER_NO ASC, INSURER_TYPE ASC");
            // table.getTable().revalidate();
            // table.getTable().sort("INSURER_NO ASC");
            setButtonsEnabled();
            jigyoushoTableChangeFlg.setSelected(true);

            // 行の再選択
            for (int i = 0; i < jigyoushoData.getDataSize(); i++) {
                VRMap row = (VRMap) jigyoushoData.getData(i);
                String insurerNoTmp = ACCastUtilities.toString(row.getData("INSURER_NO"),"");
                String insurerTypeTmp = ACCastUtilities.toString(row.getData("INSURER_TYPE"),"");
                if (insurerNoTmp.equals(insurerNoNew)&& insurerTypeTmp.equals(insurerTypeNow)) {
                    table.setSelectedModelRow(((VRSortableTableModelar) table
                            .getModel()).getReverseTranslateIndex(i));
                    break;
                }
            }
        }
    }

    /**
     * テーブルから選択行を削除します。
     */
    private void doDelete() {
        // 確認メッセージの表示
        int result = ACMessageBox.show("削除してもよろしいですか？",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                ACMessageBox.FOCUS_CANCEL);
        if (result == ACMessageBox.RESULT_CANCEL) {
            return;
        }

        // 最終選択位置(モデルではなく画面上)を退避
        int lastSelectedIndex = table.getSelectedSortedRow();

        // 選択行の取得
        int selRow = table.getSelectedModelRow();
        if (selRow < 0) {
            return;
        }
        int delRow = ((VRSortableTableModelar) table.getModel())
                .getTranslateIndex(selRow);

        // 削除
        jigyoushoData.removeData(delRow);

        // 再選択
        table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

        // 後処理
        setButtonsEnabled();
        jigyoushoTableChangeFlg.setSelected(true);
    }

    public boolean noControlError() {
        // 診療所・病院区分 / 未選択チェック
        if (miKbn.getSelectedIndex() <= 0) {
            ACMessageBox.show("診療所・病院区分を選択してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            miKbn.requestFocus();
            return false;
        }

        return true;
    }

    public boolean noControlWarning() {
        // 事業所番号 / 未登録チェック
        if (table.getRowCount() <= 0) {
            int result = ACMessageBox.show("事業所番号が登録されていません。\nよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            } else {
                table.requestFocus();
                // table.getTable().requestFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * 事業所番号テーブルにデータを設定します。
     * 
     * @param drCd String
     * @throws Exception
     */
    public void doSelect(String drCd) throws Exception {
        doSelectFromDB(drCd);
        createTable();
        setButtonsEnabled();
        if (table.getRowCount() > 0) {
            if (table.getSelectedModelRow() < 0) {
                table.setSelectedModelRow(0);
            }
        }
    }

    /**
     * 事業所番号テーブル(DB)からデータを取得します。
     * 
     * @param drCd String
     * @return VRArrayList
     * @throws Exception
     */
    private VRArrayList doSelectFromDB(String drCd) throws Exception {
        // キーを元に、DBからデータを取得
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DISTINCT");
        sb.append(" JIGYOUSHA.DR_CD");
        sb.append(",JIGYOUSHA.INSURER_NO");
        sb.append(",INSURER.INSURER_NM");
        sb.append(",JIGYOUSHA.INSURER_TYPE");
        sb.append(",JIGYOUSHA.JIGYOUSHA_NO");
        sb.append(",JIGYOUSHA.LAST_TIME");
        sb.append(" FROM");
        sb.append(" JIGYOUSHA, INSURER");
        sb.append(" WHERE");
        sb.append(" JIGYOUSHA.INSURER_NO = INSURER.INSURER_NO");
        sb.append(" AND");
        sb.append(" JIGYOUSHA.INSURER_TYPE = INSURER.INSURER_TYPE");
        sb.append(" AND");
        sb.append(" DR_CD=" + drCd);
        sb.append(" ORDER BY");
        sb.append(" JIGYOUSHA.INSURER_NO ASC, JIGYOUSHA.INSURER_TYPE ASC");
        jigyoushoData = (VRArrayList) dbm.executeQuery(sb.toString());

        return jigyoushoData;
    }

    public ACTable getTable() {
        return table;
    }

    /**
     * ボタン、メニューの有効状態を設定します。
     */
    public void setButtonsEnabled() {
        boolean enabled = false;
        if (table.getRowCount() > 0) {
            if (table.getSelectedModelRow() < 0) {
                enabled = false;
            } else {
                enabled = true;
            }
        }

        jigyoushoUpdate.setEnabled(enabled);
        jigyoushoDelete.setEnabled(enabled);
    }

    public VRArrayList getJigyoushoData() {
        return jigyoushoData;
    }

    public ACTable getJigyoushoTable() {
        return table;
    }
}
