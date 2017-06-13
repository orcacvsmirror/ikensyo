package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JPanel;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaHoumonKangoShijishoPrintSetting extends IkenshoDialog {
    
    private VRPanel contents;
    private ACGroupBox styleGroup;
    private ACClearableRadioButtonGroup style;
    private VRPanel buttons;
    private ACButton ok;
    private ACButton cancel;
    protected VRMap source;
    private boolean printed = false;
    private boolean isPageBreak = false;
    
    /**
     * 医療機関他
     */
    public static final int DOCUMENT_TYPE_ORGAN = 1;
    
    /**
     * 老人保健施設
     */
    public static final int DOCUMENT_TYPE_HOKEN_SHISETSU = 2;
    
    /**
     * コンストラクタ
     * @param data 印刷データ
     */
    public IkenshoSeishinkaHoumonKangoShijishoPrintSetting(VRMap data) throws HeadlessException {
        super(ACFrame.getInstance(), "「精神科訪問看護指示書」印刷設定", true);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();
            this.source = (VRMap) contents.createSource();
            this.source.putAll(data);
            contents.setSource(this.source);
            contents.bindSource();
            // 引数から、これから印字するデータが改ページが必要かを判定する
            isPageBreak = ACCastUtilities.toBoolean(data.get("IS_PAGE_BREAK"), false);
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        cancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        ok.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                print();
                printed = true;
                dispose();
            }
        });
    }
    
    /**
     * 初期処理
     */
    private void jbInit() throws Exception {
        
        ((JPanel) this.getContentPane()).add(getContents());
        
        getContents().setLayout(new VRLayout());
        getContents().add(getStyleGroup(), VRLayout.NORTH);
        getContents().add(getButtons(), VRLayout.NORTH);
        // 帳票様式
        getStyleGroup().setText("帳票様式（老人保健施設以外の施設は「医療機関他」を選択してください）");
        getStyleGroup().setLayout(new BorderLayout());
        getStyleGroup().add(getStyle(), BorderLayout.CENTER);
        VRLayout styleLayout = new VRLayout();
        styleLayout.setAutoWrap(false);
        getStyle().setLayout(styleLayout);
        getStyle().setUseClearButton(false);
        getStyle().setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "医療機関他", "老人保健施設" }))));
        getStyle().setSelectedIndex(1);
        // 下部ボタン
        getButtons().setLayout(new VRLayout());
        getButtons().add(getCancel(), VRLayout.EAST);
        getButtons().add(getOk(), VRLayout.EAST);
        getOk().setText("印刷(O)");
        getOk().setMnemonic('O');
        getCancel().setText("キャンセル(C)");
        getCancel().setMnemonic('C');
    }
    
    /**
     * サイズ、位置の初期設定処理
     */
    private void init() {

        ACFrame frame = ACFrame.getInstance();
        if (frame.isSmall()){
            setSize(new Dimension(600,110));
        }
        else if (frame.isMiddle()){
            setSize(new Dimension(650,130));
        }
        else if (frame.isLarge()){
            setSize(new Dimension(850,155));
        }
        else {
            setSize(new Dimension(1150,164));
        }
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
     * モーダル表示し、印刷されたかを返します
     * @return 印刷されたか
     */
    public boolean showModal() {
        setVisible(true);
        return printed;
    }
    
    /**
     * 印刷処理
     */
    protected void print() {
        try {
            contents.applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
            
            // 印刷開始
            pd.beginPrintEdit();
            
            int printStyle = style.getSelectedIndex();
            
            switch (printStyle) {
            // 医療機関
            case DOCUMENT_TYPE_ORGAN:
                //改ページフラグを参照し、定義体を設定する
                if (isPageBreak) {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "Seishinsho_M1.xml");
                    ACChotarouXMLUtilities.addFormat(pd, "page2", "Seishinsho_M2.xml");
                } else {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "Seishinsho.xml");
                }
                break;
            // 老人保健施設
            case DOCUMENT_TYPE_HOKEN_SHISETSU:
                if (isPageBreak) {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "SeishinshoB_M1.xml");
                    ACChotarouXMLUtilities.addFormat(pd, "page2", "SeishinshoB_M2.xml");
                } else {
                    ACChotarouXMLUtilities.addFormat(pd, "page1", "SeishinshoB.xml");
                }
                break;
            default:
                throw new RuntimeException("想定しない印刷モードが指定されました。");
            }
            // 印刷処理
            printShijisho(pd, source, printStyle, isPageBreak);
            // 印刷終了
            pd.endPrintEdit();
            // PDFオープン
            IkenshoCommon.openPDF(pd);
            
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }
    
    /**
     * 精神科訪問看護指示書印刷処理
     * @param pd XML生成クラス
     * @param data データソース
     * @param printStyle 印刷タイプ(1: 医療機関、 2:老人保健施設)
     * @param isPageBreak 改頁有無
     */
    public static void printShijisho(ACChotarouXMLWriter pd, VRMap data, int printStyle, boolean isPageBreak) throws Exception {
        
        pd.beginPageEdit("page1");
        
        // 氏名
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w2");
        
        // 生年月日
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("明".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } 
        else if ("大".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } 
        else if ("昭".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } 
        else if ("平".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } 
        else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }
        // 年
        IkenshoCommon.addString(pd, "Grid2.h1.w5", VRDateParser.format(date, "ee"));
        // 月
        IkenshoCommon.addString(pd, "Grid2.h1.w7", VRDateParser.format(date, "MM"));
        // 日
        IkenshoCommon.addString(pd, "Grid2.h1.w9", VRDateParser.format(date, "dd"));
        
        // 年齢
        IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w11");
        
        // 郵便番号
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid3.h1.w4");
        // 住所
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid3.h2.w4");
        // 電話番号
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid3.h3.w2");
        
        // 指示期間 開始
        String text = "";
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_FROM", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h1.w1", "（"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h1.w2", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon
                        .addString(pd, "Grid1.h1.w4", text.substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h1.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "Grid1.h1.w1", "（");
        }
        // 指示期間 終了
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_TO", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h1.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h1.w8", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon.addString(pd, "Grid1.h1.w10", text
                        .substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h1.w12", text.substring(
                            8, 10));
                }
            }
        }
        
        // 施設名
        IkenshoCommon.addString(pd, data, "SISETU_NM", "Grid15.h1.w2");
        
        // 主たる傷病名
        StringBuffer sb = new StringBuffer();
        VRArrayList array = new VRArrayList() ;
        
        Object shindanName1 = VRBindPathParser.get("SINDAN_NM1", data);
        if (!IkenshoCommon.isNullText(shindanName1)){
            array.add(String.valueOf(shindanName1));
        }
        Object shindanName2 = VRBindPathParser.get("SINDAN_NM2", data);
        if (!IkenshoCommon.isNullText(shindanName2)){
            array.add(String.valueOf(shindanName2));
        }
        Object shindanName3 = VRBindPathParser.get("SINDAN_NM3", data);
        if (!IkenshoCommon.isNullText(shindanName3)){
            array.add(String.valueOf(shindanName3));
        }
        
        // arrayの中にデータが入っているかどうか確認
        if (!array.isEmpty()){
            // 主たる傷病が名が１つしか記載されていない場合、番号は付けない。
            if (array.size() == 1){
                sb.append(array.get(0));
            }
            // 主たる傷病名が１つ以上記載されている場合、"（１）"から番号を付ける。
            else{
                for (int i=0; i<array.size(); i++){
                    switch (i){
                    case 0 :
                        sb.append("(１)");
                        sb.append(array.get(i));
                        break;
                    case 1 :
                        sb.append(" ");
                        sb.append("(２)");
                        sb.append(array.get(i));
                        break;
                    case 2 :
                        sb.append(" ");
                        sb.append("(３)");
                        sb.append(array.get(i));
                        break;
                    }
                    
                }
            }
        }
        IkenshoCommon.addString(pd, "Grid4.h1.w2", sb.toString());
        
        // 症状・治療状態（改頁ありとなしで別処理）
        if (!isPageBreak) {
            String[] lines = ACTextUtilities.separateLineWrapOnByte(ACCastUtilities.toString(data.get("MT_STS")), 100);
            int linesCount = Math.min(5, lines.length);
            int totalByteCount = 0;
            StringBuffer sbSickProgress = new StringBuffer();
            for (int i = 0; i < linesCount; i++) {
                if (totalByteCount > 0) {
                    sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
                }
                int lineByteLen = lines[i].getBytes().length;
                if (totalByteCount + lineByteLen > 500) {
                    sbSickProgress.append(lines[i].substring(0, Math.max(0,501 - (totalByteCount + lineByteLen))));
                    break;
                }
                sbSickProgress.append(lines[i]);
                totalByteCount += lineByteLen;
            }
            ACChotarouXMLUtilities.setValue(pd, "Grid6.h1.w2", sbSickProgress.toString());
        }
        else {
            IkenshoCommon.addString(pd, "lblJyotai", getLineBreakedString(data.get("MT_STS")));
        }
        
        // 投与中の薬剤の用法・用量（改頁ありとなしで別処理）
        if (!isPageBreak) {
            if (!(IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", data)) && 
                    IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", data)) &&
                    IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", data)))) {
                //薬剤7か薬剤8が入力されている場合
                addMedicine(pd, data, "1", "sickMedicines8.h1.w4");
                addMedicine(pd, data, "2", "sickMedicines8.h1.w2");
                addMedicine(pd, data, "3", "sickMedicines8.h2.w4");
                addMedicine(pd, data, "4", "sickMedicines8.h2.w2");
                addMedicine(pd, data, "5", "sickMedicines8.h3.w4");
                addMedicine(pd, data, "6", "sickMedicines8.h3.w2");
                addMedicine(pd, data, "7", "sickMedicines8.h4.w4");
                addMedicine(pd, data, "8", "sickMedicines8.h4.w2");
                pd.addAttribute("Grid7", "Visible", "FALSE");
            }else{
                addMedicine(pd, data, "1", "Grid7.h1.w4");
                addMedicine(pd, data, "2", "Grid7.h1.w2");
                addMedicine(pd, data, "3", "Grid7.h2.w4");
                addMedicine(pd, data, "4", "Grid7.h2.w2");
                addMedicine(pd, data, "5", "Grid7.h3.w4");
                addMedicine(pd, data, "6", "Grid7.h3.w2");
                pd.addAttribute("sickMedicines8", "Visible", "FALSE");
            }
        }
        else {
            addMedicine(pd, data, "1", "sickMedicines8.h1.w4");
            addMedicine(pd, data, "2", "sickMedicines8.h1.w2");
            addMedicine(pd, data, "3", "sickMedicines8.h2.w4");
            addMedicine(pd, data, "4", "sickMedicines8.h2.w2");
            addMedicine(pd, data, "5", "sickMedicines8.h3.w4");
            addMedicine(pd, data, "6", "sickMedicines8.h3.w2");
            addMedicine(pd, data, "7", "sickMedicines8.h4.w4");
            addMedicine(pd, data, "8", "sickMedicines8.h4.w2");
        }
        
        // 病名告知
        IkenshoCommon.addSelection(pd, data, "BYOUMEI_KOKUTI", new String[] { "Label14", "Label15" }, -1);
        // 治療の受け入れ
        IkenshoCommon.addString(pd, data, "TIRYOU_UKEIRE", "Grid8.h2.w2");
        // 複数名訪問の必要性
        IkenshoCommon.addSelection(pd, data, "FUKUSU_HOUMON", new String[] { "Label16", "Label17" }, -1);
        // 短時間訪問の必要性
        IkenshoCommon.addSelection(pd, data, "TANJIKAN_HOUMON", new String[] { "Label18", "Label19" }, -1);
        // 日常生活自立度－認知症の状況
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape28", "Shape17", "Shape18", "Shape13", "Shape11",
                "Shape12", "Shape16", "Shape14" }, -1);
        
        int intValue;
        // 生活リズムの確立有無
        intValue = ((Integer) VRBindPathParser.get("SEIKATU_RIZUMU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label20", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // 生活リズムの確立（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h3.w3", getLineBreakedString(data.get("SEIKATU_RIZUMU")));
            }
            else {
                IkenshoCommon.addString(pd, "lblSeikatu", getLineBreakedString(data.get("SEIKATU_RIZUMU")));
            }
        }
        
        // 改頁ありの場合はここで改頁
        if (isPageBreak) {
            pd.endPageEdit();
            pd.beginPageEdit("page2");
            // 患者氏名と年齢を再掲
            IkenshoCommon.addString(pd, data, "PATIENT_NM", "patientData.h1.w1");
            IkenshoCommon.addString(pd, data, "AGE", "patientData.h1.w3");
        }
        
        // 家事能力、社会技能の獲得有無
        intValue = ((Integer) VRBindPathParser.get("KAJI_NOURYOKU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label21", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // 家事能力、社会技能の獲得（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h5.w3", getLineBreakedString(data.get("KAJI_NOURYOKU")));
            }
            else {
                IkenshoCommon.addString(pd, "lblKaji", getLineBreakedString(data.get("KAJI_NOURYOKU")));
            }
        }
        
        // 対人関係の改善（家族含む）有無
        intValue = ((Integer) VRBindPathParser.get("TAIJIN_KANKEI_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label22", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // 対人関係の改善（家族含む）（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h7.w3", getLineBreakedString(data.get("TAIJIN_KANKEI")));
            }
            else {
                IkenshoCommon.addString(pd, "lblTaijin", getLineBreakedString(data.get("TAIJIN_KANKEI")));
            }
        }
        
        // 社会資源活用の支援有無
        intValue = ((Integer) VRBindPathParser.get("SYAKAI_SHIGEN_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label23", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // 社会資源活用の支援（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h9.w3", getLineBreakedString(data.get("SYAKAI_SHIGEN")));
            }
            else {
                IkenshoCommon.addString(pd, "lblSyakai", getLineBreakedString(data.get("SYAKAI_SHIGEN")));
            }
        }
        
        // 薬物療法への援助有無
        intValue = ((Integer) VRBindPathParser.get("YAKUBUTU_RYOUHOU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label24", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // 薬物療法への援助（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h11.w3", getLineBreakedString(data.get("YAKUBUTU_RYOUHOU")));
            }
            else {
                IkenshoCommon.addString(pd, "lblYakubutu", getLineBreakedString(data.get("YAKUBUTU_RYOUHOU")));
            }
        }
        
        // 身体合併症の発症・悪化の防止有無
        intValue = ((Integer) VRBindPathParser.get("SHINTAI_GAPPEISYO_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label25", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // 身体合併症の発症・悪化の防止（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h13.w3", getLineBreakedString(data.get("SHINTAI_GAPPEISYO")));
            }
            else {
                IkenshoCommon.addString(pd, "lblShintai", getLineBreakedString(data.get("SHINTAI_GAPPEISYO")));
            }
        }
        
        // その他有無
        intValue = ((Integer) VRBindPathParser.get("SEISHIN_OTHER_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("Label26", "Visible", "FALSE");
        }
        if (intValue == 1) {
            // その他（改頁ありとなしで別処理）
            if (!isPageBreak) {
                IkenshoCommon.addString(pd, "Grid10.h15.w3", getLineBreakedString(data.get("SEISHIN_OTHER")));
            }
            else {
                IkenshoCommon.addString(pd, "lblEtc", getLineBreakedString(data.get("SEISHIN_OTHER")));
            }
        }
        
        // 緊急時の連絡先
        IkenshoCommon.addString(pd, data, "KINKYU_RENRAKU", "Grid11.h1.w2");
        // 不在時の対応法
        IkenshoCommon.addString(pd, data, "FUZAIJI_TAIOU", "Grid11.h2.w2");
        // 主治医との情報交換の手段
        IkenshoCommon.addString(pd, data, "JYOUHOU_SYUDAN", "Grid9.h1.w2");
        // 特記すべき留意事項（改頁ありとなしで別処理）
        if (!isPageBreak) {
            IkenshoCommon.addString(pd, "Grid12.h2.w1", getLineBreakedString(data.get("SIJI_TOKKI")));
        }
        else {
            IkenshoCommon.addString(pd, "lblTokki", getLineBreakedString(data.get("SIJI_TOKKI")));
        }
        
        // 記入日
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "Label2", VRDateParser.format((Date) obj, "gggee年MM月dd日"));
            }
        }
        
        // 医療機関名
        IkenshoCommon.addString(pd, data, "MI_NM", "Grid13.h1.w2");
        // 医療機関住所
        IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid13.h2.w2");
        // 医療機関電話番号
        IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid13.h3.w2");
        // 医療機関FAX番号
        IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid13.h4.w2");
        // 医師氏名
        IkenshoCommon.addString(pd, data, "DR_NM", "Grid13.h5.w2");
        
        // 訪問看護ステーション
        IkenshoCommon.addString(pd, "Label6", String.valueOf(VRBindPathParser.get("STATION_NM", data)) + " 殿");
        
        pd.endPageEdit();
    }
    
    /**
     * 薬剤情報取得処理
     * @param pd XML生成クラス
     * @param data データソース
     * @param index インデックス
     * @param target 出力先
     */
    protected static void addMedicine(ACChotarouXMLWriter pd, VRMap data, String index, String target) throws Exception {
        StringBuffer sb = new StringBuffer();
        Object obj;
        obj = VRBindPathParser.get("MEDICINE" + index, data);
        if (obj != null) {
            sb.append(String.valueOf(obj));
        }
        sb.append("　");
        obj = VRBindPathParser.get("DOSAGE" + index, data);
        if (obj != null) {
            sb.append(String.valueOf(obj));
        }
        obj = VRBindPathParser.get("UNIT" + index, data);
        if (obj != null) {
            sb.append(String.valueOf(obj));
        }
        obj = VRBindPathParser.get("USAGE" + index, data);
        if (obj != null) {
            // 用法・用量の前に空白を挿入し、意見書の表示と合わせる
            sb.append("　");
            sb.append(String.valueOf(obj));
        }
        IkenshoCommon.addString(pd, target, sb.toString());
    }
    
    /**
     * 改行追加処理
     * @param obj 印字データ
     */
    private static String getLineBreakedString(Object obj) throws Exception {
        
        if (obj == null) {
            return "";
        }
        String value = ACCastUtilities.toString(obj);
        StringBuffer result = new StringBuffer();
        String[] lines = ACTextUtilities.separateLineWrapOnByte(value, 100);
        
        for (int i = 0; i < lines.length; i++) {
            if (i != 0) {
                result.append(IkenshoConstants.LINE_SEPARATOR);
            }
            result.append(lines[i]);
        }
        return result.toString();
    }
    
    /**
     * ウィンドウクローズ時処理
     */
    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }
    
    /**
     * 終了処理
     */
    protected void closeWindow() {
        // 自身を破棄する
        this.dispose();
    }
    
    /**
     * contents を返します。
     * @return contents
     */
    protected VRPanel getContents() {
        if(contents==null){
            contents = new VRPanel();
        }
        return contents;
    }
    
    /**
     * styleGroup を返します。
     * @return styleGroup
     */
    protected ACGroupBox getStyleGroup() {
        if(styleGroup==null){
            styleGroup = new ACGroupBox();
        }
        return styleGroup;
    }
    
    /**
     * buttons を返します。
     * @return buttons
     */
    protected VRPanel getButtons() {
        if(buttons==null){
            buttons = new VRPanel();
        }
        return buttons;
    }
    
    /**
     * style を返します。
     * @return style
     */
    protected ACClearableRadioButtonGroup getStyle() {
        if(style==null){
            style = new ACClearableRadioButtonGroup();
        }
        return style;
    }
    
    /**
     * cancel を返します。
     * @return cancel
     */
    protected ACButton getCancel() {
        if(cancel==null){
            cancel = new ACButton();
        }
        return cancel;
    }
    
    /**
     * ok を返します。
     * @return ok
     */
    protected ACButton getOk() {
        if(ok==null){
            ok = new ACButton();
        }
        return ok;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End