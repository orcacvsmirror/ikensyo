package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/**
 * IkenshoOptionTeikeibunEdit�ł��B
 * 
 * �R���{�J�ڌ�̒�^���ҏW�Ɩ��ł��B
 * 
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public class IkenshoOptionTeikeibunEdit extends IkenshoTeikeibunEdit {

    // ����{�^��������߂�
    public static final int CLOSE_RETURN = 0;
    // �I�����Ė߂�
    public static final int SELECT_RETURN = 1;
    // �I�����Ė߂�{�^��
    private ACButton selectReturn;
    // �I�����Ė߂鉟�����̒l
    private String returnValue = "";
    // �߂莞�̏�Ԃ���肷��t���O
    public int returnType = 99;
    
    /**
     * @deprecated ��������Ȃ��R���X�g���N�^�ł��B
     * @param title
     * @param tableNo
     * @param kubun
     */
    public IkenshoOptionTeikeibunEdit(String title, int tableNo, int kubun) {
        super(title, tableNo, kubun);
    }

    /**
     * 
     * �R���X�g���N�^�ł��B
     * @param title
     * @param tableNo
     * @param kubun
     * @param length
     */
    public IkenshoOptionTeikeibunEdit(String title, int tableNo, int kubun, int length) {
        super(title, tableNo, kubun, length);
    }
    /**
     * 
     * �R���X�g���N�^�ł��B
     * @param title
     * @param tableNo
     * @param kubun
     * @param length
     * @param comboValue �J�ڎ��̃R���{�̒l��ݒ�B
     */
    public IkenshoOptionTeikeibunEdit(String title, int tableNo, int kubun, int length,String comboValue) {
        super(title, tableNo, kubun, length);
        // �l��ޔ�����B
        returnValue = comboValue;
    }
    
    /**
     * 
     * �R���X�g���N�^�ł��B
     */
    public IkenshoOptionTeikeibunEdit() {
        super();
    }

    /**
     * ��ʐ�����override
     */
    protected void jbInit() throws Exception {
        super.jbInit();
    }
    
    /**
     * �I�v�V�����R���{��ʕύX�����p�B
     * @return
     */
    protected void jbInitCustom() {

    }
    
    /**
     * �{�^���̈�̐������s���܂��B
     */
    protected void buildButtonsPanel(){
        getButtons().add(getOtherAdd(),null);
        getButtons().add(getAdd(),null);
        getButtons().add(getEdit(),null);
        getButtons().add(getDelete(),null);
        getButtons().add(getComit(),null);
        getButtons().add(getSelectReturn(),null);
        getButtons().add(getClose(),null);
    }
    
    
    /**
     * �C�x���g�̒ǉ����s���܂��B
     */
    protected void addEvents() {
        
        // �o�^����Ă���C�x���g�̍폜
        getComit().removeActionListener(getComit().getActionListeners()[0]);
        
        // �o�^���̃C�x���g�Ē�`
        getComit().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // DB�X�V
                try {
                    updateData();
                } catch (Exception ex) {
                    IkenshoCommon.showExceptionMessage(ex);
                    return;
                }
                ACMessageBox.show("�X�V���܂����B");
                // �e�[�u���̍Ď擾����
                doSelect();
            }
            
        });
        
        // �I�����Ė߂�I�����̃C�x���g 
        getSelectReturn().addActionListener(new ActionListener(){
            // �C�x���g����
            public void actionPerformed(ActionEvent e){
                
                try {
                    // ���X�g���ڂɕύX������ꍇ�i���o�^�j
                    if(isModified()){
                        if (ACMessageBox
                                .showOkCancel("���X�g��₪�ύX����Ă��܂��B�ύX���e��o�^���܂����H") == ACMessageBox.RESULT_OK) {
                            updateData();
                            ACMessageBox.show("�X�V���܂����B");
                        }
                        // ��U�������I����B
                        return;
                    }
                    
                    String selectValue = getReturnValue();
                    // �I������Ă��邩
                    if(getTable().isSelected()){
                        selectValue = ACCastUtilities.toString(((VRMap)getTable().getSelectedModelRowValue()).getData("TEIKEIBUN"));
                        // �l�ɍ�������A�Ȃ����l���󔒈ȊO�̏ꍇ
                        if(!getReturnValue().equals(selectValue)&&!"".equals(getReturnValue())){
                            int msgResult = ACMessageBox.showOkCancel("�����f�[�^��u�������Ă�낵���ł����H");
                            // �L�����Z���������ꍇ
                            if(msgResult == ACMessageBox.RESULT_CANCEL){
                                // �����I��
                                return;
                            }
                        }
                    }
                    // �l��ݒ�
                    setReturnValue(selectValue);
                    
                } catch (Exception err) {
                    err.printStackTrace();
                }
                // �_�C�A���O�����
                closeWindow();
                // �߂蔻��t���O�ݒ�
                returnType = SELECT_RETURN;
            }
        });
    }
    
    
    /**
     * �E�B���h�E����܂��Boverride
     */
    protected void closeWindow() {
        super.closeWindow();
        returnType = CLOSE_RETURN;
    }
    
    /**
     * �߂莞�̏����擾���܂��B
     * @return
     */
    public int getReturnType(){
        return this.returnType;
    }
    
    /**
     * �I�����Ė߂�{�^�����擾���܂��B
     * @return
     */
    protected ACButton getSelectReturn() {
        if(selectReturn == null){
            selectReturn = new ACButton();
            selectReturn.setText("�I�����Ė߂�(E)");
            selectReturn.setMnemonic('E');
            selectReturn.setToolTipText("�I������Ă��鍀�ڂ��̗p�����̉�ʂɖ߂�܂��B");
        }
        return selectReturn;
    }

    /**
     * �߂莞�̒l���擾���܂��B
     * @return
     */
    public String getReturnValue() {
        return returnValue;
    }
    
    /**
     * �߂莞�̒l��ޔ����܂��B
     * @param selectReturnValue
     */
    public void setReturnValue(String selectReturnValue) {
        this.returnValue = selectReturnValue;
    }
    
    /**
     * �ύX�{�^���������̒ǉ�����
     * 
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    protected void addChangeAfter() throws Exception{
    	if(table != null){
    		// �Đݒ菈���𑖂点��
    		table.setSelectedModelRow(editRow);
    	}
    }
    
}
