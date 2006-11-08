package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoPassiveCheck;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTaxSetting extends IkenshoDialog {
    private JPanel contentPane;
    private ACGroupBox taxGrp = new ACGroupBox();
    private ACLabelContainer taxContainer = new ACLabelContainer();
    private JLabel taxCaption = new JLabel();
    private ACTextField tax = new ACTextField();
    private VRPanel btnGrp = new VRPanel();
    private ACButton submit = new ACButton();
    private ACButton cancel = new ACButton();

    private IkenshoPassiveCheck passiveCheck = new IkenshoPassiveCheck();
    private VRArrayList data;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "TAX", new String[] { "TAX" }, new Format[] { null }, "LAST_TIME",
            "LAST_TIME");

    public IkenshoTaxSetting(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
            event();
            doSelect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IkenshoTaxSetting() {
        this(ACFrame.getInstance(), "����ŗ��̐ݒ�", true);
    }

    public void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(taxGrp, BorderLayout.CENTER);
        contentPane.add(btnGrp, BorderLayout.SOUTH);

        taxGrp.setText("�ŗ�");
        taxGrp.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        VRLayout taxGrpLayout = new VRLayout();
        taxGrpLayout.setAutoWrap(false);
        taxGrp.add(taxContainer, VRLayout.FLOW);
        taxContainer.setText("����ŗ�");
        taxContainer.add(tax, null);
        taxContainer.add(taxCaption, null);
        taxCaption.setText("��");
        tax.setColumns(3);
        tax.setMaxLength(3);
        tax.setHorizontalAlignment(SwingConstants.RIGHT);
        tax.setIMEMode(InputSubset.LATIN_DIGITS);
        tax.setCharType(VRCharType.ONLY_FLOAT);
        tax.setBindPath("TAX");

        btnGrp.setLayout(new VRLayout());
        btnGrp.add(cancel, VRLayout.EAST);
        btnGrp.add(submit, VRLayout.EAST);
        submit.setText("�ۑ�(S)");
        submit.setMnemonic('S');
        cancel.setText("����(C)");
        cancel.setMnemonic('C');
    }

    public void init() throws Exception {
        // ��ʃT�C�Y
        setSize(new Dimension(260, 120));

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

        // �X�i�b�v�V���b�g�B�e�Ώۂ�ݒ�
        IkenshoSnapshot.getInstance().setRootContainer(taxGrp);
    }

    public void event() throws Exception {
        // �ۑ�
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canSubmit()) {
                        doUpdate();
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // ����
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canClose()) {
                        closeWindow();
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (canClose()) {
                closeWindow();
            } else {
                return;
            }
        }
        super.processWindowEvent(e);
    }

    protected void closeWindow() {
        this.dispose();
    }

    /**
     * �o�^�������\���ǂ������`�F�b�N���܂��B
     * 
     * @return true:OK, false:NG
     */
    private boolean canSubmit() {
        // ����ŗ��E�����̓`�F�b�N
        if (tax.getText().length() <= 0) {
            tax.requestFocus();
            ACMessageBox.show("���p�����A�����œ��͂��Ă��������B", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }
        return true;
    }

    /**
     * ���Ă悢���ǂ����𔻒肵�܂��B
     * 
     * @return boolean
     */
    private boolean canClose() {
        try {
            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show("�ύX���e���j������܂��B\n��낵���ł����H",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_OK) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private void doSelect() throws Exception {
        // �t�H�[�J�X
        tax.requestFocus();

        // �擾
        doSelectFromDB();

        // 3���ɍ��킹��
        String figure = tax.getText();
        if (figure.substring(figure.length() - 1, figure.length()).equals(".")) { // ��Ԍ�낪�����_�Ȃ甲��
            tax.setText(figure.substring(0, figure.length() - 1));
        }

        // �X�i�b�v�V���b�g
        IkenshoSnapshot.getInstance().snapshot(); // �B�e
    }

    /**
     * DB����f�[�^���擾���܂��B
     * 
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        try {
            VRMap map = null;
            // ���݂̏���ŗ������[�h
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            data = (VRArrayList) dbm
                    .executeQuery("SELECT TAX,LAST_TIME FROM TAX");
            if (data.getDataSize() > 0) {
                // �p�b�V�u�`�F�b�N
                passiveCheck.clearReservedPassive();
                passiveCheck.reservedPassive(PASSIVE_CHECK_KEY, data);

                map = (VRMap) data.getData(0);
                taxGrp.setSource(map);
                taxGrp.bindSource();
            } else {
                taxGrp.createSource();
                tax.setText("5.0");
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    private void doUpdate() throws Exception {
        if (doUpdateToDB()) {
            ACMessageBox.show("�ۑ�����܂����B");
            doSelect();
            tax.requestFocus();
        }
    }

    /**
     * DB�Ƀf�[�^���X�V���܂��B
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        try {
            // �p�b�V�u�`�F�b�N / �g�����U�N�V�����J�n
            if (data.getDataSize() > 0) {
                passiveCheck.clearPassiveTask();
                passiveCheck.addPassiveDeleteTask(PASSIVE_CHECK_KEY, 0);
                dbm = passiveCheck.getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox
                            .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    doSelect();
                    return false;
                }
            }
            // �폜
            dbm.executeUpdate("DELETE FROM TAX");

            // �C���T�[�g
            StringBuffer sb = new StringBuffer();
            sb.append("INSERT INTO TAX(TAX,LAST_TIME) VALUES(" + tax.getText()
                    + ",CURRENT_TIMESTAMP)");
            dbm.executeUpdate(sb.toString());

            dbm.commitTransaction(); // �R�~�b�g
        } catch (Exception ex) {
            dbm.rollbackTransaction(); // ���[���o�b�N
            throw new Exception(ex);
        }

        return true;
    }
}
