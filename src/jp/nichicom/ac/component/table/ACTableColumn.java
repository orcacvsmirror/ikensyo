package jp.nichicom.ac.component.table;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import jp.nichicom.ac.component.style.ACComponentStylizer;
import jp.nichicom.ac.text.ACNullToBlankFormat;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableColumn;

/**
 * テーブル用のカラムです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRTableColumn
 * @see ACTableCellViewer
 * @see ACComponentStylizer
 */
public class ACTableColumn extends VRTableColumn implements
        PropertyChangeListener {
    private ACComponentStylizer cellEditorStylizer;
    private ACComponentStylizer cellRendererStylizer;
    private String columnName;
    private int columnNameModelIndex = -1;
    private int columns;
    private int columnWidth;
    private List customCells;
    private JPopupMenu headerPopupMenu;
    private boolean cellEditorStylizeOnRowValue;
    private boolean cellRendererStylizeOnRowValue;

    /**
     * セルエディタ用のスタイライザには行データを渡すか を返します。
     * @return セルエディタ用のスタイライザには行データを渡すか
     */
    public boolean isCellEditorStylizeOnRowValue() {
        return cellEditorStylizeOnRowValue;
    }

    /**
     * セルエディタ用のスタイライザには行データを渡すか を設定します。
     * @param cellEditorStylizeOnRowValue セルエディタ用のスタイライザには行データを渡すか
     */
    public void setCellEditorStylizeOnRowValue(boolean cellEditorStylizeOnRowValue) {
        this.cellEditorStylizeOnRowValue = cellEditorStylizeOnRowValue;
    }

    /**
     * セルレンダラ用のスタイライザには行データを渡すか を返します。
     * @return セルレンダラ用のスタイライザには行データを渡すか
     */
    public boolean isCellRendererStylizeOnRowValue() {
        return cellRendererStylizeOnRowValue;
    }

    /**
     * セルレンダラ用のスタイライザには行データを渡すか を設定します。
     * @param cellRendererStylizeOnRowValue セルレンダラ用のスタイライザには行データを渡すか
     */
    public void setCellRendererStylizeOnRowValue(
            boolean cellRendererStylizeOnRowValue) {
        this.cellRendererStylizeOnRowValue = cellRendererStylizeOnRowValue;
    }

    /**
     * Cover method, using a default model index of 0, default width of 75, a
     * <code>null</code> renderer and a <code>null</code> editor. This
     * method is intended for serialization.
     */
    public ACTableColumn() {
        super();
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * Cover method, using a default width of 75, a <code>null</code> renderer
     * and a <code>null</code> editor.
     */
    public ACTableColumn(int modelIndex) {
        super(modelIndex);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * Cover method, using a <code>null</code> renderer and a
     * <code>null</code> editor.
     */
    public ACTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     */
    public ACTableColumn(int modelIndex, int width, String header) {
        super(modelIndex, width, header);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param format フォーマット
     */
    public ACTableColumn(int modelIndex, int width, String header, Format format) {
        super(modelIndex, width, header, format);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param format フォーマット
     */
    public ACTableColumn(int modelIndex, int width, String header,
            Format format, TableCellRenderer cellRenderer,
            TableCellEditor cellEditor) {
        super(modelIndex, width, header, format);
        setCellRenderer(cellRenderer);
        setCellEditor(cellEditor);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param horizontalAlignment 水平方向の文字揃え
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment) {
        super(modelIndex, width, header, horizontalAlignment);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param horizontalAlignment 水平方向の文字揃え
     * @param format フォーマット
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, Format format) {
        super(modelIndex, width, header, horizontalAlignment, format);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param horizontalAlignment 水平方向の文字揃え
     * @param format フォーマット
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, Format format,
            TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, header, horizontalAlignment, format);
        setCellRenderer(cellRenderer);
        setCellEditor(cellEditor);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param horizontalAlignment 水平方向の文字揃え
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, TableCellRenderer cellRenderer,
            TableCellEditor cellEditor) {
        super(modelIndex, width, header, horizontalAlignment);
        setCellRenderer(cellRenderer);
        setCellEditor(cellEditor);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     */
    public ACTableColumn(int modelIndex, int width, String header,
            TableCellRenderer renderer, TableCellEditor editor) {
        super(modelIndex, width, header);
        setCellRenderer(renderer);
        setCellEditor(editor);
        this.setEditable(true);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param cellRenderer セルレンダラ
     * @param cellEditor セルエディタ
     */
    public ACTableColumn(int modelIndex, int width,
            TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
        setFormat(ACNullToBlankFormat.getInstance());
        this.setEditable(true);
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param header ヘッダ
     */
    public ACTableColumn(int modelIndex, String header) {
        super(modelIndex, header);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    public void columnClick(MouseEvent e, int column) {
        JPopupMenu menu = getHeaderPopupMenu();
        if (menu instanceof JPopupMenu) {
            // ポップアップメニューがあれば表示する
            menu.show((Component) e.getSource(), e.getX(), e.getY());
        } else {
            super.columnClick(e, column);
        }
    }

    /**
     * セルエディタ用のスタイライザ を返します。
     * <p>
     * nullの場合、スタイライザを使用しません。
     * </p>
     * 
     * @return セルエディタ用のスタイライザ
     */
    public ACComponentStylizer getCellEditorStylizer() {
        return cellEditorStylizer;
    }

    /**
     * セルレンダラ用のスタイライザ を返します。
     * <p>
     * nullの場合、スタイライザを使用しません。
     * </p>
     * 
     * @return セルレンダラ用のスタイライザ
     */
    public ACComponentStylizer getCellRendererStylizer() {
        return cellRendererStylizer;
    }

    /**
     * 参照するテーブルモデルにおけるカラム名 を返します。
     * 
     * @return 参照するテーブルモデルにおけるカラム名
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * 表示桁数 を返します。
     * 
     * @return 表示桁数
     */
    public int getColumns() {
        return columns;
    }

    /**
     * カスタムセル設定集合 を返します。
     * <p>
     * <code>NCTableCellViewerCustomCell</code>を連ねたListもしくはnullが返ります。
     * </p>
     * 
     * @return カスタムセル設定
     */
    public List getCustomCells() {
        return customCells;
    }

    /**
     * ヘッダをクリックしたときに表示するポップアップメニュー を返します。
     * <p>
     * nullの場合、ポップアップメニューではなくデフォルト処理（ソート）となります。
     * </p>
     * 
     * @return ヘッダをクリックしたときに表示するポップアップメニュー
     */
    public JPopupMenu getHeaderPopupMenu() {
        return headerPopupMenu;
    }

    public int getModelIndex() {
        int colNameIndex = getColumnNameModelIndex();
        if (colNameIndex >= 0) {
            // カラム名からModelIndexが求まっている場合
            return colNameIndex;
        }
        return super.getModelIndex();
    }

    /**
     * カラムを表示するかを返します。
     * 
     * @return boolean カラムを表示するか
     */
    public boolean isVisible() {
        return super.isVisible() && (getWidth() > 0);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("model".equals(evt.getPropertyName())) {
            // モデルが変更された場合
            checkColumnName();
        }
    }

    public void setCellEditor(TableCellEditor cellEditor) {
        if (cellEditor instanceof ACTableCellViewer) {
            // カスタムエディタを設定
            ((ACTableCellViewer) cellEditor).setCustomCells(getCustomCells());
        }
        super.setCellEditor(cellEditor);
    }

    /**
     * セルエディタ用のスタイライザ を設定します。
     * <p>
     * nullの場合、スタイライザを使用しません。
     * </p>
     * 
     * @param cellEditorStylizer セルエディタ用のスタイライザ
     */
    public void setCellEditorStylizer(ACComponentStylizer cellEditorStylizer) {
        this.cellEditorStylizer = cellEditorStylizer;
    }

    public void setCellRenderer(TableCellRenderer cellRenderer) {
        if (cellRenderer instanceof ACTableCellViewer) {
            // カスタムレンダラを設定
            ((ACTableCellViewer) cellRenderer).setCustomCells(getCustomCells());
        }
        super.setCellRenderer(cellRenderer);
    }

    /**
     * セルレンダラ用のスタイライザ を設定します。
     * <p>
     * nullの場合、スタイライザを使用しません。
     * </p>
     * 
     * @param cellRendererStylizer セルレンダラ用のスタイライザ
     */
    public void setCellRendererStylizer(ACComponentStylizer cellRendererStylizer) {
        this.cellRendererStylizer = cellRendererStylizer;
    }

    /**
     * 参照するテーブルモデルにおけるカラム名 を設定します。
     * 
     * @param columnName 参照するテーブルモデルにおけるカラム名
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
        checkColumnName();
    }

    /**
     * 表示桁数 を設定します。
     * 
     * @param columns 表示桁数
     */
    public void setColumns(int columns) {
        this.columns = columns;
        if (getTable() != null) {
            // カラム計算キャッシュをクリア
            columnWidth = 0;
            // 表示桁数が設定されていれば、幅を再設定する
            setPreferredWidth(calcurateColumnsWidth());
        }
    }

    /**
     * カスタムセル設定集合 を設定します。
     * <p>
     * <code>NCTableCellViewerCustomCell</code>を連ねたListを設定します。
     * </p>
     * 
     * @param customCells カスタムセル設定集合
     */
    public void setCustomCells(List customCells) {
        this.customCells = customCells;
        if (getCellRenderer() instanceof ACTableCellViewer) {
            // カスタムレンダラを設定
            ((ACTableCellViewer) getCellRenderer())
                    .setCustomCells(getCustomCells());
        }
    }

    /**
     * ヘッダをクリックしたときに表示するポップアップメニュー を設定します。
     * <p>
     * nullの場合、ポップアップメニューではなくデフォルト処理（ソート）となります。
     * </p>
     * 
     * @param headerPopupMenu ヘッダをクリックしたときに表示するポップアップメニュー
     */
    public void setHeaderPopupMenu(JPopupMenu headerPopupMenu) {
        this.headerPopupMenu = headerPopupMenu;
    }

    public void setTable(JTable table) {
        if (getTable() != null) {
            getTable().removePropertyChangeListener(this);
        }
        super.setTable(table);

        if (table != null) {
            int columns = getColumns();
            if (columns > 0) {
                // カラム計算キャッシュをクリア
                columnWidth = 0;
                // 表示桁数が設定されていれば、幅を再設定する
                setPreferredWidth(calcurateColumnsWidth());
            }
            // モデルの変更検知用にリスナとして登録
            table.addPropertyChangeListener(this);
        }
    }

    /**
     * 表示桁数に応じたカラム幅を返します。
     * 
     * @return 表示桁数に応じたカラム幅
     */
    protected int calcurateColumnsWidth() {
        return getColumns() * getColumnWidth();
    }

    /**
     * テーブルモデルからカラム名に対応するをModelIndexを取得します。
     */
    protected void checkColumnName() {
        if (getTable() != null) {
            TableModel model = getTable().getModel();
            if (model != null) {
                if (isUseColumnName()) {
                    // /カラム名が指定されているならば対応するModelIndexを読み直す
                    String colName = getColumnName();
                    int end = model.getColumnCount();
                    for (int i = 0; i < end; i++) {

                        if (colName.equals(model.getColumnName(i))) {
                            setColumnNameModelIndex(i);
                            return;
                        }
                    }
                }
            }
        }
        setColumnNameModelIndex(-1);
    }

    protected VRTableCellViewer createTableCellViewer() {
        return new ACTableCellViewer();
    }

    /**
     * カラム名に対応するModelIndex を返します。
     * 
     * @return カラム名に対応するModelIndex
     */
    protected int getColumnNameModelIndex() {
        return columnNameModelIndex;
    }

    /**
     * カラム幅を計算して返します。
     * 
     * @return カラム幅
     */
    protected int getColumnWidth() {
        if (columnWidth == 0) {
            if (getTable() != null) {
                FontMetrics fm = getTable()
                        .getFontMetrics(getTable().getFont());
                columnWidth = fm.charWidth('m');
                // 日本語文字対応のため、1.1倍する
                columnWidth = (int) (columnWidth * 1.1);
            }
        }
        return columnWidth;
    }

    /**
     * カラム名を使用するかを返します。
     * 
     * @return カラム名を使用するか
     */
    protected boolean isUseColumnName() {
        String colName = getColumnName();
        return (colName != null) && (!"".equals(colName));
    }

    /**
     * カラム名に対応するModelIndex を設定します。
     * 
     * @param columnNameModelIndex カラム名に対応するModelIndex
     */
    protected void setColumnNameModelIndex(int columnNameModelIndex) {
        this.columnNameModelIndex = columnNameModelIndex;
    }
}
