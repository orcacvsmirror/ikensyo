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

// VRからACに変更のため
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

            //スナップショット撮影
            IkenshoSnapshot.getInstance().snapshot();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public IkenshoSettingDB() {
        this(ACFrame.getInstance(), "データベースの設定", true);
    }

    /**
     * ダイアログを表示し、設定を変更したかを返します。
     * @return 設定を変更したか
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
// VR からACに変更した為以下コメント        
//        contentPane.add(info0, VRLayout.NORTH);
//        contentPane.add(info1, VRLayout.NORTH);
//        contentPane.add(info2, VRLayout.NORTH);
//        contentPane.add(info3, VRLayout.NORTH);
//        contentPane.add(info4, VRLayout.NORTH);
//        contentPane.add(info5, VRLayout.NORTH);
        contentPane.add(btnGrp, VRLayout.SOUTH);

        //サーバ選択
        VRLayout serverPnlLayout = new VRLayout();
        serverPnlLayout.setAutoWrap(false);
        serverPnl.add(serverTypeContainer, VRLayout.FLOW_INSETLINE);
        serverPnl.add(serverNameContainer, VRLayout.FLOW_RETURN);

        serverTypeContainer.setText("サーバー");
        serverTypeContainer.add(serverType, null);
        VRLayout serverTypeLayout = new VRLayout();
        serverTypeLayout.setAutoWrap(false);
        serverType.setLayout(serverTypeLayout);
        serverType.setModel(new VRListModelAdapter(
            new VRArrayList(Arrays.asList(new String[] {
                                          "ローカル",
                                          "他のコンピューター"}))));
        serverType.setBindPath("SERVER_TYPE");

       //サーバ名
       serverNameContainer.setText("IP");
       serverNameContainer.add(serverName, null);
       serverName.setColumns(20);
       serverName.setMaxLength(30);
       serverName.setIMEMode(InputSubset.LATIN_DIGITS);
       serverName.setCharType(VRCharType.ONLY_ASCII);
       serverName.setBindPath("SERVER_NAME");

       //ファイル名
       VRLayout filePnlLayout = new VRLayout();
       filePnlLayout.setAutoWrap(false);
       filePnl.setLayout(filePnlLayout);
       filePnl.add(DBFileNameContainer, VRLayout.FLOW_INSETLINE);
       filePnl.add(DBFileNameChooseBbn, VRLayout.FLOW_RETURN);

       DBFileNameContainer.setText("ファイルの場所");
       DBFileNameContainer.add(DBFileName, null);
       DBFileName.setColumns(38);
       DBFileName.setMaxLength(1000);
       DBFileName.setIMEMode(InputSubset.LATIN_DIGITS);
       DBFileName.setBindPath("FILE_NAME");
       DBFileNameChooseBbn.setText("参照(L)");
       DBFileNameChooseBbn.setMnemonic('L');

       //説明文
       // Add Kazumi Hirose (Kazumix) 2006/02/27
       infomation.setAutoWrap(true);
       	String infoStr = "データベースの設定の前に以下の事項を確認してください。"
       		+ IkenshoConstants.LINE_SEPARATOR + "　1.他のコンピューターを選択した場合は、他のコンピューターでのデータベースのファイルの場所を手入力してください。"
       		+ IkenshoConstants.LINE_SEPARATOR + "　  なお、接続先のコンピューターに Firebird をインストールする必要があります。"
       		+ IkenshoConstants.LINE_SEPARATOR + "　  ※NAS/SAN等の外部ストレージは使用できません。"
       		+ IkenshoConstants.LINE_SEPARATOR + "　"
       		+ IkenshoConstants.LINE_SEPARATOR + "　2.ファイルの場所には半角英数字のみで日本語文字を含めないでください。" 
       		+ IkenshoConstants.LINE_SEPARATOR + "　  含む場合はインストール先を変更するか、データベースを日本語を含まない場所へ移動してください。"
            + IkenshoConstants.LINE_SEPARATOR + "　  例○ → C:\\Ikensyo\\IKENSYO.FDB"
            + IkenshoConstants.LINE_SEPARATOR + "　  　× → C:\\医見書\\IKENSYO.FDB"
       		+ IkenshoConstants.LINE_SEPARATOR + "　"
       		+ IkenshoConstants.LINE_SEPARATOR + "　3.Windowsにおいて接続先のコンピューター名が日本語の場合、正常に接続できません。" 
       		+ IkenshoConstants.LINE_SEPARATOR + "　  [コントロールパネル]-[システム]からコンピュータ名を変更してください。"
       		;
       	infomation.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
       	infomation.setText(infoStr);
       
// 上記修正の為
//       info0.setText("　データベースの設定の前に以下の事項を確認してください。");
//       info1.setText("　1.他のコンピューターを選択した場合は、他のコンピューターでのデータベースのファイルの場所を");
//       info2.setText("　手入力してください。なお、接続先のコンピューターに Firebird をインストールする必要が");
//       info3.setText("　あります。　※NAS/SAN等の外部ストレージは使用できません。");
//       info4.setText("　2.ファイルパスに日本語は含めないでください、含む場合はFDBファイルを移動してください。");
//       info5.setText("　3.日本語コンピューター名の場合には接続できません。変更して再度設定してください。");
//       info0.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info2.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info3.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info4.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
//       info5.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
       
       //操作ボタン
       btnGrp.add(submit, null);
       btnGrp.add(cancel, null);
       submit.setText("設定(S)");
       submit.setMnemonic('S');
       cancel.setText("キャンセル(C)");
       cancel.setMnemonic('C');
    }

    private void init() throws Exception {
        //ウィンドウサイズ
    	// [ID:0000729][Masahiko.Higuchi]del - begin 2012年度対応
        //setSize(new Dimension(680, 350));
    	// [ID:0000729][Masahiko.Higuchi]del - end
    	// [ID:0000729][Masahiko.Higuchi]add - begin 2012年度対応
    	//setSize(new Dimension(700, 350));
    	// [ID:0000729][Masahiko.Higuchi]add - end
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

       //設定をロード
       loadSetting();

       //スナップショット撮影対象を設定
       IkenshoSnapshot.getInstance().setRootContainer(contentPane);
    }

    private void event() throws Exception {
        //サーバ種類
        serverType.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setButtonEnabled();
            }
        });

        //参照
        DBFileNameChooseBbn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                doSelectFile();
            }
        });

        //設定
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canSubmit()) { //入力チェック
                        if (writeDBSetting()) { //XML更新
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

        //キャンセル
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
     * 登録処理が可能かどうかをチェックします。
     * @return true:OK, false:NG
     */
    private boolean canSubmit() throws Exception {
        //DBファイル名未入力
        if (serverType.getSelectedIndex() == 1) {
            if (IkenshoCommon.isNullText(serverName.getText())) {
                serverName.requestFocus();
                ACMessageBox.showExclamation("IPが未入力です。");
                return false;
            }
        }

        //ファイル名未入力
        if (IkenshoCommon.isNullText(DBFileName.getText())) {
            DBFileName.requestFocus();
            ACMessageBox.showExclamation("ファイル名が未入力です。");
            return false;
        }

        //許可文字コード
        if(!VRCharType.ONLY_HALF_CHAR.isMatch(DBFileName.getText())){
          DBFileName.requestFocus();
          ACMessageBox.showExclamation("データベースの保存場所には日本語を使用できません。" +
                                       IkenshoConstants.LINE_SEPARATOR +
                                       "データベースを移動するか、別のデータベースを指定してください。");
          return false;
        }

        //DB接続テスト
        if (!isAvailablePath()) {
            DBFileName.requestFocus();
            //2006/02/03[Tozo Tanaka] : replace begin 
            //            ACMessageBox.showExclamation("データベースをオープンできません。データベースの設定を見直してください。");
            ACMessageBox.showExclamation("データベースをオープンできません。データベースの設定を見直してください。"
                    + IkenshoConstants.LINE_SEPARATOR + "※例として、以下の項目をご確認ください。"
                    + IkenshoConstants.LINE_SEPARATOR
                    + IkenshoConstants.LINE_SEPARATOR + "　【動作環境】"
                    + IkenshoConstants.LINE_SEPARATOR + "　　・OSは「Windows2000(SP2以降)」「WindowsXP」「Mac OS X v10.3.4～10.4.4」のいずれかですか？"
                    + IkenshoConstants.LINE_SEPARATOR + "　　・Java実行環境(JRE)は「v1.5/1.4」、Macでは「Java 1.4.2 Update 2」を適用済みですか？"
                    + IkenshoConstants.LINE_SEPARATOR + "　　・医見書のインストール時にFirebird(Ver1.5)をインストールしていますか？"
                    + IkenshoConstants.LINE_SEPARATOR
                    + IkenshoConstants.LINE_SEPARATOR + "　【セキュリティソフトによる制限】"
                    + IkenshoConstants.LINE_SEPARATOR + "　　・ウィルス対策ソフトや、セキュリティ関係のソフトウェアをインストールしていますか？"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　→当該ソフトの設定を変更し、ポート3050での通信を許可するか、Firebird に実行権限を付与してください。"
                    + IkenshoConstants.LINE_SEPARATOR + "　　・ Windows XPの場合、「ファイアウォール」機能を使用されていますか？"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　→ファイアウォールの設定を変更し、ブロックしない例外プログラムに Firebird を追加してください。"
                    + IkenshoConstants.LINE_SEPARATOR + "　　※Windowsで標準設定のまま Firebird をインストールした場合、"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　C:\\Program Files\\Firebird\\bin\\fbguard.exe と"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　C:\\Program Files\\Firebird\\bin\\fbserver.exe の"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　実行を許可する設定にしてください。"
                    + IkenshoConstants.LINE_SEPARATOR + "　　※各ソフトウェアの設定方法の詳細につきましては、ソフトウェアの"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　メーカーにお問い合わせください。"
                    //2006/02/10[Tozo Tanaka] : add begin
                    + IkenshoConstants.LINE_SEPARATOR
                    + IkenshoConstants.LINE_SEPARATOR + "　【ネットワークの制限】"
                    + IkenshoConstants.LINE_SEPARATOR + "　　・Windowsの場合、コンピュータ名に半角英数字以外の日本語文字を使用していますか？"
                    + IkenshoConstants.LINE_SEPARATOR + "　　　→接続先のコンピュータ名を半角英数字で構成してください。（マイ コンピュータのプロパティを参照）"
                    //2006/02/10[Tozo Tanaka] : add end
            );
            //2006/02/03[Tozo Tanaka] : replace end
            return false;
        }

        return true;
    }

    /**
     * 閉じてよいかどうかを判定します。
     * @return boolean
     */
    private boolean canClose() {
        try {
            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show(
                    "変更内容が破棄されます。\nよろしいですか？",
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
     * ボタンのEnabledを設定します。
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
     * 設定ファイルから値を取得します。
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
     * 設定をロードします。
     * @throws Exception
     */
    private void loadSetting() throws Exception {
        //サーバの種類
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

        //ファイルパス
        DBFileName.setText(getProperty("DBConfig/Path").toLowerCase());
    }

    /**
     * ローカル内のDBファイルの参照を取得します。
     */
    private void doSelectFile() {
        //ファイル指定
        ACFileChooser fileChooser = new ACFileChooser();
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(new String[] {"fdb"});
        filter.setDescription("データベースファイル(*.fdb)");

        File file = null;
        File fileOld = new File(getProperty("DBConfig/Path"));
        boolean loopFlg = false;
        do {
            loopFlg = false;

            //ファイル選択
            file = fileChooser.showOpenDialog(
                fileOld.getParent(), fileOld.getName(),
                "使用するデータベースファイルを指定して下さい。",
                filter);

            //キャンセル時は中断
            if (file == null) {
                return;
            }

            //拡張子を補完
            file = new File(file.getParent() + separator +
                            ( (ACFileFilter) fileChooser.getFileFilter()).
                            getFilePathWithExtension(file.getName()));

            //ファイル存在チェック
            if (!file.exists()) {
                ACMessageBox.showExclamation("ファイル名が不正です。");
                loopFlg = true;
                continue;
            }
        }while (loopFlg);

        DBFileName.setText(file.getPath());
    }

    /**
     * 利用可能なパスかどうか検証します。
     * @return boolean
     */
    private boolean isAvailablePath() throws Exception {
        IkenshoFirebirdDBManager fdbm = new IkenshoFirebirdDBManager();
        try {
            //新パラメータ取得
            String server;
            if (serverType.getSelectedIndex() == 0) {
                server = "localhost";
            }
            else {
                server = serverName.getText();
            }
            String path = DBFileName.getText();

            //接続テスト
            fdbm.releaseAll(); //プールしているコネクションを解放
            fdbm = new IkenshoFirebirdDBManager(
                server,
                Integer.parseInt(getProperty("DBConfig/Port")),
                getProperty("DBConfig/UserName"),
                getProperty("DBConfig/Password"),
                path,
                Integer.parseInt(getProperty("DBConfig/LoginTimeOut")),
                Integer.parseInt(getProperty("DBConfig/MaxPoolSize")));
            if (fdbm.isAvailableDBConnection()) {
                fdbm.releaseAll(); //後片付け
                return true;
            }
            else {
                fdbm.releaseAll(); //後片付け
                return false;
            }
        }
        catch (Exception ex) {
            fdbm.releaseAll(); //後片付け
            return false;
        }
    }

    private void restoreToOriginalConnection() {

    }

    /**
     * DB設定ファイル書き込みを行います。
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
            //例外処理（tryせずに呼び出し元メソッドにthrowsしてもよい）
            IkenshoCommon.showExceptionMessage(ex);
        }

        return true;
    }
}
