
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
 * �쐬��: 2006/04/25  ���{�R���s���[�^�[������� �c���@���� �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���[ (P)
 * �v���Z�X �ȈՒ��[�J�X�^�}�C�Y�c�[�� (001)
 * �v���O���� ���[�I�� (IP001001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import jp.nichicom.ac.ACCommon;

/**
 * ���[�I���C�x���g��`(IP001001) 
 */
public abstract class IP001001Event extends IP001001Design {
  /**
   * �R���X�g���N�^�ł��B
   */
  public IP001001Event(){
    addEvents();
  }
  /**
   * �C�x���g�����������`���܂��B
   */
  protected void addEvents() {
    getOk().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                okActionPerformed(e);
            }catch(Throwable ex){
                ACCommon.getInstance().showExceptionMessage(ex);
            }finally{
                lockFlag = false;
            }
        }
    });
    getCancel().addActionListener(new ActionListener(){
        private boolean lockFlag = false;
        public void actionPerformed(ActionEvent e) {
            if (lockFlag) {
                return;
            }
            lockFlag = true;
            try {
                cancelActionPerformed(e);
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
   * �uOK�v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void okActionPerformed(ActionEvent e) throws Exception;

  /**
   * �u�L�����Z���v�C�x���g�ł��B
   * @param e �C�x���g���
   * @throws Exception ������O
   */
  protected abstract void cancelActionPerformed(ActionEvent e) throws Exception;

  //�ϐ���`

  private File selectedFile;
  //getter/setter

  /**
   * selectedFile��Ԃ��܂��B
   * @return selectedFile
   */
  protected File getSelectedFile(){
    return selectedFile;
  }
  /**
   * selectedFile��ݒ肵�܂��B
   * @param selectedFile selectedFile
   */
  protected void setSelectedFile(File selectedFile){
    this.selectedFile = selectedFile;
  }

  //�����֐�

  /**
   * �u���[�I�������v�Ɋւ��鏈�����s�Ȃ��܂��B
   *
   * @param openedFile File
   * @throws Exception ������O
   * @return File
   */
  public abstract File showModal(File openedFile) throws Exception;

}
