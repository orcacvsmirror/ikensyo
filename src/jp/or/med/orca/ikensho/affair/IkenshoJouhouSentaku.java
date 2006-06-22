package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import jp.nichicom.ac.component.mainmenu.ACMainMenuButton;
import jp.nichicom.vr.layout.VRLayout;

/** TODO <HEAD_IKENSYO> */
public class IkenshoJouhouSentaku extends JDialog {
    private VRLayout menusLayout = new VRLayout();
    private JPanel contentPane;
    private ACMainMenuButton renkeii;
    private ACMainMenuButton station;
    private ACMainMenuButton close;
    public static final int CLOSED = 0;
    public static final int RENKEII = 1;
    public static final int STATION = 2;
    private int pushedButton = CLOSED;

    /**
     * �R���X�g���N�^
     * @param owner Frame
     * @param title String
     * @param modal boolean
     */
    public IkenshoJouhouSentaku(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            initComponent();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IkenshoJouhouSentaku() {
        this(new Frame(), "���I�����", true);
    }


    private void jbInit() throws Exception {
        setTitle("���I�����");
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(menusLayout);
        menusLayout.setFitHLast(true);

        renkeii = new ACMainMenuButton("�u�A�g����v�o�^�^�X�V���(L)");
        renkeii.setMnemonic('L');
        renkeii.setBackground(new java.awt.Color(102, 102, 255));
        renkeii.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        renkeii.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_01.png")));
        renkeii.setToolTipText("�A�g����ꗗ��ʂ�\�����܂��B");
        renkeii.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = RENKEII;
                closeWindow();
            }
        });

        station = new ACMainMenuButton("�u�K��Ō�X�e�[�V�������v�o�^�^�X�V���(H)");
        station.setMnemonic('H');
        station.setBackground(new java.awt.Color(102, 102, 255));
        station.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        station.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_02.png")));
        station.setToolTipText("�K��Ō�X�e�[�V�������ꗗ��ʂ�\�����܂��B");
        station.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = STATION;
                closeWindow();
            }
        });

        close = new ACMainMenuButton("����(C)");
        close.setMnemonic('C');
        close.setBackground(new java.awt.Color(102, 102, 255));
        close.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        close.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_03.png")));
        close.setToolTipText("��ʂ���܂��B");
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = CLOSED;
                closeWindow();
            }
        });

        contentPane.add(renkeii, VRLayout.NORTH);
        contentPane.add(station, VRLayout.NORTH);
        contentPane.add(close, VRLayout.NORTH);
    }

    /**
     * �R���|�[�l���g�̏�����
     */
    private void initComponent() {
        //�E�B���h�E�̃T�C�Y
        setSize(new Dimension(600, 300));
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

    /**
     * �E�B���h�E������ꂽ�Ƃ���Dispose����悤�ɃI�[�o�[���C�h
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }

    /**
     * �E�B���h�E�����
     */
    protected void closeWindow() {
        this.dispose();
    }

    public int getPushedButtion() {
        return pushedButton;
    }
}
