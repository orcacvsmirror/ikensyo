package jp.nichicom.ac.component;

import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.event.ACFollowContainerFormatEventListener;
import jp.nichicom.ac.text.ACTimeFormat;
import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * 時刻用に設定したテキストフィールドです。
 * <p>
 * 時刻として不適切な値を入力した場合、親コンテナを走査して着色します。
 * </p>
 * <p>
 * 時・分の入力を対象としており、年月日や秒の値は保証しません。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see ACTextField
 * @see ACTimeFormat
 */
public class ACTimeTextField extends ACTextField {
    private ACTimeFormat timeFormat = new ACTimeFormat();
    private ACFollowContainerFormatEventListener followFormatListener = new ACFollowContainerFormatEventListener();

    /**
     * コンストラクタです。
     */
    public ACTimeTextField() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.addFormatEventListener(followFormatListener);
    }

    /**
     * コンポーネントを初期化します。
     * 
     * @throws Exception 処理例外
     */
    private void jbInit() throws Exception {
        this.setColumns(3);
        this.setMaxLength(6);
        this.setFormat(timeFormat);
        this.setCharType(ACConstants.CHAR_TYPE_TIME_HOUR_MINUTE);
    }

    /**
     * エラー時に検索する親階層の数を返します。
     * 
     * @return エラー時に検索する親階層
     */
    public int getParentFindCount() {
        return followFormatListener.getParentFindCount();
    }

    /**
     * エラー時に検索する親階層の数を設定します。
     * 
     * @param parentFindCount エラー時に検索する親階層
     */
    public void setParentFindCount(int parentFindCount) {
        followFormatListener.setParentFindCount(parentFindCount);
    }

    /**
     * 解析結果をDate型とする場合に基準となる日付 を返します。
     * 
     * @return 解析結果をDate型とする場合に基準となる日付
     */
    public Date getBaseDate() {
        return timeFormat.getBaseDate();
    }

    /**
     * 出力書式 を返します。
     * 
     * @return 出力書式
     */
    public String getFormatedFormat() {
        return timeFormat.getFormatedFormat();
    }

    /**
     * 解析結果を文字列とする場合の出力書式 を返します。
     * 
     * @return 解析結果を文字列とする場合の出力書式
     */
    public String getPargedFormat() {
        return timeFormat.getPargedFormat();
    }

    /**
     * 解析結果の型 を返します。
     * <p>
     * VALUE_TYPE_STRING : 文字列<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @return 解析結果の型
     */
    public int getParsedValueType() {
        return timeFormat.getParsedValueType();
    }

    /**
     * 解析結果をDate型とする場合に基準となる日付 を設定します。
     * 
     * @param baseDate 解析結果をDate型とする場合に基準となる日付
     */
    public void setBaseDate(Date baseDate) {
        timeFormat.setBaseDate(baseDate);
    }

    /**
     * 出力書式 を設定します。
     * 
     * @param formatedFormat 出力書式
     */
    public void setFormatedFormat(String formatedFormat) {
        timeFormat.setFormatedFormat(formatedFormat);
    }

    /**
     * 解析結果を文字列とする場合の出力書式 を設定します。
     * 
     * @param pargedFormat 解析結果を文字列とする場合の出力書式
     */
    public void setPargedFormat(String pargedFormat) {
        timeFormat.setPargedFormat(pargedFormat);
    }

    /**
     * 解析結果の型 を設定します。
     * <p>
     * VALUE_TYPE_STRING : 文字列<br />
     * VALUE_TYPE_DATE : Date
     * </p>
     * 
     * @param parsedValueType 解析結果の型
     */
    public void setParsedValueType(int parsedValueType) {
        timeFormat.setParsedValueType(parsedValueType);
    }

    /**
     * 有効な日付が入力されているかを返します。
     * 
     * @return 有効な日付が入力されているか
     */
    public boolean isValidDate() {
        return followFormatListener.isValid();
    }

    /**
     * 入力値をDate型で取得します。
     * 
     * @return 入力値
     * @throws Exception 処理例外
     */
    public Date getDate() throws Exception {
        return (Date)timeFormat.parseObject(getText());
//        return VRDateParser.parse(getText());
    }

    /**
     * 入力値をDate型で設定します。
     * 
     * @param value 入力値
     * @throws Exception 処理例外
     */
    public void setDate(Date value) throws Exception {
        setText(VRDateParser.format(value, "HH:mm"));
    }

    /**
     * 未入力を許可するか を返します。
     * 
     * @return 未入力を許可するか
     */
    public boolean isAllowedBlank() {
        return followFormatListener.isAllowedBlank();
    }

    /**
     * 未入力を許可するか を設定します。
     * 
     * @param allowedBlank 未入力を許可するか
     */
    public void setAllowedBlank(boolean allowedBlank) {
        followFormatListener.setAllowedBlank(allowedBlank);
    }

}
