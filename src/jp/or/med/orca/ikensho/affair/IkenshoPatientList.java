package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;

import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.component.table.ACTable;
import jp.nichicom.ac.component.table.ACTableColumn;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACAffairable;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
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

        VRMap params = affair.getParameters();
        if (VRBindPathParser.has("PREV_DATA", params)) {
            // ��ʑJ�ڃL���b�V���f�[�^������ꍇ�́A�p�����^��u��������
            params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
        }

        doSelect();

        setTableModelAdapter(new ACTableModelAdapter(data, new String[] {
                "CHART_NO", "PATIENT_NM", "PATIENT_KN", "SEX", "BIRTHDAY",
                "IKN_ORIGIN_KINYU_DT", "SIS_ORIGIN_KINYU_DT", "KOUSIN_DT",
                "DELETE_FLAG" }));

        table.setModel(getTableModelAdapter());

        table.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
                new ACTableColumn(8, 22, "�@",
                        new IkenshoCheckBoxTableCellRenderer(),
                        deleteCheckEditor),
                new ACTableColumn(0, 50, "����ID"),
                new ACTableColumn(1, 110, "����"),
                new ACTableColumn(2, 130, "�ӂ肪��"),
                new ACTableColumn(3, 32, "����", SwingConstants.CENTER,
                        IkenshoConstants.FORMAT_SEX),
                new ACTableColumn(4, 32, "�N��", SwingConstants.RIGHT,
                        IkenshoConstants.FORMAT_NOW_AGE),
                new ACTableColumn(5, 120, "�ŐV�ӌ����L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new ACTableColumn(6, 120, "�ŐV�w�����L����",
                        IkenshoConstants.FORMAT_ERA_YMD),
                new ACTableColumn(7, 150, "�ŏI�X�V��",
                        IkenshoConstants.FORMAT_ERA_HMS), }));

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
        sb.append(" IKN_ORIGIN_NEW.IKN_ORIGIN_MAX_EDA,");
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
            Object maxEdaNo = VRBindPathParser.get("IKN_ORIGIN_MAX_EDA",
                    rowData);
            if (maxEdaNo instanceof Integer) {
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
                sb.append(((Integer) maxEdaNo).intValue());
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
                            return;
                        }
                    }
                }
            }

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

            // switch (NCMessageBox.showYesNoCancel("�쐬����ӌ����̑Ή��N�x��I�����Ă��������B",
            // "����17�N�x(7)", '7', "����18�N�x(8)",
            // '8')) {
            // case NCMessageBox.RESULT_YES:
            // IkenshoIkenshoInfo.goIkensho(IkenshoConstants.IKENSHO_LOW_DEFAULT,
            // param);
            // break;
            // case NCMessageBox.RESULT_NO:
            IkenshoIkenshoInfo.goIkensho(IkenshoConstants.IKENSHO_LOW_H18,
                    param);
            // break;
            // default:
            // return;
            // }
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

            ACAffairInfo affair = new ACAffairInfo(
                    IkenshoHoumonKangoShijisho.class.getName(), param,
                    "�K��Ō�w����");
            ACFrame.getInstance().next(affair);
        }
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
            doSelect();
            table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

        }
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

}
