package jp.or.med.orca.ikensho.affair;

import java.awt.Color;
import java.util.Iterator;

import javax.swing.JPanel;
import javax.swing.JTextField;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoDocumentTabTitleLabel;
import jp.or.med.orca.ikensho.component.IkenshoHoumonKangoShijishoInstructContainer;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

//[ID:0000514][Masahiko Higuchi] 2009/09/08 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
public class IkenshoTokubetsuHoumonKangoShijishoShojoShuso extends
        IkenshoTabbableChildAffairContainer {

    protected IkenshoHoumonKangoShijishoInstructContainer tokubetsuShuso;
    protected ACGroupBox tokubetsuShusoGroup;
    protected IkenshoDocumentTabTitleLabel shojoShusoTitle;
    
    //[ID:0000687][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��F�u�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�v�ǉ��z
    protected ACLabel adviceLabel1;
//    protected JTextField adviceText2;
    protected ACTextField adviceText2;
    protected ACLabel adviceLabel3;
    protected ACLabel adviceLabel4;
    //[ID:0000687][Shin Fujihara] 2012/03/12 add end
    
    public IkenshoTokubetsuHoumonKangoShijishoShojoShuso() {
        super();
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     * ��ʍ\�z����
     * 
     * @throws Exception
     */
    private void jbInit()throws Exception{
        this.setLayout(new VRLayout());
        getShojoShusoTitle().setText("�Ǐ�E��i");
        
        
        getTokubetsuShuso().setColumns(100);
        getTokubetsuShuso().setRows(14);
        getTokubetsuShuso().setMaxRows(14);
        getTokubetsuShuso().setMaxLength(700);
        getTokubetsuShuso().setShowSelectVisible(false);
        getTokubetsuShuso().setCheckVisible(false);
        getTokubetsuShuso().setTextBindPath("TOKUBETSU_SHOJO_SHUSO");
        getTokubetsuShuso().setCaption("�Ǐ�E��i");
        getTokubetsuShuso().setTitle("�Ǐ�E��i�i700����/14�s�ȓ��j");
        getTokubetsuShuso().fitTextArea();
        //[ID:0000687][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��F�u�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�v�ǉ��z
        VRLayout layout = new VRLayout();
        layout.setHgap(4);
        JPanel advicePanel = new JPanel(layout);
        advicePanel.add(getAdviceLabel1());
        advicePanel.add(getAdviceText2());
        advicePanel.add(getAdviceLabel3(), VRLayout.FLOW_RETURN);
        advicePanel.add(getAdviceLabel4()); 
        //[ID:0000687][Shin Fujihara] 2012/03/12 add end
        
        getTokubetsuShusoGroup().add(getTokubetsuShuso(), VRLayout.NORTH);
        //[ID:0000687][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��F�u�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�v�ǉ��z
        getTokubetsuShusoGroup().add(advicePanel, VRLayout.NORTH);
        //[ID:0000687][Shin Fujihara] 2012/03/12 add end
        
        this.add(getShojoShusoTitle(), VRLayout.NORTH);
        this.add(getTokubetsuShusoGroup(), VRLayout.CLIENT);
        
    }
    
    /**
     * tokubetsuShuso ��Ԃ��܂��B
     * @return tokubetsuShuso
     */
    protected IkenshoHoumonKangoShijishoInstructContainer getTokubetsuShuso() {
        if(tokubetsuShuso==null){
            tokubetsuShuso = new IkenshoHoumonKangoShijishoInstructContainer();
        }
        return tokubetsuShuso;
    }
    
    /**
     * tokubetsuShusoGroup ��Ԃ��܂��B
     * @return tokubetsuShusoGroup
     */
    protected ACGroupBox getTokubetsuShusoGroup() {
        if(tokubetsuShusoGroup==null){
            tokubetsuShusoGroup = new ACGroupBox();
        }
        return tokubetsuShusoGroup;
    }
    
    /**
     * ryuuiShijiTitle ��Ԃ��܂��B
     * 
     * @return ryuuiShijiTitle
     */
    protected IkenshoDocumentTabTitleLabel getShojoShusoTitle() {
        if (shojoShusoTitle == null) {
            shojoShusoTitle = new IkenshoDocumentTabTitleLabel();
        }
        return shojoShusoTitle;
    }
    
    //[ID:0000687][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��F�u�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�v�ǉ��z
    /**
     * �������e�L�X�g<br>
     * [����24�N�x�f�Õ�V����ɂ��A�Ǐ�E��i���ڂɁu]�܂ł̃��x��
     * @return
     */
    protected ACLabel getAdviceLabel1() {
        if (adviceLabel1 == null) {
            adviceLabel1 = new ACLabel();
            adviceLabel1.setText("����24�N�x�f�Õ�V����ɂ��A�Ǐ�E��i���ڂɁu");
        }
        return adviceLabel1;
    }
    
    /**
     * �������e�L�X�g<br>
     * [�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�F]�̃e�L�X�g
     * @return
     */
    protected JTextField getAdviceText2() {
        if (adviceText2 == null) {
//            adviceText2 = new JTextField();
        	adviceText2 = new ACTextField();
            adviceText2.setOpaque(false );
            adviceText2.setBorder(null);
            adviceText2.setForeground(Color.red);
            adviceText2.setEditable(false);
            
            adviceText2.setText("�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�F");
        }
        return adviceText2;
    }
    
    /**
     * �������e�L�X�g<br>
     * [�v���ǉ�����܂����B]�̃��x��
     * @return
     */
    protected ACLabel getAdviceLabel3() {
        if (adviceLabel3 == null) {
            adviceLabel3 = new ACLabel();
            adviceLabel3.setText("�v���ǉ�����܂����B");
            
        }
        return adviceLabel3;
    }
    
    /**
     * �������e�L�X�g<br>
     * [�K�v�ɉ����ċL������悤�ɂ��Ă��������B]�̃��x��
     * @return
     */
    protected ACLabel getAdviceLabel4() {
        if (adviceLabel4 == null) {
            adviceLabel4 = new ACLabel();
            adviceLabel4.setText("�K�v�ɉ����ċL������悤�ɂ��Ă��������B");
        }
        return adviceLabel4;
    }
    //[ID:0000687][Shin Fujihara] 2012/03/12 add end
    
    
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        getTokubetsuShuso().setLoadRecentSetting(
                dbm,
                getMasterAffair().getPatientNo(),
                "MT_STS",
                "0",
                "�Ǎ�(L)",
                'L',
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
//                "�ŐV�̖K��Ō�w�����ɓo�^�����u�Ǐ�E���Ï�ԁv��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
                "�ŐV�́i���_�ȁj�K��Ō�w�����ɓo�^�����u�Ǐ�E���Ï�ԁv��ǂݍ��݂܂��B" + ACConstants.LINE_SEPARATOR
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
                + "��낵���ł����H", true);
        
    }
    
    //[ID:0000687][Shin Fujihara] 2012/03/12 add begin �y2012�N�x�Ή��F�u�ꎞ�I�ɖK��Ō삪�p��ɕK�v�ȗ��R�v�ǉ��z
    protected void bindSourceInnerBindComponent() throws Exception {
        super.bindSourceInnerBindComponent();
        
        //�V�K�쐬���[�h�̎�
        if (IkenshoConstants.AFFAIR_MODE_INSERT.equals(getMasterAffair().getNowMode())) {
            if (ACTextUtilities.isNullText(getTokubetsuShuso().getText())) {
                StringBuffer sb = new StringBuffer();
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(getAdviceText2().getText());
                
                getTokubetsuShuso().setText(sb.toString());
            }
        }
        
    }
    //[ID:0000687][Shin Fujihara] 2012/03/12 add end

}
//[ID:0000514][Masahiko Higuchi] 2009/09/08 add end