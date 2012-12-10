
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/**
 * ���O�r���[�A�C�x���g��`(IL001) 
 */
public abstract class IL001Event extends IL001SQL {
	private static final long serialVersionUID = 1L;
  /**
   * �R���X�g���N�^�ł��B
   */
  public IL001Event(){
    addEvents();
  }
  /**
   * �C�x���g�����������`���܂��B
   */
  protected void addEvents() {
    getPrevButton().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                prevButtonActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getNextButton().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                nextButtonActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });

  }
  //�R���|�[�l���g�C�x���g

  /**
   * �u�ЂƂO�̃��O�t�@�C�����J���v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void prevButtonActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u���̃��O�t�@�C�����J���v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void nextButtonActionPerformed(ActionEvent e) throws Exception;

  //�ϐ���`

  private VRMap headerMap = new VRHashMap();
  private VRMap patientMap = new VRHashMap();
  private VRMap affairMap = new VRHashMap();
  private VRList logFileList = new VRArrayList();
  private VRList logFileContentList = new VRArrayList();
  private boolean canDBConnect;
  private int readLogFileNumber;
  public static final String FILE_COUNT = "FILE_COUNT";
  public static final String VIEW_FILE_NUMBER = "VIEW_FILE_NUMBER";
  public static final String LOG_DATETIME = "LOG_DATETIME";
  public static final String USER_NAME = "USER_NAME";
  public static final String AFFAIR_ID = "AFFAIR_ID";
  public static final String AFFAIR_NAME = "AFFAIR_NAME";
  public static final String PATIENT_NAME = "PATIENT_NAME";
  public static final String PATIENT_NAME_KANA = "PATIENT_NAME_KANA";
  public static final String MESSAGE_PROGRAM_TITLE = "�㌩�� ���O�t�@�C���r���[�A";
  public static final String MESSAGE_PROGRAM_EXIT = "�㌩�� ���O�t�@�C���r���[�A���I�����Ă���낵���ł����H";
  public static final String MESSAGE_LOG_NOT_FOUND = "�\�����郍�O�t�@�C��������܂���B";
  private IkenshoFirebirdDBManager dbm;
  //getter/setter

  /**
   * headerMap��Ԃ��܂��B
   * @return headerMap
   */
  protected VRMap getHeaderMap(){
    return headerMap;
  }
  /**
   * headerMap��ݒ肵�܂��B
   * @param headerMap headerMap
   */
  protected void setHeaderMap(VRMap headerMap){
    this.headerMap = headerMap;
  }

  /**
   * patientMap��Ԃ��܂��B
   * @return patientMap
   */
  protected VRMap getPatientMap(){
    return patientMap;
  }
  /**
   * patientMap��ݒ肵�܂��B
   * @param patientMap patientMap
   */
  protected void setPatientMap(VRMap patientMap){
    this.patientMap = patientMap;
  }

  /**
   * affairMap��Ԃ��܂��B
   * @return affairMap
   */
  protected VRMap getAffairMap(){
    return affairMap;
  }
  /**
   * affairMap��ݒ肵�܂��B
   * @param affairMap affairMap
   */
  protected void setAffairMap(VRMap affairMap){
    this.affairMap = affairMap;
  }

  /**
   * logFileList��Ԃ��܂��B
   * @return logFileList
   */
  protected VRList getLogFileList(){
    return logFileList;
  }
  /**
   * logFileList��ݒ肵�܂��B
   * @param logFileList logFileList
   */
  protected void setLogFileList(VRList logFileList){
    this.logFileList = logFileList;
  }

  /**
   * logFileContentList��Ԃ��܂��B
   * @return logFileContentList
   */
  protected VRList getLogFileContentList(){
    return logFileContentList;
  }
  /**
   * logFileContentList��ݒ肵�܂��B
   * @param logFileContentList logFileContentList
   */
  protected void setLogFileContentList(VRList logFileContentList){
    this.logFileContentList = logFileContentList;
  }

  /**
   * canDBConnect��Ԃ��܂��B
   * @return canDBConnect
   */
  protected boolean getCanDBConnect(){
    return canDBConnect;
  }
  /**
   * canDBConnect��ݒ肵�܂��B
   * @param canDBConnect canDBConnect
   */
  protected void setCanDBConnect(boolean canDBConnect){
    this.canDBConnect = canDBConnect;
  }

  /**
   * readLogFileNumber��Ԃ��܂��B
   * @return readLogFileNumber
   */
  protected int getReadLogFileNumber(){
    return readLogFileNumber;
  }
  /**
   * readLogFileNumber��ݒ肵�܂��B
   * @param readLogFileNumber readLogFileNumber
   */
  protected void setReadLogFileNumber(int readLogFileNumber){
    this.readLogFileNumber = readLogFileNumber;
  }

  /**
   * IkenshoFirebirdDBManager��Ԃ��܂�
   * @return IkenshoFirebirdDBManager
   */
  protected IkenshoFirebirdDBManager getDBManager() {
	  return dbm;
  }
  
  /**
   * IkenshoFirebirdDBManager��ݒ肵�܂��B
   * @param IkenshoFirebirdDBManager dbm
   */
  protected void setDBManager(IkenshoFirebirdDBManager dbm ) {
	  this.dbm = dbm;
  }


  //�����֐�

  /**
   * �u���O�f�[�^�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @throws Exception ������O
   *
   */
  public abstract void readLogFile() throws Exception;

  /**
   * �u�Ɩ����̖|�󏉊����v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @throws Exception ������O
   *
   */
  public abstract void initAffairNameMap() throws Exception;

  /**
   * �u���O�t�@�C���̖��̎擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @throws Exception ������O
   *
   */
  public abstract void initLogFileNameList() throws Exception;

  /**
   * �u���[�U�[���̐ݒ�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param row VRMap
   * @param patient_id String
   * @throws Exception ������O
   *
   */
  public abstract void setUserInfo(VRMap row, String patient_id) throws Exception;


}
