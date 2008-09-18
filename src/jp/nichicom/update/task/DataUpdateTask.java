package jp.nichicom.update.task;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import jp.nichicom.update.util.DBConnect;
import jp.nichicom.update.util.HttpConnecter;
import jp.nichicom.update.util.Log;
import jp.nichicom.update.util.Util;

/**
 * データ更新タスク実行クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class DataUpdateTask extends AbstractTask{
	
	/**
	 * 実行するデータ更新プランを保存する
	 */
	private ArrayList taskList = new ArrayList();
	
	/**
	 * 実行するデータ更新プランを設定する
	 * @param pre_query データ更新前に実行するクエリ
	 * @param query_url データ更新に使用するクエリ(PrepardStatementを設定)
	 * @param data_url データ更新に使用するデータが保存してあるURL
	 */
	public void putTask(String pre_query, String query_url,String data_url){
		taskList.add(new UpdateTask(pre_query,query_url,data_url));
	}
	
	/**
	 * データ更新タスクの実行
	 */
	public boolean runTask(TaskProcesser tp) throws Exception{
		DBConnect db = new DBConnect();
		try{
            ///バージョンチェック
			String version = getDataVersion(db);
			//サーバ上のモジュールバージョンが新しければ、コピー実行
			if(version.compareTo(getVersionNo()) >= 0){
				db = null;
                tp.skipTask(this);
				return false;
			}
			db.commit();
			
			db.begin();
			
            tp.setStatus("マスタ更新("+getVersionNo()+")");
			for(int i = 0; i < taskList.size(); i++){
				UpdateTask task = (UpdateTask)taskList.get(i);
				task.runTask(db);
                tp.addProgress();
			}
			db.exec("UPDATE IKENSYO_VERSION SET DATA_VERSION = " + getVersionNo() + ",LAST_TIME = CURRENT_TIMESTAMP");
			
			db.commit();
			db = null;
		} catch(Exception e) {
			db.rollback();
			throw e;
		} finally{
			db = null;
		}
		return true;
	}
	
	/**
	 * DBから現在のデータバージョンを取得する
	 * @param db DBアクセスツール
	 * @return 取得したバージョン情報
	 */
	public String getDataVersion(DBConnect db) throws Exception {
		return db.execQuerySingle("SELECT DATA_VERSION FROM IKENSYO_VERSION");
	}
    
      public int size(){
            return taskList.size();
        }
}

/**
 * データ更新実行クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
class UpdateTask{
	/**
	 * データ更新前に実行するクエリ
	 */
	private String pre_qurery;
	/**
	 * データ更新に使用するクエリ(PrepardStatementを設定)
	 */
	private String param_query;
	/**
	 * データ更新に使用するデータが保存してあるURL
	 */
	private String data_url;
	
	/**
	 * コンストラクタ
	 * @param pre_query データ更新前に実行するクエリ
	 * @param param_query データ更新に使用するクエリ(PrepardStatementを設定)
	 * @param data_url データ更新に使用するデータが保存してあるURL
	 */
	public UpdateTask(String pre_query,String param_query,String data_url){
		setPreQuery(pre_query);
		setParamQuery(param_query);
		setDataURL(data_url);
	}
	
	/**
	 * データ更新の実行
	 * @param db DBアクセスクラス
	 */
	public void runTask(DBConnect db) throws Exception {
		if(Util.isNull(param_query)){
			Log.warning("UpdateTask runTask 発行するクエリ取得エラー");
			return;
		}
		if(!Util.isNull(data_url)){
			HttpConnecter.getFile(data_url,"temp/temp.csv");
		}
		
		if(!Util.isNull(pre_qurery)){
			db.exec(pre_qurery);
		}

		db.setPrepareQuery(param_query);
		BufferedReader reader = new BufferedReader(new FileReader("temp/temp.csv"));
		String line;
		String[] tempData;
		ArrayList ary;
		while((line=reader.readLine())!=null){
			ary = new ArrayList();
			tempData = line.split("\",\"");
			for(int i = 0; i < tempData.length; i++){
				if(0 == i){
					if((tempData[0] != null) && (tempData.length > 0)){
						tempData[0] = tempData[0].substring(1);
					}
				}
				if(i == tempData.length - 1){
					if((tempData[i] != null) && (tempData.length > 0)){
						tempData[i] = tempData[i].substring(0,tempData[i].length() - 1);
					}
				}
				ary.add(tempData[i]);
			}
			db.execPrepareQuery(ary);
		}
		reader.close();
		File file = new File("temp/temp.csv");
		if(file.exists()){
			file.delete();
		}
	}
	
	/**
	 * データ更新前に実行するクエリを設定する
	 * @param query クエリ
	 */
	public void setPreQuery(String query){
		pre_qurery = query;
	}
	/**
	 * データ更新に使用するクエリ(PrepardStatement)を設定する
	 * @param query PrepardStatement
	 */
	public void setParamQuery(String query){
		param_query = query;
	}
	/**
	 * データ更新に使用するデータが保存してあるURLを設定する
	 * @param dir データファイルのURL
	 */
	public void setDataURL(String dir){
		data_url = dir;
	}
	/**
	 * データ更新前に実行するクエリを取得する
	 * @return クエリ
	 */
	public String getPreQuery(){
		return pre_qurery;
	}
	/**
	 * データ更新に使用するクエリ(PrepardStatement)を取得する
	 * @return PrepardStatement
	 */
	public String getParamQuery(){
		return param_query;
	}
	/**
	 * データ更新に使用するデータが保存してあるURLを取得する
	 * @return データファイルのURL
	 */
	public String getDataURL(){
		return data_url;
	}
}