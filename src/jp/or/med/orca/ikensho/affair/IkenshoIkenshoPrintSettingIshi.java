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
							"Grid6.h4.w4");
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
							"Grid6.h4.w4");
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

// [ID:0000792][Satoshi Tokusari] 2015/11 add-Start �Ǐ�̈��萫�̈󎚌������Ή�
        switch(ACCastUtilities.toInt(VRBindPathParser.get("SHJ_ANT", data) , 0)) {
        case 1:
            // ����
            IkenshoCommon.addString(pd, "ShojoFuanteiJokyo", "����");
            break;
        case 2:
            // �s����
            break;
        case 3:
            // �s��
            IkenshoCommon.addString(pd, "ShojoFuanteiJokyo", "�s��");
            break;
        }
// [ID:0000792][Satoshi Tokusari] 2015/11 add-End

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
        
        // ��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߏo��
        ACChotarouXMLUtilities.setValue(pd, "ShobyoKeika", sbSickProgress.toString());
        
        
        
        
        
        // -----------------------------------------------------
        // 2.�g�̂̏�ԂɊւ���ӌ�
        // -----------------------------------------------------
	    // �g���܂킵�ϐ�
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
        
        
		// �����r
		IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {
				"KikiudeRight", "KikiudeLeft" }, -1);
		// �g��
		IkenshoCommon.addString(pd, data, "HEIGHT", "PatientHeight");
		// �̏d
		IkenshoCommon.addString(pd, data, "WEIGHT", "PatientWeight");

		// �̏d�̕ω�
		IkenshoCommon.addSelection(pd, data, "WEIGHT_CHANGE", new String[] {
				"WeightChangeZouka", "WeightChangeIdi", "WeightChangeGensho" },
				-1);

		// �l������
		if (isSelected(data, "SISIKESSON")) {
			IkenshoCommon.addString(pd, data, "SISIKESSON_BUI", "ShishikessonBui");
		}

	    // ���
	    ary = new String[] { "MahiLeftArmTeidoKei", "MahiLeftArmTeidoChu", "MahiLeftArmTeidoJu" };
		ary2 = new String[] { "MahiLowerLeftLimbTeidoKei", "MahiLowerLeftLimbTeidoChu", "MahiLowerLeftLimbTeidoJu" };
		ary3 = new String[] { "MahiRightArmTeidoKei", "MahiRightArmTeidoChu", "MahiRightArmTeidoJu" };
		ary4 = new String[] { "MahiLowerRightLimbTeidoKei", "MahiLowerRightLimbTeidoChu", "MahiLowerRightLimbTeidoJu" };
		ary5 = new String[] { "MahiOtherTeidoKei", "MahiOtherTeidoChu", "MahiOtherTeidoJu" };
	    
		if (isSelected(data, "MAHI_FLAG")) {
			// ���㎈
			setSynchronizedChecks(pd, data, "MAHI_LEFTARM", "MAHI_LEFTARM_TEIDO", ary);
			// ������
			setSynchronizedChecks(pd, data, "MAHI_LOWERLEFTLIMB", "MAHI_LOWERLEFTLIMB_TEIDO", ary2);
			// �E�㎈
	    	setSynchronizedChecks(pd, data, "MAHI_RIGHTARM", "MAHI_RIGHTARM_TEIDO", ary3);
			// �E����
	    	setSynchronizedChecks(pd, data, "MAHI_LOWERRIGHTLIMB", "MAHI_LOWERRIGHTLIMB_TEIDO", ary4);
	    	// ���̑�
	    	setSynchronizedChecksWithText(pd, data, "MAHI_ETC", "MAHI_ETC_TEIDO", ary5, "MAHI_ETC_BUI", "MahiOtherBui");
	    	
	    }else{
	    	// ���OFF
	    	// �S�Ẵ`�F�b�N��OFF�ɂ���
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    }

	    // �ؗ͂̒ቺ
		ary = new String[] {"KinryokuTeikaTeidoKei", "KinryokuTeikaTeidoChu", "KinryokuTeikaTeidoJu"};
	    isCheck = setSynchronizedChecksWithText(pd, data, "KINRYOKU_TEIKA", "KINRYOKU_TEIKA_TEIDO",
	    											ary, "KINRYOKU_TEIKA_BUI", "KinryokuTeikaBui");
	    
	    // �ؗ͂̒ቺ�ϓ�
	    ary = new String[]{"KinryokuTeikaHendoUp" ,"KinryokuTeikaHendoKeep", "KinryokuTeikaHendoDown"};
	    if (isCheck) {
	    	setSynchronizedChecks(pd, data, "KINRYOKU_TEIKA_CHANGE", ary);
	    } else {
	    	setVisibleOffAll(pd, ary);
	    }
	    
	    // �֐߂̍S�k
	    // �z���`
	    // ���֐߉E
	    ary = new String[] { "KataKoushukuRightTeidoKei", "KataKoushukuRightTeidoChu", "KataKoushukuRightTeidoJu" };
	    // ���֐ߍ�
		ary2 = new String[] { "KataKoushukuLeftTeidoKei", "KataKoushukuLeftTeidoChu", "KataKoushukuLeftTeidoJu" };
	    // �Ҋ֐߉E
		ary3 = new String[] { "MataKoushukuRightTeidoKei", "MataKoushukuRightTeidoChu", "MataKoushukuRightTeidoJu" };
	    // �Ҋ֐ߍ�
		ary4 = new String[] { "MataKoushukuLeftTeidoKei", "MataKoushukuLeftTeidoChu", "MataKoushukuLeftTeidoJu" };
	    // �I�֐߉E
		ary5 = new String[] { "HijiKoushukuRightTeidoKei", "HijiKoushukuRightTeidoChu", "HijiKoushukuRightTeidoJu" };
	    // �I�֐ߍ�
		ary6 = new String[] { "HijiKoushukuLeftTeidoKei", "HijiKoushukuLeftTeidoChu", "HijiKoushukuLeftTeidoJu" };
	    // �G�֐߉E
		ary7 = new String[] { "HizaKoushukuRightTeidoKei", "HizaKoushukuRightTeidoChu", "HizaKoushukuRightTeidoJu" };
	    // �G�֐ߍ�
		ary8 = new String[] { "HizaKoushukuLeftTeidoKei", "HizaKoushukuLeftTeidoChu", "HizaKoushukuLeftTeidoJu" };
		// �֐߂��̑����x
		ary9 = new String[]{"SonotaKoushukuTeidoKei", "SonotaKoushukuTeidoChu", "SonotaKoushukuTeidoJu"};
		
		
		// �֐߂̍S�kON
		if (isSelected(data, "KOUSHU")) {
			
			// ���֐�ON
	    	if (isSelected(data, "KATA_KOUSHU")) {
				// ���֐�ON
	    		boolean actionChild = false;
				// ���֐߉E
				setSynchronizedChecks(pd, data, "KATA_KOUSHU_MIGI", "KATA_KOUSHU_MIGI_TEIDO", ary);
				// ���֐ߍ�
				setSynchronizedChecks(pd, data, "KATA_KOUSHU_HIDARI", "KATA_KOUSHU_HIDARI_TEIDO", ary2);
				
			} else {
				// ���֐�OFF
				setVisibleOffAll(pd, ary);
				setVisibleOffAll(pd, ary2);
			}
	    	
	    	// �I�֐�ON
			if (isSelected(data, "HIJI_KOUSHU")) {
				// �I�֐߉E
				setSynchronizedChecks(pd, data, "HIJI_KOUSHU_MIGI", "HIJI_KOUSHU_MIGI_TEIDO", ary5);
				// �I�֐ߍ�
				setSynchronizedChecks(pd, data, "HIJI_KOUSHU_HIDARI", "HIJI_KOUSHU_HIDARI_TEIDO", ary6);
				
			} else {
				// �I�֐�OFF
				setVisibleOffAll(pd, ary5);
				setVisibleOffAll(pd, ary6);
			}
	    	
			// �Ҋ֐�ON
			if (isSelected(data, "MATA_KOUSHU")) {
				// �Ҋ֐߉E
				setSynchronizedChecks(pd, data, "MATA_KOUSHU_MIGI", "MATA_KOUSHU_MIGI_TEIDO", ary3);
				// �Ҋ֐ߍ�
				setSynchronizedChecks(pd, data, "MATA_KOUSHU_HIDARI", "MATA_KOUSHU_HIDARI_TEIDO", ary4);
				
			} else {
				// �Ҋ֐�OFF
				setVisibleOffAll(pd, ary3);
				setVisibleOffAll(pd, ary4);
			}

			// �G�֐�ON
			if (isSelected(data, "HIZA_KOUSHU")) {
				// �G�֐߉E
				setSynchronizedChecks(pd, data, "HIZA_KOUSHU_MIGI", "HIZA_KOUSHU_MIGI_TEIDO", ary7);
				// �G�֐ߍ�
				setSynchronizedChecks(pd, data, "HIZA_KOUSHU_HIDARI", "HIZA_KOUSHU_HIDARI_TEIDO", ary8);
				
			} else {
				// �G�֐�OFF
				setVisibleOffAll(pd, ary7);
				setVisibleOffAll(pd, ary8);
			}
			
			// ���̑�����
			setSynchronizedChecksWithText(pd, data, "KOUSHU_ETC", "KOUSHU_ETC_BUI_TEIDO",
											ary9, "KOUSHU_ETC_BUI", "KoushukuOtherBui");
			
	    }else{
	    	// �֐߂̍S�kOFF
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
	    
	    // �֐߂̒ɂ�
		ary = new String[]{"KansetsuItamiTeidokei", "KansetsuItamiTeidoChu","KansetsuItamiTeidoJu"};
	    isCheck = setSynchronizedChecksWithText(pd, data, "KANSETU_ITAMI", "KANSETU_ITAMI_TEIDO",
	    											ary, "KANSETU_ITAMI_BUI", "KansetsuItamiBui");
	    
	    // �֐߂̒ɂ� �ϓ�
	    ary = new String[]{"KansetsuItamiHendoUp" ,"KansetsuItamiKeep", "KansetsuItamiDown"};
	    if (isCheck) {
	    	setSynchronizedChecks(pd, data, "KANSETU_ITAMI_CHANGE", ary);
	    } else {
	    	setVisibleOffAll(pd, ary);
	    }
	    
	    
	    // �����E�s���Ӊ^��
	    // �z���`
		// �㎈�E���x
		ary = new String[] { "JoushiShicchoRightTeidoKei", "JoushiShicchoRightTeidoChu", "JoushiShicchoRightTeidoJu" };
		// �㎈�����x
		ary2 = new String[] { "JoushiShicchoLeftTeidoKei", "JoushiShicchoLeftTeidoChu", "JoushiShicchoLeftTeidoJu" };
		// �̊��E���x
		ary3 = new String[] { "TaikanShicchoRightTeidoKei", "TaikanShicchoRightTeidoChu", "TaikanShicchoRightTeidoJu" };
		// �̊������x
		ary4 = new String[] { "TaikanShicchoLeftTeidoKei", "TaikanShicchoLeftTeidoChu", "TaikanShicchoLeftTeidoJu" };
		// �����E���x
		ary5 = new String[] { "KashiShicchoRightTeidoKei", "KashiShicchoRightTeidoChu", "KashiShicchoRightTeidoJu" };
		// ���������x
		ary6 = new String[] { "KashiShicchoLeftTeidoKei", "KashiShicchoLeftTeidoChu", "KashiShicchoLeftTeidoJu" };
		
		// �����E�s���Ӊ^��ON
	    if(isSelected(data, "SICCHOU_FLAG")){
			// �㎈�E
			setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_MIGI", "JOUSI_SICCHOU_MIGI_TEIDO", ary);
			// �㎈��
			setSynchronizedChecks(pd, data, "JOUSI_SICCHOU_HIDARI", "JOUSI_SICCHOU_HIDARI_TEIDO", ary2);
			// �̊��E
			setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_MIGI", "TAIKAN_SICCHOU_MIGI_TEIDO", ary3);
			// �̊���
			setSynchronizedChecks(pd, data, "TAIKAN_SICCHOU_HIDARI", "TAIKAN_SICCHOU_HIDARI_TEIDO", ary4);
			// �����E
			setSynchronizedChecks(pd, data, "KASI_SICCHOU_MIGI", "KASI_SICCHOU_MIGI_TEIDO", ary5);
			// ������
			setSynchronizedChecks(pd, data, "KASI_SICCHOU_HIDARI", "KASI_SICCHOU_HIDARI_TEIDO", ary6);
			
	    }else{
	    	// �����E�s���Ӊ^��OFF
	    	setVisibleOffAll(pd, ary);
	    	setVisibleOffAll(pd, ary2);
	    	setVisibleOffAll(pd, ary3);
	    	setVisibleOffAll(pd, ary4);
	    	setVisibleOffAll(pd, ary5);
	    	setVisibleOffAll(pd, ary6);
	    }
	    
	    // ���
	    ary = new String[]{"JokusouTeidoKei", "JokusouTeidoChu", "JokusouTeidoJu"};
		setSynchronizedChecksWithText(pd, data, "JOKUSOU", "Jokusou", "JOKUSOU_TEIDO",
				ary, "JOKUSOU_BUI", "JokusouBui");

		// ���̑��̔畆����
		ary = new String[]{"HifuShikkanTeidoKei", "HifuShikkanTeidoChu", "HifuShikkanTeidoJu"};
		setSynchronizedChecksWithText(pd, data, "HIFUSIKKAN", "HifuShikkan", "HIFUSIKKAN_TEIDO",
				ary, "HIFUSIKKAN_BUI", "HifuShikkanBui");
		

		pd.endPageEdit();

	}

	public static void printIkensho2(ACChotarouXMLWriter pd, String formatName,
			VRMap data, Date printDate, byte[] imageBytes) throws Exception {

	    pd.beginPageEdit(formatName);

	    // �g���܂킵�ϐ�
	    String[] ary = null;
	    
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
        
        
		// -----------------------------------------------------
		// 3.�s���y�ѐ��_���̏�ԂɊւ���ӌ�
		// -----------------------------------------------------
		// (1) �s����̏�Q�̗L��
		if (isSelected(data, "MONDAI_FLAG")) {
			
			// ����t�]
			IkenshoCommon.addCheck(pd, data, "KS_CHUYA", "KodoShogaiTyuya", 1);
			// �\��
			IkenshoCommon.addCheck(pd, data, "KS_BOUGEN", "KodoShogaiBogen", 1);
			// ����
			IkenshoCommon.addCheck(pd, data, "KS_JISYOU", "KodoShogaiJisyo", 1);
			// ���Q
			IkenshoCommon.addCheck(pd, data, "KS_BOUKOU", "KodoShogaiTagai", 1);
			// ��R
			IkenshoCommon.addCheck(pd, data, "KS_TEIKOU", "KodoShogaiTeikou", 1);
			// �p�j
			IkenshoCommon.addCheck(pd, data, "KS_HAIKAI", "KodoShogaiHaikai", 1);
			// �댯�̔F��������(�΂̕s�n��)
			IkenshoCommon.addCheck(pd, data, "KS_FUSIMATU", "KodoShogaiKiken", 1);
			// �s��
			IkenshoCommon.addCheck(pd, data, "KS_FUKETU", "KodoShogaiFuketsu", 1);
			// �ِH�s��
			IkenshoCommon.addCheck(pd, data, "KS_ISHOKU", "KodoShogaiIshoku", 1);
			// ���I��E�s��
			IkenshoCommon.addCheck(pd, data, "KS_SEITEKI_MONDAI", "KodoShogaiSeiteki", 1);
			// ���̑�
			if (IkenshoCommon.addCheck(pd, data, "KS_OTHER", "KodoShogaiOther", 1)) {
				// ���̑�����
				IkenshoCommon.addString(pd, data, "KS_OTHER_NM", "Grid14.h3.w9");
			}

		} else {
			// �S�Ẵ`�F�b�N���O��
			pd.addAttribute("KodoShogaiTyuya", "Visible", "FALSE"); //����t�]
			pd.addAttribute("KodoShogaiBogen", "Visible", "FALSE"); //�\��
			pd.addAttribute("KodoShogaiJisyo", "Visible", "FALSE"); //����
			pd.addAttribute("KodoShogaiTagai", "Visible", "FALSE"); //���Q
			pd.addAttribute("KodoShogaiTeikou", "Visible", "FALSE"); //��R
			pd.addAttribute("KodoShogaiHaikai", "Visible", "FALSE"); //�p�j
			pd.addAttribute("KodoShogaiKiken", "Visible", "FALSE"); //�댯�̔F��������
			pd.addAttribute("KodoShogaiFuketsu", "Visible", "FALSE"); //�s��
			pd.addAttribute("KodoShogaiIshoku", "Visible", "FALSE"); //�ِH�s��
			pd.addAttribute("KodoShogaiSeiteki", "Visible", "FALSE"); //���I��E�s��
			pd.addAttribute("KodoShogaiOther", "Visible", "FALSE"); //���̑�
			pd.addAttribute("KodoShogaiOn", "Visible", "FALSE");
		}
        
        
        //  (2) ���_�Ǐ�E�\�͏�Q�񎲕]��
		// ���_�Ǐ�]��
		setSynchronizedChecks(pd, data, "SK_NIJIKU_SEISHIN", "NijikuSeishin", 6);
		// �\�͏�Q�]��
		setSynchronizedChecks(pd, data, "SK_NIJIKU_NORYOKU", "NijikuNouryoku", 5);
		// ���莞��
		setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_NIJIKU_DT", data)), "NijikuHanteiEra", new String[] {
				"NijikuHanteiYear", "NijikuHanteiMonth" }, new String[] {});
		

		// (3) ������Q�]��
		// �H��
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_SHOKUJI", "SeikatsuSyokuji", 5);
		// �������Y��
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_RHYTHM", "SeikatsuRhythm", 5);
		// �ې�
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_HOSEI", "SeikatsuHosei", 5);
		// ���K�Ǘ�
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_KINSEN_KANRI", "SeikatsuKinsen", 5);
		// ����Ǘ�
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_HUKUYAKU_KANRI", "SeikatsuFukuyaku", 5);
		// �ΐl�֌W
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_TAIJIN_KANKEI", "SeikatsuTaijin", 5);
		// �Љ�I�K����W����s��
		setSynchronizedChecks(pd, data, "SK_SEIKATSU_SHAKAI_TEKIOU", "SeikatsuTekiou", 5);
		
		// ���f����
		setStringDate(pd, ACCastUtilities.toString(VRBindPathParser
				.get("SK_SEIKATSU_DT", data)), "SeikatsuHanteiEra",
				new String[] { "SeikatsuHanteiYear", "SeikatsuHanteiMonth" },
				new String[] {});
		
		
		
		// (4) ���_�E�_�o�Ǐ�̗L��
		switch (ACCastUtilities.toInt(VRBindPathParser.get("SEISIN", data), 0)) {
		case 1:
			// �L�̏ꍇ
			//IkenshoCommon.addString(pd, data, "SEISIN_NM", "SeishinShinkeiName");
			//pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");

			// �Ǐ󍀖�
			// �ӎ���Q
			IkenshoCommon.addCheck(pd, data, "SS_ISHIKI_SHOGAI", "SeishinShinkeiIshikiSyogai", 1);
			//�L����Q
			IkenshoCommon.addCheck(pd, data, "SS_KIOKU_SHOGAI", "SeishinShinkeiKiokuShogai", 1);
			// ���ӏ�Q
			IkenshoCommon.addCheck(pd, data, "SS_CHUI_SHOGAI", "SeishinShinkeiChuiShogai", 1);
			// ���s�@�\��Q
			IkenshoCommon.addCheck(pd, data, "SS_SUIKOU_KINO_SHOGAI", "SeishinShinkeiSuikouKinoShogai", 1);
			// �Љ�I�s����Q
			IkenshoCommon.addCheck(pd, data, "SS_SHAKAITEKI_KODO_SHOGAI", "SeishinShinkeiShakaitekiKodoShogai", 1);
			// ���̑��̔F�m��Q
			IkenshoCommon.addCheck(pd, data, "SS_NINCHI_SHOGAI", "SeishinShinkeiNinchiShogai", 1);
			// �C����Q
			IkenshoCommon.addCheck(pd, data, "SS_KIBUN_SHOGAI", "SeishinShinkeiKibunSyogai", 1);
			// ������Q
			IkenshoCommon.addCheck(pd, data, "SS_SUIMIN_SHOGAI", "SeishinShinkeiSuiminSyogai", 1);
			// ���o
			IkenshoCommon.addCheck(pd, data, "SS_GNS_GNC", "SeishinShinkeiGenkaku", 1);
			// �ϑz
			IkenshoCommon.addCheck(pd, data, "SS_MOUSOU", "SeishinShinkeiMousou", 1);

			// ���̑�
			if (IkenshoCommon.addCheck(pd, data, "SS_OTHER",
					"SeishinShinkeiOther", 1)) {
				// ���̑�����
				IkenshoCommon.addString(pd, data, "SS_OTHER_NM",
						"SeishinShinkeiOtherName");
			}

// [ID:0000790][Satoshi Tokusari] 2014/10 del-Start ��t�ӌ����̐��Ȏ�f�̗L���̎d�l�ύX�Ή�
//			// �����f�̗L��
//			if (IkenshoCommon
//					.addSelection(pd, data, "SENMONI", new String[] {
//							"SeishinShinkeiSenmoniOn",
//							"SeishinShinkeiSenmoniOff" }, -1)) {
//				if ("1".equals(ACCastUtilities.toString(data.getData("SENMONI")))) {
//					// ����
//					IkenshoCommon.addString(pd, data, "SENMONI_NM",
//							"SeishinShinkeiSenmoniName");
//				}
//			}
// [ID:0000790][Satoshi Tokusari] 2014/10 del-End

			break;
		default:
			pd.addAttribute("SeishinShinkeiOff", "Visible", "FALSE");
		case 2:
			// ���̏ꍇ
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
// [ID:0000790][Satoshi Tokusari] 2014/10 del-Start ��t�ӌ����̐��Ȏ�f�̗L���̎d�l�ύX�Ή�
//			pd.addAttribute("SeishinShinkeiSenmoniOn", "Visible", "FALSE");
//			pd.addAttribute("SeishinShinkeiSenmoniOff", "Visible", "FALSE");
// [ID:0000790][Satoshi Tokusari] 2014/10 del-End
			break;
		}

// [ID:0000790][Satoshi Tokusari] 2014/10 add-Start ��t�ӌ����̐��Ȏ�f�̗L���̎d�l�ύX�Ή�
		// ���Ȏ�f�̗L��
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
// [ID:0000790][Satoshi Tokusari] 2014/10 add-End
        
	    // (5) �Ă񂩂�
	    ary = new String[] { "TenkanHindoShuichi", "TenkanHindoTsukiichi",
				"TenkanHindoNenichi"}; 
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
	    
	    
	    
	    
	    
		// -----------------------------------------------------
		// 4.���ʂȈ��
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
		
		// �ԟ[�I���A
		IkenshoCommon.addCheck(pd, data, "KANKETSUTEKI_DOUNYOU", "SochiDounyo", 1);
		
		
		// ���j�^�[����
		IkenshoCommon
				.addCheck(pd, data, "MONITOR", "TokubetsuShochiMonitor", 1);
		// ��ጂ̏��u
		IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI",
				"TokubetsuShochiJokuso", 1);
		// �J�e�[�e��
		IkenshoCommon.addCheck(pd, data, "CATHETER", "ShikkinShochi", 1);
	    
	    
	    
		// -----------------------------------------------------
		// 5.�T�[�r�X���p�Ɋւ���ӌ�
		// -----------------------------------------------------	    
	    // (1) ���݁A�����̉\���������a�ԂƂ��̑Ώ����j
		boolean checkByotai = false;
		// �A����
		checkByotai |= IkenshoCommon.addCheck(pd, data, "NYOUSIKKIN", "IkenNyoushikkin", 1);
	    // �]�|�E����
		checkByotai |= IkenshoCommon.addCheck(pd, data, "TENTOU_KOSSETU", "IkenTentouKossetsu", 1);
	    // �p�j
		checkByotai |= IkenshoCommon.addCheck(pd, data, "HAIKAI_KANOUSEI", "IkenHaikai", 1);
	    // ���
		checkByotai |= IkenshoCommon.addCheck(pd, data, "JOKUSOU_KANOUSEI", "IkenJokusou", 1);
	    // �������x��
		checkByotai |= IkenshoCommon.addCheck(pd, data, "ENGESEIHAIEN", "IkenEngeseiHaien", 1);
	    // ����
		checkByotai |= IkenshoCommon.addCheck(pd, data, "CHOUHEISOKU", "IkenChouheisoku", 1);
	    // �Պ�����
		checkByotai |= IkenshoCommon.addCheck(pd, data, "EKIKANKANSEN", "IkenEkikansensei", 1);
	    // �S�x�@�\�̒ቺ
		checkByotai |= IkenshoCommon.addCheck(pd, data, "SINPAIKINOUTEIKA", "IkenShinpaikinouTeika", 1);
	    // �u��
		checkByotai |= IkenshoCommon.addCheck(pd, data, "ITAMI", "IkenToutsu", 1);
	    // �E��
		checkByotai |= IkenshoCommon.addCheck(pd, data, "DASSUI", "IkenDassui", 1);
	    // �s����Q
		checkByotai |= IkenshoCommon.addCheck(pd, data, "KOUDO_SHOGAI", "IkenKoudo", 1);
	    // ���_��Ԃ̑���
		checkByotai |= IkenshoCommon.addCheck(pd, data, "SEISIN_ZOAKU", "IkenSeishin", 1);
	    // ������񔭍�
		checkByotai |= IkenshoCommon.addCheck(pd, data, "KEIREN_HOSSA", "IkenKeiren", 1);
	    
		// ���̑�
	    if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "IkenByoutaita", 1)) {
	      // ���̑���
	      IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "IkenByoutaitaName");
	      checkByotai = true;
	    }
	    // �Ώ����j
	    if (checkByotai) {
	    	IkenshoCommon.addString(pd, "Grid10.h1.w2", ACCastUtilities.toString(VRBindPathParser.get("TAISHO_HOUSIN", data)));
	    }
	    
	    
	    // ���T�[�r�X�i�z�[���w���v�T�[�r�X���j�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ���
		// �����ɂ���
	    setStringIfChecked(pd, data, "KETUATU", "KETUATU_RYUIJIKOU", "IkenKetsuatsuRyuijikou");
		// �����ɂ���
	    setStringIfChecked(pd, data, "ENGE", "ENGE_RYUIJIKOU", "IkenEngeRyuijikou");
		// �ېH�ɂ���
	    setStringIfChecked(pd, data, "SESHOKU", "SESHOKU_RYUIJIKOU", "IkenSesshokuRyuijikou");
		// �ړ��ɂ���
	    setStringIfChecked(pd, data, "IDOU", "IDOU_RYUIJIKOU", "IkenIdouRyuijikou");
		// �s����Q�ɂ���
	    setStringIfChecked(pd, data, "SFS_KOUDO", "SFS_KOUDO_RYUIJIKOU", "IkenKoudoRyuijikou");
		// ���_�Ǐ�ɂ���
	    setStringIfChecked(pd, data, "SFS_SEISIN", "SFS_SEISIN_RYUIJIKOU", "IkenSeishinRyuijikou");
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
	
	// ���ڂ��I������Ă��邩
	private static boolean isSelected(VRMap data, String key) throws Exception {
		if(ACCastUtilities.toInt(VRBindPathParser.get(key, data), 0) == 1){
			return true;
		}
		return false;
	}
	
	// �e�̃`�F�b�N�ƘA�����āA���x�̃`�F�b�N��ݒ肷��
	protected static boolean setSynchronizedChecks(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String childKey, String[] childrenTarget) throws Exception {
		
		// �e�̃`�F�b�N��ON�̏ꍇ
		if (isSelected(data, parentKey)) {
			IkenshoCommon.addSelection(pd, data, childKey, childrenTarget, -1);
			return true;
		} else {
			// �e�̃`�F�b�N��OFF�̏ꍇ
			setVisibleOffAll(pd, childrenTarget);
			return false;
		}
		
	}
	
	// �e�̃`�F�b�N�ƘA�����āA���x�̃`�F�b�N��ݒ肵���l���ݒ肷��
	protected static boolean setSynchronizedChecksWithText(ACChotarouXMLWriter pd,
			VRMap data, String parentKey, String childKey,
			String[] childrenTarget, String textKey, String textTarget) throws Exception {
		
		boolean select = setSynchronizedChecks(pd, data, parentKey, childKey, childrenTarget);
		// �e�̃`�F�b�N��ON�̏ꍇ
		if (select) {
			// ������
			IkenshoCommon.addString(pd, textTarget, ACCastUtilities.toString(VRBindPathParser.get(textKey, data)));
		}
		
		return select;
		
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
	 * ���_�Ǐ�E�\�͏�Q�񎲒��ߓ��A���l�̃`�F�b�N�p
	 * @param pd
	 * @param data
	 * @param key Map����l�����o���L�[
	 * @param chtTargetName ��`�̏�̍��ږ�(�A�ԏ���)
	 * @param length 1�`length�܂ō��ڂ�����
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
        return "IkenshoShien1FromH2604.xml";
    }

    protected String getIkenshoFormatFilePath2() {
        return "IkenshoShien2FromH2604.xml";
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
