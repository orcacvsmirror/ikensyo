package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACParentHesesPanelContainer;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoOptionComboBox;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoIshiIkenshoInfoSickH18�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/06
 */
public class IkenshoIshiIkenshoInfoSick1 extends IkenshoIkenshoInfoSickH18 {
    // ���@�����x��
    private ACLabel nyuinrekiLabel;
    // ���@��1 - �J�n
    private IkenshoEraDateTextField nyuinrekiStartDate1;
    // ���@��2 - �J�n
    private IkenshoEraDateTextField nyuinrekiStartDate2;
    // ���@��1 - �I��
    private IkenshoEraDateTextField nyuinrekiEndDate1;
    // ���@��2 - �I��
    private IkenshoEraDateTextField nyuinrekiEndDate2;    
    // ���@��1Heses�p�l��
    private ACParentHesesPanelContainer nyuinrekiHesesPanel1; 
    // ���@��2Heses�p�l��
    private ACParentHesesPanelContainer nyuinrekiHesesPanel2;
    // ���a��1���x���R���e�i
    private ACBackLabelContainer nyuinrekiLabelContainer1;
    // ���a��2���x���R���e�i
    private ACBackLabelContainer nyuinrekiLabelContainer2;
//  2007/10/18 [Masahiko Higuchi] Replace - begin �Ɩ��J�ڃR���{�Ή� ACComboBox��IkenshoOptionComboBox
    // ���a��1�R���{�{�b�N�X
    private IkenshoOptionComboBox nyuinrekiSickCombo1;
    // ���a��2�R���{�{�b�N�X
    private IkenshoOptionComboBox nyuinrekiSickCombo2;
//  2007/10/18 [Masahiko Higuchi] Replace - end
    // ���a���R���{�p���x���R���e�i1
    private ACLabelContainer nyuinrekiSickCombo1LabelContainer;
    // ���a���R���{�p���x���R���e�i2
    private ACLabelContainer nyuinrekiSickCombo2LabelContainer;
    // ���@����t�p���x���R���e�i1
    private ACLabelContainer  nyuinrekiDateLabelContainer1;
    // ���@����t�p���x���R���e�i2
    private ACLabelContainer  nyuinrekiDateLabelContainer2;
    // ���@����t1�u����v
    private ACLabel nyuinrekiDateLabel1;
    // ���@����t2�u����v
    private ACLabel nyuinrekiDateLabel2;
    // ���@����t���C�A�E�g�P
    private VRLayout nyuinrekiDateLayout1;
    // ���@����t���C�A�E�g�Q
    private VRLayout nyuinrekiDateLayout2;
    // �o�����`�F�b�N�P
    private ACIntegerCheckBox shusseiCheck1;
    // �o�����`�F�b�N�Q
    private ACIntegerCheckBox shusseiCheck2;
    // �o�����`�F�b�N�R
    private ACIntegerCheckBox shusseiCheck3;
    
    public IkenshoIshiIkenshoInfoSick1() {
        try{
            // ��ʃR���|�[�l���g����
            buildNyuinrekiGroup();
            // ��ʍ\�z
            jbInit();
            // �C�x���g���X�i���`���܂��B
            setEvent();
            // 
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * ��ʍ��ڂ�ύX���܂��B
     */
    private void jbInit(){
        getProgressGroup().setText("��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a�̌o�ߋy�ѓ�����e���܂ގ��Ó��e");
        getSickNameGroup().setText("�f�f���i��Q�̒��ڂ̌����ƂȂ��Ă��鏝�a���ɂ��Ă͂P�D�ɋL���j�y�є��ǔN����");
        // ���莾�a�{�^��������
        getSickSpecial1().setVisible(false);
        getSickSpecial2().setVisible(false);
        getSickSpecial3().setVisible(false);
    }

    
    /**
     * ���@���R���|�[�l���g�𐶐����܂��B
     */
    protected void buildNyuinrekiGroup(){
        // ���x���R���e�i�ɃR���{��ǉ�
        getNyuinrekiSickCombo1LabelContainer().add(getNyuinrekiSickCombo1(),VRLayout.CLIENT);
        // Heses�p�l���Ƀ��x���R���e�i�ǉ�
        getNyuinrekiHesesPanel1().add(getNyuinrekiSickCombo1LabelContainer(),VRLayout.CLIENT);
        // ���t���x���R���e�i��Heses�p�l���ǉ�
        getNyuinrekiDateLabelContainer1().setLayout(getNyuinrekiDateLayout2());
        getNyuinrekiDateLabelContainer1().add(getNyuinrekiStartDate1(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer1().add(getNyuinrekiDateLabel1(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer1().add(getNyuinrekiEndDate1(),VRLayout.FLOW);
        // ���@��1���x���R���e�i
        getNyuinrekiLabelContainer1().add(getNyuinrekiDateLabelContainer1(),VRLayout.WEST);
        getNyuinrekiLabelContainer1().add(getNyuinrekiHesesPanel1(),VRLayout.CLIENT);
        
        // ���x���R���e�i�ɃR���{��ǉ�
        getNyuinrekiSickCombo2LabelContainer().add(getNyuinrekiSickCombo2(),VRLayout.CLIENT);
        // Heses�p�l���Ƀ��x���R���e�i�ǉ�
        getNyuinrekiHesesPanel2().add(getNyuinrekiSickCombo2LabelContainer(),VRLayout.CLIENT);
        // ���t���x���R���e�i��Heses�p�l���ǉ�
        getNyuinrekiDateLabelContainer2().setLayout(getNyuinrekiDateLayout1());
        getNyuinrekiDateLabelContainer2().add(getNyuinrekiStartDate2(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer2().add(getNyuinrekiDateLabel2(),VRLayout.FLOW);
        getNyuinrekiDateLabelContainer2().add(getNyuinrekiEndDate2(),VRLayout.FLOW);
        // ���@��2���x���R���e�i
        getNyuinrekiLabelContainer2().add(getNyuinrekiDateLabelContainer2(),VRLayout.WEST);
        getNyuinrekiLabelContainer2().add(getNyuinrekiHesesPanel2(),VRLayout.CLIENT);
    }
    
    /**
     * ���a���O���[�v�ɒǉ����鍀�ڂ��`���܂��B
     */
    protected void addSickGroupComponent(){
        // ���a���O���[�v��ʏ�ǉ�
        super.addSickGroupComponent();
        // �ǉ�����
        getSickNames1().add(getShusseiCheck1(),VRLayout.EAST);
        getSickNames2().add(getShusseiCheck2(),VRLayout.EAST);
        getSickNames3().add(getShusseiCheck3(),VRLayout.EAST);
        // �V�K����
        getSickNameGroup().setHgap(0);
        getSickNameGroup().add(getNyuinrekiLabel(),VRLayout.FLOW_RETURN);
        getSickNameGroup().add(getNyuinrekiLabelContainer1(),VRLayout.FLOW_RETURN);
        getSickNameGroup().add(getNyuinrekiLabelContainer2(),VRLayout.FLOW_RETURN);
    }
    
    /**
     * �C�x���g���X�i���`���܂��B
     */
    protected void setEvent(){
        // �o�����`�F�b�N�{�b�N�X�I���C�x���g
        getShusseiCheck1().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                boolean enable;
                switch(e.getStateChange()){
                case ItemEvent.SELECTED:
                    enable = false;
                    break;
                case ItemEvent.DESELECTED:
                    enable = true;
                    break;
                default:
                    return;
                }
                shusseiCheck1Event(enable);
            }
        });
        // �o�����`�F�b�N�{�b�N�X�I���C�x���g
        getShusseiCheck2().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                boolean enable;
                switch(e.getStateChange()){
                case ItemEvent.SELECTED:
                    enable = false;
                    break;
                case ItemEvent.DESELECTED:
                    enable = true;
                    break;
                default:
                    return;
                }
                shusseiCheck2Event(enable);
            }
        });
        // �o�����`�F�b�N�{�b�N�X�I���C�x���g
        getShusseiCheck3().addItemListener(new ItemListener(){
            public void itemStateChanged(ItemEvent e) {
                boolean enable;
                switch(e.getStateChange()){
                case ItemEvent.SELECTED:
                    enable = false;
                    break;
                case ItemEvent.DESELECTED:
                    enable = true;
                    break;
                default:
                    return;
                }
                shusseiCheck3Event(enable);
            }
        });
    }
    
    /**
     * �o�����`�F�b�N�{�b�N�X�P�I�����̃C�x���g�ł��B
     */
    protected void shusseiCheck1Event(boolean enable){
        getSickDate1().setEnabled(enable);
        // ���ǔN������Enable��false�ł���ꍇ�̓o�C���h�p�X�������
        if(!enable){
            getSickDate1().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }else{
            getSickDate1().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            // �ēx��Ԃ��`�F�b�N���ݒ肵�Ȃ����B
            getSickDate1().checkState();
        }
        // �s�ڂ̏ꍇ�̂�Enable�������߂�
        if(getSickDate1().isDateUnknown()){
            getSickDate1().setEraUnknownState();
        }
    }
    
    /**
     * �o�����`�F�b�N�{�b�N�X�Q�I�����̃C�x���g�ł��B
     */
    protected void shusseiCheck2Event(boolean enable){
        getSickDate2().setEnabled(enable);
        // ���ǔN������Enable��false�ł���ꍇ�̓o�C���h�p�X�������
        if(!enable){
            getSickDate2().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }else{
            getSickDate2().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            // �ēx��Ԃ��`�F�b�N���ݒ肵�Ȃ����B
            getSickDate2().checkState();
        }
        // �s�ڂ̏ꍇ�̂�Enable�������߂�
        if(getSickDate2().isDateUnknown()){
            getSickDate2().setEraUnknownState();
        }

    }
    
    /**
     * �o�����`�F�b�N�{�b�N�X�R�I�����̃C�x���g�ł��B
     */
    protected void shusseiCheck3Event(boolean enable){
        getSickDate3().setEnabled(enable);
        // ���ǔN������Enable��false�ł���ꍇ�̓o�C���h�p�X�������
        if(!enable){
            getSickDate3().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }else{
            getSickDate3().setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            // �ēx��Ԃ��`�F�b�N���ݒ肵�Ȃ����B
            getSickDate3().checkState();
        }
        // �s�ڂ̏ꍇ�̂�Enable�������߂�
        if(getSickDate3().isDateUnknown()){
            getSickDate3().setEraUnknownState();
        }
    }
    
    /**
     * override���ă^�u�ɒǉ����鍀�ڂ��`���܂��B
     */
    protected void addThisComponent(){
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getSickNameGroup(), VRLayout.NORTH);
        this.add(getStableAndOutlook(), VRLayout.NORTH);
//        this.add(getProgressGroup(), VRLayout.NORTH);
    }
    /**
     * ���@�� - �I�����t�P��Ԃ��܂��B
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiEndDate1() {
        if(nyuinrekiEndDate1 == null){
            nyuinrekiEndDate1 = new IkenshoEraDateTextField();
            nyuinrekiEndDate1.setAgeVisible(false);
            nyuinrekiEndDate1.setDayVisible(false);
            nyuinrekiEndDate1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiEndDate1.setBindPath("NYUIN_DT_END1");
        }
        return nyuinrekiEndDate1;
    }
    /**
     * ���@�� - �I�����t�Q��Ԃ��܂��B
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiEndDate2() {
        if(nyuinrekiEndDate2 == null){
            nyuinrekiEndDate2 = new IkenshoEraDateTextField();
            nyuinrekiEndDate2.setAgeVisible(false);
            nyuinrekiEndDate2.setDayVisible(false);
            nyuinrekiEndDate2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiEndDate2.setBindPath("NYUIN_DT_END2");
        }
        return nyuinrekiEndDate2;
    }
    /**
     * ���@��Heses�p�l���R���e�i�P��Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getNyuinrekiHesesPanel1() {
        if(nyuinrekiHesesPanel1 == null){
            nyuinrekiHesesPanel1 = new ACParentHesesPanelContainer();
        }
        return nyuinrekiHesesPanel1;
    }
    /**
     * ���@��Heses�p�l���R���e�i�Q��Ԃ��܂��B
     * @return
     */
    protected ACParentHesesPanelContainer getNyuinrekiHesesPanel2() {
        if(nyuinrekiHesesPanel2 == null){
            nyuinrekiHesesPanel2 = new ACParentHesesPanelContainer();
        }
        return nyuinrekiHesesPanel2;
    }
    /**
     * ���@���^�C�g�����x����Ԃ��܂��B
     * @return
     */
    protected ACLabel getNyuinrekiLabel() {
        if(nyuinrekiLabel == null){
            nyuinrekiLabel = new ACLabel();
            nyuinrekiLabel.setText("���@���i���߂̓��@�����L���j");
        }
        return nyuinrekiLabel;
    }
    /**
     * ���@���o�b�N���x���R���e�i�P��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getNyuinrekiLabelContainer1() {
        if(nyuinrekiLabelContainer1 == null){
            nyuinrekiLabelContainer1 = new ACBackLabelContainer();
            nyuinrekiLabelContainer1.setLayout(new VRLayout());
            nyuinrekiLabelContainer1.setHgap(0);
            nyuinrekiLabelContainer1.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
        }
        return nyuinrekiLabelContainer1;
    }
    /**
     * ���@���o�b�N���x���R���e�i�Q��Ԃ��܂��B
     * @return
     */
    protected ACBackLabelContainer getNyuinrekiLabelContainer2() {
        if(nyuinrekiLabelContainer2 == null){
            nyuinrekiLabelContainer2 = new ACBackLabelContainer();
            nyuinrekiLabelContainer2.setLayout(new VRLayout());
            nyuinrekiLabelContainer2.setHgap(0);
            nyuinrekiLabelContainer2.setFocusForeground(IkenshoConstants.COLOR_BACK_PANEL_FOREGROUND);
            
        }
        return nyuinrekiLabelContainer2;
    }
    /**
     * ���@��-���a���R���{�P��Ԃ��܂��B
     * @return
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected IkenshoOptionComboBox getNyuinrekiSickCombo1() {
        if(nyuinrekiSickCombo1 == null){
            nyuinrekiSickCombo1 = new IkenshoOptionComboBox();
            nyuinrekiSickCombo1.setBindPath("NYUIN_NM1");
            nyuinrekiSickCombo1.setMaxLength(30);
            nyuinrekiSickCombo1.setColumns(25);
            nyuinrekiSickCombo1.setIMEMode(InputSubset.KANJI);
        }
        return nyuinrekiSickCombo1;
    }
    /**
     * ���@���R���{�p���x���R���e�i�P��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getNyuinrekiSickCombo1LabelContainer() {
        if(nyuinrekiSickCombo1LabelContainer == null){
            nyuinrekiSickCombo1LabelContainer = new ACLabelContainer();
            nyuinrekiSickCombo1LabelContainer.setText("���a��");
        }
        return nyuinrekiSickCombo1LabelContainer;
    }
    /**
     * ���@��-���a���R���{�Q��Ԃ��܂��B
     * @return
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected IkenshoOptionComboBox getNyuinrekiSickCombo2() {
        if(nyuinrekiSickCombo2 == null){
            nyuinrekiSickCombo2 = new IkenshoOptionComboBox();
            nyuinrekiSickCombo2.setBindPath("NYUIN_NM2");
            nyuinrekiSickCombo2.setMaxLength(30);
            nyuinrekiSickCombo2.setColumns(25);
            nyuinrekiSickCombo2.setIMEMode(InputSubset.KANJI);
        }
        return nyuinrekiSickCombo2;
    }
    /**
     * ���@���R���{�p���x���R���e�i�Q��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getNyuinrekiSickCombo2LabelContainer() {
        if(nyuinrekiSickCombo2LabelContainer == null){
            nyuinrekiSickCombo2LabelContainer = new ACLabelContainer();
            nyuinrekiSickCombo2LabelContainer.setText("���a��");
        }
        return nyuinrekiSickCombo2LabelContainer;
    }
    /**
     * ���@�� - �J�n���t�P��Ԃ��܂��B
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiStartDate1() {
        if(nyuinrekiStartDate1 == null){
            nyuinrekiStartDate1 = new IkenshoEraDateTextField();
            nyuinrekiStartDate1.setAgeVisible(false);
            nyuinrekiStartDate1.setDayVisible(false);
            nyuinrekiStartDate1.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiStartDate1.setBindPath("NYUIN_DT_STA1");
        }
        return nyuinrekiStartDate1;
    }
    /**
     * ���@�� - �J�n���t�Q��Ԃ��܂��B
     * @return
     */
    protected IkenshoEraDateTextField getNyuinrekiStartDate2() {
        if(nyuinrekiStartDate2 == null){
            nyuinrekiStartDate2 = new IkenshoEraDateTextField();
            nyuinrekiStartDate2.setAgeVisible(false);
            nyuinrekiStartDate2.setDayVisible(false);
            nyuinrekiStartDate2.setRequestedRange(IkenshoEraDateTextField.RNG_YEAR);
            nyuinrekiStartDate2.setBindPath("NYUIN_DT_STA2");
        }
        return nyuinrekiStartDate2;
    }
    /**
     * ���@����t�p���x���R���e�i�P��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getNyuinrekiDateLabelContainer1() {
        if(nyuinrekiDateLabelContainer1 == null){
            nyuinrekiDateLabelContainer1 = new ACLabelContainer();
            nyuinrekiDateLabelContainer1.setText("�P�D");
        }
        return nyuinrekiDateLabelContainer1;
    }
    /**
     * ���@����t�p���x���R���e�i�Q��Ԃ��܂��B
     * @return
     */
    protected ACLabelContainer getNyuinrekiDateLabelContainer2() {
        if(nyuinrekiDateLabelContainer2 == null){
            nyuinrekiDateLabelContainer2 = new ACLabelContainer();
            nyuinrekiDateLabelContainer2.setText("�Q�D");
        }
        return nyuinrekiDateLabelContainer2;
    }

    protected ACLabel getNyuinrekiDateLabel1() {
        if(nyuinrekiDateLabel1 == null){
            nyuinrekiDateLabel1 = new ACLabel();
            nyuinrekiDateLabel1.setText(" ���� ");
        }
        return nyuinrekiDateLabel1;
    }

    protected ACLabel getNyuinrekiDateLabel2() {
        if(nyuinrekiDateLabel2 == null){
            nyuinrekiDateLabel2 = new ACLabel();
            nyuinrekiDateLabel2.setText(" ���� ");
        }
        return nyuinrekiDateLabel2;
    }

    protected VRLayout getNyuinrekiDateLayout1() {
        if(nyuinrekiDateLayout1 == null){
            nyuinrekiDateLayout1 = new VRLayout();
            nyuinrekiDateLayout1.setHgap(0);
            nyuinrekiDateLayout1.setAutoWrap(false);
        }
        return nyuinrekiDateLayout1;
    }

    protected VRLayout getNyuinrekiDateLayout2() {
        if(nyuinrekiDateLayout2 == null){
            nyuinrekiDateLayout2 = new VRLayout();
            nyuinrekiDateLayout2.setHgap(0);
            nyuinrekiDateLayout2.setAutoWrap(false);
        }
        return nyuinrekiDateLayout2;
    }
    /**
     * �o�����`�F�b�N�{�b�N�X�P��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getShusseiCheck1() {
        if(shusseiCheck1 == null){
            shusseiCheck1 = new ACIntegerCheckBox();
            shusseiCheck1.setText("�o����");
            shusseiCheck1.setBindPath("SHUSSEI1");
        }
        return shusseiCheck1;
    }
    /**
     * �o�����`�F�b�N�{�b�N�X�Q��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getShusseiCheck2() {
        if(shusseiCheck2 == null){
            shusseiCheck2 = new ACIntegerCheckBox();
            shusseiCheck2.setText("�o����");
            shusseiCheck2.setBindPath("SHUSSEI2");
        }
        return shusseiCheck2;
    }
    /**
     * �o�����`�F�b�N�{�b�N�X�R��Ԃ��܂��B
     * @return
     */
    protected ACIntegerCheckBox getShusseiCheck3() {
        if(shusseiCheck3 == null){
            shusseiCheck3 = new ACIntegerCheckBox();
            shusseiCheck3.setText("�o����");
            shusseiCheck3.setBindPath("SHUSSEI3");
        }
        return shusseiCheck3;
    }
    
    /**
     * �R���g���[���Ƃ��čX�V�������\����Ԃ��܂��B<br />
     * apply�O�ɌĂ΂�܂��B
     * 
     * @throws Exception ������O
     * @return �X�V�������\��
     */
    public boolean noControlError() {
        if(super.noControlError()){
            // ���@����t���`�F�b�N
            IkenshoEraDateTextField[] sicks = new IkenshoEraDateTextField[] {
                    nyuinrekiStartDate1, nyuinrekiEndDate1, nyuinrekiStartDate2,
                    nyuinrekiEndDate2 };
            int end = sicks.length;
            for (int i = 0; i < end; i++) {
                switch (sicks[i].getInputStatus()) {
                case IkenshoEraDateTextField.STATE_EMPTY:
                case IkenshoEraDateTextField.STATE_VALID:
                    break;
                case IkenshoEraDateTextField.STATE_FUTURE:
                    ACMessageBox.showExclamation("�����̓��t�ł��B");
                    sicks[i].requestChildFocus();
                    return false;
                default:
                    ACMessageBox.show("���t�Ɍ�肪����܂��B");
                    sicks[i].requestChildFocus();
                    return false;
                }
            }
        }else{
            return false;
        }
        return true;
    }
    
    /**
     * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {
      super.initDBCopmponent(dbm);
      
      getSickSpecial1().setPressedModel(new ACComboBoxModelAdapter());
      getSickSpecial2().setPressedModel(new ACComboBoxModelAdapter());
      getSickSpecial3().setPressedModel(new ACComboBoxModelAdapter());
      
      applyPoolTeikeibun(getSickName1(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getSickName2(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getSickName3(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getNyuinrekiSickCombo1(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getNyuinrekiSickCombo2(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
      applyPoolTeikeibun(getNotStableState(), IkenshoCommon.TEIKEI_ISHI_INSECURE_CONDITION_NAME);
            

        // 2007/10/18 [Masahiko Higuchi] Addition - begin �Ɩ��J�ڃR���{�Ή�
        // ACComboBox��IkenshoOptionComboBox
        getSickName1().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getSickName2().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getSickName3().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getNyuinrekiSickCombo1().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getNyuinrekiSickCombo2().setOptionComboBoxParameters("���a��",
                IkenshoCommon.TEIKEI_ISHI_SICK_NAME, 30);
        getNotStableState().setOptionComboBoxParameters("�u�s����v�Ƃ����ꍇ�̋�̓I��",
                IkenshoCommon.TEIKEI_ISHI_INSECURE_CONDITION_NAME, 30);

        // �R���{�̘A����ݒ�
        getSickName1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName2(), getSickName3(),
                        getNyuinrekiSickCombo1(), getNyuinrekiSickCombo2() });
        getSickName2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName3(),
                        getNyuinrekiSickCombo1(), getNyuinrekiSickCombo2() });
        getSickName3().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2(),
                        getNyuinrekiSickCombo1(), getNyuinrekiSickCombo2() });
        getNyuinrekiSickCombo1().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2(),
                        getSickName3(), getNyuinrekiSickCombo2() });
        getNyuinrekiSickCombo2().addInterlockComboComponents(
                new IkenshoOptionComboBox[] { getSickName1(), getSickName2(),
                        getSickName3(), getNyuinrekiSickCombo1() });

        // �ݒ肷��l�������ւ���
        getSickSpecial1().setUnpressedModel(getSickName1().getOriginalModel());
        getSickSpecial2().setUnpressedModel(getSickName2().getOriginalModel());
        getSickSpecial3().setUnpressedModel(getSickName3().getOriginalModel());
        //    2007/10/18 [Masahiko Higuchi] Addition - end
      
    }
    
    /**
     * �ǉ������Ώۂ̃R���|�[�l���g�ɎQ�Ɛ�\�[�X�̒l�𗬂����݂܂��B
     * 
     * @throws Exception ��͗�O
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        // �X�[�p�[�N���X�̃��\�b�h���ĂԑO�ɂ��悤
        // �ꗥ�����Ă��Ȃ����Ƃɂ���
        getSickSpecial1().setPushed(true);
        getSickSpecial2().setPushed(true);
        getSickSpecial3().setPushed(true);
        getSickSpecial1().refreshCombo();
        getSickSpecial2().refreshCombo();
        getSickSpecial3().refreshCombo();
        // �{�^���X�e�[�^�X�ݒ��Ƀ��o�C���h
        applyPoolTeikeibun(getSickName1(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
        applyPoolTeikeibun(getSickName2(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
        applyPoolTeikeibun(getSickName3(), IkenshoCommon.TEIKEI_ISHI_SICK_NAME);
        getSickSpecial1().setUnpressedModel( getSickName1().getModel() );
        getSickSpecial2().setUnpressedModel( getSickName2().getModel() );
        getSickSpecial3().setUnpressedModel( getSickName3().getModel() );
        
        
        super.bindSourceInnerBindComponent();
        // �o�����`�F�b�N�{�b�N�X1�I���C�x���g
        if(getShusseiCheck1().isSelected()){
            shusseiCheck1Event(false);
        }else{
            shusseiCheck1Event(true);
        }
        
        // �o�����`�F�b�N�{�b�N�X2�I���C�x���g
        if(getShusseiCheck2().isSelected()){
            shusseiCheck2Event(false);
        }else{
            shusseiCheck2Event(true);
        }
        
        // �o�����`�F�b�N�{�b�N�X3�I���C�x���g
        if(getShusseiCheck3().isSelected()){
            shusseiCheck3Event(false);
        }else{
            shusseiCheck3Event(true);
        }
        
    }
    
    /**
     * �ǉ������Ώۂ̃R���|�[�l���g�̒l���Q�Ɛ�\�[�X�ɓK�p���܂��B
     * 
     * @throws Exception ��͗�O
     */
    protected void applySourceInnerBindComponent() throws Exception {
        // �o�����`�F�b�N�{�b�N�X1�I���C�x���g
        if(getShusseiCheck1().isSelected()){
            shusseiCheck1Event(false);
            // �f�[�^�������l�œo�^����
            if(getSickDate1().getSource() != null){
                getSickDate1().getSource().setData("HASHOU_DT1","0000�N00��00��");
            }
        }else{
            shusseiCheck1Event(true);
        }
        
        // �o�����`�F�b�N�{�b�N�X2�I���C�x���g
        if(getShusseiCheck2().isSelected()){
            shusseiCheck2Event(false);
            // �f�[�^�������l�œo�^����
            if(getSickDate2().getSource() != null){
                getSickDate2().getSource().setData("HASHOU_DT2","0000�N00��00��");
            }
        }else{
            shusseiCheck2Event(true);
        }
        
        // �o�����`�F�b�N�{�b�N�X3�I���C�x���g
        if(getShusseiCheck3().isSelected()){
            shusseiCheck3Event(false);
            // �f�[�^�������l�œo�^����
            if(getSickDates3().getSource() != null){
                getSickDate3().getSource().setData("HASHOU_DT3","0000�N00��00��");
            }
        }else{
            shusseiCheck3Event(true);
        }
    }
    
}
