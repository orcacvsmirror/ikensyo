package jp.or.med.orca.ikensho.lib;

import java.io.File;

import javax.swing.filechooser.FileFilter;

public class IkenshoFDBFileFilter extends FileFilter {
    private final String[] fileExtensions = {"old", "fdb"};

    public boolean accept(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                return true;
            }

            String ext = getFileExtension(file);
            if (ext != null) {
                for (int i=0; i<fileExtensions.length; i++) {
                    if ((ext.equals(fileExtensions[i]))) {
                        return true;
                    }
                }
            }
        }
        return false; //どれにも当てはまらなければfalse
    }

    /**
     * フィルタの説明文を返す
     * @return String
     */
    public String getDescription() {
        StringBuffer sb = new StringBuffer();
        sb.append("バックアップファイル(");
        for(int i=0; i<fileExtensions.length; i++) {
            sb.append("*." + fileExtensions[i]);
        }
        sb.append(")");
        return sb.toString();
    }

    /**
     * ファイルの拡張子を小文字で返す。取得できなければnullを返す。
     * @param ファイル  Fileのインスタンス
     */
    public String getFileExtension(File file) {
        if (file == null) {
            return null;
        }

        String fileNm = file.getName();
        int i = fileNm.lastIndexOf('.'); //最後のピリオド位置を取得
        if (i == -1) {
            return null;
        }

        if ( (i > 0) && (i < (fileNm.length() - 1))) {
            return fileNm.substring(i + 1).toLowerCase(); //最後のピリオドより後の文字列を小文字で返す
        }
        else {
            return null;
        }
    }

    /**
     * 拡張子付きファイルファイル名を取得します。
     * @param fileName ファイル名
     * @param defaultExtension 拡張子が付いていない場合、拡張子候補の何番目の拡張子を付与するか
     * @param extensions 拡張子候補
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName, int defaultExtensionIndex, String[] extensions) {
        if (fileName == null) {
            return "";
        }

        int pos = fileName.lastIndexOf('.'); //最後のピリオド位置を取得
        if (pos == -1) {
            //拡張子を追加して返す
            return fileName + "." + extensions[defaultExtensionIndex];
        }

        if ( (pos > 0) && (pos < (fileName.length() - 1))) {
            String ext = fileName.substring(pos + 1).toLowerCase(); //最後のピリオドより後の文字列を小文字で返す
            for (int i=0; i<fileExtensions.length; i++) {
                if (ext.equals(fileExtensions[i])) {
                    return fileName;
                }
            }
            return fileName + "." + extensions[defaultExtensionIndex];
        }
        else {
            return "";
        }
    }

    /**
     * 拡張子付きファイルファイル名を取得します。
     * @param fileName ファイル名
     * @param defaultExtension 拡張子が付いていない場合、拡張子候補の何番目の拡張子を付与するか
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName, int defaultExtensionIndex) {
        return getFilePathWithExtension(fileName, defaultExtensionIndex, fileExtensions);
    }

    /**
     * 拡張子付きファイルファイル名を取得します。
     * @param fileName ファイル名
     * @return 拡張子付きファイル名
     */
    public String getFilePathWithExtension(String fileName) {
        return getFilePathWithExtension(fileName, 0, fileExtensions);
    }

    /**
     * デフォルトの拡張子を取得します。
     * @return デフォルトの拡張子(「.」は含まない)
     */
    public String getDefaultFileExtension() {
        return fileExtensions[0];
    }
}
