package jp.nichicom.ac.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * �t�@�C���֘A�̔ėp���\�b�h���W�߂��N���X�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/04/14
 */
public class ACFileUtilities {

    /**
     * �t�@�C�����쐬�\�����؂��A�쐬���s���ʂ�Ԃ��܂��B
     * <p>
     * �w��t�H���_�p�X�̍쐬�Ɏ��s�����ꍇ�A�\���t�H���_�p�X�ł̍쐬���s���s���܂��B
     * </p>
     * @param fileName �t�@�C����
     * @param directory �t�H���_�p�X
     * @param spareDirectory �\���t�H���_�p�X
     * @return �쐬���s����
     */
    public static ACFileUtilitiesCreateResult getCreatableFilePath(
            String fileName, String directory, String spareDirectory) {
        ACFileUtilitiesCreateResult result = new ACFileUtilitiesCreateResult();
        result
                .setResult(ACFileUtilitiesCreateResult.SUCCESS_ON_TARGET_DIRECTORY);
        // �V�X�e���K��̏ꏊ�������͈ꎞ�f�B���N�g�����쐬
        File dir = createDirectory(directory, spareDirectory);
        if (dir == null) {
            // �ꎞ�f�B���N�g���̍쐬�Ɏ��s
            result
                    .setResult(ACFileUtilitiesCreateResult.ERROR_OF_DIRECTORY_CREATE);
        } else {
            result.setFile(getCreatableFilePath(new File(dir.getAbsolutePath(),
                    fileName)));

            if (result.getFile() == null) {
                // �t�@�C���������݂����ۂ��ꂽ�ꍇ
                if (!dir.equals(new File(spareDirectory))) {
                    // �쐬���s���ꎞ�f�B���N�g���łȂ��ꍇ
                    dir = createDirectory(spareDirectory);
                    if (dir == null) {
                        // �ꎞ�f�B���N�g���̍쐬�Ɏ��s
                        result
                                .setResult(ACFileUtilitiesCreateResult.ERROR_OF_DIRECTORY_CREATE);
                    } else {
                        // �ꎞ�f�B���N�g���ō쐬���s
                        result.setFile(getCreatableFilePath(new File(dir
                                .getAbsolutePath(), fileName)));
                        if (result.getFile() == null) {
                            result
                                    .setResult(ACFileUtilitiesCreateResult.ERROR_OF_FILE_CREATE);
                        } else {
                            // �ꎞ�f�B���N�g���ɂ͍쐬�\
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
     * �t�@�C�����쐬�\�����؂��A�쐬�\�ł���΂��̃t�@�C����Ԃ��܂��B
     * <p>
     * �쐬�����ۂ��ꂽ�ꍇ�Anull��Ԃ��܂��B
     * </p>
     * 
     * @param f �t�@�C��
     * @return �t�@�C��
     */
    public static File getCreatableFilePath(File f) {
        if (f.exists()) {
            // �t�@�C���͊���
            if (f.canWrite()) {
                // �㏑���\�Ȃ�p�X��Ԃ�
                return f;
            }
        } else {
            // �t�@�C���쐬�����s
            try {
                if (f.createNewFile()) {
                    // �t�@�C���쐬�ɐ���
                    return f;
                }
            } catch (SecurityException ex) {
                // �Z�L�����e�B�ɂ��쐬����
            } catch (IOException ex) {
                // ���o�͗�O
            }
        }
        // �t�@�C���쐬�s�\
        return null;
    }

    /**
     * �w��p�X�̃t�H���_���Ȃ���΍쐬���܂��B
     * 
     * @param dir �t�H���_�p�X
     * @param defaultDir �쐬���s���̑�p�t�H���_�p�X
     * @return ���������ꍇ�̓t�@�C���I�u�W�F�N�g�B���s�Ȃ�null�B
     */
    public static File createDirectory(String dir, String defaultDir) {
        File f = createDirectory(dir);
        if (f == null) {
            f = createDirectory(defaultDir);
        }
        return f;
    }

    /**
     * �w��p�X�̃t�H���_���Ȃ���΍쐬���܂��B
     * 
     * @param dir �t�H���_�p�X
     * @return ���������ꍇ�̓t�@�C���I�u�W�F�N�g�B���s�Ȃ�null�B
     */
    public static File createDirectory(String dir) {
        File f = new File(dir);
        if (!f.exists()) {
            // �f�B���N�g���쐬
            if (!f.mkdirs()) {
                return null;
            }
        }
        return f;
    }
    
    /**
     * ��̃t�@�C���z����r���A���̂��قȂ���̂𔲂��o���܂��B
     * <p>
     * �t�@�C���̑�����r�Ɏg�p���܂��B<br/>
     * �t�@�C�����̂ݔ�r���܂��̂ŁA�^�C���X�^���v���قȂ��Ă������Ȃ瓯��ƌ��Ȃ��܂��B<br/>
     * ���S�Ɉ�v�����ꍇ�͗v�f��0��File�z���Ԃ��܂��B
     * <p/>
     * 
     * @param files1 ��r��1
     * @param files2 ��r��2
     * @return ��r����
     */
    public static File[] compareFileNames(File[] files1, File[] files2) {
        // ��r���₷���悤1�̔z��ɂ܂Ƃ߂�
        int len1 = files1.length;
        int len2 = files2.length;
        int fullLen = len1 + len2;
        File[] full = new File[fullLen];
        System.arraycopy(files1, 0, full, 0, len1);
        System.arraycopy(files2, 0, full, len1, len2);

        // �s��v��Ԃ�z���̃t���O�ŊǗ�
        boolean[] modified = new boolean[fullLen];
        Arrays.fill(modified, true);

        // ��r
        for (int i = 0; i < len1; i++) {
            String f1Name = full[i].getName().toLowerCase();
            for (int j = len1; j < fullLen; j++) {
                if (modified[j]) {
                    if (f1Name.equals(full[j].getName().toLowerCase())) {
                        // ����
                        modified[i] = false;
                        modified[j] = false;
                        break;
                    }
                }
            }
        }

        // �Ō�܂ŕs��v���������̂𔲂��o��
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
     * �t�@�C���W���̒��ŁA�ł��X�V�������V�����t�@�C����Ԃ��܂��B
     * <p>
     * ���݂��Ȃ��t�@�C���͏��O���Ĕ�r���܂��B<br />
     * ���ׂẴt�@�C�������݂��Ȃ��ꍇ�Anull���Ԃ�܂��B
     * </p>
     * @param files ��r��
     * @return �ł��X�V�������V�����t�@�C��
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
     * �t�@�C�����R�s�[���܂��B
     * @param from �R�s�[��
     * @param to �R�s�[��
     * @return ����������
     * @throws IOException ���o�͗�O
     */
    public static boolean copyFile(String from, String to) throws IOException {
        return copyFile(new File(from), new File(to));
    }
    /**
     * �t�@�C�����R�s�[���܂��B
     * @param from �R�s�[��
     * @param to �R�s�[��
     * @return ����������
     * @throws IOException ���o�͗�O
     */
    public static boolean copyFile(File from, File to) throws IOException {
        if((from==null)||(!from.exists())||(!from.canRead())){
            //���݂��Ȃ����ǂݍ��ݕs�\�Ȃ�false
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
