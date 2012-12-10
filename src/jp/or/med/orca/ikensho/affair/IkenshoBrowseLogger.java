package jp.or.med.orca.ikensho.affair;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * 業務起動ログ出力クラス
 * <p>
 * Copyright (c) 2012 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2012/09/18
 */
public class IkenshoBrowseLogger {
	
	private static Logger logger = Logger.getLogger(IkenshoBrowseLogger.class.getName());
	private static final int LOG_LIMIT = 100 * 1024 * 1024; //100MB
	private static final int LOG_COUNT = 512;
	public static final String LOG_FILE = "logs/browse.log";
	
	static {
		
	    try {
	    	FileHandler handler = new FileHandler(LOG_FILE, LOG_LIMIT, LOG_COUNT, true);
	        handler.setFormatter(new IkenshoBrowseLoggerFormatter());
	        logger.addHandler(handler);
	        logger.setUseParentHandlers(false);
	        
	    } catch (IOException e) {
	    	VRLogger.warning("Failed to initialize QkanBrowseLogger handler.");
	    }
	}
	
	public static void log(ACAffairInfo affair) {
		logger.log(Level.INFO, "browse", affair);
	}
	
}
