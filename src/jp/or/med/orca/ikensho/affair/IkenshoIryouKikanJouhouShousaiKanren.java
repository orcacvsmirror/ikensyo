package jp.or.med.orca.ikensho.affair;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.im.InputSubset;
import java.util.Arrays;
import java.util.Map;

import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACClearableRadioButtonGroup;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.component.VRCheckBox;
import jp.nichicom.vr.component.VRLabel;
import jp.nichicom.vr.component.table.VRSortableTableModelar;
import jp.nichicom.vr.component.table.VRTableColumn;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.container.VRPanel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRListModelAdapter;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.lib.IkenshoInsurerTypeFormat;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;
import jp.or.med.orca.ikensho.util.IkenshoSnapshot;

/** TODO <HEAD_IKENSYO> */
public class IkenshoIryouKikanJouhouShousaiKanren extends
        IkenshoTabbableChildAffairContainer {
    private VRPanel kanrenPnl1 = new VRPanel();
    private VRLabel note = new VRLabel();
    private VRPanel kanrenPnl2 = new VRPanel();
    private VRPanel kanrenPnl3 = new VRPanel();
    private ACLabelContainer kaisetushaNmContainer = new ACLabelContainer();
    private ACTextField kaisetushaNm = new ACTextField();
    private ACGroupBox miKbnGrp = new ACGroupBox();
    private ACClearableRadioButtonGroup miKbn = new ACClearableRadioButtonGroup();
    private ACLabelContainer bankNmContainer = new ACLabelContainer();
    private ACTextField bankNm = new ACTextField();
    private ACLabelContainer bankSitenNmContainer = new ACLabelContainer();
    private ACTextField bankSitenNm = new ACTextField();
    private ACLabelContainer bankKouzaNoContainer = new ACLabelContainer();
    private ACTextField bankKouzaNo = new ACTextField();
    private ACLabelContainer bankKouzaKindContainer = new ACLabelContainer();
    private ACClearableRadioButtonGroup bankKouzaKind = new ACClearableRadioButtonGroup();
    private ACLabelContainer furikomiMeigiContainer = new ACLabelContainer();
    private ACTextField furikomiMeigi = new ACTextField();
    private VRPanel drNoPnl = new VRPanel();
    private ACLabelContainer drNoContainer = new ACLabelContainer();
    private ACTextField drNo = new ACTextField();
    private VRLabel drNoCaption1 = new VRLabel();
    private VRLabel drNoCaption2 = new VRLabel();
    private ACGroupBox jigyoushoGrp = new ACGroupBox();
    private ACTable table = new ACTable();
    private VRPanel jigyoushoBtnPnl = new VRPanel();
    private ACButton jigyoushoInsert = new ACButton();
    private ACButton jigyoushoUpdate = new ACButton();
    private ACButton jigyoushoDelete = new ACButton();
    private VRCheckBox jigyoushoTableChangeFlg = new VRCheckBox();
    
    private ACIntegerCheckBox addItCheck = new ACIntegerCheckBox();

    private VRArrayList jigyoushoData = new VRArrayList();

    private final int ACT_INSERT = 0;
    private final int ACT_UPDATE = 1;

    public IkenshoIryouKikanJouhouShousaiKanren() {
        try {
            jbInit();
            event();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        this.setLayout(new VRLayout());
        this.add(kanrenPnl1, VRLayout.NORTH);
        this.add(kanrenPnl2, VRLayout.NORTH);
        this.add(jigyoushoGrp, VRLayout.CLIENT);

        // �L���v�V����
        kanrenPnl1.setLayout(new VRLayout());
        kanrenPnl1.add(note, VRLayout.FLOW);
        note.setText("��������(�ی���)�ɂ���ĕK�v�ȍ��ڂ͈قȂ�܂��B");
        note
                .setForeground(IkenshoConstants.COLOR_MESSAGE_ALART_TEXT_FOREGROUND);

        // ���
        VRLayout kanrenPnl2Layout = new VRLayout();
        kanrenPnl2.setLayout(kanrenPnl2Layout);
        kanrenPnl2.add(kanrenPnl3, VRLayout.NORTH);
        kanrenPnl2.add(bankNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(bankSitenNmContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(bankKouzaNoContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(bankKouzaKindContainer, VRLayout.FLOW_INSETLINE_RETURN);
        kanrenPnl2.add(furikomiMeigiContainer, VRLayout.FLOW_INSETLINE);
        kanrenPnl2.add(drNoPnl, VRLayout.FLOW_RETURN);
        kanrenPnl2.add(addItCheck, VRLayout.FLOW_RETURN);

        VRLayout kanrenPnl3Layout = new VRLayout();
        kanrenPnl3Layout.setLabelMargin(122);
        kanrenPnl3.setLayout(kanrenPnl3Layout);
        kanrenPnl3.add(kaisetushaNmContainer, VRLayout.FLOW_INSETLINE);
        kanrenPnl3.add(miKbnGrp, VRLayout.FLOW_RETURN);
        kaisetushaNmContainer.setText("�J�ݎҎ���");
        kaisetushaNmContainer.add(kaisetushaNm, null);
        // 2009/01/06 [Mizuki Tsutsumi] : change begin
        // �J�ݎҎ����A�U���於�`�l�������g��
        //kaisetushaNm.setColumns(15);
        //kaisetushaNm.setMaxLength(15);
        kaisetushaNm.setColumns(50);
        kaisetushaNm.setMaxLength(50);
        // 2009/01/06 [Mizuki Tsutsumi] : change end
        kaisetushaNm.setIMEMode(InputSubset.KANJI);
        kaisetushaNm.setBindPath("KAISETUSHA_NM");

        miKbnGrp.setText("�f�Ï��E�a�@�敪");
        miKbnGrp.setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        miKbnGrp.add(miKbn, null);
        miKbn.setUseClearButton(false);
        miKbn.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "�f�Ï�", "�a�@", "���̑��̎{��" }))));
        miKbn.setBindPath("MI_KBN");

        bankNmContainer.setText("�U������Z�@�֖�");
        bankNmContainer.add(bankNm, null);
        bankNm.setColumns(25);
        bankNm.setMaxLength(25);
        bankNm.setIMEMode(InputSubset.KANJI);
        bankNm.setBindPath("BANK_NM");

        bankSitenNmContainer.setText("�U������Z�@�֎x�X��");
        bankSitenNmContainer.add(bankSitenNm, null);
        bankSitenNm.setColumns(25);
        bankSitenNm.setMaxLength(25);
        bankSitenNm.setIMEMode(InputSubset.KANJI);
        bankSitenNm.setBindPath("BANK_SITEN_NM");

        bankKouzaNoContainer.setText("�U��������ԍ�");
        bankKouzaNoContainer.add(bankKouzaNo, null);
        bankKouzaNo.setColumns(10);
        bankKouzaNo.setMaxLength(10);
        bankKouzaNo.setIMEMode(InputSubset.LATIN_DIGITS);
        bankKouzaNo.setCharType(VRCharType.ONLY_ALNUM);
        // bankKouzaNo.setCharType(VRCharType.ONLY_DIGIT);
        bankKouzaNo.setBindPath("BANK_KOUZA_NO");

        bankKouzaKindContainer.setText("�U����������");
        bankKouzaKindContainer.add(bankKouzaKind, null);
        bankKouzaKind.setModel(new VRListModelAdapter(new VRArrayList(Arrays
                .asList(new String[] { "����", "����" }))));
        bankKouzaKind.setBindPath("BANK_KOUZA_KIND");

        furikomiMeigiContainer.setText("�U���於�`�l");
        furikomiMeigiContainer.add(furikomiMeigi, null);
        // 2009/01/06 [Mizuki Tsutsumi] : change begin
        // �J�ݎҎ����A�U���於�`�l�������g��
        //furikomiMeigi.setColumns(15);
        //furikomiMeigi.setMaxLength(15);
        furikomiMeigi.setColumns(50);
        furikomiMeigi.setMaxLength(50);
        // 2009/01/06 [Mizuki Tsutsumi] : change end
        furikomiMeigi.setIMEMode(InputSubset.KANJI);
        furikomiMeigi.setBindPath("FURIKOMI_MEIGI");

        VRLayout drNoPnlLayout = new VRLayout();
        drNoPnlLayout.setHgap(0);
        drNoPnlLayout.setVgap(0);
        drNoPnlLayout.setAutoWrap(false);
        drNoPnl.setLayout(drNoPnlLayout);
        drNoPnl.add(drNoCaption1, VRLayout.FLOW);
        drNoPnl.add(drNoContainer, VRLayout.FLOW);
        drNoPnl.add(drNoCaption2, VRLayout.FLOW_RETURN);
        VRLayout drNoContainerLayout = new VRLayout();
        drNoContainerLayout.setAutoWrap(false);
        drNoContainerLayout.setHgap(0);
        drNoContainerLayout.setVgap(0);
        drNoContainer.setLayout(drNoContainerLayout);
        drNoContainer.setText("��t�ԍ�");
        drNoContainer.add(drNo, VRLayout.FLOW);
        drNo.setColumns(10);
        drNo.setMaxLength(10);
        drNo.setIMEMode(InputSubset.LATIN_DIGITS);
        // drNo.setCharType(VRCharType.ONLY_ASCII);
        drNo.setBindPath("DR_NO");
        drNoCaption1.setText("(");
        drNoCaption2.setText(")");

        addItCheck.setBindPath("DR_ADD_IT");
        addItCheck.setText("���̈�Ë@�ւ�d�q�����Z�̑Ώۂɂ���B");

        // Grp
        jigyoushoGrp.setText("���Ə��ԍ�");
        jigyoushoGrp
                .setForeground(IkenshoConstants.COLOR_MESSAGE_TEXT_FOREGROUND);
        jigyoushoGrp.setLayout(new BorderLayout());
        jigyoushoGrp.add(table, BorderLayout.CENTER);
        jigyoushoGrp.add(jigyoushoBtnPnl, BorderLayout.EAST);

        jigyoushoBtnPnl.setLayout(new VRLayout());
        jigyoushoBtnPnl.add(jigyoushoInsert, VRLayout.FLOW_RETURN);
        jigyoushoBtnPnl.add(jigyoushoUpdate, VRLayout.FLOW_RETURN);
        jigyoushoBtnPnl.add(jigyoushoDelete, VRLayout.FLOW_RETURN);
        jigyoushoBtnPnl.add(jigyoushoTableChangeFlg, VRLayout.FLOW_RETURN);

        jigyoushoInsert.setText("�o�^(T)");
        jigyoushoInsert.setMnemonic('T');
        jigyoushoUpdate.setText("�ҏW(E)");
        jigyoushoUpdate.setMnemonic('E');
        jigyoushoDelete.setText("�폜(D)");
        jigyoushoDelete.setMnemonic('D');
        jigyoushoTableChangeFlg.setVisible(false);
        jigyoushoTableChangeFlg.setSelected(false);
        jigyoushoTableChangeFlg.setBindPath("TABLE_CHANGE_FLG");
    }

    private void createTable() throws Exception {
        // �e�[�u���̐���
        table.setModel(new ACTableModelAdapter(jigyoushoData, new String[] {
                "INSURER_NO", "INSURER_NM", "INSURER_TYPE", "JIGYOUSHA_NO" }));

        // ColumnModel�̐���
        table.setColumnModel(new VRTableColumnModel(new VRTableColumn[] {
                new VRTableColumn(0, 90, "�ی��Ҕԍ�"),
                new VRTableColumn(1, 300, "�ی��Җ���"),
                new VRTableColumn(2, 160, "�ی��ҋ敪", IkenshoInsurerTypeFormat.getInstance()),
                new VRTableColumn(3, 150, "���Ə��ԍ�") }));
    }

    private void event() throws Exception {
        // ���Ə��ԍ��o�^
        jigyoushoInsert.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDlg(ACT_INSERT);
            }
        });

        // ���Ə��ԍ��X�V
        jigyoushoUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showDlg(ACT_UPDATE);
            }
        });

        // ���Ə��ԍ��폜
        jigyoushoDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                doDelete();
            }
        });

        // �e�[�u���I����
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                setButtonsEnabled();
                if (e.getClickCount() == 2) {
                    showDlg(ACT_UPDATE);
                }
            }
        });
    }

    /**
     * ���Ə��ԍ��ݒ�_�C�A���O�\���������s���܂��B
     * 
     * @param act String
     */
    private void showDlg(int act) {
        int selRow = 0; // �I���s
        VRMap affair = new VRHashMap(); // DLG�ɓn���p�����[�^
        if (act == ACT_INSERT) {
            // �_�C�A���O�ɓn���p�����[�^������
            affair.put("ACT", "insert");
        } else if (act == ACT_UPDATE) {
            // �I���s�̎擾
            selRow = table.getSelectedModelRow();
            if (selRow < 0) {
                return;
            }
            // �_�C�A���O�ɓn���p�����[�^������
            affair.put("ACT", "update");
            affair.put("SEL_ROW", String.valueOf(selRow));
        }

        // ���݂̃e�[�u����̃f�[�^��n��p�����[�^�ɒǉ�
        affair.put("DATA", jigyoushoData);

        // �X�i�b�v�V���b�g�ޔ�
        Map snap = IkenshoSnapshot.getInstance().getMemento();
        Container container = IkenshoSnapshot.getInstance().getRootContainer();

        // DLG���s(�\��)
        IkenshoJigyoushoBangouSetting dlg = new IkenshoJigyoushoBangouSetting(
                affair);
        dlg.setVisible(true);
        // dlg.show();

        // �X�i�b�v�V���b�g����
        IkenshoSnapshot.getInstance().setRootContainer(container);
        IkenshoSnapshot.getInstance().setMemento(snap);

        // �_�C�A���O����p�����[�^���擾
        VRMap params = dlg.getParams();
        String result = String.valueOf(params.getData("ACT"));

        if (result.equals("submit")) {
            // �s�̒ǉ�(�X�V)�������s��
            VRMap newRow = new VRHashMap();
            String insurerNoNew = ACCastUtilities.toString(params.getData("INSURER_NO"),"");
            String insurerTypeNow = ACCastUtilities.toString(params.getData("INSURER_TYPE"),"");
            newRow.setData("INSURER_NO", ACCastUtilities.toString(params.getData("INSURER_NO"),""));
            newRow.setData("INSURER_NM", ACCastUtilities.toString(params.getData("INSURER_NM"),""));
            newRow.setData("JIGYOUSHA_NO", ACCastUtilities.toString(params.getData("JIGYOUSHA_NO"),""));
            newRow.setData("INSURER_TYPE", params.getData("INSURER_TYPE"));
            if (act == ACT_INSERT) {
                jigyoushoData.addData(newRow);
            } else if (act == ACT_UPDATE) {
                jigyoushoData.setData(selRow, newRow);
            }

            // �㏈��
            table.revalidate();
            table.sort("INSURER_NO ASC, INSURER_TYPE ASC");
            // table.getTable().revalidate();
            // table.getTable().sort("INSURER_NO ASC");
            setButtonsEnabled();
            jigyoushoTableChangeFlg.setSelected(true);

            // �s�̍đI��
            for (int i = 0; i < jigyoushoData.getDataSize(); i++) {
                VRMap row = (VRMap) jigyoushoData.getData(i);
                String insurerNoTmp = ACCastUtilities.toString(row.getData("INSURER_NO"),"");
                String insurerTypeTmp = ACCastUtilities.toString(row.getData("INSURER_TYPE"),"");
                if (insurerNoTmp.equals(insurerNoNew)&& insurerTypeTmp.equals(insurerTypeNow)) {
                    table.setSelectedModelRow(((VRSortableTableModelar) table
                            .getModel()).getReverseTranslateIndex(i));
                    break;
                }
            }
        }
    }

    /**
     * �e�[�u������I���s���폜���܂��B
     */
    private void doDelete() {
        // �m�F���b�Z�[�W�̕\��
        int result = ACMessageBox.show("�폜���Ă���낵���ł����H",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                ACMessageBox.FOCUS_CANCEL);
        if (result == ACMessageBox.RESULT_CANCEL) {
            return;
        }

        // �ŏI�I���ʒu(���f���ł͂Ȃ���ʏ�)��ޔ�
        int lastSelectedIndex = table.getSelectedSortedRow();

        // �I���s�̎擾
        int selRow = table.getSelectedModelRow();
        if (selRow < 0) {
            return;
        }
        int delRow = ((VRSortableTableModelar) table.getModel())
                .getTranslateIndex(selRow);

        // �폜
        jigyoushoData.removeData(delRow);

        // �đI��
        table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

        // �㏈��
        setButtonsEnabled();
        jigyoushoTableChangeFlg.setSelected(true);
    }

    public boolean noControlError() {
        // �f�Ï��E�a�@�敪 / ���I���`�F�b�N
        if (miKbn.getSelectedIndex() <= 0) {
            ACMessageBox.show("�f�Ï��E�a�@�敪��I�����Ă��������B", ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_EXCLAMATION);
            miKbn.requestFocus();
            return false;
        }

        return true;
    }

    public boolean noControlWarning() {
        // ���Ə��ԍ� / ���o�^�`�F�b�N
        if (table.getRowCount() <= 0) {
            int result = ACMessageBox.show("���Ə��ԍ����o�^����Ă��܂���B\n��낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
            if (result == ACMessageBox.RESULT_OK) {
                return true;
            } else {
                table.requestFocus();
                // table.getTable().requestFocus();
                return false;
            }
        }

        return true;
    }

    /**
     * ���Ə��ԍ��e�[�u���Ƀf�[�^��ݒ肵�܂��B
     * 
     * @param drCd String
     * @throws Exception
     */
    public void doSelect(String drCd) throws Exception {
        doSelectFromDB(drCd);
        createTable();
        setButtonsEnabled();
        if (table.getRowCount() > 0) {
            if (table.getSelectedModelRow() < 0) {
                table.setSelectedModelRow(0);
            }
        }
    }

    /**
     * ���Ə��ԍ��e�[�u��(DB)����f�[�^���擾���܂��B
     * 
     * @param drCd String
     * @return VRArrayList
     * @throws Exception
     */
    private VRArrayList doSelectFromDB(String drCd) throws Exception {
        // �L�[�����ɁADB����f�[�^���擾
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT");
        sb.append(" DISTINCT");
        sb.append(" JIGYOUSHA.DR_CD");
        sb.append(",JIGYOUSHA.INSURER_NO");
        sb.append(",INSURER.INSURER_NM");
        sb.append(",JIGYOUSHA.INSURER_TYPE");
        sb.append(",JIGYOUSHA.JIGYOUSHA_NO");
        sb.append(",JIGYOUSHA.LAST_TIME");
        sb.append(" FROM");
        sb.append(" JIGYOUSHA, INSURER");
        sb.append(" WHERE");
        sb.append(" JIGYOUSHA.INSURER_NO = INSURER.INSURER_NO");
        sb.append(" AND");
        sb.append(" JIGYOUSHA.INSURER_TYPE = INSURER.INSURER_TYPE");
        sb.append(" AND");
        sb.append(" DR_CD=" + drCd);
        sb.append(" ORDER BY");
        sb.append(" JIGYOUSHA.INSURER_NO ASC, JIGYOUSHA.INSURER_TYPE ASC");
        jigyoushoData = (VRArrayList) dbm.executeQuery(sb.toString());

        return jigyoushoData;
    }

    public ACTable getTable() {
        return table;
    }

    /**
     * �{�^���A���j���[�̗L����Ԃ�ݒ肵�܂��B
     */
    public void setButtonsEnabled() {
        boolean enabled = false;
        if (table.getRowCount() > 0) {
            if (table.getSelectedModelRow() < 0) {
                enabled = false;
            } else {
                enabled = true;
            }
        }

        jigyoushoUpdate.setEnabled(enabled);
        jigyoushoDelete.setEnabled(enabled);
    }

    public VRArrayList getJigyoushoData() {
        return jigyoushoData;
    }

    public ACTable getJigyoushoTable() {
        return table;
    }
}
