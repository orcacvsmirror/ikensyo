package jp.or.med.orca.ikensho.component;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACToggleButton;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoHintButton
    extends ACToggleButton {
  protected JComponent hintContainer;
  protected IkenshoHintContainer hintArea;
  protected String hintTitle;
  protected String hintText;
  protected JComponent[] followHideComponents;
  protected IkenshoHintButton[] followPressedButtons;
  protected JComponent[] followDisabledComponents;

  /**
   * コンストラクタです。
   */
  public IkenshoHintButton() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    ( (ToggleButtonModel)this.getModel()).addItemListener(new ItemListener() {
      public void itemStateChanged(ItemEvent e) {
        boolean select;
        if (e.getStateChange() == ItemEvent.SELECTED) {
          if (getHintArea() != null) {
            getHintArea().setText(hintText);
            getHintArea().setTitle(hintTitle);
          }
          select = true;

          if (getFollowPressedButtons() != null) {
            int end = getFollowPressedButtons().length;
            for (int i = 0; i < end; i++) {
              if(getFollowPressedButtons()[i].isPushed()){
                getFollowPressedButtons()[i].fireActionPerformed(new ActionEvent(getFollowPressedButtons()[i], 0, ""));
                getFollowPressedButtons()[i].setSelected(false);
              }
            }
          }
        }
        else if (e.getStateChange() == ItemEvent.DESELECTED) {
          select = false;
        }
        else {
          return;
        }

        if (getHintContainer() != null) {
          getHintContainer().setVisible(select);
        }

        if (getFollowHideComponents() != null) {
          int end = getFollowHideComponents().length;
          for (int i = 0; i < end; i++) {
            getFollowHideComponents()[i].setVisible(!select);
          }
        }
        if (getFollowDisabledComponents() != null) {
          int end = getFollowDisabledComponents().length;
          for (int i = 0; i < end; i++) {
            getFollowDisabledComponents()[i].setEnabled(!select);
          }
        }
      }
    });
  }

  /**
   * コンポーネントを初期化します。
   * @throws Exception 初期化例外
   */
  private void jbInit() throws Exception {
    this.setIconPathUnPushed(IkenshoConstants.IMAGE_PATH_HINT_BUTTON);
    this.setMaximumSize(new Dimension(29, 20));
    this.setPreferredSize(new Dimension(50, 20));
    this.setTextPushed("戻る");
  }

  /**
   * ヒントを表示させるヒントコンポーネントを返します。
   * @return ヒントを表示させるヒントコンポーネント
   */
  public IkenshoHintContainer getHintArea() {
    return hintArea;
  }

  /**
   * ヒント表示領域となるコンテナを返します。
   * @return ヒント表示領域となるコンテナ
   */
  public JComponent getHintContainer() {
    return hintContainer;
  }

  /**
   * ヒント本文を返します。
   * @return ヒント本文
   */
  public String getHintText() {
    return hintText;
  }

  /**
   * ヒントタイトルを返します。
   * @return ヒントタイトル
   */
  public String getHintTitle() {
    return hintTitle;
  }

  /**
   * ヒントタイトルを設定します。
   * @param hintTitle ヒントタイトル
   */
  public void setHintTitle(String hintTitle) {
    this.hintTitle = hintTitle;
  }

  /**
   * ヒント本文を設定します。
   * @param hintText ヒント本文
   */
  public void setHintText(String hintText) {
    this.hintText = hintText;
  }

  /**
   * ヒント表示領域となるコンテナを設定します。
   * @param hintContainer ヒント表示領域となるコンテナ
   */
  public void setHintContainer(JComponent hintContainer) {
    this.hintContainer = hintContainer;
  }

  /**
   * ヒントを表示させるヒントコンポーネントを設定します。
   * @param hintArea ヒントを表示させるヒントコンポーネント
   */
  public void setHintArea(IkenshoHintContainer hintArea) {
    this.hintArea = hintArea;
  }

  /**
   * 連動して表示を切り替えるコンポーネント群を返します。
   * @return 連動して表示を切り替えるコンポーネント群
   */
  public JComponent[] getFollowHideComponents() {
    return followHideComponents;
  }

  /**
   * 連動して表示を切り替えるコンポーネント群を設定します。
   * @param followVisibleComponents 連動して表示を切り替えるコンポーネント群
   */
  public void setFollowHideComponents(JComponent[] followVisibleComponents) {
    this.followHideComponents = followVisibleComponents;
  }

  /**
   * 連動して押し下げ状態を切り替えるボタン群を返します。
   * @return 連動して押し下げ状態を切り替えるボタン群
   */
  public IkenshoHintButton[] getFollowPressedButtons() {
    return followPressedButtons;
  }

  /**
   * 連動して押し下げ状態を切り替えるボタン群を設定します。
   * @param followPressedButtons 連動して押し下げ状態を切り替えるボタン群
   */
  public void setFollowPressedButtons(IkenshoHintButton[] followPressedButtons) {
    this.followPressedButtons = followPressedButtons;
  }

  /**
   * 連動して有効状態を切り替えるコンポーネント群を返します。
   * @return 連動して有効状態を切り替えるコンポーネント群
   */
  public JComponent[] getFollowDisabledComponents() {
    return followDisabledComponents;
  }

  /**
   * 連動して有効状態を切り替えるコンポーネント群を設定します。
   * @param followDisabledComponents 連動して有効状態を切り替えるコンポーネント群
   */
  public void setFollowDisabledComponents(JComponent[] followDisabledComponents) {
    this.followDisabledComponents = followDisabledComponents;
  }
}
