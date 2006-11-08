package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;
import java.io.File;

import javax.swing.JPanel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.filechooser.ACFileFilter;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSettingPDF extends IkenshoDialog {
    private JPanel contentPane;
    private VRPanel client = new VRPanel();
    private ACLabel info = new ACLabel();
    private VRPanel fileNamePnl = new VRPanel();
    private ACLabelContainer fileNameContainer = new ACLabelContainer();
    private ACTextField fileName = new ACTextField();
    private VRPanel btnGrp = new VRPanel();
    private ACButton submit = new ACButton();
    private ACButton cancel = new ACButton();
    private ACButton fileChoose = new ACButton();

    private static String separator = System.getProperty("file.separator");

    public IkenshoSettingPDF(Frame owner, String title, boolean modal) {
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
    public IkenshoSettingPDF() {
        this(ACFrame.getInstance(), "PDF�̐ݒ�", true);
    }

    private void jbInit() {
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(client, BorderLayout.CENTER);

        client.setLayout(new VRLayout());
        client.add(fileNamePnl, VRLayout.FLOW_RETURN);
        client.add(info, VRLayout.FLOW_RETURN);
        client.add(btnGrp, VRLayout.SOUTH);

       //�t�@�C����
       VRLayout filePnlLayout = new VRLayout();
       filePnlLayout.setAutoWrap(false);
       fileNamePnl.setLayout(filePnlLayout);
       fileNamePnl.add(fileNameContainer, VRLayout.FLOW_INSETLINE);

       fileNameContainer.setText("�t�@�C����");
       fileNameContainer.add(fileName, null);
       fileName.setColumns(32);
       fileName.setMaxLength(1000);
       fileName.setIMEMode(InputSubset.LATIN_DIGITS);
       fileName.setBindPath("FILE_NAME");
       fileNameContainer.add(fileChoose, null);

       //������
       info.setText("AcrobatReader�̎��s�t�@�C����I�����Ă��������B"
                + IkenshoConstants.LINE_SEPARATOR + "��(Windows) �� C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader\\AcroRd32.exe"
       );
       info.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

       //����{�^��
       btnGrp.add(submit, null);
       btnGrp.add(cancel, null);
//       btnGrp.add(fileChoose, null);
       submit.setText("�ݒ�(S)");
       submit.setMnemonic('S');
       cancel.setText("�L�����Z��(C)");
       cancel.setMnemonic('C');
       fileChoose.setText("�Q��(L)");
       fileChoose.setMnemonic('L');
    }

    private void init() throws Exception {
        //�E�B���h�E�T�C�Y
        setSize(new Dimension(480, 160));
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
        //�ݒ�
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canSubmit()) { //���̓`�F�b�N
                        if (writeSetting()) { //XML�X�V
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

        //�Q��
        fileChoose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                doSelectFile();
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
    private boolean canSubmit() {
        //���̓`�F�b�N
        if (IkenshoCommon.isNullText(fileName.getText())) {
            fileName.requestFocus();
            ACMessageBox.showExclamation("�t�@�C�����������͂ł��B");
            return false;
        }

        //�t�@�C�����݃`�F�b�N
        File tmp = new File(fileName.getText());
        if (!tmp.exists()) {
            fileName.requestFocus();
            ACMessageBox.showExclamation("�t�@�C�������s���ł��B");
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
        //�t�@�C���p�X
        fileName.setText(getProperty("Acrobat/Path").toLowerCase());
    }

    /**
     * �t�@�C����I�����܂��B
     */
    private void doSelectFile() {
        //�t�@�C���w��
        ACFileChooser fileChooser = new ACFileChooser();
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(new String[] {"exe"});
        filter.setDescription("Adobe acrobat(*.exe)");

        File fileOld = new File(getProperty("Acrobat/Path"));
        File file = null;
        boolean loopFlg = false;
        do {
            loopFlg = false;

            //�t�@�C���I��
            file = fileChooser.showOpenDialog(fileOld.getParent(), fileOld.getName(), "�J��", filter);

            //�L�����Z�����A���f
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
        } while(loopFlg);

        //�p�X����ʂɔ��f
        fileName.setText(file.getPath());
    }

    /**
     * �ݒ���������݂܂��B
     * @return boolean
     */
    private boolean writeSetting() {
        try {
            ACFrame.getInstance().getPropertyXML().setForceValueAt("Acrobat/Path", fileName.getText());
            //2006/02/12[Tozo Tanaka] : replace begin
//          ACFrame.getInstance().getProperityXML().write();
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
