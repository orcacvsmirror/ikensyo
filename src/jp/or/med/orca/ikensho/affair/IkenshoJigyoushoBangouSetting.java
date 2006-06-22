package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.WindowEvent;
import java.awt.im.InputSubset;

import javax.swing.FocusManager;
import javax.swing.JDialog;
import javax.swing.JPanel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** <HEAD_IKENSYO> */
public class IkenshoJigyoushoBangouSetting extends JDialog {
    private JPanel contentPane = new JPanel();
    private VRPanel client = new VRPanel();
    private ACLabelContainer insurerNmContainer = new ACLabelContainer();
    private ACComboBox insurerNm = new ACComboBox();
    private VRPanel insurerNoPnl = new VRPanel();
    private VRLabel insurerNoCaption1 = new VRLabel();
    private VRLabel insurerNoCaption2 = new VRLabel();
    private ACTextField insurerNoField = new ACTextField();
    private ACLabelContainer jigyoushoNoContainer = new ACLabelContainer();
    private ACTextField jigyoushoNoField = new ACTextField();
    private VRLabel jigyoushoNoCaption = new VRLabel();
    private VRPanel btnPnl = new VRPanel();
    private ACButton submit = new ACButton();
    private ACButton close = new ACButton();

    private VRArrayList insurerData = new VRArrayList();
    private VRArrayList enteredData = new VRArrayList();
    private VRMap affair = new VRHashMap();
    private VRMap paramas = new VRHashMap();
    private String insurerNoOld;
    private boolean isUpdate;

    public IkenshoJigyoushoBangouSetting(VRMap affair) {
        super(ACFrame.getInstance(), "事業所番号設定", true);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            IkenshoSnapshot.getInstance().setRootContainer(client); // スナップショット対象設定
            initComponent(affair);
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        contentPane = (JPanel) this.getContentPane();
        contentPane.add(client);

        VRLayout clientLayout = new VRLayout();
        clientLayout.setHgap(2);
        clientLayout.setVgap(2);
        client.setLayout(clientLayout);
        client.add(insurerNmContainer, VRLayout.FLOW_INSETLINE);
        client.add(insurerNoPnl, VRLayout.FLOW_RETURN);
        client.add(jigyoushoNoContainer, VRLayout.FLOW_INSETLINE);
        client.add(btnPnl, VRLayout.FLOW_RETURN);

        insurerNmContainer.setText("保険者番号");
        insurerNmContainer.add(insurerNm, null);
        insurerNm.setEditable(false);
        insurerNm.setPreferredSize(new Dimension(280, 19));
        insurerNm.setBindPath("INSURER_NM");

        VRLayout insurerNoPnlLayout = new VRLayout();
        insurerNoPnlLayout.setHgap(0);
        insurerNoPnlLayout.setVgap(0);
        insurerNoPnlLayout.setAutoWrap(false);
        insurerNoPnl.setLayout(insurerNoPnlLayout);
        insurerNoPnl.add(insurerNoCaption1, VRLayout.FLOW);
        insurerNoPnl.add(insurerNoField, VRLayout.FLOW);
        insurerNoPnl.add(insurerNoCaption2, VRLayout.FLOW);
        insurerNoCaption1.setText("(");
        insurerNoField.setColumns(6);
        insurerNoField.setEditable(false);
        insurerNoField.setBindPath("INSURER_NO");
        insurerNoCaption2.setText(")");

        jigyoushoNoContainer.setText("事業所番号");
        VRLayout jigyoushoNoContainerLayout = new VRLayout();
        jigyoushoNoContainerLayout.setHgap(0);
        jigyoushoNoContainerLayout.setVgap(1);
        jigyoushoNoContainerLayout.setAutoWrap(false);
        jigyoushoNoContainer.add(jigyoushoNoField, VRLayout.FLOW);
        jigyoushoNoContainer.add(jigyoushoNoCaption, VRLayout.FLOW);
        jigyoushoNoField.setColumns(10);
        jigyoushoNoField.setMaxLength(10);
        jigyoushoNoField.setIMEMode(InputSubset.LATIN_DIGITS);
        jigyoushoNoField.setCharType(VRCharType.ONLY_DIGIT);
        // jigyoushoNoField.setCharType(VRCharType.ONLY_ALNUM);
        jigyoushoNoField.setBindPath("JIGYOUSHA_NO");
        jigyoushoNoCaption.setText("(数字10桁)");
        jigyoushoNoCaption
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

        VRLayout btnPnlLayout = new VRLayout();
        btnPnlLayout.setHgap(0);
        btnPnlLayout.setVgap(0);
        btnPnlLayout.setAutoWrap(false);
        btnPnl.setLayout(btnPnlLayout);
        btnPnl.add(submit, VRLayout.FLOW);
        btnPnl.add(close, VRLayout.FLOW);
        submit.setText("登録(S)");
        submit.setMnemonic('S');
        close.setText("閉じる(C)");
        close.setMnemonic('C');
    }

    private void initComponent(VRMap affair) throws Exception {
        // ウィンドウのサイズ
        setSize(new Dimension(470, 110));
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

        // 保険者一覧をDBから取得・コンボに設定する
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" INSURER_NO");
        sb.append(" ,INSURER_NM");
        sb.append(" FROM");
        sb.append(" INSURER");
        insurerData = (VRArrayList) dbm.executeQuery(sb.toString());
        IkenshoCommon.applyComboModel(insurerNm,
                new VRHashMapArrayToConstKeyArrayAdapter(insurerData,
                        "INSURER_NM"));

        // 渡りデータを取得し、各コンポーネントに設定する
        this.affair = affair;
        String actParam = String.valueOf(affair.getData("ACT"));
        if (actParam.equals("update")) {
            isUpdate = true;
        } else {
            isUpdate = false;
        }
        if (isUpdate) {
            submit.setText("更新(S)");
            enteredData = (VRArrayList) affair.getData("DATA");
            int selRow = Integer.parseInt(String.valueOf(affair
                    .getData("SEL_ROW")));
            VRMap row = (VRMap) enteredData.getData(selRow);

            // 保険者名
            String insurerNmParam = String.valueOf(row.getData("INSURER_NM"));
            for (int i = 0; i < insurerNm.getItemCount(); i++) {
                if (insurerNm.getItemAt(i).toString().equals(insurerNmParam)) {
                    insurerNm.setSelectedIndex(i);
                    break;
                }
            }

            // 保険者番号
            insurerNoOld = String.valueOf(row.getData("INSURER_NO"));
            insurerNoField.setText(insurerNoOld);

            // 事業所番号
            jigyoushoNoField.setText(String
                    .valueOf(row.getData("JIGYOUSHA_NO")));
        } else {
            submit.setText("登録(S)");
        }

        // スナップショット撮影
        IkenshoSnapshot.getInstance().snapshot();
    }

    private void event() throws Exception {
        // 保険者名コンボ
        insurerNm.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // 選択された保険者名から保険者番号を取得し、TextFieldに設定する
                insurerNoField.setText(findInsurerNo(String.valueOf(insurerNm
                        .getSelectedItem())));
            }
        });

        // 登録ボタン
        submit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (canSubmit()) {
                    // パラメータを渡し用HashMapに格納する
                    paramas.put("ACT", "submit");
                    paramas.put("INSURER_NM", String.valueOf(insurerNm
                            .getSelectedItem()));
                    paramas.put("INSURER_NO", insurerNoField.getText());
                    paramas.put("JIGYOUSHA_NO", jigyoushoNoField.getText()); // DB:JIGYOUSHA,
                                                                                // 画面:事業所
                    closeWindow();
                }
            }
        });

        // 閉じるボタン
        close.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (canClose()) {
                    // パラメータを渡し用HashMapに格納する
                    paramas.put("ACT", "close");
                    paramas.put("INSURER_NM", "");
                    paramas.put("INSURER_NO", "");
                    paramas.put("JIGYOUSHA_NO", "");
                    closeWindow();
                }
            }
        });
    }

    /**
     * 起動時に取得したデータの中から、insurerNmからinsurerNoを取得します。
     * 
     * @param nm insurerNm
     * @return insurerNo
     */
    private String findInsurerNo(String nm) {
        for (int i = 0; i < insurerData.getDataSize(); i++) {
            VRMap hash = (VRMap) insurerData.getData(i);
            String nmTmp = String.valueOf(hash.getData("INSURER_NM"));
            if (nm.equals(nmTmp)) {
                return String.valueOf(hash.getData("INSURER_NO"));
            }
        }
        return "";
    }

    protected void processWindowEvent(WindowEvent e) {
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            try {
                FocusManager fm = FocusManager.getCurrentManager();
                if (fm != null) {
                    Component cmp = fm.getFocusOwner();
                    if (cmp != null) {
                        // フォーカスロスト前に×ボタンで閉じる場合を想定し、擬似的にFocusLostを発行する
                        cmp.dispatchEvent(new FocusEvent(cmp,
                                FocusEvent.FOCUS_LOST));
                    }
                }
                if (canClose()) {
                    // パラメータを渡し用HashMapに格納する
                    paramas.put("ACT", "close");
                    paramas.put("INSURER_NM", "");
                    paramas.put("INSURER_NO", "");
                    paramas.put("JIGYOUSHA_NO", "");
                    closeWindow();
                } else {
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            super.processWindowEvent(e);
            closeWindow();
        }
        super.processWindowEvent(e);
    }

    protected void closeWindow() {
        // 自身を破棄する
        this.dispose();
    }

    public void setParams(VRMap params) {
        this.paramas = params;
    }

    public VRMap getParams() {
        return paramas;
    }

    /**
     * 登録処理が可能かどうかをチェックします。
     * 
     * @return true:OK, false:NG
     */
    private boolean canSubmit() {
        // 保険者番号・未選択チェック
        if (insurerNm.getSelectedIndex() < 0) {
            insurerNm.requestFocus();
            ACMessageBox.show("保険者を選択してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }

        // 事業所番号・未入力チェック
        if (IkenshoCommon.isNullText(jigyoushoNoField.getText())) {
            jigyoushoNoField.requestFocus();
            ACMessageBox.show("事業所番号を入力してください。", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            return false;
        }

        // 保険者重複チェック
        boolean checkFlg = true;
        if (isUpdate) {
            if (insurerNoOld.equals(insurerNoField.getText())) {
                // 更新であり、保険者に変更が無い場合はチェックしない
                checkFlg = false;
            }
        }
        if (checkFlg) {
            String insurerNoNew = insurerNoField.getText();
            VRArrayList data = (VRArrayList) affair.getData("DATA");
            for (int i = 0; i < data.getDataSize(); i++) {
                VRMap tmp = (VRMap) data.getData(i);
                String dataInsurerNo = tmp.getData("INSURER_NO").toString();
                if (dataInsurerNo.equals(insurerNoNew)) {
                    insurerNm.requestFocus();
                    ACMessageBox.show("同一の保険者が既に登録されています。",
                            ACMessageBox.BUTTON_OK,
                            ACMessageBox.ICON_EXCLAMATION);
                    return false;
                }
            }
        }

        // 事業所番号・桁数チェック
        if (jigyoushoNoField.getText().length() < 10) {
            jigyoushoNoField.requestFocus();
            int result = ACMessageBox.show("事業所番号が数字10桁入力されていません。\nよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_CANCEL) {
                return false;
            }
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
}
