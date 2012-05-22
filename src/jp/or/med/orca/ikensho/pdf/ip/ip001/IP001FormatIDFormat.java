package jp.or.med.orca.ikensho.pdf.ip.ip001;

import java.io.File;
import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * ���[��`��ID��ϊ�����t�H�[�}�b�g�ł��B<br>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2005/12/07
 * @see Format
 */

public class IP001FormatIDFormat extends Format {

    private static IP001FormatIDFormat singleton;
    protected int formatType = -1;

    /**
     * ���[�`�� ��Ԃ��܂��B
     * 
     * @return ���[�`��
     */
    public int getFormatType() {
        return formatType;
    }

    /**
     * ���[�`�� ��ݒ肵�܂��B
     * 
     * @param formatType ���[�`��
     */
    public void setFormatType(int formatType) {
        this.formatType = formatType;
    }

    /**
     * �Ώۂ̃t�@�C�� ��ݒ肵�܂��B
     * 
     * @param targetFile �Ώۂ̃t�@�C��
     */
    public void setTargetFile(File targetFile) {
        int newID = -1;
        if (targetFile != null) {
            String fileName = targetFile.getName().toLowerCase();
            if (fileName.endsWith("newikensho1.xml")) {
                newID = 0;
            } else if (fileName.endsWith("newikensho2.xml")) {
                newID = 1;
//              [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
            } else if ("specialshijisho.xml".equals(fileName)) {
                newID = 13;
//              [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\ 
            } else if (fileName.endsWith("shijisho.xml")) {
                newID = 2;
            } else if (fileName.endsWith("shijishob.xml")) {
                newID = 3;
            } else if (fileName.endsWith("seikyuichiran.xml")) {
                newID = 4;
            } else if (fileName.endsWith("seikyuichirantotal.xml")) {
                newID = 5;
            } else if (fileName.endsWith("soukatusho.xml")) {
                newID = 6;
            } else if (fileName.endsWith("ikenshomeisai.xml")) {
                newID = 7;
            } else if (fileName.endsWith("csvfileoutputpatientlist.xml")) {
                newID = 10;
            } else if (fileName.endsWith("patientlist.xml")) {
                newID = 8;
            } else if (fileName.endsWith("seikyuikenshoichiran.xml")) {
                newID = 9;
            } else if (fileName.endsWith("ikenshoshien1.xml")) {
                newID = 11;
            } else if (fileName.endsWith("ikenshoshien2.xml")) {
                newID = 12; 
            }
            //[ID:0000639][Shin Fujihara] 2011/03 add begin
            else if(fileName.equals("shijisho_m1.xml")) {
            	newID = 14;
            } else if(fileName.equals("shijisho_m2.xml")) {
            	newID = 15;
            } else if(fileName.equals("shijishob_m1.xml")) {
            	newID = 16;
            } else if(fileName.equals("shijishob_m2.xml")) {
            	newID = 17;
            }
            //[ID:0000639][Shin Fujihara] 2011/03 add end
        }
        setFormatType(newID);

    }
    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj == null) {
            return toAppendTo;
        }

        String code = String.valueOf(obj);
        switch (getFormatType()) {
        case 0://�厡��ӌ���1�y�[�W��
            obj = formatIkensho1(code, obj);
            break;
        case 1://�厡��ӌ���2�y�[�W��
            obj = formatIkensho2(code, obj);
            break;
        case 2://�K��Ō�w�����i��Ë@�ցj
            obj = formatShijisho(code, obj);
            break;
        case 3://�K��Ō�w�����i���V�l�ی��{�݁j
            obj = formatShijishoB(code, obj);
            break;
        case 4://�������ꗗ
            obj = formatSeikyuIchitan(code, obj);
            break;
        case 5://���������v
            obj = formatSeikyuIchiranTotal(code, obj);
            break;
        case 6://�厡��ӌ����쐬���E����������(����)��
            obj = formatSoukatsusho(code, obj);
            break;
        case 7://�厡��ӌ����쐬������(����)��
            obj = formatMeisaisho(code, obj);
            break;
        case 8://�o�^���҈ꗗ
            obj = formatPatientList(code, obj);
            break;
        case 9://�����Ώۈӌ����ꗗ
            obj = formatSeikyuIkenshoIchiran(code, obj);
            break;
        case 10://�b�r�u�t�@�C����o���҈ꗗ
            obj = formatCSVList(code, obj);
            break;
        case 11://��t�ӌ���1�y�[�W��
            obj = formatIkenshoShien1(code, obj);
            break;
        case 12://��t�ӌ���2�y�[�W��
            obj = formatIkenshoShien2(code, obj);
            break;
//          [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        case 13://���ʖK��Ō�w����
            obj = formatTokubetsuShijisho(code, obj);
            break;
//          [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
        //[ID:0000639][Shin Fujihara] 2011/03 add begin
        case 14: //�K��Ō�w�����i��Ë@�ցj1�y�[�W��
        	obj = formatShijisho_M1(code, obj);
        	break;
        case 15: //�K��Ō�w�����i��Ë@�ցj2�y�[�W��
        	obj = formatShijisho_M2(code, obj);
        	break;
        case 16: //�K��Ō�w�����i���V�l�ی��{�݁j1�y�[�W��
        	obj = formatShijishoB_M1(code, obj);
        	break;
        case 17: //�K��Ō�w�����i���V�l�ی��{�݁j2�y�[�W��
        	obj = formatShijishoB_M2(code, obj);
        	break;
        //[ID:0000639][Shin Fujihara] 2011/03 add end
        }

        toAppendTo.append(obj);

        return toAppendTo;
    }

    /**
     * �C���X�^���X��Ԃ��܂��B
     * 
     * @return �C���X�^���X
     */
    public static IP001FormatIDFormat getInstance() {
        if (singleton == null) {
            singleton = new IP001FormatIDFormat();
        }
        return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (source == null) {
            return "";
        }

        switch (getFormatType()) {
        case 0://�厡��ӌ���1�y�[�W��
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            break;
            
        case 10://�b�r�u�t�@�C����o���҈ꗗ
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            if ("�Е�".equals(source))
                return "3";
            break;
        }

        return "";
    }
    
    /**
     * �ӌ���1�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatIkensho1(String code, Object obj){
        if ("Label20".equals(code)) obj = "�u�厡��ӌ����v";
        else if ("Grid5.h3.w1".equals(code)) obj = "��Ë@�ցEFAX�ԍ��@�uFAX�v";
        else if ("Grid5.h3.w2".equals(code)) obj = "��Ë@�ցEFAX�ԍ��@FAX�ԍ�1";
        else if ("Grid5.h3.w4".equals(code)) obj = "��Ë@�ցEFAX�ԍ��@FAX�ԍ�2";
        else if ("Grid5.h3.w6".equals(code)) obj = "��Ë@�ցEFAX�ԍ��@FAX�ԍ�3";
        else if ("Grid5.h1.w1".equals(code)) obj = "��Ë@�ցE�d�b�ԍ��@�u�d�b�v";
        else if ("Grid5.h1.w2".equals(code)) obj = "��Ë@�ցE�d�b�ԍ��@�d�b�ԍ�1";
        else if ("Grid5.h1.w4".equals(code)) obj = "��Ë@�ցE�d�b�ԍ��@�d�b�ԍ�2";
        else if ("Grid5.h1.w6".equals(code)) obj = "��Ë@�ցE�d�b�ԍ��@�d�b�ԍ�3";
        else if ("Grid4.h1.w1".equals(code)) obj = "��Ë@�֏��E��t�����@�u��t�����v";
        else if ("Grid2.h1.w1".equals(code)) obj = "�L�����@�u�L�����v";
        else if ("Grid2.h1.w3".equals(code)) obj = "�L����";
        else if ("Grid6.h1.w1".equals(code)) obj = "�ŏI�f�@���@�u(�P) �ŏI�f�@���v";
        else if ("Grid13.h2.w2".equals(code)) obj = "�Ǐ󖼁@�u�k�Ǐ󖼁F�v";
        else if ("Label9".equals(code)) obj = "���a�Ɋւ���ӌ��@�u�P. ���a�Ɋւ���ӌ��v";
        else if ("Label3".equals(code)) obj = "���a�܂��͎��Ó��e";
        else if ("Grid8.h1.w2".equals(code)) obj = "�f�f��1";
        else if ("Grid8.h2.w2".equals(code)) obj = "�f�f��2";
        else if ("Grid8.h3.w2".equals(code)) obj = "�f�f��3";
        else if ("Grid7.h5.w2".equals(code)) obj = "���a���͎��Ó��e2�@�u(�R) �����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a�܂��͓��莾�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e�v";
        else if ("Label23".equals(code)) obj = "�\���ҁE���N�����E�N���@�u���v";
        else if ("Label26".equals(code)) obj = "�\���ҁE���ʁ@�u�j�v";
        else if ("Grid1.h3.w11".equals(code)) obj = "�\���ҏ��@�u�A����v";
        else if ("Grid1.h3.w15".equals(code)) obj = "�\���ҏ��E�N��";
        else if ("Grid7.h1.w2".equals(code)) obj = "�f�f���y�є��ǔN�����@�u(�P) �f�f���v";
        else if ("Label21".equals(code)) obj = "�f�f���y�є��ǔN�����@�u�i���莾�a�܂��͐����@�\�ቺ�̒��ڂ̌����ƂȂ��Ă��鏝�a���ɂ��Ă͂P.�ɋL���j�y�є��ǔN�����v";
        else if ("Grid13.h1.w1".equals(code)) obj = "���̑��̐��_��_�o�Ǐ�@�u(�S) ���̑��̐��_��_�o�Ǐ�v";
        else if ("Grid6.h5.w1".equals(code)) obj = "���Ȏ�f�̗L���@�u(�R) ���Ȏ�f�̗L���v";
        else if ("Grid9".equals(code)) obj = "������e���";
        else if ("Grid9.h1.w1".equals(code)) obj = "������e�E1�s�ڍ�";
        else if ("Grid9.h1.w2".equals(code)) obj = "������e�E1�s�ډE";
        else if ("Grid9.h2.w1".equals(code)) obj = "������e�E2�s�ڍ�";
        else if ("Grid9.h2.w2".equals(code)) obj = "������e�E2�s�ډE";
        else if ("Grid9.h3.w1".equals(code)) obj = "������e�E3�s�ڍ�";
        else if ("Grid9.h3.w2".equals(code)) obj = "������e�E3�s�ډE";
        else if ("Grid10.h1.w1".equals(code)) obj = "���ʂȈ�ÁE���u���e�@�u���u���e�v";
        else if ("Label11".equals(code)) obj = "���ʂȈ��1�@�u�Q. ���ʂȈ�Áv";
        else if ("Grid8.h1.w3".equals(code)) obj = "���ǔN����1�@�u���ǔN�����v";
        else if ("Grid8.h2.w3".equals(code)) obj = "���ǔN����2�@�u���ǔN�����v";
        else if ("Grid8.h3.w3".equals(code)) obj = "���ǔN����3�@�u���ǔN�����v";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("INSURER_NO_LABEL".equals(code)) obj = "�ی��Ҕԍ� ���o��";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "��ی��Ҕԍ� ���o��";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "�쐬���� ���o��";
        else if ("Label113".equals(code)) obj = "�ݑ�E�{�݋敪";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("INSURED_NO".equals(code)) obj = "��ی��Ҕԍ�";
        else if ("INSURER_NO".equals(code)) obj = "�ی��Ҕԍ�";
        else if ("Grid6.h2.w1".equals(code)) obj = "�ӌ����쐬�񐔁@�u(�Q) �ӌ����쐬�񐔁v";
        else if ("Grid4.h3.w5".equals(code)) obj = "��Ë@�֏��E��Ë@�֏��ݒn";
        else if ("Grid4.h3.w1".equals(code)) obj = "��Ë@�֏��E��Ë@�֏��ݒn�@�u��Ë@�֏��ݒn�v";
        else if ("Grid4.h2.w4".equals(code)) obj = "��Ë@�֏��E��Ë@�֖�";
        else if ("Grid4.h2.w1".equals(code)) obj = "��Ë@�֏��E��Ë@�֖��@�u��Ë@�֖��v";
        else if ("Grid4.h1.w4".equals(code)) obj = "��Ë@�֏��E��t����";
        else if ("Grid7.h2.w2".equals(code)) obj = "�Ǐ�Ƃ��Ă̈��萫�@�u(�Q) �Ǐ�Ƃ��Ă̈��萫�v";
        else if ("Grid13.h2.w3".equals(code)) obj = "�Ǐ�";
        else if ("Label8".equals(code)) obj = "�S�g�̏�ԂɊւ���ӌ��@�u�R. �S�g�̏�ԂɊւ���ӌ��v";
        else if ("Label27".equals(code)) obj = "�\���ҁE���ʁ@�u���v";
        else if ("Label25".equals(code)) obj = "�\���ҁE���N�����E�N���@�u���v";
        else if ("Label24".equals(code)) obj = "�\���ҁE���N�����E�N���@�u��v";
        else if ("Grid1".equals(code)) obj = "�\���ҏ��";
        else if ("Grid1.h2.w1".equals(code)) obj = "�\���ҏ��@�u�\���ҁv";
        else if ("Grid1.h2.w3".equals(code)) obj = "�\���ҏ��E����";
        else if ("Grid1.h2.w12".equals(code)) obj = "�\���ҏ��E�Z��";
        else if ("Grid1.h3.w3".equals(code)) obj = "�\���ҏ��E���N����";
        else if ("Grid1.h1.w3".equals(code)) obj = "�\���ҏ��E�ӂ肪��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�\���ҏ��E�ӂ肪�ȁ@�u(�ӂ肪��)�v";
        else if ("Grid1.h1.w11".equals(code)) obj = "�\���ҏ��E�X�֔ԍ�1";
        else if ("Grid1.h1.w12".equals(code)) obj = "�\���ҏ��E�X�֔ԍ��@�L��";
        else if ("Grid13.h2.w4".equals(code)) obj = "�����f�̗L���@�u�����f�̗L���v";
        else if ("Grid10.h4.w1".equals(code)) obj = "���ʂȈ�ÁE���ււ̑Ή��@�u���ււ̑Ή��v";
        else if ("Grid10.h3.w1".equals(code)) obj = "���ʂȈ�ÁE���ʂȑΉ��@�u���ʂȑΉ��v";
        else if ("Label10".equals(code)) obj = "���ʂȈ��2�@�u(�ߋ��P�S���Ԉȓ��Ɏ󂯂���Â̂��ׂĂɃ`�F�b�N)�v";
        else if ("Grid11.h1.w1".equals(code)) obj = "���퐶���̎����x���ɂ��ā@�u(�P) ���퐶���̎����x���ɂ��āv";
        else if ("Grid11.h2.w1".equals(code)) obj = "���퐶���̎����x���ɂ��ā@�u�E��Q����҂̓��퐶�������x�i�Q������x�j�v";
        else if ("Grid11.h3.w1".equals(code)) obj = "���퐶���̎����x���ɂ��ā@�u�E�F�m�Ǎ���҂̓��퐶�������x�v";
        else if ("Label1".equals(code)) obj = "�F�m�ǂ̎��ӏǏ�@�u(�R) �F�m�ǂ̎��ӏǏ� �i�Y�����鍀�ڑS�ă`�F�b�N�F�v";
        else if ("Label2".equals(code)) obj = "�F�m�ǂ̎��ӏǏ�@�u�F�m�ǈȊO�̎����œ��l�̏Ǐ��F�߂�ꍇ���܂ށv";
        else if ("Label5".equals(code)) obj = "�F�m�ǂ̒��j�Ǐ�@�u(�Q) �F�m�ǂ̒��j�Ǐ�v";
        else if ("Label17".equals(code)) obj = "�F�m�ǂ̒��j�Ǐ�@�u�i�F�m�ǈȊO�̎����œ��l�̏Ǐ��F�߂�ꍇ���܂ށj�v";
        else if ("Grid12.h4.w1".equals(code)) obj = "�F�m�ǂ̒��j�Ǐ�@�u�E�����̈ӎv�̓`�B�\�́v";
        else if ("Grid12.h2.w1".equals(code)) obj = "�F�m�ǂ̒��j�Ǐ�@�u�E�Z���L���v";
        else if ("Grid12.h3.w1".equals(code)) obj = "�F�m�ǂ̒��j�Ǐ�@�u�E����̈ӎv������s�����߂̔F�m�\�́v";
        else if ("Grid8.h1.w15".equals(code)) obj = "���ǔN����1";
        else if ("Grid8.h2.w15".equals(code)) obj = "���ǔN����2";
        else if ("Grid8.h3.w15".equals(code)) obj = "���ǔN����3";
        else if ("Grid6.h1.w12".equals(code)) obj = "�ŏI�f�@��";
        else if ("CORNER_BLOCK".equals(code)) obj = "�u���v";
        else if ("FD_OUTPUT_TIME".equals(code)) obj = "�^�C���X�^���v";
        else if ("Grid11".equals(code)) obj = "���퐶���̎����x�����";
        else if ("Grid14".equals(code)) obj = "�F�m�ǂ̎��ӏǏ���";
        else if ("Grid7".equals(code)) obj = "���a�Ɋւ���ӌ����";
        else if ("Grid13".equals(code)) obj = "���̑��̐��_�E�_�o�Ǐ���";
        else if ("Grid3".equals(code)) obj = "���ӏ��";
        else if ("Grid4".equals(code)) obj = "��Ë@�֏��";
        else if ("Grid10".equals(code)) obj = "���ʂȈ�Ï��";
        else if ("Grid6".equals(code)) obj = "�ŏI�f�@�����";
        else if ("Grid12".equals(code)) obj = "�F�m�ǂ̒��j�Ǐ���";
        else if ("Grid1.h1.w17".equals(code)) obj = "�\���ҏ��E�X�֔ԍ�2";
        else if ("Grid1.h3.w17".equals(code)) obj = "�\���ҏ��E�d�b�ԍ�1";
        else if ("Grid1.h3.w19".equals(code)) obj = "�\���ҏ��E�d�b�ԍ�2";
        else if ("Grid1.h3.w21".equals(code)) obj = "�\���ҏ��E�d�b�ԍ�3";
        else if ("Grid2".equals(code)) obj = "�L�������";
        else if ("Grid5".equals(code)) obj = "��Ë@�֘A������";
        else if ("Grid8".equals(code)) obj = "�f�f�����";
        return obj;
    }
    /**
     * �ӌ���2�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatIkensho2(String code, Object obj){
        if ("Label75".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��p�j��@���o��";
        else if ("Label74".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��ӗ~�ቺ��@���o��";
        else if ("Label80".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E����̑��i��@���o��";
        else if ("Label73".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��������裁@���o��";
        else if ("Label79".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E����񓙂ɂ���u�ɣ�@���o��";
        else if ("Label72".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��S�x�@�\�̒ቺ��@���o��";
        else if ("Label78".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��Պ�������@���o��";
        else if ("Label71".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E���ጣ�@���o��";
        else if ("Label77".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��E����@���o��";
        else if ("Label70".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��ړ��\�͂̒ቺ��@���o��";
        else if ("Label76".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��ېH�E�����@�\�ቺ��@���o��";
        else if ("Label69".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��]�|�E���ܣ�@���o��";
        else if ("Label68".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E���h�{��@���o��";
        else if ("Label67".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�E��A���֣�@���o���";
        else if ("Label4".equals(code)) obj = "�i�T�j�g�̂̏�ԁE��g������@���o��";
        else if ("INSURER_NO".equals(code)) obj = "�ی��Ҕԍ�";
        else if ("INSURED_NO".equals(code)) obj = "��ی��Ҕԍ�";
        else if ("FD_OUTPUT_TIME".equals(code)) obj = "�^�C���X�^���v";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("INSURER_NO_LABEL".equals(code)) obj = "�ی��Ҕԍ� ���o��";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "��ی��Ҕԍ� ���o��";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "�쐬���� ���o��";
        else if ("Label113".equals(code)) obj = "�ݑ�E�{�݋敪";
        else if ("Grid14.h1.w2".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�ɂ�����厡��ӌ����쐬���_�Ɣ�r���āw���̕K�v�x�x�@���o��";
        else if ("Grid14.h1.w12".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�@�������@���o��";
        else if ("Grid14.h1.w11".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�@���ω��Ȃ��@���o��";
        else if ("Grid14.h1.w10".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�@�������@���o��";
        else if ("Label96".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�E�������@�^";
        else if ("Label97".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�E���ω��Ȃ��@�^";
        else if ("Label98".equals(code)) obj = "�T�D���L���ׂ������E�O��̗v���x�E�������@�^";
        else if ("Grid27.h1.w1".equals(code)) obj = "�T�D���L���ׂ������E����J�쎮 =��@���o��";
        else if ("Grid27.h1.w4".equals(code)) obj = "�T�D���L���ׂ������E��_ (��@���o��";
        else if ("Grid27.h1.w9".equals(code)) obj = "�T�D���L���ׂ������E��N��@���o��";
        else if ("Grid27.h1.w12".equals(code)) obj = "�T�D���L���ׂ������E����@)��@���o��";
        else if ("Grid27.h1.w14".equals(code)) obj = "�T�D���L���ׂ������E�(�O��@���o��";
        else if ("Grid27.h1.w19".equals(code)) obj = "�T�D���L���ׂ������E�O��E��_ (��@���o��";
        else if ("Grid27.h1.w24".equals(code)) obj = "�T�D���L���ׂ������E�O��E��N��@���o��";
        else if ("Grid27.h1.w27".equals(code)) obj = "�T�D���L���ׂ������E�O��E��� ))��@���o��";
        else if ("Grid28.h1.w1".equals(code)) obj = "�T�D���L���ׂ������E��{�ݑI��(�D��x)��E��i�E�P�@���o��";
        else if ("Grid28.h1.w3".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E��i�E�P�@���o��";
        else if ("Grid28.h1.w6".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E��i�E�P�@���e";
        else if ("Grid28.h1.w7".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E��i�E�Q�@���o��";
        else if ("Grid28.h1.w10".equals(code)) obj = "�T�D���L���ׂ������E��{�ݑI���i�D��x�j��E��i�E�Q�@���e";
        else if ("Grid26.h1.w2".equals(code)) obj = "�v���F�茋�ʂ̏��񋟂���]�@���o��";
        else if ("Grid26.h1.w10".equals(code)) obj = "�v���F�茋�ʂ̏��񋟂���]�E������@�����Ȃ��@���o��";
        else if ("Label94".equals(code)) obj = "�v���F�茋�ʂ̏��񋟂���]�E������@�^";
        else if ("Label95".equals(code)) obj = "�v���F�茋�ʂ̏��񋟂���]�E�����Ȃ��@�^";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("Label6".equals(code)) obj = "�y�[�W��";
        else if ("Label7".equals(code)) obj = "�u�S. �����@�\�ƃT�[�r�X�Ɋւ���ӌ��v�@���o��";
        else if ("Label9".equals(code)) obj = "���L���ׂ������E�u�T. ���L���ׂ������v�@���o��";
        else if ("Label10".equals(code)) obj = "���L���ׂ������E��v���F��y�щ��T�[�r�X�v��쐬���ɕK�v�Ȉ�w�I�Ȃ��ӌ������L�ڂ��ĉ������B�Ȃ��A���㓙�ɕʓr�ӌ������ߣ�@���o��";
        else if ("Label12".equals(code)) obj = "�i�T�j��w�I�Ǘ��̕K�v���E�(���ɕK�v���̍������̂ɂ͉����������ĉ������B�\�h���t�ɂ��񋟂����T�[�r�X���܂݂܂��B)��@���o��";
        else if ("Label8".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�@���̑�";
        else if ("Grid8".equals(code)) obj = "�i�R�j���݂��邩�܂��͍���~����̉\���̍�����ԂƂ��̑Ώ����j�@�g";
        else if ("Grid8.h1.w1".equals(code)) obj = "�(�R)���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j��@���o��";
        else if ("Grid3".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�g";
        else if ("Grid3.h1.w1".equals(code)) obj = "�(�T)�g�̂̏�ԣ�@���o��";
        else if ("Grid1.h3.w18".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�l������";
        else if ("Grid1.h7.w18".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�ؗ͂̒ቺ";
        else if ("Grid1.h6.w18".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�֐߂̍S�k";
        else if ("Grid1.h5.w18".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�֐߂̒ɂ�";
        else if ("Grid1.h1.w18".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@���";
        else if ("Grid1.h2.w18".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@���̑��̔畆����";
        else if ("Grid23.h1.w1".equals(code)) obj = "�(�P)�ړ���@���o��";
        else if ("Grid24.h1.w1".equals(code)) obj = "�i�P�j�ړ��E����O���s��@���o��";
        else if ("Grid24.h2.w1".equals(code)) obj = "�i�P�j�ړ��E��Ԃ����̎g�p��@���o��";
        else if ("Grid24.h3.w1".equals(code)) obj = "�i�P�j�ړ��E����s�⏕�陋���̎g�p��@���o��";
        else if ("Grid21.h1.w1".equals(code)) obj = "(�Q)�h�{�E�H�����E�(�Q)�h�{�E�H������@���o��";
        else if ("Grid22.h1.w1".equals(code)) obj = "(�Q)�h�{�E�H�����E��H���s�ף�@���o��";
        else if ("Grid22.h2.w1".equals(code)) obj = "(�Q)�h�{�E�H�����E����݂̉h�{��ԣ�@���o��";
        else if ("Grid4.h1.w1".equals(code)) obj = "�i�T�j�g�̂̏�ԁE������r�i�v�@���o��";
        else if ("Grid4.h1.w2".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�g��";
        else if ("Grid4.h1.w3".equals(code)) obj = "�i�T�j�g�̂̏�ԁE�cm �̏d����@���o��";
        else if ("Grid4.h1.w4".equals(code)) obj = "�i�T�j�g�̂̏�ԁ@�̏d";
        else if ("Grid4.h1.w9".equals(code)) obj = "�i�T�j�g�̂̏�ԁE�cm �̏d��kg��@���o��";
        else if ("Grid13.h1.w1".equals(code)) obj = "�(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ�����@���o��";
        else if ("Grid5.h1.w1".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ�����������@���o��";
        else if ("Grid5.h1.w2".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����@����";
        else if ("Grid5.h1.w3".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E��ړ��)���o��";
        else if ("Grid5.h1.w5".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����@�ړ�";
        else if ("Grid5.h1.w6".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E�ړ��E�)��@���o��";
        else if ("Grid5.h2.w1".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E���ېH�v�@���o��";
        else if ("Grid5.h2.w2".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����@�ېH";
        else if ("Grid5.h2.w3".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E�)��^���v�@���o��";
        else if ("Grid5.h2.w5".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����@�^��";
        else if ("Grid5.h2.w6".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E�^���E�)�v�@���o��";
        else if ("Grid5.h3.w1".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E��������@���o��";
        else if ("Grid5.h3.w2".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����@����";
        else if ("Grid5.h3.w3".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E�)����̑� (��@���o��";
        else if ("Grid5.h3.w4".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����@���̑�";
        else if ("Grid5.h3.w6".equals(code)) obj = "(�U)�T�[�r�X�񋟎��ɂ������w�I�ϓ_����̗��ӎ����E���̑��)��@���o��";
        else if ("Grid15.h1.w1".equals(code)) obj = "(�V)�����ǂ̗L���E�(�V)�����ǂ̗L�� (�L�̏ꍇ�͋�̓I�ɋL�����ĉ�����)��@���o��";
        else if ("Grid16.h1.w5".equals(code)) obj = "�i�V�j�����ǂ̗L���E�L�@���e";
        else if ("Grid16.h1.w3".equals(code)) obj = "(�V)�����ǂ̗L���E�)��@���o��";
        else if ("Grid11.h1.w1".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���@���o��";
        else if ("Grid70.h1.w1".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E����K��f�ã�@���o��";
        else if ("Grid70.h1.w2".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E����K��Ō죁@���o��";
        else if ("Grid70.h1.w3".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E����K�⎕�Ȑf�ã�@���o��";
        else if ("Grid70.h1.w4".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E�u���K���܊Ǘ��w���v�@���o��";
        else if ("Grid70.h2.w1".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E�u���K�⃊�n�r���e�[�V�����v�@���o��";
        else if ("Grid70.h2.w2".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E�u���Z�������×{���v�@���o��";
        else if ("Grid70.h2.w3".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E�u���K�⎕�ȉq���w���v�@���o��";
        else if ("Grid2.h1.w4".equals(code)) obj = "���p�ҏ��E��� (��@���o��";
        else if ("Grid2.h1.w7".equals(code)) obj = "���p�ҏ��E��N��@���o��";
        else if ("Grid2.h1.w9".equals(code)) obj = "���p�ҏ��E�����@���o��";
        else if ("Grid2.h1.w11".equals(code)) obj = "���p�ҏ��E���)��@���o��";
        else if ("Grid6.h1.w1".equals(code)) obj = "�(�S)�T�[�r�X���p�ɂ�鐶���@�\�̈ێ�����P�̌��ʂ���@���o��";
        else if ("Grid12.h1.w1".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E����ʏ����n�r���e�[�V������@���o��";
        else if ("Grid12.h1.w2".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E������̑��̈�Ìn�T�[�r�X�@(��@���o��";
        else if ("Grid12.h1.w3".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���@���̑��̈�Ìn�T�[�r�X";
        else if ("Grid12.h1.w4".equals(code)) obj = "(�T)��w�I�Ǘ��̕K�v���E�)��@���o��";
        else if ("Grid7.h1.w1".equals(code)) obj = "(�S)�T�[�r�X���p�ɂ�鐶���@�\�̈ێ�����P�̌��ʂ��E������҂ł��飁@���o��";
        else if ("Grid7.h1.w2".equals(code)) obj = "(�S)�T�[�r�X���p�ɂ�鐶���@�\�̈ێ�����P�̌��ʂ��E������҂ł��Ȃ���@���o��";
        else if ("Grid7.h1.w3".equals(code)) obj = "(�S)�T�[�r�X���p�ɂ�鐶���@�\�̈ێ�����P�̌��ʂ��E����s����@���o��";
        else if ("Grid10.h1.w1".equals(code)) obj = "(�R)���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j�E��Ώ����j��@���o��";
        else if ("Grid10.h1.w2".equals(code)) obj = "(�R)���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j�@�Ώ����j";
        else if ("Grid10.h1.w3".equals(code)) obj = "(�R)���݂��邩�܂��͍��㔭���̉\���̍�����ԂƂ��̑Ώ����j�E�Ώ����j�E�)��@���o��";
        else if ("Grid25.h1.w1".equals(code)) obj = "�i�Q�j�h�{�E�H�����E��h�{�E�H������̗��ӓ_��@���o��";
        else if ("Grid25.h1.w2".equals(code)) obj = "�i�Q�j�h�{�E�H�����@�h�{�E�H������̗��ӓ_";
        else if ("Grid25.h1.w3".equals(code)) obj = "�i�Q�j�h�{�E�H�����E�h�{�E�H������̗��ӓ_�E�)��@���o��";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("Grid18.h1.w1".equals(code)) obj = "�T�D���L���ׂ������E����J�쎮 =��E���i�@���o��";
        else if ("Grid18.h1.w4".equals(code)) obj = "�T�D���L���ׂ������E��_ (��E���i�@���o��";
        else if ("Grid18.h1.w9".equals(code)) obj = "�T�D���L���ׂ������E��N��E���i�@���o��";
        else if ("Grid18.h1.w12".equals(code)) obj = "�T�D���L���ׂ������E����@)��E���i�@���o��";
        else if ("Grid18.h1.w14".equals(code)) obj = "�T�D���L���ׂ������E�(�O��E���i�@���o��";
        else if ("Grid18.h1.w19".equals(code)) obj = "�T�D���L���ׂ������E�O��E��_ (��E���i�@���o��";
        else if ("Grid18.h1.w24".equals(code)) obj = "�T�D���L���ׂ������E�O��E��N��E���i�@���o��";
        else if ("Grid18.h1.w27".equals(code)) obj = "�T�D���L���ׂ������E�O��E��� ))��E���i�@���o��";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("Grid19.h1.w1".equals(code)) obj = "�T�D���L���ׂ������E��{�ݑI��(�D��x)��E���i�E�P�@���o��";
        else if ("Grid19.h1.w3".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�P�@���o��";
        else if ("Grid19.h1.w6".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�P�@���e";
        else if ("Grid19.h1.w7".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�Q�@���o��";
        else if ("Grid19.h1.w10".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�Q�@���e";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("Grid20.h1.w1".equals(code)) obj = "�T�D���L���ׂ������E��{�ݑI��(�D��x)��E���i�E�P�@���o��";
        else if ("Grid20.h1.w3".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�P�@���o��";
        else if ("Grid20.h1.w6".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�P�@���e";
        else if ("Grid20.h1.w7".equals(code)) obj = "�T�D���L���ׂ������E�{�ݑI���i�D��x�j�E���i�E�Q�@���o��";
        else if ("Grid20.h1.w10".equals(code)) obj = "�T�D���L���ׂ������E��{�ݑI���i�D��x�j��E���i�E�Q�@���e";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 replace end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        return obj;
    }

    /**
     * �K��Ō�w�����i��Ë@�ցj�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatShijisho(String code, Object obj){
        if ("Label22".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�S�D���̑��v";
        else if ("Label21".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��v";
        else if ("Label20".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�Q. ��ጂ̏��u���v";
        else if ("Label19".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�P. ���n�r���e�[�V�����v";
        else if ("Label18".equals(code)) obj = "�v���F��̏󋵁@�u�T �j�v";
        else if ("Label17".equals(code)) obj = "�v���F��̏󋵁@�u�S�v";
        else if ("Label16".equals(code)) obj = "�v���F��̏󋵁@�u�R�v";
        else if ("Label15".equals(code)) obj = "�v���F��̏󋵁@�u�Q�v";
        else if ("Label14".equals(code)) obj = "�v���F��̏󋵁@�u�P�v";
        else if ("Label10".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("Label8".equals(code)) obj = "���N�����E�����@�u��v�@���o��";
        else if ("Label7".equals(code)) obj = "���N�����E�����@�u����@���o��";
        else if ("Shape27".equals(code)) obj = "���퐶�������x�E�Q������x�@�u�����@�^�v";
        else if ("Label9".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("title".equals(code)) obj = "�u�K��Ō�w�����v�@���o��";
        else if ("Label1".equals(code)) obj = "�u��L�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B�v�@���o��";
        else if ("Label2".equals(code)) obj = "�쐬�N�����@�u����  �N  ��  ���v";
        else if ("Label3".equals(code)) obj = "�K��Ō�X�e�[�V�����@�u��v�@���o��";
        else if ("Label4".equals(code)) obj = "�u��Ë@�֖��v�@���o��";
        else if ("Label5".equals(code)) obj = "�u��Ë@�ֈ�t�����v�@���o��";
        else if ("Label6".equals(code)) obj = "�K��Ō�X�e�[�V������";
        else if ("Label23".equals(code)) obj = "�u�v���F��̏󋵁v�@���o��";
        else if ("Label24".equals(code)) obj = "�u�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j�v�@���o��";
        else if ("Grid13.h1.w2".equals(code)) obj = "��Ë@�֖�";
        else if ("Grid13.h2.w1".equals(code)) obj = "�u�Z�@�@�@���v�@���o��";
        else if ("Grid13.h2.w2".equals(code)) obj = "��Ë@�֏Z��";
        else if ("Grid13.h3.w1".equals(code)) obj = "�u�d�@�@�@�b�v�@���o��";
        else if ("Grid13.h3.w2".equals(code)) obj = "��Ë@�ց@�d�b�ԍ�";
        else if ("Grid13.h4.w1".equals(code)) obj = "�u�i�e�`�w�j�v�@���o��";
        else if ("Grid13.h4.w2".equals(code)) obj = "��Ë@�ց@�e�`�w�ԍ�";
        else if ("Grid13.h5.w2".equals(code)) obj = "��Ë@�ֈ�t����";
        else if ("Grid8".equals(code)) obj = "���퐶���@�g";
        else if ("Grid8.h1.w1".equals(code)) obj = "���퐶�������x�@�u���퐶���v�@���o��";
        else if ("Grid8.h1.w2".equals(code)) obj = "�u�Q������x�v�@���o��";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "���퐶�������x�@�u�� �� �x�v�@���o��";
        else if ("Grid8.h2.w2".equals(code)) obj = "�u�F�m�ǂ̏󋵁v�@���o��";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " II��";
        else if ("Grid8.h2.w15".equals(code)) obj = " II��";
        else if ("Grid8.h2.w13".equals(code)) obj = " III��";
        else if ("Grid8.h2.w11".equals(code)) obj = " III��";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " �l";
        else if ("Grid8.h3.w21".equals(code)) obj = "�v�x��";
        else if ("Grid8.h3.w19".equals(code)) obj = "�v���";
        else if ("Grid10".equals(code)) obj = "���ӎ����y�юw�������@�g";
        else if ("Grid10.h1.w1".equals(code)) obj = "�u���ӎ����y�юw�������v�@���o��";
        else if ("Grid10.h13.w1".equals(code)) obj = "�u I �×{�����w����̗��ӎ����v�@���o��";
        else if ("Grid10.h2.w2".equals(code)) obj = " I �×{�����w����̗��ӎ���";
        else if ("Grid10.h3.w1".equals(code)) obj = "�uII�v �@���o��";
        else if ("Grid10.h4.w2".equals(code)) obj = "�P. ���n�r���e�[�V����";
        else if ("Grid10.h6.w2".equals(code)) obj = "�Q. ��ጂ̏��u��";
        else if ("Grid10.h8.w2".equals(code)) obj = "�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�";
        else if ("Grid10.h12.w2".equals(code)) obj = "�S. ���̑�";
        else if ("Grid10.h10.w2".equals(code)) obj = "�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j";
        else if ("Grid1".equals(code)) obj = "�K��Ō�w�����ԁE�_�H���ˎw�����ԁ@�g";
        else if ("Grid1.h1.w14".equals(code)) obj = "�u�K��Ō�w�����ԁv�@���o��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N";
        else if ("Grid1.h1.w3".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h1.w4".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w5".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w6".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w7".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w8".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N";
        else if ("Grid1.h1.w9".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N�@���o��";
        else if ("Grid1.h1.w10".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w11".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h1.w12".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w13".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w14".equals(code)) obj = "�u�_�H���ˎw�����ԁv�@���o��";
        else if ("Grid1.h2.w2".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N";
        else if ("Grid1.h2.w3".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h2.w4".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w5".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w6".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w7".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w8".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N";
        else if ("Grid1.h2.w9".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N�@���o��";
        else if ("Grid1.h2.w10".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w11".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w12".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w13".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("Grid2.h1.w1".equals(code)) obj = "�u���Ҏ����v�@���o��";
        else if ("Grid2.h1.w2".equals(code)) obj = "���Ҏ���";
        else if ("Grid2.h1.w3".equals(code)) obj = "�u���N�����v";
        else if ("Grid2.h1.w5".equals(code)) obj = "���N�����E�N";
        else if ("Grid2.h1.w6".equals(code)) obj = "���N�����E�N�@���o��";
        else if ("Grid2.h1.w7".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w8".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w9".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w10".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w12".equals(code)) obj = "�΁j";
        else if ("Grid3.h1.w1".equals(code)) obj = "�u���ҏZ���v�@���o��";
        else if ("Grid3.h1.w3".equals(code)) obj = "�u���v�@���o��";
        else if ("Grid3.h1.w4".equals(code)) obj = "���җX�֔ԍ�";
        else if ("Grid3.h2.w4".equals(code)) obj = "���ҏZ��";
        else if ("Grid3.h3.w2".equals(code)) obj = "�d�b�ԍ�";
        else if ("Grid3.h3.w6".equals(code)) obj = "�u�d�b�v�@���o��";
        else if ("Grid4.h1.w1".equals(code)) obj = "�u�傽�鏝�a���v�@���o��";
        else if ("Grid4.h1.w2".equals(code)) obj = "�傽�鏝�a��";
        else if ("Grid5.h1.w1".equals(code)) obj = "�u���݂̏󋵁v�@���o��";
        else if ("Grid6.h1.w1".equals(code)) obj = "�u�Ǐ�E���Ï�ԁv�@���o��";
        else if ("Grid6.h1.w2".equals(code)) obj = "�Ǐ�E���Ï��";
        else if ("Grid7.h1.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u���^���́v�@���o��";
        else if ("Grid7.h1.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v�@���o��";
        else if ("Grid7.h1.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v";
        else if ("Grid7.h1.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v�@���o��";
        else if ("Grid7.h1.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v";
        else if ("Grid7.h2.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u��܂̗p�v";
        else if ("Grid7.h2.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v�@���o��";
        else if ("Grid7.h2.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v";
        else if ("Grid7.h2.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v�@���o��";
        else if ("Grid7.h2.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v";
        else if ("Grid7.h3.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�@�E�p�ʁv";
        else if ("Grid7.h3.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v�@���o��";
        else if ("Grid7.h3.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v";
        else if ("Grid7.h3.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v�@���o��";
        else if ("Grid7.h3.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v";
        else if ("Grid9".equals(code)) obj = "�u�����E�g�p��Ë@�퓙�v�@�g";
        else if ("Grid9.h1.w3".equals(code)) obj = "�P�D���������󗬑��u�E�u1�v�@���o��";
        else if ("Grid9.h1.w4".equals(code)) obj = "�P�D���������󗬑��u�E�u���������󗬑��u�v�@���o��";
        else if ("Grid9.h1.w7".equals(code)) obj = "�Q�D���͉t�������u�E�u2�v�@���o��";
        else if ("Grid9.h1.w8".equals(code)) obj = "�Q�D���͉t�������u�E�u���͉t�������u�v�@���o��";
        else if ("Grid9.h1.w15".equals(code)) obj = "�R�D�_�f�Ö@�E�u3�v�@���o��";
        else if ("Grid9.h1.w24".equals(code)) obj = "�R�D�_�f�Ö@�E�u�_�f�Ö@�i�v�@���o��";
        else if ("Grid9.h1.w20".equals(code)) obj = "�R�D�_�f�Ö@";
        else if ("Grid9.h1.w21".equals(code)) obj = "�R�D�_�f�Ö@�E�u l/min�v�@���o��";
        else if ("Grid9.h1.w22".equals(code)) obj = "�R�D�_�f�Ö@�E�u�j�v�@���o��";
        else if ("Grid9.h2.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u�����E�g�v�@���o��";
        else if ("Grid9.h2.w3".equals(code)) obj = "�S�D�z����E�u4�v�@���o��";
        else if ("Grid9.h2.w4".equals(code)) obj = "�S�D�z����E�u�z����v�@���o��";
        else if ("Grid9.h2.w7".equals(code)) obj = "�T�D���S�Ö��h�{�E�u5�v�@���o��";
        else if ("Grid9.h2.w8".equals(code)) obj = "�T�D���S�Ö��h�{�E�u���S�Ö��h�{�v�@���o��";
        else if ("Grid9.h2.w15".equals(code)) obj = "�U�D�A�t�|���v�E�u6�v�@���o��";
        else if ("Grid9.h2.w24".equals(code)) obj = "�U�D�A�t�|���v�E�u�A�t�|���v�v�@���o��";
        else if ("Grid9.h3.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u�p��Ë@�v�@���o��";
        else if ("Grid9.h3.w3".equals(code)) obj = "�V�D�o�ǉh�{�E�u7�v�@���o��";
        else if ("Grid9.h3.w4".equals(code)) obj = "�V�D�o�ǉh�{�E�u�o�ǉh�{�@�@�@�i�v�@���o��";
        else if ("Grid9.h3.w23".equals(code)) obj = "�V�D�o�ǉh�{";
        else if ("Grid9.h3.w9".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v�@���o��";
        else if ("Grid9.h3.w15".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v";
        else if ("Grid9.h3.w16".equals(code)) obj = "�V�D�o�ǉh�{�E�u�A�v�@���o��";
        else if ("Grid9.h3.w18".equals(code)) obj = "�V�D�o�ǉh�{�@��";
        else if ("Grid9.h3.w19".equals(code)) obj = "�V�D�o�ǉh�{�E�u����1������v�@���o��";
        else if ("Grid9.h3.w22".equals(code)) obj = "�V�D�o�ǉh�{�E�u�j�v�@���o��";
        else if ("Grid9.h4.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u �퓙�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
//        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
//        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
//        else if ("Grid9.h4.w9".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
//        else if ("Grid9.h4.w13".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
//        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
//        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
//        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i���ʁF�v�@���o��";
        else if ("Grid9.h4.w23".equals(code)) obj = "�W�D���u�J�e�[�e���@����";
        else if ("Grid9.h4.w12".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
        else if ("Grid9.h4.w15".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
        else if ("Grid9.h4.w17".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid9.h5.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u �i�ԍ��Ɂv�@���o��";
        else if ("Grid9.h5.w3".equals(code)) obj = "�X�D�l�H�ċz��E�u9�v�@���o��";
        else if ("Grid9.h5.w4".equals(code)) obj = "�X�D�l�H�ċz��E�u�l�H�ċz��@�@�i�v�@���o��";
        else if ("Grid9.h5.w8".equals(code)) obj = "�X�D�l�H�ċz��@���";
        else if ("Grid9.h5.w10".equals(code)) obj = "�X�D�l�H�ċz��E�u�F�ݒ�v�@���o��";
        else if ("Grid9.h5.w6".equals(code)) obj = "�X�D�l�H�ċz��@�ݒ�";
        else if ("Grid9.h5.w22".equals(code)) obj = "�X�D�l�H�ċz��E�u�j�v�@���o��";
        else if ("Grid9.h6.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u ����j�v�@���o��";
        else if ("Grid9.h6.w3".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u10��@���o��";
        else if ("Grid9.h6.w4".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�C�ǃJ�j���[���i�T�C�Y�v�@���o��";
        else if ("Grid9.h6.w9".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���@�T�C�Y";
        else if ("Grid9.h6.w11".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
//        else if ("Grid9.h6.w12".equals(code)) obj = "�P�P�D�h���[���E�u11�v�@���o��";
//        else if ("Grid9.h6.w13".equals(code)) obj = "�P�P�D�h���[���E�u�h���[���i���ʁF�v�@���o��";
//        else if ("Grid9.h6.w20".equals(code)) obj = "�P�P�D�h���[���@����";
//        else if ("Grid9.h6.w22".equals(code)) obj = "�P�P�D�h���[���E�u�j�v�@���o��";
//        else if ("Grid9.h7.w3".equals(code)) obj = "�P�Q�D�l�H���E�u12�v�@���o��";
//        else if ("Grid9.h7.w4".equals(code)) obj = "�P�Q�D�l�H���E�u�l�H���v�@���o��";
//        else if ("Grid9.h7.w5".equals(code)) obj = "�P�R�D�l�H�N���E�u13�v�@���o��";
//        else if ("Grid9.h7.w23".equals(code)) obj = "�P�R�D�l�H�N���E�u�l�H�N���v�@���o��";
//        else if ("Grid9.h7.w10".equals(code)) obj = "�P�S�E���̑��E�u14�v�@���o��";
//        else if ("Grid9.h7.w11".equals(code)) obj = "�P�S�E���̑��E�u���̑��i�v�@���o��";
//        else if ("Grid9.h7.w14".equals(code)) obj = "�P�S�D���̑�";
//        else if ("Grid9.h7.w22".equals(code)) obj = "�P�S�E���̑��E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
        else if ("Grid9.h7.w3".equals(code)) obj = "�P�P�D�l�H���E�u11�v�@���o��";
        else if ("Grid9.h7.w4".equals(code)) obj = "�P�P�D�l�H���E�u�l�H���v�@���o��";
        else if ("Grid9.h7.w5".equals(code)) obj = "�P�Q�D�l�H�N���E�u12�v�@���o��";
        else if ("Grid9.h7.w23".equals(code)) obj = "�P�Q�D�l�H�N���E�u�l�H�N���v�@���o��";
        else if ("Grid9.h7.w10".equals(code)) obj = "�P�R�E���̑��E�u13�v�@���o��";
        else if ("Grid9.h7.w11".equals(code)) obj = "�P�R�E���̑��E�u���̑��i�v�@���o��";
        else if ("Grid9.h7.w14".equals(code)) obj = "�P�R�D���̑�";
        else if ("Grid9.h7.w22".equals(code)) obj = "�P�R�E���̑��E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid11.h1.w1".equals(code)) obj = "�u�ً}���̘A����v�@���o��";
        else if ("Grid11.h1.w2".equals(code)) obj = "�ً}���̘A����";
        else if ("Grid11.h2.w1".equals(code)) obj = "�u�s�ݎ��̑Ή��@�v�@���o��";
        else if ("Grid11.h2.w2".equals(code)) obj = "�s�ݎ��̑Ή��@";
        else if ("Grid12.h1.w1".equals(code)) obj = "���L���ׂ����ӎ����E�u���L���ׂ����ӎ����v�@���o��";
        else if ("Grid12.h1.w6".equals(code)) obj = "���L���ׂ����ӎ����E�u�i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��ĉ������B�j�v�@���o��";
        else if ("Grid12.h2.w1".equals(code)) obj = "���L���ׂ����ӎ���";
        else if ("Grid12.h3.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���̖K��Ō�X�e�[�V�����ւ̎w���v�@���o��";
        else if ("Grid12.h4.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h4.w2".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h4.w4".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h4.w5".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�F�w��K��Ō�X�e�[�V�������v�@���o��";
        else if ("Grid12.h4.w6".equals(code)) obj = "���̖K��Ō�X�e�[�V����";
        else if ("Grid12.h4.w8".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u �a�v�@���o��";
        else if ("Grid12.h4.w7".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�j�v�@���o��";
        
        //[ID:0000732][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        else if ("Grid12.h6.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���v�@���o��";
        else if ("Grid12.h8.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h8.w2".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h8.w4".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h8.w5".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�F�K���쎖�Ə����v�@���o��";
        else if ("Grid12.h8.w6".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə�";
        else if ("Grid12.h8.w8".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u �a�v�@���o��";
        else if ("Grid12.h8.w7".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�j�v�@���o��";
        //[ID:0000732][Shin Fujihara] 2012/04/20 add end �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        
        //[ID:0000639][Shin Fujihara] 2011/03 add begin �u��ጂ̐[���v�Ή��R��
        else if ("Label26".equals(code)) obj = "�u��ጂ̐[���v�@���o��";
        else if ("Grid15.h1.w4".equals(code)) obj = "��ጂ̐[���@�uNPUAP���ށv";
        else if ("Grid15.h1.w9".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�V�x�v";
        else if ("Grid15.h1.w7".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�W�x�v";
        else if ("Grid15.h1.w14".equals(code)) obj = "��ጂ̐[���@�uDESIGN���ށv";
        else if ("Grid15.h1.w18".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD3�v";
        else if ("Grid15.h1.w16".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD4�v";
        else if ("Grid15.h1.w19".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD5�v";
        //[ID:0000639][Shin Fujihara] 2011/03 add end
        return obj;
    }

    /**
     * �K��Ō�w�����i���V�l�ی��{�݁j�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatShijishoB(String code, Object obj){
        if ("Label22".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�S�D���̑��v";
        else if ("Label21".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��v";
        else if ("Label20".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�Q. ��ጂ̏��u���v";
        else if ("Label19".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�P. ���n�r���e�[�V�����v";
        else if ("Shape47".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�S�D���̑��@���v";
        else if ("Shape44".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��@���v";
        else if ("Shape45".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�Q. ��ጂ̏��u���@���v";
        else if ("Shape46".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�P. ���n�r���e�[�V�����@���v";
        else if ("Label18".equals(code)) obj = "�v���F��̏󋵁@�u�T �j�v";
        else if ("Label17".equals(code)) obj = "�v���F��̏󋵁@�u�S�v";
        else if ("Label16".equals(code)) obj = "�v���F��̏󋵁@�u�R�v";
        else if ("Label15".equals(code)) obj = "�v���F��̏󋵁@�u�Q�v";
        else if ("Label14".equals(code)) obj = "�v���F��̏󋵁@�u�P�v";
        else if ("Label10".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("Label8".equals(code)) obj = "���N�����E�����@�u��v�@���o��";
        else if ("Label7".equals(code)) obj = "���N�����E�����@�u����@���o��";
        else if ("Shape27".equals(code)) obj = "���퐶�������x�E�Q������x�@�u�����@�^�v";
        else if ("Label9".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("title".equals(code)) obj = "�u�K��Ō�w�����v�@���o��";
        else if ("Label1".equals(code)) obj = "�u��L�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B�v�@���o��";
        else if ("Label2".equals(code)) obj = "�쐬�N�����@�u����  �N  ��  ���v";
        else if ("Label3".equals(code)) obj = "�K��Ō�X�e�[�V�����@�u��v�@���o��";
        else if ("Label4".equals(code)) obj = "�u���V�l�ی��{�ݖ��v�@���o��";
        else if ("Label5".equals(code)) obj = "�u���V�l�ی��{�݈�t�����v�@���o��";
        else if ("Label6".equals(code)) obj = "�K��Ō�X�e�[�V������";
        else if ("Label23".equals(code)) obj = "�u�v���F��̏󋵁v�@���o��";
        else if ("Label24".equals(code)) obj = "�u�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j�v�@���o��";
        else if ("Grid13.h1.w2".equals(code)) obj = "���V�l�ی��{�ݖ�";
        else if ("Grid13.h2.w1".equals(code)) obj = "�u�Z�@�@�@���v�@���o��";
        else if ("Grid13.h2.w2".equals(code)) obj = "���V�l�ی��{�ݏZ��";
        else if ("Grid13.h3.w1".equals(code)) obj = "�u�d�@�@�@�b�v�@���o��";
        else if ("Grid13.h3.w2".equals(code)) obj = "���V�l�ی��{�݁@�d�b�ԍ�";
        else if ("Grid13.h4.w1".equals(code)) obj = "�u�i�e�`�w�j�v�@���o��";
        else if ("Grid13.h4.w2".equals(code)) obj = "���V�l�ی��{�݁@�e�`�w�ԍ�";
        else if ("Grid13.h5.w2".equals(code)) obj = "���V�l�ی��{�݈�t����";
        else if ("Grid8".equals(code)) obj = "���퐶���@�g";
        else if ("Grid8.h1.w1".equals(code)) obj = "���퐶�������x�@�u���퐶���v�@���o��";
        else if ("Grid8.h1.w2".equals(code)) obj = "�u�Q������x�v�@���o��";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "���퐶�������x�@�u�� �� �x�v�@���o��";
        else if ("Grid8.h2.w2".equals(code)) obj = "�u�F�m�ǂ̏󋵁v�@���o��";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " II��";
        else if ("Grid8.h2.w15".equals(code)) obj = " II��";
        else if ("Grid8.h2.w13".equals(code)) obj = " III��";
        else if ("Grid8.h2.w11".equals(code)) obj = " III��";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " �l";
        else if ("Grid8.h3.w19".equals(code)) obj = " �v�x��";
        else if ("Grid8.h3.w17".equals(code)) obj = " �v���";
        else if ("Grid10".equals(code)) obj = "���ӎ����y�юw�������@�g";
        else if ("Grid10.h1.w1".equals(code)) obj = "�u���ӎ����y�юw�������v�@���o��";
        else if ("Grid10.h13.w1".equals(code)) obj = "�u I �×{�����w����̗��ӎ����v�@���o��";
        else if ("Grid10.h2.w2".equals(code)) obj = " I �×{�����w����̗��ӎ���";
        else if ("Grid10.h3.w1".equals(code)) obj = "�uII�v �@���o��";
        else if ("Grid10.h4.w2".equals(code)) obj = "�P. ���n�r���e�[�V����";
        else if ("Grid10.h6.w2".equals(code)) obj = "�Q. ��ጂ̏��u��";
        else if ("Grid10.h8.w2".equals(code)) obj = "�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ�";
        else if ("Grid10.h12.w2".equals(code)) obj = "�S. ���̑�";
        else if ("Grid10.h10.w2".equals(code)) obj = "�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j";
        else if ("Grid1".equals(code)) obj = "�K��Ō�w�����ԁE�_�H���ˎw�����ԁ@�g";
        else if ("Grid1.h1.w14".equals(code)) obj = "�u�K��Ō�w�����ԁv�@���o��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N";
        else if ("Grid1.h1.w3".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h1.w4".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w5".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w6".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w7".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w8".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N";
        else if ("Grid1.h1.w9".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N�@���o��";
        else if ("Grid1.h1.w10".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w11".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h1.w12".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w13".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w14".equals(code)) obj = "�u�_�H���ˎw�����ԁv�@���o��";
        else if ("Grid1.h2.w2".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N";
        else if ("Grid1.h2.w3".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h2.w4".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w5".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w6".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w7".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w8".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N";
        else if ("Grid1.h2.w9".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N�@���o��";
        else if ("Grid1.h2.w10".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w11".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w12".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w13".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("Grid2.h1.w1".equals(code)) obj = "�u�����Ҏ����v�@���o��";
        else if ("Grid2.h1.w2".equals(code)) obj = "�����Ҏ���";
        else if ("Grid2.h1.w3".equals(code)) obj = "�u���N�����v";
        else if ("Grid2.h1.w5".equals(code)) obj = "���N�����E�N";
        else if ("Grid2.h1.w6".equals(code)) obj = "���N�����E�N�@���o��";
        else if ("Grid2.h1.w7".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w8".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w9".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w10".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w12".equals(code)) obj = "�΁j";
        else if ("Grid3.h1.w1".equals(code)) obj = "�u�����ҏZ���v�@���o��";
        else if ("Grid3.h1.w3".equals(code)) obj = "�u���v�@���o��";
        else if ("Grid3.h1.w4".equals(code)) obj = "�����җX�֔ԍ�";
        else if ("Grid3.h2.w4".equals(code)) obj = "�����ҏZ��";
        else if ("Grid3.h3.w2".equals(code)) obj = "�d�b�ԍ�";
        else if ("Grid3.h3.w6".equals(code)) obj = "�u�d�b�v�@���o��";
        else if ("Grid4.h1.w1".equals(code)) obj = "�u�傽�鏝�a���v�@���o��";
        else if ("Grid4.h1.w2".equals(code)) obj = "�傽�鏝�a��";
        else if ("Grid5.h1.w1".equals(code)) obj = "�u���݂̏󋵁v�@���o��";
        else if ("Grid6.h1.w1".equals(code)) obj = "�u�Ǐ�E���Ï�ԁv�@���o��";
        else if ("Grid6.h1.w2".equals(code)) obj = "�Ǐ�E���Ï��";
        else if ("Grid7.h1.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u���^���́v�@���o��";
        else if ("Grid7.h1.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v�@���o��";
        else if ("Grid7.h1.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v";
        else if ("Grid7.h1.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v�@���o��";
        else if ("Grid7.h1.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v";
        else if ("Grid7.h2.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u��܂̗p�v";
        else if ("Grid7.h2.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v�@���o��";
        else if ("Grid7.h2.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v";
        else if ("Grid7.h2.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v�@���o��";
        else if ("Grid7.h2.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v";
        else if ("Grid7.h3.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�@�E�p�ʁv";
        else if ("Grid7.h3.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v�@���o��";
        else if ("Grid7.h3.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v";
        else if ("Grid7.h3.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v�@���o��";
        else if ("Grid7.h3.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v";
        else if ("Grid9".equals(code)) obj = "�u�����E�g�p��Ë@�퓙�v�@�g";
        else if ("Grid9.h1.w3".equals(code)) obj = "�P�D���������󗬑��u�E�u1�v�@���o��";
        else if ("Grid9.h1.w4".equals(code)) obj = "�P�D���������󗬑��u�E�u���������󗬑��u�v�@���o��";
        else if ("Grid9.h1.w7".equals(code)) obj = "�Q�D���͉t�������u�E�u2�v�@���o��";
        else if ("Grid9.h1.w8".equals(code)) obj = "�Q�D���͉t�������u�E�u���͉t�������u�v�@���o��";
        else if ("Grid9.h1.w15".equals(code)) obj = "�R�D�_�f�Ö@�E�u3�v�@���o��";
        else if ("Grid9.h1.w24".equals(code)) obj = "�R�D�_�f�Ö@�E�u�_�f�Ö@�i�v�@���o��";
        else if ("Grid9.h1.w20".equals(code)) obj = "�R�D�_�f�Ö@";
        else if ("Grid9.h1.w21".equals(code)) obj = "�R�D�_�f�Ö@�E�u l/min�v�@���o��";
        else if ("Grid9.h1.w22".equals(code)) obj = "�R�D�_�f�Ö@�E�u�j�v�@���o��";
        else if ("Grid9.h2.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u�����E�g�v�@���o��";
        else if ("Grid9.h2.w3".equals(code)) obj = "�S�D�z����E�u4�v�@���o��";
        else if ("Grid9.h2.w4".equals(code)) obj = "�S�D�z����E�u�z����v�@���o��";
        else if ("Grid9.h2.w7".equals(code)) obj = "�T�D���S�Ö��h�{�E�u5�v�@���o��";
        else if ("Grid9.h2.w8".equals(code)) obj = "�T�D���S�Ö��h�{�E�u���S�Ö��h�{�v�@���o��";
        else if ("Grid9.h2.w15".equals(code)) obj = "�U�D�A�t�|���v�E�u6�v�@���o��";
        else if ("Grid9.h2.w24".equals(code)) obj = "�U�D�A�t�|���v�E�u�A�t�|���v�v�@���o��";
        else if ("Grid9.h3.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u�p��Ë@�v�@���o��";
        else if ("Grid9.h3.w3".equals(code)) obj = "�V�D�o�ǉh�{�E�u7�v�@���o��";
        else if ("Grid9.h3.w4".equals(code)) obj = "�V�D�o�ǉh�{�E�u�o�ǉh�{�@�@�@�i�v�@���o��";
        else if ("Grid9.h3.w23".equals(code)) obj = "�V�D�o�ǉh�{";
        else if ("Grid9.h3.w9".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v�@���o��";
        else if ("Grid9.h3.w15".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v";
        else if ("Grid9.h3.w16".equals(code)) obj = "�V�D�o�ǉh�{�E�u�A�v�@���o��";
        else if ("Grid9.h3.w18".equals(code)) obj = "�V�D�o�ǉh�{�@��";
        else if ("Grid9.h3.w19".equals(code)) obj = "�V�D�o�ǉh�{�E�u����1������v�@���o��";
        else if ("Grid9.h3.w22".equals(code)) obj = "�V�D�o�ǉh�{�E�u�j�v�@���o��";
        else if ("Grid9.h4.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u �퓙�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
//        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
//        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
//        else if ("Grid9.h4.w9".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
//        else if ("Grid9.h4.w13".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
//        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
//        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
//        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i���ʁF�v�@���o��";
        else if ("Grid9.h4.w23".equals(code)) obj = "�W�D���u�J�e�[�e���@����";
        else if ("Grid9.h4.w12".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
        else if ("Grid9.h4.w15".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
        else if ("Grid9.h4.w17".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid9.h5.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u �i�ԍ��Ɂv�@���o��";
        else if ("Grid9.h5.w3".equals(code)) obj = "�X�D�l�H�ċz��E�u9�v�@���o��";
        else if ("Grid9.h5.w4".equals(code)) obj = "�X�D�l�H�ċz��E�u�l�H�ċz��@�@�i�v�@���o��";
        else if ("Grid9.h5.w8".equals(code)) obj = "�X�D�l�H�ċz��@���";
        else if ("Grid9.h5.w10".equals(code)) obj = "�X�D�l�H�ċz��E�u�F�ݒ�v�@���o��";
        else if ("Grid9.h5.w6".equals(code)) obj = "�X�D�l�H�ċz��@�ݒ�";
        else if ("Grid9.h5.w22".equals(code)) obj = "�X�D�l�H�ċz��E�u�j�v�@���o��";
        else if ("Grid9.h6.w1".equals(code)) obj = "�����E�g�p��Ë@�퓙�E�u ����j�v�@���o��";
        else if ("Grid9.h6.w3".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u10��@���o��";
        else if ("Grid9.h6.w4".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�C�ǃJ�j���[���i�T�C�Y�v�@���o��";
        else if ("Grid9.h6.w9".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���@�T�C�Y";
        else if ("Grid9.h6.w11".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
//        else if ("Grid9.h6.w12".equals(code)) obj = "�P�P�D�h���[���E�u11�v�@���o��";
//        else if ("Grid9.h6.w13".equals(code)) obj = "�P�P�D�h���[���E�u�h���[���i���ʁF�v�@���o��";
//        else if ("Grid9.h6.w20".equals(code)) obj = "�P�P�D�h���[���@����";
//        else if ("Grid9.h6.w22".equals(code)) obj = "�P�P�D�h���[���E�u�j�v�@���o��";
//        else if ("Grid9.h7.w3".equals(code)) obj = "�P�Q�D�l�H���E�u12�v�@���o��";
//        else if ("Grid9.h7.w4".equals(code)) obj = "�P�Q�D�l�H���E�u�l�H���v�@���o��";
//        else if ("Grid9.h7.w5".equals(code)) obj = "�P�R�D�l�H�N���E�u13�v�@���o��";
//        else if ("Grid9.h7.w23".equals(code)) obj = "�P�R�D�l�H�N���E�u�l�H�N���v�@���o��";
//        else if ("Grid9.h7.w10".equals(code)) obj = "�P�S�E���̑��E�u14�v�@���o��";
//        else if ("Grid9.h7.w11".equals(code)) obj = "�P�S�E���̑��E�u���̑��i�v�@���o��";
//        else if ("Grid9.h7.w14".equals(code)) obj = "�P�S�D���̑�";
//        else if ("Grid9.h7.w22".equals(code)) obj = "�P�S�E���̑��E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
        else if ("Grid9.h7.w3".equals(code)) obj = "�P�P�D�l�H���E�u11�v�@���o��";
        else if ("Grid9.h7.w4".equals(code)) obj = "�P�P�D�l�H���E�u�l�H���v�@���o��";
        else if ("Grid9.h7.w5".equals(code)) obj = "�P�Q�D�l�H�N���E�u12�v�@���o��";
        else if ("Grid9.h7.w23".equals(code)) obj = "�P�Q�D�l�H�N���E�u�l�H�N���v�@���o��";
        else if ("Grid9.h7.w10".equals(code)) obj = "�P�R�E���̑��E�u13�v�@���o��";
        else if ("Grid9.h7.w11".equals(code)) obj = "�P�R�E���̑��E�u���̑��i�v�@���o��";
        else if ("Grid9.h7.w14".equals(code)) obj = "�P�R�D���̑�";
        else if ("Grid9.h7.w22".equals(code)) obj = "�P�R�E���̑��E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid11.h1.w1".equals(code)) obj = "�u�ً}���̘A����v�@���o��";
        else if ("Grid11.h1.w2".equals(code)) obj = "�ً}���̘A����";
        else if ("Grid11.h2.w1".equals(code)) obj = "�u�s�ݎ��̑Ή��@�v�@���o��";
        else if ("Grid11.h2.w2".equals(code)) obj = "�s�ݎ��̑Ή��@";
        else if ("Grid12.h1.w1".equals(code)) obj = "���L���ׂ����ӎ����E�u���L���ׂ����ӎ����v�@���o��";
        else if ("Grid12.h1.w6".equals(code)) obj = "���L���ׂ����ӎ����E�u�i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��ĉ������B�j�v�@���o��";
        else if ("Grid12.h2.w1".equals(code)) obj = "���L���ׂ����ӎ���";
        else if ("Grid12.h3.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���̖K��Ō�X�e�[�V�����ւ̎w���v�@���o��";
        else if ("Grid12.h4.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h4.w2".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h4.w4".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h4.w5".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�F�w��K��Ō�X�e�[�V�������v�@���o��";
        else if ("Grid12.h4.w6".equals(code)) obj = "���̖K��Ō�X�e�[�V����";
        else if ("Grid12.h4.w8".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u �a�v�@���o��";
        else if ("Grid12.h4.w7".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�j�v�@���o��";
        
        //[ID:0000732][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        else if ("Grid12.h6.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���v�@���o��";
        else if ("Grid12.h8.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h8.w2".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h8.w4".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h8.w5".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�F�K���쎖�Ə����v�@���o��";
        else if ("Grid12.h8.w6".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə�";
        else if ("Grid12.h8.w8".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u �a�v�@���o��";
        else if ("Grid12.h8.w7".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�j�v�@���o��";
        //[ID:0000732][Shin Fujihara] 2012/04/20 add end �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        
        //[ID:0000639][Shin Fujihara] 2011/03 add begin �u��ጂ̐[���v�Ή��R��
        else if ("Label26".equals(code)) obj = "�u��ጂ̐[���v�@���o��";
        else if ("Grid15.h3.w22".equals(code)) obj = "��ጂ̐[���@�uNPUAP���ށv";
        else if ("Grid15.h3.w20".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�V�x�v";
        else if ("Grid15.h3.w17".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�W�x�v";
        else if ("Grid15.h3.w15".equals(code)) obj = "��ጂ̐[���@�uDESIGN���ށv";
        else if ("Grid15.h3.w13".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD3�v";
        else if ("Grid15.h3.w11".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD4�v";
        else if ("Grid15.h3.w9".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD5�v";
        //[ID:0000639][Shin Fujihara] 2011/03 add end
        return obj;
    }

    /**
     * �������ꗗ�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatSeikyuIchitan(String code, Object obj){
        if ("keizoku12".equals(code)) obj = "����12�s�ځu�p���v����";
        else if ("sinki12".equals(code)) obj = "����12�s�ځu�V�K�v����";
        else if ("sisetu12".equals(code)) obj = "����12�s�ځu�{�݁v����";
        else if ("zaitaku12".equals(code)) obj = "����12�s�ځu�ݑ�v����";
        else if ("keizoku11".equals(code)) obj = "����11�s�ځu�p���v����";
        else if ("sinki11".equals(code)) obj = "����11�s�ځu�V�K�v����";
        else if ("sisetu11".equals(code)) obj = "����11�s�ځu�{�݁v����";
        else if ("zaitaku11".equals(code)) obj = "����11�s�ځu�ݑ�v����";
        else if ("keizoku10".equals(code)) obj = "����10�s�ځu�p���v����";
        else if ("sinki10".equals(code)) obj = "����10�s�ځu�V�K�v����";
        else if ("sisetu10".equals(code)) obj = "����10�s�ځu�{�݁v����";
        else if ("zaitaku10".equals(code)) obj = "����10�s�ځu�ݑ�v����";
        else if ("keizoku9".equals(code)) obj = "����9�s�ځu�p���v����";
        else if ("sinki9".equals(code)) obj = "����9�s�ځu�V�K�v����";
        else if ("sisetu9".equals(code)) obj = "����9�s�ځu�{�݁v����";
        else if ("zaitaku9".equals(code)) obj = "����9�s�ځu�ݑ�v����";
        else if ("keizoku8".equals(code)) obj = "����8�s�ځu�p���v����";
        else if ("sinki8".equals(code)) obj = "����8�s�ځu�V�K�v����";
        else if ("sisetu8".equals(code)) obj = "����8�s�ځu�{�݁v����";
        else if ("zaitaku8".equals(code)) obj = "����8�s�ځu�ݑ�v����";
        else if ("keizoku7".equals(code)) obj = "����7�s�ځu�p���v����";
        else if ("sinki7".equals(code)) obj = "����7�s�ځu�V�K�v����";
        else if ("sisetu7".equals(code)) obj = "����7�s�ځu�{�݁v����";
        else if ("zaitaku7".equals(code)) obj = "����7�s�ځu�ݑ�v����";
        else if ("keizoku6".equals(code)) obj = "����6�s�ځu�p���v����";
        else if ("sinki6".equals(code)) obj = "����6�s�ځu�V�K�v����";
        else if ("sisetu6".equals(code)) obj = "����6�s�ځu�{�݁v����";
        else if ("zaitaku6".equals(code)) obj = "����6�s�ځu�ݑ�v����";
        else if ("keizoku5".equals(code)) obj = "����5�s�ځu�p���v����";
        else if ("sinki5".equals(code)) obj = "����5�s�ځu�V�K�v����";
        else if ("sisetu5".equals(code)) obj = "����5�s�ځu�{�݁v����";
        else if ("zaitaku5".equals(code)) obj = "����5�s�ځu�ݑ�v����";
        else if ("keizoku4".equals(code)) obj = "����4�s�ځu�p���v����";
        else if ("sinki4".equals(code)) obj = "����4�s�ځu�V�K�v����";
        else if ("sisetu4".equals(code)) obj = "����4�s�ځu�{�݁v����";
        else if ("zaitaku4".equals(code)) obj = "����4�s�ځu�ݑ�v����";
        else if ("keizoku3".equals(code)) obj = "����3�s�ځu�p���v����";
        else if ("sinki3".equals(code)) obj = "����3�s�ځu�V�K�v����";
        else if ("sisetu3".equals(code)) obj = "����3�s�ځu�{�݁v����";
        else if ("zaitaku3".equals(code)) obj = "����3�s�ځu�ݑ�v����";
        else if ("keizoku2".equals(code)) obj = "����2�s�ځu�p���v����";
        else if ("sinki2".equals(code)) obj = "����2�s�ځu�V�K�v����";
        else if ("sisetu2".equals(code)) obj = "����2�s�ځu�{�݁v����";
        else if ("zaitaku2".equals(code)) obj = "����2�s�ځu�ݑ�v����";
        else if ("keizoku1".equals(code)) obj = "����1�s�ځu�p���v����";
        else if ("sinki1".equals(code)) obj = "����1�s�ځu�V�K�v����";
        else if ("sisetu1".equals(code)) obj = "����1�s�ځu�{�݁v����";
        else if ("zaitaku1".equals(code)) obj = "����1�s�ځu�ݑ�v����";
        else if ("pageNo".equals(code)) obj = "�y�[�W�ԍ�";
        else if ("Label1".equals(code)) obj = "����1�s�ځu�ݑ�v";
        else if ("Label2".equals(code)) obj = "����1�s�ځu�E�v1";
        else if ("Label3".equals(code)) obj = "����1�s�ځu�{�݁v";
        else if ("Label4".equals(code)) obj = "����1�s�ځu�V�K�v";
        else if ("Label5".equals(code)) obj = "����1�s�ځu�p���v";
        else if ("Label6".equals(code)) obj = "����1�s�ځu�E�v2";
        else if ("Label8".equals(code)) obj = "����2�s�ځu�ݑ�v";
        else if ("Label9".equals(code)) obj = "����2�s�ځu�E�v1";
        else if ("Label10".equals(code)) obj = "����2�s�ځu�{�݁v";
        else if ("Label11".equals(code)) obj = "����2�s�ځu�V�K�v";
        else if ("Label12".equals(code)) obj = "����2�s�ځu�p���v";
        else if ("Label13".equals(code)) obj = "����2�s�ځu�E�v2";
        else if ("Label14".equals(code)) obj = "����3�s�ځu�ݑ�v";
        else if ("Label15".equals(code)) obj = "����3�s�ځu�E�v1";
        else if ("Label16".equals(code)) obj = "����3�s�ځu�{�݁v";
        else if ("Label17".equals(code)) obj = "����3�s�ځu�V�K�v";
        else if ("Label18".equals(code)) obj = "����3�s�ځu�p���v";
        else if ("Label19".equals(code)) obj = "����3�s�ځu�E�v2";
        else if ("Label20".equals(code)) obj = "����4�s�ځu�ݑ�v";
        else if ("Label21".equals(code)) obj = "����4�s�ځu�E�v1";
        else if ("Label22".equals(code)) obj = "����4�s�ځu�{�݁v";
        else if ("Label23".equals(code)) obj = "����4�s�ځu�V�K�v";
        else if ("Label24".equals(code)) obj = "����4�s�ځu�p���v";
        else if ("Label25".equals(code)) obj = "����4�s�ځu�E�v2";
        else if ("Label26".equals(code)) obj = "����5�s�ځu�ݑ�v";
        else if ("Label27".equals(code)) obj = "����5�s�ځu�E�v1";
        else if ("Label28".equals(code)) obj = "����5�s�ځu�{�݁v";
        else if ("Label29".equals(code)) obj = "����5�s�ځu�V�K�v";
        else if ("Label30".equals(code)) obj = "����5�s�ځu�p���v";
        else if ("Label31".equals(code)) obj = "����5�s�ځu�E�v2";
        else if ("Label32".equals(code)) obj = "����6�s�ځu�ݑ�v";
        else if ("Label33".equals(code)) obj = "����6�s�ځu�E�v1";
        else if ("Label34".equals(code)) obj = "����6�s�ځu�{�݁v";
        else if ("Label35".equals(code)) obj = "����6�s�ځu�V�K�v";
        else if ("Label36".equals(code)) obj = "����6�s�ځu�p���v";
        else if ("Label37".equals(code)) obj = "����6�s�ځu�E�v2";
        else if ("Label38".equals(code)) obj = "����7�s�ځu�ݑ�v";
        else if ("Label39".equals(code)) obj = "����7�s�ځu�E�v1";
        else if ("Label40".equals(code)) obj = "����7�s�ځu�{�݁v";
        else if ("Label41".equals(code)) obj = "����7�s�ځu�V�K�v";
        else if ("Label42".equals(code)) obj = "����7�s�ځu�p���v";
        else if ("Label43".equals(code)) obj = "����7�s�ځu�E�v2";
        else if ("Label44".equals(code)) obj = "����8�s�ځu�ݑ�v";
        else if ("Label45".equals(code)) obj = "����8�s�ځu�E�v1";
        else if ("Label46".equals(code)) obj = "����8�s�ځu�{�݁v";
        else if ("Label47".equals(code)) obj = "����8�s�ځu�V�K�v";
        else if ("Label48".equals(code)) obj = "����8�s�ځu�p���v";
        else if ("Label49".equals(code)) obj = "����8�s�ځu�E�v2";
        else if ("Label50".equals(code)) obj = "����9�s�ځu�ݑ�v";
        else if ("Label51".equals(code)) obj = "����9�s�ځu�E�v1";
        else if ("Label52".equals(code)) obj = "����9�s�ځu�{�݁v";
        else if ("Label53".equals(code)) obj = "����9�s�ځu�V�K�v";
        else if ("Label54".equals(code)) obj = "����9�s�ځu�p���v";
        else if ("Label55".equals(code)) obj = "����9�s�ځu�E�v2";
        else if ("Label56".equals(code)) obj = "����10�s�ځu�ݑ�v";
        else if ("Label57".equals(code)) obj = "����10�s�ځu�E�v1";
        else if ("Label58".equals(code)) obj = "����10�s�ځu�{�݁v";
        else if ("Label59".equals(code)) obj = "����10�s�ځu�V�K�v";
        else if ("Label60".equals(code)) obj = "����10�s�ځu�p���v";
        else if ("Label61".equals(code)) obj = "����10�s�ځu�E�v2";
        else if ("Label62".equals(code)) obj = "����11�s�ځu�ݑ�v";
        else if ("Label63".equals(code)) obj = "����11�s�ځu�E�v1";
        else if ("Label64".equals(code)) obj = "����11�s�ځu�{�݁v";
        else if ("Label65".equals(code)) obj = "����11�s�ځu�V�K�v";
        else if ("Label66".equals(code)) obj = "����11�s�ځu�p���v";
        else if ("Label67".equals(code)) obj = "����11�s�ځu�E�v2";
        else if ("Label68".equals(code)) obj = "����12�s�ځu�ݑ�v";
        else if ("Label69".equals(code)) obj = "����12�s�ځu�E�v1";
        else if ("Label70".equals(code)) obj = "����12�s�ځu�{�݁v";
        else if ("Label71".equals(code)) obj = "����12�s�ځu�V�K�v";
        else if ("Label72".equals(code)) obj = "����12�s�ځu�p���v";
        else if ("Label73".equals(code)) obj = "����12�s�ځu�E�v2";
        else if ("table".equals(code)) obj = "�ꗗ";
        else if ("table.h1.w2".equals(code)) obj = "����1�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h1.w3".equals(code)) obj = "����1�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h1.w4".equals(code)) obj = "����1�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h1.w5".equals(code)) obj = "����1�s�ځu�ӌ����쐬���v���e";
        else if ("table.h2.w4".equals(code)) obj = "����1�s�ځu�ӌ������t���v���o��";
        else if ("table.h2.w5".equals(code)) obj = "����1�s�ځu�ӌ������t���v���e";
        else if ("table.h4.w2".equals(code)) obj = "����1�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h4.w3".equals(code)) obj = "����1�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h4.w4".equals(code)) obj = "����1�s�ځu��ʁv���o��";
        else if ("table.h4.w5".equals(code)) obj = "����1�s�ځu��ʁv���e";
        else if ("table.h5.w2".equals(code)) obj = "����1�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h5.w3".equals(code)) obj = "����1�s�ځu��ی��Ҏ����v���e";
        else if ("table.h5.w4".equals(code)) obj = "����1�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h5.w5".equals(code)) obj = "����1�s�ځu�ӌ����쐬���v���e";
        else if ("table.h5.w6".equals(code)) obj = "����1�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h6.w4".equals(code)) obj = "����1�s�ځu�f�@�E������p�v���o��";
        else if ("table.h6.w5".equals(code)) obj = "����1�s�ځu�f�@�E������p�v���e";
        else if ("table.h6.w6".equals(code)) obj = "����1�s�ځu�f�@�E������p�v�P��";
        else if ("table.h7.w2".equals(code)) obj = "����2�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h7.w3".equals(code)) obj = "����2�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h7.w4".equals(code)) obj = "����2�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h7.w5".equals(code)) obj = "����2�s�ځu�ӌ����쐬���v���e";
        else if ("table.h8.w4".equals(code)) obj = "����2�s�ځu�ӌ������t���v���o��";
        else if ("table.h8.w5".equals(code)) obj = "����2�s�ځu�ӌ������t���v���e";
        else if ("table.h10.w2".equals(code)) obj = "����2�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h10.w3".equals(code)) obj = "����2�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h10.w4".equals(code)) obj = "����2�s�ځu��ʁv���o��";
        else if ("table.h10.w5".equals(code)) obj = "����2�s�ځu��ʁv���e";
        else if ("table.h11.w2".equals(code)) obj = "����2�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h11.w3".equals(code)) obj = "����2�s�ځu��ی��Ҏ����v���e";
        else if ("table.h11.w4".equals(code)) obj = "����2�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h11.w5".equals(code)) obj = "����2�s�ځu�ӌ����쐬���v���e";
        else if ("table.h11.w6".equals(code)) obj = "����2�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h12.w4".equals(code)) obj = "����2�s�ځu�f�@�E������p�v���o��";
        else if ("table.h12.w5".equals(code)) obj = "����2�s�ځu�f�@�E������p�v���e";
        else if ("table.h12.w6".equals(code)) obj = "����2�s�ځu�f�@�E������p�v�P��";
        else if ("table.h13.w2".equals(code)) obj = "����3�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h13.w3".equals(code)) obj = "����3�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h13.w4".equals(code)) obj = "����3�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h13.w5".equals(code)) obj = "����3�s�ځu�ӌ����쐬���v���e";
        else if ("table.h14.w4".equals(code)) obj = "����3�s�ځu�ӌ������t���v���o��";
        else if ("table.h14.w5".equals(code)) obj = "����3�s�ځu�ӌ������t���v���e";
        else if ("table.h16.w2".equals(code)) obj = "����3�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h16.w3".equals(code)) obj = "����3�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h16.w4".equals(code)) obj = "����3�s�ځu��ʁv���o��";
        else if ("table.h16.w5".equals(code)) obj = "����3�s�ځu��ʁv���e";
        else if ("table.h17.w2".equals(code)) obj = "����3�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h17.w3".equals(code)) obj = "����3�s�ځu��ی��Ҏ����v���e";
        else if ("table.h17.w4".equals(code)) obj = "����3�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h17.w5".equals(code)) obj = "����3�s�ځu�ӌ����쐬���v���e";
        else if ("table.h17.w6".equals(code)) obj = "����3�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h18.w4".equals(code)) obj = "����3�s�ځu�f�@�E������p�v���o��";
        else if ("table.h18.w5".equals(code)) obj = "����3�s�ځu�f�@�E������p�v���e";
        else if ("table.h18.w6".equals(code)) obj = "����3�s�ځu�f�@�E������p�v�P��";
        else if ("table.h19.w2".equals(code)) obj = "����4�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h19.w3".equals(code)) obj = "����4�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h19.w4".equals(code)) obj = "����4�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h19.w5".equals(code)) obj = "����4�s�ځu�ӌ����쐬���v���e";
        else if ("table.h20.w4".equals(code)) obj = "����4�s�ځu�ӌ������t���v���o��";
        else if ("table.h20.w5".equals(code)) obj = "����4�s�ځu�ӌ������t���v���e";
        else if ("table.h22.w2".equals(code)) obj = "����4�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h22.w3".equals(code)) obj = "����4�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h22.w4".equals(code)) obj = "����4�s�ځu��ʁv���o��";
        else if ("table.h22.w5".equals(code)) obj = "����4�s�ځu��ʁv���e";
        else if ("table.h23.w2".equals(code)) obj = "����4�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h23.w3".equals(code)) obj = "����4�s�ځu��ی��Ҏ����v���e";
        else if ("table.h23.w4".equals(code)) obj = "����4�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h23.w5".equals(code)) obj = "����4�s�ځu�ӌ����쐬���v���e";
        else if ("table.h23.w6".equals(code)) obj = "����4�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h24.w4".equals(code)) obj = "����4�s�ځu�f�@�E������p�v���o��";
        else if ("table.h24.w5".equals(code)) obj = "����4�s�ځu�f�@�E������p�v���e";
        else if ("table.h24.w6".equals(code)) obj = "����4�s�ځu�f�@�E������p�v�P��";
        else if ("table.h25.w2".equals(code)) obj = "����5�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h25.w3".equals(code)) obj = "����5�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h25.w4".equals(code)) obj = "����5�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h25.w5".equals(code)) obj = "����5�s�ځu�ӌ����쐬���v���e";
        else if ("table.h26.w4".equals(code)) obj = "����5�s�ځu�ӌ������t���v���o��";
        else if ("table.h26.w5".equals(code)) obj = "����5�s�ځu�ӌ������t���v���e";
        else if ("table.h28.w2".equals(code)) obj = "����5�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h28.w3".equals(code)) obj = "����5�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h28.w4".equals(code)) obj = "����5�s�ځu��ʁv���o��";
        else if ("table.h28.w5".equals(code)) obj = "����5�s�ځu��ʁv���e";
        else if ("table.h29.w2".equals(code)) obj = "����5�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h29.w3".equals(code)) obj = "����5�s�ځu��ی��Ҏ����v���e";
        else if ("table.h29.w4".equals(code)) obj = "����5�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h29.w5".equals(code)) obj = "����5�s�ځu�ӌ����쐬���v���e";
        else if ("table.h29.w6".equals(code)) obj = "����5�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h30.w4".equals(code)) obj = "����5�s�ځu�f�@�E������p�v���o��";
        else if ("table.h30.w5".equals(code)) obj = "����5�s�ځu�f�@�E������p�v���e";
        else if ("table.h30.w6".equals(code)) obj = "����5�s�ځu�f�@�E������p�v�P��";
        else if ("table.h31.w2".equals(code)) obj = "����6�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h31.w3".equals(code)) obj = "����6�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h31.w4".equals(code)) obj = "����6�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h31.w5".equals(code)) obj = "����6�s�ځu�ӌ����쐬���v���e";
        else if ("table.h32.w4".equals(code)) obj = "����6�s�ځu�ӌ������t���v���o��";
        else if ("table.h32.w5".equals(code)) obj = "����6�s�ځu�ӌ������t���v���e";
        else if ("table.h34.w2".equals(code)) obj = "����6�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h34.w3".equals(code)) obj = "����6�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h34.w4".equals(code)) obj = "����6�s�ځu��ʁv���o��";
        else if ("table.h34.w5".equals(code)) obj = "����6�s�ځu��ʁv���e";
        else if ("table.h35.w2".equals(code)) obj = "����6�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h35.w3".equals(code)) obj = "����6�s�ځu��ی��Ҏ����v���e";
        else if ("table.h35.w4".equals(code)) obj = "����6�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h35.w5".equals(code)) obj = "����6�s�ځu�ӌ����쐬���v���e";
        else if ("table.h35.w6".equals(code)) obj = "����6�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h36.w4".equals(code)) obj = "����6�s�ځu�f�@�E������p�v���o��";
        else if ("table.h36.w5".equals(code)) obj = "����6�s�ځu�f�@�E������p�v���e";
        else if ("table.h36.w6".equals(code)) obj = "����6�s�ځu�f�@�E������p�v�P��";
        else if ("table.h37.w2".equals(code)) obj = "����7�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h37.w3".equals(code)) obj = "����7�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h37.w4".equals(code)) obj = "����7�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h37.w5".equals(code)) obj = "����7�s�ځu�ӌ����쐬���v���e";
        else if ("table.h38.w4".equals(code)) obj = "����7�s�ځu�ӌ������t���v���o��";
        else if ("table.h38.w5".equals(code)) obj = "����7�s�ځu�ӌ������t���v���e";
        else if ("table.h40.w2".equals(code)) obj = "����7�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h40.w3".equals(code)) obj = "����7�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h40.w4".equals(code)) obj = "����7�s�ځu��ʁv���o��";
        else if ("table.h40.w5".equals(code)) obj = "����7�s�ځu��ʁv���e";
        else if ("table.h41.w2".equals(code)) obj = "����7�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h41.w3".equals(code)) obj = "����7�s�ځu��ی��Ҏ����v���e";
        else if ("table.h41.w4".equals(code)) obj = "����7�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h41.w5".equals(code)) obj = "����7�s�ځu�ӌ����쐬���v���e";
        else if ("table.h41.w6".equals(code)) obj = "����7�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h42.w4".equals(code)) obj = "����7�s�ځu�f�@�E������p�v���o��";
        else if ("table.h42.w5".equals(code)) obj = "����7�s�ځu�f�@�E������p�v���e";
        else if ("table.h42.w6".equals(code)) obj = "����7�s�ځu�f�@�E������p�v�P��";
        else if ("table.h43.w2".equals(code)) obj = "����8�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h43.w3".equals(code)) obj = "����8�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h43.w4".equals(code)) obj = "����8�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h43.w5".equals(code)) obj = "����8�s�ځu�ӌ����쐬���v���e";
        else if ("table.h44.w4".equals(code)) obj = "����8�s�ځu�ӌ������t���v���o��";
        else if ("table.h44.w5".equals(code)) obj = "����8�s�ځu�ӌ������t���v���e";
        else if ("table.h46.w2".equals(code)) obj = "����8�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h46.w3".equals(code)) obj = "����8�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h46.w4".equals(code)) obj = "����8�s�ځu��ʁv���o��";
        else if ("table.h46.w5".equals(code)) obj = "����8�s�ځu��ʁv���e";
        else if ("table.h47.w2".equals(code)) obj = "����8�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h47.w3".equals(code)) obj = "����8�s�ځu��ی��Ҏ����v���e";
        else if ("table.h47.w4".equals(code)) obj = "����8�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h47.w5".equals(code)) obj = "����8�s�ځu�ӌ����쐬���v���e";
        else if ("table.h47.w6".equals(code)) obj = "����8�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h48.w4".equals(code)) obj = "����8�s�ځu�f�@�E������p�v���o��";
        else if ("table.h48.w5".equals(code)) obj = "����8�s�ځu�f�@�E������p�v���e";
        else if ("table.h48.w6".equals(code)) obj = "����8�s�ځu�f�@�E������p�v�P��";
        else if ("table.h49.w2".equals(code)) obj = "����9�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h49.w3".equals(code)) obj = "����9�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h49.w4".equals(code)) obj = "����9�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h49.w5".equals(code)) obj = "����9�s�ځu�ӌ����쐬���v���e";
        else if ("table.h50.w4".equals(code)) obj = "����9�s�ځu�ӌ������t���v���o��";
        else if ("table.h50.w5".equals(code)) obj = "����9�s�ځu�ӌ������t���v���e";
        else if ("table.h52.w2".equals(code)) obj = "����9�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h52.w3".equals(code)) obj = "����9�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h52.w4".equals(code)) obj = "����9�s�ځu��ʁv���o��";
        else if ("table.h52.w5".equals(code)) obj = "����9�s�ځu��ʁv���e";
        else if ("table.h53.w2".equals(code)) obj = "����9�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h53.w3".equals(code)) obj = "����9�s�ځu��ی��Ҏ����v���e";
        else if ("table.h53.w4".equals(code)) obj = "����9�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h53.w5".equals(code)) obj = "����9�s�ځu�ӌ����쐬���v���e";
        else if ("table.h53.w6".equals(code)) obj = "����9�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h54.w4".equals(code)) obj = "����9�s�ځu�f�@�E������p�v���o��";
        else if ("table.h54.w5".equals(code)) obj = "����9�s�ځu�f�@�E������p�v���e";
        else if ("table.h54.w6".equals(code)) obj = "����9�s�ځu�f�@�E������p�v�P��";
        else if ("table.h55.w2".equals(code)) obj = "����10�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h55.w3".equals(code)) obj = "����10�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h55.w4".equals(code)) obj = "����10�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h55.w5".equals(code)) obj = "����10�s�ځu�ӌ����쐬���v���e";
        else if ("table.h56.w4".equals(code)) obj = "����10�s�ځu�ӌ������t���v���o��";
        else if ("table.h56.w5".equals(code)) obj = "����10�s�ځu�ӌ������t���v���e";
        else if ("table.h58.w2".equals(code)) obj = "����10�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h58.w3".equals(code)) obj = "����10�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h58.w4".equals(code)) obj = "����10�s�ځu��ʁv���o��";
        else if ("table.h58.w5".equals(code)) obj = "����10�s�ځu��ʁv���e";
        else if ("table.h59.w2".equals(code)) obj = "����10�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h59.w3".equals(code)) obj = "����10�s�ځu��ی��Ҏ����v���e";
        else if ("table.h59.w4".equals(code)) obj = "����10�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h59.w5".equals(code)) obj = "����10�s�ځu�ӌ����쐬���v���e";
        else if ("table.h59.w6".equals(code)) obj = "����10�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h60.w4".equals(code)) obj = "����10�s�ځu�f�@�E������p�v���o��";
        else if ("table.h60.w5".equals(code)) obj = "����10�s�ځu�f�@�E������p�v���e";
        else if ("table.h60.w6".equals(code)) obj = "����10�s�ځu�f�@�E������p�v�P��";
        else if ("table.h61.w2".equals(code)) obj = "����11�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h61.w3".equals(code)) obj = "����11�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h61.w4".equals(code)) obj = "����11�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h61.w5".equals(code)) obj = "����11�s�ځu�ӌ����쐬���v���e";
        else if ("table.h62.w4".equals(code)) obj = "����11�s�ځu�ӌ������t���v���o��";
        else if ("table.h62.w5".equals(code)) obj = "����11�s�ځu�ӌ������t���v���e";
        else if ("table.h64.w2".equals(code)) obj = "����11�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h64.w3".equals(code)) obj = "����11�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h64.w4".equals(code)) obj = "����11�s�ځu��ʁv���o��";
        else if ("table.h64.w5".equals(code)) obj = "����11�s�ځu��ʁv���e";
        else if ("table.h65.w2".equals(code)) obj = "����11�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h65.w3".equals(code)) obj = "����11�s�ځu��ی��Ҏ����v���e";
        else if ("table.h65.w4".equals(code)) obj = "����11�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h65.w5".equals(code)) obj = "����11�s�ځu�ӌ����쐬���v���e";
        else if ("table.h65.w6".equals(code)) obj = "����11�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h66.w4".equals(code)) obj = "����11�s�ځu�f�@�E������p�v���o��";
        else if ("table.h66.w5".equals(code)) obj = "����11�s�ځu�f�@�E������p�v���e";
        else if ("table.h66.w6".equals(code)) obj = "����11�s�ځu�f�@�E������p�v�P��";
        else if ("table.h67.w2".equals(code)) obj = "����12�s�ځu��ی��Ҕԍ��v���o��";
        else if ("table.h67.w3".equals(code)) obj = "����12�s�ځu��ی��Ҕԍ��v���e";
        else if ("table.h67.w4".equals(code)) obj = "����12�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h67.w5".equals(code)) obj = "����12�s�ځu�ӌ����쐬���v���e";
        else if ("table.h68.w4".equals(code)) obj = "����12�s�ځu�ӌ������t���v���o��";
        else if ("table.h68.w5".equals(code)) obj = "����12�s�ځu�ӌ������t���v���e";
        else if ("table.h70.w2".equals(code)) obj = "����12�s�ځu�ӂ肪�ȁv���o��";
        else if ("table.h70.w3".equals(code)) obj = "����12�s�ځu�ӂ肪�ȁv���e";
        else if ("table.h70.w4".equals(code)) obj = "����12�s�ځu��ʁv���o��";
        else if ("table.h70.w5".equals(code)) obj = "����12�s�ځu��ʁv���e";
        else if ("table.h71.w2".equals(code)) obj = "����12�s�ځu��ی��Ҏ����v���o��";
        else if ("table.h71.w3".equals(code)) obj = "����12�s�ځu��ی��Ҏ����v���e";
        else if ("table.h71.w4".equals(code)) obj = "����12�s�ځu�ӌ����쐬���v���o��";
        else if ("table.h71.w5".equals(code)) obj = "����12�s�ځu�ӌ����쐬���v���e";
        else if ("table.h71.w6".equals(code)) obj = "����12�s�ځu�ӌ����쐬���v�P��";
        else if ("table.h72.w4".equals(code)) obj = "����12�s�ځu�f�@�E������p�v���o��";
        else if ("table.h72.w5".equals(code)) obj = "����12�s�ځu�f�@�E������p�v���e";
        else if ("table.h72.w6".equals(code)) obj = "����12�s�ځu�f�@�E������p�v�P��";
        else if ("atena".equals(code)) obj = "����";
        else if ("atena.h1.name".equals(code)) obj = "�������e";
        else if ("dateRange".equals(code)) obj = "�o�͔͈͓��t";
        else if ("dateRange.h1.fromY".equals(code)) obj = "�o�͔͈͓��t�u�J�n�N�v���e";
        else if ("dateRange.h1.fromM".equals(code)) obj = "�o�͔͈͓��t�u�J�n���v���e";
        else if ("dateRange.h1.fromD".equals(code)) obj = "�o�͔͈͓��t�u�J�n���v���e";
        else if ("dateRange.h1.w10".equals(code)) obj = "�o�͔͈͓��t�u�I���N�v���e";
        else if ("dateRange.h1.w12".equals(code)) obj = "�o�͔͈͓��t�u�I�����v���e";
        else if ("dateRange.h1.w14".equals(code)) obj = "�o�͔͈͓��t�u�I�����v���e";
        return obj;
    }

    /**
     * ���������v�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatSeikyuIchiranTotal(String code, Object obj){
        if ("pageNo".equals(code)) obj = "�y�[�W�ԍ�";
        else if ("Label1".equals(code)) obj = "�u���������v";
        else if ("atena".equals(code)) obj = "������ی��ҏ��";
        else if ("atena.h1.name".equals(code)) obj = "������ی��Җ�";
        else if ("dateRange".equals(code)) obj = "�o�͔͈͓��t���";
        else if ("dateRange.h1.fromY".equals(code)) obj = "�o�͔͈͓��t�E�J�n���E�N";
        else if ("dateRange.h1.w2".equals(code)) obj = "�u�N�v�@1";
        else if ("dateRange.h1.fromM".equals(code)) obj = "�o�͔͈͓��t�E�J�n���E��";
        else if ("dateRange.h1.w4".equals(code)) obj = "�u���v�@2";
        else if ("dateRange.h1.fromD".equals(code)) obj = "�o�͔͈͓��t�E�J�n���E��";
        else if ("dateRange.h1.w6".equals(code)) obj = "�u���v�@3";
        else if ("dateRange.h1.w8".equals(code)) obj = "�u����v";
        else if ("dateRange.h1.toY".equals(code)) obj = "�o�͔͈͓��t�E�I�����E�N";
        else if ("dateRange.h1.w10".equals(code)) obj = "�u�N�v�@2";
        else if ("dateRange.h1.toM".equals(code)) obj = "�o�͔͈͓��t�E�I�����E��";
        else if ("dateRange.h1.w12".equals(code)) obj = "�u���v�@2";
        else if ("dateRange.h1.toD".equals(code)) obj = "�o�͔͈͓��t�E�I�����E��";
        else if ("dateRange.h1.w14".equals(code)) obj = "�u���v�@2";
        else if ("totalCost".equals(code)) obj = "���z���";
        else if ("totalCost.h1.w1".equals(code)) obj = "�u�ӌ����쐬���v";
        else if ("totalCost.h1.cost".equals(code)) obj = "���z���E�ӌ����쐬��";
        else if ("totalCost.h1.w2".equals(code)) obj = "�u�~�v�@1";
        else if ("totalCost.h2.w1".equals(code)) obj = "�u�f�@�E������p�v";
        else if ("totalCost.h2.cost".equals(code)) obj = "���z���E�f�@�E������p";
        else if ("totalCost.h2.w2".equals(code)) obj = "�u�~�v�@2";
        else if ("totalCost.h3.w1".equals(code)) obj = "�u����ł̑��z�v";
        else if ("totalCost.h3.cost".equals(code)) obj = "���z���E����ł̑��z";
        else if ("totalCost.h3.w2".equals(code)) obj = "�u�~�v�@3";
        else if ("totalCost.h4.w1".equals(code)) obj = "�u���v�������z�v";
        else if ("totalCost.h4.cost".equals(code)) obj = "���z���E���v�������z";
        else if ("totalCost.h4.w2".equals(code)) obj = "�u�~�v�@4";
        else if ("totalCount".equals(code)) obj = "�����������";
        else if ("totalCount.h1.count".equals(code)) obj = "��������";
        else if ("totalCount.h1.w3".equals(code)) obj = "�u���v";
        else if ("bank".equals(code)) obj = "���Z�@�֏��";
        else if ("bank.h1.w1".equals(code)) obj = "�u�U������Z�@�ցv";
        else if ("bank.h2.w2".equals(code)) obj = "�u���Z�@�֖��v";
        else if ("bank.h2.w3".equals(code)) obj = "���Z�@�֏��E�U������Z�@�֖�";
        else if ("bank.h3.w2".equals(code)) obj = "�u�x�X���v";
        else if ("bank.h3.w3".equals(code)) obj = "���Z�@�֏��E�x�X��";
        else if ("bank.h4.w2".equals(code)) obj = "�������";
        else if ("bank.h4.w3".equals(code)) obj = "���Z�@�֏��E�������";
        else if ("bank.h5.w2".equals(code)) obj = "�����ԍ�";
        else if ("bank.h5.w3".equals(code)) obj = "���Z�@�֏��E�����ԍ�";
        else if ("bank.h6.w2".equals(code)) obj = "���`�l";
        else if ("bank.h6.w3".equals(code)) obj = "���Z�@�֏��E���`�l";
        return obj;
    }

    /**
     * �厡��ӌ����쐬���E����������(����)���̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatSoukatsusho(String code, Object obj){
        if ("dateRange".equals(code)) obj = "�����Ώۊ��ԁ@�g";
        else if ("dateRange.h1.fromY".equals(code)) obj = "�����Ώۊ��ԁ@�J�n�N";
        else if ("dateRange.h1.w2".equals(code)) obj = "�����Ώۊ��ԁ@�J�n�N���o��";
        else if ("dateRange.h1.fromM".equals(code)) obj = "�����Ώۊ��ԁ@�J�n��";
        else if ("dateRange.h1.w4".equals(code)) obj = "�����Ώۊ��ԁ@�J�n�����o��";
        else if ("dateRange.h1.fromD".equals(code)) obj = "�����Ώۊ��ԁ@�J�n��";
        else if ("dateRange.h1.w6".equals(code)) obj = "�����Ώۊ��ԁ@�J�n�����o��";
        else if ("dateRange.h1.w8".equals(code)) obj = "�����Ώۊ��ԁ@����";
        else if ("dateRange.h1.toY".equals(code)) obj = "�����Ώۊ��ԁ@�I���N��";
        else if ("dateRange.h1.w11".equals(code)) obj = "�����Ώۊ��ԁ@�I���N�����o��";
        else if ("dateRange.h1.toM".equals(code)) obj = "�����Ώۊ��ԁ@�I����";
        else if ("dateRange.h1.w13".equals(code)) obj = "�����Ώۊ��ԁ@�I�������o��";
        else if ("dateRange.h1.toD".equals(code)) obj = "�����Ώۊ��ԁ@�I����";
        else if ("dateRange.h1.w15".equals(code)) obj = "�����Ώۊ��ԁ@�I�������o��";
        else if ("dateRange.h1.w16".equals(code)) obj = "�����Ώۊ��ԁ@�I�����o��";
        else if ("Shape1".equals(code)) obj = "�����Ώۊ��ԁ@����";
        else if ("lblTitle".equals(code)) obj = "���ی� �厡��ӌ����쐬���E����������(����)���@�^�C�g��";
        else if ("prtDate".equals(code)) obj = "�쐬�N�����@�g";
        else if ("prtDate.h1.era".equals(code)) obj = "�쐬�N�����@����";
        else if ("prtDate.h1.y".equals(code)) obj = "�쐬�N�����@�N";
        else if ("prtDate.h1.w3".equals(code)) obj = "�쐬�N�����@�N���o��";
        else if ("prtDate.h1.m".equals(code)) obj = "�쐬�N�����@��";
        else if ("prtDate.h1.w5".equals(code)) obj = "�쐬�N�����@�����o��";
        else if ("prtDate.h1.d".equals(code)) obj = "�쐬�N�����@��";
        else if ("prtDate.h1.w7".equals(code)) obj = "�쐬�N�����@�����o��";
        else if ("atena".equals(code)) obj = "�ی��ҁ@�����@�g";
        else if ("atena.h1.name".equals(code)) obj = "�ی��ҁ@����";
        else if ("hospAdd".equals(code)) obj = "��Ë@�֏��@�g";
        else if ("hospAdd.address.w1".equals(code)) obj = "��Ë@�֏Z���@���o��";
        else if ("hospAdd.address.w2".equals(code)) obj = "��Ë@�֏Z��";
        else if ("hospAdd.name.w1".equals(code)) obj = "��Ë@�֖��́@���o��";
        else if ("hospAdd.name.w2".equals(code)) obj = "��Ë@�֖���";
        else if ("hospAdd.kaisetuname.w1".equals(code)) obj = "��Ë@�֊J�ݎҎ����@���o��";
        else if ("hospAdd.kaisetuname.w2".equals(code)) obj = "��Ë@�֊J�ݎҎ���";
        else if ("hospAdd.tel.w1".equals(code)) obj = "��Ë@�֓d�b�ԍ��@���o��";
        else if ("hospAdd.tel.w2".equals(code)) obj = "��Ë@�֓d�b�ԍ�";
        else if ("Label3".equals(code)) obj = "��Ë@�ց@��";
        else if ("hospCd".equals(code)) obj = "��Ë@�փR�[�h�@�g";
        else if ("hospCd.h1.w2".equals(code)) obj = "��Ë@�փR�[�h�@���o��";
        else if ("hospCd.h2.w2".equals(code)) obj = "��Ë@�փR�[�h";
        else if ("lblSeikyu".equals(code)) obj = "�u���L�̒ʂ萿�����܂��B�v";
        else if ("Label1".equals(code)) obj = "�ӌ����쐬���@���o��";
        else if ("ikenSeikyuTotal".equals(code)) obj = "�ӌ����쐬���E�������� �g";
        else if ("ikenSeikyuTotal.h1.w1".equals(code)) obj = "�ӌ����쐬���E���������@���o��";
        else if ("ikenSeikyuTotal.h2.w1".equals(code)) obj = "�ӌ����쐬���E��������";
        else if ("ikenSeikyuTotal.h2.w2".equals(code)) obj = "�ӌ����쐬���E���������@���o���Q";
        else if ("ikenCost".equals(code)) obj = "�ӌ����쐬���E�������z�@�g";
        else if ("ikenCost.h1.w1".equals(code)) obj = "�ӌ����쐬���E�������z�@���o��";
        else if ("ikenCost.h2.w1".equals(code)) obj = "�ӌ����쐬���E�������z";
        else if ("ikenCost.h2.w2".equals(code)) obj = "�ӌ����쐬���E�������z�@���o���Q";
        else if ("Label2".equals(code)) obj = "�������@���o��";
        else if ("kensaSeikyuTotal".equals(code)) obj = "�������E���������@�g";
        else if ("kensaSeikyuTotal.h1.w1".equals(code)) obj = "�������E�����������o��";
        else if ("kensaSeikyuTotal.h2.w1".equals(code)) obj = "�������E��������";
        else if ("kensaSeikyuTotal.h2.w2".equals(code)) obj = "�������E���������@���o���Q";
        else if ("kensaCost".equals(code)) obj = "�������E�������z�@�g";
        else if ("kensaCost.h1.w1".equals(code)) obj = "�������E�������z�@���o��";
        else if ("kensaCost.h2.w1".equals(code)) obj = "�������E�������z";
        else if ("kensaCost.h2.w2".equals(code)) obj = "�������E�������z�@���o���Q";
        else if ("tax".equals(code)) obj = "����ł̑��z�@�g";
        else if ("tax.h1.w1".equals(code)) obj = "����ł̑��z�@���o��";
        else if ("tax.h2.w1".equals(code)) obj = "����ł̑��z";
        else if ("tax.h2.w2".equals(code)) obj = "����ł̑��z�@���o���Q";
        else if ("seikyuTotal".equals(code)) obj = "���v�������z�@�g";
        else if ("seikyuTotal.h1.w1".equals(code)) obj = "���v�������z�@���o��";
        else if ("seikyuTotal.h2.w1".equals(code)) obj = "���v�������z";
        else if ("seikyuTotal.h2.w2".equals(code)) obj = "���v�������z�@���o���Q";
        else if ("bank".equals(code)) obj = "�U������@�g";
        else if ("bank.h1.w1".equals(code)) obj = "�U������Z�@�ց@���o��";
        else if ("bank.h1.w2".equals(code)) obj = "�U������Z�@��";
        else if ("bank.h3.w1".equals(code)) obj = "���Z�@�֖��@���o��";
        else if ("bank.h3.w2".equals(code)) obj = "���Z�@�֖�";
        else if ("bank.h4.w1".equals(code)) obj = "�x�X���@���o��";
        else if ("bank.h4.w2".equals(code)) obj = "�x�X��";
        else if ("bank.h5.w1".equals(code)) obj = "������ށ@���o��";
        else if ("bank.h5.w2".equals(code)) obj = "�������";
        else if ("bank.h6.w1".equals(code)) obj = "�����ԍ��@���o��";
        else if ("bank.h6.w2".equals(code)) obj = "�����ԍ�";
        else if ("bank.h7.w1".equals(code)) obj = "���`�l�@���o��";
        else if ("bank.h7.w2".equals(code)) obj = "���`�l";
        return obj;
    }

    /**
     * �厡��ӌ����쐬������(����)���̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatMeisaisho(String code, Object obj){
        if ("lblTitle".equals(code)) obj = "�^�C�g��";
        else if ("printData".equals(code)) obj = "�����";
        else if ("atena".equals(code)) obj = "������ی��Җ�";
        else if ("hiyokensya".equals(code)) obj = "��ی��ҏ��";
        else if ("hiyokensya.h1.w1".equals(code)) obj = "��ی��ҏ��@���o��";
        else if ("hiyokensya.h1.w2".equals(code)) obj = "��ی��ҏ��E��ی��Ҕԍ��@���o��";
        else if ("hiyokensya.h1.w4".equals(code)) obj = "��ی��ҏ��E��ی��Ҕԍ�";
        else if ("hiyokensya.h3.w2".equals(code)) obj = "��ی��ҏ��E�ӂ肪�ȁ@���o��";
        else if ("hiyokensya.h3.w4".equals(code)) obj = "��ی��ҏ��E�ӂ肪��";
        else if ("hiyokensya.h4.w2".equals(code)) obj = "��ی��ҏ��E�����@���o��";
        else if ("hiyokensya.h4.w4".equals(code)) obj = "��ی��ҏ��E����";
        else if ("hiyokensya.h5.w2".equals(code)) obj = "��ی��ҏ��E���N�����@���o��1";
        else if ("hiyokensya.h6.w2".equals(code)) obj = "��ی��ҏ��E���N�����@���o��2";
        else if ("hiyokensya.h6.w4".equals(code)) obj = "��ی��ҏ��E���N����";
        else if ("hiyokensya.h5.w5".equals(code)) obj = "��ی��ҏ��E���ʁ@���o��1";
        else if ("hiyokensya.h6.w5".equals(code)) obj = "��ی��ҏ��E���ʁ@���o��2";
        else if ("hiyokensya.h5.w6".equals(code)) obj = "��ی��ҏ��E���ʁ@�u�j�v";
        else if ("hiyokensya.h6.w6".equals(code)) obj = "��ی��ҏ��E���ʁ@�u���v";
        else if ("Label2".equals(code)) obj = "��ی��ҏ��E���N�����E�N���@�u�����v";
        else if ("Label3".equals(code)) obj = "��ی��ҏ��E���N�����E�N���@�u�吳�v";
        else if ("Label4".equals(code)) obj = "��ی��ҏ��E���N�����E�N���@�u���a�v";
        else if ("meiji".equals(code)) obj = "��ی��ҏ��E���N�����E�N���E�V�F�C�v�@�u�����v";
        else if ("taisyo".equals(code)) obj = "��ی��ҏ��E���N�����E�N���E�V�F�C�v�@�u�吳�v";
        else if ("syowa".equals(code)) obj = "��ی��ҏ��E���N�����E�N���E�V�F�C�v�@�u���a�v";
        else if ("man".equals(code)) obj = "��ی��ҏ��E���ʁE�V�F�C�v�@�u�j�v";
        else if ("woman".equals(code)) obj = "��ی��ҏ��E���ʁE�V�F�C�v�@�u���v";
        else if ("hokensyaNo".equals(code)) obj = "�ی��ҏ��";
        else if ("hokensyaNo.h1.w1".equals(code)) obj = "�ی��ҏ��E�Ώ۔N��";
        else if ("hokensyaNo.h2.w1".equals(code)) obj = "�ی��ҏ��E�ی��Ҕԍ��@���o��";
        else if ("hokensyaNo.h2.w2".equals(code)) obj = "�ی��ҏ��E�ی��Ҕԍ�";
        else if ("iryoKikan".equals(code)) obj = "��Ë@�֏��";
        else if ("iryoKikan.h1.w1".equals(code)) obj = "��Ë@�֏��@���o��";
        else if ("iryoKikan.h1.w2".equals(code)) obj = "��Ë@�֏��E���Ə��ԍ��@���o��";
        else if ("iryoKikan.h1.w3".equals(code)) obj = "��Ë@�֏��E���Ə��ԍ�";
        else if ("iryoKikan.h2.w2".equals(code)) obj = "��Ë@�֏��E���Ə����́@���o��";
        else if ("iryoKikan.h2.w3".equals(code)) obj = "��Ë@�֏��E���Ə�����";
        else if ("iryoKikan.h4.w2".equals(code)) obj = "��Ë@�֏��E���ݒn�@���o��";
        else if ("iryoKikan.h3.w3".equals(code)) obj = "��Ë@�֏��E���ݒn�E�X�֔ԍ��@���o��";
        else if ("iryoKikan.h3.w4".equals(code)) obj = "��Ë@�֏��E���ݒn�E�X�֔ԍ�";
        else if ("iryoKikan.h4.w3".equals(code)) obj = "��Ë@�֏��E���ݒn";
        else if ("iryoKikan.h5.w3".equals(code)) obj = "��Ë@�֏��E�d�b�ԍ��@���o��";
        else if ("iryoKikan.h5.w5".equals(code)) obj = "��Ë@�֏��E�d�b�ԍ�";
        else if ("dateList".equals(code)) obj = "���t���";
        else if ("dateList.h1.w1".equals(code)) obj = "���t���E�쐬�˗����@���o��";
        else if ("dateList.h1.w2".equals(code)) obj = "���t���E�쐬�˗���";
        else if ("dateList.h1.w3".equals(code)) obj = "���t���E�˗��ԍ��@���o��";
        else if ("dateList.h1.w4".equals(code)) obj = "���t���E�˗��ԍ�";
        else if ("dateList.h2.w1".equals(code)) obj = "���t���E�ӌ����쐬���@���o��";
        else if ("dateList.h2.w2".equals(code)) obj = "���t���E�ӌ����쐬��";
        else if ("dateList.h2.w3".equals(code)) obj = "���t���E�ӌ������t���@���o��";
        else if ("dateList.h2.w4".equals(code)) obj = "���t���E�ӌ������t��";
        else if ("dateList.h1.w5".equals(code)) obj = "���t���E�ی��Ҋm�F�@���o��1";
        else if ("dateList.h1.w6".equals(code)) obj = "���t���E�ی��Ҋm�F�@���o��2";
        else if ("dateList.h1.w7".equals(code)) obj = "���t���E�ی��Ҋm�F�@�u���v";
        else if ("Grid1".equals(code)) obj = "�ӌ����쐬�����";
        else if ("Grid1.h1.w1".equals(code)) obj = "�ӌ����쐬�����@���o��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�ӌ����쐬�����E��ʁ@���o��";
        else if ("Grid1.h1.w3".equals(code)) obj = "�ӌ����쐬�����E���1";
        else if ("Grid1.h1.w4".equals(code)) obj = "�ӌ����쐬�����E���2";
        else if ("Grid1.h1.w5".equals(code)) obj = "�ӌ����쐬�����E���z�@���o��";
        else if ("Grid1.h1.w6".equals(code)) obj = "�ӌ����쐬�����E���z";
        else if ("Grid1.h1.w7".equals(code)) obj = "�ӌ����쐬�����E���z�@�P��";
        else if ("costList1".equals(code)) obj = "�f�@�E������p���";
        else if ("costList1.h1.w1".equals(code)) obj = "�f�@�E������p���@���o��";
        else if ("costList1.h1.w2".equals(code)) obj = "�f�@�E������p���E����@���o��";
        else if ("costList1.h1.point".equals(code)) obj = "�f�@�E������p���E�_���@���o��";
        else if ("costList1.h1.tekiyo".equals(code)) obj = "�f�@�E������p���E�E�v�@���o��";
        else if ("costList1.shosin.w3".equals(code)) obj = "�f�@�E������p���E���f�@���o��";
        else if ("costList1.shosin.point".equals(code)) obj = "�f�@�E������p���E���f�@�_��";
        else if ("costList1.shosin.tekiyo".equals(code)) obj = "�f�@�E������p���E���f�@�E�v";
        else if ("costList1.xray.w2".equals(code)) obj = "�f�@�E������p���E�����@���o��";
        else if ("costList1.xray.w4".equals(code)) obj = "�f�@�E������p���E�����P���w���B�e�@���o��";
        else if ("costList1.xray.point".equals(code)) obj = "�f�@�E������p���E�����P���w���B�e�@�_��";
        else if ("costList1.xray.tekiyo".equals(code)) obj = "�f�@�E������p���E�����P���w���B�e�@�E�v";
        else if ("costList1.bld_ippan.w4".equals(code)) obj = "�f�@�E������p���E���t��ʌ����@���o��";
        else if ("costList1.bld_ippan.point".equals(code)) obj = "�f�@�E������p���E���t��ʌ����@�_��";
        else if ("costList1.bld_ippan.tekiyo".equals(code)) obj = "�f�@�E������p���E���t��ʌ����@�E�v";
        else if ("costList1.bld_kagaku.w4".equals(code)) obj = "�f�@�E������p���E���t���w�����@���o��";
        else if ("costList1.bld_kagaku.point".equals(code)) obj = "�f�@�E������p���E���t���w�����@�_��";
        else if ("costList1.bld_kagaku.tekiyo".equals(code)) obj = "�f�@�E������p���E���t���w�����@�E�v";
        else if ("costList1.nyo.w4".equals(code)) obj = "�f�@�E������p���E�A����ʕ����萫����ʌ����@���o��";
        else if ("costList1.nyo.point".equals(code)) obj = "�f�@�E������p���E�A����ʕ����萫����ʌ����@�_��";
        else if ("costList1.nyo.tekiyo".equals(code)) obj = "�f�@�E������p���E�A����ʕ����萫����ʌ����@�E�v";
        else if ("costList1.total.w3".equals(code)) obj = "�f�@�E������p���E���v�@���o��";
        else if ("costList1.total.point".equals(code)) obj = "�f�@�E������p���E���v�@�_��";
        else if ("costList1.total.tekiyo".equals(code)) obj = "�f�@�E������p���E�_�����v�~10�~�@���o��";
        else if ("costList1.total.w7".equals(code)) obj = "�f�@�E������p���E�_�����v�~10�~�@���z";
        else if ("costList1.total.w8".equals(code)) obj = "�f�@�E������p���E�_�����v�~10�~�@�P��";
        else if ("bank".equals(code)) obj = "�U������Z�@�֏��";
        else if ("bank.h1.w1".equals(code)) obj = "�U������Z�@�֏��@���o��";
        else if ("bank.h3.w1".equals(code)) obj = "�U������Z�@�֏��E���Z�@�֖��@���o��";
        else if ("bank.h3.w2".equals(code)) obj = "�U������Z�@�֏��E���Z�@�֖�";
        else if ("bank.h4.w1".equals(code)) obj = "�U������Z�@�֏��E�x�X���@���o��";
        else if ("bank.h4.w2".equals(code)) obj = "�U������Z�@�֏��E�x�X��";
        else if ("bank.h5.w1".equals(code)) obj = "�U������Z�@�֏��E������ށ@���o��";
        else if ("bank.h5.w2".equals(code)) obj = "�U������Z�@�֏��E�������";
        else if ("bank.h6.w1".equals(code)) obj = "�U������Z�@�֏��E�����ԍ��@���o��";
        else if ("bank.h6.w2".equals(code)) obj = "�U������Z�@�֏��E�����ԍ�";
        else if ("bank.h7.w1".equals(code)) obj = "�U������Z�@�֏��E���`�l�@���o��";
        else if ("bank.h7.w2".equals(code)) obj = "�U������Z�@�֏��E���`�l";
        else if ("costList2".equals(code)) obj = "�����z���";
        else if ("costList2.h1.w1".equals(code)) obj = "�����z���@���o��";
        else if ("costList2.h1.w2".equals(code)) obj = "�����z���E�ӌ����쐬���@���o��";
        else if ("costList2.h1.cost".equals(code)) obj = "�����z���E�ӌ����쐬��";
        else if ("costList2.h1.w4".equals(code)) obj = "�����z���E�ӌ����쐬���@�P��";
        else if ("costList2.h2.w2".equals(code)) obj = "�����z���E�f�@�E������p�@���o��";
        else if ("costList2.h2.cost".equals(code)) obj = "�����z���E�f�@�E������p";
        else if ("costList2.h2.w4".equals(code)) obj = "�����z���E�f�@�E������p�@�P��";
        else if ("costList2.h3.w2".equals(code)) obj = "�����z���E����Ł@���o��";
        else if ("costList2.h3.cost".equals(code)) obj = "�����z���E�����";
        else if ("costList2.h3.w4".equals(code)) obj = "�����z���E����Ł@�P��";
        else if ("costList2.h4.w2".equals(code)) obj = "�����z���E���v�@���o��";
        else if ("costList2.h4.cost".equals(code)) obj = "�����z���E���v";
        else if ("costList2.h4.w4".equals(code)) obj = "�����z���E���v�@�P��";
        else if ("CostTotal".equals(code)) obj = "�x������z���";
        else if ("CostTotal.h1.w1".equals(code)) obj = "�x������z���E�x������z�@���o��";
        else if ("CostTotal.h1.w2".equals(code)) obj = "�x������z���E�x������z";
        else if ("CostTotal.h1.w3".equals(code)) obj = "�x������z���E�x������z�@�P��";
        else if ("Label1".equals(code)) obj = "�u���̗��ɂ͋L�����Ȃ��ŉ������v";
        return obj;
    }

    /**
     * �o�^���҈ꗗ�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatPatientList(String code, Object obj){
        if ("date".equals(code)) obj = "�쐬��";
        else if ("page".equals(code)) obj = "�y�[�W��";
        else if ("table".equals(code)) obj = "�o�^���҈ꗗ�@�g";
        else if ("table.h1.w1".equals(code)) obj = "�񖼁u����ID�v";
        else if ("table.h1.w2".equals(code)) obj = "�񖼁u�����v";
        else if ("table.h1.w3".equals(code)) obj = "�񖼁u�ӂ肪�ȁv";
        else if ("table.h1.w4".equals(code)) obj = "�񖼁u���ʁv";
        else if ("table.h1.w5".equals(code)) obj = "�񖼁u�N��v";
        else if ("table.h1.w6".equals(code)) obj = "�񖼁u���N�����v";
        else if ("table.h1.w7".equals(code)) obj = "�񖼁u�ŏI�f�@���v";
        else if ("table.h1.w8".equals(code)) obj = "�񖼁u�ŐV�ӌ����L�����v";
        else if ("table.h1.w9".equals(code)) obj = "�񖼁u�ŐV�w�����L�����v";
        else if ("table.h2.w1".equals(code)) obj = "1�s�ځu���҂h�c�v";
        else if ("table.h2.w2".equals(code)) obj = "1�s�ځu�����v";
        else if ("table.h2.w3".equals(code)) obj = "1�s�ځu�ӂ肪�ȁv";
        else if ("table.h2.w4".equals(code)) obj = "1�s�ځu���ʁv";
        else if ("table.h2.w5".equals(code)) obj = "1�s�ځu�N��v";
        else if ("table.h2.w6".equals(code)) obj = "1�s�ځu���N�����v";
        else if ("table.h2.w7".equals(code)) obj = "1�s�ځu�ŏI�f�@���v";
        else if ("table.h2.w8".equals(code)) obj = "1�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h2.w9".equals(code)) obj = "1�s�ځu�ŐV�w�����L�����v";
        else if ("table.h3.w1".equals(code)) obj = "2�s�ځu���҂h�c�v";
        else if ("table.h3.w2".equals(code)) obj = "2�s�ځu�����v";
        else if ("table.h3.w3".equals(code)) obj = "2�s�ځu�ӂ肪�ȁv";
        else if ("table.h3.w4".equals(code)) obj = "2�s�ځu���ʁv";
        else if ("table.h3.w5".equals(code)) obj = "2�s�ځu�N��v";
        else if ("table.h3.w6".equals(code)) obj = "2�s�ځu���N�����v";
        else if ("table.h3.w7".equals(code)) obj = "2�s�ځu�ŏI�f�@���v";
        else if ("table.h3.w8".equals(code)) obj = "2�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h3.w9".equals(code)) obj = "2�s�ځu�ŐV�w�����L�����v";
        else if ("table.h4.w1".equals(code)) obj = "3�s�ځu���҂h�c�v";
        else if ("table.h4.w2".equals(code)) obj = "3�s�ځu�����v";
        else if ("table.h4.w3".equals(code)) obj = "3�s�ځu�ӂ肪�ȁv";
        else if ("table.h4.w4".equals(code)) obj = "3�s�ځu���ʁv";
        else if ("table.h4.w5".equals(code)) obj = "3�s�ځu�N��v";
        else if ("table.h4.w6".equals(code)) obj = "3�s�ځu���N�����v";
        else if ("table.h4.w7".equals(code)) obj = "3�s�ځu�ŏI�f�@���v";
        else if ("table.h4.w8".equals(code)) obj = "3�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h4.w9".equals(code)) obj = "3�s�ځu�ŐV�w�����L�����v";
        else if ("table.h5.w1".equals(code)) obj = "4�s�ځu���҂h�c�v";
        else if ("table.h5.w2".equals(code)) obj = "4�s�ځu�����v";
        else if ("table.h5.w3".equals(code)) obj = "4�s�ځu�ӂ肪�ȁv";
        else if ("table.h5.w4".equals(code)) obj = "4�s�ځu���ʁv";
        else if ("table.h5.w5".equals(code)) obj = "4�s�ځu�N��v";
        else if ("table.h5.w6".equals(code)) obj = "4�s�ځu���N�����v";
        else if ("table.h5.w7".equals(code)) obj = "4�s�ځu�ŏI�f�@���v";
        else if ("table.h5.w8".equals(code)) obj = "4�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h5.w9".equals(code)) obj = "4�s�ځu�ŐV�w�����L�����v";
        else if ("table.h6.w1".equals(code)) obj = "5�s�ځu���҂h�c�v";
        else if ("table.h6.w2".equals(code)) obj = "5�s�ځu�����v";
        else if ("table.h6.w3".equals(code)) obj = "5�s�ځu�ӂ肪�ȁv";
        else if ("table.h6.w4".equals(code)) obj = "5�s�ځu���ʁv";
        else if ("table.h6.w5".equals(code)) obj = "5�s�ځu�N��v";
        else if ("table.h6.w6".equals(code)) obj = "5�s�ځu���N�����v";
        else if ("table.h6.w7".equals(code)) obj = "5�s�ځu�ŏI�f�@���v";
        else if ("table.h6.w8".equals(code)) obj = "5�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h6.w9".equals(code)) obj = "5�s�ځu�ŐV�w�����L�����v";
        else if ("table.h7.w1".equals(code)) obj = "6�s�ځu���҂h�c�v";
        else if ("table.h7.w2".equals(code)) obj = "6�s�ځu�����v";
        else if ("table.h7.w3".equals(code)) obj = "6�s�ځu�ӂ肪�ȁv";
        else if ("table.h7.w4".equals(code)) obj = "6�s�ځu���ʁv";
        else if ("table.h7.w5".equals(code)) obj = "6�s�ځu�N��v";
        else if ("table.h7.w6".equals(code)) obj = "6�s�ځu���N�����v";
        else if ("table.h7.w7".equals(code)) obj = "6�s�ځu�ŏI�f�@���v";
        else if ("table.h7.w8".equals(code)) obj = "6�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h7.w9".equals(code)) obj = "6�s�ځu�ŐV�w�����L�����v";
        else if ("table.h8.w1".equals(code)) obj = "7�s�ځu���҂h�c�v";
        else if ("table.h8.w2".equals(code)) obj = "7�s�ځu�����v";
        else if ("table.h8.w3".equals(code)) obj = "7�s�ځu�ӂ肪�ȁv";
        else if ("table.h8.w4".equals(code)) obj = "7�s�ځu���ʁv";
        else if ("table.h8.w5".equals(code)) obj = "7�s�ځu�N��v";
        else if ("table.h8.w6".equals(code)) obj = "7�s�ځu���N�����v";
        else if ("table.h8.w7".equals(code)) obj = "7�s�ځu�ŏI�f�@���v";
        else if ("table.h8.w8".equals(code)) obj = "7�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h8.w9".equals(code)) obj = "7�s�ځu�ŐV�w�����L�����v";
        else if ("table.h9.w1".equals(code)) obj = "8�s�ځu���҂h�c�v";
        else if ("table.h9.w2".equals(code)) obj = "8�s�ځu�����v";
        else if ("table.h9.w3".equals(code)) obj = "8�s�ځu�ӂ肪�ȁv";
        else if ("table.h9.w4".equals(code)) obj = "8�s�ځu���ʁv";
        else if ("table.h9.w5".equals(code)) obj = "8�s�ځu�N��v";
        else if ("table.h9.w6".equals(code)) obj = "8�s�ځu���N�����v";
        else if ("table.h9.w7".equals(code)) obj = "8�s�ځu�ŏI�f�@���v";
        else if ("table.h9.w8".equals(code)) obj = "8�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h9.w9".equals(code)) obj = "8�s�ځu�ŐV�w�����L�����v";
        else if ("table.h10.w1".equals(code)) obj = "9�s�ځu���҂h�c�v";
        else if ("table.h10.w2".equals(code)) obj = "9�s�ځu�����v";
        else if ("table.h10.w3".equals(code)) obj = "9�s�ځu�ӂ肪�ȁv";
        else if ("table.h10.w4".equals(code)) obj = "9�s�ځu���ʁv";
        else if ("table.h10.w5".equals(code)) obj = "9�s�ځu�N��v";
        else if ("table.h10.w6".equals(code)) obj = "9�s�ځu���N�����v";
        else if ("table.h10.w7".equals(code)) obj = "9�s�ځu�ŏI�f�@���v";
        else if ("table.h10.w8".equals(code)) obj = "9�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h10.w9".equals(code)) obj = "9�s�ځu�ŐV�w�����L�����v";
        else if ("table.h11.w1".equals(code)) obj = "10�s�ځu���҂h�c�v";
        else if ("table.h11.w2".equals(code)) obj = "10�s�ځu�����v";
        else if ("table.h11.w3".equals(code)) obj = "10�s�ځu�ӂ肪�ȁv";
        else if ("table.h11.w4".equals(code)) obj = "10�s�ځu���ʁv";
        else if ("table.h11.w5".equals(code)) obj = "10�s�ځu�N��v";
        else if ("table.h11.w6".equals(code)) obj = "10�s�ځu���N�����v";
        else if ("table.h11.w7".equals(code)) obj = "10�s�ځu�ŏI�f�@���v";
        else if ("table.h11.w8".equals(code)) obj = "10�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h11.w9".equals(code)) obj = "10�s�ځu�ŐV�w�����L�����v";
        else if ("table.h12.w1".equals(code)) obj = "11�s�ځu���҂h�c�v";
        else if ("table.h12.w2".equals(code)) obj = "11�s�ځu�����v";
        else if ("table.h12.w3".equals(code)) obj = "11�s�ځu�ӂ肪�ȁv";
        else if ("table.h12.w4".equals(code)) obj = "11�s�ځu���ʁv";
        else if ("table.h12.w5".equals(code)) obj = "11�s�ځu�N��v";
        else if ("table.h12.w6".equals(code)) obj = "11�s�ځu���N�����v";
        else if ("table.h12.w7".equals(code)) obj = "11�s�ځu�ŏI�f�@���v";
        else if ("table.h12.w8".equals(code)) obj = "11�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h12.w9".equals(code)) obj = "11�s�ځu�ŐV�w�����L�����v";
        else if ("table.h13.w1".equals(code)) obj = "12�s�ځu���҂h�c�v";
        else if ("table.h13.w2".equals(code)) obj = "12�s�ځu�����v";
        else if ("table.h13.w3".equals(code)) obj = "12�s�ځu�ӂ肪�ȁv";
        else if ("table.h13.w4".equals(code)) obj = "12�s�ځu���ʁv";
        else if ("table.h13.w5".equals(code)) obj = "12�s�ځu�N��v";
        else if ("table.h13.w6".equals(code)) obj = "12�s�ځu���N�����v";
        else if ("table.h13.w7".equals(code)) obj = "12�s�ځu�ŏI�f�@���v";
        else if ("table.h13.w8".equals(code)) obj = "12�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h13.w9".equals(code)) obj = "12�s�ځu�ŐV�w�����L�����v";
        else if ("table.h14.w1".equals(code)) obj = "13�s�ځu���҂h�c�v";
        else if ("table.h14.w2".equals(code)) obj = "13�s�ځu�����v";
        else if ("table.h14.w3".equals(code)) obj = "13�s�ځu�ӂ肪�ȁv";
        else if ("table.h14.w4".equals(code)) obj = "13�s�ځu���ʁv";
        else if ("table.h14.w5".equals(code)) obj = "13�s�ځu�N��v";
        else if ("table.h14.w6".equals(code)) obj = "13�s�ځu���N�����v";
        else if ("table.h14.w7".equals(code)) obj = "13�s�ځu�ŏI�f�@���v";
        else if ("table.h14.w8".equals(code)) obj = "13�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h14.w9".equals(code)) obj = "13�s�ځu�ŐV�w�����L�����v";
        else if ("table.h15.w1".equals(code)) obj = "14�s�ځu���҂h�c�v";
        else if ("table.h15.w2".equals(code)) obj = "14�s�ځu�����v";
        else if ("table.h15.w3".equals(code)) obj = "14�s�ځu�ӂ肪�ȁv";
        else if ("table.h15.w4".equals(code)) obj = "14�s�ځu���ʁv";
        else if ("table.h15.w5".equals(code)) obj = "14�s�ځu�N��v";
        else if ("table.h15.w6".equals(code)) obj = "14�s�ځu���N�����v";
        else if ("table.h15.w7".equals(code)) obj = "14�s�ځu�ŏI�f�@���v";
        else if ("table.h15.w8".equals(code)) obj = "14�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h15.w9".equals(code)) obj = "14�s�ځu�ŐV�w�����L�����v";
        else if ("table.h16.w1".equals(code)) obj = "15�s�ځu���҂h�c�v";
        else if ("table.h16.w2".equals(code)) obj = "15�s�ځu�����v";
        else if ("table.h16.w3".equals(code)) obj = "15�s�ځu�ӂ肪�ȁv";
        else if ("table.h16.w4".equals(code)) obj = "15�s�ځu���ʁv";
        else if ("table.h16.w5".equals(code)) obj = "15�s�ځu�N��v";
        else if ("table.h16.w6".equals(code)) obj = "15�s�ځu���N�����v";
        else if ("table.h16.w7".equals(code)) obj = "15�s�ځu�ŏI�f�@���v";
        else if ("table.h16.w8".equals(code)) obj = "15�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h16.w9".equals(code)) obj = "15�s�ځu�ŐV�w�����L�����v";
        else if ("table.h17.w1".equals(code)) obj = "16�s�ځu���҂h�c�v";
        else if ("table.h17.w2".equals(code)) obj = "16�s�ځu�����v";
        else if ("table.h17.w3".equals(code)) obj = "16�s�ځu�ӂ肪�ȁv";
        else if ("table.h17.w4".equals(code)) obj = "16�s�ځu���ʁv";
        else if ("table.h17.w5".equals(code)) obj = "16�s�ځu�N��v";
        else if ("table.h17.w6".equals(code)) obj = "16�s�ځu���N�����v";
        else if ("table.h17.w7".equals(code)) obj = "16�s�ځu�ŏI�f�@���v";
        else if ("table.h17.w8".equals(code)) obj = "16�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h17.w9".equals(code)) obj = "16�s�ځu�ŐV�w�����L�����v";
        else if ("table.h18.w1".equals(code)) obj = "17�s�ځu���҂h�c�v";
        else if ("table.h18.w2".equals(code)) obj = "17�s�ځu�����v";
        else if ("table.h18.w3".equals(code)) obj = "17�s�ځu�ӂ肪�ȁv";
        else if ("table.h18.w4".equals(code)) obj = "17�s�ځu���ʁv";
        else if ("table.h18.w5".equals(code)) obj = "17�s�ځu�N��v";
        else if ("table.h18.w6".equals(code)) obj = "17�s�ځu���N�����v";
        else if ("table.h18.w7".equals(code)) obj = "17�s�ځu�ŏI�f�@���v";
        else if ("table.h18.w8".equals(code)) obj = "17�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h18.w9".equals(code)) obj = "17�s�ځu�ŐV�w�����L�����v";
        else if ("table.h19.w1".equals(code)) obj = "18�s�ځu���҂h�c�v";
        else if ("table.h19.w2".equals(code)) obj = "18�s�ځu�����v";
        else if ("table.h19.w3".equals(code)) obj = "18�s�ځu�ӂ肪�ȁv";
        else if ("table.h19.w4".equals(code)) obj = "18�s�ځu���ʁv";
        else if ("table.h19.w5".equals(code)) obj = "18�s�ځu�N��v";
        else if ("table.h19.w6".equals(code)) obj = "18�s�ځu���N�����v";
        else if ("table.h19.w7".equals(code)) obj = "18�s�ځu�ŏI�f�@���v";
        else if ("table.h19.w8".equals(code)) obj = "18�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h19.w9".equals(code)) obj = "18�s�ځu�ŐV�w�����L�����v";
        else if ("table.h20.w1".equals(code)) obj = "19�s�ځu���҂h�c�v";
        else if ("table.h20.w2".equals(code)) obj = "19�s�ځu�����v";
        else if ("table.h20.w3".equals(code)) obj = "19�s�ځu�ӂ肪�ȁv";
        else if ("table.h20.w4".equals(code)) obj = "19�s�ځu���ʁv";
        else if ("table.h20.w5".equals(code)) obj = "19�s�ځu�N��v";
        else if ("table.h20.w6".equals(code)) obj = "19�s�ځu���N�����v";
        else if ("table.h20.w7".equals(code)) obj = "19�s�ځu�ŏI�f�@���v";
        else if ("table.h20.w8".equals(code)) obj = "19�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h20.w9".equals(code)) obj = "19�s�ځu�ŐV�w�����L�����v";
        else if ("table.h21.w1".equals(code)) obj = "20�s�ځu���҂h�c�v";
        else if ("table.h21.w2".equals(code)) obj = "20�s�ځu�����v";
        else if ("table.h21.w3".equals(code)) obj = "20�s�ځu�ӂ肪�ȁv";
        else if ("table.h21.w4".equals(code)) obj = "20�s�ځu���ʁv";
        else if ("table.h21.w5".equals(code)) obj = "20�s�ځu�N��v";
        else if ("table.h21.w6".equals(code)) obj = "20�s�ځu���N�����v";
        else if ("table.h21.w7".equals(code)) obj = "20�s�ځu�ŏI�f�@���v";
        else if ("table.h21.w8".equals(code)) obj = "20�s�ځu�ŐV�ӌ����L�����v";
        else if ("table.h21.w9".equals(code)) obj = "20�s�ځu�ŐV�w�����L�����v";
        else if ("title".equals(code)) obj = "�o�^���҈ꗗ�E���o���@�g";
        else if ("title.h1.w1".equals(code)) obj = "�u�o�^���҈ꗗ�v�@���o��";
        // 2006/08/25
        // ��t�ӌ����Ή��J�����ǉ�
        else if ("table.h1.w10".equals(code)) obj = "�񖼁u�ŐV��t�ӌ����L�����v";
        else if ("table.h2.w10".equals(code)) obj = "1�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h3.w10".equals(code)) obj = "2�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h4.w10".equals(code)) obj = "3�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h5.w10".equals(code)) obj = "4�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h6.w10".equals(code)) obj = "5�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h7.w10".equals(code)) obj = "6�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h8.w10".equals(code)) obj = "7�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h9.w10".equals(code)) obj = "8�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h10.w10".equals(code)) obj = "9�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h11.w10".equals(code)) obj = "10�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h12.w10".equals(code)) obj = "11�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h13.w10".equals(code)) obj = "12�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h14.w10".equals(code)) obj = "13�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h15.w10".equals(code)) obj = "14�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h16.w10".equals(code)) obj = "15�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h17.w10".equals(code)) obj = "16�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h18.w10".equals(code)) obj = "17�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h19.w10".equals(code)) obj = "18�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h20.w10".equals(code)) obj = "19�s�ځu�ŐV��t�ӌ����L�����v";
        else if ("table.h21.w10".equals(code)) obj = "20�s�ځu�ŐV��t�ӌ����L�����v";
        return obj;
    }

    /**
     * �����Ώۈӌ����ꗗ�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatSeikyuIkenshoIchiran(String code, Object obj){
        if ("lblTitle".equals(code)) obj = "�u�����Ώۈӌ����ꗗ�v";
        else if ("Shape1".equals(code)) obj = "�u�����Ώۈӌ����ꗗ�v�@����";
        else if ("hokensya".equals(code)) obj = "�ی��ҏ��";
        else if ("date".equals(code)) obj = "�����";
        else if ("page".equals(code)) obj = "�y�[�W�ԍ�";
        else if ("dateRange".equals(code)) obj = "�L�������";
        else if ("dateRange.data.title".equals(code)) obj = "�u�L�����F�v";
        else if ("dateRange.data.fromY".equals(code)) obj = "�L�����E�J�n���E�N";
        else if ("dateRange.data.w3".equals(code)) obj = "�u�N�v�@1";
        else if ("dateRange.data.fromM".equals(code)) obj = "�L�����E�J�n���E��";
        else if ("dateRange.data.w5".equals(code)) obj = "�u���v�@1";
        else if ("dateRange.data.fromD".equals(code)) obj = "�L�����E�J�n���E��";
        else if ("dateRange.data.w7".equals(code)) obj = "�u���v�@1";
        else if ("dateRange.data.w9".equals(code)) obj = "�u����v";
        else if ("dateRange.data.toY".equals(code)) obj = "�L�����E�I�����E�N";
        else if ("dateRange.data.w12".equals(code)) obj = "�u�N�v�@2";
        else if ("dateRange.data.toM".equals(code)) obj = "�L�����E�I�����E��";
        else if ("dateRange.data.w14".equals(code)) obj = "�u���v�@2";
        else if ("dateRange.data.toD".equals(code)) obj = "�L�����E�I�����E��";
        else if ("dateRange.data.w16".equals(code)) obj = "�u���v�@2";
        else if ("table".equals(code)) obj = "�ӌ����ꗗ";
        else if ("table.title.HAKKOU_KBN".equals(code)) obj = "�񖼁@�u�������v";
        else if ("table.title.PATIENT_NM".equals(code)) obj = "�񖼁@�u�����v";
        else if ("table.title.PATIENT_KN".equals(code)) obj = "�񖼁@�u�ӂ肪�ȁv";
        else if ("table.title.SEX".equals(code)) obj = "�񖼁@�u���ʁv";
        else if ("table.title.AGE".equals(code)) obj = "�񖼁@�u�N��v";
        else if ("table.title.BIRTHDAY".equals(code)) obj = "�񖼁@�u���N�����v";
        else if ("table.title.INSURED_NO".equals(code)) obj = "�񖼁@�u��ی��Ҕԍ��v";
        else if ("table.title.DR_NM".equals(code)) obj = "�񖼁@�u��t���v";
        else if ("table.title.REQ_DT".equals(code)) obj = "�񖼁@�u�쐬�˗����v";
        else if ("table.title.KINYU_DT".equals(code)) obj = "�񖼁@�u�ӌ����L�����v";
        else if ("table.title.SEND_DT".equals(code)) obj = "�񖼁@�u�ӌ������t���v";
        else if ("table.h1.HAKKOU_KBN".equals(code)) obj = "1�s�ځ@�u�������v";
        else if ("table.h1.PATIENT_NM".equals(code)) obj = "1�s�ځ@�u�����v";
        else if ("table.h1.PATIENT_KN".equals(code)) obj = "1�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h1.SEX".equals(code)) obj = "1�s�ځ@�u���ʁv";
        else if ("table.h1.AGE".equals(code)) obj = "1�s�ځ@�u�N��v";
        else if ("table.h1.BIRTHDAY".equals(code)) obj = "1�s�ځ@�u���N�����v";
        else if ("table.h1.INSURED_NO".equals(code)) obj = "1�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h1.DR_NM".equals(code)) obj = "1�s�ځ@�u��t���v";
        else if ("table.h1.REQ_DT".equals(code)) obj = "1�s�ځ@�u�쐬�˗����v";
        else if ("table.h1.KINYU_DT".equals(code)) obj = "1�s�ځ@�u�ӌ����L�����v";
        else if ("table.h1.SEND_DT".equals(code)) obj = "1�s�ځ@�u�ӌ������t���v";
        else if ("table.h2.HAKKOU_KBN".equals(code)) obj = "2�s�ځ@�u�������v";
        else if ("table.h2.PATIENT_NM".equals(code)) obj = "2�s�ځ@�u�����v";
        else if ("table.h2.PATIENT_KN".equals(code)) obj = "2�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h2.SEX".equals(code)) obj = "2�s�ځ@�u���ʁv";
        else if ("table.h2.AGE".equals(code)) obj = "2�s�ځ@�u�N��v";
        else if ("table.h2.BIRTHDAY".equals(code)) obj = "2�s�ځ@�u���N�����v";
        else if ("table.h2.INSURED_NO".equals(code)) obj = "2�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h2.DR_NM".equals(code)) obj = "2�s�ځ@�u��t���v";
        else if ("table.h2.REQ_DT".equals(code)) obj = "2�s�ځ@�u�쐬�˗����v";
        else if ("table.h2.KINYU_DT".equals(code)) obj = "2�s�ځ@�u�ӌ����L�����v";
        else if ("table.h2.SEND_DT".equals(code)) obj = "2�s�ځ@�u�ӌ������t���v";
        else if ("table.h3.HAKKOU_KBN".equals(code)) obj = "3�s�ځ@�u�������v";
        else if ("table.h3.PATIENT_NM".equals(code)) obj = "3�s�ځ@�u�����v";
        else if ("table.h3.PATIENT_KN".equals(code)) obj = "3�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h3.SEX".equals(code)) obj = "3�s�ځ@�u���ʁv";
        else if ("table.h3.AGE".equals(code)) obj = "3�s�ځ@�u�N��v";
        else if ("table.h3.BIRTHDAY".equals(code)) obj = "3�s�ځ@�u���N�����v";
        else if ("table.h3.INSURED_NO".equals(code)) obj = "3�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h3.DR_NM".equals(code)) obj = "3�s�ځ@�u��t���v";
        else if ("table.h3.REQ_DT".equals(code)) obj = "3�s�ځ@�u�쐬�˗����v";
        else if ("table.h3.KINYU_DT".equals(code)) obj = "3�s�ځ@�u�ӌ����L�����v";
        else if ("table.h3.SEND_DT".equals(code)) obj = "3�s�ځ@�u�ӌ������t���v";
        else if ("table.h4.HAKKOU_KBN".equals(code)) obj = "4�s�ځ@�u�������v";
        else if ("table.h4.PATIENT_NM".equals(code)) obj = "4�s�ځ@�u�����v";
        else if ("table.h4.PATIENT_KN".equals(code)) obj = "4�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h4.SEX".equals(code)) obj = "4�s�ځ@�u���ʁv";
        else if ("table.h4.AGE".equals(code)) obj = "4�s�ځ@�u�N��v";
        else if ("table.h4.BIRTHDAY".equals(code)) obj = "4�s�ځ@�u���N�����v";
        else if ("table.h4.INSURED_NO".equals(code)) obj = "4�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h4.DR_NM".equals(code)) obj = "4�s�ځ@�u��t���v";
        else if ("table.h4.REQ_DT".equals(code)) obj = "4�s�ځ@�u�쐬�˗����v";
        else if ("table.h4.KINYU_DT".equals(code)) obj = "4�s�ځ@�u�ӌ����L�����v";
        else if ("table.h4.SEND_DT".equals(code)) obj = "4�s�ځ@�u�ӌ������t���v";
        else if ("table.h5.HAKKOU_KBN".equals(code)) obj = "5�s�ځ@�u�������v";
        else if ("table.h5.PATIENT_NM".equals(code)) obj = "5�s�ځ@�u�����v";
        else if ("table.h5.PATIENT_KN".equals(code)) obj = "5�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h5.SEX".equals(code)) obj = "5�s�ځ@�u���ʁv";
        else if ("table.h5.AGE".equals(code)) obj = "5�s�ځ@�u�N��v";
        else if ("table.h5.BIRTHDAY".equals(code)) obj = "5�s�ځ@�u���N�����v";
        else if ("table.h5.INSURED_NO".equals(code)) obj = "5�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h5.DR_NM".equals(code)) obj = "5�s�ځ@�u��t���v";
        else if ("table.h5.REQ_DT".equals(code)) obj = "5�s�ځ@�u�쐬�˗����v";
        else if ("table.h5.KINYU_DT".equals(code)) obj = "5�s�ځ@�u�ӌ����L�����v";
        else if ("table.h5.SEND_DT".equals(code)) obj = "5�s�ځ@�u�ӌ������t���v";
        else if ("table.h6.HAKKOU_KBN".equals(code)) obj = "6�s�ځ@�u�������v";
        else if ("table.h6.PATIENT_NM".equals(code)) obj = "6�s�ځ@�u�����v";
        else if ("table.h6.PATIENT_KN".equals(code)) obj = "6�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h6.SEX".equals(code)) obj = "6�s�ځ@�u���ʁv";
        else if ("table.h6.AGE".equals(code)) obj = "6�s�ځ@�u�N��v";
        else if ("table.h6.BIRTHDAY".equals(code)) obj = "6�s�ځ@�u���N�����v";
        else if ("table.h6.INSURED_NO".equals(code)) obj = "6�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h6.DR_NM".equals(code)) obj = "6�s�ځ@�u��t���v";
        else if ("table.h6.REQ_DT".equals(code)) obj = "6�s�ځ@�u�쐬�˗����v";
        else if ("table.h6.KINYU_DT".equals(code)) obj = "6�s�ځ@�u�ӌ����L�����v";
        else if ("table.h6.SEND_DT".equals(code)) obj = "6�s�ځ@�u�ӌ������t���v";
        else if ("table.h7.HAKKOU_KBN".equals(code)) obj = "7�s�ځ@�u�������v";
        else if ("table.h7.PATIENT_NM".equals(code)) obj = "7�s�ځ@�u�����v";
        else if ("table.h7.PATIENT_KN".equals(code)) obj = "7�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h7.SEX".equals(code)) obj = "7�s�ځ@�u���ʁv";
        else if ("table.h7.AGE".equals(code)) obj = "7�s�ځ@�u�N��v";
        else if ("table.h7.BIRTHDAY".equals(code)) obj = "7�s�ځ@�u���N�����v";
        else if ("table.h7.INSURED_NO".equals(code)) obj = "7�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h7.DR_NM".equals(code)) obj = "7�s�ځ@�u��t���v";
        else if ("table.h7.REQ_DT".equals(code)) obj = "7�s�ځ@�u�쐬�˗����v";
        else if ("table.h7.KINYU_DT".equals(code)) obj = "7�s�ځ@�u�ӌ����L�����v";
        else if ("table.h7.SEND_DT".equals(code)) obj = "7�s�ځ@�u�ӌ������t���v";
        else if ("table.h8.HAKKOU_KBN".equals(code)) obj = "8�s�ځ@�u�������v";
        else if ("table.h8.PATIENT_NM".equals(code)) obj = "8�s�ځ@�u�����v";
        else if ("table.h8.PATIENT_KN".equals(code)) obj = "8�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h8.SEX".equals(code)) obj = "8�s�ځ@�u���ʁv";
        else if ("table.h8.AGE".equals(code)) obj = "8�s�ځ@�u�N��v";
        else if ("table.h8.BIRTHDAY".equals(code)) obj = "8�s�ځ@�u���N�����v";
        else if ("table.h8.INSURED_NO".equals(code)) obj = "8�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h8.DR_NM".equals(code)) obj = "8�s�ځ@�u��t���v";
        else if ("table.h8.REQ_DT".equals(code)) obj = "8�s�ځ@�u�쐬�˗����v";
        else if ("table.h8.KINYU_DT".equals(code)) obj = "8�s�ځ@�u�ӌ����L�����v";
        else if ("table.h8.SEND_DT".equals(code)) obj = "8�s�ځ@�u�ӌ������t���v";
        else if ("table.h9.HAKKOU_KBN".equals(code)) obj = "9�s�ځ@�u�������v";
        else if ("table.h9.PATIENT_NM".equals(code)) obj = "9�s�ځ@�u�����v";
        else if ("table.h9.PATIENT_KN".equals(code)) obj = "9�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h9.SEX".equals(code)) obj = "9�s�ځ@�u���ʁv";
        else if ("table.h9.AGE".equals(code)) obj = "9�s�ځ@�u�N��v";
        else if ("table.h9.BIRTHDAY".equals(code)) obj = "9�s�ځ@�u���N�����v";
        else if ("table.h9.INSURED_NO".equals(code)) obj = "9�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h9.DR_NM".equals(code)) obj = "9�s�ځ@�u��t���v";
        else if ("table.h9.REQ_DT".equals(code)) obj = "9�s�ځ@�u�쐬�˗����v";
        else if ("table.h9.KINYU_DT".equals(code)) obj = "9�s�ځ@�u�ӌ����L�����v";
        else if ("table.h9.SEND_DT".equals(code)) obj = "9�s�ځ@�u�ӌ������t���v";
        else if ("table.h10.HAKKOU_KBN".equals(code)) obj = "10�s�ځ@�u�������v";
        else if ("table.h10.PATIENT_NM".equals(code)) obj = "10�s�ځ@�u�����v";
        else if ("table.h10.PATIENT_KN".equals(code)) obj = "10�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h10.SEX".equals(code)) obj = "10�s�ځ@�u���ʁv";
        else if ("table.h10.AGE".equals(code)) obj = "10�s�ځ@�u�N��v";
        else if ("table.h10.BIRTHDAY".equals(code)) obj = "10�s�ځ@�u���N�����v";
        else if ("table.h10.INSURED_NO".equals(code)) obj = "10�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h10.DR_NM".equals(code)) obj = "10�s�ځ@�u��t���v";
        else if ("table.h10.REQ_DT".equals(code)) obj = "10�s�ځ@�u�쐬�˗����v";
        else if ("table.h10.KINYU_DT".equals(code)) obj = "10�s�ځ@�u�ӌ����L�����v";
        else if ("table.h10.SEND_DT".equals(code)) obj = "10�s�ځ@�u�ӌ������t���v";
        else if ("table.h11.HAKKOU_KBN".equals(code)) obj = "11�s�ځ@�u�������v";
        else if ("table.h11.PATIENT_NM".equals(code)) obj = "11�s�ځ@�u�����v";
        else if ("table.h11.PATIENT_KN".equals(code)) obj = "11�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h11.SEX".equals(code)) obj = "11�s�ځ@�u���ʁv";
        else if ("table.h11.AGE".equals(code)) obj = "11�s�ځ@�u�N��v";
        else if ("table.h11.BIRTHDAY".equals(code)) obj = "11�s�ځ@�u���N�����v";
        else if ("table.h11.INSURED_NO".equals(code)) obj = "11�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h11.DR_NM".equals(code)) obj = "11�s�ځ@�u��t���v";
        else if ("table.h11.REQ_DT".equals(code)) obj = "11�s�ځ@�u�쐬�˗����v";
        else if ("table.h11.KINYU_DT".equals(code)) obj = "11�s�ځ@�u�ӌ����L�����v";
        else if ("table.h11.SEND_DT".equals(code)) obj = "11�s�ځ@�u�ӌ������t���v";
        else if ("table.h12.HAKKOU_KBN".equals(code)) obj = "12�s�ځ@�u�������v";
        else if ("table.h12.PATIENT_NM".equals(code)) obj = "12�s�ځ@�u�����v";
        else if ("table.h12.PATIENT_KN".equals(code)) obj = "12�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h12.SEX".equals(code)) obj = "12�s�ځ@�u���ʁv";
        else if ("table.h12.AGE".equals(code)) obj = "12�s�ځ@�u�N��v";
        else if ("table.h12.BIRTHDAY".equals(code)) obj = "12�s�ځ@�u���N�����v";
        else if ("table.h12.INSURED_NO".equals(code)) obj = "12�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h12.DR_NM".equals(code)) obj = "12�s�ځ@�u��t���v";
        else if ("table.h12.REQ_DT".equals(code)) obj = "12�s�ځ@�u�쐬�˗����v";
        else if ("table.h12.KINYU_DT".equals(code)) obj = "12�s�ځ@�u�ӌ����L�����v";
        else if ("table.h12.SEND_DT".equals(code)) obj = "12�s�ځ@�u�ӌ������t���v";
        else if ("table.h13.HAKKOU_KBN".equals(code)) obj = "13�s�ځ@�u�������v";
        else if ("table.h13.PATIENT_NM".equals(code)) obj = "13�s�ځ@�u�����v";
        else if ("table.h13.PATIENT_KN".equals(code)) obj = "13�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h13.SEX".equals(code)) obj = "13�s�ځ@�u���ʁv";
        else if ("table.h13.AGE".equals(code)) obj = "13�s�ځ@�u�N��v";
        else if ("table.h13.BIRTHDAY".equals(code)) obj = "13�s�ځ@�u���N�����v";
        else if ("table.h13.INSURED_NO".equals(code)) obj = "13�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h13.DR_NM".equals(code)) obj = "13�s�ځ@�u��t���v";
        else if ("table.h13.REQ_DT".equals(code)) obj = "13�s�ځ@�u�쐬�˗����v";
        else if ("table.h13.KINYU_DT".equals(code)) obj = "13�s�ځ@�u�ӌ����L�����v";
        else if ("table.h13.SEND_DT".equals(code)) obj = "13�s�ځ@�u�ӌ������t���v";
        else if ("table.h14.HAKKOU_KBN".equals(code)) obj = "14�s�ځ@�u�������v";
        else if ("table.h14.PATIENT_NM".equals(code)) obj = "14�s�ځ@�u�����v";
        else if ("table.h14.PATIENT_KN".equals(code)) obj = "14�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h14.SEX".equals(code)) obj = "14�s�ځ@�u���ʁv";
        else if ("table.h14.AGE".equals(code)) obj = "14�s�ځ@�u�N��v";
        else if ("table.h14.BIRTHDAY".equals(code)) obj = "14�s�ځ@�u���N�����v";
        else if ("table.h14.INSURED_NO".equals(code)) obj = "14�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h14.DR_NM".equals(code)) obj = "14�s�ځ@�u��t���v";
        else if ("table.h14.REQ_DT".equals(code)) obj = "14�s�ځ@�u�쐬�˗����v";
        else if ("table.h14.KINYU_DT".equals(code)) obj = "14�s�ځ@�u�ӌ����L�����v";
        else if ("table.h14.SEND_DT".equals(code)) obj = "14�s�ځ@�u�ӌ������t���v";
        else if ("table.h15.HAKKOU_KBN".equals(code)) obj = "15�s�ځ@�u�������v";
        else if ("table.h15.PATIENT_NM".equals(code)) obj = "15�s�ځ@�u�����v";
        else if ("table.h15.PATIENT_KN".equals(code)) obj = "15�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h15.SEX".equals(code)) obj = "15�s�ځ@�u���ʁv";
        else if ("table.h15.AGE".equals(code)) obj = "15�s�ځ@�u�N��v";
        else if ("table.h15.BIRTHDAY".equals(code)) obj = "15�s�ځ@�u���N�����v";
        else if ("table.h15.INSURED_NO".equals(code)) obj = "15�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h15.DR_NM".equals(code)) obj = "15�s�ځ@�u��t���v";
        else if ("table.h15.REQ_DT".equals(code)) obj = "15�s�ځ@�u�쐬�˗����v";
        else if ("table.h15.KINYU_DT".equals(code)) obj = "15�s�ځ@�u�ӌ����L�����v";
        else if ("table.h15.SEND_DT".equals(code)) obj = "15�s�ځ@�u�ӌ������t���v";
        else if ("table.h16.HAKKOU_KBN".equals(code)) obj = "16�s�ځ@�u�������v";
        else if ("table.h16.PATIENT_NM".equals(code)) obj = "16�s�ځ@�u�����v";
        else if ("table.h16.PATIENT_KN".equals(code)) obj = "16�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h16.SEX".equals(code)) obj = "16�s�ځ@�u���ʁv";
        else if ("table.h16.AGE".equals(code)) obj = "16�s�ځ@�u�N��v";
        else if ("table.h16.BIRTHDAY".equals(code)) obj = "16�s�ځ@�u���N�����v";
        else if ("table.h16.INSURED_NO".equals(code)) obj = "16�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h16.DR_NM".equals(code)) obj = "16�s�ځ@�u��t���v";
        else if ("table.h16.REQ_DT".equals(code)) obj = "16�s�ځ@�u�쐬�˗����v";
        else if ("table.h16.KINYU_DT".equals(code)) obj = "16�s�ځ@�u�ӌ����L�����v";
        else if ("table.h16.SEND_DT".equals(code)) obj = "16�s�ځ@�u�ӌ������t���v";
        else if ("table.h17.HAKKOU_KBN".equals(code)) obj = "17�s�ځ@�u�������v";
        else if ("table.h17.PATIENT_NM".equals(code)) obj = "17�s�ځ@�u�����v";
        else if ("table.h17.PATIENT_KN".equals(code)) obj = "17�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h17.SEX".equals(code)) obj = "17�s�ځ@�u���ʁv";
        else if ("table.h17.AGE".equals(code)) obj = "17�s�ځ@�u�N��v";
        else if ("table.h17.BIRTHDAY".equals(code)) obj = "17�s�ځ@�u���N�����v";
        else if ("table.h17.INSURED_NO".equals(code)) obj = "17�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h17.DR_NM".equals(code)) obj = "17�s�ځ@�u��t���v";
        else if ("table.h17.REQ_DT".equals(code)) obj = "17�s�ځ@�u�쐬�˗����v";
        else if ("table.h17.KINYU_DT".equals(code)) obj = "17�s�ځ@�u�ӌ����L�����v";
        else if ("table.h17.SEND_DT".equals(code)) obj = "17�s�ځ@�u�ӌ������t���v";
        else if ("table.h18.HAKKOU_KBN".equals(code)) obj = "18�s�ځ@�u�������v";
        else if ("table.h18.PATIENT_NM".equals(code)) obj = "18�s�ځ@�u�����v";
        else if ("table.h18.PATIENT_KN".equals(code)) obj = "18�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h18.SEX".equals(code)) obj = "18�s�ځ@�u���ʁv";
        else if ("table.h18.AGE".equals(code)) obj = "18�s�ځ@�u�N��v";
        else if ("table.h18.BIRTHDAY".equals(code)) obj = "18�s�ځ@�u���N�����v";
        else if ("table.h18.INSURED_NO".equals(code)) obj = "18�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h18.DR_NM".equals(code)) obj = "18�s�ځ@�u��t���v";
        else if ("table.h18.REQ_DT".equals(code)) obj = "18�s�ځ@�u�쐬�˗����v";
        else if ("table.h18.KINYU_DT".equals(code)) obj = "18�s�ځ@�u�ӌ����L�����v";
        else if ("table.h18.SEND_DT".equals(code)) obj = "18�s�ځ@�u�ӌ������t���v";
        else if ("table.h19.HAKKOU_KBN".equals(code)) obj = "19�s�ځ@�u�������v";
        else if ("table.h19.PATIENT_NM".equals(code)) obj = "19�s�ځ@�u�����v";
        else if ("table.h19.PATIENT_KN".equals(code)) obj = "19�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h19.SEX".equals(code)) obj = "19�s�ځ@�u���ʁv";
        else if ("table.h19.AGE".equals(code)) obj = "19�s�ځ@�u�N��v";
        else if ("table.h19.BIRTHDAY".equals(code)) obj = "19�s�ځ@�u���N�����v";
        else if ("table.h19.INSURED_NO".equals(code)) obj = "19�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h19.DR_NM".equals(code)) obj = "19�s�ځ@�u��t���v";
        else if ("table.h19.REQ_DT".equals(code)) obj = "19�s�ځ@�u�쐬�˗����v";
        else if ("table.h19.KINYU_DT".equals(code)) obj = "19�s�ځ@�u�ӌ����L�����v";
        else if ("table.h19.SEND_DT".equals(code)) obj = "19�s�ځ@�u�ӌ������t���v";
        else if ("table.h20.HAKKOU_KBN".equals(code)) obj = "20�s�ځ@�u�������v";
        else if ("table.h20.PATIENT_NM".equals(code)) obj = "20�s�ځ@�u�����v";
        else if ("table.h20.PATIENT_KN".equals(code)) obj = "20�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h20.SEX".equals(code)) obj = "20�s�ځ@�u���ʁv";
        else if ("table.h20.AGE".equals(code)) obj = "20�s�ځ@�u�N��v";
        else if ("table.h20.BIRTHDAY".equals(code)) obj = "20�s�ځ@�u���N�����v";
        else if ("table.h20.INSURED_NO".equals(code)) obj = "20�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h20.DR_NM".equals(code)) obj = "20�s�ځ@�u��t���v";
        else if ("table.h20.REQ_DT".equals(code)) obj = "20�s�ځ@�u�쐬�˗����v";
        else if ("table.h20.KINYU_DT".equals(code)) obj = "20�s�ځ@�u�ӌ����L�����v";
        else if ("table.h20.SEND_DT".equals(code)) obj = "20�s�ځ@�u�ӌ������t���v";
        else if ("table.h21.HAKKOU_KBN".equals(code)) obj = "21�s�ځ@�u�������v";
        else if ("table.h21.PATIENT_NM".equals(code)) obj = "21�s�ځ@�u�����v";
        else if ("table.h21.PATIENT_KN".equals(code)) obj = "21�s�ځ@�u�ӂ肪�ȁv";
        else if ("table.h21.SEX".equals(code)) obj = "21�s�ځ@�u���ʁv";
        else if ("table.h21.AGE".equals(code)) obj = "21�s�ځ@�u�N��v";
        else if ("table.h21.BIRTHDAY".equals(code)) obj = "21�s�ځ@�u���N�����v";
        else if ("table.h21.INSURED_NO".equals(code)) obj = "21�s�ځ@�u��ی��Ҕԍ��v";
        else if ("table.h21.DR_NM".equals(code)) obj = "21�s�ځ@�u��t���v";
        else if ("table.h21.REQ_DT".equals(code)) obj = "21�s�ځ@�u�쐬�˗����v";
        else if ("table.h21.KINYU_DT".equals(code)) obj = "21�s�ځ@�u�ӌ����L�����v";
        else if ("table.h21.SEND_DT".equals(code)) obj = "21�s�ځ@�u�ӌ������t���v";
        return obj;
    }
    
    /**
     * �b�r�u�t�@�C����o���҈ꗗ�̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatCSVList(String code, Object obj){
        if ("lblTitle".equals(code)) obj = "�^�C�g��";
        else if ("formatVersion".equals(code)) obj = "�t�H�[�}�b�g�o�[�W����";
        else if ("date".equals(code)) obj = "�����";
        else if ("hokensya".equals(code)) obj = "�ی��Җ�";
        else if ("dateRange".equals(code)) obj = "�L����";
        else if ("table".equals(code)) obj = "�ꗗ";
        else if ("table.title.DR_NM".equals(code)) obj = "�񖼁u��t���v";
        else if ("table.h1.DR_NM".equals(code)) obj = "1�s�ځu��t���v";
        else if ("table.h2.DR_NM".equals(code)) obj = "2�s�ځu��t���v";
        else if ("table.h3.DR_NM".equals(code)) obj = "3�s�ځu��t���v";
        else if ("table.h4.DR_NM".equals(code)) obj = "4�s�ځu��t���v";
        else if ("table.h5.DR_NM".equals(code)) obj = "5�s�ځu��t���v";
        else if ("table.h6.DR_NM".equals(code)) obj = "6�s�ځu��t���v";
        else if ("table.h7.DR_NM".equals(code)) obj = "7�s�ځu��t���v";
        else if ("table.h8.DR_NM".equals(code)) obj = "8�s�ځu��t���v";
        else if ("table.h9.DR_NM".equals(code)) obj = "9�s�ځu��t���v";
        else if ("table.h10.DR_NM".equals(code)) obj = "10�s�ځu��t���v";
        else if ("table.h11.DR_NM".equals(code)) obj = "11�s�ځu��t���v";
        else if ("table.h12.DR_NM".equals(code)) obj = "12�s�ځu��t���v";
        else if ("table.h13.DR_NM".equals(code)) obj = "13�s�ځu��t���v";
        else if ("table.h14.DR_NM".equals(code)) obj = "14�s�ځu��t���v";
        else if ("table.h15.DR_NM".equals(code)) obj = "15�s�ځu��t���v";
        else if ("table.h16.DR_NM".equals(code)) obj = "16�s�ځu��t���v";
        else if ("table.h17.DR_NM".equals(code)) obj = "17�s�ځu��t���v";
        else if ("table.h18.DR_NM".equals(code)) obj = "18�s�ځu��t���v";
        else if ("table.h19.DR_NM".equals(code)) obj = "19�s�ځu��t���v";
        else if ("table.h20.DR_NM".equals(code)) obj = "20�s�ځu��t���v";
        else if ("table.h21.DR_NM".equals(code)) obj = "21�s�ځu��t���v";
        else if ("table.title.PATIENT_NM".equals(code)) obj = "�񖼁u�����v";
        else if ("table.h1.PATIENT_NM".equals(code)) obj = "1�s�ځu�����v";
        else if ("table.h2.PATIENT_NM".equals(code)) obj = "2�s�ځu�����v";
        else if ("table.h3.PATIENT_NM".equals(code)) obj = "3�s�ځu�����v";
        else if ("table.h4.PATIENT_NM".equals(code)) obj = "4�s�ځu�����v";
        else if ("table.h5.PATIENT_NM".equals(code)) obj = "5�s�ځu�����v";
        else if ("table.h6.PATIENT_NM".equals(code)) obj = "6�s�ځu�����v";
        else if ("table.h7.PATIENT_NM".equals(code)) obj = "7�s�ځu�����v";
        else if ("table.h8.PATIENT_NM".equals(code)) obj = "8�s�ځu�����v";
        else if ("table.h9.PATIENT_NM".equals(code)) obj = "9�s�ځu�����v";
        else if ("table.h10.PATIENT_NM".equals(code)) obj = "10�s�ځu�����v";
        else if ("table.h11.PATIENT_NM".equals(code)) obj = "11�s�ځu�����v";
        else if ("table.h12.PATIENT_NM".equals(code)) obj = "12�s�ځu�����v";
        else if ("table.h13.PATIENT_NM".equals(code)) obj = "13�s�ځu�����v";
        else if ("table.h14.PATIENT_NM".equals(code)) obj = "14�s�ځu�����v";
        else if ("table.h15.PATIENT_NM".equals(code)) obj = "15�s�ځu�����v";
        else if ("table.h16.PATIENT_NM".equals(code)) obj = "16�s�ځu�����v";
        else if ("table.h17.PATIENT_NM".equals(code)) obj = "17�s�ځu�����v";
        else if ("table.h18.PATIENT_NM".equals(code)) obj = "18�s�ځu�����v";
        else if ("table.h19.PATIENT_NM".equals(code)) obj = "19�s�ځu�����v";
        else if ("table.h20.PATIENT_NM".equals(code)) obj = "20�s�ځu�����v";
        else if ("table.h21.PATIENT_NM".equals(code)) obj = "21�s�ځu�����v";
        else if ("table.title.PATIENT_KN".equals(code)) obj = "�񖼁u�ӂ肪�ȁv";
        else if ("table.h1.PATIENT_KN".equals(code)) obj = "1�s�ځu�ӂ肪�ȁv";
        else if ("table.h2.PATIENT_KN".equals(code)) obj = "2�s�ځu�ӂ肪�ȁv";
        else if ("table.h3.PATIENT_KN".equals(code)) obj = "3�s�ځu�ӂ肪�ȁv";
        else if ("table.h4.PATIENT_KN".equals(code)) obj = "4�s�ځu�ӂ肪�ȁv";
        else if ("table.h5.PATIENT_KN".equals(code)) obj = "5�s�ځu�ӂ肪�ȁv";
        else if ("table.h6.PATIENT_KN".equals(code)) obj = "6�s�ځu�ӂ肪�ȁv";
        else if ("table.h7.PATIENT_KN".equals(code)) obj = "7�s�ځu�ӂ肪�ȁv";
        else if ("table.h8.PATIENT_KN".equals(code)) obj = "8�s�ځu�ӂ肪�ȁv";
        else if ("table.h9.PATIENT_KN".equals(code)) obj = "9�s�ځu�ӂ肪�ȁv";
        else if ("table.h10.PATIENT_KN".equals(code)) obj = "10�s�ځu�ӂ肪�ȁv";
        else if ("table.h11.PATIENT_KN".equals(code)) obj = "11�s�ځu�ӂ肪�ȁv";
        else if ("table.h12.PATIENT_KN".equals(code)) obj = "12�s�ځu�ӂ肪�ȁv";
        else if ("table.h13.PATIENT_KN".equals(code)) obj = "13�s�ځu�ӂ肪�ȁv";
        else if ("table.h14.PATIENT_KN".equals(code)) obj = "14�s�ځu�ӂ肪�ȁv";
        else if ("table.h15.PATIENT_KN".equals(code)) obj = "15�s�ځu�ӂ肪�ȁv";
        else if ("table.h16.PATIENT_KN".equals(code)) obj = "16�s�ځu�ӂ肪�ȁv";
        else if ("table.h17.PATIENT_KN".equals(code)) obj = "17�s�ځu�ӂ肪�ȁv";
        else if ("table.h18.PATIENT_KN".equals(code)) obj = "18�s�ځu�ӂ肪�ȁv";
        else if ("table.h19.PATIENT_KN".equals(code)) obj = "19�s�ځu�ӂ肪�ȁv";
        else if ("table.h20.PATIENT_KN".equals(code)) obj = "20�s�ځu�ӂ肪�ȁv";
        else if ("table.h21.PATIENT_KN".equals(code)) obj = "21�s�ځu�ӂ肪�ȁv";
        else if ("table.title.SEX".equals(code)) obj = "�񖼁u���ʁv";
        else if ("table.h1.SEX".equals(code)) obj = "1�s�ځu���ʁv";
        else if ("table.h2.SEX".equals(code)) obj = "2�s�ځu���ʁv";
        else if ("table.h3.SEX".equals(code)) obj = "3�s�ځu���ʁv";
        else if ("table.h4.SEX".equals(code)) obj = "4�s�ځu���ʁv";
        else if ("table.h5.SEX".equals(code)) obj = "5�s�ځu���ʁv";
        else if ("table.h6.SEX".equals(code)) obj = "6�s�ځu���ʁv";
        else if ("table.h7.SEX".equals(code)) obj = "7�s�ځu���ʁv";
        else if ("table.h8.SEX".equals(code)) obj = "8�s�ځu���ʁv";
        else if ("table.h9.SEX".equals(code)) obj = "9�s�ځu���ʁv";
        else if ("table.h10.SEX".equals(code)) obj = "10�s�ځu���ʁv";
        else if ("table.h11.SEX".equals(code)) obj = "11�s�ځu���ʁv";
        else if ("table.h12.SEX".equals(code)) obj = "12�s�ځu���ʁv";
        else if ("table.h13.SEX".equals(code)) obj = "13�s�ځu���ʁv";
        else if ("table.h14.SEX".equals(code)) obj = "14�s�ځu���ʁv";
        else if ("table.h15.SEX".equals(code)) obj = "15�s�ځu���ʁv";
        else if ("table.h16.SEX".equals(code)) obj = "16�s�ځu���ʁv";
        else if ("table.h17.SEX".equals(code)) obj = "17�s�ځu���ʁv";
        else if ("table.h18.SEX".equals(code)) obj = "18�s�ځu���ʁv";
        else if ("table.h19.SEX".equals(code)) obj = "19�s�ځu���ʁv";
        else if ("table.h20.SEX".equals(code)) obj = "20�s�ځu���ʁv";
        else if ("table.h21.SEX".equals(code)) obj = "21�s�ځu���ʁv";
        else if ("table.title.AGE".equals(code)) obj = "�񖼁u�N��v";
        else if ("table.h1.AGE".equals(code)) obj = "1�s�ځu�N��v";
        else if ("table.h2.AGE".equals(code)) obj = "2�s�ځu�N��v";
        else if ("table.h3.AGE".equals(code)) obj = "3�s�ځu�N��v";
        else if ("table.h4.AGE".equals(code)) obj = "4�s�ځu�N��v";
        else if ("table.h5.AGE".equals(code)) obj = "5�s�ځu�N��v";
        else if ("table.h6.AGE".equals(code)) obj = "6�s�ځu�N��v";
        else if ("table.h7.AGE".equals(code)) obj = "7�s�ځu�N��v";
        else if ("table.h8.AGE".equals(code)) obj = "8�s�ځu�N��v";
        else if ("table.h9.AGE".equals(code)) obj = "9�s�ځu�N��v";
        else if ("table.h10.AGE".equals(code)) obj = "10�s�ځu�N��v";
        else if ("table.h11.AGE".equals(code)) obj = "11�s�ځu�N��v";
        else if ("table.h12.AGE".equals(code)) obj = "12�s�ځu�N��v";
        else if ("table.h13.AGE".equals(code)) obj = "13�s�ځu�N��v";
        else if ("table.h14.AGE".equals(code)) obj = "14�s�ځu�N��v";
        else if ("table.h15.AGE".equals(code)) obj = "15�s�ځu�N��v";
        else if ("table.h16.AGE".equals(code)) obj = "16�s�ځu�N��v";
        else if ("table.h17.AGE".equals(code)) obj = "17�s�ځu�N��v";
        else if ("table.h18.AGE".equals(code)) obj = "18�s�ځu�N��v";
        else if ("table.h19.AGE".equals(code)) obj = "19�s�ځu�N��v";
        else if ("table.h20.AGE".equals(code)) obj = "20�s�ځu�N��v";
        else if ("table.h21.AGE".equals(code)) obj = "21�s�ځu�N��v";
        else if ("table.title.BIRTHDAY".equals(code)) obj = "�񖼁u���N�����v";
        else if ("table.h1.BIRTHDAY".equals(code)) obj = "1�s�ځu���N�����v";
        else if ("table.h2.BIRTHDAY".equals(code)) obj = "2�s�ځu���N�����v";
        else if ("table.h3.BIRTHDAY".equals(code)) obj = "3�s�ځu���N�����v";
        else if ("table.h4.BIRTHDAY".equals(code)) obj = "4�s�ځu���N�����v";
        else if ("table.h5.BIRTHDAY".equals(code)) obj = "5�s�ځu���N�����v";
        else if ("table.h6.BIRTHDAY".equals(code)) obj = "6�s�ځu���N�����v";
        else if ("table.h7.BIRTHDAY".equals(code)) obj = "7�s�ځu���N�����v";
        else if ("table.h8.BIRTHDAY".equals(code)) obj = "8�s�ځu���N�����v";
        else if ("table.h9.BIRTHDAY".equals(code)) obj = "9�s�ځu���N�����v";
        else if ("table.h10.BIRTHDAY".equals(code)) obj = "10�s�ځu���N�����v";
        else if ("table.h11.BIRTHDAY".equals(code)) obj = "11�s�ځu���N�����v";
        else if ("table.h12.BIRTHDAY".equals(code)) obj = "12�s�ځu���N�����v";
        else if ("table.h13.BIRTHDAY".equals(code)) obj = "13�s�ځu���N�����v";
        else if ("table.h14.BIRTHDAY".equals(code)) obj = "14�s�ځu���N�����v";
        else if ("table.h15.BIRTHDAY".equals(code)) obj = "15�s�ځu���N�����v";
        else if ("table.h16.BIRTHDAY".equals(code)) obj = "16�s�ځu���N�����v";
        else if ("table.h17.BIRTHDAY".equals(code)) obj = "17�s�ځu���N�����v";
        else if ("table.h18.BIRTHDAY".equals(code)) obj = "18�s�ځu���N�����v";
        else if ("table.h19.BIRTHDAY".equals(code)) obj = "19�s�ځu���N�����v";
        else if ("table.h20.BIRTHDAY".equals(code)) obj = "20�s�ځu���N�����v";
        else if ("table.h21.BIRTHDAY".equals(code)) obj = "21�s�ځu���N�����v";
        else if ("table.title.INSURED_NO".equals(code)) obj = "�񖼁u��ی��Ҕԍ��v";
        else if ("table.h1.INSURED_NO".equals(code)) obj = "1�s�ځu��ی��Ҕԍ��v";
        else if ("table.h2.INSURED_NO".equals(code)) obj = "2�s�ځu��ی��Ҕԍ��v";
        else if ("table.h3.INSURED_NO".equals(code)) obj = "3�s�ځu��ی��Ҕԍ��v";
        else if ("table.h4.INSURED_NO".equals(code)) obj = "4�s�ځu��ی��Ҕԍ��v";
        else if ("table.h5.INSURED_NO".equals(code)) obj = "5�s�ځu��ی��Ҕԍ��v";
        else if ("table.h6.INSURED_NO".equals(code)) obj = "6�s�ځu��ی��Ҕԍ��v";
        else if ("table.h7.INSURED_NO".equals(code)) obj = "7�s�ځu��ی��Ҕԍ��v";
        else if ("table.h8.INSURED_NO".equals(code)) obj = "8�s�ځu��ی��Ҕԍ��v";
        else if ("table.h9.INSURED_NO".equals(code)) obj = "9�s�ځu��ی��Ҕԍ��v";
        else if ("table.h10.INSURED_NO".equals(code)) obj = "10�s�ځu��ی��Ҕԍ��v";
        else if ("table.h11.INSURED_NO".equals(code)) obj = "11�s�ځu��ی��Ҕԍ��v";
        else if ("table.h12.INSURED_NO".equals(code)) obj = "12�s�ځu��ی��Ҕԍ��v";
        else if ("table.h13.INSURED_NO".equals(code)) obj = "13�s�ځu��ی��Ҕԍ��v";
        else if ("table.h14.INSURED_NO".equals(code)) obj = "14�s�ځu��ی��Ҕԍ��v";
        else if ("table.h15.INSURED_NO".equals(code)) obj = "15�s�ځu��ی��Ҕԍ��v";
        else if ("table.h16.INSURED_NO".equals(code)) obj = "16�s�ځu��ی��Ҕԍ��v";
        else if ("table.h17.INSURED_NO".equals(code)) obj = "17�s�ځu��ی��Ҕԍ��v";
        else if ("table.h18.INSURED_NO".equals(code)) obj = "18�s�ځu��ی��Ҕԍ��v";
        else if ("table.h19.INSURED_NO".equals(code)) obj = "19�s�ځu��ی��Ҕԍ��v";
        else if ("table.h20.INSURED_NO".equals(code)) obj = "20�s�ځu��ی��Ҕԍ��v";
        else if ("table.h21.INSURED_NO".equals(code)) obj = "21�s�ځu��ی��Ҕԍ��v";
        else if ("table.title.FD_TIMESTAMP".equals(code)) obj = "�񖼁u�^�C���X�^���v�v";
        else if ("table.h1.FD_TIMESTAMP".equals(code)) obj = "1�s�ځu�^�C���X�^���v�v";
        else if ("table.h2.FD_TIMESTAMP".equals(code)) obj = "2�s�ځu�^�C���X�^���v�v";
        else if ("table.h3.FD_TIMESTAMP".equals(code)) obj = "3�s�ځu�^�C���X�^���v�v";
        else if ("table.h4.FD_TIMESTAMP".equals(code)) obj = "4�s�ځu�^�C���X�^���v�v";
        else if ("table.h5.FD_TIMESTAMP".equals(code)) obj = "5�s�ځu�^�C���X�^���v�v";
        else if ("table.h6.FD_TIMESTAMP".equals(code)) obj = "6�s�ځu�^�C���X�^���v�v";
        else if ("table.h7.FD_TIMESTAMP".equals(code)) obj = "7�s�ځu�^�C���X�^���v�v";
        else if ("table.h8.FD_TIMESTAMP".equals(code)) obj = "8�s�ځu�^�C���X�^���v�v";
        else if ("table.h9.FD_TIMESTAMP".equals(code)) obj = "9�s�ځu�^�C���X�^���v�v";
        else if ("table.h10.FD_TIMESTAMP".equals(code)) obj = "10�s�ځu�^�C���X�^���v�v";
        else if ("table.h11.FD_TIMESTAMP".equals(code)) obj = "11�s�ځu�^�C���X�^���v�v";
        else if ("table.h12.FD_TIMESTAMP".equals(code)) obj = "12�s�ځu�^�C���X�^���v�v";
        else if ("table.h13.FD_TIMESTAMP".equals(code)) obj = "13�s�ځu�^�C���X�^���v�v";
        else if ("table.h14.FD_TIMESTAMP".equals(code)) obj = "14�s�ځu�^�C���X�^���v�v";
        else if ("table.h15.FD_TIMESTAMP".equals(code)) obj = "15�s�ځu�^�C���X�^���v�v";
        else if ("table.h16.FD_TIMESTAMP".equals(code)) obj = "16�s�ځu�^�C���X�^���v�v";
        else if ("table.h17.FD_TIMESTAMP".equals(code)) obj = "17�s�ځu�^�C���X�^���v�v";
        else if ("table.h18.FD_TIMESTAMP".equals(code)) obj = "18�s�ځu�^�C���X�^���v�v";
        else if ("table.h19.FD_TIMESTAMP".equals(code)) obj = "19�s�ځu�^�C���X�^���v�v";
        else if ("table.h20.FD_TIMESTAMP".equals(code)) obj = "20�s�ځu�^�C���X�^���v�v";
        else if ("table.h21.FD_TIMESTAMP".equals(code)) obj = "21�s�ځu�^�C���X�^���v�v";
        else if ("table.title.REQ_DT".equals(code)) obj = "�񖼁u�쐬�˗����v";
        else if ("table.h1.REQ_DT".equals(code)) obj = "1�s�ځu�쐬�˗����v";
        else if ("table.h2.REQ_DT".equals(code)) obj = "2�s�ځu�쐬�˗����v";
        else if ("table.h3.REQ_DT".equals(code)) obj = "3�s�ځu�쐬�˗����v";
        else if ("table.h4.REQ_DT".equals(code)) obj = "4�s�ځu�쐬�˗����v";
        else if ("table.h5.REQ_DT".equals(code)) obj = "5�s�ځu�쐬�˗����v";
        else if ("table.h6.REQ_DT".equals(code)) obj = "6�s�ځu�쐬�˗����v";
        else if ("table.h7.REQ_DT".equals(code)) obj = "7�s�ځu�쐬�˗����v";
        else if ("table.h8.REQ_DT".equals(code)) obj = "8�s�ځu�쐬�˗����v";
        else if ("table.h9.REQ_DT".equals(code)) obj = "9�s�ځu�쐬�˗����v";
        else if ("table.h10.REQ_DT".equals(code)) obj = "10�s�ځu�쐬�˗����v";
        else if ("table.h11.REQ_DT".equals(code)) obj = "11�s�ځu�쐬�˗����v";
        else if ("table.h12.REQ_DT".equals(code)) obj = "12�s�ځu�쐬�˗����v";
        else if ("table.h13.REQ_DT".equals(code)) obj = "13�s�ځu�쐬�˗����v";
        else if ("table.h14.REQ_DT".equals(code)) obj = "14�s�ځu�쐬�˗����v";
        else if ("table.h15.REQ_DT".equals(code)) obj = "15�s�ځu�쐬�˗����v";
        else if ("table.h16.REQ_DT".equals(code)) obj = "16�s�ځu�쐬�˗����v";
        else if ("table.h17.REQ_DT".equals(code)) obj = "17�s�ځu�쐬�˗����v";
        else if ("table.h18.REQ_DT".equals(code)) obj = "18�s�ځu�쐬�˗����v";
        else if ("table.h19.REQ_DT".equals(code)) obj = "19�s�ځu�쐬�˗����v";
        else if ("table.h20.REQ_DT".equals(code)) obj = "20�s�ځu�쐬�˗����v";
        else if ("table.h21.REQ_DT".equals(code)) obj = "21�s�ځu�쐬�˗����v";
        else if ("table.title.KINYU_DT".equals(code)) obj = "�񖼁u�ӌ����L�����v";
        else if ("table.h1.KINYU_DT".equals(code)) obj = "1�s�ځu�ӌ����L�����v";
        else if ("table.h2.KINYU_DT".equals(code)) obj = "2�s�ځu�ӌ����L�����v";
        else if ("table.h3.KINYU_DT".equals(code)) obj = "3�s�ځu�ӌ����L�����v";
        else if ("table.h4.KINYU_DT".equals(code)) obj = "4�s�ځu�ӌ����L�����v";
        else if ("table.h5.KINYU_DT".equals(code)) obj = "5�s�ځu�ӌ����L�����v";
        else if ("table.h6.KINYU_DT".equals(code)) obj = "6�s�ځu�ӌ����L�����v";
        else if ("table.h7.KINYU_DT".equals(code)) obj = "7�s�ځu�ӌ����L�����v";
        else if ("table.h8.KINYU_DT".equals(code)) obj = "8�s�ځu�ӌ����L�����v";
        else if ("table.h9.KINYU_DT".equals(code)) obj = "9�s�ځu�ӌ����L�����v";
        else if ("table.h10.KINYU_DT".equals(code)) obj = "10�s�ځu�ӌ����L�����v";
        else if ("table.h11.KINYU_DT".equals(code)) obj = "11�s�ځu�ӌ����L�����v";
        else if ("table.h12.KINYU_DT".equals(code)) obj = "12�s�ځu�ӌ����L�����v";
        else if ("table.h13.KINYU_DT".equals(code)) obj = "13�s�ځu�ӌ����L�����v";
        else if ("table.h14.KINYU_DT".equals(code)) obj = "14�s�ځu�ӌ����L�����v";
        else if ("table.h15.KINYU_DT".equals(code)) obj = "15�s�ځu�ӌ����L�����v";
        else if ("table.h16.KINYU_DT".equals(code)) obj = "16�s�ځu�ӌ����L�����v";
        else if ("table.h17.KINYU_DT".equals(code)) obj = "17�s�ځu�ӌ����L�����v";
        else if ("table.h18.KINYU_DT".equals(code)) obj = "18�s�ځu�ӌ����L�����v";
        else if ("table.h19.KINYU_DT".equals(code)) obj = "19�s�ځu�ӌ����L�����v";
        else if ("table.h20.KINYU_DT".equals(code)) obj = "20�s�ځu�ӌ����L�����v";
        else if ("table.h21.KINYU_DT".equals(code)) obj = "21�s�ځu�ӌ����L�����v";
        else if ("page".equals(code)) obj = "�y�[�W�ԍ�";

        return obj;
    }

    /**
     * ��t�ӌ���1�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatIkenshoShien1(String code, Object obj){
        // ��t�ӌ���1�y�[�W��
        if ("Label27".equals(code)) obj = "�\���ҏ��E���ʁu���v�@���o��";
        else if ("Label26".equals(code)) obj = "�\���ҏ��E���ʁu�j�v�@���o��";
        else if ("Label25".equals(code)) obj = "�\���ҏ��E���N�����u���a�v�@���o��";
        else if ("Label24".equals(code)) obj = "�\���ҏ��E���N�����u�吳�v�@���o��";
        else if ("Label23".equals(code)) obj = "�\���ҏ��E���N�����u�����v�@���o��";
        else if ("PatienBirthShowa".equals(code)) obj = "�\���ҏ��E���N�����u���a�v�@��";
        else if ("PatienBirthTaisho".equals(code)) obj = "�\���ҏ��E���p�Ґ��N�����u�吳�v�@��";
        else if ("Label21".equals(code)) obj = "�P.���a�Ɋւ���ӌ��E��i1�j�f�f���v�@���o��2";
        else if ("Label16".equals(code)) obj = "��Ë@�֏��ݒn�@����";
        else if ("Label15".equals(code)) obj = "��Ë@�֖��@����";
        else if ("Label14".equals(code)) obj = "��t�����@����";
        else if ("Label1".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u(�P)�s����̏�Q�̗L�� �i�Y�����鍀�ڑS�ă`�F�b�N�j��@���o��";
        else if ("Label20".equals(code)) obj = "��t�ӌ����@���o��";
        else if ("Label6".equals(code)) obj = "��t�ӌ����@�y�[�W��";
        else if ("Label8".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��@���o��1";
        else if ("Label9".equals(code)) obj = "�P. ���a�Ɋւ���ӌ� ���o��";
        else if ("Label10".equals(code)) obj = "�Q. ���ʂȈ�Á@���o��2";
        else if ("Label11".equals(code)) obj = "�Q. ���ʂȈ�Á@���o��1";
        else if ("PatienBirthMeiji".equals(code)) obj = "�\���ҏ��E���N�����u�����v�@��";
        else if ("PatientSexMale".equals(code)) obj = "�\���ҏ��E���ʁu�j�v�@��";
        else if ("PatientSexFemale".equals(code)) obj = "�\���ҏ��E���ʁu���v�@��";
        else if ("Shape93".equals(code)) obj = "�L�����@����";
        else if ("ShobyoKeika".equals(code)) obj = "�P.���a�Ɋւ���ӌ��E�u��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e�v�@���e";
        else if ("ShojoFuanteiJokyo".equals(code)) obj = "�P.���a�Ɋւ���ӌ��E�u(�Q) �Ǐ�Ƃ��Ă̈��萫�v�@���e";
        else if ("Label5".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���v�@���o��";
        else if ("Label87".equals(code)) obj = "�R�D�S�g�̏�ԂɊւ���ӌ��E�u�����f�̗L���v�@���o��";
        else if ("SeishinShinkeiName".equals(code)) obj = "�R�D�S�g�̏�ԂɊւ���ӌ��E�u(�Q) ���_��_�o�Ǐ�̗L���v�@�Ǐ�";
        else if ("SeishinShinkeiSenmoniName".equals(code)) obj = "�R�D�S�g�̏�ԂɊւ���ӌ��E�u�����f�̗L���v�@���e";
        else if ("SeishinShinkeiOtherName".equals(code)) obj = "�R�D�S�g�̏�ԂɊւ���ӌ��E�u(�Q) ���_��_�o�Ǐ�̗L���i���̑��j�v�@�Ǐ�";
        else if ("Label18".equals(code)) obj = "�\���ҏ��E���N�����u�����v�@���o��";
        else if ("PatienBirthHeisei".equals(code)) obj = "�\���ҏ��E���N�����u�����v�@��";
        else if ("INSURER_NO".equals(code)) obj = "�ی��Ҕԍ�";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
//        else if ("INSURED_NO".equals(code)) obj = "��ی��Ҕԍ�";
        else if ("INSURED_NO".equals(code)) obj = "�󋋎Ҕԍ�";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("INSURER_NO_LABEL".equals(code)) obj = "�ی��Ҕԍ� ���o��";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "�󋋎Ҕԍ� ���o��";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "�쐬���� ���o��";
        else if ("Label113".equals(code)) obj = "�ݑ�E�{�݋敪";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("SeishinShinkeiKiokuShogaiTanki".equals(code)) obj = "�R. �S�g�̏�ԂɊւ���ӌ��E�u(�Q) ���_��_�o�Ǐ�̗L���i�Z���j�v�@��";
        else if ("SeishinShinkeiKiokuShogaiChouki".equals(code)) obj = "�R. �S�g�̏�ԂɊւ���ӌ��E�u(�Q) ���_��_�o�Ǐ�̗L���i�����j�v�@��";
        else if ("ShochiKyuinCount".equals(code)) obj = "�Q. ���ʂȈ�ÁE�u�z�����u�@�񐔁v";
        else if ("shussei1".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u�o����1�v";
        else if ("shussei2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u�o����2�v";
        else if ("shussei3".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u�o����3�v";
        else if ("Grid10.h1.w1".equals(code)) obj = "�Q. ���ʂȈ�ÁE�u���u���e�v�@���o��";
        else if ("Grid10.h3.w1".equals(code)) obj = "�Q. ���ʂȈ�ÁE�u���ʂȑΉ��v�@���o��";
        else if ("Grid10.h4.w1".equals(code)) obj = "�Q. ���ʂȈ�ÁE�u���ււ̑Ή��v�@���o��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�\���ҏ��E�u�ӂ肪�ȁv�@���o��";
        else if ("Grid1.h1.w3".equals(code)) obj = "�\���ҏ��E�u�ӂ肪�ȁv";
        else if ("Grid1.h1.w12".equals(code)) obj = "�\���ҏ��E�u���v�@���o��";
        else if ("Grid1.h1.w11".equals(code)) obj = "�\���ҏ��E�u�X�֔ԍ�1�v";
        else if ("Grid1.h1.w17".equals(code)) obj = "�\���ҏ��E�u�X�֔ԍ�2�v";
        else if ("Grid1.h2.w1".equals(code)) obj = "�\���ҏ��E�u �\ �� �ҁv�@���o��";
        else if ("Grid1.h2.w3".equals(code)) obj = "�\���ҏ��E�u���Җ��v";
        else if ("Grid1.h2.w12".equals(code)) obj = "�\���ҏ��E�u�Z���v";
        else if ("Grid1.h3.w3".equals(code)) obj = "�\���ҏ��E���N�����u�N�v";
        else if ("Grid1.h3.w4".equals(code)) obj = "�\���ҏ��E���N�����u�N�v�@���o��";
        else if ("Grid1.h3.w5".equals(code)) obj = "�\���ҏ��E���N�����u���v";
        else if ("Grid1.h3.w6".equals(code)) obj = "�\���ҏ��E���N�����u���v�@���o��";
        else if ("Grid1.h3.w7".equals(code)) obj = "�\���ҏ��E���N�����u���v";
        else if ("Grid1.h3.w8".equals(code)) obj = "�\���ҏ��E���N�����u�����v�@���o��";
        else if ("Grid1.h3.w10".equals(code)) obj = "�\���ҏ��E���N�����u�N��v�@���o��";
        else if ("Grid1.h3.w11".equals(code)) obj = "�\���ҏ��E�u�A����v�@���o��";
        else if ("Grid1.h3.w17".equals(code)) obj = "�\���ҏ��E�u�d�b�ԍ�1�v";
        else if ("Grid1.h3.w19".equals(code)) obj = "�\���ҏ��E�u�d�b�ԍ�2�v";
        else if ("Grid1.h3.w21".equals(code)) obj = "�\���ҏ��E�u�d�b�ԍ�3�v";
        else if ("Grid4.h1.w1".equals(code)) obj = "�u��t�����v�@���o��";
        else if ("Grid4.h1.w3".equals(code)) obj = "�u��t�����v";
        else if ("Grid4.h2.w1".equals(code)) obj = "�u��Ë@�֖��v�@���o��";
        else if ("Grid4.h2.w4".equals(code)) obj = "�u��Ë@�֖��v";
        else if ("Grid4.h3.w1".equals(code)) obj = "�u��Ë@�֏��ݒn�v�@���o��";
        else if ("Grid4.h3.w5".equals(code)) obj = "�u��Ë@�֏��ݒn�v";
        else if ("Grid14.h3.w9".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E(�P)�s����̏�Q�̗L���u���̑��v�@���e";
        else if ("Grid7.h1.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u(�P) �f�f���v�@���o��";
        else if ("Grid7.h2.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u(�Q) �Ǐ�Ƃ��Ă̈��萫�v�@���o��";
        else if ("Grid7.h5.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u(�R) ��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e�v�@���o��";
        else if ("Grid8.h1.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u�f�f��1�v";
        else if ("Grid8.h1.w3".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����1�v�@���o��";
        else if ("Grid8.h1.w15".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����1-1�v";
        else if ("Grid8.h1.w14".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����1-2�v";
        else if ("Grid8.h1.w13".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u�N�v�@���o��";
        else if ("Grid8.h1.w12".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����1-3�v";
        else if ("Grid8.h1.w11".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u���v�@���o��";
        else if ("Grid8.h1.w10".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����1-4�v";
        else if ("Grid8.h1.w9".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u�����v�@���o��";
        else if ("Grid8.h2.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u�f�f��2�v";
        else if ("Grid8.h2.w3".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����2�v�@���o��";
        else if ("Grid8.h2.w15".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����2-1�v";
        else if ("Grid8.h2.w14".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����2-2�v";
        else if ("Grid8.h2.w13".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u�N�v�@���o��";
        else if ("Grid8.h2.w12".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����2-3�v";
        else if ("Grid8.h2.w11".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u���v�@���o��";
        else if ("Grid8.h2.w10".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����2-4�v";
        else if ("Grid8.h2.w9".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u�����v�@���o��";
        else if ("Grid8.h3.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u�f�f��3�v";
        else if ("Grid8.h3.w3".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����3�v�@���o��";
        else if ("Grid8.h3.w15".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����3-1�v";
        else if ("Grid8.h3.w14".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����3-2�v";
        else if ("Grid8.h3.w13".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u�N�v�@���o��";
        else if ("Grid8.h3.w12".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����3-3�v";
        else if ("Grid8.h3.w11".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u���v�@���o��";
        else if ("Grid8.h3.w10".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���ǔN����3-4�v";
        else if ("Grid8.h3.w9".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E���ǔN�����u�����v�@���o��";
        else if ("Grid13.h1.w1".equals(code)) obj = "�R. �S�g�̏�ԂɊւ���ӌ��E�u(�Q) ���_��_�o�Ǐ�̗L���v�@���o��";
        else if ("Grid6.h1.w1".equals(code)) obj = "�u(�P) �ŏI�f�@���v�@���o��";
        else if ("Grid6.h1.w12".equals(code)) obj = "(�P) �ŏI�f�@���u�����v";
        else if ("Grid6.h1.w11".equals(code)) obj = "(�P) �ŏI�f�@���u�N�v";
        else if ("Grid6.h1.w10".equals(code)) obj = "(�P) �ŏI�f�@���u�N�v�@���o��";
        else if ("Grid6.h1.w9".equals(code)) obj = "(�P) �ŏI�f�@���u���v";
        else if ("Grid6.h1.w8".equals(code)) obj = "(�P) �ŏI�f�@���u���v�@���o��";
        else if ("Grid6.h1.w7".equals(code)) obj = "(�P) �ŏI�f�@���u���v";
        else if ("Grid6.h1.w6".equals(code)) obj = "(�P) �ŏI�f�@���u���v�@���o��";
        else if ("Grid6.h2.w1".equals(code)) obj = "�u(�Q) �ӌ����쐬�񐔁v�@���o��";
        else if ("Grid6.h5.w1".equals(code)) obj = "�u(�R) ���Ȏ�f�̗L���v�@���o��";
        else if ("Grid6.h3.w4".equals(code)) obj = "(�R) ���Ȏ�f�̗L���E�u���̑��v�@���e";
        else if ("Grid5.h1.w1".equals(code)) obj = "�u��Ë@�֘A����v�@���o��";
        else if ("Grid5.h1.w2".equals(code)) obj = "�u��Ë@�֘A����1�v";
        else if ("Grid5.h1.w4".equals(code)) obj = "�u��Ë@�֘A����2�v";
        else if ("Grid5.h1.w6".equals(code)) obj = "�u��Ë@�֘A����3�v";
        else if ("Grid5.h3.w1".equals(code)) obj = "�u��Ë@��FAX�v�@���o��";
        else if ("Grid5.h3.w2".equals(code)) obj = "�u��Ë@��FAX1�v";
        else if ("Grid5.h3.w4".equals(code)) obj = "�u��Ë@��FAX2�v";
        else if ("Grid5.h3.w6".equals(code)) obj = "�u��Ë@��FAX3�v";
        else if ("Grid9.h1.w1".equals(code)) obj = "���a�̌o�߁E���Ó��e�E������e1";
        else if ("Grid9.h1.w2".equals(code)) obj = "���a�̌o�߁E���Ó��e�E������e2";
        else if ("Grid9.h2.w1".equals(code)) obj = "���a�̌o�߁E���Ó��e�E������e3";
        else if ("Grid9.h2.w2".equals(code)) obj = "���a�̌o�߁E���Ó��e�E������e4";
        else if ("Grid9.h3.w1".equals(code)) obj = "���a�̌o�߁E���Ó��e�E������e5";
        else if ("Grid9.h3.w2".equals(code)) obj = "���a�̌o�߁E���Ó��e�E������e6";
        else if ("Grid11.h1.w1".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u1�v�@���o��";
        else if ("Grid11.h1.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���J�n1-1�v";
        else if ("Grid11.h1.w3".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���J�n1-2�v";
        else if ("Grid11.h1.w5".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���J�n1-3�v";
        else if ("Grid11.h1.w7".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��1-1�v";
        else if ("Grid11.h1.w8".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��1-2�v";
        else if ("Grid11.h1.w10".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��1-3�v";
        else if ("Grid11.h1.w13".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��1���a���v";
        else if ("Grid12.h1.w1".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u2�v�@���o��";
        else if ("Grid12.h1.w2".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���J�n2-1�v";
        else if ("Grid12.h1.w3".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���J�n2-2�v";
        else if ("Grid12.h1.w5".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���J�n2-3�v";
        else if ("Grid12.h1.w7".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��2-1�v";
        else if ("Grid12.h1.w8".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��2-2�v";
        else if ("Grid12.h1.w10".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��2-3�v";
        else if ("Grid12.h1.w13".equals(code)) obj = "�P. ���a�Ɋւ���ӌ��E�u���@���I��2���a���v";
        else if ("Grid2.h1.w1".equals(code)) obj = "�u�L�����v�@�����@���o��";
        else if ("Grid2.h1.w2".equals(code)) obj = "�u�L�����v�@����";
        else if ("Grid2.h1.w3".equals(code)) obj = "�u�L�����v�@�N";
        else if ("Grid2.h1.w4".equals(code)) obj = "�u�L�����v�@�N�@���o��";
        else if ("Grid2.h1.w5".equals(code)) obj = "�u�L�����v�@��";
        else if ("Grid2.h1.w6".equals(code)) obj = "�u�L�����v�@���@���o��";
        else if ("Grid2.h1.w7".equals(code)) obj = "�u�L�����v�@��";
        else if ("Grid2.h1.w8".equals(code)) obj = "�u�L�����v�@���@���o��";
        return obj;
    }

    /**
     * ��t�ӌ���2�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatIkenshoShien2(String code, Object obj){
        // ��t�ӌ���2�y�[�W��
        if ("NijikuSeishin".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u�񎲕]���F���_�Ǐ�v";
        else if ("INSURER_NO".equals(code)) obj = "�ی��Ҕԍ�";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
//        else if ("INSURED_NO".equals(code)) obj = "��ی��Ҕԍ�";
        else if ("INSURED_NO".equals(code)) obj = "�󋋎Ҕԍ�";
        // [ID:0000555][Tozo TANAKA] 2009/09/14 replace end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add begin �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("INSURER_NO_LABEL".equals(code)) obj = "�ی��Ҕԍ� ���o��";
        else if ("INSURERD_NO_LABEL".equals(code)) obj = "�󋋎Ҕԍ� ���o��";
        else if ("FD_OUTPUT_TIME_LABEL".equals(code)) obj = "�쐬���� ���o��";
        else if ("Label113".equals(code)) obj = "�ݑ�E�{�݋敪";
        // [ID:0000555][Masahiko Higuchi] 2009/09/17 add end �y2009�N�x�Ή��F�ǉ��Č��z��t�ӌ����̎󋋎Ҕԍ��Ή�
        else if ("Label7".equals(code)) obj = "�u�S. �T�[�r�X���p�Ɋւ���ӌ��v�@���o��";
        else if ("Label9".equals(code)) obj = "�u�T. ���̑����L���ׂ������v�@���o��";
        else if ("KansetsuItamiBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�֐߂̒ɂ݁u���ʁv�@����";
        else if ("IkenByoutaitaName".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E(�P)���݁A�����̉\���������a�ԂƂ��̑Ώ����j�u���̑��v";
        else if ("MahiOtherBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E��Ⴡu���̑��v�@����";
        else if ("KoushukuOtherBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�֐߂̍S�k�u���̑����ʁv";
        else if ("JokusouBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E��ጁu���ʁv";
        else if ("Label178".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E���̑��̔畆�����u���ʁv�@���o��";
        else if ("HifuShikkanBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E���̑��̔畆�����u���ʁv";
        else if ("KinouHyoukaTitle".equals(code)) obj = "�T. ���̑����L���ׂ������E�u<���_��Q�̋@�\�]��>�v�@���o��";
        else if ("NijikuNoryoku".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u�񎲕]���F�\�͏�Q�v";
        else if ("SeikatsuShokuji".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F�H���v";
        else if ("SeikatsuRhythm".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F�������Y���v";
        else if ("SeikatsuHosei".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F�ې��v";
        else if ("SeikatsuKinsenKanri".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F���K�Ǘ��v";
        else if ("SeikatsuFukuyakuKanri".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F����Ǘ��v";
        else if ("SeikatsuTaijinKankei".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F�ΐl�֌W�v";
        else if ("SeikatsuShakaiTekiou".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F�Љ�I�K����W����s���v";
        else if ("SeikatsuHanteiYear".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F���f�����v�@�N";
        else if ("SeikatsuHanteiMonth".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F���f�����v�@��";
        else if ("NijikuHanteiYear".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u�񎲕]���F���莞���v�@�N";
        else if ("NijikuHanteiMonth".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u�񎲕]���F���莞���v�@��";
        else if ("IkenKaigoOther".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E(�Q)���T�[�r�X�i�z�[���w���v�T�[�r�X���j�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ����u���̑��v";
        else if ("KinryokuTeikaBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�ؗ͂̒ቺ�u���ʁv";
        else if ("NijikuHanteiEra".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u�񎲕]���F���莞���v�@����";
        else if ("SeikatsuHanteiEra".equals(code)) obj = "�T. ���̑����L���ׂ������E<���_��Q�̋@�\�]��>�u������Q�]���F���f�����v�@����";
        else if ("ShishikessonBui".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�l�������u���ʁv";
        else if ("Grid3.h1.w1".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u(�R)�g�̂̏�ԁv�@���o��";
        else if ("Grid15.h1.w1".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E�u(�R)�����ǂ̗L�� (�L�̏ꍇ�͋�̓I�ɋL�����ĉ�����)�v�@���o��";
        else if ("Grid16.h1.w5".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E�u(�R)�����ǂ̗L���v�@�ڍ�";
        else if ("Grid8.h1.w1".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E�u(�P)���݁A�����̉\���������a�ԂƂ��̑Ώ����j�v�@���o��";
        else if ("Grid10.h1.w2".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E�u�Ώ����j�v";
        else if ("Grid23.h1.w1".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u���Ă񂩂񁄁v�@���o��";
        else if ("Grid4.h1.w1".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u�����r�i�v�@���o��";
        else if ("Grid4.h1.w2".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u�g���v";
        else if ("Grid4.h1.w3".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u�̏d�v�@���o��";
        else if ("Grid4.h1.w4".equals(code)) obj = "�R.�S�g�̏�ԂɊւ���ӌ��E�u�̏d�v";
        else if ("Grid13.h1.w1".equals(code)) obj = "�S. �T�[�r�X���p�Ɋւ���ӌ��E�u(�Q)���T�[�r�X�i�z�[���w���v�T�[�r�X���j�̗��p���Ɋւ����w�I�ϓ_����̗��ӎ����v�@���o��";
        else if ("Grid2.h1.w1".equals(code)) obj = "�\���ҏ��E�u���Ҏ����v";
        else if ("Grid2.h1.w3".equals(code)) obj = "�\���ҏ��E�u�N��v";
        else if ("Grid2.h1.w4".equals(code)) obj = "�\���ҏ��E�u�N��v�@���o��";
        else if ("Grid2.h1.w5".equals(code)) obj = "�\���ҏ��E���N�����u�����v";
        else if ("Grid2.h1.w6".equals(code)) obj = "�\���ҏ��E���N�����u�N�v";
        else if ("Grid2.h1.w7".equals(code)) obj = "�\���ҏ��E���N�����u�N�v�@���o��";
        else if ("Grid2.h1.w8".equals(code)) obj = "�\���ҏ��E���N�����u���v";
        else if ("Grid2.h1.w9".equals(code)) obj = "�\���ҏ��E���N�����u���v�@���o��";
        else if ("Grid2.h1.w10".equals(code)) obj = "�\���ҏ��E���N�����u���v";
        else if ("Grid2.h1.w11".equals(code)) obj = "�\���ҏ��E���N�����u���v�@���o��";
        return obj;
    }

    //  [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    /**
     * ���ʖK��Ō�w�����̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatTokubetsuShijisho(String code, Object obj){
        if ("TENTEKI".equals(code)) obj = "�_�H���ˎw�����e";
        else if ("Grid3".equals(code)) obj = "�_�H���ˎw�����e�@�g";
        else if ("Grid3.h2.w1".equals(code)) obj = "�_�H���ˎw�����e�@���o��";
        else if ("SyojoSyusoGrid".equals(code)) obj = "�Ǐ�E��i�@�g";
        else if ("SyojoSyusoGrid.h2.w1".equals(code)) obj = "�Ǐ�E��i�@���o��";
        else if ("SYOJYO".equals(code)) obj = "�Ǐ�E��i";
        else if ("RyuiShijiGrid".equals(code)) obj = "���ӎ����y�юw�������@�g";
        else if ("Label17".equals(code)) obj = "���ӎ����y�юw�������@���o��";
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace begin �y��Q�z���ʎw�����̒��[�󎚕����Ɍ뎚  
//        else if ("Label18".equals(code)) obj = "�i���F�_�H���˖�̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_������΋L�ڂ��Ă��������B�j�@���o��";
        else if ("Label18".equals(code)) obj = "�i���F�_�H���˖�̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_������΋L�ڂ��Ă��������B�j�@���o��";
//      [ID:0000558][Tozo TANAKA] 2009/10/13 replace end �y��Q�z���ʎw�����̒��[�󎚕����Ɍ뎚  
        else if ("RYUI".equals(code)) obj = "���ӎ����y�юw������";
        else if ("Label10".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("Label8".equals(code)) obj = "���N�����E�����@�u��v�@���o��";
        else if ("Label7".equals(code)) obj = "���N�����E�����@�u����@���o��";
        else if ("Shape27".equals(code)) obj = "���퐶�������x�E�Q������x�@�u�����@�^�v";
        else if ("Label9".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("Shape1".equals(code)) obj = "���N�����E�����@�u���v�@��";
        else if ("Shape2".equals(code)) obj = "���N�����E�����@�u��v�@��";
        else if ("Shape3".equals(code)) obj = "���N�����E�����@�u���v�@��";
        else if ("Shape4".equals(code)) obj = "���N�����E�����@�u���v�@��";
        else if ("Label1".equals(code)) obj = "�u��L�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B�v�@���o��";
        else if ("KinyuDateLabell".equals(code)) obj = "�쐬�N�����@�u����  �N  ��  ���v";
        else if ("Label3".equals(code)) obj = "�K��Ō�X�e�[�V�����@�u��v�@���o��";
        else if ("IryoKikanLabel".equals(code)) obj = "�u��Ë@�֖��v�@���o��";
        else if ("Label5".equals(code)) obj = "�u��Ë@�ֈ�t�����v�@���o��";
        else if ("StationNameLabel".equals(code)) obj = "�K��Ō�X�e�[�V������";
        else if ("IryoKikanGrid.h1.w2".equals(code)) obj = "��Ë@�֖�";
        else if ("IryoKikanGrid.h2.w1".equals(code)) obj = "�u�Z�@�@�@���v�@���o��";
        else if ("IryoKikanGrid.h2.w2".equals(code)) obj = "��Ë@�֏Z��";
        else if ("IryoKikanGrid.h3.w1".equals(code)) obj = "�u�d�@�@�@�b�v�@���o��";
        else if ("IryoKikanGrid.h3.w2".equals(code)) obj = "��Ë@�ց@�d�b�ԍ�";
        else if ("IryoKikanGrid.h4.w1".equals(code)) obj = "�u�i�e�`�w�j�v�@���o��";
        else if ("IryoKikanGrid.h4.w2".equals(code)) obj = "��Ë@�ց@�e�`�w�ԍ�";
        else if ("IryoKikanGrid.h5.w2".equals(code)) obj = "��Ë@�ֈ�t����";
        else if ("SijiDateGrid".equals(code)) obj = "�K��Ō�w�����ԁE�_�H���ˎw�����ԁ@�g";
        else if ("SijiDateGrid.h1.w14".equals(code)) obj = "�u�K��Ō�w�����ԁv�@���o��";
        else if ("SijiDateGrid.h1.w2".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N";
        else if ("SijiDateGrid.h1.w3".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N�@���o��";
        else if ("SijiDateGrid.h1.w4".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("SijiDateGrid.h1.w5".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("SijiDateGrid.h1.w6".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("SijiDateGrid.h1.w7".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("SijiDateGrid.h1.w8".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N";
        else if ("SijiDateGrid.h1.w9".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N�@���o��";
        else if ("SijiDateGrid.h1.w10".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("SijiDateGrid.h1.w11".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("SijiDateGrid.h1.w12".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("SijiDateGrid.h1.w13".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("SijiDateGrid.h2.w14".equals(code)) obj = "�u�_�H���ˎw�����ԁv�@���o��";
        else if ("SijiDateGrid.h2.w2".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N";
        else if ("SijiDateGrid.h2.w3".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N�@���o��";
        else if ("SijiDateGrid.h2.w4".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("SijiDateGrid.h2.w5".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("SijiDateGrid.h2.w6".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("SijiDateGrid.h2.w7".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("SijiDateGrid.h2.w8".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N";
        else if ("SijiDateGrid.h2.w9".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N�@���o��";
        else if ("SijiDateGrid.h2.w10".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("SijiDateGrid.h2.w11".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("SijiDateGrid.h2.w12".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("SijiDateGrid.h2.w13".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("KanjyaGrid.h1.w1".equals(code)) obj = "�u���Ҏ����v�@���o��";
        else if ("KanjyaGrid.h1.w2".equals(code)) obj = "���Ҏ���";
        else if ("KanjyaGrid.h1.w3".equals(code)) obj = "�u���N�����v";
        else if ("KanjyaGrid.h1.w5".equals(code)) obj = "���N�����E�N";
        else if ("KanjyaGrid.h1.w6".equals(code)) obj = "���N�����E�N�@���o��";
        else if ("KanjyaGrid.h1.w7".equals(code)) obj = "���N�����E��";
        else if ("KanjyaGrid.h1.w8".equals(code)) obj = "���N�����E���@���o��";
        else if ("KanjyaGrid.h1.w9".equals(code)) obj = "���N�����E��";
        else if ("KanjyaGrid.h1.w10".equals(code)) obj = "���N�����E���@���o��";
        else if ("KanjyaGrid.h1.w12".equals(code)) obj = "�΁j";
        else if ("Grid4".equals(code)) obj = "�u�ً}���̘A����v�@�g";
        else if ("Grid4.h2.w1".equals(code)) obj = "�u�ً}���̘A����v�@���o��";
        else if ("KINKYU".equals(code)) obj = "�ً}���̘A����";
        else if ("TITLE_TOKUBETU".equals(code)) obj = "���ʖK��Ō�w�����@���o��";
        else if ("TITLE_TENTEKI".equals(code)) obj = "�ݑ�ҖK��_�H���ˎw�����@���o��";
        else if ("TITLE_TOKUBETU_TENTEKI".equals(code)) obj = "���ʖK��Ō�w�����E�ݑ�ҖK��_�H���ˎw�����@���o��";
        return obj;
    }
    //  [ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
    
    
    //[ID:0000639][Shin Fujihara] 2011/03 add begin
    /**
     * �K��Ō�w�����i��Ë@�ցj1�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatShijisho_M1(String code, Object obj){
    	
    	if ("title".equals(code)) obj = "�u�K��Ō�w�����v�@���o��";
    	
        else if ("Grid1".equals(code)) obj = "�K��Ō�w�����ԁE�_�H���ˎw�����ԁ@�g";
        else if ("Grid1.h1.w14".equals(code)) obj = "�u�K��Ō�w�����ԁv�@���o��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N";
        else if ("Grid1.h1.w3".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h1.w4".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w5".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w6".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w7".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w8".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N";
        else if ("Grid1.h1.w9".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N�@���o��";
        else if ("Grid1.h1.w10".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w11".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h1.w12".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w13".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w14".equals(code)) obj = "�u�_�H���ˎw�����ԁv�@���o��";
        else if ("Grid1.h2.w2".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N";
        else if ("Grid1.h2.w3".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h2.w4".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w5".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w6".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w7".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w8".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N";
        else if ("Grid1.h2.w9".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N�@���o��";
        else if ("Grid1.h2.w10".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w11".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w12".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w13".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
    	
        else if ("Grid2.h1.w1".equals(code)) obj = "�u���Ҏ����v�@���o��";
        else if ("Grid2.h1.w2".equals(code)) obj = "���Ҏ���";
        else if ("Grid2.h1.w3".equals(code)) obj = "�u���N�����v";
        else if ("Grid2.h1.w5".equals(code)) obj = "���N�����E�N";
        else if ("Grid2.h1.w6".equals(code)) obj = "���N�����E�N�@���o��";
        else if ("Grid2.h1.w7".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w8".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w9".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w10".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w12".equals(code)) obj = "�΁j";
        
        else if ("Label7".equals(code)) obj = "���N�����E�����@�u����@���o��";
        else if ("Label8".equals(code)) obj = "���N�����E�����@�u��v�@���o��";
        else if ("Label9".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("Label10".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
    	
    	
        else if ("Grid3.h1.w1".equals(code)) obj = "�u���ҏZ���v�@���o��";
        else if ("Grid3.h1.w3".equals(code)) obj = "�u���v�@���o��";
        else if ("Grid3.h1.w4".equals(code)) obj = "���җX�֔ԍ�";
        else if ("Grid3.h2.w4".equals(code)) obj = "���ҏZ��";
        else if ("Grid3.h3.w2".equals(code)) obj = "�d�b�ԍ�";
        else if ("Grid3.h3.w6".equals(code)) obj = "�u�d�b�v�@���o��";

        else if ("Grid4.h1.w1".equals(code)) obj = "�u�傽�鏝�a���v�@���o��";
        else if ("Grid4.h1.w2".equals(code)) obj = "�傽�鏝�a��";
    	
        else if ("Grid5.h1.w1".equals(code)) obj = "�u���݂̏󋵁v�@���o��";
    	
        else if ("Grid6.h1.w1".equals(code)) obj = "�u�Ǐ�E���Ï�ԁv�@���o��";
        else if ("lblJyotai".equals(code)) obj = "�Ǐ�E���Ï��";

        else if ("sickMedicines8.h1.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u���^���̖�܂̗p�@�E�p�ʁv�@���o��";
        else if ("sickMedicines8.h1.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v�@���o��";
        else if ("sickMedicines8.h1.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v";
        else if ("sickMedicines8.h1.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v�@���o��";
        else if ("sickMedicines8.h1.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v";
        else if ("sickMedicines8.h2.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v�@���o��";
        else if ("sickMedicines8.h2.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v";
        else if ("sickMedicines8.h2.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v�@���o��";
        else if ("sickMedicines8.h2.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v";
        else if ("sickMedicines8.h3.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v�@���o��";
        else if ("sickMedicines8.h3.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v";
        else if ("sickMedicines8.h3.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v�@���o��";
        else if ("sickMedicines8.h3.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v";
        else if ("sickMedicines8.h4.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�V�v�@���o��";
        else if ("sickMedicines8.h4.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�V�v";
        else if ("sickMedicines8.h4.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�W�v�@���o��";
        else if ("sickMedicines8.h4.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�W�v";
    	
        else if ("Grid8".equals(code)) obj = "���퐶���@�g";
        else if ("Grid8.h1.w1".equals(code)) obj = "���퐶�������x�@�u���퐶���v�@���o��";
        else if ("Grid8.h1.w2".equals(code)) obj = "�u�Q������x�v�@���o��";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "���퐶�������x�@�u�� �� �x�v�@���o��";
        else if ("Grid8.h2.w2".equals(code)) obj = "�u�F�m�ǂ̏󋵁v�@���o��";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " II��";
        else if ("Grid8.h2.w15".equals(code)) obj = " II��";
        else if ("Grid8.h2.w13".equals(code)) obj = " III��";
        else if ("Grid8.h2.w11".equals(code)) obj = " III��";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " �l";

        else if ("Label23".equals(code)) obj = "�u�v���F��̏󋵁v�@���o��";
        else if ("Grid8.h3.w21".equals(code)) obj = "�v�x��";
        else if ("Grid8.h3.w19".equals(code)) obj = "�v���";
    	
        else if ("Label18".equals(code)) obj = "�v���F��̏󋵁@�u�T �j�v";
        else if ("Label17".equals(code)) obj = "�v���F��̏󋵁@�u�S�v";
        else if ("Label16".equals(code)) obj = "�v���F��̏󋵁@�u�R�v";
        else if ("Label15".equals(code)) obj = "�v���F��̏󋵁@�u�Q�v";
        else if ("Label14".equals(code)) obj = "�v���F��̏󋵁@�u�P�v";
    	
        else if ("Label26".equals(code)) obj = "�u��ጂ̐[���v�@���o��";
        else if ("Grid15.h3.w22".equals(code)) obj = "��ጂ̐[���@�uNPUAP���ށv";
        else if ("Grid15.h3.w20".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�V�x�v";
        else if ("Grid15.h3.w17".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�W�x�v";
        else if ("Grid15.h3.w15".equals(code)) obj = "��ጂ̐[���@�uDESIGN���ށv";
        else if ("Grid15.h3.w13".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD3�v";
        else if ("Grid15.h3.w11".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD4�v";
        else if ("Grid15.h3.w9".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD5�v";
    	
        else if ("Grid9".equals(code)) obj = "�u�����E�g�p��Ë@�퓙�v�@�g";
        else if ("Grid9.h1.w1".equals(code)) obj = "�u�����E�g�p��Ë@�퓙�v�@���o��";
        else if ("Grid9.h1.w3".equals(code)) obj = "�P�D���������󗬑��u�E�u1�v�@���o��";
        else if ("Grid9.h1.w4".equals(code)) obj = "�P�D���������󗬑��u�E�u���������󗬑��u�v�@���o��";
        else if ("Grid9.h1.w7".equals(code)) obj = "�Q�D���͉t�������u�E�u2�v�@���o��";
        else if ("Grid9.h1.w8".equals(code)) obj = "�Q�D���͉t�������u�E�u���͉t�������u�v�@���o��";
        else if ("Grid9.h1.w15".equals(code)) obj = "�R�D�_�f�Ö@�E�u3�v�@���o��";
        else if ("Grid9.h1.w24".equals(code)) obj = "�R�D�_�f�Ö@�E�u�_�f�Ö@�i�v�@���o��";
        else if ("Grid9.h1.w20".equals(code)) obj = "�R�D�_�f�Ö@";
        else if ("Grid9.h1.w21".equals(code)) obj = "�R�D�_�f�Ö@�E�u l/min�v�@���o��";
        else if ("Grid9.h1.w22".equals(code)) obj = "�R�D�_�f�Ö@�E�u�j�v�@���o��";
        
        else if ("Grid9.h2.w3".equals(code)) obj = "�S�D�z����E�u4�v�@���o��";
        else if ("Grid9.h2.w4".equals(code)) obj = "�S�D�z����E�u�z����v�@���o��";
        else if ("Grid9.h2.w7".equals(code)) obj = "�T�D���S�Ö��h�{�E�u5�v�@���o��";
        else if ("Grid9.h2.w8".equals(code)) obj = "�T�D���S�Ö��h�{�E�u���S�Ö��h�{�v�@���o��";
        else if ("Grid9.h2.w15".equals(code)) obj = "�U�D�A�t�|���v�E�u6�v�@���o��";
        else if ("Grid9.h2.w24".equals(code)) obj = "�U�D�A�t�|���v�E�u�A�t�|���v�v�@���o��";
        
        else if ("Grid9.h3.w3".equals(code)) obj = "�V�D�o�ǉh�{�E�u7�v�@���o��";
        else if ("Grid9.h3.w4".equals(code)) obj = "�V�D�o�ǉh�{�E�u�o�ǉh�{�@�@�@�i�v�@���o��";
        else if ("Grid9.h3.w23".equals(code)) obj = "�V�D�o�ǉh�{";
        else if ("Grid9.h3.w9".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v�@���o��";
        else if ("Grid9.h3.w15".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v";
        else if ("Grid9.h3.w16".equals(code)) obj = "�V�D�o�ǉh�{�E�u�A�v�@���o��";
        else if ("Grid9.h3.w18".equals(code)) obj = "�V�D�o�ǉh�{�@��";
        else if ("Grid9.h3.w19".equals(code)) obj = "�V�D�o�ǉh�{�E�u����1������v�@���o��";
        else if ("Grid9.h3.w22".equals(code)) obj = "�V�D�o�ǉh�{�E�u�j�v�@���o��";
        
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
//        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
//        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
//        else if ("Grid9.h4.w9".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
//        else if ("Grid9.h4.w13".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
//        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
//        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
//        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i���ʁF�v�@���o��";
        else if ("Grid9.h4.w23".equals(code)) obj = "�W�D���u�J�e�[�e���@����";
        else if ("Grid9.h4.w12".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
        else if ("Grid9.h4.w15".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
        else if ("Grid9.h4.w17".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add end
        
        else if ("Grid9.h5.w3".equals(code)) obj = "�X�D�l�H�ċz��E�u9�v�@���o��";
        else if ("Grid9.h5.w4".equals(code)) obj = "�X�D�l�H�ċz��E�u�l�H�ċz��@�@�i�v�@���o��";
        else if ("Grid9.h5.w8".equals(code)) obj = "�X�D�l�H�ċz��@���";
        else if ("Grid9.h5.w10".equals(code)) obj = "�X�D�l�H�ċz��E�u�F�ݒ�v�@���o��";
        else if ("Grid9.h5.w6".equals(code)) obj = "�X�D�l�H�ċz��@�ݒ�";
        else if ("Grid9.h5.w22".equals(code)) obj = "�X�D�l�H�ċz��E�u�j�v�@���o��";
        
        else if ("Grid9.h6.w3".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u10��@���o��";
        else if ("Grid9.h6.w4".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�C�ǃJ�j���[���i�T�C�Y�v�@���o��";
        else if ("Grid9.h6.w9".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���@�T�C�Y";
        else if ("Grid9.h6.w11".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
//        else if ("Grid9.h6.w12".equals(code)) obj = "�P�P�D�h���[���E�u11�v�@���o��";
//        else if ("Grid9.h6.w13".equals(code)) obj = "�P�P�D�h���[���E�u�h���[���i���ʁF�v�@���o��";
//        else if ("Grid9.h6.w20".equals(code)) obj = "�P�P�D�h���[���@����";
//        else if ("Grid9.h6.w22".equals(code)) obj = "�P�P�D�h���[���E�u�j�v�@���o��";
//        else if ("Grid9.h7.w3".equals(code)) obj = "�P�Q�D�l�H���E�u12�v�@���o��";
//        else if ("Grid9.h7.w4".equals(code)) obj = "�P�Q�D�l�H���E�u�l�H���v�@���o��";
//        else if ("Grid9.h7.w5".equals(code)) obj = "�P�R�D�l�H�N���E�u13�v�@���o��";
//        else if ("Grid9.h7.w23".equals(code)) obj = "�P�R�D�l�H�N���E�u�l�H�N���v�@���o��";
//        else if ("Grid9.h7.w10".equals(code)) obj = "�P�S�E���̑��E�u14�v�@���o��";
//        else if ("Grid9.h7.w11".equals(code)) obj = "�P�S�E���̑��E�u���̑��i�v�@���o��";
//        else if ("Grid9.h7.w14".equals(code)) obj = "�P�S�D���̑�";
//        else if ("Grid9.h7.w22".equals(code)) obj = "�P�S�E���̑��E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
        else if ("Grid9.h7.w3".equals(code)) obj = "�P�P�D�l�H���E�u11�v�@���o��";
        else if ("Grid9.h7.w4".equals(code)) obj = "�P�P�D�l�H���E�u�l�H���v�@���o��";
        else if ("Grid9.h7.w5".equals(code)) obj = "�P�Q�D�l�H�N���E�u12�v�@���o��";
        else if ("Grid9.h7.w23".equals(code)) obj = "�P�Q�D�l�H�N���E�u�l�H�N���v�@���o��";
        else if ("Grid9.h7.w10".equals(code)) obj = "�P�R�E���̑��E�u13�v�@���o��";
        else if ("Grid9.h7.w11".equals(code)) obj = "�P�R�E���̑��E�u���̑��i�v�@���o��";
        else if ("Grid9.h7.w14".equals(code)) obj = "�P�R�D���̑�";
        else if ("Grid9.h7.w22".equals(code)) obj = "�P�R�E���̑��E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid7.h1.w1".equals(code)) obj = "�����ł֑�����";
    	
        return obj;
    }
    
    /**
     * �K��Ō�w�����i��Ë@�ցj2�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatShijisho_M2(String code, Object obj){
    	
    	if ("patientData.h1.w2".equals(code)) obj = "�����E�N��u�N��v���o��";
    	else if ("patientData.h1.w4".equals(code)) obj = "�����E�N��u�΁v���o��";
    	
        else if ("Grid10".equals(code)) obj = "���ӎ����y�юw�������@�g";
        else if ("Grid10.h1.w1".equals(code)) obj = "�u���ӎ����y�юw�������v�@���o��";
        else if ("Grid10.h13.w1".equals(code)) obj = "�u I �×{�����w����̗��ӎ����v�@���o��";
        else if ("Grid10.h3.w1".equals(code)) obj = "�uII�v �@���o��";
    	
        else if ("Label19".equals(code)) obj = "�u�P. ���n�r���e�[�V�����v�@���o��";
        else if ("Label20".equals(code)) obj = "�u�Q. ��ጂ̏��u���v�@���o��";
        else if ("Label21".equals(code)) obj = "�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��v�@���o��";
        else if ("Label22".equals(code)) obj = "�u�S. ���̑��v�@���o��";
    	
        else if ("lblRyoyo".equals(code)) obj = " I �×{�����w����̗��ӎ���";
        else if ("lblRiha".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�P. ���n�r���e�[�V�����v";
        else if ("lblJyokusyo".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�Q. ��ጂ̏��u���v";
        else if ("lblSochaku".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��v";
    	else if ("lblEtc".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�S�D���̑��v";
    	
        else if ("Label24".equals(code)) obj = "�u�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j�v�@���o��";
        else if ("lblZaitaku".equals(code)) obj = "�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j";
    	
        else if ("Grid11.h1.w1".equals(code)) obj = "�u�ً}���̘A����v�@���o��";
        else if ("Grid11.h1.w2".equals(code)) obj = "�ً}���̘A����";
        else if ("Grid11.h2.w1".equals(code)) obj = "�u�s�ݎ��̑Ή��@�v�@���o��";
        else if ("Grid11.h2.w2".equals(code)) obj = "�s�ݎ��̑Ή��@";
    	
        else if ("Grid12.h1.w1".equals(code)) obj = "���L���ׂ����ӎ����E�u���L���ׂ����ӎ����v�@���o��";
        else if ("Grid12.h1.w6".equals(code)) obj = "���L���ׂ����ӎ����E�u�i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��ĉ������B�j�v�@���o��";
        else if ("lblTokki".equals(code)) obj = "���L���ׂ����ӎ���";
        else if ("Grid12.h3.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���̖K��Ō�X�e�[�V�����ւ̎w���v�@���o��";
        else if ("Grid12.h4.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h4.w2".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h4.w4".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h4.w5".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�F�w��K��Ō�X�e�[�V�������v�@���o��";
        else if ("Grid12.h4.w8".equals(code)) obj = "���̖K��Ō�X�e�[�V����";
        else if ("Grid12.h4.w7".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�j�v�@���o��";
        
    	//[ID:0000732][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        else if ("Grid12.h6.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���v�@���o��";
        else if ("Grid12.h8.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h8.w2".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h8.w4".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h8.w5".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�F�K���쎖�Ə����v�@���o��";
        else if ("Grid12.h8.w8".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə�";
        else if ("Grid12.h8.w7".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�j�v�@���o��";
    	//[ID:0000732][Shin Fujihara] 2012/04/20 add end �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    	
        else if ("Label1".equals(code)) obj = "�u��L�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B�v�@���o��";
        else if ("Label2".equals(code)) obj = "�쐬�N�����@�u����  �N  ��  ���v";
    	
        else if ("Grid13.h1.w1".equals(code)) obj = "�u��Ë@�֖��v�@���o��";
        else if ("Grid13.h1.w2".equals(code)) obj = "��Ë@�֖�";
        else if ("Grid13.h2.w1".equals(code)) obj = "�u�Z�@�@�@���v�@���o��";
        else if ("Grid13.h2.w2".equals(code)) obj = "��Ë@�֏Z��";
        else if ("Grid13.h3.w1".equals(code)) obj = "�u�d�@�@�@�b�v�@���o��";
        else if ("Grid13.h3.w2".equals(code)) obj = "��Ë@�ց@�d�b�ԍ�";
        else if ("Grid13.h4.w1".equals(code)) obj = "�u�i�e�`�w�j�v�@���o��";
        else if ("Grid13.h4.w2".equals(code)) obj = "��Ë@�ց@�e�`�w�ԍ�";
        else if ("Grid13.h5.w1".equals(code)) obj = "�u��Ë@�ֈ�t�����v�@���o��";
        else if ("Grid13.h5.w2".equals(code)) obj = "��Ë@�ֈ�t����";
    	
        else if ("Label3".equals(code)) obj = "�K��Ō�X�e�[�V�����@�u��v�@���o��";
        else if ("Label6".equals(code)) obj = "�K��Ō�X�e�[�V������";

        return obj;
    }
    
    
    /**
     * �K��Ō�w�����i���V�l�ی��{�݁j1�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatShijishoB_M1(String code, Object obj){
    	
    	if ("title".equals(code)) obj = "�u�K��Ō�w�����v�@���o��";
    	
        else if ("Grid1".equals(code)) obj = "�K��Ō�w�����ԁE�_�H���ˎw�����ԁ@�g";
        else if ("Grid1.h1.w14".equals(code)) obj = "�u�K��Ō�w�����ԁv�@���o��";
        else if ("Grid1.h1.w2".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N";
        else if ("Grid1.h1.w3".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h1.w4".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w5".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w6".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n��";
        else if ("Grid1.h1.w7".equals(code)) obj = "�K��Ō�w�����ԁ@�J�n���@���o��";
        else if ("Grid1.h1.w8".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N";
        else if ("Grid1.h1.w9".equals(code)) obj = "�K��Ō�w�����ԁ@�I���N�@���o��";
        else if ("Grid1.h1.w10".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w11".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h1.w12".equals(code)) obj = "�K��Ō�w�����ԁ@�I����";
        else if ("Grid1.h1.w13".equals(code)) obj = "�K��Ō�w�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w14".equals(code)) obj = "�u�_�H���ˎw�����ԁv�@���o��";
        else if ("Grid1.h2.w2".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N";
        else if ("Grid1.h2.w3".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n�N�@���o��";
        else if ("Grid1.h2.w4".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w5".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w6".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n��";
        else if ("Grid1.h2.w7".equals(code)) obj = "�_�H���ˎw�����ԁ@�J�n���@���o��";
        else if ("Grid1.h2.w8".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N";
        else if ("Grid1.h2.w9".equals(code)) obj = "�_�H���ˎw�����ԁ@�I���N�@���o��";
        else if ("Grid1.h2.w10".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w11".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
        else if ("Grid1.h2.w12".equals(code)) obj = "�_�H���ˎw�����ԁ@�I����";
        else if ("Grid1.h2.w13".equals(code)) obj = "�_�H���ˎw�����ԁ@�I�����@���o��";
    	
        else if ("Grid2.h1.w1".equals(code)) obj = "�u�����Ҏ����v�@���o��";
        else if ("Grid2.h1.w2".equals(code)) obj = "�����Ҏ���";
        else if ("Grid2.h1.w3".equals(code)) obj = "�u���N�����v";
        else if ("Grid2.h1.w5".equals(code)) obj = "���N�����E�N";
        else if ("Grid2.h1.w6".equals(code)) obj = "���N�����E�N�@���o��";
        else if ("Grid2.h1.w7".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w8".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w9".equals(code)) obj = "���N�����E��";
        else if ("Grid2.h1.w10".equals(code)) obj = "���N�����E���@���o��";
        else if ("Grid2.h1.w12".equals(code)) obj = "�΁j";
        
        else if ("Label7".equals(code)) obj = "���N�����E�����@�u����@���o��";
        else if ("Label8".equals(code)) obj = "���N�����E�����@�u��v�@���o��";
        else if ("Label9".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
        else if ("Label10".equals(code)) obj = "���N�����E�����@�u���v�@���o��";
    	
    	
        else if ("Grid3.h1.w1".equals(code)) obj = "�u�����ҏZ���v�@���o��";
        else if ("Grid3.h1.w3".equals(code)) obj = "�u���v�@���o��";
        else if ("Grid3.h1.w4".equals(code)) obj = "�����җX�֔ԍ�";
        else if ("Grid3.h2.w4".equals(code)) obj = "�����ҏZ��";
        else if ("Grid3.h3.w2".equals(code)) obj = "�d�b�ԍ�";
        else if ("Grid3.h3.w6".equals(code)) obj = "�u�d�b�v�@���o��";

        else if ("Grid4.h1.w1".equals(code)) obj = "�u�傽�鏝�a���v�@���o��";
        else if ("Grid4.h1.w2".equals(code)) obj = "�傽�鏝�a��";
    	
        else if ("Grid5.h1.w1".equals(code)) obj = "�u���݂̏󋵁v�@���o��";
    	
        else if ("Grid6.h1.w1".equals(code)) obj = "�u�Ǐ�E���Ï�ԁv�@���o��";
        else if ("lblJyotai".equals(code)) obj = "�Ǐ�E���Ï��";

        else if ("sickMedicines8.h1.w1".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u���^���̖�܂̗p�@�E�p�ʁv�@���o��";
        else if ("sickMedicines8.h1.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v�@���o��";
        else if ("sickMedicines8.h1.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�P�v";
        else if ("sickMedicines8.h1.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v�@���o��";
        else if ("sickMedicines8.h1.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�Q�v";
        else if ("sickMedicines8.h2.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v�@���o��";
        else if ("sickMedicines8.h2.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�R�v";
        else if ("sickMedicines8.h2.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v�@���o��";
        else if ("sickMedicines8.h2.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�S�v";
        else if ("sickMedicines8.h3.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v�@���o��";
        else if ("sickMedicines8.h3.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�T�v";
        else if ("sickMedicines8.h3.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v�@���o��";
        else if ("sickMedicines8.h3.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�U�v";
        else if ("sickMedicines8.h4.w5".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�V�v�@���o��";
        else if ("sickMedicines8.h4.w4".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�V�v";
        else if ("sickMedicines8.h4.w3".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�W�v�@���o��";
        else if ("sickMedicines8.h4.w2".equals(code)) obj = "���^���̖�܂̗p�@�E�p�ʁE�u�W�v";
    	
        else if ("Grid8".equals(code)) obj = "���퐶���@�g";
        else if ("Grid8.h1.w1".equals(code)) obj = "���퐶�������x�@�u���퐶���v�@���o��";
        else if ("Grid8.h1.w2".equals(code)) obj = "�u�Q������x�v�@���o��";
        else if ("Grid8.h1.w19".equals(code)) obj = " J1";
        else if ("Grid8.h1.w17".equals(code)) obj = " J2";
        else if ("Grid8.h1.w15".equals(code)) obj = " A1";
        else if ("Grid8.h1.w13".equals(code)) obj = " A2";
        else if ("Grid8.h1.w11".equals(code)) obj = " B1";
        else if ("Grid8.h1.w9".equals(code)) obj = " B2";
        else if ("Grid8.h1.w7".equals(code)) obj = " C1";
        else if ("Grid8.h1.w5".equals(code)) obj = " C2";
        else if ("Grid8.h2.w1".equals(code)) obj = "���퐶�������x�@�u�� �� �x�v�@���o��";
        else if ("Grid8.h2.w2".equals(code)) obj = "�u�F�m�ǂ̏󋵁v�@���o��";
        else if ("Grid8.h2.w19".equals(code)) obj = " I";
        else if ("Grid8.h2.w17".equals(code)) obj = " II��";
        else if ("Grid8.h2.w15".equals(code)) obj = " II��";
        else if ("Grid8.h2.w13".equals(code)) obj = " III��";
        else if ("Grid8.h2.w11".equals(code)) obj = " III��";
        else if ("Grid8.h2.w9".equals(code)) obj = " IV";
        else if ("Grid8.h2.w7".equals(code)) obj = " �l";

        else if ("Label23".equals(code)) obj = "�u�v���F��̏󋵁v�@���o��";
        else if ("Grid8.h3.w21".equals(code)) obj = "����";
        else if ("Grid8.h3.w19".equals(code)) obj = "�v�x��";
        else if ("Grid8.h3.w17".equals(code)) obj = "�v���";
    	
        else if ("Label18".equals(code)) obj = "�v���F��̏󋵁@�u�T �j�v";
        else if ("Label17".equals(code)) obj = "�v���F��̏󋵁@�u�S�v";
        else if ("Label16".equals(code)) obj = "�v���F��̏󋵁@�u�R�v";
        else if ("Label15".equals(code)) obj = "�v���F��̏󋵁@�u�Q�v";
        else if ("Label14".equals(code)) obj = "�v���F��̏󋵁@�u�P�v";
    	
        else if ("Label26".equals(code)) obj = "�u��ጂ̐[���v�@���o��";
        else if ("Grid15.h3.w22".equals(code)) obj = "��ጂ̐[���@�uNPUAP���ށv";
        else if ("Grid15.h3.w20".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�V�x�v";
        else if ("Grid15.h3.w17".equals(code)) obj = "��ጂ̐[���ENPUAP���ށ@�u�W�x�v";
        else if ("Grid15.h3.w15".equals(code)) obj = "��ጂ̐[���@�uDESIGN���ށv";
        else if ("Grid15.h3.w13".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD3�v";
        else if ("Grid15.h3.w11".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD4�v";
        else if ("Grid15.h3.w9".equals(code)) obj = "��ጂ̐[���EDESIGN���ށ@�uD5�v";
    	
        else if ("Grid9".equals(code)) obj = "�u�����E�g�p��Ë@�퓙�v�@�g";
        else if ("Grid9.h1.w1".equals(code)) obj = "�u�����E�g�p��Ë@�퓙�v�@���o��";
        else if ("Grid9.h1.w3".equals(code)) obj = "�P�D���������󗬑��u�E�u1�v�@���o��";
        else if ("Grid9.h1.w4".equals(code)) obj = "�P�D���������󗬑��u�E�u���������󗬑��u�v�@���o��";
        else if ("Grid9.h1.w7".equals(code)) obj = "�Q�D���͉t�������u�E�u2�v�@���o��";
        else if ("Grid9.h1.w8".equals(code)) obj = "�Q�D���͉t�������u�E�u���͉t�������u�v�@���o��";
        else if ("Grid9.h1.w15".equals(code)) obj = "�R�D�_�f�Ö@�E�u3�v�@���o��";
        else if ("Grid9.h1.w24".equals(code)) obj = "�R�D�_�f�Ö@�E�u�_�f�Ö@�i�v�@���o��";
        else if ("Grid9.h1.w20".equals(code)) obj = "�R�D�_�f�Ö@";
        else if ("Grid9.h1.w21".equals(code)) obj = "�R�D�_�f�Ö@�E�u l/min�v�@���o��";
        else if ("Grid9.h1.w22".equals(code)) obj = "�R�D�_�f�Ö@�E�u�j�v�@���o��";
        
        else if ("Grid9.h2.w3".equals(code)) obj = "�S�D�z����E�u4�v�@���o��";
        else if ("Grid9.h2.w4".equals(code)) obj = "�S�D�z����E�u�z����v�@���o��";
        else if ("Grid9.h2.w7".equals(code)) obj = "�T�D���S�Ö��h�{�E�u5�v�@���o��";
        else if ("Grid9.h2.w8".equals(code)) obj = "�T�D���S�Ö��h�{�E�u���S�Ö��h�{�v�@���o��";
        else if ("Grid9.h2.w15".equals(code)) obj = "�U�D�A�t�|���v�E�u6�v�@���o��";
        else if ("Grid9.h2.w24".equals(code)) obj = "�U�D�A�t�|���v�E�u�A�t�|���v�v�@���o��";
        
        else if ("Grid9.h3.w3".equals(code)) obj = "�V�D�o�ǉh�{�E�u7�v�@���o��";
        else if ("Grid9.h3.w4".equals(code)) obj = "�V�D�o�ǉh�{�E�u�o�ǉh�{�@�@�@�i�v�@���o��";
        else if ("Grid9.h3.w23".equals(code)) obj = "�V�D�o�ǉh�{";
        else if ("Grid9.h3.w9".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v�@���o��";
        else if ("Grid9.h3.w15".equals(code)) obj = "�V�D�o�ǉh�{�E�u�F�`���[�u�T�C�Y�v";
        else if ("Grid9.h3.w16".equals(code)) obj = "�V�D�o�ǉh�{�E�u�A�v�@���o��";
        else if ("Grid9.h3.w18".equals(code)) obj = "�V�D�o�ǉh�{�@��";
        else if ("Grid9.h3.w19".equals(code)) obj = "�V�D�o�ǉh�{�E�u����1������v�@���o��";
        else if ("Grid9.h3.w22".equals(code)) obj = "�V�D�o�ǉh�{�E�u�j�v�@���o��";
        
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
//        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
//        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
//        else if ("Grid9.h4.w9".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
//        else if ("Grid9.h4.w13".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
//        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
//        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
//        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
        //[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̗��u�J�e�[�e�����ʒǉ�
        else if ("Grid9.h4.w3".equals(code)) obj = "�W�D���u�J�e�[�e���E�u8�v�@���o��";
        else if ("Grid9.h4.w4".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i���ʁF�v�@���o��";
        else if ("Grid9.h4.w23".equals(code)) obj = "�W�D���u�J�e�[�e���@����";
        else if ("Grid9.h4.w12".equals(code)) obj = "�W�D���u�J�e�[�e���E�u���u�J�e�[�e���i�T�C�Y�v�@���o��";
        else if ("Grid9.h4.w15".equals(code)) obj = "�W�D���u�J�e�[�e���@�T�C�Y";
        else if ("Grid9.h4.w17".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�A�v�@���o��";
        else if ("Grid9.h4.w18".equals(code)) obj = "�W�D���u�J�e�[�e���@��";
        else if ("Grid9.h4.w19".equals(code)) obj = "�W�D���u�J�e�[�e���E�u ����1������v�@���o��";
        else if ("Grid9.h4.w22".equals(code)) obj = "�W�D���u�J�e�[�e���E�u�j�v�@���o��";
        //[ID:0000689][Shin Fujihara] 2012/03/12 add end
        
        else if ("Grid9.h5.w3".equals(code)) obj = "�X�D�l�H�ċz��E�u9�v�@���o��";
        else if ("Grid9.h5.w4".equals(code)) obj = "�X�D�l�H�ċz��E�u�l�H�ċz��@�@�i�v�@���o��";
        else if ("Grid9.h5.w8".equals(code)) obj = "�X�D�l�H�ċz��@���";
        else if ("Grid9.h5.w10".equals(code)) obj = "�X�D�l�H�ċz��E�u�F�ݒ�v�@���o��";
        else if ("Grid9.h5.w6".equals(code)) obj = "�X�D�l�H�ċz��@�ݒ�";
        else if ("Grid9.h5.w22".equals(code)) obj = "�X�D�l�H�ċz��E�u�j�v�@���o��";
        
        else if ("Grid9.h6.w3".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u10��@���o��";
        else if ("Grid9.h6.w4".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�C�ǃJ�j���[���i�T�C�Y�v�@���o��";
        else if ("Grid9.h6.w9".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���@�T�C�Y";
        else if ("Grid9.h6.w11".equals(code)) obj = "�P�O�D�C�ǃJ�j���[���E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
//        else if ("Grid9.h6.w12".equals(code)) obj = "�P�P�D�h���[���E�u11�v�@���o��";
//        else if ("Grid9.h6.w13".equals(code)) obj = "�P�P�D�h���[���E�u�h���[���i���ʁF�v�@���o��";
//        else if ("Grid9.h6.w20".equals(code)) obj = "�P�P�D�h���[���@����";
//        else if ("Grid9.h6.w22".equals(code)) obj = "�P�P�D�h���[���E�u�j�v�@���o��";
//        else if ("Grid9.h7.w3".equals(code)) obj = "�P�Q�D�l�H���E�u12�v�@���o��";
//        else if ("Grid9.h7.w4".equals(code)) obj = "�P�Q�D�l�H���E�u�l�H���v�@���o��";
//        else if ("Grid9.h7.w5".equals(code)) obj = "�P�R�D�l�H�N���E�u13�v�@���o��";
//        else if ("Grid9.h7.w23".equals(code)) obj = "�P�R�D�l�H�N���E�u�l�H�N���v�@���o��";
//        else if ("Grid9.h7.w10".equals(code)) obj = "�P�S�E���̑��E�u14�v�@���o��";
//        else if ("Grid9.h7.w11".equals(code)) obj = "�P�S�E���̑��E�u���̑��i�v�@���o��";
//        else if ("Grid9.h7.w14".equals(code)) obj = "�P�S�D���̑�";
//        else if ("Grid9.h7.w22".equals(code)) obj = "�P�S�E���̑��E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 delete end
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��z�K��w�����̃h���[���폜
        else if ("Grid9.h7.w3".equals(code)) obj = "�P�P�D�l�H���E�u11�v�@���o��";
        else if ("Grid9.h7.w4".equals(code)) obj = "�P�P�D�l�H���E�u�l�H���v�@���o��";
        else if ("Grid9.h7.w5".equals(code)) obj = "�P�Q�D�l�H�N���E�u12�v�@���o��";
        else if ("Grid9.h7.w23".equals(code)) obj = "�P�Q�D�l�H�N���E�u�l�H�N���v�@���o��";
        else if ("Grid9.h7.w10".equals(code)) obj = "�P�R�E���̑��E�u13�v�@���o��";
        else if ("Grid9.h7.w11".equals(code)) obj = "�P�R�E���̑��E�u���̑��i�v�@���o��";
        else if ("Grid9.h7.w14".equals(code)) obj = "�P�R�D���̑�";
        else if ("Grid9.h7.w22".equals(code)) obj = "�P�R�E���̑��E�u�j�v�@���o��";
    	//[ID:0000689][Shin Fujihara] 2012/03/12 add end
        else if ("Grid7.h1.w1".equals(code)) obj = "�����ł֑�����";
    	
    	
        return obj;
    }

    /**
     * �K��Ō�w�����i���V�l�ی��{�݁j2�y�[�W�ڂ̒�`��ID���t�H�[�}�b�g�����܂��B
     * @param code �R�[�h
     * @param obj �ϊ��O
     * @return �ϊ�����
     */
    protected Object formatShijishoB_M2(String code, Object obj){
    	if ("patientData.h1.w2".equals(code)) obj = "�����E�N��u�N��v���o��";
    	else if ("patientData.h1.w4".equals(code)) obj = "�����E�N��u�΁v���o��";
    	
        else if ("Grid10".equals(code)) obj = "���ӎ����y�юw�������@�g";
        else if ("Grid10.h1.w1".equals(code)) obj = "�u���ӎ����y�юw�������v�@���o��";
        else if ("Grid10.h13.w1".equals(code)) obj = "�u I �×{�����w����̗��ӎ����v�@���o��";
        else if ("Grid10.h3.w1".equals(code)) obj = "�uII�v �@���o��";
    	
        else if ("Label19".equals(code)) obj = "�u�P. ���n�r���e�[�V�����v�@���o��";
        else if ("Label20".equals(code)) obj = "�u�Q. ��ጂ̏��u���v�@���o��";
        else if ("Label21".equals(code)) obj = "�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��v�@���o��";
        else if ("Label22".equals(code)) obj = "�u�S. ���̑��v�@���o��";
    	
        else if ("lblRyoyo".equals(code)) obj = " I �×{�����w����̗��ӎ���";
        else if ("lblRiha".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�P. ���n�r���e�[�V�����v";
        else if ("lblJyokusyo".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�Q. ��ጂ̏��u���v";
        else if ("lblSochaku".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�R. �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ��v";
    	else if ("lblEtc".equals(code)) obj = "���ӎ����y�юw�������E�U�@�u�S�D���̑��v";
    	
        else if ("Label24".equals(code)) obj = "�u�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j�v�@���o��";
        else if ("lblZaitaku".equals(code)) obj = "�ݑ�ҖK��_�H���˂Ɋւ���w���i���^��܁E���^�ʁE���^���@���j";
    	
        else if ("Grid11.h1.w1".equals(code)) obj = "�u�ً}���̘A����v�@���o��";
        else if ("Grid11.h1.w2".equals(code)) obj = "�ً}���̘A����";
        else if ("Grid11.h2.w1".equals(code)) obj = "�u�s�ݎ��̑Ή��@�v�@���o��";
        else if ("Grid11.h2.w2".equals(code)) obj = "�s�ݎ��̑Ή��@";
    	
        else if ("Grid12.h1.w1".equals(code)) obj = "���L���ׂ����ӎ����E�u���L���ׂ����ӎ����v�@���o��";
        else if ("Grid12.h1.w6".equals(code)) obj = "���L���ׂ����ӎ����E�u�i���F��̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_�A�򕨃A�����M�[�̊���������΋L�ڂ��ĉ������B�j�v�@���o��";
        else if ("lblTokki".equals(code)) obj = "���L���ׂ����ӎ���";
        else if ("Grid12.h3.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���̖K��Ō�X�e�[�V�����ւ̎w���v�@���o��";
        else if ("Grid12.h4.w1".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h4.w2".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h4.w4".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h4.w5".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�F�w��K��Ō�X�e�[�V�������v�@���o��";
        else if ("Grid12.h4.w8".equals(code)) obj = "���̖K��Ō�X�e�[�V����";
        else if ("Grid12.h4.w7".equals(code)) obj = "���̖K��Ō�X�e�[�V�����ւ̎w���E�u�j�v�@���o��";
        
        //[ID:0000732][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        else if ("Grid12.h6.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���v�@���o��";
        else if ("Grid12.h8.w1".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�i�v�@���o�� ";
        else if ("Grid12.h8.w2".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u���v�@���o��";
        else if ("Grid12.h8.w4".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�L�v�@���o��";
        else if ("Grid12.h8.w5".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�F�K���쎖�Ə����v�@���o��";
        else if ("Grid12.h8.w8".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə�";
        else if ("Grid12.h8.w7".equals(code)) obj = "����̋z�������{�̂��߂̖K���쎖�Ə��ւ̎w���E�u�j�v�@���o��";
        //[ID:0000732][Shin Fujihara] 2012/04/20 add end �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
    	
        else if ("Label1".equals(code)) obj = "�u��L�̂Ƃ���A�w��K��Ō�̎��{���w���������܂��B�v�@���o��";
        else if ("Label2".equals(code)) obj = "�쐬�N�����@�u����  �N  ��  ���v";
    	
        else if ("Grid13.h1.w1".equals(code)) obj = "�u���V�l�ی��{�ݖ��v�@���o��";
        else if ("Grid13.h1.w2".equals(code)) obj = "���V�l�ی��{�ݖ�";
        else if ("Grid13.h2.w1".equals(code)) obj = "�u�Z�@�@�@���v�@���o��";
        else if ("Grid13.h2.w2".equals(code)) obj = "���V�l�ی��{�ݖ��Z��";
        else if ("Grid13.h3.w1".equals(code)) obj = "�u�d�@�@�@�b�v�@���o��";
        else if ("Grid13.h3.w2".equals(code)) obj = "���V�l�ی��{�ݖ��@�d�b�ԍ�";
        else if ("Grid13.h4.w1".equals(code)) obj = "�u�i�e�`�w�j�v�@���o��";
        else if ("Grid13.h4.w2".equals(code)) obj = "���V�l�ی��{�ݖ��@�e�`�w�ԍ�";
        else if ("Grid13.h5.w1".equals(code)) obj = "�u���V�l�ی��{�݈�t�����v�@���o��";
        else if ("Grid13.h5.w2".equals(code)) obj = "���V�l�ی��{�݈�t����";
    	
        else if ("Label3".equals(code)) obj = "�K��Ō�X�e�[�V�����@�u��v�@���o��";
        else if ("Label6".equals(code)) obj = "�K��Ō�X�e�[�V������";
    	
        return obj;
    }

    //[ID:0000639][Shin Fujihara] 2011/03 add end

}
