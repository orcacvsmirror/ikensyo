package jp.or.med.orca.ikensho.convert;

import jp.nichicom.ac.core.ACClassLoader;

public class IkenshoShijishoCSVImportStarter {
    public static void main(String[] args) {
        try {
            ACClassLoader.executeMain(
                    "jp.or.med.orca.qkan.convert.QkanMacCSVImport",
                    ACClassLoader.toURL(new String[] {
                            "lib/firebirdsql-full.jar", "lib/iText.jar",
                            "lib/iTextAsian.jar", "lib/vr-impl.jar",
                            "lib/bridge.jar","lib/ac-lib-impl.jar","Qkan.jar" }), new String[] { "jp" });
        } catch (Throwable ex) {
            ex.printStackTrace();
        }
    }
}
