package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.table.TableColumnModel;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRCheckBox;
import jp.nichicom.vr.component.VRComboBox;
import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.table.IkenshoSeikyushoHakkouKubunTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoSeikyushoHakkouKubunReserveFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** <HEAD_IKENSYO> */
public class IkenshoSeikyuIchiran extends IkenshoAffairContainer implements
        ACAffairable {

    // �ӌ����{�^���o�[
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    // �X�V�{�^��
    private ACAffairButton update = new ACAffairButton();
    // �����{�^��
    private ACAffairButton find = new ACAffairButton();
    // ����������{�^��
    private ACAffairButton print = new ACAffairButton();
    // �ꗗ����{�^��
    private ACAffairButton printList = new ACAffairButton();
    // ���������A�e�[�u���ݒ�p�p�l��
    private VRPanel client = new VRPanel();
    // ���ʕ\���p�e�[�u��
    private ACTable table = new ACTable();

    // //���������E�������ʂ̕ύX�{�^���p�l��
    // private VRPanel searchPnl = new VRPanel();
    // ���������p�l��
    private ACGroupBox searchGrp = new ACGroupBox();
    // �������ʕύX�{�^���p�l��
    private VRPanel searchButtonPnl = new VRPanel();

    // �ی��Җ��̃R���{�{�b�N�X
    private VRComboBox hokenjyaCombo = new VRComboBox();
    private ACLabelContainer hokenjyaContainer = new ACLabelContainer();
    // ��t���̃R���{�{�b�N�X
    private VRComboBox doctorCombo = new VRComboBox();
    private ACLabelContainer doctorContainer = new ACLabelContainer();
    // ���Ԏw��R���{�{�b�N�X
    private VRComboBox taisyoDayCombo = new VRComboBox();
    private IkenshoEraDateTextField taisyoKikanFrom = new IkenshoEraDateTextField();
    private JLabel taisyoKikanFromLabel = new JLabel();
    private IkenshoEraDateTextField taisyoKikanTo = new IkenshoEraDateTextField();
    private JLabel taisyoKikanToLabel = new JLabel();
    private ACLabelContainer taisyoDayContainer = new ACLabelContainer();
    // ���t�����{�^��
    private ACButton clearDate = new ACButton();
    // ���s�ς݂��܂߂�`�F�b�N�{�b�N�X
    private VRCheckBox hakkozumiCheck = new VRCheckBox();

    // �����s�{�^��
    private ACButton mihakko = new ACButton();
    // �ۗ��{�^��
    private ACButton horyu = new ACButton();
    // ���s�ς݃{�^��
    private ACButton hakkozumi = new ACButton();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem mihakkoMenu = new JMenuItem();
    private JMenuItem horyuMenu = new JMenuItem();
    private JMenuItem hakkozumiMenu = new JMenuItem();

    // �e�[�u���\���p�f�[�^�I�u�W�F�N�g
    private VRArrayList data;
    // �ی��҃��X�g
    private VRArrayList hokenjyaList;
    // ��t���̃��X�g
    private VRArrayList doctorList;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO", "EDA_NO" }, new Format[] {
                    null, null }, "LAST_TIME", "LAST_TIME");

    private ACTableModelAdapter tableModel;
    private IkenshoSeikyushoHakkouKubunTableCellRenderer tableCellRenderer = new IkenshoSeikyushoHakkouKubunTableCellRenderer(
            "HAKKOU_KBN", "HAKKOU_KBN_ORIGIN");

    private static String separator = System.getProperty("file.separator");
    private static DecimalFormat dayFormat = new DecimalFormat("00");
    private static NumberFormat costFormat = NumberFormat.getInstance();

    // �ӌ����쐬�萔
    public static final int IKEN_PRINT = 1;
    // �w�����쐬�萔
    public static final int KENSA_PRINT = 2;
    // �ӌ����E�w�����쐬�萔
    public static final int BOTH_PRINT = 3;

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoSeikyuIchiran() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        clearDate
                .addActionListener(new IkenSeikyuIchiran_clearDate_actionAdapter(
                        this));
        hakkozumi
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        horyu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        mihakko
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        hakkozumiMenu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        horyuMenu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));
        mihakkoMenu
                .addActionListener(new IkenSeikyuIchiran_changeStatus_actionAdapter(
                        this));

        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        // -----�{�^���ݒ�
        buttons.setTitle("�����Ώۈӌ����ꗗ");
        // �X�V�{�^��
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);
        update.setMnemonic('S');
        update.setText("�X�V(S)");
        update.setToolTipText("�{��ʂŕύX���ꂽ����󋵂�ۑ����܂��B");
        // �����{�^��
        find.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_FIND);
        find.setMnemonic('V');
        find.setText("����(V)");
        find.setToolTipText("���ݓ��͂���Ă�������ɂ��A�ꗗ��\�����܂��B");
        // �������{�^��
        print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_PRINT);
        print.setMnemonic('P');
        print.setText("������(P)");
        print.setToolTipText("���X�g�ɕ\������Ă���ӌ����ɂ��āu�������v��������܂��B");
        // �ꗗ����{�^��
        printList.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_TABLE_PRINT);
        printList.setMnemonic('L');
        printList.setText("�ꗗ���(L)");
        printList.setToolTipText("�����Ώۈӌ����ꗗ�\��������܂��B");

        buttons.add(printList, VRLayout.EAST);
        buttons.add(print, VRLayout.EAST);
        buttons.add(find, VRLayout.EAST);
        buttons.add(update, VRLayout.EAST);
        // ---�{�^���ݒ�

        // --�\���̈�ݒ�
        client.setLayout(new VRLayout());
        client.add(searchGrp, VRLayout.NORTH);
        client.add(searchButtonPnl, VRLayout.NORTH);
        client.add(table, VRLayout.CLIENT);
        // --�\���̈�ݒ�

        // ---���������ݒ�
        searchGrp.setText("�\������");
        VRLayout searchGrpLayout = new VRLayout();
        searchGrpLayout.setHgap(0);
        // searchGrpLayout.setFitHLast(true);
        searchGrp.setLayout(searchGrpLayout);
        // �ی��ґI���R���{�{�b�N�X
        hokenjyaContainer.setText("�˗����i�ی��ҁj");
        hokenjyaCombo.setEditable(false);
        hokenjyaCombo.setPreferredSize(new Dimension(250, 20));
        hokenjyaCombo.setBindPath("hokenjya");
        hokenjyaContainer.add(hokenjyaCombo, null);
        // ��t�����R���{�{�b�N�X
        doctorContainer.setText("��t����");
        doctorCombo.setEditable(false);
        doctorCombo.setPreferredSize(new Dimension(200, 20));
        doctorCombo.setBindPath("doctroName");
        doctorContainer.add(doctorCombo, null);

        // ���Ԏw��
        ACLabelContainer panel1 = new ACLabelContainer();
        VRLayout layout1 = new VRLayout();
        layout1.setHgap(0);
        layout1.setVgap(0);
        panel1.setLayout(layout1);
        panel1.setText("�w��Ώ�");
        taisyoDayCombo.setEditable(false);
        taisyoDayCombo.addItem("�L����");
        taisyoDayCombo.addItem("���t��");
        taisyoDayCombo.setBindPath("taisyoDay");
        panel1.add(taisyoDayCombo, null);

        ACLabelContainer panel2 = new ACLabelContainer();
        VRLayout layout2 = new VRLayout();
        layout2.setHgap(0);
        layout2.setVgap(0);
        panel2.setLayout(layout2);
        panel2.setText(" �J�n���t ");
        // panel2.add(taisyoKikanFromLabel, VRLayout.FLOW);
        taisyoKikanFrom.setAgeVisible(false);
        taisyoKikanFrom.setAllowedFutureDate(true);
        taisyoKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoKikanFrom.setEra("����");
        taisyoKikanFrom.setBindPath("taisyoKikanFrom");
        panel2.add(taisyoKikanFrom, null);

        ACLabelContainer panel3 = new ACLabelContainer();
        VRLayout layout3 = new VRLayout();
        layout3.setHgap(0);
        layout3.setVgap(0);
        panel3.setLayout(layout3);
        panel3.setText("�I�����t ");
        taisyoKikanToLabel.setText(" �` ");
        // panel3.add(taisyoKikanToLabel, VRLayout.FLOW);
        taisyoKikanTo.setAgeVisible(false);
        taisyoKikanTo.setAllowedFutureDate(true);
        taisyoKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoKikanTo.setEra("����");
        taisyoKikanTo.setBindPath("taisyoKikanTo");
        panel3.add(taisyoKikanTo, null);

        VRLayout taisyoDayContainerLayout = new VRLayout();
        taisyoDayContainerLayout.setAutoWrap(false);
        taisyoDayContainerLayout.setHgap(0);
        taisyoDayContainerLayout.setVgap(0);
        taisyoDayContainerLayout.setVAlignment(VRLayout.CENTER);
        taisyoDayContainer.setLayout(taisyoDayContainerLayout);
        taisyoDayContainer.add(panel1, VRLayout.FLOW);
        taisyoDayContainer.add(panel2, VRLayout.FLOW);
        taisyoDayContainer.add(taisyoKikanToLabel, VRLayout.FLOW);
        taisyoDayContainer.add(panel3, VRLayout.FLOW);

        clearDate.setText("���t����(E)");
        clearDate.setToolTipText("���͂��ꂽ���t���������܂��B");
        clearDate.setMnemonic('E');
        // clearDate.setPreferredSize(new Dimension(110, 22));

        taisyoDayContainer
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        taisyoDayContainer
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        taisyoDayContainer.setContentAreaFilled(true);

        taisyoDayContainer.add(clearDate, VRLayout.FLOW);

        hakkozumiCheck.setText("���������s�ς݂́u�ӌ����v���\������B");
        hakkozumiCheck.setBindPath("hakkozumiCheck");
        ACLabelContainer panelCheck = new ACLabelContainer();
        panelCheck.add(hakkozumiCheck, null);

        searchGrp.add(hokenjyaContainer, VRLayout.FLOW_INSETLINE);
        searchGrp.add(doctorContainer, VRLayout.FLOW_RETURN);
        searchGrp.add(taisyoDayContainer, VRLayout.FLOW_INSETLINE_RETURN);
        searchGrp.add(panelCheck, VRLayout.FLOW_INSETLINE_RETURN);
        // ---���������ݒ�

        // ---���s��ԕύX�{�^��
        searchButtonPnl.setLayout(new VRLayout());
        // ���s�σ{�^��
        hakkozumi.setMnemonic('T');
        hakkozumi.setText("�����s��(T)");
        hakkozumi.setToolTipText("�I�����ꂽ�������̈���󋵂��u���s�ρv�ɂ��܂��B");
        searchButtonPnl.add(hakkozumi, VRLayout.EAST);
        // �ۗ��{�^��
        horyu.setMnemonic('H');
        horyu.setText("�ۗ�(H)");
        horyu.setToolTipText("�I�����ꂽ�������̈���󋵂��u�ۗ��v�ɂ��܂��B");
        searchButtonPnl.add(horyu, VRLayout.EAST);
        // �����s�{�^��
        mihakko.setMnemonic('F');
        mihakko.setText("�����s(F)");
        mihakko.setToolTipText("�I�����ꂽ�������̈���󋵂��u�����s�v�ɂ��܂��B");
        searchButtonPnl.add(mihakko, VRLayout.EAST);

        // ���j���[
        mihakkoMenu.setToolTipText(mihakko.getToolTipText());
        mihakkoMenu.setMnemonic(mihakko.getMnemonic());
        mihakkoMenu.setText(mihakko.getText());
        horyuMenu.setToolTipText(horyu.getToolTipText());
        horyuMenu.setMnemonic(horyu.getMnemonic());
        horyuMenu.setText(horyu.getText());
        hakkozumiMenu.setToolTipText(hakkozumi.getToolTipText());
        hakkozumiMenu.setMnemonic(hakkozumi.getMnemonic());
        hakkozumiMenu.setText("���s��(T)");

        popup.add(mihakkoMenu);
        popup.add(horyuMenu);
        popup.add(hakkozumiMenu);
        table.setPopupMenu(popup);
        table.setPopupMenuEnabled(true);

        // ---���s��ԕύX�{�^��
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // ---���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        // �X�V�{�^���̊֘A�t��
        addUpdateTrigger(update);
        // �����{�^���̊֘A�t��
        addFindTrigger(find);
        // ����{�^���̊֘A�t��
        addPrintTrigger(print);
        // �ꗗ����{�^���̊֘A�t��
        addPrintTableTrigger(printList);

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

        // ---�R���{�{�b�N�X���f�[�^�̏�����
        // �ی��҃R���{�{�b�N�X
        hokenjyaList = (VRArrayList) dbm
                .executeQuery("SELECT INSURER_NO,INSURER_NM FROM INSURER ORDER BY INSURER_NM");
        setComboModel(hokenjyaCombo, hokenjyaList, "INSURER_NM");
        if (hokenjyaCombo.getItemCount() == 1) {
            hokenjyaCombo.setSelectedIndex(0);
        }
        // ��t���R���{�{�b�N�X
        doctorList = (VRArrayList) dbm
                .executeQuery("SELECT DISTINCT DR_NM FROM DOCTOR ORDER BY DR_NM");
        // �󔒍s��ǉ�
        VRMap spaceMap = new VRHashMap();
        spaceMap.setData("DR_NM", "");
        doctorList.add(0, spaceMap);
        setComboModel(doctorCombo, doctorList, "DR_NM");

        data = new VRArrayList();
        tableModel = new ACTableModelAdapter(data, new String[] { "HAKKOU_KBN",
                "PATIENT_NM", "AGE", "INSURED_NO", "DR_NM", "REQ_DT",
                "KINYU_DT", "SEND_DT" });

        // �e�[�u���̐���
        table.setModel(tableModel);

        // ColumnModel�̐���
        table
                .setColumnModel(new VRTableColumnModel(
                        new ACTableColumn[] {
                                new ACTableColumn(0, 50, "���s��",
                                        SwingConstants.CENTER,
                                        IkenshoSeikyushoHakkouKubunReserveFormat
                                                .getInstance()),
                                new ACTableColumn(1, 180, "����",
                                        tableCellRenderer, null),
                                new ACTableColumn(2, 50, "�N��",
                                        SwingConstants.RIGHT,
                                        tableCellRenderer, null),
                                new ACTableColumn(3, 120, "��ی��Ҕԍ�",
                                        tableCellRenderer, null),
                                new ACTableColumn(4, 150, "��t����",
                                        tableCellRenderer, null),
                                new ACTableColumn(5, 120, "�쐬�˗���",
                                        IkenshoConstants.FORMAT_ERA_YMD,
                                        tableCellRenderer, null),
                                new ACTableColumn(6, 120, "�ӌ����L����",
                                        IkenshoConstants.FORMAT_ERA_YMD,
                                        tableCellRenderer, null),
                                new ACTableColumn(7, 120, "�ӌ������t��",
                                        IkenshoConstants.FORMAT_ERA_YMD,
                                        tableCellRenderer, null) }));

        TableColumnModel colMdl = table.getColumnModel();
        // VRTableColumnModel colMdl = table.getColumnModel();
        for (int i = 0; i < colMdl.getColumnCount(); i++) {
            colMdl.getColumn(i).setCellRenderer(null);
        }

        table.setDefaultRenderer(Object.class, tableCellRenderer);
        // table.getTable().setDefaultRenderer(Object.class, tableCellRenderer);

        setButtonsEnabled();
        // �X�e�[�^�X�o�[
        setStatusText("�����Ώۈӌ����ꗗ");

        // �X�i�b�v�V���b�g�Ώېݒ�
        IkenshoSnapshot.getInstance().setRootContainer(searchGrp);
    }

    /**
     * VRArrayList�̔C�ӂ̗�̒l��ComboBox�ɐݒ肵�܂��B
     *
     * @param combo VRComboBox
     * @param tbl VRArrayList
     * @param field String
     */
    private void setComboModel(JComboBox combo, VRArrayList tbl, String field) {
        try {
            if (tbl.size() > 0) {
                combo.setModel(new ACComboBoxModelAdapter(
                        new VRHashMapArrayToConstKeyArrayAdapter(tbl, field)));
            }
        } catch (Exception e) {
            IkenshoCommon.showExceptionMessage(e);
        }
    }

    public boolean canBack(VRMap parameters) throws Exception {
        // �X�V�{�^���������s�ł���΁A�������s�킸�߂�B
        if (!update.isEnabled()) {
            return true;
        }
        int result = ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�ۑ����܂����H",
                "�X�V���Ė߂�(S)", 'S', "�j�����Ė߂�(R)", 'R');

        // DLG����
        // �ۑ����Ė߂�I����
        if (result == ACMessageBox.RESULT_YES) {
            return doUpdate(); // DB�X�V����:true, ���s:false
            // �ۑ����Ȃ��Ŗ߂�I����
        } else if (result == ACMessageBox.RESULT_NO) {
            return true;
            // �L�����Z���I����
        } else {
            return false;
        }
    }

    public boolean canClose() throws Exception {
        if (update.isEnabled()) {
            return ACMessageBox.show("�X�V���ꂽ���e�͔j������܂��B\n�I�����Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK;
        }
        return true;
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return table;
    }

    /**
     * �u�����v�������̃C�x���g�ł��B
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void findActionPerformed(ActionEvent e) throws Exception {
        // �������ړ��͂̑Ó����`�F�b�N
        if (!isValidInput()) {
            return;
        }
        // �������s
        doSelect(true);
    }

    /**
     * �u�X�V�v�������̃C�x���g�ł�
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (ACMessageBox.show("�X�V���܂��B��낵���ł����H", ACMessageBox.BUTTON_OKCANCEL,
                ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK) == ACMessageBox.RESULT_CANCEL) {
            return;
        }
        // �X�V
        if (doUpdate()) {
            // �Č���
            doSelect(true);
        }
    }

    /**
     * �ꗗ����������̃C�x���g�ł��B
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        // �����������ύX���ꂽ�ꍇ�A���b�Z�[�W��\�����A����𒆒f����
        if (IkenshoSnapshot.getInstance().isModified()) {
            ACMessageBox.show("�����������ύX����܂����B\n���������ɖ߂����A�ēx���������ĉ������B");
            return;
        }

        // �f�[�^�̕ύX�`�F�b�N
        if (isChangeData()) {
            if (ACMessageBox.show("�ύX����Ă��܂��B�ۑ����Ă���낵���ł���",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_OK) {
                // �X�V���s
                if (doUpdate()) {
                    // �Č���
                    doSelect(true);
                } else {
                    return;
                }
            } else {
                return;
            }
        }

        if (ACMessageBox.showOkCancel("�����Ώۈӌ����ꗗ�̈��",
                "�ꗗ�\���o�͂��Ă���낵���ł����H\n�i��ی��Ҕԍ����Ɉ������܂��j", "���(O)", 'O') != ACMessageBox.RESULT_OK) {
            return;
        }
        // ������s
        doPrintList();
    }

    private void doPrintList() throws Exception {
        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        int endRow = data.getDataSize();
        // �y�[�W���̃J�E���g
        int page = (int) Math.ceil((double) endRow / 21);
        int row = 1;

        // ����f�[�^�J�n�ݒ�錾
        pd.beginPrintEdit();

        String path = ACFrame.getInstance().getExeFolderPath() + separator
                + "format" + separator + "SeikyuIkenshoIchiran.xml";
//        pd.addFormat("daicho", path);
        ACChotarouXMLUtilities.addFormat(pd, "daicho", "SeikyuIkenshoIchiran.xml");

        for (int i = 0; i < page; i++) {
            pd.beginPageEdit("daicho");
            // �e�y�[�W���̐ݒ�
            // �ی���
            IkenshoCommon.addString(pd, "hokensya", getSelectedCboData(
                    hokenjyaCombo, hokenjyaList, "INSURER_NM")
                    + "�@("
                    + getSelectedCboData(hokenjyaCombo, hokenjyaList,
                            "INSURER_NO") + ")");
            // �o�͕���
            IkenshoCommon.addString(pd, "dateRange.data.title", taisyoDayCombo
                    .getSelectedItem().toString()
                    + "�F");
            // ���t���ԊJ�n
            if (isTaisyoKikanInput(taisyoKikanFrom)) {
                Date date = taisyoKikanFrom.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.fromY",
                        VRDateParser.format(date, "ggg�@ee"));
                IkenshoCommon.addString(pd, "dateRange.data.fromM",
                        VRDateParser.format(date, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.fromD",
                        VRDateParser.format(date, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.fromY",
                // taisyoKikanFrom.getEra() + " " +
                // dayFormat.format(Long.parseLong(taisyoKikanFrom.getYear())));
                // IkenshoCommon.addString(pd, "dateRange.data.fromM",
                // dayFormat.format(Long.parseLong(taisyoKikanFrom.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.fromD",
                // dayFormat.format(Long.parseLong(taisyoKikanFrom.getDay())));
            }
            // ���t���ԏI��
            if (isTaisyoKikanInput(taisyoKikanTo)) {
                Date date = taisyoKikanTo.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.toY", VRDateParser
                        .format(date, "ggg�@ee"));
                IkenshoCommon.addString(pd, "dateRange.data.toM", VRDateParser
                        .format(date, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.toD", VRDateParser
                        .format(date, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.toY",
                // taisyoKikanTo.getEra() + " " +
                // dayFormat.format(Long.parseLong(taisyoKikanTo.getYear())));
                // IkenshoCommon.addString(pd, "dateRange.data.toM",
                // dayFormat.format(Long.parseLong(taisyoKikanTo.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.toD",
                // dayFormat.format(Long.parseLong(taisyoKikanTo.getDay())));
            }

            for (int j = 0; (j < 21) && (row < endRow + 1); j++, row++) {
                VRMap map = (VRMap) data.getData(row - 1);
                // ������
                switch (Integer.parseInt(VRBindPathParser
                        .get("HAKKOU_KBN", map).toString())) {
                case 1:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "���o��");
                    break;
                case 2:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "�o�͍�");
                    break;
                case 3:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "�ۗ�");
                    break;
                default:
                    IkenshoCommon.addString(pd, getHeader(j + 1)
                            + ".HAKKOU_KBN", "-");
                    break;
                }
                // ����
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".PATIENT_NM",
                        getString("PATIENT_NM", map));
                // �ӂ肪��
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".PATIENT_KN",
                        getString("PATIENT_KN", map));
                // ����
                switch (((Integer) VRBindPathParser.get("SEX", map)).intValue()) {
                case 1:
                    IkenshoCommon
                            .addString(pd, getHeader(j + 1) + ".SEX", "�j��");
                    break;
                case 2:
                    IkenshoCommon
                            .addString(pd, getHeader(j + 1) + ".SEX", "����");
                    break;
                }
                // �N��
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".AGE",
                        getString("AGE", map));
                // ���N����
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".BIRTHDAY",
                        IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                                .get("BIRTHDAY", map)));
                // ��ی��Ҕԍ�
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".INSURED_NO",
                        getString("INSURED_NO", map));
                // ��t��
                IkenshoCommon.addString(pd, getHeader(j + 1) + ".DR_NM",
                        getString("DR_NM", map));

                Object irai = VRBindPathParser.get("REQ_DT", map);
                if (irai != null) {
                    IkenshoCommon.addString(pd, getHeader(j + 1) + ".REQ_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(irai));
                }
                // �L����
                Object kinyu = VRBindPathParser.get("KINYU_DT", map);
                if (kinyu != null) {
                    IkenshoCommon.addString(pd, getHeader(j + 1) + ".KINYU_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(kinyu));
                }
                // �ŐV�w�����L����
                Object soufu = VRBindPathParser.get("SEND_DT", map);
                if (soufu != null) {
                    IkenshoCommon.addString(pd, getHeader(j + 1) + ".SEND_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(soufu));
                }
            }
            pd.endPageEdit();
        }

        pd.endPrintEdit();

        // writePDF(pd);
        // openPDF();
        openPDF(pd);

    }

    private static String getString(String key, VRMap map) throws Exception {
        Object obj = VRBindPathParser.get(key, map);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    private String getHeader(int row) {
        return "table.h" + Integer.toString(row);
    }

    private String getSelectedCboData(VRComboBox combo, VRArrayList ary,
            String key) {
        return ((VRMap) ary.getData(combo.getSelectedIndex())).getData(key)
                .toString();
    }

    /**
     * ������������s
     *
     * @param e ActionEvent
     * @throws Exception
     */
    protected void printActionPerformed(ActionEvent e) throws Exception {
        IkenshoSeikyuPrintSetting seikyuPrtSet = new IkenshoSeikyuPrintSetting();
        // �����������ύX���ꂽ�ꍇ�A���b�Z�[�W��\�����A����𒆒f����
        if (IkenshoSnapshot.getInstance().isModified()) {
            ACMessageBox.show("�����������ύX����܂����B\n���������ɖ߂����A�ēx���������ĉ������B");
            return;
        }
        // �f�[�^�̕ύX�`�F�b�N
        if (isChangeData()) {
            if (ACMessageBox.show("�ύX����Ă��܂��B�ۑ����Ă���낵���ł���",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_OK) {
                // �X�V���s
                if (doUpdate()) {
                    // �Č���
                    doSelect(true);
                } else {
                    return;
                }
            } else {
                return;
            }
        }
        seikyuPrtSet.setModal(true);
        seikyuPrtSet.setResizable(false);
        // ����ݒ�_�C�A���O��\��
        if (seikyuPrtSet.showDialog(getSelectedCboData(hokenjyaCombo,
                hokenjyaList, "INSURER_NO")) == IkenshoSeikyuPrintSetting.BUTTON_CANCEL) {
            // �L�����Z�����������ꂽ�ꍇ�́A�������~
            return;
        }
        // �f�[�^�ύX�̊m�F
        if (ACMessageBox.show("������s���ƁA�Y���u�ӌ����v�̐������́u���s�ς݁v�ɕύX����܂��B\n��낵���ł����H",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
            // �L�����Z�����������ꂽ�ꍇ�́A�������~
            return;
        }
        // ����t���O�̍X�V�ƁAPDF�쐬����(�P�g�����U�N�V����)
        if (!doSeikyuPrint(seikyuPrtSet)) {
            ACMessageBox.showExclamation("����Ɏ��s���܂����B");
        }

    }

    /**
     * �������̂̔��s���s���܂��B
     *
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @return boolean
     * @exception Exception
     */
    private boolean doSeikyuPrint(IkenshoSeikyuPrintSetting seikyuPrtSet)
            throws Exception {
        // ����p�f�[�^�ێ��I�u�W�F�N�g
        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        // ��Ë@�փf�[�^�ێ��I�u�W�F�N�g
        VRMap doctorData = null;
        // �����f�[�^
        VRArrayList seikyuData = new VRArrayList();
        // ���z�f�[�^
        HashMap costData = null;
        VRMap map = null;
        IkenshoFirebirdDBManager dbm = null;

        int out_mode = 0;
        int sokatu_select = 0;
        boolean sokatu_check = false;
        int meisai_select = 0;
        boolean meisai_check = false;

        try {

            // �����̃A�b�v�f�[�g
            dbm = doUpdateAll();
            if (dbm == null) {
                return false;
            }
            // PDF�t�@�C���̍쐬
            // ��Ë@�֎擾
            doctorData = getDefaultDoctor(dbm, getSelectedCboData(
                    hokenjyaCombo, hokenjyaList, "INSURER_NO"));
            if (doctorData == null) {
                dbm.rollbackTransaction();
                return false;
            }

            // ���������s�Ώۃf�[�^�𒊏o
            for (int i = 0; i < data.getDataSize(); i++) {
                map = (VRMap) data.getData(i);
                if (map.getData("HAKKOU_KBN").toString().equals("1")) {
                    seikyuData.add(map);
                }
            }

            // ���v���z�Ȃǂ��擾
            costData = getCostData(seikyuData);

            // ����J�n�錾
            pd.beginPrintEdit();

            // �e��`�̂�ݒ�
            String dir = ACFrame.getInstance().getExeFolderPath() + separator
                    + "format" + separator;
            ACChotarouXMLUtilities.addFormat(pd, "seikyu", "Soukatusho.xml");
            ACChotarouXMLUtilities.addFormat(pd, "ichiran", "SeikyuIchiran.xml");
            ACChotarouXMLUtilities.addFormat(pd, "ichiranTotal", "SeikyuIchiranTotal.xml");
            ACChotarouXMLUtilities.addFormat(pd, "syosai", "IkenshoMeisai.xml");
//            pd.addFormat("seikyu", dir + "Soukatusho.xml");
//            pd.addFormat("ichiran", dir + "SeikyuIchiran.xml");
//            pd.addFormat("ichiranTotal", dir + "SeikyuIchiranTotal.xml");
//            pd.addFormat("syosai", dir + "IkenshoMeisai.xml");

            out_mode = IKEN_PRINT;
            switch (seikyuPrtSet.getOutputPattern()) {
            case 1:
                out_mode = BOTH_PRINT;
            // no break;
            case 2:
            // no break;
            case 3:
                sokatu_select = seikyuPrtSet.createSummaryRadio
                        .getSelectedIndex();
                sokatu_check = seikyuPrtSet.createSummaryPrint.isSelected();
                meisai_select = seikyuPrtSet.createDetailsRadio
                        .getSelectedIndex();
                meisai_check = seikyuPrtSet.createDetailsPrint.isSelected();
                break;
            case 4:
                out_mode = KENSA_PRINT;
                sokatu_select = seikyuPrtSet.inspectionSummaryRadio
                        .getSelectedIndex();
                sokatu_check = seikyuPrtSet.inspectionSummaryPrint.isSelected();
                meisai_select = seikyuPrtSet.inspectionDetailsRadio
                        .getSelectedIndex();
                meisai_check = seikyuPrtSet.inspectionDetailsPrint.isSelected();
                break;
            }
            // �������o�͐ݒ�ŁA�����̌�����0���ł���΁A�������s��Ȃ��B
            if ((seikyuPrtSet.getOutputPattern() != 4)
                    || (!costData.get("KENSA_COUNT").toString().equals("0"))) {
                // ����
                if (sokatu_select == 1) {
                    setSokatuPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, out_mode, sokatu_check);
                }
                // �ꗗ
                if ((meisai_select & 2) == 2) {
                    setIchiranPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, out_mode, meisai_check);
                }
                // ����
                if ((meisai_select & 1) == 1) {
                    setSyosaiPrtDataAll(pd, seikyuData, seikyuPrtSet, out_mode,
                            meisai_check);
                }
            }
            //
            if ((seikyuPrtSet.getOutputPattern() == 2)
                    && (!costData.get("KENSA_COUNT").toString().equals("0"))) {
                // ����
                if (seikyuPrtSet.inspectionSummaryRadio.getSelectedIndex() == 1) {
                    setSokatuPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, KENSA_PRINT,
                            seikyuPrtSet.inspectionSummaryPrint.isSelected());
                }
                // �ꗗ
                if ((seikyuPrtSet.inspectionDetailsRadio.getSelectedIndex() & 2) == 2) {
                    setIchiranPrtData(pd, seikyuData, seikyuPrtSet, doctorData,
                            costData, KENSA_PRINT, meisai_check);
                }
                // ����
                if ((seikyuPrtSet.inspectionDetailsRadio.getSelectedIndex() & 1) == 1) {
                    setSyosaiPrtDataAll(pd, seikyuData, seikyuPrtSet,
                            KENSA_PRINT, meisai_check);
                }
            }

            pd.endPrintEdit();

            if (!costData.get("KENSA_COUNT").toString().equals("0")
                    || (seikyuPrtSet.getOutputPattern() != 4)) {
                // writePDF(pd);
                // openPDF();
                openPDF(pd);
            }

            dbm.commitTransaction();
        } catch (Exception e) {
            dbm.rollbackTransaction();
            throw e;
        }

        if (costData.get("KENSA_COUNT").toString().equals("0")) {
            // �������݂̂̒��[���o�͂����\��������ꍇ
            // 2:�ӌ���(1��) ������(1��)
            // 4:�������̂�
            if (seikyuPrtSet.getOutputPattern() == 2
                    || seikyuPrtSet.getOutputPattern() == 4) {
                ACMessageBox.show("�f�@�E������p�����ΏۂƂȂ�ӌ���������܂���ł����B");
            }
        }

        doSelect(false);
        return true;
    }

    /**
     * �ӌ����쐬�������̈���f�[�^���쐬���܂��B <BR>
     *
     * @param pd PrintData
     * @param seikyuData VRArrayList
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @param doctorData VRHashMap
     * @param costData HashMap
     * @param printMode int
     * @param bankCheck boolean
     * @return boolean
     * @throws Exception
     */
    private boolean setSokatuPrtData(ACChotarouXMLWriter pd,
            VRArrayList seikyuData, IkenshoSeikyuPrintSetting seikyuPrtSet,
            VRMap doctorData, HashMap costData, int printMode,
            boolean bankCheck) throws Exception {

        // ����f�[�^�J�n�ݒ�錾
        pd.beginPageEdit("seikyu");
        // ���ԊJ�n
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeFrom)) {
            IkenshoCommon.addString(pd, "dateRange.h1.fromY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeFrom));
        }
        // ���ԏI��
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeTo)) {
            IkenshoCommon.addString(pd, "dateRange.h1.toY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeTo));
        }
        // �o�͓�
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDate)) {
            IkenshoCommon.addString(pd, "prtDate.h1.era",
                    seikyuPrtSet.billPrintDate.getEra());
            IkenshoCommon.addString(pd, "prtDate.h1.y",
                    getDayFormat(seikyuPrtSet.billPrintDate.getYear()));
            IkenshoCommon.addString(pd, "prtDate.h1.m",
                    getDayFormatMonth(seikyuPrtSet.billPrintDate));
            IkenshoCommon.addString(pd, "prtDate.h1.d",
                    getDayFormatDay(seikyuPrtSet.billPrintDate));
        }
        // ����
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCreateCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCreateCost.getText() + "�@�l");
        } else if (printMode == KENSA_PRINT) {
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCheckCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCheckCost.getText() + "�@�l");
        }

        // ��Ë@�֏Z��
        IkenshoCommon.addString(pd, "hospAdd.address.w2", getString(
                "MI_ADDRESS", doctorData));
        // ��Ë@�֖���
        IkenshoCommon.addString(pd, "hospAdd.name.w2", getString("MI_NM",
                doctorData));
        // �J�ݎҎ���
        IkenshoCommon.addString(pd, "hospAdd.kaisetuname.w2", getString(
                "KAISETUSHA_NM", doctorData));
        // �d�b
        StringBuffer strTemp = new StringBuffer();
        if (!getString("MI_TEL1", doctorData).equals("")) {
            strTemp.append(getString("MI_TEL1", doctorData));
            strTemp.append("-");
        }
        IkenshoCommon.addString(pd, "hospAdd.tel.w2", strTemp.toString()
                + getString("MI_TEL2", doctorData));
        // �ی���Ë@�փR�[�h
        IkenshoCommon.addString(pd, "hospCd.h2.w2", getString("JIGYOUSHA_NO",
                doctorData));

        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // �ӌ����쐬����
            IkenshoCommon.addString(pd, "ikenSeikyuTotal.h2.w1", Integer
                    .toString(seikyuData.getDataSize()));
            // �ӌ����������z
            IkenshoCommon.addString(pd, "ikenCost.h2.w1", costFormat
                    .format(costData.get("IKENSYO_COST_TOTAL")));
        }

        if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
            // ������p��������
            IkenshoCommon.addString(pd, "kensaSeikyuTotal.h2.w1", costFormat
                    .format(costData.get("KENSA_COUNT")));
            // ������p�������z
            IkenshoCommon.addString(pd, "kensaCost.h2.w1", costFormat
                    .format(costData.get("KENSA_COST_TOTAL")));
        }

        // ����ŋ��z���v
        // ���v���z
        switch (printMode) {
        // �ӌ����̂�
        case IKEN_PRINT:
            IkenshoCommon.addString(pd, "tax.h2.w1", costFormat.format(costData
                    .get("IKENSYO_TAX_TOTAL")));
            IkenshoCommon.addString(pd, "seikyuTotal.h2.w1", costFormat
                    .format(costData.get("IKENSYO_TOTAL")));
            break;
        // �������̂�
        case KENSA_PRINT:
            IkenshoCommon.addString(pd, "tax.h2.w1", costFormat.format(costData
                    .get("KENSA_TAX_TOTAL")));
            IkenshoCommon.addString(pd, "seikyuTotal.h2.w1", costFormat
                    .format(costData.get("KENSA_TOTAL")));
            break;
        // ����
        case BOTH_PRINT:
            IkenshoCommon.addString(pd, "tax.h2.w1", costFormat.format(costData
                    .get("TAX_TOTAL")));
            IkenshoCommon.addString(pd, "seikyuTotal.h2.w1", costFormat
                    .format(costData.get("TOTAL")));
            break;
        }

        // �U��������o�͂Ƀ`�F�b�N������ꍇ
        if (bankCheck) {
            // ���Z�@�֖�
            IkenshoCommon.addString(pd, "bank.h3.w2", getString("BANK_NM",
                    doctorData));
            // �x�X��
            IkenshoCommon.addString(pd, "bank.h4.w2", getString(
                    "BANK_SITEN_NM", doctorData));
            // �������
            IkenshoCommon.addString(pd, "bank.h5.w2", getKozaKind(getString(
                    "BANK_KOUZA_KIND", doctorData)));
            // �����ԍ�
            IkenshoCommon.addString(pd, "bank.h6.w2", getString(
                    "BANK_KOUZA_NO", doctorData));
            // ���`�l
            IkenshoCommon.addString(pd, "bank.h7.w2", getString(
                    "FURIKOMI_MEIGI", doctorData));
            // �o�͂̎w�肪�Ȃ��ꍇ�́A�\��������
        } else {
            // �U������Z�@��
            IkenshoCommon.addString(pd, "bank.h1.w1", "");
            // ���Z�@�֖�
            IkenshoCommon.addString(pd, "bank.h3.w1", "");
            // �x�X��
            IkenshoCommon.addString(pd, "bank.h4.w1", "");
            // �������
            IkenshoCommon.addString(pd, "bank.h5.w1", "");
            // �����ԍ�
            IkenshoCommon.addString(pd, "bank.h6.w1", "");
            // ���`�l
            IkenshoCommon.addString(pd, "bank.h7.w1", "");
        }
        pd.endPageEdit();

        return true;
    }

    /**
     * �ӌ����쐬���̈ꗗ�\�̈���f�[�^���쐬���܂��B <BR>
     *
     * @param pd PrintData
     * @param seikyuData VRArrayList
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @param doctorData VRHashMap
     * @param costData HashMap
     * @param printMode int
     * @param bankCheck boolean
     * @return boolean
     * @throws Exception
     */
    private boolean setIchiranPrtData(ACChotarouXMLWriter pd,
            VRArrayList seikyuDataTemp, IkenshoSeikyuPrintSetting seikyuPrtSet,
            VRMap doctorData, HashMap costData, int printMode,
            boolean bankCheck) throws Exception {

        VRArrayList seikyuData = new VRArrayList();
        // �������̂ݏo�͂̏ꍇ
        if (printMode == KENSA_PRINT) {
            for (int i = 0; i < seikyuDataTemp.getDataSize(); i++) {
                VRMap map = (VRMap) seikyuDataTemp.getData(i);
                // ��������0�_�łȂ���Βl��ݒ�
                // if (getKensaTotal(map) != 0) {
                if (getKensaTotal(map).compareTo(new BigDecimal("0")) != 0) {
                    seikyuData.add(map);
                }
            }
            if (seikyuData.getDataSize() == 0) {
                return true;
            }

            // ����ȊO�͈����̒l�����̂܂ܐݒ�
        } else {
            seikyuData = seikyuDataTemp;
        }

        int endRow = seikyuData.getDataSize();
        // �y�[�W���̃J�E���g
        int page = (int) Math.ceil((double) endRow / 12);
        int row = 1;
        int pageRow = 0;

        for (int i = 0; i < page; i++) {

            // ����f�[�^�J�n�ݒ�錾
            pd.beginPageEdit("ichiran");
            // ���ԊJ�n
            if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeFrom)) {
                IkenshoCommon.addString(pd, "dateRange.h1.fromY",
                        getDayFormatYear(seikyuPrtSet.billPrintDateRangeFrom));
                IkenshoCommon.addString(pd, "dateRange.h1.fromM",
                        getDayFormatMonth(seikyuPrtSet.billPrintDateRangeFrom));
                IkenshoCommon.addString(pd, "dateRange.h1.fromD",
                        getDayFormatDay(seikyuPrtSet.billPrintDateRangeFrom));
            }
            // ���ԏI��
            if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeTo)) {
                IkenshoCommon.addString(pd, "dateRange.h1.toY",
                        getDayFormatYear(seikyuPrtSet.billPrintDateRangeTo));
                IkenshoCommon.addString(pd, "dateRange.h1.toM",
                        getDayFormatMonth(seikyuPrtSet.billPrintDateRangeTo));
                IkenshoCommon.addString(pd, "dateRange.h1.toD",
                        getDayFormatDay(seikyuPrtSet.billPrintDateRangeTo));
            }

            // ����
            if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
                // IkenshoCommon.addString(pd, "atena.h1.name",
                // seikyuPrtSet.toCreateCost.getText());
                IkenshoCommon.addString(pd, "atena.h1.name",
                        seikyuPrtSet.toCreateCost.getText() + "�@�l");
            } else if (printMode == KENSA_PRINT) {
                // IkenshoCommon.addString(pd, "atena.h1.name",
                // seikyuPrtSet.toCheckCost.getText());
                IkenshoCommon.addString(pd, "atena.h1.name",
                        seikyuPrtSet.toCheckCost.getText() + "�@�l");
            }

            for (pageRow = 0; (pageRow < 12) && (row < endRow + 1); pageRow++, row++) {
                VRMap map = (VRMap) seikyuData.getData(row - 1);
                // ��ی��Ҕԍ�
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 1) + ".w3",
                        getString("INSURED_NO", map));
                // �ӂ肪��
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 4) + ".w3",
                        getString("PATIENT_KN", map));
                // ��ی��Ҏ���
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 5) + ".w3",
                        getString("PATIENT_NM", map));
                // �ӌ����쐬��
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 1) + ".w5",
                        IkenshoConstants.FORMAT_ERA_YMD.format(map
                                .get("KINYU_DT")));
                // �ӌ���������
                IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 2) + ".w5",
                        IkenshoConstants.FORMAT_ERA_YMD.format(map
                                .get("SEND_DT")));

                // �ݑ�E�{��
                switch (Integer.parseInt(getString("IKN_CHARGE", map))) {
                // �ݑ�V�K
                case 0:
                    pd.addAttribute("sisetu" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("keizoku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                // �ݑ�p��
                case 1:
                    pd.addAttribute("sisetu" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("sinki" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                // �{�ݐV�K
                case 2:
                    pd.addAttribute("zaitaku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("keizoku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                // �{�݌p��
                case 3:
                    pd.addAttribute("zaitaku" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    pd.addAttribute("sinki" + Integer.toString(pageRow + 1),
                            "Visible", "FALSE");
                    break;
                }

                // �ӌ����쐬
                if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
                    // �ӌ����쐬��
                    IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 5)
                            + ".w5", costFormat.format(Double
                            .parseDouble(getIkenshoCost(map))));
                }

                // �f�@�E������p
                if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
                    // �f�@�E������p
                    if (getKensaTotal(map).compareTo(new BigDecimal("0")) > 0) {
                        IkenshoCommon.addString(pd, getHeader(pageRow * 6 + 6)
                                + ".w5", costFormat.format((getKensaTotal(map)
                                .multiply(new BigDecimal("10"))).longValue()));
                    }
                }
            }
            for (; pageRow < 12; pageRow++) {
                // �s�v�ȃ}�[�N������
                // �ݑ�E�{��
                pd.addAttribute("sisetu" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
                pd.addAttribute("zaitaku" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
                // �V�K�E�p��
                pd.addAttribute("keizoku" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
                pd.addAttribute("sinki" + Integer.toString(pageRow + 1),
                        "Visible", "FALSE");
            }
            // �y�[�W�ԍ�
            IkenshoCommon.addString(pd, "pageNo", Integer.toString(i + 1));
            pd.endPageEdit();
        }
        // ����f�[�^�J�n�ݒ�錾
        pd.beginPageEdit("ichiranTotal");

        // ���ԊJ�n
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeFrom)) {
            IkenshoCommon.addString(pd, "dateRange.h1.fromY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeFrom));
            IkenshoCommon.addString(pd, "dateRange.h1.fromD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeFrom));
        }
        // ���ԏI��
        if (isTaisyoKikanInput(seikyuPrtSet.billPrintDateRangeTo)) {
            IkenshoCommon.addString(pd, "dateRange.h1.toY",
                    getDayFormatYear(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toM",
                    getDayFormatMonth(seikyuPrtSet.billPrintDateRangeTo));
            IkenshoCommon.addString(pd, "dateRange.h1.toD",
                    getDayFormatDay(seikyuPrtSet.billPrintDateRangeTo));
        }

        // �ӌ���
        if (printMode == IKEN_PRINT) {
            // ����
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCreateCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCreateCost.getText() + "�@�l");
            // ��������
            IkenshoCommon.addString(pd, "totalCount.h1.count", costData.get(
                    "IKENSYO_COUNT").toString());
            // �ӌ����쐬��
            IkenshoCommon.addString(pd, "totalCost.h1.cost", costFormat
                    .format(costData.get("IKENSYO_COST_TOTAL")));
            // ����ł̑��z
            IkenshoCommon.addString(pd, "totalCost.h3.cost", costFormat
                    .format(costData.get("IKENSYO_TAX_TOTAL")));
            // ���v���z
            IkenshoCommon.addString(pd, "totalCost.h4.cost", costFormat
                    .format(costData.get("IKENSYO_TOTAL")));
            // ������
        } else if (printMode == KENSA_PRINT) {
            // ����
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCheckCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCheckCost.getText() + "�@�l");
            // ��������
            IkenshoCommon.addString(pd, "totalCount.h1.count", costData.get(
                    "KENSA_COUNT").toString());
            // �f�@�E������p
            IkenshoCommon.addString(pd, "totalCost.h2.cost", costFormat
                    .format(costData.get("KENSA_COST_TOTAL")));
            // ����ł̑��z
            IkenshoCommon.addString(pd, "totalCost.h3.cost", costFormat
                    .format(costData.get("KENSA_TAX_TOTAL")));
            // ���v���z
            IkenshoCommon.addString(pd, "totalCost.h4.cost", costFormat
                    .format(costData.get("KENSA_TOTAL")));
            // ����
        } else if (printMode == BOTH_PRINT) {
            // ����
            // IkenshoCommon.addString(pd, "atena.h1.name",
            // seikyuPrtSet.toCreateCost.getText());
            IkenshoCommon.addString(pd, "atena.h1.name",
                    seikyuPrtSet.toCreateCost.getText() + "�@�l");
            // ��������
            IkenshoCommon.addString(pd, "totalCount.h1.count", costData.get(
                    "IKENSYO_COUNT").toString());
            // �ӌ����쐬��
            IkenshoCommon.addString(pd, "totalCost.h1.cost", costFormat
                    .format(costData.get("IKENSYO_COST_TOTAL")));
            // �f�@�E������p
            IkenshoCommon.addString(pd, "totalCost.h2.cost", costFormat
                    .format(costData.get("KENSA_COST_TOTAL")));
            // ����ł̑��z
            IkenshoCommon.addString(pd, "totalCost.h3.cost", costFormat
                    .format(costData.get("TAX_TOTAL")));
            // ���v���z
            IkenshoCommon.addString(pd, "totalCost.h4.cost", costFormat
                    .format(costData.get("TOTAL")));
        }

        // �U��������o�͂Ƀ`�F�b�N������ꍇ
        if (bankCheck) {
            // ���Z�@�֖�
            IkenshoCommon.addString(pd, "bank.h2.w3", getString("BANK_NM",
                    doctorData));
            // �x�X��
            IkenshoCommon.addString(pd, "bank.h3.w3", getString(
                    "BANK_SITEN_NM", doctorData));
            // �������
            IkenshoCommon.addString(pd, "bank.h4.w3", getKozaKind(getString(
                    "BANK_KOUZA_KIND", doctorData)));
            // �����ԍ�
            IkenshoCommon.addString(pd, "bank.h5.w3", getString(
                    "BANK_KOUZA_NO", doctorData));
            // ���`�l
            IkenshoCommon.addString(pd, "bank.h6.w3", getString(
                    "FURIKOMI_MEIGI", doctorData));
            // �o�͂̎w�肪�Ȃ��ꍇ�́A�\��������
        } else {
            // �U������Z�@��
            IkenshoCommon.addString(pd, "bank.h1.w1", "");
            // ���Z�@�֖�
            IkenshoCommon.addString(pd, "bank.h2.w2", "");
            // �x�X��
            IkenshoCommon.addString(pd, "bank.h3.w2", "");
            // �������
            IkenshoCommon.addString(pd, "bank.h4.w2", "");
            // �����ԍ�
            IkenshoCommon.addString(pd, "bank.h5.w2", "");
            // ���`�l
            IkenshoCommon.addString(pd, "bank.h6.w2", "");
        }
        IkenshoCommon.addString(pd, "pageNo", Integer.toString(page + 1));
        pd.endPageEdit();

        return true;
    }

    /**
     * ���������ׂ�S�ďo�͂���B
     *
     * @param pd PrintData
     * @param seikyuData VRArrayList
     * @param seikyuPrtSet IkenshoSeikyuPrintSetting
     * @param printMode int
     * @param bankCheck boolean
     * @return boolean
     * @throws Exception
     */
    private boolean setSyosaiPrtDataAll(ACChotarouXMLWriter pd,
            VRArrayList seikyuData, IkenshoSeikyuPrintSetting seikyuPrtSet,
            int printMode, boolean bankCheck) throws Exception {

        VRMap map = null;
        // �������o�͓��t�����͂���Ă��邩
        boolean outputFlg = isTaisyoKikanInput(seikyuPrtSet.billPrintDate);
        // �����������͂���Ă��邩
        boolean targetFlg = isTaisyoKikanInput(seikyuPrtSet.billDetailPrintDate);
        String outputEra = "";
        String outputYear = "";
        String outputMonth = "";
        String outputDay = "";

        if (outputFlg) {
            Date outputDate = seikyuPrtSet.billPrintDate.getDate();
            outputEra = VRDateParser.format(outputDate, "ggg");
            outputYear = VRDateParser.format(outputDate, "e");
            outputMonth = VRDateParser.format(outputDate, "M");
            outputDay = VRDateParser.format(outputDate, "d");
        }
        String targetEra = "";
        String targetYear = "";
        String targetMonth = "";
        if (targetFlg) {
            Date targeDate = seikyuPrtSet.billDetailPrintDate.getDate();
            targetEra = VRDateParser.format(targeDate, "ggg");
            targetYear = VRDateParser.format(targeDate, "e");
            targetMonth = VRDateParser.format(targeDate, "M");
        }

        String hokensyaNo = getSelectedCboData(hokenjyaCombo, hokenjyaList,
                "INSURER_NO");
        String atena = "";
        // ����
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            atena = seikyuPrtSet.toCreateCost.getText();
        } else if (printMode == KENSA_PRINT) {
            atena = seikyuPrtSet.toCheckCost.getText();
        }

        for (int i = 0; i < seikyuData.getDataSize(); i++) {
            map = (VRMap) seikyuData.getData(i);

            // �������݂̂̏o�͂̏ꍇ
            if (printMode == KENSA_PRINT) {
                // ��������0�_�Ȃ珈�����s��Ȃ��B
                if (getKensaTotal(map).compareTo(new BigDecimal("0")) == 0) {
                    continue;
                }
            }

            setSyosaiPrtData(pd, map, printMode, bankCheck, outputEra,
                    outputYear, outputMonth, outputDay, outputFlg, targetEra,
                    targetYear, targetMonth, targetFlg, false, atena,
                    hokensyaNo);

        }
        return true;
    }

    /**
     * �����ڍׂ̈���f�[�^���쐬���܂��B
     *
     * @param pd PrintData
     * @param map VRHashMap �ݒ肷�鐿�����f�[�^
     * @param printMode int �o�̓��[�h IKEN_PRINT : �ӌ��������̂� KENSA_PRINT �F �������̂�
     *            BOTH_PRINT �F ����
     * @param bankCheck boolean �U��������o�̗͂L��
     * @param outputEra String �o�͔N��
     * @param outputYear String �o�͔N
     * @param outputMonth String �o�͌�
     * @param outputDay String �o�͓�
     * @param outputFlg boolean �o�͓��\���̗L��
     * @param targetEra String �Ώ۔N��
     * @param targetYear String �Ώ۔N
     * @param targetMonth String �Ώی�
     * @param targetFlg boolean �Ώۓ��t�̏o�̗͂L��
     * @param atena String �������̈���
     * @return boolean
     * @throws Exception
     */
    public static boolean setSyosaiPrtData(ACChotarouXMLWriter pd, VRMap map,
            int printMode, boolean bankCheck, String outputEra,
            String outputYear, String outputMonth, String outputDay,
            boolean outputFlg, String targetEra, String targetYear,
            String targetMonth, boolean targetFlg, String hokensyaNo)
            throws Exception {

        String atena = "";
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // ����
            atena = getString("ISS_INSURER_NM", map);
        } else if (printMode == KENSA_PRINT) {
            // ����
            atena = getString("SKS_INSURER_NM", map);
        }

        return setSyosaiPrtData(pd, map, printMode, bankCheck, outputEra,
                outputYear, outputMonth, outputDay, outputFlg, targetEra,
                targetYear, targetMonth, targetFlg, true, atena, hokensyaNo);

    }

    /**
     * �����ڍׂ̈���f�[�^���쐬���܂��B
     *
     * @param pd PrintData
     * @param map VRHashMap �ݒ肷�鐿�����f�[�^
     * @param printMode int �o�̓��[�h IKEN_PRINT : �ӌ��������̂� KENSA_PRINT �F �������̂�
     *            BOTH_PRINT �F ����
     * @param bankCheck boolean �U��������o�̗͂L��
     * @param outputEra String �o�͔N��
     * @param outputYear String �o�͔N
     * @param outputMonth String �o�͌�
     * @param outputDay String �o�͓�
     * @param outputFlg boolean �o�͓��\���̗L��
     * @param targetEra String �Ώ۔N��
     * @param targetYear String �Ώ۔N
     * @param targetMonth String �Ώی�
     * @param targetFlg boolean �Ώۓ��t�̏o�̗͂L��
     * @param formatFlg String �ی��Ҕԍ�
     * @return boolean
     * @throws Exception
     */
    private static boolean setSyosaiPrtData(ACChotarouXMLWriter pd,
            VRMap map, int printMode, boolean bankCheck, String outputEra,
            String outputYear, String outputMonth, String outputDay,
            boolean outputFlg, String targetEra, String targetYear,
            String targetMonth, boolean targetFlg, boolean formatFlg,
            String atena, String hokensyaNo) throws Exception {

        if (formatFlg) {
            // �e��`�̂�ݒ�
            String dir = ACFrame.getInstance().getExeFolderPath() + separator
                    + "format" + separator;
            ACChotarouXMLUtilities.addFormat(pd, "syosai", "IkenshoMeisai.xml");
//            pd.addFormat("syosai", dir + "IkenshoMeisai.xml");
        }

        BigDecimal point = new BigDecimal("0");
        BigDecimal totalPoint = new BigDecimal("0");
        BigDecimal ikenCost = new BigDecimal("0");
        double tax = 0;

        // ����f�[�^�J�n�ݒ�錾
        pd.beginPageEdit("syosai");
        // ����
        // IkenshoCommon.addString(pd, "atena.h1.name", atena);
        IkenshoCommon.addString(pd, "atena.h1.name", atena + "�@�l");

        // �o�͓��t
        if (outputFlg) {
            IkenshoCommon.addString(pd, "printData.h1.Y", outputEra + " "
                    + dayFormat.format(Integer.parseInt(outputYear)));
            IkenshoCommon.addString(pd, "printData.h1.M", dayFormat
                    .format(Integer.parseInt(outputMonth)));
            IkenshoCommon.addString(pd, "printData.h1.D", dayFormat
                    .format(Integer.parseInt(outputDay)));
        }

        // ---��ی���hiyokensya
        // ��ی��Ҕԍ�
        IkenshoCommon.addString(pd, "hiyokensya.h1.w4", getString("INSURED_NO",
                map));
        // �ӂ肪��
        IkenshoCommon.addString(pd, "hiyokensya.h3.w4", getString("PATIENT_KN",
                map));
        // ����
        IkenshoCommon.addString(pd, "hiyokensya.h4.w4", getString("PATIENT_NM",
                map));
        // ���N�����N��BIRTHDAY
        String birthDay = IkenshoConstants.FORMAT_ERA_YMD.format(map
                .get("BIRTHDAY"));
        // �N��
        if (birthDay.indexOf("����") != -1) {
            pd.addAttribute("taisyo", "Visible", "FALSE");
            pd.addAttribute("syowa", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("����", "");
        } else if (birthDay.indexOf("�吳") != -1) {
            pd.addAttribute("meiji", "Visible", "FALSE");
            pd.addAttribute("syowa", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("�吳", "");
        } else if (birthDay.indexOf("���a") != -1) {
            pd.addAttribute("meiji", "Visible", "FALSE");
            pd.addAttribute("taisyo", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("���a", "");
        } else {
            pd.addAttribute("meiji", "Visible", "FALSE");
            pd.addAttribute("taisyo", "Visible", "FALSE");
            pd.addAttribute("syowa", "Visible", "FALSE");
            birthDay = birthDay.replaceAll("����", "");
        }
        IkenshoCommon.addString(pd, "hiyokensya.h6.w3", birthDay);

        // ����
        if (getString("SEX", map).equals("1")) {
            // �j��
            pd.addAttribute("woman", "Visible", "FALSE");
        } else if (getString("SEX", map).equals("2")) {
            // ����
            pd.addAttribute("man", "Visible", "FALSE");
        } else {
            pd.addAttribute("woman", "Visible", "FALSE");
            pd.addAttribute("man", "Visible", "FALSE");
        }

        // ---�ی��Ҕԍ�hokensyaNo
        // �Ώی�
        if (targetFlg) {
            IkenshoCommon.addString(pd, "hokensyaNo.h1.w1", targetEra + " "
                    + dayFormat.format(Integer.parseInt(targetYear)) + " �N "
                    + dayFormat.format(Integer.parseInt(targetMonth)) + " ����");
        } else {
            IkenshoCommon.addString(pd, "hokensyaNo.h1.w1", "�@�@    �N    ����");
        }

        // �ی��Ҕԍ�
        // if ( (printMode & IKEN_PRINT) == IKEN_PRINT) {
        // IkenshoCommon.addString(pd, "hokensyaNo.h2.w2",
        // getString("ISS_INSURER_NO", map));
        // }
        // else if (printMode == KENSA_PRINT) {
        // IkenshoCommon.addString(pd, "hokensyaNo.h2.w2",
        // getString("SKS_INSURER_NO", map));
        // }
        IkenshoCommon.addString(pd, "hokensyaNo.h2.w2", hokensyaNo);

        // ---������Ë@��iryoKikan
        // ���ƎҔԍ�
        IkenshoCommon.addString(pd, "iryoKikan.h1.w3", getString(
                "JIGYOUSHA_NO", map));
        // ���ƎҖ���
        IkenshoCommon.addString(pd, "iryoKikan.h2.w3", getString("MI_NM", map));
        // ���ݒn�X�֔ԍ�
        IkenshoCommon.addString(pd, "iryoKikan.h3.w4", getString("MI_POST_CD",
                map));
        // ���ݒn�Z��
        IkenshoCommon.addString(pd, "iryoKikan.h4.w3", getString("MI_ADDRESS",
                map));
        // �d�b�ԍ�
        if (getString("MI_TEL1", map).equals("")) {
            IkenshoCommon.addString(pd, "iryoKikan.h5.w5", getString("MI_TEL2",
                    map));
        } else {
            IkenshoCommon.addString(pd, "iryoKikan.h5.w5", getString("MI_TEL1",
                    map)
                    + "-" + getString("MI_TEL2", map));
        }
        // ---�˗����t�� dateList
        // �쐬�˗���
        // IkenshoConstants.FORMAT_ERA_YMD
        if (!getString("REQ_DT", map).equals("")) {
            IkenshoCommon.addString(pd, "dateList.h1.w2",
                    IkenshoConstants.FORMAT_ERA_YMD.format(map.get("REQ_DT")));
        }
        // �ӌ����쐬��
        if (!getString("KINYU_DT", map).equals("")) {
            IkenshoCommon
                    .addString(pd, "dateList.h2.w2",
                            IkenshoConstants.FORMAT_ERA_YMD.format(map
                                    .get("KINYU_DT")));
        }
        // �˗��ԍ�
        IkenshoCommon.addString(pd, "dateList.h1.w4", getString("REQ_NO", map));
        // �ӌ������t��
        if (!getString("SEND_DT", map).equals("")) {
            IkenshoCommon.addString(pd, "dateList.h2.w4",
                    IkenshoConstants.FORMAT_ERA_YMD.format(map.get("SEND_DT")));
        }

        // ---�ӌ����쐬�� sakuseiCost
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // �ݑ�E�{��
            switch (Integer.parseInt(getString("IKN_CHARGE", map))) {
            // �ݑ�V�K
            case 0:
                pd.addAttribute("sisetu", "Visible", "FALSE");
                pd.addAttribute("keizoku", "Visible", "FALSE");
                break;
            // �ݑ�p��
            case 1:
                pd.addAttribute("sisetu", "Visible", "FALSE");
                pd.addAttribute("sinki", "Visible", "FALSE");
                break;
            // �{�ݐV�K
            case 2:
                pd.addAttribute("zaitaku", "Visible", "FALSE");
                pd.addAttribute("keizoku", "Visible", "FALSE");
                break;
            // �{�݌p��
            case 3:
                pd.addAttribute("zaitaku", "Visible", "FALSE");
                pd.addAttribute("sinki", "Visible", "FALSE");
                break;
            }

            // �ӌ����쐬��
            ikenCost = new BigDecimal(getIkenshoCost(map));
            IkenshoCommon.addString(pd, "sakuseiCost.h1.w6", costFormat
                    .format(Double.parseDouble(getIkenshoCost(map))));
            // �������̂ݏo�͎��́���\�����Ȃ�
        } else {
            pd.addAttribute("zaitaku", "Visible", "FALSE");
            pd.addAttribute("sisetu", "Visible", "FALSE");
            pd.addAttribute("sinki", "Visible", "FALSE");
            pd.addAttribute("keizoku", "Visible", "FALSE");
        }

        totalPoint = new BigDecimal("0");
        point = new BigDecimal("0");

        if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
            // ---�f�@�E������p
            // ���f�_��
            if (getString("SHOSIN_TAISHOU", map).equals("1")) {
                switch (Integer.parseInt(getString("SHOSIN", map))) {
                // �f�Ï�
                case 1:
                    totalPoint = totalPoint.add(new BigDecimal(getString(
                            "SHOSIN_SINRYOUJO", map)));
                    IkenshoCommon.addString(pd, "costList1.shosin.point",
                            getString("SHOSIN_SINRYOUJO", map).replaceAll(
                                    "\\.0", ""));
                    break;
                // �a�@
                case 2:
                    totalPoint = totalPoint.add(new BigDecimal(getString(
                            "SHOSIN_HOSPITAL", map)));
                    IkenshoCommon.addString(pd, "costList1.shosin.point",
                            getString("SHOSIN_HOSPITAL", map).replaceAll(
                                    "\\.0", ""));
                    break;
                // ���̑�
                case 3:
                    totalPoint = totalPoint.add(new BigDecimal(getString(
                            "SHOSIN_OTHER", map)));
                    IkenshoCommon.addString(pd, "costList1.shosin.point",
                            getString("SHOSIN_OTHER", map).replaceAll("\\.0",
                                    ""));
                    break;
                }
            }
            // �E�v
            IkenshoCommon.addString(pd, "costList1.shosin.tekiyo", getString(
                    "SHOSIN_TEKIYOU", map));

            // �����P��X���B�e
            if (getString("XRAY_TANJUN_SATUEI", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_XRAY_TS", map)));
            }
            if (getString("XRAY_SHASIN_SINDAN", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_XRAY_SS", map)));
            }
            if (getString("XRAY_FILM", map).equals("1")) {
                point = point.add(new BigDecimal(
                        getString("EXP_XRAY_FILM", map)));
            }
            if (point.compareTo(new BigDecimal("0")) != 0) {
                // �_��
                IkenshoCommon.addString(pd, "costList1.xray.point", point
                        .toString().replaceAll("\\.0", ""));
            }
            // �E�v
            IkenshoCommon.addString(pd, "costList1.xray.tekiyo", getString(
                    "XRAY_TEKIYOU", map));
            totalPoint = totalPoint.add(point);
            point = new BigDecimal("0");
            // ���t��ʌ���
            if (getString("BLD_SAISHU", map).equals("1")) {
                point = point.add(new BigDecimal(getString("EXP_KS", map)));
            }
            if (getString("BLD_IPPAN_MASHOU_KETUEKI", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KIK_MKI", map)));
            }
            if (getString("BLD_IPPAN_EKIKAGAKUTEKIKENSA", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KIK_KEKK", map)));
            }
            if (point.compareTo(new BigDecimal("0")) != 0) {
                // �_��
                IkenshoCommon.addString(pd, "costList1.bld_ippan.point", point
                        .toString().replaceAll("\\.0", ""));
            }
            // �E�v
            IkenshoCommon.addString(pd, "costList1.bld_ippan.tekiyo",
                    getString("BLD_IPPAN_TEKIYOU", map));
            totalPoint = totalPoint.add(point);
            point = new BigDecimal("0");
            // ���t���w����
            if (getString("BLD_KAGAKU_KETUEKIKAGAKUKENSA", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KKK_KKK", map)));
            }
            if (getString("BLD_KAGAKU_SEIKAGAKUTEKIKENSA", map).equals("1")) {
                point = point
                        .add(new BigDecimal(getString("EXP_KKK_SKK", map)));
            }
            if (point.compareTo(new BigDecimal("0")) != 0) {
                // �_��
                IkenshoCommon.addString(pd, "costList1.bld_kagaku.point", point
                        .toString().replaceAll("\\.0", ""));
            }
            // �E�v
            IkenshoCommon.addString(pd, "costList1.bld_kagaku.tekiyo",
                    getString("BLD_KAGAKU_TEKIYOU", map));
            totalPoint = totalPoint.add(point);
            point = new BigDecimal("0");
            // �A����ʕ����萫����ʌ���
            if (getString("NYO_KENSA", map).equals("1")) {
                totalPoint = totalPoint.add(new BigDecimal(getString(
                        "EXP_NITK", map)));
                // �_��
                IkenshoCommon.addString(pd, "costList1.nyo.point", getString(
                        "EXP_NITK", map).replaceAll("\\.0", ""));
            }
            // �E�v
            IkenshoCommon.addString(pd, "costList1.nyo.tekiyo", getString(
                    "NYO_KENSA_TEKIYOU", map));

            if (totalPoint.compareTo(new BigDecimal("0")) != 0) {
                // ���v�_��
                IkenshoCommon.addString(pd, "costList1.total.point", totalPoint
                        .toString().replaceAll("\\.0", ""));
            }
            // ���v�_���~10
            IkenshoCommon.addString(pd, "costList1.total.w7", costFormat
                    .format(totalPoint.multiply(new BigDecimal("10"))
                            .doubleValue()));

        }
        // �����
        tax = Double.parseDouble(getString("TAX", map));
        tax = tax / 100d;
        int totalCost = 0;
        // �����z
        if ((printMode & IKEN_PRINT) == IKEN_PRINT) {
            // �ӌ����쐬��costList2
            IkenshoCommon.addString(pd, "costList2.h1.cost", costFormat
                    .format(ikenCost));
            totalCost += ikenCost.intValue();
        }
        if ((printMode & KENSA_PRINT) == KENSA_PRINT) {
            // �f�@�E������p
            IkenshoCommon.addString(pd, "costList2.h2.cost", costFormat
                    .format(totalPoint.multiply(new BigDecimal("10"))));
            totalCost += totalPoint.multiply(new BigDecimal("10")).intValue();
        }
        // ����ő��z
        IkenshoCommon.addString(pd, "costList2.h3.cost", costFormat
                .format(Math.floor((ikenCost.add(totalPoint
                        .multiply(new BigDecimal("10")))).doubleValue()
                        * tax)));
        totalCost += Math.floor(Math.floor((ikenCost.add(totalPoint
                .multiply(new BigDecimal("10")))).doubleValue()
                * tax));
        // ���v
        IkenshoCommon.addString(pd, "costList2.h4.cost", costFormat
                .format((double) totalCost));

        // �U����
        if (bankCheck) {
            IkenshoCommon
                    .addString(pd, "bank.h3.w2", getString("BANK_NM", map));
            IkenshoCommon.addString(pd, "bank.h4.w2", getString(
                    "BANK_SITEN_NM", map));
            IkenshoCommon.addString(pd, "bank.h5.w2", getKozaKind(getString(
                    "KOUZA_KIND", map)));
            IkenshoCommon.addString(pd, "bank.h6.w2",
                    getString("KOUZA_NO", map));
            IkenshoCommon.addString(pd, "bank.h7.w2", getString("KOUZA_MEIGI",
                    map));
        } else {
            IkenshoCommon.addString(pd, "bank.h1.w1", "");
            IkenshoCommon.addString(pd, "bank.h3.w1", "");
            IkenshoCommon.addString(pd, "bank.h4.w1", "");
            IkenshoCommon.addString(pd, "bank.h5.w1", "");
            IkenshoCommon.addString(pd, "bank.h6.w1", "");
            IkenshoCommon.addString(pd, "bank.h7.w1", "");
        }
        pd.endPageEdit();
        return true;
    }

    /**
     * ���z�̌v�Z
     *
     * @param seikyuData VRArrayList
     * @return HashMap
     * @throws Exception
     */
    private HashMap getCostData(VRArrayList seikyuData) throws Exception {
        HashMap costData = new HashMap();
        // �ӌ����������z
        int ikenshoCostTotal = 0;
        // �������쐬����
        int kensaCount = 0;
        // �������������z
        int kensaCostTotal = 0;
        BigDecimal kensaCostTotalTemp = new BigDecimal("0");
        // �ӌ�������Ŋz
        int ikenshoTaxTotal = 0;
        // ����������Ŋz
        int kensaTaxTotal = 0;
        // �ӌ����E���������Z����Ŋz
        int taxTotal = 0;
        // ����ŗ�
        // double tax = 0d;
        BigDecimal tax = new BigDecimal("0.000");
        VRMap map = null;

        for (int i = 0; i < seikyuData.getDataSize(); i++) {
            BigDecimal taxTemp = new BigDecimal("0");
            map = (VRMap) seikyuData.getData(i);
            // tax = Double.parseDouble(getString("TAX", map)) / 100d;
            tax = new BigDecimal(getString("TAX", map) + "0000");
            tax = tax.divide(new BigDecimal("100"), BigDecimal.ROUND_HALF_UP);
            // �ӌ����쐬���Ə���ŋ��z
            ikenshoCostTotal += Integer.parseInt(getIkenshoCost(map));
            // ikenshoTaxTotal += (int)Math.floor(new
            // BigDecimal(getIkenshoCost(map)).multiply(new
            // BigDecimal(tax)).doubleValue());
            ikenshoTaxTotal += (int) Math.floor(new BigDecimal(
                    getIkenshoCost(map)).multiply(tax).doubleValue());
            taxTemp = new BigDecimal(getIkenshoCost(map));
            // ������p
            kensaCostTotalTemp = getKensaTotal(map);
            if (kensaCostTotalTemp.compareTo(new BigDecimal("0")) > 0) {
                kensaCount++;
                kensaCostTotal += kensaCostTotalTemp.multiply(
                        new BigDecimal(10)).intValue();
                // kensaTaxTotal += (int)
                // Math.floor(kensaCostTotalTemp.multiply(new
                // BigDecimal("10")).multiply(new
                // BigDecimal(tax)).doubleValue());
                kensaTaxTotal += (int) Math.floor(kensaCostTotalTemp.multiply(
                        new BigDecimal("10")).multiply(tax).doubleValue());
                taxTemp = taxTemp.add(kensaCostTotalTemp
                        .multiply(new BigDecimal("10")));
            }
            // taxTotal += (int)Math.floor(taxTemp.multiply(new
            // BigDecimal(tax)).doubleValue());
            taxTotal += (int) Math.floor(taxTemp.multiply(tax).doubleValue());
        }
        // �擾�����l��HashMap�ɐݒ肷��B
        costData.put("IKENSYO_COUNT", new Integer(seikyuData.getDataSize()));
        costData.put("IKENSYO_COST_TOTAL", new Integer(ikenshoCostTotal));
        costData.put("KENSA_COUNT", new Integer(kensaCount));
        costData.put("KENSA_COST_TOTAL", new Integer(kensaCostTotal));
        costData.put("IKENSYO_TAX_TOTAL", new Integer(ikenshoTaxTotal));
        costData.put("KENSA_TAX_TOTAL", new Integer(kensaTaxTotal));
        costData.put("IKENSYO_TOTAL", new Integer(ikenshoCostTotal
                + ikenshoTaxTotal));
        costData
                .put("KENSA_TOTAL", new Integer(kensaCostTotal + kensaTaxTotal));

        costData.put("TAX_TOTAL", new Integer(taxTotal));
        costData.put("TOTAL", new Integer(ikenshoCostTotal + kensaCostTotal
                + taxTotal));

        return costData;
    }

    private BigDecimal getKensaTotal(VRMap map) throws Exception {
        BigDecimal total = new BigDecimal("0");
        // ���f�_��
        if (getString("SHOSIN_TAISHOU", map).equals("1")) {
            switch (Integer.parseInt(getString("SHOSIN", map))) {
            case 1:
                total = total.add(new BigDecimal(getString("SHOSIN_SINRYOUJO",
                        map)));
                break;
            case 2:
                total = total.add(new BigDecimal(getString("SHOSIN_HOSPITAL",
                        map)));
                break;
            case 3:
                total = total
                        .add(new BigDecimal(getString("SHOSIN_OTHER", map)));
                break;
            }
        }

        if (getString("BLD_SAISHU", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KS", map)));
        }
        if (getString("BLD_IPPAN_MASHOU_KETUEKI", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KIK_MKI", map)));
        }
        if (getString("BLD_IPPAN_EKIKAGAKUTEKIKENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KIK_KEKK", map)));
        }
        if (getString("BLD_KAGAKU_KETUEKIKAGAKUKENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KKK_KKK", map)));
        }
        if (getString("BLD_KAGAKU_SEIKAGAKUTEKIKENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_KKK_SKK", map)));
        }
        if (getString("NYO_KENSA", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_NITK", map)));
        }
        if (getString("XRAY_TANJUN_SATUEI", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_XRAY_TS", map)));
        }
        if (getString("XRAY_SHASIN_SINDAN", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_XRAY_SS", map)));
        }
        if (getString("XRAY_FILM", map).equals("1")) {
            total = total.add(new BigDecimal(getString("EXP_XRAY_FILM", map)));
        }
        return total;
    }

    /**
     * �ӌ����쐬���̎擾���s���܂��B
     *
     * @param map VRHashMap �f�[�^
     * @return String �ӌ����쐬��
     * @throws Exception ���s����O
     */
    private static String getIkenshoCost(VRMap map) throws Exception {
        String cost = "0";

        switch (Integer.parseInt(getString("IKN_CHARGE", map))) {
        case 0:
            cost = getString("ZAITAKU_SINKI_CHARGE", map);
            break;
        case 1:
            cost = getString("ZAITAKU_KEIZOKU_CHARGE", map);
            break;
        case 2:
            cost = getString("SISETU_SINKI_CHARGE", map);
            break;
        case 3:
            cost = getString("SISETU_KEIZOKU_CHARGE", map);
            break;
        }
        return cost;
    }

    private String getDayFormatYear(IkenshoEraDateTextField date) {
        Date val = date.getDate();
        try {
            return VRDateParser.format(val, "ggg ee");
        } catch (Exception ex) {
            return "";
        }
        // return date.getEra() + " " + getDayFormat(date.getYear());
    }

    private String getDayFormatMonth(IkenshoEraDateTextField date) {
        return getDayFormat(date.getMonth());
    }

    private String getDayFormatDay(IkenshoEraDateTextField date) {
        return getDayFormat(date.getDay());
    }

    private String getDayFormat(String str) {
        return dayFormat.format(Long.parseLong(str));
    }

    private static String getKozaKind(String kozaKindCode) {
        if (kozaKindCode.equals("0")) {
            return "����";
        } else if (kozaKindCode.equals("1")) {
            return "����";
        }
        return "";
    }

    /**
     * DB���X�V���܂��B
     *
     * @return boolean
     * @throws Exception
     */
    private IkenshoFirebirdDBManager doUpdateAll() throws Exception {
        ArrayList updateData = new ArrayList();
        // update
        IkenshoFirebirdDBManager dbm = null;
        try {
            // �p�b�V�u�`�F�b�N / �g�����U�N�V�����J�n
            clearPassiveTask();
            for (int i = 0; i < data.size(); i++) {
                // �����s�̃f�[�^���p�b�V�u�`�F�b�N�̑ΏۂƂ���
                if (((VRMap) data.get(i)).getData("HAKKOU_KBN").toString()
                        .equals("1")) {
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, i);
                    updateData.add(new String[] {
                            ((VRMap) data.get(i)).getData("PATIENT_NO")
                                    .toString(),
                            ((VRMap) data.get(i)).getData("EDA_NO")
                                    .toString() });
                }
            }

            dbm = getPassiveCheckedDBManager();
            if (dbm == null) {
                ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                return null;
            }

            for (int i = 0; i < updateData.size(); i++) {
                String[] data = (String[]) updateData.get(i);
                // SQL���𐶐�
                StringBuffer sb = new StringBuffer();
                sb.append(" UPDATE");
                sb.append(" IKN_BILL");
                sb.append(" SET");
                sb.append(" HAKKOU_KBN = 2");
                sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                sb.append(" WHERE");
                sb.append(" (PATIENT_NO = " + data[0] + ")");
                sb.append(" AND (EDA_NO = " + data[1] + ")");
                // �X�V�pSQL���s
                dbm.executeUpdate(sb.toString());
            }
        } catch (Exception ex) {
            // ���[���o�b�N
            dbm.rollbackTransaction();
            dbm = null;
            throw new Exception(ex);
        }
        return dbm;
    }

    /**
     * ���̓`�F�b�N����
     *
     * @return boolean
     * @throws Exception
     */
    private boolean isValidInput() throws Exception {
        boolean taisyoKikanFromInput = false;
        boolean taisyoKikanToInput = false;
        // ---�����O�����`�F�b�N
        // �ی��ґI���`�F�b�N
        if (hokenjyaCombo.getSelectedIndex() == -1) {
            ACMessageBox.showExclamation("�˗���(�ی���)��I�����Ă��������B");
            hokenjyaCombo.requestFocus();
            return false;
        }
        // �������t�G���[�`�F�b�N
        if (isTaisyoKikanInput(taisyoKikanFrom)) {
            taisyoKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoKikanFrom.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("�����J�n���t�̓��͂Ɍ�肪����܂��B");
                taisyoKikanFrom.transferFocus();
                taisyoKikanFrom
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            taisyoKikanFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            taisyoKikanFromInput = true;
        }
        if (isTaisyoKikanInput(taisyoKikanTo)) {
            taisyoKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoKikanTo.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("�����I�����t�̓��͂Ɍ�肪����܂��B");
                taisyoKikanTo.transferFocus();
                taisyoKikanTo
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            taisyoKikanTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            taisyoKikanToInput = true;
        }
        // ���t�̑O��֌W�`�F�b�N
        if (taisyoKikanFromInput && taisyoKikanToInput) {
            if (taisyoKikanFrom.getDate().after(taisyoKikanTo.getDate())) {
                ACMessageBox.showExclamation("�J�n���t�ƏI�����t���t�]���Ă��܂��B");
                taisyoKikanFrom.transferFocus();
                return false;
            }
        }
        if (isChangeData()) {
            int result = ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�ۑ����Ă���낵���ł����H",
                    "�͂�(Y)", 'Y', "������(N)", 'N');

            // �ۑ����Č���
            if (result == ACMessageBox.RESULT_YES) {
                // �X�V���s
                if (!doUpdate()) {
                    return false;
                }
                // �ۑ���������
            } else if (result == ACMessageBox.RESULT_NO) {
                // �������Ȃ�
                // �������s�L�����Z��
            } else {
                return false;
            }
        }
        return true;
    }

    /**
     * DB����f�[�^���擾���܂��B
     *
     * @param msgFlg boolean
     * @throws Exception
     */
    private void doSelect(boolean msgFlg) throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();

        // �����pSQL
        sb.append(" SELECT");
        // ��ʕ\���p�f�[�^
        sb.append(" COMMON_IKN_SIS.PATIENT_NO");
        sb.append(" ,COMMON_IKN_SIS.EDA_NO");
        sb.append(" ,IKN_BILL.HAKKOU_KBN");
        sb.append(" ,IKN_BILL.HAKKOU_KBN AS HAKKOU_KBN_ORIGIN");
        sb.append(" ,COMMON_IKN_SIS.PATIENT_NM");
        sb.append(" ,COMMON_IKN_SIS.PATIENT_KN");
        sb.append(" ,COMMON_IKN_SIS.SEX");
        sb.append(" ,COMMON_IKN_SIS.BIRTHDAY");
        sb.append(" ,IKN_ORIGIN.INSURED_NO");
        sb.append(" ,COMMON_IKN_SIS.AGE");
        sb.append(" ,IKN_ORIGIN.INSURED_NO");
        sb.append(" ,COMMON_IKN_SIS.DR_NM");
        sb.append(" ,IKN_ORIGIN.REQ_DT");
        sb.append(" ,IKN_ORIGIN.KINYU_DT");
        sb.append(" ,IKN_ORIGIN.SEND_DT");
        sb.append(" ,IKN_BILL.LAST_TIME");
        // ���������s�ɕK�v�ȃf�[�^
        sb.append(" ,IKN_ORIGIN.IKN_CREATE_CNT");

        sb.append(" ,IKN_BILL.BANK_NM");
        sb.append(" ,IKN_BILL.BANK_SITEN_NM");
        sb.append(" ,IKN_BILL.KOUZA_NO");
        sb.append(" ,IKN_BILL.KOUZA_KIND");
        sb.append(" ,IKN_BILL.KOUZA_MEIGI");
        sb.append(" ,IKN_BILL.ZAITAKU_SINKI_CHARGE");
        sb.append(" ,IKN_BILL.ZAITAKU_KEIZOKU_CHARGE");
        sb.append(" ,IKN_BILL.SISETU_SINKI_CHARGE");
        sb.append(" ,IKN_BILL.SISETU_KEIZOKU_CHARGE");
        sb.append(" ,IKN_BILL.TAX");
        sb.append(" ,IKN_BILL.IKN_CHARGE");
        sb.append(" ,IKN_BILL.SHOSIN_TAISHOU");
        sb.append(" ,IKN_BILL.SHOSIN");
        sb.append(" ,IKN_BILL.SHOSIN_SINRYOUJO");
        sb.append(" ,IKN_BILL.SHOSIN_HOSPITAL");
        sb.append(" ,IKN_BILL.SHOSIN_OTHER");
        sb.append(" ,IKN_BILL.SHOSIN_TEKIYOU");
        sb.append(" ,IKN_BILL.BLD_SAISHU");
        sb.append(" ,IKN_BILL.BLD_IPPAN_MASHOU_KETUEKI");
        sb.append(" ,IKN_BILL.BLD_IPPAN_EKIKAGAKUTEKIKENSA");
        sb.append(" ,IKN_BILL.BLD_IPPAN_TEKIYOU");
        sb.append(" ,IKN_BILL.BLD_KAGAKU_KETUEKIKAGAKUKENSA");
        sb.append(" ,IKN_BILL.BLD_KAGAKU_SEIKAGAKUTEKIKENSA");
        sb.append(" ,IKN_BILL.BLD_KAGAKU_TEKIYOU");
        sb.append(" ,IKN_BILL.NYO_KENSA");
        sb.append(" ,IKN_BILL.NYO_KENSA_TEKIYOU");
        sb.append(" ,IKN_BILL.XRAY_TANJUN_SATUEI");
        sb.append(" ,IKN_BILL.XRAY_SHASIN_SINDAN");
        sb.append(" ,IKN_BILL.XRAY_FILM");
        sb.append(" ,IKN_BILL.XRAY_TEKIYOU");
        sb.append(" ,IKN_BILL.EXP_KS");
        sb.append(" ,IKN_BILL.EXP_KIK_MKI");
        sb.append(" ,IKN_BILL.EXP_KIK_KEKK");
        sb.append(" ,IKN_BILL.EXP_KKK_KKK");
        sb.append(" ,IKN_BILL.EXP_KKK_SKK");
        sb.append(" ,IKN_BILL.EXP_NITK");
        sb.append(" ,IKN_BILL.EXP_XRAY_TS");
        sb.append(" ,IKN_BILL.EXP_XRAY_SS");
        sb.append(" ,IKN_BILL.EXP_XRAY_FILM");
        sb.append(" ,IKN_BILL.ISS_INSURER_NO");
        sb.append(" ,IKN_BILL.ISS_INSURER_NM");
        sb.append(" ,IKN_BILL.SKS_INSURER_NM");
        sb.append(" ,IKN_BILL.SKS_INSURER_NO");
        sb.append(" ,IKN_BILL.JIGYOUSHA_NO");

        sb.append(" ,IKN_ORIGIN.KIND");
        sb.append(" ,IKN_ORIGIN.REQ_NO");

        sb.append(" ,COMMON_IKN_SIS.MI_NM");
        sb.append(" ,COMMON_IKN_SIS.MI_POST_CD");
        sb.append(" ,COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(" ,COMMON_IKN_SIS.MI_TEL1");
        sb.append(" ,COMMON_IKN_SIS.MI_TEL2");

        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,");
        sb.append(" IKN_ORIGIN,");
        sb.append(" IKN_BILL");
        sb.append(" WHERE");
        // �O�����
        sb.append(" (COMMON_IKN_SIS.PATIENT_NO = IKN_ORIGIN.PATIENT_NO)");
        sb.append(" AND (COMMON_IKN_SIS.EDA_NO = IKN_ORIGIN.EDA_NO)");
        sb.append(" AND (COMMON_IKN_SIS.DOC_KBN = 1)");
        sb.append(" AND (IKN_ORIGIN.PATIENT_NO = IKN_BILL.PATIENT_NO)");
        sb.append(" AND (IKN_ORIGIN.EDA_NO = IKN_BILL.EDA_NO)");
        // �ی���(�K�{)
        //2005-12-24 edit sta fujihara shin �V���O���N�H�[�e�[�V������t��
//        sb.append(" AND (IKN_ORIGIN.INSURER_NO = "
//                + getSelectedCboData(hokenjyaCombo, hokenjyaList, "INSURER_NO")
//                + ")");
        sb.append(" AND (IKN_ORIGIN.INSURER_NO = '"
                + getSelectedCboData(hokenjyaCombo, hokenjyaList, "INSURER_NO")
                + "')");
        //2005-12-24 edit end
        //2006-2-10 edit sta fujihara shin �ӌ����쐬�����K��0�~�ɂȂ�(�f�[�^�ڍs����̃f�[�^)�͏���
        sb.append(" AND ((IKN_BILL.ZAITAKU_SINKI_CHARGE + IKN_BILL.ZAITAKU_KEIZOKU_CHARGE + IKN_BILL.SISETU_SINKI_CHARGE + IKN_BILL.SISETU_KEIZOKU_CHARGE) <> 0)");
        //2006-2-10 edit end

        // ��t��
        if (doctorCombo.getSelectedIndex() > 0) {
            sb.append(" AND (COMMON_IKN_SIS.DR_NM = '"
                    + getSelectedCboData(doctorCombo, doctorList, "DR_NM")
                            .replaceAll("'", "''") + "')");
        }
        // ���ԊJ�n��
        if (isTaisyoKikanInput(taisyoKikanFrom)) {
            // �L����
            if (taisyoDayCombo.getSelectedIndex() == 0) {
                sb.append(" AND (IKN_ORIGIN.KINYU_DT >= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanFrom
                                .getDate()) + "')");
                // �˗���
                // 2005.10.12 ���t���̌��
            } else if (taisyoDayCombo.getSelectedIndex() == 1) {
                // sb.append(" AND (IKN_ORIGIN.REQ_DT >= '" +
                // IkenshoConstants.FORMAT_YMD.format(taisyoKikanFrom.getDate())
                // + "')");
                sb.append(" AND (IKN_ORIGIN.SEND_DT >= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanFrom
                                .getDate()) + "')");
            }
        }
        // ���ԏI����
        if (isTaisyoKikanInput(taisyoKikanTo)) {
            // �L����
            if (taisyoDayCombo.getSelectedIndex() == 0) {
                sb.append(" AND (IKN_ORIGIN.KINYU_DT <= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanTo
                                .getDate()) + "')");
                // �˗���
                // 2005.10.12 ���t���̌��
            } else if (taisyoDayCombo.getSelectedIndex() == 1) {
                // sb.append(" AND (IKN_ORIGIN.REQ_DT <= '" +
                // IkenshoConstants.FORMAT_YMD.format(taisyoKikanTo.getDate()) +
                // "')");
                sb.append(" AND (IKN_ORIGIN.SEND_DT <= '"
                        + IkenshoConstants.FORMAT_YMD.format(taisyoKikanTo
                                .getDate()) + "')");
            }
        }

        // ���s�ς݂̕\��
        if (hakkozumiCheck.isSelected()) {
            sb.append(" AND (IKN_BILL.HAKKOU_KBN IN (1,2,3))");
        } else {
            sb.append(" AND (IKN_BILL.HAKKOU_KBN IN (1,3))");
        }
        sb.append(" ORDER BY");
        sb.append(" IKN_ORIGIN.INSURED_NO");
        // sb.append(" ,IKN_BILL.LAST_TIME");
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            // �p�b�V�u�`�F�b�N
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }
        tableCellRenderer.setData(data);
        // �e�[�u���ĕ`��
        tableModel.setAdaptee(data);
        // �\�[�g���Đݒ�
        table.resort();
        // table.getTable().resort();
        // ���j���[�{�^����Enabled�ݒ�
        setButtonsEnabled();
        // �X�e�[�^�X�o�[�̐ݒ�
        setStatus();

        // ����0�����̃��b�Z�[�W��\��
        if (msgFlg && table.getRowCount() == 0) {
            ACMessageBox.show("�����ɍ��v����u�ӌ����v������܂���ł����B");
        } else {
            table.setSelectedModelRow(((VRSortableTableModelar) table
                    .getModel()).getReverseTranslateIndex(0));
            table.requestFocus();
            // table.getTable().requestFocus();
        }
        // �����L�[�̃X�i�b�v�V���b�g���쐬
        IkenshoSnapshot.getInstance().snapshot();
    }

    /**
     * DB���X�V���܂��B
     *
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdate() throws Exception {
        ArrayList updateData = new ArrayList();

        // update
        IkenshoFirebirdDBManager dbm = null;
        try {
            // �p�b�V�u�`�F�b�N / �g�����U�N�V�����J�n
            clearPassiveTask();
            for (int i = 0; i < data.size(); i++) {
                // �f�[�^���ύX����Ă���΁A�p�b�V�u�`�F�b�N�ƍX�V�ΏۂɊ܂߂�
                if (isChangeData(i)) {
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, i);
                    updateData.add(new String[] {
                            ((VRMap) data.get(i)).getData("PATIENT_NO")
                                    .toString(),
                            ((VRMap) data.get(i)).getData("EDA_NO")
                                    .toString(),
                            ((VRMap) data.get(i)).getData("HAKKOU_KBN")
                                    .toString() });
                }
            }

            dbm = getPassiveCheckedDBManager();
            if (dbm == null) {
                ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                return false;
            }

            for (int i = 0; i < updateData.size(); i++) {
                String[] data = (String[]) updateData.get(i);
                // SQL���𐶐�
                StringBuffer sb = new StringBuffer();
                sb.append(" UPDATE");
                sb.append(" IKN_BILL");
                sb.append(" SET");
                sb.append(" HAKKOU_KBN = " + data[2]);
                sb.append(" ,LAST_TIME = CURRENT_TIMESTAMP");
                sb.append(" WHERE");
                sb.append(" (PATIENT_NO = " + data[0] + ")");
                sb.append(" AND (EDA_NO = " + data[1] + ")");
                // �X�V�pSQL���s
                dbm.executeUpdate(sb.toString());
            }
            // �R�~�b�g
            dbm.commitTransaction();
        } catch (Exception ex) {
            // ���[���o�b�N
            dbm.rollbackTransaction();
            throw new Exception(ex);
        }
        // if(commitMessageShow){
        // //���������ʒmMsg�\��
        // IkenshoMessageBox.show("�X�V����܂����B");
        // }
        return true;
    }

    private VRMap getDefaultDoctor(IkenshoFirebirdDBManager dbm,
            String insurer_no) throws Exception {
        StringBuffer sb = new StringBuffer();

        // del mac �Ή�(union���g�p���Ȃ��悤�ύX)
        // sb.append(" SELECT");
        // sb.append(" DOCTOR.MI_ADDRESS,");
        // sb.append(" DOCTOR.MI_NM,");
        // sb.append(" DOCTOR.KAISETUSHA_NM,");
        // sb.append(" DOCTOR.MI_TEL1,");
        // sb.append(" DOCTOR.MI_TEL2,");
        // sb.append(" JIGYOUSHA.JIGYOUSHA_NO,");
        // sb.append(" DOCTOR.BANK_NM,");
        // sb.append(" DOCTOR.BANK_SITEN_NM,");
        // sb.append(" DOCTOR.BANK_KOUZA_KIND,");
        // sb.append(" DOCTOR.BANK_KOUZA_NO,");
        // sb.append(" DOCTOR.FURIKOMI_MEIGI,");
        // sb.append(" DOCTOR.MI_DEFAULT,");
        // sb.append(" DOCTOR.DR_CD");
        // sb.append(" FROM ");
        // sb.append(" DOCTOR ");
        // sb.append(" LEFT OUTER JOIN ");
        // sb.append(" JIGYOUSHA ");
        // sb.append(" ON DOCTOR.DR_CD = JIGYOUSHA.DR_CD ");
        // //add
        // sb.append(" AND JIGYOUSHA.INSURER_NO = '" + insurer_no + "'");
        // //add
        // sb.append(" UNION");
        // sb.append(" SELECT");
        // sb.append(" CAST('' AS VARCHAR(300)) AS MI_ADDRESS,");
        // sb.append(" CAST('' AS VARCHAR(180)) AS MI_NM,");
        // sb.append(" CAST('' AS VARCHAR(90)) AS KAISETUSHA_NM,");
        // sb.append(" CAST('' AS VARCHAR(30)) AS MI_TEL1,");
        // sb.append(" CAST('' AS VARCHAR(60)) AS MI_TEL2,");
        // sb.append(" CAST('' AS VARCHAR(60)) AS JIGYOUSHA_NO,");
        // sb.append(" CAST('' AS VARCHAR(150)) AS BANK_NM,");
        // sb.append(" CAST('' AS VARCHAR(150)) AS BANK_SITEN_NM,");
        // sb.append(" CAST(2 AS INTEGER) AS BANK_KOUZA_KIND,");
        // sb.append(" CAST('' AS VARCHAR(60)) AS BANK_KOUZA_NO,");
        // sb.append(" CAST('' AS VARCHAR(90)) AS FURIKOMI_MEIGI,");
        // sb.append(" CAST(-1 AS INTEGER) AS MI_DEFAULT,");
        // sb.append(" CAST(-1 AS INTEGER) AS DR_CD");
        // sb.append(" FROM ");
        // sb.append(" RDB$DATABASE ");
        // sb.append(" ORDER BY");
        // sb.append(" 12 DESC,");
        // sb.append(" 13");
        // del end

        sb.append(" SELECT");
        sb.append(" DOCTOR.MI_ADDRESS,");
        sb.append(" DOCTOR.MI_NM,");
        sb.append(" DOCTOR.KAISETUSHA_NM,");
        sb.append(" DOCTOR.MI_TEL1,");
        sb.append(" DOCTOR.MI_TEL2,");
        sb.append(" JIGYOUSHA.JIGYOUSHA_NO,");
        sb.append(" DOCTOR.BANK_NM,");
        sb.append(" DOCTOR.BANK_SITEN_NM,");
        sb.append(" DOCTOR.BANK_KOUZA_KIND,");
        sb.append(" DOCTOR.BANK_KOUZA_NO,");
        sb.append(" DOCTOR.FURIKOMI_MEIGI,");
        sb.append(" DOCTOR.MI_DEFAULT,");
        sb.append(" DOCTOR.DR_CD");
        sb.append(" FROM ");
        sb.append(" DOCTOR ");
        sb.append(" LEFT OUTER JOIN ");
        sb.append(" JIGYOUSHA ");
        sb.append(" ON DOCTOR.DR_CD = JIGYOUSHA.DR_CD ");
        sb.append(" AND  JIGYOUSHA.INSURER_NO = '" + insurer_no + "'");
        sb.append(" ORDER BY");
        sb.append(" DOCTOR.MI_DEFAULT DESC,");
        sb.append(" DOCTOR.DR_CD");
        VRArrayList ary = (VRArrayList) dbm.executeQuery(sb.toString());

        VRMap result = null;
        // ��t���̂̎擾���s�����ꍇ�́A��Ԑ擪�̃f�[�^���̗p����B
        if (ary.getDataSize() > 0) {
            result = (VRMap) ary.get(0);

            // ��t���̂̎擾���s���Ȃ��ꍇ�A���HashMap���쐬����B
        } else {
            result = new VRHashMap();
            result.put("MI_ADDRESS", "");
            result.put("MI_NM", "");
            result.put("KAISETUSHA_NM", "");
            result.put("MI_TEL1", "");
            result.put("MI_TEL2", "");
            result.put("JIGYOUSHA_NO", "");
            result.put("BANK_NM", "");
            result.put("BANK_SITEN_NM", "");
            result.put("BANK_KOUZA_KIND", "2");
            result.put("BANK_KOUZA_NO", "");
            result.put("FURIKOMI_MEIGI", "");
            result.put("MI_DEFAULT", "-1");
            result.put("DR_CD", "-1");
        }

        // return (VRHashMap)ary.get(0);
        return result;
    }

    /**
     * �c�[���{�^���A���j���[�̗L����Ԃ�ݒ肵�܂��B
     */
    private void setButtonsEnabled() {
        boolean enabled = false;
        if (table.getRowCount() > 0) {
            enabled = true;
        }
        // �����t����ǉ����Ă���
        update.setEnabled(isChangeData());
        print.setEnabled(isPrintData());
        printList.setEnabled(enabled);
        mihakko.setEnabled(enabled);
        hakkozumi.setEnabled(enabled);
        horyu.setEnabled(enabled);
    }

    private boolean isChangeData() {
        for (int i = 0; i < data.size(); i++) {
            if (isChangeData(i)) {
                return true;
            }
        }
        return false;
    }

    private boolean isChangeData(int i) {
        return !((VRMap) data.getData(i)).getData("HAKKOU_KBN").toString()
                .equals(
                        ((VRMap) data.getData(i)).getData(
                                "HAKKOU_KBN_ORIGIN").toString());
    }

    private boolean isPrintData() {
        try {
            for (int i = 0; i < data.size(); i++) {
                if (getString("HAKKOU_KBN", (VRMap) data.get(i))
                        .equals("1")) {
                    return true;
                }
            }
        } catch (Exception e) {
            VRLogger.warning(e.toString());
        }
        return false;
    }

    private void setStatus() {
        int mihakko = 0;
        for (int i = 0; i < data.size(); i++) {
            if (((VRMap) data.getData(i)).getData("HAKKOU_KBN").toString()
                    .equals("1")) {
                mihakko++;
            }
        }
        // �X�e�[�^�X�o�[
        setStatusText(Integer.toString(table.getRowCount()) + " �����o����܂����B(�� "
                + Integer.toString(mihakko) + " ���������s�ł�)");

    }

    private boolean isTaisyoKikanInput(IkenshoEraDateTextField date) {
        return (!date.getEra().equals("") && !date.getYear().equals(""));
    }

    /**
     * �u���t�����v�{�^��������
     *
     * @param e ActionEvent
     */
    public void doClearDate_actionPerformed(ActionEvent e) {
        taisyoKikanFrom.clear();
        taisyoKikanFrom.setEra("����");
        taisyoKikanTo.clear();
        taisyoKikanTo.setEra("����");
    }

    /**
     * �u�����s�v�u�ۗ��v�u���s�ρv�{�^��������
     *
     * @param e ActionEvent
     */
    public void changeStatus_actionPerformed(ActionEvent e) {
        int selectedRow = table.getSelectedModelRow();
        // �O���b�h���I������Ă��Ȃ��ꍇ�́A�������s��Ȃ�
        if (selectedRow < 0) {
            return;
        }
        // �����s�{�^��������
        if (e.getSource().equals(mihakko) || e.getSource().equals(mihakkoMenu)) {
            ((VRMap) data.getData(selectedRow)).setData("HAKKOU_KBN",
                    new Integer(1));
            // �ۗ��{�^��������
        } else if (e.getSource().equals(horyu)
                || e.getSource().equals(horyuMenu)) {
            ((VRMap) data.getData(selectedRow)).setData("HAKKOU_KBN",
                    new Integer(3));
            // ���s�σ{�^��������
        } else if (e.getSource().equals(hakkozumi)
                || e.getSource().equals(hakkozumiMenu)) {
            ((VRMap) data.getData(selectedRow)).setData("HAKKOU_KBN",
                    new Integer(2));
        }
        // �e�[�u���ĕ`��
        tableModel.setAdaptee(data);
        table.setSelectedModelRow(selectedRow);
        // table.setModelSelectedRow(((VRTableModel)table.getModel()).getReverseTranslateIndex(selectedRow));
        // table.getTable().requestFocus();
        table.requestFocus();

        // table.setSelectedRow(((VRTableModel)table.getModel()).getTranslateIndex(selectedRow));
        // ���j���[�{�^����Enabled�ݒ�
        setButtonsEnabled();
        // �X�e�[�^�X�o�[�̐ݒ�
        setStatus();
    }
}

class IkenSeikyuIchiran_clearDate_actionAdapter implements ActionListener {
    private IkenshoSeikyuIchiran adaptee;

    IkenSeikyuIchiran_clearDate_actionAdapter(IkenshoSeikyuIchiran adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.doClearDate_actionPerformed(e);
    }
}

class IkenSeikyuIchiran_changeStatus_actionAdapter implements ActionListener {
    private IkenshoSeikyuIchiran adaptee;

    IkenSeikyuIchiran_changeStatus_actionAdapter(IkenshoSeikyuIchiran adaptee) {
        this.adaptee = adaptee;
    }

    public void actionPerformed(ActionEvent e) {
        adaptee.changeStatus_actionPerformed(e);
    }
}
