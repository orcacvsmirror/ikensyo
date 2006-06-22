package jp.or.med.orca.ikensho.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACToggleButton;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHintButton
    extends ACToggleButton {
  protected JComponent hintContainer;
  protected IkenshoHintContainer hintArea;
  protected String hintTitle;
  protected String hintText;
  protected JComponent[] followHideComponents;
  protected IkenshoHintButton[] followPressedButtons;
  protected JComponent[] followDisabledComponents;

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoHintButton() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    ( (ToggleButtonModel)this.getModel()).addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED) {
          if (getHintArea() != null) {
            getHintArea().setText(hintText);
            getHintArea().setTitle(hintTitle);
          }
          select = true;

          if (getFollowPressedButtons() != null) {
            int end = getFollowPressedButtons().length;
            for (int i = 0; i < end; i++) {
              if(getFollowPressedButtons()[i].isPushed()){
                getFollowPressedButtons()[i].fireActionPerformed(new ActionEvent(getFollowPressedButtons()[i], 0, ""));
                getFollowPressedButtons()[i].setSelected(false);
              }
            }
          }
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED) {
          select = false;
        }
        else {
          return;
        }

        if (getHintContainer() != null) {
          getHintContainer().setVisible(select);
        }

        if (getFollowHideComponents() != null) {
          int end = getFollowHideComponents().length;
          for (int i = 0; i < end; i++) {
            getFollowHideComponents()[i].setVisible(!select);
          }
        }
        if (getFollowDisabledComponents() != null) {
          int end = getFollowDisabledComponents().length;
          for (int i = 0; i < end; i++) {
            getFollowDisabledComponents()[i].setEnabled(!select);
          }
        }
      }
    });
  }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ��������O
   */
  private void jbInit() throws Exception {
    this.setIconPathUnPushed(IkenshoConstants.IMAGE_PATH_HINT_BUTTON);
    this.setMaximumSize(new Dimension(29, 20));
    this.setPreferredSize(new Dimension(50, 20));
    this.setTextPushed("�߂�");
  }

  /**
   * �q���g��\��������q���g�R���|�[�l���g��Ԃ��܂��B
   * @return �q���g��\��������q���g�R���|�[�l���g
   */
  public IkenshoHintContainer getHintArea() {
    return hintArea;
  }

  /**
   * �q���g�\���̈�ƂȂ�R���e�i��Ԃ��܂��B
   * @return �q���g�\���̈�ƂȂ�R���e�i
   */
  public JComponent getHintContainer() {
    return hintContainer;
  }

  /**
   * �q���g�{����Ԃ��܂��B
   * @return �q���g�{��
   */
  public String getHintText() {
    return hintText;
  }

  /**
   * �q���g�^�C�g����Ԃ��܂��B
   * @return �q���g�^�C�g��
   */
  public String getHintTitle() {
    return hintTitle;
  }

  /**
   * �q���g�^�C�g����ݒ肵�܂��B
   * @param hintTitle �q���g�^�C�g��
   */
  public void setHintTitle(String hintTitle) {
    this.hintTitle = hintTitle;
  }

  /**
   * �q���g�{����ݒ肵�܂��B
   * @param hintText �q���g�{��
   */
  public void setHintText(String hintText) {
    this.hintText = hintText;
  }

  /**
   * �q���g�\���̈�ƂȂ�R���e�i��ݒ肵�܂��B
   * @param hintContainer �q���g�\���̈�ƂȂ�R���e�i
   */
  public void setHintContainer(JComponent hintContainer) {
    this.hintContainer = hintContainer;
  }

  /**
   * �q���g��\��������q���g�R���|�[�l���g��ݒ肵�܂��B
   * @param hintArea �q���g��\��������q���g�R���|�[�l���g
   */
  public void setHintArea(IkenshoHintContainer hintArea) {
    this.hintArea = hintArea;
  }

  /**
   * �A�����ĕ\����؂�ւ���R���|�[�l���g�Q��Ԃ��܂��B
   * @return �A�����ĕ\����؂�ւ���R���|�[�l���g�Q
   */
  public JComponent[] getFollowHideComponents() {
    return followHideComponents;
  }

  /**
   * �A�����ĕ\����؂�ւ���R���|�[�l���g�Q��ݒ肵�܂��B
   * @param followVisibleComponents �A�����ĕ\����؂�ւ���R���|�[�l���g�Q
   */
  public void setFollowHideComponents(JComponent[] followVisibleComponents) {
    this.followHideComponents = followVisibleComponents;
  }

  /**
   * �A�����ĉ���������Ԃ�؂�ւ���{�^���Q��Ԃ��܂��B
   * @return �A�����ĉ���������Ԃ�؂�ւ���{�^���Q
   */
  public IkenshoHintButton[] getFollowPressedButtons() {
    return followPressedButtons;
  }

  /**
   * �A�����ĉ���������Ԃ�؂�ւ���{�^���Q��ݒ肵�܂��B
   * @param followPressedButtons �A�����ĉ���������Ԃ�؂�ւ���{�^���Q
   */
  public void setFollowPressedButtons(IkenshoHintButton[] followPressedButtons) {
    this.followPressedButtons = followPressedButtons;
  }

  /**
   * �A�����ėL����Ԃ�؂�ւ���R���|�[�l���g�Q��Ԃ��܂��B
   * @return �A�����ėL����Ԃ�؂�ւ���R���|�[�l���g�Q
   */
  public JComponent[] getFollowDisabledComponents() {
    return followDisabledComponents;
  }

  /**
   * �A�����ėL����Ԃ�؂�ւ���R���|�[�l���g�Q��ݒ肵�܂��B
   * @param followDisabledComponents �A�����ėL����Ԃ�؂�ւ���R���|�[�l���g�Q
   */
  public void setFollowDisabledComponents(JComponent[] followDisabledComponents) {
    this.followDisabledComponents = followDisabledComponents;
  }
}
