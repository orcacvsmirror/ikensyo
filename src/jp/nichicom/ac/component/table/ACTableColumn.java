package jp.nichicom.ac.component.table;

import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.Format;
import java.util.List;

import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableModel;

import jp.nichicom.ac.component.style.ACComponentStylizer;
import jp.nichicom.ac.text.ACNullToBlankFormat;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableColumn;

/**
 * �e�[�u���p�̃J�����ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRTableColumn
 * @see ACTableCellViewer
 * @see ACComponentStylizer
 */
public class ACTableColumn extends VRTableColumn implements
        PropertyChangeListener {
    private ACComponentStylizer cellEditorStylizer;
    private ACComponentStylizer cellRendererStylizer;
    private String columnName;
    private int columnNameModelIndex = -1;
    private int columns;
    private int columnWidth;
    private List customCells;
    private JPopupMenu headerPopupMenu;
    private boolean cellEditorStylizeOnRowValue;
    private boolean cellRendererStylizeOnRowValue;

    /**
     * �Z���G�f�B�^�p�̃X�^�C���C�U�ɂ͍s�f�[�^��n���� ��Ԃ��܂��B
     * @return �Z���G�f�B�^�p�̃X�^�C���C�U�ɂ͍s�f�[�^��n����
     */
    public boolean isCellEditorStylizeOnRowValue() {
        return cellEditorStylizeOnRowValue;
    }

    /**
     * �Z���G�f�B�^�p�̃X�^�C���C�U�ɂ͍s�f�[�^��n���� ��ݒ肵�܂��B
     * @param cellEditorStylizeOnRowValue �Z���G�f�B�^�p�̃X�^�C���C�U�ɂ͍s�f�[�^��n����
     */
    public void setCellEditorStylizeOnRowValue(boolean cellEditorStylizeOnRowValue) {
        this.cellEditorStylizeOnRowValue = cellEditorStylizeOnRowValue;
    }

    /**
     * �Z�������_���p�̃X�^�C���C�U�ɂ͍s�f�[�^��n���� ��Ԃ��܂��B
     * @return �Z�������_���p�̃X�^�C���C�U�ɂ͍s�f�[�^��n����
     */
    public boolean isCellRendererStylizeOnRowValue() {
        return cellRendererStylizeOnRowValue;
    }

    /**
     * �Z�������_���p�̃X�^�C���C�U�ɂ͍s�f�[�^��n���� ��ݒ肵�܂��B
     * @param cellRendererStylizeOnRowValue �Z�������_���p�̃X�^�C���C�U�ɂ͍s�f�[�^��n����
     */
    public void setCellRendererStylizeOnRowValue(
            boolean cellRendererStylizeOnRowValue) {
        this.cellRendererStylizeOnRowValue = cellRendererStylizeOnRowValue;
    }

    /**
     * Cover method, using a default model index of 0, default width of 75, a
     * <code>null</code> renderer and a <code>null</code> editor. This
     * method is intended for serialization.
     */
    public ACTableColumn() {
        super();
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * Cover method, using a default width of 75, a <code>null</code> renderer
     * and a <code>null</code> editor.
     */
    public ACTableColumn(int modelIndex) {
        super(modelIndex);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * Cover method, using a <code>null</code> renderer and a
     * <code>null</code> editor.
     */
    public ACTableColumn(int modelIndex, int width) {
        super(modelIndex, width);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     */
    public ACTableColumn(int modelIndex, int width, String header) {
        super(modelIndex, width, header);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     * @param format �t�H�[�}�b�g
     */
    public ACTableColumn(int modelIndex, int width, String header, Format format) {
        super(modelIndex, width, header, format);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     * @param format �t�H�[�}�b�g
     */
    public ACTableColumn(int modelIndex, int width, String header,
            Format format, TableCellRenderer cellRenderer,
            TableCellEditor cellEditor) {
        super(modelIndex, width, header, format);
        setCellRenderer(cellRenderer);
        setCellEditor(cellEditor);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     * @param horizontalAlignment ���������̕�������
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment) {
        super(modelIndex, width, header, horizontalAlignment);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     * @param horizontalAlignment ���������̕�������
     * @param format �t�H�[�}�b�g
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, Format format) {
        super(modelIndex, width, header, horizontalAlignment, format);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     * @param horizontalAlignment ���������̕�������
     * @param format �t�H�[�}�b�g
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, Format format,
            TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, header, horizontalAlignment, format);
        setCellRenderer(cellRenderer);
        setCellEditor(cellEditor);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     * @param horizontalAlignment ���������̕�������
     */
    public ACTableColumn(int modelIndex, int width, String header,
            int horizontalAlignment, TableCellRenderer cellRenderer,
            TableCellEditor cellEditor) {
        super(modelIndex, width, header, horizontalAlignment);
        setCellRenderer(cellRenderer);
        setCellEditor(cellEditor);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param header �w�b�_
     */
    public ACTableColumn(int modelIndex, int width, String header,
            TableCellRenderer renderer, TableCellEditor editor) {
        super(modelIndex, width, header);
        setCellRenderer(renderer);
        setCellEditor(editor);
        this.setEditable(true);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param width ��
     * @param cellRenderer �Z�������_��
     * @param cellEditor �Z���G�f�B�^
     */
    public ACTableColumn(int modelIndex, int width,
            TableCellRenderer cellRenderer, TableCellEditor cellEditor) {
        super(modelIndex, width, cellRenderer, cellEditor);
        setFormat(ACNullToBlankFormat.getInstance());
        this.setEditable(true);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param modelIndex ���f���ԍ�
     * @param header �w�b�_
     */
    public ACTableColumn(int modelIndex, String header) {
        super(modelIndex, header);
        setFormat(ACNullToBlankFormat.getInstance());
    }

    public void columnClick(MouseEvent e, int column) {
        JPopupMenu menu = getHeaderPopupMenu();
        if (menu instanceof JPopupMenu) {
            // �|�b�v�A�b�v���j���[������Ε\������
            menu.show((Component) e.getSource(), e.getX(), e.getY());
        } else {
            super.columnClick(e, column);
        }
    }

    /**
     * �Z���G�f�B�^�p�̃X�^�C���C�U ��Ԃ��܂��B
     * <p>
     * null�̏ꍇ�A�X�^�C���C�U���g�p���܂���B
     * </p>
     * 
     * @return �Z���G�f�B�^�p�̃X�^�C���C�U
     */
    public ACComponentStylizer getCellEditorStylizer() {
        return cellEditorStylizer;
    }

    /**
     * �Z�������_���p�̃X�^�C���C�U ��Ԃ��܂��B
     * <p>
     * null�̏ꍇ�A�X�^�C���C�U���g�p���܂���B
     * </p>
     * 
     * @return �Z�������_���p�̃X�^�C���C�U
     */
    public ACComponentStylizer getCellRendererStylizer() {
        return cellRendererStylizer;
    }

    /**
     * �Q�Ƃ���e�[�u�����f���ɂ�����J������ ��Ԃ��܂��B
     * 
     * @return �Q�Ƃ���e�[�u�����f���ɂ�����J������
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * �\������ ��Ԃ��܂��B
     * 
     * @return �\������
     */
    public int getColumns() {
        return columns;
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
     * �w�b�_���N���b�N�����Ƃ��ɕ\������|�b�v�A�b�v���j���[ ��Ԃ��܂��B
     * <p>
     * null�̏ꍇ�A�|�b�v�A�b�v���j���[�ł͂Ȃ��f�t�H���g�����i�\�[�g�j�ƂȂ�܂��B
     * </p>
     * 
     * @return �w�b�_���N���b�N�����Ƃ��ɕ\������|�b�v�A�b�v���j���[
     */
    public JPopupMenu getHeaderPopupMenu() {
        return headerPopupMenu;
    }

    public int getModelIndex() {
        int colNameIndex = getColumnNameModelIndex();
        if (colNameIndex >= 0) {
            // �J����������ModelIndex�����܂��Ă���ꍇ
            return colNameIndex;
        }
        return super.getModelIndex();
    }

    /**
     * �J������\�����邩��Ԃ��܂��B
     * 
     * @return boolean �J������\�����邩
     */
    public boolean isVisible() {
        return super.isVisible() && (getWidth() > 0);
    }

    public void propertyChange(PropertyChangeEvent evt) {
        if ("model".equals(evt.getPropertyName())) {
            // ���f�����ύX���ꂽ�ꍇ
            checkColumnName();
        }
    }

    public void setCellEditor(TableCellEditor cellEditor) {
        if (cellEditor instanceof ACTableCellViewer) {
            // �J�X�^���G�f�B�^��ݒ�
            ((ACTableCellViewer) cellEditor).setCustomCells(getCustomCells());
        }
        super.setCellEditor(cellEditor);
    }

    /**
     * �Z���G�f�B�^�p�̃X�^�C���C�U ��ݒ肵�܂��B
     * <p>
     * null�̏ꍇ�A�X�^�C���C�U���g�p���܂���B
     * </p>
     * 
     * @param cellEditorStylizer �Z���G�f�B�^�p�̃X�^�C���C�U
     */
    public void setCellEditorStylizer(ACComponentStylizer cellEditorStylizer) {
        this.cellEditorStylizer = cellEditorStylizer;
    }

    public void setCellRenderer(TableCellRenderer cellRenderer) {
        if (cellRenderer instanceof ACTableCellViewer) {
            // �J�X�^�������_����ݒ�
            ((ACTableCellViewer) cellRenderer).setCustomCells(getCustomCells());
        }
        super.setCellRenderer(cellRenderer);
    }

    /**
     * �Z�������_���p�̃X�^�C���C�U ��ݒ肵�܂��B
     * <p>
     * null�̏ꍇ�A�X�^�C���C�U���g�p���܂���B
     * </p>
     * 
     * @param cellRendererStylizer �Z�������_���p�̃X�^�C���C�U
     */
    public void setCellRendererStylizer(ACComponentStylizer cellRendererStylizer) {
        this.cellRendererStylizer = cellRendererStylizer;
    }

    /**
     * �Q�Ƃ���e�[�u�����f���ɂ�����J������ ��ݒ肵�܂��B
     * 
     * @param columnName �Q�Ƃ���e�[�u�����f���ɂ�����J������
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
        checkColumnName();
    }

    /**
     * �\������ ��ݒ肵�܂��B
     * 
     * @param columns �\������
     */
    public void setColumns(int columns) {
        this.columns = columns;
        if (getTable() != null) {
            // �J�����v�Z�L���b�V�����N���A
            columnWidth = 0;
            // �\���������ݒ肳��Ă���΁A�����Đݒ肷��
            setPreferredWidth(calcurateColumnsWidth());
        }
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
        if (getCellRenderer() instanceof ACTableCellViewer) {
            // �J�X�^�������_����ݒ�
            ((ACTableCellViewer) getCellRenderer())
                    .setCustomCells(getCustomCells());
        }
    }

    /**
     * �w�b�_���N���b�N�����Ƃ��ɕ\������|�b�v�A�b�v���j���[ ��ݒ肵�܂��B
     * <p>
     * null�̏ꍇ�A�|�b�v�A�b�v���j���[�ł͂Ȃ��f�t�H���g�����i�\�[�g�j�ƂȂ�܂��B
     * </p>
     * 
     * @param headerPopupMenu �w�b�_���N���b�N�����Ƃ��ɕ\������|�b�v�A�b�v���j���[
     */
    public void setHeaderPopupMenu(JPopupMenu headerPopupMenu) {
        this.headerPopupMenu = headerPopupMenu;
    }

    public void setTable(JTable table) {
        if (getTable() != null) {
            getTable().removePropertyChangeListener(this);
        }
        super.setTable(table);

        if (table != null) {
            int columns = getColumns();
            if (columns > 0) {
                // �J�����v�Z�L���b�V�����N���A
                columnWidth = 0;
                // �\���������ݒ肳��Ă���΁A�����Đݒ肷��
                setPreferredWidth(calcurateColumnsWidth());
            }
            // ���f���̕ύX���m�p�Ƀ��X�i�Ƃ��ēo�^
            table.addPropertyChangeListener(this);
        }
    }

    /**
     * �\�������ɉ������J��������Ԃ��܂��B
     * 
     * @return �\�������ɉ������J������
     */
    protected int calcurateColumnsWidth() {
        return getColumns() * getColumnWidth();
    }

    /**
     * �e�[�u�����f������J�������ɑΉ������ModelIndex���擾���܂��B
     */
    protected void checkColumnName() {
        if (getTable() != null) {
            TableModel model = getTable().getModel();
            if (model != null) {
                if (isUseColumnName()) {
                    // /�J���������w�肳��Ă���Ȃ�ΑΉ�����ModelIndex��ǂݒ���
                    String colName = getColumnName();
                    int end = model.getColumnCount();
                    for (int i = 0; i < end; i++) {

                        if (colName.equals(model.getColumnName(i))) {
                            setColumnNameModelIndex(i);
                            return;
                        }
                    }
                }
            }
        }
        setColumnNameModelIndex(-1);
    }

    protected VRTableCellViewer createTableCellViewer() {
        return new ACTableCellViewer();
    }

    /**
     * �J�������ɑΉ�����ModelIndex ��Ԃ��܂��B
     * 
     * @return �J�������ɑΉ�����ModelIndex
     */
    protected int getColumnNameModelIndex() {
        return columnNameModelIndex;
    }

    /**
     * �J���������v�Z���ĕԂ��܂��B
     * 
     * @return �J������
     */
    protected int getColumnWidth() {
        if (columnWidth == 0) {
            if (getTable() != null) {
                FontMetrics fm = getTable()
                        .getFontMetrics(getTable().getFont());
                columnWidth = fm.charWidth('m');
                // ���{�ꕶ���Ή��̂��߁A1.1�{����
                columnWidth = (int) (columnWidth * 1.1);
            }
        }
        return columnWidth;
    }

    /**
     * �J���������g�p���邩��Ԃ��܂��B
     * 
     * @return �J���������g�p���邩
     */
    protected boolean isUseColumnName() {
        String colName = getColumnName();
        return (colName != null) && (!"".equals(colName));
    }

    /**
     * �J�������ɑΉ�����ModelIndex ��ݒ肵�܂��B
     * 
     * @param columnNameModelIndex �J�������ɑΉ�����ModelIndex
     */
    protected void setColumnNameModelIndex(int columnNameModelIndex) {
        this.columnNameModelIndex = columnNameModelIndex;
    }
}
