package jp.or.med.orca.ikensho.convert;

//import jp.nichicom.ac.core.ACClassLoader;

/**
 * <p>タイトル: </p>
 *
 * <p>説明: </p>
 *
 * <p>著作権: Copyright (c) 2005</p>
 *
 * <p>会社名: </p>
 *
 * @author 未入力
 * @version 1.0
 */
public class IkenshoMacConvertStarter {
	public static void main(String[] args) {
//		try {
//			ACClassLoader.executeMain(
//					"jp.or.med.orca.ikensho.convert.IkenshoMacConvert",
//					ACClassLoader.toURL(new String[] {
//							"lib/firebirdsql-full.jar", "lib/vr-impl.jar",
//							"ikensyo.jar", }), new String[] { "jp" });
//		} catch (Throwable ex) {
//			ex.printStackTrace();
//		}
		IkenshoMacConvert.main(args);
	}

}
