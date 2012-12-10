
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
/**
 * ���O�r���[�A��Ԓ�`(IL001) 
 */
public class IL001State extends IL001Design {
	
	private static final long serialVersionUID = 1L;

/**
   * �R���X�g���N�^�ł��B
   */
  public IL001State(){
  }

  /**
   * �u�o���������v�̏�Ԃɐݒ肵�܂��B
   * @throws Exception ������O
   */
  public void setState_MOVE_NONE() throws Exception {

        getPrevButton().setEnabled(false);

        getNextButton().setEnabled(false);

  }

  /**
   * �u���ւ̂ݗL���v�̏�Ԃɐݒ肵�܂��B
   * @throws Exception ������O
   */
  public void setState_MOVE_NEXT_ONLY() throws Exception {

        getPrevButton().setEnabled(false);

        getNextButton().setEnabled(true);

  }

  /**
   * �u�߂�̂ݗL���v�̏�Ԃɐݒ肵�܂��B
   * @throws Exception ������O
   */
  public void setState_MOVE_PREV_ONLY() throws Exception {

        getPrevButton().setEnabled(true);

        getNextButton().setEnabled(false);

  }

  /**
   * �u�o�����L���v�̏�Ԃɐݒ肵�܂��B
   * @throws Exception ������O
   */
  public void setState_MOVE_ALL() throws Exception {

        getPrevButton().setEnabled(true);

        getNextButton().setEnabled(true);

  }

}
