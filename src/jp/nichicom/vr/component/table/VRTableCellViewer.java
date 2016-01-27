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
 * �����_���ƃG�f�B�^���p�e�[�u���Z���r���[���ł��B
 * <p>
 * RendererType/EditorType��ݒ肷�邱�ƂŎg�p����R���g���[����ύX�ł��܂��B
 * </p>
 * <p>
 * �g�p����R���g���[���� <code>VRTableCellViewerDelegate</code> ����邱�ƂŎ��R�Ɋg���\�ł��B
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
     * �`�F�b�N�{�b�N�X�`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_CHECK_BOX = "CheckBox";

    /**
     * �R���{�{�b�N�X�`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_COMBO_BOX = "ComboBox";

    /**
     * ���x���`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_LABEL = "Label";

    /**
     * �e�L�X�g�t�B�[���h�`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_TEXT_FIELD = "TextField";

    /**
     * �`�F�b�N�{�b�N�X�`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_CHECK_BOX = "CheckBox";

    /**
     * �R���{�{�b�N�X�`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_COMBO_BOX = "ComboBox";

    /**
     * ���x���`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_LABEL = "Label";

    /**
     * �e�L�X�g�t�B�[���h�`����\�������_���萔�ł��B
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
     * �ҏW�^�C�v�Ǝg�p����ҏW�f���Q�[�g��ǉ����܂��B
     * 
     * @param type �ҏW�^�C�v
     * @param ed �ҏW�f���Q�[�g
     */
    public void addEditor(String type, VRTableCellViewerDelegate ed) {
        editorDelegateHash.put(type, ed);
    }

    /**
     * �`��^�C�v�Ǝg�p����`��f���Q�[�g��ǉ����܂��B
     * 
     * @param type �`��^�C�v
     * @param ed �`��f���Q�[�g
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
     * �`�F�b�N�{�b�N�X�^�̃f���Q�[�g�𐶐����܂��B
     * 
     * @param component �R���|�[�l���g
     * @return �f���Q�[�g
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
     * �R���{�{�b�N�X�^�̃f���Q�[�g�𐶐����܂��B
     * 
     * @param component �R���|�[�l���g
     * @return �f���Q�[�g
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
     * ���x���^�̃f���Q�[�g�𐶐����܂��B
     * 
     * @param component �R���|�[�l���g
     * @return �f���Q�[�g
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
     * �e�L�X�g�^�̃f���Q�[�g�𐶐����܂��B
     * 
     * @param component �R���|�[�l���g
     * @return �f���Q�[�g
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
     * �g�O���{�^���^�̃f���Q�[�g�𐶐����܂��B
     * 
     * @param component �R���|�[�l���g
     * @return �f���Q�[�g
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
     * �G�f�B�^�^�C�v�ɉ������f���Q�[�g�𐶐����܂��B
     * 
     * @param type �G�f�B�^�^�C�v
     * @return �f���Q�[�g
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
     * �����_���^�C�v�ɉ������f���Q�[�g�𐶐����܂��B
     * 
     * @param type �����_���^�C�v
     * @return �f���Q�[�g
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
     * �G�f�B�^�Ƃ��Ďg�p����f���Q�[�g�̒�`�n�b�V����Ԃ��܂��B
     * 
     * @return �G�f�B�^�f���Q�[�g�̒�`�n�b�V��
     */
    public HashMap getEditorDelegateHash() {
        return editorDelegateHash;
    }

    /**
     * �G�f�B�^�p�̃��f����Ԃ��܂��B
     * @return �G�f�B�^�p�̃��f��
     */
    public Object getEditorModel(){
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            return ed.getModel();
        }
        return null;
    }

    /**
     * �G�f�B�^�`����Ԃ��܂��B
     * 
     * @return �G�f�B�^�`��
     */
    public String getEditorType() {
        return editorType;
    }

    /**
     * �����_���Ƃ��Ďg�p����f���Q�[�g�̒�`�n�b�V����Ԃ��܂��B
     * 
     * @return �����_���f���Q�[�g�̒�`�n�b�V��
     */
    public HashMap getRendererDelegateHash() {
        return rendererDelegateHash;
    }

    /**
     * �����_���p�̃��f����Ԃ��܂��B
     * @return �����_���p�̃��f��
     */
    public Object getRendererModel(){
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            return ed.getModel();
        }
        return null;
    }

    /**
     * �����_���`����Ԃ��܂��B
     * 
     * @return �����_���`��
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
     * �ҏW�\����Ԃ��܂��B
     * 
     * @return �ҏW�\��
     */
    public boolean isEditable() {
        return editable;
    }

    /**
     * �ҏW�\����ݒ肵�܂��B
     * 
     * @param editable �ҏW�\��
     */
    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    /**
     * �G�f�B�^�Ƃ��Ďg�p����f���Q�[�g�̒�`�n�b�V����ݒ肵�܂��B
     * 
     * @param editorDelegateHash �G�f�B�^�f���Q�[�g�̒�`�n�b�V��
     */
    public void setEditorDelegateHash(HashMap editorDelegateHash) {
        this.editorDelegateHash = editorDelegateHash;
    }

    /**
     * �G�f�B�^�p�̃��f����ݒ肵�܂��B
     * @param model �G�f�B�^�p�̃��f��
     */
    public void setEditorModel(Object model){
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            ed.setModel(model);
        }
    }

    /**
     * �G�f�B�^�`����ݒ肵�܂��B
     * 
     * @param editorType �G�f�B�^�`��
     */
    public void setEditorType(String editorType) {
        if (getEditorDelegateHash().get(editorType) == null) {
            // ���o�^�̃G�f�B�^�Ȃ�V�K����
            addEditor(editorType, createEditorDelegate(editorType));
        }

        this.editorType = editorType;
    }

    /**
     * �����_���Ƃ��Ďg�p����f���Q�[�g�̒�`�n�b�V����ݒ肵�܂��B
     * 
     * @param rendererDelegateHash �����_���f���Q�[�g�̒�`�n�b�V��
     */
    public void setRendererDelegateHash(HashMap rendererDelegateHash) {
        this.rendererDelegateHash = rendererDelegateHash;
    }

    /**
     * �����_���p�̃��f����ݒ肵�܂��B
     * @param model �����_���p�̃��f��
     */
    public void setRendererModel(Object model){
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            ed.setModel(model);
        }
    }

    /**
     * �����_���`����ݒ肵�܂��B
     * 
     * @param rendererType �����_���`��
     */
    public void setRendererType(String rendererType) {
        if (getRendererDelegateHash().get(rendererType) == null) {
            // ���o�^�̃����_���Ȃ�V�K����
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
     * �e�[�u���Z���̑I����Ԃɉ����ăR���|�[�l���g�𒅐F���܂��B
     * 
     * @param table �e�[�u��
     * @param value �l
     * @param isSelected �I����Ԃ�
     * @param hasFocus �t�H�[�J�X��ێ����Ă��邩
     * @param row �s
     * @param column ��
     * @param cmp �ݒ��R���|�[�l���g
     */
    protected void applyTableCellColor(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column,
            Component cmp) {

        if (cmp != null) {
            // �I��F�̒��F
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
     * �W���̃����_���𐶐����܂��B
     * 
     * @param value the value to assign to the cell at
     *            <code>[row, column]</code>
     * @param isSelected true if cell is selected
     * @param hasFocus true if cell has focus
     * @param row the row of the cell to render
     * @param column the column of the cell to render
     * @return �W���̃����_��
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
     * ������Ԃ̃G�f�B�^�`����Ԃ��܂��B
     * @return ������Ԃ̃G�f�B�^�`��
     */
    protected String getDefaultEditorType(){
        return VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD;
    }
    /**
     * ������Ԃ̃����_���`����Ԃ��܂��B
     * @return ������Ԃ̃����_���`��
     */
    protected String getDefaultRendererType(){
        return VRTableCellViewer.RENDERER_TYPE_LABEL;
    }
    /**
     * �G�f�B�^�p�̃f���Q�[�g��Ԃ��܂��B
     * @return �G�f�B�^�p�̃f���Q�[�g
     */
    protected VRTableCellViewerDelegate getEditorDelegate(){
        return (VRTableCellViewerDelegate) getEditorDelegateHash()
        .get(getEditorType());
    }

    /**
     * �����_���p�̃f���Q�[�g��Ԃ��܂��B
     * @return �����_���p�̃f���Q�[�g
     */
    protected VRTableCellViewerDelegate getRendererDelegate(){
        return (VRTableCellViewerDelegate) getRendererDelegateHash()
        .get(getRendererType());
    }

    /**
     * �e�[�u���Z���p�̃f���Q�[�g��Ԃ��܂��B
     * 
     * @param table �e�[�u��
     * @param value �l
     * @param row �s
     * @param column ��
     * @param delegateHash �f���Q�[�g�n�b�V��
     * @param type �f���Q�[�g�^�C�v
     * @return �f���Q�[�g
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
     * �c���[�Z���p�̃f���Q�[�g��Ԃ��܂��B
     * 
     * @param tree �c���[
     * @param value �l
     * @param row �s
     * @param delegateHash �f���Q�[�g�n�b�V��
     * @param type �f���Q�[�g�^�C�v
     * @return �f���Q�[�g
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
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        setRendererType(getDefaultRendererType());
        setEditorType(getDefaultEditorType());
    }

}