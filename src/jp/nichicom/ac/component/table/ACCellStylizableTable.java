package jp.nichicom.ac.component.table;

import java.awt.Component;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.Vector;

import javax.swing.JComponent;
import javax.swing.ListSelectionModel;
import javax.swing.event.ChangeEvent;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import jp.nichicom.ac.component.style.ACComponentStylizer;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.component.table.VRTable;
import jp.nichicom.vr.util.adapter.VRBindSourceAdapter;

/**
 * �X�^�C���C�U����эs�P�ʂŃf�[�^�������_���ɓn���@�\�ɑΉ������e�[�u���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRTable
 */
public class ACCellStylizableTable extends VRTable {

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �f�t�H���g�f�[�^���f���A�f�t�H���g�񃂃f���A����уf�t�H���g�I�����f���ŏ����������A�f�t�H���g�� Table ���\�z���܂��B
     * </p>
     */
    public ACCellStylizableTable() {
        super();
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
    public ACCellStylizableTable(int numRows, int numColumns) {
        super(numRows, numColumns);
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
    public ACCellStylizableTable(Object[][] rowData, Object[] columnNames) {
        super(rowData, columnNames);
    }

    /**
     * �R���X�g���N�^�ł��B
     * <p>
     * �f�[�^���f�� dm�A�f�t�H���g�񃂃f���A����уf�t�H���g�I�����f���ŏ���������� JTable ���\�z���܂��B
     * </p>
     * 
     * @param dm �e�[�u���̃f�[�^���f��
     */
    public ACCellStylizableTable(TableModel dm) {
        super(dm);
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
    public ACCellStylizableTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
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
    public ACCellStylizableTable(TableModel dm, TableColumnModel cm,
            ListSelectionModel sm) {
        super(dm, cm, sm);
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
    public ACCellStylizableTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
    }

    /**
     * �w��ԍ��̃J������Ԃ��܂��B
     * 
     * @param columnIndex �J�����ԍ�
     * @return �J����
     */
    public TableColumn getColumn(int columnIndex) {
        return getColumnModel().getColumn(columnIndex);
    }

    /**
     * ���f���̍s�f�[�^��Ԃ��܂��B
     * 
     * @param row �s�ԍ�
     * @return �s�f�[�^
     * @throws Exception �擾�Ɏ��s�����ꍇ
     */
    protected Object getModelRowValue(int row) throws Exception {
        TableModel mdl = getModel();
        if (mdl instanceof VRSortableTableModelar) {
            return ((VRSortableTableModelar) mdl).getValueAt(row);
        } else {
            VRBindSource source = null;
            if (mdl instanceof VRBindSource) {
                // �\�[�X�Ȃ璼�ڎ擾
                source = (VRBindSource) mdl;
            } else if (mdl instanceof VRBindSourceAdapter) {
                // �A�_�v�^�Ȃ�A�_�v�e�B�[����擾
                source = ((VRBindSourceAdapter) mdl).getAdaptee();
            }
            return ((VRBindSource) source).getData(row);
        }
    }

    public Component prepareRenderer(TableCellRenderer renderer, int row,
            int column) {

        ACComponentStylizer style = null;
        boolean valSet = false;
        Object value = null;
        TableColumn clm = getColumn(column);
        if (clm instanceof ACTableColumn) {
            if (((ACTableColumn) clm).isCellRendererStylizeOnRowValue()) {
                // �s�f�[�^��n���ꍇ
                try {
                    value = getModelRowValue(row);
                    valSet = true;
                } catch (Exception ex) {
                    // ���s�����ꍇ��valSet = false�̂܂�
                }
            }
            // �X�^�C���w���ޔ�
            style = ((ACTableColumn) clm).getCellRendererStylizer();
        }
        if (!valSet) {
            // �f�[�^���m�肵�Ă��Ȃ���ΕW���̃f�[�^�擾���@�ɏ]��
            value = getValueAt(row, column);
        }

        // �W���̃����_������
        boolean isSelected = isCellSelected(row, column);
        boolean rowIsAnchor = (selectionModel.getAnchorSelectionIndex() == row);
        boolean colIsAnchor = (columnModel.getSelectionModel()
                .getAnchorSelectionIndex() == column);
        boolean hasFocus = (rowIsAnchor && colIsAnchor) && isFocusOwner();

        Component comp = renderer.getTableCellRendererComponent(this, value,
                isSelected, hasFocus, row, column);

        if (style != null) {
            // �X�^�C����K�p����ꍇ�͎��s
            comp = style.stylize(comp, value);
        }
        return comp;
    }

    public Component prepareEditor(TableCellEditor editor, int row, int column) {

        ACComponentStylizer style = null;
        boolean valSet = false;
        Object value = null;
        TableColumn clm = getColumn(column);
        if (clm instanceof ACTableColumn) {
            if (((ACTableColumn) clm).isCellEditorStylizeOnRowValue()) {
                // �s�f�[�^��n���ꍇ
                try {
                    value = getModelRowValue(row);
                    valSet = true;
                } catch (Exception ex) {
                    // ���s�����ꍇ��valSet = false�̂܂�
                }
            }
            // �X�^�C���w���ޔ�
            style = ((ACTableColumn) clm).getCellEditorStylizer();
        }
        if (!valSet) {
            // �f�[�^���m�肵�Ă��Ȃ���ΕW���̃f�[�^�擾���@�ɏ]��
            value = getValueAt(row, column);
        }

        // �W���̃G�f�B�^����
        boolean isSelected = isCellSelected(row, column);
        Component comp = editor.getTableCellEditorComponent(this, value,
                isSelected, row, column);
        if (comp instanceof JComponent) {
            JComponent jComp = (JComponent) comp;
            if (jComp.getNextFocusableComponent() == null) {
                jComp.setNextFocusableComponent(this);
            }
        }

        if (style != null) {
            // �X�^�C����K�p����ꍇ�͎��s
            comp = style.stylize(comp, value);
        }
        
        editorComponent = comp;
        if(comp != null) {
            comp.addFocusListener(cellFocusListener);
        }
        
        return comp;

    }
    private Component editorComponent;
    public void removeEditor() {
        if(editorComponent!=null) {
            editorComponent.removeFocusListener(cellFocusListener);
            editorComponent = null;
        }
        super.removeEditor();
    }
    protected FocusListener cellFocusListener = new FocusAdapter(){
        public void focusLost(FocusEvent e) {
            Component c=e.getOppositeComponent();
            if(c!=null){
                if(c==ACCellStylizableTable.this){
                    return;
                }
                //�e�[�u���ȊO�Ƀt�H�[�J�X���ڂ����ꍇ�A�ҏW��Ԃ���������
                editingStopped(new ChangeEvent(ACCellStylizableTable.this));
            }
        }
        
    };

//    protected TableCellRenderer createHeaderRenderer(){
//        return new ACTableHeaderViewer();
//    }    
    
    protected JTableHeader createDefaultTableHeader() {
        return new ACTableHeader(columnModel);
    }
    
    /**
     * �w�b�_�Ƃ��Ċm�ۂ��鍂���𕶎��� �ŕԂ��܂��B
     * @return �w�b�_�Ƃ��Ċm�ۂ��鍂���𕶎���
     */
    public int getHeaderRows(){
        if(getTableHeader() instanceof ACTableHeader){
            return ((ACTableHeader)getTableHeader()).getRows();
        }
        return 0; 
    }
    /**
     * �w�b�_�Ƃ��Ċm�ۂ��鍂���𕶎��� ��ݒ肵�܂��B
     * @param rows �w�b�_�Ƃ��Ċm�ۂ��鍂���𕶎���
     */
    public void setHeaderRows(int rows){
        if(getTableHeader() instanceof ACTableHeader){
            ((ACTableHeader)getTableHeader()).setRows(rows);
//            if(getParent()!=null){
//                getParent().invalidate();
//                getParent().repaint();
//            }
//            getTableHeader().updateUI();
//            getTableHeader().invalidate();
//            getTableHeader().repaint();
        }
    }
//    public void removeNotify(){
//        if((getModel()!=null)&&(getModel().getRowCount()>0)){
//            setModel(new DefaultTableModel());
//        }
//        super.removeNotify();
//    }

	public TableCellEditor getDefaultEditor1(Class columnClass) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		return null;
	}

	public void setDefaultEditor1(Class columnClass, TableCellEditor editor) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}

	public void setDefaultRenderer1(Class columnClass,
			TableCellRenderer renderer) {
		// TODO �����������ꂽ���\�b�h�E�X�^�u
		
	}
}
