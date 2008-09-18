package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.event.ACFollowDisableSelectionListener;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoCare2です。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/10
 */
public class IkenshoIshiIkenshoInfoCare2 extends IkenshoIkenshoInfoCare2H18 {

    public IkenshoIshiIkenshoInfoCare2() {
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
        // タイトルラベル設定
        getTitle().setText("４．サービス利用に関する意見（続き）");
        
        getKansenGroup().setText("感染症の有無（有の場合は具体的に記入して下さい）（30文字以内）");
        getServiceGroup().setText("介護サービス（ホームヘルプサービス等）の利用時に関する医学的観点からの留意事項（30文字以内。「その他」は50文字）");
        getCare2Kansen().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] {"有", "無", "不明" }))));
        getCare2Kansen().setValues(new int[] { 1, 2, 3 });

        // 画面項目を定義
        addGroup();
    }
    
    /**
     * overrideしてパネルへ追加する項目を定義します。
     */
    protected void addGroup() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getServiceGroup(), VRLayout.NORTH);
        this.add(getKansenGroup(), VRLayout.NORTH);
    }
    
    /**
     * overrideして医学的観点からの留意事項の追加順序を定義します。
     */
    protected void addRyuijiko() {
        getServiceGroup().add(getKetsuattsus(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getEnges(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getSesshokus(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getMoves(), VRLayout.FLOW_INSETLINE_RETURN);
        getServiceGroup().add(getServiceOthers(), VRLayout.FLOW_RETURN);
    }
    

    protected void setCare2KanseEventListener() {
        getCare2Kansen().addListSelectionListener(
                new ACFollowDisableSelectionListener(
                        new JComponent[] { getCare2KansenName() }, 0));
    }

    protected void addCare2Kansen() {
        getCare2Kansen().add(getCare2KansenName(), null, 1);
    }


    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        
        applyPoolTeikeibun(getCare2KetsuattsuValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME);
        applyPoolTeikeibun(getCare2EngeValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME);
        applyPoolTeikeibun(getCare2SesshokuValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_EAT_NAME);
        applyPoolTeikeibun(getCare2MoveValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME);
        applyPoolTeikeibun(getCare2ServiceOtherValue(),
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME);
        applyPoolTeikeibun(getCare2KansenName(),
                IkenshoCommon.TEIKEI_ISHI_INFECTION_NAME);
        

        // 2007/10/18 [Masahiko Higuchi] Addition - begin 業務遷移コンボ対応
        // ACComboBox⇒IkenshoOptionComboBox
        getCare2KetsuattsuValue().setOptionComboBoxParameters("血圧",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME, 30);
        getCare2EngeValue().setOptionComboBoxParameters("嚥下",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME, 30);
        getCare2SesshokuValue().setOptionComboBoxParameters("摂食",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_EAT_NAME, 30);
        getCare2MoveValue().setOptionComboBoxParameters("移動",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME, 30);
        getCare2ServiceOtherValue().setOptionComboBoxParameters("その他",
                IkenshoCommon.TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME, 50);
        getCare2KansenName().setOptionComboBoxParameters("有の場合",
                IkenshoCommon.TEIKEI_ISHI_INFECTION_NAME, 30);
        //      2007/10/18 [Masahiko Higuchi] Addition - end
        
        
        
      }

}
