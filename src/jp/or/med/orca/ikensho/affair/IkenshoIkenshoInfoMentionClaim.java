package jp.or.med.orca.ikensho.affair;

import jp.nichicom.vr.layout.VRLayout;

//[ID:0000515][Tozo TANAKA] 2009/09/09 add begin y2009”N“x‘Î‰Få¡ˆãˆÓŒ©‘zs’¬‘º“Æ©€–Ú‚Ìˆóš‚É‘Î‰   
public class IkenshoIkenshoInfoMentionClaim extends
        IkenshoIkenshoInfoMentionH18 {

    public IkenshoIkenshoInfoMentionClaim() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        getTitle().setText("¿‹");
    }

    protected void addComponent() {
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getMentionSeikyushoGroup(), VRLayout.NORTH);

    }

}
// [ID:0000515][Tozo TANAKA] 2009/09/09 add end y2009”N“x‘Î‰Få¡ˆãˆÓŒ©‘zs’¬‘º“Æ©€–Ú‚Ìˆóš‚É‘Î‰ 
