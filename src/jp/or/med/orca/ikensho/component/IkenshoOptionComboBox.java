package jp.or.med.orca.ikensho.component;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.Icon;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.w3c.dom.views.AbstractView;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACBindListCellRenderer;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACListBox;
import jp.nichicom.ac.component.ACListItem;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.mainmenu.ACMainMenuButton;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.component.AbstractVRTextField;
import jp.nichicom.vr.component.VRListBox;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.affair.IkenshoTeikeibunEdit;
import jp.or.med.orca.ikensho.affair.IkenshoOptionTeikeibunEdit;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * 
 * IkenshoOptionComboBox�ł��B
 * <p>
 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/10
 */
public class IkenshoOptionComboBox extends ACComboBox {

    public static final String CLEAR = "�N���A";

    public static final String EDIT = "�ҏW...";

    protected IkenshoOptionComboBoxModelDecorator modelDecorator;
    // �ҏW���^�C�g��
    private String editComboTitle = "";
    // ��^���敪
    private int teikeibunKbn = 0;
    // �ő啶����
    private int fontMaxLength = 0;
    
    private boolean isSelectEvent = false;
    
    private boolean optionMode = true;

    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin ��ʒ����ɔ����T�C�Y�w����\�Ƃ���B
    private int optionSize = 0;
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    
    // �A���ΏۂƂȂ�R���{�{�b�N�X�Q
    private ArrayList interlockComboComponents;

    /**
     * �R���X�g���N�^
     *
     */
    public IkenshoOptionComboBox() {
        this(new DefaultComboBoxModel());
    }

    /**
     * �R���X�g���N�^
     *
     * @param items
     */
    public IkenshoOptionComboBox(Object[] items) {
        this(new DefaultComboBoxModel(items));
    }

    /**
     * �R���X�g���N�^
     *
     * @param items
     */
    public IkenshoOptionComboBox(Vector items) {
        this(new DefaultComboBoxModel(items));
    }

    /**
     * �R���X�g���N�^
     *
     * @param aModel
     */
    public IkenshoOptionComboBox(ComboBoxModel aModel) {
        super();
        setEditable(true);
        this.setModel(aModel);
        this.setRenderer(new ACBindListCellRenderer());
    }

    /**
     * �����\�z
     */
    protected void initComponent() {
        super.initComponent();
    }
    
    /**
     * �I�v�V�����R���|�[�l���g�ǉ���̃��f�����擾���܂��B
     */
    public ComboBoxModel getModel() {
        if(isOptionMode()){
            
            return modelDecorator;
            
        }else{
            
            return modelDecorator.getOriginalModel();
            
        }
    }
    
    /**
     * �I�v�V�����R���|�[�l���g�ݒ�O�̃��f�����擾���܂��B
     * @return
     */
    public ComboBoxModel getOriginalModel(){
        return modelDecorator.getOriginalModel();
    }

    /**
     * ���f���ݒ���s���܂��B
     */
    public void setModel(ComboBoxModel aModel) {
        //�V�K�Ƀ��f����ݒ肵���ꍇ�A���f���f�R���[�^�[���V�K�쐬����
        modelDecorator = new IkenshoOptionComboBoxModelDecorator(aModel);
        if(isOptionMode()){
            modelDecorator.getOptions().add(" ");
            modelDecorator.getOptions().add(EDIT);
        }
        
        super.setModel(modelDecorator);
        

    }
    
    /**
     * customizable ��Ԃ��܂��B
     *
     * @return customizable
     */
    public ArrayList getOptions() {
        return modelDecorator.getOptions();
    }

    /**
     * Selects the item at index <code>anIndex</code>.
     *
     * @param anIndex an integer specifying the list item to select,
     *          where 0 specifies the first item in the list and -1 indicates no selection
     * @exception IllegalArgumentException if <code>anIndex</code> < -1 or
     *          <code>anIndex</code> is greater than or equal to size
     * @beaninfo
     *   preferred: true
     *  description: The item at index is selected.
     */
    public void setSelectedIndex(int index) {
        if (index < modelDecorator.getOriginalModel().getSize()) {
            super.setSelectedIndex(index);
        } else {
            if(!isSelectEvent){
                // �A���C�x���g�΍�
                isSelectEvent = true;
                //�C�x���g����
                fireVROptionSelected(new IkenshoOptionComboBoxEvent(this, getOptions().get(index - modelDecorator.getOriginalModel().getSize())));
                isSelectEvent = false;
            }
        }
    }

    /**
     * �I�v�V�������ڑI�����̏������s���܂��B
     * @param e �C�x���g�\�[�X
     */
    protected void fireVROptionSelected(IkenshoOptionComboBoxEvent e) {
        
        // �󔒂͏������Ȃ�
        if(" ".equals(e.getOptionItem())){
            super.clearSelection();
            return;
        }
        
        try{
            // ��^���_�C�A���O�̐���
            IkenshoOptionTeikeibunEdit editDlg = new IkenshoOptionTeikeibunEdit(
                    getEditComboTitle(), IkenshoTeikeibunEdit.TEIKEIBUN,
                    getTeikeibunKbn(), getFontMaxLength(),this.getText());
            
            int otherDocKbn = -1;
            int otherDocType = -1;
            switch(getTeikeibunKbn()){
            case IkenshoCommon.TEIKEI_SICK_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_SICK_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_SICK_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_SICK_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case IkenshoCommon.TEIKEI_MEDICINE_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_MEDICINE_NAME:
                otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_NAME;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT:
                otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_DOSAGE_UNIT;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            case IkenshoCommon.TEIKEI_MEDICINE_USAGE:
                otherDocKbn = IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE;
                otherDocType = IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO;
                break;
            case IkenshoCommon.TEIKEI_ISHI_MEDICINE_USAGE:
                otherDocKbn = IkenshoCommon.TEIKEI_MEDICINE_USAGE;
                otherDocType = IkenshoConstants.IKENSHO_LOW_H18;
                break;
            }
            
            // �R���{��ނɂ��L���v�V�������̂̕ύX
            switch (otherDocType) {
            case IkenshoConstants.IKENSHO_LOW_DEFAULT:
            case IkenshoConstants.IKENSHO_LOW_H18:
                editDlg.setAllowedAddOtherDocument(otherDocKbn, "�厡��ӌ���");
                break;
            case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
                editDlg.setAllowedAddOtherDocument(otherDocKbn, "��t�ӌ���");
                break;
            }
            
            editDlg.setVisible(true);
            
            VRMap param = new VRHashMap();
            param.setData("RETURN_TYPE",new Integer(editDlg.getReturnType()));
            param.setData("RETURN_VALUE",editDlg.getReturnValue());
            // �R���{���ڂ̍Đݒ菈��
            resettingComboItem(param);
            
        }catch(Exception er){
            er.printStackTrace();
            ACMessageBox.show("��^���ҏW�ɕK�v�ȃp�����[�^�[���s�����Ă��܂��B");
            return;
        }
        
    }
    

    /**
     * �ҏW�R���{���̂�ݒ肵�܂��B
     * @param name
     */
    public void setEditComboTitle(String name){
        editComboTitle = name;
    }
    
    /**
     * �ҏW�R���{���̂�ݒ肵�܂��B
     * @param name
     */
    public String getEditComboTitle(){
        return editComboTitle;
    }
    
    /**
     * ��^���Ăяo���p�̋敪�ԍ����擾���܂��B
     * @return
     */
    public int getTeikeibunKbn() {
        return teikeibunKbn;
    }
    
    /**
     * ��^���Ăяo���p�̋敪�ԍ���ݒ肵�܂��B
     * @param teikeibunKbn
     */
    public void setTeikeibunKbn(int index) {
        this.teikeibunKbn = index;
    }
    
    /**
     * �ő啶�������擾���܂��B
     * @return
     */
    public int getFontMaxLength() {
        return fontMaxLength;
    }
    
    /**
     * �ő啶������ݒ肵�܂��B
     * @param fontMaxLength
     */
    public void setFontMaxLength(int maxLength) {
        this.fontMaxLength = maxLength;
    }
    
    /**
     * �I�v�V�����R���{�{�b�N�X�ɕK�{�ƂȂ鍀�ڂ�ݒ肵�܂��B
     * @param editComboTitle
     * @param teikeibunKbn
     * @param fontMaxLength
     */
    public void setOptionComboBoxParameters(String editComboTitle,
            int teikeibunKbn, int fontMaxLength) {
        // �^�C�g��
        setEditComboTitle(editComboTitle);
        // ��^���敪
        setTeikeibunKbn(teikeibunKbn);
        // �ő啶����
        setFontMaxLength(fontMaxLength);
    }

    /**
     * �ҏW��̐ݒ菈�����s���܂��B
     * 
     * @param param �p�����[�^�[�Q
     * @throws Exception
     */
    protected void resettingComboItem(VRMap param) throws Exception{
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        HashMap teikeibunMap = new HashMap();
        // ��^���̍Ď擾
        IkenshoCommon.getMasterTable(dbm, teikeibunMap, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", "TKB_CD");
        Object obj = teikeibunMap.get(new Integer(getTeikeibunKbn()));
        // �������g�ɍĐݒ�
        if (obj instanceof VRHashMapArrayToConstKeyArrayAdapter) {
          IkenshoCommon.applyComboModel(this,
                                        (VRHashMapArrayToConstKeyArrayAdapter)
                                        obj);
              Object objItem = new Object();
              // �A���R���{�ɂ�����ݒ�
            if (getInterlockComboComponents() != null) {
                for (int i = 0; i < getInterlockComboComponents().size(); i++) {
                    // �I�v�V�����R���{�{�b�N�X�ł��鎖�͕ۏႷ��
                    if (getInterlockComboComponents().get(i) instanceof IkenshoOptionComboBox) {
                        // ���[�J���ϐ��Ɏ擾
                        IkenshoOptionComboBox combo = (IkenshoOptionComboBox) getInterlockComboComponents()
                                .get(i);
                        // �l�͈�U�ޔ����Ă���
                        objItem = combo.getSelectedItem();
                        // Null�������ꍇ
                        if(objItem == null){
                            // �e�L�X�g���當������擾����
                            objItem = combo.getText();
                        }
                        // �I�v�V�����R���{���[�h�ł���ꍇ
                        if (combo.isOptionMode()) {
                            // ���f���Đݒ�
                            IkenshoCommon.applyComboModel(combo,
                                    (VRHashMapArrayToConstKeyArrayAdapter) obj);
                        }
                        // �l�̍����߂�
                        combo.setSelectedItem(objItem);
                    }
                }
            }
          
        }else{
            this.setModel(new ACComboBoxModelAdapter());
        }
        
        // �߂莞�̃{�^���ɂ�菈������
        switch(ACCastUtilities.toInt(param.getData("RETURN_TYPE"))){
            case IkenshoOptionTeikeibunEdit.CLOSE_RETURN:
                // ����
                // �l�̍����߂�
                this.setSelectedItem(param.getData("RETURN_VALUE"));
                break;
            case IkenshoOptionTeikeibunEdit.SELECT_RETURN:
                // �I��L
                // �I�������l��ݒ肷��B
                this.setSelectedItem(param.getData("RETURN_VALUE"));
                break;
            default:
                
                break;
        }
        
    }
    
    /**
     * �I�v�V�������[�h��ԋp���܂��B
     * @return
     */
    public boolean isOptionMode() {
        return optionMode;
    }

    /**
     * �I�v�V�������[�h��ݒ肵�܂��B
     * @param optionMode
     */
    public void setOptionMode(boolean optionMode) {
        this.optionMode = optionMode;

    }

    /**
     * ���f���̘A������R���{�Q���擾���܂��B
     * 
     * @return �I�����̘A������R���{�Q
     */
    public ArrayList getInterlockComboComponents() {
        return interlockComboComponents;
        
    }

    /**
     * ���f���̘A������R���{�Q��ݒ肵�܂��B
     * @param �I�������A������R���{�Q
     */
    public void addInterlockComboComponents(
            IkenshoOptionComboBox[] interlockComboComponents) {
        if(this.interlockComboComponents == null){
            this.interlockComboComponents = new ArrayList();
        }
        
        for(int i=0;i<interlockComboComponents.length; i++){
            this.interlockComboComponents.add(interlockComboComponents[i]);
        }
    }
    
    /**
     * ��^�����R���{�{�b�N�X���f���`���Ŏ擾���܂��B
     * @throws Exception
     */
    public ComboBoxModel getTeikeibunComboBoxModel() throws Exception {

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        HashMap teikeibunMap = new HashMap();
        // ��^���̍Ď擾
        IkenshoCommon.getMasterTable(dbm, teikeibunMap, "TEIKEIBUN",
                "TEIKEIBUN", "TKB_KBN", "TKB_CD");
        Object obj = teikeibunMap.get(new Integer(getTeikeibunKbn()));
        if (obj instanceof VRHashMapArrayToConstKeyArrayAdapter) {
            return (ComboBoxModel) IkenshoCommon
                    .createComboAdapter((VRHashMapArrayToConstKeyArrayAdapter) obj);
        } else {

            return (ComboBoxModel) new ACComboBoxModelAdapter();
        }
    }
 
    
    // [ID:0000509][Masahiko Higuchi] 2009/06 add begin ��ʒ����ɔ����T�C�Y�w����\�Ƃ���B
    
    /**
     * �R���{�{�b�N�X�̎w��T�C�Y��ԋp���܂��B 
     */
    public int getOptionSize() {
        return optionSize;
    }

    /**
     * �R���{�{�b�N�X�̎w��T�C�Y��ݒ肵�܂��B
     * @param optionColumnSize
     */
    public void setOptionSize(int optionColumnSize) {
        this.optionSize = optionColumnSize;
    }
    
    public Dimension getPreferredSize() {
        Dimension d = super.getPreferredSize();
        if(getMaxLength()>0 && d != null){
            //�ő啝�Ő�������
            d.width += 6;
            // �I�v�V�����T�C�Y��0�𒴂��Ă���ꍇ
            if(getOptionSize() > 0) {
                // �J�����T�C�Y���̗p����
                d.width = getOptionSize();
            }
            
        }
        return d;
    }
    // [ID:0000509][Masahiko Higuchi] 2009/06 add end
    
    
    // [ID:0000438][Tozo TANAKA] 2009/06/10 delete begin �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
    protected AbstractVRTextField createEditorField() {
        return new IkenshoACTextField();
    }
    // [ID:0000438][Tozo TANAKA] 2009/06/10 delete end �y�厡��㌩���E��t�㌩���z��ܖ��e�L�X�g�̒ǉ�
    

}

