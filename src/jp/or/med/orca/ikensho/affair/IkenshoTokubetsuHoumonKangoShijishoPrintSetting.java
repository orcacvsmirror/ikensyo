package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

//[ID:0000514][Tozo TANAKA] 2009/09/07 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
public class IkenshoTokubetsuHoumonKangoShijishoPrintSetting extends
        IkenshoHoumonKangoShijishoPrintSetting {

    public IkenshoTokubetsuHoumonKangoShijishoPrintSetting(VRMap data)
            throws HeadlessException {
        super(data);
        setTitle("「特別訪問看護指示書」印刷設定");
        getStyleGroup().setVisible(false);

        
        // ウィンドウのサイズ
        setSize(new Dimension(600, 150));
        
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

    protected void print() {
        try {
            getContents().applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();

            pd.beginPrintEdit();
            int printStyle = getStyle().getSelectedIndex();

            ACChotarouXMLUtilities.addFormat(pd, "page1", "SpecialShijisho.xml");

            
            String station1 = String.valueOf(VRBindPathParser.get("STATION_NM",
                    source));
            String station2 = String.valueOf(VRBindPathParser.get(
                    "OTHER_STATION_NM", source));
            if (getSendTo().isEnabled()) {
                switch (getSendTo().getSelectedIndex()) {
                case 1: // 2枚
                    printShijisho(pd, "page1", source, station1, station2,
                            printStyle);
                    printShijisho(pd, "page1", source, station2, station1,
                            printStyle);
                    break;
                case 2: // 前者のみ
                    printShijisho(pd, "page1", source, station1, station2,
                            printStyle);
                    break;
                case 3: // 後者のみ
                    printShijisho(pd, "page1", source, station2, station1,
                            printStyle);
                    break;
                }
            } else {
                // 単一のステーションのみ
                printShijisho(pd, "page1", source, station1, station2,
                        printStyle);
            }

            pd.endPrintEdit();

            IkenshoCommon.openPDF(pd);

        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }

    }
    public static void printShijisho(ACChotarouXMLWriter pd, String formatName,
            VRMap data, String stationName1, String stationName2, int printStyle)
            throws Exception {

        pd.beginPageEdit(formatName);

        String text;
        StringBuffer sb;

        // 氏名
        IkenshoCommon.addString(pd, data, "PATIENT_NM", "KanjyaGrid.h1.w2");

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
        IkenshoCommon.addString(pd, "KanjyaGrid.h1.w5", VRDateParser.format(date,
                "ee"));
        // 月
        IkenshoCommon.addString(pd, "KanjyaGrid.h1.w7", VRDateParser.format(date,
                "MM"));
        // 日
        IkenshoCommon.addString(pd, "KanjyaGrid.h1.w9", VRDateParser.format(date,
                "dd"));

        // 年齢
        IkenshoCommon.addString(pd, data, "AGE", "KanjyaGrid.h1.w11");

        // 特別訪問看護指示期間 開始
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_FROM", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w1", "（"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w2", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon
                        .addString(pd, "SijiDateGrid.h1.w4", text.substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h1.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w1", "（");
        }

        // 特別訪問看護指示期間 終了
        text = String.valueOf(VRBindPathParser.get("SIJI_KIKAN_TO", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h1.w8", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon.addString(pd, "SijiDateGrid.h1.w10", text
                        .substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h1.w12", text.substring(
                            8, 10));
                }
            }
        }

        // 点滴注射指示期間 開始
        text = String.valueOf(VRBindPathParser.get("TENTEKI_FROM", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w1", "（"
                    + text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w2", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon
                        .addString(pd, "SijiDateGrid.h2.w4", text.substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h2.w6", text.substring(
                            8, 10));
                }
            }
        } else {
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w1", "（");
        }

        // 点滴注射指示期間 終了
        text = String.valueOf(VRBindPathParser.get("TENTEKI_TO", data));
        if (text.indexOf("0000年") < 0) {
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w15", text.substring(0, 2));
            IkenshoCommon.addString(pd, "SijiDateGrid.h2.w8", text.substring(2, 4));
            if (text.indexOf("00月") < 0) {
                IkenshoCommon.addString(pd, "SijiDateGrid.h2.w10", text
                        .substring(5, 7));
                if (text.indexOf("00日") < 0) {
                    IkenshoCommon.addString(pd, "SijiDateGrid.h2.w12", text.substring(
                            8, 10));
                }
            }
        }


        // 症状・主訴
        IkenshoCommon.addString(pd, "SYOJYO", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_SHOJO_SHUSO")), 100)));

        // 留意事項及び指示事項
        IkenshoCommon.addString(pd, "RYUI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_RYUI")), 100)));

        // 点滴注射指示
        IkenshoCommon.addString(pd, "TENTEKI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_CHUSHA_SHIJI")), 100)));

        // 緊急時の連絡先
        sb = new StringBuffer();
        sb.append(ACCastUtilities.toString(VRBindPathParser.get("KINKYU_RENRAKU", data)));
        if(sb.length() > 0){
            sb.append(ACConstants.LINE_SEPARATOR);
        }
        sb.append(ACTextUtilities.concatLineWrap(ACTextUtilities
                .separateLineWrapOnByte(ACCastUtilities.toString(data
                        .get("TOKUBETSU_KINKYU_RENRAKU")), 100)));
        if(!ACTextUtilities.isNullText(VRBindPathParser.get("FUZAIJI_TAIOU", data))){
            if(sb.length() > 0){
                sb.append(ACConstants.LINE_SEPARATOR);
            }
            sb.append("不在時の対応法　"+VRBindPathParser.get("FUZAIJI_TAIOU", data));
        }
        IkenshoCommon.addString(pd, "KINKYU", sb.toString());


        // 記入日
        if (VRBindPathParser.has("KINYU_DT", data)) {
            Object obj = VRBindPathParser.get("KINYU_DT", data);
            if (obj instanceof Date) {
                IkenshoCommon.addString(pd, "KinyuDateLabell", VRDateParser.format(
                        (Date) obj, "gggee年MM月dd日"));
            }
        }

        // 医療機関名
        IkenshoCommon.addString(pd, data, "MI_NM", "IryoKikanGrid.h1.w2");

        // 医療機関電話番号
        IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "IryoKikanGrid.h3.w2");

        // 医療機関FAX番号
        IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "IryoKikanGrid.h4.w2");

        // 医師氏名
        IkenshoCommon.addString(pd, data, "DR_NM", "IryoKikanGrid.h5.w2");

        // 対象の訪問看護ステーション
        IkenshoCommon.addString(pd, "StationNameLabel", stationName1 + " 殿");

        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // 特別訪問看護指示書・在宅患者訪問点滴注射指示書
            ACChotarouXMLUtilities.setVisible(pd, "TITLE_TENTEKI", false);
            if (((Integer) VRBindPathParser.get("TENTEKI_SIJISYO", data))
                    .intValue() != -1) {
                //特別訪問看護指示書
                ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU_TENTEKI", false);
            }else{
                ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU", false);
            }
        } else {
            // 在宅患者訪問点滴注射指示書
            ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU", false);
            ACChotarouXMLUtilities.setVisible(pd, "TITLE_TOKUBETU_TENTEKI", false);
        }
        
        
        pd.endPageEdit();

    }
    public boolean showModal() {
//        try {
//            boolean useOtherStation = ((Integer) VRBindPathParser.get(
//                    "OTHER_STATION_SIJI", source)).intValue() == 2;
//
//            if (useOtherStation) {
//                String station1 = String.valueOf(VRBindPathParser.get(
//                        "STATION_NM", source));
//                String station2 = String.valueOf(VRBindPathParser.get(
//                        "OTHER_STATION_NM", source));
//                if (("".equals(station1)) || ("".equals(station2))) {
//                    useOtherStation = false;
//                }
//            }
//
//            if (!useOtherStation) {
                getSendToGroup().setEnabled(false);
                getSendTo().setEnabled(false);
                getSendTo().setModel(new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "", "", "" }))));
                
                print();
                dispose();
                return true;
//            }
//        } catch (ParseException ex) {
//            IkenshoCommon.showExceptionMessage(ex);
//            return false;
//        }
//        
//        setVisible(true);
//        return printed;

    }

}
//[ID:0000514][Tozo TANAKA] 2009/09/07 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
