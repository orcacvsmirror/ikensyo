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
 * �f�[�^�X�V�^�X�N���s�N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class DataUpdateTask extends AbstractTask{
	
	/**
	 * ���s����f�[�^�X�V�v������ۑ�����
	 */
	private ArrayList taskList = new ArrayList();
	
	/**
	 * ���s����f�[�^�X�V�v������ݒ肷��
	 * @param pre_query �f�[�^�X�V�O�Ɏ��s����N�G��
	 * @param query_url �f�[�^�X�V�Ɏg�p����N�G��(PrepardStatement��ݒ�)
	 * @param data_url �f�[�^�X�V�Ɏg�p����f�[�^���ۑ����Ă���URL
	 */
	public void putTask(String pre_query, String query_url,String data_url){
		taskList.add(new UpdateTask(pre_query,query_url,data_url));
	}
	
	/**
	 * �f�[�^�X�V�^�X�N�̎��s
	 */
	public boolean runTask() throws Exception{
		DBConnect db = new DBConnect();
		try{
			///�o�[�W�����`�F�b�N
			String version = getDataVersion(db);
			//�T�[�o��̃��W���[���o�[�W�������V������΁A�R�s�[���s
			if(version.compareTo(getVersionNo()) >= 0){
				db = null;
				return false;
			}
			db.commit();
			
			db.begin();
			
			for(int i = 0; i < taskList.size(); i++){
				UpdateTask task = (UpdateTask)taskList.get(i);
				task.runTask(db);
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
	 * DB���猻�݂̃f�[�^�o�[�W�������擾����
	 * @param db DB�A�N�Z�X�c�[��
	 * @return �擾�����o�[�W�������
	 */
	public String getDataVersion(DBConnect db) throws Exception {
		return db.execQuerySingle("SELECT DATA_VERSION FROM IKENSYO_VERSION");
	}
}

/**
 * �f�[�^�X�V���s�N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
class UpdateTask{
	/**
	 * �f�[�^�X�V�O�Ɏ��s����N�G��
	 */
	private String pre_qurery;
	/**
	 * �f�[�^�X�V�Ɏg�p����N�G��(PrepardStatement��ݒ�)
	 */
	private String param_query;
	/**
	 * �f�[�^�X�V�Ɏg�p����f�[�^���ۑ����Ă���URL
	 */
	private String data_url;
	
	/**
	 * �R���X�g���N�^
	 * @param pre_query �f�[�^�X�V�O�Ɏ��s����N�G��
	 * @param param_query �f�[�^�X�V�Ɏg�p����N�G��(PrepardStatement��ݒ�)
	 * @param data_url �f�[�^�X�V�Ɏg�p����f�[�^���ۑ����Ă���URL
	 */
	public UpdateTask(String pre_query,String param_query,String data_url){
		setPreQuery(pre_query);
		setParamQuery(param_query);
		setDataURL(data_url);
	}
	
	/**
	 * �f�[�^�X�V�̎��s
	 * @param db DB�A�N�Z�X�N���X
	 */
	public void runTask(DBConnect db) throws Exception {
		if(Util.isNull(param_query)){
			Log.warning("UpdateTask runTask ���s����N�G���擾�G���[");
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
	 * �f�[�^�X�V�O�Ɏ��s����N�G����ݒ肷��
	 * @param query �N�G��
	 */
	public void setPreQuery(String query){
		pre_qurery = query;
	}
	/**
	 * �f�[�^�X�V�Ɏg�p����N�G��(PrepardStatement)��ݒ肷��
	 * @param query PrepardStatement
	 */
	public void setParamQuery(String query){
		param_query = query;
	}
	/**
	 * �f�[�^�X�V�Ɏg�p����f�[�^���ۑ����Ă���URL��ݒ肷��
	 * @param dir �f�[�^�t�@�C����URL
	 */
	public void setDataURL(String dir){
		data_url = dir;
	}
	/**
	 * �f�[�^�X�V�O�Ɏ��s����N�G�����擾����
	 * @return �N�G��
	 */
	public String getPreQuery(){
		return pre_qurery;
	}
	/**
	 * �f�[�^�X�V�Ɏg�p����N�G��(PrepardStatement)���擾����
	 * @return PrepardStatement
	 */
	public String getParamQuery(){
		return param_query;
	}
	/**
	 * �f�[�^�X�V�Ɏg�p����f�[�^���ۑ����Ă���URL���擾����
	 * @return �f�[�^�t�@�C����URL
	 */
	public String getDataURL(){
		return data_url;
	}
}