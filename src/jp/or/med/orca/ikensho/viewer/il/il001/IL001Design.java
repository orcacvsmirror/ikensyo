
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
 * �J����: �����@�L
 * �쐬��: 2012/09/20  ���{�R���s���[�^�[������� �����@�L �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���O (L)
 * �v���Z�X ���O�r���[�A (001)
 * �v���O���� ���O�r���[�A (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;
import java.awt.Component;

import javax.swing.table.TableColumn;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.affair.IkenshoAffairContainer;
/**
 * ���O�r���[�A��ʍ��ڃf�U�C��(IL001) 
 */
public class IL001Design extends IkenshoAffairContainer implements ACAffairable {
	private static final long serialVersionUID = 1L;
  //GUI�R���|�[�l���g

  private ACAffairButtonBar buttons;

  private ACAffairButton prevButton;

  private ACAffairButton nextButton;

  private ACPanel contents;

  private ACGroupBox fileInfoGroup;

  private ACLabel fileCountLabel;

  private ACLabel fileCount;

  private ACLabel viewFileLavel;

  private ACLabel viewFile;

  private ACTable logDataTable;

  private VRTableColumnModel logDataTableColumnModel;

  private ACTableColumn logDataTableColumn1;

  private ACTableColumn logDataTableColumn2;

  private ACTableColumn logDataTableColumn3;

  private ACTableColumn logDataTableColumn4;

  private ACTableColumn logDataTableColumn5;

  private ACTableColumn logDataTableColumn6;

  //getter

  /**
   * �Ɩ��{�^���o�[���擾���܂��B
   * @return �Ɩ��{�^���o�[
   */
  public ACAffairButtonBar getButtons(){
    if(buttons==null){

      buttons = new ACAffairButtonBar();

      buttons.setText("�㌩�� ���O�t�@�C���r���[�A");

      buttons.setBackVisible(false);

      addButtons();
    }
    return buttons;

  }

  /**
   * �߂�{�^�����擾���܂��B
   * @return �߂�{�^��
   */
  public ACAffairButton getPrevButton(){
    if(prevButton==null){

      prevButton = new ACAffairButton();

      prevButton.setText("�߂�(P)");

      prevButton.setMnemonic('P');

      prevButton.setIconPath(ACConstants.ICON_PATH_LEFT_24);

      addPrevButton();
    }
    return prevButton;

  }

  /**
   * ���փ{�^�����擾���܂��B
   * @return ���փ{�^��
   */
  public ACAffairButton getNextButton(){
    if(nextButton==null){

      nextButton = new ACAffairButton();

      nextButton.setText("����(N)");

      nextButton.setMnemonic('N');

      nextButton.setIconPath(ACConstants.ICON_PATH_RIGHT_24);

      addNextButton();
    }
    return nextButton;

  }

  /**
   * �N���C�A���g�̈���擾���܂��B
   * @return �N���C�A���g�̈�
   */
  public ACPanel getContents(){
    if(contents==null){

      contents = new ACPanel();

      addContents();
    }
    return contents;

  }

  /**
   * ���O�t�@�C�����O���[�v���擾���܂��B
   * @return ���O�t�@�C�����O���[�v
   */
  public ACGroupBox getFileInfoGroup(){
    if(fileInfoGroup==null){

      fileInfoGroup = new ACGroupBox();

      fileInfoGroup.setText("���O�t�@�C�����");

      addFileInfoGroup();
    }
    return fileInfoGroup;

  }

  /**
   * �����x�����擾���܂��B
   * @return �����x��
   */
  public ACLabel getFileCountLabel(){
    if(fileCountLabel==null){

      fileCountLabel = new ACLabel();

      fileCountLabel.setText("���F");

      addFileCountLabel();
    }
    return fileCountLabel;

  }

  /**
   * ���O�t�@�C�������擾���܂��B
   * @return ���O�t�@�C����
   */
  public ACLabel getFileCount(){
    if(fileCount==null){

      fileCount = new ACLabel();

      fileCount.setBindPath("FILE_COUNT");

      addFileCount();
    }
    return fileCount;

  }

  /**
   * �\�����O�t�@�C�����x�����擾���܂��B
   * @return �\�����O�t�@�C�����x��
   */
  public ACLabel getViewFileLavel(){
    if(viewFileLavel==null){

      viewFileLavel = new ACLabel();

      viewFileLavel.setText("�\���F");

      addViewFileLavel();
    }
    return viewFileLavel;

  }

  /**
   * �\�����O�t�@�C�����擾���܂��B
   * @return �\�����O�t�@�C��
   */
  public ACLabel getViewFile(){
    if(viewFile==null){

      viewFile = new ACLabel();

      viewFile.setBindPath("VIEW_FILE_NUMBER");

      addViewFile();
    }
    return viewFile;

  }

  /**
   * ���O�f�[�^�e�[�u�����擾���܂��B
   * @return ���O�f�[�^�e�[�u��
   */
  public ACTable getLogDataTable(){
    if(logDataTable==null){

      logDataTable = new ACTable();

      logDataTable.setColumnModel(getLogDataTableColumnModel());

      logDataTable.setColumnSort(true);

      addLogDataTable();
    }
    return logDataTable;

  }

  /**
   * ���O�f�[�^�e�[�u���J�������f�����擾���܂��B
   * @return ���O�f�[�^�e�[�u���J�������f��
   */
  protected VRTableColumnModel getLogDataTableColumnModel(){
    if(logDataTableColumnModel==null){
      logDataTableColumnModel = new VRTableColumnModel(new TableColumn[]{});
      addLogDataTableColumnModel();
    }
    return logDataTableColumnModel;
  }

  /**
   * �����J�������擾���܂��B
   * @return �����J����
   */
  public ACTableColumn getLogDataTableColumn1(){
    if(logDataTableColumn1==null){

      logDataTableColumn1 = new ACTableColumn();

      logDataTableColumn1.setHeaderValue("����");

      logDataTableColumn1.setColumnName("LOG_DATETIME");

      logDataTableColumn1.setEditable(false);

      logDataTableColumn1.setColumns(12);

      logDataTableColumn1.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn1();
    }
    return logDataTableColumn1;

  }

  /**
   * �{���҃J�������擾���܂��B
   * @return �{���҃J����
   */
  public ACTableColumn getLogDataTableColumn2(){
    if(logDataTableColumn2==null){

      logDataTableColumn2 = new ACTableColumn();

      logDataTableColumn2.setHeaderValue("���[�U�[");

      logDataTableColumn2.setColumnName("USER_NAME");

      logDataTableColumn2.setEditable(false);

      logDataTableColumn2.setColumns(8);

      logDataTableColumn2.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn2();
    }
    return logDataTableColumn2;

  }

  /**
   * �v���O����ID�J�������擾���܂��B
   * @return �v���O����ID�J����
   */
  public ACTableColumn getLogDataTableColumn3(){
    if(logDataTableColumn3==null){

      logDataTableColumn3 = new ACTableColumn();

      logDataTableColumn3.setHeaderValue("�v���O����ID");

      logDataTableColumn3.setColumnName("AFFAIR_ID");

      logDataTableColumn3.setEditable(false);

      logDataTableColumn3.setColumns(8);

      logDataTableColumn3.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn3();
    }
    return logDataTableColumn3;

  }

  /**
   * �v���O�������̃J�������擾���܂��B
   * @return �v���O�������̃J����
   */
  public ACTableColumn getLogDataTableColumn4(){
    if(logDataTableColumn4==null){

      logDataTableColumn4 = new ACTableColumn();

      logDataTableColumn4.setHeaderValue("�v���O������");

      logDataTableColumn4.setColumnName("AFFAIR_NAME");

      logDataTableColumn4.setEditable(false);

      logDataTableColumn4.setColumns(28);

      logDataTableColumn4.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn4();
    }
    return logDataTableColumn4;

  }

  /**
   * �{�������J�������擾���܂��B
   * @return �{�������J����
   */
  public ACTableColumn getLogDataTableColumn5(){
    if(logDataTableColumn5==null){

      logDataTableColumn5 = new ACTableColumn();

      logDataTableColumn5.setHeaderValue("���p�Ҏ���");

      logDataTableColumn5.setColumnName("PATIENT_NAME");

      logDataTableColumn5.setEditable(false);

      logDataTableColumn5.setColumns(12);

      logDataTableColumn5.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn5();
    }
    return logDataTableColumn5;

  }

  /**
   * �{���J�i�����J�������擾���܂��B
   * @return �{���J�i�����J����
   */
  public ACTableColumn getLogDataTableColumn6(){
    if(logDataTableColumn6==null){

      logDataTableColumn6 = new ACTableColumn();

      logDataTableColumn6.setHeaderValue("���p�҃J�i����");

      logDataTableColumn6.setColumnName("PATIENT_NAME_KANA");

      logDataTableColumn6.setEditable(false);

      logDataTableColumn6.setColumns(8);

      logDataTableColumn6.setRendererType(VRTableCellViewer.RENDERER_TYPE_LABEL);

      addLogDataTableColumn6();
    }
    return logDataTableColumn6;

  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IL001Design() {

    try {
      initialize();

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

    this.add(getButtons(), VRLayout.NORTH);

    this.add(getContents(), VRLayout.CLIENT);

  }

  /**
   * �Ɩ��{�^���o�[�ɓ������ڂ�ǉ����܂��B
   */
  protected void addButtons(){

    buttons.add(getNextButton(), VRLayout.EAST);
    buttons.add(getPrevButton(), VRLayout.EAST);
  }

  /**
   * �߂�{�^���ɓ������ڂ�ǉ����܂��B
   */
  protected void addPrevButton(){

  }

  /**
   * ���փ{�^���ɓ������ڂ�ǉ����܂��B
   */
  protected void addNextButton(){

  }

  /**
   * �N���C�A���g�̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addContents(){

    contents.add(getFileInfoGroup(), VRLayout.NORTH);

    contents.add(getLogDataTable(), VRLayout.CLIENT);

  }

  /**
   * ���O�t�@�C�����O���[�v�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFileInfoGroup(){

    fileInfoGroup.add(getFileCountLabel(), null);

    fileInfoGroup.add(getFileCount(), null);

    fileInfoGroup.add(getViewFileLavel(), null);

    fileInfoGroup.add(getViewFile(), null);

  }

  /**
   * �����x���ɓ������ڂ�ǉ����܂��B
   */
  protected void addFileCountLabel(){

  }

  /**
   * ���O�t�@�C�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addFileCount(){

  }

  /**
   * �\�����O�t�@�C�����x���ɓ������ڂ�ǉ����܂��B
   */
  protected void addViewFileLavel(){

  }

  /**
   * �\�����O�t�@�C���ɓ������ڂ�ǉ����܂��B
   */
  protected void addViewFile(){

  }

  /**
   * ���O�f�[�^�e�[�u���ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTable(){

  }

  /**
   * ���O�f�[�^�e�[�u���J�������f���ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumnModel(){

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn1());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn2());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn3());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn4());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn5());

    getLogDataTableColumnModel().addColumn(getLogDataTableColumn6());

  }

  /**
   * �����J�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumn1(){

  }

  /**
   * �{���҃J�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumn2(){

  }

  /**
   * �v���O����ID�J�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumn3(){

  }

  /**
   * �v���O�������̃J�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumn4(){

  }

  /**
   * �{�������J�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumn5(){

  }

  /**
   * �{���J�i�����J�����ɓ������ڂ�ǉ����܂��B
   */
  protected void addLogDataTableColumn6(){

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

  /**
   * ���g��Ԃ��܂��B
   */
  protected IL001Design getThis() {
    return this;
  }
}
