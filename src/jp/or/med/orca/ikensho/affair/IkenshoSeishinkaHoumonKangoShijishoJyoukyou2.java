package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHintButton;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoJyoukyou2 extends IkenshoHoumonKangoShijishoMindBody1 {

    // 病名告知
    protected ACGroupBox byomeiKokutiGrp = new ACGroupBox();
    private ACLabelContainer byomeiKokutiContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup byomeiKokuti = new ACClearableRadioButtonGroup();
    // 治療の受け入れ
    protected ACGroupBox chiryoGrp = new ACGroupBox();
    private ACLabelContainer chiryoContainer = new ACLabelContainer();
    private IkenshoOptionComboBox chiryo = new IkenshoOptionComboBox();
    // 複数名訪問の必要性
    protected ACGroupBox fukusuHomonGrp = new ACGroupBox();
    private ACLabelContainer fukusuHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup fukusuHomon = new ACClearableRadioButtonGroup();
    // 短時間訪問の必要性
    protected ACGroupBox tanjikanHomonGrp = new ACGroupBox();
    private ACLabelContainer tanjikanHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup tanjikanHomon = new ACClearableRadioButtonGroup();

    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaHoumonKangoShijishoJyoukyou2() {
        super();
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初期処理
     */
    private void jbInit() throws Exception {

        getTitle().setText("現在の状況（続き）");
        this.remove(getJiritsuGroup());
        this.add(byomeiKokutiGrp, VRLayout.NORTH);
        this.add(chiryoGrp, VRLayout.NORTH);
        this.add(fukusuHomonGrp, VRLayout.NORTH);
        this.add(tanjikanHomonGrp, VRLayout.NORTH);
        this.add(getJiritsuGroup(), VRLayout.NORTH);

        // 病名告知
        byomeiKokuti.setBindPath("BYOUMEI_KOKUTI");
        byomeiKokuti.setModel(new VRListModelAdapter(
                new VRArrayList(Arrays.asList(new String[] {"あり", "なし"}))));
        byomeiKokutiContainer.setLayout(new VRLayout());
        byomeiKokutiContainer.add(byomeiKokuti, VRLayout.FLOW);
        byomeiKokutiGrp.setText("病名告知");
        byomeiKokutiGrp.setLayout(new BorderLayout());
        byomeiKokutiGrp.add(byomeiKokutiContainer, BorderLayout.WEST);
        // 治療の受け入れ
        chiryo.setMaxLength(30);
        chiryo.setColumns(30);
        chiryo.setIMEMode(InputSubset.KANJI);
        chiryo.setBindPath("TIRYOU_UKEIRE");
        chiryoContainer.setLayout(new VRLayout());
        chiryoContainer.add(chiryo, VRLayout.FLOW);
        chiryoGrp.setText("治療の受け入れ");
        chiryoGrp.add(chiryoContainer, BorderLayout.WEST);
        // 複数名訪問の必要性
        fukusuHomon.setBindPath("FUKUSU_HOUMON");
        fukusuHomon.setModel(new VRListModelAdapter(
                new VRArrayList(Arrays.asList(new String[] {"あり", "なし"}))));
        fukusuHomonContainer.setLayout(new VRLayout());
        fukusuHomonContainer.add(fukusuHomon, VRLayout.FLOW);
        fukusuHomonGrp.setText("複数名訪問の必要性");
        fukusuHomonGrp.setLayout(new BorderLayout());
        fukusuHomonGrp.add(fukusuHomonContainer, VRLayout.WEST);
        // 短時間訪問の必要性
        tanjikanHomon.setBindPath("TANJIKAN_HOUMON");
        tanjikanHomon.setModel(new VRListModelAdapter(
                new VRArrayList(Arrays.asList(new String[] {"あり", "なし"}))));
        tanjikanHomonContainer.setLayout(new VRLayout());
        tanjikanHomonContainer.add(tanjikanHomon, VRLayout.FLOW);
        tanjikanHomonGrp.setText("短時間訪問の必要性");
        tanjikanHomonGrp.setLayout(new BorderLayout());
        tanjikanHomonGrp.add(tanjikanHomonContainer, VRLayout.WEST);
        // 障害老人の日常生活自立度、褥瘡の深さの非表示
        getSyougaiRoujinJiritsu().setVisible(false);
        getJokusoDepthGroup().setVisible(false);
        // 認知症高齢者の日常生活自立度のヘルプボタン押下時の連動部品の設定
        getChihouJiritsuHelp().setFollowHideComponents(new JComponent[] { byomeiKokutiGrp, chiryoGrp, });
    }
    
    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * @param dbm DBManager
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(chiryo, IkenshoCommon.TEIKEI_CARE_RECEIVING);
        chiryo.setOptionComboBoxParameters("治療の受け入れ", IkenshoCommon.TEIKEI_CARE_RECEIVING, 30);
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End