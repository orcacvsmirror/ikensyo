package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;
import java.text.ParseException;

import javax.swing.JDialog;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTeikeibunEdit extends JDialog {

    private VRLabel header1 = new VRLabel();
    private VRLabel header2 = new VRLabel();
    private VRLabel header3 = new VRLabel();
    private VRPanel inputs = new VRPanel();
    private ACTable table = new ACTable();
    private VRPanel messages = new VRPanel();
    private VRLabel inputNote = new VRLabel();
    private VRLabel inputNote2 = new VRLabel();
    private ACTextField input = new ACTextField();
    private ACButton up = new ACButton();
    private ACButton down = new ACButton();
    private ACButton add = new ACButton();
    private ACButton edit = new ACButton();
    private ACButton delete = new ACButton();
    private ACButton comit = new ACButton();
    private ACButton close = new ACButton();
    private VRPanel header = new VRPanel();
    private VRPanel contents = new VRPanel();
    private VRPanel clients = new VRPanel();
    private VRPanel editors = new VRPanel();
    private VRPanel mover = new VRPanel();
    private VRPanel buttons = new VRPanel();

    // properties
    public static final int DISEASE = 0;
    public static final int TEIKEIBUN = 1;
    private int tableNo;
    private int kubun;
    private VRArrayList rows;
    private boolean modified = false;
    private ACTableModelAdapter model;
    private int length; // 定型文最大長

    protected String tableName;
    protected String dataFieldName;
    protected String orderStatement;
    protected String whereStatement;
    protected String whereFieldName;

    protected boolean changed = false;

    /**
     * @deprecated このコンストラクタは推奨されません。 IkenshoTeikeibunEdit(String title, int
     *             tableNo, int kubun, int length)を使用してください。
     * @param title String
     * @param tableNo int
     * @param kubun int
     */
    public IkenshoTeikeibunEdit(String title, int tableNo, int kubun) {
        this(title, tableNo, kubun, 30);
    }

    /**
     * コンストラクタです。
     * 
     * @param title タイトル
     * @param tableNo テーブル番号
     * @param kubun 定型区分
     * @param length 定型文最大文字数
     */
    public IkenshoTeikeibunEdit(String title, int tableNo, int kubun, int length) {
        super(ACFrame.getInstance(), title, true);
        setTableNo(tableNo);
        setKubun(kubun);
        this.length = length;

        try {
            // setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            initComponent();
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        switch (tableNo) {
        case DISEASE:
            tableName = "M_DISEASE";
            whereFieldName = "DISEASE_KBN";
            dataFieldName = "DISEASE_NM";
            orderStatement = " ORDER BY DISEASE_CD ASC";
            break;
        case TEIKEIBUN:
            tableName = "TEIKEIBUN";
            whereFieldName = "TKB_KBN";
            dataFieldName = "TEIKEIBUN";
            orderStatement = " ORDER BY TKB_CD";
            break;
        }
        whereStatement = " WHERE (" + whereFieldName + " = "
                + String.valueOf(getKubun()) + ")";
        input.setBindPath(dataFieldName);

        // 列の生成
        table
                .setColumnModel(new VRTableColumnModel(
                        new ACTableColumn[] { new ACTableColumn(0, 450,
                                getTitle()), }));

        model = new ACTableModelAdapter(new VRArrayList(),
                new String[] { dataFieldName });
        table.setModel(model);
        doSelect();

        // テーブル選択時
        table.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }

                int selRow = table.getSelectedModelRow();
                if ((selRow >= 0) && (selRow < table.getRowCount())) {
                    try {
                        input.setSource((VRBindSource) rows.getData(selRow));
                        input.bindSource();
                    } catch (ParseException ex) {
                        IkenshoCommon.showExceptionMessage(ex);
                        return;
                    }
                } else {
                    input.setSource(null);
                    input.setText("");
                }
            }
        });

        add.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 入力チェック
                String text = input.getText();
                if (IkenshoCommon.isNullText(text)) {
                    return;
                }

                try {
                    if (indexOf(text) >= 0) {
                        ACMessageBox.show("名称が重複しています。",
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);
                        return;
                    }

                    // 追加
                    VRBindSource newRow = (VRBindSource) inputs.createSource();
                    input.setSource(newRow);
                    input.applySource();
                    newRow.setData(whereFieldName, new Integer(getKubun()));
                    rows.addData(newRow);

                    table.revalidate();
                    // table.getTable().revalidate();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }

                // 後処理
                table.clearSelection();
                modified = true;
            }
        });

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 境界チェック
                int selRow = table.getSelectedModelRow();
                if (selRow < 0) {
                    return;
                }

                // 入力チェック
                String text = input.getText();
                if (IkenshoCommon.isNullText(text)) {
                    return;
                }
                try {
                    int idx = indexOf(text);
                    if (idx == selRow) {
                        return;
                    }
                    if (idx >= 0) {
                        ACMessageBox.show("名称が重複しています。",
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);
                        return;
                    }

                    if (input.getSource() == null) {
                        return;
                    }

                    // 更新
                    input.applySource();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }

                // 後処理
                table.clearSelection();
                modified = true;
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table.getSelectedModelRow();

                // 境界チェック
                if (selRow < 0) {
                    return;
                }

                // 一旦inputをクリア
                input.setText("");

                // 最終選択位置(モデルではなく画面上)を退避
                int lastSelectedIndex = table.getSelectedSortedRow();

                // 削除
                rows.remove(selRow);

                // 再選択
                table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

                // 後処理
                modified = true;
            }
        });

        comit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // DB更新
                try {
                    updateData();
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }
                ACMessageBox.show("更新しました。");
                closeWindow();
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ウィンドウを閉じる
                closeWindow();
            }
        });

        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table.getSelectedModelRow();

                // 境界チェック
                if (selRow < 1) {
                    return;
                }

                // 入替
                Object swap = rows.getData(selRow);
                rows.removeData(selRow);
                rows.add(selRow - 1, swap);
                table.setSelectedModelRow(selRow - 1);

                // 後処理
                modified = true;
            }
        });

        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table.getSelectedModelRow();
                // 境界チェック
                if ((selRow < 0) || (selRow > table.getRowCount() - 2)) {
                    return;
                }

                // 入替
                Object swap = rows.getData(selRow);
                rows.removeData(selRow);
                rows.add(selRow + 1, swap);
                table.setSelectedModelRow(selRow + 1);

                // 後処理
                modified = true;
            }
        });
    }

    /**
     * コンポーネントを初期化します。
     * 
     * @throws Exception 処理例外
     */
    private void jbInit() throws Exception {
        FlowLayout buttonsLayout = new FlowLayout();
        buttonsLayout.setAlignment(FlowLayout.RIGHT);

        header.setLayout(new VRLayout());
        mover.setLayout(new VRLayout());
        buttons.setLayout(buttonsLayout);
        contents.setLayout(new BorderLayout());
        clients.setLayout(new BorderLayout());
        editors.setLayout(new BorderLayout());
        up.setToolTipText("選択されている項目を1つ上へ移動します。");
        down.setToolTipText("選択されている項目を1つ下へ移動します。");
        add.setToolTipText("新規に項目を追加します。");
        edit.setToolTipText("選択されている項目を変更します。");
        delete.setToolTipText("選択されている項目を削除します。");
        comit.setToolTipText("変更内容を登録します。");
        close.setToolTipText("画面を閉じ、元の画面に戻ります。");
        editors.add(inputs, BorderLayout.NORTH);
        editors.add(buttons, BorderLayout.SOUTH);
        buttons.add(add, null);
        buttons.add(edit, null);
        buttons.add(delete, null);
        buttons.add(comit, null);
        buttons.add(close, null);

        inputs.setLayout(new BorderLayout());
        messages.setLayout(new BorderLayout());

        messages.add(inputNote, BorderLayout.WEST);
        messages.add(inputNote2, BorderLayout.EAST);
        inputs.add(input, BorderLayout.SOUTH);
        inputs.add(messages, BorderLayout.NORTH);

        this.getContentPane().add(contents, BorderLayout.CENTER);
        contents.add(header, BorderLayout.NORTH);
        header.add(header1, VRLayout.FLOW_RETURN);
        header.add(header2, VRLayout.FLOW_RETURN);
        header.add(header3, VRLayout.FLOW_RETURN);
        contents.add(clients, BorderLayout.CENTER);
        contents.add(editors, BorderLayout.SOUTH);
        clients.add(mover, BorderLayout.EAST);
        mover.add(up, VRLayout.FLOW_RETURN);
        mover.add(down, VRLayout.FLOW_RETURN);
        clients.add(table, BorderLayout.CENTER);

        // 上部の注意書き
        header1.setText("「" + getTitle() + "」の編集");
        header2.setText("[変更]もしくは[削除]したい項目を候補リストから選んでください。");
        header3.setText("[追加]の時は下のテキストボックスに入力して[追加]を押してください。");

        // 入力欄
        inputNote.setText("入力・編集テキストボックス");
        inputNote2.setText("(" + String.valueOf(length) + "文字以内)");
        inputNote2.setForeground(Color.BLUE.brighter());
        input.setMaxLength(length);
        input.setIMEMode(InputSubset.KANJI);

        // ボタン名の設定
        up.setText("上へ(P)");
        up.setMnemonic('P');
        down.setText("下へ(N)");
        down.setMnemonic('N');

        // ボタン名の設定
        add.setText("追加(A)");
        add.setMnemonic('A');
        edit.setText("変更(U)");
        edit.setMnemonic('U');
        delete.setText("削除(D)");
        delete.setMnemonic('D');
        comit.setText("登録(S)");
        comit.setMnemonic('S');
        close.setText("閉じる(C)");
        close.setMnemonic('C');

        table.setColumnSort(false);
    }

    /**
     * 位置を初期化します。
     */
    private void initComponent() {
        // ウィンドウのサイズ
        setSize(new Dimension(600, 400));
        // ウィンドウを中央に配置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);
    }

    /**
     * モーダルモードで表示し、変更があったかを返します。
     * 
     * @return boolean
     */
    public boolean showModal() {
        setVisible(true);
        // show();
        return changed;
    }

    /**
     * 対象の項目の位置を返します。
     * 
     * @param text 検索対象
     * @throws ParseException 解析例外
     * @return 存在すればその番号、存在しなければ-1
     */
    protected int indexOf(String text) throws ParseException {
        int end = rows.getDataSize();
        for (int i = 0; i < end; i++) {
            if (text.equals(VRBindPathParser.get(dataFieldName, (VRMap) rows
                    .getData(i)))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * コンストラクタです。
     */
    public IkenshoTeikeibunEdit() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // ウィンドウが閉じられたときにDisposeするようにオーバーライド
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        } else {
            super.processWindowEvent(e);
        }
    }

    /**
     * DBからデータを取得し、テーブルに設定します。
     */
    protected void doSelect() {

        // 値の設定
        try {
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT * FROM ");
            sb.append(tableName);
            sb.append(whereStatement);
            sb.append(orderStatement);

            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            rows = (VRArrayList) dbm.executeQuery(sb.toString());
            dbm.finalize();
            model.setAdaptee(rows);

            // 更新フラグの更新
            modified = false;
        } catch (Exception e) {
            IkenshoCommon.showExceptionMessage(e);
        }
    }

    /**
     * テーブル番号を設定します。
     * 
     * @param tableNo テーブル番号
     */
    protected void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    /**
     * 定型区分を設定します。
     * 
     * @param kubun 定型区分
     */
    protected void setKubun(int kubun) {
        this.kubun = kubun;
    }

    /**
     * 定型区分を返します。
     * 
     * @return 定型区分
     */
    protected int getKubun() {
        return kubun;
    }

    /**
     * ウィンドウを閉じます。
     */
    protected void closeWindow() {
        // 確認処理
        if (modified) { // 変更がコミットされていない場合に確認する
            switch (ACMessageBox.showYesNoCancel("変更されています。変更内容を登録しますか？",
                    "登録して戻る(S)", 'S', "破棄して戻る(R)", 'R')) {
            case ACMessageBox.RESULT_YES:
                try {
                    updateData();
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
                break;
            case ACMessageBox.RESULT_NO:
                break;
            default:
                return;
            }
        }

        // 自身を破棄する
        this.dispose();
    }

    /**
     * テーブルの変更内容をDBに反映します。
     * 
     * @throws Exception 処理例外
     */
    private void updateData() throws Exception {
        // DELETE文

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        try {
            dbm.beginTransaction();

            // DELETE
            StringBuffer sb = new StringBuffer();
            sb.append("DELETE");
            sb.append(" FROM ");
            sb.append(tableName);
            sb.append(whereStatement);
            dbm.executeUpdate(sb.toString());

            String head = "INSERT INTO " + tableName + "(";
            String middle = ") VALUES (";
            String foot = ")";

            // INSERT
            switch (tableNo) {
            case DISEASE:
                for (int i = 0; i < rows.getDataSize(); i++) {
                    VRMap row = (VRMap) rows.getData(i);
                    sb = new StringBuffer();
                    sb.append(head);
                    sb.append(" DISEASE_KBN");
                    sb.append(",DISEASE_NM");
                    sb.append(",LAST_TIME");
                    sb.append(middle);
                    sb
                            .append(IkenshoCommon.getDBSafeNumber(
                                    "DISEASE_KBN", row));
                    sb.append(",");
                    sb.append(IkenshoCommon.getDBSafeString("DISEASE_NM", row));
                    sb.append(",CURRENT_TIMESTAMP");
                    sb.append(foot);
                    dbm.executeUpdate(sb.toString());
                }
                break;
            case TEIKEIBUN:
                for (int i = 0; i < rows.getDataSize(); i++) {
                    VRMap row = (VRMap) rows.getData(i);
                    sb = new StringBuffer();
                    sb.append(head);
                    sb.append(" TKB_KBN");
                    sb.append(",TEIKEIBUN");
                    sb.append(",LAST_TIME");
                    sb.append(middle);
                    sb.append(IkenshoCommon.getDBSafeNumber("TKB_KBN", row));
                    sb.append(",");
                    sb.append(IkenshoCommon.getDBSafeString("TEIKEIBUN", row));
                    sb.append(",CURRENT_TIMESTAMP");
                    sb.append(foot);
                    dbm.executeUpdate(sb.toString());
                }
                break;
            }
            dbm.commitTransaction();

            changed = true;
            modified = false;
        } catch (Exception ex) {
            dbm.rollbackTransaction();
            IkenshoCommon.showExceptionMessage(ex);
        }
        dbm.finalize();

    }
}
