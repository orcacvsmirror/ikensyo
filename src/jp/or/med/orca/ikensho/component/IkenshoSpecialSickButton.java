package jp.or.med.orca.ikensho.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

import jp.nichicom.ac.component.ACToggleButton;

/** TODO <HEAD_IKENSYO> */
public class IkenshoSpecialSickButton
    extends ACToggleButton {
  protected JComboBox combo;
  protected ComboBoxModel unpressedModel;
  protected ComboBoxModel pressedModel;
  protected Object pressedValue;
  protected Object unPressedValue;

  /**
   * コンストラクタです。
   */
  public IkenshoSpecialSickButton() {

    addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        refreshCombo();
      }
    });
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * 連動するコンボ設定等を再設定します。
   */
  public void refreshCombo(){
    if (getCombo() != null) {
      if (!isPushed()) {
        if (getPressedModel() != null) {
          unPressedValue = getCombo().getEditor().getItem();
          // 2007/10/10 [Masahiko Higuchi] Addition - begin
          // 押下時
          if(getCombo() instanceof IkenshoOptionComboBox){
            ((IkenshoOptionComboBox)getCombo()).setOptionMode(false);
            setUnpressedModel(((IkenshoOptionComboBox)getCombo()).getOriginalModel());
          }
          // 2007/10/10 [Masahiko Higuchi] Addition - end
          getCombo().setModel(getPressedModel());
          getCombo().setEditable(false);
          getCombo().setSelectedItem(pressedValue);
        }
      }
      else {
        if (getUnpressedModel() != null) {
          pressedValue = getCombo().getEditor().getItem();
          // 2007/10/10 [Masahiko Higuchi] Replace - begin
          // 非押下時
          if(getCombo() instanceof IkenshoOptionComboBox){
              try {
                ((IkenshoOptionComboBox)getCombo()).setOptionMode(true);
                // 押下時⇒非押下時変更時のコンボの差し替え時に、定型文が編集されている場合に備え再設定
                setUnpressedModel(((IkenshoOptionComboBox)getCombo()).getTeikeibunComboBoxModel());
              } catch (Exception e) {}
          }
          // 2007/10/10 [Masahiko Higuchi] Replace - end
          getCombo().setModel(getUnpressedModel());
          getCombo().setEditable(true);
          getCombo().setSelectedItem(unPressedValue);
        }
      }
    }
  }

  /**
   * 連動するコンボを返します。
   * @return コンボ
   */
  public JComboBox getCombo() {
    return combo;
  }

  /**
   * 押下時のモデルを返します。
   * @return 押下時のモデル
   */
  public ComboBoxModel getPressedModel() {
    return pressedModel;
  }

  /**
   * 非押下時のモデルを返します。
   * @return 非押下時のモデル
   */
  public ComboBoxModel getUnpressedModel() {
    return unpressedModel;
  }

  /**
   * 非押下時のモデルを設定します。
   * @param unpressedModel 非押下時のモデル
   */
  public void setUnpressedModel(ComboBoxModel unpressedModel) {
    this.unpressedModel = unpressedModel;
  }

  /**
   * 押下時のモデルを設定します。
   * @param pressedModel 押下時のモデル
   */
  public void setPressedModel(ComboBoxModel pressedModel) {
    this.pressedModel = pressedModel;
  }

  /**
   * 連動するコンボを設定します。
   * @param combo コンボ
   */
  public void setCombo(JComboBox combo) {
    this.combo = combo;
  }

  /**
   * コンポーネントを初期化します。
   * @throws Exception 初期化例外
   */
  private void jbInit() throws Exception {
    this.setTextPushed("特定疾病");
    this.setTextUnPushed("特定疾病");
  }

}
