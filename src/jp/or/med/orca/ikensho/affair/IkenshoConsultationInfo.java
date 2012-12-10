package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACOneDecimalDoubleTextField;
import jp.nichicom.ac.component.ACRadioButtonItem;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.ACValueArrayRadioButtonGroup;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACListModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoInitialNegativeIntegerTextField;
import jp.or.med.orca.ikensho.component.IkenshoUnderlineFormatableLabel;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoConsultationInfo extends IkenshoDialog {
    public static final int STATE_ZAITAKU = 1;
    public static final int STATE_SHISETSU = 2;
    public static final int STATE_FIRST = 4;
    public static final int STATE_EVER = 8;
    public static final int STATE_FIRST_SINRYOUJYO = 16;
    public static final int STATE_FIRST_HOSPITAL = 32;

    private VRPanel root = new VRPanel();
    private VRLayout rootLayout = new VRLayout();
    private VRPanel buttons = new VRPanel();
    private ACGroupBox toGroup = new ACGroupBox();
    private ACGroupBox bodyGroup = new ACGroupBox();
    private ACGroupBox costGroup = new ACGroupBox();
    private VRPanel taxPanel = new VRPanel();
    private ACGroupBox taxGroup = new ACGroupBox();
    private ACLabelContainer taxs = new ACLabelContainer();
    private VRLabel taxUnit = new VRLabel();
    private ACOneDecimalDoubleTextField tax = new ACOneDecimalDoubleTextField();
    private VRLabel toConsultationHead = new VRLabel();
    private VRLabel toIkenshoHead = new VRLabel();
    private ACLabelContainer costs = new ACLabelContainer();
    private ACTextField cost = new ACTextField();
    private ACTextField costType = new ACTextField();
    private ACTextField costTarget = new ACTextField();
    private VRLabel costUnit = new VRLabel();
    private ACButton cancel = new ACButton();
    private ACButton ok = new ACButton();
    private ACButton reset = new ACButton();
    // 2009/01/13 [Tozo Tanaka] Replace - begin
//    private VRPanel points = new VRPanel();
//    private VRPanel pointsLeft = new VRPanel();
//    private VRPanel pointsRight = new VRPanel();
    private ACPanel points = new ACPanel();
    private ACPanel pointsLeft = new ACPanel();
    private ACPanel pointsRight = new ACPanel();
    private VRLabel[] pointsSpacer = { new VRLabel(" "), new VRLabel(" "),
            new VRLabel(" "), new VRLabel(" "), new VRLabel(" "),
            new VRLabel(" "), new VRLabel(" "), new VRLabel(" "),
            new VRLabel(" "), new VRLabel(" ") };
    // 2009/01/13 [Tozo Tanaka] Replace - end

    private VRPanel calcRoot = new VRPanel();
    private GridBagLayout calcRootLayout = new GridBagLayout();
    // 2009/01/13 [Tozo Tanaka] Delete - begin
//    private VRLayout pointsLayout = new VRLayout();
//    private GridBagLayout pointsLeftLayout = new GridBagLayout();
//    private GridBagLayout pointsRightLayout = new GridBagLayout();
//    private VRLabel pointsRightSpacer1 = new VRLabel();
    // 2009/01/13 [Tozo Tanaka] Delete - end
    private ACIntegerCheckBox xrayFilm = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodBasic = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodBasicTest = new ACIntegerCheckBox();
    private ACIntegerCheckBox ketsuekigakuTestCost = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodChemistry = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodChemistryTest = new ACIntegerCheckBox();
    private ACIntegerCheckBox seikagakuTestCost = new ACIntegerCheckBox();
    private ACIntegerCheckBox nyouTest = new ACIntegerCheckBox();
    private ACIntegerCheckBox xray = new ACIntegerCheckBox();
    private ACIntegerCheckBox xrayBasic = new ACIntegerCheckBox();
    private ACIntegerCheckBox xrayPhotoCheck = new ACIntegerCheckBox();
    private ACIntegerCheckBox bloodSaishu = new ACIntegerCheckBox();
    private VRLabel bloodBasicSpacer = new VRLabel();
    private VRLabel xraySpacer = new VRLabel();
    private ACButton bloodChemistryTestPointChange = new ACButton();
    private VRPanel bloodChemistryTestPoints = new VRPanel();
    private ACOneDecimalDoubleTextField bloodChemistryTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel bloodChemistryTestPointUnit = new VRLabel();
    private VRPanel seikagakuTestCostPoints = new VRPanel();
    private ACOneDecimalDoubleTextField seikagakuTestCostPoint = new ACOneDecimalDoubleTextField();
    private VRLabel seikagakuTestCostPointUnit = new VRLabel();
    private VRPanel ketsuekigakuTestCostPoints = new VRPanel();
    private ACOneDecimalDoubleTextField ketsuekigakuTestCostPoint = new ACOneDecimalDoubleTextField();
    private VRLabel ketsuekigakuTestCostPointUnit = new VRLabel();
    private VRPanel bloodBasicTestPoints = new VRPanel();
    private ACOneDecimalDoubleTextField bloodBasicTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel bloodBasicTestPointUnit = new VRLabel();
    private VRPanel bloodSaishuPoints = new VRPanel();
    private ACOneDecimalDoubleTextField bloodSaishuPoint = new ACOneDecimalDoubleTextField();
    private VRLabel bloodSaishuPointUnit = new VRLabel();
    private VRPanel nyouTestPoints = new VRPanel();
    private ACOneDecimalDoubleTextField nyouTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel nyouTestPointUnit = new VRLabel();
    private VRPanel xrayBasicPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayBasicPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayBasicPointUnit = new VRLabel();
    private VRPanel xrayPhotoCheckPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayPhotoCheckPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayPhotoCheckPointUnit = new VRLabel();
    private VRPanel xrayFilmPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayFilmPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayFilmPointUnit = new VRLabel();

    // 2009/01/06 [Tozo Tanaka] Add - begin
    // デジタル撮影の場合：フィルムを算定する場合：画像記録用フィルム(大角)
    private ACIntegerCheckBox xrayDigitalFilm = new ACIntegerCheckBox();
    private VRPanel xrayDigitalFilmPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalFilmPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalFilmPointUnit = new VRLabel();

    // デジタル撮影の場合：フィルムを算定する場合：デジタル映像化処理加算
    private ACIntegerCheckBox xrayDigitalImaging = new ACIntegerCheckBox();
    private VRPanel xrayDigitalImagingPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalImagingPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalImagingPointUnit = new VRLabel();

    // デジタル撮影の場合：フィルムレスの場合：電子画像管理加算
    private ACIntegerCheckBox xrayDigitalImageManagement = new ACIntegerCheckBox();
    private VRPanel xrayDigitalImageManagementPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalImageManagementPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalImageManagementPointUnit = new VRLabel();
    // 2009/01/06 [Tozo Tanaka] Add - end
    
    // [ID:0000601][Masahiko Higuchi] 2010/02 add begin 診療報酬単価の変更対応
    private ACIntegerCheckBox xrayDigital = new ACIntegerCheckBox();
    private VRPanel xrayDigitalPoints = new VRPanel();
    private ACOneDecimalDoubleTextField xrayDigitalPoint = new ACOneDecimalDoubleTextField();
    private VRLabel xrayDigitalPointUnit = new VRLabel();
    // [ID:0000601][Masahiko Higuchi] 2010/02 add end
    
    private VRLabel category = new VRLabel();
    private VRLabel point = new VRLabel();
    private VRLabel summary = new VRLabel();
    private VRLabel calcXRay = new VRLabel();
    private VRLabel calcCheckTotalCostMessage = new VRLabel();
    private VRLabel calcCheckTotal = new VRLabel();
    private VRLabel calcNyoTest = new VRLabel();
    private VRLabel calcBloodChemistry = new VRLabel();
    private VRLabel calcBloodBasic = new VRLabel();
    private ACTextField spaceType = new ACTextField();
    private ACOneDecimalDoubleTextField calcXRayPoint = new ACOneDecimalDoubleTextField();
    private ACOneDecimalDoubleTextField calcBloodBasicPoint = new ACOneDecimalDoubleTextField();
    private ACOneDecimalDoubleTextField calcBloodChemistryPoint = new ACOneDecimalDoubleTextField();
    private ACOneDecimalDoubleTextField calcCheckTotalPoint = new ACOneDecimalDoubleTextField();
    private ACTextField firstTestSummary = new ACTextField();
    private ACTextField calcCheckTotalCost = new ACTextField();
    private ACOneDecimalDoubleTextField firstTestPoint = new ACOneDecimalDoubleTextField();
    private ACIntegerCheckBox firstTest = new ACIntegerCheckBox();
    private ACOneDecimalDoubleTextField calcNyoTestPoint = new ACOneDecimalDoubleTextField();
    private VRLabel calcCheckTotalCostUnit = new VRLabel();
    private ACTextField calcBloodBasicSummary = new ACTextField();
    private ACTextField calcBloodChemistrySummary = new ACTextField();
    private ACTextField calcXRaySummary = new ACTextField();
    private ACTextField calcNyoTestSummary = new ACTextField();
    private VRPanel calcCheckMessages = new VRPanel();
    private VRLabel calcCheckMessage2 = new VRLabel();
    private VRLabel calcCheckMessage1 = new VRLabel();
    private VRPanel toConsultationPanel = new VRPanel();
    private VRPanel toIkenshoPanel = new VRPanel();
    private VRLayout costGroupLayout = new VRLayout();
    private VRLayout taxGroupLayout = new VRLayout();
    private VRLayout toIkenshoPanelLayout = new VRLayout();
    private VRLayout toConsultationPanelLayout = new VRLayout();
    private IkenshoUnderlineFormatableLabel toIkenshoName = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toIkenshoNoHead = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toIkenshoNo = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toIkenshoNoFoot = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationName = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationNo = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationNoFoot = new IkenshoUnderlineFormatableLabel();
    private IkenshoUnderlineFormatableLabel toConsultationNoHead = new IkenshoUnderlineFormatableLabel();
    private ACGroupBox hiddenParameters = new ACGroupBox();
    private IkenshoInitialNegativeIntegerTextField outputPattern = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField hakkouKubun = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField fdOutputKubun = new IkenshoInitialNegativeIntegerTextField();
    private IkenshoInitialNegativeIntegerTextField shoshin = new IkenshoInitialNegativeIntegerTextField();
    private VRLayout bloodChemistryTestPointsLayout = new VRLayout();

    private String lastInputedFirstTestPoint = "0";
    protected boolean changed = false;
    protected VRMap source;
    protected IkenshoTreeFollowChecker bloodBasicChecker = new IkenshoTreeFollowChecker(
            bloodBasic,
            new JCheckBox[] { bloodBasicTest, ketsuekigakuTestCost });
    protected IkenshoTreeFollowChecker bloodChemistryChecker = new IkenshoTreeFollowChecker(
            bloodChemistry, new JCheckBox[] { bloodChemistryTest,
                    seikagakuTestCost });
    //2009/01/13 [Tozo Tanaka] Replace - begin
//    protected IkenshoTreeFollowChecker xrayChecker = new IkenshoTreeFollowChecker(
//            xray, new JCheckBox[] { xrayBasic, xrayPhotoCheck, xrayFilm});
    // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin 診療報酬単価の変更対応
    protected IkenshoTreeFollowChecker xrayChecker = new IkenshoTreeFollowChecker(
            xray, new JCheckBox[] { xrayBasic, xrayDigital, xrayPhotoCheck,
                    xrayFilm, xrayDigitalImageManagement, xrayDigitalFilm,
                    xrayDigitalImaging }, new JCheckBox[] { xrayBasic,
                    xrayPhotoCheck, xrayFilm, });
    // [ID:0000601][Masahiko Higuchi] 2010/02 edit end
    //2009/01/13 [Tozo Tanaka] Replace - begin
    protected VRMap defaultInsure;
    protected VRMap pointMap = new VRHashMap();
    protected HashMap checkItemListenerMap = new HashMap();
    protected String firstTestName;
    protected String firstTestKey;
    protected int stateFlag;
    protected int formatKubun;

    protected IkenshoInitialNegativeIntegerTextField ikenshoCharge = new IkenshoInitialNegativeIntegerTextField();

    /**
     * /** モーダルモードで表示し、変更があったかを返します。
     * 
     * @return 変更があったか
     */
    public boolean showModal() {
        root.setSource(source);
        // TODO SET
        // calcRootにsourceを設定 ※範囲が大きいので要注意
        calcRoot.setSource(source);
        try {

            // フィールド翻訳
            source.setData("SEIKYUSHO_OUTPUT_PATTERN", source
                    .getData("OUTPUT_PATTERN"));

            boolean loadTax = true;
            if (VRBindPathParser.has("TAX", source)) {
                Object obj = VRBindPathParser.get("TAX", source);
                if (obj instanceof Double) {
                    if (((Double) obj).doubleValue() >= 0) {
                        loadTax = false;
                    }
                }
                if (loadTax) {
                    // 税金
                    IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
                    IkenshoCommon.setTax(dbm, source);
                    dbm.finalize();
                }

            }

            followBind();
            // 摘要欄はチェックで上書きされるのでリバインド
            calcXRaySummary.bindSource();
            calcBloodBasicSummary.bindSource();
            calcBloodChemistrySummary.bindSource();
            calcNyoTestSummary.bindSource();

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }

        setVisible(true);
        // show();
        return changed;
    }

    /**
     * 最新の値を読み込みます。
     * 
     * @throws Exception 処理例外
     */
    protected void loadDefault() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT");
        sb.append(" *");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" WHERE");
        sb.append(" (INSURER.INSURER_NO=");
        sb.append(IkenshoCommon.getDBSafeString("INSURER_NO", source));
        sb.append(")");
        sb.append(" AND(INSURER.INSURER_TYPE=");
        sb.append(IkenshoCommon.getDBSafeNumber("INSURER_TYPE", source));
        sb.append(")");

        VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
        if (array.size() == 0) {
            IkenshoCommon.showExceptionMessage(new IllegalArgumentException(
                    "存在しない保険者が指定されました。"));
            return;
        }
        defaultInsure = (VRMap) array.getData();
        // フィールド翻訳
        defaultInsure.setData("OUTPUT_PATTERN", defaultInsure
                .getData("SEIKYUSHO_OUTPUT_PATTERN"));

        source.putAll((VRMap) bodyGroup.createSource());

        dbm.finalize();
    }

    /**
     * 状態フラグを解析します。
     * 
     * @throws ParseException 処理例外
     */
    protected void paraseState() throws ParseException {
        StringBuffer costSB = new StringBuffer();
        int flag = 0;
        if ((stateFlag & IkenshoConsultationInfo.STATE_ZAITAKU) != 0) {
            source.put("AFFAIR_CARE_TYPE", "在宅");
            costSB.append("ZAITAKU_");
        } else if ((stateFlag & IkenshoConsultationInfo.STATE_SHISETSU) != 0) {
            source.put("AFFAIR_CARE_TYPE", "施設");
            costSB.append("SISETU_");
            flag += 2;
        }
        if ((stateFlag & IkenshoConsultationInfo.STATE_FIRST) != 0) {
            source.put("AFFAIR_FIRST_CREATE", "新規");
            costSB.append("SINKI_CHARGE");
        } else if ((stateFlag & IkenshoConsultationInfo.STATE_EVER) != 0) {
            source.put("AFFAIR_FIRST_CREATE", "継続");
            costSB.append("KEIZOKU_CHARGE");
            flag += 1;
        }
        source.put("IKN_CHARGE", new Integer(flag));
        ikenshoCharge.setText(String.valueOf(flag));

        cost.setBindPath(costSB.toString());
        cost.setFormat(NumberFormat.getIntegerInstance());

        Object kubunObj = VRBindPathParser.get("MI_KBN", source);
        int kubun = 0;
        if (kubunObj instanceof Integer) {
            kubun = ((Integer) kubunObj).intValue();
        }

        int shoshin = 0;
        switch (kubun) {
        case 1:
            firstTestName = "診療所";
            firstTestKey = "SHOSIN_SINRYOUJO";
            shoshin = 1;
            break;
        case 2:
            firstTestName = "病院";
            firstTestKey = "SHOSIN_HOSPITAL";
            shoshin = 2;
            break;
        default:
            firstTestName = "その他の施設";
            firstTestKey = "SHOSIN_OTHER";
            if (!pointMap.containsKey("SHOSIN_OTHER")) {
                pointMap.setData("SHOSIN_OTHER", new Double(0));
            } else {
                lastInputedFirstTestPoint = String.valueOf(pointMap
                        .getData("SHOSIN_OTHER"));
            }
            shoshin = 3;
        }
        source.put("SHOSIN", new Integer(shoshin));
        this.shoshin.setText(String.valueOf(shoshin));

    }

    /**
     * コンストラクタです。
     * 
     * @param src データソース
     * @param stateFlg 状態フラグ
     * @param formatKubun 文書区分
     * @see stateFlagは定数の組み合わせで指定します。
     */
    public IkenshoConsultationInfo(VRMap src, int stateFlg, int formatKubun) {
        super(ACFrame.getInstance(), "診察・検査内容入力", true);
        this.source = src;
        this.stateFlag = stateFlg;
        this.formatKubun = formatKubun;

        pointMap.clear();
        pointMap.putAll(source);

        try {
            jbInit();
            pack();
            init();
            paraseState();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        hiddenParameters.setVisible(false);

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (IkenshoCommon.isNullText(tax.getText())) {
                        ACMessageBox.show("消費税が不正です。", ACMessageBox.BUTTON_OK,
                                ACMessageBox.ICON_EXCLAMATION);
                        tax.requestFocus();
                        return;
                    }
                    if ((!IkenshoCommon.isNullText(calcCheckTotalPoint
                            .getText()))
                            && ((stateFlag & IkenshoConsultationInfo.STATE_FIRST) != 0)) {
                        // 初回
                        if (!firstTest.isSelected()) {
                            if (ACMessageBox.show("初診が選択されていません。\n選択しますか？",
                                    ACMessageBox.BUTTON_YES
                                            | ACMessageBox.BUTTON_NO,
                                    ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                                firstTest.setSelected(true);
                                return;
                            }
                        }
                    }

                    // 2006/02/11[Tozo Tanaka] : replace begin
                    // canEdit?
                    // root.applySource();
                    // 血液化学検査が空欄でも、nullに上書きさせない
                    Object old = source.getData(bloodChemistryTestPoint
                            .getBindPath());
                    root.applySource();
                    Object now = source.getData(bloodChemistryTestPoint
                            .getBindPath());
                    if ((now == null) || ("".equals(now))) {
                        source.setData(bloodChemistryTestPoint.getBindPath(),
                                old);
                    }
                    // 2006/02/11[Tozo Tanaka] : replace end
                    if ("SHOSIN_OTHER".equals(firstTestKey)
                            && firstTest.isSelected()) {
                        firstTestPoint.setBindPath(firstTestKey);
                        firstTestPoint.setSource(source);
                        firstTestPoint.applySource();
                    }

                    // //フィールド翻訳
                    // source.setData("OUTPUT_PATTERN",
                    // source.getData("SEIKYUSHO_OUTPUT_PATTERN"));

                    changed = true;
                    dispose();
                } catch (ParseException ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                }
            }
        });

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (ACMessageBox.show("最新の保険者の情報を反映させますか？\n請求金額が変わる可能性があります。",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {
                    try {
                        // 適用前のデータを退避
                        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin 診療報酬単価の変更対応
                        // ソースの取得前に削除となったデジタル映像化処理加算は除外
                        xrayDigitalImaging.setSelected(false);
                        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
                        
                        VRBindSource old = calcRoot.getSource();
                        VRBindSource tmp = (VRBindSource) calcRoot
                                .createSource();
                        calcRoot.setSource(tmp);
                        calcRoot.applySource();
                        calcRoot.setSource(old);

                        useDefaultPoint();
                        followBind();
                        
                        // 2006/08/03 TODO
                        // useDefaultPoint()内で取得したSHOSIN_TAISHOUチェックの値を保持する。
                        // 同時に現在の点数で再計算した値を表示する。
                        // Addition - begin [Masahiko Higuchi]
                        tmp.setData("SHOSIN_TAISHOU", source
                                .getData("SHOSIN_TAISHOU"));
                        source.putAll((Map) tmp);
                        // 初診チェックがついている場合のみ再計算する。
                        // チェック無しの状態での表示変更対策
                        if (new Integer(1).equals(source
                                .getData("SHOSIN_TAISHOU"))) {
                            firstTestPoint
                                    .setText(IkenshoConstants.FORMAT_DOUBLE1
                                            .format(new Double(String
                                                    .valueOf(VRBindPathParser
                                                            .get(firstTestKey,
                                                                    source)))));
                            calcCost();
                        }
                        // Addition - end
                        // 摘要欄を差し戻す
                        old = calcRoot.getSource();
                        calcRoot.setSource(tmp);
                        calcRoot.bindSource();
                        calcRoot.setSource(old);
                    } catch (Exception ex) {
                        IkenshoCommon.showExceptionMessage(ex);
                    }
                }
            }
        });

        bloodChemistryTestPointChange.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (new IkenshoBloodChemistry(pointMap).showModal()) {
                    // 変更
                    getCheckItemListener(bloodChemistryTest).follow(true);
                }
            }
        });

        firstTest.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    // 初診点数

                    try {
                        if ("SHOSIN_OTHER".equals(firstTestKey)) {
                            firstTestPoint.setText(lastInputedFirstTestPoint);
                            firstTestPoint.setEditable(true);
                        } else {
                            firstTestPoint
                                    .setText(IkenshoConstants.FORMAT_DOUBLE1
                                            .format(new Double(String
                                                    .valueOf(VRBindPathParser
                                                            .get(firstTestKey,
                                                                    pointMap)))));
                            firstTestPoint.setEditable(false);
                        }
                        spaceType.setText(firstTestName);
                    } catch (ParseException ex) {
                        IkenshoCommon.showExceptionMessage(ex);
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    if (!"".equals(firstTestPoint.getText())) {
                        lastInputedFirstTestPoint = firstTestPoint.getText();
                    }
                    firstTestPoint.setEditable(false);
                    spaceType.setText("");
                    firstTestPoint.setText("");
                }
                calcCost();
            }
        });

        firstTestPoint.getDocument().addDocumentListener(
                new DocumentListener() {
                    public void changedUpdate(DocumentEvent e) {
                        calcCost();
                    }

                    public void insertUpdate(DocumentEvent e) {
                        calcCost();
                    }

                    public void removeUpdate(DocumentEvent e) {
                        calcCost();
                    }
                });

        addCheckItemListenerMap(pointMap, new String[] { "EXP_KS",
                "EXP_KIK_MKI", "EXP_KIK_KEKK" }, new JCheckBox[] { bloodSaishu,
                bloodBasicTest, ketsuekigakuTestCost }, new JTextField[] {
                bloodSaishuPoint, bloodBasicTestPoint,
                ketsuekigakuTestCostPoint }, new String[] {
                bloodSaishu.getText(), bloodBasicTest.getText(),
                ketsuekigakuTestCost.getText() }, calcBloodBasicPoint,
                calcBloodBasicSummary);

        addCheckItemListenerMap(pointMap, new String[] { "EXP_KKK_KKK",
                "EXP_KKK_SKK" }, new JCheckBox[] { bloodChemistryTest,
                seikagakuTestCost }, new JTextField[] {
                bloodChemistryTestPoint, seikagakuTestCostPoint },
                new String[] { bloodChemistryTest.getText() + "(10項目以上)",
                        seikagakuTestCost.getText() }, calcBloodChemistryPoint,
                calcBloodChemistrySummary);

        addCheckItemListenerMap(pointMap, new String[] { "EXP_NITK" },
                new JCheckBox[] { nyouTest },
                new JTextField[] { nyouTestPoint }, new String[] { "" },
                calcNyoTestPoint, calcNyoTestSummary);
        // 2009/01/09[Tozo Tanaka] : replace begin
        // addCheckItemListenerMap(pointMap, new String[] { "EXP_XRAY_TS",
        // "EXP_XRAY_SS", "EXP_XRAY_FILM" }, new JCheckBox[] { xrayBasic,
        // xrayPhotoCheck, xrayFilm }, new JTextField[] { xrayBasicPoint,
        // xrayPhotoCheckPoint, xrayFilmPoint }, new String[] {
        // xrayBasic.getText(), xrayPhotoCheck.getText(),
        // xrayFilm.getText() }, calcXRayPoint, calcXRaySummary);
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin 診療報酬単価の変更対応
        addCheckItemListenerMap(pointMap, new String[] { "EXP_XRAY_TS",
                "EXP_XRAY_TS_DIGITAL", "EXP_XRAY_SS", "EXP_XRAY_FILM",
                "EXP_XRAY_DIGITAL_MANAGEMENT", "EXP_XRAY_DIGITAL_FILM",
                "EXP_XRAY_DIGITAL_IMAGING" },
                new JCheckBox[] { xrayBasic, xrayDigital, xrayPhotoCheck,
                        xrayFilm, xrayDigitalImageManagement, xrayDigitalFilm,
                        xrayDigitalImaging }, new JTextField[] {
                        xrayBasicPoint, xrayDigitalPoint, xrayPhotoCheckPoint,
                        xrayFilmPoint, xrayDigitalImageManagementPoint,
                        xrayDigitalFilmPoint, xrayDigitalImagingPoint },
                new String[] { xrayBasic.getText(), xrayDigital.getText(),
                        xrayPhotoCheck.getText(), xrayFilm.getText(),
                        xrayDigitalImageManagement.getText(),
                        xrayDigitalFilm.getText(),
                        xrayDigitalImaging.getText(), }, calcXRayPoint,
                calcXRaySummary);
//            xrayFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayDigitalImageManagement, xrayDigitalFilm,
//                            xrayDigitalImaging }));
//            xrayDigitalImageManagement.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayFilm, xrayDigitalFilm,
//                            xrayDigitalImaging }));
//            xrayDigitalFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayFilm, xrayDigitalImageManagement },
//                            new JCheckBox[] { xrayDigitalImaging }));
//            xrayDigitalImaging.addItemListener(new IkenshoExclusionDisabledItemListener(
//                    new JComponent[] { xrayFilm, xrayDigitalImageManagement },
//                            new JCheckBox[] { xrayDigitalFilm }));
        xrayFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayDigitalImageManagement, xrayDigitalFilm }));
        xrayDigitalImageManagement.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayFilm, xrayDigitalFilm }));
        xrayDigitalFilm.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayFilm, xrayDigitalImageManagement },
                        new JCheckBox[] { }));
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit end
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin 診療報酬単価の変更対応
        xrayBasic.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayDigital},
                        new JCheckBox[] {}));        
        xrayDigital.addItemListener(new IkenshoExclusionDisabledItemListener(
                new JComponent[] { xrayBasic},
                        new JCheckBox[] {}));
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        
//        xrayDigitalFilm.addItemListener(new IkenshoFollowCheckItemListener(
//                new JCheckBox[] { xrayDigitalImaging }));
//        xrayDigitalImaging.addItemListener(new IkenshoFollowCheckItemListener(
//                new JCheckBox[] { xrayDigitalFilm }));
        // 2009/01/09[Tozo Tanaka] : replace end

        bloodChemistryTest.addItemListener(new ACFollowDisabledItemListener(
                new JComponent[] { bloodChemistryTestPointChange }));

    }

    // 2009/01/13[Tozo Tanaka] : add begin
    protected class IkenshoExclusionDisabledItemListener implements ItemListener{
        private JComponent[] disableTargets;
        private JCheckBox[] followTargets;
        public IkenshoExclusionDisabledItemListener(JComponent[] disableTargets) {
            this.disableTargets = disableTargets;
        }
        public IkenshoExclusionDisabledItemListener(JComponent[] disableTargets, JCheckBox[] followTargets) {
            this.disableTargets = disableTargets;
            this.followTargets = followTargets;
        }
     
        public void itemStateChanged(ItemEvent e) {
            boolean enabled;
            if (e.getStateChange() == ItemEvent.SELECTED) {
                enabled = false;
            } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                enabled = true;
                if(followTargets!=null){
                    for(int i=0; i<followTargets.length; i++){
                        if(followTargets[i].isSelected()){
                            //連動するチェックのいずれか一つにまだチェックがついていたら、無効状態を解除しない
                            return;
                        }
                    }
                }
            } else {
                return;
            }
            if (disableTargets != null) {
                int end = disableTargets.length;
                for (int i = 0; i < end; i++) {
                    disableTargets[i].setEnabled(enabled);
                }
            }
        }
        
    }
    protected class IkenshoFollowCheckItemListener implements ItemListener{
        private JCheckBox[] followTargets;
        public IkenshoFollowCheckItemListener(JCheckBox[] followTargets) {
            this.followTargets = followTargets;
        }
     
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if(followTargets!=null){
                    for(int i=0; i<followTargets.length; i++){
                        if(!followTargets[i].isSelected()){
                            //連動するチェックに未選択のものがあれば、選択状態にする
                            followTargets[i].setSelected(true);
                        }
                    }
                }
            }
        }
        
    }
    // 2009/01/13[Tozo Tanaka] : add end
    
    public void calcCost() {
        JTextField[] totalPoints = new JTextField[] { firstTestPoint,
                calcXRayPoint, calcBloodBasicPoint, calcBloodChemistryPoint,
                calcNyoTestPoint };
        JTextField fullTotalPoint = calcCheckTotalPoint;
        JTextField fullTotalCost = calcCheckTotalCost;

        double val = 0;
        int end = totalPoints.length;
        for (int i = 0; i < end; i++) {
            String text = totalPoints[i].getText();
            if (!("".equals(text))) {
                val += Double.parseDouble(text);
            }
        }
        if (val == 0) {
            fullTotalPoint.setText("");
            fullTotalCost.setText("");
        } else {
            fullTotalPoint.setText(IkenshoConstants.FORMAT_DOUBLE1
                    .format(new Double(val)));

            double dblValue = new Double(val * 10).doubleValue();
            int intValue = (int) dblValue;
            double dblTmp = Double.parseDouble(String.valueOf(intValue));

            if (dblValue == dblTmp) {
                fullTotalCost.setText(new DecimalFormat("#").format(dblValue));
            } else {
                fullTotalCost
                        .setText(new DecimalFormat("#.#").format(dblValue));
            }
        }
    }

    /**
     * 指定チェックに関連付けられたチェックリスナを返します。
     * 
     * @param check 対象のチェック
     * @return チェックリスナ
     */
    protected IkenshoFollowCalcItemListener getCheckItemListener(JCheckBox check) {
        return (IkenshoFollowCalcItemListener) checkItemListenerMap.get(check);
    }

    /**
     * 指定した関連コンポーネント群にチェックリスナを追加します。
     * 
     * @param source 点数マップ
     * @param keys 対応する点数キー群
     * @param checks 付随して関連するチェック群
     * @param points 付随して関連する点数欄群
     * @param summarys 付随して出力する摘要群
     * @param totalPoint 合計点数欄
     * @param totalSummary 合計の摘要欄
     */
    protected void addCheckItemListenerMap(VRMap source, String[] keys,
            JCheckBox[] checks, JTextField[] points, String[] summarys,
            JTextField totalPoint, JTextField totalSummary) {
        int end = keys.length;
        for (int i = 0; i < end; i++) {
            IkenshoFollowCalcItemListener l = new IkenshoFollowCalcItemListener(
                    points[i], source, keys[i], checks, points, summarys,
                    totalPoint, totalSummary, this);
            checkItemListenerMap.put(checks[i], l);
            checks[i].addItemListener(l);
        }
    }

    /**
     * 前画面から引き継いだソースを、デフォルト値の適用も含めて設定します。
     * 
     * @throws Exception 処理例外
     */
    protected void useDefaultPoint() throws Exception {
        // デフォルト設定
        // pointMap.clear();

        VRMap newSource = (VRMap) points.createSource();
        newSource.setData("SHOSIN_TAISHOU", ACCastUtilities.toInteger(firstTest
                .getValue()));
        loadDefault();

        // VRMap newSource = (VRMap) points.createSource();
        points.setSource(newSource);
        points.applySource();
        tax.applySource();
        firstTest.applySource();
        // newSource.setData("SHOSIN_TAISHOU",
        // source.getData("SHOSIN_TAISHOU"));
        newSource.setData("TAX", source.getData("TAX"));
        points.setSource(source);

        pointMap.putAll(defaultInsure);
        if (!pointMap.containsKey("SHOSIN_OTHER")) {
            pointMap.setData("SHOSIN_OTHER", new Double(0));
        }
        source.putAll(defaultInsure);

        // 初期値を読込んだ後に改めて上書きする。
        // TODO ★選択中の医療機関の電子化加算の有無によって、初診点数の加算を行う★
        // 電子化加算フラグが渡って来た場合
        if (VRBindPathParser.has("DR_ADD_IT", source)) {
            // 電子化加算点数をdoubleの値として保持する
            // 2006/02/07[Tozo Tanaka] : replace begin
            // if (ACCastUtilities.toInt(VRBindPathParser.get("DR_ADD_IT",
            // source)) == 1){
            if (IkenshoCommon.canAddIT(formatKubun, ACCastUtilities
                    .toInt(VRBindPathParser.get("DR_ADD_IT", source)) == 1,
                    source)) {
                // 2006/02/07[Tozo Tanaka] : replace end
                double addIT = ACCastUtilities.toDouble(source
                        .getData("SHOSIN_ADD_IT"), 0);
                double shoshinHos = ACCastUtilities.toDouble(source
                        .getData("SHOSIN_HOSPITAL"), 0);
                double shoshinSin = ACCastUtilities.toDouble(source
                        .getData("SHOSIN_SINRYOUJO"), 0);

              // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin 診療報酬単価の変更対応
//                double shoshinHosAdd = shoshinHos += addIT;
//                double shoshinSinAdd = shoshinSin += addIT;
              double shoshinHosAdd = shoshinHos;
              double shoshinSinAdd = shoshinSin;
              // [ID:0000601][Masahiko Higuchi] 2010/02 edit end

                source.setData("SHOSIN_HOSPITAL", new Double(shoshinHosAdd));
                source.setData("SHOSIN_SINRYOUJO", new Double(shoshinSinAdd));
                pointMap.setData("SHOSIN_HOSPITAL", new Double(shoshinHosAdd));
                pointMap.setData("SHOSIN_SINRYOUJO", new Double(shoshinSinAdd));
            }
        }

        source.putAll(newSource);

        if ("SHOSIN_OTHER".equals(firstTestKey)) {
            lastInputedFirstTestPoint = "0";
            if (firstTestPoint.isEditable()) {
                firstTestPoint.setText("0");
            }
        }

        // 2006/09/09 [Tozo Tanaka] : remove begin
        // //2006/09/07 [Tozo Tanaka] : add begin
        // source.put("NEW_DR_ADD_IT", source.get("DR_ADD_IT"));
        // //2006/09/07 [Tozo Tanaka] : add end
        // 2006/09/09 [Tozo Tanaka] : remove end

    }

    /**
     * 直接バインドが不可能なコントロールに値を設定します。
     * 
     * @throws ParseException 解析例外
     */
    protected void followBind() throws ParseException {
        root.bindSource();

        Iterator it = checkItemListenerMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            ((IkenshoFollowCalcItemListener) entry.getValue())
                    .follow(((JCheckBox) entry.getKey()).isSelected());
        }

        if ("".equals(toIkenshoName.getText())) {
            toIkenshoNoHead.setVisible(false);
            toIkenshoNoFoot.setVisible(false);
        } else {
            toIkenshoNoHead.setVisible(true);
            toIkenshoNoFoot.setVisible(true);
        }
        if ("".equals(toConsultationName.getText())) {
            toConsultationNoHead.setVisible(false);
            toConsultationNoFoot.setVisible(false);
        } else {
            toConsultationNoHead.setVisible(true);
            toConsultationNoFoot.setVisible(true);
        }

    }

    /**
     * 位置を初期化します。
     */
    private void init() {
        // ウィンドウのサイズ
        // 2009/01/06 [Tozo Tanaka] Replace - begin
        setSize(new Dimension(770, getHeight()));
        // setSize(new Dimension(770, 590));
        // 2009/01/06 [Tozo Tanaka] Replace - end
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

    /**
     * コンポーネントを初期化します。
     * 
     * @throws Exception 初期化例外
     */
    private void jbInit() throws Exception {
        bodyGroup.setLayout(new VRLayout());
        buttons.setLayout(new VRLayout());
        taxPanel.setLayout(new VRLayout());
        toGroup.setLayout(new VRLayout());
        taxGroup.setLayout(taxGroupLayout);
        taxGroupLayout.setAlignment(VRLayout.RIGHT);
        costGroup.setLayout(costGroupLayout);
        costGroupLayout.setAlignment(VRLayout.LEFT);
        bloodChemistryTestPointsLayout.setHgap(5);
        bloodChemistryTestPointsLayout.setAutoWrap(false);
        bloodChemistryTestPoints.setLayout(bloodChemistryTestPointsLayout);

        this.setTitle("診察・検査内容入力");
        root.setLayout(rootLayout);
        toGroup.setText("請求先");
        bodyGroup.setText("診察・検査内容（意見書作成のために行なった基本的な診察・検査費用を入力してください）");
        costGroup.setText("意見書作成料");
        taxGroup.setText("消費税率");
        taxUnit.setText("％");
        // tax.setMargin(new Insets(1, 5, 2, 4));
        tax.setColumns(3);
        tax.setHorizontalAlignment(SwingConstants.RIGHT);
        tax.setBindPath("TAX");
        tax.setMaxLength(3);
        toConsultationHead.setText("診察・検査料請求先（番号）：");
        toIkenshoHead.setText("意見書作成料請求先（番号）：");
        toConsultationName.setBindPath("SKS_INSURER_NM");
        toConsultationName.setText("テスト自治体");
        costType.setEditable(false);
        costType.setColumns(2);
        costType.setBindPath("AFFAIR_FIRST_CREATE");
        costTarget.setEditable(false);
        costTarget.setColumns(2);
        costTarget.setBindPath("AFFAIR_CARE_TYPE");
        costUnit.setText("円");
        cost.setEditable(false);
        cost.setColumns(8);
        cost.setHorizontalAlignment(SwingConstants.RIGHT);
        cancel.setMnemonic('C');
        cancel.setText("キャンセル(C)");
        ok.setMnemonic('S');
        ok.setText("確定(S)");
        reset.setMnemonic('T');
        reset.setText("請求先・点数再設定(T)");
        calcRoot.setLayout(calcRootLayout);
        // 2009/01/13 [Tozo Tanaka] Delete - begin
//        points.setLayout(pointsLayout);
//        pointsRight.setLayout(pointsRightLayout);
//        pointsLeft.setLayout(pointsLeftLayout);
//        pointsRightSpacer1.setPreferredSize(new Dimension(100, 70));
        // 2009/01/13 [Tozo Tanaka] Delete - end
        rootLayout.setVgap(0);
        points.add(pointsRight, VRLayout.EAST);
        points.add(pointsLeft, VRLayout.WEST);
        xrayFilm.setText("フィルム(大角)");
        xrayFilm.setBindPath("XRAY_FILM");
        bloodBasic.setText("血液一般検査");
        bloodBasic.setBindPath("");
        bloodBasicTest.setText("末梢血液一般検査");
        bloodBasicTest.setBindPath("BLD_IPPAN_MASHOU_KETUEKI");
        ketsuekigakuTestCost.setText("血液学的検査判断料");
        ketsuekigakuTestCost.setBindPath("BLD_IPPAN_EKIKAGAKUTEKIKENSA");
        bloodChemistry.setText("血液化学検査");
        bloodChemistryTest.setText("血液化学検査");
        bloodChemistryTest.setBindPath("BLD_KAGAKU_KETUEKIKAGAKUKENSA");
        seikagakuTestCost.setText("生化学的検査(I)判断料");
        seikagakuTestCost.setBindPath("BLD_KAGAKU_SEIKAGAKUTEKIKENSA");
        nyouTest.setText("尿中一般物質定性半定量検査");
        nyouTest.setBindPath("NYO_KENSA");
        xray.setText("胸部単純X線撮影");
        xray.setBindPath("");
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin 診療報酬単価の変更対応
        //xrayBasic.setText("単純撮影");
        xrayBasic.setText("単純撮影(アナログ)");
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin 診療報酬単価の変更対応
        xrayBasic.setBindPath("XRAY_TANJUN_SATUEI");
        xrayPhotoCheck.setText("写真診断(胸部)");
        xrayPhotoCheck.setBindPath("XRAY_SHASIN_SINDAN");
        bloodSaishu.setText("血液採取(静脈)");
        bloodSaishu.setBindPath("BLD_SAISHU");
        bloodBasicSpacer.setText("　　");
        xraySpacer.setText("　");
        bloodChemistryTestPointChange.setEnabled(false);
        bloodChemistryTestPointChange.setMnemonic('U');
        bloodChemistryTestPointChange.setText("変更(U)");
        bloodChemistryTestPoint.setEditable(false);
        // bloodChemistryTestPoint.setMargin(new Insets(1, 5, 2, 4));
        bloodChemistryTestPoint.setColumns(7);
        bloodChemistryTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        // 2006/02/06[Tozo Tanaka] : add begin
        bloodChemistryTestPoint.setBindPath("EXP_KKK_KKK");
        // 2006/02/06[Tozo Tanaka] : add end
        bloodChemistryTestPointUnit.setText("点");
        seikagakuTestCostPoint.setEditable(false);
        // seikagakuTestCostPoint.setMargin(new Insets(1, 5, 2, 4));
        seikagakuTestCostPoint.setColumns(7);
        seikagakuTestCostPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        seikagakuTestCostPointUnit.setText("点");
        ketsuekigakuTestCostPoint.setEditable(false);
        // ketsuekigakuTestCostPoint.setMargin(new Insets(1, 5, 2, 4));
        ketsuekigakuTestCostPoint.setColumns(7);
        ketsuekigakuTestCostPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        ketsuekigakuTestCostPointUnit.setText("点");
        bloodBasicTestPoint.setEditable(false);
        // bloodBasicTestPoint.setMargin(new Insets(1, 5, 2, 4));
        bloodBasicTestPoint.setColumns(7);
        bloodBasicTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        bloodBasicTestPointUnit.setText("点");
        bloodSaishuPoint.setEditable(false);
        // bloodSaishuPoint.setMargin(new Insets(1, 5, 2, 4));
        bloodSaishuPoint.setColumns(7);
        bloodSaishuPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        bloodSaishuPointUnit.setText("点");
        nyouTestPoint.setEditable(false);
        // nyouTestPoint.setMargin(new Insets(1, 5, 2, 4));
        nyouTestPoint.setColumns(7);
        nyouTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        nyouTestPointUnit.setText("点");
        xrayBasicPoint.setEditable(false);
        // xrayBasicPoint.setMargin(new Insets(1, 5, 2, 4));
        xrayBasicPoint.setColumns(7);
        xrayBasicPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayBasicPointUnit.setText("点");
        xrayPhotoCheckPoint.setEditable(false);
        // xrayPhotoCheckPoint.setMargin(new Insets(1, 5, 2, 4));
        xrayPhotoCheckPoint.setColumns(7);
        xrayPhotoCheckPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayPhotoCheckPointUnit.setText("点");
        xrayFilmPoint.setEditable(false);
        // xrayFilmPoint.setMargin(new Insets(1, 5, 2, 4));
        xrayFilmPoint.setColumns(7);
        xrayFilmPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayFilmPointUnit.setText("点");

        // 2009/01/06 [Tozo Tanaka] Add - begin
        // デジタル撮影の場合：フィルムレスの場合：電子画像管理加算
        xrayDigitalImageManagement.setText("電子画像管理加算");
        xrayDigitalImageManagement.setBindPath("XRAY_DIGITAL_MANAGEMENT");
        xrayDigitalImageManagementPoint.setEditable(false);
        xrayDigitalImageManagementPoint.setColumns(7);
        xrayDigitalImageManagementPoint
                .setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalImageManagementPointUnit.setText("点");

        // デジタル撮影の場合：フィルムを算定する場合：画像記録用フィルム(大角)
        xrayDigitalFilm.setText("画像記録用フィルム(大角)");
        xrayDigitalFilm.setBindPath("XRAY_DIGITAL_FILM");
        xrayDigitalFilmPoint.setEditable(false);
        xrayDigitalFilmPoint.setColumns(7);
        xrayDigitalFilmPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalFilmPointUnit.setText("点");

        // デジタル撮影の場合：フィルムを算定する場合：デジタル映像化処理加算
        xrayDigitalImaging.setText("デジタル映像化処理加算");
        xrayDigitalImaging.setBindPath("XRAY_DIGITAL_IMAGING");
        xrayDigitalImagingPoint.setEditable(false);
        xrayDigitalImagingPoint.setColumns(7);
        xrayDigitalImagingPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalImagingPointUnit.setText("点");

        xrayDigitalFilmPoints.add(xrayDigitalFilmPoint, null);
        xrayDigitalFilmPoints.add(xrayDigitalFilmPointUnit, null);
        xrayDigitalImagingPoints.add(xrayDigitalImagingPoint, null);
        xrayDigitalImagingPoints.add(xrayDigitalImagingPointUnit, null);
        xrayDigitalImageManagementPoints.add(xrayDigitalImageManagementPoint,
                null);
        xrayDigitalImageManagementPoints.add(
                xrayDigitalImageManagementPointUnit, null);
        // 2009/01/06 [Tozo Tanaka] Add - end

        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin 診療報酬単価の変更対応
        // 単純撮影（デジタル）
        xrayDigital.setText("単純撮影(デジタル)");
        xrayDigital.setBindPath("XRAY_TANJUN_SATUEI_DIGITAL");
        xrayDigitalPoint.setEditable(false);
        xrayDigitalPoint.setColumns(7);
        xrayDigitalPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        xrayDigitalPointUnit.setText("点");
        // コンポーネント配置
        xrayDigitalPoints.add(xrayDigitalPoint, null);
        xrayDigitalPoints.add(xrayDigitalPointUnit, null);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        
        category.setText("内訳");
        category.setOpaque(true);
        category.setHorizontalAlignment(SwingConstants.CENTER);
        category.setBackground(Color.lightGray);
        category.setBorder(BorderFactory.createLineBorder(Color.black));
        point.setBorder(BorderFactory.createLineBorder(Color.black));
        point.setBackground(Color.lightGray);
        point.setOpaque(true);
        point.setHorizontalAlignment(SwingConstants.CENTER);
        point.setText("点数");
        summary.setBorder(BorderFactory.createLineBorder(Color.black));
        summary.setBackground(Color.lightGray);
        summary.setOpaque(true);
        summary.setHorizontalAlignment(SwingConstants.CENTER);
        summary.setText("摘要");
        calcXRay.setBorder(BorderFactory.createLineBorder(Color.black));
        calcXRay.setOpaque(true);
        calcXRay.setBackground(Color.lightGray);
        calcXRay.setText("胸部単純X線撮影");
        calcCheckTotalCostMessage.setBorder(BorderFactory
                .createLineBorder(Color.black));
        calcCheckTotalCostMessage.setBackground(Color.lightGray);
        calcCheckTotalCostMessage.setOpaque(true);
        calcCheckTotalCostMessage.setPreferredSize(new Dimension(200, 17));
        calcCheckTotalCostMessage.setText("点数合計×10円");
        calcCheckTotal.setText("合計");
        calcCheckTotal.setOpaque(true);
        calcCheckTotal.setBackground(Color.lightGray);
        calcCheckTotal.setBorder(BorderFactory.createLineBorder(Color.black));
        calcNyoTest.setText("尿中一般物質定性半定量検査");
        calcNyoTest.setOpaque(true);
        calcNyoTest.setBackground(Color.lightGray);
        calcNyoTest.setBorder(BorderFactory.createLineBorder(Color.black));
        calcBloodChemistry.setText("血液化学検査");
        calcBloodChemistry.setOpaque(true);
        calcBloodChemistry.setBackground(Color.lightGray);
        calcBloodChemistry.setBorder(BorderFactory
                .createLineBorder(Color.black));
        calcBloodBasic.setText("血液一般検査");
        calcBloodBasic.setOpaque(true);
        calcBloodBasic.setBackground(Color.lightGray);
        calcBloodBasic.setBorder(BorderFactory.createLineBorder(Color.black));
        spaceType.setEditable(false);
        spaceType.setColumns(4);
        spaceType.setHorizontalAlignment(SwingConstants.CENTER);
        calcXRayPoint.setColumns(7);
        calcXRayPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcXRayPoint.setEditable(false);
        calcBloodBasicPoint.setColumns(7);
        calcBloodBasicPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcBloodBasicPoint.setEditable(false);
        calcBloodChemistryPoint.setColumns(7);
        calcBloodChemistryPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcBloodChemistryPoint.setEditable(false);
        calcCheckTotalPoint.setColumns(7);
        calcCheckTotalPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcCheckTotalPoint.setEditable(false);
        firstTestSummary.setColumns(50);
        firstTestSummary.setBindPath("SHOSIN_TEKIYOU");
        firstTestSummary.setMaxLength(50);
        calcCheckTotalCost.setEditable(false);
        calcCheckTotalCost.setColumns(13);
        calcCheckTotalCost.setHorizontalAlignment(SwingConstants.RIGHT);
        calcCheckTotalCost.setText("2700");
        firstTestPoint.setEditable(false);
        firstTestPoint.setColumns(7);
        firstTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        firstTestPoint.setMaxLength(6);
        // firstTestPoint.setCharType(IkenshoConstants.CHAR_TYPE_DOUBLE1);
        // firstTestPoint.setFormat(IkenshoConstants.FORMAT_DOUBLE1);
        firstTest.setBackground(Color.white);
        firstTest.setBorder(BorderFactory.createLineBorder(Color.black));
        firstTest.setActionCommand("初診");
        firstTest.setText("初診　　　");
        firstTest.setBindPath("SHOSIN_TAISHOU");
        rootLayout.setFitHGrid(false);
        rootLayout.setFitVLast(true);
        rootLayout.setFitHLast(true);
        calcNyoTestPoint.setEditable(false);
        calcNyoTestPoint.setColumns(7);
        calcNyoTestPoint.setHorizontalAlignment(SwingConstants.RIGHT);
        calcCheckTotalCostUnit.setText("円");
        calcCheckTotalCostUnit.setOpaque(true);
        calcCheckTotalCostUnit.setPreferredSize(new Dimension(13, 17));
        calcCheckTotalCostUnit.setBackground(Color.lightGray);
        calcCheckTotalCostUnit.setBorder(BorderFactory
                .createLineBorder(Color.black));
        calcBloodBasicSummary.setColumns(40);
        calcBloodBasicSummary.setBindPath("BLD_IPPAN_TEKIYOU");
        calcBloodBasicSummary.setMaxLength(50);
        calcBloodChemistrySummary.setColumns(50);
        calcBloodChemistrySummary.setBindPath("BLD_KAGAKU_TEKIYOU");
        calcBloodChemistrySummary.setMaxLength(50);
        calcXRaySummary.setColumns(50);
        calcXRaySummary.setBindPath("XRAY_TEKIYOU");
        calcXRaySummary.setMaxLength(50);
        calcNyoTestSummary.setMaxLength(50);
        calcNyoTestSummary.setColumns(50);
        calcNyoTestSummary.setBindPath("NYO_KENSA_TEKIYOU");
        calcCheckMessage2.setText("査");
        calcCheckMessage2.setOpaque(false);
        calcCheckMessage1.setMinimumSize(new Dimension(11, 60));
        calcCheckMessage1.setText("検");
        calcCheckMessages.setBackground(Color.lightGray);
        calcCheckMessages
                .setBorder(BorderFactory.createLineBorder(Color.black));
        toIkenshoName.setBindPath("ISS_INSURER_NM");
        toIkenshoName.setText("テスト自治体");
        toIkenshoNoHead.setText("（");
        toIkenshoNo.setBindPath("ISS_INSURER_NO");
        toIkenshoNo.setText("123456");
        toIkenshoNoFoot.setText("）");
        toIkenshoPanelLayout.setHgap(0);
        toConsultationNo.setBindPath("SKS_INSURER_NO");
        toConsultationNo.setText("123456");
        toConsultationNoFoot.setText("）");
        toConsultationNoHead.setText("（");
        hiddenParameters.setText("隠しパラメタ");
        outputPattern.setBindPath("SEIKYUSHO_OUTPUT_PATTERN");
        hakkouKubun.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        fdOutputKubun.setBindPath("FD_OUTPUT_UMU");
        ikenshoCharge.setBindPath("IKN_CHARGE");
        shoshin.setBindPath("SHOSIN");
        // 2009/01/13 [Tozo Tanaka] Delete - begin
//        pointsRightSpacer1.setText(" ");
        // 2009/01/13 [Tozo Tanaka] Delete - end
        hiddenParameters.add(shoshin, null);
        hiddenParameters.add(ikenshoCharge, null);
        hiddenParameters.add(fdOutputKubun, null);
        hiddenParameters.add(hakkouKubun, null);
        bloodBasicTestPoints.add(bloodBasicTestPoint, null);
        bloodBasicTestPoints.add(bloodBasicTestPointUnit, null);
        ketsuekigakuTestCostPoints.add(ketsuekigakuTestCostPoint, null);
        ketsuekigakuTestCostPoints.add(ketsuekigakuTestCostPointUnit, null);
        // 2009/01/13 [Tozo Tanaka] Replace - begin
//        pointsLeft.add(bloodChemistryTestPoints, new GridBagConstraints(2, 5,
//                1, 1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(ketsuekigakuTestCostPoints, new GridBagConstraints(2, 3,
//                1, 1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasicTestPoints, new GridBagConstraints(2, 2, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodSaishu, new GridBagConstraints(0, 0, 2, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasic, new GridBagConstraints(0, 1, 2, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasicTest, new GridBagConstraints(1, 2, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(ketsuekigakuTestCost, new GridBagConstraints(1, 3, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodChemistry, new GridBagConstraints(0, 4, 2, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodChemistryTest, new GridBagConstraints(1, 5, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(seikagakuTestCost, new GridBagConstraints(1, 6, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodBasicSpacer, new GridBagConstraints(0, 2, 1, 2,
//                0.0, 0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(bloodSaishuPoints, new GridBagConstraints(2, 0, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsLeft.add(seikagakuTestCostPoints, new GridBagConstraints(2, 6, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayBasicPoints, new GridBagConstraints(2, 2, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(nyouTest, new GridBagConstraints(0, 0, 2, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xray, new GridBagConstraints(0, 1, 2, 1, 100.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        pointsRight.add(xrayBasic, new GridBagConstraints(1, 2, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayPhotoCheck, new GridBagConstraints(1, 3, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayFilm, new GridBagConstraints(1, 4, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xraySpacer, new GridBagConstraints(0, 2, 1, 3, 0.0,
//                0.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayPhotoCheckPoints, new GridBagConstraints(2, 3, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.NONE, new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(xrayFilmPoints, new GridBagConstraints(2, 4, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(nyouTestPoints, new GridBagConstraints(2, 0, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.NONE,
//                new Insets(0, 0, 0, 0), 0, 0));
//        pointsRight.add(pointsRightSpacer1, new GridBagConstraints(0, 5, 3, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
        pointsLeft.setAutoWrap(false);
        pointsRight.setAutoWrap(false);
        pointsLeft.setHgrid(0);
        pointsLeft.setHgap(0);
        pointsRight.setHgrid(0);
        pointsRight.setHgap(0);
        //チェックボックスのサイズを規定
        Dimension checkSpaceDimension = new Dimension(20, 10);
        for(int i=0; i<pointsSpacer.length; i++){
            pointsSpacer[i].setPreferredSize(checkSpaceDimension);
        }
        Dimension nyouTestDimension = nyouTest.getPreferredSize();
        Dimension checkMinDimension = new Dimension((int) Math.max(200,
                nyouTestDimension.getWidth() + 20), (int) Math.max(19,
                nyouTestDimension.getHeight()));
        ACIntegerCheckBox[] checks = new ACIntegerCheckBox[] { bloodSaishu,
                bloodBasic, bloodChemistry, nyouTest, xray, };
        for (int i = 0; i < checks.length; i++) {
            checks[i].setMinimumSize(checkMinDimension);
            checks[i].setPreferredSize(checkMinDimension);
        }
        checkMinDimension = new Dimension(
                (int) (checkMinDimension.getWidth() - checkSpaceDimension.getWidth()),
                (int) checkMinDimension.getHeight());
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit begin 診療報酬単価の変更対応
        checks = new ACIntegerCheckBox[] { bloodBasicTest,
                ketsuekigakuTestCost, bloodChemistryTest, seikagakuTestCost,
                xrayBasic, xrayDigital, xrayPhotoCheck, xrayFilm,
                xrayDigitalImageManagement, xrayDigitalFilm,
                xrayDigitalImaging, };
        // [ID:0000601][Masahiko Higuchi] 2010/02 edit end
        for (int i = 0; i < checks.length; i++) {
            checks[i].setMinimumSize(checkMinDimension);
            checks[i].setPreferredSize(checkMinDimension);
        }
        
        int pointsSpacerIndex = 0;
        //血液採取(静脈)
        pointsLeft.add(bloodSaishu, VRLayout.FLOW);
        pointsLeft.add(bloodSaishuPoints, VRLayout.FLOW_RETURN);
        //血液一般検査
        pointsLeft.add(bloodBasic, VRLayout.FLOW_RETURN);
        //血液一般検査＞末梢血液一般検査
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(bloodBasicTest, VRLayout.FLOW);
        pointsLeft.add(bloodBasicTestPoints, VRLayout.FLOW_RETURN);
        //血液一般検査＞血液学的検査判断料
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(ketsuekigakuTestCost, VRLayout.FLOW);
        pointsLeft.add(ketsuekigakuTestCostPoints, VRLayout.FLOW_RETURN);
        //血液化学検査
        pointsLeft.add(bloodChemistry, VRLayout.FLOW_RETURN);
        //血液化学検査＞血液化学検査
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(bloodChemistryTest, VRLayout.FLOW);
        pointsLeft.add(bloodChemistryTestPoints, VRLayout.FLOW_RETURN);
        //血液化学検査＞生化学的検査(I)判断料
        pointsLeft.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsLeft.add(seikagakuTestCost, VRLayout.FLOW);
        pointsLeft.add(seikagakuTestCostPoints, VRLayout.FLOW_RETURN);

        //尿中一般物質定性半定量検査
//        pointsLeft.add(nyouTest, VRLayout.FLOW);
//        pointsLeft.add(nyouTestPoints, VRLayout.FLOW_RETURN);
        pointsRight.add(nyouTest, VRLayout.FLOW);
        pointsRight.add(nyouTestPoints, VRLayout.FLOW_RETURN);
        
        //胸部単純X線撮影
        pointsRight.add(xray, VRLayout.FLOW_RETURN);
        //胸部単純X線撮影＞単純撮影
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayBasic, VRLayout.FLOW);
        pointsRight.add(xrayBasicPoints, VRLayout.FLOW_RETURN);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin 診療報酬単価の変更対応
        //胸部単純X線撮影＞単純撮影(デジタル）
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayDigital, VRLayout.FLOW);
        pointsRight.add(xrayDigitalPoints, VRLayout.FLOW_RETURN);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        //胸部単純X線撮影＞写真診断(胸部)
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayPhotoCheck, VRLayout.FLOW);
        pointsRight.add(xrayPhotoCheckPoints, VRLayout.FLOW_RETURN);
        //胸部単純X線撮影＞フィルム(大角)
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayFilm, VRLayout.FLOW);
        pointsRight.add(xrayFilmPoints, VRLayout.FLOW_RETURN);
        //胸部単純X線撮影＞電子画像管理加算
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayDigitalImageManagement, VRLayout.FLOW);
        pointsRight.add(xrayDigitalImageManagementPoints, VRLayout.FLOW_RETURN);
        //胸部単純X線撮影＞画像記録用フィルム(大角)
        pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        pointsRight.add(xrayDigitalFilm, VRLayout.FLOW);
        pointsRight.add(xrayDigitalFilmPoints, VRLayout.FLOW_RETURN);
        //胸部単純X線撮影＞デジタル映像化処理加算
        // [ID:0000601][Masahiko Higuchi] 2010/02 del begin 診療報酬単価の変更対応
        // このスペースがあるとMacで見切れるのでAddしない
        //pointsRight.add(pointsSpacer[pointsSpacerIndex++], VRLayout.FLOW);
        // [ID:0000601][Masahiko Higuchi] 2010/02 del end
        pointsRight.add(xrayDigitalImaging, VRLayout.FLOW);
        pointsRight.add(xrayDigitalImagingPoints, VRLayout.FLOW_RETURN);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add begin 診療報酬単価の変更対応
        // 非表示化
        xrayDigitalImaging.setVisible(false);
        xrayDigitalImagingPoints.setVisible(false);
        // [ID:0000601][Masahiko Higuchi] 2010/02 add end
        // 2009/01/13 [Tozo Tanaka] Replace - end

        seikagakuTestCostPoints.add(seikagakuTestCostPoint, null);
        seikagakuTestCostPoints.add(seikagakuTestCostPointUnit, null);
        bloodSaishuPoints.add(bloodSaishuPoint, null);
        bloodSaishuPoints.add(bloodSaishuPointUnit, null);
        nyouTestPoints.add(nyouTestPoint, null);
        nyouTestPoints.add(nyouTestPointUnit, null);
        xrayBasicPoints.add(xrayBasicPoint, null);
        xrayBasicPoints.add(xrayBasicPointUnit, null);
        xrayPhotoCheckPoints.add(xrayPhotoCheckPoint, null);
        xrayPhotoCheckPoints.add(xrayPhotoCheckPointUnit, null);
        xrayFilmPoints.add(xrayFilmPoint, null);
        xrayFilmPoints.add(xrayFilmPointUnit, null);
        bloodChemistryTestPoints.add(bloodChemistryTestPoint, null);
        bloodChemistryTestPoints.add(bloodChemistryTestPointUnit, null);
        bloodChemistryTestPoints.add(bloodChemistryTestPointChange, null);
        costs.add(costTarget, null);
        costs.add(costType, null);
        costs.add(cost, null);
        costs.add(costUnit, null);
        taxs.add(tax, null);
        taxs.add(taxUnit, null);
        toIkenshoPanelLayout.setAutoWrap(false);
        toIkenshoPanel.setLayout(toIkenshoPanelLayout);
        toIkenshoPanel.add(toIkenshoHead, VRLayout.FLOW);
        toConsultationPanelLayout.setAutoWrap(false);
        toConsultationPanelLayout.setHgap(0);
        toConsultationPanel.setLayout(toConsultationPanelLayout);
        
        // 2009/01/13 [Tozo Tanaka] Replace - begin
//        calcRoot.add(category, new GridBagConstraints(0, 0, 3, 1, 30.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(point, new GridBagConstraints(3, 0, 1, 1, 100.0, 100.0,
//                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
//                        0, 0, 0, 0), 0, 0));
//        calcRoot.add(summary, new GridBagConstraints(4, 0, 3, 1, 100.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(calcXRay, new GridBagConstraints(1, 2, 2, 1, 20.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodBasic, new GridBagConstraints(1, 3, 2, 1, 20.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodChemistry, new GridBagConstraints(1, 4, 2, 1,
//                20.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcNyoTest, new GridBagConstraints(1, 5, 2, 1, 20.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotal, new GridBagConstraints(0, 6, 3, 1, 30.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalCostMessage, new GridBagConstraints(4, 6, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(spaceType, new GridBagConstraints(2, 1, 1, 1, 10.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(calcXRayPoint, new GridBagConstraints(3, 2, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodBasicPoint, new GridBagConstraints(3, 3, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodChemistryPoint, new GridBagConstraints(3, 4, 1,
//                1, 100.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalPoint, new GridBagConstraints(3, 6, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(firstTestSummary, new GridBagConstraints(4, 1, 3, 1,
//                300.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalCost, new GridBagConstraints(5, 6, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(firstTest, new GridBagConstraints(0, 1, 2, 1, 20.0, 100.0,
//                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
//                        0, 0, 0), 0, 0));
//        calcRoot.add(firstTestPoint, new GridBagConstraints(3, 1, 1, 1, 100.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 3));
//        calcRoot.add(calcNyoTestPoint, new GridBagConstraints(3, 5, 1, 1,
//                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckTotalCostUnit, new GridBagConstraints(6, 6, 1, 1,
//                10.0, 100.0, GridBagConstraints.CENTER,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcXRaySummary, new GridBagConstraints(4, 2, 3, 1, 300.0,
//                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodBasicSummary, new GridBagConstraints(4, 3, 3, 1,
//                300.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcBloodChemistrySummary, new GridBagConstraints(4, 4, 3,
//                1, 300.0, 100.0, GridBagConstraints.WEST,
//                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcNyoTestSummary, new GridBagConstraints(4, 5, 3, 1,
//                300.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));
//        calcRoot.add(calcCheckMessages, new GridBagConstraints(0, 2, 1, 4, 0.0,
//                0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
//                new Insets(0, 0, 0, 0), 0, 0));        
        calcRoot.add(category, new GridBagConstraints(0, 0, 3, 1, 30.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(point, new GridBagConstraints(3, 0, 1, 1, 200.0, 100.0,
                GridBagConstraints.CENTER, GridBagConstraints.BOTH, new Insets(
                        0, 0, 0, 0), 0, 0));
        calcRoot.add(summary, new GridBagConstraints(4, 0, 3, 1, 2000.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(calcXRay, new GridBagConstraints(1, 2, 2, 1, 20.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(calcBloodBasic, new GridBagConstraints(1, 3, 2, 1, 20.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodChemistry, new GridBagConstraints(1, 4, 2, 1,
                20.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcNyoTest, new GridBagConstraints(1, 5, 2, 1, 20.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotal, new GridBagConstraints(0, 6, 3, 1, 30.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalCostMessage, new GridBagConstraints(4, 6, 1,
                1, 100.0, 100.0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(spaceType, new GridBagConstraints(2, 1, 1, 1, 10.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(calcXRayPoint, new GridBagConstraints(3, 2, 1, 1, 200.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodBasicPoint, new GridBagConstraints(3, 3, 1, 1,
                200.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodChemistryPoint, new GridBagConstraints(3, 4, 1,
                1, 200.0, 100.0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalPoint, new GridBagConstraints(3, 6, 1, 1,
                200.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(firstTestSummary, new GridBagConstraints(4, 1, 3, 1,
                2000.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalCost, new GridBagConstraints(5, 6, 1, 1,
                100.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(firstTest, new GridBagConstraints(0, 1, 2, 1, 20.0, 100.0,
                GridBagConstraints.WEST, GridBagConstraints.BOTH, new Insets(0,
                        0, 0, 0), 0, 0));
        calcRoot.add(firstTestPoint, new GridBagConstraints(3, 1, 1, 1, 200.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 3));
        calcRoot.add(calcNyoTestPoint, new GridBagConstraints(3, 5, 1, 1,
                200.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckTotalCostUnit, new GridBagConstraints(6, 6, 1, 1,
                10.0, 100.0, GridBagConstraints.CENTER,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcXRaySummary, new GridBagConstraints(4, 2, 3, 1, 2000.0,
                100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodBasicSummary, new GridBagConstraints(4, 3, 3, 1,
                2000.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcBloodChemistrySummary, new GridBagConstraints(4, 4, 3,
                1, 2000.0, 100.0, GridBagConstraints.WEST,
                GridBagConstraints.BOTH, new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcNyoTestSummary, new GridBagConstraints(4, 5, 3, 1,
                2000.0, 100.0, GridBagConstraints.WEST, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        calcRoot.add(calcCheckMessages, new GridBagConstraints(0, 2, 1, 4, 0.0,
                0.0, GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0), 0, 0));
        // 2009/01/13 [Tozo Tanaka] Replace - end
        calcCheckMessages.add(calcCheckMessage1, BorderLayout.NORTH);
        calcCheckMessages.add(calcCheckMessage2, BorderLayout.SOUTH);
        root.add(hiddenParameters, null);
        toGroup.add(toIkenshoPanel, VRLayout.FLOW_RETURN);
        toGroup.add(toConsultationPanel, VRLayout.FLOW_RETURN);
        taxGroup.add(taxs, null);
        costGroup.add(costs, null);
        taxPanel.add(costGroup, VRLayout.CLIENT);
        taxPanel.add(taxGroup, VRLayout.CLIENT);
        bodyGroup.add(points, VRLayout.NORTH);
        bodyGroup.add(calcRoot, VRLayout.NORTH);
        buttons.add(reset, VRLayout.WEST);
        buttons.add(cancel, VRLayout.EAST);
        buttons.add(ok, VRLayout.EAST);

        root.add(toGroup, VRLayout.NORTH);
        root.add(taxPanel, VRLayout.NORTH);
        root.add(bodyGroup, VRLayout.CLIENT);
        root.add(buttons, VRLayout.SOUTH);
        this.getContentPane().add(root, BorderLayout.CENTER);

        toIkenshoPanel.add(toIkenshoName, VRLayout.FLOW);
        toIkenshoPanel.add(toIkenshoNoHead, VRLayout.FLOW);
        toIkenshoPanel.add(toIkenshoNo, VRLayout.FLOW);
        toIkenshoPanel.add(toIkenshoNoFoot, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationHead, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationName, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationNoHead, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationNo, VRLayout.FLOW);
        toConsultationPanel.add(toConsultationNoFoot, VRLayout.FLOW);
        hiddenParameters.add(outputPattern, null);

    }

    protected class IkenshoTreeFollowChecker {

        /**
         * コンストラクタです。
         * 
         * @param parent 親としてチェックを追随するコンポーネント
         * @param children 子としてチェックを追随するコンポーネント群
         */
        public IkenshoTreeFollowChecker(JCheckBox parent, JCheckBox[] children) {
            IkenshoTreeFollowChildrenListener childrenListener = new IkenshoTreeFollowChildrenListener(
                    parent, children);
            IkenshoTreeFollowPatientListener parentListener = new IkenshoTreeFollowPatientListener(
                    parent, children);

            parent.addItemListener(parentListener);
            int end = children.length;
            for (int i = 0; i < end; i++) {
                children[i].addItemListener(childrenListener);
            }

        }
        
        //2009/01/13 [Tozo Tanaka] Add - begin
        public IkenshoTreeFollowChecker(JCheckBox parent, JCheckBox[] children, JCheckBox[] parentCheckedFollowChildren) {
            IkenshoTreeFollowChildrenListener childrenListener = new IkenshoTreeFollowChildrenListener(
                    parent, children);
            IkenshoTreeFollowPatientListener parentListener = new IkenshoTreeFollowPatientListener(
                    parent, children, parentCheckedFollowChildren);

            parent.addItemListener(parentListener);
            int end = children.length;
            for (int i = 0; i < end; i++) {
                children[i].addItemListener(childrenListener);
            }

        }
        //2009/01/13 [Tozo Tanaka] Add - end
        
        protected class IkenshoTreeFollowChildrenListener implements
                ItemListener {
            private JCheckBox parent;
            private JCheckBox[] children;

            public IkenshoTreeFollowChildrenListener(JCheckBox parent,
                    JCheckBox[] children) {
                this.parent = parent;
                this.children = children;
            }

            public void itemStateChanged(ItemEvent e) {
                boolean select;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    select = true;
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    select = false;
                } else {
                    return;
                }
                if (parent != null) {
                    if (!parent.isFocusOwner()) {
                        if (parent.isSelected() != select) {
                            // 親を追随
                            if (!select) {
                                // 解除するかどうかは子を全走査
                                int selectCount = 0;
                                int end = children.length;
                                for (int i = 0; i < end; i++) {
                                    if (children[i].isSelected()) {
                                        selectCount++;
                                    }
                                }
                                if (selectCount > 0) {
                                    // 他の子が選択されているので親は追随させない。
                                    return;
                                }
                            }
                            parent.setSelected(select);
                        }
                    }
                }
            }
        }

        //2009/01/13 [Tozo Tanaka] Replace - begin
//        protected class IkenshoTreeFollowPatientListener implements
//                ItemListener {
//            private JCheckBox parent;
//            private JCheckBox[] children;
//
//            public IkenshoTreeFollowPatientListener(JCheckBox parent,
//                    JCheckBox[] children) {
//                this.parent = parent;
//                this.children = children;
//            }
//
//            public void itemStateChanged(ItemEvent e) {
//                boolean select;
//                if (e.getStateChange() == ItemEvent.SELECTED) {
//                    select = true;
//                    if (!parent.isFocusOwner()) {
//                        return;
//                    }
//                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
//                    select = false;
//                } else {
//                    return;
//                }
//                if (children != null) {
//                    // 子を追随
//                    int end = children.length;
//                    for (int i = 0; i < end; i++) {
//                        if (children[i].isSelected() != select) {
//                            children[i].setSelected(select);
//                        }
//                    }
//                }
//            }
//        }
        protected class IkenshoTreeFollowPatientListener implements
                ItemListener {
            private JCheckBox parent;
            private JCheckBox[] children;
            private JCheckBox[] parentCheckedFollowChildren;

            public IkenshoTreeFollowPatientListener(JCheckBox parent,
                    JCheckBox[] children) {
                this.parent = parent;
                this.children = children;
            }

            public IkenshoTreeFollowPatientListener(JCheckBox parent,
                    JCheckBox[] children, JCheckBox[] parentCheckedFollowChildren) {
                this.parent = parent;
                this.children = children;
                this.parentCheckedFollowChildren = parentCheckedFollowChildren;
            }

            public void itemStateChanged(ItemEvent e) {
                boolean select;
                JCheckBox[] targetChildren = null;
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    select = true;
                    if (!parent.isFocusOwner()) {
                        return;
                    }
                    if(parentCheckedFollowChildren!=null){
                        targetChildren = parentCheckedFollowChildren;
                    }else{
                        targetChildren = children;
                    }
                } else if (e.getStateChange() == ItemEvent.DESELECTED) {
                    select = false;
                    targetChildren = children;
                } else {
                    return;
                }
                if (targetChildren != null) {
                    // 子を追随
                    int end = targetChildren.length;
                    for (int i = 0; i < end; i++) {
                        if (targetChildren[i].isSelected() != select) {
                            targetChildren[i].setSelected(select);
                        }
                    }
                }
            }
        }
        //2009/01/13 [Tozo Tanaka] Replace - end

    }

    protected class IkenshoFollowCalcItemListener implements ItemListener {
        protected JTextField point;
        protected VRMap source;
        protected String key;
        protected JCheckBox[] checks;
        protected JTextField[] points;
        protected String[] summarys;
        protected JTextField totalPoint;
        protected JTextField totalSummary;
        protected IkenshoConsultationInfo adaptee;

        /**
         * コンストラクタです。
         * 
         * @param point チェック項目が直接関係する点数欄
         * @param source 点数マップ
         * @param key 対応する点数キー
         * @param checks 付随して関連するチェック群
         * @param points 付随して関連する点数欄群
         * @param summarys 付随して出力する摘要群
         * @param totalPoint 合計点数欄
         * @param totalSummary 合計の摘要欄
         * @param adaptee アダプタ
         */
        public IkenshoFollowCalcItemListener(JTextField point, VRMap source,
                String key, JCheckBox[] checks, JTextField[] points,
                String[] summarys, JTextField totalPoint,
                JTextField totalSummary, IkenshoConsultationInfo adaptee) {
            this.point = point;
            this.source = source;
            this.key = key;
            this.points = points;
            this.checks = checks;
            this.summarys = summarys;
            this.totalPoint = totalPoint;
            this.totalSummary = totalSummary;
            this.adaptee = adaptee;
        }

        public void follow(boolean select) {
            try {
                if (select) {
                    Object val = VRBindPathParser.get(key, source);
                    if (val == null) {
                        point.setText("");
                    } else {
                        point.setText(IkenshoConstants.FORMAT_DOUBLE1
                                .format(new Double(String.valueOf(val))));
                    }
                } else {
                    point.setText("");
                }

                ArrayList array = new ArrayList();
                double total = 0;
                int end = checks.length;
                for (int i = 0; i < end; i++) {
                    if (checks[i].isSelected()) {
                        array.add(summarys[i]);
                        // 2006/02/11[Tozo Tanaka] : replace begin
                        // TODO canEdit?
                        // total += Double.parseDouble(points[i].getText());
                        String val = points[i].getText();
                        if (!"".equals(val)) {
                            total += Double.parseDouble(val);
                        }
                        // 2006/02/11[Tozo Tanaka] : replace end
                    }
                }

                end = array.size();
                if (end == 0) {
                    totalPoint.setText("");
                    totalSummary.setText("");
                } else {
                    totalPoint.setText(IkenshoConstants.FORMAT_DOUBLE1
                            .format(new Double(total)));
                    StringBuffer sb = new StringBuffer(String.valueOf(array
                            .get(0)));
                    for (int i = 1; i < end; i++) {
                        String val = String.valueOf(array.get(i));
                        if (!"".equals(val)) {
                            sb.append("、");
                            sb.append(val);
                        }
                    }
                    totalSummary.setText(sb.toString());
                    totalSummary.setSelectionStart(0);
                    totalSummary.setSelectionEnd(0);
                }
                adaptee.calcCost();
            } catch (ParseException ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }
        }

        public void itemStateChanged(ItemEvent e) {
            follow(e.getStateChange() == ItemEvent.SELECTED);
        }
    }

}
