package jp.nichicom.ac.io;

import java.io.File;

/**
 * ファイル関連の汎用メソッドを集めたクラスにおける、生成試行結果です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/04/14
 */
public class ACFileUtilitiesCreateResult {
    /**
     * フォルダを作成不可能であることをあらわす作成試行結果定数です。
     */
    public static final int ERROR_OF_DIRECTORY_CREATE = 1;
    /**
     * ファイルを作成不可能であることをあらわす作成試行結果定数です。
     */
    public static final int ERROR_OF_FILE_CREATE = 2;
    /**
     * 指定のフォルダでファイルを作成可能であることをあらわす作成試行結果定数です。
     */
    public static final int SUCCESS_ON_TARGET_DIRECTORY = 0;
    /**
     * 予備フォルダでファイルを作成可能であることをあらわす作成試行結果定数です。
     */
    public static final int SUCCESS_ON_SPARE_DIRECTORY = -1;

    private File file;
    private int result;
    /**
     * コンストラクタです。
     */
    public ACFileUtilitiesCreateResult() {

    }

    /**
     * 生成可能なファイルオブジェクト を返します。
     * 
     * @return 生成可能なファイルオブジェクト
     */
    public File getFile() {
        return file;
    }

    /**
     * 生成試行結果 を返します。
     * 
     * @return 生成試行結果
     */
    public int getResult() {
        return result;
    }

    /**
     * 生成可能なファイルオブジェクト を設定します。
     * 
     * @param file 生成可能なファイルオブジェクト
     */
    public void setFile(File file) {
        this.file = file;
    }

    /**
     * 生成試行結果 を設定します。
     * 
     * @param result 生成試行結果
     */
    public void setResult(int result) {
        this.result = result;
    }
    /**
     * ファイル作成可能かを返します。
     * @return ファイル作成可能か
     */
    public boolean isSuccess(){
        switch(getResult()){
        case SUCCESS_ON_TARGET_DIRECTORY:
        case SUCCESS_ON_SPARE_DIRECTORY:
            return true;
        }
        return false;
    }
}
