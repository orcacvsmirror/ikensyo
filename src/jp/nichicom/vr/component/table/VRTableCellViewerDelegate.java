package jp.nichicom.vr.component.table;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.io.Serializable;
import java.text.Format;
import java.text.ParseException;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.table.TableCellEditor;
import javax.swing.text.JTextComponent;

import jp.nichicom.vr.bind.VRBindModelable;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.util.VRHashMap;

/**
 * <code>VRTableCellViewer</code> で使用する処理委譲デリゲートクラスです。
 * <p>
 * <code>VRTableCellViewer</code>
 * はこのデリゲートクラスを介することで、アクセスメソッドの異なるエディタコンポーネントを透過的に扱います。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTableCellViewer
 * @see AbstractCellEditor
 */
public class VRTableCellViewerDelegate implements ActionListener, ItemListener,
        Serializable {

    private int clickCountToStart = 1;
    private Component component;

    private TableCellEditor editor;

    private Format format;
    private int horizontalAlignment;

    /** The value of this cell. */
    private Object value;

    /**
     * 直接記入可能なコンポーネントであるか を返します。
     * @return 直接記入可能なコンポーネントであるか
     */
    public boolean isEditable() {
        if(getComponent() instanceof JTextComponent){
            return ((JTextComponent)getComponent()).isEditable();
        }else if(getComponent() instanceof JComboBox){
            return ((JComboBox)getComponent()).isEditable();
        }
        return false;
    }

    /**
     * 直接記入可能なコンポーネントであるか を設定します。
     * @param editable 直接記入可能なコンポーネントであるか
     */
    public void setEditable(boolean editable) {
        if(getComponent() instanceof JTextComponent){
            ((JTextComponent)getComponent()).setEditable(editable);
        }else if(getComponent() instanceof JComboBox){
            ((JComboBox)getComponent()).setEditable(editable);
        }
    }

    /**
     * フォーマット を返します。
     * 
     * @return フォーマット
     */
    public Format getFormat() {
        return format;
    }

    /**
     * フォーマット を設定します。
     * 
     * @param format フォーマット
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * 水平方向の文字揃え を返します。
     * 
     * @return 水平方向の文字揃え
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * 水平方向の文字揃え を設定します。
     * 
     * @param horizontalAlignment 水平方向の文字揃え
     */
    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * コンストラクタです。
     * 
     * @param editor エディタ
     * @param component 描画部品
     */
    public VRTableCellViewerDelegate(TableCellEditor editor, Component component) {
        super();
        setEditor(editor);
        if (component instanceof JComponent) {
            ((JComponent) component).setOpaque(true);
        }
        setComponent(component);
    }

    /**
     * 設定する値をフォーマット変換します。
     * 
     * @param value 変換元
     * @return 変換結果
     */
    public Object formatValue(Object value) {
        Format fmt = getFormat();
        if (fmt != null) {
            value = fmt.format(value);
        }
        return value;
    }

    /**
     * 返却する値を解析変換します。
     * 
     * @param value 解析元
     * @return 解析結果
     */
    public Object parseValue(Object value) {
        Format fmt = getFormat();
        if (fmt != null) {
            try {
                Object tmp = fmt.parseObject(String.valueOf(value));
                value = tmp;
            } catch (ParseException e) {

            }
        }
        return value;
    }

    /**
     * When an action is performed, editing is ended.
     * 
     * @param e the action event
     * @see #stopCellEditing
     */
    public void actionPerformed(ActionEvent e) {
        getEditor().stopCellEditing();
    }

    /**
     * Cancels editing. This method calls <code>fireEditingCanceled</code>.
     */
    public void cancelCellEditing() {
        // getEditor().cancelCellEditing();
    }

    /**
     * セルエディタの値を返します。
     * <p>
     * エディタがVRBindableであればapplyを用いて値を取得します。
     * </p>
     * <p>
     * エディタがVRBindableでなければgetCellEditorValueeCustomメソッドを用いて値を設定します。
     * </p>
     * 
     * @return 値
     * @see #getValueCustom
     */
    public Object getValue() {
        Object ret = null;
        if (getComponent() instanceof VRBindable) {
            // bindableならばapplyを用いて値を取得する
            VRBindable bind = (VRBindable) getComponent();
            VRBindSource oldSource = bind.getSource();
            String oldPath = bind.getBindPath();
            String tmpPath = oldPath;
            if ((tmpPath == null) || ("".equals(tmpPath))) {
                tmpPath = "DATA";
            }
            try {
                VRHashMap map = new VRHashMap();
                bind.setBindPath(tmpPath);
                bind.setSource(map);
                bind.applySource();
                ret = parseValue(VRBindPathParser.get(tmpPath, map));
            } catch (Exception ex) {
            }
            bind.setSource(oldSource);
            bind.setBindPath(oldPath);
        } else {
            ret = getValueCustom();
        }
        return ret;
    }

    /**
     * Returns the value of this cell.
     * 
     * @return the value of this cell
     */
    public Object getValueCustom() {
        return value;
    }

    /**
     * clickCountToStart を返します。
     * 
     * @return clickCountToStart
     */
    public int getClickCountToStart() {
        return clickCountToStart;
    }

    public Component getComponent() {
        return component;
    }

    /**
     * editor を返します。
     * 
     * @return editor
     */
    public TableCellEditor getEditor() {
        return editor;
    }

    /**
     * Returns true if <code>anEvent</code> is <b>not </b> a
     * <code>MouseEvent</code>. Otherwise, it returns true if the necessary
     * number of clicks have occurred, and returns false otherwise.
     * 
     * @param anEvent the event
     * @return true if cell is ready for editing, false otherwise
     * @see #setClickCountToStart
     * @see #shouldSelectCell
     */
    public boolean isCellEditable(EventObject anEvent) {
        if (anEvent instanceof MouseEvent) {
            return ((MouseEvent) anEvent).getClickCount() >= getClickCountToStart();
        }
        return true;
    }

    /**
     * When an item's state changes, editing is ended.
     * 
     * @param e the action event
     * @see #stopCellEditing
     */
    public void itemStateChanged(ItemEvent e) {
        getEditor().stopCellEditing();
    }

    /**
     * clickCountToStart を設定します。
     * 
     * @param clickCountToStart clickCountToStart
     */
    public void setClickCountToStart(int clickCountToStart) {
        this.clickCountToStart = clickCountToStart;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    /**
     * セルエディタ を設定します。
     * 
     * @param editor セルエディタ
     */
    public void setEditor(TableCellEditor editor) {
        this.editor = editor;
    }

    /**
     * セルエディタに値を設定します。
     * <p>
     * エディタがVRBindableであればbindを用いて値を設定します。
     * </p>
     * <p>
     * エディタがVRBindableでなければsetValueCustomメソッドを用いて値を設定します。
     * </p>
     * 
     * @param value 値
     * @param row 行番号
     * @param column 列番号
     * @see #setValueCustom
     */
    public void setValue(Object value, int row, int column) {
        if (getComponent() instanceof VRBindable) {
            // bindableならばbindを用いて値を設定する
            VRBindable bind = (VRBindable) getComponent();
            VRBindSource oldSource = bind.getSource();
            String oldPath = bind.getBindPath();
            String tmpPath = oldPath;
            if ((tmpPath == null) || ("".equals(tmpPath))) {
                tmpPath = "DATA";
            }
            try {
                VRHashMap map = new VRHashMap();
                VRBindPathParser.set(tmpPath, map, formatValue(value));
                bind.setBindPath(tmpPath);
                bind.setSource(map);
                bind.bindSource();
            } catch (Exception ex) {
            }
            bind.setSource(oldSource);
            bind.setBindPath(oldPath);
        } else {
            setValueCustom(value, row, column);
        }
    }

    /**
     * セルエディタ固有の値設定方法を用いてセルエディタに値を設定します。
     * <p>
     * エディタがVRBindableでない場合に呼ばれます。
     * </p>
     * 
     * @param value 値
     * @param row 行番号
     * @param column 列番号
     */
    public void setValueCustom(Object value, int row, int column) {
        this.value = value;
    }

    /**
     * Returns true to indicate that the editing cell may be selected.
     * 
     * @param anEvent the event
     * @return true
     * @see #isCellEditable
     */
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    /**
     * Returns true to indicate that editing has begun.
     * 
     * @param anEvent the event
     */
    public boolean startCellEditing(EventObject anEvent) {
        return true;
    }

    /**
     * Stops editing and returns true to indicate that editing has stopped. This
     * method calls <code>fireEditingStopped</code>.
     * 
     * @return true
     */
    public boolean stopCellEditing() {
        return true;
    }

    private Object model;

    /**
     * モデルを設定します。
     * 
     * @param model モデル
     */
    public void setModel(Object model) {
        this.model = model;
        if (getComponent() instanceof VRBindModelable) {
            VRBindModelable cmp = (VRBindModelable) getComponent();
            VRBindSource oldSource = cmp.getModelSource();
            String oldPath = cmp.getModelBindPath();
            String tmpPath = oldPath;
            if ((tmpPath == null) || ("".equals(tmpPath))) {
                tmpPath = "MODEL";
            }

            try {
                VRHashMap map = new VRHashMap();
                VRBindPathParser.set(tmpPath, map, model);
                cmp.setModelBindPath(tmpPath);
                cmp.setModelSource(map);
                cmp.bindModelSource();
            } catch (Exception ex) {
            }
            cmp.setModelSource(oldSource);
            cmp.setModelBindPath(oldPath);
        }
    }

    /**
     * モデルを返します。
     * 
     * @return モデル
     */
    public Object getModel() {
        return model;
    }
}