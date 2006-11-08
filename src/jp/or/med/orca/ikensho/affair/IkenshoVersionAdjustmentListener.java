package jp.or.med.orca.ikensho.affair;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.version.ACDefaultVersionAdjustmentListener;
import jp.nichicom.ac.sql.ACDBManager;

/**
 * �o�[�W������񍷈ٕ␳���X�i�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Tozo Tanaka
 * @version 1.0 2006/07/05
 */
public class IkenshoVersionAdjustmentListener extends
        ACDefaultVersionAdjustmentListener {

    /**
     * �R���X�g���N�^�ł��B
     */
    public IkenshoVersionAdjustmentListener() {
        super();
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param dbm DB�}�l�[�W��
     */
    public IkenshoVersionAdjustmentListener(ACDBManager dbm) {
        super(dbm);
    }

    protected String getDBVersionTableName() {
        return "IKENSYO_VERSION";
    }
    protected String getSystemVersionKey() {
        return "Version/no";
    }
    protected String getMasterDataVersionKey() {
        return "DATA_VERSION";
    }
    protected String getSchemeVersionKey() {
        return "SCHEMA_VERSION";
    }
    protected void setSystemVersion(String version) throws Exception {
        super.setSystemVersion(version);
        ACFrame.getInstance().getPropertyXML().setForceValueAt("Version/no", version);
    }
}
