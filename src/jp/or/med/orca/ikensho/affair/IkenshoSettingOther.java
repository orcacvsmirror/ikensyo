package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JPanel;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.component.VRRadioButtonGroup;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

public class IkenshoSettingOther extends IkenshoDialog {

    private JPanel contentPane;
    private ACPanel client = new ACPanel();
    private ACPanel medicineViewCountPanel = new ACPanel();
    private ACLabelContainer medicineViewCountContainer = new ACLabelContainer();
    private VRRadioButtonGroup medicineViewCount = new VRRadioButtonGroup();
    private ACLabel medicineViewCountSummary = new ACLabel();
    private ACLabelContainer medicalViewCountOfShijisho6Container = new ACLabelContainer();
    private ACIntegerCheckBox medicalViewCountOfShijisho6 = new ACIntegerCheckBox();
    private ACPanel btnGrp = new ACPanel();
    private ACButton submit = new ACButton();
    private ACButton cancel = new ACButton();

    public IkenshoSettingOther(Frame owner, String title, boolean modal) {
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
    public IkenshoSettingOther() {
        this(ACFrame.getInstance(), "���̑��̐ݒ�", true);
    }

    private void jbInit() {
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(client, BorderLayout.CENTER);

        client.add(medicineViewCountPanel, VRLayout.FLOW_RETURN);
        client.add(btnGrp, VRLayout.SOUTH);

       //�t�@�C����
       medicineViewCountPanel.setAutoWrap(false);
       medicineViewCountPanel.add(medicineViewCountContainer, VRLayout.FLOW_INSETLINE_RETURN);

       medicineViewCountContainer.setText("��ܖ��̕\����");
       medicineViewCountContainer.add(medicineViewCount, null);
       medicineViewCount.setModel(new ACListModelAdapter(new VRArrayList(Arrays.asList(new String[] {
                                          "6��",
                                          "8��"}))));
       medicineViewCount.setBindPath("FILE_NAME");

       medicineViewCountSummary.setText("��܍��ڂɂ���āA���a�̌o�߂ɓ��͉\�ȕ��������قȂ�܂��B"
                + ACConstants.LINE_SEPARATOR + "�@��܂�1�`6���͂����ꍇ�F���a�̌o�߂�250�����܂œ��͉\"
                + ACConstants.LINE_SEPARATOR + "�@��܂�7���͂����ꍇ�F���a�̌o�߂�220�����܂œ��͉\"
                + ACConstants.LINE_SEPARATOR + "�@��܂�8���͂����ꍇ�F���a�̌o�߂�190�����܂œ��͉\"
                );
       
       medicalViewCountOfShijisho6.setText("�K��Ō�w�����̖�ܖ��\������6�ɂ���");
       medicalViewCountOfShijisho6Container.add(medicalViewCountOfShijisho6);
       
       medicineViewCountSummary.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
       medicineViewCountPanel.add(medicineViewCountSummary, VRLayout.FLOW_INSETLINE_RETURN);
       medicineViewCountPanel.add(medicalViewCountOfShijisho6Container, VRLayout.FLOW_RETURN);
       
       
       //����{�^��
       btnGrp.setAlignment(VRLayout.CENTER);
       btnGrp.add(submit, null);
       btnGrp.add(cancel, null);

       submit.setText("�ݒ�(S)");
       submit.setMnemonic('S');
       cancel.setText("�L�����Z��(C)");
       cancel.setMnemonic('C');
    }

    private void init() throws Exception {
        //�E�B���h�E�T�C�Y
        //setSize(new Dimension(480, 160));
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
                    if (writeSetting()) { //XML�X�V
                        closeWindow();
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
        
        if (ACFrame.getInstance().hasProperty(
                "DocumentSetting/MedicineViewCount")
                && ACCastUtilities.toInt(getProperty("DocumentSetting/MedicineViewCount"),6)==8) {
            medicineViewCount.setSelectedIndex(1);
        }else{
            medicineViewCount.setSelectedIndex(0);
        }
        
        if (ACFrame.getInstance().hasProperty(
                "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6")
                && ACCastUtilities
                        .toBoolean(
                                getProperty("DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6"),
                                false)) {
            medicalViewCountOfShijisho6.setSelected(true);
        } else {
            medicalViewCountOfShijisho6.setSelected(false);
        }
        
    }

    /**
     * �ݒ���������݂܂��B
     * @return boolean
     */
    private boolean writeSetting() {
        try {
            String medicineViewCountValue = "6";
            if(medicineViewCount.getSelectedIndex()==1){
                medicineViewCountValue = "8";
            }
            if("6".equals(medicineViewCountValue)){
                //6��I�������ꍇ
                if (ACFrame.getInstance().hasProperty(
                        "DocumentSetting/MedicineViewCount")
                        && ACCastUtilities
                                .toInt(
                                        getProperty("DocumentSetting/MedicineViewCount"),
                                        6) == 8) {
                    //���̐ݒ��8�������ꍇ
                    //TODO ���7�܂��͖��8�̓��͂���Ă���f�[�^�����邩�m�F
                    
                    if (ACMessageBox
                            .show(
                                    "��ܖ���7�ȏ���͂���Ă���ӌ��������݂��܂��B"
                                            + ACConstants.LINE_SEPARATOR
                                            + "��ܖ��̕\������6�ɐݒ肵���ꍇ�A7�ȏ�̖�ܖ��͈������Ȃ��Ȃ�܂��B"
                                            + ACConstants.LINE_SEPARATOR
                                            + "��낵���ł����H",
                                    ACMessageBox.BUTTON_OKCANCEL,
                                    ACMessageBox.ICON_QUESTION,
                                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return false;
                    }
                    
                }
            }
            ACFrame.getInstance().getPropertyXML().setForceValueAt("DocumentSetting/MedicineViewCount", medicineViewCountValue);
            

            //�K��Ō�w�����̖�ܖ��\������6�ɂ���
            boolean medicineViewCountOfHoumonKangoShijishoFixed6 = medicalViewCountOfShijisho6.isSelected();
            ACFrame
                    .getInstance()
                    .getPropertyXML()
                    .setForceValueAt(
                            "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6",
                            ACCastUtilities
                                    .toString(new Boolean(
                                            medicineViewCountOfHoumonKangoShijishoFixed6)));
      
          if(!ACFrame.getInstance().getPropertyXML().writeWithCheck()){
              return false;
          }
        }
        catch (Exception ex) {
            //��O�����itry�����ɌĂяo�������\�b�h��throws���Ă��悢�j
            IkenshoCommon.showExceptionMessage(ex);
        }

        return true;
    }
}
