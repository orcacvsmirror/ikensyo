package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.im.InputSubset;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.vr.component.VRButton;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.affair.IkenshoExtraSpecialNoteDialog;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoShijishoInstructContainer extends VRPanel {
  private ACTextArea text = new ACTextArea();
  private VRButton showSelect = new VRButton();
//  private JScrollPane textScroll = new JScrollPane();
  private VRPanel buttuns = new VRPanel();
  private VRLabel title = new VRLabel();
  private VRLabel spacer = new VRLabel();
  private VRPanel titles = new VRPanel();
  private ACIntegerCheckBox check = new ACIntegerCheckBox();
  private int code;
  private String caption;
  private int teikeiMaxLength = 50;

  /**
   * �{�����擾���܂��B
   * @return �{��
   */
  public String getText(){
    return text.getText();
  }

  /**
   * �{����ݒ肵�܂��B
   * @param text �{��
   */
  public void setText(String text){
    this.text.setText(text);
  }

  /**
   * �{���̍ő���͍s�� ��Ԃ��܂��B
   * @return �{���̍ő���͍s��
   */
  public int getMaxRows() {
      return text.getMaxRows();
  }
  /**
   * �{���̍ő���͍s�� ��ݒ肵�܂��B
   * @param maxRows �{���̍ő���͍s��
   */
  public void setMaxRows(int maxRows) {
      text.setMaxRows(maxRows);
  }

//  /**
//   * �{���̍s���𐧌����邩��Ԃ��܂��B
//   * @return �{���̍s���𐧌����邩
//   */
//  public boolean isUseMaxRows() {
//    return text.isUseMaxRows();
//  }
//  /**
//   * �{���̍s���𐧌����邩��ݒ肵�܂��B
//   * @param useMaxRows �{���̍s���𐧌����邩
//   */
//  public void setUseMaxRows(boolean useMaxRows) {
//    text.setUseMaxRows(useMaxRows);
//  }

  /**
   * �{���̍s�����擾���܂��B
   * @return �{���̍s��
   */
  public int getRows(){
    return text.getRows();
  }

  /**
   * �{���̍s����ݒ肵�܂��B
   * @param rows �{���̍s��
   */
  public void setRows(int rows){
    this.text.setRows(rows);
  }

  /**
   * �{���̗񐔂��擾���܂��B
   * @return �{���̗�
   */
  public int getColumns(){
    return text.getColumns();
  }

  /**
   * �{���̗񐔂�ݒ肵�܂��B
   * @param columns �{���̗�
   */
  public void setColumns(int columns){
    this.text.setColumns(columns);
  }

//  /**
//   * �{���̗񐔐������擾���܂��B
//   * @return �{���̗񐔐���
//   */
//  public int getMaxColumns(){
//    return text.getMaxColumns();
//  }
//
//  /**
//   * �{���̗񐔐�����ݒ肵�܂��B
//   * @param maxColumns �{���̗񐔐���
//   */
//  public void setMaxColumns(int maxColumns){
//    this.text.setMaxColumns(maxColumns);
//  }

  /**
   * �{���̕������������擾���܂��B
   * @return �{���̕���������
   */
  public int getMaxLength(){
    return text.getMaxLength();
  }

  /**
   * �{���̕�����������ݒ肵�܂��B
   * @param maxLength �{���̕���������
   */
  public void setMaxLength(int maxLength){
    this.text.setMaxLength(maxLength);
  }

  /**
   * �{���̃o�C���h�p�X���擾���܂��B
   * @return �{���̃o�C���h�p�X
   */
  public String getTextBindPath(){
    return text.getBindPath();
  }

  /**
   * �{���̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �{���̃o�C���h�p�X
   */
  public void setTextBindPath(String bindPath){
    this.text.setBindPath(bindPath);
  }

  /**
   * �^�C�g�����擾���܂��B
   * @return �^�C�g��
   */
  public String getTitle(){
    return title.getText();
  }
  /**
   * �^�C�g����ݒ肵�܂��B
   * @param title �^�C�g��
   */
  public void setTitle(String title){
    this.title.setText(title);
  }

  /**
   * �I����ʕ\���{�^���̃e�L�X�g���擾���܂��B
   * @return �I����ʕ\���{�^���̃e�L�X�g
   */
  public String getShowSelectText(){
    return showSelect.getText();
  }
  /**
   * �I����ʕ\���{�^���̃e�L�X�g��ݒ肵�܂��B
   * @param showSelectText �I����ʕ\���{�^���̃e�L�X�g
   */
  public void setShowSelectText(String showSelectText){
    this.showSelect.setText(showSelectText);
  }

  /**
   * �I����ʕ\���{�^���̃j�[���j�b�N���擾���܂��B
   * @return �I����ʕ\���{�^���̃j�[���j�b�N
   */
  public int getShowSelectMnemonic(){
    return showSelect.getMnemonic();
  }

  /**
   * �I����ʕ\���{�^���̃j�[���j�b�N��ݒ肵�܂��B
   * @param mnemonic �I����ʕ\���{�^���̃j�[���j�b�N
   */
  public void setShowSelectMnemonic(int mnemonic){
    this.showSelect.setMnemonic(mnemonic);
  }

  /**
   * �`�F�b�N�̃e�L�X�g���擾���܂��B
   * @return �`�F�b�N�̃e�L�X�g
   */
  public String getCheckText(){
    return check.getText();
  }
  /**
   * �`�F�b�N�̃e�L�X�g��ݒ肵�܂��B
   * @param checkText �`�F�b�N�̃e�L�X�g
   */
  public void setCheckText(String checkText){
    this.check.setText(checkText);
  }

  /**
   * �`�F�b�N�̃o�C���h�p�X���擾���܂��B
   * @return �`�F�b�N�̃o�C���h�p�X
   */
  public String getCheckBindPath(){
    return check.getBindPath();
  }

  /**
   * �`�F�b�N�̃o�C���h�p�X��ݒ肵�܂��B
   * @param bindPath �`�F�b�N�̃o�C���h�p�X
   */
  public void setCheckBindPath(String bindPath){
    this.check.setBindPath(bindPath);
  }

  /**
   * �`�F�b�N��Visible���擾���܂��B
   * @return �`�F�b�N��Visible
   */
  public boolean isCheckVisible(){
    return check.isVisible();
  }

  /**
   * �`�F�b�N��Visible��ݒ肵�܂��B
   * @param visible �`�F�b�N��Visible
   */
  public void setCheckVisible(boolean visible){
    this.check.setVisible(visible);
    if(!visible){
      text.setEnabled(true);
      showSelect.setEnabled(true);
    }
  }
  /**
   * �I���_�C�A���O�p�̒�^���R�[�h��Ԃ��܂��B
   * @return ��^���R�[�h
   */
  public int getCode() {
    return code;
  }
  /**
   * �I���_�C�A���O�p�̒�^���R�[�h��ݒ肵�܂��B
   * @param code ��^���R�[�h
   */
  public void setCode(int code) {
    this.code = code;
  }

  /**
   * ���ږ����擾���܂��B
   * @return ���ږ�
   */
  public String getCaption() {
    return caption;
  }
  /**
   * ���ږ���ݒ肵�܂��B
   * @param caption ���ږ�
   */
  public void setCaption(String caption) {
    this.caption = caption;
    this.showSelect.setToolTipText("�u" + caption + "�v�I����ʂ�\�����܂��B");
  }

  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoHoumonKangoShijishoInstructContainer() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }

    showSelect.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        setText(new IkenshoExtraSpecialNoteDialog(getCaption(), getCode(),
                                                  getMaxLength(),
                                                  text.getColumns(),
                                                  text.getRows(),
                                                  teikeiMaxLength).showModal(
            getText()));
      }
    });

    check.addItemListener(new ACFollowDisabledItemListener(new JComponent[] {
        showSelect, text}));

  }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ��������O
   */
  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    text.setLineWrap(true);
    text.setMaxLength(120);
//    text.setMaxColumns(84);
    text.setMaxRows(3);
//    text.setUseMaxRows(true);
    text.setRows(3);
    text.setIMEMode(InputSubset.KANJI);
    text.setEnabled(false);
    text.setColumns(104);
    titles.setLayout(new BorderLayout());
    spacer.setText(" �@");
    showSelect.setEnabled(false);
    this.add(titles, VRLayout.NORTH);
    this.add(spacer, VRLayout.WEST);
    this.add(buttuns, VRLayout.EAST);
//    this.add(text, VRLayout.LEFT);
    this.add(text, VRLayout.FLOW);

    //    buttuns.setLayout(new FlowLayout());
    buttuns.add(showSelect, null);
//    textScroll.setViewportView(text);
    titles.add(title, BorderLayout.WEST);
    titles.add(check, BorderLayout.CENTER);
  }
  /**
   * ��^���̍ő啶���񒷂�Ԃ��܂��B
   * @return ��^���̍ő啶����
   */
  public int getTeikeiMaxLength() {
    return teikeiMaxLength;
  }
  /**
   * ��^���̍ő啶���񒷂�ݒ肵�܂��B
   * @param teikeiMaxLength ��^���̍ő啶����
   */
  public void setTeikeiMaxLength(int teikeiMaxLength) {
    this.teikeiMaxLength = teikeiMaxLength;
  }
}