package jp.nichicom.update.task;

import java.util.ArrayList;

import jp.nichicom.update.util.DBConnect;
import jp.nichicom.update.util.Util;

/**
 * �X�L�[�}�ύX�^�X�N���s�N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class SchemaChangeTask extends AbstractTask{

	/**
	 * ���s����^�X�N�f�[�^��ێ�����B
	 */
	private ArrayList taskList = new ArrayList();
	
	/**
	 * �^�X�N��ݒ肷��B
	 * @param query_url ���s����N�G��
	 */
	public void putTask(String query_url){
		taskList.add(query_url);
	}
	
	/**
	 * �ݒ肳��Ă���^�X�N�����s����B
	 * 
	 */
	public boolean runTask(TaskProcesser tp) throws Exception{
		DBConnect db = new DBConnect();
		try{
			///�o�[�W�����`�F�b�N
			String version = getDataVersion(db);
			//�T�[�o��̃��W���[���o�[�W�������V������΁A�R�s�[���s
			if(version.compareTo(getVersionNo()) >= 0){
				db = null;
                tp.skipTask(this);
				return false;
			}
			db.commit();
			
			db.begin();
			
            tp.setStatus("�X�L�[�}�X�V("+getVersionNo()+")");
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
	 * DB����X�L�[�}�̃o�[�W���������擾����
	 * @param db DB�A�N�Z�X�I�u�W�F�N�g
	 * @return �擾�����X�L�[�}�o�[�W����
	 * @throws Exception ���s���G���[
	 */
	public String getDataVersion(DBConnect db) throws Exception {
		return db.execQuerySingle("SELECT SCHEMA_VERSION FROM IKENSYO_VERSION");
	}
      public int size(){
            return taskList.size();
        }
}
