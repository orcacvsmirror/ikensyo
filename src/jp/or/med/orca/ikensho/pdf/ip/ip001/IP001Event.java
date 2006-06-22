
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
 * �J����: �c���@����
 * �쐬��: 2006/05/01  ���{�R���s���[�^�[������� �c���@���� �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���[ (P)
 * �v���Z�X �ȈՒ��[�J�X�^�}�C�Y�c�[�� (001)
 * �v���O���� �ȈՒ��[�J�X�^�}�C�Y�c�[�� (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRHashMap;
import jp.nichicom.vr.util.VRList;
import jp.nichicom.vr.util.VRMap;

import org.w3c.dom.Node;

/**
 * �ȈՒ��[�J�X�^�}�C�Y�c�[���C�x���g��`(IP001) 
 */
public abstract class IP001Event extends IP001State {
  /**
   * �R���X�g���N�^�ł��B
   */
  public IP001Event(){
    addEvents();
  }
  /**
   * �C�x���g�����������`���܂��B
   */
  protected void addEvents() {
    getOpen().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                openActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getPrint().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                printActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getSave().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                saveActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
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
    getFormatX().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatXCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatY().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatYCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatWidth().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatWidthCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatHeight().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatHeightCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatSize().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatSizeCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatBorderWidth().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatBorderWidthCellEditing(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getFormatText().addCellEditorListener(new CellEditorListener(){
        private boolean lockFlag = false;
        public void editingStopped(ChangeEvent e) {
          cellEditing(e);
        }
        public void editingCanceled(ChangeEvent e) {
          cellEditing(e);
        }
        public void cellEditing(ChangeEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                formatTextCellEditing(e);
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
   * �u���[���J���v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void openActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u����v���r���[�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void printActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u���[��ۑ��v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void saveActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u�I���v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void closeActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatXCellEditing(ChangeEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatYCellEditing(ChangeEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatWidthCellEditing(ChangeEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatHeightCellEditing(ChangeEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatSizeCellEditing(ChangeEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatBorderWidthCellEditing(ChangeEvent e) throws Exception;

  /**
   * �u�Z���ύX�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void formatTextCellEditing(ChangeEvent e) throws Exception;

  //�ϐ���`

  private VRMap idReplaceMap = new VRHashMap();
  private VRMap formatNodes = new VRHashMap();
  private org.w3c.dom.Document opendDocument;
  private File opendFile;
  private boolean modified;
  private VRList formatElements = new VRArrayList();
  //getter/setter

  /**
   * idReplaceMap��Ԃ��܂��B
   * @return idReplaceMap
   */
  protected VRMap getIdReplaceMap(){
    return idReplaceMap;
  }
  /**
   * idReplaceMap��ݒ肵�܂��B
   * @param idReplaceMap idReplaceMap
   */
  protected void setIdReplaceMap(VRMap idReplaceMap){
    this.idReplaceMap = idReplaceMap;
  }

  /**
   * formatNodes��Ԃ��܂��B
   * @return formatNodes
   */
  protected VRMap getFormatNodes(){
    return formatNodes;
  }
  /**
   * formatNodes��ݒ肵�܂��B
   * @param formatNodes formatNodes
   */
  protected void setFormatNodes(VRMap formatNodes){
    this.formatNodes = formatNodes;
  }

  /**
   * opendDocument��Ԃ��܂��B
   * @return opendDocument
   */
  protected org.w3c.dom.Document getOpendDocument(){
    return opendDocument;
  }
  /**
   * opendDocument��ݒ肵�܂��B
   * @param opendDocument opendDocument
   */
  protected void setOpendDocument(org.w3c.dom.Document opendDocument){
    this.opendDocument = opendDocument;
  }

  /**
   * opendFile��Ԃ��܂��B
   * @return opendFile
   */
  protected File getOpendFile(){
    return opendFile;
  }
  /**
   * opendFile��ݒ肵�܂��B
   * @param opendFile opendFile
   */
  protected void setOpendFile(File opendFile){
    this.opendFile = opendFile;
  }

  /**
   * modified��Ԃ��܂��B
   * @return modified
   */
  protected boolean getModified(){
    return modified;
  }
  /**
   * modified��ݒ肵�܂��B
   * @param modified modified
   */
  protected void setModified(boolean modified){
    this.modified = modified;
  }

  /**
   * formatElements��Ԃ��܂��B
   * @return formatElements
   */
  protected VRList getFormatElements(){
    return formatElements;
  }
  /**
   * formatElements��ݒ肵�܂��B
   * @param formatElements formatElements
   */
  protected void setFormatElements(VRList formatElements){
    this.formatElements = formatElements;
  }

  //�����֐�

  /**
   * �u���[��`�̓ǂݍ��݁v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @throws Exception ������O
   *
   */
  public abstract void readFoamrt() throws Exception;

  /**
   * �u�q�m�[�h�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param node Node
   * @param childName String
   * @throws Exception ������O
   * @return Node
   */
  public abstract Node getChildNode(Node node, String childName) throws Exception;

  /**
   * �u�q�m�[�h�̃e�L�X�g�v�f�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param node Node
   * @param childName String
   * @throws Exception ������O
   * @return String
   */
  public abstract String getChildNodeText(Node node, String childName) throws Exception;

  /**
   * �u�m�[�h�̃e�L�X�g�v�f�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param node Node
   * @throws Exception ������O
   * @return String
   */
  public abstract String getNodeText(Node node) throws Exception;

  /**
   * �u�t�H���g�T�C�Y�m�[�h�擾�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param item Node
   * @throws Exception ������O
   * @return String
   */
  public abstract String getNodeFontSize(Node item) throws Exception;

  /**
   * �u�t�H�[�}�b�g�ۑ��v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param outputFile File
   * @throws Exception ������O
   *
   */
  public abstract void saveFormat(File outputFile) throws Exception;

  /**
   * �u�Z���ύX�v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @throws Exception ������O
   *
   */
  public abstract void cellChanged() throws Exception;

}
