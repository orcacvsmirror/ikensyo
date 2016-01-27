package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.im.InputSubset;
import java.text.Format;

import javax.swing.JScrollPane;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACRowMaximumableTextArea;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.component.IkenshoZipTextField;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoRenkeiIJyouhouShousai extends IkenshoAffairContainer
        implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel client = new VRPanel();
    private ACLabelContainer drNmContainer = new ACLabelContainer();
    private ACTextField drNm = new ACTextField();
    private ACLabelContainer sinryoukaContainer = new ACLabelContainer();
    private ACLabelContainer sinryoukaPanel = new ACLabelContainer();
    private ACTextField sinryouka = new ACTextField();
    private VRLabel sinryoukaNote = new VRLabel();
    private ACLabelContainer nmContainer = new ACLabelContainer();
    private ACTextField nm = new ACTextField();
    private ACLabelContainer postCdContainer = new ACLabelContainer();
    private IkenshoZipTextField postCd = new IkenshoZipTextField();
    private ACLabelContainer addrContainer = new ACLabelContainer();
    private ACTextField addr = new ACTextField();
    private ACLabelContainer telContainer = new ACLabelContainer();
    private IkenshoTelTextField tel = new IkenshoTelTextField();
    private ACLabelContainer faxContainer = new ACLabelContainer();
    private IkenshoTelTextField fax = new IkenshoTelTextField();
    private ACLabelContainer celTelContainer = new ACLabelContainer();
    private IkenshoTelTextField celTel = new IkenshoTelTextField();
    private ACLabelContainer kinkyuRenrakuContainer = new ACLabelContainer();
    private ACTextField kinkyuRenraku = new ACTextField();
    private ACLabelContainer fuzaijiTaiouContainer = new ACLabelContainer();
    private ACTextField fuzaijiTaiou = new ACTextField();
    private ACLabelContainer bikouContainer = new ACLabelContainer();
    private JScrollPane bikouScrPane = new JScrollPane();
    private ACRowMaximumableTextArea bikou = new ACRowMaximumableTextArea();
    private VRLabel blank1 = new VRLabel();
    private VRLabel blank2 = new VRLabel();
    private VRLabel blank3 = new VRLabel();
    private VRLabel blank4 = new VRLabel();

    private VRMap renkeiiData;
    private String renkeiiCd; // 連携医コード
    private boolean isUpdate; // true : 更新, false : 追加
    private boolean hasData; // true : 有, false : 無
    protected VRMap prevData; // 前画面キャッシュ

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "RENKEII", new String[] { "RENKEII_CD" }, new Format[] { null },
            "LAST_TIME", "LAST_TIME");

    /**
     * コンストラクタです。
     */
    public IkenshoRenkeiIJyouhouShousai() {
        try {
            jbInit();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        buttons.setTitle("連携医情報詳細");
        buttons.add(update, VRLayout.EAST);

        // メイン
        VRLayout ll = new VRLayout();
        ll.setFitHLast(true);
        ll.setFitVLast(true);
        client.setLayout(ll);
        client.add(drNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(sinryoukaPanel, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);
        client.add(blank1, VRLayout.FLOW_RETURN);
        client.add(nmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(postCdContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank2, VRLayout.FLOW_RETURN);
        client.add(addrContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(telContainer, VRLayout.FLOW_INSETLINE);
        client.add(faxContainer, VRLayout.FLOW);
        client.add(blank3, VRLayout.FLOW_RETURN);
        client.add(celTelContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank4, VRLayout.FLOW_RETURN);
        client.add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(bikouContainer, VRLayout.FLOW_INSETLINE_RETURN);

        // 登録ボタン
//        update.setText("登録(S)");
        update.setText("　登録(S)　");
        update.setMnemonic('S');
        update.setActionCommand("登録(S)");
        update.setToolTipText("現在の内容を登録します。");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        // 医師氏名
        drNmContainer.add(drNm);
        drNmContainer.setText("医師氏名");
        drNm.setColumns(15);
        drNm.setMaxLength(15);
        drNm.setBindPath("DR_NM");
        drNm.setIMEMode(InputSubset.KANJI);

        // 診療科
        sinryoukaPanel.add(sinryoukaContainer);
        sinryoukaPanel.add(sinryoukaNote);
        sinryoukaPanel.setLabelFilled(true);
        sinryoukaPanel.setContentAreaFilled(true);
        sinryoukaPanel.setBackground(new Color(0x99cc99));
        sinryoukaPanel.setFocusBackground(new Color(0x99e099));

        sinryoukaContainer.add(sinryouka);
        sinryoukaContainer.setText("診療科名");
        sinryouka.setColumns(10);
        sinryouka.setMaxLength(10);
        sinryouka.setBindPath("SINRYOUKA");
        sinryouka.setIMEMode(InputSubset.KANJI);

        sinryoukaNote.setText("「主治医意見書」「医師医見書」に印刷される項目");
        sinryoukaNote.setForeground(new Color(0x003300));

        // 医療機関名
        nmContainer.add(nm);
        nmContainer.setText("医療機関名");
        nm.setColumns(30);
        nm.setMaxLength(30);
        nm.setBindPath("MI_NM");
        nm.setIMEMode(InputSubset.KANJI);

        // 郵便番号
        postCdContainer.add(postCd);
        postCdContainer.setText("郵便番号");
        postCd.setAddressTextField(addr);
        postCd.setBindPath("MI_POST_CD");

        // 所在地
        addrContainer.add(addr);
        addrContainer.setText("所在地");
        addr.setColumns(45);
        addr.setMaxLength(45);
        addr.setBindPath("MI_ADDRESS");
        addr.setIMEMode(InputSubset.KANJI);

        // 連絡先(TEL)
        telContainer.add(tel);
        telContainer.setText("連絡先(TEL)");
        tel.setBindPath("MI_TEL1", "MI_TEL2");

        // 連絡先(FAX)
        faxContainer.add(fax);
        faxContainer.setText("連絡先(FAX)");
        fax.setBindPath("MI_FAX1", "MI_FAX2");

        // 連絡先(携帯)
        celTelContainer.add(celTel);
        celTelContainer.setText("連絡先(携帯)");
        celTel.setBindPath("MI_CEL_TEL1", "MI_CEL_TEL2");

        // 緊急時連絡先
        kinkyuRenrakuContainer.add(kinkyuRenraku);
        kinkyuRenrakuContainer.setText("緊急時連絡先");
        kinkyuRenraku.setColumns(40);
        kinkyuRenraku.setMaxLength(40);
        kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
        kinkyuRenraku.setIMEMode(InputSubset.KANJI);

        // 不在時対応法
        fuzaijiTaiouContainer.add(fuzaijiTaiou);
        fuzaijiTaiouContainer.setText("不在時対応法");
        fuzaijiTaiou.setColumns(40);
        fuzaijiTaiou.setMaxLength(40);
        fuzaijiTaiou.setBindPath("FUZAIJI_TAIOU");
        fuzaijiTaiou.setIMEMode(InputSubset.KANJI);

        bikouContainer.setLayout(new BorderLayout());
        bikouScrPane.getViewport().add(bikou);
        bikouContainer.add(bikouScrPane, java.awt.BorderLayout.CENTER);
        bikouContainer.setText("備考");
        bikou.setBindPath("BIKOU");
        bikou.setIMEMode(InputSubset.KANJI);
        bikou.setMaxLength(200);
        bikou.setLineWrap(true);

    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // スナップショット対象設定
        IkenshoSnapshot.getInstance().setRootContainer(client);
        // メニューバーのボタンに対応するトリガーの設定
        addUpdateTrigger(update);

        // 前画面から引継ぎのデータを取得・設定
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            if (VRBindPathParser.has("PREV_DATA", params)) {
                prevData = (VRMap) VRBindPathParser
                        .get("PREV_DATA", params);
            }

            // 前画面にて、どのボタンが押されての遷移かを取得
            String act = String.valueOf(params.get("ACT"));
            if (act.equals("insert")) {
                isUpdate = false;
                hasData = false;
            } else if (act.equals("copy")) {
                isUpdate = false;
                hasData = true;
            } else if (act.equals("detail")) {
                isUpdate = true;
                hasData = true;
            }

            // ボタンのキャプション
            if (isUpdate) {
                update.setText("更新(S)");
                update.setToolTipText("現在の内容を更新します。");
            } else {
                update.setText("登録(S)");
                update.setToolTipText("現在の内容を登録します。");
            }

            // 渡り時の情報郡からRENKEII_CDを取得
            if (hasData) {
                renkeiiCd = String.valueOf(VRBindPathParser.get("RENKEII_CD",
                        params));
            }

            // 入力欄にデータを設定
            doSelect();
        } else {
            throw new Exception("不正なパラメータが渡されました。");
        }
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return drNm;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        String key = "RENKEII_CD";
        String value = renkeiiCd; // (UPDATE前の値)

        // 前画面キャッシュを再設定
        if (prevData != null) {
            parameters.put("PREV_DATA", prevData);
        }

        if (IkenshoSnapshot.getInstance().isModified()) {
            // MSG, Captionの設定
            String msg;
            String btnCaption;
            if (isUpdate) {
                msg = "変更されています。保存しますか？";
                btnCaption = "更新して戻る(S)";
            } else {
                msg = "登録内容を保存しますか？";
                btnCaption = "登録して戻る(S)";
            }
            int result = ACMessageBox.showYesNoCancel(msg, btnCaption, 'S',
                    "破棄して戻る(R)", 'R');

            // DLG処理
            if (result == ACMessageBox.RESULT_YES) { // 保存して戻る
                boolean updateFlg = doUpdate(); // UPDATE成功:true, 失敗:false
                parameters.put(key, renkeiiCd); // 値はUPDATE後のものでなければならない
                return updateFlg;
            } else if (result == ACMessageBox.RESULT_NO) { // 保存しないで戻る
                if (!isNullText(value)) {
                    parameters.put(key, value);
                }
                return true;
            } else { // 戻らない
                return false;
            }
        } else { // 戻る
            if (!isNullText(value)) {
                parameters.put(key, value);
            }
            return true;
        }
    }

    public boolean canClose() throws Exception {
        if (IkenshoSnapshot.getInstance().isModified()) {
            String msg = "";
            msg = "更新されています。\n終了してもよろしいですか？";
            int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            } else {
                return false;
            }
        }
        return true;
    }

    protected void updateActionPerformed(ActionEvent e) throws Exception {
        if (doUpdate()) { // update成功時、一覧画面に戻る
            ACFrame.getInstance().back();
        }
    }

    /**
     * データ取得処理を行います
     * 
     * @throws Exception
     */
    private void doSelect() throws Exception {
        // スナップショット撮影(コピー時用)
        IkenshoSnapshot.getInstance().snapshot();

        // DBからデータを取得
        doSelectFromDB();

        // コピー時以外は、改めてスナップショットを撮りなおす
        if (isUpdate) {
            IkenshoSnapshot.getInstance().snapshot();
        } else if (!hasData) {
            IkenshoSnapshot.getInstance().snapshot();
        }

        // ステータスバー
        setStatusText("連携医情報詳細");

    }

    /**
     * DBからデータを取得します。
     * 
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        VRArrayList renkeiiArray = null;
        if (hasData) {
            // キーを元に、DBからデータを取得
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append(" SELECT ");
            sb.append(" RENKEII_CD,");
            sb.append(" DR_NM,");
            sb.append(" SINRYOUKA,");
            sb.append(" MI_NM,");
            sb.append(" MI_POST_CD,");
            sb.append(" MI_ADDRESS,");
            sb.append(" MI_TEL1,");
            sb.append(" MI_TEL2,");
            sb.append(" MI_FAX1,");
            sb.append(" MI_FAX2,");
            sb.append(" MI_CEL_TEL1,");
            sb.append(" MI_CEL_TEL2,");
            sb.append(" KINKYU_RENRAKU,");
            sb.append(" FUZAIJI_TAIOU,");
            sb.append(" BIKOU,");
            sb.append(" LAST_TIME");
            sb.append(" FROM");
            sb.append(" RENKEII");
            sb.append(" WHERE");
            sb.append(" RENKEII_CD=" + renkeiiCd);

            renkeiiArray = (VRArrayList) dbm.executeQuery(sb.toString());

            if (renkeiiArray.getDataSize() > 0) { // DB上にデータ有
                renkeiiData = (VRMap) renkeiiArray.getData();
                // パッシブチェック用
                clearReservedPassive();
                reservedPassive(PASSIVE_CHECK_KEY, renkeiiArray);
            } else {
                renkeiiData = (VRMap) client.createSource(); // DB上にデータ無
            }
        } else { // 渡りデータ無
            renkeiiData = (VRMap) client.createSource();
        }
        client.setSource(renkeiiData);
        client.bindSource();
    }

    /**
     * DB更新
     */
    private boolean doUpdate() throws Exception {
        // 入力チェック
        if (isValidInput()) {
            // DBUPDATE
            if (!doUpdateToDB()) {
                return false;
            }

            // 後処理(「新規」、及び「複製」モードで登録したら、「更新」モードへ移行する)
            String msg = new String();
            if (isUpdate) {
                msg = "更新されました。";
            } else {
                msg = "登録されました。";
                isUpdate = true;
                hasData = true;
                update.setText("更新(S)");
                update.setToolTipText("現在の内容を更新します。");
            }

            // スナップショット撮影
            IkenshoSnapshot.getInstance().snapshot();

            // 処理完了通知Msg表示
            ACMessageBox.show(msg, ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
            return true;
        }
        return false;
    }

    /**
     * DBに対して更新を行う。
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        // update
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        try {
            // 入力データを取得
            client.applySource();

            // パッシブチェック / トランザクション開始
            if (isUpdate) {
                // 更新時
                clearPassiveTask();
                addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox
                            .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    return false;
                }
            } else {
                // 追加時
                dbm.beginTransaction(); // トランザクション開始
            }

            // SQL文を生成 + 完了通知Msg設定
            StringBuffer sb = new StringBuffer();
            if (isUpdate) {
                // 更新時
                sb.append(" UPDATE");
                sb.append(" RENKEII");
                sb.append(" SET");
                sb.append(" DR_NM=" + getDBSafeString("DR_NM", renkeiiData));
                sb.append(" ,SINRYOUKA="
                        + getDBSafeString("SINRYOUKA", renkeiiData));
                sb.append(" ,MI_NM=" + getDBSafeString("MI_NM", renkeiiData));
                sb.append(" ,MI_POST_CD="
                        + getDBSafeString("MI_POST_CD", renkeiiData));
                sb.append(" ,MI_ADDRESS="
                        + getDBSafeString("MI_ADDRESS", renkeiiData));
                sb.append(" ,MI_TEL1="
                        + getDBSafeString("MI_TEL1", renkeiiData));
                sb.append(" ,MI_TEL2="
                        + getDBSafeString("MI_TEL2", renkeiiData));
                sb.append(" ,MI_FAX1="
                        + getDBSafeString("MI_FAX1", renkeiiData));
                sb.append(" ,MI_FAX2="
                        + getDBSafeString("MI_FAX2", renkeiiData));
                sb.append(" ,MI_CEL_TEL1="
                        + getDBSafeString("MI_CEL_TEL1", renkeiiData));
                sb.append(" ,MI_CEL_TEL2="
                        + getDBSafeString("MI_CEL_TEL2", renkeiiData));
                sb.append(" ,KINKYU_RENRAKU="
                        + getDBSafeString("KINKYU_RENRAKU", renkeiiData));
                sb.append(" ,FUZAIJI_TAIOU="
                        + getDBSafeString("FUZAIJI_TAIOU", renkeiiData));
                sb.append(" ,BIKOU=" + getDBSafeString("BIKOU", renkeiiData));
                sb.append(" ,LAST_TIME=CURRENT_TIMESTAMP");
                sb.append(" WHERE");
                sb.append(" RENKEII_CD=" + renkeiiCd);
            } else {
                // 追加時
                sb.append("INSERT INTO");
                sb.append(" RENKEII");
                sb.append(" (");
                sb.append(" DR_NM,");
                sb.append(" SINRYOUKA,");
                sb.append(" MI_NM,");
                sb.append(" MI_POST_CD,");
                sb.append(" MI_ADDRESS,");
                sb.append(" MI_TEL1,");
                sb.append(" MI_TEL2,");
                sb.append(" MI_FAX1,");
                sb.append(" MI_FAX2,");
                sb.append(" MI_CEL_TEL1,");
                sb.append(" MI_CEL_TEL2,");
                sb.append(" KINKYU_RENRAKU,");
                sb.append(" FUZAIJI_TAIOU,");
                sb.append(" BIKOU,");
                sb.append(" LAST_TIME");
                sb.append(" )");
                sb.append(" VALUES(");
                sb.append(" " + getDBSafeString("DR_NM", renkeiiData));
                sb.append("," + getDBSafeString("SINRYOUKA", renkeiiData));
                sb.append("," + getDBSafeString("MI_NM", renkeiiData));
                sb.append("," + getDBSafeString("MI_POST_CD", renkeiiData));
                sb.append("," + getDBSafeString("MI_ADDRESS", renkeiiData));
                sb.append("," + getDBSafeString("MI_TEL1", renkeiiData));
                sb.append("," + getDBSafeString("MI_TEL2", renkeiiData));
                sb.append("," + getDBSafeString("MI_FAX1", renkeiiData));
                sb.append("," + getDBSafeString("MI_FAX2", renkeiiData));
                sb.append("," + getDBSafeString("MI_CEL_TEL1", renkeiiData));
                sb.append("," + getDBSafeString("MI_CEL_TEL2", renkeiiData));
                sb.append("," + getDBSafeString("KINKYU_RENRAKU", renkeiiData));
                sb.append("," + getDBSafeString("FUZAIJI_TAIOU", renkeiiData));
                sb.append("," + getDBSafeString("BIKOU", renkeiiData));
                sb.append(",CURRENT_TIMESTAMP");
                sb.append(" )");
            }

            // 更新用SQL実行
            dbm.executeUpdate(sb.toString());

            // 「新規」「複製」の場合、今追加したデータの連携医CDを取得しておく
            if (!isUpdate) {
                // 連携医CD(オートナンバー)取得用のSQL文
                String sql = "SELECT GEN_ID(GEN_RENKEII, 0) FROM rdb$database";

                // 連携医CD取得用SQL実行
                VRArrayList tmpArray = (VRArrayList) dbm.executeQuery(sql);

                // 連携医CDを取得
                renkeiiCd = ((VRMap) tmpArray.get(0)).get("GEN_ID").toString();
            }

            // コミット
            dbm.commitTransaction();
        } catch (Exception ex) {
            // ロールバック
            dbm.rollbackTransaction();
            throw new Exception(ex);
        }
        return true;
    }

    /**
     * SQL内の値用の文字列を生成します。
     * 
     * @param columnName 列名
     * @return String
     * @throws Exception
     */
    private String createSqlParam(String columnName) throws Exception {
        return String.valueOf(VRBindPathParser.get(columnName, renkeiiData))
                .replaceAll("'", "''");
    }

    /**
     * 入力チェック制御
     */
    private boolean isValidInput() {
        boolean valid = true;
        Component target = null;
        String msg = "";

        // エラーチェック
        if (isNullText(drNm.getText())) {
            valid = false;
            target = drNm;
            msg = "氏名を入力してください。";
        }

        // エラーダイアログ表示
        if (!valid) {
            ACMessageBox.show(msg, ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            target.requestFocus();
            return false;
        }
        return true;
    }
}
