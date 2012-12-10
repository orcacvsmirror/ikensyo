package jp.nichicom.ac.util.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jp.nichicom.ac.component.table.ACTableCellViewerCustomCell;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.table.VRSortableTableModel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRTableModelAdapter;

/**
 * テーブルモデル用のアダプタです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRSortableTableModel
 */

public class ACTableModelAdapter extends VRTableModelAdapter {

    private HashMap parsedConcatStatusCache = new HashMap();

    /**
     * コンストラクタです。
     */
    public ACTableModelAdapter() {
        super(new VRArrayList(), new String[] {});
    }

    /**
     * コンストラクタです。
     * 
     * @param columns カラム定義
     */
    public ACTableModelAdapter(String[] columns) {
        super(new VRArrayList(), columns);
    }

    /**
     * コンストラクタです。
     * 
     * @param adaptee アダプティーとなるバインドソース
     * @param columns カラム定義
     */
    public ACTableModelAdapter(VRBindSource adaptee, String[] columns) {
        super(adaptee, columns);
    }

    /**
     * カラム名が引数と一致する列番号を返します。
     * 
     * @param columnName カラム名
     * @return 列番号
     */
    public int getColumnIndex(String columnName) {
        if (columnName != null) {
            String[] columns = getColumns();
            if (columns != null) {
                int end = columns.length;
                for (int i = 0; i < end; i++) {
                    if (columnName.equals(columns[i])) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = (Object[]) getParsedConcatStatusCache().get(
                new Integer(columnIndex));
        if (obj instanceof Object[]) {
            Object[] statuses = (Object[]) obj;
            // 結合設定を解釈する
            StringBuffer sb = new StringBuffer();
            int end = statuses.length;
            for (int i = 0; i < end; i++) {
                Object status = statuses[i];
                if (status instanceof Integer) {
                    // カラムのインデックス指定
                    Object val = super.getValueAt(rowIndex, ((Integer) status)
                            .intValue());
                    if (val != null) {
                        sb.append(val);
                    }
                } else {
                    // リテラル
                    sb.append(status);
                }
            }
            return sb.toString();

        }
        // 結合設定がなされていないカラムならば標準の値取得メソッドを使用する
        return super.getValueAt(rowIndex, columnIndex);
    }

    public boolean isCellEditable(int row, int column) {
        if (getTable() != null) {
            TableColumnModel model = getTable().getColumnModel();
            if (model != null) {
                int end = model.getColumnCount();
                for (int i = 0; i < end; i++) {
                    TableColumn tableColumn = model.getColumn(i);
                    if (column == tableColumn.getModelIndex()) {
                        if (tableColumn instanceof VRTableColumn) {
                            if (tableColumn instanceof ACTableColumn) {
                                // NCテーブルカラムの場合、カスタムセル設定も確認する。
                                List cells = ((ACTableColumn) tableColumn)
                                        .getCustomCells();
                                if ((cells != null) && (cells.size() <= row)) {
                                    Object obj = cells.get(row);
                                    if (obj instanceof ACTableCellViewerCustomCell) {
                                        // カスタムセルの指定を優先する
                                        return ((ACTableCellViewerCustomCell) obj)
                                                .isEditable();
                                    }
                                }
                            }
                            VRTableColumn ncc = (VRTableColumn) tableColumn;
                            return ncc.isVisible() & ncc.isEditable();
                        }
                    }
                }
            }
        }
        return super.isCellEditable(row, column);
    }

    public void setColumns(String[] columns) {
        super.setColumns(columns);
        // キャッシュをクリア
        setParsedConcatStatusCache(new HashMap());
        parseConcatStatus();
    }

    /**
     * 結合設定の解析済みキャッシュ を返します。
     * <p>
     * キャッシュされるキー要素はそれぞれObject[]で構成されます。<br />
     * Object[]の要素はIntegerならばカラムの列番号、それ以外はリテラル文字と見なします。
     * </p>
     * 
     * @return 結合設定の解析済みキャッシュ
     */
    protected HashMap getParsedConcatStatusCache() {
        return parsedConcatStatusCache;
    }

    /**
     * 結合設定を解析します。
     */
    protected void parseConcatStatus() {
        HashMap map = new HashMap();
        String[] columns = getColumns();
        int end = columns.length;
        for (int i = 0; i < end; i++) {
            // すべてのカラムを走査する
            map.put(new Integer(i), parseConcatStatus(columns[i]));
        }
        // キャッシュに登録
        getParsedConcatStatusCache().putAll(map);
    }

    /**
     * 結合設定を解析します。
     * <p>
     * 結合設定がなされていないから無名であれば、nullを返します。
     * </p>
     * 
     * @param column 解析対象のカラム名
     * @return 解析結果
     */
    protected Object[] parseConcatStatus(String column) {
        // 結合識別子
        final String CONCAT_TOKEN = "+";
        // エスケープシーケンス
        final char ESCAPE_TAG = '\\';
        // リテラル文字を囲む識別子
        final String LITERAL_QUOTE = "'";

        String[] array = column.split("\\" + CONCAT_TOKEN);
        int arrayLength = array.length;
        if (arrayLength <= 1) {
            // トークン(+)がなければリテラルを含まない
            return null;
        }

        StringBuffer sb = new StringBuffer();
        ArrayList list = new ArrayList();
        for (int j = 0; j < arrayLength; j++) {
            // カラム名を+で分割して走査する
            String concat = array[j];
            if (!"".equals(concat)) {
                boolean escaping = false;
                int len = concat.length();
                for (int k = 0; k < len; k++) {
                    // エスケープシーケンスの走査
                    char c = concat.charAt(k);
                    if (c == ESCAPE_TAG) {
                        if (escaping) {
                            // エスケープシーケンスの連続はエスケープ文字
                            sb.append(ESCAPE_TAG);
                        }
                        escaping = !escaping;
                    } else {
                        sb.append(concat.charAt(k));
                        escaping = false;
                    }
                }
                if (escaping) {
                    // エスケープシーケンスで終わった
                    // →次の要素を連結する
                    sb.append(CONCAT_TOKEN);
                    continue;
                }

                String val = sb.toString();
                if (val.startsWith(LITERAL_QUOTE)) {
                    // リテラル開始文字で始まっている
                    if (!val.endsWith(LITERAL_QUOTE)) {
                        // リテラル終端文字で終わっていない（リテラル中のトークン）
                        // →次の要素を連結する
                        sb.append(CONCAT_TOKEN);
                        continue;
                    }
                    // リテラル文字として追加
                    list.add(val.substring(1, val.length() - 1));
                } else {
                    // カラムの列番号を追加
                    list.add(new Integer(getColumnIndex(val)));
                }
            }

            sb = new StringBuffer();
        }
        return list.toArray();
    }

    /**
     * 結合設定の解析済みキャッシュ を設定します。
     * 
     * @param parsedConcatStatusCache 結合設定の解析済みキャッシュ
     */
    protected void setParsedConcatStatusCache(HashMap parsedConcatStatusCache) {
        this.parsedConcatStatusCache = parsedConcatStatusCache;
    }

}
