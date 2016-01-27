package jp.nichicom.ac.core;

import jp.nichicom.vr.util.VRMap;

/**
 * 業務情報です。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */

public class ACAffairInfo {
    protected String className;
    protected VRMap parameters;
    protected String title;
    protected boolean splashed;

    /**
     * 業務画面タイトル を返します。
     * 
     * @return 業務画面タイトル
     */
    public String getTitle() {
        return title;
    }

    /**
     * 業務画面タイトル を設定します。
     * 
     * @param title 業務画面タイトル
     */
    protected void setTitle(String title) {
        this.title = title;
    }

    /**
     * パッケージを含む業務クラス名 を返します。
     * 
     * @return パッケージを含む業務クラス名
     */
    public String getClassName() {
        return className;
    }

    /**
     * パッケージを含む業務クラス名 を設定します。
     * 
     * @param className パッケージを含む業務クラス名
     */
    protected void setClassName(String className) {
        this.className = className;
    }

    /**
     * 画面遷移にかかる遷移パラメータ を返します。
     * 
     * @return 画面遷移にかかる遷移パラメータ
     */
    public VRMap getParameters() {
        return parameters;
    }

    /**
     * 画面遷移にかかる遷移パラメータ を設定します。
     * 
     * @param parameters 画面遷移にかかる遷移パラメータ
     */
    public void setParameters(VRMap parameters) {
        this.parameters = parameters;
    }

    /**
     * 遷移時にスプラッシュ画面を表示するか を返します。
     * 
     * @return 遷移時にスプラッシュ画面を表示するか
     */
    public boolean isSplashed() {
        return splashed;
    }

    /**
     * 遷移時にスプラッシュ画面を表示するか を設定します。
     * 
     * @param splashed 遷移時にスプラッシュ画面を表示するか
     */
    public void setSplashed(boolean splashed) {
        this.splashed = splashed;
    }

    /**
     * コンストラクタです。
     * 
     * @param className パッケージを含む業務クラス名
     */
    public ACAffairInfo(String className) {
        this(className, null, "", false);
    }

    /**
     * コンストラクタです。
     * 
     * @param className パッケージを含む業務クラス名
     * @param parameters 画面遷移にかかる遷移パラメータ
     */
    public ACAffairInfo(String className, VRMap parameters) {
        this(className, parameters, "", false);
    }

    /**
     * コンストラクタです。
     * 
     * @param className パッケージを含む業務クラス名
     * @param parameters 画面遷移にかかる遷移パラメータ
     * @param title タイトル
     */
    public ACAffairInfo(String className, VRMap parameters, String title) {
        this(className, parameters, title, false);
    }

    /**
     * コンストラクタです。
     * 
     * @param className パッケージを含む業務クラス名
     * @param splashed 画面遷移時にスプラッシュ画面を表示するか
     */
    public ACAffairInfo(String className, boolean splashed) {
        this(className, null, "", splashed);
    }

    /**
     * コンストラクタです。
     * 
     * @param className パッケージを含む業務クラス名
     * @param parameters 画面遷移にかかる遷移パラメータ
     * @param splashed 画面遷移時にスプラッシュ画面を表示するか
     */
    public ACAffairInfo(String className, VRMap parameters, boolean splashed) {
        this(className, parameters, "", splashed);
    }

    /**
     * コンストラクタです。
     * 
     * @param className パッケージを含む業務クラス名
     * @param parameters 画面遷移にかかる遷移パラメータ
     * @param title タイトル
     * @param splashed 画面遷移時にスプラッシュ画面を表示するか
     */
    public ACAffairInfo(String className, VRMap parameters, String title,
            boolean splashed) {
        setClassName(className);
        setParameters(parameters);
        setTitle(title);
        setSplashed(splashed);
    }
}
