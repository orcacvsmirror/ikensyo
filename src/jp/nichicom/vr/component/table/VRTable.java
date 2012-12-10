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
 * カラムヘッダクリックによるソート機能に対応したテーブルです。
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
     * <code>ストライプカラーの初期設定色</code>
     */
    public static final Color DEFAULT_STRIPE_COLOR = Color.decode("#FFFFCC");

    private boolean columnSort = true;
    private boolean extendLastColumn;
    private Point pressPoint;
    private HashMap sortSequence;
    private boolean stripe;
    private Color stripeColor;

    /**
     * コンストラクタです。
     * <p>
     * デフォルトデータモデル、デフォルト列モデル、およびデフォルト選択モデルで初期化される、デフォルトの Table を構築します。
     * </p>
     */
    public VRTable() {
        super();
        initComponent();
    }

    /**
     * コンストラクタです。
     * <p>
     * DefaultTableModel を使って、空のセルの numRows と numColumns で Table
     * を構築します。列は、「A」、「B」、「C」といった形式の名前を持ちます。
     * </p>
     * 
     * @param numRows テーブルが保持する行数
     * @param numColumns テーブルが保持する列数
     */
    public VRTable(int numRows, int numColumns) {
        super(numRows, numColumns);
        initComponent();
    }

    /**
     * コンストラクタです。
     * <p>
     * 2 次元配列 rowData の値を表示する JTable を、列名 columnNames で構築します。
     * </p>
     * 
     * @param rowData 新しいテーブルのデータ
     * @param columnNames 各列の名前
     */
    public VRTable(final Object[][] rowData, final Object[] columnNames) {
        super(rowData, columnNames);
        initComponent();
    }

    /**
     * コンストラクタです。
     * <p>
     * データモデル dm、デフォルト列モデル、およびデフォルト選択モデルで初期化される JTable を構築します。
     * </p>
     * 
     * @param dm テーブルのデータモデル
     */
    public VRTable(TableModel dm) {
        super(dm);
        initComponent();
    }

    /**
     * コンストラクタです。
     * <p>
     * データモデル dm、列モデル cm、およびデフォルト選択モデルで初期化される JTable を構築します。
     * </p>
     * 
     * @param dm テーブルのデータモデル
     * @param cm テーブルの列モデル
     */
    public VRTable(TableModel dm, TableColumnModel cm) {
        super(dm, cm);
        initComponent();
    }

    /**
     * コンストラクタです。
     * <p>
     * データモデル dm、列モデル cm、および選択モデル sm で初期化される JTable を構築します。
     * </p>
     * 
     * @param dm テーブルのデータモデル
     * @param cm テーブルの列モデル
     * @param sm テーブルの行選択モデル
     */
    public VRTable(TableModel dm, TableColumnModel cm, ListSelectionModel sm) {
        super(dm, cm, sm);
        initComponent();
    }

    /**
     * コンストラクタです。
     * <p>
     * Vectors の Vector の値を表示する JTable、つまり rowData を、列名 columnNames で構築します。
     * </p>
     * 
     * @param rowData 新しいテーブルのデータ
     * @param columnNames 各列の名前
     */
    public VRTable(Vector rowData, Vector columnNames) {
        super(rowData, columnNames);
        initComponent();
    }

    /**
     * ソート状態をクリアします。
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
     * 右端自動調節機能を実行します。
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
     * ソート状態マップを返します。
     * 
     * @return ソート状態マップ
     */
    public HashMap getSortSequence() {
        return sortSequence;
    }

    /**
     * ストライプカラーを返します。
     * 
     * @return ストライプカラー
     */
    public Color getStripeColor() {
        return stripeColor;
    }

    /**
     * カラムのクリックによるソートを許可するか を返します。
     * 
     * @return カラムのクリックによるソートを許可するか
     */
    public boolean isColumnSort() {
        return columnSort;
    }

    /**
     * 右端自動調整機能の使用/未使用を返します。
     * 
     * @return 右端自動調整機能を使用する場合はtrue、しない場合はfalse
     */
    public boolean isExtendLastColumn() {
        return extendLastColumn;
    }

    /**
     * ソート状態マップを設定します。
     * 
     * @param sortSequence ソート状態マップ
     */
    public void setSortSequence(HashMap sortSequence) {
        this.sortSequence = sortSequence;
    }

    /**
     * ストライプ機能の使用/未使用を返します。
     * 
     * @return ストライプ機能を使用する場合はtrue、しない場合はfalse
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
                // カーソルが標準でなければ何もしない
                return;
            }
            if (!isColumnSort()) {
                // カラムソート禁止ならば何もしない
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
     * ソート処理を行います。
     * 
     * @param column カラム番号
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
                // 強制的に昇順ソート
                sort(columnName + " " + VRTableSortable.ORDER_ASC_VALUE);
                seq.put(columnName, VRTableSortable.ORDER_ASC_VALUE);
            }
        }
    }

    /**
     * モデル上の選択行データを返します。
     * 
     * @return 選択行データ
     * @throws Exception 処理例外
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
        throw new IllegalAccessException("未対応の行データモデルです。");
    }

    /**
     * ソート結果を考慮した画面上の選択列番号を返します。
     * 
     * @return 画面上の選択中の列番号
     */
    public int getSelectedSortedRow() {
        return getSelectedRow();
    }

    /**
     * 選択中のデータ列番号を返します。
     * 
     * @return 選択中のデータ列番号
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
     * 最後にソートしたときと同条件でソート処理を行います。
     */
    public void resort() {
        if (getModel() instanceof VRSortableTableModelar) {
            VRSortableTableModelar model = (VRSortableTableModelar) getModel();
            model.resort();
        }
    }

    /**
     * カラムのクリックによるソートを許可するかを設定します。
     * 
     * @param columnSort カラムのクリックによるソートを許可するか
     */
    public void setColumnSort(boolean columnSort) {
        this.columnSort = columnSort;
    }

    /**
     * 右端自動調整機能の使用/未使用を設定します。
     * 
     * @param extendLastColumn 右端自動調整機能を使用する場合はtrue、しない場合はfalse
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
            // ソート可能なモデルでなければラップする
            dataModel = createSortableModel(dataModel);
            ((VRSortableTableModelar) dataModel).setTable(this);
        }
        super.setModel(dataModel);
        if (getModel() != null) {
            getModel().addTableModelListener(this);
        }
        //ソート状態を解除
        clearSortSequence();
    }

    public void setColumnModel(TableColumnModel columnModel) {
        super.setColumnModel(columnModel);
        if (columnModel instanceof VRTableColumnModelar) {
            ((VRTableColumnModelar) columnModel).setTable(this);
        }
    }

    /**
     * ソート可能なテーブルモデルにラップして返します。
     * 
     * @param model ラッピングされるテーブルモデル
     * @return ソート可能なテーブルモデル
     */
    protected VRSortableTableModelar createSortableModel(TableModel model) {
        return new VRSortableTableModel(model);
    }

    /**
     * ストライプ機能の使用/未使用を設定します。
     * 
     * @param stripe ストライプ機能を使用する場合はtrue、しない場合はfalse
     */
    public void setStripe(boolean stripe) {
        this.stripe = stripe;
        resizeAndRepaint();
    }

    /**
     * ストライプカラーを設定します。
     * 
     * @param stripeColor ストライプカラー
     */
    public void setStripeColor(Color stripeColor) {
        this.stripeColor = stripeColor;
    }

    /**
     * ソート処理を行います。
     * 
     * @param condition ソート条件式
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
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {
        addComponentListener(this);
        // 変数初期化
        sortSequence = new HashMap();
        // 初期設定
        VRTableCellViewer viewer = new VRTableCellViewer();
        setDefaultRenderer(Object.class, viewer);
        setDefaultEditor(Object.class, viewer);
        // getTableHeader().getDefaultRenderer();
        getTableHeader().setDefaultRenderer(createHeaderRenderer());
        setStripe(false);
        setStripeColor(DEFAULT_STRIPE_COLOR);
        setAutoResizeMode(AUTO_RESIZE_OFF);
        setExtendLastColumn(false);
        // イベント設定
        getTableHeader().addMouseListener(this);
        // 擬似フォーカストラバーサル
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
     * テーブルヘッダとして使用するレンダラを生成します。
     * @return テーブルヘッダとして使用するレンダラ
     */
    protected TableCellRenderer createHeaderRenderer(){
        return new VRTableHeaderRenderer();
    }
}