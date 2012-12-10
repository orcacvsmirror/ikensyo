/** TODO <HEAD> */
package jp.nichicom.vr.util.logging;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.XMLFormatter;

/**
 * ���O�o�͂�⏕����N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see Logger
 * @see Level
 */
public class VRLogger {
    private static FileHandler fileHandler;

    private static int lastDate;

    private static String path;

    private static Logger singleton;

    private static int stockDayCount;

    /**
     * �ÓI���������s���܂��B
     */
    static {
        singleton = Logger.getLogger(VRLogger.class.getName());
        setLevel(Level.INFO);
        setStockDayCount(30);
        //���s�p�X������logs�t�H���_�֏o��
        try {
            setPath("logs");
        } catch (IOException ex) {
        }
    }

    /**
     * �t�@�C���n���h�����J�����܂��B
     */
    public static void close() {
        if (fileHandler != null) {
            fileHandler.close();
            fileHandler = null;
        }
    }

    /**
     * CONFIG���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void config(Object o) {
        log(Level.CONFIG, o);
    }

    /**
     * FINE���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void fine(Object o) {
        log(Level.FINE, o);
    }

    /**
     * FINER���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void finer(Object o) {
        log(Level.FINER, o);
    }

    /**
     * FINEST���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void finest(Object o) {
        log(Level.FINEST, o);
    }

    /**
     * �o�̓��x����Ԃ��܂��B
     * 
     * @return �o�̓��x��
     */
    public static Level getLevel() {
        return getInstance().getLevel();
    }

    /**
     * �o�̓t�H���_�p�X��Ԃ��܂��B
     * 
     * @return �o�̓t�H���_�p�X
     */
    public static String getPath() {
        return path;
    }

    /**
     * �ۑ�����������Ԃ��܂��B
     * 
     * @return �ۑ���������
     */
    public static int getStockDayCount() {
        return stockDayCount;
    }

    /**
     * INFO���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void info(Object o) {
        log(Level.INFO, o);
    }

    /**
     * ���O���o�͂��܂��B
     * 
     * @param l �o�̓��x��
     * @param o �f�[�^
     */
    public static void log(Level l, Object o) {
        Logger logger = getInstance();
        if ((l.intValue() < logger.getLevel().intValue())
                || (logger.getLevel().intValue() == Level.OFF.intValue())) {
            return;
        }

        Calendar cal = Calendar.getInstance();
        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DATE);
        int date = y * 10000 + m * 100 + d;

        try {
            //���ɂ����ς���Ă�����t�@�C���n���h�����X�V
            if ((fileHandler == null) || (date != lastDate)) {
                //�����̃n���h�����폜
                if (fileHandler != null) {
                    logger.removeHandler(fileHandler);
                }

                lastDate = date;

                //Mac�Ή�
                //fileHandler = new FileHandler(getPath() + "\\" +
                // String.valueOf(lastDate) + ".xml");
                fileHandler = new FileHandler(getPath()
                        + System.getProperty("file.separator")
                        + String.valueOf(lastDate) + ".xml");
                fileHandler.setFormatter(new XMLFormatter());
                fileHandler.setEncoding("UTF-8");
                logger.addHandler(fileHandler);

                int stock = getStockDayCount();
                if (stock > 0) {
                    //�폜������0���傫����΁A�ߋ����O�폜
                    cal.add(Calendar.DATE, -stock);
                    y = cal.get(Calendar.YEAR);
                    m = cal.get(Calendar.MONTH) + 1;
                    d = cal.get(Calendar.DATE);
                    date = y * 10000 + m * 100 + d;

                    File[] files = new File(getPath()).listFiles();
                    int count = files.length;
                    for (int i = 0; i < count; i++) {
                        if (files[i].isFile()) {
                            try {
                                String fileName = files[i].getName();
                                if (fileName.indexOf(".xml") > 0) {
                                    int nameDate = Integer.parseInt(fileName
                                            .split(".xml", 2)[0]);
                                    if (nameDate < date) {
                                        files[i].delete();
                                    }
                                }
                            } catch (Throwable ex) {
                            }
                        }
                    }
                }
            }

            logger.log(l, o.toString());
        } catch (IOException ex) {
            //���O�o�͎��s
        }
    }

    /**
     * �o�̓��x����ݒ肵�܂��B
     * 
     * @param l �o�̓��x��
     */
    public static void setLevel(Level l) {
        getInstance().setLevel(l);
    }

    /**
     * �o�̓t�H���_�p�X��ݒ肵�܂��B
     * 
     * @param path �o�̓t�H���_�p�X
     * @throws IOException �t�H���_�������s
     */
    public static void setPath(String path) throws IOException {
        VRLogger.path = path;

        File f = new File(getPath());
        if (!f.exists()) {
            if (!f.mkdir()) {
                throw new IOException(getPath() + "���쐬�ł��܂���B");
            }
        }

    }

    /**
     * �ۑ�����������ݒ肵�܂��B
     * 
     * @param stockDayCount �ۑ���������
     */
    public static void setStockDayCount(int stockDayCount) {
        if (stockDayCount < 0) {
            return;
        }
        VRLogger.stockDayCount = stockDayCount;
    }

    /**
     * SEVERE���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void severe(Object o) {
        log(Level.SEVERE, o);
    }

    /**
     * WARNING���x���Ń��O���o�͂��܂��B
     * 
     * @param o �f�[�^
     */
    public static void warning(Object o) {
        log(Level.WARNING, o);
    }

    /**
     * �B��̃C���X�^���X��Ԃ��܂��B <br />
     * Singleton Pattern
     * 
     * @return �C���X�^���X
     */
    protected static Logger getInstance() {
        return singleton;
    }

    /**
     * �R���X�g���N�^
     */
    protected VRLogger() {
    }

}