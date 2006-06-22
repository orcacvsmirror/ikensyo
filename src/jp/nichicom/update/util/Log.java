package jp.nichicom.update.util;

import java.util.logging.*;

/**
 * アップデートツール用ログ出力クラス
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class Log {
	
	/**
	 * 出力に使用するロガー
	 */
	private static Logger logger = Logger.getLogger("update.logging");
	
	/**
	 * ロガーの初期化処理
	 */
	static {
		try{
			Util.makeDir("logs/update.log");
	        // FileHandlerを生成
	        FileHandler fh = new FileHandler("logs/update.log");
	        // Formatterを設定
	        fh.setFormatter(new SimpleFormatter());
	        // ログの出力先を追加
	        logger.addHandler(fh);
	        // ログの出力レベルを設定
	        logger.setLevel(Level.INFO);
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public static void setLogLevel(String level){
		if("OFF".equalsIgnoreCase(level)){
			setLogLevel(Level.OFF);
		} else if ("SEVERE".equalsIgnoreCase(level)){
			setLogLevel(Level.SEVERE);
		} else if ("WARNING".equalsIgnoreCase(level)){
			setLogLevel(Level.WARNING);
		} else if ("INFO".equalsIgnoreCase(level)){
			setLogLevel(Level.INFO);
		} else if ("CONFIG".equalsIgnoreCase(level)){
			setLogLevel(Level.CONFIG);
		} else if ("FINE".equalsIgnoreCase(level)){
			setLogLevel(Level.FINE);
		} else if ("FINER".equalsIgnoreCase(level)){
			setLogLevel(Level.FINER);
		} else if ("FINEST".equalsIgnoreCase(level)){
			setLogLevel(Level.FINEST);
		} else if ("ALL".equalsIgnoreCase(level)){
			setLogLevel(Level.ALL);
		}
	}
	
	/**
	 * ログレベルの変更を行う
	 * @param level 変更するログレベル
	 */
	public static void setLogLevel(Level level){
		logger.setLevel(level);
	}
	
	/**
	 * 致命的なエラーが発生した場合
	 * @param msg 出力メッセージ
	 */
	public static void severe(String msg){
		try{
			if(logger.isLoggable(Level.SEVERE)){
				logger.log(Level.SEVERE,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 起こりうる問題が発生した場合
	 * @param msg 出力するメッセージ
	 */
	public static void warning(String msg){
		try{
			if(logger.isLoggable(Level.WARNING)){
				logger.log(Level.WARNING,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 重要な情報を出力する場合
	 * @param msg 出力するメッセージ
	 */
	public static void info(String msg){
		try{
			if(logger.isLoggable(Level.INFO)){
				logger.log(Level.INFO,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 設定情報を出力する場合
	 * @param msg 出力するメッセージ
	 */
	public static void config(String msg){
		try{
			if(logger.isLoggable(Level.CONFIG)){
				logger.log(Level.CONFIG,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * デバック情報を出力する場合
	 * @param msg 出力するメッセージ
	 */
	public static void fine(String msg){
		try{
			if(logger.isLoggable(Level.FINE)){
				logger.log(Level.FINE,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * より詳細なデバック情報を出力する場合
	 * @param msg 出力するメッセージ
	 */
	public static void finer(String msg){
		try{
			if(logger.isLoggable(Level.FINER)){
				logger.log(Level.FINER,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * もっとも詳細なデバック情報を出力する場合
	 * @param msg 出力するメッセージ
	 */
	public static void finest(String msg){
		try{
			if(logger.isLoggable(Level.FINEST)){
				logger.log(Level.FINEST,msg);
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
}
