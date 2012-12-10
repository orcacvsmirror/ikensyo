package jp.nichicom.vr.component;

import java.awt.Color;
import java.awt.HeadlessException;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JViewport;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.Scrollable;
import javax.swing.TransferHandler;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.bind.event.VRBindModelEventListener;

/**
 * バインド機構を実装したリストボックスインターフェースです。
 * <p>
 * モデルバインド機構も実装しています。
 * </p>
 * <p>
 * 選択モードがListSelectionModel.SINGLE_SELECTIONの場合は選択している単一オジェクトがbind対象となり、それ以外の選択モードならば選択しているオブジェクトの配列がbind対象となります。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see JList
 * @see VRBindable
 * @see VRBindEventListener
 * @see VRBindSource
 * @see VRBindModelable
 * @see VRBindModelEventListener
 */
public interface VRListBoxar extends VRBindable, VRJComponentar,
        VRBindModelable, Scrollable {
    /**
     * Adds a listener to the list that's notified each time a change to the
     * selection occurs. Listeners added directly to the <code>JList</code>
     * will have their <code>ListSelectionEvent.getSource() == 
     * this JList</code>
     * (instead of the <code>ListSelectionModel</code>).
     * 
     * @param listener the <code>ListSelectionListener</code> to add
     * @see #getSelectionModel
     * @see #getListSelectionListeners
     */
    public void addListSelectionListener(ListSelectionListener listener);

    /**
     * Sets the selection to be the union of the specified interval with current
     * selection. Both the anchor and lead indices are included. It's not
     * necessary for anchor to be less than lead. This is a convenience method
     * that just delegates to the <code>selectionModel</code>. The
     * <code>DefaultListSelectionModel</code> implementation will do nothing
     * if either <code>anchor</code> or <code>lead</code> are -1. If
     * <code>anchor</code> or <code>lead</code> are less than -1,
     * <code>IndexOutOfBoundsException</code> is thrown.
     * 
     * @param anchor the first index to add to the selection
     * @param lead the last index to add to the selection
     * @see ListSelectionModel#addSelectionInterval
     * @see #setSelectionInterval
     * @see #removeSelectionInterval
     * @see #addListSelectionListener
     * @exception IndexOutOfBoundsException if either <code>anchor</code> or
     *                <code>lead</code> are less than -1
     */
    public void addSelectionInterval(int anchor, int lead);

    /**
     * Clears the selection - after calling this method
     * <code>isSelectionEmpty</code> will return true. This is a convenience
     * method that just delegates to the <code>selectionModel</code>.
     * 
     * @see ListSelectionModel#clearSelection
     * @see #isSelectionEmpty
     * @see #addListSelectionListener
     */
    public void clearSelection();

    /**
     * Scrolls the viewport to make the specified cell completely visible. Note,
     * for this method to work, the <code>JList</code> must be displayed
     * within a <code>JViewport</code>.
     * 
     * @param index the index of the cell to make visible
     * @see JComponent#scrollRectToVisible
     * @see #getVisibleRect
     */
    public void ensureIndexIsVisible(int index);

    /**
     * Returns the first index argument from the most recent
     * <code>addSelectionModel</code> or <code>setSelectionInterval</code>
     * call. This is a convenience method that just delegates to the
     * <code>selectionModel</code>.
     * 
     * @return the index that most recently anchored an interval selection
     * @see ListSelectionModel#getAnchorSelectionIndex
     * @see #addSelectionInterval
     * @see #setSelectionInterval
     * @see #addListSelectionListener
     */
    public int getAnchorSelectionIndex();

    /**
     * Returns the bounds of the specified range of items in <code>JList</code>
     * coordinates. Returns <code>null</code> if index isn't valid.
     * 
     * @param index0 the index of the first <code>JList</code> cell in the
     *            range
     * @param index1 the index of the last <code>JList</code> cell in the
     *            range
     * @return the bounds of the indexed cells in pixels
     */
    public Rectangle getCellBounds(int index0, int index1);

    /**
     * Returns the object that renders the list items.
     * 
     * @return the <code>ListCellRenderer</code>
     * @see #setCellRenderer
     */
    public ListCellRenderer getCellRenderer();

    /**
     * Gets the <code>dragEnabled</code> property.
     * 
     * @return the value of the <code>dragEnabled</code> property
     * @see #setDragEnabled
     * @since 1.4
     */
    public boolean getDragEnabled();

    /**
     * Returns the index of the first visible cell. The cell considered to be
     * "first" depends on the list's <code>componentOrientation</code>
     * property. If the orientation is horizontal left-to-right, then the first
     * visible cell is in the list's upper-left corner. If the orientation is
     * horizontal right-to-left, then the first visible cell is in the list's
     * upper-right corner. If nothing is visible or the list is empty, a -1 is
     * returned. Note that the returned cell may only be partially visible.
     * 
     * @return the index of the first visible cell
     * @see #getLastVisibleIndex
     * @see JComponent#getVisibleRect
     */
    public int getFirstVisibleIndex();

    /**
     * Returns the fixed cell height value -- the value specified by setting the
     * <code>fixedCellHeight</code> property, rather than that calculated from
     * the list elements.
     * 
     * @return the fixed cell height, in pixels
     * @see #setFixedCellHeight
     */
    public int getFixedCellHeight();

    /**
     * Returns the fixed cell width value -- the value specified by setting the
     * <code>fixedCellWidth</code> property, rather than that calculated from
     * the list elements.
     * 
     * @return the fixed cell width
     * @see #setFixedCellWidth
     */
    public int getFixedCellWidth();

    /**
     * Returns the index of the last visible cell. The cell considered to be
     * "last" depends on the list's <code>componentOrientation</code>
     * property. If the orientation is horizontal left-to-right, then the last
     * visible cell is in the JList's lower-right corner. If the orientation is
     * horizontal right-to-left, then the last visible cell is in the JList's
     * lower-left corner. If nothing is visible or the list is empty, a -1 is
     * returned. Note that the returned cell may only be partially visible.
     * 
     * @return the index of the last visible cell
     * @see #getFirstVisibleIndex
     * @see JComponent#getVisibleRect
     */
    public int getLastVisibleIndex();

    /**
     * Returns <code>JList.VERTICAL</code> if the layout is a single column of
     * cells, or <code>JList.VERTICAL_WRAP</code> if the layout is "newspaper
     * style" with the content flowing vertically then horizontally or
     * <code>JList.HORIZONTAL_WRAP</code> if the layout is "newspaper style"
     * with the content flowing horizontally then vertically.
     * 
     * @return the value of the layoutOrientation property
     * @see #setLayoutOrientation
     * @since 1.4
     */
    public int getLayoutOrientation();

    /**
     * Returns the second index argument from the most recent
     * <code>addSelectionInterval</code> or <code>setSelectionInterval</code>
     * call. This is a convenience method that just delegates to the
     * <code>selectionModel</code>.
     * 
     * @return the index that most recently ended a interval selection
     * @see ListSelectionModel#getLeadSelectionIndex
     * @see #addSelectionInterval
     * @see #setSelectionInterval
     * @see #addListSelectionListener
     */
    public int getLeadSelectionIndex();

    /**
     * Returns an array of all the <code>ListSelectionListener</code>s added
     * to this JList with addListSelectionListener().
     * 
     * @return all of the <code>ListSelectionListener</code>s added or an
     *         empty array if no listeners have been added
     * @see #addListSelectionListener
     * @since 1.4
     */
    public ListSelectionListener[] getListSelectionListeners();

    /**
     * Returns the largest selected cell index. This is a convenience method
     * that just delegates to the <code>selectionModel</code>.
     * 
     * @return the largest selected cell index
     * @see ListSelectionModel#getMaxSelectionIndex
     * @see #addListSelectionListener
     */
    public int getMaxSelectionIndex();

    /**
     * Returns the smallest selected cell index. This is a convenience method
     * that just delegates to the <code>selectionModel</code>.
     * 
     * @return the smallest selected cell index
     * @see ListSelectionModel#getMinSelectionIndex
     * @see #addListSelectionListener
     */
    public int getMinSelectionIndex();

    /**
     * Returns the data model that holds the list of items displayed by the
     * <code>JList</code> component.
     * 
     * @return the <code>ListModel</code> that provides the displayed list of
     *         items
     * @see #setModel
     */
    public ListModel getModel();

    /**
     * Returns the next list element that starts with a prefix.
     * 
     * @param prefix the string to test for a match
     * @param startIndex the index for starting the search
     * @param bias the search direction, either Position.Bias.Forward or
     *            Position.Bias.Backward.
     * @return the index of the next list element that starts with the prefix;
     *         otherwise -1
     * @exception IllegalArgumentException if prefix is null or startIndex is
     *                out of bounds
     * @since 1.4
     */
    public int getNextMatch(String prefix, int startIndex, Position.Bias bias);

    /**
     * Returns the cell width of the "prototypical cell" -- a cell used for the
     * calculation of cell widths, because it has the same value as all other
     * list items.
     * 
     * @return the value of the <code>prototypeCellValue</code> property
     * @see #setPrototypeCellValue
     */
    public Object getPrototypeCellValue();

    /**
     * Returns the first selected index; returns -1 if there is no selected
     * item.
     * 
     * @return the value of <code>getMinSelectionIndex</code>
     * @see #getMinSelectionIndex
     * @see #addListSelectionListener
     */
    public int getSelectedIndex();

    /**
     * Returns an array of all of the selected indices in increasing order.
     * 
     * @return all of the selected indices, in increasing order
     * @see #removeSelectionInterval
     * @see #addListSelectionListener
     */
    public int[] getSelectedIndices();

    /**
     * Returns the first selected value, or <code>null</code> if the selection
     * is empty.
     * 
     * @return the first selected value
     * @see #getMinSelectionIndex
     * @see #getModel
     * @see #addListSelectionListener
     */
    public Object getSelectedValue();

    /**
     * Returns an array of the values for the selected cells. The returned
     * values are sorted in increasing index order.
     * 
     * @return the selected values or an empty list if nothing is selected
     * @see #isSelectedIndex
     * @see #getModel
     * @see #addListSelectionListener
     */
    public Object[] getSelectedValues();

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
     * Returns the selection foreground color.
     * 
     * @return the <code>Color</code> object for the foreground property
     * @see #setSelectionForeground
     * @see #setSelectionBackground
     */
    public Color getSelectionForeground();

    /**
     * Returns whether single-item or multiple-item selections are allowed.
     * 
     * @return the value of the <code>selectionMode</code> property
     * @see #setSelectionMode
     */
    public int getSelectionMode();

    /**
     * Returns the value of the current selection model. The selection model
     * handles the task of making single selections, selections of contiguous
     * ranges, and non-contiguous selections.
     * 
     * @return the <code>ListSelectionModel</code> that implements list
     *         selections
     * @see #setSelectionModel
     * @see ListSelectionModel
     */
    public ListSelectionModel getSelectionModel();

    /**
     * Returns the value of the data model's <code>isAdjusting</code>
     * property. This value is true if multiple changes are being made.
     * 
     * @return true if multiple selection-changes are occurring, as when the
     *         mouse is being dragged over the list
     * @see ListSelectionModel#getValueIsAdjusting
     */
    public boolean getValueIsAdjusting();

    /**
     * bindによる単一選択時に選択した項目が見えるようにスクロールするかを返します。
     * 
     * @return 選択した項目が見えるようにスクロールするか
     */
    public boolean isShouldScrollOnSelect();

    /**
     * 単一選択モード(<code>ListSelectionModel.SINGLE_SELECTION</code>)であるかを返します。
     * 
     * @return 単一選択モードであるか
     */
    public boolean isSingleSelection();

    /**
     * モデルバインドパスを設定します。
     * 
     * @param modelBindPath モデルバインドパス
     */
    public void setModelBindPath(String modelBindPath);

    /**
     * bindによる単一選択時に選択した項目が見えるようにスクロールするかを返します。
     * 
     * @param shouldScrollOnSelect 選択した項目が見えるようにスクロールするか
     */
    public void setShouldScrollOnSelect(boolean shouldScrollOnSelect);

    /**
     * Sets the preferred number of rows in the list that can be displayed
     * without a scrollbar, as determined by the nearest <code>JViewport</code>
     * ancestor, if any. The value of this property only affects the value of
     * the <code>JList</code>'s <code>preferredScrollableViewportSize</code>.
     * <p>
     * The default value of this property is 8.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param visibleRowCount an integer specifying the preferred number of
     *            visible rows
     * @see #getVisibleRowCount
     * @see JComponent#getVisibleRect
     * @see JViewport
     */
    public void setVisibleRowCount(int visibleRowCount);

    /**
     * Selects the specified interval. Both the <code>anchor</code> and
     * <code>lead</code> indices are included. It's not necessary for
     * <code>anchor</code> to be less than <code>lead</code>. This is a
     * convenience method that just delegates to the <code>selectionModel</code>.
     * The <code>DefaultListSelectionModel</code> implementation will do
     * nothing if either <code>anchor</code> or <code>lead</code> are -1. If
     * <code>anchor</code> or <code>lead</code> are less than -1,
     * <code>IndexOutOfBoundsException</code> is thrown.
     * 
     * @param anchor the first index to select
     * @param lead the last index to select
     * @exception IndexOutOfBoundsException if either <code>anchor</code> or
     *                <code>lead</code> are less than -1
     * @see ListSelectionModel#setSelectionInterval
     * @see #addSelectionInterval
     * @see #removeSelectionInterval
     * @see #addListSelectionListener
     */
    public void setSelectionInterval(int anchor, int lead);

    /**
     * Sets the selection to be the set difference of the specified interval and
     * the current selection. Both the <code>index0</code> and
     * <code>index1</code> indices are removed. It's not necessary for
     * <code>index0</code> to be less than <code>index1</code>. This is a
     * convenience method that just delegates to the <code>selectionModel</code>.
     * The <code>DefaultListSelectionModel</code> implementation will do
     * nothing if either <code>index0</code> or <code>index1</code> are -1.
     * If <code>index0</code> or <code>index1</code> are less than -1,
     * <code>IndexOutOfBoundsException</code> is thrown.
     * 
     * @param index0 the first index to remove from the selection
     * @param index1 the last index to remove from the selection
     * @exception IndexOutOfBoundsException if either <code>index0</code> or
     *                <code>index1</code> are less than -1
     * @see ListSelectionModel#removeSelectionInterval
     * @see #setSelectionInterval
     * @see #addSelectionInterval
     * @see #addListSelectionListener
     */
    public void removeSelectionInterval(int index0, int index1);

    /**
     * Returns true if nothing is selected. This is a convenience method that
     * just delegates to the <code>selectionModel</code>.
     * 
     * @return true if nothing is selected
     * @see ListSelectionModel#isSelectionEmpty
     * @see #clearSelection
     * @see #addListSelectionListener
     */
    public boolean isSelectionEmpty();

    /**
     * Determines whether single-item or multiple-item selections are allowed.
     * The following <code>selectionMode</code> values are allowed:
     * <ul>
     * <li> <code>ListSelectionModel.SINGLE_SELECTION</code> Only one list
     * index can be selected at a time. In this mode the
     * <code>setSelectionInterval</code> and <code>addSelectionInterval</code>
     * methods are equivalent, and only the second index argument is used.
     * <li> <code>ListSelectionModel.SINGLE_INTERVAL_SELECTION</code> One
     * contiguous index interval can be selected at a time. In this mode
     * <code>setSelectionInterval</code> and <code>addSelectionInterval</code>
     * are equivalent.
     * <li> <code>ListSelectionModel.MULTIPLE_INTERVAL_SELECTION</code> In
     * this mode, there's no restriction on what can be selected. This is the
     * default.
     * </ul>
     * 
     * @param selectionMode an integer specifying the type of selections that
     *            are permissible
     * @see #getSelectionMode
     */
    public void setSelectionMode(int selectionMode);

    /**
     * Sets the <code>selectionModel</code> for the list to a non-<code>null</code> <code>ListSelectionModel</code>
     * implementation. The selection model handles the task of making single
     * selections, selections of contiguous ranges, and non-contiguous
     * selections.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param selectionModel the <code>ListSelectionModel</code> that
     *            implements the selections
     * @exception IllegalArgumentException if <code>selectionModel</code> is
     *                <code>null</code>
     * @see #getSelectionModel
     */
    public void setSelectionModel(ListSelectionModel selectionModel);

    /**
     * Sets the background color for selected cells. Cell renderers can use this
     * color to the fill selected cells.
     * <p>
     * The default value of this property is defined by the look and feel
     * implementation.
     * <p>
     * This is a JavaBeans bound property.
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

    /**
     * Sets the foreground color for selected cells. Cell renderers can use this
     * color to render text and graphics for selected cells.
     * <p>
     * The default value of this property is defined by the look and feel
     * implementation.
     * <p>
     * This is a JavaBeans bound property.
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

    /**
     * Returns true if the specified index is selected. This is a convenience
     * method that just delegates to the <code>selectionModel</code>.
     * 
     * @param index index to be queried for selection state
     * @return true if the specified index is selected
     * @see ListSelectionModel#isSelectedIndex
     * @see #setSelectedIndex
     * @see #addListSelectionListener
     */
    public boolean isSelectedIndex(int index);

    /**
     * Computes the <code>fixedCellWidth</code> and
     * <code>fixedCellHeight</code> properties by configuring the
     * <code>cellRenderer</code> to index equals zero for the specified value
     * and then computing the renderer component's preferred size. These
     * properties are useful when the list is too long to allow
     * <code>JList</code> to compute the width/height of each cell and there
     * is a single cell value that is known to occupy as much space as any of
     * the others.
     * <p>
     * Note that we do set the <code>fixedCellWidth</code> and
     * <code>fixedCellHeight</code> properties here but only a
     * <code>prototypeCellValue PropertyChangeEvent</code> is fired.
     * <p>
     * To see an example which sets this property, see the <a href =
     * #prototype_example>class description</a> above.
     * <p>
     * The default value of this property is <code>null</code>.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param prototypeCellValue the value on which to base
     *            <code>fixedCellWidth</code> and <code>fixedCellHeight</code>
     * @see #getPrototypeCellValue
     * @see #setFixedCellWidth
     * @see #setFixedCellHeight
     * @see JComponent#addPropertyChangeListener
     */
    public void setPrototypeCellValue(Object prototypeCellValue);

    /**
     * Sets the height of every cell in the list. If <code>height</code> is
     * -1, cell heights are computed by applying <code>getPreferredSize</code>
     * to the <code>cellRenderer</code> component for each list element.
     * <p>
     * The default value of this property is -1.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param height an integer giving the height, in pixels, for all cells in
     *            this list
     * @see #getPrototypeCellValue
     * @see #setFixedCellWidth
     * @see JComponent#addPropertyChangeListener
     */
    public void setFixedCellHeight(int height);

    /**
     * Sets the width of every cell in the list. If <code>width</code> is -1,
     * cell widths are computed by applying <code>getPreferredSize</code> to
     * the <code>cellRenderer</code> component for each list element.
     * <p>
     * The default value of this property is -1.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param width the width, in pixels, for all cells in this list
     * @see #getPrototypeCellValue
     * @see #setFixedCellWidth
     * @see JComponent#addPropertyChangeListener
     */
    public void setFixedCellWidth(int width);

    /**
     * Sets the model that represents the contents or "value" of the list and
     * clears the list selection after notifying
     * <code>PropertyChangeListeners</code>.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param model the <code>ListModel</code> that provides the list of items
     *            for display
     * @exception IllegalArgumentException if <code>model</code> is
     *                <code>null</code>
     * @see #getModel
     */
    public void setModel(ListModel model);

    /**
     * Defines the way list cells are layed out. Consider a <code>JList</code>
     * with four cells, this can be layed out in one of the following ways:
     * 
     * <pre>
     *       0
     *       1
     *       2
     *       3
     * </pre>
     * <pre>
     *       0  1
     *       2  3
     * </pre>
     * <pre>
     *       0  2
     *       1  3
     * </pre>
     * 
     * <p>
     * These correspond to the following values: <table border="1"
     * summary="Describes layouts VERTICAL, HORIZONTAL_WRAP, and VERTICAL_WRAP">
     * <tr>
     * <th>
     * <p align="left">
     * Value
     * </p>
     * </th>
     * <th>
     * <p align="left">
     * Description
     * </p>
     * </th>
     * </tr>
     * <tr>
     * <td><code>JList.VERTICAL</code>
     * <td>The cells should be layed out vertically in one column.
     * <tr>
     * <td><code>JList.HORIZONTAL_WRAP</code>
     * <td>The cells should be layed out horizontally, wrapping to a new row as
     * necessary. The number of rows to use will either be defined by
     * <code>getVisibleRowCount</code> if > 0, otherwise the number of rows
     * will be determined by the width of the <code>JList</code>.
     * <tr>
     * <td><code>JList.VERTICAL_WRAP</code>
     * <td>The cells should be layed out vertically, wrapping to a new column
     * as necessary. The number of rows to use will either be defined by
     * <code>getVisibleRowCount</code> if > 0, otherwise the number of rows
     * will be determined by the height of the <code>JList</code>. </table>
     * The default value of this property is <code>JList.VERTICAL</code>.
     * <p>
     * This will throw an <code>IllegalArgumentException</code> if
     * <code>layoutOrientation</code> is not one of
     * <code>JList.HORIZONTAL_WRAP</code> or <code>JList.VERTICAL</code> or
     * <code>JList.VERTICAL_WRAP</code>
     * 
     * @param layoutOrientation New orientation, one of
     *            <code>JList.HORIZONTAL_WRAP</code>,
     *            <code>JList.VERTICAL</code> or
     *            <code>JList.VERTICAL_WRAP</code>.
     * @see #getLayoutOrientation
     * @see #setVisibleRowCount
     * @see #getScrollableTracksViewportHeight
     * @since 1.4
     */
    public void setLayoutOrientation(int layoutOrientation);

    /**
     * Returns the preferred number of visible rows.
     * 
     * @return an integer indicating the preferred number of rows to display
     *         without using a scroll bar
     * @see #setVisibleRowCount
     */
    public int getVisibleRowCount();

    /**
     * Sets the delegate that's used to paint each cell in the list. If
     * <code>prototypeCellValue</code> was set then the
     * <code>fixedCellWidth</code> and <code>fixedCellHeight</code>
     * properties are set as well. Only one <code>PropertyChangeEvent</code>
     * is generated however - for the <code>cellRenderer</code> property.
     * <p>
     * The default value of this property is provided by the ListUI delegate,
     * i.e. by the look and feel implementation.
     * <p>
     * To see an example which sets the cell renderer, see the <a href =
     * #cellrenderer_example>class description</a> above.
     * <p>
     * This is a JavaBeans bound property.
     * 
     * @param cellRenderer the <code>ListCellRenderer</code> that paints list
     *            cells
     * @see #getCellRenderer
     */
    public void setCellRenderer(ListCellRenderer cellRenderer);

    /**
     * Sets the <code>dragEnabled</code> property, which must be
     * <code>true</code> to enable automatic drag handling (the first part of
     * drag and drop) on this component. The <code>transferHandler</code>
     * property needs to be set to a non-<code>null</code> value for the drag
     * to do anything. The default value of the <code>dragEnabled</code>
     * property is <code>false</code>.
     * <p>
     * When automatic drag handling is enabled, most look and feels begin a
     * drag-and-drop operation whenever the user presses the mouse button over a
     * selection and then moves the mouse a few pixels. Setting this property to
     * <code>true</code> can therefore have a subtle effect on how selections
     * behave.
     * <p>
     * Some look and feels might not support automatic drag and drop; they will
     * ignore this property. You can work around such look and feels by
     * modifying the component to directly call the <code>exportAsDrag</code>
     * method of a <code>TransferHandler</code>.
     * 
     * @param b the value to set the <code>dragEnabled</code> property to
     * @exception HeadlessException if <code>b</code> is <code>true</code>
     *                and <code>GraphicsEnvironment.isHeadless()</code>
     *                returns <code>true</code>
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see #getDragEnabled
     * @see #setTransferHandler
     * @see TransferHandler
     * @since 1.4
     */
    public void setDragEnabled(boolean b);

    /**
     * Selects a single cell.
     * 
     * @param index the index of the one cell to select
     * @see ListSelectionModel#setSelectionInterval
     * @see #isSelectedIndex
     * @see #addListSelectionListener
     */
    public void setSelectedIndex(int index);

}
