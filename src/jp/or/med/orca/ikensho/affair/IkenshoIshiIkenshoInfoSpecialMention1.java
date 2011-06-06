package jp.or.med.orca.ikensho.affair;

import java.awt.event.ItemEvent;

import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;

/**
 * 
 * IkenshoIshIkenshoInfoSpecialMention1�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/11
 */
public class IkenshoIshiIkenshoInfoSpecialMention1 extends
        IkenshoIkenshoInfoMentionH18 {
    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoIshiIkenshoInfoSpecialMention1() {
        try{
            jbInit();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    /**
     * ��ʍ\���Ɋւ��鏈�����s���܂��B
     */
    private void jbInit(){
        // �^�C�g���ݒ�
        getTitle().setText("�T�D���̑����L���ׂ�����");
        // ���x��������ύX
        getTokkiAbstraction1()
                .setText(
                        "��Q���x�敪�F���T�[�r�X���p�v��쐬�ɕK�v�Ȉ�w�I�Ȃ��ӌ��������L�ڂ��ĉ������B�Ȃ��A���㓙�ɕʓr�ӌ������߂��ꍇ�͂��̓��e�A���ʂ��L�ڂ��ĉ������B�i���񋟏���g�̏�Q�Ґ\���f�f���̎ʂ�����Y�t���Ē����Ă����\�ł��B�j");
        getMentionTokkiMoreAbstraction().setText("");
        
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        getMentionTokki().setColumns(92);
        getMentionTokki().setMaxRows(9);
        getMentionTokki().setRows(10);
        //[ID:0000514][Tozo TANAKA] 2009/09/10 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        
        // �p�l���ǉ��ւ̒�`
        addComponent();
    }
    
    /**
     * override���ē��L�����O���[�v�ւ̒ǉ��������`���܂��B
     */
    protected void addMentionTokkiGroupComponent(){
        getMentionTokkiGroup().add(getMentionTokkiAbstractions(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkis(), VRLayout.NORTH);
        getMentionTokkiGroup().add(getMentionTokkiMoreAbstractions(), VRLayout.NORTH);
    }
    
    /**
     * override���ă^�u�p�l���ւ̒ǉ����`���܂��B
     */
    protected void addComponent(){
        this.add(getTitle(), VRLayout.NORTH);
        this.add(getMentionTokkiGroup(), VRLayout.NORTH);
        
    }
    
    /**
     * ��Ë@�֓o�^��ʂ�\�����܂��B
     */
    public void showRegistHokensya() {
        // �������s��Ȃ��B
    }
    
    /**
     * �����_�����͉�ʂ�\�����܂��B
     */
    protected void showInputTestPoint() {
        // �������s��Ȃ��B
    }
    
    /** 
     * override���ď������s��Ȃ�
     */
    public boolean isTestPointCheckWarning() {
        return true;
    }
    
    /**
     * override���ď������s��Ȃ�
     */
    public boolean noControlWarning() {
        return true;
    }
    
    /**
     * �ی��Җ��̕ύX���������܂��B
     * 
     * @param e �C�x���g���
     */
    protected void changeInsurerName(ItemEvent e) {
        // �������s�킹�Ȃ��B
    }

    
    protected IkenshoExtraSpecialNoteDialog createMentionTeikeibunKubun(){
        // [ID:0000514][Tozo TANAKA] 2009/09/24 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        return new IkenshoExtraSpecialNoteDialog(
//                "���̑����L���ׂ�����",
//                IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, 400, 100, 8,
//                50);
        return new IkenshoExtraSpecialNoteDialog(
                "���̑����L���ׂ�����",
                IkenshoCommon.TEIKEI_ISHI_MENTION_NAME, getMentionTokki().getMaxLength(), getMentionTokki().getColumns(), getMentionTokki().getMaxRows(),
                50);
        // [ID:0000514][Tozo TANAKA] 2009/09/24 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
    }

}
