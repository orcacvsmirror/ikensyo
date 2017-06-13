package jp.or.med.orca.ikensho.affair;

import java.awt.HeadlessException;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
public class IkenshoSeishinkaTokubetsuHoumonKangoShijishoPrintSetting extends IkenshoSeishinkaHoumonKangoShijishoPrintSetting {
    
    /**
     * コンストラクタ
     * @param data 印刷データ
     */
    public IkenshoSeishinkaTokubetsuHoumonKangoShijishoPrintSetting(VRMap data) throws HeadlessException {
        super(data);
    }
    
    /**
     * モーダル表示し、印刷されたかを返します
     * @return 印刷されたか
     */
    public boolean showModal() {
        // 印刷処理
        print();
        // 終了処理
        dispose();
        return true;
    }
    
    /**
     * 印刷処理
     */
    protected void print() {
        try {
            getContents().applySource();
            ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
            // 印刷開始
            pd.beginPrintEdit();
            // 定義体設定
            ACChotarouXMLUtilities.addFormat(pd, "page1", "SpecialSeishinsho.xml");
            // 印刷処理
            printShijisho(pd, source);
            // 印刷終了
            pd.endPrintEdit();
            // PDFオープン
            IkenshoCommon.openPDF(pd);
            
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
    }
    
    /**
     * 精神科特別訪問看護指示書印刷処理
     * @param pd XML生成クラス
     * @param data データソース
     */
    public static void printShijisho(ACChotarouXMLWriter pd, VRMap data) throws Exception {
        
        pd.beginPageEdit("page1");
        
        // タイトル
        if (((Integer) VRBindPathParser.get("HOUMON_SIJISYO", data)).intValue() == -1) {
            // 精神科特別訪問看護指示書・在宅患者訪問点滴注射指示書
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
        
        // 特別看護指示期間 開始
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
        // 特別看護指示期間 終了
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
        
        // 症状・主訴
        IkenshoCommon.addString(pd, "SYOJYO", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_SHOJO_SHUSO")), 100)));
        // 複数名訪問の必要性
        IkenshoCommon.addSelection(pd, data, "FUKUSU_HOUMON", new String[] { "Label16", "Label17" }, -1);
        // 複数名訪問の必要性：理由
        IkenshoCommon.addString(pd, "lblFkRiyu", String.valueOf(VRBindPathParser.get("FUKUSU_HOUMON_RIYU", data)));
        // 短時間訪問の必要性
        IkenshoCommon.addSelection(pd, data, "TANJIKAN_HOUMON", new String[] { "Label18", "Label19" }, -1);
        // 短時間訪問の必要性：理由
        IkenshoCommon.addString(pd, "lblTanRiyu", String.valueOf(VRBindPathParser.get("TANJIKAN_HOUMON_RIYU", data)));
        // 留意事項及び指示事項
        IkenshoCommon.addString(pd, "RYUI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_RYUI")), 100)));
        
        int intValue = 0;
        
        // 服薬確認
        intValue = ((Integer) VRBindPathParser.get("FUKUYAKU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblFukuyakuUmu", "Visible", "FALSE");
        }
        // 水分及び食物摂取の状況
        intValue = ((Integer) VRBindPathParser.get("SUIBUN_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblSuibunUmu", "Visible", "FALSE");
        }
        // 精神症状
        intValue = ((Integer) VRBindPathParser.get("SEISHIN_SYOUJYOU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblSeishinUmu", "Visible", "FALSE");
        }
        // 精神症状（観察が必要な項目）
        if (intValue == 1) {
            IkenshoCommon.addString(pd, "lblSeishinKansatu", String.valueOf(VRBindPathParser.get("SEISHIN_SYOUJYOU", data)));
        }
        // 身体症状
        intValue = ((Integer) VRBindPathParser.get("SHINTAI_SYOUJYOU_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblShintaiUmu", "Visible", "FALSE");
        }
        // 身体症状（観察が必要な項目）
        if (intValue == 1) {
            IkenshoCommon.addString(pd, "lblShintaiKansatu", String.valueOf(VRBindPathParser.get("SHINTAI_SYOUJYOU", data)));
        }
        // その他
        intValue = ((Integer) VRBindPathParser.get("KANSATU_OTHER_UMU", data)).intValue();
        if (intValue != 1) {
            pd.addAttribute("lblEtcUmu", "Visible", "FALSE");
        }
        // その他内容
        if (intValue == 1) {
            IkenshoCommon.addString(pd, "lblEtcNaiyo", String.valueOf(VRBindPathParser.get("KANSATU_OTHER", data)));
        }
        
        // 点滴注射指示
        IkenshoCommon.addString(pd, "TENTEKI", ACTextUtilities
                .concatLineWrap(ACTextUtilities
                        .separateLineWrapOnByte(ACCastUtilities.toString(data
                                .get("TOKUBETSU_CHUSHA_SHIJI")), 100)));
        // 緊急時の連絡先
        StringBuffer sb = new StringBuffer();
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
                IkenshoCommon.addString(pd, "KinyuDateLabell", VRDateParser.format((Date) obj, "gggee年MM月dd日"));
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
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End