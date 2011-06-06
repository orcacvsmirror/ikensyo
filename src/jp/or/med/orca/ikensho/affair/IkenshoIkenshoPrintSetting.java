/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.JComponent;
import javax.swing.SwingConstants;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.io.VRBase64;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.picture.IkenshoHumanPicture;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

public class IkenshoIkenshoPrintSetting extends IkenshoDialog {

    private VRPanel contents = new VRPanel();
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//    private ACGroupBox printOptionGroup = new ACGroupBox();
    protected ACGroupBox printOptionGroup;
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    private VRPanel billPrintPanel = new VRPanel();
    private ACGroupBox billPrintGroup = new ACGroupBox();
    private ACGroupBox csvGroup = new ACGroupBox();
    private ACLabelContainer billPrints = new ACLabelContainer();
    private ACIntegerCheckBox billPrint = new ACIntegerCheckBox();
    private VRLabel billHokensyaUnselectAlert = new VRLabel();
    private VRLabel csvSubmitUnselectAlert = new VRLabel();
    private ACIntegerCheckBox csvSubmit = new ACIntegerCheckBox();
    private ACLabelContainer csvSubmits = new ACLabelContainer();
    private ACGroupBox toBillGroup = new ACGroupBox();
    private ACLabelContainer printToCreateCosts = new ACLabelContainer();
    private ACIntegerCheckBox printToCreateCost = new ACIntegerCheckBox();
    private ACGroupBox billPrintDateGroup = new ACGroupBox();
    private VRPanel billPanel = new VRPanel();
    private ACLabelContainer billPrintDates = new ACLabelContainer();
    private ACGroupBox billPatternGroup = new ACGroupBox();
    private ACLabelContainer billPatterns = new ACLabelContainer();
    private VRLabel billPattern = new VRLabel();
    private VRLabel toCheckCost = new VRLabel();
    private ACLabelContainer toCheckCosts = new ACLabelContainer();
    private VRLabel toCreateCost = new VRLabel();
    private ACLabelContainer toCreateCosts = new ACLabelContainer();
    private ACIntegerCheckBox printToCheckCost = new ACIntegerCheckBox();
    private ACLabelContainer printToCheckCosts = new ACLabelContainer();
    private IkenshoEraDateTextField billPrintDate = new IkenshoEraDateTextField();
    private ACLabelContainer billDetailPrintDates = new ACLabelContainer();
    private IkenshoEraDateTextField billDetailPrintDate = new IkenshoEraDateTextField();
    private ACParentHesesPanelContainer billDetailPrintDateHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer billPrintDateHeses = new ACParentHesesPanelContainer();
    private VRPanel billPrintDateButtons = new VRPanel();
    private VRPanel billPrintDatesPanel = new VRPanel();
    private ACButton nowDate = new ACButton();
    private VRLayout contentsLayout = new VRLayout();
    private VRPanel buttons = new VRPanel();
    private ACButton clearDate = new ACButton();
    private VRLayout billPrintPanelLayout = new VRLayout();
    private VRLayout billPanelLayout = new VRLayout();
    private ACIntegerCheckBox printPageHeader = new ACIntegerCheckBox();
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    //private ACGroupBox pageHeaderGroup = new ACGroupBox();
    protected ACGroupBox pageHeaderGroup;
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    private ACLabelContainer printPageHeaders = new ACLabelContainer();
    private ACLabelContainer secondHeaders = new ACLabelContainer();
    private ACIntegerCheckBox printSecondHeader = new ACIntegerCheckBox();
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//    private ACGroupBox secondHeaderGroup = new ACGroupBox();
    protected ACGroupBox secondHeaderGroup;
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    private ACLabelContainer doctorNames = new ACLabelContainer();
    private ACIntegerCheckBox printDoctorName = new ACIntegerCheckBox();
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//    private ACGroupBox doctorNameGroup = new ACGroupBox();
    protected ACGroupBox doctorNameGroup;
    //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    private VRLayout printOptionGroupLayout = new VRLayout();
    private ACButton ok = new ACButton();
    private ACButton cancel = new ACButton();
    private VRLayout billPatternGroupLayout = new VRLayout();
    private VRLayout billPrintDatesPanelLayout = new VRLayout();
    private VRLayout toBillGroupLayout = new VRLayout();
    private VRLabel toCheckCostHead = new VRLabel();
    private VRLabel toCreateCostHead = new VRLabel();
    private VRPanel vRPanel1 = new VRPanel();
    private VRLabel billPrintNoSaveAlert1 = new VRLabel();
    private VRPanel csvSubmitAlerts = new VRPanel();
    private VRLabel csvSubmitNoSaveAlert = new VRLabel();
    // [ID:0000555][Tozo TANAKA] 2009/09/28 replace begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
//    private VRLabel csvSubmitHiHokensyaUnselectAlert = new VRLabel();
    protected VRLabel csvSubmitHiHokensyaUnselectAlert = new VRLabel();
    // [ID:0000555][Tozo TANAKA] 2009/09/28 replace end 【2009年度対応：追加案件】医師意見書の受給者番号対応
    private VRLabel billDetailPrintDateUnit = new VRLabel();
    private ACParentHesesPanelContainer toCreateCostHeses = new ACParentHesesPanelContainer();
    private ACParentHesesPanelContainer billPatternHeses = new ACParentHesesPanelContainer();
    private VRLabel csvSubmitedAlert = new VRLabel();
    private VRLabel csvTargetAlert = new VRLabel();
    private VRLabel notMostNewDocumentAlert = new VRLabel();

    protected VRMap source;
    protected IkenshoHumanPicture picture;
    protected int outputPattern = -1;
    protected boolean printed = false;
    protected IkenshoIkenshoInfoPrintParameter printParameter;
    private VRLabel billKindUnselectAlert = new VRLabel();
    private VRLabel billDoctorUnselectAlert = new VRLabel();
    private VRLabel billConvertedNoBillAlert = new VRLabel();

    /**
     * モーダル表示し、印刷されたかを返します。
     * 
     * @param printParam 印刷パラメータ
     * @return 印刷されたか
     */
    public boolean showModal(IkenshoIkenshoInfoPrintParameter printParam) {
        this.printParameter = printParam;
        try {
            VRMap map = (VRMap) contents.createSource();
            map.putAll(source);
            source = map;

            if (VRBindPathParser.has("OUTPUT_PATTERN", source)) {
                outputPattern = (new Integer(String.valueOf(VRBindPathParser
                        .get("OUTPUT_PATTERN", source)))).intValue();
                switch (outputPattern) {
                case 1:
                    source.setData("BILL_PATTERN", "意見書作成料・検査料(1枚)");
                    printToCreateCost.setText("印刷する");
                    // 意見書作成料＝検査料で表示する仕様
                    source.setData("SKS_INSURER_NM", source
                            .getData("ISS_INSURER_NM"));
                    source.setData("SKS_INSURER_NO", source
                            .getData("ISS_INSURER_NO"));
                    break;
                case 2:
                    source.setData("BILL_PATTERN", "意見書作成料(1枚)・検査料(1枚)");
                    break;
                case 3:
                    source.setData("BILL_PATTERN", "意見書作成料のみ");
                    billPatternHeses.setVisible(false);
                    break;
                case 4:
                    source.setData("BILL_PATTERN", "検査料のみ");
                    toCreateCostHeses.setVisible(false);
                    break;
                default:
                    source.setData("BILL_PATTERN", "");
                    billPatternHeses.setVisible(false);
                    toCreateCostHeses.setVisible(false);
                }
            } else {
                source.setData("BILL_PATTERN", "");
                billPatternHeses.setVisible(false);
                toCreateCostHeses.setVisible(false);
            }

            // エラー無し
            final int NO_ERROR = 0;
            // 未保存
            final int NEVER_SAVED = 2 << 0;
            // 種別未選択
            final int KIND_UNSELECT = 2 << 1;
            // 保険者未選択
            final int INSURER_UNSELECT = 2 << 2;
            // 保険者未選択
            final int DOCTOR_UNSELECT = 2 << 3;
            // CSV出力済み
            final int CSV_OUTPUTED = 2 << 4;
            // CSV出力不可能な保険者
            final int CSV_CANNOT_OUTPUT_INSURER = 2 << 5;
            // 被保険者番号未入力
            final int INSURED_NO_UNINPUT = 2 << 6;
            // 最新の意見書ではない
            final int NOT_MOST_NEW_DOCUMENT = 2 << 7;
            // 2005/12/11[Tozo Tanaka] : add begin
            // 請求データの無い移行直後のデータ
            final int NO_BILL_OF_OLD_VERSION_CONVERTED = 2 << 8;
            // 2005/12/11[Tozo Tanaka] : add end
            //2006/02/12[Tozo Tanaka] : add begin
            // CSV出力対象
            final int CSV_TARGET = 2 << 9;
            //2006/02/12[Tozo Tanaka] : add end
            
            
            // 請求書印刷不可要件
            final int CANNOT_OUTPUT_BILL = NEVER_SAVED | INSURER_UNSELECT
                    | KIND_UNSELECT | DOCTOR_UNSELECT | NO_BILL_OF_OLD_VERSION_CONVERTED;
            // CSV出力不可要件
            //2006/09/09[Tozo Tanaka] : replace begin
//            //2006/02/12[Tozo Tanaka] : replace begin
//            final int CANNOT_OUTPUT_CSV = NEVER_SAVED
//                    | CSV_CANNOT_OUTPUT_INSURER | INSURED_NO_UNINPUT
//                    | CSV_OUTPUTED | NOT_MOST_NEW_DOCUMENT;
//            final int CANNOT_OUTPUT_CSV = NEVER_SAVED
//            | CSV_CANNOT_OUTPUT_INSURER | INSURED_NO_UNINPUT
//            | CSV_OUTPUTED | NOT_MOST_NEW_DOCUMENT | CSV_TARGET;
//            //2006/02/12[Tozo Tanaka] : replace end
            final int CANNOT_OUTPUT_CSV = NEVER_SAVED
                    | CSV_CANNOT_OUTPUT_INSURER | INSURED_NO_UNINPUT
                    | CSV_OUTPUTED | NOT_MOST_NEW_DOCUMENT | CSV_TARGET
                    | NO_BILL_OF_OLD_VERSION_CONVERTED;
            //2006/09/09[Tozo Tanaka] : replace end

            
            int alertFlag = NO_ERROR;

            // 保存チェック
            if (printParameter.isNeverSaved()) {
                alertFlag |= NEVER_SAVED;
            }
            // 保険者選択チェック
            if (IkenshoCommon.isNullText(VRBindPathParser.get("INSURER_NO",
                    source))) {
                alertFlag |= INSURER_UNSELECT;
            }
            // 医療機関選択チェック
            Object obj = VRBindPathParser.get("MI_KBN", source);
            if ((!(obj instanceof Integer))
                    || (((Integer) obj).intValue() <= 0)) {
                alertFlag |= DOCTOR_UNSELECT;
            }
            // 保険者種別
            if (((Integer) VRBindPathParser.get("KIND", source)).intValue() == 0) {
                alertFlag |= KIND_UNSELECT;
            }
            // CSV出力可否
            if (((Integer) VRBindPathParser.get("FD_OUTPUT_UMU", source))
                    .intValue() == 0) {
                alertFlag |= CSV_CANNOT_OUTPUT_INSURER;
            }
            // 2006/12/12[Tozo Tanaka] : add begin
            // CSV出力対象チェック
            if (printParameter.isCsvTarget()) {
                alertFlag |= CSV_TARGET;
            }
            // 2006/12/12[Tozo Tanaka] : add end
            // CSV出力済みチェック
            if (printParameter.isCsvSubmited()) {
                alertFlag |= CSV_OUTPUTED;
            }
            // 被保険者番号チェック
            if (IkenshoCommon.isNullText(VRBindPathParser.get("INSURED_NO",
                    source))) {
                alertFlag |= INSURED_NO_UNINPUT;
            }
            // 最新意見書チェック
            if (printParameter.isNotMostNewDocument()) {
                alertFlag |= NOT_MOST_NEW_DOCUMENT;
            }

            
            // 2005/12/11[Tozo Tanaka] : add begin
            if (IkenshoCommon.isConvertedNoBill(map)) {
                // 保険者選択済みかつ請求書出力パターンが0＝以降直後で請求データが不正
                alertFlag |= NO_BILL_OF_OLD_VERSION_CONVERTED;
            }
            // 2005/12/11[Tozo Tanaka] : add end

            
            // 保存チェック
            if ((alertFlag & NEVER_SAVED) > 0) {
                billPrintNoSaveAlert1.setVisible(true);
                csvSubmitNoSaveAlert.setVisible(true);
                // -------------------------------------------------
                billHokensyaUnselectAlert.setVisible(false);
                billKindUnselectAlert.setVisible(false);
                billDoctorUnselectAlert.setVisible(false);
                csvSubmitUnselectAlert.setVisible(false);
                csvSubmitHiHokensyaUnselectAlert.setVisible(false);
                csvSubmitedAlert.setVisible(false);
                notMostNewDocumentAlert.setVisible(false);
                // 2005/12/11[Tozo Tanaka] : add begin
                billConvertedNoBillAlert.setVisible(false);
                // 2005/12/11[Tozo Tanaka] : add end
                // 2006/12/12[Tozo Tanaka] : add begin
                csvTargetAlert.setVisible(false);
                // 2006/12/12[Tozo Tanaka] : add end
            } else {
                billPrintNoSaveAlert1.setVisible(false);
                csvSubmitNoSaveAlert.setVisible(false);
                // 2005/12/11[Tozo Tanaka] : add begin
                // -------------------------------------------------
                //移行データチェック
                if ((alertFlag & NO_BILL_OF_OLD_VERSION_CONVERTED) > 0) {
                    billConvertedNoBillAlert.setVisible(true);
                    billHokensyaUnselectAlert.setVisible(false);
                // -------------------------------------------------
                    billKindUnselectAlert.setVisible(false);
                    billDoctorUnselectAlert.setVisible(false);
                    // 2005/12/11[Tozo Tanaka] : add end
                
                // 保険者選択チェック
                }else if ((alertFlag & INSURER_UNSELECT) > 0) {
                    // 2005/12/11[Tozo Tanaka] : add begin
                    billConvertedNoBillAlert.setVisible(false);
                    // 2005/12/11[Tozo Tanaka] : add end
                    billHokensyaUnselectAlert.setVisible(true);
                    // -------------------------------------------------
                    billKindUnselectAlert.setVisible(false);
                    billDoctorUnselectAlert.setVisible(false);
                } else {
                    // 2005/12/11[Tozo Tanaka] : add begin
                    billConvertedNoBillAlert.setVisible(false);
                    // 2005/12/11[Tozo Tanaka] : add end
                    billHokensyaUnselectAlert.setVisible(false);
                    // -------------------------------------------------
                    // 保険者種別
                    if ((alertFlag & KIND_UNSELECT) > 0) {
                        billKindUnselectAlert.setVisible(true);
                        billDoctorUnselectAlert.setVisible(false);
                    } else {
                        billKindUnselectAlert.setVisible(false);
                        // -------------------------------------------------
                        // 医療機関
                        if ((alertFlag & DOCTOR_UNSELECT) > 0) {
                            billDoctorUnselectAlert.setVisible(true);
                        } else {
                            billDoctorUnselectAlert.setVisible(false);
                        }
                    }
                }

                // CSV出力可否
                // 2006/09/09[Tozo Tanaka] : replace begin
                //if ((alertFlag & CSV_CANNOT_OUTPUT_INSURER) > 0) {
                if ((alertFlag & (CSV_CANNOT_OUTPUT_INSURER | NO_BILL_OF_OLD_VERSION_CONVERTED)) > 0) {
                    // 2006/09/09[Tozo Tanaka] : replace end
                    csvSubmitUnselectAlert.setVisible(true);
                    // -------------------------------------------------
                    csvSubmitHiHokensyaUnselectAlert.setVisible(false);
                    notMostNewDocumentAlert.setVisible(false);
                    csvSubmitedAlert.setVisible(false);

                    // 2005/12/12[Tozo Tanaka] : add begin
                    csvTargetAlert.setVisible(false);
                    // 2005/12/12[Tozo Tanaka] : add end
                } else {
                    csvSubmitUnselectAlert.setVisible(false);
                    // -------------------------------------------------
                    // 被保険者番号チェック
                    if ((alertFlag & INSURED_NO_UNINPUT) > 0) {
                        csvSubmitHiHokensyaUnselectAlert.setVisible(true);
                        // -------------------------------------------------
                        notMostNewDocumentAlert.setVisible(false);
                        csvSubmitedAlert.setVisible(false);
                        // 2006/12/12[Tozo Tanaka] : add begin
                        csvTargetAlert.setVisible(false);
                        // 2006/12/12[Tozo Tanaka] : add end
                    } else {
                        csvSubmitHiHokensyaUnselectAlert.setVisible(false);
                        // -------------------------------------------------
                        // 最新意見書チェック
                        if ((alertFlag & NOT_MOST_NEW_DOCUMENT) > 0) {
                            notMostNewDocumentAlert.setVisible(true);
                            // -------------------------------------------------
                            csvSubmitedAlert.setVisible(false);
                            // 2006/12/12[Tozo Tanaka] : add begin
                            csvTargetAlert.setVisible(false);
                            // 2006/12/12[Tozo Tanaka] : add end
                        } else {
                            notMostNewDocumentAlert.setVisible(false);
                            // -------------------------------------------------
                            // CSV出力済みチェック
                            if ((alertFlag & CSV_OUTPUTED) > 0) {
                                csvSubmitedAlert.setVisible(true);
                                // 2006/12/12[Tozo Tanaka] : add begin
                                csvTargetAlert.setVisible(false);
                                // 2006/12/12[Tozo Tanaka] : add end
                            } else {
                                csvSubmitedAlert.setVisible(false);
                                // 2006/12/12[Tozo Tanaka] : add begin
                                // CSV出力対象チェック
                                if ((alertFlag & CSV_TARGET) > 0) {
                                    csvTargetAlert.setVisible(true );
                                }else{
                                    csvTargetAlert.setVisible(false);
                                }
                                // 2006/12/12[Tozo Tanaka] : add end
                            }
                        }
                    }
                }
            }

            if ((alertFlag & INSURER_UNSELECT) > 0) {
                // 保険者未選択のため、デフォルト値代入
                source.setData("HEADER_OUTPUT_UMU1", new Integer(1));
                source.setData("HEADER_OUTPUT_UMU2", new Integer(1));
            }


            contents.setSource(source);
            contents.bindSource();

            if ((alertFlag & CANNOT_OUTPUT_BILL) > 0) {
                // 請求書印刷不可
                billPrint.setEnabled(false);
                billPrint.setSelected(false);
                printToCreateCost.setSelected(false);
                printToCheckCost.setSelected(false);
                csvSubmit.setSelected(false);
            } else {
                billPrint.setEnabled(true);

                // 転記
                obj = source.getData("KOUZA_KIND");
                if (obj == null) {
                    source.setData("KOUZA_MEIGI", source
                            .getData("FURIKOMI_MEIGI"));
                    source.setData("KOUZA_NO", source.getData("BANK_KOUZA_NO"));
                    source.setData("KOUZA_KIND", source
                            .getData("BANK_KOUZA_KIND"));
                }
            }

            if ((alertFlag & CANNOT_OUTPUT_CSV) > 0) {
                // CSV印刷不可
                csvSubmit.setEnabled(false);
                csvSubmit.setSelected(false);
            } else {
                csvSubmit.setEnabled(true);
            }

            if (csvSubmit.isEnabled() || printParameter.isCsvSubmited()) {
                // CSV出力が可能でかつ出力する場合はヘッダへの保険者番号と被保険者番号の印字は固定
                printPageHeader.setEnabled(false);
            }

            if (billPrint.isSelected()) {
                followBillEnabled(true);
            }

        } catch (ParseException ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }
        
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        beforeShow();
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        
        setVisible(true);
        // show();
        return printed;
    }
    
    /**
     * コンストラクタです。
     * 
     * @param data 印刷データ
     * @param picture 全身図
     * @throws HeadlessException 初期化例外
     */
    public IkenshoIkenshoPrintSetting(VRMap data, IkenshoHumanPicture picture)
            throws HeadlessException {
        super(ACFrame.getInstance(), "「主治医意見書」印刷設定", true);
        this.picture = picture;

        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();

            // 印刷画面の操作によって、元データが上書きされないようクローンを作成する
            this.source = (VRMap) contents.createSource();
            this.source.putAll(data);
            contents.setSource(this.source);
            contents.bindSource();
            this.source = new VRHashMap();
            this.source.putAll(data);
            this.source.putAll((VRMap) contents.createSource());
            contents.setSource(this.source);
            contents.applySource();
        } catch (Exception e) {
            e.printStackTrace();
        }

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (print()) {
                    dispose();
                }
            }
        });

        nowDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                billPrintDate.setDate(new Date());
                billDetailPrintDate.setDate(new Date());
            }
        });
        clearDate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                billPrintDate.clear();
                billDetailPrintDate.clear();
            }
        });

        billPrint.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {

                followBillEnabled(e.getStateChange() == ItemEvent.SELECTED);
            }
        });

        csvSubmit.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                if (csvSubmit.isEnabled()) {
                    // CSV出力が可能でかつ出力する場合はヘッダへの保険者番号と被保険者番号の印字は固定
                    printPageHeader.setEnabled(!csvSubmit.isSelected());
                }
            }
        });

    }

    protected void followBillEnabled(boolean selected) {
        selected = selected && billPrint.isEnabled();
        if (selected) {
            switch (outputPattern) {
            case 1: // 1:作成料･検査料(1枚)
                printToCreateCost.setEnabled(true);
                printToCheckCost.setEnabled(false);
                break;
            case 2: // 2:作成料(1枚)･検査料(1枚)
                printToCreateCost.setEnabled(true);
                printToCheckCost.setEnabled(true);
                break;
            case 3: // 3:意見書作成料のみ
                printToCreateCost.setEnabled(true);
                break;
            case 4: // 4:検査料のみ
                printToCheckCost.setEnabled(true);
                break;
            }
        } else {
            printToCreateCost.setEnabled(false);
            printToCheckCost.setEnabled(false);
        }

        final JComponent[] comps = new JComponent[] { billPatterns,
                billPattern, toCreateCosts, toCreateCostHead,
                toCreateCostHeses, toCreateCost, toCheckCosts, toCheckCostHead,
                billPatternHeses, toCheckCost, billPrintDates,
                billPrintDateHeses, billPrintDate, billDetailPrintDateHeses,
                billDetailPrintDate, billDetailPrintDateUnit, nowDate,
                clearDate };

        final int end = comps.length;
        for (int i = 0; i < end; i++) {
            comps[i].setEnabled(selected);
        }

    }

    /**
     * 意見書のヘッダを印刷します。
     * 
     * @param pd XML生成クラス
     * @param data データソース
     * @param printDate 出力日
     * @throws Exception 処理例外
     */
    protected static void printIkenshoHeader(ACChotarouXMLWriter pd,
            VRMap data, Date printDate) throws Exception {
        
        // 頁ヘッダ(保険者・被保険者番号)
        if (((Integer) VRBindPathParser.get("HEADER_OUTPUT_UMU1", data))
                .intValue() == 1) {
            if (!IkenshoCommon.isNullText((String) VRBindPathParser.get(
                    "INSURER_NO", data))) {
                // 保険者番号
                IkenshoCommon.addString(pd, data, "INSURER_NO", "INSURER_NO");
            } else {
                // [ID:0000516][Masahiko Higuchi] 2009/09 add begin ヘッダの表示対応
                ACChotarouXMLUtilities.setVisible(pd, "INSURER_NO_LABEL", false);
                // [ID:0000516][Masahiko Higuchi] 2009/09 add end
            }
            
            if (!IkenshoCommon.isNullText((String) VRBindPathParser.get(
                    "INSURED_NO", data))) {
                // 被保険者番号
                IkenshoCommon.addString(pd, data, "INSURED_NO", "INSURED_NO");
            } else {
                // [ID:0000516][Masahiko Higuchi] 2009/09 add begin ヘッダの表示対応
                ACChotarouXMLUtilities.setVisible(pd, "INSURERD_NO_LABEL", false);
                // [ID:0000516][Masahiko Higuchi] 2009/09 add end
            }
            
        } else {
            // [ID:0000516][Masahiko Higuchi] 2009/09 add begin ヘッダの表示対応
            ACChotarouXMLUtilities.setVisible(pd, "INSURER_NO_LABEL", false);
            ACChotarouXMLUtilities.setVisible(pd, "INSURERD_NO_LABEL", false);
            // [ID:0000516][Masahiko Higuchi] 2009/09 add end
            pd.addAttribute("CORNER_BLOCK", "Visible", "FALSE");
            // IkenshoCommon.addString(pd, "CORNER_BLOCK", "");
        }
        if (printDate != null) {
            // 日時分秒
            IkenshoCommon.addString(pd, "FD_OUTPUT_TIME", VRDateParser.format(
                    printDate, "ddHHmmss"));
        } else {
            // [ID:0000516][Masahiko Higuchi] 2009/09 add begin ヘッダの表示対応
            ACChotarouXMLUtilities.setVisible(pd, "FD_OUTPUT_TIME_LABEL", false);
            // [ID:0000516][Masahiko Higuchi] 2009/09 add end
        }
    }

    /**
     * overrideして意見書の1ページ目印刷関数呼び出しを定義します。
     * 
     * @param pd XML生成クラス
     * @param formatName フォーマットID
     * @param data データソース
     * @param printDate 出力日
     * @throws Exception 処理例外
     */
    protected void callPrintIkensho(ACChotarouXMLWriter pd, String formatName,
            VRMap data, Date printDate) throws Exception {
        IkenshoIkenshoPrintSetting
                .printIkensho(pd, formatName, data, printDate);
    }

    /**
     * overrideして意見書の2ページ目印刷関数呼び出しを定義します。
     * 
     * @param pd XML生成クラス
     * @param formatName フォーマットID
     * @param data データソース
     * @param printDate 出力日
     * @param imageBytes 画像バイトデータ
     * @throws Exception 処理例外
     */
    protected void callPrintIkensho2(ACChotarouXMLWriter pd, String formatName,
            VRMap data, Date printDate, byte[] imageBytes) throws Exception {
        IkenshoIkenshoPrintSetting.printIkensho2(pd, formatName, data,
                printDate, imageBytes);
    }

    /**
     * 意見書の1ページ目を印刷します。
     * 
     * @param pd XML生成クラス
     * @param formatName フォーマットID
     * @param data データソース
     * @param printDate 出力日
     * @throws Exception 処理例外
     */
    public static void printIkensho(ACChotarouXMLWriter pd, String formatName,
            VRMap data, Date printDate) throws Exception {

        pd.beginPageEdit(formatName);

        printIkenshoHeader(pd, data, printDate);

        // ふりがな
        IkenshoCommon.addString(pd, data, "PATIENT_KN", "Grid1.h1.w3");
        // 氏名
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid1.h2.w3");

        // 生年月日
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("明".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } else if ("大".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } else if ("昭".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
        } else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        }
        // 年
        IkenshoCommon.addString(pd, "Grid1.h3.w3", VRDateParser.format(date,
                "ee"));
        // 月
        IkenshoCommon.addString(pd, "Grid1.h3.w5", VRDateParser.format(date,
                "MM"));
        // 日
        IkenshoCommon.addString(pd, "Grid1.h3.w7", VRDateParser.format(date,
                "dd"));

        // 年齢
        IkenshoCommon.addString(pd, data, "AGE", "Grid1.h3.w15", "(");

        // 性別
        IkenshoCommon.addSelection(pd, data, "SEX", new String[] { "Shape4",
                "Shape5" }, -1);

        // 郵便番号
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid1.h1.w11");
        // 住所
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid1.h2.w12");
        // 電話番号
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid1.h3.w9");

        // 記入日
        IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 2, 1, "日");

        // 医師同意
        IkenshoCommon.addSelection(pd, data, "DR_CONSENT", new String[] {
                "Shape6", "Shape7" }, -1);

        if (((Integer) VRBindPathParser.get("DR_NM_OUTPUT_UMU", data))
                .intValue() == 1) {
            // 医師氏名
            IkenshoCommon.addString(pd, data, "DR_NM", "Grid4.h1.w3");
        }
        // 医療機関名
        IkenshoCommon.addString(pd, data, "MI_NM", "Grid4.h2.w4");
        // 医療機関所在地
        IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid4.h3.w5");

        // 医療機関電話番号
        IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid5.h1.w2");

        // 医療機関FAX番号
        IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid5.h3.w2");

        // 最終診察日
        IkenshoCommon.addEraDate(pd, data, "LASTDAY", "Grid6.h1.w", 12, -1);

        // 意見書作成回数
        IkenshoCommon.addSelection(pd, data, "IKN_CREATE_CNT", new String[] {
                "Shape8", "Shape9" }, -1);

        if (((Integer) VRBindPathParser.get("TAKA_FLAG", data)).intValue() == 1) {
            // 他科受診
            int taka = ((Integer) VRBindPathParser.get("TAKA", data))
                    .intValue();
            if (taka == 0) {
                // 他科内容
                for (int i = 0; i < 12; i++) {
                    pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
                }
                // 他科その他
                if (((Integer) VRBindPathParser.get("TAKA_OTHER_FLAG", data))
                        .intValue() == 1) {
                    IkenshoCommon.addString(pd, data, "TAKA_OTHER",
                            "Grid6.h3.w4");
                    pd.addAttribute("Shape11", "Visible", "FALSE");
                } else {
                    pd.addAttribute("Shape10", "Visible", "FALSE");
                    pd.addAttribute("Shape24", "Visible", "FALSE");
                }
                // pd.addAttribute("Shape24", "Visible", "FALSE");
            } else {
                pd.addAttribute("Shape11", "Visible", "FALSE");
                // 他科内容
                for (int i = 0; i < 12; i++) {
                    if ((taka & (1 << i)) == 0) {
                        pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
                    }
                }
                // 他科その他
                if (((Integer) VRBindPathParser.get("TAKA_OTHER_FLAG", data))
                        .intValue() == 1) {
                    IkenshoCommon.addString(pd, data, "TAKA_OTHER",
                            "Grid6.h3.w4");
                } else {
                    pd.addAttribute("Shape24", "Visible", "FALSE");
                }
            }
        } else {
            pd.addAttribute("Shape10", "Visible", "FALSE");
            // 他科内容
            for (int i = 0; i < 12; i++) {
                pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
            }
            // 他科その他
            pd.addAttribute("Shape24", "Visible", "FALSE");
        }

        // 傷病
        final String SPACER = "  ";
        for (int i = 0; i < 3; i++) {
            IkenshoCommon.addString(pd, data, "SINDAN_NM" + (i + 1), "Grid8.h"
                    + (i + 1) + ".w2");
            String text = String.valueOf(VRBindPathParser.get("HASHOU_DT"
                    + (i + 1), data));
            if (text.length() == 11) {
                era = text.substring(0, 2);
                if ("不詳".equals(era)) {
                    IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w5",
                            "不詳");
                } else if (!"0000".equals(text.substring(0, 4))) {
                    text = text.replaceAll("00", SPACER);
                    String head = "Grid8.h" + (i + 1) + ".w";

                    IkenshoCommon.addString(pd, head + "5", era);

                    String val;
                    val = text.substring(2, 4);
                    if (!SPACER.equals(val)) {
                        IkenshoCommon.addString(pd, head + 16, val);
                    }
                    IkenshoCommon.addString(pd, head + 15, "年");
                    val = text.substring(5, 7);
                    if (!SPACER.equals(val)) {
                        IkenshoCommon.addString(pd, head + 14, val);
                    }
                    IkenshoCommon.addString(pd, head + 13, "月");
                    val = text.substring(8, 10);
                    if (!SPACER.equals(val)) {
                        IkenshoCommon.addString(pd, head + 12, val);
                    }
                    IkenshoCommon.addString(pd, head + 11, "日");
                    IkenshoCommon.addString(pd, head + 10, "頃");
                }
            }
        }

        // 症状安定性
        IkenshoCommon.addSelection(pd, data, "SHJ_ANT", new String[] {
                "Shape25", "Shape26", "Shape27" }, -1);
        // 予後の見通し
        IkenshoCommon.addSelection(pd, data, "YKG_YOGO", new String[] {
                "Shape28", "Shape29", "Shape30" }, -1);
        // 傷病治療状態
        IkenshoCommon.addString(pd, data, "MT_STS", "Label13");

        // 薬剤
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                StringBuffer sb = new StringBuffer();
                String text;
                String index = String.valueOf(i * 2 + j + 1);
                text = (String) VRBindPathParser.get("MEDICINE" + index, data);
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                    sb.append(" ");
                }
                text = (String) VRBindPathParser.get("DOSAGE" + index, data);
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                }
                text = (String) VRBindPathParser.get("UNIT" + index, data);
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                }
                text = (String) VRBindPathParser.get("USAGE" + index, data);
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(" ");
                    sb.append(text);
                }
                if (sb.length() > 0) {
                    IkenshoCommon.addString(pd, "Grid9.h" + (i + 1) + ".w"
                            + (j + 1), sb.toString());
                }
            }
        }
        // 点滴管理
        IkenshoCommon.addCheck(pd, data, "TNT_KNR", "Shape31", 1);
        // 中心静脈栄養
        IkenshoCommon.addCheck(pd, data, "CHU_JOU_EIYOU", "Shape32", 1);
        // 透析
        IkenshoCommon.addCheck(pd, data, "TOUSEKI", "Shape33", 1);
        // ストーマの処置
        IkenshoCommon.addCheck(pd, data, "JINKOU_KOUMON", "Shape34", 1);
        // 酸素療法
        IkenshoCommon.addCheck(pd, data, "OX_RYO", "Shape35", 1);
        // レスピレーター
        IkenshoCommon.addCheck(pd, data, "JINKOU_KOKYU", "Shape36", 1);
        // 気管切開の処置
        IkenshoCommon.addCheck(pd, data, "KKN_SEK_SHOCHI", "Shape37", 1);
        // 疼痛の看護
        IkenshoCommon.addCheck(pd, data, "TOUTU", "Shape38", 1);
        // 経管栄養
        IkenshoCommon.addCheck(pd, data, "KEKN_EIYOU", "Shape39", 1);
        // モニター測定
        IkenshoCommon.addCheck(pd, data, "MONITOR", "Shape40", 1);
        // 褥瘡の処置
        IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI", "Shape41", 1);
        // カテーテル
        IkenshoCommon.addCheck(pd, data, "CATHETER", "Shape42", 1);

        // 寝たきり度
        IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {
                "Shape43", "Shape44", "Shape45", "Shape46", "Shape47",
                "Shape48", "Shape49", "Shape50", "Shape51" }, -1);
        // 自立度
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape52", "Shape53", "Shape54", "Shape55", "Shape56",
                "Shape57", "Shape58", "Shape59" }, -1);
        // 短期記憶
        IkenshoCommon.addSelection(pd, data, "TANKI_KIOKU", new String[] {
                "Shape60", "Shape61" }, -1);
        // 認知能力
        IkenshoCommon.addSelection(pd, data, "NINCHI", new String[] {
                "Shape62", "Shape63", "Shape64", "Shape65" }, -1);
        // 伝達能力
        IkenshoCommon.addSelection(pd, data, "DENTATU", new String[] {
                "Shape66", "Shape67", "Shape68", "Shape69" }, -1);
        // 食事
        IkenshoCommon.addSelection(pd, data, "SHOKUJI", new String[] {
                "Shape70", "Shape71" }, -1);

        if (((Integer) VRBindPathParser.get("MONDAI_FLAG", data)).intValue() == 1) {
            boolean problemAction = false;
            // 幻視・幻聴
            problemAction |= IkenshoCommon.addCheck(pd, data, "GNS_GNC",
                    "Shape74", 1);
            // 妄想
            problemAction |= IkenshoCommon.addCheck(pd, data, "MOUSOU",
                    "Shape75", 1);
            // 昼夜逆転
            problemAction |= IkenshoCommon.addCheck(pd, data, "CHUYA",
                    "Shape76", 1);
            // 暴言
            problemAction |= IkenshoCommon.addCheck(pd, data, "BOUGEN",
                    "Shape77", 1);
            // 暴行
            problemAction |= IkenshoCommon.addCheck(pd, data, "BOUKOU",
                    "Shape78", 1);
            // 抵抗
            problemAction |= IkenshoCommon.addCheck(pd, data, "TEIKOU",
                    "Shape79", 1);
            // 徘徊
            problemAction |= IkenshoCommon.addCheck(pd, data, "HAIKAI",
                    "Shape80", 1);
            // 火の不始末
            problemAction |= IkenshoCommon.addCheck(pd, data, "FUSIMATU",
                    "Shape81", 1);
            // 不潔
            problemAction |= IkenshoCommon.addCheck(pd, data, "FUKETU",
                    "Shape82", 1);
            // 異食行動
            problemAction |= IkenshoCommon.addCheck(pd, data, "ISHOKU",
                    "Shape83", 1);
            // 性的問題行動
            problemAction |= IkenshoCommon.addCheck(pd, data, "SEITEKI_MONDAI",
                    "Shape84", 1);
            // その他
            if (IkenshoCommon.addCheck(pd, data, "MONDAI_OTHER", "Shape85", 1)) {
                // その他名称
                IkenshoCommon.addString(pd, data, "MONDAI_OTHER_NM",
                        "Grid13.h2.w3");
                problemAction = true;
            }

            if (problemAction) {
                pd.addAttribute("Shape73", "Visible", "FALSE");
            } else {
                pd.addAttribute("Shape72", "Visible", "FALSE");
            }
        } else {
            pd.addAttribute("Shape74", "Visible", "FALSE");
            pd.addAttribute("Shape75", "Visible", "FALSE");
            pd.addAttribute("Shape76", "Visible", "FALSE");
            pd.addAttribute("Shape77", "Visible", "FALSE");
            pd.addAttribute("Shape78", "Visible", "FALSE");
            pd.addAttribute("Shape79", "Visible", "FALSE");
            pd.addAttribute("Shape80", "Visible", "FALSE");
            pd.addAttribute("Shape81", "Visible", "FALSE");
            pd.addAttribute("Shape82", "Visible", "FALSE");
            pd.addAttribute("Shape83", "Visible", "FALSE");
            pd.addAttribute("Shape84", "Visible", "FALSE");
            pd.addAttribute("Shape85", "Visible", "FALSE");
            pd.addAttribute("Shape72", "Visible", "FALSE");
        }

        pd.endPageEdit();

    }

    /**
     * 意見書の2ページ目を印刷します。
     * 
     * @param pd XML生成クラス
     * @param formatName フォーマットID
     * @param data データソース
     * @param printDate 出力日
     * @param imageBytes 画像バイトデータ
     * @throws Exception 処理例外
     */
    public static void printIkensho2(ACChotarouXMLWriter pd, String formatName,
            VRMap data, Date printDate, byte[] imageBytes) throws Exception {

        pd.beginPageEdit(formatName);

        printIkenshoHeader(pd, data, printDate);

        if (((Integer) VRBindPathParser.get("HEADER_OUTPUT_UMU2", data))
                .intValue() == 1) {
            // 氏名
            IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w1");
            // 年
            IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w3");
            // 記入日
            IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 5, 1,
                    "日)");
        } else {
            IkenshoCommon.addString(pd, "Grid2.h1.w4", "");
            IkenshoCommon.addString(pd, "Grid2.h1.w7", "");
            IkenshoCommon.addString(pd, "Grid2.h1.w9", "");
            IkenshoCommon.addString(pd, "Grid2.h1.w11", "");
        }

        // 精神・神経症状の有無
        switch (((Integer) VRBindPathParser.get("SEISIN", data)).intValue()) {
        case 1:
            IkenshoCommon.addString(pd, data, "SEISIN_NM", "Grid1.h2.w10");
            pd.addAttribute("Shape3", "Visible", "FALSE");

            // 専門医受信の有無
            if (IkenshoCommon.addSelection(pd, data, "SENMONI", new String[] {
                    "Shape23", "Shape4" }, -1)) {
                // 専門医
                IkenshoCommon.addString(pd, data, "SENMONI_NM", "Grid1.h3.w9");
            }

            break;
        case 2:
            pd.addAttribute("Shape22", "Visible", "FALSE");
            pd.addAttribute("Shape23", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
            break;
        default:
            pd.addAttribute("Shape22", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");

            pd.addAttribute("Shape23", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }

        // 精神・神経
        // 利き腕
        IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {
                "Shape5", "Shape21" }, -1);
        // 体重
        IkenshoCommon.addString(pd, data, "WEIGHT", "Grid4.h1.w2");
        // 身長
        IkenshoCommon.addString(pd, data, "HEIGHT", "Grid4.h1.w4");

        // 四肢欠損
        if (IkenshoCommon.addCheck(pd, data, "SISIKESSON", "Shape24", 1)) {
            // 四肢欠損部位
            IkenshoCommon.addString(pd, data, "SISIKESSON_BUI", "Grid6.h1.w3");
            // 四肢欠損程度
            IkenshoCommon.addSelection(pd, data, "SISIKESSON_TEIDO",
                    new String[] { "Shape20", "Shape6", "Shape7" }, -1);
        } else {
            pd.addAttribute("Shape20", "Visible", "FALSE");
            pd.addAttribute("Shape6", "Visible", "FALSE");
            pd.addAttribute("Shape7", "Visible", "FALSE");
        }

        // 麻痺
        if (IkenshoCommon.addCheck(pd, data, "MAHI", "Shape25", 1)) {
            // 麻痺部位
            IkenshoCommon.addString(pd, data, "MAHI_BUI", "Grid6.h2.w3");
            // 麻痺程度
            IkenshoCommon.addSelection(pd, data, "MAHI_TEIDO", new String[] {
                    "Shape8", "Shape9", "Shape10" }, -1);
        } else {
            pd.addAttribute("Shape8", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape10", "Visible", "FALSE");
        }

        // 筋力の低下
        if (IkenshoCommon.addCheck(pd, data, "KINRYOKU_TEIKA", "Shape26", 1)) {
            // 筋力の低下部位
            IkenshoCommon.addString(pd, data, "KINRYOKU_TEIKA_BUI",
                    "Grid6.h3.w3");
            // 筋力の低下程度
            IkenshoCommon.addSelection(pd, data, "KINRYOKU_TEIKA_TEIDO",
                    new String[] { "Shape11", "Shape12", "Shape13" }, -1);
        } else {
            pd.addAttribute("Shape11", "Visible", "FALSE");
            pd.addAttribute("Shape12", "Visible", "FALSE");
            pd.addAttribute("Shape13", "Visible", "FALSE");
        }

        // 褥瘡
        if (IkenshoCommon.addCheck(pd, data, "JOKUSOU", "Shape27", 1)) {
            // 褥瘡部位
            IkenshoCommon.addString(pd, data, "JOKUSOU_BUI", "Grid6.h4.w3");
            // 褥瘡程度
            IkenshoCommon.addSelection(pd, data, "JOKUSOU_TEIDO", new String[] {
                    "Shape14", "Shape15", "Shape16" }, -1);
        } else {
            pd.addAttribute("Shape14", "Visible", "FALSE");
            pd.addAttribute("Shape15", "Visible", "FALSE");
            pd.addAttribute("Shape16", "Visible", "FALSE");
        }

        // その他の皮膚疾患
        if (IkenshoCommon.addCheck(pd, data, "HIFUSIKKAN", "Shape28", 1)) {
            // その他の皮膚疾患部位
            IkenshoCommon.addString(pd, data, "HIFUSIKKAN_BUI", "Grid6.h5.w3");
            // その他の皮膚疾患程度
            IkenshoCommon.addSelection(pd, data, "HIFUSIKKAN_TEIDO",
                    new String[] { "Shape17", "Shape18", "Shape19" }, -1);
        } else {
            pd.addAttribute("Shape17", "Visible", "FALSE");
            pd.addAttribute("Shape18", "Visible", "FALSE");
            pd.addAttribute("Shape19", "Visible", "FALSE");
        }

        if (((Integer) VRBindPathParser.get("KOUSHU_FLAG", data)).intValue() == 1) {
            boolean checkFlag = false;
            // 肩関節拘縮右
            checkFlag |= IkenshoCommon.addCheck(pd, data, "KATA_KOUSHU_MIGI",
                    "Shape31", 1);
            // 肩関節拘縮左
            checkFlag |= IkenshoCommon.addCheck(pd, data, "KATA_KOUSHU_HIDARI",
                    "Shape32", 1);
            // 肘関節拘縮右
            checkFlag |= IkenshoCommon.addCheck(pd, data, "HIJI_KOUSHU_MIGI",
                    "Shape33", 1);
            // 肘関節拘縮左
            checkFlag |= IkenshoCommon.addCheck(pd, data, "HIJI_KOUSHU_HIDARI",
                    "Shape34", 1);
            // 股関節拘縮右
            checkFlag |= IkenshoCommon.addCheck(pd, data, "MATA_KOUSHU_MIGI",
                    "Shape39", 1);
            // 股関節拘縮左
            checkFlag |= IkenshoCommon.addCheck(pd, data, "MATA_KOUSHU_HIDARI",
                    "Shape40", 1);
            // 膝関節拘縮右
            checkFlag |= IkenshoCommon.addCheck(pd, data, "HIZA_KOUSHU_MIGI",
                    "Shape41", 1);
            // 膝関節拘縮左
            checkFlag |= IkenshoCommon.addCheck(pd, data, "HIZA_KOUSHU_HIDARI",
                    "Shape42", 1);
            // 関節の拘縮
            if (!checkFlag) {
                pd.addAttribute("Shape29", "Visible", "FALSE");
            }
        } else {
            pd.addAttribute("Shape31", "Visible", "FALSE");
            pd.addAttribute("Shape32", "Visible", "FALSE");
            pd.addAttribute("Shape33", "Visible", "FALSE");
            pd.addAttribute("Shape34", "Visible", "FALSE");
            pd.addAttribute("Shape39", "Visible", "FALSE");
            pd.addAttribute("Shape40", "Visible", "FALSE");
            pd.addAttribute("Shape41", "Visible", "FALSE");
            pd.addAttribute("Shape42", "Visible", "FALSE");
            pd.addAttribute("Shape29", "Visible", "FALSE");
        }

        if (((Integer) VRBindPathParser.get("SICCHOU_FLAG", data)).intValue() == 1) {
            boolean checkFlag = false;
            // 上肢失調右
            checkFlag |= IkenshoCommon.addCheck(pd, data, "JOUSI_SICCHOU_MIGI",
                    "Shape35", 1);
            // 上肢失調左
            checkFlag |= IkenshoCommon.addCheck(pd, data,
                    "JOUSI_SICCHOU_HIDARI", "Shape36", 1);
            // 体幹失調右
            checkFlag |= IkenshoCommon.addCheck(pd, data,
                    "TAIKAN_SICCHOU_MIGI", "Shape43", 1);
            // 体幹失調左
            checkFlag |= IkenshoCommon.addCheck(pd, data,
                    "TAIKAN_SICCHOU_HIDARI", "Shape44", 1);
            // 下肢失調右
            checkFlag |= IkenshoCommon.addCheck(pd, data, "KASI_SICCHOU_MIGI",
                    "Shape37", 1);
            // 下肢失調左
            checkFlag |= IkenshoCommon.addCheck(pd, data,
                    "KASI_SICCHOU_HIDARI", "Shape38", 1);
            // 失調・不随意運動
            if (!checkFlag) {
                pd.addAttribute("Shape30", "Visible", "FALSE");
            }
        } else {
            pd.addAttribute("Shape35", "Visible", "FALSE");
            pd.addAttribute("Shape36", "Visible", "FALSE");
            pd.addAttribute("Shape43", "Visible", "FALSE");
            pd.addAttribute("Shape44", "Visible", "FALSE");
            pd.addAttribute("Shape37", "Visible", "FALSE");
            pd.addAttribute("Shape38", "Visible", "FALSE");
            pd.addAttribute("Shape30", "Visible", "FALSE");
        }

        if (imageBytes != null) {
            pd.addDataDirect("Label12", "<Image>" + VRBase64.encode(imageBytes)
                    + "</Image>");
        }

        ArrayList words = new ArrayList();
        // 尿失禁
        addSickStateCheck(pd, data, "NYOUSIKKIN", "Shape45",
                "NYOUSIKKIN_TAISHO_HOUSIN", words);
        // 転倒・骨折
        addSickStateCheck(pd, data, "TENTOU_KOSSETU", "Shape47",
                "TENTOU_KOSSETU_TAISHO_HOUSIN", words);
        // 徘徊可能性
        addSickStateCheck(pd, data, "HAIKAI_KANOUSEI", "Shape49",
                "HAIKAI_KANOUSEI_TAISHO_HOUSIN", words);
        // 褥瘡可能性
        addSickStateCheck(pd, data, "JOKUSOU_KANOUSEI", "Shape51",
                "JOKUSOU_KANOUSEI_TAISHO_HOUSIN", words);
        // 嚥下性肺炎
        addSickStateCheck(pd, data, "ENGESEIHAIEN", "Shape53",
                "ENGESEIHAIEN_TAISHO_HOUSIN", words);
        // 腸閉塞
        addSickStateCheck(pd, data, "CHOUHEISOKU", "Shape54",
                "CHOUHEISOKU_TAISHO_HOUSIN", words);
        // 易感染性
        addSickStateCheck(pd, data, "EKIKANKANSEN", "Shape55",
                "EKIKANKANSEN_TAISHO_HOUSIN", words);
        // 心肺機能の低下
        addSickStateCheck(pd, data, "SINPAIKINOUTEIKA", "Shape46",
                "SINPAIKINOUTEIKA_TAISHO_HOUSIN", words);
        // 痛み
        addSickStateCheck(pd, data, "ITAMI", "Shape48", "ITAMI_TAISHO_HOUSIN",
                words);
        // 脱水
        addSickStateCheck(pd, data, "DASSUI", "Shape50",
                "DASSUI_TAISHO_HOUSIN", words);
        // 病態他
        if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "Shape52", 1)) {
            // 病態他名
            IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "Label8");
            addSickStateCheck(data, "BYOUTAITA_TAISHO_HOUSIN", words);
        }

        StringBuffer sb;
        if (words.size() > 0) {
            // 病態対処方針
            // 病態を「、」で連結し、病態単位で折り返しチェック。末尾は「。」補完の3行出力。
            // 病態単位の折り返しで4行以上となるデータの場合、文字単位で連結して表示可能なところまで。
            ArrayList lines = new ArrayList();

            int inlineSize = 0;
            int linePos = 1;
            sb = new StringBuffer();
            int end = words.size() - 1;
            for (int i = 0; i < end; i++) {
                String text = String.valueOf(words.get(i));

                StringBuffer line = new StringBuffer();
                line.append(text);

                int wordSize = 0;
                char c = text.charAt(text.length() - 1);
                if ((c != '。') && (c != '、')) {
                    line.append("、");
                    wordSize += 2;
                }
                wordSize += text.getBytes().length;
                inlineSize += wordSize;

                if (inlineSize > 96) {
                    // 改行
                    lines.add(sb.toString());
                    linePos++;
                    inlineSize = wordSize;
                    sb = new StringBuffer();
                }
                sb.append(line.toString());
            }
            // 末尾追加
            String text = String.valueOf(words.get(end));

            StringBuffer lineBuff = new StringBuffer();
            lineBuff.append(text);

            char c = text.charAt(text.length() - 1);
            if ((c != '。') && (c != '、')) {
                lineBuff.append("。");
                inlineSize += 2;
            }
            inlineSize += text.getBytes().length;

            if (inlineSize > 96) {
                // 改行
                lines.add(sb.toString());
                linePos++;
                sb = new StringBuffer();
            }
            sb.append(lineBuff.toString());
            lines.add(sb.toString());

            if (linePos < 4) {
                // 病態単位の折り返しでも範囲内
                for (int i = 0; i < lines.size(); i++) {
                    IkenshoCommon.addString(pd, "Grid10.h" + (i + 1) + ".w4",
                            lines.get(i));
                }
            } else {
                // 病態単位の折り返しでは範囲外→全連結
                sb = new StringBuffer();
                linePos = 1;

                String line = String.valueOf(lines.get(0));
                sb.append(line);
                inlineSize = line.getBytes().length;

                end = lines.size();
                for (int i = 1; i < end; i++) {
                    line = String.valueOf(lines.get(i));
                    int dataSize = line.getBytes().length;
                    if (inlineSize + dataSize > 96) {
                        // 改行

                        int jEnd = line.length();
                        int nextLinePos = jEnd;
                        for (int j = 0; j < jEnd; j++) {
                            if (inlineSize >= 96) {
                                // 行終了チェック
                                if (inlineSize == 96) {
                                    String str = line.substring(j, j + 1);
                                    if (str.getBytes().length == 1) {
                                        // 次が1バイト文字ならばぎりぎり許す
                                        sb.append(str);
                                        nextLinePos = j + 1;
                                    } else {
                                        nextLinePos = j;
                                    }
                                } else {
                                    nextLinePos = j;
                                }
                                break;
                            }

                            String str = line.substring(j, j + 1);
                            sb.append(str);
                            inlineSize += str.getBytes().length;
                        }
                        IkenshoCommon.addString(pd, "Grid10.h" + (linePos++)
                                + ".w4", sb.toString());

                        if (linePos >= 4) {
                            break;
                        }

                        sb = new StringBuffer();
                        // 残りの文字は次行にまわす
                        if (jEnd >= nextLinePos) {
                            String str = line.substring(nextLinePos, jEnd);
                            sb.append(str);
                            inlineSize = str.getBytes().length;
                        } else {
                            inlineSize = 0;
                        }
                    } else {
                        inlineSize += dataSize;
                        sb.append(line);
                    }
                }
                if (linePos < 4) {
                    // 末尾も追加
                    IkenshoCommon.addString(pd, "Grid10.h" + (linePos) + ".w4",
                            sb.toString());
                }

            }
        }

        // 訪問診療
        IkenshoCommon.addCheck(pd, data, "HOUMON_SINRYOU", "Shape56", 1);
        // 訪問診療下線
        IkenshoCommon.addCheck(pd, data, "HOUMON_SINRYOU_UL", "Shape66", 1);
        // 訪問看護
        IkenshoCommon.addCheck(pd, data, "HOUMON_KANGO", "Shape57", 1);
        // 訪問看護下線
        IkenshoCommon.addCheck(pd, data, "HOUMON_KANGO_UL", "Shape67", 1);
        // 訪問リハビリ
        IkenshoCommon.addCheck(pd, data, "HOUMON_REHA", "Shape58", 1);
        // 訪問リハビリ下線
        IkenshoCommon.addCheck(pd, data, "HOUMON_REHA_UL", "Shape68", 1);
        // 通所リハビリ
        IkenshoCommon.addCheck(pd, data, "TUUSHO_REHA", "Shape59", 1);
        // 通所リハビリ下線
        IkenshoCommon.addCheck(pd, data, "TUUSHO_REHA_UL", "Shape69", 1);
        // 短期入所診療介護
        IkenshoCommon.addCheck(pd, data, "TANKI_NYUSHO_RYOUYOU", "Shape60", 1);
        // 短期入所診療介護下線
        IkenshoCommon.addCheck(pd, data, "TANKI_NYUSHO_RYOUYOU_UL", "Shape71",
                1);
        // 訪問歯科診療
        IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_SINRYOU", "Shape61", 1);
        // 訪問歯科診療下線
        IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_SINRYOU_UL", "Shape70", 1);
        // 訪問歯科衛生指導
        IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_EISEISIDOU", "Shape62", 1);
        // 訪問歯科衛生指導下線
        IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_EISEISIDOU_UL", "Shape72",
                1);
        // 訪問薬剤管理指導
        IkenshoCommon.addCheck(pd, data, "HOUMONYAKUZAI_KANRISIDOU", "Shape63",
                1);
        // 訪問薬剤管理指導下線
        IkenshoCommon.addCheck(pd, data, "HOUMONYAKUZAI_KANRISIDOU_UL",
                "Shape73", 1);
        // 訪問栄養食事指導
        IkenshoCommon.addCheck(pd, data, "HOUMONEIYOU_SHOKUJISIDOU", "Shape64",
                1);
        // 訪問栄養食事指導下線
        IkenshoCommon.addCheck(pd, data, "HOUMONEIYOU_SHOKUJISIDOU_UL",
                "Shape74", 1);
        // 医学的管理他
        if (IkenshoCommon.addCheck(pd, data, "IGAKUTEKIKANRI_OTHER", "Shape65",
                1)) {
            // 医学的管理他名称
            IkenshoCommon.addString(pd, data, "IGAKUTEKIKANRI_OTHER_NM",
                    "Grid12.h2.w7");
        }
        // 医学的管理他下線
        IkenshoCommon.addCheck(pd, data, "IGAKUTEKIKANRI_OTHER_UL", "Shape75",
                1);

        // 血圧
        IkenshoCommon.addSelection(pd, data, "KETUATU", new String[] {
                "Shape76", "Shape77" }, -1, "KETUATU_RYUIJIKOU",
                "Grid14.h1.w7", 2);
        // 嚥下
        IkenshoCommon.addSelection(pd, data, "ENGE", new String[] { "Shape78",
                "Shape79" }, -1, "ENGE_RYUIJIKOU", "Grid14.h2.w7", 2);
        // 摂食
        IkenshoCommon.addSelection(pd, data, "SESHOKU", new String[] {
                "Shape80", "Shape81" }, -1, "SESHOKU_RYUIJIKOU",
                "Grid14.h3.w7", 2);
        // 移動
        IkenshoCommon.addSelection(pd, data, "IDOU", new String[] { "Shape82",
                "Shape83" }, -1, "IDOU_RYUIJIKOU", "Grid14.h4.w7", 2);
        // その他留意事項
        IkenshoCommon.addString(pd, data, "KAIGO_OTHER", "Grid14.h5.w3");

        // 感染症
        IkenshoCommon.addSelection(pd, data, "KANSENSHOU", new String[] {
                "Shape86", "Shape84", "Shape85" }, -1, "KANSENSHOU_NM",
                "Grid16.h1.w5", 1);

        sb = new StringBuffer();
        String institutionGrid;
        String hasePoint = String.valueOf(VRBindPathParser.get("HASE_SCORE",
                data));
        String haseDate = String.valueOf(VRBindPathParser.get("HASE_SCR_DT",
                data));
        String hasePointPreview = String.valueOf(VRBindPathParser.get(
                "P_HASE_SCORE", data));
        String haseDatePreview = String.valueOf(VRBindPathParser.get(
                "P_HASE_SCR_DT", data));
        if (!("".equals(hasePoint) && "0000年00月".equals(haseDate)
                && "".equals(hasePointPreview) && "0000年00月"
                .equals(haseDatePreview))) {
            // 長谷川式点数
            IkenshoCommon.addString(pd, data, "HASE_SCORE", "Grid18.h1.w3");
            // 長谷川式日付
            addHasegawaDate(pd, data, "HASE_SCR_DT", "Grid18.h1.w", 6);
            // 長谷川式前回点数
            IkenshoCommon.addString(pd, data, "P_HASE_SCORE", "Grid18.h1.w17");
            // 長谷川式日付
            addHasegawaDate(pd, data, "P_HASE_SCR_DT", "Grid18.h1.w", 21);

            // 1行目の施設選択を隠す
            pd.addAttribute("Grid20", "Visible", "FALSE");
            institutionGrid = "Grid19";
            sb.append(IkenshoConstants.LINE_SEPARATOR);
        } else {
            institutionGrid = "Grid20";
            pd.addAttribute("Grid19", "Visible", "FALSE");
            pd.addAttribute("Grid18", "Visible", "FALSE");
        }

        String institution1 = String.valueOf(VRBindPathParser.get(
                "INST_SEL_PR1", data));
        if (!("".equals(institution1))) {
            // 施設選択
            IkenshoCommon.addString(pd, data, "INST_SEL_PR1", institutionGrid
                    + ".h1.w6");
            IkenshoCommon.addString(pd, data, "INST_SEL_PR2", institutionGrid
                    + ".h1.w10");
            sb.append(IkenshoConstants.LINE_SEPARATOR);
        } else {
            pd.addAttribute(institutionGrid, "Visible", "FALSE");
        }
        // 特記事項
        sb.append(String.valueOf(VRBindPathParser.get("IKN_TOKKI", data)));
        IkenshoCommon.addString(pd, "Grid17.h1.w1", sb.toString());

        pd.endPageEdit();
    }

    /**
     * 長谷川式点数の日付を出力します。
     * 
     * @param pd XML生成クラス
     * @param data データソース
     * @param key キー
     * @param tag 出力タグ
     * @param wPos インデックス
     * @throws Exception 処理例外
     */
    protected static void addHasegawaDate(ACChotarouXMLWriter pd, VRMap data,
            String key, String tag, int wPos) throws Exception {
        Object obj = VRBindPathParser.get(key, data);
        if (obj != null) {
            String text = String.valueOf(obj);
            if ((text.length() >= 8) && (text.indexOf("0000年") < 0)) {
                Date date = VRDateParser.parse(text.replaceAll("00月", "01月")
                        + "01日");
                //[ID:0000515][Tozo TANAKA] 2009/09/16 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   
//                IkenshoCommon.addString(pd, "Grid18.h1.w" + wPos, VRDateParser
//                        .format(date, "ggg"));
//                IkenshoCommon.addString(pd, "Grid18.h1.w" + (wPos + 2),
//                        VRDateParser.format(date, "ee"));
//                if (text.indexOf("00月") < 0) {
//                    IkenshoCommon.addString(pd, "Grid18.h1.w" + (wPos + 5),
//                            text.substring(5, 7));
//                }
                IkenshoCommon.addString(pd, tag + wPos, VRDateParser
                        .format(date, "ggg"));
                IkenshoCommon.addString(pd, tag + (wPos + 2),
                        VRDateParser.format(date, "ee"));
                if (text.indexOf("00月") < 0) {
                    IkenshoCommon.addString(pd, tag + (wPos + 5),
                            text.substring(5, 7));
                }
                //[ID:0000515][Tozo TANAKA] 2009/09/16 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   
            }
        }
    }

    /**
     * 傷病状態のチェックを追加します。
     * 
     * @param pd XML生成クラス
     * @param data データソース
     * @param shapeKey チェックキー
     * @param shape チェックシェイプ
     * @param careKey 対処方針キー
     * @param words 追加先
     * @throws Exception 処理例外
     */
    protected static void addSickStateCheck(ACChotarouXMLWriter pd, VRMap data,
            String shapeKey, String shape, String careKey, ArrayList words)
            throws Exception {
        if (IkenshoCommon.addCheck(pd, data, shapeKey, shape, 1)) {
            addSickStateCheck(data, careKey, words);
        }
    }

    /**
     * 傷病状態のチェックを追加します。
     * 
     * @param data データソース
     * @param careKey 対処方針キー
     * @param words 追加先
     * @throws Exception 処理例外
     */
    protected static void addSickStateCheck(VRMap data, String careKey,
            ArrayList words) throws Exception {
        Object obj = VRBindPathParser.get(careKey, data);
        if (IkenshoCommon.isNullText(obj)) {
            return;
        }
        words.add(String.valueOf(obj));
    }

    /**
     * 意見書帳票定義1ページ目のファイルパスを返します。
     * 
     * @return 意見書帳票定義1ページ目のファイルパス
     */
    protected String getIkenshoFormatFilePath1() {
        return "Ikensho1.xml";
//        return ACFrame.getInstance().getExeFolderPath()
//                + IkenshoConstants.FILE_SEPARATOR + "format"
//                + IkenshoConstants.FILE_SEPARATOR + "Ikensho1.xml";
    }

    /**
     * 意見書帳票定義1ページ目のファイルパスを返します。
     * 
     * @return 意見書帳票定義1ページ目のファイルパス
     */
    protected String getIkenshoFormatFilePath2() {
        return "Ikensho2.xml";
//        return ACFrame.getInstance().getExeFolderPath()
//                + IkenshoConstants.FILE_SEPARATOR + "format"
//                + IkenshoConstants.FILE_SEPARATOR + "Ikensho2.xml";
    }

    /**
     * 印刷処理を行います。
     * 
     * @return 終了してよいか
     */
    protected boolean print() {
        switch (billPrintDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
            break;
        case IkenshoEraDateTextField.STATE_FUTURE:
            ACMessageBox.showExclamation("未来の日付です。");
            billPrintDate.requestChildFocus();
            return false;
        default:
            ACMessageBox.showExclamation("出力日付の入力に誤りがあります。");
            billPrintDate.requestChildFocus();
            return false;
        }

        switch (billDetailPrintDate.getInputStatus()) {
        case IkenshoEraDateTextField.STATE_EMPTY:
        case IkenshoEraDateTextField.STATE_VALID:
        case IkenshoEraDateTextField.STATE_FUTURE:
            break;
        default:
            ACMessageBox.showExclamation("日付に誤りがあります。");
            billDetailPrintDate.requestChildFocus();
            return false;
        }

        Date printDate;
        // 発行済みならば発行日を、未発行ならば現在日時を使用する。
        if (printParameter.isCsvSubmited()) {
            printDate = printParameter.getCsvOutputTime();
        } else {
            if (csvSubmit.isEnabled() && csvSubmit.isSelected()) {
                printDate = new Date();
            } else {
                printDate = null;
            }
        }

        try {
            contents.applySource();

            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
            pd.beginPrintEdit();

            ACChotarouXMLUtilities.addFormat(pd, "page1", getIkenshoFormatFilePath1());
            ACChotarouXMLUtilities.addFormat(pd, "page2", getIkenshoFormatFilePath2());
//            pd.addFormat("page1", getIkenshoFormatFilePath1());
//            pd.addFormat("page2", getIkenshoFormatFilePath2());


            callPrintIkensho(pd, "page1", source, printDate);
            if (picture != null) {
                callPrintIkensho2(pd, "page2", source, printDate, picture
                        .getImageByteArray());
            } else {
                callPrintIkensho2(pd, "page2", source, printDate, null);
            }

            if (billPrint.isEnabled() && billPrint.isSelected()) {
                Date billPrint = billPrintDate.getDate();
                Date billDetailPrint = billDetailPrintDate.getDate();
                String billPrintE = "";
                String billPrintY = "";
                String billPrintM = "";
                String billPrintD = "";
                String billDetailPrintE = "";
                String billDetailPrintY = "";
                String billDetailPrintM = "";
                if (billPrint != null) {
                    billPrintE = VRDateParser.format(billPrint, "ggg");
                    billPrintY = VRDateParser.format(billPrint, "e");
                    billPrintM = VRDateParser.format(billPrint, "M");
                    billPrintD = VRDateParser.format(billPrint, "d");
                }
                if (billDetailPrint != null) {
                    billDetailPrintE = VRDateParser.format(billDetailPrint,
                            "ggg");
                    billDetailPrintY = VRDateParser
                            .format(billDetailPrint, "e");
                    billDetailPrintM = VRDateParser
                            .format(billDetailPrint, "M");
                }

                // 請求書明細も出力
                switch (outputPattern) {
                case 1: // 意見書作成料・検査料(1枚)
                    IkenshoSeikyuIchiran
                            .setSyosaiPrtData(
                                    pd,
                                    source,
                                    IkenshoSeikyuIchiran.BOTH_PRINT,
                                    printToCreateCost.isSelected(),
                                    billPrintE,
                                    billPrintY,
                                    billPrintM,
                                    billPrintD,
                                    billPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    billDetailPrintE,
                                    billDetailPrintY,
                                    billDetailPrintM,
                                    billDetailPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    VRBindPathParser.get("INSURER_NO", source)
                                            .toString(),getFormatType());
                    break;
                case 2: // 意見書作成料(1枚)・検査料(1枚)
                    IkenshoSeikyuIchiran
                            .setSyosaiPrtData(
                                    pd,
                                    source,
                                    IkenshoSeikyuIchiran.IKEN_PRINT,
                                    printToCreateCost.isSelected(),
                                    billPrintE,
                                    billPrintY,
                                    billPrintM,
                                    billPrintD,
                                    billPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    billDetailPrintE,
                                    billDetailPrintY,
                                    billDetailPrintM,
                                    billDetailPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    VRBindPathParser.get("INSURER_NO", source)
                                            .toString(),getFormatType());
                    IkenshoSeikyuIchiran
                            .setSyosaiPrtData(
                                    pd,
                                    source,
                                    IkenshoSeikyuIchiran.KENSA_PRINT,
                                    printToCheckCost.isSelected(),
                                    billPrintE,
                                    billPrintY,
                                    billPrintM,
                                    billPrintD,
                                    billPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    billDetailPrintE,
                                    billDetailPrintY,
                                    billDetailPrintM,
                                    billDetailPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    VRBindPathParser.get("INSURER_NO", source)
                                            .toString(),getFormatType());
                    break;
                case 3: // 意見書作成料のみ
                    IkenshoSeikyuIchiran
                            .setSyosaiPrtData(
                                    pd,
                                    source,
                                    IkenshoSeikyuIchiran.IKEN_PRINT,
                                    printToCreateCost.isSelected(),
                                    billPrintE,
                                    billPrintY,
                                    billPrintM,
                                    billPrintD,
                                    billPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    billDetailPrintE,
                                    billDetailPrintY,
                                    billDetailPrintM,
                                    billDetailPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    VRBindPathParser.get("INSURER_NO", source)
                                            .toString(),getFormatType());
                    break;
                case 4: // 検査料のみ
                    IkenshoSeikyuIchiran
                            .setSyosaiPrtData(
                                    pd,
                                    source,
                                    IkenshoSeikyuIchiran.KENSA_PRINT,
                                    printToCheckCost.isSelected(),
                                    billPrintE,
                                    billPrintY,
                                    billPrintM,
                                    billPrintD,
                                    billPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    billDetailPrintE,
                                    billDetailPrintY,
                                    billDetailPrintM,
                                    billDetailPrintDate.getInputStatus() == IkenshoEraDateTextField.STATE_VALID,
                                    VRBindPathParser.get("INSURER_NO", source)
                                            .toString(),getFormatType());
                    break;
                }
            }

            pd.endPrintEdit();

            String path = IkenshoCommon.writePDF(pd);
            if((path==null)||"".equals(path)){
                return false;
            }
            
            ACMessageBox.show("医師氏名欄は本人による自筆が必要です。\n印刷後の用紙に署名してください。");
            IkenshoCommon.openPDF(path);

            printed = true;

            if (csvSubmit.isEnabled()) {
                if (csvSubmit.isSelected()) {
                    if (ACMessageBox.show("CSVファイルの出力対象にしてもよろしいですか？",
                            ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                            ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                        ACMessageBox
                                .show("出力対象に設定します。\n\nCSVファイルの出力は[メインメニュー]-[その他の機能]-[「主治医意見書」「医師意見書」CSVファイル出力]\nにて行ってください。");
                        printParameter
                                .setCsvOutputType(IkenshoIkenshoInfoPrintParameter.CSV_OUTPUT_TYPE_TARGET);
                    } else {
                        printDate = null;
                    }
                    printParameter.setTypeChanged(true);
                }
                printParameter.setCsvOutputTime(printDate);
            }

            if (billPrint.isEnabled() && billPrint.isSelected()) {
                if (printParameter.getHakkouType() != IkenshoIkenshoInfoPrintParameter.BILL_HAKKOU_TYPE_PRINTED) {
                    if (ACMessageBox.show("請求書を発行済みにしてもよろしいですか？",
                            ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                            ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                        // 発行済みにする
                        printParameter
                                .setHakkouType(IkenshoIkenshoInfoPrintParameter.BILL_HAKKOU_TYPE_PRINTED);
                    } else {
                        // 発行済みにしない
                        if (printParameter.getHakkouType() != IkenshoIkenshoInfoPrintParameter.BILL_HAKKOU_TYPE_RESERVE) {
                            // 発行状態が保留の場合は保留を維持する
                            printParameter
                                    .setHakkouType(IkenshoIkenshoInfoPrintParameter.BILL_HAKKOU_TYPE_NEVER_PRINT);
                        }
                    }
                    printParameter.setTypeChanged(true);
                }
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

        return true;
    }

    /**
     * 位置を初期化します。
     */
    private void init() {
        // ウィンドウのサイズ
        //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//        setSize(new Dimension(700, 420));
        setPackSize();
        //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        
        
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

    protected void jbInit() throws Exception {
        contentsLayout.setFitVLast(true);
        contentsLayout.setFitHLast(true);
        contents.setLayout(contentsLayout);
        billPrintPanelLayout.setFitHLast(true);
        billPrintPanelLayout.setAutoWrap(false);
        billPrintPanelLayout.setFitVLast(true);
        billPrintPanel.setLayout(billPrintPanelLayout);
        billPanel.setLayout(billPanelLayout);
        billPatternGroup.setLayout(billPatternGroupLayout);
        billPrintDateGroup.setLayout(new BorderLayout());
        billPrintDatesPanel.setLayout(billPrintDatesPanelLayout);
        billPrintDateButtons.setLayout(new VRLayout());
        billPrintGroup.setLayout(new BorderLayout());
        csvGroup.setLayout(new BorderLayout());
        toBillGroup.setLayout(toBillGroupLayout);
        printOptionGroupLayout.setFitHLast(true);
        printOptionGroupLayout.setFitVLast(true);
        printOptionGroupLayout.setAutoWrap(false);
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//        printOptionGroup.setLayout(printOptionGroupLayout);
//        ((VRLayout) billDetailPrintDateHeses.getLayout()).setAutoWrap(false);
//
//        this.setTitle("「主治医意見書」印刷設定");
//        printOptionGroup.setText("「主治医意見書」印刷オプション");
        getPrintOptionGroup().setLayout(printOptionGroupLayout);
        ((VRLayout) billDetailPrintDateHeses.getLayout()).setAutoWrap(false);

        this.setTitle("「主治医意見書」印刷設定");
        getPrintOptionGroup().setText("「主治医意見書」印刷オプション");
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        billPrintGroup.setText("請求書印刷（「主治医意見書」と同時に）");
        csvGroup.setText("CSVファイルでの「主治医意見書」の提出");
        billPrint.setEnabled(false);
        billPrint.setHorizontalAlignment(SwingConstants.LEADING);
        billPrint.setText("印刷する");
        billPrint.setBindPath("SEIKYUSHO_HAKKOU_PATTERN");
        billHokensyaUnselectAlert.setText("保険者が選択されていません。");
        csvSubmitUnselectAlert.setText("CSV設定できる保険者が選択されていません。");
        csvSubmit.setEnabled(false);
        csvSubmit.setHorizontalAlignment(SwingConstants.LEADING);
        csvSubmit.setText("提出する");
        csvSubmit.setBindPath("FD_OUTPUT_UMU");
        toBillGroup.setText("請求書(明細書)振込先");
        printToCreateCost.setEnabled(false);
        printToCreateCost.setText("印刷する(意見書作成料請求先)");
        billPrintDateGroup.setText("請求書(明細書)出力日付");
        billPatternGroup.setText("請求パターン・請求先");
        billPatterns.setEnabled(false);
        billPatterns.setText("請求パターン：");
        toCheckCosts.setEnabled(false);
        toCheckCosts.setText("診察・検査料請求先(番号)：");
        toCreateCost.setEnabled(false);
        toCreateCost.setBindPath("ISS_INSURER_NO");
        toCreateCosts.setEnabled(false);
        toCreateCosts.setText("意見書作成料請求先(番号)：");
        printToCheckCost.setEnabled(false);
        printToCheckCost.setText("印刷する(診察・検査料請求先)");
        billPrintDate.setBindPath("PRINT_DATE");
        billPrintDate.setEnabled(false);
        billPrintDate.setAgeVisible(false);
        billPrintDates.setEnabled(false);
        billPrintDates.setText("出力日付");
        billPrintDates.setLabelColumns(5);
        billDetailPrintDates.setEnabled(false);
        billDetailPrintDates.setText("");
        billDetailPrintDates.setLabelColumns(5);
        billDetailPrintDate.setBindPath("PRINT_DETAIL_DATE");
        billDetailPrintDate.setDayVisible(false);
        billDetailPrintDate.setEnabled(false);
        billDetailPrintDate
                .setRequestedRange(IkenshoEraDateTextField.RNG_MONTH);
        billDetailPrintDate.setAgeVisible(false);
        billPrintDateHeses.setBeginText("：");
        billPrintDateHeses.setEndText("");
        billPrintDateHeses.setEnabled(false);
        nowDate.setEnabled(false);
        nowDate.setMnemonic('D');
        nowDate.setText("今日の日付(D)");
        clearDate.setText("日付消去(E)");
        clearDate.setEnabled(false);
        clearDate.setMnemonic('E');
        billHokensyaUnselectAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        csvSubmitUnselectAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);

        printPageHeader.setText("印刷する");
        printPageHeader.setBindPath("HEADER_OUTPUT_UMU1");
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//        pageHeaderGroup.setText("頁ヘッダ(保険者・被保険者番号)");
//        pageHeaderGroup.setLayout(new BorderLayout());
        getPageHeaderGroup().setText("頁ヘッダ(保険者・被保険者番号)");
        getPageHeaderGroup().setLayout(new BorderLayout());
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        printSecondHeader.setText("印刷する");
        printSecondHeader.setBindPath("HEADER_OUTPUT_UMU2");
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//        secondHeaderGroup.setText("２頁目ヘッダ(氏名、記入日)");
//        secondHeaderGroup.setLayout(new BorderLayout());
//        printDoctorName.setText("印刷する");
//        printDoctorName.setBindPath("DR_NM_OUTPUT_UMU");
//        doctorNameGroup.setText("医師氏名");
//        doctorNameGroup.setLayout(new BorderLayout());
        getSecondHeaderGroup().setText("２頁目ヘッダ(氏名、記入日)");
        getSecondHeaderGroup().setLayout(new BorderLayout());
        printDoctorName.setText("印刷する");
        printDoctorName.setBindPath("DR_NM_OUTPUT_UMU");
        getDoctorNameGroup().setText("医師氏名");
        getDoctorNameGroup().setLayout(new BorderLayout());
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        ok.setMnemonic('O');
        ok.setText("印刷(O)");
        cancel.setText("キャンセル(C)");
        cancel.setMnemonic('C');
        billPrintDatesPanelLayout.setFitHGrid(true);
        billPrintDatesPanelLayout.setHgrid(200);
        billPatternGroupLayout.setFitHGrid(true);
        billPatternGroupLayout.setHgrid(200);
        billPanelLayout.setFitVLast(true);
        billPanelLayout.setFitHLast(true);
        billPanelLayout.setAutoWrap(false);
        billPattern.setEnabled(false);
        billPattern.setBindPath("BILL_PATTERN");
        toCheckCostHead.setEnabled(false);
        toCheckCostHead.setBindPath("SKS_INSURER_NM");
        toCheckCost.setEnabled(false);
        toCheckCost.setBindPath("SKS_INSURER_NO");
        toCreateCostHead.setEnabled(false);
        toCreateCostHead.setBindPath("ISS_INSURER_NM");
        // 2005/12/11[Tozo Tanaka] : add begin
//        vRPanel1.setLayout(new BorderLayout());
        vRPanel1.setLayout(new VRLayout());
        // 2005/12/11[Tozo Tanaka] : add end
        billPrintNoSaveAlert1
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        billPrintNoSaveAlert1.setText("データが保存されていません。");
        csvSubmitAlerts.setLayout(new VRLayout());
        csvSubmitNoSaveAlert.setText("データが保存されていません。");
        csvSubmitNoSaveAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        csvSubmitHiHokensyaUnselectAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        csvSubmitHiHokensyaUnselectAlert.setText("被保険者番号が設定されていません。");
        billDetailPrintDateUnit.setEnabled(false);
        billDetailPrintDateUnit.setText("分");
        billPatternHeses.setEnabled(false);
        toCreateCostHeses.setEnabled(false);
        billDetailPrintDateHeses.setEnabled(false);
        toBillGroupLayout.setHgap(0);
        toBillGroupLayout.setLabelMargin(20);
        csvSubmitedAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        csvSubmitedAlert.setText("既にCSVの出力済みになっています。");
        notMostNewDocumentAlert.setText("最新の意見書ではありません。");
        notMostNewDocumentAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        billKindUnselectAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        billKindUnselectAlert.setText("種別が選択されていません。");
        billDoctorUnselectAlert
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        billDoctorUnselectAlert.setText("診療所・病院区分が設定されていません。");
        // 2006/02/11[Tozo Tanaka] : add begin
        billConvertedNoBillAlert.setText("移行データのため請求データが存在しません。");
        billConvertedNoBillAlert.setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
        // 2006/02/11[Tozo Tanaka] : add end
        // 2006/02/12[Tozo Tanaka] : add begin
        csvTargetAlert
        .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);
csvTargetAlert.setText("既にCSVの出力対象になっています。");
// 2006/02/12[Tozo Tanaka] : add end


toCheckCosts.add(toCheckCostHead, null);
        toCreateCosts.add(toCreateCostHead, null);
        toCheckCosts.add(billPatternHeses, null);
        billPatternHeses.add(toCheckCost, null);
        billPatterns.add(billPattern, null);
        this.getContentPane().add(contents, BorderLayout.CENTER);
        //[ID:0000515][Tozo TANAKA] 2009/09/10 replace begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
//        contents.add(printOptionGroup, VRLayout.NORTH);
//        printOptionGroup.add(doctorNameGroup, VRLayout.CLIENT);
//        doctorNameGroup.add(doctorNames, BorderLayout.WEST);
//        doctorNames.add(printDoctorName, null);
//        printOptionGroup.add(secondHeaderGroup, VRLayout.CLIENT);
//        secondHeaderGroup.add(secondHeaders, BorderLayout.WEST);
//        secondHeaders.add(printSecondHeader, null);
//        printOptionGroup.add(pageHeaderGroup, VRLayout.CLIENT);
//        pageHeaderGroup.add(printPageHeaders, BorderLayout.WEST);
        contents.add(getPrintOptionGroup(), VRLayout.NORTH);
        getDoctorNameGroup().add(doctorNames, BorderLayout.WEST);
        doctorNames.add(printDoctorName, null);
        getSecondHeaderGroup().add(secondHeaders, BorderLayout.WEST);
        secondHeaders.add(printSecondHeader, null);
        getPageHeaderGroup().add(printPageHeaders, BorderLayout.WEST);
        addPrintOptionGroup();
        //[ID:0000515][Tozo TANAKA] 2009/09/10 replace end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        printPageHeaders.add(printPageHeader, null);
        contents.add(billPrintPanel, VRLayout.NORTH);
        contents.add(billPatternGroup, VRLayout.NORTH);
        contents.add(billPanel, VRLayout.NORTH);

        billPrintPanel.add(billPrintGroup, VRLayout.CLIENT);
        billPrintGroup.add(billPrints, BorderLayout.NORTH);
        billPrintGroup.add(vRPanel1, BorderLayout.SOUTH);
        // 2005/12/11[Tozo Tanaka] : replace begin
//        vRPanel1.add(billHokensyaUnselectAlert, VRLayout.SOUTH);
//        vRPanel1.add(billPrintNoSaveAlert1, BorderLayout.CENTER);
//        vRPanel1.add(billKindUnselectAlert, BorderLayout.WEST);
//        vRPanel1.add(billDoctorUnselectAlert, BorderLayout.NORTH);
        vRPanel1.add(billHokensyaUnselectAlert, VRLayout.CLIENT);
        vRPanel1.add(billPrintNoSaveAlert1, VRLayout.CLIENT);
        vRPanel1.add(billKindUnselectAlert, VRLayout.CLIENT);
        vRPanel1.add(billDoctorUnselectAlert, VRLayout.CLIENT);
        vRPanel1.add(billConvertedNoBillAlert, VRLayout.CLIENT);
        // 2005/12/11[Tozo Tanaka] : replace end
        billPrintPanel.add(csvGroup, VRLayout.CLIENT);
        csvGroup.add(csvSubmits, BorderLayout.NORTH);
        csvSubmits.add(csvSubmit, null);
        billPrints.add(billPrint, null);
        billPatternGroup.add(billPatterns, VRLayout.FLOW_INSETLINE_RETURN);
        billPatternGroup.add(toCreateCosts, VRLayout.FLOW_INSETLINE_RETURN);
        billPatternGroup.add(toCheckCosts, VRLayout.FLOW_INSETLINE_RETURN);
        billPanel.add(toBillGroup, VRLayout.WEST);
        toBillGroup.add(printToCreateCosts, VRLayout.FLOW_INSETLINE_RETURN);
        printToCreateCosts.add(printToCreateCost, null);
        toBillGroup.add(printToCheckCosts, VRLayout.FLOW_INSETLINE_RETURN);
        printToCheckCosts.add(printToCheckCost, null);
        billPanel.add(billPrintDateGroup, VRLayout.CLIENT);

        billPrintDateGroup.add(billPrintDateButtons, BorderLayout.EAST);
        billPrintDateGroup.add(billPrintDatesPanel, BorderLayout.CENTER);
        billPrintDatesPanel.add(billPrintDates, VRLayout.FLOW_INSETLINE_RETURN);
        billPrintDatesPanel.add(billDetailPrintDates,
                VRLayout.FLOW_INSETLINE_RETURN);
        billPrintDateButtons.add(nowDate, VRLayout.NORTH);
        billPrintDateButtons.add(clearDate, VRLayout.NORTH);
        billPrintDates.add(billPrintDateHeses, null);
        billPrintDateHeses.add(billPrintDate, null);
        billDetailPrintDates.add(billDetailPrintDateHeses, null);
        billDetailPrintDateHeses.add(billDetailPrintDate, null);
        billDetailPrintDateHeses.add(billDetailPrintDateUnit, null);
        contents.add(buttons, VRLayout.SOUTH);
        buttons.add(ok, null);
        buttons.add(cancel, null);
        csvGroup.add(csvSubmitAlerts, BorderLayout.SOUTH);
        csvSubmitAlerts.add(csvSubmitUnselectAlert, VRLayout.FLOW_RETURN);
        csvSubmitAlerts.add(csvSubmitNoSaveAlert, VRLayout.FLOW_RETURN);
        csvSubmitAlerts.add(csvSubmitHiHokensyaUnselectAlert,
                VRLayout.FLOW_RETURN);
        csvSubmitAlerts.add(csvSubmitedAlert, VRLayout.FLOW_RETURN);
        csvSubmitAlerts.add(notMostNewDocumentAlert, VRLayout.FLOW_RETURN);
        // 2006/12/12[Tozo Tanaka] : add begin
        csvSubmitAlerts.add(csvTargetAlert, VRLayout.FLOW_RETURN);
        // 2006/12/12[Tozo Tanaka] : add end
        toCreateCosts.add(toCreateCostHeses, null);
        toCreateCostHeses.add(toCreateCost, null);

    }

    // 医師意見書対応
    // 2006/07/19[kamitsukasa] add begin
    /**
     * GroupBox『「主治医意見書」印刷オプション』Setter 
     */
    protected void setPrintOptionGroup(ACGroupBox printOptionGroup){
    	this.printOptionGroup = printOptionGroup;
    }
    
    /**
     * GroupBox『「主治医意見書」印刷オプション』Getter 
     */
    protected ACGroupBox getPrintOptionGroup(){
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        if(printOptionGroup==null){
            printOptionGroup = new ACGroupBox();
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    	return this.printOptionGroup;
    }
    
    /**
     * GroupBox『「主治医意見書」と同時に』Setter
     */
    protected void setBillPrintGroup(ACGroupBox billPrintGroup){
    	this.billPrintGroup = billPrintGroup;
    }

    /**
     * GroupBox『「主治医意見書」と同時に』Getter
     */
    protected ACGroupBox getBillPrintGroup(){
    	return this.billPrintGroup;
    }

    /**
     * GroupBox『CSVファイルでの「主治医意見書」の提出』Setter
     */
    protected void setCsvGroup(ACGroupBox csvGroup){
    	this.csvGroup = csvGroup;
    }

    /**
     * GroupBox『CSVファイルでの「主治医意見書」の提出』Getter
     */
    protected ACGroupBox getCsvGroup(){
    	return this.csvGroup;
    }
    
    // 2006/07/19[kamitsukasa] add end
    
    /**
     * 対象とする意見書の様式区分を返します。
     * @return 対象とする意見書の様式区分
     */
    protected int getFormatType(){
        return IkenshoConstants.IKENSHO_LOW_DEFAULT;
    }

    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    protected void addPrintOptionGroup(){
      getPrintOptionGroup().add(getDoctorNameGroup(), VRLayout.CLIENT);
      getPrintOptionGroup().add(getSecondHeaderGroup(), VRLayout.CLIENT);
      getPrintOptionGroup().add(getPageHeaderGroup(), VRLayout.CLIENT);
    }

    /**
     * doctorNameGroup を返します。
     * @return doctorNameGroup
     */
    protected ACGroupBox getDoctorNameGroup() {
        if(doctorNameGroup==null){
            doctorNameGroup = new ACGroupBox();
        }
        return doctorNameGroup;
    }

    /**
     * secondHeaderGroup を返します。
     * @return secondHeaderGroup
     */
    protected ACGroupBox getSecondHeaderGroup() {
        if(secondHeaderGroup==null){
            secondHeaderGroup = new ACGroupBox();
        }
        return secondHeaderGroup;
    }

    /**
     * pageHeaderGroup を返します。
     * @return pageHeaderGroup
     */
    protected ACGroupBox getPageHeaderGroup() {
        if(pageHeaderGroup==null){
            pageHeaderGroup = new ACGroupBox();
        }
        return pageHeaderGroup;
    }
    
    protected void setPackSize(){
        setSize(new Dimension(700, 420));
    }

    /**
     * 表示直前の処理をoverrideして実装します。
     */
    protected void beforeShow(){
        
    }

    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 

}