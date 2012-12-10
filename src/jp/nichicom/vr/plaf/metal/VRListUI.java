/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JComponent;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicListUI;

/**
 * VR Look&FeelにおけるリストUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRListUI extends BasicListUI {

	private Color bgColor;
	
    public static ComponentUI createUI(JComponent c) {
        return new VRListUI();
    }

    protected void paintCell(

    Graphics g, int row, Rectangle rowBounds, ListCellRenderer cellRenderer, ListModel dataModel, ListSelectionModel selModel, int leadIndex) {

        Object value = dataModel.getElementAt(row);
        boolean cellHasFocus = list.hasFocus() && (row == leadIndex);
        boolean isSelected = selModel.isSelectedIndex(row);

        Component rendererComponent = cellRenderer.getListCellRendererComponent(list, value, row, isSelected, cellHasFocus);
        
        if (list.hasFocus() && (rendererComponent.getBackground().equals(list.getBackground()))) {
        //    rendererComponent.setBackground(VRDraw.blend(VRLookAndFeel.getTextFocusBackground(), list.getBackground(), 0.5));
        	rendererComponent.setBackground(getBgColor());
        }

        int cx = rowBounds.x;
        int cy = rowBounds.y;
        int cw = rowBounds.width;
        int ch = rowBounds.height;
        rendererPane.paintComponent(g, rendererComponent, list, cx, cy, cw, ch, true);
    }
    
    private Color getBgColor(){
    	if(bgColor==null){
    		if(list.hasFocus()){
    			bgColor= VRDraw.blend(VRLookAndFeel.getTextFocusBackground(), list.getBackground(), 0.5);
    		}else{
    			bgColor=list.getBackground();
    		}
    	}
    	
    	return bgColor;
    }
    
	public void paint(Graphics g, JComponent c) {
		if(c.hasFocus()){
			Rectangle r=g.getClipBounds();
			g.setColor( getBgColor());
			g.fillRect(r.x,r.y,r.width,r.height);
		}
		
		super.paint(g, c);
		
	}
    /**
     * VR用フォーカスハンドラー <br />
     * VR用のフォーカスハンドラーです。 <br />
     * 
     * 作成日 2005/02/26 <br />
     * 更新日 <br />
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved. <br />
     * 
     * @author Susumu Nakahara
     * @version 1.0
     */
    public class VRFocusHandler implements FocusListener {
        protected void repaintCellFocus() {
            bgColor=null;
            list.repaint();
        }

        public void focusGained(FocusEvent e) {
            repaintCellFocus();
        }

        public void focusLost(FocusEvent e) {
            repaintCellFocus();
        }
    }

    protected FocusListener createFocusListener() {
        return new VRFocusHandler();
    }

}