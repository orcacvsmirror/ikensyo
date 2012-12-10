package jp.nichicom.ac.text;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * キーと値の関係で変換を行なうフォーマットです。
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
   * コンストラクタです。
   */
  public ACHashMapFormat(){
    super();
  }
  /**
   * コンストラクタです。
   * @param keys キー(文字列)集合
   * @param vals 値(Object)集合
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
   * コンストラクタです。
   * @param map 変換マップ
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
   * [key:文字列, value:Object]形式の変換マップを返します。
   * @return 変換マップ
   */
  public HashMap getMap() {
    return map;
  }
  /**
   * [key:文字列, value:Object]形式の変換マップを設定します。
   * @param map 変換マップ
   */
  public void setMap(HashMap map) {
    this.map = map;
  }

}
