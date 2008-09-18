package jp.or.med.orca.ikensho.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import jp.nichicom.ac.component.ACToggleButton;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSpecialSickButton
    extends ACToggleButton {
  protected JComboBox combo;
  protected ComboBoxModel unpressedModel;
  protected ComboBoxModel pressedModel;
  protected Object pressedValue;
  protected Object unPressedValue;

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoSpecialSickButton() {

    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        refreshCombo();
      }
    });
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * �A������R���{�ݒ蓙���Đݒ肵�܂��B
   */
  public void refreshCombo(){
    if (getCombo() != null) {
      if (!isPushed()) {
        if (getPressedModel() != null) {
          unPressedValue = getCombo().getEditor().getItem();
          // 2007/10/10 [Masahiko Higuchi] Addition - begin
          // ������
          if(getCombo() instanceof IkenshoOptionComboBox){
            ((IkenshoOptionComboBox)getCombo()).setOptionMode(false);
            setUnpressedModel(((IkenshoOptionComboBox)getCombo()).getOriginalModel());
          }
          // 2007/10/10 [Masahiko Higuchi] Addition - end
          getCombo().setModel(getPressedModel());
          getCombo().setEditable(false);
          getCombo().setSelectedItem(pressedValue);
        }
      }
      else {
        if (getUnpressedModel() != null) {
          pressedValue = getCombo().getEditor().getItem();
          // 2007/10/10 [Masahiko Higuchi] Replace - begin
          // �񉟉���
          if(getCombo() instanceof IkenshoOptionComboBox){
              try {
                ((IkenshoOptionComboBox)getCombo()).setOptionMode(true);
                // �������˔񉟉����ύX���̃R���{�̍����ւ����ɁA��^�����ҏW����Ă���ꍇ�ɔ����Đݒ�
                setUnpressedModel(((IkenshoOptionComboBox)getCombo()).getTeikeibunComboBoxModel());
              } catch (Exception e) {}
          }
          // 2007/10/10 [Masahiko Higuchi] Replace - end
          getCombo().setModel(getUnpressedModel());
          getCombo().setEditable(true);
          getCombo().setSelectedItem(unPressedValue);
        }
      }
    }
  }

  /**
   * �A������R���{��Ԃ��܂��B
   * @return �R���{
   */
  public JComboBox getCombo() {
    return combo;
  }

  /**
   * �������̃��f����Ԃ��܂��B
   * @return �������̃��f��
   */
  public ComboBoxModel getPressedModel() {
    return pressedModel;
  }

  /**
   * �񉟉����̃��f����Ԃ��܂��B
   * @return �񉟉����̃��f��
   */
  public ComboBoxModel getUnpressedModel() {
    return unpressedModel;
  }

  /**
   * �񉟉����̃��f����ݒ肵�܂��B
   * @param unpressedModel �񉟉����̃��f��
   */
  public void setUnpressedModel(ComboBoxModel unpressedModel) {
    this.unpressedModel = unpressedModel;
  }

  /**
   * �������̃��f����ݒ肵�܂��B
   * @param pressedModel �������̃��f��
   */
  public void setPressedModel(ComboBoxModel pressedModel) {
    this.pressedModel = pressedModel;
  }

  /**
   * �A������R���{��ݒ肵�܂��B
   * @param combo �R���{
   */
  public void setCombo(JComboBox combo) {
    this.combo = combo;
  }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ��������O
   */
  private void jbInit() throws Exception {
    this.setTextPushed("���莾�a");
    this.setTextUnPushed("���莾�a");
  }

}
