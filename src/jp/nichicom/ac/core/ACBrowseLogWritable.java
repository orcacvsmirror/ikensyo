package jp.nichicom.ac.core;

/**
 * プログラム起動ログ、個人情報閲覧ログを出力のインターフェース。
 * <p>
 * 主として、ACAffairableインターフェースとあわせてシステムイベント処理クラスに実装します。
 * </p>
 * <p>
 * Copyright (c) 2012 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Shin Fujihara
 * @version 1.0 2012/09/19
 * @see ACAffairable
 * @see ACFrame
 */
public interface ACBrowseLogWritable {
	
	/**
	 * プログラム起動、個人情報閲覧ログを出力する
	 * @param affair 次画面情報
	 */
	public void writeBrowseLog(ACAffairInfo affair);

}
