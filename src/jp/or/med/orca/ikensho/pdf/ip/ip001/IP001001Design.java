
/*
 * Project code name "ORCA"
 * ���t�Ǘ��䒠�\�t�g QKANCHO�iJMA care benefit management software�j
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "QKANCHO (JMA care benefit management software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * �A�v��: QKANCHO
 * �J����: �c���@����
 * �쐬��: 2006/04/27  ���{�R���s���[�^�[������� �c���@���� �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���[ (P)
 * �v���Z�X �ȈՒ��[�J�X�^�}�C�Y�c�[�� (001)
 * �v���O���� ���[�I�� (IP001001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.*;
import java.awt.event.*;
import java.awt.im.*;
import java.io.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import jp.nichicom.ac.*;
import jp.nichicom.ac.bind.*;
import jp.nichicom.ac.component.*;
import jp.nichicom.ac.component.dnd.*;
import jp.nichicom.ac.component.dnd.event.*;
import jp.nichicom.ac.component.event.*;
import jp.nichicom.ac.component.mainmenu.*;
import jp.nichicom.ac.component.table.*;
import jp.nichicom.ac.component.table.event.*;
import jp.nichicom.ac.container.*;
import jp.nichicom.ac.core.*;
import jp.nichicom.ac.filechooser.*;
import jp.nichicom.ac.io.*;
import jp.nichicom.ac.lang.*;
import jp.nichicom.ac.pdf.*;
import jp.nichicom.ac.sql.*;
import jp.nichicom.ac.text.*;
import jp.nichicom.ac.util.*;
import jp.nichicom.ac.util.adapter.*;
import jp.nichicom.vr.*;
import jp.nichicom.vr.bind.*;
import jp.nichicom.vr.bind.event.*;
import jp.nichicom.vr.border.*;
import jp.nichicom.vr.component.*;
import jp.nichicom.vr.component.event.*;
import jp.nichicom.vr.component.table.*;
import jp.nichicom.vr.container.*;
import jp.nichicom.vr.focus.*;
import jp.nichicom.vr.image.*;
import jp.nichicom.vr.io.*;
import jp.nichicom.vr.layout.*;
import jp.nichicom.vr.text.*;
import jp.nichicom.vr.text.parsers.*;
import jp.nichicom.vr.util.*;
import jp.nichicom.vr.util.adapter.*;
import jp.nichicom.vr.util.logging.*;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
/**
 * ���[�I����ʍ��ڃf�U�C��(IP001001) 
 */
public class IP001001Design extends ACAffairDialog {
  //GUI�R���|�[�l���g

  private ACGroupBox contents;

  private ACComboBox formats;

  private ACLabelContainer formatsContainer;

  private ACComboBoxModelAdapter formatsModel;

  private ACListItem formatNewIkensho1;

  private ACListItem formatNewIkensho2;

  private ACListItem formatShijisho;

  private ACListItem formatShijishoB;

  private ACListItem formatSeikyuIkenshoIchiran;

  private ACListItem formatSeikyuIchiranTotal;

  private ACListItem formatSoukatusho;

  private ACListItem formatIkenshoMeisai;

  private ACListItem formatPatientList;

  private ACListItem formatSeikyuIchiran;

  private ACListItem formatCSVFileOutputPatientList;

  private ACPanel buttons;

  private ACButton ok;

  private ACButton cancel;

  //getter

  /**
   * ���[�̑I�����擾���܂��B
   * @return ���[�̑I��
   */
  public ACGroupBox getContents(){
    if(contents==null){

      contents = new ACGroupBox();

      contents.setText("���[�̑I��");

      addContents();
    }
    return contents;

  }

  /**
   * ���[���擾���܂��B
   * @return ���[
   */
  public ACComboBox getFormats(){
    if(formats==null){

      formats = new ACComboBox();

      getFormatsContainer().setText("���[");

      formats.setBindPath("FORMAT");

      formats.setEditable(false);

      formats.setRenderBindPath("FORMAT_TEXT");

      formats.setModel(getFormatsModel());

      addFormats();
    }
    return formats;

  }

  /**
   * ���[�R���e�i���擾���܂��B
   * @return ���[�R���e�i
   */
  protected ACLabelContainer getFormatsContainer(){
    if(formatsContainer==null){
      formatsContainer = new ACLabelContainer();
      formatsContainer.setFollowChildEnabled(true);
      formatsContainer.setVAlignment(VRLayout.CENTER);
      formatsContainer.add(getFormats(), VRLayout.CLIENT);
    }
    return formatsContainer;
  }

  /**
   * ���[���f�����擾���܂��B
   * @return ���[���f��
   */
  protected ACComboBoxModelAdapter getFormatsModel(){
    if(formatsModel==null){
      formatsModel = new ACComboBoxModelAdapter();
      addFormatsModel();
    }
    return formatsModel;
  }

  /**
   * �厡��ӌ���1�y�[�W�ڂ��擾���܂��B
   * @return �厡��ӌ���1�y�[�W��
   */
  public ACListItem getFormatNewIkensho1(){
    if(formatNewIkensho1==null){

      formatNewIkensho1 = new ACListItem();

      formatNewIkensho1.setText("�厡��ӌ���1�y�[�W��");

      formatNewIkensho1.setSimpleValueMode(false);
      formatNewIkensho1.put(getFormats().getRenderBindPath(), "�厡��ӌ���1�y�[�W��");
      formatNewIkensho1.put(getFormats().getBindPath(), new Integer(0));

      addFormatNewIkensho1();
    }
    return formatNewIkensho1;

  }

  /**
   * �厡��ӌ���2�y�[�W�ڂ��擾���܂��B
   * @return �厡��ӌ���2�y�[�W��
   */
  public ACListItem getFormatNewIkensho2(){
    if(formatNewIkensho2==null){

      formatNewIkensho2 = new ACListItem();

      formatNewIkensho2.setText("�厡��ӌ���2�y�[�W��");

      formatNewIkensho2.setSimpleValueMode(false);
      formatNewIkensho2.put(getFormats().getRenderBindPath(), "�厡��ӌ���2�y�[�W��");
      formatNewIkensho2.put(getFormats().getBindPath(), new Integer(1));

      addFormatNewIkensho2();
    }
    return formatNewIkensho2;

  }

  /**
   * �K��Ō�w�����i��Ë@�ցj���擾���܂��B
   * @return �K��Ō�w�����i��Ë@�ցj
   */
  public ACListItem getFormatShijisho(){
    if(formatShijisho==null){

      formatShijisho = new ACListItem();

      formatShijisho.setText("�K��Ō�w�����i��Ë@�ցj");

      formatShijisho.setSimpleValueMode(false);
      formatShijisho.put(getFormats().getRenderBindPath(), "�K��Ō�w�����i��Ë@�ցj");
      formatShijisho.put(getFormats().getBindPath(), new Integer(2));

      addFormatShijisho();
    }
    return formatShijisho;

  }

  /**
   * �K��Ō�w�����i���V�l�ی��{�݁j���擾���܂��B
   * @return �K��Ō�w�����i���V�l�ی��{�݁j
   */
  public ACListItem getFormatShijishoB(){
    if(formatShijishoB==null){

      formatShijishoB = new ACListItem();

      formatShijishoB.setText("�K��Ō�w�����i���V�l�ی��{�݁j");

      formatShijishoB.setSimpleValueMode(false);
      formatShijishoB.put(getFormats().getRenderBindPath(), "�K��Ō�w�����i���V�l�ی��{�݁j");
      formatShijishoB.put(getFormats().getBindPath(), new Integer(3));

      addFormatShijishoB();
    }
    return formatShijishoB;

  }

  /**
   * �������ꗗ���擾���܂��B
   * @return �������ꗗ
   */
  public ACListItem getFormatSeikyuIkenshoIchiran(){
    if(formatSeikyuIkenshoIchiran==null){

      formatSeikyuIkenshoIchiran = new ACListItem();

      formatSeikyuIkenshoIchiran.setText("�������ꗗ");

      formatSeikyuIkenshoIchiran.setSimpleValueMode(false);
      formatSeikyuIkenshoIchiran.put(getFormats().getRenderBindPath(), "�������ꗗ");
      formatSeikyuIkenshoIchiran.put(getFormats().getBindPath(), new Integer(4));

      addFormatSeikyuIkenshoIchiran();
    }
    return formatSeikyuIkenshoIchiran;

  }

  /**
   * ���������v���擾���܂��B
   * @return ���������v
   */
  public ACListItem getFormatSeikyuIchiranTotal(){
    if(formatSeikyuIchiranTotal==null){

      formatSeikyuIchiranTotal = new ACListItem();

      formatSeikyuIchiranTotal.setText("���������v");

      formatSeikyuIchiranTotal.setSimpleValueMode(false);
      formatSeikyuIchiranTotal.put(getFormats().getRenderBindPath(), "���������v");
      formatSeikyuIchiranTotal.put(getFormats().getBindPath(), new Integer(5));

      addFormatSeikyuIchiranTotal();
    }
    return formatSeikyuIchiranTotal;

  }

  /**
   * �厡��ӌ����쐬���E����������(����)�����擾���܂��B
   * @return �厡��ӌ����쐬���E����������(����)��
   */
  public ACListItem getFormatSoukatusho(){
    if(formatSoukatusho==null){

      formatSoukatusho = new ACListItem();

      formatSoukatusho.setText("�厡��ӌ����쐬���E����������(����)��");

      formatSoukatusho.setSimpleValueMode(false);
      formatSoukatusho.put(getFormats().getRenderBindPath(), "�厡��ӌ����쐬���E����������(����)��");
      formatSoukatusho.put(getFormats().getBindPath(), new Integer(6));

      addFormatSoukatusho();
    }
    return formatSoukatusho;

  }

  /**
   * �厡��ӌ����쐬������(����)�����擾���܂��B
   * @return �厡��ӌ����쐬������(����)��
   */
  public ACListItem getFormatIkenshoMeisai(){
    if(formatIkenshoMeisai==null){

      formatIkenshoMeisai = new ACListItem();

      formatIkenshoMeisai.setText("�厡��ӌ����쐬������(����)��");

      formatIkenshoMeisai.setSimpleValueMode(false);
      formatIkenshoMeisai.put(getFormats().getRenderBindPath(), "�厡��ӌ����쐬������(����)��");
      formatIkenshoMeisai.put(getFormats().getBindPath(), new Integer(7));

      addFormatIkenshoMeisai();
    }
    return formatIkenshoMeisai;

  }

  /**
   * �o�^���҈ꗗ���擾���܂��B
   * @return �o�^���҈ꗗ
   */
  public ACListItem getFormatPatientList(){
    if(formatPatientList==null){

      formatPatientList = new ACListItem();

      formatPatientList.setText("�o�^���҈ꗗ");

      formatPatientList.setSimpleValueMode(false);
      formatPatientList.put(getFormats().getRenderBindPath(), "�o�^���҈ꗗ");
      formatPatientList.put(getFormats().getBindPath(), new Integer(8));

      addFormatPatientList();
    }
    return formatPatientList;

  }

  /**
   * �����Ώۈӌ����ꗗ���擾���܂��B
   * @return �����Ώۈӌ����ꗗ
   */
  public ACListItem getFormatSeikyuIchiran(){
    if(formatSeikyuIchiran==null){

      formatSeikyuIchiran = new ACListItem();

      formatSeikyuIchiran.setText("�����Ώۈӌ����ꗗ");

      formatSeikyuIchiran.setSimpleValueMode(false);
      formatSeikyuIchiran.put(getFormats().getRenderBindPath(), "�����Ώۈӌ����ꗗ");
      formatSeikyuIchiran.put(getFormats().getBindPath(), new Integer(9));

      addFormatSeikyuIchiran();
    }
    return formatSeikyuIchiran;

  }

  /**
   * �b�r�u�t�@�C����o���҈ꗗ���擾���܂��B
   * @return �b�r�u�t�@�C����o���҈ꗗ
   */
  public ACListItem getFormatCSVFileOutputPatientList(){
    if(formatCSVFileOutputPatientList==null){

      formatCSVFileOutputPatientList = new ACListItem();

      formatCSVFileOutputPatientList.setText("�b�r�u�t�@�C����o���҈ꗗ");

      formatCSVFileOutputPatientList.setSimpleValueMode(false);
      formatCSVFileOutputPatientList.put(getFormats().getRenderBindPath(), "�b�r�u�t�@�C����o���҈ꗗ");
      formatCSVFileOutputPatientList.put(getFormats().getBindPath(), new Integer(10));

      addFormatCSVFileOutputPatientList();
    }
    return formatCSVFileOutputPatientList;

  }

  /**
   * �{�^���̈���擾���܂��B
   * @return �{�^���̈�
   */
  public ACPanel getButtons(){
    if(buttons==null){

      buttons = new ACPanel();

      addButtons();
    }
    return buttons;

  }

  /**
   * OK���擾���܂��B
   * @return OK
   */
  public ACButton getOk(){
    if(ok==null){

      ok = new ACButton();

      ok.setText("OK");

      ok.setToolTipText("�I���������[���J���܂��B");

      ok.setMnemonic('O');

      ok.setIconPath(ACConstants.ICON_PATH_YES_24);

      addOk();
    }
    return ok;

  }

  /**
   * �L�����Z�����擾���܂��B
   * @return �L�����Z��
   */
  public ACButton getCancel(){
    if(cancel==null){

      cancel = new ACButton();

      cancel.setText("�L�����Z��(C)");

      cancel.setToolTipText("����������߂܂��B");

      cancel.setMnemonic('C');

      cancel.setIconPath(ACConstants.ICON_PATH_CANCEL_24);

      addCancel();
    }
    return cancel;

  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IP001001Design() {

    super(ACFrame.getInstance(), true);
    this.getContentPane().setLayout(new VRLayout());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    try {
      initialize();

      pack();

      // �E�B���h�E�𒆉��ɔz�u
      Point pos;
      try{
          pos= ACFrame.getInstance().getLocationOnScreen();
      }catch(Exception ex){
          pos = new Point(0,0);
      }
      Dimension screenSize = ACFrame.getInstance().getSize();
      Dimension frameSize = this.getSize();
      if (frameSize.height > screenSize.height) {
          frameSize.height = screenSize.height;
      }
      if (frameSize.width > screenSize.width) {
          frameSize.width = screenSize.width;
      }
      this.setLocation((int)(pos.getX()+(screenSize.width - frameSize.width) / 2),
              (int)(pos.getY()+(screenSize.height - frameSize.height) / 2));

    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * ���g�̐ݒ���s�Ȃ��܂��B
   */
  protected void initThis(){
  }

  /**
   * this�ɓ������ڂ�ǉ����܂��B
   */
  protected void addThis(){

    this.getContentPane().add(getContents(), VRLayout.CLIENT);

    this.getContentPane().add(getButtons(), VRLayout.SOUTH);
  }

  /**
   * ���[�̑I���ɓ������ڂ�ǉ����܂��B
   */
  protected void addContents(){

    contents.add(getFormatsContainer(), VRLayout.EAST);
  }

  /**
   * ���[�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormats(){

  }

  /**
   * ���[���f���ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatsModel(){

    getFormatsModel().add(getFormatNewIkensho1());

    getFormatsModel().add(getFormatNewIkensho2());

    getFormatsModel().add(getFormatShijisho());

    getFormatsModel().add(getFormatShijishoB());

    getFormatsModel().add(getFormatSeikyuIkenshoIchiran());

    getFormatsModel().add(getFormatSeikyuIchiranTotal());

    getFormatsModel().add(getFormatSoukatusho());

    getFormatsModel().add(getFormatIkenshoMeisai());

    getFormatsModel().add(getFormatPatientList());

    getFormatsModel().add(getFormatSeikyuIchiran());

    getFormatsModel().add(getFormatCSVFileOutputPatientList());

  }

  /**
   * �厡��ӌ���1�y�[�W�ڂɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatNewIkensho1(){

  }

  /**
   * �厡��ӌ���2�y�[�W�ڂɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatNewIkensho2(){

  }

  /**
   * �K��Ō�w�����i��Ë@�ցj�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatShijisho(){

  }

  /**
   * �K��Ō�w�����i���V�l�ی��{�݁j�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatShijishoB(){

  }

  /**
   * �������ꗗ�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatSeikyuIkenshoIchiran(){

  }

  /**
   * ���������v�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatSeikyuIchiranTotal(){

  }

  /**
   * �厡��ӌ����쐬���E����������(����)���ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatSoukatusho(){

  }

  /**
   * �厡��ӌ����쐬������(����)���ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatIkenshoMeisai(){

  }

  /**
   * �o�^���҈ꗗ�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatPatientList(){

  }

  /**
   * �����Ώۈӌ����ꗗ�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatSeikyuIchiran(){

  }

  /**
   * �b�r�u�t�@�C����o���҈ꗗ�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatCSVFileOutputPatientList(){

  }

  /**
   * �{�^���̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addButtons(){

    buttons.add(getCancel(), VRLayout.EAST);
    buttons.add(getOk(), VRLayout.EAST);
  }

  /**
   * OK�ɓ������ڂ�ǉ����܂��B
   */
  protected void addOk(){

  }

  /**
   * �L�����Z���ɓ������ڂ�ǉ����܂��B
   */
  protected void addCancel(){

  }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ��������O
   */
  private void initialize() throws Exception {
    initThis();
    addThis();
  }
  public boolean canBack(VRMap parameters) throws Exception {
    return true;
  }
  public Component getFirstFocusComponent() {

    return null;

  }
  public void initAffair(ACAffairInfo affair) throws Exception {
  }

  public void setVisible(boolean visible){
    if(visible){
      try{
        initAffair(null);
      }catch(Throwable ex){
        ACCommon.getInstance().showExceptionMessage(ex);
      }
    }
    super.setVisible(visible);
  }
  public static void main(String[] args) {
    //�f�t�H���g�f�o�b�O�N��
    try {
      ACFrame.setVRLookAndFeel();
      ACFrame.getInstance().setFrameEventProcesser(new IkenshoFrameEventProcesser());
      new IP001001Design().setVisible(true);
      System.exit(0);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * ���g��Ԃ��܂��B
   */
  protected IP001001Design getThis() {
    return this;
  }
}
