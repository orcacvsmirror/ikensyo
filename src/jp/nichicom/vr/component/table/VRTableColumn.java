/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.EventListenerList;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

/**
 * <code>VRTableCellViewer</code> に対応したテーブルカラムです。
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
public class VRTableColumn extends TableColumn implements VRTableColumnar {
    protected EventListenerList listenerList = new EventListenerList();

    private boolean componentEditable = true;
    private boolean editable = false;
    private boolean sortable = true;
    private JTable table;
    private boolean visible = true;
    protected Format format;
    protected int horizontalAlignment = SwingConstants.LEFT;

    /**
     * Cover method, using a default model index of 0, default width of 75, a
     * <code>null</code> renderer and a <code>null</code> editor. This
     * method is intended for serialization.
     * 
     * @see #VRTableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public VRTableColumn() {
        super();
        initComponent();
    }

    /**
     * Cover method, using a default width of 75, a <code>null</code> renderer
     * and a <code>null</code> editor.
     * 
     * @see #VRTableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public VRTableColumn(int modelIndex) {
        super(modelIndex);
        initComponent();
    }

    /**
     * Cover method, using a <code>null</code> renderer and a
     * <code>null</code> editor.
     * 
     * @see #VRTableColumn(int, int, TableCellRenderer, TableCellEditor)
     */
    public VRTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
        initComponent();
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     */
    public VRTableColumn(int modelIndex, int width, String header) {
        super(modelIndex, width);
        setHeaderValue(header);
        initComponent();
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param format フォーマット
     */
    public VRTableColumn(int modelIndex, int width, String header, Format format) {
        super(modelIndex, width);
        setHeaderValue(header);
        setFormat(format);
        initComponent();
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param header ヘッダ
     * @param horizontalAlignment 水平方向の文字揃え
     */
    public VRTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment) {
        super(modelIndex, width);
        setHeaderValue(header);
        setHorizontalAlignment(horizontalAlignment);
        initComponent();
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
    public VRTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, Format format) {
        super(modelIndex, width);
        setHeaderValue(header);
        setHorizontalAlignment(horizontalAlignment);
        setFormat(format);
        initComponent();
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param width 幅
     * @param cellRenderer セルレンダラ
     * @param cellEditor セルエディタ
     */
    public VRTableColumn(int modelIndex, int width,
            TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
        initComponent();
    }

    /**
     * コンストラクタです。
     * 
     * @param modelIndex モデル番号
     * @param header ヘッダ
     */
    public VRTableColumn(int modelIndex, String header) {
        super(modelIndex);
        setHeaderValue(header);
        initComponent();
    }

    public String getEditorType() {
        TableCellEditor editor = getCellEditor();
        if (editor instanceof VRTableCellViewer) {
            return ((VRTableCellViewer) editor).getEditorType();
        }
        return null;
    }

    public Format getFormat() {
        return format;
    }

    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    public String getRendererType() {
        TableCellEditor renderer = getCellEditor();
        if (renderer instanceof VRTableCellViewer) {
            return ((VRTableCellViewer) renderer).getRendererType();
        }
        return null;
    }

    public JTable getTable() {
        return table;
    }

    public boolean isEditable() {
        return editable;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setCellEditor(TableCellEditor cellEditor) {
        if (getCellEditor() instanceof VRTableCellViewer) {
            // 以前のセルエディタのリスナ一覧から自身を除外
            ((VRTableCellViewer) getCellEditor())
                    .removeCellEditorListener(this);
        }
        if (cellEditor instanceof VRTableCellViewer) {
            ((VRTableCellViewer) cellEditor).setEditable(isEditable());
            // 新しいセルエディタのリスナ一覧に自身を登録
            ((VRTableCellViewer) cellEditor).addCellEditorListener(this);
        }
        super.setCellEditor(cellEditor);
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
        if (getCellEditor() instanceof VRTableCellViewer) {
            ((VRTableCellViewer) getCellEditor()).setEditable(editable);
        }
    }

    public void setEditorType(String editorType) {
        TableCellEditor editor = getCellEditor();
        if (editor instanceof VRTableCellViewer) {
            ((VRTableCellViewer) editor).setEditorType(editorType);
        }
    }

    public void setFormat(Format format) {
        this.format = format;
    }

    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    public void setRendererType(String rendererType) {
        TableCellEditor renderer = getCellEditor();
        if (renderer instanceof VRTableCellViewer) {
            ((VRTableCellViewer) renderer).setRendererType(rendererType);
        }
    }

    public void setTable(JTable table) {
        this.table = table;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * セルビュワーを生成します。
     * 
     * @return セルビュワー
     */
    protected VRTableCellViewer createTableCellViewer() {
        return new VRTableCellViewer();
    }

    /**
     * コンポーネントを初期化します。
     */
    protected void initComponent() {
        VRTableCellViewer editor = createTableCellViewer();
        if (getCellEditor() == null) {
            setCellEditor(editor);
        }
        if (getCellRenderer() == null) {
            setCellRenderer(editor);
        }
        editor.addCellEditorListener(this);

    }

    public void addEditor(String type, VRTableCellViewerDelegate ed) {
        TableCellEditor renderer = getCellEditor();
        if (renderer instanceof VRTableCellViewer) {
            ((VRTableCellViewer) renderer).addEditor(type, ed);
        }
    }

    public void addRenderer(String type, VRTableCellViewerDelegate ed) {
        TableCellEditor renderer = getCellEditor();
        if (renderer instanceof VRTableCellViewer) {
            ((VRTableCellViewer) renderer).addRenderer(type, ed);
        }
    }

    public void addCellEditorListener(CellEditorListener l) {
        listenerList.add(CellEditorListener.class, l);
    }

    public void removeCellEditorListener(CellEditorListener l) {
        listenerList.remove(CellEditorListener.class, l);
    }

    public CellEditorListener[] getCellEditorListeners() {
        return (CellEditorListener[]) listenerList
                .getListeners(CellEditorListener.class);
    }

    public void editingStopped(ChangeEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                ((CellEditorListener) listeners[i + 1]).editingStopped(e);
            }
        }
    }

    public void editingCanceled(ChangeEvent e) {
        // Guaranteed to return a non-null array
        Object[] listeners = listenerList.getListenerList();
        // Process the listeners last to first, notifying
        // those that are interested in this event
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == CellEditorListener.class) {
                ((CellEditorListener) listeners[i + 1]).editingCanceled(e);
            }
        }
    }

    public boolean isSortable() {
        return sortable;
    }

    public void setSortable(boolean sortable) {
        this.sortable = sortable;
    }

    public void columnClick(MouseEvent e, int column) {
        if (!isSortable()) {
            // ソート不可のカラムならば何もしない
            return;
        }
        if (getTable() instanceof VRTablar) {
            // ソート
            ((VRTablar) getTable()).sort(column);
        }
    }

    public boolean isComponentEditable() {
        return componentEditable;
    }

    public void setComponentEditable(boolean componentEditable) {
        this.componentEditable = componentEditable;
    }
    
    /**
     * セルエディタとして使用するコンポーネントを返します。
     * <p>
     * エディタ取得にかかる引数を[value=null,isSelected=false,row=0,column=0]として取得します。
     * </p>
     * @return セルエディタとして使用するコンポーネント
     */
    public Component getCellEditorComponent(){
        return getCellEditorComponent(null, false, 0,0);
    }
    /**
    /**
     * セルエディタとして使用するコンポーネントを返します。
     * @param value 値
     * @param isSelected 選択有無
     * @param row 行番号
     * @param column 列番号
     * @return セルエディタとして使用するコンポーネント
     */
    public Component getCellEditorComponent(Object value, boolean isSelected, int row, int column){
        TableCellEditor ed= getCellEditor();
        if(ed!=null){
            return ed.getTableCellEditorComponent(getTable(), value, isSelected, row,column);
        }
        return null;
    }
    /**
     * セルレンダラとして使用するコンポーネントを返します。
     * <p>
     * レンダラ取得にかかる引数を[value=null,isSelected=false,hasFocus=false,row=0,column=0]として取得します。
     * </p>
     * @return セルレンダラとして使用するコンポーネント
     */
    public Component getCellRendererComponent(){
        return getCellRendererComponent(null, false, false, 0,0);
    }
    /**
    /**
     * セルレンダラとして使用するコンポーネントを返します。
     * @param value 値
     * @param isSelected 選択有無
     * @param hasFocus フォーカス有無
     * @param row 行番号
     * @param column 列番号
     * @return セルレンダラとして使用するコンポーネント
     */
    public Component getCellRendererComponent(Object value, boolean isSelected, boolean hasFocus, int row, int column){
        TableCellRenderer ed= getCellRenderer();
        if(ed!=null){
            return ed.getTableCellRendererComponent(getTable(), value, isSelected, hasFocus, row,column);
        }
        return null;
    }

}