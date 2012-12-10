package jp.nichicom.ac;

import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACSQLSafeDateFormat;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroIntegerFormat;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.ac.text.ACTimeFormat;
import jp.nichicom.vr.text.VRCharType;

/**
 * NC基盤の共通定数定義です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACConstants {


    /**
     * 左矢印を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_LEFT_24 = "jp/nichicom/ac/images/icon/pix24/btn_001.png";
    /**
     * 右矢印を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_RIGHT_24 = "jp/nichicom/ac/images/icon/pix24/btn_002.png";
    /**
     * 上矢印を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_UP_24 = "jp/nichicom/ac/images/icon/pix24/btn_003.png";
    /**
     * 下矢印を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_DOWN_24 = "jp/nichicom/ac/images/icon/pix24/btn_004.png";
    /**
     * 棒グラフを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_GRAPH_BAR_24 = "jp/nichicom/ac/images/icon/pix24/btn_005.png";
    /**
     * 積み上げグラフを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_GRAPH_STACK_24 = "jp/nichicom/ac/images/icon/pix24/btn_006.png";

    /**
     * 新規作成を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_NEW_24 = "jp/nichicom/ac/images/icon/pix24/btn_013.png";
    /**
     * 開始を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_START_24 = "jp/nichicom/ac/images/icon/pix24/btn_022.png";

    /**
     * キャンセルを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CANCEL_24 = "jp/nichicom/ac/images/icon/pix24/btn_026.png";
    /**
     * OKを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_OK_24 = "jp/nichicom/ac/images/icon/pix24/btn_027.png";
    /**
     * YESを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_YES_24 = ICON_PATH_OK_24;
    /**
     * NOを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_NO_24 = "jp/nichicom/ac/images/icon/pix24/btn_028.png";
    /**
     * 更新を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_UPDATE_24 = "jp/nichicom/ac/images/icon/pix24/btn_029.png";
    /**
     * 開くを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_OPEN_24 = "jp/nichicom/ac/images/icon/pix24/btn_031.png";
    /**
     * 外部出力を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_EXPORT_24 = "jp/nichicom/ac/images/icon/pix24/btn_032.png";
    /**
     * 切り取りを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CUT_24 = "jp/nichicom/ac/images/icon/pix24/btn_033.png";
    /**
     * 複写を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_COPY_24 = "jp/nichicom/ac/images/icon/pix24/btn_034.png";
    /**
     * 貼り付けを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_PASTE_24 = "jp/nichicom/ac/images/icon/pix24/btn_035.png";
    /**
     * 検索を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_FIND_24 = "jp/nichicom/ac/images/icon/pix24/btn_036.png";
    /**
     * 望遠を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_WIDE_24 = "jp/nichicom/ac/images/icon/pix24/btn_037.png";
    /**
     * 拡大を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_ZOOM_24 = "jp/nichicom/ac/images/icon/pix24/btn_038.png";
    /**
     * やり直しを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_REDO_24 = "jp/nichicom/ac/images/icon/pix24/btn_039.png";
    /**
     * 戻るを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_BACK_24 = "jp/nichicom/ac/images/icon/pix24/btn_040.png";
    /**
     * 画像を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_IMAGE_24 = "jp/nichicom/ac/images/icon/pix24/btn_041.png";
    /**
     * ダイアログを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_DIALOG_24 = "jp/nichicom/ac/images/icon/pix24/btn_042.png";
    /**
     * 詳細を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_DETAIL_24 = "jp/nichicom/ac/images/icon/pix24/btn_043.png";
    /**
     * 白紙一覧を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_PAPERS_24 = "jp/nichicom/ac/images/icon/pix24/btn_044.png";
    /**
     * 解読を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_PARSE_24 = "jp/nichicom/ac/images/icon/pix24/btn_045.png";
    /**
     * 印刷を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_PRINT_24 = "jp/nichicom/ac/images/icon/pix24/btn_046.png";
    /**
     * カメラを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CAMERA_24 = "jp/nichicom/ac/images/icon/pix24/btn_047.png";
    /**
     * リンクを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_LINK_24 = "jp/nichicom/ac/images/icon/pix24/btn_048.png";
    /**
     * 赤チェックを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_RED_24 = "jp/nichicom/ac/images/icon/pix24/btn_049.png";
    /**
     * 緑チェックを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_GREEN_24 = "jp/nichicom/ac/images/icon/pix24/btn_050.png";

    /**
     * 追加状態を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_INSERT_24 = "jp/nichicom/ac/images/icon/pix24/btn_051.png";
    /**
     * 更新状態を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_UPDATE_24 = "jp/nichicom/ac/images/icon/pix24/btn_052.png";
    /**
     * 削除状態を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_DELETE_24 = "jp/nichicom/ac/images/icon/pix24/btn_053.png";
    /**
     * 書き込みを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_WRITE_24 = "jp/nichicom/ac/images/icon/pix24/btn_054.png";
    /**
     * 書き直しを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_REWRITE_24 = "jp/nichicom/ac/images/icon/pix24/btn_055.png";
    /**
     * クリアを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CLEAR_24 = "jp/nichicom/ac/images/icon/pix24/btn_056.png";
    /**
     * 削除を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_DELETE_24 = "jp/nichicom/ac/images/icon/pix24/btn_057.png";
    /**
     * 終了を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_EXIT_24 = "jp/nichicom/ac/images/icon/pix24/btn_058.png";
    /**
     * 時間を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_TIME_24 = "jp/nichicom/ac/images/icon/pix24/btn_060.png";
    /**
     * 一覧印刷を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_TABLE_PRINT_24 = "jp/nichicom/ac/images/icon/pix24/btn_061.png";
    /**
     * カレンダーを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CALENDAR_24 = "jp/nichicom/ac/images/icon/pix24/btn_065.png";
    /**
     * フィルタを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_FILTER_24 = "jp/nichicom/ac/images/icon/pix24/btn_066.png";
    /**
     * レコードの取下を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_RECORD_DOWNLOAD_24 = "jp/nichicom/ac/images/icon/pix24/btn_078.png";
    /**
     * 計算を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CALC_24 = "jp/nichicom/ac/images/icon/pix24/btn_082.png";
    /**
     * 参照を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_BROWSE_24 = "jp/nichicom/ac/images/icon/pix24/btn_083.png";
    /**
     * チェックなしチェックボックスを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_BOX_BLANK_24 = "jp/nichicom/ac/images/icon/pix24/btn_085.png";
    /**
     * 緑色チェック付きチェックボックスを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_BOX_GREEN_24 = "jp/nichicom/ac/images/icon/pix24/btn_086.png";
    /**
     * 灰色チェック付きチェックボックスを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_BOX_GRAY_24 = "jp/nichicom/ac/images/icon/pix24/btn_087.png";
    /**
     * すべてチェックを表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_ALL_CHECK_24 = "jp/nichicom/ac/images/icon/pix24/btn_088.png";
    /**
     * 情報を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_INFORMATION_24 = "jp/nichicom/ac/images/icon/pix24/btn_071.png";
    /**
     * 質問を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_QUESTION_24 = "jp/nichicom/ac/images/icon/pix24/btn_009.png";
    /**
     * 警告を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_EXCLAMATION_24 = "jp/nichicom/ac/images/icon/pix24/btn_008.png";
    /**
     * 停止を表すサイズ24のアイコン定数です。
     */
    public static final String ICON_PATH_STOP_24 = "jp/nichicom/ac/images/icon/pix24/btn_084.png";

    /**
     * 情報を表すサイズ48のアイコン定数です。
     */
    public static final String ICON_PATH_INFORMATION_48 = "jp/nichicom/ac/images/icon/pix48/btn_071.png";
    /**
     * 質問を表すサイズ48のアイコン定数です。
     */
    public static final String ICON_PATH_QUESTION_48 = "jp/nichicom/ac/images/icon/pix48/btn_009.png";
    /**
     * 警告を表すサイズ48のアイコン定数です。
     */
    public static final String ICON_PATH_EXCLAMATION_48 = "jp/nichicom/ac/images/icon/pix48/btn_008.png";
    /**
     * 停止を表すサイズ48のアイコン定数です。
     */
    public static final String ICON_PATH_STOP_48 = "jp/nichicom/ac/images/icon/pix48/btn_084.png";

    /**
     * 終了を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_EXIT_16 = "jp/nichicom/ac/images/icon/pix16/btn_058.png";
    /**
     * 開始を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_START_16 = "jp/nichicom/ac/images/icon/pix16/btn_022.png";
    /**
     * OKを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_OK_16 = "jp/nichicom/ac/images/icon/pix16/btn_027.png";
    /**
     * YESを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_YES_16 = ICON_PATH_OK_16;
    /**
     * NOを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_NO_16 = "jp/nichicom/ac/images/icon/pix16/btn_028.png";
    /**
     * 戻るを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_BACK_16 = "jp/nichicom/ac/images/icon/pix16/btn_040.png";
    /**
     * 情報を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_INFORMATION_16 = "jp/nichicom/ac/images/icon/pix16/btn_071.png";
    /**
     * 質問を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_QUESTION_16 = "jp/nichicom/ac/images/icon/pix16/btn_009.png";
    /**
     * 警告を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_EXCLAMATION_16 = "jp/nichicom/ac/images/icon/pix16/btn_008.png";
    /**
     * 停止を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_STOP_16 = "jp/nichicom/ac/images/icon/pix16/btn_084.png";
    /**
     * クリアを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_CLEAR_16 = "jp/nichicom/ac/images/icon/pix16/btn_056.png";
    /**
     * レコードの取下を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_RECORD_DOWNLOAD_16 = "jp/nichicom/ac/images/icon/pix16/btn_078.png";
    /**
     * 左矢印を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_LEFT_16 = "jp/nichicom/ac/images/icon/pix16/btn_001.png";
    /**
     * 右矢印を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_RIGHT_16 = "jp/nichicom/ac/images/icon/pix16/btn_002.png";
    /**
     * キャンセルを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_CANCEL_16 = "jp/nichicom/ac/images/icon/pix16/btn_026.png";
    /**
     * 切り取りを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_CUT_16 = "jp/nichicom/ac/images/icon/pix16/btn_033.png";
    /**
     * 複写を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_COPY_16 = "jp/nichicom/ac/images/icon/pix16/btn_034.png";
    /**
     * 貼り付けを表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_PASTE_16 = "jp/nichicom/ac/images/icon/pix16/btn_035.png";
    /**
     * 追加状態を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_INSERT_16 = "jp/nichicom/ac/images/icon/pix16/btn_051.png";
    /**
     * 更新状態を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_UPDATE_16 = "jp/nichicom/ac/images/icon/pix16/btn_052.png";
    /**
     * 削除状態を表すサイズ16のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_DELETE_16 = "jp/nichicom/ac/images/icon/pix16/btn_053.png";

    /**
     * クリアを表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_CLEAR_12 = "jp/nichicom/ac/images/icon/pix12/btn_056.png";
    /**
     * 追加状態を表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_INSERT_12 = "jp/nichicom/ac/images/icon/pix12/btn_051.png";
    /**
     * 更新状態を表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_UPDATE_12 = "jp/nichicom/ac/images/icon/pix12/btn_052.png";
    /**
     * 削除状態を表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_STATE_DELETE_12 = "jp/nichicom/ac/images/icon/pix12/btn_053.png";
    /**
     * 左矢印を表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_LEFT_12 = "jp/nichicom/ac/images/icon/pix12/btn_001.png";
    /**
     * 右矢印を表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_RIGHT_12 = "jp/nichicom/ac/images/icon/pix12/btn_002.png";
    /**
     * カレンダーを表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_CALENDAR_12 = "jp/nichicom/ac/images/icon/pix12/btn_065.png";
    /**
     * チェックなしチェックボックスを表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_BOX_BLANK_12 = "jp/nichicom/ac/images/icon/pix12/btn_085.png";
    /**
     * 緑色チェック付きチェックボックスを表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_BOX_GREEN_12 = "jp/nichicom/ac/images/icon/pix12/btn_086.png";
    /**
     * 灰色チェック付きチェックボックスを表すサイズ12のアイコン定数です。
     */
    public static final String ICON_PATH_CHECK_BOX_GRAY_12 = "jp/nichicom/ac/images/icon/pix12/btn_087.png";

    /**
     * 西暦で年月日時分秒を表す0詰めフォーマット定数です。
     */
    public static final ACDateFormat FORMAT_FULL_YMD_HMS = new ACDateFormat(
            "yyyy/MM/d HH:mm:ss");
    /**
     * 西暦で年月日を表す0詰めフォーマット定数です。
     */
    public static final ACDateFormat FORMAT_FULL_YMD = new ACDateFormat(
            "yyyy/MM/dd");
    /**
     * 西暦で年月を表す0詰めフォーマット定数です。
     */
    public static final ACDateFormat FORMAT_FULL_YM = new ACDateFormat(
            "yyyy/MM");
    /**
     * 和暦で年月日時分秒を表す0詰めフォーマット定数です。
     */
    public static final ACDateFormat FORMAT_FULL_ERA_YMD_HMS = new ACDateFormat(
            "gggee年MM月dd日 HH:mm:ss");
    /**
     * 和暦で年月日を表す0詰めフォーマット定数です。
     */
    public static final ACDateFormat FORMAT_FULL_ERA_YMD = new ACDateFormat(
            "gggee年MM月dd日");
    /**
     * 和暦で年月を表す0詰めフォーマット定数です。
     */
    public static final ACDateFormat FORMAT_FULL_ERA_YM = new ACDateFormat(
            "gggee年MM月");
    /**
     * 時分を表す0詰めフォーマット定数です。
     */
    public static final ACTimeFormat FORMAT_FULL_HOUR_MINUTE = new ACTimeFormat();

    /**
     * SQL形式に変換する西暦で年月日時分秒を表す0詰めフォーマット定数です。
     */
    public static final ACSQLSafeDateFormat FORMAT_SQL_FULL_YMD_HMS = new ACSQLSafeDateFormat(
            "yyyy-M-d HH:mm:ss");
    /**
     * SQL形式に変換する西暦で年月日を表す0詰めフォーマット定数です。
     */
    public static final ACSQLSafeDateFormat FORMAT_SQL_FULL_YMD = new ACSQLSafeDateFormat(
            "yyyy-M-d");
    /**
     * SQL形式に変換する文字列を表すフォーマット定数です。
     */
    public static final ACSQLSafeStringFormat FORMAT_SQL_STRING = ACSQLSafeStringFormat
            .getInstance();
    /**
     * SQL形式に変換する文字列を表すフォーマット定数です。
     */
    public static final ACSQLSafeNullToZeroIntegerFormat FORMAT_SQL_NULL_TO_ZERO_INTEGER = ACSQLSafeNullToZeroIntegerFormat
            .getInstance();

    /**
     * 小数点第一位までの数値入力を許可する文字種別です。
     */
    public static final VRCharType CHAR_TYPE_ONE_DECIMAL_DOUBLE = new VRCharType(
            "CHAR_TYPE_ONE_DECIMAL_DOUBLE", "^(\\d+)|((\\d+)(\\.\\d{0,1}))$");

    /**
     * 1～4桁の時刻入力を許可する文字種別です。
     */
    public static final VRCharType CHAR_TYPE_TIME_HOUR_MINUTE = new VRCharType(
            "CHAR_TYPE_TIME_HOUR_MINUTE",
            "^((([ 0-1][0-9]?)|(2[0-3]?))[:時]?([0-5][0-9]?)?)|(([0-9])|([0-5][0-9])|([1-9][0-5][:時]?)|([1-9][:時]?[0-5][0-9])|([:時]?[0-5][0-9]分)|([:時][0-5][0-9][分]?))$");

    /**
     * IPアドドレスおよびホスト名を許可する文字種別です。
     */
    public static final VRCharType CHAR_TYPE_IP_OR_HOSTNAME = new VRCharType(
            "CHAR_TYPE_IP_OR_HOSTNAME", "^[0-9A-Za-z\\.]*$");

    /**
     * ファイルの区切り文字を表すシステムプロパティです。
     */
    public static final String FILE_SEPARATOR = System
            .getProperty("file.separator");
    /**
     * 改行文字を表すシステムプロパティです。
     */
    public static final String LINE_SEPARATOR = System
            .getProperty("line.separator");

}