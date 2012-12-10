package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/**
 * テーブルカラムに設定されることを想定した特定データ認識フォーマットです。
 * <p>
 * データが特定データの場合は選択状態を表す文字列を出力します。<br />
 * データが特定データでない場合は、非選択状態を表す文字列を出力します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACSingleSelectFormat extends Format {
    protected Object selectedData;
    protected String selectedText;
    protected String unselectedText;

    /**
     * コンストラクタです。
     * 
     * @param selectedText 選択扱いのデータに対応するフォーマット結果文字列
     * @param unselectedText 非選択扱いのデータに対応するフォーマット結果文字列
     */
    public ACSingleSelectFormat(String selectedText, String unselectedText) {
        setSelectedText(selectedText);
        setUnselectedText(unselectedText);
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo,
            FieldPosition pos) {
        if (obj == null) {
            if (getSelectedData() == null) {
                toAppendTo.append(getSelectedText());
            } else {
                toAppendTo.append(getUnselectedText());
            }
        } else {
            if (obj.equals(getSelectedData())) {
                toAppendTo.append(getSelectedText());
            } else {
                toAppendTo.append(getUnselectedText());
            }
        }
        return toAppendTo;
    }

    /**
     * 選択扱いとするデータを返します。
     * 
     * @return 選択扱いとするデータ
     */
    public Object getSelectedData() {
        return selectedData;
    }

    /**
     * 選択扱いのデータに対応するフォーマット結果文字列を返します。
     * 
     * @return 選択扱いのデータに対応するフォーマット結果文字列
     */
    public String getSelectedText() {
        return selectedText;
    }

    /**
     * 非選択扱いのデータに対応するフォーマット結果文字列を返します。
     * 
     * @return 非選択扱いのデータに対応するフォーマット結果文字列
     */
    public String getUnselectedText() {
        return unselectedText;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if (source == null) {
            if (getSelectedText() == null) {
                return getSelectedData();
            }
        } else {
            if (source.equals(getSelectedText())) {
                return getSelectedData();
            }
        }
        return null;
    }

    /**
     * 選択扱いとするデータを設定します。
     * 
     * @param selectedData 選択扱いとするデータ
     */
    public void setSelectedData(Object selectedData) {
        this.selectedData = selectedData;
    }

    /**
     * 選択扱いのデータに対応するフォーマット結果文字列を設定します。
     * 
     * @param selectedText 選択扱いのデータに対応するフォーマット結果文字列
     */
    public void setSelectedText(String selectedText) {
        this.selectedText = selectedText;
    }

    /**
     * 非選択扱いのデータに対応するフォーマット結果文字列を設定します。
     * 
     * @param unselectedText 非選択扱いのデータに対応するフォーマット結果文字列
     */
    public void setUnselectedText(String unselectedText) {
        this.unselectedText = unselectedText;
    }

}
