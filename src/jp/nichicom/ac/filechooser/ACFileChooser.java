package jp.nichicom.ac.filechooser;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.core.ACFrame;

/**
 * ファイル選択ダイアログです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2005/12/01
 */

public class ACFileChooser extends JFileChooser {
    /**
     * 開くモードを表す定数です。
     */
    public static final int MODE_FILE_LOAD = 1;
    /**
     * 保存モードを表す定数です。
     */
    public static final int MODE_FILE_SAVE = 2;

    /**
     * コンストラクタです。
     */
    public ACFileChooser() {
    }

    /**
     * 引数のファイルに拡張子を補完したファイルを返します。
     * 
     * @param src 変換元
     * @return 変換結果
     */
    public File getFilePathWithExtension(File src) {

        if (src == null) {
            return null;
        }
        // 拡張子を補完
        return new File(src.getParent()
                + ACConstants.FILE_SEPARATOR
                + ((ACFileFilter) getFileFilter()).getFilePathWithExtension(src
                        .getName()));
    }

    /**
     * ファイル選択ダイアログを表示します。
     * <p>
     * すべてのオプションを指定してmodeに応じたダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * modeは以下の値をとります。<br />
     * MODE_FILE_LOAD : 開くダイアログ<br />
     * MODE_FILE_SAVE : 保存ダイアログ
     * </code>
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @param currentFile 初期選択ファイル
     * @param title ダイアログタイトル
     * @param fileFilter ファイルフィルタ
     * @param mode ダイアログモード
     * @return 指定されたファイル
     */
    public File showDialog(String currentDirectory, String currentFile,
            String title, FileFilter fileFilter, int mode) {
        // 入力チェック・初期選択ディレクトリ
        if (ACCommon.getInstance().isNullText(currentDirectory)) {
            currentDirectory = "";
        }
        // 入力チェック・初期選択ファイル
        if (ACCommon.getInstance().isNullText(currentFile)) {
            currentFile = "";
        }
        // 入力チェック・タイトル
        if (ACCommon.getInstance().isNullText(title)) {
            switch (mode) {
            case MODE_FILE_LOAD:
                title = "ファイルを開く";
                break;
            case MODE_FILE_SAVE:
                title = "名前を付けて保存";
                break;
            default:
                title = "ファイルを選択";
                break;
            }
        }

        // ダイアログ
        this.setFileFilter(fileFilter); // フィルタを設定
        this.setCurrentDirectory(new File(currentDirectory)); // 初期選択ディレクトリ
        this.setSelectedFile(new File(currentFile)); // 初期選択ファイル名
        this.setDialogTitle(title); // ダイアログタイトル

        switch (mode) {
        case MODE_FILE_LOAD:
            if (this.showOpenDialog(ACFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                return this.getSelectedFile();
            }
            break;
        case MODE_FILE_SAVE:
            if (this.showSaveDialog(ACFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                return this.getSelectedFile();
            }
            break;
        default:
            break;
        }

        return null;
    }

    /**
     * ファイル選択ダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してmodeに応じたダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * modeは以下の値をとります。<br />
     * MODE_FILE_LOAD : 開くダイアログ<br />
     * MODE_FILE_SAVE : 保存ダイアログ
     * </code>
     * </p>
     * <p>
     * 許容する拡張子を配列で指定します。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showDialog("c:\\", "c:\\file.txt", "タイトル", new String[]{"txt","bat"}, "テキストファイル(*.txt, *.bat)", MODE_FILE_LOAD)
     * </code>
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @param currentFile 初期選択ファイル
     * @param title ダイアログタイトル
     * @param fileExtensions フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @param mode ダイアログモード
     * @return 指定されたファイル
     */
    public File showDialog(String currentDirectory, String currentFile,
            String title, String[] fileExtensions,
            String fileExtensionsDescription, int mode) {
        ACFileFilter filter = new ACFileFilter();
        filter.setFileExtensions(fileExtensions);
        filter.setDescription(fileExtensionsDescription);
        return showDialog(currentDirectory, currentFile, title, filter, mode);
    }

    /**
     * ファイル選択ダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してmodeに応じたダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * modeは以下の値をとります。<br />
     * MODE_FILE_LOAD : 開くダイアログ<br />
     * MODE_FILE_SAVE : 保存ダイアログ
     * </code>
     * </p>
     * <p>
     * 許容する拡張子を配列で指定します。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showDialog("タイトル", new String[]{"txt","bat"}, "テキストファイル(*.txt, *.bat)", MODE_FILE_LOAD)
     * </code>
     * </p>
     * 
     * @param title ダイアログタイトル
     * @param fileExtensions フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @param mode ダイアログモード
     * @return 指定されたファイル
     */
    public File showDialog(String title, String[] fileExtensions,
            String fileExtensionsDescription, int mode) {
        return showDialog("", "", title, fileExtensions,
                fileExtensionsDescription, mode);
    }

    /**
     * ファイル保存ダイアログを表示します。
     * <p>
     * 許容するファイルをFileFilterで指定します。
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @param currentFile 初期選択ファイル
     * @param title ダイアログタイトル
     * @param fileFilter ファイルフィルタ
     * @return 指定されたファイル
     */
    public File showSaveDialog(String currentDirectory, String currentFile,
            String title, FileFilter fileFilter) {
        return showDialog(currentDirectory, currentFile, title, fileFilter,
                MODE_FILE_SAVE);
    }

    /**
     * ファイル保存ダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * 許容する拡張子を配列で指定します。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("c:\\", "c:\\file.txt", "タイトル", new String[]{"txt","bat"}, "テキストファイル(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @param currentFile 初期選択ファイル
     * @param title ダイアログタイトル
     * @param fileExtensions フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showSaveDialog(String currentDirectory, String currentFile,
            String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(currentDirectory, currentFile, title, fileExtensions,
                fileExtensionsDescription, MODE_FILE_SAVE);
    }

    /**
     * ファイル保存ダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * 許容する拡張子を配列で指定します。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("タイトル", new String[]{"txt","bat"}, "テキストファイル(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param title ダイアログタイトル
     * @param fileExtensions フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showSaveDialog(String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(title, fileExtensions, fileExtensionsDescription,
                MODE_FILE_SAVE);
    }

    /**
     * ファイル保存ダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("タイトル", "txt", "テキストファイル(*.txt)")
     * </code>
     * </p>
     * 
     * @param title ダイアログタイトル
     * @param fileExtension フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showSaveDialog(String title, String fileExtension,
            String fileExtensionsDescription) {
        return showDialog(title, new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_SAVE);
    }

    /**
     * ファイル保存ダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showSaveDialog("txt", "テキストファイル(*.txt)")
     * </code>
     * </p>
     * <p>
     * ダイアログタイトルを「ファイルを保存」として表示します。
     * </p>
     * 
     * @param fileExtension フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showSaveDialog(String fileExtension,
            String fileExtensionsDescription) {
        return showDialog("ファイルを保存", new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_SAVE);
    }

    /**
     * ファイルを開くダイアログを表示します。
     * <p>
     * 許容するファイルをFileFilterで指定します。
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @param currentFile 初期選択ファイル
     * @param title ダイアログタイトル
     * @param fileFilter ファイルフィルタ
     * @return 指定されたファイル
     */
    public File showOpenDialog(String currentDirectory, String currentFile,
            String title, FileFilter fileFilter) {
        return showDialog(currentDirectory, currentFile, title, fileFilter,
                MODE_FILE_LOAD);
    }

    /**
     * ファイルを開くダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * 許容する拡張子を配列で指定します。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("c:\\", "c:\\file.txt", "タイトル", new String[]{"txt", "bat"}, "テキストファイル(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @param currentFile 初期選択ファイル
     * @param title ダイアログタイトル
     * @param fileExtensions フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showOpenDialog(String currentDirectory, String currentFile,
            String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(currentDirectory, currentFile, title, fileExtensions,
                fileExtensionsDescription, MODE_FILE_LOAD);
    }

    /**
     * ファイルを開くダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * 許容する拡張子を配列で指定します。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("タイトル", new String[]{"txt", "bat"}, "テキストファイル(*.txt, *.bat)")
     * </code>
     * </p>
     * 
     * @param title ダイアログタイトル
     * @param fileExtensions フィルタ集合
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showOpenDialog(String title, String[] fileExtensions,
            String fileExtensionsDescription) {
        return showDialog(title, fileExtensions, fileExtensionsDescription,
                MODE_FILE_LOAD);
    }

    /**
     * ファイルを開くダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("タイトル", "txt", "テキストファイル(*.txt)")
     * </code>
     * </p>
     * 
     * @param title ダイアログタイトル
     * @param fileExtension フィルタ
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showOpenDialog(String title, String fileExtension,
            String fileExtensionsDescription) {
        return showDialog(title, new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_LOAD);
    }

    /**
     * ファイルを開くダイアログを表示します。
     * <p>
     * 内部でFileFilterを生成してダイアログを開きます。
     * </p>
     * <p>
     * <code>
     * ex:<br />
     * showOpenDialog("txt", "テキストファイル(*.txt)")
     * </code>
     * </p>
     * <p>
     * ダイアログタイトルを「ファイルを開く」として表示します。
     * </p>
     * 
     * @param fileExtension フィルタ
     * @param fileExtensionsDescription ファイルフィルタの説明
     * @return 指定されたファイル
     */
    public File showOpenDialog(String fileExtension,
            String fileExtensionsDescription) {
        return showDialog("ファイルを開く", new String[] { fileExtension },
                fileExtensionsDescription, MODE_FILE_LOAD);
    }

    /**
     * フォルダを開くダイアログを表示します。
     * <p>
     * <code>
     * ex:<br />
     * showDirectorySelectDaialog("C:\\")
     * </code>
     * </p>
     * <p>
     * ダイアログタイトルを「フォルダを開く」として表示します。
     * </p>
     * 
     * @return 指定されたフォルダ
     */
    public File showDirectorySelectDaialog() {
        return showDirectorySelectDaialog(null, null);
    }

    /**
     * フォルダを開くダイアログを表示します。
     * <p>
     * <code>
     * ex:<br />
     * showDirectorySelectDaialog("C:\\")
     * </code>
     * </p>
     * <p>
     * ダイアログタイトルを「フォルダを開く」として表示します。
     * </p>
     * 
     * @param currentDirectory 初期表示フォルダ
     * @return 指定されたフォルダ
     */
    public File showDirectorySelectDaialog(String currentDirectory) {
        return showDirectorySelectDaialog("フォルダを開く", currentDirectory);
    }

    /**
     * フォルダを開くダイアログを表示します。
     * <p>
     * <code>
     * ex:<br />
     * showDirectorySelectDaialog("フォルダを開く", "C:\\")
     * </code>
     * </p>
     * 
     * @param title ダイアログタイトル
     * @param currentDirectory 初期表示フォルダ
     * @return 指定されたフォルダ
     */
    public File showDirectorySelectDaialog(String title, String currentDirectory) {
        if ((title != null)&&(!"".equals(title))) {
            title = "フォルダを開く";
        }
        this.setDialogTitle(title); // ダイアログタイトル
        if ((currentDirectory != null)&&(!"".equals(currentDirectory))) {
            this.setCurrentDirectory(new File(currentDirectory)); // 初期選択ディレクトリ
        }
        setFileSelectionMode(ACFileChooser.DIRECTORIES_ONLY);

        if (this.showOpenDialog(ACFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
            return this.getSelectedFile();
        }
        return null;
    }
}
