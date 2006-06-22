package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMindBody1H18 extends IkenshoIkenshoInfoMindBody1 {
  private VRListModelAdapter unExistEmptyListModel = new VRListModelAdapter(new
      VRArrayList(Arrays.asList(new String[] {"��", "�L"})));

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoIkenshoInfoMindBody1H18() {
    try {
     jbInit();
   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }
 private void jbInit() throws Exception {
   getSyougaiRoujinJiritsu().setText("��Q����҂̓��퐶�������x" +
                                     IkenshoConstants.LINE_SEPARATOR +
                                     "�i�Q������x�j");
   getChihouRoujinJiritsu().setText("�F�m�Ǎ���҂̓��퐶�������x");
   getRikaiKiokuGroup().setText("�F�m�ǂ̒��j�Ǐ�i�F�m�ǈȊO�̎����œ��l�̏Ǐ��F�߂�ꍇ���܂ށj");
   getMondaiGroup().setText("�F�m�ǂ̎��ӏǏ�i�F�m�ǈȊO�̎����œ��l�̏Ǐ��F�߂�ꍇ���܂ށj");
   getShinkeiGroup().setText("���̑��̐��_�E�_�o�Ǐ�");
   getMindBody1SyougaiJiritsu().setModel(new VRListModelAdapter(new
           VRArrayList(Arrays.asList(new
                                     String[] {"����", "�i�P", "�i�Q", "�`�P", "�`�Q", "�a�P",
                                     "�a�Q", "�b�P", "�b�Q"}))));
   getMindBody1ChihouJiritsu().setModel(new VRListModelAdapter(new
           VRArrayList(Arrays.asList(new
                                     String[] {"����", "I", "II��", "II��", "III��", "III��",
                                     "IV", "�l"}))));

   getRikaiKiokuClearButton().setToolTipText("�u�F�m�ǂ̒��j�Ǐ�v�̑S���ڂ̑I�����������܂��B");

   getMindBody1HasMondai().setModel(unExistEmptyListModel);
   getMindBody1HasMondai().setValues(new int[]{2,1});
   getMindBody1HasShinkei().setModel(unExistEmptyListModel);
   getMindBody1HasShinkei().setValues(new int[]{2,1});

   getRikaiKiokuSyokuji().setVisible(false);
 }
 protected void addRikaiKiokuClearButton(){
   getRikaiKiokuDentatsuHelpPanel().add(getRikaiKiokuClearButton(), null);
 }

 protected String getProblemActionCaption() {
   return "�F�m�ǂ̎��ӏǏ�";
 }

 protected int getFomratKubun(){
   return 1;
 }

 protected void addMindBody1HasShinkei(){
   getMindBody1HasShinkei().add(getMindBody1Shinkei(), null);
 }


}
