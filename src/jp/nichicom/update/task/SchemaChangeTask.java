package jp.nichicom.update.task;

import java.util.ArrayList;

import jp.nichicom.update.util.DBConnect;
import jp.nichicom.update.util.Util;

/**
 * スキーマ変更タスク実行クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class SchemaChangeTask extends AbstractTask{

	/**
	 * 実行するタスクデータを保持する。
	 */
	private ArrayList taskList = new ArrayList();
	
	/**
	 * タスクを設定する。
	 * @param query_url 実行するクエリ
	 */
	public void putTask(String query_url){
		taskList.add(query_url);
	}
	
	/**
	 * 設定されているタスクを実行する。
	 * 
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
			
            tp.setStatus("スキーマ更新("+getVersionNo()+")");
			for(int i = 0; i < taskList.size(); i++){
				String query = (String)taskList.get(i);
				if(!Util.isNull(query)){
					db.exec(query);
				}
                tp.addProgress();
			}
			db.exec("UPDATE IKENSYO_VERSION SET SCHEMA_VERSION = " + getVersionNo() + ",LAST_TIME = CURRENT_TIMESTAMP");
			db.commit();
		} catch (Exception e){
			db.rollback();
			throw e;
		} finally{
			db = null;
		}
		db = null;
		return true;
	}
	
	/**
	 * DBからスキーマのバージョン情報を取得する
	 * @param db DBアクセスオブジェクト
	 * @return 取得したスキーマバージョン
	 * @throws Exception 実行時エラー
	 */
	public String getDataVersion(DBConnect db) throws Exception {
		return db.execQuerySingle("SELECT SCHEMA_VERSION FROM IKENSYO_VERSION");
	}
      public int size(){
            return taskList.size();
        }
}
