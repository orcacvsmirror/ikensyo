package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;

//[ID:0000515][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：主治医意見書】市町村独自項目の印字に対応   
public class IkenshoIkenshoInfoMentionSpecial extends
        IkenshoIkenshoInfoMentionH18 {
    protected ACLabelContainer kaigoHitsuyodoHenkaContainer;
    protected ACClearableRadioButtonGroup kaigoHitsuyodoHenka;
    
    protected ACLabel insurerInputCommentLabel;
    protected ACLabelContainer ninteiJohoTeikyoKiboContainer;
    protected ACClearableRadioButtonGroup ninteiJohoTeikyoKibo;

    public IkenshoIkenshoInfoMentionSpecial() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getTitle().setText("５．その他特記すべき事項");
        
        
        getKaigoHitsuyodoHenkaContainer().setText("前回の要介護度における主治医意見書作成時点と比較した『介護の必要度』");
        getKaigoHitsuyodoHenka().setBindPath("KAIGO_HITSUYODO_HENKA");
        getKaigoHitsuyodoHenka().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "減少", "変化なし", "増加" }))));
        getKaigoHitsuyodoHenkaContainer().add(getKaigoHitsuyodoHenka());
        
        getInsurerInputCommentLabel().setText("以下の項目は、市町村によって対応が異なります。");
        getInsurerInputCommentLabel().setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        
        getNinteiJohoTeikyoKiboContainer().setText("要介護認定結果の情報提供を希望");
        getNinteiJohoTeikyoKibo().setBindPath("NINTEI_JOHO_KIBO");
        getNinteiJohoTeikyoKibo().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "する", "しない" }))));
        getNinteiJohoTeikyoKiboContainer().add(getNinteiJohoTeikyoKibo());
        
        
        getMentionTokki().setColumns(94);
        getMentionTokki().setMaxRows(9);
        getMentionTokki().setRows(9);
        getMentionTokki().fitTextArea();
        
    }

    protected void addComponent() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getMentionTokkiGroup(), VRLayout.NORTH);

    }
    protected void addMentionTokkiGroupComponent(){
        getMentionTokkiGroup().add(getMentionTokkiAbstractions(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkis(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkiMoreAbstractions(), VRLayout.NORTH);
        
        getMentionTokkiGroup().add(getKaigoHitsuyodoHenkaContainer(), VRLayout.NORTH);
        
        getMentionTokkiGroup().add(getMentionHasegawas(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionShisetsus(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getInsurerInputCommentLabel(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getNinteiJohoTeikyoKiboContainer(), VRLayout.NORTH);
    }

    /**
     * kaigoHitsuyodoHenka を返します。
     * @return kaigoHitsuyodoHenka
     */
    protected ACClearableRadioButtonGroup getKaigoHitsuyodoHenka() {
        if(kaigoHitsuyodoHenka==null){
            kaigoHitsuyodoHenka = new ACClearableRadioButtonGroup();
        }
        return kaigoHitsuyodoHenka;
    }

    /**
     * kaigoHitsuyodoHenkaContainer を返します。
     * @return kaigoHitsuyodoHenkaContainer
     */
    protected ACLabelContainer getKaigoHitsuyodoHenkaContainer() {
        if(kaigoHitsuyodoHenkaContainer==null){
            kaigoHitsuyodoHenkaContainer = new ACLabelContainer();
        }
        return kaigoHitsuyodoHenkaContainer;
    }

    /**
     * ninteiJohoTeikyoKibo を返します。
     * @return ninteiJohoTeikyoKibo
     */
    protected ACClearableRadioButtonGroup getNinteiJohoTeikyoKibo() {
        if(ninteiJohoTeikyoKibo==null){
            ninteiJohoTeikyoKibo = new ACClearableRadioButtonGroup();
        }
        return ninteiJohoTeikyoKibo;
    }

    /**
     * ninteiJohoTeikyoKiboContainer を返します。
     * @return ninteiJohoTeikyoKiboContainer
     */
    protected ACLabelContainer getNinteiJohoTeikyoKiboContainer() {
        if(ninteiJohoTeikyoKiboContainer==null){
            ninteiJohoTeikyoKiboContainer = new ACLabelContainer();
        }
        return ninteiJohoTeikyoKiboContainer;
    }

    /**
     * insurerInputCommentLabel を返します。
     * @return insurerInputCommentLabel
     */
    public ACLabel getInsurerInputCommentLabel() {
        if(insurerInputCommentLabel == null) {
            insurerInputCommentLabel = new ACLabel();
        }
        return insurerInputCommentLabel;
    }


}
// [ID:0000515][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：主治医意見書】市町村独自項目の印字に対応 
