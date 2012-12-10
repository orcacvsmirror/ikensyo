/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.AbstractButton;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.core.ACAffairContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACBrowseLogWritable;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.sql.ACSQLUtilities;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroIntegerFormat;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoPassiveCheck;

//[ID:0000754][Shin Fujihara] 2012/09 edit begin 2012年度対応 閲覧ログ出力機能
//public class IkenshoAffairContainer extends ACAffairContainer {
public class IkenshoAffairContainer extends ACAffairContainer implements ACBrowseLogWritable {
//[ID:0000754][Shin Fujihara] 2012/09 edit end 2012年度対応 閲覧ログ出力機能
    private CopyActionAdapter copy = new CopyActionAdapter(this);

    private DeleteActionAdapter delete = new DeleteActionAdapter(this);
    private DetailActionAdapter detail = new DetailActionAdapter(this);
    private FindActionAdapter find = new FindActionAdapter(this);
    private InsertActionAdapter insert = new InsertActionAdapter(this);
    private PrintActionAdapter print = new PrintActionAdapter(this);
    private PrintTableActionAdapter printTable = new PrintTableActionAdapter(
            this);
    private TableDoubleClickAdapter tableDoubleClick = new TableDoubleClickAdapter(
            this);
    private TableSelectionAdapter tableSelected = new TableSelectionAdapter(
            this);
    private UpdateActionAdapter update = new UpdateActionAdapter(this);
    protected IkenshoPassiveCheck passiveChecker = new IkenshoPassiveCheck();
    public IkenshoAffairContainer() {
        super();
    }

    /**
     * 複製処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addCopyTrigger(AbstractButton comp) {
        comp.addActionListener(copy);
    }

    /**
     * 削除処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addDeleteTrigger(AbstractButton comp) {
        comp.addActionListener(delete);
    }

    /**
     * 詳細表示処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addDetailTrigger(AbstractButton comp) {
        comp.addActionListener(detail);
    }

    /**
     * 検索処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addFindTrigger(AbstractButton comp) {
        comp.addActionListener(find);
    }

    /**
     * 追加処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addInsertTrigger(AbstractButton comp) {
        comp.addActionListener(insert);
    }

    /**
     * 削除パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     */
    protected void addPassiveDeleteTask(ACPassiveKey key, int row) {
        passiveChecker.addPassiveDeleteTask(key, row);
    }

    /**
     * 追加パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     */
    protected void addPassiveInsertTask(ACPassiveKey key, int row) {
        passiveChecker.addPassiveInsertTask(key, row);
    }

    /**
     * 更新パッシブチェックタスクを追加します。
     * 
     * @param key 比較キー
     * @param row 対象行番号
     */
    protected void addPassiveUpdateTask(ACPassiveKey key, int row) {
        passiveChecker.addPassiveUpdateTask(key, row);
    }

    /**
     * 一覧印刷処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addPrintTableTrigger(AbstractButton comp) {
        comp.addActionListener(printTable);
    }

    /**
     * 印刷処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addPrintTrigger(AbstractButton comp) {
        comp.addActionListener(print);
    }

    /**
     * テーブルのダブルクリック処理のトリガとなるテーブルを追加します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void addTableDoubleClickedTrigger(JTable comp) {
        comp.addMouseListener(tableDoubleClick);
    }

    /**
     * テーブルのダブルクリック処理のトリガとなるテーブルを追加します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void addTableDoubleClickedTrigger(ACTable comp) {
        comp.addMouseListener(tableDoubleClick);
        // addTableDoubleClickedTrigger(comp.getTable());
    }

    /**
     * テーブル選択処理のトリガとなるテーブルを追加します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void addTableSelectedTrigger(JTable comp) {
        comp.getSelectionModel().addListSelectionListener(tableSelected);
    }

    /**
     * テーブル選択処理のトリガとなるテーブルを追加します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void addTableSelectedTrigger(ACTable comp) {
        comp.addListSelectionListener(tableSelected);
    }

    /**
     * 更新処理のトリガとなるボタンを追加します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void addUpdateTrigger(AbstractButton comp) {
        comp.addActionListener(update);
    }

    /**
     * パッシブチェックタスクを初期化します。
     */
    protected void clearPassiveTask() {
        passiveChecker.clearPassiveTask();
    }

    /**
     * パッシブチェック用の検索結果を初期化します。
     */
    protected void clearReservedPassive() {
        passiveChecker.clearReservedPassive();
    }

    /**
     * Over rideして複製処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void copyActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なcopyActionPerformedメソッドが呼ばれました");
    }

    /**
     * Over rideして削除処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なdeleteActionPerformedメソッドが呼ばれました");
    }

    /**
     * Over rideして詳細表示処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void detailActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なdetailActionPerformedメソッドが呼ばれました");
    }

    /**
     * Over rideして検索処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void findActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なfindActionPerformedメソッドが呼ばれました");
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な日付文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    protected String getDBSafeDate(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeDate(key, source);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な数値文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    protected String getDBSafeNumber(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeNumber(key, source);
    }
    /**
     * ソース内の指定キーの値をDBへ格納可能な数値文字列として返します。
     * Nullの場合は0を返します。
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    protected String getDBSafeNumberNullToZero(String key, VRBindSource source)
            throws ParseException {
        return ACSQLSafeNullToZeroIntegerFormat.getInstance().format(VRBindPathParser.get(key, source));
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    protected String getDBSafeString(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeString(key, source);
    }

    /**
     * ソース内の指定キーの値をDBへ格納可能な日時文字列として返します。
     * 
     * @param key 取得キー
     * @param source ソース
     * @throws ParseException 解析例外
     * @return 変換結果
     */
    protected String getDBSafeTime(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeTime(key, source);
    }

    /**
     * パッシブチェックを実行します。
     * 
     * @throws Exception 処理例外
     * @return boolean チェック成功
     */
    protected IkenshoFirebirdDBManager getPassiveCheckedDBManager()
            throws Exception {
        return passiveChecker.getPassiveCheckedDBManager();
    }

    /**
     * Over rideして追加処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void insertActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なinsertActionPerformedメソッドが呼ばれました");
    }

    /**
     * 文字列がNullまたは空文字であるかを返します。
     * 
     * @param obj 評価文字列
     * @return 文字列がNullまたは空文字であるか
     */
    protected boolean isNullText(Object obj) {
        return ACCommon.getInstance().isNullText(obj);
    }

    /**
     * PDFファイルを生成し、生成したPDFファイルを開きます。
     * 
     * @param pd 印刷データ
     * @throws Exception
     */
    protected void openPDF(ACChotarouXMLWriter pd) throws Exception {
        ACChotarouXMLUtilities.getInstance().openPDF(pd);
    }

    /**
     * Over rideして印刷処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void printActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なprintActionPerformedメソッドが呼ばれました");
    }

    /**
     * Over rideして一覧印刷処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なprintTableActionPerformedメソッドが呼ばれました");
    }

    /**
     * 複製処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removeCopyTrigger(AbstractButton comp) {
        comp.removeActionListener(copy);
    }

    /**
     * 削除処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removeDeleteTrigger(AbstractButton comp) {
        comp.removeActionListener(delete);
    }

    /**
     * 詳細表示処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removeDetailTrigger(AbstractButton comp) {
        comp.removeActionListener(detail);
    }

    /**
     * 検索処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removeFindTrigger(AbstractButton comp) {
        comp.removeActionListener(find);
    }

    /**
     * 追加処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removeInsertTrigger(AbstractButton comp) {
        comp.removeActionListener(insert);
    }

    /**
     * 一覧印刷処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removePrintTableTrigger(AbstractButton comp) {
        comp.removeActionListener(printTable);
    }

    /**
     * 印刷処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removePrintTrigger(AbstractButton comp) {
        comp.removeActionListener(print);
    }

    /**
     * テーブルのダブルクリック処理のトリガとなるテーブルを除外します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void removeTableDoubleClickedTrigger(JTable comp) {
        comp.removeMouseListener(tableDoubleClick);
    }

    /**
     * テーブルのダブルクリック処理のトリガとなるテーブルを除外します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void removeTableDoubleClickedTrigger(ACTable comp) {
        comp.removeMouseListener(tableDoubleClick);
    }

    /**
     * テーブル選択処理のトリガとなるテーブルを除外します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void removeTableSelectedTrigger(JTable comp) {
        comp.getSelectionModel().removeListSelectionListener(tableSelected);
    }

    /**
     * テーブル選択処理のトリガとなるテーブルを除外します。
     * 
     * @param comp トリガとなるテーブル
     */
    protected void removeTableSelectedTrigger(ACTable comp) {
        comp.getSelectionModel().removeListSelectionListener(tableSelected);
    }

    /**
     * 更新処理のトリガとなるボタンを除外します。
     * 
     * @param comp トリガとなるボタン
     */
    protected void removeUpdateTrigger(AbstractButton comp) {
        comp.removeActionListener(update);
    }

    /**
     * パッシブチェック用の検索結果を退避します。
     * 
     * @param key 比較キー
     * @param data 検索結果
     */
    protected void reservedPassive(ACPassiveKey key, VRArrayList data) {
        passiveChecker.reservedPassive(key, data);
    }

    /**
     * Over rideしてテーブルのダブルクリック処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void tableDoubleClicked(MouseEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なtableDoubleClickedメソッドが呼ばれました");
    }

    /**
     * Over rideしてテーブル選択処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void tableSelected(ListSelectionEvent e) throws Exception {
        throw new RuntimeException("明示的なOver rideが必要なtableSelectedメソッドが呼ばれました");
    }

    /**
     * Over rideして更新処理を記述します。
     * 
     * @param e イベント情報
     * @throws Exception 処理例外
     */
    protected void updateActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "明示的なOver rideが必要なupdateActionPerformedメソッドが呼ばれました");
    }

//    /**
//     * 規定アダプタクラス
//     */
//    private abstract class AbstractActionAdapter implements ActionListener {
//        protected IkenshoAffairContainer adaptee;
//        protected boolean lockFlag = false;
//    }

    /**
     * 複製表示処理アダプタクラス
     */
    private class CopyActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public CopyActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.copyActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 削除処理アダプタクラス
     */
    private class DeleteActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public DeleteActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.deleteActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 詳細表示処理アダプタクラス
     */
    private class DetailActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public DetailActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.detailActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 検索処理アダプタクラス
     */
    private class FindActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public FindActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.findActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 追加処理アダプタクラス
     */
    private class InsertActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public InsertActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.insertActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 印刷表示処理アダプタクラス
     */
    private class PrintActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public PrintActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.printActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 一覧印刷表示処理アダプタクラス
     */
    private class PrintTableActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public PrintTableActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.printTableActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * テーブルのダブルクリック処理アダプタクラス
     */
    private class TableDoubleClickAdapter extends MouseAdapter {
        protected IkenshoAffairContainer adaptee;
        protected boolean lockFlag = false;

        public TableDoubleClickAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void mouseClicked(MouseEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            if (e.getClickCount() == 2) {
                try {
                    adaptee.tableDoubleClicked(e);
                } catch (Exception ex) {
                    ACCommon.getInstance().showExceptionMessage(ex);
                }
            }
            lockFlag = false;
        }

    }

    /**
     * テーブル選択処理アダプタクラス
     */
    private class TableSelectionAdapter implements ListSelectionListener {
        protected IkenshoAffairContainer adaptee;
        protected boolean lockFlag = false;

        public TableSelectionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void valueChanged(ListSelectionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.tableSelected(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * 更新処理アダプタクラス
     */
    private class UpdateActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public UpdateActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.updateActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    //[ID:0000754][Shin Fujihara] 2012/09 edit begin 2012年度対応 閲覧ログ出力機能
	public void writeBrowseLog(ACAffairInfo affair) {
		IkenshoBrowseLogger.log(affair);
	}
	//[ID:0000754][Shin Fujihara] 2012/09 edit end 2012年度対応 閲覧ログ出力機能
	
}
