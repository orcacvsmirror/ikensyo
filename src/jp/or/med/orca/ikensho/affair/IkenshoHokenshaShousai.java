/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;
import java.text.Format;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACOneDecimalDoubleFormat;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

public class IkenshoHokenshaShousai extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton update = new ACAffairButton();
    private VRPanel tabPnl = new VRPanel();
    private JTabbedPane tab = new JTabbedPane(JTabbedPane.TOP,
            JTabbedPane.SCROLL_TAB_LAYOUT);

    private VRPanel tab1 = new VRPanel();
    private VRPanel tab1North = new VRPanel();
    private ACLabelContainer insurerNmContainer = new ACLabelContainer();
    private ACTextField insurerNmField = new ACTextField();
    private ACLabelContainer insurerNoContainer = new ACLabelContainer();
    private ACTextField insurerNoField = new ACTextField();

    private ACGroupBox seikyuPatternGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup seikyuPattern = new ACClearableRadioButtonGroup();

    private ACGroupBox issGrp = new ACGroupBox();
    private ACLabelContainer issInsurerNmContainer = new ACLabelContainer();
    private ACTextField issInsurerNm = new ACTextField();
    private ACLabelContainer issInsurerNoContainer = new ACLabelContainer();
    private ACTextField issInsurerNo = new ACTextField();
    private ACButton issInsurerSame = new ACButton();
    private VRPanel seikyusakiPanel = new VRPanel();
    private ACGroupBox soukatuhyouGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup soukatuhyou = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox soukatuhyouPrint = new ACIntegerCheckBox();
    private ACGroupBox meisaiKindGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup meisaiKind = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox meisaiKindPrint = new ACIntegerCheckBox();

    private ACGroupBox sksGrp = new ACGroupBox();
    private ACLabelContainer sksInsurerNmContainer = new ACLabelContainer();
    private ACTextField sksInsurerNm = new ACTextField();
    private ACLabelContainer sksInsurerNoContainer = new ACLabelContainer();
    private ACTextField sksInsurerNo = new ACTextField();
    private ACButton sksInsurerSame = new ACButton();
    private VRPanel seikyusakiPanel2 = new VRPanel();
    private ACGroupBox soukatuhyouGrp2 = new ACGroupBox();
    private ACClearableRadioButtonGroup soukatuhyou2 = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox soukatuhyouPrint2 = new ACIntegerCheckBox();
    private ACGroupBox meisaiKindGrp2 = new ACGroupBox();
    private ACClearableRadioButtonGroup meisaiKind2 = new ACClearableRadioButtonGroup();
    private ACIntegerCheckBox meisaiKindPrint2 = new ACIntegerCheckBox();

    private VRPanel tab2 = new VRPanel();

    private VRPanel seikyushoCsvPnl = new VRPanel();
    private ACGroupBox seikyushoPrintGrp = new ACGroupBox();
    private ACIntegerCheckBox seikyushoPrint = new ACIntegerCheckBox();
    private ACGroupBox csvGrp = new ACGroupBox();
    private ACIntegerCheckBox csv = new ACIntegerCheckBox();

    private ACGroupBox printOptionGrp = new ACGroupBox();
    private ACGroupBox drNameGrp = new ACGroupBox();
    private ACIntegerCheckBox drName = new ACIntegerCheckBox();
    private ACGroupBox pageHeaderPrintGrp = new ACGroupBox();
    private ACIntegerCheckBox pageHeaderPrint = new ACIntegerCheckBox();
    private ACGroupBox pageHeaderPrintGrp2 = new ACGroupBox();
    private ACIntegerCheckBox pageHeaderPrint2 = new ACIntegerCheckBox();

    private ACGroupBox pointsGrp = new ACGroupBox();
    private VRPanel sakuseiryoShosinryoPnl = new VRPanel();

    private ACGroupBox ikenshoSakuseiryouGrp = new ACGroupBox();
    private JPanel zaitakuPnl = new JPanel();
    private JLabel zaitaku = new JLabel();
    private VRPanel zaitakuTextPnl = new VRPanel();
    private ACLabelContainer zaitakuSinkiChargeContainer = new ACLabelContainer();
    private ACTextField zaitakuSinkiCharge = new ACTextField();
    private JLabel zaitakuSinkiChargeUnit = new JLabel();
    private ACLabelContainer zaitakuKeizokuChargeContainer = new ACLabelContainer();
    private ACTextField zaitakuKeizokuCharge = new ACTextField();
    private JLabel zaitakuKeizokuChargeUnit = new JLabel();
    private JPanel sisetuPnl = new JPanel();
    private JLabel sisetu = new JLabel();
    private VRPanel sisetuTextPnl = new VRPanel();
    private ACLabelContainer sisetuSinkiChargeContainer = new ACLabelContainer();
    private ACTextField sisetuSinkiCharge = new ACTextField();
    private JLabel sisetuSinkiChargeUnit = new JLabel();
    private ACLabelContainer sisetuKeizokuChargeContainer = new ACLabelContainer();
    private ACTextField sisetuKeizokuCharge = new ACTextField();
    private JLabel sisetuKeizokuChargeUnit = new JLabel();

    private ACGroupBox shosinGrp = new ACGroupBox();
    private JPanel shosinPnl = new JPanel();
    private JLabel shosin = new JLabel();
    private VRPanel shosinTextPnl = new VRPanel();
    private ACLabelContainer shosinSinryoujoContainer = new ACLabelContainer();
    private ACTextField shosinSinryoujo = new ACTextField();
    private JLabel shosinSinryoujoUnit = new JLabel();
    private ACLabelContainer shosinHospitalContainer = new ACLabelContainer();
    private ACTextField shosinHospital = new ACTextField();
    private JLabel shosinHospitalUnit = new JLabel();

    private ACGroupBox sinsatuPointsGrp = new ACGroupBox();
    private VRPanel sinsatuPointsLeftPnl = new VRPanel();
    private VRPanel sinsatuPointsRightPnl = new VRPanel();
    private ACLabelContainer expKsContainer = new ACLabelContainer();
    private ACTextField expKs = new ACTextField();
    private JLabel expKsUnit = new JLabel();
    private ACLabelContainer expKikMkiContainer = new ACLabelContainer();
    private ACTextField expKikMki = new ACTextField();
    private JLabel expKikMkiUnit = new JLabel();
    private ACLabelContainer expKikKekkContainer = new ACLabelContainer();
    private ACTextField expKikKekk = new ACTextField();
    private JLabel expKikKekkUnit = new JLabel();
    private ACLabelContainer expKkkKkkContainer = new ACLabelContainer();
    private ACTextField expKkkKkk = new ACTextField();
    private JLabel expKkkKkkUnit = new JLabel();
    private ACLabelContainer expKkkSkkContainer = new ACLabelContainer();
    private ACTextField expKkkSkk = new ACTextField();
    private JLabel expKkkSkkUnit = new JLabel();
    private ACLabelContainer expNitkContainer = new ACLabelContainer();
    private ACTextField expNitk = new ACTextField();
    private JLabel expNitkUnit = new JLabel();
    private ACLabelContainer expXrayTsContainer = new ACLabelContainer();
    private ACTextField expXrayTs = new ACTextField();
    private JLabel expXrayTsUnit = new JLabel();
    private ACLabelContainer expXraySsContainer = new ACLabelContainer();
    private ACTextField expXraySs = new ACTextField();
    private JLabel expXraySsUnit = new JLabel();
    private ACLabelContainer expXrayFilmContainer = new ACLabelContainer();
    private ACTextField expXrayFilm = new ACTextField();
    private JLabel expXrayFilmUnit = new JLabel();

    private VRPanel rollBackPnl = new VRPanel();
    private ACButton rollBack = new ACButton();

    private VRMap insurerData; // 保険者データ
    private String insurerNo; // 保険者番号
    private String insurerNm; // 保険者名
    private boolean isUpdate; // true : 更新, false : 追加
    private boolean hasData; // true : 有, false : 無
    protected VRMap prevData; // 前画面キャッシュ

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "INSURER", new String[] { "INSURER_NO" },
            new Format[] { IkenshoConstants.FORMAT_PASSIVE_STRING },
            "LAST_TIME", "LAST_TIME");

    private static final String DOUBLE_INPUT_PERSER = "(\\d+)|((\\d+)(\\.\\d{0,1}))";

    /**
     * コンストラクタです。
     */
    public IkenshoHokenshaShousai() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {

        this.setBackground(IkenshoConstants.FRAME_BACKGROUND);
        this.add(buttons, VRLayout.NORTH);
        this.add(tabPnl, VRLayout.CLIENT);

        buttons.setTitle("保険者詳細");
        buttons.add(update, VRLayout.EAST);

        update.setText("登録(S)");
        update.setMnemonic('S');
        update.setActionCommand("登録(S)");
        update.setToolTipText("現在の内容を登録します。");
        update.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_UPDATE);

        tabPnl.setLayout(new BorderLayout());
        tabPnl.add(tab, BorderLayout.CENTER);
        tab.addTab("保険者情報1", tab1);
        tab.addTab("保険者情報2", tab2);

        // tab1
        tab1.setLayout(new VRLayout());
        tab1.add(tab1North, VRLayout.NORTH);
        tab1.add(seikyuPatternGrp, VRLayout.CLIENT);

        tab1North.setLayout(new VRLayout());
        tab1North.add(insurerNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        tab1North.add(insurerNoContainer, VRLayout.FLOW_INSETLINE_RETURN);

        // 保険者名称
        insurerNmContainer.setText("保険者名称(依頼した自治体)");
        insurerNmContainer.setLayout(new BorderLayout());
        insurerNmContainer.add(insurerNmField);
        insurerNmField.setColumns(40);
        insurerNmField.setMaxLength(40);
        insurerNmField.setIMEMode(InputSubset.KANJI);
        insurerNmField.setBindPath("INSURER_NM");

        // 保険者番号
        insurerNoContainer.setText("コード(保険者番号)(数字6桁)");
        insurerNoContainer.setLayout(new VRLayout());
        insurerNoContainer.add(insurerNoField, VRLayout.CLIENT);
        insurerNoField.setColumns(6);
        insurerNoField.setMaxLength(6);
        insurerNoField.setIMEMode(InputSubset.LATIN_DIGITS);
        insurerNoField.setCharType(VRCharType.ONLY_DIGIT);
        insurerNoField.setBindPath("INSURER_NO");

        // 請求パターン
        seikyuPatternGrp.setLayout(new VRLayout());
        seikyuPatternGrp.setText("請求パターン");
        seikyuPatternGrp.setForeground(Color.BLUE);
        seikyuPatternGrp.add(seikyuPattern, VRLayout.NORTH);
        seikyuPatternGrp.add(issGrp, VRLayout.NORTH);
        seikyuPatternGrp.add(sksGrp, VRLayout.NORTH);

        seikyuPattern.setLayout(new VRLayout());
        seikyuPattern.setUseClearButton(false);
        seikyuPattern.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "意見書作成料・検査料(1枚)", "意見書作成料(1枚)・検査料(1枚)",
                        "意見書作成料のみ", "検査料のみ" }))));
        seikyuPattern.setBindPath("SEIKYUSHO_OUTPUT_PATTERN");

        // 意見書請求先
        issGrp.setLayout(new VRLayout());
        issGrp.setForeground(Color.BLUE);
        issGrp.setText("意見書作成料請求先");
        issGrp.add(seikyusakiPanel, BorderLayout.NORTH);
        issGrp.add(soukatuhyouGrp, BorderLayout.NORTH);
        issGrp.add(meisaiKindGrp, BorderLayout.NORTH);
        VRLayout seikyusakiLayout = new VRLayout();
        seikyusakiLayout.setHgap(0);
        seikyusakiLayout.setAutoWrap(false);
        seikyusakiPanel.setLayout(seikyusakiLayout);
        seikyusakiPanel.add(issInsurerNmContainer, VRLayout.FLOW);
        seikyusakiPanel.add(issInsurerNoContainer, VRLayout.FLOW);
        seikyusakiPanel.add(issInsurerSame, VRLayout.FLOW_RETURN);

        issInsurerNmContainer.setText("名称");
        issInsurerNmContainer.setLayout(new BorderLayout());
        issInsurerNmContainer.add(issInsurerNm);
        issInsurerNm.setColumns(40);
        issInsurerNm.setMaxLength(40);
        issInsurerNm.setIMEMode(InputSubset.KANJI);
        issInsurerNm.setBindPath("ISS_INSURER_NM");

        issInsurerNoContainer.setText("保険者番号");
        issInsurerNoContainer.setLayout(new BorderLayout());
        issInsurerNoContainer.add(issInsurerNo);
        issInsurerNo.setColumns(6);
        issInsurerNo.setMaxLength(6);
        issInsurerNo.setIMEMode(InputSubset.LATIN_DIGITS);
        issInsurerNo.setCharType(VRCharType.ONLY_DIGIT);
        issInsurerNo.setBindPath("ISS_INSURER_NO");

        issInsurerSame.setText("依頼元と同一(C)");
        issInsurerSame.setMnemonic('C');

        // 意見書請求先・総括書
        soukatuhyouGrp.setLayout(new VRLayout());
        soukatuhyouGrp.setText("総括書(複数患者分の請求書を同時作成する場合の表紙)");
        soukatuhyouGrp.add(soukatuhyou, VRLayout.CLIENT);
        soukatuhyouGrp.add(soukatuhyouPrint, VRLayout.CLIENT);
        VRLayout soukatuhyouLayout = new VRLayout();
        soukatuhyouLayout.setAutoWrap(false);
        soukatuhyou.setLayout(soukatuhyouLayout);
        soukatuhyou.setUseClearButton(false);
        soukatuhyou.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "総括書あり", "総括書なし" }))));
        soukatuhyou.setBindPath("SOUKATUHYOU_PRT");
        soukatuhyouPrint.setText("振込先印刷する");
        soukatuhyouPrint.setBindPath("SOUKATU_FURIKOMI_PRT");

        // 意見書請求先・明細書
        meisaiKindGrp.setLayout(new VRLayout());
        meisaiKindGrp.setText("明細書種類");
        meisaiKindGrp.add(meisaiKind, VRLayout.CLIENT);
        meisaiKindGrp.add(meisaiKindPrint, VRLayout.CLIENT);
        VRLayout meisaiKindLayout = new VRLayout();
        meisaiKindLayout.setAutoWrap(false);
        meisaiKind.setLayout(meisaiKindLayout);
        meisaiKind.setUseClearButton(false);
        meisaiKind.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "明細書のみ", "一覧表のみ", "明細書と一覧表" }))));
        meisaiKind.setBindPath("MEISAI_KIND");
        meisaiKindPrint.setText("振込先印刷する");
        meisaiKindPrint.setBindPath("FURIKOMISAKI_PRT");

        // 診察・検査費用請求先
        sksGrp.setText("診察・検査費用請求先");
        sksGrp.setForeground(Color.BLUE);
        sksGrp.setLayout(new VRLayout());
        sksGrp.add(seikyusakiPanel2, BorderLayout.NORTH);
        sksGrp.add(soukatuhyouGrp2, BorderLayout.NORTH);
        sksGrp.add(meisaiKindGrp2, BorderLayout.NORTH);

        // 「意見書請求先」「診察・検査費用」
        VRLayout seikyusakiLayout2 = new VRLayout();
        seikyusakiLayout2.setHgap(0);
        seikyusakiLayout2.setAutoWrap(false);
        seikyusakiPanel2.setLayout(seikyusakiLayout2);
        seikyusakiPanel2.add(sksInsurerNmContainer, VRLayout.FLOW);
        seikyusakiPanel2.add(sksInsurerNoContainer, VRLayout.FLOW);
        seikyusakiPanel2.add(sksInsurerSame, VRLayout.FLOW_RETURN);

        sksInsurerNmContainer.setText("名称");
        sksInsurerNmContainer.setLayout(new BorderLayout());
        sksInsurerNmContainer.add(sksInsurerNm);
        sksInsurerNm.setColumns(40);
        sksInsurerNm.setMaxLength(40);
        sksInsurerNm.setIMEMode(InputSubset.KANJI);
        sksInsurerNm.setBindPath("SKS_INSURER_NM");

        sksInsurerNoContainer.setText("保険者番号");
        sksInsurerNoContainer.setLayout(new BorderLayout());
        sksInsurerNoContainer.add(sksInsurerNo);
        sksInsurerNo.setColumns(6);
        sksInsurerNo.setMaxLength(6);
        sksInsurerNo.setIMEMode(InputSubset.LATIN_DIGITS);
        sksInsurerNo.setCharType(VRCharType.ONLY_DIGIT);
        sksInsurerNo.setBindPath("SKS_INSURER_NO");

        sksInsurerSame.setText("依頼元と同一(P)");
        sksInsurerSame.setMnemonic('P');

        // 意見書請求先・総括書
        soukatuhyouGrp2.setText("総括書(複数患者分の請求書を同時作成する場合の表紙)");
        soukatuhyouGrp2.setLayout(new VRLayout());
        soukatuhyouGrp2.add(soukatuhyou2, VRLayout.CLIENT);
        soukatuhyouGrp2.add(soukatuhyouPrint2, VRLayout.CLIENT);
        VRLayout soukatuhyouLayout2 = new VRLayout();
        soukatuhyouLayout2.setAutoWrap(false);
        soukatuhyou2.setLayout(soukatuhyouLayout2);
        soukatuhyou2.setUseClearButton(false);
        soukatuhyou2.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "総括書あり", "総括書なし" }))));
        soukatuhyou2.setBindPath("SOUKATUHYOU_PRT2");
        soukatuhyouPrint2.setText("振込先印刷する");
        soukatuhyouPrint2.setBindPath("SOUKATU_FURIKOMI_PRT2");

        // 意見書請求先・明細書種類
        meisaiKindGrp2.setLayout(new VRLayout());
        meisaiKindGrp2.setText("明細書種類");
        meisaiKindGrp2.add(meisaiKind2, VRLayout.CLIENT);
        meisaiKindGrp2.add(meisaiKindPrint2, VRLayout.CLIENT);
        VRLayout meisaiKindLayout2 = new VRLayout();
        meisaiKindLayout2.setAutoWrap(false);
        meisaiKind2.setLayout(meisaiKindLayout2);
        meisaiKind2.setUseClearButton(false);
        meisaiKind2.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "明細書のみ", "一覧表のみ", "明細書と一覧表" }))));
        meisaiKind2.setBindPath("MEISAI_KIND2");
        meisaiKindPrint2.setText("振込先印刷する");
        meisaiKindPrint2.setBindPath("FURIKOMISAKI_PRT2");

        // tab2
        tab2.setLayout(new VRLayout());
        tab2.add(seikyushoCsvPnl, VRLayout.NORTH);
        tab2.add(printOptionGrp, VRLayout.NORTH);
        tab2.add(pointsGrp, VRLayout.NORTH);

        // 請求書印刷 + CSV
        seikyushoCsvPnl.setLayout(new VRLayout());
        seikyushoCsvPnl.add(seikyushoPrintGrp, VRLayout.CLIENT);
        seikyushoCsvPnl.add(csvGrp, VRLayout.CLIENT);
        // 請求書印刷
        seikyushoPrintGrp.setText("請求書印刷(「主治医意見書」印刷時)");
        seikyushoPrintGrp.setForeground(Color.BLUE);
        seikyushoPrintGrp.setLayout(new BorderLayout());
        seikyushoPrintGrp.add(seikyushoPrint);
        seikyushoPrint.setText("印刷する");
        seikyushoPrint.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        // CSVファイルでの提出
        csvGrp.setText("CSVファイルでの「主治医意見書」の提出");
        csvGrp.setForeground(Color.BLUE);
        csvGrp.setLayout(new BorderLayout());
        csvGrp.add(csv);
        csv.setText("提出する");
        csv.setBindPath("FD_OUTPUT_UMU");

        // 主治医意見書」印刷オプション
        printOptionGrp.setText("「主治医意見書」印刷オプション");
        printOptionGrp.setForeground(Color.BLUE);
        printOptionGrp.setLayout(new VRLayout());
        printOptionGrp.add(drNameGrp, VRLayout.CLIENT);
        printOptionGrp.add(pageHeaderPrintGrp, VRLayout.CLIENT);
        printOptionGrp.add(pageHeaderPrintGrp2, VRLayout.CLIENT);
        // 医師氏名
        drNameGrp.setText("医師氏名");
        drNameGrp.setLayout(new BorderLayout());
        drNameGrp.add(drName);
        drName.setText("印刷する");
        drName.setBindPath("DR_NM_OUTPUT_UMU");
        // 1頁目ヘッダ
        pageHeaderPrintGrp.setText("頁ヘッダ(保険者・被保険者番号)");
        pageHeaderPrintGrp.setLayout(new BorderLayout());
        pageHeaderPrintGrp.add(pageHeaderPrint);
        pageHeaderPrint.setText("印刷する");
        pageHeaderPrint.setBindPath("HEADER_OUTPUT_UMU1");
        // 2頁目ヘッダ
        pageHeaderPrintGrp2.setText("2頁目ヘッダ(氏名、年齢、記入日)");
        pageHeaderPrintGrp2.setLayout(new BorderLayout());
        pageHeaderPrintGrp2.add(pageHeaderPrint2);
        pageHeaderPrint2.setText("印刷する");
        pageHeaderPrint2.setBindPath("HEADER_OUTPUT_UMU2");

        // 意見書作成料/診察・検査費用点数
        pointsGrp.setText("意見書作成料/診察・検査費用点数");
        pointsGrp.setForeground(Color.BLUE);
        pointsGrp.setLayout(new VRLayout());
        pointsGrp.add(sakuseiryoShosinryoPnl, VRLayout.NORTH);
        pointsGrp.add(sinsatuPointsGrp, VRLayout.NORTH);
        pointsGrp.add(rollBackPnl, VRLayout.NORTH);

        // 作成料 + 初診料
        sakuseiryoShosinryoPnl.setLayout(new VRLayout());
        sakuseiryoShosinryoPnl.add(ikenshoSakuseiryouGrp, VRLayout.CLIENT);
        sakuseiryoShosinryoPnl.add(shosinGrp, VRLayout.CLIENT);

        // 意見書作成料
        ikenshoSakuseiryouGrp.setText("意見書作成料");
        ikenshoSakuseiryouGrp.setLayout(new VRLayout());
        ikenshoSakuseiryouGrp.add(zaitakuPnl, VRLayout.CLIENT);
        ikenshoSakuseiryouGrp.add(sisetuPnl, VRLayout.CLIENT);

        zaitakuPnl.setLayout(new BorderLayout());
        zaitakuPnl.add(zaitaku, BorderLayout.WEST);
        zaitakuPnl.add(zaitakuTextPnl, BorderLayout.CENTER);

        zaitaku.setText("在宅");
        zaitaku.setVerticalAlignment(JTextField.NORTH);
        zaitakuTextPnl.setLayout(new VRLayout());
        zaitakuTextPnl.add(zaitakuSinkiChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        zaitakuTextPnl.add(zaitakuKeizokuChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        zaitakuSinkiChargeContainer.setText("新規");
        zaitakuSinkiChargeContainer.setLayout(new BorderLayout());
        zaitakuSinkiChargeContainer
                .add(zaitakuSinkiCharge, BorderLayout.CENTER);
        zaitakuSinkiChargeContainer.add(zaitakuSinkiChargeUnit,
                BorderLayout.EAST);
        zaitakuSinkiCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        zaitakuSinkiCharge.setCharType(VRCharType.ONLY_DIGIT);
        zaitakuSinkiCharge.setColumns(5);
        zaitakuSinkiCharge.setMaxLength(5);
        zaitakuSinkiCharge.setHorizontalAlignment(JTextField.RIGHT);
        zaitakuSinkiCharge.setBindPath("ZAITAKU_SINKI_CHARGE");
        zaitakuSinkiChargeUnit.setText("円");
        zaitakuKeizokuChargeContainer.setText("継続");
        zaitakuKeizokuChargeContainer.setLayout(new BorderLayout());
        zaitakuKeizokuChargeContainer.add(zaitakuKeizokuCharge,
                BorderLayout.CENTER);
        zaitakuKeizokuChargeContainer.add(zaitakuKeizokuChargeUnit,
                BorderLayout.EAST);
        zaitakuKeizokuCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        zaitakuKeizokuCharge.setCharType(VRCharType.ONLY_DIGIT);
        zaitakuKeizokuCharge.setColumns(5);
        zaitakuKeizokuCharge.setMaxLength(5);
        zaitakuKeizokuCharge.setHorizontalAlignment(JTextField.RIGHT);
        zaitakuKeizokuCharge.setBindPath("ZAITAKU_KEIZOKU_CHARGE");
        zaitakuKeizokuChargeUnit.setText("円");

        sisetuPnl.setLayout(new BorderLayout());
        sisetuPnl.add(sisetu, BorderLayout.WEST);
        sisetuPnl.add(sisetuTextPnl, BorderLayout.CENTER);
        sisetu.setText("施設");
        sisetu.setVerticalAlignment(JTextField.NORTH);
        sisetuTextPnl.setLayout(new VRLayout());
        sisetuTextPnl.add(sisetuSinkiChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sisetuTextPnl.add(sisetuKeizokuChargeContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sisetuSinkiChargeContainer.setText("新規");
        sisetuSinkiChargeContainer.setLayout(new BorderLayout());
        sisetuSinkiChargeContainer.add(sisetuSinkiCharge, BorderLayout.CENTER);
        sisetuSinkiChargeContainer
                .add(sisetuSinkiChargeUnit, BorderLayout.EAST);
        sisetuSinkiCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        sisetuSinkiCharge.setCharType(VRCharType.ONLY_DIGIT);
        sisetuSinkiCharge.setColumns(5);
        sisetuSinkiCharge.setMaxLength(5);
        sisetuSinkiCharge.setHorizontalAlignment(JTextField.RIGHT);
        sisetuSinkiCharge.setBindPath("SISETU_SINKI_CHARGE");
        sisetuSinkiChargeUnit.setText("円");
        sisetuKeizokuChargeContainer.setText("継続");
        sisetuKeizokuChargeContainer.setLayout(new BorderLayout());
        sisetuKeizokuChargeContainer.add(sisetuKeizokuCharge,
                BorderLayout.CENTER);
        sisetuKeizokuChargeContainer.add(sisetuKeizokuChargeUnit,
                BorderLayout.EAST);
        sisetuKeizokuCharge.setIMEMode(InputSubset.LATIN_DIGITS);
        sisetuKeizokuCharge.setCharType(VRCharType.ONLY_DIGIT);
        sisetuKeizokuCharge.setColumns(5);
        sisetuKeizokuCharge.setMaxLength(5);
        sisetuKeizokuCharge.setHorizontalAlignment(JTextField.RIGHT);
        sisetuKeizokuCharge.setBindPath("SISETU_KEIZOKU_CHARGE");
        sisetuKeizokuChargeUnit.setText("円");

        // 診察費用点数
        shosinGrp.setText("診察費用点数 - 小数点第一位まで");
        shosinGrp.setLayout(new BorderLayout());
        shosinGrp.add(shosinPnl, BorderLayout.WEST);
        shosinGrp.add(shosinTextPnl, BorderLayout.CENTER);
        shosinPnl.setLayout(new BorderLayout());
        shosinPnl.add(shosin);
        shosin.setText("初診料");
        shosin.setVerticalAlignment(JTextField.NORTH);
        shosinTextPnl.setLayout(new VRLayout());
        shosinTextPnl.add(shosinSinryoujoContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        shosinTextPnl.add(shosinHospitalContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        shosinSinryoujoContainer.setText("診療所");
        shosinSinryoujoContainer.setLayout(new BorderLayout());
        shosinSinryoujoContainer.add(shosinSinryoujo, BorderLayout.CENTER);
        shosinSinryoujoContainer.add(shosinSinryoujoUnit, BorderLayout.EAST);
        shosinSinryoujo.setIMEMode(InputSubset.LATIN_DIGITS);
        shosinSinryoujo.setFormat(new ACOneDecimalDoubleFormat());
        shosinSinryoujo.setCharType(new VRCharType("DOUBLE1",
                DOUBLE_INPUT_PERSER));
        shosinSinryoujo.setColumns(6);
        shosinSinryoujo.setMaxLength(6);
        shosinSinryoujo.setHorizontalAlignment(JTextField.RIGHT);
        shosinSinryoujo.setBindPath("SHOSIN_SINRYOUJO");
        shosinSinryoujoUnit.setText("点");
        shosinHospitalContainer.setText("病院");
        shosinHospitalContainer.setLayout(new BorderLayout());
        shosinHospitalContainer.add(shosinHospital, BorderLayout.CENTER);
        shosinHospitalContainer.add(shosinHospitalUnit, BorderLayout.EAST);
        shosinHospital.setIMEMode(InputSubset.LATIN_DIGITS);
        shosinHospital.setFormat(new ACOneDecimalDoubleFormat());
        shosinHospital.setCharType(new VRCharType("SHOUSUU1",
                DOUBLE_INPUT_PERSER));
        shosinHospital.setColumns(6);
        shosinHospital.setMaxLength(6);
        shosinHospital.setHorizontalAlignment(JTextField.RIGHT);
        shosinHospital.setBindPath("SHOSIN_HOSPITAL");
        shosinHospitalUnit.setText("点");

        // 診察・検査費用点数
        sinsatuPointsGrp.setText("診察・検査費用点数 - 小数点第一位まで");
        sinsatuPointsGrp.setLayout(new VRLayout());
        sinsatuPointsGrp.add(sinsatuPointsLeftPnl, VRLayout.CLIENT);
        sinsatuPointsGrp.add(sinsatuPointsRightPnl, VRLayout.CLIENT);

        VRLayout sinsatuPointsLeftPnlLayout = new VRLayout();
        sinsatuPointsLeftPnlLayout.setHgrid(100);
        sinsatuPointsLeftPnl.setLayout(sinsatuPointsLeftPnlLayout);
        sinsatuPointsLeftPnl
                .add(expKsContainer, VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKikMkiContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKikKekkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKkkKkkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsLeftPnl.add(expKkkSkkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);

        expKsContainer.setText("血液採取(静脈)");
        expKsContainer.setLayout(new BorderLayout());
        expKsContainer.add(expKs, BorderLayout.CENTER);
        expKsContainer.add(expKsUnit, BorderLayout.EAST);
        expKs.setIMEMode(InputSubset.LATIN_DIGITS);
        expKs.setFormat(new ACOneDecimalDoubleFormat());
        expKs.setCharType(new VRCharType("SHOUSUU1", DOUBLE_INPUT_PERSER));
        expKs.setColumns(6);
        expKs.setMaxLength(6);
        expKs.setHorizontalAlignment(JTextField.RIGHT);
        expKs.setBindPath("EXP_KS");
        expKsUnit.setText("点");
        expKikMkiContainer.setText("末梢血液一般検査");
        expKikMkiContainer.setLayout(new BorderLayout());
        expKikMkiContainer.add(expKikMki, BorderLayout.CENTER);
        expKikMkiContainer.add(expKikMkiUnit, BorderLayout.EAST);
        expKikMki.setIMEMode(InputSubset.LATIN_DIGITS);
        expKikMki.setFormat(new ACOneDecimalDoubleFormat());
        expKikMki.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKikMki.setColumns(6);
        expKikMki.setMaxLength(6);
        expKikMki.setHorizontalAlignment(JTextField.RIGHT);
        expKikMki.setBindPath("EXP_KIK_MKI");
        expKikMkiUnit.setText("点");
        expKikKekkContainer.setText("血液学的検査判断料");
        expKikKekkContainer.setLayout(new BorderLayout());
        expKikKekkContainer.add(expKikKekk, BorderLayout.CENTER);
        expKikKekkContainer.add(expKikKekkUnit, BorderLayout.EAST);
        expKikKekk.setIMEMode(InputSubset.LATIN_DIGITS);
        expKikKekk.setFormat(new ACOneDecimalDoubleFormat());
        expKikKekk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKikKekk.setColumns(6);
        expKikKekk.setMaxLength(6);
        expKikKekk.setHorizontalAlignment(JTextField.RIGHT);
        expKikKekk.setBindPath("EXP_KIK_KEKK");
        expKikKekkUnit.setText("点");
        expKkkKkkContainer.setText("血液化学検査(10項目以上)");
        expKkkKkkContainer.setLayout(new BorderLayout());
        expKkkKkkContainer.add(expKkkKkk, BorderLayout.CENTER);
        expKkkKkkContainer.add(expKkkKkkUnit, BorderLayout.EAST);
        expKkkKkk.setIMEMode(InputSubset.LATIN_DIGITS);
        expKkkKkk.setFormat(new ACOneDecimalDoubleFormat());
        expKkkKkk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKkkKkk.setColumns(6);
        expKkkKkk.setMaxLength(6);
        expKkkKkk.setHorizontalAlignment(JTextField.RIGHT);
        expKkkKkk.setBindPath("EXP_KKK_KKK");
        expKkkKkkUnit.setText("点");
        expKkkSkkContainer.setText("生化学的検査(I)判断料");
        expKkkSkkContainer.setLayout(new BorderLayout());
        expKkkSkkContainer.add(expKkkSkk, BorderLayout.CENTER);
        expKkkSkkContainer.add(expKkkSkkUnit, BorderLayout.EAST);
        expKkkSkk.setIMEMode(InputSubset.LATIN_DIGITS);
        expKkkSkk.setFormat(new ACOneDecimalDoubleFormat());
        expKkkSkk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expKkkSkk.setColumns(6);
        expKkkSkk.setMaxLength(6);
        expKkkSkk.setHorizontalAlignment(JTextField.RIGHT);
        expKkkSkk.setBindPath("EXP_KKK_SKK");
        expKkkSkkUnit.setText("点");

        VRLayout sinsatuPointsRightPnlLayout = new VRLayout();
        sinsatuPointsRightPnlLayout.setHgrid(200);
        sinsatuPointsRightPnl.setLayout(sinsatuPointsRightPnlLayout);
        sinsatuPointsRightPnl.add(expNitkContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRightPnl.add(expXrayTsContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRightPnl.add(expXraySsContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        sinsatuPointsRightPnl.add(expXrayFilmContainer,
                VRLayout.FLOW_INSETLINE_RETURN);
        expNitkContainer.setText("尿中一般物質定性半定量検査");
        expNitkContainer.setLayout(new BorderLayout());
        expNitkContainer.add(expNitk, BorderLayout.CENTER);
        expNitkContainer.add(expNitkUnit, BorderLayout.EAST);
        expNitk.setIMEMode(InputSubset.LATIN_DIGITS);
        expNitk.setFormat(new ACOneDecimalDoubleFormat());
        expNitk.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expNitk.setColumns(6);
        expNitk.setMaxLength(6);
        expNitk.setHorizontalAlignment(JTextField.RIGHT);
        expNitk.setBindPath("EXP_NITK");
        expNitkUnit.setText("点");
        expXrayTsContainer.setText("単純撮影");
        expXrayTsContainer.setLayout(new BorderLayout());
        expXrayTsContainer.add(expXrayTs, BorderLayout.CENTER);
        expXrayTsContainer.add(expXrayTsUnit, BorderLayout.EAST);
        expXrayTs.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayTs.setFormat(new ACOneDecimalDoubleFormat());
        expXrayTs.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayTs.setColumns(6);
        expXrayTs.setMaxLength(6);
        expXrayTs.setHorizontalAlignment(JTextField.RIGHT);
        expXrayTs.setBindPath("EXP_XRAY_TS");
        expXrayTsUnit.setText("点");
        expXraySsContainer.setText("写真診断(胸部)");
        expXraySsContainer.setLayout(new BorderLayout());
        expXraySsContainer.add(expXraySs, BorderLayout.CENTER);
        expXraySsContainer.add(expXraySsUnit, BorderLayout.EAST);
        expXraySs.setIMEMode(InputSubset.LATIN_DIGITS);
        expXraySs.setFormat(new ACOneDecimalDoubleFormat());
        expXraySs.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXraySs.setColumns(6);
        expXraySs.setMaxLength(6);
        expXraySs.setHorizontalAlignment(JTextField.RIGHT);
        expXraySs.setBindPath("EXP_XRAY_SS");
        expXraySsUnit.setText("点");
        expXrayFilmContainer.setText("フィルム(大角)");
        expXrayFilmContainer.setLayout(new BorderLayout());
        expXrayFilmContainer.add(expXrayFilm, BorderLayout.CENTER);
        expXrayFilmContainer.add(expXrayFilmUnit, BorderLayout.EAST);
        expXrayFilm.setIMEMode(InputSubset.LATIN_DIGITS);
        expXrayFilm.setFormat(new ACOneDecimalDoubleFormat());
        expXrayFilm.setCharType(new VRCharType("DOUBLE1", DOUBLE_INPUT_PERSER));
        expXrayFilm.setColumns(6);
        expXrayFilm.setMaxLength(6);
        expXrayFilm.setHorizontalAlignment(JTextField.RIGHT);
        expXrayFilm.setBindPath("EXP_XRAY_FILM");
        expXrayFilmUnit.setText("点");

        // 戻すボタン
        rollBackPnl.setLayout(new VRLayout());
        rollBackPnl.add(rollBack, VRLayout.EAST);
        insurerNmContainer.add(insurerNmField, java.awt.BorderLayout.CENTER);
        rollBack.setText("厚労省提示額に戻す(D)");
        rollBack.setMnemonic('D');
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        // スナップショット対象設定
        IkenshoSnapshot.getInstance().setRootContainer(tabPnl);

        // メニューバーのボタンに対応するトリガーの設定
        addUpdateTrigger(update);

        // ステータスバー
        setStatusText("保険者詳細");

        // 前画面から引継ぎのデータを取得・設定
        VRMap params = affair.getParameters();
        if (params.size() > 0) {
            if (VRBindPathParser.has("PREV_DATA", params)) {
                prevData = (VRMap) VRBindPathParser.get("PREV_DATA", params);
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

            // 渡りデータの有無で分岐
            if (hasData) {
                // 渡り時の情報郡からINSURER_NOを取得
                insurerNo = String.valueOf(VRBindPathParser.get("INSURER_NO",
                        params));

                // 入力欄にデータを設定
                doSelect();

                // 意見書作成料・検査料(1枚)の場合、下段は消去
                if (seikyuPattern.getSelectedIndex() == 1) {
                    sksInsurerNm.setText("");
                    sksInsurerNo.setText("");
                    soukatuhyou2.setSelectedIndex(0);
                    soukatuhyouPrint2.setSelected(false);
                    meisaiKind2.setSelectedIndex(0);
                    meisaiKindPrint2.setSelected(false);
                }
            } else {
                // 入力欄にデータ(空白)を設定
                doSelect();

                // 保険者情報2のCheckBoxの設定
                seikyushoPrint.setSelected(false);
                csv.setSelected(false);
                drName.setSelected(false);
                pageHeaderPrint.setSelected(true);
                pageHeaderPrint2.setSelected(true);

                // 点数の初期値を取得・設定
                loadMKingakuTensu();
            }

            // 請求パターン内のEnabledの設定
            setseikyuPatternEnabled();

            // スナップショット撮影
            IkenshoSnapshot.getInstance().snapshot();
        } else {
            throw new Exception("不正なパラメータが渡されました。");
        }
    }

    private void event() throws Exception {
        // 厚労省提示額に戻す
        rollBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int result = ACMessageBox.show(
                        "意見書作成料／診察・検査費用点数に\n厚労省提示額を設定します。よろしいですか？",
                        ACMessageBox.BUTTON_OKCANCEL,
                        ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK);
                if (result == ACMessageBox.RESULT_OK) {
                    try {
                        loadMKingakuTensu();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        // 「依頼元と同一」(意見書作成料)
        issInsurerSame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                issInsurerNm.setText(insurerNmField.getText());
                issInsurerNo.setText(insurerNoField.getText());
            }
        });

        // 「依頼元と同一」(診察・検査費用)
        sksInsurerSame.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sksInsurerNm.setText(insurerNmField.getText());
                sksInsurerNo.setText(insurerNoField.getText());
            }
        });

        // 請求パターン
        seikyuPattern.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setseikyuPatternEnabled();
            }
        });

        // 総括票(意見書作成料)
        soukatuhyou.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSoukatuhyouValue(soukatuhyou, soukatuhyouPrint);
            }
        });

        // 総括票(診察・検査費用)
        soukatuhyou2.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                setSoukatuhyouValue(soukatuhyou2, soukatuhyouPrint2);
            }
        });
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return insurerNmField;
    }

    public boolean canBack(VRMap parameters) throws Exception {
        String key = "INSURER_NO";
        String value = insurerNo;

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
                boolean updateFlg = doUpdate(); // DB更新成功:true, 失敗:false
                parameters.put(key, insurerNo);
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
            msg = "更新された内容は破棄されます。\n終了してもよろしいですか？";
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
     * M_KINGAKU_TENSUテーブルから値を取得する
     * 
     * @throws Exception
     */
    private void loadMKingakuTensu() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" ZAITAKU_SINKI_CHARGE,");
        sb.append(" ZAITAKU_KEIZOKU_CHARGE,");
        sb.append(" SISETU_SINKI_CHARGE,");
        sb.append(" SISETU_KEIZOKU_CHARGE,");
        sb.append(" SHOSIN_SINRYOUJO,");
        sb.append(" SHOSIN_HOSPITAL,");
        sb.append(" EXP_KS,");
        sb.append(" EXP_KIK_MKI,");
        sb.append(" EXP_KIK_KEKK,");
        sb.append(" EXP_KKK_KKK,");
        sb.append(" EXP_KKK_SKK,");
        sb.append(" EXP_NITK,");
        sb.append(" EXP_XRAY_TS,");
        sb.append(" EXP_XRAY_SS,");
        sb.append(" EXP_XRAY_FILM,");
        sb.append(" LAST_TIME");
        sb.append(" FROM");
        sb.append(" M_KINGAKU_TENSU");
        VRArrayList kingakuTensuArray = (VRArrayList) dbm.executeQuery(sb
                .toString());

        VRMap kingakuTensuData;
        if (kingakuTensuArray.getDataSize() > 0) { // DB上にデータ有
            kingakuTensuData = (VRMap) kingakuTensuArray.getData();
        } else {
            kingakuTensuData = (VRMap) pointsGrp.createSource(); // DB上にデータ無
        }
        pointsGrp.setSource(kingakuTensuData);
        pointsGrp.bindSource();
        pointsGrp.setSource(insurerData);
    }

    /**
     * DBからデータを取得します。
     * 
     * @throws Exception
     */
    private void doSelect() throws Exception {
        VRArrayList insurerArray = null;

        // キーを元に、DBからデータを取得
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" INSURER_NO,");
        sb.append(" INSURER_NM,");
        sb.append(" FD_OUTPUT_UMU,");
        sb.append(" SEIKYUSHO_HAKKOU_PATTERN,");
        sb.append(" SEIKYUSHO_OUTPUT_PATTERN,");
        sb.append(" DR_NM_OUTPUT_UMU,");
        sb.append(" HEADER_OUTPUT_UMU1,");
        sb.append(" HEADER_OUTPUT_UMU2,");
        sb.append(" ISS_INSURER_NO,");
        sb.append(" ISS_INSURER_NM,");
        sb.append(" SKS_INSURER_NO,");
        sb.append(" SKS_INSURER_NM,");
        sb.append(" ZAITAKU_SINKI_CHARGE,");
        sb.append(" ZAITAKU_KEIZOKU_CHARGE,");
        sb.append(" SISETU_SINKI_CHARGE,");
        sb.append(" SISETU_KEIZOKU_CHARGE,");
        sb.append(" SHOSIN_SINRYOUJO,");
        sb.append(" SHOSIN_HOSPITAL,");
        sb.append(" EXP_KS,");
        sb.append(" EXP_KIK_MKI,");
        sb.append(" EXP_KIK_KEKK,");
        sb.append(" EXP_KKK_KKK,");
        sb.append(" EXP_KKK_SKK,");
        sb.append(" EXP_NITK,");
        sb.append(" EXP_XRAY_TS,");
        sb.append(" EXP_XRAY_SS,");
        sb.append(" EXP_XRAY_FILM,");
        sb.append(" SOUKATUHYOU_PRT,");
        sb.append(" MEISAI_KIND,");
        sb.append(" FURIKOMISAKI_PRT,");
        sb.append(" SOUKATU_FURIKOMI_PRT,");
        sb.append(" SOUKATUHYOU_PRT2,");
        sb.append(" MEISAI_KIND2,");
        sb.append(" FURIKOMISAKI_PRT2,");
        sb.append(" SOUKATU_FURIKOMI_PRT2,");
        sb.append(" LAST_TIME");
        sb.append(" FROM");
        sb.append(" INSURER");
        sb.append(" WHERE");
        sb.append(" INSURER_NO='" + insurerNo + "'");
        sb.append(" ORDER BY");
        sb.append(" INSURER_NO");
        insurerArray = (VRArrayList) dbm.executeQuery(sb.toString());

        if (insurerArray.getDataSize() > 0) { // DB上にデータ有
            insurerData = (VRMap) insurerArray.getData();
            insurerNm = insurerData.getData("INSURER_NM").toString(); // 保険者名を退避
            // パッシブチェック用
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, insurerArray);
        } else {
            insurerData = (VRMap) tabPnl.createSource(); // DB上にデータ無
            hasData = false;
        }

        tabPnl.setSource(insurerData);
        tabPnl.bindSource();
    }

    /**
     * DBを更新します。
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean doUpdate() throws Exception {
        // 入力チェック
        if (isValidInput()) {
            String msg = "";

            // update
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            try {
                // 入力データを取得
                tabPnl.applySource();
                String INSURER_NO = getDBSafeString("INSURER_NO", insurerData);
                String INSURER_NM = getDBSafeString("INSURER_NM", insurerData);
                String FD_OUTPUT_UMU = getDBSafeNumber("FD_OUTPUT_UMU",
                        insurerData);
                String SEIKYUSHO_HAKKOU_PATTERN = getDBSafeNumber(
                        "SEIKYUSHO_HAKKOU_PATTERN", insurerData);
                String SEIKYUSHO_OUTPUT_PATTERN = getDBSafeNumber(
                        "SEIKYUSHO_OUTPUT_PATTERN", insurerData);
                String DR_NM_OUTPUT_UMU = getDBSafeNumber("DR_NM_OUTPUT_UMU",
                        insurerData);
                String HEADER_OUTPUT_UMU1 = getDBSafeNumber(
                        "HEADER_OUTPUT_UMU1", insurerData);
                String HEADER_OUTPUT_UMU2 = getDBSafeNumber(
                        "HEADER_OUTPUT_UMU2", insurerData);

                String ISS_INSURER_NO = "''";
                String ISS_INSURER_NM = "''";
                String SOUKATUHYOU_PRT = "0";
                String MEISAI_KIND = "0";
                String FURIKOMISAKI_PRT = "0";
                String SOUKATU_FURIKOMI_PRT = "0";
                if (issGrp.isEnabled()) {
                    ISS_INSURER_NO = getDBSafeString("ISS_INSURER_NO",
                            insurerData);
                    ISS_INSURER_NM = getDBSafeString("ISS_INSURER_NM",
                            insurerData);
                    SOUKATUHYOU_PRT = getDBSafeNumber("SOUKATUHYOU_PRT",
                            insurerData);
                    MEISAI_KIND = getDBSafeNumber("MEISAI_KIND", insurerData);
                    FURIKOMISAKI_PRT = getDBSafeNumber("FURIKOMISAKI_PRT",
                            insurerData);
                    if (soukatuhyouPrint.isEnabled()) {
                        SOUKATU_FURIKOMI_PRT = getDBSafeNumber(
                                "SOUKATU_FURIKOMI_PRT", insurerData);
                    }
                }

                String SKS_INSURER_NO = "''";
                String SKS_INSURER_NM = "''";
                String SOUKATUHYOU_PRT2 = "0";
                String MEISAI_KIND2 = "0";
                String FURIKOMISAKI_PRT2 = "0";
                String SOUKATU_FURIKOMI_PRT2 = "0";
                if (sksGrp.isEnabled()) {
                    SKS_INSURER_NO = getDBSafeString("SKS_INSURER_NO",
                            insurerData);
                    SKS_INSURER_NM = getDBSafeString("SKS_INSURER_NM",
                            insurerData);
                    SOUKATUHYOU_PRT2 = getDBSafeNumber("SOUKATUHYOU_PRT2",
                            insurerData);
                    MEISAI_KIND2 = getDBSafeNumber("MEISAI_KIND2", insurerData);
                    FURIKOMISAKI_PRT2 = getDBSafeNumber("FURIKOMISAKI_PRT2",
                            insurerData);
                    if (soukatuhyouPrint2.isEnabled()) {
                        SOUKATU_FURIKOMI_PRT2 = getDBSafeNumber(
                                "SOUKATU_FURIKOMI_PRT2", insurerData);
                    }
                } else if (seikyuPattern.getSelectedIndex() == 1) {
                    SKS_INSURER_NO = ISS_INSURER_NO;
                    SKS_INSURER_NM = ISS_INSURER_NM;
                    SOUKATUHYOU_PRT2 = SOUKATUHYOU_PRT;
                    MEISAI_KIND2 = MEISAI_KIND;
                    FURIKOMISAKI_PRT2 = FURIKOMISAKI_PRT;
                    if (soukatuhyouPrint.isEnabled()) {
                        SOUKATU_FURIKOMI_PRT2 = SOUKATU_FURIKOMI_PRT;
                    }
                }

                String ZAITAKU_SINKI_CHARGE = getDBSafeNumber(
                        "ZAITAKU_SINKI_CHARGE", insurerData);
                String ZAITAKU_KEIZOKU_CHARGE = getDBSafeNumber(
                        "ZAITAKU_KEIZOKU_CHARGE", insurerData);
                String SISETU_SINKI_CHARGE = getDBSafeNumber(
                        "SISETU_SINKI_CHARGE", insurerData);
                String SISETU_KEIZOKU_CHARGE = getDBSafeNumber(
                        "SISETU_KEIZOKU_CHARGE", insurerData);
                String SHOSIN_SINRYOUJO = getDBSafeNumber("SHOSIN_SINRYOUJO",
                        insurerData);
                String SHOSIN_HOSPITAL = getDBSafeNumber("SHOSIN_HOSPITAL",
                        insurerData);
                String EXP_KS = getDBSafeNumber("EXP_KS", insurerData);
                String EXP_KIK_MKI = getDBSafeNumber("EXP_KIK_MKI", insurerData);
                String EXP_KIK_KEKK = getDBSafeNumber("EXP_KIK_KEKK",
                        insurerData);
                String EXP_KKK_KKK = getDBSafeNumber("EXP_KKK_KKK", insurerData);
                String EXP_KKK_SKK = getDBSafeNumber("EXP_KKK_SKK", insurerData);
                String EXP_NITK = getDBSafeNumber("EXP_NITK", insurerData);
                String EXP_XRAY_TS = getDBSafeNumber("EXP_XRAY_TS", insurerData);
                String EXP_XRAY_SS = getDBSafeNumber("EXP_XRAY_SS", insurerData);
                String EXP_XRAY_FILM = getDBSafeNumber("EXP_XRAY_FILM",
                        insurerData);

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
                    msg = "更新されました。";
                    sb.append(" UPDATE");
                    sb.append(" INSURER");
                    sb.append(" SET");
                    sb.append(" INSURER_NO=" + INSURER_NO);
                    sb.append(",INSURER_NM=" + INSURER_NM);
                    sb.append(",FD_OUTPUT_UMU=" + FD_OUTPUT_UMU);
                    sb.append(",SEIKYUSHO_HAKKOU_PATTERN="
                            + SEIKYUSHO_HAKKOU_PATTERN);
                    sb.append(",SEIKYUSHO_OUTPUT_PATTERN="
                            + SEIKYUSHO_OUTPUT_PATTERN);
                    sb.append(",DR_NM_OUTPUT_UMU=" + DR_NM_OUTPUT_UMU);
                    sb.append(",HEADER_OUTPUT_UMU1=" + HEADER_OUTPUT_UMU1);
                    sb.append(",HEADER_OUTPUT_UMU2=" + HEADER_OUTPUT_UMU2);
                    sb.append(",ISS_INSURER_NO=" + ISS_INSURER_NO);
                    sb.append(",ISS_INSURER_NM=" + ISS_INSURER_NM);
                    sb.append(",SKS_INSURER_NO=" + SKS_INSURER_NO);
                    sb.append(",SKS_INSURER_NM=" + SKS_INSURER_NM);
                    sb.append(",ZAITAKU_SINKI_CHARGE=" + ZAITAKU_SINKI_CHARGE);
                    sb.append(",ZAITAKU_KEIZOKU_CHARGE="
                            + ZAITAKU_KEIZOKU_CHARGE);
                    sb.append(",SISETU_SINKI_CHARGE=" + SISETU_SINKI_CHARGE);
                    sb
                            .append(",SISETU_KEIZOKU_CHARGE="
                                    + SISETU_KEIZOKU_CHARGE);
                    sb.append(",SHOSIN_SINRYOUJO=" + SHOSIN_SINRYOUJO);
                    sb.append(",SHOSIN_HOSPITAL=" + SHOSIN_HOSPITAL);
                    sb.append(",EXP_KS=" + EXP_KS);
                    sb.append(",EXP_KIK_MKI=" + EXP_KIK_MKI);
                    sb.append(",EXP_KIK_KEKK=" + EXP_KIK_KEKK);
                    sb.append(",EXP_KKK_KKK=" + EXP_KKK_KKK);
                    sb.append(",EXP_KKK_SKK=" + EXP_KKK_SKK);
                    sb.append(",EXP_NITK=" + EXP_NITK);
                    sb.append(",EXP_XRAY_TS=" + EXP_XRAY_TS);
                    sb.append(",EXP_XRAY_SS=" + EXP_XRAY_SS);
                    sb.append(",EXP_XRAY_FILM=" + EXP_XRAY_FILM);
                    sb.append(",SOUKATUHYOU_PRT=" + SOUKATUHYOU_PRT);
                    sb.append(",MEISAI_KIND=" + MEISAI_KIND);
                    sb.append(",FURIKOMISAKI_PRT=" + FURIKOMISAKI_PRT);
                    sb.append(",SOUKATU_FURIKOMI_PRT=" + SOUKATU_FURIKOMI_PRT);
                    sb.append(",SOUKATUHYOU_PRT2=" + SOUKATUHYOU_PRT2);
                    sb.append(",MEISAI_KIND2=" + MEISAI_KIND2);
                    sb.append(",FURIKOMISAKI_PRT2=" + FURIKOMISAKI_PRT2);
                    sb
                            .append(",SOUKATU_FURIKOMI_PRT2="
                                    + SOUKATU_FURIKOMI_PRT2);
                    sb.append(",LAST_TIME=CURRENT_TIMESTAMP");
                    sb.append(" WHERE");
                    sb.append(" INSURER_NO='" + insurerNo + "'");
                } else {
                    // 追加時
                    msg = "登録されました。";
                    sb.append("INSERT INTO");
                    sb.append(" INSURER");
                    sb.append(" (");
                    sb.append(" INSURER_NO,");
                    sb.append(" INSURER_NM,");
                    sb.append(" FD_OUTPUT_UMU,");
                    sb.append(" SEIKYUSHO_HAKKOU_PATTERN,");
                    sb.append(" SEIKYUSHO_OUTPUT_PATTERN,");
                    sb.append(" DR_NM_OUTPUT_UMU,");
                    sb.append(" HEADER_OUTPUT_UMU1,");
                    sb.append(" HEADER_OUTPUT_UMU2,");
                    sb.append(" ISS_INSURER_NO,");
                    sb.append(" ISS_INSURER_NM,");
                    sb.append(" SKS_INSURER_NO,");
                    sb.append(" SKS_INSURER_NM,");
                    sb.append(" ZAITAKU_SINKI_CHARGE,");
                    sb.append(" ZAITAKU_KEIZOKU_CHARGE,");
                    sb.append(" SISETU_SINKI_CHARGE,");
                    sb.append(" SISETU_KEIZOKU_CHARGE,");
                    sb.append(" SHOSIN_SINRYOUJO,");
                    sb.append(" SHOSIN_HOSPITAL,");
                    sb.append(" EXP_KS,");
                    sb.append(" EXP_KIK_MKI,");
                    sb.append(" EXP_KIK_KEKK,");
                    sb.append(" EXP_KKK_KKK,");
                    sb.append(" EXP_KKK_SKK,");
                    sb.append(" EXP_NITK,");
                    sb.append(" EXP_XRAY_TS,");
                    sb.append(" EXP_XRAY_SS,");
                    sb.append(" EXP_XRAY_FILM,");
                    sb.append(" SOUKATUHYOU_PRT,");
                    sb.append(" MEISAI_KIND,");
                    sb.append(" FURIKOMISAKI_PRT,");
                    sb.append(" SOUKATU_FURIKOMI_PRT,");
                    sb.append(" SOUKATUHYOU_PRT2,");
                    sb.append(" MEISAI_KIND2,");
                    sb.append(" FURIKOMISAKI_PRT2,");
                    sb.append(" SOUKATU_FURIKOMI_PRT2,");
                    sb.append(" LAST_TIME");
                    sb.append(" )");
                    sb.append(" VALUES(");
                    sb.append(" " + INSURER_NO);
                    sb.append("," + INSURER_NM);
                    sb.append("," + FD_OUTPUT_UMU);
                    sb.append("," + SEIKYUSHO_HAKKOU_PATTERN);
                    sb.append("," + SEIKYUSHO_OUTPUT_PATTERN);
                    sb.append("," + DR_NM_OUTPUT_UMU);
                    sb.append("," + HEADER_OUTPUT_UMU1);
                    sb.append("," + HEADER_OUTPUT_UMU2);
                    sb.append("," + ISS_INSURER_NO);
                    sb.append("," + ISS_INSURER_NM);
                    sb.append("," + SKS_INSURER_NO);
                    sb.append("," + SKS_INSURER_NM);
                    sb.append("," + ZAITAKU_SINKI_CHARGE);
                    sb.append("," + ZAITAKU_KEIZOKU_CHARGE);
                    sb.append("," + SISETU_SINKI_CHARGE);
                    sb.append("," + SISETU_KEIZOKU_CHARGE);
                    sb.append("," + SHOSIN_SINRYOUJO);
                    sb.append("," + SHOSIN_HOSPITAL);
                    sb.append("," + EXP_KS);
                    sb.append("," + EXP_KIK_MKI);
                    sb.append("," + EXP_KIK_KEKK);
                    sb.append("," + EXP_KKK_KKK);
                    sb.append("," + EXP_KKK_SKK);
                    sb.append("," + EXP_NITK);
                    sb.append("," + EXP_XRAY_TS);
                    sb.append("," + EXP_XRAY_SS);
                    sb.append("," + EXP_XRAY_FILM);
                    sb.append("," + SOUKATUHYOU_PRT);
                    sb.append("," + MEISAI_KIND);
                    sb.append("," + FURIKOMISAKI_PRT);
                    sb.append("," + SOUKATU_FURIKOMI_PRT);
                    sb.append("," + SOUKATUHYOU_PRT2);
                    sb.append("," + MEISAI_KIND2);
                    sb.append("," + FURIKOMISAKI_PRT2);
                    sb.append("," + SOUKATU_FURIKOMI_PRT2);
                    sb.append(",CURRENT_TIMESTAMP");
                    sb.append(" )");
                }

                // 更新用SQL実行
                dbm.executeUpdate(sb.toString());

                // 後処理(「新規」、及び「複製」モードで登録したら、「更新」モードへ移行する)
                if (!isUpdate) {
                    isUpdate = true;
                    hasData = true;
                    update.setText("更新(S)");
                    update.setToolTipText("現在の内容を更新します。");
                }

                // コミット
                dbm.commitTransaction();

                // insurerNoを更新
                insurerNo = INSURER_NO.replaceAll("'", "");
            } catch (Exception ex) {
                // ロールバック
                dbm.rollbackTransaction();
                throw new Exception(ex);
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
     * 入力チェック制御
     * 
     * @return boolean
     * @throws Exception
     */
    private boolean isValidInput() throws Exception {
        boolean valid = false;

        // エラーチェック
        valid = checkErr();

        if (valid) {
            valid = checkWarning();
        }

        return valid;
    }

    /**
     * エラーメッセージを表示します。
     * 
     * @param tabIndex int
     * @param tgt メッセージ表示後にフォーカスを当てるコンポーネント
     * @param msg エラーメッセージ
     */
    private void showErrMessageBox(int tabIndex, Component tgt, String msg) {
        ACMessageBox.show(msg, ACMessageBox.BUTTON_OK,
                ACMessageBox.ICON_EXCLAMATION);
        if (tgt != null) {
            if (tabIndex >= 0) {
                if (tabIndex < tab.getTabCount()) {
                    tab.setSelectedIndex(tabIndex);
                }
            }
            tgt.requestFocus();
        }
    }

    /**
     * 警告メッセージを表示します。
     * 
     * @param msg エラーメッセージ
     * @return true:OK, false:CANCEL
     */
    private boolean showWarningMessageBox(String msg) {
        int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK);
        if (result == ACMessageBox.RESULT_OK) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * エラーチェック
     * 
     * @return valid / true:有効(OK), false:無効(ERR)
     * @throws Exception
     */
    private boolean checkErr() throws Exception {
        boolean doCheckFlg = false;

        // 保険者名存在チェック(未入力チェック)
        if (isNullText(insurerNmField.getText())) {
            showErrMessageBox(0, insurerNmField, "保険者名称を入力してください。");
            return false;
        }

        // 次のチェックは、「更新」「保険者番号変更なし」以外のケースで行う
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNoField.getText().equals(insurerNo)) {
                doCheckFlg = false; // 更新・変更なしの場合は重複チェックは行わない
            }
        }
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm;
            StringBuffer sb;
            VRArrayList tmpArray;
            VRMap tmpHashMap;
            int cntInsurerNo;

            // 意見書固有情報(IKN_ORIGIN)で使用されている番号かチェック
            dbm = new IkenshoFirebirdDBManager();
            sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_NO");
            sb.append(" FROM");
            sb.append(" IKN_ORIGIN");
            sb.append(" WHERE");
            sb.append(" INSURER_NO = '" + insurerNo + "'");
            tmpArray = (VRArrayList) dbm.executeQuery(sb.toString());
            tmpHashMap = (VRMap) tmpArray.getData();
            cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NO")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNoField,
                        "意見書に使用されている保険者番号は変更できません。");
                return false;
            }
        }

        // 次のチェックは、「更新」「保険者番号変更なし」以外のケースで行う
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNoField.getText().equals(insurerNo)) {
                doCheckFlg = false; // 更新・変更なしの場合は重複チェックは行わない
            }
        }
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm;
            StringBuffer sb;
            VRArrayList tmpArray;
            VRMap tmpHashMap;
            int cntInsurerNo;

            // 事業者テーブル(JIGYOUSHA)で使用されている番号かチェック
            dbm = new IkenshoFirebirdDBManager();
            sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_NO");
            sb.append(" FROM");
            sb.append(" JIGYOUSHA");
            sb.append(" WHERE");
            sb.append(" INSURER_NO = '" + insurerNo + "'");
            tmpArray = (VRArrayList) dbm.executeQuery(sb.toString());
            tmpHashMap = (VRMap) tmpArray.getData();
            cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NO")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNoField,
                        "事業者マスタに使用されている保険者番号は変更できません。");
                return false;
            }
        }

        // 次のチェックは、「更新」「保険者名変更なし」以外のケースで行う
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNmField.getText().equals(insurerNm)) {
                doCheckFlg = false; // 更新、かつ変更なしの場合は重複チェックは行わない
            }
        }
        // 保険者名重複チェック
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm;
            StringBuffer sb = new StringBuffer();
            VRArrayList tmpArray;
            VRMap tmpHashMap;
            int cntInsurerNo;

            dbm = new IkenshoFirebirdDBManager();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NM) AS CNT_INSURER_NM");
            sb.append(" FROM");
            sb.append(" INSURER");
            sb.append(" WHERE");
            sb.append(" INSURER_NM = '" + insurerNmField.getText() + "'");
            tmpArray = (VRArrayList) dbm.executeQuery(sb.toString());
            tmpHashMap = (VRMap) tmpArray.getData();
            cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NM")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNmField, "保険者名が重複しています。");
                return false;
            }
        }

        // 次のチェックは、「更新」「保険者番号変更なし」以外のケースで行う
        doCheckFlg = true;
        if (isUpdate) {
            if (insurerNoField.getText().equals(insurerNo)) {
                doCheckFlg = false; // 更新・変更なしの場合は重複チェックは行わない
            }
        }
        // 保険者番号重複チェック
        if (doCheckFlg) {
            IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
            StringBuffer sb = new StringBuffer();
            sb = new StringBuffer();
            sb.append(" SELECT");
            sb.append(" COUNT(INSURER_NO) AS CNT_INSURER_NO");
            sb.append(" FROM");
            sb.append(" INSURER");
            sb.append(" WHERE");
            sb.append(" INSURER_NO = '" + insurerNoField.getText() + "'");
            VRArrayList tmpArray = (VRArrayList) dbm
                    .executeQuery(sb.toString());
            VRMap tmpHashMap = (VRMap) tmpArray.getData();
            int cntInsurerNo = Integer.parseInt(String.valueOf(tmpHashMap
                    .get("CNT_INSURER_NO")));
            if (cntInsurerNo > 0) {
                showErrMessageBox(0, insurerNoField, "保険者番号が重複しています。");
                return false;
            }
        }

        // 保険者番号存在チェック(未入力チェック)
        if (isNullText(insurerNoField.getText())) {
            showErrMessageBox(0, insurerNoField, "保険者番号を入力してください。");
            return false;
        }
        // 保険者番号正規入力チェック(整数チェック)
        /*
         * Pattern pattern = Pattern.compile("[0-9]*"); Matcher matcher =
         * pattern.matcher(insurerNoField.getText()); if (!matcher.matches()) {
         * showErrMessageBox(0, insurerNoField, "保険者番号は、整数で入力してください。"); return
         * false; }
         */

        // 出力パターン未選択チェック
        if (seikyuPattern.getSelectedIndex() <= 0) {
            showErrMessageBox(0, seikyuPattern, "請求パターンが選択されていません。");
            return false;
        }

        // 意見書作成料請求先 / 請求先名称入力チェック(使用可能な場合のみ)
        if (issInsurerNm.isEnabled()) {
            if (isNullText(issInsurerNm.getText())) {
                showErrMessageBox(0, issInsurerNm, "請求先名称を入力してください。");
                return false;
            }
        }
        // 意見書作成料請求先 / 意見書総括表出力状態チェック
        if (soukatuhyou.isEnabled()) {
            if (soukatuhyou.getSelectedIndex() <= 0) {
                showErrMessageBox(0, soukatuhyou, issGrp.getText()
                        + "の総括表出力が選択されていません。");
                return false;
            }
        }
        // 意見書作成料請求先 / 明細書種類状態チェック
        if (meisaiKind.isEnabled()) {
            if (meisaiKind.getSelectedIndex() <= 0) {
                showErrMessageBox(0, meisaiKind, issGrp.getText()
                        + "の明細書種類が選択されていません。");
                return false;
            }
        }

        // 診察・検査費用請求先 / 請求先名称2入力チェック(使用可能な場合のみ)
        if (sksInsurerNm.isEnabled()) {
            if (isNullText(sksInsurerNm.getText())) {
                showErrMessageBox(0, sksInsurerNm, "請求先名称を入力してください。");
                return false;
            }
        }
        // 診察・検査費用請求先 / 総括表出力状態チェック
        if (soukatuhyou2.isEnabled()) {
            if (soukatuhyou2.getSelectedIndex() <= 0) {
                showErrMessageBox(0, soukatuhyou2, sksGrp.getText()
                        + "の総括表出力が選択されていません。");
                return false;
            }
        }
        // 診察・検査費用請求先 / 明細書出力状態チェック
        if (meisaiKind2.isEnabled()) {
            if (meisaiKind2.getSelectedIndex() <= 0) {
                showErrMessageBox(0, meisaiKind2, sksGrp.getText()
                        + "の明細表種類が選択されていません。");
                return false;
            }
        }

        String pat = "[0-9]+";
        String msg = "設定値が不正です。";
        // 在宅新規入力チェック
        if (!isValidPattern(pat, 1, zaitakuSinkiCharge, msg)) {
            return false;
        }
        // 在宅継続入力チェック
        if (!isValidPattern(pat, 1, zaitakuKeizokuCharge, msg)) {
            return false;
        }
        // 施設新規入力チェック
        if (!isValidPattern(pat, 1, sisetuSinkiCharge, msg)) {
            return false;
        }
        // 施設継続入力チェック
        if (!isValidPattern(pat, 1, sisetuKeizokuCharge, msg)) {
            return false;
        }

        pat = "(\\d+)|((\\d+)(\\.\\d))";
        msg = "点数は小数点以下1桁までの正の数を入力してください。";
        // 初診料 診療所入力チェック
        if (!isValidPattern(pat, 1, shosinSinryoujo, msg)) {
            return false;
        }
        // 初診料 病院入力チェック
        if (!isValidPattern(pat, 1, shosinHospital, msg)) {
            return false;
        }
        // 血液採取(静脈)入力チェック
        if (!isValidPattern(pat, 1, expKs, msg)) {
            return false;
        }
        // 末梢血液一般検査入力チェック
        if (!isValidPattern(pat, 1, expKikMki, msg)) {
            return false;
        }
        // 血液学的検査判断料入力チェック
        if (!isValidPattern(pat, 1, expKikKekk, msg)) {
            return false;
        }
        // 血液化学検査(10項目以上)入力チェック
        if (!isValidPattern(pat, 1, expKkkKkk, msg)) {
            return false;
        }
        // 生化学的検査(I)判断料入力チェック
        if (!isValidPattern(pat, 1, expKkkSkk, msg)) {
            return false;
        }
        // 尿中一般物質定性半定量検査入力チェック
        if (!isValidPattern(pat, 1, expNitk, msg)) {
            return false;
        }
        // 単純撮影入力チェック
        if (!isValidPattern(pat, 1, expXrayTs, msg)) {
            return false;
        }
        // 写真診断(胸部)入力チェック
        if (!isValidPattern(pat, 1, expXraySs, msg)) {
            return false;
        }
        // フィルム(大角)入力チェック
        if (!isValidPattern(pat, 1, expXrayFilm, msg)) {
            return false;
        }

        return true;
    }

    /**
     * 整数チェック(必須レベル)
     * 
     * @param pat String
     * @param tabIdx int
     * @param txtFld IkenshoTextField
     * @param msg String
     * @return boolean
     */
    private boolean isValidPattern(String pat, int tabIdx, ACTextField txtFld,
            String msg) {
        Pattern pattern = Pattern.compile(pat);
        Matcher matcher = pattern.matcher(txtFld.getText());
        if (!matcher.matches()) {
            showErrMessageBox(tabIdx, txtFld, msg);
            return false;
        }
        return true;
    }

    /**
     * ワーニングチェック
     * 
     * @return valid / true:有効(OK), false:無効(ERR)
     * @throws Exception
     */
    private boolean checkWarning() throws Exception {
        // 保険者番号桁数チェック
        Pattern pattern = Pattern.compile("[0-9]{6}");
        Matcher matcher = pattern.matcher(insurerNoField.getText());
        if (!matcher.matches()) {
            if (!showWarningMessageBox("保険者番号が数字6桁入力されていません。\nよろしいですか？")) {
                return false;
            }
        }

        // 保険者番号1
        if (issInsurerNo.isEnabled()) {
            matcher = pattern.matcher(issInsurerNo.getText());
            if (!matcher.matches()) {
                if (!showWarningMessageBox(issGrp.getText()
                        + "の保険者番号が数字6桁入力されていません。\nよろしいですか？")) {
                    return false;
                }
            }
        }

        // 保険者番号2
        if (sksInsurerNo.isEnabled()) {
            matcher = pattern.matcher(sksInsurerNo.getText());
            if (!matcher.matches()) {
                if (!showWarningMessageBox(sksGrp.getText()
                        + "の保険者番号が数字6桁入力されていません。\nよろしいですか？")) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 「提示額に戻す」ボタン押下
     * 
     * @param e ActionEvent
     */
    public void rollBack_actionPerformed(ActionEvent e) {
        String msg = "意見書作成料／診察・検査費用点数に\n厚労省提示額を設定します。よろしいですか？";
        int result = ACMessageBox.show(msg, ACMessageBox.BUTTON_OKCANCEL,
                ACMessageBox.ICON_QUESTION, ACMessageBox.FOCUS_OK);
        if (result == ACMessageBox.RESULT_OK) {
            try {
                loadMKingakuTensu();
            } catch (Exception ex) {
                IkenshoCommon.showExceptionMessage(ex);
            }
        }
    }

    /**
     * 「依頼元と同一」(意見書作成料)
     * 
     * @param e ActionEvent
     */
    public void issInsurerSame_actionPerformed(ActionEvent e) {
        issInsurerNm.setText(insurerNmField.getText());
        issInsurerNo.setText(insurerNoField.getText());
    }

    /**
     * 「依頼元と同一」(診察・検査費用)
     * 
     * @param e ActionEvent
     */
    public void sksInsurerSame_actionPerformed(ActionEvent e) {
        sksInsurerNm.setText(insurerNmField.getText());
        sksInsurerNo.setText(insurerNoField.getText());
    }

    /**
     * 「請求パターン」Radio選択時
     * 
     * @param e ListSelectionEvent
     */
    public void seikyuPattern_valueChanged(ListSelectionEvent e) {
        setseikyuPatternEnabled();
    }

    /**
     * 請求パターン内の各コンポーネントのEnabledを設定します。
     */
    public void setseikyuPatternEnabled() {
        switch (seikyuPattern.getSelectedIndex()) {
        case 1: // 「意見書作成料・検査料(1枚)」
            issGrp.setText("意見書作成料／診察・検査費用請求先");
            setIssGrpEnabled(true);
            setSksGrpEnabled(false);
            break;

        case 2: // 「意見書作成料(1枚)・検査料(1枚)」
            issGrp.setText("意見書作成料請求先");
            setIssGrpEnabled(true);
            setSksGrpEnabled(true);
            break;

        case 3: // 「意見書作成料のみ」
            issGrp.setText("意見書作成料請求先");
            setIssGrpEnabled(true);
            setSksGrpEnabled(false);
            break;

        case 4: // 「検査料のみ」

            // issGrp.setText("意見書作成料請求先");
            setIssGrpEnabled(false);
            setSksGrpEnabled(true);
            break;

        default:
            setIssGrpEnabled(false);
            setSksGrpEnabled(false);
            break;
        }
    }

    /**
     * 意見書作成料請求先のEnabledを設定します。
     * 
     * @param enabled boolean
     */
    public void setIssGrpEnabled(boolean enabled) {
        issGrp.setEnabled(enabled);
        issInsurerNmContainer.setEnabled(enabled);
        issInsurerNm.setEnabled(enabled);
        issInsurerNoContainer.setEnabled(enabled);
        issInsurerNo.setEnabled(enabled);
        issInsurerSame.setEnabled(enabled);
        soukatuhyouGrp.setEnabled(enabled);
        soukatuhyou.setEnabled(enabled);
        setSoukatuhyouPrintEnabled(enabled, soukatuhyou, soukatuhyouPrint);
        meisaiKindGrp.setEnabled(enabled);
        meisaiKind.setEnabled(enabled);
        meisaiKindPrint.setEnabled(enabled);
    }

    /**
     * 診察・検査費用請求先のEnabledを設定します。
     * 
     * @param enabled boolean
     */
    public void setSksGrpEnabled(boolean enabled) {
        sksGrp.setEnabled(enabled);
        sksInsurerNmContainer.setEnabled(enabled);
        sksInsurerNm.setEnabled(enabled);
        sksInsurerNoContainer.setEnabled(enabled);
        sksInsurerNo.setEnabled(enabled);
        sksInsurerSame.setEnabled(enabled);
        soukatuhyouGrp2.setEnabled(enabled);
        soukatuhyou2.setEnabled(enabled);
        setSoukatuhyouPrintEnabled(enabled, soukatuhyou2, soukatuhyouPrint2);
        meisaiKindGrp2.setEnabled(enabled);
        meisaiKind2.setEnabled(enabled);
        meisaiKindPrint2.setEnabled(enabled);
    }

    /**
     * 総括表振込先印刷のEnabledを設定します。
     * 
     * @param enabled boolean
     * @param rdo IkenshoRadioButtonGroup
     * @param chk IkenshoIntegerCheckBox
     */
    public void setSoukatuhyouPrintEnabled(boolean enabled,
            ACClearableRadioButtonGroup rdo, ACIntegerCheckBox chk) {
        if (enabled) {
            switch (rdo.getSelectedIndex()) {
            case 1:
                chk.setEnabled(true);
                break;
            case 2:
                chk.setEnabled(false);
                break;
            default:
                chk.setEnabled(false);
                break;
            }
        } else {
            chk.setEnabled(false);
        }
    }

    /**
     * 総括表Groupの値変更時の設定を行います。
     * 
     * @param skh 総括表RadioButtonGroup
     * @param skhPrint 総括表振込先印刷CheckBox
     */
    public void setSoukatuhyouValue(ACClearableRadioButtonGroup skh,
            ACIntegerCheckBox skhPrint) {
        switch (skh.getSelectedIndex()) {
        case 1:
            skhPrint.setEnabled(true);
            skhPrint.setSelected(true);
            break;

        case 2:
            skhPrint.setEnabled(false);
            break;

        default:
            skhPrint.setEnabled(false);
            skhPrint.setSelected(false);
            break;
        }
    }
}
