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
 * �X�N���[���y�C����̌^�̃e�[�u���ł��B
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
     * �R���X�g���N�^�ł��B
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
     * �e�[�u���I���C�x���g���X�i��ǉ����܂��B
     * 
     * @param listener ���X�i
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
     * �I�����������܂��B
     */
    public void clearSelection() {
        getTable().getSelectionModel().clearSelection();
    }

    /**
     * �\�[�g��Ԃ��N���A���܂��B
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
     * �J�����̌���Ԃ��܂��B
     * 
     * @return �J�����̌�
     */
    public int getColumnCount() {
        return getTable().getColumnCount();
    }

    /**
     * �J�������f����Ԃ��܂��B
     * 
     * @return �J�������f��
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
     * �f�[�^���f����Ԃ��܂��B
     * 
     * @return �f�[�^���f��
     */
    public TableModel getModel() {
        return getTable().getModel();
    }

    /**
     * �|�b�v�A�b�v���j���[��Ԃ��܂��B
     * 
     * @return �|�b�v�A�b�v���j���[
     */
    public JPopupMenu getPopupMenu() {
        return popupMenu;
    }

    public Dimension getPreferredScrollableViewportSize() {
        return getTable().getPreferredScrollableViewportSize();
    }

    /**
     * �񐔂�Ԃ��܂��B
     * 
     * @return ��
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
     * �I�����Ă���J�����ԍ���Ԃ��܂��B
     * 
     * @return �I�����Ă���J�����ԍ�
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
     * ���f����̒l��Ԃ��܂��B
     * 
     * @param row �s�ԍ�
     * @param column ��ԍ�
     * @return �l
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
     * �J�����̃N���b�N�ɂ��\�[�g�������邩 ��Ԃ��܂��B
     * 
     * @return �J�����̃N���b�N�ɂ��\�[�g�������邩
     */
    public boolean isColumnSort() {
        return getTable().isColumnSort();
    }

    public boolean isEditing() {
        return getTable().isEditing();
    }

    /**
     * �E�[���������@�\�̎g�p/���g�p��Ԃ��܂��B
     * 
     * @return �E�[���������@�\���g�p����ꍇ��true�A���Ȃ��ꍇ��false
     */
    public boolean isExtendLastColumn() {
        return getTable().isExtendLastColumn();
    }

    /**
     * �|�b�v�A�b�v���j���[��L���ɂ��邩��Ԃ��܂��B
     * 
     * @return �|�b�v�A�b�v���j���[��L���ɂ��邩
     */
    public boolean isPopupMenuEnabled() {
        return popupMenuEnabled;
    }

    public boolean isRowSelected(int row) {
        return getTable().isRowSelected(row);
    }

    /**
     * �e�[�u�����̍��ڂ�I�����Ă��邩��Ԃ��܂��B
     * 
     * @return �e�[�u�����̍��ڂ�I�����Ă��邩
     */
    public boolean isSelected() {
        return getSelectedModelRow() >= 0;
    }

    public boolean isStripe() {
        return getTable().isStripe();
    }

    public void keyPressed(KeyEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
        processKeyEvent(e);
    }

    public void keyReleased(KeyEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
        processKeyEvent(e);
    }

    public void keyTyped(KeyEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
        processKeyEvent(e);
    }

    public void mouseClicked(MouseEvent e) {
        processMouseEvent(e);
    }

    public void mouseDragged(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g��u��
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
            // �q�̃C�x���g��u��
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
            // �q�̃C�x���g��u��
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
     * �e�[�u���I���C�x���g���X�i�����O���܂��B
     * 
     * @param x ���X�i
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
     * �J�������f����ݒ肵�܂��B
     * 
     * @param model �J�������f��
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
     * �J�����̃N���b�N�ɂ��\�[�g�������邩��ݒ肵�܂��B
     * 
     * @param columnSort �J�����̃N���b�N�ɂ��\�[�g�������邩
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
     * �E�[���������@�\�̎g�p/���g�p��ݒ肵�܂��B
     * 
     * @param extendLastColumn �E�[���������@�\���g�p����ꍇ��true�A���Ȃ��ꍇ��false
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
     * �f�[�^���f����ݒ肵�܂��B
     * 
     * @param model �f�[�^���f��
     */
    public void setModel(TableModel model) {
        getTable().setModel(model);
    }

    /**
     * �|�b�v�A�b�v���j���[��ݒ肵�܂��B
     * 
     * @param popupMenu �|�b�v�A�b�v���j���[
     */
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }

    /**
     * �|�b�v�A�b�v���j���[��L���ɂ��邩��ݒ肵�܂��B
     * 
     * @param popupMenuEnabled �|�b�v�A�b�v���j���[��L���ɂ��邩
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
     * �w��̃J������I�����܂��B
     * 
     * @param selectedColumn �I���J�����ԍ�
     */
    public void setSelectedColumn(int selectedColumn) {
        setSelectedColumn(selectedColumn, selectedColumn);
    }

    /**
     * �w��̃J�����͈͂�I�����܂��B
     * 
     * @param selectedColumnBegin �I���J�n�J�����ԍ�
     * @param selectedColumnEnd �I���I���J�����ԍ�
     */
    public void setSelectedColumn(int selectedColumnBegin, int selectedColumnEnd) {
        getColumnModel().getSelectionModel().setSelectionInterval(
                selectedColumnBegin, selectedColumnEnd);
    }

    /**
     * ���f����̑I���s�ԍ���ݒ肵�܂��B
     * 
     * @param row ���f����̑I���s�ԍ�
     */
    public void setSelectedModelRow(int row) {
        setSelectedModelRow(row, row);
    }

    /**
     * ���f����̑I���s�ԍ���ݒ肵�܂��B
     * 
     * @param firstRow ���f����̑I���J�n�s�ԍ�
     * @param lastRow ���f����̑I���I���s�ԍ�
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
     * ��ʏ��1�s�ڂ�I�����܂��B
     */
    public void setSelectedSortedFirstRow() {
        if (getRowCount() > 0) {
            setSelectedSortedRow(0);
        }
    }

    /**
     * �\�[�g���ʂ��l��������ʏ�̑I���s�ԍ���ݒ肵�܂��B
     * 
     * @param row ��ʏ�̑I���s�ԍ�
     */
    public void setSelectedSortedRow(int row) {
        setSelectedSortedRow(row, row);
    }

    /**
     * �\�[�g���ʂ��l��������ʏ�̑I���s�ԍ���ݒ肵�܂��B
     * 
     * @param firstRow ��ʏ�̑I���J�n�s�ԍ�
     * @param lastRow ��ʏ�̑I���I���s�ԍ�
     */
    public void setSelectedSortedRow(int firstRow, int lastRow) {
        ListSelectionModel selModel = table.getSelectionModel();
        if (selModel != null) {
            selModel.setSelectionInterval(firstRow, lastRow);
        }
    }

    /**
     * ���ڂ̍폜�������z�肵�ă\�[�g���ʂ��l��������ʏ�̑I���s�ԍ���ݒ肵�܂��B
     * 
     * @param lastSortedSelectedRow �Ō�ɑI�����Ă�����ʏ�̑I���s�ԍ�
     */
    public void setSelectedSortedRowOnAfterDelete(int lastSortedSelectedRow) {
        if (getRowCount() > 0) {
            if (lastSortedSelectedRow <= 0) {
                // �s���ȑI��͈͂Ȃ�1�s�ڂ�I������
                setSelectedSortedRow(0);
            } else if (lastSortedSelectedRow >= getRowCount()) {
                // �ێ��s���𒴂��Ă�����ŏI�s��I������
                setSelectedSortedRow(getRowCount() - 1);
            } else {
                // �ŏI�I���s-1��I������
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
     * ���f����̒l��ݒ肵�܂��B
     * 
     * @param aValue �l
     * @param row �s�ԍ�
     * @param column ��ԍ�
     * @return �l
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
     * �\�[�g�������s���܂��B
     * 
     * @param condition �\�[�g������
     */
    public void sort(String condition) {
        getTable().sort(condition);
    }

    /**
     * �w��̃��f���J�����������Z���̕ҏW��Ԃ��I�����܂��B
     * 
     * @param modelColumnName �e�[�u�����f���ɂ�����J������
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
     * �R���|�[�l���g��ݒ肵�܂��B
     * 
     * @throws Exception �ݒ��O
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
     * �����e�[�u���𐶐����ĕԂ��܂��B
     * 
     * @return �e�[�u��
     */
    protected VRTablar createTable() {
        return new ACCellStylizableTable();
    }

    /**
     * �����e�[�u����Ԃ��܂��B
     * 
     * @return �e�[�u��
     */
    protected VRTablar getTable() {
        if (table == null) {
            table = createTable();
            // ���g���q�̃��X�i�Ƃ��ēo�^���邱�ƂŁA�ԐړI�Ɏq�̃C�x���g���s
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
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
        super.processFocusEvent(e);
    }

    protected void processKeyEvent(KeyEvent e) {
        // �C�x���g���������Ȃ�
        // if (e.getSource() == getTable()) {
        // // �q�̃C�x���g���ԐړI�ɔ��s
        // e = new KeyEvent(this, e.getID(), e.getWhen(), e.getModifiers(),
        // e.getKeyCode(), e.getKeyChar(), e.getKeyLocation());
        // e.setSource(this);
        // }
        // super.processKeyEvent(e);
    }

    protected void processMouseEvent(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g���ԐړI�ɔ��s
            e.setSource(this);
        }
        super.processMouseEvent(e);
    }

    protected void processMouseMotionEvent(MouseEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g���ԐړI�ɔ��s
            e.setSource(this);
        }
        super.processMouseMotionEvent(e);
    }

    protected void processMouseWheelEvent(MouseWheelEvent e) {
        if (e.getSource() == getTable()) {
            // �q�̃C�x���g��u��
            e.setSource(this);
        }
        super.processMouseWheelEvent(e);
    }

    /**
     * �����e�[�u����ݒ肵�܂��B
     * 
     * @param table �e�[�u��
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
            // ���g���q�̃��X�i�Ƃ��ēo�^���邱�ƂŁA�ԐړI�Ɏq�̃C�x���g���s
            table.addKeyListener(this);
            table.addMouseListener(this);
            table.addMouseMotionListener(this);
            table.addMouseWheelListener(this);
            table.addFocusListener(this);
        }
    }

    /**
     * �|�b�v�A�b�v���j���[��\�����܂��B
     * 
     * @param e �C�x���g���
     */
    protected void showPopup(MouseEvent e) {
        if (e.isPopupTrigger() && isPopupMenuEnabled()
                && (getPopupMenu() != null)) {
            getPopupMenu().show((Component) e.getSource(), e.getX(), e.getY());
        }
    }

}
