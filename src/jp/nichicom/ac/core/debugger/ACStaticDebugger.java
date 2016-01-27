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
 * �V�X�e���f�o�b�K�ł��B
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
     * �C���X�^���X��Ԃ��܂��B
     * 
     * @return �C���X�^���X
     */
    public static ACStaticDebugger getInstance() {
        if (singleton == null) {
            singleton = new ACStaticDebugger(ACFrame.getInstance(), "�f�o�b�K",
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
     * ��ʍ��ڂ����������܂��B
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
        reflesh = new JButton("�ŐV�̏�ԂɍX�V(R)");
        windows = new JComboBox();
        windowChildren = new JTree();
        windowChildrenScroller = new JScrollPane(windowChildren);
        componentEditors = new JPanel();
        componentInfomation = new JTextArea(4, 20);
        componentFieldValue = new JTextField();
        componentFieldValueSet = new JButton("�ύX");
        componentFieldValuePanel = new JPanel();
        componentSnapshotPanel = new ACPanel();
        componentSnapshot = new JButton("�L���v�`��");
        componentSnapshotOutputPath = new JTextField();
        componentSnapshotOutputPathBrowse = new JButton("�Q��..");
        componentSnapshotOutputPathContainer = new ACLabelContainer("�o�͐�");
        componentSnapshotButtons = new JPanel();
        componentSnapshotOutputType = new ACClearableRadioButtonGroup();
        componentSnapshotOutputTypeContainer = new ACLabelContainer("�o�͌`��");

        macros = new ACPanel();
        macroContents = new ACPanel();
        macroExecute = new JButton("���s");
        macroStop = new JButton("��~");
        macroClassName = new ACTextField();
        macroArgs = new ACTextField();
        macroClassNameContainer = new ACLabelContainer("�}�N���N���X��");
        macroArgsContainer = new ACLabelContainer("�}�N������");
        
        
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
                        .asList(new String[] { "�t�@�C��", "�N���b�v�{�[�h", }))));
        componentSnapshotOutputType.addListSelectionListener(new ListSelectionListener(){
            public void valueChanged(ListSelectionEvent e) {
                boolean fileEnabled = false;
                switch(componentSnapshotOutputType.getSelectedIndex()){
                case 1:
                    //�t�@�C��
                    fileEnabled = true;
                    break;
                case 2:
                    //�N���b�v�{�[�h
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

        tabs.addTab("��ʍ���", components);
        tabs.addTab("�}�N��", macros);
        componetTabs.addTab("�l", componentEditors);
        componetTabs.addTab("���", componentSnapshotPanel);

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
     * �}�N�����~���܂��B
     */
    protected void stopMacro(){
        if(macro!=null){
            macro.stopMacro();
            macro = null;
        }
    }    
    private ACDebuggerMacro macro;
    /**
     * �}�N�������s���܂��B
     */
    protected void executeMacro(){
        Class c;
        try {
            c=Class.forName(macroClassName.getText());
        } catch (ClassNotFoundException ex) {
            VRLogger.info(ex);
            ACMessageBox.show("�}�N���N���X�̒�`��������܂���B");
            return;
        }
        Object ins;
        try {
            ins = c.newInstance();
        } catch (Exception ex) {
            VRLogger.info(ex);
            ACMessageBox.show("�}�N���N���X���C���X�^���X���ł��܂���B");
            return;
        }
        if(!(ins instanceof ACDebuggerMacro)){
            ACMessageBox.show("�}�N���N���X��ACDebuggerMacro�C���^�[�t�F�[�X���������Ă��܂���B");
            return;
        }
        stopMacro();
        
        macro = (ACDebuggerMacro)ins;
        
        macro.executeMacro(macroArgs.getText());
        
    }

    /**
     * ��ʍ��ڂ��L���v�`�����ĕۑ����܂��B
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
                        case 1: {// �t�@�C��
                            File outputDir = new File(componentSnapshotOutputPath.getText());
                            if (!outputDir.exists()) {
                                // �w��p�X�����݂��Ȃ�
                                ACMessageBox.show("�ۑ���̃t�H���_�����݂��܂���B");
                                return;
                            }
                            if (outputDir.isFile()) {
                                outputDir = new File(outputDir.getParent());
                            } else {
                                String targetDir = outputDir.getPath();
                                if (!targetDir.endsWith(ACConstants.FILE_SEPARATOR)) {
                                    // ���΃p�X�͐�΃p�X�ɂ���
                                    targetDir += ACConstants.FILE_SEPARATOR;
                                    outputDir = new File(targetDir);
                                }
                            }
                            // �t�@�C�����쐬
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
                                    + ACConstants.LINE_SEPARATOR + "�ɏo�͂��܂����B");
                            break;
                        }
                        case 2: {// �N���b�v�{�[�h
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

    /** Image ��]������ۂɕK�v�Ȕ\�͂��������� Transferable �ł��B */
    protected class ACStaticDebuggerComponentCapture implements Transferable,
            ClipboardOwner {
        Image image = null;

        /**
         * �R���X�g���N�^�ł��B
         * 
         * @param image �摜
         */
        public ACStaticDebuggerComponentCapture(Image image) {
            this.image = image;
        }

        /**
         * �]�������f�[�^��\���I�u�W�F�N�g��Ԃ��܂��B
         */
        public Object getTransferData(DataFlavor flavor)
                throws UnsupportedFlavorException {
            if (DataFlavor.imageFlavor.equals(flavor)) {
                return image;
            }
            throw new UnsupportedFlavorException(flavor);
        }

        /**
         * �f�[�^��񋟂��邱�Ƃ��ł���t���[�o������ DataFlavor �I�u�W�F�N�g�̔z���Ԃ��܂��B
         */
        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[] { DataFlavor.imageFlavor };
        }

        /**
         * �w�肳�ꂽ�f�[�^�t���[�o���A���̃I�u�W�F�N�g�ɑ΂��ăT�|�[�g����Ă��邩�ǂ�����Ԃ��܂��B
         */
        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        /**
         * �N���b�v�{�[�h�̓��e�̃I�[�i�ł͂Ȃ��Ȃ������Ƃ��A���̃I�u�W�F�N�g�ɒʒm���܂��B
         */
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
            image = null;
        }
    }

    /**
     * �p�b�P�[�W�����������N���X����Ԃ��܂��B
     * 
     * @param cls �N���X
     * @return �p�b�P�[�W�����������N���X��
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
     * �I�����Ă���t�B�[���h�̒l���w��l�ɕύX���܂��B
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
                        // int�^�̉��
                        val = Integer.valueOf(componentFieldValue.getText());
                    } else if (String.class.equals(type)) {
                        // ������^�̉��
                        val = componentFieldValue.getText();
                    } else if (Boolean.class.equals(type)
                            || boolean.class.equals(type)) {
                        // �^�U�l�^�̉��
                        val = Boolean.valueOf(componentFieldValue.getText());
                    } else if (Long.class.equals(type)
                            || long.class.equals(type)) {
                        // �������̉��
                        val = Long.valueOf(componentFieldValue.getText());
                    } else if (Double.class.equals(type)
                            || double.class.equals(type)) {
                        // �{���x�����̉��
                        val = Double.valueOf(componentFieldValue.getText());
                    } else if (Float.class.equals(type)
                            || float.class.equals(type)) {
                        // �P���x�����̉��
                        val = Float.valueOf(componentFieldValue.getText());
                    } else {
                        // ��Ή�
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
     * ��ʍ��ڂ̑I���C�x���g���������܂��B
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
     * ��ʍ��ڂ��ċA�I�ɉ�͂��܂��B
     * 
     * @param parent ��ʃc���[�m�[�h
     * @param target ��ʍ���
     * @param addedComponents �ǉ��ς݂̃R���|�[�l���g�Z�b�g
     * @throws Exception ������O
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
     * ��ʍ��ڂ̃t�B�[���h���ċA�I�ɉ�͂��܂��B
     * 
     * @param parent ��ʃc���[�m�[�h
     * @param target ��ʍ���
     * @param addedComponents �ǉ��ς݂̃R���|�[�l���g�Z�b�g
     * @param fieldClass �t�B�[���h�̃N���X
     * @throws Exception ������O
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
                    //Map�̏ꍇ��entrySet
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
            // ��ʃN���X�̃t�B�[���h��ǉ�
            DefaultMutableTreeNode leaf = new DefaultMutableTreeNode(
                    parentClass);
            enumFieldAnalize(leaf, target, addedComponents, parentClass);
            parent.add(leaf);
        }
    }

    /**
     * ��ʈꗗ�̍X�V�C�x���g���������܂��B
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
     * ��ʍ��ڂ��ēǂݍ��݂��܂��B
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
     * ���������܂��B
     */
    public void clear() {
        windows.setModel(null);
        windowChildren.setModel(null);
        componentInfomation.setText("");
        componentFieldValue.setText("");
    }

    /**
     * �V�X�e���f�o�b�K�̉�ʈꗗ�p�Z�������_���ł��B
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
     * �V�X�e���f�o�b�K�̃t�B�[���h�ꗗ��`�ł��B
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
         * �R���X�g���N�^�ł��B
         */
        public ACStaticDebuggerComponentFieldContainer() {

        }

        /**
         * �Ώۃm�[�h��Ԃ��܂��B
         * 
         * @return �Ώۃm�[�h
         */
        public TreeNode getNode() {
            return node;
        }

        /**
         * �Ώۃm�[�h��ݒ肵�܂��B
         * 
         * @param node �Ώۃm�[�h
         */
        public void setNode(TreeNode node) {
            this.node = node;
        }

        public String toString() {

            if (getNode() != null) {
                int end = getNode().getChildCount();
                return "�t�B�[���h(" + end + ")";
            }
            return "";
        }
    }

    /**
     * �V�X�e���f�o�b�K�̃t�B�[���h��`�ł��B
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
         * �t�B�[���h�̏��L�҂�Ԃ��܂��B
         * 
         * @return �t�B�[���h�̏��L��
         */
        public Object getOwner() {
            return owner;
        }

        /**
         * �t�B�[���h�̏��L�҂�ݒ肵�܂��B
         * 
         * @param owner �t�B�[���h�̏��L��
         */
        public void setOwner(Object owner) {
            this.owner = owner;
        }

        /**
         * �R���X�g���N�^�ł��B
         * 
         * @param owner �t�B�[���h�̏��L��
         * @param field �t�B�[���h
         * @param value �l
         */
        public ACStaticDebuggerComponentField(Object owner, Field field,
                Object value) {
            setOwner(owner);
            setField(field);
            setValue(value);
        }

        /**
         * �t�B�[���h��Ԃ��܂��B
         * 
         * @return �t�B�[���h
         */
        public Field getField() {
            return field;
        }

        /**
         * �t�B�[���h��ݒ肵�܂��B
         * 
         * @param field �t�B�[���h
         */
        public void setField(Field field) {
            this.field = field;
        }

        /**
         * �l��Ԃ��܂��B
         * 
         * @return �l
         */
        public Object getValue() {
            return value;
        }

        /**
         * �l��ݒ肵�܂��B
         * 
         * @param value �l
         */
        public void setValue(Object value) {
            this.value = value;
        }

        public String toString() {
            return String.valueOf(getField());
        }
    }

    /**
     * �V�X�e���f�o�b�K�̉�ʍ��ڃZ�������_���ł��B
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
                value = "��ʃN���X : " + toSinmpleName(cls);
            } else if (value instanceof ACStaticDebuggerComponentFieldContainer) {
                ACStaticDebuggerComponentFieldContainer fieldContainer = (ACStaticDebuggerComponentFieldContainer) value;
                value = fieldContainer.toString();
            }
            return super.getTreeCellRendererComponent(tree, value, sel,
                    expanded, leaf, row, hasFocus);
        }
    }

}
