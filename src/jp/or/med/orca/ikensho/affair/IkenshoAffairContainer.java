/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;

import javax.swing.AbstractButton;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.core.ACAffairContainer;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACBrowseLogWritable;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.sql.ACSQLUtilities;
import jp.nichicom.ac.text.ACSQLSafeNullToZeroIntegerFormat;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.sql.IkenshoPassiveCheck;

//[ID:0000754][Shin Fujihara] 2012/09 edit begin 2012�N�x�Ή� �{�����O�o�͋@�\
//public class IkenshoAffairContainer extends ACAffairContainer {
public class IkenshoAffairContainer extends ACAffairContainer implements ACBrowseLogWritable {
//[ID:0000754][Shin Fujihara] 2012/09 edit end 2012�N�x�Ή� �{�����O�o�͋@�\
    private CopyActionAdapter copy = new CopyActionAdapter(this);

    private DeleteActionAdapter delete = new DeleteActionAdapter(this);
    private DetailActionAdapter detail = new DetailActionAdapter(this);
    private FindActionAdapter find = new FindActionAdapter(this);
    private InsertActionAdapter insert = new InsertActionAdapter(this);
    private PrintActionAdapter print = new PrintActionAdapter(this);
    private PrintTableActionAdapter printTable = new PrintTableActionAdapter(
            this);
    private TableDoubleClickAdapter tableDoubleClick = new TableDoubleClickAdapter(
            this);
    private TableSelectionAdapter tableSelected = new TableSelectionAdapter(
            this);
    private UpdateActionAdapter update = new UpdateActionAdapter(this);
    protected IkenshoPassiveCheck passiveChecker = new IkenshoPassiveCheck();
    public IkenshoAffairContainer() {
        super();
    }

    /**
     * ���������̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addCopyTrigger(AbstractButton comp) {
        comp.addActionListener(copy);
    }

    /**
     * �폜�����̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addDeleteTrigger(AbstractButton comp) {
        comp.addActionListener(delete);
    }

    /**
     * �ڍו\�������̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addDetailTrigger(AbstractButton comp) {
        comp.addActionListener(detail);
    }

    /**
     * ���������̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addFindTrigger(AbstractButton comp) {
        comp.addActionListener(find);
    }

    /**
     * �ǉ������̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addInsertTrigger(AbstractButton comp) {
        comp.addActionListener(insert);
    }

    /**
     * �폜�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    protected void addPassiveDeleteTask(ACPassiveKey key, int row) {
        passiveChecker.addPassiveDeleteTask(key, row);
    }

    /**
     * �ǉ��p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    protected void addPassiveInsertTask(ACPassiveKey key, int row) {
        passiveChecker.addPassiveInsertTask(key, row);
    }

    /**
     * �X�V�p�b�V�u�`�F�b�N�^�X�N��ǉ����܂��B
     * 
     * @param key ��r�L�[
     * @param row �Ώۍs�ԍ�
     */
    protected void addPassiveUpdateTask(ACPassiveKey key, int row) {
        passiveChecker.addPassiveUpdateTask(key, row);
    }

    /**
     * �ꗗ��������̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addPrintTableTrigger(AbstractButton comp) {
        comp.addActionListener(printTable);
    }

    /**
     * ��������̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addPrintTrigger(AbstractButton comp) {
        comp.addActionListener(print);
    }

    /**
     * �e�[�u���̃_�u���N���b�N�����̃g���K�ƂȂ�e�[�u����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void addTableDoubleClickedTrigger(JTable comp) {
        comp.addMouseListener(tableDoubleClick);
    }

    /**
     * �e�[�u���̃_�u���N���b�N�����̃g���K�ƂȂ�e�[�u����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void addTableDoubleClickedTrigger(ACTable comp) {
        comp.addMouseListener(tableDoubleClick);
        // addTableDoubleClickedTrigger(comp.getTable());
    }

    /**
     * �e�[�u���I�������̃g���K�ƂȂ�e�[�u����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void addTableSelectedTrigger(JTable comp) {
        comp.getSelectionModel().addListSelectionListener(tableSelected);
    }

    /**
     * �e�[�u���I�������̃g���K�ƂȂ�e�[�u����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void addTableSelectedTrigger(ACTable comp) {
        comp.addListSelectionListener(tableSelected);
    }

    /**
     * �X�V�����̃g���K�ƂȂ�{�^����ǉ����܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void addUpdateTrigger(AbstractButton comp) {
        comp.addActionListener(update);
    }

    /**
     * �p�b�V�u�`�F�b�N�^�X�N�����������܂��B
     */
    protected void clearPassiveTask() {
        passiveChecker.clearPassiveTask();
    }

    /**
     * �p�b�V�u�`�F�b�N�p�̌������ʂ����������܂��B
     */
    protected void clearReservedPassive() {
        passiveChecker.clearReservedPassive();
    }

    /**
     * Over ride���ĕ����������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void copyActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��copyActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * Over ride���č폜�������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��deleteActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * Over ride���ďڍו\���������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void detailActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��detailActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * Over ride���Č����������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void findActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��findActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ��t������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    protected String getDBSafeDate(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeDate(key, source);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�Ȑ��l������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    protected String getDBSafeNumber(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeNumber(key, source);
    }
    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�Ȑ��l������Ƃ��ĕԂ��܂��B
     * Null�̏ꍇ��0��Ԃ��܂��B
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    protected String getDBSafeNumberNullToZero(String key, VRBindSource source)
            throws ParseException {
        return ACSQLSafeNullToZeroIntegerFormat.getInstance().format(VRBindPathParser.get(key, source));
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȕ�����Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    protected String getDBSafeString(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeString(key, source);
    }

    /**
     * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ���������Ƃ��ĕԂ��܂��B
     * 
     * @param key �擾�L�[
     * @param source �\�[�X
     * @throws ParseException ��͗�O
     * @return �ϊ�����
     */
    protected String getDBSafeTime(String key, VRBindSource source)
            throws ParseException {
        return ACSQLUtilities.getInstance().getDBSafeTime(key, source);
    }

    /**
     * �p�b�V�u�`�F�b�N�����s���܂��B
     * 
     * @throws Exception ������O
     * @return boolean �`�F�b�N����
     */
    protected IkenshoFirebirdDBManager getPassiveCheckedDBManager()
            throws Exception {
        return passiveChecker.getPassiveCheckedDBManager();
    }

    /**
     * Over ride���Ēǉ��������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void insertActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��insertActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * ������Null�܂��͋󕶎��ł��邩��Ԃ��܂��B
     * 
     * @param obj �]��������
     * @return ������Null�܂��͋󕶎��ł��邩
     */
    protected boolean isNullText(Object obj) {
        return ACCommon.getInstance().isNullText(obj);
    }

    /**
     * PDF�t�@�C���𐶐����A��������PDF�t�@�C�����J���܂��B
     * 
     * @param pd ����f�[�^
     * @throws Exception
     */
    protected void openPDF(ACChotarouXMLWriter pd) throws Exception {
        ACChotarouXMLUtilities.getInstance().openPDF(pd);
    }

    /**
     * Over ride���Ĉ���������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void printActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��printActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * Over ride���Ĉꗗ����������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��printTableActionPerformed���\�b�h���Ă΂�܂���");
    }

    /**
     * ���������̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removeCopyTrigger(AbstractButton comp) {
        comp.removeActionListener(copy);
    }

    /**
     * �폜�����̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removeDeleteTrigger(AbstractButton comp) {
        comp.removeActionListener(delete);
    }

    /**
     * �ڍו\�������̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removeDetailTrigger(AbstractButton comp) {
        comp.removeActionListener(detail);
    }

    /**
     * ���������̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removeFindTrigger(AbstractButton comp) {
        comp.removeActionListener(find);
    }

    /**
     * �ǉ������̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removeInsertTrigger(AbstractButton comp) {
        comp.removeActionListener(insert);
    }

    /**
     * �ꗗ��������̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removePrintTableTrigger(AbstractButton comp) {
        comp.removeActionListener(printTable);
    }

    /**
     * ��������̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removePrintTrigger(AbstractButton comp) {
        comp.removeActionListener(print);
    }

    /**
     * �e�[�u���̃_�u���N���b�N�����̃g���K�ƂȂ�e�[�u�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void removeTableDoubleClickedTrigger(JTable comp) {
        comp.removeMouseListener(tableDoubleClick);
    }

    /**
     * �e�[�u���̃_�u���N���b�N�����̃g���K�ƂȂ�e�[�u�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void removeTableDoubleClickedTrigger(ACTable comp) {
        comp.removeMouseListener(tableDoubleClick);
    }

    /**
     * �e�[�u���I�������̃g���K�ƂȂ�e�[�u�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void removeTableSelectedTrigger(JTable comp) {
        comp.getSelectionModel().removeListSelectionListener(tableSelected);
    }

    /**
     * �e�[�u���I�������̃g���K�ƂȂ�e�[�u�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�e�[�u��
     */
    protected void removeTableSelectedTrigger(ACTable comp) {
        comp.getSelectionModel().removeListSelectionListener(tableSelected);
    }

    /**
     * �X�V�����̃g���K�ƂȂ�{�^�������O���܂��B
     * 
     * @param comp �g���K�ƂȂ�{�^��
     */
    protected void removeUpdateTrigger(AbstractButton comp) {
        comp.removeActionListener(update);
    }

    /**
     * �p�b�V�u�`�F�b�N�p�̌������ʂ�ޔ����܂��B
     * 
     * @param key ��r�L�[
     * @param data ��������
     */
    protected void reservedPassive(ACPassiveKey key, VRArrayList data) {
        passiveChecker.reservedPassive(key, data);
    }

    /**
     * Over ride���ăe�[�u���̃_�u���N���b�N�������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void tableDoubleClicked(MouseEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��tableDoubleClicked���\�b�h���Ă΂�܂���");
    }

    /**
     * Over ride���ăe�[�u���I���������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void tableSelected(ListSelectionEvent e) throws Exception {
        throw new RuntimeException("�����I��Over ride���K�v��tableSelected���\�b�h���Ă΂�܂���");
    }

    /**
     * Over ride���čX�V�������L�q���܂��B
     * 
     * @param e �C�x���g���
     * @throws Exception ������O
     */
    protected void updateActionPerformed(ActionEvent e) throws Exception {
        throw new RuntimeException(
                "�����I��Over ride���K�v��updateActionPerformed���\�b�h���Ă΂�܂���");
    }

//    /**
//     * �K��A�_�v�^�N���X
//     */
//    private abstract class AbstractActionAdapter implements ActionListener {
//        protected IkenshoAffairContainer adaptee;
//        protected boolean lockFlag = false;
//    }

    /**
     * �����\�������A�_�v�^�N���X
     */
    private class CopyActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public CopyActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.copyActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * �폜�����A�_�v�^�N���X
     */
    private class DeleteActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public DeleteActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.deleteActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * �ڍו\�������A�_�v�^�N���X
     */
    private class DetailActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public DetailActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.detailActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * ���������A�_�v�^�N���X
     */
    private class FindActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public FindActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.findActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * �ǉ������A�_�v�^�N���X
     */
    private class InsertActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public InsertActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.insertActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * ����\�������A�_�v�^�N���X
     */
    private class PrintActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public PrintActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.printActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * �ꗗ����\�������A�_�v�^�N���X
     */
    private class PrintTableActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public PrintTableActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.printTableActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * �e�[�u���̃_�u���N���b�N�����A�_�v�^�N���X
     */
    private class TableDoubleClickAdapter extends MouseAdapter {
        protected IkenshoAffairContainer adaptee;
        protected boolean lockFlag = false;

        public TableDoubleClickAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void mouseClicked(MouseEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            if (e.getClickCount() == 2) {
                try {
                    adaptee.tableDoubleClicked(e);
                } catch (Exception ex) {
                    ACCommon.getInstance().showExceptionMessage(ex);
                }
            }
            lockFlag = false;
        }

    }

    /**
     * �e�[�u���I�������A�_�v�^�N���X
     */
    private class TableSelectionAdapter implements ListSelectionListener {
        protected IkenshoAffairContainer adaptee;
        protected boolean lockFlag = false;

        public TableSelectionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void valueChanged(ListSelectionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.tableSelected(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    /**
     * �X�V�����A�_�v�^�N���X
     */
    private class UpdateActionAdapter extends AbstractIkenshoAffairContainerActionAdapter {
        public UpdateActionAdapter(IkenshoAffairContainer adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                adaptee.updateActionPerformed(e);
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
            lockFlag = false;
        }
    }

    //[ID:0000754][Shin Fujihara] 2012/09 edit begin 2012�N�x�Ή� �{�����O�o�͋@�\
	public void writeBrowseLog(ACAffairInfo affair) {
		IkenshoBrowseLogger.log(affair);
	}
	//[ID:0000754][Shin Fujihara] 2012/09 edit end 2012�N�x�Ή� �{�����O�o�͋@�\
	
}
