/** TODO <HEAD> */
package jp.nichicom.vr.plaf.metal;

import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JComponent;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.basic.BasicPanelUI;

/**
 * VR Look&FeelにおけるパネルUIです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/10/31
 */
public class VRPanelUI extends BasicPanelUI {

    private static VRPanelUI VRPANELUI = new VRPanelUI();

    public static ComponentUI createUI(JComponent c) {
        return VRPANELUI;
    }

    public void paint(Graphics g, JComponent c) {
        if (c.isOpaque()) {
            Graphics2D g2d = (Graphics2D) g;

            if(c.getBackground().equals(c.getRootPane().getContentPane().getBackground())){
                Point cp = c.getLocationOnScreen();
                Point sp=c.getRootPane().getLocationOnScreen();
                g2d.setPaint(new GradientPaint(0.0f, sp.y - cp.y,
                        c.getBackground(), 0.0f, sp.y - cp.y + c.getRootPane().getHeight(), c
                                .getBackground().brighter()));

                
            }else{
                g2d.setPaint(c.getBackground());
                
            }
//            
//            Point cp = c.getLocationOnScreen();
//            //        Point sp=c.getRootPane().getLocationOnScreen();
//
//            //色が同じ親を探っていく処理
//            Container po = c;
//            Container p = c.getParent();
//            while (p != null && (p.getBackground().equals(c.getBackground()))) {
//                po = p;
//                p = p.getParent();
//            }
//
//            Point sp = po.getLocationOnScreen();
//
//            g2d.setPaint(new GradientPaint(0.0f, sp.y - cp.y,
//                    c.getBackground(), 0.0f, sp.y - cp.y + po.getHeight(), c
//                            .getBackground().brighter()));

            g2d.fill(g.getClipBounds());

        }

    }

}