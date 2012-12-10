package jp.nichicom.ac.component;

import java.util.Date;
import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;

import jp.nichicom.ac.text.ACTimeFormat;
import jp.nichicom.vr.component.AbstractVRTextField;

/**
 * 時刻用に設定したコンボボックスです。
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
 * @see ACComboBox
 * @see ACTextField
 * @see ACTimeFormat
 */
public class ACTimeComboBox extends ACComboBox {

    /**
     * Creates a <code>JComboBox</code> with a default data model. The default
     * data model is an empty list of objects. Use <code>addItem</code> to add
     * items. By default the first item in the data model becomes selected.
     * 
     * @see DefaultComboBoxModel
     */
    public ACTimeComboBox() {
        super();
    }

    /**
     * Creates a <code>JComboBox</code> that takes it's items from an existing
     * <code>ComboBoxModel</code>. Since the <code>ComboBoxModel</code> is
     * provided, a combo box created using this constructor does not create a
     * default combo box model and may impact how the insert, remove and add
     * methods behave.
     * 
     * @param aModel the <code>ComboBoxModel</code> that provides the
     *            displayed list of items
     * @see DefaultComboBoxModel
     */
    public ACTimeComboBox(ComboBoxModel aModel) {
        super(aModel);
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements in the
     * specified array. By default the first item in the array (and therefore
     * the data model) becomes selected.
     * 
     * @param items an array of objects to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public ACTimeComboBox(Object[] items) {
        super(items);
    }

    /**
     * Creates a <code>JComboBox</code> that contains the elements in the
     * specified Vector. By default the first item in the vector and therefore
     * the data model) becomes selected.
     * 
     * @param items an array of vectors to insert into the combo box
     * @see DefaultComboBoxModel
     */
    public ACTimeComboBox(Vector items) {
        super(items);
    }

    /**
     * 解析結果をDate型とする場合に基準となる日付 を返します。
     * 
     * @return 解析結果をDate型とする場合に基準となる日付
     */
    public Date getBaseDate() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getBaseDate();
        }
        return null;
    }

    /**
     * 入力値をDate型で取得します。
     * 
     * @return 入力値
     * @throws Exception 処理例外
     */
    public Date getDate() throws Exception {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getDate();
        }
        return null;
    }

    /**
     * 出力書式 を返します。
     * 
     * @return 出力書式
     */
    public String getFormatedFormat() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getFormatedFormat();
        }
        return null;
    }

    /**
     * エラー時に検索する親階層の数を返します。
     * 
     * @return エラー時に検索する親階層
     */
    public int getParentFindCount() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getParentFindCount();
        }
        return 0;
    }

    /**
     * 解析結果を文字列とする場合の出力書式 を返します。
     * 
     * @return 解析結果を文字列とする場合の出力書式
     */
    public String getPargedFormat() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getPargedFormat();
        }
        return null;
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
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).getParsedValueType();
        }
        return 0;
    }

    /**
     * 未入力を許可するか を返します。
     * 
     * @return 未入力を許可するか
     */
    public boolean isAllowedBlank() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).isAllowedBlank();
        }
        return false;
    }

    /**
     * 有効な日付が入力されているかを返します。
     * 
     * @return 有効な日付が入力されているか
     */
    public boolean isValidDate() {
        if (getEditorField() instanceof ACTimeTextField) {
            return ((ACTimeTextField) getEditorField()).isValidDate();
        }
        return false;
    }

    /**
     * 未入力を許可するか を設定します。
     * 
     * @param allowedBlank 未入力を許可するか
     */
    public void setAllowedBlank(boolean allowedBlank) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setAllowedBlank(allowedBlank);
        }
    }

    /**
     * 解析結果をDate型とする場合に基準となる日付 を設定します。
     * 
     * @param baseDate 解析結果をDate型とする場合に基準となる日付
     */
    public void setBaseDate(Date baseDate) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setBaseDate(baseDate);
        }
    }

    /**
     * 入力値をDate型で設定します。
     * 
     * @param value 入力値
     * @throws Exception 処理例外
     */
    public void setDate(Date value) throws Exception {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setDate(value);
        }
    }

    /**
     * 出力書式 を設定します。
     * 
     * @param formatedFormat 出力書式
     */
    public void setFormatedFormat(String formatedFormat) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField())
                    .setFormatedFormat(formatedFormat);
        }
    }

    /**
     * エラー時に検索する親階層の数を設定します。
     * 
     * @param parentFindCount エラー時に検索する親階層
     */
    public void setParentFindCount(int parentFindCount) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField())
                    .setParentFindCount(parentFindCount);
        }
    }

    /**
     * 解析結果を文字列とする場合の出力書式 を設定します。
     * 
     * @param pargedFormat 解析結果を文字列とする場合の出力書式
     */
    public void setPargedFormat(String pargedFormat) {
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField()).setPargedFormat(pargedFormat);
        }
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
        if (getEditorField() instanceof ACTimeTextField) {
            ((ACTimeTextField) getEditorField())
                    .setParsedValueType(parsedValueType);
        }
    }

    protected AbstractVRTextField createEditorField() {
        return new ACTimeTextField();
    }
}
