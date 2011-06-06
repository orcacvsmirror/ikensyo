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
   * �R���X�g���N�^�ł��B
   * @param data ����f�[�^
   * @param picture �S�g�}
   * @throws HeadlessException ��������O
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

    //�ӂ肪��
    IkenshoCommon.addString(pd, data, "PATIENT_KN", "Grid1.h1.w3");
    //����
    IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid1.h2.w3");

    //���N����
    Date date = (Date)VRBindPathParser.get("BIRTHDAY", data);
    String era = VRDateParser.format(date, "gg");
    if ("��".equals(era)) {
      pd.addAttribute("Shape2", "Visible", "FALSE");
      pd.addAttribute("Shape3", "Visible", "FALSE");
    }
    else if ("��".equals(era)) {
      pd.addAttribute("Shape1", "Visible", "FALSE");
      pd.addAttribute("Shape3", "Visible", "FALSE");
    }
    else if ("��".equals(era)) {
      pd.addAttribute("Shape1", "Visible", "FALSE");
      pd.addAttribute("Shape2", "Visible", "FALSE");
    }
    else {
      pd.addAttribute("Shape1", "Visible", "FALSE");
      pd.addAttribute("Shape2", "Visible", "FALSE");
      pd.addAttribute("Shape3", "Visible", "FALSE");
    }
    //�N
    IkenshoCommon.addString(pd, "Grid1.h3.w3", VRDateParser.format(date, "ee"));
    //��
    IkenshoCommon.addString(pd, "Grid1.h3.w5", VRDateParser.format(date, "MM"));
    //��
    IkenshoCommon.addString(pd, "Grid1.h3.w7", VRDateParser.format(date, "dd"));

    //�N��
    IkenshoCommon.addString(pd, data, "AGE", "Grid1.h3.w10");

    //����
    IkenshoCommon.addSelection(pd, data, "SEX", new String[] {"Shape4",
                               "Shape5"}
                               , -1);

    //�X�֔ԍ�
    IkenshoCommon.addZip(pd, data, "POST_CD", "Grid1.h1.w11", "Grid1.h1.w17");
    //�Z��
    IkenshoCommon.addString(pd, data, "ADDRESS", "Grid1.h2.w12");
    //�d�b�ԍ�
    IkenshoCommon.addTel(pd, data, "TEL1", "TEL2", "Grid1.h3.w17",
                         "Grid1.h3.w19", "Grid1.h3.w21");

    //�L����
    IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 2, 1);

    //��t����
    IkenshoCommon.addSelection(pd, data, "DR_CONSENT", new String[] {"Shape6",
                               "Shape7"}
                               , -1);

    if ( ( (Integer) VRBindPathParser.get("DR_NM_OUTPUT_UMU", data)).intValue() ==
        1) {
      //��t����
      IkenshoCommon.addString(pd, data, "DR_NM", "Grid4.h1.w3");
    }
    //��Ë@�֖�
    IkenshoCommon.addString(pd, data, "MI_NM", "Grid4.h2.w4");
    //��Ë@�֏��ݒn
    IkenshoCommon.addString(pd, data, "MI_ADDRESS", "Grid4.h3.w5");

    //��Ë@�֓d�b�ԍ�
    IkenshoCommon.addTel(pd, data, "MI_TEL1", "MI_TEL2", "Grid5.h1.w2",
                         "Grid5.h1.w4", "Grid5.h1.w6");

    //��Ë@��FAX�ԍ�
    IkenshoCommon.addTel(pd, data, "MI_FAX1", "MI_FAX2", "Grid5.h3.w2",
                         "Grid5.h3.w4", "Grid5.h3.w6");

    //�ŏI�f�@��
    date = (Date)VRBindPathParser.get("LASTDAY", data);
    if(date != null){
      //����
      IkenshoCommon.addString(pd, "Grid6.h1.w12", VRDateParser.format(date, "ggg"));
      //�N
      IkenshoCommon.addString(pd, "Grid6.h1.w11", VRDateParser.format(date, "ee"));
      //��
      IkenshoCommon.addString(pd, "Grid6.h1.w9", VRDateParser.format(date, "MM"));
      //��
      IkenshoCommon.addString(pd, "Grid6.h1.w7", VRDateParser.format(date, "dd"));
    }

    //�ӌ����쐬��
    IkenshoCommon.addSelection(pd, data, "IKN_CREATE_CNT",
                               new String[] {"Shape8",
                               "Shape9"}
                               , -1);

    if(( (Integer) VRBindPathParser.get("TAKA_FLAG", data)).intValue()==1){
      //���Ȏ�f
      int taka = ( (Integer) VRBindPathParser.get("TAKA", data)).intValue();
      if (taka == 0) {
        //���ȓ��e
        for (int i = 0; i < 12; i++) {
          pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
        }
        //���Ȃ��̑�
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
        //���ȓ��e
        for (int i = 0; i < 12; i++) {
          if ( (taka & (1 << i)) == 0) {
            pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
          }
        }
        //���Ȃ��̑�
        if ( ( (Integer) VRBindPathParser.get("TAKA_OTHER_FLAG", data)).intValue() == 1) {
          IkenshoCommon.addString(pd, data, "TAKA_OTHER", "Grid6.h3.w4");
        }else{
          pd.addAttribute("Shape24", "Visible", "FALSE");
        }
      }
    }else{
      pd.addAttribute("Shape10", "Visible", "FALSE");
      //���ȓ��e
      for (int i = 0; i < 12; i++) {
        pd.addAttribute("Shape" + (i + 12), "Visible", "FALSE");
      }
      //���Ȃ��̑�
      pd.addAttribute("Shape24", "Visible", "FALSE");
    }

    //���a
    final String SPACER = "  ";
    for (int i = 0; i < 3; i++) {
      IkenshoCommon.addString(pd, data, "SINDAN_NM" + (i + 1),
                              "Grid8.h" + (i + 1) + ".w2");
      String text = String.valueOf(VRBindPathParser.get("HASHOU_DT" + (i + 1),
          data));
      boolean eraPrinted = false;
      String eYear="";
      if (text.length() == 11) {
         if(text.startsWith("�s��")){
             //����
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w15", "�s��"); 
             //�N���������
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w13", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w11", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w9", "");
         }else if(text.startsWith("0000")){
             //�N���������
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w13", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w11", "");
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w9", "");
         }else{
             date = VRDateParser.parse(text.replaceAll("00��", "01��").replaceAll("00��", "01��"));
             era = VRDateParser.format(date, "ggg");
             eYear = VRDateParser.format(date, "ee");
             //����
             IkenshoCommon.addString(pd, "Grid8.h" + (i + 1) + ".w15", era); 
//          if ("���a".equals(era)) {
//            pd.addAttribute("Shape" + (95 + i * 2), "Visible", "FALSE");
//          }
//          else if ("����".equals(era)) {
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

    //�Ǐ���萫
    //�s����ɂ������̓I�ȏ�
    IkenshoCommon.addSelection(pd, data, "SHJ_ANT", new String[] {"Shape25",
                               "Shape26", "Shape27"}
                               , -1, "INSECURE_CONDITION", "Label12", 2);

    //2009/01/09 [Tozo Tanaka] Replace - begin
//    //���a���Ï��
//    IkenshoCommon.addString(pd, data, "MT_STS", "Label3");
//
//    //���
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
//    //���a���Ï��
//    ACChotarouXMLUtilities.setValue(pd, "Label3",
//            getInsertionLineSeparatorToStringOnByte(ACCastUtilities.toString(data
//                    .get("MT_STS")), 100));
//    
//    //���
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
//        //���7�����8�����͂���Ă���ꍇ
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

    if (totalByteCount > 500 || totalLineCount > 5
            || isUseOver6Medicine) {
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


    ACChotarouXMLUtilities.setValue(pd, "Label3", sbSickProgress.toString());
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
    //2009/01/09 [Tozo Tanaka] Replace - end
    
    //�_�H�Ǘ�
    IkenshoCommon.addCheck(pd, data, "TNT_KNR", "Shape31", 1);
    //���S�Ö��h�{
    IkenshoCommon.addCheck(pd, data, "CHU_JOU_EIYOU", "Shape32", 1);
    //����
    IkenshoCommon.addCheck(pd, data, "TOUSEKI", "Shape33", 1);
    //�X�g�[�}�̏��u
    IkenshoCommon.addCheck(pd, data, "JINKOU_KOUMON", "Shape34", 1);
    //�_�f�Ö@
    IkenshoCommon.addCheck(pd, data, "OX_RYO", "Shape35", 1);
    //���X�s���[�^�[
    IkenshoCommon.addCheck(pd, data, "JINKOU_KOKYU", "Shape36", 1);
    //�C�ǐ؊J�̏��u
    IkenshoCommon.addCheck(pd, data, "KKN_SEK_SHOCHI", "Shape37", 1);
    //�u�ɂ̊Ō�
    IkenshoCommon.addCheck(pd, data, "TOUTU", "Shape38", 1);
    //�o�ǉh�{
    IkenshoCommon.addCheck(pd, data, "KEKN_EIYOU", "Shape39", 1);
    //���j�^�[����
    IkenshoCommon.addCheck(pd, data, "MONITOR", "Shape40", 1);
    //��ጂ̏��u
    IkenshoCommon.addCheck(pd, data, "JOKUSOU_SHOCHI", "Shape41", 1);
    //�J�e�[�e��
    IkenshoCommon.addCheck(pd, data, "CATHETER", "Shape42", 1);

    //�Q������x
    IkenshoCommon.addSelection(pd, data, "NETAKIRI", new String[] {"Shape43",
                               "Shape44", "Shape45", "Shape46", "Shape47",
                               "Shape48", "Shape49", "Shape50", "Shape51"}
                               , -1);
    //�����x
    IkenshoCommon.addSelection(pd, data, "CHH_STS", new String[] {"Shape52",
                               "Shape53", "Shape54", "Shape55", "Shape56",
                               "Shape57", "Shape58", "Shape59"}
                               , -1);
    //�Z���L��
    IkenshoCommon.addSelection(pd, data, "TANKI_KIOKU",
                               new String[] {"Shape60",
                               "Shape61"}
                               , -1);
    //�F�m�\��
    IkenshoCommon.addSelection(pd, data, "NINCHI", new String[] {"Shape62",
                               "Shape63", "Shape64", "Shape65"}
                               , -1);
    //�`�B�\��
    IkenshoCommon.addSelection(pd, data, "DENTATU", new String[] {"Shape66",
                               "Shape67", "Shape68", "Shape69"}
                               , -1);

    if ( ( (Integer) VRBindPathParser.get("MONDAI_FLAG", data)).intValue() == 1) {
      boolean problemAction = false;
      //�����E����
      problemAction |= IkenshoCommon.addCheck(pd, data, "GNS_GNC", "Shape74", 1);
      //�ϑz
      problemAction |= IkenshoCommon.addCheck(pd, data, "MOUSOU", "Shape75", 1);
      //����t�]
      problemAction |= IkenshoCommon.addCheck(pd, data, "CHUYA", "Shape76", 1);
      //�\��
      problemAction |= IkenshoCommon.addCheck(pd, data, "BOUGEN", "Shape77", 1);
      //�\�s
      problemAction |= IkenshoCommon.addCheck(pd, data, "BOUKOU", "Shape78", 1);
      //��R
      problemAction |= IkenshoCommon.addCheck(pd, data, "TEIKOU", "Shape79", 1);
      //�p�j
      problemAction |= IkenshoCommon.addCheck(pd, data, "HAIKAI", "Shape80", 1);
      //�΂̕s�n��
      problemAction |=
          IkenshoCommon.addCheck(pd, data, "FUSIMATU", "Shape81", 1);
      //�s��
      problemAction |= IkenshoCommon.addCheck(pd, data, "FUKETU", "Shape82", 1);
      //�ِH�s��
      problemAction |= IkenshoCommon.addCheck(pd, data, "ISHOKU", "Shape83", 1);
      //���I���s��
      problemAction |=
          IkenshoCommon.addCheck(pd, data, "SEITEKI_MONDAI", "Shape84", 1);
      //���̑�
      if (IkenshoCommon.addCheck(pd, data, "MONDAI_OTHER", "Shape85", 1)) {
        //���̑�����
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

    //���_�E�_�o�Ǐ�̗L��
    switch ( ( (Integer) VRBindPathParser.get("SEISIN", data)).intValue()) {
      case 1:
        IkenshoCommon.addString(pd, data, "SEISIN_NM", "Grid13.h2.w3");
            pd.addAttribute("Shape104", "Visible", "FALSE");

            // �����M�̗L��
            if (IkenshoCommon.addSelection(pd, data, "SENMONI", new String[] {
                    "Shape102", "Shape103" }, -1)) {
                // 2006/02/03[Tozo Tanaka] : replace begin
                // //����
                // IkenshoCommon.addString(pd, data, "SENMONI_NM",
                // "Grid13.h2.w7");
                if ("1".equals(String.valueOf(data.getData("SENMONI")))) {
                    // ����
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
      //����
      IkenshoCommon.addString(pd, data, "PATIENT_NM", "Grid2.h1.w1");
      //�N
      IkenshoCommon.addString(pd, data, "AGE", "Grid2.h1.w3");
      //�L����
      IkenshoCommon.addEraDate(pd, data, "KINYU_DT", "Grid2.h1.w", 5, 1, "��)");
    }else{
      IkenshoCommon.addString(pd, "Grid2.h1.w4", "");
      IkenshoCommon.addString(pd, "Grid2.h1.w7", "");
      IkenshoCommon.addString(pd, "Grid2.h1.w9", "");
      IkenshoCommon.addString(pd, "Grid2.h1.w11", "");
    }



    //���_�E�_�o
    //�����r
    IkenshoCommon.addSelection(pd, data, "KIKIUDE", new String[] {"Shape9",
                               "Shape10"}
                               , -1);
    //�g��
    IkenshoCommon.addString(pd, data, "HEIGHT", "Grid4.h1.w2");
    //�̏d
    IkenshoCommon.addString(pd, data, "WEIGHT", "Grid4.h1.w4");

    //�̏d�̕ω�
    IkenshoCommon.addSelection(pd, data, "WEIGHT_CHANGE",
                               new String[] {"Shape11", "Shape12", "Shape13"}
                               , -1);

    //�l������
    if(IkenshoCommon.addCheck(pd, data, "SISIKESSON", "Shape14", 1)){
      //�l����������
      IkenshoCommon.addString(pd, data, "SISIKESSON_BUI", "Grid1.h3.w18");
    }

    if ( ( (Integer) VRBindPathParser.get("MAHI_FLAG", data)).intValue() == 1) {

      boolean checkFlag = false;
      //�E�㎈���
      if (IkenshoCommon.addCheck(pd, data, "MAHI_RIGHTARM", "Shape16", 1)) {
        //�E�㎈��გ��x
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
      //���㎈���
      if (IkenshoCommon.addCheck(pd, data, "MAHI_LEFTARM", "Shape24", 1)) {
        //���㎈��გ��x
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
      //�E�������
      if (IkenshoCommon.addCheck(pd, data, "MAHI_LOWERRIGHTLIMB", "Shape17", 1)) {
        //�E������გ��x
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
      //���������
      if (IkenshoCommon.addCheck(pd, data, "MAHI_LOWERLEFTLIMB", "Shape25", 1)) {
        //��������გ��x
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
      //���̑����
      if (IkenshoCommon.addCheck(pd, data, "MAHI_ETC", "Shape34", 1)) {
        //���̑���ვ���
        IkenshoCommon.addString(pd, data, "MAHI_ETC_BUI", "Label2");
        //���̑���გ��x
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
      //���
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


    //�ؗ͂̒ቺ
    if(IkenshoCommon.addCheck(pd, data, "KINRYOKU_TEIKA", "Shape51", 1)){
      //�ؗ͂̒ቺ����
      IkenshoCommon.addString(pd, data, "KINRYOKU_TEIKA_BUI", "Grid1.h7.w18");
      //�ؗ͂̒ቺ���x
      IkenshoCommon.addSelection(pd, data, "KINRYOKU_TEIKA_TEIDO",
                                 new String[] {"Shape35", "Shape36", "Shape37"}
                                 , -1);
    }else{
      pd.addAttribute("Shape35", "Visible", "FALSE");
      pd.addAttribute("Shape36", "Visible", "FALSE");
      pd.addAttribute("Shape37", "Visible", "FALSE");
    }

    //�֐߂̍S�k
    if(IkenshoCommon.addCheck(pd, data, "KOUSHU", "Shape52", 1)){
      //�֐߂̍S�k����
      IkenshoCommon.addString(pd, data, "KOUSHU_BUI", "Grid1.h6.w18");
      //�֐߂̍S�k���x
      IkenshoCommon.addSelection(pd, data, "KOUSHU_TEIDO",
                                 new String[] {"Shape39", "Shape40", "Shape41"}
                                 , -1);
    }else{
      pd.addAttribute("Shape39", "Visible", "FALSE");
      pd.addAttribute("Shape40", "Visible", "FALSE");
      pd.addAttribute("Shape41", "Visible", "FALSE");
    }

    //�֐߂̒ɂ�
    if(IkenshoCommon.addCheck(pd, data, "KANSETU_ITAMI", "Shape53", 1)){
      //�֐߂̒ɂݕ���
      IkenshoCommon.addString(pd, data, "KANSETU_ITAMI_BUI", "Grid1.h5.w18");
      //�֐߂̒ɂݒ��x
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
      //�㎈�����E
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "JOUSI_SICCHOU_MIGI", "Shape57", 1);
      //�㎈������
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "JOUSI_SICCHOU_HIDARI", "Shape58", 1);
      //�̊������E
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "TAIKAN_SICCHOU_MIGI", "Shape59", 1);
      //�̊�������
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "TAIKAN_SICCHOU_HIDARI", "Shape60",
                                 1);
      //���������E
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "KASI_SICCHOU_MIGI", "Shape61", 1);
      //����������
      checkFlag |=
          IkenshoCommon.addCheck(pd, data, "KASI_SICCHOU_HIDARI", "Shape62", 1);
      //�����E�s���Ӊ^��
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

    //���
    if(IkenshoCommon.addCheck(pd, data, "JOKUSOU", "Shape55", 1)){
      //��ጕ���
      IkenshoCommon.addString(pd, data, "JOKUSOU_BUI", "Grid1.h1.w18");
      //��ጒ��x
      IkenshoCommon.addSelection(pd, data, "JOKUSOU_TEIDO",
                                 new String[] {"Shape45", "Shape46", "Shape47"}
                                 , -1);
    }else{
      pd.addAttribute("Shape45", "Visible", "FALSE");
      pd.addAttribute("Shape46", "Visible", "FALSE");
      pd.addAttribute("Shape47", "Visible", "FALSE");
    }

    //���̑��̔畆����
    if(IkenshoCommon.addCheck(pd, data, "HIFUSIKKAN", "Shape56", 1)){
      //���̑��̔畆��������
      IkenshoCommon.addString(pd, data, "HIFUSIKKAN_BUI", "Grid1.h2.w18");
      //���̑��̔畆�������x
      IkenshoCommon.addSelection(pd, data, "HIFUSIKKAN_TEIDO",
                                 new String[] {"Shape48", "Shape49", "Shape50"}
                                 , -1);
    }else{
      pd.addAttribute("Shape48", "Visible", "FALSE");
      pd.addAttribute("Shape49", "Visible", "FALSE");
      pd.addAttribute("Shape50", "Visible", "FALSE");
    }


    //���O���s
    IkenshoCommon.addSelection(pd, data, "OUTDOOR", new String[] {"Shape63",
                               "Shape66", "Shape69"}
                               , -1);

    //�Ԃ����̎g�p
      IkenshoCommon.addSelection(pd, data, "WHEELCHAIR", new String[] {"Shape64",
                                 "Shape67", "Shape70"}
                                 , -1);

      //���s�⏕��E����̎g�p
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


    //�H���s��
    IkenshoCommon.addSelection(pd, data, "MEAL", new String[] {"Shape72",
                               "Shape73"}
                               , -1);

    //���݂̉h�{���
    IkenshoCommon.addSelection(pd, data, "NOURISHMENT", new String[] {"Shape78",
                               "Shape79"}
                               , -1);
    //�h�{�E�H������̗��ӓ_
    IkenshoCommon.addString(pd, data, "EATING_RYUIJIKOU", "Grid25.h1.w2");


    //���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j
    ArrayList words = new ArrayList();
    //�A����
    addSickStateCheck(pd, data, "NYOUSIKKIN", "Shape80",
                      "NYOUSIKKIN_TAISHO_HOUSIN", words);
    //�]�|�E����
    addSickStateCheck(pd, data, "TENTOU_KOSSETU", "Shape81",
                      "TENTOU_KOSSETU_TAISHO_HOUSIN", words);
    //�ړ��\�͂̒ቺ
    addSickStateCheck(pd, data, "IDOUTEIKA", "Shape82",
                      "IDOUTEIKA_TAISHO_HOUSIN", words);

    //��ጉ\��
    addSickStateCheck(pd, data, "JOKUSOU_KANOUSEI", "Shape83",
                      "JOKUSOU_KANOUSEI_TAISHO_HOUSIN", words);
    //�S�x�@�\�̒ቺ
    addSickStateCheck(pd, data, "SINPAIKINOUTEIKA", "Shape84",
                      "SINPAIKINOUTEIKA_TAISHO_HOUSIN", words);

    //��������
    addSickStateCheck(pd, data, "TOJIKOMORI", "Shape85",
                      "TOJIKOMORI_TAISHO_HOUSIN", words);

    //�ӗ~�ቺ
    addSickStateCheck(pd, data, "IYOKUTEIKA", "Shape86",
                      "IYOKUTEIKA_TAISHO_HOUSIN", words);

    //�p�j�\��
    addSickStateCheck(pd, data, "HAIKAI_KANOUSEI", "Shape87",
                      "HAIKAI_KANOUSEI_TAISHO_HOUSIN", words);

    //��h�{
    addSickStateCheck(pd, data, "TEIEIYOU", "Shape88",
                      "TEIEIYOU_TAISHO_HOUSIN", words);

    //�ېH�E�����@�\�ቺ
    addSickStateCheck(pd, data, "SESSYOKUENGE", "Shape89",
                      "SESSYOKUENGE_TAISHO_HOUSIN", words);

    //�E��
    addSickStateCheck(pd, data, "DASSUI", "Shape90", "DASSUI_TAISHO_HOUSIN",
                      words);
    //�Պ�����
    addSickStateCheck(pd, data, "EKIKANKANSEN", "Shape91",
                      "EKIKANKANSEN_TAISHO_HOUSIN", words);

    //���񓙂ɂ���u��
    addSickStateCheck(pd, data, "GAN_TOUTU", "Shape92",
                      "GAN_TOUTU_TAISHO_HOUSIN", words);

    //�a�ԑ�
    if (IkenshoCommon.addCheck(pd, data, "BYOUTAITA", "Shape93", 1)) {
      //�a�ԑ���
      IkenshoCommon.addString(pd, data, "BYOUTAITA_NM", "Label8");
      addSickStateCheck(data, "BYOUTAITA_TAISHO_HOUSIN", words);
    }

    StringBuffer sb;
    if (words.size() > 0) {
      //�a�ԑΏ����j
      //�a�Ԃ𕶎��P�ʂŘA�����ĕ\���\�ȂƂ���܂ŁB

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
        if ( (c != '�B') && (c != '�A')) {
          line.append("�A");
//          wordSize += 2;
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
        String text = String.valueOf(words.get(end));

        StringBuffer line = new StringBuffer();
        line.append(text);

        int wordSize = 0;
        char c = text.charAt(text.length() - 1);
        if ( (c != '�B') && (c != '�A')) {
          line.append("�B");
//          wordSize += 2;
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

    //�T�[�r�X���p�ɂ�鐶���@�\�̈ێ��E���P�̌��ʂ�
    IkenshoCommon.addSelection(pd, data, "VITAL_FUNCTIONS_OUTLOOK", new String[] {"Shape94",
                               "Shape95", "Shape97"}
                               , -1);
    //�K��f��
    IkenshoCommon.addCheck(pd, data, "HOUMON_SINRYOU", "Shape98", 1);
    //�K��f�É���
    IkenshoCommon.addCheck(pd, data, "HOUMON_SINRYOU_UL", "Shape121", 1);
    //�K��Ō�
    IkenshoCommon.addCheck(pd, data, "HOUMON_KANGO", "Shape99", 1);
    //�K��Ō쉺��
    IkenshoCommon.addCheck(pd, data, "HOUMON_KANGO_UL", "Shape124", 1);
    // 2009/02/03 [Tozo Tanaka] Add - begin
    //�Ō�E���̖K��ɂ�鑊�k�E�x��
    IkenshoCommon.addCheck(pd, data, "HOUMON_SODAN", "HOUMON_SODAN", 1);
    //�Ō�E���̖K��ɂ�鑊�k�E�x������
    IkenshoCommon.addCheck(pd, data, "HOUMON_SODAN_UL", "HOUMON_SODAN_UL", 1);
    // 2009/02/03 [Tozo Tanaka] Add - end
    //�K�⎕�Ȑf��
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_SINRYOU", "Shape100", 1);
    //�K�⎕�Ȑf�É���
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_SINRYOU_UL", "Shape127", 1);
    //�K���܊Ǘ��w��
    IkenshoCommon.addCheck(pd, data, "HOUMONYAKUZAI_KANRISIDOU", "Shape101", 1);
    //�K���܊Ǘ��w������
    IkenshoCommon.addCheck(pd, data, "HOUMONYAKUZAI_KANRISIDOU_UL", "Shape129",
                           1);

    //�K�⃊�n�r��
    IkenshoCommon.addCheck(pd, data, "HOUMON_REHA", "Shape102", 1);
    //�K�⃊�n�r������
    IkenshoCommon.addCheck(pd, data, "HOUMON_REHA_UL", "Shape122", 1);
    //�Z�������f�É��
    IkenshoCommon.addCheck(pd, data, "TANKI_NYUSHO_RYOUYOU", "Shape103", 1);
    //�Z�������f�É�쉺��
    IkenshoCommon.addCheck(pd, data, "TANKI_NYUSHO_RYOUYOU_UL", "Shape125", 1);
    //�K�⎕�ȉq���w��
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_EISEISIDOU", "Shape104", 1);
    //�K�⎕�ȉq���w������
    IkenshoCommon.addCheck(pd, data, "HOUMONSIKA_EISEISIDOU_UL", "Shape128", 1);
    //�K��h�{�H���w��
    IkenshoCommon.addCheck(pd, data, "HOUMONEIYOU_SHOKUJISIDOU", "Shape105", 1);
    //�K��h�{�H���w������
    IkenshoCommon.addCheck(pd, data, "HOUMONEIYOU_SHOKUJISIDOU_UL", "Shape130",
                           1);

    //�ʏ����n�r��
    IkenshoCommon.addCheck(pd, data, "TUUSHO_REHA", "Shape106", 1);
    //�ʏ����n�r������
    IkenshoCommon.addCheck(pd, data, "TUUSHO_REHA_UL", "Shape123", 1);
    //��w�I�Ǘ���
    if (IkenshoCommon.addCheck(pd, data, "IGAKUTEKIKANRI_OTHER", "Shape107", 1)) {
      //��w�I�Ǘ�������
      IkenshoCommon.addString(pd, data, "IGAKUTEKIKANRI_OTHER_NM",
                              "Grid12.h1.w3");
    }
    //��w�I�Ǘ�������
    IkenshoCommon.addCheck(pd, data, "IGAKUTEKIKANRI_OTHER_UL", "Shape126", 1);

    //����
    IkenshoCommon.addSelection(pd, data, "KETUATU", new String[] {
                               "Shape108", "Shape109"}
                               , -1, "KETUATU_RYUIJIKOU", "Grid5.h1.w2", 2);
    //�ړ�
    IkenshoCommon.addSelection(pd, data, "IDOU", new String[] {
                               "Shape114", "Shape115"}
                               , -1, "IDOU_RYUIJIKOU", "Grid5.h1.w5", 2);
    //�ېH
    IkenshoCommon.addSelection(pd, data, "SESHOKU", new String[] {
                               "Shape110", "Shape111"}
                               , -1, "SESHOKU_RYUIJIKOU", "Grid5.h2.w2", 2);
    //�^��
    IkenshoCommon.addSelection(pd, data, "UNDOU", new String[] {
                               "Shape116", "Shape117"}
                               , -1, "UNDOU_RYUIJIKOU", "Grid5.h2.w5", 2);
    //����
    IkenshoCommon.addSelection(pd, data, "ENGE", new String[] {
                               "Shape112", "Shape113"}
                               , -1, "ENGE_RYUIJIKOU", "Grid5.h3.w2", 2);
    //���̑����ӎ���
    IkenshoCommon.addString(pd, data, "KAIGO_OTHER", "Grid5.h3.w4");

    //������
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
    if (! ("".equals(hasePoint) && "0000�N00��".equals(haseDate) &&
           "".equals(hasePointPreview) && "0000�N00��".equals(haseDatePreview))) {
      //���J�쎮�_��
      IkenshoCommon.addString(pd, data, "HASE_SCORE", "Grid18.h1.w3");
      //���J�쎮���t
      addHasegawaDate(pd, data, "HASE_SCR_DT", "Grid18.h1.w", 6);
      //���J�쎮�O��_��
      IkenshoCommon.addString(pd, data, "P_HASE_SCORE", "Grid18.h1.w17");
      //���J�쎮���t
      addHasegawaDate(pd, data, "P_HASE_SCR_DT", "Grid18.h1.w", 21);

      //1�s�ڂ̎{�ݑI�����B��
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
      //�{�ݑI��
      IkenshoCommon.addString(pd, data, "INST_SEL_PR1",
                              institutionGrid + ".h1.w6");
      IkenshoCommon.addString(pd, data, "INST_SEL_PR2",
                              institutionGrid + ".h1.w10");
      sb.append(IkenshoConstants.LINE_SEPARATOR);
    }
    else {
      pd.addAttribute(institutionGrid, "Visible", "FALSE");
    }
    //���L����
    sb.append(String.valueOf(VRBindPathParser.get("IKN_TOKKI", data)));
    IkenshoCommon.addString(pd, "Grid17.h1.w1", sb.toString());

    pd.endPageEdit();
  }
  
  
  protected int getFormatType(){
      return IkenshoConstants.IKENSHO_LOW_H18;
  }
  

  //2009/01/21 [Tozo Tanaka] Add - begin
  protected static int getMedicineViewCount() {
      // [ID:0000438][Tozo TANAKA] 2009/06/02 replace begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
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
    // [ID:0000438][Tozo TANAKA] 2009/06/02 replace end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
  }
  /**
   * �w�肳�ꂽ�������ŉ��s�����������Ԃ��܂��B
   * @param chr ���s�ΏۂƂȂ镶����
   * @param byteIndex ���s��o�C�g��
   * @return ���s������}������������
   */
  public static String getInsertionLineSeparatorToStringOnByte(String chr, int byteIndex){
      String[] slCharacter = ACTextUtilities.separateLineWrapOnByte(chr,byteIndex);
      
      StringBuffer sbCharacter = new StringBuffer();

      for (int i = 0; i < slCharacter.length; i++) {
          sbCharacter.append(slCharacter[i]);
          //�ŏI�s�ł���ꍇ�͉��s�R�[�h��ǉ����Ȃ�
          if (i != slCharacter.length - 1) {
              //���s�R�[�h��ǉ�����
              sbCharacter.append(ACConstants.LINE_SEPARATOR);
          }
      }

      String insertionLineSeparatorString = sbCharacter.toString();
      
      return insertionLineSeparatorString;
  }
  //2009/01/21 [Tozo Tanaka] Add - end
  
}
