package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.util.Arrays;

import javax.swing.JPanel;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACCheckBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.component.VRRadioButtonGroup;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

public class IkenshoSettingOther extends IkenshoDialog {

    private JPanel contentPane;
    private ACPanel client = new ACPanel();
    private ACPanel medicineViewCountPanel = new ACPanel();
    private ACLabelContainer medicineViewCountContainer = new ACLabelContainer();
    private VRRadioButtonGroup medicineViewCount = new VRRadioButtonGroup();
    private ACLabel medicineViewCountSummary = new ACLabel();
    private ACLabelContainer medicalViewCountOfShijisho6Container = new ACLabelContainer();
    private ACIntegerCheckBox medicalViewCountOfShijisho6 = new ACIntegerCheckBox();
    private ACPanel btnGrp = new ACPanel();
    private ACButton submit = new ACButton();
    private ACButton cancel = new ACButton();

    public IkenshoSettingOther(Frame owner, String title, boolean modal) {
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
    public IkenshoSettingOther() {
        this(ACFrame.getInstance(), "その他の設定", true);
    }

    private void jbInit() {
        contentPane = (JPanel)getContentPane();
        contentPane.setLayout(new BorderLayout());
        contentPane.add(client, BorderLayout.CENTER);

        client.add(medicineViewCountPanel, VRLayout.FLOW_RETURN);
        client.add(btnGrp, VRLayout.SOUTH);

       //ファイル名
       medicineViewCountPanel.setAutoWrap(false);
       medicineViewCountPanel.add(medicineViewCountContainer, VRLayout.FLOW_INSETLINE_RETURN);

       medicineViewCountContainer.setText("薬剤名の表示個数");
       medicineViewCountContainer.add(medicineViewCount, null);
       medicineViewCount.setModel(new ACListModelAdapter(new VRArrayList(Arrays.asList(new String[] {
                                          "6個",
                                          "8個"}))));
       medicineViewCount.setBindPath("FILE_NAME");

       medicineViewCountSummary.setText("薬剤項目によって、傷病の経過に入力可能な文字数が異なります。"
                + ACConstants.LINE_SEPARATOR + "　薬剤を1～6個入力した場合：傷病の経過は250文字まで入力可能"
                + ACConstants.LINE_SEPARATOR + "　薬剤を7個入力した場合：傷病の経過は220文字まで入力可能"
                + ACConstants.LINE_SEPARATOR + "　薬剤を8個入力した場合：傷病の経過は190文字まで入力可能"
                );
       
       medicalViewCountOfShijisho6.setText("訪問看護指示書の薬剤名表示個数を6個にする");
       medicalViewCountOfShijisho6Container.add(medicalViewCountOfShijisho6);
       
       medicineViewCountSummary.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
       medicineViewCountPanel.add(medicineViewCountSummary, VRLayout.FLOW_INSETLINE_RETURN);
       medicineViewCountPanel.add(medicalViewCountOfShijisho6Container, VRLayout.FLOW_RETURN);
       
       
       //操作ボタン
       btnGrp.setAlignment(VRLayout.CENTER);
       btnGrp.add(submit, null);
       btnGrp.add(cancel, null);

       submit.setText("設定(S)");
       submit.setMnemonic('S');
       cancel.setText("キャンセル(C)");
       cancel.setMnemonic('C');
    }

    private void init() throws Exception {
        //ウィンドウサイズ
        //setSize(new Dimension(480, 160));
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
                    if (writeSetting()) { //XML更新
                        closeWindow();
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
        
        if (ACFrame.getInstance().hasProperty(
                "DocumentSetting/MedicineViewCount")
                && ACCastUtilities.toInt(getProperty("DocumentSetting/MedicineViewCount"),6)==8) {
            medicineViewCount.setSelectedIndex(1);
        }else{
            medicineViewCount.setSelectedIndex(0);
        }
        
        if (ACFrame.getInstance().hasProperty(
                "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6")
                && ACCastUtilities
                        .toBoolean(
                                getProperty("DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6"),
                                false)) {
            medicalViewCountOfShijisho6.setSelected(true);
        } else {
            medicalViewCountOfShijisho6.setSelected(false);
        }
        
    }

    /**
     * 設定を書き込みます。
     * @return boolean
     */
    private boolean writeSetting() {
        try {
            String medicineViewCountValue = "6";
            if(medicineViewCount.getSelectedIndex()==1){
                medicineViewCountValue = "8";
            }
            if("6".equals(medicineViewCountValue)){
                //6個を選択した場合
                if (ACFrame.getInstance().hasProperty(
                        "DocumentSetting/MedicineViewCount")
                        && ACCastUtilities
                                .toInt(
                                        getProperty("DocumentSetting/MedicineViewCount"),
                                        6) == 8) {
                    //元の設定は8個だった場合
                    //TODO 薬剤7または薬剤8の入力されているデータがあるか確認
                    
                    if (ACMessageBox
                            .show(
                                    "薬剤名が7個以上入力されている意見書が存在します。"
                                            + ACConstants.LINE_SEPARATOR
                                            + "薬剤名の表示個数を6個に設定した場合、7個以上の薬剤名は印刷されなくなります。"
                                            + ACConstants.LINE_SEPARATOR
                                            + "よろしいですか？",
                                    ACMessageBox.BUTTON_OKCANCEL,
                                    ACMessageBox.ICON_QUESTION,
                                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                        return false;
                    }
                    
                }
            }
            ACFrame.getInstance().getPropertyXML().setForceValueAt("DocumentSetting/MedicineViewCount", medicineViewCountValue);
            

            //訪問看護指示書の薬剤名表示個数を6個にする
            boolean medicineViewCountOfHoumonKangoShijishoFixed6 = medicalViewCountOfShijisho6.isSelected();
            ACFrame
                    .getInstance()
                    .getPropertyXML()
                    .setForceValueAt(
                            "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6",
                            ACCastUtilities
                                    .toString(new Boolean(
                                            medicineViewCountOfHoumonKangoShijishoFixed6)));
      
          if(!ACFrame.getInstance().getPropertyXML().writeWithCheck()){
              return false;
          }
        }
        catch (Exception ex) {
            //例外処理（tryせずに呼び出し元メソッドにthrowsしてもよい）
            IkenshoCommon.showExceptionMessage(ex);
        }

        return true;
    }
}
