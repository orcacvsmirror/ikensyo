package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoMindBody1H18 extends IkenshoIkenshoInfoMindBody1 {
  private VRListModelAdapter unExistEmptyListModel = new VRListModelAdapter(new
      VRArrayList(Arrays.asList(new String[] {"無", "有"})));

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoMindBody1H18() {
    try {
     jbInit();
   }
   catch (Exception e) {
     e.printStackTrace();
   }
 }
 private void jbInit() throws Exception {
   getSyougaiRoujinJiritsu().setText("障害高齢者の日常生活自立度" +
                                     IkenshoConstants.LINE_SEPARATOR +
                                     "（寝たきり度）");
   getChihouRoujinJiritsu().setText("認知症高齢者の日常生活自立度");
   getRikaiKiokuGroup().setText("認知症の中核症状（認知症以外の疾患で同様の症状を認める場合を含む）");
   getMondaiGroup().setText("認知症の周辺症状（認知症以外の疾患で同様の症状を認める場合を含む）");
   getShinkeiGroup().setText("その他の精神・神経症状");
   getMindBody1SyougaiJiritsu().setModel(new VRListModelAdapter(new
           VRArrayList(Arrays.asList(new
                                     String[] {"自立", "Ｊ１", "Ｊ２", "Ａ１", "Ａ２", "Ｂ１",
                                     "Ｂ２", "Ｃ１", "Ｃ２"}))));
   getMindBody1ChihouJiritsu().setModel(new VRListModelAdapter(new
           VRArrayList(Arrays.asList(new
                                     String[] {"自立", "I", "IIａ", "IIｂ", "IIIａ", "IIIｂ",
                                     "IV", "Ｍ"}))));

   getRikaiKiokuClearButton().setToolTipText("「認知症の中核症状」の全項目の選択を解除します。");

   getMindBody1HasMondai().setModel(unExistEmptyListModel);
   getMindBody1HasMondai().setValues(new int[]{2,1});
   getMindBody1HasShinkei().setModel(unExistEmptyListModel);
   getMindBody1HasShinkei().setValues(new int[]{2,1});

   getRikaiKiokuSyokuji().setVisible(false);
 }
 protected void addRikaiKiokuClearButton(){
   getRikaiKiokuDentatsuHelpPanel().add(getRikaiKiokuClearButton(), null);
 }

 protected String getProblemActionCaption() {
   return "認知症の周辺症状";
 }

 protected int getFomratKubun(){
   return 1;
 }

 protected void addMindBody1HasShinkei(){
   getMindBody1HasShinkei().add(getMindBody1Shinkei(), null);
 }

//2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応 ACComboBox⇒IkenshoOptionComboBox
    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        
        getMindBody1Shinkei().setOptionComboBoxParameters("精神・神経症状",
                IkenshoCommon.TEIKEI_MIND_SICK_NAME, 30);

    }
//  2007/10/18 [Masahiko Higuchi] Addition - end

}
