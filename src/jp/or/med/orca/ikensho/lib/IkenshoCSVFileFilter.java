/*
 * Project code name "ORCA"
 * 主治医意見書作成ソフト ITACHI（JMA IKENSYO software）
 * Copyright(C) 2002 JMA (Japan Medical Association)
 *
 * This program is part of "ITACHI (JMA IKENSYO software)".
 *
 * This program is distributed in the hope that it will be useful
 * for further advancement in medical care, according to JMA Open
 * Source License, but WITHOUT ANY WARRANTY.
 * Everyone is granted permission to use, copy, modify and
 * redistribute this program, but only under the conditions described
 * in the JMA Open Source License. You should have received a copy of
 * this license along with this program. If not, stop using this
 * program and contact JMA, 2-28-16 Honkomagome, Bunkyo-ku, Tokyo,
 * 113-8621, Japan.
 *****************************************************************
 * アプリ: ITACHI
 * 開発者: 堤瑞樹
 * 作成日: 2005/12/01  日本コンピュータ株式会社 堤瑞樹 新規作成
 * 更新日: ----/--/--
 *****************************************************************
 */
package jp.or.med.orca.ikensho.lib;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class IkenshoCSVFileFilter extends FileFilter {
    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }

            String ext = getFileExtension(file);
            if (ext.equals("csv")) {
                return true;
            }
        }
        return false; //どれにも当てはまらなければfalse
    }

    /**
     * フィルタの説明文を返す
     * @return String
     */
    public String getDescription() {
        return "CSVファイル(*.csv)";
    }

    /**
     * ファイルの拡張子を小文字で返す。取得できなければnullを返す。
     * @param file File
     * @return String
     */
    public String getFileExtension(File file) {
        if (file == null) {
            return "";
        }

        String fileNm = file.getName();
        int i = fileNm.lastIndexOf('.'); //最後のピリオド位置を取得
        if (i == -1) {
            return "";
        }

        if ( (i > 0) && (i < (fileNm.length() - 1))) {
            return fileNm.substring(i + 1).toLowerCase(); //最後のピリオドより後の文字列を小文字で返す
        }
        else {
            return "";
        }
    }

    /**
     * 拡張子付きファイルファイル名を取得します。
     * @param fileName ファイル名
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName) {
        if (fileName == null) {
            return "";
        }

        int pos = fileName.lastIndexOf('.'); //最後のピリオド位置を取得
        if (pos == -1) {
            //拡張子を追加して返す
            return fileName + ".csv";
        }

        if ( (pos > 0) && (pos < (fileName.length() - 1))) {
            String ext = fileName.substring(pos + 1).toLowerCase(); //最後のピリオドより後の文字列を小文字で返す
            if (ext.equals("csv")) {
                return fileName;
            }
            return fileName + ".csv";
        }
        else {
            return "";
        }
    }

    /**
     * デフォルトの拡張子を取得します。
     * @return デフォルトの拡張子(「.」は含まない)
     */
    public String getDefaultFileExtension() {
        return "csv";
    }
}
