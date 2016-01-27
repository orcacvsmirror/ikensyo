package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.ACOSInfo;
import jp.nichicom.ac.component.mainmenu.ACMainMenuButton;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.layout.VRLayout;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSubMenu extends IkenshoDialog {
    private VRLayout menusLayout = new VRLayout();
    private JPanel contentPane;
    private ACMainMenuButton close;

    public static final int MAIN_BASIC = 0;
    public static final int MAIN_OTHER = 1;
    public static final int MAIN_SETTING = 2;

    public static final int SUB_CLOSED = 0;
    public static final int SUB_BASIC_DOCTOR = 1;
    public static final int SUB_BASIC_STATION = SUB_BASIC_DOCTOR + 1;
    public static final int SUB_BASIC_INSURER = SUB_BASIC_DOCTOR + 2;
    public static final int SUB_BASIC_JIGYOUSHA = SUB_BASIC_DOCTOR + 3;
    public static final int SUB_BASIC_SPECIAL = SUB_BASIC_DOCTOR + 4;
    public static final int SUB_OTHER_BACKUP = 11;
    public static final int SUB_OTHER_RESTORE = SUB_OTHER_BACKUP + 1;
    public static final int SUB_OTHER_CSV_OUTPUT = SUB_OTHER_BACKUP + 2;
    public static final int SUB_OTHER_RECEIPT_ACCESS = SUB_OTHER_BACKUP + 3;
    public static final int SUB_SETTING_DB = 21;
    public static final int SUB_SETTING_TAX = SUB_SETTING_DB + 1;
    public static final int SUB_SETTING_PDF = SUB_SETTING_DB + 2;
    public static final int SUB_SETTING_OTHER = SUB_SETTING_DB + 3;

    private int pushedButton = SUB_CLOSED;

    public IkenshoSubMenu(Frame owner, String title, boolean modal, int mainMenu) {
        super(ACFrame.getInstance(), title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            initComponent(mainMenu);
            //pack();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IkenshoSubMenu(int mainMenu) {
        this(new Frame(), "title", true, mainMenu);
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel)getContentPane();
        this.getContentPane().setBackground(Color.white);
        contentPane.setLayout(menusLayout);
        menusLayout.setFitHLast(true);

        //����
        close = new ACMainMenuButton("����(E)");
        close.setToolTipText("��ʂ���܂��B");
        close.setMnemonic('E');
        close.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_17.png")));
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_CLOSED;
                closeWindow();
            }
        });
        setAjustFont(close);
    }

    private void initComponent(int mainMenu) {
        //�T�u���j���[�ݒ�
    	int[] widths = new int[4];
        switch (mainMenu) {
            case MAIN_BASIC:
                setMenuBasic();
                widths = new int[]{700, 700, 800, 900};
                break;
            case MAIN_OTHER:
                setMenuOther();
                widths = new int[]{600, 600, 700, 800};
                break;
            case MAIN_SETTING:
            	setMenuSetting();
            	widths = new int[]{600, 600, 700, 800};
                break;
        }
        
        int width = 0;
        ACFrame frame = ACFrame.getInstance();
        if (frame.isSmall()){
        	width = widths[0];
        } else if (frame.isMiddle()){
        	width = widths[1];
       	} else if (frame.isLarge()){
       		width = widths[2];
       	} else {
       		width = widths[3];
       	}
        
        int height = (int)close.getPreferredSize().getHeight();
        setSize(new Dimension(width, 30 + height*contentPane.getComponentCount()));

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
    }

    private void setMenuBasic() {
        setTitle("��b�f�[�^�o�^");

        ACMainMenuButton doctor = new ACMainMenuButton();
        ACMainMenuButton station = new ACMainMenuButton();
        ACMainMenuButton insurer = new ACMainMenuButton();
        ACMainMenuButton jigyousha = new ACMainMenuButton();
        ACMainMenuButton special = new ACMainMenuButton();

        //�A�g��
        doctor = new ACMainMenuButton("�u�A�g����v�o�^�^�X�V���(L)");
        doctor.setToolTipText("�u�A�g����v�̓o�^�ƍX�V���s���܂��B");
        doctor.setMnemonic('L');
//        doctor.setBackground(new java.awt.Color(102, 102, 255));
//        doctor.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(doctor);
        
        doctor.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_06.png")));
        doctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_DOCTOR;
                closeWindow();
            }
        });

        //�X�e�[�V����
        station = new ACMainMenuButton("�u�K��Ō�X�e�[�V�������v�o�^�^�X�V���(H)");
        station.setToolTipText("�u�K��Ō�X�e�[�V�������v�̓o�^�ƍX�V���s���܂��B");
        station.setMnemonic('H');
//        station.setBackground(new java.awt.Color(102, 102, 255));
//        station.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(station);
        
        station.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_07.png")));
        station.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_STATION;
                closeWindow();
            }
        });

        //�ی���
        insurer = new ACMainMenuButton("�u�ی��ҁv�o�^�^�X�V���(B)");
        insurer.setToolTipText("�u�ی��ҁv�̓o�^�ƍX�V���s���܂��B");
        insurer.setMnemonic('B');
//        insurer.setBackground(new java.awt.Color(102, 102, 255));
//        insurer.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(insurer);
        
        insurer.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_08.png")));
        insurer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_INSURER;
                closeWindow();
            }
        });

        //��Ë@��
        jigyousha = new ACMainMenuButton("�u��Ë@�֏��v�o�^�^�X�V���(C)");
        jigyousha.setToolTipText("�u��Ë@�֏��v�̓o�^�ƍX�V���s���܂��B");
        jigyousha.setMnemonic('C');
//        jigyousha.setBackground(new java.awt.Color(102, 102, 255));
//        jigyousha.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(jigyousha);
        
        jigyousha.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_09.png")));
        jigyousha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_JIGYOUSHA;
                closeWindow();
            }
        });

        //���L�����ҏW
        special = new ACMainMenuButton("�u���L�����v�ҏW���(T)");
        special.setToolTipText("�u���L�����v�̓o�^�ƍX�V���s���܂��B");
        special.setMnemonic('T');
//        special.setBackground(new java.awt.Color(102, 102, 255));
//        special.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(special);
        
        special.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_10.png")));
        special.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_SPECIAL;
                closeWindow();
            }
        });

        //���j���[�ɒǉ�
        contentPane.add(doctor, VRLayout.NORTH);
        contentPane.add(station, VRLayout.NORTH);
        contentPane.add(insurer, VRLayout.NORTH);
        contentPane.add(jigyousha, VRLayout.NORTH);
        contentPane.add(special, VRLayout.NORTH);
        contentPane.add(close, VRLayout.NORTH);
    }
    private void setMenuOther() {
        setTitle("���̑��̋@�\");

        ACMainMenuButton backup = new ACMainMenuButton();
        ACMainMenuButton restore = new ACMainMenuButton();
        ACMainMenuButton csvOutput = new ACMainMenuButton();
        ACMainMenuButton receiptAccess = new ACMainMenuButton();
        
        //�o�b�N�A�b�v
        backup.setText("�f�[�^�̑ޔ��i�o�b�N�A�b�v�j(T)");
        backup.setMnemonic('t');
        backup.setToolTipText("�f�[�^�̑ޔ�(�o�b�N�A�b�v)���s���܂��B");
//        backup.setBackground(new java.awt.Color(102, 102, 255));
//        backup.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(backup);
        
        backup.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_11.png")));
        backup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_OTHER_BACKUP;
                closeWindow();
            }
        });

        //�f�[�^�̕���
        restore.setText("�f�[�^�̕����i���X�g�A�j(F)");
        restore.setMnemonic('f');
        restore.setToolTipText("�f�[�^�̕����i���X�g�A�j���s���܂��B");
//        restore.setBackground(new java.awt.Color(102, 102, 255));
//        restore.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(restore);
        
        restore.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_12.png")));
        restore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_OTHER_RESTORE;
                closeWindow();
            }
        });

        //CSV�t�@�C���o��
        csvOutput.setText("�u�厡��ӌ����E��t�ӌ����v"+ACConstants.LINE_SEPARATOR+"�@�b�r�u�t�@�C���o��(C)");
        csvOutput.setMnemonic('c');
        csvOutput.setToolTipText("�ӌ����f�[�^��CSV�t�@�C���ɏ����o���܂��B");
//        csvOutput.setBackground(new java.awt.Color(102, 102, 255));
//        csvOutput.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(csvOutput);
        
        csvOutput.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_13.png")));
        csvOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_OTHER_CSV_OUTPUT;
                closeWindow();
            }
        });

        //����W�����Z�v�g�\�t�g�A�g
        receiptAccess.setText("����W�����Z�v�g�\�t�g�A�g(R)");
        receiptAccess.setMnemonic('R');
        receiptAccess.setToolTipText("����W�����Z�v�g�\�t�g���犳�ҏ�����荞�݂܂��B");
//        receiptAccess.setBackground(new java.awt.Color(102, 102, 255));
//        receiptAccess.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(receiptAccess);
        
        receiptAccess.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_19.png")));
        receiptAccess.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent e) {
            pushedButton = SUB_OTHER_RECEIPT_ACCESS;
            closeWindow();
          }
        });


        //���j���[�ɒǉ�
        contentPane.add(backup, VRLayout.NORTH);
        contentPane.add(restore, VRLayout.NORTH);
        contentPane.add(csvOutput, VRLayout.NORTH);
        contentPane.add(receiptAccess, VRLayout.NORTH);
        contentPane.add(close, VRLayout.NORTH);
    }
    private void setMenuSetting() {
        setTitle("�ݒ�");
        ACMainMenuButton db = new ACMainMenuButton();
        ACMainMenuButton tax = new ACMainMenuButton();
        ACMainMenuButton pdf = new ACMainMenuButton();

        //�f�[�^�x�[�X�ݒ�
        db.setText("�f�[�^�x�[�X�ݒ�(D)");
        db.setMnemonic('D');
        db.setToolTipText("�f�[�^�x�[�X�̐ݒ���s���܂��B");
//        db.setBackground(new java.awt.Color(102, 102, 255));
//        db.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(db);

        db.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_14.png")));
        db.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_SETTING_DB;
                closeWindow();
            }
        });

        //����ŗ��ݒ�
        tax.setText("����ŗ��̐ݒ�(T)");
        tax.setMnemonic('T');
        tax.setToolTipText("����ŗ��̐ݒ���s���܂��B");
//        tax.setBackground(new java.awt.Color(102, 102, 255));
//        tax.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(tax);
        
        tax.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_15.png")));
        tax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_SETTING_TAX;
                closeWindow();
            }
        });

        //PDF�ݒ�
        pdf.setText("�o�c�e�ݒ�(P)");
        pdf.setMnemonic('P');
        pdf.setToolTipText("PDF�̐ݒ���s���܂��B");
//        pdf.setBackground(new java.awt.Color(102, 102, 255));
//        pdf.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        setAjustFont(pdf);
        
        pdf.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_16.png")));
        pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_SETTING_PDF;
                closeWindow();
            }
        });

        //���j���[�ɒǉ�
        contentPane.add(db, VRLayout.NORTH);
        contentPane.add(tax, VRLayout.NORTH);
        if(!ACOSInfo.isMac()){
          //Mac�łȂ����PDF�ݒ��ǉ�����
          contentPane.add(pdf, VRLayout.NORTH);
        }
        
        //2009/01/07 [Tozo Tanaka] Add - begin����ܖ����b��B�؂�
//        //���̑��̐ݒ�
//        ACMainMenuButton other = new ACMainMenuButton();
//        other.setText("���̑��̐ݒ�(O)");
//        other.setMnemonic('O');
//        other.setToolTipText("���̑��̐ݒ���s���܂��B");
//        other.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_04.png")));
//        other.addActionListener(new java.awt.event.ActionListener() {
//            public void actionPerformed(java.awt.event.ActionEvent e) {
//                pushedButton = SUB_SETTING_OTHER;
//                closeWindow();
//            }
//        });
//        contentPane.add(other, VRLayout.NORTH);
        //2009/01/07 [Tozo Tanaka] Add - end����ܖ����b��B�؂�
        
        contentPane.add(close, VRLayout.NORTH);
    }
    
    private void setAjustFont(ACMainMenuButton button) {
    	
    	ACFrame frame = ACFrame.getInstance();
    	
        if (frame.isSmall() || frame.isMiddle()){
	        if ( ACOSInfo.isMac() ) {
	        	button.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
	        	
	        } else {
	        	button.setFont(new java.awt.Font("Meiryo", java.awt.Font.PLAIN, 18));
	        }
        }
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
    public int getPushedButtion() {
        return pushedButton;
    }
}
