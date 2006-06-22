package jp.nichicom.update.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

/**
 * HTTP�ڑ��Ńt�@�C���̎擾���s��
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class HttpConnecter {
	
	/**
	 * �v���L�V�T�[�o�̐ݒ���s��
	 * @param host �v���L�V�T�[�o�̃z�X�g��
	 */
	public static void setProxyHost(String host){
		System.setProperty("http.proxyHost", PropertyUtil.getProperty("http.proxyHost"));
	}
	/**
	 * �v���L�V�|�[�g�̐ݒ���s��
	 * @param port �|�[�g�ԍ�
	 */
	public static void setProxyPort(String port){
		System.setProperty("http.proxyPort", PropertyUtil.getProperty("http.proxyPort"));
	}
	/**
	 * �v���L�V���g�p���Ȃ��T�[�o��ݒ肷��
	 * @param host �v���L�V���珜�O����z�X�g��
	 */
	public static void setNonProxyHosts(String host){
		System.setProperty("http.nonProxyHosts", PropertyUtil.getProperty("http.nonProxyHosts"));
	}
	
	/**
	 * �w�肳�ꂽURL�ɐڑ����A�t�@�C���̎擾���s���B<br>
	 * @param urlStr �擾����URL
	 * @param fileName �擾�����t�@�C�����̕ۑ���
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
	    
	    //��ƃt�H���_���m��
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
