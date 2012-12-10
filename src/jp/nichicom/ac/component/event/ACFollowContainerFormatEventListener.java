package jp.nichicom.ac.component.event;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;

import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.vr.component.event.VRFormatEvent;
import jp.nichicom.vr.component.event.VRFormatEventListener;
import jp.nichicom.vr.container.VRLabelContainer;

/**
 * フォーマットのエラー有無に応じて上位のコンテナを着色するフォーマットイベントリスナです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corpration. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/03/31
 * @see VRFormatEventListener
 */
public class ACFollowContainerFormatEventListener implements
        VRFormatEventListener {
    private boolean allowedBlank = true;
    private VRLabelContainer changedContainer;
    private int parentFindCount = 3;
    private boolean valid = true;

    /**
     * コンストラクタです。
     */
    public ACFollowContainerFormatEventListener() {
        super();
    }

    /**
     * コンストラクタです。
     * 
     * @param allowedBlank 未入力を許可するか
     */
    public ACFollowContainerFormatEventListener(boolean allowedBlank) {
        super();
        setAllowedBlank(allowedBlank);
    }

    /**
     * コンストラクタです。
     * 
     * @param parentFindCount エラー時に検索する親階層
     */
    public ACFollowContainerFormatEventListener(int parentFindCount) {
        super();
        setParentFindCount(parentFindCount);
    }

    /**
     * コンテナを無効状態表示にします。
     */
    public void changeInvalidContainer() {
        if (changedContainer != null) {
            changedContainer.setLabelFilled(true);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            Color fore;
            Color back;
            if (processer == null) {
                fore = Color.white;
                back = Color.red;
            } else {
                fore = processer.getContainerErrorForeground();
                back = processer.getContainerErrorBackground();
            }
            changedContainer.setForeground(fore);
            changedContainer.setBackground(back);

            setValid(false);
        }
    }

    /**
     * コンテナを警告状態表示にします。
     */
    public void changeWarningContainer() {
        if (changedContainer != null) {
            changedContainer.setLabelFilled(true);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            Color fore;
            Color back;
            if (processer == null) {
                fore = Color.black;
                back = Color.yellow;
            } else {
                fore = processer.getContainerWarningForeground();
                back = processer.getContainerWarningBackground();
            }
            changedContainer.setForeground(fore);
            changedContainer.setBackground(back);

            setValid(false);
        }
    }

    /**
     * コンテナを有効状態表示にします。
     */
    public void changeValidContainer() {
        if (changedContainer != null) {
            changedContainer.setLabelFilled(false);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            Color fore;
            Color back;
            if (processer == null) {
                fore = Color.black;
                back = Color.black;
            } else {
                fore = processer.getContainerDefaultForeground();
                back = processer.getContainerDefaultBackground();
            }
            changedContainer.setForeground(fore);
            changedContainer.setBackground(back);

            setValid(true);
        }
    }

    public void formatInvalid(VRFormatEvent e) {
        if (isAllowedBlank()) {
            Object parse = e.getParseValue();
            if ((parse == null) || "".equals(parse)) {
                // 未入力は許容する
                changeValidContainer();
                return;
            }
        }
        Object source = e.getSource();
        if (source instanceof Component) {
            // 親ラベルコンテナを検索
            changedContainer = findParent(((Component) source).getParent(), 0);
            changeInvalidContainer();
        }
    }
    /**
     * フォーマット警告イベントを処理します。
     * @param e イベント情報
     */
    public void formatWarning(VRFormatEvent e) {
        if (isAllowedBlank()) {
            Object parse = e.getParseValue();
            if ((parse == null) || "".equals(parse)) {
                // 未入力は許容する
                changeValidContainer();
                return;
            }
        }
        Object source = e.getSource();
        if (source instanceof Component) {
            // 親ラベルコンテナを検索
            changedContainer = findParent(((Component) source).getParent(), 0);
            changeWarningContainer();
        }
    }
    public void formatValid(VRFormatEvent e) {
        if (changedContainer == null) {
            // 親ラベルコンテナが設定されていなければ検索
            Object source = e.getSource();
            if (source instanceof Component) {
                // 親ラベルコンテナを検索
                changedContainer = findParent(((Component) source).getParent(),
                        0);
            }
        }
        
        if (!isAllowedBlank()) {
            if ((e.getParseValue() == null) || ("".equals(e.getParseValue()))) {
                // 空文字を認めない設定
                changeInvalidContainer();
                return;
            }
        }
        changeValidContainer();
    }

    /**
     * エラー時に検索する親階層の数を返します。
     * 
     * @return エラー時に検索する親階層
     */
    public int getParentFindCount() {
        return parentFindCount;
    }

    /**
     * 未入力を許可するか を返します。
     * 
     * @return 未入力を許可するか
     */
    public boolean isAllowedBlank() {
        return allowedBlank;
    }

    /**
     * 親コンテナが有効状態表示であるかを返します。
     * 
     * @return 親コンテナが有効状態表示であるか
     */
    public boolean isValid() {
        return valid;
    }

    /**
     * 未入力を許可するか を設定します。
     * 
     * @param allowedBlank 未入力を許可するか
     */
    public void setAllowedBlank(boolean allowedBlank) {
        this.allowedBlank = allowedBlank;
    }

    /**
     * エラー時に検索する親階層の数を設定します。
     * 
     * @param parentFindCount エラー時に検索する親階層
     */
    public void setParentFindCount(int parentFindCount) {
        this.parentFindCount = parentFindCount;
    }

    /**
     * 親コンテナを再帰検索し、着色します。
     * 
     * @param parent 親コンテナ
     * @param depth 再帰深度
     */
    protected VRLabelContainer findParent(Container parent, int depth) {
        // 入力エラー時にはコンテナにエラー色を設定
        if (parent == null) {
            return null;
        }
        if (parent instanceof VRLabelContainer) {
            return (VRLabelContainer) parent;
        }
        if (depth < parentFindCount) {
            // 親へかけ上がる
            return findParent(parent.getParent(), depth + 1);
        }
        return null;
    }

    /**
     * 親コンテナが有効状態表示であるか を設定します。
     * 
     * @param valid 親コンテナが有効状態表示であるか
     */
    protected void setValid(boolean valid) {
        this.valid = valid;
    }

}
