package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * �L�[�ƒl�̊֌W�ŕϊ����s�Ȃ��t�H�[�}�b�g�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see Format
 */

public class ACHashMapFormat extends Format {
  protected HashMap map;

  /**
   * �R���X�g���N�^�ł��B
   */
  public ACHashMapFormat(){
    super();
  }
  /**
   * �R���X�g���N�^�ł��B
   * @param keys �L�[(������)�W��
   * @param vals �l(Object)�W��
   */
  public ACHashMapFormat(Object[] keys, Object[] vals){
    super();
    HashMap map = new HashMap();
    int end = Math.min(keys.length, vals.length);
    for(int i=0; i<end; i++){
      map.put(keys[i], vals[i]);
    }
    setMap(map);
  }

  /**
   * �R���X�g���N�^�ł��B
   * @param map �ϊ��}�b�v
   */
  public ACHashMapFormat(HashMap map){
    super();
    setMap(map);
  }


  public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
    if(map==null){
      return toAppendTo;
    }

    if(obj!=null){
      Iterator it = map.entrySet().iterator();
      while (it.hasNext()) {
        Map.Entry ent = (Map.Entry) it.next();
        if(obj.equals(ent.getValue())){
          toAppendTo.append(String.valueOf(ent.getKey()));
          break;
        }
      }
    }
    return toAppendTo;
  }

  public Object parseObject(String source, ParsePosition pos) {
    if(map==null){
      return null;
    }
    return map.get(source);
  }

  /**
   * [key:������, value:Object]�`���̕ϊ��}�b�v��Ԃ��܂��B
   * @return �ϊ��}�b�v
   */
  public HashMap getMap() {
    return map;
  }
  /**
   * [key:������, value:Object]�`���̕ϊ��}�b�v��ݒ肵�܂��B
   * @param map �ϊ��}�b�v
   */
  public void setMap(HashMap map) {
    this.map = map;
  }

}
