package jp.nichicom.ac.io;

import java.util.HashMap;

import javax.swing.Icon;
import javax.swing.ImageIcon;

/**
 * アイコンリソースの再利用ライブラリです。
 * <p>
 * Flyweight Pattern
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACResourceIconPooler extends HashMap {
    protected static ACResourceIconPooler singleton;

    /**
     * コンストラクタです。
     */
    private ACResourceIconPooler() {
    }

    /**
     * インスタンスを返します。
     * 
     * @return インスタンス
     */
    public static ACResourceIconPooler getInstance() {
        if (singleton == null) {
            singleton = new ACResourceIconPooler();
        }
        return singleton;
    }

    /**
     * リソース内の画像をプールしながら取得します。
     * 
     * @param path String
     * @return Image
     */
    public Icon getImage(String path) {
        Object obj = get(path);
        if (!(obj instanceof Icon)) {
            obj = new ImageIcon(getClass().getClassLoader().getResource(path));
            put(path, obj);
        }
        return (Icon) obj;
    }
}
