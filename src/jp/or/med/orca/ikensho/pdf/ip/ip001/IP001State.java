
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
 * �v���O���� �ȈՒ��[�J�X�^�}�C�Y�c�[�� (IP001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.pdf.ip.ip001;
/**
 * �ȈՒ��[�J�X�^�}�C�Y�c�[����Ԓ�`(IP001) 
 */
public class IP001State extends IP001Design {
  /**
   * �R���X�g���N�^�ł��B
   */
  public IP001State(){
  }

  /**
   * �u�t�@�C���ǂݍ��ݍς݁v�̏�Ԃɐݒ肵�܂��B
   * @throws Exception ������O
   */
  public void setState_FILE_OPENED() throws Exception {

        getPrint().setEnabled(true);

        getSave().setEnabled(true);

  }

  /**
   * �u�t�@�C���ǂݍ��ݑ҂��v�̏�Ԃɐݒ肵�܂��B
   * @throws Exception ������O
   */
  public void setState_FILE_CLOSED() throws Exception {

        getPrint().setEnabled(false);

        getSave().setEnabled(false);

  }

}
