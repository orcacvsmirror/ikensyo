/** TODO <HEAD> */
package jp.nichicom.vr.component.table;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableModelEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.adapter.VRTableModelAdapter;

/**
 * �J�����w�b�_�N���b�N�ɂ��\�[�g�@�\�ɑΉ������e�[�u���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Yosuke Takemoto
 * @version 1.0 2005/12/01
 * @see JTable
 * @see VRTableCellViewer
 * @see VRSortableTableModel
 * @see VRTableModelar
 * @see VRTableModelAdapter
 * @see VRTableHeaderRenderer
 */
public class VRTable extends JTable implements VRTablar {
    /**
     * <code>�X�g���C�v�J���[�̏����ݒ�F</code>
     */
    public static final Color DEFAULT_STRIPE_COLOR = Color.decode("#FFFFCC");

    private boolean columnSort = true;
    private boolean extendLastColumn;
    private Point pressPoint;
    private HashMap sortSequence;
    private boolean stripe;
    private Color stripeColor;

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �f�t�H���g�f�[�^���f���A�f�t�H���g�񃂃f���A����уf�t�H���g�I�����f���ŏ����������A�f�t�H���g�� Table ���\�z���܂��B
     * </p>
     */
    public VRTable() {
        super();
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * DefaultTableModel ���g���āA��̃Z���� numRows �� numColumns �� Table
     * ���\�z���܂��B��́A�uA�v�A�uB�v�A�uC�v�Ƃ������`���̖��O�������܂��B
     * </p>
     * 
     * @param numRows �e�[�u�����ێ�����s��
     * @param numColumns �e�[�u�����ێ������
     */
    public VRTable(int numRows, int numColumns) {
        super(numRows, numColumns);
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * 2 �����z�� rowData �̒l��\������ JTable ���A�� columnNames �ō\�z���܂��B
     * </p>
     * 
     * @param rowData �V�����e�[�u���̃f�[�^
     * @param columnNames �e��̖��O
     */
    public VRTable(final Object[][] rowData, final Object[] columnNames) {
        super(rowData, columnNames);
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �f�[�^���f�� dm�A�f�t�H���g�񃂃f���A����уf�t�H���g�I�����f���ŏ���������� JTable ���\�z���܂��B
     * </p>
     * 
     * @param dm �e�[�u���̃f�[�^���f��
     */
    public VRTable(TableModel dm) {
        super(dm);
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �f�[�^���f�� dm�A�񃂃f�� cm�A����уf�t�H���g�I�����f���ŏ���������� JTable ���\�z���܂��B
     * </p>
     * 
     * @param dm �e�[�u���̃f�[�^���f��
     * @param cm �e�[�u���̗񃂃f��
     */
    public VRTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �f�[�^���f�� dm�A�񃂃f�� cm�A����ёI�����f�� sm �ŏ���������� JTable ���\�z���܂��B
     * </p>
     * 
     * @param dm �e�[�u���̃f�[�^���f��
     * @param cm �e�[�u���̗񃂃f��
     * @param sm �e�[�u���̍s�I�����f��
     */
    public VRTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        initComponent();
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * Vectors �� Vector �̒l��\������ JTable�A�܂� rowData ���A�� columnNames �ō\�z���܂��B
     * </p>
     * 
     * @param rowData �V�����e�[�u���̃f�[�^
     * @param columnNames �e��̖��O
     */
    public VRTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
        initComponent();
    }

    /**
     * �\�[�g��Ԃ��N���A���܂��B
     */
    public void clearSortSequence() {
        setSortSequence(new HashMap());
        repaint();
    }

    public void columnMarginChanged(ChangeEvent e) {
        super.columnMarginChanged(e);
        doExtendLastColumn();
    }

    public void columnMoved(TableColumnModelEvent e) {
        super.columnMoved(e);
        doExtendLastColumn();
    }

    public void componentHidden(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
        doExtendLastColumn();
    }

    public void componentResized(ComponentEvent e) {
        doExtendLastColumn();
    }

    public void componentShown(ComponentEvent e) {
    }

    /**
     * �E�[�������ߋ@�\�����s���܂��B
     */
    public void doExtendLastColumn() {
        if (isExtendLastColumn()) {
            if (getParent() != null && getParent() instanceof JViewport) {
                int viewWidth = ((JViewport) getParent()).getExtentSize().width;
                int lastWidth = viewWidth;
                if (getColumnCount() > 0) {
                    for (int i = 0; i < getColumnCount() - 1; i++) {
                        lastWidth -= getColumnModel().getColumn(i).getWidth();
                    }
                    if (lastWidth > 0) {
                        TableColumn column = getColumnModel().getColumn(
                                getColumnCount() - 1);
                        if (lastWidth < column.getMinWidth()) {
                            lastWidth = column.getMinWidth();
                        }
                        column.setWidth(lastWidth);
                        column.setPreferredWidth(lastWidth);
                        resizeAndRepaint();
                    }
                }
            }
        }
    }

    /**
     * �\�[�g��ԃ}�b�v��Ԃ��܂��B
     * 
     * @return �\�[�g��ԃ}�b�v
     */
    public HashMap getSortSequence() {
        return sortSequence;
    }

    /**
     * �X�g���C�v�J���[��Ԃ��܂��B
     * 
     * @return �X�g���C�v�J���[
     */
    public Color getStripeColor() {
        return stripeColor;
    }

    /**
     * �J�����̃N���b�N�ɂ��\�[�g�������邩 ��Ԃ��܂��B
     * 
     * @return �J�����̃N���b�N�ɂ��\�[�g�������邩
     */
    public boolean isColumnSort() {
        return columnSort;
    }

    /**
     * �E�[���������@�\�̎g�p/���g�p��Ԃ��܂��B
     * 
     * @return �E�[���������@�\���g�p����ꍇ��true�A���Ȃ��ꍇ��false
     */
    public boolean isExtendLastColumn() {
        return extendLastColumn;
    }

    /**
     * �\�[�g��ԃ}�b�v��ݒ肵�܂��B
     * 
     * @param sortSequence �\�[�g��ԃ}�b�v
     */
    public void setSortSequence(HashMap sortSequence) {
        this.sortSequence = sortSequence;
    }

    /**
     * �X�g���C�v�@�\�̎g�p/���g�p��Ԃ��܂��B
     * 
     * @return �X�g���C�v�@�\���g�p����ꍇ��true�A���Ȃ��ꍇ��false
     */
    public boolean isStripe() {
        return stripe;
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
        pressPoint = e.getPoint();
    }

    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof JTableHeader) {
            if (getTableHeader().getCursor().getType() != Cursor.DEFAULT_CURSOR) {
                // �J�[�\�����W���łȂ���Ή������Ȃ�
                return;
            }
            if (!isColumnSort()) {
                // �J�����\�[�g�֎~�Ȃ�Ή������Ȃ�
                return;
            }
            Point releasePoint = e.getPoint();
            if (Math.abs(pressPoint.getX() - releasePoint.getX()) < 3) {
                int columnIndex = columnAtPoint(releasePoint);
                if (columnIndex > -1) {
                    TableColumn column = getColumnModel()
                            .getColumn(columnIndex);
                    if (column instanceof VRTableColumnar) {
                        ((VRTableColumnar) column).columnClick(e, columnIndex);
                    }
                }
            }
        }
    }

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param column �J�����ԍ�
     */
    public void sort(int column) {
        String columnName = getColumnName(column);
        if (columnName != null) {
            HashMap seq = getSortSequence();
            if (seq.get(columnName) != null) {
                String sequenceValue = String.valueOf(seq.get(columnName));
                if (VRTableSortable.ORDER_ASC_VALUE.equals(sequenceValue)) {
                    sort(columnName + " " + VRTableSortable.ORDER_DESC_VALUE);
                    seq.put(columnName, VRTableSortable.ORDER_DESC_VALUE);
                } else {
                    sort(columnName + " " + VRTableSortable.ORDER_ASC_VALUE);
                    seq.put(columnName, VRTableSortable.ORDER_ASC_VALUE);
                }
            } else {
                // �����I�ɏ����\�[�g
                sort(columnName + " " + VRTableSortable.ORDER_ASC_VALUE);
                seq.put(columnName, VRTableSortable.ORDER_ASC_VALUE);
            }
        }
    }

    /**
     * ���f����̑I���s�f�[�^��Ԃ��܂��B
     * 
     * @return �I���s�f�[�^
     * @throws Exception ������O
     */
    public Object getSelectedModelRowValue() throws Exception {
        TableModel model = getModel();
        if (model instanceof VRSortableTableModel) {
            return ((VRSortableTableModel) model)
                    .getValueAt(getSelectedModelRow());
        }
        if (model == null) {
            return null;
        }
        throw new IllegalAccessException("���Ή��̍s�f�[�^���f���ł��B");
    }

    /**
     * �\�[�g���ʂ��l��������ʏ�̑I���ԍ���Ԃ��܂��B
     * 
     * @return ��ʏ�̑I�𒆂̗�ԍ�
     */
    public int getSelectedSortedRow() {
        return getSelectedRow();
    }

    /**
     * �I�𒆂̃f�[�^��ԍ���Ԃ��܂��B
     * 
     * @return �I�𒆂̃f�[�^��ԍ�
     */
    public int getSelectedModelRow() {
        int row = getSelectedRow();
        if ((row < 0) || (row >= getRowCount())) {
            return -1;
        }
        TableModel model = getModel();
        if (model instanceof VRSortableTableModelar) {
            return ((VRSortableTableModelar) model).getTranslateIndex(row);
        }
        return row;
    }

    /**
     * �Ō�Ƀ\�[�g�����Ƃ��Ɠ������Ń\�[�g�������s���܂��B
     */
    public void resort() {
        if (getModel() instanceof VRSortableTableModelar) {
            VRSortableTableModelar model = (VRSortableTableModelar) getModel();
            model.resort();
        }
    }

    /**
     * �J�����̃N���b�N�ɂ��\�[�g�������邩��ݒ肵�܂��B
     * 
     * @param columnSort �J�����̃N���b�N�ɂ��\�[�g�������邩
     */
    public void setColumnSort(boolean columnSort) {
        this.columnSort = columnSort;
    }

    /**
     * �E�[���������@�\�̎g�p/���g�p��ݒ肵�܂��B
     * 
     * @param extendLastColumn �E�[���������@�\���g�p����ꍇ��true�A���Ȃ��ꍇ��false
     */
    public void setExtendLastColumn(boolean extendLastColumn) {
        this.extendLastColumn = extendLastColumn;
    }

    public void setModel(TableModel dataModel) {
        if (getModel() != null) {
            getModel().removeTableModelListener(this);
        }
        if (dataModel instanceof VRTableModelar) {
            ((VRTableModelar) dataModel).setTable(this);
        }
        if (!(dataModel instanceof VRSortableTableModelar)) {
            // �\�[�g�\�ȃ��f���łȂ���΃��b�v����
            dataModel = createSortableModel(dataModel);
            ((VRSortableTableModelar) dataModel).setTable(this);
        }
        super.setModel(dataModel);
        if (getModel() != null) {
            getModel().addTableModelListener(this);
        }
        //�\�[�g��Ԃ�����
        clearSortSequence();
    }

    public void setColumnModel(TableColumnModel columnModel) {
        super.setColumnModel(columnModel);
        if (columnModel instanceof VRTableColumnModelar) {
            ((VRTableColumnModelar) columnModel).setTable(this);
        }
    }

    /**
     * �\�[�g�\�ȃe�[�u�����f���Ƀ��b�v���ĕԂ��܂��B
     * 
     * @param model ���b�s���O�����e�[�u�����f��
     * @return �\�[�g�\�ȃe�[�u�����f��
     */
    protected VRSortableTableModelar createSortableModel(TableModel model) {
        return new VRSortableTableModel(model);
    }

    /**
     * �X�g���C�v�@�\�̎g�p/���g�p��ݒ肵�܂��B
     * 
     * @param stripe �X�g���C�v�@�\���g�p����ꍇ��true�A���Ȃ��ꍇ��false
     */
    public void setStripe(boolean stripe) {
        this.stripe = stripe;
        resizeAndRepaint();
    }

    /**
     * �X�g���C�v�J���[��ݒ肵�܂��B
     * 
     * @param stripeColor �X�g���C�v�J���[
     */
    public void setStripeColor(Color stripeColor) {
        this.stripeColor = stripeColor;
    }

    /**
     * �\�[�g�������s���܂��B
     * 
     * @param condition �\�[�g������
     */
    public void sort(String condition) {
        if (getModel() instanceof VRSortableTableModelar) {
            VRSortableTableModelar model = (VRSortableTableModelar) getModel();
            getSortSequence().clear();
            model.sort(condition);
        }
    }

    public void tableChanged(TableModelEvent e) {
        super.tableChanged(e);
        revalidate();
    }

    /**
     * �R���X�g���N�^���s��ɕK���Ă΂�鏉���������ł��B
     */
    protected void initComponent() {
        addComponentListener(this);
        // �ϐ�������
        sortSequence = new HashMap();
        // �����ݒ�
        VRTableCellViewer viewer = new VRTableCellViewer();
        setDefaultRenderer(Object.class, viewer);
        setDefaultEditor(Object.class, viewer);
        // getTableHeader().getDefaultRenderer();
        getTableHeader().setDefaultRenderer(createHeaderRenderer());
        setStripe(false);
        setStripeColor(DEFAULT_STRIPE_COLOR);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setExtendLastColumn(false);
        // �C�x���g�ݒ�
        getTableHeader().addMouseListener(this);
        // �[���t�H�[�J�X�g���o�[�T��
        addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_TAB) {
                    if ((e.getModifiers() & KeyEvent.SHIFT_MASK) != 0) {
                        transferFocusBackward();
                    } else {
                        transferFocus();
                    }
                }
            }
        });
        addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent e) {
                repaint();
            }

            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }
    /**
     * �e�[�u���w�b�_�Ƃ��Ďg�p���郌���_���𐶐����܂��B
     * @return �e�[�u���w�b�_�Ƃ��Ďg�p���郌���_��
     */
    protected TableCellRenderer createHeaderRenderer(){
        return new VRTableHeaderRenderer();
    }
}