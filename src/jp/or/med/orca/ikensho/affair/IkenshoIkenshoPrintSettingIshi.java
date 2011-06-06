/*
 * 作成日: 2006/07/19
 * add kamitsukasa
 */
package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.picture.IkenshoHumanPicture;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

public class IkenshoIkenshoPrintSettingIshi extends
		IkenshoIkenshoPrintSettingH18 {

	// 医師意見書対応
	
	/**
	 * コンストラクタです。
	 * 
	 * @param data
	 *            印刷データ
	 * @param picture
	 *            全身図
	 * @throws HeadlessException
	 *             初期化例外
	 */
	public IkenshoIkenshoPrintSettingIshi(VRMap data,
			IkenshoHumanPicture picture) throws HeadlessException {
		super(data, picture);
	}
    
    /**
     * 印刷処理を行います。
     * 
     * @return 終了してよいか
     */
    protected boolean print() {
        return super.print();
    }
    

	/**
	 * 画面設定
	 * 
	 */
	protected void jbInit() throws Exception {
		super.jbInit();

		this.setTitle("「医師意見書」印刷設定");
		getPrintOptionGroup().setText("「医師意見書」印刷オプション");
		getBillPrintGroup().setText("請求書印刷（「医師意見書」と同時に）");
		getCsvGroup().setText("CSVファイルでの「医師意見書」の提出");

        // [ID:0000555][Tozo TANAKA] 2009/09/14 add begin 【2009年度対応：追加案件】医師意見書の受給者番号対応
        getPageHeaderGroup().setText("頁ヘッダ(保険者・受給者番号)");
        csvSubmitHiHokensyaUnselectAlert.setText("受給者番号が設定されていません。");
        // [ID:0000555][Tozo TANAKA] 2009/09/14 add end 【2009年度対応：追加案件】医師意見書の受給者番号対応

	}
	protected void callPrintIkensho(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate) throws Exception {
          //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
          if(!patientCareState.isEnabled() || !patientCareState.isSelected()){
              data.put("KIND", ACCastUtilities.toInteger(0));
          }
          //[ID:0000515][Tozo TANAKA] 2009/09/16 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
		IkenshoIkenshoPrintSettingIshi.printIkensho(pd, formatName, data,
				printDate);
	}

    protected void callPrintIkensho2(ACChotarouXMLWriter pd, String formatName,
            VRMap data, Date printDate, byte[] imageBytes) throws Exception {
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
        if(!patientCareState.isEnabled() || !patientCareState.isSelected()){
            data.put("KIND", ACCastUtilities.toInteger(0));
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 

		IkenshoIkenshoPrintSettingIshi.printIkensho2(pd, formatName, data,
				printDate, imageBytes);
	}

	public static void printIkensho(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate) throws Exception {

		// 使いまわし変数
		Date date = new Date();

		pd.beginPageEdit(formatName);

		// -----------------------------------------------------
		// ヘッダ
		// -----------------------------------------------------
		// 保険者番号等
		printIkenshoHeader(pd, data, printDate);
		// 記入日
		IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 2, 1);

		// -----------------------------------------------------
		// 申請者基本情報
		// -----------------------------------------------------
		// ふりがな
		IkenshoCommon.addString(pd, data, "PATIENT_KN", "Grid1.h1.w3");
		// 氏名
		IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid1.h2.w3");

		// 生年月日
		date = (Date) VRBindPathParser.get("BIRTHDAY", data);
		String era = VRDateParser.format(date, "gg");
		if ("明".equals(era)) {
			pd.addAttribute("PatienBirthTaisho", "Visible", "FALSE");
			pd.addAttribute("PatienBirthShowa", "Visible", "FALSE");
			pd.addAttribute("PatienBirthHeisei", "Visible", "FALSE");
		} else if ("大".equals(era)) {
			pd.addAttribute("PatienBirthMeiji", "Visible", "FALSE");
			pd.addAttribute("PatienBirthShowa", "Visible", "FALSE");
			pd.addAttribute("PatienBirthHeisei", "Visible", "FALSE");
		} else if ("昭".equals(era)) {
			pd.addAttribute("PatienBirthMeiji", "Visible", "FALSE");
			pd.addAttribute("PatienBirthTaisho", "Visible", "FALSE");
			pd.addAttribute("PatienBirthHeisei", "Visible", "FALSE");
		} else {
			pd.addAttribute("PatienBirthMeiji", "Visible", "FALSE");
			pd.addAttribute("PatienBirthTaisho", "Visible", "FALSE");
			pd.addAttribute("PatienBirthShowa", "Visible", "FALSE");
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
		IkenshoCommon.addString(pd, data, "AGE", "Grid1.h3.w10");

		// 性別
		IkenshoCommon.addSelection(pd, data, "SEX", new String[] {
				"PatientSexMale", "PatientSexFemale" }, -1);

		// 郵便番号
		IkenshoCommon.addZip(pd, data, "POST_CD", "Grid1.h1.w11",
				"Grid1.h1.w17");
		// 住所
		IkenshoCommon.addString(pd, data, "ADDRESS", "Grid1.h2.w12");
		// 電話番号
		IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid1.h3.w17",
				"Grid1.h3.w19", "Grid1.h3.w21");

		// 医師同意
		IkenshoCommon.addSelection(pd, data, "DR_CONSENT", new String[] {
				"DoctorAgree", "DoctorDisagree" }, -1);

		if (ACCastUtilities.toInt(VRBindPathParser.get("DR_NM_OUTPUT_UMU", data), 0) == 1) {
			// 医師氏名
			IkenshoCommon.addString(pd, data, "DR_NM", "Grid4.h1.w3");
		}
		// 医療機関名
		IkenshoCommon.addString(pd, data, "MI_NM", "Grid4.h2.w4");
		// 医療機関所在地
		IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid4.h3.w5");

		// 医療機関電話番号
		IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid5.h1.w2",
				"Grid5.h1.w4", "Grid5.h1.w6");

		// 医療機関FAX番号
		IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid5.h3.w2",
				"Grid5.h3.w4", "Grid5.h3.w6");

		// 最終診察日
		date = (Date) VRBindPathParser.get("LASTDAY", data);
		if (date != null) {
			// 元号
			IkenshoCommon.addString(pd, "Grid6.h1.w12", VRDateParser.format(
					date, "ggg"));
			// 年
			IkenshoCommon.addString(pd, "Grid6.h1.w11", VRDateParser.format(
					date, "ee"));
			// 月
			IkenshoCommon.addString(pd, "Grid6.h1.w9", VRDateParser.format(
					date, "MM"));
			// 日
			IkenshoCommon.addString(pd, "Grid6.h1.w7", VRDateParser.format(
					date, "dd"));
		}

		// 意見書作成回数
		IkenshoCommon.addSelection(pd, data, "IKN_CREATE_CNT", new String[] {
				"CreateFirstTime", "CreateManyTime" }, -1);

		// 他科受診
		String takaList[] = new String[12];
		takaList[0] = "TakaNaika";
		takaList[1] = "TakaSeishinka";
		takaList[2] = "TakaGeka";
		takaList[3] = "TakaSeikeigeka";
		takaList[4] = "TakaNoushinkeigeka";
		takaList[5] = "TakaHifuka";
		takaList[6] = "TakaHinyoukika";
		takaList[7] = "TakaFujinka";
		takaList[8] = "TakaGanka";
		takaList[9] = "TakaJibiintouka";
		takaList[10] = "TakaRehabilika";
		takaList[11] = "TakaShika";

		if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA_FLAG", data), 0) == 1) {
			int taka = ACCastUtilities.toInt(VRBindPathParser.get("TAKA", data), 0);
			if (taka == 0) {
				// 他科内容
				for (int i = 0; i < 12; i++) {
					pd.addAttribute(takaList[i], "Visible", "FALSE");
				}
				// 他科その他
				if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA_OTHER_FLAG", data), 0) == 1) {
					IkenshoCommon.addString(pd, data, "TAKA_OTHER",
							"Grid6.h3.w4");
					pd.addAttribute("TakaOff", "Visible", "FALSE");
				} else {
					pd.addAttribute("TakaOn", "Visible", "FALSE");
					pd.addAttribute("TakaOther", "Visible", "FALSE");
				}
			} else {
				pd.addAttribute("TakaOff", "Visible", "FALSE");
				// 他科内容
				for (int i = 0; i < 12; i++) {
					if ((taka & (1 << i)) == 0) {
						pd.addAttribute(takaList[i], "Visible", "FALSE");
					}
				}
				// 他科その他
				if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA_OTHER_FLAG", data), 0) == 1) {
					IkenshoCommon.addString(pd, data, "TAKA_OTHER",
							"Grid6.h3.w4");
				} else {
					pd.addAttribute("TakaOther", "Visible", "FALSE");
				}
			}
		} else {
			pd.addAttribute("TakaOn", "Visible", "FALSE");
			// 他科内容
			for (int i = 0; i < 12; i++) {
				pd.addAttribute(takaList[i], "Visible", "FALSE");
			}
			// 他科その他
			pd.addAttribute("TakaOther", "Visible", "FALSE");
		}

		// -----------------------------------------------------
		// 傷病に関する意見
		// -----------------------------------------------------
		// 傷病
		for (int i = 0; i < 3; i++) {
			// 診断名
			IkenshoCommon.addString(pd, data, "SINDAN_NM" + (i + 1), "Grid8.h"
					+ (i + 1) + ".w2");
			
			String[] deleteTarget = new String[] {
					"Grid8.h" + (i + 1) + ".w13", "Grid8.h" + (i + 1) + ".w11",
					"Grid8.h" + (i + 1) + ".w9" };
			if(ACCastUtilities.toInt(VRBindPathParser.get("SHUSSEI" + (i + 1), data), 0) == 0){
				// 発症年月日
				String text = ACCastUtilities.toString(VRBindPathParser.get("HASHOU_DT"
						+ (i + 1), data));
				setStringDate(pd, text, "Grid8.h" + (i + 1) + ".w15", new String[] {
						"Grid8.h" + (i + 1) + ".w14", "Grid8.h" + (i + 1) + ".w12",
						"Grid8.h" + (i + 1) + ".w10", }, deleteTarget);
				// 出生時OFF
				pd.addAttribute("shussei" + (i + 1), "Visible", "FALSE");
			}else{
				// 出生時ONの場合、年月日の文字を削る
				for(int j = 0; j < deleteTarget.length; j++){
					IkenshoCommon.addString(pd, deleteTarget[j], "");
				}
			}
		}

		// 入院歴 TODO
		for (int i = 0; i < 2; i++) {
			// 傷病名
			IkenshoCommon.addString(pd, data, "NYUIN_NM" + (i + 1), "Grid1" + (i + 1) + ".h1.w13");
			
			// 入院開始・終了年月
			String sta = ACCastUtilities.toString(VRBindPathParser.get("NYUIN_DT_STA" + (i + 1), data));
			String end = ACCastUtilities.toString(VRBindPathParser.get("NYUIN_DT_END" + (i + 1), data));
//			setStringDate(pd, sta, "Grid1" + (i + 1) + ".h1.w2",
//					new String[] { "Grid1" + (i + 1) + ".h1.w3",
//							"Grid1" + (i + 1) + ".h1.w5" }, new String[] {
//							"Grid1" + (i + 1) + ".h1.w4",
//							"Grid1" + (i + 1) + ".h1.w6" });
//			setStringDate(pd, end, "Grid1" + (i + 1) + ".h1.w7",
//					new String[] { "Grid1" + (i + 1) + ".h1.w8",
//							"Grid1" + (i + 1) + ".h1.w10" }, new String[] {
//							"Grid1" + (i + 1) + ".h1.w9",
//							"Grid1" + (i + 1) + ".h1.w11" });
			setStringDate(pd, sta, "Grid1" + (i + 1) + ".h1.w2",
					new String[] { "Grid1" + (i + 1) + ".h1.w3",
							"Grid1" + (i + 1) + ".h1.w5" }, new String[] {});
			setStringDate(pd, end, "Grid1" + (i + 1) + ".h1.w7",
					new String[] { "Grid1" + (i + 1) + ".h1.w8",
							"Grid1" + (i + 1) + ".h1.w10" }, new String[] {});
		}

		// 症状安定性
		// 不安定における具体的な状況
		IkenshoCommon.addSelection(pd, data, "SHJ_ANT", new String[] {
				"ShojoAntei", "ShojoFuantei", "ShojoFumei" }, -1,
				"INSECURE_CONDITION", "ShojoFuanteiJokyo", 2);

        //2009/01/09 [Tozo Tanaka] Replace - begin
//		// 傷病治療状態
//		IkenshoCommon.addString(pd, data, "MT_STS", "ShobyoKeika");
//
//		// 薬剤
//		for (int i = 0; i < 3; i++) {
//			for (int j = 0; j < 2; j++) {
//				StringBuffer sb = new StringBuffer();
//				String text;
//				String index = ACCastUtilities.toString(i * 2 + j + 1);
//				text = (String) VRBindPathParser.get("MEDICINE" + index, data);
//				if (!IkenshoCommon.isNullText(text)) {
//					sb.append(text);
//					sb.append(" ");
//				}
//				text = (String) VRBindPathParser.get("DOSAGE" + index, data);
//				if (!IkenshoCommon.isNullText(text)) {
//					sb.append(text);
//				}
//				text = (String) VRBindPathParser.get("UNIT" + index, data);
//				if (!IkenshoCommon.isNullText(text)) {
//					sb.append(text);
//				}
//				text = (String) VRBindPathParser.get("USAGE" + index, data);
//				if (!IkenshoCommon.isNullText(text)) {
//					sb.append(" ");
//					sb.append(text);
//				}
//				if (sb.length() > 0) {
//					IkenshoCommon.addString(pd, "Grid9.h" + (i + 1) + ".w"
//							+ (j + 1), sb.toString());
//				}
//			}
//		}
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin 【主治医医見書・医師医見書】薬剤名テキストの追加
//        //傷病治療状態
//        ACChotarouXMLUtilities.setValue(pd, "ShobyoKeika",
//                getInsertionLineSeparatorToStringOnByte(ACCastUtilities.toString(data
//                        .get("MT_STS")), 100));
//        
//        //薬剤
//        String sickMedicinesGridName = "Grid9";
//        int sickMedicinesRows = 3;
//        if (!(
//                IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE7", data)) && 
//                IkenshoCommon.isNullText(VRBindPathParser.get("MEDICINE8", data)) &&
//                IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE7", data)) &&
//                IkenshoCommon.isNullText(VRBindPathParser.get("DOSAGE8", data)) &&
//                IkenshoCommon.isNullText(VRBindPathParser.get("UNIT7", data)) &&
//                IkenshoCommon.isNullText(VRBindPathParser.get("UNIT8", data)) &&
//                IkenshoCommon.isNullText(VRBindPathParser.get("USAGE7", data)) && 
//                IkenshoCommon.isNullText(VRBindPathParser.get("USAGE8", data))
//                ) &&
//                (getMedicineViewCount()>6)) {
//            //薬剤7か薬剤8が入力されている場合
//            sickMedicinesGridName = "sickMedicines8";
//            sickMedicinesRows = 4;
//        }
//        
//        for (int i = 0; i < sickMedicinesRows; i++) {
//            for (int j = 0; j < 2; j++) {
//                StringBuffer sb = new StringBuffer();
//                String text;
//                String index = ACCastUtilities.toString(i * 2 + j + 1);
//                text = (String) VRBindPathParser.get("MEDICINE" + index, data);
//                if (!IkenshoCommon.isNullText(text)) {
//                    sb.append(text);
//                    sb.append(" ");
//                }
//                text = (String) VRBindPathParser.get("DOSAGE" + index, data);
//                if (!IkenshoCommon.isNullText(text)) {
//                    sb.append(text);
//                }
//                text = (String) VRBindPathParser.get("UNIT" + index, data);
//                if (!IkenshoCommon.isNullText(text)) {
//                    sb.append(text);
//                }
//                text = (String) VRBindPathParser.get("USAGE" + index, data);
//                if (!IkenshoCommon.isNullText(text)) {
//                    sb.append(" ");
//                    sb.append(text);
//                }
//                if (sb.length() > 0) {
//                    IkenshoCommon.addString(pd, sickMedicinesGridName+".h" + (i + 1) + ".w"
//                            + (j + 1), sb.toString());
//                }
//            }
//        }
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

        if (totalByteCount > 500 || totalLineCount > 5 || isUseOver6Medicine) {
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

        ACChotarouXMLUtilities.setValue(pd, "ShobyoKeika", sbSickProgress.toString());
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end 【主治医医見書・医師医見書】薬剤名テキストの追加
        
        //2009/01/09 [Tozo Tanaka] Replace - end

		// -----------------------------------------------------
		// 特別な医療（現在、定期的に、あるいは頻回に受けている医療）
		// -----------------------------------------------------
		// 点滴管理
		IkenshoCommon.addCheck(pd, data, "TNT_KNR", "ShochiTenteki", 1);
		// 中心静脈栄養
		IkenshoCommon.addCheck(pd, data, "CHU_JOU_EIYOU",
				"ShochiTyushinJomyakuEiyo", 1);
		// 透析
		IkenshoCommon.addCheck(pd, data, "TOUSEKI", "ShochiTouseki", 1);
		// ストーマの処置
		IkenshoCommon.addCheck(pd, data, "JINKOU_KOUMON", "ShochiStoma", 1);
		// 酸素療法
		IkenshoCommon.addCheck(pd, data, "OX_RYO", "ShochiOXRyoho", 1);
		// レスピレーター
		IkenshoCommon.addCheck(pd, data, "JINKOU_KOKYU", "ShochiRespirator", 1);
		// 気管切開の処置
		IkenshoCommon.addCheck(pd, data, "KKN_SEK_SHOCHI", "ShochiKikanSekkai",
				1);
		// 疼痛の看護
		IkenshoCommon.addCheck(pd, data, "TOUTU", "ShochiToutsu", 1);
		// 経管栄養
		IkenshoCommon.addCheck(pd, data, "KEKN_EIYOU", "ShochiKeikanEiyo", 1);
		// 吸引処置
		if (ACCastUtilities.toInt(VRBindPathParser.get("KYUIN_SHOCHI", data), 0) == 1) {
			// 吸引回数
			IkenshoCommon.addString(pd, "ShochiKyuinCount", ACCastUtilities
					.toString(VRBindPathParser.get("KYUIN_SHOCHI_CNT", data)));
			// 吸引時期
			IkenshoCommon.addSelection(pd, data, "KYUIN_SHOCHI_JIKI",
					new String[] { "ShochiKyuinTemporary",
							"ShochiKyuinContinuous" }, -1);
		} else {
			// 吸引処置、吸引回数、吸引時期のチェックを外す
			pd.addAttribute("ShochiKyuin", "Visible", "FALSE");
			pd.addAttribute("ShochiKyuinTemporary", "Visible", "FALSE");
			pd.addAttribute("ShochiKyuinContinuous", "Visible", "FALSE");
		}
		// モニター測定
		IkenshoCommon
				.addCheck(pd, data, "MONITOR", "TokubetsuShochiMonitor", 1);
		// 褥瘡の処置
		IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI",
				"TokubetsuShochiJokuso", 1);
		// カテーテル
		IkenshoCommon.addCheck(pd, data, "CATHETER", "ShikkinShochi", 1);

		// -----------------------------------------------------
		// 心身の状態に関する意見
		// -----------------------------------------------------
		// 行動上の障害の有無
		if (ACCastUtilities.toInt(VRBindPathParser.get("MONDAI_FLAG", data), 0) == 1) {
			boolean problemAction = false;
			// 昼夜逆転
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_CHUYA",
					"KodoShogaiTyuya", 1);
			// 暴言
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_BOUGEN",
					"KodoShogaiBogen", 1);
			// 暴行
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_BOUKOU",
					"KodoShogaiBoukou", 1);
			// 抵抗
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_TEIKOU",
					"KodoShogaiTeikou", 1);
			// 徘徊
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_HAIKAI",
					"KodoShogaiHaikai", 1);
			// 火の不始末
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_FUSIMATU",
					"KodoShogaiFushimatsu", 1);
			// 不潔
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_FUKETU",
					"KodoShogaiFuketsu", 1);
			// 異食行動
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_ISHOKU",
					"KodoShogaiIshoku", 1);
			// 性的問題行動
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_SEITEKI_MONDAI",
					"KodoShogaiSeiteki", 1);
			// その他
			if (IkenshoCommon.addCheck(pd, data, "KS_OTHER",
					"KodoShogaiOther", 1)) {
				// その他名称
				IkenshoCommon.addString(pd, data, "KS_OTHER_NM",
						"Grid14.h3.w9");
				problemAction = true;
			}

			if (problemAction) {
				pd.addAttribute("KodoShogaiOff", "Visible", "FALSE");
			} else {
				pd.addAttribute("KodoShogaiOn", "Visible", "FALSE");
			}

		} else {
			// 全てのチェックを外す
			pd.addAttribute("KodoShogaiTyuya", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiBogen", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiBoukou", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiTeikou", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiHaikai", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiFushimatsu", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiFuketsu", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiIshoku", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiSeiteki", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiOther", "Visible", "FALSE");
			pd.addAttribute("KodoShogaiOn", "Visible", "FALSE");
		}

		// 精神・神経症状の有無
		switch (ACCastUtilities.toInt(VRBindPathParser.get("SEISIN", data), 0)) {
		case 1:
			// 有の場合
			IkenshoCommon
					.addString(pd, data, "SEISIN_NM", "SeishinShinkeiName");
			pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");

			// 症状項目
			// せん妄
			IkenshoCommon.addCheck(pd, data, "SS_SENMO", "SeishinShinkeiSenmo",
					1);
			// 傾眠傾向
			IkenshoCommon.addCheck(pd, data, "SS_KEMIN_KEIKO",
					"SeishinShinkeiKeiminKeiko", 1);
			// 幻視・幻聴
			IkenshoCommon.addCheck(pd, data, "SS_GNS_GNC",
					"SeishinShinkeiGenshiGenkaku", 1);
			// 妄想
			IkenshoCommon.addCheck(pd, data, "SS_MOUSOU",
					"SeishinShinkeiMousou", 1);
			// 失見当識
			IkenshoCommon.addCheck(pd, data, "SS_SHIKKEN_TOSHIKI",
					"SeishinShinkeiShikkenToshiki", 1);
			// 失認
			IkenshoCommon.addCheck(pd, data, "SS_SHITUNIN",
					"SeishinShinkeiShitsunin", 1);
			// 失行
			IkenshoCommon.addCheck(pd, data, "SS_SHIKKO",
					"SeishinShinkeiShikko", 1);
			// 認知障害
			IkenshoCommon.addCheck(pd, data, "SS_NINCHI_SHOGAI",
					"SeishinShinkeiNinchiShogai", 1);
			// 記憶障害（短期・長期）
			if (IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI",
					"SeishinShinkeiKiokuShogai", 1)) {
				// 記憶障害（短期）
				IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI_TANKI",
						"SeishinShinkeiKiokuShogaiTanki", 1);
				// 記憶障害（長期）
				IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI_CHOUKI",
						"SeishinShinkeiKiokuShogaiChouki", 1);
			}else{
				// 記憶障害（短期）・記憶障害（長期）のチェックを外す
				pd.addAttribute("SeishinShinkeiKiokuShogaiTanki", "Visible", "FALSE");
				pd.addAttribute("SeishinShinkeiKiokuShogaiChouki", "Visible", "FALSE");
			}
			// 注意障害
			IkenshoCommon.addCheck(pd, data, "SS_CHUI_SHOGAI",
					"SeishinShinkeiChuiShogai", 1);
			// 遂行機能障害
			IkenshoCommon.addCheck(pd, data, "SS_SUIKOU_KINO_SHOGAI",
					"SeishinShinkeiSuikouKinoShogai", 1);
			// 社会的行動障害
			IkenshoCommon.addCheck(pd, data, "SS_SHAKAITEKI_KODO_SHOGAI",
					"SeishinShinkeiShakaitekiKodoShogai", 1);
			// その他
			if (IkenshoCommon.addCheck(pd, data, "SS_OTHER",
					"SeishinShinkeiOther", 1)) {
				// その他名称
				IkenshoCommon.addString(pd, data, "SS_OTHER_NM",
						"SeishinShinkeiOtherName");
			}

			// 専門医受診の有無
			if (IkenshoCommon
					.addSelection(pd, data, "SENMONI", new String[] {
							"SeishinShinkeiSenmoniOn",
							"SeishinShinkeiSenmoniOff" }, -1)) {
				if ("1".equals(ACCastUtilities.toString(data.getData("SENMONI")))) {
					// 専門医
					IkenshoCommon.addString(pd, data, "SENMONI_NM",
							"SeishinShinkeiSenmoniName");
				}
			}

			break;
		default:
			pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");
		case 2:
			// 無の場合
			pd.addAttribute("SeishinShinkeiOn", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiSenmoniOn", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiSenmoniOff", "Visible", "FALSE");

			pd.addAttribute("SeishinShinkeiSenmo", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiKeiminKeiko", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiGenshiGenkaku", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiMousou", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiShikkenToshiki", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiShitsunin", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiShikko", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiNinchiShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiKiokuShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiChuiShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiSuikouKinoShogai", "Visible",
					"FALSE");
			pd.addAttribute("SeishinShinkeiShakaitekiKodoShogai", "Visible",
					"FALSE");
			pd.addAttribute("SeishinShinkeiOther", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiKiokuShogaiTanki", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiKiokuShogaiChouki", "Visible", "FALSE");
			break;
		}
        
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   
        //施設在宅区分
        switch(ACCastUtilities.toInt(VRBindPathParser.get("KIND", data) , 0)){
        case 1:
            //在宅
            IkenshoCommon.addString(pd, "Label113", "在宅");
            break;
        case 2:
            //施設
            IkenshoCommon.addString(pd, "Label113", "施設");
            break;
        default:
            //施設在宅区分を隠す
            pd.addAttribute("Label113", "Visible", "FALSE");
            break;
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   

		pd.endPageEdit();

	}

	public static void printIkensho2(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate, byte[] imageBytes) throws Exception {

	    pd.beginPageEdit(formatName);

	    // 使いまわし変数
	    String[] ary = null;
	    String[] ary2 = null;
	    String[] ary3 = null;
	    String[] ary4 = null;
	    String[] ary5 = null;
	    String[] ary6 = null;
	    String[] ary7 = null;
	    String[] ary8 = null;
	    
		// -----------------------------------------------------
		// ヘッダ
		// -----------------------------------------------------
	    printIkenshoHeader(pd, data, printDate);

        if (ACCastUtilities.toInt(VRBindPathParser.get("HEADER_OUTPUT_UMU2", data)) == 1) {
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
	    
		// -----------------------------------------------------
		// 心身の状態に関する意見・続
		// -----------------------------------------------------
	    // てんかん
	    ary = new String[] { "TenkanHindoShuichi", "TenkanHindoTsukiichi",
				"TenkanHindoNenichi", "TenkanHindoIchinenIjou" }; 
	    if(ACCastUtilities.toInt(VRBindPathParser.get("TENKAN", data), 0) == 1){
	    	// 有の場合
	    	pd.addAttribute("TenkanOff", "Visible", "FALSE");
	    	// 頻度
	    	IkenshoCommon.addSelection(pd, data, "TENKAN_HINDO", ary, -1);
	    }else{
	    	// 無の場合
	    	pd.addAttribute("TenkanOn", "Visible", "FALSE");
	    	// 頻度のチェックを全て外す
	    	setVisibleOffAll(pd, ary);
	    }
	    
	    // 身体の状態
		// 利き腕
		IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {
				"KikiudeRight", "KikiudeLeft" }, -1);
		// 身長
		IkenshoCommon.addString(pd, data, "HEIGHT", "Grid4.h1.w2");
		// 体重
		IkenshoCommon.addString(pd, data, "WEIGHT", "Grid4.h1.w4");

		// 体重の変化
		IkenshoCommon.addSelection(pd, data, "WEIGHT_CHANGE", new String[] {
				"WeightChangeZouka", "WeightChangeIdi", "WeightChangeGensho" },
				-1);

		// 四肢欠損
		setSynchronizedChecksWithText(pd, data, "SISIKESSON", "Shishikesson",
				"SISIKESSON_TEIDO", new String[] { "ShishikessonTeidoKei",
						"ShishikessonTeidoChu", "ShishikessonTeidoJu" },
				"SISIKESSON_BUI", "ShishikessonBui");

	    // 麻痺
	    ary = new String[] { "MahiLeftArmTeidoKei",
				"MahiLeftArmTeidoChu", "MahiLeftArmTeidoJu" };
		ary2 = new String[] { "MahiLowerLeftLimbTeidoKei",
				"MahiLowerLeftLimbTeidoChu", "MahiLowerLeftLimbTeidoJu" };
		ary3 = new String[] { "MahiRightArmTeidoKei",
				"MahiRightArmTeidoChu", "MahiRightArmTeidoJu" };
		ary4 = new String[] { "MahiLowerRightLimbTeidoKei",
				"MahiLowerRightLimbTeidoChu", "MahiLowerRightLimbTeidoJu" };
		ary5 = new String[] { "MahiOtherTeidoKei",
				"MahiOtherTeidoChu", "MahiOtherTeidoJu" };
	    if(IkenshoCommon.addCheck(pd, data, "MAHI_FLAG", "Mahi", 1)){
	    	// 麻痺ON
	    	boolean action = false;
	    	// 左上肢
	    	action |= setSynchronizedChecks(pd, data, "MAHI_LEFTARM", "MahiLeftArm",
					"MAHI_LEFTARM_TEIDO", ary);
	    	// 左下肢
	    	action |= setSynchronizedChecks(pd, data, "MAHI_LOWERLEFTLIMB",
					"MahiLowerLeftLimb", "MAHI_LOWERLEFTLIMB_TEIDO", ary2);
			// 右上肢
	    	action |= setSynchronizedChecks(pd, data, "MAHI_RIGHTARM", "MahiRightArm",
					"MAHI_RIGHTARM_TEIDO", ary3);
			// 右下肢
	    	action |= setSynchronizedChecks(pd, data, "MAHI_LOWERRIGHTLIMB",
					"MahiLowerRightLimb", "MAHI_LOWERRIGHTLIMB_TEIDO", ary4);
	    	// その他
	    	action |= setSynchronizedChecksWithText(pd, data, "MAHI_ETC", "MahiOther",
					"MAHI_ETC_TEIDO", ary5, "MAHI_ETC_BUI", "MahiOtherBui");
	    	if(!action){
	    		pd.addAttribute("Mahi", "Visible", "FALSE");
	    	}
	    	
	    }else{
	    	// 麻痺OFF
	    	// 全てのチェックをOFFにする
	    	// 中分類
	    	pd.addAttribute("MahiLeftArm", "Visible", "FALSE");
	    	pd.addAttribute("MahiLowerLeftLimb", "Visible", "FALSE");
	    	pd.addAttribute("MahiRightArm", "Visible", "FALSE");
	    	pd.addAttribute("MahiLowerRightLimb", "Visible", "FALSE");
	    	pd.addAttribute("MahiOther", "Visible", "FALSE");
	    	// 程度
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    }

	    // 筋力の低下
	    setSynchronizedChecksWithText(pd, data, "KINRYOKU_TEIKA",
				"KinryokuTeika", "KINRYOKU_TEIKA_TEIDO", new String[] {
						"KinryokuTeikaTeidoKei", "KinryokuTeikaTeidoChu",
						"KinryokuTeikaTeidoJu" }, "KINRYOKU_TEIKA_BUI",
				"KinryokuTeikaBui");
	    
	    // 関節の拘縮
	    // 配列定義
	    // 肩関節右
	    ary = new String[] { "KataKoushukuRightTeidoKei",
				"KataKoushukuRightTeidoChu", "KataKoushukuRightTeidoJu" };
	    // 肩関節左
		ary2 = new String[] { "KataKoushukuLeftTeidoKei",
				"KataKoushukuLeftTeidoChu", "KataKoushukuLeftTeidoJu" };
	    // 股関節右
		ary3 = new String[] { "MataKoushukuRightTeidoKei",
				"MataKoushukuRightTeidoChu", "MataKoushukuRightTeidoJu" };
	    // 股関節左
		ary4 = new String[] { "MataKoushukuLeftTeidoKei",
				"MataKoushukuLeftTeidoChu", "MataKoushukuLeftTeidoJu" };
	    // 肘関節右
		ary5 = new String[] { "HijiKoushukuRightTeidoKei",
				"HijiKoushukuRightTeidoChu", "HijiKoushukuRightTeidoJu" };
	    // 肘関節左
		ary6 = new String[] { "HijiKoushukuLeftTeidoKei",
				"HijiKoushukuLeftTeidoChu", "HijiKoushukuLeftTeidoJu" };
	    // 膝関節右
		ary7 = new String[] { "HizaKoushukuRightTeidoKei",
				"HizaKoushukuRightTeidoChu", "HizaKoushukuRightTeidoJu" };
	    // 膝関節左
		ary8 = new String[] { "HizaKoushukuLeftTeidoKei",
				"HizaKoushukuLeftTeidoChu", "HizaKoushukuLeftTeidoJu" };
	    if(IkenshoCommon.addCheck(pd, data, "KOUSHU", "Koushuku", 1)){
	    	// 関節の拘縮ON
    		// 親のアクションを決定するフラグ
	    	boolean actionParent = false;
	    	if (IkenshoCommon.addCheck(pd, data, "KATA_KOUSHU", "KataKoushuku",
					1)) {
				// 肩関節ON
	    		boolean actionChild = false;
				// 肩関節右
				actionChild |= setSynchronizedChecks(pd, data, "KATA_KOUSHU_MIGI",
						"KataKoushukuRight", "KATA_KOUSHU_MIGI_TEIDO", ary);
				// 肩関節左
				actionChild |= setSynchronizedChecks(pd, data, "KATA_KOUSHU_HIDARI",
						"KataKoushukuLeft", "KATA_KOUSHU_HIDARI_TEIDO", ary2);
				if(actionChild){
					// 親のフラグをtrueに設定する
					actionParent = true;
				}else{
					// 各項目にチェックが一つも付かない場合、一つ上の階層を非表示に設定する
					pd.addAttribute("KataKoushuku","Visible","FALSE");
				}
			} else {
				// 肩関節OFF
				pd.addAttribute("KataKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("KataKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary);
				setVisibleOffAll(pd, ary2);
			}
			if (IkenshoCommon.addCheck(pd, data, "MATA_KOUSHU", "MataKoushuku",
					1)) {
				// 股関節ON
	    		boolean actionChild = false;
				// 股関節右
				actionChild |= setSynchronizedChecks(pd, data, "MATA_KOUSHU_MIGI",
						"MataKoushukuRight", "MATA_KOUSHU_MIGI_TEIDO", ary3);
				// 股関節左
				actionChild |= setSynchronizedChecks(pd, data, "MATA_KOUSHU_HIDARI",
						"MataKoushukuLeft", "MATA_KOUSHU_HIDARI_TEIDO", ary4);
				if(actionChild){
					// 親のフラグをtrueに設定する
					actionParent = true;
				}else{
					// 各項目にチェックが一つも付かない場合、一つ上の階層を非表示に設定する
					pd.addAttribute("MataKoushuku","Visible","FALSE");
				}
			} else {
				// 股関節OFF
				pd.addAttribute("MataKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("MataKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary3);
				setVisibleOffAll(pd, ary4);
			}
			if (IkenshoCommon.addCheck(pd, data, "HIJI_KOUSHU", "HijiKoushuku",
					1)) {
				// 肘関節ON
	    		boolean actionChild = false;
				// 肘関節右
				actionChild |= setSynchronizedChecks(pd, data, "HIJI_KOUSHU_MIGI",
						"HijiKoushukuRight", "HIJI_KOUSHU_MIGI_TEIDO", ary5);
				// 肘関節左
				actionChild |= setSynchronizedChecks(pd, data, "HIJI_KOUSHU_HIDARI",
						"HijiKoushukuLeft", "HIJI_KOUSHU_HIDARI_TEIDO", ary6);
				if(actionChild){
					// 親のフラグをtrueに設定する
					actionParent = true;
				}else{
					// 各項目にチェックが一つも付かない場合、一つ上の階層を非表示に設定する
					pd.addAttribute("HijiKoushuku","Visible","FALSE");
				}
			} else {
				// 肘関節OFF
				pd.addAttribute("HijiKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("HijiKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary5);
				setVisibleOffAll(pd, ary6);
			}
			if (IkenshoCommon.addCheck(pd, data, "HIZA_KOUSHU", "HizaKoushuku",
					1)) {
				// 膝関節ON
	    		boolean actionChild = false;
				// 膝関節右
				actionChild |= setSynchronizedChecks(pd, data, "HIZA_KOUSHU_MIGI",
						"HizaKoushukuRight", "HIZA_KOUSHU_MIGI_TEIDO", ary7);
				// 膝関節左
				actionChild |= setSynchronizedChecks(pd, data, "HIZA_KOUSHU_HIDARI",
						"HizaKoushukuLeft", "HIZA_KOUSHU_HIDARI_TEIDO", ary8);
				if(actionChild){
					// 親のフラグをtrueに設定する
					actionParent = true;
				}else{
					// 各項目にチェックが一つも付かない場合、一つ上の階層を非表示に設定する
					pd.addAttribute("HizaKoushuku","Visible","FALSE");
				}
			} else {
				// 膝関節OFF
				pd.addAttribute("HizaKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("HizaKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary7);
				setVisibleOffAll(pd, ary8);
			}
			if (IkenshoCommon.addCheck(pd, data, "KOUSHU_ETC", "KoushukuOther",
					1)) {
				// その他ON
				// その他部位
				IkenshoCommon
						.addString(pd, "KoushukuOtherBui", ACCastUtilities
								.toString(VRBindPathParser.get(
										"KOUSHU_ETC_BUI", data)));
				// 親のフラグをtrueに設定する
				actionParent = true;
			}			
			if(!actionParent){
				// 各項目に一つもチェックが入らない場合、親のチェックを非表示に設定する
				pd.addAttribute("Koushuku", "Visible", "FALSE");
			}			
	    }else{
	    	// 関節の拘縮OFF
	    	pd.addAttribute("KataKoushuku", "Visible", "FALSE");
	    	pd.addAttribute("MataKoushuku", "Visible", "FALSE");
	    	pd.addAttribute("HijiKoushuku", "Visible", "FALSE");
	    	pd.addAttribute("HizaKoushuku", "Visible", "FALSE");
	    	pd.addAttribute("KoushukuOther", "Visible", "FALSE");
	    	pd.addAttribute("KataKoushukuRight", "Visible", "FALSE");
			pd.addAttribute("KataKoushukuLeft", "Visible", "FALSE");
			pd.addAttribute("MataKoushukuRight", "Visible", "FALSE");
			pd.addAttribute("MataKoushukuLeft", "Visible", "FALSE");
			pd.addAttribute("HijiKoushukuRight", "Visible", "FALSE");
			pd.addAttribute("HijiKoushukuLeft", "Visible", "FALSE");
			pd.addAttribute("HizaKoushukuRight", "Visible", "FALSE");
			pd.addAttribute("HizaKoushukuLeft", "Visible", "FALSE");
			setVisibleOffAll(pd, ary);
			setVisibleOffAll(pd, ary2);
			setVisibleOffAll(pd, ary3);
			setVisibleOffAll(pd, ary4);
			setVisibleOffAll(pd, ary5);
			setVisibleOffAll(pd, ary6);
			setVisibleOffAll(pd, ary7);
			setVisibleOffAll(pd, ary8);
	    }
	    
	    // 関節の痛み
	    setSynchronizedChecksWithText(pd, data, "KANSETU_ITAMI",
				"KansetsuItami", "KANSETU_ITAMI_TEIDO", new String[] {
						"KansetsuItamiTeidokei", "KansetsuItamiTeidoChu",
						"KansetsuItamiTeidoJu" }, "KANSETU_ITAMI_BUI",
				"KansetsuItamiBui");
	    
	    // 失調・不随意運動
	    // 配列定義
		// 上肢右程度
		ary = new String[] { "JoushiShicchoRightTeidoKei",
				"JoushiShicchoRightTeidoChu", "JoushiShicchoRightTeidoJu" };
		// 上肢左程度
		ary2 = new String[] { "JoushiShicchoLeftTeidoKei",
				"JoushiShicchoLeftTeidoChu", "JoushiShicchoLeftTeidoJu" };
		// 体幹右程度
		ary3 = new String[] { "TaikanShicchoRightTeidoKei",
				"TaikanShicchoRightTeidoChu", "TaikanShicchoRightTeidoJu" };
		// 体幹左程度
		ary4 = new String[] { "TaikanShicchoLeftTeidoKei",
				"TaikanShicchoLeftTeidoChu", "TaikanShicchoLeftTeidoJu" };
		// 下肢右程度
		ary5 = new String[] { "KashiShicchoRightTeidoKei",
				"KashiShicchoRightTeidoChu", "KashiShicchoRightTeidoJu" };
		// 下肢左程度
		ary6 = new String[] { "KashiShicchoLeftTeidoKei",
				"KashiShicchoLeftTeidoChu", "KashiShicchoLeftTeidoJu" };
	    if(IkenshoCommon.addCheck(pd, data, "SICCHOU_FLAG", "Shiccho", 1)){
	    	// 失調・不随意運動ON
	    	// 親のアクションを決定するフラグ
	    	boolean action = false;
			// 上肢右
			action |= setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_MIGI",
					"JoushiShicchoRight", "JOUSI_SICCHOU_MIGI_TEIDO", ary);
			// 上肢左
			action |= setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_HIDARI",
					"JoushiShicchoLeft", "JOUSI_SICCHOU_HIDARI_TEIDO", ary2);
			// 体幹右
			action |= setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_MIGI",
					"TaikanShicchoRight", "TAIKAN_SICCHOU_MIGI_TEIDO", ary3);
			// 体幹左
			action |= setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_HIDARI",
					"TaikanShicchoLeft", "TAIKAN_SICCHOU_HIDARI_TEIDO", ary4);
			// 下肢右
			action |= setSynchronizedChecks(pd, data, "KASI_SICCHOU_MIGI",
					"KashiShicchoRight", "KASI_SICCHOU_MIGI_TEIDO", ary5);
			// 下肢左
			action |= setSynchronizedChecks(pd, data, "KASI_SICCHOU_HIDARI",
					"KashiShicchoLeft", "KASI_SICCHOU_HIDARI_TEIDO", ary6);
			if(!action){
				// 各項目に一つもチェックが入らなかった場合は親を非表示にする。
				pd.addAttribute("Shiccho", "Visible", "FALSE");
			}
	    }else{
	    	// 失調・不随意運動OFF
	    	pd.addAttribute("JoushiShicchoRight", "Visible", "FALSE");
	    	pd.addAttribute("JoushiShicchoLeft", "Visible", "FALSE");
	    	pd.addAttribute("TaikanShicchoRight", "Visible", "FALSE");
	    	pd.addAttribute("TaikanShicchoLeft", "Visible", "FALSE");
	    	pd.addAttribute("KashiShicchoRight", "Visible", "FALSE");
	    	pd.addAttribute("KashiShicchoLeft", "Visible", "FALSE");
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    	setVisibleOffAll(pd, ary6);
	    }
	    
	    // 褥瘡
		setSynchronizedChecksWithText(pd, data, "JOKUSOU", "Jokusou",
				"JOKUSOU_TEIDO", new String[] { "JokusouTeidoKei",
						"JokusouTeidoChu", "JokusouTeidoJu" }, "JOKUSOU_BUI",
				"JokusouBui");

		// その他の皮膚疾患
		setSynchronizedChecksWithText(pd, data, "HIFUSIKKAN", "HifuShikkan",
				"HIFUSIKKAN_TEIDO", new String[] { "HifuShikkanTeidoKei",
						"HifuShikkanTeidoChu", "HifuShikkanTeidoJu" },
				"HIFUSIKKAN_BUI", "HifuShikkanBui");
	    
		// -----------------------------------------------------
		// サービス利用に関する意見
		// -----------------------------------------------------	    
	    // 現在、発生の可能性が高い病態とその対処方針
		// 対処方針格納用
	    ArrayList words = new ArrayList();
		// 尿失禁
	    addSickStateCheck(pd, data, "NYOUSIKKIN", "IkenNyoushikkin",
	                      "NYOUSIKKIN_TAISHO_HOUSIN", words);
		// 転倒・骨折
	    addSickStateCheck(pd, data, "TENTOU_KOSSETU", "IkenTentouKossetsu",
                "TENTOU_KOSSETU_TAISHO_HOUSIN", words);
		// 徘徊
	    addSickStateCheck(pd, data, "HAIKAI_KANOUSEI", "IkenHaikai",
                "HAIKAI_KANOUSEI_TAISHO_HOUSIN", words);
		// 褥瘡
	    addSickStateCheck(pd, data, "JOKUSOU_KANOUSEI", "IkenJokusou",
                "JOKUSOU_KANOUSEI_TAISHO_HOUSIN", words);
		// 嚥下性肺炎
	    addSickStateCheck(pd, data, "ENGESEIHAIEN", "IkenEngeseiHaien",
                "ENGESEIHAIEN_TAISHO_HOUSIN", words);
		// 腸閉塞
	    addSickStateCheck(pd, data, "CHOUHEISOKU", "IkenChouheisoku",
                "CHOUHEISOKU_TAISHO_HOUSIN", words);
		// 易感染性
	    addSickStateCheck(pd, data, "EKIKANKANSEN", "IkenEkikansensei",
                "EKIKANKANSEN_TAISHO_HOUSIN", words);
		// 心肺機能の低下
	    addSickStateCheck(pd, data, "SINPAIKINOUTEIKA", "IkenShinpaikinouTeika",
                "SINPAIKINOUTEIKA_TAISHO_HOUSIN", words);
		// 痛み
	    addSickStateCheck(pd, data, "ITAMI", "IkenItami",
                "ITAMI_TAISHO_HOUSIN", words);
		// 脱水
	    addSickStateCheck(pd, data, "DASSUI", "IkenDassui",
                "DASSUI_TAISHO_HOUSIN", words);
		// その他
	    if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "IkenByoutaita", 1)) {
	      // その他名
	      IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "IkenByoutaitaName");
	      addSickStateCheck(data, "BYOUTAITA_TAISHO_HOUSIN", words);
	    }
	    // 対処方針
	    StringBuffer sb;
	    if (words.size() > 0) {
	      //病態対処方針
	      //病態を文字単位で連結して表示可能なところまで。

	      final int MAX_LENGTH = 89;

	      int inlineSize = 0;
	      sb = new StringBuffer();
	      int end = words.size() - 1;
	      for (int i = 0; i < end; i++) {
	        String text = ACCastUtilities.toString(words.get(i));

	        StringBuffer line = new StringBuffer();
	        line.append(text);

	        int wordSize = 0;
	        char c = text.charAt(text.length() - 1);
	        if ( (c != '。') && (c != '、')) {
	          line.append("、");
//	          wordSize += 2;
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
	        String text = ACCastUtilities.toString(words.get(end));

	        StringBuffer line = new StringBuffer();
	        line.append(text);

	        int wordSize = 0;
	        char c = text.charAt(text.length() - 1);
	        if ( (c != '。') && (c != '、')) {
	          line.append("。");
//	          wordSize += 2;
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
	    
	    // 介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項
		// 血圧について
		IkenshoCommon.addSelection(pd, data, "KETUATU", new String[] {
				"IkenKetsuatsuOff", "IkenKetsuatsuOn" }, -1,
				"KETUATU_RYUIJIKOU", "IkenKetsuatsuRyuijikou", 2);
		// 嚥下について
		IkenshoCommon.addSelection(pd, data, "ENGE", new String[] {
				"IkenEngeOff", "IkenEngeOn" }, -1, "ENGE_RYUIJIKOU",
				"IkenEngeRyuijikou", 2);
		// 摂食について
		IkenshoCommon.addSelection(pd, data, "SESHOKU", new String[] {
				"IkenSesshokuOff", "IkenSesshokuOn" }, -1, "SESHOKU_RYUIJIKOU",
				"IkenSesshokuRyuijikou", 2);
		// 移動について
		IkenshoCommon.addSelection(pd, data, "IDOU", new String[] {
				"IkenIdouOff", "IkenIdouOn" }, -1, "IDOU_RYUIJIKOU",
				"IkenIdouRyuijikou", 2);
		// その他
		IkenshoCommon.addString(pd, "IkenKaigoOther", ACCastUtilities
				.toString(VRBindPathParser.get("KAIGO_OTHER", data)));
	    
	    // 感染症の有無（有の場合は具体的に記入してください）
		IkenshoCommon.addSelection(pd, data, "KANSENSHOU", new String[] {
				"IkenKansenshouOn", "IkenKansenshouOff",
				"IkenKansenshouUnknown" }, -1, "KANSENSHOU_NM", "Grid16.h1.w5",
				1);

		// -----------------------------------------------------
		// その他特記すべき事項
		// -----------------------------------------------------
		// 特記事項
		IkenshoCommon.addString(pd, "Grid17.h1.w1", ACCastUtilities
				.toString((VRBindPathParser.get("IKN_TOKKI", data))));

		// 精神障害の機能評価
		boolean action = false;
		// 精神症状・能力障害ニ軸評価
		// 精神症状
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_NIJIKU_SEISHIN",
								data))), "NijikuSeishin", new String[] { "０" });
		// 能力障害
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_NIJIKU_NORYOKU",
								data))), "NijikuNoryoku", new String[] { "０" });
		// 判定時期
		action |= setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_NIJIKU_DT", data)), "NijikuHanteiEra", new String[] {
				"NijikuHanteiYear", "NijikuHanteiMonth" }, new String[] {});

		// 生活障害評価
		// 食事
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_SEIKATSU_SHOKUJI",
								data))), "SeikatsuShokuji",
				new String[] { "０" });
		// 生活リズム
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_SEIKATSU_RHYTHM",
								data))), "SeikatsuRhythm", new String[] { "０" });
		// 保清
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_SEIKATSU_HOSEI",
								data))), "SeikatsuHosei", new String[] { "０" });
		// 金銭管理
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_KINSEN_KANRI", data))),
				"SeikatsuKinsenKanri", new String[] { "０" });
		// 服薬管理
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_HUKUYAKU_KANRI", data))),
				"SeikatsuFukuyakuKanri", new String[] { "０" });
		// 対人関係
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_TAIJIN_KANKEI", data))),
				"SeikatsuTaijinKankei", new String[] { "０" });
		// 社会的適応を妨げる行動
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_SHAKAI_TEKIOU", data))),
				"SeikatsuShakaiTekiou", new String[] { "０" });
		// 判断時期
		action |= setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_SEIKATSU_DT", data)), "SeikatsuHanteiEra",
				new String[] { "SeikatsuHanteiYear", "SeikatsuHanteiMonth" },
				new String[] {});
		
		// 各項目に一つも印字されていない場合、ラベルを非表示に設定する
		if(!action){
			pd.addAttribute("KinouHyoukaTitle", "Visible", "FALSE");
			pd.addAttribute("KinouHyoukaBody", "Visible", "FALSE");
			pd.addAttribute("NijikuHanteiEra", "Visible", "FALSE");
			pd.addAttribute("NijikuHanteiYear", "Visible", "FALSE");
			pd.addAttribute("NijikuHanteiMonth", "Visible", "FALSE");
			pd.addAttribute("SeikatsuHanteiEra", "Visible", "FALSE");
			pd.addAttribute("SeikatsuHanteiYear", "Visible", "FALSE");
			pd.addAttribute("SeikatsuHanteiMonth", "Visible", "FALSE");
		}
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   
        //施設在宅区分
        switch(ACCastUtilities.toInt(VRBindPathParser.get("KIND", data) , 0)){
        case 1:
            //在宅
            IkenshoCommon.addString(pd, "Label113", "在宅");
            break;
        case 2:
            //施設
            IkenshoCommon.addString(pd, "Label113", "施設");
            break;
        default:
            //施設在宅区分を隠す
            pd.addAttribute("Label113", "Visible", "FALSE");
            break;
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   
		
		pd.endPageEdit();
	    
	}

	protected int getFormatType() {
		return IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
	}

	/**
	 * 特定の文字列だった場合印字しない関数
	 * @param pd 印刷仲介クラス
	 * @param text 対象の文字列
	 * @param target 文字列の印字先
	 * @param avoid 印字しない文字
	 * @throws Exception 例外
	 * @return 印字した場合はtrueを、印字しないもしくは空文字を印字した場合falseを返す
	 * @throws Exception
	 */
	protected static boolean setAvoidedString(ACChotarouXMLWriter pd,
			String text, String target, String[] avoid) throws Exception {
		
		if(text == null || "".equals(text)){
			return false;
		}
		
		if (avoid != null) {
			for (int i = 0; i < avoid.length; i++) {
				if (text.equals(avoid[i])) {
					return false;
				}
			}
		}
		
		IkenshoCommon.addString(pd, target, text);
		return true;
		
	}
	
	/**
	 * 特定の文字列だった場合印字しない関数
	 * @return 印字を行った場合はtrueを、印字しない、もしくは空文字の印字を行った場合はfalseを返す
	 * @param pd 印刷仲介クラス
	 * @param data データ
	 * @param key 文字列のバインドパス
	 * @param target 文字列の印字先
	 * @param avoid 印字しない文字
	 * @throws Exception 例外
	 */
	protected static boolean setAvoidedString(ACChotarouXMLWriter pd,
			VRMap data, String key, String target, String[] avoid)
			throws Exception {
		return setAvoidedString(pd, ACCastUtilities.toString(VRBindPathParser
				.get(key, data)), target, avoid);
	}
	
	/**
	 * String型の日付を解析し、印刷する関数
	 * @param pd 印刷仲介クラス
	 * @param text String型の日付
	 * @param setEraTarget 元号の設定先
	 * @param setYMDTarget 年月日の設定先
	 * @param deleteTarget 不詳もしくは未入力の場合の削る対象
	 * @throws Exception 例外
	 */
	protected static boolean setStringDate(ACChotarouXMLWriter pd, String text,
			String setEraTarget, String[] setYMDTarget, String[] deleteTarget)
			throws Exception {

		if(text == null || "".equals(text)){
			// 年月日を削り、終了
			if(deleteTarget != null){
				for (int i = 0; i < deleteTarget.length; i++) {
					IkenshoCommon.addString(pd, deleteTarget[i], "");
				}
			}
			return false;
		}
		
		final String SPACER = "  ";
		Date date = new Date();
		String era = "";
		String eYear = "";
		boolean eraPrinted = false;

		// if (text.length() == 11) {
		if (text.startsWith("不詳")) {
			// 元号
			IkenshoCommon.addString(pd, setEraTarget, "不詳");
			// 年月日を削る
			if(deleteTarget != null){
				for (int i = 0; i < deleteTarget.length; i++) {
					IkenshoCommon.addString(pd, deleteTarget[i], "");
				}
			}
			// 印字したため、trueを返す
			return true;
		} else if (text.startsWith("0000")) {
			// 年月日を削る
			if(deleteTarget != null){
				for (int i = 0; i < deleteTarget.length; i++) {
					IkenshoCommon.addString(pd, deleteTarget[i], "");
				}
			}
			// 印字しなかっため、falseを返す
			return false;
		} else {
			switch (text.length()) {
			case 11:
				// 『平成○○年○○月○○日』の場合
				date = VRDateParser.parse(text.replaceAll("00月", "01月")
						.replaceAll("00日", "01日"));
				break;
			case 8:
				// 『平成○○年○○月』の場合
				date = VRDateParser
						.parse(text.replaceAll("00月", "01月") + "01日");
				break;
			case 5:
				// 『平成○○年』の場合
				date = VRDateParser.parse(text + "01月01日");
				break;
			default:
				// 上記以外の場合、解析不能とみなす
				if(deleteTarget != null){
					for (int i = 0; i < deleteTarget.length; i++) {
						IkenshoCommon.addString(pd, deleteTarget[i], "");
					}
				}
				// 印字しなかっため、falseを返す
				return false;
			}
			era = VRDateParser.format(date, "ggg");
			eYear = VRDateParser.format(date, "ee");
			// 元号
			IkenshoCommon.addString(pd, setEraTarget, era);
			eraPrinted = true;
		}
		// }
		if (eraPrinted) {
			String val;
			text = text.replaceAll("00", SPACER);
			switch (setYMDTarget.length) {
			case 3:
				// 日
				val = text.substring(8, 10);
				if (!SPACER.equals(val)) {
					IkenshoCommon.addString(pd, setYMDTarget[2], val);
				}
			case 2:
				// 月
				val = text.substring(5, 7);
				if (!SPACER.equals(val)) {
					IkenshoCommon.addString(pd, setYMDTarget[1], val);
				}
			case 1:
				// 年
				IkenshoCommon.addString(pd, setYMDTarget[0], eYear);
			}
		}
		
		return true;

	}
	
	/**
	 * チェックボックス＋ラジオを制御する関数
	 * 例）肩間接 □右（程度：□軽 □中 □重）
	 * @param pd 印刷仲介クラス
	 * @param data データ
	 * @param parentKey 親のバインドパス
	 * @param parentTarget 親の出力先
	 * @param chiledKey 子のバインドパス
	 * @param childrenTarget 子の出力先
	 * @throws Exception 例外
	 */
	protected static boolean setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget) throws Exception {

		return setSynchronizedChecks(pd, data, parentKey, parentTarget, childKey, childrenTarget, 1, -1);
		
	}
		
	/**
	 * チェックボックス＋ラジオを制御する関数
	 * 例）肩間接 □右（程度：□軽 □中 □重）
	 * @param pd 印刷仲介クラス
	 * @param data データ
	 * @param parentKey 親のバインドパス
	 * @param parentTarget 親の出力先
	 * @param chiledKey 子のバインドパス
	 * @param childrenTarget 子の出力先
	 * @param checkValue 親をチェックと見なす値
	 * @param offSet 子のオフセット
	 * @throws Exception 例外
	 */
	protected static boolean setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget, int checkValue, int offset) throws Exception {

		if(IkenshoCommon.addCheck(pd, data, parentKey, parentTarget, checkValue)){
			// 親のチェックがONの場合
			IkenshoCommon.addSelection(pd, data, childKey, childrenTarget, offset);
			return true;
		}else{
			// 親のチェックがOFFの場合
			setVisibleOffAll(pd, childrenTarget);
			return false;
		}
		
	}

	/**
	 * チェックボックス＋テキスト＋ラジオを制御する関数
	 * 例）麻痺 □その他（部位：■■■■ 程度：□軽 □中 □重）
	 * @param pd 印刷仲介クラス
	 * @param data データ
	 * @param parentKey 親のバインドパス
	 * @param parentTarget 親の出力先
	 * @param childKey 子のバインドパス
	 * @param childrenTarget 子の出力先
	 * @param textKey 文字列のバインドパス
	 * @param textTarget 文字列の出力先
	 * @throws Exception 例外
	 */
	protected static boolean setSynchronizedChecksWithText(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget, String textKey, String textTarget)
			throws Exception {

		return setSynchronizedChecksWithText(pd, data, parentKey, parentTarget,
				childKey, childrenTarget, textKey, textTarget, 1, -1);

	}

	/**
	 * チェックボックス＋テキスト＋ラジオを制御する関数
	 * 例）麻痺 □その他（部位：■■■■ 程度：□軽 □中 □重）
	 * @param pd 印刷仲介クラス
	 * @param data データ
	 * @param parentKey 親のバインドパス
	 * @param parentTarget 親の出力先
	 * @param childKey 子のバインドパス
	 * @param childrenTarget 子の出力先
	 * @param textKey 文字列のバインドパス
	 * @param textTarget 文字列の出力先
	 * @param checkValue 親をチェックと見なす値
	 * @param offSet 子のオフセット
	 * @throws Exception 例外
	 */
	protected static boolean setSynchronizedChecksWithText(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget, String textKey, String textTarget, int checkValue,
			int offset) throws Exception {

		if(IkenshoCommon.addCheck(pd, data, parentKey, parentTarget, checkValue)){
			// 親のチェックがONの場合
			// 文字列
			IkenshoCommon.addString(pd, textTarget, ACCastUtilities
					.toString(VRBindPathParser.get(textKey, data)));
			// チェック
			IkenshoCommon.addSelection(pd, data, childKey, childrenTarget, offset);
			return true;
		}else{
			// 親のチェックがOFFの場合
			setVisibleOffAll(pd, childrenTarget);
			return false;
		}
		
	}

	/**
	 * 渡された項目を非表示にする関数
	 * @param pd 印刷仲介クラス
	 * @param target 対象項目
	 * @throws Exception 例外
	 */
	protected static void setVisibleOffAll(ACChotarouXMLWriter pd, String[] target)
			throws Exception {
		
		for(int i = 0; i < target.length; i++){
			pd.addAttribute(target[i], "Visible", "FALSE");
		}
		
	}
	
	/**
	 * 半角数字を全角数字に変換する関数
	 * @param text 変換元の文字列
	 * @return 変換済の文字列
	 */
	public static String convertHankakuNumToZenkakuNum(String text) {
		if(text == null){
			return null;
		}
	    StringBuffer sb = new StringBuffer(text);
	    for (int i = 0; i < text.length(); i++) {
	      char c = text.charAt(i);
	      if (c >= '0' && c <= '9') {
	        sb.setCharAt(i, (char) (c - '0' + '０'));
	      }
	    }
	    return sb.toString();
	}
	
    protected String getIkenshoFormatFilePath1() {
        return "IkenshoShien1.xml";
    }

    protected String getIkenshoFormatFilePath2() {
        return "IkenshoShien2.xml";
    }
	
//	// TODO デバッグ用　後に削除予定
//
//    /**
//     * 印刷処理を行います。
//     * 
//     * @return 終了してよいか
//     */
//    protected boolean print() {
//    	
//    	try{
//    		return TestPrintClass.print();
//    	}catch(Exception e){
//    		System.out.println("PRINT_ERROR");
//    		return false;
//    	}
//    	
//    }

    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
    protected void addPrintOptionGroup(){
        getPrintOptionGroup().add(getDoctorNameGroup(), VRLayout.CLIENT);
        getPrintOptionGroup().add(getSecondHeaderGroup(), VRLayout.CLIENT);
        getPrintOptionGroup().add(getPageHeaderGroup(), VRLayout.CLIENT);
        getPrintOptionGroup().add(getPatientCareStateGroup(), VRLayout.SOUTH);
    }
    
    protected void setPackSize(){
        setSize(new Dimension(700, 420));
    }
    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 

}
