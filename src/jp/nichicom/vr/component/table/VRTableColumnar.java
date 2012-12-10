/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * <code>VRTableCellViewer</code> �ɑΉ������e�[�u���J�����C���^�[�t�F�[�X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see TableColumn
 * @see VRTableCellViewer
 * @see JTable
 * @see Format
 */
public interface VRTableColumnar extends CellEditorListener {

    /**
     * Sets the model index for this column. The model index is the index of the
     * column in the model that will be displayed by this
     * <code>TableColumn</code>. As the <code>TableColumn</code> is moved
     * around in the view the model index remains constant.
     * 
     * @param modelIndex the new modelIndex
     */
    public void setModelIndex(int modelIndex);

    /**
     * Returns the model index for this column.
     * 
     * @return the <code>modelIndex</code> property
     */
    public int getModelIndex();

    /**
     * Sets the <code>TableColumn</code>'s identifier to
     * <code>anIdentifier</code>.
     * <p>
     * Note: identifiers are not used by the <code>JTable</code>, they are
     * purely a convenience for the external tagging and location of columns.
     * 
     * @param identifier an identifier for this column
     * @see #getIdentifier
     */
    public void setIdentifier(Object identifier);

    /**
     * Returns the <code>identifier</code> object for this column. Note
     * identifiers are not used by <code>JTable</code>, they are purely a
     * convenience for external use. If the <code>identifier</code> is
     * <code>null</code>, <code>getIdentifier()</code> returns
     * <code>getHeaderValue</code> as a default.
     * 
     * @return the <code>identifier</code> property
     * @see #setIdentifier
     */
    public Object getIdentifier();

    /**
     * Sets the <code>Object</code> whose string representation will be used
     * as the value for the <code>headerRenderer</code>. When the
     * <code>TableColumn</code> is created, the default
     * <code>headerValue</code> is <code>null</code>.
     * 
     * @param headerValue the new headerValue
     * @see #getHeaderValue
     */
    public void setHeaderValue(Object headerValue);

    /**
     * Returns the <code>Object</code> used as the value for the header
     * renderer.
     * 
     * @return the <code>headerValue</code> property
     * @see #setHeaderValue
     */
    public Object getHeaderValue();

    //
    // Renderers and Editors
    //

    /**
     * Sets the <code>TableCellRenderer</code> used to draw the
     * <code>TableColumn</code>'s header to <code>headerRenderer</code>.
     * 
     * @param headerRenderer the new headerRenderer
     * @see #getHeaderRenderer
     */
    public void setHeaderRenderer(TableCellRenderer headerRenderer);

    /**
     * Returns the <code>TableCellRenderer</code> used to draw the header of
     * the <code>TableColumn</code>. When the <code>headerRenderer</code>
     * is <code>null</code>, the <code>JTableHeader</code> uses its
     * <code>defaultRenderer</code>. The default value for a
     * <code>headerRenderer</code> is <code>null</code>.
     * 
     * @return the <code>headerRenderer</code> property
     * @see #setHeaderRenderer
     * @see #setHeaderValue
     * @see javax.swing.table.JTableHeader#getDefaultRenderer()
     */
    public TableCellRenderer getHeaderRenderer();

    /**
     * Sets the <code>TableCellRenderer</code> used by <code>JTable</code>
     * to draw individual values for this column.
     * 
     * @param cellRenderer the new cellRenderer
     * @see #getCellRenderer
     */
    public void setCellRenderer(TableCellRenderer cellRenderer);

    /**
     * Returns the <code>TableCellRenderer</code> used by the
     * <code>JTable</code> to draw values for this column. The
     * <code>cellRenderer</code> of the column not only controls the visual
     * look for the column, but is also used to interpret the value object
     * supplied by the <code>TableModel</code>. When the
     * <code>cellRenderer</code> is <code>null</code>, the
     * <code>JTable</code> uses a default renderer based on the class of the
     * cells in that column. The default value for a <code>cellRenderer</code>
     * is <code>null</code>.
     * 
     * @return the <code>cellRenderer</code> property
     * @see #setCellRenderer
     * @see JTable#setDefaultRenderer
     */
    public TableCellRenderer getCellRenderer();

    /**
     * Sets the editor to used by when a cell in this column is edited.
     * 
     * @param cellEditor the new cellEditor
     * @see #getCellEditor
     */
    public void setCellEditor(TableCellEditor cellEditor);

    /**
     * Returns the <code>TableCellEditor</code> used by the
     * <code>JTable</code> to edit values for this column. When the
     * <code>cellEditor</code> is <code>null</code>, the
     * <code>JTable</code> uses a default editor based on the class of the
     * cells in that column. The default value for a <code>cellEditor</code>
     * is <code>null</code>.
     * 
     * @return the <code>cellEditor</code> property
     * @see #setCellEditor
     * @see JTable#setDefaultEditor
     */
    public TableCellEditor getCellEditor();

    /**
     * This method should not be used to set the widths of columns in the
     * <code>JTable</code>, use <code>setPreferredWidth</code> instead.
     * Like a layout manager in the AWT, the <code>JTable</code> adjusts a
     * column's width automatically whenever the table itself changes size, or a
     * column's preferred width is changed. Setting widths programmatically
     * therefore has no long term effect.
     * <p>
     * This method sets this column's width to <code>width</code>. If
     * <code>width</code> exceeds the minimum or maximum width, it is adjusted
     * to the appropriate limiting value.
     * <p>
     * 
     * @param width the new width
     * @see #getWidth
     * @see #setMinWidth
     * @see #setMaxWidth
     * @see #setPreferredWidth
     * @see JTable#sizeColumnsToFit(int)
     */
    public void setWidth(int width);

    /**
     * Returns the width of the <code>TableColumn</code>. The default width
     * is 75.
     * 
     * @return the <code>width</code> property
     * @see #setWidth
     */
    public int getWidth();

    /**
     * Sets this column's preferred width to <code>preferredWidth</code>. If
     * <code>preferredWidth</code> exceeds the minimum or maximum width, it is
     * adjusted to the appropriate limiting value.
     * <p>
     * For details on how the widths of columns in the <code>JTable</code>
     * (and <code>JTableHeader</code>) are calculated from the
     * <code>preferredWidth</code>, see the <code>sizeColumnsToFit</code>
     * method in <code>JTable</code>.
     * 
     * @param preferredWidth the new preferred width
     * @see #getPreferredWidth
     * @see JTable#sizeColumnsToFit(int)
     */
    public void setPreferredWidth(int preferredWidth);

    /**
     * Returns the preferred width of the <code>TableColumn</code>. The
     * default preferred width is 75.
     * 
     * @return the <code>preferredWidth</code> property
     * @see #setPreferredWidth
     */
    public int getPreferredWidth();

    /**
     * Sets the <code>TableColumn</code>'s minimum width to
     * <code>minWidth</code>; also adjusts the current width and preferred
     * width if they are less than this value.
     * 
     * @param minWidth the new minimum width
     * @see #getMinWidth
     * @see #setPreferredWidth
     * @see #setMaxWidth
     */
    public void setMinWidth(int minWidth);

    /**
     * Returns the minimum width for the <code>TableColumn</code>. The
     * <code>TableColumn</code>'s width can't be made less than this either
     * by the user or programmatically. The default minWidth is 15.
     * 
     * @return the <code>minWidth</code> property
     * @see #setMinWidth
     */
    public int getMinWidth();

    /**
     * Sets the <code>TableColumn</code>'s maximum width to
     * <code>maxWidth</code>; also adjusts the width and preferred width if
     * they are greater than this value.
     * 
     * @param maxWidth the new maximum width
     * @see #getMaxWidth
     * @see #setPreferredWidth
     * @see #setMinWidth
     */
    public void setMaxWidth(int maxWidth);

    /**
     * Returns the maximum width for the <code>TableColumn</code>. The
     * <code>TableColumn</code>'s width can't be made larger than this either
     * by the user or programmatically. The default maxWidth is
     * Integer.MAX_VALUE.
     * 
     * @return the <code>maxWidth</code> property
     * @see #setMaxWidth
     */
    public int getMaxWidth();

    /**
     * Sets whether this column can be resized.
     * 
     * @param isResizable if true, resizing is allowed; otherwise false
     * @see #getResizable
     */
    public void setResizable(boolean isResizable);

    /**
     * Returns true if the user is allowed to resize the
     * <code>TableColumn</code>'s width, false otherwise. You can change the
     * width programmatically regardless of this setting. The default is true.
     * 
     * @return the <code>isResizable</code> property
     * @see #setResizable
     */
    public boolean getResizable();

    /**
     * Resizes the <code>TableColumn</code> to fit the width of its header
     * cell. This method does nothing if the header renderer is
     * <code>null</code> (the default case). Otherwise, it sets the minimum,
     * maximum and preferred widths of this column to the widths of the minimum,
     * maximum and preferred sizes of the Component delivered by the header
     * renderer. The transient "width" property of this TableColumn is also set
     * to the preferred width. Note this method is not used internally by the
     * table package.
     * 
     * @see #setPreferredWidth
     */
    public void sizeWidthToFit();

    /**
     * This field was not used in previous releases and there are currently no
     * plans to support it in the future.
     * 
     * @deprecated as of Java 2 platform v1.3
     */
    public void disableResizedPosting();

    /**
     * This field was not used in previous releases and there are currently no
     * plans to support it in the future.
     * 
     * @deprecated as of Java 2 platform v1.3
     */
    public void enableResizedPosting();

    /**
     * �J�����̕ҏW�`����Ԃ��܂��B
     * 
     * @return �J�����̕ҏW�`��
     */
    public String getEditorType();

    /**
     * �t�H�[�}�b�g ��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
     */
    public Format getFormat();

    /**
     * ���������̕������� ��Ԃ��܂��B
     * 
     * @return ���������̕�������
     */
    public int getHorizontalAlignment();

    /**
     * �J�����̕\���`����Ԃ��܂��B
     * 
     * @return �J�����̕\���`��
     */
    public String getRendererType();

    /**
     * �e�e�[�u����Ԃ��܂��B
     * 
     * @return �e�e�[�u��
     */
    public JTable getTable();

    /**
     * �J������ҏW�\����Ԃ��܂��B
     * 
     * @return �J������ҏW�\��
     */
    public boolean isEditable();

    /**
     * �J������\�����邩��Ԃ��܂��B
     * 
     * @return �J������\�����邩
     */
    public boolean isVisible();

    /**
     * �J������ҏW�\����ݒ肵�܂��B
     * 
     * @param editable �J������ҏW�\��
     */
    public void setEditable(boolean editable);

    /**
     * �J�����̕ҏW�`����ݒ肵�܂��B
     * 
     * @param editorType �J�����̕ҏW�`��
     */
    public void setEditorType(String editorType);

    /**
     * �t�H�[�}�b�g ��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format);

    /**
     * ���������̕������� ��ݒ肵�܂��B
     * 
     * @param horizontalAlignment ���������̕�������
     */
    public void setHorizontalAlignment(int horizontalAlignment);

    /**
     * �J�����̕\���`����ݒ肵�܂��B
     * 
     * @param rendererType �J�����̕\���`��
     */
    public void setRendererType(String rendererType);

    /**
     * �e�e�[�u����ݒ肵�܂��B
     * 
     * @param table �e�e�[�u��
     */
    public void setTable(JTable table);

    /**
     * �J������\�����邩��ݒ肵�܂��B
     * 
     * @param visible �J������\�����邩
     */
    public void setVisible(boolean visible);

    /**
     * �ҏW�^�C�v�Ǝg�p����ҏW�f���Q�[�g��ǉ����܂��B
     * 
     * @param type �ҏW�^�C�v
     * @param ed �ҏW�f���Q�[�g
     */
    public void addEditor(String type, VRTableCellViewerDelegate ed);

    /**
     * �`��^�C�v�Ǝg�p����`��f���Q�[�g��ǉ����܂��B
     * 
     * @param type �`��^�C�v
     * @param ed �`��f���Q�[�g
     */
    public void addRenderer(String type, VRTableCellViewerDelegate ed);

    /**
     * Adds a <code>CellEditorListener</code> to the listener list.
     * 
     * @param l the new listener to be added
     */
    public void addCellEditorListener(CellEditorListener l);

    /**
     * Removes a <code>CellEditorListener</code> from the listener list.
     * 
     * @param l the listener to be removed
     */
    public void removeCellEditorListener(CellEditorListener l);

    /**
     * Returns an array of all the <code>CellEditorListener</code> s added to
     * this AbstractCellEditor with addCellEditorListener().
     * 
     * @return all of the <code>CellEditorListener</code> s added or an empty
     *         array if no listeners have been added
     * @since 1.4
     */
    public CellEditorListener[] getCellEditorListeners();

    /**
     * �\�[�g�\�ȃJ�����ł��邩 ��Ԃ��܂��B
     * 
     * @return �\�[�g�\�ȃJ�����ł��邩
     */
    public boolean isSortable();

    /**
     * �\�[�g�\�ȃJ�����ł��邩 ��ݒ肵�܂��B
     * 
     * @param sortable �\�[�g�\�ȃJ�����ł��邩
     */
    public void setSortable(boolean sortable);

    /**
     * �J�������N���b�N�����ꍇ�̏����ł��B
     * 
     * @param e �C�x���g���
     * @param column �J�����ԍ�
     */
    public void columnClick(MouseEvent e, int column);

    /**
     * �G�f�B�^�R���|�[�l���g�ɒ��ڋL���\�ł��邩��Ԃ��܂��B
     * 
     * @return �G�f�B�^�R���|�[�l���g�ɒ��ڋL���\�ł��邩
     */
    public boolean isComponentEditable();

    /**
     * �G�f�B�^�R���|�[�l���g�ɒ��ڋL���\�ł��邩��ݒ肵�܂��B
     * 
     * @param componentEditable �G�f�B�^�R���|�[�l���g�ɒ��ڋL���\�ł��邩
     */
    public void setComponentEditable(boolean componentEditable);
}
