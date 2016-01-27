package jp.nichicom.ac.core.debugger;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * システムデバッガです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/30
 */
public class ACStaticDebugger extends JDialog {
    private static ACStaticDebugger singleton;

    /**
     * インスタンスを返します。
     * 
     * @return インスタンス
     */
    public static ACStaticDebugger getInstance() {
        if (singleton == null) {
            singleton = new ACStaticDebugger(ACFrame.getInstance(), "デバッガ",
                    false);
        }
        return singleton;
    }

    /**
     * Creates a modal or non-modal dialog with the specified title and the
     * specified owner <code>Frame</code>. If <code>owner</code> is
     * <code>null</code>, a shared, hidden frame will be set as the owner of
     * this dialog. All constructors defer to this one.
     * <p>
     * NOTE: Any popup components (<code>JComboBox</code>,
     * <code>JPopupMenu</code>, <code>JMenuBar</code>) created within a
     * modal dialog will be forced to be lightweight.
     * <p>
     * This constructor sets the component's locale property to the value
     * returned by <code>JComponent.getDefaultLocale</code>.
     * 
     * @param owner the <code>Frame</code> from which the dialog is displayed
     * @param title the <code>String</code> to display in the dialog's title
     *            bar
     * @param modal true for a modal dialog, false for one that allows other
     *            windows to be active at the same time
     * @exception HeadlessException if GraphicsEnvironment.isHeadless() returns
     *                true.
     * @see java.awt.GraphicsEnvironment#isHeadless
     * @see JComponent#getDefaultLocale
     */
    protected ACStaticDebugger(Frame owner, String title, boolean modal)
            throws HeadlessException {
        super(owner, title, modal);
        initComponent();
    }

    private JButton reflesh;

    private JComboBox windows;

    private JTabbedPane tabs;

    private JPanel components;

    private JPanel componentsContents;

    private JPanel componentEditors;

    private JTree windowChildren;

    private JTextArea componentInfomation;

    private JTextField componentFieldValue;

    private JButton componentFieldValueSet;

    private JPanel componentFieldValuePanel;

    private JScrollPane windowChildrenScroller;

    private JTabbedPane componetTabs;

    private ACPanel componentSnapshotPanel;

    private JTextField componentSnapshotOutputPath;
    private ACLabelContainer componentSnapshotOutputPathContainer;

    private JButton componentSnapshotOutputPathBrowse;

    private JPanel componentSnapshotButtons;

    private JButton componentSnapshot;

    private ACClearableRadioButtonGroup componentSnapshotOutputType;
    private ACLabelContainer componentSnapshotOutputTypeContainer;

    private ACPanel macros;
    private ACPanel macroContents;
    private JButton macroExecute;
    private JButton macroStop;
    private ACTextField macroClassName;
    private ACTextField macroArgs;
    private ACLabelContainer macroClassNameContainer;
    private ACLabelContainer macroArgsContainer;

    /**
     * 画面項目を初期化します。
     */
    protected void initComponent() {
        setSize(300, 600);
        setLocation((int) Toolkit.getDefaultToolkit().getScreenSize()
                .getWidth() - 300, 0);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        tabs = new JTabbedPane();
        componetTabs = new JTabbedPane();
        components = new JPanel();
        componentsContents = new JPanel();
        reflesh = new JButton("最新の状態に更新(R)");
        windows = new JComboBox();
        windowChildren = new JTree();
        windowChildrenScroller = new JScrollPane(windowChildren);
        componentEditors = new JPanel();
        componentInfomation = new JTextArea(4, 20);
        componentFieldValue = new JTextField();
        componentFieldValueSet = new JButton("変更");
        componentFieldValuePanel = new JPanel();
        componentSnapshotPanel = new ACPanel();
        componentSnapshot = new JButton("キャプチャ");
        componentSnapshotOutputPath = new JTextField();
        componentSnapshotOutputPathBrowse = new JButton("参照..");
        componentSnapshotOutputPathContainer = new ACLabelContainer("出力先");
        componentSnapshotButtons = new JPanel();
        componentSnapshotOutputType = new ACClearableRadioButtonGroup();
        componentSnapshotOutputTypeContainer = new ACLabelContainer("出力形式");

        macros = new ACPanel();
        macroContents = new ACPanel();
        macroExecute = new JButton("実行");
        macroStop = new JButton("停止");
        macroClassName = new ACTextField();
        macroArgs = new ACTextField();
        macroClassNameContainer = new ACLabelContainer("マクロクラス名");
        macroArgsContainer = new ACLabelContainer("マクロ引数");
        
        
        getContentPane().setLayout(new BorderLayout());
        components.setLayout(new BorderLayout());
        componentsContents.setLayout(new BorderLayout());
        componentEditors.setLayout(new BorderLayout());
        componentFieldValuePanel.setLayout(new BorderLayout());
        componentSnapshotButtons.setLayout(new FlowLayout());

        reflesh.setMnemonic('R');
        reflesh.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                componentReflesh();
            }

        });
        windows.setRenderer(new ACStaticDebuggerWindowListCellRenderer());
        windows.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                windowListSelectionChanged();
            }
        });
        windowChildren
                .setCellRenderer(new ACStaticDebuggerComponentTreeCellRenderer());
        windowChildren.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                componentSelectionChanged();
            }
        });
        componentInfomation.setLineWrap(true);
        componentFieldValueSet.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateFieldValue();
            }
        });

        componentSnapshotOutputPath.setColumns(15);

        componentSnapshot.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                componetCapture();
            }
        });
        componentSnapshotOutputType.setUseClearButton(false);
        componentSnapshotOutputType.setModel(new ACListModelAdapter(
                new VRArrayList(Arrays
                        .asList(new String[] { "ファイル", "クリップボード", }))));
        componentSnapshotOutputType.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                boolean fileEnabled = false;
                switch(componentSnapshotOutputType.getSelectedIndex()){
                case 1:
                    //ファイル
                    fileEnabled = true;
                    break;
                case 2:
                    //クリップボード
                    break;
                }
                componentSnapshotOutputPathContainer.setEnabled(fileEnabled);
                componentSnapshotOutputPath.setEnabled(fileEnabled);
                componentSnapshotOutputPathBrowse.setEnabled(fileEnabled);
            }
        });
        componentSnapshotOutputType.setSelectedIndex(1);

        macroClassName.setColumns(15);
        macroArgs.setColumns(15);
        macroExecute.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                executeMacro();
            }
        });
        macroStop.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                stopMacro();
            }
        });
        
        getContentPane().add(tabs, BorderLayout.CENTER);

        tabs.addTab("画面項目", components);
        tabs.addTab("マクロ", macros);
        componetTabs.addTab("値", componentEditors);
        componetTabs.addTab("画面", componentSnapshotPanel);

        components.add(reflesh, BorderLayout.NORTH);
        components.add(componentsContents, BorderLayout.CENTER);
        componentsContents.add(windows, BorderLayout.NORTH);
        componentsContents.add(windowChildrenScroller, BorderLayout.CENTER);
        componentsContents.add(componetTabs, BorderLayout.SOUTH);
        componentEditors.add(componentInfomation, BorderLayout.CENTER);
        componentEditors.add(componentFieldValuePanel, BorderLayout.SOUTH);
        componentFieldValuePanel.add(componentFieldValueSet, BorderLayout.EAST);
        componentFieldValuePanel.add(componentFieldValue, BorderLayout.CENTER);

        componentSnapshotPanel.add(componentSnapshotOutputTypeContainer,
                VRLayout.NORTH);
        componentSnapshotPanel.add(componentSnapshotOutputPathContainer,
                BorderLayout.NORTH);
        componentSnapshotPanel.add(componentSnapshotButtons,
                VRLayout.CENTER);

        componentSnapshotOutputTypeContainer.add(componentSnapshotOutputType,
                VRLayout.FLOW);

        componentSnapshotOutputPathContainer.add(componentSnapshotOutputPath,
                VRLayout.CLIENT);
        componentSnapshotOutputPathContainer.add(
                componentSnapshotOutputPathBrowse, VRLayout.EAST);
        componentSnapshotButtons.add(componentSnapshot, null);

        macroClassNameContainer.add(macroClassName, VRLayout.FLOW);
        macroContents.add(macroClassNameContainer, VRLayout.FLOW_INSETLINE_RETURN);
        macroArgsContainer.add(macroArgs, VRLayout.FLOW);
        macroContents.add(macroArgsContainer, VRLayout.FLOW_INSETLINE_RETURN);
        macroContents.add(macroExecute, VRLayout.FLOW);
        macroContents.add(macroStop, VRLayout.FLOW_RETURN);
        macros.add(macroContents, VRLayout.CLIENT);

        
        componentReflesh();
    }
    /**
     * マクロを停止します。
     */
    protected void stopMacro(){
        if(macro!=null){
            macro.stopMacro();
            macro = null;
        }
    }    
    private ACDebuggerMacro macro;
    /**
     * マクロを実行します。
     */
    protected void executeMacro(){
        Class c;
        try {
            c=Class.forName(macroClassName.getText());
        } catch (ClassNotFoundException ex) {
            VRLogger.info(ex);
            ACMessageBox.show("マクロクラスの定義が見つかりません。");
            return;
        }
        Object ins;
        try {
            ins = c.newInstance();
        } catch (Exception ex) {
            VRLogger.info(ex);
            ACMessageBox.show("マクロクラスをインスタンス化できません。");
            return;
        }
        if(!(ins instanceof ACDebuggerMacro)){
            ACMessageBox.show("マクロクラスはACDebuggerMacroインターフェースを実装していません。");
            return;
        }
        stopMacro();
        
        macro = (ACDebuggerMacro)ins;
        
        macro.executeMacro(macroArgs.getText());
        
    }

    /**
     * 画面項目をキャプチャして保存します。
     */
    protected void componetCapture() {

        TreePath path = windowChildren.getSelectionPath();
        if (path != null) {
            Object obj = path.getLastPathComponent();
            if (obj instanceof DefaultMutableTreeNode) {
                obj = ((DefaultMutableTreeNode) obj).getUserObject();
                if (obj instanceof Component) {
                    Component comp = (Component) obj;

                    BufferedImage buf = new BufferedImage(comp.getWidth(), comp
                            .getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics g = buf.getGraphics();
                    comp.paintAll(g);
                    g.dispose();

                    try {
                        switch (componentSnapshotOutputType.getSelectedIndex()) {
                        case 1: {// ファイル
                            File outputDir = new File(componentSnapshotOutputPath.getText());
                            if (!outputDir.exists()) {
                                // 指定パスが存在しない
                                ACMessageBox.show("保存先のフォルダが存在しません。");
                                return;
                            }
                            if (outputDir.isFile()) {
                                outputDir = new File(outputDir.getParent());
                            } else {
                                String targetDir = outputDir.getPath();
                                if (!targetDir.endsWith(ACConstants.FILE_SEPARATOR)) {
                                    // 相対パスは絶対パスにする
                                    targetDir += ACConstants.FILE_SEPARATOR;
                                    outputDir = new File(targetDir);
                                }
                            }
                            // ファイル名作成
                            String targetDir = outputDir.getAbsolutePath();
                            StringBuffer sb = new StringBuffer(targetDir);
                            sb.append(toSinmpleName(comp.getClass()));
                            String name = comp.getName();
                            if ((name != null) && (!"".equals(name))) {
                                sb.append("(" + name + ")");
                            }
                            sb.append(".jpg");

                            FileOutputStream st = new FileOutputStream(sb
                                    .toString());
//                            JPEGImageEncoder enc = JPEGCodec
//                                    .createJPEGEncoder(st);
//                            enc.encode(buf);
                            
                            ImageIO.write(new Robot().createScreenCapture(new Rectangle(
                                    Toolkit.getDefaultToolkit().getScreenSize())), "jpeg", st);
                            
                            
                            st.close();

                            ACMessageBox.show(targetDir
                                    + ACConstants.LINE_SEPARATOR + "に出力しました。");
                            break;
                        }
                        case 2: {// クリップボード
                            ACStaticDebuggerComponentCapture img = new ACStaticDebuggerComponentCapture(
                                    buf);
                            Toolkit.getDefaultToolkit().getSystemClipboard()
                                    .setContents(img, img);
                            break;
                        }
                        }
                    } catch (Exception ex) {
                        ACCommon.getInstance().showExceptionMessage(ex);
                    }
                }
            }
        }
    }

    /** Image を転送する際に必要な能力を実装する Transferable です。 */
    protected class ACStaticDebuggerComponentCapture implements Transferable,
            ClipboardOwner {
        Image image = null;

        /**
         * コンストラクタです。
         * 
         * @param image 画像
         */
        public ACStaticDebuggerComponentCapture(Image image) {
            this.image = image;
        }

        /**
         * 転送されるデータを表すオブジェクトを返します。
         */
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (DataFlavor.imageFlavor.equals(flavor)) {
                return image;
            }
            throw new UnsupportedFlavorException(flavor);
        }

        /**
         * データを提供することができるフレーバを示す DataFlavor オブジェクトの配列を返します。
         */
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }

        /**
         * 指定されたデータフレーバが、このオブジェクトに対してサポートされているかどうかを返します。
         */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        /**
         * クリップボードの内容のオーナではなくなったことを、このオブジェクトに通知します。
         */
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
            image = null;
        }
    }

    /**
     * パッケージ名を除いたクラス名を返します。
     * 
     * @param cls クラス
     * @return パッケージ名を除いたクラス名
     */
    protected String toSinmpleName(Class cls) {
        String fullName = cls.getName();
        if (fullName != null) {
            Package pkg = cls.getPackage();
            if (pkg != null) {
                String pkgName = pkg.getName();
                if (pkgName != null) {
                    int pkgLen = pkgName.length() + 1;
                    if (fullName.length() > pkgLen) {
                        return fullName.substring(pkgLen);
                    }
                }
            }
            return fullName;
        }
        return "";
    }

    /**
     * 選択しているフィールドの値を指定値に変更します。
     */
    protected void updateFieldValue() {
        TreePath path = windowChildren.getSelectionPath();
        if (path != null) {
            Object obj = path.getLastPathComponent();
            if (obj instanceof DefaultMutableTreeNode) {
                obj = ((DefaultMutableTreeNode) obj).getUserObject();
                if (obj instanceof ACStaticDebuggerComponentField) {
                    ACStaticDebuggerComponentField compField = (ACStaticDebuggerComponentField) obj;
                    Field field = compField.getField();
                    Class type = field.getType();
                    Object val;
                    if (Integer.class.equals(type) || int.class.equals(type)) {
                        // int型の解析
                        val = Integer.valueOf(componentFieldValue.getText());
                    } else if (String.class.equals(type)) {
                        // 文字列型の解析
                        val = componentFieldValue.getText();
                    } else if (Boolean.class.equals(type)
                            || boolean.class.equals(type)) {
                        // 真偽値型の解析
                        val = Boolean.valueOf(componentFieldValue.getText());
                    } else if (Long.class.equals(type)
                            || long.class.equals(type)) {
                        // 長整数の解析
                        val = Long.valueOf(componentFieldValue.getText());
                    } else if (Double.class.equals(type)
                            || double.class.equals(type)) {
                        // 倍精度実数の解析
                        val = Double.valueOf(componentFieldValue.getText());
                    } else if (Float.class.equals(type)
                            || float.class.equals(type)) {
                        // 単精度実数の解析
                        val = Float.valueOf(componentFieldValue.getText());
                    } else {
                        // 非対応
                        return;
                    }
                    try {
                        field.set(compField.getOwner(), val);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }

    /**
     * 画面項目の選択イベントを処理します。
     */
    protected void componentSelectionChanged() {
        if (windowChildren.isSelectionEmpty()) {
            return;
        }
        Object item = windowChildren.getSelectionPath().getLastPathComponent();
        if (item instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = ((DefaultMutableTreeNode) item);
            Object obj = node.getUserObject();
            componentInfomation.setText(String.valueOf(obj));
            if (obj instanceof ACStaticDebuggerComponentField) {
                ACStaticDebuggerComponentField field = (ACStaticDebuggerComponentField) obj;
                componentFieldValue.setText(String.valueOf(field.getValue()));
                Class type = field.getField().getType();
                if (int.class.equals(type) || String.class.equals(type)
                        || boolean.class.equals(type)
                        || long.class.equals(type) || float.class.equals(type)
                        || double.class.equals(type)
                        || Boolean.class.equals(type)
                        || Integer.class.equals(type)
                        || Long.class.equals(type) || Float.class.equals(type)
                        || Double.class.equals(type)) {
                    componentFieldValueSet.setEnabled(true);
                } else {
                    componentFieldValueSet.setEnabled(false);
                }
            } else {
                componentFieldValue.setText("");
                componentFieldValueSet.setEnabled(false);
            }
        }
    }

    /**
     * 画面項目を再帰的に解析します。
     * 
     * @param parent 上位ツリーノード
     * @param target 画面項目
     * @param addedComponents 追加済みのコンポーネントセット
     * @throws Exception 処理例外
     */
    protected void enumComponentAnalize(DefaultMutableTreeNode parent,
            Component target, Set addedComponents) throws Exception {
        int end;

        if (target instanceof Container) {
            end = ((Container) target).getComponentCount();
            for (int i = 0; i < end; i++) {
                Component child = ((Container) target).getComponent(i);
                if (addedComponents.contains(child)) {
                    continue;
                }
                addedComponents.add(child);

                DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(child);
                enumComponentAnalize(leaf, child, addedComponents);
                parent.add(leaf);
            }
        }
        enumFieldAnalize(parent, target, addedComponents, target.getClass());
    }

    /**
     * 画面項目のフィールドを再帰的に解析します。
     * 
     * @param parent 上位ツリーノード
     * @param target 画面項目
     * @param addedComponents 追加済みのコンポーネントセット
     * @param fieldClass フィールドのクラス
     * @throws Exception 処理例外
     */
    protected void enumFieldAnalize(DefaultMutableTreeNode parent,
            Object target, Set addedComponents, Class fieldClass)
            throws Exception {
        ACStaticDebuggerComponentFieldContainer fieldContainer = new ACStaticDebuggerComponentFieldContainer();
        DefaultMutableTreeNode fieldParent = new DefaultMutableTreeNode(
                fieldContainer);
        fieldContainer.setNode(fieldParent);

        Field[] fields = fieldClass.getDeclaredFields();
        int end = fields.length;
        for (int i = 0; i < end; i++) {
            Field field = fields[i];
            field.setAccessible(true);
            Object val = field.get(target);

            if (val instanceof Component) {
                Component comp = (Component) val;
                if (addedComponents.contains(comp)) {
                    continue;
                }
                addedComponents.add(comp);

                DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(val);
                enumComponentAnalize(leaf, (Component) val, addedComponents);
                parent.add(leaf);
            } else {
                DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(
                        new ACStaticDebuggerComponentField(target, field, val));

                if (val instanceof Collection) {
                    Collection c = (Collection) val;
                    Iterator it = c.iterator();
                    while (it.hasNext()) {
                        Object v = it.next();
//                        enumFieldAnalize(leaf, val, addedComponents, v
//                                .getClass());
                    }
                }else if(val instanceof Map){
                    //Mapの場合はentrySet
                    Map map = (Map)val;
                    
                    Collection c=map.entrySet();
                    Iterator it = c.iterator();
                    while (it.hasNext()) {
                        Object v = it.next();
//                        enumFieldAnalize(leaf, val, addedComponents, c
//                                .getClass());
                    }
                    
                    
                }

                fieldParent.add(leaf);
            }
        }
        parent.add(fieldParent);

        Class parentClass = fieldClass.getSuperclass();
        if (parentClass != null) {
            // 上位クラスのフィールドを追加
            DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(
                    parentClass);
            enumFieldAnalize(leaf, target, addedComponents, parentClass);
            parent.add(leaf);
        }
    }

    /**
     * 画面一覧の更新イベントを処理します。
     */
    protected void windowListSelectionChanged() {
        Object obj = windows.getSelectedItem();
        if (!(obj instanceof Component)) {
            return;
        }
        Component rootInstance = (Component) obj;

        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootInstance);
        try {
            Set addedComponents = new HashSet();
            addedComponents.add(rootInstance);
            enumComponentAnalize(root, rootInstance, addedComponents);
        } catch (Exception ex) {

        }

        windowChildren.setModel(new DefaultTreeModel(root));

    }

    /**
     * 画面項目を再読み込みします。
     */
    protected void componentReflesh() {
        Frame[] frames = JFrame.getFrames();

        windows.setModel(new DefaultComboBoxModel(frames));
        if (windows.getItemCount() > 0) {
            windows.setSelectedIndex(0);
        } else {
            windowChildren.setModel(null);
        }
    }

    /**
     * 初期化します。
     */
    public void clear() {
        windows.setModel(null);
        windowChildren.setModel(null);
        componentInfomation.setText("");
        componentFieldValue.setText("");
    }

    /**
     * システムデバッガの画面一覧用セルレンダラです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2006/03/31
     */
    protected class ACStaticDebuggerWindowListCellRenderer extends
            DefaultListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            if (value instanceof Frame) {
                value = ((Frame) value).getTitle();
            }
            return super.getListCellRendererComponent(list, value, index,
                    isSelected, cellHasFocus);
        }
    }

    /**
     * システムデバッガのフィールド一覧定義です。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2006/01/30
     */
    protected class ACStaticDebuggerComponentFieldContainer {
        private TreeNode node;

        /**
         * コンストラクタです。
         */
        public ACStaticDebuggerComponentFieldContainer() {

        }

        /**
         * 対象ノードを返します。
         * 
         * @return 対象ノード
         */
        public TreeNode getNode() {
            return node;
        }

        /**
         * 対象ノードを設定します。
         * 
         * @param node 対象ノード
         */
        public void setNode(TreeNode node) {
            this.node = node;
        }

        public String toString() {

            if (getNode() != null) {
                int end = getNode().getChildCount();
                return "フィールド(" + end + ")";
            }
            return "";
        }
    }

    /**
     * システムデバッガのフィールド定義です。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2006/01/30
     */
    protected class ACStaticDebuggerComponentField {
        private Field field;

        private Object value;

        private Object owner;

        /**
         * フィールドの所有者を返します。
         * 
         * @return フィールドの所有者
         */
        public Object getOwner() {
            return owner;
        }

        /**
         * フィールドの所有者を設定します。
         * 
         * @param owner フィールドの所有者
         */
        public void setOwner(Object owner) {
            this.owner = owner;
        }

        /**
         * コンストラクタです。
         * 
         * @param owner フィールドの所有者
         * @param field フィールド
         * @param value 値
         */
        public ACStaticDebuggerComponentField(Object owner, Field field,
                Object value) {
            setOwner(owner);
            setField(field);
            setValue(value);
        }

        /**
         * フィールドを返します。
         * 
         * @return フィールド
         */
        public Field getField() {
            return field;
        }

        /**
         * フィールドを設定します。
         * 
         * @param field フィールド
         */
        public void setField(Field field) {
            this.field = field;
        }

        /**
         * 値を返します。
         * 
         * @return 値
         */
        public Object getValue() {
            return value;
        }

        /**
         * 値を設定します。
         * 
         * @param value 値
         */
        public void setValue(Object value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(getField());
        }
    }

    /**
     * システムデバッガの画面項目セルレンダラです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2006/01/30
     */
    protected class ACStaticDebuggerComponentTreeCellRenderer extends
            DefaultTreeCellRenderer {
        public Component getTreeCellRendererComponent(JTree tree, Object value,
                boolean sel, boolean expanded, boolean leaf, int row,
                boolean hasFocus) {
            if (value instanceof DefaultMutableTreeNode) {
                value = ((DefaultMutableTreeNode) value).getUserObject();
            }
            if (value instanceof ACStaticDebuggerComponentField) {
                value = ((ACStaticDebuggerComponentField) value).getField();
            }
            if (value instanceof Field) {
                Field field = (Field) value;
                value = toSinmpleName(field.getType()) + " : "
                        + field.getName();
            } else if (value instanceof Component) {
                Component comp = (Component) value;
                value = toSinmpleName(comp.getClass());
                String name = comp.getName();
                if ((name != null) && (!"".equals(name))) {
                    value = value + " : " + name;
                }
                if (comp instanceof JTextComponent) {
                    value = value + "(" + ((JTextComponent) comp).getText()
                            + ")";
                } else if (comp instanceof AbstractButton) {
                    value = value + "(" + ((AbstractButton) comp).getText()
                            + ")";
                } else if (comp instanceof JLabel) {
                    value = value + "(" + ((JLabel) comp).getText() + ")";
                } else if (comp instanceof ACGroupBox) {
                    value = value + "(" + ((ACGroupBox) comp).getText() + ")";
                } else if (comp instanceof ACAffairButtonBar) {
                    value = value + "(" + ((ACAffairButtonBar) comp).getText()
                            + ")";
                } else if (comp instanceof Frame) {
                    value = value + "(" + ((Frame) comp).getTitle() + ")";
                }
            } else if (value instanceof Class) {
                Class cls = (Class) value;
                value = "上位クラス : " + toSinmpleName(cls);
            } else if (value instanceof ACStaticDebuggerComponentFieldContainer) {
                ACStaticDebuggerComponentFieldContainer fieldContainer = (ACStaticDebuggerComponentFieldContainer) value;
                value = fieldContainer.toString();
            }
            return super.getTreeCellRendererComponent(tree, value, sel,
                    expanded, leaf, row, hasFocus);
        }
    }

}
