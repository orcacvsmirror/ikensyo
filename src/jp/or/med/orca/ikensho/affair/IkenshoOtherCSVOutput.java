package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.Format;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.table.ACFollowConditionForegroundTableCellRenderer;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.io.ACBmpWriter;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACFillCharFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.io.VRCSVFile;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRDateFormat;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellEditor;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCSVFileFilter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** <HEAD_IKENSYO> */
public class IkenshoOtherCSVOutput extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private ACAffairButton find = new ACAffairButton();
    private ACAffairButton print = new ACAffairButton();
    private ACAffairButton printTable = new ACAffairButton();
    private ACTable table = new ACTable();
    private ACTableModelAdapter tableModel;
    private ACFollowConditionForegroundTableCellRenderer tableCellRenderer;
    private VRArrayList data;

    private VRPanel client = new VRPanel();
    private ACGroupBox searchGrp = new ACGroupBox();
    private ACLabelContainer insurerContainer = new ACLabelContainer();
    private ACComboBox insurer = new ACComboBox();
    private ACLabelContainer doctorContainer = new ACLabelContainer();
    private ACComboBox doctor = new ACComboBox();
    private ACLabelContainer formatKbnContainer = new ACLabelContainer();
    private ACComboBox formatKbn = new ACComboBox();
    // add begin 2006/08/03 kamitsukasa
    private ACLabelContainer formatKbnIshiContainer = new ACLabelContainer();
    private ACComboBox formatKbnIshi = new ACComboBox();
    // add end 2006/08/03 kamitsukasa
    private ACLabelContainer taisyoContainer = new ACLabelContainer();
    private ACLabelContainer taisyoDayContainer = new ACLabelContainer();
    private ACComboBox taisyoDay = new ACComboBox();
    private ACLabelContainer taisyoFromContainer = new ACLabelContainer();
    private IkenshoEraDateTextField taisyoFrom = new IkenshoEraDateTextField();
    private JLabel taisyoCaption = new JLabel();
    private ACLabelContainer taisyoToContainer = new ACLabelContainer();
    private IkenshoEraDateTextField taisyoTo = new IkenshoEraDateTextField();
    private ACButton taisyoClear = new ACButton();

    private VRPanel btnGrp = new VRPanel();
    private ACButton cancel = new ACButton();
    private ACButton output = new ACButton();
    private ACButton taisyoCancel = new ACButton();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem cancelMenu = new JMenuItem();
    private JMenuItem outputMenu = new JMenuItem();
    private JMenuItem taisyoCancelMenu = new JMenuItem();

    private String bkInsurerNm;
    private int printOutCount = 1;

    private static String separator = System.getProperty("file.separator");

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO", "EDA_NO" }, new Format[] {
                    null, null }, "LAST_TIME", "LAST_TIME");

    private final String h17 = "����17�N�x";
    private final String h18 = "����18�N�x";
    // add begin 2006/08/03 kamitsukasa
    private final String IKN_NEW = "�厡��ӌ���";
    private final String IKN_ISHI = "��t�ӌ���";
    // add end 2006/08/03 kamitsukasa
    
    // 2006/06/23
    // CRLF - �u���Ή�
    // Addition - begin [Masahiko Higuchi]
    private static final String VT = String.valueOf('\u000b');
    // Addition - end

    public IkenshoOtherCSVOutput() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        buttons.setTitle("�u�ӌ����vCSV�t�@�C���o��");
        buttons.add(printTable, VRLayout.EAST);
        buttons.add(print, VRLayout.EAST);
        buttons.add(find, VRLayout.EAST);
        buttons.add(update, VRLayout.EAST);

        client.setLayout(new VRLayout());
        client.add(searchGrp, VRLayout.NORTH);
        client.add(btnGrp, VRLayout.NORTH);
        client.add(table, VRLayout.CLIENT);

        // �{�^��
        update.setText("�X�V(S)");
        update.setMnemonic('S');
        update.setActionCommand("�폜(D)");
        update.setToolTipText("[�o�͑Ώ�]�̐ݒ���X�V���܂��B");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        find.setText("����(V)");
        find.setMnemonic('V');
        find.setActionCommand("����(V)");
        find.setToolTipText("���ݓ��͂���Ă�������ɂ��A�ꗗ��\�����܂��B");
        find.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_FIND);

        print.setText("̧�ٍ쐬(F)");
        print.setMnemonic('F');
        print.setActionCommand("̧�ٍ쐬(F)");
        print.setToolTipText("�I������Ă���ӌ�����CSV�t�@�C���ɏ����o���܂��B");
        print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_EXPORT);

        printTable.setText("�ꗗ���(L)");
        printTable.setMnemonic('L');
        printTable.setActionCommand("�ꗗ���(L)");
        printTable.setToolTipText("CSV�o�͑Ώۊ��҈ꗗ�\��������܂��B");
        printTable.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_TABLE_PRINT);

        // ��������
        searchGrp.setText("�\������");
        VRLayout searchGrpLayout = new VRLayout();
        searchGrpLayout.setAutoWrap(false);
        searchGrp.setLayout(searchGrpLayout);
        searchGrp.add(insurerContainer, VRLayout.FLOW_INSETLINE);
        searchGrp.add(formatKbnContainer, VRLayout.FLOW);
        // add begin 2006/08/04 kamitsukasa
        searchGrp.add(formatKbnIshiContainer, VRLayout.FLOW);
        // add end 2006/08/04 kamitsukasa
        searchGrp.add(doctorContainer, VRLayout.FLOW_RETURN);
        searchGrp.add(taisyoContainer, VRLayout.FLOW_INSETLINE_RETURN);

        // ���������E�˗���(�ی���)
        insurerContainer.setText("�˗���(�ی���)");
        insurerContainer.setLayout(new BorderLayout());
        insurerContainer.add(insurer, null);
        insurer.setPreferredSize(new Dimension(250, 20));
        insurer.setEditable(false);
        insurer.setBindPath("INSURER_NM");

        // ���������E���
        doctorContainer.setText("��t����");
        doctorContainer.setLayout(new BorderLayout());
        doctorContainer.add(doctor, null);
        doctor.setPreferredSize(new Dimension(200, 20));
        doctor.setEditable(false);
        doctor.setBindPath("DR_NM");

        // ���������E�t�H�[�}�b�g�敪
        formatKbnContainer.setText("�Ή��N�x");
        formatKbnContainer.setLayout(new BorderLayout());
        formatKbnContainer.add(formatKbn, null);
        formatKbn.setEditable(false);
        formatKbn.addItem(h18);
        formatKbn.addItem(h17);
        formatKbn.setBindPath("FORMAT_KBN");
        formatKbnContainer.setVisible(false);

        // add begin 2006/08/03 kamitsukasa
        // ���������E�t�H�[�}�b�g�敪(�厡��ӌ����A��t�㌩��)
        formatKbnIshiContainer.setText("�ӌ����敪");
        formatKbnIshiContainer.setLayout(new BorderLayout());
        formatKbnIshiContainer.add(formatKbnIshi, null);
        formatKbnIshi.setEditable(false);
        formatKbnIshi.addItem(IKN_NEW);
        formatKbnIshi.addItem(IKN_ISHI);
        formatKbnIshiContainer.setVisible(true);
        // add end 2006/08/03 kamitsukasa

        // ���������E�Ώۊ���
        VRLayout taisyoContainerLayout = new VRLayout();
        taisyoContainerLayout.setHgap(0);
        taisyoContainerLayout.setVgap(0);
        taisyoContainerLayout.setAutoWrap(false);
        taisyoContainer.setContentAreaFilled(true);
        taisyoContainer
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        taisyoContainer.setLayout(taisyoContainerLayout);
        taisyoContainer.add(taisyoDayContainer, VRLayout.FLOW);
        taisyoContainer.add(taisyoFromContainer, VRLayout.FLOW);
        taisyoContainer.add(taisyoCaption, VRLayout.FLOW);
        taisyoContainer.add(taisyoToContainer, VRLayout.FLOW);
        taisyoContainer.add(taisyoClear, VRLayout.FLOW_RETURN);
        taisyoContainer
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        taisyoContainer
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        taisyoContainer.setContentAreaFilled(true);

        // ���������E�Ώۊ��ԁE����
        taisyoDayContainer.setText("�w��Ώ�");
        taisyoDayContainer.add(taisyoDay, VRLayout.FLOW);
        taisyoDay.setEditable(false);
        taisyoDay.addItem("�L����");
        taisyoDay.addItem("�˗���");
        taisyoDay.setBindPath("TAISYO_DAY");
        // ���������E�Ώۊ��ԁE�J�n
        taisyoFromContainer.setText("�J�n���t");
        taisyoFromContainer.add(taisyoFrom, VRLayout.FLOW);
        taisyoFrom.setAgeVisible(false);
        taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoFrom.setBindPath("TAISYO_FROM");
        taisyoCaption.setText("�`");
        // ���������E�Ώۊ��ԁE�I��
        taisyoToContainer.setText("�I�����t");
        taisyoToContainer.add(taisyoTo, VRLayout.FLOW);
        taisyoTo.setAgeVisible(false);
        taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        taisyoTo.setBindPath("TAISHO_TO");
        // ���������E�Ώۊ��ԁE���t����
        taisyoClear.setText("���t����(E)");
        taisyoClear.setMnemonic('E');
        taisyoClear.setPreferredSize(new Dimension(110, 22));

        // ���{�^��
        btnGrp.setLayout(new VRLayout());
        btnGrp.add(taisyoCancel, VRLayout.EAST);
        btnGrp.add(output, VRLayout.EAST);
        btnGrp.add(cancel, VRLayout.EAST);
        cancel.setText("���(C)");
        cancel.setMnemonic('C');
        cancel.setToolTipText("[�s�v]�ɂ��ꂽ�ӌ�����[�o�͑Ώ�]�����ɖ߂��܂��B");
        output.setText("�� �o��(T)");
        output.setMnemonic('T');
        output.setToolTipText("�I�����ꂽ�ӌ�����[�o��]�ɂ��܂��B");
        taisyoCancel.setText("�~ �Ώێ��(U)");
        taisyoCancel.setMnemonic('U');
        taisyoCancel.setToolTipText("�I�����ꂽ�ӌ�����[���]�ɂ��܂��B");

        // ���j���[
        popup.add(cancelMenu);
        popup.add(outputMenu);
        popup.add(taisyoCancelMenu);

        cancelMenu.setActionCommand(cancel.getActionCommand());
        cancelMenu.setMnemonic(cancel.getMnemonic());
        cancelMenu.setText(cancel.getText());
        cancelMenu.setToolTipText(cancel.getToolTipText());

        outputMenu.setActionCommand(output.getActionCommand());
        outputMenu.setMnemonic(output.getMnemonic());
        outputMenu.setText(output.getText());
        outputMenu.setToolTipText(output.getToolTipText());

        taisyoCancelMenu.setActionCommand(taisyoCancel.getActionCommand());
        taisyoCancelMenu.setMnemonic(taisyoCancel.getMnemonic());
        taisyoCancelMenu.setText(taisyoCancel.getText());
        taisyoCancelMenu.setToolTipText(taisyoCancel.getToolTipText());
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        IkenshoSnapshot.getInstance().setRootContainer(searchGrp); // �X�i�b�v�V���b�g�B�e�Ώ�
        // ���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        addUpdateTrigger(update);
        addFindTrigger(find);
        addPrintTrigger(print);
        addPrintTableTrigger(printTable);

        // Combo��Item��ݒ�
        setComboItem();

        // �e�[�u���̃����_���ݒ�
        tableCellRenderer = new ACFollowConditionForegroundTableCellRenderer(
                new String[] { "OUTPUT_FLG", "CANCEL_FLG" }, new String[] {
                        "true", "true" }, new Color[] {
                        new java.awt.Color(49, 83, 255),
                        new Color(236, 148, 32) });
        // �e�[�u���擾(�񐶐��̂���)
        doSelect(true);

        // �X�e�[�^�X�o�[(����ʂ̂�)
        setStatusText("�u�厡��ӌ����v�u��t�ӌ����vCSV�t�@�C���o��");

        // ����(DatePanel)�̏�����
        initTaisyoDatePanel();

        // ����񐔏�����
        printOutCount = 1;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        if (update.isEnabled()) { // �X�V�{�^����Enabled���X�i�b�v�V���b�g����Ɏg�p
            int result = ACMessageBox.showYesNoCancel("�ύX����Ă��܂��B�ۑ����܂����H",
                    "�X�V���Ė߂�(S)", 'S', "�j�����Ė߂�(R)", 'R');
            if (result == ACMessageBox.RESULT_YES) { // �ۑ����Ė߂�
                if (doUpdateToDB()) {
                    // �{�^����Enabled�ݒ�
                    setButtonsEnabled();

                    // �X�e�[�^�X�o�[
                    setStatusText(String.valueOf(table.getRowCount())
                            + "�����o����܂����B");

                    // �����ʒmMSG
                    ACMessageBox.show("�X�V����܂����B", ACMessageBox.BUTTON_OK,
                            ACMessageBox.ICON_INFOMATION);
                }
            } else if (result == ACMessageBox.RESULT_NO) { // �ۑ����Ȃ��Ŗ߂�
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public boolean canClose() throws Exception {
        if (update.isEnabled()) { // �X�V�{�^����Enabled���X�i�b�v�V���b�g����Ɏg�p
            String msg = "";
            msg = "�X�V���ꂽ���e�͔j������܂��B\n�I�����Ă���낵���ł����H";
            int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return insurer;
    }

    private void event() throws Exception {
        // ���t����
        taisyoClear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // ����(DatePanel)�̏�����
                initTaisyoDatePanel();
            }
        });

        // �u����v����
        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCancel();
            }
        });
        // popupMenu:�u����v�I��
        cancelMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectCancel();
            }
        });

        // �u�o�́v����
        output.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectOutput();
            }
        });
        // popupMenu:�u�o�́v����
        outputMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectOutput();
            }
        });

        // �u�Ώێ���v����
        taisyoCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectTaisyoCancel();
            }
        });
        // popupMenu:�u�Ώێ���v����
        taisyoCancelMenu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                selectTaisyoCancel();
            }
        });

        // �e�[�u���I����
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                showPopup(e);
            }

            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
        });
    }

    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (update.isEnabled()) { // �X�V�{�^����Enabled���X�i�b�v�V���b�g����Ɏg�p
            int result = ACMessageBox.show("�X�V����Ă��܂��B�ۑ����Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
            if (result != ACMessageBox.RESULT_OK) {
                return;
            }
        }

        if (doUpdateToDB()) {
            // �{�^����Enabled�ݒ�
            setButtonsEnabled();

            // �X�e�[�^�X�o�[
            setStatusText(String.valueOf(table.getRowCount()) + "�����o����܂����B");

            // �����ʒmMSG
            ACMessageBox.show("�X�V����܂����B", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
        }
    }

    protected void findActionPerformed(ActionEvent e) throws Exception {
        if (isValidInput()) {
            // �f�[�^�擾
            doSelect(false);
        }
    }

    protected void printActionPerformed(ActionEvent e) throws Exception {
        doOutput();
    }

    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        // �����������ύX���ꂽ�ꍇ�A���b�Z�[�W��\�����A����𒆒f����
        if (IkenshoSnapshot.getInstance().isModified()) {
            ACMessageBox.show("�����������ύX����܂����B\n���������ɖ߂����A�ēx���������ĉ������B");
            return;
        }

        // �f�[�^�Ɂu����v���܂ޏꍇ�A�X�V�𑣂�
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                int result = ACMessageBox.show("�ύX����Ă��܂��B�ۑ����Ă���낵���ł����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_CANCEL) {
                    return;
                } else {
                    if (doUpdateToDB()) {
                        // �{�^����Enabled�ݒ�
                        setButtonsEnabled();

                        // �X�e�[�^�X�o�[
                        setStatusText(String.valueOf(table.getRowCount())
                                + "�����o����܂����B");
                        break;
                    } else {
                        return;
                    }
                }
            }
        }

        // ���
        doPrintTable();
    }

    /**
     * �{�^����Enabled��ݒ肵�܂��B
     */
    private void setButtonsEnabled() {
        // �u�o�́v��Row��1���ȏ゠��΁u�t�@�C���쐬�v���L���ɂȂ�
        print.setEnabled(false);
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            if (map.getData("OUTPUT_FLG").toString().equals("true")) {
                print.setEnabled(true);
                break;
            }
        }

        // �u����v��Row��1���ȏ゠��΁u�X�V�v���L���ɂȂ�
        update.setEnabled(false);
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            if (map.getData("CANCEL_FLG").toString().equals("true")) {
                update.setEnabled(true);
                break;
            }
        }

        // �������ʂ�1���ȏ゠��΁u����v���L���ɂȂ�
        printTable.setEnabled(false);
        if (data.getDataSize() > 0) {
            printTable.setEnabled(true);
        }

        // ����A�o�͑ΏہA�Ώێ��
        boolean enabledFlg = false;
        if (data.getDataSize() > 0) {
            enabledFlg = true;
        }
        cancel.setEnabled(enabledFlg);
        output.setEnabled(enabledFlg);
        taisyoCancel.setEnabled(enabledFlg);
    }

    /**
     * �|�b�v�A�b�v���j���[��\�����܂��B
     *
     * @param e MouseEvent
     */
    private void showPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            popup.show((Component) e.getSource(), e.getX(), e.getY());
        }
    }

    /**
     * �e��Combo��Item��ݒ肵�܂��B
     */
    private void setComboItem() {
        // �˗���(�ی���)
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DISTINCT");
        sb.append(" INSURER_NM");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" ORDER BY");
        sb.append(" INSURER_NM");
        IkenshoCommon.setComboModel(insurer, sb.toString(), "INSURER_NM");

        // ��t����
        sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DR_NM");
        sb.append(" FROM");
        sb.append(" DOCTOR");
        sb.append(" GROUP BY");
        sb.append(" DR_NM");
        sb.append(" ORDER BY");
        sb.append(" DR_NM");
        try {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            VRArrayList tbl = (VRArrayList) dbm.executeQuery(sb.toString());
            VRMap map = new VRHashMap();
            map.put("DR_NM", "");
            tbl.add(0, map);
            if (tbl.size() > 0) {
                doctor
                        .setModel(new ACComboBoxModelAdapter(
                                new VRHashMapArrayToConstKeyArrayAdapter(tbl,
                                        "DR_NM")));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ����(DatePanel)�̏�����
     */
    private void initTaisyoDatePanel() {
        taisyoDay.setSelectedIndex(0);
        taisyoFrom.clear();
        taisyoFrom.setEra("����");
        taisyoTo.clear();
        taisyoTo.setEra("����");
    }

    /**
     * �u����v�I�����̓���ł��B
     */
    private void selectCancel() {
        // �I���s���擾
        if (table.getSelectedModelRow() < 0) {
            return;
        }
        if (table.getSelectedModelRow() >= table.getRowCount()) {
            return;
        }
        int selectedDataRow = table.getSelectedModelRow();

        // �I���s�́u�o�́v�u����v�̕ύX
        VRMap map = (VRMap) data.getData(selectedDataRow);
        map.setData("OUTPUT_FLG", new Boolean(false));
        map.setData("CANCEL_FLG", new Boolean(false));

        // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
        table.stopCellEditing("OUTPUT_FLG");

        // �{�^����enabled�ݒ�
        setButtonsEnabled();

        // �t�H�[�J�X
        table.requestFocus();
        // table.getTable().requestFocus();
    }

    /**
     * �u�o�́v�I�����̓���ł��B
     */
    private void selectOutput() {
        // �I���s���擾
        if (table.getSelectedModelRow() < 0) {
            return;
        }
        if (table.getSelectedModelRow() >= table.getRowCount()) {
            return;
        }
        int selectedDataRow = table.getSelectedModelRow();

        // �I���s�́u�o�́v�u����v�̕ύX
        VRMap map = (VRMap) data.getData(selectedDataRow);
        map.setData("OUTPUT_FLG", new Boolean(true));
        map.setData("CANCEL_FLG", new Boolean(false));

        // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
        table.stopCellEditing("OUTPUT_FLG");

        // �{�^����enabled�ݒ�
        setButtonsEnabled();

        // �t�H�[�J�X
        table.requestFocus();
        // table.getTable().requestFocus();
    }

    /**
     * �u�Ώێ���v�I�����̓���ł��B
     */
    private void selectTaisyoCancel() {
        // �I���s���擾
        if (table.getSelectedSortedRow() < 0) {
            return;
        }
        if (table.getSelectedSortedRow() >= table.getRowCount()) {
            return;
        }
        // if (table.getTable().getSelectedRow() < 0) {
        // return;
        // }
        // if (table.getTable().getSelectedRow() >=
        // table.getTable().getRowCount()) {
        // return;
        // }
        int selectedTableRow = table.getSelectedSortedRow();
        // int selectedTableRow = table.getTable().getSelectedRow();
        int selectedDataRow = table.getSelectedModelRow();

        // �I���s�́u�o�́v�u����v�̕ύX
        VRMap map = (VRMap) data.getData(selectedDataRow);
        map.setData("OUTPUT_FLG", new Boolean(false));
        map.setData("CANCEL_FLG", new Boolean(true));

        // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
        table.stopCellEditing("OUTPUT_FLG");

        // �{�^����enabled�ݒ�
        setButtonsEnabled();

        // �t�H�[�J�X
        table.requestFocus();
        // table.getTable().requestFocus();
    }

    /**
     * ��������(����)�`�F�b�N
     *
     * @return boolean
     */
    private boolean isValidInput() {
        // �ی���
        if (insurer.getSelectedIndex() < 0) {
            ACMessageBox.showExclamation("�˗���(�ی���)��I�����Ă��������B");
            insurer.requestFocus();
            return false;
        }

        // ���t(�J�n)
        if (hasDate(taisyoFrom)) {
            taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoFrom.getInputStatus() == IkenshoEraDateTextField.STATE_VALID
                    || taisyoFrom.getInputStatus() == IkenshoEraDateTextField.STATE_EMPTY) {
                taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                taisyoFrom.getInputStatus();
            } else {
                ACMessageBox.showExclamation("�����J�n���t�̓��͂Ɍ�肪����܂��B");
                taisyoFrom.transferFocus();
                taisyoFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }

        // ���t(�I��)
        if (hasDate(taisyoTo)) {
            taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (taisyoTo.getInputStatus() == IkenshoEraDateTextField.STATE_VALID
                    || taisyoTo.getInputStatus() == IkenshoEraDateTextField.STATE_EMPTY) {
                taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            } else {
                ACMessageBox.showExclamation("�����I�����t�̓��͂Ɍ�肪����܂��B");
                taisyoTo.transferFocus();
                taisyoTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
        }

        // ���t(�J�n�ƏI���̑O��`�F�b�N)
        if (hasDate(taisyoFrom)) {
            if (hasDate(taisyoTo)) {
                if (taisyoFrom.getDate().after(taisyoTo.getDate())) {
                    ACMessageBox.showExclamation("�J�n���t�ƏI�����t���t�]���Ă��܂��B");
                    taisyoFrom.transferFocus();
                    taisyoFrom
                            .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * ���t���ڂ����������Ƃ��Ēl���ݒ肳��Ă��邩�ǂ������擾���܂��B
     *
     * @param dp DatePanel
     * @return boolean
     */
    private boolean hasDate(IkenshoEraDateTextField dp) {
        if (dp.getEra().equals("")) {
            return false;
        }

        if (dp.getYear().equals("")) {
            return false;
        }

        return true;
    }

    /**
     * �e�[�u���Ƀf�[�^���Z�b�g���܂��B
     *
     * @param firstFlg ����ʃt���O
     * @throws Exception
     */
    private void doSelect(boolean firstFlg) throws Exception {
        // DB����f�[�^���擾
        doSelectFromDB();

        // �e�[�u���񐶐�
        createTableColumns();

        // ���b�Z�[�W
        if (!firstFlg) { // ��ʑJ�ڎ��łȂ��Ƃ�(����������)
            if (data.getDataSize() <= 0) {
                ACMessageBox.show("�����ɍ��v����u�ӌ����v������܂���ł����B");

                // �t�H�[�J�X
                insurer.requestFocus();
            } else {
                // �����_���ɗ^����f�[�^�̍Đݒ�
                tableCellRenderer.setData(data);

                // 1�s�ڂ�I��
                table.setSelectedModelRow(0);

                // �t�H�[�J�X
                table.requestFocus();
                // table.getTable().requestFocus();
            }

            // ����񐔂̏�����
            printOutCount = 1;

            // �X�e�[�^�X�o�[
            setStatusText(String.valueOf(table.getRowCount()) + "�����o����܂����B");
        }

        // �{�^����Enabled�ݒ�
        setButtonsEnabled();

        // �X�i�b�v�V���b�g�B�e
        IkenshoSnapshot.getInstance().snapshot();
    }

    /**
     * DB����f�[�^���擾���܂��B
     *
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        // ���������E�˗���(�ی���)
        String insurerNmSql;
        if (insurer.getSelectedIndex() >= 0) {
            bkInsurerNm = insurer.getSelectedItem().toString().replaceAll("''",
                    "'");
            insurerNmSql = " AND INSURER.INSURER_NM='" + bkInsurerNm + "'";
        } else {
            bkInsurerNm = "";
            insurerNmSql = " AND INSURER.INSURER_NM=''";
        }

        // �t�H�[�}�b�g�敪
        String formatKbnSql;
        if (formatKbn.getSelectedItem().toString().equals(h18)) {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=1";
        } else {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=0";
        }
        
        // add begin 2006/08/03 kamitsukasa
        // �ی��ҋ敪
        String insurerTypeSql;
        if (IKN_NEW.equals(formatKbnIshi.getSelectedItem().toString())) {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=1";
            insurerTypeSql = "AND INSURER.INSURER_TYPE IN (0, 1)";
        } else {
            formatKbnSql = "AND IKN_ORIGIN.FORMAT_KBN=2";
            insurerTypeSql = "AND INSURER.INSURER_TYPE IN (0, 2)";
        }
        // add end 2006/08/03 kamitsukasa

        // ���������E��t����
        String drNmSql;
        if (doctor.getSelectedIndex() >= 1) { // 1�ڂ͋󔒂Ȃ̂ŁB
            drNmSql = " AND COMMON_IKN_SIS.DR_NM='"
                    + doctor.getSelectedItem().toString().replaceFirst("''",
                            "'") + "'";
        } else {
            drNmSql = "";
        }

        StringBuffer sbKikanSql = new StringBuffer();
        ACDateFormat idf = new ACDateFormat("yyyy-MM-dd");
        // ���������E����(�J�n)
        try {
            if (taisyoFrom.getDate() != null) {
                switch (taisyoDay.getSelectedIndex()) {
                case 0:
                    sbKikanSql.append(" AND IKN_ORIGIN.KINYU_DT>='");
                    break;
                case 1:
                    sbKikanSql.append(" AND IKN_ORIGIN.REQ_DT>='");
                    break;
                }
                sbKikanSql.append(idf.format(taisyoFrom.getDate()));
                sbKikanSql.append("'");
            }
        } catch (Exception ex) {
        }
        // ���������E����(�I��)
        try {
            if (taisyoTo.getDate() != null) {
                switch (taisyoDay.getSelectedIndex()) {
                case 0:
                    sbKikanSql.append(" AND IKN_ORIGIN.KINYU_DT<='");
                    break;
                case 1:
                    sbKikanSql.append(" AND IKN_ORIGIN.REQ_DT<='");
                    break;
                }
                sbKikanSql.append(idf.format(taisyoTo.getDate()));
                sbKikanSql.append("'");
            }
        } catch (Exception ex) {
        }

        // �f�[�^�擾
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" IKN_BILL.DR_NO");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_BILL.LAST_TIME");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL,INSURER");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.INSURER_NO=INSURER.INSURER_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.DOC_KBN=1");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        sb.append(insurerNmSql);
        sb.append(drNmSql);
        sb.append(sbKikanSql.toString());
        sb.append(formatKbnSql);
        // add begin 2006/08/07 kamitsukasa
        sb.append(insurerTypeSql);
        // add end 2006/08/07 kamitsukasa
        sb.append(" ORDER BY INSURED_NO ASC");
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        // �t�B�[���h�̒ǉ�
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            map.put("OUTPUT_FLG", new Boolean(false));
            map.put("CANCEL_FLG", new Boolean(false));
        }

        // �p�b�V�u�`�F�b�N�p
        if (data.getDataSize() > 0) {
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }
    }

    /**
     * �e�[�u����𐶐����܂��B
     *
     * @throws Exception
     */
    private void createTableColumns() throws Exception {
        // �\����CheckBox
        IkenshoCheckBoxTableCellEditor outputCheckEditor = new IkenshoCheckBoxTableCellEditor();
        outputCheckEditor.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // �I���s���擾
                    if (table.getSelectedModelRow() < 0) {
                        return;
                    }
                    if (table.getSelectedModelRow() >= table.getRowCount()) {
                        return;
                    }
                    int selectedRow = table.getSelectedModelRow();

                    // �I���s�́u�o�́v�u����v�̕ύX
                    VRMap map = (VRMap) data.getData(selectedRow);
                    map.setData("CANCEL_FLG", new Boolean(false));

                    // �`�F�b�N�{�b�N�X�ҏW��Ԃ̊m��
                    table.stopCellEditing("OUTPUT_FLG");
                }
                // �{�^����enabled�ݒ�
                setButtonsEnabled();
            }
        });

        // �e�[�u���̐���
        tableModel = new ACTableModelAdapter(data, new String[] { "DR_NM",
                "PATIENT_NM", "SEX", "BIRTHDAY", "INSURED_NO", "REQ_DT",
                "KINYU_DT", "OUTPUT_FLG", "CANCEL_FLG" });
        table.setModel(tableModel);

        // ColumnModel�̐���
        table.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(7, 22, "�@",
                        new IkenshoCheckBoxTableCellRenderer(),
                        outputCheckEditor),
                new ACTableColumn(8, 35, "���", IkenshoConstants.FORMAT_CANCEL,
                        tableCellRenderer, null),
                new ACTableColumn(0, 150, "��t����", tableCellRenderer, null),
                new ACTableColumn(1, 150, "���Ҏ���", tableCellRenderer, null),
                new ACTableColumn(2, 32, "����", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_SEX, tableCellRenderer, null),
                new ACTableColumn(3, 32, "�N��", SwingConstants.RIGHT,
                        IkenshoConstants.FORMAT_NOW_AGE, tableCellRenderer,
                        null),
                new ACTableColumn(4, 100, "��ی��Ҕԍ�", tableCellRenderer, null),
                new ACTableColumn(5, 120, "�쐬�˗���",
                        IkenshoConstants.FORMAT_ERA_YMD, tableCellRenderer,
                        null),
                new ACTableColumn(6, 120, "�ӌ����L����",
                        IkenshoConstants.FORMAT_ERA_YMD, tableCellRenderer,
                        null) }));

        // �����_���̐ݒ�
        // table.getTable().setDefaultRenderer(Object.class, tableCellRenderer);
        table.setDefaultRenderer(Object.class, tableCellRenderer);
    }

    /**
     * DB���X�V���܂��B
     *
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        if (data.getDataSize() <= 0) {
            return true;
        }

        // UPDATE(�u����v�Ȃ�΃t���OOFF
        IkenshoFirebirdDBManager dbm = null;
        try {
            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                    // �p�b�V�u�`�F�b�N
                    clearPassiveTask();
                    addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
                    dbm = getPassiveCheckedDBManager();
                    if (dbm == null) {
                        ACMessageBox
                                .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                        return false;
                    }
                }
            }

            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                    // UPDATE�N�G���쐬
                    String patientNo = String
                            .valueOf(map.getData("PATIENT_NO"));
                    String edaNo = String.valueOf(map.getData("EDA_NO"));
                    StringBuffer sb = new StringBuffer();
                    sb.append(" UPDATE");
                    sb.append(" IKN_BILL");
                    sb.append(" SET");
                    sb.append(" FD_OUTPUT_KBN=0");
                    sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
                    sb.append(" WHERE");
                    sb.append(" PATIENT_NO=" + patientNo);
                    sb.append(" AND");
                    sb.append(" EDA_NO=" + edaNo);

                    // �X�V�pSQL���s
                    dbm.executeUpdate(sb.toString());
                }
            }
            dbm.commitTransaction();
        } catch (Exception ex) {
            dbm.rollbackTransaction();
            throw new Exception(ex.getMessage());
        }

        // �X�V�����f�[�^��data����폜
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("CANCEL_FLG")).equals("true")) {
                data.remove(i);
            }
        }

        // �p�b�V�u�`�F�b�N�p
        if (data.getDataSize() > 0) {
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }

        // �e�[�u���ĕ`��
        tableCellRenderer.setData(data); // �����_���ɗ^����f�[�^�̍Đݒ�
        table.setModel(tableModel);
        table.revalidate();
        return true;
    }

    /**
     * CSV�t�@�C���o�͂��s���܂��B
     *
     * @throws Exception
     */
    private void doOutput() throws Exception {
        int result = 0;

        // add sta s-fujihara
        // ��ی��Ҕԍ��E�L�����̏d���`�F�b�N
        HashMap checkMap = new HashMap();
        String key = "";
        for (int i = 0; i < data.getDataSize(); i++) {
            VRMap map = (VRMap) data.getData(i);
            // �o�͑ΏۊO�̃f�[�^�͓ǂݔ�΂�
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false"))
                continue;

            // ��ی��Ҕԍ�+�L�����̃L�[���쐬����B
            key = String.valueOf(map.getData("INSURED_NO"))
                    + String.valueOf(map.getData("KINYU_DT"));
            // ���ɓo�^����Ă���L�[�ł���Ώd���L��
            // �G���[���b�Z�[�W���o�͂��ď������I������B
            if (checkMap.containsKey(key)) {
                StringBuffer errMsg = new StringBuffer();
                errMsg.append("�ȉ��̊��҂͔�ی��Ҕԍ��ƋL�������d�����Ă��܂��B�b�r�u�t�@�C���̏o�͂��ł��܂���B\n");
                errMsg.append("�@" + checkMap.get(key) + "\n");
                errMsg.append("�@" + map.getData("PATIENT_NM"));
                ACMessageBox.show(errMsg.toString(), ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                ACMessageBox.show("�b�r�u�t�@�C���̏����o���͍s���܂���ł����B",
                        ACMessageBox.BUTTON_OK, ACMessageBox.ICON_INFOMATION);
                checkMap = null;
                return;
                // ���o�^�ł���΁A���Ҏ�����l�ɓo�^����B
            } else {
                checkMap.put(key, String.valueOf(map.getData("PATIENT_NM")));
            }
        }
        checkMap = null;
        // add end s-fujihara

        // �u�ʏ�g����t�v�̌��������u�ی��ҁv�́u���ƎҔԍ��v���擾

        // del sta tsutsumi
        // StringBuffer sb = new StringBuffer();
        // sb.append(" SELECT");
        // sb.append(" jigyousha.jigyousha_no");
        // sb.append(" FROM");
        // sb.append(" doctor,jigyousha,insurer");
        // sb.append(" WHERE");
        // sb.append(" doctor.mi_default=1");
        // sb.append(" AND");
        // sb.append(" doctor.dr_cd=jigyousha.dr_cd");
        // sb.append(" AND");
        // sb.append(" jigyousha.insurer_no=insurer.insurer_no");
        // sb.append(" AND");
        // sb.append(" insurer.insurer_nm='" +
        // insurer.getSelectedItem().toString().replaceAll("''", "'") + "'");
        // del end tsutsumi

        // add sta tsutsumi
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" jigyousha.jigyousha_no");
        sb.append(" FROM");
        sb.append(" doctor,jigyousha,insurer");
        sb.append(" WHERE");
        sb.append(" doctor.mi_default=(");
        sb.append(" CASE (SELECT COUNT(*) FROM doctor)");
        sb.append(" WHEN 1 THEN");
        sb.append(" doctor.mi_default");
        sb.append(" ELSE");
        sb.append(" 1");
        sb.append(" END");
        sb.append(" )");
        sb.append(" AND");
        sb.append(" doctor.dr_cd=jigyousha.dr_cd");
        sb.append(" AND");
        sb.append(" jigyousha.insurer_no=insurer.insurer_no");
        sb.append(" AND");
        sb.append(" insurer.insurer_nm='"
                + insurer.getSelectedItem().toString().replaceAll("''", "'")
                + "'");
        // add end tsutsumi

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataJigyousha = (VRArrayList) dbm.executeQuery(sb
                .toString());

        // �����I���t�@�C�����𐶐�
        String fileName;
        if (dataJigyousha.getDataSize() > 0) {
            Date today = Calendar.getInstance().getTime();
            VRDateFormat vrdf = new VRDateFormat("yyyyMMdd");

            VRMap tmp = (VRMap) dataJigyousha.getData(0);
            fileName = tmp.get("JIGYOUSHA_NO").toString()
                    + "_"
                    + vrdf.format(today)
                    + new ACFillCharFormat("0", 2).toFilled(String
                            .valueOf(printOutCount)) + ".csv";
        } else {
            // del sta tsutsumi
            // NCMessageBox.show(
            // "��Ë@�ւɎ��Ə��ԍ����ݒ肳��Ă��܂���B\n�t�@�C�������w�肵�ĉ������B",
            // NCMessageBox.BUTTON_OK,
            // NCMessageBox.ICON_INFOMATION);
            // del end tsutsumi
            fileName = "";
        }

        // CSV�t�@�C���w��DLG
        File CSVFile = null;
        boolean loopFlg = false;
        do {
            loopFlg = false;

            // �t�@�C���w��
            ACFileChooser fileChooser = new ACFileChooser();
            CSVFile = fileChooser.showSaveDialog("", fileName,
                    "CSV�t�@�C����ۑ�����ꏊ���w�肵�ĉ������B", new IkenshoCSVFileFilter());
            if (CSVFile == null) {
                return;
            }

            // �g���q��⊮
            CSVFile = new File(CSVFile.getParent()
                    + IkenshoConstants.FILE_SEPARATOR
                    + ((IkenshoCSVFileFilter) fileChooser.getFileFilter())
                            .getFilePathWithExtension(CSVFile.getName()));

            // �����t�@�C���`�F�b�N
            if (CSVFile.exists()) {
                result = ACMessageBox.show(
                        "�����t�@�C�����̃t�@�C�������݂��Ă��܂��B\n�㏑�����Ă���낵���ł����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_CANCEL) {
                    loopFlg = true;
                }
            }
        } while (loopFlg);

        // �V���ǂ���̃t�H�[�}�b�g��
        boolean newFormat = false;
        if (formatKbn.getSelectedItem().equals(h18)) {
            newFormat = true;
        }
        
        // add begin 2006/08/03 kamitsukasa
        String iknFormat = "";
        if (IKN_NEW.equals(formatKbnIshi.getSelectedItem())) {
        	iknFormat = IKN_NEW;
        }else{
        	iknFormat = IKN_ISHI;
        }
		// add end 2006/08/03 kamitsukasa

        // �摜�d���`�F�b�N
        if (!newFormat) {
            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("OUTPUT_FLG")).equals("true")) {
                    StringBuffer sbBuf = new StringBuffer();
                    sbBuf.append(getDataString(map, "INSURED_NO"));
                    sbBuf.append(formatYYYYMMDD(map.getData("KINYU_DT")));
                    sbBuf.append(".bmp");
                    File tmpFile = new File(CSVFile.getParent(), sbBuf
                            .toString());
                    if (tmpFile.exists()) {
                        ACMessageBox.show(
                                "�����t�@�C����(��ی��Ҕԍ�+�L����)�̑S�g�}�t�@�C�������݂��Ă��܂��B\n\n"
                                        + "CSV�t�@�C���̏o�͂��o���܂���B\n" + "���Ҏ����F"
                                        + map.getData("PATIENT_NM"),
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);

                        ACMessageBox.show("CSV�t�@�C���̏����o���͍s���܂���ł����B",
                                ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_INFOMATION);
                        return;
                    }
                }
            }
        }

        // �ŏI�m�F
        result = ACMessageBox.show("CSV�t�@�C�����쐬���܂��B\n��낵���ł����H",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                ACMessageBox.FOCUS_CANCEL);
        if (result != ACMessageBox.RESULT_OK) {
            return;
        }

        // �{�^��Enabled����(�ł�)
        update.setEnabled(false);
        find.setEnabled(false);
        print.setEnabled(false);
        printTable.setEnabled(false);
        cancel.setEnabled(false);
        output.setEnabled(false);
        taisyoCancel.setEnabled(false);

        // CSV�t�@�C���̏o��
        boolean writeSuccess;
        if (newFormat) {
        	// replace begin 2006/08/03 kamitsukasa
        	if(IKN_NEW.equals(iknFormat)){
        		writeSuccess = doOutputCSVNew((VRArrayList) data.clone(), CSVFile);
        	}else{
        		writeSuccess = doOutputCSVIshi((VRArrayList) data.clone(), CSVFile);
        	}
        	// replace end 2006/08/03 kamitsukasa
        } else {
            writeSuccess = doOutputCSVOld((VRArrayList) data.clone(), CSVFile);
        }

        if (writeSuccess) {
            // UPDATE(�X�V�����f�[�^�̃t���O��ύX����)
            dbm = null;
            try {
                
                //2006/02/12[Tozo Tanaka] : replace begin                
//              for (int i = 0; i < data.getDataSize(); i++) {
//                  VRMap map = (VRMap) data.getData(i);
//                  if (String.valueOf(map.getData("OUTPUT_FLG"))
//                          .equals("true")) {
//                      // �p�b�V�u�`�F�b�N
//                      clearPassiveTask();
//                      addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
//                      dbm = getPassiveCheckedDBManager();
//                      if (dbm == null) {
//                          ACMessageBox
//                                  .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
//                          // �{�^��Enabled�Đݒ�
//                          setButtonsEnabled();
//                          find.setEnabled(true);
//                          cancel.setEnabled(true);
//                          output.setEnabled(true);
//                          taisyoCancel.setEnabled(true);
//                          return;
//                      }
//
//                      // UPDATE�N�G���쐬
//                      String patientNo = String.valueOf(map
//                              .getData("PATIENT_NO"));
//                      String edaNo = String.valueOf(map.getData("EDA_NO"));
//                      sb = new StringBuffer();
//                      sb.append(" UPDATE");
//                      sb.append(" IKN_BILL");
//                      sb.append(" SET");
//                      sb.append(" FD_OUTPUT_KBN=2");
//                      sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
//                      sb.append(" WHERE");
//                      sb.append(" PATIENT_NO=" + patientNo);
//                      sb.append(" AND");
//                      sb.append(" EDA_NO=" + edaNo);
//
//                      // �X�V�pSQL���s
//                      dbm.executeUpdate(sb.toString());
//                  }
//              }
              
                //�p�b�V�u�`�F�b�N�͎��O�ɂ��ׂẴ^�X�N��􂢏o��
                clearPassiveTask();
                for (int i = 0; i < data.getDataSize(); i++) {
                    VRMap map = (VRMap) data.getData(i);
                    if (String.valueOf(map.getData("OUTPUT_FLG"))
                            .equals("true")) {
                        // �p�b�V�u�`�F�b�N
                        addPassiveUpdateTask(PASSIVE_CHECK_KEY, i);
                    }
                }
                //��������΃g�����U�N�V�������J�n����DBM���ԋp�����
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox
                            .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    // �{�^��Enabled�Đݒ�
                    setButtonsEnabled();
                    find.setEnabled(true);
                    cancel.setEnabled(true);
                    output.setEnabled(true);
                    taisyoCancel.setEnabled(true);
                    return;
                }
                
                for (int i = 0; i < data.getDataSize(); i++) {
                    VRMap map = (VRMap) data.getData(i);
                    if (String.valueOf(map.getData("OUTPUT_FLG"))
                            .equals("true")) {
                        // UPDATE�N�G���쐬
                        String patientNo = String.valueOf(map
                                .getData("PATIENT_NO"));
                        String edaNo = String.valueOf(map.getData("EDA_NO"));
                        sb = new StringBuffer();
                        sb.append(" UPDATE");
                        sb.append(" IKN_BILL");
                        sb.append(" SET");
                        sb.append(" FD_OUTPUT_KBN=2");
                        sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
                        sb.append(" WHERE");
                        sb.append(" PATIENT_NO=" + patientNo);
                        sb.append(" AND");
                        sb.append(" EDA_NO=" + edaNo);

                        // �X�V�pSQL���s
                        dbm.executeUpdate(sb.toString());
                    }
                }
                //���ׂĂ�UPDATE������������R�~�b�g����
                //2006/02/12[Tozo Tanaka] : replace end 
                
                dbm.commitTransaction();
            } catch (Exception ex) {
                dbm.rollbackTransaction();
                throw new Exception(ex.getMessage());
            }
            // �X�V�����f�[�^��data����폜
            for (int i = data.getDataSize() - 1; i >= 0; i--) {
                VRMap map = (VRMap) data.getData(i);
                if (String.valueOf(map.getData("OUTPUT_FLG")).equals("true")) {
                    data.remove(i);
                }
            }

            // �p�b�V�u�`�F�b�N�p
            if (data.getDataSize() > 0) {
                clearReservedPassive();
                reservedPassive(PASSIVE_CHECK_KEY, data);
            }
            // �e�[�u���ĕ`��
            tableCellRenderer.setData(data); // �����_���ɗ^����f�[�^�̍Đݒ�
            // table.getTable().tableChanged(new
            // TableModelEvent(table.getModel()));
            table.tableChanged(new TableModelEvent(table.getModel()));

            // �����ʒmMSG
            ACMessageBox.show("CSV�t�@�C���̏����o�����s���܂����B", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);

            // ����񐔃C���N�������g
            printOutCount++;
        } else {
            // ���s�ʒmMSG
            ACMessageBox.show("CSV�t�@�C���̏����o���Ɏ��s���܂����B", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
        }

        // �{�^��Enabled�Đݒ�
        setButtonsEnabled();
        find.setEnabled(true);
        cancel.setEnabled(true);
        output.setEnabled(true);
        taisyoCancel.setEnabled(true);

    }

    /**
     * CSV�o�͏������s���܂��B(�@�����O�t�H�[�}�b�g)
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     * @return ����������
     */
    public boolean doOutputCSVOld(VRArrayList data, File file) throws Exception {
        // �v���O���X����
        IkenshoWaitingForm iwf = new IkenshoWaitingForm(ACFrame.getInstance(),
                "CSV�t�@�C���o�͒�");

        // �o�͑ΏۂłȂ��f�[�^���Ԉ���
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
                data.remove(i);
            }
        }

        // CSV�o�͗p�̃f�[�^��DB����擾����
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSqlOld());

        // CSV�o�͏���(������)
        int max = data.getDataSize();
        iwf.setMaxCount(max); // �v���O���X�o�[�ő�l
        StringBuffer sbBuf = new StringBuffer();
        String softName = IkenshoCommon.getProperity("Version/SoftName");
        VRCSVFile cvs = new VRCSVFile(file.getPath()); // CSV����
        iwf.setVisible(true);// �v���O���X�o�[�\��
        // iwf.show(); //�v���O���X�o�[�\��

        // CSV�o�͏���
        for (int i = 0; i < max; i++) {
            VRMap tmp = (VRMap) data.getData(i);
            String patientNo = tmp.getData("PATIENT_NO").toString();
            String edaNo = tmp.getData("EDA_NO").toString();
            int idx = matchingData(dataDB, patientNo, edaNo);
            if (idx < 0) {
                continue;
            }
            VRMap map = (VRMap) dataDB.getData(idx);
            String imgFileName = "";

            // �s����
            ArrayList row = new ArrayList();
            // 001:FormatVersion
            row.add("1.0");
            // 002:SoftName
            row.add(softName);
            // 003:�^�C���X�^���v
            row.add(getDataString(map, "INSURED_NO")
                    + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            // 004:�ی��Ҕԍ�
            row.add(getDataString(map, "INSURER_NO"));
            // 005:�ی��Җ���
            row.add(getDataString(map, "INSURER_NM"));
            // 006:��ی��Ҕԍ�
            row.add(getDataString(map, "INSURED_NO"));
            // 007:���Ə��ԍ�
            row.add(getDataString(map, "JIGYOUSHA_NO"));
            // 008:�\����
            row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
            // 009:�쐬�˗���
            row.add(formatYYYYMMDD(map.getData("REQ_DT")));
            // 010:���t��
            row.add(formatYYYYMMDD(map.getData("SEND_DT")));
            // 011:�˗��ԍ�
            row.add(getDataString(map, "REQ_NO"));
            // 012:��t�ԍ�
            row.add(getDataString(map, "DR_NO"));
            // 013:���
            row.add(getDataString(map, "KIND"));
            // 014:�L����
            row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

            // 015:���Җ�����
            row.add(getDataString(map, "PATIENT_KN"));
            // 016:���Җ�
            row.add(getDataString(map, "PATIENT_NM"));
            // 017:���ҁE���N����
            row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
            // 018:���ҁE�N��
            row.add(getDataString(map, "AGE"));
            // 019:���ҁE����
            row.add(getDataString(map, "SEX"));
            // 020:���ҁE�X�֔ԍ�
            row.add(getDataString(map, "POST_CD"));
            // 021:���ҁE�Z��
            row.add(getDataString(map, "ADDRESS"));
            // 022:���ҁE�d�b�ԍ�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TEL1"));
            if (getDataString(map, "TEL1").length() > 0) {
                if (getDataString(map, "TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "TEL2"));
            row.add(sbBuf.toString());
            // 023:��t����
            row.add(getDataString(map, "DR_NM"));
            // 024:��Ë@�֖�
            row.add(getDataString(map, "MI_NM"));
            // 025:��Ë@�ցE�X�֔ԍ�
            row.add(getDataString(map, "MI_POST_CD"));
            // 026:��Ë@�ցE���ݒn
            row.add(getDataString(map, "MI_ADDRESS"));
            // 027:��Ë@�ցE�A����(TEL)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_TEL1"));
            if (getDataString(map, "MI_TEL1").length() > 0) {
                if (getDataString(map, "MI_TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_TEL2"));
            row.add(sbBuf.toString());
            // 028:��Ë@�ցE�A����(FAX)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_FAX1"));
            if (getDataString(map, "MI_FAX1").length() > 0) {
                if (getDataString(map, "MI_FAX2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_FAX2"));
            row.add(sbBuf.toString());
            // 029:��t����
            row.add(getDataString(map, "DR_CONSENT"));
            // 030:�ŏI�f�@��
            row.add(formatYYYYMMDD(map.getData("LASTDAY")));
            // 031:�쐬��
            row.add(getDataString(map, "IKN_CREATE_CNT"));
            // 032:���Ȏ�f�̗L��
            if (map.getData("TAKA").toString().equals("0")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 033:���Ȏ�f�E��f����
            sbBuf = new StringBuffer();
            sbBuf.append(getBitFromRonriwa(getDataString(map, "TAKA"), 12));
            if (isNullText(getDataString(map, "TAKA_OTHER"))) {
                sbBuf.append("0");
            } else {
                sbBuf.append("1");
            }
            row.add(sbBuf.toString());
            // 034:���Ȏ�f�E��f���ځE���̑����e
            row.add(getDataString(map, "TAKA_OTHER"));

            // 035:�f�f��1
            row.add(getDataString(map, "SINDAN_NM1"));
            // 036:���ǔN����1
            row.add(formatUnknownDate(map.getData("HASHOU_DT1")));
            // 037:�f�f��2
            row.add(getDataString(map, "SINDAN_NM2"));
            // 038:���ǔN����2
            row.add(formatUnknownDate(map.getData("HASHOU_DT2")));
            // 039:�f�f��3
            row.add(getDataString(map, "SINDAN_NM3"));
            // 040:���ǔN����3
            row.add(formatUnknownDate(map.getData("HASHOU_DT3")));
            // 041:�Ǐ�Ƃ��Ă̈��萫
            row.add(getDataString(map, "SHJ_ANT"));
            // 042:���̕K�v�̒��x�Ɋւ���\��̌��ʂ�
            row.add(getDataString(map, "YKG_YOGO"));
            // 043:���a�̌o�߁E���Ó��e
            sbBuf = new StringBuffer();
            if (getDataString(map, "MT_STS").length() > 0) {
                // 2006/06/22
                // CRLF - �u���Ή�
                // Replace - begin [Masahiko Higuchi]
                sbBuf
                        .append((getDataString(map, "MT_STS").replaceAll("\r\n",VT)).replaceAll("\n",
                                VT)); // 0x0B
                // Replace - end
                sbBuf.append("");
            }
            if ((getDataString(map, "MEDICINE1").length()
                    + getDataString(map, "DOSAGE1").length()
                    + getDataString(map, "UNIT1").length()
                    + getDataString(map, "USAGE1").length()
                    + getDataString(map, "MEDICINE2").length()
                    + getDataString(map, "DOSAGE2").length()
                    + getDataString(map, "UNIT2").length() + getDataString(map,
                    "USAGE2").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE1") + " ");
                sbBuf.append(getDataString(map, "DOSAGE1"));
                sbBuf.append(getDataString(map, "UNIT1") + " ");
                sbBuf.append(getDataString(map, "USAGE1") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE2") + " ");
                sbBuf.append(getDataString(map, "DOSAGE2"));
                sbBuf.append(getDataString(map, "UNIT2") + " ");
                sbBuf.append(getDataString(map, "USAGE2") + "");
            }
            if ((getDataString(map, "MEDICINE3").length()
                    + getDataString(map, "DOSAGE3").length()
                    + getDataString(map, "UNIT3").length()
                    + getDataString(map, "USAGE3").length()
                    + getDataString(map, "MEDICINE4").length()
                    + getDataString(map, "DOSAGE4").length()
                    + getDataString(map, "UNIT4").length() + getDataString(map,
                    "USAGE4").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE3") + " ");
                sbBuf.append(getDataString(map, "DOSAGE3"));
                sbBuf.append(getDataString(map, "UNIT3") + " ");
                sbBuf.append(getDataString(map, "USAGE3") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE4") + " ");
                sbBuf.append(getDataString(map, "DOSAGE4"));
                sbBuf.append(getDataString(map, "UNIT4") + " ");
                sbBuf.append(getDataString(map, "USAGE4") + "");
            }
            if ((getDataString(map, "MEDICINE5").length()
                    + getDataString(map, "DOSAGE5").length()
                    + getDataString(map, "UNIT5").length()
                    + getDataString(map, "USAGE5").length()
                    + getDataString(map, "MEDICINE6").length()
                    + getDataString(map, "DOSAGE6").length()
                    + getDataString(map, "UNIT6").length() + getDataString(map,
                    "USAGE6").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE5") + " ");
                sbBuf.append(getDataString(map, "DOSAGE5"));
                sbBuf.append(getDataString(map, "UNIT5") + " ");
                sbBuf.append(getDataString(map, "USAGE5") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE6") + " ");
                sbBuf.append(getDataString(map, "DOSAGE6"));
                sbBuf.append(getDataString(map, "UNIT6") + " ");
                sbBuf.append(getDataString(map, "USAGE6") + "");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // �Ō��0x0B���폜
            }
            row.add(sbBuf.toString());

            // 044:���u���e
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TNT_KNR"));
            sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
            sbBuf.append(getDataString(map, "TOUSEKI"));
            sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
            sbBuf.append(getDataString(map, "OX_RYO"));
            sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
            sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
            sbBuf.append(getDataString(map, "TOUTU"));
            sbBuf.append(getDataString(map, "KEKN_EIYOU"));
            row.add(sbBuf.toString());
            // 045:���ʂȑ[�u
            row.add(getDataString(map, "MONITOR")
                    + getDataString(map, "JOKUSOU_SHOCHI"));
            // 046:���ււ̑Ή�
            row.add(getDataString(map, "RYU_CATHETER"));

            // 047:��Q�V�l�̓��퐶�������x(�Q������x)
            row.add(getDataString(map, "NETAKIRI"));
            // 048:�s�𐫘V�l�̓��퐶�������x
            row.add(getDataString(map, "CHH_STS"));
            // 049:�Z���L��
            row.add(getDataString(map, "TANKI_KIOKU"));
            // 050:����̈ӎv������s�����߂̔F�m�\��
            row.add(getDataString(map, "NINCHI"));
            // 051:�����̈ӎv�̓`�B�\��
            row.add(getDataString(map, "DENTATU"));
            // 052:�H��
            row.add(getDataString(map, "SHOKUJI"));
            // 053:���s���L��
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "GNS_GNC"));
            sbBuf.append(getDataString(map, "MOUSOU"));
            sbBuf.append(getDataString(map, "CHUYA"));
            sbBuf.append(getDataString(map, "BOUGEN"));
            sbBuf.append(getDataString(map, "BOUKOU"));
            sbBuf.append(getDataString(map, "TEIKOU"));
            sbBuf.append(getDataString(map, "HAIKAI"));
            sbBuf.append(getDataString(map, "FUSIMATU"));
            sbBuf.append(getDataString(map, "FUKETU"));
            sbBuf.append(getDataString(map, "ISHOKU"));
            sbBuf.append(getDataString(map, "SEITEKI_MONDAI"));
            if (sbBuf.toString().equals("00000000000")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 054:���s���E�L�̏ꍇ
            row.add(sbBuf.toString());
            // 055:���s�����̑�
            row.add(getDataString(map, "MONDAI_OTHER_NM"));
            // 056:���_�_�o�Ǐ�E�L��
            row.add(getDataString(map, "SEISIN"));
            // 057:���_�_�o�Ǐ�E�Ǐ�
            row.add(getDataString(map, "SEISIN_NM"));
            // 058:�����f�E�L��
            row.add(getDataString(map, "SENMONI"));
            // 059:�����f�E�ڍ�
            row.add(getDataString(map, "SENMONI_NM"));
            // 060:�����r
            row.add(getDataString(map, "KIKIUDE"));
            // 061:�̏d
            row.add(getDataString(map, "WEIGHT"));
            // 062:�g��
            row.add(getDataString(map, "HEIGHT"));
            // 063:�l������
            row.add(getDataString(map, "SISIKESSON"));
            // 064:�l����������
            row.add(getDataString(map, "SISIKESSON_BUI"));
            // 065:�l���������x
            row.add(getDataString(map, "SISIKESSON_TEIDO"));
            // 066:���
            row.add(getDataString(map, "MAHI"));
            // 067:��ვ���
            row.add(getDataString(map, "MAHI_BUI"));
            // 068:��გ��x
            row.add(getDataString(map, "MAHI_TEIDO"));
            // 069:�ؗ͒ቺ
            row.add(getDataString(map, "KINRYOKU_TEIKA"));
            // 070:�ؗ͒ቺ����
            row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
            // 071:�ؗ͒ቺ���x
            row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
            // 072:���
            row.add(getDataString(map, "JOKUSOU"));
            // 073:��ጕ���
            row.add(getDataString(map, "JOKUSOU_BUI"));
            // 074:��ጒ��x
            row.add(getDataString(map, "JOKUSOU_TEIDO"));
            // 075:�畆����
            row.add(getDataString(map, "HIFUSIKKAN"));
            // 076:�畆��������
            row.add(getDataString(map, "HIFUSIKKAN_BUI"));
            // 077:�畆�������x
            row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));
            // 078:�֐߂̍S�k
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "KATA_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_HIDARI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_HIDARI"));
            if (sbBuf.toString().equals("00000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 079:���֐�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "KATA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 080:�I�֐�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIJI_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 081:�Ҋ֐�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MATA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "MATA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 082:���֐�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_MIGI"));
            sbBuf.append(getDataString(map, "HIZA_KOUSHU_HIDARI"));
            row.add(sbBuf.toString());
            // 083:�����E�s���Ӊ^��
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            if (sbBuf.toString().equals("000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 084:�㎈
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 085:�̊�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 086:����
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 087:�S�g�}
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "INSURED_NO"));
            sbBuf.append(formatYYYYMMDD(map.getData("KINYU_DT")));
            imgFileName = sbBuf.toString();
            row.add(sbBuf.toString());

            // 088:���݁A�����̉\���������a��
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "NYOUSIKKIN"));
            sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
            sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
            sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
            sbBuf.append(getDataString(map, "ENGESEIHAIEN"));
            sbBuf.append(getDataString(map, "CHOUHEISOKU"));
            sbBuf.append(getDataString(map, "EKIKANKANSEN"));
            sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
            sbBuf.append(getDataString(map, "ITAMI"));
            sbBuf.append(getDataString(map, "DASSUI"));
            sbBuf.append(getDataString(map, "BYOUTAITA"));
            row.add(sbBuf.toString());
            // 089:���݁A�����̉\���������a�ԁE���̑�
            row.add(getDataString(map, "BYOUTAITA_NM"));
            // 090:�����̉\���������a�Ԃ̑Ώ����j
            sbBuf = new StringBuffer();
            if (getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "TENTOU_KOSSETU_TAISHO_HOUSIN").length() > 0) {
                sbBuf
                        .append(getDataString(map,
                                "TENTOU_KOSSETU_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
                sbBuf
                        .append(getDataString(map,
                                "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map,
                        "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "ENGESEIHAIEN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "ENGESEIHAIEN_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "CHOUHEISOKU_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "CHOUHEISOKU_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map,
                        "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "ITAMI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "ITAMI_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "DASSUI_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "DASSUI_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (getDataString(map, "BYOUTAITA_TAISHO_HOUSIN").length() > 0) {
                sbBuf.append(getDataString(map, "BYOUTAITA_TAISHO_HOUSIN"));
                sbBuf.append("�A");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // �Ō��"�A"���폜
                sbBuf.append("�B"); // "�B"��ǉ�����
            }
            row.add(sbBuf.toString());

            // 091:��w�I�Ǘ��̕K�v��
            sbBuf = new StringBuffer();
            sbBuf
                    .append(getUnderlinedCheckValue(getDataString(map,
                            "HOUMON_SINRYOU"), getDataString(map,
                            "HOUMON_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_KANGO"), getDataString(map, "HOUMON_KANGO_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_REHA"), getDataString(map, "HOUMON_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TUUSHO_REHA"), getDataString(map, "TUUSHO_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU"), getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_SINRYOU"), getDataString(map,
                    "HOUMONSIKA_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_EISEISIDOU"), getDataString(map,
                    "HOUMONSIKA_EISEISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU"), getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU"), getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "IGAKUTEKIKANRI_OTHER"), getDataString(map,
                    "IGAKUTEKIKANRI_OTHER_UL")));
            row.add(sbBuf.toString());
            // 092:��w�I�Ǘ��̕K�v���E���̑�
            row.add(getDataString(map, "IGAKUTEKIKANRI_OTHER_NM"));
            // 093:�����ɂ���
            row.add(getDataString(map, "KETUATU"));
            // 094:�����ɂ��āE���ӎ���
            row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
            // 095:�����ɂ���
            row.add(getDataString(map, "ENGE"));
            // 096:�����ɂ��āE���ӎ���
            row.add(getDataString(map, "ENGE_RYUIJIKOU"));
            // 097:�ېH�ɂ���
            row.add(getDataString(map, "SESHOKU"));
            // 098:�ېH�ɂ��āE���ӎ���
            row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
            // 099:�ړ��ɂ���
            row.add(getDataString(map, "IDOU"));
            // 100:�ړ��ɂ��āE���ӎ���
            row.add(getDataString(map, "IDOU_RYUIJIKOU"));
            // 101:���̑�
            row.add(getDataString(map, "KAIGO_OTHER"));
            // 102:�����ǂ̗L��
            row.add(getDataString(map, "KANSENSHOU"));
            // 103:�����ǂ̗L���E�ڍ�
            row.add(getDataString(map, "KANSENSHOU_NM"));
            // 104:���̑����L���ׂ�����
            sbBuf = new StringBuffer();
            if (getDataString(map, "HASE_SCORE").length() > 0) {
                sbBuf.append("���J�쎮 = ");
                sbBuf.append(getDataString(map, "HASE_SCORE"));
                sbBuf.append("�_ (");
                sbBuf.append(getDataString(map, "HASE_SCR_DT"));
                sbBuf.append(")");
            }
            if (getDataString(map, "P_HASE_SCORE").length() > 0) {
                sbBuf.append(" (�O�� ");
                sbBuf.append(getDataString(map, "P_HASE_SCORE"));
                sbBuf.append("�_ (");
                sbBuf.append(getDataString(map, "P_HASE_SCR_DT"));
                sbBuf.append("))");
            }
            String hase = sbBuf.toString();
            if (hase.length() > 0) {
                hase += "";
            }
            sbBuf = new StringBuffer();
            if (getDataString(map, "INST_SEL_PR1").length() > 0) {
                sbBuf.append("�{�ݑI��(�D��x) 1. ");
                String tmpPr = getDataString(map, "INST_SEL_PR1");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 16 - tmpPr.length())); // 16�����ɂ���
            }
            if (getDataString(map, "INST_SEL_PR2").length() > 0) {
                sbBuf.append("2. ");
                String tmpPr = getDataString(map, "INST_SEL_PR2");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 15 - tmpPr.length())); // 15�����ɂ���
            }
            String pr = sbBuf.toString();
            if (pr.length() > 0) {
                pr += "";
            }
            // 2006/06/22 TODO
            // CRLF - �u���Ή�
            // Reeplace - begin [Masahiko Higuchi]
            
            row.add(hase + pr
                    + (getDataString(map, "IKN_TOKKI").replaceAll("\r\n",VT)).replaceAll("\n", VT));
            // Replace - end
            // 1���R�[�h�ǉ�
            cvs.addRow(row);

            // �摜�o��
            StringBuffer sb = new StringBuffer();
            sb.append("SELECT");
            sb.append(" BODY_FIGURE");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");

            //2005-12-24 edit sta fujihara shin �V���O���N�H�[�e�[�V�����폜
//            sb.append(" (IKN_ORIGIN.PATIENT_NO='");
//            sb.append(patientNo);
//            sb.append("')");
//            sb.append(" AND (IKN_ORIGIN.EDA_NO='");
//            sb.append(edaNo);
//            sb.append("')");
            sb.append(" (IKN_ORIGIN.PATIENT_NO=");
            sb.append(patientNo);
            sb.append(")");
            sb.append(" AND (IKN_ORIGIN.EDA_NO=");
            sb.append(edaNo);
            sb.append(")");
            //2005-12-24 edit end fujihara shin

            VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
            if (array.getDataSize() > 0) {
                // �l�̐}�擾
                VRMap imgMap = (VRMap) array.getData();
                byte[] image = (byte[]) imgMap.getData("BODY_FIGURE");
                File imgFileBmp = new File(file.getParent()
                        + IkenshoConstants.FILE_SEPARATOR + imgFileName
                        + ".bmp");
                writeBmp(imgFileBmp, image);
            }

            // �v���O���X�o�[�i�s����
            iwf.setProgressValue(i);
        }

        // CSV�o��
        try {
            // �����o��
            cvs.write(true, true);
        } catch (Exception ex) {
            return false;
        }

        iwf.setProgressValue(max);

        return true;
    }

    /**
     * CSV�o�͏������s���܂��B(�@������t�H�[�}�b�g)
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     * @return ����������
     */
    public boolean doOutputCSVNew(VRArrayList data, File file) throws Exception {
        // �v���O���X����
        IkenshoWaitingForm iwf = new IkenshoWaitingForm(ACFrame.getInstance(),
                "CSV�t�@�C���o�͒�");

        // �o�͑ΏۂłȂ��f�[�^���Ԉ���
        for (int i = data.getDataSize() - 1; i >= 0; i--) {
            VRMap map = (VRMap) data.getData(i);
            if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
                data.remove(i);
            }
        }

        // CSV�o�͗p�̃f�[�^��DB����擾����
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSqlNew());

        // CSV�o�͏���(������)
        int max = data.getDataSize();
        iwf.setMaxCount(max); // �v���O���X�o�[�ő�l
        StringBuffer sbBuf = new StringBuffer();
        String softName = IkenshoCommon.getProperity("Version/SoftName");
        VRCSVFile cvs = new VRCSVFile(file.getPath()); // CSV����
        iwf.setVisible(true); // �v���O���X�o�[�\��

        // CSV�o�͏���
        for (int i = 0; i < max; i++) {
            VRMap tmp = (VRMap) data.getData(i);
            String insuredNo = convertInsuredNo(tmp.getData("INSURED_NO").toString());
            String patientNo = tmp.getData("PATIENT_NO").toString();
            String edaNo = tmp.getData("EDA_NO").toString();
            int idx = matchingData(dataDB, patientNo, edaNo);
            if (idx < 0) {
                continue;
            }
            VRMap map = (VRMap) dataDB.getData(idx);

            // �s����
            ArrayList row = new ArrayList();
            // 001:FormatVersion
            row.add("1.1");
            // 002:SoftName
            row.add(softName);
            // 003:�^�C���X�^���v
//            row.add(getDataString(map, "INSURED_NO")
//                    + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            row.add(insuredNo + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
            // 004:�ی��Ҕԍ�
            row.add(getDataString(map, "INSURER_NO"));
            // 005:�ی��Җ���
            row.add(getDataString(map, "INSURER_NM"));
            // 006:��ی��Ҕԍ�
//            row.add(getDataString(map, "INSURED_NO"));
            row.add(insuredNo);
            // 007:���Ə��ԍ�
            row.add(getDataString(map, "JIGYOUSHA_NO"));
            // 008:�\����
            row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
            // 009:�쐬�˗���
            row.add(formatYYYYMMDD(map.getData("REQ_DT")));
            // 010:���t��
            row.add(formatYYYYMMDD(map.getData("SEND_DT")));
            // 011:�˗��ԍ�
            row.add(getDataString(map, "REQ_NO"));
            // 012:��t�ԍ�
            row.add(getDataString(map, "DR_NO"));
            // 013:���
            row.add(getDataString(map, "KIND"));
            // 014:�L����
            row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

            // 015:���Җ�����
            row.add(getDataString(map, "PATIENT_KN"));
            // 016:���Җ�
            row.add(getDataString(map, "PATIENT_NM"));
            // 017:���ҁE���N����
            row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
            // 018:���ҁE�N��
            row.add(getDataString(map, "AGE"));
            // 019:���ҁE����
            row.add(getDataString(map, "SEX"));
            // 020:���ҁE�X�֔ԍ�
            row.add(getDataString(map, "POST_CD"));
            // 021:���ҁE�Z��
            row.add(getDataString(map, "ADDRESS"));
            // 022:���ҁE�d�b�ԍ�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TEL1"));
            if (getDataString(map, "TEL1").length() > 0) {
                if (getDataString(map, "TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "TEL2"));
            row.add(sbBuf.toString());
            // 023:��t����
            row.add(getDataString(map, "DR_NM"));
            // 024:��Ë@�֖�
            row.add(getDataString(map, "MI_NM"));
            // 025:��Ë@�ցE�X�֔ԍ�
            row.add(getDataString(map, "MI_POST_CD"));
            // 026:��Ë@�ցE���ݒn
            row.add(getDataString(map, "MI_ADDRESS"));
            // 027:��Ë@�ցE�A����(TEL)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_TEL1"));
            if (getDataString(map, "MI_TEL1").length() > 0) {
                if (getDataString(map, "MI_TEL2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_TEL2"));
            row.add(sbBuf.toString());
            // 028:��Ë@�ցE�A����(FAX)
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MI_FAX1"));
            if (getDataString(map, "MI_FAX1").length() > 0) {
                if (getDataString(map, "MI_FAX2").length() > 0) {
                    sbBuf.append("-");
                }
            }
            sbBuf.append(getDataString(map, "MI_FAX2"));
            row.add(sbBuf.toString());
            // 029:��t����
            row.add(getDataString(map, "DR_CONSENT"));
            // 030:�ŏI�f�@��
            row.add(formatYYYYMMDD(map.getData("LASTDAY")));
            // 031:�쐬��
            row.add(getDataString(map, "IKN_CREATE_CNT"));
            // 032:���Ȏ�f�̗L��
            // replace begin 2006/08/04 kamitsukasa
            if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA", map)) == 0) {
            	if((getDataString(map, "TAKA_OTHER")).length() <= 0){
                    row.add("2");
            	}else{
            		row.add("1");
            	}
            } else {
                row.add("1");
            }
//            if (map.getData("TAKA").toString().equals("0")) {
//                row.add("2");
//            } else {
//                row.add("1");
//            }
            // replace end 2006/08/04 kamitsukasa
            // 033:���Ȏ�f�E��f����
            sbBuf = new StringBuffer();
            sbBuf.append(getBitFromRonriwa(getDataString(map, "TAKA"), 12));
            if (isNullText(getDataString(map, "TAKA_OTHER"))) {
                sbBuf.append("0");
            } else {
                sbBuf.append("1");
            }
            row.add(sbBuf.toString());
            // 034:���Ȏ�f�E��f���ځE���̑����e
            row.add(getDataString(map, "TAKA_OTHER"));

            // 035:�f�f��1
            row.add(getDataString(map, "SINDAN_NM1"));
            // 036:���ǔN����1
            row.add(formatUnknownDate(map.getData("HASHOU_DT1")));
            // 037:�f�f��2
            row.add(getDataString(map, "SINDAN_NM2"));
            // 038:���ǔN����2
            row.add(formatUnknownDate(map.getData("HASHOU_DT2")));
            // 039:�f�f��3
            row.add(getDataString(map, "SINDAN_NM3"));
            // 040:���ǔN����3
            row.add(formatUnknownDate(map.getData("HASHOU_DT3")));
            // 041:�Ǐ�Ƃ��Ă̈��萫
            row.add(getDataString(map, "SHJ_ANT"));
            // 042:�Ǐ�s����̋�̓I��
            row.add(getDataString(map, "INSECURE_CONDITION"));
            // 043:���a�̌o�߁E���Ó��e
            sbBuf = new StringBuffer();
            if (getDataString(map, "MT_STS").length() > 0) {
                // 2006/06/23
                // CRLF - �u���Ή�
                // Replace - begin [Masahiko Higuchi]
                sbBuf.append((getDataString(map, "MT_STS").replaceAll("\r\n",VT)).replaceAll("\n",VT)); // 0x0B
                // Replace - end
                sbBuf.append("");
            }
            if ((getDataString(map, "MEDICINE1").length()
                    + getDataString(map, "DOSAGE1").length()
                    + getDataString(map, "UNIT1").length()
                    + getDataString(map, "USAGE1").length()
                    + getDataString(map, "MEDICINE2").length()
                    + getDataString(map, "DOSAGE2").length()
                    + getDataString(map, "UNIT2").length() + getDataString(map,
                    "USAGE2").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE1") + " ");
                sbBuf.append(getDataString(map, "DOSAGE1"));
                sbBuf.append(getDataString(map, "UNIT1") + " ");
                sbBuf.append(getDataString(map, "USAGE1") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE2") + " ");
                sbBuf.append(getDataString(map, "DOSAGE2"));
                sbBuf.append(getDataString(map, "UNIT2") + " ");
                sbBuf.append(getDataString(map, "USAGE2") + "");
            }
            if ((getDataString(map, "MEDICINE3").length()
                    + getDataString(map, "DOSAGE3").length()
                    + getDataString(map, "UNIT3").length()
                    + getDataString(map, "USAGE3").length()
                    + getDataString(map, "MEDICINE4").length()
                    + getDataString(map, "DOSAGE4").length()
                    + getDataString(map, "UNIT4").length() + getDataString(map,
                    "USAGE4").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE3") + " ");
                sbBuf.append(getDataString(map, "DOSAGE3"));
                sbBuf.append(getDataString(map, "UNIT3") + " ");
                sbBuf.append(getDataString(map, "USAGE3") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE4") + " ");
                sbBuf.append(getDataString(map, "DOSAGE4"));
                sbBuf.append(getDataString(map, "UNIT4") + " ");
                sbBuf.append(getDataString(map, "USAGE4") + "");
            }
            if ((getDataString(map, "MEDICINE5").length()
                    + getDataString(map, "DOSAGE5").length()
                    + getDataString(map, "UNIT5").length()
                    + getDataString(map, "USAGE5").length()
                    + getDataString(map, "MEDICINE6").length()
                    + getDataString(map, "DOSAGE6").length()
                    + getDataString(map, "UNIT6").length() + getDataString(map,
                    "USAGE6").length()) > 0) {
                sbBuf.append(getDataString(map, "MEDICINE5") + " ");
                sbBuf.append(getDataString(map, "DOSAGE5"));
                sbBuf.append(getDataString(map, "UNIT5") + " ");
                sbBuf.append(getDataString(map, "USAGE5") + " / ");
                sbBuf.append(getDataString(map, "MEDICINE6") + " ");
                sbBuf.append(getDataString(map, "DOSAGE6"));
                sbBuf.append(getDataString(map, "UNIT6") + " ");
                sbBuf.append(getDataString(map, "USAGE6") + "");
            }
            if (sbBuf.length() > 0) {
                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // �Ō��0x0B���폜
            }
            row.add(sbBuf.toString());

            // 044:���u���e
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TNT_KNR"));
            sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
            sbBuf.append(getDataString(map, "TOUSEKI"));
            sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
            sbBuf.append(getDataString(map, "OX_RYO"));
            sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
            sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
            sbBuf.append(getDataString(map, "TOUTU"));
            sbBuf.append(getDataString(map, "KEKN_EIYOU"));
            row.add(sbBuf.toString());
            // 045:���ʂȑ[�u
            row.add(getDataString(map, "MONITOR")
                    + getDataString(map, "JOKUSOU_SHOCHI"));
            // 046:���ււ̑Ή�
            // 2006/03/16[Tozo Tanaka] : replace begin
//            row.add(getDataString(map, "RYU_CATHETER"));
            row.add(getDataString(map, "CATHETER"));
            // 2006/03/16[Tozo Tanaka] : replace end

            // 047:��Q����҂̓��퐶�������x(�Q������x)
            row.add(getDataString(map, "NETAKIRI"));
            // 048:�F�m�Ǎ���҂̓��퐶�������x
            row.add(getDataString(map, "CHH_STS"));
            // 049:�Z���L��
            row.add(getDataString(map, "TANKI_KIOKU"));
            // 050:����̈ӎv������s�����߂̔F�m�\��
            row.add(getDataString(map, "NINCHI"));
            // 051:�����̈ӎv�̓`�B�\��
            row.add(getDataString(map, "DENTATU"));
            // 052:�F�m�ǂ̎��ӏǏ�E�L��
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "GNS_GNC"));
            sbBuf.append(getDataString(map, "MOUSOU"));
            sbBuf.append(getDataString(map, "CHUYA"));
            sbBuf.append(getDataString(map, "BOUGEN"));
            sbBuf.append(getDataString(map, "BOUKOU"));
            sbBuf.append(getDataString(map, "TEIKOU"));
            sbBuf.append(getDataString(map, "HAIKAI"));
            sbBuf.append(getDataString(map, "FUSIMATU"));
            sbBuf.append(getDataString(map, "FUKETU"));
            sbBuf.append(getDataString(map, "ISHOKU"));
            sbBuf.append(getDataString(map, "SEITEKI_MONDAI"));
            sbBuf.append(getDataString(map, "MONDAI_OTHER"));
            if (sbBuf.toString().equals("000000000000")) {
                row.add("2");
            } else {
                row.add("1");
            }
            // 053:���ӏǏ�E�L�̏ꍇ
            row.add(sbBuf.toString());
            // 054:���s�����̑�
            row.add(getDataString(map, "MONDAI_OTHER_NM"));
            // 055:���̑��̐��_�E�_�o�Ǐ�E�L��
            row.add(getDataString(map, "SEISIN"));
            // 056:���̑��̐��_�E�_�o�Ǐ�E�Ǐ�
            row.add(getDataString(map, "SEISIN_NM"));
            // 057:���̑��̐��_�E�_�o�Ǐ�E�����f�E�L��
            row.add(getDataString(map, "SENMONI"));
            // 058:���̑��̐��_�E�_�o�Ǐ�E�����f�E�ڍ�
            row.add(getDataString(map, "SENMONI_NM"));
            // 059:�����r
            row.add(getDataString(map, "KIKIUDE"));
            // 060:�g��
            row.add(getDataString(map, "HEIGHT"));
            // 061:�̏d
            row.add(getDataString(map, "WEIGHT"));
            // 062:�ߋ�6�����̑̏d�̕ω�
            row.add(getDataString(map, "WEIGHT_CHANGE"));
            // 063:�l������
            row.add(getDataString(map, "SISIKESSON"));
            // 064:�l����������
            row.add(getDataString(map, "SISIKESSON_BUI"));
            // 065:���(ikn_origin.mahi�ł͂Ȃ�)
            // row.add(getDataString(map, "MAHI"));
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "MAHI_LEFTARM"));
            sbBuf.append(getDataString(map, "MAHI_RIGHTARM"));
            sbBuf.append(getDataString(map, "MAHI_LOWERLEFTLIMB"));
            sbBuf.append(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
            sbBuf.append(getDataString(map, "MAHI_ETC"));
            if (sbBuf.toString().equals("00000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 066:���-�E�㎈
            row.add(getDataString(map, "MAHI_RIGHTARM"));
            // 067:���-�E�㎈-���x
            row.add(getDataString(map, "MAHI_RIGHTARM_TEIDO"));
            // 068:���-���㎈
            row.add(getDataString(map, "MAHI_LEFTARM"));
            // 069:���-���㎈-���x
            row.add(getDataString(map, "MAHI_LEFTARM_TEIDO"));
            // 070:���-�E����
            row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
            // 071:���-�E����-���x
            row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB_TEIDO"));
            // 072:���-������
            row.add(getDataString(map, "MAHI_LOWERLEFTLIMB"));
            // 073:���-������-���x
            row.add(getDataString(map, "MAHI_LOWERLEFTLIMB_TEIDO"));
            // 074:���-���̑�
            row.add(getDataString(map, "MAHI_ETC"));
            // 075:���-���̑�-����
            row.add(getDataString(map, "MAHI_ETC_BUI"));
            // 076:���-���̑�-���x
            row.add(getDataString(map, "MAHI_ETC_TEIDO"));
            // 077:�ؗ͒ቺ
            row.add(getDataString(map, "KINRYOKU_TEIKA"));
            // 078:�ؗ͒ቺ����
            row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
            // 079:�ؗ͒ቺ���x
            row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
            // 080:�֐߂̍S�k
            row.add(getDataString(map, "KOUSHU"));
            // 081:�֐߂̍S�k-����
            row.add(getDataString(map, "KOUSHU_BUI"));
            // 082:�֐߂̍S�k-���x
            row.add(getDataString(map, "KOUSHU_TEIDO"));
            // 083:�֐߂̒ɂ�
            row.add(getDataString(map, "KANSETU_ITAMI"));
            // 084:�֐߂̒ɂ�-����
            row.add(getDataString(map, "KANSETU_ITAMI_BUI"));
            // 085:�֐߂̒ɂ�-���x
            row.add(getDataString(map, "KANSETU_ITAMI_TEIDO"));
            // 086:�����E�s���Ӊ^��
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            if (sbBuf.toString().equals("000000")) {
                row.add("0");
            } else {
                row.add("1");
            }
            // 087:�㎈
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 088:����
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 089:�̊�
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
            sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
            row.add(sbBuf.toString());
            // 090:���
            row.add(getDataString(map, "JOKUSOU"));
            // 091:���-����
            row.add(getDataString(map, "JOKUSOU_BUI"));
            // 092:���-���x
            row.add(getDataString(map, "JOKUSOU_TEIDO"));
            // 093:���̑��畆����
            row.add(getDataString(map, "HIFUSIKKAN"));
            // 094:���̑��畆����-����
            row.add(getDataString(map, "HIFUSIKKAN_BUI"));
            // 095:���̑��畆����-���x
            row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));

            // 096:���O���s
            row.add(getDataString(map, "OUTDOOR"));
            // 097:�Ԉ֎q�̎g�p
            row.add(getDataString(map, "WHEELCHAIR"));
            // 098:���s�⏕��E����̎g�p
            row.add(getBitFromRonriwa(
                            getDataString(map, "ASSISTANCE_TOOL"), 3));
            // 099:�H���s��
            row.add(getDataString(map, "MEAL"));
            // 100:���݂̉h�{���
            row.add(getDataString(map, "NOURISHMENT"));
            // 101:�h�{��H������̗��ӓ_
            row.add(getDataString(map, "EATING_RYUIJIKOU"));

            // 102:���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j�i��ԁj
            sbBuf = new StringBuffer();
            sbBuf.append(getDataString(map, "NYOUSIKKIN"));
            sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
            sbBuf.append(getDataString(map, "IDOUTEIKA"));
            sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
            sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
            sbBuf.append(getDataString(map, "TOJIKOMORI"));
            sbBuf.append(getDataString(map, "IYOKUTEIKA"));
            sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
            sbBuf.append(getDataString(map, "TEIEIYOU"));
            sbBuf.append(getDataString(map, "SESSYOKUENGE"));
            sbBuf.append(getDataString(map, "DASSUI"));
            sbBuf.append(getDataString(map, "EKIKANKANSEN"));
            sbBuf.append(getDataString(map, "GAN_TOUTU"));
            sbBuf.append(getDataString(map, "BYOUTAITA"));
            row.add(sbBuf.toString());
            // 103:���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j�i��ԁj
            row.add(getDataString(map, "BYOUTAITA_NM"));
            // 104:���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j�i�Ώ����j�j
            // replace begin 2006/08/07 kamitsukasa
//            sbBuf = new StringBuffer();
//            if (getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "NYOUSIKKIN_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "TENTOU_KOSSETU_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                                "TENTOU_KOSSETU_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "IDOUTEIKA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "IDOUTEIKA_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "JOKUSOU_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                        "JOKUSOU_KANOUSEI_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "SINPAIKINOUTEIKA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                        "SINPAIKINOUTEIKA_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "TOJIKOMORI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "TOJIKOMORI_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "IYOKUTEIKA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "IYOKUTEIKA_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "HAIKAI_KANOUSEI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map,
//                                "HAIKAI_KANOUSEI_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "TEIEIYOU_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "TEIEIYOU_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "SESSYOKUENGE_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "SESSYOKUENGE_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "DASSUI_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "DASSUI_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "EKIKANKANSEN_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "GAN_TOUTU_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "GAN_TOUTU_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (getDataString(map, "BYOUTAITA_TAISHO_HOUSIN").length() > 0) {
//                sbBuf.append(getDataString(map, "BYOUTAITA_TAISHO_HOUSIN"));
//                sbBuf.append("�A");
//            }
//            if (sbBuf.length() > 0) {
//                sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // �Ō��"�A"���폜
//                sbBuf.append("�B"); // "�B"��ǉ�����
//                //add sta 140�����Ő؂�
//                if (sbBuf.toString().length() > 140) {
//                	String tmpBuf = sbBuf.toString();
//                	sbBuf = new StringBuffer();
//                	sbBuf.append(tmpBuf.substring(0, 140));
//                }
//                //add end
//            }
//            row.add(sbBuf.toString());
            
		    sbBuf = new StringBuffer();
		    VRList words = new VRArrayList();
		    poolString(map, words, new String[] { 
		    		"NYOUSIKKIN_TAISHO_HOUSIN",
					"TENTOU_KOSSETU_TAISHO_HOUSIN",
					"IDOUTEIKA_TAISHO_HOUSIN",
					"JOKUSOU_KANOUSEI_TAISHO_HOUSIN",
					"SINPAIKINOUTEIKA_TAISHO_HOUSIN", 
					"TOJIKOMORI_TAISHO_HOUSIN",
					"IYOKUTEIKA_TAISHO_HOUSIN",
					"HAIKAI_KANOUSEI_TAISHO_HOUSIN", 
					"TEIEIYOU_TAISHO_HOUSIN",
					"SESSYOKUENGE_TAISHO_HOUSIN",
					"DASSUI_TAISHO_HOUSIN",
					"EKIKANKANSEN_TAISHO_HOUSIN", 
					"GAN_TOUTU_TAISHO_HOUSIN",
					"BYOUTAITA_TAISHO_HOUSIN" });
		    
		    if (words.size() > 0) {
				// �Ώ����j�𕶎��P�ʂŘA�����ĕ\���\�ȂƂ���܂ŁB
				final int MAX_LENGTH = 89;

				int inlineSize = 0;
				sbBuf = new StringBuffer();
				int end = words.size() - 1;
				for (int j = 0; j < end; j++) {
					String text = ACCastUtilities.toString(words.get(j));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '�B') && (c != '�A')) {
						line.append("�A");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// �o�͉\�ȂƂ���܂Œǉ�
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// �s�I���`�F�b�N
								break;
							}
						}
						break;
					}
					inlineSize += wordSize;
					sbBuf.append(line.toString());
				}
				if (inlineSize <= MAX_LENGTH) {
					// �����ǉ�
					String text = ACCastUtilities.toString(words.get(end));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '�B') && (c != '�A')) {
						line.append("�B");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// �o�͉\�ȂƂ���܂Œǉ�
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// �s�I���`�F�b�N
								break;
							}
						}
					} else {
						sbBuf.append(line.toString());
					}
				}
			}
			row.add(sbBuf.toString());
            // replace end 2006/08/07 kamitsukasa
            
            
            // //102:���̕K�v�̒��x�Ɋւ���\��̌��ʂ�
            // row.add(getDataString(map, "YKG_YOGO"));
            // //103:���̕K�v�̒��x�Ɋւ���\��̌��ʂ��E���P�ւ̊�^�����҂ł���T�[�r�X
            // row.add(getDataString(map, "IMPRO_SERVICE"));
            // 105:�T�[�r�X���p�ɂ�鐶���@�\�̈ێ��E���P�̌��ʂ�
            row.add(getDataString(map, "VITAL_FUNCTIONS_OUTLOOK"));

            // 106:��w�I�Ǘ��̕K�v��
            sbBuf = new StringBuffer();
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                            "HOUMON_SINRYOU"), getDataString(map,
                            "HOUMON_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_KANGO"), getDataString(map, "HOUMON_KANGO_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMON_REHA"), getDataString(map, "HOUMON_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TUUSHO_REHA"), getDataString(map, "TUUSHO_REHA_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU"), getDataString(map,
                    "TANKI_NYUSHO_RYOUYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_SINRYOU"), getDataString(map,
                    "HOUMONSIKA_SINRYOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONSIKA_EISEISIDOU"), getDataString(map,
                    "HOUMONSIKA_EISEISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU"), getDataString(map,
                    "HOUMONYAKUZAI_KANRISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU"), getDataString(map,
                    "HOUMONEIYOU_SHOKUJISIDOU_UL")));
            sbBuf.append(getUnderlinedCheckValue(getDataString(map,
                    "IGAKUTEKIKANRI_OTHER"), getDataString(map,
                    "IGAKUTEKIKANRI_OTHER_UL")));
            row.add(sbBuf.toString());
            // 107:��w�I�Ǘ��̕K�v���E���̑�
            row.add(getDataString(map, "IGAKUTEKIKANRI_OTHER_NM"));
            // 108:��쌌��
            row.add(getDataString(map, "KETUATU"));
            // 109:��쌌���E���ӎ���
            row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
            // 110:���ېH
            row.add(getDataString(map, "SESHOKU"));
            // 111:���ېH�E���ӎ���
            row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
            // 112:��욋��
            row.add(getDataString(map, "ENGE"));
            // 113:��욋���E���ӎ���
            row.add(getDataString(map, "ENGE_RYUIJIKOU"));
            // 114:���ړ�
            row.add(getDataString(map, "IDOU"));
            // 115:���ړ��E���ӎ���
            row.add(getDataString(map, "IDOU_RYUIJIKOU"));
            // 116:���^��
            row.add(getDataString(map, "UNDOU"));
            // 117:���^���E���ӎ���
            row.add(getDataString(map, "UNDOU_RYUIJIKOU"));
            // 118:��삻�̑�
            row.add(getDataString(map, "KAIGO_OTHER"));
            // 119:�����ǂ̗L��
            row.add(getDataString(map, "KANSENSHOU"));
            // 120:�����ǂ̗L���E�ڍ�
            row.add(getDataString(map, "KANSENSHOU_NM"));
            // 121:���̑����L���ׂ�����
            sbBuf = new StringBuffer();
            if (getDataString(map, "HASE_SCORE").length() > 0) {
                sbBuf.append("���J�쎮 = ");
                sbBuf.append(getDataString(map, "HASE_SCORE"));
                sbBuf.append("�_ (");
                sbBuf.append(getDataString(map, "HASE_SCR_DT"));
                sbBuf.append(")");
            }
            if (getDataString(map, "P_HASE_SCORE").length() > 0) {
                sbBuf.append(" (�O�� ");
                sbBuf.append(getDataString(map, "P_HASE_SCORE"));
                sbBuf.append("�_ (");
                sbBuf.append(getDataString(map, "P_HASE_SCR_DT"));
                sbBuf.append("))");
            }
            String hase = sbBuf.toString();
            if (hase.length() > 0) {
                hase += "";
            }
            sbBuf = new StringBuffer();
            if (getDataString(map, "INST_SEL_PR1").length() > 0) {
                sbBuf.append("�{�ݑI��(�D��x) 1. ");
                String tmpPr = getDataString(map, "INST_SEL_PR1");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 16 - tmpPr.length())); // 16�����ɂ���
            }
            if (getDataString(map, "INST_SEL_PR2").length() > 0) {
                sbBuf.append("2. ");
                String tmpPr = getDataString(map, "INST_SEL_PR2");
                String blank = "                ";
                sbBuf.append(tmpPr + blank.substring(0, 15 - tmpPr.length())); // 15�����ɂ���
            }
            String pr = sbBuf.toString();
            if (pr.length() > 0) {
                pr += "";
            }
            
            // 2006/06/22
            // CRLF - �u���Ή�
            // Replace - begin [Masahiko Higuchi]
            row.add(hase + pr
                    + (getDataString(map, "IKN_TOKKI").replaceAll("\r\n",VT)).replaceAll("\n", VT));
            // Replace - end
            
            // 1���R�[�h�ǉ�
            cvs.addRow(row);

            // �v���O���X�o�[�i�s����
            iwf.setProgressValue(i);
        }

        // CSV�o��
        try {
            // �����o��
            cvs.write(true, true);
        } catch (Exception ex) {
            return false;
        }

        iwf.setProgressValue(max);
        return true;
    }

    /**
     * CSV�o�͏������s���܂��B(��t�㌩���t�H�[�}�b�g�FI1.0)
     *
     * @param data VRArrayList
     * @param file File
     * @throws Exception
     * @return ����������
     */
    public boolean doOutputCSVIshi(VRArrayList data, File file)
			throws Exception {

		// add begin 2006/08/03 kamitsukasa

		// �v���O���X����
		IkenshoWaitingForm iwf = new IkenshoWaitingForm(ACFrame.getInstance(),
				"CSV�t�@�C���o�͒�");

		// �o�͑ΏۂłȂ��f�[�^���Ԉ���
		for (int i = data.getDataSize() - 1; i >= 0; i--) {
			VRMap map = (VRMap) data.getData(i);
			if (String.valueOf(map.getData("OUTPUT_FLG")).equals("false")) {
				data.remove(i);
			}
		}

		 // CSV�o�͗p�̃f�[�^��DB����擾����
		 IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		 VRArrayList dataDB = (VRArrayList) dbm.executeQuery(getSqlIshi());
		 // CSV�o�͏���(������)
		 int max = data.getDataSize();
		 iwf.setMaxCount(max); // �v���O���X�o�[�ő�l
		 StringBuffer sbBuf = new StringBuffer();
		 String softName = IkenshoCommon.getProperity("Version/SoftName");
		 VRCSVFile csv = new VRCSVFile(file.getPath()); // CSV����
		 iwf.setVisible(true); // �v���O���X�o�[�\��

//		// debug begin TODO �폜�\��
//		// CSV�o�͗p�̃f�[�^��DB����擾����
//		VRArrayList dataDB = TestPrintClass.makeDataVR("");
//		// CSV�o�͏���(������)
//		int max = dataDB.getDataSize();
//		iwf.setMaxCount(max); // �v���O���X�o�[�ő�l
//		StringBuffer sbBuf = new StringBuffer();
//		String softName = IkenshoCommon.getProperity("Version/SoftName");
//		VRCSVFile csv = new VRCSVFile(file.getPath()); // CSV����
//		iwf.setVisible(true); // �v���O���X�o�[�\��
//		// debug end

		// CSV�o�͏���
		for (int i = 0; i < max; i++) {
			 VRMap tmp = (VRMap) data.getData(i);
			 String insuredNo =
			 convertInsuredNo(ACCastUtilities.toString(tmp.getData("INSURED_NO")));
			 String patientNo =
			 ACCastUtilities.toString(tmp.getData("PATIENT_NO"));
			 String edaNo = ACCastUtilities.toString(tmp.getData("EDA_NO"));
			 int idx = matchingData(dataDB, patientNo, edaNo);
			 if (idx < 0) {
				 continue;
			 }
//			// debug begin TODO �폜�\��
//			VRMap tmp = (VRMap) dataDB.getData(i);
//			String insuredNo = convertInsuredNo(ACCastUtilities.toString(tmp
//					.getData("INSURED_NO")));
//			int idx = i;
//			// debug end
			VRMap map = (VRMap) dataDB.getData(idx);

			// �s����
			ArrayList row = new ArrayList();
			// 001:FormatVersion
			row.add("I1.0");
			// 002:SoftName
			row.add(softName);
			// 003:�^�C���X�^���v
			row.add(insuredNo + formatDDHHMMSS(map.getData("FD_TIMESTAMP")));
			// 004:�ی��Ҕԍ�
			row.add(getDataString(map, "INSURER_NO"));
			// 005:�ی��Җ���
			row.add(getDataString(map, "INSURER_NM"));
			// 006:��ی��Ҕԍ�
			row.add(insuredNo);
			// 007:���Ə��ԍ�
			row.add(getDataString(map, "JIGYOUSHA_NO"));
			// 008:�\����
			row.add(formatYYYYMMDD(map.getData("SINSEI_DT")));
			// 009:�쐬�˗���
			row.add(formatYYYYMMDD(map.getData("REQ_DT")));
			// 010:���t��
			row.add(formatYYYYMMDD(map.getData("SEND_DT")));
			// 011:�˗��ԍ�
			row.add(getDataString(map, "REQ_NO"));
			// 012:��t�ԍ�
			row.add(getDataString(map, "DR_NO"));
			// 013:���
			row.add(getDataString(map, "KIND"));
			// 014:�L����
			row.add(formatYYYYMMDD(map.getData("KINYU_DT")));

			// 015:���ҁ]������
			row.add(getDataString(map, "PATIENT_KN"));
			// 016:���ҁ]��
			row.add(getDataString(map, "PATIENT_NM"));
			// 017:���ҁ]���N����
			row.add(formatYYYYMMDD(map.getData("BIRTHDAY")));
			// 018:���ҁ]�N��
			row.add(getDataString(map, "AGE"));
			// 019:���ҁ]����
			row.add(getDataString(map, "SEX"));
			// 020:���ҁ]�X�֔ԍ�
			row.add(getDataString(map, "POST_CD"));
			// 021:���ҁ]�Z��
			row.add(getDataString(map, "ADDRESS"));
			// 022:���ҁ]�A����d�b�ԍ�
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "TEL1"));
			if (getDataString(map, "TEL1").length() > 0) {
				if (getDataString(map, "TEL2").length() > 0) {
					sbBuf.append("-");
				}
			}
			sbBuf.append(getDataString(map, "TEL2"));
			row.add(sbBuf.toString());
			// 023:��t����
			row.add(getDataString(map, "DR_NM"));
			// 024:��Ë@�֖�
			row.add(getDataString(map, "MI_NM"));
			// 025:��Ë@�ց]�X�֔ԍ�
			row.add(getDataString(map, "MI_POST_CD"));
			// 026:��Ë@�ց]���ݒn
			row.add(getDataString(map, "MI_ADDRESS"));
			// 027:��Ë@�ց]�d�b�ԍ�
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "MI_TEL1"));
			if (getDataString(map, "MI_TEL1").length() > 0) {
				if (getDataString(map, "MI_TEL2").length() > 0) {
					sbBuf.append("-");
				}
			}
			sbBuf.append(getDataString(map, "MI_TEL2"));
			row.add(sbBuf.toString());
			// 028:��Ë@�ց]FAX�ԍ�
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "MI_FAX1"));
			if (getDataString(map, "MI_FAX1").length() > 0) {
				if (getDataString(map, "MI_FAX2").length() > 0) {
					sbBuf.append("-");
				}
			}
			sbBuf.append(getDataString(map, "MI_FAX2"));
			row.add(sbBuf.toString());
			// 029:��t�̓���
			row.add(getDataString(map, "DR_CONSENT"));
			// 030:�ŏI�f�@��
			row.add(formatYYYYMMDD(map.getData("LASTDAY")));
			// 031:�ӌ����쐬��
			row.add(getDataString(map, "IKN_CREATE_CNT"));
			// 032:���Ȏ�f�̗L��
			if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA", map)) == 0) {
				if ((getDataString(map, "TAKA_OTHER")).length() <= 0) {
					row.add("2");
				} else {
					row.add("1");
				}
			} else {
				row.add("1");
			}
			// 033:���Ȗ�
			sbBuf = new StringBuffer();
			sbBuf.append(getBitFromRonriwa(getDataString(map, "TAKA"), 12));
			if (isNullText(getDataString(map, "TAKA_OTHER"))) {
				sbBuf.append("0");
			} else {
				sbBuf.append("1");
			}
			row.add(sbBuf.toString());
			// 034:���̑��̑��Ȗ�
			row.add(getDataString(map, "TAKA_OTHER"));

			// 035:�f�f��1
			row.add(getDataString(map, "SINDAN_NM1"));
			// 036:���ǔN����1
			String temp = getDataString(map, "SHUSSEI1");
			if ("1".equals(temp)) {
				row.add("");
			} else {
				row.add(formatUnknownDateCustom(map.getData("HASHOU_DT1")));
			}
			// 037:�o����1
			row.add(temp);
			// 038:�f�f��2
			row.add(getDataString(map, "SINDAN_NM2"));
			// 039:���ǔN����2
			temp = getDataString(map, "SHUSSEI2");
			if ("1".equals(temp)) {
				row.add("");
			} else {
				row.add(formatUnknownDateCustom(map.getData("HASHOU_DT2")));
			}
			// 040:�o����2
			row.add(temp);
			// 041:�f�f��3
			row.add(getDataString(map, "SINDAN_NM3"));
			// 042:���ǔN����3
			temp = getDataString(map, "SHUSSEI3");
			if ("1".equals(temp)) {
				row.add("");
			} else {
				row.add(formatUnknownDateCustom(map.getData("HASHOU_DT3")));
			}
			// 043:�o����3
			row.add(temp);
			// 044:���@��1�J�n
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_STA1")));
			// 045:���@��1�I��
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_END1")));
			// 046:���@��1���a��
			row.add(getDataString(map, "NYUIN_NM1"));
			// 047:���@��2�J�n
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_STA2")));
			// 048:���@��2�I��
			row.add(formatUnknownDateCustom(map.getData("NYUIN_DT_END2")));
			// 049:���@��2���a��
			row.add(getDataString(map, "NYUIN_NM2"));
			// 050:�Ǐ�Ƃ��Ă̈��萫
			row.add(getDataString(map, "SHJ_ANT"));
			// 051:�Ǐ�s����̋�̓I��
			row.add(getDataString(map, "INSECURE_CONDITION"));
			// 052:���a�̌o�߁]���Ó��e�]���Ï��
			sbBuf = new StringBuffer();
			if (getDataString(map, "MT_STS").length() > 0) {
				sbBuf.append((getDataString(map, "MT_STS").replaceAll("\r\n",
						VT)).replaceAll("\n", VT)); // 0x0B
				sbBuf.append("");
			}
			if ((getDataString(map, "MEDICINE1").length()
					+ getDataString(map, "DOSAGE1").length()
					+ getDataString(map, "UNIT1").length()
					+ getDataString(map, "USAGE1").length()
					+ getDataString(map, "MEDICINE2").length()
					+ getDataString(map, "DOSAGE2").length()
					+ getDataString(map, "UNIT2").length() + getDataString(map,
					"USAGE2").length()) > 0) {
				sbBuf.append(getDataString(map, "MEDICINE1") + " ");
				sbBuf.append(getDataString(map, "DOSAGE1"));
				sbBuf.append(getDataString(map, "UNIT1") + " ");
				sbBuf.append(getDataString(map, "USAGE1") + " / ");
				sbBuf.append(getDataString(map, "MEDICINE2") + " ");
				sbBuf.append(getDataString(map, "DOSAGE2"));
				sbBuf.append(getDataString(map, "UNIT2") + " ");
				sbBuf.append(getDataString(map, "USAGE2") + "");
			}
			if ((getDataString(map, "MEDICINE3").length()
					+ getDataString(map, "DOSAGE3").length()
					+ getDataString(map, "UNIT3").length()
					+ getDataString(map, "USAGE3").length()
					+ getDataString(map, "MEDICINE4").length()
					+ getDataString(map, "DOSAGE4").length()
					+ getDataString(map, "UNIT4").length() + getDataString(map,
					"USAGE4").length()) > 0) {
				sbBuf.append(getDataString(map, "MEDICINE3") + " ");
				sbBuf.append(getDataString(map, "DOSAGE3"));
				sbBuf.append(getDataString(map, "UNIT3") + " ");
				sbBuf.append(getDataString(map, "USAGE3") + " / ");
				sbBuf.append(getDataString(map, "MEDICINE4") + " ");
				sbBuf.append(getDataString(map, "DOSAGE4"));
				sbBuf.append(getDataString(map, "UNIT4") + " ");
				sbBuf.append(getDataString(map, "USAGE4") + "");
			}
			if ((getDataString(map, "MEDICINE5").length()
					+ getDataString(map, "DOSAGE5").length()
					+ getDataString(map, "UNIT5").length()
					+ getDataString(map, "USAGE5").length()
					+ getDataString(map, "MEDICINE6").length()
					+ getDataString(map, "DOSAGE6").length()
					+ getDataString(map, "UNIT6").length() + getDataString(map,
					"USAGE6").length()) > 0) {
				sbBuf.append(getDataString(map, "MEDICINE5") + " ");
				sbBuf.append(getDataString(map, "DOSAGE5"));
				sbBuf.append(getDataString(map, "UNIT5") + " ");
				sbBuf.append(getDataString(map, "USAGE5") + " / ");
				sbBuf.append(getDataString(map, "MEDICINE6") + " ");
				sbBuf.append(getDataString(map, "DOSAGE6"));
				sbBuf.append(getDataString(map, "UNIT6") + " ");
				sbBuf.append(getDataString(map, "USAGE6") + "");
			}
			if (sbBuf.length() > 0) {
				sbBuf.delete(sbBuf.length() - 1, sbBuf.length()); // �Ō��0x0B���폜
			}
			row.add(sbBuf.toString());

			// 053:���u���e
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "TNT_KNR"));
			sbBuf.append(getDataString(map, "CHU_JOU_EIYOU"));
			sbBuf.append(getDataString(map, "TOUSEKI"));
			sbBuf.append(getDataString(map, "JINKOU_KOUMON"));
			sbBuf.append(getDataString(map, "OX_RYO"));
			sbBuf.append(getDataString(map, "JINKOU_KOKYU"));
			sbBuf.append(getDataString(map, "KKN_SEK_SHOCHI"));
			sbBuf.append(getDataString(map, "TOUTU"));
			sbBuf.append(getDataString(map, "KEKN_EIYOU"));
			sbBuf.append(getDataString(map, "KYUIN_SHOCHI"));
			row.add(sbBuf.toString());
			// 054:���u���e�]�z�����u��
			row.add(getDataString(map, "KYUIN_SHOCHI_CNT"));
			// 055:���u���e�]�z�����u����
			row.add(getDataString(map, "KYUIN_SHOCHI_JIKI"));
			// 056:���ʂȑΉ�
			row.add(getDataString(map, "MONITOR")
					+ getDataString(map, "JOKUSOU_SHOCHI"));
			// 057:���ււ̑Ή�
			row.add(getDataString(map, "CATHETER"));

			// 058:�s����̏�Q�̗L���]�L��
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "KS_CHUYA"));
			sbBuf.append(getDataString(map, "KS_BOUGEN"));
			sbBuf.append(getDataString(map, "KS_BOUKOU"));
			sbBuf.append(getDataString(map, "KS_TEIKOU"));
			sbBuf.append(getDataString(map, "KS_HAIKAI"));
			sbBuf.append(getDataString(map, "KS_FUSIMATU"));
			sbBuf.append(getDataString(map, "KS_FUKETU"));
			sbBuf.append(getDataString(map, "KS_ISHOKU"));
			sbBuf.append(getDataString(map, "KS_SEITEKI_MONDAI"));
			sbBuf.append(getDataString(map, "KS_OTHER"));
			if ("0000000000".equals(sbBuf.toString())) {
				row.add("2");
			} else {
				row.add("1");
			}
			// 059:�s����̏�Q�̗L���]�ڍ�
			row.add(sbBuf.toString());
			// 060:�s����̏�Q�̗L���]���̑����e
			row.add(getDataString(map, "KS_OTHER_NM"));
			// 061:���_�E�_�o�Ǐ�̗L���]�L��
			row.add(getDataString(map, "SEISIN"));
			// 062:���_�E�_�o�Ǐ�̗L���]�Ǐ�
			row.add(getDataString(map, "SEISIN_NM"));
			// 063:���_�E�_�o�Ǐ�̗L���]�ڍ�
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "SS_SENMO"));
			sbBuf.append(getDataString(map, "SS_KEMIN_KEIKO"));
			sbBuf.append(getDataString(map, "SS_GNS_GNC"));
			sbBuf.append(getDataString(map, "SS_MOUSOU"));
			sbBuf.append(getDataString(map, "SS_SHIKKEN_TOSHIKI"));
			sbBuf.append(getDataString(map, "SS_SHITUNIN"));
			sbBuf.append(getDataString(map, "SS_SHIKKO"));
			sbBuf.append(getDataString(map, "SS_NINCHI_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_KIOKU_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_CHUI_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_SUIKOU_KINO_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_SHAKAITEKI_KODO_SHOGAI"));
			sbBuf.append(getDataString(map, "SS_OTHER"));
			row.add(sbBuf.toString());
			// 064:���_�E�_�o�Ǐ�̗L���]�L����Q�i�Z���E�����j
			row.add(getDataString(map, "SS_KIOKU_SHOGAI_TANKI")
					+ getDataString(map, "SS_KIOKU_SHOGAI_CHOUKI"));
			// 065:���_�E�_�o�Ǐ�̗L���]���̑����e
			row.add(getDataString(map, "SS_OTHER_NM"));
			// 066:���_�E�_�o�Ǐ�̗L���]�����f�L��
			row.add(getDataString(map, "SENMONI"));
			// 067:���_�E�_�o�Ǐ�̗L���]�����f�Ȗ�
			row.add(getDataString(map, "SENMONI_NM"));

			// 068:�Ă񂩂�]�L��
			row.add(getDataString(map, "TENKAN"));
			// 069:�Ă񂩂�]�p�x
			row.add(getDataString(map, "TENKAN_HINDO"));
			// 070:�����r
			row.add(getDataString(map, "KIKIUDE"));
			// 071:�g��
			row.add(getDataString(map, "HEIGHT"));
			// 072:�̏d
			row.add(getDataString(map, "WEIGHT"));
			// 073:�ߋ�6�����̑̏d�̕ω�
			row.add(getDataString(map, "WEIGHT_CHANGE"));
			// 074:�l������
			row.add(getDataString(map, "SISIKESSON"));
			// 075:�l����������
			row.add(getDataString(map, "SISIKESSON_BUI"));
			// 076:�l���������x
			row.add(getDataString(map, "SISIKESSON_TEIDO"));
			// 077:���
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "MAHI_LEFTARM"));
			sbBuf.append(getDataString(map, "MAHI_LOWERLEFTLIMB"));
			sbBuf.append(getDataString(map, "MAHI_RIGHTARM"));
			sbBuf.append(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
			sbBuf.append(getDataString(map, "MAHI_ETC"));
			if ("00000".equals(sbBuf.toString())) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 078:��Ⴡ]���㎈
			row.add(getDataString(map, "MAHI_LEFTARM"));
			// 079:��Ⴡ]���㎈�]���x
			row.add(getDataString(map, "MAHI_LEFTARM_TEIDO"));
			// 080:��Ⴡ]������
			row.add(getDataString(map, "MAHI_LOWERLEFTLIMB"));
			// 081:��Ⴡ]�������]���x
			row.add(getDataString(map, "MAHI_LOWERLEFTLIMB_TEIDO"));
			// 082:��Ⴡ]�E�㎈
			row.add(getDataString(map, "MAHI_RIGHTARM"));
			// 083:��Ⴡ]�E�㎈�]���x
			row.add(getDataString(map, "MAHI_RIGHTARM_TEIDO"));
			// 084:��Ⴡ]�E����
			row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB"));
			// 085:��Ⴡ]�E�����]���x
			row.add(getDataString(map, "MAHI_LOWERRIGHTLIMB_TEIDO"));
			// 086:��Ⴡ]���̑�
			row.add(getDataString(map, "MAHI_ETC"));
			// 087:��Ⴡ]���̑��]����
			row.add(getDataString(map, "MAHI_ETC_BUI"));
			// 088:��Ⴡ]���̑��]���x
			row.add(getDataString(map, "MAHI_ETC_TEIDO"));
			// 089:�ؗ͂̒ቺ
			row.add(getDataString(map, "KINRYOKU_TEIKA"));
			// 090:�ؗ͂̒ቺ�]����
			row.add(getDataString(map, "KINRYOKU_TEIKA_BUI"));
			// 091:�ؗ͂̒ቺ�]���x
			row.add(getDataString(map, "KINRYOKU_TEIKA_TEIDO"));
			// 092:�֐߂̍S�k
			String tempKata = getDataString(map, "KATA_KOUSHU_MIGI")
					+ getDataString(map, "KATA_KOUSHU_HIDARI");
			String tempMata = getDataString(map, "MATA_KOUSHU_MIGI")
					+ getDataString(map, "MATA_KOUSHU_HIDARI");
			String tempHiji = getDataString(map, "HIJI_KOUSHU_MIGI")
					+ getDataString(map, "HIJI_KOUSHU_HIDARI");
			String tempHiza = getDataString(map, "HIZA_KOUSHU_MIGI")
					+ getDataString(map, "HIZA_KOUSHU_HIDARI");
			if ("00".equals(tempKata) && "00".equals(tempMata)
					&& "00".equals(tempHiji) && "00".equals(tempHiza)
					&& "0".equals(getDataString(map, "KOUSHU_ETC"))) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 093:���֐ߍS�k
			if ("00".equals(tempKata)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 094:���֐ߍS�k�E
			row.add(getDataString(map, "KATA_KOUSHU_MIGI"));
			// 095:���֐ߍS�k�E���x
			row.add(getDataString(map, "KATA_KOUSHU_MIGI_TEIDO"));
			// 096:���֐ߍS�k��
			row.add(getDataString(map, "KATA_KOUSHU_HIDARI"));
			// 097:���֐ߍS�k�����x
			row.add(getDataString(map, "KATA_KOUSHU_HIDARI_TEIDO"));
			// 098:�Ҋ֐ߍS�k
			if ("00".equals(tempMata)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 099:�Ҋ֐ߍS�k�E
			row.add(getDataString(map, "MATA_KOUSHU_MIGI"));
			// 100:�Ҋ֐ߍS�k�E���x
			row.add(getDataString(map, "MATA_KOUSHU_MIGI_TEIDO"));
			// 101:�Ҋ֐ߍS�k��
			row.add(getDataString(map, "MATA_KOUSHU_HIDARI"));
			// 102:�Ҋ֐ߍS�k�����x
			row.add(getDataString(map, "MATA_KOUSHU_HIDARI_TEIDO"));
			// 103:�I�֐ߍS�k
			if ("00".equals(tempHiji)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 104:�I�֐ߍS�k�E
			row.add(getDataString(map, "HIJI_KOUSHU_MIGI"));
			// 105:�I�֐ߍS�k�E���x
			row.add(getDataString(map, "HIJI_KOUSHU_MIGI_TEIDO"));
			// 106:�I�֐ߍS�k��
			row.add(getDataString(map, "HIJI_KOUSHU_HIDARI"));
			// 107:�I�֐ߍS�k�����x
			row.add(getDataString(map, "HIJI_KOUSHU_HIDARI_TEIDO"));
			// 108:�G�֐ߍS�k
			if ("00".equals(tempHiza)) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 109:�G�֐ߍS�k�E
			row.add(getDataString(map, "HIZA_KOUSHU_MIGI"));
			// 110:�G�֐ߍS�k�E���x
			row.add(getDataString(map, "HIZA_KOUSHU_MIGI_TEIDO"));
			// 111:�G�֐ߍS�k��
			row.add(getDataString(map, "HIZA_KOUSHU_HIDARI"));
			// 112:�G�֐ߍS�k�����x
			row.add(getDataString(map, "HIZA_KOUSHU_HIDARI_TEIDO"));
			// 113:�֐߂̍S�k���̑�
			row.add(getDataString(map, "KOUSHU_ETC"));
			// 114:�֐߂̍S�k���̑�����
			row.add(getDataString(map, "KOUSHU_ETC_BUI"));
			// 115:�֐߂̒ɂ�
			row.add(getDataString(map, "KANSETU_ITAMI"));
			// 116:�֐߂̒ɂ݁]����
			row.add(getDataString(map, "KANSETU_ITAMI_BUI"));
			// 117:�֐߂̒ɂ݁]���x
			row.add(getDataString(map, "KANSETU_ITAMI_TEIDO"));
			// 118:�����E�s���Ӊ^��
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "JOUSI_SICCHOU_MIGI"));
			sbBuf.append(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
			sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
			sbBuf.append(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
			sbBuf.append(getDataString(map, "KASI_SICCHOU_MIGI"));
			sbBuf.append(getDataString(map, "KASI_SICCHOU_HIDARI"));
			if ("000000".equals(sbBuf.toString())) {
				row.add("0");
			} else {
				row.add("1");
			}
			// 119:�����E�s���Ӊ^���E�㎈�E�E
			row.add(getDataString(map, "JOUSI_SICCHOU_MIGI"));
			// 120:�����E�s���Ӊ^���E�㎈�E�E�E���x
			row.add(getDataString(map, "JOUSI_SICCHOU_MIGI_TEIDO"));
			// 121:�����E�s���Ӊ^���E�㎈�E��
			row.add(getDataString(map, "JOUSI_SICCHOU_HIDARI"));
			// 122:�����E�s���Ӊ^���E�㎈�E���E���x
			row.add(getDataString(map, "JOUSI_SICCHOU_HIDARI_TEIDO"));
			// 123:�����E�s���Ӊ^���E�̊��E�E
			row.add(getDataString(map, "TAIKAN_SICCHOU_MIGI"));
			// 124:�����E�s���Ӊ^���E�̊��E�E�E���x
			row.add(getDataString(map, "TAIKAN_SICCHOU_MIGI_TEIDO"));
			// 125:�����E�s���Ӊ^���E�̊��E��
			row.add(getDataString(map, "TAIKAN_SICCHOU_HIDARI"));
			// 126:�����E�s���Ӊ^���E�̊��E���E���x
			row.add(getDataString(map, "TAIKAN_SICCHOU_HIDARI_TEIDO"));
			// 127:�����E�s���Ӊ^���E�����E�E
			row.add(getDataString(map, "KASI_SICCHOU_MIGI"));
			// 128:�����E�s���Ӊ^���E�����E�E�E���x
			row.add(getDataString(map, "KASI_SICCHOU_MIGI_TEIDO"));
			// 129:�����E�s���Ӊ^���E�����E��
			row.add(getDataString(map, "KASI_SICCHOU_HIDARI"));
			// 130:�����E�s���Ӊ^���E�����E���E���x
			row.add(getDataString(map, "KASI_SICCHOU_HIDARI_TEIDO"));
			// 131:���
			row.add(getDataString(map, "JOKUSOU"));
			// 132:��ጁ]����
			row.add(getDataString(map, "JOKUSOU_BUI"));
			// 133:��ጁ]���x
			row.add(getDataString(map, "JOKUSOU_TEIDO"));
			// 134:���̑��̔畆����
			row.add(getDataString(map, "HIFUSIKKAN"));
			// 135:���̑��̔畆�����]����
			row.add(getDataString(map, "HIFUSIKKAN_BUI"));
			// 136:���̑��̔畆�����]���x
			row.add(getDataString(map, "HIFUSIKKAN_TEIDO"));

			// 137:���݂��邩�܂��͍��㔭���̍�����ԂƂ��̑Ώ����j(���)
			sbBuf = new StringBuffer();
			sbBuf.append(getDataString(map, "NYOUSIKKIN"));
			sbBuf.append(getDataString(map, "TENTOU_KOSSETU"));
			sbBuf.append(getDataString(map, "HAIKAI_KANOUSEI"));
			sbBuf.append(getDataString(map, "JOKUSOU_KANOUSEI"));
			sbBuf.append(getDataString(map, "ENGESEIHAIEN"));
			sbBuf.append(getDataString(map, "CHOUHEISOKU"));
			sbBuf.append(getDataString(map, "EKIKANKANSEN"));
			sbBuf.append(getDataString(map, "SINPAIKINOUTEIKA"));
			sbBuf.append(getDataString(map, "ITAMI"));
			sbBuf.append(getDataString(map, "DASSUI"));
			sbBuf.append(getDataString(map, "BYOUTAITA"));
			row.add(sbBuf.toString());
			// 138:���݂��邩�܂��͍��㔭���̍�����ԂƂ��̑Ώ����j(���)�E���̑����e
			row.add(getDataString(map, "BYOUTAITA_NM"));
			// 139:���݂��邩�܂��͍��㔭���̍�����ԂƂ��̑Ώ����j(�Ώ����j)
		    sbBuf = new StringBuffer();
		    VRList words = new VRArrayList();
		    poolString(map, words, new String[] { 
		    		"NYOUSIKKIN_TAISHO_HOUSIN",
					"TENTOU_KOSSETU_TAISHO_HOUSIN",
					"HAIKAI_KANOUSEI_TAISHO_HOUSIN",
					"JOKUSOU_KANOUSEI_TAISHO_HOUSIN",
					"ENGESEIHAIEN_TAISHO_HOUSIN", 
					"CHOUHEISOKU_TAISHO_HOUSIN",
					"EKIKANKANSEN_TAISHO_HOUSIN",
					"SINPAIKINOUTEIKA_TAISHO_HOUSIN", 
					"ITAMI_TAISHO_HOUSIN",
					"DASSUI_TAISHO_HOUSIN", 
					"BYOUTAITA_TAISHO_HOUSIN" });
		    
		    if (words.size() > 0) {
				// �Ώ����j�𕶎��P�ʂŘA�����ĕ\���\�ȂƂ���܂ŁB
				final int MAX_LENGTH = 89;

				int inlineSize = 0;
				sbBuf = new StringBuffer();
				int end = words.size() - 1;
				for (int j = 0; j < end; j++) {
					String text = ACCastUtilities.toString(words.get(j));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '�B') && (c != '�A')) {
						line.append("�A");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// �o�͉\�ȂƂ���܂Œǉ�
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// �s�I���`�F�b�N
								break;
							}
						}
						break;
					}
					inlineSize += wordSize;
					sbBuf.append(line.toString());
				}
				if (inlineSize <= MAX_LENGTH) {
					// �����ǉ�
					String text = ACCastUtilities.toString(words.get(end));

					StringBuffer line = new StringBuffer();
					line.append(text);

					int wordSize = 0;
					char c = text.charAt(text.length() - 1);
					if ((c != '�B') && (c != '�A')) {
						line.append("�B");
					}
					wordSize += text.getBytes().length;

					if (inlineSize + wordSize > MAX_LENGTH) {
						// �o�͉\�ȂƂ���܂Œǉ�
						int jEnd = line.length();
						for (int k = 0; k < jEnd; k++) {
							String str = line.substring(k, k + 1);
							sbBuf.append(str);
							inlineSize += str.getBytes().length;
							if (inlineSize > MAX_LENGTH) {
								// �s�I���`�F�b�N
								break;
							}
						}
					} else {
						sbBuf.append(line.toString());
					}
				}
			}
			row.add(sbBuf.toString());		    
			
			// 140:��w�I�ϓ_����̗��ӎ����E����
			row.add(getDataString(map, "KETUATU"));
			// 141:��w�I�ϓ_����̗��ӎ����E�����E���ӎ���
			row.add(getDataString(map, "KETUATU_RYUIJIKOU"));
			// 142:��w�I�ϓ_����̗��ӎ����E����
			row.add(getDataString(map, "ENGE"));
			// 143:��w�I�ϓ_����̗��ӎ����E�����E���ӎ���
			row.add(getDataString(map, "ENGE_RYUIJIKOU"));
			// 144:��w�I�ϓ_����̗��ӎ����E�ېH
			row.add(getDataString(map, "SESHOKU"));
			// 145:��w�I�ϓ_����̗��ӎ����E�ېH�E���ӎ���
			row.add(getDataString(map, "SESHOKU_RYUIJIKOU"));
			// 146:��w�I�ϓ_����̗��ӎ����E�ړ�
			row.add(getDataString(map, "IDOU"));
			// 147:��w�I�ϓ_����̗��ӎ����E�ړ��E���ӎ���
			row.add(getDataString(map, "IDOU_RYUIJIKOU"));
			// 148:��w�I�ϓ_����̗��ӎ����E���̑�
			row.add(getDataString(map, "KAIGO_OTHER"));
			// 149:�����ǂ̗L��
			row.add(getDataString(map, "KANSENSHOU"));
			// 150:�����ǂ̗L���E�ڍ�
			row.add(getDataString(map, "KANSENSHOU_NM"));

			// 151:���L����
			row.add((getDataString(map, "IKN_TOKKI").replaceAll("\r\n", VT))
					.replaceAll("\n", VT));
			// 152:�񎲕]��:���_�Ǐ�
			temp = getDataString(map, "SK_NIJIKU_SEISHIN");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 153:�񎲕]��:�\�͏�Q
			temp = getDataString(map, "SK_NIJIKU_NORYOKU");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 154:�񎲕]��:���莞��
			row.add(formatUnknownDateCustom(map.getData("SK_NIJIKU_DT")));
			// 155:������Q�]��:�H��
			temp = getDataString(map, "SK_SEIKATSU_SHOKUJI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 156:������Q�]��:�������Y��
			temp = getDataString(map, "SK_SEIKATSU_RHYTHM");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 157:������Q�]��:�ې�
			temp = getDataString(map, "SK_SEIKATSU_HOSEI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 158:������Q�]��:���K�Ǘ�
			temp = getDataString(map, "SK_SEIKATSU_KINSEN_KANRI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 159:������Q�]��:����Ǘ�
			temp = getDataString(map, "SK_SEIKATSU_HUKUYAKU_KANRI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 160:������Q�]��:�ΐl�֌W
			temp = getDataString(map, "SK_SEIKATSU_TAIJIN_KANKEI");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 161:������Q�]��:�Љ�I�K����W����s��
			temp = getDataString(map, "SK_SEIKATSU_SHAKAI_TEKIOU");
			if (!("0".equals(temp))) {
				row.add(temp);
			} else {
				row.add("");
			}
			// 162:������Q�]��:���f����
			row.add(formatUnknownDateCustom(map.getData("SK_SEIKATSU_DT")));

			// 1���R�[�h�ǉ�
			csv.addRow(row);

			// �v���O���X�o�[�i�s����
			iwf.setProgressValue(i);

		}

		// CSV�o��
		try {
			// �����o��
			csv.write(true, true);
		} catch (Exception ex) {
			return false;
		}

		iwf.setProgressValue(max);
		return true;

		// add end 2006/08/03 kamitsukasa

	}
    
    /**
     * �����̕������map���甲���o���Alist�Ɋi�[����֐��B
     * @param data �f�[�^
     * @param target �i�[��list
     * @param keys �������KEY
     */
    private void poolString(VRMap data, VRList target, String[] keys) throws Exception{
    	
    	if(keys == null){
    		return;
    	}
    	
    	for(int i = 0; i < keys.length; i++){
    		String temp = ACCastUtilities.toString(VRBindPathParser.get(keys[i], data));
    		if(temp.length() > 0){
    			target.add(temp);
    		}
    	}
    	
    }
    
    /**
	 * CSV�o�͂ɕK�v�ȃf�[�^���擾����SQL�����擾���܂��B
	 * 
	 * @return String
	 */
    private String getSqlOld() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURER_NM");
        sb.append(",IKN_BILL.JIGYOUSHA_NO");
        sb.append(",IKN_BILL.SINSEI_DT");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.SEND_DT");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_BILL.DR_NO");
        sb.append(",IKN_ORIGIN.KIND");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",COMMON_IKN_SIS.POST_CD");
        sb.append(",COMMON_IKN_SIS.ADDRESS");
        sb.append(",COMMON_IKN_SIS.TEL1");
        sb.append(",COMMON_IKN_SIS.TEL2");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.MI_NM");
        sb.append(",COMMON_IKN_SIS.MI_POST_CD");
        sb.append(",COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(",COMMON_IKN_SIS.MI_TEL1");
        sb.append(",COMMON_IKN_SIS.MI_TEL2");
        sb.append(",COMMON_IKN_SIS.MI_FAX1");
        sb.append(",COMMON_IKN_SIS.MI_FAX2");
        sb.append(",IKN_ORIGIN.DR_CONSENT");
        sb.append(",IKN_ORIGIN.LASTDAY");
        sb.append(",IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(",IKN_ORIGIN.TAKA");
        sb.append(",IKN_ORIGIN.TAKA_OTHER");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(",COMMON_IKN_SIS.SHJ_ANT");
        sb.append(",COMMON_IKN_SIS.YKG_YOGO");
        sb.append(",COMMON_IKN_SIS.MT_STS");
        sb.append(",COMMON_IKN_SIS.MEDICINE1");
        sb.append(",COMMON_IKN_SIS.DOSAGE1");
        sb.append(",COMMON_IKN_SIS.UNIT1");
        sb.append(",COMMON_IKN_SIS.USAGE1");
        sb.append(",COMMON_IKN_SIS.MEDICINE2");
        sb.append(",COMMON_IKN_SIS.DOSAGE2");
        sb.append(",COMMON_IKN_SIS.UNIT2");
        sb.append(",COMMON_IKN_SIS.USAGE2");
        sb.append(",COMMON_IKN_SIS.MEDICINE3");
        sb.append(",COMMON_IKN_SIS.DOSAGE3");
        sb.append(",COMMON_IKN_SIS.UNIT3");
        sb.append(",COMMON_IKN_SIS.USAGE3");
        sb.append(",COMMON_IKN_SIS.MEDICINE4");
        sb.append(",COMMON_IKN_SIS.DOSAGE4");
        sb.append(",COMMON_IKN_SIS.UNIT4");
        sb.append(",COMMON_IKN_SIS.USAGE4");
        sb.append(",COMMON_IKN_SIS.MEDICINE5");
        sb.append(",COMMON_IKN_SIS.DOSAGE5");
        sb.append(",COMMON_IKN_SIS.UNIT5");
        sb.append(",COMMON_IKN_SIS.USAGE5");
        sb.append(",COMMON_IKN_SIS.MEDICINE6");
        sb.append(",COMMON_IKN_SIS.DOSAGE6");
        sb.append(",COMMON_IKN_SIS.UNIT6");
        sb.append(",COMMON_IKN_SIS.USAGE6");
        sb.append(",COMMON_IKN_SIS.TNT_KNR");
        sb.append(",COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(",COMMON_IKN_SIS.TOUSEKI");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(",COMMON_IKN_SIS.OX_RYO");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(",COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(",COMMON_IKN_SIS.TOUTU");
        sb.append(",COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(",COMMON_IKN_SIS.MONITOR");
        sb.append(",COMMON_IKN_SIS.JOKUSOU_SHOCHI");
        sb.append(",COMMON_IKN_SIS.RYU_CATHETER");
        sb.append(",COMMON_IKN_SIS.NETAKIRI");
        sb.append(",COMMON_IKN_SIS.CHH_STS");
        sb.append(",IKN_ORIGIN.TANKI_KIOKU");
        sb.append(",IKN_ORIGIN.NINCHI");
        sb.append(",IKN_ORIGIN.DENTATU");
        sb.append(",IKN_ORIGIN.SHOKUJI");
        sb.append(",IKN_ORIGIN.GNS_GNC");
        sb.append(",IKN_ORIGIN.MOUSOU");
        sb.append(",IKN_ORIGIN.CHUYA");
        sb.append(",IKN_ORIGIN.BOUGEN");
        sb.append(",IKN_ORIGIN.BOUKOU");
        sb.append(",IKN_ORIGIN.TEIKOU");
        sb.append(",IKN_ORIGIN.HAIKAI");
        sb.append(",IKN_ORIGIN.FUSIMATU");
        sb.append(",IKN_ORIGIN.FUKETU");
        sb.append(",IKN_ORIGIN.ISHOKU");
        sb.append(",IKN_ORIGIN.SEITEKI_MONDAI");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER_NM");
        sb.append(",IKN_ORIGIN.SEISIN");
        sb.append(",IKN_ORIGIN.SEISIN_NM");
        sb.append(",IKN_ORIGIN.SENMONI");
        sb.append(",IKN_ORIGIN.SENMONI_NM");
        sb.append(",IKN_ORIGIN.KIKIUDE");
        sb.append(",IKN_ORIGIN.WEIGHT");
        sb.append(",IKN_ORIGIN.HEIGHT");
        sb.append(",IKN_ORIGIN.SISIKESSON");
        sb.append(",IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(",IKN_ORIGIN.SISIKESSON_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI");
        sb.append(",IKN_ORIGIN.MAHI_BUI");
        sb.append(",IKN_ORIGIN.MAHI_TEIDO");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(",IKN_ORIGIN.JOKUSOU");
        sb.append(",IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(",IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(",IKN_ORIGIN.KATA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.KATA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.HIJI_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.HIJI_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.MATA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.MATA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.HIZA_KOUSHU_MIGI");
        sb.append(",IKN_ORIGIN.HIZA_KOUSHU_HIDARI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(",IKN_ORIGIN.ENGESEIHAIEN");
        sb.append(",IKN_ORIGIN.CHOUHEISOKU");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(",IKN_ORIGIN.ITAMI");
        sb.append(",IKN_ORIGIN.DASSUI");
        sb.append(",IKN_ORIGIN.BYOUTAITA");
        sb.append(",IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.ITAMI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO_UL");
        sb.append(",IKN_ORIGIN.HOUMON_REHA");
        sb.append(",IKN_ORIGIN.HOUMON_REHA_UL");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA_UL");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_NM");
        sb.append(",IKN_ORIGIN.KETUATU");
        sb.append(",IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.SESHOKU");
        sb.append(",IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.ENGE");
        sb.append(",IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.IDOU");
        sb.append(",IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.KAIGO_OTHER");
        sb.append(",IKN_ORIGIN.KANSENSHOU");
        sb.append(",IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(",IKN_ORIGIN.HASE_SCORE");
        sb.append(",IKN_ORIGIN.HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.P_HASE_SCORE");
        sb.append(",IKN_ORIGIN.P_HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.INST_SEL_PR1");
        sb.append(",IKN_ORIGIN.INST_SEL_PR2");
        sb.append(",IKN_ORIGIN.IKN_TOKKI");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.FORMAT_KBN=0");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        return sb.toString();
    }

    /**
     * CSV�o�͂ɕK�v�ȃf�[�^���擾����SQL�����擾���܂��B
     *
     * @return String
     */
    private String getSqlNew() {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(",IKN_ORIGIN.EDA_NO");
        sb.append(",IKN_ORIGIN.INSURED_NO");
        sb.append(",IKN_BILL.FD_TIMESTAMP");
        sb.append(",IKN_ORIGIN.INSURER_NO");
        sb.append(",IKN_ORIGIN.INSURER_NM");
        sb.append(",IKN_BILL.JIGYOUSHA_NO");
        sb.append(",IKN_BILL.SINSEI_DT");
        sb.append(",IKN_ORIGIN.REQ_DT");
        sb.append(",IKN_ORIGIN.SEND_DT");
        sb.append(",IKN_ORIGIN.REQ_NO");
        sb.append(",IKN_BILL.DR_NO");
        sb.append(",IKN_ORIGIN.KIND");
        sb.append(",IKN_ORIGIN.KINYU_DT");
        sb.append(",COMMON_IKN_SIS.PATIENT_KN");
        sb.append(",COMMON_IKN_SIS.PATIENT_NM");
        sb.append(",COMMON_IKN_SIS.BIRTHDAY");
        sb.append(",COMMON_IKN_SIS.AGE");
        sb.append(",COMMON_IKN_SIS.SEX");
        sb.append(",COMMON_IKN_SIS.POST_CD");
        sb.append(",COMMON_IKN_SIS.ADDRESS");
        sb.append(",COMMON_IKN_SIS.TEL1");
        sb.append(",COMMON_IKN_SIS.TEL2");
        sb.append(",COMMON_IKN_SIS.DR_NM");
        sb.append(",COMMON_IKN_SIS.MI_NM");
        sb.append(",COMMON_IKN_SIS.MI_POST_CD");
        sb.append(",COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(",COMMON_IKN_SIS.MI_TEL1");
        sb.append(",COMMON_IKN_SIS.MI_TEL2");
        sb.append(",COMMON_IKN_SIS.MI_FAX1");
        sb.append(",COMMON_IKN_SIS.MI_FAX2");
        sb.append(",IKN_ORIGIN.DR_CONSENT");
        sb.append(",IKN_ORIGIN.LASTDAY");
        sb.append(",IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(",IKN_ORIGIN.TAKA");
        sb.append(",IKN_ORIGIN.TAKA_OTHER");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(",COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(",COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(",COMMON_IKN_SIS.SHJ_ANT");
        sb.append(",IKN_ORIGIN.INSECURE_CONDITION");
        sb.append(",COMMON_IKN_SIS.MT_STS");
        sb.append(",COMMON_IKN_SIS.MEDICINE1");
        sb.append(",COMMON_IKN_SIS.DOSAGE1");
        sb.append(",COMMON_IKN_SIS.UNIT1");
        sb.append(",COMMON_IKN_SIS.USAGE1");
        sb.append(",COMMON_IKN_SIS.MEDICINE2");
        sb.append(",COMMON_IKN_SIS.DOSAGE2");
        sb.append(",COMMON_IKN_SIS.UNIT2");
        sb.append(",COMMON_IKN_SIS.USAGE2");
        sb.append(",COMMON_IKN_SIS.MEDICINE3");
        sb.append(",COMMON_IKN_SIS.DOSAGE3");
        sb.append(",COMMON_IKN_SIS.UNIT3");
        sb.append(",COMMON_IKN_SIS.USAGE3");
        sb.append(",COMMON_IKN_SIS.MEDICINE4");
        sb.append(",COMMON_IKN_SIS.DOSAGE4");
        sb.append(",COMMON_IKN_SIS.UNIT4");
        sb.append(",COMMON_IKN_SIS.USAGE4");
        sb.append(",COMMON_IKN_SIS.MEDICINE5");
        sb.append(",COMMON_IKN_SIS.DOSAGE5");
        sb.append(",COMMON_IKN_SIS.UNIT5");
        sb.append(",COMMON_IKN_SIS.USAGE5");
        sb.append(",COMMON_IKN_SIS.MEDICINE6");
        sb.append(",COMMON_IKN_SIS.DOSAGE6");
        sb.append(",COMMON_IKN_SIS.UNIT6");
        sb.append(",COMMON_IKN_SIS.USAGE6");
        sb.append(",COMMON_IKN_SIS.TNT_KNR");
        sb.append(",COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(",COMMON_IKN_SIS.TOUSEKI");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(",COMMON_IKN_SIS.OX_RYO");
        sb.append(",COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(",COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(",COMMON_IKN_SIS.TOUTU");
        sb.append(",COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(",COMMON_IKN_SIS.MONITOR");
        sb.append(",COMMON_IKN_SIS.JOKUSOU_SHOCHI");
        // 2006/03/16[Tozo Tanaka] : add begin
        sb.append(",COMMON_IKN_SIS.CATHETER");
        // 2006/03/16[Tozo Tanaka] : add end
        sb.append(",COMMON_IKN_SIS.RYU_CATHETER");
        sb.append(",COMMON_IKN_SIS.NETAKIRI");
        sb.append(",COMMON_IKN_SIS.CHH_STS");
        sb.append(",IKN_ORIGIN.TANKI_KIOKU");
        sb.append(",IKN_ORIGIN.NINCHI");
        sb.append(",IKN_ORIGIN.DENTATU");
        sb.append(",IKN_ORIGIN.GNS_GNC");
        sb.append(",IKN_ORIGIN.MOUSOU");
        sb.append(",IKN_ORIGIN.CHUYA");
        sb.append(",IKN_ORIGIN.BOUGEN");
        sb.append(",IKN_ORIGIN.BOUKOU");
        sb.append(",IKN_ORIGIN.TEIKOU");
        sb.append(",IKN_ORIGIN.HAIKAI");
        sb.append(",IKN_ORIGIN.FUSIMATU");
        sb.append(",IKN_ORIGIN.FUKETU");
        sb.append(",IKN_ORIGIN.ISHOKU");
        sb.append(",IKN_ORIGIN.SEITEKI_MONDAI");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER");
        sb.append(",IKN_ORIGIN.MONDAI_OTHER_NM");
        sb.append(",IKN_ORIGIN.SEISIN");
        sb.append(",IKN_ORIGIN.SEISIN_NM");
        sb.append(",IKN_ORIGIN.SENMONI");
        sb.append(",IKN_ORIGIN.SENMONI_NM");
        sb.append(",IKN_ORIGIN.KIKIUDE");
        sb.append(",IKN_ORIGIN.HEIGHT");
        sb.append(",IKN_ORIGIN.WEIGHT");
        sb.append(",IKN_ORIGIN.WEIGHT_CHANGE");
        sb.append(",IKN_ORIGIN.SISIKESSON");
        sb.append(",IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(",IKN_ORIGIN.MAHI");
        sb.append(",IKN_ORIGIN.MAHI_RIGHTARM");
        sb.append(",IKN_ORIGIN.MAHI_RIGHTARM_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_LEFTARM");
        sb.append(",IKN_ORIGIN.MAHI_LEFTARM_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_LOWERRIGHTLIMB");
        sb.append(",IKN_ORIGIN.MAHI_LOWERRIGHTLIMB_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_LOWERLEFTLIMB");
        sb.append(",IKN_ORIGIN.MAHI_LOWERLEFTLIMB_TEIDO");
        sb.append(",IKN_ORIGIN.MAHI_ETC");
        sb.append(",IKN_ORIGIN.MAHI_ETC_BUI");
        sb.append(",IKN_ORIGIN.MAHI_ETC_TEIDO");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(",IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(",IKN_ORIGIN.KOUSHU");
        sb.append(",IKN_ORIGIN.KOUSHU_BUI");
        sb.append(",IKN_ORIGIN.KOUSHU_TEIDO");
        sb.append(",IKN_ORIGIN.KANSETU_ITAMI");
        sb.append(",IKN_ORIGIN.KANSETU_ITAMI_BUI");
        sb.append(",IKN_ORIGIN.KANSETU_ITAMI_TEIDO");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(",IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(",IKN_ORIGIN.JOKUSOU");
        sb.append(",IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(",IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(",IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(",IKN_ORIGIN.OUTDOOR");
        sb.append(",IKN_ORIGIN.WHEELCHAIR");
        sb.append(",IKN_ORIGIN.ASSISTANCE_TOOL");
        sb.append(",IKN_ORIGIN.MEAL");
        sb.append(",IKN_ORIGIN.NOURISHMENT");
        sb.append(",IKN_ORIGIN.EATING_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(",IKN_ORIGIN.IDOUTEIKA");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(",IKN_ORIGIN.TOJIKOMORI");
        sb.append(",IKN_ORIGIN.IYOKUTEIKA");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(",IKN_ORIGIN.TEIEIYOU");
        sb.append(",IKN_ORIGIN.SESSYOKUENGE");
        sb.append(",IKN_ORIGIN.DASSUI");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN");
        sb.append(",IKN_ORIGIN.GAN_TOUTU");
        sb.append(",IKN_ORIGIN.BYOUTAITA");
        sb.append(",IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(",IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.IDOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TOJIKOMORI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.IYOKUTEIKA_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.TEIEIYOU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.SESSYOKUENGE_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.GAN_TOUTU_TAISHO_HOUSIN");
        sb.append(",IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        // sb.append(",COMMON_IKN_SIS.YKG_YOGO");
        // sb.append(",COMMON_IKN_SIS.IMPRO_SERVICE");
        sb.append(",IKN_ORIGIN.VITAL_FUNCTIONS_OUTLOOK");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMON_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO");
        sb.append(",IKN_ORIGIN.HOUMON_KANGO_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_SINRYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONYAKUZAI_KANRISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMON_REHA");
        sb.append(",IKN_ORIGIN.HOUMON_REHA_UL");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU");
        sb.append(",IKN_ORIGIN.TANKI_NYUSHO_RYOUYOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONSIKA_EISEISIDOU_UL");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU");
        sb.append(",IKN_ORIGIN.HOUMONEIYOU_SHOKUJISIDOU_UL");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA");
        sb.append(",IKN_ORIGIN.TUUSHO_REHA_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_UL");
        sb.append(",IKN_ORIGIN.IGAKUTEKIKANRI_OTHER_NM");
        sb.append(",IKN_ORIGIN.KETUATU");
        sb.append(",IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.IDOU");
        sb.append(",IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.SESHOKU");
        sb.append(",IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.UNDOU");
        sb.append(",IKN_ORIGIN.UNDOU_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.ENGE");
        sb.append(",IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(",IKN_ORIGIN.KAIGO_OTHER");
        sb.append(",IKN_ORIGIN.KANSENSHOU");
        sb.append(",IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(",IKN_ORIGIN.HASE_SCORE");
        sb.append(",IKN_ORIGIN.HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.P_HASE_SCORE");
        sb.append(",IKN_ORIGIN.P_HASE_SCR_DT");
        sb.append(",IKN_ORIGIN.INST_SEL_PR1");
        sb.append(",IKN_ORIGIN.INST_SEL_PR2");
        sb.append(",IKN_ORIGIN.IKN_TOKKI");
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.FORMAT_KBN=1");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        return sb.toString();
    }

    /**
     * CSV�o�͂ɕK�v�ȃf�[�^���擾����SQL�����擾���܂��B
     *
     * @return String
     */
    private String getSqlIshi() {
   	 
    	// add begin 2006/08/03 kamitsukasa
    
        StringBuffer sb = new StringBuffer();
        
        sb.append(" SELECT");
        sb.append(" IKN_ORIGIN.PATIENT_NO");
        sb.append(", IKN_ORIGIN.EDA_NO");
        sb.append(", IKN_ORIGIN.INSURED_NO");
        sb.append(", IKN_BILL.FD_TIMESTAMP");
        sb.append(", IKN_ORIGIN.INSURER_NO");
        sb.append(", IKN_ORIGIN.INSURER_NM");
        sb.append(", IKN_ORIGIN.INSURED_NO");
        sb.append(", IKN_BILL.JIGYOUSHA_NO");
        sb.append(", IKN_BILL.SINSEI_DT");
        sb.append(", IKN_ORIGIN.REQ_DT");
        sb.append(", IKN_ORIGIN.SEND_DT");
        sb.append(", IKN_ORIGIN.REQ_NO");
        sb.append(", IKN_BILL.DR_NO");
        sb.append(", IKN_ORIGIN.KIND");
        sb.append(", IKN_ORIGIN.KINYU_DT");
        sb.append(", COMMON_IKN_SIS.PATIENT_KN");
        sb.append(", COMMON_IKN_SIS.PATIENT_NM");
        sb.append(", COMMON_IKN_SIS.BIRTHDAY");
        sb.append(", COMMON_IKN_SIS.AGE");
        sb.append(", COMMON_IKN_SIS.SEX");
        sb.append(", COMMON_IKN_SIS.POST_CD");
        sb.append(", COMMON_IKN_SIS.ADDRESS");
        sb.append(", COMMON_IKN_SIS.TEL1");
        sb.append(", COMMON_IKN_SIS.TEL2");
        sb.append(", COMMON_IKN_SIS.DR_NM");
        sb.append(", COMMON_IKN_SIS.MI_NM");
        sb.append(", COMMON_IKN_SIS.MI_POST_CD");
        sb.append(", COMMON_IKN_SIS.MI_ADDRESS");
        sb.append(", COMMON_IKN_SIS.MI_TEL1");
        sb.append(", COMMON_IKN_SIS.MI_TEL2");
        sb.append(", COMMON_IKN_SIS.MI_FAX1");
        sb.append(", COMMON_IKN_SIS.MI_FAX2");
        sb.append(", IKN_ORIGIN.DR_CONSENT");
        sb.append(", IKN_ORIGIN.LASTDAY");
        sb.append(", IKN_ORIGIN.IKN_CREATE_CNT");
        sb.append(", IKN_ORIGIN.TAKA");
        sb.append(", IKN_ORIGIN.TAKA_OTHER");
        sb.append(", COMMON_IKN_SIS.SINDAN_NM1");
        sb.append(", COMMON_IKN_SIS.HASHOU_DT1");
        sb.append(", IKN_ORIGIN.SHUSSEI1");
        sb.append(", COMMON_IKN_SIS.SINDAN_NM2");
        sb.append(", COMMON_IKN_SIS.HASHOU_DT2");
        sb.append(", IKN_ORIGIN.SHUSSEI2");
        sb.append(", COMMON_IKN_SIS.SINDAN_NM3");
        sb.append(", COMMON_IKN_SIS.HASHOU_DT3");
        sb.append(", IKN_ORIGIN.SHUSSEI3");
        sb.append(", IKN_ORIGIN.NYUIN_DT_STA1");
        sb.append(", IKN_ORIGIN.NYUIN_DT_END1");
        sb.append(", IKN_ORIGIN.NYUIN_NM1");
        sb.append(", IKN_ORIGIN.NYUIN_DT_STA2");
        sb.append(", IKN_ORIGIN.NYUIN_DT_END2");
        sb.append(", IKN_ORIGIN.NYUIN_NM2");
        sb.append(", COMMON_IKN_SIS.SHJ_ANT");
        sb.append(", IKN_ORIGIN.INSECURE_CONDITION");
        sb.append(", COMMON_IKN_SIS.MT_STS");
        sb.append(", COMMON_IKN_SIS.MEDICINE1");
        sb.append(", COMMON_IKN_SIS.DOSAGE1");
        sb.append(", COMMON_IKN_SIS.UNIT1");
        sb.append(", COMMON_IKN_SIS.USAGE1");
        sb.append(", COMMON_IKN_SIS.MEDICINE2");
        sb.append(", COMMON_IKN_SIS.DOSAGE2");
        sb.append(", COMMON_IKN_SIS.UNIT2");
        sb.append(", COMMON_IKN_SIS.USAGE2");
        sb.append(", COMMON_IKN_SIS.MEDICINE3");
        sb.append(", COMMON_IKN_SIS.DOSAGE3");
        sb.append(", COMMON_IKN_SIS.UNIT3");
        sb.append(", COMMON_IKN_SIS.USAGE3");
        sb.append(", COMMON_IKN_SIS.MEDICINE4");
        sb.append(", COMMON_IKN_SIS.DOSAGE4");
        sb.append(", COMMON_IKN_SIS.UNIT4");
        sb.append(", COMMON_IKN_SIS.USAGE4");
        sb.append(", COMMON_IKN_SIS.MEDICINE5");
        sb.append(", COMMON_IKN_SIS.DOSAGE5");
        sb.append(", COMMON_IKN_SIS.UNIT5");
        sb.append(", COMMON_IKN_SIS.USAGE5");
        sb.append(", COMMON_IKN_SIS.MEDICINE6");
        sb.append(", COMMON_IKN_SIS.DOSAGE6");
        sb.append(", COMMON_IKN_SIS.UNIT6");
        sb.append(", COMMON_IKN_SIS.USAGE6");
        sb.append(", COMMON_IKN_SIS.TNT_KNR");
        sb.append(", COMMON_IKN_SIS.CHU_JOU_EIYOU");
        sb.append(", COMMON_IKN_SIS.TOUSEKI");
        sb.append(", COMMON_IKN_SIS.JINKOU_KOUMON");
        sb.append(", COMMON_IKN_SIS.OX_RYO");
        sb.append(", COMMON_IKN_SIS.JINKOU_KOKYU");
        sb.append(", COMMON_IKN_SIS.KKN_SEK_SHOCHI");
        sb.append(", COMMON_IKN_SIS.TOUTU");
        sb.append(", COMMON_IKN_SIS.KEKN_EIYOU");
        sb.append(", IKN_ORIGIN.KYUIN_SHOCHI");
        sb.append(", IKN_ORIGIN.KYUIN_SHOCHI_CNT");
        sb.append(", IKN_ORIGIN.KYUIN_SHOCHI_JIKI");
        sb.append(", COMMON_IKN_SIS.MONITOR");
        sb.append(", COMMON_IKN_SIS.JOKUSOU_SHOCHI");
        sb.append(", COMMON_IKN_SIS.CATHETER");
        sb.append(", IKN_ORIGIN.KS_CHUYA");
        sb.append(", IKN_ORIGIN.KS_BOUGEN");
        sb.append(", IKN_ORIGIN.KS_BOUKOU");
        sb.append(", IKN_ORIGIN.KS_TEIKOU");
        sb.append(", IKN_ORIGIN.KS_HAIKAI");
        sb.append(", IKN_ORIGIN.KS_FUSIMATU");
        sb.append(", IKN_ORIGIN.KS_FUKETU");
        sb.append(", IKN_ORIGIN.KS_ISHOKU");
        sb.append(", IKN_ORIGIN.KS_SEITEKI_MONDAI");
        sb.append(", IKN_ORIGIN.KS_OTHER");
        sb.append(", IKN_ORIGIN.KS_OTHER_NM");
        sb.append(", IKN_ORIGIN.SEISIN");
        sb.append(", IKN_ORIGIN.SEISIN_NM");
        sb.append(", IKN_ORIGIN.SS_SENMO");
        sb.append(", IKN_ORIGIN.SS_KEMIN_KEIKO");
        sb.append(", IKN_ORIGIN.SS_GNS_GNC");
        sb.append(", IKN_ORIGIN.SS_MOUSOU");
        sb.append(", IKN_ORIGIN.SS_SHIKKEN_TOSHIKI");
        sb.append(", IKN_ORIGIN.SS_SHITUNIN");
        sb.append(", IKN_ORIGIN.SS_SHIKKO");
        sb.append(", IKN_ORIGIN.SS_NINCHI_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_KIOKU_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_CHUI_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_SUIKOU_KINO_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_SHAKAITEKI_KODO_SHOGAI");
        sb.append(", IKN_ORIGIN.SS_OTHER");
        sb.append(", IKN_ORIGIN.SS_KIOKU_SHOGAI_TANKI");
        sb.append(", IKN_ORIGIN.SS_KIOKU_SHOGAI_CHOUKI");
        sb.append(", IKN_ORIGIN.SS_OTHER_NM");
        sb.append(", IKN_ORIGIN.SENMONI");
        sb.append(", IKN_ORIGIN.SENMONI_NM");
        sb.append(", IKN_ORIGIN.TENKAN");
        sb.append(", IKN_ORIGIN.TENKAN_HINDO");
        sb.append(", IKN_ORIGIN.KIKIUDE");
        sb.append(", IKN_ORIGIN.HEIGHT");
        sb.append(", IKN_ORIGIN.WEIGHT");
        sb.append(", IKN_ORIGIN.WEIGHT_CHANGE");
        sb.append(", IKN_ORIGIN.SISIKESSON");
        sb.append(", IKN_ORIGIN.SISIKESSON_BUI");
        sb.append(", IKN_ORIGIN.SISIKESSON_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_LEFTARM");
        sb.append(", IKN_ORIGIN.MAHI_LEFTARM_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_LOWERLEFTLIMB");
        sb.append(", IKN_ORIGIN.MAHI_LOWERLEFTLIMB_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_RIGHTARM");
        sb.append(", IKN_ORIGIN.MAHI_RIGHTARM_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_LOWERRIGHTLIMB");
        sb.append(", IKN_ORIGIN.MAHI_LOWERRIGHTLIMB_TEIDO");
        sb.append(", IKN_ORIGIN.MAHI_ETC");
        sb.append(", IKN_ORIGIN.MAHI_ETC_BUI");
        sb.append(", IKN_ORIGIN.MAHI_ETC_TEIDO");
        sb.append(", IKN_ORIGIN.KINRYOKU_TEIKA");
        sb.append(", IKN_ORIGIN.KINRYOKU_TEIKA_BUI");
        sb.append(", IKN_ORIGIN.KINRYOKU_TEIKA_TEIDO");
        sb.append(", IKN_ORIGIN.KOUSHU");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.KATA_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.MATA_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.HIJI_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_MIGI");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_HIDARI");
        sb.append(", IKN_ORIGIN.HIZA_KOUSHU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.KOUSHU_ETC");
        sb.append(", IKN_ORIGIN.KOUSHU_ETC_BUI");
        sb.append(", IKN_ORIGIN.KANSETU_ITAMI");
        sb.append(", IKN_ORIGIN.KANSETU_ITAMI_BUI");
        sb.append(", IKN_ORIGIN.KANSETU_ITAMI_TEIDO");
        //sb.append(", IKN_ORIGIN.SHICCHO_FLAG");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_MIGI");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_HIDARI");
        sb.append(", IKN_ORIGIN.JOUSI_SICCHOU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_MIGI");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI");
        sb.append(", IKN_ORIGIN.TAIKAN_SICCHOU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_MIGI");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_MIGI_TEIDO");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_HIDARI");
        sb.append(", IKN_ORIGIN.KASI_SICCHOU_HIDARI_TEIDO");
        sb.append(", IKN_ORIGIN.JOKUSOU");
        sb.append(", IKN_ORIGIN.JOKUSOU_BUI");
        sb.append(", IKN_ORIGIN.JOKUSOU_TEIDO");
        sb.append(", IKN_ORIGIN.HIFUSIKKAN");
        sb.append(", IKN_ORIGIN.HIFUSIKKAN_BUI");
        sb.append(", IKN_ORIGIN.HIFUSIKKAN_TEIDO");
        sb.append(", IKN_ORIGIN.NYOUSIKKIN");
        sb.append(", IKN_ORIGIN.TENTOU_KOSSETU");
        sb.append(", IKN_ORIGIN.HAIKAI_KANOUSEI");
        sb.append(", IKN_ORIGIN.JOKUSOU_KANOUSEI");
        sb.append(", IKN_ORIGIN.ENGESEIHAIEN");
        sb.append(", IKN_ORIGIN.CHOUHEISOKU");
        sb.append(", IKN_ORIGIN.EKIKANKANSEN");
        sb.append(", IKN_ORIGIN.SINPAIKINOUTEIKA");
        sb.append(", IKN_ORIGIN.ITAMI");
        sb.append(", IKN_ORIGIN.DASSUI");
        sb.append(", IKN_ORIGIN.BYOUTAITA");
        sb.append(", IKN_ORIGIN.BYOUTAITA_NM");
        sb.append(", IKN_ORIGIN.NYOUSIKKIN_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.TENTOU_KOSSETU_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.HAIKAI_KANOUSEI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.JOKUSOU_KANOUSEI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.ENGESEIHAIEN_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.CHOUHEISOKU_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.EKIKANKANSEN_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.SINPAIKINOUTEIKA_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.ITAMI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.DASSUI_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.BYOUTAITA_TAISHO_HOUSIN");
        sb.append(", IKN_ORIGIN.KETUATU");
        sb.append(", IKN_ORIGIN.KETUATU_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.ENGE");
        sb.append(", IKN_ORIGIN.ENGE_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.SESHOKU");
        sb.append(", IKN_ORIGIN.SESHOKU_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.IDOU");
        sb.append(", IKN_ORIGIN.IDOU_RYUIJIKOU");
        sb.append(", IKN_ORIGIN.KAIGO_OTHER");
        sb.append(", IKN_ORIGIN.KANSENSHOU");
        sb.append(", IKN_ORIGIN.KANSENSHOU_NM");
        sb.append(", IKN_ORIGIN.IKN_TOKKI");
        sb.append(", IKN_ORIGIN.SK_NIJIKU_SEISHIN");
        sb.append(", IKN_ORIGIN.SK_NIJIKU_NORYOKU");
        sb.append(", IKN_ORIGIN.SK_NIJIKU_DT");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_SHOKUJI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_RHYTHM");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_HOSEI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_KINSEN_KANRI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_HUKUYAKU_KANRI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_TAIJIN_KANKEI");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_SHAKAI_TEKIOU");
        sb.append(", IKN_ORIGIN.SK_SEIKATSU_DT");        
        sb.append(" FROM");
        sb.append(" COMMON_IKN_SIS,IKN_ORIGIN,IKN_BILL");
        sb.append(" WHERE");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_ORIGIN.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.PATIENT_NO=IKN_BILL.PATIENT_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_ORIGIN.EDA_NO");
        sb.append(" AND");
        sb.append(" COMMON_IKN_SIS.EDA_NO=IKN_BILL.EDA_NO");
        sb.append(" AND");
        sb.append(" IKN_ORIGIN.FORMAT_KBN=2");
        sb.append(" AND");
        sb.append(" IKN_BILL.FD_OUTPUT_KBN=1");
        
        return sb.toString();
     
    	// add end 2006/08/03 kamitsukasa
    
    }
    
    /**
     * dataDB���ŁA�����ɍ������R�[�h�����Ԗڂɂ���̂����擾���܂��B
     *
     * @param dataDB VRArrayList
     * @param patientNo String
     * @param edaNo String
     * @return int
     */
    private int matchingData(VRArrayList dataDB, String patientNo, String edaNo) {
        for (int i = 0; i < dataDB.getDataSize(); i++) {
            VRMap map = (VRMap) dataDB.getData(i);
            if (String.valueOf(map.getData("PATIENT_NO")).equals(patientNo)) {
                if (String.valueOf(map.getData("EDA_NO")).equals(edaNo)) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * VRHashMap�̃f�[�^�𕶎���Ŏ擾���܂��B
     *
     * @param map VRHashMap
     * @param key String
     * @return String
     */
    private String getDataString(VRMap map, String key) {
        Object tmp = null;
        try {
            tmp = VRBindPathParser.get(key, (VRBindSource) map);
            if (tmp == null) {
                return "";
            }
            if (String.valueOf(tmp).equals("null")) {
                return "";
            }
        } catch (Exception ex) {
            return "";
        }
        return String.valueOf(tmp);
    }

    /**
     * �_���ւ��r�b�g��ɕϊ����܂��B
     *
     * @param ronriwa �_����
     * @param digit ����
     * @return �r�b�g��(������E�֓��֍ς�)
     */
    private String getBitFromRonriwa(String ronriwa, int digit) {
        // �r�b�g��擾
        String bit = "";
        int wa = Integer.parseInt(ronriwa);
        for (int i = 0; i < digit; i++) {
            if ((wa % 2) == 1) {
                bit = "1" + bit;
            } else {
                bit = "0" + bit;
            }
            wa /= 2;
        }

        // ����
        String value = "";
        for (int i = 0; i < bit.length(); i++) {
            value += bit.substring(bit.length() - i - 1, bit.length() - i);
        }

        return value;
    }

    /**
     * �`�F�b�N�L���A�����L����2���ڂ��������A0:�`�F�b�N���A1:�`�F�b�N�̂݁A2:�`�F�b�N�{�����ɕϊ����܂��B
     *
     * @param check �`�F�b�N�L��("0", "1")
     * @param underline �����L��("0", "1")
     * @return "0":�`�F�b�N���A"1":�`�F�b�N�̂݁A"2":�`�F�b�N�{����
     */
    private String getUnderlinedCheckValue(String check, String underline) {
        if (check.equals("1")) {
            if (underline.equals("1")) {
                return "2";
            } else {
                return "1";
            }
        } else {
            return "0";
        }
    }

    /**
     * yyyyMMdd�`���̕�����ɕϊ����܂��B
     *
     * @param obj Object
     * @return String
     */
    private String formatYYYYMMDD(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Date) {
            try {
                return VRDateParser.format((Date) obj, "yyyyMMdd");
            } catch (Exception ex) {

            }
        }

        return "";
    }

    /**
     * ddHHmmss�`���̕�����ɕϊ����܂��B
     *
     * @param obj Object
     * @return String
     */
    private String formatDDHHMMSS(Object obj) {
        if (obj == null) {
            return "";
        }

        if (obj instanceof Date) {
            try {
                return VRDateParser.format((Date) obj, "ddHHmmss");
            } catch (Exception ex) {

            }
        }

        return "";
    }

    /**
     * �u�s�ځv�t���s���S���t��CSV�o�͗p�`���ɕϊ����܂��B
     *
     * @param obj Object
     * @return String
     */
    private String formatUnknownDate(Object obj) {
        // ������ɕϊ�
        if (obj.equals("null")) {
            return "";
        }
        if (obj.equals("null")) {
            return "";
        }
        String date = String.valueOf(obj);

        // ����
        if (date.substring(0, 2).equals("00")) { // ��
            return "";
        }
        if (date.substring(0, 4).equals("0000")) { // ��
            return "";
        }
        if (date.substring(0, 2).equals("�s��")) { // �s��
            return "�s��";
        }
        if (date.substring(5, 7).equals("00")) { // �N�܂�
            return date.substring(0, 5);
//            return date.substring(0, 5) + "��";
        }
        if (date.substring(8, 10).equals("00")) { // ���܂�
            return date.substring(0, 8);
//            return date.substring(0, 8) + "��";
        }
        return date;
//        return date + "��";
    }
    
    /**
     * �u�s�ځv�t���s���S���t��CSV�o�͗p�`���ɕϊ����܂��B
     *
     * @param obj Object
     * @return String
     */
    private String formatUnknownDateCustom(Object obj) throws Exception {
    	// add begin 2006/08/03 kamitsukasa
    	
        // ������ɕϊ�
    	if(obj == null || obj.equals("null")){
    		return "";
    	}
        String date = ACCastUtilities.toString(obj);

        // ����
        if(date.length() >= 2){
	        if (date.substring(0, 2).equals("00")) { // ��
	            return "";
	        }
	        if (date.substring(0, 2).equals("�s��")) { // �s��
	            return "�s��";
	        }
        }
        if(date.length() >= 4){
	        if (date.substring(0, 4).equals("0000")) { // ��
	            return "";
	        }
        }
        if(date.length() >= 7){
	        if (date.substring(5, 7).equals("00")) { // �N�܂�
	            return date.substring(0, 5);
	        }
        }
        if(date.length() >= 10){
	        if (date.substring(8, 10).equals("00")) { // ���܂�
	            return date.substring(0, 8);
	        }
        }
        return date;
        
    	// add end 2006/08/03 kamitsukasa
    }
    
    /**
     * �S�g�}BMP���o�͂��܂��B
     *
     * @param file File
     * @param pix byte[]
     */
    private void writeBmp(File file, byte[] pix) {

        ByteArrayInputStream bais = new ByteArrayInputStream(pix);
        try {
            BufferedImage img = ImageIO.read(bais);
            ACBmpWriter bmp = new ACBmpWriter(file, img, img.getWidth(this),
                    img.getHeight(this));
            bmp.write();
        } catch (IOException ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    /**
     * �ꗗ������s���܂��B
     *
     * @throws Exception
     */
    private void doPrintTable() throws Exception {
        // �y�[�W���̃J�E���g
        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        int endRow = data.getDataSize();
        int page = (int) Math.ceil((double) endRow / 21);
        int row = 1;

        // ����f�[�^�J�n�ݒ�錾
        pd.beginPrintEdit();

        // ���[�擾
        String path = ACFrame.getInstance().getExeFolderPath() + separator
                + "format" + separator + "CSVFileOutputPatientList.xml";
//        pd.addFormat("daicho", path);
        ACChotarouXMLUtilities.addFormat(pd, "daicho", "CSVFileOutputPatientList.xml");

        VRMap tmp = (VRMap) data.getData(0);
        String bkInsurerNo = tmp.getData("INSURER_NO").toString();
        for (int i = 0; i < page; i++) {
            pd.beginPageEdit("daicho");

            // �ی���
            IkenshoCommon.addString(pd, "hokensya", bkInsurerNm + " ("
                    + bkInsurerNo + ")");
            // �o�͕���
            IkenshoCommon.addString(pd, "dateRange.data.title", taisyoDay
                    .getSelectedItem().toString()
                    + "�F");
            // ���t���ԊJ�n
            if (taisyoFrom.getDate() != null) {
                Date taisyoFromDate = taisyoFrom.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.fromY",
                        VRDateParser.format(taisyoFromDate, "ggg�@ee"));
                IkenshoCommon.addString(pd, "dateRange.data.fromM",
                        VRDateParser.format(taisyoFromDate, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.fromD",
                        VRDateParser.format(taisyoFromDate, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.fromY",
                // taisyoFrom.getEra() + " " + taisyoFrom.getYear());
                // IkenshoCommon.addString(pd, "dateRange.data.fromM",
                // dayFormat.format(Long.parseLong(taisyoFrom.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.fromD",
                // dayFormat.format(Long.parseLong(taisyoFrom.getDay())));
            }
            // ���t���ԏI��
            if (taisyoTo.getDate() != null) {
                Date taisyoToDate = taisyoTo.getDate();
                IkenshoCommon.addString(pd, "dateRange.data.toY", VRDateParser
                        .format(taisyoToDate, "ggg�@ee"));
                IkenshoCommon.addString(pd, "dateRange.data.toM", VRDateParser
                        .format(taisyoToDate, "MM"));
                IkenshoCommon.addString(pd, "dateRange.data.toD", VRDateParser
                        .format(taisyoToDate, "dd"));
                // IkenshoCommon.addString(pd, "dateRange.data.toY",
                // taisyoTo.getEra() + " " + taisyoTo.getYear());
                // IkenshoCommon.addString(pd, "dateRange.data.toM",
                // dayFormat.format(Long.parseLong(taisyoTo.getMonth())));
                // IkenshoCommon.addString(pd, "dateRange.data.toD",
                // dayFormat.format(Long.parseLong(taisyoTo.getDay())));
            }
            // �t�H�[�}�b�g
            IkenshoCommon.addString(pd, "formatVersion", "("
                    +ACCastUtilities.toString( formatKbn.getSelectedItem()) + "�t�H�[�}�b�g)");

            for (int j = 0; (j < 21) && (row < endRow + 1); j++, row++) {
                VRMap map = (VRMap) data.getData(row - 1);
                // ��t��
                IkenshoCommon.addString(pd, getHeader(row) + ".DR_NM", 
                        getString("DR_NM", map));
                // ����
                IkenshoCommon.addString(pd, getHeader(row) + ".PATIENT_NM",
                        getString("PATIENT_NM", map));
                // �ӂ肪��
                IkenshoCommon.addString(pd, getHeader(row) + ".PATIENT_KN",
                        getString("PATIENT_KN", map));
                // ����
                switch (((Integer) VRBindPathParser.get("SEX", map)).intValue()) {
                case 1:
                    IkenshoCommon.addString(pd, getHeader(row) + ".SEX", "�j��");
                    break;
                case 2:
                    IkenshoCommon.addString(pd, getHeader(row) + ".SEX", "����");
                    break;
                }
                // �N��
                IkenshoCommon.addString(pd, getHeader(row) + ".AGE", getString(
                        "AGE", map));
                // ���N����
                IkenshoCommon.addString(pd, getHeader(row) + ".BIRTHDAY",
                        IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                                .get("BIRTHDAY", map)));
                // ��ی��Ҕԍ�
                IkenshoCommon.addString(pd, getHeader(row) + ".INSURED_NO",
                        getString("INSURED_NO", map));
                // ��ѽ����
                Object fdTimestamp = map.getData("FD_TIMESTAMP");
                if (fdTimestamp != null) {
                    IkenshoCommon.addString(pd, getHeader(row)
                            + ".FD_TIMESTAMP", formatDDHHMMSS(fdTimestamp));
                }
                // �쐬�˗���
                Object reqDt = map.getData("REQ_DT");
                if (reqDt != null) {
                    IkenshoCommon.addString(pd, getHeader(row) + ".REQ_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(reqDt));
                }
                // �L����
                Object kinyuDt = map.getData("KINYU_DT");
                if (kinyuDt != null) {
                    IkenshoCommon.addString(pd, getHeader(row) + ".KINYU_DT",
                            IkenshoConstants.FORMAT_ERA_YMD.format(kinyuDt));
                }
            }
            pd.endPageEdit();
        }

        pd.endPrintEdit();
        openPDF(pd);
    }

    private String getHeader(int row) {
        return "table.h" + Integer.toString((row - 1) % 21 + 1);
    }

    private static String getString(String key, VRMap map) throws Exception {
        Object obj = VRBindPathParser.get(key, map);
        if (obj != null) {
            return String.valueOf(obj);
        }
        return "";
    }

    /**
     * ��ی��Ҕԍ���0�����s���܂��B
     * @param insuredNoOrg
     * @return
     */
    private String convertInsuredNo(String insuredNoOrg) {
    	String insuredNo = new String();

    	try {
    		//H�Ŏn�܂�ꍇ
        	if (insuredNoOrg.replace('h', 'H').charAt(0) == 'H') {
        		StringBuffer sbZero = new StringBuffer();
        		for (int i=0; i<10 - insuredNoOrg.length(); i++) {
        			sbZero.append("0");
        		}
        		insuredNo = insuredNoOrg.substring(0, 1) + sbZero.toString() +
        			insuredNoOrg.substring(1, insuredNoOrg.length());
        	}
        	//H�Ŏn�܂�Ȃ��ꍇ
        	else {
                DecimalFormat df = new DecimalFormat("0000000000");
                insuredNo = df.format(Integer.parseInt(insuredNoOrg));
        	}

    	}
    	catch(Exception ex) {
    		return insuredNoOrg;
    	}

    	return insuredNo;
    }
}
