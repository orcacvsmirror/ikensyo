package jp.or.med.orca.ikensho.affair;

import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIkenshoInfoSpecial extends IkenshoDocumentAffairSpecial {

  /**
   * コンストラクタです。
   */
  public IkenshoIkenshoInfoSpecial() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }

  private void jbInit() throws Exception {
    specialTitle.setText("２．特別な医療（過去14日間以内に受けた医療のすべてにチェック）");
    specialMessage1.setVisible(true);
    specialMessage2.setVisible(true);
 }

    // 2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応
    // ACComboBox⇒IkenshoOptionComboBox
    /**
     * コンボへの定型文設定などDBへのアクセスを必要とする初期化処理を生成します。
     * 
     * @param dbm DBManager
     * @throws Exception 処理例外
     * @since 3.0.5
     * @author Masahiko Higuchi
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);

        getSpecialCannulaSize().setOptionComboBoxParameters("気管カニューレサイズ",
                IkenshoCommon.TEIKEI_CANURE_SIZE, 5);
        getSpecialDorenPos().setOptionComboBoxParameters("ドレーン部位",
                IkenshoCommon.TEIKEI_DOREN_POS_NAME, 10);
        getSpecialRyuchiCatheterChange().setOptionComboBoxParameters(
                "留置カテーテル交換", IkenshoCommon.TEIKEI_CATHETER_CHANGE_SPAN, 5);
        getSpecialRyuchiCatheterSize().setOptionComboBoxParameters(
                "留置カテーテルサイズ", IkenshoCommon.TEIKEI_CATHETER_SIZE, 5);
        getSpecialKeikanEiyouSize().setOptionComboBoxParameters("経管栄養サイズ",
                IkenshoCommon.TEIKEI_TUBE_SIZE, 5);
        getSpecialKeikanEiyouChange().setOptionComboBoxParameters("経管栄養交換",
                IkenshoCommon.TEIKEI_TUBE_CHANGE_SPAN, 5);
        getSpecialJinkouKokyuHousiki().setOptionComboBoxParameters("人工呼吸器方式",
                IkenshoCommon.TEIKEI_RESPIRATOR_TYPE, 5);
        getSpecialJinkouKokyuSettei().setOptionComboBoxParameters("人工呼吸器設定",
                IkenshoCommon.TEIKEI_RESPIRATOR_SETTING, 10);
        getSpecialKeikanEiyouMethod().setOptionComboBoxParameters("経管栄養方法",
                IkenshoCommon.TEIKEI_TUBE_TYPE, 5);
        
        //[ID:0000688][Shin Fujihara] 2012/03/12 Addition - start
        //留置カテーテルの部位コンボ追加
        getSpecialRyuchiCatheterPos().setOptionComboBoxParameters("留置カテーテル部位", IkenshoCommon.TEIKEI_CATHETER_POS_NAME, 10);
        //[ID:0000688][Shin Fujihara] 2012/03/12 Addition - end

    }
    // 2007/10/18 [Masahiko Higuchi] Addition - end
  
}
