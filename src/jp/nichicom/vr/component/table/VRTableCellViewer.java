/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.MouseEvent;
import java.util.EventObject;
import java.util.HashMap;

import javax.swing.AbstractCellEditor;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.JTextComponent;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;

import jp.nichicom.vr.component.VRCheckBox;
import jp.nichicom.vr.component.VRComboBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.VRTextField;

/**
 * レンダラとエディタ兼用テーブルセルビューワです。
 * <p>
 * RendererType/EditorTypeを設定することで使用するコントロールを変更できます。
 * </p>
 * <p>
 * 使用するコントロールは <code>VRTableCellViewerDelegate</code> を介することで自由に拡張可能です。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see TableCellEditor
 * @see TableCellRenderer
 * @see TreeCellEditor
 * @see TreeCellRenderer
 * @see VRTableCellViewerDelegate
 */
public class VRTableCellViewer extends AbstractCellEditor implements
        TableCellEditor, TableCellRenderer, TreeCellEditor, TreeCellRenderer {

    /**
     * チェックボックス形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_CHECK_BOX = "CheckBox";

    /**
     * コンボボックス形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_COMBO_BOX = "ComboBox";

    /**
     * ラベル形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_LABEL = "Label";

    /**
     * テキストフィールド形式を表すエディタ定数です。
     */
    public static final String EDITOR_TYPE_TEXT_FIELD = "TextField";

    /**
     * チェックボックス形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_CHECK_BOX = "CheckBox";

    /**
     * コンボボックス形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_COMBO_BOX = "ComboBox";

    /**
     * ラベル形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_LABEL = "Label";

    /**
     * テキストフィールド形式を表すレンダラ定数です。
     */
    public static final String RENDERER_TYPE_TEXT_FIELD = "TextField";

    private JLabel defaultRenderer;

    private boolean editable = false;

    private HashMap editorDelegateHash = new HashMap();

    private String editorType = VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD;

    private HashMap rendererDelegateHash = new HashMap();

    private String rendererType = VRTableCellViewer.RENDERER_TYPE_LABEL;

    /**
     * The delegate class which handles all methods sent from the
     * <code>CellEditor</code>.
     */
    protected VRTableCellViewerDelegate delegate;

    public VRTableCellViewer() {
        super();
        initComponent();
    }

    /**
     * 編集タイプと使用する編集デリゲートを追加します。
     * 
     * @param type 編集タイプ
     * @param ed 編集デリゲート
     */
    public void addEditor(String type, VRTableCellViewerDelegate ed) {
        editorDelegateHash.put(type, ed);
    }

    /**
     * 描画タイプと使用する描画デリゲートを追加します。
     * 
     * @param type 描画タイプ
     * @param ed 描画デリゲート
     */
    public void addRenderer(String type, VRTableCellViewerDelegate ed) {
        rendererDelegateHash.put(type, ed);
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the
     * <code>delegate</code>.
     * 
     * @see VRTableCellViewerDelegate#cancelCellEditing
     */
    public void cancelCellEditing() {
        if (delegate != null) {
            delegate.cancelCellEditing();
        }
        fireEditingCanceled();
    }

    /**
     * チェックボックス型のデリゲートを生成します。
     * 
     * @param component コンポーネント
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createEditorDelegate(
            final JCheckBox component) {
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValueCustom() {
                return parseValue(Boolean.valueOf(component.isSelected()));
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValueCustom(Object value, int row, int column) {
                value = formatValue(value);
                if (value instanceof String) {
                    value = Boolean.valueOf((String) value);
                } else if (!(value instanceof Boolean)) {
                    value = Boolean.valueOf(String.valueOf(value));
                }

                boolean selected = false;
                if (value instanceof Boolean) {
                    selected = ((Boolean) value).booleanValue();
                }
                component.setSelected(selected);
            }
        };
        component.addActionListener(ed);

        return ed;
    }

    /**
     * コンボボックス型のデリゲートを生成します。
     * 
     * @param component コンポーネント
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createEditorDelegate(
            final JComboBox component) {
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValueCustom() {
                return parseValue(component.getSelectedItem());
            }

            public void setValueCustom(Object value, int row, int column) {
                value = formatValue(value);
                component.setSelectedItem(value);
            }

            public boolean shouldSelectCell(EventObject anEvent) {
                if (anEvent instanceof MouseEvent) {
                    MouseEvent e = (MouseEvent) anEvent;
                    return e.getID() != MouseEvent.MOUSE_DRAGGED;
                }
                return true;
            }

            public boolean stopCellEditing() {
                if (component.isEditable()) {
                    // Commit edited value.
                    component.actionPerformed(new ActionEvent(this, 0, ""));
                }
                return super.stopCellEditing();
            }
        };
        component.addActionListener(ed);
        component
                .putClientProperty("JComboBox.isTableCellEditor", Boolean.TRUE);

        return ed;
    }

    /**
     * ラベル型のデリゲートを生成します。
     * 
     * @param component コンポーネント
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createEditorDelegate(final JLabel component) {
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValueCustom() {
                return parseValue(component.getText());
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValueCustom(Object value, int row, int column) {
                value = formatValue(value);
                component.setText((value != null) ? value.toString() : "");
            }
        };

        return ed;
    }

    /**
     * テキスト型のデリゲートを生成します。
     * 
     * @param component コンポーネント
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createEditorDelegate(
            final JTextField component) {
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValueCustom() {
                return parseValue(component.getText());
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValueCustom(Object value, int row, int column) {
                value = formatValue(value);
                component.setText((value != null) ? value.toString() : "");
            }
        };
        component.addActionListener(ed);

        return ed;
    }

    /**
     * トグルボタン型のデリゲートを生成します。
     * 
     * @param component コンポーネント
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createEditorDelegate(
            final JToggleButton component) {
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValueCustom() {
                return parseValue(Boolean.valueOf(component.isSelected()));
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValueCustom(Object value, int row, int column) {
                value = formatValue(value);
                boolean selected = false;
                if (value instanceof Boolean) {
                    selected = ((Boolean) value).booleanValue();
                } else if (value instanceof String) {
                    selected = Boolean.getBoolean((String) value);
                } else {
                    selected = Boolean.getBoolean(String.valueOf(value));
                }
                component.setSelected(selected);
            }
        };
        component.addActionListener(ed);

        return ed;
    }

    /**
     * エディタタイプに応じたデリゲートを生成します。
     * 
     * @param type エディタタイプ
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createEditorDelegate(final String type) {
        if (VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD.equals(type)) {
            return createEditorDelegate(new VRTextField());
        } else if (VRTableCellViewer.EDITOR_TYPE_LABEL.equals(type)) {
            return createEditorDelegate(new VRLabel());
        } else if (VRTableCellViewer.EDITOR_TYPE_CHECK_BOX.equals(type)) {
            return createEditorDelegate(new VRCheckBox());
        } else if (VRTableCellViewer.EDITOR_TYPE_COMBO_BOX.equals(type)) {
            return createEditorDelegate(new VRComboBox());
        }
        return null;
    }

    /**
     * レンダラタイプに応じたデリゲートを生成します。
     * 
     * @param type レンダラタイプ
     * @return デリゲート
     */
    public VRTableCellViewerDelegate createRendererDelegate(final String type) {
        if (VRTableCellViewer.RENDERER_TYPE_TEXT_FIELD.equals(type)) {
            return createEditorDelegate(new VRTextField());
        } else if (VRTableCellViewer.RENDERER_TYPE_LABEL.equals(type)) {
            return createEditorDelegate(new VRLabel());
        } else if (VRTableCellViewer.RENDERER_TYPE_CHECK_BOX.equals(type)) {
            return createEditorDelegate(new VRCheckBox());
        } else if (VRTableCellViewer.RENDERER_TYPE_COMBO_BOX.equals(type)) {
            return createEditorDelegate(new VRComboBox());
        }
        return null;
    }

    public Object getCellEditorValue() {
        if (delegate != null) {
            if(delegate.getComponent() instanceof Component){
                delegate.getComponent().dispatchEvent(new FocusEvent(delegate.getComponent(), FocusEvent.FOCUS_LOST)); 
            }
            return delegate.getValue();
        }
        return null;
    }

    /**
     * エディタとして使用するデリゲートの定義ハッシュを返します。
     * 
     * @return エディタデリゲートの定義ハッシュ
     */
    public HashMap getEditorDelegateHash() {
        return editorDelegateHash;
    }

    /**
     * エディタ用のモデルを返します。
     * @return エディタ用のモデル
     */
    public Object getEditorModel(){
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            return ed.getModel();
        }
        return null;
    }

    /**
     * エディタ形式を返します。
     * 
     * @return エディタ形式
     */
    public String getEditorType() {
        return editorType;
    }

    /**
     * レンダラとして使用するデリゲートの定義ハッシュを返します。
     * 
     * @return レンダラデリゲートの定義ハッシュ
     */
    public HashMap getRendererDelegateHash() {
        return rendererDelegateHash;
    }

    /**
     * レンダラ用のモデルを返します。
     * @return レンダラ用のモデル
     */
    public Object getRendererModel(){
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            return ed.getModel();
        }
        return null;
    }

    /**
     * レンダラ形式を返します。
     * 
     * @return レンダラ形式
     */
    public String getRendererType() {
        return rendererType;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        VRTableCellViewerDelegate ed = getTableCellDelegate(table, value, row,
                column, getEditorDelegateHash(), getEditorType());

        Component cmp = null;
        if (ed instanceof VRTableCellViewerDelegate) {
            cmp = ed.getComponent();
            delegate = ed;
        }

        return cmp;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        VRTableCellViewerDelegate ed = getTableCellDelegate(table, value, row,
                column, getRendererDelegateHash(), getRendererType());
        Component cmp = null;
        if (ed instanceof VRTableCellViewerDelegate) {
            cmp = ed.getComponent();
        }

        if (cmp == null) {
            cmp = getDefaultCellRenderer(value, isSelected, hasFocus, row,
                    column);
        }

        applyTableCellColor(table, value, isSelected, hasFocus, row, column,
                cmp);
        
        return cmp;
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
            boolean isSelected, boolean expanded, boolean leaf, int row) {
        String stringValue = tree.convertValueToText(value, isSelected,
                expanded, leaf, row, false);
        VRTableCellViewerDelegate ed = getTreeCellDelegate(tree, stringValue,
                row, getRendererDelegateHash(), getRendererType());

        Component cmp = null;
        if (ed instanceof VRTableCellViewerDelegate) {
            cmp = ed.getComponent();

            delegate = ed;
        }

        return cmp;
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        String stringValue = tree.convertValueToText(value, selected, expanded,
                leaf, row, false);

        VRTableCellViewerDelegate ed = getTreeCellDelegate(tree, stringValue,
                row, getRendererDelegateHash(), getRendererType());

        Component cmp = null;
        if (ed instanceof VRTableCellViewerDelegate) {
            cmp = ed.getComponent();
        }

        if (cmp == null) {
            cmp = getDefaultCellRenderer(stringValue, selected, hasFocus, row,
                    0);
        }
        return cmp;
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the
     * <code>delegate</code>.
     */
    public boolean isCellEditable(EventObject anEvent) {
        if (!isEditable()) {
            return false;
        }
        if (delegate != null) {
            return delegate.isCellEditable(anEvent);
        }
        return true;
    }

    /**
     * 編集可能かを返します。
     * 
     * @return 編集可能か
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * 編集可能かを設定します。
     * 
     * @param editable 編集可能か
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * エディタとして使用するデリゲートの定義ハッシュを設定します。
     * 
     * @param editorDelegateHash エディタデリゲートの定義ハッシュ
     */
    public void setEditorDelegateHash(HashMap editorDelegateHash) {
        this.editorDelegateHash = editorDelegateHash;
    }

    /**
     * エディタ用のモデルを設定します。
     * @param model エディタ用のモデル
     */
    public void setEditorModel(Object model){
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            ed.setModel(model);
        }
    }

    /**
     * エディタ形式を設定します。
     * 
     * @param editorType エディタ形式
     */
    public void setEditorType(String editorType) {
        if (getEditorDelegateHash().get(editorType) == null) {
            // 未登録のエディタなら新規生成
            addEditor(editorType, createEditorDelegate(editorType));
        }

        this.editorType = editorType;
    }

    /**
     * レンダラとして使用するデリゲートの定義ハッシュを設定します。
     * 
     * @param rendererDelegateHash レンダラデリゲートの定義ハッシュ
     */
    public void setRendererDelegateHash(HashMap rendererDelegateHash) {
        this.rendererDelegateHash = rendererDelegateHash;
    }

    /**
     * レンダラ用のモデルを設定します。
     * @param model レンダラ用のモデル
     */
    public void setRendererModel(Object model){
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            ed.setModel(model);
        }
    }

    /**
     * レンダラ形式を設定します。
     * 
     * @param rendererType レンダラ形式
     */
    public void setRendererType(String rendererType) {
        if (getRendererDelegateHash().get(rendererType) == null) {
            // 未登録のレンダラなら新規生成
            addRenderer(rendererType, createRendererDelegate(rendererType));
        }

        this.rendererType = rendererType;

    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the
     * <code>delegate</code>.
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        if (delegate != null) {
            return delegate.shouldSelectCell(anEvent);
        }
        return true;
    }

    /**
     * Forwards the message from the <code>CellEditor</code> to the
     * <code>delegate</code>.
     */
    public boolean stopCellEditing() {
        if (delegate != null) {
            if (delegate.stopCellEditing()) {
                fireEditingStopped();
                return true;
            }
        }
        return false;
    }

    /**
     * テーブルセルの選択状態に応じてコンポーネントを着色します。
     * 
     * @param table テーブル
     * @param value 値
     * @param isSelected 選択状態か
     * @param hasFocus フォーカスを保持しているか
     * @param row 行
     * @param column 列
     * @param cmp 設定先コンポーネント
     */
    protected void applyTableCellColor(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column,
            Component cmp) {

        if (cmp != null) {
            // 選択色の着色
            if (isSelected) {
                if (table.hasFocus()) {
                    cmp.setBackground(UIManager
                            .getColor("Table.selectionBackground"));
                    cmp.setForeground(UIManager
                            .getColor("Table.selectionForeground"));
                } else {
                    cmp.setBackground(UIManager.getColor(
                            "Table.selectionBackground").brighter());
                    cmp.setForeground(UIManager
                            .getColor("Table.selectionForeground"));
                }
            } else {
                cmp.setForeground(UIManager.getColor("Table.foreground"));
                if ((table instanceof VRTable)
                        && (((VRTable) table).isStripe())) {
                    if (row % 2 != 0) {
                        Color stripeColor = ((VRTable) table).getStripeColor();
                        if (stripeColor != null) {
                            cmp.setBackground(stripeColor);
                        } else {
                            cmp.setBackground(UIManager
                                    .getColor("Table.background"));
                        }
                    } else {
                        cmp.setBackground(UIManager
                                .getColor("Table.background"));
                    }
                } else {
                    cmp.setBackground(UIManager.getColor("Table.background"));
                }
            }
        }
    }

    /**
     * 標準のレンダラを生成します。
     * 
     * @param value the value to assign to the cell at
     *            <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row the row of the cell to render
     * @param column the column of the cell to render
     * @return 標準のレンダラ
     */
    protected Component getDefaultCellRenderer(Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        if (defaultRenderer == null) {
            defaultRenderer = new JLabel();
            defaultRenderer.setOpaque(true);
        }
        defaultRenderer.setText(String.valueOf(value));
        return defaultRenderer;
    }
    

    /**
     * 初期状態のエディタ形式を返します。
     * @return 初期状態のエディタ形式
     */
    protected String getDefaultEditorType(){
        return VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD;
    }
    /**
     * 初期状態のレンダラ形式を返します。
     * @return 初期状態のレンダラ形式
     */
    protected String getDefaultRendererType(){
        return VRTableCellViewer.RENDERER_TYPE_LABEL;
    }
    /**
     * エディタ用のデリゲートを返します。
     * @return エディタ用のデリゲート
     */
    protected VRTableCellViewerDelegate getEditorDelegate(){
        return (VRTableCellViewerDelegate) getEditorDelegateHash()
        .get(getEditorType());
    }

    /**
     * レンダラ用のデリゲートを返します。
     * @return レンダラ用のデリゲート
     */
    protected VRTableCellViewerDelegate getRendererDelegate(){
        return (VRTableCellViewerDelegate) getRendererDelegateHash()
        .get(getRendererType());
    }

    /**
     * テーブルセル用のデリゲートを返します。
     * 
     * @param table テーブル
     * @param value 値
     * @param row 行
     * @param column 列
     * @param delegateHash デリゲートハッシュ
     * @param type デリゲートタイプ
     * @return デリゲート
     */
    protected VRTableCellViewerDelegate getTableCellDelegate(JTable table,
            Object value, int row, int column, HashMap delegateHash, String type) {
        VRTableCellViewerDelegate ed = (VRTableCellViewerDelegate) delegateHash
                .get(type);
        if (ed instanceof VRTableCellViewerDelegate) {
            if ((table!=null)&&(table.getColumnModel() != null)) {
                TableColumn tableColumn = table.getColumnModel().getColumn(
                        column);
                if (tableColumn instanceof VRTableColumnar) {
                    VRTableColumnar clm = (VRTableColumnar) tableColumn;

                    ed.setFormat(clm.getFormat());
                    ed.setHorizontalAlignment(clm.getHorizontalAlignment());
                    ed.setEditable(clm.isComponentEditable());
                }
            }
            if(row<0){
                ed.setFormat(null);
                ed.setValueCustom(value, row, column);
            }else{
                ed.setValue(value, row, column);
            }
        }
        return ed;
    }
    /**
     * ツリーセル用のデリゲートを返します。
     * 
     * @param tree ツリー
     * @param value 値
     * @param row 行
     * @param delegateHash デリゲートハッシュ
     * @param type デリゲートタイプ
     * @return デリゲート
     */
    protected VRTableCellViewerDelegate getTreeCellDelegate(JTree tree,
            Object value, int row, HashMap delegateHash, String type) {
        VRTableCellViewerDelegate ed = (VRTableCellViewerDelegate) delegateHash
                .get(type);
        if (ed instanceof VRTableCellViewerDelegate) {
            ed.setValue(value, row, 0);
        }
        return ed;
    }
    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {
        setRendererType(getDefaultRendererType());
        setEditorType(getDefaultEditorType());
    }

}