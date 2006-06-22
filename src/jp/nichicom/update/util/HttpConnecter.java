package jp.nichicom.update.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * HTTP接続でファイルの取得を行う
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class HttpConnecter {
	
	/**
	 * プロキシサーバの設定を行う
	 * @param host プロキシサーバのホスト名
	 */
	public static void setProxyHost(String host){
		System.setProperty("http.proxyHost", PropertyUtil.getProperty("http.proxyHost"));
	}
	/**
	 * プロキシポートの設定を行う
	 * @param port ポート番号
	 */
	public static void setProxyPort(String port){
		System.setProperty("http.proxyPort", PropertyUtil.getProperty("http.proxyPort"));
	}
	/**
	 * プロキシを使用しないサーバを設定する
	 * @param host プロキシから除外するホスト名
	 */
	public static void setNonProxyHosts(String host){
		System.setProperty("http.nonProxyHosts", PropertyUtil.getProperty("http.nonProxyHosts"));
	}
	
	/**
	 * 指定されたURLに接続し、ファイルの取得を行う。<br>
	 * @param urlStr 取得元のURL
	 * @param fileName 取得したファイル名の保存先
	 */
	public static void getFile(String urlStr,String fileName) throws Exception{
	    InputStream in = null;
	    OutputStream out = null;
	    
	    if(Util.isNull(urlStr)){
	    	Log.warning("HttpConnecter : URL String is Empty!");
	    	return;
	    }
	    if(Util.isNull(fileName)){
	    	Log.warning("HttpConnecter : FileName String is Empty!");
	    	return;
	    }
	    
	    Log.info("--- start getFile : URL " + urlStr + " FileName : " + fileName + " ---");
	    
	    //作業フォルダを確保
	    Util.makeDir(fileName);
	    
	    Log.info("--- start Http Connection to " + urlStr + " ---");
	    
	    try {
	    	URL url = new URL(urlStr);
	        in = url.openStream();
	        out = new FileOutputStream(fileName);
	        byte[] buffer = new byte[8192];
	        int rsize;
	        while ((rsize = in.read(buffer)) != -1) {
	            out.write(buffer, 0, rsize);
	        }
	    } catch (FileNotFoundException e) {
	    	Log.warning("FileNotFound : " + e.getLocalizedMessage());
	    	throw e;
	    } catch (IOException e) {
	    	Log.warning("IOException : " + e.getLocalizedMessage());
	    	throw e;
	    } finally {
	        try {
	            if (in != null) {
	                in.close();
	            }
	            if (out != null) {
	                out.flush();
	                out.close();
	            }
	        } catch (Exception e) {
	        	Log.warning(e.getLocalizedMessage());
	        }
	    }
	    Log.info("---- end Http Connection ---");
	}
}
