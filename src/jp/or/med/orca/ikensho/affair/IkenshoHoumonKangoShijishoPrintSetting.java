package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import javax.swing.JPanel;

import sun.security.action.GetBooleanAction;

import jp.nichicom.ac.ACConstants;
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
import jp.or.med.orca.ikensho.component.IkenshoVirticalRadioButtonGroup;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoPrintSetting extends IkenshoDialog {
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    private JPanel contentPane = new JPanel();
//    private VRPanel contents = new VRPanel();
//    private ACGroupBox styleGroup = new ACGroupBox();
//    private ACClearableRadioButtonGroup style = new ACClearableRadioButtonGroup();
//    private ACGroupBox sendToGroup = new ACGroupBox();
//    private IkenshoVirticalRadioButtonGroup sendTo = new IkenshoVirticalRadioButtonGroup();
//    private VRPanel buttons = new VRPanel();
//    private ACButton ok = new ACButton();
//    private ACButton cancel = new ACButton();
    private VRPanel contents;
    private ACGroupBox styleGroup;
    private ACClearableRadioButtonGroup style;
    private ACGroupBox sendToGroup;
    private IkenshoVirticalRadioButtonGroup sendTo;
    private VRPanel buttons;
    private ACButton ok;
    private ACButton cancel;
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  

    protected boolean printed = false;
    protected VRMap source;
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin 【2010年度要望対応】
    //改ページが発生しているかの判定フラグ
    private boolean isPageBreak = false;
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end 【2010年度要望対応】

    /**
     * 医療機関他
     */
    public static final int DOCUMENT_TYPE_ORGAN = 1;
    /**
     * 老人保健施設
     */
    public static final int DOCUMENT_TYPE_HOKEN_SHISETSU = 2;

    /**
     * モーダル表示し、印刷されたかを返します。
     * 
     * @return 印刷されたか
     */
    public boolean showModal() {

        try {
            boolean useOtherStation = ((Integer) VRBindPathParser.get(
                    "OTHER_STATION_SIJI", source)).intValue() == 2;

            if (useOtherStation) {
                String station1 = String.valueOf(VRBindPathParser.get(
                        "STATION_NM", source));
                String station2 = String.valueOf(VRBindPathParser.get(
                        "OTHER_STATION_NM", source));
                if (("".equals(station1)) || ("".equals(station2))) {
                    useOtherStation = false;
                } else {
                    sendTo.setModel(new VRListModelAdapter(new VRArrayList(
                            Arrays.asList(new String[] { "双方を宛先とし、２ページ印刷",
                                    station1, station2 }))));
                    sendTo.setSelectedIndex(1);
                }
            }

            if (!useOtherStation) {
                sendToGroup.setEnabled(false);
                sendTo.setEnabled(false);
                sendTo.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "", "", "" }))));
            }

        } catch (ParseException ex) {
            IkenshoCommon.showExceptionMessage(ex);
            return false;
        }

        setVisible(true);
        // show();
        return printed;
    }

    /**
     * コンストラクタです。
     * 
     * @param data 印刷データ
     * @throws HeadlessException 初期化例外
     */
    public IkenshoHoumonKangoShijishoPrintSetting(VRMap data)
            throws HeadlessException {
        super(ACFrame.getInstance(), "「訪問看護指示書」印刷設定", true);
        try {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            jbInit();
            pack();
            init();

            this.source = (VRMap) contents.createSource();
            this.source.putAll(data);
            contents.setSource(this.source);
            contents.bindSource();
            //[ID:0000635][Shin Fujihara] 2011/02/25 add begin 【2010年度要望対応】
            //引数から、これから印字するデータが改ページが必要かを判定する
            isPageBreak = ACCastUtilities.toBoolean(data.get("IS_PAGE_BREAK"), false);
            //[ID:0000635][Shin Fujihara] 2011/02/25 add end 【2010年度要望対応】
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
     * 印刷処理を行います。
     */
    protected void print() {
        try {
            contents.applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();

            pd.beginPrintEdit();
            int printStyle = style.getSelectedIndex();
            
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit begin 【2010年度要望対応】
            switch (printStyle) {
            case DOCUMENT_TYPE_ORGAN: // 医療機関
            	//改ページフラグを参照し、定義体を設定する
            	if (isPageBreak) {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "Shijisho_M1.xml");
            		ACChotarouXMLUtilities.addFormat(pd, "page2", "Shijisho_M2.xml");
            	} else {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "Shijisho.xml");
            	}
                break;
            case DOCUMENT_TYPE_HOKEN_SHISETSU: // 老人保健施設
            	if (isPageBreak) {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "ShijishoB_M1.xml");
            		ACChotarouXMLUtilities.addFormat(pd, "page2", "ShijishoB_M2.xml");
            	} else {
            		ACChotarouXMLUtilities.addFormat(pd, "page1", "ShijishoB.xml");
            	}
                break;
            default:
                throw new RuntimeException("想定しない印刷モードが指定されました。");
            }
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit end 【2010年度要望対応】


            String station1 = String.valueOf(VRBindPathParser.get("STATION_NM", source));
            String station2 = String.valueOf(VRBindPathParser.get("OTHER_STATION_NM", source));
            
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit begin 【2010年度要望対応】
            if (sendTo.isEnabled()) {
                switch (sendTo.getSelectedIndex()) {
                case 1: // 2枚
                	printShijishoBranch(pd, "page1", source, station1, station2, printStyle);
                	printShijishoBranch(pd, "page1", source, station2, station1, printStyle);
                    break;
                case 2: // 前者のみ
                	printShijishoBranch(pd, "page1", source, station1, station2, printStyle);
                    break;
                case 3: // 後者のみ
                	printShijishoBranch(pd, "page1", source, station2, station1, printStyle);
                    break;
                }
            } else {
                // 単一のステーションのみ
            	printShijishoBranch(pd, "page1", source, station1, station2, printStyle);
            }
            //[ID:0000635][Shin Fujihara] 2011/02/25 edit end 【2010年度要望対応】

            pd.endPrintEdit();

            IkenshoCommon.openPDF(pd);

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

    }

    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin 【2010年度要望対応】
    public void printShijishoBranch(ACChotarouXMLWriter pd, String formatName,
            VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {
    	
    	//改ページが発生しているかどうかで、印字に使用するメソッドを切り替える
    	//改ページアリ
    	if (isPageBreak) {
    		printShijishoMulti(pd, data, stationName1, stationName2, printStyle);
    		
    	//改ページなし（従来どおりのメソッドを使用）
    	} else {
    		printShijisho(pd, formatName, data, stationName1, stationName2, printStyle);
    	}
    	
    }
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end 【2010年度要望対応】
    
    
    /**
     * 指示書を印刷します。
     * 
     * @param pd XML生成クラス
     * @param formatName フォーマットID
     * @param data データソース
     * @param stationName1 宛先ステーション名
     * @param stationName2 その他の宛先ステーション名
     * @param printStyle 印刷タイプ(1: 医療機関、 2:老人保健施設)
     * @throws Exception 処理例外
     */
    public static void printShijisho(ACChotarouXMLWriter pd, String formatName,
            VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {
    	
        pd.beginPageEdit(formatName);

        String text;
        StringBuffer sb;

        // 氏名
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w2");

        // 生年月日
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("明".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("大".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("昭".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("平".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }
        // 年
        IkenshoCommon.addString(pd, "Grid2.h1.w5", VRDateParser.format(date,
                "ee"));
        // 月
        IkenshoCommon.addString(pd, "Grid2.h1.w7", VRDateParser.format(date,
                "MM"));
        // 日
        IkenshoCommon.addString(pd, "Grid2.h1.w9", VRDateParser.format(date,
                "dd"));

        // 年齢
        IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w11");

        // 郵便番号
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid3.h1.w4");
        // 住所
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid3.h2.w4");
        // 電話番号
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid3.h3.w2");

        // 訪問看護指示期間 開始
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

        // 訪問看護指示期間 終了
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

        // 点滴注射指示期間 開始
        text = String.valueOf(VRBindPathParser.get("TENTEKI_FROM", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "（"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w2", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon
                        .addString(pd, "Grid1.h2.w4", text.substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "（");
        }

        // 点滴注射指示期間 終了
        text = String.valueOf(VRBindPathParser.get("TENTEKI_TO", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w8", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon.addString(pd, "Grid1.h2.w10", text
                        .substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w12", text.substring(
                            8, 10));
                }
            }
        }

        //2008/3/6 H.Tanaka Add Sta 訪問看護指示書印字変更対応
        // 主たる傷病名
        sb = new StringBuffer();
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
        
        //arrayの中にデータが入っているかどうか確認
        if (!array.isEmpty()){
        	
         	//主たる傷病が名が１つしか記載されていないの場合、番号は付けない。
	        if (array.size() == 1){
	        	sb.append(array.get(0));
	        }
	        //主たる傷病名が１つ以上記載されている場合、"（１）"から番号を付ける。
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
        //2008/3/6 H.Tanaka Add End
        //2008/3/6 H.Tanaka Del Sta 訪問看護指示書印字変更対応
        //text = String.valueOf(VRBindPathParser.get("SINDAN_NM1", data));
        //if (!IkenshoCommon.isNullText(text)) {
        //    sb.append(text);
        //}
        //text = String.valueOf(VRBindPathParser.get("SINDAN_NM2", data));
        //if (!IkenshoCommon.isNullText(text)) {
        //    if (sb.length() > 0) {
        //        sb.append("／");
        //    }
        //    sb.append(text);
        //}
        //text = String.valueOf(VRBindPathParser.get("SINDAN_NM3", data));
        //if (!IkenshoCommon.isNullText(text)) {
        //    if (sb.length() > 0) {
        //        sb.append("／");
        //    }
        //    sb.append(text);
        //}
        //2008/3/6 H.Tanaka Del end
        IkenshoCommon.addString(pd, "Grid4.h1.w2", sb.toString());


        //2009/01/09 [Tozo Tanaka] Replace - begin
//        // 傷病治療状態
//        IkenshoCommon.addString(pd, data, "MT_STS", "Grid6.h1.w2");
//        // 投与中の薬剤の用法・用量
//        addMedicine(pd, data, "1", "Grid7.h1.w4");
//        addMedicine(pd, data, "2", "Grid7.h1.w2");
//        addMedicine(pd, data, "3", "Grid7.h2.w4");
//        addMedicine(pd, data, "4", "Grid7.h2.w2");
//        addMedicine(pd, data, "5", "Grid7.h3.w4");
//        addMedicine(pd, data, "6", "Grid7.h3.w2");

        // 傷病治療状態
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//        ACChotarouXMLUtilities.setValue(pd, "Grid6.h1.w2",
//                getInsertionLineSeparatorToStringOnByte(ACCastUtilities.toString(data
//                        .get("MT_STS")), 100));
        String[] lines = ACTextUtilities.separateLineWrapOnByte(ACCastUtilities
                .toString(data.get("MT_STS")), 100);
        int linesCount = Math.min(5, lines.length);
        int totalByteCount = 0;
        StringBuffer sbSickProgress = new StringBuffer();
        for (int i = 0; i < linesCount; i++) {
            if (totalByteCount > 0) {
                sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
            }
            int lineByteLen = lines[i].getBytes().length;
            if (totalByteCount + lineByteLen > 500) {
                sbSickProgress.append(lines[i].substring(0,
                        Math.max(0,501 - (totalByteCount + lineByteLen))));
                break;
            }
            sbSickProgress.append(lines[i]);
            totalByteCount += lineByteLen;
        }
        ACChotarouXMLUtilities.setValue(pd, "Grid6.h1.w2", sbSickProgress
                .toString());
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
        
        // 投与中の薬剤の用法・用量
        if (!(
                IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", data)) && 
                IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", data)) && 
                IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", data)) &&
                IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", data)) && 
                IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", data)) 
                ) &&
                (getMedicineViewCount()>6)) {
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
        //2009/01/09 [Tozo Tanaka] Replace - end

        // 寝たきり度
        IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {
                "Shape27", "Shape25", "Shape26", "Shape22", "Shape20",
                "Shape21", "Shape24", "Shape23", "Shape15" }, -1);
        // 痴呆の状態
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape28", "Shape17", "Shape18", "Shape13", "Shape11",
                "Shape12", "Shape16", "Shape14" }, -1);

        // 要介護認定の状況
        switch (((Integer) VRBindPathParser.get("YOUKAIGO_JOUKYOU", data))
                .intValue()) {
        case 1: // 自立
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            break;
        case 11:// 要支援
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 21:// 要介護1
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 22:// 要介護2
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 23:// 要介護3
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 24:// 要介護4
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 25:// 要介護5
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        default:
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        }
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add begin 平成21年4月法改正対応
        //褥瘡の深さ：NPUAP分類
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_NPUAP", new String[] {
                "Shape55", "Shape56" }, -1);
        //褥瘡の深さ：DESIGN分類
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_DESIGN", new String[] {
                "Shape57", "Shape58", "Shape59" }, -1);
        // [ID:0000463][Tozo TANAKA] 2009/03/20 add end

        // 装着・使用医療機器等
        // 自動腹膜灌流装置
        if (((Integer) VRBindPathParser.get("JD_FUKU", data)).intValue() != 1) {
            pd.addAttribute("Shape36", "Visible", "FALSE");
        }
        // 透析液供給装置
        if (((Integer) VRBindPathParser.get("TOU_KYOUKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape30", "Visible", "FALSE");
        }
        // 酸素療法
        if (((Integer) VRBindPathParser.get("OX_RYO", data)).intValue() != 1) {
            pd.addAttribute("Shape50", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "OX_RYO_RYO", "Grid9.h1.w20");
        }
        // 吸引機
        if (((Integer) VRBindPathParser.get("KYUINKI", data)).intValue() != 1) {
            pd.addAttribute("Shape33", "Visible", "FALSE");
        }
        // 中心静脈栄養
        if (((Integer) VRBindPathParser.get("CHU_JOU_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape32", "Visible", "FALSE");
        }
        // 輸液ポンプ
        if (((Integer) VRBindPathParser.get("YUEKI_PUMP", data)).intValue() != 1) {
            pd.addAttribute("Shape31", "Visible", "FALSE");
        }
        // 経管栄養
        if (((Integer) VRBindPathParser.get("KEKN_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape34", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_METHOD",
                    "Grid9.h3.w23");
            IkenshoCommon
                    .addString(pd, data, "KEKN_EIYOU_SIZE", "Grid9.h3.w14");
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_CHG", "Grid9.h3.w18");
        }
        // 留置カテーテル
        if (((Integer) VRBindPathParser.get("RYU_CATHETER", data)).intValue() != 1) {
            pd.addAttribute("Shape42", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "RYU_CAT_SIZE", "Grid9.h4.w7");
            IkenshoCommon.addString(pd, data, "RYU_CAT_CHG", "Grid9.h4.w18");
        }
        // 人工呼吸器
        if (((Integer) VRBindPathParser.get("JINKOU_KOKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape41", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_HOUSIKI",
                    "Grid9.h5.w23");
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_SET", "Grid9.h5.w13");
        }
        // 気管カニューレ
        if (((Integer) VRBindPathParser.get("CANNULA", data)).intValue() != 1) {
            pd.addAttribute("Shape40", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "CANNULA_SIZE", "Grid9.h6.w7");
        }
        // ドレーン
        if (((Integer) VRBindPathParser.get("DOREN", data)).intValue() != 1) {
            pd.addAttribute("Shape37", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "DOREN_BUI", "Grid9.h6.w17");
        }
        // 人工肛門
        if (((Integer) VRBindPathParser.get("JINKOU_KOUMON", data)).intValue() != 1) {
            pd.addAttribute("Shape43", "Visible", "FALSE");
        }
        // 人工膀胱
        if (((Integer) VRBindPathParser.get("JINKOU_BOUKOU", data)).intValue() != 1) {
            pd.addAttribute("Shape38", "Visible", "FALSE");
        }
        // その他
        if (((Integer) VRBindPathParser.get("SOUCHAKU_OTHER_FLAG", data))
                .intValue() == 1) {
            Object obj = VRBindPathParser.get("SOUCHAKU_OTHER", data);
            if (IkenshoCommon.isNullText(obj)) {
                pd.addAttribute("Shape35", "Visible", "FALSE");
            } else {
                IkenshoCommon.addString(pd, "Grid9.h7.w6", String.valueOf(obj));
            }
        } else {
            pd.addAttribute("Shape35", "Visible", "FALSE");
        }

        //[ID:0000635][Shin Fujihara] 2011/03/01 edit begin 【2010年度要望対応】
        // 療養生活指導上の留意事項
        //IkenshoCommon.addString(pd, data, "RSS_RYUIJIKOU", "Grid10.h2.w2");
        IkenshoCommon.addString(pd, "Grid10.h2.w2", getLineBreakedString(data.get("RSS_RYUIJIKOU")));
        
        // リハビリテーション
        if (((Integer) VRBindPathParser.get("REHA_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape46", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "REHA_SIJI", "Grid10.h4.w2");
            IkenshoCommon.addString(pd, "Grid10.h4.w2", getLineBreakedString(data.get("REHA_SIJI")));
        }
        // 褥瘡
        if (((Integer) VRBindPathParser.get("JOKUSOU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape45", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "JOKUSOU_SIJI", "Grid10.h6.w2");
            IkenshoCommon.addString(pd, "Grid10.h6.w2", getLineBreakedString(data.get("JOKUSOU_SIJI")));
        }
        // 機器等の操作援助
        if (((Integer) VRBindPathParser.get("SOUCHAKU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape44", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "SOUCHAKU_SIJI", "Grid10.h8.w2");
            IkenshoCommon.addString(pd, "Grid10.h8.w2", getLineBreakedString(data.get("SOUCHAKU_SIJI")));
        }
        // その他
        if (((Integer) VRBindPathParser.get("RYUI_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape47", "Visible", "FALSE");
        } else {
            //IkenshoCommon.addString(pd, data, "RYUI_SIJI", "Grid10.h12.w2");
        	IkenshoCommon.addString(pd, "Grid10.h12.w2", getLineBreakedString(data.get("RYUI_SIJI")));
        }

        // 点滴注射指示
        //IkenshoCommon.addString(pd, data, "TENTEKI_SIJI", "Grid10.h10.w1");
        IkenshoCommon.addString(pd, "Grid10.h10.w1", getLineBreakedString(data.get("TENTEKI_SIJI")));

        // 緊急時の連絡先
        IkenshoCommon.addString(pd, data, "KINKYU_RENRAKU", "Grid11.h1.w2");

        // 不在時の対応法
        IkenshoCommon.addString(pd, data, "FUZAIJI_TAIOU", "Grid11.h2.w2");

        // 特記すべき留意事項
        //IkenshoCommon.addString(pd, data, "SIJI_TOKKI", "Grid12.h2.w1");
        IkenshoCommon.addString(pd, "Grid12.h2.w1", getLineBreakedString(data.get("SIJI_TOKKI")));
        //[ID:0000635][Shin Fujihara] 2011/03/01 edit begin 【2010年度要望対応】

        // 他の訪問看護ステーションへの指示
        if (((Integer) VRBindPathParser.get("OTHER_STATION_SIJI", data))
                .intValue() != 2) {
            pd.addAttribute("Shape39", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", " 殿");
        } else {
            pd.addAttribute("Shape48", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", stationName2 + " 殿");
        }

        // 記入日
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "Label2", VRDateParser.format(
                        (Date) obj, "gggee年MM月dd日"));
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

        // 対象の訪問看護ステーション
        IkenshoCommon.addString(pd, "Label6", stationName1 + " 殿");

        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // 訪問看護指示書
            if (((Integer) VRBindPathParser.get("TENTEKI_SIJISYO", data))
                    .intValue() == -1) {
                // 訪問点滴注射指示書
                IkenshoCommon.addString(pd, "title", "訪問看護指示書・在宅患者訪問点滴注射指示書");

            } else {
                IkenshoCommon.addString(pd, "title", "訪問看護指示書");
            }
        } else {
            // 訪問点滴注射指示書
            IkenshoCommon.addString(pd, "title", "在宅患者訪問点滴注射指示書");
        }

        pd.endPageEdit();

    }
    
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add begin 【2010年度要望対応】
    //指示書を改ページして印字する
    public static void printShijishoMulti(
    		ACChotarouXMLWriter pd,  VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {
    	
        pd.beginPageEdit("page1");

        String text;
        StringBuffer sb;
        
        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // 訪問看護指示書
            if (((Integer) VRBindPathParser.get("TENTEKI_SIJISYO", data)).intValue() == -1) {
                // 訪問点滴注射指示書
                IkenshoCommon.addString(pd, "title", "訪問看護指示書・在宅患者訪問点滴注射指示書");

            } else {
                IkenshoCommon.addString(pd, "title", "訪問看護指示書");
            }
        } else {
            // 訪問点滴注射指示書
            IkenshoCommon.addString(pd, "title", "在宅患者訪問点滴注射指示書");
        }

        // 氏名
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w2");

        // 生年月日
        Date date = (Date) VRBindPathParser.get("BIRTHDAY", data);
        String era = VRDateParser.format(date, "gg");
        if ("明".equals(era)) {
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("大".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("昭".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        } else if ("平".equals(era)) {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
        } else {
            pd.addAttribute("Shape1", "Visible", "FALSE");
            pd.addAttribute("Shape2", "Visible", "FALSE");
            pd.addAttribute("Shape3", "Visible", "FALSE");
            pd.addAttribute("Shape4", "Visible", "FALSE");
        }
        // 年
        IkenshoCommon.addString(pd, "Grid2.h1.w5", VRDateParser.format(date,"ee"));
        // 月
        IkenshoCommon.addString(pd, "Grid2.h1.w7", VRDateParser.format(date,"MM"));
        // 日
        IkenshoCommon.addString(pd, "Grid2.h1.w9", VRDateParser.format(date,"dd"));

        // 年齢
        IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w11");

        // 郵便番号
        IkenshoCommon.addString(pd, data, "POST_CD", "Grid3.h1.w4");
        // 住所
        IkenshoCommon.addString(pd, data, "ADDRESS", "Grid3.h2.w4");
        // 電話番号
        IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid3.h3.w2");

        // 訪問看護指示期間 開始
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

        // 訪問看護指示期間 終了
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

        // 点滴注射指示期間 開始
        text = String.valueOf(VRBindPathParser.get("TENTEKI_FROM", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "（"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w2", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon
                        .addString(pd, "Grid1.h2.w4", text.substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "Grid1.h2.w1", "（");
        }

        // 点滴注射指示期間 終了
        text = String.valueOf(VRBindPathParser.get("TENTEKI_TO", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "Grid1.h2.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "Grid1.h2.w8", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon.addString(pd, "Grid1.h2.w10", text
                        .substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "Grid1.h2.w12", text.substring(
                            8, 10));
                }
            }
        }

        // 主たる傷病名
        sb = new StringBuffer();
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
        
        //arrayの中にデータが入っているかどうか確認
        if (!array.isEmpty()){
        	
         	//主たる傷病が名が１つしか記載されていないの場合、番号は付けない。
	        if (array.size() == 1){
	        	sb.append(array.get(0));
	        }
	        //主たる傷病名が１つ以上記載されている場合、"（１）"から番号を付ける。
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
        
        //症状･治療状態
        IkenshoCommon.addString(pd, "lblJyotai", getLineBreakedString(data.get("MT_STS")));
        
        // 投与中の薬剤の用法・用量
        addMedicine(pd, data, "1", "sickMedicines8.h1.w4");
        addMedicine(pd, data, "2", "sickMedicines8.h1.w2");
        addMedicine(pd, data, "3", "sickMedicines8.h2.w4");
        addMedicine(pd, data, "4", "sickMedicines8.h2.w2");
        addMedicine(pd, data, "5", "sickMedicines8.h3.w4");
        addMedicine(pd, data, "6", "sickMedicines8.h3.w2");
        addMedicine(pd, data, "7", "sickMedicines8.h4.w4");
        addMedicine(pd, data, "8", "sickMedicines8.h4.w2");
        
        // 寝たきり度
        IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {
                "Shape27", "Shape25", "Shape26", "Shape22", "Shape20",
                "Shape21", "Shape24", "Shape23", "Shape15" }, -1);
        // 痴呆の状態
        IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {
                "Shape28", "Shape17", "Shape18", "Shape13", "Shape11",
                "Shape12", "Shape16", "Shape14" }, -1);

        // 要介護認定の状況
        switch (((Integer) VRBindPathParser.get("YOUKAIGO_JOUKYOU", data))
                .intValue()) {
        case 1: // 自立
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            break;
        case 11:// 要支援
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 21:// 要介護1
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 22:// 要介護2
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 23:// 要介護3
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 24:// 要介護4
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        case 25:// 要介護5
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        default:
            pd.addAttribute("Shape49", "Visible", "FALSE");
            pd.addAttribute("Shape54", "Visible", "FALSE");
            pd.addAttribute("Shape9", "Visible", "FALSE");
            pd.addAttribute("Shape51", "Visible", "FALSE");
            pd.addAttribute("Shape52", "Visible", "FALSE");
            pd.addAttribute("Shape53", "Visible", "FALSE");
            if (printStyle == DOCUMENT_TYPE_HOKEN_SHISETSU) {
                pd.addAttribute("Shape5", "Visible", "FALSE");
            }
            break;
        }
        //褥瘡の深さ：NPUAP分類
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_NPUAP", new String[] {
                "Shape55", "Shape56" }, -1);
        //褥瘡の深さ：DESIGN分類
        IkenshoCommon.addSelection(pd, data, "JOKUSOU_DESIGN", new String[] {
                "Shape57", "Shape58", "Shape59" }, -1);

        // 装着・使用医療機器等
        // 自動腹膜灌流装置
        if (((Integer) VRBindPathParser.get("JD_FUKU", data)).intValue() != 1) {
            pd.addAttribute("Shape36", "Visible", "FALSE");
        }
        // 透析液供給装置
        if (((Integer) VRBindPathParser.get("TOU_KYOUKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape30", "Visible", "FALSE");
        }
        // 酸素療法
        if (((Integer) VRBindPathParser.get("OX_RYO", data)).intValue() != 1) {
            pd.addAttribute("Shape50", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "OX_RYO_RYO", "Grid9.h1.w20");
        }
        // 吸引機
        if (((Integer) VRBindPathParser.get("KYUINKI", data)).intValue() != 1) {
            pd.addAttribute("Shape33", "Visible", "FALSE");
        }
        // 中心静脈栄養
        if (((Integer) VRBindPathParser.get("CHU_JOU_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape32", "Visible", "FALSE");
        }
        // 輸液ポンプ
        if (((Integer) VRBindPathParser.get("YUEKI_PUMP", data)).intValue() != 1) {
            pd.addAttribute("Shape31", "Visible", "FALSE");
        }
        // 経管栄養
        if (((Integer) VRBindPathParser.get("KEKN_EIYOU", data)).intValue() != 1) {
            pd.addAttribute("Shape34", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_METHOD",
                    "Grid9.h3.w23");
            IkenshoCommon
                    .addString(pd, data, "KEKN_EIYOU_SIZE", "Grid9.h3.w14");
            IkenshoCommon.addString(pd, data, "KEKN_EIYOU_CHG", "Grid9.h3.w18");
        }
        // 留置カテーテル
        if (((Integer) VRBindPathParser.get("RYU_CATHETER", data)).intValue() != 1) {
            pd.addAttribute("Shape42", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "RYU_CAT_SIZE", "Grid9.h4.w7");
            IkenshoCommon.addString(pd, data, "RYU_CAT_CHG", "Grid9.h4.w18");
        }
        // 人工呼吸器
        if (((Integer) VRBindPathParser.get("JINKOU_KOKYU", data)).intValue() != 1) {
            pd.addAttribute("Shape41", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_HOUSIKI",
                    "Grid9.h5.w23");
            IkenshoCommon.addString(pd, data, "JINKOU_KKY_SET", "Grid9.h5.w13");
        }
        // 気管カニューレ
        if (((Integer) VRBindPathParser.get("CANNULA", data)).intValue() != 1) {
            pd.addAttribute("Shape40", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "CANNULA_SIZE", "Grid9.h6.w7");
        }
        // ドレーン
        if (((Integer) VRBindPathParser.get("DOREN", data)).intValue() != 1) {
            pd.addAttribute("Shape37", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, data, "DOREN_BUI", "Grid9.h6.w17");
        }
        // 人工肛門
        if (((Integer) VRBindPathParser.get("JINKOU_KOUMON", data)).intValue() != 1) {
            pd.addAttribute("Shape43", "Visible", "FALSE");
        }
        // 人工膀胱
        if (((Integer) VRBindPathParser.get("JINKOU_BOUKOU", data)).intValue() != 1) {
            pd.addAttribute("Shape38", "Visible", "FALSE");
        }
        // その他
        if (((Integer) VRBindPathParser.get("SOUCHAKU_OTHER_FLAG", data))
                .intValue() == 1) {
            Object obj = VRBindPathParser.get("SOUCHAKU_OTHER", data);
            if (IkenshoCommon.isNullText(obj)) {
                pd.addAttribute("Shape35", "Visible", "FALSE");
            } else {
                IkenshoCommon.addString(pd, "Grid9.h7.w6", String.valueOf(obj));
            }
        } else {
            pd.addAttribute("Shape35", "Visible", "FALSE");
        }

        
        //ここで改ページ============================
        pd.endPageEdit();
        pd.beginPageEdit("page2");
        
        //患者氏名と年齢を再掲
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "patientData.h1.w1");
        IkenshoCommon.addString(pd, data, "AGE", "patientData.h1.w3");
        
        
        // 療養生活指導上の留意事項
        IkenshoCommon.addString(pd, "lblRyoyo", getLineBreakedString(data.get("RSS_RYUIJIKOU")));
        
        // リハビリテーション
        if (((Integer) VRBindPathParser.get("REHA_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape46", "Visible", "FALSE");
        } else {
        	IkenshoCommon.addString(pd, "lblRiha", getLineBreakedString(data.get("REHA_SIJI")));
        }
        // 褥瘡
        if (((Integer) VRBindPathParser.get("JOKUSOU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape45", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, "lblJyokusyo", getLineBreakedString(data.get("JOKUSOU_SIJI")));
        }
        // 機器等の操作援助
        if (((Integer) VRBindPathParser.get("SOUCHAKU_SIJI_UMU", data))
                .intValue() != 1) {
            pd.addAttribute("Shape44", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, "lblSochaku", getLineBreakedString(data.get("SOUCHAKU_SIJI")));
        }
        // その他
        if (((Integer) VRBindPathParser.get("RYUI_SIJI_UMU", data)).intValue() != 1) {
            pd.addAttribute("Shape47", "Visible", "FALSE");
        } else {
            IkenshoCommon.addString(pd, "lblEtc", getLineBreakedString(data.get("RYUI_SIJI")));
        }

        // 点滴注射指示
        IkenshoCommon.addString(pd, "lblZaitaku", getLineBreakedString(data.get("TENTEKI_SIJI")));

        // 緊急時の連絡先
        IkenshoCommon.addString(pd, data, "KINKYU_RENRAKU", "Grid11.h1.w2");

        // 不在時の対応法
        IkenshoCommon.addString(pd, data, "FUZAIJI_TAIOU", "Grid11.h2.w2");

        // 特記すべき留意事項
        IkenshoCommon.addString(pd, "lblTokki", getLineBreakedString(data.get("SIJI_TOKKI")));

        // 他の訪問看護ステーションへの指示
        if (((Integer) VRBindPathParser.get("OTHER_STATION_SIJI", data))
                .intValue() != 2) {
            pd.addAttribute("Shape39", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", " 殿");
        } else {
            pd.addAttribute("Shape48", "Visible", "FALSE");
            IkenshoCommon.addString(pd, "Grid12.h4.w8", stationName2 + " 殿");
        }

        // 記入日
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "Label2", VRDateParser.format(
                        (Date) obj, "gggee年MM月dd日"));
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

        // 対象の訪問看護ステーション
        IkenshoCommon.addString(pd, "Label6", stationName1 + " 殿");

        pd.endPageEdit();

    }
    
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
    
    
    //[ID:0000635][Shin Fujihara] 2011/02/25 add end 【2010年度要望対応】
    

    /**
     * 薬剤情報を出力します。
     * 
     * @param pd XML生成クラス
     * @param data データソース
     * @param index インデックス
     * @param target 出力先
     * @throws Exception 解析例外
     */
    protected static void addMedicine(ACChotarouXMLWriter pd, VRMap data,
            String index, String target) throws Exception {
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
            sb.append(String.valueOf(obj));
        }
        IkenshoCommon.addString(pd, target, sb.toString());
    }

    private void jbInit() throws Exception {
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//        contentPane = (JPanel) this.getContentPane();
//        contentPane.add(contents);
//
//        contents.setLayout(new VRLayout());
//        contents.add(styleGroup, VRLayout.NORTH);
//        contents.add(sendToGroup, VRLayout.NORTH);
//        contents.add(buttons, VRLayout.NORTH);
//
//        // 帳票様式
//        styleGroup.setText("帳票様式（老人保健施設以外の施設は「医療機関他」を選択してください）");
//        styleGroup.setLayout(new BorderLayout());
//        styleGroup.add(style, BorderLayout.CENTER);
//        VRLayout styleLayout = new VRLayout();
//        styleLayout.setAutoWrap(false);
//        style.setLayout(styleLayout);
//        style.setUseClearButton(false);
//        style.setModel(new VRListModelAdapter(new VRArrayList(Arrays
//                .asList(new String[] { "医療機関他", "老人保健施設" }))));
//        style.setSelectedIndex(1);
//
//        // 宛先となる訪問看護ステーション
//        sendToGroup.setText("宛先となる訪問看護ステーションを選択してください。（複数選択時のみ選択可能）");
//        sendToGroup.setLayout(new VRLayout());
//        sendToGroup.add(sendTo, VRLayout.CLIENT);
//        sendTo.setUseClearButton(false);
//
//        // 下部ボタン
//        buttons.setLayout(new VRLayout());
//        buttons.add(cancel, VRLayout.EAST);
//        buttons.add(ok, VRLayout.EAST);
//        ok.setText("印刷(O)");
//        ok.setMnemonic('O');
//        cancel.setText("キャンセル(C)");
//        cancel.setMnemonic('C');
        
        ((JPanel) this.getContentPane()).add(getContents());

        getContents().setLayout(new VRLayout());
        getContents().add(getStyleGroup(), VRLayout.NORTH);
        getContents().add(getSendToGroup(), VRLayout.NORTH);
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

        // 宛先となる訪問看護ステーション
        getSendToGroup().setText("宛先となる訪問看護ステーションを選択してください。（複数選択時のみ選択可能）");
        getSendToGroup().setLayout(new VRLayout());
        getSendToGroup().add(getSendTo(), VRLayout.CLIENT);
        getSendTo().setUseClearButton(false);

        // 下部ボタン
        getButtons().setLayout(new VRLayout());
        getButtons().add(getCancel(), VRLayout.EAST);
        getButtons().add(getOk(), VRLayout.EAST);
        getOk().setText("印刷(O)");
        getOk().setMnemonic('O');
        getCancel().setText("キャンセル(C)");
        getCancel().setMnemonic('C');
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    }

    /**
     * 位置を初期化します。
     */
    private void init() {
        // ウィンドウのサイズ
        setSize(new Dimension(600, 200));
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

    protected void processWindowEvent(WindowEvent e) {
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSING) {
            closeWindow();
        }
    }

    protected void closeWindow() {
        // 自身を破棄する
        this.dispose();
    }

    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
//    private class IkenshoVirticalRadioButtonGroup extends
//            ACClearableRadioButtonGroup {
//        public IkenshoVirticalRadioButtonGroup() {
//            super();
//            setLayout(new VRLayout());
//        }
//
//        protected void addRadioButton(JRadioButton item) {
//            this.add(item, VRLayout.FLOW_RETURN);
//        }
//    }

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
     * ok を返します。
     * @return ok
     */
    protected ACButton getOk() {
        if(ok==null){
            ok = new ACButton();
        }
        return ok;
    }

    /**
     * sendTo を返します。
     * @return sendTo
     */
    protected IkenshoVirticalRadioButtonGroup getSendTo() {
        if(sendTo==null){
            sendTo = new IkenshoVirticalRadioButtonGroup();
        }
        return sendTo;
    }

    /**
     * sendToGroup を返します。
     * @return sendToGroup
     */
    protected ACGroupBox getSendToGroup() {
        if(sendToGroup==null){
            sendToGroup = new ACGroupBox();
        }
        return sendToGroup;
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
     * styleGroup を返します。
     * @return styleGroup
     */
    protected ACGroupBox getStyleGroup() {
        if(styleGroup==null){
            styleGroup = new ACGroupBox();
        }
        return styleGroup;
    }
    // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
    
    //2009/01/21 [Tozo Tanaka] Add - begin
    protected static int getMedicineViewCount() {
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//        int ikenshoCount = 6;
//        int shijishoCount = ikenshoCount;
//        try {
//            if (ACFrame.getInstance().hasProperty(
//                    "DocumentSetting/MedicineViewCount")
//                    && ACCastUtilities.toInt(ACFrame.getInstance().getProperty(
//                            "DocumentSetting/MedicineViewCount"), 6) == 8) {
//                ikenshoCount = 8;
//            }
//
//            shijishoCount = ikenshoCount;
//            if (ACFrame
//                    .getInstance()
//                    .hasProperty(
//                            "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6")
//                    && ACCastUtilities
//                            .toBoolean(
//                                    ACFrame
//                                            .getInstance()
//                                            .getProperty(
//                                                    "DocumentSetting/MedicineViewCountOfHoumonKangoShijishoFixed6"),
//                                    false)) {
//                // 指示書設定を優先
//                shijishoCount = 6;
//            }
//
//        } catch (Exception e) {
//        }
//        return shijishoCount;
        return 8;
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
    }
    /**
     * 指定された文字数で改行した文字列を返します。
     * @param chr 改行対象となる文字列
     * @param byteIndex 改行基準バイト数
     * @return 改行文字を挿入した文字列
     */
    public static String getInsertionLineSeparatorToStringOnByte(String chr, int byteIndex){
        String[] slCharacter = ACTextUtilities.separateLineWrapOnByte(chr,byteIndex);
        
        StringBuffer sbCharacter = new StringBuffer();

        for (int i = 0; i < slCharacter.length; i++) {
            sbCharacter.append(slCharacter[i]);
            //最終行である場合は改行コードを追加しない
            if (i != slCharacter.length - 1) {
                //改行コードを追加する
                sbCharacter.append(ACConstants.LINE_SEPARATOR);
            }
        }

        String insertionLineSeparatorString = sbCharacter.toString();
        
        return insertionLineSeparatorString;
    }
    //2009/01/21 [Tozo Tanaka] Add - end}

}
