package jp.nichicom.update.util;

import java.util.logging.*;

/**
 * �A�b�v�f�[�g�c�[���p���O�o�̓N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class Log {
	
	/**
	 * �o�͂Ɏg�p���郍�K�[
	 */
	private static Logger logger = Logger.getLogger("update.logging");
	
	/**
	 * ���K�[�̏���������
	 */
	static {
		try{
			Util.makeDir("logs/update.log");
	        // FileHandler�𐶐�
	        FileHandler fh = new FileHandler("logs/update.log");
	        // Formatter��ݒ�
	        fh.setFormatter(new SimpleFormatter());
	        // ���O�̏o�͐��ǉ�
	        logger.addHandler(fh);
	        // ���O�̏o�̓��x����ݒ�
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
	 * ���O���x���̕ύX���s��
	 * @param level �ύX���郍�O���x��
	 */
	public static void setLogLevel(Level level){
		logger.setLevel(level);
	}
	
	/**
	 * �v���I�ȃG���[�����������ꍇ
	 * @param msg �o�̓��b�Z�[�W
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
	 * �N���肤���肪���������ꍇ
	 * @param msg �o�͂��郁�b�Z�[�W
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
	 * �d�v�ȏ����o�͂���ꍇ
	 * @param msg �o�͂��郁�b�Z�[�W
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
	 * �ݒ�����o�͂���ꍇ
	 * @param msg �o�͂��郁�b�Z�[�W
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
	 * �f�o�b�N�����o�͂���ꍇ
	 * @param msg �o�͂��郁�b�Z�[�W
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
	 * ���ڍׂȃf�o�b�N�����o�͂���ꍇ
	 * @param msg �o�͂��郁�b�Z�[�W
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
	 * �����Ƃ��ڍׂȃf�o�b�N�����o�͂���ꍇ
	 * @param msg �o�͂��郁�b�Z�[�W
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
