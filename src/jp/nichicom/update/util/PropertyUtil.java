package jp.nichicom.update.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * propertyファイル操作クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class PropertyUtil {
	
	/**
	 * アプリケーション内で使用するプロパティファイル名称の設定
	 */
	private static String propertyFileName = "Application.properties";
	
	/**
	 * プロパティファイル名称を設定する
	 * @param name
	 */
	public static void setPropertyFileName(String name){
		propertyFileName = name;
	}
	
	/**
	 * プロパティ値を取得する
	 * @param str キー
	 * @return 取得した値
	 */
	public static String getProperty(String str){
		Properties prop = new Properties();
		// プロパティファイルからキーと値のリストを読み込みます
		try {
			prop.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			Log.warning("プロパティの取得に失敗しました。\n" + e.getLocalizedMessage());
			return "";
		}
		return prop.getProperty(str);
	}
	
	/**
	 * プロパティ値を保存する
	 * @param key キー
	 * @param str 設定値
	 */
	public static void setProperty(String key,String str){
		Properties prop = new Properties();
		// プロパティファイルに保存します。
		try {
		    // プロパティファイルからキーと値のリストを読み込みます
		    prop.load(new FileInputStream(propertyFileName));
			prop.setProperty(key,str);
			prop.store(new FileOutputStream(propertyFileName),propertyFileName);
		} catch (IOException e) {
			Log.warning("プロパティの設定に失敗しました。\n" + e.getLocalizedMessage());
		}
	}
}
