package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairDialog;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.filechooser.ACFileFilter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;

public class IkenshoIshiShowHelp extends ACAffairDialog {
    protected ACButton show;
    protected ACButton close;
    protected ACPanel buttons;
    protected ACLabel infomation;
    protected ACLabelContainer urls;
    protected ACTextField url;
    protected ACLabelContainer browsers;
    protected ACTextField browser;
    protected ACButton browserBrowse;
    protected ACGroupBox settings;

    /**
     * browser を返します。
     * 
     * @return browser
     */
    protected ACButton getBrowserBrowse() {
        if (browserBrowse == null) {
            browserBrowse = new ACButton("参照..");
            browserBrowse.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    doSelectFile();
                }
                
            });
        }
        return browserBrowse;
    }

    /**
     * ファイルを選択します。
     */
    protected void doSelectFile() {
        //ファイル指定
        ACFileChooser fileChooser = new ACFileChooser();
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(new String[] {"exe"});
        filter.setDescription("アプリケーション(*.exe)");

        File fileOld = new File(getBrowser().getText());
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
            file = new File(file.getParent() + ACConstants.FILE_SEPARATOR +
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
        getBrowser().setText(file.getPath());
    }
    /**
     * browser を返します。
     * 
     * @return browser
     */
    protected ACTextField getBrowser() {
        if (browser == null) {
            browser = new ACTextField();
            browser.setColumns(30);
        }
        return browser;
    }

    /**
     * browsers を返します。
     * 
     * @return browsers
     */
    protected ACLabelContainer getBrowsers() {
        if (browsers == null) {
            browsers = new ACLabelContainer();
            browsers.setText("表示に使用するブラウザ");
            browsers.add(getBrowser(), VRLayout.FLOW);
            browsers.add(getBrowserBrowse(), VRLayout.FLOW);
        }
        return browsers;
    }

    /**
     * buttons を返します。
     * 
     * @return buttons
     */
    protected ACPanel getButtons() {
        if (buttons == null) {
            buttons = new ACPanel();
            buttons.add(getClose(), VRLayout.EAST);
            buttons.add(getShow(), VRLayout.EAST);
        }
        return buttons;
    }

    /**
     * close を返します。
     * 
     * @return close
     */
    protected ACButton getClose() {
        if (close == null) {
            close = new ACButton("閉じる");
            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return close;
    }

    /**
     * infomation を返します。
     * 
     * @return infomation
     */
    protected ACLabel getInfomation() {
        if (infomation == null) {
            infomation = new ACLabel();
            infomation.setText("「表示」ボタンで以下の記載例を表示します。"); 
        }
        return infomation;
    }

    /**
     * settings を返します。
     * 
     * @return settings
     */
    protected ACGroupBox getSettings() {
        if (settings == null) {
            settings = new ACGroupBox("設定");
            settings.add(getBrowsers(), VRLayout.FLOW_INSETLINE_RETURN);
        }
        return settings;
    }

    /**
     * show を返します。
     * 
     * @return show
     */
    protected ACButton getShow() {
        if (show == null) {
            show = new ACButton("表示");
            show.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showHelp();
                }
            });
        }
        return show;
    }

    /**
     * urls を返します。
     * 
     * @return urls
     */
    protected ACLabelContainer getUrls() {
        if (urls == null) {
            urls = new ACLabelContainer();
            urls.setText("記載例の場所");
            urls.add(getUrl());
        }
        return urls;
    }

    /**
     * ブラウザで表示します。
     */
    protected void showHelp() {
        try {
            Runtime.getRuntime().exec(new String[] { getBrowser().getText(), getUrl().getText() });
        } catch (IOException e) {
            ACMessageBox.showExclamation("選択した設定では表示できません。");
        }
    }

    /**
     * url を返します。
     * 
     * @return url
     */
    protected ACTextField getUrl() {
        if (url == null) {
            url = new ACTextField();
            url.setEditable(false);
            url.setText(getHelpURL());
        }
        return url;
    }

    /**
     * アクセスするURLを返します。
     * 
     * @return アクセスするURL
     */
    protected String getHelpURL() {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    new FileInputStream("i_tebiki.txt")));
            if (br != null) {
                String txt = br.readLine();
                if (!ACTextUtilities.isNullText(txt)) {
                    return txt;
                }
                br.close();
            }
        } catch (Exception e) {
        }
        return "http://www.mhlw.go.jp/bunya/shougaihoken/jiritsushienhou15/pdf/1.pdf";
    }


    /**
     * 自身に内包項目を追加します。
     */
    protected void addThis() {
        getContentPane().add(getInfomation(), VRLayout.NORTH);
        getContentPane().add(getUrls(), VRLayout.NORTH);
        getContentPane().add(getSettings(), VRLayout.CLIENT);
        getContentPane().add(getButtons(), VRLayout.SOUTH);
    }

    /**
     * コンストラクタです。
     * 
     * @throws HeadlessException 処理例外
     */
    public IkenshoIshiShowHelp() throws HeadlessException {
        super(ACFrame.getInstance(), "記載例の表示", true);
    }

    protected void initComponent() {
        super.initComponent();

        getContentPane().setLayout(new VRLayout());

        addThis();
        
        String osName = System.getProperty("os.name", "").toLowerCase();
        if (osName.startsWith("mac")) {
            getBrowser().setText("open");
        }else{
            getBrowser().setText("C:\\Program Files\\Internet Explorer\\iexplore.exe");
        }

        initPosition();
    }

    /**
     * 位置を初期化します。
     */
    protected void initPosition() {
        // ウィンドウのサイズ
        setSize(new Dimension(520, 180));
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
    }

}
