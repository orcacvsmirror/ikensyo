package jp.or.med.orca.ikensho.affair;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoACTextArea;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoIryoukikan extends IkenshoHoumonKangoShijishoIryoukikan {

    protected ACLabelContainer jyohouSyudanContainer;
    private IkenshoOptionComboBox jyohouSyudan;

    /**
     * �R���X�g���N�^
     */
    public IkenshoSeishinkaHoumonKangoShijishoIryoukikan() {
        super();
        try {
            jbInit();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ��������
     */
    private void jbInit() throws Exception {
        // ���X�e�[�V�����ւ̎w���A����z���w���̔�\��
        otherStationSijiUmuContainer.setVisible(false);
        otherStationSijiContainer.setVisible(false);
        kyuinStationSijiUmuContainer.setVisible(false);
        kyuinStationSijiContainer.setVisible(false);
        // �厡��Ƃ̏������̎�i
        getJyohouSyudanContainer().setText("�������̎�i");
        getJyohouSyudanContainer().add(getJyohouSyudan(), null);
        getJyohouSyudan().setMaxLength(40);
        getJyohouSyudan().setColumns(40);
        getJyohouSyudan().setIMEMode(InputSubset.KANJI);
        getJyohouSyudan().setBindPath("JYOUHOU_SYUDAN");
        addInnerBindComponent(getJyohouSyudan());
    }

    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(getJyohouSyudan(), IkenshoCommon.TEIKEI_JYOUHOU_SYUDAN);
        getJyohouSyudan().setOptionComboBoxParameters("�������̎�i", IkenshoCommon.TEIKEI_JYOUHOU_SYUDAN, 40);
    }

    /**
     * followDoctorContainer�ւ̒ǉ����`���܂��B
     */
    protected void addFollowDoctorContainer(){
        getFollowDoctorContainer().add(getIryoukikanHeader(), VRLayout.NORTH);
        getFollowDoctorContainer().add(getKinkyuRenrakuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getFuzaijiTaiouContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getFollowDoctorContainer().add(getJyohouSyudanContainer(), VRLayout.FLOW_INSETLINE_RETURN);
    }

    /**
     * jyohouSyudan ��Ԃ��܂��B
     * @return jyohouSyudan
     */
    protected IkenshoOptionComboBox getJyohouSyudan() {
        if(jyohouSyudan == null){
            jyohouSyudan = new IkenshoOptionComboBox();
        }
        return jyohouSyudan;
    }

    /**
     * jyohouSyudanContainer ��Ԃ��܂��B
     * @return jyohouSyudanContainer
     */
    protected ACLabelContainer getJyohouSyudanContainer() {
        if(jyohouSyudanContainer == null){
            jyohouSyudanContainer = new ACLabelContainer();
        }
        return jyohouSyudanContainer;
    }

    /**
     * ��t�ύX������
     * @return ��t�ύX����������
     */
    protected boolean changeDoctor(ItemEvent e) {
        //�o�C���h�Ώۂ���ꎞ�I�ɏ��O����B
        VRBindSource oldSource = getJyohouSyudan().getSource();
        getJyohouSyudan().setSource(null);
        boolean returnValue = super.changeDoctor(e);
        getJyohouSyudan().setSource(oldSource);

        return returnValue;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End