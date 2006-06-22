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
     * コンストラクタ
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
        this(new Frame(), "情報選択画面", true);
    }


    private void jbInit() throws Exception {
        setTitle("情報選択画面");
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(menusLayout);
        menusLayout.setFitHLast(true);

        renkeii = new ACMainMenuButton("「連携医情報」登録／更新画面(L)");
        renkeii.setMnemonic('L');
        renkeii.setBackground(new java.awt.Color(102, 102, 255));
        renkeii.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        renkeii.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_01.png")));
        renkeii.setToolTipText("連携医情報一覧画面を表示します。");
        renkeii.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = RENKEII;
                closeWindow();
            }
        });

        station = new ACMainMenuButton("「訪問看護ステーション情報」登録／更新画面(H)");
        station.setMnemonic('H');
        station.setBackground(new java.awt.Color(102, 102, 255));
        station.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        station.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_02.png")));
        station.setToolTipText("訪問看護ステーション情報一覧画面を表示します。");
        station.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = STATION;
                closeWindow();
            }
        });

        close = new ACMainMenuButton("閉じる(C)");
        close.setMnemonic('C');
        close.setBackground(new java.awt.Color(102, 102, 255));
        close.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        close.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_03.png")));
        close.setToolTipText("画面を閉じます。");
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
     * コンポーネントの初期化
     */
    private void initComponent() {
        //ウィンドウのサイズ
        setSize(new Dimension(600, 300));
        //ウィンドウを中央に配置
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
     * ウィンドウが閉じられたときにDisposeするようにオーバーライド
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }

    /**
     * ウィンドウを閉じる
     */
    protected void closeWindow() {
        this.dispose();
    }

    public int getPushedButtion() {
        return pushedButton;
    }
}
