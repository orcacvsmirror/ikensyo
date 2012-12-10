package jp.nichicom.ac.util.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import jp.nichicom.ac.component.table.ACTableCellViewerCustomCell;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.table.VRSortableTableModel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRTableModelAdapter;

/**
 * �e�[�u�����f���p�̃A�_�v�^�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRSortableTableModel
 */

public class ACTableModelAdapter extends VRTableModelAdapter {

    private HashMap parsedConcatStatusCache = new HashMap();

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACTableModelAdapter() {
        super(new VRArrayList(), new String[] {});
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param columns �J������`
     */
    public ACTableModelAdapter(String[] columns) {
        super(new VRArrayList(), columns);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param adaptee �A�_�v�e�B�[�ƂȂ�o�C���h�\�[�X
     * @param columns �J������`
     */
    public ACTableModelAdapter(VRBindSource adaptee, String[] columns) {
        super(adaptee, columns);
    }

    /**
     * �J�������������ƈ�v�����ԍ���Ԃ��܂��B
     * 
     * @param columnName �J������
     * @return ��ԍ�
     */
    public int getColumnIndex(String columnName) {
        if (columnName != null) {
            String[] columns = getColumns();
            if (columns != null) {
                int end = columns.length;
                for (int i = 0; i < end; i++) {
                    if (columnName.equals(columns[i])) {
                        return i;
                    }
                }
            }
        }
        return -1;
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Object obj = (Object[]) getParsedConcatStatusCache().get(
                new Integer(columnIndex));
        if (obj instanceof Object[]) {
            Object[] statuses = (Object[]) obj;
            // �����ݒ�����߂���
            StringBuffer sb = new StringBuffer();
            int end = statuses.length;
            for (int i = 0; i < end; i++) {
                Object status = statuses[i];
                if (status instanceof Integer) {
                    // �J�����̃C���f�b�N�X�w��
                    Object val = super.getValueAt(rowIndex, ((Integer) status)
                            .intValue());
                    if (val != null) {
                        sb.append(val);
                    }
                } else {
                    // ���e����
                    sb.append(status);
                }
            }
            return sb.toString();

        }
        // �����ݒ肪�Ȃ���Ă��Ȃ��J�����Ȃ�ΕW���̒l�擾���\�b�h���g�p����
        return super.getValueAt(rowIndex, columnIndex);
    }

    public boolean isCellEditable(int row, int column) {
        if (getTable() != null) {
            TableColumnModel model = getTable().getColumnModel();
            if (model != null) {
                int end = model.getColumnCount();
                for (int i = 0; i < end; i++) {
                    TableColumn tableColumn = model.getColumn(i);
                    if (column == tableColumn.getModelIndex()) {
                        if (tableColumn instanceof VRTableColumn) {
                            if (tableColumn instanceof ACTableColumn) {
                                // NC�e�[�u���J�����̏ꍇ�A�J�X�^���Z���ݒ���m�F����B
                                List cells = ((ACTableColumn) tableColumn)
                                        .getCustomCells();
                                if ((cells != null) && (cells.size() <= row)) {
                                    Object obj = cells.get(row);
                                    if (obj instanceof ACTableCellViewerCustomCell) {
                                        // �J�X�^���Z���̎w���D�悷��
                                        return ((ACTableCellViewerCustomCell) obj)
                                                .isEditable();
                                    }
                                }
                            }
                            VRTableColumn ncc = (VRTableColumn) tableColumn;
                            return ncc.isVisible() & ncc.isEditable();
                        }
                    }
                }
            }
        }
        return super.isCellEditable(row, column);
    }

    public void setColumns(String[] columns) {
        super.setColumns(columns);
        // �L���b�V�����N���A
        setParsedConcatStatusCache(new HashMap());
        parseConcatStatus();
    }

    /**
     * �����ݒ�̉�͍ς݃L���b�V�� ��Ԃ��܂��B
     * <p>
     * �L���b�V�������L�[�v�f�͂��ꂼ��Object[]�ō\������܂��B<br />
     * Object[]�̗v�f��Integer�Ȃ�΃J�����̗�ԍ��A����ȊO�̓��e���������ƌ��Ȃ��܂��B
     * </p>
     * 
     * @return �����ݒ�̉�͍ς݃L���b�V��
     */
    protected HashMap getParsedConcatStatusCache() {
        return parsedConcatStatusCache;
    }

    /**
     * �����ݒ����͂��܂��B
     */
    protected void parseConcatStatus() {
        HashMap map = new HashMap();
        String[] columns = getColumns();
        int end = columns.length;
        for (int i = 0; i < end; i++) {
            // ���ׂẴJ�����𑖍�����
            map.put(new Integer(i), parseConcatStatus(columns[i]));
        }
        // �L���b�V���ɓo�^
        getParsedConcatStatusCache().putAll(map);
    }

    /**
     * �����ݒ����͂��܂��B
     * <p>
     * �����ݒ肪�Ȃ���Ă��Ȃ����疳���ł���΁Anull��Ԃ��܂��B
     * </p>
     * 
     * @param column ��͑Ώۂ̃J������
     * @return ��͌���
     */
    protected Object[] parseConcatStatus(String column) {
        // �������ʎq
        final String CONCAT_TOKEN = "+";
        // �G�X�P�[�v�V�[�P���X
        final char ESCAPE_TAG = '\\';
        // ���e�����������͂ގ��ʎq
        final String LITERAL_QUOTE = "'";

        String[] array = column.split("\\" + CONCAT_TOKEN);
        int arrayLength = array.length;
        if (arrayLength <= 1) {
            // �g�[�N��(+)���Ȃ���΃��e�������܂܂Ȃ�
            return null;
        }

        StringBuffer sb = new StringBuffer();
        ArrayList list = new ArrayList();
        for (int j = 0; j < arrayLength; j++) {
            // �J��������+�ŕ������đ�������
            String concat = array[j];
            if (!"".equals(concat)) {
                boolean escaping = false;
                int len = concat.length();
                for (int k = 0; k < len; k++) {
                    // �G�X�P�[�v�V�[�P���X�̑���
                    char c = concat.charAt(k);
                    if (c == ESCAPE_TAG) {
                        if (escaping) {
                            // �G�X�P�[�v�V�[�P���X�̘A���̓G�X�P�[�v����
                            sb.append(ESCAPE_TAG);
                        }
                        escaping = !escaping;
                    } else {
                        sb.append(concat.charAt(k));
                        escaping = false;
                    }
                }
                if (escaping) {
                    // �G�X�P�[�v�V�[�P���X�ŏI�����
                    // �����̗v�f��A������
                    sb.append(CONCAT_TOKEN);
                    continue;
                }

                String val = sb.toString();
                if (val.startsWith(LITERAL_QUOTE)) {
                    // ���e�����J�n�����Ŏn�܂��Ă���
                    if (!val.endsWith(LITERAL_QUOTE)) {
                        // ���e�����I�[�����ŏI����Ă��Ȃ��i���e�������̃g�[�N���j
                        // �����̗v�f��A������
                        sb.append(CONCAT_TOKEN);
                        continue;
                    }
                    // ���e���������Ƃ��Ēǉ�
                    list.add(val.substring(1, val.length() - 1));
                } else {
                    // �J�����̗�ԍ���ǉ�
                    list.add(new Integer(getColumnIndex(val)));
                }
            }

            sb = new StringBuffer();
        }
        return list.toArray();
    }

    /**
     * �����ݒ�̉�͍ς݃L���b�V�� ��ݒ肵�܂��B
     * 
     * @param parsedConcatStatusCache �����ݒ�̉�͍ς݃L���b�V��
     */
    protected void setParsedConcatStatusCache(HashMap parsedConcatStatusCache) {
        this.parsedConcatStatusCache = parsedConcatStatusCache;
    }

}
