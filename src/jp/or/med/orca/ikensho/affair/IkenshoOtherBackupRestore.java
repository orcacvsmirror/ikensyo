package jp.or.med.orca.ikensho.affair;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Calendar;

import jp.nichicom.ac.filechooser.ACFileChooser;
import jp.nichicom.ac.util.ACMessageBox;
import jp.nichicom.vr.text.VRDateFormat;
import jp.or.med.orca.ikensho.lib.IkenshoCommon;
import jp.or.med.orca.ikensho.lib.IkenshoFDBFileFilter;
import jp.or.med.orca.ikensho.sql.IkenshoFirebirdDBManager;

/** <HEAD_IKENSYO> */
public class IkenshoOtherBackupRestore {
    private final int STATE_SUCCESS = 0;
    private final int STATE_CANCEL = 1;
    private final int STATE_ERROR = 2;

    private String dbServer;
    private String dbPath;

    /**
     * コンストラクタ
     * @param dbServer DBサーバ名
     * @param dbPath DBファイルパス
     */
    public IkenshoOtherBackupRestore(String dbServer, String dbPath) {
        this.dbServer = dbServer;
        this.dbPath = dbPath;
    }

    /**
     * DBのバックアップ処理を行います。（とりまとめ）
     * @throws Exception
     */
    public void doBackUp() throws Exception {
        switch (dataBackUp()) {
            case STATE_SUCCESS:
                ACMessageBox.show(
                    "データの退避(バックアップ)が終了しました。",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                break;

            case STATE_CANCEL:
                break;

            case STATE_ERROR:
                ACMessageBox.show(
                    "データの退避(バックアップ)に失敗しました。",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                break;
        }
    }

    /**
     * DBのバックアップ処理を行います。（実務）
     * @return int
     * @throws Exception
     */
    private int dataBackUp() throws Exception {
      //サーバチェック
      if (!checkLocalHost(dbServer)) {
        return STATE_CANCEL;
      }

      //退避先ファイル取得
      File file = getBackUpFile();
      if (file == null) {
        return STATE_CANCEL;
      }

      //バックアップ開始確認MSG
      int result = ACMessageBox.show(
        "データの退避(バックアップ)処理を開始します。\nしばらく時間がかかります。よろしいですか？",
        ACMessageBox.BUTTON_OKCANCEL,
        ACMessageBox.ICON_QUESTION,
        ACMessageBox.FOCUS_OK);
      if (result == ACMessageBox.RESULT_CANCEL) {
        return STATE_CANCEL;
      }

      //バックアップ処理
      try {
        fileCpy(dbPath, file.getPath());
      }
      catch (Exception ex) {
        ex.printStackTrace();
        return STATE_ERROR;
      }
      return STATE_SUCCESS;
    }

    /**
     * DBのりストア処理を行います。(とりまとめ)
     * @throws Exception
     */
    public boolean doRestore() throws Exception {
        switch (dataRestore()) {
            case STATE_SUCCESS:
                ACMessageBox.show(
                    "データの復元(リストア)が終了しました。",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                return true;

            case STATE_CANCEL:
                break;

            case STATE_ERROR:
                ACMessageBox.show(
                    "データの復元(リストア)に失敗しました。",
                    ACMessageBox.BUTTON_OK,
                    ACMessageBox.ICON_INFOMATION);
                break;
        }
        return false;
    }

    /**
     * DBのりストア処理を行います。（実務)
     * @return int
     * @throws Exception
     */
    private int dataRestore() throws Exception {
        //サーバチェック
        if (!checkLocalHost(dbServer)) {
            return STATE_CANCEL;
        }

        //復元元ファイルを取得
        File restoreFile = getRestoreFile();
        if (restoreFile == null) {
            return STATE_CANCEL;
        }

        //退避先ファイルを取得
        File bkupFile = getBackUpFileForRestore(restoreFile);
        if (bkupFile == null) {
            return STATE_CANCEL;
        }

        //リストア開始確認MSG
        int result = ACMessageBox.show(
            "データの復元(リストア)処理を開始します。\nしばらく時間がかかります。よろしいですか？",
            ACMessageBox.BUTTON_OKCANCEL,
            ACMessageBox.ICON_QUESTION,
            ACMessageBox.FOCUS_OK);
        if (result == ACMessageBox.RESULT_CANCEL) {
            return STATE_CANCEL;
        }

        // リストア処理
        try {
            // 現DBを退避
            fileCpy(dbPath, bkupFile.getPath());

            // リストア
             fileCpy(restoreFile.getPath(), dbPath);
        } catch (Exception ex) {
            ex.printStackTrace();
            return STATE_ERROR;
        }
        return STATE_SUCCESS;
    }

    /**
     * ファイルのコピーを行います。
     * @param orgFile コピー元ファイルパス
     * @param newFile コピー先ファイルパス
     * @throws Exception
     */
    private void fileCpy(String orgFile, String newFile) throws Exception {
        BufferedInputStream input = new BufferedInputStream(new FileInputStream(orgFile));
        BufferedOutputStream output = new BufferedOutputStream(new FileOutputStream(newFile));
        byte buf[] = new byte[256];
        int len;
        while ( (len = input.read(buf)) != -1) {
            output.write(buf, 0, len);
        }
        output.flush();
        output.close();
        input.close();
    }

    /**
     * DBサーバがLocalHostかどうかチェックします。
     * @param server サーバ名
     * @return boolean
     */
    private boolean checkLocalHost(String server) {
        if (!server.equals("localhost") &&
            !server.equals("127.0.0.1")) {
            ACMessageBox.show(
                "別のコンピュータのデータベースを使用している場合は、この機能を利用することはできません。",
                ACMessageBox.BUTTON_OK, ACMessageBox.ICON_INFOMATION);
            return false;
        }
        return true;
    }

    /**
     * 退避先ファイルを取得します。
     * @return 退避先ファイル
     */
    private File getBackUpFile() {
        File file;
        boolean loopFlg;

        do {
            ACFileChooser fileChooser = new ACFileChooser();
//            fileChooser.setFileFilter(new IkenshoDBFileFilter());

            //ループフラグをリセット
            loopFlg = false;

            //ファイル選択ダイアログ
            file = fileChooser.showSaveDialog(
                dbPath, getDefaultFileName("old"),
                "データベースのバックアップファイルの保存場所を指定して下さい。",
                new IkenshoFDBFileFilter());

            //キャンセルされた場合は終了
            if (file == null) {
                return null;
            }

            //拡張子を補完する
            file = new File(
                file.getParent(),
                ((IkenshoFDBFileFilter)fileChooser.getFileFilter()).getFilePathWithExtension(file.getName(), 0));

            
            // 退避先が現在DBでないかをチェック
            // 2006/02/03[Tozo Tanaka] : replace begin
            // if (file.getPath().toLowerCase().equals(dbPath.toLowerCase())) {
            if (file.getPath().toLowerCase().equals(
                    new File(dbPath).getPath().toLowerCase())) {
                // 2006/02/03[Tozo Tanaka] : replace end
                ACMessageBox.show("無効なパス名です。\n"
                        + "(バックアップ先ファイルが現在のデータベースファイルと同じです)",
                        ACMessageBox.BUTTON_OK, ACMessageBox.ICON_EXCLAMATION);
                loopFlg = true; // ファイル選択をやり直す
                continue;
            }

            //同名ファイル存在チェック・上書き確認
            if (file.exists()) {
                int result = ACMessageBox.show(
                    "同じ名前のファイルが存在しています。\n上書きしてもよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_CANCEL) {
                    loopFlg = true; //ファイル選択をやり直す
                    continue;
                }
            }
        } while (loopFlg);

        return file;
    }

    /**
     * 復元元ファイルを取得します。
     * @return 復元元ファイル
     */
    private File getRestoreFile() {
        File file;
        boolean loopFlg;
        do {
            ACFileChooser fileChooser = new ACFileChooser();
//            fileChooser.setFileFilter(new IkenshoDBFileFilter());

            //ループフラグをリセット
            loopFlg = false;

            //ファイル選択ダイアログ
            file = fileChooser.showOpenDialog(
                dbPath, "",
                "復元元のデータベースファイルの格納場所を指定して下さい。",
                new IkenshoFDBFileFilter());

            //キャンセルされた場合は終了
            if (file == null) {
                return null;
            }

            // 拡張子を補完する
            // 2006/12/03[Tozo Tanaka] : replace begin
            // file = new File(
            // file.getParent(),
            // ((IkenshoFDBFileFilter)fileChooser.getFileFilter()).getFilePathWithExtension(file.getName(),
            // 1));
            file = new File(file.getParent(), getFilePathWithExtension(file
                    .getName(), 1, fileExtensions));
            // 2006/12/03[Tozo Tanaka] : replace end

            // 復元元が現在DBでないかをチェック
            // 2006/02/03[Tozo Tanaka] : replace begin
            // if (file.getPath().toLowerCase().equals(dbPath.toLowerCase())) {
            if (file.getPath().toLowerCase().equals(
                    new File(dbPath).getPath().toLowerCase())) {
                // 2006/02/03[Tozo Tanaka] : replace end
                ACMessageBox.show("無効なパス名です。\n"
                        + "(復元元ファイルが現在のデータベースファイルと同じです)",
                        ACMessageBox.BUTTON_OK, ACMessageBox.ICON_EXCLAMATION);
                loopFlg = true; // ファイル選択をやり直す
                continue;
            }

            //ファイル存在チェック
            if (!file.exists()) {
                //2006/12/03[Tozo Tanaka] : replace begin 
//                ACMessageBox.show(
//                    "ファイルを指定して下さい。",
//                    ACMessageBox.BUTTON_OK,
//                    ACMessageBox.ICON_EXCLAMATION);
                ACMessageBox.show(
                        "FirebirdのDBファイルを指定して下さい。",
                        ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                //2006/12/03[Tozo Tanaka] : replace end
                loopFlg = true; //ファイル選択をやり直す
                continue;
            }
        } while (loopFlg);

        return file;
    }

    private final String[] fileExtensions = {"old", "fdb"};
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
     * 復元時の現在DB退避先ファイルを取得します。
     * @param restoreFile File
     * @return File
     */
    private File getBackUpFileForRestore(File restoreFile) {
        File bkupFile = new File(dbPath);
        bkupFile = new File(restoreFile.getParent(), "ikenold.fdb");
        boolean loopFlg;
        int result;

        //現DB退避確認MSG
        result = ACMessageBox.show(
            "選択されたファイル(" + restoreFile.getName() + ")から全データの復元を実行します。\n" +
            "現在のデータは別ファイル(" + bkupFile.getName() + ")に保存されます。\n" +
            "実行してよろしいですか？",
            ACMessageBox.BUTTON_OKCANCEL,
            ACMessageBox.ICON_QUESTION,
            ACMessageBox.FOCUS_CANCEL);
        if (result == ACMessageBox.RESULT_CANCEL) {
            return null;
        }

        do {
            //ループフラグをリセット
            loopFlg = false;

            //同名ファイル存在チェック・上書き確認
            if (bkupFile.exists()) {
                result = ACMessageBox.show(
                    "現在のデータベースの保存先に同じファイル名のファイルが存在しています。\n" +
                    "上書きしてよろしいですか？",
                    ACMessageBox.BUTTON_OKCANCEL,
                    ACMessageBox.ICON_QUESTION,
                    ACMessageBox.FOCUS_CANCEL);
                if (result == ACMessageBox.RESULT_OK) {
                    return bkupFile;
                }
            }
            else {
                break;
            }

            //退避先ファイルを取得
            boolean loopFlg2;
            do {
                ACFileChooser fileChooser = new ACFileChooser();
                fileChooser.setFileFilter(new IkenshoFDBFileFilter());

                //ループフラグをリセット
                loopFlg = true;
                loopFlg2 = false;

                //ファイル選択ダイアログ
                bkupFile = fileChooser.showSaveDialog(
                    dbPath, getDefaultFileName("fdb"),
                    "現在データベースの保存場所を指定して下さい。",
                    new IkenshoFDBFileFilter());

                //キャンセルされた場合は終了
                if (bkupFile == null) {
                    return null;
                }

                //拡張子を補完する
                bkupFile = new File(
                    bkupFile.getParent(),
                    ((IkenshoFDBFileFilter)fileChooser.getFileFilter()).getFilePathWithExtension(bkupFile.getName(), 1));

                //退避先が現在DBでないかをチェック
                if (bkupFile.getPath().toLowerCase().equals(dbPath.toLowerCase())) {
                    ACMessageBox.show(
                        "無効なパス名です。\n" +
                        "(バックアップ先ファイルが現在のデータベースファイルと同じです)",
                        ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                    loopFlg2 = true; //ファイル選択をやり直す
                    continue;
                }

                //退避先が退避元でないかをチェック
                if (bkupFile.getPath().toLowerCase().equals(restoreFile.getPath().toLowerCase())) {
                    ACMessageBox.show(
                        "無効なパス名です。\n" +
                        "(バックアップ先ファイルが復元元ファイルと同じです)",
                        ACMessageBox.BUTTON_OK,
                        ACMessageBox.ICON_EXCLAMATION);
                    loopFlg2 = true; //ファイル選択をやり直す
                    continue;
                }
            }
            while (loopFlg2);
        }
        while (loopFlg);

        return bkupFile;
    }

    /**
     * デフォルトのファイル名を取得します。
     * @param fileExtension 拡張子
     * @return ファイル名
     */
    public String getDefaultFileName(String fileExtension) {
        String today = "";
        try {
            Calendar cal = Calendar.getInstance();
            VRDateFormat vrdf = new VRDateFormat("yyMMdd");
            today = vrdf.format(cal.getTime());
        }
        catch (Exception ex) {
            IkenshoCommon.showExceptionMessage(ex);
        }
        return "iken" + today + "." + fileExtension;
    }
}
