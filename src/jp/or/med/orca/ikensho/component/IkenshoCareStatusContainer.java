package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.im.InputSubset;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoCareStatusContainer
    extends VRLabelContainer {
//  2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
    private IkenshoOptionComboBox value = new IkenshoOptionComboBox();
//  2007/10/18 [Masahiko Higuchi] Replace - end
  private ACIntegerCheckBox check = new ACIntegerCheckBox();
  private VRLabelContainer values = new VRLabelContainer();
  private ACParentHesesPanelContainer heses = new ACParentHesesPanelContainer();
  /**
   * �`�F�b�N�{�b�N�X��Ԃ��܂��B
   * @return �`�F�b�N�{�b�N�X
   */
  public ACIntegerCheckBox getCheckBox(){
    return check;
  }

//2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
  /**
   * �R���{�{�b�N�X��Ԃ��܂��B
   * @return �R���{�{�b�N�X
   * @since 3.0.5
   */
  public IkenshoOptionComboBox getComboBox(){
    return value;
  }
//2007/10/18 [Masahiko Higuchi] Replace - end

  /**
   * �`�F�b�N�p�̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �o�C���h�p�X
   */
  public void setCheckBindPath(String bindPath) {
    check.setBindPath(bindPath);
  }

  /**
   * �`�F�b�N�p�̃o�C���h�p�X��Ԃ��܂��B
   * @return �o�C���h�p�X
   */
  public String getCheckBindPath() {
    return check.getBindPath();
  }

  /**
   * �l�p�̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �o�C���h�p�X
   */
  public void setValueBindPath(String bindPath) {
    value.setBindPath(bindPath);
  }

  /**
   * �l�p�̃o�C���h�p�X��Ԃ��܂��B
   * @return �o�C���h�p�X
   */
  public String getValueBindPath() {
    return value.getBindPath();
  }

  /**
   * �l�p�̃��f����ݒ肵�܂��B
   * @param model ���f��
   */
  public void setValueModel(ComboBoxModel model) {
    value.setModel(model);
  }

  /**
   * �l�p�̃��f����Ԃ��܂��B
   * @return ���f��
   */
  public ComboBoxModel getValueModel() {
    return value.getModel();
  }
  /**
   * �l�̃R���{��Ԃ��܂��B
   * @return �R���{
   */
  public JComboBox getValueCombo(){
    return value;
  }

  /**
   * �`�F�b�N���Ă��邩��ݒ肵�܂��B
   * @param checked �`�F�b�N���Ă��邩
   */
  public void setChecked(boolean checked) {
    check.setSelected(checked);
  }

  /**
   * �`�F�b�N���Ă��邩��Ԃ��܂��B
   * @return �`�F�b�N���Ă��邩
   */
  public boolean isChecked() {
    return check.isSelected();
  }

  /**
   * �`�F�b�N�̃e�L�X�g��ݒ肵�܂��B
   * @param text �e�L�X�g
   */
  public void setCheckText(String text) {
    check.setText(text);
  }

  /**
   * �`�F�b�N�̃e�L�X�g��Ԃ��܂��B
   * @return �e�L�X�g
   */
  public String getCheckText() {
    return check.getText();
  }

  /**
   * ���͍ς݂̕�������Ԃ��܂��B
   * @return ���͍ς݂̕�����
   */
  public int getInputedLength(){
    return String.valueOf(getValueCombo().getEditor().getItem()).length();
  }


  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoCareStatusContainer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    check.addItemListener(new ACFollowDisabledItemListener(new JComponent[]{value, heses}));
  }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ������O
   */
  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    this.setContentAreaFilled(true);
    this.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    check.setOpaque(false);
    check.setPreferredSize(new Dimension(311, 14));
    value.setEnabled(false);
    value.setIMEMode(InputSubset.KANJI);
    value.setMaxLength(30);
    heses.setEnabled(false);
    heses.setOpaque(false);
    values.setLayout(new BorderLayout());
    values.add(value, BorderLayout.CENTER);
    heses.add(values, VRLayout.CLIENT);
    this.add(check, VRLayout.WEST);
    this.add(heses, VRLayout.CLIENT);
  }

}
