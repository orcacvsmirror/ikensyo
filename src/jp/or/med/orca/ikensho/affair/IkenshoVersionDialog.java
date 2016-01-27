package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.swing.JTabbedPane;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.component.VRTextArea;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;

/** TODO <HEAD_IKENSYO> */
public class IkenshoVersionDialog extends IkenshoDialog {
    private VRPanel contents = new VRPanel();
    private VRPanel footer = new VRPanel();
    private VRPanel body = new VRPanel();
    private VRTextArea vRTextArea2 = new VRTextArea();
    private VRPanel buttons = new VRPanel();
    private ACButton close = new ACButton();
    private ACTextArea message = new ACTextArea();
    private String systemVersion;
    private JTabbedPane licences = new JTabbedPane();

    /**
     * 主たる著作権表示メッセージを返します。
     * 
     * @return 主たる著作権表示メッセージ
     */
    protected String getMainLicence() {
        return readFile("licence/main_licence.txt");
    }

    /**
     * テキストファイル内容を読み込んで返します。
     * 
     * @param path ファイルパス
     * @return ファイル内容
     */
    protected String readFile(String path) {
        StringBuffer sb = new StringBuffer();
        try {

            FileReader fr;
            fr = new FileReader(new File(path));
            if (fr != null) {
                try {

                    BufferedReader br = new BufferedReader(fr);
                    if (br != null) {
                        // 読込みループ。
                        String line; // 読み込まれた１行。
                        while ((line = br.readLine()) != null) {
                            sb.append(line);
                            sb.append(ACConstants.LINE_SEPARATOR);
                        }

                        // 入力・出力ストリームを閉じる。
                        br.close();
                    }
                } catch (Exception ex) {

                } finally {
                    fr.close();
                }
            }
        } catch (Exception e) {
        }
        return sb.toString();
    }

    /**
     * 関連著作権表示メッセージを返します。
     * 
     * @return 関連著作権表示メッセージ(key:String キャプション, value:String 内容)
     */
    protected HashMap getLicences() {
        LinkedHashMap licenceMap = new LinkedHashMap();

        String[] licences = readFile("licence/sub_licence.txt").split(
                ACConstants.LINE_SEPARATOR);
        int end = licences.length;
        for (int i = 0; i < end; i++) {
            String[] params = licences[i].split(",", 2);
            if (params.length >= 2) {
                licenceMap.put(params[1], readFile("licence/" + params[0]));
            }
        }
        return licenceMap;
    }

    public IkenshoVersionDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();

            init();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    public IkenshoVersionDialog() {
        this(ACFrame.getInstance(), "医見書", true);
    }

    /**
     * ダイアログを表示します。
     * 
     * @param version システムバージョン
     */
    public void show(String version) {
        systemVersion = version;
        setTitle("医見書Ver" + version);
        message.setText(getMainLicence());
        setVisible(true);
    }

    private void jbInit() throws Exception {
        contents.setLayout(new BorderLayout());
        vRTextArea2.setOpaque(false);
        vRTextArea2.setEditable(false);
        vRTextArea2.setLineWrap(true);
        vRTextArea2.setText("本プログラムの使用により発生したいかなる損害について当方は責任を負いません。");
        close.setMnemonic('C');
        close.setText("閉じる(C)");
        body.setLayout(new BorderLayout());
        message.setEditable(false);
        message.getViewport().setBackground(Color.white);
        message.setOpaque(false);
//        message.setPreferredSize(new Dimension(2, 135));
        message.setPreferredSize(new Dimension(2, 210));
        footer.setLayout(new BorderLayout());
        this.getContentPane().add(contents, BorderLayout.CENTER);
        buttons.setLayout(new VRLayout());
        contents.add(body, BorderLayout.CENTER);
        contents.add(footer, BorderLayout.SOUTH);
        footer.add(vRTextArea2, BorderLayout.CENTER);
        footer.add(buttons, BorderLayout.EAST);
        buttons.add(close, null);
        body.add(message, BorderLayout.NORTH);
        body.add(licences, BorderLayout.CENTER);

        HashMap licenceMap = getLicences();
        Iterator it = licenceMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry ent = (Map.Entry) it.next();
            ACTextArea page = new ACTextArea();
            page.setText(String.valueOf(ent.getValue()));
            page.setEditable(false);
            page.setLineWrap(true);
            page.setBackground(Color.white);
            page.setSelectionStart(0);
            page.setSelectionEnd(0);
            licences.add(page, String.valueOf(ent.getKey()));
        }
    }

    private void init() throws Exception {
//        setSize(new Dimension(500, 380));
        setSize(new Dimension(900, 700));
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
