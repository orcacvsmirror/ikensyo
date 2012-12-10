package jp.nichicom.ac.component.mainmenu;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JButton;
import javax.swing.plaf.basic.BasicGraphicsUtils;

/**
 * メインメニュー用のボタンです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see JButton
 */

public class ACMainMenuButton
    extends JButton{

  private Dimension preferredSize;

  /**
   * コンストラクタです。
   */
  public ACMainMenuButton() {
    this(null);
  }

  /**
   * @param text テキスト
   */
  public ACMainMenuButton(String text) {
    super(text);
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
    
//    this.addKeyListener(new KeyAdapter(){
//        public void keyPressed(KeyEvent e) {
//            switch(e.getKeyCode()){
//            case KeyEvent.VK_UP:
//                NCMainMenuButton.this.transferFocusBackward();
//                break;
//            case KeyEvent.VK_DOWN:
//                NCMainMenuButton.this.transferFocus();
//                break;
//            }
//            
//        }
//    });
  }

  public Dimension getPreferredSize() {
    if (preferredSize != null) {
      return preferredSize;
    }

    FontMetrics fm = getFontMetrics(getFont());
    int s = fm.getHeight() * 6;

    int len = 3;

    if (getText() != null && getText().length() > 0) {
      String[] sl = getText().split(System.getProperty("line.separator"));
      for (int i = 0; i < sl.length; i++) {
        s = (int) Math.max(s, fm.stringWidth(sl[i]));
      }

      if (sl.length > len) {
        len = sl.length;
      }

    }

    return new Dimension(s + fm.getHeight() * 3, fm.getHeight() * len);

  }

  public void setPreferredSize(Dimension preferredSize) {
    this.preferredSize = preferredSize;
  }

  public void paint(Graphics g) {
    Graphics2D g2 = (Graphics2D) g;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);

    g2.setFont(getFont());
    FontMetrics fm = g2.getFontMetrics();

    Color iconC = getBackground();
    g2.setPaint(iconC);

    boolean filled = (model.isRollover() || model.isSelected() ||
                      model.isPressed() || isFocusOwner());

    if (filled) {
      Color cb = getBackground().brighter().brighter();
      Color ct = cb.brighter();

      if (model.isPressed() || model.isSelected() || isFocusOwner()) {
        ct = getBackground().brighter().brighter();
        cb = getBackground().brighter();
      }

      g2.setPaint(new GradientPaint(0, 0, ct, 0, getHeight(), cb));

      g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),
                                          getHeight(), getHeight()));

    }
    else {
      iconC = getBackground().brighter();
    }

    int iconw = fm.getHeight() * 2;
    int iconh = iconw;

    int mgn = (getHeight() - iconh) / 2;

    if (getIcon() != null) {
      iconw = getIcon().getIconWidth();
      iconh = getIcon().getIconHeight();

      mgn = (getHeight() - iconh) / 2;
      getIcon().paintIcon(this, g2, mgn, mgn);
      g2.setStroke(new BasicStroke(2));
      if (filled) {
        g2.draw(new Ellipse2D.Double(mgn, mgn, iconw, iconh));
      }

    }
    else {
      g2.setPaint(iconC);
      g2.fill(new Ellipse2D.Double(mgn, mgn, iconw, iconh));
    }

    g2.setPaint(getForeground());
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);

    if (getText() != null && getText().length() > 0) {
      String[] sl = getText().split(System.getProperty("line.separator"));
      int l = iconw + mgn * 2;
      int w = getWidth() - l - mgn;
      int t = getHeight() / 2 - sl.length * fm.getHeight() / 2 + fm.getAscent();
      for (int i = 0; i < sl.length; i++) {
        int sw = 0;
        switch (getHorizontalAlignment()) {
          case JButton.RIGHT:
            sw = w - fm.stringWidth(sl[i]);
            break;
          case JButton.CENTER:
            sw = w / 2 - fm.stringWidth(sl[i]) / 2;
            break;

        }
        BasicGraphicsUtils.drawStringUnderlineCharAt(g2, sl[i], getDisplayedMnemonicIndex(),
                                                   l + sw, t + i * fm.getHeight());
//        g2.drawString(sl[i], l + sw, t + i * fm.getHeight());
      }

    }


  }
  private void jbInit() throws Exception {
    setOpaque(false);
    setBorder(null);
    setContentAreaFilled(false);
    setHorizontalAlignment(JButton.LEFT);
    setBackground(new java.awt.Color(102, 102, 255));

    Font nowFont = getFont();
    if(nowFont==null){
      setFont(new java.awt.Font("Dialog", java.awt.Font.PLAIN, 18));
    }else{
      setFont(new java.awt.Font(nowFont.getName(), nowFont.getStyle(), 18));
    }
  }

}
