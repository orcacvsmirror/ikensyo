package jp.or.med.orca.ikensho.affair;

import java.util.Arrays;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;

//[ID:0000515][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ�   
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
        getTitle().setText("�T�D���̑����L���ׂ�����");
        
        
        getKaigoHitsuyodoHenkaContainer().setText("�O��̗v���x�ɂ�����厡��ӌ����쐬���_�Ɣ�r�����w���̕K�v�x�x");
        getKaigoHitsuyodoHenka().setBindPath("KAIGO_HITSUYODO_HENKA");
        getKaigoHitsuyodoHenka().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "����", "�ω��Ȃ�", "����" }))));
        getKaigoHitsuyodoHenkaContainer().add(getKaigoHitsuyodoHenka());
        
        getInsurerInputCommentLabel().setText("�ȉ��̍��ڂ́A�s�����ɂ���đΉ����قȂ�܂��B");
        getInsurerInputCommentLabel().setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        
        getNinteiJohoTeikyoKiboContainer().setText("�v���F�茋�ʂ̏��񋟂���]");
        getNinteiJohoTeikyoKibo().setBindPath("NINTEI_JOHO_KIBO");
        getNinteiJohoTeikyoKibo().setModel(
                new VRListModelAdapter(new VRArrayList(Arrays
                        .asList(new String[] { "����", "���Ȃ�" }))));
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
     * kaigoHitsuyodoHenka ��Ԃ��܂��B
     * @return kaigoHitsuyodoHenka
     */
    protected ACClearableRadioButtonGroup getKaigoHitsuyodoHenka() {
        if(kaigoHitsuyodoHenka==null){
            kaigoHitsuyodoHenka = new ACClearableRadioButtonGroup();
        }
        return kaigoHitsuyodoHenka;
    }

    /**
     * kaigoHitsuyodoHenkaContainer ��Ԃ��܂��B
     * @return kaigoHitsuyodoHenkaContainer
     */
    protected ACLabelContainer getKaigoHitsuyodoHenkaContainer() {
        if(kaigoHitsuyodoHenkaContainer==null){
            kaigoHitsuyodoHenkaContainer = new ACLabelContainer();
        }
        return kaigoHitsuyodoHenkaContainer;
    }

    /**
     * ninteiJohoTeikyoKibo ��Ԃ��܂��B
     * @return ninteiJohoTeikyoKibo
     */
    protected ACClearableRadioButtonGroup getNinteiJohoTeikyoKibo() {
        if(ninteiJohoTeikyoKibo==null){
            ninteiJohoTeikyoKibo = new ACClearableRadioButtonGroup();
        }
        return ninteiJohoTeikyoKibo;
    }

    /**
     * ninteiJohoTeikyoKiboContainer ��Ԃ��܂��B
     * @return ninteiJohoTeikyoKiboContainer
     */
    protected ACLabelContainer getNinteiJohoTeikyoKiboContainer() {
        if(ninteiJohoTeikyoKiboContainer==null){
            ninteiJohoTeikyoKiboContainer = new ACLabelContainer();
        }
        return ninteiJohoTeikyoKiboContainer;
    }

    /**
     * insurerInputCommentLabel ��Ԃ��܂��B
     * @return insurerInputCommentLabel
     */
    public ACLabel getInsurerInputCommentLabel() {
        if(insurerInputCommentLabel == null) {
            insurerInputCommentLabel = new ACLabel();
        }
        return insurerInputCommentLabel;
    }


}
// [ID:0000515][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�厡��ӌ����z�s�����Ǝ����ڂ̈󎚂ɑΉ� 
