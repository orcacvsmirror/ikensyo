package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoStationJouhouIchiran extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton copy = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private ACTable table = new ACTable();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem insertMenu = new JMenuItem();
    private JMenuItem copyMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();
    private VRArrayList data;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new
        ACPassiveKey("STATION", new String[] {"STATION_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");

    /**
     * コンストラクタです。
     */
    public IkenshoHoumonKangoStationJouhouIchiran() {
        try {
            jbInit();
            events();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * コンポーネントを設定します。
     * @throws Exception 設定例外
     */
    private void jbInit() throws Exception {
        buttons.setTitle("訪問看護ステーション情報一覧");
        this.add(buttons, VRLayout.NORTH);
        this.add(table, VRLayout.CLIENT);

        //ボタン系
        buttons.add(delete, BorderLayout.EAST);
        buttons.add(copy, BorderLayout.EAST);
        buttons.add(insert, BorderLayout.EAST);
        buttons.add(detail, BorderLayout.EAST);

        detail.setText("詳細情報(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("詳細情報(E)");
        detail.setToolTipText("選択された訪問看護ステーション情報の詳細画面へ移ります。");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        insert.setText("新規登録(N)");
        insert.setMnemonic('N');
        insert.setActionCommand("新規登録(N)");
        insert.setToolTipText("訪問看護ステーション情報を新規に作成します。");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);

        copy.setText("複製(C)");
        copy.setMnemonic('C');
        copy.setActionCommand("複製(C)");
        copy.setToolTipText("選択された訪問看護ステーション情報を複製します。");
        copy.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_COPY);

        delete.setText("削除(D)");
        delete.setMnemonic('D');
        delete.setActionCommand("削除(D)");
        delete.setToolTipText("選択された訪問看護ステーション情報を削除します。");
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

        //DBからステーションデータを取得
        doSelect(affair);
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent(){
        return table;
//      return table.getTable();
    }

    public boolean canBack(VRMap parameters) throws Exception{
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
        ACAffairInfo affair = new ACAffairInfo(IkenshoHoumonKangoStationJouhouShousai.class.
                                                 getName(), selectedRow, "訪問看護ステーション情報詳細");
        ACFrame.getInstance().next(affair);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        //どのボタンからの遷移かという情報を付加
        VRMap selectedRow = new VRHashMap();
        selectedRow.put("ACT", "insert");

        //遷移
        ACAffairInfo affair = new ACAffairInfo(IkenshoHoumonKangoStationJouhouShousai.class.
                                                 getName(), selectedRow, "訪問看護ステーション情報詳細");
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
        ACAffairInfo affair = new ACAffairInfo(IkenshoHoumonKangoStationJouhouShousai.class.
                                                 getName(), selectedRow, "訪問看護ステーション情報詳細");
        ACFrame.getInstance().next(affair);
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        delete.setEnabled(false);

        //選択行の取得
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //確認MSG
        String msg = "選択された情報を削除します。\nよろしいですか？";
        int result = ACMessageBox.show(msg,
                                            ACMessageBox.BUTTON_OKCANCEL,
                                            ACMessageBox.ICON_QUESTION,
                                            ACMessageBox.FOCUS_CANCEL);

        //最終選択位置(モデルではなく画面上)を退避
        int lastSelectedIndex = table.getSelectedSortedRow();

        if (result == ACMessageBox.RESULT_OK) {
            //DBとテーブルからデータを削除する
            IkenshoFirebirdDBManager dbm = null;
            try {
                //パッシブチェック
                clearPassiveTask();
                addPassiveDeleteTask(PASSIVE_CHECK_KEY, row);
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    //再SELECT
                    doSelect(null);
                    return;
                }
                //選択行の連携医コードを取得
                String stationCd = String.valueOf(((VRMap)data.get(row)).getData("STATION_CD"));

                //SQL文の生成
                String sql = "DELETE FROM STATION WHERE STATION_CD=" + stationCd;

                //SQLの実行
                dbm.executeUpdate(sql);

                //コミット
                dbm.commitTransaction();
            }
            catch (Exception ex) {
                //ロールバック
                if (dbm != null) {
                    dbm.rollbackTransaction();
                }
                setButtonsEnabled();
                throw new Exception(ex);
            }

            //再SELECT
            doSelect(null);

            //再選択
            table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);
        }
        // add sta s-fujihara 削除キャンセル時、削除ボタンが押下不可となる
        // 障害の対応
        else {
          setButtonsEnabled();
        }
        // add end s-fujihara
    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        setButtonsEnabled();
    }

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

        //ボタンのEnabled設定
        setButtonsEnabled();

        //テーブルにフォーカスを当てる
//        table.getTable().requestFocus();
        table.requestFocus();

        //ステータスバー
        setStatusText(String.valueOf(table.getRowCount()) + "件登録されています。");

        //テーブルの行を選択
        setInitSelectedRow(affair);
    }

    /**
     * DBからデータを取得します。
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        //DBからデータを取得
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT" );
        sb.append( " STATION_CD," );
        sb.append( " DR_NM," );
        sb.append( " MI_NM," );
        sb.append( " MI_POST_CD," );
        sb.append( " MI_ADDRESS," );
        sb.append( " MI_TEL1," );
        sb.append( " MI_TEL2," );
        sb.append( " MI_FAX1," );
        sb.append( " MI_FAX2," );
        sb.append( " MI_CEL_TEL1," );
        sb.append( " MI_CEL_TEL2," );
        sb.append( " KINKYU_RENRAKU," );
        sb.append( " FUZAIJI_TAIOU," );
        sb.append( " BIKOU," );
        sb.append( " LAST_TIME" );
        sb.append( " FROM" );
        sb.append( " STATION" );
        sb.append( " ORDER BY" );
        sb.append( " DR_NM ASC" );
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            //パッシブチェック
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }

        //表示用Fieldの追加
        for( int i = 0; i < data.getDataSize(); i++ ) {
            IkenshoTelTextField telTmp = new IkenshoTelTextField();
            //電話番号
            String tel1 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_TEL1"));
            String tel2 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_TEL2"));
            telTmp.setArea(tel1);
            telTmp.setNumber(tel2);
            ( (VRMap) data.getData(i)).setData("TEL", telTmp.getTelNo());

            //FAX
            String fax1 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_FAX1"));
            String fax2 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_FAX2"));
            telTmp.setArea(fax1);
            telTmp.setNumber(fax2);
            ( (VRMap) data.getData(i)).setData("FAX", telTmp.getTelNo());

            //連絡先(携帯)
            String celTel1 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_CEL_TEL1"));
            String celTel2 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_CEL_TEL2"));
            telTmp.setArea(celTel1);
            telTmp.setNumber(celTel2);
            ( (VRMap) data.getData(i)).setData("CEL_TEL", telTmp.getTelNo());
        }

        //テーブルの生成
        table.setModel(new ACTableModelAdapter(data, new String[] {
                                               "DR_NM",
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
//          new VRTableColumn(0, 150, "代表者名"),
//          new VRTableColumn(1, 150, "ステーション名"),
//          new VRTableColumn(2, 80, "郵便番号"),
//          new VRTableColumn(3, 200, "所在地"),
//          new VRTableColumn(4, 120, "連絡先(電話)"),
//          new VRTableColumn(5, 120, "連絡先(FAX)"),
//          new VRTableColumn(6, 120, "連絡先(携帯)"),
//          new VRTableColumn(7, 200, "緊急時連絡先"),
//          new VRTableColumn(8, 200, "不在時対応法"),            		
            new VRTableColumn(0, 180, "代表者名"),
            new VRTableColumn(1, 180, "ステーション名"),
            new VRTableColumn(2, 120, "郵便番号"),
            new VRTableColumn(3, 260, "所在地"),
            new VRTableColumn(4, 160, "連絡先(電話)"),
            new VRTableColumn(5, 160, "連絡先(FAX)"),
            new VRTableColumn(6, 160, "連絡先(携帯)"),
            new VRTableColumn(7, 260, "緊急時連絡先"),
            new VRTableColumn(8, 260, "不在時対応法"),
            new VRTableColumn(9, 300, "備考")
        })
            );
    }

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
                    String key = "STATION_CD";
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
