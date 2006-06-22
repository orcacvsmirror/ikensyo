package jp.or.med.orca.ikensho.lib;

import jp.nichicom.vr.io.VRCSVFile;

public class IkenshoCSVFile extends VRCSVFile {

    /**
     * コンストラクタです。
     * 
     * @param path ファイルパス
     */
    public IkenshoCSVFile(String path) {
        this(path, VRCSVFile.SJIS);
    }

    /**
     * コンストラクタです。
     * 
     * @param path ファイルパス
     * @param encode エンコード
     */
    public IkenshoCSVFile(String path, String encode) {
        super(path, encode);
        setLineSeparator("\r\n");
    }

    protected String createWriteData(boolean putHeader, boolean canOverride) {
        String data = super.createWriteData(putHeader, canOverride);
        // 終了コード
        char[] eof = new char[] { 0x0A };

        // 末尾に終了コードを付加して出力
        return data + new String(eof);
    }
}
