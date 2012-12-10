package jp.nichicom.ac.util.splash;

import javax.swing.JProgressBar;

import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * �v���O���X�\���X�v���b�V���ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/02/09
 */
public class ACProgressSplash extends ACSplash {

    private JProgressBar progressBar;

    /**
     * �R���X�g���N�^�ł��B
     */
    public ACProgressSplash() {
        super();
    }

    /**
     * �i�s�󋵂�ݒ肵�܂��B
     * 
     * @param value �i�s��
     */
    public void setProgressValue(int value) {
        getProgressBar().setValue(value);
        getProgressBar().setString(
                Integer.toString(value) + " / "
                        + Integer.toString(getProgressBar().getMaximum()));
        getProgressBar().paintImmediately(getProgressBar().getVisibleRect());
        // if (progressBar.getMaximum() <= value) {
        // this.dispose();
        // }
    }

    protected boolean addExpands(ACPanel container) {
        container.add(getProgressBar(), VRLayout.CLIENT);
        return true;
    }

    /**
     * �v���O���X�o�[�𐶐����܂��B
     * 
     * @return �v���O���X�o�[
     */
    protected JProgressBar createProgressBar() {
        return new JProgressBar();
    }

    /**
     * �v���O���X�o�[��Ԃ��܂��B
     * 
     * @return �v���O���X�o�[
     */
    protected JProgressBar getProgressBar() {
        if (progressBar == null) {
            progressBar = createProgressBar();
        }
        return progressBar;
    }

    protected void initComponent() {
        super.initComponent();
        getProgressBar().setMinimum(0);
        getProgressBar().setMaximum(100);
        getProgressBar().setStringPainted(true);
        getProgressBar().setVisible(false);

    }

}
