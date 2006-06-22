package jp.or.med.orca.ikensho.component;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JToggleButton.ToggleButtonModel;

import jp.nichicom.ac.component.ACIntegerCheckBox;


/** TODO <HEAD_IKENSYO> */
public class IkenshoUndelineIntegerCheckBox
    extends JPanel {
  private IkenshoIntegerUnderlineFormatableLabel text = new IkenshoIntegerUnderlineFormatableLabel();
  private ACIntegerCheckBox check = new ACIntegerCheckBox();
  public IkenshoUndelineIntegerCheckBox(LayoutManager layout,
                                        boolean isDoubleBuffered) {
    super(layout, isDoubleBuffered);
  }

  public IkenshoUndelineIntegerCheckBox(LayoutManager layout) {
    super(layout);
  }

  public IkenshoUndelineIntegerCheckBox(boolean isDoubleBuffered) {
    super(isDoubleBuffered);
  }

  public IkenshoUndelineIntegerCheckBox() {
    super();
    check.setModel(new IkensyoUndelineIntegerCheckBoxButtonModel(text));
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    text.addMouseListener(new MouseAdapter(){
      int flag = FREE;
      static final int FREE = 0;
      static final int PRESS = 1;
      static final int ENTER = 2;
      public void mousePressed(MouseEvent e) {
        flag |= PRESS;
        if(!check.hasFocus()){
          check.requestFocus();
        }
      }
      public void mouseReleased(MouseEvent e) {
        if((flag & (PRESS|ENTER))!=(PRESS|ENTER)){
          return;
        }

        if(check.isSelected()){
          if(text.isUnderline()){
            check.setSelected(false);
            text.setUnderline(false);
          }else{
            text.setUnderline(true);
          }
        }else{
          //未選択ならば選択
          check.setSelected(true);
        }
        if(!check.hasFocus()){
          check.requestFocus();
        }
        flag &= ~PRESS;
      }
      public void mouseEntered(MouseEvent e) {
        flag |= ENTER;
      }
      public void mouseExited(MouseEvent e) {
        flag &= ~ENTER;
      }

    });

  }

  private void jbInit() throws Exception {
    this.setLayout(new BorderLayout());
    check.setOpaque(false);
    this.add(check, BorderLayout.WEST);
    this.add(text, BorderLayout.CENTER);
  }

  /**
   * チェックボックスを返します。
   * @return チェックボックス
   */
  public ACIntegerCheckBox getCheckBox(){
    return check;
  }

  /**
   * ラベルを返します。
   * @return ラベル
   */
  public IkenshoIntegerUnderlineFormatableLabel getLabel(){
    return text;
  }

  public String getCheckBindPath() {
    return check.getBindPath();
  }

  public void setCheckBindPath(String checkBindPath) {
    check.setBindPath(checkBindPath);
  }

  public String getUnderlineBindPath() {
    return text.getBindPath();
  }

  public void setUnderlineBindPath(String underlineBindPath) {
    text.setBindPath(underlineBindPath);
  }

  public String getText() {
    return text.getText();
  }

  public void setText(String text) {
    this.text.setText(text);
  }

  protected class IkensyoUndelineIntegerCheckBoxButtonModel extends ToggleButtonModel {
    protected IkenshoIntegerUnderlineFormatableLabel label;
    public IkensyoUndelineIntegerCheckBoxButtonModel (IkenshoIntegerUnderlineFormatableLabel label) {
      this.label = label;
}

    public void setPressed(boolean b) {
    if ((isPressed() == b) || !isEnabled()) {
        return;
    }

    if (b == false && isArmed()) {

        if (this.isSelected()) {
            if (label.isUnderline()) {
                label.setUnderline(false);
                setSelected(false);
            } else {
                label.setUnderline(true);
            }

        } else {
            setSelected(true);

        }

    }

    if (b) {
        stateMask |= PRESSED;
    } else {
        stateMask &= ~PRESSED;
    }

    fireStateChanged();

    if (!isPressed() && isArmed()) {
        int modifiers = 0;
        AWTEvent currentEvent = EventQueue.getCurrentEvent();
        if (currentEvent instanceof InputEvent) {
            modifiers = ((InputEvent) currentEvent).getModifiers();
        } else if (currentEvent instanceof ActionEvent) {
            modifiers = ((ActionEvent) currentEvent).getModifiers();
        }
        fireActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, getActionCommand(), EventQueue.getMostRecentEventTime(), modifiers));
    }

}

  }
}
