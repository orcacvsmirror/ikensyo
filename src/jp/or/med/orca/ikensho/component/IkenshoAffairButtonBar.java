package jp.or.med.orca.ikensho.component;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.SwingConstants;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACAffairButton;
import jp.nichicom.ac.component.ACAffairButtonBar;
import jp.nichicom.ac.core.ACAffairInfo;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.vr.layout.VRLayout;
import jp.or.med.orca.ikensho.IkenshoConstants;

/**
 * 業務ボタンバーです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Mizuki Tsutsumi
 * @version 1.0 2009/1/13
 * @see ACAffairButtonBar
 */
public class IkenshoAffairButtonBar extends ACAffairButtonBar {
	protected ACAffairButton backToMainMenu = new ACAffairButton();

	public IkenshoAffairButtonBar() {
		super();
		
		try {
			jbInit();
		}catch(Exception e) {
		}
	}

	/**
	 * メインメニューに戻るボタンの表示状態を設定します。
	 * 
	 * @param backVisible(戻るボタンの表示状態)
	 */
	public void setBackToMainMenuVisible(boolean backToMainMenuVisible) {
		backToMainMenu.setVisible(backToMainMenuVisible);
	}

	/**
	 * メインメニューに戻るボタンの表示状態を返します。
	 * 
	 * @return 戻るボタンの表示状態
	 */
	public boolean isBackToMainMenuVisible() {
		return backToMainMenu.isVisible();
	}

	/**
	 * コンポーネントを初期化します。
	 * 
	 * @throws Exception
	 *             初期化例外
	 */
	private void jbInit() throws Exception {
		title.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 0));

		// メインメニューに戻るボタンの初期化
		backToMainMenu.setText("メインへ(M)");
		backToMainMenu.setMnemonic('M');
		backToMainMenu
				.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_BACK_TO_MAIN_MENU);
		backToMainMenu.setToolTipText("メインメニューへ戻ります。");
		backToMainMenu.addActionListener(new ActionListener() {
			protected boolean lockFlag = false;

			public void actionPerformed(ActionEvent e) {
				if (lockFlag) {
					return;
				}
				lockFlag = true;
                // 履歴の複製
                Object history = ((ArrayList)ACFrame.getInstance().getTraceAffairs()).clone();
				try {
					// 最初の業務(メニュー画面)をローカル変数に退避
					ACAffairInfo first = (ACAffairInfo) ACFrame.getInstance()
							.getTraceAffairs().get(0);
					ACAffairInfo last = (ACAffairInfo) ACFrame.getInstance()
							.getTraceAffairs().get(
									ACFrame.getInstance().getTraceAffairs()
											.size() - 1);                    
                    
					// 履歴をクリア
					ACFrame.getInstance().getTraceAffairs().clear();
					// 退避した業務を差し戻す
					ACFrame.getInstance().getTraceAffairs().add(first);
					ACFrame.getInstance().getTraceAffairs().add(last);
					// 一画面戻る命令
					ACFrame.getInstance().back();
				} catch (Exception ex) {
					// 例外の処理はイベント委譲
					ACFrame.getInstance().showExceptionMessage(ex);
				}
                // キャンセルされたとみなし復元する
                if(ACFrame.getInstance().getTraceAffairs().size() == 2) {
                    if(history instanceof List) {
                        // 履歴の復元
                        ACFrame.getInstance().getTraceAffairs().addAll((List)history);
                    }
                }
                
				lockFlag = false;
			}
		});
		setBackToMainMenuVisible(false); // 初期値は非表示。必要に応じて表示にすること。

		this.removeAll();
		this.add(back, VRLayout.WEST);
		this.add(backToMainMenu, VRLayout.WEST);
		this.add(title, VRLayout.WEST);
	}

	/**
	 * メインメニューへ戻るボタンを返します。
	 * 
	 * @return メインメニューへ戻るボタン
	 */
	public ACAffairButton getBackToMainMenuButton() {
		return backToMainMenu;
	}
}
