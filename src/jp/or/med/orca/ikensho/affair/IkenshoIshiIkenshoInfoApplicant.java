package jp.or.med.orca.ikensho.affair;

/**
 * 
 * IkenshoIshiIkenshoInfoApplicant�ł��B
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2006/07/06
 */
public class IkenshoIshiIkenshoInfoApplicant extends
        IkenshoIkenshoInfoApplicantH18 {

    public IkenshoIshiIkenshoInfoApplicant() {
        try {
            jbInit();
          }
          catch (Exception e) {
            e.printStackTrace();
          }
        }
    
        private void jbInit() throws Exception {
          getAgreeGroup().setText("�厡��Ƃ��Ė{�ӌ������T�[�r�X���p�v��쐬�ɗ��p����邱�Ƃ�");
        }

        /**
         * ���ی��K�p�͈͓��̔N��ł��邩���`�F�b�N���܂��B
         */
        protected void checkWriteDateAge() {
            // ���\�b�h��override�ɂ�菈�����s�킹�Ȃ��B
        }
        
}
