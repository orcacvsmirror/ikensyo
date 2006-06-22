package jp.nichicom.update.task;

/**
 * ���s�^�X�N�̒��ۃN���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
public abstract class AbstractTask {
	
	/**
	 * �o�[�W����No
	 */
	private String version_no = "";
	/**
	 * �^�X�N�̎��s���e���L�q����B
	 * @return �^�X�N�����s�������ۂ�
	 * @throws Exception ���s����O
	 */
	 public boolean runTask() throws Exception{
	 	return true;
	 }
	 
	 /**
	  * ���s�^�X�N�̃o�[�W������ݒ肷��
	  * @param no �o�[�W����
	  */
	 public void setVersionNo(String no){
	 	version_no = no;
	 }
	 
	 /**
	  * ���s�^�X�N�̃o�[�W�������擾����
	  * @return
	  */
	 public String getVersionNo(){
	 	return version_no;
	 }
}
