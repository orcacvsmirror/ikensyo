package jp.or.med.orca.ikensho.util;

import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;

import javax.swing.JTextField;

import jp.nichicom.ac.component.event.ACFollowContainerFormatEventListener;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.text.ACSQLSafeStringFormat;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.bridge.sql.BridgeFirebirdDBManager;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

/**
 *****************************************************************
 * �A�v��: Ikensho
 * �J����: ����@��F
 * �쐬��: 2009  ���{�R���s���[�^�[������� ��� ��F �V�K�쐬
 * @since V3.0.9
 *
 *****************************************************************
 */
public class IkenshoInsurerRelation implements  KeyListener, FocusListener, VRBindEventListener {
    // DB
    private ACDBManager dbManager;
    
    private boolean convertNo = false;
    private boolean convertName = false;
    private boolean checkNo = false;
    private boolean checkName = false;
    
    private boolean isCommitInsurer = false;
    
    // �e�L�X�g�t�B�[���h
    private JTextField insurerNoText;
    private JTextField insurerNameText;
    // �e�L�X�g������̕ێ��p
    private String insurerNoValue;
    private String insurerNameValue;
    // �ˍ�����
    private VRList validInsurerListToNo;
    private VRList validInsurerListToName;
    
    private ACDBManager insurerDbm;
    // �t�H�[�}�b�g�C�x���g���X�i�[�̐���
    private ACFollowContainerFormatEventListener noFollowFormatListener = new ACFollowContainerFormatEventListener(
            true);
    private ACFollowContainerFormatEventListener nameFollowFormatListener = new ACFollowContainerFormatEventListener(
            true);
    
    /**
     * 
     */
    public IkenshoInsurerRelation() {
       super(); 
    }
    
    /**
     * �R���X�g���N�^
     * 
     * @param insurerNoText �ی��Ҕԍ��e�L�X�g
     * @param insurerNameText �ی��Җ��̃e�L�X�g
     * @param isChangeNo �ی��Ҕԍ�����ی��Җ��̂ɕϊ����邩
     * @param isChangeName �ی��Җ��̂���ی��Ҕԍ��ɕϊ����邩
     * @param isCheckNo �ی��Ҕԍ��̃`�F�b�N���s�����B
     * @param isCheckName �ی��Җ��̂̃`�F�b�N���s�����B
     */
    public IkenshoInsurerRelation(ACDBManager insurerDbm,
            JTextField insurerNoText, JTextField insurerNameText,
            boolean isChangeNo, boolean isChangeName, boolean isCheckNo,
            boolean isCheckName) throws Exception {
        super();
        // �f�[�^�x�[�X�̐ݒ�
        setInsurerDbm(insurerDbm);
        // �ŏ��͑S�Đ���Ƃ��Ă���
        if (insurerNoText != null) {
            validDataNo(insurerNoText, insurerNoText.getText());
        }
        if (insurerNameText != null) {
            validDataName(insurerNameText, insurerNameText.getText());
        }
        // �f�[�^�ݒ�
        buildData(insurerNoText, insurerNameText, isChangeNo, isChangeName ,isCheckNo , isCheckName);
    }
    
    /**
     * �m�F�p�̃f�[�^�쐬���s���܂��B
     * 
     * @param insurerNoText �ی��Ҕԍ��e�L�X�g
     * @param insurerNameText �ی��Җ��̃e�L�X�g
     * @param isChangeNo �ی��Ҕԍ�����ی��Җ��̂ɕϊ����邩
     * @param isChangeName �ی��Җ��̂���ی��Ҕԍ��ɕϊ����邩
     * @param isCheckNo �ی��Ҕԍ��̃`�F�b�N���s�����B
     * @param isCheckName �ی��Җ��̂̃`�F�b�N���s�����B
     * @throws Exception
     */
    public void buildData(JTextField insurerNoText, JTextField insurerNameText,
            boolean isChangeNo, boolean isChangeName , boolean isCheckNo, boolean isCheckName) throws Exception {
        // �K�v�ϐ��̐ݒ�
        setInsurerNoText(insurerNoText);
        setInsurerNameText(insurerNameText);
        setConvertNo(isChangeNo);
        setConvertName(isChangeName);
        setCheckNo(isCheckNo);
        setCheckName(isCheckName);
        // SQL�ɂ�茋�ʂ��擾���܂��B
        buildInsurerList();
        
    }

    /**
     * �w�肳�ꂽ�敪�����Ƀ`�F�b�N���s���܂��B
     * 
     * @return
     * @throws Exception
     */
    public boolean isValidInsurer() throws Exception{
        boolean resultNo = false;
        boolean resultName = false;
        // �f�[�^�̍Đݒ�i�N���X������̍ă`�F�b�N�ɑΉ��j
        buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                isConvertName(), isCheckNo(), isCheckName());
        // ���ʂ���Ȃ�G���[�ɂ���B
        if(isConvertNo() && getValidInsurerListToNo().isEmpty()) {
            return false;
        }
        if(isConvertName() && getValidInsurerListToName().isEmpty()) {
            return false; 
        }
        
        if(isConvertName()) {
            // �T�C�Y1���B�B�B�L��̏ꍇ�ɕ������݂���P�[�X����
            if(getValidInsurerListToNo().size() == 1) {
                VRMap insurerRecord = (VRMap)getValidInsurerListToNo().getData(0);
                String masterName = ACCastUtilities.toString(insurerRecord
                        .getData("INSURER_NAME"), "");
                // ��ʂ̕ی��Җ��̂ƃ}�X�^����擾�����ی��Җ��̂���v����ꍇ
                if(masterName.equals(getInsurerNameValue())) {
                    resultName = true;
                } else {
                    // ��v���Ȃ��ꍇ�̓G���[
                    resultName = false;
                }
            } else {
                resultName = false;
            }
        }
        
        if(isConvertNo()) {
            // ���̂͋�łȂ���
            if(getValidInsurerListToName().size() == 1) {
                VRMap insurerRecord = (VRMap)getValidInsurerListToName().getData(0);
                String masterNo = ACCastUtilities.toString(insurerRecord
                        .getData("INSURER_NO"), "");
                if(masterNo.equals(getInsurerNoValue())) {
                    resultNo = true;
                } else {
                    // ��v���Ȃ��ꍇ�̓G���[
                    resultNo = false;
                }
            } else {
                resultNo = false;
            }
        }
        // �o���̃`�F�b�N���s���ꍇ
        if(isConvertName() && isConvertNo()) {
            return (resultName && resultNo);
        }
        
        // ���ʂ̕ԋp
        if(isConvertName()) {
            return resultName;
            
        } else if(isConvertNo()) {
            return resultNo;
            
        } else {
            return false;
            
        }
    }
    
    /**
     * �ˍ����ʂ̍쐬
     * 
     * @throws Exception
     */
    protected void buildInsurerList() throws Exception {
        if(getInsurerDbm() != null) {
            // �}�X�^�ˍ��p��SQL
            // ���ʂ̊i�[�i���ʂ�SQL���s�͖h�~�j
            if(isConvertNo() && !"".equals(getInsurerNoValue())) {
                StringBuffer sbNoSql = new StringBuffer();
                sbNoSql.append("SELECT ");
                sbNoSql.append("M_INSURER.INSURER_NAME ");
                sbNoSql.append("FROM ");
                sbNoSql.append("M_INSURER ");
                sbNoSql.append("WHERE ");
                sbNoSql.append("M_INSURER.INSURER_NO =");
                sbNoSql.append(ACSQLSafeStringFormat.getInstance().format(getInsurerNoValue()));
                sbNoSql.append(" ORDER BY ");
                sbNoSql.append("M_INSURER.INSURER_NAME_KANA ");
                sbNoSql.append("ASC");
                
                // No����ɓˍ�����SQL
                setValidInsurerListToNo(getInsurerDbm().executeQuery(sbNoSql.toString()));
            } else {
                setValidInsurerListToNo(new VRArrayList());
            }
            
            if(isConvertName() && !"".equals(getInsurerNameValue()) ) {
                StringBuffer sbNameSql = new StringBuffer();
                sbNameSql.append("SELECT ");
                sbNameSql.append("M_INSURER.INSURER_NO ");
                sbNameSql.append("FROM ");
                sbNameSql.append("M_INSURER ");
                sbNameSql.append("WHERE ");
                sbNameSql.append("M_INSURER.INSURER_NAME =");
                sbNameSql.append(ACSQLSafeStringFormat.getInstance().format(getInsurerNameValue()));
                sbNameSql.append(" ORDER BY ");
                sbNameSql.append("M_INSURER.INSURER_NAME_KANA ");
                sbNameSql.append("ASC");
                
                // Name����ɓˍ�����SQL
                setValidInsurerListToName(getInsurerDbm().executeQuery(sbNameSql.toString()));
            } else {
                setValidInsurerListToName(new VRArrayList());
            }
        }
    }
    
    /**
     * �g�p���邩�͓䂾�������T�|�[�g����ꍇ�̏���
     */
    public void focusGained(FocusEvent e) {

    }
    
    /**
     * �t�H�[�J�X���X�g�C�x���g
     */
    public void focusLost(FocusEvent e) {
        // �R���|�[�l���g��Null�`�F�b�N
        if (e == null && getInsurerNoText() == null
                || getInsurerNameText() == null) {
            return;
        }
        
        applyInsurerData(e);
    }
    
    private void applyInsurerData(ComponentEvent e) {
        // �����p�ɒl�����o���B
        setInsurerNoValue(getInsurerNoText().getText());
        setInsurerNameValue(getInsurerNameText().getText());

        try{
            // �ϊ��������t���O
            boolean isConverted = false;
            // ���͔��f�t���O
            boolean isInput = false;
            // �f�[�^�̌���
            buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                    isConvertName(), isCheckNo(), isCheckName());
            // �C�x���g�����ی��Ҕԍ��e�L�X�g�ł���ꍇ
            if(e.getSource() == getInsurerNoText()) {
                // ���͕ی��Ҕԍ����猋�ʂ��擾�ł��Ă���ꍇ
                if(!getValidInsurerListToNo().isEmpty()) {
                    VRMap map = (VRMap)getValidInsurerListToNo().getData(0);
                    // �ی��Ҕԍ�����̃��X�g�t�H�[�J�X�ȊO�i�L�[���̓C�x���g�j�̏ꍇ�̂�
                    if (!(e instanceof FocusEvent)){
                        // �ϊ����ʂ𔽉f����
                        getInsurerNameText().setText(
                                ACCastUtilities.toString(ACCastUtilities.toString(map
                                                .getData("INSURER_NAME"), "")));
                        isInput = true;
                    }
                    // �ϊ�����
                    isConverted = true;
                    
                }
                
                if(isCheckNo()) {
                    // �����͂̏ꍇ
                    if(getInsurerNoValue().length() == 0) {
                        validDataNo(getInsurerNoText(), getInsurerNoValue());
                        return;
                    } else if(getInsurerNoValue().length() != 6) {
                        // �����s��
                        warningDataNo(getInsurerNoText(), getInsurerNoValue());
                        return;
                    }
                    if(isConverted) {
                        // ���͂����f���ꂽ�ꍇ�̂ݔ��f���L���ɂ���B
                        if(isInput) {
                            validDataName(getInsurerNameText(), getInsurerNameValue());
                        }
                        validDataNo(getInsurerNoText(), getInsurerNoValue());
                        
                    } else {
                        // �ϊ��ɐ������Ă��Ȃ�
                        warningDataNo(getInsurerNoText(), getInsurerNoValue());
                    }
                    
                } else {
                    // �`�F�b�N���Ȃ��ꍇ�́A�ꗥ����
                    validDataNo(getInsurerNoText(), getInsurerNoValue());
                }
                return;
                
            }
            // �ی��Җ��̂���̃��X�g�t�H�[�J�X�̏ꍇ
            if(e.getSource() == getInsurerNameText() && isConvertName()) {
                if(!getValidInsurerListToName().isEmpty()) {
                    VRMap map = (VRMap)getValidInsurerListToName().getData(0);
                    if (!(e instanceof FocusEvent)){
                        // �s�������˔ԍ��̕ϊ�������
                        getInsurerNoText().setText(
                                ACCastUtilities.toString(ACCastUtilities.toString(map
                                                .getData("INSURER_NO"), "")));
                        isInput = true;
                    }
                    isConverted = true;
                    
                }
                
                // �`�F�b�N����
                if(isCheckName()) {
                    // �����`�F�b�N
                    if(getInsurerNameValue().length() == 0) {
                        validDataName(getInsurerNameText(), getInsurerNameValue());
                        return;                
                    }
                    if(isConverted) {
                        // ���͂����f���ꂽ�ꍇ�̂ݔ��f���L���ɂ���B
                        if(isInput) {
                            validDataNo(getInsurerNoText(), getInsurerNoValue());
                        }
                        validDataName(getInsurerNameText(), getInsurerNameValue());
                        
                    } else {
                        // �ϊ��ɐ������Ă��Ȃ�
                        warningDataName(getInsurerNameText(), getInsurerNameValue());
                    }
                    
                } else {
                    // �`�F�b�N���Ȃ��ꍇ�́A�ꗥ����
                    validDataName(getInsurerNameText(), getInsurerNameValue());
                }
                return;
            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void applySource(VRBindEvent e) {
    }

    public void bindSource(VRBindEvent e) {
        // �o�C���h���ɓ����ŕێ����镶����Ɠ��͕����ɍ��ق��o�Ȃ��悤�ɔ��f����
        if (getInsurerNoText() != null) {
            setInsurerNoValue(getInsurerNoText().getText());
            // �󔒂Ȃ�OK
            if("".equals(getInsurerNoValue())) {
                validDataNo(getInsurerNoText(), getInsurerNoValue());
            }
        }
        if (getInsurerNameText() != null) {
            setInsurerNameValue(getInsurerNameText().getText());
            // �󔒂Ȃ�OK
            if("".equals(getInsurerNameValue())) {
                validDataName(getInsurerNameText(), getInsurerNameValue());
            }
        }
    }
    
    /**
     * �R���|�[�l���g�̃R���e�i���x����Ԃɂ��܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param data �ݒ�f�[�^
     */
    public void warningDataNo(Component comp, String data) {
        noFollowFormatListener.formatWarning(new VRFormatEvent(comp, null, data));
    }
    
    /**
     * �R���|�[�l���g�̃R���e�i��L����Ԃɂ��܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param data �ݒ�f�[�^
     */
    public void validDataNo(Component comp, String data) {
        noFollowFormatListener.formatValid(new VRFormatEvent(comp, null, data));
    }
    
    /**
     * �R���|�[�l���g�̃R���e�i���x����Ԃɂ��܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param data �ݒ�f�[�^
     */
    public void warningDataName(Component comp, String data) {
        nameFollowFormatListener.formatWarning(new VRFormatEvent(comp, null, data));
    }
    
    /**
     * �R���|�[�l���g�̃R���e�i��L����Ԃɂ��܂��B
     * 
     * @param comp �ΏۃR���|�[�l���g
     * @param data �ݒ�f�[�^
     */
    public void validDataName(Component comp, String data) {
        nameFollowFormatListener.formatValid(new VRFormatEvent(comp, null, data));
    }

    /**
     * DBManager�̐���
     * 
     * @return
     * @throws Exception
     */
    public ACDBManager getDbManager() throws Exception{
        if(dbManager == null) {
            dbManager = new BridgeFirebirdDBManager();
        }
        return dbManager;
    }

    /**
     * DBManager��ݒ肷��B
     * @param dbManager
     */
    public void setDbManager(ACDBManager dbManager) {
        this.dbManager = dbManager;
    }

    public ACFollowContainerFormatEventListener getNoFollowFormatListener() {
        if(noFollowFormatListener == null) {
            noFollowFormatListener = new ACFollowContainerFormatEventListener();
        }
        return noFollowFormatListener;
    }

    public void setNoFollowFormatListener(
            ACFollowContainerFormatEventListener followFormatListener) {
        this.noFollowFormatListener = followFormatListener;
    }

    /**
     * �ی��Җ��e�L�X�g�t�B�[���h���擾���܂��B
     * @return
     */
    public JTextField getInsurerNameText() {
        return insurerNameText;
    }

    /**
     * �ی��Җ��e�L�X�g�ݒ�
     * @param insurerNameText
     */
    public void setInsurerNameText(JTextField insurerNameText) {
        // �e�L�X�g�̕�����͕ێ����Ă���
        if(getInsurerNameText() != null) {
            // ����܂łɐݒ肳��Ă����e�L�X�g����t�H�[�J�X���X�i������
            getInsurerNameText().removeFocusListener(this);
            getInsurerNameText().removeKeyListener(this);
            if (getInsurerNameText() instanceof VRBindable) {
                ((VRBindable) getInsurerNameText()).removeBindEventListener(this);
            }

        }
        this.insurerNameText = insurerNameText;
        // �V���ɐݒ肳�ꂽ�e�L�X�g�Ƀt�H�[�J�X���X�i��ǉ�
        if (getInsurerNameText() != null) {
            getInsurerNameText().addFocusListener(this);
            getInsurerNameText().addKeyListener(this);
            setInsurerNameValue(getInsurerNameText().getText());
            if (getInsurerNameText() instanceof VRBindable) {
                ((VRBindable) getInsurerNameText()).addBindEventListener(this);
            }
        }
        
    }

    /**
     * �ی��Ҕԍ��e�L�X�g�t�B�[���h���擾���܂��B
     * @return
     */
    public JTextField getInsurerNoText() {
        return insurerNoText;
    }

    /**
     * �ی��Ҕԍ��e�L�X�g�t�B�[���h��ݒ肵�܂��B
     * @param insurerNoText
     */
    public void setInsurerNoText(JTextField insurerNoText) {
        // �e�L�X�g�̕�����͕ێ����Ă���
        if(getInsurerNoText() != null) {
            // ����܂łɐݒ肳��Ă����e�L�X�g����t�H�[�J�X���X�i������
            getInsurerNoText().removeFocusListener(this);
            getInsurerNoText().removeKeyListener(this);
            if (getInsurerNoText() instanceof VRBindable) {
                ((VRBindable) getInsurerNoText()).removeBindEventListener(this);
            }

        } 
        this.insurerNoText = insurerNoText;
        // �V���ɐݒ肳�ꂽ�e�L�X�g�Ƀt�H�[�J�X���X�i��ǉ�
        if (getInsurerNoText() != null) {
            getInsurerNoText().addFocusListener(this);
            getInsurerNoText().addKeyListener(this);
            setInsurerNoValue((getInsurerNoText().getText()));
            if (getInsurerNoText() instanceof VRBindable) {
                ((VRBindable) getInsurerNoText()).addBindEventListener(this);
            }
        }
    }
    
    /**
     * �ی��Ҕԍ��e�L�X�g�ɐݒ肳��Ă���C�x���g���폜���܂��B
     * 
     * @throws Exception
     */
    public void deleteInsurerNoListener() throws Exception {
        if(getInsurerNoText() != null) {
            // ����܂łɐݒ肳��Ă����e�L�X�g����t�H�[�J�X���X�i������
            getInsurerNoText().removeFocusListener(this);
            getInsurerNoText().removeKeyListener(this);
            if (getInsurerNoText() instanceof VRBindable) {
                ((VRBindable) getInsurerNoText()).removeBindEventListener(this);
            }
            validDataNo(getInsurerNoText(), "");
        } 
    }
    
    /**
     * �ی��Җ��̃e�L�X�g�ɐݒ肳��Ă���C�x���g���폜���܂��B
     * 
     * @throws Exception
     */
    public void deleteInsurerNameListener() throws Exception {
        if(getInsurerNameText() != null) {
            // ����܂łɐݒ肳��Ă����e�L�X�g����t�H�[�J�X���X�i������
            getInsurerNameText().removeFocusListener(this);
            getInsurerNameText().removeKeyListener(this);
            if (getInsurerNameText() instanceof VRBindable) {
                ((VRBindable) getInsurerNameText()).removeBindEventListener(this);
            }
            validDataName(getInsurerNameText(), "");
        }
    }
    
    /**
     * �ی��Ҕԍ��`�F�b�N�p�̌��ʃZ�b�g���擾���܂��B
     * @return
     */
    public VRList getValidInsurerListToNo() {
        if(validInsurerListToNo == null) {
            validInsurerListToNo = new VRArrayList();
        }
        return validInsurerListToNo;
    }
    
    /**
     * �ی��Ҕԍ��`�F�b�N�p�̌��ʃZ�b�g��ݒ肵�܂��B
     * @param validInsurerList
     */
    public void setValidInsurerListToNo(VRList validInsurerList) {
        this.validInsurerListToNo = validInsurerList;
    }

    /**
     * �ی��Җ��̂��擾���܂��B
     * @return
     */
    public String getInsurerNameValue() {
        if(insurerNameValue == null) {
            insurerNameValue = new String();
        }
        return insurerNameValue;
    }

    /**
     * �ی��Җ��̂�ݒ肵�܂��B
     * @param insurerNameValue
     */
    public void setInsurerNameValue(String insurerNameValue) {
        this.insurerNameValue = insurerNameValue;
    }

    /**
     * �ی��Ҕԍ����擾���܂��B
     * @return
     */
    public String getInsurerNoValue() {
        if(insurerNoValue == null) {
            insurerNoValue = new String();
        }
        return insurerNoValue;
    }

    /**
     * �ی��Ҕԍ���ݒ肵�܂��B
     * @param insurerNoValue
     */
    public void setInsurerNoValue(String insurerNoValue) {
        this.insurerNoValue = insurerNoValue;
    }

    /**
     * �ی��Җ��̃`�F�b�N�̌��ʃZ�b�g���擾���܂��B
     * @return
     */
    public VRList getValidInsurerListToName() {
        if(validInsurerListToName == null) {
            validInsurerListToName = new VRArrayList();
        }
        return validInsurerListToName;
    }

    /**
     * �ی��Җ��̃`�F�b�N�̌��ʃZ�b�g��ݒ肵�܂��B
     * @param validInsurerListToName
     */
    public void setValidInsurerListToName(VRList validInsurerListToName) {
        this.validInsurerListToName = validInsurerListToName;
    }

    /**
     * ���̂���Ƀ`�F�b�N���s�����B
     * @return
     */
    public boolean isConvertName() {
        return convertName;
    }

    /**
     * �ԍ�����Ƀ`�F�b�N���s�����B
     * @param isCheckName
     */
    public void setConvertName(boolean isCheckName) {
        this.convertName = isCheckName;
    }

    /**
     * �ی��Ҕԍ��`�F�b�N�̃t���O���擾���܂��B
     * 
     * @return
     */
    public boolean isConvertNo() {
        return convertNo;
    }

    /**
     * �ی��Ҕԍ��`�F�b�N�̃t���O��ݒ肵�܂�
     * 
     * @param isCheckNo
     */
    public void setConvertNo(boolean isCheckNo) {
        this.convertNo = isCheckNo;
    }

    /**
     * �ی��Ҕԍ��}�X�^���擾���܂��B
     * @return
     */
    public ACDBManager getInsurerDbm() {
        return insurerDbm;
    }

    /**
     * �ی��Ҕԍ��}�X�^��ݒ肵�܂��B
     * @param insurerDbm
     */
    public void setInsurerDbm(ACDBManager insurerDbm) {
        this.insurerDbm = insurerDbm;
    }

    /**
     * �L�[�������Ă���Ƃ��ɌĂяo����܂��B
     */
	public void keyPressed(KeyEvent e) {
	}

	/**
	 * �L�[�𗣂����Ƃ��ɌĂяo����܂��B
	 */
	public void keyReleased(KeyEvent e) {
		
        // �R���|�[�l���g��Null�`�F�b�N
        if (e == null && getInsurerNoText() == null
                || getInsurerNameText() == null) {
            return;
        }
		
        if(e.getSource() == null) {
        	return;
        }
        
        // �ی��Ҕԍ��̓���
        if(e.getSource().equals(getInsurerNoText())) {
    		if (getInsurerNoText().getText().length() != 6){
    			isCommitInsurer = false;
    			return;
    		}
            
    		if (!isCommitInsurer) {
    	        applyInsurerData(e);
    	        isCommitInsurer = true;
    		}
            return;
        }
        
        // �ی��Җ�����
        if(e.getSource().equals(getInsurerNameText())) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                // �G���^�[�L�[�������ꂽ
                applyInsurerData(e);
            }
            
            return;
        }
        
	}

	/**
	 * �L�[���^�C�v����ƌĂяo����܂��B
	 */
	public void keyTyped(KeyEvent e) {
	}

	/**
     * �ی��Ҕԍ��Ɋւ���`�F�b�N����
     *  
     * @throws Exception
	 */
    public void checkInsurerNo() throws Exception {
        boolean isConverted = false;
        // �����p�ɒl�����o���B
        setInsurerNoValue(getInsurerNoText().getText());
        
        // �f�[�^�̌���
        buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                isConvertName(), isCheckNo(), isCheckName());
        
        // ���͕ی��Ҕԍ����猋�ʂ��擾�ł��Ă���ꍇ
        if(!getValidInsurerListToNo().isEmpty()) {
            isConverted = true;
        }
        
        if(isCheckNo()) {
            // �����͂̏ꍇ
            if(getInsurerNoValue().length() == 0) {
                validDataNo(getInsurerNoText(), getInsurerNoValue());
                return;
            } else if(getInsurerNoValue().length() != 6) {
                // �����s��
                warningDataNo(getInsurerNoText(), getInsurerNoValue());
                return;
            }
            if(isConverted) {
                // �ϊ��ɐ��������̂őo������
                validDataNo(getInsurerNoText(), getInsurerNoValue());
            } else {
                // �ϊ��ɐ������Ă��Ȃ�
                warningDataNo(getInsurerNoText(), getInsurerNoValue());
            }
            
        } else {
            // �`�F�b�N���Ȃ��ꍇ�́A�ꗥ����
            validDataNo(getInsurerNoText(), getInsurerNoValue());
        }
        
        getInsurerNoText().revalidate();
        getInsurerNoText().repaint();
    
    }
    
    /**
     * �ی��Җ��̂Ɋւ���`�F�b�N����
     *  
     * @throws Exception
     */
    public void checkInsurerName() throws Exception {
        boolean isConverted = false;
        
        // �����p�ɒl�����o���B
        setInsurerNameValue(getInsurerNameText().getText());
        // �f�[�^�̌���
        buildData(getInsurerNoText(), getInsurerNameText(), isConvertNo(),
                isConvertName(), isCheckNo(), isCheckName());

        if(!getValidInsurerListToName().isEmpty()) {
            isConverted = true;
        }
        
        // �`�F�b�N����
        if(isCheckName()) {
            // �����`�F�b�N
            if(getInsurerNameValue().length() == 0) {
                validDataName(getInsurerNameText(), getInsurerNameValue());
                return;                
            }
            if(isConverted) {
                // �ϊ��ɐ��������̂őo������
                validDataName(getInsurerNameText(), getInsurerNameValue());
            } else {
                // �ϊ��ɐ������Ă��Ȃ�
                warningDataName(getInsurerNameText(), getInsurerNameValue());
            }
            
        } else {
            // �`�F�b�N���Ȃ��ꍇ�́A�ꗥ����
            validDataName(getInsurerNameText(), getInsurerNameValue());
        }

        getInsurerNameText().revalidate();
        getInsurerNameText().repaint();
        
    }

    /**
     * �ی��Җ��̂̃`�F�b�N���s�����𔻕ʂ���t���O���擾���܂��B
     * 
     * @return
     */
    public boolean isCheckName() {
        return checkName;
    }

    /**
     * �ی��Җ��̂̃`�F�b�N���s�����𔻕ʂ���t���O��ݒ肵�܂��B
     * 
     * @param checkName
     */
    public void setCheckName(boolean checkName) {
        this.checkName = checkName;
    }

    /**
     * �ی��Ҕԍ��̃`�F�b�N���s�����𔻕ʂ���t���O��ݒ肵�܂��B
     * 
     * @return
     */
    public boolean isCheckNo() {
        return checkNo;
    }

    /**
     * �ی��Ҕԍ��̃`�F�b�N���s�����𔻕ʂ���t���O��ݒ肵�܂��B
     * 
     * @param checkNo
     */
    public void setCheckNo(boolean checkNo) {
        this.checkNo = checkNo;
    }
    
}
