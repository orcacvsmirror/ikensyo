package jp.nichicom.update.util;

import java.sql.*;
import java.util.ArrayList;

/**
 * DB����N���X<br>
 * �v���p�e�B�t�@�C����ǂݍ��݁ADB�ւ̐ڑ����s��
 * @version 1.01 2005/10/13
 * @author shin fujihara
 * 1.01 java1.5 �ɑΉ�
 */
public class DBConnect {
	
	/**
	 * �ڑ��Ɏg�p����R�l�N�V����
	 */
	private Connection con = null;
	/**
	 * �f�[�^�X�V�Ɏg�p����PreparedStatement
	 */
	private PreparedStatement pstmt = null;
	
	/**
	 * �R���X�g���N�^<br>
	 * �R�l�N�V�����̐������s��
	 */
	public DBConnect() throws Exception{
		makeConnection();
	}
	
	/**
	 * �R�l�N�V�����̐������s��
	 *
	 */
	private void makeConnection() throws Exception{
		try{
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			// java 5 �Ή�
	        //�ݒ�t�@�C������DB�ڑ������擾����B
//	        String server = doc.getNodeValue("//properities[@id='DBConfig']/properity[@id='Server']");
//	        String path = doc.getNodeValue("//properities[@id='DBConfig']/properity[@id='Path']");
//	        String port = doc.getNodeValue("//properities[@id='DBConfig']/properity[@id='Port']");
//	        String id = doc.getNodeValue("//properities[@id='DBConfig']/properity[@id='UserName']");
//	        String pass = doc.getNodeValue("//properities[@id='DBConfig']/properity[@id='Password']");
	        String server = doc.getNodeValue("DBConfig","Server");
	        String path = doc.getNodeValue("DBConfig","Path");
	        String port = doc.getNodeValue("DBConfig","Port");
	        String id = doc.getNodeValue("DBConfig","UserName");
	        String pass = doc.getNodeValue("DBConfig","Password");
			
			
			Class.forName("org.firebirdsql.jdbc.FBDriver");
			con = DriverManager.getConnection("jdbc:firebirdsql://" + server + ":" + port + "/" + path, id, pass);
			con.setAutoCommit(false);
		} catch(Exception e){
			Log.warning("makeConnection Error : " + e.getLocalizedMessage());
			throw e;
		}
	}
	
	/**
	 * SQL���̎��s���s��
	 * @param sql ���s����SQL��
	 */
	public void exec(String sql) throws Exception{
		try {
			Log.info("--- start exec " + sql + " ---");
			if(con == null){
				Log.warning("Connection is null -- run makeConnection");
				makeConnection();
			}
			Statement stmt = con.createStatement();
			Log.info("createStatement success");
			
			stmt.execute(sql);
			Log.info("exec " + sql + " success");
			
			stmt.close();
			
		} catch (Exception e) {
			Log.warning("fail exec : " + e.getLocalizedMessage() + "\nSQL : " + sql);
			throw e;
		}
		Log.info("--- end exec " + sql + " ---");
	}
	
	/**
	 * SQL���̎��s���s���A�f�[�^�̎擾���s���B<BR>
	 * �擾�������R�[�h�̍ŏ��̍s�̍ŏ��̗�f�[�^��ԋp����B
	 * @param sql ���s����SQL(select)
	 * @return �擾�f�[�^
	 */
	public String execQuerySingle(String sql) throws Exception{
		String result = null;
		try {
			Log.info("--- start execQuerySingle " + sql + " ---");
			if(con == null){
				Log.warning("Connection is null -- run makeConnection");
				makeConnection();
			}
			Statement stmt = con.createStatement();
			Log.info("createStatement success");
			
			ResultSet rs = stmt.executeQuery(sql);
			
			if(rs.next()){
				result = rs.getString(1);
			}
			Log.info("execQuerySingle " + sql + " success\nreturn data:" + result);
			
			rs.close();
			stmt.close();
			
		} catch (Exception e) {
			Log.warning("fail execQuerySingle : " + e.getLocalizedMessage() + "\nSQL : " + sql);
			throw e;
		}
		Log.info("--- end execQuerySingle " + sql + " ---");
		return result;
	}
	/**
	 * ���s����prepareStatement�̐ݒ���s��<BR>
	 * @param sql ���s����prepareStatement
	 */
	public void setPrepareQuery(String sql) throws Exception{
		try {
			Log.info("--- start setPrepareQuery " + sql + " ---");
			if(con == null){
				Log.warning("Connection is null -- run makeConnection");
				makeConnection();
			}
			pstmt = con.prepareStatement(sql);
			Log.info("setPrepareQuery " + sql + " success");
		} catch (Exception e) {
			Log.warning("fail setPrepareQuery : " + e.getLocalizedMessage() + "\nSQL : " + sql);
			throw e;
		}
		Log.info("--- end setPrepareQuery " + sql + " ---");
	}
	
	/**
	 * �ݒ肵��prepareStatement�̎��s���s��
	 * @param ary prepareStatement�ɐݒ肷��f�[�^
	 */
	public void execPrepareQuery(ArrayList ary) throws Exception{
		Log.fine("--- start execPrepareQuery " + ary.toString() + " ---");
		if(pstmt == null){
			Log.warning("prepareStatement is null -- cancel running");
			return;
		}
		if(ary == null){
			Log.warning("DataArray is null -- cancel running");
			return;
		}
		
		try{
			for(int i = 0; i < ary.size(); i++){
				pstmt.setString(i + 1,ary.get(i).toString());
			}
			pstmt.execute();
		} catch(Exception e){
			Log.warning("fail execPrepareQuery : " + e.getLocalizedMessage() + "\ndata : " + ary.toString());
			throw e;
		}
		Log.fine("--- end execPrepareQuery " + ary.toString() + " ---");
	}
	
	/**
	 * �g�����U�N�V�����𖾎��I�ɊJ�n����
	 */
	public void begin() throws Exception{
		Log.info("--- start begin transactin ---");
		if(con != null){
			Log.info("connection is alive! -- run commit transaction");
			commit();
		}
		makeConnection();
		Log.info("--- end begin transactin ---");
	}
	/**
	 * ���݂̃g�����U�N�V�������R�~�b�g����
	 */
	public void commit(){
		Log.info("--- start commit transactin ---");
		if(con != null){
			try{
				if(!con.isClosed()){
					con.commit();
					con.close();
				}
			} catch(Exception e){
				Log.warning("fail commit transaction : " + e.getLocalizedMessage());
			}
			con = null;
		}
		Log.info("--- end commit transactin ---");
	}
	
	/**
	 * ���݂̃g�����U�N�V���������[���o�b�N����
	 */
	public void rollback(){
		Log.info("--- start rollback transactin ---");
		if(con != null){
			try{
				if(!con.isClosed()){
					con.rollback();
					con.close();
				}
				
			} catch (Exception e){
				Log.warning("fail rollback transaction : " + e.getLocalizedMessage());
			}
			con = null;
		}
		Log.info("--- end rollback transactin ---");
	}
	
	/**
	 * �t�@�C�i���C�U<br>
	 * �g�����U�N�V�����̏I�����s���B
	 */
	protected void finalize(){
		Log.info("--- DBConnect finalize ---");
		commit();
	}
	
	
}
