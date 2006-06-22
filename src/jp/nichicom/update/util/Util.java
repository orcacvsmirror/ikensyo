package jp.nichicom.update.util;

import java.io.File;

/**
 * アップデートツール　ユーティリティクラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class Util {

	public static boolean isNull(String str){
		return (str == null) || ("".equals(trim(str)));
	}
	/**
	 * 文字列前後の空白、改行を取り除く
	 * @param str 対象データ
	 * @return 処理後の文字列
	 */
	public static String trim(String str){
		if(str == null) return "";
		
		char[] temp = str.toCharArray();
		int start = -1;
		int end = -1;
		//空白とみなす文字
		String comp = " " + "　" + "\n";
		
		for(int i = 0; i < temp.length; i++){
			if(comp.indexOf(temp[i]) == -1){
				start = i;
				break;
			}
		}
		//全て空白
		if(start == -1) return "";
		
		for(int i = temp.length - 1; i >= 0; i--){
			if(comp.indexOf(temp[i]) == -1){
				end = i;
				break;
			}
		}
		return str.substring(start,end + 1);
	}
	/**
	 * 指定されたパスのディレクトリを作成する。
	 * @param fileName ファイルパス
	 */
	public static void makeDir(String fileName){
		if(isNull(fileName)){
			return;
		}
	    File file = new File(fileName);
	    //フォルダがあれば、作成する
	    if(file.getParent() != null){
	    	File dir = new File(file.getParent());
	    	if(!dir.exists()){
		    	dir.mkdirs();
	    	}
	    }
	}
}
