/*
 * Project code name "ORCA"
 * �厡��ӌ����쐬�\�t�g ITACHI�iJMA IKENSYO software�j
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
 * �A�v��: ITACHI
 * �J����: �c������
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �c������ �V�K�쐬
 * �X�V��: ----/--/--
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

	/** ���� */
	public static final int DEPARTMENT_DEFAULTT = 0;

	/** ���̑��̑��� */
	public static final int DEPARTMENT_OTHER = 1;

	/** �{�� */
	public static final int INSTITUTION_DEFAULTT = 0;

	/** ���莾�a */
	public static final int DISEASE_SEPCIAL_SICK_NAME = 0;

	/** ���a */
	public static final int TEIKEI_SICK_NAME = 1;

	/** ��� */
	public static final int TEIKEI_MEDICINE_NAME = 2;

	/** �p�ʒP�� */
	public static final int TEIKEI_MEDICINE_DOSAGE_UNIT = 3;

	/** �p�@ */
	public static final int TEIKEI_MEDICINE_USAGE = 4;

	/** ���_�E�_�o�Ǐ� */
	public static final int TEIKEI_MIND_SICK_NAME = 5;

	/** �l������ */
	public static final int TEIKEI_BODY_STATUS_HAND_FOOT_NAME = 6;

	/** ��� */
	public static final int TEIKEI_BODY_STATUS_PARALYSIS_NAME = 7;

	/** �ؗ͂̒ቺ */
	public static final int TEIKEI_BODY_STATUS_MUSCULAR_DOWN_NAME = 8;

	/** ��� */
	public static final int TEIKEI_BODY_STATUS_JYOKUSOU_NAME = 9;

	/** �畆���� */
	public static final int TEIKEI_BODY_STATUS_SKIN_NAME = 10;

	/** ���� */
	public static final int TEIKEI_SICK_COPE_URINE_NAME = 11;

	/** �]�|�E���� */
	public static final int TEIKEI_SICK_COPE_FACTURE_NAME = 12;

	/** �p�j */
	public static final int TEIKEI_SICK_COPE_PROWL_NAME = 13;

	/** ��� */
	public static final int TEIKEI_SICK_COPE_JYOKUSOU_NAME = 14;

	/** �������x�� */
	public static final int TEIKEI_SICK_COPE_PNEUMONIA_NAME = 15;

	/** ���� */
	public static final int TEIKEI_SICK_COPE_INTESTINES_NAME = 16;

	/** �Պ����� */
	public static final int TEIKEI_SICK_COPE_INFECTION_NAME = 17;

	/** �S�x�@�\�̒ቺ */
	public static final int TEIKEI_SICK_COPE_HEART_LUNG_NAME = 18;

	/** �ɂ� */
	public static final int TEIKEI_SICK_COPE_PAIN_NAME = 19;

	/** �E�� */
	public static final int TEIKEI_SICK_COPE_DEHYDRATION_NAME = 20;

	/** ���̑� */
	public static final int TEIKEI_SICK_COPE_OTHER_NAME = 21;

	/** ���̑� */
	public static final int TEIKEI_SICK_TYPE_OTHER_NAME = 22;

	/** ���� */
	public static final int TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME = 23;

	/** ���� */
	public static final int TEIKEI_CARE_SERVICE_ENGE_NAME = 24;

	/** �ېH */
	public static final int TEIKEI_CARE_SERVICE_EAT_NAME = 25;

	/** �ړ� */
	public static final int TEIKEI_CARE_SERVICE_MOVE_NAME = 26;

	/** ���̑� */
	public static final int TEIKEI_CARE_SERVICE_OTHER_NAME = 27;

	/** ������ */
	public static final int TEIKEI_INFECTION_NAME = 28;

	/** ���X�s���[�^ */
	public static final int TEIKEI_RESPIRATOR_TYPE = 29;

	/** ���X�s���[�^ */
	public static final int TEIKEI_RESPIRATOR_SETTING = 30;

	/** �o�� */
	public static final int TEIKEI_TUBE_TYPE = 31;

	/** �o��-�T�C�Y */
	public static final int TEIKEI_TUBE_SIZE = 32;

	/** �o��-���� */
	public static final int TEIKEI_TUBE_CHANGE_SPAN = 33;

	/** �J�j���[�� */
	public static final int TEIKEI_CANURE_SIZE = 34;

	/** �h���[�� */
	public static final int TEIKEI_DOREN_POS_NAME = 35;

	/** �J�e�[�e�� */
	public static final int TEIKEI_CATHETER_SIZE = 36;

	/** �J�e�[�e�� */
	public static final int TEIKEI_CATHETER_CHANGE_SPAN = 37;

	/** ���L���� */
	public static final int TEIKEI_MENTION_NAME = 38;

	/** �×{�����w����̗��ӎ��� */
	public static final int TEIKEI_HOUMON_RYOUYOU_SHIDOU_RYUIJIKOU = 39;

	/** ���n�r���e�[�V���� */
	public static final int TEIKEI_HOUMON_REHABILITATION = 40;

	/** ��ጂ̏��u�� */
	public static final int TEIKEI_HOUMON_JYOKUSOU = 41;

	/** �����E�g�p��Ë@�퓙�̑��쉇���E�Ǘ� */
	public static final int TEIKEI_HOUMON_KIKI_SOUSA_ENJYO = 42;

	/** ���̑� */
	public static final int TEIKEI_HOUMON_SONOTA = 43;

	/** ���L���ׂ����ӎ��� */
	public static final int TEIKEI_HOUMON_TOKKI = 44;

	/** �ݑ�K��_�H���� */
	public static final int TEIKEI_HOUMON_TENTEKI_CHUSHA = 45;

	// �ȍ~�AH18�@�����ǉ���^��
	/** �Ǐ�Ƃ��ĕs����ɂ������̓I�� */
	public static final int TEIKEI_INSECURE_CONDITION_NAME = 46;

	/** �S�g�̏��-���(���̑�) */
	public static final int TEIKEI_MAHI_POSITION_OTHER_NAME = 47;
	/** �S�g�̏��-�֐߂̍S�k */
	public static final int TEIKEI_CENNECT_KOSHUKU_NAME = 48;
	/** �S�g�̏��-�֐߂̒ɂ� */
	public static final int TEIKEI_CONNECT_PAIN_NAME = 49;
	// /** �h�{�E�H����-����̐H���ێ�� */
	// public static final int TEIKEI_MEAL_INTAKE_NAME = 49;
	// /** �h�{�E�H����-�H�~ */
	// public static final int TEIKEI_APPETITE_NAME = 50;
	/** �h�{�E�H����-�h�{��H������̗��ӓ_ */
	public static final int TEIKEI_EATING_RYUIJIKOU_NAME = 50;
	/** �a��-�ړ��\�͂̒ቺ */
	public static final int TEIKEI_MOVILITY_DOWN_NAME = 51;
	/** �a��-�������� */
	public static final int TEIKEI_TOJIKOMORI_NAME = 52;
	/** �a��-�ӗ~�ቺ */
	public static final int TEIKEI_IYOKU_DOWN_NAME = 53;
	/** �a��-��h�{ */
	public static final int TEIKEI_LOW_ENERGY_NAME = 54;
	/** �a��-�ېH�E�����@�\�ቺ */
	public static final int TEIKEI_SESSHOKU_ENGE_DOWN_NAME = 55;
	/** �a��-���񓙂ɂ���u�� */
	public static final int TEIKEI_GAN_TOTSU_NAME = 56;
	/** ��w�I�ϓ_����̗��ӎ���-�^�� */
	public static final int TEIKEI_CARE_SERVICE_UNDOU_NAME = 57;
	// /** ���̕K�v���̒��x�Ɋւ���\��̌��ʂ�-���P�ւ̊�^�����҂ł���T�[�r�X */
	// public static final int TEIKEI_OUTLOOK_SERVISE_NAME = 59;

    // �ȍ~�A��t�ӌ����ǉ���^��
    /** ��t�ӌ���-���a */
    public static final int TEIKEI_ISHI_SICK_NAME = 60;

    /** ��t�ӌ���-��� */
    public static final int TEIKEI_ISHI_MEDICINE_NAME = 61;

    /** ��t�ӌ���-�p�ʒP�� */
    public static final int TEIKEI_ISHI_MEDICINE_DOSAGE_UNIT = 62;

    /** ��t�ӌ���-�p�@ */
    public static final int TEIKEI_ISHI_MEDICINE_USAGE = 63;

    /** ��t�ӌ���-�Ǐ�Ƃ��ĕs����ɂ������̓I�� */
    public static final int TEIKEI_ISHI_INSECURE_CONDITION_NAME = TEIKEI_INSECURE_CONDITION_NAME;

    /** ��t�ӌ���-���_�E�_�o�Ǐ� */
    public static final int TEIKEI_ISHI_MIND_SICK_NAME = TEIKEI_MIND_SICK_NAME;

    /** ��t�ӌ���-���� */
    public static final int TEIKEI_ISHI_SICK_COPE_URINE_NAME = TEIKEI_SICK_COPE_URINE_NAME;

    /** ��t�ӌ���-�]�|�E���� */
    public static final int TEIKEI_ISHI_SICK_COPE_FACTURE_NAME = TEIKEI_SICK_COPE_FACTURE_NAME;

    /** ��t�ӌ���-�p�j */
    public static final int TEIKEI_ISHI_SICK_COPE_PROWL_NAME = TEIKEI_SICK_COPE_PROWL_NAME;

    /** ��t�ӌ���-��� */
    public static final int TEIKEI_ISHI_SICK_COPE_JYOKUSOU_NAME = TEIKEI_SICK_COPE_JYOKUSOU_NAME;

    /** ��t�ӌ���-�������x�� */
    public static final int TEIKEI_ISHI_SICK_COPE_PNEUMONIA_NAME = 64;

    /** ��t�ӌ���-���� */
    public static final int TEIKEI_ISHI_SICK_COPE_INTESTINES_NAME = 65;

    /** ��t�ӌ���-�Պ����� */
    public static final int TEIKEI_ISHI_SICK_COPE_INFECTION_NAME = TEIKEI_SICK_COPE_INFECTION_NAME;

    /** ��t�ӌ���-�S�x�@�\�̒ቺ */
    public static final int TEIKEI_ISHI_SICK_COPE_HEART_LUNG_NAME = TEIKEI_SICK_COPE_HEART_LUNG_NAME;

    /** ��t�ӌ���-�ɂ� */
    public static final int TEIKEI_ISHI_SICK_COPE_PAIN_NAME = 66;

    /** ��t�ӌ���-�E�� */
    public static final int TEIKEI_ISHI_SICK_COPE_DEHYDRATION_NAME = TEIKEI_SICK_COPE_DEHYDRATION_NAME;

    /** ��t�ӌ���-���̑� */
    public static final int TEIKEI_ISHI_SICK_COPE_OTHER_NAME = TEIKEI_SICK_COPE_OTHER_NAME;

    /** ��t�ӌ���-���̑� */
    public static final int TEIKEI_ISHI_SICK_TYPE_OTHER_NAME = TEIKEI_SICK_TYPE_OTHER_NAME;

    /** ��t�ӌ���-���� */
    public static final int TEIKEI_ISHI_CARE_SERVICE_BLOOD_PRESSURE_NAME = TEIKEI_CARE_SERVICE_BLOOD_PRESSURE_NAME;

    /** ��t�ӌ���-���� */
    public static final int TEIKEI_ISHI_CARE_SERVICE_ENGE_NAME = TEIKEI_CARE_SERVICE_ENGE_NAME;

    /** ��t�ӌ���-�ېH */
    public static final int TEIKEI_ISHI_CARE_SERVICE_EAT_NAME = TEIKEI_CARE_SERVICE_EAT_NAME;

    /** ��t�ӌ���-�ړ� */
    public static final int TEIKEI_ISHI_CARE_SERVICE_MOVE_NAME = TEIKEI_CARE_SERVICE_MOVE_NAME;

    /** ��t�ӌ���-���̑� */
    public static final int TEIKEI_ISHI_CARE_SERVICE_OTHER_NAME = TEIKEI_CARE_SERVICE_OTHER_NAME;

    /** ��t�ӌ���-������ */
    public static final int TEIKEI_ISHI_INFECTION_NAME = TEIKEI_INFECTION_NAME;

    /** ��t�ӌ���-���L���� */
    public static final int TEIKEI_ISHI_MENTION_NAME = 67;
    
    // [ID:0000518][Tozo TANAKA] 2009/09/04 add begin �y2009�N�x�Ή��F���L�����ꗗ�z���莾�a���ڂ̕ҏW���\�Ƃ��� 
    /** ���莾�a */
    public static final int TEIKEI_SPECIFIED_DISEASE_NAME = 68;
    // [ID:0000518][Tozo TANAKA] 2009/09/04 add end �y2009�N�x�Ή��F���L�����ꗗ�z���莾�a���ڂ̕ҏW���\�Ƃ��� 

	/**
	 * �ŐV�����Y���Ȃ�
	 */
	public static final int NEW_DOC_NONE = 0;

	/**
	 * �ŐV�����͈ӌ���
	 */
	public static final int NEW_DOC_IKENSHO = 1;

	/**
	 * �ی��ҁE��Ë@�ւƂ���1���ȏ㑶��
	 */
	public static final int CHECK_OK = 0;
	/**
	 * �ی��҂�0��
	 */
	public static final int CHECK_INSURER_NOTHING = 1;
	/**
	 * ��Ë@�ւ�0��
	 */
	public static final int CHECK_DOCTOR_NOTHING = 2;

	/**
	 * �R���X�g���N�^�ł��B<br />
	 * Singleton Pattern
	 */
	protected IkenshoCommon() {
	}

	/**
	 * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒lComboBox�ɐݒ肵�܂��B
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
	 * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒lComboBox�ɐݒ肵�܂��B
	 * 
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param field
	 *            �Ώۂ̃t�B�[���h��
	 * @param table
	 *            �Ώۂ̃e�[�u����
	 * @param codeField
	 *            WHERE��ɗp����t�B�[���h��
	 * @param code
	 *            WHERE��ɗp����t�B�[���h�l
	 * @param orderField
	 *            �\�[�g��ƂȂ�t�B�[���h��
	 * @throws Exception
	 *             ������O
	 */
	public static void setComboModel(JComboBox combo, String field, String table, String codeField, int code, String orderField) throws Exception {
		IkenshoFirebirdDBManager dbm = new IkenshoFirebirdDBManager();
		setComboModel(dbm, combo, field, table, codeField, code, orderField);
		dbm.finalize();
	}

	/**
	 * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒l��VRHashMapArrayToConstKeyArrayAdapter�Ƃ��ĕԂ��܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param field
	 *            �Ώۂ̃t�B�[���h��
	 * @param table
	 *            �Ώۂ̃e�[�u����
	 * @param codeField
	 *            WHERE��ɗp����t�B�[���h��
	 * @param code
	 *            WHERE��ɗp����t�B�[���h�l
	 * @param orderField
	 *            �\�[�g��ƂȂ�t�B�[���h��
	 * @return VRHashMapArrayToConstKeyArrayAdapter
	 * @throws SQLException
	 *             SQL��O
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter geArrayAdapter(IkenshoFirebirdDBManager dbm, String field, String table, String codeField, int code, String orderField) throws SQLException {
		return getFieldRows(dbm, field, table, codeField, code, orderField);
	}

	/**
	 * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒lComboBox�ɐݒ肵�܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param field
	 *            �Ώۂ̃t�B�[���h��
	 * @param table
	 *            �Ώۂ̃e�[�u����
	 * @param codeField
	 *            WHERE��ɗp����t�B�[���h��
	 * @param code
	 *            WHERE��ɗp����t�B�[���h�l
	 * @param orderField
	 *            �\�[�g��ƂȂ�t�B�[���h��
	 * @throws SQLException
	 *             SQL��O
	 */
	public static void setComboModel(IkenshoFirebirdDBManager dbm, JComboBox combo, String field, String table, String codeField, int code, String orderField) throws SQLException {
		VRHashMapArrayToConstKeyArrayAdapter adapter = geArrayAdapter(dbm, field, table, codeField, code, orderField);
		if ((adapter != null) && (adapter.getDataSize() > 0)) {
			applyComboModel(combo, adapter);
		}
	}

	/**
	 * SQL�Ŏ擾�����q���g�{�^���p��^����^����ꂽ�q���g�{�^���ɐݒ肵�܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param codes
	 *            ��^���R�[�h�z��
	 * @param formatKubun
	 *            �@�����Ή��敪
	 * @param buttons
	 *            �q���g�{�^���z��
	 * @throws SQLException
	 *             SQL��O
	 * @throws ParseException
	 *             ��͗�O
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
	 * SQL�Ŏ擾�������R�[�h�̔C�ӂ̗�̒l�W����Ԃ��܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param field
	 *            �Ώۂ̃t�B�[���h��
	 * @param table
	 *            �Ώۂ̃e�[�u����
	 * @param codeField
	 *            WHERE��ɗp����t�B�[���h��
	 * @param code
	 *            WHERE��ɗp����t�B�[���h�l
	 * @param orderField
	 *            �\�[�g��ƂȂ�t�B�[���h��
	 * @throws SQLException
	 *             SQL��O
	 * @return �C�ӂ̗�̒l�W��
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
	 * M_INSTITUTION(�{�݃}�X�^)��INSTITUTION_NM(�{�ݖ�)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param institutionKbn
	 *            �{�݋敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setMInstitution(JComboBox combo, int institutionKbn) throws Exception {
		// String sql =
		// "SELECT INSTITUTION_NM FROM M_INSTITUTION WHERE INSTITUTION_KBN=" +
		// institutionKbn;
		// setComboModel(combo, sql, "INSTITUTION_NM");
		setComboModel(combo, "INSTITUTION_NM", "M_INSTITUTION", "INSTITUTION_KBN", institutionKbn, "");
	}

	/**
	 * M_INSTITUTION(�{�݃}�X�^)��INSTITUTION_NM(�{�ݖ�)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param institutionKbn
	 *            �{�݋敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setMInstitution(IkenshoFirebirdDBManager dbm, JComboBox combo, int institutionKbn) throws Exception {
		setComboModel(dbm, combo, "INSTITUTION_NM", "M_INSTITUTION", "INSTITUTION_KBN", institutionKbn, "");
	}

	/**
	 * M_DISEASE(�������}�X�^)��DISEASE_NM(������)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param diseaseKbn
	 *            �������敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setMDisease(JComboBox combo, int diseaseKbn) throws Exception {
		// String sql = "SELECT DISEASE_NM FROM M_DISEASE WHERE DISEASE_KBN=" +
		// diseaseKbn +
		// " ORDER BY DISEASE_CD ASC";
		setComboModel(combo, "DISEASE_NM", "M_DISEASE", "DISEASE_KBN", diseaseKbn, "DISEASE_CD ASC");
	}

	/**
	 * M_DISEASE(�������}�X�^)��DISEASE_NM(������)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param diseaseKbn
	 *            �������敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setMDisease(IkenshoFirebirdDBManager dbm, JComboBox combo, int diseaseKbn) throws Exception {
		setComboModel(dbm, combo, "DISEASE_NM", "M_DISEASE", "DISEASE_KBN", diseaseKbn, "DISEASE_CD ASC");
	}

	/**
	 * M_DISEASE(�������}�X�^)��DISEASE_NM(������)���A�_�v�^�`���ŕԂ��܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param diseaseKbn
	 *            �������敪
	 * @return �A�_�v�^
	 * @throws Exception
	 *             ������O
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter getMDisease(IkenshoFirebirdDBManager dbm, int diseaseKbn) throws Exception {
		return geArrayAdapter(dbm, "DISEASE_NM", "M_DISEASE", "DISEASE_KBN", diseaseKbn, "DISEASE_CD ASC");
	}

	/**
	 * �R���{�ɃA�_�v�^�����b�v�����R���{���f���𐶐����Đݒ肵�܂��B
	 * 
	 * @param combo
	 *            �ݒ��R���{
	 * @param adapter
	 *            �f�[�^�A�_�v�^
	 */
	public static void applyComboModel(JComboBox combo, VRHashMapArrayToConstKeyArrayAdapter adapter) {
		combo.setModel(createComboAdapter(adapter));
	}

	/**
	 * �R���{�ɃA�_�v�^�����b�v�����R���{���f���𐶐����Đݒ肵�܂��B
	 * 
	 * @param combo
	 *            �ݒ��R���{
	 * @param adapter
	 *            �f�[�^�A�_�v�^
	 */
	public static void applyComboModel(JComboBox combo, VRArrayList adapter) {
		combo.setModel(new ACComboBoxModelAdapter(adapter));
	}

	/**
	 * �A�_�v�^�����b�v�����R���{���f���𐶐����ĕԂ��܂��B
	 * 
	 * @param adapter
	 *            �f�[�^�A�_�v�^
	 * @return �R���{���f��
	 */
	public static ACComboBoxModelAdapter createComboAdapter(VRHashMapArrayToConstKeyArrayAdapter adapter) {
		return new ACComboBoxModelAdapter(adapter);
	}

	/**
	 * M_SINRYOUKA(�f�Éȃ}�X�^)��SINRYOUKA_NM(�f�ÉȖ�)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param sinryoukaKbn
	 *            �f�Éȋ敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setMSinryouka(JComboBox combo, int sinryoukaKbn) throws Exception {
		// String sql = "SELECT SINRYOUKA_NM FROM M_SINRYOUKA WHERE
		// SINRYOUKA_KBN=" +
		// sinryoukaKbn +
		// " ORDER BY SINRYOUKA_CD ASC";
		setComboModel(combo, "SINRYOUKA_NM", "M_SINRYOUKA", "SINRYOUKA_KBN", sinryoukaKbn, "SINRYOUKA_CD ASC");
	}

	/**
	 * M_SINRYOUKA(�f�Éȃ}�X�^)��SINRYOUKA_NM(�f�ÉȖ�)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param sinryoukaKbn
	 *            �f�Éȋ敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setMSinryouka(IkenshoFirebirdDBManager dbm, JComboBox combo, int sinryoukaKbn) throws Exception {
		setComboModel(dbm, combo, "SINRYOUKA_NM", "M_SINRYOUKA", "SINRYOUKA_KBN", sinryoukaKbn, "SINRYOUKA_CD ASC");
	}

	/**
	 * TKB_KBN(��^���e�[�u��)��TEIKEIBUN(��^��)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param tkbKbn
	 *            ��^���敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setTeikeibun(JComboBox combo, int tkbKbn) throws Exception {
		// String sql = "SELECT TEIKEIBUN FROM TEIKEIBUN WHERE TKB_KBN=" +
		// tkbKbn +
		// " ORDER BY TKB_CD";
		setComboModel(combo, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", tkbKbn, "TKB_CD");
	}

	/**
	 * TKB_KBN(��^���e�[�u��)��TEIKEIBUN(��^��)���A�C�e���Ƃ��Đݒ肵�܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param combo
	 *            �Ώۂ̃R���{
	 * @param tkbKbn
	 *            ��^���敪
	 * @throws Exception
	 *             ������O
	 */
	public static void setTeikeibun(IkenshoFirebirdDBManager dbm, JComboBox combo, int tkbKbn) throws Exception {
		setComboModel(dbm, combo, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", tkbKbn, "TKB_CD");
	}

	/**
	 * TKB_KBN(��^���e�[�u��)��TEIKEIBUN(��^��)���A�_�v�^�`���ŕԂ��܂��B
	 * 
	 * @param dbm
	 *            DMManager
	 * @param tkbKbn
	 *            ��^���敪
	 * @return �A�_�v�^
	 * @throws Exception
	 *             ������O
	 */
	public static VRHashMapArrayToConstKeyArrayAdapter getTeikeibun(IkenshoFirebirdDBManager dbm, int tkbKbn) throws Exception {
		return geArrayAdapter(dbm, "TEIKEIBUN", "TEIKEIBUN", "TKB_KBN", tkbKbn, "TKB_CD");
	}

	/**
	 * �ŐV�����͎w����
	 */
	public static final int NEW_DOC_SIJISHO = 2;

	/**
	 * �ӌ����f�[�^�Ǝw�����f�[�^���r���A�ŐV�̕��������ł��邩��Ԃ��܂��B
	 * 
	 * @param ikenshoData
	 *            �ӌ����f�[�^
	 * @param ikenshoIndex
	 *            �ӌ����f�[�^�s�ԍ�
	 * @param ikenshoCreateDateField
	 *            �ӌ����̍쐬���t�B�[���h��
	 * @param sijishoData
	 *            �w�����f�[�^
	 * @param sijishoIndex
	 *            �w�����f�[�^�s�ԍ�
	 * @param sijishoCreateDateField
	 *            �w�����̍쐬���t�B�[���h��
	 * @throws ParseException
	 *             ��͗�O
	 * @return int ��r����
	 */
	public static int getNewDocumentStatus(VRArrayList ikenshoData, int ikenshoIndex, String ikenshoCreateDateField, VRArrayList sijishoData, int sijishoIndex, String sijishoCreateDateField) throws ParseException {
		int useDoc = NEW_DOC_NONE;

		Date ikenshoDate = null;
		if (ikenshoData.getDataSize() > ikenshoIndex) {
			// �ӌ���������
			Object ikenObj = VRBindPathParser.get(ikenshoCreateDateField, (VRBindSource) ikenshoData.getData(ikenshoIndex));
			if (ikenObj instanceof Date) {
				ikenshoDate = (Date) ikenObj;
			}
		}

		Date sijishoDate = null;
		if (sijishoData.getDataSize() > sijishoIndex) {
			// �w����������
			Object sijiObj = VRBindPathParser.get(sijishoCreateDateField, (VRBindSource) sijishoData.getData(sijishoIndex));
			if (sijiObj instanceof Date) {
				sijishoDate = (Date) sijiObj;
			}
		}

		if (ikenshoDate != null) {
			// �ӌ���������
			if (sijishoDate != null) {
				// �w����������
				Calendar ikenCal = Calendar.getInstance();
				Calendar sijiCal = Calendar.getInstance();
				ikenCal.setTime(ikenshoDate);
				sijiCal.setTime(sijishoDate);

				if (ikenCal.before(sijiCal)) {
					// �w�������ŐV
					useDoc = NEW_DOC_SIJISHO;
				} else {
					// �ӌ������ŐV
					useDoc = NEW_DOC_IKENSHO;
				}
			} else {
				// �ӌ������ŐV
				useDoc = NEW_DOC_IKENSHO;
			}
		} else {
			// �ӌ����͂Ȃ�
			if (sijishoDate != null) {
				// �w�������ŐV
				useDoc = NEW_DOC_SIJISHO;
			}
		}
		return useDoc;
	}

	/**
	 * ���������̊��҂����݂��邩��Ԃ��܂��B
	 * 
	 * @param map
	 *            �p�����^�}�b�v
	 * @throws Exception
	 *             ������O
	 * @return ���������̊��҂����݂��邩
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
	 * �w�肵�����t�R���g���[�����K���l���͍ς݂Ȃ�΃t�H�[�}�b�g���ʂ��A����ȊO�͋󕶎���Ԃ��܂��B
	 * 
	 * @param date
	 *            ���t�R���g���[��
	 * @param format
	 *            �t�H�[�}�b�g
	 * @return String ���茋��
	 */
	public static String getBrankOrFormatedDate(IkenshoEraDateTextField date, Format format) {
		if (date.getInputStatus() == IkenshoEraDateTextField.STATE_VALID) {
			return format.format(date.getDate());
		}
		return "";

	}

	/**
	 * ������Null�܂��͋󕶎��ł��邩��Ԃ��܂��B
	 * 
	 * @param obj
	 *            �]��������
	 * @return ������Null�܂��͋󕶎��ł��邩
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
		final char FULL_SPACE = '�@';
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
	 * ��O���b�Z�[�W��\�����܂��B
	 * 
	 * @param ex
	 *            ��O
	 */
	public static void showExceptionMessage(Throwable ex) {
		// 2006/12/03[Tozo Tanaka] : replace begin
		// StringWriter sw = new StringWriter();
		// ex.printStackTrace(new PrintWriter(sw));
		// ACMessageBox.show("�d��ȃG���[���������܂����B\n�T�|�[�g�Z���^�[�ɓd�b���Ă��������B");
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
		final ACGroupBox detailInfomation = new ACGroupBox("�T�|�[�g���");
		final ACTextArea area = new ACTextArea();
		final ACLabel environment = new ACLabel();
		ACPanel buttonArea = new ACPanel();
		ACButton copy = new ACButton("�R�s�[(C)");
		copy.setMnemonic('C');
		ACButton detail = new ACButton("�ڍ�(D)");
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
			// �p�b�`��unknown�Ȃ牽���\�����Ȃ�
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
					ACMessageBox.show("�N���b�v�{�[�h�ɃT�|�[�g�����R�s�[���܂����B");
				} else {
					ACMessageBox.show("�N���b�v�{�[�h�ւ̃A�N�Z�X�Ɏ��s���܂����B");
				}
			}
		});

		// 2006/12/09[Tozo Tanaka] : add end

		// �G���[���b�Z�[�W�u�� begin
		// 2006/02/27[Kazumi Hirose (Kazumix) add]
		sb.append(ACConstants.LINE_SEPARATOR);
		String errLine = ex.toString();

		if (errLine.indexOf("Firebird 1.0") >= 0) {
			errLine = "���o�[�W������Firebird�����o����܂����A���Ђ̐��i�Ƌ������Ă���\��������܂��B";
        } else if (errLine.indexOf("FileNotFoundException") >= 0) {
            if(errLine.toLowerCase().indexOf(".pdf") >= 0) {
                errLine = "�y����p�̃t�@�C�����쐬�ł��܂���B�z�t�@�C���̏o�͂Ɏ��s���܂����B�㌩���͊Ǘ��Ҍ����ŃC���X�g�[���A���s����K�v������܂��B";
            }else{
                errLine = "�y�t�@�C���ɃA�N�Z�X�ł��܂���B�z�t�@�C���A�N�Z�X�Ɏ��s���܂����B�㌩���͊Ǘ��Ҍ����ŃC���X�g�[���A���s����K�v������܂��B";
            }
		} else if (errLine.indexOf("ParseException: ���P�[����`�����݂��܂���B") >= 0) {
			errLine = "�ycalendar.xml�����݂��Ȃ��z�C���X�g�[�����calendar.xml�����݂��Ă��܂���B�ăC���X�g�[�����Ă��������B";

		} else if (errLine.indexOf("ArrayIndexOutOfBoundsException: Acrobat/Path at") >= 0) {
			errLine = "�yPDF�̐ݒ�ɖ�肪����܂��B�zPDF�̐ݒ��Acrobat5.0�ȍ~��PDF�{���\�t�g���ݒ肳��Ă��邩�m�F���Ă��������B";

		} else if (errLine.indexOf("ACProperityXML.getValueAt") >= 0) {
			errLine = "�y�ݒ�t�@�C���ɖ�肪����܂��B�zPDF�̐ݒ�������̓f�[�^�x�[�X�̐ݒ���m�F���Ă��������B" +ACConstants.LINE_SEPARATOR+"��������Ȃ��ꍇ�A�ăC���X�g�[�����Ă��������B";

		} else if (errLine.indexOf("ParseException: Acrobat/Path") >= 0) {
			errLine = "�yPDF�ݒ�ǂݍ��݂Ɏ��s���܂����B�z�C���X�g�[����ɓ��{�ꖼ�t�H���_���܂܂�Ă��Ȃ����m�F���Ă��������B";

		} else if (errLine.indexOf("ParseException: DBConfig/Path") >= 0) {
			errLine = "�yDB�ݒ�ǂݍ��݂Ɏ��s���܂����B�z�C���X�g�[����ɓ��{�ꖼ�t�H���_���܂܂�Ă��Ȃ����m�F���Ă��������B";

		} else if (errLine.indexOf("SAXParseException: �����ϊ��G���[:") >= 0) {
			errLine = "�yXML�̉�͂Ɏ��s���܂����B�z�C���X�g�[����ɓ��{�ꖼ�t�H���_���܂܂�Ă��Ȃ����m�F���Ă��������B";

		} else if (errLine.indexOf("SAXParseException: Invalid byte 1 of 1-byte UTF-8 sequence.") >= 0) {
			errLine = "�yXML�̉�͂Ɏ��s���܂����B�z�C���X�g�[����ɓ��{�ꖼ�t�H���_���܂܂�Ă��Ȃ����m�F���Ă��������B";

		} else if (errLine.indexOf("SAXParseException: �s���� XML ����:  &#xb") >= 0) {
			errLine = "�y�����^�u���܂܂�Ă��܂��z�ŐV�̃f�[�^�ڍs�c�[���ōēx�f�[�^�ڍs���s���Ă��������B";

		} else if (errLine.indexOf("error for column PATIENT_NO") >= 0) {
			errLine = "�y�f�[�^�ڍs�Ɏ��s���Ă��܂��B�z�ŐV�̃f�[�^�ڍs�c�[���ōēx�f�[�^�ڍs���s���Ă��������B";

		}

		sb.append(errLine);
		// �G���[���b�Z�[�W�u�� end

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
					// �ύX�O�ƌ�̃T�C�Y���擾
					Dimension oldD = content.getPreferredSize();
					area.setVisible(!area.isVisible());
					Dimension newD = content.getPreferredSize();

					// ��ʗ̈���擾
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
					// �v�������T�C�Y���v�Z
					int newH = (int) Math.max(minimumSize.getHeight(), pnl.getParent().getHeight() + newD.getHeight() - oldD.getHeight());
					Point pos = pnl.getParent().getLocationOnScreen();
					int newY = (int) pos.getY();

					if (newH > screenSize.getHeight()) {
						// �v�����鍂�������E�̏ꍇ
						newY = (int) corner.getY();
						newH = (int) screenSize.getHeight() - 20;
					} else {
						if (newY + newH > corner.getY() + screenSize.getHeight()) {
							// ���[�𒴂���ꍇ�͏�ւ��炷
							newY = (int) (screenSize.getHeight() - newH);
						} else if (newY < corner.getY()) {
							// ��[�ɂ߂荞��ł��ꍇ�͉��ւ��炷
							newY = (int) corner.getY() + 20;
						}
					}
					pnl.getParent().setBounds((int) pos.getX(), newY, pnl.getParent().getWidth(), newH);
					// �ĕ`��
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
		ACMessageBox.show("�����𑱍s�ł��܂���B", content, true);
		// 2006/12/03[Tozo Tanaka] : replace end

	}

	/**
	 * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�Ȑ��l������Ƃ��ĕԂ��܂��B
	 * 
	 * @param key
	 *            �擾�L�[
	 * @param source
	 *            �\�[�X
	 * @throws ParseException
	 *             ��͗�O
	 * @return �ϊ�����
	 */
	public static String getDBSafeNumber(String key, VRBindSource source) throws ParseException {
		Object obj = VRBindPathParser.get(key, source);
		if (obj == null) {
			return "NULL";
		}
		return String.valueOf(obj);
	}

	/**
	 * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȕ�����Ƃ��ĕԂ��܂��B
	 * 
	 * @param key
	 *            �擾�L�[
	 * @param source
	 *            �\�[�X
	 * @throws ParseException
	 *             ��͗�O
	 * @return �ϊ�����
	 */
	public static String getDBSafeString(String key, VRBindSource source) throws ParseException {
		return IkenshoConstants.FORMAT_PASSIVE_STRING.format(VRBindPathParser.get(key, source));
	}

	/**
	 * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ��t������Ƃ��ĕԂ��܂��B
	 * 
	 * @param key
	 *            �擾�L�[
	 * @param source
	 *            �\�[�X
	 * @throws ParseException
	 *             ��͗�O
	 * @return �ϊ�����
	 */
	public static String getDBSafeDate(String key, VRBindSource source) throws ParseException {
		return IkenshoConstants.FORMAT_PASSIVE_YMD.format(VRBindPathParser.get(key, source));
	}

	/**
	 * �\�[�X���̎w��L�[�̒l��DB�֊i�[�\�ȓ���������Ƃ��ĕԂ��܂��B
	 * 
	 * @param key
	 *            �擾�L�[
	 * @param source
	 *            �\�[�X
	 * @throws ParseException
	 *             ��͗�O
	 * @return �ϊ�����
	 */
	public static String getDBSafeTime(String key, VRBindSource source) throws ParseException {
		return IkenshoConstants.FORMAT_PASSIVE_YMD_HMS.format(VRBindPathParser.get(key, source));
	}

	/**
	 * �ӌ������������쐬����Ă��邩��Ԃ��܂��B
	 * 
	 * @param source
	 *            ���ؗp�\�[�X
	 * @throws ParseException
	 *             ������O
	 * @return �ӌ������������쐬����Ă��邩
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
	 * �w��f�[�^��PDF���Œ�p�X�ɏo�͂��܂��B
	 * 
	 * @param data
	 *            ���f�[�^
	 * @throws Exception
	 *             ������O
	 * @return �o�̓t�@�C���p�X
	 */
	public static String writePDF(ACChotarouXMLWriter data) throws Exception {
		ACFrameEventProcesser processer = ACFrame.getInstance().getFrameEventProcesser();
		if (processer instanceof ACPDFCreatable) {
			return ((ACPDFCreatable) processer).writePDF(data);
		}
		return "";

		// File pdfDirectory = new File(IkenshoConstants.PRINT_PDF_DIRECTORY);
		// if (!pdfDirectory.exists()) {
		// // �f�B���N�g���쐬
		// if (!pdfDirectory.mkdir()) {
		// ACMessageBox.showExclamation("����Ɏ��s���܂����B");
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
	 * �o�͂��ꂽ�Œ�p�X��PDF�t�@�C�����J���܂��B
	 * 
	 * @throws Exception
	 *             ������O
	 */
	public static void openPDF() throws Exception {
		openPDF(IkenshoConstants.PRINT_PDF_PATH);
	}

	/**
	 * PDF�t�@�C���𐶐����A��������PDF�t�@�C�����J���܂��B
	 * 
	 * @param pd
	 *            ����f�[�^
	 * @throws Exception
	 */
	public static void openPDF(ACChotarouXMLWriter pd) throws Exception {
		// PDF�t�@�C������
		String pdfPath = writePDF(pd);

		// ��������PDF���J��
		if (pdfPath.length() > 0) {
			openPDF(pdfPath);
		}
	}

	/**
	 * �o�͂��ꂽ�C�Ӄp�X��PDF�t�@�C�����J���܂��B
	 * 
	 * @param pdfPath
	 *            PDF�t�@�C���̃p�X
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
			// Mac�Ȃ�Ί֘A�t���ŊJ��
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
				// �A�N���o�b�g�����݂��Ȃ�
				ACMessageBox.showExclamation("PDF�̐ݒ肪����������܂���B" + ACConstants.LINE_SEPARATOR + "[���C�����j���[]-[�ݒ�(S)]-[PDF�ݒ�(P)]���Acrobat�̐ݒ���s���Ă��������B");
				return;
			}
		}
		Runtime.getRuntime().exec(arg);
	}

	/**
	 * �v���p�e�B�t�@�C������l���擾���܂��B
	 * 
	 * @param path
	 *            �L�[
	 * @throws Exception
	 *             ������O
	 * @return �L�[�ɑΉ�����l
	 */
	public static String getProperity(String path) throws Exception {
		return ACFrame.getInstance().getProperty(path);
	}

	/**
	 * 2����؂�̌����N������XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param head
	 *            �O���b�h�̐ړ���
	 * @param wPos
	 *            �O���b�h��w�����C���f�b�N�X
	 * @param step
	 *            ����
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
	 */
	public static boolean addEraDate(ACChotarouXMLWriter pd, VRMap data, String key, String head, int wPos, int step) throws Exception {
		return addEraDate(pd, data, key, head, wPos, step, "��");
	}

	/**
	 * 2����؂�̌����N������XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param head
	 *            �O���b�h�̐ړ���
	 * @param wPos
	 *            �O���b�h��w�����C���f�b�N�X
	 * @param step
	 *            ����
	 * @param dateText
	 *            ���t�P�ʕ�����
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
	 */
	public static boolean addEraDate(ACChotarouXMLWriter pd, VRMap data, String key, String head, int wPos, int step, String dateText) throws Exception {
		// �L����
		Object obj = VRBindPathParser.get(key, data);
		if (obj instanceof Date) {
			Date date = (Date) obj;
			addString(pd, head + wPos, VRDateParser.format(date, "ggg"));
			// �N
			addString(pd, head + (wPos + step), VRDateParser.format(date, "ee"));
			addString(pd, head + (wPos + step * 2), "�N");
			// ��
			addString(pd, head + (wPos + step * 3), VRDateParser.format(date, "MM"));
			addString(pd, head + (wPos + step * 4), "��");
			// ��
			addString(pd, head + (wPos + step * 5), VRDateParser.format(date, "dd"));
			addString(pd, head + (wPos + step * 6), dateText);
			return true;
		}
		return false;
	}

	/**
	 * �d�b�ԍ���XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key1
	 *            �f�[�^�L�[1
	 * @param key2
	 *            �f�[�^�L�[1
	 * @param target
	 *            �O���b�h��
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
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
	 * �d�b�ԍ���n�Ԃ�()�ŕ�������XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key1
	 *            �f�[�^�L�[1
	 * @param key2
	 *            �f�[�^�L�[1
	 * @param target1
	 *            �O���b�h�ʒu1
	 * @param target2
	 *            �O���b�h�ʒu2
	 * @param target3
	 *            �O���b�h�ʒu3
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
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
	 * �X�֔ԍ���-�ŕ�������XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param target1
	 *            �O���b�h�ʒu1
	 * @param target2
	 *            �O���b�h�ʒu2
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
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
	 * �I���^���ڂ�XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param shaps
	 *            Visible����ΏیQ
	 * @param offset
	 *            �l�Ɣz��Y�����Ƃ̃I�t�Z�b�g
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
	 */
	public static boolean addSelection(ACChotarouXMLWriter pd, VRMap data, String key, String[] shaps, int offset) throws Exception {
		Object obj = VRBindPathParser.get(key, data);
		if (!(obj instanceof Integer)) {
			return false;
		}

		int end = shaps.length;
		int pos = ((Integer) obj).intValue() + offset;
		if ((pos < 0) || (pos >= end)) {
			// ���ׂ�false
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
	 * �I���^���ڂ�XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param shaps
	 *            Visible����ΏیQ
	 * @param offset
	 *            �l�Ɣz��Y�����Ƃ̃I�t�Z�b�g
	 * @param optionKey
	 *            �A�����ďo�͂��镶����L�[
	 * @param optionTarget
	 *            �A�����ďo�͂��镶����o�͈ʒu
	 * @param useOptionIndex
	 *            �A�����ďo�͂��镶����̏o�͏����ƂȂ�I��ԍ�
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
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
			// ���ׂ�false
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
	 * �������ڂ�XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param target
	 *            �^�O��
	 * @param value
	 *            �o�͒l
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
	 */
	public static boolean addString(ACChotarouXMLWriter pd, String target, Object value) throws Exception {
		return ACCommon.getInstance().setValue(pd, target, value);
	}

	/**
	 * �������ڂ�XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param target
	 *            �^�O��
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
	 */
	public static boolean addString(ACChotarouXMLWriter pd, VRMap data, String key, String target) throws Exception {
		return ACCommon.getInstance().setValue(pd, data, key, target);
	}

	/**
	 * �������ڂ�XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param target
	 *            �^�O��
	 * @param head
	 *            �������ڒl�̑O�ɘA�����ďo�͂��镶����
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
	 */
	public static boolean addString(ACChotarouXMLWriter pd, VRMap data, String key, String target, String head) throws Exception {
		return ACCommon.getInstance().setValue(pd, data, key, target, head);
	}

	/**
	 * �`�F�b�N���ڂ�XML�o�͂��܂��B
	 * 
	 * @param pd
	 *            �o�̓N���X
	 * @param data
	 *            �f�[�^�\�[�X
	 * @param key
	 *            �f�[�^�L�[
	 * @param target
	 *            �^�O��
	 * @param checkValue
	 *            �`�F�b�N�Ƃ݂Ȃ��l
	 * @throws Exception
	 *             ������O
	 * @return �o�͂�����
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
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followTextKeys
	 *            ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
	 */
	public static void addFollowCheckTextUpdate(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, boolean addCheckValue) throws ParseException {
		addFollowCheckTextUpdate(sb, map, checkNumberKey, followTextKeys, 1, addCheckValue);
	}

	/**
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followTextKeys
	 *            ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param selectedIndex
	 *            �I�������Ƃ݂Ȃ��`�F�b�N���ڒl
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
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
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followTextKeys
	 *            ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param followNumberKeys
	 *            ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param textToNumber
	 *            �e�L�X�g�o�͂��悩
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
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
				// �����o�͂���
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
				// ���l�o�͂���
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
				// �����o�͂���
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
				// ���l�o�͂���
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
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followNumberKeys
	 *            ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
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
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followTextKeys
	 *            ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
	 */
	public static void addFollowCheckTextInsert(StringBuffer sb, VRMap map, String checkNumberKey, String[] followTextKeys, boolean addCheckValue) throws ParseException {
		addFollowCheckTextInsert(sb, map, checkNumberKey, followTextKeys, 1, addCheckValue);
	}

	/**
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followTextKeys
	 *            ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param selectedIndex
	 *            �I�������Ƃ݂Ȃ��`�F�b�N���ڒl
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
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
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followTextKeys
	 *            ������^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param followNumberKeys
	 *            ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param textToNumber
	 *            �e�L�X�g�o�͂��悩
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
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
				// �����o�͂���
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
				// ���l�o�͂���
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
				// �����o�͂���
				end = followTextKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",''");
				}
				end = followNumberKeys.length;
				for (int i = 0; i < end; i++) {
					sb.append(",0");
				}
			} else {
				// ���l�o�͂���
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
	 * �`�F�b�N�l�ƘA�����ďo�͗L����؂�ւ��ďo�͂��܂��B
	 * 
	 * @param sb
	 *            �ǉ���o�b�t�@
	 * @param map
	 *            �l�擾��
	 * @param checkNumberKey
	 *            ���l�^�`�F�b�N���ڃL�[
	 * @param followNumberKeys
	 *            ���l�^�`�F�b�N�A���e�L�X�g���ڃL�[�W��
	 * @param addCheckValue
	 *            �`�F�b�N���ڂ��o�͂��邩
	 * @throws ParseException
	 *             ��͗�O
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
	 * �w��L�[�t�B�[���h����v����s�}�b�v��Ԃ��܂��B
	 * 
	 * @param array
	 *            �f�[�^�W��
	 * @param fieldName
	 *            ��r�t�B�[���h��
	 * @param source
	 *            ��r���\�[�X
	 * @throws ParseException
	 *             ��͗�O
	 * @return �w��L�[�t�B�[���h����v����s�}�b�v
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
	 * �w��t�B�[���h�ƈ�v����C���f�b�N�X�f�[�^���R���{�ɐݒ肵�܂��B
	 * 
	 * @param array
	 *            �R���{�Ɋ֘A�t����ꂽ�f�[�^�W��
	 * @param field
	 *            �`�F�b�N�t�B�[���h��
	 * @param source
	 *            ���݂̃f�[�^��
	 * @param combo
	 *            �R���{
	 * @throws ParseException
	 *             ��͗�O
	 * @return �Y������f�[�^�������������B
	 * @see �\���f�[�^�����j�[�N�L�[�ł͂Ȃ��A���f�[�^�ƕ\���f�[�^����v���Ȃ��R���{�̃f�[�^���蓖�ĂɎg�p���܂��B
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
	 * �ŐV�̏���ŏ����擾���܂��B
	 * 
	 * @param dbm
	 *            DBManager
	 * @param map
	 *            �擾���ǉ���
	 * @throws SQLException
	 *             SQL��O
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
	 * �}�X�^�f�[�^���ꊇ���Ď擾���܂��B
	 * 
	 * @param dbm
	 *            DBManager
	 * @param dest
	 *            ���ʑ����
	 * @param nameField
	 *            �Ώۂ̃t�B�[���h��
	 * @param table
	 *            �Ώۂ̃e�[�u����
	 * @param codeField
	 *            WHERE��ɗp����t�B�[���h��
	 * @param orderField
	 *            ORDER BY��ɗp����t�B�[���h��
	 * @throws SQLException
	 *             SQL��O
	 * @throws ParseException
	 *             ��͗�O
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
	 * �ی��҂���ш�Ë@�ւ̓o�^�󋵂�Ԃ��܂��B
	 * 
	 * @throws Exception
	 *             ������O
	 * @return int �ی��҂���ш�Ë@�ւ̓o�^��(�r�b�g�t���O):0=�Ƃ���1���ȏ㑶�݁A1=�ی��҂�0���A2=��Ë@�ւ�0��
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
					// �ی��ґI���ς݂��������o�̓p�^�[����0���ȍ~����Ő����f�[�^���s��
					return true;
				}
			}
			// �ӌ����Ŏg�p������Ë@�ւ��폜����Ɖ��L�ɊY������̂ŃR�����g�A�E�g
			// obj = map.getData("DR_NM");
			// if ((obj != null) && (!"".equals(obj))) {
			// obj = map.getData("DR_NO");
			// if ((obj == null) || "".equals(String.valueOf(obj))) {
			// // ��Җ����݂���Ҕԍ����Ȃ����ȍ~����Ő����f�[�^���s��
			// return true;
			// }
			// }
		}

		return false;
	}
	// 2006/12/11[Tozo Tanaka] : add end


    /**
     * �}�b�v���̕ی��Җ��ƕی��ҋ敪�����������t�B�[���h��ǉ����܂��B
     * @param insurerData �ی��҈ꗗ
     * @param nameTypeBindPath �ǉ��t�B�[���h��
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
                row.setData(nameTypeBindPath, insureName + "(�厡��ӌ����̂�)");
                break;
            case 2:
                row.setData(nameTypeBindPath, insureName + "(��t�ӌ����̂�)");
                break;
            default:
                row.setData(nameTypeBindPath, insureName);
                break;
            }
        }
    }
    
    //2006/09/07 [Tozo Tanaka] : add begin
    /**
     * �d�q�����Z�����Z���Ă悢����Ԃ��܂��B
     * @param formatKubun �����敪
     * @param doctorAddIT ��Ë@�ւ̓d�q�����Z�敪
     * @param insurer �ی���
     * @return �d�q�����Z�����Z���Ă悢��
     * @throws ParseException ������O
     */
    public static boolean canAddIT(int formatKubun, boolean isDoctorAddIT, VRMap insurer) throws ParseException{
        return canAddIT(formatKubun, isDoctorAddIT, ACCastUtilities.toInt(VRBindPathParser.get(
                        "SHOSIN_ADD_IT_TYPE", insurer), 1));
//        if(isDoctorAddIT){
//            //�d�q�����Z����̈�Ë@��
//            switch(formatKubun){
//            case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
//                //��t�ӌ����̏ꍇ�A�K���d�q�����Z�ΏۂƂ���B
//                return true;
//            default:
//                //�厡��ӌ����̏ꍇ�A�d�q�����Z�敪��0�̂Ƃ��̂݁A�d�q�����Z�ΏۂƂ���B
//                return ACCastUtilities.toInt(VRBindPathParser.get(
//                        "SHOSIN_ADD_IT_TYPE", insurer), 1) == 0;
//            }
//        }
//        return false;
    }
    /**
     * �d�q�����Z�����Z���Ă悢����Ԃ��܂��B
     * @param formatKubun �����敪
     * @param doctorAddIT ��Ë@�ւ̓d�q�����Z�敪
     * @param insurerAddITType �ی��҂̓d�q�����Z�敪
     * @return �d�q�����Z�����Z���Ă悢��
     * @throws ParseException ������O
     */
    public static boolean canAddIT(int formatKubun, boolean isDoctorAddIT,
            int insurerAddITType) throws ParseException {
        if(isDoctorAddIT){
            //�d�q�����Z����̈�Ë@��
            switch(formatKubun){
            case IkenshoConstants.IKENSHO_LOW_ISHI_IKENSHO:
                //��t�ӌ����̏ꍇ�A�K���d�q�����Z�ΏۂƂ���B
                return true;
            default:
                //�厡��ӌ����̏ꍇ�A�d�q�����Z�敪��0�̂Ƃ��̂݁A�d�q�����Z�ΏۂƂ���B
                return insurerAddITType == 0;
            }
        }
        return false;
    }
    //2006/09/07 [Tozo Tanaka] : add end
}
