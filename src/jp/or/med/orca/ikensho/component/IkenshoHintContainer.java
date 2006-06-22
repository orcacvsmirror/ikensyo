package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.SystemColor;

import javax.swing.BorderFactory;

import jp.nichicom.ac.io.ACResourceIconPooler;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.VRTextArea;
import jp.nichicom.vr.container.VRPanel;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHintContainer extends VRPanel {
  private VRTextArea hintText = new VRTextArea();
  private VRLabel hintTitle = new VRLabel();
  private BorderLayout borderLayout1 = new BorderLayout();
  /**
   * �q���g�{����Ԃ��܂��B
   * @return �q���g�{��
   */
  public String getText() {
    return hintText.getText();
  }

  /**
   * �q���g�{����ݒ肵�܂��B
   * @param text �q���g�{��
   */
  public void setText(String text) {
    hintText.setText(text);
  }

  /**
   * �q���g�^�C�g����Ԃ��܂��B
   * @return �q���g�^�C�g��
   */
  public String getTitle() {
    return hintTitle.getText();
  }

  /**
   * �q���g�^�C�g����ݒ肵�܂��B
   * @param title �q���g�^�C�g��
   */
  public void setTitle(String title) {
    hintTitle.setText(title);
  }

  /**
   * �R���X�g���N�^
   */
  public IkenshoHintContainer() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * �R���|�[�l���g�����������܂��B
   * @throws Exception ��������O
   */
  private void jbInit() throws Exception {
    hintTitle.setIcon(ACResourceIconPooler.getInstance().getImage(IkenshoConstants.IMAGE_PATH_HINT));
    hintTitle.setOpaque(false);
    hintText.setRows(10);
    hintText.setLineWrap(true);
    hintText.setColumns(100);
    hintText.setOpaque(false);
    hintText.setEditable(false);
    this.setBackground(SystemColor.text);
    this.setBorder(BorderFactory.createLoweredBevelBorder());
    this.setLayout(borderLayout1);
    this.add(hintTitle, BorderLayout.NORTH);
    this.add(hintText, BorderLayout.CENTER);
  }

}
