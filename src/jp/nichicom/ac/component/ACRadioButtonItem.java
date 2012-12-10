package jp.nichicom.ac.component;

import javax.swing.JRadioButton;

import jp.nichicom.vr.component.VRRadioButtonGroup;

/**
 * 対応するボタンを制御可能なラジオモデル用の項目データです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRRadioButtonGroup
 */
public class ACRadioButtonItem {
    private int buttonIndex;
    private Object constraints;
    private VRRadioButtonGroup group;
    private Object text;

    /**
     * コンストラクタです。
     */
    public ACRadioButtonItem() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param value 表示する値
     */
    public ACRadioButtonItem(Object value) {
        super();
        setText(value);
    }

    /**
     * コンストラクタです。
     * 
     * @param value 表示する値
     * @param group 親グループ
     * @param buttonIndex 対応する親グループ内のボタン番号
     */
    public ACRadioButtonItem(Object value, VRRadioButtonGroup group,
            int buttonIndex) {
        super();
        setText(value);
        setGroup(group);
        setButtonIndex(buttonIndex);
    }

    /**
     * コンストラクタです。
     * 
     * @param group 親グループ
     * @param buttonIndex 対応する親グループ内のボタン番号
     */
    public ACRadioButtonItem(VRRadioButtonGroup group, int buttonIndex) {
        super();
        setGroup(group);
        setButtonIndex(buttonIndex);
    }

    /**
     * 対応する親グループ内のボタン を返します。
     * 
     * @return button 対応する親グループ内のボタン
     */
    public JRadioButton getButton() {
        if (getGroup() != null) {
            return getGroup().getButton(getButtonIndex());
        }
        return null;
    }

    /**
     * 対応する親グループ内のボタン番号 を返します。
     * 
     * @return 対応する親グループ内のボタン番号
     */
    public int getButtonIndex() {
        return buttonIndex;
    }

    /**
     * 親ラジオグループへの配置方法 を返します。
     * 
     * @return 親ラジオグループへの配置方法
     */
    public Object getConstraints() {
        return constraints;
    }

    /**
     * 対応する親グループ を返します。
     * 
     * @return 対応する親グループ
     */
    public VRRadioButtonGroup getGroup() {
        return group;
    }

    /**
     * 表示する値 を返します。
     * 
     * @return 表示する値
     */
    public Object getText() {
        return text;
    }

    /**
     * 対応する親グループ内のボタンの有効状態を返します。
     * 
     * @return 対応する親グループ内のボタンの有効状態
     */
    public boolean isEnabled() {
        if (getButton() != null) {
            return getButton().isEnabled();
        }
        return true;
    }

    /**
     * 対応する親グループ内のボタンの選択状態を返します。
     * 
     * @return 対応する親グループ内のボタンの選択状態
     */
    public boolean isSelected() {
        if (getButton() != null) {
            return getButton().isSelected();
        }
        return false;
    }

    /**
     * 対応する親グループ内のボタンの表示可否を返します。
     * 
     * @return 対応する親グループ内のボタンの表示可否
     */
    public boolean isVisible() {
        if (getButton() != null) {
            return getButton().isVisible();
        }
        return true;
    }

    /**
     * 対応する親グループ内のボタン番号 を設定します。
     * 
     * @param buttonIndex 対応する親グループ内のボタン番号
     */
    public void setButtonIndex(int buttonIndex) {
        this.buttonIndex = buttonIndex;
    }

    /**
     * 親ラジオグループへの配置方法 を設定します。
     * 
     * @param constraints 親ラジオグループへの配置方法
     */
    public void setConstraints(Object constraints) {
        this.constraints = constraints;
    }

    /**
     * 対応する親グループ内のボタンの有効状態を設定します。
     * 
     * @param enabled 対応する親グループ内のボタンの有効状態
     */
    public void setEnabled(boolean enabled) {
        if (getButton() != null) {
            getButton().setEnabled(enabled);
        }
    }

    /**
     * 対応する親グループ を設定します。
     * 
     * @param group 対応する親グループ
     */
    public void setGroup(VRRadioButtonGroup group) {
        this.group = group;
    }

    /**
     * 対応する親グループ内のボタンの選択状態を設定します。
     * 
     * @param selected 対応する親グループ内のボタンの選択状態
     */
    public void setSelected(boolean selected) {
        if (getButton() != null) {
            getButton().setSelected(selected);
        }
    }

    /**
     * 表示する値 を設定します。
     * 
     * @param value 表示する値
     */
    public void setText(Object value) {
        this.text = value;
    }

    /**
     * 対応する親グループ内のボタンの表示可否を設定します。
     * 
     * @param visible 対応する親グループ内のボタンの表示可否
     */
    public void setVisible(boolean visible) {
        if (getButton() != null) {
            getButton().setVisible(visible);
        }
    }

    public String toString() {
        if (getText() == null) {
            return null;
        }
        return getText().toString();
    }
}
