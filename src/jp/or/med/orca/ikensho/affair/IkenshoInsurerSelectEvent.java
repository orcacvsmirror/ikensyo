
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
 * �J����: ����@��F
 * �쐬��: 2009/07/09  ���{�R���s���[�^�[������� ����@��F �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� ���t�Ǘ��䒠 (Q)
 * �T�u�V�X�e�� �ی��ҊǗ� (O)
 * �v���Z�X �ی��ғo�^ (002)
 * �v���O���� �ی��҈ꗗ (IkenshoInsurerSelect)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.affair;
import java.awt.*;
import java.awt.event.*;
import java.awt.im.*;
import java.io.*;
import java.sql.SQLException;
import java.text.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;
import jp.nichicom.ac.*;
import jp.nichicom.ac.bind.*;
import jp.nichicom.ac.component.*;
import jp.nichicom.ac.component.dnd.*;
import jp.nichicom.ac.component.dnd.event.*;
import jp.nichicom.ac.component.event.*;
import jp.nichicom.ac.component.mainmenu.*;
import jp.nichicom.ac.component.table.*;
import jp.nichicom.ac.component.table.event.*;
import jp.nichicom.ac.container.*;
import jp.nichicom.ac.core.*;
import jp.nichicom.ac.filechooser.*;
import jp.nichicom.ac.io.*;
import jp.nichicom.ac.lang.*;
import jp.nichicom.ac.pdf.*;
import jp.nichicom.ac.sql.*;
import jp.nichicom.ac.text.*;
import jp.nichicom.ac.util.*;
import jp.nichicom.ac.util.adapter.*;
import jp.nichicom.vr.*;
import jp.nichicom.vr.bind.*;
import jp.nichicom.vr.bind.event.*;
import jp.nichicom.vr.border.*;
import jp.nichicom.vr.component.*;
import jp.nichicom.vr.component.event.*;
import jp.nichicom.vr.component.table.*;
import jp.nichicom.vr.container.*;
import jp.nichicom.vr.focus.*;
import jp.nichicom.vr.image.*;
import jp.nichicom.vr.io.*;
import jp.nichicom.vr.layout.*;
import jp.nichicom.vr.text.*;
import jp.nichicom.vr.text.parsers.*;
import jp.nichicom.vr.util.*;
import jp.nichicom.vr.util.adapter.*;
import jp.nichicom.vr.util.logging.*;

/**
 *****************************************************************
 * �A�v��: Ikensho
 * �J����: ����@��F
 * �쐬��: 2009  ���{�R���s���[�^�[������� ��� ��F �V�K�쐬
 * @since V3.0.9
 *
 *****************************************************************
 */
public abstract class IkenshoInsurerSelectEvent extends IkenshoInsurerSelectSQL {
  /**
   * �R���X�g���N�^�ł��B
   */
  public IkenshoInsurerSelectEvent(){
    addEvents();
  }
  /**
   * �C�x���g�����������`���܂��B
   */
  protected void addEvents() {
    getClose().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                closeActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getInsurerText().getDocument().addDocumentListener(new DocumentListener(){
        private boolean lockFlag = false;
        public void changedUpdate(DocumentEvent e) {
          textChanged(e);
        }
        public void insertUpdate(DocumentEvent e) {
          textChanged(e);
        }
        public void removeUpdate(DocumentEvent e) {
          textChanged(e);
        }
        public void textChanged(DocumentEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                insurerTextTextChanged(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getApply().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                applyActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getDetailsTable().addListSelectionListener(new ListSelectionListener(){
        private boolean lockFlag = false;
        public void valueChanged(ListSelectionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                detailsTableSelectionChanged(e);
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
   * �u����v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void closeActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u�e�L�X�g�ύX�ɔ��������v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void insurerTextTextChanged(DocumentEvent e) throws Exception;

  /**
   * �u���f�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void applyActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u�e�[�u���I�����v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void detailsTableSelectionChanged(ListSelectionEvent e) throws Exception;

  //�ϐ���`

  private VRList masterInsurerList = new VRArrayList();
  private VRMap selectInsurerData = new VRHashMap();
  private ACDBManager insurerDBManager;
  private ACTableModelAdapter insurerTableModel;
  private String beforeFindKey;
  //getter/setter

  /**
   * masterInsurerList��Ԃ��܂��B
   * @return masterInsurerList
   */
  protected VRList getMasterInsurerList(){
    return masterInsurerList;
  }
  /**
   * masterInsurerList��ݒ肵�܂��B
   * @param masterInsurerList masterInsurerList
   */
  protected void setMasterInsurerList(VRList masterInsurerList){
    this.masterInsurerList = masterInsurerList;
  }

  /**
   * selectInsurerData��Ԃ��܂��B
   * @return selectInsurerData
   */
  protected VRMap getSelectInsurerData(){
    return selectInsurerData;
  }
  /**
   * selectInsurerData��ݒ肵�܂��B
   * @param selectInsurerData selectInsurerData
   */
  protected void setSelectInsurerData(VRMap selectInsurerData){
    this.selectInsurerData = selectInsurerData;
  }

  /**
   * insurerDBManager��Ԃ��܂��B
   * @return insurerDBManager
   */
  protected ACDBManager getInsurerDBManager(){
    return insurerDBManager;
  }
  /**
   * insurerDBManager��ݒ肵�܂��B
   * @param insurerDBManager insurerDBManager
   */
  protected void setInsurerDBManager(ACDBManager insurerDBManager){
    this.insurerDBManager = insurerDBManager;
  }

  /**
   * insurerTableModel��Ԃ��܂��B
   * @return insurerTableModel
   */
  protected ACTableModelAdapter getInsurerTableModel(){
    return insurerTableModel;
  }
  /**
   * insurerTableModel��ݒ肵�܂��B
   * @param insurerTableModel insurerTableModel
   */
  protected void setInsurerTableModel(ACTableModelAdapter insurerTableModel){
    this.insurerTableModel = insurerTableModel;
  }

  /**
   * beforeFindKey��Ԃ��܂��B
   * @return beforeFindKey
   */
  protected String getBeforeFindKey(){
    return beforeFindKey;
  }
  /**
   * beforeFindKey��ݒ肵�܂��B
   * @param beforeFindKey beforeFindKey
   */
  protected void setBeforeFindKey(String beforeFindKey){
    this.beforeFindKey = beforeFindKey;
  }

  //�����֐�

  /**
   * �u�����ݒ�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param dbm ACDBManager
   * @throws Exception ������O
   * @return VRMap
   */
  public abstract VRMap showModal(ACDBManager dbm) throws Exception;

  /**
   * �u�f�[�^�����v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @throws Exception ������O
   *
   */
  public abstract void findData() throws Exception;

}
