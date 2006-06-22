package jp.or.med.orca.ikensho.pdf;

import jp.nichicom.ac.core.ACClassLoader;

public class IkenshoPDFCustomStarter {
  public static void main(String[] args) {
    try {
        ACClassLoader.executeMain(
                "jp.or.med.orca.ikensho.pdf.ip.ip001.IP001",
                ACClassLoader.toURL(new String[] {
                        "lib/firebirdsql-full.jar", "lib/iText.jar",
                        "lib/iTextAsian.jar", "lib/vr-impl.jar",
                        "lib/bridge.jar", "Ikensyo.jar" }), new String[] { "jp" });
    } catch (Throwable ex) {
        ex.printStackTrace();
    }
}

}
