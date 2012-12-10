package jp.nichicom.ac.filechooser;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * 許容する拡張子を複数指定可能なファイルフィルタです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */

public class ACFileFilter extends FileFilter {
    private String description;
    private String[] fileExtensions;

    /**
     * コンストラクタです。
     */
    public ACFileFilter() {
    }

    public boolean accept(File f) {
        if (f != null) {
            if (f.isDirectory()) {
                return true;
            }

            String ext = getFileExtension(f);
            if (ext != null) {
                for (int i = 0; i < fileExtensions.length; i++) {
                    if ((ext.equals(fileExtensions[i]))) {
                        return true;
                    }
                }
            }
        }
        return false; // どれにも当てはまらなければfalse
    }

    /**
     * デフォルトの拡張子を取得します。
     * 
     * @return デフォルトの拡張子(「.」は含まない)
     */
    public String getDefaultFileExtension() {
        return fileExtensions[0];
    }

    public String getDescription() {
        return this.description;
    }

    /**
     * 許容する拡張子配列を返します。
     * 
     * @return 許容する拡張子配列
     */
    public String[] getFileExtensions() {
        return this.fileExtensions;
    }

    /**
     * 拡張子付きファイルファイル名を取得します。
     * 
     * @param fileName ファイル名
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName) {
        return getFilePathWithExtension(fileName, 0, fileExtensions);
    }

    /**
     * 拡張子付きファイルファイル名を取得します。
     * 
     * @param fileName ファイル名
     * @param defaultExtensionIndex 拡張子が付いていない場合、拡張子候補の何番目の拡張子を付与するか
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName,
            int defaultExtensionIndex) {
        return getFilePathWithExtension(fileName, defaultExtensionIndex,
                fileExtensions);
    }

    /**
     * 拡張子付きファイル名を取得します。
     * 
     * @param fileName ファイル名
     * @param defaultExtensionIndex 拡張子が付いていない場合、拡張子候補の何番目の拡張子を付与するか
     * @param extensions 拡張子候補
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName,
            int defaultExtensionIndex, String[] extensions) {
        if (fileName == null) {
            return "";
        }

        int pos = fileName.lastIndexOf('.'); // 最後のピリオド位置を取得
        if (pos == -1) {
            // 拡張子を追加して返す
            return fileName + "." + extensions[defaultExtensionIndex];
        }

        if ((pos > 0) && (pos < (fileName.length() - 1))) {
            String ext = fileName.substring(pos + 1).toLowerCase(); // 最後のピリオドより後の文字列を小文字で返す
            for (int i = 0; i < fileExtensions.length; i++) {
                if (ext.equals(fileExtensions[i])) {
                    return fileName;
                }
            }
            return fileName + "." + extensions[defaultExtensionIndex];
        } else {
            return "";
        }
    }

    /**
     * フィルタの説明を設定します。
     * 
     * @param description フィルタの説明
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 許容する拡張子配列を設定します。
     * 
     * @param fileExtensions 許容する拡張子配列
     */
    public void setFileExtensions(String[] fileExtensions) {
        this.fileExtensions = fileExtensions;
    }

    /**
     * ファイルの拡張子を小文字で返す。取得できなければnullを返す。
     * 
     * @param ファイル Fileのインスタンス
     */
    private String getFileExtension(File file) {
        if (file == null) {
            return null;
        }

        String fileNm = file.getName();
        int i = fileNm.lastIndexOf('.'); // 最後のピリオド位置を取得
        if (i == -1) {
            return null;
        }

        if ((i > 0) && (i < (fileNm.length() - 1))) {
            return fileNm.substring(i + 1).toLowerCase(); // 最後のピリオドより後の文字列を小文字で返す
        } else {
            return null;
        }
    }
}
