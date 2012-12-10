package jp.nichicom.vr.component;

import java.io.IOException;
import java.net.URL;

/**
 * VREditorPaneです。
 * <p>
 * Copyright (c) 2006 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * @author Masahiko Higuchi
 * @version 1.0 2007/10/06
 * @see jp.nichicom.vr.component.AbstractVREditorPane
 */
public class VREditorPane extends AbstractVREditorPane {

    /**
     * コンストラクタです。
     */
    public VREditorPane() {
        super();
    }

    /**
     * コンストラクタです。
     * @param url
     * @throws IOException
     */
    public VREditorPane(String url) throws IOException {
        super(url);
    }

    /**
     * コンストラクタです。
     * @param initialPage
     * @throws IOException
     */
    public VREditorPane(URL initialPage) throws IOException {
        super(initialPage);
    }

    /**
     * コンストラクタです。
     * @param type
     * @param text
     */
    public VREditorPane(String type, String text) {
        super(type, text);
    }

}
