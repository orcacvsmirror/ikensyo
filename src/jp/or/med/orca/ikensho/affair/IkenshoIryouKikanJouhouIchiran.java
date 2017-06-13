package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.Format;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
import javax.swing.SwingConstants;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.ac.component.table.ACTableColumn;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.sql.ACPassiveKey;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
import jp.nichicom.ac.text.ACHashMapFormat;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoTelTextField;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;


/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouIchiran extends IkenshoAffairContainer implements ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton copy = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private VRPanel tablePnl = new VRPanel();
    private VRPanel infoPnl = new VRPanel();
    private VRLabel info = new VRLabel();
    private ACTable table = new ACTable();
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem insertMenu = new JMenuItem();
    private JMenuItem copyMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();
    private VRArrayList data;
    private VRArrayList jigyoushoData;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
    // �L���J����
    private ACTableColumn enabledColumn;
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
    private static final ACPassiveKey PASSIVE_CHECK_KEY_DOCTOR = new
        ACPassiveKey("DOCTOR", new String[] {"DR_CD"}
                          , new Format[] {null}, "LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_KEY_JIGYOUSHA = new
        ACPassiveKey("JIGYOUSHA", new String[] {"DR_CD", "INSURER_NO", "JIGYOUSHA_NO"}
                          , new Format[] {null, IkenshoConstants.FORMAT_PASSIVE_STRING, IkenshoConstants.FORMAT_PASSIVE_STRING}
                          , "LAST_TIME", "LAST_TIME");


    public IkenshoIryouKikanJouhouIchiran() {
        try {
            jbInit();
            events();
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        buttons.setTitle("��Ë@�֏��ꗗ");
        this.add(buttons, VRLayout.NORTH);
        this.add(tablePnl, VRLayout.CLIENT);

        //�{�^���n
        buttons.add(delete, VRLayout.EAST);
        buttons.add(copy, VRLayout.EAST);
        buttons.add(insert, VRLayout.EAST);
        buttons.add(detail, VRLayout.EAST);

        detail.setText("�ڍ׏��(E)");
        detail.setMnemonic('E');
        detail.setActionCommand("�ڍ׏��(E)");
        detail.setToolTipText("�I�����ꂽ��Ë@�֏��̏ڍ׉�ʂֈڂ�܂��B");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);

        copy.setText("����(C)");
        copy.setMnemonic('C');
        copy.setActionCommand("����(C)");
        copy.setToolTipText("�I�����ꂽ�A�g����𕡐����܂��B");
        copy.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_COPY);

        insert.setText("�V�K�o�^(N)");
        insert.setMnemonic('N');
        insert.setActionCommand("�V�K�o�^(N)");
        insert.setToolTipText("��Ë@�֏���V�K�ɍ쐬���܂��B");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);

        delete.setText("�폜(D)");
        delete.setMnemonic('D');
        delete.setActionCommand("�폜(D)");
        delete.setToolTipText("�I�����ꂽ��Ë@�֏����폜���܂��B");
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

        //�e�[�u��
        tablePnl.setLayout(new BorderLayout());
        tablePnl.add(infoPnl, BorderLayout.NORTH);
        tablePnl.add(table, BorderLayout.CENTER);

        infoPnl.setLayout(new BorderLayout());
        infoPnl.add(info, BorderLayout.EAST);
        info.setText("���\����t���ʏ�g�p�����t");
        info.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);

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

        //�e�[�u���`��
        doSelect(affair);
    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    public Component getFirstFocusComponent() {
        return table;
//        return table.getTable();
    }

    public boolean canBack(VRMap parameters) throws Exception {
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
        ACAffairInfo affair = new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class.getName(), selectedRow, "��Ë@�֏��ڍ�");
        ACFrame.getInstance().next(affair);
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        //�ǂ̃{�^������̑J�ڂ��Ƃ�������t��
        VRMap selectedRow = new VRHashMap();
        selectedRow.put("ACT", "insert");

        //�J��
        ACAffairInfo affair = new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class.getName(), selectedRow, "��Ë@�֏��ڍ�");
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
        ACAffairInfo affair = new ACAffairInfo(IkenshoIryouKikanJouhouShousai.class.getName(), selectedRow, "��Ë@�֏��ڍ�");
        ACFrame.getInstance().next(affair);
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        delete.setEnabled(false);

        //�I���s�̎擾
        int row = table.getSelectedModelRow();
        if (row < 0) {
            setButtonsEnabled();
            return;
        }

        //�m�FMSG
        String msg = "�I�����ꂽ�����폜���܂��B��낵���ł����H";
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
                addPassiveDeleteTask(PASSIVE_CHECK_KEY_DOCTOR, row);
                for (int i=0; i<jigyoushoData.getDataSize(); i++) {
                    addPassiveDeleteTask(PASSIVE_CHECK_KEY_JIGYOUSHA, i);
                }
                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    doSelect(null); //��SELECT
                    return;
                }

                //�I���s�̈�t�R�[�h���擾
                String DR_CD = String.valueOf(((VRMap)data.get(row)).getData("DR_CD"));

                //SQL���̐���
                dbm.executeUpdate("DELETE FROM DOCTOR WHERE DR_CD=" + DR_CD);
                dbm.executeUpdate("DELETE FROM JIGYOUSHA WHERE DR_CD=" + DR_CD);

                //�R�~�b�g
                dbm.commitTransaction();
            }
            catch (Exception ex) {
                if (dbm != null) {
                    dbm.rollbackTransaction(); //���[���o�b�N
                }
                setButtonsEnabled();
                throw new Exception(ex);
            }

            doSelect(null); //��SELECT

            //�đI��
            table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);
        }
        else {
          setButtonsEnabled();
        }
    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        setButtonsEnabled();
    }

    /**
     * �|�b�v�A�b�v���j���[��\�����܂��B
     * @param e MouseEvent
     */
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

        //column������
        createTableColumn();

        //�{�^����Enabled�ݒ�
        setButtonsEnabled();

        //�e�[�u���Ƀt�H�[�J�X�𓖂Ă�
//        table.getTable().requestFocus();
        table.requestFocus();

        //�e�[�u���̍s��I��
        setInitSelectedRow(affair);

        //�X�e�[�^�X�o�[
        setStatusText(String.valueOf(table.getRowCount()) + "���o�^����Ă��܂��B");
    }

    /**
     * DB����f�[�^���擾���܂��B
     * @throws Exception
     */
    private void doSelectFromDB() throws Exception {
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append( " SELECT" );
        sb.append( " DR_CD" );
        sb.append( ",DR_NM" );
        sb.append( ",MI_NM" );
        sb.append( ",MI_POST_CD" );
        sb.append( ",MI_ADDRESS" );
        sb.append( ",MI_TEL1" );
        sb.append( ",MI_TEL2" );
        sb.append( ",MI_FAX1" );
        sb.append( ",MI_FAX2" );
        sb.append( ",MI_CEL_TEL1" );
        sb.append( ",MI_CEL_TEL2" );
        sb.append( ",KINKYU_RENRAKU" );
        sb.append( ",FUZAIJI_TAIOU" );
        sb.append( ",BIKOU" );
        sb.append( ",MI_DEFAULT" );
        sb.append( ",DR_NO" );
        sb.append( ",LAST_TIME" );
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
        sb.append( ",INVALID_FLAG" );
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-Start ��t�̕��я��̕ύX�Ή�
        sb.append( ",DR_KN" );
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-End
        sb.append( " FROM" );
        sb.append( " DOCTOR" );
        sb.append( " ORDER BY" );
// [ID:0000801][Ryosuke Koinuma] 2016/10 edit-Start ��t�̕��я��̕ύX�Ή�
//        sb.append( " DR_NM" );
        //��t�����R���{�{�b�N�X�̕��я��ɍ��킹��
        sb.append(" DR_KN");
        sb.append(" NULLS FIRST");
        sb.append(" ,DR_NM");
// [ID:0000801][Ryosuke Koinuma] 2016/10 edit-End
        data = (VRArrayList) dbm.executeQuery(sb.toString());

        if (data.getDataSize() > 0) {
            //�p�b�V�u�`�F�b�N
            clearReservedPassive();
            reservedPassive(PASSIVE_CHECK_KEY_DOCTOR, data);
        }

        //�\���pField�̒ǉ�
        for( int i = 0; i < data.getDataSize(); i++ ) {
            String miDefault;
            IkenshoTelTextField telTmp = new IkenshoTelTextField();

            //�ʏ�g����t
            if (String.valueOf(((VRMap)data.getData(i)).getData("MI_DEFAULT")).equals("1")) {
                miDefault = "��";
            }
            else {
                miDefault = "";
            }
            ((VRMap)data.getData(i)).setData("MI_DEFAULT_MARK", miDefault);

            //�d�b�ԍ�
            String tel1 = String.valueOf(((VRMap)data.getData(i)).getData("MI_TEL1"));
            String tel2 = String.valueOf(((VRMap)data.getData(i)).getData("MI_TEL2"));
            telTmp.setArea(tel1);
            telTmp.setNumber(tel2);
            ((VRMap)data.getData(i)).setData("TEL", telTmp.getTelNo());

            //FAX
            String fax1 = String.valueOf(((VRMap)data.getData(i)).getData("MI_FAX1"));
            String fax2 = String.valueOf(((VRMap)data.getData(i)).getData("MI_FAX2"));
            telTmp.setArea(fax1);
            telTmp.setNumber(fax2);
            ((VRMap)data.getData(i)).setData("FAX", telTmp.getTelNo());

            //�A����(�g��)
            String celTel1 = String.valueOf(((VRMap)data.getData(i)).getData("MI_CEL_TEL1"));
            String celTel2 = String.valueOf(((VRMap)data.getData(i)).getData("MI_CEL_TEL2"));
            telTmp.setArea(celTel1);
            telTmp.setNumber(celTel2);
            ((VRMap)data.getData(i)).setData("CEL_TEL", telTmp.getTelNo());
        }


        //���Ə��f�[�^�ADB����f�[�^���擾(�p�b�V�u�`�F�b�N�p)
        dbm = new IkenshoFirebirdDBManager();
        sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DR_CD");
        sb.append(",INSURER_NO");
        sb.append(",JIGYOUSHA_NO");
        sb.append(",LAST_TIME");
        sb.append(" FROM");
        sb.append(" JIGYOUSHA");
        jigyoushoData = (VRArrayList)dbm.executeQuery(sb.toString());
        if (jigyoushoData.getDataSize() > 0) {
            //�p�b�V�u�`�F�b�N
            reservedPassive(PASSIVE_CHECK_KEY_JIGYOUSHA, jigyoushoData);
        }
    }

    /**
     *
     * @throws Exception
     */
    private void createTableColumn() throws Exception {
        //�e�[�u���̐���
        table.setModel(new ACTableModelAdapter(data, new String[] {
            "MI_DEFAULT_MARK",
// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
            "INVALID_FLAG",
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End
            "DR_NM",
// [ID:0000801][Ryosuke Koinuma]  2014/10 add-Start ��t�̕��я��̕ύX�Ή�
            "DR_KN",
// [ID:0000801][Ryosuke Koinuma]  2014/10 add-End
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
//            new VRTableColumn(0, 16, " "),
//            new VRTableColumn(1, 150, "��t����"),
//            new VRTableColumn(2, 200, "��Ë@�֖�"),
//            new VRTableColumn(3, 80, "�X�֔ԍ�"),
//            new VRTableColumn(4, 200, "���ݒn"),
//            new VRTableColumn(5, 120, "�A����(�d�b)"),
//            new VRTableColumn(6, 120, "�A����(FAX)"),
//            new VRTableColumn(7, 120, "�A����(�g��)"),
//            new VRTableColumn(8, 200, "�ً}���A����"),
//            new VRTableColumn(9, 200, "�s�ݎ��Ή��@"),
            new VRTableColumn(0, 32, " "),
// [ID:0000787][Satoshi Tokusari] 2014/10 edit-Start ��Ë@�֏��̖������Ή�
//            new VRTableColumn(1, 180, "��t����"),
//            new VRTableColumn(2, 260, "��Ë@�֖�"),
//            new VRTableColumn(3, 120, "�X�֔ԍ�"),
//            new VRTableColumn(4, 260, "���ݒn"),
//            new VRTableColumn(5, 160, "�A����(�d�b)"),
//            new VRTableColumn(6, 160, "�A����(FAX)"),
//            new VRTableColumn(7, 160, "�A����(�g��)"),
//            new VRTableColumn(8, 260, "�ً}���A����"),
//            new VRTableColumn(9, 260, "�s�ݎ��Ή��@"),
//            new VRTableColumn(10, 300, "���l")
// [ID:0000801][Ryosuke Koinuma] 2016/10 del-Start ��t�̕��я��̕ύX�Ή�
//            getEnabledColumn(),
//            new VRTableColumn(2, 180, "��t����"),
//            new VRTableColumn(3, 260, "��Ë@�֖�"),
//            new VRTableColumn(4, 120, "�X�֔ԍ�"),
//            new VRTableColumn(5, 260, "���ݒn"),
//            new VRTableColumn(6, 160, "�A����(�d�b)"),
//            new VRTableColumn(7, 160, "�A����(FAX)"),
//            new VRTableColumn(8, 160, "�A����(�g��)"),
//            new VRTableColumn(9, 260, "�ً}���A����"),
//            new VRTableColumn(10, 260, "�s�ݎ��Ή��@"),
//            new VRTableColumn(11, 300, "���l")
// [ID:0000801][Ryosuke Koinuma] 2016/10 del-End
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-Start ��t�̕��я��̕ύX�Ή�
            getEnabledColumn(),
            new VRTableColumn(2, 180, "��t����"),
            new VRTableColumn(3, 260, "�ӂ肪��"),
            new VRTableColumn(4, 260, "��Ë@�֖�"),
            new VRTableColumn(5, 120, "�X�֔ԍ�"),
            new VRTableColumn(6, 260, "���ݒn"),
            new VRTableColumn(7, 160, "�A����(�d�b)"),
            new VRTableColumn(8, 160, "�A����(FAX)"),
            new VRTableColumn(9, 160, "�A����(�g��)"),
            new VRTableColumn(10, 260, "�ً}���A����"),
            new VRTableColumn(11, 260, "�s�ݎ��Ή��@"),
            new VRTableColumn(12, 300, "���l")
// [ID:0000801][Ryosuke Koinuma] 2016/10 add-End
// [ID:0000787][Satoshi Tokusari] 2014/10 edit-End
        })
            );

        /*
        //�e�[�u���̐ݒ�
        tableCellRenderer = new IkenshoIryouKikanJouhouTableCellRenderer("MI_DEFAULT", "1",
            IkenshoIryouKikanJouhouTableCellRenderer.TGT_CELL, "DR_NM");
        tableCellRenderer.setData(data);
        table.getTable().setDefaultRenderer(Object.class, tableCellRenderer);
        */
    }

// [ID:0000787][Satoshi Tokusari] 2014/10 add-Start ��Ë@�֏��̖������Ή�
    /**
     * ��Ë@�֏��ꗗ�F�L�����擾���܂��B
     * 
     * @return ��Ë@�֏��ꗗ�F�L��
     */
    public ACTableColumn getEnabledColumn() {
        if (enabledColumn == null) {
        	enabledColumn = new ACTableColumn(1);
            enabledColumn.setHeaderValue("�L��");
            enabledColumn.setColumns(3);
            enabledColumn.setHorizontalAlignment(SwingConstants.CENTER);
            enabledColumn.setRendererType(ACTableCellViewer.RENDERER_TYPE_ICON);
            // �e�[�u���J�����Ƀt�H�[�}�b�^��ݒ肷��B
            enabledColumn.setFormat(new ACHashMapFormat(new String[] {
                    "jp/nichicom/ac/images/icon/pix16/btn_079.png",
                    "jp/nichicom/ac/images/icon/pix16/btn_080.png" },
                    new Integer[] { new Integer(0), new Integer(1), }));
        }
        return enabledColumn;
    }
// [ID:0000787][Satoshi Tokusari] 2014/10 add-End

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
                    String key = "DR_CD";
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
