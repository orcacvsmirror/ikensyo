/*
 * �쐬��: 2006/07/19
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

	// ��t�ӌ����Ή�
	
	/**
	 * �R���X�g���N�^�ł��B
	 * 
	 * @param data
	 *            ����f�[�^
	 * @param picture
	 *            �S�g�}
	 * @throws HeadlessException
	 *             ��������O
	 */
	public IkenshoIkenshoPrintSettingIshi(VRMap data,
			IkenshoHumanPicture picture) throws HeadlessException {
		super(data, picture);
	}
    
    /**
     * ����������s���܂��B
     * 
     * @return �I�����Ă悢��
     */
    protected boolean print() {
        return super.print();
    }
    

	/**
	 * ��ʐݒ�
	 * 
	 */
	protected void jbInit() throws Exception {
		super.jbInit();

		this.setTitle("�u��t�ӌ����v����ݒ�");
		getPrintOptionGroup().setText("�u��t�ӌ����v����I�v�V����");
		getBillPrintGroup().setText("����������i�u��t�ӌ����v�Ɠ����Ɂj");
		getCsvGroup().setText("CSV�t�@�C���ł́u��t�ӌ����v�̒�o");

        // [ID:0000555][Tozo TANAKA] 2009/09/14 add begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        getPageHeaderGroup().setText("�Ńw�b�_(�ی��ҁE�󋋎Ҕԍ�)");
        csvSubmitHiHokensyaUnselectAlert.setText("�󋋎Ҕԍ����ݒ肳��Ă��܂���B");
        // [ID:0000555][Tozo TANAKA] 2009/09/14 add end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�

	}
	protected void callPrintIkensho(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate) throws Exception {
          //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 
          if(!patientCareState.isEnabled() || !patientCareState.isSelected()){
              data.put("KIND", ACCastUtilities.toInteger(0));
          }
          //[ID:0000515][Tozo TANAKA] 2009/09/16 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 
		IkenshoIkenshoPrintSettingIshi.printIkensho(pd, formatName, data,
				printDate);
	}

    protected void callPrintIkensho2(ACChotarouXMLWriter pd, String formatName,
            VRMap data, Date printDate, byte[] imageBytes) throws Exception {
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 
        if(!patientCareState.isEnabled() || !patientCareState.isSelected()){
            data.put("KIND", ACCastUtilities.toInteger(0));
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 

		IkenshoIkenshoPrintSettingIshi.printIkensho2(pd, formatName, data,
				printDate, imageBytes);
	}

	public static void printIkensho(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate) throws Exception {

		// �g���܂킵�ϐ�
		Date date = new Date();

		pd.beginPageEdit(formatName);

		// -----------------------------------------------------
		// �w�b�_
		// -----------------------------------------------------
		// �ی��Ҕԍ���
		printIkenshoHeader(pd, data, printDate);
		// �L����
		IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 2, 1);

		// -----------------------------------------------------
		// �\���Ҋ�{���
		// -----------------------------------------------------
		// �ӂ肪��
		IkenshoCommon.addString(pd, data, "PATIENT_KN", "Grid1.h1.w3");
		// ����
		IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid1.h2.w3");

		// ���N����
		date = (Date) VRBindPathParser.get("BIRTHDAY", data);
		String era = VRDateParser.format(date, "gg");
		if ("��".equals(era)) {
			pd.addAttribute("PatienBirthTaisho", "Visible", "FALSE");
			pd.addAttribute("PatienBirthShowa", "Visible", "FALSE");
			pd.addAttribute("PatienBirthHeisei", "Visible", "FALSE");
		} else if ("��".equals(era)) {
			pd.addAttribute("PatienBirthMeiji", "Visible", "FALSE");
			pd.addAttribute("PatienBirthShowa", "Visible", "FALSE");
			pd.addAttribute("PatienBirthHeisei", "Visible", "FALSE");
		} else if ("��".equals(era)) {
			pd.addAttribute("PatienBirthMeiji", "Visible", "FALSE");
			pd.addAttribute("PatienBirthTaisho", "Visible", "FALSE");
			pd.addAttribute("PatienBirthHeisei", "Visible", "FALSE");
		} else {
			pd.addAttribute("PatienBirthMeiji", "Visible", "FALSE");
			pd.addAttribute("PatienBirthTaisho", "Visible", "FALSE");
			pd.addAttribute("PatienBirthShowa", "Visible", "FALSE");
		}
		// �N
		IkenshoCommon.addString(pd, "Grid1.h3.w3", VRDateParser.format(date,
				"ee"));
		// ��
		IkenshoCommon.addString(pd, "Grid1.h3.w5", VRDateParser.format(date,
				"MM"));
		// ��
		IkenshoCommon.addString(pd, "Grid1.h3.w7", VRDateParser.format(date,
				"dd"));

		// �N��
		IkenshoCommon.addString(pd, data, "AGE", "Grid1.h3.w10");

		// ����
		IkenshoCommon.addSelection(pd, data, "SEX", new String[] {
				"PatientSexMale", "PatientSexFemale" }, -1);

		// �X�֔ԍ�
		IkenshoCommon.addZip(pd, data, "POST_CD", "Grid1.h1.w11",
				"Grid1.h1.w17");
		// �Z��
		IkenshoCommon.addString(pd, data, "ADDRESS", "Grid1.h2.w12");
		// �d�b�ԍ�
		IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid1.h3.w17",
				"Grid1.h3.w19", "Grid1.h3.w21");

		// ��t����
		IkenshoCommon.addSelection(pd, data, "DR_CONSENT", new String[] {
				"DoctorAgree", "DoctorDisagree" }, -1);

		if (ACCastUtilities.toInt(VRBindPathParser.get("DR_NM_OUTPUT_UMU", data), 0) == 1) {
			// ��t����
			IkenshoCommon.addString(pd, data, "DR_NM", "Grid4.h1.w3");
		}
		// ��Ë@�֖�
		IkenshoCommon.addString(pd, data, "MI_NM", "Grid4.h2.w4");
		// ��Ë@�֏��ݒn
		IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid4.h3.w5");

		// ��Ë@�֓d�b�ԍ�
		IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid5.h1.w2",
				"Grid5.h1.w4", "Grid5.h1.w6");

		// ��Ë@��FAX�ԍ�
		IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid5.h3.w2",
				"Grid5.h3.w4", "Grid5.h3.w6");

		// �ŏI�f�@��
		date = (Date) VRBindPathParser.get("LASTDAY", data);
		if (date != null) {
			// ����
			IkenshoCommon.addString(pd, "Grid6.h1.w12", VRDateParser.format(
					date, "ggg"));
			// �N
			IkenshoCommon.addString(pd, "Grid6.h1.w11", VRDateParser.format(
					date, "ee"));
			// ��
			IkenshoCommon.addString(pd, "Grid6.h1.w9", VRDateParser.format(
					date, "MM"));
			// ��
			IkenshoCommon.addString(pd, "Grid6.h1.w7", VRDateParser.format(
					date, "dd"));
		}

		// �ӌ����쐬��
		IkenshoCommon.addSelection(pd, data, "IKN_CREATE_CNT", new String[] {
				"CreateFirstTime", "CreateManyTime" }, -1);

		// ���Ȏ�f
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
				// ���ȓ��e
				for (int i = 0; i < 12; i++) {
					pd.addAttribute(takaList[i], "Visible", "FALSE");
				}
				// ���Ȃ��̑�
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
				// ���ȓ��e
				for (int i = 0; i < 12; i++) {
					if ((taka & (1 << i)) == 0) {
						pd.addAttribute(takaList[i], "Visible", "FALSE");
					}
				}
				// ���Ȃ��̑�
				if (ACCastUtilities.toInt(VRBindPathParser.get("TAKA_OTHER_FLAG", data), 0) == 1) {
					IkenshoCommon.addString(pd, data, "TAKA_OTHER",
							"Grid6.h3.w4");
				} else {
					pd.addAttribute("TakaOther", "Visible", "FALSE");
				}
			}
		} else {
			pd.addAttribute("TakaOn", "Visible", "FALSE");
			// ���ȓ��e
			for (int i = 0; i < 12; i++) {
				pd.addAttribute(takaList[i], "Visible", "FALSE");
			}
			// ���Ȃ��̑�
			pd.addAttribute("TakaOther", "Visible", "FALSE");
		}

		// -----------------------------------------------------
		// ���a�Ɋւ���ӌ�
		// -----------------------------------------------------
		// ���a
		for (int i = 0; i < 3; i++) {
			// �f�f��
			IkenshoCommon.addString(pd, data, "SINDAN_NM" + (i + 1), "Grid8.h"
					+ (i + 1) + ".w2");
			
			String[] deleteTarget = new String[] {
					"Grid8.h" + (i + 1) + ".w13", "Grid8.h" + (i + 1) + ".w11",
					"Grid8.h" + (i + 1) + ".w9" };
			if(ACCastUtilities.toInt(VRBindPathParser.get("SHUSSEI" + (i + 1), data), 0) == 0){
				// ���ǔN����
				String text = ACCastUtilities.toString(VRBindPathParser.get("HASHOU_DT"
						+ (i + 1), data));
				setStringDate(pd, text, "Grid8.h" + (i + 1) + ".w15", new String[] {
						"Grid8.h" + (i + 1) + ".w14", "Grid8.h" + (i + 1) + ".w12",
						"Grid8.h" + (i + 1) + ".w10", }, deleteTarget);
				// �o����OFF
				pd.addAttribute("shussei" + (i + 1), "Visible", "FALSE");
			}else{
				// �o����ON�̏ꍇ�A�N�����̕��������
				for(int j = 0; j < deleteTarget.length; j++){
					IkenshoCommon.addString(pd, deleteTarget[j], "");
				}
			}
		}

		// ���@�� TODO
		for (int i = 0; i < 2; i++) {
			// ���a��
			IkenshoCommon.addString(pd, data, "NYUIN_NM" + (i + 1), "Grid1" + (i + 1) + ".h1.w13");
			
			// ���@�J�n�E�I���N��
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

		// �Ǐ���萫
		// �s����ɂ������̓I�ȏ�
		IkenshoCommon.addSelection(pd, data, "SHJ_ANT", new String[] {
				"ShojoAntei", "ShojoFuantei", "ShojoFumei" }, -1,
				"INSECURE_CONDITION", "ShojoFuanteiJokyo", 2);

        //2009/01/09 [Tozo Tanaka] Replace - begin
//		// ���a���Ï��
//		IkenshoCommon.addString(pd, data, "MT_STS", "ShobyoKeika");
//
//		// ���
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
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
//        //���a���Ï��
//        ACChotarouXMLUtilities.setValue(pd, "ShobyoKeika",
//                getInsertionLineSeparatorToStringOnByte(ACCastUtilities.toString(data
//                        .get("MT_STS")), 100));
//        
//        //���
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
//            //���7�����8�����͂���Ă���ꍇ
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

        // ���a���Ï��
        String text = ACCastUtilities.toString(data.get("MT_STS"));
        String[] sickProgressLines = ACTextUtilities.separateLineWrapOnByte(
                text, 100);
        int totalLineCount = sickProgressLines.length;
        sbSickProgress
                .append(ACTextUtilities.concatLineWrap(sickProgressLines));

        text = text.replaceAll("[\r\n]", "");
        int totalCharCount = text.length();
        int totalByteCount = text.getBytes().length;

        // ���
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
            // ���a�̌o�߂�251�����ȏ�܂���6�s�ȏ�܂��͖�ܖ���6�ȏ�̏ꍇ
            int lastLineByteCount = 0;
            StringBuffer sbMedicine = new StringBuffer();
            for (int index = 1; index <= 8; index++) {
                StringBuffer sb = new StringBuffer();
                int medicineCharLen = 0;
                int dosageCharLen = 0;
                int unitCharLen = 0;
                int usageCharLen = 0;
                // ��܃Z�b�g������
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

                // �A�����ĕ\�����邩���s���邩�𔻒�
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
                // �ő啶���������𔻒�
                if (totalCharCount + lineCharLen > 560) {
                    int useCharCount = 560 - totalCharCount;
                    int lastPos = 0;
                    // ��ܖ�
                    if (useCharCount > medicineCharLen) {
                        lastPos += medicineCharLen + 1;
                        useCharCount -= medicineCharLen;
                        // �p��
                        if (useCharCount > dosageCharLen) {
                            lastPos += dosageCharLen;
                            useCharCount -= dosageCharLen;
                            // �p�ʒP��
                            if (useCharCount > unitCharLen) {
                                lastPos += unitCharLen;
                                useCharCount -= unitCharLen;
                                // �p�@
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
            // ���a�̌o�߂Ɩ�ܖ��̋�؂�Ƃ��ĉ��sx2���g�p
            if (sbSickProgress.length() > 0 && sbMedicine.length() > 0) {
                // ���a�̌o�߂Ɩ�ܖ��̊Ԃɂ�1�s���̋�s������ŋ�؂�Ƃ���B
                sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
                sbSickProgress.append(IkenshoConstants.LINE_SEPARATOR);
            }
            // �A��
            sbSickProgress.append(sbMedicine.toString());
        } else {
            // ���a�̌o�߂�250�����ȓ�����5�s�ȓ�����ܖ���6�ȓ��̏ꍇ

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
        // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
        
        //2009/01/09 [Tozo Tanaka] Replace - end

		// -----------------------------------------------------
		// ���ʂȈ�Ái���݁A����I�ɁA���邢�͕p��Ɏ󂯂Ă����Áj
		// -----------------------------------------------------
		// �_�H�Ǘ�
		IkenshoCommon.addCheck(pd, data, "TNT_KNR", "ShochiTenteki", 1);
		// ���S�Ö��h�{
		IkenshoCommon.addCheck(pd, data, "CHU_JOU_EIYOU",
				"ShochiTyushinJomyakuEiyo", 1);
		// ����
		IkenshoCommon.addCheck(pd, data, "TOUSEKI", "ShochiTouseki", 1);
		// �X�g�[�}�̏��u
		IkenshoCommon.addCheck(pd, data, "JINKOU_KOUMON", "ShochiStoma", 1);
		// �_�f�Ö@
		IkenshoCommon.addCheck(pd, data, "OX_RYO", "ShochiOXRyoho", 1);
		// ���X�s���[�^�[
		IkenshoCommon.addCheck(pd, data, "JINKOU_KOKYU", "ShochiRespirator", 1);
		// �C�ǐ؊J�̏��u
		IkenshoCommon.addCheck(pd, data, "KKN_SEK_SHOCHI", "ShochiKikanSekkai",
				1);
		// �u�ɂ̊Ō�
		IkenshoCommon.addCheck(pd, data, "TOUTU", "ShochiToutsu", 1);
		// �o�ǉh�{
		IkenshoCommon.addCheck(pd, data, "KEKN_EIYOU", "ShochiKeikanEiyo", 1);
		// �z�����u
		if (ACCastUtilities.toInt(VRBindPathParser.get("KYUIN_SHOCHI", data), 0) == 1) {
			// �z����
			IkenshoCommon.addString(pd, "ShochiKyuinCount", ACCastUtilities
					.toString(VRBindPathParser.get("KYUIN_SHOCHI_CNT", data)));
			// �z������
			IkenshoCommon.addSelection(pd, data, "KYUIN_SHOCHI_JIKI",
					new String[] { "ShochiKyuinTemporary",
							"ShochiKyuinContinuous" }, -1);
		} else {
			// �z�����u�A�z���񐔁A�z�������̃`�F�b�N���O��
			pd.addAttribute("ShochiKyuin", "Visible", "FALSE");
			pd.addAttribute("ShochiKyuinTemporary", "Visible", "FALSE");
			pd.addAttribute("ShochiKyuinContinuous", "Visible", "FALSE");
		}
		// ���j�^�[����
		IkenshoCommon
				.addCheck(pd, data, "MONITOR", "TokubetsuShochiMonitor", 1);
		// ��ጂ̏��u
		IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI",
				"TokubetsuShochiJokuso", 1);
		// �J�e�[�e��
		IkenshoCommon.addCheck(pd, data, "CATHETER", "ShikkinShochi", 1);

		// -----------------------------------------------------
		// �S�g�̏�ԂɊւ���ӌ�
		// -----------------------------------------------------
		// �s����̏�Q�̗L��
		if (ACCastUtilities.toInt(VRBindPathParser.get("MONDAI_FLAG", data), 0) == 1) {
			boolean problemAction = false;
			// ����t�]
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_CHUYA",
					"KodoShogaiTyuya", 1);
			// �\��
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_BOUGEN",
					"KodoShogaiBogen", 1);
			// �\�s
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_BOUKOU",
					"KodoShogaiBoukou", 1);
			// ��R
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_TEIKOU",
					"KodoShogaiTeikou", 1);
			// �p�j
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_HAIKAI",
					"KodoShogaiHaikai", 1);
			// �΂̕s�n��
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_FUSIMATU",
					"KodoShogaiFushimatsu", 1);
			// �s��
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_FUKETU",
					"KodoShogaiFuketsu", 1);
			// �ِH�s��
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_ISHOKU",
					"KodoShogaiIshoku", 1);
			// ���I���s��
			problemAction |= IkenshoCommon.addCheck(pd, data, "KS_SEITEKI_MONDAI",
					"KodoShogaiSeiteki", 1);
			// ���̑�
			if (IkenshoCommon.addCheck(pd, data, "KS_OTHER",
					"KodoShogaiOther", 1)) {
				// ���̑�����
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
			// �S�Ẵ`�F�b�N���O��
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

		// ���_�E�_�o�Ǐ�̗L��
		switch (ACCastUtilities.toInt(VRBindPathParser.get("SEISIN", data), 0)) {
		case 1:
			// �L�̏ꍇ
			IkenshoCommon
					.addString(pd, data, "SEISIN_NM", "SeishinShinkeiName");
			pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");

			// �Ǐ󍀖�
			// �����
			IkenshoCommon.addCheck(pd, data, "SS_SENMO", "SeishinShinkeiSenmo",
					1);
			// �X���X��
			IkenshoCommon.addCheck(pd, data, "SS_KEMIN_KEIKO",
					"SeishinShinkeiKeiminKeiko", 1);
			// �����E����
			IkenshoCommon.addCheck(pd, data, "SS_GNS_GNC",
					"SeishinShinkeiGenshiGenkaku", 1);
			// �ϑz
			IkenshoCommon.addCheck(pd, data, "SS_MOUSOU",
					"SeishinShinkeiMousou", 1);
			// ��������
			IkenshoCommon.addCheck(pd, data, "SS_SHIKKEN_TOSHIKI",
					"SeishinShinkeiShikkenToshiki", 1);
			// ���F
			IkenshoCommon.addCheck(pd, data, "SS_SHITUNIN",
					"SeishinShinkeiShitsunin", 1);
			// ���s
			IkenshoCommon.addCheck(pd, data, "SS_SHIKKO",
					"SeishinShinkeiShikko", 1);
			// �F�m��Q
			IkenshoCommon.addCheck(pd, data, "SS_NINCHI_SHOGAI",
					"SeishinShinkeiNinchiShogai", 1);
			// �L����Q�i�Z���E�����j
			if (IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI",
					"SeishinShinkeiKiokuShogai", 1)) {
				// �L����Q�i�Z���j
				IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI_TANKI",
						"SeishinShinkeiKiokuShogaiTanki", 1);
				// �L����Q�i�����j
				IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI_CHOUKI",
						"SeishinShinkeiKiokuShogaiChouki", 1);
			}else{
				// �L����Q�i�Z���j�E�L����Q�i�����j�̃`�F�b�N���O��
				pd.addAttribute("SeishinShinkeiKiokuShogaiTanki", "Visible", "FALSE");
				pd.addAttribute("SeishinShinkeiKiokuShogaiChouki", "Visible", "FALSE");
			}
			// ���ӏ�Q
			IkenshoCommon.addCheck(pd, data, "SS_CHUI_SHOGAI",
					"SeishinShinkeiChuiShogai", 1);
			// ���s�@�\��Q
			IkenshoCommon.addCheck(pd, data, "SS_SUIKOU_KINO_SHOGAI",
					"SeishinShinkeiSuikouKinoShogai", 1);
			// �Љ�I�s����Q
			IkenshoCommon.addCheck(pd, data, "SS_SHAKAITEKI_KODO_SHOGAI",
					"SeishinShinkeiShakaitekiKodoShogai", 1);
			// ���̑�
			if (IkenshoCommon.addCheck(pd, data, "SS_OTHER",
					"SeishinShinkeiOther", 1)) {
				// ���̑�����
				IkenshoCommon.addString(pd, data, "SS_OTHER_NM",
						"SeishinShinkeiOtherName");
			}

			// �����f�̗L��
			if (IkenshoCommon
					.addSelection(pd, data, "SENMONI", new String[] {
							"SeishinShinkeiSenmoniOn",
							"SeishinShinkeiSenmoniOff" }, -1)) {
				if ("1".equals(ACCastUtilities.toString(data.getData("SENMONI")))) {
					// ����
					IkenshoCommon.addString(pd, data, "SENMONI_NM",
							"SeishinShinkeiSenmoniName");
				}
			}

			break;
		default:
			pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");
		case 2:
			// ���̏ꍇ
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
        
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�   
        //�{�ݍݑ�敪
        switch(ACCastUtilities.toInt(VRBindPathParser.get("KIND", data) , 0)){
        case 1:
            //�ݑ�
            IkenshoCommon.addString(pd, "Label113", "�ݑ�");
            break;
        case 2:
            //�{��
            IkenshoCommon.addString(pd, "Label113", "�{��");
            break;
        default:
            //�{�ݍݑ�敪���B��
            pd.addAttribute("Label113", "Visible", "FALSE");
            break;
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�   

		pd.endPageEdit();

	}

	public static void printIkensho2(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate, byte[] imageBytes) throws Exception {

	    pd.beginPageEdit(formatName);

	    // �g���܂킵�ϐ�
	    String[] ary = null;
	    String[] ary2 = null;
	    String[] ary3 = null;
	    String[] ary4 = null;
	    String[] ary5 = null;
	    String[] ary6 = null;
	    String[] ary7 = null;
	    String[] ary8 = null;
	    
		// -----------------------------------------------------
		// �w�b�_
		// -----------------------------------------------------
	    printIkenshoHeader(pd, data, printDate);

        if (ACCastUtilities.toInt(VRBindPathParser.get("HEADER_OUTPUT_UMU2", data)) == 1) {
            // ����
            IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w1");
            // �N
            IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w3");
            // �L����
            IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 5, 1,
                    "��)");
        } else {
            IkenshoCommon.addString(pd, "Grid2.h1.w4", "");
            IkenshoCommon.addString(pd, "Grid2.h1.w7", "");
            IkenshoCommon.addString(pd, "Grid2.h1.w9", "");
            IkenshoCommon.addString(pd, "Grid2.h1.w11", "");
        }
	    
		// -----------------------------------------------------
		// �S�g�̏�ԂɊւ���ӌ��E��
		// -----------------------------------------------------
	    // �Ă񂩂�
	    ary = new String[] { "TenkanHindoShuichi", "TenkanHindoTsukiichi",
				"TenkanHindoNenichi", "TenkanHindoIchinenIjou" }; 
	    if(ACCastUtilities.toInt(VRBindPathParser.get("TENKAN", data), 0) == 1){
	    	// �L�̏ꍇ
	    	pd.addAttribute("TenkanOff", "Visible", "FALSE");
	    	// �p�x
	    	IkenshoCommon.addSelection(pd, data, "TENKAN_HINDO", ary, -1);
	    }else{
	    	// ���̏ꍇ
	    	pd.addAttribute("TenkanOn", "Visible", "FALSE");
	    	// �p�x�̃`�F�b�N��S�ĊO��
	    	setVisibleOffAll(pd, ary);
	    }
	    
	    // �g�̂̏��
		// �����r
		IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {
				"KikiudeRight", "KikiudeLeft" }, -1);
		// �g��
		IkenshoCommon.addString(pd, data, "HEIGHT", "Grid4.h1.w2");
		// �̏d
		IkenshoCommon.addString(pd, data, "WEIGHT", "Grid4.h1.w4");

		// �̏d�̕ω�
		IkenshoCommon.addSelection(pd, data, "WEIGHT_CHANGE", new String[] {
				"WeightChangeZouka", "WeightChangeIdi", "WeightChangeGensho" },
				-1);

		// �l������
		setSynchronizedChecksWithText(pd, data, "SISIKESSON", "Shishikesson",
				"SISIKESSON_TEIDO", new String[] { "ShishikessonTeidoKei",
						"ShishikessonTeidoChu", "ShishikessonTeidoJu" },
				"SISIKESSON_BUI", "ShishikessonBui");

	    // ���
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
	    	// ���ON
	    	boolean action = false;
	    	// ���㎈
	    	action |= setSynchronizedChecks(pd, data, "MAHI_LEFTARM", "MahiLeftArm",
					"MAHI_LEFTARM_TEIDO", ary);
	    	// ������
	    	action |= setSynchronizedChecks(pd, data, "MAHI_LOWERLEFTLIMB",
					"MahiLowerLeftLimb", "MAHI_LOWERLEFTLIMB_TEIDO", ary2);
			// �E�㎈
	    	action |= setSynchronizedChecks(pd, data, "MAHI_RIGHTARM", "MahiRightArm",
					"MAHI_RIGHTARM_TEIDO", ary3);
			// �E����
	    	action |= setSynchronizedChecks(pd, data, "MAHI_LOWERRIGHTLIMB",
					"MahiLowerRightLimb", "MAHI_LOWERRIGHTLIMB_TEIDO", ary4);
	    	// ���̑�
	    	action |= setSynchronizedChecksWithText(pd, data, "MAHI_ETC", "MahiOther",
					"MAHI_ETC_TEIDO", ary5, "MAHI_ETC_BUI", "MahiOtherBui");
	    	if(!action){
	    		pd.addAttribute("Mahi", "Visible", "FALSE");
	    	}
	    	
	    }else{
	    	// ���OFF
	    	// �S�Ẵ`�F�b�N��OFF�ɂ���
	    	// ������
	    	pd.addAttribute("MahiLeftArm", "Visible", "FALSE");
	    	pd.addAttribute("MahiLowerLeftLimb", "Visible", "FALSE");
	    	pd.addAttribute("MahiRightArm", "Visible", "FALSE");
	    	pd.addAttribute("MahiLowerRightLimb", "Visible", "FALSE");
	    	pd.addAttribute("MahiOther", "Visible", "FALSE");
	    	// ���x
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    }

	    // �ؗ͂̒ቺ
	    setSynchronizedChecksWithText(pd, data, "KINRYOKU_TEIKA",
				"KinryokuTeika", "KINRYOKU_TEIKA_TEIDO", new String[] {
						"KinryokuTeikaTeidoKei", "KinryokuTeikaTeidoChu",
						"KinryokuTeikaTeidoJu" }, "KINRYOKU_TEIKA_BUI",
				"KinryokuTeikaBui");
	    
	    // �֐߂̍S�k
	    // �z���`
	    // ���֐߉E
	    ary = new String[] { "KataKoushukuRightTeidoKei",
				"KataKoushukuRightTeidoChu", "KataKoushukuRightTeidoJu" };
	    // ���֐ߍ�
		ary2 = new String[] { "KataKoushukuLeftTeidoKei",
				"KataKoushukuLeftTeidoChu", "KataKoushukuLeftTeidoJu" };
	    // �Ҋ֐߉E
		ary3 = new String[] { "MataKoushukuRightTeidoKei",
				"MataKoushukuRightTeidoChu", "MataKoushukuRightTeidoJu" };
	    // �Ҋ֐ߍ�
		ary4 = new String[] { "MataKoushukuLeftTeidoKei",
				"MataKoushukuLeftTeidoChu", "MataKoushukuLeftTeidoJu" };
	    // �I�֐߉E
		ary5 = new String[] { "HijiKoushukuRightTeidoKei",
				"HijiKoushukuRightTeidoChu", "HijiKoushukuRightTeidoJu" };
	    // �I�֐ߍ�
		ary6 = new String[] { "HijiKoushukuLeftTeidoKei",
				"HijiKoushukuLeftTeidoChu", "HijiKoushukuLeftTeidoJu" };
	    // �G�֐߉E
		ary7 = new String[] { "HizaKoushukuRightTeidoKei",
				"HizaKoushukuRightTeidoChu", "HizaKoushukuRightTeidoJu" };
	    // �G�֐ߍ�
		ary8 = new String[] { "HizaKoushukuLeftTeidoKei",
				"HizaKoushukuLeftTeidoChu", "HizaKoushukuLeftTeidoJu" };
	    if(IkenshoCommon.addCheck(pd, data, "KOUSHU", "Koushuku", 1)){
	    	// �֐߂̍S�kON
    		// �e�̃A�N�V���������肷��t���O
	    	boolean actionParent = false;
	    	if (IkenshoCommon.addCheck(pd, data, "KATA_KOUSHU", "KataKoushuku",
					1)) {
				// ���֐�ON
	    		boolean actionChild = false;
				// ���֐߉E
				actionChild |= setSynchronizedChecks(pd, data, "KATA_KOUSHU_MIGI",
						"KataKoushukuRight", "KATA_KOUSHU_MIGI_TEIDO", ary);
				// ���֐ߍ�
				actionChild |= setSynchronizedChecks(pd, data, "KATA_KOUSHU_HIDARI",
						"KataKoushukuLeft", "KATA_KOUSHU_HIDARI_TEIDO", ary2);
				if(actionChild){
					// �e�̃t���O��true�ɐݒ肷��
					actionParent = true;
				}else{
					// �e���ڂɃ`�F�b�N������t���Ȃ��ꍇ�A���̊K�w���\���ɐݒ肷��
					pd.addAttribute("KataKoushuku","Visible","FALSE");
				}
			} else {
				// ���֐�OFF
				pd.addAttribute("KataKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("KataKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary);
				setVisibleOffAll(pd, ary2);
			}
			if (IkenshoCommon.addCheck(pd, data, "MATA_KOUSHU", "MataKoushuku",
					1)) {
				// �Ҋ֐�ON
	    		boolean actionChild = false;
				// �Ҋ֐߉E
				actionChild |= setSynchronizedChecks(pd, data, "MATA_KOUSHU_MIGI",
						"MataKoushukuRight", "MATA_KOUSHU_MIGI_TEIDO", ary3);
				// �Ҋ֐ߍ�
				actionChild |= setSynchronizedChecks(pd, data, "MATA_KOUSHU_HIDARI",
						"MataKoushukuLeft", "MATA_KOUSHU_HIDARI_TEIDO", ary4);
				if(actionChild){
					// �e�̃t���O��true�ɐݒ肷��
					actionParent = true;
				}else{
					// �e���ڂɃ`�F�b�N������t���Ȃ��ꍇ�A���̊K�w���\���ɐݒ肷��
					pd.addAttribute("MataKoushuku","Visible","FALSE");
				}
			} else {
				// �Ҋ֐�OFF
				pd.addAttribute("MataKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("MataKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary3);
				setVisibleOffAll(pd, ary4);
			}
			if (IkenshoCommon.addCheck(pd, data, "HIJI_KOUSHU", "HijiKoushuku",
					1)) {
				// �I�֐�ON
	    		boolean actionChild = false;
				// �I�֐߉E
				actionChild |= setSynchronizedChecks(pd, data, "HIJI_KOUSHU_MIGI",
						"HijiKoushukuRight", "HIJI_KOUSHU_MIGI_TEIDO", ary5);
				// �I�֐ߍ�
				actionChild |= setSynchronizedChecks(pd, data, "HIJI_KOUSHU_HIDARI",
						"HijiKoushukuLeft", "HIJI_KOUSHU_HIDARI_TEIDO", ary6);
				if(actionChild){
					// �e�̃t���O��true�ɐݒ肷��
					actionParent = true;
				}else{
					// �e���ڂɃ`�F�b�N������t���Ȃ��ꍇ�A���̊K�w���\���ɐݒ肷��
					pd.addAttribute("HijiKoushuku","Visible","FALSE");
				}
			} else {
				// �I�֐�OFF
				pd.addAttribute("HijiKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("HijiKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary5);
				setVisibleOffAll(pd, ary6);
			}
			if (IkenshoCommon.addCheck(pd, data, "HIZA_KOUSHU", "HizaKoushuku",
					1)) {
				// �G�֐�ON
	    		boolean actionChild = false;
				// �G�֐߉E
				actionChild |= setSynchronizedChecks(pd, data, "HIZA_KOUSHU_MIGI",
						"HizaKoushukuRight", "HIZA_KOUSHU_MIGI_TEIDO", ary7);
				// �G�֐ߍ�
				actionChild |= setSynchronizedChecks(pd, data, "HIZA_KOUSHU_HIDARI",
						"HizaKoushukuLeft", "HIZA_KOUSHU_HIDARI_TEIDO", ary8);
				if(actionChild){
					// �e�̃t���O��true�ɐݒ肷��
					actionParent = true;
				}else{
					// �e���ڂɃ`�F�b�N������t���Ȃ��ꍇ�A���̊K�w���\���ɐݒ肷��
					pd.addAttribute("HizaKoushuku","Visible","FALSE");
				}
			} else {
				// �G�֐�OFF
				pd.addAttribute("HizaKoushukuRight", "Visible", "FALSE");
				pd.addAttribute("HizaKoushukuLeft", "Visible", "FALSE");
				setVisibleOffAll(pd, ary7);
				setVisibleOffAll(pd, ary8);
			}
			if (IkenshoCommon.addCheck(pd, data, "KOUSHU_ETC", "KoushukuOther",
					1)) {
				// ���̑�ON
				// ���̑�����
				IkenshoCommon
						.addString(pd, "KoushukuOtherBui", ACCastUtilities
								.toString(VRBindPathParser.get(
										"KOUSHU_ETC_BUI", data)));
				// �e�̃t���O��true�ɐݒ肷��
				actionParent = true;
			}			
			if(!actionParent){
				// �e���ڂɈ���`�F�b�N������Ȃ��ꍇ�A�e�̃`�F�b�N���\���ɐݒ肷��
				pd.addAttribute("Koushuku", "Visible", "FALSE");
			}			
	    }else{
	    	// �֐߂̍S�kOFF
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
	    
	    // �֐߂̒ɂ�
	    setSynchronizedChecksWithText(pd, data, "KANSETU_ITAMI",
				"KansetsuItami", "KANSETU_ITAMI_TEIDO", new String[] {
						"KansetsuItamiTeidokei", "KansetsuItamiTeidoChu",
						"KansetsuItamiTeidoJu" }, "KANSETU_ITAMI_BUI",
				"KansetsuItamiBui");
	    
	    // �����E�s���Ӊ^��
	    // �z���`
		// �㎈�E���x
		ary = new String[] { "JoushiShicchoRightTeidoKei",
				"JoushiShicchoRightTeidoChu", "JoushiShicchoRightTeidoJu" };
		// �㎈�����x
		ary2 = new String[] { "JoushiShicchoLeftTeidoKei",
				"JoushiShicchoLeftTeidoChu", "JoushiShicchoLeftTeidoJu" };
		// �̊��E���x
		ary3 = new String[] { "TaikanShicchoRightTeidoKei",
				"TaikanShicchoRightTeidoChu", "TaikanShicchoRightTeidoJu" };
		// �̊������x
		ary4 = new String[] { "TaikanShicchoLeftTeidoKei",
				"TaikanShicchoLeftTeidoChu", "TaikanShicchoLeftTeidoJu" };
		// �����E���x
		ary5 = new String[] { "KashiShicchoRightTeidoKei",
				"KashiShicchoRightTeidoChu", "KashiShicchoRightTeidoJu" };
		// ���������x
		ary6 = new String[] { "KashiShicchoLeftTeidoKei",
				"KashiShicchoLeftTeidoChu", "KashiShicchoLeftTeidoJu" };
	    if(IkenshoCommon.addCheck(pd, data, "SICCHOU_FLAG", "Shiccho", 1)){
	    	// �����E�s���Ӊ^��ON
	    	// �e�̃A�N�V���������肷��t���O
	    	boolean action = false;
			// �㎈�E
			action |= setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_MIGI",
					"JoushiShicchoRight", "JOUSI_SICCHOU_MIGI_TEIDO", ary);
			// �㎈��
			action |= setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_HIDARI",
					"JoushiShicchoLeft", "JOUSI_SICCHOU_HIDARI_TEIDO", ary2);
			// �̊��E
			action |= setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_MIGI",
					"TaikanShicchoRight", "TAIKAN_SICCHOU_MIGI_TEIDO", ary3);
			// �̊���
			action |= setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_HIDARI",
					"TaikanShicchoLeft", "TAIKAN_SICCHOU_HIDARI_TEIDO", ary4);
			// �����E
			action |= setSynchronizedChecks(pd, data, "KASI_SICCHOU_MIGI",
					"KashiShicchoRight", "KASI_SICCHOU_MIGI_TEIDO", ary5);
			// ������
			action |= setSynchronizedChecks(pd, data, "KASI_SICCHOU_HIDARI",
					"KashiShicchoLeft", "KASI_SICCHOU_HIDARI_TEIDO", ary6);
			if(!action){
				// �e���ڂɈ���`�F�b�N������Ȃ������ꍇ�͐e���\���ɂ���B
				pd.addAttribute("Shiccho", "Visible", "FALSE");
			}
	    }else{
	    	// �����E�s���Ӊ^��OFF
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
	    
	    // ���
		setSynchronizedChecksWithText(pd, data, "JOKUSOU", "Jokusou",
				"JOKUSOU_TEIDO", new String[] { "JokusouTeidoKei",
						"JokusouTeidoChu", "JokusouTeidoJu" }, "JOKUSOU_BUI",
				"JokusouBui");

		// ���̑��̔畆����
		setSynchronizedChecksWithText(pd, data, "HIFUSIKKAN", "HifuShikkan",
				"HIFUSIKKAN_TEIDO", new String[] { "HifuShikkanTeidoKei",
						"HifuShikkanTeidoChu", "HifuShikkanTeidoJu" },
				"HIFUSIKKAN_BUI", "HifuShikkanBui");
	    
		// -----------------------------------------------------
		// �T�[�r�X���p�Ɋւ���ӌ�
		// -----------------------------------------------------	    
	    // ���݁A�����̉\���������a�ԂƂ��̑Ώ����j
		// �Ώ����j�i�[�p
	    ArrayList words = new ArrayList();
		// �A����
	    addSickStateCheck(pd, data, "NYOUSIKKIN", "IkenNyoushikkin",
	                      "NYOUSIKKIN_TAISHO_HOUSIN", words);
		// �]�|�E����
	    addSickStateCheck(pd, data, "TENTOU_KOSSETU", "IkenTentouKossetsu",
                "TENTOU_KOSSETU_TAISHO_HOUSIN", words);
		// �p�j
	    addSickStateCheck(pd, data, "HAIKAI_KANOUSEI", "IkenHaikai",
                "HAIKAI_KANOUSEI_TAISHO_HOUSIN", words);
		// ���
	    addSickStateCheck(pd, data, "JOKUSOU_KANOUSEI", "IkenJokusou",
                "JOKUSOU_KANOUSEI_TAISHO_HOUSIN", words);
		// �������x��
	    addSickStateCheck(pd, data, "ENGESEIHAIEN", "IkenEngeseiHaien",
                "ENGESEIHAIEN_TAISHO_HOUSIN", words);
		// ����
	    addSickStateCheck(pd, data, "CHOUHEISOKU", "IkenChouheisoku",
                "CHOUHEISOKU_TAISHO_HOUSIN", words);
		// �Պ�����
	    addSickStateCheck(pd, data, "EKIKANKANSEN", "IkenEkikansensei",
                "EKIKANKANSEN_TAISHO_HOUSIN", words);
		// �S�x�@�\�̒ቺ
	    addSickStateCheck(pd, data, "SINPAIKINOUTEIKA", "IkenShinpaikinouTeika",
                "SINPAIKINOUTEIKA_TAISHO_HOUSIN", words);
		// �ɂ�
	    addSickStateCheck(pd, data, "ITAMI", "IkenItami",
                "ITAMI_TAISHO_HOUSIN", words);
		// �E��
	    addSickStateCheck(pd, data, "DASSUI", "IkenDassui",
                "DASSUI_TAISHO_HOUSIN", words);
		// ���̑�
	    if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "IkenByoutaita", 1)) {
	      // ���̑���
	      IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "IkenByoutaitaName");
	      addSickStateCheck(data, "BYOUTAITA_TAISHO_HOUSIN", words);
	    }
	    // �Ώ����j
	    StringBuffer sb;
	    if (words.size() > 0) {
	      //�a�ԑΏ����j
	      //�a�Ԃ𕶎��P�ʂŘA�����ĕ\���\�ȂƂ���܂ŁB

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
	        if ( (c != '�B') && (c != '�A')) {
	          line.append("�A");
//	          wordSize += 2;
	        }
	        wordSize += text.getBytes().length;

	        if (inlineSize + wordSize > MAX_LENGTH) {
	          //�󎚉\�ȂƂ���܂Œǉ�
	          int jEnd = line.length();
	          for (int j = 0; j < jEnd; j++) {
	            String str = line.substring(j, j + 1);
	            sb.append(str);
	            inlineSize += str.getBytes().length;
	            if (inlineSize > MAX_LENGTH) {
	              //�s�I���`�F�b�N
	              break;
	            }
	          }
	          break;
	        }
	        inlineSize += wordSize;
	        sb.append(line.toString());
	      }
	      if (inlineSize <= MAX_LENGTH) {
	        //�����ǉ�
	        String text = ACCastUtilities.toString(words.get(end));

	        StringBuffer line = new StringBuffer();
	        line.append(text);

	        int wordSize = 0;
	        char c = text.charAt(text.length() - 1);
	        if ( (c != '�B') && (c != '�A')) {
	          line.append("�B");
//	          wordSize += 2;
	        }
	        wordSize += text.getBytes().length;

	        if (inlineSize + wordSize > MAX_LENGTH) {
	          //�󎚉\�ȂƂ���܂Œǉ�
	          int jEnd = line.length();
	          for (int j = 0; j < jEnd; j++) {
	            String str = line.substring(j, j + 1);
	            sb.append(str);
	            inlineSize += str.getBytes().length;
	            if (inlineSize > MAX_LENGTH) {
	              //�s�I���`�F�b�N
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
	    
	    // ���T�[�r�X�i�z�[���w���v�T�[�r�X���j�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ���
		// �����ɂ���
		IkenshoCommon.addSelection(pd, data, "KETUATU", new String[] {
				"IkenKetsuatsuOff", "IkenKetsuatsuOn" }, -1,
				"KETUATU_RYUIJIKOU", "IkenKetsuatsuRyuijikou", 2);
		// �����ɂ���
		IkenshoCommon.addSelection(pd, data, "ENGE", new String[] {
				"IkenEngeOff", "IkenEngeOn" }, -1, "ENGE_RYUIJIKOU",
				"IkenEngeRyuijikou", 2);
		// �ېH�ɂ���
		IkenshoCommon.addSelection(pd, data, "SESHOKU", new String[] {
				"IkenSesshokuOff", "IkenSesshokuOn" }, -1, "SESHOKU_RYUIJIKOU",
				"IkenSesshokuRyuijikou", 2);
		// �ړ��ɂ���
		IkenshoCommon.addSelection(pd, data, "IDOU", new String[] {
				"IkenIdouOff", "IkenIdouOn" }, -1, "IDOU_RYUIJIKOU",
				"IkenIdouRyuijikou", 2);
		// ���̑�
		IkenshoCommon.addString(pd, "IkenKaigoOther", ACCastUtilities
				.toString(VRBindPathParser.get("KAIGO_OTHER", data)));
	    
	    // �����ǂ̗L���i�L�̏ꍇ�͋�̓I�ɋL�����Ă��������j
		IkenshoCommon.addSelection(pd, data, "KANSENSHOU", new String[] {
				"IkenKansenshouOn", "IkenKansenshouOff",
				"IkenKansenshouUnknown" }, -1, "KANSENSHOU_NM", "Grid16.h1.w5",
				1);

		// -----------------------------------------------------
		// ���̑����L���ׂ�����
		// -----------------------------------------------------
		// ���L����
		IkenshoCommon.addString(pd, "Grid17.h1.w1", ACCastUtilities
				.toString((VRBindPathParser.get("IKN_TOKKI", data))));

		// ���_��Q�̋@�\�]��
		boolean action = false;
		// ���_�Ǐ�E�\�͏�Q�j���]��
		// ���_�Ǐ�
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_NIJIKU_SEISHIN",
								data))), "NijikuSeishin", new String[] { "�O" });
		// �\�͏�Q
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_NIJIKU_NORYOKU",
								data))), "NijikuNoryoku", new String[] { "�O" });
		// ���莞��
		action |= setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_NIJIKU_DT", data)), "NijikuHanteiEra", new String[] {
				"NijikuHanteiYear", "NijikuHanteiMonth" }, new String[] {});

		// ������Q�]��
		// �H��
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_SEIKATSU_SHOKUJI",
								data))), "SeikatsuShokuji",
				new String[] { "�O" });
		// �������Y��
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_SEIKATSU_RHYTHM",
								data))), "SeikatsuRhythm", new String[] { "�O" });
		// �ې�
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get("SK_SEIKATSU_HOSEI",
								data))), "SeikatsuHosei", new String[] { "�O" });
		// ���K�Ǘ�
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_KINSEN_KANRI", data))),
				"SeikatsuKinsenKanri", new String[] { "�O" });
		// ����Ǘ�
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_HUKUYAKU_KANRI", data))),
				"SeikatsuFukuyakuKanri", new String[] { "�O" });
		// �ΐl�֌W
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_TAIJIN_KANKEI", data))),
				"SeikatsuTaijinKankei", new String[] { "�O" });
		// �Љ�I�K����W����s��
		action |= setAvoidedString(pd,
				convertHankakuNumToZenkakuNum(ACCastUtilities
						.toString(VRBindPathParser.get(
								"SK_SEIKATSU_SHAKAI_TEKIOU", data))),
				"SeikatsuShakaiTekiou", new String[] { "�O" });
		// ���f����
		action |= setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_SEIKATSU_DT", data)), "SeikatsuHanteiEra",
				new String[] { "SeikatsuHanteiYear", "SeikatsuHanteiMonth" },
				new String[] {});
		
		// �e���ڂɈ���󎚂���Ă��Ȃ��ꍇ�A���x�����\���ɐݒ肷��
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
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�   
        //�{�ݍݑ�敪
        switch(ACCastUtilities.toInt(VRBindPathParser.get("KIND", data) , 0)){
        case 1:
            //�ݑ�
            IkenshoCommon.addString(pd, "Label113", "�ݑ�");
            break;
        case 2:
            //�{��
            IkenshoCommon.addString(pd, "Label113", "�{��");
            break;
        default:
            //�{�ݍݑ�敪���B��
            pd.addAttribute("Label113", "Visible", "FALSE");
            break;
        }
        //[ID:0000515][Tozo TANAKA] 2009/09/16 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�   
		
		pd.endPageEdit();
	    
	}

	protected int getFormatType() {
		return IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
	}

	/**
	 * ����̕����񂾂����ꍇ�󎚂��Ȃ��֐�
	 * @param pd �������N���X
	 * @param text �Ώۂ̕�����
	 * @param target ������̈󎚐�
	 * @param avoid �󎚂��Ȃ�����
	 * @throws Exception ��O
	 * @return �󎚂����ꍇ��true���A�󎚂��Ȃ��������͋󕶎����󎚂����ꍇfalse��Ԃ�
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
	 * ����̕����񂾂����ꍇ�󎚂��Ȃ��֐�
	 * @return �󎚂��s�����ꍇ��true���A�󎚂��Ȃ��A�������͋󕶎��̈󎚂��s�����ꍇ��false��Ԃ�
	 * @param pd �������N���X
	 * @param data �f�[�^
	 * @param key ������̃o�C���h�p�X
	 * @param target ������̈󎚐�
	 * @param avoid �󎚂��Ȃ�����
	 * @throws Exception ��O
	 */
	protected static boolean setAvoidedString(ACChotarouXMLWriter pd,
			VRMap data, String key, String target, String[] avoid)
			throws Exception {
		return setAvoidedString(pd, ACCastUtilities.toString(VRBindPathParser
				.get(key, data)), target, avoid);
	}
	
	/**
	 * String�^�̓��t����͂��A�������֐�
	 * @param pd �������N���X
	 * @param text String�^�̓��t
	 * @param setEraTarget �����̐ݒ��
	 * @param setYMDTarget �N�����̐ݒ��
	 * @param deleteTarget �s�ڂ������͖����͂̏ꍇ�̍��Ώ�
	 * @throws Exception ��O
	 */
	protected static boolean setStringDate(ACChotarouXMLWriter pd, String text,
			String setEraTarget, String[] setYMDTarget, String[] deleteTarget)
			throws Exception {

		if(text == null || "".equals(text)){
			// �N���������A�I��
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
		if (text.startsWith("�s��")) {
			// ����
			IkenshoCommon.addString(pd, setEraTarget, "�s��");
			// �N���������
			if(deleteTarget != null){
				for (int i = 0; i < deleteTarget.length; i++) {
					IkenshoCommon.addString(pd, deleteTarget[i], "");
				}
			}
			// �󎚂������߁Atrue��Ԃ�
			return true;
		} else if (text.startsWith("0000")) {
			// �N���������
			if(deleteTarget != null){
				for (int i = 0; i < deleteTarget.length; i++) {
					IkenshoCommon.addString(pd, deleteTarget[i], "");
				}
			}
			// �󎚂��Ȃ������߁Afalse��Ԃ�
			return false;
		} else {
			switch (text.length()) {
			case 11:
				// �w���������N�������������x�̏ꍇ
				date = VRDateParser.parse(text.replaceAll("00��", "01��")
						.replaceAll("00��", "01��"));
				break;
			case 8:
				// �w���������N�������x�̏ꍇ
				date = VRDateParser
						.parse(text.replaceAll("00��", "01��") + "01��");
				break;
			case 5:
				// �w���������N�x�̏ꍇ
				date = VRDateParser.parse(text + "01��01��");
				break;
			default:
				// ��L�ȊO�̏ꍇ�A��͕s�\�Ƃ݂Ȃ�
				if(deleteTarget != null){
					for (int i = 0; i < deleteTarget.length; i++) {
						IkenshoCommon.addString(pd, deleteTarget[i], "");
					}
				}
				// �󎚂��Ȃ������߁Afalse��Ԃ�
				return false;
			}
			era = VRDateParser.format(date, "ggg");
			eYear = VRDateParser.format(date, "ee");
			// ����
			IkenshoCommon.addString(pd, setEraTarget, era);
			eraPrinted = true;
		}
		// }
		if (eraPrinted) {
			String val;
			text = text.replaceAll("00", SPACER);
			switch (setYMDTarget.length) {
			case 3:
				// ��
				val = text.substring(8, 10);
				if (!SPACER.equals(val)) {
					IkenshoCommon.addString(pd, setYMDTarget[2], val);
				}
			case 2:
				// ��
				val = text.substring(5, 7);
				if (!SPACER.equals(val)) {
					IkenshoCommon.addString(pd, setYMDTarget[1], val);
				}
			case 1:
				// �N
				IkenshoCommon.addString(pd, setYMDTarget[0], eYear);
			}
		}
		
		return true;

	}
	
	/**
	 * �`�F�b�N�{�b�N�X�{���W�I�𐧌䂷��֐�
	 * ��j���Ԑ� ���E�i���x�F���y ���� ���d�j
	 * @param pd �������N���X
	 * @param data �f�[�^
	 * @param parentKey �e�̃o�C���h�p�X
	 * @param parentTarget �e�̏o�͐�
	 * @param chiledKey �q�̃o�C���h�p�X
	 * @param childrenTarget �q�̏o�͐�
	 * @throws Exception ��O
	 */
	protected static boolean setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget) throws Exception {

		return setSynchronizedChecks(pd, data, parentKey, parentTarget, childKey, childrenTarget, 1, -1);
		
	}
		
	/**
	 * �`�F�b�N�{�b�N�X�{���W�I�𐧌䂷��֐�
	 * ��j���Ԑ� ���E�i���x�F���y ���� ���d�j
	 * @param pd �������N���X
	 * @param data �f�[�^
	 * @param parentKey �e�̃o�C���h�p�X
	 * @param parentTarget �e�̏o�͐�
	 * @param chiledKey �q�̃o�C���h�p�X
	 * @param childrenTarget �q�̏o�͐�
	 * @param checkValue �e���`�F�b�N�ƌ��Ȃ��l
	 * @param offSet �q�̃I�t�Z�b�g
	 * @throws Exception ��O
	 */
	protected static boolean setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget, int checkValue, int offset) throws Exception {

		if(IkenshoCommon.addCheck(pd, data, parentKey, parentTarget, checkValue)){
			// �e�̃`�F�b�N��ON�̏ꍇ
			IkenshoCommon.addSelection(pd, data, childKey, childrenTarget, offset);
			return true;
		}else{
			// �e�̃`�F�b�N��OFF�̏ꍇ
			setVisibleOffAll(pd, childrenTarget);
			return false;
		}
		
	}

	/**
	 * �`�F�b�N�{�b�N�X�{�e�L�X�g�{���W�I�𐧌䂷��֐�
	 * ��j��� �����̑��i���ʁF�������� ���x�F���y ���� ���d�j
	 * @param pd �������N���X
	 * @param data �f�[�^
	 * @param parentKey �e�̃o�C���h�p�X
	 * @param parentTarget �e�̏o�͐�
	 * @param childKey �q�̃o�C���h�p�X
	 * @param childrenTarget �q�̏o�͐�
	 * @param textKey ������̃o�C���h�p�X
	 * @param textTarget ������̏o�͐�
	 * @throws Exception ��O
	 */
	protected static boolean setSynchronizedChecksWithText(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget, String textKey, String textTarget)
			throws Exception {

		return setSynchronizedChecksWithText(pd, data, parentKey, parentTarget,
				childKey, childrenTarget, textKey, textTarget, 1, -1);

	}

	/**
	 * �`�F�b�N�{�b�N�X�{�e�L�X�g�{���W�I�𐧌䂷��֐�
	 * ��j��� �����̑��i���ʁF�������� ���x�F���y ���� ���d�j
	 * @param pd �������N���X
	 * @param data �f�[�^
	 * @param parentKey �e�̃o�C���h�p�X
	 * @param parentTarget �e�̏o�͐�
	 * @param childKey �q�̃o�C���h�p�X
	 * @param childrenTarget �q�̏o�͐�
	 * @param textKey ������̃o�C���h�p�X
	 * @param textTarget ������̏o�͐�
	 * @param checkValue �e���`�F�b�N�ƌ��Ȃ��l
	 * @param offSet �q�̃I�t�Z�b�g
	 * @throws Exception ��O
	 */
	protected static boolean setSynchronizedChecksWithText(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String parentTarget, String childKey,
			String[] childrenTarget, String textKey, String textTarget, int checkValue,
			int offset) throws Exception {

		if(IkenshoCommon.addCheck(pd, data, parentKey, parentTarget, checkValue)){
			// �e�̃`�F�b�N��ON�̏ꍇ
			// ������
			IkenshoCommon.addString(pd, textTarget, ACCastUtilities
					.toString(VRBindPathParser.get(textKey, data)));
			// �`�F�b�N
			IkenshoCommon.addSelection(pd, data, childKey, childrenTarget, offset);
			return true;
		}else{
			// �e�̃`�F�b�N��OFF�̏ꍇ
			setVisibleOffAll(pd, childrenTarget);
			return false;
		}
		
	}

	/**
	 * �n���ꂽ���ڂ��\���ɂ���֐�
	 * @param pd �������N���X
	 * @param target �Ώۍ���
	 * @throws Exception ��O
	 */
	protected static void setVisibleOffAll(ACChotarouXMLWriter pd, String[] target)
			throws Exception {
		
		for(int i = 0; i < target.length; i++){
			pd.addAttribute(target[i], "Visible", "FALSE");
		}
		
	}
	
	/**
	 * ���p������S�p�����ɕϊ�����֐�
	 * @param text �ϊ����̕�����
	 * @return �ϊ��ς̕�����
	 */
	public static String convertHankakuNumToZenkakuNum(String text) {
		if(text == null){
			return null;
		}
	    StringBuffer sb = new StringBuffer(text);
	    for (int i = 0; i < text.length(); i++) {
	      char c = text.charAt(i);
	      if (c >= '0' && c <= '9') {
	        sb.setCharAt(i, (char) (c - '0' + '�O'));
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
	
//	// TODO �f�o�b�O�p�@��ɍ폜�\��
//
//    /**
//     * ����������s���܂��B
//     * 
//     * @return �I�����Ă悢��
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

    //[ID:0000515][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 
    protected void addPrintOptionGroup(){
        getPrintOptionGroup().add(getDoctorNameGroup(), VRLayout.CLIENT);
        getPrintOptionGroup().add(getSecondHeaderGroup(), VRLayout.CLIENT);
        getPrintOptionGroup().add(getPageHeaderGroup(), VRLayout.CLIENT);
        getPrintOptionGroup().add(getPatientCareStateGroup(), VRLayout.SOUTH);
    }
    
    protected void setPackSize(){
        setSize(new Dimension(700, 420));
    }
    //[ID:0000515][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 

}
