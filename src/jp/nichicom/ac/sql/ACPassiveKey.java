package jp.nichicom.ac.sql;

import java.text.Format;

import jp.nichicom.ac.text.ACSQLSafeStringFormat;

/**
 * �p�b�V�u�^�X�N�p�̃f�[�^����L�[�ł��B
 * <p>
 * ���̃L�[��񂩂�A�p�b�V�u�`�F�b�N�p��WHERE����\�����܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACPassiveKey {
    protected String table;
    protected String[] fields;
    protected Format[] formats;
    protected String clientPassiveTimeField;
    protected String serverPassiveTimeField;

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param table ��e�[�u����
     * @param fields ��L�[��
     * @param formats �L�[�l�����񉻃t�H�[�}�b�g
     * @param clientPassiveTimeField �ޔ��f�[�^�ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     * @param serverPassiveTimeField ��r��e�[�u���ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     */
    public ACPassiveKey(String table, String[] fields, Format[] formats,
            String clientPassiveTimeField, String serverPassiveTimeField) {
        this.table = table;
        this.fields = fields;
        this.formats = formats;
        this.clientPassiveTimeField = clientPassiveTimeField;
        this.serverPassiveTimeField = serverPassiveTimeField;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param table ��e�[�u����
     * @param fields ��L�[��
     * @param quotes �L�[�l��''�Ŋ���ׂ�������ł��邩
     * @param clientPassiveTimeField �ޔ��f�[�^�ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     * @param serverPassiveTimeField ��r��e�[�u���ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     */
    public ACPassiveKey(String table, String[] fields, boolean[] quotes,
            String clientPassiveTimeField, String serverPassiveTimeField) {
        int end = quotes.length;
        Format[] f = new Format[end];
        for (int i = 0; i < end; i++) {
            f[i] = quotes[i] ? ACSQLSafeStringFormat.getInstance() : null;
        }
        this.table = table;
        this.fields = fields;
        this.formats = f;
        this.clientPassiveTimeField = clientPassiveTimeField;
        this.serverPassiveTimeField = serverPassiveTimeField;
    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param table ��e�[�u����
     * @param fields ��L�[��
     * @param clientPassiveTimeField �ޔ��f�[�^�ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     * @param serverPassiveTimeField ��r��e�[�u���ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     */
    public ACPassiveKey(String table, String[] fields,
            String clientPassiveTimeField, String serverPassiveTimeField) {
        this(table, fields, (Format[]) null, clientPassiveTimeField,
                serverPassiveTimeField);
    }

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACPassiveKey() {
    }

    public boolean equals(Object o) {
        if (o instanceof ACPassiveKey) {
            ACPassiveKey x = (ACPassiveKey) o;
            if (table.equals(x.table) && fields.equals(x.fields)) {
                if (formats == null) {
                    return x.formats == null;
                } else {
                    return formats.equals(x.formats);
                }
            }
        }
        return false;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("([table=");
        sb.append(table);
        sb.append("][fields=(");
        int end = fields.length;
        if (end > 0) {
            sb.append(fields[0]);
            for (int i = 1; i < end; i++) {
                sb.append(",");
                sb.append(fields[i]);
            }
        }
        sb.append(")][formats=(");
        end = formats.length;
        if (end > 0) {
            sb.append(formats[0]);
            for (int i = 1; i < end; i++) {
                sb.append(",");
                sb.append(formats[i]);
            }
        }
        sb.append(")])");
        return sb.toString();
    }

    /**
     * ��L�[����Ԃ��܂��B
     * 
     * @return ��L�[��
     */
    public String[] getFields() {
        return fields;
    }

    /**
     * �L�[�l�����񉻃t�H�[�}�b�g��Ԃ��܂��B
     * 
     * @return �L�[�l�����񉻃t�H�[�}�b�g
     */
    public Format[] getFormats() {
        return formats;
    }

    /**
     * ��e�[�u������Ԃ��܂��B
     * 
     * @return ��e�[�u����
     */
    public String getTable() {
        return table;
    }

    /**
     * ��r��e�[�u���ɂ�����p�b�V�u�`�F�b�N�p�����L�[����Ԃ��܂��B
     * 
     * @return �ޔ�r��e�[�u���ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     */
    public String getServerPassiveTimeField() {
        return serverPassiveTimeField;
    }

    /**
     * �ޔ��f�[�^�ɂ�����p�b�V�u�`�F�b�N�p�����L�[����Ԃ��܂��B
     * 
     * @return �ޔ��f�[�^�ɂ�����p�b�V�u�`�F�b�N�p�����L�[��
     */
    public String getClientPassiveTimeField() {
        return clientPassiveTimeField;
    }
}
