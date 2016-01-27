package jp.or.med.orca.ikensho.component;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.ListModel;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;

/** TODO <HEAD_IKENSYO> */
public class IkenshoBodyStatusContainer
    extends VRLabelContainer {
  private ACClearableRadioButtonGroup rank = new
      ACClearableRadioButtonGroup();
  private ACIntegerCheckBox check = new
      ACIntegerCheckBox();
  // 2007/10/18 [Masahiko Higuchi] Replace - begin 業務遷移コンボ対応
    // ACComboBox⇒IkenshoOptionComboBox
    private IkenshoOptionComboBox pos = new IkenshoOptionComboBox();
    // 2007/10/18 [Masahiko Higuchi] end
  private VRLabelContainer poss = new VRLabelContainer();
  private VRLabelContainer ranks = new VRLabelContainer();
  private ACParentHesesPanelContainer heses = new ACParentHesesPanelContainer();

  /**
   * 部位コンテナの表示可否を返します。
   * @param posVisible 部位コンテナの表示可否
   */
 public void setPosVisible(boolean posVisible){
   poss.setVisible(posVisible);
 }
 /**
  * 部位コンテナの表示可否を返します。
  * @return 部位コンテナの表示可否
  */
  public boolean isPosVisible(){
    return poss.isVisible();
  }

  /**
   * 程度コンテナの表示可否を返します。
   * @param rankVisible 程度コンテナの表示可否
   */
 public void setRankVisible(boolean rankVisible){
   ranks.setVisible(rankVisible);
 }

  /**
   * 程度コンテナの表示可否を返します。
   * @return 程度コンテナの表示可否
   */
  public boolean getRankVisible(){
    return ranks.isVisible();
  }


  /**
   * ラジオグループを返します。
   * @return ラジオグループ
   */
  public ACClearableRadioButtonGroup getRadioGroup() {
    return rank;
  }

  /**
   * チェックボックスを返します。
   * @return チェックボックス
   */
  public ACIntegerCheckBox getCheckBox() {
    return check;
  }

  /**
   * コンボボックスを返します。
   * @return コンボボックス
   * @since 3.0.5
   */
  public IkenshoOptionComboBox getComboBox() {
    return pos;
  }

  /**
   *
   * 程度のバインドパスを設定します。
   * @param bindPath バインドパス
   */
  public void setRankBindPath(String bindPath) {
    rank.setBindPath(bindPath);
  }

  /**
   * 程度のバインドパスを返します。
   * @return バインドパス
   */
  public String getRankBindPath() {
    return rank.getBindPath();
  }

  /**
   * 部位のバインドパスを設定します。
   * @param bindPath バインドパス
   */
  public void setPosBindPath(String bindPath) {
    pos.setBindPath(bindPath);
  }

  /**
   * 部位のバインドパスを返します。
   * @return バインドパス
   */
  public String getPosBindPath() {
    return pos.getBindPath();
  }

  /**
   * チェックのバインドパスを設定します。
   * @param bindPath バインドパス
   */
  public void setCheckBindPath(String bindPath) {
    check.setBindPath(bindPath);
  }

  /**
   * チェックのバインドパスを返します。
   * @return バインドパス
   */
  public String getCheckBindPath() {
    return check.getBindPath();
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
   * 部位のコンボを返します。
   * @return コンボ
   */
  public JComboBox getPosCombo() {
    return pos;
  }

  /**
   * 部位のモデルを設定します。
   * @param model モデル
   */
  public void setPosModel(ComboBoxModel model) {
    pos.setModel(model);
  }

  /**
   * 部位のモデルを返します。
   * @return モデル
   */
  public ComboBoxModel getPosModel() {
    return pos.getModel();
  }

  /**
   * 程度のモデルを設定します。
   * @param model モデル
   */
  public void setRankModel(ListModel model) {
    rank.setModel(model);
  }

  /**
   * 程度のモデルを返します。
   * @return モデル
   */
  public ListModel getRankModel() {
    return rank.getModel();
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
   * 親の有効状態変化に追随します。
   * @param enabled 親の有効状態
   */
  public void followParentEnabled(boolean enabled){
    boolean childEnabled;
    if(enabled){
      //親は有効 → 選択状態に依存
      check.setEnabled(true);
      childEnabled = check.isSelected();
    }else{
      //親は無効 → 一律無効
      check.setEnabled(false);
      childEnabled = false;
    }

    heses.setEnabled(childEnabled);
    poss.setEnabled(childEnabled);
    pos.setEnabled(childEnabled);
    ranks.setEnabled(childEnabled);
    rank.setEnabled(childEnabled);
    rank.getClearButton().setEnabled(childEnabled);
  }

  /**
   * コンストラクタです。
   */
  public IkenshoBodyStatusContainer() {
    try {
      jbInit();
    }
    catch (Exception e) {
      e.printStackTrace();
    }

    check.addItemListener(new ACFollowDisabledItemListener(new JComponent[] {
        rank.getClearButton(), rank, pos, poss, ranks, heses}));

  }

  private void jbInit() throws Exception {
    rank.setModel(new VRListModelAdapter(new
                                         VRArrayList(Arrays.asList(new String[] {
        "軽", "中", "重"}))));
    rank.setEnabled(false);
    check.setOpaque(false);
//    check.setPreferredSize(new Dimension(150, 23));
    check.setPreferredSize(new Dimension(210, 23));
    pos.setMaxLength(10);
    pos.setEnabled(false);
    pos.setIMEMode(InputSubset.KANJI);
    poss.setEnabled(false);
    poss.setText("部位：");
    poss.setColumns(15);
    ranks.setEnabled(false);
    ranks.setText("程度：");
    ranks.setColumns(20);
    rank.getClearButton().setEnabled(false);
    this.setLayout(new BorderLayout());
    this.setContentAreaFilled(true);
    this.setFocusBackground(IkenshoConstants.COLOR_BACK_PANEL_BACKGROUND);
    heses.setEnabled(false);
    heses.setOpaque(false);
    ( (VRLayout) heses.getLayout()).setAutoWrap(false);
    this.add(heses, BorderLayout.CENTER);
    heses.add(poss, null);
    poss.add(pos, null);
    heses.add(ranks, null);
    ranks.add(rank, null);
    this.add(check, BorderLayout.WEST);
    
  }

}
