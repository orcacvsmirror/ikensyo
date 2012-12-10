/** TODO <HEAD> */
package jp.nichicom.vr.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.swing.table.AbstractTableModel;

/**
 * CSV�t�@�C�����e�[�u�����f���Ƃ��Ĉ����A�_�v�^�N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see AbstractTableModel
 */
public class VRCSVFile extends AbstractTableModel {
    public static final String EUC_JP = "EUC-JP";

    public static final String ISO_2022_JP = "ISO-2022-JP";

    public static final String JIS = "JIS";

    public static final String SJIS = "SJIS";

    public static final String UTF8 = "UTF8";

    private static final char COMMA = ',';

    private static final String ONE_QUOTE = "\"";

    private static final char QUOTE = '\"';

    private static final String QUOTE_AND_COMMA = "\",";

    private static final String TWO_QUOTE = "\"\"";

    private String[] column;

    private String encode;

    private String lineSeparator = null;

    private String path;

    private ArrayList rows = new ArrayList();

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param path �t�@�C���p�X
     */
    public VRCSVFile(String path) {
        this(path, System.getProperty("file.encoding"));
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param path �t�@�C���p�X
     * @param encode �G���R�[�h
     */
    public VRCSVFile(String path, String encode) {
        setPath(path);
        setEncode(encode);
    }

    /**
     * ��f�[�^��ǉ����܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     * @param data ��f�[�^
     */
    public void addRow(int index, List data) {
        getRows().add(index, data);
    }

    /**
     * ��f�[�^��ǉ����܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     * @param data ��f�[�^
     */
    public void addRow(int index, String[] data) {
        addRow(index, Arrays.asList(data));
    }

    /**
     * ��f�[�^��ǉ����܂��B
     * 
     * @param data ��f�[�^
     */
    public void addRow(List data) {
        getRows().add(data);
    }

    /**
     * ��f�[�^��ǉ����܂��B
     * 
     * @param data ��f�[�^
     */
    public void addRow(String[] data) {
        addRow(new ArrayList(Arrays.asList(data)));
    }

    /**
     * �w���Ƀf�[�^��ǉ����܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @param data �f�[�^
     */
    public void addValue(int rowIndex, int colIndex, Object data) {
        getRow(rowIndex).add(colIndex, data);
    }

    /**
     * �w���Ƀf�[�^��ǉ����܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param data �f�[�^
     */
    public void addValue(int rowIndex, Object data) {
        getRow(rowIndex).add(data);
    }

    /**
     * �ێ��f�[�^���������܂��B
     */
    public void clear() {
        getRows().clear();
    }

    /**
     * �w���̗�f�[�^�����݂��邩��Ԃ��܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     * @return �w���̗�f�[�^�����݂��邩
     */
    public boolean existsRow(int index) {
        return (index >= 0) && (getRowCount() > index);
    }

    /**
     * �w��s��̃f�[�^�����݂��邩��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @return �w��s��̃f�[�^�����݂��邩
     */
    public boolean existsValue(int rowIndex, int colIndex) {
        if (existsRow(rowIndex)) {
            if ((colIndex >= 0) && (getColumnCount(rowIndex) > colIndex)) {
                return true;
            }
        }
        return false;
    }

    /**
     * �s��`��Ԃ��܂��B
     * 
     * @return �s��`
     */
    public String[] getColumn() {
        return this.column;
    }

    public Class getColumnClass(int columnIndex) {
        if (existsValue(0, columnIndex)) {
            return getRow(0).get(columnIndex).getClass();
        } else {
            return String.class;
        }
    }

    public int getColumnCount() {
        if (getColumn() != null) {
            return getColumn().length;
        }
        return getRow(0).size();
    }

    /**
     * �w���̍s�f�[�^����Ԃ��܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     * @return �ێ��s��
     */
    public int getColumnCount(int index) {
        return getRow(index).size();
    }

    public String getColumnName(int column) {
        return getColumn()[column];
    }

    /**
     * �t�@�C���G���R�[�h��Ԃ��܂��B
     * 
     * @return �G���R�[�h
     */
    public String getEncode() {
        return encode;
    }

    /**
     * ���s�R�[�h ��Ԃ��܂��B
     * <p>
     * �f�t�H���g(null)�Ȃ�΃V�X�e���̉��s�R�[�h���g�p���܂��B
     * </p>
     * 
     * @return ���s�R�[�h
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * �t�@�C���p�X��Ԃ��܂��B
     * 
     * @return �t�@�C���p�X
     */
    public String getPath() {
        return path;
    }

    /**
     * �w���̗�f�[�^��Ԃ��܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     * @return ��f�[�^
     */
    public List getRow(int index) {
        return (List) getRows().get(index);
    }

    /**
     * �ێ��񐔂�Ԃ��܂��B
     * 
     * @return �ێ���
     */
    public int getRowCount() {
        return getRows().size();
    }

    /**
     * �w��s��̃f�[�^��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @return �f�[�^
     */
    public Object getValueAt(int rowIndex, int colIndex) {
        return getRow(rowIndex).get(colIndex);
    }

    /**
     * �w��s��̃f�[�^��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @return �f�[�^
     */
    public boolean getValueAtBoolean(int rowIndex, int colIndex) {
        return Boolean.valueOf(getValueAtString(rowIndex, colIndex))
                .booleanValue();
    }

    /**
     * �w��s��̃f�[�^��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @return �f�[�^
     */
    public double getValueAtDouble(int rowIndex, int colIndex) {
        return Double.valueOf(getValueAtString(rowIndex, colIndex))
                .doubleValue();
    }

    /**
     * �w��s��̃f�[�^��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @return �f�[�^
     */
    public int getValueAtInteger(int rowIndex, int colIndex) {
        return Integer.valueOf(getValueAtString(rowIndex, colIndex)).intValue();
    }

    /**
     * �w��s��̃f�[�^��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     * @return �f�[�^
     */
    public String getValueAtString(int rowIndex, int colIndex) {
        return String.valueOf(getValueAt(rowIndex, colIndex));
    }

    /**
     * �w���Ƀf�[�^�����݂��邩��Ԃ��܂��B
     * 
     * @param rowIndex ��ԍ�[0..n-1]
     * @param data �f�[�^
     * @return �f�[�^�ʒu�ԍ��B�Y�����Ȃ����-1
     */
    public int indexOf(int rowIndex, Object data) {
        return getRow(rowIndex).indexOf(data);
    }

    /**
     * �ێ��f�[�^�͋�ł��邩��Ԃ��܂��B
     * 
     * @return �ێ��f�[�^�͋�ł��邩
     */
    public boolean isEmpty() {
        return getRows().isEmpty();
    }

    /**
     * �w���̍s�f�[�^�͋�ł��邩��Ԃ��܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     * @return �w���̍s�f�[�^�͋�ł��邩
     */
    public boolean isEmpty(int index) {
        return getRow(index).isEmpty();
    }

    /**
     * �w��̃p�X�Ƀt�@�C�������݂��邩��Ԃ��܂��B
     * 
     * @return �w��̃p�X�Ƀt�@�C�������݂��邩
     */
    public boolean isFile() {
        return new File(getPath()).isFile();
    }

    /**
     * �ǂݍ��݉\�ł��邩��Ԃ��܂��B
     * 
     * @return �ǂݍ��݉\�ł��邩
     */
    public boolean canRead() {
        return new File(getPath()).canRead();
    }

    /**
     * �������݉\�ł��邩��Ԃ��܂��B
     * 
     * @return �������݉\�ł��邩
     */
    public boolean canWrite() {
        return new File(getPath()).canWrite();
    }

    /**
     * CSV�t�@�C����ǂݍ��݂܂��B
     * 
     * @param hasHeader �J�n�s���w�b�_�Ƃ��ĉ��߂��邩
     * @throws IOException �ǂݍ��݃G���[
     */
    public void read(boolean hasHeader) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(getPath()), encode));

        // �N�H�[�g�ł������Ă��邩
        final int NONE = 0;
        final int QUOTING = 1;
        final int QUOTED = 2;
        int quoteStatus = NONE;
        // �N�H�[�g���G�X�P�[�v������
        boolean quoteEscaped = false;
        
        final String LINE_SEPARATOR = System.getProperty("line.separator");
        

        StringBuffer quoteHead = new StringBuffer();
        ArrayList row = new ArrayList();
        String line;
        int cellBeginPos = 0;
        int cellEndPos = 0;
        while ((line = br.readLine()) != null) {
            char[] arr = line.toCharArray();
            int len = arr.length;
            for (int i = 0; i < len; i++) {
                if (arr[i] == QUOTE) {
                    switch (quoteStatus) {
                    case NONE:
                        // �N�H�[�g�ȍ~���؂�o���Ώ�
                        cellBeginPos = i + 1;
                        quoteStatus = QUOTING;
                        quoteEscaped = false;
                        break;
                    case QUOTING:
                        // �N�H�[�g���̃N�H�[�g �� ���O�܂ł��؂�o���Ώ�
                        cellEndPos = i;
                        quoteStatus = QUOTED;
                        break;
                    case QUOTED:
                        // �N�H�[�g�I���Ǝv�����̂ɑ����ăN�H�[�g �� �N�H�[�g�G�X�P�[�v
                        cellEndPos = i;
                        quoteStatus = QUOTING;
                        quoteEscaped = true;
                        break;
                    }
                } else if (arr[i] == COMMA) {
                    switch (quoteStatus) {
                    case NONE:
                        // �N�H�[�g�Ȃ��̏�ԂŃJ���} �� ���O�܂ł��؂�o���Ώ�
                        cellEndPos = i;
                        row.add(line.substring(cellBeginPos, cellEndPos));
                        cellBeginPos = i + 1;
                        break;
                    case QUOTING:
                        // �N�H�[�g�� �� �X�L�b�v
                        break;
                    case QUOTED:
                        // �N�H�[�g�ς݂̏�ԂŃJ���} �� �N�H�[�g�̒��O�܂ł��؂�o���Ώ�

                        if (quoteEscaped) {
                            // �N�H�[�g�G�X�P�[�v���s���� �� [""]��["]�ɒu��
                            row.add((quoteHead.toString() + line.substring(cellBeginPos,
                                    cellEndPos)).replaceAll(TWO_QUOTE,
                                    ONE_QUOTE));
                            quoteEscaped = false;
                        } else {
                            row.add(quoteHead.toString()
                                    + line.substring(cellBeginPos, cellEndPos));
                        }
                        cellBeginPos = i + 1;
                        quoteStatus = NONE;
                        if(quoteHead.length()>0){
                            quoteHead = new StringBuffer();
                        }
                        break;
                    }
                }
            }

            // ���s
            if (quoteStatus == QUOTING) {
                // �N�H�[�g���Ȃ̂Ŏ��s�ւ܂����点��
                quoteHead.append(line.substring(cellBeginPos));
                quoteHead.append(LINE_SEPARATOR);
                cellBeginPos = 0;
            } else {
                // ��I��
                if (cellBeginPos <= cellEndPos) {
                    // cellBeginPos>cellEndPos�̏ꍇ�A�Ō�̃Z�����N�H�[�g�̂��ߒǉ��ς�
                    // �Ō�̃Z����ǉ�
                    row.add(quoteHead
                            + line.substring(cellBeginPos, cellEndPos));
                }
                if(quoteHead.length()>0){
                    quoteHead =  new StringBuffer();
                }
                quoteStatus = NONE;

                if (hasHeader) {
                    // �J�n�s�̓w�b�_
                    int size = row.size();
                    String[] head = new String[size];
                    System.arraycopy(row.toArray(), 0, head, 0, size);
                    setColumn(head);
                    hasHeader = false;
                } else {
                    addRow(row);
                }
                row = new ArrayList();
            }
        }
        br.close();
    }

    /**
     * �w�����폜���܂��B
     * 
     * @param index ��ԍ�[0..n-1]
     */
    public void removeRow(int index) {
        getRows().remove(index);
    }

    /**
     * �s��`��ݒ肵�܂��B
     * 
     * @param column �s��`
     */
    public void setColumn(String[] column) {
        this.column = column;
    }

    /**
     * �t�@�C���G���R�[�h��ݒ肵�܂��B
     * 
     * @param encode �G���R�[�h
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * ���s�R�[�h ��ݒ肵�܂��B
     * <p>
     * �f�t�H���g(null)�Ȃ�΃V�X�e���̉��s�R�[�h���g�p���܂��B
     * </p>
     * 
     * @param lineSeparator ���s�R�[�h
     */
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    /**
     * �t�@�C���p�X��ݒ肵�܂��B
     * 
     * @param path �t�@�C���p�X
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * �w��s��̃f�[�^��ݒ肵�܂��B�s�����͕K�v�ɉ����Ċg������܂��B
     * 
     * @param data �f�[�^
     * @param rowIndex ��ԍ�[0..n-1]
     * @param colIndex �s�ԍ�[0..n-1]
     */
    public void setValueAt(Object data, int rowIndex, int colIndex) {
        List row = getRow(rowIndex);
        if (row.size() > colIndex) {
            row.set(colIndex, data);
        } else {
            // �͈͊O�Ȃ̂Ŋg������
            int addSize = colIndex - row.size();
            Object[] addData = new Object[addSize + 1];
            for (int i = 0; i < addSize; i++) {
                addData[i] = "";
            }
            addData[addSize] = data;
            row.addAll(Arrays.asList(addData));
        }
    }

    /**
     * CSV�t�@�C���������o���܂��B
     * 
     * @param putHeader �w�b�_��t�����邩
     * @param canOverride �㏑���������邩
     * @throws IOException �������݃G���[
     */
    public void write(boolean putHeader, boolean canOverride)
            throws IOException {
        File f = new File(getPath());
        if (!canOverride) {
            if (f.exists()) {
                throw new IOException("���Ƀt�@�C�������݂��܂��B");
            }
        }

        // �����o��
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f), encode));
        bw.write(createWriteData(putHeader, canOverride));
        bw.close();
    }

    /**
     * �s�f�[�^��CSV����������StringBuffer�ɒǉ����܂��B
     * 
     * @param row �s�f�[�^
     * @param sb �ǉ���
     */
    protected void appendRow(List row, StringBuffer sb) {
        Iterator colIt = row.iterator();
        if (!colIt.hasNext()) {
            return;
        }

        do {
            Object col = colIt.next();
            sb.append(ONE_QUOTE);
            sb.append(String.valueOf(col).replaceAll(ONE_QUOTE, TWO_QUOTE));
            sb.append(QUOTE_AND_COMMA);
        } while (colIt.hasNext());

        // ������','�����
        sb.deleteCharAt(sb.length() - 1);

        sb.append(lineSeparator);
    }

    /**
     * CSV�`���̕������Ԃ��܂��B
     * 
     * @param putHeader �w�b�_��t�����邩
     * @param canOverride �㏑���������邩
     * @throws IOException �������݃G���[
     */
    protected String createWriteData(boolean putHeader, boolean canOverride) {
        if (lineSeparator == null) {
            // �x���萔��
            lineSeparator = System.getProperty("line.separator");
        }
        StringBuffer sb = new StringBuffer();

        if (putHeader && (getColumn() != null)) {
            // ��Ƀw�b�_���o��
            appendRow(Arrays.asList(getColumn()), sb);
        }

        // �ێ��s�𑖍����ďo��
        Iterator rowIt = getRows().iterator();
        while (rowIt.hasNext()) {
            Object row = rowIt.next();
            if (row instanceof List) {
                appendRow((List) row, sb);
            }
        }
        return sb.toString();
    }

    /**
     * ��f�[�^�W����Ԃ��܂��B
     * 
     * @return ��f�[�^�W��
     */
    protected ArrayList getRows() {
        return this.rows;
    }

}