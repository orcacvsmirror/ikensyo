package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
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
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoHoumonKangoStationJouhouIchiran extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton copy = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private ACTable table = new ACTable();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem insertMenu = new JMenuItem();
    private JMenuItem copyMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();
    private VRArrayList data;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new
        ACPassiveKey("STATION", new String[] {"STATION_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoHoumonKangoStationJouhouIchiran() {
        try {
            jbInit();
            events();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * �R���|�[�l���g��ݒ肵�܂��B
     * @throws Exception �ݒ��O
     */
    private void jbInit() throws Exception {
        buttons.setTitle("�K��Ō�X�e�[�V�������ꗗ");
        this.add(buttons, VRLayout.NORTH);
        this.add(table, VRLayout.CLIENT);

        //�{�^���n
        buttons.add(delete, BorderLayout.EAST);
        buttons.add(copy, BorderLayout.EAST);
        buttons.add(insert, BorderLayout.EAST);
        buttons.add(detail, BorderLayout.EAST);

        detail.setText("�ڍ׏��(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("�ڍ׏��(E)");
        detail.setToolTipText("�I�����ꂽ�K��Ō�X�e�[�V�������̏ڍ׉�ʂֈڂ�܂��B");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        insert.setText("�V�K�o�^(N)");
        insert.setMnemonic('N');
        insert.setActionCommand("�V�K�o�^(N)");
        insert.setToolTipText("�K��Ō�X�e�[�V��������V�K�ɍ쐬���܂��B");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);

        copy.setText("����(C)");
        copy.setMnemonic('C');
        copy.setActionCommand("����(C)");
        copy.setToolTipText("�I�����ꂽ�K��Ō�X�e�[�V�������𕡐����܂��B");
        copy.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_COPY);

        delete.setText("�폜(D)");
        delete.setMnemonic('D');
        delete.setActionCommand("�폜(D)");
        delete.setToolTipText("�I�����ꂽ�K��Ō�X�e�[�V���������폜���܂��B");
        delete.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DELETE);

        //�|�b�v�A�b�v�n
        popup.add(detailMenu);
        popup.add(insertMenu);
        popup.add(copyMenu);
        popup.add(deleteMenu);

        detailMenu.setActionCommand("�ڍ׏��(E)");
        detailMenu.setMnemonic('E');
        detailMenu.setText("�ڍ׏��(E)");
        detailMenu.setToolTipText(detail.getToolTipText());

        insertMenu.setActionCommand("�V�K�o�^(N)");
        insertMenu.setMnemonic('N');
        insertMenu.setText("�V�K�o�^(N)");
        insertMenu.setToolTipText(insert.getToolTipText());

        copyMenu.setActionCommand("����(C)");
        copyMenu.setMnemonic('C');
        copyMenu.setText("����(C)");
        copyMenu.setToolTipText(copy.getToolTipText());

        deleteMenu.setActionCommand("�폜(D)");
        deleteMenu.setMnemonic('D');
        deleteMenu.setText("�폜(D)");
        deleteMenu.setToolTipText(delete.getToolTipText());

    }

    public void initAffair(ACAffairInfo affair) throws Exception {
        //���j���[�o�[�̃{�^���ɑΉ�����g���K�[�̐ݒ�
        addDetailTrigger(detail);
        addDetailTrigger(detailMenu);
        addCopyTrigger(copy);
        addCopyTrigger(copyMenu);
        addInsertTrigger(insert);
        addInsertTrigger(insertMenu);
        addDeleteTrigger(delete);
        addDeleteTrigger(deleteMenu);
        addTableSelectedTrigger(table);

        //DB����X�e�[�V�����f�[�^���擾
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
        ACAffairInfo affair = new ACAffairInfo(IkenshoHoumonKangoStationJouhouShousai.class.
                                                 getName(), selectedRow, "�K��Ō�X�e�[�V�������ڍ�");
        ACFrame.getInstance().next(affair);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        //�ǂ̃{�^������̑J�ڂ��Ƃ�������t��
        VRMap selectedRow = new VRHashMap();
        selectedRow.put("ACT", "insert");

        //�J��
        ACAffairInfo affair = new ACAffairInfo(IkenshoHoumonKangoStationJouhouShousai.class.
                                                 getName(), selectedRow, "�K��Ō�X�e�[�V�������ڍ�");
        ACFrame.getInstance().next(affair);

        //�㏈���E�c�[���{�^���⃁�j���[�̗L����Ԃ̐ݒ�
        setButtonsEnabled();
    }

    protected void copyActionPerformed(ActionEvent e) throws Exception {
        //�I���s�̎擾
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //�ǂ̃{�^������̑J�ڂ��Ƃ�������t��
        VRMap selectedRow = (VRMap)data.getData(row);
        selectedRow.put("ACT", "copy");

        //�J��
        ACAffairInfo affair = new ACAffairInfo(IkenshoHoumonKangoStationJouhouShousai.class.
                                                 getName(), selectedRow, "�K��Ō�X�e�[�V�������ڍ�");
        ACFrame.getInstance().next(affair);
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        delete.setEnabled(false);

        //�I���s�̎擾
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }

        //�m�FMSG
        String msg = "�I�����ꂽ�����폜���܂��B\n��낵���ł����H";
        int result = ACMessageBox.show(msg,
                                            ACMessageBox.BUTTON_OKCANCEL,
                                            ACMessageBox.ICON_QUESTION,
                                            ACMessageBox.FOCUS_CANCEL);

        //�ŏI�I���ʒu(���f���ł͂Ȃ���ʏ�)��ޔ�
        int lastSelectedIndex = table.getSelectedSortedRow();

        if (result == ACMessageBox.RESULT_OK) {
            //DB�ƃe�[�u������f�[�^���폜����
            IkenshoFirebirdDBManager dbm = null;
            try {
                //�p�b�V�u�`�F�b�N
                clearPassiveTask();
                addPassiveDeleteTask(PASSIVE_CHECK_KEY, row);
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    //��SELECT
                    doSelect(null);
                    return;
                }
                //�I���s�̘A�g��R�[�h���擾
                String stationCd = String.valueOf(((VRMap)data.get(row)).getData("STATION_CD"));

                //SQL���̐���
                String sql = "DELETE FROM STATION WHERE STATION_CD=" + stationCd;

                //SQL�̎��s
                dbm.executeUpdate(sql);

                //�R�~�b�g
                dbm.commitTransaction();
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

    private void showPopup(MouseEvent e) {
      if (e.isPopupTrigger()) {
        popup.show( (Component) e.getSource(), e.getX(), e.getY());
      }
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
//        table.getTable().requestFocus();
        table.requestFocus();

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
        //DB����f�[�^���擾
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT" );
        sb.append( " STATION_CD," );
        sb.append( " DR_NM," );
        sb.append( " MI_NM," );
        sb.append( " MI_POST_CD," );
        sb.append( " MI_ADDRESS," );
        sb.append( " MI_TEL1," );
        sb.append( " MI_TEL2," );
        sb.append( " MI_FAX1," );
        sb.append( " MI_FAX2," );
        sb.append( " MI_CEL_TEL1," );
        sb.append( " MI_CEL_TEL2," );
        sb.append( " KINKYU_RENRAKU," );
        sb.append( " FUZAIJI_TAIOU," );
        sb.append( " BIKOU," );
        sb.append( " LAST_TIME" );
        sb.append( " FROM" );
        sb.append( " STATION" );
        sb.append( " ORDER BY" );
        sb.append( " DR_NM ASC" );
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            //�p�b�V�u�`�F�b�N
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY, data);
        }

        //�\���pField�̒ǉ�
        for( int i = 0; i < data.getDataSize(); i++ ) {
            IkenshoTelTextField telTmp = new IkenshoTelTextField();
            //�d�b�ԍ�
            String tel1 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_TEL1"));
            String tel2 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_TEL2"));
            telTmp.setArea(tel1);
            telTmp.setNumber(tel2);
            ( (VRMap) data.getData(i)).setData("TEL", telTmp.getTelNo());

            //FAX
            String fax1 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_FAX1"));
            String fax2 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_FAX2"));
            telTmp.setArea(fax1);
            telTmp.setNumber(fax2);
            ( (VRMap) data.getData(i)).setData("FAX", telTmp.getTelNo());

            //�A����(�g��)
            String celTel1 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_CEL_TEL1"));
            String celTel2 = String.valueOf( ( (VRMap) data.getData(i)).getData("MI_CEL_TEL2"));
            telTmp.setArea(celTel1);
            telTmp.setNumber(celTel2);
            ( (VRMap) data.getData(i)).setData("CEL_TEL", telTmp.getTelNo());
        }

        //�e�[�u���̐���
        table.setModel(new ACTableModelAdapter(data, new String[] {
                                               "DR_NM",
                                               "MI_NM",
                                               "MI_POST_CD",
                                               "MI_ADDRESS",
                                               "TEL",
                                               "FAX",
                                               "CEL_TEL",
                                               "KINKYU_RENRAKU",
                                               "FUZAIJI_TAIOU",
                                               "BIKOU"}));

        //ColumnModel�̐���
        table.setColumnModel(new VRTableColumnModel(
            new VRTableColumn[] {
//          new VRTableColumn(0, 150, "��\�Җ�"),
//          new VRTableColumn(1, 150, "�X�e�[�V������"),
//          new VRTableColumn(2, 80, "�X�֔ԍ�"),
//          new VRTableColumn(3, 200, "���ݒn"),
//          new VRTableColumn(4, 120, "�A����(�d�b)"),
//          new VRTableColumn(5, 120, "�A����(FAX)"),
//          new VRTableColumn(6, 120, "�A����(�g��)"),
//          new VRTableColumn(7, 200, "�ً}���A����"),
//          new VRTableColumn(8, 200, "�s�ݎ��Ή��@"),            		
            new VRTableColumn(0, 180, "��\�Җ�"),
            new VRTableColumn(1, 180, "�X�e�[�V������"),
            new VRTableColumn(2, 120, "�X�֔ԍ�"),
            new VRTableColumn(3, 260, "���ݒn"),
            new VRTableColumn(4, 160, "�A����(�d�b)"),
            new VRTableColumn(5, 160, "�A����(FAX)"),
            new VRTableColumn(6, 160, "�A����(�g��)"),
            new VRTableColumn(7, 260, "�ً}���A����"),
            new VRTableColumn(8, 260, "�s�ݎ��Ή��@"),
            new VRTableColumn(9, 300, "���l")
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
                    String key = "STATION_CD";
                    String value = String.valueOf(params.get(key));
                    if (value != null) {
                        //�n��f�[�^�̍s������
                        for (int i = 0; i < data.size(); i++) {
                            if (String.valueOf(((VRMap)data.getData(i)).get(key)).equals(value)) {
                                selectedRow = i;
                                break;
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
        copy.setEnabled(enabled);
        delete.setEnabled(enabled);
        detailMenu.setEnabled(enabled);
        copyMenu.setEnabled(enabled);
        deleteMenu.setEnabled(enabled);
    }
}
