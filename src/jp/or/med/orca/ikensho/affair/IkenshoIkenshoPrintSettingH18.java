package jp.or.med.orca.ikensho.affair;

import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.picture.IkenshoHumanPicture;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoPrintSettingH18
    extends IkenshoIkenshoPrintSetting {
  /**
   * コンストラクタです。
   * @param data 印刷データ
   * @param picture 全身図
   * @throws HeadlessException 初期化例外
   */
  public IkenshoIkenshoPrintSettingH18(VRMap data, IkenshoHumanPicture picture) throws
      HeadlessException {
    super(data, picture);
  }

  protected String getIkenshoFormatFilePath1() {
      return "NewIkensho1.xml";
//    return ACFrame.getInstance().getExeFolderPath() + IkenshoConstants.FILE_SEPARATOR +
//        "format" + IkenshoConstants.FILE_SEPARATOR + "NewIkensho1.xml";
  }

  protected String getIkenshoFormatFilePath2() {
      return "NewIkensho2.xml";
//    return ACFrame.getInstance().getExeFolderPath() + IkenshoConstants.FILE_SEPARATOR +
//        "format" +IkenshoConstants.FILE_SEPARATOR + "NewIkensho2.xml";
  }

  protected void callPrintIkensho(ACChotarouXMLWriter pd, String formatName,
                                  VRMap data, Date printDate) throws
      Exception {
      IkenshoIkenshoPrintSettingH18.printIkensho(pd, formatName, data, printDate);
  }

  protected void callPrintIkensho2(ACChotarouXMLWriter pd, String formatName,
                                       VRMap data, Date printDate,
                                       byte[] imageBytes) throws
      Exception {
      IkenshoIkenshoPrintSettingH18.printIkensho2(pd, formatName, data, printDate,
                                                imageBytes);
  }

  public static void printIkensho(ACChotarouXMLWriter pd, String formatName,
                                  VRMap data, Date printDate) throws
      Exception {

    pd.beginPageEdit(formatName);

    printIkenshoHeader(pd, data, printDate);

    //ふりがな
    IkenshoCommon.addString(pd, data, "PATIENT_KN", "Grid1.h1.w3");
    //氏名
    IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid1.h2.w3");

    //生年月日
    Date date = (Date)VRBindPathParser.get("BIRTHDAY", data);
    String era = VRDateParser.format(date, "gg");
    if ("明".equals(era)) {
      pd.addAttribute("Shape2", "Visible", "FALSE");
      pd.addAttribute("Shape3", "Visible", "FALSE");
    }
    else if ("大".equals(era)) {
      pd.addAttribute("Shape1", "Visible", "FALSE");
      pd.addAttribute("Shape3", "Visible", "FALSE");
    }
    else if ("昭".equals(era)) {
      pd.addAttribute("Shape1", "Visible", "FALSE");
      pd.addAttribute("Shape2", "Visible", "FALSE");
    }
    else {
      pd.addAttribute("Shape1", "Visible", "FALSE");
      pd.addAttribute("Shape2", "Visible", "FALSE");
      pd.addAttribute("Shape3", "Visible", "FALSE");
    }
    //年
    IkenshoCommon.addString(pd, "Grid1.h3.w3", VRDateParser.format(date, "ee"));
    //月
    IkenshoCommon.addString(pd, "Grid1.h3.w5", VRDateParser.format(date, "MM"));
    //日
    IkenshoCommon.addString(pd, "Grid1.h3.w7", VRDateParser.format(date, "dd"));

    //年齢
    IkenshoCommon.addString(pd, data, "AGE", "Grid1.h3.w10");

    //性別
    IkenshoCommon.addSelection(pd, data, "SEX", new String[] {"Shape4",
                               "Shape5"}
                               , -1);

    //郵便番号
    IkenshoCommon.addZip(pd, data, "POST_CD", "Grid1.h1.w11", "Grid1.h1.w17");
    //住所
    IkenshoCommon.addString(pd, data, "ADDRESS", "Grid1.h2.w12");
    //電話番号
    IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid1.h3.w17",
                         "Grid1.h3.w19", "Grid1.h3.w21");

    //記入日
    IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 2, 1);

    //医師同意
    IkenshoCommon.addSelection(pd, data, "DR_CONSENT", new String[] {"Shape6",
                               "Shape7"}
                               , -1);

    if ( ( (Integer) VRBindPathParser.get("DR_NM_OUTPUT_UMU", data)).intValue() ==
        1) {
      //医師氏名
      IkenshoCommon.addString(pd, data, "DR_NM", "Grid4.h1.w3");
    }
    //医療機関名
    IkenshoCommon.addString(pd, data, "MI_NM", "Grid4.h2.w4");
    //医療機関所在地
    IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid4.h3.w5");

    //医療機関電話番号
    IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid5.h1.w2",
                         "Grid5.h1.w4", "Grid5.h1.w6");

    //医療機関FAX番号
    IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid5.h3.w2",
                         "Grid5.h3.w4", "Grid5.h3.w6");

    //最終診察日
    date = (Date)VRBindPathParser.get("LASTDAY", data);
    if(date != null){
      //元号
      IkenshoCommon.addString(pd, "Grid6.h1.w12", VRDateParser.format(date, "ggg"));
      //年
      IkenshoCommon.addString(pd, "Grid6.h1.w11", VRDateParser.format(date, "ee"));
      //月
      IkenshoCommon.addString(pd, "Grid6.h1.w9", VRDateParser.format(date, "MM"));
      //日
      IkenshoCommon.addString(pd, "Grid6.h1.w7", VRDateParser.format(date, "dd"));
    }

    //意見書作成回数
    IkenshoCommon.addSelection(pd, data, "IKN_CREATE_CNT",
                               new String[] {"Shape8",
                               "Shape9"}
                               , -1);

    if(( (Integer) VRBindPathParser.get("TAKA_FLAG", data)).intValue()==1){
      //他科受診
      int taka = ( (Integer) VRBindPathParser.get("TAKA", data)).intValue();
      if (taka == 0) {
        //他科内容
        for (int i = 0; i < 12; i++) {
          pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
        }
        //他科その他
        if ( ( (Integer) VRBindPathParser.get("TAKA_OTHER_FLAG", data)).intValue() == 1) {
          IkenshoCommon.addString(pd, data, "TAKA_OTHER", "Grid6.h3.w4");
          pd.addAttribute("Shape11", "Visible", "FALSE");
        }else{
          pd.addAttribute("Shape10", "Visible", "FALSE");
          pd.addAttribute("Shape24", "Visible", "FALSE");
        }
      }
      else {
        pd.addAttribute("Shape11", "Visible", "FALSE");
        //他科内容
        for (int i = 0; i < 12; i++) {
          if ( (taka & (1 << i)) == 0) {
            pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
          }
        }
        //他科その他
        if ( ( (Integer) VRBindPathParser.get("TAKA_OTHER_FLAG", data)).intValue() == 1) {
          IkenshoCommon.addString(pd, data, "TAKA_OTHER", "Grid6.h3.w4");
        }else{
          pd.addAttribute("Shape24", "Visible", "FALSE");
        }
      }
    }else{
      pd.addAttribute("Shape10", "Visible", "FALSE");
      //他科内容
      for (int i = 0; i < 12; i++) {
        pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
      }
      //他科その他
      pd.addAttribute("Shape24", "Visible", "FALSE");
    }

    //傷病
    final String SPACER = "  ";
    for (int i = 0; i < 3; i++) {
      IkenshoCommon.addString(pd, data, "SINDAN_NM" + (i + 1),
                              "Grid8.h" + (i + 1) + ".w2");
      String text = String.valueOf(VRBindPathParser.get("HASHOU_DT" + (i + 1),
          data));
      boolean eraPrinted = false;
      String eYear="";
      if (text.length() == 11) {
         if(text.startsWith("不詳")){
             //元号
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w15", "不詳"); 
             //年月日を削る
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w13", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w11", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w9", "");
         }else if(text.startsWith("0000")){
             //年月日を削る
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w13", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w11", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w9", "");
         }else{
             date = VRDateParser.parse(text.replaceAll("00月", "01月").replaceAll("00日", "01日"));
             era = VRDateParser.format(date, "ggg");
             eYear = VRDateParser.format(date, "ee");
             //元号
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w15", era); 
//          if ("昭和".equals(era)) {
//            pd.addAttribute("Shape" + (95 + i * 2), "Visible", "FALSE");
//          }
//          else if ("平成".equals(era)) {
//            pd.addAttribute("Shape" + (94 + i * 2), "Visible", "FALSE");
//          }
          eraPrinted = true;
        }
      }
      if (eraPrinted) {
        text = text.replaceAll("00", SPACER);
        String head = "Grid8.h" + (i + 1) + ".w";

        IkenshoCommon.addString(pd, head + 14, eYear);
        String val;
        val = text.substring(5, 7);
        if (!SPACER.equals(val)) {
          IkenshoCommon.addString(pd, head + 12, val);
        }
        val = text.substring(8, 10);
        if (!SPACER.equals(val)) {
          IkenshoCommon.addString(pd, head + 10, val);
        }
      }
      else {
        pd.addAttribute("Shape" + (95 + i * 2), "Visible", "FALSE");
        pd.addAttribute("Shape" + (94 + i * 2), "Visible", "FALSE");
      }

    }

    //症状安定性
    //不安定における具体的な状況
    IkenshoCommon.addSelection(pd, data, "SHJ_ANT", new String[] {"Shape25",
                               "Shape26", "Shape27"}
                               , -1, "INSECURE_CONDITION", "Label12", 2);

    //2009/01/09 [Tozo Tanaka] Replace - begin
//    //傷病治療状態
//    IkenshoCommon.addString(pd, data, "MT_STS", "Label3");
//
//    //薬剤
//    for (int i = 0; i < 3; i++) {
//        for (int j = 0; j < 2; j++) {
//          StringBuffer sb = new StringBuffer();
//          String text;
//          String index = String.valueOf(i * 2 + j + 1);
//          text = (String) VRBindPathParser.get("MEDICINE" + index, data);
//          if (!IkenshoCommon.isNullText(text)) {
//            sb.append(text);
//            sb.append(" ");
//          }
//          text = (String) VRBindPathParser.get("DOSAGE" + index, data);
//          if (!IkenshoCommon.isNullText(text)) {
//            sb.append(text);
//          }
//          text = (String) VRBindPathParser.get("UNIT" + index, data);
//          if (!IkenshoCommon.isNullText(text)) {
//            sb.append(text);
//          }
//          text = (String) VRBindPathParser.get("USAGE" + index, data);
//          if (!IkenshoCommon.isNullText(text)) {
//            sb.append(" ");
//            sb.append(text);
//          }
//          if (sb.length() > 0) {
//            IkenshoCommon.addString(pd, "Grid9.h" + (i + 1) + ".w" + (j + 1), sb.toString());
//          }
//        }
//      }
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//    //傷病治療状態
//    ACChotarouXMLUtilities.setValue(pd, "Label3",
//            getInsertionLineSeparatorToStringOnByte(ACCastUtilities.toString(data
//                    .get("MT_STS")), 100));
//    
//    //薬剤
//    String sickMedicinesGridName = "Grid9";
//    int sickMedicinesRows = 3;
//    if (!(
//            IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", data)) && 
//            IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", data)) &&
//            IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", data)) &&
//            IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", data)) &&
//            IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", data)) &&
//            IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", data)) &&
//            IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", data)) && 
//            IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", data)) 
//            )&&
//            (getMedicineViewCount()>6)) {
//        //薬剤7か薬剤8が入力されている場合
//        sickMedicinesGridName = "sickMedicines8";
//        sickMedicinesRows = 4;
//    }
//    
//    for (int i = 0; i < sickMedicinesRows; i++) {
//      for (int j = 0; j < 2; j++) {
//        StringBuffer sb = new StringBuffer();
//        String text;
//        String index = String.valueOf(i * 2 + j + 1);
//        text = (String) VRBindPathParser.get("MEDICINE" + index, data);
//        if (!IkenshoCommon.isNullText(text)) {
//          sb.append(text);
//          sb.append(" ");
//        }
//        text = (String) VRBindPathParser.get("DOSAGE" + index, data);
//        if (!IkenshoCommon.isNullText(text)) {
//          sb.append(text);
//        }
//        text = (String) VRBindPathParser.get("UNIT" + index, data);
//        if (!IkenshoCommon.isNullText(text)) {
//          sb.append(text);
//        }
//        text = (String) VRBindPathParser.get("USAGE" + index, data);
//        if (!IkenshoCommon.isNullText(text)) {
//          sb.append(" ");
//          sb.append(text);
//        }
//        if (sb.length() > 0) {
//          IkenshoCommon.addString(pd, sickMedicinesGridName+".h" + (i + 1) + ".w" + (j + 1), sb.toString());
//        }
//      }
//    }
    
    StringBuffer sbSickProgress = new StringBuffer();

    // 傷病治療状態
    String text = ACCastUtilities.toString(data.get("MT_STS"));
    String[] sickProgressLines = ACTextUtilities.separateLineWrapOnByte(
            text, 100);
    int totalLineCount = sickProgressLines.length;
    sbSickProgress
            .append(ACTextUtilities.concatLineWrap(sickProgressLines));

    text = text.replaceAll("[\r\n]", "");
    int totalCharCount = text.length();
    int totalByteCount = text.getBytes().length;

    // 薬剤
    boolean isUseOver6Medicine = false;
    for (int index = 7; index <= 8; index++) {
        text = (String) VRBindPathParser.get("MEDICINE" + index, data);
        if (!IkenshoCommon.isNullText(text)) {
            isUseOver6Medicine = true;
            break;
        }
        text = (String) VRBindPathParser.get("DOSAGE" + index, data);
        if (!IkenshoCommon.isNullText(text)) {
            isUseOver6Medicine = true;
            break;
        }
        text = (String) VRBindPathParser.get("UNIT" + index, data);
        if (!IkenshoCommon.isNullText(text)) {
            isUseOver6Medicine = true;
            break;
        }
        text = (String) VRBindPathParser.get("USAGE" + index, data);
        if (!IkenshoCommon.isNullText(text)) {
            isUseOver6Medicine = true;
            break;
        }
    }

    if (totalByteCount > 500 || totalLineCount > 5
            || isUseOver6Medicine) {
        // 傷病の経過が251文字以上または6行以上または薬剤名が6個以上の場合
        int lastLineByteCount = 0;
        StringBuffer sbMedicine = new StringBuffer();
        for (int index = 1; index <= 8; index++) {
            StringBuffer sb = new StringBuffer();
            int medicineCharLen = 0;
            int dosageCharLen = 0;
            int unitCharLen = 0;
            int usageCharLen = 0;
            // 薬剤セットを結合
            text = (String) VRBindPathParser.get("MEDICINE" + index, data);
            if (!IkenshoCommon.isNullText(text)) {
                sb.append(text);
                sb.append(" ");
                medicineCharLen = text.length();
            }
            text = (String) VRBindPathParser.get("DOSAGE" + index, data);
            if (!IkenshoCommon.isNullText(text)) {
                sb.append(text);
                dosageCharLen = text.length();
            }
            text = (String) VRBindPathParser.get("UNIT" + index, data);
            if (!IkenshoCommon.isNullText(text)) {
                sb.append(text);
                unitCharLen = text.length();
            }
            text = (String) VRBindPathParser.get("USAGE" + index, data);
            if (!IkenshoCommon.isNullText(text)) {
                sb.append(" ");
                sb.append(text);
                usageCharLen = text.length();
            }

            // 連結して表示するか改行するかを判定
            int lineCharLen = medicineCharLen + dosageCharLen + unitCharLen
                    + usageCharLen;
            int lineByetLen = sb.toString().getBytes().length;
            if (lastLineByteCount + lineByetLen
                    + ((lastLineByteCount > 0) ? 2 : 0) > 100) {
                sbMedicine.append(IkenshoConstants.LINE_SEPARATOR);
                lastLineByteCount = lineByetLen;
            } else {
                if (lastLineByteCount > 0) {
                    sbMedicine.append("  ");
                    lastLineByteCount += 2;
                }
                lastLineByteCount += lineByetLen;
            }
            // 最大文字数制限を判定
            if (totalCharCount + lineCharLen > 560) {
                int useCharCount = 560 - totalCharCount;
                int lastPos = 0;
                // 薬剤名
                if (useCharCount > medicineCharLen) {
                    lastPos += medicineCharLen + 1;
                    useCharCount -= medicineCharLen;
                    // 用量
                    if (useCharCount > dosageCharLen) {
                        lastPos += dosageCharLen;
                        useCharCount -= dosageCharLen;
                        // 用量単位
                        if (useCharCount > unitCharLen) {
                            lastPos += unitCharLen;
                            useCharCount -= unitCharLen;
                            // 用法
                            if (useCharCount <= usageCharLen) {
                                useCharCount++;
                            }
                        }
                    }
                }
                lastPos += useCharCount;

                sbMedicine.append(sb.substring(0, lastPos));
                break;
            } else {
                sbMedicine.append(sb.toString());
            }
            totalCharCount += lineCharLen;
        }
        // 傷病の経過と薬剤名の区切りとして改行x2を使用
        if (sbSickProgress.length() > 0 && sbMedicine.length() > 0) {
            // 傷病の経過と薬剤名の間には1行分の空行を挟んで区切りとする。
            sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
            sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
        }
        // 連結
        sbSickProgress.append(sbMedicine.toString());
    } else {
        // 傷病の経過が250文字以内かつ5行以内かつ薬剤名が6個以内の場合

        String sickMedicinesGridName = "Grid9";
        int sickMedicinesRows = 3;
        for (int i = 0; i < sickMedicinesRows; i++) {
            for (int j = 0; j < 2; j++) {
                StringBuffer sb = new StringBuffer();
                String index = String.valueOf(i * 2 + j + 1);
                text = (String) VRBindPathParser.get("MEDICINE" + index,
                        data);
                if (!IkenshoCommon.isNullText(text)) {
                    sb.append(text);
                    sb.append(" ");
                }
                text = (String) VRBindPathParser
                        .get("DOSAGE" + index, data);
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
                    IkenshoCommon.addString(pd, sickMedicinesGridName
                            + ".h" + (i + 1) + ".w" + (j + 1), sb
                            .toString());
                }
            }
        }
    }


    ACChotarouXMLUtilities.setValue(pd, "Label3", sbSickProgress.toString());
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
    //2009/01/09 [Tozo Tanaka] Replace - end
    
    //点滴管理
    IkenshoCommon.addCheck(pd, data, "TNT_KNR", "Shape31", 1);
    //中心静脈栄養
    IkenshoCommon.addCheck(pd, data, "CHU_JOU_EIYOU", "Shape32", 1);
    //透析
    IkenshoCommon.addCheck(pd, data, "TOUSEKI", "Shape33", 1);
    //ストーマの処置
    IkenshoCommon.addCheck(pd, data, "JINKOU_KOUMON", "Shape34", 1);
    //酸素療法
    IkenshoCommon.addCheck(pd, data, "OX_RYO", "Shape35", 1);
    //レスピレーター
    IkenshoCommon.addCheck(pd, data, "JINKOU_KOKYU", "Shape36", 1);
    //気管切開の処置
    IkenshoCommon.addCheck(pd, data, "KKN_SEK_SHOCHI", "Shape37", 1);
    //疼痛の看護
    IkenshoCommon.addCheck(pd, data, "TOUTU", "Shape38", 1);
    //経管栄養
    IkenshoCommon.addCheck(pd, data, "KEKN_EIYOU", "Shape39", 1);
    //モニター測定
    IkenshoCommon.addCheck(pd, data, "MONITOR", "Shape40", 1);
    //褥瘡の処置
    IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI", "Shape41", 1);
    //カテーテル
    IkenshoCommon.addCheck(pd, data, "CATHETER", "Shape42", 1);

    //寝たきり度
    IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {"Shape43",
                               "Shape44", "Shape45", "Shape46", "Shape47",
                               "Shape48", "Shape49", "Shape50", "Shape51"}
                               , -1);
    //自立度
    IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {"Shape52",
                               "Shape53", "Shape54", "Shape55", "Shape56",
                               "Shape57", "Shape58", "Shape59"}
                               , -1);
    //短期記憶
    IkenshoCommon.addSelection(pd, data, "TANKI_KIOKU",
                               new String[] {"Shape60",
                               "Shape61"}
                               , -1);
    //認知能力
    IkenshoCommon.addSelection(pd, data, "NINCHI", new String[] {"Shape62",
                               "Shape63", "Shape64", "Shape65"}
                               , -1);
    //伝達能力
    IkenshoCommon.addSelection(pd, data, "DENTATU", new String[] {"Shape66",
                               "Shape67", "Shape68", "Shape69"}
                               , -1);

    if ( ( (Integer) VRBindPathParser.get("MONDAI_FLAG", data)).intValue() == 1) {
      boolean problemAction = false;
      //幻視・幻聴
      problemAction |= IkenshoCommon.addCheck(pd, data, "GNS_GNC", "Shape74", 1);
      //妄想
      problemAction |= IkenshoCommon.addCheck(pd, data, "MOUSOU", "Shape75", 1);
      //昼夜逆転
      problemAction |= IkenshoCommon.addCheck(pd, data, "CHUYA", "Shape76", 1);
      //暴言
      problemAction |= IkenshoCommon.addCheck(pd, data, "BOUGEN", "Shape77", 1);
      //暴行
      problemAction |= IkenshoCommon.addCheck(pd, data, "BOUKOU", "Shape78", 1);
      //抵抗
      problemAction |= IkenshoCommon.addCheck(pd, data, "TEIKOU", "Shape79", 1);
      //徘徊
      problemAction |= IkenshoCommon.addCheck(pd, data, "HAIKAI", "Shape80", 1);
      //火の不始末
      problemAction |=
          IkenshoCommon.addCheck(pd, data, "FUSIMATU", "Shape81", 1);
      //不潔
      problemAction |= IkenshoCommon.addCheck(pd, data, "FUKETU", "Shape82", 1);
      //異食行動
      problemAction |= IkenshoCommon.addCheck(pd, data, "ISHOKU", "Shape83", 1);
      //性的問題行動
      problemAction |=
          IkenshoCommon.addCheck(pd, data, "SEITEKI_MONDAI", "Shape84", 1);
      //その他
      if (IkenshoCommon.addCheck(pd, data, "MONDAI_OTHER", "Shape85", 1)) {
        //その他名称
        IkenshoCommon.addString(pd, data, "MONDAI_OTHER_NM", "Grid14.h3.w9");
        problemAction = true;
      }

      if (problemAction) {
        pd.addAttribute("Shape73", "Visible", "FALSE");
      }
      else {
        pd.addAttribute("Shape72", "Visible", "FALSE");
      }

    }
    else {
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

    //精神・神経症状の有無
    switch ( ( (Integer) VRBindPathParser.get("SEISIN", data)).intValue()) {
      case 1:
        IkenshoCommon.addString(pd, data, "SEISIN_NM", "Grid13.h2.w3");
            pd.addAttribute("Shape104", "Visible", "FALSE");

            // 専門医受信の有無
            if (IkenshoCommon.addSelection(pd, data, "SENMONI", new String[] {
                    "Shape102", "Shape103" }, -1)) {
                // 2006/02/03[Tozo Tanaka] : replace begin
                // //専門医
                // IkenshoCommon.addString(pd, data, "SENMONI_NM",
                // "Grid13.h2.w7");
                if ("1".equals(String.valueOf(data.getData("SENMONI")))) {
                    // 専門医
                    IkenshoCommon.addString(pd, data, "SENMONI_NM",
                            "Grid13.h2.w7");
                }
                // 2006/02/03[Tozo Tanaka] : replace end

            }

            break;
      case 2:
        pd.addAttribute("Shape101", "Visible", "FALSE");
        pd.addAttribute("Shape102", "Visible", "FALSE");
        pd.addAttribute("Shape103", "Visible", "FALSE");
        break;
      default:
        pd.addAttribute("Shape101", "Visible", "FALSE");
        pd.addAttribute("Shape102", "Visible", "FALSE");

        pd.addAttribute("Shape103", "Visible", "FALSE");
        pd.addAttribute("Shape104", "Visible", "FALSE");
    }

    pd.endPageEdit();

  }

  public static void printIkensho2(ACChotarouXMLWriter pd, String formatName,
                                   VRMap data, Date printDate,
                                   byte[] imageBytes) throws
      Exception {

    pd.beginPageEdit(formatName);

    printIkenshoHeader(pd, data, printDate);

    if(( (Integer) VRBindPathParser.get("HEADER_OUTPUT_UMU2", data)).intValue()==1){
      //氏名
      IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w1");
      //年
      IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w3");
      //記入日
      IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 5, 1, "日)");
    }else{
      IkenshoCommon.addString(pd, "Grid2.h1.w4", "");
      IkenshoCommon.addString(pd, "Grid2.h1.w7", "");
      IkenshoCommon.addString(pd, "Grid2.h1.w9", "");
      IkenshoCommon.addString(pd, "Grid2.h1.w11", "");
    }



    //精神・神経
    //利き腕
    IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {"Shape9",
                               "Shape10"}
                               , -1);
    //身長
    IkenshoCommon.addString(pd, data, "HEIGHT", "Grid4.h1.w2");
    //体重
    IkenshoCommon.addString(pd, data, "WEIGHT", "Grid4.h1.w4");

    //体重の変化
    IkenshoCommon.addSelection(pd, data, "WEIGHT_CHANGE",
                               new String[] {"Shape11", "Shape12", "Shape13"}
                               , -1);

    //四肢欠損
    if(IkenshoCommon.addCheck(pd, data, "SISIKESSON", "Shape14", 1)){
      //四肢欠損部位
      IkenshoCommon.addString(pd, data, "SISIKESSON_BUI", "Grid1.h3.w18");
    }

    if ( ( (Integer) VRBindPathParser.get("MAHI_FLAG", data)).intValue() == 1) {

      boolean checkFlag = false;
      //右上肢麻痺
      if (IkenshoCommon.addCheck(pd, data, "MAHI_RIGHTARM", "Shape16", 1)) {
        //右上肢麻痺程度
        IkenshoCommon.addSelection(pd, data, "MAHI_RIGHTARM_TEIDO",
                                   new String[] {"Shape18", "Shape20",
                                   "Shape22"}
                                   , -1);
        checkFlag = true;
      }else{
        pd.addAttribute("Shape18", "Visible", "FALSE");
        pd.addAttribute("Shape20", "Visible", "FALSE");
        pd.addAttribute("Shape22", "Visible", "FALSE");
      }
      //左上肢麻痺
      if (IkenshoCommon.addCheck(pd, data, "MAHI_LEFTARM", "Shape24", 1)) {
        //左上肢麻痺程度
        IkenshoCommon.addSelection(pd, data, "MAHI_LEFTARM_TEIDO",
                                   new String[] {"Shape26", "Shape28",
                                   "Shape30"}
                                   , -1);
        checkFlag = true;
      }else{
        pd.addAttribute("Shape26", "Visible", "FALSE");
        pd.addAttribute("Shape28", "Visible", "FALSE");
        pd.addAttribute("Shape30", "Visible", "FALSE");
      }
      //右下肢麻痺
      if (IkenshoCommon.addCheck(pd, data, "MAHI_LOWERRIGHTLIMB", "Shape17", 1)) {
        //右下肢麻痺程度
        IkenshoCommon.addSelection(pd, data, "MAHI_LOWERRIGHTLIMB_TEIDO",
                                   new String[] {"Shape19", "Shape21",
                                   "Shape23"}
                                   , -1);
        checkFlag = true;
      }else{
        pd.addAttribute("Shape19", "Visible", "FALSE");
        pd.addAttribute("Shape21", "Visible", "FALSE");
        pd.addAttribute("Shape23", "Visible", "FALSE");
      }
      //左下肢麻痺
      if (IkenshoCommon.addCheck(pd, data, "MAHI_LOWERLEFTLIMB", "Shape25", 1)) {
        //左下肢麻痺程度
        IkenshoCommon.addSelection(pd, data, "MAHI_LOWERLEFTLIMB_TEIDO",
                                   new String[] {"Shape27", "Shape29",
                                   "Shape38"}
                                   , -1);
        checkFlag = true;
      }
      else {
        pd.addAttribute("Shape27", "Visible", "FALSE");
        pd.addAttribute("Shape29", "Visible", "FALSE");
        pd.addAttribute("Shape38", "Visible", "FALSE");
      }
      //その他麻痺
      if (IkenshoCommon.addCheck(pd, data, "MAHI_ETC", "Shape34", 1)) {
        //その他麻痺部位
        IkenshoCommon.addString(pd, data, "MAHI_ETC_BUI", "Label2");
        //その他麻痺程度
        IkenshoCommon.addSelection(pd, data, "MAHI_ETC_TEIDO",
                                   new String[] {"Shape32", "Shape33",
                                   "Shape31"}
                                   , -1);
        checkFlag = true;
      }
      else {
        pd.addAttribute("Shape32", "Visible", "FALSE");
        pd.addAttribute("Shape33", "Visible", "FALSE");
        pd.addAttribute("Shape31", "Visible", "FALSE");
      }
      //麻痺
      if (!checkFlag) {
        pd.addAttribute("Shape15", "Visible", "FALSE");
      }
    }else{
      pd.addAttribute("Shape16", "Visible", "FALSE");
      pd.addAttribute("Shape18", "Visible", "FALSE");
      pd.addAttribute("Shape20", "Visible", "FALSE");
      pd.addAttribute("Shape22", "Visible", "FALSE");
      pd.addAttribute("Shape24", "Visible", "FALSE");
      pd.addAttribute("Shape26", "Visible", "FALSE");
      pd.addAttribute("Shape28", "Visible", "FALSE");
      pd.addAttribute("Shape30", "Visible", "FALSE");
      pd.addAttribute("Shape17", "Visible", "FALSE");
      pd.addAttribute("Shape19", "Visible", "FALSE");
      pd.addAttribute("Shape21", "Visible", "FALSE");
      pd.addAttribute("Shape23", "Visible", "FALSE");
      pd.addAttribute("Shape25", "Visible", "FALSE");
      pd.addAttribute("Shape27", "Visible", "FALSE");
      pd.addAttribute("Shape29", "Visible", "FALSE");
      pd.addAttribute("Shape38", "Visible", "FALSE");
      pd.addAttribute("Shape34", "Visible", "FALSE");
      pd.addAttribute("Shape32", "Visible", "FALSE");
      pd.addAttribute("Shape33", "Visible", "FALSE");
      pd.addAttribute("Shape31", "Visible", "FALSE");
      pd.addAttribute("Shape15", "Visible", "FALSE");
    }


    //筋力の低下
    if(IkenshoCommon.addCheck(pd, data, "KINRYOKU_TEIKA", "Shape51", 1)){
      //筋力の低下部位
      IkenshoCommon.addString(pd, data, "KINRYOKU_TEIKA_BUI", "Grid1.h7.w18");
      //筋力の低下程度
      IkenshoCommon.addSelection(pd, data, "KINRYOKU_TEIKA_TEIDO",
                                 new String[] {"Shape35", "Shape36", "Shape37"}
                                 , -1);
    }else{
      pd.addAttribute("Shape35", "Visible", "FALSE");
      pd.addAttribute("Shape36", "Visible", "FALSE");
      pd.addAttribute("Shape37", "Visible", "FALSE");
    }

    //関節の拘縮
    if(IkenshoCommon.addCheck(pd, data, "KOUSHU", "Shape52", 1)){
      //関節の拘縮部位
      IkenshoCommon.addString(pd, data, "KOUSHU_BUI", "Grid1.h6.w18");
      //関節の拘縮程度
      IkenshoCommon.addSelection(pd, data, "KOUSHU_TEIDO",
                                 new String[] {"Shape39", "Shape40", "Shape41"}
                                 , -1);
    }else{
      pd.addAttribute("Shape39", "Visible", "FALSE");
      pd.addAttribute("Shape40", "Visible", "FALSE");
      pd.addAttribute("Shape41", "Visible", "FALSE");
    }

    //関節の痛み
    if(IkenshoCommon.addCheck(pd, data, "KANSETU_ITAMI", "Shape53", 1)){
      //関節の痛み部位
      IkenshoCommon.addString(pd, data, "KANSETU_ITAMI_BUI", "Grid1.h5.w18");
      //関節の痛み程度
      IkenshoCommon.addSelection(pd, data, "KANSETU_ITAMI_TEIDO",
                                 new String[] {"Shape42", "Shape43", "Shape44"}
                                 , -1);
    }else{
      pd.addAttribute("Shape42", "Visible", "FALSE");
      pd.addAttribute("Shape43", "Visible", "FALSE");
      pd.addAttribute("Shape44", "Visible", "FALSE");
    }

    if ( ( (Integer) VRBindPathParser.get("SICCHOU_FLAG", data)).intValue() == 1) {
      boolean checkFlag = false;
      //上肢失調右
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "JOUSI_SICCHOU_MIGI", "Shape57", 1);
      //上肢失調左
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "JOUSI_SICCHOU_HIDARI", "Shape58", 1);
      //体幹失調右
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "TAIKAN_SICCHOU_MIGI", "Shape59", 1);
      //体幹失調左
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "TAIKAN_SICCHOU_HIDARI", "Shape60",
                                 1);
      //下肢失調右
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "KASI_SICCHOU_MIGI", "Shape61", 1);
      //下肢失調左
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "KASI_SICCHOU_HIDARI", "Shape62", 1);
      //失調・不随意運動
      if (!checkFlag) {
        pd.addAttribute("Shape54", "Visible", "FALSE");
      }
    }else{
      pd.addAttribute("Shape57", "Visible", "FALSE");
      pd.addAttribute("Shape58", "Visible", "FALSE");
      pd.addAttribute("Shape59", "Visible", "FALSE");
      pd.addAttribute("Shape60", "Visible", "FALSE");
      pd.addAttribute("Shape61", "Visible", "FALSE");
      pd.addAttribute("Shape62", "Visible", "FALSE");
      pd.addAttribute("Shape54", "Visible", "FALSE");
    }

    //褥瘡
    if(IkenshoCommon.addCheck(pd, data, "JOKUSOU", "Shape55", 1)){
      //褥瘡部位
      IkenshoCommon.addString(pd, data, "JOKUSOU_BUI", "Grid1.h1.w18");
      //褥瘡程度
      IkenshoCommon.addSelection(pd, data, "JOKUSOU_TEIDO",
                                 new String[] {"Shape45", "Shape46", "Shape47"}
                                 , -1);
    }else{
      pd.addAttribute("Shape45", "Visible", "FALSE");
      pd.addAttribute("Shape46", "Visible", "FALSE");
      pd.addAttribute("Shape47", "Visible", "FALSE");
    }

    //その他の皮膚疾患
    if(IkenshoCommon.addCheck(pd, data, "HIFUSIKKAN", "Shape56", 1)){
      //その他の皮膚疾患部位
      IkenshoCommon.addString(pd, data, "HIFUSIKKAN_BUI", "Grid1.h2.w18");
      //その他の皮膚疾患程度
      IkenshoCommon.addSelection(pd, data, "HIFUSIKKAN_TEIDO",
                                 new String[] {"Shape48", "Shape49", "Shape50"}
                                 , -1);
    }else{
      pd.addAttribute("Shape48", "Visible", "FALSE");
      pd.addAttribute("Shape49", "Visible", "FALSE");
      pd.addAttribute("Shape50", "Visible", "FALSE");
    }


    //屋外歩行
    IkenshoCommon.addSelection(pd, data, "OUTDOOR", new String[] {"Shape63",
                               "Shape66", "Shape69"}
                               , -1);

    //車いすの使用
      IkenshoCommon.addSelection(pd, data, "WHEELCHAIR", new String[] {"Shape64",
                                 "Shape67", "Shape70"}
                                 , -1);

      //歩行補助具・装具の使用
//      IkenshoCommon.addSelection(pd, data, "OUTDOOR", new String[] {"Shape65",
//                                 "Shape68", "Shape71"}
//                                 , -1);
    Object obj = VRBindPathParser.get("ASSISTANCE_TOOL", data);
    if(obj instanceof Integer){
      int val = ((Integer)obj).intValue();
      if((val & 1)==0){
        pd.addAttribute("Shape65", "Visible", "FALSE");
      }
      if((val & 2)==0){
        pd.addAttribute("Shape68", "Visible", "FALSE");
      }
      if((val & 4)==0){
        pd.addAttribute("Shape71", "Visible", "FALSE");
      }
    }else{
      pd.addAttribute("Shape65", "Visible", "FALSE");
      pd.addAttribute("Shape68", "Visible", "FALSE");
      pd.addAttribute("Shape71", "Visible", "FALSE");
    }


    //食事行為
    IkenshoCommon.addSelection(pd, data, "MEAL", new String[] {"Shape72",
                               "Shape73"}
                               , -1);

    //現在の栄養状態
    IkenshoCommon.addSelection(pd, data, "NOURISHMENT", new String[] {"Shape78",
                               "Shape79"}
                               , -1);
    //栄養・食生活上の留意点
    IkenshoCommon.addString(pd, data, "EATING_RYUIJIKOU", "Grid25.h1.w2");


    //現在あるかまたは今後発生の可能性の高い状態とその対処方針
    ArrayList words = new ArrayList();
    //尿失禁
    addSickStateCheck(pd, data, "NYOUSIKKIN", "Shape80",
                      "NYOUSIKKIN_TAISHO_HOUSIN", words);
    //転倒・骨折
    addSickStateCheck(pd, data, "TENTOU_KOSSETU", "Shape81",
                      "TENTOU_KOSSETU_TAISHO_HOUSIN", words);
    //移動能力の低下
    addSickStateCheck(pd, data, "IDOUTEIKA", "Shape82",
                      "IDOUTEIKA_TAISHO_HOUSIN", words);

    //褥瘡可能性
    addSickStateCheck(pd, data, "JOKUSOU_KANOUSEI", "Shape83",
                      "JOKUSOU_KANOUSEI_TAISHO_HOUSIN", words);
    //心肺機能の低下
    addSickStateCheck(pd, data, "SINPAIKINOUTEIKA", "Shape84",
                      "SINPAIKINOUTEIKA_TAISHO_HOUSIN", words);

    //閉じこもり
    addSickStateCheck(pd, data, "TOJIKOMORI", "Shape85",
                      "TOJIKOMORI_TAISHO_HOUSIN", words);

    //意欲低下
    addSickStateCheck(pd, data, "IYOKUTEIKA", "Shape86",
                      "IYOKUTEIKA_TAISHO_HOUSIN", words);

    //徘徊可能性
    addSickStateCheck(pd, data, "HAIKAI_KANOUSEI", "Shape87",
                      "HAIKAI_KANOUSEI_TAISHO_HOUSIN", words);

    //低栄養
    addSickStateCheck(pd, data, "TEIEIYOU", "Shape88",
                      "TEIEIYOU_TAISHO_HOUSIN", words);

    //摂食・嚥下機能低下
    addSickStateCheck(pd, data, "SESSYOKUENGE", "Shape89",
                      "SESSYOKUENGE_TAISHO_HOUSIN", words);

    //脱水
    addSickStateCheck(pd, data, "DASSUI", "Shape90", "DASSUI_TAISHO_HOUSIN",
                      words);
    //易感染性
    addSickStateCheck(pd, data, "EKIKANKANSEN", "Shape91",
                      "EKIKANKANSEN_TAISHO_HOUSIN", words);

    //がん等による疼痛
    addSickStateCheck(pd, data, "GAN_TOUTU", "Shape92",
                      "GAN_TOUTU_TAISHO_HOUSIN", words);

    //病態他
    if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "Shape93", 1)) {
      //病態他名
      IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "Label8");
      addSickStateCheck(data, "BYOUTAITA_TAISHO_HOUSIN", words);
    }

    StringBuffer sb;
    if (words.size() > 0) {
      //病態対処方針
      //病態を文字単位で連結して表示可能なところまで。

      final int MAX_LENGTH = 89;

      int inlineSize = 0;
      sb = new StringBuffer();
      int end = words.size() - 1;
      for (int i = 0; i < end; i++) {
        String text = String.valueOf(words.get(i));

        StringBuffer line = new StringBuffer();
        line.append(text);

        int wordSize = 0;
        char c = text.charAt(text.length() - 1);
        if ( (c != '。') && (c != '、')) {
          line.append("、");
//          wordSize += 2;
        }
        wordSize += text.getBytes().length;

        if (inlineSize + wordSize > MAX_LENGTH) {
          //印字可能なところまで追加
          int jEnd = line.length();
          for (int j = 0; j < jEnd; j++) {
            String str = line.substring(j, j + 1);
            sb.append(str);
            inlineSize += str.getBytes().length;
            if (inlineSize > MAX_LENGTH) {
              //行終了チェック
              break;
            }
          }
          break;
        }
        inlineSize += wordSize;
        sb.append(line.toString());
      }
      if (inlineSize <= MAX_LENGTH) {
        //末尾追加
        String text = String.valueOf(words.get(end));

        StringBuffer line = new StringBuffer();
        line.append(text);

        int wordSize = 0;
        char c = text.charAt(text.length() - 1);
        if ( (c != '。') && (c != '、')) {
          line.append("。");
//          wordSize += 2;
        }
        wordSize += text.getBytes().length;

        if (inlineSize + wordSize > MAX_LENGTH) {
          //印字可能なところまで追加
          int jEnd = line.length();
          for (int j = 0; j < jEnd; j++) {
            String str = line.substring(j, j + 1);
            sb.append(str);
            inlineSize += str.getBytes().length;
            if (inlineSize > MAX_LENGTH) {
              //行終了チェック
              break;
            }
          }
        }
        else {
          sb.append(line.toString());
        }
      }

      IkenshoCommon.addString(pd, "Grid10.h1.w2", sb.toString());
    }

    //サービス利用による生活機能の維持・改善の見通し
    IkenshoCommon.addSelection(pd, data, "VITAL_FUNCTIONS_OUTLOOK", new String[] {"Shape94",
                               "Shape95", "Shape97"}
                               , -1);
    //訪問診療
    IkenshoCommon.addCheck(pd, data, "HOUMON_SINRYOU", "Shape98", 1);
    //訪問診療下線
    IkenshoCommon.addCheck(pd, data, "HOUMON_SINRYOU_UL", "Shape121", 1);
    //訪問看護
    IkenshoCommon.addCheck(pd, data, "HOUMON_KANGO", "Shape99", 1);
    //訪問看護下線
    IkenshoCommon.addCheck(pd, data, "HOUMON_KANGO_UL", "Shape124", 1);
    // 2009/02/03 [Tozo Tanaka] Add - begin
    //看護職員の訪問による相談・支援
    IkenshoCommon.addCheck(pd, data, "HOUMON_SODAN", "HOUMON_SODAN", 1);
    //看護職員の訪問による相談・支援下線
    IkenshoCommon.addCheck(pd, data, "HOUMON_SODAN_UL", "HOUMON_SODAN_UL", 1);
    // 2009/02/03 [Tozo Tanaka] Add - end
    //訪問歯科診療
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_SINRYOU", "Shape100", 1);
    //訪問歯科診療下線
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_SINRYOU_UL", "Shape127", 1);
    //訪問薬剤管理指導
    IkenshoCommon.addCheck(pd, data, "HOUMONYAKUZAI_KANRISIDOU", "Shape101", 1);
    //訪問薬剤管理指導下線
    IkenshoCommon.addCheck(pd, data, "HOUMONYAKUZAI_KANRISIDOU_UL", "Shape129",
                           1);

    //訪問リハビリ
    IkenshoCommon.addCheck(pd, data, "HOUMON_REHA", "Shape102", 1);
    //訪問リハビリ下線
    IkenshoCommon.addCheck(pd, data, "HOUMON_REHA_UL", "Shape122", 1);
    //短期入所診療介護
    IkenshoCommon.addCheck(pd, data, "TANKI_NYUSHO_RYOUYOU", "Shape103", 1);
    //短期入所診療介護下線
    IkenshoCommon.addCheck(pd, data, "TANKI_NYUSHO_RYOUYOU_UL", "Shape125", 1);
    //訪問歯科衛生指導
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_EISEISIDOU", "Shape104", 1);
    //訪問歯科衛生指導下線
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_EISEISIDOU_UL", "Shape128", 1);
    //訪問栄養食事指導
    IkenshoCommon.addCheck(pd, data, "HOUMONEIYOU_SHOKUJISIDOU", "Shape105", 1);
    //訪問栄養食事指導下線
    IkenshoCommon.addCheck(pd, data, "HOUMONEIYOU_SHOKUJISIDOU_UL", "Shape130",
                           1);

    //通所リハビリ
    IkenshoCommon.addCheck(pd, data, "TUUSHO_REHA", "Shape106", 1);
    //通所リハビリ下線
    IkenshoCommon.addCheck(pd, data, "TUUSHO_REHA_UL", "Shape123", 1);
    //医学的管理他
    if (IkenshoCommon.addCheck(pd, data, "IGAKUTEKIKANRI_OTHER", "Shape107", 1)) {
      //医学的管理他名称
      IkenshoCommon.addString(pd, data, "IGAKUTEKIKANRI_OTHER_NM",
                              "Grid12.h1.w3");
    }
    //医学的管理他下線
    IkenshoCommon.addCheck(pd, data, "IGAKUTEKIKANRI_OTHER_UL", "Shape126", 1);

    //血圧
    IkenshoCommon.addSelection(pd, data, "KETUATU", new String[] {
                               "Shape108", "Shape109"}
                               , -1, "KETUATU_RYUIJIKOU", "Grid5.h1.w2", 2);
    //移動
    IkenshoCommon.addSelection(pd, data, "IDOU", new String[] {
                               "Shape114", "Shape115"}
                               , -1, "IDOU_RYUIJIKOU", "Grid5.h1.w5", 2);
    //摂食
    IkenshoCommon.addSelection(pd, data, "SESHOKU", new String[] {
                               "Shape110", "Shape111"}
                               , -1, "SESHOKU_RYUIJIKOU", "Grid5.h2.w2", 2);
    //運動
    IkenshoCommon.addSelection(pd, data, "UNDOU", new String[] {
                               "Shape116", "Shape117"}
                               , -1, "UNDOU_RYUIJIKOU", "Grid5.h2.w5", 2);
    //嚥下
    IkenshoCommon.addSelection(pd, data, "ENGE", new String[] {
                               "Shape112", "Shape113"}
                               , -1, "ENGE_RYUIJIKOU", "Grid5.h3.w2", 2);
    //その他留意事項
    IkenshoCommon.addString(pd, data, "KAIGO_OTHER", "Grid5.h3.w4");

    //感染症
    IkenshoCommon.addSelection(pd, data, "KANSENSHOU", new String[] {
                               "Shape118", "Shape119", "Shape120"}
                               , -1, "KANSENSHOU_NM", "Grid16.h1.w5", 1);

    sb = new StringBuffer();
    String institutionGrid;
    String hasePoint = String.valueOf(VRBindPathParser.get("HASE_SCORE", data));
    String haseDate = String.valueOf(VRBindPathParser.get("HASE_SCR_DT", data));
    String hasePointPreview = String.valueOf(VRBindPathParser.get(
        "P_HASE_SCORE", data));
    String haseDatePreview = String.valueOf(VRBindPathParser.get(
        "P_HASE_SCR_DT", data));
    if (! ("".equals(hasePoint) && "0000年00月".equals(haseDate) &&
           "".equals(hasePointPreview) && "0000年00月".equals(haseDatePreview))) {
      //長谷川式点数
      IkenshoCommon.addString(pd, data, "HASE_SCORE", "Grid18.h1.w3");
      //長谷川式日付
      addHasegawaDate(pd, data, "HASE_SCR_DT", "Grid18.h1.w", 6);
      //長谷川式前回点数
      IkenshoCommon.addString(pd, data, "P_HASE_SCORE", "Grid18.h1.w17");
      //長谷川式日付
      addHasegawaDate(pd, data, "P_HASE_SCR_DT", "Grid18.h1.w", 21);

      //1行目の施設選択を隠す
      pd.addAttribute("Grid20", "Visible", "FALSE");
      institutionGrid = "Grid19";
      sb.append(IkenshoConstants.LINE_SEPARATOR);
    }
    else {
      institutionGrid = "Grid20";
      pd.addAttribute("Grid19", "Visible", "FALSE");
      pd.addAttribute("Grid18", "Visible", "FALSE");
    }

    String institution1 = String.valueOf(VRBindPathParser.get("INST_SEL_PR1",
        data));
    if (! ("".equals(institution1))) {
      //施設選択
      IkenshoCommon.addString(pd, data, "INST_SEL_PR1",
                              institutionGrid + ".h1.w6");
      IkenshoCommon.addString(pd, data, "INST_SEL_PR2",
                              institutionGrid + ".h1.w10");
      sb.append(IkenshoConstants.LINE_SEPARATOR);
    }
    else {
      pd.addAttribute(institutionGrid, "Visible", "FALSE");
    }
    //特記事項
    sb.append(String.valueOf(VRBindPathParser.get("IKN_TOKKI", data)));
    IkenshoCommon.addString(pd, "Grid17.h1.w1", sb.toString());

    pd.endPageEdit();
  }
  
  
  protected int getFormatType(){
      return IkenshoConstants.IKENSHO_LOW_H18;
  }
  

  //2009/01/21 [Tozo Tanaka] Add - begin
  protected static int getMedicineViewCount() {
      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//      try {
//          if (ACFrame.getInstance().hasProperty(
//                  "DocumentSetting/MedicineViewCount")
//                  && ACCastUtilities.toInt(ACFrame.getInstance().getProperty(
//                          "DocumentSetting/MedicineViewCount"), 6) == 8) {
//              return 8;
//          }
//      } catch (Exception e) {
//      }      
//    return 6;
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
  //2009/01/21 [Tozo Tanaka] Add - end
  
}
