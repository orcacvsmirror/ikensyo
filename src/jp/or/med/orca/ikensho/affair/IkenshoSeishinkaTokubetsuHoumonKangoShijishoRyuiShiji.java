package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.im.InputSubset;
import java.util.Arrays;

import javax.swing.JComponent;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.event.ACFollowDisabledItemListener;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start ���_�ȖK��Ō�w�����̒ǉ��Ή�
public class IkenshoSeishinkaTokubetsuHoumonKangoShijishoRyuiShiji extends IkenshoTokubetsuHoumonKangoShijishoRyuiShiji {

    private ACLabel title = new ACLabel();
    // �������K��̕K�v��
    protected ACGroupBox fukusuHomonGrp = new ACGroupBox();
    private ACLabelContainer fukusuHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup fukusuHomon = new ACClearableRadioButtonGroup();
    private ACLabelContainer fukusuHomonRiyuLabel = new ACLabelContainer();
    private IkenshoOptionComboBox fukusuHomonRiyu = new IkenshoOptionComboBox();
    // �Z���ԖK��̕K�v��
    protected ACGroupBox tanjikanHomonGrp = new ACGroupBox();
    private ACLabelContainer tanjikanHomonContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup tanjikanHomon = new ACClearableRadioButtonGroup();
    private ACLabelContainer tanjikanHomonRiyuLabel = new ACLabelContainer();
    private IkenshoOptionComboBox tanjikanHomonRiyu= new IkenshoOptionComboBox();
    // ���Ɋώ@��v���鍀��
    private ACGroupBox kansatuGroup;
    private ACLabelContainer kansatuContainer;
    // �P ����m�F
    private ACLabelContainer fukuyakuConteiner;
    private ACIntegerCheckBox fukuyaku;
    // �Q �����y�ѐH���ێ�̏�
    private ACLabelContainer suibunConteiner;
    private ACIntegerCheckBox suibun;
    // �R ���_�Ǐ�
    private ACLabelContainer seishinConteiner;
    private ACIntegerCheckBox seishin;
    private IkenshoOptionComboBox seishinJikou;
    private ACLabel seishinEndCaption;
    // �S �g�̏Ǐ�
    private ACLabelContainer shintaiConteiner;
    private ACIntegerCheckBox shintai;
    private IkenshoOptionComboBox shintaiJikou;
    private ACLabel shintaiEndCaption;
    // �T ���̑�
    private ACLabelContainer otherConteiner;
    private ACIntegerCheckBox other;
    private IkenshoOptionComboBox otherText;
    private ACLabel otherEndCaption;

    /**
     * �R���X�g���N�^
     */
    public IkenshoSeishinkaTokubetsuHoumonKangoShijishoRyuiShiji() {
        try {
            jbInit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ��������
     */
    private void jbInit()throws Exception {

        this.add(getKansatuGroup(), VRLayout.CLIENT);
        this.add(getTokubetsuGroup(), VRLayout.NORTH);
        // ���_�ȓ��ʖK��Ō�w�����̗��ӎ����y�юw�������͕ʂ̃R���|�[�l���g���g�p����
        getRyuuiShijiTitle().setText("���ӎ����y�юw������");
        getRyuiShijiGrp().setVisible(false);
        
        title.setText("���ӎ����y�юw�������i���F�_�H���˖�̑��ݍ�p�E����p�ɂ��Ă̗��ӓ_������΋L�ڂ��Ă��������B�j");
        getTokubetsuGroup().add(title, VRLayout.NORTH);
        // �������K��̕K�v��
        fukusuHomon.setBindPath("FUKUSU_HOUMON");
        fukusuHomon.setModel(new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] {"����", "�Ȃ�"}))));
        fukusuHomonRiyu.setIMEMode(InputSubset.KANJI);
        fukusuHomonRiyu.setColumns(25);
        fukusuHomonRiyu.setMaxLength(25);
        fukusuHomonRiyu.setBindPath("FUKUSU_HOUMON_RIYU");
        fukusuHomonRiyuLabel.setText("���R�F");
        fukusuHomonContainer.setLayout(new VRLayout());
        fukusuHomonContainer.setText("�������K��̕K�v��");
        fukusuHomonRiyuLabel.add(fukusuHomonRiyu, VRLayout.FLOW);
        fukusuHomonContainer.add(fukusuHomon, VRLayout.FLOW);
        fukusuHomonContainer.add(fukusuHomonRiyuLabel, VRLayout.FLOW);
        // �Z���ԖK��̕K�v��
        tanjikanHomon.setBindPath("TANJIKAN_HOUMON");
        tanjikanHomon.setModel(new VRListModelAdapter(new VRArrayList(Arrays.asList(new String[] {"����", "�Ȃ�"}))));
        tanjikanHomonRiyu.setIMEMode(InputSubset.KANJI);
        tanjikanHomonRiyu.setColumns(25);
        tanjikanHomonRiyu.setBindPath("TANJIKAN_HOUMON_RIYU");
        tanjikanHomonRiyu.setMaxLength(25);
        tanjikanHomonRiyuLabel.setText("���R�F");
        tanjikanHomonContainer.setLayout(new VRLayout());
        tanjikanHomonContainer.setText("�Z���ԖK��̕K�v��");
        tanjikanHomonRiyuLabel.add(tanjikanHomonRiyu, VRLayout.FLOW);
        tanjikanHomonContainer.add(tanjikanHomon, VRLayout.FLOW);
        tanjikanHomonContainer.add(tanjikanHomonRiyuLabel, VRLayout.FLOW);
        getTokubetsuGroup().add(fukusuHomonContainer, VRLayout.NORTH);
        getTokubetsuGroup().add(tanjikanHomonContainer, VRLayout.NORTH);
        // ���ӎ����y�юw������
        getTokubetsuRyuijiko().setColumns(100);
        getTokubetsuRyuijiko().setRows(14);
        getTokubetsuRyuijiko().setMaxRows(14);
        getTokubetsuRyuijiko().setMaxLength(700);
        getTokubetsuRyuijiko().fitTextArea();
        getTokubetsuRyuijiko().setShowSelectVisible(false);
        getTokubetsuRyuijiko().setCheckVisible(false);
        getTokubetsuRyuijiko().setTextBindPath("TOKUBETSU_RYUI");
        getTokubetsuRyuijiko().setCaption("");
        getTokubetsuRyuijiko().setTitle("�@�@ �i700����/14�s�ȓ��j");
        getTokubetsuGroup().add(getTokubetsuRyuijiko(), VRLayout.NORTH);
        // �ώ@��v���鍀��
        getKansatuContainer().setText("���Ɋώ@��v���鍀��");
        getKansatuGroup().add(getKansatuContainer(), VRLayout.FLOW_INSETLINE_RETURN);
        getKansatuGroup().add(getFukuyakuContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getSuibunContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getSeishinContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getShintaiContainer(), VRLayout.FLOW_RETURN);
        getKansatuGroup().add(getOtherContainer(), VRLayout.FLOW_RETURN);
        // �P ����m�F
        getFukuyaku().setText("�P ����m�F");
        getFukuyaku().setBindPath("FUKUYAKU_UMU");
        getFukuyakuContainer().add(getFukuyaku(), VRLayout.FLOW);
        // �Q �����y�ѐH���ێ�̏�
        getSuibun().setText("�Q �����y�ѐH���ێ�̏�");
        getSuibun().setBindPath("SUIBUN_UMU");
        getSuibunContainer().add(getSuibun(), VRLayout.FLOW);
        // �R ���_�Ǐ�
        getSeishin().setText("�R ���_�Ǐ�i�ώ@���K�v�ȍ��ځF");
        getSeishin().setBindPath("SEISHIN_SYOUJYOU_UMU");
        getSeishinJikou().setIMEMode(InputSubset.KANJI);
        getSeishinJikou().setColumns(30);
        getSeishinJikou().setMaxLength(30);
        getSeishinJikou().setEnabled(false);
        getSeishinJikou().setBindPath("SEISHIN_SYOUJYOU");
        getSeishinEndCaption().setText(" �j");
        getSeishinContainer().add(getSeishin(), VRLayout.FLOW);
        getSeishinContainer().add(getSeishinJikou(), VRLayout.FLOW);
        getSeishinContainer().add(getSeishinEndCaption(), VRLayout.FLOW);
        // �S �g�̏Ǐ�
        getShintai().setText("�S �g�̏Ǐ�i�ώ@���K�v�ȍ��ځF");
        getShintai().setBindPath("SHINTAI_SYOUJYOU_UMU");
        getShintaiJikou().setIMEMode(InputSubset.KANJI);
        getShintaiJikou().setColumns(30);
        getShintaiJikou().setMaxLength(30);
        getShintaiJikou().setEnabled(false);
        getShintaiJikou().setBindPath("SHINTAI_SYOUJYOU");
        getShintaiEndCaption().setText(" �j");
        getShintaiContainer().add(getShintai(), VRLayout.FLOW);
        getShintaiContainer().add(getShintaiJikou(), VRLayout.FLOW);
        getShintaiContainer().add(getShintaiEndCaption(), VRLayout.FLOW);
        // �T ���̑�
        getOther().setText("�T ���̑��i");
        getOther().setBindPath("KANSATU_OTHER_UMU");
        getOtherText().setIMEMode(InputSubset.KANJI);
        getOtherText().setColumns(40);
        getOtherText().setMaxLength(40);
        getOtherText().setEnabled(false);
        getOtherText().setBindPath("KANSATU_OTHER");
        getOtherEndCaption().setText(" �j");
        getOtherContainer().add(getOther(), VRLayout.FLOW);
        getOtherContainer().add(getOtherText(), VRLayout.FLOW);
        getOtherContainer().add(getOtherEndCaption(), VRLayout.FLOW);
        getSeishin().addItemListener(new ACFollowDisabledItemListener(new JComponent[] {getSeishinJikou()}));
        getShintai().addItemListener(new ACFollowDisabledItemListener(new JComponent[] {getShintaiJikou()}));
        getOther().addItemListener(new ACFollowDisabledItemListener(new JComponent[] {getOtherText()}));
    }

    /**
     * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
     * @param dbm DBManager
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
        super.initDBCopmponent(dbm);
        applyPoolTeikeibun(fukusuHomonRiyu, IkenshoCommon.TEIKEI_PLURAL_VISIT_REASON);
        applyPoolTeikeibun(tanjikanHomonRiyu, IkenshoCommon.TEIKEI_SHROT_VISIT_REASON);
        applyPoolTeikeibun(getSeishinJikou(), IkenshoCommon.TEIKEI_SEISHIN_KANSATU);
        applyPoolTeikeibun(getShintaiJikou(), IkenshoCommon.TEIKEI_SHINTAI_KANSATU);
        applyPoolTeikeibun(getOtherText(), IkenshoCommon.TEIKEI_KANSATU_OTHER);
        fukusuHomonRiyu.setOptionComboBoxParameters("�������K��̕K�v���F���R", IkenshoCommon.TEIKEI_PLURAL_VISIT_REASON, 25);
        tanjikanHomonRiyu.setOptionComboBoxParameters("�Z���ԖK��̕K�v���F���R", IkenshoCommon.TEIKEI_SHROT_VISIT_REASON, 25);
        getSeishinJikou().setOptionComboBoxParameters("���_�Ǐ�i�ώ@���K�v�ȍ��ځj", IkenshoCommon.TEIKEI_SEISHIN_KANSATU, 30);
        getShintaiJikou().setOptionComboBoxParameters("�g�̏Ǐ�i�ώ@���K�v�ȍ��ځj", IkenshoCommon.TEIKEI_SHINTAI_KANSATU, 30);
        getOtherText().setOptionComboBoxParameters("���̑�", IkenshoCommon.TEIKEI_KANSATU_OTHER, 40);
    }

    /**
     * fukuyaku ��Ԃ��܂��B
     * @return fukuyaku
     */
    protected ACIntegerCheckBox getFukuyaku() {
        if(fukuyaku == null){
            fukuyaku = new ACIntegerCheckBox();
        }
        return fukuyaku;
    }

    /**
     * fukuyakuConteiner ��Ԃ��܂��B
     * @return fukuyakuConteiner
     */
    protected ACLabelContainer getFukuyakuContainer() {
        if(fukuyakuConteiner == null){
            fukuyakuConteiner = new ACLabelContainer();
        }
        return fukuyakuConteiner;
    }

    /**
     * suibun ��Ԃ��܂��B
     * @return suibun
     */
    protected ACIntegerCheckBox getSuibun() {
        if(suibun == null){
            suibun = new ACIntegerCheckBox();
        }
        return suibun;
    }

    /**
     * suibunConteiner ��Ԃ��܂��B
     * @return suibunConteiner
     */
    protected ACLabelContainer getSuibunContainer() {
        if(suibunConteiner == null){
            suibunConteiner = new ACLabelContainer();
        }
        return suibunConteiner;
    }

    /**
     * seishin ��Ԃ��܂��B
     * @return seishin
     */
    protected ACIntegerCheckBox getSeishin() {
        if(seishin == null){
            seishin = new ACIntegerCheckBox();
        }
        return seishin;
    }

    /**
     * seishinJikou ��Ԃ��܂��B
     * @return seishinJikou
     */
    protected IkenshoOptionComboBox getSeishinJikou() {
        if(seishinJikou == null){
            seishinJikou = new IkenshoOptionComboBox();
        }
        return seishinJikou;
    }

    /**
     * seishinConteiner ��Ԃ��܂��B
     * @return seishinConteiner
     */
    protected ACLabelContainer getSeishinContainer() {
        if(seishinConteiner == null){
            seishinConteiner = new ACLabelContainer();
        }
        return seishinConteiner;
    }

    /**
     * shintai ��Ԃ��܂��B
     * @return shintai
     */
    protected ACIntegerCheckBox getShintai() {
        if(shintai == null){
            shintai = new ACIntegerCheckBox();
        }
        return shintai;
    }

    /**
     * shintaiJikou ��Ԃ��܂��B
     * @return shintaiJikou
     */
    protected IkenshoOptionComboBox getShintaiJikou() {
        if(shintaiJikou == null){
            shintaiJikou = new IkenshoOptionComboBox();
        }
        return shintaiJikou;
    }

    /**
     * shintaiConteiner ��Ԃ��܂��B
     * @return shintaiConteiner
     */
    protected ACLabelContainer getShintaiContainer() {
        if(shintaiConteiner == null){
            shintaiConteiner = new ACLabelContainer();
        }
        return shintaiConteiner;
    }

    /**
     * other ��Ԃ��܂��B
     * @return other
     */
    protected ACIntegerCheckBox getOther() {
        if(other == null){
            other = new ACIntegerCheckBox();
        }
        return other;
    }

    /**
     * otherText ��Ԃ��܂��B
     * @return otherText
     */
    protected IkenshoOptionComboBox getOtherText() {
        if(otherText == null){
            otherText = new IkenshoOptionComboBox();
        }
        return otherText;
    }

    /**
     * otherConteiner ��Ԃ��܂��B
     * @return otherConteiner
     */
    protected ACLabelContainer getOtherContainer() {
        if(otherConteiner == null){
            otherConteiner = new ACLabelContainer();
        }
        return otherConteiner;
    }

    /**
     * kansatuContainer ��Ԃ��܂��B
     * @return kansatuContainer
     */
    protected ACLabelContainer getKansatuContainer() {
        if(kansatuContainer == null){
            kansatuContainer = new ACLabelContainer();
        }
        return kansatuContainer;
    }

    /**
     * kansatuGroup ��Ԃ��܂��B
     * @return kansatuGroup
     */
    protected ACGroupBox getKansatuGroup() {
        if(kansatuGroup == null){
            kansatuGroup = new ACGroupBox();
        }
        return kansatuGroup;
    }
    /**
     * seishinEndCaption ��Ԃ��܂��B
     * @return seishinEndCaption
     */
    protected ACLabel getSeishinEndCaption() {
        if(seishinEndCaption == null){
            seishinEndCaption = new ACLabel();
        }
        return seishinEndCaption;
    }
    /**
     * shintaiEndCaption ��Ԃ��܂��B
     * @return shintaiEndCaption
     */
    protected ACLabel getShintaiEndCaption() {
        if(shintaiEndCaption == null){
            shintaiEndCaption = new ACLabel();
        }
        return shintaiEndCaption;
    }
    /**
     * otherEndCaption ��Ԃ��܂��B
     * @return otherEndCaption
     */
    protected ACLabel getOtherEndCaption() {
        if(otherEndCaption == null){
            otherEndCaption = new ACLabel();
        }
        return otherEndCaption;
    }
}
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End