package jp.nichicom.ac.core;

/**
 * 業務置換インターフェースです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public interface ACAffairReplacable {
    /**
     * エディション等に応じて、適切な業務クラス名を返します。
     * 
     * @param className 検査対象の業務クラス名
     * @return 適切な業務クラス名
     */
    String getValidClassName(String className);

}
