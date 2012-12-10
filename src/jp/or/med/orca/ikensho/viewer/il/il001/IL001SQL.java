
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
 * �J����: ���O�r���[�A�[
 * �쐬��: 2012/09/20  ���{�R���s���[�^�[������� ���O�r���[�A�[ �V�K�쐬
 * �X�V��: ----/--/--
 * �V�X�e�� �厡��ӌ��� (I)
 * �T�u�V�X�e�� ���O (L)
 * �v���Z�X ���O�r���[�A�[ (001)
 * �v���O���� ���O�r���[�A (IL001)
 *
 *****************************************************************
 */
package jp.or.med.orca.ikensho.viewer.il.il001;
import jp.nichicom.ac.text.ACSQLSafeIntegerFormat;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.util.VRMap;

/**
 * ���O�r���[�ASQL��`(IL001) 
 */
public class IL001SQL extends IL001State {
	private static final long serialVersionUID = 1L;
	
  /**
   * �R���X�g���N�^�ł��B
   */
  public IL001SQL() {
  }

  /**
   * �u���ҏ����擾����v�̂��߂�SQL��Ԃ��܂��B
   * @param sqlParam SQL�\�z�ɕK�v�ȃp�����^���i�[�����n�b�V���}�b�v
   * @throws Exception ������O
   * @return SQL��
   */
  public String getSQL_GET_PATIENT_INFO(VRMap sqlParam) throws Exception{
    StringBuffer sb = new StringBuffer();

    sb.append("SELECT");

    sb.append(" PATIENT_NM");

    sb.append(",PATIENT_KN");

    sb.append(" FROM");

    sb.append(" PATIENT");

    sb.append(" WHERE");

    sb.append("(");

    sb.append(" PATIENT_NO");

    sb.append(" =");

    sb.append(ACSQLSafeIntegerFormat.getInstance().format(VRBindPathParser.get("PATIENT_NO", sqlParam)));

    sb.append(")");

    return sb.toString();
  }

}
