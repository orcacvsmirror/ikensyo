package jp.nichicom.update.task;

/**
 * 実行タスクの抽象クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
public abstract class AbstractTask {
	
	/**
	 * バージョンNo
	 */
	private String version_no = "";
	/**
	 * タスクの実行内容を記述する。
	 * @return タスクを実行したか否か
	 * @throws Exception 実行時例外
	 */
	 public boolean runTask() throws Exception{
	 	return true;
	 }
	 
	 /**
	  * 実行タスクのバージョンを設定する
	  * @param no バージョン
	  */
	 public void setVersionNo(String no){
	 	version_no = no;
	 }
	 
	 /**
	  * 実行タスクのバージョンを取得する
	  * @return
	  */
	 public String getVersionNo(){
	 	return version_no;
	 }
}
