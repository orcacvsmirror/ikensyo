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
   * ヒント本文を返します。
   * @return ヒント本文
   */
  public String getText() {
    return hintText.getText();
  }

  /**
   * ヒント本文を設定します。
   * @param text ヒント本文
   */
  public void setText(String text) {
    hintText.setText(text);
  }

  /**
   * ヒントタイトルを返します。
   * @return ヒントタイトル
   */
  public String getTitle() {
    return hintTitle.getText();
  }

  /**
   * ヒントタイトルを設定します。
   * @param title ヒントタイトル
   */
  public void setTitle(String title) {
    hintTitle.setText(title);
  }

  /**
   * コンストラクタ
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
   * コンポーネントを初期化します。
   * @throws Exception 初期化例外
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
