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

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTeikeibunEdit extends IkenshoDialog {

    private VRLabel header1 = new VRLabel();
    private VRLabel header2 = new VRLabel();
    private VRLabel header3 = new VRLabel();
    private VRPanel inputs = new VRPanel();
    // 2008/01/16 [Masahiko Higuchi] add - begin �ύX��I��Ή��i�X�R�[�v�͈͂̕ύX�j
    protected ACTable table = new ACTable();
    // 2008/01/16 [Masahiko Higuchi] add - end
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
    private ACButton otherAdd = new ACButton();

    // properties
    public static final int DISEASE = 0;
    public static final int TEIKEIBUN = 1;
    private int tableNo;
    private int kubun;
    private VRArrayList rows;
    private boolean modified = false;
    private ACTableModelAdapter model;
    private int length; // ��^���ő咷

    protected String tableName;
    protected String dataFieldName;
    protected String orderStatement;
    protected String whereStatement;
    protected String whereFieldName;
    
    // 2008/01/16 [Masahiko Higuchi] add - begin �ύX��I��Ή��i�ύX����Row�ێ��p�j
    protected int editRow = -1;
    // 2008/01/16 [Masahiko Higuchi] add - end

    protected boolean changed = false;

    /**
     * @deprecated ���̃R���X�g���N�^�͐�������܂���B IkenshoTeikeibunEdit(String title, int
     *             tableNo, int kubun, int length)���g�p���Ă��������B
     * @param title String
     * @param tableNo int
     * @param kubun int
     */
    public IkenshoTeikeibunEdit(String title, int tableNo, int kubun) {
        this(title, tableNo, kubun, 30);
    }

    /**
     * ���ڂ̒ǉ����������s���܂��B
     * 
     * @return �ǉ��ɐ���������
     */
    protected boolean doAdd() {
        // ���̓`�F�b�N
        String text = input.getText();
        if (IkenshoCommon.isNullText(text)) {
            return false;
        }

        try {
            if (indexOf(text) >= 0) {
                ACMessageBox.show("���̂��d�����Ă��܂��B", ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                return false;
            }

            // �ǉ�
            VRBindSource newRow = (VRBindSource) inputs.createSource();
            input.setSource(newRow);
            input.applySource();
            newRow.setData(whereFieldName, new Integer(getKubun()));
            rows.addData(newRow);

            table.revalidate();
            // table.getTable().revalidate();
        } catch (ParseException ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }

        // �㏈��
        table.clearSelection();
        modified = true;

        return true;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param title �^�C�g��
     * @param tableNo �e�[�u���ԍ�
     * @param kubun ��^�敪
     * @param length ��^���ő啶����
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
            init();
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

        // ��̐���
        table
                .setColumnModel(new VRTableColumnModel(
                        new ACTableColumn[] { new ACTableColumn(0, 450,
                                getTitle()), }));

        model = new ACTableModelAdapter(new VRArrayList(),
                new String[] { dataFieldName });
        table.setModel(model);
        doSelect();

        // �e�[�u���I����
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
                doAdd();
            }
        });

        otherAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doOtherAdd();
            }
        });

        edit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ���E�`�F�b�N
                int selRow = table.getSelectedModelRow();
                if (selRow < 0) {
                    return;
                }

                // ���̓`�F�b�N
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
                        ACMessageBox.show("���̂��d�����Ă��܂��B",
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);
                        return;
                    }

                    if (input.getSource() == null) {
                        return;
                    }

                    // �X�V
                    input.applySource();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }

                // �㏈��
                table.clearSelection();
                
                // 2008/01/16 [Masahiko Higuchi] add - begin �ύX��I��Ή�
                try{
                	// �I���s�̑ޔ�
                	editRow = selRow;
                	// �C�x���g���Ă�
                	addChangeAfter();
                }catch (Exception ex) {
                	return;
                }
                // 2008/01/16 [Masahiko Higuchi] add - end
                
                modified = true;
            }
        });

        delete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table.getSelectedModelRow();

                // ���E�`�F�b�N
                if (selRow < 0) {
                    return;
                }

                // ��Uinput���N���A
                input.setText("");

                // �ŏI�I���ʒu(���f���ł͂Ȃ���ʏ�)��ޔ�
                int lastSelectedIndex = table.getSelectedSortedRow();

                // �폜
                rows.remove(selRow);

                // �đI��
                table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

                // �㏈��
                modified = true;
            }
        });

        comit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // DB�X�V
                try {
                    updateData();
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }
                ACMessageBox.show("�X�V���܂����B");
                closeWindow();
            }
        });

        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // �E�B���h�E�����
                closeWindow();
            }
        });

        up.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table.getSelectedModelRow();

                // ���E�`�F�b�N
                if (selRow < 1) {
                    return;
                }

                // ����
                Object swap = rows.getData(selRow);
                rows.removeData(selRow);
                rows.add(selRow - 1, swap);
                table.setSelectedModelRow(selRow - 1);

                // �㏈��
                modified = true;
            }
        });

        down.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selRow = table.getSelectedModelRow();
                // ���E�`�F�b�N
                if ((selRow < 0) || (selRow > table.getRowCount() - 2)) {
                    return;
                }

                // ����
                Object swap = rows.getData(selRow);
                rows.removeData(selRow);
                rows.add(selRow + 1, swap);
                table.setSelectedModelRow(selRow + 1);

                // �㏈��
                modified = true;
            }
        });
        
        
       // 2007/10/05 [Masahiko Higuchi] 2007�N�x�Ή� Addition - begin
        addEvents();
       // 2007/10/05 [Masahiko Higuchi] Addition - begin
        
    }

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ������O
     */
    protected void jbInit() throws Exception {
        //FlowLayout buttonsLayout = new FlowLayout();
        //buttonsLayout.setAlignment(FlowLayout.RIGHT);
        // 2007/10/10 [Masahiko Higuchi] Replace - begin
        VRLayout buttonsLayout = new VRLayout();
        buttonsLayout.setAlignment(VRLayout.RIGHT);
        buttonsLayout.setAutoWrap(true);
        buttonsLayout.setHgap(5);
        buttonsLayout.setVgap(0);
        // 2007/10/10 [Masahiko Higuchi] Replace - end
        
        header.setLayout(new VRLayout());
        mover.setLayout(new VRLayout());
        buttons.setLayout(buttonsLayout);
        contents.setLayout(new BorderLayout());
        clients.setLayout(new BorderLayout());
        editors.setLayout(new BorderLayout());
        up.setToolTipText("�I������Ă��鍀�ڂ�1��ֈړ����܂��B");
        down.setToolTipText("�I������Ă��鍀�ڂ�1���ֈړ����܂��B");
        add.setToolTipText("�V�K�ɍ��ڂ�ǉ����܂��B");
        edit.setToolTipText("�I������Ă��鍀�ڂ�ύX���܂��B");
        delete.setToolTipText("�I������Ă��鍀�ڂ��폜���܂��B");
        comit.setToolTipText("�ύX���e��o�^���܂��B");
        close.setToolTipText("��ʂ���A���̉�ʂɖ߂�܂��B");
        otherAdd.setToolTipText("�ɂ��V�K�ɍ��ڂ�ǉ����܂��B");
        otherAdd.setVisible(false);
        editors.add(inputs, BorderLayout.NORTH);
        editors.add(buttons, BorderLayout.SOUTH);
        // 2007/10/18 [Masahiko Higuchi] Delete - begin
        //buttons.add(otherAdd, null);
        //buttons.add(add, null);
        //buttons.add(edit, null);
        //buttons.add(delete, null);
        //buttons.add(comit, null);
        //buttons.add(close, null);
        // 2007/10/18 [Masahiko Higuchi] Delete - end
        // 2007/10/18 [Masahiko Higuchi] Addition - begin
        buildButtonsPanel();
        // 2007/10/18 [Masahiko Higuchi] Addition - end        

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

        // �㕔�̒��ӏ���
        header1.setText("�u" + getTitle() + "�v�̕ҏW");
        header2.setText("[�ύX]��������[�폜]���������ڂ���⃊�X�g����I��ł��������B");
        header3.setText("[�ǉ�]�̎��͉��̃e�L�X�g�{�b�N�X�ɓ��͂���[�ǉ�]�������Ă��������B");

        // ���͗�
        inputNote.setText("���́E�ҏW�e�L�X�g�{�b�N�X");
        inputNote2.setText("(" + String.valueOf(length) + "�����ȓ�)");
        inputNote2.setForeground(Color.BLUE.brighter());
        input.setMaxLength(length);
        input.setIMEMode(InputSubset.KANJI);

        // �{�^�����̐ݒ�
        up.setText("���(P)");
        up.setMnemonic('P');
        down.setText("����(N)");
        down.setMnemonic('N');

        // �{�^�����̐ݒ�
        add.setText("�ǉ�(A)");
        add.setMnemonic('A');
        edit.setText("�ύX(U)");
        edit.setMnemonic('U');
        delete.setText("�폜(D)");
        delete.setMnemonic('D');
        comit.setText("�o�^(S)");
        comit.setMnemonic('S');
        close.setText("����(C)");
        close.setMnemonic('C');
        otherAdd.setText("�ɂ��ǉ�(O)");
        otherAdd.setMnemonic('O');

        table.setColumnSort(false);
        
        
        // 2007/10/05 [Masahiko Higuchi] �R���{����̒�^���ҏW�@�\ Addition - begin
        jbInitCustom();
        // 2007/10/05 [Masahiko Higuchi] Addition - end
        
    }

    /**
     * �ʒu�����������܂��B
     */
    private void init() {
        // �E�B���h�E�̃T�C�Y
        setSize(new Dimension(660, 400));
        // �E�B���h�E�𒆉��ɔz�u
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
     * ���[�_�����[�h�ŕ\�����A�ύX������������Ԃ��܂��B
     * 
     * @return boolean
     */
    public boolean showModal() {
        setVisible(true);
        // show();
        return changed;
    }

    /**
     * �Ώۂ̍��ڂ̈ʒu��Ԃ��܂��B
     * 
     * @param text �����Ώ�
     * @throws ParseException ��͗�O
     * @return ���݂���΂��̔ԍ��A���݂��Ȃ����-1
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
     * �R���X�g���N�^�ł��B
     */
    public IkenshoTeikeibunEdit() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // �E�B���h�E������ꂽ�Ƃ���Dispose����悤�ɃI�[�o�[���C�h
    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        } else {
            super.processWindowEvent(e);
        }
    }

    /**
     * DB����f�[�^���擾���A�e�[�u���ɐݒ肵�܂��B
     */
    protected void doSelect() {

        // �l�̐ݒ�
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

            // �X�V�t���O�̍X�V
            modified = false;
        } catch (Exception e) {
            IkenshoCommon.showExceptionMessage(e);
        }
    }

    /**
     * �e�[�u���ԍ���ݒ肵�܂��B
     * 
     * @param tableNo �e�[�u���ԍ�
     */
    protected void setTableNo(int tableNo) {
        this.tableNo = tableNo;
    }

    /**
     * ��^�敪��ݒ肵�܂��B
     * 
     * @param kubun ��^�敪
     */
    protected void setKubun(int kubun) {
        this.kubun = kubun;
    }

    /**
     * ��^�敪��Ԃ��܂��B
     * 
     * @return ��^�敪
     */
    protected int getKubun() {
        return kubun;
    }

    /**
     * �E�B���h�E����܂��B
     */
    protected void closeWindow() {
        // �m�F����
        if (modified) { // �ύX���R�~�b�g����Ă��Ȃ��ꍇ�Ɋm�F����
            switch (ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�ύX���e��o�^���܂����H",
                    "�o�^���Ė߂�(S)", 'S', "�j�����Ė߂�(R)", 'R')) {
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

        // ���g��j������
        this.dispose();
    }

    /**
     * �e�[�u���̕ύX���e��DB�ɔ��f���܂��B
     * 
     * @throws Exception ������O
     * @version 1.0 
     * @version 2.0 
     *  Masahiko Higuchi �X�R�[�v�͈͂�private����protected�ɏC��
     */
    protected void updateData() throws Exception {
        // DELETE��

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

            final String head = "INSERT INTO " + tableName + "(";
            final String middle = ") VALUES (";
            final String foot = ")";

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
                    // sb = new StringBuffer();
                    // sb.append(head);
                    // sb.append(" TKB_KBN");
                    // sb.append(",TEIKEIBUN");
                    // sb.append(",LAST_TIME");
                    // sb.append(middle);
                    // sb.append(IkenshoCommon.getDBSafeNumber("TKB_KBN", row));
                    // sb.append(",");
                    // sb.append(IkenshoCommon.getDBSafeString("TEIKEIBUN",
                    // row));
                    // sb.append(",CURRENT_TIMESTAMP");
                    // sb.append(foot);
                    // dbm.executeUpdate(sb.toString());
                    dbm.executeUpdate(createTeikeibunInsertSQL(row, head,
                            middle, foot));
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

    /**
     * ��^���e�[�u���ւ�INSERT�p��SQL����Ԃ��܂��B
     * 
     * @param row �l
     * @param head �O�uSQL
     * @param middle ���uSQL
     * @param foot ��uSQL
     * @return ��^���e�[�u���ւ�INSERT�p��SQL��
     * @throws ParseException ������O
     */
    protected String createTeikeibunInsertSQL(VRMap row, String head,
            String middle, String foot) throws ParseException {
        StringBuffer sb = new StringBuffer();
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
        return sb.toString();
    }

    //���̑��̕����̒�^���敪
    private int otherKubun;
//  ���̑��̕����̖���
    private String otherDocumentName;
    /**
     * ���̑��̕����ɂ���^���̓����ǉ������e���܂��B
     * @param otherKubun ���̑��̕����̒�^���敪
     * @param otherDocumentName ���̑��̕�����
     */
    public void setAllowedAddOtherDocument(int otherKubun, String otherDocumentName){
        this.otherKubun = otherKubun;
        this.otherDocumentName = otherDocumentName;
        otherAdd.setText(otherDocumentName+"�ɂ��ǉ�(O)");
                otherAdd.setToolTipText(otherDocumentName+"�ɂ��V�K�ɍ��ڂ�ǉ����܂��B");
        otherAdd.setVisible(true);
    }
    
    /**
     * ���̒�^���ɂ����ڂ̒ǉ����������s���܂��B
     * 
     * @return �ǉ��ɐ���������
     */
    protected boolean doOtherAdd() {
        if (doAdd()) {
            // ���̉�ʂւ̒ǉ��͐���

            IkenshoFirebirdDBManager dbm = null;
            try {
                dbm = new IkenshoFirebirdDBManager();
                dbm.beginTransaction();

                VRMap addRow = (VRMap)rows.get(rows.size()-1);
                VRMap row = new VRHashMap(addRow);
                VRBindPathParser.set("TKB_KBN", row, new Integer(otherKubun));
                
                
                // DELETE
                StringBuffer sb = new StringBuffer();
                sb.append("SELECT");
                sb.append(" * ");
                sb.append(" FROM ");
                sb.append(tableName);
                sb.append(" WHERE");
                sb.append(" (TKB_KBN=");
                sb.append(otherKubun);
                sb.append(" )AND(TEIKEIBUN=");
                sb.append(IkenshoCommon.getDBSafeString("TEIKEIBUN", row) );
                sb.append(")");

                VRList otherNow = dbm.executeQuery(sb.toString());
                if (!otherNow.isEmpty()) {
                    // �����̂�o�^�ς�
                    ACMessageBox.show("�u" +otherDocumentName+ "�v�ɂ͓o�^�ς݂̍��ڂł��B");
                    return false;
                }

                final String head = "INSERT INTO " + tableName + "(";
                final String middle = ") VALUES (";
                final String foot = ")";

                // INSERT
                switch (tableNo) {
                case TEIKEIBUN:
                        dbm.executeUpdate(createTeikeibunInsertSQL(row, head,
                                middle, foot));
                    break;
                }
                dbm.commitTransaction();

            } catch (Exception ex) {
                try {
                    if (dbm != null) {
                        dbm.rollbackTransaction();
                    }
                    IkenshoCommon.showExceptionMessage(ex);
                } catch (Exception ex2) {
                    IkenshoCommon.showExceptionMessage(ex2);
                }
            }
            if (dbm != null) {
                dbm.finalize();
            }

            return true;
        }

        return false;
    }

    /**
     * �I�v�V�����R���{��ʕύX�����p�B
     * 
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected void jbInitCustom() {
        
    }
    
    /**
     * �{�^���̈���擾���܂��B
     * 
     * @return �{�^���̈�
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected VRPanel getButtons() {
        return buttons;
    }
    
    /**
     * �o�^�{�^�����擾���܂��B
     * 
     * @return �o�^�{�^��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACButton getComit() {
        return comit;
    }
    
    /**
     * �p���悩��C�x���g��ǉ�����ۂɎg�p���܂��B
     * 
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected void addEvents(){
        
    }
    
    /**
     * �ꗗ���擾���܂��B
     * 
     * @return �ꗗ�e�[�u��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACTable getTable() {
        return table;
    }

    /**
     * �{�^���̈�̐������s���܂��B
     * 
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected void buildButtonsPanel(){
        buttons.add(otherAdd, null);
        buttons.add(add, null);
        buttons.add(edit, null);
        buttons.add(delete, null);
        buttons.add(comit, null);
        buttons.add(close, null);
    }
    
    
    /**
     * �ύX�������������`�F�b�N���܂��B
     * 
     * @return �ύX�̗L��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected boolean isModified() {
        return modified;
    }

    /**
     * �ǉ��{�^�� ��Ԃ��܂��B
     * @return �ǉ��{�^��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACButton getAdd() {
        return add;
    }

    /**
     * ����{�^�� ��Ԃ��܂��B
     * @return ����{�^��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACButton getClose() {
        return close;
    }

    /**
     * �폜�{�^�� ��Ԃ��܂��B
     * @return �폜�{�^��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACButton getDelete() {
        return delete;
    }

    /**
     * �ύX�{�^�� ��Ԃ��܂��B
     * @return �ύX�{�^��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACButton getEdit() {
        return edit;
    }

    /**
     * ���ɒǉ��{�^�� ��Ԃ��܂��B
     * @return ���ɒǉ��{�^��
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected ACButton getOtherAdd() {
        return otherAdd;
    }
    
    /**
     * �ύX�{�^��������̏���������
     * 
     * @throws Exception ������O
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected void addChangeAfter() throws Exception{
    	
    }
    
}
