package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.im.InputSubset;
import java.sql.Date;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACIntegerCheckBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextField;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableCellViewer;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.container.ACBackLabelContainer;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACHashMapFormat;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellEditor;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** TODO <HEAD_IKENSYO> */
public class IkenshoPatientList extends IkenshoAffairContainer implements
        ACAffairable {
    private ACAffairButtonBar buttons = new ACAffairButtonBar();
    private ACAffairButton detail = new ACAffairButton();
    private ACAffairButton insert = new ACAffairButton();
    private ACAffairButton sijisho = new ACAffairButton();
    private ACAffairButton ikensho = new ACAffairButton();
    private ACAffairButton delete = new ACAffairButton();
    private ACAffairButton print = new ACAffairButton();
    private ACTable table = new ACTable();

    // 2007/09/18 [Masahiko Higuchi] Addition - begin
    private ACPanel findPanel;
    private ACPanel findButtonPanel;
    private ACTextField kanjyaText;
    private ACLabelContainer kanjyaIDContainer;
    private ACLabelContainer furiganaContainer;
    private ACLabelContainer reportContainer;
    private ACLabelContainer kanjyaContainer;
    private ACLabelContainer birthDayContainer;
    private ACTextField furiganaText;
    private ACComboBox reportCombo;
    private ACIntegerCheckBox kanjyaCheck;
    private IkenshoEraDateTextField birthDayDateText;
    private ACLabelContainer reportDateFromContainer;
    private ACLabelContainer reportDateToContainer;
    private IkenshoEraDateTextField reportDateFrom;
    private IkenshoEraDateTextField reportDateTo;
    private ACBackLabelContainer reportBackContainer;
    private ACLabel reportFromToLabel;
    private ACButton clearButton;
    private ACPanel reportPanel;
    private ACButton findButton;
    private ACButton initialFindButton;
    // �L���J����
    private ACTableColumn patientEnabledColumn;
    // ���N�����J����
    private ACTableColumn patientBirthColumn;
    // 2007/09/18 [Masahiko Higuchi] Addition - end
    
    /** @todo menu */
    private JPopupMenu popup = new JPopupMenu();
    private JMenuItem detailMenu = new JMenuItem();
    private JMenuItem ikenshoMenu = new JMenuItem();
    private JMenuItem sijishoMenu = new JMenuItem();
    private JMenuItem deleteMenu = new JMenuItem();

    private VRArrayList data;
    private ACTableModelAdapter tableModelAdapter;
    private IkenshoCheckBoxTableCellEditor deleteCheckEditor;

    private static final ACPassiveKey PASSIVE_CHECK_KEY = new ACPassiveKey(
            "PATIENT", new String[] { "PATIENT_NO" }, new Format[] { null },
            "LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_IKENSHO_KEY = new ACPassiveKey(
            "IKN_ORIGIN", new String[] { "PATIENT_NO" }, new Format[] { null },
            "IKN_ORIGI_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_SIJISHO_KEY = new ACPassiveKey(
            "SIS_ORIGIN", new String[] { "PATIENT_NO" }, new Format[] { null },
            "SIS_ORIGIN_LAST_TIME", "LAST_TIME");
    private static final ACPassiveKey PASSIVE_CHECK_BILL_KEY = new ACPassiveKey(
            "IKN_BILL", new String[] { "PATIENT_NO" }, new Format[] { null },
            "IKN_BILL_LAST_TIME", "LAST_TIME");

    public void initAffair(ACAffairInfo affair) throws Exception {
        addDeleteTrigger(delete);
        addDeleteTrigger(deleteMenu);
        addDetailTrigger(detail);
        addDetailTrigger(detailMenu);
        addInsertTrigger(insert);
        addInsertTrigger(ikensho);
        addInsertTrigger(sijisho);
        addInsertTrigger(ikenshoMenu);
        addInsertTrigger(sijishoMenu);
        addTableSelectedTrigger(table);
        addPrintTableTrigger(print);
        addTableDoubleClickedTrigger(table);
        
        // 2007/09/18 [Masahiko Higuchi] Addition - begin
        //addFindTrigger(getFindMenu());
        //addFindTrigger(getFind());
        addFindTrigger(getFindButton());
        getClearButton().addActionListener(
                new IkenshoPatientList_clearDate_actionAdapter(this));
        getInitialFindButton().addActionListener(
				new IkenshoPatientList_initialFind_actionAdapter(this));
        // 2007/09/18 [Masahiko Higuchi] Addition - end        

        VRMap params = affair.getParameters();
        if (VRBindPathParser.has("PREV_DATA", params)) {
            // ��ʑJ�ڃL���b�V���f�[�^������ꍇ�́A�p�����^��u��������
            params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
        }
        
        // 2007/10/15 [Masahiko Higuchi] Delete - begin
        //doSelect();
        // 2007/10/15 [Masahiko Higuchi] Delete - end
        // 2007/10/15 [Masahiko Higuchi] Addition - begin
        doFind();
        // 2007/10/15 [Masahiko Higuchi] Addition - end
        
        
        // 2007/10/15 [Masahiko Higuchi] Replace - begin
        // 2006/07/05
        // ��t�ӌ����Ή�
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//        // ��t�ӌ����ŏI�L���N�����\���p�̃J������ǉ�
//        // Addition - begin [Masahiko Higuchi]
//        setTableModelAdapter(new ACTableModelAdapter(data, new String[] {
//                "CHART_NO","PATIENT_NM", "PATIENT_KN", "SEX", "BIRTHDAY",
//                "IKN_ORIGIN_KINYU_DT", "SIS_ORIGIN_KINYU_DT", "KOUSIN_DT",
//                "DELETE_FLAG","IKN_ORIGIN_KINYU_DT_ISHI","SHOW_FLAG","BIRTHDAY","PATIENT_NO" }));
//        // Addition - end
        setTableModelAdapter(new ACTableModelAdapter(data, new String[] {
                "CHART_NO","PATIENT_NM", "PATIENT_KN", "SEX", "BIRTHDAY",
                "IKN_ORIGIN_KINYU_DT", "SIS_ORIGIN_KINYU_DT", "KOUSIN_DT",
                "DELETE_FLAG","IKN_ORIGIN_KINYU_DT_ISHI","SHOW_FLAG","BIRTHDAY","PATIENT_NO",
                "SIS_ORIGIN_KINYU_DT_TOKUBETSU"}));
        // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        table.setModel(getTableModelAdapter());

        table.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(8, 22, "�@",
                        new IkenshoCheckBoxTableCellRenderer(),
                        deleteCheckEditor),
                getPatientEnabledColumn(),
                new ACTableColumn(0, 50, "����ID"),
                new ACTableColumn(1, 110, "����"),
                new ACTableColumn(2, 130, "�ӂ肪��"),
                new ACTableColumn(3, 32, "����", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_SEX),
                getPatientBirthColumn(),
                new ACTableColumn(4, 32, "�N��", SwingConstants.RIGHT,
                        IkenshoConstants.FORMAT_NOW_AGE),
                        // 2006/07/05
                        // ��t�ӌ����Ή�
                        // ��t�ӌ����ŏI�L���N�����\���p�̃J������ǉ�
                        // Addition - begin [Masahiko Higuchi]
                new ACTableColumn(5, 150, "�ŐV�厡��ӌ����L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new ACTableColumn(9, 150, "�ŐV��t�ӌ����L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                        // Addition - end
                new ACTableColumn(6, 120, "�ŐV�w�����L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
                new ACTableColumn(13, 140, "�ŐV���ʎw�����L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
                new ACTableColumn(7, 120, "�ŏI�X�V��",
                        IkenshoConstants.FORMAT_ERA_HMS), }));
        // 2007/10/15 [Masahiko Higuchi] Replace - end
        
        if (table.getRowCount() > 0) {
            int sel = 0;
            if (VRBindPathParser.has("PATIENT_NO", params)) {
                int selPatient = ((Integer) VRBindPathParser.get("PATIENT_NO",
                        params)).intValue();
                int size = data.size();
                for (int i = 0; i < size; i++) {
                    int patient = ((Integer) VRBindPathParser.get("PATIENT_NO",
                            (VRMap) data.getData(i))).intValue();
                    if (patient == selPatient) {
                        sel = i;
                        break;
                    }
                }
            }

            // ���s�������I��
            table.setSelectedModelRow(sel);
        }
        
    }

    /**
     * �I���������s���܂��B
     * 
     * @deprecated doFind���\�b�h�̎g�p�𐄏�
     * @throws Exception ������O
     */
    private void doSelect() throws Exception {

        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        StringBuffer sb = new StringBuffer();
        // del start s-fujihara SQL���ύX
        // sb.append("SELECT");
        // sb.append(" PATIENT.PATIENT_NO");
        // sb.append(",PATIENT.CHART_NO");
        // sb.append(",PATIENT.PATIENT_NM");
        // sb.append(",PATIENT.PATIENT_KN");
        // sb.append(",PATIENT.SEX");
        // sb.append(",PATIENT.BIRTHDAY");
        // sb.append(",PATIENT.KOUSIN_DT");
        // sb.append(",MAX(IKN_ORIGIN.EDA_NO) IKN_MAX");
        // sb.append(",MAX(SIS_ORIGIN.EDA_NO) SIS_MAX");
        // sb.append(",PATIENT.LAST_TIME");
        // sb.append(" FROM");
        // sb.append(" (PATIENT LEFT OUTER JOIN IKN_ORIGIN");
        // sb.append(" ON PATIENT.PATIENT_NO = IKN_ORIGIN.PATIENT_NO)");
        // sb.append(" LEFT OUTER JOIN SIS_ORIGIN");
        // sb.append(" ON PATIENT.PATIENT_NO = SIS_ORIGIN.PATIENT_NO");
        // sb.append(" GROUP BY");
        // sb.append(" PATIENT.PATIENT_NO");
        // sb.append(",PATIENT.CHART_NO");
        // sb.append(",PATIENT.PATIENT_NM");
        // sb.append(",PATIENT.PATIENT_KN");
        // sb.append(",PATIENT.SEX");
        // sb.append(",PATIENT.BIRTHDAY");
        // sb.append(",PATIENT.KOUSIN_DT");
        // sb.append(",PATIENT.LAST_TIME");
        // sb.append(" ORDER BY");
        // sb.append(" PATIENT_KN ASC");
        // del end s-fujihara

        // add start s-fujihara
        sb.append(" SELECT");
        sb.append(" PATIENT.PATIENT_NO,");
        sb.append(" PATIENT.CHART_NO,");
        sb.append(" PATIENT.PATIENT_NM,");
        sb.append(" PATIENT.PATIENT_KN,");
        sb.append(" PATIENT.SEX,");
        sb.append(" PATIENT.BIRTHDAY,");
        sb.append(" PATIENT.KOUSIN_DT,");
        // 2007/10/15 [Masahiko Higuchi] Addition - begin
        sb.append(" PATIENT.SHOW_FLAG,");
        // 2007/10/15 [Masahiko Higuchi] Addition - end        
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_MAX_EDA,");
        // 2006/07/05
        // ��t�ӌ����Ή� TODO
        // SQL�ύX�ɔ����X�g�A�h�v���V�[�W�����ύX����K�v������
        // Addition - begin [Masahiko Higuchi]
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_FORMAT_KBN,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_KINYU_DT_ISHI,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_MAX_EDA_ISHI,"); // Addition [Kazuyoshi Kamitsukasa]
        // Addition - end
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_KINYU_DT,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_LASTDAY,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_CREATE_DT,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_LAST_TIME,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_KINYU_DT,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_CREATE_DT,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_LAST_TIME,");
        sb.append(" PATIENT.LAST_TIME");
        sb.append(" FROM");
        sb
                .append(" PATIENT LEFT OUTER JOIN IKN_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = IKN_ORIGIN_NEW.IKN_ORIGIN_PATIENT_NO");
        sb
                .append(" LEFT OUTER JOIN SIS_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = SIS_ORIGIN_NEW.SIS_ORIGIN_PATIENT_NO");
        sb.append(" ORDER BY");
        sb.append(" PATIENT_KN ASC");
        // add end s-fujihara

        data = (VRArrayList) dbm.executeQuery(sb.toString());
        dbm.finalize();

        // add start s-fujihara
        int end = data.getDataSize();
        for (int i = 0; i < end; i++) {
            VRMap map = ((VRMap) data.getData(i));
            map.setData("DELETE_FLAG", new Boolean(false));
        }
        // add end s-fujihara

        // del start s-fujihara
        // int end = data.getDataSize();
        // for (int i = 0; i < end; i++) {
        // VRHashMap map = ( (VRHashMap) data.getData(i));
        // Integer patientNo = (Integer) VRBindPathParser.get("PATIENT_NO",
        // map);
        // Integer ikenEda = (Integer) VRBindPathParser.get("IKN_MAX", map);
        // Integer sijiEda = (Integer) VRBindPathParser.get("SIS_MAX", map);
        //
        // if (ikenEda != null) {
        // sb = new StringBuffer();
        // sb.append("SELECT");
        // sb.append(" IKN_ORIGIN.KINYU_DT AS IKN_ORIGIN_KINYU_DT");
        // sb.append(",IKN_ORIGIN.LASTDAY AS IKN_ORIGIN_LASTDAY");
        // sb.append(",IKN_ORIGIN.CREATE_DT AS IKN_ORIGIN_CREATE_DT");
        // sb.append(",IKN_ORIGIN.LAST_TIME AS IKN_ORIGIN_LAST_TIME");
        // sb.append(" FROM");
        // sb.append(" IKN_ORIGIN");
        // sb.append(" WHERE");
        // sb.append(" (IKN_ORIGIN.PATIENT_NO=");
        // sb.append(patientNo);
        // sb.append(")");
        // sb.append(" AND(IKN_ORIGIN.EDA_NO=");
        // sb.append(ikenEda);
        // sb.append(")");
        //
        // map.putAll( (VRHashMap) ( (VRArrayList)
        // dbm.executeQuery(sb.toString())).
        // getData());
        // }
        // else {
        // map.setData("IKN_ORIGIN_KINYU_DT", new VRObject());
        // map.setData("IKN_ORIGIN_CREATE_DT", new VRObject());
        // map.setData("IKN_ORIGIN_LAST_TIME", new VRObject());
        // }
        //
        // if (sijiEda != null) {
        // sb = new StringBuffer();
        // sb.append("SELECT");
        // sb.append(" SIS_ORIGIN.KINYU_DT AS SIS_ORIGIN_KINYU_DT");
        // sb.append(",SIS_ORIGIN.CREATE_DT AS SIS_ORIGIN_CREATE_DT");
        // sb.append(",SIS_ORIGIN.LAST_TIME AS SIS_ORIGIN_LAST_TIME");
        // sb.append(" FROM");
        // sb.append(" SIS_ORIGIN");
        // sb.append(" WHERE");
        // sb.append(" (SIS_ORIGIN.PATIENT_NO=");
        // sb.append(patientNo);
        // sb.append(")");
        // sb.append(" AND(SIS_ORIGIN.EDA_NO=");
        // sb.append(sijiEda);
        // sb.append(")");
        //
        // map.putAll( (VRHashMap) ( (VRArrayList)
        // dbm.executeQuery(sb.toString())).
        // getData());
        // }
        // else {
        // map.setData("SIS_ORIGIN_KINYU_DT", new VRObject());
        // map.setData("SIS_ORIGIN_CREATE_DT", new VRObject());
        // map.setData("SIS_ORIGIN_LAST_TIME", new VRObject());
        // }
        //
        // map.setData("DELETE_FLAG", new VRBoolean());
        //
        // }
        // del end s-fujihara
        // �p�b�V�u�`�F�b�N�\��
        clearReservedPassive();
        reservedPassive(PASSIVE_CHECK_KEY, data);
        reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, data);
        reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, data);

        if (getTableModelAdapter() != null) {
            getTableModelAdapter().setAdaptee(data);
        }
        
        checkButtonsEnabled();

        setStatusText(String.valueOf(data.getDataSize()) + "���o�^����Ă��܂��B");
    }

    public Component getFirstFocusComponent() {
        return table;
        // return table.getTable();
    }

    /**
     * �c�[���{�^���̗L����Ԃ�ݒ肵�܂��B
     * 
     * @throws ParseException
     */
    private void checkButtonsEnabled() throws ParseException {
        boolean enabled = table.getSelectedModelRow() >= 0;
        detail.setEnabled(enabled);
        sijisho.setEnabled(enabled);
        ikensho.setEnabled(enabled);
        /** @todo menu */
        detailMenu.setEnabled(enabled);
        sijishoMenu.setEnabled(enabled);
        ikenshoMenu.setEnabled(enabled);

        print.setEnabled(table.getRowCount() > 0);

        enabled = getDeleteCheckedRows().size() > 0;
        delete.setEnabled(enabled);
        deleteMenu.setEnabled(enabled);

    }

    /**
     * �폜�`�F�b�N�̂����s�ԍ��W����Ԃ��܂��B
     * 
     * @throws ParseException ��͗�O
     * @return ArrayList �s�ԍ��W��
     */
    private ArrayList getDeleteCheckedRows() throws ParseException {
        ArrayList rows = new ArrayList();

        int end = data.getDataSize();
        for (int i = 0; i < end; i++) {
            VRMap row = (VRMap) data.getData(i);
            Boolean val = (Boolean) VRBindPathParser.get("DELETE_FLAG", row);
            if (val.booleanValue()) {
                rows.add(new Integer(i));
            }
        }
        return rows;
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoPatientList() {
        super();
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 2007/09/18 [Masahiko Higuchi] Addition - begin        
        //fitAffairButtonSize();
        // 2007/09/18 [Masahiko Higuchi] Addition - end
        
        deleteCheckEditor = new IkenshoCheckBoxTableCellEditor();

        deleteCheckEditor.addItemListener(new ItemListener() {

            public void itemStateChanged(ItemEvent e) {
                try {
                    checkButtonsEnabled();
                } catch (ParseException ex) {
                }
            }
        });

    }

    protected void tableSelected(ListSelectionEvent e) throws Exception {
        checkButtonsEnabled();
    }

    protected void printTableActionPerformed(ActionEvent e) throws Exception {
        if (ACMessageBox.showOkCancel("���ҏ��ꗗ�̈��", "�ꗗ�\���o�͂��Ă���낵���ł����H"
                + IkenshoConstants.LINE_SEPARATOR + "�i�ӂ肪�ȏ��Ɉ������܂��j", "���(O)",
                'O') != ACMessageBox.RESULT_OK) {
            return;
        }

        if (data.getDataSize() >= 2000) {
            if (ACMessageBox.show("���ҏ��̑�ʈ��", "���ҏ��2000���ȏ㑶�݂��܂��B"
                    + IkenshoConstants.LINE_SEPARATOR
                    + "�S���҂����������Ȃ��\��������܂����A�o�͂��Ă���낵���ł����H",
                    ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL) != ACMessageBox.RESULT_OK) {
                return;
            }
        }

        ACChotarouXMLWriter pd = new ACChotarouXMLWriter();
        String path = ACFrame.getInstance().getExeFolderPath()
                + IkenshoConstants.FILE_SEPARATOR + "format"
                + IkenshoConstants.FILE_SEPARATOR + "PatientList.xml";

        pd.beginPrintEdit();
        ACChotarouXMLUtilities.addFormat(pd, "daicho", "PatientList.xml");
//        pd.addFormat("daicho", path);

        int i = 0;
        int end = data.getDataSize();
        while (i < end) {
            int pageEnd = i + 20;
            if (pageEnd > end) {
                pageEnd = end;
            }

            int offset = i - 2;
            pd.beginPageEdit("daicho");
            for (; i < pageEnd; i++) {
                int index = i - offset;
                VRMap map = (VRMap) data.getData(i);
                // ����ID
                IkenshoCommon.addString(pd, map, "CHART_NO", "table.h" + index
                        + ".w1");
                // ����
                IkenshoCommon.addString(pd, map, "PATIENT_NM", "table.h"
                        + index + ".w2");
                // �ӂ肪��
                IkenshoCommon.addString(pd, map, "PATIENT_KN", "table.h"
                        + index + ".w3");
                // ����
                Object sex = VRBindPathParser.get("SEX", map);
                if (sex instanceof Integer) {
                    switch (((Integer) sex).intValue()) {
                    case 1:
                        IkenshoCommon.addString(pd, "table.h" + index + ".w4",
                                "�j��");
                        break;
                    case 2:
                        IkenshoCommon.addString(pd, "table.h" + index + ".w4",
                                "����");
                        break;
                    }
                }
                // �N��
                IkenshoCommon.addString(pd, "table.h" + index + ".w5",
                        IkenshoConstants.FORMAT_NOW_AGE.format(VRBindPathParser
                                .get("BIRTHDAY", map)));
                // ���N����
                IkenshoCommon.addString(pd, "table.h" + index + ".w6",
                        IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
                                .get("BIRTHDAY", map)));
                // �ŏI�f�@��
                // if (IkenshoCommon.getNewDocumentStatus(data, i,
                // "IKN_ORIGIN_CREATE_DT",
                // data, i, "SIS_ORIGIN_CREATE_DT") ==
                // IkenshoCommon.NEW_DOC_IKENSHO) {
                if (VRBindPathParser.has("IKN_ORIGIN_LASTDAY", map)) {
                    Object sinsatu = VRBindPathParser.get("IKN_ORIGIN_LASTDAY",
                            map);
                    if (sinsatu != null) {
                        IkenshoCommon
                                .addString(pd, "table.h" + index + ".w7",
                                        IkenshoConstants.FORMAT_ERA_YMD
                                                .format(sinsatu));
                    }
                }
                // }
                // �ŐV�ӌ����L����
                Object ikensho = VRBindPathParser.get("IKN_ORIGIN_KINYU_DT",
                        map);
                if (ikensho != null) {
                    IkenshoCommon.addString(pd, "table.h" + index + ".w8",
                            IkenshoConstants.FORMAT_ERA_YMD.format(ikensho));
                }
                // �ŐV�w�����L����
                Object sijisho = VRBindPathParser.get("SIS_ORIGIN_KINYU_DT",
                        map);
                if (sijisho != null) {
                    IkenshoCommon.addString(pd, "table.h" + index + ".w9",
                            IkenshoConstants.FORMAT_ERA_YMD.format(sijisho));
                }
                
                // 2006/07/05
                // ��t�ӌ����Ή�
                // Addition - begin [Masahiko Higuchi]
                // �ŐV��t�ӌ����L����
                Object isiIkensho = VRBindPathParser.get(
                        "IKN_ORIGIN_KINYU_DT_ISHI", map);
                if (isiIkensho != null) {
                    IkenshoCommon.addString(pd, "table.h" + index + ".w10",
                            IkenshoConstants.FORMAT_ERA_YMD.format(isiIkensho));
                }
                // Addition - end
                
                // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
                Object tokubetsuShijisho = VRBindPathParser.get(
                        "SIS_ORIGIN_KINYU_DT_TOKUBETSU", map);
                if (tokubetsuShijisho != null) {
                    // �����[��`�̎���
                    IkenshoCommon.addString(pd, "table.h" + index + ".w11",
                            IkenshoConstants.FORMAT_ERA_YMD
                                    .format(tokubetsuShijisho));
                }
                // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
                
            }
            pd.endPageEdit();
        }

        pd.endPrintEdit();

        openPDF(pd);

    }

    protected void tableDoubleClicked(MouseEvent e) throws Exception {
        detailActionPerformed(null);
    }

    /**
     * ���A�p�p�����^�}�b�v�𐶐����ĕԂ��܂��B
     * 
     * @param patientNo �I�𒆂̊��Ҕԍ�
     * @return ���A�p�p�����^�}�b�v
     */
    protected VRMap createPreviewData(int patientNo) {
        VRMap param = new VRHashMap();
        VRMap bs = new VRHashMap();
        bs.setData("PATIENT_NO", new Integer(patientNo));
        param.setData("ACT", "insert");
        param.setData("PREV_DATA", bs);
        return param;
    }

    protected void insertActionPerformed(ActionEvent e) throws Exception {
        if (e.getSource() == insert) {
            VRMap param = new VRHashMap();
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);

            ACAffairInfo affair = new ACAffairInfo(IkenshoPatientInfo.class
                    .getName(), param, "���ҍŐV��{���");
            ACFrame.getInstance().next(affair);
        }
        else if ((e.getSource() == ikensho) || (e.getSource() == ikenshoMenu) ) {
            int row = table.getSelectedModelRow();
            if (row < 0) {
                return;
            }

            VRMap rowData = (VRMap) data.getData(row);

            int patientNo = ((Integer) VRBindPathParser.get("PATIENT_NO",
                    rowData)).intValue();
            
            
            // 2006/09/22
            // remove - begin [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
            
//            Object maxEdaNo = VRBindPathParser.get("IKN_ORIGIN_MAX_EDA",
//                    rowData);
//            if (maxEdaNo instanceof Integer) {
//                StringBuffer sb = new StringBuffer();
//                sb.append("SELECT");
//                sb.append(" FD_OUTPUT_KBN");
//                sb.append(" FROM");
//                sb.append(" IKN_BILL");
//                sb.append(" WHERE");
//                sb.append(" (PATIENT_NO = ");
//                sb.append(patientNo);
//                sb.append(")");
//                sb.append(" AND (EDA_NO = ");
//                sb.append(((Integer) maxEdaNo).intValue());
//                sb.append(")");
//
//                IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
//                VRArrayList billArray = (VRArrayList) dbm.executeQuery(sb
//                        .toString());
//                dbm.finalize();
//                if (billArray.getDataSize() > 0) {
//                    Object obj = VRBindPathParser.get("FD_OUTPUT_KBN",
//                            (VRMap) billArray.getData());
//                    if (obj instanceof Integer) {
//                        if (((Integer) obj).intValue() == 1) {
//                            ACMessageBox.show("CSV�o�͑Ώۂ̈ӌ���������̂ŐV�K�쐬�ł��܂���B");
//                            return;
//                        }
//                    }
//                }
//            }

            // remove - end [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
            
            int flag = IkenshoCommon.checkInsurerDoctorCheck();
            if ((flag & IkenshoCommon.CHECK_INSURER_NOTHING) > 0) {
                if (ACMessageBox.show("�ی��҂��o�^����Ă��܂���B���o�^���܂����H",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(IkenshoHokenshaShousai.class
                                    .getName(), createPreviewData(patientNo),
                                    "�ی��ҏڍ�"));
                    return;
                }
            }
            if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
                if (ACMessageBox.show("��Ë@�֏�񂪓o�^����Ă��܂���B���o�^���܂����H",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    ACFrame.getInstance().next(
                            new ACAffairInfo(
                                    IkenshoIryouKikanJouhouShousai.class
                                            .getName(),
                                    createPreviewData(patientNo), "��Ë@�֏��ڍ�"));
                    return;
                }
            }

            VRMap param = new VRHashMap();
            param.putAll(rowData);
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);
            
            // 2006/09/22
            // CSV�o�͋敪���ʗp
            // Addition - begin [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
            int maxEdaNo = 0;
            // Addition - end [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
            
            // 2006/07/20 - ��t�ӌ����Ή� TODO
            // Addition - begin [Masahiko Higuchi]
             switch (ACMessageBox.showYesNoCancel("�쐬����ӌ����̎�ނ�I�����Ă��������B",
             "�厡��ӌ���(S)", 'S', "��t�ӌ���(I)",
             'I')) {
             case ACMessageBox.RESULT_YES:
                 
                 // 2006/09/22
                 // CSV�o�͋敪���ʗp
                 // Addition - begin [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
               maxEdaNo = ACCastUtilities.toInt(VRBindPathParser.get("IKN_ORIGIN_MAX_EDA",rowData),0);

               if (checkFDOutPutKubun(patientNo, maxEdaNo)) {
                   IkenshoIkenshoInfo.goIkensho(IkenshoConstants.IKENSHO_LOW_H18,
                           param);
               }
               // Addition - end [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]               
                 break;
             case ACMessageBox.RESULT_NO:
                 
                 // 2006/09/22
                 // CSV�o�͋敪���ʗp
                 // Addition - begin [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
               maxEdaNo = ACCastUtilities.toInt(VRBindPathParser.get("IKN_ORIGIN_MAX_EDA_ISHI",rowData),0);

               if (checkFDOutPutKubun(patientNo, maxEdaNo)) {
                   IkenshoIkenshoInfo.goIkensho(IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO,
                           param);
              }
               // Addition - end [Masahiko Higuchi] & [Kazuyoshi Kamitsukasa]
                 break;
             default:
                 return;
             }
             // Addition -End
        }
        else if ((e.getSource() == sijisho)  || (e.getSource() == sijishoMenu) ) {
            int row = table.getSelectedModelRow();
            if (row < 0) {
                return;
            }

            int flag = IkenshoCommon.checkInsurerDoctorCheck();
            if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
                if (ACMessageBox.show("��Ë@�֏�񂪓o�^����Ă��܂���B���o�^���܂����H",
                        ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
                        ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
                    VRMap rowData = (VRMap) data.getData(row);

                    int patientNo = ((Integer) VRBindPathParser.get(
                            "PATIENT_NO", rowData)).intValue();
                    ACFrame.getInstance().next(
                            new ACAffairInfo(
                                    IkenshoIryouKikanJouhouShousai.class
                                            .getName(),
                                    createPreviewData(patientNo), "��Ë@�֏��ڍ�"));
                    return;
                }
            }

            VRMap param = new VRHashMap();
            param.putAll((VRMap) data.getData(row));
            param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);

            
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
//            ACAffairInfo affair = new ACAffairInfo(
//                    IkenshoHoumonKangoShijisho.class.getName(), param,
//                    "�K��Ō�w����");
            ACAffairInfo affair = null;
             switch (ACMessageBox.showYesNoCancel("�쐬����w�����̎�ނ�I�����Ă��������B",
                    "�K��Ō�w����(H)", 'H', "���ʖK��Ō�w����(T)", 'T')) {
             case ACMessageBox.RESULT_YES:
                 affair = new ACAffairInfo(
                       IkenshoHoumonKangoShijisho.class.getName(), param,
                       "�K��Ō�w����");
                 break;
             case ACMessageBox.RESULT_NO:
                 affair = new ACAffairInfo(
                         IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param,
                         "���ʖK��Ō�w����");
                 break;
             default:
                 return;
             }
             // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
             ACFrame.getInstance().next(affair);

        }
    }

    /**
     * FD�o�͋敪�𔻕ʂ��܂��B
     * @param patientNo ���p�Ҕԍ�
     * @param maxEdaNo �ő�}�ԍ�
     * @return
     * @throws Exception
     */
    protected boolean checkFDOutPutKubun(int patientNo,int maxEdaNo) throws Exception{
      StringBuffer sb = new StringBuffer();
      sb.append("SELECT");
      sb.append(" FD_OUTPUT_KBN");
      sb.append(" FROM");
      sb.append(" IKN_BILL");
      sb.append(" WHERE");
      sb.append(" (PATIENT_NO = ");
      sb.append(patientNo);
      sb.append(")");
      sb.append(" AND (EDA_NO = ");
      sb.append(maxEdaNo);
      sb.append(")");
      
      IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
      VRArrayList billArray = (VRArrayList) dbm.executeQuery(sb
              .toString());
      dbm.finalize();
      if (billArray.getDataSize() > 0) {
          Object obj = VRBindPathParser.get("FD_OUTPUT_KBN",
                  (VRMap) billArray.getData());
          if (obj instanceof Integer) {
              if (((Integer) obj).intValue() == 1) {
                  ACMessageBox.show("CSV�o�͑Ώۂ̈ӌ���������̂ŐV�K�쐬�ł��܂���B");
                  return false;
              }
          }
      }

        return true;
    }

    protected void detailActionPerformed(ActionEvent e) throws Exception {
        int row = table.getSelectedModelRow();
        if (row < 0) {
            return;
        }
        // �I�����Ă��銳�҂̏ڍ׏���ʂɑJ�ڂ��܂��B
        VRMap param = new VRHashMap();
        param.putAll((VRMap) data.getData(row));
        param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_UPDATE);
        ACAffairInfo affair = new ACAffairInfo(IkenshoPatientInfo.class
                .getName(), param, "���ҍŐV��{���");
        ACFrame.getInstance().next(affair);
    }

    protected void deleteActionPerformed(ActionEvent e) throws Exception {
        ArrayList rows = getDeleteCheckedRows();
        int rowSize = rows.size();
        if (rowSize <= 0) {
            return;
        }

        // �`�F�b�N�{�b�N�X�̕ҏW��Ԃ̊m��
        table.stopCellEditing("DELETE_FLAG");

        if (ACMessageBox.show("�`�F�b�N����Ă��銳�҂̃f�[�^���S�č폜����܂��B\n��낵���ł����H",
                ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
                ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {

            IkenshoFirebirdDBManager dbm = null;
            try {
                // �p�b�V�u�`�F�b�N�J�n
                clearPassiveTask();

                for (int i = 0; i < rowSize; i++) {
                    addPassiveDeleteTask(PASSIVE_CHECK_KEY, ((Integer) rows
                            .get(i)).intValue());
                }
                // addPassiveDeleteTask(PASSIVE_CHECK_IKENSYO_KEY, row);
                // addPassiveDeleteTask(PASSIVE_CHECK_SIJISYO_KEY, row);

                dbm = getPassiveCheckedDBManager();
                if (dbm == null) {
                    ACMessageBox
                            .show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
                    return;
                }
                // �p�b�V�u�`�F�b�N�I��

                StringBuffer sb;
                sb = new StringBuffer();
                sb.append(" WHERE");
                sb.append(" PATIENT_NO IN (");
                sb.append(VRBindPathParser.get("PATIENT_NO", (VRMap) data
                        .getData(((Integer) rows.get(0)).intValue())));
                for (int i = 1; i < rowSize; i++) {
                    sb.append(", ");
                    sb.append(VRBindPathParser.get("PATIENT_NO", (VRMap) data
                            .getData(((Integer) rows.get(i)).intValue())));
                }
                sb.append(")");
                String whereStatement = sb.toString();

                sb = new StringBuffer();
                sb.append("DELETE");
                sb.append(" FROM");
                sb.append(" PATIENT");
                sb.append(whereStatement);
                dbm.executeUpdate(sb.toString());

                sb = new StringBuffer();
                sb.append("DELETE");
                sb.append(" FROM");
                sb.append(" COMMON_IKN_SIS");
                sb.append(whereStatement);
                dbm.executeUpdate(sb.toString());

                sb = new StringBuffer();
                sb.append("DELETE");
                sb.append(" FROM");
                sb.append(" IKN_ORIGIN");
                sb.append(whereStatement);
                dbm.executeUpdate(sb.toString());

                sb = new StringBuffer();
                sb.append("DELETE");
                sb.append(" FROM");
                sb.append(" GRAPHICS_COMMAND");
                sb.append(whereStatement);
                dbm.executeUpdate(sb.toString());

                sb = new StringBuffer();
                sb.append("DELETE");
                sb.append(" FROM");
                sb.append(" IKN_BILL");
                sb.append(whereStatement);
                dbm.executeUpdate(sb.toString());

                sb = new StringBuffer();
                sb.append("DELETE");
                sb.append(" FROM");
                sb.append(" SIS_ORIGIN");
                sb.append(whereStatement);
                dbm.executeUpdate(sb.toString());

                dbm.commitTransaction();
                dbm.finalize();
            } catch (Exception ex) {
                if (dbm != null) {
                    dbm.rollbackTransaction();
                    dbm.finalize();
                }
                throw ex;
            }

            int lastSelectedIndex = table.getSelectedSortedRow();
            // 2007/10/24 [Masahiko Higuchi] Delete - begin �\�[�X���r���[��C��
            //doSelect();
            // 2007/10/24 [Masahiko Higuchi] Delete - end
            // 2007/10/24 [Masahiko Higuchi] Addition - begin �\�[�X���r���[��C��
            doFind();
            // 2007/10/24 [Masahiko Higuchi] Addition - end
            table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

        }
    }
    
    /**
     * �������������s���܂��B
     */
    protected void findActionPerformed(ActionEvent e) throws Exception {
        // �����̎��s
        doFind();
        
    }

    public boolean canBack(VRMap parameters) throws Exception {
        return true;
    }

    public boolean canClose() {
        return true;
    }

    /**
     * �R���|�[�l���g�����������܂��B
     * 
     * @throws Exception ��������O
     */
    private void jbInit() throws Exception {
        buttons.setTitle("���ҏ��ꗗ");
        detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);
        detail.setActionCommand("���ҏ��(E)");
        detail.setMnemonic('E');
        detail.setText("���ҏ��(E)");
        detail.setToolTipText("�I�����ꂽ���҂̕ҏW��ʂֈڂ�܂��B");
        insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);
        insert.setMnemonic('S');
        insert.setText("���ғo�^(S)");
        insert.setToolTipText("���ҏ���V�K�ɍ쐬���܂��B");
        sijisho.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_SIJISYO);
        sijisho.setActionCommand("�w�����쐬(J)");
        sijisho.setMnemonic('J');
        sijisho.setText("�w����(J)");
        sijisho.setToolTipText("�I�����ꂽ���҂̎w������V�K�ɍ쐬���܂��B");
        ikensho.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_IKENSYO);
        ikensho.setActionCommand("�ӌ����쐬(I)");
        ikensho.setMnemonic('I');
        ikensho.setText("�ӌ���(I)");
        ikensho.setToolTipText("�I�����ꂽ���҂̈ӌ�����V�K�ɍ쐬���܂��B");
        delete.setText("���ҍ폜(D)");
        delete.setToolTipText("�`�F�b�N���ꂽ���҂̑S�����폜���܂��B");
        delete.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DELETE);
        delete.setActionCommand("���ғo�^(S)");
        delete.setMnemonic('D');
        print.setMnemonic('L');
        print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_TABLE_PRINT);
        print.setText("�ꗗ���(L)");
        print.setToolTipText("�o�^���҈ꗗ�\��������܂��B");
        /** @todo menu */
        detailMenu.setToolTipText("�I�����ꂽ���҂̕ҏW��ʂֈڂ�܂��B");
        detailMenu.setActionCommand("���ҏ��(E)");
        detailMenu.setMnemonic('E');
        detailMenu.setText("���ҏ��(E)");
        table.setPopupMenu(popup);
        table.setPopupMenuEnabled(true);

        ikenshoMenu.setText("�ӌ���(I)");
        ikenshoMenu.setToolTipText("�I�����ꂽ���҂̈ӌ�����V�K�ɍ쐬���܂��B");
        ikenshoMenu.setMnemonic('I');
        ikenshoMenu.setActionCommand("���ҏ��(E)");
        sijishoMenu.setText("�w����(J)");
        sijishoMenu.setToolTipText("�I�����ꂽ���҂̎w������V�K�ɍ쐬���܂��B");
        sijishoMenu.setMnemonic('J');
        sijishoMenu.setActionCommand("���ҏ��(E)");
        deleteMenu.setToolTipText("�`�F�b�N���ꂽ���҂̑S�����폜���܂��B");
        deleteMenu.setActionCommand("���ҏ��(E)");
        deleteMenu.setMnemonic('D');
        deleteMenu.setText("���ҍ폜(D)");
        this.add(buttons, VRLayout.NORTH);
        
        // 2007/09/18 [Masahiko Higuchi] Addition - begin 
        this.add(getFindPanel(),VRLayout.NORTH);
        this.add(getFindButtonPanel(),VRLayout.NORTH);
        // 2007/09/18 [Masahiko Higuchi] Addition - end
        
        this.add(table, VRLayout.CLIENT);

        buttons.add(print, VRLayout.EAST);
        buttons.add(delete, VRLayout.EAST);
        buttons.add(insert, VRLayout.EAST);
        buttons.add(sijisho, VRLayout.EAST);
        buttons.add(ikensho, VRLayout.EAST);
        buttons.add(detail, VRLayout.EAST);
        /** @todo menu */
        popup.add(detailMenu);
        popup.add(ikenshoMenu);
        popup.add(sijishoMenu);
        popup.add(deleteMenu);

    }

    public ACAffairButtonBar getButtonBar() {
        return buttons;
    }

    /**
     * ���҈ꗗ�e�[�u�����f���A�_�v�^��Ԃ��܂��B
     * 
     * @return ���҈ꗗ�e�[�u�����f���A�_�v�^
     */
    protected ACTableModelAdapter getTableModelAdapter() {
        return tableModelAdapter;
    }

    /**
     * ���҈ꗗ�e�[�u�����f���A�_�v�^��ݒ肵�܂��B
     * 
     * @param tableModelAdapter ���҈ꗗ�e�[�u�����f���A�_�v�^
     */
    protected void setTableModelAdapter(ACTableModelAdapter tableModelAdapter) {
        this.tableModelAdapter = tableModelAdapter;
    }
    
    /**
     * �����̈���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACPanel getFindPanel() {
        if(findPanel == null){
            findPanel = new ACPanel();
            findPanel.add(getKanjyaIDContainer(),VRLayout.FLOW_INSETLINE);
            findPanel.add(getKanjyaContainer(),VRLayout.FLOW_INSETLINE_RETURN);
            findPanel.add(getFuriganaContainer(),VRLayout.FLOW_INSETLINE);
            getFindPanel().add(getBirthDayContainer(),VRLayout.FLOW_INSETLINE_RETURN);
            findPanel.add(getReportBackContainer(),VRLayout.FLOW_INSETLINE);
        }
        
        return findPanel;
    }

    /**
     * �u�ӂ肪�ȁv�R���e�i���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getFuriganaContainer() {
        if(furiganaContainer == null){
            furiganaContainer = new ACLabelContainer();
            furiganaContainer.setText("�ӂ肪��");
            furiganaContainer.add(getFuriganaText());
        }
        return furiganaContainer;
    }

    /**
     * �u�ӂ肪�ȁv�e�L�X�g���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACTextField getFuriganaText() {
        if(furiganaText == null){
            furiganaText = new ACTextField();
            furiganaText.setColumns(15);
            furiganaText.setMaxLength(30);
            furiganaText.setIMEMode(InputSubset.KANJI);
            furiganaText.setHorizontalAlignment(SwingConstants.LEFT);
        }        
        return furiganaText;
    }

    /**
     * �u���ݗL���łȂ����҂��܂߂Č�������v�`�F�b�N�{�b�N�X���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACIntegerCheckBox getKanjyaCheck() {
        if(kanjyaCheck == null){
            kanjyaCheck = new ACIntegerCheckBox();
            kanjyaCheck.setText("���ݗL���łȂ����҂��܂߂Č�������(A)");
            kanjyaCheck.setMnemonic('A');
            kanjyaCheck.setBindPath("HIDE_FLAG");
        }    
        return kanjyaCheck;
    }

    /**
     * ����ID�R���e�i���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getKanjyaIDContainer() {
        if(kanjyaIDContainer == null){
            kanjyaIDContainer = new ACLabelContainer();
            kanjyaIDContainer.setText("����ID");
            kanjyaIDContainer.add(getKanjyaText());
        }    
        return kanjyaIDContainer;
    }

    /**
     * ����ID�e�L�X�g���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACTextField getKanjyaText() {
        if(kanjyaText == null){
            kanjyaText = new ACTextField();
            kanjyaText.setColumns(15);
            kanjyaText.setMaxLength(20);
            kanjyaText.setIMEMode(InputSubset.LATIN);
            kanjyaText.setHorizontalAlignment(SwingConstants.LEFT);
        }    
        return kanjyaText;
    }

    /**
     * ���[��ރR���{���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACComboBox getReportCombo() {
        if(reportCombo == null){
            reportCombo = new ACComboBox();
            reportCombo.setEditable(false);
            reportCombo.setColumns(8);
            reportCombo.setBlankItem("");
            reportCombo.setBlankable(true);
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
            //reportCombo.setModel(new String[]{"�厡��ӌ���","��t�ӌ���","�K��Ō�w����"});
            reportCombo.setModel(new String[]{"�厡��ӌ���","��t�ӌ���","�K��Ō�w����","���ʖK��Ō�w����"});
            // [ID:0000514][Tozo TANAKA] 2009/09/07 replace end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        } 
        return reportCombo;
    }
    
    /**
     * ���[��ރ��x���R���e�i���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getReportContainer() {
        if(reportContainer == null){
            reportContainer = new ACLabelContainer();
            VRLayout reportLayout = new VRLayout();
            reportLayout.setHgap(0);
            reportContainer.setText("���[���");
            reportContainer.setLayout(reportLayout);
            reportContainer.add(getReportCombo(),null);
        } 
        return reportContainer;
    }
    
    /**
     * ���N�����R���|�[�l���g���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected IkenshoEraDateTextField getBirthDayDateText() {
        if(birthDayDateText == null){
            birthDayDateText = new IkenshoEraDateTextField();
            birthDayDateText.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            birthDayDateText.setVisible(true);
        }
        return birthDayDateText;
    }

    /**
     * ���N�������x���R���e�i���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getBirthDayContainer() {
        if(birthDayContainer == null){
            birthDayContainer = new ACLabelContainer();
            birthDayContainer.add(getBirthDayDateText());
            birthDayContainer.setText("���N����");
        }
        return birthDayContainer;
    }
    
    /**
     * ���[���Ԍ����̈��Ԃ��܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACBackLabelContainer getReportBackContainer() {
        if(reportBackContainer == null){
            reportBackContainer = new ACBackLabelContainer();
            reportBackContainer.setAutoWrap(false);
            // ���[���
            reportBackContainer.add(getReportContainer(),VRLayout.FLOW);
            // �J�n���t
            reportBackContainer.add(getReportDateFromContainer(),VRLayout.FLOW);
            // �u�`�v
            reportBackContainer.add(getReportFromToLabel(),VRLayout.FLOW);
            // �I�����t
            reportBackContainer.add(getReportDateToContainer(),VRLayout.FLOW);
            // �N���A�{�^��
            reportBackContainer.add(getClearButton(),VRLayout.FLOW);
        }
        return reportBackContainer;
    }

    /**
     * �ŐV�L�����i�I�����j���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected IkenshoEraDateTextField getReportDateTo() {
        if(reportDateTo == null){
            reportDateTo = new IkenshoEraDateTextField();
            reportDateTo.setAgeVisible(false);
            reportDateTo.setEra("����");
            reportDateTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            reportDateTo.setAllowedFutureDate(true);
            reportDateTo.setBindPath("FIND_DATE_TO");
        }
        return reportDateTo;
    }
    /**
     * �ŐV�L�����R���e�i���擾���܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getReportDateToContainer() {
        if(reportDateToContainer == null){
            reportDateToContainer = new ACLabelContainer();
            reportDateToContainer.add(getReportDateTo());
        }
        return reportDateToContainer;
    }

    /**
     * �J�n���t���擾���܂��B
     * 
     * @return �J�n���t
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected IkenshoEraDateTextField getReportDateFrom() {
        if(reportDateFrom == null){
            reportDateFrom = new IkenshoEraDateTextField();
            reportDateFrom.setAgeVisible(false);
            reportDateFrom.setEra("����");
            reportDateFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
            reportDateFrom.setAllowedFutureDate(true);
            reportDateFrom.setBindPath("FIND_DATE_FROM");
        }
        return reportDateFrom;
    }
    
    /**
     * �J�n���t��Ԃ��܂��B
     * 
     * 2007/09/19 2007�N�x�Ή� [Masahiko Higuchi] Addition
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getReportDateFromContainer() {
        if(reportDateFromContainer == null){
            reportDateFromContainer = new ACLabelContainer();
            reportDateFromContainer.setText("�ŐV�L����");
            reportDateFromContainer.add(getReportDateFrom());
        }
        return reportDateFromContainer;
    }
    
    /**
     * �w�`�x�i���ԃZ�p���[�^�[�j���x����Ԃ��܂��B
     * 
     * 2007/09/19 2007�N�x�Ή� [Masahiko Higuchi] Addition
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabel getReportFromToLabel() {
        if(reportFromToLabel == null){
            reportFromToLabel = new ACLabel();
            reportFromToLabel.setText("�`");
        }
        return reportFromToLabel;
    }

    /**
     * ���t�����{�^����Ԃ��܂��B
     * 
     * 2007/09/19 2007�N�x�Ή� [Masahiko Higuchi] Addition
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACButton getClearButton() {
        if(clearButton == null){
            clearButton = new ACButton();
            clearButton.setText("���t����(C)");
            clearButton.setMnemonic('C');
            clearButton.setToolTipText("���͂��ꂽ���t���������܂��B");
        }
        return clearButton;
    }
    
    /**
     * ���[��ތ����̈�p�l����Ԃ��܂��B
     * 
     * 2007/09/19 2007�N�x�Ή� [Masahiko Higuchi] Addition
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACPanel getReportPanel() {
        if(reportPanel == null){
            reportPanel = new ACPanel();

        }
        return reportPanel;
    }


    /**
     * ���҃R���e�i���擾���܂��B
     * 
     * 2007/09/19 2007�N�x�Ή� [Masahiko Higuchi] Addition
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACLabelContainer getKanjyaContainer() {
        if(kanjyaContainer == null){
            kanjyaContainer = new ACLabelContainer();
            kanjyaContainer.add(getKanjyaCheck());
        }
        return kanjyaContainer;
    }
    
    /**
     * �����{�^�����擾���܂��B
     * 
     * 2007/09/19 2007�N�x�Ή� [Masahiko Higuchi] Addition
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACButton getFindButton() {
        if(findButton == null){
            VRLayout findButtonLayout = new VRLayout();
            findButtonLayout.setHgap(20);
            findButtonLayout.setVgap(20);
            findButton = new ACButton();
            findButton.setLayout(findButtonLayout);
            findButton.setText("����(V)");
            findButton.setMnemonic('V');
            findButton.setToolTipText("���ݓ��͂���Ă�������ɂ��A�ꗗ��\�����܂��B");
            
        }
        return findButton;
    }
    
    /**
     * �����������N���A�{�^�����擾���܂��B
     * 
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2008/01/16
     * @since 3.0.5
     */
    protected ACButton getInitialFindButton() {
    	if(initialFindButton == null){
            VRLayout initButtonLayout = new VRLayout();
            initButtonLayout.setHgap(20);
            initButtonLayout.setVgap(20);
            initialFindButton = new ACButton();
            initialFindButton.setLayout(initButtonLayout);
            initialFindButton.setText("�����������N���A(K)");
            initialFindButton.setMnemonic('K');
            initialFindButton.setToolTipText("�����������N���A���A�ꗗ��\�����܂��B");    		
    	}
    	return initialFindButton;
    }
    
    /**
     * �����{�^���p�̃p�l����Ԃ��܂��B
     * @return
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected ACPanel getFindButtonPanel() {
        if(findButtonPanel == null){
            ACLabel spaces = new ACLabel();
            ACLabel buttonSpace = new ACLabel();
            spaces.setText("�@");
            buttonSpace.setText(" ");
            findButtonPanel = new ACPanel();
            findButtonPanel.add(spaces,VRLayout.WEST);
            findButtonPanel.add(getFindButton(),VRLayout.WEST);
            findButtonPanel.add(buttonSpace,VRLayout.WEST);
            findButtonPanel.add(getInitialFindButton(),VRLayout.WEST);
        }
        return findButtonPanel;
    }
    
    /**
     * ���ҏ��ꗗ�F�L�����擾���܂��B
     * @return ���ҏ��ꗗ�F�L��
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    public ACTableColumn getPatientEnabledColumn(){
      if(patientEnabledColumn==null){
        patientEnabledColumn = new ACTableColumn(10);
        patientEnabledColumn.setHeaderValue("�L��");
        patientEnabledColumn.setColumns(3);
        patientEnabledColumn.setHorizontalAlignment(SwingConstants.CENTER);
        patientEnabledColumn.setRendererType(ACTableCellViewer.RENDERER_TYPE_ICON);
        patientEnabledColumn.setSortable(false);
        // �e�[�u���J�����Ƀt�H�[�}�b�^��ݒ肷��B
        patientEnabledColumn.setFormat(
                new ACHashMapFormat(new String[] {
                        "jp/nichicom/ac/images/icon/pix16/btn_080.png",
                        "jp/nichicom/ac/images/icon/pix16/btn_079.png" },
                        new Integer[] { new Integer(0), new Integer(1), }));

      }
      return patientEnabledColumn;

    }
    
    /**
     * ���p�҈ꗗ�F���N�������擾���܂��B
     * @return ���p�҈ꗗ�F���N����
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    public ACTableColumn getPatientBirthColumn(){
      if(patientBirthColumn==null){
        patientBirthColumn = new ACTableColumn(11);
        patientBirthColumn.setHeaderValue("���N����");
        patientBirthColumn.setColumns(10);
        patientBirthColumn.setFormat(new ACDateFormat("ggge�NMM��dd��"));
      }
      return patientBirthColumn;

    }

    /**
     * �����L�[���擾���܂��B
     * @return
     * @throws Exception
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    protected VRMap getFindKey() throws Exception{
        VRMap keyParam = new VRHashMap();
        
        String keyValue;
        int keyInteger;
        java.util.Date keyDate;
        
        // �ꗗ�\��
        keyInteger = 0;
        keyInteger = getKanjyaCheck().getValue();
        if(keyInteger == 0){
            // �`�F�b�N�Ȃ�
            keyParam.setData("HIDE_FLAG",new Boolean(false));
            
        }else{
            // �`�F�b�N�L
            keyParam.setData("HIDE_FLAG",new Boolean(true));
            
        }
        
        // ����ID
        keyValue = "";
        keyValue = getKanjyaText().getText();
        if(!"".equals(keyValue)){
            keyParam.setData("CHART_NO",getKanjyaText().getText());
        }
        
        // �ӂ肪��
        keyValue = "";
        keyValue = getFuriganaText().getText();
        if(!"".equals(keyValue)){
            keyParam.setData("PATIENT_KN",keyValue);
        }
        
        // ���N����
        keyDate = null;
        keyDate = (java.util.Date) getBirthDayDateText().getDate();
        if(keyDate != null){
            keyParam.setData("BIRTHDAY",keyDate);
        }
        
        // ���[���
        keyInteger = -1;
        keyInteger = getReportCombo().getSelectedIndex();
        switch(keyInteger){
            case -1: // ���I��
                break;
            case 1: // �厡��ӌ���
                keyParam.setData("IKN_ORIGIN_KINYU_DT",new Boolean(true));
                break;
            case 2: // ��t�ӌ���
                keyParam.setData("IKN_ORIGIN_KINYU_DT_ISHI",new Boolean(true));
                break;
            case 3: // �K��Ō�w����
                keyParam.setData("SIS_ORIGIN_KINYU_DT",new Boolean(true));
                break;
            // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
            case 4: // ���ʖK��Ō�w����
                keyParam.setData("SIS_ORIGIN_KINYU_DT_TOKUBETSU",new Boolean(true));
                break;
            // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        }
        
        // �ŐV�L����
        keyDate = null;
        keyDate = (java.util.Date)getReportDateFrom().getDate();
        if(keyDate != null){
            keyParam.setData("KINYU_DT_FROM",keyDate);
        }
        
        keyDate = null;
        keyDate = (java.util.Date)getReportDateTo().getDate();
        if(keyDate != null){
            keyParam.setData("KINYU_DT_TO",keyDate);
        }
        
        return keyParam;
        
    }

    /**
    /**
     * �������������s���܂��B
     * @throws Exception
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    public void doFind() throws Exception{
        
        VRMap param = new VRHashMap();
        StringBuffer sb = new StringBuffer();
        IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
        
        // �����L�[�̃`�F�b�N����
        if(!checkFindKey()){
            return;
            
        }
        // �����L�[�̎擾
        param = getFindKey();
        
        sb.append(" SELECT");
        sb.append(" PATIENT.PATIENT_NO,");
        sb.append(" PATIENT.CHART_NO,");
        sb.append(" PATIENT.PATIENT_NM,");
        sb.append(" PATIENT.PATIENT_KN,");
        sb.append(" PATIENT.SEX,");
        sb.append(" PATIENT.BIRTHDAY,");
        sb.append(" PATIENT.KOUSIN_DT,");
        sb.append(" PATIENT.SHOW_FLAG,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_MAX_EDA,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_FORMAT_KBN,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_KINYU_DT_ISHI,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_MAX_EDA_ISHI,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_KINYU_DT,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_LASTDAY,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_CREATE_DT,");
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_LAST_TIME,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_KINYU_DT,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_CREATE_DT,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_LAST_TIME,");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_FORMAT_KBN,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_KINYU_DT_TOKUBETSU,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_MAX_EDA_TOKUBETSU,");
        // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
        sb.append(" PATIENT.LAST_TIME");
        sb.append(" FROM");
        sb
                .append(" PATIENT LEFT OUTER JOIN IKN_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = IKN_ORIGIN_NEW.IKN_ORIGIN_PATIENT_NO");
        sb
                .append(" LEFT OUTER JOIN SIS_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = SIS_ORIGIN_NEW.SIS_ORIGIN_PATIENT_NO");
        
        // WHERE��̍쐬
        buildFindWhere(sb,param);
        
        sb.append(" ORDER BY");
        sb.append(" PATIENT_KN ASC");
        
        data = (VRArrayList) dbm.executeQuery(sb.toString());
        dbm.finalize();

        // �ӂ肪�Ȃ�SQL�����s�\�Ȃ��߃L�[�������Ă���ꍇ�͎蓮�Ō�������B
        data = doManualFind(data,param,"PATIENT_KN");
        
        // ����ID�ɂ��Ă��蓮�Ō���
        data = doManualFind(data,param,"CHART_NO");
        
        int end = data.getDataSize();
        for (int k = 0; k < end; k++) {
            VRMap map = ((VRMap) data.getData(k));
            map.setData("DELETE_FLAG", new Boolean(false));
        }
        // �p�b�V�u�`�F�b�N�\��
        clearReservedPassive();
        reservedPassive(PASSIVE_CHECK_KEY, data);
        reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, data);
        reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, data);

        if (getTableModelAdapter() != null) {
            getTableModelAdapter().setAdaptee(data);
        }
        
        checkButtonsEnabled();

        setStatusText(String.valueOf(data.getDataSize()) + "���o�^����Ă��܂��B");
        
        table.setSelectedSortedFirstRow();            

    }
    
    /**
     * �����L�[���`�F�b�N���܂��B
     * @return True:���� False:�ُ�L
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    public boolean checkFindKey(){
        // ���N�������s���ȏꍇ
        if (!"".equals(getBirthDayDateText().getEra())) {
            if (getBirthDayDateText().getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("���N�����̓��͂Ɍ�肪����܂��B");
                getBirthDayDateText().transferFocus();
                return false;

            }
        }
        
        // �ŐV�L�����i�J�n�j�����͂���Ă���ꍇ
        if(isIkenshoEraDateInput(getReportDateFrom())){
        //if (!"".equals(getReportDateFrom().getEra())) {
            getReportDateFrom().setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if(getReportDateFrom().getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("�ŐV�L�����̊J�n���t�̓��͂Ɍ�肪����܂��B");
                getReportDateFrom().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                getReportDateFrom().transferFocus();
                return false;            
            }
            getReportDateFrom().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }

        // �ŐV�L�����i�I���j�����͂���Ă���ꍇ
        if(isIkenshoEraDateInput(getReportDateTo())){
        //if (!"".equals(getReportDateTo().getEra())) {
            getReportDateTo().setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
            if(getReportDateTo().getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
                ACMessageBox.showExclamation("�ŐV�L�����̏I�����t�̓��͂Ɍ�肪����܂��B");
                getReportDateTo().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
                getReportDateTo().transferFocus();
                return false;            
            }
            getReportDateTo().setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
        }
        
        // ���t�̑召��r
        if (isIkenshoEraDateInput(getReportDateFrom())
                && isIkenshoEraDateInput(getReportDateTo())) {
            if (ACDateUtilities.compareOnDay(getReportDateFrom().getDate(),
                    getReportDateTo().getDate()) > 0) {
                ACMessageBox.showExclamation("�ŐV�L�����̊J�n���t�ƏI�����t���t�]���Ă��܂��B");
                getReportDateFrom().transferFocus();
                return false;

            }
        }
        
        return true;
    }
    
    /**
     * �㌩�����t�R���|�[�l���g�ɓ��͂����邩�`�F�b�N���܂��B
     * @param eraDateText
     * @return
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    private boolean isIkenshoEraDateInput(IkenshoEraDateTextField eraDateText){
        return (!"".equals(eraDateText.getEra())
                &&!"".equals(eraDateText.getYear()));

    }
    
    /**
     * ��������Where����\�z���܂��B
     * @param sb
     * @param sqlParam
     * @throws Exception
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    public StringBuffer buildFindWhere(StringBuffer sb,VRMap sqlParam) throws Exception{
        
        boolean dayFrom = false;
        boolean dayTo = false;
        
        sb.append(" WHERE");
        // �ꗗ�\��
        if(ACCastUtilities.toBoolean(sqlParam.getData("HIDE_FLAG")) == false){
            sb.append(" SHOW_FLAG IN(1)");
        }else{
            sb.append(" SHOW_FLAG IN(0,1)");
        }
        // ���N����
        if(sqlParam.containsKey("BIRTHDAY")){
            sb.append(" AND");
            sb.append(" BIRTHDAY = ");
            sb.append(getDBSafeDate("BIRTHDAY",sqlParam));
        }
        // ���[���
        //�@�厡��ӌ����I����
        if(sqlParam.containsKey("IKN_ORIGIN_KINYU_DT")){
            sb.append(" AND");
            sb.append(" IKN_ORIGIN_KINYU_DT IS NOT NULL");
            // �ŐV�L�����i�J�n�j
            if(sqlParam.containsKey("KINYU_DT_FROM")){
                sb.append(" AND");
                sb.append(" IKN_ORIGIN_KINYU_DT >= ");
                sb.append(getDBSafeDate("KINYU_DT_FROM",sqlParam));
            }
            // �ŐV�L�����i�I���j
            if(sqlParam.containsKey("KINYU_DT_TO")){
                sb.append(" AND");
                sb.append(" IKN_ORIGIN_KINYU_DT <= ");
                sb.append(getDBSafeDate("KINYU_DT_TO",sqlParam));
            }            

        }else{
            // ��t�ӌ����I����
            if(sqlParam.containsKey("IKN_ORIGIN_KINYU_DT_ISHI")){
                sb.append(" AND");
                sb.append(" IKN_ORIGIN_KINYU_DT_ISHI IS NOT NULL");
                // �ŐV�L�����i�J�n�j
                if(sqlParam.containsKey("KINYU_DT_FROM")){
                    sb.append(" AND");
                    sb.append(" IKN_ORIGIN_KINYU_DT_ISHI >= ");
                    sb.append(getDBSafeDate("KINYU_DT_FROM",sqlParam));
                }
                // �ŐV�L�����i�I���j
                if(sqlParam.containsKey("KINYU_DT_TO")){
                    sb.append(" AND");
                    sb.append(" IKN_ORIGIN_KINYU_DT_ISHI <= ");
                    sb.append(getDBSafeDate("KINYU_DT_TO",sqlParam));
                }         
                
            }else{
                // �K��Ō�w�����I����
                if(sqlParam.containsKey("SIS_ORIGIN_KINYU_DT")){
                    sb.append(" AND");
                    sb.append(" SIS_ORIGIN_KINYU_DT IS NOT NULL");
                    // �ŐV�L�����i�J�n�j
                    if(sqlParam.containsKey("KINYU_DT_FROM")){
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT >= ");
                        sb.append(getDBSafeDate("KINYU_DT_FROM",sqlParam));
                    }
                    // �ŐV�L�����i�I���j
                    if(sqlParam.containsKey("KINYU_DT_TO")){
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT <= ");
                        sb.append(getDBSafeDate("KINYU_DT_TO",sqlParam));
                    }     
                    // [ID:0000514][Tozo TANAKA] 2009/09/07 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
                }else if(sqlParam.containsKey("SIS_ORIGIN_KINYU_DT_TOKUBETSU")){
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT_TOKUBETSU IS NOT NULL");
                        // �ŐV�L�����i�J�n�j
                        if(sqlParam.containsKey("KINYU_DT_FROM")){
                            sb.append(" AND");
                            sb.append(" SIS_ORIGIN_KINYU_DT_TOKUBETSU >= ");
                            sb.append(getDBSafeDate("KINYU_DT_FROM",sqlParam));
                        }
                        // �ŐV�L�����i�I���j
                        if(sqlParam.containsKey("KINYU_DT_TO")){
                            sb.append(" AND");
                            sb.append(" SIS_ORIGIN_KINYU_DT_TOKUBETSU <= ");
                            sb.append(getDBSafeDate("KINYU_DT_TO",sqlParam));
                        }     
                    
                    // [ID:0000514][Tozo TANAKA] 2009/09/07 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\  
                    
                }else{
                // �����I������Ă��Ȃ��ꍇ�i���[��ށj
                    // �ŐV�L����
                    if(sqlParam.containsKey("KINYU_DT_FROM")){
                        dayFrom = true;
                    }
                    if(sqlParam.containsKey("KINYU_DT_TO")){
                        dayTo = true;
                    }
                    
                    // �o���Ƃ����͂��Ȃ��ꍇ�͏����I��
                    if(!dayFrom && !dayTo){
                        return sb;
                    }else{
                        sb.append(" AND(");
                    }
                                        
                    // �厡��ӌ����̓��t������쐬
                    buildDateWhere(sb,sqlParam,dayFrom,dayTo,"IKN_ORIGIN_KINYU_DT");

                    sb.append(" OR");
                    // ��t�ӌ����̓��t������쐬
                    buildDateWhere(sb,sqlParam,dayFrom,dayTo,"IKN_ORIGIN_KINYU_DT_ISHI");
                    
                    sb.append(" OR");
                    // �K��Ō�w�����̓��t������쐬
                    buildDateWhere(sb,sqlParam,dayFrom,dayTo,"SIS_ORIGIN_KINYU_DT");

                    // [ID:0000514][Tozo TANAKA] 2009/09/15 add begin �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
                    sb.append(" OR");
                    // ���ʖK��Ō�w�����̓��t������쐬
                    buildDateWhere(sb,sqlParam,dayFrom,dayTo,"SIS_ORIGIN_KINYU_DT_TOKUBETSU");
                    // [ID:0000514][Tozo TANAKA] 2009/09/15 add end �y2009�N�x�Ή��F�K��Ō�w�����z���ʎw�����̊Ǘ��@�\
                    
                    sb.append(")");
                }
            }
        }
        
        return sb;
    }

    /**
     * �厡��ӌ����̓��t��Where����쐬���܂��B
     * @param sb SQL�{��
     * @param sqlParam �����L�[�Q
     * @param isDateFromKey �J�n���t�̓��̗͂L�� True:���͗L False:���͖�
     * @param isDateToKey �I�����t�̓��̗͂L�� True:���͗L False:���͖�
     * @param dateKeyName Where��̃L�[����
     * @return
     * @throws Exception
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    private void buildDateWhere(StringBuffer sb, VRMap sqlParam,
            boolean isDateFromKey, boolean isDateToKey,String dateKeyName) throws Exception {
        
        // �ŐV�L�����i�J�n�j
        if(sqlParam.containsKey("KINYU_DT_FROM")){
            sb.append(" ");
            sb.append(dateKeyName);
            sb.append(" >= ");
            sb.append(getDBSafeDate("KINYU_DT_FROM",sqlParam));
        }
        
        // ���ɓ��͍ς�
        if (isDateFromKey && isDateToKey) {
            sb.append(" AND");
        }
        
        // �ŐV�L�����i�I���j
        if(sqlParam.containsKey("KINYU_DT_TO")){
            sb.append(" ");
            sb.append(dateKeyName);
            sb.append(" <= ");
            sb.append(getDBSafeDate("KINYU_DT_TO",sqlParam));
        }

    }
    
    /**
     * �u���t�����v�{�^��������
     *
     * @param e ActionEvent
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    private void doClearDate_actionPerformed(ActionEvent e) {
        getReportDateFrom().clear();
        getReportDateFrom().setEra("����");
        getReportDateTo().clear();
        getReportDateTo().setEra("����");
    }
    
    /**
     * �f�[�^�Q�̒����猟���������s���܂��B
     * 
     * @param data �����Ώۃf�[�^�Q
     * @param findParam �����L�[�p�����[�^�[�Q
     * @param findKey �����L�[
     * @return �����L�[�̒l�Ɉ�v����f�[�^�Q
     * @throws Exception ��O
     * @author Masahiko Higuchi
     * @since 3.0.5
     */
    private VRArrayList doManualFind(VRArrayList data, VRMap findParam,
            String findKey) throws Exception {
        // �����l�����݂���ꍇ
        if (findParam != null && findParam.containsKey(findKey)) {
            // �����L�[
            String findValue = ACCastUtilities.toString(findParam.get(findKey));
            VRArrayList cloneArray = new VRArrayList();
            cloneArray = (VRArrayList) data.clone();
            data = new VRArrayList();
            for (int j = 0; j < cloneArray.size(); j++) {
                VRMap map = new VRHashMap();
                map = (VRMap) cloneArray.getData(j);
                // �O����v����
                if ((ACCastUtilities.toString(map.getData(findKey)))
                        .startsWith(findValue)) {
                    // ��v�����l�����ʂɊi�[����B
                    data.add(map);
                }
            }

        }

        return data;
    }
    
    
    /**
     * 
     * IkenshoPatientList_clearDate_actionAdapter�ł��B
     * <p>
     * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
     * </p>
     * @author Masahiko Higuchi
     * @version 1.0 2007/10/15
     */
    class IkenshoPatientList_clearDate_actionAdapter implements ActionListener {
        private IkenshoPatientList adaptee;

        IkenshoPatientList_clearDate_actionAdapter(IkenshoPatientList adaptee) {
            this.adaptee = adaptee;
        }

        public void actionPerformed(ActionEvent e) {
            adaptee.doClearDate_actionPerformed(e);
        }
    }
    
    /**
     * ���������N���A�{�^���̃A�N�V�������X�i�N���X�ł�
     * 
     * @author Masahiko Higuchi
     * @version 1.0 2008/01/16
     * @since 3.0.5
     * 
     */
    class IkenshoPatientList_initialFind_actionAdapter implements ActionListener {
        private IkenshoPatientList adaptee; 
    	
        IkenshoPatientList_initialFind_actionAdapter(IkenshoPatientList adaptee){
        	this.adaptee = adaptee; 
        }
        
        public void actionPerformed(ActionEvent e) {
        	try {
        		// ���������N���A����
        		adaptee.doInitialFind_actionPerformed(e);
        		
        	}catch(Exception ex){
        		ex.printStackTrace();
        	}
        	
        }
    	
    }
    
    /**
     * ���������N���A�������̃��C������
     * 
     * @param e
     * @author Masahiko Higuchi
     * @since 3.0.5
     * @version 1.0 2008/01/16
     */
    private void doInitialFind_actionPerformed(ActionEvent e) throws Exception{
    	// �S�ď��������Č��������𑖂点��
        // ����ID
        getKanjyaText().setText("");
        // �t���K�i
        getFuriganaText().setText("");
        // ���N����
        getBirthDayDateText().clear();
        // ���[���
        getReportCombo().setText("");
    	// ���[����
        getReportDateFrom().clear();
        getReportDateFrom().setEra("����");
        getReportDateTo().clear();
        getReportDateTo().setEra("����");
        // �ꗗ�\��
        getKanjyaCheck().setValue(0);
        
        // ��������
        doFind();
        
    }
    
}
