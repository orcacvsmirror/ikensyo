package jp.or.med.orca.ikensho.component.picture;

import java.text.ParseException;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/** TODO <HEAD> */
abstract public class AbstractIkenshoHumanPictureDrawArea extends AbstractIkenshoHumanPictureDrawPoint {

    protected int width;

    protected int height;

    /**
     * height を返します。
     * @return height
     */
    public int getHeight() {
        return height;
    }

    /**
     * height を設定します
     * @param height height
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * width を返します。
     * @return width
     */
    public int getWidth() {
        return width;
    }

    /**
     * width を設定します
     * @param width width
     */
    public void setWidth(int width) {
        this.width = width;
    }
    public void input(VRMap row) throws ParseException {
      super.input(row);
      this.setWidth(Integer.parseInt(String.valueOf(VRBindPathParser.get("SX",
          row))));
      this.setHeight(Integer.parseInt(String.valueOf(VRBindPathParser.get("SY",
          row))));
    }
    public void output(VRMap row) throws ParseException {
      super.output(row);
      VRBindPathParser.set("SX", row, new Integer(this.getWidth()));
      VRBindPathParser.set("SY", row, new Integer(this.getHeight()));
    }

}
