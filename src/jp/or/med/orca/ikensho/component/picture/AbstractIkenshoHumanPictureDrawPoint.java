package jp.or.med.orca.ikensho.component.picture;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.text.ParseException;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/** TODO <HEAD> */
abstract public class AbstractIkenshoHumanPictureDrawPoint implements IkenshoHumanPictureDrawable {
    private static Stroke DEFSTROKE = new BasicStroke(1f);

    protected Stroke stroke = DEFSTROKE;

    protected Color color = Color.black;

    protected int x;

    protected int y;

    protected int width;

    protected int height;

    /**
     * color ��Ԃ��܂��B
     * @return color
     */
    public Color getColor() {
        return color;
    }

    /**
     * color ��ݒ肵�܂�
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /**
     * stroke ��Ԃ��܂��B
     * @return stroke
     */
    public Stroke getStroke() {
        return stroke;
    }

    /**
     * stroke ��ݒ肵�܂�
     * @param stroke stroke
     */
    public void setStroke(Stroke stroke) {
        this.stroke = stroke;
    }

    /**
     * x ��Ԃ��܂��B
     * @return x
     */
    public int getX() {
        return x;
    }

    /**
     * x ��ݒ肵�܂�
     * @param x x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * y ��Ԃ��܂��B
     * @return y
     */
    public int getY() {
        return y;
    }

    /**
     * y ��ݒ肵�܂�
     * @param y y
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * height ��Ԃ��܂��B
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * height ��ݒ肵�܂�
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * width ��Ԃ��܂��B
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * width ��ݒ肵�܂�
     * @param width width
     */
    public void setWidth(int width) {
        this.width = width;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public void execute(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(getStroke());
        g2.setColor(getColor());
        draw(g2);
    }

    abstract public void draw(Graphics2D g2);

    public void input(VRMap row) throws ParseException {
      Object x = VRBindPathParser.get("X", row);
      Object y = VRBindPathParser.get("Y", row);
      if(x instanceof Double){
        this.setX( ( (Double) x).intValue());
      }else if(x instanceof Integer){
        this.setX( ( (Integer) x).intValue());
      }
      if(y instanceof Double){
        this.setY( ( (Double) y).intValue());
      }else if(y instanceof Integer){
        this.setY( ( (Integer) y).intValue());
      }
    }

    public void output(VRMap row) throws ParseException {
      VRBindPathParser.set("X", row, new Integer(this.getX()));
      VRBindPathParser.set("Y", row, new Integer(this.getY()));
    }

}
