/*
 * Project code name "ORCA"
 * ���t�Ǘ��䒠�\�t�g QKANCHO�iJMA care benefit management software�j
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "QKANCHO (JMA care benefit management software)".
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
 * �A�v��: QKANCHO
 * �J����: �����@�L
 * �쐬��: 2012/09/20  ���{�R���s���[�^�[������� �����@�L �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���O (L)
 * �v���Z�X ���O�r���[�A (001)
 * �v���O���� ���O�r���[�A (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.lang.ACCastUtilities;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.ac.util.adapter.ACTableModelAdapter;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.nichicom.vr.util.logging.VRLogger;
import jp.or.med.orca.ikensho.affair.IkenshoBrowseLogger;
import jp.or.med.orca.ikensho.affair.IkenshoBrowseLoggerFormatter;
import jp.or.med.orca.ikensho.affair.IkenshoFrameEventProcesser;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * ���O�r���[�A(IL001)
 */
public class IL001 extends IL001Event {
	
	private static final long serialVersionUID = 1L;
	/**
	 * �R���X�g���N�^�ł��B
	 */
	public IL001() {
	}

	public void initAffair(ACAffairInfo affair) throws Exception {
		super.initAffair(affair);
		initAction(affair);
	}

	/**
	 * �������������s�Ȃ��܂��B
	 * 
	 * @param affair
	 *            �Ɩ����
	 * @throws Exception
	 *             ������O
	 */
	protected void initAction(ACAffairInfo affair) throws Exception {
		
		//�f�[�^�x�[�X�ڑ��I�u�W�F�N�g��ݒ�
		setDBManager(new IkenshoFirebirdDBManager());
		
		// DB�ڑ��m�F DB�ڑ��\���ǂ���
        if (canConnect()) {
        	//DB�ڑ����\�ł���΁AcanDBConnect��true
            setCanDBConnect(true);
            
        	//canDBConnect��true�̏ꍇ�A�f�[�^�x�[�X����Ɩ����̂��擾����
        	initAffairNameMap();
        	
        } else {
        	//�s�\�ł����canDBConnect��false
            setCanDBConnect(false);
        }
        
		// ���O�t�@�C���̃��X�g���擾����
        initLogFileNameList();
        
		// ���O�t�@�C�������݂��Ȃ��ꍇ
        if (getLogFileList().getDataSize() == 0) {
        	// �{�^���������Ȃ���ԂɕύX
        	setState_MOVE_NONE();
        	
        	// ���O�t�@�C�������݂��Ȃ����b�Z�[�W��\�����A�����𒆒f����B
        	ACMessageBox.show(MESSAGE_LOG_NOT_FOUND, ACMessageBox.BUTTON_OK , ACMessageBox.ICON_INFOMATION);
        	return;
        }
        
        //readLogFileNumber��0�ɂ���B
        setReadLogFileNumber(0);
        
		// ���O�t�@�C�����̕\����ύX����
        getHeaderMap().setData(FILE_COUNT, ACCastUtilities.toString(getLogFileList().getDataSize()));
        
		// ��ʏ�̃��O�t�@�C��������L�����Ŏ擾�������ōX�V����B
        getFileInfoGroup().setSource(getHeaderMap());
        getFileInfoGroup().bindSource();
        
        // ���O�\���e�[�u���̏�����
        String[] logDataTableSchema = new String[] {
    		LOG_DATETIME,
    		USER_NAME,
    		AFFAIR_ID,
    		AFFAIR_NAME,
    		PATIENT_NAME,
    		PATIENT_NAME_KANA
        };

        // �i�[
        ACTableModelAdapter logDataTableModel = new ACTableModelAdapter();
        logDataTableModel.setColumns(logDataTableSchema);

        // csvDataTable��csvDataTableModel���Z�b�g����
        getLogDataTable().setModel(logDataTableModel);

        // ���f���Ƀf�[�^���Z�b�g����
        logDataTableModel.setAdaptee(getLogFileContentList());
        
		// �e�[�u���փ��O�t�@�C���̕\�����s���B
		readLogFile();

	}

	public boolean canClose() throws Exception {
		if (!super.canClose()) {
			return false;
		}
        // ���I������
        // �I���m�F�̃��b�Z�[�W��\������BCSV�r���[�A���I�����Ă���낵���ł����H
        if (ACMessageBox.showOkCancel(MESSAGE_PROGRAM_EXIT) == ACMessageBox.RESULT_OK) {
            // �uOK�v�I����
        	//�f�[�^�x�[�X�ڑ��I��
        	if (getCanDBConnect()) {
        		getDBManager().releaseAll();
        	}
            // �v���O�������I������B�i��Ղ�true��Ԃ��j
            return true;
        }
        // �u�L�����Z���v�I����
        // �����𒆒f����B(��Ղ�false��Ԃ�)
        return false;
	}

	// �R���|�[�l���g�C�x���g

	/**
	 * �u�ЂƂO�̃��O�t�@�C�����J���v�C�x���g�ł��B
	 * 
	 * @param e
	 *            �C�x���g���
	 * @throws Exception
	 *             ������O
	 */
	protected void prevButtonActionPerformed(ActionEvent e) throws Exception {
		// readLogFileNumber��-1����B
		setReadLogFileNumber(getReadLogFileNumber() - 1);
		
		// �e�[�u���փ��O�t�@�C���̕\�����s���B
		readLogFile();

	}

	/**
	 * �u���̃��O�t�@�C�����J���v�C�x���g�ł��B
	 * 
	 * @param e
	 *            �C�x���g���
	 * @throws Exception
	 *             ������O
	 */
	protected void nextButtonActionPerformed(ActionEvent e) throws Exception {
		// readLogFileNumber��+1����B
		setReadLogFileNumber(getReadLogFileNumber() + 1);
		
		// �e�[�u���փ��O�t�@�C���̕\�����s���B
		readLogFile();

	}

	// �����֐�

	/**
	 * �u���O�f�[�^�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
	 * 
	 * @throws Exception
	 *             ������O
	 */
	public void readLogFile() throws Exception {
		// logFileContentList�̓��e���N���A
		getLogFileContentList().clear();
		
		// logFileList����readLogFileNumber�Ԗڂ̃t�@�C�����̂��擾����B
		File log = (File)getLogFileList().getData(getReadLogFileNumber());
		
		BufferedReader br = null;
		String line = null;
		
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(log), "UTF-8"));
			
			String[] ary = null;
			int length = 0;
			while((line = br.readLine()) != null) {
				
				//��s���X�y�[�X�ŕ���
				ary = line.split(" ");
				
				//�ؒ萔�����̕����s�͕s���Ƃ݂Ȃ��X�L�b�v
				length = ary.length;
				if (length < 4) {
					continue;
				}
				
				//VRMap�I�u�W�F�N�g�𐶐�
				VRMap row = new VRHashMap();
				
				// Array[0] + " " + Array[1]���L�[LOG_DATETIME�ŁAVRMap�ɐݒ肷��B
				row.put(LOG_DATETIME, ary[0] + " " + ary[1]);
				// Array[2]���L�[USER_NAME�ŁAVRMap�ɐݒ肷��B
				row.put(USER_NAME, ary[2]);
				// Array[3]���L�[AFFAIR_ID�ŁAVRMap�ɐݒ肷��B
				row.put(AFFAIR_ID, ary[3]);
				
				// arrairMap����Y���̋Ɩ����̂��擾���A�L�[AFFAIR_NAME��VRMap�ɐݒ肷��B
				row.put(AFFAIR_NAME, getAffairMap().get(ary[3]));
				
				
				// canDBConnect��false�̏ꍇ�͏����I��
				if (!getCanDBConnect()) {
					continue;
				}
				
				// QkanBrowseLoggerFormatter.AFFAIR_PERSONAL��AFFAIR_ID�����݂���ꍇ(�l�����{������Ɩ��̏ꍇ))
				if (IkenshoBrowseLoggerFormatter.AFFAIR_PERSONAL.contains(ary[3])) {
					
					//���p�҂̏���ݒ肷��
					setUserInfo(row, ary[length - 1]);
					
				// QkanBrowseLoggerFormatter.AFFAIR_PERSONAL��AFFAIR_ID�����݂��Ȃ��ꍇ
				} else {
					// �󔒂��L�[PATIENT_NAME�ŁAVRMap�ɐݒ肷��B
					row.put(PATIENT_NAME, "");
					// �󔒂��L�[INSURED_ID�ŁAVRMap�ɐݒ肷��B
					row.put(PATIENT_NAME_KANA, "");
				}
				
				//�쐬����VRMap��logFileContentList�֒ǉ�
				getLogFileContentList().add(row);
			}
			
			
		} catch (Exception e) {
			System.out.println(line);
			throw e;
		} finally {
			if (br != null) {
				br.close();
			}
		}
		
		// ���O�t�@�C�����̕\����ύX����
        getHeaderMap().setData(VIEW_FILE_NUMBER, ACCastUtilities.toString(getReadLogFileNumber() + 1) + "�Ԗ�");
        getFileInfoGroup().bindSource();

		// logFileList�̃T�C�Y��1�̏ꍇ
		if (getLogFileList().size() == 1) {
			// �{�^����ԕύX
			setState_MOVE_NONE();
			return;
		}
		
		// readLogFileNumber��0�̏ꍇ
		if (getReadLogFileNumber() == 0) {
			// �{�^����ԕύX
			setState_MOVE_NEXT_ONLY();
			return;
		}
		
		// readLogFileNumber��logFileList�̃T�C�Y-1�̏ꍇ
		if (getLogFileList().size() - 1 <= getReadLogFileNumber()) {
			// �{�^����ԕύX
			setState_MOVE_PREV_ONLY();
			return;
		}
		
		// �{�^����ԕύX
		setState_MOVE_ALL();

	}

	/**
	 * �u�Ɩ����̖|�󏉊����v�Ɋւ��鏈�����s�Ȃ��܂��B
	 * 
	 * @throws Exception
	 *             ������O
	 */
	public void initAffairNameMap() throws Exception {
		
		//�}�X�^���Ȃ��̂ŁA�蓮�őΉ��\���쐬
		getAffairMap().put("IkenshoPatientList", "�o�^���ҏ��ꗗ");
		getAffairMap().put("IkenshoPatientInfo", "���ҍŐV��{���");
		getAffairMap().put("IkenshoIkenshoInfoH18", "�厡��ӌ���");
		getAffairMap().put("IkenshoIshiIkenshoInfo", "��t�ӌ���");
		getAffairMap().put("IkenshoHoumonKangoShijisho", "�K��Ō�w����");
		getAffairMap().put("IkenshoTokubetsuHoumonKangoShijisho", "���ʖK��Ō�w����");
		getAffairMap().put("IkenshoSeikyuIchiran", "�����Ώۈӌ����ꗗ");
		getAffairMap().put("IkenshoRenkeiIJyouhouIchiran", "�A�g����ꗗ");
		getAffairMap().put("IkenshoRenkeiIJyouhouShousai", "�A�g��ڍ׏��");
		getAffairMap().put("IkenshoHoumonKangoStationJouhouIchiran", "�K��Ō�X�e�[�V�������ꗗ");
		getAffairMap().put("IkenshoHoumonKangoStationJouhouShousai", "�K��Ō�X�e�[�V�������ڍ�");
		getAffairMap().put("IkenshoHokenshaIchiran", "�ی��҈ꗗ");
		getAffairMap().put("IkenshoHokenshaShousai", "�ی��ҏڍ�");
		getAffairMap().put("IkenshoIryouKikanJouhouIchiran", "��Ë@�֏��ꗗ");
		getAffairMap().put("IkenshoIryouKikanJouhouShousai", "��Ë@�֏��ڍ�");
		getAffairMap().put("IkenshoTeikeibunList", "���L�����ꗗ");
		getAffairMap().put("IkenshoOtherCSVOutput", "�u�厡��ӌ����E��t�ӌ����vCSV�t�@�C���o��");
		getAffairMap().put("IkenshoReceiptSoftAccess", "���Z�v�g�\�t�g�A�g");
		
		getAffairMap().put("IL001", "���O�t�@�C���r���[�A�[");
	}

	/**
	 * �u���O�t�@�C���̖��̎擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
	 * 
	 * @throws Exception
	 *             ������O
	 */
	public void initLogFileNameList() throws Exception {
		// QkanBrowseLogger.LOG_FILE + ".[���l]"�`���ŕ����̃��O�t�@�C�����o�͂���Ă���
		// ���O�t�@�C���̖��̂����ׂĎ擾���AlogFileList�ɐݒ肷��B
		
		File baseLog = new File(IkenshoBrowseLogger.LOG_FILE);
		File logDir = baseLog.getParentFile();
		
		if (!logDir.isDirectory()) {
			VRLogger.warning("���O�o�̓t�H���_�̎擾�Ɏ��s���܂����B");
			return;
		}
		
		VRList list = new VRArrayList();
		String baseLogName = baseLog.getName();
		
		File f = null;
		for (int i = 0; i < logDir.listFiles().length; i++) {
			f = logDir.listFiles()[i];
			
			String fileName = f.getName();
			
			//�w��̃��O�t�@�C�����Ŏn�܂��Ă��邩
			if (!fileName.startsWith(baseLogName)) {
				continue;
			}
			
			//lck�t�@�C���͏��O����
			if (fileName.endsWith("lck")) {
				continue;
			}
			
			//�Y�����O�t�@�C����ޔ�
			list.add(f);
		}
		
		setLogFileList(list);
	}

	/**
	 * �u���[�U�[���̐ݒ�v�Ɋւ��鏈�����s�Ȃ��܂��B
	 * 
	 * @throws Exception
	 *             ������O
	 */
	public void setUserInfo(VRMap row, String patient_id) throws Exception {
		
		VRMap info = null;
		
		//patientMap�Ɋ��ɑޔ�����patient_id�̏�񂪂���ꍇ
		if (getPatientMap().containsKey(patient_id)) {
			//Map�̓��e��ݒ肷��
			info = (VRMap)getPatientMap().get(patient_id);
			row.putAll(info);
			return;
		}
		
		//patientMap�Ɋ��ɑޔ�����patient_id�̏�񂪂Ȃ��ꍇ
		//patient_id�����l�ł��邩�m�F�@���l�ł͖����ꍇ�͏����I��
		if (ACCastUtilities.toInt(patient_id, -1) == -1) {
			return;
		}
		
		
		//�l���擾�p��SQL�����쐬����
		VRMap param = new VRHashMap();
		param.put("PATIENT_NO", patient_id);
		
		//�쐬����SQL�������s����B
		VRList list = getDBManager().executeQuery(getSQL_GET_PATIENT_INFO(param));
		info = new VRHashMap();
		
		//�f�[�^���擾�ł��Ȃ��ꍇ�́A"�s��"��ݒ�
		if (list.size() == 0) {
			info.put(PATIENT_NAME, "�s��");
			info.put(PATIENT_NAME_KANA, "�s��");
			
		} else {
			VRMap map = (VRMap)list.get(0);
			info.put(PATIENT_NAME, map.get("PATIENT_NM"));
			info.put(PATIENT_NAME_KANA, map.get("PATIENT_KN"));
		}
		
		getPatientMap().put(patient_id, info);
		row.putAll(info);

	}
	
    private boolean canConnect() {
        try {
            // �ʐM�e�X�g
            if (getDBManager().isAvailableDBConnection()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return false;
    }

	public static void main(String[] args) {

		try {
			ACFrame.setVRLookAndFeel();
			ACFrameEventProcesser processer = new IkenshoFrameEventProcesser();
			ACFrame.getInstance().setFrameEventProcesser(processer);
			ACFrame.getInstance().next(new ACAffairInfo(IL001.class.getName()));
			ACFrame.getInstance().setTitle(MESSAGE_PROGRAM_TITLE);
			ACFrame.getInstance().setExtendedState(Frame.MAXIMIZED_BOTH);
			ACFrame.getInstance().setVisible(true);
		} catch (Exception ex) {
			VRLogger.info(ex);
		}
	}
}
