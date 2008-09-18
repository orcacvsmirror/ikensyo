package jp.or.med.orca.ikensho.component;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * property�t�@�C������N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class PropertyUtil {
	
	/**
	 * �A�v���P�[�V�������Ŏg�p����v���p�e�B�t�@�C�����̂̐ݒ�
	 */
	private static String propertyFileName = "Application.properties";
	
	/**
	 * �v���p�e�B�t�@�C�����̂�ݒ肷��
	 * @param name
	 */
	public static void setPropertyFileName(String name){
		propertyFileName = name;
	}
	
	/**
	 * �v���p�e�B�l���擾����
	 * @param str �L�[
	 * @return �擾�����l
	 */
	public static String getProperty(String str){
		Properties prop = new Properties();
		// �v���p�e�B�t�@�C������L�[�ƒl�̃��X�g��ǂݍ��݂܂�
		try {
			prop.load(new FileInputStream(propertyFileName));
		} catch (IOException e) {
			//Log.warning("�v���p�e�B�̎擾�Ɏ��s���܂����B\n" + e.getLocalizedMessage());
			return "";
		}
		return prop.getProperty(str);
	}
	
	/**
	 * �v���p�e�B�l��ۑ�����
	 * @param key �L�[
	 * @param str �ݒ�l
	 */
	public static void setProperty(String key,String str){
		Properties prop = new Properties();
		// �v���p�e�B�t�@�C���ɕۑ����܂��B
		try {
		    // �v���p�e�B�t�@�C������L�[�ƒl�̃��X�g��ǂݍ��݂܂�
		    prop.load(new FileInputStream(propertyFileName));
			prop.setProperty(key,str);
			prop.store(new FileOutputStream(propertyFileName),propertyFileName);
		} catch (IOException e) {
			//Log.warning("�v���p�e�B�̐ݒ�Ɏ��s���܂����B\n" + e.getLocalizedMessage());
		}
	}
}
