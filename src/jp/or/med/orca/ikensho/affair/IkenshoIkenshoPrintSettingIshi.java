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
							"Grid6.h4.w4");
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
							"Grid6.h4.w4");
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

// [ID:0000792][Satoshi Tokusari] 2015/11 add-Start 症状の安定性の印字見直し対応
        switch(ACCastUtilities.toInt(VRBindPathParser.get("SHJ_ANT", data) , 0)) {
        case 1:
            // 安定
            IkenshoCommon.addString(pd, "ShojoFuanteiJokyo", "安定");
            break;
        case 2:
            // 不安定
            break;
        case 3:
            // 不明
            IkenshoCommon.addString(pd, "ShojoFuanteiJokyo", "不明");
            break;
        }
// [ID:0000792][Satoshi Tokusari] 2015/11 add-End

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
        
        // 障害の直接の原因となっている傷病の経過出力
        ACChotarouXMLUtilities.setValue(pd, "ShobyoKeika", sbSickProgress.toString());
        
        
        
        
        
        // -----------------------------------------------------
        // 2.身体の状態に関する意見
        // -----------------------------------------------------
	    // 使いまわし変数
        boolean isCheck = false;
	    String[] ary = null;
	    String[] ary2 = null;
	    String[] ary3 = null;
	    String[] ary4 = null;
	    String[] ary5 = null;
	    String[] ary6 = null;
	    String[] ary7 = null;
	    String[] ary8 = null;
	    String[] ary9 = null;
        
        
		// 利き腕
		IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {
				"KikiudeRight", "KikiudeLeft" }, -1);
		// 身長
		IkenshoCommon.addString(pd, data, "HEIGHT", "PatientHeight");
		// 体重
		IkenshoCommon.addString(pd, data, "WEIGHT", "PatientWeight");

		// 体重の変化
		IkenshoCommon.addSelection(pd, data, "WEIGHT_CHANGE", new String[] {
				"WeightChangeZouka", "WeightChangeIdi", "WeightChangeGensho" },
				-1);

		// 四肢欠損
		if (isSelected(data, "SISIKESSON")) {
			IkenshoCommon.addString(pd, data, "SISIKESSON_BUI", "ShishikessonBui");
		}

	    // 麻痺
	    ary = new String[] { "MahiLeftArmTeidoKei", "MahiLeftArmTeidoChu", "MahiLeftArmTeidoJu" };
		ary2 = new String[] { "MahiLowerLeftLimbTeidoKei", "MahiLowerLeftLimbTeidoChu", "MahiLowerLeftLimbTeidoJu" };
		ary3 = new String[] { "MahiRightArmTeidoKei", "MahiRightArmTeidoChu", "MahiRightArmTeidoJu" };
		ary4 = new String[] { "MahiLowerRightLimbTeidoKei", "MahiLowerRightLimbTeidoChu", "MahiLowerRightLimbTeidoJu" };
		ary5 = new String[] { "MahiOtherTeidoKei", "MahiOtherTeidoChu", "MahiOtherTeidoJu" };
	    
		if (isSelected(data, "MAHI_FLAG")) {
			// 左上肢
			setSynchronizedChecks(pd, data, "MAHI_LEFTARM", "MAHI_LEFTARM_TEIDO", ary);
			// 左下肢
			setSynchronizedChecks(pd, data, "MAHI_LOWERLEFTLIMB", "MAHI_LOWERLEFTLIMB_TEIDO", ary2);
			// 右上肢
	    	setSynchronizedChecks(pd, data, "MAHI_RIGHTARM", "MAHI_RIGHTARM_TEIDO", ary3);
			// 右下肢
	    	setSynchronizedChecks(pd, data, "MAHI_LOWERRIGHTLIMB", "MAHI_LOWERRIGHTLIMB_TEIDO", ary4);
	    	// その他
	    	setSynchronizedChecksWithText(pd, data, "MAHI_ETC", "MAHI_ETC_TEIDO", ary5, "MAHI_ETC_BUI", "MahiOtherBui");
	    	
	    }else{
	    	// 麻痺OFF
	    	// 全てのチェックをOFFにする
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    }

	    // 筋力の低下
		ary = new String[] {"KinryokuTeikaTeidoKei", "KinryokuTeikaTeidoChu", "KinryokuTeikaTeidoJu"};
	    isCheck = setSynchronizedChecksWithText(pd, data, "KINRYOKU_TEIKA", "KINRYOKU_TEIKA_TEIDO",
	    											ary, "KINRYOKU_TEIKA_BUI", "KinryokuTeikaBui");
	    
	    // 筋力の低下変動
	    ary = new String[]{"KinryokuTeikaHendoUp" ,"KinryokuTeikaHendoKeep", "KinryokuTeikaHendoDown"};
	    if (isCheck) {
	    	setSynchronizedChecks(pd, data, "KINRYOKU_TEIKA_CHANGE", ary);
	    } else {
	    	setVisibleOffAll(pd, ary);
	    }
	    
	    // 関節の拘縮
	    // 配列定義
	    // 肩関節右
	    ary = new String[] { "KataKoushukuRightTeidoKei", "KataKoushukuRightTeidoChu", "KataKoushukuRightTeidoJu" };
	    // 肩関節左
		ary2 = new String[] { "KataKoushukuLeftTeidoKei", "KataKoushukuLeftTeidoChu", "KataKoushukuLeftTeidoJu" };
	    // 股関節右
		ary3 = new String[] { "MataKoushukuRightTeidoKei", "MataKoushukuRightTeidoChu", "MataKoushukuRightTeidoJu" };
	    // 股関節左
		ary4 = new String[] { "MataKoushukuLeftTeidoKei", "MataKoushukuLeftTeidoChu", "MataKoushukuLeftTeidoJu" };
	    // 肘関節右
		ary5 = new String[] { "HijiKoushukuRightTeidoKei", "HijiKoushukuRightTeidoChu", "HijiKoushukuRightTeidoJu" };
	    // 肘関節左
		ary6 = new String[] { "HijiKoushukuLeftTeidoKei", "HijiKoushukuLeftTeidoChu", "HijiKoushukuLeftTeidoJu" };
	    // 膝関節右
		ary7 = new String[] { "HizaKoushukuRightTeidoKei", "HizaKoushukuRightTeidoChu", "HizaKoushukuRightTeidoJu" };
	    // 膝関節左
		ary8 = new String[] { "HizaKoushukuLeftTeidoKei", "HizaKoushukuLeftTeidoChu", "HizaKoushukuLeftTeidoJu" };
		// 関節その他程度
		ary9 = new String[]{"SonotaKoushukuTeidoKei", "SonotaKoushukuTeidoChu", "SonotaKoushukuTeidoJu"};
		
		
		// 関節の拘縮ON
		if (isSelected(data, "KOUSHU")) {
			
			// 肩関節ON
	    	if (isSelected(data, "KATA_KOUSHU")) {
				// 肩関節ON
	    		boolean actionChild = false;
				// 肩関節右
				setSynchronizedChecks(pd, data, "KATA_KOUSHU_MIGI", "KATA_KOUSHU_MIGI_TEIDO", ary);
				// 肩関節左
				setSynchronizedChecks(pd, data, "KATA_KOUSHU_HIDARI", "KATA_KOUSHU_HIDARI_TEIDO", ary2);
				
			} else {
				// 肩関節OFF
				setVisibleOffAll(pd, ary);
				setVisibleOffAll(pd, ary2);
			}
	    	
	    	// 肘関節ON
			if (isSelected(data, "HIJI_KOUSHU")) {
				// 肘関節右
				setSynchronizedChecks(pd, data, "HIJI_KOUSHU_MIGI", "HIJI_KOUSHU_MIGI_TEIDO", ary5);
				// 肘関節左
				setSynchronizedChecks(pd, data, "HIJI_KOUSHU_HIDARI", "HIJI_KOUSHU_HIDARI_TEIDO", ary6);
				
			} else {
				// 肘関節OFF
				setVisibleOffAll(pd, ary5);
				setVisibleOffAll(pd, ary6);
			}
	    	
			// 股関節ON
			if (isSelected(data, "MATA_KOUSHU")) {
				// 股関節右
				setSynchronizedChecks(pd, data, "MATA_KOUSHU_MIGI", "MATA_KOUSHU_MIGI_TEIDO", ary3);
				// 股関節左
				setSynchronizedChecks(pd, data, "MATA_KOUSHU_HIDARI", "MATA_KOUSHU_HIDARI_TEIDO", ary4);
				
			} else {
				// 股関節OFF
				setVisibleOffAll(pd, ary3);
				setVisibleOffAll(pd, ary4);
			}

			// 膝関節ON
			if (isSelected(data, "HIZA_KOUSHU")) {
				// 膝関節右
				setSynchronizedChecks(pd, data, "HIZA_KOUSHU_MIGI", "HIZA_KOUSHU_MIGI_TEIDO", ary7);
				// 膝関節左
				setSynchronizedChecks(pd, data, "HIZA_KOUSHU_HIDARI", "HIZA_KOUSHU_HIDARI_TEIDO", ary8);
				
			} else {
				// 膝関節OFF
				setVisibleOffAll(pd, ary7);
				setVisibleOffAll(pd, ary8);
			}
			
			// その他部位
			setSynchronizedChecksWithText(pd, data, "KOUSHU_ETC", "KOUSHU_ETC_BUI_TEIDO",
											ary9, "KOUSHU_ETC_BUI", "KoushukuOtherBui");
			
	    }else{
	    	// 関節の拘縮OFF
			setVisibleOffAll(pd, ary);
			setVisibleOffAll(pd, ary2);
			setVisibleOffAll(pd, ary3);
			setVisibleOffAll(pd, ary4);
			setVisibleOffAll(pd, ary5);
			setVisibleOffAll(pd, ary6);
			setVisibleOffAll(pd, ary7);
			setVisibleOffAll(pd, ary8);
			setVisibleOffAll(pd, ary9);
	    }
	    
	    // 関節の痛み
		ary = new String[]{"KansetsuItamiTeidokei", "KansetsuItamiTeidoChu","KansetsuItamiTeidoJu"};
	    isCheck = setSynchronizedChecksWithText(pd, data, "KANSETU_ITAMI", "KANSETU_ITAMI_TEIDO",
	    											ary, "KANSETU_ITAMI_BUI", "KansetsuItamiBui");
	    
	    // 関節の痛み 変動
	    ary = new String[]{"KansetsuItamiHendoUp" ,"KansetsuItamiKeep", "KansetsuItamiDown"};
	    if (isCheck) {
	    	setSynchronizedChecks(pd, data, "KANSETU_ITAMI_CHANGE", ary);
	    } else {
	    	setVisibleOffAll(pd, ary);
	    }
	    
	    
	    // 失調・不随意運動
	    // 配列定義
		// 上肢右程度
		ary = new String[] { "JoushiShicchoRightTeidoKei", "JoushiShicchoRightTeidoChu", "JoushiShicchoRightTeidoJu" };
		// 上肢左程度
		ary2 = new String[] { "JoushiShicchoLeftTeidoKei", "JoushiShicchoLeftTeidoChu", "JoushiShicchoLeftTeidoJu" };
		// 体幹右程度
		ary3 = new String[] { "TaikanShicchoRightTeidoKei", "TaikanShicchoRightTeidoChu", "TaikanShicchoRightTeidoJu" };
		// 体幹左程度
		ary4 = new String[] { "TaikanShicchoLeftTeidoKei", "TaikanShicchoLeftTeidoChu", "TaikanShicchoLeftTeidoJu" };
		// 下肢右程度
		ary5 = new String[] { "KashiShicchoRightTeidoKei", "KashiShicchoRightTeidoChu", "KashiShicchoRightTeidoJu" };
		// 下肢左程度
		ary6 = new String[] { "KashiShicchoLeftTeidoKei", "KashiShicchoLeftTeidoChu", "KashiShicchoLeftTeidoJu" };
		
		// 失調・不随意運動ON
	    if(isSelected(data, "SICCHOU_FLAG")){
			// 上肢右
			setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_MIGI", "JOUSI_SICCHOU_MIGI_TEIDO", ary);
			// 上肢左
			setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_HIDARI", "JOUSI_SICCHOU_HIDARI_TEIDO", ary2);
			// 体幹右
			setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_MIGI", "TAIKAN_SICCHOU_MIGI_TEIDO", ary3);
			// 体幹左
			setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_HIDARI", "TAIKAN_SICCHOU_HIDARI_TEIDO", ary4);
			// 下肢右
			setSynchronizedChecks(pd, data, "KASI_SICCHOU_MIGI", "KASI_SICCHOU_MIGI_TEIDO", ary5);
			// 下肢左
			setSynchronizedChecks(pd, data, "KASI_SICCHOU_HIDARI", "KASI_SICCHOU_HIDARI_TEIDO", ary6);
			
	    }else{
	    	// 失調・不随意運動OFF
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    	setVisibleOffAll(pd, ary6);
	    }
	    
	    // 褥瘡
	    ary = new String[]{"JokusouTeidoKei", "JokusouTeidoChu", "JokusouTeidoJu"};
		setSynchronizedChecksWithText(pd, data, "JOKUSOU", "Jokusou", "JOKUSOU_TEIDO",
				ary, "JOKUSOU_BUI", "JokusouBui");

		// その他の皮膚疾患
		ary = new String[]{"HifuShikkanTeidoKei", "HifuShikkanTeidoChu", "HifuShikkanTeidoJu"};
		setSynchronizedChecksWithText(pd, data, "HIFUSIKKAN", "HifuShikkan", "HIFUSIKKAN_TEIDO",
				ary, "HIFUSIKKAN_BUI", "HifuShikkanBui");
		

		pd.endPageEdit();

	}

	public static void printIkensho2(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate, byte[] imageBytes) throws Exception {

	    pd.beginPageEdit(formatName);

	    // 使いまわし変数
	    String[] ary = null;
	    
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
        
        
		// -----------------------------------------------------
		// 3.行動及び精神等の状態に関する意見
		// -----------------------------------------------------
		// (1) 行動上の障害の有無
		if (isSelected(data, "MONDAI_FLAG")) {
			
			// 昼夜逆転
			IkenshoCommon.addCheck(pd, data, "KS_CHUYA", "KodoShogaiTyuya", 1);
			// 暴言
			IkenshoCommon.addCheck(pd, data, "KS_BOUGEN", "KodoShogaiBogen", 1);
			// 自傷
			IkenshoCommon.addCheck(pd, data, "KS_JISYOU", "KodoShogaiJisyo", 1);
			// 他害
			IkenshoCommon.addCheck(pd, data, "KS_BOUKOU", "KodoShogaiTagai", 1);
			// 抵抗
			IkenshoCommon.addCheck(pd, data, "KS_TEIKOU", "KodoShogaiTeikou", 1);
			// 徘徊
			IkenshoCommon.addCheck(pd, data, "KS_HAIKAI", "KodoShogaiHaikai", 1);
			// 危険の認識が困難(火の不始末)
			IkenshoCommon.addCheck(pd, data, "KS_FUSIMATU", "KodoShogaiKiken", 1);
			// 不潔
			IkenshoCommon.addCheck(pd, data, "KS_FUKETU", "KodoShogaiFuketsu", 1);
			// 異食行動
			IkenshoCommon.addCheck(pd, data, "KS_ISHOKU", "KodoShogaiIshoku", 1);
			// 性的逸脱行動
			IkenshoCommon.addCheck(pd, data, "KS_SEITEKI_MONDAI", "KodoShogaiSeiteki", 1);
			// その他
			if (IkenshoCommon.addCheck(pd, data, "KS_OTHER", "KodoShogaiOther", 1)) {
				// その他名称
				IkenshoCommon.addString(pd, data, "KS_OTHER_NM", "Grid14.h3.w9");
			}

		} else {
			// 全てのチェックを外す
			pd.addAttribute("KodoShogaiTyuya", "Visible", "FALSE"); //昼夜逆転
			pd.addAttribute("KodoShogaiBogen", "Visible", "FALSE"); //暴言
			pd.addAttribute("KodoShogaiJisyo", "Visible", "FALSE"); //自傷
			pd.addAttribute("KodoShogaiTagai", "Visible", "FALSE"); //他害
			pd.addAttribute("KodoShogaiTeikou", "Visible", "FALSE"); //抵抗
			pd.addAttribute("KodoShogaiHaikai", "Visible", "FALSE"); //徘徊
			pd.addAttribute("KodoShogaiKiken", "Visible", "FALSE"); //危険の認識が困難
			pd.addAttribute("KodoShogaiFuketsu", "Visible", "FALSE"); //不潔
			pd.addAttribute("KodoShogaiIshoku", "Visible", "FALSE"); //異食行動
			pd.addAttribute("KodoShogaiSeiteki", "Visible", "FALSE"); //性的逸脱行動
			pd.addAttribute("KodoShogaiOther", "Visible", "FALSE"); //その他
			pd.addAttribute("KodoShogaiOn", "Visible", "FALSE");
		}
        
        
        //  (2) 精神症状・能力障害二軸評価
		// 精神症状評価
		setSynchronizedChecks(pd, data, "SK_NIJIKU_SEISHIN", "NijikuSeishin", 6);
		// 能力障害評価
		setSynchronizedChecks(pd, data, "SK_NIJIKU_NORYOKU", "NijikuNouryoku", 5);
		// 判定時期
		setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_NIJIKU_DT", data)), "NijikuHanteiEra", new String[] {
				"NijikuHanteiYear", "NijikuHanteiMonth" }, new String[] {});
		

		// (3) 生活障害評価
		// 食事
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_SHOKUJI", "SeikatsuSyokuji", 5);
		// 生活リズム
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_RHYTHM", "SeikatsuRhythm", 5);
		// 保清
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_HOSEI", "SeikatsuHosei", 5);
		// 金銭管理
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_KINSEN_KANRI", "SeikatsuKinsen", 5);
		// 服薬管理
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_HUKUYAKU_KANRI", "SeikatsuFukuyaku", 5);
		// 対人関係
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_TAIJIN_KANKEI", "SeikatsuTaijin", 5);
		// 社会的適応を妨げる行為
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_SHAKAI_TEKIOU", "SeikatsuTekiou", 5);
		
		// 判断時期
		setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_SEIKATSU_DT", data)), "SeikatsuHanteiEra",
				new String[] { "SeikatsuHanteiYear", "SeikatsuHanteiMonth" },
				new String[] {});
		
		
		
		// (4) 精神・神経症状の有無
		switch (ACCastUtilities.toInt(VRBindPathParser.get("SEISIN", data), 0)) {
		case 1:
			// 有の場合
			//IkenshoCommon.addString(pd, data, "SEISIN_NM", "SeishinShinkeiName");
			//pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");

			// 症状項目
			// 意識障害
			IkenshoCommon.addCheck(pd, data, "SS_ISHIKI_SHOGAI", "SeishinShinkeiIshikiSyogai", 1);
			//記憶障害
			IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI", "SeishinShinkeiKiokuShogai", 1);
			// 注意障害
			IkenshoCommon.addCheck(pd, data, "SS_CHUI_SHOGAI", "SeishinShinkeiChuiShogai", 1);
			// 遂行機能障害
			IkenshoCommon.addCheck(pd, data, "SS_SUIKOU_KINO_SHOGAI", "SeishinShinkeiSuikouKinoShogai", 1);
			// 社会的行動障害
			IkenshoCommon.addCheck(pd, data, "SS_SHAKAITEKI_KODO_SHOGAI", "SeishinShinkeiShakaitekiKodoShogai", 1);
			// その他の認知障害
			IkenshoCommon.addCheck(pd, data, "SS_NINCHI_SHOGAI", "SeishinShinkeiNinchiShogai", 1);
			// 気分障害
			IkenshoCommon.addCheck(pd, data, "SS_KIBUN_SHOGAI", "SeishinShinkeiKibunSyogai", 1);
			// 睡眠障害
			IkenshoCommon.addCheck(pd, data, "SS_SUIMIN_SHOGAI", "SeishinShinkeiSuiminSyogai", 1);
			// 幻覚
			IkenshoCommon.addCheck(pd, data, "SS_GNS_GNC", "SeishinShinkeiGenkaku", 1);
			// 妄想
			IkenshoCommon.addCheck(pd, data, "SS_MOUSOU", "SeishinShinkeiMousou", 1);

			// その他
			if (IkenshoCommon.addCheck(pd, data, "SS_OTHER",
					"SeishinShinkeiOther", 1)) {
				// その他名称
				IkenshoCommon.addString(pd, data, "SS_OTHER_NM",
						"SeishinShinkeiOtherName");
			}

// [ID:0000790][Satoshi Tokusari] 2014/10 del-Start 医師意見書の専門科受診の有無の仕様変更対応
//			// 専門医受診の有無
//			if (IkenshoCommon
//					.addSelection(pd, data, "SENMONI", new String[] {
//							"SeishinShinkeiSenmoniOn",
//							"SeishinShinkeiSenmoniOff" }, -1)) {
//				if ("1".equals(ACCastUtilities.toString(data.getData("SENMONI")))) {
//					// 専門医
//					IkenshoCommon.addString(pd, data, "SENMONI_NM",
//							"SeishinShinkeiSenmoniName");
//				}
//			}
// [ID:0000790][Satoshi Tokusari] 2014/10 del-End

			break;
		default:
			pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");
		case 2:
			// 無の場合
			pd.addAttribute("SeishinShinkeiIshikiSyogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiKiokuShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiChuiShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiSuikouKinoShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiShakaitekiKodoShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiNinchiShogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiKibunSyogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiSuiminSyogai", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiGenkaku", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiMousou", "Visible", "FALSE");
			pd.addAttribute("SeishinShinkeiOther", "Visible", "FALSE");
// [ID:0000790][Satoshi Tokusari] 2014/10 del-Start 医師意見書の専門科受診の有無の仕様変更対応
//			pd.addAttribute("SeishinShinkeiSenmoniOn", "Visible", "FALSE");
//			pd.addAttribute("SeishinShinkeiSenmoniOff", "Visible", "FALSE");
// [ID:0000790][Satoshi Tokusari] 2014/10 del-End
			break;
		}

// [ID:0000790][Satoshi Tokusari] 2014/10 add-Start 医師意見書の専門科受診の有無の仕様変更対応
		// 専門科受診の有無
		if (IkenshoCommon
				.addSelection(pd, data, "SENMONI", new String[] {
						"SeishinShinkeiSenmoniOn",
						"SeishinShinkeiSenmoniOff" }, -1)) {
			if ("1".equals(ACCastUtilities.toString(data.getData("SENMONI")))) {
				// 専門科
				IkenshoCommon.addString(pd, data, "SENMONI_NM",
						"SeishinShinkeiSenmoniName");
			}
		}
// [ID:0000790][Satoshi Tokusari] 2014/10 add-End
        
	    // (5) てんかん
	    ary = new String[] { "TenkanHindoShuichi", "TenkanHindoTsukiichi",
				"TenkanHindoNenichi"}; 
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
	    
	    
	    
	    
	    
		// -----------------------------------------------------
		// 4.特別な医療
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
		
		// 間歇的導尿
		IkenshoCommon.addCheck(pd, data, "KANKETSUTEKI_DOUNYOU", "SochiDounyo", 1);
		
		
		// モニター測定
		IkenshoCommon
				.addCheck(pd, data, "MONITOR", "TokubetsuShochiMonitor", 1);
		// 褥瘡の処置
		IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI",
				"TokubetsuShochiJokuso", 1);
		// カテーテル
		IkenshoCommon.addCheck(pd, data, "CATHETER", "ShikkinShochi", 1);
	    
	    
	    
		// -----------------------------------------------------
		// 5.サービス利用に関する意見
		// -----------------------------------------------------	    
	    // (1) 現在、発生の可能性が高い病態とその対処方針
		boolean checkByotai = false;
		// 尿失禁
		checkByotai |= IkenshoCommon.addCheck(pd, data, "NYOUSIKKIN", "IkenNyoushikkin", 1);
	    // 転倒・骨折
		checkByotai |= IkenshoCommon.addCheck(pd, data, "TENTOU_KOSSETU", "IkenTentouKossetsu", 1);
	    // 徘徊
		checkByotai |= IkenshoCommon.addCheck(pd, data, "HAIKAI_KANOUSEI", "IkenHaikai", 1);
	    // 褥瘡
		checkByotai |= IkenshoCommon.addCheck(pd, data, "JOKUSOU_KANOUSEI", "IkenJokusou", 1);
	    // 嚥下性肺炎
		checkByotai |= IkenshoCommon.addCheck(pd, data, "ENGESEIHAIEN", "IkenEngeseiHaien", 1);
	    // 腸閉塞
		checkByotai |= IkenshoCommon.addCheck(pd, data, "CHOUHEISOKU", "IkenChouheisoku", 1);
	    // 易感染性
		checkByotai |= IkenshoCommon.addCheck(pd, data, "EKIKANKANSEN", "IkenEkikansensei", 1);
	    // 心肺機能の低下
		checkByotai |= IkenshoCommon.addCheck(pd, data, "SINPAIKINOUTEIKA", "IkenShinpaikinouTeika", 1);
	    // 疼痛
		checkByotai |= IkenshoCommon.addCheck(pd, data, "ITAMI", "IkenToutsu", 1);
	    // 脱水
		checkByotai |= IkenshoCommon.addCheck(pd, data, "DASSUI", "IkenDassui", 1);
	    // 行動障害
		checkByotai |= IkenshoCommon.addCheck(pd, data, "KOUDO_SHOGAI", "IkenKoudo", 1);
	    // 精神状態の増悪
		checkByotai |= IkenshoCommon.addCheck(pd, data, "SEISIN_ZOAKU", "IkenSeishin", 1);
	    // けいれん発作
		checkByotai |= IkenshoCommon.addCheck(pd, data, "KEIREN_HOSSA", "IkenKeiren", 1);
	    
		// その他
	    if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "IkenByoutaita", 1)) {
	      // その他名
	      IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "IkenByoutaitaName");
	      checkByotai = true;
	    }
	    // 対処方針
	    if (checkByotai) {
	    	IkenshoCommon.addString(pd, "Grid10.h1.w2", ACCastUtilities.toString(VRBindPathParser.get("TAISHO_HOUSIN", data)));
	    }
	    
	    
	    // 介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項
		// 血圧について
	    setStringIfChecked(pd, data, "KETUATU", "KETUATU_RYUIJIKOU", "IkenKetsuatsuRyuijikou");
		// 嚥下について
	    setStringIfChecked(pd, data, "ENGE", "ENGE_RYUIJIKOU", "IkenEngeRyuijikou");
		// 摂食について
	    setStringIfChecked(pd, data, "SESHOKU", "SESHOKU_RYUIJIKOU", "IkenSesshokuRyuijikou");
		// 移動について
	    setStringIfChecked(pd, data, "IDOU", "IDOU_RYUIJIKOU", "IkenIdouRyuijikou");
		// 行動障害について
	    setStringIfChecked(pd, data, "SFS_KOUDO", "SFS_KOUDO_RYUIJIKOU", "IkenKoudoRyuijikou");
		// 精神症状について
	    setStringIfChecked(pd, data, "SFS_SEISIN", "SFS_SEISIN_RYUIJIKOU", "IkenSeishinRyuijikou");
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
		String tokki = ACCastUtilities.toString((VRBindPathParser.get("IKN_TOKKI", data)));
	    IkenshoCommon.addString(pd, "Grid17.h1.w1", ACTextUtilities.concatLineWrap(ACTextUtilities.separateLineWrapOnByte(tokki, 92)));		
		
		pd.endPageEdit();
	    
	}

	protected int getFormatType() {
		return IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
	}
	
	
	
	private static void setStringIfChecked(ACChotarouXMLWriter pd, VRMap data,
			String checkKey, String stringKey, String chtField) throws Exception {
		
		if(ACCastUtilities.toInt(VRBindPathParser.get(checkKey, data), 0) != 2){
			return;
		}
		
		String value = ACCastUtilities.toString(VRBindPathParser.get(stringKey, data));
		IkenshoCommon.addString(pd, chtField, value);
		
	}
	
	// 項目が選択されているか
	private static boolean isSelected(VRMap data, String key) throws Exception {
		if(ACCastUtilities.toInt(VRBindPathParser.get(key, data), 0) == 1){
			return true;
		}
		return false;
	}
	
	// 親のチェックと連動して、程度のチェックを設定する
	protected static boolean setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String childKey, String[] childrenTarget) throws Exception {
		
		// 親のチェックがONの場合
		if (isSelected(data, parentKey)) {
			IkenshoCommon.addSelection(pd, data, childKey, childrenTarget, -1);
			return true;
		} else {
			// 親のチェックがOFFの場合
			setVisibleOffAll(pd, childrenTarget);
			return false;
		}
		
	}
	
	// 親のチェックと連動して、程度のチェックを設定しつつ備考も設定する
	protected static boolean setSynchronizedChecksWithText(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String childKey,
			String[] childrenTarget, String textKey, String textTarget) throws Exception {
		
		boolean select = setSynchronizedChecks(pd, data, parentKey, childKey, childrenTarget);
		// 親のチェックがONの場合
		if (select) {
			// 文字列
			IkenshoCommon.addString(pd, textTarget, ACCastUtilities.toString(VRBindPathParser.get(textKey, data)));
		}
		
		return select;
		
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
	 * 精神症状・能力障害二軸超過等、数値のチェック用
	 * @param pd
	 * @param data
	 * @param key Mapから値を取り出すキー
	 * @param chtTargetName 定義体上の項目名(連番除く)
	 * @param length 1～lengthまで項目が存在
	 * @throws Exception
	 */
	protected static void setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String key, String chtTargetName, int length) throws Exception {
		
		int value = ACCastUtilities.toInt(data.get(key), 0);
		
		for(int i = 1; i <= length; i++) {
			
			if (value != i) {
				pd.addAttribute(chtTargetName + i, "Visible", "FALSE");
			}
		}
	}
	
	protected static void setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String key, String[] chtTargetNames) throws Exception {
		
		int value = ACCastUtilities.toInt(data.get(key), 0);
		
		for(int i = 0; i < chtTargetNames.length; i++) {
			
			if (value != (i + 1)) {
				pd.addAttribute(chtTargetNames[i], "Visible", "FALSE");
			}
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
        return "IkenshoShien1FromH2604.xml";
    }

    protected String getIkenshoFormatFilePath2() {
        return "IkenshoShien2FromH2604.xml";
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
