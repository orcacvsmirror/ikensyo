package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoCare2H18 extends IkenshoIkenshoInfoCare2 {
    private ACLabelContainer outlooks = new ACLabelContainer();
    private VRLayout outlookLayout = new VRLayout();
    private ACGroupBox sickOutlookGroup;
    private ACClearableRadioButtonGroup outlook = new ACClearableRadioButtonGroup();
    // private NCParentHesesPanelContainer outlookServices = new
    // NCParentHesesPanelContainer();
    // private NCComboBox outlookService = new NCComboBox();
    private IkenshoAddWestClearableRadioButtonGroup sports;
    private ACParentHesesPanelContainer sportstHeses;
    private ACLabelContainer sportss;
    private ACComboBox sportsValue;

    // private VRListModelAdapter careServiceSportsModel;

    /**
     * 運動についてコンテナを返します。
     * 
     * @return 運動についてコンテナ
     */
    protected ACLabelContainer getSportss() {
        if (sportss == null) {
            sportss = new ACLabelContainer();
        }
        return sportss;
    }

    protected VRListModelAdapter getCareServiceListModel() {
        VRListModelAdapter adapt = super.getCareServiceListModel();
        adapt.setData(0, "特になし");
        adapt.setData(1, "あり");
        return adapt;
    }

    // /**
    // * 運動についてラジオグループモデルを返します。
    // * @return 運動についてラジオグループモデル
    // */
    // protected VRListModelAdapter getCareServiceSportsModel() {
    // if (careServiceSportsModel == null) {
    // careServiceSportsModel = new VRListModelAdapter(new
    // VRArrayList(Arrays.asList(new
    // String[] {"特になし ", "あり"})));
    // }
    // return careServiceSportsModel;
    // }

    /**
     * 運動についてを返します。
     * 
     * @return 運動について
     */
    protected ACComboBox getSportsValue() {
        if (sportsValue == null) {
            sportsValue = new ACComboBox();
        }
        return sportsValue;
    }

    /**
     * 運動についてラジオグループを返します。
     * 
     * @return 運動についてラジオグループ
     */
    protected IkenshoAddWestClearableRadioButtonGroup getSports() {
        if (sports == null) {
            sports = new IkenshoAddWestClearableRadioButtonGroup();
        }
        return sports;
    }

    /**
     * 運動について括弧パネルを返します。
     * 
     * @return 運動について括弧パネル
     */
    protected ACParentHesesPanelContainer getSportstHeses() {
        if (sportstHeses == null) {
            sportstHeses = new ACParentHesesPanelContainer();
        }
        return sportstHeses;
    }

    /**
     * 予後の見通しグループを返します。
     * 
     * @return 予後の見通しグループ
     */
    protected ACGroupBox getOutlookGroup() {
        if (sickOutlookGroup == null) {
            sickOutlookGroup = new ACGroupBox();
        }
        return sickOutlookGroup;
    }

    protected void addRyuijiko() {
        getServiceGroup().add(getKetsuattsus(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getSesshokus(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getEnges(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getMoves(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getSportss(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getServiceOthers(), VRLayout.FLOW_RETURN);
    }

    public VRMap createSourceInnerBindComponent() {
        VRMap map = super.createSourceInnerBindComponent();
        map.setData("UNDOU", new Integer(1));

        return map;
    }

    protected void addNeedMnagement() {
        VRLayout vl = new VRLayout();
        vl.setHgap(0);
        vl.setLabelMargin(10);
        vl.setHgrid(400);
        getNeedManagementGroup().setLayout(vl);

        getNeedManagementGroup().add(getHoumonsShinryous(),
                VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getHoumonsKangos(),
                VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getHoumonShikaShinryous(),
                VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getHoumonYakuzais(),
                VRLayout.FLOW_INSETLINE_RETURN);

        getNeedManagementGroup().add(getHoumonRehas(), VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getTankiNyuushyoKaigos(),
                VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getHoumonShikaEiseis(),
                VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getHoumonEiyous(),
                VRLayout.FLOW_INSETLINE_RETURN);

        getNeedManagementGroup().add(getTsuushyoRehas(),
                VRLayout.FLOW_INSETLINE);
        getNeedManagementGroup().add(getHoumonOthers(),
                VRLayout.FLOW_INSETLINE_RETURN);
        getNeedManagementGroup().add(getHoumonAbstract1(), VRLayout.FLOW);
        getNeedManagementGroup().add(getHoumonAbstract2(), VRLayout.FLOW);

    }

    public boolean noControlWarning() throws Exception {
        if (!super.noControlWarning()) {
            return false;
        }

        if (getSports().getSelectedIndex() == 2) {
            if (isNullText(getSportsValue().getEditor().getItem())) {
                if (ACMessageBox.show("「留意事項（運動）」で未記入があります。\nこのまま実行しますか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION) != ACMessageBox.RESULT_OK) {
                    getSports().requestChildFocus();
                    return false;
                }
            }
        }

        return true;
    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        applyPoolTeikeibun(getSportsValue(),
                IkenshoCommon.TEIKEI_CARE_SERVICE_UNDOU_NAME);
        // applyPoolTeikeibun(outlookService,
        // IkenshoCommon.TEIKEI_OUTLOOK_SERVISE_NAME);

    }

    /**
     * コンストラクタです。
     */
    public IkenshoIkenshoInfoCare2H18() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        getSports().addListSelectionListener(
                new ACFollowDisableSelectionListener(new JComponent[] {
                        getSportsValue(), getSportstHeses() }, 1));

    }

    protected void setCare2KanseEventListener() {
        getCare2Kansen().addListSelectionListener(
                new ACFollowDisableSelectionListener(
                        new JComponent[] { getCare2KansenName() }, 1));
    }

    protected void addCare2Kansen() {
        getCare2Kansen().add(getCare2KansenName(), null, 2);
    }

    private void jbInit() throws Exception {
        getTitle().setText("４．生活機能とサービスに関する意見（続き２）");
        outlookLayout.setAlignment(FlowLayout.LEFT);
        getOutlookGroup().setText("サービス利用による生活機能の維持・改善の見通し");
        getOutlookGroup().setLayout(outlookLayout);
        outlook.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "期待できる", "期待できない", "不明" }))));
        outlook.setBindPath("VITAL_FUNCTIONS_OUTLOOK");
        // outlookService.setMinimumSize(new Dimension(240, 19));
        // outlookService.setPreferredSize(new Dimension(300, 19));
        // outlookService.setBindPath("IMPRO_SERVICE");
        // outlookService.setMaxLength(30);
        // outlookService.setIMEMode(InputSubset.KANJI);
        // outlookServices.setBeginText("改善への寄与が期待できるサービス→ （");
        getSesshokus().setText("・摂食");
        getKetsuattsus().setText("・血圧");
        getMoves().setText("・移動");
        getEnges().setText("・嚥下");
        getSportss().setText("・運動");
        getSports().setUseClearButton(false);
        getSportsValue().setEnabled(false);
        getSportsValue().setPreferredSize(new Dimension(380, 19));
        getSportsValue().setBindPath("UNDOU_RYUIJIKOU");
        getSportsValue().setIMEMode(InputSubset.KANJI);
        getSportsValue().setMaxLength(30);
        getSports().setBindPath("UNDOU");
        getOutlookGroup().add(outlooks, VRLayout.FLOW_INSETLINE_RETURN);
        outlooks.add(outlook, null);
        // getOutlookGroup().add(outlookServices,
        // VRLayout.FLOW_INSETLINE_RETURN);

        getServiceGroup().setText("サービス提供時における医学的観点からの留意事項（30文字以内。「その他」は50文字）");

        //2006/02/07[Tozo Tanaka] : add begin 
        getNeedManagementGroup().setText("医学的管理の必要性（特に必要性の高いものには下線を引いて下さい。予防給付により提供されるサービスを含みます。）");
        getCare2HoumonOther().setText("その他の医療系サービス");
        //2006/02/07[Tozo Tanaka] : add end 
        getSports().setModel(getCareServiceListModel());
        // getSports().setModel(getCareServiceSportsModel());
        getSports().setUseClearButton(false);

        getCare2Kansen().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "無", "有", "不明" }))));
        getCare2Kansen().setValues(new int[] { 2, 1, 3 });

        // outlookServices.add(outlookService, null);
        sportss.add(getSports(), null);
        getSports().add(getSportstHeses(), VRLayout.CLIENT);
        // getSports().add(getSportstHeses(), null, 2);
        getSportstHeses().add(getSportsValue(), VRLayout.CLIENT);

        addInnerBindComponent(getSportsValue());
    }

    protected void addGroup() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getOutlookGroup(), VRLayout.NORTH);
        this.add(getNeedManagementGroup(), VRLayout.NORTH);
        this.add(getServiceGroup(), VRLayout.NORTH);
        this.add(getKansenGroup(), VRLayout.NORTH);
    }

}
