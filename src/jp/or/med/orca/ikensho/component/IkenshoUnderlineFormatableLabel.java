package jp.or.med.orca.ikensho.component;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.Icon;
import javax.swing.SwingUtilities;
import javax.swing.plaf.basic.BasicLabelUI;

import jp.nichicom.ac.component.ACLabel;

/** TODO <HEAD_IKENSYO> */
public class IkenshoUnderlineFormatableLabel extends ACLabel{
  private boolean underline = true;
  public IkenshoUnderlineFormatableLabel() {
      super();
  }

  public IkenshoUnderlineFormatableLabel(Icon image) {
      super(image);
  }

  public IkenshoUnderlineFormatableLabel(Icon image, int horizontalAlignment) {
      super(image, horizontalAlignment);
  }

  public IkenshoUnderlineFormatableLabel(String text) {
      super(text);
  }

  public IkenshoUnderlineFormatableLabel(String text, Icon image, int horizontalAlignment) {
      super(text, image, horizontalAlignment);
  }

  public IkenshoUnderlineFormatableLabel(String text, int horizontalAlignment) {
      super(text, horizontalAlignment);
  }

  public void paint(Graphics g) {
      super.paint(g);
      if (isUnderline()) {
          g.setColor(getForeground());

          if (getUI() instanceof BasicLabelUI) {
              BasicLabelUI ui = (BasicLabelUI) getUI();

              FontMetrics fm = g.getFontMetrics();

              Insets i = getInsets();

              Rectangle viewRect = new Rectangle(getSize());

              viewRect.x += i.left;
              viewRect.y += i.top;
              viewRect.width -= (i.right + viewRect.x);
              viewRect.height -= (i.bottom + viewRect.y);

              Rectangle iconRect = new Rectangle();
              Rectangle textRect = new Rectangle();
              Icon altIcon = getIcon();

              Font f = getFont();
              g.setFont(f);

              // layout the text and icon
              String text = SwingUtilities.layoutCompoundLabel(this, fm, getText(), altIcon != null ? altIcon : null, getVerticalAlignment(), getHorizontalAlignment(), getVerticalTextPosition(), getHorizontalTextPosition(), viewRect, iconRect, textRect, getText() == null ? 0 : getIconTextGap());

              g.fillRect(textRect.x, textRect.y + textRect.height-1, textRect.width, 1);
          } else {
              g.fillRect(0, getHeight() - 1, getWidth(), 1);
          }

      }
  }

  /**
   * â∫ê¸Çà¯Ç≠Ç©Çï‘ÇµÇ‹Ç∑ÅB
   * @return â∫ê¸Çà¯Ç≠Ç©
   */
  public boolean isUnderline() {
    return underline;
  }
  /**
   * â∫ê¸Çà¯Ç≠Ç©Çê›íËÇµÇ‹Ç∑ÅB
   * @param underline â∫ê¸Çà¯Ç≠Ç©
   */
  public void setUnderline(boolean underline) {
    this.underline = underline;
    repaint();
  }

}
