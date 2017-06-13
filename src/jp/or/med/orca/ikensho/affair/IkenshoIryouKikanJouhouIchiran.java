package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
import javax.swing.SwingConstants;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.ac.component.table.ACTableColumn;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
import jp.nichicom.ac.text.ACHashMapFormat;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouIchiran extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton copy = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private VRPanel tablePnl = new VRPanel();
    private VRPanel infoPnl = new VRPanel();
    private VRLabel info = new VRLabel();
    private ACTable table = new ACTable();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem insertMenu = new JMenuItem();
    private JMenuItem copyMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();
    private VRArrayList data;
    private VRArrayList jigyoushoData;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
    // 有効カラム
    private ACTableColumn enabledColumn;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
    private static final ACPassiveKey PASSIVE_CHECK_KEY_DOCTOR = new
        ACPassiveKey("DOCTOR", new String[] {"DR_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_KEY_JIGYOUSHA = new
        ACPassiveKey("JIGYOUSHA", new String[] {"DR_CD", "INSURER_NO", "JIGYOUSHA_NO"}
                          , new Format[] {null, IkenshoConstants.FORMAT_PASSIVE_STRING, IkenshoConstants.FORMAT_PASSIVE_STRING}
                          , "LAST_TIME", "LAST_TIME");


    public IkenshoIryouKikanJouhouIchiran() {
        try {
            jbInit();
            events();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        buttons.setTitle("医療機関情報一覧");
        this.add(buttons, VRLayout.NORTH);
        this.add(tablePnl, VRLayout.CLIENT);

        //ボタン系
        buttons.add(delete, VRLayout.EAST);
        buttons.add(copy, VRLayout.EAST);
        buttons.add(insert, VRLayout.EAST);
        buttons.add(detail, VRLayout.EAST);

        detail.setText("詳細情報(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("詳細情報(E)");
        detail.setToolTipText("選択された医療機関情報の詳細画面へ移ります。");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        copy.setText("複製(C)");
        copy.setMnemonic('C');
        copy.setActionCommand("複製(C)");
        copy.setToolTipText("選択された連携医情報を複製します。");
        copy.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_COPY);

        insert.setText("新規登録(N)");
        insert.setMnemonic('N');
        insert.setActionCommand("新規登録(N)");
        insert.setToolTipText("医療機関情報を新規に作成します。");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);

        delete.setText("削除(D)");
        delete.setMnemonic('D');
        delete.setActionCommand("削除(D)");
        delete.setToolTipText("選択された医療機関情報を削除します。");
        delete.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DELETE);

        //ポップアップ系
        popup.add(detailMenu);
        popup.add(insertMenu);
        popup.add(copyMenu);
        popup.add(deleteMenu);

        detailMenu.setActionCommand("詳細情報(E)");
        detailMenu.setMnemonic('E');
        detailMenu.setText("詳細情報(E)");
        detailMenu.setToolTipText(detail.getToolTipText());

        insertMenu.setActionCommand("新規登録(N)");
        insertMenu.setMnemonic('N');
        insertMenu.setText("新規登録(N)");
        insertMenu.setToolTipText(insert.getToolTipText());

        copyMenu.setActionCommand("複製(C)");
        copyMenu.setMnemonic('C');
        copyMenu.setText("複製(C)");
        copyMenu.setToolTipText(copy.getToolTipText());

        deleteMenu.setActionCommand("削除(D)");
        deleteMenu.setMnemonic('D');
        deleteMenu.setText("削除(D)");
        deleteMenu.setToolTipText(delete.getToolTipText());

        //テーブル
        tablePnl.setLayout(new BorderLayout());
        tablePnl.add(infoPnl, BorderLayout.NORTH);
        tablePnl.add(table, BorderLayout.CENTER);

        infoPnl.setLayout(new BorderLayout());
        infoPnl.add(info, BorderLayout.EAST);
        info.setText("◎表示医師→通常使用する医師");
        info.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        //メニューバーのボタンに対応するトリガーの設定
        addDetailTrigger(detail);
        addDetailTrigger(detailMenu);
        addCopyTrigger(copy);
        addCopyTrigger(copyMenu);
        addInsertTrigger(insert);
        addInsertTrigger(insertMenu);
        addDeleteTrigger(delete);
        addDeleteTrigger(deleteMenu);
        addTableSelectedTrigger(table);

        //テーブル形成
        doSelect(affair);
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return table;
//        return table.getTable();
    }

    public boolean canBack(VRMap parameters) throws Exception {
        return true;
    }

    private void events() {
        //テーブル選択時
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        detailActionPerformed(null);
                    }
                    catch (Exception ex) {
                      IkenshoCommon.showExceptionMessage(ex);
                    }
                }
                else {
                    showPopup(e);
                }
            }

            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
        });
    }

    protected void detailActionPerformed(ActionEvent e) throws Exception {
        //選択行の取得
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //どのボタンからの遷移かという情報を付加
        VRMap selectedRow = (VRMap)data.getData(row);
        selectedRow.put("ACT", "detail");

        //遷移
        ACAffairInfo affair = new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class.getName(), selectedRow, "医療機関情報詳細");
        ACFrame.getInstance().next(affair);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        //どのボタンからの遷移かという情報を付加
        VRMap selectedRow = new VRHashMap();
        selectedRow.put("ACT", "insert");

        //遷移
        ACAffairInfo affair = new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class.getName(), selectedRow, "医療機関情報詳細");
        ACFrame.getInstance().next(affair);

        //後処理・ツールボタンやメニューの有効状態の設定
        setButtonsEnabled();
    }

    protected void copyActionPerformed(ActionEvent e) throws Exception {
        //選択行の取得
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //どのボタンからの遷移かという情報を付加
        VRMap selectedRow = (VRMap)data.getData(row);
        selectedRow.put("ACT", "copy");

        //遷移
        ACAffairInfo affair = new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class.getName(), selectedRow, "医療機関情報詳細");
        ACFrame.getInstance().next(affair);
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        delete.setEnabled(false);

        //選択行の取得
        int row = table.getSelectedModelRow();
        if (row < 0) {
            setButtonsEnabled();
            return;
        }

        //確認MSG
        String msg = "選択された情報を削除します。よろしいですか？";
        int result = ACMessageBox.show(msg,
                                            ACMessageBox.BUTTON_OKCANCEL,
                                            ACMessageBox.ICON_QUESTION,
                                            ACMessageBox.FOCUS_CANCEL);

        if (result == ACMessageBox.RESULT_OK) {
            //最終選択位置(モデルではなく画面上)を退避
            int lastSelectedIndex = table.getSelectedSortedRow();

            //DBとテーブルからデータを削除する
            IkenshoFirebirdDBManager dbm = null;
            try {
                //パッシブチェック
                clearPassiveTask();
                addPassiveDeleteTask(PASSIVE_CHECK_KEY_DOCTOR, row);
                for (int i=0; i<jigyoushoData.getDataSize(); i++) {
                    addPassiveDeleteTask(PASSIVE_CHECK_KEY_JIGYOUSHA, i);
                }
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    doSelect(null); //再SELECT
                    return;
                }

                //選択行の医師コードを取得
                String DR_CD = String.valueOf(((VRMap)data.get(row)).getData("DR_CD"));

                //SQL文の生成
                dbm.executeUpdate("DELETE FROM DOCTOR WHERE DR_CD=" + DR_CD);
                dbm.executeUpdate("DELETE FROM JIGYOUSHA WHERE DR_CD=" + DR_CD);

                //コミット
                dbm.commitTransaction();
            }
            catch (Exception ex) {
                if (dbm != null) {
                    dbm.rollbackTransaction(); //ロールバック
                }
                setButtonsEnabled();
                throw new Exception(ex);
            }

            doSelect(null); //再SELECT

            //再選択
            table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);
        }
        else {
          setButtonsEnabled();
        }
    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        setButtonsEnabled();
    }

    /**
     * ポップアップメニューを表示します。
     * @param e MouseEvent
     */
    private void showPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        popup.show( (Component) e.getSource(), e.getX(), e.getY());
      }
    }

    /**
     * テーブルにデータを設定します。
     * @param affair 画面渡りパラメータ
     * @throws Exception
     */
    private void doSelect(ACAffairInfo affair) throws Exception {
        //DBからデータを取得
        doSelectFromDB();

        //column等生成
        createTableColumn();

        //ボタンのEnabled設定
        setButtonsEnabled();

        //テーブルにフォーカスを当てる
//        table.getTable().requestFocus();
        table.requestFocus();

        //テーブルの行を選択
        setInitSelectedRow(affair);

        //ステータスバー
        setStatusText(String.valueOf(table.getRowCount()) + "件登録されています。");
    }

    /**
     * DBからデータを取得します。
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT" );
        sb.append( " DR_CD" );
        sb.append( ",DR_NM" );
        sb.append( ",MI_NM" );
        sb.append( ",MI_POST_CD" );
        sb.append( ",MI_ADDRESS" );
        sb.append( ",MI_TEL1" );
        sb.append( ",MI_TEL2" );
        sb.append( ",MI_FAX1" );
        sb.append( ",MI_FAX2" );
        sb.append( ",MI_CEL_TEL1" );
        sb.append( ",MI_CEL_TEL2" );
        sb.append( ",KINKYU_RENRAKU" );
        sb.append( ",FUZAIJI_TAIOU" );
        sb.append( ",BIKOU" );
        sb.append( ",MI_DEFAULT" );
        sb.append( ",DR_NO" );
        sb.append( ",LAST_TIME" );
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
        sb.append( ",INVALID_FLAG" );
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-Start 医師の並び順の変更対応
        sb.append( ",DR_KN" );
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-End
        sb.append( " FROM" );
        sb.append( " DOCTOR" );
        sb.append( " ORDER BY" );
// [ID:0000801][Ryosuke Koinuma] 2016/10 edit-Start 医師の並び順の変更対応
//        sb.append( " DR_NM" );
        //医師氏名コンボボックスの並び順に合わせる
        sb.append(" DR_KN");
        sb.append(" NULLS FIRST");
        sb.append(" ,DR_NM");
// [ID:0000801][Ryosuke Koinuma] 2016/10 edit-End
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            //パッシブチェック
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY_DOCTOR, data);
        }

        //表示用Fieldの追加
        for( int i = 0; i < data.getDataSize(); i++ ) {
            String miDefault;
            IkenshoTelTextField telTmp = new IkenshoTelTextField();

            //通常使う医師
            if (String.valueOf(((VRMap)data.getData(i)).getData("MI_DEFAULT")).equals("1")) {
                miDefault = "◎";
            }
            else {
                miDefault = "";
            }
            ((VRMap)data.getData(i)).setData("MI_DEFAULT_MARK", miDefault);

            //電話番号
            String tel1 = String.valueOf(((VRMap)data.getData(i)).getData("MI_TEL1"));
            String tel2 = String.valueOf(((VRMap)data.getData(i)).getData("MI_TEL2"));
            telTmp.setArea(tel1);
            telTmp.setNumber(tel2);
            ((VRMap)data.getData(i)).setData("TEL", telTmp.getTelNo());

            //FAX
            String fax1 = String.valueOf(((VRMap)data.getData(i)).getData("MI_FAX1"));
            String fax2 = String.valueOf(((VRMap)data.getData(i)).getData("MI_FAX2"));
            telTmp.setArea(fax1);
            telTmp.setNumber(fax2);
            ((VRMap)data.getData(i)).setData("FAX", telTmp.getTelNo());

            //連絡先(携帯)
            String celTel1 = String.valueOf(((VRMap)data.getData(i)).getData("MI_CEL_TEL1"));
            String celTel2 = String.valueOf(((VRMap)data.getData(i)).getData("MI_CEL_TEL2"));
            telTmp.setArea(celTel1);
            telTmp.setNumber(celTel2);
            ((VRMap)data.getData(i)).setData("CEL_TEL", telTmp.getTelNo());
        }


        //事業所データ、DBからデータを取得(パッシブチェック用)
        dbm = new IkenshoFirebirdDBManager();
        sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DR_CD");
        sb.append(",INSURER_NO");
        sb.append(",JIGYOUSHA_NO");
        sb.append(",LAST_TIME");
        sb.append(" FROM");
        sb.append(" JIGYOUSHA");
        jigyoushoData = (VRArrayList)dbm.executeQuery(sb.toString());
        if (jigyoushoData.getDataSize() > 0) {
            //パッシブチェック
            reservedPassive(PASSIVE_CHECK_KEY_JIGYOUSHA, jigyoushoData);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void createTableColumn() throws Exception {
        //テーブルの生成
        table.setModel(new ACTableModelAdapter(data, new String[] {
            "MI_DEFAULT_MARK",
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
            "INVALID_FLAG",
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
            "DR_NM",
// [ID:0000801][Ryosuke Koinuma]  2014/10 add-Start 医師の並び順の変更対応
            "DR_KN",
// [ID:0000801][Ryosuke Koinuma]  2014/10 add-End
            "MI_NM",
            "MI_POST_CD",
            "MI_ADDRESS",
            "TEL",
            "FAX",
            "CEL_TEL",
            "KINKYU_RENRAKU",
            "FUZAIJI_TAIOU",
            "BIKOU"}));

        //ColumnModelの生成
        table.setColumnModel(new VRTableColumnModel(
            new VRTableColumn[] {
//            new VRTableColumn(0, 16, " "),
//            new VRTableColumn(1, 150, "医師氏名"),
//            new VRTableColumn(2, 200, "医療機関名"),
//            new VRTableColumn(3, 80, "郵便番号"),
//            new VRTableColumn(4, 200, "所在地"),
//            new VRTableColumn(5, 120, "連絡先(電話)"),
//            new VRTableColumn(6, 120, "連絡先(FAX)"),
//            new VRTableColumn(7, 120, "連絡先(携帯)"),
//            new VRTableColumn(8, 200, "緊急時連絡先"),
//            new VRTableColumn(9, 200, "不在時対応法"),
            new VRTableColumn(0, 32, " "),
// [ID:0000787][Satoshi Tokusari] 2014/10 edit-Start 医療機関情報の無効化対応
//            new VRTableColumn(1, 180, "医師氏名"),
//            new VRTableColumn(2, 260, "医療機関名"),
//            new VRTableColumn(3, 120, "郵便番号"),
//            new VRTableColumn(4, 260, "所在地"),
//            new VRTableColumn(5, 160, "連絡先(電話)"),
//            new VRTableColumn(6, 160, "連絡先(FAX)"),
//            new VRTableColumn(7, 160, "連絡先(携帯)"),
//            new VRTableColumn(8, 260, "緊急時連絡先"),
//            new VRTableColumn(9, 260, "不在時対応法"),
//            new VRTableColumn(10, 300, "備考")
// [ID:0000801][Ryosuke Koinuma] 2016/10 del-Start 医師の並び順の変更対応
//            getEnabledColumn(),
//            new VRTableColumn(2, 180, "医師氏名"),
//            new VRTableColumn(3, 260, "医療機関名"),
//            new VRTableColumn(4, 120, "郵便番号"),
//            new VRTableColumn(5, 260, "所在地"),
//            new VRTableColumn(6, 160, "連絡先(電話)"),
//            new VRTableColumn(7, 160, "連絡先(FAX)"),
//            new VRTableColumn(8, 160, "連絡先(携帯)"),
//            new VRTableColumn(9, 260, "緊急時連絡先"),
//            new VRTableColumn(10, 260, "不在時対応法"),
//            new VRTableColumn(11, 300, "備考")
// [ID:0000801][Ryosuke Koinuma] 2016/10 del-End
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-Start 医師の並び順の変更対応
            getEnabledColumn(),
            new VRTableColumn(2, 180, "医師氏名"),
            new VRTableColumn(3, 260, "ふりがな"),
            new VRTableColumn(4, 260, "医療機関名"),
            new VRTableColumn(5, 120, "郵便番号"),
            new VRTableColumn(6, 260, "所在地"),
            new VRTableColumn(7, 160, "連絡先(電話)"),
            new VRTableColumn(8, 160, "連絡先(FAX)"),
            new VRTableColumn(9, 160, "連絡先(携帯)"),
            new VRTableColumn(10, 260, "緊急時連絡先"),
            new VRTableColumn(11, 260, "不在時対応法"),
            new VRTableColumn(12, 300, "備考")
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-End
// [ID:0000787][Satoshi Tokusari] 2014/10 edit-End
        })
            );

        /*
        //テーブルの設定
        tableCellRenderer = new IkenshoIryouKikanJouhouTableCellRenderer("MI_DEFAULT", "1",
            IkenshoIryouKikanJouhouTableCellRenderer.TGT_CELL, "DR_NM");
        tableCellRenderer.setData(data);
        table.getTable().setDefaultRenderer(Object.class, tableCellRenderer);
        */
    }

// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start 医療機関情報の無効化対応
    /**
     * 医療機関情報一覧：有効を取得します。
     * 
     * @return 医療機関情報一覧：有効
     */
    public ACTableColumn getEnabledColumn() {
        if (enabledColumn == null) {
        	enabledColumn = new ACTableColumn(1);
            enabledColumn.setHeaderValue("有効");
            enabledColumn.setColumns(3);
            enabledColumn.setHorizontalAlignment(SwingConstants.CENTER);
            enabledColumn.setRendererType(ACTableCellViewer.RENDERER_TYPE_ICON);
            // テーブルカラムにフォーマッタを設定する。
            enabledColumn.setFormat(new ACHashMapFormat(new String[] {
                    "jp/nichicom/ac/images/icon/pix16/btn_079.png",
                    "jp/nichicom/ac/images/icon/pix16/btn_080.png" },
                    new Integer[] { new Integer(0), new Integer(1), }));
        }
        return enabledColumn;
    }
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End

    /**
     * テーブルの初期選択行の選択を行います。
     * @param affair IkenshoAffair
     * @throws Exception
     */
    private void setInitSelectedRow(ACAffairInfo affair) throws Exception {
        int selectedRow = 0;

        //渡りパラメータのデータを選択
        if (affair != null) {
            //渡りパラメータ取得
            VRMap params = affair.getParameters();
            if (params != null) {
                if (params.size() > 0) {
                    String key = "DR_CD";
                    String value = String.valueOf(params.get(key));
                    if (value != null) {
                        //渡りデータの行を検索
                        for (int i = 0; i < data.size(); i++) {
                            if (String.valueOf(((VRMap)data.getData(i)).get(key)).equals(value)) {
                                selectedRow = i;
                                break;
                            }
                        }
                    }
                }
            }
        }

        //テーブルの行を選択
        if (table.getRowCount() > 0) {
            table.setSelectedModelRow(selectedRow);
        }
    }

    /**
     * ツールボタン、メニューの有効状態を設定します。
     */
    private void setButtonsEnabled() {
        boolean enabled = false;
        if (table.getRowCount() > 0) {
            if (table.getSelectedModelRow() < 0) {
                enabled = false;
            }
            else {
                enabled = true;
            }
        }

        detail.setEnabled(enabled);
        copy.setEnabled(enabled);
        delete.setEnabled(enabled);
        detailMenu.setEnabled(enabled);
        copyMenu.setEnabled(enabled);
        deleteMenu.setEnabled(enabled);
    }
}
