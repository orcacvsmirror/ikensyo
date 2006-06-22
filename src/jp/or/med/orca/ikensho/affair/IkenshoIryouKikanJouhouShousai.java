package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.text.Format;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JTabbedPane;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouShousai extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel tabPnl = new VRPanel();
    private JTabbedPane tabs = new JTabbedPane(JTabbedPane.TOP, JTabbedPane.SCROLL_TAB_LAYOUT);
    private IkenshoTabbableChildAffairContainer kihon = new IkenshoIryouKikanJouhouShousaiKihon();
    private IkenshoTabbableChildAffairContainer kanren = new IkenshoIryouKikanJouhouShousaiKanren();
    private ArrayList tabArray = new ArrayList();

    private VRMap doctorData;

    private String drCd; //連携医コード
    private boolean isUpdate; //true : 更新, false : 追加
    private boolean hasData; //true : 有, false : 無
    private int initJigyoushaCnt; //初期表示時の事業者数
    protected VRMap prevData; //前画面キャッシュ

    private static final ACPassiveKey PASSIVE_CHECK_KEY_DOCTOR = new
        ACPassiveKey("DOCTOR", new String[] {"DR_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_KEY_JIGYOUSHA = new
        ACPassiveKey("JIGYOUSHA", new String[] {"DR_CD", "INSURER_NO", "JIGYOUSHA_NO"}
                          , new Format[] {null, IkenshoConstants.FORMAT_PASSIVE_STRING, IkenshoConstants.FORMAT_PASSIVE_STRING}
                          , "LAST_TIME", "LAST_TIME");

    public IkenshoIryouKikanJouhouShousai() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

        //ArrayListに追加
        tabArray.add(kihon);
        tabArray.add(kanren);

    }

    private void jbInit() throws Exception {
        this.add(buttons, VRLayout.NORTH);
        this.add(tabPnl, VRLayout.CLIENT);

        buttons.setTitle("医療機関情報詳細");
        buttons.add(update, VRLayout.EAST);

        update.setText("登録(S)");
        update.setMnemonic('S');
        update.setActionCommand("登録(S)");
        update.setToolTipText("現在の内容を登録します。");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        tabPnl.setLayout(new BorderLayout());
        tabPnl.add(tabs, BorderLayout.CENTER);
        tabs.addTab("基本情報", kihon);
        tabs.addTab("請求書関連情報", kanren);
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        //スナップショット対象設定
        IkenshoSnapshot.getInstance().setRootContainer(tabPnl);
        //メニューバーのボタンに対応するトリガーの設定
        addUpdateTrigger(update);
        addTableSelectedTrigger(((IkenshoIryouKikanJouhouShousaiKanren) kanren).getTable());

        //前画面から引継ぎのデータを取得・設定
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            if (VRBindPathParser.has("PREV_DATA", params)) {
                prevData = (VRMap) VRBindPathParser.get("PREV_DATA", params);
            }

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
                update.setToolTipText("現在の内容を更新します。");
            }
            else {
                update.setText("登録(S)");
                update.setToolTipText("現在の内容を登録します。");
            }

            //渡り時の情報郡からDR_CDを取得
            if (hasData) {
                drCd = String.valueOf(VRBindPathParser.get("DR_CD", params));
            }

            //入力欄にデータを設定
            doSelect();

            //初期表示時の事業者数を退避
            initJigyoushaCnt = ((IkenshoIryouKikanJouhouShousaiKanren) kanren).getJigyoushoData().getDataSize();
        }
        else {
            throw new Exception("不正なパラメータが渡されました。");
        }
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return ((IkenshoIryouKikanJouhouShousaiKihon)kihon).getFirstFocusComponent();
    }

    public boolean canBack(VRMap parameters) throws Exception {
        String key = "DR_CD";
        String value = drCd; //(UPDATE前の値)

        //前画面キャッシュを再設定
        if (prevData != null) {
          parameters.put("PREV_DATA", prevData);
        }

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
                boolean updateFlg = doUpdate(); //UPDATE成功:true, 失敗:false
                parameters.put(key, drCd); //値はUPDATE後のものでなければならない
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

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        ((IkenshoIryouKikanJouhouShousaiKanren)kanren).setButtonsEnabled();
    }

    /**
     * データ取得処理を行います
     * @throws Exception
     */
    private void doSelect() throws Exception {
        //スナップショット撮影(コピー時用)
        IkenshoSnapshot.getInstance().snapshot();

        //DBからデータを取得
        doSelectFromDBAsDoctor();
        doSelectFromDBAsJigyousha();

        //コピー時以外は、改めてスナップショットを撮りなおす
        if (isUpdate) {
            IkenshoSnapshot.getInstance().snapshot();
        }
        else if(!hasData) {
            IkenshoSnapshot.getInstance().snapshot();
        }

        //ステータスバー
        setStatusText("医療機関情報詳細");
    }

    /**
     * DBからデータを取得します。
     * @throws Exception
     */
    private void doSelectFromDBAsDoctor() throws Exception {
        VRArrayList doctorArray = null;
        if (hasData) {
            //キーを元に、DBからデータを取得
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb.append( " SELECT" );
            sb.append( " DR_CD" );
            sb.append( ",DR_NM" );
            sb.append( ",MI_NM" );
            sb.append( ",MI_POST_CD" );
            sb.append( ",MI_ADDRESS" );
            sb.append( ",MI_TEL1" );
            sb.append( ",MI_TEL2" );
            sb.append( ",MI_FAX1" );
            sb.append( ",MI_FAX2" );
            sb.append( ",MI_CEL_TEL1" );
            sb.append( ",MI_CEL_TEL2" );
            sb.append( ",KINKYU_RENRAKU" );
            sb.append( ",FUZAIJI_TAIOU" );
            sb.append( ",BIKOU" );
            sb.append( ",JIGYOUSHA_NO" );
            sb.append( ",MI_DEFAULT" );
            sb.append( ",KAISETUSHA_NM" );
            sb.append( ",DR_NO" );
            sb.append( ",MI_KBN" );
            sb.append( ",BANK_NM" );
            sb.append( ",BANK_SITEN_NM" );
            sb.append( ",BANK_KOUZA_NO" );
            sb.append( ",BANK_KOUZA_KIND" );
            sb.append( ",FURIKOMI_MEIGI" );
            sb.append( ",LAST_TIME" );
            sb.append( " FROM" );
            sb.append( " DOCTOR" );
            sb.append( " WHERE" );
            sb.append( " DR_CD=" + drCd );

            doctorArray = (VRArrayList) dbm.executeQuery(sb.toString());

            if (doctorArray.getDataSize() > 0) { //DB上にデータ有
                doctorData = (VRMap)doctorArray.getData();
                doctorData.setData("TABLE_CHANGE_FLG", new Boolean(false));
                doctorData.setData("BANK_KOUZA_KIND",
                                   getBankKouzaKindPropertyValue(String.valueOf(doctorData.getData("BANK_KOUZA_KIND")))); //初期値が特殊なため。
                //パッシブチェック用
                clearReservedPassive();
                reservedPassive(PASSIVE_CHECK_KEY_DOCTOR, doctorArray);
            }
            else {
                doctorData = (VRMap)tabPnl.createSource(); //DB上にデータ無
            }
        }
        else { //渡りデータ無
            doctorData = (VRMap)tabPnl.createSource();
        }
        tabPnl.setSource(doctorData);
        tabPnl.bindSource();
    }

    /**
     * DBからデータを取得します。
     * @throws Exception
     */
    private void doSelectFromDBAsJigyousha() throws Exception {
        //キーとする医師コードを設定する。新規の場合は-1(ヒットしないキー)としておく
        String drCdTmp;
        if (hasData) {
            drCdTmp = drCd;
        }
        else {
            drCdTmp = "-1";
        }

        //テーブルにデータを設定する
        ((IkenshoIryouKikanJouhouShousaiKanren)kanren).doSelect(drCdTmp);

        VRArrayList jigyoushoData = ((IkenshoIryouKikanJouhouShousaiKanren)kanren).getJigyoushoData();
        initJigyoushaCnt = jigyoushoData.getDataSize();
        if (jigyoushoData.getDataSize() > 0) {
            //パッシブチェック
            reservedPassive(PASSIVE_CHECK_KEY_JIGYOUSHA, jigyoushoData);
        }
    }

    /**
     * 登録・更新処理
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdate() throws Exception {
        //入力チェック
        if (canControlUpdate()) {
            //DBUPDATE
            if (!doUpdateToDB()) {
                return false;
            }

            //後処理(「新規」、及び「複製」モードで登録したら、「更新」モードへ移行する)
            String msg = new String();
            if (isUpdate){
                msg = "更新されました。";
            }
            else {
                msg = "登録されました。";
                isUpdate = true;
                hasData = true;
                update.setText("更新(S)");
                update.setToolTipText("現在の内容を更新します。");
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
     * DBに対して更新を行う。
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdateToDB() throws Exception {
        //update
        String drCdOld = drCd;
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

        try {
            //入力データを取得
            tabPnl.applySource();

            //パッシブチェック
            if (isUpdate) {
                clearPassiveTask();
                addPassiveUpdateTask(PASSIVE_CHECK_KEY_DOCTOR, 0);
                for (int i=0; i<initJigyoushaCnt; i++) {
                    addPassiveDeleteTask(PASSIVE_CHECK_KEY_JIGYOUSHA, i);
                }

                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    return false;
                }
            }
            else {
                dbm.beginTransaction(); //トランザクション開始
            }

            //「通常使う医師」にチェックが入っている場合、すべての「通常使う医師」のフラグを取り消す
            if (getDBSafeNumber("MI_DEFAULT", doctorData).equals("1")) {
                dbm.executeUpdate("UPDATE DOCTOR SET MI_DEFAULT=0,LAST_TIME=CURRENT_TIMESTAMP");
            }

            //SQL文を生成 + 完了通知Msg設定
            StringBuffer sbDoctor = new StringBuffer();
            if (isUpdate) {
                //更新時
                sbDoctor.append(" UPDATE");
                sbDoctor.append(" DOCTOR");
                sbDoctor.append(" SET");
                sbDoctor.append(" DR_NM=" + getDBSafeString("DR_NM", doctorData));
                sbDoctor.append(",MI_NM=" + getDBSafeString("MI_NM", doctorData));
                sbDoctor.append(",MI_POST_CD=" + getDBSafeString("MI_POST_CD", doctorData));
                sbDoctor.append(",MI_ADDRESS=" + getDBSafeString("MI_ADDRESS", doctorData));
                sbDoctor.append(",MI_TEL1=" + getDBSafeString("MI_TEL1", doctorData));
                sbDoctor.append(",MI_TEL2=" + getDBSafeString("MI_TEL2", doctorData));
                sbDoctor.append(",MI_FAX1=" + getDBSafeString("MI_FAX1", doctorData));
                sbDoctor.append(",MI_FAX2=" + getDBSafeString("MI_FAX2", doctorData));
                sbDoctor.append(",MI_CEL_TEL1=" + getDBSafeString("MI_CEL_TEL1", doctorData));
                sbDoctor.append(",MI_CEL_TEL2=" + getDBSafeString("MI_CEL_TEL2", doctorData));
                sbDoctor.append(",KINKYU_RENRAKU=" + getDBSafeString("KINKYU_RENRAKU", doctorData));
                sbDoctor.append(",FUZAIJI_TAIOU=" + getDBSafeString("FUZAIJI_TAIOU", doctorData));
                sbDoctor.append(",BIKOU=" + getDBSafeString("BIKOU", doctorData));
                sbDoctor.append(",MI_DEFAULT=" + getDBSafeNumber("MI_DEFAULT", doctorData));
                sbDoctor.append(",KAISETUSHA_NM=" + getDBSafeString("KAISETUSHA_NM", doctorData));
                sbDoctor.append(",DR_NO=" + getDBSafeString("DR_NO", doctorData));
                sbDoctor.append(",MI_KBN=" + getDBSafeNumber("MI_KBN", doctorData));
                sbDoctor.append(",BANK_NM=" + getDBSafeString("BANK_NM", doctorData));
                sbDoctor.append(",BANK_SITEN_NM=" + getDBSafeString("BANK_SITEN_NM", doctorData));
                sbDoctor.append(",BANK_KOUZA_NO=" + getDBSafeString("BANK_KOUZA_NO", doctorData));
                sbDoctor.append(",BANK_KOUZA_KIND=" + getBankKouzaKindDBValue(getDBSafeNumber("BANK_KOUZA_KIND", doctorData)));
                sbDoctor.append(",FURIKOMI_MEIGI=" + getDBSafeString("FURIKOMI_MEIGI", doctorData));
                sbDoctor.append(",LAST_TIME=CURRENT_TIMESTAMP");
                sbDoctor.append(" WHERE");
                sbDoctor.append(" DR_CD=" + drCd);
            }
            else {
                //追加時
                sbDoctor.append("INSERT INTO");
                sbDoctor.append(" DOCTOR");
                sbDoctor.append(" (");
                sbDoctor.append(" DR_NM,");
                sbDoctor.append(" MI_NM,");
                sbDoctor.append(" MI_POST_CD,");
                sbDoctor.append(" MI_ADDRESS,");
                sbDoctor.append(" MI_TEL1,");
                sbDoctor.append(" MI_TEL2,");
                sbDoctor.append(" MI_FAX1,");
                sbDoctor.append(" MI_FAX2,");
                sbDoctor.append(" MI_CEL_TEL1,");
                sbDoctor.append(" MI_CEL_TEL2,");
                sbDoctor.append(" KINKYU_RENRAKU,");
                sbDoctor.append(" FUZAIJI_TAIOU,");
                sbDoctor.append(" BIKOU,");
                sbDoctor.append(" MI_DEFAULT,");
                sbDoctor.append(" KAISETUSHA_NM,");
                sbDoctor.append(" DR_NO,");
                sbDoctor.append(" MI_KBN,");
                sbDoctor.append(" BANK_NM,");
                sbDoctor.append(" BANK_SITEN_NM,");
                sbDoctor.append(" BANK_KOUZA_NO,");
                sbDoctor.append(" BANK_KOUZA_KIND,");
                sbDoctor.append(" FURIKOMI_MEIGI,");
                sbDoctor.append(" LAST_TIME");
                sbDoctor.append(" )");
                sbDoctor.append(" VALUES(");
                sbDoctor.append(" " + getDBSafeString("DR_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_POST_CD", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_ADDRESS", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_TEL1", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_TEL2", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_FAX1", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_FAX2", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_CEL_TEL1", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_CEL_TEL2", doctorData));
                sbDoctor.append("," + getDBSafeString("KINKYU_RENRAKU", doctorData));
                sbDoctor.append("," + getDBSafeString("FUZAIJI_TAIOU", doctorData));
                sbDoctor.append("," + getDBSafeString("BIKOU", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_DEFAULT", doctorData));
                sbDoctor.append("," + getDBSafeString("KAISETUSHA_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("DR_NO", doctorData));
                sbDoctor.append("," + getDBSafeString("MI_KBN", doctorData));
                sbDoctor.append("," + getDBSafeString("BANK_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("BANK_SITEN_NM", doctorData));
                sbDoctor.append("," + getDBSafeString("BANK_KOUZA_NO", doctorData));
                sbDoctor.append("," + getBankKouzaKindDBValue(getDBSafeNumber("BANK_KOUZA_KIND", doctorData)));
                sbDoctor.append("," + getDBSafeString("FURIKOMI_MEIGI", doctorData));
                sbDoctor.append(",CURRENT_TIMESTAMP");
                sbDoctor.append(" )");
            }

            //更新用SQL実行(DOCTORテーブル)
            dbm.executeUpdate(sbDoctor.toString());

            //「新規」「複製」の場合、今追加したデータの医師CDを取得しておく
            if (!isUpdate) {
                String sql = "SELECT GEN_ID(GEN_DOCTOR, 0) FROM rdb$database";
                VRArrayList tmpArray = (VRArrayList) dbm.executeQuery(sql);
                drCd = ((VRMap) tmpArray.get(0)).get("GEN_ID").toString();
            }

            //事業者テーブルDELETE
            dbm.executeUpdate("DELETE FROM JIGYOUSHA WHERE DR_CD=" + drCd);

            //事業者テーブルINSERT
            VRArrayList jigyoushoData = ((IkenshoIryouKikanJouhouShousaiKanren)kanren).getJigyoushoData();
            for (int i=0; i<jigyoushoData.getDataSize(); i++) {
                //パラメータ取得
                VRMap jigyoushaRow = (VRMap)jigyoushoData.getData(i);
                String INSURER_NO = jigyoushaRow.getData("INSURER_NO").toString();
                String JIGYOUSHA_NO = jigyoushaRow.getData("JIGYOUSHA_NO").toString();

                //事業者テーブル用SQL
                StringBuffer sbJigyousha = new StringBuffer();
                sbJigyousha.append("INSERT INTO");
                sbJigyousha.append(" JIGYOUSHA");
                sbJigyousha.append(" (");
                sbJigyousha.append(" DR_CD");
                sbJigyousha.append(",INSURER_NO");
                sbJigyousha.append(",JIGYOUSHA_NO");
                sbJigyousha.append(",LAST_TIME");
                sbJigyousha.append(")");
                sbJigyousha.append(" VALUES(");
                sbJigyousha.append(" " + drCd);
                sbJigyousha.append(",'" + INSURER_NO + "'");
                sbJigyousha.append(",'" + JIGYOUSHA_NO + "'");
                sbJigyousha.append(",CURRENT_TIMESTAMP");
                sbJigyousha.append(")");

                //INSERT
                dbm.executeUpdate(sbJigyousha.toString());
            }

            //コミット
            dbm.commitTransaction();
        }
        catch (Exception ex) {
            //ロールバック
            drCd = drCdOld;
            dbm.rollbackTransaction();
            throw new Exception(ex);
        }
        return true;
    }

    /**
     * Apply前の段階で更新処理可能かを返します。
     * @throws Exception 処理例外
     * @return 更新処理可能か
     */
    private boolean canControlUpdate() throws Exception {
        //エラーチェック
        Iterator it = tabArray.iterator();
        while (it.hasNext()) {
            IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
            if (!tab.noControlError()) {
                tabs.setSelectedComponent(tab);
                return false;
            }
        }

        //警告チェック
        it = tabArray.iterator();
        while (it.hasNext()) {
            IkenshoTabbableChildAffairContainer tab = (IkenshoTabbableChildAffairContainer) it.next();
            if (!tab.noControlWarning()) {
                tabs.setSelectedComponent(tab);
                return false;
            }
        }
        return true;
    }

    /**
     * BANK_KOUZA_KINDに関して、IkenshoRadioButtonGroupのselectedIndexから、DBの値へ変換します。(SQL文生成向け)
     * @param propertyValue IkenshoRadioButtonGroupのselectedIndex
     * @return DBの値
     */
    private String getBankKouzaKindDBValue(String propertyValue) {
        String dbValue = "2";

        if (propertyValue.equals("0")) { //未選択
            dbValue = "2"; //「不明」
        }
        else if (propertyValue.equals("1")) { //1番目を選択
            dbValue = "1"; //「普通」
        }
        else if (propertyValue.equals("2")) { //2番目を選択
            dbValue = "0"; //「当座」
        }

        return dbValue;
    }

    /**
     * BANK_KOUZA_KINDに関して、DBの値から、IkenshoRadioButtonGroupのselectedIndexへ変換します。
     * @param dbValue DBの値
     * @return IkenshoRadioButtonGroupのselectedIndex
     */
    private String getBankKouzaKindPropertyValue(String dbValue) {
        String propertyValue = "0";
        if (dbValue.equals("2")) { //「不明」
            propertyValue = "0"; //未選択
        }
        else if (dbValue.equals("1")) { //「普通」
            propertyValue = "1"; //1番目を選択
        }
        else if (dbValue.equals("0")) { //「当座」
            propertyValue = "2"; //2番目を選択
        }
        return propertyValue;
    }
}
