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
 * <code>VRTableCellViewer</code> �Ŏg�p���鏈���Ϗ��f���Q�[�g�N���X�ł��B
 * <p>
 * <code>VRTableCellViewer</code>
 * �͂��̃f���Q�[�g�N���X����邱�ƂŁA�A�N�Z�X���\�b�h�̈قȂ�G�f�B�^�R���|�[�l���g�𓧉ߓI�Ɉ����܂��B
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
     * ���ڋL���\�ȃR���|�[�l���g�ł��邩 ��Ԃ��܂��B
     * @return ���ڋL���\�ȃR���|�[�l���g�ł��邩
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
     * ���ڋL���\�ȃR���|�[�l���g�ł��邩 ��ݒ肵�܂��B
     * @param editable ���ڋL���\�ȃR���|�[�l���g�ł��邩
     */
    public void setEditable(boolean editable) {
        if(getComponent() instanceof JTextComponent){
            ((JTextComponent)getComponent()).setEditable(editable);
        }else if(getComponent() instanceof JComboBox){
            ((JComboBox)getComponent()).setEditable(editable);
        }
    }

    /**
     * �t�H�[�}�b�g ��Ԃ��܂��B
     * 
     * @return �t�H�[�}�b�g
     */
    public Format getFormat() {
        return format;
    }

    /**
     * �t�H�[�}�b�g ��ݒ肵�܂��B
     * 
     * @param format �t�H�[�}�b�g
     */
    public void setFormat(Format format) {
        this.format = format;
    }

    /**
     * ���������̕������� ��Ԃ��܂��B
     * 
     * @return ���������̕�������
     */
    public int getHorizontalAlignment() {
        return horizontalAlignment;
    }

    /**
     * ���������̕������� ��ݒ肵�܂��B
     * 
     * @param horizontalAlignment ���������̕�������
     */
    public void setHorizontalAlignment(int horizontalAlignment) {
        this.horizontalAlignment = horizontalAlignment;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param editor �G�f�B�^
     * @param component �`�敔�i
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
     * �ݒ肷��l���t�H�[�}�b�g�ϊ����܂��B
     * 
     * @param value �ϊ���
     * @return �ϊ�����
     */
    public Object formatValue(Object value) {
        Format fmt = getFormat();
        if (fmt != null) {
            value = fmt.format(value);
        }
        return value;
    }

    /**
     * �ԋp����l����͕ϊ����܂��B
     * 
     * @param value ��͌�
     * @return ��͌���
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
     * �Z���G�f�B�^�̒l��Ԃ��܂��B
     * <p>
     * �G�f�B�^��VRBindable�ł����apply��p���Ēl���擾���܂��B
     * </p>
     * <p>
     * �G�f�B�^��VRBindable�łȂ����getCellEditorValueeCustom���\�b�h��p���Ēl��ݒ肵�܂��B
     * </p>
     * 
     * @return �l
     * @see #getValueCustom
     */
    public Object getValue() {
        Object ret = null;
        if (getComponent() instanceof VRBindable) {
            // bindable�Ȃ��apply��p���Ēl���擾����
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
     * clickCountToStart ��Ԃ��܂��B
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
     * editor ��Ԃ��܂��B
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
     * clickCountToStart ��ݒ肵�܂��B
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
     * �Z���G�f�B�^ ��ݒ肵�܂��B
     * 
     * @param editor �Z���G�f�B�^
     */
    public void setEditor(TableCellEditor editor) {
        this.editor = editor;
    }

    /**
     * �Z���G�f�B�^�ɒl��ݒ肵�܂��B
     * <p>
     * �G�f�B�^��VRBindable�ł����bind��p���Ēl��ݒ肵�܂��B
     * </p>
     * <p>
     * �G�f�B�^��VRBindable�łȂ����setValueCustom���\�b�h��p���Ēl��ݒ肵�܂��B
     * </p>
     * 
     * @param value �l
     * @param row �s�ԍ�
     * @param column ��ԍ�
     * @see #setValueCustom
     */
    public void setValue(Object value, int row, int column) {
        if (getComponent() instanceof VRBindable) {
            // bindable�Ȃ��bind��p���Ēl��ݒ肷��
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
     * �Z���G�f�B�^�ŗL�̒l�ݒ���@��p���ăZ���G�f�B�^�ɒl��ݒ肵�܂��B
     * <p>
     * �G�f�B�^��VRBindable�łȂ��ꍇ�ɌĂ΂�܂��B
     * </p>
     * 
     * @param value �l
     * @param row �s�ԍ�
     * @param column ��ԍ�
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
     * ���f����ݒ肵�܂��B
     * 
     * @param model ���f��
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
     * ���f����Ԃ��܂��B
     * 
     * @return ���f��
     */
    public Object getModel() {
        return model;
    }
}