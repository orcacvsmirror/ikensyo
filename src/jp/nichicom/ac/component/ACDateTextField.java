package jp.nichicom.ac.component;

import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Calendar;
import java.util.Date;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.event.ACFollowContainerFormatEventListener;
import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * 日付用に設定したテキストフィールドです。
 * <p>
 * 日付として不適切な値を入力した場合、親コンテナを走査して着色します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see ACTextField
 */
public class ACDateTextField extends ACTextField {
    private boolean allowedFutureDate = true;
    private Date futureBaseDate;
    private ACFollowContainerFormatEventListener followFormatListener = new ACFollowContainerFormatEventListener(
            true);

    /**
     * コンストラクタです。
     */
    public ACDateTextField() {
        try {
            jbInit();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.addFormatEventListener(followFormatListener);

        this.addFocusListener(new FocusAdapter() {
            public void focusLost(FocusEvent e) {
                if (!isAllowedFutureDate()) {
                    // 未来日付を許容しない場合
                    if (isValid()) {
                        // 日付としては適正であるため、未来日付をチェックする
                        Calendar x = Calendar.getInstance();
                        Calendar y = Calendar.getInstance();
                        boolean future = false;
                        try {
                            x.setTime(getDate());
                            y.setTime(getFutureBaseDate());

                            int cmpY = x.get(Calendar.YEAR);
                            int baseY = y.get(Calendar.YEAR);
                            int cmpD = x.get(Calendar.DAY_OF_YEAR);
                            int baseD = y.get(Calendar.DAY_OF_YEAR);
                            if (cmpY == baseY) {
                                // 同年ならば年内の日にちで比較
                                if (cmpD > baseD) {
                                    // 検査日のほうが後→未来
                                    future = true;
                                }
                            } else {
                                // 検査日と基準日を年で比較
                                future = cmpY > baseY;
                            }
                        } catch (Exception e1) {
                            future = true;
                        }

                        if (future) {
                            // 未来の場合は無効扱いとする
                            followFormatListener.changeInvalidContainer();
                        }

                    }
                }
            }
        });
    }

    /**
     * 入力値をDate型で取得します。
     * 
     * @return 入力値
     * @throws Exception 処理例外
     */
    public Date getDate() throws Exception {
        return VRDateParser.parse(getText());
    }

    /**
     * 未来日付判定の基準となる現在日時 を返します。
     * <p>
     * リアルタイムな現在日時を使う場合はnullを指定します。
     * </p>
     * 
     * @return 未来日付判定の基準となる現在日時
     */
    public Date getFutureBaseDate() {
        return futureBaseDate;
    }

    /**
     * エラー時に検索する親階層の数を返します。
     * 
     * @return エラー時に検索する親階層
     */
    public int getParentFindCount() {
        return followFormatListener.getParentFindCount();
    }

    /**
     * 未入力を許可するか を返します。
     * 
     * @return 未入力を許可するか
     */
    public boolean isAllowedBlank() {
        return followFormatListener.isAllowedBlank();
    }

    /**
     * 未来日付を許可するか を返します。
     * 
     * @return 未来日付を許可するか
     * @see #setFutureBaseDate(Date)
     */
    public boolean isAllowedFutureDate() {
        return allowedFutureDate;
    }

    /**
     * 有効な日付が入力されているかを返します。
     * 
     * @return 有効な日付が入力されているか
     */
    public boolean isValidDate() {
        return followFormatListener.isValid();
    }

    /**
     * 未入力を許可するか を設定します。
     * 
     * @param allowedBlank 未入力を許可するか
     */
    public void setAllowedBlank(boolean allowedBlank) {
        followFormatListener.setAllowedBlank(allowedBlank);
    }

    /**
     * 未来日付を許可するか を設定します。
     * 
     * @param allowedFutureDate 未来日付を許可するか
     */
    public void setAllowedFutureDate(boolean allowedFutureDate) {
        this.allowedFutureDate = allowedFutureDate;
    }

    /**
     * 入力値をDate型で設定します。
     * 
     * @param value 入力値
     * @throws Exception 処理例外
     */
    public void setDate(Date value) throws Exception {
        setText(VRDateParser.format(value, "yyyy/MM/dd hh:mm:ss"));
    }

    /**
     * 未来日付判定の基準となる現在日時 を設定します。
     * <p>
     * リアルタイムな現在日時を使う場合はnullを指定します。
     * </p>
     * 
     * @param futureBaseDate 未来日付判定の基準となる現在日時
     */
    public void setFutureBaseDate(Date futureBaseDate) {
        this.futureBaseDate = futureBaseDate;
    }

    /**
     * エラー時に検索する親階層の数を設定します。
     * 
     * @param parentFindCount エラー時に検索する親階層
     */
    public void setParentFindCount(int parentFindCount) {
        followFormatListener.setParentFindCount(parentFindCount);
    }

    /**
     * コンポーネントを初期化します。
     * 
     * @throws Exception 処理例外
     */
    private void jbInit() throws Exception {
        this.setColumns(11);
        this.setMaxLength(11);
        this.setFormat(ACConstants.FORMAT_FULL_ERA_YMD);
    }

}
