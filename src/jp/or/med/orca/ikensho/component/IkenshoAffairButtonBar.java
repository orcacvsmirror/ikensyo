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
 * �Ɩ��{�^���o�[�ł��B
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
	 * ���C�����j���[�ɖ߂�{�^���̕\����Ԃ�ݒ肵�܂��B
	 * 
	 * @param backVisible(�߂�{�^���̕\�����)
	 */
	public void setBackToMainMenuVisible(boolean backToMainMenuVisible) {
		backToMainMenu.setVisible(backToMainMenuVisible);
	}

	/**
	 * ���C�����j���[�ɖ߂�{�^���̕\����Ԃ�Ԃ��܂��B
	 * 
	 * @return �߂�{�^���̕\�����
	 */
	public boolean isBackToMainMenuVisible() {
		return backToMainMenu.isVisible();
	}

	/**
	 * �R���|�[�l���g�����������܂��B
	 * 
	 * @throws Exception
	 *             ��������O
	 */
	private void jbInit() throws Exception {
		title.setBorder(javax.swing.BorderFactory.createEmptyBorder(4, 8, 4, 0));

		// ���C�����j���[�ɖ߂�{�^���̏�����
		backToMainMenu.setText("���C����(M)");
		backToMainMenu.setMnemonic('M');
		backToMainMenu
				.setIconPath(IkenshoConstants.BUTTON_IMAGE_PATH_BACK_TO_MAIN_MENU);
		backToMainMenu.setToolTipText("���C�����j���[�֖߂�܂��B");
		backToMainMenu.addActionListener(new ActionListener() {
			protected boolean lockFlag = false;

			public void actionPerformed(ActionEvent e) {
				if (lockFlag) {
					return;
				}
				lockFlag = true;
                // �����̕���
                Object history = ((ArrayList)ACFrame.getInstance().getTraceAffairs()).clone();
				try {
					// �ŏ��̋Ɩ�(���j���[���)�����[�J���ϐ��ɑޔ�
					ACAffairInfo first = (ACAffairInfo) ACFrame.getInstance()
							.getTraceAffairs().get(0);
					ACAffairInfo last = (ACAffairInfo) ACFrame.getInstance()
							.getTraceAffairs().get(
									ACFrame.getInstance().getTraceAffairs()
											.size() - 1);                    
                    
					// �������N���A
					ACFrame.getInstance().getTraceAffairs().clear();
					// �ޔ������Ɩ��������߂�
					ACFrame.getInstance().getTraceAffairs().add(first);
					ACFrame.getInstance().getTraceAffairs().add(last);
					// ���ʖ߂閽��
					ACFrame.getInstance().back();
				} catch (Exception ex) {
					// ��O�̏����̓C�x���g�Ϗ�
					ACFrame.getInstance().showExceptionMessage(ex);
				}
                // �L�����Z�����ꂽ�Ƃ݂Ȃ���������
                if(ACFrame.getInstance().getTraceAffairs().size() == 2) {
                    if(history instanceof List) {
                        // �����̕���
                        ACFrame.getInstance().getTraceAffairs().addAll((List)history);
                    }
                }
                
				lockFlag = false;
			}
		});
		setBackToMainMenuVisible(false); // �����l�͔�\���B�K�v�ɉ����ĕ\���ɂ��邱�ƁB

		this.removeAll();
		this.add(back, VRLayout.WEST);
		this.add(backToMainMenu, VRLayout.WEST);
		this.add(title, VRLayout.WEST);
	}

	/**
	 * ���C�����j���[�֖߂�{�^����Ԃ��܂��B
	 * 
	 * @return ���C�����j���[�֖߂�{�^��
	 */
	public ACAffairButton getBackToMainMenuButton() {
		return backToMainMenu;
	}
}
