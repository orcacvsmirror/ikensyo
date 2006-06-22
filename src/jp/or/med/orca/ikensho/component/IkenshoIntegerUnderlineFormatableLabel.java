package jp.or.med.orca.ikensho.component;

import java.text.ParseException;

import javax.swing.Icon;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.event.VRBindEvent;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIntegerUnderlineFormatableLabel
    extends IkenshoUnderlineFormatableLabel {

  public IkenshoIntegerUnderlineFormatableLabel() {
    super();
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  public IkenshoIntegerUnderlineFormatableLabel(Icon image) {
    super(image);
  }

  public IkenshoIntegerUnderlineFormatableLabel(Icon image, int horizontalAlignment) {
    super(image, horizontalAlignment);
  }

  public IkenshoIntegerUnderlineFormatableLabel(String text) {
    super(text);
  }

  public IkenshoIntegerUnderlineFormatableLabel(String text, Icon image,
                                      int horizontalAlignment) {
    super(text, image, horizontalAlignment);
  }

  public IkenshoIntegerUnderlineFormatableLabel(String text, int horizontalAlignment) {
    super(text, horizontalAlignment);
  }

  public void setUnderline(boolean underline) {
    super.setUnderline(underline);
    if (isAutoApplySource()) {
      try {
        applySource();
      }
      catch (ParseException e) {
        ACCommon.getInstance().showExceptionMessage(e);
      }
    }
  }

  public void applySource() throws ParseException {
    Integer val;
    if (super.isUnderline()) {
      val = new Integer(1);
    }
    else {
      val = new Integer(0);
    }
    if (VRBindPathParser.set(getBindPath(), getSource(), val)) {
      fireApplySource(new VRBindEvent(this));
    }
  }

  public void bindSource() throws ParseException {
    Object obj = VRBindPathParser.get(getBindPath(), getSource());

    int val;
    if (obj == null) {
      return;
    }
    else if (obj instanceof Integer) {
      val = ( (Integer) obj).intValue();
    }
    else {
      val = Integer.valueOf(String.valueOf(obj)).intValue();
    }

    super.setUnderline(val != 0);
    fireBindSource(new VRBindEvent(this));
  }

  public Object createSource() {
    return new Integer(0);
  }
  private void jbInit() throws Exception {
    super.setUnderline(false);
  }
}
