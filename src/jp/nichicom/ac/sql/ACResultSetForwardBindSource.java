package jp.nichicom.ac.sql;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.VRLinkedHashMap;
import jp.nichicom.vr.util.VRList;

/**
 * JDBC��ResultSet��VR�@�\��z�肵���f�[�^�\���֕ϊ����郉�C�u�����ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2005/12/01
 */

public class ACResultSetForwardBindSource {

    /**
     * java.sql.ResultSet ���� VRArrayList �ւ̓]���������s���܂��B
     * <p>
     * �Ԃ�l�̓t�B�[���h���F�l�̊֌W����Ȃ� VRHashMap ��񋓂��� VRArrayList �ł��B
     * </p>
     * 
     * @param rs SQL ���̎��s����
     * @return �]����̃I�u�W�F�N�g
     * @throws SQLException �]�����s���̗�O
     */
    public static VRList forward(ResultSet rs) throws SQLException {
        VRArrayList tbl = new VRArrayList();
        VRBindSource row = null;

        ResultSetMetaData rsmeta = rs.getMetaData();
        int colCount = rsmeta.getColumnCount();
        String[] fields = new String[colCount];

        // ResultSet �̗񖼂ƃ^�C�v���擾
        for (int i = 0; i < colCount; i++) {
            fields[i] = rsmeta.getColumnName(i + 1);
        }

        // ResultSet �̓��e��]��
        while (rs.next()) {

            row = new VRLinkedHashMap();

            // �擾�����񕪃��[�v���A���ʂ� VRHashMap �ɐݒ肷��B
            for (int i = 0; i < colCount; i++) {
                row.setData(fields[i], rs.getObject(fields[i]));
            }
            // ��f�[�^��ݒ肵�� VRHashMap �� VRArrayList �ɐݒ肷��B
            tbl.addData(row);
        }

        return tbl;
    }
}