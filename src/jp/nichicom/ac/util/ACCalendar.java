package jp.nichicom.ac.util;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.TimeZone;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.util.event.ACCalendarChangeEvent;
import jp.nichicom.ac.util.event.ACCalendarChangeListener;
import jp.nichicom.ac.util.event.ACCalendarDrawEvent;
import jp.nichicom.ac.util.event.ACCalendarDrawListener;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.text.parsers.VRDateParserHolyday;
import jp.nichicom.vr.util.logging.VRLogger;

/**
 * 日付選択可能なカレンダーです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendar extends ACPanel implements KeyListener, FocusListener {

    /** 選択モードを「複数選択」に設定する定数です。 */
    public final static int SELECT_MULTI = 2;

    /** 選択モードを「単一選択」に設定する定数です。 */
    public final static int SELECT_SINGLE = 0;

    /** 選択モードを「範囲選択」に設定する定数です。 */
    public final static int SELECT_SPAN = 1;

    private Calendar beginSelectCalendar;
    private Color beginSelectColor = new Color(255, 198, 106);
    private transient Vector calendarChangeListeners;
    private transient Vector calendarDrawListeners;
    private transient Vector dayMouseListeners;
    private int cols = 1;
    private Calendar currentCalednar;
    private Border dayBorder;

    private Font dayFont = null;
    private Dimension dayPreferedSize;
    private boolean ellipse = true;
    private Calendar endSelectCalendar;
    private Color endSelectColor = new Color(255, 198, 106);
    private Calendar focusCalendar;
    private Color focusColor = Color.decode("#FF6600");
    private Color holidayForeground = Color.pink;
    private Calendar maximumCalendar;
    private Calendar minimumCalendar;
    private boolean monthVisible = true;
    private NCCalendarDateMouseListener mouseListener = new NCCalendarDateMouseListener();
    private Set multiSelectedCalendarMap = new HashSet();
    private Color normalDayColor;

    private Color outsideColor;
    private int rows = 1;
    private Color saturdayForeground = Color.blue;

    private Color selectedBackground = new Color(255, 242, 151);

    private int selectMode;
    private Calendar shiftPressedCalendar;
    private Color sundayForeground = Color.red;

    private Calendar today = Calendar.getInstance();

    private Color todayBackground = new Color(208, 255, 151);
    private Color focusGainedBackground = new Color(255, 255, 255);
    private Color focusLostBackground = new Color(247, 252, 255);

    private Color yearHeaderBackground = new Color(100, 100, 100);
    private Color yearHeaderForeground = Color.white;

    private Color monthHeaderBackground = new Color(100, 100, 100);
    private Color monthHeaderForeground = Color.white;

    private Color weekHeaderBackground = new Color(114, 160, 215);
    private Color weekHeaderForeground = Color.white;
    private boolean weekVisible = true;

    private boolean yearVisible = true;

    /**
     * 月タイトルの背景色 を返します。
     * 
     * @return 月タイトルの背景色
     */
    public Color getMonthHeaderBackground() {
        return monthHeaderBackground;
    }

    /**
     * 月タイトルの背景色 を設定します。
     * 
     * @param monthHeaderBackground 月タイトルの背景色
     */
    public void setMonthHeaderBackground(Color monthHeaderBackground) {
        this.monthHeaderBackground = monthHeaderBackground;
    }

    /**
     * 月タイトルの前景色 を返します。
     * 
     * @return 月タイトルの前景色
     */
    public Color getMonthHeaderForeground() {
        return monthHeaderForeground;
    }

    /**
     * 月タイトルの前景色 を設定します。
     * 
     * @param monthHeaderForeground 月タイトルの前景色
     */
    public void setMonthHeaderForeground(Color monthHeaderForeground) {
        this.monthHeaderForeground = monthHeaderForeground;
    }

    /**
     * 年タイトルの背景色 を返します。
     * 
     * @return 年タイトルの背景色
     */
    public Color getYearHeaderBackground() {
        return yearHeaderBackground;
    }

    /**
     * 年タイトルの背景色 を設定します。
     * 
     * @param yearHeaderBackground 年タイトルの背景色
     */
    public void setYearHeaderBackground(Color yearHeaderBackground) {
        this.yearHeaderBackground = yearHeaderBackground;
    }

    /**
     * 年タイトルの前景色 を返します。
     * 
     * @return 年タイトルの前景色
     */
    public Color getYearHeaderForeground() {
        return yearHeaderForeground;
    }

    /**
     * 年タイトルの前景色 を設定します。
     * 
     * @param yearHeaderForeground 年タイトルの前景色
     */
    public void setYearHeaderForeground(Color yearHeaderForeground) {
        this.yearHeaderForeground = yearHeaderForeground;
    }

    /**
     * 週タイトルの背景色 を返します。
     * 
     * @return 週タイトルの背景色
     */
    public Color getWeekHeaderBackground() {
        return weekHeaderBackground;
    }

    /**
     * 週タイトルの背景色 を設定します。
     * 
     * @param weekHeaderBackground 週タイトルの背景色
     */
    public void setWeekHeaderBackground(Color weekHeaderBackground) {
        this.weekHeaderBackground = weekHeaderBackground;
    }

    /**
     * 週タイトルの前景色 を返します。
     * 
     * @return 週タイトルの前景色
     */
    public Color getWeekHeaderForeground() {
        return weekHeaderForeground;
    }

    /**
     * 週タイトルの前景色 を設定します。
     * 
     * @param weekHeaderForeground 週タイトルの前景色
     */
    public void setWeekHeaderForeground(Color weekHeaderForeground) {
        this.weekHeaderForeground = weekHeaderForeground;
    }

    /**
     * フォーカス取得時の背景色 を返します。
     * 
     * @return フォーカス取得時の背景色
     */
    public Color getFocusGainedBackground() {
        return focusGainedBackground;
    }

    /**
     * フォーカス取得時の背景色 を設定します。
     * 
     * @param focusGainedBackground フォーカス取得時の背景色
     */
    public void setFocusGainedBackground(Color focusGainedBackground) {
        this.focusGainedBackground = focusGainedBackground;
        repaint();
    }

    /**
     * フォーカス喪失時の背景色 を返します。
     * 
     * @return フォーカス喪失時の背景色
     */
    public Color getFocusLostBackground() {
        return focusLostBackground;
    }

    /**
     * フォーカス喪失時の背景色 を設定します。
     * 
     * @param focusLostBackground フォーカス喪失時の背景色
     */
    public void setFocusLostBackground(Color focusLostBackground) {
        this.focusLostBackground = focusLostBackground;
        repaint();
    }

    /**
     * コンストラクタです。
     */
    public ACCalendar() {
        try {
            jbInit();
            restracture();

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * 指定した表示月更新リスナーをリスナー一覧に追加します。
     * 
     * @param l リスナー一覧に追加する表示月更新リスナー
     */
    public synchronized void addCalendarChangeListener(
            ACCalendarChangeListener l) {
        Vector v = calendarChangeListeners == null ? new Vector(2)
                : (Vector) calendarChangeListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            calendarChangeListeners = v;
        }
    }

    /**
     * 指定した祝日情報取得リスナーをリスナー一覧に追加します。
     * 
     * @param l リスナー一覧に追加する祝日情報取得リスナー
     */
    public synchronized void addDayMouseListener(MouseListener l) {
        Vector v = dayMouseListeners == null ? new Vector(2)
                : (Vector) dayMouseListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            dayMouseListeners = v;
        }
    }

    /**
     * 指定した描画リスナーをリスナー一覧に追加します。
     * 
     * @param l リスナー一覧に追加する描画リスナー
     */
    public synchronized void addWelCalendarDrawListener(ACCalendarDrawListener l) {
        Vector v = calendarDrawListeners == null ? new Vector(2)
                : (Vector) calendarDrawListeners.clone();
        if (!v.contains(l)) {
            v.addElement(l);
            calendarDrawListeners = v;
        }
    }

    /**
     * 次の月に進めるかを返します。
     * 
     * @return 次の月に進める場合はtrue
     */
    public boolean canMoveNextMonth() {
        return compareYM(getMaximumCalendar(), getCurrentCalendar()) > 0;
    }

    /**
     * 前の月に戻れるかを返します。
     * 
     * @return 前の月に戻れる場合はtrue
     */
    public boolean canMovePreviewMonth() {
        return compareYM(getMinimumCalendar(), getCurrentCalendar()) < 0;
    }

    /**
     * 選択情報をクリアします。
     * <p>
     * どの日付も選択していない状態になります。
     * </p>
     */
    public void clearSelected() {
        multiSelectedCalendarMap.clear();
        setBeginSelectCalendar(null);
        setEndSelectCalendar(null);
        onSelectedChanged();
    }

    /**
     * フォーカス取得時に呼び出されるイベントです。
     * <p>
     * コンポーネントの再描画を行います。
     * </p>
     * 
     * @param e フォーカスイベント情報
     */
    public void focusGained(FocusEvent e) {
        setBackground(getFocusGainedBackground());
        repaint();
    }

    /**
     * フォーカス喪失時に呼び出されるイベントです。
     * <p>
     * コンポーネントの再描画を行います。
     * </p>
     * 
     * @param e フォーカスイベント情報
     */
    public void focusLost(FocusEvent e) {
        setBackground(getFocusLostBackground());
        repaint();
    }

    /**
     * 選択中の日付を返します。
     * <p>
     * 選択モードがSELECT_SPAN(範囲)の場合は、選択開始日を意味します。
     * </p>
     * 
     * @return 選択開始日
     */
    public Calendar getBeginSelectCalendar() {
        return beginSelectCalendar;
    }

    /**
     * 選択中の日付を返します。
     * <p>
     * 選択モードがSELECT_SPAN(範囲)の場合は、選択開始日を意味します。
     * </p>
     * 
     * @return 選択開始日
     */
    public Date getBeginSelectDate() {
        if (beginSelectCalendar == null) {
            return null;
        }
        return beginSelectCalendar.getTime();
    }

    /**
     * 選択開始日の日付を囲む図形の描画色を返します。
     * 
     * @return 選択開始日の図形描画色
     */
    public Color getBeginSelectColor() {
        return beginSelectColor;
    }

    /**
     * カレンダーを行方向にいくつ描画するかを返します。
     * <p>
     * 現在の表示対象月を左上に描画し、それ以降の未来のカレンダーを続けて描画します。
     * </p>
     * 
     * @return 行方向の描画数
     */
    public int getCols() {
        return cols;
    }

    /**
     * 表示対象年月 を返します。
     * 
     * @return 表示対象年月
     */
    public Calendar getCurrentCalendar() {
        return currentCalednar;
    }

    /**
     * 日付用のボーダー を返します。
     * 
     * @return 日付用のボーダー
     */
    public Border getDayBorder() {
        return dayBorder;
    }

    /**
     * 日付用のフォント を返します。
     * 
     * @return 日付用のフォント
     */
    public Font getDayFont() {
        return dayFont;
    }

    /**
     * 選択モードがSELECT_SPAN(範囲)の場合に使用する、選択終了日を返します。
     * 
     * @return 選択終了日
     */
    public Calendar getEndSelectCalendar() {
        return endSelectCalendar;
    }

    /**
     * 選択モードがSELECT_SPAN(範囲)の場合に使用する、選択終了日を返します。
     * <p>
     * 選択されていない場合は、nullを返します。
     * </p>
     * 
     * @return 選択終了日
     */
    public Date getEndSelectDate() {
        if (endSelectCalendar == null) {
            return null;
        }
        return endSelectCalendar.getTime();
    }

    /**
     * 選択終了日の日付を囲む図形の描画色を返します。
     * 
     * @return 選択終了日の図形描画色
     */
    public Color getEndSelectColor() {
        return endSelectColor;
    }

    /**
     * フォーカスの対象日 を返します。
     * 
     * @return focusCalendar
     */
    public Calendar getFocusCalendar() {
        return focusCalendar;
    }

    /**
     * 移動中の選択範囲の日付を囲む図形の描画色を返します。
     * <p>
     * 選択範囲の端をドラッグして範囲を変更している時にその日付を囲む図形の描画色です。
     * </p>
     * 
     * @return 選択範囲移動中の図形描画色
     */
    public Color getFocusColor() {
        return focusColor;
    }

    /**
     * 休日に該当する日付の描画色を返します。
     * 
     * @return 休日の描画色
     */
    public Color getHolidayForeground() {
        return holidayForeground;
    }

    /**
     * 最大表示年月を返します。
     * 
     * @return 最大表示年月
     */
    public Calendar getMaximumCalendar() {
        return maximumCalendar;
    }

    /**
     * 最小表示年月を返します。
     * 
     * @return 最小表示年月
     */
    public Calendar getMinimumCalendar() {
        return minimumCalendar;
    }

    /**
     * 表示対象の月を返します。
     * 
     * @return 表示対象の月
     */
    public int getMonth() {
        return getCurrentCalendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 通常日の文字色 を返します。
     * 
     * @return 通常日の文字色
     */
    public Color getNormalDayColor() {
        return normalDayColor;
    }

    /**
     * 日付部分以外の背景色を返します。
     * 
     * @return 日付部分以外の背景色
     */
    public Color getOutsideColor() {
        return outsideColor;
    }

    /**
     * カレンダーを列方向にいくつ描画するかを返します。
     * <p>
     * 現在の表示対象月を左上に描画し、それ以降の未来のカレンダーを続けて描画します。
     * </p>
     * 
     * @return 列方向の描画数
     */
    public int getRows() {
        return rows;
    }

    /**
     * 土曜日の日付描画色を返します。
     * 
     * @return 土曜日の日付描画色
     */
    public Color getSaturdayForeground() {
        return saturdayForeground;
    }

    /**
     * 選択日の日付を囲む図形の描画色を返します。
     * 
     * @return 選択日の図形描画色
     */
    public Color getSelectedBackground() {
        return selectedBackground;
    }

    /**
     * 複数選択モードにおける選択日付の集合を返します。
     * 
     * @return 選択日付の集合
     */
    public ArrayList getMultiSelectCalendar() {
        return new ArrayList(Arrays.asList(multiSelectedCalendarMap.toArray()));
    }

    /**
     * 選択している日数を返します。
     * 
     * @return 選択している日数
     */
    public int getSelectedCount() {
        switch (getSelectMode()) {
        case ACCalendar.SELECT_SINGLE:
            if (getBeginSelectCalendar() != null) {
                return 1;
            }
            break;
        case ACCalendar.SELECT_SPAN:
            if ((getBeginSelectCalendar() != null)
                    && (getEndSelectCalendar() != null)) {
                Calendar cal = Calendar.getInstance();
                cal.setTime(getEndSelectCalendar().getTime());
                cal.add(Calendar.YEAR, 1 - getBeginSelectCalendar().get(
                        Calendar.YEAR));
                cal.add(Calendar.DAY_OF_YEAR, -getBeginSelectCalendar().get(
                        Calendar.DAY_OF_YEAR));
                return cal.get(Calendar.YEAR) * 12
                        + cal.get(Calendar.DAY_OF_YEAR) + 1;
            }
        case ACCalendar.SELECT_MULTI:
            return multiSelectedCalendarMap.size();
        }
        return 0;
    }

    /**
     * 日付の選択モードを返します。
     * <p>
     * 選択モードは、次のいずれかです。<br>
     * SELECT_SINGLE（単一選択）：0 <br>
     * SELECT_SPAN（範囲選択）：1 <br>
     * SELECT_MULTI（複数選択）：2
     * </p>
     * 
     * @return 選択モード
     */
    public int getSelectMode() {
        return selectMode;
    }

    /**
     * 日曜日の日付描画色を返します。
     * 
     * @return 日曜日の日付描画色
     */
    public Color getSundayForeground() {
        return sundayForeground;
    }

    /**
     * 本日日付 を返します。
     * 
     * @return 本日日付
     */
    public Calendar getToday() {
        return today;
    }

    /**
     * 今日の日付を囲む図形の描画色を返します。
     * 
     * @return 今日の図形描画色
     */
    public Color getTodayBackground() {
        return todayBackground;
    }

    /**
     * 表示対象の年を返します。
     * 
     * @return 表示対象の年
     */
    public int getYear() {
        return getCurrentCalendar().get(Calendar.YEAR);
    }

    /**
     * 日付を囲む図形に円を使用するかを返します。
     * <p>
     * falseの場合、矩形で囲みます。
     * </p>
     * 
     * @return 日付を囲む図形に円を使用する場合はtrue
     */
    public boolean isEllipse() {
        return ellipse;
    }

    /**
     * 表示対象月の領域を表示するかを返します。
     * 
     * @return 表示対象月の領域を表示させる場合はtrue
     */
    public boolean isMonthVisible() {
        return monthVisible;
    }

    /**
     * 指定日を選択しているかを返します。
     * 
     * @param cal 対象日付
     * @return 選択しているか
     */
    public boolean isSelected(Calendar cal) {

        if (getSelectMode() == SELECT_MULTI) {
            // 複数選択で、描画する日が選択されている
            return multiSelectedCalendarMap.contains(cal);
        }
        if ((getBeginSelectCalendar() == null)
                || (getEndSelectCalendar() == null)) {
            return false;
        }
        if (compareYMD(cal, getBeginSelectCalendar()) < 0) {
            return false;
        }
        if (compareYMD(cal, getEndSelectCalendar()) > 0) {
            return false;
        }
        return true;
    }

    /**
     * 週凡例の領域を表示するかを返します。
     * 
     * @return 週凡例の領域を表示させる場合はtrue
     */
    public boolean isWeekVisible() {
        return weekVisible;
    }

    /**
     * 表示対象年の領域を表示するかを返します。
     * 
     * @return 表示対象年の領域を表示させる場合はtrue
     */
    public boolean isYearVisible() {
        return yearVisible;
    }

    /**
     * キーが押された時に呼び出されるイベントです。
     * <p>
     * 押下されたボタンに応じ、再描画を行います。
     * </p>
     * 
     * @param e KeyEvent キーボードイベント情報
     */
    public void keyPressed(KeyEvent e) {
        if (e.isShiftDown()) {
            if (shiftPressedCalendar == null) {
                shiftPressedCalendar = getFocusCalendar();
            }
        } else {
            shiftPressedCalendar = null;
        }

        switch (e.getKeyCode()) {
        case KeyEvent.VK_PAGE_DOWN:
            previewMonth();
            break;
        case KeyEvent.VK_PAGE_UP:
            nextMonth();
            break;
        case KeyEvent.VK_HOME:
            previewYear();
            break;
        case KeyEvent.VK_END:
            nextYear();
            break;
        case KeyEvent.VK_SPACE:
            if (getSelectMode() == ACCalendar.SELECT_MULTI) {
                swapMultiSelect(getFocusCalendar());
                repaint();
            }
            break;
        default:
            if (getBeginSelectCalendar() != null) {
                int d = 0;
                switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    d = -7;
                    break;
                case KeyEvent.VK_DOWN:
                    d = 7;
                    break;
                case KeyEvent.VK_LEFT:
                    d = -1;
                    break;
                case KeyEvent.VK_RIGHT:
                    d = 1;
                    break;
                default:
                    // カーソル以外は何もしない。
                    return;
                }

                Calendar cal;
                cal = (Calendar) getFocusCalendar().clone();
                cal.add(Calendar.DATE, d);
                if (!canSelectDate(cal)) {
                    return;
                }

                setFocusCalendar(cal);

                if ((shiftPressedCalendar != null)
                        && (getSelectMode() == ACCalendar.SELECT_SPAN)) {
                    // 範囲選択且つShiftとカーソル押下
                    int cmp = compareYMD(shiftPressedCalendar, cal);
                    if (cmp < 0) {
                        // 終了日を変更
                        setBeginSelectCalendar(shiftPressedCalendar);
                        setEndSelectCalendar(cal);
                    } else if (cmp > 0) {
                        // 開始日を変更
                        setBeginSelectCalendar(cal);
                        setEndSelectCalendar(shiftPressedCalendar);
                    } else {
                        setBeginSelectCalendar(cal);
                        setEndSelectCalendar(cal);
                    }
                } else {
                    setBeginSelectCalendar(cal);
                    setEndSelectCalendar(cal);
                }

                int cmp = compareYM(getCurrentCalendar(), getFocusCalendar());
                if (cmp > 0) {
                    // 表示月が戻る
                    setCurrentCalendar(getFocusCalendar());
                } else if (cmp < 0) {
                    cal = (Calendar) getCurrentCalendar().clone();
                    cal.add(Calendar.MONTH, getRows() * getCols() - 1);
                    if (compareYM(cal, getFocusCalendar()) < 0) {
                        // 表示月が進む
                        setCurrentCalendar(getFocusCalendar());
                    }
                }
                repaint();

            }
            break;
        }

    }

    public void keyReleased(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    /**
     * 表示対象の月を現在の表示日から１ヶ月先に移動します。
     * <p>
     * 移動する年が0 ～ 9999年の範囲にない場合、 <br>
     * もしくは、最大表示年月を超える場合は何もしません。
     * </p>
     */
    public void nextMonth() {
        if (canMoveNextMonth()) {
            getCurrentCalendar().add(Calendar.MONTH, 1);
            // 表示年月の１日にフォーカス枠を設定
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * 表示対象の年を現在の表示日から１年先に移動します。
     * <p>
     * 移動する年が0 ～ 9999年の範囲にない場合、 <br>
     * もしくは、最大表示年月を超える場合は何もしません。
     * </p>
     */
    public void nextYear() {
        if (canMoveNextYear()) {
            getCurrentCalendar().add(Calendar.YEAR, 1);
            // 表示年月の１日にフォーカス枠を設定
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * 表示対象の月を現在の表示日から１ヶ月前に移動します。
     * <p>
     * 移動する年が0 ～ 9999年の範囲にない場合、 <br>
     * もしくは、最小表示年月を超える場合は何もしません。
     * </p>
     */
    public void previewMonth() {
        if (canMovePreviewMonth()) {
            getCurrentCalendar().add(Calendar.MONTH, -1);
            // 表示年月の１日にフォーカス枠を設定
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * 表示対象の年を現在の表示日から１年前に移動します。
     * <p>
     * 移動する年が0 ～ 9999年の範囲にない場合、 <br>
     * もしくは、最小表示年月を超える場合は何もしません。
     * </p>
     */
    public void previewYear() {
        if (canMovePreviewYear()) {
            getCurrentCalendar().add(Calendar.YEAR, -1);
            // 表示年月の１日にフォーカス枠を設定
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * 指定した表示月更新リスナーをリスナー一覧から削除します。
     * 
     * @param l リスナー一覧から削除する表示月更新リスナー
     */
    public synchronized void removeCalendarChangeListener(
            ACCalendarChangeListener l) {
        if (calendarChangeListeners != null
                && calendarChangeListeners.contains(l)) {
            Vector v = (Vector) calendarChangeListeners.clone();
            v.removeElement(l);
            calendarChangeListeners = v;
        }
    }

    /**
     * 指定した祝日情報取得リスナーをリスナー一覧から削除します。
     * 
     * @param l リスナー一覧から削除する祝日情報取得リスナー
     */
    public synchronized void removeDayMouseListener(MouseListener l) {
        if (dayMouseListeners != null && dayMouseListeners.contains(l)) {
            Vector v = (Vector) dayMouseListeners.clone();
            v.removeElement(l);
            dayMouseListeners = v;
        }
    }

    /**
     * 指定した描画リスナーをリスナー一覧から削除します。
     * 
     * @param l リスナー一覧から削除する描画リスナー
     */
    public synchronized void removeCalendarDrawListener(ACCalendarDrawListener l) {
        if (calendarDrawListeners != null && calendarDrawListeners.contains(l)) {
            Vector v = (Vector) calendarDrawListeners.clone();
            v.removeElement(l);
            calendarDrawListeners = v;
        }
    }

    /**
     * カレンダーを再構成します。
     */
    public void restracture() {

        this.removeAll();

        if (isYearVisible()) {
            this.add(createYearTitle(), VRLayout.NORTH);
        }

        try {
            Calendar cal = (Calendar) getCurrentCalendar().clone();
            int cols = getCols() - 1;
            for (int row = 0; row < getRows(); row++) {
                for (int col = 0; col < cols; col++) {
                    add(createMonth(cal), VRLayout.FLOW);
                    cal.add(Calendar.MONTH, 1);
                }
                add(createMonth(cal), VRLayout.FLOW_RETURN);
                cal.add(Calendar.MONTH, 1);
            }
        } catch (Exception ex) {
            VRLogger.warning(ex);
        }

        revalidate();
        repaint();
    }

    /**
     * 選択中の日付をDate型で設定します。
     * <p>
     * nullを設定すると、どの日付も選択していない状態になります。 <br>
     * 設定結果を反映するため、再描画を行います。
     * </p>
     * 
     * @param date 選択開始日
     */
    public void setBeginSelectDate(Date date) {
        if (date != null) {

            if (this.beginSelectCalendar == null) {
                this.beginSelectCalendar = Calendar.getInstance(TimeZone
                        .getDefault(), Locale.JAPAN);
            }
            this.beginSelectCalendar.setTime(date);
        } else {
            this.beginSelectCalendar = null;
        }
        onSelectedChanged();
        repaint();
    }

    /**
     * 選択中の日付を設定します。
     * <p>
     * 選択モードがSELECT_SPAN(範囲)の場合は、選択開始日を意味します。 <br>
     * 設定結果を反映するため、再描画を行います。
     * </p>
     * 
     * @param beginSelectCalendar 選択開始日
     */
    public void setBeginSelectCalendar(Calendar beginSelectCalendar) {
        this.beginSelectCalendar = beginSelectCalendar;

        onSelectedChanged();
        repaint();
    }

    /**
     * 選択開始日の日付を囲む図形の描画色を設定します。
     * 
     * @param beginSelectColor 選択開始日の図形描画色
     */
    public void setBeginSelectColor(Color beginSelectColor) {
        if (beginSelectColor == null) {
            return;
        }
        this.beginSelectColor = beginSelectColor;
    }

    /**
     * カレンダーを行方向にいくつ描画するかを設定します。
     * <p>
     * 現在の表示対象月を左上に描画し、それ以降の未来のカレンダーを続けて描画します。
     * </p>
     * 
     * @param cols 行方向の描画数
     */
    public void setCols(int cols) {
        this.cols = cols;
        if (this.cols <= 0) {
            this.cols = 1;
        }
        restracture();
    }

    /**
     * 表示対象年月 を設定します。
     * 
     * @param currentCalednar 表示対象年月
     */
    public void setCurrentCalendar(Calendar currentCalednar) {
        if (currentCalednar == null) {
            return;
        }
        currentCalednar.set(Calendar.DAY_OF_MONTH, 1);
        this.currentCalednar = currentCalednar;
        onDisplayMonthChange();
    }

    /**
     * 日付用のボーダー を設定します。
     * 
     * @param dayBorder 日付用のボーダー
     */
    public void setDayBorder(Border dayBorder) {
        this.dayBorder = dayBorder;
        restracture();
    }

    /**
     * 日付用のフォント を設定します。
     * 
     * @param dayFont 日付用のフォント
     */
    public void setDayFont(Font dayFont) {
        this.dayFont = dayFont;
        dayPreferedSize = null;
        restracture();
    }

    /**
     * 日付を囲む図形に円を使用するかを返設定します。
     * <p>
     * falseの場合、矩形で囲みます。
     * </p>
     * 
     * @param ellipse 日付を囲む図形に円を使用する場合はtrue
     */
    public void setEllipse(boolean ellipse) {
        this.ellipse = ellipse;
    }

    /**
     * 選択モードがSELECT_SPAN(範囲)の場合に使用する選択終了日をDate型で設定します。
     * <p>
     * nullを設定すると、選択開始日のみ選択していることになります。 <br>
     * 設定結果を反映するため、再描画を行います。
     * </p>
     * 
     * @param endSelectDate 選択終了日
     */
    public void setEndSelectDate(Date endSelectDate) {
        if (endSelectDate != null) {

            if (this.endSelectCalendar == null) {
                this.endSelectCalendar = Calendar.getInstance(TimeZone
                        .getDefault(), Locale.JAPAN);
            }
            this.endSelectCalendar.setTime(endSelectDate);
        } else {
            this.endSelectCalendar = null;
        }
        onSelectedChanged();
        repaint();
    }

    /**
     * 選択モードがSELECT_SPAN(範囲)の場合に使用する、選択終了日を設定します。 <br>
     * 設定結果を反映するため、再描画を行います。
     * 
     * @param endSelectCalendar 選択終了日
     */
    public void setEndSelectCalendar(Calendar endSelectCalendar) {
        this.endSelectCalendar = endSelectCalendar;
        onSelectedChanged();
        repaint();
    }

    /**
     * 選択終了日の日付を囲む図形の描画色を設定します。
     * 
     * @param endSelectColor 選択終了日の図形描画色
     */
    public void setEndSelectColor(Color endSelectColor) {
        if (endSelectColor == null) {
            return;
        }
        this.endSelectColor = endSelectColor;
    }

    /**
     * フォーカスの対象日 を設定します。
     * 
     * @param focusCalendar フォーカスの対象日
     */
    public void setFocusCalendar(Calendar focusCalendar) {
        this.focusCalendar = focusCalendar;
        repaint();
    }

    /**
     * 移動中の選択範囲の日付を囲む図形の描画色を設定します。 <br>
     * 選択範囲の端をドラッグして範囲を変更している時にその日付を囲む図形の描画色です。
     * 
     * @param focusColor 選択範囲移動中の図形描画色
     */
    public void setFocusColor(Color focusColor) {
        if (focusColor == null) {
            return;
        }
        this.focusColor = focusColor;
    }

    /**
     * 休日に該当する日付の描画色を設定します。
     * 
     * @param holidayColor 休日の描画色
     */
    public void setHolidayForeground(Color holidayColor) {
        if (holidayColor == null) {
            return;
        }
        this.holidayForeground = holidayColor;
    }

    /**
     * 最大表示年月を設定します。
     * 
     * @param maximumCalendar 最大表示年月
     */
    public void setMaximumCalendar(Calendar maximumCalendar) {
        if (maximumCalendar == null) {
            return;
        }
        this.maximumCalendar = maximumCalendar;
    }

    /**
     * 最小表示年月を設定します。
     * 
     * @param minimumCalendar 最小表示年月
     */
    public void setMinimumCalendar(Calendar minimumCalendar) {
        if (minimumCalendar == null) {
            return;
        }
        this.minimumCalendar = minimumCalendar;
    }

    /**
     * 表示対象の月を設定します。 <br>
     * 指定の値が1-12の範囲にない場合は、何もしません。 <br>
     * 設定結果を反映するために再描画を行います。
     * 
     * @param month 表示対象の月
     */
    public void setMonth(int month) {
        if (1 <= month && month <= 12) {
            int m = getCurrentCalendar().get(Calendar.MONTH) + 1;
            if (m != month) {
                getCurrentCalendar().set(Calendar.MONTH, month - 1);
                onDisplayMonthChange();
            }
        }
    }

    /**
     * 表示対象月の領域を表示するかを設定します。
     * 
     * @param monthVisible 表示対象月の領域を表示させる場合はtrue
     */
    public void setMonthVisible(boolean monthVisible) {
        this.monthVisible = monthVisible;
        restracture();
    }

    /**
     * 通常日の文字色 を設定します。
     * 
     * @param normalDayColor 通常日の文字色
     */
    public void setNormalDayColor(Color normalDayColor) {
        this.normalDayColor = normalDayColor;
    }

    /**
     * 日付部分以外の背景色を設定します。
     * 
     * @param outsideColor 日付部分以外の背景色
     */
    public void setOutsideColor(Color outsideColor) {
        this.outsideColor = outsideColor;
    }

    /**
     * カレンダーを列方向にいくつ描画するかを設定します。 <br>
     * 現在の表示対象月を左上に描画し、それ以降の未来のカレンダーを続けて描画します。
     * 
     * @param rows 列方向の描画数
     */
    public void setRows(int rows) {
        this.rows = rows;
        if (this.rows <= 0) {
            this.rows = 1;
        }
        restracture();
    }

    /**
     * 土曜日の日付描画色を設定します。
     * 
     * @param saturdayColor 土曜日の日付描画色
     */
    public void setSaturdayForeground(Color saturdayColor) {
        if (saturdayColor == null) {
            return;
        }
        this.saturdayForeground = saturdayColor;
    }

    /**
     * 選択日の日付を囲む図形の描画色を設定します。
     * 
     * @param selectedColor 選択日の図形描画色
     */
    public void setSelectedBackground(Color selectedColor) {
        if (selectedColor == null) {
            return;
        }
        this.selectedBackground = selectedColor;
    }

    /**
     * 日付の選択モードを設定します。 <br>
     * 選択モードは、次のいずれかです。
     * <p>
     * 選択モード <br>
     * SELECT_SINGLE（単一選択）：0 <br>
     * SELECT_SPAN（範囲選択）：1 <br>
     * SELECT_MULTI（複数選択）：2
     * 
     * @param selectMode 選択モード
     */
    public void setSelectMode(int selectMode) {
        this.selectMode = selectMode;
    }

    /**
     * 日曜日の日付描画色を設定します。
     * 
     * @param sundayColor 日曜日の日付描画色
     */
    public void setSundayForeground(Color sundayColor) {
        if (sundayColor == null) {
            return;
        }
        this.sundayForeground = sundayColor;
    }

    /**
     * 本日日付 を設定します。
     * 
     * @param today 本日日付
     */
    public void setToday(Calendar today) {
        this.today = today;
        repaint();
    }

    /**
     * 今日の日付を囲む図形の描画色を設定します。
     * 
     * @param todayColor 今日の図形描画色
     */
    public void setTodayBackground(Color todayColor) {
        if (todayColor == null) {
            return;
        }
        this.todayBackground = todayColor;
        repaint();
    }

    /**
     * 週凡例の領域を表示するかを設定します。
     * 
     * @param weekVisible 週凡例の領域を表示させる場合はtrue
     */
    public void setWeekVisible(boolean weekVisible) {
        this.weekVisible = weekVisible;
        restracture();
    }

    /**
     * 表示対象の年を設定します。 <br>
     * 指定の値が0-9999の範囲にない場合は、何もしません。 <br>
     * 設定結果を反映するために再描画を行います。
     * 
     * @param year 表示対象の年
     */
    public void setYear(int year) {
        if ((getMinimumCalendar().get(Calendar.YEAR) <= year)
                && (year <= getMaximumCalendar().get(Calendar.YEAR))) {
            if (getCurrentCalendar().get(Calendar.YEAR) != year) {
                getCurrentCalendar().set(Calendar.YEAR, year);
                onDisplayMonthChange();
            }
        }
    }

    /**
     * 表示対象年の領域を表示するかを設定します。
     * 
     * @param yearVisible 表示対象年の領域を表示させる場合はtrue
     */
    public void setYearVisible(boolean yearVisible) {
        this.yearVisible = yearVisible;
        restracture();
    }

    /**
     * 表示対象の月を当月に移動します。
     */
    public void thisMonth() {
        setCurrentCalendar(Calendar.getInstance());
    }

    /**
     * 次の年に進めるかを返します。
     * 
     * @return 次の年に進める場合はtrue
     */
    private boolean canMoveNextYear() {
        return compareY(getMaximumCalendar(), getCurrentCalendar()) > 0;
    }

    /**
     * 前の年に戻れるかを返します。
     * 
     * @return 前の年に戻れる場合はtrue
     */
    private boolean canMovePreviewYear() {
        return compareY(getMinimumCalendar(), getCurrentCalendar()) < 0;
    }

    /**
     * 対象日付が選択できる年月かを返します。
     * 
     * @param cr 対象日付
     * @return 選択できる場合はtrue
     */
    private boolean canSelectDate(Calendar cr) {
        if (compareYM(getMinimumCalendar(), cr) <= 0) {
            if (compareYM(getMaximumCalendar(), cr) >= 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * プロパティによるコンポーネントの初期化を行います。
     * 
     * @throws Exception 初期設定ができなかった場合。
     */
    private void jbInit() throws Exception {
        addFocusListener(this);
        addKeyListener(this);

        setAutoWrap(false);
        setHgap(0);
        setVgap(0);

        setFocusable(true);
        setSelectMode(SELECT_SINGLE);

        Calendar cal = Calendar.getInstance();
        cal = (Calendar) cal.clone();
        cal.set(9999, 11, 1);
        setMaximumCalendar(cal);
        cal = (Calendar) cal.clone();
        cal.set(1, 11, 1);
        setMinimumCalendar(cal);

        cal = Calendar.getInstance();
        setCurrentCalendar(cal);
        setFocusCalendar(cal);
        setBeginSelectCalendar(cal);
        setEndSelectCalendar(cal);

        setDayFont(getFont());

        this.setFocusable(true);
    }

    /**
     * 日付を年のみで比較します。
     * 
     * @param x 比較元1
     * @param y 比較元2
     * @return -1(比較元1の方が前)、0(比較元1と比較元2は同一)、1(比較元1の方が後)
     */
    protected int compareY(Calendar x, Calendar y) {
        int xY = x.get(Calendar.YEAR) * 10000;
        int yY = y.get(Calendar.YEAR) * 10000;
        if (xY < yY) {
            return -1;
        }
        if (xY > yY) {
            return 1;
        }
        return 0;
    }

    /**
     * 日付を年月のみで比較します。
     * 
     * @param x 比較元1
     * @param y 比較元2
     * @return -1(比較元1の方が前)、0(比較元1と比較元2は同一)、1(比較元1の方が後)
     */
    protected int compareYM(Calendar x, Calendar y) {
        int xYM = x.get(Calendar.YEAR) * 10000 + x.get(Calendar.MONTH) * 100;
        int yYM = y.get(Calendar.YEAR) * 10000 + y.get(Calendar.MONTH) * 100;
        if (xYM < yYM) {
            return -1;
        }
        if (xYM > yYM) {
            return 1;
        }
        return 0;
    }

    /**
     * 日付を年月日のみで比較します。
     * 
     * @param x 比較元1
     * @param y 比較元2
     * @return -1(比較元1の方が前)、0(比較元1と比較元2は同一)、1(比較元1の方が後)
     */
    protected int compareYMD(Calendar x, Calendar y) {
        int xYMD = x.get(Calendar.YEAR) * 10000 + x.get(Calendar.MONTH) * 100
                + x.get(Calendar.DAY_OF_MONTH);
        int yYMD = y.get(Calendar.YEAR) * 10000 + y.get(Calendar.MONTH) * 100
                + y.get(Calendar.DAY_OF_MONTH);
        if (xYMD < yYMD) {
            return -1;
        }
        if (xYMD > yYMD) {
            return 1;
        }
        return 0;
    }

    /**
     * 空日付ラベルを生成して返します。
     * 
     * @return 空日付ラベル
     */
    protected JComponent createBlankDate() {
        ACLabel blank = new ACLabel();
        blank.setPreferredSize(getDaySize());
        blank.setBorder(getDayBorder());
        return blank;
    }

    /**
     * 日にちコントロールを生成して返します。
     * 
     * @param cal 対象日付
     * @throws Exception 処理例外
     * @return 日にちコントロール
     */
    protected JComponent createDate(Calendar cal) throws Exception {
        NCCalendarLabel day = new NCCalendarLabel();
        day.addMouseListener(mouseListener);

        day.setFont(getDayFont());
        day.setParentCalendar(this);
        day.setCalendar(cal);

        day.setPreferredSize(getDaySize());
        day.setBorder(getDayBorder());
        day.setText(String.valueOf(cal.get(Calendar.DAY_OF_MONTH)));

        // 祝日対応
        ArrayList holydays = VRDateParser.getHolydays(cal);
        int holydaySize = holydays.size();
        if (holydaySize > 0) {
            StringBuffer sb = new StringBuffer();
            VRDateParserHolyday h = (VRDateParserHolyday) holydays.get(0);
            sb.append(h.getId());
            for (int j = 1; j < holydaySize; j++) {
                h = (VRDateParserHolyday) holydays.get(j);
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(h.getId());
            }
            day.setToolTipText(sb.toString());
            day.setForeground(getHolidayForeground());
        } else {
            switch (cal.get(Calendar.DAY_OF_WEEK)) {
            case Calendar.SUNDAY:
                day.setForeground(getSundayForeground());
                break;
            case Calendar.SATURDAY:
                day.setForeground(getSaturdayForeground());
                break;
            default:
                day.setForeground(getNormalDayColor());
            }
        }

        return day;
    }

    /**
     * 月ラベルを生成して返します。
     * 
     * @param cal 対象年月
     * @return 月ラベル
     */
    protected JComponent createMonthTitle(Calendar cal) {

        ACLabel monthLabel = new ACLabel();
        monthLabel.setBackground(getMonthHeaderBackground());
        monthLabel.setForeground(getMonthHeaderForeground());
        monthLabel.setOpaque(true);
        monthLabel.setText((cal.get(Calendar.MONTH) + 1) + " 月");
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return monthLabel;
    }

    /**
     * 曜日ラベルを生成して返します。
     * 
     * @param wday 曜日
     * @return 曜日ラベル
     */
    protected JComponent createWeekTitle(int wday) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_WEEK, wday);

        ACLabel lbl = new ACLabel();

        lbl.setForeground(getWeekHeaderForeground());
        lbl.setBackground(getWeekHeaderBackground());
        lbl.setOpaque(true);
        int h = 20;
        if (getFont() != null) {
            FontMetrics fm = getFontMetrics(getFont());
            h = Math.max(h, fm.getHeight());
        }
        lbl.setPreferredSize(new Dimension(getDaySize().width, h));
        try {
            lbl.setText(VRDateParser.format(cal, "E"));
        } catch (Exception ex) {
            lbl.setText("");
        }
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        return lbl;
    }

    /**
     * 年ラベルを生成して返します。
     * 
     * @return 年ラベル
     */
    protected JComponent createYearTitle() {
        ACLabel yearLabel = new ACLabel();
        yearLabel.setBackground(getYearHeaderBackground());
        yearLabel.setForeground(getYearHeaderForeground());
        yearLabel.setOpaque(true);
        yearLabel.setText(getYear() + " 年");
        yearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return yearLabel;
    }

    /**
     * カレンダーを描画する必要がある事をリスナーに通知します。<br>
     * 日付等の描画を行う直前に呼び出されるため、背景描画などに用います。
     * 
     * @param e カレンダー描画イベント情報
     */
    protected void fireCalendarDrawing(ACCalendarDrawEvent e) {
        if (calendarDrawListeners != null) {
            Vector listeners = calendarDrawListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ACCalendarDrawListener) listeners.elementAt(i))
                        .calendarDrawing(e);
            }
        }
    }

    /**
     * 表示対象の月を更新したことをリスナーに通知します。<br>
     * 管理情報の更新中であるかを確認せずに通知するため、<br>
     * 直接この関数を呼ばずonDisplayMonthChangeを呼ぶようにしてください。
     * 
     * @param e カレンダー変更イベント情報
     */
    protected void fireDisplayMonthChanged(ACCalendarChangeEvent e) {
        if (calendarChangeListeners != null) {
            Vector listeners = calendarChangeListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ACCalendarChangeListener) listeners.elementAt(i))
                        .displayMonthChanged(e);
            }
        }
    }

    /**
     * 日付のクリックをリスナーに通知します。
     * 
     * @param e マウスイベント情報
     */
    protected void fireDayMouseClicked(MouseEvent e) {
        if (dayMouseListeners != null) {
            Vector listeners = dayMouseListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((MouseListener) listeners.elementAt(i)).mouseClicked(e);
            }
        }
    }

    /**
     * 日付の選択情報を更新したことをリスナーに通知します。<br>
     * 管理情報の更新中であるかを確認せずに通知するため、<br>
     * 直接この関数を呼ばずonSelectedChangedを呼ぶようにしてください。
     * 
     * @param e カレンダー変更イベント情報
     */
    protected void fireSelectedChanged(ACCalendarChangeEvent e) {
        if (calendarChangeListeners != null) {
            Vector listeners = calendarChangeListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((ACCalendarChangeListener) listeners.elementAt(i))
                        .selectedChanged(e);
            }
        }
    }

    /**
     * 日付ラベルの推奨サイズを返します。
     * 
     * @return 日付ラベルの推奨サイズ
     */
    protected Dimension getDaySize() {
        if (dayPreferedSize == null) {
            int wh = 30;
            if (getDayFont() != null) {
                FontMetrics fm = getFontMetrics(getDayFont());
                if (fm != null) {
                    wh = Math.max(fm.getHeight() + 2, fm.stringWidth("WWW"));
                }
            }
            dayPreferedSize = new Dimension(wh, wh);
        }
        return dayPreferedSize;
    }

    /**
     * フォーカス対象日であるかを返します。
     * 
     * @param label 対象
     * @return フォーカス対象日であるか
     */
    protected boolean isFocusDay(NCCalendarLabel label) {
        return compareYMD(label.getCalendar(), getFocusCalendar()) == 0;
    }

    /**
     * 本日日付であるかを返します。
     * 
     * @param label 対象
     * @return 本日日付であるか
     */
    protected boolean isToday(NCCalendarLabel label) {
        return compareYMD(label.getCalendar(), getToday()) == 0;
    }

    /**
     * 表示対象の月を更新した時に呼び出されるイベントです。 <br>
     * fireDisplayMonthChangeを呼び出して、リスナーに表示対象月の更新を通知します。
     */
    protected void onDisplayMonthChange() {
        restracture();
        fireDisplayMonthChanged(new ACCalendarChangeEvent(this, getYear(),
                getMonth(), getBeginSelectCalendar(), getEndSelectCalendar()));
    }

    /**
     * 日付の選択情報を更新した時に呼び出されるイベントです。 <br>
     * fireSelectedChangedを呼び出して、リスナーに選択情報の更新を通知します。
     */
    protected void onSelectedChanged() {
        fireSelectedChanged(new ACCalendarChangeEvent(this, getYear(),
                getMonth(), getBeginSelectCalendar(), getEndSelectCalendar()));
    }

    /**
     * 指定日を描画します。
     * 
     * @param g 描画先
     * @param label 描画対象
     */
    protected void paintDay(Graphics g, NCCalendarLabel label) {

        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = g.getClipBounds();
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();

        // 描画系イベント発生
        ACCalendarDrawEvent cde = new ACCalendarDrawEvent(this, label
                .getCalendar(), label.getToolTipText(), null, label
                .getForeground(), getForeground());
        fireCalendarDrawing(cde);
        Color selectedColor = cde.getSelectedColor();

        if (label.getCalendar() == getBeginSelectCalendar()) {
            selectedColor = getBeginSelectColor();
        } else if (label.getCalendar() == getEndSelectCalendar()) {
            selectedColor = getEndSelectColor();
        } else if (isSelected(label.getCalendar())) {
            selectedColor = getSelectedBackground();
        }

        // １コマ毎の背景色
        if (selectedColor != null) {
            g2.setColor(selectedColor);
            if (isEllipse()) {
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.fillOval(x, y, w, h);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
            } else {
                g2.fillRect(x + 1, y + 1, w - 2, h - 2);
            }
        }

        if (isToday(label)) {

            // 当日描画
            g2.setColor(getTodayBackground());
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.fillOval(x + 3, y + 3, w - 6, h - 6);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        label.superPaintComponent(g);

        // フォーカス枠描画

        if (isFocusDay(label)) {
            g2.setColor(getFocusColor());
            if (isEllipse()) {
                // 丸で描画
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawOval(x + 2, y + 2, w - 4, h - 4);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
            } else {
                // 四角で描画
                Border db = getDayBorder();
                if (db != null) {
                    Insets is = db.getBorderInsets(label);
                    // カレンダー内枠がある場合は、小さめに描画
                    g2.drawRect(x + is.left, y + is.top,
                            w - is.left - is.right, h - is.top - is.bottom);
                } else {
                    g2.drawRect(x + 2, y + 2, w - 4, h - 4);
                }
            }
        }

    }

    /**
     * 指定月のカレンダを生成して返します。
     * 
     * @param cal 対象年月
     * @throws Exception 処理例外
     * @return カレンダ
     */
    protected ACPanel createMonth(Calendar cal) throws Exception {
        ACPanel monthPanel = new ACPanel();
        monthPanel.setAutoWrap(false);
        monthPanel.setHgap(0);
        monthPanel.setVgap(0);
        monthPanel.setOpaque(false);
        monthPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        // 月ヘッダ
        if (isMonthVisible()) {
            monthPanel.add(createMonthTitle(cal), VRLayout.NORTH);
        }

        // 週ヘッダ
        if (isWeekVisible()) {
            for (int i = Calendar.SUNDAY; i < Calendar.SATURDAY; i++) {
                monthPanel.add(createWeekTitle(i), VRLayout.FLOW);
            }
            monthPanel.add(createWeekTitle(Calendar.SATURDAY),
                    VRLayout.FLOW_RETURN);
        }

        // 月初の空曜日を追加
        int begin = cal.get(Calendar.DAY_OF_WEEK);
        for (int i = Calendar.SUNDAY; i < begin; i++) {
            monthPanel.add(createBlankDate(), VRLayout.FLOW);
        }
        int end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 1日ずつ追加
        Calendar c = (Calendar) cal.clone();
        for (int i = 1; i <= end; i++) {
            JComponent day = createDate((Calendar) c.clone());

            if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
                monthPanel.add(day, VRLayout.FLOW_RETURN);
            } else {
                monthPanel.add(day, VRLayout.FLOW);
            }
            c.add(Calendar.DAY_OF_MONTH, 1);
        }
        c = (Calendar) cal.clone();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));

        // 月末の空曜日を追加
        begin = c.get(Calendar.DAY_OF_WEEK);
        if (begin < Calendar.SATURDAY) {
            for (int i = begin; i < Calendar.SATURDAY - 1; i++) {
                monthPanel.add(createBlankDate(), VRLayout.FLOW);
            }
            monthPanel.add(createBlankDate(), VRLayout.FLOW_RETURN);
        }

        // 月末の空週を追加
        for (int i = c.get(Calendar.WEEK_OF_MONTH); i < 6; i++) {
            for (int j = Calendar.SUNDAY; j < Calendar.SATURDAY; j++) {
                monthPanel.add(createBlankDate(), VRLayout.FLOW);
            }
            monthPanel.add(createBlankDate(), VRLayout.FLOW_RETURN);
        }

        setBackground(Color.white);
        return monthPanel;
    }

    /**
     * 複数選択モードにおいて、指定日付の選択状況を反転します。
     * 
     * @param cal 対象日付
     */
    protected void swapMultiSelect(Calendar cal) {
        if (multiSelectedCalendarMap.contains(cal)) {
            multiSelectedCalendarMap.remove(cal);
        } else {
            multiSelectedCalendarMap.add(cal);
        }
    }

    /**
     * カレンダー日付用マウスリスナです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2005/12/01
     */
    protected class NCCalendarDateMouseListener extends MouseAdapter {

        private Calendar pressedCalendar = null;

        public void mouseClicked(MouseEvent e) {
            fireDayMouseClicked(e);
        }

        public void mouseEntered(MouseEvent e) {
            if (isPressed()) {
                // マウス押下中
                if (e.getSource() instanceof NCCalendarLabel) {
                    NCCalendarLabel label = (NCCalendarLabel) e.getSource();
                    Calendar cal = label.getCalendar();
                    setFocusCalendar(cal);

                    switch (getSelectMode()) {
                    case ACCalendar.SELECT_SPAN:
                        if (compareYMD(pressedCalendar, cal) < 0) {
                            setEndSelectCalendar(cal);
                            setBeginSelectCalendar(pressedCalendar);
                        } else if (compareYMD(pressedCalendar, cal) > 0) {
                            setBeginSelectCalendar(cal);
                            setEndSelectCalendar(pressedCalendar);
                        }
                        break;
                    }
                    repaint();
                }

            }
        }

        public void mouseExited(MouseEvent e) {
        }

        public void mousePressed(MouseEvent e) {
            requestFocus();
            if (e.getSource() instanceof NCCalendarLabel) {
                Calendar cal = ((NCCalendarLabel) e.getSource()).getCalendar();
                pressedCalendar = cal;
                setFocusCalendar(cal);
                setBeginSelectCalendar(cal);
                setEndSelectCalendar(cal);
                repaint();
            }

        }

        public void mouseReleased(MouseEvent e) {
            if (isPressed()) {
                pressedCalendar = null;
                if (e.getSource() instanceof NCCalendarLabel) {
                    NCCalendarLabel label = (NCCalendarLabel) e.getSource();
                    switch (getSelectMode()) {
                    case ACCalendar.SELECT_SINGLE:
                        setBeginSelectCalendar(getFocusCalendar());
                        setEndSelectCalendar(getFocusCalendar());
                        break;
                    case ACCalendar.SELECT_MULTI:
                        swapMultiSelect(label.getCalendar());
                        break;

                    }
                    repaint();
                }

            }
        }

        /**
         * マウスを押下しているかを返します。
         * 
         * @return マウスを押下しているか
         */
        private boolean isPressed() {
            return pressedCalendar != null;
        }

    }

    /**
     * カレンダー日付コンポーネントです。
     * <p>
     * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
     * </p>
     * 
     * @author Tozo Tanaka
     * @version 1.0 2005/12/01
     */
    protected class NCCalendarLabel extends ACLabel {
        private ACCalendar calendar;

        private Calendar date;

        public NCCalendarLabel() {
            super();
        }

        public NCCalendarLabel(Icon image) {
            super(image);
        }

        public NCCalendarLabel(Icon image, int horizontalAlignment) {
            super(image, horizontalAlignment);
        }

        public NCCalendarLabel(String text) {
            super(text);
        }

        public NCCalendarLabel(String text, Icon image, int horizontalAlignment) {
            super(text, image, horizontalAlignment);
        }

        public NCCalendarLabel(String text, int horizontalAlignment) {
            super(text, horizontalAlignment);
        }

        /**
         * 対象日付 を返します。
         * 
         * @return date 対象日付
         */
        public Calendar getCalendar() {
            return date;
        }

        public ACCalendar getParentCalendar() {
            return calendar;
        }

        public void paintComponent(Graphics g) {
            if (getParentCalendar() == null) {
                super.paintComponent(g);

            } else {
                getParentCalendar().paintDay(g, this);
            }

        }

        /**
         * 対象日付 を設定します。
         * 
         * @param date 対象日付
         */
        public void setCalendar(Calendar date) {
            this.date = date;
        }

        public void setParentCalendar(ACCalendar calendar) {
            this.calendar = calendar;
        }

        public void superPaintComponent(Graphics g) {
            super.paintComponent(g);
        }

        protected void initComponent() {
            super.initComponent();
            this.setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

}
