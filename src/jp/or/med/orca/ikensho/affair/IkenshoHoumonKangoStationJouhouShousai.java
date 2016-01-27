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
public class IkenshoHoumonKangoStationJouhouShousai extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel client = new VRPanel();
    private ACLabelContainer drNmContainer = new ACLabelContainer();
    private ACTextField drNm = new ACTextField();
    private ACLabelContainer coloredPanel = new ACLabelContainer();
    private VRLabel coloredLabel = new VRLabel();
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
    private VRLabel blank5 = new VRLabel();

    private VRMap stationData;
    private String stationCd; //ステーションコード
    private boolean isUpdate; //true : 更新, false : 追加
    private boolean hasData; //true : 有, false : 無

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new
        ACPassiveKey("STATION", new String[] {"STATION_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");

    /**
     * コンストラクタです。
     */
    public IkenshoHoumonKangoStationJouhouShousai() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        buttons.setTitle("訪問看護ステーション情報詳細");
        buttons.add(update, VRLayout.EAST);

        //メイン
        VRLayout ll = new VRLayout();
        ll.setFitHLast(true);
        ll.setFitVLast(true);
        ll.setHgrid(100);
        client.setLayout(ll);
        client.add(drNmContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank1, VRLayout.FLOW_RETURN);
        client.add(coloredPanel, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);
        client.add(blank2, VRLayout.FLOW_RETURN);
        client.add(postCdContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank3, VRLayout.FLOW_RETURN);
        client.add(addrContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(telContainer, VRLayout.FLOW_INSETLINE);
        client.add(faxContainer, VRLayout.FLOW);
        client.add(blank4, VRLayout.FLOW_RETURN);
        client.add(celTelContainer, VRLayout.FLOW_INSETLINE);
        client.add(blank5, VRLayout.FLOW_RETURN);
        client.add(kinkyuRenrakuContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(fuzaijiTaiouContainer, VRLayout.FLOW_INSETLINE_RETURN);
        client.add(bikouContainer, VRLayout.FLOW_INSETLINE_RETURN);

        //登録ボタン
//       update.setText("登録(S)");
       update.setText("　登録(S)　");
       update.setMnemonic('S');
       update.setActionCommand("登録(S)");
       update.setToolTipText("現在の内容を登録します。");
       update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

       //代表者名
       drNmContainer.add(drNm);
       drNmContainer.setText("代表者名");
       drNm.setColumns(15);
       drNm.setMaxLength(15);
       drNm.setBindPath("DR_NM");
       drNm.setIMEMode(InputSubset.KANJI);

       //ステーション名
       coloredPanel.add(nmContainer, VRLayout.FLOW_INSETLINE);
       coloredPanel.add(coloredLabel);
       coloredPanel.setLabelFilled(true);
       coloredPanel.setContentAreaFilled(true);
       coloredPanel.setBackground(new Color(0x99cc99));
       coloredPanel.setFocusBackground(new Color(0x99e099));

       nmContainer.add(nm);
       nmContainer.setText("ステーション名");
       nm.setColumns(30);
       nm.setMaxLength(30);
       nm.setBindPath("MI_NM");
       nm.setIMEMode(InputSubset.KANJI);

       // [ID:0000514][Masahiko Higuchi] 2009/09/25 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能
       coloredLabel.setText("「（特別）訪問看護指示書」に印刷される項目");
       // [ID:0000514][Masahiko Higuchi] 2009/09/25 replace end
       coloredLabel.setForeground(new Color(0x003300));

       //郵便番号
       postCdContainer.add(postCd);
       postCdContainer.setText("郵便番号");
       postCd.setAddressTextField(addr);
       postCd.setBindPath("MI_POST_CD");

       //所在地
       addrContainer.add(addr);
       addrContainer.setText("所在地");
       addr.setColumns(45);
       addr.setMaxLength(45);
       addr.setBindPath("MI_ADDRESS");
       addr.setIMEMode(InputSubset.KANJI);

       //連絡先(TEL)
       telContainer.add(tel);
       telContainer.setText("連絡先(TEL)");
       tel.setBindPath("MI_TEL1", "MI_TEL2");

       //連絡先(FAX)
       faxContainer.add(fax);
       faxContainer.setText("連絡先(FAX)");
       fax.setBindPath("MI_FAX1", "MI_FAX2");

       //連絡先(携帯)
       celTelContainer.add(celTel);
       celTelContainer.setText("連絡先(携帯)");
       celTel.setBindPath("MI_CEL_TEL1", "MI_CEL_TEL2");

       //緊急時連絡先
       kinkyuRenrakuContainer.add(kinkyuRenraku);
       kinkyuRenrakuContainer.setText("緊急時連絡先");
       kinkyuRenraku.setColumns(40);
       kinkyuRenraku.setMaxLength(40);
       kinkyuRenraku.setBindPath("KINKYU_RENRAKU");
       kinkyuRenraku.setIMEMode(InputSubset.KANJI);

       //不在時対応法
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
        //スナップショット対象設定
        IkenshoSnapshot.getInstance().setRootContainer(client);
        //メニューバーのボタンに対応するトリガーの設定
        addUpdateTrigger(update);

        //前画面から引継ぎのデータを取得・設定
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            //前画面にて、どのボタンが押されての遷移かを取得
            String act = String.valueOf(params.get("ACT"));
            if (act.equals("insert")) {
                isUpdate = false;
                hasData = false;
            }
            else if (act.equals("copy")) {
                isUpdate = false;
                hasData = true;
            }
            else if (act.equals("detail")) {
                isUpdate = true;
                hasData = true;
            }

            //ボタンのキャプション
            if (isUpdate) {
                update.setText("更新(S)");
            }
            else {
                update.setText("登録(S)");
            }

            //渡り時の情報郡からSTATION_CDを取得
            if (hasData) {
                stationCd = String.valueOf(VRBindPathParser.get("STATION_CD", params));
            }

            //入力欄にデータを設定
            doSelect();
        }
        else {
            throw new Exception("不正なパラメータが渡されました。");
        }
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return drNm;
    }

   public boolean canBack(VRMap parameters) throws Exception{
       String key = "STATION_CD";
       String value = stationCd;

       if (IkenshoSnapshot.getInstance().isModified()) {
           //MSG, Captionの設定
           String msg;
           String btnCaption;
           if (isUpdate) {
               msg = "変更されています。保存しますか？";
               btnCaption = "更新して戻る(S)";
           }
           else {
               msg = "登録内容を保存しますか？";
               btnCaption = "登録して戻る(S)";
           }
           int result = ACMessageBox.showYesNoCancel(msg,
               btnCaption, 'S',
               "破棄して戻る(R)", 'R');

           //DLG処理
           if (result == ACMessageBox.RESULT_YES) { //保存して戻る
               boolean updateFlg = doUpdate(); //DB更新成功:true, 失敗:false
               parameters.put(key, stationCd);
               return updateFlg;
           }
           else if (result == ACMessageBox.RESULT_NO) { //保存しないで戻る
               if (!isNullText(value)) {
                   parameters.put(key, value);
               }
               return true;
           }
           else { //戻らない
               return false;
           }
       }
       else { //戻る
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
           int result = ACMessageBox.show(msg,
                                               ACMessageBox.
                                               BUTTON_OKCANCEL,
                                               ACMessageBox.ICON_QUESTION,
                                               ACMessageBox.FOCUS_CANCEL);
           if (result == ACMessageBox.RESULT_OK) {
               return true;
           }
           else {
               return false;
           }
       }
       return true;
   }

   protected void updateActionPerformed(ActionEvent e) throws Exception {
       if (doUpdate()) { //update成功時、一覧画面に戻る
           ACFrame.getInstance().back();
       }
   }

   /**
    * データ取得処理を行います
    * @throws Exception
    */
   private void doSelect() throws Exception {
       //スナップショット撮影(コピー時用)
       IkenshoSnapshot.getInstance().snapshot();

       //DBからデータを取得
       doSelectFromDB();

       //コピー時以外は、改めてスナップショットを撮りなおす
       if (isUpdate) {
           IkenshoSnapshot.getInstance().snapshot();
       }
       else if(!hasData) {
           IkenshoSnapshot.getInstance().snapshot();
       }

       //ステータスバー
       setStatusText("訪問看護ステーション情報詳細");
   }

   /**
    * DBからデータを取得する
    * @throws Exception
    */
   private void doSelectFromDB() throws Exception {
       VRArrayList stationArray = null;
       if (hasData) {
           //キーを元に、DBからデータを取得
           IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
           StringBuffer sb = new StringBuffer();
           sb.append(" SELECT ");
           sb.append(" STATION_CD,");
           sb.append(" DR_NM,");
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
           sb.append(" STATION");
           sb.append(" WHERE");
           sb.append(" STATION_CD=" + stationCd);

           stationArray = (VRArrayList) dbm.executeQuery(sb.toString());

           if (stationArray.getDataSize() > 0) { //DB上にデータ有
               stationData = (VRMap) stationArray.getData();
               //パッシブチェック用
               clearReservedPassive();
               reservedPassive(PASSIVE_CHECK_KEY, stationArray);
           }
           else {
               stationData = (VRMap) client.createSource(); //DB上にデータ無
           }
       }
       else { //渡りデータ無
           stationData = (VRMap) client.createSource();
       }
       client.setSource(stationData);
       client.bindSource();
   }

   /**
    * 更新処理を行います。
    */
   private boolean doUpdate() throws Exception{
       //入力チェック
       if (isValidInput()) {
           //DBUPDATE
           if (!doUpdateToDB()) {
               return false;
           }

           //後処理(「新規」、及び「複製」モードで登録したら、「更新」モードへ移行する)
           String msg = "";
           if (isUpdate) {
               msg = "更新されました。";
           }
          else {
              msg = "登録されました。";
               isUpdate = true;
               hasData = true;
               update.setText("更新(S)");
           }

           //スナップショット撮影
           IkenshoSnapshot.getInstance().snapshot();

           //処理完了通知Msg表示
           ACMessageBox.show(msg,
                                  ACMessageBox.BUTTON_OK,
                                  ACMessageBox.ICON_INFOMATION);
           return true;
       }
       return false;
   }

   /**
    * DBに対してUPDATEを行います。
    * @return boolean
    * @throws Exception
    */
   private boolean doUpdateToDB() throws Exception {
       //update
       IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
       try {
           //入力データを取得
           client.applySource();

           //パッシブチェック / トランザクション開始
           if (isUpdate) {
               //更新時
               clearPassiveTask();
               addPassiveUpdateTask(PASSIVE_CHECK_KEY, 0);
               dbm = getPassiveCheckedDBManager();
               if (dbm == null) {
                   ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                   return false;
               }
           }
           else {
               //追加時
               dbm.beginTransaction(); //トランザクション開始
           }

           //SQL文を生成 + 完了通知Msg設定
           StringBuffer sb = new StringBuffer();
           if (isUpdate) {
               //更新時
               sb.append(" UPDATE");
               sb.append(" STATION");
               sb.append(" SET");
               sb.append(" DR_NM=" + getDBSafeString("DR_NM", stationData));
               sb.append(" ,MI_NM=" + getDBSafeString("MI_NM", stationData));
               sb.append(" ,MI_POST_CD=" + getDBSafeString("MI_POST_CD", stationData));
               sb.append(" ,MI_ADDRESS=" + getDBSafeString("MI_ADDRESS", stationData));
               sb.append(" ,MI_TEL1=" + getDBSafeString("MI_TEL1", stationData));
               sb.append(" ,MI_TEL2=" + getDBSafeString("MI_TEL2", stationData));
               sb.append(" ,MI_FAX1=" + getDBSafeString("MI_FAX1", stationData));
               sb.append(" ,MI_FAX2=" + getDBSafeString("MI_FAX2", stationData));
               sb.append(" ,MI_CEL_TEL1=" + getDBSafeString("MI_CEL_TEL1", stationData));
               sb.append(" ,MI_CEL_TEL2=" + getDBSafeString("MI_CEL_TEL2", stationData));
               sb.append(" ,KINKYU_RENRAKU=" + getDBSafeString("KINKYU_RENRAKU", stationData));
               sb.append(" ,FUZAIJI_TAIOU=" + getDBSafeString("FUZAIJI_TAIOU", stationData));
               sb.append(" ,BIKOU=" + getDBSafeString("BIKOU", stationData));
               sb.append(" ,LAST_TIME=CURRENT_TIMESTAMP");
               sb.append(" WHERE");
               sb.append(" STATION_CD=" + stationCd);
           }
           else {
               //追加時
               sb.append("INSERT INTO");
               sb.append(" STATION");
               sb.append(" (");
               sb.append(" DR_NM,");
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
               sb.append(" " + getDBSafeString("DR_NM", stationData));
               sb.append("," + getDBSafeString("MI_NM", stationData));
               sb.append("," + getDBSafeString("MI_POST_CD", stationData));
               sb.append("," + getDBSafeString("MI_ADDRESS", stationData));
               sb.append("," + getDBSafeString("MI_TEL1", stationData));
               sb.append("," + getDBSafeString("MI_TEL2", stationData));
               sb.append("," + getDBSafeString("MI_FAX1", stationData));
               sb.append("," + getDBSafeString("MI_FAX2", stationData));
               sb.append("," + getDBSafeString("MI_CEL_TEL1", stationData));
               sb.append("," + getDBSafeString("MI_CEL_TEL2", stationData));
               sb.append("," + getDBSafeString("KINKYU_RENRAKU", stationData));
               sb.append("," + getDBSafeString("FUZAIJI_TAIOU", stationData));
               sb.append("," + getDBSafeString("BIKOU", stationData));
               sb.append(",CURRENT_TIMESTAMP");
               sb.append(")");
           }

           //更新用SQL実行
           dbm.executeUpdate(sb.toString());

           //「新規」「複製」の場合、今追加したデータの連携医CDを取得しておく
           if (!isUpdate) {
               //連携医CD(オートナンバー)取得用のSQL文
               String sql = "SELECT GEN_ID(GEN_STATION, 0) FROM rdb$database";

               //連携医CD取得用SQL実行
               VRArrayList tmpArray = (VRArrayList) dbm.executeQuery(sql);

               //連携医CDを取得
               stationCd = ((VRMap) tmpArray.get(0)).get("GEN_ID").toString();
           }

           //コミット
           dbm.commitTransaction();
       }
       catch (Exception ex) {
           //ロールバック
           dbm.rollbackTransaction();
           throw new Exception(ex);
       }

       return true;
   }

   /**
    * 入力チェック制御
    */
   private boolean isValidInput() {
       boolean valid = true;
       Component target = null;
       String msg = "";

       //エラーチェック
       if (isNullText(nm.getText())) {
           valid = false;
           target = nm;
           msg = "訪問看護ステーション名を入力してください。";
       }

       //エラーダイアログ表示
       if (!valid) {
           ACMessageBox.show(msg,
                                  ACMessageBox.BUTTON_OK,
                                  ACMessageBox.ICON_EXCLAMATION);
           target.requestFocus();
           return false;
       }
       return true;
   }
}
