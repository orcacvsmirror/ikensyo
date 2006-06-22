package jp.nichicom.update.task;

import java.util.ArrayList;

import jp.nichicom.update.util.HttpConnecter;
import jp.nichicom.update.util.PropertyUtil;
import jp.nichicom.update.util.XMLDocumentUtil;

/**
 * ���W���[���R�s�[�^�X�N���s�N���X
 * @version 1.01 2005/10/13
 * @author shin fujihara
 * 
 * 1.01 java1.5�ɑΉ�
 */
public class ModuleCopyTask extends AbstractTask{
	/**
	 * Windows �p�̃o�[�W�������
	 */
	private String windows_name = "";
	/**
	 * Mac �p�̃o�[�W�������
	 */
	private String mac_name = "";
	/**
	 * ���s����^�X�N���
	 */
	private ArrayList taskList = new ArrayList();
	
	/**
	 * Windows �p�̃o�[�W��������ݒ肷��
	 * @param name
	 */
	public void setWindowsName(String name){
		windows_name = name;
	}
	/**
	 * Mac �p�̃o�[�W��������ݒ肷��B
	 * @param name
	 */
	public void setMacName(String name){
		mac_name = name;
	}
	/**
	 * Windows �p�̃o�[�W���������擾����
	 * @return Windows �p�̃o�[�W�������
	 */
	public String getWindowsName(){
		return windows_name;
	}
	/**
	 * Mac �p�̃o�[�W���������擾����
	 * @return Mac �p�̃o�[�W�������
	 */
	public String getMacName(){
		return mac_name;
	}
	/**
	 * ���s�^�X�N�̏���ݒ肷��
	 * @param module_url �R�s�[����URL
	 * @param copy_dir �R�s�[��̃f�B���N�g��
	 */
	public void putTask(String module_url,String copy_dir){
		taskList.add(new CopyTask(module_url,copy_dir));
	}
	
	/**
	 * ���W���[���u���̎���
	 */
	public boolean runTask() throws Exception{
        XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
        
        //String version = doc.getNodeValue("//properities[@id='Version']/properity[@id='no']");
        String version = doc.getNodeValue("Version","no");
		
		//�T�[�o��̃��W���[���o�[�W�������V������΁A�R�s�[���s
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
 * ���W���[���R�s�[�^�X�N���s���e�N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
class CopyTask{
	/**
	 * ���W���[���R�s�[����URL
	 */
	private String module_url;
	/**
	 * ���W���[���R�s�[��̃f�B���N�g��
	 */
	private String copy_dir;
	
	/**
	 * �R���X�g���N�^
	 * @param url ���W���[���R�s�[����URL
	 * @param dir ���W���[���R�s�[��̃f�B���N�g��
	 */
	public CopyTask(String url,String dir){
		setModuleURL(url);
		setCopyDir(dir);
	}
	
	/**
	 * ���W���[���R�s�[�^�X�N�̎��s
	 * @throws Exception
	 */
	public void runTask() throws Exception {
		HttpConnecter.getFile(module_url,copy_dir);
	}
	/**
	 * ���W���[���R�s�[����URL��ݒ肷��
	 * @param url ���W���[���R�s�[����URL
	 */
	public void setModuleURL(String url){
		module_url = url;
	}
	/**
	 * ���W���[���R�s�[��̃f�B���N�g����ݒ肷��
	 * @param dir ���W���[���R�s�[��̃f�B���N�g��
	 */
	public void setCopyDir(String dir){
		copy_dir = dir;
	}
	/**
	 * ���W���[���R�s�[����URL���擾����
	 * @return ���W���[���R�s�[����URL
	 */
	public String getModuleURL(){
		return module_url;
	}
	/**
	 * ���W���[���R�s�[��̃f�B���N�g�����擾����
	 * @return ���W���[���R�s�[��̃f�B���N�g��
	 */
	public String getCopyDir(){
		return copy_dir;
	}
}
