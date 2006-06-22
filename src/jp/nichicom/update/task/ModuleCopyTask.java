package jp.nichicom.update.task;

import java.util.ArrayList;

import jp.nichicom.update.util.HttpConnecter;
import jp.nichicom.update.util.PropertyUtil;
import jp.nichicom.update.util.XMLDocumentUtil;

/**
 * モジュールコピータスク実行クラス
 * @version 1.01 2005/10/13
 * @author shin fujihara
 * 
 * 1.01 java1.5に対応
 */
public class ModuleCopyTask extends AbstractTask{
	/**
	 * Windows 用のバージョン情報
	 */
	private String windows_name = "";
	/**
	 * Mac 用のバージョン情報
	 */
	private String mac_name = "";
	/**
	 * 実行するタスク情報
	 */
	private ArrayList taskList = new ArrayList();
	
	/**
	 * Windows 用のバージョン情報を設定する
	 * @param name
	 */
	public void setWindowsName(String name){
		windows_name = name;
	}
	/**
	 * Mac 用のバージョン情報を設定する。
	 * @param name
	 */
	public void setMacName(String name){
		mac_name = name;
	}
	/**
	 * Windows 用のバージョン情報を取得する
	 * @return Windows 用のバージョン情報
	 */
	public String getWindowsName(){
		return windows_name;
	}
	/**
	 * Mac 用のバージョン情報を取得する
	 * @return Mac 用のバージョン情報
	 */
	public String getMacName(){
		return mac_name;
	}
	/**
	 * 実行タスクの情報を設定する
	 * @param module_url コピー元のURL
	 * @param copy_dir コピー先のディレクトリ
	 */
	public void putTask(String module_url,String copy_dir){
		taskList.add(new CopyTask(module_url,copy_dir));
	}
	
	/**
	 * モジュール置換の実装
	 */
	public boolean runTask() throws Exception{
        XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
        
        //String version = doc.getNodeValue("//properities[@id='Version']/properity[@id='no']");
        String version = doc.getNodeValue("Version","no");
		
		//サーバ上のモジュールバージョンが新しければ、コピー実行
		if(version.compareTo(getVersionNo()) >= 0){
			return false;
		}
		
		for(int i = 0; i<taskList.size();i++){
			CopyTask task = (CopyTask)taskList.get(i);
			task.runTask();
		}
		
		doc.setNodeValue("Version","no",getVersionNo());
		
		if(System.getProperty("os.name").toLowerCase().startsWith("windows")){
			doc.setNodeValue("Version","SoftName",getWindowsName());
		} else {
			doc.setNodeValue("Version","SoftName", getMacName());
		}
		doc.store();
		
		return true;
	}
}

/**
 * モジュールコピータスク実行内容クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
class CopyTask{
	/**
	 * モジュールコピー元のURL
	 */
	private String module_url;
	/**
	 * モジュールコピー先のディレクトリ
	 */
	private String copy_dir;
	
	/**
	 * コンストラクタ
	 * @param url モジュールコピー元のURL
	 * @param dir モジュールコピー先のディレクトリ
	 */
	public CopyTask(String url,String dir){
		setModuleURL(url);
		setCopyDir(dir);
	}
	
	/**
	 * モジュールコピータスクの実行
	 * @throws Exception
	 */
	public void runTask() throws Exception {
		HttpConnecter.getFile(module_url,copy_dir);
	}
	/**
	 * モジュールコピー元のURLを設定する
	 * @param url モジュールコピー元のURL
	 */
	public void setModuleURL(String url){
		module_url = url;
	}
	/**
	 * モジュールコピー先のディレクトリを設定する
	 * @param dir モジュールコピー先のディレクトリ
	 */
	public void setCopyDir(String dir){
		copy_dir = dir;
	}
	/**
	 * モジュールコピー元のURLを取得する
	 * @return モジュールコピー元のURL
	 */
	public String getModuleURL(){
		return module_url;
	}
	/**
	 * モジュールコピー先のディレクトリを取得する
	 * @return モジュールコピー先のディレクトリ
	 */
	public String getCopyDir(){
		return copy_dir;
	}
}
