package jp.nichicom.ac.component;

import java.awt.Color;

import javax.swing.Action;
import javax.swing.Icon;

import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.component.VRButton;

/**
 * アイコンをリソースパスで指定可能なボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see VRButton
 */
public class ACButton extends VRButton {
    private boolean environmentChecked = false;
    private boolean colorIgnore = false;
    protected String iconType = "";

    /**
     * 環境チェックを完了したか を返します。
     * 
     * @return 環境チェックを完了したか
     */
    protected boolean isEnvironmentChecked() {
        return environmentChecked;
    }

    /**
     * 環境チェックを完了したか を設定します。
     * 
     * @param environmentChecked 環境チェックを完了したか
     */
    protected void setEnvironmentChecked(boolean environmentChecked) {
        this.environmentChecked = environmentChecked;
    }

    public ACButton() {
        super();
    }

    public ACButton(Action a) {
        super(a);
    }

    public ACButton(Icon icon) {
        super(icon);
    }

    public ACButton(String text) {
        super(text);
    }

    public ACButton(String text, Icon icon) {
        super(text, icon);
    }

    protected void initComponent() {
        super.initComponent();
        checkEnvironment();
    }

    /**
     * 環境に依存する設定変更を行ないます。
     */
    protected void checkEnvironment() {
        if (isEnvironmentChecked()) {
            // 環境チェック済ならばなにもしない
            return;
        }
        setEnvironmentChecked(true);
        String osName = System.getProperty("os.name");
        if ((osName == null) || (osName.indexOf("Mac") < 0)) {
            // Mac以外は色変更を許す
            setColorIgnore(false);
        } else {
            // Macは色変更を無視する
            setColorIgnore(true);
        }
    }

    public void setForeground(Color foreground) {
        if (isColorIgnore()) {
            return;
        }
        super.setForeground(foreground);
    }

    public void setBackground(Color background) {
        if (isColorIgnore()) {
            return;
        }
        super.setBackground(background);
    }

    /**
     * 色設定を無視するかを返します。
     * 
     * @return 色設定を無視するか
     */
    public boolean isColorIgnore() {
        // 環境チェック必須
        checkEnvironment();
        return colorIgnore;
    }

    /**
     * 色設定を無視するかを設定します。
     * 
     * @param colorIgnore 色設定を無視するか
     */
    public void setColorIgnore(boolean colorIgnore) {
        this.colorIgnore = colorIgnore;
        if (colorIgnore) {
            super.setForeground(null);
            super.setBackground(null);
        }
    }

    /**
     * アイコンのリソースパスを返します。
     * 
     * @return アイコンのリソースパス
     */
    public String getIconPath() {
        return iconType;
    }

    /**
     * アイコンのリソースパスを設定します。
     * 
     * @param iconPath アイコンのリソースパス
     */
    public void setIconPath(String iconPath) {
        this.iconType = iconPath;
        if ((iconPath == null) || ("".equals(iconPath))) {
            setIcon(null);
        } else {
            try {
                setIcon(ACResourceIconPooler.getInstance().getImage(iconPath));
            } catch (Exception ex) {
                ex.printStackTrace();
                setIcon(null);
                this.iconType = "";
            }
        }
    }

}
