package jp.nichicom.ac.component;

import java.awt.Dimension;
import java.awt.FontMetrics;

import javax.swing.Action;
import javax.swing.Icon;

/**
 * 業務ボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACButton
 */
public class ACAffairButton extends ACButton {
    public ACAffairButton() {
        super();
    }

    public ACAffairButton(Action a) {
        super(a);
    }

    public ACAffairButton(Icon icon) {
        super(icon);
    }

    public ACAffairButton(String text) {
        super(text);
    }

    public ACAffairButton(String text, Icon icon) {
        super(text, icon);
    }

    protected void initComponent() {
        super.initComponent();
        // Macの時は処理を行わない。
//        if (isColorIgnore()) {
//            return;
//        }
        this.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        this.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        this.setIconTextGap(0);
        this.setMargin(new java.awt.Insets(0, 4, 0, 4));
        this.setContentAreaFilled(true);
        this.setForeground(java.awt.Color.white);
        //if (IkenshoMainMenu.getMojiSize().equals("Small")){
//        if (ACScreenMode.isSmall()) {
//        	this.setMinimumSize(new Dimension(90, 44));
//        	this.setPreferredSize(new Dimension(90, 44));
//        }
        
        FontMetrics fo = getFontMetrics(getFont());
        int width = fo.stringWidth("＝＝＝＝(M)") + 24;
        int height = fo.getHeight() + 28;
        this.setPreferredSize(new Dimension(width, height));
        
        this.setBackground(new java.awt.Color(0, 51, 153));
    }

}
