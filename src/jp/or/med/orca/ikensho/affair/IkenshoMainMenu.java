package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACEditorPane;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.mainmenu.ACMainMenuButton;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.version.ACVersionAdjuster;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.splash.ACSplashChaine;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.event.IkenshoHiddenCommandKeyListener;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.component.*;

/** TODO <HEAD_IKENSYO> */
public class IkenshoMainMenu extends VRPanel implements ACAffairable {
    public static void main(String[] args) {
        try {

            ACFrame.setVRLookAndFeel();

            ACFrame.getInstance().setFrameEventProcesser(
                    new IkenshoFrameEventProcesser());
            ACFrame.getInstance().next(
                    new ACAffairInfo(IkenshoMainMenu.class.getName(),
                            new VRHashMap(), "「医見書Ver2.5」メインメニュー"));
            ACFrame frame = ACFrame.getInstance();
            frame.setVisible(true);
            // frame.show();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private String systemVersionText;

    private VRPanel titleBar = new VRPanel();
    private JLabel title = new JLabel();

    private VRPanel menus = new VRPanel();
    private ACMainMenuButton ikenshoShijisho = new ACMainMenuButton(
            "「主治医意見書・医師意見書・訪問看護指示書」"+ACConstants.LINE_SEPARATOR+"　作成／編集(K)");
    private ACMainMenuButton seikyuusho = new ACMainMenuButton("「請求書」発行(V)");
    private ACMainMenuButton basic = new ACMainMenuButton("基礎データ登録(B)");
    private ACMainMenuButton other = new ACMainMenuButton("その他の機能(O)");
    private ACMainMenuButton setting = new ACMainMenuButton("設定(S)");
    private ACMainMenuButton close = new ACMainMenuButton("システムの終了(E)");
    private VRPanel closeBtnPnl = new VRPanel();

    private IkenshoHiddenCommandKeyListener cmd = new IkenshoHiddenCommandKeyListener();
    private JLabel systemVersion = new JLabel();
    private VRPanel versionPanel = new VRPanel();
    private JLabel schemaVersion = new JLabel();
    private JLabel dataVersion = new JLabel();
    private JLabel versionBottomSpacer = new JLabel();
    private JLabel versionTopSpacer = new JLabel();
    
    // 2006/02/08[Shin Fujihara] : add start
    private JLabel osVersion = new JLabel();
    private JLabel spVersion = new JLabel();
    private JLabel javaVersion = new JLabel();
    private JLabel javaVersion2 = new JLabel();
    private JLabel firebirfVersion = new JLabel();
    private JLabel firebirfVersion2 = new JLabel();
    // 2006/02/08[Shin Fujihara] : add end
    // 2007/10/22 [Masahiko Higuchi] Addition - begin バージョンアップお知らせ機能対応
    private ACEditorPane editor;
    // 2007/10/22 [Masahiko Higuchi] Addition - end

    public IkenshoMainMenu() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    private void jbInit() throws Exception {
        this.setLayout(new BorderLayout());
        systemVersion.setForeground(Color.white);
        systemVersion.setHorizontalAlignment(SwingConstants.CENTER);
        versionPanel.setOpaque(false);
        versionPanel.setLayout(new VRLayout());

        dataVersion.setForeground(Color.white);
        schemaVersion.setForeground(Color.white);
        versionBottomSpacer.setText(" ");
        versionTopSpacer.setText(" ");
        versionPanel.add(versionTopSpacer, VRLayout.FLOW_RETURN);
        //2006/02/08[Shin Fujihara] : add start
        //Java,OS,Firebirdのバージョン表記を追加
        javaVersion.setForeground(Color.white);
        javaVersion2.setForeground(Color.white);
        osVersion.setForeground(Color.white);
        spVersion.setForeground(Color.white);
        firebirfVersion.setForeground(Color.white);
        firebirfVersion2.setForeground(Color.white);
        versionPanel.add(osVersion,VRLayout.FLOW_RETURN);
        versionPanel.add(spVersion,VRLayout.FLOW_RETURN);
        versionPanel.add(javaVersion,VRLayout.FLOW_RETURN);
        versionPanel.add(javaVersion2,VRLayout.FLOW_RETURN);
        versionPanel.add(firebirfVersion,VRLayout.FLOW_RETURN);
        versionPanel.add(firebirfVersion2,VRLayout.FLOW_RETURN);
        //2006/02/08[Shin Fujihara] : add end
        versionPanel.add(systemVersion, VRLayout.FLOW_RETURN);
        versionPanel.add(dataVersion, VRLayout.FLOW_RETURN);
        versionPanel.add(schemaVersion, VRLayout.FLOW_RETURN);
        versionPanel.add(versionBottomSpacer, VRLayout.FLOW_RETURN);
        
        // 2007/10/22 [Masahiko Higuchi] Addition - begin バージョンアップお知らせ機能対応
        editor = new ACEditorPane();
        try{
            // HTML読込みクラス生成
            IkenshoHtmlPageReader reader = new IkenshoHtmlPageReader();
            // 別スレッドで読込み開始
            reader.start();
            
        }catch(Exception e){}
        
        VRPanel menuIkenshoShijishoPanel = new VRPanel(new VRLayout());
        VRPanel menuSeikyuushoAndBasicPanel = new VRPanel(new VRLayout());
        VRPanel menuOtherAndSettingPanel = new VRPanel(new VRLayout());
        VRPanel menuClosePanel = new VRPanel(new VRLayout());
        VRPanel htmlPanel = new VRPanel();
        VRLayout htmlLayout = new VRLayout();
        // 終了ボタンの左側の領域に配置
        ACLabel hiddenLabel = new ACLabel("   ");
        // 2007/10/22 [Masahiko Higuchi] Addition - end
        // 2007/10/22 [Masahiko Higuchi] Replace - begin バージョンアップお知らせ機能対応
        this.add(titleBar, VRLayout.WEST);
        this.add(menus, VRLayout.CENTER);
        // 2007/10/22 [Masahiko Higuchi] Replace - end
        titleBar.setLayout(new BorderLayout());
        titleBar.setBackground(new java.awt.Color(0, 51, 153));
        // 2006/02/03[Tozo Tanaka] : replace begin
//      titleBar.add(title, BorderLayout.NORTH);
        title.setVerticalAlignment(SwingConstants.TOP);
        titleBar.add(title, BorderLayout.CENTER);
        // 2006/02/03[Tozo Tanaka] : replace end
        titleBar.add(versionPanel, BorderLayout.SOUTH);
        title.setText("");
        title.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/top.jpg")));

        menus.setBackground(Color.white);
        VRLayout menusLayout = new VRLayout();
        menusLayout.setHgap(10);
        // 2007/10/22 [Masahiko Higuchi] Replace - begin バージョンアップお知らせ機能対応
        menusLayout.setVgap(2);
        // 2007/10/22 [Masahiko Higuchi] Replace - end
        menus.setLayout(menusLayout);
		// 2007/10/22 [Masahiko Higuchi] Replace - begin バージョンアップお知らせ機能対応
        htmlPanel.setLayout(htmlLayout);
        menuIkenshoShijishoPanel.add(ikenshoShijisho, VRLayout.CLIENT);
        menuSeikyuushoAndBasicPanel.add(seikyuusho, VRLayout.CLIENT);
        menuSeikyuushoAndBasicPanel.add(basic, VRLayout.CLIENT);
        menuOtherAndSettingPanel.add(other, VRLayout.CLIENT);
        menuOtherAndSettingPanel.add(setting, VRLayout.CLIENT);
        menuClosePanel.add(hiddenLabel, VRLayout.CLIENT);
        menuClosePanel.add(closeBtnPnl, VRLayout.CLIENT);
        htmlPanel.add(editor);
        // 背景色は白に設定
        menuIkenshoShijishoPanel.setBackground(Color.white);
        menuSeikyuushoAndBasicPanel.setBackground(Color.white);
        menuOtherAndSettingPanel.setBackground(Color.white);
        menuClosePanel.setBackground(Color.white);
        editor.setBackground(Color.white);
        // メニューにボタンの配置
        menus.add(menuIkenshoShijishoPanel,VRLayout.NORTH);
        menus.add(menuSeikyuushoAndBasicPanel,VRLayout.NORTH);
        menus.add(menuOtherAndSettingPanel,VRLayout.NORTH);
        menus.add(menuClosePanel,VRLayout.NORTH);
        menus.add(editor,VRLayout.CLIENT);
        // 2007/10/22 [Masahiko Higuchi] Replace - end
        // 意見書・指示書
        ikenshoShijisho.setToolTipText("「主治医意見書・医師意見書・訪問看護指示書」の作成および編集を行います。");
        ikenshoShijisho.setMnemonic('K');
        // ikenshoShijisho.setBackground(new java.awt.Color(102, 102, 255));
        // ikenshoShijisho.setFont(new java.awt.Font("Dialog",
        // java.awt.Font.PLAIN, 18));
        ikenshoShijisho.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource(
                        "jp/or/med/orca/ikensho/images/menu/menuicon_01.png")));

        // 請求書
        seikyuusho.setToolTipText("「請求書」の作成および編集を行います。");
        seikyuusho.setMnemonic('V');
        // seikyuusho.setBackground(new java.awt.Color(102, 102, 255));
        // seikyuusho.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
        // 18));
        seikyuusho.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource(
                        "jp/or/med/orca/ikensho/images/menu/menuicon_02.png")));

        // 基本データ登録
        basic.setToolTipText("基礎データ登録の画面へ移ります。");
        basic.setMnemonic('B');
        // basic.setBackground(new java.awt.Color(102, 102, 255));
        // basic.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        basic.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_03.png")));

        // その他機能
        other.setToolTipText("その他の機能の画面へ移ります。");
        other.setMnemonic('O');
        // other.setBackground(new java.awt.Color(102, 102, 255));
        // other.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        other.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_04.png")));

        // 設定
        setting.setToolTipText("設定の画面へ移ります。");
        setting.setMnemonic('S');
        // setting.setBackground(new java.awt.Color(102, 102, 255));
        // setting.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
        // 18));
        setting.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_05.png")));

        // 閉じる
        closeBtnPnl.setLayout(new BorderLayout());
        closeBtnPnl.setOpaque(false);
		// 2007/10/22 [Masahiko Higuchi] Replace - begin バージョンアップお知らせ機能対応
        closeBtnPnl.add(close, BorderLayout.CENTER);
		// 2007/10/22 [Masahiko Higuchi] Replace - end
        close.setToolTipText("プログラムを終了します。");
        close.setMnemonic('E');
        // close.setBackground(new java.awt.Color(102, 102, 255));
        // close.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        close.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_17.png")));

        setting.addKeyListener(cmd);
    }

    private void event() {
        // 意見書・指示書
        ikenshoShijisho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB接続チェック
                    if (!isAvailableDB(false)) {
                        return;
                    }

                    ACAffairInfo affair = new ACAffairInfo(
                            IkenshoPatientList.class.getName(), null, "患者情報一覧");
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // 請求書
        seikyuusho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB接続チェック
                    if (!isAvailableDB(false)) {
                        return;
                    }

                    ACAffairInfo affair = new ACAffairInfo(
                            IkenshoSeikyuIchiran.class.getName(), null,
                            "請求対象意見書一覧");
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // 基礎データ登録
        basic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB接続チェック
                    if (!isAvailableDB(false)) {
                        return;
                    }

                    IkenshoSubMenu dlg = new IkenshoSubMenu(
                            IkenshoSubMenu.MAIN_BASIC);
                    dlg.setVisible(true);
                    // dlg.show();
                    int pushedButton = dlg.getPushedButtion();

                    ACAffairInfo affair = null;
                    switch (pushedButton) {
                    // 連携医情報一覧
                    case IkenshoSubMenu.SUB_BASIC_DOCTOR:
                        affair = new ACAffairInfo(
                                IkenshoRenkeiIJyouhouIchiran.class.getName(),
                                null, "連携医情報一覧");
                        break;

                    // 訪問看護ステーション情報一覧
                    case IkenshoSubMenu.SUB_BASIC_STATION:
                        affair = new ACAffairInfo(
                                IkenshoHoumonKangoStationJouhouIchiran.class
                                        .getName(), null, "訪問看護ステーション情報一覧");
                        break;

                    // 保険者一覧
                    case IkenshoSubMenu.SUB_BASIC_INSURER:
                        affair = new ACAffairInfo(IkenshoHokenshaIchiran.class
                                .getName(), null, "保険者一覧");
                        break;

                    // 医療機関情報一覧
                    case IkenshoSubMenu.SUB_BASIC_JIGYOUSHA:
                        affair = new ACAffairInfo(
                                IkenshoIryouKikanJouhouIchiran.class.getName(),
                                null, "医療機関情報一覧");
                        break;

                    // 特記事項編集
                    case IkenshoSubMenu.SUB_BASIC_SPECIAL:
                        affair = new ACAffairInfo(IkenshoTeikeibunList.class
                                .getName(), null, "特記事項一覧");
                        break;

                    // 閉じる
                    case IkenshoSubMenu.SUB_CLOSED:
                        return;

                    // その他
                    default:
                        return;
                    }
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // その他の機能登録
        other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB接続チェック
                    if (!isAvailableDB(false)) {
                        return;
                    }

                    IkenshoSubMenu dlg = new IkenshoSubMenu(
                            IkenshoSubMenu.MAIN_OTHER);
                    dlg.setVisible(true);
                    // dlg.show();
                    int pushedButton = dlg.getPushedButtion();

                    ACAffairInfo affair = null;
                    IkenshoOtherBackupRestore bkupRestore;
                    switch (pushedButton) {
                    // バックアップ
                    case IkenshoSubMenu.SUB_OTHER_BACKUP:
                        bkupRestore = new IkenshoOtherBackupRestore(
                                IkenshoCommon.getProperity("DBConfig/Server"),
                                IkenshoCommon.getProperity("DBConfig/Path"));
                        bkupRestore.doBackUp();
                        return;

                    // リストア
                    case IkenshoSubMenu.SUB_OTHER_RESTORE:
                        bkupRestore = new IkenshoOtherBackupRestore(
                                IkenshoCommon.getProperity("DBConfig/Server"),
                                IkenshoCommon.getProperity("DBConfig/Path"));
                        if (bkupRestore.doRestore()) {
                            // DBを変更したらver情報を取り直す
                            checkDBVersion();
                        }
                        return;

                    // CSV出力
                    case IkenshoSubMenu.SUB_OTHER_CSV_OUTPUT:
                        affair = new ACAffairInfo(IkenshoOtherCSVOutput.class
                                .getName(), null, "「主治医意見書・医師意見書」CSVファイル出力");
                        break;
                    // 日医標準レセプトソフト連携
                    case IkenshoSubMenu.SUB_OTHER_RECEIPT_ACCESS:
                        //2006/02/11[Tozo Tanaka] : replace begin 
//                        affair = new ACAffairInfo(
//                                IkenshotReceiptSoftAccess.class.getName(),
//                                null, "日医標準レセプトソフト連係");
                        affair = new ACAffairInfo(
                                IkenshoReceiptSoftAccess.class.getName(),
                                null, "日医標準レセプトソフト連携");
                        //2006/02/11[Tozo Tanaka] : replace end 
                        break;

                    // 閉じる
                    case IkenshoSubMenu.SUB_CLOSED:
                        return;

                    // その他
                    default:
                        return;
                    }
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // 設定
        setting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    IkenshoSubMenu dlg = new IkenshoSubMenu(
                            IkenshoSubMenu.MAIN_SETTING);
                    dlg.setVisible(true);
                    // dlg.show();
                    int pushedButton = dlg.getPushedButtion();

                    switch (pushedButton) {
                    // データベース設定
                    case IkenshoSubMenu.SUB_SETTING_DB:
                        IkenshoSettingDB settingDB = new IkenshoSettingDB();
                        if (settingDB.showModified()) {
                            // DBを変更したらver情報を取り直す
                            checkDBVersion();
                        }
                        return;

                    // 消費税率の設定
                    case IkenshoSubMenu.SUB_SETTING_TAX:

                        // DB接続チェック
                        if (!isAvailableDB(false)) {
                            return;
                        }
                        IkenshoTaxSetting taxDlg = new IkenshoTaxSetting();
                        taxDlg.setVisible(true);
                        // taxDlg.show();
                        return;

                    // PDF設定
                    case IkenshoSubMenu.SUB_SETTING_PDF:
                        IkenshoSettingPDF iasPdf = new IkenshoSettingPDF();
                        iasPdf.setVisible(true);
                        // iasPdf.show();
                        return;
                    //2009/01/06 [Tozo Tanaka] Add - begin
                        // その他の設定
                    case IkenshoSubMenu.SUB_SETTING_OTHER:
                        IkenshoSettingOther iasOther = new IkenshoSettingOther();
                        iasOther.setVisible(true);
                        return;
                    //2009/01/06 [Tozo Tanaka] Add - end

                    // 閉じる
                    case IkenshoSubMenu.SUB_CLOSED:
                        return;

                    // その他
                    default:
                        return;
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // システムの終了
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    System.exit(0);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // コマンド
        setting.addKeyListener(new KeyListener() {
            public void keyTyped(KeyEvent e) {
            }

            public void keyPressed(KeyEvent e) {
                checkHiddenCmd(e.getKeyCode());
            }

            public void keyReleased(KeyEvent e) {
                checkHiddenCmd(e.getKeyCode());
            }
        });

        // バージョン情報パネルのマウス挙動
        versionPanel.addMouseListener(new MouseAdapter() {
            private final Color BRIGHT_OUTER = new Color(52, 101, 204);
            private final Color BRIGHT_INNER = new Color(52, 101, 204);
            private final Color DARK_OUTER = new Color(0, 30, 100);
            private final Color DARK_INNER = new Color(0, 30, 100);
            private boolean pressed = false;

            public void mousePressed(MouseEvent e) {
                pressed = true;
                if (versionPanel.getBorder() != null) {
                    versionPanel.setBorder(BorderFactory.createBevelBorder(
                            BevelBorder.LOWERED, BRIGHT_OUTER, BRIGHT_INNER,
                            DARK_OUTER, DARK_INNER));
                }
            }

            public void mouseReleased(MouseEvent e) {
                pressed = false;
                if (versionPanel.getBorder() != null) {
                    versionPanel.setBorder(BorderFactory.createBevelBorder(
                            BevelBorder.RAISED, BRIGHT_OUTER, BRIGHT_INNER,
                            DARK_OUTER, DARK_INNER));
                    new IkenshoVersionDialog().show(systemVersionText);
                    // PreferredSizeを元に戻す
                    versionPanel.setPreferredSize(null);
                }
            }

            public void mouseEntered(MouseEvent e) {
                int style;
                if (pressed) {
                    style = BevelBorder.LOWERED;
                } else {
                    style = BevelBorder.RAISED;
                }
                // Look&FeelによってはBorder指定によってPreferredSizeが変わってしまう
                // （外側に拡張される）ため、Border指定前のPreferredSizeを維持させる。
                versionPanel.setPreferredSize(versionPanel.getPreferredSize());
                versionPanel.setBorder(BorderFactory.createBevelBorder(style,
                        BRIGHT_OUTER, BRIGHT_INNER, DARK_OUTER, DARK_INNER));
            }

            public void mouseExited(MouseEvent e) {
                versionPanel.setBorder(null);
            }
        });
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // 前回印刷分のPDFファイルを削除する
        File pdfDirectory = new File(IkenshoConstants.PRINT_PDF_DIRECTORY);
        if (pdfDirectory.exists()) {
            File pdfFileAll = new File(IkenshoConstants.PRINT_PDF_DIRECTORY,
                    ".");
            File[] pdfFiles = pdfFileAll.listFiles();
            for (int i = 0; i < pdfFiles.length; i++) {
                pdfFiles[i].delete();
            }
        }

        // 2008/02/08[Masahiko Higuchi] : add start version 3.0.5 Mac JVM1.5対応
        //3回までは、FrameProcesserを設定しなおす
        for (int i = 0; (ACFrame.getInstance().getFrameEventProcesser() == null)
				&& (i < 3); i++) {
			ACFrame.getInstance().setFrameEventProcesser(
					new IkenshoFrameEventProcesser());
		}
        // 2008/02/08[Masahiko Higuchi] : add end
        
        // DB接続テスト
        checkDBVersion();

    }

    /**
     * DBとの接続チェックおよびバージョン情報の再取得を行ないます。
     * 
     * @throws Exception 処理例外
     * @return バージョン情報まで正しく取得できたか
     */
    protected boolean checkDBVersion() throws Exception {
        
        // 2006/02/08[Shin Fujihara] : add begin
        osVersion.setText("OS : " + System.getProperty("os.name", "unknown"));
        String patch = System.getProperty("sun.os.patch.level", "");
        if(!patch.equalsIgnoreCase("unknown")){
            spVersion.setText("     " + patch);
        }
        String vender = System.getProperty("java.vendor", "unknown") + System.getProperty("java.version", "-");
        
        if(vender.length() < 16){
            javaVersion.setText("VM : " + vender);
        } else {
            javaVersion.setText("VM : " + vender.substring(0,15));
            javaVersion2.setText(vender.substring(15));
        }
        // 2006/02/08[Shin Fujihara] : add end

        // DB接続テスト
        if (isAvailableDB(true)) {
            // 成功した場合はver取得
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            // 2006/02/08[Shin Fujihara] : add begin
            String dbVersion = dbm.getDBMSVersion();
            if(dbVersion.length() < 11){
                firebirfVersion.setText("Firebird : " + dbVersion);
            } else {
                firebirfVersion.setText("Firebird : " + dbVersion.substring(0,10));
                firebirfVersion2.setText(dbVersion.substring(10));
            }
            
            //モジュールの対象バージョンに合わせてデータベースを整形する。
            try{
                new ACVersionAdjuster().adjustment(dbm,
                    "version/update.xml",
                    new IkenshoVersionAdjustmentListener(dbm));
            }catch(Exception ex){
                VRLogger.info(ex);
            }finally{
                ACSplashChaine.getInstance().closeAll();
            }            
            
            // 2006/02/08[Shin Fujihara] : end begin
            VRList result = dbm.executeQuery("SELECT * FROM IKENSYO_VERSION");
            // 2006/02/06[Tozo Tanaka] : replace begin
            // if (result.size() > 0) {
            if ((result != null) && (result.size() > 0)) {
                // 2006/02/06[Tozo Tanaka] : replace end
                
                VRMap ver = (VRMap) result.getData();
                String data = String.valueOf(VRBindPathParser.get(
                        "DATA_VERSION", ver));
                String schema = String.valueOf(VRBindPathParser.get(
                        "SCHEMA_VERSION", ver));
                String sys;
                try {
                    sys = ACFrame.getInstance().getProperty("Version/no");
                } catch (Exception ex) {
                    ACMessageBox
                            .showExclamation("システムバージョンの取得に失敗しました。環境設定ファイルを確認してください。");
                    systemVersion.setText(" version情報 : 取得失敗");
                    systemVersionText = "不明";
                    return false;
                }
                systemVersionText = sys;
                systemVersion.setText("システムversion : " + sys);
                dataVersion.setText("データversion : " + data);
                schemaVersion.setText("スキーマversion : " + schema);

                ACFrame.getInstance().setTitle("「医見書Ver" + sys + "」メインメニュー");

                return true;
            }
        }
        systemVersion.setText(" version情報 : 取得失敗");
        systemVersionText = "不明";
        return false;
    }

    public ACAffairButtonBar getButtonBar() {
        return null;
    }

    public Component getFirstFocusComponent() {
        return ikenshoShijisho;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        return false;
    }

    public boolean canClose() throws Exception {
        return true;
    }

    /**
     * 接続テスト
     * 
     * @param first 初回起動時であるか
     * @return 接続に成功したか
     */
    private boolean isAvailableDB(boolean first) {
        try {
            // 接続テスト

            if (!VRCharType.ONLY_HALF_CHAR.isMatch(ACFrame.getInstance()
                    .getProperty("DBConfig/Path"))) {
                // 許可文字コード
                ACMessageBox.showExclamation("データベースの保存場所には日本語を使用できません。"
                        + IkenshoConstants.LINE_SEPARATOR
                        + "データベースを移動するか、別のデータベースを指定してください。");
                if (first) {
                    IkenshoSettingDB dlg = new IkenshoSettingDB();
                    dlg.setVisible(true);
                    return isAvailableDB(false);
                }
                return false;
            }

            // 通信テスト
            IkenshoFirebirdDBManager fdbm = new IkenshoFirebirdDBManager();
            if (fdbm.isAvailableDBConnection()) {
                fdbm.releaseAll();
                return true;
            } else {
                if (first) {
                    ACMessageBox.showExclamation("データベースファイルが見つかりません。");
                    IkenshoSettingDB dlg = new IkenshoSettingDB();
                    dlg.setVisible(true);
                    return isAvailableDB(false);
                } else {
                    ACMessageBox.showExclamation("データベースファイルが見つかりません。"
                            + IkenshoConstants.LINE_SEPARATOR
                            + "データベースファイルを指定し直してください。");
                }
                return false;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return false;
    }

    private void checkHiddenCmd(int keyCode) {
        if (cmd.checkHiddenCmd(keyCode)) {
            // DB接続チェック
            if (!isAvailableDB(false)) {
                return;
            }

            IkenshoQueryAnalizer dlg = new IkenshoQueryAnalizer();
            dlg.setVisible(true);
            // dlg.show();
        }
    }
    
    
    /**
     * アクセスするURLを返します。
     * 
     * @return アクセスするURL
     */
    protected String getVersionUpInfoURL() {
        try {
            File f = new File(ACFrame.getInstance().getExeFolderPath(),
                    "versionUpInfo.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream(f)));
            if (br != null) {
                String txt = br.readLine();
                if (!ACTextUtilities.isNullText(txt)) {
                    return txt;
                }
                br.close();
            }
        } catch (Exception e) {
        }
        return "";
    }
    
    /**
     * 
     * IkenshoHtmlPageReaderです。
     * <p>
     * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     * @see Runnable
     */
    class IkenshoHtmlPageReader implements Runnable{
        Thread th;
        
        /**
         * HTMLページ読み込みのメイン処理です。
         */
        public void run() {
            if(th != null){
                // 最初は非表示
                editor.setVisible(false);
                URL url;
                try {
                    // OS種類を取得
                    String osName = System.getProperty("os.name");
                    // Macの場合は非適用
                    if ((osName != null) && (osName.indexOf("Mac") < 0)) {
                        // プロキシサーバ名称を設定する
                        System.setProperty("http.proxyHost", PropertyUtil
                                .getProperty("http.proxyHost"));
                        // プロキシのポート番号を設定する。
                        System.setProperty("http.proxyPort", PropertyUtil
                                .getProperty("http.proxyPort"));
                        // プロキシを使わないサーバの設定
                        System.setProperty("http.nonProxyHosts", PropertyUtil
                                .getProperty("http.nonProxyHosts"));
                    }
                    // URLを生成
                    url = new URL(getVersionUpInfoURL());
                    // 先にVisibleを切り替える
                    editor.setVisible(true);
                    // ページの表示
                    editor.showHtmlPage(url);
                    // Enable制御
                    editor.setEnabled(true);
                    // スレッドの破棄
                    th = null;
                    editor.revalidate();
                } catch (Exception e) {
                    // 読み込み失敗時は非表示
                    editor.setVisible(false);
                }
            }
            
        }
        
        /**
         * 新規のスレッドを生成して処理を実行します。
         */
        public void start(){
            th = new Thread(this);
            th.start();
        }
        
    }
    
}