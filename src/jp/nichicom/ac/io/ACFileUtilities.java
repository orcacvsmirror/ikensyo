package jp.nichicom.ac.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ファイル関連の汎用メソッドを集めたクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/04/14
 */
public class ACFileUtilities {

    /**
     * ファイルを作成可能か検証し、作成試行結果を返します。
     * <p>
     * 指定フォルダパスの作成に失敗した場合、予備フォルダパスでの作成試行を行います。
     * </p>
     * @param fileName ファイル名
     * @param directory フォルダパス
     * @param spareDirectory 予備フォルダパス
     * @return 作成試行結果
     */
    public static ACFileUtilitiesCreateResult getCreatableFilePath(
            String fileName, String directory, String spareDirectory) {
        ACFileUtilitiesCreateResult result = new ACFileUtilitiesCreateResult();
        result
                .setResult(ACFileUtilitiesCreateResult.SUCCESS_ON_TARGET_DIRECTORY);
        // システム規定の場所もしくは一時ディレクトリを作成
        File dir = createDirectory(directory, spareDirectory);
        if (dir == null) {
            // 一時ディレクトリの作成に失敗
            result
                    .setResult(ACFileUtilitiesCreateResult.ERROR_OF_DIRECTORY_CREATE);
        } else {
            result.setFile(getCreatableFilePath(new File(dir.getAbsolutePath(),
                    fileName)));

            if (result.getFile() == null) {
                // ファイル書き込みを拒否された場合
                if (!dir.equals(new File(spareDirectory))) {
                    // 作成試行が一時ディレクトリでない場合
                    dir = createDirectory(spareDirectory);
                    if (dir == null) {
                        // 一時ディレクトリの作成に失敗
                        result
                                .setResult(ACFileUtilitiesCreateResult.ERROR_OF_DIRECTORY_CREATE);
                    } else {
                        // 一時ディレクトリで作成試行
                        result.setFile(getCreatableFilePath(new File(dir
                                .getAbsolutePath(), fileName)));
                        if (result.getFile() == null) {
                            result
                                    .setResult(ACFileUtilitiesCreateResult.ERROR_OF_FILE_CREATE);
                        } else {
                            // 一時ディレクトリには作成可能
                            result
                                    .setResult(ACFileUtilitiesCreateResult.SUCCESS_ON_SPARE_DIRECTORY);
                        }
                    }
                }
            }
        }
        return result;
    }

    /**
     * ファイルを作成可能か検証し、作成可能であればそのファイルを返します。
     * <p>
     * 作成を拒否された場合、nullを返します。
     * </p>
     * 
     * @param f ファイル
     * @return ファイル
     */
    public static File getCreatableFilePath(File f) {
        if (f.exists()) {
            // ファイルは既存
            if (f.canWrite()) {
                // 上書き可能ならパスを返す
                return f;
            }
        } else {
            // ファイル作成を試行
            try {
                if (f.createNewFile()) {
                    // ファイル作成に成功
                    return f;
                }
            } catch (SecurityException ex) {
                // セキュリティによる作成拒否
            } catch (IOException ex) {
                // 入出力例外
            }
        }
        // ファイル作成不能
        return null;
    }

    /**
     * 指定パスのフォルダがなければ作成します。
     * 
     * @param dir フォルダパス
     * @param defaultDir 作成失敗時の代用フォルダパス
     * @return 成功した場合はファイルオブジェクト。失敗ならnull。
     */
    public static File createDirectory(String dir, String defaultDir) {
        File f = createDirectory(dir);
        if (f == null) {
            f = createDirectory(defaultDir);
        }
        return f;
    }

    /**
     * 指定パスのフォルダがなければ作成します。
     * 
     * @param dir フォルダパス
     * @return 成功した場合はファイルオブジェクト。失敗ならnull。
     */
    public static File createDirectory(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            // ディレクトリ作成
            if (!f.mkdirs()) {
                return null;
            }
        }
        return f;
    }
    
    /**
     * 二つのファイル配列を比較し、名称が異なるものを抜き出します。
     * <p>
     * ファイルの増減比較に使用します。<br/>
     * ファイル名のみ比較しますので、タイムスタンプが異なっても同名なら同一と見なします。<br/>
     * 完全に一致した場合は要素数0個のFile配列を返します。
     * <p/>
     * 
     * @param files1 比較元1
     * @param files2 比較元2
     * @return 比較結果
     */
    public static File[] compareFileNames(File[] files1, File[] files2) {
        // 比較しやすいよう1つの配列にまとめる
        int len1 = files1.length;
        int len2 = files2.length;
        int fullLen = len1 + len2;
        File[] full = new File[fullLen];
        System.arraycopy(files1, 0, full, 0, len1);
        System.arraycopy(files2, 0, full, len1, len2);

        // 不一致状態を配列上のフラグで管理
        boolean[] modified = new boolean[fullLen];
        Arrays.fill(modified, true);

        // 比較
        for (int i = 0; i < len1; i++) {
            String f1Name = full[i].getName().toLowerCase();
            for (int j = len1; j < fullLen; j++) {
                if (modified[j]) {
                    if (f1Name.equals(full[j].getName().toLowerCase())) {
                        // 同名
                        modified[i] = false;
                        modified[j] = false;
                        break;
                    }
                }
            }
        }

        // 最後まで不一致だったものを抜き出す
        List result = new ArrayList();
        for (int i = 0; i < fullLen; i++) {
            if (modified[i]) {
                result.add(full[i]);
            }
        }
        fullLen = result.size();
        full = new File[fullLen];
        System.arraycopy(result.toArray(), 0, full, 0, fullLen);

        return full;
    }
    
    /**
     * ファイル集合の中で、最も更新日時が新しいファイルを返します。
     * <p>
     * 存在しないファイルは除外して比較します。<br />
     * すべてのファイルが存在しない場合、nullが返ります。
     * </p>
     * @param files 比較元
     * @return 最も更新日時が新しいファイル
     */
    public static File getMostLastModifiedFile(File[] files){
        File lastFile = null;
        long lastFileTime = 0;
        int end = files.length;
        for(int i=0; i<end; i++){
            if(files[i].exists()){
                long time =files[i].lastModified(); 
                if(time>lastFileTime){
                    lastFile =files[i];
                    lastFileTime = time;
                }
            }
        }
        return lastFile;
    }
    
    /**
     * ファイルをコピーします。
     * @param from コピー元
     * @param to コピー先
     * @return 成功したか
     * @throws IOException 入出力例外
     */
    public static boolean copyFile(String from, String to) throws IOException {
        return copyFile(new File(from), new File(to));
    }
    /**
     * ファイルをコピーします。
     * @param from コピー元
     * @param to コピー先
     * @return 成功したか
     * @throws IOException 入出力例外
     */
    public static boolean copyFile(File from, File to) throws IOException {
        if((from==null)||(!from.exists())||(!from.canRead())){
            //存在しないか読み込み不能ならfalse
            return false;
        }
        FileInputStream in = new FileInputStream(from);
        FileOutputStream out = new FileOutputStream(to);
        int ch;
        while ((ch = in.read()) != -1) {
            out.write(ch);
        }
        in.close();
        out.close();
        return true;
    }    
}
