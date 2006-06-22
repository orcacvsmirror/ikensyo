package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoTabbableChildAffairContainer extends VRPanel {
    protected ArrayList innerBindComponents = new ArrayList();
    protected IkenshoTabbableAffairContainer masterAffair;

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoTabbableChildAffairContainer() {
        super();
    }

    /**
     * �Ɩ��J�n���Ƀt�H�[�J�X���Z�b�g����R���|�[�l���g��Ԃ��܂��B
     * 
     * @return �Ɩ��J�n���Ƀt�H�[�J�X���Z�b�g����R���|�[�l���g
     */
    public Component getFirstFocusComponent() {
        return null;
    }

    /**
     * �R���g���[���Ƃ��Čx���������s��������Ԃ��܂��B<br />
     * apply�O�ɌĂ΂�܂��B
     * 
     * @throws Exception ������O
     * @return �x���������s������
     */
    boolean noControlWarning() throws Exception {
        return true;

    }

    /**
     * �R���g���[���Ƃ��čX�V�������\����Ԃ��܂��B<br />
     * apply�O�ɌĂ΂�܂��B
     * 
     * @throws Exception ������O
     * @return �X�V�������\��
     */
    boolean noControlError() throws Exception {
        return true;
    }

    /**
     * �f�[�^�Ƃ��čX�V�������\����Ԃ��܂��B<br />
     * apply��ɌĂ΂�܂��B
     * 
     * @throws Exception ������O
     * @return �X�V�������\��
     */
    boolean canDataUpdate() throws Exception {
        return true;
    }

    /**
     * ������Null�܂��͋󕶎��ł��邩��Ԃ��܂��B
     * 
     * @param text �]��������
     * @return ������Null�܂��͋󕶎��ł��邩
     */
    protected boolean isNullText(Object text) {
        return IkenshoCommon.isNullText(text);
    }

    /**
     * �ǉ������Ώۂ̃R���|�[�l���g��ǉ����܂��B<br />
     * VR�R���|�[�l���g�ɑ΂��A�R���e�i�Ƃ��Ė����I�ɒǉ������R���|�[�l���g��ǉ����܂��B
     * 
     * @param comp �R���|�[�l���g
     */
    protected void addInnerBindComponent(VRBindable comp) {
        innerBindComponents.add(comp);
    }

    /**
     * �ǉ������Ώۂ̃R���|�[�l���g���폜���܂��B<br />
     * VR�R���|�[�l���g�ɑ΂��A�R���e�i�Ƃ��Ė����I�ɒǉ������R���|�[�l���g���폜���܂��B
     * 
     * @param comp �R���|�[�l���g
     */
    protected void removeInnerBindComponent(VRBindable comp) {
        innerBindComponents.remove(comp);
    }

    /**
     * �ǉ������Ώۂ̃R���|�[�l���g�ɎQ�Ɛ�\�[�X��ݒ肵�܂��B
     * 
     * @param source �Q�Ɛ�\�[�X
     */
    protected void setSourceInnerBindComponent(VRBindSource source) {
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            ((VRBindable) it.next()).setSource(source);
        }
    }

    /**
     * �ǉ������Ώۂ̃R���|�[�l���g�̒l���Q�Ɛ�\�[�X�ɓK�p���܂��B
     * 
     * @throws Exception ��͗�O
     */
    protected void applySourceInnerBindComponent() throws Exception {
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            ((VRBindable) it.next()).applySource();
        }
    }

    /**
     * �ǉ������Ώۂ̃R���|�[�l���g�ɎQ�Ɛ�\�[�X�̒l�𗬂����݂܂��B
     * 
     * @throws Exception ��͗�O
     */
    protected void bindSourceInnerBindComponent() throws Exception {
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            ((VRBindable) it.next()).bindSource();
        }
    }

    /**
     * �ǉ������Ώۂ̃R���|�[�l���g�̃f�t�H���g�f�[�^���i�[�����\�[�X�C���X�^���X�𐶐����܂��B
     * 
     * @return �q�v�f�C���X�^���X
     */
    public VRMap createSourceInnerBindComponent() {
        VRMap map = new VRHashMap();
        Iterator it = innerBindComponents.iterator();
        while (it.hasNext()) {
            VRBindable comp = (VRBindable) it.next();
            map.setData(comp.getBindPath(), comp.createSource());
        }
        return map;
    }

    /**
     * �R���{�ւ̒�^���ݒ�Ȃ�DB�ւ̃A�N�Z�X��K�v�Ƃ��鏉���������𐶐����܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    public void initDBCopmponent(IkenshoFirebirdDBManager dbm) throws Exception {

    }

    /**
     * ���Ɩ��N���X�����\�[�X��Ԃ��܂��B
     * 
     * @return ���Ɩ��N���X�����\�[�X
     */
    public VRBindSource getMasterSource() {
        return getSource();
    }

    /**
     * ���Ɩ��N���X�����\�[�X��ݒ肵�܂��B
     * 
     * @param masterSource ���Ɩ��N���X�����\�[�X
     */
    public void setMasterSource(VRBindSource masterSource) {
        setSource(masterSource);
    }

    /**
     * �O��ʂƂ��đJ�ڂ��Ă������Ƀt�H�[�J�X���Z�b�g����R���|�[�l���g��Ԃ��܂��B
     * 
     * @return �O��ʂƂ��đJ�ڂ��Ă������Ƀt�H�[�J�X���Z�b�g����R���|�[�l���g
     */
    public Component getPreviewFocusComponent() {
        return null;
    }

    /**
     * ���Ɩ��N���X��Ԃ��܂��B
     * 
     * @return ���Ɩ��N���X
     */
    public IkenshoTabbableAffairContainer getMasterAffair() {
        return masterAffair;
    }

    /**
     * ���Ɩ��N���X��ݒ肵�܂��B
     * 
     * @param masterAffair ���Ɩ��N���X
     */
    public void setMasterAffair(IkenshoTabbableAffairContainer masterAffair) {
        this.masterAffair = masterAffair;
    }

    /**
     * �O��ʕ����p�̃f�[�^��ۑ����܂��B
     * 
     * @param param �f�[�^�i�[��
     * @throws Exception ������O
     */
    protected void savePreviewData(VRMap param) throws Exception {
        if (getMasterAffair() != null) {
            // �ŐV��K�p
            IkenshoTabbableAffairContainer info = getMasterAffair();
            info.fullApplySource();

            VRBindSource bs = getMasterSource();

            bs.setData("TAB", new Integer(info.getSelctedTabIndex()));
            bs.setData("AFFAIR_MODE", info.getNowMode());
            param.setData("PREV_DATA", bs);
        }
    }

    /**
     * �v�[��������^�����R���{�ɐݒ肵�܂��B
     * 
     * @param combo �R���{
     * @param code ��^���R�[�h
     */
    protected void applyPoolTeikeibun(JComboBox combo, int code) {
        if (getMasterAffair() != null) {
            getMasterAffair().applyPoolTeikeibun(combo, code);
        }
    }

    /**
     * ������A�K�p���s�Ȃ����O�ɏ������s�Ȃ��܂��B
     * 
     * @param dbm DBManager
     * @throws Exception ������O
     */
    public void doBeforeBindOnSelected() throws Exception {

    }
}
