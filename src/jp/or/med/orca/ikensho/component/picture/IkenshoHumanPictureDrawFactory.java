package jp.or.med.orca.ikensho.component.picture;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.TexturePaint;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;


/** TODO <HEAD_IKENSYO> */
public class IkenshoHumanPictureDrawFactory {
  protected IkenshoHumanPictureDrawFactory() {
  }

  private static BufferedImage DOWNLINEIMAGE = new BufferedImage(8, 8,
      BufferedImage.TYPE_INT_ARGB);

  private static BufferedImage RISELINEIMAGE = new BufferedImage(8, 8,
      BufferedImage.TYPE_INT_ARGB);

  private static Stroke LINESTROKE = new BasicStroke(2f);
  private static Stroke BOLDSTROKE = new BasicStroke(16f, BasicStroke.CAP_ROUND,
      BasicStroke.JOIN_ROUND);

  private static Stroke DOTSTROKE = new BasicStroke(1f, BasicStroke.CAP_BUTT,
      BasicStroke.JOIN_MITER, 10f, new float[] {3, 3}
      , 1f);

  public static TexturePaint getDownLineTexture(Color color) {
    Graphics2D g = DOWNLINEIMAGE.createGraphics();
    g.setColor(color);
    g.draw(new Line2D.Double(0, 0, 7, 7));
    return new TexturePaint(DOWNLINEIMAGE, new Rectangle(0, 0, 8, 8));
  }

  public static TexturePaint getRiseLineTexture(Color color) {
    Graphics2D g = RISELINEIMAGE.createGraphics();
    g.setColor(color);
    g.draw(new Line2D.Double(0, 7, 7, 0));
    return new TexturePaint(RISELINEIMAGE, new Rectangle(0, 0, 8, 8));
  }

  public static class DrawDownLine
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.draw(new Line2D.Double(x - 12, y - 12, x + 12, y + 12));
      g2.draw(new Line2D.Double(x - 11, y - 12, x + 12, y + 11));
      g2.draw(new Line2D.Double(x - 12, y - 11, x + 11, y + 12));
    }
  }

  public static class DrawRiseLine
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.draw(new Line2D.Double(x - 12, y + 12, x + 12, y - 12));
      g2.draw(new Line2D.Double(x - 11, y + 12, x + 12, y - 11));
      g2.draw(new Line2D.Double(x - 12, y + 11, x + 11, y - 12));
    }
  }

  public static class DrawHorizontalLine
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.draw(new Line2D.Double(x - 15, y, x + 15, y));
      g2.draw(new Line2D.Double(x - 15, y + 1, x + 15, y + 1));
    }
  }

  public static class DrawVerticalLine
      extends AbstractIkenshoHumanPictureDrawPoint {

    public void draw(Graphics2D g2) {
      g2.draw(new Line2D.Double(x, y - 15, x, y + 15));
      g2.draw(new Line2D.Double(x + 1, y - 15, x + 1, y + 15));
    }
  }

  public static class DrawLine
      extends AbstractIkenshoHumanPictureDrawLine {
    public Stroke getStroke() {
      return LINESTROKE;
    }

    public void draw(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.draw(new Line2D.Double(x, y, x2, y2));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }

  public static class DrawCircle
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.draw(new Ellipse2D.Double(x - 15, y - 15, 31, 31));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }

  public static class DrawArea
      extends AbstractIkenshoHumanPictureDrawArea {
    public Stroke getStroke() {
      return DOTSTROKE;
    }

    public void draw(Graphics2D g2) {
      g2.draw(new Rectangle(x, y, width, height));
    }
  }

  public static class DrawEllipse
      extends AbstractIkenshoHumanPictureDrawArea {
    public void draw(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.draw(new Ellipse2D.Double(x, y, width, height));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_OFF);

    }
  }

  public static class DrawDot
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new Ellipse2D.Double(x - 8, y - 10, 16, 20));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }

  public static class DrawBlackArea
      extends AbstractIkenshoHumanPictureDrawArea {
    public void draw(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.fill(new Ellipse2D.Double(x, y, width, height));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }

  public static class DrawBoldLineArea
      extends AbstractIkenshoHumanPictureDrawLine {
    public Stroke getStroke() {
      return BOLDSTROKE;
    }

    public void draw(Graphics2D g2) {
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_ON);
      g2.draw(new Line2D.Double(x, y, x2, y2));
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                          RenderingHints.VALUE_ANTIALIAS_OFF);
    }
  }

  public static class DrawDownLineLine
      extends AbstractIkenshoHumanPictureDrawLine {
    public Stroke getStroke() {
      return BOLDSTROKE;
    }

    public void draw(Graphics2D g2) {
      g2.setPaint(getDownLineTexture(getColor()));
      g2.draw(new Line2D.Double(x, y, x2, y2));
    }
  }

  public static class DrawRiseLineLine
      extends AbstractIkenshoHumanPictureDrawLine {
    public Stroke getStroke() {
      return BOLDSTROKE;
    }

    public void draw(Graphics2D g2) {
      g2.setPaint(getRiseLineTexture(getColor()));
      g2.draw(new Line2D.Double(x, y, x2, y2));
    }
  }

  public static class DrawDownLineElipse
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.setPaint(getDownLineTexture(getColor()));
      g2.fill(new Ellipse2D.Double(x - 8, y - 10, 16, 20));
    }
  }

  public static class DrawRiseLineElipse
      extends AbstractIkenshoHumanPictureDrawPoint {
    public void draw(Graphics2D g2) {
      g2.setPaint(getRiseLineTexture(getColor()));
      g2.fill(new Ellipse2D.Double(x - 8, y - 10, 16, 20));
    }
  }

  public static class DrawText
      extends AbstractIkenshoHumanPictureDrawText {
    public void draw(Graphics2D g2) {
      if (text != null) {
        int ty = y + g2.getFontMetrics().getAscent() / 2;
        g2.setPaint(Color.white);
        for (int i = -1; i <= 1; i++) {
          for (int j = -1; j <= 1; j++) {
            if (i != 0 || j != 0) {
              g2.drawString(text, x + i, ty + j);
            }
          }
        }
        g2.setPaint(Color.black);
        g2.drawString(text, x, ty);
      }
    }
  }

}
