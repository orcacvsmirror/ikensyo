package jp.nichicom.ac.component.table;

import java.awt.Component;
import java.awt.Insets;
import java.util.HashMap;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.JTree;

import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACStateIconLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableCellViewerDelegate;

/**
 * �Z���P�ʂ̕\���ύX��A�Ԃɂ��Ή������Z���r���[���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRTableCellViewer
 */

public class ACTableCellViewer extends VRTableCellViewer {
    /**
     * �n�b�V���A�C�R���`����\�������_���萔�ł��B
     */
    public static final String EDITOR_TYPE_ICON = "Icon";

    /**
     * �A�Ԍ`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_SERIAL_NO = "SerialNo";
    /**
     * ��ԃA�C�R���`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_STATE_ICON = "StateIcon";
    /**
     * �g�O���{�^���`����\���G�f�B�^�萔�ł��B
     */
    public static final String EDITOR_TYPE_TOGGLE_BUTTON = "ToggleButton";
    /**
     * �n�b�V���A�C�R���`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_ICON = "Icon";
    /**
     * �A�Ԍ`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_SERIAL_NO = "SerialNo";
    /**
     * ��ԃA�C�R���`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_STATE_ICON = "StateIcon";
    /**
     * �g�O���{�^���`����\�������_���萔�ł��B
     */
    public static final String RENDERER_TYPE_TOGGLE_BUTTON = "ToggleButton";

    /**
     * �Z���G�f�B�^������킷�擾�萔�ł��B
     */
    protected static final int GET_TYPE_EDITOR = 1;
    /**
     * �Z�������_��������킷�擾�萔�ł��B
     */
    protected static final int GET_TYPE_RENDERER = 0;
    private HashMap customCellEditorCache = new HashMap();

    private HashMap customCellRendererCache = new HashMap();

    private List customCells;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACTableCellViewer() {
    }

    /**
     * �J�X�^���s�����_���E�G�f�B�^�p�̃R���|�[�l���g�L���b�V�����N���A���܂��B
     */
    public void clearCustomCellCache() {
        getCustomCellRendererCache().clear();
    }

    public VRTableCellViewerDelegate createEditorDelegate(final String type) {
        if (VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD.equals(type)) {
            return createEditorDelegate(new ACTextField());
        } else if (VRTableCellViewer.EDITOR_TYPE_LABEL.equals(type)) {
            ACLabel lbl = new ACLabel();
            lbl.setMargin(new Insets(0, 2, 0, 4));
            return createEditorDelegate(lbl);
        } else if (VRTableCellViewer.EDITOR_TYPE_CHECK_BOX.equals(type)) {
            return createEditorDelegate(new ACCheckBox());
        } else if (VRTableCellViewer.EDITOR_TYPE_COMBO_BOX.equals(type)) {
            return createEditorDelegate(new ACComboBox());
        } else if (ACTableCellViewer.EDITOR_TYPE_TOGGLE_BUTTON.equals(type)) {
            return createEditorDelegate(new JToggleButton());
        } else if (ACTableCellViewer.EDITOR_TYPE_SERIAL_NO.equals(type)) {
            return createSerialEditorDelegate();
        } else if (ACTableCellViewer.EDITOR_TYPE_STATE_ICON.equals(type)) {
            return createStateIconEditorDelegate();
        } else if (ACTableCellViewer.EDITOR_TYPE_ICON.equals(type)) {
            return createIconEditorDelegate();
        }
        return super.createEditorDelegate(type);
    }

    public VRTableCellViewerDelegate createRendererDelegate(final String type) {
        if (VRTableCellViewer.RENDERER_TYPE_TEXT_FIELD.equals(type)) {
            return createEditorDelegate(new ACTextField());
        } else if (VRTableCellViewer.RENDERER_TYPE_LABEL.equals(type)) {
            ACLabel lbl = new ACLabel();
            lbl.setMargin(new Insets(0, 2, 0, 2));
            return createEditorDelegate(lbl);
        } else if (VRTableCellViewer.RENDERER_TYPE_CHECK_BOX.equals(type)) {
            return createEditorDelegate(new ACCheckBox());
        } else if (VRTableCellViewer.RENDERER_TYPE_COMBO_BOX.equals(type)) {
            return createEditorDelegate(new ACComboBox());
        } else if (ACTableCellViewer.RENDERER_TYPE_TOGGLE_BUTTON.equals(type)) {
            return createEditorDelegate(new JToggleButton());
        } else if (ACTableCellViewer.RENDERER_TYPE_SERIAL_NO.equals(type)) {
            return createSerialEditorDelegate();
        } else if (ACTableCellViewer.RENDERER_TYPE_STATE_ICON.equals(type)) {
            return createStateIconEditorDelegate();
        } else if (ACTableCellViewer.RENDERER_TYPE_ICON.equals(type)) {
            return createIconEditorDelegate();
        }
        return super.createRendererDelegate(type);
    }

    /**
     * �w��s�̃J�X�^���Z������Ԃ��܂��B
     * <p>
     * �ݒ�ς݂̃J�X�^���Z���������w��s���傫���ꍇ�Anull��Ԃ��܂��B
     * </p>
     * 
     * @param row �s�ԍ�
     * @return �J�X�^���Z�����
     */
    public ACTableCellViewerCustomCell getCustomCell(int row) {
        List cells = getCustomCells();
        if ((cells != null) && (cells.size() > row)) {
            // �J�X�^���s�G�f�B�^��ݒ肵�Ă���ꍇ
            Object obj = cells.get(row);
            if (obj instanceof ACTableCellViewerCustomCell) {
                return (ACTableCellViewerCustomCell) obj;
            }
        }
        return null;
    }

    /**
     * �J�X�^���Z���ݒ�W�� ��Ԃ��܂��B
     * <p>
     * <code>NCTableCellViewerCustomCell</code>��A�˂�List��������null���Ԃ�܂��B
     * </p>
     * 
     * @return �J�X�^���Z���ݒ�
     */
    public List getCustomCells() {
        return customCells;
    }

    /**
     * �G�f�B�^�p�̕`��Ώۂ̃o�C���h�p�X��Ԃ��܂��B
     * <p>
     * �G�f�B�^��<code>ACComboBox</code>���p�����Ă���ꍇ�ɗL���ł��B
     * </p>
     * 
     * @return �`��Ώۂ̃o�C���h�p�X
     */
    public String getEditorRenderBindPath() {
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                return ((ACComboBox) cmp).getRenderBindPath();
            }
        }
        return null;
    }

    /**
     * �����_���p�̕`��Ώۂ̃o�C���h�p�X��Ԃ��܂��B
     * <p>
     * �����_����<code>ACComboBox</code>���p�����Ă���ꍇ�ɗL���ł��B
     * </p>
     * 
     * @return �`��Ώۂ̃o�C���h�p�X
     */
    public String getRendererRenderBindPath() {
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                return ((ACComboBox) cmp).getRenderBindPath();
            }
        }
        return null;
    }

    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
        ACTableCellViewerCustomCell cell = getCustomCell(row);
        if ((cell != null) && (!cell.isEditable())) {
            return null;
        }
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, column,
                cell, ACTableCellViewer.GET_TYPE_EDITOR);
        Component cmp;
        if (ed instanceof VRTableCellViewerDelegate) {
            // �f���Q�[�g������ꂽ�ꍇ�͗D�悵�Ďg�p����B
            delegate = ed;
            cmp = ed.getComponent();
        } else {
            cmp = super.getTableCellEditorComponent(table, value, isSelected,
                    row, column);
        }
        return cmp;
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {
        ACTableCellViewerCustomCell cell = getCustomCell(row);
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, column,
                cell, ACTableCellViewer.GET_TYPE_RENDERER);
        if (ed instanceof VRTableCellViewerDelegate) {
            // �f���Q�[�g������ꂽ�ꍇ�͗D�悵�Ďg�p����B
            Component cmp = ed.getComponent();
            if (!cell.isIgnoreSelectColor()) {
                // ���F�𖳎����Ȃ��Ȃ�ΑI��F�𔽉f
                // Color fore = cmp.getForeground();
                // Color back = cmp.getBackground();
                applyTableCellColor(table, value, isSelected, hasFocus, row,
                        column, cmp);
                // if (fore != null) {
                // cmp.setForeground(fore);
                // }
                // if (back != null) {
                // cmp.setBackground(back);
                // }
            }
            return cmp;
        }
        return super.getTableCellRendererComponent(table, value, isSelected,
                hasFocus, row, column);
    }

    public Component getTreeCellEditorComponent(JTree tree, Object value,
            boolean isSelected, boolean expanded, boolean leaf, int row) {
        ACTableCellViewerCustomCell cell = getCustomCell(row);
        if ((cell != null) && (!cell.isEditable())) {
            return null;
        }
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, 0,
                cell, ACTableCellViewer.GET_TYPE_EDITOR);
        if (ed instanceof VRTableCellViewerDelegate) {
            delegate = ed;
            return ed.getComponent();
        }

        return super.getTreeCellEditorComponent(tree, value, isSelected,
                expanded, leaf, row);
    }

    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {
        VRTableCellViewerDelegate ed = getCustomRowDelegate(value, row, 0,
                getCustomCell(row), ACTableCellViewer.GET_TYPE_RENDERER);
        if (ed instanceof VRTableCellViewerDelegate) {
            return ed.getComponent();
        }

        return super.getTreeCellRendererComponent(tree, value, selected,
                expanded, leaf, row, hasFocus);
    }

    /**
     * �J�X�^���Z���ݒ�W�� ��ݒ肵�܂��B
     * <p>
     * <code>NCTableCellViewerCustomCell</code>��A�˂�List��ݒ肵�܂��B
     * </p>
     * 
     * @param customCells �J�X�^���Z���ݒ�W��
     */
    public void setCustomCells(List customCells) {
        this.customCells = customCells;
        clearCustomCellCache();
    }
    /**
     * �G�f�B�^�p�̕`��Ώۂ̃o�C���h�p�X��ݒ肵�܂��B
     * <p>
     * �G�f�B�^��<code>ACComboBox</code>���p�����Ă���ꍇ�ɗL���ł��B
     * </p>
     * 
     * @param renderBindPath �`��Ώۂ̃o�C���h�p�X
     */
    public void setEditorRenderBindPath(String renderBindPath) {
        VRTableCellViewerDelegate ed = getEditorDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                ((ACComboBox) cmp).setRenderBindPath(renderBindPath);
            }
        }
    }

    /**
     * �����_���p�̕`��Ώۂ̃o�C���h�p�X��ݒ肵�܂��B
     * <p>
     * �����_����<code>ACComboBox</code>���p�����Ă���ꍇ�ɗL���ł��B
     * </p>
     * 
     * @param renderBindPath �`��Ώۂ̃o�C���h�p�X
     */
    public void setRendererRenderBindPath(String renderBindPath) {
        VRTableCellViewerDelegate ed = getRendererDelegate();
        if (ed != null) {
            Component cmp = ed.getComponent();
            if (cmp instanceof ACComboBox) {
                ((ACComboBox) cmp).setRenderBindPath(renderBindPath);
            }
        }
    }

    /**
     * �A�C�R���^�G�f�B�^�𐶐����ĕԂ��܂��B
     * 
     * @return �A�C�R���^�G�f�B�^
     */
    protected VRTableCellViewerDelegate createIconEditorDelegate() {
        final ACLabel component = new ACLabel();
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {
            public Object getValue() {
                return parseValue(component.getIconPath());
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValue(Object value, int row, int column) {
                value = formatValue(value);
                if (value == null) {
                    component.setIconPath(null);
                } else {
                    component.setIconPath(String.valueOf(value));
                }
            }

        };
        return ed;
    }

    /**
     * �V���A���^�G�f�B�^�𐶐����ĕԂ��܂��B
     * 
     * @return �V���A���^�G�f�B�^
     */
    protected VRTableCellViewerDelegate createSerialEditorDelegate() {
        final ACLabel component = new ACLabel();
        // component.setOpaque(true);
        // component.setBackground(UIManager
        // .getColor("TableHeader.background"));
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {
            public Object getValue() {
                return parseValue(component.getText());
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValue(Object value, int row, int column) {
                value = formatValue(value);
                component.setText(String.valueOf(row + 1));
            }

        };
        return ed;
    }

    /**
     * ��ԃA�C�R���^�G�f�B�^�𐶐����ĕԂ��܂��B
     * 
     * @return ��ԃA�C�R���^�G�f�B�^
     */
    protected VRTableCellViewerDelegate createStateIconEditorDelegate() {
        final ACStateIconLabel component = new ACStateIconLabel();
        VRTableCellViewerDelegate ed = new VRTableCellViewerDelegate(this,
                component) {

            public Object getValue() {
                return parseValue(new Integer(component.getState()));
            }

            public void setHorizontalAlignment(int horizontalAlignment) {
                super.setHorizontalAlignment(horizontalAlignment);
                component.setHorizontalAlignment(horizontalAlignment);
            }

            public void setValue(Object value, int row, int column) {
                value = formatValue(value);
                if (value == null) {
                    component.setState(ACStateIconLabel.STATE_NONE);
                } else {
                    component.setState(Integer.parseInt(String.valueOf(value)));
                }
            }

        };
        return ed;
    }

    /**
     * �G�f�B�^�p�J�X�^���Z���L���b�V�� ��Ԃ��܂��B
     * 
     * @return �G�f�B�^�p�J�X�^���Z���L���b�V��
     */
    protected HashMap getCustomCellEditorCache() {
        return customCellEditorCache;
    }

    /**
     * �����_���p�J�X�^���Z���L���b�V�� ��Ԃ��܂��B
     * 
     * @return �����_���p�J�X�^���Z���L���b�V��
     */
    protected HashMap getCustomCellRendererCache() {
        return customCellRendererCache;
    }

    /**
     * �J�X�^���s�����_���E�G�f�B�^�ɂ��f���Q�[�g��`��Ԃ��܂��B
     * <p>
     * �Y�����Ȃ����null��Ԃ��܂��B
     * </p>
     * <p>
     * <code>getType</code>�ɂ́A�ȉ��̒l���w�肵�܂��B<br />
     * NCTableCellViewer.GET_TYPE_RENDERER : �Z�������_��<br />
     * NCTableCellViewer.GET_TYPE_EDITOR : �Z���G�f�B�^<br />
     * </p>
     * 
     * @param value �l
     * @param row �Ώۍs�ԍ�
     * @param column �Ώۗ�ԍ�
     * @param cell �J�X�^���Z���ݒ�
     * @param getType �擾����`��
     * @return �f���Q�[�g
     */
    protected VRTableCellViewerDelegate getCustomRowDelegate(Object value,
            int row, int column, ACTableCellViewerCustomCell cell, int getType) {
        if (cell == null) {
            return null;
        }
        // �擾�`���ɂ���ăL���b�V����ύX
        HashMap cache;
        switch (getType) {
        case ACTableCellViewer.GET_TYPE_RENDERER:
            cache = getCustomCellRendererCache();
            break;
        case ACTableCellViewer.GET_TYPE_EDITOR:
            cache = getCustomCellEditorCache();
            break;
        default:
            return null;
        }

        // �J�X�^���s�G�f�B�^��ݒ肵�Ă���ꍇ
        Object cached = cache.get(cell);
        VRTableCellViewerDelegate ed = null;
        if (cached instanceof VRTableCellViewerDelegate) {
            // �L���b�V������Ă���ꍇ�͂�������g��
            ed = (VRTableCellViewerDelegate) cached;
        } else {
            Component obj;
            switch (getType) {
            case ACTableCellViewer.GET_TYPE_RENDERER:
                obj = cell.getRenderer();
                break;
            case ACTableCellViewer.GET_TYPE_EDITOR:
                obj = cell.getEditor();
                break;
            default:
                return null;
            }

            // �N���X�ʂɃf���Q�[�g�𐶐�
            if (obj instanceof JTextField) {
                ed = createEditorDelegate((JTextField) obj);
            } else if (obj instanceof JLabel) {
                ed = createEditorDelegate((JLabel) obj);
            } else if (obj instanceof JCheckBox) {
                ed = createEditorDelegate((JCheckBox) obj);
            } else if (obj instanceof JComboBox) {
                ed = createEditorDelegate((JComboBox) obj);
            } else {
                return null;
            }
        }
        if (ed instanceof VRTableCellViewerDelegate) {
            // �f���Q�[�g������ꂽ�ꍇ�̓L���b�V���ɃZ�b�g���g�p����B
            cache.put(cell, ed);
            ed.setValue(value, row, column);
            return ed;
        }

        return null;
    }

    /**
     * �G�f�B�^�p�J�X�^���Z���L���b�V�� ��ݒ肵�܂��B
     * 
     * @param customCellEditorCache �G�f�B�^�p�J�X�^���Z���L���b�V��
     */
    protected void setCustomCellEditorCache(HashMap customCellEditorCache) {
        this.customCellEditorCache = customCellEditorCache;
    }

    /**
     * �����_���p�J�X�^���Z���L���b�V�� ��ݒ肵�܂��B
     * 
     * @param customCellRendererCache �����_���p�J�X�^���Z���L���b�V��
     */
    protected void setCustomCellRendererCache(HashMap customCellRendererCache) {
        this.customCellRendererCache = customCellRendererCache;
    }
}
