package jp.or.med.orca.ikensho.affair;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.im.InputSubset;
import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import javax.swing.event.ListSelectionEvent;

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
import jp.nichicom.ac.io.ACAgeEncorder;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.sql.ACPassiveKey;
import jp.nichicom.ac.text.ACDateFormat;
import jp.nichicom.ac.text.ACHashMapFormat;
import jp.nichicom.ac.util.ACDateUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.ACMessageBoxDialogPlus;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.component.table.VRTableColumnModel;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellEditor;
import jp.or.med.orca.ikensho.component.table.IkenshoCheckBoxTableCellRenderer;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoReceiptSoftGLServerManager;
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

	// 2007/09/18 [Masahiko.Higuchi] Addition - begin
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
	// 有効カラム
	private ACTableColumn patientEnabledColumn;
	// 生年月日カラム
	private ACTableColumn patientBirthColumn;
	// 2007/09/18 [Masahiko.Higuchi] Addition - end

	// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
	private ACLabelContainer receiptContainer;
	private ACIntegerCheckBox receiptCheck;
	private ACPanel receiptChangePanel;

	private ACButton receiptSettingButton;
	// 最大検索可能数
	private int maxFindCount = 1000;

	// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end

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
// [ID:0000788][Satoshi Tokusari] 2015/11 add-Start 患者情報一覧の項目幅見直し対応
    // ＤＢ項目、ヘッダー名、カラムサイズ
    // フォーマット（0：なし、1：チェック、2：有効、3：性別、4：年齢、5：日付、6：日時、7：非表示）
    private static final String[][] columnsList = new String[][] {
        { "DELETE_FLAG", "　", "2", "1" },
        { "SHOW_FLAG", "有効", "3", "2" },
        { "CHART_NO", "患者ID", "4", "0" },
        { "PATIENT_NM", "氏名", "8", "0" },
        { "PATIENT_KN", "ふりがな", "9", "0" },
        { "SEX", "性別", "3", "3"},
        { "BIRTHDAY", "生年月日", "9", "5" },
        { "BIRTHDAY", "年齢", "3", "4" },
        { "IKN_ORIGIN_KINYU_DT", "最新主治医意見書記入日", "12", "5" },
        { "IKN_ORIGIN_KINYU_DT_ISHI", "最新医師意見書記入日", "11", "5" },
        { "SIS_ORIGIN_KINYU_DT", "最新指示書記入日", "9", "5" },
        { "SIS_ORIGIN_KINYU_DT_TOKUBETSU", "最新特別指示書記入日", "11", "5" },
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
        { "SIS_ORIGIN_KINYU_DT_SEISHIN", "最新精神指示書記入日", "11", "5" },
        { "SIS_ORIGIN_KINYU_DT_TK_SEISHIN", "最新精神特別指示書記入日", "13", "5" },
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
        { "KOUSIN_DT", "最終更新日", "14", "6" },
// [ID:0000799][Satoshi Tokusari] 2015/11 add-Start 患者一覧画面の[一覧印刷]ソート対応
        { "IKN_ORIGIN_LASTDAY", "", "0", "7" },
// [ID:0000799][Satoshi Tokusari] 2015/11 add-End
        } ;
// [ID:0000788][Satoshi Tokusari] 2015/11 add-End

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

		// 2007/09/18 [Masahiko.Higuchi] Addition - begin
		// addFindTrigger(getFindMenu());
		// addFindTrigger(getFind());
		addFindTrigger(getFindButton());
		getClearButton().addActionListener(
				new IkenshoPatientList_clearDate_actionAdapter(this));
		getInitialFindButton().addActionListener(
				new IkenshoPatientList_initialFind_actionAdapter(this));
		// 2007/09/18 [Masahiko.Higuchi] Addition - end

		VRMap params = affair.getParameters();
		if (VRBindPathParser.has("PREV_DATA", params)) {
			// 画面遷移キャッシュデータがある場合は、パラメタを置き換える
			params = (VRMap) VRBindPathParser.get("PREV_DATA", params);
		}

		// 検索処理
		doFind();

// [ID:0000788][Satoshi Tokusari] 2015/11 edit-Start 患者情報一覧の項目幅見直し対応
//		setTableModelAdapter(new ACTableModelAdapter(data, new String[] {
//				"CHART_NO", "PATIENT_NM", "PATIENT_KN", "SEX", "BIRTHDAY",
//				"IKN_ORIGIN_KINYU_DT", "SIS_ORIGIN_KINYU_DT", "KOUSIN_DT",
//				"DELETE_FLAG", "IKN_ORIGIN_KINYU_DT_ISHI", "SHOW_FLAG",
//				"BIRTHDAY", "PATIENT_NO", "SIS_ORIGIN_KINYU_DT_TOKUBETSU" }));
//		table.setModel(getTableModelAdapter());
//
//		table.setColumnModel(new VRTableColumnModel(new ACTableColumn[] {
////				new ACTableColumn(8, 22, "　",
////						new IkenshoCheckBoxTableCellRenderer(),
////						deleteCheckEditor),
////				getPatientEnabledColumn(),
////				new ACTableColumn(0, 50, "患者ID"),
////				new ACTableColumn(1, 110, "氏名"),
////				new ACTableColumn(2, 130, "ふりがな"),
////				new ACTableColumn(3, 32, "性別", SwingConstants.CENTER,
////						IkenshoConstants.FORMAT_SEX),
////				getPatientBirthColumn(),
////				new ACTableColumn(4, 32, "年齢", SwingConstants.RIGHT,
////						IkenshoConstants.FORMAT_NOW_AGE),
////				new ACTableColumn(5, 150, "最新主治医意見書記入日",
////						IkenshoConstants.FORMAT_ERA_YMD),
////				new ACTableColumn(9, 150, "最新医師意見書記入日",
////						IkenshoConstants.FORMAT_ERA_YMD),
////				new ACTableColumn(6, 120, "最新指示書記入日",
////						IkenshoConstants.FORMAT_ERA_YMD),
////				new ACTableColumn(13, 140, "最新特別指示書記入日",
////						IkenshoConstants.FORMAT_ERA_YMD),
////				new ACTableColumn(7, 120, "最終更新日",
////						IkenshoConstants.FORMAT_ERA_HMS), }));
//				new ACTableColumn(8, 30, "　",
//						new IkenshoCheckBoxTableCellRenderer(),
//						deleteCheckEditor),
//				getPatientEnabledColumn(),
//				new ACTableColumn(0, 90, "患者ID"),
//				new ACTableColumn(1, 180, "氏名"),
//				new ACTableColumn(2, 240, "ふりがな"),
//				new ACTableColumn(3, 60, "性別", SwingConstants.CENTER,
//						IkenshoConstants.FORMAT_SEX),
//				getPatientBirthColumn(),
//				new ACTableColumn(4, 60, "年齢", SwingConstants.RIGHT,
//						IkenshoConstants.FORMAT_NOW_AGE),
//				new ACTableColumn(5, 260, "最新主治医意見書記入日",
//						IkenshoConstants.FORMAT_ERA_YMD),
//				new ACTableColumn(9, 260, "最新医師意見書記入日",
//						IkenshoConstants.FORMAT_ERA_YMD),
//				new ACTableColumn(6, 200, "最新指示書記入日",
//						IkenshoConstants.FORMAT_ERA_YMD),
//				new ACTableColumn(13, 260, "最新特別指示書記入日",
//						IkenshoConstants.FORMAT_ERA_YMD),
//				new ACTableColumn(7, 220, "最終更新日",
//						IkenshoConstants.FORMAT_ERA_HMS), }));
        setTableModelAdapter(new ACTableModelAdapter(data, getDbName()));
        table.setModel(getTableModelAdapter());
		table.setColumnModel(new VRTableColumnModel(getColumns()));
		table.setExtendLastColumn(false);
// [ID:0000788][Satoshi Tokusari] 2015/11 edit-End

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

			// 初行を自動選択
			table.setSelectedModelRow(sel);
		}

	}

// [ID:0000788][Satoshi Tokusari] 2015/11 add-Start 患者情報一覧の項目幅見直し対応
    /**
     * ＤＢの項目名を取得します。
     *
     * @return ＤＢの項目名
     */
    private String[] getDbName () {
        String[] dbName = new String[columnsList.length];
        for (int i = 0; i < columnsList.length; i++) {
            dbName[i] = (String)columnsList[i][0];
        }
        return dbName;
    };

    /**
     * グリッドカラムを取得します。
     *
     * @return グリッドカラム
     */
    private ACTableColumn[] getColumns () {
        ACTableColumn[] columns = new ACTableColumn[columnsList.length];
        for (int i = 0; i < columnsList.length; i ++ ) {
            columns[i] = getColumn(columnsList[i], i);
        }
        return columns;
    }

    /**
     * グリッドカラムを取得します。
     *
     * @return グリッドカラム
     */
    private ACTableColumn getColumn (String[] columnData, int index) {
        // インスタンス生成
        ACTableColumn column = new ACTableColumn(index);
        // identifier
        column.setIdentifier(columnData[0]);
        // ヘッダー名
        column.setHeaderValue(columnData[1]);
        // カラムサイズ
        column.setColumns(Integer.parseInt(columnData[2]));
        // フォーマットその他
        switch (Integer.parseInt(columnData[3])) {
        case 1:
            // チェック
            column.setCellRenderer(new IkenshoCheckBoxTableCellRenderer());
            column.setCellEditor(deleteCheckEditor);
            column.setEditable(true);
            break;
        case 2:
            // 有効
            column.setHorizontalAlignment(SwingConstants.CENTER);
            column.setFormat(new ACHashMapFormat(new String[]
                    {"jp/nichicom/ac/images/icon/pix16/btn_080.png",
                    "jp/nichicom/ac/images/icon/pix16/btn_079.png" },
                    new Integer[] { new Integer(0), new Integer(1), }));
            column.setRendererType(ACTableCellViewer.RENDERER_TYPE_ICON);
            column.setSortable(false);
            break;
        case 3:
            // 性別
            column.setHorizontalAlignment(SwingConstants.CENTER);
            column.setFormat(IkenshoConstants.FORMAT_SEX);
            break;
        case 4:
            // 年齢
            column.setHorizontalAlignment(SwingConstants.CENTER);
            column.setFormat(IkenshoConstants.FORMAT_NOW_AGE);
            break;
        case 5:
            // 日付
            column.setFormat(IkenshoConstants.FORMAT_ERA_YMD);
            break;
        case 6:
            // 日時
            column.setFormat(IkenshoConstants.FORMAT_ERA_HMS);
            break;
// [ID:0000799][Satoshi Tokusari] 2015/11 add-Start 患者一覧画面の[一覧印刷]ソート対応
        case 7:
            // 非表示
            column.setVisible(false);
            break;
// [ID:0000799][Satoshi Tokusari] 2015/11 add-End
        }

        return column;
    };
// [ID:0000788][Satoshi Tokusari] 2015/11 add-End

	/**
	 * 選択処理を行います。
	 * 
	 * @deprecated doFindメソッドの使用を推奨
	 * @throws Exception
	 *             処理例外
	 */
	private void doSelect() throws Exception {

		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		StringBuffer sb = new StringBuffer();

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
		sb.append(" PATIENT.LAST_TIME");
		sb.append(" FROM");
		sb.append(" PATIENT LEFT OUTER JOIN IKN_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = IKN_ORIGIN_NEW.IKN_ORIGIN_PATIENT_NO");
		sb.append(" LEFT OUTER JOIN SIS_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = SIS_ORIGIN_NEW.SIS_ORIGIN_PATIENT_NO");
		sb.append(" ORDER BY");
		sb.append(" PATIENT_KN ASC");

		data = (VRArrayList) dbm.executeQuery(sb.toString());
		dbm.finalize();

		int end = data.getDataSize();
		for (int i = 0; i < end; i++) {
			VRMap map = ((VRMap) data.getData(i));
			map.setData("DELETE_FLAG", new Boolean(false));
		}

		// パッシブチェック予約
		clearReservedPassive();
		reservedPassive(PASSIVE_CHECK_KEY, data);
		reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, data);
		reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, data);

		if (getTableModelAdapter() != null) {
			getTableModelAdapter().setAdaptee(data);
		}

		checkButtonsEnabled();

		setStatusText(String.valueOf(data.getDataSize()) + "件登録されています。");
	}

	public Component getFirstFocusComponent() {
		return table;
		// return table.getTable();
	}

	/**
	 * ツールボタンの有効状態を設定します。
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
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Replace - begin
		if (isReceiptMode()) {
			delete.setEnabled(false);
		} else {
			delete.setEnabled(enabled);
		}
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Replace - end
		deleteMenu.setEnabled(enabled);

	}

	/**
	 * 削除チェックのついた行番号集合を返します。
	 * 
	 * @throws ParseException
	 *             解析例外
	 * @return ArrayList 行番号集合
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
	 * コンストラクタです。
	 */
	public IkenshoPatientList() {
		super();
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 2007/09/18 [Masahiko.Higuchi] Addition - begin
		// fitAffairButtonSize();
		// 2007/09/18 [Masahiko.Higuchi] Addition - end

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
// [ID:0000799][Satoshi Tokusari] 2015/11 add-Start 患者一覧画面の[一覧印刷]ソート対応
        HashMap column = table.getSortSequence();
        Iterator ite = column.entrySet().iterator();
        String key = "";
        String value = "";
        while (ite.hasNext()) {
            Map.Entry map = (Map.Entry)ite.next();
            key = (String)map.getKey();
            value = (String)map.getValue();
            break;
        }
        String header = "ふりがな";
        if (!key.equals("")) {
            if (key.equals("DELETE_FLAG")) {
                header = "チェック";
            }
            else {
                header = (String)table.getColumn(key).getHeaderValue();
            }
        }
        String sort = "降順";
        if (!value.equals("DESC")) {
            sort = "昇順";
        }
// [ID:0000799][Satoshi Tokusari] 2015/11 add-End
		if (ACMessageBox.showOkCancel("患者情報一覧の印刷", "一覧表を出力してもよろしいですか？"
// [ID:0000799][Satoshi Tokusari] 2015/11 edit-Start 患者一覧画面の[一覧印刷]ソート対応
//				+ IkenshoConstants.LINE_SEPARATOR + "（ふりがな順に印刷されます）", "印刷(O)",
				+ IkenshoConstants.LINE_SEPARATOR + "（" + header + "順（" + sort + "）に印刷されます）", "印刷(O)",
// [ID:0000799][Satoshi Tokusari] 2015/11 edit-End
				'O') != ACMessageBox.RESULT_OK) {
			return;
		}

		if (data.getDataSize() >= 2000) {
			if (ACMessageBox.show("患者情報の大量印刷", "患者情報が2000件以上存在します。"
					+ IkenshoConstants.LINE_SEPARATOR
					+ "全患者を印刷しきれない可能性がありますが、出力してもよろしいですか？",
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
		// pd.addFormat("daicho", path);

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
// [ID:0000799][Satoshi Tokusari] 2015/11 edit-Start 患者一覧画面の[一覧印刷]ソート対応
//				VRMap map = (VRMap) data.getData(i);
//				// 患者ID
//				IkenshoCommon.addString(pd, map, "CHART_NO", "table.h" + index
//						+ ".w1");
//				// 氏名
//				IkenshoCommon.addString(pd, map, "PATIENT_NM", "table.h"
//						+ index + ".w2");
//				// ふりがな
//				IkenshoCommon.addString(pd, map, "PATIENT_KN", "table.h"
//						+ index + ".w3");
//				// 性別
//				Object sex = VRBindPathParser.get("SEX", map);
//				if (sex instanceof Integer) {
//					switch (((Integer) sex).intValue()) {
//					case 1:
//						IkenshoCommon.addString(pd, "table.h" + index + ".w4",
//								"男性");
//						break;
//					case 2:
//						IkenshoCommon.addString(pd, "table.h" + index + ".w4",
//								"女性");
//						break;
//					}
//				}
//				// 年齢
//				IkenshoCommon.addString(pd, "table.h" + index + ".w5",
//						IkenshoConstants.FORMAT_NOW_AGE.format(VRBindPathParser
//								.get("BIRTHDAY", map)));
//				// 生年月日
//				IkenshoCommon.addString(pd, "table.h" + index + ".w6",
//						IkenshoConstants.FORMAT_ERA_YMD.format(VRBindPathParser
//								.get("BIRTHDAY", map)));
//				// 最終診察日
//				// if (IkenshoCommon.getNewDocumentStatus(data, i,
//				// "IKN_ORIGIN_CREATE_DT",
//				// data, i, "SIS_ORIGIN_CREATE_DT") ==
//				// IkenshoCommon.NEW_DOC_IKENSHO) {
//				if (VRBindPathParser.has("IKN_ORIGIN_LASTDAY", map)) {
//					Object sinsatu = VRBindPathParser.get("IKN_ORIGIN_LASTDAY",
//							map);
//					if (sinsatu != null) {
//						IkenshoCommon
//								.addString(pd, "table.h" + index + ".w7",
//										IkenshoConstants.FORMAT_ERA_YMD
//												.format(sinsatu));
//					}
//				}
//				// }
//				// 最新意見書記入日
//				Object ikensho = VRBindPathParser.get("IKN_ORIGIN_KINYU_DT",
//						map);
//				if (ikensho != null) {
//					IkenshoCommon.addString(pd, "table.h" + index + ".w8",
//							IkenshoConstants.FORMAT_ERA_YMD.format(ikensho));
//				}
//				// 最新指示書記入日
//				Object sijisho = VRBindPathParser.get("SIS_ORIGIN_KINYU_DT",
//						map);
//				if (sijisho != null) {
//					IkenshoCommon.addString(pd, "table.h" + index + ".w9",
//							IkenshoConstants.FORMAT_ERA_YMD.format(sijisho));
//				}
//
//				// 2006/07/05
//				// 医師意見書対応
//				// Addition - begin [Masahiko.Higuchi]
//				// 最新医師意見書記入日
//				Object isiIkensho = VRBindPathParser.get(
//						"IKN_ORIGIN_KINYU_DT_ISHI", map);
//				if (isiIkensho != null) {
//					IkenshoCommon.addString(pd, "table.h" + index + ".w10",
//							IkenshoConstants.FORMAT_ERA_YMD.format(isiIkensho));
//				}
//				// Addition - end
//
//				// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin
//				// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
//				Object tokubetsuShijisho = VRBindPathParser.get(
//						"SIS_ORIGIN_KINYU_DT_TOKUBETSU", map);
//				if (tokubetsuShijisho != null) {
//					// ★帳票定義体次第
//					IkenshoCommon.addString(pd, "table.h" + index + ".w11",
//							IkenshoConstants.FORMAT_ERA_YMD
//									.format(tokubetsuShijisho));
//				}
//				// [ID:0000514][Tozo TANAKA] 2009/09/07 add end
//				// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
                // 患者ID
                IkenshoCommon.addString(pd, "table.h" + index + ".w1", table.getValueAt(i, table.getColumn("CHART_NO").getModelIndex()));
                // 氏名
                IkenshoCommon.addString(pd, "table.h" + index + ".w2", table.getValueAt(i, table.getColumn("PATIENT_NM").getModelIndex()));
                // ふりがな
                IkenshoCommon.addString(pd, "table.h" + index + ".w3", table.getValueAt(i, table.getColumn("PATIENT_KN").getModelIndex()));
                // 性別
                String sex = "男性";
                if (String.valueOf(table.getValueAt(i, table.getColumn("SEX").getModelIndex())).equals("2")) {
                    sex = "女性";
                }
                IkenshoCommon.addString(pd, "table.h" + index + ".w4", sex);
                // 年齢
                IkenshoCommon.addString(pd, "table.h" + index + ".w5", 
                        IkenshoConstants.FORMAT_NOW_AGE.format(
                                table.getValueAt(i, table.getColumn("BIRTHDAY").getModelIndex())));
                // 生年月日
                IkenshoCommon.addString(pd, "table.h" + index + ".w6", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("BIRTHDAY").getModelIndex())));
                // 最終診察日　※画面上非表示のため、identifierからインデックス取得不可
                IkenshoCommon.addString(pd, "table.h" + index + ".w7", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(table.getValueAt(i, 15)));
                // 最新意見書記入日
                IkenshoCommon.addString(pd, "table.h" + index + ".w8", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("IKN_ORIGIN_KINYU_DT").getModelIndex())));
                // 最新医師意見書記入日
                IkenshoCommon.addString(pd, "table.h" + index + ".w10", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("IKN_ORIGIN_KINYU_DT_ISHI").getModelIndex())));
                // 最新指示書記入日
                IkenshoCommon.addString(pd, "table.h" + index + ".w9", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("SIS_ORIGIN_KINYU_DT").getModelIndex())));
                // 最新特別指示書記入日
                IkenshoCommon.addString(pd, "table.h" + index + ".w11", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("SIS_ORIGIN_KINYU_DT_TOKUBETSU").getModelIndex())));
// [ID:0000799][Satoshi Tokusari] 2015/11 edit-End
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
                // 最新精神指示書記入日
                IkenshoCommon.addString(pd, "table.h" + index + ".w12", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("SIS_ORIGIN_KINYU_DT_SEISHIN").getModelIndex())));
                // 最新精神特別指示書記入日
                IkenshoCommon.addString(pd, "table.h" + index + ".w13", 
                        IkenshoConstants.FORMAT_ERA_YMD.format(
                                table.getValueAt(i, table.getColumn("SIS_ORIGIN_KINYU_DT_TK_SEISHIN").getModelIndex())));
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
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
	 * 復帰用パラメタマップを生成して返します。
	 * 
	 * @param patientNo
	 *            選択中の患者番号
	 * @return 復帰用パラメタマップ
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

			ACAffairInfo affair = new ACAffairInfo(
					IkenshoPatientInfo.class.getName(), param, "患者最新基本情報");
			ACFrame.getInstance().next(affair);
		} else if ((e.getSource() == ikensho) || (e.getSource() == ikenshoMenu)) {
			int row = table.getSelectedModelRow();
			if (row < 0) {
				return;
			}

			VRMap rowData = (VRMap) data.getData(row);

			// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Add - begin
			if (!VRBindPathParser.has("PATIENT_NO", rowData)) {
				// 日レセデータであれば登録して画面遷移
				int patientID = receiptDataInsert(rowData);
				VRBindPathParser.set("PATIENT_NO", rowData,
						ACCastUtilities.toInteger(patientID));
			}
			// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Add - end

			int patientNo = ((Integer) VRBindPathParser.get("PATIENT_NO",
					rowData)).intValue();

			int flag = IkenshoCommon.checkInsurerDoctorCheck();
			if ((flag & IkenshoCommon.CHECK_INSURER_NOTHING) > 0) {
				if (ACMessageBox.show("保険者が登録されていません。今登録しますか？",
						ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
						ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
					ACFrame.getInstance().next(
							new ACAffairInfo(IkenshoHokenshaShousai.class
									.getName(), createPreviewData(patientNo),
									"保険者詳細"));
					return;
				}
			}
			if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
				if (ACMessageBox.show("医療機関情報が登録されていません。今登録しますか？",
						ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
						ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
					ACFrame.getInstance().next(
							new ACAffairInfo(
									IkenshoIryouKikanJouhouShousai.class
											.getName(),
									createPreviewData(patientNo), "医療機関情報詳細"));
					return;
				}
			}

			VRMap param = new VRHashMap();
			param.putAll(rowData);
			param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);

			int maxEdaNo = 0;

			switch (ACMessageBox.showYesNoCancel("作成する意見書の種類を選択してください。",
					"主治医意見書(S)", 'S', "医師意見書(I)", 'I')) {
			case ACMessageBox.RESULT_YES:

				maxEdaNo = ACCastUtilities.toInt(
						VRBindPathParser.get("IKN_ORIGIN_MAX_EDA", rowData), 0);

				if (checkFDOutPutKubun(patientNo, maxEdaNo)) {
					IkenshoIkenshoInfo.goIkensho(
							IkenshoConstants.IKENSHO_LOW_H18, param);
				}
				break;
			case ACMessageBox.RESULT_NO:

				maxEdaNo = ACCastUtilities.toInt(VRBindPathParser.get(
						"IKN_ORIGIN_MAX_EDA_ISHI", rowData), 0);

				if (checkFDOutPutKubun(patientNo, maxEdaNo)) {
					IkenshoIkenshoInfo.goIkensho(
							IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO, param);
				}
				break;
			default:
				return;
			}

		} else if ((e.getSource() == sijisho) || (e.getSource() == sijishoMenu)) {
			int row = table.getSelectedModelRow();
			if (row < 0) {
				return;
			}

			// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Add - begin
			if (!VRBindPathParser.has("PATIENT_NO", (VRMap) data.getData(row))) {
				// 日レセデータであれば登録して画面遷移
				int patientID = receiptDataInsert((VRMap) data.getData(row));
				VRBindPathParser.set("PATIENT_NO", (VRMap) data.getData(row),
						ACCastUtilities.toInteger(patientID));
			}
			// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Add - end

			int flag = IkenshoCommon.checkInsurerDoctorCheck();
			if ((flag & IkenshoCommon.CHECK_DOCTOR_NOTHING) > 0) {
				if (ACMessageBox.show("医療機関情報が登録されていません。今登録しますか？",
						ACMessageBox.BUTTON_YES | ACMessageBox.BUTTON_NO,
						ACMessageBox.ICON_QUESTION) == ACMessageBox.RESULT_YES) {
					VRMap rowData = (VRMap) data.getData(row);

					int patientNo = ((Integer) VRBindPathParser.get(
							"PATIENT_NO", rowData)).intValue();
					ACFrame.getInstance().next(
							new ACAffairInfo(
									IkenshoIryouKikanJouhouShousai.class
											.getName(),
									createPreviewData(patientNo), "医療機関情報詳細"));
					return;
				}
			}

			VRMap param = new VRHashMap();
			param.putAll((VRMap) data.getData(row));
			param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_INSERT);

			ACAffairInfo affair = null;
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-Start 精神科訪問看護指示書の追加対応
//			switch (ACMessageBox.showYesNoCancel("作成する指示書の種類を選択してください。",
//					"訪問看護指示書(H)", 'H', "特別訪問看護指示書(T)", 'T')) {
//			case ACMessageBox.RESULT_YES:
//				affair = new ACAffairInfo(
//						IkenshoHoumonKangoShijisho.class.getName(), param,
//						"訪問看護指示書");
//				break;
//			case ACMessageBox.RESULT_NO:
//				affair = new ACAffairInfo(
//						IkenshoTokubetsuHoumonKangoShijisho.class.getName(),
//						param, "特別訪問看護指示書");
//				break;
//			default:
//				return;
//			}
            switch (ACMessageBox.showMessage(
                    "作成する指示書の種類を選択してください。",
                    "訪問看護指示書(H) ", 'H',
                    "特別訪問看護指示書(T)", 'T',
                    "精神科訪問看護指示書(I)", 'I',
                    "精神科特別訪問看護指示書(U)", 'U')) {
            case ACMessageBoxDialogPlus.RESULT_SELECT1:
                affair = new ACAffairInfo(
                        IkenshoHoumonKangoShijisho.class.getName(), param, "訪問看護指示書");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT2:
                affair = new ACAffairInfo(
                        IkenshoTokubetsuHoumonKangoShijisho.class.getName(), param, "特別訪問看護指示書");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT3:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaHoumonKangoShijisho.class.getName(), param, "精神科訪問看護指示書");
                break;
            case ACMessageBoxDialogPlus.RESULT_SELECT4:
                affair = new ACAffairInfo(
                        IkenshoSeishinkaTokubetsuHoumonKangoShijisho.class.getName(), param, "精神科特別訪問看護指示書");
                break;
            default:
                return;
            }
// [ID:0000798][Satoshi Tokusari] 2015/11 edit-End
			ACFrame.getInstance().next(affair);

		}
	}

	/**
	 * FD出力区分を判別します。
	 * 
	 * @param patientNo
	 *            利用者番号
	 * @param maxEdaNo
	 *            最大枝番号
	 * @return
	 * @throws Exception
	 */
	protected boolean checkFDOutPutKubun(int patientNo, int maxEdaNo)
			throws Exception {
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
		VRArrayList billArray = (VRArrayList) dbm.executeQuery(sb.toString());
		dbm.finalize();
		if (billArray.getDataSize() > 0) {
			Object obj = VRBindPathParser.get("FD_OUTPUT_KBN",
					(VRMap) billArray.getData());
			if (obj instanceof Integer) {
				if (((Integer) obj).intValue() == 1) {
					ACMessageBox.show("CSV出力対象の意見書があるので新規作成できません。");
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
		// 選択している患者の詳細情報画面に遷移します。
		VRMap param = new VRHashMap();

		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Replace - begin
		VRMap patientRec = (VRMap) data.getData(row);
		if (!VRBindPathParser.has("PATIENT_NO", patientRec)) {
			// 日レセデータであれば登録して画面遷移
			int patientID = receiptDataInsert(patientRec);
			VRBindPathParser.set("PATIENT_NO", patientRec,
					ACCastUtilities.toInteger(patientID));
		}
		param.putAll(patientRec);
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Replace - end
		param.setData("AFFAIR_MODE", IkenshoConstants.AFFAIR_MODE_UPDATE);
		ACAffairInfo affair = new ACAffairInfo(
				IkenshoPatientInfo.class.getName(), param, "患者最新基本情報");
		ACFrame.getInstance().next(affair);
	}

	protected void deleteActionPerformed(ActionEvent e) throws Exception {
		ArrayList rows = getDeleteCheckedRows();
		int rowSize = rows.size();
		if (rowSize <= 0) {
			return;
		}

		// チェックボックスの編集状態の確定
		table.stopCellEditing("DELETE_FLAG");

		if (ACMessageBox.show("チェックされている患者のデータが全て削除されます。\nよろしいですか？",
				ACMessageBox.BUTTON_OKCANCEL, ACMessageBox.ICON_QUESTION,
				ACMessageBox.FOCUS_CANCEL) == ACMessageBox.RESULT_OK) {

			IkenshoFirebirdDBManager dbm = null;
			try {
				// パッシブチェック開始
				clearPassiveTask();

				for (int i = 0; i < rowSize; i++) {
					addPassiveDeleteTask(PASSIVE_CHECK_KEY,
							((Integer) rows.get(i)).intValue());
				}
				// addPassiveDeleteTask(PASSIVE_CHECK_IKENSYO_KEY, row);
				// addPassiveDeleteTask(PASSIVE_CHECK_SIJISYO_KEY, row);

				dbm = getPassiveCheckedDBManager();
				if (dbm == null) {
					ACMessageBox
							.show(IkenshoConstants.PASSIVE_CHECK_ERROR_MESSAGE);
					return;
				}
				// パッシブチェック終了

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
			// 検索処理
			doFind();

			table.setSelectedSortedRowOnAfterDelete(lastSelectedIndex);

		}
	}

	/**
	 * 検索処理を実行します。
	 */
	protected void findActionPerformed(ActionEvent e) throws Exception {
		// 検索の実行
		doFind();

	}

	public boolean canBack(VRMap parameters) throws Exception {
		return true;
	}

	public boolean canClose() {
		return true;
	}

	/**
	 * コンポーネントを初期化します。
	 * 
	 * @throws Exception
	 *             初期化例外
	 */
	private void jbInit() throws Exception {
		buttons.setTitle("患者情報一覧");
		detail.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DETAIL);
		detail.setActionCommand("患者情報(E)");
		detail.setMnemonic('E');
		detail.setText("患者情報(E)");
		detail.setToolTipText("選択された患者の編集画面へ移ります。");
		insert.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_NEW);
		insert.setMnemonic('S');
		insert.setText("患者登録(S)");
		insert.setToolTipText("患者情報を新規に作成します。");
		sijisho.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_SIJISYO);
		sijisho.setActionCommand("指示書作成(J)");
		sijisho.setMnemonic('J');
		sijisho.setText("指示書(J)");
		sijisho.setToolTipText("選択された患者の指示書を新規に作成します。");
		ikensho.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_IKENSYO);
		ikensho.setActionCommand("意見書作成(I)");
		ikensho.setMnemonic('I');
		ikensho.setText("意見書(I)");
		ikensho.setToolTipText("選択された患者の意見書を新規に作成します。");
		delete.setText("患者削除(D)");
		delete.setToolTipText("チェックされた患者の全情報を削除します。");
		delete.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_DELETE);
		delete.setActionCommand("患者登録(S)");
		delete.setMnemonic('D');
		print.setMnemonic('L');
		print.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_TABLE_PRINT);
		print.setText("一覧印刷(L)");
		print.setToolTipText("登録患者一覧表を印刷します。");
		/** @todo menu */
		detailMenu.setToolTipText("選択された患者の編集画面へ移ります。");
		detailMenu.setActionCommand("患者情報(E)");
		detailMenu.setMnemonic('E');
		detailMenu.setText("患者情報(E)");
		table.setPopupMenu(popup);
		table.setPopupMenuEnabled(true);
		
		ikenshoMenu.setText("意見書(I)");
		ikenshoMenu.setToolTipText("選択された患者の意見書を新規に作成します。");
		ikenshoMenu.setMnemonic('I');
		ikenshoMenu.setActionCommand("患者情報(E)");
		sijishoMenu.setText("指示書(J)");
		sijishoMenu.setToolTipText("選択された患者の指示書を新規に作成します。");
		sijishoMenu.setMnemonic('J');
		sijishoMenu.setActionCommand("患者情報(E)");
		deleteMenu.setToolTipText("チェックされた患者の全情報を削除します。");
		deleteMenu.setActionCommand("患者情報(E)");
		deleteMenu.setMnemonic('D');
		deleteMenu.setText("患者削除(D)");
		this.add(buttons, VRLayout.NORTH);

		// 2007/09/18 [Masahiko.Higuchi] Addition - begin
		this.add(getFindPanel(), VRLayout.NORTH);
		this.add(getFindButtonPanel(), VRLayout.NORTH);
		// 2007/09/18 [Masahiko.Higuchi] Addition - end

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
		
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
		getReceiptCheck().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 画面状態変更
					setReceiptState();

				} catch (Exception ex) {
					IkenshoCommon.showExceptionMessage(ex);
				}
			}
		});

		// 日レセ検索設定ボタン
		getReceiptSettingButton().addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					// 日レセ連携設定ダイアログの表示
					IkenshoReceiptApiSetting apiSettingDialog = new IkenshoReceiptApiSetting();
					apiSettingDialog.setVisible(true);
					apiSettingDialog = null;

				} catch (Exception ex) {
				}
			}
		});
		// 画面状態制御
		setReceiptState();
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end

	}

	public ACAffairButtonBar getButtonBar() {
		return buttons;
	}

	/**
	 * 患者一覧テーブルモデルアダプタを返します。
	 * 
	 * @return 患者一覧テーブルモデルアダプタ
	 */
	protected ACTableModelAdapter getTableModelAdapter() {
		return tableModelAdapter;
	}

	/**
	 * 患者一覧テーブルモデルアダプタを設定します。
	 * 
	 * @param tableModelAdapter
	 *            患者一覧テーブルモデルアダプタ
	 */
	protected void setTableModelAdapter(ACTableModelAdapter tableModelAdapter) {
		this.tableModelAdapter = tableModelAdapter;
	}

	/**
	 * 検索領域を取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACPanel getFindPanel() {
		if (findPanel == null) {
			findPanel = new ACPanel();
			findPanel.add(getKanjyaIDContainer(), VRLayout.FLOW_INSETLINE);
			// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
			getReceiptChangePanel().add(getReceiptContainar(), VRLayout.FLOW);
			getReceiptChangePanel().add(getKanjyaContainer(), VRLayout.FLOW);
			getReceiptChangePanel().add(getReceiptSettingButton(),
					VRLayout.FLOW);
			findPanel.add(getReceiptChangePanel(),
					VRLayout.FLOW_INSETLINE_RETURN);
			// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end
			findPanel.add(getFuriganaContainer(), VRLayout.FLOW_INSETLINE);
			getFindPanel().add(getBirthDayContainer(),
					VRLayout.FLOW_INSETLINE_RETURN);
			findPanel.add(getReportBackContainer(), VRLayout.FLOW_INSETLINE);
		}

		return findPanel;
	}

	/**
	 * 「ふりがな」コンテナを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getFuriganaContainer() {
		if (furiganaContainer == null) {
			furiganaContainer = new ACLabelContainer();
			furiganaContainer.setText("ふりがな");
			furiganaContainer.add(getFuriganaText());
		}
		return furiganaContainer;
	}

	/**
	 * 「ふりがな」テキストを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACTextField getFuriganaText() {
		if (furiganaText == null) {
			furiganaText = new ACTextField();
			furiganaText.setColumns(15);
			furiganaText.setMaxLength(30);
			furiganaText.setIMEMode(InputSubset.KANJI);
			furiganaText.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return furiganaText;
	}

	/**
	 * 「現在有効でない患者も含めて検索する」チェックボックスを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACIntegerCheckBox getKanjyaCheck() {
		if (kanjyaCheck == null) {
			kanjyaCheck = new ACIntegerCheckBox();
			kanjyaCheck.setText("現在有効でない患者も含めて検索する(A)");
			kanjyaCheck.setMnemonic('A');
			kanjyaCheck.setBindPath("HIDE_FLAG");
		}
		return kanjyaCheck;
	}

	/**
	 * 患者IDコンテナを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getKanjyaIDContainer() {
		if (kanjyaIDContainer == null) {
			kanjyaIDContainer = new ACLabelContainer();
			kanjyaIDContainer.setText("患者ID");
			kanjyaIDContainer.add(getKanjyaText());
		}
		return kanjyaIDContainer;
	}

	/**
	 * 患者IDテキストを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACTextField getKanjyaText() {
		if (kanjyaText == null) {
			kanjyaText = new ACTextField();
			kanjyaText.setColumns(15);
			kanjyaText.setMaxLength(20);
			kanjyaText.setIMEMode(InputSubset.LATIN);
			kanjyaText.setHorizontalAlignment(SwingConstants.LEFT);
		}
		return kanjyaText;
	}

	/**
	 * 帳票種類コンボを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACComboBox getReportCombo() {
		if (reportCombo == null) {
			reportCombo = new ACComboBox();
			reportCombo.setEditable(false);
			reportCombo.setColumns(8);
			reportCombo.setBlankItem("");
			reportCombo.setBlankable(true);
			// [ID:0000514][Tozo TANAKA] 2009/09/07 replace begin
			// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
			// reportCombo.setModel(new String[]{"主治医意見書","医師意見書","訪問看護指示書"});
			reportCombo.setModel(new String[] { "主治医意見書", "医師意見書", "訪問看護指示書",
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
//					"特別訪問看護指示書" });
			        "特別訪問看護指示書", "精神科訪問看護指示書", "精神科特別訪問看護指示書" });
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
			// [ID:0000514][Tozo TANAKA] 2009/09/07 replace end
			// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
		}
		return reportCombo;
	}

	/**
	 * 帳票種類ラベルコンテナを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getReportContainer() {
		if (reportContainer == null) {
			reportContainer = new ACLabelContainer();
			VRLayout reportLayout = new VRLayout();
			reportLayout.setHgap(0);
			reportContainer.setText("帳票種類");
			reportContainer.setLayout(reportLayout);
			reportContainer.add(getReportCombo(), null);
		}
		return reportContainer;
	}

	/**
	 * 生年月日コンポーネントを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected IkenshoEraDateTextField getBirthDayDateText() {
		if (birthDayDateText == null) {
			birthDayDateText = new IkenshoEraDateTextField();
			birthDayDateText.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
			birthDayDateText.setVisible(true);
		}
		return birthDayDateText;
	}

	/**
	 * 生年月日ラベルコンテナを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getBirthDayContainer() {
		if (birthDayContainer == null) {
			birthDayContainer = new ACLabelContainer();
			birthDayContainer.add(getBirthDayDateText());
			birthDayContainer.setText("生年月日");
		}
		return birthDayContainer;
	}

	/**
	 * 帳票期間検索領域を返します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACBackLabelContainer getReportBackContainer() {
		if (reportBackContainer == null) {
			reportBackContainer = new ACBackLabelContainer();
			reportBackContainer.setAutoWrap(false);
			// 帳票種類
			reportBackContainer.add(getReportContainer(), VRLayout.FLOW);
			// 開始日付
			reportBackContainer
					.add(getReportDateFromContainer(), VRLayout.FLOW);
			// 「～」
			reportBackContainer.add(getReportFromToLabel(), VRLayout.FLOW);
			// 終了日付
			reportBackContainer.add(getReportDateToContainer(), VRLayout.FLOW);
			// クリアボタン
			reportBackContainer.add(getClearButton(), VRLayout.FLOW);
		}
		return reportBackContainer;
	}

	/**
	 * 最新記入日（終了日）を取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected IkenshoEraDateTextField getReportDateTo() {
		if (reportDateTo == null) {
			reportDateTo = new IkenshoEraDateTextField();
			reportDateTo.setAgeVisible(false);
			reportDateTo.setEra("平成");
			reportDateTo.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
			reportDateTo.setAllowedFutureDate(true);
			reportDateTo.setBindPath("FIND_DATE_TO");
		}
		return reportDateTo;
	}

	/**
	 * 最新記入日コンテナを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getReportDateToContainer() {
		if (reportDateToContainer == null) {
			reportDateToContainer = new ACLabelContainer();
			reportDateToContainer.add(getReportDateTo());
		}
		return reportDateToContainer;
	}

	/**
	 * 開始日付を取得します。
	 * 
	 * @return 開始日付
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected IkenshoEraDateTextField getReportDateFrom() {
		if (reportDateFrom == null) {
			reportDateFrom = new IkenshoEraDateTextField();
			reportDateFrom.setAgeVisible(false);
			reportDateFrom.setEra("平成");
			reportDateFrom.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
			reportDateFrom.setAllowedFutureDate(true);
			reportDateFrom.setBindPath("FIND_DATE_FROM");
		}
		return reportDateFrom;
	}

	/**
	 * 開始日付を返します。
	 * 
	 * 2007/09/19 2007年度対応 [Masahiko.Higuchi] Addition
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getReportDateFromContainer() {
		if (reportDateFromContainer == null) {
			reportDateFromContainer = new ACLabelContainer();
			reportDateFromContainer.setText("最新記入日");
			reportDateFromContainer.add(getReportDateFrom());
		}
		return reportDateFromContainer;
	}

	/**
	 * 『～』（期間セパレーター）ラベルを返します。
	 * 
	 * 2007/09/19 2007年度対応 [Masahiko.Higuchi] Addition
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabel getReportFromToLabel() {
		if (reportFromToLabel == null) {
			reportFromToLabel = new ACLabel();
			reportFromToLabel.setText("～");
		}
		return reportFromToLabel;
	}

	/**
	 * 日付消去ボタンを返します。
	 * 
	 * 2007/09/19 2007年度対応 [Masahiko.Higuchi] Addition
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACButton getClearButton() {
		if (clearButton == null) {
			clearButton = new ACButton();
			clearButton.setText("日付消去(C)");
			clearButton.setMnemonic('C');
			clearButton.setToolTipText("入力された日付を消去します。");
		}
		return clearButton;
	}

	/**
	 * 帳票種類検索領域パネルを返します。
	 * 
	 * 2007/09/19 2007年度対応 [Masahiko.Higuchi] Addition
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACPanel getReportPanel() {
		if (reportPanel == null) {
			reportPanel = new ACPanel();

		}
		return reportPanel;
	}

	/**
	 * 患者コンテナを取得します。
	 * 
	 * 2007/09/19 2007年度対応 [Masahiko.Higuchi] Addition
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACLabelContainer getKanjyaContainer() {
		if (kanjyaContainer == null) {
			kanjyaContainer = new ACLabelContainer();
			kanjyaContainer.add(getKanjyaCheck());
		}
		return kanjyaContainer;
	}

	/**
	 * 検索ボタンを取得します。
	 * 
	 * 2007/09/19 2007年度対応 [Masahiko.Higuchi] Addition
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACButton getFindButton() {
		if (findButton == null) {
			VRLayout findButtonLayout = new VRLayout();
			findButtonLayout.setHgap(20);
			findButtonLayout.setVgap(20);
			findButton = new ACButton();
			findButton.setLayout(findButtonLayout);
			findButton.setText("検索(V)");
			findButton.setMnemonic('V');
			findButton.setToolTipText("現在入力されている条件により、一覧を表示します。");

		}
		return findButton;
	}

	/**
	 * 検索条件をクリアボタンを取得します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2008/01/16
	 * @since 3.0.5
	 */
	protected ACButton getInitialFindButton() {
		if (initialFindButton == null) {
			VRLayout initButtonLayout = new VRLayout();
			initButtonLayout.setHgap(20);
			initButtonLayout.setVgap(20);
			initialFindButton = new ACButton();
			initialFindButton.setLayout(initButtonLayout);
			initialFindButton.setText("検索条件をクリア(K)");
			initialFindButton.setMnemonic('K');
			initialFindButton.setToolTipText("検索条件をクリアし、一覧を表示します。");
		}
		return initialFindButton;
	}

	/**
	 * 検索ボタン用のパネルを返します。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected ACPanel getFindButtonPanel() {
		if (findButtonPanel == null) {
			ACLabel spaces = new ACLabel();
			ACLabel buttonSpace = new ACLabel();
			spaces.setText("　");
			buttonSpace.setText(" ");
			findButtonPanel = new ACPanel();
			findButtonPanel.add(spaces, VRLayout.WEST);
			findButtonPanel.add(getFindButton(), VRLayout.WEST);
			findButtonPanel.add(buttonSpace, VRLayout.WEST);
			findButtonPanel.add(getInitialFindButton(), VRLayout.WEST);
		}
		return findButtonPanel;
	}

	/**
	 * 患者情報一覧：有効を取得します。
	 * 
	 * @return 患者情報一覧：有効
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	public ACTableColumn getPatientEnabledColumn() {
		if (patientEnabledColumn == null) {
			patientEnabledColumn = new ACTableColumn(10);
			patientEnabledColumn.setHeaderValue("有効");
			patientEnabledColumn.setColumns(5);
			patientEnabledColumn.setHorizontalAlignment(SwingConstants.CENTER);
			patientEnabledColumn
					.setRendererType(ACTableCellViewer.RENDERER_TYPE_ICON);
			patientEnabledColumn.setSortable(false);
			// テーブルカラムにフォーマッタを設定する。
			patientEnabledColumn.setFormat(new ACHashMapFormat(new String[] {
					"jp/nichicom/ac/images/icon/pix16/btn_080.png",
					"jp/nichicom/ac/images/icon/pix16/btn_079.png" },
					new Integer[] { new Integer(0), new Integer(1), }));

		}
		return patientEnabledColumn;

	}

	/**
	 * 利用者一覧：生年月日を取得します。
	 * 
	 * @return 利用者一覧：生年月日
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	public ACTableColumn getPatientBirthColumn() {
		if (patientBirthColumn == null) {
			patientBirthColumn = new ACTableColumn(11);
			patientBirthColumn.setHeaderValue("生年月日");
			patientBirthColumn.setColumns(18);
			patientBirthColumn.setFormat(new ACDateFormat("ggge年MM月dd日"));
		}
		return patientBirthColumn;

	}

	/**
	 * 検索キーを取得します。
	 * 
	 * @return
	 * @throws Exception
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	protected VRMap getFindKey() throws Exception {
		VRMap keyParam = new VRHashMap();

		String keyValue;
		int keyInteger;
		java.util.Date keyDate;

		// 一覧表示
		keyInteger = 0;
		keyInteger = getKanjyaCheck().getValue();
		if (keyInteger == 0) {
			// チェックなし
			keyParam.setData("HIDE_FLAG", new Boolean(false));

		} else {
			// チェック有
			keyParam.setData("HIDE_FLAG", new Boolean(true));

		}

		// 患者ID
		keyValue = "";
		keyValue = getKanjyaText().getText();
		if (!"".equals(keyValue)) {
			keyParam.setData("CHART_NO", getKanjyaText().getText());
		}

		// ふりがな
		keyValue = "";
		keyValue = getFuriganaText().getText();
		if (!"".equals(keyValue)) {
			keyParam.setData("PATIENT_KN", keyValue);
		}

		// 生年月日
		keyDate = null;
		keyDate = (java.util.Date) getBirthDayDateText().getDate();
		if (keyDate != null) {
			keyParam.setData("BIRTHDAY", keyDate);
		}

		// 帳票種類
		keyInteger = -1;
		keyInteger = getReportCombo().getSelectedIndex();
		switch (keyInteger) {
		case -1: // 未選択
			break;
		case 1: // 主治医意見書
			keyParam.setData("IKN_ORIGIN_KINYU_DT", new Boolean(true));
			break;
		case 2: // 医師意見書
			keyParam.setData("IKN_ORIGIN_KINYU_DT_ISHI", new Boolean(true));
			break;
		case 3: // 訪問看護指示書
			keyParam.setData("SIS_ORIGIN_KINYU_DT", new Boolean(true));
			break;
		// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin
		// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
		case 4: // 特別訪問看護指示書
			keyParam.setData("SIS_ORIGIN_KINYU_DT_TOKUBETSU", new Boolean(true));
			break;
		// [ID:0000514][Tozo TANAKA] 2009/09/07 add end
		// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
        case 5:
            keyParam.setData("SIS_ORIGIN_KINYU_DT_SEISHIN", new Boolean(true));
            break;
        case 6:
            keyParam.setData("SIS_ORIGIN_KINYU_DT_TK_SEISHIN", new Boolean(true));
            break;
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
		}

		// 最新記入日
		keyDate = null;
		keyDate = (java.util.Date) getReportDateFrom().getDate();
		if (keyDate != null) {
			keyParam.setData("KINYU_DT_FROM", keyDate);
		}

		keyDate = null;
		keyDate = (java.util.Date) getReportDateTo().getDate();
		if (keyDate != null) {
			keyParam.setData("KINYU_DT_TO", keyDate);
		}

		return keyParam;

	}

	/**
	 * /** 検索処理を実行します。
	 * 
	 * @throws Exception
	 * @author Masahiko.Higuchi
	 * @version 1.0 2007/10/15
	 */
	public void doFind() throws Exception {

		VRMap param = new VRHashMap();
		StringBuffer sb = new StringBuffer();
		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
		boolean isErrorMsg = false;
		if (isReceiptMode()) {

			// 検索条件をチェックし終了
			if (!checkReceiptFindKey()) {
				return;
			}

			VRMap sqlParam = new VRHashMap();
			String chartNo = getKanjyaText().getText();
			String furigana = getFuriganaText().getText();

			if (!"".equals(chartNo.trim())) {
				sqlParam.setData("CHART_NO", chartNo);
			}
			if (!"".equals(furigana.trim())) {
				sqlParam.setData("PATIENT_KN", furigana);
			}

			// データ取得とフラグ設定
			if (true) {
				IkenshoReceiptApiSetting apiSettingDialog = new IkenshoReceiptApiSetting();

				try {

					IkenshoReceiptSoftGLServerManager rsm = new IkenshoReceiptSoftGLServerManager(
							apiSettingDialog.getReceiptSettingIp(),
							ACCastUtilities.toInt(
									apiSettingDialog.getReceiptSettingApiPort(),
									8000),
							apiSettingDialog.getReceiptSettingApiUser(),
							apiSettingDialog.getReceiptSettingApiPass());

					// 検索方法の分岐
					if (!"".equals(chartNo.trim())) {
						data = (VRArrayList) rsm.getPatientForID(chartNo);
					} else if (!"".equals(furigana.trim())) {
						data = (VRArrayList) rsm.getPatientForName(furigana);
					}

					// 100件超えたら警告
					if (rsm.getTargetPatientCount() > 100) {
						ACMessageBox
								.showExclamation("検索結果が100件を超えています。\n検索条件を見直してください。");
						data = new VRArrayList();
						isErrorMsg = true;
					}
				} catch (Exception e) {
					ACMessageBox.showExclamation("検索に失敗しました。\n通信設定を見直してください。");
					// 日レセ連携設定ダイアログの表示
					apiSettingDialog.setVisible(true);
					apiSettingDialog = null;
					return;
				}

			}

			// 突合
			VRMap saved = getReceiptSavedHash();
			int end = data.getDataSize() - 1;
			for (int l = end; l >= 0; l--) {
				VRMap map = ((VRMap) data.getData(l));
				String key = "";
				key = ACCastUtilities.toString(map.getData("PTID"), "") + "_"
						+ ACCastUtilities.toString(map.getData("HOSPNUM"), "");
				if (VRBindPathParser.has(key, saved)) {
					data.remove(l);
					continue;
				}
				if (IkenshoCommon.hasSameName(map)) {
					data.remove(l);
					continue;
				}
				map.setData("DELETE_FLAG", new Boolean(false));
				map.setData("SHOW_FLAG", new Boolean(false));
			}

			if (getTableModelAdapter() != null) {
				getTableModelAdapter().setAdaptee(data);
			}

			checkButtonsEnabled();

			setStatusText(String.valueOf(data.getDataSize()) + "件登録されています。");

			table.setSelectedSortedFirstRow();

			if (data.getDataSize() <= 0 && !isErrorMsg) {
				ACMessageBox.showExclamation("検索結果が0件でした。\n検索条件を見直してください。");
			}

			return;
		}
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end

		// 検索キーのチェック処理
		if (!checkFindKey()) {
			return;

		}
		// 検索キーの取得
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
		// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin
		// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
		sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_FORMAT_KBN,");
		sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_KINYU_DT_TOKUBETSU,");
		sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_MAX_EDA_TOKUBETSU,");
		// [ID:0000514][Tozo TANAKA] 2009/09/07 add end
		// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_KINYU_DT_SEISHIN,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_MAX_EDA_SEISHIN,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_KINYU_DT_TK_SEISHIN,");
        sb.append(" SIS_ORIGIN_NEW.SIS_ORIGIN_MAX_EDA_TK_SEISHIN,");
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End
		sb.append(" PATIENT.LAST_TIME");
		sb.append(" FROM");
		sb.append(" PATIENT LEFT OUTER JOIN IKN_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = IKN_ORIGIN_NEW.IKN_ORIGIN_PATIENT_NO");
		sb.append(" LEFT OUTER JOIN SIS_ORIGIN_NEW(PATIENT.PATIENT_NO) ON PATIENT.PATIENT_NO = SIS_ORIGIN_NEW.SIS_ORIGIN_PATIENT_NO");

		// WHERE句の作成
		buildFindWhere(sb, param);

		sb.append(" ORDER BY");
		sb.append(" PATIENT_KN ASC");

		data = (VRArrayList) dbm.executeQuery(sb.toString());
		dbm.finalize();

		// ふりがなをSQL検索不可能なためキーが入っている場合は手動で検索する。
		data = doManualFind(data, param, "PATIENT_KN");

		// 患者IDについても手動で検索
		data = doManualFind(data, param, "CHART_NO");

		int end = data.getDataSize();
		for (int k = 0; k < end; k++) {
			VRMap map = ((VRMap) data.getData(k));
			map.setData("DELETE_FLAG", new Boolean(false));
		}
		// パッシブチェック予約
		clearReservedPassive();
		reservedPassive(PASSIVE_CHECK_KEY, data);
		reservedPassive(PASSIVE_CHECK_IKENSHO_KEY, data);
		reservedPassive(PASSIVE_CHECK_SIJISHO_KEY, data);

		if (getTableModelAdapter() != null) {
			getTableModelAdapter().setAdaptee(data);
		}

		checkButtonsEnabled();

		setStatusText(String.valueOf(data.getDataSize()) + "件登録されています。");

		table.setSelectedSortedFirstRow();

	}

	/**
	 * 検索条件のチェックを行います。
	 * 
	 * @return
	 * @author Masahiko.Higuchi
	 * @since 3.1.6
	 * @throws Exception
	 */
	public boolean checkReceiptFindKey() throws Exception {

		String patinetID = getKanjyaText().getText();
		String furigana = getFuriganaText().getText();

		if ("".equals(patinetID.trim()) && "".equals(furigana.trim())) {
			ACMessageBox.showExclamation("検索条件が指定されていません。\n検索条件を１つ以上指定してください。");
			getKanjyaContainer().transferFocus();
			return false;
		}

		return true;
	}

	/**
	 * 検索キーをチェックします。
	 * 
	 * @return True:正常 False:異常有
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 */
	public boolean checkFindKey() {
		// 生年月日が不正な場合
		if (!"".equals(getBirthDayDateText().getEra())) {
			if (getBirthDayDateText().getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
				ACMessageBox.showExclamation("生年月日の入力に誤りがあります。");
				getBirthDayDateText().transferFocus();
				return false;

			}
		}

		// 最新記入日（開始）が入力されている場合
		if (isIkenshoEraDateInput(getReportDateFrom())) {
			// if (!"".equals(getReportDateFrom().getEra())) {
			getReportDateFrom().setRequestedRange(
					IkenshoEraDateTextField.RNG_DAY);
			if (getReportDateFrom().getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
				ACMessageBox.showExclamation("最新記入日の開始日付の入力に誤りがあります。");
				getReportDateFrom().setRequestedRange(
						IkenshoEraDateTextField.RNG_ERA);
				getReportDateFrom().transferFocus();
				return false;
			}
			getReportDateFrom().setRequestedRange(
					IkenshoEraDateTextField.RNG_ERA);
		}

		// 最新記入日（終了）が入力されている場合
		if (isIkenshoEraDateInput(getReportDateTo())) {
			// if (!"".equals(getReportDateTo().getEra())) {
			getReportDateTo()
					.setRequestedRange(IkenshoEraDateTextField.RNG_DAY);
			if (getReportDateTo().getInputStatus() != IkenshoEraDateTextField.STATE_VALID) {
				ACMessageBox.showExclamation("最新記入日の終了日付の入力に誤りがあります。");
				getReportDateTo().setRequestedRange(
						IkenshoEraDateTextField.RNG_ERA);
				getReportDateTo().transferFocus();
				return false;
			}
			getReportDateTo()
					.setRequestedRange(IkenshoEraDateTextField.RNG_ERA);
		}

		// 日付の大小比較
		if (isIkenshoEraDateInput(getReportDateFrom())
				&& isIkenshoEraDateInput(getReportDateTo())) {
			if (ACDateUtilities.compareOnDay(getReportDateFrom().getDate(),
					getReportDateTo().getDate()) > 0) {
				ACMessageBox.showExclamation("最新記入日の開始日付と終了日付が逆転しています。");
				getReportDateFrom().transferFocus();
				return false;

			}
		}

		return true;
	}

	/**
	 * 医見書日付コンポーネントに入力があるかチェックします。
	 * 
	 * @param eraDateText
	 * @return
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 */
	private boolean isIkenshoEraDateInput(IkenshoEraDateTextField eraDateText) {
		return (!"".equals(eraDateText.getEra()) && !"".equals(eraDateText
				.getYear()));

	}

	/**
	 * 検索時のWhere句を構築します。
	 * 
	 * @param sb
	 * @param sqlParam
	 * @throws Exception
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 */
	public StringBuffer buildFindWhere(StringBuffer sb, VRMap sqlParam)
			throws Exception {

		boolean dayFrom = false;
		boolean dayTo = false;

		sb.append(" WHERE");
		// 一覧表示
		if (ACCastUtilities.toBoolean(sqlParam.getData("HIDE_FLAG")) == false) {
			sb.append(" SHOW_FLAG IN(1)");
		} else {
			sb.append(" SHOW_FLAG IN(0,1)");
		}
		// 生年月日
		if (sqlParam.containsKey("BIRTHDAY")) {
			sb.append(" AND");
			sb.append(" BIRTHDAY = ");
			sb.append(getDBSafeDate("BIRTHDAY", sqlParam));
		}
		// 帳票種類
		// 　主治医意見書選択時
		if (sqlParam.containsKey("IKN_ORIGIN_KINYU_DT")) {
			sb.append(" AND");
			sb.append(" IKN_ORIGIN_KINYU_DT IS NOT NULL");
			// 最新記入日（開始）
			if (sqlParam.containsKey("KINYU_DT_FROM")) {
				sb.append(" AND");
				sb.append(" IKN_ORIGIN_KINYU_DT >= ");
				sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
			}
			// 最新記入日（終了）
			if (sqlParam.containsKey("KINYU_DT_TO")) {
				sb.append(" AND");
				sb.append(" IKN_ORIGIN_KINYU_DT <= ");
				sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
			}

		} else {
			// 医師意見書選択時
			if (sqlParam.containsKey("IKN_ORIGIN_KINYU_DT_ISHI")) {
				sb.append(" AND");
				sb.append(" IKN_ORIGIN_KINYU_DT_ISHI IS NOT NULL");
				// 最新記入日（開始）
				if (sqlParam.containsKey("KINYU_DT_FROM")) {
					sb.append(" AND");
					sb.append(" IKN_ORIGIN_KINYU_DT_ISHI >= ");
					sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
				}
				// 最新記入日（終了）
				if (sqlParam.containsKey("KINYU_DT_TO")) {
					sb.append(" AND");
					sb.append(" IKN_ORIGIN_KINYU_DT_ISHI <= ");
					sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
				}

			} else {
				// 訪問看護指示書選択時
				if (sqlParam.containsKey("SIS_ORIGIN_KINYU_DT")) {
					sb.append(" AND");
					sb.append(" SIS_ORIGIN_KINYU_DT IS NOT NULL");
					// 最新記入日（開始）
					if (sqlParam.containsKey("KINYU_DT_FROM")) {
						sb.append(" AND");
						sb.append(" SIS_ORIGIN_KINYU_DT >= ");
						sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
					}
					// 最新記入日（終了）
					if (sqlParam.containsKey("KINYU_DT_TO")) {
						sb.append(" AND");
						sb.append(" SIS_ORIGIN_KINYU_DT <= ");
						sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
					}
					// [ID:0000514][Tozo TANAKA] 2009/09/07 add begin
					// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
				} else if (sqlParam
						.containsKey("SIS_ORIGIN_KINYU_DT_TOKUBETSU")) {
					sb.append(" AND");
					sb.append(" SIS_ORIGIN_KINYU_DT_TOKUBETSU IS NOT NULL");
					// 最新記入日（開始）
					if (sqlParam.containsKey("KINYU_DT_FROM")) {
						sb.append(" AND");
						sb.append(" SIS_ORIGIN_KINYU_DT_TOKUBETSU >= ");
						sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
					}
					// 最新記入日（終了）
					if (sqlParam.containsKey("KINYU_DT_TO")) {
						sb.append(" AND");
						sb.append(" SIS_ORIGIN_KINYU_DT_TOKUBETSU <= ");
						sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
					}

					// [ID:0000514][Tozo TANAKA] 2009/09/07 add end
					// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
// [ID:0000798][Satoshi Tokusari] 2015/11 add-Start 精神科訪問看護指示書の追加対応
                } else if (sqlParam.containsKey("SIS_ORIGIN_KINYU_DT_SEISHIN")) {
                    sb.append(" AND");
                    sb.append(" SIS_ORIGIN_KINYU_DT_SEISHIN IS NOT NULL");
                    // 最新記入日（開始）
                    if (sqlParam.containsKey("KINYU_DT_FROM")) {
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT_SEISHIN >= ");
                        sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
                    }
                    // 最新記入日（終了）
                    if (sqlParam.containsKey("KINYU_DT_TO")) {
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT_SEISHIN <= ");
                        sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
                    }
                } else if (sqlParam.containsKey("SIS_ORIGIN_KINYU_DT_TK_SEISHIN")) {
                    sb.append(" AND");
                    sb.append(" SIS_ORIGIN_KINYU_DT_TK_SEISHIN IS NOT NULL");
                    // 最新記入日（開始）
                    if (sqlParam.containsKey("KINYU_DT_FROM")) {
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT_TK_SEISHIN >= ");
                        sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
                    }
                    // 最新記入日（終了）
                    if (sqlParam.containsKey("KINYU_DT_TO")) {
                        sb.append(" AND");
                        sb.append(" SIS_ORIGIN_KINYU_DT_TK_SEISHIN <= ");
                        sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
                    }
// [ID:0000798][Satoshi Tokusari] 2015/11 add-End

				} else {
					// 何も選択されていない場合（帳票種類）
					// 最新記入日
					if (sqlParam.containsKey("KINYU_DT_FROM")) {
						dayFrom = true;
					}
					if (sqlParam.containsKey("KINYU_DT_TO")) {
						dayTo = true;
					}

					// 双方とも入力がない場合は処理終了
					if (!dayFrom && !dayTo) {
						return sb;
					} else {
						sb.append(" AND(");
					}

					// 主治医意見書の日付条件句作成
					buildDateWhere(sb, sqlParam, dayFrom, dayTo,
							"IKN_ORIGIN_KINYU_DT");

					sb.append(" OR");
					// 医師意見書の日付条件句作成
					buildDateWhere(sb, sqlParam, dayFrom, dayTo,
							"IKN_ORIGIN_KINYU_DT_ISHI");

					sb.append(" OR");
					// 訪問看護指示書の日付条件句作成
					buildDateWhere(sb, sqlParam, dayFrom, dayTo,
							"SIS_ORIGIN_KINYU_DT");

					// [ID:0000514][Tozo TANAKA] 2009/09/15 add begin
					// 【2009年度対応：訪問看護指示書】特別指示書の管理機能
					sb.append(" OR");
					// 特別訪問看護指示書の日付条件句作成
					buildDateWhere(sb, sqlParam, dayFrom, dayTo,
							"SIS_ORIGIN_KINYU_DT_TOKUBETSU");
					// [ID:0000514][Tozo TANAKA] 2009/09/15 add end
					// 【2009年度対応：訪問看護指示書】特別指示書の管理機能

					sb.append(")");
				}
			}
		}

		return sb;
	}

	/**
	 * 主治医意見書の日付のWhere句を作成します。
	 * 
	 * @param sb
	 *            SQL本体
	 * @param sqlParam
	 *            検索キー群
	 * @param isDateFromKey
	 *            開始日付の入力の有無 True:入力有 False:入力無
	 * @param isDateToKey
	 *            終了日付の入力の有無 True:入力有 False:入力無
	 * @param dateKeyName
	 *            Where句のキー名称
	 * @return
	 * @throws Exception
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 */
	private void buildDateWhere(StringBuffer sb, VRMap sqlParam,
			boolean isDateFromKey, boolean isDateToKey, String dateKeyName)
			throws Exception {

		// 最新記入日（開始）
		if (sqlParam.containsKey("KINYU_DT_FROM")) {
			sb.append(" ");
			sb.append(dateKeyName);
			sb.append(" >= ");
			sb.append(getDBSafeDate("KINYU_DT_FROM", sqlParam));
		}

		// 既に入力済み
		if (isDateFromKey && isDateToKey) {
			sb.append(" AND");
		}

		// 最新記入日（終了）
		if (sqlParam.containsKey("KINYU_DT_TO")) {
			sb.append(" ");
			sb.append(dateKeyName);
			sb.append(" <= ");
			sb.append(getDBSafeDate("KINYU_DT_TO", sqlParam));
		}

	}

	/**
	 * 「日付消去」ボタン押下時
	 * 
	 * @param e
	 *            ActionEvent
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 */
	private void doClearDate_actionPerformed(ActionEvent e) {
		getReportDateFrom().clear();
		getReportDateFrom().setEra("平成");
		getReportDateTo().clear();
		getReportDateTo().setEra("平成");
	}

	/**
	 * データ群の中から検索処理を行います。
	 * 
	 * @param data
	 *            検索対象データ群
	 * @param findParam
	 *            検索キーパラメーター群
	 * @param findKey
	 *            検索キー
	 * @return 検索キーの値に一致するデータ群
	 * @throws Exception
	 *             例外
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 */
	private VRArrayList doManualFind(VRArrayList data, VRMap findParam,
			String findKey) throws Exception {
		// 検索値が存在する場合
		if (findParam != null && findParam.containsKey(findKey)) {
			// 検索キー
			String findValue = ACCastUtilities.toString(findParam.get(findKey));
			VRArrayList cloneArray = new VRArrayList();
			cloneArray = (VRArrayList) data.clone();
			data = new VRArrayList();
			for (int j = 0; j < cloneArray.size(); j++) {
				VRMap map = new VRHashMap();
				map = (VRMap) cloneArray.getData(j);
				// 前方一致検索
				if ((ACCastUtilities.toString(map.getData(findKey)))
						.startsWith(findValue)) {
					// 一致した値を結果に格納する。
					data.add(map);
				}
			}

		}

		return data;
	}

	/**
	 * 
	 * IkenshoPatientList_clearDate_actionAdapterです。
	 * <p>
	 * Copyright (c) 2007 Nippon Computer Corpration. All Rights Reserved.
	 * </p>
	 * 
	 * @author Masahiko.Higuchi
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
	 * 検索条件クリアボタンのアクションリスナクラスです
	 * 
	 * @author Masahiko.Higuchi
	 * @version 1.0 2008/01/16
	 * @since 3.0.5
	 * 
	 */
	class IkenshoPatientList_initialFind_actionAdapter implements
			ActionListener {
		private IkenshoPatientList adaptee;

		IkenshoPatientList_initialFind_actionAdapter(IkenshoPatientList adaptee) {
			this.adaptee = adaptee;
		}

		public void actionPerformed(ActionEvent e) {
			try {
				// 検索条件クリア処理
				adaptee.doInitialFind_actionPerformed(e);

			} catch (Exception ex) {
				ex.printStackTrace();
			}

		}

	}

	/**
	 * 検索条件クリア押下時のメイン処理
	 * 
	 * @param e
	 * @author Masahiko.Higuchi
	 * @since 3.0.5
	 * @version 1.0 2008/01/16
	 */
	private void doInitialFind_actionPerformed(ActionEvent e) throws Exception {
		// 全て初期化して検索処理を走らせる
		// 患者ID
		getKanjyaText().setText("");
		// フリガナ
		getFuriganaText().setText("");
		// 生年月日
		getBirthDayDateText().clear();
		// 帳票種類
		getReportCombo().setText("");
		// 帳票期間
		getReportDateFrom().clear();
		getReportDateFrom().setEra("平成");
		getReportDateTo().clear();
		getReportDateTo().setEra("平成");
		// 一覧表示
		getKanjyaCheck().setValue(0);

		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - begin
		// 検索処理
		if (!isReceiptMode()) {
			doFind();
		}
		// 2011/10 [MantisID:0000655] [Masahiko.Higuchi] Addition - end

	}

	/**
	 * 日レセ連携チェックコンテナを取得します。
	 * 
	 * @author Masahiko.Higuchi
	 * @since 3.1.6
	 * @version 1.0 2011/10
	 */
	protected ACLabelContainer getReceiptContainar() {
		if (receiptContainer == null) {
			receiptContainer = new ACLabelContainer();
			receiptContainer.add(getReceiptCheck());
		}

		return receiptContainer;
	}

	/**
	 * 日レセ連携チェックを取得します。
	 * 
	 * @author Masahiko.Higuchi
	 * @since 3.1.6
	 * @version 1.0 2011/10
	 */
	protected ACIntegerCheckBox getReceiptCheck() {
		if (receiptCheck == null) {
			receiptCheck = new ACIntegerCheckBox();
			receiptCheck.setText("日レセから検索する(B)");
			receiptCheck.setMnemonic('B');
			receiptCheck.setBindPath("RECEIPT_MODE");
			receiptCheck.setSelectValue(1);
		}

		return receiptCheck;
	}

	/**
	 * チェック切替用パネル
	 * 
	 * @author Masahiko.Higuchi
	 * @since 3.1.6
	 * @version 1.0 2011/10
	 */
	public ACPanel getReceiptChangePanel() {
		if (receiptChangePanel == null) {
			receiptChangePanel = new ACPanel();
			receiptChangePanel.setAutoWrap(false);
			receiptChangePanel.setHgap(0);
		}
		return receiptChangePanel;
	}

	/**
	 * レセプト連携用画面制御
	 * 
	 * @author Masahiko.Higuchi
	 * @since 3.1.6
	 * @version 1.0 2011/10
	 */
	private void setReceiptState() throws Exception {
		// 画面状態制御
		if (isReceiptMode()) {
			// 日レセから検索
			getKanjyaContainer().setVisible(false);
			getReportBackContainer().setVisible(false);
			getBirthDayContainer().setVisible(false);
			getReceiptSettingButton().setVisible(true);
			if (getTableModelAdapter() != null) {
				data = new VRArrayList();
				getTableModelAdapter().setAdaptee(data);
			}
			setStatusText(String.valueOf(data.getDataSize()) + "件登録されています。");

		} else {
			// 患者情報を検索
			getKanjyaContainer().setVisible(true);
			getReportBackContainer().setVisible(true);
			getBirthDayContainer().setVisible(true);
			getReceiptSettingButton().setVisible(false);
			// クリア⇒検索
			doInitialFind_actionPerformed(null);
		}
	}

	/**
	 * レセプト連携中であるかを判定します。
	 * 
	 * @author Masahiko.Higuchi
	 * @since 3.1.6
	 * @version 1.0 2011/10
	 */
	protected boolean isReceiptMode() {
		if (getReceiptCheck().getValue() == 1) {
			return true;
		}
		return false;
	}

	/**
	 * レセプト連携したデータを患者情報として登録します。
	 * 
	 * @param patientData
	 * @return
	 * @throws Exception
	 */
	public int receiptDataInsert(VRMap patientData) throws Exception {

		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();

		// 誕生日は個別取得
		java.util.Date birthDay = ACCastUtilities.toDate(
				patientData.getData("BIRTHDAY"), null);
		String age = "0";
		if (birthDay != null) {
			age = ACCastUtilities.toString(
					ACAgeEncorder.getInstance().toAge(birthDay), "0");
		}

		StringBuffer sb = new StringBuffer();
		sb.append("INSERT INTO");
		sb.append(" PATIENT");
		sb.append(" (");
		sb.append(" PATIENT_NM");
		sb.append(",PATIENT_KN");
		sb.append(",SEX");
		sb.append(",BIRTHDAY");
		sb.append(",AGE");
		sb.append(",POST_CD");
		sb.append(",ADDRESS");
		sb.append(",TEL1");
		sb.append(",TEL2");
		sb.append(",CHART_NO");
		sb.append(",KOUSIN_DT");
		sb.append(",LAST_TIME");
		sb.append(",SHOW_FLAG");
		sb.append(",PTID");
		sb.append(",HOSPNUM");
		sb.append(" )");
		sb.append(" VALUES");
		sb.append(" (");
		sb.append(getDBSafeString("PATIENT_NM", patientData));
		sb.append(",");
		sb.append(getDBSafeString("PATIENT_KN", patientData));
		sb.append(",");
		sb.append(getDBSafeNumber("SEX", patientData));
		sb.append(",");
		sb.append(getDBSafeDate("BIRTHDAY", patientData));
		sb.append(",");
		sb.append(age);
		sb.append(",");
		sb.append(getDBSafeString("POST_CD", patientData));
		sb.append(",");
		sb.append(getDBSafeString("ADDRESS", patientData));
		sb.append(",");
		sb.append(getDBSafeString("TEL1", patientData));
		sb.append(",");
		sb.append(getDBSafeString("TEL2", patientData));
		sb.append(",");
		sb.append(getDBSafeNumber("PTID", patientData));
		sb.append(",CURRENT_TIMESTAMP");
		sb.append(",CURRENT_TIMESTAMP");
		sb.append(",");
		sb.append(1);
		sb.append(",");
		sb.append(getDBSafeNumber("PTID", patientData));
		sb.append(",");
		sb.append(getDBSafeNumber("HOSPNUM", patientData));
		sb.append(")");

		dbm = new IkenshoFirebirdDBManager();
		dbm.beginTransaction();
		dbm.executeUpdate(sb.toString());

		// 追加された患者番号を取得
		sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" GEN_ID(GEN_PATIENT,0)");
		sb.append(" FROM");
		sb.append(" RDB$DATABASE");
		VRArrayList patientNoArray = (VRArrayList) dbm.executeQuery(sb
				.toString());

		dbm.commitTransaction();
		dbm.finalize();

		String patientId = String.valueOf(VRBindPathParser.get("GEN_ID",
				(VRMap) patientNoArray.getData()));

		return ACCastUtilities.toInt(patientId, 0);
	}

	/**
	 * レセプト連携済みの患者のハッシュを取得します。
	 * 
	 * @return
	 * @throws Exception
	 */
	public VRHashMap getReceiptSavedHash() throws Exception {

		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		StringBuffer sb = new StringBuffer();

		VRHashMap hash = new VRHashMap();

		sb.append("SELECT");
		sb.append(" PTID,");
		sb.append(" HOSPNUM");
		sb.append(" FROM");
		sb.append(" PATIENT");
		sb.append(" WHERE");
		sb.append(" PTID <> 0");
		sb.append(" OR");
		sb.append(" HOSPNUM <> 0");

		VRArrayList result = (VRArrayList) dbm.executeQuery(sb.toString());
		dbm.finalize();

		// 結果がある場合
		if (result.getDataSize() > 0) {
			for (int i = 0; i < result.getDataSize(); i++) {
				VRMap item = (VRMap) result.getData(i);
				String id = ACCastUtilities.toString(item.getData("PTID"));
				String num = ACCastUtilities.toString(item.getData("HOSPNUM"));

				VRBindPathParser.set(id + "_" + num, hash, new Boolean(true));
			}
		}

		return hash;
	}

	/**
	 * 日レセ最新化ボタンを返却します。
	 * 
	 * @return receiptUpdateButton
	 * @since 3.1.6
	 * @author Masahiko.Higuchi
	 */
	public ACButton getReceiptSettingButton() {
		if (receiptSettingButton == null) {
			receiptSettingButton = new ACButton();
			receiptSettingButton.setText("通信設定(T)");
			receiptSettingButton.setMnemonic('T');
			receiptSettingButton.setToolTipText("日医標準レセプトソフトから患者情報を取得します。");
		}
		return receiptSettingButton;
	}

}
