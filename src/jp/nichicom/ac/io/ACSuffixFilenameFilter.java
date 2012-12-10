package jp.nichicom.ac.io;

import java.io.File;
import java.io.FilenameFilter;

/**
 * ファイルのサフィックスで判断するファイル名フィルタです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/07/09
 */
public class ACSuffixFilenameFilter implements FilenameFilter {

    private String[] suffixes;

    /**
     * コンストラクタです。
     */
    public ACSuffixFilenameFilter() {
    }

    /**
     * コンストラクタです。
     * 
     * @param suffix 許容するサフィックス
     */
    public ACSuffixFilenameFilter(String suffix) {
        this(new String[] { suffix });
    }

    /**
     * コンストラクタです。
     * 
     * @param suffixes 許容するサフィックス配列
     */
    public ACSuffixFilenameFilter(String[] suffixes) {
        setSuffixes(suffixes);
    }

    /**
     * 許容するサフィックス配列 を返します。
     * 
     * @return 許容するサフィックス配列
     */
    public String[] getSuffixes() {
        return suffixes;
    }

    /**
     * 許容するサフィックス配列 を設定します。
     * 
     * @param suffixes 許容するサフィックス配列
     */
    public void setSuffixes(String[] suffixes) {
        this.suffixes = suffixes;
    }

    public boolean accept(File dir, String name) {
        String[] s = getSuffixes();
        if (s != null) {
            int end = s.length;
            for (int i = 0; i < end; i++) {
                if (name.endsWith(s[i])) {
                    return true;
                }
            }
        }
        return false;
    }

}
