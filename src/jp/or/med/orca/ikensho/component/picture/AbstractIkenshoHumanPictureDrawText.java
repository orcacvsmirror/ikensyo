package jp.or.med.orca.ikensho.component.picture;

import java.text.ParseException;

import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;


/** TODO <HEAD> */
abstract public class AbstractIkenshoHumanPictureDrawText extends AbstractIkenshoHumanPictureDrawPoint {
    protected String text = "";;

    /**
     * text Çï‘ÇµÇ‹Ç∑ÅB
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * text Çê›íËÇµÇ‹Ç∑
     * @param text text
     */
    public void setText(String text) {
        this.text = text;
    }

    public void input(VRMap row) throws ParseException {
      super.input(row);
      this.setText(String.valueOf(VRBindPathParser.get("STRING", row)));
    }
    public void output(VRMap row) throws ParseException {
      super.output(row);
      VRBindPathParser.set("STRING", row, this.getText());
    }
}
