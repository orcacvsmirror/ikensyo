package jp.or.med.orca.ikensho.component.picture;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;

/** TODO <HEAD> */
public class IkenshoBasicHumanPictureDrawFactory {

    public static final int DRAW_LINE = 0;

    public static final int DRAW_RECTANGLE = 1;

    public static final int FILL_RECTANGLE = 2;

    public static final int DRAW_ELLIPSE = 3;

    public static final int FILL_ELLIPSE = 4;

    public static final int FILL_DOWNLINE_ELLIPSE = 5;

    public static final int FILL_RISELINE_ELLIPSE = 6;

    public static IkenshoHumanPictureDrawable create(int type) {
        switch (type) {
        case DRAW_RECTANGLE:
            return new DrawRectangle();
        case FILL_RECTANGLE:
            return new fillRectangl();
        case DRAW_ELLIPSE:
            return new DrawElipse();
        case FILL_ELLIPSE:
            return new fillElipse();
        case FILL_DOWNLINE_ELLIPSE:
            return new fillDownLineElipse();
        case FILL_RISELINE_ELLIPSE:
            return new fillRiseLineElipse();
        }
        return new DrawLine();
    }

    static class DrawLine extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            g2.draw(new Line2D.Double(x, y, x + width, y + height));
        }
    }

    static class DrawRectangle extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            g2.draw(new Rectangle(x, y, width, height));
        }
    }

    static class fillRectangl extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            g2.fill(new Rectangle(x, y, width, height));
        }
    }

    static class DrawElipse extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            g2.draw(new Ellipse2D.Double(x, y, width, height));
        }
    }

    static class fillElipse extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            g2.fill(new Ellipse2D.Double(x, y, width, height));
        }
    }

    static class fillDownLineElipse extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            Shape back = g2.getClip();
            g2.setClip(new Ellipse2D.Double(x, y, width, height));

            int xs = (int) Math.floor((x) / 8) * 8;
            int ys = (int) Math.floor((y) / 8) * 8;
            int xe = (int) Math.ceil((x + width) / 8) * 8;
            int ye = (int) Math.ceil((y + height) / 8) * 8;
            int ln = xe - xs;

            for (int i = ys - ln; i < ye; i = i + 8) {
                g2.drawLine(xs, i, xs + ln, i + ln);
            }

            g2.setClip(back);
        }
    }

    static class fillRiseLineElipse extends AbstractIkenshoHumanPictureDrawArea {
        public void draw(Graphics2D g2) {
            Shape back = g2.getClip();
            g2.setClip(new Ellipse2D.Double(x, y, width, height));

            int xs = (int) Math.floor((x) / 8) * 8;
            int ys = (int) Math.floor((y) / 8) * 8;
            int xe = (int) Math.ceil((x + width) / 8) * 8;
            int ye = (int) Math.ceil((y + height) / 8) * 8;
            int ln = xe - xs;

            for (int i = ys; i < ye + ln; i = i + 8) {
                g2.drawLine(xs, i, xs + ln, i - ln);
            }

            g2.setClip(back);
        }
    }

}
