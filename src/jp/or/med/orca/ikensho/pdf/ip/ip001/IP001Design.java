
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
 * �쐬��: 2006/05/01  ���{�R���s���[�^�[������� �c���@���� �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���[ (P)
 * �v���Z�X �ȈՒ��[�J�X�^�}�C�Y�c�[�� (001)
 * �v���O���� �ȈՒ��[�J�X�^�}�C�Y�c�[�� (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.Component;

import javax.swing.SwingConstants;
import javax.swing.table.TableColumn;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.component.table.VRTableCellViewer;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
/**
 * �ȈՒ��[�J�X�^�}�C�Y�c�[����ʍ��ڃf�U�C��(IP001) 
 */
public class IP001Design extends ACAffairContainer implements ACAffairable {
  //GUI�R���|�[�l���g

  private ACAffairButtonBar buttons;

  private ACAffairButton open;

  private ACAffairButton print;

  private ACAffairButton save;

  private ACAffairButton close;

  private ACPanel contents;

  private ACTable formats;

  private VRTableColumnModel formatsColumnModel;

  private ACTableColumn formatNo;

  private ACTableColumn formatID;

  private ACTableColumn formatType;

  private ACTableColumn formatX;

  private ACTableColumn formatY;

  private ACTableColumn formatWidth;

  private ACTableColumn formatHeight;

  private ACTableColumn formatSize;

  private ACTableColumn formatBorderWidth;

  private ACTableColumn formatText;

  //getter

  /**
   * �Ɩ��{�^���o�[���擾���܂��B
   * @return �Ɩ��{�^���o�[
   */
  public ACAffairButtonBar getButtons(){
    if(buttons==null){

      buttons = new ACAffairButtonBar();

      buttons.setText("�㌩���@�ȈՒ��[�J�X�^�}�C�Y�c�[��");

      buttons.setBackVisible(false);

      addButtons();
    }
    return buttons;

  }

  /**
   * �J�����擾���܂��B
   * @return �J��
   */
  public ACAffairButton getOpen(){
    if(open==null){

      open = new ACAffairButton();

      open.setText("�J��(O)");

      open.setToolTipText("�ҏW���钠�[���J���܂��B");

      open.setMnemonic('O');

      open.setIconPath(ACConstants.ICON_PATH_OPEN_24);

      addOpen();
    }
    return open;

  }

  /**
   * ������擾���܂��B
   * @return ���
   */
  public ACAffairButton getPrint(){
    if(print==null){

      print = new ACAffairButton();

      print.setText("���(P)");

      print.setEnabled(false);

      print.setToolTipText("���݂̒��[��񂩂�PDF���쐬���܂��B");

      print.setMnemonic('P');

      print.setIconPath(ACConstants.ICON_PATH_PRINT_24);

      addPrint();
    }
    return print;

  }

  /**
   * �ۑ����擾���܂��B
   * @return �ۑ�
   */
  public ACAffairButton getSave(){
    if(save==null){

      save = new ACAffairButton();

      save.setText("�ۑ�(S)");

      save.setEnabled(false);

      save.setToolTipText("�ҏW�������[��ۑ����܂��B");

      save.setMnemonic('S');

      save.setIconPath(ACConstants.ICON_PATH_UPDATE_24);

      addSave();
    }
    return save;

  }

  /**
   * �I�����擾���܂��B
   * @return �I��
   */
  public ACAffairButton getClose(){
    if(close==null){

      close = new ACAffairButton();

      close.setText("�I��(X)");

      close.setToolTipText("�I�����܂��B");

      close.setMnemonic('X');

      close.setIconPath(ACConstants.ICON_PATH_EXIT_24);

      addClose();
    }
    return close;

  }

  /**
   * ���̈���擾���܂��B
   * @return ���̈�
   */
  public ACPanel getContents(){
    if(contents==null){

      contents = new ACPanel();

      addContents();
    }
    return contents;

  }

  /**
   * ��`�̍��ڈꗗ���擾���܂��B
   * @return ��`�̍��ڈꗗ
   */
  public ACTable getFormats(){
    if(formats==null){

      formats = new ACTable();

      formats.setColumnModel(getFormatsColumnModel());

      addFormats();
    }
    return formats;

  }

  /**
   * ��`�̍��ڈꗗ�J�������f�����擾���܂��B
   * @return ��`�̍��ڈꗗ�J�������f��
   */
  protected VRTableColumnModel getFormatsColumnModel(){
    if(formatsColumnModel==null){
      formatsColumnModel = new VRTableColumnModel(new TableColumn[]{});
      addFormatsColumnModel();
    }
    return formatsColumnModel;
  }

  /**
   * No.���擾���܂��B
   * @return No.
   */
  public ACTableColumn getFormatNo(){
    if(formatNo==null){

      formatNo = new ACTableColumn();

      formatNo.setHeaderValue("No.");

      formatNo.setColumnName("Type");

      formatNo.setColumns(3);

      formatNo.setHorizontalAlignment(SwingConstants.RIGHT);

      formatNo.setRendererType(ACTableCellViewer.RENDERER_TYPE_SERIAL_NO);

      formatNo.setSortable(false);

      addFormatNo();
    }
    return formatNo;

  }

  /**
   * ID���擾���܂��B
   * @return ID
   */
  public ACTableColumn getFormatID(){
    if(formatID==null){

      formatID = new ACTableColumn();

      formatID.setHeaderValue("ID");

      formatID.setColumnName("Id");

      formatID.setColumns(16);

      addFormatID();
    }
    return formatID;

  }

  /**
   * ��ނ��擾���܂��B
   * @return ���
   */
  public ACTableColumn getFormatType(){
    if(formatType==null){

      formatType = new ACTableColumn();

      formatType.setHeaderValue("���");

      formatType.setColumnName("Type");

      formatType.setColumns(3);

      addFormatType();
    }
    return formatType;

  }

  /**
   * X���W���擾���܂��B
   * @return X���W
   */
  public ACTableColumn getFormatX(){
    if(formatX==null){

      formatX = new ACTableColumn();

      formatX.setHeaderValue("X���W");

      formatX.setColumnName("X");

      formatX.setEditable(true);

      formatX.setColumns(4);

      formatX.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatX();
    }
    return formatX;

  }

  /**
   * Y���W���擾���܂��B
   * @return Y���W
   */
  public ACTableColumn getFormatY(){
    if(formatY==null){

      formatY = new ACTableColumn();

      formatY.setHeaderValue("Y���W");

      formatY.setColumnName("Y");

      formatY.setEditable(true);

      formatY.setColumns(4);

      formatY.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatY();
    }
    return formatY;

  }

  /**
   * �����擾���܂��B
   * @return ��
   */
  public ACTableColumn getFormatWidth(){
    if(formatWidth==null){

      formatWidth = new ACTableColumn();

      formatWidth.setHeaderValue("��");

      formatWidth.setColumnName("Width");

      formatWidth.setEditable(true);

      formatWidth.setColumns(3);

      formatWidth.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatWidth();
    }
    return formatWidth;

  }

  /**
   * �������擾���܂��B
   * @return ����
   */
  public ACTableColumn getFormatHeight(){
    if(formatHeight==null){

      formatHeight = new ACTableColumn();

      formatHeight.setHeaderValue("����");

      formatHeight.setColumnName("Height");

      formatHeight.setEditable(true);

      formatHeight.setColumns(3);

      formatHeight.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatHeight();
    }
    return formatHeight;

  }

  /**
   * �����T�C�Y���擾���܂��B
   * @return �����T�C�Y
   */
  public ACTableColumn getFormatSize(){
    if(formatSize==null){

      formatSize = new ACTableColumn();

      formatSize.setHeaderValue("�����T�C�Y");

      formatSize.setColumnName("FontSize");

      formatSize.setEditable(true);

      formatSize.setColumns(6);

      formatSize.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatSize();
    }
    return formatSize;

  }

  /**
   * ���̑������擾���܂��B
   * @return ���̑���
   */
  public ACTableColumn getFormatBorderWidth(){
    if(formatBorderWidth==null){

      formatBorderWidth = new ACTableColumn();

      formatBorderWidth.setHeaderValue("���̑���");

      formatBorderWidth.setColumnName("BorderWidth");

      formatBorderWidth.setEditable(true);

      formatBorderWidth.setColumns(5);

      formatBorderWidth.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatBorderWidth();
    }
    return formatBorderWidth;

  }

  /**
   * �������擾���܂��B
   * @return ����
   */
  public ACTableColumn getFormatText(){
    if(formatText==null){

      formatText = new ACTableColumn();

      formatText.setHeaderValue("����");

      formatText.setColumnName("Text");

      formatText.setEditable(true);

      formatText.setColumns(8);

      formatText.setEditorType(VRTableCellViewer.EDITOR_TYPE_TEXT_FIELD);

      addFormatText();
    }
    return formatText;

  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IP001Design() {

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

    buttons.add(getClose(), VRLayout.EAST);
    buttons.add(getSave(), VRLayout.EAST);
    buttons.add(getPrint(), VRLayout.EAST);
    buttons.add(getOpen(), VRLayout.EAST);
  }

  /**
   * �J���ɓ������ڂ�ǉ����܂��B
   */
  protected void addOpen(){

  }

  /**
   * ����ɓ������ڂ�ǉ����܂��B
   */
  protected void addPrint(){

  }

  /**
   * �ۑ��ɓ������ڂ�ǉ����܂��B
   */
  protected void addSave(){

  }

  /**
   * �I���ɓ������ڂ�ǉ����܂��B
   */
  protected void addClose(){

  }

  /**
   * ���̈�ɓ������ڂ�ǉ����܂��B
   */
  protected void addContents(){

    contents.add(getFormats(), VRLayout.CLIENT);

  }

  /**
   * ��`�̍��ڈꗗ�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormats(){

  }

  /**
   * ��`�̍��ڈꗗ�J�������f���ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatsColumnModel(){

    getFormatsColumnModel().addColumn(getFormatNo());

    getFormatsColumnModel().addColumn(getFormatID());

    getFormatsColumnModel().addColumn(getFormatType());

    getFormatsColumnModel().addColumn(getFormatX());

    getFormatsColumnModel().addColumn(getFormatY());

    getFormatsColumnModel().addColumn(getFormatWidth());

    getFormatsColumnModel().addColumn(getFormatHeight());

    getFormatsColumnModel().addColumn(getFormatSize());

    getFormatsColumnModel().addColumn(getFormatBorderWidth());

    getFormatsColumnModel().addColumn(getFormatText());

  }

  /**
   * No.�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatNo(){

  }

  /**
   * ID�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatID(){

  }

  /**
   * ��ނɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatType(){

  }

  /**
   * X���W�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatX(){

  }

  /**
   * Y���W�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatY(){

  }

  /**
   * ���ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatWidth(){

  }

  /**
   * �����ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatHeight(){

  }

  /**
   * �����T�C�Y�ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatSize(){

  }

  /**
   * ���̑����ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatBorderWidth(){

  }

  /**
   * �����ɓ������ڂ�ǉ����܂��B
   */
  protected void addFormatText(){

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

  public static void main(String[] args) {
    //�f�t�H���g�f�o�b�O�N��
    try {
      ACFrame.getInstance().setFrameEventProcesser(new IkenshoFrameEventProcesser());
      ACFrame.debugStart(new ACAffairInfo(IP001Design.class.getName()));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * ���g��Ԃ��܂��B
   */
  protected IP001Design getThis() {
    return this;
  }
}
