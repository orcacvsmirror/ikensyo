package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoHintButton;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaHoumonKangoShijishoJyoukyou2 extends IkenshoHoumonKangoShijishoMindBody1 {

    // �a�����m
    protected ACGroupBox byomeiKokutiGrp = new ACGroupBox();
    private ACLabelContainer byomeiKokutiContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup byomeiKokuti = new ACClearableRadioButtonGroup();
    // ���Â̎󂯓���
    protected ACGroupBox chiryoGrp = new ACGroupBox();
    private ACLabelContainer chiryoContainer = new ACLabelContainer();
    private IkenshoOptionComboBox chiryo = new IkenshoOptionComboBox();
    // �������K��̕K�v��
    protected ACGroupBox fukusuHomonGrp = new ACGroupBox();
    private ACLabelContainer fukusuHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup fukusuHomon = new ACClearableRadioButtonGroup();
    // �Z���ԖK��̕K�v��
    protected ACGroupBox tanjikanHomonGrp = new ACGroupBox();
    private ACLabelContainer tanjikanHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup tanjikanHomon = new ACClearableRadioButtonGroup();

    /**
     * �R���X�g���N�^
     */
    public IkenshoSeishinkaHoumonKangoShijishoJyoukyou2() {
        super();
        try {
            jbInit();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * ��������
     */
    private void jbInit() throws Exception {

        getTitle().setText("���݂̏󋵁i�����j");
        this.remove(getJiritsuGroup());
        this.add(byomeiKokutiGrp, VRLayout.NORTH);
        this.add(chiryoGrp, VRLayout.NORTH);
        this.add(fukusuHomonGrp, VRLayout.NORTH);
        this.add(tanjikanHomonGrp, VRLayout.NORTH);
        this.add(getJiritsuGroup(), VRLayout.NORTH);

        // �a�����m
        byomeiKokuti.setBindPath("BYOUMEI_KOKUTI");
        byomeiKokuti.setModel(new VRListModelAdapter(
                new VRArrayList(Arrays.asList(new String[] {"����", "�Ȃ�"}))));
        byomeiKokutiContainer.setLayout(new VRLayout());
        byomeiKokutiContainer.add(byomeiKokuti, VRLayout.FLOW);
        byomeiKokutiGrp.setText("�a�����m");
        byomeiKokutiGrp.setLayout(new BorderLayout());
        byomeiKokutiGrp.add(byomeiKokutiContainer, BorderLayout.WEST);
        // ���Â̎󂯓���
        chiryo.setMaxLength(30);
        chiryo.setColumns(30);
        chiryo.setIMEMode(InputSubset.KANJI);
        chiryo.setBindPath("TIRYOU_UKEIRE");
        chiryoContainer.setLayout(new VRLayout());
        chiryoContainer.add(chiryo, VRLayout.FLOW);
        chiryoGrp.setText("���Â̎󂯓���");
        chiryoGrp.add(chiryoContainer, BorderLayout.WEST);
        // �������K��̕K�v��
        fukusuHomon.setBindPath("FUKUSU_HOUMON");
        fukusuHomon.setModel(new VRListModelAdapter(
                new VRArrayList(Arrays.asList(new String[] {"����", "�Ȃ�"}))));
        fukusuHomonContainer.setLayout(new VRLayout());
        fukusuHomonContainer.add(fukusuHomon, VRLayout.FLOW);
        fukusuHomonGrp.setText("�������K��̕K�v��");
        fukusuHomonGrp.setLayout(new BorderLayout());
        fukusuHomonGrp.add(fukusuHomonContainer, VRLayout.WEST);
        // �Z���ԖK��̕K�v��
        tanjikanHomon.setBindPath("TANJIKAN_HOUMON");
        tanjikanHomon.setModel(new VRListModelAdapter(
                new VRArrayList(Arrays.asList(new String[] {"����", "�Ȃ�"}))));
        tanjikanHomonContainer.setLayout(new VRLayout());
        tanjikanHomonContainer.add(tanjikanHomon, VRLayout.FLOW);
        tanjikanHomonGrp.setText("�Z���ԖK��̕K�v��");
        tanjikanHomonGrp.setLayout(new BorderLayout());
        tanjikanHomonGrp.add(tanjikanHomonContainer, VRLayout.WEST);
        // ��Q�V�l�̓��퐶�������x�A��ጂ̐[���̔�\��
        getSyougaiRoujinJiritsu().setVisible(false);
        getJokusoDepthGroup().setVisible(false);
        // �F�m�Ǎ���҂̓��퐶�������x�̃w���v�{�^���������̘A�����i�̐ݒ�
        getChihouJiritsuHelp().setFollowHideComponents(new JComponent[] { byomeiKokutiGrp, chiryoGrp, });
    }
    
    /**
     * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
     * @param dbm DBManager
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(chiryo, IkenshoCommon.TEIKEI_CARE_RECEIVING);
        chiryo.setOptionComboBoxParameters("���Â̎󂯓���", IkenshoCommon.TEIKEI_CARE_RECEIVING, 30);
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End