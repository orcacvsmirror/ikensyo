package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;
import java.awt.im.InputSubset;

import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.component.IkenshoACTextArea;

// [ID:0000514][Tozo TANAKA] 2009/09/09 add begin 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
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
        
        //[ID:0000731][Shin Fujihara] 2012/04/20 add begin 【2012年度対応：訪問看護指示書】たん吸引指示追加
        kyuinStationSijiUmuContainer.setVisible(false);
        kyuinStationSijiContainer.setVisible(false);
        //[ID:0000731][Shin Fujihara] 2012/04/20 add end 【2009年度対応：訪問看護指示書】たん吸引指示追加
        
        getTokubertsuKinkyuRenraku().setColumns(100);
        getTokubertsuKinkyuRenraku().setRows(2);
        getTokubertsuKinkyuRenraku().setMaxRows(2);
        getTokubertsuKinkyuRenraku().setMaxLength(200);
        getTokubertsuKinkyuRenraku().setLineWrap(true);
        getTokubertsuKinkyuRenraku().setIMEMode(InputSubset.KANJI);
        getTokubertsuKinkyuRenraku().setBindPath("TOKUBETSU_KINKYU_RENRAKU");
        getTokubertsuKinkyuRenraku().fitTextArea();
        getTokubertsuKinkyuRenrakuContainer().setText("　　　");

        
        getTokubertsuKinkyuRenrakuContainer().add(getTokubertsuKinkyuRenraku(), VRLayout.FLOW);
        
    }
    protected void addFollowDoctorContainer(){
        getFollowDoctorContainer().add(getIryoukikanHeader(), VRLayout.NORTH);
        getFollowDoctorContainer().add(getKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getTokubertsuKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getFuzaijiTaiouContainer(), VRLayout.FLOW_INSETLINE_RETURN);
    }

    /**
     * tokubertsuKinkyuRenraku を返します。
     * @return tokubertsuKinkyuRenraku
     */
    protected IkenshoACTextArea getTokubertsuKinkyuRenraku() {
        if(tokubertsuKinkyuRenraku==null){
            tokubertsuKinkyuRenraku = new IkenshoACTextArea();
        }
        return tokubertsuKinkyuRenraku;
    }

    /**
     * tokubertsuKinkyuRenrakuContainer を返します。
     * @return tokubertsuKinkyuRenrakuContainer
     */
    protected ACLabelContainer getTokubertsuKinkyuRenrakuContainer() {
        if(tokubertsuKinkyuRenrakuContainer==null){
            tokubertsuKinkyuRenrakuContainer = new ACLabelContainer();
        }
        return tokubertsuKinkyuRenrakuContainer;
    }
    
    
    protected boolean changeDoctor(ItemEvent e) {
        //バインド対象から一時的に除外する。
        VRBindSource oldSource = getTokubertsuKinkyuRenraku().getSource();
        getTokubertsuKinkyuRenraku().setSource(null);
        boolean returnValue= super.changeDoctor(e);
        getTokubertsuKinkyuRenraku().setSource(oldSource);
        
        return returnValue;
    }    
}
//[ID:0000514][Tozo TANAKA] 2009/09/09 add end 【2009年度対応：訪問看護指示書】特別指示書の管理機能  
