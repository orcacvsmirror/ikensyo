package jp.nichicom.ac.component;

import java.util.Date;

import javax.swing.JTextField;

import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.io.ACAgeEncorder;

/**
 * 年齢テキストフィールドです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 * @see ACParentHesesPanelContainer
 */
public class ACAgeTextField extends ACParentHesesPanelContainer {
    public ACAgeTextField() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private ACTextField age = new ACTextField();
    private Date birthday;
    private Date baseDate;
    private boolean enabled;

    private void jbInit() throws Exception {
        this.setBeginText("年齢");
        this.setEndText("歳");

        age.setColumns(3);
        age.setEditable(false);
        age.setFocusable(false);
        age.setText("");
        age.setHorizontalAlignment(JTextField.RIGHT);
        this.setOpaque(false);
        this.add(age);
    }

    /**
     * 生年月日を設定します。
     * @param birthday 生年月日
     */
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
        calcAge();
    }

    /**
     * 年齢計算のために生年月日と比較する基準日を設定します。
     * <p>
     * nullを設定した場合、年齢は現在日付と生年月日を比較して計算します。
     * </p>
     * @param baseDate 基準日
     */
    public void setBaseDate(Date baseDate) {
        this.baseDate = baseDate;
        calcAge();
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
        age.setEnabled(enabled);
    }

    /**
     * 年齢を設定します。
     * @param age 年齢
     */
    public void setAge(String age) {
        this.age.setText(age);
    }

    /**
     * 設定している生年月日を取得します。
     * @return 生年月日
     */
    public Date getBirthday() {
        return birthday;
    }

    /**
     * 設定している基準日を取得します。
     * <p>
     * 現在日付と比較するように設定されている場合、nullが入っています。
     * </p>
     * @return 基準日
     */
    public Date getBaseDate() {
        return baseDate;
    }

    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 現在日付と基準日(現在日付)から年齢を算出し、TextBoxに設定します。
     */
    private void calcAge() {
        if (getBirthday() == null) {
            age.setText("");
        }
        else {
            if (getBaseDate() == null) {
                age.setText(String.valueOf(ACAgeEncorder.getInstance().toAge(getBirthday())));
            }
            else {
                age.setText(String.valueOf(ACAgeEncorder.getInstance().toAge(getBirthday(), getBaseDate())));
            }
        }
    }

    /**
     * TextBoxに設定されている年齢を取得します。
     * @return TextBoxに設定されている年齢
     */
    public String getAge() {
        String age = this.age.getText();
        if (age == null) {
            age = "";
        }
        return age.trim();
    }

}
