package jp.nichicom.ac.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.component.table.VRTablar;
import jp.nichicom.vr.component.table.VRTable;
import jp.nichicom.vr.component.table.VRTableColumnModel;

/**
 * スクロールペイン一体型のテーブルです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JScrollPane
 * @see VRTable
 * @see VRTablar
 */

public class ACTable extends JScrollPane implements VRTablar, KeyListener,
        MouseMotionListener, MouseWheelListener, MouseListener, FocusListener {
    private JPopupMenu popupMenu;
    private boolean popupMenuEnabled;
    private VRTablar table;

    /**
     * コンストラクタです。
     */
    public ACTable() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
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

        getHorizontalScrollBar().setFocusable(false);
        getVerticalScrollBar().setFocusable(false);
    }

    public void addColumn(TableColumn aColumn) {
        getTable().addColumn(aColumn);
    }

    public void addColumnSelectionInterval(int index0, int index1) {
        getTable().addColumnSelectionInterval(index0, index1);
    }

    /**
     * テーブル選択イベントリスナを追加します。
     * 
     * @param listener リスナ
     */
    public void addListSelectionListener(ListSelectionListener listener) {
        getSelectionModel().addListSelectionListener(listener);
    }

    public void addRowSelectionInterval(int index0, int index1) {
        getTable().addRowSelectionInterval(index0, index1);
    }

    public void changeSelection(int rowIndex, int columnIndex, boolean toggle,
            boolean extend) {
        getTable().changeSelection(rowIndex, columnIndex, toggle, extend);
    }

    /**
     * 選択を解除します。
     */
    public void clearSelection() {
        getTable().getSelectionModel().clearSelection();
    }

    /**
     * ソート状態をクリアします。
     */
    public void clearSortSequence() {
        getTable().clearSortSequence();
    }

    public void columnAdded(TableColumnModelEvent e) {
        getTable().columnAdded(e);
    }

    // public void columnAdded(TableColumnModelEvent e) {
    //        
    // getTable().columnAdded(e);
    // }

    public int columnAtPoint(Point point) {
        return getTable().columnAtPoint(point);
    }

    // public void columnMarginChanged(ChangeEvent e) {
    // getTable().columnMarginChanged(e);
    // }
    //
    // public void columnMoved(TableColumnModelEvent e) {
    // getTable().columnMoved(e);
    // }
    //
    // public void columnRemoved(TableColumnModelEvent e) {
    // getTable().columnRemoved(e);
    // }
    //
    // public void columnSelectionChanged(ListSelectionEvent e) {
    // getTable().columnSelectionChanged(e);
    // }

    public void columnMarginChanged(ChangeEvent e) {
        getTable().columnMarginChanged(e);
    }

    public void columnMoved(TableColumnModelEvent e) {
        getTable().columnMoved(e);
    }

    public void columnRemoved(TableColumnModelEvent e) {
        getTable().columnRemoved(e);
    }

    public void columnSelectionChanged(ListSelectionEvent e) {
        getTable().columnSelectionChanged(e);
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
    }

    public void componentResized(ComponentEvent e) {
        getTable().doExtendLastColumn();
        getViewport().repaint();
    }

    public void componentShown(ComponentEvent e) {
    }

    public int convertColumnIndexToModel(int viewColumnIndex) {
        return getTable().convertColumnIndexToModel(viewColumnIndex);
    }

    public int convertColumnIndexToView(int modelColumnIndex) {
        return getTable().convertColumnIndexToView(modelColumnIndex);
    }

    public void createDefaultColumnsFromModel() {
        getTable().createDefaultColumnsFromModel();
    }

    public void doExtendLastColumn() {
        getTable().doExtendLastColumn();
    }

    public boolean editCellAt(int row, int column) {
        return getTable().editCellAt(row, column);
    }

    public boolean editCellAt(int row, int column, EventObject e) {
        return getTable().editCellAt(row, column, e);
    }

    public void editingCanceled(ChangeEvent e) {
        getTable().editingCanceled(e);
    }

    public void editingStopped(ChangeEvent e) {
        getTable().editingStopped(e);
    }

    public void focusGained(FocusEvent e) {
        processFocusEvent(e);
    }

    public void focusLost(FocusEvent e) {
        processFocusEvent(e);
    }

    public boolean getAutoCreateColumnsFromModel() {
        return getTable().getAutoCreateColumnsFromModel();
    }

    public int getAutoResizeMode() {
        return getTable().getAutoResizeMode();
    }

    public TableCellEditor getCellEditor() {
        return getTable().getCellEditor();
    }

    public TableCellEditor getCellEditor(int row, int column) {
        return getTable().getCellEditor(row, column);
    }

    public Rectangle getCellRect(int row, int column, boolean includeSpacing) {
        return getTable().getCellRect(row, column, includeSpacing);
    }

    public TableCellRenderer getCellRenderer(int row, int column) {
        return getTable().getCellRenderer(row, column);
    }

    public boolean getCellSelectionEnabled() {
        return getTable().getCellSelectionEnabled();
    }

    public TableColumn getColumn(Object identifier) {
        return getTable().getColumn(identifier);
    }

    public Class getColumnClass(int column) {
        return getTable().getColumnClass(column);
    }

    /**
     * カラムの個数を返します。
     * 
     * @return カラムの個数
     */
    public int getColumnCount() {
        return getTable().getColumnCount();
    }

    /**
     * カラムモデルを返します。
     * 
     * @return カラムモデル
     */
    public TableColumnModel getColumnModel() {
        return getTable().getColumnModel();
    }

    public String getColumnName(int column) {
        return getTable().getColumnName(column);
    }

    public boolean getColumnSelectionAllowed() {
        return getTable().getColumnSelectionAllowed();
    }

    public TableCellEditor getDefaultEditor(Class columnClass) {
        return getTable().getDefaultEditor(columnClass);
    }

    /**
     * Returns the cell renderer to be used when no renderer has been set in a
     * <code>TableColumn</code>. During the rendering of cells the renderer
     * is fetched from a <code>Hashtable</code> of entries according to the
     * class of the cells in the column. If there is no entry for this
     * <code>columnClass</code> the method returns the entry for the most
     * specific superclass. The <code>JTable</code> installs entries for
     * <code>Object</code>, <code>Number</code>, and <code>Boolean</code>,
     * all of which can be modified or replaced.
     * 
     * @param columnClass return the default cell renderer for this columnClass
     * @return the renderer for this columnClass
     * @see #setDefaultRenderer
     * @see #getColumnClass
     */
    public TableCellRenderer getDefaultRenderer(Class columnClass) {
        return getDefaultRenderer(columnClass);
    }

    public boolean getDragEnabled() {
        return getTable().getDragEnabled();
    }

    public int getEditingColumn() {
        return getTable().getEditingColumn();
    }

    public int getEditingRow() {
        return getTable().getEditingRow();
    }

    public Component getEditorComponent() {
        return getTable().getEditorComponent();
    }

    public Color getGridColor() {
        return getTable().getGridColor();
    }

    public Dimension getIntercellSpacing() {
        return getTable().getIntercellSpacing();
    }

    /**
     * データモデルを返します。
     * 
     * @return データモデル
     */
    public TableModel getModel() {
        return getTable().getModel();
    }

    /**
     * ポップアップメニューを返します。
     * 
     * @return ポップアップメニュー
     */
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getTable().getPreferredScrollableViewportSize();
    }

    /**
     * 列数を返します。
     * 
     * @return 列数
     */
    public int getRowCount() {
        return getTable().getRowCount();
    }

    public int getRowHeight() {
        return getTable().getRowHeight();
    }

    public int getRowHeight(int row) {
        return getTable().getRowHeight(row);
    }

    public int getRowMargin() {
        return getTable().getRowMargin();
    }

    public boolean getRowSelectionAllowed() {
        return getTable().getRowSelectionAllowed();
    }

    public int getScrollableBlockIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return getTable().getScrollableBlockIncrement(visibleRect, orientation,
                direction);
    }

    public boolean getScrollableTracksViewportHeight() {
        return getTable().getScrollableTracksViewportHeight();
    }

    public boolean getScrollableTracksViewportWidth() {
        return getTable().getScrollableTracksViewportWidth();
    }

    public int getScrollableUnitIncrement(Rectangle visibleRect,
            int orientation, int direction) {
        return getTable().getScrollableUnitIncrement(visibleRect, orientation,
                direction);
    }

    /**
     * 選択しているカラム番号を返します。
     * 
     * @return 選択しているカラム番号
     */
    public int getSelectedColumn() {
        return getTable().getSelectedColumn();
    }

    public int getSelectedColumnCount() {
        return getTable().getSelectedColumnCount();
    }

    public int[] getSelectedColumns() {
        return getTable().getSelectedColumns();
    }

    public int getSelectedModelRow() {
        return getTable().getSelectedModelRow();
    }

    public Object getSelectedModelRowValue() throws Exception {
        return getTable().getSelectedModelRowValue();
    }

    public int getSelectedRow() {
        return getTable().getSelectedRow();
    }

    public int getSelectedRowCount() {
        return getTable().getSelectedRowCount();
    }

    public int[] getSelectedRows() {
        return getTable().getSelectedRows();
    }

    public int getSelectedSortedRow() {
        return getTable().getSelectedSortedRow();
    }

    public Color getSelectionBackground() {
        return getTable().getSelectionBackground();
    }

    public Color getSelectionForeground() {
        return getTable().getSelectionForeground();
    }

    /**
     * Returns the <code>ListSelectionModel</code> that is used to maintain
     * row selection state.
     * 
     * @return the object that provides row selection state, <code>null</code>
     *         if row selection is not allowed
     * @see #setSelectionModel
     */
    public ListSelectionModel getSelectionModel() {
        return getTable().getSelectionModel();
    }

    public boolean getShowHorizontalLines() {
        return getTable().getShowHorizontalLines();
    }

    public boolean getShowVerticalLines() {
        return getTable().getShowVerticalLines();
    }

    public HashMap getSortSequence() {
        return getTable().getSortSequence();
    }

    public Color getStripeColor() {
        return getTable().getStripeColor();
    }

    public boolean getSurrendersFocusOnKeystroke() {
        return getTable().getSurrendersFocusOnKeystroke();
    }

    public JTableHeader getTableHeader() {
        return getTable().getTableHeader();
    }

    /**
     * モデル上の値を返します。
     * 
     * @param row 行番号
     * @param column 列番号
     * @return 値
     */
    public Object getValueAt(int row, int column) {
        return getModel().getValueAt(row, column);
    }

    public boolean isCellEditable(int row, int column) {
        return getTable().isCellEditable(row, column);
    }

    public boolean isCellSelected(int row, int column) {
        return getTable().isCellSelected(row, column);
    }

    public boolean isColumnSelected(int column) {
        return getTable().isColumnSelected(column);
    }

    /**
     * カラムのクリックによるソートを許可するか を返します。
     * 
     * @return カラムのクリックによるソートを許可するか
     */
    public boolean isColumnSort() {
        return getTable().isColumnSort();
    }

    public boolean isEditing() {
        return getTable().isEditing();
    }

    /**
     * 右端自動調整機能の使用/未使用を返します。
     * 
     * @return 右端自動調整機能を使用する場合はtrue、しない場合はfalse
     */
    public boolean isExtendLastColumn() {
        return getTable().isExtendLastColumn();
    }

    /**
     * ポップアップメニューを有効にするかを返します。
     * 
     * @return ポップアップメニューを有効にするか
     */
    public boolean isPopupMenuEnabled() {
        return popupMenuEnabled;
    }

    public boolean isRowSelected(int row) {
        return getTable().isRowSelected(row);
    }

    /**
     * テーブル内の項目を選択しているかを返します。
     * 
     * @return テーブル内の項目を選択しているか
     */
    public boolean isSelected() {
        return getSelectedModelRow() >= 0;
    }

    public boolean isStripe() {
        return getTable().isStripe();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        processKeyEvent(e);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        processKeyEvent(e);
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        processKeyEvent(e);
    }

    public void mouseClicked(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        processMouseMotionEvent(e);
    }

    public void mouseEntered(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseExited(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseMoved(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        processMouseMotionEvent(e);
    }

    public void mousePressed(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseReleased(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseWheelMoved(MouseWheelEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        processMouseWheelEvent(e);
    }

    public void moveColumn(int column, int targetColumn) {
        getTable().moveColumn(column, targetColumn);
    }

    public Component prepareEditor(TableCellEditor editor, int row, int column) {
        return getTable().prepareEditor(editor, row, column);
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column) {
        return getTable().prepareRenderer(renderer, row, column);
    }

    public void removeColumn(TableColumn aColumn) {
        getTable().removeColumn(aColumn);
    }

    public void removeColumnSelectionInterval(int index0, int index1) {
        getTable().removeColumnSelectionInterval(index0, index1);
    }

    public void removeEditor() {
        getTable().removeEditor();
    }

    /**
     * テーブル選択イベントリスナを除外します。
     * 
     * @param x リスナ
     */
    public void removeListSelectionListener(ListSelectionListener x) {
        getSelectionModel().removeListSelectionListener(x);
    }

    public void removeRowSelectionInterval(int index0, int index1) {
        getTable().removeRowSelectionInterval(index0, index1);
    }

    public void requestFocus() {
        getTable().requestFocus();
    }

    public void resort() {
        getTable().resort();
    }

    public void revalidate() {
        super.revalidate();
        getTable().revalidate();
    }

    public int rowAtPoint(Point point) {
        return getTable().rowAtPoint(point);
    }

    public void selectAll() {
        getTable().selectAll();
    }

    public void setAutoCreateColumnsFromModel(boolean autoCreateColumnsFromModel) {
        getTable().setAutoCreateColumnsFromModel(autoCreateColumnsFromModel);
    }

    public void setAutoResizeMode(int mode) {
        getTable().setAutoResizeMode(mode);
    }

    public void setCellEditor(TableCellEditor anEditor) {
        getTable().setCellEditor(anEditor);
    }

    public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
        getTable().setCellSelectionEnabled(cellSelectionEnabled);
    }

    public void setColumnModel(TableColumnModel columnModel) {
        getTable().setColumnModel(columnModel);
    }

    /**
     * カラムモデルを設定します。
     * 
     * @param model カラムモデル
     */
    public void setColumnModel(VRTableColumnModel model) {
        getTable().setColumnModel(model);
    }

    public void setColumnSelectionAllowed(boolean columnSelectionAllowed) {
        getTable().setColumnSelectionAllowed(columnSelectionAllowed);
    }

    public void setColumnSelectionInterval(int index0, int index1) {
        getTable().setColumnSelectionInterval(index0, index1);
    }

    /**
     * カラムのクリックによるソートを許可するかを設定します。
     * 
     * @param columnSort カラムのクリックによるソートを許可するか
     */
    public void setColumnSort(boolean columnSort) {
        getTable().setColumnSort(columnSort);
    }

    public void setDefaultEditor(Class columnClass, TableCellEditor editor) {
        getTable().setDefaultEditor(columnClass, editor);
    }

    /**
     * Sets a default cell renderer to be used if no renderer has been set in a
     * <code>TableColumn</code>. If renderer is <code>null</code>, removes
     * the default renderer for this column class.
     * 
     * @param columnClass set the default cell renderer for this columnClass
     * @param renderer default cell renderer to be used for this columnClass
     * @see #getDefaultRenderer
     * @see #setDefaultEditor
     */
    public void setDefaultRenderer(Class columnClass, TableCellRenderer renderer) {
        getTable().setDefaultRenderer(columnClass, renderer);
    }

    public void setDragEnabled(boolean b) {
        getTable().setDragEnabled(b);
    }

    public void setEditingColumn(int aColumn) {
        getTable().setEditingColumn(aColumn);
    }

    public void setEditingRow(int aRow) {
        getTable().setEditingRow(aRow);
    }

    /**
     * 右端自動調整機能の使用/未使用を設定します。
     * 
     * @param extendLastColumn 右端自動調整機能を使用する場合はtrue、しない場合はfalse
     */
    public void setExtendLastColumn(boolean extendLastColumn) {
        getTable().setExtendLastColumn(extendLastColumn);
    }

    public void setGridColor(Color gridColor) {
        getTable().setGridColor(gridColor);
    }

    public void setIntercellSpacing(Dimension intercellSpacing) {
        getTable().setIntercellSpacing(intercellSpacing);
    }

    /**
     * データモデルを設定します。
     * 
     * @param model データモデル
     */
    public void setModel(TableModel model) {
        getTable().setModel(model);
    }

    /**
     * ポップアップメニューを設定します。
     * 
     * @param popupMenu ポップアップメニュー
     */
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    /**
     * ポップアップメニューを有効にするかを設定します。
     * 
     * @param popupMenuEnabled ポップアップメニューを有効にするか
     */
    public void setPopupMenuEnabled(boolean popupMenuEnabled) {
        this.popupMenuEnabled = popupMenuEnabled;
    }

    public void setPreferredScrollableViewportSize(Dimension size) {
        getTable().setPreferredScrollableViewportSize(size);
    }

    public void setRowHeight(int rowHeight) {
        getTable().setRowHeight(rowHeight);
    }

    public void setRowHeight(int row, int rowHeight) {
        getTable().setRowHeight(row, rowHeight);
    }

    public void setRowMargin(int rowMargin) {
        getTable().setRowMargin(rowMargin);
    }

    public void setRowSelectionAllowed(boolean rowSelectionAllowed) {
        getTable().setRowSelectionAllowed(rowSelectionAllowed);
    }

    public void setRowSelectionInterval(int index0, int index1) {
        getTable().setRowSelectionInterval(index0, index1);
    }

    /**
     * 指定のカラムを選択します。
     * 
     * @param selectedColumn 選択カラム番号
     */
    public void setSelectedColumn(int selectedColumn) {
        setSelectedColumn(selectedColumn, selectedColumn);
    }

    /**
     * 指定のカラム範囲を選択します。
     * 
     * @param selectedColumnBegin 選択開始カラム番号
     * @param selectedColumnEnd 選択終了カラム番号
     */
    public void setSelectedColumn(int selectedColumnBegin, int selectedColumnEnd) {
        getColumnModel().getSelectionModel().setSelectionInterval(
                selectedColumnBegin, selectedColumnEnd);
    }

    /**
     * モデル上の選択行番号を設定します。
     * 
     * @param row モデル上の選択行番号
     */
    public void setSelectedModelRow(int row) {
        setSelectedModelRow(row, row);
    }

    /**
     * モデル上の選択行番号を設定します。
     * 
     * @param firstRow モデル上の選択開始行番号
     * @param lastRow モデル上の選択終了行番号
     */
    public void setSelectedModelRow(int firstRow, int lastRow) {
        TableModel model = table.getModel();
        if (model instanceof VRSortableTableModelar) {
            firstRow = ((VRSortableTableModelar) model)
                    .getReverseTranslateIndex(firstRow);
            lastRow = ((VRSortableTableModelar) model)
                    .getReverseTranslateIndex(lastRow);
        }
        setSelectedSortedRow(firstRow, lastRow);
    }

    /**
     * 画面上の1行目を選択します。
     */
    public void setSelectedSortedFirstRow() {
        if (getRowCount() > 0) {
            setSelectedSortedRow(0);
        }
    }

    /**
     * ソート結果を考慮した画面上の選択行番号を設定します。
     * 
     * @param row 画面上の選択行番号
     */
    public void setSelectedSortedRow(int row) {
        setSelectedSortedRow(row, row);
    }

    /**
     * ソート結果を考慮した画面上の選択行番号を設定します。
     * 
     * @param firstRow 画面上の選択開始行番号
     * @param lastRow 画面上の選択終了行番号
     */
    public void setSelectedSortedRow(int firstRow, int lastRow) {
        ListSelectionModel selModel = table.getSelectionModel();
        if (selModel != null) {
            selModel.setSelectionInterval(firstRow, lastRow);
        }
    }

    /**
     * 項目の削除処理後を想定してソート結果を考慮した画面上の選択行番号を設定します。
     * 
     * @param lastSortedSelectedRow 最後に選択していた画面上の選択行番号
     */
    public void setSelectedSortedRowOnAfterDelete(int lastSortedSelectedRow) {
        if (getRowCount() > 0) {
            if (lastSortedSelectedRow <= 0) {
                // 不正な選択範囲なら1行目を選択する
                setSelectedSortedRow(0);
            } else if (lastSortedSelectedRow >= getRowCount()) {
                // 保持行数を超えていたら最終行を選択する
                setSelectedSortedRow(getRowCount() - 1);
            } else {
                // 最終選択行-1を選択する
                setSelectedSortedRow(lastSortedSelectedRow - 1);
            }
        }
    }

    public void setSelectionBackground(Color selectionBackground) {
        getTable().setSelectionBackground(selectionBackground);
    }

    public void setSelectionForeground(Color selectionForeground) {
        getTable().setSelectionForeground(selectionForeground);
    }

    public void setSelectionMode(int selectionMode) {
        getTable().setSelectionMode(selectionMode);
    }

    public void setSelectionModel(ListSelectionModel newModel) {
        getTable().setSelectionModel(newModel);
    }

    public void setShowGrid(boolean showGrid) {
        getTable().setShowGrid(showGrid);
    }

    public void setShowHorizontalLines(boolean showHorizontalLines) {
        getTable().setShowHorizontalLines(showHorizontalLines);
    }

    public void setShowVerticalLines(boolean showVerticalLines) {
        getTable().setShowVerticalLines(showVerticalLines);
    }

    public void setSortSequence(HashMap sortSequence) {
        getTable().setSortSequence(sortSequence);
    }

    public void setStripe(boolean stripe) {
        getTable().setStripe(stripe);
    }

    public void setStripeColor(Color stripeColor) {
        getTable().setStripeColor(stripeColor);
    }

    public void setSurrendersFocusOnKeystroke(boolean surrendersFocusOnKeystroke) {
        getTable().setSurrendersFocusOnKeystroke(surrendersFocusOnKeystroke);
    }

    public void setTableHeader(JTableHeader tableHeader) {
        getTable().setTableHeader(tableHeader);
    }

    /**
     * モデル上の値を設定します。
     * 
     * @param aValue 値
     * @param row 行番号
     * @param column 列番号
     * @return 値
     */
    public void setValueAt(Object aValue, int row, int column) {
        getModel().setValueAt(aValue, row, column);
    }

    public void sizeColumnsToFit(boolean lastColumnOnly) {
        getTable().sizeColumnsToFit(lastColumnOnly);
    }

    public void sizeColumnsToFit(int resizingColumn) {
        getTable().sizeColumnsToFit(resizingColumn);
    }

    public void sort(int column) {
        getTable().sort(column);
    }

    /**
     * ソート処理を行います。
     * 
     * @param condition ソート条件式
     */
    public void sort(String condition) {
        getTable().sort(condition);
    }

    /**
     * 指定のモデルカラム名をもつセルの編集状態を終了します。
     * 
     * @param modelColumnName テーブルモデルにおけるカラム名
     */
    public void stopCellEditing(String modelColumnName) {
        if (modelColumnName == null) {
            return;
        }

        int row = getSelectedModelRow();
        if (row < 0) {
            return;
        }
        TableModel model = getModel();
        if (model == null) {
            return;
        }

        int end = getTable().getColumnCount();
        for (int i = 0; i < end; i++) {
            if (modelColumnName.equals(model.getColumnName(i))) {
                int tblCol = getTable().convertColumnIndexToView(i);
                if (tblCol >= 0) {
                    getTable().getCellEditor(row, tblCol).stopCellEditing();
                }
                return;
            }
        }
    }

    public void tableChanged(TableModelEvent e) {
        getTable().tableChanged(e);
    }

    public void valueChanged(ListSelectionEvent e) {
        getTable().valueChanged(e);
    }

    /**
     * コンポーネントを設定します。
     * 
     * @throws Exception 設定例外
     */
    private void jbInit() throws Exception {
        this.setPreferredSize(new Dimension(100, 100));
        this.getViewport().add((Component) getTable(), null);
        this.getViewport().addComponentListener(this);

        // setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        getSelectionModel().setSelectionMode(
                ListSelectionModel.SINGLE_SELECTION);
        ACTableCellViewer viewer = new ACTableCellViewer();
        setDefaultRenderer(Object.class, viewer);
        setDefaultEditor(Object.class, viewer);
        setAutoCreateColumnsFromModel(false);

        setExtendLastColumn(true);
    }

    /**
     * 内部テーブルを生成して返します。
     * 
     * @return テーブル
     */
    protected VRTablar createTable() {
        return new ACCellStylizableTable();
    }

    /**
     * 内部テーブルを返します。
     * 
     * @return テーブル
     */
    protected VRTablar getTable() {
        if (table == null) {
            table = createTable();
            // 自身を子のリスナとして登録することで、間接的に子のイベントを代行
            table.addKeyListener(this);
            table.addMouseListener(this);
            table.addMouseMotionListener(this);
            table.addMouseWheelListener(this);
            table.addFocusListener(this);
        }
        return table;
    }

    protected void processFocusEvent(FocusEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        super.processFocusEvent(e);
    }

    protected void processKeyEvent(KeyEvent e) {
        // イベントを処理しない
        // if (e.getSource() == getTable()) {
        // // 子のイベントを間接的に発行
        // e = new KeyEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
        // e.getKeyCode(), e.getKeyChar(), e.getKeyLocation());
        // e.setSource(this);
        // }
        // super.processKeyEvent(e);
    }

    protected void processMouseEvent(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを間接的に発行
            e.setSource(this);
        }
        super.processMouseEvent(e);
    }

    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを間接的に発行
            e.setSource(this);
        }
        super.processMouseMotionEvent(e);
    }

    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (e.getSource() == getTable()) {
            // 子のイベントを置換
            e.setSource(this);
        }
        super.processMouseWheelEvent(e);
    }

    /**
     * 内部テーブルを設定します。
     * 
     * @param table テーブル
     */
    protected void setTable(VRTablar table) {
        if (this.table == table) {
            return;
        }
        if (this.table != null) {
            this.table.removeKeyListener(this);
            this.table.removeMouseListener(this);
            this.table.removeMouseMotionListener(this);
            this.table.removeMouseWheelListener(this);
            this.table.removeFocusListener(this);
        }
        this.table = table;
        if (table == null) {
            // 自身を子のリスナとして登録することで、間接的に子のイベントを代行
            table.addKeyListener(this);
            table.addMouseListener(this);
            table.addMouseMotionListener(this);
            table.addMouseWheelListener(this);
            table.addFocusListener(this);
        }
    }

    /**
     * ポップアップメニューを表示します。
     * 
     * @param e イベント情報
     */
    protected void showPopup(MouseEvent e) {
        if (e.isPopupTrigger() && isPopupMenuEnabled()
                && (getPopupMenu() != null)) {
            getPopupMenu().show((Component) e.getSource(), e.getX(), e.getY());
        }
    }

}
