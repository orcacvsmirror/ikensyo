package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaTokubetsuHoumonKangoShijishoRyuiShiji extends IkenshoTokubetsuHoumonKangoShijishoRyuiShiji {

    private ACLabel title = new ACLabel();
    // 複数名訪問の必要性
    protected ACGroupBox fukusuHomonGrp = new ACGroupBox();
    private ACLabelContainer fukusuHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup fukusuHomon = new ACClearableRadioButtonGroup();
    private ACLabelContainer fukusuHomonRiyuLabel = new ACLabelContainer();
    private IkenshoOptionComboBox fukusuHomonRiyu = new IkenshoOptionComboBox();
    // 短時間訪問の必要性
    protected ACGroupBox tanjikanHomonGrp = new ACGroupBox();
    private ACLabelContainer tanjikanHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup tanjikanHomon = new ACClearableRadioButtonGroup();
    private ACLabelContainer tanjikanHomonRiyuLabel = new ACLabelContainer();
    private IkenshoOptionComboBox tanjikanHomonRiyu= new IkenshoOptionComboBox();
    // 特に観察を要する項目
    private ACGroupBox kansatuGroup;
    private ACLabelContainer kansatuContainer;
    // １ 服薬確認
    private ACLabelContainer fukuyakuConteiner;
    private ACIntegerCheckBox fukuyaku;
    // ２ 水分及び食物摂取の状況
    private ACLabelContainer suibunConteiner;
    private ACIntegerCheckBox suibun;
    // ３ 精神症状
    private ACLabelContainer seishinConteiner;
    private ACIntegerCheckBox seishin;
    private IkenshoOptionComboBox seishinJikou;
    private ACLabel seishinEndCaption;
    // ４ 身体症状
    private ACLabelContainer shintaiConteiner;
    private ACIntegerCheckBox shintai;
    private IkenshoOptionComboBox shintaiJikou;
    private ACLabel shintaiEndCaption;
    // ５ その他
    private ACLabelContainer otherConteiner;
    private ACIntegerCheckBox other;
    private IkenshoOptionComboBox otherText;
    private ACLabel otherEndCaption;

    /**
     * コンストラクタ
     */
    public IkenshoSeishinkaTokubetsuHoumonKangoShijishoRyuiShiji() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 初期処理
     */
    private void jbInit()throws Exception {

        this.add(getKansatuGroup(), VRLayout.CLIENT);
        this.add(getTokubetsuGroup(), VRLayout.NORTH);
        // 精神科特別訪問看護指示書の留意事項及び指示事項は別のコンポーネントを使用する
        getRyuuiShijiTitle().setText("留意事項及び指示事項");
        getRyuiShijiGrp().setVisible(false);
        
        title.setText("留意事項及び指示事項（注：点滴注射薬の相互作用・副作用についての留意点があれば記載してください。）");
        getTokubetsuGroup().add(title, VRLayout.NORTH);
        // 複数名訪問の必要性
        fukusuHomon.setBindPath("FUKUSU_HOUMON");
        fukusuHomon.setModel(new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] {"あり", "なし"}))));
        fukusuHomonRiyu.setIMEMode(InputSubset.KANJI);
        fukusuHomonRiyu.setColumns(25);
        fukusuHomonRiyu.setMaxLength(25);
        fukusuHomonRiyu.setBindPath("FUKUSU_HOUMON_RIYU");
        fukusuHomonRiyuLabel.setText("理由：");
        fukusuHomonContainer.setLayout(new VRLayout());
        fukusuHomonContainer.setText("複数名訪問の必要性");
        fukusuHomonRiyuLabel.add(fukusuHomonRiyu, VRLayout.FLOW);
        fukusuHomonContainer.add(fukusuHomon, VRLayout.FLOW);
        fukusuHomonContainer.add(fukusuHomonRiyuLabel, VRLayout.FLOW);
        // 短時間訪問の必要性
        tanjikanHomon.setBindPath("TANJIKAN_HOUMON");
        tanjikanHomon.setModel(new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] {"あり", "なし"}))));
        tanjikanHomonRiyu.setIMEMode(InputSubset.KANJI);
        tanjikanHomonRiyu.setColumns(25);
        tanjikanHomonRiyu.setBindPath("TANJIKAN_HOUMON_RIYU");
        tanjikanHomonRiyu.setMaxLength(25);
        tanjikanHomonRiyuLabel.setText("理由：");
        tanjikanHomonContainer.setLayout(new VRLayout());
        tanjikanHomonContainer.setText("短時間訪問の必要性");
        tanjikanHomonRiyuLabel.add(tanjikanHomonRiyu, VRLayout.FLOW);
        tanjikanHomonContainer.add(tanjikanHomon, VRLayout.FLOW);
        tanjikanHomonContainer.add(tanjikanHomonRiyuLabel, VRLayout.FLOW);
        getTokubetsuGroup().add(fukusuHomonContainer, VRLayout.NORTH);
        getTokubetsuGroup().add(tanjikanHomonContainer, VRLayout.NORTH);
        // 留意事項及び指示事項
        getTokubetsuRyuijiko().setColumns(100);
        getTokubetsuRyuijiko().setRows(14);
        getTokubetsuRyuijiko().setMaxRows(14);
        getTokubetsuRyuijiko().setMaxLength(700);
        getTokubetsuRyuijiko().fitTextArea();
        getTokubetsuRyuijiko().setShowSelectVisible(false);
        getTokubetsuRyuijiko().setCheckVisible(false);
        getTokubetsuRyuijiko().setTextBindPath("TOKUBETSU_RYUI");
        getTokubetsuRyuijiko().setCaption("");
        getTokubetsuRyuijiko().setTitle("　　 （700文字/14行以内）");
        getTokubetsuGroup().add(getTokubetsuRyuijiko(), VRLayout.NORTH);
        // 観察を要する項目
        getKansatuContainer().setText("特に観察を要する項目");
        getKansatuGroup().add(getKansatuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getKansatuGroup().add(getFukuyakuContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getSuibunContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getSeishinContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getShintaiContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getOtherContainer(), VRLayout.FLOW_RETURN);
        // １ 服薬確認
        getFukuyaku().setText("１ 服薬確認");
        getFukuyaku().setBindPath("FUKUYAKU_UMU");
        getFukuyakuContainer().add(getFukuyaku(), VRLayout.FLOW);
        // ２ 水分及び食物摂取の状況
        getSuibun().setText("２ 水分及び食物摂取の状況");
        getSuibun().setBindPath("SUIBUN_UMU");
        getSuibunContainer().add(getSuibun(), VRLayout.FLOW);
        // ３ 精神症状
        getSeishin().setText("３ 精神症状（観察が必要な項目：");
        getSeishin().setBindPath("SEISHIN_SYOUJYOU_UMU");
        getSeishinJikou().setIMEMode(InputSubset.KANJI);
        getSeishinJikou().setColumns(30);
        getSeishinJikou().setMaxLength(30);
        getSeishinJikou().setEnabled(false);
        getSeishinJikou().setBindPath("SEISHIN_SYOUJYOU");
        getSeishinEndCaption().setText(" ）");
        getSeishinContainer().add(getSeishin(), VRLayout.FLOW);
        getSeishinContainer().add(getSeishinJikou(), VRLayout.FLOW);
        getSeishinContainer().add(getSeishinEndCaption(), VRLayout.FLOW);
        // ４ 身体症状
        getShintai().setText("４ 身体症状（観察が必要な項目：");
        getShintai().setBindPath("SHINTAI_SYOUJYOU_UMU");
        getShintaiJikou().setIMEMode(InputSubset.KANJI);
        getShintaiJikou().setColumns(30);
        getShintaiJikou().setMaxLength(30);
        getShintaiJikou().setEnabled(false);
        getShintaiJikou().setBindPath("SHINTAI_SYOUJYOU");
        getShintaiEndCaption().setText(" ）");
        getShintaiContainer().add(getShintai(), VRLayout.FLOW);
        getShintaiContainer().add(getShintaiJikou(), VRLayout.FLOW);
        getShintaiContainer().add(getShintaiEndCaption(), VRLayout.FLOW);
        // ５ その他
        getOther().setText("５ その他（");
        getOther().setBindPath("KANSATU_OTHER_UMU");
        getOtherText().setIMEMode(InputSubset.KANJI);
        getOtherText().setColumns(40);
        getOtherText().setMaxLength(40);
        getOtherText().setEnabled(false);
        getOtherText().setBindPath("KANSATU_OTHER");
        getOtherEndCaption().setText(" ）");
        getOtherContainer().add(getOther(), VRLayout.FLOW);
        getOtherContainer().add(getOtherText(), VRLayout.FLOW);
        getOtherContainer().add(getOtherEndCaption(), VRLayout.FLOW);
        getSeishin().addItemListener(new ACFollowDisabledItemListener(new JComponent[] {getSeishinJikou()}));
        getShintai().addItemListener(new ACFollowDisabledItemListener(new JComponent[] {getShintaiJikou()}));
        getOther().addItemListener(new ACFollowDisabledItemListener(new JComponent[] {getOtherText()}));
    }

    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * @param dbm DBManager
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(fukusuHomonRiyu, IkenshoCommon.TEIKEI_PLURAL_VISIT_REASON);
        applyPoolTeikeibun(tanjikanHomonRiyu, IkenshoCommon.TEIKEI_SHROT_VISIT_REASON);
        applyPoolTeikeibun(getSeishinJikou(), IkenshoCommon.TEIKEI_SEISHIN_KANSATU);
        applyPoolTeikeibun(getShintaiJikou(), IkenshoCommon.TEIKEI_SHINTAI_KANSATU);
        applyPoolTeikeibun(getOtherText(), IkenshoCommon.TEIKEI_KANSATU_OTHER);
        fukusuHomonRiyu.setOptionComboBoxParameters("複数名訪問の必要性：理由", IkenshoCommon.TEIKEI_PLURAL_VISIT_REASON, 25);
        tanjikanHomonRiyu.setOptionComboBoxParameters("短時間訪問の必要性：理由", IkenshoCommon.TEIKEI_SHROT_VISIT_REASON, 25);
        getSeishinJikou().setOptionComboBoxParameters("精神症状（観察が必要な項目）", IkenshoCommon.TEIKEI_SEISHIN_KANSATU, 30);
        getShintaiJikou().setOptionComboBoxParameters("身体症状（観察が必要な項目）", IkenshoCommon.TEIKEI_SHINTAI_KANSATU, 30);
        getOtherText().setOptionComboBoxParameters("その他", IkenshoCommon.TEIKEI_KANSATU_OTHER, 40);
    }

    /**
     * fukuyaku を返します。
     * @return fukuyaku
     */
    protected ACIntegerCheckBox getFukuyaku() {
        if(fukuyaku == null){
            fukuyaku = new ACIntegerCheckBox();
        }
        return fukuyaku;
    }

    /**
     * fukuyakuConteiner を返します。
     * @return fukuyakuConteiner
     */
    protected ACLabelContainer getFukuyakuContainer() {
        if(fukuyakuConteiner == null){
            fukuyakuConteiner = new ACLabelContainer();
        }
        return fukuyakuConteiner;
    }

    /**
     * suibun を返します。
     * @return suibun
     */
    protected ACIntegerCheckBox getSuibun() {
        if(suibun == null){
            suibun = new ACIntegerCheckBox();
        }
        return suibun;
    }

    /**
     * suibunConteiner を返します。
     * @return suibunConteiner
     */
    protected ACLabelContainer getSuibunContainer() {
        if(suibunConteiner == null){
            suibunConteiner = new ACLabelContainer();
        }
        return suibunConteiner;
    }

    /**
     * seishin を返します。
     * @return seishin
     */
    protected ACIntegerCheckBox getSeishin() {
        if(seishin == null){
            seishin = new ACIntegerCheckBox();
        }
        return seishin;
    }

    /**
     * seishinJikou を返します。
     * @return seishinJikou
     */
    protected IkenshoOptionComboBox getSeishinJikou() {
        if(seishinJikou == null){
            seishinJikou = new IkenshoOptionComboBox();
        }
        return seishinJikou;
    }

    /**
     * seishinConteiner を返します。
     * @return seishinConteiner
     */
    protected ACLabelContainer getSeishinContainer() {
        if(seishinConteiner == null){
            seishinConteiner = new ACLabelContainer();
        }
        return seishinConteiner;
    }

    /**
     * shintai を返します。
     * @return shintai
     */
    protected ACIntegerCheckBox getShintai() {
        if(shintai == null){
            shintai = new ACIntegerCheckBox();
        }
        return shintai;
    }

    /**
     * shintaiJikou を返します。
     * @return shintaiJikou
     */
    protected IkenshoOptionComboBox getShintaiJikou() {
        if(shintaiJikou == null){
            shintaiJikou = new IkenshoOptionComboBox();
        }
        return shintaiJikou;
    }

    /**
     * shintaiConteiner を返します。
     * @return shintaiConteiner
     */
    protected ACLabelContainer getShintaiContainer() {
        if(shintaiConteiner == null){
            shintaiConteiner = new ACLabelContainer();
        }
        return shintaiConteiner;
    }

    /**
     * other を返します。
     * @return other
     */
    protected ACIntegerCheckBox getOther() {
        if(other == null){
            other = new ACIntegerCheckBox();
        }
        return other;
    }

    /**
     * otherText を返します。
     * @return otherText
     */
    protected IkenshoOptionComboBox getOtherText() {
        if(otherText == null){
            otherText = new IkenshoOptionComboBox();
        }
        return otherText;
    }

    /**
     * otherConteiner を返します。
     * @return otherConteiner
     */
    protected ACLabelContainer getOtherContainer() {
        if(otherConteiner == null){
            otherConteiner = new ACLabelContainer();
        }
        return otherConteiner;
    }

    /**
     * kansatuContainer を返します。
     * @return kansatuContainer
     */
    protected ACLabelContainer getKansatuContainer() {
        if(kansatuContainer == null){
            kansatuContainer = new ACLabelContainer();
        }
        return kansatuContainer;
    }

    /**
     * kansatuGroup を返します。
     * @return kansatuGroup
     */
    protected ACGroupBox getKansatuGroup() {
        if(kansatuGroup == null){
            kansatuGroup = new ACGroupBox();
        }
        return kansatuGroup;
    }
    /**
     * seishinEndCaption を返します。
     * @return seishinEndCaption
     */
    protected ACLabel getSeishinEndCaption() {
        if(seishinEndCaption == null){
            seishinEndCaption = new ACLabel();
        }
        return seishinEndCaption;
    }
    /**
     * shintaiEndCaption を返します。
     * @return shintaiEndCaption
     */
    protected ACLabel getShintaiEndCaption() {
        if(shintaiEndCaption == null){
            shintaiEndCaption = new ACLabel();
        }
        return shintaiEndCaption;
    }
    /**
     * otherEndCaption を返します。
     * @return otherEndCaption
     */
    protected ACLabel getOtherEndCaption() {
        if(otherEndCaption == null){
            otherEndCaption = new ACLabel();
        }
        return otherEndCaption;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End