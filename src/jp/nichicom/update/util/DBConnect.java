package jp.nichicom.update.util;

import java.sql.*;
import java.util.ArrayList;

/**
 * DB操作クラス<br>
 * プロパティファイルを読み込み、DBへの接続を行う
 * @version 1.01 2005/10/13
 * @author shin fujihara
 * 1.01 java1.5 に対応
 */
public class DBConnect {
	
	/**
	 * 接続に使用するコネクション
	 */
	private Connection con = null;
	/**
	 * データ更新に使用するPreparedStatement
	 */
	private PreparedStatement pstmt = null;
	
	/**
	 * コンストラクタ<br>
	 * コネクションの生成を行う
	 */
	public DBConnect() throws Exception{
		makeConnection();
	}
	
	/**
	 * コネクションの生成を行う
	 *
	 */
	private void makeConnection() throws Exception{
		try{
			XMLDocumentUtil doc = new XMLDocumentUtil(PropertyUtil.getProperty("property.filename"));
			// java 5 対応
	        //設定ファイルからDB接続情報を取得する。
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
	 * SQL文の実行を行う
	 * @param sql 実行するSQL文
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
	 * SQL文の実行を行い、データの取得を行う。<BR>
	 * 取得したレコードの最初の行の最初の列データを返却する。
	 * @param sql 実行するSQL(select)
	 * @return 取得データ
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
	 * 実行するprepareStatementの設定を行う<BR>
	 * @param sql 実行するprepareStatement
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
	 * 設定したprepareStatementの実行を行う
	 * @param ary prepareStatementに設定するデータ
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
	 * トランザクションを明示的に開始する
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
	 * 現在のトランザクションをコミットする
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
	 * 現在のトランザクションをロールバックする
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
	 * ファイナライザ<br>
	 * トランザクションの終了を行う。
	 */
	protected void finalize(){
		Log.info("--- DBConnect finalize ---");
		commit();
	}
	
	
}
