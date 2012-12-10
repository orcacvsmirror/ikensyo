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
 * CSVファイルをテーブルモデルとして扱うアダプタクラスです。
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
     * コンストラクタです。
     * 
     * @param path ファイルパス
     */
    public VRCSVFile(String path) {
        this(path, System.getProperty("file.encoding"));
    }

    /**
     * コンストラクタです。
     * 
     * @param path ファイルパス
     * @param encode エンコード
     */
    public VRCSVFile(String path, String encode) {
        setPath(path);
        setEncode(encode);
    }

    /**
     * 列データを追加します。
     * 
     * @param index 列番号[0..n-1]
     * @param data 列データ
     */
    public void addRow(int index, List data) {
        getRows().add(index, data);
    }

    /**
     * 列データを追加します。
     * 
     * @param index 列番号[0..n-1]
     * @param data 列データ
     */
    public void addRow(int index, String[] data) {
        addRow(index, Arrays.asList(data));
    }

    /**
     * 列データを追加します。
     * 
     * @param data 列データ
     */
    public void addRow(List data) {
        getRows().add(data);
    }

    /**
     * 列データを追加します。
     * 
     * @param data 列データ
     */
    public void addRow(String[] data) {
        addRow(new ArrayList(Arrays.asList(data)));
    }

    /**
     * 指定列にデータを追加します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @param data データ
     */
    public void addValue(int rowIndex, int colIndex, Object data) {
        getRow(rowIndex).add(colIndex, data);
    }

    /**
     * 指定列にデータを追加します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param data データ
     */
    public void addValue(int rowIndex, Object data) {
        getRow(rowIndex).add(data);
    }

    /**
     * 保持データを消去します。
     */
    public void clear() {
        getRows().clear();
    }

    /**
     * 指定列の列データが存在するかを返します。
     * 
     * @param index 列番号[0..n-1]
     * @return 指定列の列データが存在するか
     */
    public boolean existsRow(int index) {
        return (index >= 0) && (getRowCount() > index);
    }

    /**
     * 指定行列のデータが存在するかを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @return 指定行列のデータが存在するか
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
     * 行定義を返します。
     * 
     * @return 行定義
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
     * 指定列の行データ数を返します。
     * 
     * @param index 列番号[0..n-1]
     * @return 保持行数
     */
    public int getColumnCount(int index) {
        return getRow(index).size();
    }

    public String getColumnName(int column) {
        return getColumn()[column];
    }

    /**
     * ファイルエンコードを返します。
     * 
     * @return エンコード
     */
    public String getEncode() {
        return encode;
    }

    /**
     * 改行コード を返します。
     * <p>
     * デフォルト(null)ならばシステムの改行コードを使用します。
     * </p>
     * 
     * @return 改行コード
     */
    public String getLineSeparator() {
        return lineSeparator;
    }

    /**
     * ファイルパスを返します。
     * 
     * @return ファイルパス
     */
    public String getPath() {
        return path;
    }

    /**
     * 指定列の列データを返します。
     * 
     * @param index 列番号[0..n-1]
     * @return 列データ
     */
    public List getRow(int index) {
        return (List) getRows().get(index);
    }

    /**
     * 保持列数を返します。
     * 
     * @return 保持列数
     */
    public int getRowCount() {
        return getRows().size();
    }

    /**
     * 指定行列のデータを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @return データ
     */
    public Object getValueAt(int rowIndex, int colIndex) {
        return getRow(rowIndex).get(colIndex);
    }

    /**
     * 指定行列のデータを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @return データ
     */
    public boolean getValueAtBoolean(int rowIndex, int colIndex) {
        return Boolean.valueOf(getValueAtString(rowIndex, colIndex))
                .booleanValue();
    }

    /**
     * 指定行列のデータを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @return データ
     */
    public double getValueAtDouble(int rowIndex, int colIndex) {
        return Double.valueOf(getValueAtString(rowIndex, colIndex))
                .doubleValue();
    }

    /**
     * 指定行列のデータを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @return データ
     */
    public int getValueAtInteger(int rowIndex, int colIndex) {
        return Integer.valueOf(getValueAtString(rowIndex, colIndex)).intValue();
    }

    /**
     * 指定行列のデータを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     * @return データ
     */
    public String getValueAtString(int rowIndex, int colIndex) {
        return String.valueOf(getValueAt(rowIndex, colIndex));
    }

    /**
     * 指定列にデータが存在するかを返します。
     * 
     * @param rowIndex 列番号[0..n-1]
     * @param data データ
     * @return データ位置番号。該当しなければ-1
     */
    public int indexOf(int rowIndex, Object data) {
        return getRow(rowIndex).indexOf(data);
    }

    /**
     * 保持データは空であるかを返します。
     * 
     * @return 保持データは空であるか
     */
    public boolean isEmpty() {
        return getRows().isEmpty();
    }

    /**
     * 指定列の行データは空であるかを返します。
     * 
     * @param index 列番号[0..n-1]
     * @return 指定列の行データは空であるか
     */
    public boolean isEmpty(int index) {
        return getRow(index).isEmpty();
    }

    /**
     * 指定のパスにファイルが存在するかを返します。
     * 
     * @return 指定のパスにファイルが存在するか
     */
    public boolean isFile() {
        return new File(getPath()).isFile();
    }

    /**
     * 読み込み可能であるかを返します。
     * 
     * @return 読み込み可能であるか
     */
    public boolean canRead() {
        return new File(getPath()).canRead();
    }

    /**
     * 書き込み可能であるかを返します。
     * 
     * @return 書き込み可能であるか
     */
    public boolean canWrite() {
        return new File(getPath()).canWrite();
    }

    /**
     * CSVファイルを読み込みます。
     * 
     * @param hasHeader 開始行をヘッダとして解釈するか
     * @throws IOException 読み込みエラー
     */
    public void read(boolean hasHeader) throws IOException {

        BufferedReader br = new BufferedReader(new InputStreamReader(
                new FileInputStream(getPath()), encode));

        // クォートでくくっているか
        final int NONE = 0;
        final int QUOTING = 1;
        final int QUOTED = 2;
        int quoteStatus = NONE;
        // クォートをエスケープしたか
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
                        // クォート以降が切り出し対象
                        cellBeginPos = i + 1;
                        quoteStatus = QUOTING;
                        quoteEscaped = false;
                        break;
                    case QUOTING:
                        // クォート中のクォート → 直前までが切り出し対象
                        cellEndPos = i;
                        quoteStatus = QUOTED;
                        break;
                    case QUOTED:
                        // クォート終了と思ったのに続けてクォート → クォートエスケープ
                        cellEndPos = i;
                        quoteStatus = QUOTING;
                        quoteEscaped = true;
                        break;
                    }
                } else if (arr[i] == COMMA) {
                    switch (quoteStatus) {
                    case NONE:
                        // クォートなしの状態でカンマ → 直前までが切り出し対象
                        cellEndPos = i;
                        row.add(line.substring(cellBeginPos, cellEndPos));
                        cellBeginPos = i + 1;
                        break;
                    case QUOTING:
                        // クォート中 → スキップ
                        break;
                    case QUOTED:
                        // クォート済みの状態でカンマ → クォートの直前までが切り出し対象

                        if (quoteEscaped) {
                            // クォートエスケープを行った → [""]を["]に置換
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

            // 改行
            if (quoteStatus == QUOTING) {
                // クォート中なので次行へまたがらせる
                quoteHead.append(line.substring(cellBeginPos));
                quoteHead.append(LINE_SEPARATOR);
                cellBeginPos = 0;
            } else {
                // 列終了
                if (cellBeginPos <= cellEndPos) {
                    // cellBeginPos>cellEndPosの場合、最後のセルがクォートのため追加済み
                    // 最後のセルを追加
                    row.add(quoteHead
                            + line.substring(cellBeginPos, cellEndPos));
                }
                if(quoteHead.length()>0){
                    quoteHead =  new StringBuffer();
                }
                quoteStatus = NONE;

                if (hasHeader) {
                    // 開始行はヘッダ
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
     * 指定列を削除します。
     * 
     * @param index 列番号[0..n-1]
     */
    public void removeRow(int index) {
        getRows().remove(index);
    }

    /**
     * 行定義を設定します。
     * 
     * @param column 行定義
     */
    public void setColumn(String[] column) {
        this.column = column;
    }

    /**
     * ファイルエンコードを設定します。
     * 
     * @param encode エンコード
     */
    public void setEncode(String encode) {
        this.encode = encode;
    }

    /**
     * 改行コード を設定します。
     * <p>
     * デフォルト(null)ならばシステムの改行コードを使用します。
     * </p>
     * 
     * @param lineSeparator 改行コード
     */
    public void setLineSeparator(String lineSeparator) {
        this.lineSeparator = lineSeparator;
    }

    /**
     * ファイルパスを設定します。
     * 
     * @param path ファイルパス
     */
    public void setPath(String path) {
        this.path = path;
    }

    /**
     * 指定行列のデータを設定します。行方向は必要に応じて拡張されます。
     * 
     * @param data データ
     * @param rowIndex 列番号[0..n-1]
     * @param colIndex 行番号[0..n-1]
     */
    public void setValueAt(Object data, int rowIndex, int colIndex) {
        List row = getRow(rowIndex);
        if (row.size() > colIndex) {
            row.set(colIndex, data);
        } else {
            // 範囲外なので拡張する
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
     * CSVファイルを書き出します。
     * 
     * @param putHeader ヘッダを付加するか
     * @param canOverride 上書きを許可するか
     * @throws IOException 書き込みエラー
     */
    public void write(boolean putHeader, boolean canOverride)
            throws IOException {
        File f = new File(getPath());
        if (!canOverride) {
            if (f.exists()) {
                throw new IOException("既にファイルが存在します。");
            }
        }

        // 書き出し
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(f), encode));
        bw.write(createWriteData(putHeader, canOverride));
        bw.close();
    }

    /**
     * 行データをCSV書式化してStringBufferに追加します。
     * 
     * @param row 行データ
     * @param sb 追加先
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

        // 末尾の','を削る
        sb.deleteCharAt(sb.length() - 1);

        sb.append(lineSeparator);
    }

    /**
     * CSV形式の文字列を返します。
     * 
     * @param putHeader ヘッダを付加するか
     * @param canOverride 上書きを許可するか
     * @throws IOException 書き込みエラー
     */
    protected String createWriteData(boolean putHeader, boolean canOverride) {
        if (lineSeparator == null) {
            // 遅延定数化
            lineSeparator = System.getProperty("line.separator");
        }
        StringBuffer sb = new StringBuffer();

        if (putHeader && (getColumn() != null)) {
            // 先にヘッダを出力
            appendRow(Arrays.asList(getColumn()), sb);
        }

        // 保持行を走査して出力
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
     * 列データ集合を返します。
     * 
     * @return 列データ集合
     */
    protected ArrayList getRows() {
        return this.rows;
    }

}