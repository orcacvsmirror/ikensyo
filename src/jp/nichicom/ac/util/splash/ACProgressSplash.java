package jp.nichicom.ac.util.splash;

import javax.swing.JProgressBar;

import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.vr.layout.VRLayout;

/**
 * プログレス表示スプラッシュです。
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
     * コンストラクタです。
     */
    public ACProgressSplash() {
        super();
    }

    /**
     * 進行状況を設定します。
     * 
     * @param value 進行状況
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
     * プログレスバーを生成します。
     * 
     * @return プログレスバー
     */
    protected JProgressBar createProgressBar() {
        return new JProgressBar();
    }

    /**
     * プログレスバーを返します。
     * 
     * @return プログレスバー
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
