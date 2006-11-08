/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
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
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoInsurerTypeFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


public class IkenshoHokenshaIchiran extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private ACTable table = new ACTable();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem insertMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();
    private VRArrayList data;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new
        ACPassiveKey("INSURER", new String[] {"INSURER_NO", "INSURER_TYPE"}
                          , new Format[] {IkenshoConstants.FORMAT_PASSIVE_STRING, null}, "LAST_TIME", "LAST_TIME");

    /**
     * コンストラクタです。
     */
    public IkenshoHokenshaIchiran() {
        try {
            jbInit();
            events();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        buttons.setTitle("保険者一覧");
        this.add(buttons, VRLayout.NORTH);
        this.add(table, VRLayout.CLIENT);

        //ボタン系
        buttons.add(delete, BorderLayout.EAST);
        buttons.add(insert, BorderLayout.EAST);
        buttons.add(detail, BorderLayout.EAST);

        detail.setText("詳細情報(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("詳細情報(E)");
        detail.setToolTipText("選択された保険者の詳細画面へ移ります。");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        insert.setText("新規登録(N)");
        insert.setMnemonic('N');
        insert.setActionCommand("新規登録(N)");
        insert.setToolTipText("保険者情報を新規に作成します。");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);

        delete.setText("削除(D)");
        delete.setMnemonic('D');
        delete.setActionCommand("削除(D)");
        delete.setToolTipText("選択された保険者を削除します。");
        delete.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DELETE);

        //ポップアップ系
        popup.add(detailMenu);
        popup.add(insertMenu);
        popup.add(deleteMenu);

        detailMenu.setActionCommand("詳細情報(E)");
        detailMenu.setMnemonic('E');
        detailMenu.setText("詳細情報(E)");
        detailMenu.setToolTipText(detail.getToolTipText());

        insertMenu.setActionCommand("新規登録(N)");
        insertMenu.setMnemonic('N');
        insertMenu.setText("新規登録(N)");
        insertMenu.setToolTipText(insert.getToolTipText());

        deleteMenu.setActionCommand("削除(D)");
        deleteMenu.setMnemonic('D');
        deleteMenu.setText("削除(D)");
        deleteMenu.setToolTipText(delete.getToolTipText());
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        //メニューバーのボタンに対応するトリガーの設定
        addDetailTrigger(detail);
        addDetailTrigger(detailMenu);
        addInsertTrigger(insert);
        addInsertTrigger(insertMenu);
        addDeleteTrigger(delete);
        addDeleteTrigger(deleteMenu);
        addTableSelectedTrigger(table);

        //DBから保険者データを取得
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
        ACAffairInfo affair = new ACAffairInfo(IkenshoHokenshaShousai.class.
                                                 getName(), selectedRow, "保険者詳細");
        ACFrame.getInstance().next(affair);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        //どのボタンからの遷移かという情報を付加
        VRMap selectedRow = new VRHashMap();
        selectedRow.put("ACT", "insert");

        //遷移
        ACAffairInfo affair = new ACAffairInfo(IkenshoHokenshaShousai.class.
                                                 getName(), selectedRow, "保険者詳細");
        ACFrame.getInstance().next(affair);

        //後処理・ツールボタンやメニューの有効状態の設定
        setButtonsEnabled();
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        delete.setEnabled(false);

        //選択行の取得
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //選択行の連携医コードを取得
        String insurerNo = String.valueOf(((VRMap)data.get(row)).getData("INSURER_NO"));
        String insurerType = String.valueOf(((VRMap)data.get(row)).getData("INSURER_TYPE"));
        
        //保険者番号が他のテーブルに登録されていないかチェック
        if (hasInsurerNoInOtherTable(insurerNo, "IKN_ORIGIN")) {
            ACMessageBox.show("意見書に使用されている保険者番号は削除できません。");
            doSelect(null);//再SELECT
            return;
        }
        if (hasInsurerNoInOtherTable(insurerNo, "JIGYOUSHA")) {
            ACMessageBox.show("事業者マスタに使用されている保険者番号は削除できません。");
            doSelect(null);//再SELECT
            return;
        }


        //確認MSG
        String msg = "選択されている保険者のデータがすべて削除されます。\nよろしいですか？";
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
                addPassiveDeleteTask(PASSIVE_CHECK_KEY, row);
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
//                    //再SELECT
//                    doSelect(null);

                }else{
                //SQL文の生成
                String sql = "DELETE FROM INSURER WHERE INSURER_NO='" + insurerNo + "' AND INSURER_TYPE=" +insurerType;

                //SQLの実行
                dbm.executeUpdate(sql);

                //コミット
                dbm.commitTransaction();
                }
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

    /**
     * ポップアップメニューを表示します。
     * @param e イベント情報
     */
    private void showPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        popup.show( (Component) e.getSource(), e.getX(), e.getY());
      }
    }

    /**
     * 保険者番号が他のテーブルで使用されていないかどうかをチェックする
     * @param insurerNo String
     * @return boolean
     * @throws Exception
     */
    private boolean hasInsurerNoInOtherTable(String insurerNo, String TableName) throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT" );
        sb.append( " INSURER_NO" );
        sb.append( " FROM" );
        sb.append( " " + TableName );
        sb.append( " WHERE" );
        sb.append( " INSURER_NO='" + insurerNo + "'" );
        VRArrayList cnt = (VRArrayList) dbm.executeQuery(sb.toString());
        if (cnt.getDataSize() > 0) {
            return true;
        }
        return false;
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
        table.requestFocus();
//        table.getTable().requestFocus();

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
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT " );
        sb.append( " INSURER_NO," );
        sb.append( " INSURER_NM," );
        sb.append( " INSURER_TYPE," );
        sb.append( " FD_OUTPUT_UMU," );
        sb.append( " SEIKYUSHO_OUTPUT_PATTERN," );
        sb.append( " ISS_INSURER_NM," );
        sb.append( " SKS_INSURER_NM," );
        sb.append( " LAST_TIME" );
        sb.append( " FROM");
        sb.append( " INSURER" );
        sb.append( " ORDER BY" );
        sb.append( " INSURER_NO ASC" );
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            //パッシブチェック
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }

        //テーブルの生成
        table.setModel(new ACTableModelAdapter(data, new String[] {
                                               "INSURER_NO",
                                               "INSURER_NM",
                                               "INSURER_TYPE",
                                               "ISS_INSURER_NM",
                                               "SKS_INSURER_NM",
                                               "FD_OUTPUT_UMU",
                                               "SEIKYUSHO_OUTPUT_PATTERN"}));

        //ColumnModelの生成
        table.setColumnModel(new VRTableColumnModel(
            new VRTableColumn[] {
            new VRTableColumn(0, 70, "保険者番号"),
            new VRTableColumn(1, 167, "保険者名称"),
            new VRTableColumn(2, 50, "区分", SwingConstants.CENTER, IkenshoInsurerTypeFormat.getInstance()),
            new VRTableColumn(3, 167, "意見書作成料請求先"),
            new VRTableColumn(4, 167, "診察・検査料請求先"),
            new VRTableColumn(5, 50, "FD出力", SwingConstants.CENTER, IkenshoConstants.FORMAT_UMU),
            new VRTableColumn(6, 150, "請求パターン", IkenshoConstants.FORMAT_SEIKYUSHO_OUTPUT_PATTERN)
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
                    String key = "INSURER_NO";
                    String value = String.valueOf(params.getData(key));
                    String key2 = "INSURER_TYPE";
                    String value2 = String.valueOf(params.getData(key));
                    if (value != null) {
                        //渡りデータの行を検索
                        for (int i = 0; i < data.size(); i++) {
                            VRMap row = (VRMap)data.getData(i);
                            String tmp = String.valueOf(row.getData(key));
                            if (tmp.equals(value)) {
                                String tmp2 = String.valueOf(row.getData(key2));
                                if (tmp2.equals(value2)) {
                                    selectedRow = i;
                                    break;
                                }
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
        delete.setEnabled(enabled);
        detailMenu.setEnabled(enabled);
        deleteMenu.setEnabled(enabled);
    }
}
