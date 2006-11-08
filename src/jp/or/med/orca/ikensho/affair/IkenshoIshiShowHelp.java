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
     * browser ��Ԃ��܂��B
     * 
     * @return browser
     */
    protected ACButton getBrowserBrowse() {
        if (browserBrowse == null) {
            browserBrowse = new ACButton("�Q��..");
            browserBrowse.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e) {
                    doSelectFile();
                }
                
            });
        }
        return browserBrowse;
    }

    /**
     * �t�@�C����I�����܂��B
     */
    protected void doSelectFile() {
        //�t�@�C���w��
        ACFileChooser fileChooser = new ACFileChooser();
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(new String[] {"exe"});
        filter.setDescription("�A�v���P�[�V����(*.exe)");

        File fileOld = new File(getBrowser().getText());
        File file = null;
        boolean loopFlg = false;
        do {
            loopFlg = false;

            //�t�@�C���I��
            file = fileChooser.showOpenDialog(fileOld.getParent(), fileOld.getName(), "�J��", filter);

            //�L�����Z�����A���f
            if (file == null) {
                return;
            }

            //�g���q��⊮
            file = new File(file.getParent() + ACConstants.FILE_SEPARATOR +
                            ( (ACFileFilter) fileChooser.getFileFilter()).
                            getFilePathWithExtension(file.getName()));

            //�t�@�C�����݃`�F�b�N
            if (!file.exists()) {
                ACMessageBox.showExclamation("�t�@�C�������s���ł��B");
                loopFlg = true;
                continue;
            }
        } while(loopFlg);

        //�p�X����ʂɔ��f
        getBrowser().setText(file.getPath());
    }
    /**
     * browser ��Ԃ��܂��B
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
     * browsers ��Ԃ��܂��B
     * 
     * @return browsers
     */
    protected ACLabelContainer getBrowsers() {
        if (browsers == null) {
            browsers = new ACLabelContainer();
            browsers.setText("�\���Ɏg�p����u���E�U");
            browsers.add(getBrowser(), VRLayout.FLOW);
            browsers.add(getBrowserBrowse(), VRLayout.FLOW);
        }
        return browsers;
    }

    /**
     * buttons ��Ԃ��܂��B
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
     * close ��Ԃ��܂��B
     * 
     * @return close
     */
    protected ACButton getClose() {
        if (close == null) {
            close = new ACButton("����");
            close.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
        }
        return close;
    }

    /**
     * infomation ��Ԃ��܂��B
     * 
     * @return infomation
     */
    protected ACLabel getInfomation() {
        if (infomation == null) {
            infomation = new ACLabel();
            infomation.setText("�u�\���v�{�^���ňȉ��̋L�ڗ��\�����܂��B"); 
        }
        return infomation;
    }

    /**
     * settings ��Ԃ��܂��B
     * 
     * @return settings
     */
    protected ACGroupBox getSettings() {
        if (settings == null) {
            settings = new ACGroupBox("�ݒ�");
            settings.add(getBrowsers(), VRLayout.FLOW_INSETLINE_RETURN);
        }
        return settings;
    }

    /**
     * show ��Ԃ��܂��B
     * 
     * @return show
     */
    protected ACButton getShow() {
        if (show == null) {
            show = new ACButton("�\��");
            show.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    showHelp();
                }
            });
        }
        return show;
    }

    /**
     * urls ��Ԃ��܂��B
     * 
     * @return urls
     */
    protected ACLabelContainer getUrls() {
        if (urls == null) {
            urls = new ACLabelContainer();
            urls.setText("�L�ڗ�̏ꏊ");
            urls.add(getUrl());
        }
        return urls;
    }

    /**
     * �u���E�U�ŕ\�����܂��B
     */
    protected void showHelp() {
        try {
            Runtime.getRuntime().exec(new String[] { getBrowser().getText(), getUrl().getText() });
        } catch (IOException e) {
            ACMessageBox.showExclamation("�I�������ݒ�ł͕\���ł��܂���B");
        }
    }

    /**
     * url ��Ԃ��܂��B
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
     * �A�N�Z�X����URL��Ԃ��܂��B
     * 
     * @return �A�N�Z�X����URL
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
     * ���g�ɓ���ڂ�ǉ����܂��B
     */
    protected void addThis() {
        getContentPane().add(getInfomation(), VRLayout.NORTH);
        getContentPane().add(getUrls(), VRLayout.NORTH);
        getContentPane().add(getSettings(), VRLayout.CLIENT);
        getContentPane().add(getButtons(), VRLayout.SOUTH);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @throws HeadlessException ������O
     */
    public IkenshoIshiShowHelp() throws HeadlessException {
        super(ACFrame.getInstance(), "�L�ڗ�̕\��", true);
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
     * �ʒu�����������܂��B
     */
    protected void initPosition() {
        // �E�B���h�E�̃T�C�Y
        setSize(new Dimension(520, 180));
        // �E�B���h�E�𒆉��ɔz�u
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
