package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.ListModel;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoBodyStatusContainer
    extends VRLabelContainer {
  private ACClearableRadioButtonGroup rank = new
      ACClearableRadioButtonGroup();
  private ACIntegerCheckBox check = new
      ACIntegerCheckBox();
  // 2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�Ή�
    // ACComboBox��IkenshoOptionComboBox
    private IkenshoOptionComboBox pos = new IkenshoOptionComboBox();
    // 2007/10/18 [Masahiko Higuchi] end
  private VRLabelContainer poss = new VRLabelContainer();
  private VRLabelContainer ranks = new VRLabelContainer();
  private ACParentHesesPanelContainer heses = new ACParentHesesPanelContainer();

  /**
   * ���ʃR���e�i�̕\���ۂ�Ԃ��܂��B
   * @param posVisible ���ʃR���e�i�̕\����
   */
 public void setPosVisible(boolean posVisible){
   poss.setVisible(posVisible);
 }
 /**
  * ���ʃR���e�i�̕\���ۂ�Ԃ��܂��B
  * @return ���ʃR���e�i�̕\����
  */
  public boolean isPosVisible(){
    return poss.isVisible();
  }

  /**
   * ���x�R���e�i�̕\���ۂ�Ԃ��܂��B
   * @param rankVisible ���x�R���e�i�̕\����
   */
 public void setRankVisible(boolean rankVisible){
   ranks.setVisible(rankVisible);
 }

  /**
   * ���x�R���e�i�̕\���ۂ�Ԃ��܂��B
   * @return ���x�R���e�i�̕\����
   */
  public boolean getRankVisible(){
    return ranks.isVisible();
  }


  /**
   * ���W�I�O���[�v��Ԃ��܂��B
   * @return ���W�I�O���[�v
   */
  public ACClearableRadioButtonGroup getRadioGroup() {
    return rank;
  }

  /**
   * �`�F�b�N�{�b�N�X��Ԃ��܂��B
   * @return �`�F�b�N�{�b�N�X
   */
  public ACIntegerCheckBox getCheckBox() {
    return check;
  }

  /**
   * �R���{�{�b�N�X��Ԃ��܂��B
   * @return �R���{�{�b�N�X
   * @since 3.0.5
   */
  public IkenshoOptionComboBox getComboBox() {
    return pos;
  }

  /**
   *
   * ���x�̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �o�C���h�p�X
   */
  public void setRankBindPath(String bindPath) {
    rank.setBindPath(bindPath);
  }

  /**
   * ���x�̃o�C���h�p�X��Ԃ��܂��B
   * @return �o�C���h�p�X
   */
  public String getRankBindPath() {
    return rank.getBindPath();
  }

  /**
   * ���ʂ̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �o�C���h�p�X
   */
  public void setPosBindPath(String bindPath) {
    pos.setBindPath(bindPath);
  }

  /**
   * ���ʂ̃o�C���h�p�X��Ԃ��܂��B
   * @return �o�C���h�p�X
   */
  public String getPosBindPath() {
    return pos.getBindPath();
  }

  /**
   * �`�F�b�N�̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �o�C���h�p�X
   */
  public void setCheckBindPath(String bindPath) {
    check.setBindPath(bindPath);
  }

  /**
   * �`�F�b�N�̃o�C���h�p�X��Ԃ��܂��B
   * @return �o�C���h�p�X
   */
  public String getCheckBindPath() {
    return check.getBindPath();
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
   * ���ʂ̃R���{��Ԃ��܂��B
   * @return �R���{
   */
  public JComboBox getPosCombo() {
    return pos;
  }

  /**
   * ���ʂ̃��f����ݒ肵�܂��B
   * @param model ���f��
   */
  public void setPosModel(ComboBoxModel model) {
    pos.setModel(model);
  }

  /**
   * ���ʂ̃��f����Ԃ��܂��B
   * @return ���f��
   */
  public ComboBoxModel getPosModel() {
    return pos.getModel();
  }

  /**
   * ���x�̃��f����ݒ肵�܂��B
   * @param model ���f��
   */
  public void setRankModel(ListModel model) {
    rank.setModel(model);
  }

  /**
   * ���x�̃��f����Ԃ��܂��B
   * @return ���f��
   */
  public ListModel getRankModel() {
    return rank.getModel();
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
   * �e�̗L����ԕω��ɒǐ����܂��B
   * @param enabled �e�̗L�����
   */
  public void followParentEnabled(boolean enabled){
    boolean childEnabled;
    if(enabled){
      //�e�͗L�� �� �I����ԂɈˑ�
      check.setEnabled(true);
      childEnabled = check.isSelected();
    }else{
      //�e�͖��� �� �ꗥ����
      check.setEnabled(false);
      childEnabled = false;
    }

    heses.setEnabled(childEnabled);
    poss.setEnabled(childEnabled);
    pos.setEnabled(childEnabled);
    ranks.setEnabled(childEnabled);
    rank.setEnabled(childEnabled);
    rank.getClearButton().setEnabled(childEnabled);
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoBodyStatusContainer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    check.addItemListener(new ACFollowDisabledItemListener(new JComponent[] {
        rank.getClearButton(), rank, pos, poss, ranks, heses}));

  }

  private void jbInit() throws Exception {
    rank.setModel(new VRListModelAdapter(new
                                         VRArrayList(Arrays.asList(new String[] {
        "�y", "��", "�d"}))));
    rank.setEnabled(false);
    check.setOpaque(false);
//    check.setPreferredSize(new Dimension(150, 23));
    check.setPreferredSize(new Dimension(210, 23));
    pos.setMaxLength(10);
    pos.setEnabled(false);
    pos.setIMEMode(InputSubset.KANJI);
    poss.setEnabled(false);
    poss.setText("���ʁF");
    poss.setColumns(15);
    ranks.setEnabled(false);
    ranks.setText("���x�F");
    ranks.setColumns(20);
    rank.getClearButton().setEnabled(false);
    this.setLayout(new BorderLayout());
    this.setContentAreaFilled(true);
    this.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    heses.setEnabled(false);
    heses.setOpaque(false);
    ( (VRLayout) heses.getLayout()).setAutoWrap(false);
    this.add(heses, BorderLayout.CENTER);
    heses.add(poss, null);
    poss.add(pos, null);
    heses.add(ranks, null);
    ranks.add(rank, null);
    this.add(check, BorderLayout.WEST);
    
  }

}
