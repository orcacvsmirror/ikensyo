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
 * �J����: �琐��
 * �쐬��: 2005/12/01  ���{�R���s���[�^������� �琐�� �V�K�쐬
 * �X�V��: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;

/** TODO <HEAD_IKENSYO> */
public class IkenshoShinryoujyoTypeFormat extends Format {
  protected static IkenshoShinryoujyoTypeFormat singleton;
  protected static final Integer CONST_1 = new Integer(1);
  protected static final Integer CONST_2 = new Integer(2);
  protected static final Integer CONST_3 = new Integer(3);

  /**
   * �R���X�g���N�^�ł��B<br />
   * Singleton Pattern
   */
  protected IkenshoShinryoujyoTypeFormat(){
    super();
  }
  /**
   * �C���X�^���X���擾���܂��B
   * @return �C���X�^���X
   */
  public static IkenshoShinryoujyoTypeFormat getInstance(){
    if(singleton==null){
      singleton = new IkenshoShinryoujyoTypeFormat();
    }
    return singleton;
  }
  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    if (CONST_1.equals(obj)) {
      toAppendTo.append("�f�Ï�");
    }
    else if (CONST_2.equals(obj)) {
      toAppendTo.append("�a�@");
    }
    else if (CONST_3.equals(obj)) {
      toAppendTo.append("���̑��̎{��");
    }
    return toAppendTo;
  }
  public Object parseObject(String source, ParsePosition pos) {
    if("�f�Ï�".equals(source)){
      return new Integer(1);
    }else if("�a�@".equals(source)){
      return new Integer(2);
    }else if("���̑��̈�Î{��".equals(source)){
      return new Integer(3);
    }else{
      return new Integer(0);
    }
  }

}
