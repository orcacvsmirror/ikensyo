
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
 * �J����: ����@��F
 * �쐬��: 2009/07/13  ���{�R���s���[�^�[������� ����@��F �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� ���t�Ǘ��䒠 (Q)
 * �T�u�V�X�e�� �\��Ǘ� (O)
 * �v���Z�X �T�[�r�X�\�� (002)
 * �v���O���� �ی��ґI�� (IkenshoInsurerSelect)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.affair;
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
/**
 *****************************************************************
 * �A�v��: Ikensho
 * �J����: ����@��F
 * �쐬��: 2009  ���{�R���s���[�^�[������� ��� ��F �V�K�쐬
 * @since V3.0.9
 *
 *****************************************************************
 */
public class IkenshoInsurerSelectDesign extends IkenshoDialog {
  //GUI�R���|�[�l���g

  private ACPanel contents;

  private ACPanel contentsFind;

  private ACTextField insurerText;

  private ACLabelContainer insurerTextContainer;

  private ACGroupBox insurerGroup;

  private ACTable detailsTable;

  private VRTableColumnModel detailsTableColumnModel;

  private ACTableColumn detailsTableColumn2;

  private ACTableColumn detailsTableColumn3;

  private ACPanel buttons;

  private ACButton apply;

  private ACButton close;

  //getter

  /**
   * �N���C�A���g�̈���擾���܂��B
   * @return �N���C�A���g�̈�
   */
  public ACPanel getContents(){
    if(contents==null){

      contents = new ACPanel();

      contents.setAutoWrap(false);

      contents.setHgap(0);

      contents.setLabelMargin(0);

      contents.setVgap(0);

      addContents();
    }
    return contents;

  }

  /**
   * �����̈���擾���܂��B
   * @return �����̈�
   */
  public ACPanel getContentsFind(){
    if(contentsFind==null){

      contentsFind = new ACPanel();

      contentsFind.setAutoWrap(false);

      contentsFind.setHgap(2);

      addContentsFind();
    }
    return contentsFind;

  }

  /**
   * �ی��Җ����擾���܂��B
   * @return �ی��Җ�
   */
  public ACTextField getInsurerText(){
    if(insurerText==null){

      insurerText = new ACTextField();

      getInsurerTextContainer().setText("�ی��Җ���");

      insurerText.setBindPath("FIND_INSURER_NAME");

      insurerText.setColumns(26);

      insurerText.setIMEMode(InputSubset.KANJI);

      insurerText.setMaxLength(25);

      addInsurerText();
    }
    return insurerText;

  }

  /**
   * �ی��Җ��R���e�i���擾���܂��B
   * @return �ی��Җ��R���e�i
   */
  protected ACLabelContainer getInsurerTextContainer(){
    if(insurerTextContainer==null){
      insurerTextContainer = new ACLabelContainer();
      insurerTextContainer.setFollowChildEnabled(true);
      insurerTextContainer.setVAlignment(VRLayout.CENTER);
      insurerTextContainer.add(getInsurerText(), null);
    }
    return insurerTextContainer;
  }

  /**
   * �ی��҃e�[�u���̈���擾���܂��B
   * @return �ی��҃e�[�u���̈�
   */
  public ACGroupBox getInsurerGroup(){
    if(insurerGroup==null){

      insurerGroup = new ACGroupBox();

      addInsurerGroup();
    }
    return insurerGroup;

  }

  /**
   * �ی��҃e�[�u�����擾���܂��B
   * @return �ی��҃e�[�u��
   */
  public ACTable getDetailsTable(){
    if(detailsTable==null){

      detailsTable = new ACTable();

      detailsTable.setColumnModel(getDetailsTableColumnModel());

      detailsTable.setMinimumSize(new Dimension(500, 200));

      addDetailsTable();
    }
    return detailsTable;

  }

  /**
   * �ی��҃e�[�u���J�������f�����擾���܂��B
   * @return �ی��҃e�[�u���J�������f��
   */
  protected VRTableColumnModel getDetailsTableColumnModel(){
    if(detailsTableColumnModel==null){
      detailsTableColumnModel = new VRTableColumnModel(new TableColumn[]{});
      addDetailsTableColumnModel();
    }
    return detailsTableColumnModel;
  }

  /**
   * �ی��Ҕԍ����擾���܂��B
   * @return �ی��Ҕԍ�
   */
  public ACTableColumn getDetailsTableColumn2(){
    if(detailsTableColumn2==null){

      detailsTableColumn2 = new ACTableColumn();

      detailsTableColumn2.setHeaderValue("�ی��Ҕԍ�");

      detailsTableColumn2.setColumnName("INSURER_NO");

      detailsTableColumn2.setColumns(6);

      addDetailsTableColumn2();
    }
    return detailsTableColumn2;

  }

  /**
   * �ی��Җ��̂��擾���܂��B
   * @return �ی��Җ���
   */
  public ACTableColumn getDetailsTableColumn3(){
    if(detailsTableColumn3==null){

      detailsTableColumn3 = new ACTableColumn();

      detailsTableColumn3.setHeaderValue("�ی��Җ���");

      detailsTableColumn3.setColumnName("INSURER_NAME");

      detailsTableColumn3.setColumns(30);

      addDetailsTableColumn3();
    }
    return detailsTableColumn3;

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
   * ���f�{�^�����擾���܂��B
   * @return ���f�{�^��
   */
  public ACButton getApply(){
    if(apply==null){

      apply = new ACButton();

      apply.setText("�ݒ�(S)");

      apply.setMnemonic('S');

      addApply();
    }
    return apply;

  }

  /**
   * ����{�^�����擾���܂��B
   * @return ����{�^��
   */
  public ACButton getClose(){
    if(close==null){

      close = new ACButton();

      close.setText("����(C)");

      close.setMnemonic('C');

      addClose();
    }
    return close;

  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoInsurerSelectDesign() {

    super(ACFrame.getInstance(), true);
    this.getContentPane().setLayout(new VRLayout());
    setDefaultCloseOperation(DISPOSE_ON_CLOSE);

    try {
      initialize();

      setSize(500, 250);

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

  }

  /**
   * �N���C�A���g�̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addContents(){

    contents.add(getContentsFind(), VRLayout.NORTH);

    contents.add(getInsurerGroup(), VRLayout.CLIENT);

    contents.add(getButtons(), VRLayout.SOUTH);
  }

  /**
   * �����̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addContentsFind(){

    contentsFind.add(getInsurerTextContainer(), VRLayout.FLOW_RETURN);

  }

  /**
   * �ی��Җ��ɓ������ڂ�ǉ����܂��B
   */
  protected void addInsurerText(){

  }

  /**
   * �ی��҃e�[�u���̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addInsurerGroup(){

    insurerGroup.add(getDetailsTable(), VRLayout.CLIENT);

  }

  /**
   * �ی��҃e�[�u���ɓ������ڂ�ǉ����܂��B
   */
  protected void addDetailsTable(){

  }

  /**
   * �ی��҃e�[�u���J�������f���ɓ������ڂ�ǉ����܂��B
   */
  protected void addDetailsTableColumnModel(){

    getDetailsTableColumnModel().addColumn(getDetailsTableColumn2());

    getDetailsTableColumnModel().addColumn(getDetailsTableColumn3());

  }

  /**
   * �ی��Ҕԍ��ɓ������ڂ�ǉ����܂��B
   */
  protected void addDetailsTableColumn2(){

  }

  /**
   * �ی��Җ��̂ɓ������ڂ�ǉ����܂��B
   */
  protected void addDetailsTableColumn3(){

  }

  /**
   * �{�^���̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addButtons(){

    buttons.add(getClose(), VRLayout.EAST);
    buttons.add(getApply(), VRLayout.EAST);
  }

  /**
   * ���f�{�^���ɓ������ڂ�ǉ����܂��B
   */
  protected void addApply(){

  }

  /**
   * ����{�^���ɓ������ڂ�ǉ����܂��B
   */
  protected void addClose(){

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

  /**
   * ���g��Ԃ��܂��B
   */
  protected IkenshoInsurerSelectDesign getThis() {
    return this;
  }
}
