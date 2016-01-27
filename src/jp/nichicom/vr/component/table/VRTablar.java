/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentListener;
import java.awt.event.MouseListener;
import java.util.EventObject;
import java.util.HashMap;

import javax.accessibility.Accessible;
import javax.swing.DefaultCellEditor;
import javax.swing.JList;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.TransferHandler;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableColumnModelListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jp.nichicom.vr.component.VRJComponentar;
import jp.nichicom.vr.util.adapter.VRTableModelAdapter;

/**
 * カラムヘッダクリックによるソート機能に対応したテーブルインターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JTable
 * @see VRJComponentar
 * @see VRTableCellViewer
 * @see VRSortableTableModel
 * @see VRTableModelAdapter
 * @see VRTableHeaderRenderer
 */
public interface VRTablar extends VRJComponentar, TableModelListener,
        Scrollable, TableColumnModelListener, ListSelectionListener,
        CellEditorListener, Accessible, MouseListener, ComponentListener {
    /**
     * Appends <code>aColumn</code> to the end of the array of columns held by
     * this <code>JTable</code>'s column model. If the column name of
     * <code>aColumn</code> is <code>null</code>, sets the column name of
     * <code>aColumn</code> to the name returned by
     * <code>getModel().getColumnName()</code>.
     * <p>
     * To add a column to this <code>JTable</code> to display the
     * <code>modelColumn</code>'th column of data in the model with a given
     * <code>width</code>, <code>cellRenderer</code>, and
     * <code>cellEditor</code> you can use:
     * 
     * <pre>
     * 
     * addColumn(new TableColumn(modelColumn, width, cellRenderer, cellEditor));
     * 
     * </pre>
     * 
     * [Any of the <code>TableColumn</code> constructors can be used instead
     * of this one.] The model column number is stored inside the
     * <code>TableColumn</code> and is used during rendering and editing to
     * locate the appropriates data values in the model. The model column number
     * does not change when columns are reordered in the view.
     * 
     * @param aColumn the <code>TableColumn</code> to be added
     * @see #removeColumn
     */
    public void addColumn(TableColumn aColumn);

    /**
     * Adds the columns from <code>index0</code> to <code>index1</code>,
     * inclusive, to the current selection.
     * 
     * @exception IllegalArgumentException if <code>index0</code> or
     *                <code>index1</code> lie outside [0,
     *                <code>getColumnCount()</code>-1]
     * @param index0 one end of the interval
     * @param index1 the other end of the interval
     */
    public void addColumnSelectionInterval(int index0, int index1);

    /**
     * Adds the rows from <code>index0</code> to <code>index1</code>,
     * inclusive, to the current selection.
     * 
     * @exception IllegalArgumentException if <code>index0</code> or
     *                <code>index1</code> lie outside [0,
     *                <code>getRowCount()</code>-1]
     * @param index0 one end of the interval
     * @param index1 the other end of the interval
     */
    public void addRowSelectionInterval(int index0, int index1);

    /**
     * Updates the selection models of the table, depending on the state of the
     * two flags: <code>toggle</code> and <code>extend</code>. All changes
     * to the selection that are the result of keyboard or mouse events received
     * by the UI are channeled through this method so that the behavior may be
     * overridden by a subclass.
     * <p>
     * This implementation uses the following conventions:
     * <ul>
     * <li> <code>toggle</code>: <em>false</em>, <code>extend</code>:
     * <em>false</em>. Clear the previous selection and ensure the new cell
     * is selected.
     * <li> <code>toggle</code>: <em>false</em>, <code>extend</code>:
     * <em>true</em>. Extend the previous selection to include the specified
     * cell.
     * <li> <code>toggle</code>: <em>true</em>, <code>extend</code>:
     * <em>false</em>. If the specified cell is selected, deselect it. If it
     * is not selected, select it.
     * <li> <code>toggle</code>: <em>true</em>, <code>extend</code>:
     * <em>true</em>. Leave the selection state as it is, but move the anchor
     * index to the specified location.
     * </ul>
     * 
     * @param rowIndex affects the selection at <code>row</code>
     * @param columnIndex affects the selection at <code>column</code>
     * @param toggle see description above
     * @param extend if true, extend the current selection
     */
    public void changeSelection(int rowIndex, int columnIndex, boolean toggle,
            boolean extend);

    /**
     * Deselects all selected columns and rows.
     */
    public void clearSelection();

    /**
     * ソート状態をクリアします。
     */
    public void clearSortSequence();

    /**
     * Returns the index of the column that <code>point</code> lies in, or -1
     * if the result is not in the range [0, <code>getColumnCount()</code>-1].
     * 
     * @param point the location of interest
     * @return the index of the column that <code>point</code> lies in, or -1
     *         if the result is not in the range [0,
     *         <code>getColumnCount()</code>-1]
     * @see #rowAtPoint
     */
    public int columnAtPoint(Point point);

    /**
     * Maps the index of the column in the view at <code>viewColumnIndex</code>
     * to the index of the column in the table model. Returns the index of the
     * corresponding column in the model. If <code>viewColumnIndex</code> is
     * less than zero, returns <code>viewColumnIndex</code>.
     * 
     * @param viewColumnIndex the index of the column in the view
     * @return the index of the corresponding column in the model
     * @see #convertColumnIndexToView
     */
    public int convertColumnIndexToModel(int viewColumnIndex);

    /**
     * Maps the index of the column in the table model at
     * <code>modelColumnIndex</code> to the index of the column in the view.
     * Returns the index of the corresponding column in the view; returns -1 if
     * this column is not being displayed. If <code>modelColumnIndex</code> is
     * less than zero, returns <code>modelColumnIndex</code>.
     * 
     * @param modelColumnIndex the index of the column in the model
     * @return the index of the corresponding column in the view
     * @see #convertColumnIndexToModel
     */
    public int convertColumnIndexToView(int modelColumnIndex);

    /**
     * Creates default columns for the table from the data model using the
     * <code>getColumnCount</code> method defined in the
     * <code>TableModel</code> interface.
     * <p>
     * Clears any existing columns before creating the new columns based on
     * information from the model.
     * 
     * @see #getAutoCreateColumnsFromModel
     */
    public void createDefaultColumnsFromModel();

    /**
     * 右端自動調節機能を実行します。
     */
    public void doExtendLastColumn();

    /**
     * Programmatically starts editing the cell at <code>row</code> and
     * <code>column</code>, if the cell is editable. Note that this is a
     * convenience method for <code>editCellAt(int, int, null)</code>.
     * 
     * @param row the row to be edited
     * @param column the column to be edited
     * @exception IllegalArgumentException if <code>row</code> or
     *                <code>column</code> is not in the valid range
     * @return false if for any reason the cell cannot be edited
     */
    public boolean editCellAt(int row, int column);

    //
    // Table Attributes
    //

    /**
     * Programmatically starts editing the cell at <code>row</code> and
     * <code>column</code>, if the cell is editable. To prevent the
     * <code>JTable</code> from editing a particular table, column or cell
     * value, return false from the <code>isCellEditable</code> method in the
     * <code>TableModel</code> interface.
     * 
     * @param row the row to be edited
     * @param column the column to be edited
     * @param e event to pass into <code>shouldSelectCell</code>; note that
     *            as of Java 2 platform v1.2, the call to
     *            <code>shouldSelectCell</code> is no longer made
     * @exception IllegalArgumentException if <code>row</code> or
     *                <code>column</code> is not in the valid range
     * @return false if for any reason the cell cannot be edited
     */
    public boolean editCellAt(int row, int column, EventObject e);

    /**
     * Determines whether the table will create default columns from the model.
     * If true, <code>setModel</code> will clear any existing columns and
     * create new columns from the new model. Also, if the event in the
     * <code>tableChanged</code> notification specifies that the entire table
     * changed, then the columns will be rebuilt. The default is true.
     * 
     * @return the autoCreateColumnsFromModel of the table
     * @see #setAutoCreateColumnsFromModel
     * @see #createDefaultColumnsFromModel
     */
    public boolean getAutoCreateColumnsFromModel();

    /**
     * Returns the auto resize mode of the table. The default mode is
     * AUTO_RESIZE_SUBSEQUENT_COLUMNS.
     * 
     * @return the autoResizeMode of the table
     * @see #setAutoResizeMode
     * @see #doLayout
     */
    public int getAutoResizeMode();

    /**
     * Returns the cell editor.
     * 
     * @return the <code>TableCellEditor</code> that does the editing
     */
    public TableCellEditor getCellEditor();

    /**
     * Returns an appropriate editor for the cell specified by <code>row</code>
     * and <code>column</code>. If the <code>TableColumn</code> for this
     * column has a non-null editor, returns that. If not, finds the class of
     * the data in this column (using <code>getColumnClass</code>) and
     * returns the default editor for this type of data.
     * <p>
     * <b>Note:</b> Throughout the table package, the internal implementations
     * always use this method to provide editors so that this default behavior
     * can be safely overridden by a subclass.
     * 
     * @param row the row of the cell to edit, where 0 is the first row
     * @param column the column of the cell to edit, where 0 is the first column
     * @return the editor for this cell; if <code>null</code> return the
     *         default editor for this type of cell
     * @see DefaultCellEditor
     */
    public TableCellEditor getCellEditor(int row, int column);

    /**
     * Returns a rectangle for the cell that lies at the intersection of
     * <code>row</code> and <code>column</code>. If
     * <code>includeSpacing</code> is true then the value returned has the
     * full height and width of the row and column specified. If it is false,
     * the returned rectangle is inset by the intercell spacing to return the
     * true bounds of the rendering or editing component as it will be set
     * during rendering.
     * <p>
     * If the column index is valid but the row index is less than zero the
     * method returns a rectangle with the <code>y</code> and
     * <code>height</code> values set appropriately and the <code>x</code>
     * and <code>width</code> values both set to zero. In general, when either
     * the row or column indices indicate a cell outside the appropriate range,
     * the method returns a rectangle depicting the closest edge of the closest
     * cell that is within the table's range. When both row and column indices
     * are out of range the returned rectangle covers the closest point of the
     * closest cell.
     * <p>
     * In all cases, calculations that use this method to calculate results
     * along one axis will not fail because of anomalies in calculations along
     * the other axis. When the cell is not valid the
     * <code>includeSpacing</code> parameter is ignored.
     * 
     * @param row the row index where the desired cell is located
     * @param column the column index where the desired cell is located in the
     *            display; this is not necessarily the same as the column index
     *            in the data model for the table; the
     *            {@link #convertColumnIndexToView(int)} method may be used to
     *            convert a data model column index to a display column index
     * @param includeSpacing if false, return the true cell bounds - computed by
     *            subtracting the intercell spacing from the height and widths
     *            of the column and row models
     * @return the rectangle containing the cell at location <code>row</code>,<code>column</code>
     */
    public Rectangle getCellRect(int row, int column, boolean includeSpacing);

    /**
     * Returns an appropriate renderer for the cell specified by this row and
     * column. If the <code>TableColumn</code> for this column has a non-null
     * renderer, returns that. If not, finds the class of the data in this
     * column (using <code>getColumnClass</code>) and returns the default
     * renderer for this type of data.
     * <p>
     * <b>Note:</b> Throughout the table package, the internal implementations
     * always use this method to provide renderers so that this default behavior
     * can be safely overridden by a subclass.
     * 
     * @param row the row of the cell to render, where 0 is the first row
     * @param column the column of the cell to render, where 0 is the first
     *            column
     * @return the assigned renderer; if <code>null</code> returns the default
     *         renderer for this type of object
     * @see javax.swing.table.DefaultTableCellRenderer
     * @see javax.swing.table.TableColumn#setCellRenderer
     * @see #setDefaultRenderer
     */
    public TableCellRenderer getCellRenderer(int row, int column);

    /**
     * Returns true if both row and column selection models are enabled.
     * Equivalent to <code>getRowSelectionAllowed() &&
     * getColumnSelectionAllowed()</code>.
     * 
     * @return true if both row and column selection models are enabled
     * @see #setCellSelectionEnabled
     */
    public boolean getCellSelectionEnabled();

    /**
     * Returns the <code>TableColumn</code> object for the column in the table
     * whose identifier is equal to <code>identifier</code>, when compared
     * using <code>equals</code>.
     * 
     * @return the <code>TableColumn</code> object that matches the identifier
     * @exception IllegalArgumentException if <code>identifier</code> is
     *                <code>null</code> or no <code>TableColumn</code> has
     *                this identifier
     * @param identifier the identifier object
     */
    public TableColumn getColumn(Object identifier);

    /**
     * Returns the type of the column appearing in the view at column position
     * <code>column</code>.
     * 
     * @param column the column in the view being queried
     * @return the type of the column at position <code>column</code> in the
     *         view where the first column is column 0
     */
    public Class getColumnClass(int column);

    /**
     * Returns the number of columns in the column model. Note that this may be
     * different from the number of columns in the table model.
     * 
     * @return the number of columns in the table
     * @see #getRowCount
     * @see #removeColumn
     */
    public int getColumnCount();

    /**
     * Returns the <code>TableColumnModel</code> that contains all column
     * information of this table.
     * 
     * @return the object that provides the column state of the table
     * @see #setColumnModel
     */
    public TableColumnModel getColumnModel();

    /**
     * Returns the name of the column appearing in the view at column position
     * <code>column</code>.
     * 
     * @param column the column in the view being queried
     * @return the name of the column at position <code>column</code> in the
     *         view where the first column is column 0
     */
    public String getColumnName(int column);

    /**
     * Returns true if columns can be selected.
     * 
     * @return true if columns can be selected, otherwise false
     * @see #setColumnSelectionAllowed
     */
    public boolean getColumnSelectionAllowed();

    /**
     * Returns the editor to be used when no editor has been set in a
     * <code>TableColumn</code>. During the editing of cells the editor is
     * fetched from a <code>Hashtable</code> of entries according to the class
     * of the cells in the column. If there is no entry for this
     * <code>columnClass</code> the method returns the entry for the most
     * specific superclass. The <code>JTable</code> installs entries for
     * <code>Object</code>, <code>Number</code>, and <code>Boolean</code>,
     * all of which can be modified or replaced.
     * 
     * @param columnClass return the default cell editor for this columnClass
     * @return the default cell editor to be used for this columnClass
     * @see #setDefaultEditor
     * @see #getColumnClass
     */
    public TableCellEditor getDefaultEditor1(Class columnClass);

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
//    public TableCellRenderer getDefaultRenderer(Class columnClass);

    /**
     * Gets the value of the <code>dragEnabled</code> property.
     * 
     * @return the value of the <code>dragEnabled</code> property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled();

    /**
     * Returns the index of the column that contains the cell currently being
     * edited. If nothing is being edited, returns -1.
     * 
     * @return the index of the column that contains the cell currently being
     *         edited; returns -1 if nothing being edited
     */
    public int getEditingColumn();

    /**
     * Returns the index of the row that contains the cell currently being
     * edited. If nothing is being edited, returns -1.
     * 
     * @return the index of the row that contains the cell currently being
     *         edited; returns -1 if nothing being edited
     */
    public int getEditingRow();

    /**
     * Returns the component that is handling the editing session. If nothing is
     * being edited, returns null.
     * 
     * @return Component handling editing session
     */
    public Component getEditorComponent();

    /**
     * Returns the color used to draw grid lines. The default color is look and
     * feel dependent.
     * 
     * @return the color used to draw grid lines
     * @see #setGridColor
     */
    public Color getGridColor();

    /**
     * Returns the horizontal and vertical space between cells. The default
     * spacing is (1, 1), which provides room to draw the grid.
     * 
     * @return the horizontal and vertical spacing between cells
     * @see #setIntercellSpacing
     */
    public Dimension getIntercellSpacing();

    /**
     * Returns the <code>TableModel</code> that provides the data displayed by
     * this <code>JTable</code>.
     * 
     * @return the <code>TableModel</code> that provides the data displayed by
     *         this <code>JTable</code>
     * @see #setModel
     */
    public TableModel getModel();

    /**
     * Returns the number of rows in this table's model.
     * 
     * @return the number of rows in this table's model
     * @see #getColumnCount
     */
    public int getRowCount();

    /**
     * Returns the height of a table row, in pixels. The default row height is
     * 16.0.
     * 
     * @return the height in pixels of a table row
     * @see #setRowHeight
     */
    public int getRowHeight();

    /**
     * Returns the height, in pixels, of the cells in <code>row</code>.
     * 
     * @param row the row whose height is to be returned
     * @return the height, in pixels, of the cells in the row
     */
    public int getRowHeight(int row);

    /**
     * Gets the amount of empty space, in pixels, between cells. Equivalent to:
     * <code>getIntercellSpacing().height</code>.
     * 
     * @return the number of pixels between cells in a row
     * @see #setRowMargin
     */
    public int getRowMargin();

    /**
     * Returns true if rows can be selected.
     * 
     * @return true if rows can be selected, otherwise false
     * @see #setRowSelectionAllowed
     */
    public boolean getRowSelectionAllowed();

    /**
     * Returns the index of the first selected column, -1 if no column is
     * selected.
     * 
     * @return the index of the first selected column
     */
    public int getSelectedColumn();

    /**
     * Returns the number of selected columns.
     * 
     * @return the number of selected columns, 0 if no columns are selected
     */
    public int getSelectedColumnCount();

    /**
     * Returns the indices of all selected columns.
     * 
     * @return an array of integers containing the indices of all selected
     *         columns, or an empty array if no column is selected
     * @see #getSelectedColumn
     */
    public int[] getSelectedColumns();

    /**
     * 選択中のデータ列番号を返します。
     * 
     * @return 選択中のデータ列番号
     */
    public int getSelectedModelRow();

    /**
     * モデル上の選択行データを返します。
     * 
     * @return 選択行データ
     * @throws Exception 処理例外
     */
    public Object getSelectedModelRowValue() throws Exception;

    /**
     * Returns the index of the first selected row, -1 if no row is selected.
     * 
     * @return the index of the first selected row
     */
    public int getSelectedRow();

    /**
     * Returns the number of selected rows.
     * 
     * @return the number of selected rows, 0 if no rows are selected
     */
    public int getSelectedRowCount();

    /**
     * Returns the indices of all selected rows.
     * 
     * @return an array of integers containing the indices of all selected rows,
     *         or an empty array if no row is selected
     * @see #getSelectedRow
     */
    public int[] getSelectedRows();

    /**
     * ソート結果を考慮した画面上の選択列番号を返します。
     * 
     * @return 画面上の選択中の列番号
     */
    public int getSelectedSortedRow();

    /**
     * Returns the background color for selected cells.
     * 
     * @return the <code>Color</code> used for the background of selected list
     *         items
     * @see #setSelectionBackground
     * @see #setSelectionForeground
     */
    public Color getSelectionBackground();

    /**
     * Returns the foreground color for selected cells.
     * 
     * @return the <code>Color</code> object for the foreground property
     * @see #setSelectionForeground
     * @see #setSelectionBackground
     */
    public Color getSelectionForeground();

    /**
     * Returns the <code>ListSelectionModel</code> that is used to maintain
     * row selection state.
     * 
     * @return the object that provides row selection state, <code>null</code>
     *         if row selection is not allowed
     * @see #setSelectionModel
     */
    public ListSelectionModel getSelectionModel();

    /**
     * Returns true if the table draws horizontal lines between cells, false if
     * it doesn't. The default is true.
     * 
     * @return true if the table draws horizontal lines between cells, false if
     *         it doesn't
     * @see #setShowHorizontalLines
     */
    public boolean getShowHorizontalLines();

    /**
     * Returns true if the table draws vertical lines between cells, false if it
     * doesn't. The default is true.
     * 
     * @return true if the table draws vertical lines between cells, false if it
     *         doesn't
     * @see #setShowVerticalLines
     */
    public boolean getShowVerticalLines();

    /**
     * ソート状態マップを返します。
     * 
     * @return ソート状態マップ
     */
    public HashMap getSortSequence();

    /**
     * ストライプカラーを返します。
     * 
     * @return ストライプカラー
     */
    public Color getStripeColor();

    /**
     * Returns true if the editor should get the focus when keystrokes cause the
     * editor to be activated
     * 
     * @return true if the editor should get the focus when keystrokes cause the
     *         editor to be activated
     * @see #setSurrendersFocusOnKeystroke
     */
    public boolean getSurrendersFocusOnKeystroke();

    /**
     * Returns the <code>tableHeader</code> used by this <code>JTable</code>.
     * 
     * @return the <code>tableHeader</code> used by this table
     * @see #setTableHeader
     */
    public JTableHeader getTableHeader();

    /**
     * Returns the cell value at <code>row</code> and <code>column</code>.
     * <p>
     * <b>Note</b>: The column is specified in the table view's display order,
     * and not in the <code>TableModel</code>'s column order. This is an
     * important distinction because as the user rearranges the columns in the
     * table, the column at a given index in the view will change. Meanwhile the
     * user's actions never affect the model's column ordering.
     * 
     * @param row the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return the Object at the specified cell
     */
    public Object getValueAt(int row, int column);

    /**
     * Returns true if the cell at <code>row</code> and <code>column</code>
     * is editable. Otherwise, invoking <code>setValueAt</code> on the cell
     * will have no effect.
     * <p>
     * <b>Note</b>: The column is specified in the table view's display order,
     * and not in the <code>TableModel</code>'s column order. This is an
     * important distinction because as the user rearranges the columns in the
     * table, the column at a given index in the view will change. Meanwhile the
     * user's actions never affect the model's column ordering.
     * 
     * @param row the row whose value is to be queried
     * @param column the column whose value is to be queried
     * @return true if the cell is editable
     * @see #setValueAt
     */
    public boolean isCellEditable(int row, int column);

    /**
     * Returns true if the cell at the specified position is selected.
     * 
     * @param row the row being queried
     * @param column the column being queried
     * @return true if the cell at index <code>(row, column)</code> is
     *         selected, where the first row and first column are at index 0
     * @exception IllegalArgumentException if <code>row</code> or
     *                <code>column</code> are not in the valid range
     */
    public boolean isCellSelected(int row, int column);

    /**
     * Returns true if the column at the specified index is selected.
     * 
     * @param column the column in the column model
     * @return true if the column at index <code>column</code> is selected,
     *         where 0 is the first column
     * @exception IllegalArgumentException if <code>column</code> is not in
     *                the valid range
     */
    public boolean isColumnSelected(int column);

    //
    // Informally implement the TableModel interface.
    //

    /**
     * カラムのクリックによるソートを許可するか を返します。
     * 
     * @return カラムのクリックによるソートを許可するか
     */
    public boolean isColumnSort();

    /**
     * Returns true if a cell is being edited.
     * 
     * @return true if the table is editing a cell
     */
    public boolean isEditing();

    /**
     * 右端自動調整機能の使用/未使用を返します。
     * 
     * @return 右端自動調整機能を使用する場合はtrue、しない場合はfalse
     */
    public boolean isExtendLastColumn();

    /**
     * Returns true if the row at the specified index is selected.
     * 
     * @return true if the row at index <code>row</code> is selected, where 0
     *         is the first row
     * @exception IllegalArgumentException if <code>row</code> is not in the
     *                valid range
     */
    public boolean isRowSelected(int row);

    /**
     * ストライプ機能の使用/未使用を返します。
     * 
     * @return ストライプ機能を使用する場合はtrue、しない場合はfalse
     */
    public boolean isStripe();

    /**
     * Moves the column <code>column</code> to the position currently occupied
     * by the column <code>targetColumn</code> in the view. The old column at
     * <code>targetColumn</code> is shifted left or right to make room.
     * 
     * @param column the index of column to be moved
     * @param targetColumn the new index of the column
     */
    public void moveColumn(int column, int targetColumn);

    /**
     * Prepares the editor by querying the data model for the value and
     * selection state of the cell at <code>row</code>, <code>column</code>.
     * <p>
     * <b>Note:</b> Throughout the table package, the internal implementations
     * always use this method to prepare editors so that this default behavior
     * can be safely overridden by a subclass.
     * 
     * @param editor the <code>TableCellEditor</code> to set up
     * @param row the row of the cell to edit, where 0 is the first row
     * @param column the column of the cell to edit, where 0 is the first column
     * @return the <code>Component</code> being edited
     */
    public Component prepareEditor(TableCellEditor editor, int row, int column);

    /**
     * Prepares the renderer by querying the data model for the value and
     * selection state of the cell at <code>row</code>, <code>column</code>.
     * Returns the component (may be a <code>Component</code> or a
     * <code>JComponent</code>) under the event location.
     * <p>
     * <b>Note:</b> Throughout the table package, the internal implementations
     * always use this method to prepare renderers so that this default behavior
     * can be safely overridden by a subclass.
     * 
     * @param renderer the <code>TableCellRenderer</code> to prepare
     * @param row the row of the cell to render, where 0 is the first row
     * @param column the column of the cell to render, where 0 is the first
     *            column
     * @return the <code>Component</code> under the event location
     */
    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column);

    /**
     * Removes <code>aColumn</code> from this <code>JTable</code>'s array
     * of columns. Note: this method does not remove the column of data from the
     * model; it just removes the <code>TableColumn</code> that was
     * responsible for displaying it.
     * 
     * @param aColumn the <code>TableColumn</code> to be removed
     * @see #addColumn
     */
    public void removeColumn(TableColumn aColumn);

    //
    // Adding and removing columns in the view
    //

    /**
     * Deselects the columns from <code>index0</code> to <code>index1</code>,
     * inclusive.
     * 
     * @exception IllegalArgumentException if <code>index0</code> or
     *                <code>index1</code> lie outside [0,
     *                <code>getColumnCount()</code>-1]
     * @param index0 one end of the interval
     * @param index1 the other end of the interval
     */
    public void removeColumnSelectionInterval(int index0, int index1);

    /**
     * Discards the editor object and frees the real estate it used for cell
     * rendering.
     */
    public void removeEditor();

    /**
     * Deselects the rows from <code>index0</code> to <code>index1</code>,
     * inclusive.
     * 
     * @exception IllegalArgumentException if <code>index0</code> or
     *                <code>index1</code> lie outside [0,
     *                <code>getRowCount()</code>-1]
     * @param index0 one end of the interval
     * @param index1 the other end of the interval
     */
    public void removeRowSelectionInterval(int index0, int index1);

    //
    // Cover methods for various models and helper methods
    //

    /**
     * 最後にソートしたときと同条件でソート処理を行います。
     */
    public void resort();

    /**
     * Returns the index of the row that <code>point</code> lies in, or -1 if
     * the result is not in the range [0, <code>getRowCount()</code>-1].
     * 
     * @param point the location of interest
     * @return the index of the row that <code>point</code> lies in, or -1 if
     *         the result is not in the range [0, <code>getRowCount()</code>-1]
     * @see #columnAtPoint
     */
    public int rowAtPoint(Point point);

    /**
     * Selects all rows, columns, and cells in the table.
     */
    public void selectAll();

    /**
     * Sets this table's <code>autoCreateColumnsFromModel</code> flag. This
     * method calls <code>createDefaultColumnsFromModel</code> if
     * <code>autoCreateColumnsFromModel</code> changes from false to true.
     * 
     * @param autoCreateColumnsFromModel true if <code>JTable</code> should
     *            automatically create columns
     * @see #getAutoCreateColumnsFromModel
     * @see #createDefaultColumnsFromModel
     */
    public void setAutoCreateColumnsFromModel(boolean autoCreateColumnsFromModel);

    /**
     * Sets the table's auto resize mode when the table is resized.
     * 
     * @param mode One of 5 legal values: AUTO_RESIZE_OFF,
     *            AUTO_RESIZE_NEXT_COLUMN, AUTO_RESIZE_SUBSEQUENT_COLUMNS,
     *            AUTO_RESIZE_LAST_COLUMN, AUTO_RESIZE_ALL_COLUMNS
     * @see #getAutoResizeMode
     * @see #doLayout
     */
    public void setAutoResizeMode(int mode);

    //
    // Editing Support
    //

    /**
     * Sets the <code>cellEditor</code> variable.
     * 
     * @param anEditor the TableCellEditor that does the editing
     */
    public void setCellEditor(TableCellEditor anEditor);

    /**
     * Sets whether this table allows both a column selection and a row
     * selection to exist simultaneously. When set, the table treats the
     * intersection of the row and column selection models as the selected
     * cells. Override <code>isCellSelected</code> to change this default
     * behavior. This method is equivalent to setting both the
     * <code>rowSelectionAllowed</code> property and
     * <code>columnSelectionAllowed</code> property of the
     * <code>columnModel</code> to the supplied value.
     * 
     * @param cellSelectionEnabled true if simultaneous row and column selection
     *            is allowed
     * @see #getCellSelectionEnabled
     * @see #isCellSelected
     */
    public void setCellSelectionEnabled(boolean cellSelectionEnabled);

    /**
     * Sets the column model for this table to <code>newModel</code> and
     * registers for listener notifications from the new column model. Also sets
     * the column model of the <code>JTableHeader</code> to
     * <code>columnModel</code>.
     * 
     * @param columnModel the new data source for this table
     * @exception IllegalArgumentException if <code>columnModel</code> is
     *                <code>null</code>
     * @see #getColumnModel
     */
    public void setColumnModel(TableColumnModel columnModel);

    /**
     * Sets whether the columns in this model can be selected.
     * 
     * @param columnSelectionAllowed true if this model will allow column
     *            selection
     * @see #getColumnSelectionAllowed
     */
    public void setColumnSelectionAllowed(boolean columnSelectionAllowed);

    /**
     * Selects the columns from <code>index0</code> to <code>index1</code>,
     * inclusive.
     * 
     * @exception IllegalArgumentException if <code>index0</code> or
     *                <code>index1</code> lie outside [0,
     *                <code>getColumnCount()</code>-1]
     * @param index0 one end of the interval
     * @param index1 the other end of the interval
     */
    public void setColumnSelectionInterval(int index0, int index1);

    /**
     * カラムのクリックによるソートを許可するかを設定します。
     * 
     * @param columnSort カラムのクリックによるソートを許可するか
     */
    public void setColumnSort(boolean columnSort);

    /**
     * Sets a default cell editor to be used if no editor has been set in a
     * <code>TableColumn</code>. If no editing is required in a table, or a
     * particular column in a table, uses the <code>isCellEditable</code>
     * method in the <code>TableModel</code> interface to ensure that this
     * <code>JTable</code> will not start an editor in these columns. If
     * editor is <code>null</code>, removes the default editor for this
     * column class.
     * 
     * @param columnClass set the default cell editor for this columnClass
     * @param editor default cell editor to be used for this columnClass
     * @see TableModel#isCellEditable
     * @see #getDefaultEditor
     * @see #setDefaultRenderer
     */
    public void setDefaultEditor1(Class columnClass, TableCellEditor editor);

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
    public void setDefaultRenderer1(Class columnClass, TableCellRenderer renderer);

    //
    // Managing TableUI
    //

    /**
     * Sets the <code>dragEnabled</code> property, which must be
     * <code>true</code> to enable automatic drag handling (the first part of
     * drag and drop) on this component. The <code>transferHandler</code>
     * property needs to be set to a non-<code>null</code> value for the drag
     * to do anything. The default value of the <code>dragEnabled</code
     * property
     * is <code>false</code>.
     *
     * <p>
     *
     * When automatic drag handling is enabled,
     * most look and feels begin a drag-and-drop operation
     * whenever the user presses the mouse button over a selection
     * and then moves the mouse a few pixels.
     * Setting this property to <code>true</code>
     * can therefore have a subtle effect on
     * how selections behave.
     *
     * <p>
     *
     * Some look and feels might not support automatic drag and drop;
     * they will ignore this property.  You can work around such
     * look and feels by modifying the component
     * to directly call the <code>exportAsDrag</code> method of a
     * <code>TransferHandler</code>.
     *
     * @param b the value to set the <code>dragEnabled</code> property to
     * @exception HeadlessException if
     *            <code>b</code> is <code>true</code> and
     *            <code>GraphicsEnvironment.isHeadless()</code>
     *            returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.4
     */
    public void setDragEnabled(boolean b);

    /**
     * Sets the <code>editingColumn</code> variable.
     * 
     * @param aColumn the column of the cell to be edited
     */
    public void setEditingColumn(int aColumn);

    //
    // Managing models
    //

    /**
     * Sets the <code>editingRow</code> variable.
     * 
     * @param aRow the row of the cell to be edited
     */
    public void setEditingRow(int aRow);

    /**
     * 右端自動調整機能の使用/未使用を設定します。
     * 
     * @param extendLastColumn 右端自動調整機能を使用する場合はtrue、しない場合はfalse
     */
    public void setExtendLastColumn(boolean extendLastColumn);

    /**
     * Sets the color used to draw grid lines to <code>gridColor</code> and
     * redisplays. The default color is look and feel dependent.
     * 
     * @param gridColor the new color of the grid lines
     * @exception IllegalArgumentException if <code>gridColor</code> is
     *                <code>null</code>
     * @see #getGridColor
     */
    public void setGridColor(Color gridColor);

    /**
     * Sets the <code>rowMargin</code> and the <code>columnMargin</code> --
     * the height and width of the space between cells -- to
     * <code>intercellSpacing</code>.
     * 
     * @param intercellSpacing a <code>Dimension</code> specifying the new
     *            width and height between cells
     * @see #getIntercellSpacing
     */
    public void setIntercellSpacing(Dimension intercellSpacing);

    /**
     * Sets the data model for this table to <code>newModel</code> and
     * registers with it for listener notifications from the new data model.
     * 
     * @param dataModel the new data source for this table
     * @exception IllegalArgumentException if <code>newModel</code> is
     *                <code>null</code>
     * @see #getModel
     */
    public void setModel(TableModel dataModel);

    //
    // Implementing TableModelListener interface
    //

    /**
     * Sets the preferred size of the viewport for this table.
     * 
     * @param size a <code>Dimension</code> object specifying the
     *            <code>preferredSize</code> of a <code>JViewport</code>
     *            whose view is this table
     * @see Scrollable#getPreferredScrollableViewportSize
     */
    public void setPreferredScrollableViewportSize(Dimension size);

    //
    // Implementing TableColumnModelListener interface
    //

    /**
     * Sets the height, in pixels, of all cells to <code>rowHeight</code>,
     * revalidates, and repaints. The height of the cells will be equal to the
     * row height minus the row margin.
     * 
     * @param rowHeight new row height
     * @exception IllegalArgumentException if <code>rowHeight</code> is less
     *                than 1
     * @see #getRowHeight
     */
    public void setRowHeight(int rowHeight);

    /**
     * Sets the height for <code>row</code> to <code>rowHeight</code>,
     * revalidates, and repaints. The height of the cells in this row will be
     * equal to the row height minus the row margin.
     * 
     * @param row the row whose height is being changed
     * @param rowHeight new row height, in pixels
     * @exception IllegalArgumentException if <code>rowHeight</code> is less
     *                than 1
     */
    public void setRowHeight(int row, int rowHeight);

    /**
     * Sets the amount of empty space between cells in adjacent rows.
     * 
     * @param rowMargin the number of pixels between cells in a row
     * @see #getRowMargin
     */
    public void setRowMargin(int rowMargin);

    /**
     * Sets whether the rows in this model can be selected.
     * 
     * @param rowSelectionAllowed true if this model will allow row selection
     * @see #getRowSelectionAllowed
     */
    public void setRowSelectionAllowed(boolean rowSelectionAllowed);

    /**
     * Selects the rows from <code>index0</code> to <code>index1</code>,
     * inclusive.
     * 
     * @exception IllegalArgumentException if <code>index0</code> or
     *                <code>index1</code> lie outside [0,
     *                <code>getRowCount()</code>-1]
     * @param index0 one end of the interval
     * @param index1 the other end of the interval
     */
    public void setRowSelectionInterval(int index0, int index1);

    //
    // Implementing ListSelectionListener interface
    //

    /**
     * Sets the background color for selected cells. Cell renderers can use this
     * color to the fill selected cells.
     * <p>
     * The default value of this property is defined by the look and feel
     * implementation.
     * <p>
     * This is a <a
     * href="http://java.sun.com/docs/books/tutorial/javabeans/whatis/beanDefinition.html">JavaBeans</a>
     * bound property.
     * 
     * @param selectionBackground the <code>Color</code> to use for the
     *            background of selected cells
     * @see #getSelectionBackground
     * @see #setSelectionForeground
     * @see #setForeground
     * @see #setBackground
     * @see #setFont
     */
    public void setSelectionBackground(Color selectionBackground);

    //
    // Implementing the CellEditorListener interface
    //

    /**
     * Sets the foreground color for selected cells. Cell renderers can use this
     * color to render text and graphics for selected cells.
     * <p>
     * The default value of this property is defined by the look and feel
     * implementation.
     * <p>
     * This is a <a
     * href="http://java.sun.com/docs/books/tutorial/javabeans/whatis/beanDefinition.html">JavaBeans</a>
     * bound property.
     * 
     * @param selectionForeground the <code>Color</code> to use in the
     *            foreground for selected list items
     * @see #getSelectionForeground
     * @see #setSelectionBackground
     * @see #setForeground
     * @see #setBackground
     * @see #setFont
     */
    public void setSelectionForeground(Color selectionForeground);

    //
    // Selection methods
    //
    /**
     * Sets the table's selection mode to allow only single selections, a single
     * contiguous interval, or multiple intervals.
     * <P>
     * <bold>Note:</bold> <code>JTable</code> provides all the methods for
     * handling column and row selection. When setting states, such as
     * <code>setSelectionMode</code>, it not only updates the mode for the
     * row selection model but also sets similar values in the selection model
     * of the <code>columnModel</code>. If you want to have the row and
     * column selection models operating in different modes, set them both
     * directly.
     * <p>
     * Both the row and column selection models for <code>JTable</code>
     * default to using a <code>DefaultListSelectionModel</code> so that
     * <code>JTable</code> works the same way as the <code>JList</code>.
     * See the <code>setSelectionMode</code> method in <code>JList</code>
     * for details about the modes.
     * 
     * @see JList#setSelectionMode
     */
    public void setSelectionMode(int selectionMode);

    //
    // Implementing the Scrollable interface
    //

    /**
     * Sets the row selection model for this table to <code>newModel</code>
     * and registers for listener notifications from the new selection model.
     * 
     * @param newModel the new selection model
     * @exception IllegalArgumentException if <code>newModel</code> is
     *                <code>null</code>
     * @see #getSelectionModel
     */
    public void setSelectionModel(ListSelectionModel newModel);

    /**
     * Sets whether the table draws grid lines around cells. If
     * <code>showGrid</code> is true it does; if it is false it doesn't. There
     * is no <code>getShowGrid</code> method as this state is held in two
     * variables -- <code>showHorizontalLines</code> and
     * <code>showVerticalLines</code> -- each of which can be queried
     * independently.
     * 
     * @param showGrid true if table view should draw grid lines
     * @see #setShowVerticalLines
     * @see #setShowHorizontalLines
     */
    public void setShowGrid(boolean showGrid);

    /**
     * Sets whether the table draws horizontal lines between cells. If
     * <code>showHorizontalLines</code> is true it does; if it is false it
     * doesn't.
     * 
     * @param showHorizontalLines true if table view should draw horizontal
     *            lines
     * @see #getShowHorizontalLines
     * @see #setShowGrid
     * @see #setShowVerticalLines
     */
    public void setShowHorizontalLines(boolean showHorizontalLines);

    /**
     * Sets whether the table draws vertical lines between cells. If
     * <code>showVerticalLines</code> is true it does; if it is false it
     * doesn't.
     * 
     * @param showVerticalLines true if table view should draw vertical lines
     * @see #getShowVerticalLines
     * @see #setShowGrid
     * @see #setShowHorizontalLines
     */
    public void setShowVerticalLines(boolean showVerticalLines);

    /**
     * ソート状態マップを設定します。
     * 
     * @param sortSequence ソート状態マップ
     */
    public void setSortSequence(HashMap sortSequence);

    /**
     * ストライプ機能の使用/未使用を設定します。
     * 
     * @param stripe ストライプ機能を使用する場合はtrue、しない場合はfalse
     */
    public void setStripe(boolean stripe);

    /**
     * ストライプカラーを設定します。
     * 
     * @param stripeColor ストライプカラー
     */
    public void setStripeColor(Color stripeColor);

    /**
     * Sets whether editors in this JTable get the keyboard focus when an editor
     * is activated as a result of the JTable forwarding keyboard events for a
     * cell. By default, this property is false, and the JTable retains the
     * focus unless the cell is clicked.
     * 
     * @param surrendersFocusOnKeystroke true if the editor should get the focus
     *            when keystrokes cause the editor to be activated
     * @see #getSurrendersFocusOnKeystroke
     */
    public void setSurrendersFocusOnKeystroke(boolean surrendersFocusOnKeystroke);

    /**
     * Sets the <code>tableHeader</code> working with this <code>JTable</code>
     * to <code>newHeader</code>. It is legal to have a
     * <code>null</code> <code>tableHeader</code>.
     * 
     * @param tableHeader new tableHeader
     * @see #getTableHeader
     */
    public void setTableHeader(JTableHeader tableHeader);

    /**
     * Sets the value for the cell in the table model at <code>row</code> and
     * <code>column</code>.
     * <p>
     * <b>Note</b>: The column is specified in the table view's display order,
     * and not in the <code>TableModel</code>'s column order. This is an
     * important distinction because as the user rearranges the columns in the
     * table, the column at a given index in the view will change. Meanwhile the
     * user's actions never affect the model's column ordering.
     * <code>aValue</code> is the new value.
     * 
     * @param aValue the new value
     * @param row the row of the cell to be changed
     * @param column the column of the cell to be changed
     * @see #getValueAt
     */
    public void setValueAt(Object aValue, int row, int column);

    /**
     * Sizes the table columns to fit the available space.
     * 
     * @deprecated As of Swing version 1.0.3, replaced by
     *             <code>doLayout()</code>.
     * @see #doLayout
     */
    public void sizeColumnsToFit(boolean lastColumnOnly);

    /**
     * Obsolete as of Java 2 platform v1.4. Please use the
     * <code>doLayout()</code> method instead.
     * 
     * @param resizingColumn the column whose resizing made this adjustment
     *            necessary or -1 if there is no such column
     * @see #doLayout
     */
    public void sizeColumnsToFit(int resizingColumn);

    /**
     * ソート処理を行います。
     * 
     * @param condition ソート条件式
     */
    public void sort(String condition);

    /**
     * ソート処理を行います。
     * 
     * @param column カラム番号
     */
    public void sort(int column);
}
