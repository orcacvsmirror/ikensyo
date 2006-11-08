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

            //スナップショット撮影
            IkenshoSnapshot.getInstance().snapshot();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public IkenshoSettingPDF() {
        this(ACFrame.getInstance(), "PDFの設定", true);
    }

    private void jbInit() {
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(client, BorderLayout.CENTER);

        client.setLayout(new VRLayout());
        client.add(fileNamePnl, VRLayout.FLOW_RETURN);
        client.add(info, VRLayout.FLOW_RETURN);
        client.add(btnGrp, VRLayout.SOUTH);

       //ファイル名
       VRLayout filePnlLayout = new VRLayout();
       filePnlLayout.setAutoWrap(false);
       fileNamePnl.setLayout(filePnlLayout);
       fileNamePnl.add(fileNameContainer, VRLayout.FLOW_INSETLINE);

       fileNameContainer.setText("ファイル名");
       fileNameContainer.add(fileName, null);
       fileName.setColumns(32);
       fileName.setMaxLength(1000);
       fileName.setIMEMode(InputSubset.LATIN_DIGITS);
       fileName.setBindPath("FILE_NAME");
       fileNameContainer.add(fileChoose, null);

       //説明文
       info.setText("AcrobatReaderの実行ファイルを選択してください。"
                + IkenshoConstants.LINE_SEPARATOR + "例(Windows) → C:\\Program Files\\Adobe\\Acrobat 7.0\\Reader\\AcroRd32.exe"
       );
       info.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

       //操作ボタン
       btnGrp.add(submit, null);
       btnGrp.add(cancel, null);
//       btnGrp.add(fileChoose, null);
       submit.setText("設定(S)");
       submit.setMnemonic('S');
       cancel.setText("キャンセル(C)");
       cancel.setMnemonic('C');
       fileChoose.setText("参照(L)");
       fileChoose.setMnemonic('L');
    }

    private void init() throws Exception {
        //ウィンドウサイズ
        setSize(new Dimension(480, 160));
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
        //設定
        submit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                try {
                    if (canSubmit()) { //入力チェック
                        if (writeSetting()) { //XML更新
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

        //参照
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
     * 登録処理が可能かどうかをチェックします。
     * @return true:OK, false:NG
     */
    private boolean canSubmit() {
        //入力チェック
        if (IkenshoCommon.isNullText(fileName.getText())) {
            fileName.requestFocus();
            ACMessageBox.showExclamation("ファイル名が未入力です。");
            return false;
        }

        //ファイル存在チェック
        File tmp = new File(fileName.getText());
        if (!tmp.exists()) {
            fileName.requestFocus();
            ACMessageBox.showExclamation("ファイル名が不正です。");
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
        //ファイルパス
        fileName.setText(getProperty("Acrobat/Path").toLowerCase());
    }

    /**
     * ファイルを選択します。
     */
    private void doSelectFile() {
        //ファイル指定
        ACFileChooser fileChooser = new ACFileChooser();
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(new String[] {"exe"});
        filter.setDescription("Adobe acrobat(*.exe)");

        File fileOld = new File(getProperty("Acrobat/Path"));
        File file = null;
        boolean loopFlg = false;
        do {
            loopFlg = false;

            //ファイル選択
            file = fileChooser.showOpenDialog(fileOld.getParent(), fileOld.getName(), "開く", filter);

            //キャンセル時、中断
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
        } while(loopFlg);

        //パスを画面に反映
        fileName.setText(file.getPath());
    }

    /**
     * 設定を書き込みます。
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
            //例外処理（tryせずに呼び出し元メソッドにthrowsしてもよい）
            IkenshoCommon.showExceptionMessage(ex);
        }

        return true;
    }
}
