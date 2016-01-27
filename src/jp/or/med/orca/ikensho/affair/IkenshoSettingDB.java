package jp.or.med.orca.ikensho.affair;


import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;
import java.io.File;
import java.util.Arrays;

import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.filechooser.ACFileFilter;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRRadioButtonGroup;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSettingDB extends IkenshoDialog {
    private JPanel contentPane;
    private VRPanel serverPnl = new VRPanel();
    private ACLabelContainer serverTypeContainer = new ACLabelContainer();
    private VRRadioButtonGroup serverType = new VRRadioButtonGroup();
    private ACLabelContainer serverNameContainer = new ACLabelContainer();
    private ACTextField serverName = new ACTextField();
    private VRPanel filePnl = new VRPanel();
    private ACLabelContainer DBFileNameContainer = new ACLabelContainer();
    private ACTextField DBFileName = new ACTextField();
    private ACButton DBFileNameChooseBbn = new ACButton();
    private ACLabel infomation = new ACLabel();

// VR����AC�ɕύX�̂���
//    private VRLabel info0 = new VRLabel();
//    private VRLabel info1 = new VRLabel();
//    private VRLabel info2 = new VRLabel();
//    private VRLabel info3 = new VRLabel();
//    private VRLabel info4 = new VRLabel();
//    private VRLabel info5 = new VRLabel();
    private VRPanel btnGrp = new VRPanel();
    private ACButton submit = new ACButton();
    private ACButton cancel = new ACButton();
    private boolean modified = false;

    private static String separator = System.getProperty("file.separator");

    public IkenshoSettingDB(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
            event();

            //�X�i�b�v�V���b�g�B�e
            IkenshoSnapshot.getInstance().snapshot();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public IkenshoSettingDB() {
        this(ACFrame.getInstance(), "�f�[�^�x�[�X�̐ݒ�", true);
    }

    /**
     * �_�C�A���O��\�����A�ݒ��ύX��������Ԃ��܂��B
     * @return �ݒ��ύX������
     */
    public boolean showModified(){
        setVisible(true);
//      super.show();
      return modified;
    }

    private void jbInit() {
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new VRLayout());

        contentPane.add(serverPnl, VRLayout.NORTH);
        contentPane.add(filePnl, VRLayout.NORTH);
        contentPane.add(infomation, VRLayout.NORTH);
// VR ����AC�ɕύX�����׈ȉ��R�����g        
//        contentPane.add(info0, VRLayout.NORTH);
//        contentPane.add(info1, VRLayout.NORTH);
//        contentPane.add(info2, VRLayout.NORTH);
//        contentPane.add(info3, VRLayout.NORTH);
//        contentPane.add(info4, VRLayout.NORTH);
//        contentPane.add(info5, VRLayout.NORTH);
        contentPane.add(btnGrp, VRLayout.SOUTH);

        //�T�[�o�I��
        VRLayout serverPnlLayout = new VRLayout();
        serverPnlLayout.setAutoWrap(false);
        serverPnl.add(serverTypeContainer, VRLayout.FLOW_INSETLINE);
        serverPnl.add(serverNameContainer, VRLayout.FLOW_RETURN);

        serverTypeContainer.setText("�T�[�o�[");
        serverTypeContainer.add(serverType, null);
        VRLayout serverTypeLayout = new VRLayout();
        serverTypeLayout.setAutoWrap(false);
        serverType.setLayout(serverTypeLayout);
        serverType.setModel(new VRListModelAdapter(
            new VRArrayList(Arrays.asList(new String[] {
                                          "���[�J��",
                                          "���̃R���s���[�^�["}))));
        serverType.setBindPath("SERVER_TYPE");

       //�T�[�o��
       serverNameContainer.setText("IP");
       serverNameContainer.add(serverName, null);
       serverName.setColumns(20);
       serverName.setMaxLength(30);
       serverName.setIMEMode(InputSubset.LATIN_DIGITS);
       serverName.setCharType(VRCharType.ONLY_ASCII);
       serverName.setBindPath("SERVER_NAME");

       //�t�@�C����
       VRLayout filePnlLayout = new VRLayout();
       filePnlLayout.setAutoWrap(false);
       filePnl.setLayout(filePnlLayout);
       filePnl.add(DBFileNameContainer, VRLayout.FLOW_INSETLINE);
       filePnl.add(DBFileNameChooseBbn, VRLayout.FLOW_RETURN);

       DBFileNameContainer.setText("�t�@�C���̏ꏊ");
       DBFileNameContainer.add(DBFileName, null);
       DBFileName.setColumns(38);
       DBFileName.setMaxLength(1000);
       DBFileName.setIMEMode(InputSubset.LATIN_DIGITS);
       DBFileName.setBindPath("FILE_NAME");
       DBFileNameChooseBbn.setText("�Q��(L)");
       DBFileNameChooseBbn.setMnemonic('L');

       //������
       // Add Kazumi Hirose (Kazumix) 2006/02/27
       infomation.setAutoWrap(true);
       	String infoStr = "�f�[�^�x�[�X�̐ݒ�̑O�Ɉȉ��̎������m�F���Ă��������B"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@1.���̃R���s���[�^�[��I�������ꍇ�́A���̃R���s���[�^�[�ł̃f�[�^�x�[�X�̃t�@�C���̏ꏊ������͂��Ă��������B"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@  �Ȃ��A�ڑ���̃R���s���[�^�[�� Firebird ���C���X�g�[������K�v������܂��B"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@  ��NAS/SAN���̊O���X�g���[�W�͎g�p�ł��܂���B"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@2.�t�@�C���̏ꏊ�ɂ͔��p�p�����݂̂œ��{�ꕶ�����܂߂Ȃ��ł��������B" 
       		+ IkenshoConstants.LINE_SEPARATOR + "�@  �܂ޏꍇ�̓C���X�g�[�����ύX���邩�A�f�[�^�x�[�X����{����܂܂Ȃ��ꏊ�ֈړ����Ă��������B"
            + IkenshoConstants.LINE_SEPARATOR + "�@  �ၛ �� C:\\Ikensyo\\IKENSYO.FDB"
            + IkenshoConstants.LINE_SEPARATOR + "�@  �@�~ �� C:\\�㌩��\\IKENSYO.FDB"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@"
       		+ IkenshoConstants.LINE_SEPARATOR + "�@3.Windows�ɂ����Đڑ���̃R���s���[�^�[�������{��̏ꍇ�A����ɐڑ��ł��܂���B" 
       		+ IkenshoConstants.LINE_SEPARATOR + "�@  [�R���g���[���p�l��]-[�V�X�e��]����R���s���[�^����ύX���Ă��������B"
       		;
       	infomation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
       	infomation.setText(infoStr);
       
// ��L�C���̈�
//       info0.setText("�@�f�[�^�x�[�X�̐ݒ�̑O�Ɉȉ��̎������m�F���Ă��������B");
//       info1.setText("�@1.���̃R���s���[�^�[��I�������ꍇ�́A���̃R���s���[�^�[�ł̃f�[�^�x�[�X�̃t�@�C���̏ꏊ��");
//       info2.setText("�@����͂��Ă��������B�Ȃ��A�ڑ���̃R���s���[�^�[�� Firebird ���C���X�g�[������K�v��");
//       info3.setText("�@����܂��B�@��NAS/SAN���̊O���X�g���[�W�͎g�p�ł��܂���B");
//       info4.setText("�@2.�t�@�C���p�X�ɓ��{��͊܂߂Ȃ��ł��������A�܂ޏꍇ��FDB�t�@�C�����ړ����Ă��������B");
//       info5.setText("�@3.���{��R���s���[�^�[���̏ꍇ�ɂ͐ڑ��ł��܂���B�ύX���čēx�ݒ肵�Ă��������B");
//       info0.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info2.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info3.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info4.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info5.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
       
       //����{�^��
       btnGrp.add(submit, null);
       btnGrp.add(cancel, null);
       submit.setText("�ݒ�(S)");
       submit.setMnemonic('S');
       cancel.setText("�L�����Z��(C)");
       cancel.setMnemonic('C');
    }

    private void init() throws Exception {
        //�E�B���h�E�T�C�Y
    	// [ID:0000729][Masahiko.Higuchi]del - begin 2012�N�x�Ή�
        //setSize(new Dimension(680, 350));
    	// [ID:0000729][Masahiko.Higuchi]del - end
    	// [ID:0000729][Masahiko.Higuchi]add - begin 2012�N�x�Ή�
    	//setSize(new Dimension(700, 350));
    	// [ID:0000729][Masahiko.Higuchi]add - end
        //�E�B���h�E�𒆉��ɔz�u
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
        }
        this.setLocation( (screenSize.width - frameSize.width) / 2,
                         (screenSize.height - frameSize.height) / 2);

       //�ݒ�����[�h
       loadSetting();

       //�X�i�b�v�V���b�g�B�e�Ώۂ�ݒ�
       IkenshoSnapshot.getInstance().setRootContainer(contentPane);
    }

    private void event() throws Exception {
        //�T�[�o���
        serverType.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setButtonEnabled();
            }
        });

        //�Q��
        DBFileNameChooseBbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                doSelectFile();
            }
        });

        //�ݒ�
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canSubmit()) { //���̓`�F�b�N
                        if (writeDBSetting()) { //XML�X�V
                          modified = true;
                          closeWindow();
                        }
                    }
                }
                catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        //�L�����Z��
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (canClose()) {
                    closeWindow();
                }
            }
        });
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (canClose()) {
                closeWindow();
            }
            else {
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
     * @return true:OK, false:NG
     */
    private boolean canSubmit() throws Exception {
        //DB�t�@�C����������
        if (serverType.getSelectedIndex() == 1) {
            if (IkenshoCommon.isNullText(serverName.getText())) {
                serverName.requestFocus();
                ACMessageBox.showExclamation("IP�������͂ł��B");
                return false;
            }
        }

        //�t�@�C����������
        if (IkenshoCommon.isNullText(DBFileName.getText())) {
            DBFileName.requestFocus();
            ACMessageBox.showExclamation("�t�@�C�����������͂ł��B");
            return false;
        }

        //�������R�[�h
        if(!VRCharType.ONLY_HALF_CHAR.isMatch(DBFileName.getText())){
          DBFileName.requestFocus();
          ACMessageBox.showExclamation("�f�[�^�x�[�X�̕ۑ��ꏊ�ɂ͓��{����g�p�ł��܂���B" +
                                       IkenshoConstants.LINE_SEPARATOR +
                                       "�f�[�^�x�[�X���ړ����邩�A�ʂ̃f�[�^�x�[�X���w�肵�Ă��������B");
          return false;
        }

        //DB�ڑ��e�X�g
        if (!isAvailablePath()) {
            DBFileName.requestFocus();
            //2006/02/03[Tozo Tanaka] : replace begin 
            //            ACMessageBox.showExclamation("�f�[�^�x�[�X���I�[�v���ł��܂���B�f�[�^�x�[�X�̐ݒ���������Ă��������B");
            ACMessageBox.showExclamation("�f�[�^�x�[�X���I�[�v���ł��܂���B�f�[�^�x�[�X�̐ݒ���������Ă��������B"
                    + IkenshoConstants.LINE_SEPARATOR + "����Ƃ��āA�ȉ��̍��ڂ����m�F���������B"
                    + IkenshoConstants.LINE_SEPARATOR
                    + IkenshoConstants.LINE_SEPARATOR + "�@�y������z"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�EOS�́uWindows2000(SP2�ȍ~)�v�uWindowsXP�v�uMac OS X v10.3.4�`10.4.4�v�̂����ꂩ�ł����H"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�EJava���s��(JRE)�́uv1.5/1.4�v�AMac�ł́uJava 1.4.2 Update 2�v��K�p�ς݂ł����H"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�E�㌩���̃C���X�g�[������Firebird(Ver1.5)���C���X�g�[�����Ă��܂����H"
                    + IkenshoConstants.LINE_SEPARATOR
                    + IkenshoConstants.LINE_SEPARATOR + "�@�y�Z�L�����e�B�\�t�g�ɂ�鐧���z"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�E�E�B���X�΍�\�t�g��A�Z�L�����e�B�֌W�̃\�t�g�E�F�A���C���X�g�[�����Ă��܂����H"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@�����Y�\�t�g�̐ݒ��ύX���A�|�[�g3050�ł̒ʐM�������邩�AFirebird �Ɏ��s������t�^���Ă��������B"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�E Windows XP�̏ꍇ�A�u�t�@�C�A�E�H�[���v�@�\���g�p����Ă��܂����H"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@���t�@�C�A�E�H�[���̐ݒ��ύX���A�u���b�N���Ȃ���O�v���O������ Firebird ��ǉ����Ă��������B"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@��Windows�ŕW���ݒ�̂܂� Firebird ���C���X�g�[�������ꍇ�A"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@C:\\Program Files\\Firebird\\bin\\fbguard.exe ��"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@C:\\Program Files\\Firebird\\bin\\fbserver.exe ��"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@���s��������ݒ�ɂ��Ă��������B"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@���e�\�t�g�E�F�A�̐ݒ���@�̏ڍׂɂ��܂��ẮA�\�t�g�E�F�A��"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@���[�J�[�ɂ��₢���킹���������B"
                    //2006/02/10[Tozo Tanaka] : add begin
                    + IkenshoConstants.LINE_SEPARATOR
                    + IkenshoConstants.LINE_SEPARATOR + "�@�y�l�b�g���[�N�̐����z"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�EWindows�̏ꍇ�A�R���s���[�^���ɔ��p�p�����ȊO�̓��{�ꕶ�����g�p���Ă��܂����H"
                    + IkenshoConstants.LINE_SEPARATOR + "�@�@�@���ڑ���̃R���s���[�^���𔼊p�p�����ō\�����Ă��������B�i�}�C �R���s���[�^�̃v���p�e�B���Q�Ɓj"
                    //2006/02/10[Tozo Tanaka] : add end
            );
            //2006/02/03[Tozo Tanaka] : replace end
            return false;
        }

        return true;
    }

    /**
     * ���Ă悢���ǂ����𔻒肵�܂��B
     * @return boolean
     */
    private boolean canClose() {
        try {
            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show(
                    "�ύX���e���j������܂��B\n��낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_OK) {
                    return true;
                }
                else {
                    return false;
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    /**
     * �{�^����Enabled��ݒ肵�܂��B
     */
    private void setButtonEnabled() {
        boolean enabled = false;
        if (serverType.getSelectedIndex() == 0) {
            enabled = true;
        }

        DBFileNameChooseBbn.setEnabled(enabled);
        serverName.setEnabled(!enabled);
    }

    /**
     * �ݒ�t�@�C������l���擾���܂��B
     * @param key String
     * @return String
     */
    private String getProperty(String key) {
        String value = "";
        try {
            value = IkenshoCommon.getProperity(key);
        }
        catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return value;
    }

    /**
     * �ݒ�����[�h���܂��B
     * @throws Exception
     */
    private void loadSetting() throws Exception {
        //�T�[�o�̎��
        String svName = getProperty("DBConfig/Server").toLowerCase();
        int svType = -1;
        if (svName.equals("localhost")) {
            svType = 0;
            serverName.setText("");
        }
        else if (svName.equals("127.0.0.1")) {
            svType = 0;
            serverName.setText("");
        }
        else if (!IkenshoCommon.isNullText(svName)) {
            svType = 1;
            serverName.setText(getProperty("DBConfig/Server"));
        }
        serverType.setSelectedIndex(svType);
        setButtonEnabled();

        //�t�@�C���p�X
        DBFileName.setText(getProperty("DBConfig/Path").toLowerCase());
    }

    /**
     * ���[�J������DB�t�@�C���̎Q�Ƃ��擾���܂��B
     */
    private void doSelectFile() {
        //�t�@�C���w��
        ACFileChooser fileChooser = new ACFileChooser();
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(new String[] {"fdb"});
        filter.setDescription("�f�[�^�x�[�X�t�@�C��(*.fdb)");

        File file = null;
        File fileOld = new File(getProperty("DBConfig/Path"));
        boolean loopFlg = false;
        do {
            loopFlg = false;

            //�t�@�C���I��
            file = fileChooser.showOpenDialog(
                fileOld.getParent(), fileOld.getName(),
                "�g�p����f�[�^�x�[�X�t�@�C�����w�肵�ĉ������B",
                filter);

            //�L�����Z�����͒��f
            if (file == null) {
                return;
            }

            //�g���q��⊮
            file = new File(file.getParent() + separator +
                            ( (ACFileFilter) fileChooser.getFileFilter()).
                            getFilePathWithExtension(file.getName()));

            //�t�@�C�����݃`�F�b�N
            if (!file.exists()) {
                ACMessageBox.showExclamation("�t�@�C�������s���ł��B");
                loopFlg = true;
                continue;
            }
        }while (loopFlg);

        DBFileName.setText(file.getPath());
    }

    /**
     * ���p�\�ȃp�X���ǂ������؂��܂��B
     * @return boolean
     */
    private boolean isAvailablePath() throws Exception {
        IkenshoFirebirdDBManager fdbm = new IkenshoFirebirdDBManager();
        try {
            //�V�p�����[�^�擾
            String server;
            if (serverType.getSelectedIndex() == 0) {
                server = "localhost";
            }
            else {
                server = serverName.getText();
            }
            String path = DBFileName.getText();

            //�ڑ��e�X�g
            fdbm.releaseAll(); //�v�[�����Ă���R�l�N�V���������
            fdbm = new IkenshoFirebirdDBManager(
                server,
                Integer.parseInt(getProperty("DBConfig/Port")),
                getProperty("DBConfig/UserName"),
                getProperty("DBConfig/Password"),
                path,
                Integer.parseInt(getProperty("DBConfig/LoginTimeOut")),
                Integer.parseInt(getProperty("DBConfig/MaxPoolSize")));
            if (fdbm.isAvailableDBConnection()) {
                fdbm.releaseAll(); //��Еt��
                return true;
            }
            else {
                fdbm.releaseAll(); //��Еt��
                return false;
            }
        }
        catch (Exception ex) {
            fdbm.releaseAll(); //��Еt��
            return false;
        }
    }

    private void restoreToOriginalConnection() {

    }

    /**
     * DB�ݒ�t�@�C���������݂��s���܂��B
     * @return boolean
     * @throws Exception
     */
    private boolean writeDBSetting() throws Exception {
        try {
            if (serverType.getSelectedIndex() == 0) {
                ACFrame.getInstance().getPropertyXML().setForceValueAt("DBConfig/Server", "localhost");
            }
            else {
                ACFrame.getInstance().getPropertyXML().setForceValueAt("DBConfig/Server", serverName.getText());
            }
            ACFrame.getInstance().getPropertyXML().setForceValueAt("DBConfig/Path", DBFileName.getText());
            
            //2006/02/12[Tozo Tanaka] : replace begin
//            ACFrame.getInstance().getProperityXML().write();
            if(!ACFrame.getInstance().getPropertyXML().writeWithCheck()){
                return false;
            }
            //2006/02/12[Tozo Tanaka] : replace end
        }
        catch (Exception ex) {
            //��O�����itry�����ɌĂяo�������\�b�h��throws���Ă��悢�j
            IkenshoCommon.showExceptionMessage(ex);
        }

        return true;
    }
}
