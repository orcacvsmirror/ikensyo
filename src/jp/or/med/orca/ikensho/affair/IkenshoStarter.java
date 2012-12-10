package jp.or.med.orca.ikensho.affair;

//import jp.nichicom.ac.core.ACClassLoader;

public class IkenshoStarter {
    public static void main(String[] args) {
//        try {
//            ACClassLoader.executeMain(
//                    "jp.or.med.orca.ikensho.affair.IkenshoMainMenu",
//                    ACClassLoader.toURL(new String[] {
//                            "lib/firebirdsql-full.jar", "lib/iText.jar",
//                            "lib/iTextAsian.jar", "lib/vr-impl.jar",
//                            "lib/bridge.jar" }), new String[] { "jp" });
//        } catch (Throwable ex) {
//            ex.printStackTrace();
//        }
    	IkenshoMainMenu.main(args);
    }

}