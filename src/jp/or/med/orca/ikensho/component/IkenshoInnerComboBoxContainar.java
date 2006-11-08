package jp.or.med.orca.ikensho.component;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;

import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;

/**
 * 
 * IkenshoInnerComboBoxContainar�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/11
 */
public class IkenshoInnerComboBoxContainar extends ACLabelContainer {

    private ACComboBox combo = new ACComboBox();
    private IkenshoHintButton hintButton = new IkenshoHintButton();
    private VRMap maps = new VRHashMap();
    private ArrayList listArray = new ArrayList();
    
    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoInnerComboBoxContainar() {
        super();
        try{
            if(combo == null){
                combo = new ACComboBox();
            }
            // �R���{�ǉ�
            this.add(combo,VRLayout.FLOW);
            // �q���g�{�^���ǉ�
            this.add(hintButton,VRLayout.FLOW);
            // ����������
            initialize();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * �R���X�g���N�^�ł��B
     * @param text
     */
    public IkenshoInnerComboBoxContainar(String text) {
        super(text);
    }

    /**
     * ����������
     */
    public void initialize(){
        // ����̃R���{�̃T�C�Y���K��
        combo.setPreferredSize(new Dimension(50,19));
        combo.setEditable(false);
        combo.setBlankable(false);
    }
    
    /**
     * �R���{�̑I������ݒ肵�܂��B
     */
    public void setInnerComboModel(String bindPath, String renderBintPath,
            Object[] bindChoice, Object[] renderChoice) {
        for (int i = 0; i < Math.min(bindChoice.length, renderChoice.length); i++) {
            maps = new VRHashMap();
            maps.setData(bindPath, bindChoice[i]);
            maps.setData(renderBintPath, renderChoice[i]);
            listArray.add(maps);
        }
        combo.setModel(listArray);
// combo.setModel(maps);
    }
    
    
    /**
     * �R���{�̃T�C�Y��ύX���܂�
     */
    public void setComboSize(int width,int height){
        combo.setPreferredSize(new Dimension(width,height));
    }
    
    /**
     * �����ɐݒ肵�Ă���R���{�{�b�N�X��Ԃ��܂��B
     * @return
     */
    public ACComboBox getInnerComboBox(){
        return combo;
    }

    /**
     * �����ɐݒ肵�Ă���q���g�{�^����Ԃ��܂��B
     * @return
     */
    public IkenshoHintButton getHintButton() {
        return hintButton;
    }
    
    /**
     * �����R���{��Editable��ݒ肵�܂��B
     * @param editable
     */
    public void setInnerComboEditable(boolean editable){
        combo.setEditable(editable);
    }
    
    /**
     * �����R���{�Ƀo�C���h�p�X��ݒ肵�܂��B
     * @param bindPath
     */
    public void setComboBindPath(String bindPath){
        combo.setBindPath(bindPath);
    }

    /**
     * �����R���{�ɐݒ肳��Ă���o�C���h�p�X��Ԃ��܂��B
     * @return
     */
    public String getComboBindPath(){
        return combo.getBindPath();
    }

    /**
     * �����R���{�̃����_�[�o�C���h�p�X��ݒ肵�܂��B
     * @param renderPath
     */
    public void setComboRenderBindPath(String renderPath){
        combo.setRenderBindPath(renderPath);
    }
    
    /**
     * �����R���{�̃����_�[�o�C���h�p�X��Ԃ��܂��B
     * @return
     */
    public String getComboRenderBindPath(){
        return combo.getRenderBindPath();
    }
    
}
