package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoQueryAnalizer extends IkenshoDialog {
    private JPanel contentPane;
    private ACTable table = new ACTable();
    private ACRowMaximumableTextArea area = new ACRowMaximumableTextArea();
    private ACRowMaximumableTextArea info = new ACRowMaximumableTextArea();
    private JScrollPane areaPane = new JScrollPane();
    private JScrollPane infoPane = new JScrollPane();
    private ACButton throwQuery = new ACButton();

    public IkenshoQueryAnalizer(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IkenshoQueryAnalizer() {
        this(ACFrame.getInstance(), "医見書 Query Analizer", false);
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new VRLayout());
        contentPane.add(areaPane, VRLayout.NORTH);
        contentPane.add(throwQuery, VRLayout.NORTH);
        contentPane.add(infoPane, VRLayout.SOUTH);
        contentPane.add(table, VRLayout.CLIENT);
        areaPane.setViewportView(area);
        infoPane.setViewportView(info);
        area.setRows(5);
        info.setRows(5);
        info.setEditable(false);
        throwQuery.setText("実行(E)");
        throwQuery.setMnemonic('E');
    }

    private void init() throws Exception {
        setSize(new Dimension(800, 640));
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

    private void event() throws Exception {
        throwQuery.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                doThrowQuery();
                area.requestFocus();
            }
        });
    }

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }

    protected void closeWindow() {
        this.dispose();
    }

    private void doThrowQuery() {
        info.setText("");

        if (queryResult != null) {
            // 全件クリア
            queryResult.clear();
        }
        table.repaint();

        String sql = area.getText();
        if (!IkenshoCommon.isNullText(sql)) {
            if (sql.toLowerCase().trim().startsWith("select")) {
                doExecuteQueryProc(sql);
            } else {
                doUpdateQueryProc(sql);
            }
        }
    }

    private VRArrayList queryResult;

    private void doExecuteQueryProc(String sql) {
        try {
            VRArrayList data = doExecuteQuery(sql);
            if (data.getDataSize() > 0) {
                VRMap map = (VRMap) data.getData(0);
                Iterator it = map.keySet().iterator();
                String[] cols = new String[map.size()];
                VRTableColumn[] tblCol = new VRTableColumn[map.size()];
                for (int i = 0; i < map.size(); i++) {
                    String field = it.next().toString();
                    cols[i] = field;
                    tblCol[i] = new VRTableColumn(i, 100, field);
                }
                table.setColumnModel(new VRTableColumnModel(tblCol));
                table.setModel(new ACTableModelAdapter(data, cols));
                table.setVisible(true);

                // 全件クリア用に退避
                queryResult = data;
            } else {
                setMsg("該当するレコードが見つかりませんでした。");
            }
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public VRArrayList doExecuteQuery(String sql) throws Exception {
        VRArrayList data = null;
        try {
            IkenshoFirebirdDBManager fdbm = new IkenshoFirebirdDBManager();
            data = (VRArrayList) fdbm.executeQuery(sql);
        } catch (Exception ex) {
            throw new Exception(ex);
        }
        return data;
    }

    private void doUpdateQueryProc(String sql) {
        try {
            int cnt = doUpdateQuery(sql);
            setMsg(cnt + "件処理しました。\n\n");
        } catch (Exception ex) {
            setMsg(ex.getMessage());
        }
    }

    public int doUpdateQuery(String sql) throws Exception {
        IkenshoFirebirdDBManager fdbm = new IkenshoFirebirdDBManager();
        int cnt = 0;
        try {
            fdbm.beginTransaction();
            cnt = fdbm.executeUpdate(sql);
            fdbm.commitTransaction();
        } catch (Exception ex) {
            fdbm.rollbackTransaction();
            throw new Exception(ex);
        }
        return cnt;
    }

    private void setMsg(String msg) {
        info.setText(msg);
        table.setVisible(false);
    }
}
