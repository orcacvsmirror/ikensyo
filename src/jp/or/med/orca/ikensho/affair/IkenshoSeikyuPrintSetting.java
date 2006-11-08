package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Date;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** <HEAD_IKENSYO> */

public class IkenshoSeikyuPrintSetting extends IkenshoDialog implements
        ActionListener, ListSelectionListener {
    /**
     * 印刷ボタン定数
     */
    public static final int BUTTON_PRINT = 1;
    /**
     * キャンセルボタン定数
     */
    public static final int BUTTON_CANCEL = 2;
    private int buttonAction = 0;
    private int output_pattern = 0;

    private VRPanel contents = new VRPanel();
    // 請求パターン・請求先
    private ACGroupBox billPatternGroup = new ACGroupBox();
    // /請求パターン
    private ACLabelContainer billPatterns = new ACLabelContainer();
    private VRLabel billPattern = new VRLabel();
    private VRLabel billPatternHead = new VRLabel();
    // /意見書作成料請求先
    private ACLabelContainer toCreateCosts = new ACLabelContainer();
    VRLabel toCreateCost = new VRLabel();
    VRLabel toCreateCostCode = new VRLabel();
    private ACParentHesesPanelContainer toCreateCostCodeHeses = new ACParentHesesPanelContainer();
    private VRLabel toCreateCostHead = new VRLabel();
    // /診察・検査料請求先
    private ACLabelContainer toCheckCosts = new ACLabelContainer();
    VRLabel toCheckCost = new VRLabel();
    VRLabel toCheckCostCode = new VRLabel();
    private ACParentHesesPanelContainer toCheckCostCodeHeses = new ACParentHesesPanelContainer();
    private VRLabel toCheckCostHead = new VRLabel();

    // 意見書作成料請求
    private ACGroupBox billCreateCostsGroup = new ACGroupBox();
    // 総括書
    private ACGroupBox createSummaryGroup = new ACGroupBox();
    // //総括書ラジオボタン
    ACClearableRadioButtonGroup createSummaryRadio = new ACClearableRadioButtonGroup();
    // //総括書チェックコンテナー
    private ACLabelContainer createSummaryPrints = new ACLabelContainer();
    // ///総括書チェック
    ACIntegerCheckBox createSummaryPrint = new ACIntegerCheckBox();
    // /明細書種類
    private ACGroupBox createDetailsGroup = new ACGroupBox();
    // //明細書ラジオボタン
    ACClearableRadioButtonGroup createDetailsRadio = new ACClearableRadioButtonGroup();
    // //明細書種類チェックコンテナー
    private ACLabelContainer createDetailsPrints = new ACLabelContainer();
    // ///明細書種類チェックボックス
    ACIntegerCheckBox createDetailsPrint = new ACIntegerCheckBox();

    // 診察・検査料請求
    private ACGroupBox billInspectionCostsGroup = new ACGroupBox();
    // 総括書
    private ACGroupBox inspectionSummaryGroup = new ACGroupBox();
    // //総括書ラジオボタン
    ACClearableRadioButtonGroup inspectionSummaryRadio = new ACClearableRadioButtonGroup();
    // //総括書チェックコンテナー
    private ACLabelContainer inspectionSummaryPrints = new ACLabelContainer();
    // ///総括書チェック
    ACIntegerCheckBox inspectionSummaryPrint = new ACIntegerCheckBox();
    // /明細書種類
    private ACGroupBox inspectionDetailsGroup = new ACGroupBox();
    // //明細書ラジオボタン
    ACClearableRadioButtonGroup inspectionDetailsRadio = new ACClearableRadioButtonGroup();
    // //明細書種類チェックコンテナー
    private ACLabelContainer inspectionDetailsPrints = new ACLabelContainer();
    // ///明細書種類チェックボックス
    ACIntegerCheckBox inspectionDetailsPrint = new ACIntegerCheckBox();

    // /請求書出力日付
    private ACGroupBox billPrintDateGroup = new ACGroupBox();
    private VRPanel dateRange = new VRPanel();
    // //今日の日付・日付消去ボタン
    private VRPanel billPrintDateButtons = new VRPanel();
    private ACButton nowDate = new ACButton();
    private ACButton clearDate = new ACButton();
    // 出力日付範囲
    protected ACLabelContainer billPrintDatesRange = new ACLabelContainer();
    protected ACParentHesesPanelContainer billPrintDateRangeHeses = new ACParentHesesPanelContainer();
    protected IkenshoEraDateTextField billPrintDateRangeFrom = new IkenshoEraDateTextField();
    protected IkenshoEraDateTextField billPrintDateRangeTo = new IkenshoEraDateTextField();
    // //出力日付
    protected ACParentHesesPanelContainer billPrintDateHeses = new ACParentHesesPanelContainer();
    protected ACLabelContainer billPrintDates = new ACLabelContainer();
    protected IkenshoEraDateTextField billPrintDate = new IkenshoEraDateTextField();
    // //何月分
    protected ACParentHesesPanelContainer billDetailPrintDateHeses = new ACParentHesesPanelContainer();
    protected IkenshoEraDateTextField billDetailPrintDate = new IkenshoEraDateTextField();

    // ボタンパネル
    private VRPanel buttons = new VRPanel();
    private ACButton ok = new ACButton();
    private ACButton cancel = new ACButton();

    private VRLayout contentsLayout = new VRLayout();
    private VRLayout billPatternGroupLayout = new VRLayout();
    private VRLayout billCreateCostsGroupLayout = new VRLayout();
    private VRLayout billPanelLayout = new VRLayout();
    private VRLayout createSummaryGroupLayout = new VRLayout();
    private VRLayout createSummaryRadioLayout = new VRLayout();
    private VRLayout createDetailsRadioLayout = new VRLayout();
    private VRLayout billInspectionGroupLayout = new VRLayout();
    private VRLayout inspectionSummaryGroupLayout = new VRLayout();
    private VRLayout inspectionSummaryRadioLayout = new VRLayout();
    private VRLayout inspectionDetailsRadioLayout = new VRLayout();

    /**
     * コンストラクタです。
     * 
     * @throws HeadlessException 初期化例外
     */
    public IkenshoSeikyuPrintSetting() throws HeadlessException {
        super(ACFrame.getInstance());
        // super(owner, title, modal);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        // IkenshoSeikyuPrintSetting_actionAdapter actionAdapter = new
        // IkenshoSeikyuPrintSetting_actionAdapter(this);
        // IkenshoSeikyuPrintSetting_listSelectionAdapter listSelectionAdapter =
        // new IkenshoSeikyuPrintSetting_listSelectionAdapter(this);
        contentsLayout.setFitVLast(true);
        contentsLayout.setFitHLast(true);
        contents.setLayout(contentsLayout);
        billPatternGroup.setLayout(billPatternGroupLayout);
        billPrintDateGroup.setLayout(new VRLayout());
        billPrintDateButtons.setLayout(new VRLayout());

        billCreateCostsGroupLayout.setFitHLast(true);
        billCreateCostsGroupLayout.setAutoWrap(false);
        billCreateCostsGroupLayout.setFitVLast(true);
        billCreateCostsGroup.setLayout(billCreateCostsGroupLayout);
        createSummaryGroupLayout.setHgrid(140);
        createSummaryGroup.setLayout(createSummaryGroupLayout);
        createDetailsGroup.setLayout(new VRLayout());

        billInspectionGroupLayout.setFitHLast(true);
        billInspectionGroupLayout.setAutoWrap(false);
        billInspectionGroupLayout.setFitVLast(true);
        billInspectionCostsGroup.setLayout(billInspectionGroupLayout);
        inspectionSummaryGroupLayout.setHgrid(140);
        inspectionSummaryGroup.setLayout(inspectionSummaryGroupLayout);
        inspectionDetailsGroup.setLayout(new VRLayout());

        billPanelLayout.setFitVLast(true);
        billPanelLayout.setFitHLast(true);
        billPanelLayout.setAutoWrap(false);

        this.setTitle("「請求書」印刷設定");

        // ---請求パターン・請求先
        billPatternGroup.setText("請求パターン・請求先");
        billPatternGroup.setForeground(Color.BLUE);
        billPatternGroupLayout.setFitHGrid(true);
        billPatternGroupLayout.setHgrid(200);

        billPatterns.setText("請求パターン");
        billPatternHead.setText("：");
        billPatterns.add(billPatternHead, null);
        billPatterns.add(billPattern, null);

        toCreateCosts.setText("意見書作成料請求先(番号)");
        toCreateCostHead.setText("：");
        toCreateCost.setBindPath("ISS_INSURER_NM");
        toCreateCostCode.setBindPath("ISS_INSURER_NO");
        toCreateCostCodeHeses.add(toCreateCostCode);
        toCreateCosts.add(toCreateCostHead, null);
        toCreateCosts.add(toCreateCost, null);
        toCreateCosts.add(toCreateCostCodeHeses, null);

        toCheckCosts.setText("診察・検査料請求先(番号)");
        toCheckCostHead.setText("：");
        toCheckCost.setBindPath("SKS_INSURER_NM");
        toCheckCostCode.setBindPath("SKS_INSURER_NO");
        toCheckCostCodeHeses.add(toCheckCostCode);
        toCheckCosts.add(toCheckCostHead, null);
        toCheckCosts.add(toCheckCost, null);
        toCheckCosts.add(toCheckCostCodeHeses, null);

        billPatternGroup.add(billPatterns, VRLayout.FLOW_INSETLINE_RETURN);
        billPatternGroup.add(toCreateCosts, VRLayout.FLOW_INSETLINE_RETURN);
        billPatternGroup.add(toCheckCosts, VRLayout.FLOW_INSETLINE_RETURN);

        // ---意見書作成料 請求書
        billCreateCostsGroup.setText("意見書作成料 請求書");
        billCreateCostsGroup.setForeground(Color.BLUE);
        createSummaryGroup.setText("総括書");
        createSummaryRadioLayout.setAutoWrap(false);
        createSummaryRadioLayout.setHgap(2);
        createSummaryRadio.setLayout(createSummaryRadioLayout);
        createSummaryRadio.setUseClearButton(false);
        createSummaryRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "総括書あり", "総括書なし" }))));
        createSummaryRadio.setBindPath("SOUKATUHYOU_PRT");
        createSummaryRadio.addListSelectionListener(this);

        createSummaryPrint.setText("振込先印刷する");
        createSummaryPrint.setBindPath("SOUKATU_FURIKOMI_PRT");
        createSummaryPrints.add(createSummaryPrint, null);

        ACLabelContainer panel1 = new ACLabelContainer();
        panel1.add(createSummaryRadio, null);
        createSummaryGroup.add(panel1, VRLayout.FLOW_RETURN);
        createSummaryGroup.add(createSummaryPrints, VRLayout.FLOW_RETURN);

        createDetailsGroup.setText("明細書種類");
        createDetailsRadioLayout.setAutoWrap(false);
        createDetailsRadioLayout.setHgap(2);
        createDetailsRadio.setLayout(createDetailsRadioLayout);
        createDetailsRadio.setUseClearButton(false);
        createDetailsRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "明細書のみ", "一覧表のみ", "明細書と一覧表" }))));
        createDetailsRadio.setBindPath("MEISAI_KIND");

        createDetailsPrint.setText("振込先印刷する");
        createDetailsPrint.setBindPath("FURIKOMISAKI_PRT");

        createDetailsPrints.add(createDetailsPrint, null);
        ACLabelContainer panel2 = new ACLabelContainer();
        panel2.add(createDetailsRadio, null);
        createDetailsGroup.add(panel2, VRLayout.FLOW_RETURN);
        createDetailsGroup.add(createDetailsPrints, VRLayout.FLOW_RETURN);

        billCreateCostsGroup.add(createSummaryGroup, VRLayout.WEST);
        billCreateCostsGroup.add(createDetailsGroup, VRLayout.CLIENT);

        // ---診察・検査料 請求書
        billInspectionCostsGroup.setText("診察・検査料 請求書");
        billInspectionCostsGroup.setForeground(Color.BLUE);
        inspectionSummaryGroup.setText("総括書");
        inspectionSummaryRadioLayout.setAutoWrap(false);
        inspectionSummaryRadioLayout.setHgap(2);
        inspectionSummaryRadio.setLayout(inspectionSummaryRadioLayout);
        inspectionSummaryRadio.setUseClearButton(false);
        inspectionSummaryRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "総括書あり", "総括書なし" }))));
        inspectionSummaryRadio.setBindPath("SOUKATUHYOU_PRT2");
        inspectionSummaryRadio.addListSelectionListener(this);

        inspectionSummaryPrint.setText("振込先印刷する");
        inspectionSummaryPrint.setBindPath("SOUKATU_FURIKOMI_PRT2");
        inspectionSummaryPrints.add(inspectionSummaryPrint, null);
        ACLabelContainer panel3 = new ACLabelContainer();
        panel3.add(inspectionSummaryRadio, null);
        inspectionSummaryGroup.add(panel3, VRLayout.FLOW_RETURN);
        inspectionSummaryGroup.add(inspectionSummaryPrints,
                VRLayout.FLOW_RETURN);
        inspectionDetailsGroup.setText("明細書種類");
        inspectionDetailsRadioLayout.setAutoWrap(false);
        inspectionDetailsRadioLayout.setHgap(2);
        inspectionDetailsRadio.setLayout(inspectionDetailsRadioLayout);
        inspectionDetailsRadio.setUseClearButton(false);
        inspectionDetailsRadio.setModel(new VRListModelAdapter(new VRArrayList(
                Arrays.asList(new String[] { "明細書のみ", "一覧表のみ", "明細書と一覧表" }))));
        inspectionDetailsRadio.setBindPath("MEISAI_KIND2");

        inspectionDetailsPrint.setText("振込先印刷する");
        inspectionDetailsPrint.setBindPath("FURIKOMISAKI_PRT2");

        inspectionDetailsPrints.add(inspectionDetailsPrint, null);
        ACLabelContainer panel4 = new ACLabelContainer();
        panel4.add(inspectionDetailsRadio, null);
        inspectionDetailsGroup.add(panel4, VRLayout.FLOW_RETURN);
        inspectionDetailsGroup.add(inspectionDetailsPrints,
                VRLayout.FLOW_RETURN);

        billInspectionCostsGroup.add(inspectionSummaryGroup, VRLayout.WEST);
        billInspectionCostsGroup.add(inspectionDetailsGroup, VRLayout.CLIENT);

        // ---請求明細振込み先／請求出力日付
        billPrintDateGroup.setText("出力日付");
        billPrintDateGroup.setForeground(Color.BLUE);
        // 出力範囲日付
        VRLayout billPrintDatesRangeLayout = new VRLayout();
        billPrintDatesRangeLayout.setAutoWrap(false);
        billPrintDatesRange.setLayout(billPrintDatesRangeLayout);
        billPrintDateRangeHeses.setBeginText("：");
        billPrintDateRangeHeses.setEndText("～");
        billPrintDatesRange.setText("出力範囲日付");
        billPrintDateRangeFrom.setAgeVisible(false);
        billPrintDateRangeFrom.setAllowedFutureDate(true);
        billPrintDateRangeFrom
                .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        billPrintDateRangeTo.setAgeVisible(false);
        billPrintDateRangeTo.setAllowedFutureDate(true);
        billPrintDateRangeTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        ACLabelContainer panel5 = new ACLabelContainer();
        panel5.add(billPrintDateRangeFrom, null);
        billPrintDateRangeHeses.add(panel5, null);
        ACLabelContainer panel6 = new ACLabelContainer();
        panel6.add(billPrintDateRangeTo, null);
        billPrintDatesRange.add(billPrintDateRangeHeses, VRLayout.FLOW);
        billPrintDatesRange.add(panel6, VRLayout.FLOW);

        billPrintDatesRange
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        billPrintDatesRange
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        billPrintDatesRange.setContentAreaFilled(true);
        // 日付出力
        VRLayout billPrintDatesLayout = new VRLayout();
        billPrintDatesLayout.setAutoWrap(false);
        billPrintDates.setLayout(billPrintDatesLayout);
        billPrintDateHeses.setBeginText("：");
        billPrintDateHeses.setEndText("");
        billPrintDates.setText("出力日付　　");
        billPrintDate.setAgeVisible(false);
        billPrintDate.setAllowedFutureDate(true);
        billPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        billDetailPrintDate.setAgeVisible(false);
        billDetailPrintDate.setAllowedFutureDate(true);
        billDetailPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        billDetailPrintDate.setDayVisible(false);
        billDetailPrintDateHeses.setEndText("分)");
        ACLabelContainer panel7 = new ACLabelContainer();
        panel7.add(billPrintDate, null);
        billPrintDateHeses.add(panel7, null);
        ACLabelContainer panel8 = new ACLabelContainer();
        panel8.add(billDetailPrintDate, null);
        billDetailPrintDateHeses.add(panel8, null);
        billPrintDates.add(billPrintDateHeses, VRLayout.FLOW);
        billPrintDates.add(billDetailPrintDateHeses, VRLayout.FLOW);

        billPrintDates
                .setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        billPrintDates
                .setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
        billPrintDates.setContentAreaFilled(true);

        dateRange.setLayout(new VRLayout());
        dateRange
                .add(billPrintDatesRange, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);
        dateRange.add(billPrintDates, VRLayout.FLOW_DOUBLEINSETLINE_RETURN);

        nowDate.setMnemonic('D');
        nowDate.setText("今日の日付(D)");
        nowDate.addActionListener(this);
        clearDate.setText("日付消去(E)");
        clearDate.setMnemonic('E');
        clearDate.addActionListener(this);
        billPrintDateButtons.add(nowDate, VRLayout.NORTH);
        billPrintDateButtons.add(clearDate, VRLayout.NORTH);

        billPrintDateGroup.add(dateRange, VRLayout.CLIENT);
        billPrintDateGroup.add(billPrintDateButtons, VRLayout.EAST);

        // ボタン
        ok.setMnemonic('O');
        ok.setText("印刷(O)");
        ok.addActionListener(this);
        cancel.setText("キャンセル(C)");
        cancel.addActionListener(this);
        cancel.setMnemonic('C');
        buttons.add(ok, null);
        buttons.add(cancel, null);

        // パネルに追加する
        this.getContentPane().add(contents, BorderLayout.CENTER);
        contents.add(billPatternGroup, VRLayout.NORTH);
        contents.add(billCreateCostsGroup, VRLayout.NORTH);
        contents.add(billInspectionCostsGroup, VRLayout.NORTH);
        contents.add(billPrintDateGroup, VRLayout.NORTH);
        contents.add(buttons, VRLayout.SOUTH);
    }

    // コンポーネントの初期化
    private void init() {
        // ウィンドウのサイズ
        setSize(new Dimension(730, 440));
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

    }

    public int showDialog(String insurerNo, String insurerType) throws Exception {
        doSelect(insurerNo, insurerType);
        setTodayAll(true);
        super.setVisible(true);
        return buttonAction;
    }

    /**
     * DBからデータを取得します。
     * 
     * @throws Exception
     */
    private void doSelect(String insurerNo, String insurerType) throws Exception {

        // キーを元に、DBからデータを取得
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" SEIKYUSHO_OUTPUT_PATTERN,");
        sb.append(" ISS_INSURER_NO,");
        sb.append(" ISS_INSURER_NM,");
        sb.append(" SKS_INSURER_NO,");
        sb.append(" SKS_INSURER_NM,");
        sb.append(" SOUKATUHYOU_PRT,");
        sb.append(" MEISAI_KIND,");
        sb.append(" FURIKOMISAKI_PRT,");
        sb.append(" SOUKATU_FURIKOMI_PRT,");
        sb.append(" SOUKATUHYOU_PRT2,");
        sb.append(" MEISAI_KIND2,");
        sb.append(" FURIKOMISAKI_PRT2,");
        sb.append(" SOUKATU_FURIKOMI_PRT2");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" WHERE");
        sb.append(" INSURER_NO='" + insurerNo + "'");
        sb.append(" AND(INSURER_TYPE=" + insurerType+")");
        sb.append(" ORDER BY");
        sb.append(" INSURER_NO");
        VRArrayList insurerArray = (VRArrayList) dbm
                .executeQuery(sb.toString());

        if (insurerArray.getDataSize() != 1) {
            throw new Exception("保険者の情報取得失敗:取得件数" + insurerArray.getDataSize());
        }
        VRMap map = (VRMap) insurerArray.get(0);

        // 検索結果をバインド
        contents.setSource(map);
        contents.bindSource();

        // 請求パターンの表示
        output_pattern = Integer.parseInt(String.valueOf(VRBindPathParser.get(
                "SEIKYUSHO_OUTPUT_PATTERN", map)));
        setOutputPattern(output_pattern);
        toCreateCostCodeHeses.setVisible(!"".equals(String.valueOf(VRBindPathParser.get(
                "ISS_INSURER_NO", map))));
        // 意見書作成料・検査料(1枚)の時は、検査料請求対象に意見書作成料請求対象を表示する。
        if (output_pattern == 1) {
            toCheckCost.setText(String.valueOf(VRBindPathParser.get(
                    "ISS_INSURER_NM", map)));
            toCheckCostCode.setText(String.valueOf(VRBindPathParser.get(
                    "ISS_INSURER_NO", map)));
        } else {
            toCheckCostCodeHeses.setVisible(!"".equals(String.valueOf(VRBindPathParser.get(
                    "SKS_INSURER_NO", map))));
        }
    }

    private void setOutputPattern(int outputPattern) throws Exception {
        switch (outputPattern) {
        case 1:
            billPattern.setText("意見書作成料・検査料(1枚)");
            turnGrpPnl(true, false);
            billCreateCostsGroup.setText("意見書作成料／診察・検査料 請求書");
            // ラジオボタンの選択状態によるチェックボックスのEnebled変更
            turnCheckEnabled(createSummaryPrint, createSummaryRadio
                    .getSelectedIndex());
            break;
        case 2:
            billPattern.setText("意見書作成料(1枚)・検査料(1枚)");
            turnGrpPnl(true, true);
            // ラジオボタンの選択状態によるチェックボックスのEnebled変更
            turnCheckEnabled(createSummaryPrint, createSummaryRadio
                    .getSelectedIndex());
            turnCheckEnabled(inspectionSummaryPrint, inspectionSummaryRadio
                    .getSelectedIndex());

            break;
        case 3:
            billPattern.setText("意見書作成料のみ");
            turnGrpPnl(true, false);
            // ラジオボタンの選択状態によるチェックボックスのEnebled変更
            turnCheckEnabled(createSummaryPrint, createSummaryRadio
                    .getSelectedIndex());

            break;
        case 4:
            billPattern.setText("検査料のみ");
            turnGrpPnl(false, true);
            // ラジオボタンの選択状態によるチェックボックスのEnebled変更
            turnCheckEnabled(inspectionSummaryPrint, inspectionSummaryRadio
                    .getSelectedIndex());

            break;
        }
    }

    /**
     * 請求書発行パターンを返却します。 <BR>
     * 
     * @return int 請求書発行パターン
     */
    public int getOutputPattern() {
        return output_pattern;
    }

    private void turnGrpPnl(boolean iken, boolean sinryo) throws Exception {
        billCreateCostsGroup.setEnabled(iken);
        createSummaryGroup.setEnabled(iken);
        createSummaryRadio.setEnabled(iken);
        createSummaryPrint.setEnabled(iken);
        createDetailsGroup.setEnabled(iken);
        createDetailsRadio.setEnabled(iken);
        createDetailsPrint.setEnabled(iken);
        // 意見書作成料が使用不可であれば、設定した値を破棄する。
        if (!iken) {
            billCreateCostsGroup.setSource((VRBindSource) billCreateCostsGroup
                    .createSource());
            billCreateCostsGroup.bindSource();
        }

        billInspectionCostsGroup.setEnabled(sinryo);
        inspectionSummaryGroup.setEnabled(sinryo);
        inspectionSummaryRadio.setEnabled(sinryo);
        inspectionSummaryPrint.setEnabled(sinryo);
        inspectionDetailsGroup.setEnabled(sinryo);
        inspectionDetailsRadio.setEnabled(sinryo);
        inspectionDetailsPrint.setEnabled(sinryo);

        // 請求書作成料が使用不可であれば、設定した値を破棄する。
        if (!sinryo) {
            billInspectionCostsGroup
                    .setSource((VRBindSource) billInspectionCostsGroup
                            .createSource());
            billInspectionCostsGroup.bindSource();
        }

    }

    private void setTodayAll(boolean startDefault) throws Exception {
        Date today = new Date();
        if (startDefault) {
            setDefaultDay(billPrintDateRangeFrom);
        }
        billPrintDateRangeTo.setDate(today);
        billPrintDate.setDate(today);
        billDetailPrintDate.setDate(today);
        billDetailPrintDate.setDay("");
    }

    private void setDefaultDayAll() throws Exception {
        setDefaultDay(billPrintDateRangeFrom);
        setDefaultDay(billPrintDateRangeTo);
        setDefaultDay(billPrintDate);
        setDefaultDay(billDetailPrintDate);
    }

    private void setDefaultDay(IkenshoEraDateTextField date) {
        date.setDate(new Date());
        date.setYear("");
        date.setMonth("");
        date.setDay("");
    }

    private void turnCheckEnabled(ACIntegerCheckBox check, int selectIndex) {
        if (check != null) {
            switch (selectIndex) {
            case 1:
                check.setEnabled(true);
                break;
            case 2:
                check.setEnabled(false);
                break;
            }
        }
    }

    /**
     * 入力チェック制御
     */
    private boolean isValidInput() throws Exception {
        boolean outputFromInput = false;
        boolean outputToInput = false;
        // ---日付入力チェック
        // 出力範囲開始
        if (isTaisyoKikanInput(billPrintDateRangeFrom)) {
            billPrintDateRangeFrom
                    .setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (billPrintDateRangeFrom.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billPrintDateRangeFrom.transferFocus();
                ACMessageBox.showExclamation("開始日付の入力に誤りがあります。");
                billPrintDateRangeFrom
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billPrintDateRangeFrom
                    .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            outputFromInput = true;
        }
        // 出力範囲終了
        if (isTaisyoKikanInput(billPrintDateRangeTo)) {
            billPrintDateRangeTo
                    .setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (billPrintDateRangeTo.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billPrintDateRangeTo.transferFocus();
                ACMessageBox.showExclamation("終了日付の入力に誤りがあります。");
                billPrintDateRangeTo
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billPrintDateRangeTo
                    .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            outputToInput = true;
        }
        // 出力日付
        if (isTaisyoKikanInput(billPrintDate)) {
            billPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if (billPrintDate.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billPrintDate.transferFocus();
                ACMessageBox.showExclamation("出力日付の入力に誤りがあります。");
                billPrintDate
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billPrintDate.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }
        // 何月分
        if (isTaisyoKikanInput(billDetailPrintDate)) {
            billDetailPrintDate
                    .setRequestedRange(IkenshoEraDateTextField.RNG_MONTH);
            if (billDetailPrintDate.getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                billDetailPrintDate.transferFocus();
                ACMessageBox.showExclamation("日付に誤りがあります。");
                billDetailPrintDate
                        .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                return false;
            }
            billDetailPrintDate
                    .setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }
        // 日付の前後関係チェック
        if (outputFromInput && outputToInput) {
            if (billPrintDateRangeFrom.getDate().after(
                    billPrintDateRangeTo.getDate())) {
                billPrintDateRangeFrom.transferFocus();
                ACMessageBox.showExclamation("開始日付と終了日付が逆転しています。");
                return false;
            }
        }
        return true;
    }

    private boolean isTaisyoKikanInput(IkenshoEraDateTextField date) {
        return (!date.getEra().equals("") && !date.getYear().equals(""));
    }

    // ウィンドウが閉じられたときにDisposeするようにオーバーライド
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
            buttonAction = BUTTON_CANCEL;
        }
    }

    /**
     * ウィンドウを閉じる
     */
    protected void closeWindow() {
        // 自身を破棄する
        //2006-09-20 begin-replace Tozo TANAKA
        //画面破棄後に一覧からコンボの値が参照されてしまうため、破棄前に退避して差し戻す。
        //this.dispose();
        String from = billPrintDateRangeFrom.getEra();
        String to = billPrintDateRangeTo.getEra();
        String print = billPrintDate.getEra();
        String detail = billDetailPrintDate.getEra();
        this.dispose();
        try{
            billPrintDateRangeFrom.reloadEras();
            billPrintDateRangeTo.reloadEras();
            billPrintDate.reloadEras();
            billDetailPrintDate.reloadEras();
        }catch(Exception ex){
            
        }
        billPrintDateRangeFrom.setEra(from);
        billPrintDateRangeTo.setEra(to);
        billPrintDate.setEra(print);
        billDetailPrintDate.setEra(detail);
        //2006-09-20 end-replace Tozo TANAKA
    }

    public void actionPerformed(ActionEvent e) {
        Object eventSource = e.getSource();
        try {
            if (eventSource.equals(nowDate)) {
                setTodayAll(false);
            } else if (eventSource.equals(clearDate)) {
                setDefaultDayAll();
            } else if (eventSource.equals(ok)) {
                // 日付の入力に誤りがある場合
                if (!isValidInput()) {
                    return;
                }
                closeWindow();
                buttonAction = BUTTON_PRINT;
            } else if (eventSource.equals(cancel)) {
                closeWindow();
                buttonAction = BUTTON_CANCEL;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        Object eventSource = e.getSource();
        // IkenshoIntegerCheckBox check = null;
        // int selectIndex = 0;
        try {
            // 意見書作成料総括書ラジオボタン
            if (eventSource.equals(createSummaryRadio)) {
                turnCheckEnabled(createSummaryPrint, createSummaryRadio
                        .getSelectedIndex());
                // 診察料総括書ラジオボタン
            } else if (eventSource.equals(inspectionSummaryRadio)) {
                turnCheckEnabled(inspectionSummaryPrint, inspectionSummaryRadio
                        .getSelectedIndex());
            }

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

    }

}
