package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;


/** TODO <HEAD_IKENSYO> */
public class IkenshoTeikeibunList extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();

    private VRPanel client = new VRPanel();
    private VRPanel formatKbnPnl = new VRPanel();
    private ACLabelContainer formatKbnContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup formatKbn = new ACClearableRadioButtonGroup();
    private ACTable table = new ACTable();
    private VRArrayList data;

    private final String h17 = "平成17年度";
    private final String h18 = "平成18年度";

    public IkenshoTeikeibunList() {
        try {
            jbInit();
            event();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
//    private void jbInit() {
    private void jbInit() throws Exception {
        buttons.setTitle("特記事項一覧");
        this.add(buttons, VRLayout.NORTH);
        this.add(client, VRLayout.CLIENT);

        //ボタン
        buttons.add(detail, BorderLayout.EAST);
        detail.setText("　編集(E)　");
        detail.setMnemonic('E');
        detail.setActionCommand("詳細情報(E)");
        detail.setToolTipText("選択された定型文の編集画面に移ります。");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        //client
        client.setLayout(new VRLayout());
//        client.add(formatKbnPnl, VRLayout.NORTH);
        client.add(table, VRLayout.CLIENT);

/*
        //新・旧選択
        formatKbnPnl.setLayout(new VRLayout());
        formatKbnPnl.add(formatKbnContainer, VRLayout.FLOW);
        formatKbnContainer.setText("対応年度");
        formatKbnContainer.add(formatKbn, null);
        VRLayout formatKbnLayout = new VRLayout();
        formatKbnLayout.setAutoWrap(false);
        formatKbn.setLayout(formatKbnLayout);
        formatKbn.setUseClearButton(false);
        formatKbn.setModel(new VRListModelAdapter(
            new VRArrayList(Arrays.asList(new String[] {
                                          h18,
                                          h17}))));
        formatKbn.setSelectedIndex(1);
*/

        //ステータスバー
        setStatusText("編集したい定型文項目を選択し、「編集」ボタンを押してください。");
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        addDetailTrigger(detail);

        setTeikeibunData();
    }

    private void event() throws Exception {
        //区分選択
        formatKbn.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setTeikeibunData();
            }
        });

        //テーブルクリック
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        //編集DLGへ
                        showIkenshoOptionDialog();
                    }
                    catch (Exception ex) {
                      IkenshoCommon.showExceptionMessage(ex);
                    }
                }
            }
        });
    }

    public ACAffairButtonBar getButtonBar() {
        return null;
    }

    public Component getFirstFocusComponent() {
        return formatKbn;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        return true;
    }

    public boolean canClose() {
        return true;
    }

    protected void detailActionPerformed(ActionEvent e) throws Exception {
        //編集DLGへ
        showIkenshoOptionDialog();
    }

    /**
     * 定型文データをテーブルに設定します。
     */
    private void setTeikeibunData() {
//        switch (formatKbn.getSelectedIndex()) {
//            case 1:
//                createDataH18();
//                break;
//            case 2:
//                createDataH17();
//                break;
//        }
        //[ID:0000688][Shin Fujihara] 2012/03/12 Edit - start
        //ドレーンを削除し、留置カテーテルの部位を追加
        //createDataH18();
        createDataH24();
        //[ID:0000688][Shin Fujihara] 2012/03/12 Addition - end
        //テーブルのカラムを生成
        createColumn();
        //行の選択
        setSelectedRow();
    }

    /**
     * テーブルに設定するデータを生成します。(H18改正前)
     */
    private void createDataH17() {
        data = new VRArrayList();
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_NAME, 30, "1-(1)", "", "", "診断名", "疾病名"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_NAME, 12, "1-(4)", "", "", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "薬剤名"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT,  4, "1-(4)", "", "", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用量単位"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10, "1-(4)", "", "", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用法"));
        data.add(createRow(IkenshoCommon.TEIKEI_MIND_SICK_NAME, 30, "3-(4)", "", "", "精神・神経症状の有無", "精神・神経症状"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME, 10, "3-(5)", "", "", "身体の状態", "四肢欠損・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_PARALYSIS_NAME, 10, "3-(5)", "", "", "身体の状態", "麻痺・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME, 10, "3-(5)", "", "", "身体の状態", "筋力の低下・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME, 10, "3-(5)", "", "", "身体の状態", "褥瘡・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME, 10, "3-(5)", "", "", "身体の状態", "皮膚疾患・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "失禁"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "転倒・骨折"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "徘徊"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "褥瘡"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PNEUMONIA_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "嚥下性肺炎"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INTESTINES_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "腸閉塞"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "易感染性"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "心肺機能の低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PAIN_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "痛み"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "脱水"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME, 30, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "その他・詳細"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15, "4-(1)", "", "", "現在、発生の可能性が高い病態とその対処方針", "その他・項目名"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30, "4-(3)", "", "", "介護サービスにおける医学的観点からの留意事項", "血圧について"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30, "4-(3)", "", "", "介護サービスにおける医学的観点からの留意事項", "嚥下について"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30, "4-(3)", "", "", "介護サービスにおける医学的観点からの留意事項", "摂食について"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30, "4-(3)", "", "", "介護サービスにおける医学的観点からの留意事項", "移動について"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50, "4-(3)", "", "", "介護サービスにおける医学的観点からの留意事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_INFECTION_NAME, 30, "4-(4)", "", "", "感染症の有無", "有の場合"));
        data.add(createRow(IkenshoCommon.TEIKEI_MENTION_NAME, 50, "5", "", "", "その他特記すべき事項", "その他特記すべき事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_TYPE,  5, "", "●", "", "現在の状況／装着・医療機器等", "人工呼吸器方式"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10, "", "●", "", "現在の状況／装着・医療機器等", "人工呼吸器設定"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_TYPE,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養方法"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養サイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養交換"));
        data.add(createRow(IkenshoCommon.TEIKEI_CANURE_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "気管カニューレサイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10, "", "●", "", "現在の状況／装着・医療機器等", "ドレーン部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテルサイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN,  5, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテル交換"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU, 50, "", "●", "", "留意事項及び指示事項", "療養生活指導上の留意事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION, 50, "", "●", "", "留意事項及び指示事項", "リハビリテーション"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU, 50, "", "●", "", "留意事項及び指示事項", "褥瘡の処置等"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO, 50, "", "●", "", "留意事項及び指示事項", "装置・使用医療機器等の操作援助・管理"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_SONOTA, 50, "", "●", "", "留意事項及び指示事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TOKKI, 50, "", "●", "", "留意事項及び指示事項", "特記すべき留意事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA, 50, "", "●", "", "在宅患者訪問点滴注射に関する指示", "在宅訪問点滴注射"));
    }

    /**
     * テーブルに設定するデータを生成します。(H18改正後)
     */
    private void createDataH18() {
        data = new VRArrayList();
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace begin 【2009年度対応：特記事項一覧】一覧の表示の見直し
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_NAME, 30, "1-(1)", "●", "", "診断名", "疾病名"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace end
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30, "", "", "1-(1)", "診断名", "疾病名"));
        // [ID:0000518][Tozo TANAKA] 2009/09/04 add begin 【2009年度対応：特記事項一覧】特定疾病項目の編集を可能とする 
        data.add(createRow(IkenshoCommon.TEIKEI_SPECIFIED_DISEASE_NAME, 30, "●", "●", "", "特定疾病", "特定疾病"));
        // [ID:0000518][Tozo TANAKA] 2009/09/04 add end 【2009年度対応：特記事項一覧】特定疾病項目の編集を可能とする 
        data.add(createRow(IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME, 30, "1-(2)", "", "1-(2)", "症状としての安定性", "「不安定」とした場合の具体的状況"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace begin 【2009年度対応：特記事項一覧】一覧の表示の見直し
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_NAME, 12, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "薬剤名"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace end
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "薬剤名"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace begin 【2009年度対応：特記事項一覧】一覧の表示の見直し
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT,  4, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "用量単位"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace end
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT,  4, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用量単位"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace begin 【2009年度対応：特記事項一覧】一覧の表示の見直し
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "用法"));
        // [ID:0000518][Masahiko Higuchi] 2009/09/04 replace End
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用法"));

        data.add(createRow(IkenshoCommon.TEIKEI_MIND_SICK_NAME, 30, "3-(4)", "", "3-(2)", "その他の精神・神経症状", "精神・神経症状"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "四肢欠損・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "麻痺(その他)・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "筋力の低下・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "関節の拘縮・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "関節の痛み・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "褥瘡・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME, 10, "3-(5)", "", "3-(3)",  "身体の状態", "その他の皮膚疾患・部位"));
//        data.add(createRow(49, 25, "4-(2)", "", "", "栄養・食生活", "一日の食事摂取量"));
//        data.add(createRow(50, 25, "4-(2)", "", "", "栄養・食生活", "食欲"));
//        data.add(createRow(51, 25, "4-(2)", "", "", "栄養・食生活", "現在の栄養状態"));
        data.add(createRow(IkenshoCommon.TEIKEI_EATING_RYUIJIKOU_NAME, 30, "4-(2)", "", "", "栄養・食生活", "栄養・食生活上の留意点"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "尿失禁"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "転倒・骨折"));
        data.add(createRow(IkenshoCommon.TEIKEI_MOVILITY_DOWN_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "移動能力の低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "褥瘡"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "心肺機能の低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_TOJIKOMORI_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "閉じこもり"));
        data.add(createRow(IkenshoCommon.TEIKEI_IYOKU_DOWN_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "意欲低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "徘徊"));
        data.add(createRow(IkenshoCommon.TEIKEI_LOW_ENERGY_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "低栄養"));
        data.add(createRow(IkenshoCommon.TEIKEI_SESSHOKU_ENGE_DOWN_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "摂食・嚥下機能低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "脱水"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "易感染性"));
        data.add(createRow(IkenshoCommon.TEIKEI_GAN_TOTSU_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "がん等による疼痛"));

        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "嚥下性肺炎"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "腸閉塞"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PAIN_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "痛み"));

        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "その他・詳細"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "その他・項目名"));
//        data.add(createRow(IkenshoCommon.TEIKEI_OUTLOOK_SERVISE_NAME, 30, "4-(4)", "", "", "介護の必要の程度に関する予後の見通し", "改善への寄与が期待できるサービス"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "血圧"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "摂食"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "嚥下"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "移動"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_UNDOU_NAME, 30, "4-(6)", "", "", "サービス提供時における医学的観点からの留意事項", "運動"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_INFECTION_NAME, 30, "4-(7)", "", "4-(3)", "感染症の有無", "有の場合"));
        data.add(createRow(IkenshoCommon.TEIKEI_MENTION_NAME, 50, "5", "", "", "その他特記すべき事項", "その他特記すべき事項"));

        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, 50, "", "", "5", "その他特記すべき事項", "その他特記すべき事項"));
        
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_TYPE,  5, "", "●", "", "現在の状況／装着・医療機器等", "人工呼吸器方式"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10, "", "●", "", "現在の状況／装着・医療機器等", "人工呼吸器設定"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_TYPE,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養方法"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養サイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養交換"));
        data.add(createRow(IkenshoCommon.TEIKEI_CANURE_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "気管カニューレサイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10, "", "●", "", "現在の状況／装着・医療機器等", "ドレーン部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテルサイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN,  5, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテル交換"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU, 50, "", "●", "", "留意事項及び指示事項", "療養生活指導上の留意事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION, 50, "", "●", "", "留意事項及び指示事項", "リハビリテーション"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU, 50, "", "●", "", "留意事項及び指示事項", "褥瘡の処置等"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO, 50, "", "●", "", "留意事項及び指示事項", "装置・使用医療機器等の操作援助・管理"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_SONOTA, 50, "", "●", "", "留意事項及び指示事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TOKKI, 50, "", "●", "", "留意事項及び指示事項", "特記すべき留意事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA, 50, "", "●", "", "在宅患者訪問点滴注射に関する指示", "在宅訪問点滴注射"));

        //医師意見書
////        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30, "", "", "1-(1)", "診断名", "疾病名"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_INSECURE_CONDITION_NAME, 30, "", "", "1-(2)", "症状としての安定性", "「不安定」とした場合の具体的状況"));
//      //        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "薬剤名"));
//      //        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT,  4, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用量単位"));
//      //        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用法"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SUCK_COUNT, 5, "", "", "2", "処置内容", "吸引処置回数"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 10, "", "", "3-(1)", "行動上の障害の有無", "その他"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MIND_SICK_NAME, 30, "", "", "3-(2)", "精神・神経症状の有無", "症状名"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MIND_SICK_OTHER_NAME, 10, "", "", "3-(2)", "精神・神経症状の有無", "その他"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_URINE_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "尿失禁"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_FACTURE_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "転倒・骨折"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PROWL_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "徘徊"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_JYOKUSOU_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "褥瘡"));
////        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "嚥下性肺炎"));
//      //        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "腸閉塞"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INFECTION_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "易感染性"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_HEART_LUNG_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "心肺機能の低下"));
//      //        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PAIN_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "痛み"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_DEHYDRATION_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "脱水"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_OTHER_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "その他・詳細"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_TYPE_OTHER_NAME, 15, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "その他・項目名"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30, "", "", "4-(2)", "介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項", "血圧"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME, 30, "", "", "4-(2)", "介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項", "嚥下"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_EAT_NAME, 30, "", "", "4-(2)", "介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項", "摂食"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME, 30, "", "", "4-(2)", "介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項", "移動"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME, 50, "", "", "4-(2)", "介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項", "その他"));
//        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_INFECTION_NAME, 30, "", "", "4-(3)", "感染症の有無", "有の場合"));
////        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, 50, "", "", "5", "その他特記すべき事項", "その他特記すべき事項"));


    }

    /**
     * テーブルに設定するデータを生成します。(H248改正後)
     */
    private void createDataH24() {
        data = new VRArrayList();
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_NAME, 30, "1-(1)", "●", "", "診断名", "疾病名"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30, "", "", "1-(1)", "診断名", "疾病名"));
        data.add(createRow(IkenshoCommon.TEIKEI_SPECIFIED_DISEASE_NAME, 30, "●", "●", "", "特定疾病", "特定疾病"));
        data.add(createRow(IkenshoCommon.TEIKEI_INSECURE_CONDITION_NAME, 30, "1-(2)", "", "1-(2)", "症状としての安定性", "「不安定」とした場合の具体的状況"));
        // [ID:0000752][Shin Fujihara] 2012/11 edit begin 2012年度対応 薬剤名項目の入力文字数拡張
        // 定数を参照するよう変更
        //data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_NAME, 12, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "薬剤名"));
        //data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, 12, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "薬剤名"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "薬剤名"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME, IkenshoConstants.SICK_MEDICINE_NAME_MAX_LENGTH, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "薬剤名"));
        // [ID:0000752][Shin Fujihara] 2012/11 edit end 2012年度対応 薬剤名項目の入力文字数拡張
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT,  4, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "用量単位"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT,  4, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用量単位"));
        data.add(createRow(IkenshoCommon.TEIKEI_MEDICINE_USAGE, 10, "1-(3)", "●", "", "生活機能低下の直接の原因となっている傷病または特定疾病の経過及び投薬内容を含む治療内容", "用法"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE, 10, "", "", "1-(3)", "障害の直接の原因となっている傷病の経過及び投薬内容を含む治療内容", "用法"));

        data.add(createRow(IkenshoCommon.TEIKEI_MIND_SICK_NAME, 30, "3-(4)", "", "3-(2)", "その他の精神・神経症状", "精神・神経症状"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_HAND_FOOT_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "四肢欠損・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_MAHI_POSITION_OTHER_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "麻痺(その他)・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "筋力の低下・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CENNECT_KOSHUKU_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "関節の拘縮・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CONNECT_PAIN_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "関節の痛み・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_JYOKUSOU_NAME, 10, "3-(5)", "", "3-(3)", "身体の状態", "褥瘡・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_BODY_STATUS_SKIN_NAME, 10, "3-(5)", "", "3-(3)",  "身体の状態", "その他の皮膚疾患・部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_EATING_RYUIJIKOU_NAME, 30, "4-(2)", "", "", "栄養・食生活", "栄養・食生活上の留意点"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_URINE_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "尿失禁"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_FACTURE_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "転倒・骨折"));
        data.add(createRow(IkenshoCommon.TEIKEI_MOVILITY_DOWN_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "移動能力の低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_JYOKUSOU_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "褥瘡"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_HEART_LUNG_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "心肺機能の低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_TOJIKOMORI_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "閉じこもり"));
        data.add(createRow(IkenshoCommon.TEIKEI_IYOKU_DOWN_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "意欲低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_PROWL_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "徘徊"));
        data.add(createRow(IkenshoCommon.TEIKEI_LOW_ENERGY_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "低栄養"));
        data.add(createRow(IkenshoCommon.TEIKEI_SESSHOKU_ENGE_DOWN_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "摂食・嚥下機能低下"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_DEHYDRATION_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "脱水"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_INFECTION_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "易感染性"));
        data.add(createRow(IkenshoCommon.TEIKEI_GAN_TOTSU_NAME, 30, "4-(3)", "", "", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "がん等による疼痛"));

        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "嚥下性肺炎"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "腸閉塞"));
        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PAIN_NAME, 30, "", "", "4-(1)", "現在、発生の可能性が高い病態とその対処方針", "痛み"));

        data.add(createRow(IkenshoCommon.TEIKEI_SICK_COPE_OTHER_NAME, 30, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "その他・詳細"));
        data.add(createRow(IkenshoCommon.TEIKEI_SICK_TYPE_OTHER_NAME, 15, "4-(3)", "", "4-(1)", "現在あるかまたは今後発生の可能性の高い状態とその対処方針", "その他・項目名"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "血圧"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_EAT_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "摂食"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_ENGE_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "嚥下"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_MOVE_NAME, 30, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "移動"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_UNDOU_NAME, 30, "4-(6)", "", "", "サービス提供時における医学的観点からの留意事項", "運動"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_SERVICE_OTHER_NAME, 50, "4-(6)", "", "4-(2)", "サービス提供時における医学的観点からの留意事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_INFECTION_NAME, 30, "4-(7)", "", "4-(3)", "感染症の有無", "有の場合"));
        data.add(createRow(IkenshoCommon.TEIKEI_MENTION_NAME, 50, "5", "", "", "その他特記すべき事項", "その他特記すべき事項"));

        data.add(createRow(IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, 50, "", "", "5", "その他特記すべき事項", "その他特記すべき事項"));
        
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_TYPE,  5, "", "●", "", "現在の状況／装着・医療機器等", "人工呼吸器方式"));
        data.add(createRow(IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10, "", "●", "", "現在の状況／装着・医療機器等", "人工呼吸器設定"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_TYPE,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養方法"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養サイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN,  5, "", "●", "", "現在の状況／装着・医療機器等", "経管栄養交換"));
        data.add(createRow(IkenshoCommon.TEIKEI_CANURE_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "気管カニューレサイズ"));
        //data.add(createRow(IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10, "", "●", "", "現在の状況／装着・医療機器等", "ドレーン部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_POS_NAME, 10, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテル部位"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_SIZE,  5, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテルサイズ"));
        data.add(createRow(IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN,  5, "", "●", "", "現在の状況／装着・医療機器等", "留置カテーテル交換"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU, 50, "", "●", "", "留意事項及び指示事項", "療養生活指導上の留意事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_REHABILITATION, 50, "", "●", "", "留意事項及び指示事項", "リハビリテーション"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_JYOKUSOU, 50, "", "●", "", "留意事項及び指示事項", "褥瘡の処置等"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_KIKI_SOUSA_ENJYO, 50, "", "●", "", "留意事項及び指示事項", "装置・使用医療機器等の操作援助・管理"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_SONOTA, 50, "", "●", "", "留意事項及び指示事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TOKKI, 50, "", "●", "", "留意事項及び指示事項", "特記すべき留意事項"));
        data.add(createRow(IkenshoCommon.TEIKEI_HOUMON_TENTEKI_CHUSHA, 50, "", "●", "", "在宅患者訪問点滴注射に関する指示", "在宅訪問点滴注射"));
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
        data.add(createRow(IkenshoCommon.TEIKEI_FACILITY_NAME, 30, "", "●", "", "患者", "施設名"));
        data.add(createRow(IkenshoCommon.TEIKEI_CARE_RECEIVING, 30, "", "●", "", "現在の状況", "治療の受け入れ"));
        data.add(createRow(IkenshoCommon.TEIKEI_SEIKATU_RIZUMU, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "生活リズムの確立"));
        data.add(createRow(IkenshoCommon.TEIKEI_KAJI_NOURYOKU, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "家事能力、社会技能等の獲得"));
        data.add(createRow(IkenshoCommon.TEIKEI_TAIJIN_KANKEI, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "対人関係の改善（家族含む）"));
        data.add(createRow(IkenshoCommon.TEIKEI_SYAKAI_SHIGEN, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "社会資源活用の支援"));
        data.add(createRow(IkenshoCommon.TEIKEI_YAKUBUTU_RYOUHOU, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "薬物療法継続への援助"));
        data.add(createRow(IkenshoCommon.TEIKEI_SHINTAI_GAPPEISYO, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "身体合併症の発症・悪化の防止"));
        data.add(createRow(IkenshoCommon.TEIKEI_SEISHIN_OTHER, 50, "", "●", "", "精神訪問看護に関する留意事項及び指示事項", "その他"));
        data.add(createRow(IkenshoCommon.TEIKEI_JYOUHOU_SYUDAN, 40, "", "●", "", "医療機関", "情報交換の手段"));
        data.add(createRow(IkenshoCommon.TEIKEI_PLURAL_VISIT_REASON, 25, "", "●", "", "留意事項及び指示事項", "複数名訪問の必要性：理由"));
        data.add(createRow(IkenshoCommon.TEIKEI_SHROT_VISIT_REASON, 25, "", "●", "", "留意事項及び指示事項", "短時間訪問の必要性：理由"));
        data.add(createRow(IkenshoCommon.TEIKEI_SEISHIN_KANSATU, 30, "", "●", "", "留意事項及び指示事項", "精神症状（観察が必要な項目）"));
        data.add(createRow(IkenshoCommon.TEIKEI_SHINTAI_KANSATU, 30, "", "●", "", "留意事項及び指示事項", "身体症状（観察が必要な項目）"));
        data.add(createRow(IkenshoCommon.TEIKEI_KANSATU_OTHER, 40, "", "●", "", "留意事項及び指示事項", "その他"));
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
    }
    //[ID:0000688][Shin Fujihara] 2012/03/12 Addition - end
    
    /**
     * 1行分のデータを生成
     * @param tkb_kbn 定型文区分
     * @param doc_kbn 文書区分
     * @param group 分類
     * @param item 項目
     * @return 1行
     */
    private VRMap createRow(int tkb_kbn, int length, String s_ikn_kbn, String sis_kbn, String i_ikn_kbn, String group, String item) {
        VRMap tmp = new VRHashMap();
        tmp.setData("TKB_KBN", String.valueOf(tkb_kbn));
        tmp.setData("LENGTH", String.valueOf(length));
        tmp.setData("S_IKN_KBN", s_ikn_kbn);
        tmp.setData("SIS_KBN", sis_kbn);
        tmp.setData("I_IKN_KBN", i_ikn_kbn);
        tmp.setData("GROUP", group);
        tmp.setData("ITEM", item);
        return tmp;
    }

    /**
     * 列の生成
     */
    private void createColumn() {
        //テーブルの生成
        table.setModel(new ACTableModelAdapter(data, new String[] {
                                                    "TKB_KBN",
                                                    "LENGTH",
                                                    "S_IKN_KBN",
                                                    "SIS_KBN",
                                                    "GROUP",
                                                    "ITEM",
                                                    "I_IKN_KBN",
        }));

        //ColumnModelの生成
        table.setColumnModel(new VRTableColumnModel(
            new VRTableColumn[] {
//                    new VRTableColumn(2, 60, "主-意見書", SwingConstants.CENTER),
//                    new VRTableColumn(3, 40, "指示書", SwingConstants.CENTER),
//                    new VRTableColumn(6, 60, "医-意見書", SwingConstants.CENTER),
//            		  new VRTableColumn(4, 370, "分類"),
//                    new VRTableColumn(5, 270, "項目名")
                    new VRTableColumn(2, 120, "主-意見書", SwingConstants.CENTER),
                    new VRTableColumn(3, 120, "指示書", SwingConstants.CENTER),
                    new VRTableColumn(6, 120, "医-意見書", SwingConstants.CENTER),
                    new VRTableColumn(4, 650, "分類"),
                    new VRTableColumn(5, 350, "項目名")
            }));
    }

    /**
     * 定型文編集DLGを表示します。
     * @throws Exception
     */
    private void showIkenshoOptionDialog() throws Exception {
        //選択行の取得
        int selectedRow = table.getSelectedModelRow();
        if (selectedRow < 0) {
            return;
        }
        if (selectedRow >= table.getRowCount()) {
            return;
        }

        //Rowの取得～DLG表示
        VRMap row = (VRMap)data.getData(table.getSelectedModelRow());
        int selectedTkbKbn = Integer.parseInt(row.getData("TKB_KBN").toString());

        // [ID:0000518][Tozo TANAKA] 2009/09/04 replace begin 【2009年度対応：特記事項一覧】特定疾病項目の編集を可能とする 
//        IkenshoTeikeibunEdit dlg = new IkenshoTeikeibunEdit(
//                row.getData("ITEM").toString(),
//                IkenshoTeikeibunEdit.TEIKEIBUN,
//                selectedTkbKbn,
//                Integer.parseInt(row.getData("LENGTH").toString()));
        int tableNo = IkenshoTeikeibunEdit.TEIKEIBUN;
        if(selectedTkbKbn==IkenshoCommon.TEIKEI_SPECIFIED_DISEASE_NAME){
            tableNo = IkenshoTeikeibunEdit.DISEASE;
            selectedTkbKbn = 0;
        }
        IkenshoTeikeibunEdit dlg = new IkenshoTeikeibunEdit(
                row.getData("ITEM").toString(),
                tableNo,
                selectedTkbKbn,
                Integer.parseInt(row.getData("LENGTH").toString()));
        // [ID:0000518][Tozo TANAKA] 2009/09/04 replace begin 【2009年度対応：特記事項一覧】特定疾病項目の編集を可能とする 
        
        
        //2006/08/10 Tozo TANAKA add-begin 両意見書への同時追加対応のため
        int otherDocKbn = -1;
        int otherDocType = -1;
        switch(selectedTkbKbn){
        case IkenshoCommon.TEIKEI_SICK_NAME:
            otherDocKbn = IkenshoCommon.TEIKEI_ISHI_SICK_NAME;
            otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
            break;
        case IkenshoCommon.TEIKEI_ISHI_SICK_NAME:
            otherDocKbn = IkenshoCommon.TEIKEI_SICK_NAME;
            otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
            break;
        case IkenshoCommon.TEIKEI_MEDICINE_NAME:
            otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME;
            otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
            break;
        case IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME:
            otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_NAME;
            otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
            break;
        case IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT:
            otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT;
            otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
            break;
        case IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT:
            otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT;
            otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
            break;
        case IkenshoCommon.TEIKEI_MEDICINE_USAGE:
            otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE;
            otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
            break;
        case IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE:
            otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_USAGE;
            otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
            break;
        }
        
        switch (otherDocType) {
        case IkenshoConstants.IKENSHO_LOW_DEFAULT:
        case IkenshoConstants.IKENSHO_LOW_H18:
            dlg.setAllowedAddOtherDocument(otherDocKbn, "主治医意見書");
            break;
        case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
            dlg.setAllowedAddOtherDocument(otherDocKbn, "医師意見書");
            break;
        }
        
        // 2006/08/10 Tozo TANAKA add-end 両意見書への同時追加対応のため
        
        dlg.setVisible(true);
//        dlg.show();
    }

    /**
     * テーブルの行を選択します。
     * @throws Exception
     */
    private void setSelectedRow(){
        if (table.getRowCount() > 0) {
//        if (table.getTable().getRowCount() > 0) {
            detail.setEnabled(true);
            table.setSelectedModelRow(0);
        }
        else {
            detail.setEnabled(false);
        }
    }
}
