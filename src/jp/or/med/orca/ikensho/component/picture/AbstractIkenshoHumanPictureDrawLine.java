package jp.or.med.orca.ikensho.component.picture;

import java.text.ParseException;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/** TODO <HEAD> */
abstract public class AbstractIkenshoHumanPictureDrawLine extends AbstractIkenshoHumanPictureDrawPoint {

    protected int x2;

    protected int y2;

    /**
     * x2 Çï‘ÇµÇ‹Ç∑ÅB
     * @return x2
     */
    public int getX2() {
        return x2;
    }

    /**
     * x2 Çê›íËÇµÇ‹Ç∑
     * @param x2 x2
     */
    public void setX2(int x2) {
        this.x2 = x2;
    }

    /**
     * y2 Çï‘ÇµÇ‹Ç∑ÅB
     * @return y2
     */
    public int getY2() {
        return y2;
    }

    /**
     * y2 Çê›íËÇµÇ‹Ç∑
     * @param y2 y2
     */
    public void setY2(int y2) {
        this.y2 = y2;
    }

    public void input(VRMap row) throws ParseException {
      super.input(row);
      this.setX2(Integer.parseInt(String.valueOf(VRBindPathParser.get("SX",
          row))));
      this.setY2(Integer.parseInt(String.valueOf(VRBindPathParser.get("SY",
          row))));
    }
    public void output(VRMap row) throws ParseException {
      super.output(row);
      VRBindPathParser.set("SX", row, new Integer(this.getX2()));
      VRBindPathParser.set("SY", row, new Integer(this.getY2()));
    }

}
