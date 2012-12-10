package jp.nichicom.ac.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JToggleButton;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.io.ACResourceIconPooler;

/**
 * トグルボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */
public class ACToggleButton extends JToggleButton {
    private boolean toggleState;
    private String iconPathPushed;
    private String iconPathUnPushed;
    private String textPushed;
    private String textUnPushed;
    private char mnemonicPushed;
    private char mnemonicUnPushed;

    /**
     * コンストラクタ
     */
    public ACToggleButton() {
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 押下状態を反転します。
     */
    public void swapPushed(){
      setPushed(!isPushed()); //フラグの反転
    }

    /**
     * 初期化
     * @throws Exception
     */
    private void jbInit() throws Exception {
        this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        this.setIconTextGap(0);
        this.setMargin(new java.awt.Insets(0, 4, 0, 4));
        this.setContentAreaFilled(true);

        this.addActionListener(new ActionListener() {
          /**
           * ボタン押下時の動作
           * @param e ActionEvent
           */
          public void actionPerformed(ActionEvent e) {
            swapPushed();
          }
        });
        toggleState = false;
        setButtonProperty();
    }

    /**
     * トグルの状態により、それぞれのプロパティを設定する
     */
    private void setButtonProperty() {
        if (isPushed()) {
            this.setText(textPushed);
            this.setIconPath(iconPathPushed);
            this.setMnemonic(mnemonicPushed);
        }
        else {
            this.setText(textUnPushed);
            this.setIconPath(iconPathUnPushed);
            this.setMnemonic(mnemonicUnPushed);
        }
    }


    /**
     * 押下状態を設定します。
     * @param pushed true:押す、false:押さない
     */
    public void setPushed(boolean pushed) {
        toggleState = pushed;
        setButtonProperty();
    }

    /**
     * 押下状態時の表示文字列を設定します。
     * @param textPushed 押下状態時の表示文字列
     */
    public void setTextPushed(String textPushed) {
        this.textPushed = textPushed;
        setButtonProperty();
    }

    /**
     * 通常状態時の表示文字列を設定します。
     * @param textUnPushed 通常状態時の表示文字列
     */
    public void setTextUnPushed(String textUnPushed) {
        this.textUnPushed = textUnPushed;
        setButtonProperty();
    }

    /**
     * アイコンタイプを設定します。
     * @param iconPath アイコンタイプ
     */
    public void setIconPath(String iconPath) {
        if ( (iconPath == null) || ("".equals(iconPath))) {
            setIcon(null);
        }
        else {
            try {
                setIcon(ACResourceIconPooler.getInstance().getImage(iconPath));
            }
            catch (Exception ex) {
              ACCommon.getInstance().showExceptionMessage(ex);
                setIcon(null);
            }
        }
    }

    /**
     * 押下状態時のアイコンのリソースパスを設定します。
     * @param iconPathPushed 押下状態時のアイコンのリソースパス
     */
    public void setIconPathPushed(String iconPathPushed) {
        this.iconPathPushed = iconPathPushed;
        setButtonProperty();
    }

    /**
     * 通常状態時のアイコンのリソースパスを設定します。
     * @param iconPathUnPushed 通常状態時のアイコンのリソースパス
     */
    public void setIconPathUnPushed(String iconPathUnPushed) {
        this.iconPathUnPushed = iconPathUnPushed;
        setButtonProperty();
    }

    /**
     * 通常状態時のニーモニックを設定します。
     * @param mnemonicPushed 通常状態時のニーモニック
     */
    public void setMnemonicPushed(char mnemonicPushed) {
        this.mnemonicPushed = mnemonicPushed;
        setButtonProperty();
    }

    /**
     * 押下状態時のニーモニックを設定します。
     * @param mnemonicUnPushed 押下状態時のニーモニック
     */
    public void setMnemonicUnPushed(char mnemonicUnPushed) {
        this.mnemonicUnPushed = mnemonicUnPushed;
        setButtonProperty();
    }

    /**
     * 押下状態を取得します。
     * @return true:押されている、false:押されていない
     */
    public boolean isPushed() {
        return toggleState;
    }

    /**
     * 押下状態時の表示文字列を取得します。
     * @return 押下状態時の表示文字列
     */
    public String getTextPushed() {
        return textPushed;
    }
    /**
     * 通常状態時の表示文字列を取得します。
     * @return 通常状態時の表示文字列
     */
    public String getTextUnPushed() {
        return textUnPushed;
    }

    /**
     * 押下状態時のアイコンのリソースパスを取得します。
     * @return 押下状態時のアイコンのリソースパス
     */
    public String getIconPathPushed() {
        return iconPathPushed;
    }
    /**
     * 通常状態時のアイコンのリソースパスを取得します。
     * @return 通常状態時のアイコンのリソースパス
     */
    public String getIconPathUnPushed() {
        return iconPathUnPushed;
    }

    /**
     * 押下状態時のニーモニックを取得します。
     * @return 押下状態時のニーモニック
     */
    public char getMnemonicPushed() {
        return mnemonicPushed;
    }

    /**
     * 通常状態時のニーモニックを取得します。
     * @return 通常状態時のニーモニック
     */
    public char getMnemonicUnPushed() {
        return mnemonicPushed;
    }
}
