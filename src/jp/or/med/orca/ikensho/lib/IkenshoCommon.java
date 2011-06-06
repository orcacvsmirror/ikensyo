/*
 * Project code name "ORCA"
 * 主治医意見書作成ソフト ITACHI（JMA IKENSYO software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: ITACHI
 * 開発者: 田中統蔵
 * 作成日: 2005/12/01  日本コンピュータ株式会社 田中統蔵 新規作成
 * 更新日: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JRootPane;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.component.ACTextArea;
import jp.nichicom.ac.container.ACGroupBox;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.core.ACPDFCreatable;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.pdf.ACChotarouXMLWriter;
import jp.nichicom.ac.text.ACTextUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACComboBoxModelAdapter;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.adapter.VRHashMapArrayToConstKeyArrayAdapter;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.IkenshoConstants;
import jp.or.med.orca.ikensho.component.IkenshoEraDateTextField;
import jp.or.med.orca.ikensho.component.IkenshoHintButton;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

/** TODO <HEAD_IKENSYO> */
public class IkenshoCommon {

	/** 他科 */
	public static final int DEPARTMENT_DEFAULTT = 0;

	/** その他の他科 */
	public static final int DEPARTMENT_OTHER = 1;

	/** 施設 */
	public static final int INSTITUTION_DEFAULTT = 0;

	/** 特定疾病 */
	public static final int DISEASE_SEPCIAL_SICK_NAME = 0;

	/** 疾病 */
	public static final int TEIKEI_SICK_NAME = 1;

	/** 薬剤 */
	public static final int TEIKEI_MEDICINE_NAME = 2;

	/** 用量単位 */
	public static final int TEIKEI_MEDICINE_DOSAGE_UNIT = 3;

	/** 用法 */
	public static final int TEIKEI_MEDICINE_USAGE = 4;

	/** 精神・神経症状 */
	public static final int TEIKEI_MIND_SICK_NAME = 5;

	/** 四肢欠損 */
	public static final int TEIKEI_BODY_STATUS_HAND_FOOT_NAME = 6;

	/** 麻痺 */
	public static final int TEIKEI_BODY_STATUS_PARALYSIS_NAME = 7;

	/** 筋力の低下 */
	public static final int TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME = 8;

	/** 褥瘡 */
	public static final int TEIKEI_BODY_STATUS_JYOKUSOU_NAME = 9;

	/** 皮膚疾患 */
	public static final int TEIKEI_BODY_STATUS_SKIN_NAME = 10;

	/** 失禁 */
	public static final int TEIKEI_SICK_COPE_URINE_NAME = 11;

	/** 転倒・骨折 */
	public static final int TEIKEI_SICK_COPE_FACTURE_NAME = 12;

	/** 徘徊 */
	public static final int TEIKEI_SICK_COPE_PROWL_NAME = 13;

	/** 褥瘡 */
	public static final int TEIKEI_SICK_COPE_JYOKUSOU_NAME = 14;

	/** 嚥下性肺炎 */
	public static final int TEIKEI_SICK_COPE_PNEUMONIA_NAME = 15;

	/** 腸閉塞 */
	public static final int TEIKEI_SICK_COPE_INTESTINES_NAME = 16;

	/** 易感染性 */
	public static final int TEIKEI_SICK_COPE_INFECTION_NAME = 17;

	/** 心肺機能の低下 */
	public static final int TEIKEI_SICK_COPE_HEART_LUNG_NAME = 18;

	/** 痛み */
	public static final int TEIKEI_SICK_COPE_PAIN_NAME = 19;

	/** 脱水 */
	public static final int TEIKEI_SICK_COPE_DEHYDRATION_NAME = 20;

	/** その他 */
	public static final int TEIKEI_SICK_COPE_OTHER_NAME = 21;

	/** その他 */
	public static final int TEIKEI_SICK_TYPE_OTHER_NAME = 22;

	/** 血圧 */
	public static final int TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME = 23;

	/** 嚥下 */
	public static final int TEIKEI_CARE_SERVICE_ENGE_NAME = 24;

	/** 摂食 */
	public static final int TEIKEI_CARE_SERVICE_EAT_NAME = 25;

	/** 移動 */
	public static final int TEIKEI_CARE_SERVICE_MOVE_NAME = 26;

	/** その他 */
	public static final int TEIKEI_CARE_SERVICE_OTHER_NAME = 27;

	/** 感染症 */
	public static final int TEIKEI_INFECTION_NAME = 28;

	/** レスピレータ */
	public static final int TEIKEI_RESPIRATOR_TYPE = 29;

	/** レスピレータ */
	public static final int TEIKEI_RESPIRATOR_SETTING = 30;

	/** 経管 */
	public static final int TEIKEI_TUBE_TYPE = 31;

	/** 経管-サイズ */
	public static final int TEIKEI_TUBE_SIZE = 32;

	/** 経管-交換 */
	public static final int TEIKEI_TUBE_CHANGE_SPAN = 33;

	/** カニューレ */
	public static final int TEIKEI_CANURE_SIZE = 34;

	/** ドレーン */
	public static final int TEIKEI_DOREN_POS_NAME = 35;

	/** カテーテル */
	public static final int TEIKEI_CATHETER_SIZE = 36;

	/** カテーテル */
	public static final int TEIKEI_CATHETER_CHANGE_SPAN = 37;

	/** 特記事項 */
	public static final int TEIKEI_MENTION_NAME = 38;

	/** 療養生活指導上の留意事項 */
	public static final int TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU = 39;

	/** リハビリテーション */
	public static final int TEIKEI_HOUMON_REHABILITATION = 40;

	/** 褥瘡の処置等 */
	public static final int TEIKEI_HOUMON_JYOKUSOU = 41;

	/** 装着・使用医療機器等の操作援助・管理 */
	public static final int TEIKEI_HOUMON_KIKI_SOUSA_ENJYO = 42;

	/** その他 */
	public static final int TEIKEI_HOUMON_SONOTA = 43;

	/** 特記すべき留意事項 */
	public static final int TEIKEI_HOUMON_TOKKI = 44;

	/** 在宅訪問点滴注射 */
	public static final int TEIKEI_HOUMON_TENTEKI_CHUSHA = 45;

	// 以降、H18法改正追加定型文
	/** 症状として不安定における具体的状況 */
	public static final int TEIKEI_INSECURE_CONDITION_NAME = 46;

	/** 心身の状態-麻痺(その他) */
	public static final int TEIKEI_MAHI_POSITION_OTHER_NAME = 47;
	/** 心身の状態-関節の拘縮 */
	public static final int TEIKEI_CENNECT_KOSHUKU_NAME = 48;
	/** 心身の状態-関節の痛み */
	public static final int TEIKEI_CONNECT_PAIN_NAME = 49;
	// /** 栄養・食生活-一日の食事摂取量 */
	// public static final int TEIKEI_MEAL_INTAKE_NAME = 49;
	// /** 栄養・食生活-食欲 */
	// public static final int TEIKEI_APPETITE_NAME = 50;
	/** 栄養・食生活-栄養･食生活上の留意点 */
	public static final int TEIKEI_EATING_RYUIJIKOU_NAME = 50;
	/** 病態-移動能力の低下 */
	public static final int TEIKEI_MOVILITY_DOWN_NAME = 51;
	/** 病態-閉じこもり */
	public static final int TEIKEI_TOJIKOMORI_NAME = 52;
	/** 病態-意欲低下 */
	public static final int TEIKEI_IYOKU_DOWN_NAME = 53;
	/** 病態-低栄養 */
	public static final int TEIKEI_LOW_ENERGY_NAME = 54;
	/** 病態-摂食・嚥下機能低下 */
	public static final int TEIKEI_SESSHOKU_ENGE_DOWN_NAME = 55;
	/** 病態-がん等による疼痛 */
	public static final int TEIKEI_GAN_TOTSU_NAME = 56;
	/** 医学的観点からの留意事項-運動 */
	public static final int TEIKEI_CARE_SERVICE_UNDOU_NAME = 57;
	// /** 介護の必要性の程度に関する予後の見通し-改善への寄与が期待できるサービス */
	// public static final int TEIKEI_OUTLOOK_SERVISE_NAME = 59;

    // 以降、医師意見書追加定型文
    /** 医師意見書-疾病 */
    public static final int TEIKEI_ISHI_SICK_NAME = 60;

    /** 医師意見書-薬剤 */
    public static final int TEIKEI_ISHI_MEDICINE_NAME = 61;

    /** 医師意見書-用量単位 */
    public static final int TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT = 62;

    /** 医師意見書-用法 */
    public static final int TEIKEI_ISHI_MEDICINE_USAGE = 63;

    /** 医師意見書-症状として不安定における具体的状況 */
    public static final int TEIKEI_ISHI_INSECURE_CONDITION_NAME = TEIKEI_INSECURE_CONDITION_NAME;

    /** 医師意見書-精神・神経症状 */
    public static final int TEIKEI_ISHI_MIND_SICK_NAME = TEIKEI_MIND_SICK_NAME;

    /** 医師意見書-失禁 */
    public static final int TEIKEI_ISHI_SICK_COPE_URINE_NAME = TEIKEI_SICK_COPE_URINE_NAME;

    /** 医師意見書-転倒・骨折 */
    public static final int TEIKEI_ISHI_SICK_COPE_FACTURE_NAME = TEIKEI_SICK_COPE_FACTURE_NAME;

    /** 医師意見書-徘徊 */
    public static final int TEIKEI_ISHI_SICK_COPE_PROWL_NAME = TEIKEI_SICK_COPE_PROWL_NAME;

    /** 医師意見書-褥瘡 */
    public static final int TEIKEI_ISHI_SICK_COPE_JYOKUSOU_NAME = TEIKEI_SICK_COPE_JYOKUSOU_NAME;

    /** 医師意見書-嚥下性肺炎 */
    public static final int TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME = 64;

    /** 医師意見書-腸閉塞 */
    public static final int TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME = 65;

    /** 医師意見書-易感染性 */
    public static final int TEIKEI_ISHI_SICK_COPE_INFECTION_NAME = TEIKEI_SICK_COPE_INFECTION_NAME;

    /** 医師意見書-心肺機能の低下 */
    public static final int TEIKEI_ISHI_SICK_COPE_HEART_LUNG_NAME = TEIKEI_SICK_COPE_HEART_LUNG_NAME;

    /** 医師意見書-痛み */
    public static final int TEIKEI_ISHI_SICK_COPE_PAIN_NAME = 66;

    /** 医師意見書-脱水 */
    public static final int TEIKEI_ISHI_SICK_COPE_DEHYDRATION_NAME = TEIKEI_SICK_COPE_DEHYDRATION_NAME;

    /** 医師意見書-その他 */
    public static final int TEIKEI_ISHI_SICK_COPE_OTHER_NAME = TEIKEI_SICK_COPE_OTHER_NAME;

    /** 医師意見書-その他 */
    public static final int TEIKEI_ISHI_SICK_TYPE_OTHER_NAME = TEIKEI_SICK_TYPE_OTHER_NAME;

    /** 医師意見書-血圧 */
    public static final int TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME = TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME;

    /** 医師意見書-嚥下 */
    public static final int TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME = TEIKEI_CARE_SERVICE_ENGE_NAME;

    /** 医師意見書-摂食 */
    public static final int TEIKEI_ISHI_CARE_SERVICE_EAT_NAME = TEIKEI_CARE_SERVICE_EAT_NAME;

    /** 医師意見書-移動 */
    public static final int TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME = TEIKEI_CARE_SERVICE_MOVE_NAME;

    /** 医師意見書-その他 */
    public static final int TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME = TEIKEI_CARE_SERVICE_OTHER_NAME;

    /** 医師意見書-感染症 */
    public static final int TEIKEI_ISHI_INFECTION_NAME = TEIKEI_INFECTION_NAME;

    /** 医師意見書-特記事項 */
    public static final int TEIKEI_ISHI_MENTION_NAME = 67;
    
    // [ID:0000518][Tozo TANAKA] 2009/09/04 add begin 【2009年度対応：特記事項一覧】特定疾病項目の編集を可能とする 
    /** 特定疾病 */
    public static final int TEIKEI_SPECIFIED_DISEASE_NAME = 68;
    // [ID:0000518][Tozo TANAKA] 2009/09/04 add end 【2009年度対応：特記事項一覧】特定疾病項目の編集を可能とする 

	/**
	 * 最新文書該当なし
	 */
	public static final int NEW_DOC_NONE = 0;

	/**
	 * 最新文書は意見書
	 */
	public static final int NEW_DOC_IKENSHO = 1;

	/**
	 * 保険者・医療機関ともに1件以上存在
	 */
	public static final int CHECK_OK = 0;
	/**
	 * 保険者が0件
	 */
	public static final int CHECK_INSURER_NOTHING = 1;
	/**
	 * 医療機関が0件
	 */
	public static final int CHECK_DOCTOR_NOTHING = 2;

	/**
	 * コンストラクタです。<br />
	 * Singleton Pattern
	 */
	protected IkenshoCommon() {
	}

	/**
	 * SQLで取得したレコードの任意の列の値ComboBoxに設定します。
	 * 
	 * @param combo
	 *            VRComboBox
	 * @param sql
	 *            String
	 * @param field
	 *            String
	 */
	public static void setComboModel(JComboBox combo, String sql, String field) {
		try {
			IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
			VRArrayList tbl = (VRArrayList) dbm.executeQuery(sql);
			if (tbl.size() > 0) {
				applyComboModel(combo, new VRHashMapArrayToConstKeyArrayAdapter(tbl, field));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * SQLで取得したレコードの任意の列の値ComboBoxに設定します。
	 * 
	 * @param combo
	 *            対象のコンボ
	 * @param field
	 *            対象のフィールド名
	 * @param table
	 *            対象のテーブル名
	 * @param codeField
	 *            WHERE区に用いるフィールド名
	 * @param code
	 *            WHERE区に用いるフィールド値
	 * @param orderField
	 *            ソート基準となるフィールド名
	 * @throws Exception
	 *             処理例外
	 */
	public static void setComboModel(JComboBox combo, String field, String table, String codeField, int code, String orderField) throws Exception {
		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		setComboModel(dbm, combo, field, table, codeField, code, orderField);
		dbm.finalize();
	}

	/**
	 * SQLで取得したレコードの任意の列の値をVRHashMapArrayToConstKeyArrayAdapterとして返します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param field
	 *            対象のフィールド名
	 * @param table
	 *            対象のテーブル名
	 * @param codeField
	 *            WHERE区に用いるフィールド名
	 * @param code
	 *            WHERE区に用いるフィールド値
	 * @param orderField
	 *            ソート基準となるフィールド名
	 * @return VRHashMapArrayToConstKeyArrayAdapter
	 * @throws SQLException
	 *             SQL例外
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter geArrayAdapter(IkenshoFirebirdDBManager dbm, String field, String table, String codeField, int code, String orderField) throws SQLException {
		return getFieldRows(dbm, field, table, codeField, code, orderField);
	}

	/**
	 * SQLで取得したレコードの任意の列の値ComboBoxに設定します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            対象のコンボ
	 * @param field
	 *            対象のフィールド名
	 * @param table
	 *            対象のテーブル名
	 * @param codeField
	 *            WHERE区に用いるフィールド名
	 * @param code
	 *            WHERE区に用いるフィールド値
	 * @param orderField
	 *            ソート基準となるフィールド名
	 * @throws SQLException
	 *             SQL例外
	 */
	public static void setComboModel(IkenshoFirebirdDBManager dbm, JComboBox combo, String field, String table, String codeField, int code, String orderField) throws SQLException {
		VRHashMapArrayToConstKeyArrayAdapter adapter = geArrayAdapter(dbm, field, table, codeField, code, orderField);
		if ((adapter != null) && (adapter.getDataSize() > 0)) {
			applyComboModel(combo, adapter);
		}
	}

	/**
	 * SQLで取得したヒントボタン用定型文を与えられたヒントボタンに設定します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param codes
	 *            定型文コード配列
	 * @param formatKubun
	 *            法改正対応区分
	 * @param buttons
	 *            ヒントボタン配列
	 * @throws SQLException
	 *             SQL例外
	 * @throws ParseException
	 *             解析例外
	 */
	public static void setHintButtons(IkenshoFirebirdDBManager dbm, String[] codes, int formatKubun, IkenshoHintButton[] buttons) throws SQLException, ParseException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" TEIKEIBUN");
		sb.append(" FROM");
		sb.append(" M_HELP_TEIKEIBUN");
		sb.append(" WHERE");
		sb.append(" (FORMAT_KBN = ");
		sb.append(formatKubun);
		sb.append(" )");
		sb.append("AND(TKB_CD = ");
		String head = sb.toString();

		int end = codes.length;
		for (int i = 0; i < end; i++) {
			sb = new StringBuffer();
			sb.append(head);
			sb.append(codes[i]);
			sb.append(" )");
			VRArrayList array = (VRArrayList) dbm.executeQuery(sb.toString());
			if (array.getDataSize() > 0) {
                String[] sep = ACTextUtilities.separateLineWrapLimit(String.valueOf(VRBindPathParser.get("TEIKEIBUN", (VRMap) array.getData())),2);
				// String[] sep = String.valueOf(VRBindPathParser.get("TEIKEIBUN", (VRMap) array.getData())).split(IkenshoConstants.LINE_SEPARATOR, 2);
				if (sep.length < 2) {
					buttons[i].setHintTitle("");
                    buttons[i].setHintText(ACTextUtilities.toBlankIfNull(sep[0]));
				} else {
					buttons[i].setHintTitle(sep[0]);
                    buttons[i].setHintText(ACTextUtilities.toBlankIfNull(sep[1]));
				}
			}
		}
	}

	/**
	 * SQLで取得したレコードの任意の列の値集合を返します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param field
	 *            対象のフィールド名
	 * @param table
	 *            対象のテーブル名
	 * @param codeField
	 *            WHERE区に用いるフィールド名
	 * @param code
	 *            WHERE区に用いるフィールド値
	 * @param orderField
	 *            ソート基準となるフィールド名
	 * @throws SQLException
	 *             SQL例外
	 * @return 任意の列の値集合
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter getFieldRows(IkenshoFirebirdDBManager dbm, String field, String table, String codeField, int code, String orderField) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append(field);
		sb.append(" FROM ");
		sb.append(table);
		sb.append(" WHERE ");
		sb.append(codeField);
		sb.append("=");
		sb.append(code);
		if ((orderField != null) && (!"".equals(orderField))) {
			sb.append(" ORDER BY ");
			sb.append(orderField);
		}

		return new VRHashMapArrayToConstKeyArrayAdapter((VRArrayList) dbm.executeQuery(sb.toString()), field);
	}

	/**
	 * M_INSTITUTION(施設マスタ)のINSTITUTION_NM(施設名)をアイテムとして設定します。
	 * 
	 * @param combo
	 *            対象のコンボ
	 * @param institutionKbn
	 *            施設区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setMInstitution(JComboBox combo, int institutionKbn) throws Exception {
		// String sql =
		// "SELECT INSTITUTION_NM FROM M_INSTITUTION WHERE INSTITUTION_KBN=" +
		// institutionKbn;
		// setComboModel(combo, sql, "INSTITUTION_NM");
		setComboModel(combo, "INSTITUTION_NM", "M_INSTITUTION", "INSTITUTION_KBN", institutionKbn, "");
	}

	/**
	 * M_INSTITUTION(施設マスタ)のINSTITUTION_NM(施設名)をアイテムとして設定します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            対象のコンボ
	 * @param institutionKbn
	 *            施設区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setMInstitution(IkenshoFirebirdDBManager dbm, JComboBox combo, int institutionKbn) throws Exception {
		setComboModel(dbm, combo, "INSTITUTION_NM", "M_INSTITUTION", "INSTITUTION_KBN", institutionKbn, "");
	}

	/**
	 * M_DISEASE(疾患名マスタ)のDISEASE_NM(疾患名)をアイテムとして設定します。
	 * 
	 * @param combo
	 *            対象のコンボ
	 * @param diseaseKbn
	 *            疾患名区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setMDisease(JComboBox combo, int diseaseKbn) throws Exception {
		// String sql = "SELECT DISEASE_NM FROM M_DISEASE WHERE DISEASE_KBN=" +
		// diseaseKbn +
		// " ORDER BY DISEASE_CD ASC";
		setComboModel(combo, "DISEASE_NM", "M_DISEASE", "DISEASE_KBN", diseaseKbn, "DISEASE_CD ASC");
	}

	/**
	 * M_DISEASE(疾患名マスタ)のDISEASE_NM(疾患名)をアイテムとして設定します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            対象のコンボ
	 * @param diseaseKbn
	 *            疾患名区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setMDisease(IkenshoFirebirdDBManager dbm, JComboBox combo, int diseaseKbn) throws Exception {
		setComboModel(dbm, combo, "DISEASE_NM", "M_DISEASE", "DISEASE_KBN", diseaseKbn, "DISEASE_CD ASC");
	}

	/**
	 * M_DISEASE(疾患名マスタ)のDISEASE_NM(疾患名)をアダプタ形式で返します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param diseaseKbn
	 *            疾患名区分
	 * @return アダプタ
	 * @throws Exception
	 *             処理例外
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter getMDisease(IkenshoFirebirdDBManager dbm, int diseaseKbn) throws Exception {
		return geArrayAdapter(dbm, "DISEASE_NM", "M_DISEASE", "DISEASE_KBN", diseaseKbn, "DISEASE_CD ASC");
	}

	/**
	 * コンボにアダプタをラップしたコンボモデルを生成して設定します。
	 * 
	 * @param combo
	 *            設定先コンボ
	 * @param adapter
	 *            データアダプタ
	 */
	public static void applyComboModel(JComboBox combo, VRHashMapArrayToConstKeyArrayAdapter adapter) {
		combo.setModel(createComboAdapter(adapter));
	}

	/**
	 * コンボにアダプタをラップしたコンボモデルを生成して設定します。
	 * 
	 * @param combo
	 *            設定先コンボ
	 * @param adapter
	 *            データアダプタ
	 */
	public static void applyComboModel(JComboBox combo, VRArrayList adapter) {
		combo.setModel(new ACComboBoxModelAdapter(adapter));
	}

	/**
	 * アダプタをラップしたコンボモデルを生成して返します。
	 * 
	 * @param adapter
	 *            データアダプタ
	 * @return コンボモデル
	 */
	public static ACComboBoxModelAdapter createComboAdapter(VRHashMapArrayToConstKeyArrayAdapter adapter) {
		return new ACComboBoxModelAdapter(adapter);
	}

	/**
	 * M_SINRYOUKA(診療科マスタ)のSINRYOUKA_NM(診療科名)をアイテムとして設定します。
	 * 
	 * @param combo
	 *            対象のコンボ
	 * @param sinryoukaKbn
	 *            診療科区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setMSinryouka(JComboBox combo, int sinryoukaKbn) throws Exception {
		// String sql = "SELECT SINRYOUKA_NM FROM M_SINRYOUKA WHERE
		// SINRYOUKA_KBN=" +
		// sinryoukaKbn +
		// " ORDER BY SINRYOUKA_CD ASC";
		setComboModel(combo, "SINRYOUKA_NM", "M_SINRYOUKA", "SINRYOUKA_KBN", sinryoukaKbn, "SINRYOUKA_CD ASC");
	}

	/**
	 * M_SINRYOUKA(診療科マスタ)のSINRYOUKA_NM(診療科名)をアイテムとして設定します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            対象のコンボ
	 * @param sinryoukaKbn
	 *            診療科区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setMSinryouka(IkenshoFirebirdDBManager dbm, JComboBox combo, int sinryoukaKbn) throws Exception {
		setComboModel(dbm, combo, "SINRYOUKA_NM", "M_SINRYOUKA", "SINRYOUKA_KBN", sinryoukaKbn, "SINRYOUKA_CD ASC");
	}

	/**
	 * TKB_KBN(定型文テーブル)のTEIKEIBUN(定型文)をアイテムとして設定します。
	 * 
	 * @param combo
	 *            対象のコンボ
	 * @param tkbKbn
	 *            定型文区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setTeikeibun(JComboBox combo, int tkbKbn) throws Exception {
		// String sql = "SELECT TEIKEIBUN FROM TEIKEIBUN WHERE TKB_KBN=" +
		// tkbKbn +
		// " ORDER BY TKB_CD";
		setComboModel(combo, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", tkbKbn, "TKB_CD");
	}

	/**
	 * TKB_KBN(定型文テーブル)のTEIKEIBUN(定型文)をアイテムとして設定します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            対象のコンボ
	 * @param tkbKbn
	 *            定型文区分
	 * @throws Exception
	 *             処理例外
	 */
	public static void setTeikeibun(IkenshoFirebirdDBManager dbm, JComboBox combo, int tkbKbn) throws Exception {
		setComboModel(dbm, combo, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", tkbKbn, "TKB_CD");
	}

	/**
	 * TKB_KBN(定型文テーブル)のTEIKEIBUN(定型文)をアダプタ形式で返します。
	 * 
	 * @param dbm
	 *            DMManager
	 * @param tkbKbn
	 *            定型文区分
	 * @return アダプタ
	 * @throws Exception
	 *             処理例外
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter getTeikeibun(IkenshoFirebirdDBManager dbm, int tkbKbn) throws Exception {
		return geArrayAdapter(dbm, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", tkbKbn, "TKB_CD");
	}

	/**
	 * 最新文書は指示書
	 */
	public static final int NEW_DOC_SIJISHO = 2;

	/**
	 * 意見書データと指示書データを比較し、最新の文書が何であるかを返します。
	 * 
	 * @param ikenshoData
	 *            意見書データ
	 * @param ikenshoIndex
	 *            意見書データ行番号
	 * @param ikenshoCreateDateField
	 *            意見書の作成日フィールド名
	 * @param sijishoData
	 *            指示書データ
	 * @param sijishoIndex
	 *            指示書データ行番号
	 * @param sijishoCreateDateField
	 *            指示書の作成日フィールド名
	 * @throws ParseException
	 *             解析例外
	 * @return int 比較結果
	 */
	public static int getNewDocumentStatus(VRArrayList ikenshoData, int ikenshoIndex, String ikenshoCreateDateField, VRArrayList sijishoData, int sijishoIndex, String sijishoCreateDateField) throws ParseException {
		int useDoc = NEW_DOC_NONE;

		Date ikenshoDate = null;
		if (ikenshoData.getDataSize() > ikenshoIndex) {
			// 意見書が存在
			Object ikenObj = VRBindPathParser.get(ikenshoCreateDateField, (VRBindSource) ikenshoData.getData(ikenshoIndex));
			if (ikenObj instanceof Date) {
				ikenshoDate = (Date) ikenObj;
			}
		}

		Date sijishoDate = null;
		if (sijishoData.getDataSize() > sijishoIndex) {
			// 指示書が存在
			Object sijiObj = VRBindPathParser.get(sijishoCreateDateField, (VRBindSource) sijishoData.getData(sijishoIndex));
			if (sijiObj instanceof Date) {
				sijishoDate = (Date) sijiObj;
			}
		}

		if (ikenshoDate != null) {
			// 意見書が存在
			if (sijishoDate != null) {
				// 指示書も存在
				Calendar ikenCal = Calendar.getInstance();
				Calendar sijiCal = Calendar.getInstance();
				ikenCal.setTime(ikenshoDate);
				sijiCal.setTime(sijishoDate);

				if (ikenCal.before(sijiCal)) {
					// 指示書が最新
					useDoc = NEW_DOC_SIJISHO;
				} else {
					// 意見書が最新
					useDoc = NEW_DOC_IKENSHO;
				}
			} else {
				// 意見書が最新
				useDoc = NEW_DOC_IKENSHO;
			}
		} else {
			// 意見書はない
			if (sijishoDate != null) {
				// 指示書が最新
				useDoc = NEW_DOC_SIJISHO;
			}
		}
		return useDoc;
	}

	/**
	 * 同姓同名の患者が存在するかを返します。
	 * 
	 * @param map
	 *            パラメタマップ
	 * @throws Exception
	 *             処理例外
	 * @return 同姓同名の患者が存在するか
	 */
	public static boolean hasSameName(VRMap map) throws Exception {
		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" PATIENT.PATIENT_NO");
		sb.append(" FROM");
		sb.append(" PATIENT");
		sb.append(" WHERE");
		sb.append(" (PATIENT.PATIENT_NM='");
		sb.append(String.valueOf(VRBindPathParser.get("PATIENT_NM", map)).replaceAll("'", "''"));
		sb.append("')");
		sb.append("AND(PATIENT.BIRTHDAY = '");
		sb.append(IkenshoConstants.FORMAT_YMD.format(VRBindPathParser.get("BIRTHDAY", map)));
		sb.append("')");

		VRArrayList result = (VRArrayList) dbm.executeQuery(sb.toString());
		dbm.finalize();
		return result.size() > 0;
	}

	/**
	 * 指定した日付コントロールが適正値入力済みならばフォーマット結果を、それ以外は空文字を返します。
	 * 
	 * @param date
	 *            日付コントロール
	 * @param format
	 *            フォーマット
	 * @return String 判定結果
	 */
	public static String getBrankOrFormatedDate(IkenshoEraDateTextField date, Format format) {
		if (date.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
			return format.format(date.getDate());
		}
		return "";

	}

	/**
	 * 文字列がNullまたは空文字であるかを返します。
	 * 
	 * @param obj
	 *            評価文字列
	 * @return 文字列がNullまたは空文字であるか
	 */
	public static boolean isNullText(Object obj) {
		if (obj == null) {
			return true;
		}
		String text = String.valueOf(obj);
		if (text == null) {
			return true;
		}
		final char HALF_SPACE = ' ';
		final char FULL_SPACE = '　';
		int i;
		int end = text.length();
		for (i = 0; i < end; i++) {
			char c = text.charAt(i);
			if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
				break;
			}
		}
		if (i >= end) {
			return true;
		}
		return false;
	}

	/**
	 * 例外メッセージを表示します。
	 * 
	 * @param ex
	 *            例外
	 */
	public static void showExceptionMessage(Throwable ex) {
		// 2006/12/03[Tozo Tanaka] : replace begin
		// StringWriter sw = new StringWriter();
		// ex.printStackTrace(new PrintWriter(sw));
		// ACMessageBox.show("重大なエラーが発生しました。\nサポートセンターに電話してください。");
		// VRLogger.warning(sw.toString());

		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String msg = sw.toString();
		try {
			sw.close();
		} catch (Exception ex2) {
		}
		VRLogger.warning(msg);

		final ACPanel content = new ACPanel();
		final ACPanel buttons = new ACPanel();
		final ACGroupBox detailInfomation = new ACGroupBox("サポート情報");
		final ACTextArea area = new ACTextArea();
		final ACLabel environment = new ACLabel();
		ACPanel buttonArea = new ACPanel();
		ACButton copy = new ACButton("コピー(C)");
		copy.setMnemonic('C');
		ACButton detail = new ACButton("詳細(D)");
		detail.setMnemonic('D');
		area.setVisible(false);

		StringBuffer sb = new StringBuffer();
		sb.append(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
		sb.append(ACConstants.LINE_SEPARATOR);

		File captureFile = null;
		try {
			captureFile = new File(VRLogger.getPath() + ACConstants.FILE_SEPARATOR + new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + ".spt");
			FileOutputStream fos = new FileOutputStream(captureFile);
			JPEGImageEncoder enc = JPEGCodec.createJPEGEncoder(fos);
			if (enc != null) {
				enc.encode(new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize())));
			}
			fos.close();
		} catch (Exception ex2) {
			captureFile = null;
		}
		if (captureFile != null) {
			sb.append("Capture File: ");
			sb.append(captureFile.getAbsolutePath());
			sb.append(ACConstants.LINE_SEPARATOR);
		}

		sb.append(msg);
		area.setText(sb.toString());
		area.setEditable(false);
		sb = new StringBuffer();
		sb.append("OS:");
		sb.append(System.getProperty("os.name", "unknown"));
		String patch = System.getProperty("sun.os.patch.level", "");
		if ("unknown".equals(patch)) {
			// パッチがunknownなら何も表示しない
			patch = "";
		}
		sb.append(" " + patch);
		sb.append("(");
		sb.append(System.getProperty("os.version", "-"));
		sb.append(")");
		sb.append(", VM:");
		sb.append(System.getProperty("java.vendor", "unknown"));
		sb.append("(");
		sb.append(System.getProperty("java.version", "-"));
		sb.append(")");

		// 2006/12/09[Tozo Tanaka] : add begin
		sb.append(ACConstants.LINE_SEPARATOR);
		sb.append("DB:");
		try {
			String dbVersion = new IkenshoFirebirdDBManager().getDBMSVersion();
			if (dbVersion != null) {
				sb.append(dbVersion);
			}
		} catch (Exception ex2) {
			sb.append("unknown");
		}
		sb.append(", System:");
		try {
			sb.append(ACFrame.getInstance().getProperty("Version/no"));
		} catch (Exception ex2) {
			sb.append("unknown");
		}
		environment.setAutoWrap(true);

		copy.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
				if (cb != null) {
					StringSelection ss = new StringSelection(environment.getText() + ACConstants.LINE_SEPARATOR + area.getText());
					cb.setContents(ss, ss);
					ACMessageBox.show("クリップボードにサポート情報をコピーしました。");
				} else {
					ACMessageBox.show("クリップボードへのアクセスに失敗しました。");
				}
			}
		});

		// 2006/12/09[Tozo Tanaka] : add end

		// エラーメッセージ置換 begin
		// 2006/02/27[Kazumi Hirose (Kazumix) add]
		sb.append(ACConstants.LINE_SEPARATOR);
		String errLine = ex.toString();

		if (errLine.indexOf("Firebird 1.0") >= 0) {
			errLine = "旧バージョンのFirebirdが検出されました、他社の製品と競合している可能性があります。";
        } else if (errLine.indexOf("FileNotFoundException") >= 0) {
            if(errLine.toLowerCase().indexOf(".pdf") >= 0) {
                errLine = "【印刷用のファイルを作成できません。】ファイルの出力に失敗しました。医見書は管理者権限でインストール、実行する必要があります。";
            }else{
                errLine = "【ファイルにアクセスできません。】ファイルアクセスに失敗しました。医見書は管理者権限でインストール、実行する必要があります。";
            }
		} else if (errLine.indexOf("ParseException: ロケール定義が存在しません。") >= 0) {
			errLine = "【calendar.xmlが存在しない】インストール先にcalendar.xmlが存在していません。再インストールしてください。";

		} else if (errLine.indexOf("ArrayIndexOutOfBoundsException: Acrobat/Path at") >= 0) {
			errLine = "【PDFの設定に問題があります。】PDFの設定でAcrobat5.0以降のPDF閲覧ソフトが設定されているか確認してください。";

		} else if (errLine.indexOf("ACProperityXML.getValueAt") >= 0) {
			errLine = "【設定ファイルに問題があります。】PDFの設定もしくはデータベースの設定を確認してください。" +ACConstants.LINE_SEPARATOR+"解消されない場合、再インストールしてください。";

		} else if (errLine.indexOf("ParseException: Acrobat/Path") >= 0) {
			errLine = "【PDF設定読み込みに失敗しました。】インストール先に日本語名フォルダが含まれていないか確認してください。";

		} else if (errLine.indexOf("ParseException: DBConfig/Path") >= 0) {
			errLine = "【DB設定読み込みに失敗しました。】インストール先に日本語名フォルダが含まれていないか確認してください。";

		} else if (errLine.indexOf("SAXParseException: 文字変換エラー:") >= 0) {
			errLine = "【XMLの解析に失敗しました。】インストール先に日本語名フォルダが含まれていないか確認してください。";

		} else if (errLine.indexOf("SAXParseException: Invalid byte 1 of 1-byte UTF-8 sequence.") >= 0) {
			errLine = "【XMLの解析に失敗しました。】インストール先に日本語名フォルダが含まれていないか確認してください。";

		} else if (errLine.indexOf("SAXParseException: 不正な XML 文字:  &#xb") >= 0) {
			errLine = "【垂直タブが含まれています】最新のデータ移行ツールで再度データ移行を行ってください。";

		} else if (errLine.indexOf("error for column PATIENT_NO") >= 0) {
			errLine = "【データ移行に失敗しています。】最新のデータ移行ツールで再度データ移行を行ってください。";

		}

		sb.append(errLine);
		// エラーメッセージ置換 end

		environment.setText(sb.toString());

		if (buttonArea.getLayout() instanceof VRLayout) {
			((VRLayout) buttonArea.getLayout()).setAlignment(VRLayout.RIGHT);
		}
		buttonArea.add(detail, VRLayout.FLOW_RETURN);
		buttonArea.add(copy, VRLayout.FLOW_RETURN);
		buttons.add(buttonArea, VRLayout.EAST);
		buttons.add(environment, VRLayout.CLIENT);
		content.add(detailInfomation, VRLayout.CLIENT);
		detailInfomation.add(buttons, VRLayout.NORTH);
		detailInfomation.add(area, VRLayout.CLIENT);
		detail.addActionListener(new ActionListener() {
			private Dimension minimumSize;

			public void actionPerformed(ActionEvent e) {
				JRootPane pnl = content.getRootPane();
				if ((pnl != null) && (pnl.getParent() != null)) {
					if (minimumSize == null) {
						minimumSize = pnl.getParent().getSize();
					}
					// 変更前と後のサイズを取得
					Dimension oldD = content.getPreferredSize();
					area.setVisible(!area.isVisible());
					Dimension newD = content.getPreferredSize();

					// 画面領域を取得
					Point corner = new Point(0, 0);
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					GraphicsEnvironment genv = GraphicsEnvironment.getLocalGraphicsEnvironment();
					if (genv != null) {
						Rectangle screenRect = genv.getMaximumWindowBounds();
						if (screenRect != null) {
							corner = screenRect.getLocation();
							screenSize = screenRect.getSize();
						}
					}
					// 要求されるサイズを計算
					int newH = (int) Math.max(minimumSize.getHeight(), pnl.getParent().getHeight() + newD.getHeight() - oldD.getHeight());
					Point pos = pnl.getParent().getLocationOnScreen();
					int newY = (int) pos.getY();

					if (newH > screenSize.getHeight()) {
						// 要求する高さが限界の場合
						newY = (int) corner.getY();
						newH = (int) screenSize.getHeight() - 20;
					} else {
						if (newY + newH > corner.getY() + screenSize.getHeight()) {
							// 下端を超える場合は上へずらす
							newY = (int) (screenSize.getHeight() - newH);
						} else if (newY < corner.getY()) {
							// 上端にめり込んでりる場合は下へずらす
							newY = (int) corner.getY() + 20;
						}
					}
					pnl.getParent().setBounds((int) pos.getX(), newY, pnl.getParent().getWidth(), newH);
					// 再描画
					pnl.getParent().validate();
				} else {
					detailInfomation.setVisible(!detailInfomation.isVisible());
				}

			}
		});

		if (environment.getPreferredSize().getWidth() > 600) {
			environment.setPreferredSize(new Dimension(600, (int) environment.getPreferredSize().getHeight()));
		}

		// VRLogger.warning(msg);
		ACMessageBox.show("処理を続行できません。", content, true);
		// 2006/12/03[Tozo Tanaka] : replace end

	}

	/**
	 * ソース内の指定キーの値をDBへ格納可能な数値文字列として返します。
	 * 
	 * @param key
	 *            取得キー
	 * @param source
	 *            ソース
	 * @throws ParseException
	 *             解析例外
	 * @return 変換結果
	 */
	public static String getDBSafeNumber(String key, VRBindSource source) throws ParseException {
		Object obj = VRBindPathParser.get(key, source);
		if (obj == null) {
			return "NULL";
		}
		return String.valueOf(obj);
	}

	/**
	 * ソース内の指定キーの値をDBへ格納可能な文字列として返します。
	 * 
	 * @param key
	 *            取得キー
	 * @param source
	 *            ソース
	 * @throws ParseException
	 *             解析例外
	 * @return 変換結果
	 */
	public static String getDBSafeString(String key, VRBindSource source) throws ParseException {
		return IkenshoConstants.FORMAT_PASSIVE_STRING.format(VRBindPathParser.get(key, source));
	}

	/**
	 * ソース内の指定キーの値をDBへ格納可能な日付文字列として返します。
	 * 
	 * @param key
	 *            取得キー
	 * @param source
	 *            ソース
	 * @throws ParseException
	 *             解析例外
	 * @return 変換結果
	 */
	public static String getDBSafeDate(String key, VRBindSource source) throws ParseException {
		return IkenshoConstants.FORMAT_PASSIVE_YMD.format(VRBindPathParser.get(key, source));
	}

	/**
	 * ソース内の指定キーの値をDBへ格納可能な日時文字列として返します。
	 * 
	 * @param key
	 *            取得キー
	 * @param source
	 *            ソース
	 * @throws ParseException
	 *             解析例外
	 * @return 変換結果
	 */
	public static String getDBSafeTime(String key, VRBindSource source) throws ParseException {
		return IkenshoConstants.FORMAT_PASSIVE_YMD_HMS.format(VRBindPathParser.get(key, source));
	}

	/**
	 * 意見書請求書が作成されているかを返します。
	 * 
	 * @param source
	 *            検証用ソース
	 * @throws ParseException
	 *             処理例外
	 * @return 意見書請求書が作成されているか
	 */
	public static boolean isBillSeted(VRMap source) throws ParseException {
		if (VRBindPathParser.has("SHOSIN_TAISHOU", source)) {
            // 2009/01/09[Tozo Tanaka] : replace begin
//			final String[] checks = new String[]{"SHOSIN_TAISHOU", "XRAY_TANJUN_SATUEI", "XRAY_SHASIN_SINDAN", "XRAY_FILM", "BLD_SAISHU", "BLD_IPPAN_MASHOU_KETUEKI", "BLD_IPPAN_EKIKAGAKUTEKIKENSA", "BLD_KAGAKU_KETUEKIKAGAKUKENSA", "BLD_KAGAKU_SEIKAGAKUTEKIKENSA", "NYO_KENSA"};
            final String[] checks = new String[]{"SHOSIN_TAISHOU", "XRAY_TANJUN_SATUEI", "XRAY_SHASIN_SINDAN", "XRAY_FILM", "XRAY_DIGITAL_MANAGEMENT", "XRAY_DIGITAL_FILM", "XRAY_DIGITAL_IMAGING", "BLD_SAISHU", "BLD_IPPAN_MASHOU_KETUEKI", "BLD_IPPAN_EKIKAGAKUTEKIKENSA", "BLD_KAGAKU_KETUEKIKAGAKUKENSA", "BLD_KAGAKU_SEIKAGAKUTEKIKENSA", "NYO_KENSA"};
            // 2009/01/09[Tozo Tanaka] : replace end
			final int end = checks.length;
			for (int i = 0; i < end; i++) {
				Integer pattern = (Integer) VRBindPathParser.get(checks[i], source);
				if ((pattern != null) && (pattern.intValue() > 0)) {
					return true;
				}
			}

		}
		return false;
	}

	/**
	 * 指定データでPDFを固定パスに出力します。
	 * 
	 * @param data
	 *            元データ
	 * @throws Exception
	 *             処理例外
	 * @return 出力ファイルパス
	 */
	public static String writePDF(ACChotarouXMLWriter data) throws Exception {
		ACFrameEventProcesser processer = ACFrame.getInstance().getFrameEventProcesser();
		if (processer instanceof ACPDFCreatable) {
			return ((ACPDFCreatable) processer).writePDF(data);
		}
		return "";

		// File pdfDirectory = new File(IkenshoConstants.PRINT_PDF_DIRECTORY);
		// if (!pdfDirectory.exists()) {
		// // ディレクトリ作成
		// if (!pdfDirectory.mkdir()) {
		// ACMessageBox.showExclamation("印刷に失敗しました。");
		// return "";
		// }
		// }
		//
		// Calendar cal = Calendar.getInstance();
		// VRDateFormat vrdf = new VRDateFormat("yyyyMMddHHmmss");
		// String pdfFilePath = IkenshoConstants.PRINT_PDF_DIRECTORY
		// + vrdf.format(cal.getTime()) + ".pdf";
		// IkenshoConstants.PDF_WRITER.write(data.getDataXml(), pdfFilePath);
		// return pdfFilePath;
	}

	/**
	 * 出力された固定パスのPDFファイルを開きます。
	 * 
	 * @throws Exception
	 *             処理例外
	 */
	public static void openPDF() throws Exception {
		openPDF(IkenshoConstants.PRINT_PDF_PATH);
	}

	/**
	 * PDFファイルを生成し、生成したPDFファイルを開きます。
	 * 
	 * @param pd
	 *            印刷データ
	 * @throws Exception
	 */
	public static void openPDF(ACChotarouXMLWriter pd) throws Exception {
		// PDFファイル生成
		String pdfPath = writePDF(pd);

		// 生成したPDFを開く
		if (pdfPath.length() > 0) {
			openPDF(pdfPath);
		}
	}

	/**
	 * 出力された任意パスのPDFファイルを開きます。
	 * 
	 * @param pdfPath
	 *            PDFファイルのパス
	 * @throws Exception
	 */
	public static void openPDF(String pdfPath) throws Exception {
		// 2006/03/02[Tozo Tanaka] : remove begin
		// String acrobatPath =
		// ACFrame.getInstance().getProperity("Acrobat/Path");
		// 2006/03/02[Tozo Tanaka] : remove end

		String[] arg = {"", pdfPath};
		String osName = System.getProperty("os.name");
		if (osName.startsWith("Mac")) {
			// Macならば関連付けで開く
			arg[0] = "open";
		} else {
			// 2006/03/02[Tozo Tanaka] : add begin
			String acrobatPath = "";
			if (ACFrame.getInstance().hasProperty("Acrobat/Path")) {
				acrobatPath = ACFrame.getInstance().getProperty("Acrobat/Path");
			}
			// 2006/03/02[Tozo Tanaka] : add end
			arg[0] = acrobatPath;
			if (!new File(acrobatPath).exists()) {
				// アクロバットが存在しない
				ACMessageBox.showExclamation("PDFの設定が正しくありません。" + ACConstants.LINE_SEPARATOR + "[メインメニュー]-[設定(S)]-[PDF設定(P)]よりAcrobatの設定を行ってください。");
				return;
			}
		}
		Runtime.getRuntime().exec(arg);
	}

	/**
	 * プロパティファイルから値を取得します。
	 * 
	 * @param path
	 *            キー
	 * @throws Exception
	 *             処理例外
	 * @return キーに対応する値
	 */
	public static String getProperity(String path) throws Exception {
		return ACFrame.getInstance().getProperty(path);
	}

	/**
	 * 2桁区切りの元号年月日をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param head
	 *            グリッドの接頭句
	 * @param wPos
	 *            グリッドのw方向インデックス
	 * @param step
	 *            増分
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addEraDate(ACChotarouXMLWriter pd, VRMap data, String key, String head, int wPos, int step) throws Exception {
		return addEraDate(pd, data, key, head, wPos, step, "日");
	}

	/**
	 * 2桁区切りの元号年月日をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param head
	 *            グリッドの接頭句
	 * @param wPos
	 *            グリッドのw方向インデックス
	 * @param step
	 *            増分
	 * @param dateText
	 *            日付単位文字列
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addEraDate(ACChotarouXMLWriter pd, VRMap data, String key, String head, int wPos, int step, String dateText) throws Exception {
		// 記入日
		Object obj = VRBindPathParser.get(key, data);
		if (obj instanceof Date) {
			Date date = (Date) obj;
			addString(pd, head + wPos, VRDateParser.format(date, "ggg"));
			// 年
			addString(pd, head + (wPos + step), VRDateParser.format(date, "ee"));
			addString(pd, head + (wPos + step * 2), "年");
			// 月
			addString(pd, head + (wPos + step * 3), VRDateParser.format(date, "MM"));
			addString(pd, head + (wPos + step * 4), "月");
			// 日
			addString(pd, head + (wPos + step * 5), VRDateParser.format(date, "dd"));
			addString(pd, head + (wPos + step * 6), dateText);
			return true;
		}
		return false;
	}

	/**
	 * 電話番号をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key1
	 *            データキー1
	 * @param key2
	 *            データキー1
	 * @param target
	 *            グリッド名
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addTel(ACChotarouXMLWriter pd, VRMap data, String key1, String key2, String target) throws Exception {
		String tel1 = String.valueOf(VRBindPathParser.get(key1, data));
		String tel2 = String.valueOf(VRBindPathParser.get(key2, data));
		StringBuffer sb = new StringBuffer();
		if (!IkenshoCommon.isNullText(tel1)) {
			sb.append(tel1);
			sb.append("-");
		}
		if (!IkenshoCommon.isNullText(tel2)) {
			sb.append(tel2);
		}
		if (sb.length() == 0) {
			return false;
		}
		addString(pd, target, sb.toString());

		return true;
	}

	/**
	 * 電話番号を地番は()で分割してXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key1
	 *            データキー1
	 * @param key2
	 *            データキー1
	 * @param target1
	 *            グリッド位置1
	 * @param target2
	 *            グリッド位置2
	 * @param target3
	 *            グリッド位置3
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addTel(ACChotarouXMLWriter pd, VRMap data, String key1, String key2, String target1, String target2, String target3) throws Exception {
		String tel1 = String.valueOf(VRBindPathParser.get(key1, data));
		String tel2 = String.valueOf(VRBindPathParser.get(key2, data));
		boolean printed = false;
		if (!IkenshoCommon.isNullText(tel1)) {
			addString(pd, target1, tel1);
			printed = true;
		}
		if (!IkenshoCommon.isNullText(tel2)) {
			String[] tels = tel2.split("-", 2);
			if (tels.length >= 2) {
				addString(pd, target2, tels[0]);
				addString(pd, target3, tels[1]);
				printed = true;
			}
		}

		return printed;
	}

	/**
	 * 郵便番号を-で分割してXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param target1
	 *            グリッド位置1
	 * @param target2
	 *            グリッド位置2
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addZip(ACChotarouXMLWriter pd, VRMap data, String key, String target1, String target2) throws Exception {
		Object obj = VRBindPathParser.get(key, data);
		if (isNullText(obj)) {
			return false;
		}
		String[] zips = String.valueOf(obj).split("-");
		switch (zips.length) {
			case 0 :
				return false;
			case 1 :
				addString(pd, target1, zips[0]);
				break;
			default :
				addString(pd, target1, zips[0]);
				addString(pd, target2, zips[1]);
				break;
		}
		return true;
	}

	/**
	 * 選択型項目をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param shaps
	 *            Visible制御対象群
	 * @param offset
	 *            値と配列添え字とのオフセット
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addSelection(ACChotarouXMLWriter pd, VRMap data, String key, String[] shaps, int offset) throws Exception {
		Object obj = VRBindPathParser.get(key, data);
		if (!(obj instanceof Integer)) {
			return false;
		}

		int end = shaps.length;
		int pos = ((Integer) obj).intValue() + offset;
		if ((pos < 0) || (pos >= end)) {
			// すべてfalse
			for (int i = 0; i < end; i++) {
				pd.addAttribute(shaps[i], "Visible", "FALSE");
			}
		} else {
			for (int i = 0; i < pos; i++) {
				pd.addAttribute(shaps[i], "Visible", "FALSE");
			}
			for (int i = pos + 1; i < end; i++) {
				pd.addAttribute(shaps[i], "Visible", "FALSE");
			}
		}
		return true;
	}

	/**
	 * 選択型項目をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param shaps
	 *            Visible制御対象群
	 * @param offset
	 *            値と配列添え字とのオフセット
	 * @param optionKey
	 *            連動して出力する文字列キー
	 * @param optionTarget
	 *            連動して出力する文字列出力位置
	 * @param useOptionIndex
	 *            連動して出力する文字列の出力条件となる選択番号
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addSelection(ACChotarouXMLWriter pd, VRMap data, String key, String[] shaps, int offset, String optionKey, String optionTarget, int useOptionIndex) throws Exception {
		Object obj = VRBindPathParser.get(key, data);
		if (!(obj instanceof Integer)) {
			return false;
		}

		int end = shaps.length;
		int index = ((Integer) obj).intValue();
		int pos = index + offset;
		if ((pos < 0) || (pos >= end)) {
			// すべてfalse
			for (int i = 0; i < end; i++) {
				pd.addAttribute(shaps[i], "Visible", "FALSE");
			}
		} else {
			for (int i = 0; i < pos; i++) {
				pd.addAttribute(shaps[i], "Visible", "FALSE");
			}
			for (int i = pos + 1; i < end; i++) {
				pd.addAttribute(shaps[i], "Visible", "FALSE");
			}
		}

		if (index == useOptionIndex) {
			addString(pd, data, optionKey, optionTarget);
		}

		return true;
	}

	/**
	 * 文字項目をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param target
	 *            タグ名
	 * @param value
	 *            出力値
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addString(ACChotarouXMLWriter pd, String target, Object value) throws Exception {
		return ACCommon.getInstance().setValue(pd, target, value);
	}

	/**
	 * 文字項目をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param target
	 *            タグ名
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addString(ACChotarouXMLWriter pd, VRMap data, String key, String target) throws Exception {
		return ACCommon.getInstance().setValue(pd, data, key, target);
	}

	/**
	 * 文字項目をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param target
	 *            タグ名
	 * @param head
	 *            文字項目値の前に連結して出力する文字列
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addString(ACChotarouXMLWriter pd, VRMap data, String key, String target, String head) throws Exception {
		return ACCommon.getInstance().setValue(pd, data, key, target, head);
	}

	/**
	 * チェック項目をXML出力します。
	 * 
	 * @param pd
	 *            出力クラス
	 * @param data
	 *            データソース
	 * @param key
	 *            データキー
	 * @param target
	 *            タグ名
	 * @param checkValue
	 *            チェックとみなす値
	 * @throws Exception
	 *             処理例外
	 * @return 出力したか
	 */
	public static boolean addCheck(ACChotarouXMLWriter pd, VRMap data, String key, String target, int checkValue) throws Exception {
		Object obj = VRBindPathParser.get(key, data);
		if (obj instanceof Integer) {
			if (((Integer) obj).intValue() != checkValue) {
				pd.addAttribute(target, "Visible", "FALSE");
				return false;
			}
		}
		return true;
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followTextKeys
	 *            文字列型チェック連動テキスト項目キー集合
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, boolean addCheckValue) throws ParseException {
		addFollowCheckTextUpdate(sb, map, checkNumberKey, followTextKeys, 1, addCheckValue);
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followTextKeys
	 *            文字列型チェック連動テキスト項目キー集合
	 * @param selectedIndex
	 *            選択扱いとみなすチェック項目値
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, int selectedIndex, boolean addCheckValue) throws ParseException {

		Object obj = VRBindPathParser.get(checkNumberKey, map);
		if (addCheckValue) {
			sb.append(",");
			sb.append(checkNumberKey);
			sb.append(" = ");
			sb.append(getDBSafeNumber(checkNumberKey, map));
		}

		int end = followTextKeys.length;
		if ((obj instanceof Integer) && (((Integer) obj).intValue() == selectedIndex)) {
			for (int i = 0; i < end; i++) {
				sb.append(",");
				sb.append(followTextKeys[i]);
				sb.append(" = ");
				sb.append(getDBSafeString(followTextKeys[i], map));
			}
		} else {
			for (int i = 0; i < end; i++) {
				sb.append(",");
				sb.append(followTextKeys[i]);
				sb.append(" = ");
				sb.append("''");
			}
		}
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followTextKeys
	 *            文字列型チェック連動テキスト項目キー集合
	 * @param followNumberKeys
	 *            数値型チェック連動テキスト項目キー集合
	 * @param textToNumber
	 *            テキスト出力が先か
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, String[] followNumberKeys, boolean textToNumber, boolean addCheckValue) throws ParseException {

		Object obj = VRBindPathParser.get(checkNumberKey, map);
		if (addCheckValue) {
			sb.append(",");
			sb.append(checkNumberKey);
			sb.append(" = ");
			sb.append(getDBSafeNumber(checkNumberKey, map));
		}

		int end;
		if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
			if (textToNumber) {
				// 文字出力が先
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followTextKeys[i]);
					sb.append(" = ");
					sb.append(getDBSafeString(followTextKeys[i], map));
				}
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followNumberKeys[i]);
					sb.append(" = ");
					sb.append(getDBSafeNumber(followNumberKeys[i], map));
				}
			} else {
				// 数値出力が先
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followNumberKeys[i]);
					sb.append(" = ");
					sb.append(getDBSafeNumber(followNumberKeys[i], map));
				}
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followTextKeys[i]);
					sb.append(" = ");
					sb.append(getDBSafeString(followTextKeys[i], map));
				}
			}
		} else {
			if (textToNumber) {
				// 文字出力が先
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followTextKeys[i]);
					sb.append(" = ");
					sb.append("''");
				}
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followNumberKeys[i]);
					sb.append(" = 0");
				}
			} else {
				// 数値出力が先
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followNumberKeys[i]);
					sb.append(" = 0");
				}
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(followTextKeys[i]);
					sb.append(" = ");
					sb.append("''");
				}
			}
		}
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followNumberKeys
	 *            数値型チェック連動テキスト項目キー集合
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckNumberUpdate(StringBuffer sb, VRMap map, String checkNumberKey, String[] followNumberKeys, boolean addCheckValue) throws ParseException {

		Object obj = VRBindPathParser.get(checkNumberKey, map);
		if (addCheckValue) {
			sb.append(",");
			sb.append(checkNumberKey);
			sb.append(" = ");
			sb.append(getDBSafeNumber(checkNumberKey, map));
		}

		int end = followNumberKeys.length;
		if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
			for (int i = 0; i < end; i++) {
				sb.append(",");
				sb.append(followNumberKeys[i]);
				sb.append(" = ");
				sb.append(getDBSafeNumber(followNumberKeys[i], map));
			}
		} else {
			for (int i = 0; i < end; i++) {
				sb.append(",");
				sb.append(followNumberKeys[i]);
				sb.append(" = 0");
			}
		}
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followTextKeys
	 *            文字列型チェック連動テキスト項目キー集合
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, boolean addCheckValue) throws ParseException {
		addFollowCheckTextInsert(sb, map, checkNumberKey, followTextKeys, 1, addCheckValue);
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followTextKeys
	 *            文字列型チェック連動テキスト項目キー集合
	 * @param selectedIndex
	 *            選択扱いとみなすチェック項目値
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, int selectedIndex, boolean addCheckValue) throws ParseException {

		Object obj = VRBindPathParser.get(checkNumberKey, map);
		if (addCheckValue) {
			sb.append(",");
			sb.append(getDBSafeNumber(checkNumberKey, map));
		}

		int end = followTextKeys.length;
		if ((obj instanceof Integer) && (((Integer) obj).intValue() == selectedIndex)) {
			for (int i = 0; i < end; i++) {
				sb.append(",");
				sb.append(getDBSafeString(followTextKeys[i], map));
			}
		} else {
			for (int i = 0; i < end; i++) {
				sb.append(",''");
			}
		}
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followTextKeys
	 *            文字列型チェック連動テキスト項目キー集合
	 * @param followNumberKeys
	 *            数値型チェック連動テキスト項目キー集合
	 * @param textToNumber
	 *            テキスト出力が先か
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, String[] followNumberKeys, boolean textToNumber, boolean addCheckValue) throws ParseException {

		Object obj = VRBindPathParser.get(checkNumberKey, map);
		if (addCheckValue) {
			sb.append(",");
			sb.append(getDBSafeNumber(checkNumberKey, map));
		}

		int end;
		if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
			if (textToNumber) {
				// 文字出力が先
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(getDBSafeString(followTextKeys[i], map));
				}
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(getDBSafeNumber(followNumberKeys[i], map));
				}
			} else {
				// 数値出力が先
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(getDBSafeNumber(followNumberKeys[i], map));
				}
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",");
					sb.append(getDBSafeString(followTextKeys[i], map));
				}
			}
		} else {
			if (textToNumber) {
				// 文字出力が先
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",''");
				}
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",0");
				}
			} else {
				// 数値出力が先
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",0");
				}
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",''");
				}
			}
		}
	}

	/**
	 * チェック値と連動して出力有無を切り替えて出力します。
	 * 
	 * @param sb
	 *            追加先バッファ
	 * @param map
	 *            値取得元
	 * @param checkNumberKey
	 *            数値型チェック項目キー
	 * @param followNumberKeys
	 *            数値型チェック連動テキスト項目キー集合
	 * @param addCheckValue
	 *            チェック項目も出力するか
	 * @throws ParseException
	 *             解析例外
	 */
	public static void addFollowCheckNumberInsert(StringBuffer sb, VRMap map, String checkNumberKey, String[] followNumberKeys, boolean addCheckValue) throws ParseException {

		Object obj = VRBindPathParser.get(checkNumberKey, map);
		if (addCheckValue) {
			sb.append(",");
			sb.append(getDBSafeNumber(checkNumberKey, map));
		}

		int end = followNumberKeys.length;
		if ((obj instanceof Integer) && (((Integer) obj).intValue() == 1)) {
			for (int i = 0; i < end; i++) {
				sb.append(",");
				sb.append(getDBSafeNumber(followNumberKeys[i], map));
			}
		} else {
			for (int i = 0; i < end; i++) {
				sb.append(",0");
			}
		}
	}

	/**
	 * 指定キーフィールドが一致する行マップを返します。
	 * 
	 * @param array
	 *            データ集合
	 * @param fieldName
	 *            比較フィールド名
	 * @param source
	 *            比較元ソース
	 * @throws ParseException
	 *             解析例外
	 * @return 指定キーフィールドが一致する行マップ
	 */
	public static VRMap getMatchRow(VRArrayList array, String fieldName, VRBindSource source) throws ParseException {

		VRMap result = null;
		if (array != null) {
			if (VRBindPathParser.has(fieldName, source)) {
				Object obj = VRBindPathParser.get(fieldName, source);
				if (obj != null) {
					Iterator it = array.iterator();
					while (it.hasNext()) {
						VRMap row = (VRMap) it.next();
						if (obj.equals(VRBindPathParser.get(fieldName, row))) {
							result = row;
							break;
						}
					}
				}

			}
		}
		return result;
	}

	/**
	 * 指定フィールドと一致するインデックスデータをコンボに設定します。
	 * 
	 * @param array
	 *            コンボに関連付けられたデータ集合
	 * @param field
	 *            チェックフィールド名
	 * @param source
	 *            現在のデータ列
	 * @param combo
	 *            コンボ
	 * @throws ParseException
	 *             解析例外
	 * @return 該当するデータが見つかったか。
	 * @see 表示データがユニークキーではなく、実データと表示データが一致しないコンボのデータ割り当てに使用します。
	 */
	public static boolean followComboIndex(VRArrayList array, String field, VRMap source, JComboBox combo) throws ParseException {
		if (array != null) {
			Object nowNo = VRBindPathParser.get(field, source);
			if (!IkenshoCommon.isNullText(nowNo)) {
				int end = array.getDataSize();
				for (int i = 0; i < end; i++) {
					VRMap row = (VRMap) array.getData(i);
					if (VRBindPathParser.has(field, row)) {
						Object no = VRBindPathParser.get(field, row);
						if (nowNo.equals(no)) {
							if (combo.getSelectedItem() != row) {
								combo.setSelectedItem(row);
							}
							return true;
						}
					}
				}
			}
		}
		combo.setSelectedItem(null);
		return false;
	}

	/**
	 * 最新の消費税情報を取得します。
	 * 
	 * @param dbm
	 *            DBManager
	 * @param map
	 *            取得情報追加先
	 * @throws SQLException
	 *             SQL例外
	 */
	public static void setTax(IkenshoFirebirdDBManager dbm, VRMap map) throws SQLException {
		StringBuffer sb = new StringBuffer();
		sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" TAX.TAX");
		sb.append(" FROM");
		sb.append(" TAX");
		VRList array = (VRList) dbm.executeQuery(sb.toString());
		if (array.size() > 0) {
			map.putAll((VRMap) array.getData());
		}

	}

	/**
	 * マスタデータを一括して取得します。
	 * 
	 * @param dbm
	 *            DBManager
	 * @param dest
	 *            結果代入先
	 * @param nameField
	 *            対象のフィールド名
	 * @param table
	 *            対象のテーブル名
	 * @param codeField
	 *            WHERE句に用いるフィールド名
	 * @param orderField
	 *            ORDER BY句に用いるフィールド名
	 * @throws SQLException
	 *             SQL例外
	 * @throws ParseException
	 *             解析例外
	 */
	public static void getMasterTable(IkenshoFirebirdDBManager dbm, HashMap dest, String nameField, String table, String codeField, String orderField) throws SQLException, ParseException {

		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append(" *");
		sb.append(" FROM ");
		sb.append(table);
		sb.append(" ORDER BY ");
		sb.append(orderField);

		VRArrayList fullArray = (VRArrayList) dbm.executeQuery(sb.toString());
		int end = fullArray.size();
		if (end > 0) {
			HashMap arrayMap = new HashMap();
			for (int i = 0; i < end; i++) {
				VRMap row = (VRMap) fullArray.getData(i);
				Integer code = (Integer) VRBindPathParser.get(codeField, row);
				Object obj = arrayMap.get(code);
				if (obj instanceof VRArrayList) {
					((VRArrayList) obj).addData(row);
				} else {
					VRArrayList array = new VRArrayList();
					array.addData(row);
					arrayMap.put(code, array);
				}
			}

			Iterator it = arrayMap.entrySet().iterator();
			while (it.hasNext()) {
				Object obj = it.next();
				dest.put(((Map.Entry) obj).getKey(), new VRHashMapArrayToConstKeyArrayAdapter((VRArrayList) (((Map.Entry) obj).getValue()), nameField));
			}
		}
	}

	/**
	 * 保険者および医療機関の登録状況を返します。
	 * 
	 * @throws Exception
	 *             処理例外
	 * @return int 保険者および医療機関の登録状況(ビットフラグ):0=ともに1件以上存在、1=保険者が0件、2=医療機関が0件
	 */
	public static int checkInsurerDoctorCheck() throws Exception {

		int insurers = 0;
		int doctors = 0;

		StringBuffer sb;
		VRArrayList result;

		sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" COUNT(*) AS INSURER");
		sb.append(" FROM");
		sb.append(" INSURER");

		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		result = (VRArrayList) dbm.executeQuery(sb.toString());
		if (result.getDataSize() > 0) {
			insurers = ((Integer) VRBindPathParser.get("INSURER", (VRMap) result.getData())).intValue();
		}
		sb = new StringBuffer();
		sb.append("SELECT");
		sb.append(" COUNT(*) AS DOCTOR");
		sb.append(" FROM");
		sb.append(" DOCTOR");
		result = (VRArrayList) dbm.executeQuery(sb.toString());
		if (result.getDataSize() > 0) {
			doctors = ((Integer) VRBindPathParser.get("DOCTOR", (VRMap) result.getData())).intValue();
		}
		dbm.finalize();

		int flag = CHECK_OK;
		if (insurers <= 0) {
			flag |= CHECK_INSURER_NOTHING;
		}
		if (doctors <= 0) {
			flag |= CHECK_DOCTOR_NOTHING;
		}

		return flag;
	}

	// 2006/12/11[Tozo Tanaka] : add begin
	public static boolean isConvertedNoBill(VRBindSource map) {
		if (map != null) {
			Object obj = map.getData("INSURER_NO");
			if ((obj != null) && (!"".equals(obj))) {
				if ("0".equals(String.valueOf(map.getData("OUTPUT_PATTERN")))) {
					// 保険者選択済みかつ請求書出力パターンが0＝以降直後で請求データが不正
					return true;
				}
			}
			// 意見書で使用した医療機関を削除すると下記に該当するのでコメントアウト
			// obj = map.getData("DR_NM");
			// if ((obj != null) && (!"".equals(obj))) {
			// obj = map.getData("DR_NO");
			// if ((obj == null) || "".equals(String.valueOf(obj))) {
			// // 医者名存在かつ医者番号がなし＝以降直後で請求データが不正
			// return true;
			// }
			// }
		}

		return false;
	}
	// 2006/12/11[Tozo Tanaka] : add end


    /**
     * マップ内の保険者名と保険者区分を結合したフィールドを追加します。
     * @param insurerData 保険者一覧
     * @param nameTypeBindPath 追加フィールド名
     */
    public static void buildInsureNameType(VRList insurerData, String nameTypeBindPath) throws Exception{
        Iterator it = insurerData.iterator();
        while (it.hasNext()) {
            VRMap row = (VRMap) it.next();
            String insureName = ACCastUtilities.toString(VRBindPathParser.get(
                    "INSURER_NM", row));
            switch (ACCastUtilities.toInt(VRBindPathParser.get("INSURER_TYPE",
                    row), 0)) {
            case 1:
                row.setData(nameTypeBindPath, insureName + "(主治医意見書のみ)");
                break;
            case 2:
                row.setData(nameTypeBindPath, insureName + "(医師意見書のみ)");
                break;
            default:
                row.setData(nameTypeBindPath, insureName);
                break;
            }
        }
    }
    
    //2006/09/07 [Tozo Tanaka] : add begin
    /**
     * 電子化加算を加算してよいかを返します。
     * @param formatKubun 文書区分
     * @param doctorAddIT 医療機関の電子化加算区分
     * @param insurer 保険者
     * @return 電子化加算を加算してよいか
     * @throws ParseException 処理例外
     */
    public static boolean canAddIT(int formatKubun, boolean isDoctorAddIT, VRMap insurer) throws ParseException{
        return canAddIT(formatKubun, isDoctorAddIT, ACCastUtilities.toInt(VRBindPathParser.get(
                        "SHOSIN_ADD_IT_TYPE", insurer), 1));
//        if(isDoctorAddIT){
//            //電子化加算ありの医療機関
//            switch(formatKubun){
//            case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
//                //医師意見書の場合、必ず電子化加算対象とする。
//                return true;
//            default:
//                //主治医意見書の場合、電子化加算区分が0のときのみ、電子化加算対象とする。
//                return ACCastUtilities.toInt(VRBindPathParser.get(
//                        "SHOSIN_ADD_IT_TYPE", insurer), 1) == 0;
//            }
//        }
//        return false;
    }
    /**
     * 電子化加算を加算してよいかを返します。
     * @param formatKubun 文書区分
     * @param doctorAddIT 医療機関の電子化加算区分
     * @param insurerAddITType 保険者の電子化加算区分
     * @return 電子化加算を加算してよいか
     * @throws ParseException 処理例外
     */
    public static boolean canAddIT(int formatKubun, boolean isDoctorAddIT,
            int insurerAddITType) throws ParseException {
        if(isDoctorAddIT){
            //電子化加算ありの医療機関
            switch(formatKubun){
            case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
                //医師意見書の場合、必ず電子化加算対象とする。
                return true;
            default:
                //主治医意見書の場合、電子化加算区分が0のときのみ、電子化加算対象とする。
                return insurerAddITType == 0;
            }
        }
        return false;
    }
    //2006/09/07 [Tozo Tanaka] : add end
}
