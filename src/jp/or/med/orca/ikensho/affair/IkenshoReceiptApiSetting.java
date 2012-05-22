package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoPassiveCheck;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoReceiptApiSetting extends IkenshoDialog {
    private JPanel contentPane;
    private ACGroupBox apiGrp = new ACGroupBox();
    private VRPanel btnGrp = new VRPanel();
    private ACButton submit = new ACButton();
    private ACButton cancel = new ACButton();

    private ACLabelContainer hostNames;
    private ACTextField hostName;
    private ACTextField userName;
    private ACLabelContainer userNames;
    private ACTextField port;
    private ACLabelContainer ports;
    private ACTextField password;
    private ACLabelContainer passwords;

    // レセプトソフト連携の設定関連の値
    private String receiptSettingIp = "";
    private String receiptSettingApiPort = "";
    private String receiptSettingApiUser = "";
    private String receiptSettingApiPass = "";
    private String receiptSettingUseKanaConvert = "";
    private int receiptSettingReceiptVersion = 1;
    private int receiptSettingAutoUpdateFlag = 0;

    public IkenshoReceiptApiSetting(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
            event();
            doSelect();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public IkenshoReceiptApiSetting() {
        this(ACFrame.getInstance(), "医見書", true);
    }

    public void jbInit() throws Exception {
        contentPane = (JPanel) getContentPane();
        contentPane.setLayout(new BorderLayout());

        contentPane.add(apiGrp, BorderLayout.CENTER);
        contentPane.add(btnGrp, BorderLayout.SOUTH);

        apiGrp.setText("通信設定");
        apiGrp.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        VRLayout taxGrpLayout = new VRLayout();
        taxGrpLayout.setAutoWrap(false);

        apiGrp.add(getHostNames(), VRLayout.FLOW_INSETLINE_RETURN);
        apiGrp.add(getPorts(), VRLayout.FLOW_INSETLINE_RETURN);
        apiGrp.add(getUserNames(), VRLayout.FLOW_INSETLINE_RETURN);
        apiGrp.add(getPasswords(), VRLayout.FLOW_INSETLINE_RETURN);

        btnGrp.setLayout(new VRLayout());
        btnGrp.add(cancel, VRLayout.EAST);
        btnGrp.add(submit, VRLayout.EAST);
        submit.setText("保存(S)");
        submit.setMnemonic('S');
        cancel.setText("閉じる(C)");
        cancel.setMnemonic('C');
    }

    public void init() throws Exception {
        // 画面サイズ
        setSize(new Dimension(350, 200));

        // ウィンドウを中央に配置
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        Dimension frameSize = this.getSize();
        if (frameSize.height > screenSize.height) {
            frameSize.height = screenSize.height;
        }
        if (frameSize.width > screenSize.width) {
            frameSize.width = screenSize.width;
        }
        this.setLocation((screenSize.width - frameSize.width) / 2,
                (screenSize.height - frameSize.height) / 2);

        // プロパティから値を読みつつ初期値を設定
        setConnectSetting();

        // スナップショット撮影対象を設定
        IkenshoSnapshot.getInstance().setRootContainer(apiGrp);
    }

    public void event() throws Exception {
        // 保存
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canSubmit()) {
                        doUpdate();
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        // 閉じる
        cancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canClose()) {
                        closeWindow();
                    }
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            if (canClose()) {
                closeWindow();
            } else {
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
     * 
     * @return true:OK, false:NG
     */
    private boolean canSubmit() {

        String host = getHostName().getText();
        String port = getPort().getText();
        String user = getUserName().getText();
        String password = getPassword().getText();

        if ("".equals(host.trim())) {
            ACMessageBox.show("接続先ホストを入力してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }
        if ("".equals(port.trim())) {
            ACMessageBox.show("ポート番号を入力してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }
        if ("".equals(user.trim())) {
            ACMessageBox.show("ユーザー名を入力してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }
        if ("".equals(password.trim())) {
            ACMessageBox.show("パスワードを入力してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }

        return true;
    }

    /**
     * 閉じてよいかどうかを判定します。
     * 
     * @return boolean
     */
    private boolean canClose() {
        try {
            if (IkenshoSnapshot.getInstance().isModified()) {
                int result = ACMessageBox.show("変更内容が破棄されます。\nよろしいですか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_OK) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return true;
    }

    private void doSelect() throws Exception {

        // スナップショット
        IkenshoSnapshot.getInstance().snapshot(); // 撮影
    }

    private void doUpdate() throws Exception {
        if (doUpdateToxml()) {
            ACMessageBox.show("保存されました。");
            closeWindow();
        }
    }

    /**
     * DBにデータを更新します。
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToxml() throws Exception {
        try {
            // 接続情報を取得
            String host = getHostName().getText();
            String port = getPort().getText();
            String user = getUserName().getText();
            String password = getPassword().getText();

            // XMLに保存する
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/IP", host);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/ApiPort", port);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/ApiUserName", user);
            ACFrame.getInstance().getPropertyXML()
                    .setForceValueAt("ReceiptAccess/ApiPassword", password);
            // 書き込み処理
            ACFrame.getInstance().getPropertyXML().writeWithCheck();

        } catch (Exception ex) {

            throw new Exception(ex);
        }

        return true;
    }

    /**
     * 接続先ホストテキスト
     * 
     * @return ACTextFieldi
     */
    protected ACTextField getHostName() {
        if (hostName == null) {
            hostName = new ACTextField();
            hostName.setColumns(15);
            hostName.setMaxLength(255);
            hostName.setText("127.0.0.1");
            hostName.setBindPath("HOST");
        }
        return hostName;

    }

    protected ACLabelContainer getHostNames() {
        if (hostNames == null) {
            hostNames = new ACLabelContainer();
            hostNames.add(getHostName());
            hostNames.setText("接続先ホスト");
        }
        return hostNames;
    }

    protected ACTextField getUserName() {
        if (userName == null) {
            userName = new ACTextField();
            userName.setMaxLength(255);
            userName.setColumns(15);
            userName.setText("ormaster");
            userName.setBindPath("USER_NAME");
        }
        return userName;
    }

    protected ACLabelContainer getUserNames() {
        if (userNames == null) {
            userNames = new ACLabelContainer();
            userNames.add(getUserName());
            userNames.setText("ユーザー名");
        }
        return userNames;
    }

    protected ACTextField getPort() {
        if (port == null) {
            port = new ACTextField();
            port.setMaxLength(5);
            port.setColumns(5);
            port.setText("8000");
            port.setBindPath("PORT");
        }
        return port;
    }

    protected ACLabelContainer getPorts() {
        if (ports == null) {
            ports = new ACLabelContainer();
            ports.add(getPort());
            ports.setText("ポート番号");
        }
        return ports;
    }

    protected ACTextField getPassword() {
        if (password == null) {
            password = new ACTextField();
            password.setColumns(15);
            password.setMaxLength(255);
            password.setText("ormaster");
            password.setBindPath("PASSWORD");
        }
        return password;
    }

    protected ACLabelContainer getPasswords() {
        if (passwords == null) {
            passwords = new ACLabelContainer();
            passwords.add(getPassword());
            passwords.setText("パスワード");
        }
        return passwords;
    }

    /**
     * 日レセ連携の既存設定を保持します。
     * 
     * @throws Exception
     */
    public void setConnectSetting() throws Exception {

        // 過去の通信設定を読み込む
        if (ACFrame.getInstance().hasProperty("ReceiptAccess/IP")) {
            setReceiptSettingIp(ACFrame.getInstance().getProperty(
                    "ReceiptAccess/IP"));
        }
        if (ACFrame.getInstance().hasProperty("ReceiptAccess/ApiPort")) {
            setReceiptSettingApiPort(ACFrame.getInstance().getProperty(
                    "ReceiptAccess/ApiPort"));
        }
        if (ACFrame.getInstance().hasProperty("ReceiptAccess/ApiUserName")) {
            setReceiptSettingApiUser(ACFrame.getInstance().getProperty(
                    "ReceiptAccess/ApiUserName"));
        }
        if (ACFrame.getInstance().hasProperty("ReceiptAccess/ApiPassword")) {
            setReceiptSettingApiPass(ACFrame.getInstance().getProperty(
                    "ReceiptAccess/ApiPassword"));
        }
        if (ACFrame.getInstance().hasProperty("ReceiptAccess/KanaConvert")) {
            setReceiptSettingUseKanaConvert(ACFrame.getInstance().getProperty(
                    "ReceiptAccess/KanaConvert"));
        }
        if (ACFrame.getInstance().hasProperty(
                "ReceiptAccess/ReceiptSoftVersion")) {
            setReceiptSettingReceiptVersion(ACCastUtilities.toInt(
                    ACFrame.getInstance().getProperty(
                            "ReceiptAccess/ReceiptSoftVersion"), 1));
        }
        if (ACFrame.getInstance().hasProperty("ReceiptAccess/ApiAutoUpdate")) {
            setReceiptSettingAutoUpdateFlag(ACCastUtilities.toInt(ACFrame
                    .getInstance().getProperty("ReceiptAccess/ApiAutoUpdate"),
                    0));
        }

        // 初期値設定
        if (!"".equals(getReceiptSettingIp())) {
            getHostName().setText(getReceiptSettingIp());
        }
        if (!"".equals(getReceiptSettingApiUser())) {
            getUserName().setText(getReceiptSettingApiUser());
        }
        if (!"".equals(getReceiptSettingApiPass())) {
            getPassword().setText(getReceiptSettingApiPass());
        }
        if (!"".equals(getReceiptSettingApiPort())) {
            getPort().setText(getReceiptSettingApiPort());
        }

    }

    public String getReceiptSettingIp() {
        return receiptSettingIp;
    }

    public void setReceiptSettingIp(String receiptSettingIp) {
        this.receiptSettingIp = receiptSettingIp;
    }

    public String getReceiptSettingApiPort() {
        return receiptSettingApiPort;
    }

    public void setReceiptSettingApiPort(String receiptSettingApiPort) {
        this.receiptSettingApiPort = receiptSettingApiPort;
    }

    public String getReceiptSettingApiUser() {
        return receiptSettingApiUser;
    }

    public void setReceiptSettingApiUser(String receiptSettingApiUser) {
        this.receiptSettingApiUser = receiptSettingApiUser;
    }

    public String getReceiptSettingApiPass() {
        return receiptSettingApiPass;
    }

    public void setReceiptSettingApiPass(String receiptSettingApiPass) {
        this.receiptSettingApiPass = receiptSettingApiPass;
    }

    public String getReceiptSettingUseKanaConvert() {
        return receiptSettingUseKanaConvert;
    }

    public void setReceiptSettingUseKanaConvert(
            String receiptSettingUseKanaConvert) {
        this.receiptSettingUseKanaConvert = receiptSettingUseKanaConvert;
    }

    public int getReceiptSettingReceiptVersion() {
        return receiptSettingReceiptVersion;
    }

    public void setReceiptSettingReceiptVersion(int receiptSettingReceiptVersion) {
        this.receiptSettingReceiptVersion = receiptSettingReceiptVersion;
    }

    /**
     * @return receiptSettingAutoUpdateFlag
     * @since
     * @author Masahiko.Higuchi
     */
    public int getReceiptSettingAutoUpdateFlag() {
        return receiptSettingAutoUpdateFlag;
    }

    /**
     * @param receiptSettingAutoUpdateFlag
     *            セットする receiptSettingAutoUpdateFlag
     * @since
     * @author Masahiko.Higuchi
     */
    public void setReceiptSettingAutoUpdateFlag(int receiptSettingAutoUpdateFlag) {
        this.receiptSettingAutoUpdateFlag = receiptSettingAutoUpdateFlag;
    }

}
