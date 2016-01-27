package jp.nichicom.ac.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.ACOSInfo;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * �Ɩ��{�^���o�[�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACAffairButton
 * @see VRPanel
 */
public class ACAffairButtonBar extends VRPanel{
  protected ACAffairButton back = new ACAffairButton();
  protected VRLabel title = new VRLabel();

  /**
   * �߂�{�^���̕\����Ԃ�ݒ肵�܂��B
   * @param backVisible �߂�{�^���̕\�����
   */
  public void setBackVisible(boolean backVisible){
    back.setVisible(backVisible);
  }

  /**
   * �߂�{�^���̕\����Ԃ�Ԃ��܂��B
   * @return �߂�{�^���̕\�����
   */
  public boolean isBackVisible(){
    return back.isVisible();
  }

  /**
   * �Ɩ��^�C�g����Ԃ��܂��B
   * @return �Ɩ��^�C�g��
   */
  public String getTitle(){
    return title.getText();
  }
  /**
   * �Ɩ��^�C�g����ݒ肵�܂��B
   * @param title �Ɩ��^�C�g��
   */
  public void setTitle(String title){
    this.title.setText(title);
  }

  /**
   * �Ɩ��^�C�g����Ԃ��܂��B
   * @return �Ɩ��^�C�g��
   */
  public String getText(){
    return title.getText();
  }
  /**
   * �Ɩ��^�C�g����ݒ肵�܂��B
   * @param text �Ɩ��^�C�g��
   */
  public void setText(String text){
    this.title.setText(text);
  }


  /**
     * �R���X�g���N�^�ł��B
     */
    public ACAffairButtonBar() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 2006/02/06[Tozo Tanaka] : replace begin
        // setBackAffair(ACFrame.getInstance().getNowAffair());
        setBackAffair(ACFrame.getInstance().getBackAffair());
        // 2006/02/06[Tozo Tanaka] : replace end
    }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ��������O
   */
  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    back.setText("�߂�(R)");
    back.setIconPath(ACConstants.ICON_PATH_BACK_24);
    if(title.getFont()!=null){
      title.setFont(new java.awt.Font(title.getFont().getName(),
                                      title.getFont().getStyle(), (int)(title.getFont().getSize()*20.0/11.0)));
    }

    title.setBorder(javax.swing.BorderFactory.createEmptyBorder(4,8,4,8));
    title.setText("�Ɩ��^�C�g��");
    title.setHorizontalAlignment(SwingConstants.CENTER);
    back.setText("�߂�(R)");
    back.setMnemonic('R');
    back.addActionListener(new ActionListener() {
      protected boolean lockFlag = false;
      public void actionPerformed(ActionEvent e) {
        if(lockFlag){
          return;
        }
        lockFlag = true;
        try {
          ACFrame.getInstance().back();
        }
        catch (Exception ex) {
          //��O�̏����̓C�x���g�Ϗ�
           ACFrame.getInstance().showExceptionMessage(ex);
        }
        lockFlag = false;
      }

    });
    
    this.add(back, VRLayout.WEST);
    this.add(title, VRLayout.WEST);

    // Mac�łȂ���ΐF��t����
    if ( !ACOSInfo.isMac() ) {
      title.setForeground(java.awt.Color.white);
      this.setBackground(new java.awt.Color(0, 51, 153));
    }

  }

  /**
   * �O��ʂ̏�񂩂珉���ݒ肵�܂��B
   * @param affair �O��ʂ̋Ɩ����
   */
  protected void setBackAffair(ACAffairInfo affair){
    if (affair == null) {
      back.setToolTipText("���C�����j���[�֖߂�܂��B");
    }
    else {
      back.setToolTipText(affair.getTitle() + "�֖߂�܂��B");
    }
  }
  
  /**
   * �O��ʂ֖߂�{�^����Ԃ��܂��B
   * @return �O��ʂ֖߂�{�^��
   */
  public ACAffairButton getBackButton() {
    return back;
  }
}
