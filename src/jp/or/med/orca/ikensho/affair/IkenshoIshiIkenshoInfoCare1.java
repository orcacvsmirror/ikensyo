package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoCare1です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoCare1 extends IkenshoIkenshoInfoCare1H18 {
    /**
     * コンストラクタです。
     */
    public IkenshoIshiIkenshoInfoCare1() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * 画面構成に関する処理を行います。
     */
    private void jbInit(){
        // タイトル設定
        getTitle().setText("４．サービス利用に関する意見");
        // 追加順序定義
        // グループ項目名設定
        getStateAndTaisyoGroup().setText("現在、発生の可能性が高い病態とその対処方針");
        getHaien().setVisible(true);
        getChouheisoku().setVisible(true);
        getPain().setVisible(true);
        
    }
    
    /**
     * 状態と対処方針の追加順序を定義します。
     */
    protected void addStateTaisyos() {
        // 尿失禁
        getStateAndTaisyoGroup().add(getShikkin(), VRLayout.FLOW_INSETLINE_RETURN);
        // 転倒・骨折
        getStateAndTaisyoGroup().add(getTentou(), VRLayout.FLOW_INSETLINE_RETURN);
        // 徘徊
        getStateAndTaisyoGroup().add(getHaikai(), VRLayout.FLOW_INSETLINE_RETURN);
        // 褥瘡
        getStateAndTaisyoGroup().add(getJyokusou(), VRLayout.FLOW_INSETLINE_RETURN);
        // 嚥下性肺炎
        getStateAndTaisyoGroup().add(getHaien(), VRLayout.FLOW_INSETLINE_RETURN);
        // 腸閉塞
        getStateAndTaisyoGroup().add(getChouheisoku(),VRLayout.FLOW_INSETLINE_RETURN);
        // 易感染性
        getStateAndTaisyoGroup().add(getEkikan(), VRLayout.FLOW_INSETLINE_RETURN);
        // 心肺機能の低下
        getStateAndTaisyoGroup().add(getShinpaiDown(),VRLayout.FLOW_INSETLINE_RETURN);
        // 痛み
        getStateAndTaisyoGroup().add(getPain(), VRLayout.FLOW_INSETLINE_RETURN);
        // 脱水
        getStateAndTaisyoGroup().add(getDassui(), VRLayout.FLOW_INSETLINE_RETURN);
        // その他
        getStateAndTaisyoGroup().add(getOthers(), VRLayout.FLOW_INSETLINE_RETURN);

      }
    
    protected int getInputedLength() {
        int total = 0;
        if (getShikkin().isChecked()) {
            total += getShikkin().getInputedLength();
        }
        if (getTentou().isChecked()) {
            total += getTentou().getInputedLength();
        }
        if (getHaikai().isChecked()) {
            total += getHaikai().getInputedLength();
        }
        if (getJyokusou().isChecked()) {
            total += getJyokusou().getInputedLength();
        }
        if (getHaien().isChecked()) {
            total += getHaien().getInputedLength();
        }
        if (getChouheisoku().isChecked()) {
            total += getChouheisoku().getInputedLength();
        }
        if (getEkikan().isChecked()) {
            total += getEkikan().getInputedLength();
        }
        if (getShinpaiDown().isChecked()) {
            total += getShinpaiDown().getInputedLength();
        }
        if (getPain().isChecked()) {
            total += getPain().getInputedLength();
        }
        if (getDassui().isChecked()) {
            total += getDassui().getInputedLength();
        }
        if (getOther().isSelected()) {
            total += String.valueOf(getOtherTaisyo().getEditor().getItem())
                    .length();
        }
        return total;
    }
    
    

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      super.initDBCopmponent(dbm);

      applyPoolTeikeibun(getCare1Shikkin().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_URINE_NAME);
      applyPoolTeikeibun(getCare1Tentou().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_FACTURE_NAME);
      applyPoolTeikeibun(getCare1Haikai().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PROWL_NAME);
      applyPoolTeikeibun(getCare1Jyokusou().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_JYOKUSOU_NAME);
      applyPoolTeikeibun(getCare1Haien().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME);
      applyPoolTeikeibun(getCare1Chouheisoku().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME);
      applyPoolTeikeibun(getCare1Ekikan().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_INFECTION_NAME);
      applyPoolTeikeibun(getCare1ShinpaiDown().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_HEART_LUNG_NAME);
      applyPoolTeikeibun(getCare1Pain().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_PAIN_NAME);
      applyPoolTeikeibun(getCare1Dassui().getValueCombo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_DEHYDRATION_NAME);
      applyPoolTeikeibun(getCare1OtherTaisyo(), IkenshoCommon.TEIKEI_ISHI_SICK_COPE_OTHER_NAME);
      applyPoolTeikeibun(getCare1OtherName(), IkenshoCommon.TEIKEI_ISHI_SICK_TYPE_OTHER_NAME);
    }

}
