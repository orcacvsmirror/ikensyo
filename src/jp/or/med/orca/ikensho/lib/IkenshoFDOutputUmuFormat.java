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
public class IkenshoFDOutputUmuFormat extends Format {
    protected static IkenshoFDOutputUmuFormat singleton;
    protected static final Integer CONST_0 = new Integer(0);
    protected static final Integer CONST_1 = new Integer(1);

    /**
     * �R���X�g���N�^�ł��B<br />
     * Singleton Pattern
     */
    protected IkenshoFDOutputUmuFormat(){
      super();
    }
    /**
     * �C���X�^���X���擾���܂��B
     */
    public static IkenshoFDOutputUmuFormat getInstance(){
      if(singleton==null){
        singleton = new IkenshoFDOutputUmuFormat();
      }
      return singleton;
    }

    public Object parseObject(String source, ParsePosition pos) {
        if("��".equals(source)){
          return new Integer(0);
        }else if("�L".equals(source)){
          return new Integer(1);
        }
        return null;
    }

    public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
        if(CONST_0.equals(obj)){
          toAppendTo.append("��");
        }else if(CONST_1.equals(obj)){
          toAppendTo.append("�L");
        }
        return toAppendTo;
    }

}
