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
 * ログ出力を補助するクラスです。
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
     * 静的初期化を行います。
     */
    static {
        singleton = Logger.getLogger(VRLogger.class.getName());
        setLevel(Level.INFO);
        setStockDayCount(30);
        //実行パス直下のlogsフォルダへ出力
        try {
            setPath("logs");
        } catch (IOException ex) {
        }
    }

    /**
     * ファイルハンドラを開放します。
     */
    public static void close() {
        if (fileHandler != null) {
            fileHandler.close();
            fileHandler = null;
        }
    }

    /**
     * CONFIGレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void config(Object o) {
        log(Level.CONFIG, o);
    }

    /**
     * FINEレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void fine(Object o) {
        log(Level.FINE, o);
    }

    /**
     * FINERレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void finer(Object o) {
        log(Level.FINER, o);
    }

    /**
     * FINESTレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void finest(Object o) {
        log(Level.FINEST, o);
    }

    /**
     * 出力レベルを返します。
     * 
     * @return 出力レベル
     */
    public static Level getLevel() {
        return getInstance().getLevel();
    }

    /**
     * 出力フォルダパスを返します。
     * 
     * @return 出力フォルダパス
     */
    public static String getPath() {
        return path;
    }

    /**
     * 保存期限日数を返します。
     * 
     * @return 保存期限日数
     */
    public static int getStockDayCount() {
        return stockDayCount;
    }

    /**
     * INFOレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void info(Object o) {
        log(Level.INFO, o);
    }

    /**
     * ログを出力します。
     * 
     * @param l 出力レベル
     * @param o データ
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
            //日にちが変わっていたらファイルハンドラを更新
            if ((fileHandler == null) || (date != lastDate)) {
                //既存のハンドラを削除
                if (fileHandler != null) {
                    logger.removeHandler(fileHandler);
                }

                lastDate = date;

                //Mac対応
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
                    //削除期限が0より大きければ、過去ログ削除
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
            //ログ出力失敗
        }
    }

    /**
     * 出力レベルを設定します。
     * 
     * @param l 出力レベル
     */
    public static void setLevel(Level l) {
        getInstance().setLevel(l);
    }

    /**
     * 出力フォルダパスを設定します。
     * 
     * @param path 出力フォルダパス
     * @throws IOException フォルダ生成失敗
     */
    public static void setPath(String path) throws IOException {
        VRLogger.path = path;

        File f = new File(getPath());
        if (!f.exists()) {
            if (!f.mkdir()) {
                throw new IOException(getPath() + "を作成できません。");
            }
        }

    }

    /**
     * 保存期限日数を設定します。
     * 
     * @param stockDayCount 保存期限日数
     */
    public static void setStockDayCount(int stockDayCount) {
        if (stockDayCount < 0) {
            return;
        }
        VRLogger.stockDayCount = stockDayCount;
    }

    /**
     * SEVEREレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void severe(Object o) {
        log(Level.SEVERE, o);
    }

    /**
     * WARNINGレベルでログを出力します。
     * 
     * @param o データ
     */
    public static void warning(Object o) {
        log(Level.WARNING, o);
    }

    /**
     * 唯一のインスタンスを返します。 <br />
     * Singleton Pattern
     * 
     * @return インスタンス
     */
    protected static Logger getInstance() {
        return singleton;
    }

    /**
     * コンストラクタ
     */
    protected VRLogger() {
    }

}