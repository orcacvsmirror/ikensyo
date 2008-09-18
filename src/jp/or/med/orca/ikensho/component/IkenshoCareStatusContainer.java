package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.im.InputSubset;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoCareStatusContainer
    extends VRLabelContainer {
//  2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
    private IkenshoOptionComboBox value = new IkenshoOptionComboBox();
//  2007/10/18 [Masahiko Higuchi] Replace - end
  private ACIntegerCheckBox check = new ACIntegerCheckBox();
  private VRLabelContainer values = new VRLabelContainer();
  private ACParentHesesPanelContainer heses = new ACParentHesesPanelContainer();
  /**
   * チェックボックスを返します。
   * @return チェックボックス
   */
  public ACIntegerCheckBox getCheckBox(){
    return check;
  }

//2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
  /**
   * コンボボックスを返します。
   * @return コンボボックス
   * @since 3.0.5
   */
  public IkenshoOptionComboBox getComboBox(){
    return value;
  }
//2007/10/18 [Masahiko Higuchi] Replace - end

  /**
   * チェック用のバインドパスを設定します。
   * @param bindPath バインドパス
   */
  public void setCheckBindPath(String bindPath) {
    check.setBindPath(bindPath);
  }

  /**
   * チェック用のバインドパスを返します。
   * @return バインドパス
   */
  public String getCheckBindPath() {
    return check.getBindPath();
  }

  /**
   * 値用のバインドパスを設定します。
   * @param bindPath バインドパス
   */
  public void setValueBindPath(String bindPath) {
    value.setBindPath(bindPath);
  }

  /**
   * 値用のバインドパスを返します。
   * @return バインドパス
   */
  public String getValueBindPath() {
    return value.getBindPath();
  }

  /**
   * 値用のモデルを設定します。
   * @param model モデル
   */
  public void setValueModel(ComboBoxModel model) {
    value.setModel(model);
  }

  /**
   * 値用のモデルを返します。
   * @return モデル
   */
  public ComboBoxModel getValueModel() {
    return value.getModel();
  }
  /**
   * 値のコンボを返します。
   * @return コンボ
   */
  public JComboBox getValueCombo(){
    return value;
  }

  /**
   * チェックしているかを設定します。
   * @param checked チェックしているか
   */
  public void setChecked(boolean checked) {
    check.setSelected(checked);
  }

  /**
   * チェックしているかを返します。
   * @return チェックしているか
   */
  public boolean isChecked() {
    return check.isSelected();
  }

  /**
   * チェックのテキストを設定します。
   * @param text テキスト
   */
  public void setCheckText(String text) {
    check.setText(text);
  }

  /**
   * チェックのテキストを返します。
   * @return テキスト
   */
  public String getCheckText() {
    return check.getText();
  }

  /**
   * 入力済みの文字数を返します。
   * @return 入力済みの文字数
   */
  public int getInputedLength(){
    return String.valueOf(getValueCombo().getEditor().getItem()).length();
  }


  /**
   * コンストラクタです。
   */
  public IkenshoCareStatusContainer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }
    check.addItemListener(new ACFollowDisabledItemListener(new JComponent[]{value, heses}));
  }

  /**
   * コンポーネントを初期化します。
   * @throws Exception 処理例外
   */
  private void jbInit() throws Exception {
    this.setLayout(new VRLayout());
    this.setContentAreaFilled(true);
    this.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    check.setOpaque(false);
    check.setPreferredSize(new Dimension(311, 14));
    value.setEnabled(false);
    value.setIMEMode(InputSubset.KANJI);
    value.setMaxLength(30);
    heses.setEnabled(false);
    heses.setOpaque(false);
    values.setLayout(new BorderLayout());
    values.add(value, BorderLayout.CENTER);
    heses.add(values, VRLayout.CLIENT);
    this.add(check, VRLayout.WEST);
    this.add(heses, VRLayout.CLIENT);
  }

}
