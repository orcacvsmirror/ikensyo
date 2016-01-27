package jp.nichicom.ac.core.version;

import java.util.Map;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.ac.sql.ACDBManager;
import jp.nichicom.ac.util.splash.ACSplash;
import jp.nichicom.ac.util.splash.ACSplashable;
import jp.nichicom.vr.util.VRList;

/**
 * �f�t�H���g�̃o�[�W������񍷈ٕ␳���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/28
 */
public class ACDefaultVersionAdjustmentListener implements
        ACVersionAdjustmentListener {
    private ACDBManager dbm;
    private String masterDataVersion;
    private String schemeVersion;
    private ACSplashable progressSplash;
    private boolean useSplash = true;

    public ACSplashable getProgressSplash() {
        if (isUseSplash()) {
            if (progressSplash == null) {
                ACFrameEventProcesser processer = ACFrame.getInstance()
                        .getFrameEventProcesser();
                if (processer != null) {
                    try {
                        progressSplash = processer.createSplash("");
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
            return progressSplash;
        }
        return null;
    }

    /**
     * �i���񍐗p�̃X�v���b�V�� ��ݒ肵�܂��B
     * 
     * @param splash �i���񍐗p�̃X�v���b�V��
     */
    public void setProgressSplash(ACSplash progressSplash) {
        this.progressSplash = progressSplash;
    }

    /**
     * �i���񍐗p�̃X�v���b�V�����g�p���邩 ��Ԃ��܂��B
     * 
     * @return useSplash
     */
    public boolean isUseSplash() {
        return useSplash;
    }

    /**
     * �i���񍐗p�̃X�v���b�V�����g�p���邩 ��ݒ肵�܂��B
     * 
     * @param useSplash �i���񍐗p�̃X�v���b�V�����g�p���邩
     */
    public void setUseSplash(boolean useSplash) {
        this.useSplash = useSplash;
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACDefaultVersionAdjustmentListener() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param dbm DB�}�l�[�W��
     */
    public ACDefaultVersionAdjustmentListener(ACDBManager dbm) {
        super();
        setDBManager(dbm);
    }

    /**
     * DB�}�l�[�W�� ��Ԃ��܂��B
     * 
     * @return DB�}�l�[�W��
     */
    public ACDBManager getDBManager() {
        return dbm;
    }

    public String getVersion(String key) {
        try {
            if ("system".equals(key)) {
                // �V�X�e���o�[�W����
                return getSystemVersion();
            } else if ("scheme".equals(key)) {
                // �X�L�[�}�o�[�W����
                return getSchemeVersion();
            } else if ("master".equals(key)) {
                // �}�X�^�f�[�^�o�[�W����
                return getMasterDataVersion();
            }
        } catch (Exception ex) {
        }
        return null;
    }

    /**
     * DB�}�l�[�W�� ��ݒ肵�܂��B
     * 
     * @param manager DB�}�l�[�W��
     */
    public void setDBManager(ACDBManager dbm) {
        this.dbm = dbm;
    }

    public boolean setVersion(String key, String value) {
        try {
            if ("system".equals(key)) {
                // �V�X�e���o�[�W����
                setSystemVersion(value);
            } else if ("scheme".equals(key)) {
                // �X�L�[�}�o�[�W����
                setSchemeVersion(value);
            } else if ("master".equals(key)) {
                // �}�X�^�f�[�^�o�[�W����
                setMasterDataVersion(value);
            } else {
                return false;
            }
        } catch (Exception ex) {
            return false;
        }
        return true;
    }

    /**
     * DB�̃o�[�W���������擾����SQL�N�G�����擾���܂��B
     * 
     * @return DB�̃o�[�W���������擾����SQL�N�G��
     */
    protected String getDBVersionQuerySQL() {
        return "SELECT * FROM " + getDBVersionTableName();
    }

    /**
     * DB�̃o�[�W���������i�[����e�[�u�������擾���܂��B
     * 
     * @return DB�̃o�[�W���������i�[����e�[�u����
     */
    protected String getDBVersionTableName() {
        return "M_VERSION";
    }

    /**
     * �}�X�^�f�[�^�o�[�W���� ��Ԃ��܂��B
     * 
     * @throws Exception ������O
     * @return �}�X�^�f�[�^�o�[�W����
     */
    protected String getMasterDataVersion() throws Exception {
        if (masterDataVersion == null) {
            refreshDBVersion();
        }
        return masterDataVersion;
    }

    /**
     * �}�X�^�f�[�^�o�[�W����������킷�t�B�[���h����Ԃ��܂��B
     * 
     * @return �}�X�^�f�[�^�o�[�W����������킷�t�B�[���h��
     */
    protected String getMasterDataVersionKey() {
        return "MASTER_DATA_VERSION";
    }

    /**
     * �}�X�^�f�[�^�o�[�W�������X�V����SQL���擾���܂��B
     * 
     * @param version �o�[�W����
     * @return �}�X�^�f�[�^�o�[�W�������X�V����SQL
     */
    protected String getMasterDataVersionUpdateSQL(String version) {
        return "UPDATE " + getDBVersionTableName() + " SET "
                + getMasterDataVersionKey() + " = '" + version + "'";
    }

    /**
     * �X�L�[�}�o�[�W���� ��Ԃ��܂��B
     * 
     * @throws Exception ������O
     * @return �X�L�[�}�o�[�W����
     */
    protected String getSchemeVersion() throws Exception {
        if (schemeVersion == null) {
            refreshDBVersion();
        }
        return schemeVersion;
    }

    /**
     * �X�L�[�}�o�[�W����������킷�t�B�[���h����Ԃ��܂��B
     * 
     * @return �X�L�[�}�o�[�W����������킷�t�B�[���h��
     */
    protected String getSchemeVersionKey() {
        return "SCHEME_VERSION";
    }

    /**
     * �X�L�[�}�o�[�W�������X�V����SQL���擾���܂��B
     * 
     * @param version �o�[�W����
     * @return �X�L�[�}�o�[�W�������X�V����SQL
     */
    protected String getSchemeVersionUpdateSQL(String version) {
        return "UPDATE " + getDBVersionTableName() + " SET "
                + getSchemeVersionKey() + " = '" + version + "'";
    }

    /**
     * �V�X�e���̃o�[�W��������Ԃ��܂��B
     * 
     * @return �V�X�e���̃o�[�W�������
     * @throws Exception ������O
     */
    protected String getSystemVersion() throws Exception {
        return ACFrame.getInstance().getProperty(getSystemVersionKey());
    }

    /**
     * �V�X�e���̃o�[�W����������킷�o�C���h�p�X��Ԃ��܂��B
     * 
     * @return �V�X�e���̃o�[�W����������킷�o�C���h�p�X
     */
    protected String getSystemVersionKey() {
        return "Version/No";
    }

    /**
     * DB�o�[�W���������擾���܂��B
     * 
     * @throws Exception ������O
     */
    protected void refreshDBVersion() throws Exception {
        VRList list = getDBManager().executeQuery(getDBVersionQuerySQL());
        if (!list.isEmpty()) {
            Map map = (Map) list.getData();
            Object obj = map.get(getSchemeVersionKey());
            if (obj != null) {
                schemeVersion = String.valueOf(obj);
            }
            obj = map.get(getMasterDataVersionKey());
            if (obj != null) {
                masterDataVersion = String.valueOf(obj);
            }
        }
    }

    /**
     * �}�X�^�f�[�^�o�[�W���� ��ݒ肵�܂��B
     * 
     * @param masterVersion �}�X�^�f�[�^�o�[�W����
     * @throws Exception ������O
     */
    protected void setMasterDataVersion(String masterVersion) throws Exception {
        getDBManager().executeUpdate(
                getMasterDataVersionUpdateSQL(masterVersion));
        this.masterDataVersion = masterVersion;
    }

    /**
     * �X�L�[�}�o�[�W���� ��ݒ肵�܂��B
     * 
     * @param schemeVersion �X�L�[�}�o�[�W����
     * @throws Exception ������O
     */
    protected void setSchemeVersion(String schemeVersion) throws Exception {
        getDBManager().executeUpdate(getSchemeVersionUpdateSQL(schemeVersion));
        this.schemeVersion = schemeVersion;
    }

    /**
     * �V�X�e���̃o�[�W��������ݒ肵�܂��B
     * 
     * @param �V�X�e���̃o�[�W�������
     * @throws Exception ������O
     */
    protected void setSystemVersion(String version) throws Exception {
        ACFrame.getInstance().getPropertyXML().setValueAt(
                getSystemVersionKey(), version);
        ACFrame.getInstance().getPropertyXML().write();
    }

}
