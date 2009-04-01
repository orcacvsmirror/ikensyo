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
                            new VRHashMap(), "�u�㌩��Ver2.5�v���C�����j���["));
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
            "�u�厡��ӌ����E��t�ӌ����E�K��Ō�w�����v"+ACConstants.LINE_SEPARATOR+"�@�쐬�^�ҏW(K)");
    private ACMainMenuButton seikyuusho = new ACMainMenuButton("�u�������v���s(V)");
    private ACMainMenuButton basic = new ACMainMenuButton("��b�f�[�^�o�^(B)");
    private ACMainMenuButton other = new ACMainMenuButton("���̑��̋@�\(O)");
    private ACMainMenuButton setting = new ACMainMenuButton("�ݒ�(S)");
    private ACMainMenuButton close = new ACMainMenuButton("�V�X�e���̏I��(E)");
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
    // 2007/10/22 [Masahiko Higuchi] Addition - begin �o�[�W�����A�b�v���m�点�@�\�Ή�
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
        //Java,OS,Firebird�̃o�[�W�����\�L��ǉ�
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
        
        // 2007/10/22 [Masahiko Higuchi] Addition - begin �o�[�W�����A�b�v���m�点�@�\�Ή�
        editor = new ACEditorPane();
        try{
            // HTML�Ǎ��݃N���X����
            IkenshoHtmlPageReader reader = new IkenshoHtmlPageReader();
            // �ʃX���b�h�œǍ��݊J�n
            reader.start();
            
        }catch(Exception e){}
        
        VRPanel menuIkenshoShijishoPanel = new VRPanel(new VRLayout());
        VRPanel menuSeikyuushoAndBasicPanel = new VRPanel(new VRLayout());
        VRPanel menuOtherAndSettingPanel = new VRPanel(new VRLayout());
        VRPanel menuClosePanel = new VRPanel(new VRLayout());
        VRPanel htmlPanel = new VRPanel();
        VRLayout htmlLayout = new VRLayout();
        // �I���{�^���̍����̗̈�ɔz�u
        ACLabel hiddenLabel = new ACLabel("   ");
        // 2007/10/22 [Masahiko Higuchi] Addition - end
        // 2007/10/22 [Masahiko Higuchi] Replace - begin �o�[�W�����A�b�v���m�点�@�\�Ή�
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
        // 2007/10/22 [Masahiko Higuchi] Replace - begin �o�[�W�����A�b�v���m�点�@�\�Ή�
        menusLayout.setVgap(2);
        // 2007/10/22 [Masahiko Higuchi] Replace - end
        menus.setLayout(menusLayout);
		// 2007/10/22 [Masahiko Higuchi] Replace - begin �o�[�W�����A�b�v���m�点�@�\�Ή�
        htmlPanel.setLayout(htmlLayout);
        menuIkenshoShijishoPanel.add(ikenshoShijisho, VRLayout.CLIENT);
        menuSeikyuushoAndBasicPanel.add(seikyuusho, VRLayout.CLIENT);
        menuSeikyuushoAndBasicPanel.add(basic, VRLayout.CLIENT);
        menuOtherAndSettingPanel.add(other, VRLayout.CLIENT);
        menuOtherAndSettingPanel.add(setting, VRLayout.CLIENT);
        menuClosePanel.add(hiddenLabel, VRLayout.CLIENT);
        menuClosePanel.add(closeBtnPnl, VRLayout.CLIENT);
        htmlPanel.add(editor);
        // �w�i�F�͔��ɐݒ�
        menuIkenshoShijishoPanel.setBackground(Color.white);
        menuSeikyuushoAndBasicPanel.setBackground(Color.white);
        menuOtherAndSettingPanel.setBackground(Color.white);
        menuClosePanel.setBackground(Color.white);
        editor.setBackground(Color.white);
        // ���j���[�Ƀ{�^���̔z�u
        menus.add(menuIkenshoShijishoPanel,VRLayout.NORTH);
        menus.add(menuSeikyuushoAndBasicPanel,VRLayout.NORTH);
        menus.add(menuOtherAndSettingPanel,VRLayout.NORTH);
        menus.add(menuClosePanel,VRLayout.NORTH);
        menus.add(editor,VRLayout.CLIENT);
        // 2007/10/22 [Masahiko Higuchi] Replace - end
        // �ӌ����E�w����
        ikenshoShijisho.setToolTipText("�u�厡��ӌ����E��t�ӌ����E�K��Ō�w�����v�̍쐬����ѕҏW���s���܂��B");
        ikenshoShijisho.setMnemonic('K');
        // ikenshoShijisho.setBackground(new java.awt.Color(102, 102, 255));
        // ikenshoShijisho.setFont(new java.awt.Font("Dialog",
        // java.awt.Font.PLAIN, 18));
        ikenshoShijisho.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource(
                        "jp/or/med/orca/ikensho/images/menu/menuicon_01.png")));

        // ������
        seikyuusho.setToolTipText("�u�������v�̍쐬����ѕҏW���s���܂��B");
        seikyuusho.setMnemonic('V');
        // seikyuusho.setBackground(new java.awt.Color(102, 102, 255));
        // seikyuusho.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
        // 18));
        seikyuusho.setIcon(new ImageIcon(getClass().getClassLoader()
                .getResource(
                        "jp/or/med/orca/ikensho/images/menu/menuicon_02.png")));

        // ��{�f�[�^�o�^
        basic.setToolTipText("��b�f�[�^�o�^�̉�ʂֈڂ�܂��B");
        basic.setMnemonic('B');
        // basic.setBackground(new java.awt.Color(102, 102, 255));
        // basic.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        basic.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_03.png")));

        // ���̑��@�\
        other.setToolTipText("���̑��̋@�\�̉�ʂֈڂ�܂��B");
        other.setMnemonic('O');
        // other.setBackground(new java.awt.Color(102, 102, 255));
        // other.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        other.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_04.png")));

        // �ݒ�
        setting.setToolTipText("�ݒ�̉�ʂֈڂ�܂��B");
        setting.setMnemonic('S');
        // setting.setBackground(new java.awt.Color(102, 102, 255));
        // setting.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN,
        // 18));
        setting.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_05.png")));

        // ����
        closeBtnPnl.setLayout(new BorderLayout());
        closeBtnPnl.setOpaque(false);
		// 2007/10/22 [Masahiko Higuchi] Replace - begin �o�[�W�����A�b�v���m�点�@�\�Ή�
        closeBtnPnl.add(close, BorderLayout.CENTER);
		// 2007/10/22 [Masahiko Higuchi] Replace - end
        close.setToolTipText("�v���O�������I�����܂��B");
        close.setMnemonic('E');
        // close.setBackground(new java.awt.Color(102, 102, 255));
        // close.setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
        close.setIcon(new ImageIcon(getClass().getClassLoader().getResource(
                "jp/or/med/orca/ikensho/images/menu/menuicon_17.png")));

        setting.addKeyListener(cmd);
    }

    private void event() {
        // �ӌ����E�w����
        ikenshoShijisho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB�ڑ��`�F�b�N
                    if (!isAvailableDB(false)) {
                        return;
                    }

                    ACAffairInfo affair = new ACAffairInfo(
                            IkenshoPatientList.class.getName(), null, "���ҏ��ꗗ");
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // ������
        seikyuusho.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB�ڑ��`�F�b�N
                    if (!isAvailableDB(false)) {
                        return;
                    }

                    ACAffairInfo affair = new ACAffairInfo(
                            IkenshoSeikyuIchiran.class.getName(), null,
                            "�����Ώۈӌ����ꗗ");
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // ��b�f�[�^�o�^
        basic.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB�ڑ��`�F�b�N
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
                    // �A�g����ꗗ
                    case IkenshoSubMenu.SUB_BASIC_DOCTOR:
                        affair = new ACAffairInfo(
                                IkenshoRenkeiIJyouhouIchiran.class.getName(),
                                null, "�A�g����ꗗ");
                        break;

                    // �K��Ō�X�e�[�V�������ꗗ
                    case IkenshoSubMenu.SUB_BASIC_STATION:
                        affair = new ACAffairInfo(
                                IkenshoHoumonKangoStationJouhouIchiran.class
                                        .getName(), null, "�K��Ō�X�e�[�V�������ꗗ");
                        break;

                    // �ی��҈ꗗ
                    case IkenshoSubMenu.SUB_BASIC_INSURER:
                        affair = new ACAffairInfo(IkenshoHokenshaIchiran.class
                                .getName(), null, "�ی��҈ꗗ");
                        break;

                    // ��Ë@�֏��ꗗ
                    case IkenshoSubMenu.SUB_BASIC_JIGYOUSHA:
                        affair = new ACAffairInfo(
                                IkenshoIryouKikanJouhouIchiran.class.getName(),
                                null, "��Ë@�֏��ꗗ");
                        break;

                    // ���L�����ҏW
                    case IkenshoSubMenu.SUB_BASIC_SPECIAL:
                        affair = new ACAffairInfo(IkenshoTeikeibunList.class
                                .getName(), null, "���L�����ꗗ");
                        break;

                    // ����
                    case IkenshoSubMenu.SUB_CLOSED:
                        return;

                    // ���̑�
                    default:
                        return;
                    }
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // ���̑��̋@�\�o�^
        other.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    // DB�ڑ��`�F�b�N
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
                    // �o�b�N�A�b�v
                    case IkenshoSubMenu.SUB_OTHER_BACKUP:
                        bkupRestore = new IkenshoOtherBackupRestore(
                                IkenshoCommon.getProperity("DBConfig/Server"),
                                IkenshoCommon.getProperity("DBConfig/Path"));
                        bkupRestore.doBackUp();
                        return;

                    // ���X�g�A
                    case IkenshoSubMenu.SUB_OTHER_RESTORE:
                        bkupRestore = new IkenshoOtherBackupRestore(
                                IkenshoCommon.getProperity("DBConfig/Server"),
                                IkenshoCommon.getProperity("DBConfig/Path"));
                        if (bkupRestore.doRestore()) {
                            // DB��ύX������ver������蒼��
                            checkDBVersion();
                        }
                        return;

                    // CSV�o��
                    case IkenshoSubMenu.SUB_OTHER_CSV_OUTPUT:
                        affair = new ACAffairInfo(IkenshoOtherCSVOutput.class
                                .getName(), null, "�u�厡��ӌ����E��t�ӌ����vCSV�t�@�C���o��");
                        break;
                    // ����W�����Z�v�g�\�t�g�A�g
                    case IkenshoSubMenu.SUB_OTHER_RECEIPT_ACCESS:
                        //2006/02/11[Tozo Tanaka] : replace begin 
//                        affair = new ACAffairInfo(
//                                IkenshotReceiptSoftAccess.class.getName(),
//                                null, "����W�����Z�v�g�\�t�g�A�W");
                        affair = new ACAffairInfo(
                                IkenshoReceiptSoftAccess.class.getName(),
                                null, "����W�����Z�v�g�\�t�g�A�g");
                        //2006/02/11[Tozo Tanaka] : replace end 
                        break;

                    // ����
                    case IkenshoSubMenu.SUB_CLOSED:
                        return;

                    // ���̑�
                    default:
                        return;
                    }
                    ACFrame.getInstance().next(affair);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // �ݒ�
        setting.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    IkenshoSubMenu dlg = new IkenshoSubMenu(
                            IkenshoSubMenu.MAIN_SETTING);
                    dlg.setVisible(true);
                    // dlg.show();
                    int pushedButton = dlg.getPushedButtion();

                    switch (pushedButton) {
                    // �f�[�^�x�[�X�ݒ�
                    case IkenshoSubMenu.SUB_SETTING_DB:
                        IkenshoSettingDB settingDB = new IkenshoSettingDB();
                        if (settingDB.showModified()) {
                            // DB��ύX������ver������蒼��
                            checkDBVersion();
                        }
                        return;

                    // ����ŗ��̐ݒ�
                    case IkenshoSubMenu.SUB_SETTING_TAX:

                        // DB�ڑ��`�F�b�N
                        if (!isAvailableDB(false)) {
                            return;
                        }
                        IkenshoTaxSetting taxDlg = new IkenshoTaxSetting();
                        taxDlg.setVisible(true);
                        // taxDlg.show();
                        return;

                    // PDF�ݒ�
                    case IkenshoSubMenu.SUB_SETTING_PDF:
                        IkenshoSettingPDF iasPdf = new IkenshoSettingPDF();
                        iasPdf.setVisible(true);
                        // iasPdf.show();
                        return;
                    //2009/01/06 [Tozo Tanaka] Add - begin
                        // ���̑��̐ݒ�
                    case IkenshoSubMenu.SUB_SETTING_OTHER:
                        IkenshoSettingOther iasOther = new IkenshoSettingOther();
                        iasOther.setVisible(true);
                        return;
                    //2009/01/06 [Tozo Tanaka] Add - end

                    // ����
                    case IkenshoSubMenu.SUB_CLOSED:
                        return;

                    // ���̑�
                    default:
                        return;
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // �V�X�e���̏I��
        close.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    System.exit(0);
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // �R�}���h
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

        // �o�[�W�������p�l���̃}�E�X����
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
                    // PreferredSize�����ɖ߂�
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
                // Look&Feel�ɂ���Ă�Border�w��ɂ����PreferredSize���ς���Ă��܂�
                // �i�O���Ɋg�������j���߁ABorder�w��O��PreferredSize���ێ�������B
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
        // �O��������PDF�t�@�C�����폜����
        File pdfDirectory = new File(IkenshoConstants.PRINT_PDF_DIRECTORY);
        if (pdfDirectory.exists()) {
            File pdfFileAll = new File(IkenshoConstants.PRINT_PDF_DIRECTORY,
                    ".");
            File[] pdfFiles = pdfFileAll.listFiles();
            for (int i = 0; i < pdfFiles.length; i++) {
                pdfFiles[i].delete();
            }
        }

        // 2008/02/08[Masahiko Higuchi] : add start version 3.0.5 Mac JVM1.5�Ή�
        //3��܂ł́AFrameProcesser��ݒ肵�Ȃ���
        for (int i = 0; (ACFrame.getInstance().getFrameEventProcesser() == null)
				&& (i < 3); i++) {
			ACFrame.getInstance().setFrameEventProcesser(
					new IkenshoFrameEventProcesser());
		}
        // 2008/02/08[Masahiko Higuchi] : add end
        
        // DB�ڑ��e�X�g
        checkDBVersion();

    }

    /**
     * DB�Ƃ̐ڑ��`�F�b�N����уo�[�W�������̍Ď擾���s�Ȃ��܂��B
     * 
     * @throws Exception ������O
     * @return �o�[�W�������܂Ő������擾�ł�����
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

        // DB�ڑ��e�X�g
        if (isAvailableDB(true)) {
            // ���������ꍇ��ver�擾
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            // 2006/02/08[Shin Fujihara] : add begin
            String dbVersion = dbm.getDBMSVersion();
            if(dbVersion.length() < 11){
                firebirfVersion.setText("Firebird : " + dbVersion);
            } else {
                firebirfVersion.setText("Firebird : " + dbVersion.substring(0,10));
                firebirfVersion2.setText(dbVersion.substring(10));
            }
            
            //���W���[���̑Ώۃo�[�W�����ɍ��킹�ăf�[�^�x�[�X�𐮌`����B
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
                            .showExclamation("�V�X�e���o�[�W�����̎擾�Ɏ��s���܂����B���ݒ�t�@�C�����m�F���Ă��������B");
                    systemVersion.setText(" version��� : �擾���s");
                    systemVersionText = "�s��";
                    return false;
                }
                systemVersionText = sys;
                systemVersion.setText("�V�X�e��version : " + sys);
                dataVersion.setText("�f�[�^version : " + data);
                schemaVersion.setText("�X�L�[�}version : " + schema);

                ACFrame.getInstance().setTitle("�u�㌩��Ver" + sys + "�v���C�����j���[");

                return true;
            }
        }
        systemVersion.setText(" version��� : �擾���s");
        systemVersionText = "�s��";
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
     * �ڑ��e�X�g
     * 
     * @param first ����N�����ł��邩
     * @return �ڑ��ɐ���������
     */
    private boolean isAvailableDB(boolean first) {
        try {
            // �ڑ��e�X�g

            if (!VRCharType.ONLY_HALF_CHAR.isMatch(ACFrame.getInstance()
                    .getProperty("DBConfig/Path"))) {
                // �������R�[�h
                ACMessageBox.showExclamation("�f�[�^�x�[�X�̕ۑ��ꏊ�ɂ͓��{����g�p�ł��܂���B"
                        + IkenshoConstants.LINE_SEPARATOR
                        + "�f�[�^�x�[�X���ړ����邩�A�ʂ̃f�[�^�x�[�X���w�肵�Ă��������B");
                if (first) {
                    IkenshoSettingDB dlg = new IkenshoSettingDB();
                    dlg.setVisible(true);
                    return isAvailableDB(false);
                }
                return false;
            }

            // �ʐM�e�X�g
            IkenshoFirebirdDBManager fdbm = new IkenshoFirebirdDBManager();
            if (fdbm.isAvailableDBConnection()) {
                fdbm.releaseAll();
                return true;
            } else {
                if (first) {
                    ACMessageBox.showExclamation("�f�[�^�x�[�X�t�@�C����������܂���B");
                    IkenshoSettingDB dlg = new IkenshoSettingDB();
                    dlg.setVisible(true);
                    return isAvailableDB(false);
                } else {
                    ACMessageBox.showExclamation("�f�[�^�x�[�X�t�@�C����������܂���B"
                            + IkenshoConstants.LINE_SEPARATOR
                            + "�f�[�^�x�[�X�t�@�C�����w�肵�����Ă��������B");
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
            // DB�ڑ��`�F�b�N
            if (!isAvailableDB(false)) {
                return;
            }

            IkenshoQueryAnalizer dlg = new IkenshoQueryAnalizer();
            dlg.setVisible(true);
            // dlg.show();
        }
    }
    
    
    /**
     * �A�N�Z�X����URL��Ԃ��܂��B
     * 
     * @return �A�N�Z�X����URL
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
     * IkenshoHtmlPageReader�ł��B
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
         * HTML�y�[�W�ǂݍ��݂̃��C�������ł��B
         */
        public void run() {
            if(th != null){
                // �ŏ��͔�\��
                editor.setVisible(false);
                URL url;
                try {
                    // OS��ނ��擾
                    String osName = System.getProperty("os.name");
                    // Mac�̏ꍇ�͔�K�p
                    if ((osName != null) && (osName.indexOf("Mac") < 0)) {
                        // �v���L�V�T�[�o���̂�ݒ肷��
                        System.setProperty("http.proxyHost", PropertyUtil
                                .getProperty("http.proxyHost"));
                        // �v���L�V�̃|�[�g�ԍ���ݒ肷��B
                        System.setProperty("http.proxyPort", PropertyUtil
                                .getProperty("http.proxyPort"));
                        // �v���L�V���g��Ȃ��T�[�o�̐ݒ�
                        System.setProperty("http.nonProxyHosts", PropertyUtil
                                .getProperty("http.nonProxyHosts"));
                    }
                    // URL�𐶐�
                    url = new URL(getVersionUpInfoURL());
                    // ���Visible��؂�ւ���
                    editor.setVisible(true);
                    // �y�[�W�̕\��
                    editor.showHtmlPage(url);
                    // Enable����
                    editor.setEnabled(true);
                    // �X���b�h�̔j��
                    th = null;
                    editor.revalidate();
                } catch (Exception e) {
                    // �ǂݍ��ݎ��s���͔�\��
                    editor.setVisible(false);
                }
            }
            
        }
        
        /**
         * �V�K�̃X���b�h�𐶐����ď��������s���܂��B
         */
        public void start(){
            th = new Thread(this);
            th.start();
        }
        
    }
    
}