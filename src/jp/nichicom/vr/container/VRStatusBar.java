/** TODO <HEAD> */
package jp.nichicom.vr.container;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.vr.layout.VRLayout;

/**
 * コントロールを追加可能なステータスバークラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 * @see JPanel
 */
public class VRStatusBar extends JPanel {

    private JLabel status;

    /**
     * テキスト表示用のラベル を返します。
     * 
     * @return テキスト表示用のラベル
     */
    public JLabel getStatus() {
        if (status == null) {
            status = new JLabel();
            status
                    .setBorder(javax.swing.BorderFactory
                            .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));

        }
        return status;
    }

    /**
     * テキスト表示用のラベル を設定します。
     * 
     * @param textLabel テキスト表示用のラベル
     */
    public void setStatus(JLabel textLabel) {
        this.status = textLabel;
    }

    /**
     * コンストラクタです。
     */
    public VRStatusBar() {
        super();
        setOpaque(true);

        setLayout(new VRLayout());
        setBorder(javax.swing.BorderFactory
                .createEtchedBorder(javax.swing.border.EtchedBorder.LOWERED));
        
        initComponent();

    }

    /**
     * コンストラクタ実行後に必ず呼ばれる初期化処理です。
     */
    protected void initComponent() {
        add(getStatus(), VRLayout.CLIENT);
    }

    /**
     * テキスト表示用のラベルの文字列を返します。
     * 
     * @return 文字列
     */
    public String getText() {
        return getStatus().getText();
    }

    /**
     * テキスト表示用のラベルに文字列を設定します。
     * 
     * @param text 設定文字列
     */
    public void setText(String text) {
        getStatus().setText(text);
        repaint();
    }

    /**
     * テキスト表示用のラベルを表示するかを返します。
     * 
     * @return 表示するか
     */
    public boolean isStatusVisible() {
        return getStatus().isVisible();
    }

    /**
     * テキスト表示用のラベルを表示するかを設定します。
     * 
     * @param textVisible 表示するか
     */
    public void setStatusVisible(boolean textVisible) {
        getStatus().setVisible(textVisible);
    }
    
    public void setForeground(Color foreground){
        super.setForeground(foreground);
        getStatus().setForeground(foreground);
    }
    
    public void setBackground(Color background){
        super.setBackground(background);
        getStatus().setBackground(background);
    }

}