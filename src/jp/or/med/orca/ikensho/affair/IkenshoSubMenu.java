package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;

import jp.nichicom.ac.component.mainmenu.ACMainMenuButton;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.layout.VRLayout;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSubMenu extends JDialog {
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

    private int pushedButton = SUB_CLOSED;

    public IkenshoSubMenu(Frame owner, String title, boolean modal, int mainMenu) {
        super(ACFrame.getInstance(), title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            initComponent(mainMenu);
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

        //閉じる
        close = new ACMainMenuButton("閉じる(E)");
        close.setToolTipText("画面を閉じます。");
        close.setMnemonic('E');
//        close.setBackground(new java.awt.Color(102, 102, 255));
//        close.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        close.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_17.png")));
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_CLOSED;
                closeWindow();
            }
        });
    }

    private void initComponent(int mainMenu) {
        //サブメニュー設定
        switch (mainMenu) {
            case MAIN_BASIC:
                setMenuBasic();
//                setSize(new Dimension(600, 500));
                break;
            case MAIN_OTHER:
                setMenuOther();
//                setSize(new Dimension(600, 370));
                break;
            case MAIN_SETTING:
//                setSize(new Dimension(600, 370));
                setMenuSetting();
                break;
        }
        setSize(new Dimension(600, 50 + 72*contentPane.getComponentCount()));


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

    private void setMenuBasic() {
        setTitle("基礎データ登録");

        ACMainMenuButton doctor = new ACMainMenuButton();
        ACMainMenuButton station = new ACMainMenuButton();
        ACMainMenuButton insurer = new ACMainMenuButton();
        ACMainMenuButton jigyousha = new ACMainMenuButton();
        ACMainMenuButton special = new ACMainMenuButton();

        //連携医
        doctor = new ACMainMenuButton("「連携医情報」登録／更新画面(L)");
        doctor.setToolTipText("「連携医情報」の登録と更新を行います。");
        doctor.setMnemonic('L');
//        doctor.setBackground(new java.awt.Color(102, 102, 255));
//        doctor.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        doctor.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_06.png")));
        doctor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_DOCTOR;
                closeWindow();
            }
        });

        //ステーション
        station = new ACMainMenuButton("「訪問看護ステーション情報」登録／更新画面(H)");
        station.setToolTipText("「訪問看護ステーション情報」の登録と更新を行います。");
        station.setMnemonic('H');
//        station.setBackground(new java.awt.Color(102, 102, 255));
//        station.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        station.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_07.png")));
        station.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_STATION;
                closeWindow();
            }
        });

        //保険者
        insurer = new ACMainMenuButton("「保険者」登録／更新画面(B)");
        insurer.setToolTipText("「保険者」の登録と更新を行います。");
        insurer.setMnemonic('B');
//        insurer.setBackground(new java.awt.Color(102, 102, 255));
//        insurer.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        insurer.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_08.png")));
        insurer.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_INSURER;
                closeWindow();
            }
        });

        //医療機関
        jigyousha = new ACMainMenuButton("「医療機関情報」登録／更新画面(C)");
        jigyousha.setToolTipText("「医療機関情報」の登録と更新を行います。");
        jigyousha.setMnemonic('C');
//        jigyousha.setBackground(new java.awt.Color(102, 102, 255));
//        jigyousha.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        jigyousha.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_09.png")));
        jigyousha.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_JIGYOUSHA;
                closeWindow();
            }
        });

        //特記事項編集
        special = new ACMainMenuButton("「特記事項」編集画面(T)");
        special.setToolTipText("「特記事項」の登録と更新を行います。");
        special.setMnemonic('T');
//        special.setBackground(new java.awt.Color(102, 102, 255));
//        special.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        special.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_10.png")));
        special.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_BASIC_SPECIAL;
                closeWindow();
            }
        });

        //メニューに追加
        contentPane.add(doctor, VRLayout.NORTH);
        contentPane.add(station, VRLayout.NORTH);
        contentPane.add(insurer, VRLayout.NORTH);
        contentPane.add(jigyousha, VRLayout.NORTH);
        contentPane.add(special, VRLayout.NORTH);
        contentPane.add(close, VRLayout.NORTH);
    }
    private void setMenuOther() {
        setTitle("その他の機能");

        ACMainMenuButton backup = new ACMainMenuButton();
        ACMainMenuButton restore = new ACMainMenuButton();
        ACMainMenuButton csvOutput = new ACMainMenuButton();
        ACMainMenuButton receiptAccess = new ACMainMenuButton();

        //バックアップ
        backup.setText("データの退避（バックアップ）(T)");
        backup.setMnemonic('t');
        backup.setToolTipText("データの退避(バックアップ)を行います。");
//        backup.setBackground(new java.awt.Color(102, 102, 255));
//        backup.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        backup.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_11.png")));
        backup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_OTHER_BACKUP;
                closeWindow();
            }
        });

        //データの復元
        restore.setText("データの復元（リストア）(F)");
        restore.setMnemonic('f');
        restore.setToolTipText("データの復元（リストア）を行います。");
//        restore.setBackground(new java.awt.Color(102, 102, 255));
//        restore.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        restore.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_12.png")));
        restore.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_OTHER_RESTORE;
                closeWindow();
            }
        });

        //CSVファイル出力
        csvOutput.setText("「主治医意見書」ＣＳＶファイル出力(C)");
        csvOutput.setMnemonic('c');
        csvOutput.setToolTipText("意見書データをCSVファイルに書き出します。");
//        csvOutput.setBackground(new java.awt.Color(102, 102, 255));
//        csvOutput.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        csvOutput.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_13.png")));
        csvOutput.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_OTHER_CSV_OUTPUT;
                closeWindow();
            }
        });

        //日医標準レセプトソフト連携
        receiptAccess.setText("日医標準レセプトソフト連携(R)");
        receiptAccess.setMnemonic('R');
        receiptAccess.setToolTipText("日医標準レセプトソフトから患者情報を取り込みます。");
//        receiptAccess.setBackground(new java.awt.Color(102, 102, 255));
//        receiptAccess.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        receiptAccess.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
            "jp/or/med/orca/ikensho/images/menu/menuicon_19.png")));
        receiptAccess.addActionListener(new java.awt.event.ActionListener() {
          public void actionPerformed(java.awt.event.ActionEvent e) {
            pushedButton = SUB_OTHER_RECEIPT_ACCESS;
            closeWindow();
          }
        });


        //メニューに追加
        contentPane.add(backup, VRLayout.NORTH);
        contentPane.add(restore, VRLayout.NORTH);
        contentPane.add(csvOutput, VRLayout.NORTH);
        contentPane.add(receiptAccess, VRLayout.NORTH);
        contentPane.add(close, VRLayout.NORTH);
    }
    private void setMenuSetting() {
        setTitle("設定");

        ACMainMenuButton db = new ACMainMenuButton();
        ACMainMenuButton tax = new ACMainMenuButton();
        ACMainMenuButton pdf = new ACMainMenuButton();

        //データベース設定
        db.setText("データベース設定(D)");
        db.setMnemonic('D');
        db.setToolTipText("データベースの設定を行います。");
//        db.setBackground(new java.awt.Color(102, 102, 255));
//        db.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        db.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_14.png")));
        db.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_SETTING_DB;
                closeWindow();
            }
        });

        //消費税率設定
        tax.setText("消費税率の設定(T)");
        tax.setMnemonic('T');
        tax.setToolTipText("消費税率の設定を行います。");
//        tax.setBackground(new java.awt.Color(102, 102, 255));
//        tax.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        tax.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_15.png")));
        tax.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_SETTING_TAX;
                closeWindow();
            }
        });

        //PDF設定
        pdf.setText("ＰＤＦ設定(P)");
        pdf.setMnemonic('P');
        pdf.setToolTipText("PDFの設定を行います。");
//        pdf.setBackground(new java.awt.Color(102, 102, 255));
//        pdf.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        pdf.setIcon(new ImageIcon(getClass().getClassLoader().getResource("jp/or/med/orca/ikensho/images/menu/menuicon_16.png")));
        pdf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                pushedButton = SUB_SETTING_PDF;
                closeWindow();
            }
        });

        //メニューに追加
        contentPane.add(db, VRLayout.NORTH);
        contentPane.add(tax, VRLayout.NORTH);
        String osName = System.getProperty("os.name");
        if((osName==null)||(osName.indexOf("Mac")<0)){
          //MacでなければPDF設定を追加する
          contentPane.add(pdf, VRLayout.NORTH);
        }
        contentPane.add(close, VRLayout.NORTH);
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
