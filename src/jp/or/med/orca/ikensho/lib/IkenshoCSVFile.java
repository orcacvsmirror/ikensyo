package jp.or.med.orca.ikensho.lib;

import jp.nichicom.vr.io.VRCSVFile;

public class IkenshoCSVFile extends VRCSVFile {

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param path �t�@�C���p�X
     */
    public IkenshoCSVFile(String path) {
        this(path, VRCSVFile.SJIS);
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param path �t�@�C���p�X
     * @param encode �G���R�[�h
     */
    public IkenshoCSVFile(String path, String encode) {
        super(path, encode);
        setLineSeparator("\r\n");
    }

    protected String createWriteData(boolean putHeader, boolean canOverride) {
        String data = super.createWriteData(putHeader, canOverride);
        // �I���R�[�h
        char[] eof = new char[] { 0x0A };

        // �����ɏI���R�[�h��t�����ďo��
        return data + new String(eof);
    }
}
