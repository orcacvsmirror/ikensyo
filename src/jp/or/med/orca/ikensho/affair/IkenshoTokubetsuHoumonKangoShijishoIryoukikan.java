package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;
import java.awt.im.InputSubset;

import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoACTextArea;

// [ID:0000514][Tozo TANAKA] 2009/09/09 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoTokubetsuHoumonKangoShijishoIryoukikan extends
        IkenshoHoumonKangoShijishoIryoukikan {
    
    protected ACLabelContainer tokubertsuKinkyuRenrakuContainer;
    protected IkenshoACTextArea tokubertsuKinkyuRenraku;

    public IkenshoTokubetsuHoumonKangoShijishoIryoukikan() {
        super();
        try {
            jbInit();
          }
          catch (Exception ex) {
            ex.printStackTrace();
          }
    }

    private void jbInit() throws Exception {

        otherStationSijiUmuContainer.setVisible(false);
        otherStationSijiContainer.setVisible(false);
        
        //[ID:0000731][Shin Fujihara] 2012/04/20 add begin �y2012�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        kyuinStationSijiUmuContainer.setVisible(false);
        kyuinStationSijiContainer.setVisible(false);
        //[ID:0000731][Shin Fujihara] 2012/04/20 add end �y2009�N�x�Ή��F�K��Ō�w�����z����z���w���ǉ�
        
        getTokubertsuKinkyuRenraku().setColumns(100);
        getTokubertsuKinkyuRenraku().setRows(2);
        getTokubertsuKinkyuRenraku().setMaxRows(2);
        getTokubertsuKinkyuRenraku().setMaxLength(200);
        getTokubertsuKinkyuRenraku().setLineWrap(true);
        getTokubertsuKinkyuRenraku().setIMEMode(InputSubset.KANJI);
        getTokubertsuKinkyuRenraku().setBindPath("TOKUBETSU_KINKYU_RENRAKU");
        getTokubertsuKinkyuRenraku().fitTextArea();
        getTokubertsuKinkyuRenrakuContainer().setText("�@�@�@");

        
        getTokubertsuKinkyuRenrakuContainer().add(getTokubertsuKinkyuRenraku(), VRLayout.FLOW);
        
    }
    protected void addFollowDoctorContainer(){
        getFollowDoctorContainer().add(getIryoukikanHeader(), VRLayout.NORTH);
        getFollowDoctorContainer().add(getKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getTokubertsuKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getFuzaijiTaiouContainer(), VRLayout.FLOW_INSETLINE_RETURN);
    }

    /**
     * tokubertsuKinkyuRenraku ��Ԃ��܂��B
     * @return tokubertsuKinkyuRenraku
     */
    protected IkenshoACTextArea getTokubertsuKinkyuRenraku() {
        if(tokubertsuKinkyuRenraku==null){
            tokubertsuKinkyuRenraku = new IkenshoACTextArea();
        }
        return tokubertsuKinkyuRenraku;
    }

    /**
     * tokubertsuKinkyuRenrakuContainer ��Ԃ��܂��B
     * @return tokubertsuKinkyuRenrakuContainer
     */
    protected ACLabelContainer getTokubertsuKinkyuRenrakuContainer() {
        if(tokubertsuKinkyuRenrakuContainer==null){
            tokubertsuKinkyuRenrakuContainer = new ACLabelContainer();
        }
        return tokubertsuKinkyuRenrakuContainer;
    }
    
    
    protected boolean changeDoctor(ItemEvent e) {
        //�o�C���h�Ώۂ���ꎞ�I�ɏ��O����B
        VRBindSource oldSource = getTokubertsuKinkyuRenraku().getSource();
        getTokubertsuKinkyuRenraku().setSource(null);
        boolean returnValue= super.changeDoctor(e);
        getTokubertsuKinkyuRenraku().setSource(oldSource);
        
        return returnValue;
    }    
}
//[ID:0000514][Tozo TANAKA] 2009/09/09 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
