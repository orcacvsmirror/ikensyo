package jp.nichicom.ac.container;

import java.awt.Color;

import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * 内部項目を括弧で囲むことを想定したパネルです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACPanel
 */

public class ACParentHesesPanelContainer extends ACPanel {
    /**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private ACLabel hesesBegin = new ACLabel();
    private ACLabel hesesEnd = new ACLabel();

    public String getBeginText() {
        return hesesBegin.getText();
    }

    public String getEndText() {
        return hesesEnd.getText();
    }

    public void setBeginText(String text) {
        hesesBegin.setText(text);
    }

    public void setEndText(String text) {
        hesesEnd.setText(text);
    }

    public Color getBeginForeground() {
        return hesesBegin.getForeground();
    }

    public Color getEndForeground() {
        return hesesEnd.getForeground();
    }

    public void setBeginForeground(Color foreground) {
        hesesBegin.setForeground(foreground);
    }

    public void setEndForeground(Color foreground) {
        hesesEnd.setForeground(foreground);
    }

    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        hesesBegin.setEnabled(enabled);
        hesesEnd.setEnabled(enabled);
    }

    public ACParentHesesPanelContainer() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() {
        hesesBegin.setText("（");
        hesesEnd.setText("）");
        setHgap(6);

        setVgap(0);
        this.setOpaque(false);
        this.add(hesesBegin, VRLayout.WEST);
        this.add(hesesEnd, VRLayout.EAST);
    }
}
