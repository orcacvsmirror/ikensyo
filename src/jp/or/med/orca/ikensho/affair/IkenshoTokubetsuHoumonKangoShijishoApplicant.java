package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.layout.VRLayout;


// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
public class IkenshoTokubetsuHoumonKangoShijishoApplicant extends
        IkenshoHoumonKangoShijishoApplicant {

    protected ACButton twoWeekButton;
    
    public IkenshoTokubetsuHoumonKangoShijishoApplicant() {
        super();
        // �v���F��̏󋵂͏���
        youkaigoJoukyouGrp.setVisible(false);
        // 2�T�Ԑݒ�{�^���̒ǉ�
        getTwoWeekButton().addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                // ���̓`�F�b�N
                if ("".equals(sijiKikanFrom.getYear())
                        || "".equals(sijiKikanFrom.getMonth())
                        || "".equals(sijiKikanFrom.getDay())) {
                    ACMessageBox.showExclamation("�w�����ԁu�J�n���t�v�Ɍ�肪����܂��B");
                    sijiKikanFrom.requestChildFocus();
                    return;
                }
                // ���t�^�Ŏ擾
                Date fromDate = sijiKikanFrom.getDate();
                if(fromDate instanceof Date) {
                    // 2�T�ԕ��ǉ�
                     sijiKikanTo.setDate(ACDateUtilities.addDay(fromDate, 13));
                } else {
                    ACMessageBox.showExclamation("�w�����ԁu�J�n���t�v�Ɍ�肪����܂��B");
                    sijiKikanFrom.requestChildFocus();
                }
            }
          });
    }

    protected String getHoumonSijiSyoText() {
        return "���ʖK��Ō�w����";
    }

    protected String getSijiKikanContainerText() {
        return "���ʊŌ�w������";
    }

    /**
     * �K��Ō�w�����ԃR���e�i�ɃR���|�[�l���g��ǉ����܂��B
     *
     */
    protected void addSijiKikanContainer() {
        sijiKikanContainer.add(sijiKikanFroms, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanSep, VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanTos, VRLayout.FLOW);
        sijiKikanContainer.add(getTwoWeekButton(), VRLayout.FLOW);
        sijiKikanContainer.add(sijiKikanClear, VRLayout.FLOW);
    }

    /**
     * 2�T�ԃ{�^�����擾���܂��B
     * 
     * @return
     */
    public ACButton getTwoWeekButton() {
        if(twoWeekButton == null){
            twoWeekButton = new ACButton();
            twoWeekButton.setText("2�T��");
            twoWeekButton.setMnemonic('2');
        }
        return twoWeekButton;
    }

    
}
// [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
