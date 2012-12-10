package jp.nichicom.ac;

import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACSQLSafeDateFormat;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroIntegerFormat;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.ac.text.ACTimeFormat;
import jp.nichicom.vr.text.VRCharType;

/**
 * NC��Ղ̋��ʒ萔��`�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACConstants {


    /**
     * ������\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_LEFT_24 = "jp/nichicom/ac/images/icon/pix24/btn_001.png";
    /**
     * �E����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_RIGHT_24 = "jp/nichicom/ac/images/icon/pix24/btn_002.png";
    /**
     * �����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_UP_24 = "jp/nichicom/ac/images/icon/pix24/btn_003.png";
    /**
     * ������\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_DOWN_24 = "jp/nichicom/ac/images/icon/pix24/btn_004.png";
    /**
     * �_�O���t��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_GRAPH_BAR_24 = "jp/nichicom/ac/images/icon/pix24/btn_005.png";
    /**
     * �ςݏグ�O���t��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_GRAPH_STACK_24 = "jp/nichicom/ac/images/icon/pix24/btn_006.png";

    /**
     * �V�K�쐬��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_NEW_24 = "jp/nichicom/ac/images/icon/pix24/btn_013.png";
    /**
     * �J�n��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_START_24 = "jp/nichicom/ac/images/icon/pix24/btn_022.png";

    /**
     * �L�����Z����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CANCEL_24 = "jp/nichicom/ac/images/icon/pix24/btn_026.png";
    /**
     * OK��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_OK_24 = "jp/nichicom/ac/images/icon/pix24/btn_027.png";
    /**
     * YES��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_YES_24 = ICON_PATH_OK_24;
    /**
     * NO��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_NO_24 = "jp/nichicom/ac/images/icon/pix24/btn_028.png";
    /**
     * �X�V��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_UPDATE_24 = "jp/nichicom/ac/images/icon/pix24/btn_029.png";
    /**
     * �J����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_OPEN_24 = "jp/nichicom/ac/images/icon/pix24/btn_031.png";
    /**
     * �O���o�͂�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_EXPORT_24 = "jp/nichicom/ac/images/icon/pix24/btn_032.png";
    /**
     * �؂����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CUT_24 = "jp/nichicom/ac/images/icon/pix24/btn_033.png";
    /**
     * ���ʂ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_COPY_24 = "jp/nichicom/ac/images/icon/pix24/btn_034.png";
    /**
     * �\��t����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_PASTE_24 = "jp/nichicom/ac/images/icon/pix24/btn_035.png";
    /**
     * ������\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_FIND_24 = "jp/nichicom/ac/images/icon/pix24/btn_036.png";
    /**
     * �]����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_WIDE_24 = "jp/nichicom/ac/images/icon/pix24/btn_037.png";
    /**
     * �g���\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_ZOOM_24 = "jp/nichicom/ac/images/icon/pix24/btn_038.png";
    /**
     * ��蒼����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_REDO_24 = "jp/nichicom/ac/images/icon/pix24/btn_039.png";
    /**
     * �߂��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_BACK_24 = "jp/nichicom/ac/images/icon/pix24/btn_040.png";
    /**
     * �摜��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_IMAGE_24 = "jp/nichicom/ac/images/icon/pix24/btn_041.png";
    /**
     * �_�C�A���O��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_DIALOG_24 = "jp/nichicom/ac/images/icon/pix24/btn_042.png";
    /**
     * �ڍׂ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_DETAIL_24 = "jp/nichicom/ac/images/icon/pix24/btn_043.png";
    /**
     * �����ꗗ��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_PAPERS_24 = "jp/nichicom/ac/images/icon/pix24/btn_044.png";
    /**
     * ��ǂ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_PARSE_24 = "jp/nichicom/ac/images/icon/pix24/btn_045.png";
    /**
     * �����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_PRINT_24 = "jp/nichicom/ac/images/icon/pix24/btn_046.png";
    /**
     * �J������\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CAMERA_24 = "jp/nichicom/ac/images/icon/pix24/btn_047.png";
    /**
     * �����N��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_LINK_24 = "jp/nichicom/ac/images/icon/pix24/btn_048.png";
    /**
     * �ԃ`�F�b�N��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_RED_24 = "jp/nichicom/ac/images/icon/pix24/btn_049.png";
    /**
     * �΃`�F�b�N��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_GREEN_24 = "jp/nichicom/ac/images/icon/pix24/btn_050.png";

    /**
     * �ǉ���Ԃ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_INSERT_24 = "jp/nichicom/ac/images/icon/pix24/btn_051.png";
    /**
     * �X�V��Ԃ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_UPDATE_24 = "jp/nichicom/ac/images/icon/pix24/btn_052.png";
    /**
     * �폜��Ԃ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_DELETE_24 = "jp/nichicom/ac/images/icon/pix24/btn_053.png";
    /**
     * �������݂�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_WRITE_24 = "jp/nichicom/ac/images/icon/pix24/btn_054.png";
    /**
     * ����������\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_REWRITE_24 = "jp/nichicom/ac/images/icon/pix24/btn_055.png";
    /**
     * �N���A��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CLEAR_24 = "jp/nichicom/ac/images/icon/pix24/btn_056.png";
    /**
     * �폜��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_DELETE_24 = "jp/nichicom/ac/images/icon/pix24/btn_057.png";
    /**
     * �I����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_EXIT_24 = "jp/nichicom/ac/images/icon/pix24/btn_058.png";
    /**
     * ���Ԃ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_TIME_24 = "jp/nichicom/ac/images/icon/pix24/btn_060.png";
    /**
     * �ꗗ�����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_TABLE_PRINT_24 = "jp/nichicom/ac/images/icon/pix24/btn_061.png";
    /**
     * �J�����_�[��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CALENDAR_24 = "jp/nichicom/ac/images/icon/pix24/btn_065.png";
    /**
     * �t�B���^��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_FILTER_24 = "jp/nichicom/ac/images/icon/pix24/btn_066.png";
    /**
     * ���R�[�h�̎扺��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_RECORD_DOWNLOAD_24 = "jp/nichicom/ac/images/icon/pix24/btn_078.png";
    /**
     * �v�Z��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CALC_24 = "jp/nichicom/ac/images/icon/pix24/btn_082.png";
    /**
     * �Q�Ƃ�\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_BROWSE_24 = "jp/nichicom/ac/images/icon/pix24/btn_083.png";
    /**
     * �`�F�b�N�Ȃ��`�F�b�N�{�b�N�X��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_BOX_BLANK_24 = "jp/nichicom/ac/images/icon/pix24/btn_085.png";
    /**
     * �ΐF�`�F�b�N�t���`�F�b�N�{�b�N�X��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_BOX_GREEN_24 = "jp/nichicom/ac/images/icon/pix24/btn_086.png";
    /**
     * �D�F�`�F�b�N�t���`�F�b�N�{�b�N�X��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_BOX_GRAY_24 = "jp/nichicom/ac/images/icon/pix24/btn_087.png";
    /**
     * ���ׂă`�F�b�N��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_ALL_CHECK_24 = "jp/nichicom/ac/images/icon/pix24/btn_088.png";
    /**
     * ����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_INFORMATION_24 = "jp/nichicom/ac/images/icon/pix24/btn_071.png";
    /**
     * �����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_QUESTION_24 = "jp/nichicom/ac/images/icon/pix24/btn_009.png";
    /**
     * �x����\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_EXCLAMATION_24 = "jp/nichicom/ac/images/icon/pix24/btn_008.png";
    /**
     * ��~��\���T�C�Y24�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STOP_24 = "jp/nichicom/ac/images/icon/pix24/btn_084.png";

    /**
     * ����\���T�C�Y48�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_INFORMATION_48 = "jp/nichicom/ac/images/icon/pix48/btn_071.png";
    /**
     * �����\���T�C�Y48�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_QUESTION_48 = "jp/nichicom/ac/images/icon/pix48/btn_009.png";
    /**
     * �x����\���T�C�Y48�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_EXCLAMATION_48 = "jp/nichicom/ac/images/icon/pix48/btn_008.png";
    /**
     * ��~��\���T�C�Y48�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STOP_48 = "jp/nichicom/ac/images/icon/pix48/btn_084.png";

    /**
     * �I����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_EXIT_16 = "jp/nichicom/ac/images/icon/pix16/btn_058.png";
    /**
     * �J�n��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_START_16 = "jp/nichicom/ac/images/icon/pix16/btn_022.png";
    /**
     * OK��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_OK_16 = "jp/nichicom/ac/images/icon/pix16/btn_027.png";
    /**
     * YES��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_YES_16 = ICON_PATH_OK_16;
    /**
     * NO��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_NO_16 = "jp/nichicom/ac/images/icon/pix16/btn_028.png";
    /**
     * �߂��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_BACK_16 = "jp/nichicom/ac/images/icon/pix16/btn_040.png";
    /**
     * ����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_INFORMATION_16 = "jp/nichicom/ac/images/icon/pix16/btn_071.png";
    /**
     * �����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_QUESTION_16 = "jp/nichicom/ac/images/icon/pix16/btn_009.png";
    /**
     * �x����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_EXCLAMATION_16 = "jp/nichicom/ac/images/icon/pix16/btn_008.png";
    /**
     * ��~��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STOP_16 = "jp/nichicom/ac/images/icon/pix16/btn_084.png";
    /**
     * �N���A��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CLEAR_16 = "jp/nichicom/ac/images/icon/pix16/btn_056.png";
    /**
     * ���R�[�h�̎扺��\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_RECORD_DOWNLOAD_16 = "jp/nichicom/ac/images/icon/pix16/btn_078.png";
    /**
     * ������\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_LEFT_16 = "jp/nichicom/ac/images/icon/pix16/btn_001.png";
    /**
     * �E����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_RIGHT_16 = "jp/nichicom/ac/images/icon/pix16/btn_002.png";
    /**
     * �L�����Z����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CANCEL_16 = "jp/nichicom/ac/images/icon/pix16/btn_026.png";
    /**
     * �؂����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CUT_16 = "jp/nichicom/ac/images/icon/pix16/btn_033.png";
    /**
     * ���ʂ�\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_COPY_16 = "jp/nichicom/ac/images/icon/pix16/btn_034.png";
    /**
     * �\��t����\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_PASTE_16 = "jp/nichicom/ac/images/icon/pix16/btn_035.png";
    /**
     * �ǉ���Ԃ�\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_INSERT_16 = "jp/nichicom/ac/images/icon/pix16/btn_051.png";
    /**
     * �X�V��Ԃ�\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_UPDATE_16 = "jp/nichicom/ac/images/icon/pix16/btn_052.png";
    /**
     * �폜��Ԃ�\���T�C�Y16�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_DELETE_16 = "jp/nichicom/ac/images/icon/pix16/btn_053.png";

    /**
     * �N���A��\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CLEAR_12 = "jp/nichicom/ac/images/icon/pix12/btn_056.png";
    /**
     * �ǉ���Ԃ�\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_INSERT_12 = "jp/nichicom/ac/images/icon/pix12/btn_051.png";
    /**
     * �X�V��Ԃ�\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_UPDATE_12 = "jp/nichicom/ac/images/icon/pix12/btn_052.png";
    /**
     * �폜��Ԃ�\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_STATE_DELETE_12 = "jp/nichicom/ac/images/icon/pix12/btn_053.png";
    /**
     * ������\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_LEFT_12 = "jp/nichicom/ac/images/icon/pix12/btn_001.png";
    /**
     * �E����\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_RIGHT_12 = "jp/nichicom/ac/images/icon/pix12/btn_002.png";
    /**
     * �J�����_�[��\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CALENDAR_12 = "jp/nichicom/ac/images/icon/pix12/btn_065.png";
    /**
     * �`�F�b�N�Ȃ��`�F�b�N�{�b�N�X��\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_BOX_BLANK_12 = "jp/nichicom/ac/images/icon/pix12/btn_085.png";
    /**
     * �ΐF�`�F�b�N�t���`�F�b�N�{�b�N�X��\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_BOX_GREEN_12 = "jp/nichicom/ac/images/icon/pix12/btn_086.png";
    /**
     * �D�F�`�F�b�N�t���`�F�b�N�{�b�N�X��\���T�C�Y12�̃A�C�R���萔�ł��B
     */
    public static final String ICON_PATH_CHECK_BOX_GRAY_12 = "jp/nichicom/ac/images/icon/pix12/btn_087.png";

    /**
     * ����ŔN���������b��\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACDateFormat FORMAT_FULL_YMD_HMS = new ACDateFormat(
            "yyyy/MM/d HH:mm:ss");
    /**
     * ����ŔN������\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACDateFormat FORMAT_FULL_YMD = new ACDateFormat(
            "yyyy/MM/dd");
    /**
     * ����ŔN����\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACDateFormat FORMAT_FULL_YM = new ACDateFormat(
            "yyyy/MM");
    /**
     * �a��ŔN���������b��\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACDateFormat FORMAT_FULL_ERA_YMD_HMS = new ACDateFormat(
            "gggee�NMM��dd�� HH:mm:ss");
    /**
     * �a��ŔN������\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACDateFormat FORMAT_FULL_ERA_YMD = new ACDateFormat(
            "gggee�NMM��dd��");
    /**
     * �a��ŔN����\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACDateFormat FORMAT_FULL_ERA_YM = new ACDateFormat(
            "gggee�NMM��");
    /**
     * ������\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACTimeFormat FORMAT_FULL_HOUR_MINUTE = new ACTimeFormat();

    /**
     * SQL�`���ɕϊ����鐼��ŔN���������b��\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACSQLSafeDateFormat FORMAT_SQL_FULL_YMD_HMS = new ACSQLSafeDateFormat(
            "yyyy-M-d HH:mm:ss");
    /**
     * SQL�`���ɕϊ����鐼��ŔN������\��0�l�߃t�H�[�}�b�g�萔�ł��B
     */
    public static final ACSQLSafeDateFormat FORMAT_SQL_FULL_YMD = new ACSQLSafeDateFormat(
            "yyyy-M-d");
    /**
     * SQL�`���ɕϊ����镶�����\���t�H�[�}�b�g�萔�ł��B
     */
    public static final ACSQLSafeStringFormat FORMAT_SQL_STRING = ACSQLSafeStringFormat
            .getInstance();
    /**
     * SQL�`���ɕϊ����镶�����\���t�H�[�}�b�g�萔�ł��B
     */
    public static final ACSQLSafeNullToZeroIntegerFormat FORMAT_SQL_NULL_TO_ZERO_INTEGER = ACSQLSafeNullToZeroIntegerFormat
            .getInstance();

    /**
     * �����_���ʂ܂ł̐��l���͂������镶����ʂł��B
     */
    public static final VRCharType CHAR_TYPE_ONE_DECIMAL_DOUBLE = new VRCharType(
            "CHAR_TYPE_ONE_DECIMAL_DOUBLE", "^(\\d+)|((\\d+)(\\.\\d{0,1}))$");

    /**
     * 1�`4���̎������͂������镶����ʂł��B
     */
    public static final VRCharType CHAR_TYPE_TIME_HOUR_MINUTE = new VRCharType(
            "CHAR_TYPE_TIME_HOUR_MINUTE",
            "^((([ 0-1][0-9]?)|(2[0-3]?))[:��]?([0-5][0-9]?)?)|(([0-9])|([0-5][0-9])|([1-9][0-5][:��]?)|([1-9][:��]?[0-5][0-9])|([:��]?[0-5][0-9]��)|([:��][0-5][0-9][��]?))$");

    /**
     * IP�A�h�h���X����уz�X�g���������镶����ʂł��B
     */
    public static final VRCharType CHAR_TYPE_IP_OR_HOSTNAME = new VRCharType(
            "CHAR_TYPE_IP_OR_HOSTNAME", "^[0-9A-Za-z\\.]*$");

    /**
     * �t�@�C���̋�؂蕶����\���V�X�e���v���p�e�B�ł��B
     */
    public static final String FILE_SEPARATOR = System
            .getProperty("file.separator");
    /**
     * ���s������\���V�X�e���v���p�e�B�ł��B
     */
    public static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

}