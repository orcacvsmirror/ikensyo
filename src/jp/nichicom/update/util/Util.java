package jp.nichicom.update.util;

import java.io.File;

/**
 * �A�b�v�f�[�g�c�[���@���[�e�B���e�B�N���X
 * @version 1.00 4 August
 * @author shin fujihara
 */
public class Util {

	public static boolean isNull(String str){
		return (str == null) || ("".equals(trim(str)));
	}
	/**
	 * ������O��̋󔒁A���s����菜��
	 * @param str �Ώۃf�[�^
	 * @return ������̕�����
	 */
	public static String trim(String str){
		if(str == null) return "";
		
		char[] temp = str.toCharArray();
		int start = -1;
		int end = -1;
		//�󔒂Ƃ݂Ȃ�����
		String comp = " " + "�@" + "\n";
		
		for(int i = 0; i < temp.length; i++){
			if(comp.indexOf(temp[i]) == -1){
				start = i;
				break;
			}
		}
		//�S�ċ�
		if(start == -1) return "";
		
		for(int i = temp.length - 1; i >= 0; i--){
			if(comp.indexOf(temp[i]) == -1){
				end = i;
				break;
			}
		}
		return str.substring(start,end + 1);
	}
	/**
	 * �w�肳�ꂽ�p�X�̃f�B���N�g�����쐬����B
	 * @param fileName �t�@�C���p�X
	 */
	public static void makeDir(String fileName){
		if(isNull(fileName)){
			return;
		}
	    File file = new File(fileName);
	    //�t�H���_������΁A�쐬����
	    if(file.getParent() != null){
	    	File dir = new File(file.getParent());
	    	if(!dir.exists()){
		    	dir.mkdirs();
	    	}
	    }
	}
}
