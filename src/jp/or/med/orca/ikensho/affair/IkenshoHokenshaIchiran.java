/** TODO <HEAD_IKENSYO> */
package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoInsurerTypeFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


public class IkenshoHokenshaIchiran extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private ACTable table = new ACTable();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem insertMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();
    private VRArrayList data;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new
        ACPassiveKey("INSURER", new String[] {"INSURER_NO", "INSURER_TYPE"}
                          , new Format[] {IkenshoConstants.FORMAT_PASSIVE_STRING, null}, "LAST_TIME", "LAST_TIME");

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoHokenshaIchiran() {
        try {
            jbInit();
            events();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        buttons.setTitle("�ی��҈ꗗ");
        this.add(buttons, VRLayout.NORTH);
        this.add(table, VRLayout.CLIENT);

        //�{�^���n
        buttons.add(delete, BorderLayout.EAST);
        buttons.add(insert, BorderLayout.EAST);
        buttons.add(detail, BorderLayout.EAST);

        detail.setText("�ڍ׏��(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("�ڍ׏��(E)");
        detail.setToolTipText("�I�����ꂽ�ی��҂̏ڍ׉�ʂֈڂ�܂��B");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        insert.setText("�V�K�o�^(N)");
        insert.setMnemonic('N');
        insert.setActionCommand("�V�K�o�^(N)");
        insert.setToolTipText("�ی��ҏ���V�K�ɍ쐬���܂��B");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);

        delete.setText("�폜(D)");
        delete.setMnemonic('D');
        delete.setActionCommand("�폜(D)");
        delete.setToolTipText("�I�����ꂽ�ی��҂��폜���܂��B");
        delete.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DELETE);

        //�|�b�v�A�b�v�n
        popup.add(detailMenu);
        popup.add(insertMenu);
        popup.add(deleteMenu);

        detailMenu.setActionCommand("�ڍ׏��(E)");
        detailMenu.setMnemonic('E');
        detailMenu.setText("�ڍ׏��(E)");
        detailMenu.setToolTipText(detail.getToolTipText());

        insertMenu.setActionCommand("�V�K�o�^(N)");
        insertMenu.setMnemonic('N');
        insertMenu.setText("�V�K�o�^(N)");
        insertMenu.setToolTipText(insert.getToolTipText());

        deleteMenu.setActionCommand("�폜(D)");
        deleteMenu.setMnemonic('D');
        deleteMenu.setText("�폜(D)");
        deleteMenu.setToolTipText(delete.getToolTipText());
    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        //���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        addDetailTrigger(detail);
        addDetailTrigger(detailMenu);
        addInsertTrigger(insert);
        addInsertTrigger(insertMenu);
        addDeleteTrigger(delete);
        addDeleteTrigger(deleteMenu);
        addTableSelectedTrigger(table);

        //DB����ی��҃f�[�^���擾
        doSelect(affair);
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent(){
        return table;
//      return table.getTable();
    }

    public boolean canBack(VRMap parameters) throws Exception{
      return true;
    }

    private void events() {
        //�e�[�u���I����
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    try {
                        detailActionPerformed(null);
                    }
                    catch (Exception ex) {
                      IkenshoCommon.showExceptionMessage(ex);
                    }
                }
                else {
                    showPopup(e);
                }
            }

            public void mousePressed(MouseEvent e) {
                showPopup(e);
            }

            public void mouseReleased(MouseEvent e) {
                showPopup(e);
            }
        });
    }

    protected void detailActionPerformed(ActionEvent e) throws Exception {
        //�I���s�̎擾
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //�ǂ̃{�^������̑J�ڂ��Ƃ�������t��
        VRMap selectedRow = (VRMap)data.getData(row);
        selectedRow.put("ACT", "detail");

        //�J��
        ACAffairInfo affair = new ACAffairInfo(IkenshoHokenshaShousai.class.
                                                 getName(), selectedRow, "�ی��ҏڍ�");
        ACFrame.getInstance().next(affair);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        //�ǂ̃{�^������̑J�ڂ��Ƃ�������t��
        VRMap selectedRow = new VRHashMap();
        selectedRow.put("ACT", "insert");

        //�J��
        ACAffairInfo affair = new ACAffairInfo(IkenshoHokenshaShousai.class.
                                                 getName(), selectedRow, "�ی��ҏڍ�");
        ACFrame.getInstance().next(affair);

        //�㏈���E�c�[���{�^���⃁�j���[�̗L����Ԃ̐ݒ�
        setButtonsEnabled();
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        delete.setEnabled(false);

        //�I���s�̎擾
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //�I���s�̘A�g��R�[�h���擾
        String insurerNo = String.valueOf(((VRMap)data.get(row)).getData("INSURER_NO"));
        String insurerType = String.valueOf(((VRMap)data.get(row)).getData("INSURER_TYPE"));
        
        //�ی��Ҕԍ������̃e�[�u���ɓo�^����Ă��Ȃ����`�F�b�N
        if (hasInsurerNoInOtherTable(insurerNo, "IKN_ORIGIN")) {
            ACMessageBox.show("�ӌ����Ɏg�p����Ă���ی��Ҕԍ��͍폜�ł��܂���B");
            doSelect(null);//��SELECT
            return;
        }
        if (hasInsurerNoInOtherTable(insurerNo, "JIGYOUSHA")) {
            ACMessageBox.show("���Ǝ҃}�X�^�Ɏg�p����Ă���ی��Ҕԍ��͍폜�ł��܂���B");
            doSelect(null);//��SELECT
            return;
        }


        //�m�FMSG
        String msg = "�I������Ă���ی��҂̃f�[�^�����ׂč폜����܂��B\n��낵���ł����H";
        int result = ACMessageBox.show(msg,
                                            ACMessageBox.BUTTON_OKCANCEL,
                                            ACMessageBox.ICON_QUESTION,
                                            ACMessageBox.FOCUS_CANCEL);
        if (result == ACMessageBox.RESULT_OK) {
            //�ŏI�I���ʒu(���f���ł͂Ȃ���ʏ�)��ޔ�
            int lastSelectedIndex = table.getSelectedSortedRow();

            //DB�ƃe�[�u������f�[�^���폜����
            IkenshoFirebirdDBManager dbm = null;
            try {
                //�p�b�V�u�`�F�b�N
                clearPassiveTask();
                addPassiveDeleteTask(PASSIVE_CHECK_KEY, row);
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
//                    //��SELECT
//                    doSelect(null);

                }else{
                //SQL���̐���
                String sql = "DELETE FROM INSURER WHERE INSURER_NO='" + insurerNo + "' AND INSURER_TYPE=" +insurerType;

                //SQL�̎��s
                dbm.executeUpdate(sql);

                //�R�~�b�g
                dbm.commitTransaction();
                }
            }
            catch (Exception ex) {
                //���[���o�b�N
                if (dbm != null) {
                    dbm.rollbackTransaction();
                }
                setButtonsEnabled();
                throw new Exception(ex);
            }

            //��SELECT
            doSelect(null);

            //�đI��
            table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);
        }
        // add sta s-fujihara �폜�L�����Z�����A�폜�{�^���������s�ƂȂ�
        // ��Q�̑Ή�
        else {
          setButtonsEnabled();
        }
        // add end s-fujihara
    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        setButtonsEnabled();
    }

    /**
     * �|�b�v�A�b�v���j���[��\�����܂��B
     * @param e �C�x���g���
     */
    private void showPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        popup.show( (Component) e.getSource(), e.getX(), e.getY());
      }
    }

    /**
     * �ی��Ҕԍ������̃e�[�u���Ŏg�p����Ă��Ȃ����ǂ������`�F�b�N����
     * @param insurerNo String
     * @return boolean
     * @throws Exception
     */
    private boolean hasInsurerNoInOtherTable(String insurerNo, String TableName) throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT" );
        sb.append( " INSURER_NO" );
        sb.append( " FROM" );
        sb.append( " " + TableName );
        sb.append( " WHERE" );
        sb.append( " INSURER_NO='" + insurerNo + "'" );
        VRArrayList cnt = (VRArrayList) dbm.executeQuery(sb.toString());
        if (cnt.getDataSize() > 0) {
            return true;
        }
        return false;
    }

    /**
     * �e�[�u���Ƀf�[�^��ݒ肵�܂��B
     * @param affair ��ʓn��p�����[�^
     * @throws Exception
     */
    private void doSelect(ACAffairInfo affair) throws Exception {
        //DB����f�[�^���擾
        doSelectFromDB();

        //�{�^����Enabled�ݒ�
        setButtonsEnabled();

        //�e�[�u���Ƀt�H�[�J�X�𓖂Ă�
        table.requestFocus();
//        table.getTable().requestFocus();

        //�X�e�[�^�X�o�[
        setStatusText(String.valueOf(table.getRowCount()) + "���o�^����Ă��܂��B");

        //�e�[�u���̍s��I��
        setInitSelectedRow(affair);
    }

    /**
     * DB����f�[�^���擾���܂��B
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT " );
        sb.append( " INSURER_NO," );
        sb.append( " INSURER_NM," );
        sb.append( " INSURER_TYPE," );
        sb.append( " FD_OUTPUT_UMU," );
        sb.append( " SEIKYUSHO_OUTPUT_PATTERN," );
        sb.append( " ISS_INSURER_NM," );
        sb.append( " SKS_INSURER_NM," );
        sb.append( " LAST_TIME" );
        sb.append( " FROM");
        sb.append( " INSURER" );
        sb.append( " ORDER BY" );
        sb.append( " INSURER_NO ASC" );
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            //�p�b�V�u�`�F�b�N
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }

        //�e�[�u���̐���
        table.setModel(new ACTableModelAdapter(data, new String[] {
                                               "INSURER_NO",
                                               "INSURER_NM",
                                               "INSURER_TYPE",
                                               "ISS_INSURER_NM",
                                               "SKS_INSURER_NM",
                                               "FD_OUTPUT_UMU",
                                               "SEIKYUSHO_OUTPUT_PATTERN"}));

        //ColumnModel�̐���
        table.setColumnModel(new VRTableColumnModel(
            new VRTableColumn[] {
            new VRTableColumn(0, 70, "�ی��Ҕԍ�"),
            new VRTableColumn(1, 167, "�ی��Җ���"),
            new VRTableColumn(2, 50, "�敪", SwingConstants.CENTER, IkenshoInsurerTypeFormat.getInstance()),
            new VRTableColumn(3, 167, "�ӌ����쐬��������"),
            new VRTableColumn(4, 167, "�f�@�E������������"),
            new VRTableColumn(5, 50, "FD�o��", SwingConstants.CENTER, IkenshoConstants.FORMAT_UMU),
            new VRTableColumn(6, 150, "�����p�^�[��", IkenshoConstants.FORMAT_SEIKYUSHO_OUTPUT_PATTERN)
        })
            );
    }

    /**
     * �e�[�u���̏����I���s�̑I�����s���܂��B
     * @param affair IkenshoAffair
     * @throws Exception
     */
    private void setInitSelectedRow(ACAffairInfo affair) throws Exception {
        int selectedRow = 0;

        //�n��p�����[�^�̃f�[�^��I��
        if (affair != null) {
            //�n��p�����[�^�擾
            VRMap params = affair.getParameters();
            if (params != null) {
                if (params.size() > 0) {
                    String key = "INSURER_NO";
                    String value = String.valueOf(params.getData(key));
                    String key2 = "INSURER_TYPE";
                    String value2 = String.valueOf(params.getData(key));
                    if (value != null) {
                        //�n��f�[�^�̍s������
                        for (int i = 0; i < data.size(); i++) {
                            VRMap row = (VRMap)data.getData(i);
                            String tmp = String.valueOf(row.getData(key));
                            if (tmp.equals(value)) {
                                String tmp2 = String.valueOf(row.getData(key2));
                                if (tmp2.equals(value2)) {
                                    selectedRow = i;
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        //�e�[�u���̍s��I��
        if (table.getRowCount() > 0) {
            table.setSelectedModelRow(selectedRow);
        }
    }

    /**
     * �c�[���{�^���A���j���[�̗L����Ԃ�ݒ肵�܂��B
     */
    private void setButtonsEnabled() {
        boolean enabled = false;
        if (table.getRowCount() > 0) {
            if (table.getSelectedModelRow() < 0) {
                enabled = false;
            }
            else {
                enabled = true;
            }
        }

        detail.setEnabled(enabled);
        delete.setEnabled(enabled);
        detailMenu.setEnabled(enabled);
        deleteMenu.setEnabled(enabled);
    }
}
