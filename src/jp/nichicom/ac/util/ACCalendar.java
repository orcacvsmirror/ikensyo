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
 * ���t�I���\�ȃJ�����_�[�ł��B
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 */
public class ACCalendar extends ACPanel implements KeyListener, FocusListener {

    /** �I�����[�h���u�����I���v�ɐݒ肷��萔�ł��B */
    public final static int SELECT_MULTI = 2;

    /** �I�����[�h���u�P��I���v�ɐݒ肷��萔�ł��B */
    public final static int SELECT_SINGLE = 0;

    /** �I�����[�h���u�͈͑I���v�ɐݒ肷��萔�ł��B */
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
     * ���^�C�g���̔w�i�F ��Ԃ��܂��B
     * 
     * @return ���^�C�g���̔w�i�F
     */
    public Color getMonthHeaderBackground() {
        return monthHeaderBackground;
    }

    /**
     * ���^�C�g���̔w�i�F ��ݒ肵�܂��B
     * 
     * @param monthHeaderBackground ���^�C�g���̔w�i�F
     */
    public void setMonthHeaderBackground(Color monthHeaderBackground) {
        this.monthHeaderBackground = monthHeaderBackground;
    }

    /**
     * ���^�C�g���̑O�i�F ��Ԃ��܂��B
     * 
     * @return ���^�C�g���̑O�i�F
     */
    public Color getMonthHeaderForeground() {
        return monthHeaderForeground;
    }

    /**
     * ���^�C�g���̑O�i�F ��ݒ肵�܂��B
     * 
     * @param monthHeaderForeground ���^�C�g���̑O�i�F
     */
    public void setMonthHeaderForeground(Color monthHeaderForeground) {
        this.monthHeaderForeground = monthHeaderForeground;
    }

    /**
     * �N�^�C�g���̔w�i�F ��Ԃ��܂��B
     * 
     * @return �N�^�C�g���̔w�i�F
     */
    public Color getYearHeaderBackground() {
        return yearHeaderBackground;
    }

    /**
     * �N�^�C�g���̔w�i�F ��ݒ肵�܂��B
     * 
     * @param yearHeaderBackground �N�^�C�g���̔w�i�F
     */
    public void setYearHeaderBackground(Color yearHeaderBackground) {
        this.yearHeaderBackground = yearHeaderBackground;
    }

    /**
     * �N�^�C�g���̑O�i�F ��Ԃ��܂��B
     * 
     * @return �N�^�C�g���̑O�i�F
     */
    public Color getYearHeaderForeground() {
        return yearHeaderForeground;
    }

    /**
     * �N�^�C�g���̑O�i�F ��ݒ肵�܂��B
     * 
     * @param yearHeaderForeground �N�^�C�g���̑O�i�F
     */
    public void setYearHeaderForeground(Color yearHeaderForeground) {
        this.yearHeaderForeground = yearHeaderForeground;
    }

    /**
     * �T�^�C�g���̔w�i�F ��Ԃ��܂��B
     * 
     * @return �T�^�C�g���̔w�i�F
     */
    public Color getWeekHeaderBackground() {
        return weekHeaderBackground;
    }

    /**
     * �T�^�C�g���̔w�i�F ��ݒ肵�܂��B
     * 
     * @param weekHeaderBackground �T�^�C�g���̔w�i�F
     */
    public void setWeekHeaderBackground(Color weekHeaderBackground) {
        this.weekHeaderBackground = weekHeaderBackground;
    }

    /**
     * �T�^�C�g���̑O�i�F ��Ԃ��܂��B
     * 
     * @return �T�^�C�g���̑O�i�F
     */
    public Color getWeekHeaderForeground() {
        return weekHeaderForeground;
    }

    /**
     * �T�^�C�g���̑O�i�F ��ݒ肵�܂��B
     * 
     * @param weekHeaderForeground �T�^�C�g���̑O�i�F
     */
    public void setWeekHeaderForeground(Color weekHeaderForeground) {
        this.weekHeaderForeground = weekHeaderForeground;
    }

    /**
     * �t�H�[�J�X�擾���̔w�i�F ��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�擾���̔w�i�F
     */
    public Color getFocusGainedBackground() {
        return focusGainedBackground;
    }

    /**
     * �t�H�[�J�X�擾���̔w�i�F ��ݒ肵�܂��B
     * 
     * @param focusGainedBackground �t�H�[�J�X�擾���̔w�i�F
     */
    public void setFocusGainedBackground(Color focusGainedBackground) {
        this.focusGainedBackground = focusGainedBackground;
        repaint();
    }

    /**
     * �t�H�[�J�X�r�����̔w�i�F ��Ԃ��܂��B
     * 
     * @return �t�H�[�J�X�r�����̔w�i�F
     */
    public Color getFocusLostBackground() {
        return focusLostBackground;
    }

    /**
     * �t�H�[�J�X�r�����̔w�i�F ��ݒ肵�܂��B
     * 
     * @param focusLostBackground �t�H�[�J�X�r�����̔w�i�F
     */
    public void setFocusLostBackground(Color focusLostBackground) {
        this.focusLostBackground = focusLostBackground;
        repaint();
    }

    /**
     * �R���X�g���N�^�ł��B
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
     * �w�肵���\�����X�V���X�i�[�����X�i�[�ꗗ�ɒǉ����܂��B
     * 
     * @param l ���X�i�[�ꗗ�ɒǉ�����\�����X�V���X�i�[
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
     * �w�肵���j�����擾���X�i�[�����X�i�[�ꗗ�ɒǉ����܂��B
     * 
     * @param l ���X�i�[�ꗗ�ɒǉ�����j�����擾���X�i�[
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
     * �w�肵���`�惊�X�i�[�����X�i�[�ꗗ�ɒǉ����܂��B
     * 
     * @param l ���X�i�[�ꗗ�ɒǉ�����`�惊�X�i�[
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
     * ���̌��ɐi�߂邩��Ԃ��܂��B
     * 
     * @return ���̌��ɐi�߂�ꍇ��true
     */
    public boolean canMoveNextMonth() {
        return compareYM(getMaximumCalendar(), getCurrentCalendar()) > 0;
    }

    /**
     * �O�̌��ɖ߂�邩��Ԃ��܂��B
     * 
     * @return �O�̌��ɖ߂��ꍇ��true
     */
    public boolean canMovePreviewMonth() {
        return compareYM(getMinimumCalendar(), getCurrentCalendar()) < 0;
    }

    /**
     * �I�������N���A���܂��B
     * <p>
     * �ǂ̓��t���I�����Ă��Ȃ���ԂɂȂ�܂��B
     * </p>
     */
    public void clearSelected() {
        multiSelectedCalendarMap.clear();
        setBeginSelectCalendar(null);
        setEndSelectCalendar(null);
        onSelectedChanged();
    }

    /**
     * �t�H�[�J�X�擾���ɌĂяo�����C�x���g�ł��B
     * <p>
     * �R���|�[�l���g�̍ĕ`����s���܂��B
     * </p>
     * 
     * @param e �t�H�[�J�X�C�x���g���
     */
    public void focusGained(FocusEvent e) {
        setBackground(getFocusGainedBackground());
        repaint();
    }

    /**
     * �t�H�[�J�X�r�����ɌĂяo�����C�x���g�ł��B
     * <p>
     * �R���|�[�l���g�̍ĕ`����s���܂��B
     * </p>
     * 
     * @param e �t�H�[�J�X�C�x���g���
     */
    public void focusLost(FocusEvent e) {
        setBackground(getFocusLostBackground());
        repaint();
    }

    /**
     * �I�𒆂̓��t��Ԃ��܂��B
     * <p>
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�́A�I���J�n�����Ӗ����܂��B
     * </p>
     * 
     * @return �I���J�n��
     */
    public Calendar getBeginSelectCalendar() {
        return beginSelectCalendar;
    }

    /**
     * �I�𒆂̓��t��Ԃ��܂��B
     * <p>
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�́A�I���J�n�����Ӗ����܂��B
     * </p>
     * 
     * @return �I���J�n��
     */
    public Date getBeginSelectDate() {
        if (beginSelectCalendar == null) {
            return null;
        }
        return beginSelectCalendar.getTime();
    }

    /**
     * �I���J�n���̓��t���͂ސ}�`�̕`��F��Ԃ��܂��B
     * 
     * @return �I���J�n���̐}�`�`��F
     */
    public Color getBeginSelectColor() {
        return beginSelectColor;
    }

    /**
     * �J�����_�[���s�����ɂ����`�悷�邩��Ԃ��܂��B
     * <p>
     * ���݂̕\���Ώی�������ɕ`�悵�A����ȍ~�̖����̃J�����_�[�𑱂��ĕ`�悵�܂��B
     * </p>
     * 
     * @return �s�����̕`�搔
     */
    public int getCols() {
        return cols;
    }

    /**
     * �\���Ώ۔N�� ��Ԃ��܂��B
     * 
     * @return �\���Ώ۔N��
     */
    public Calendar getCurrentCalendar() {
        return currentCalednar;
    }

    /**
     * ���t�p�̃{�[�_�[ ��Ԃ��܂��B
     * 
     * @return ���t�p�̃{�[�_�[
     */
    public Border getDayBorder() {
        return dayBorder;
    }

    /**
     * ���t�p�̃t�H���g ��Ԃ��܂��B
     * 
     * @return ���t�p�̃t�H���g
     */
    public Font getDayFont() {
        return dayFont;
    }

    /**
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�Ɏg�p����A�I���I������Ԃ��܂��B
     * 
     * @return �I���I����
     */
    public Calendar getEndSelectCalendar() {
        return endSelectCalendar;
    }

    /**
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�Ɏg�p����A�I���I������Ԃ��܂��B
     * <p>
     * �I������Ă��Ȃ��ꍇ�́Anull��Ԃ��܂��B
     * </p>
     * 
     * @return �I���I����
     */
    public Date getEndSelectDate() {
        if (endSelectCalendar == null) {
            return null;
        }
        return endSelectCalendar.getTime();
    }

    /**
     * �I���I�����̓��t���͂ސ}�`�̕`��F��Ԃ��܂��B
     * 
     * @return �I���I�����̐}�`�`��F
     */
    public Color getEndSelectColor() {
        return endSelectColor;
    }

    /**
     * �t�H�[�J�X�̑Ώۓ� ��Ԃ��܂��B
     * 
     * @return focusCalendar
     */
    public Calendar getFocusCalendar() {
        return focusCalendar;
    }

    /**
     * �ړ����̑I��͈͂̓��t���͂ސ}�`�̕`��F��Ԃ��܂��B
     * <p>
     * �I��͈͂̒[���h���b�O���Ĕ͈͂�ύX���Ă��鎞�ɂ��̓��t���͂ސ}�`�̕`��F�ł��B
     * </p>
     * 
     * @return �I��͈͈ړ����̐}�`�`��F
     */
    public Color getFocusColor() {
        return focusColor;
    }

    /**
     * �x���ɊY��������t�̕`��F��Ԃ��܂��B
     * 
     * @return �x���̕`��F
     */
    public Color getHolidayForeground() {
        return holidayForeground;
    }

    /**
     * �ő�\���N����Ԃ��܂��B
     * 
     * @return �ő�\���N��
     */
    public Calendar getMaximumCalendar() {
        return maximumCalendar;
    }

    /**
     * �ŏ��\���N����Ԃ��܂��B
     * 
     * @return �ŏ��\���N��
     */
    public Calendar getMinimumCalendar() {
        return minimumCalendar;
    }

    /**
     * �\���Ώۂ̌���Ԃ��܂��B
     * 
     * @return �\���Ώۂ̌�
     */
    public int getMonth() {
        return getCurrentCalendar().get(Calendar.MONTH) + 1;
    }

    /**
     * �ʏ���̕����F ��Ԃ��܂��B
     * 
     * @return �ʏ���̕����F
     */
    public Color getNormalDayColor() {
        return normalDayColor;
    }

    /**
     * ���t�����ȊO�̔w�i�F��Ԃ��܂��B
     * 
     * @return ���t�����ȊO�̔w�i�F
     */
    public Color getOutsideColor() {
        return outsideColor;
    }

    /**
     * �J�����_�[�������ɂ����`�悷�邩��Ԃ��܂��B
     * <p>
     * ���݂̕\���Ώی�������ɕ`�悵�A����ȍ~�̖����̃J�����_�[�𑱂��ĕ`�悵�܂��B
     * </p>
     * 
     * @return ������̕`�搔
     */
    public int getRows() {
        return rows;
    }

    /**
     * �y�j���̓��t�`��F��Ԃ��܂��B
     * 
     * @return �y�j���̓��t�`��F
     */
    public Color getSaturdayForeground() {
        return saturdayForeground;
    }

    /**
     * �I����̓��t���͂ސ}�`�̕`��F��Ԃ��܂��B
     * 
     * @return �I����̐}�`�`��F
     */
    public Color getSelectedBackground() {
        return selectedBackground;
    }

    /**
     * �����I�����[�h�ɂ�����I����t�̏W����Ԃ��܂��B
     * 
     * @return �I����t�̏W��
     */
    public ArrayList getMultiSelectCalendar() {
        return new ArrayList(Arrays.asList(multiSelectedCalendarMap.toArray()));
    }

    /**
     * �I�����Ă��������Ԃ��܂��B
     * 
     * @return �I�����Ă������
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
     * ���t�̑I�����[�h��Ԃ��܂��B
     * <p>
     * �I�����[�h�́A���̂����ꂩ�ł��B<br>
     * SELECT_SINGLE�i�P��I���j�F0 <br>
     * SELECT_SPAN�i�͈͑I���j�F1 <br>
     * SELECT_MULTI�i�����I���j�F2
     * </p>
     * 
     * @return �I�����[�h
     */
    public int getSelectMode() {
        return selectMode;
    }

    /**
     * ���j���̓��t�`��F��Ԃ��܂��B
     * 
     * @return ���j���̓��t�`��F
     */
    public Color getSundayForeground() {
        return sundayForeground;
    }

    /**
     * �{�����t ��Ԃ��܂��B
     * 
     * @return �{�����t
     */
    public Calendar getToday() {
        return today;
    }

    /**
     * �����̓��t���͂ސ}�`�̕`��F��Ԃ��܂��B
     * 
     * @return �����̐}�`�`��F
     */
    public Color getTodayBackground() {
        return todayBackground;
    }

    /**
     * �\���Ώۂ̔N��Ԃ��܂��B
     * 
     * @return �\���Ώۂ̔N
     */
    public int getYear() {
        return getCurrentCalendar().get(Calendar.YEAR);
    }

    /**
     * ���t���͂ސ}�`�ɉ~���g�p���邩��Ԃ��܂��B
     * <p>
     * false�̏ꍇ�A��`�ň݂͂܂��B
     * </p>
     * 
     * @return ���t���͂ސ}�`�ɉ~���g�p����ꍇ��true
     */
    public boolean isEllipse() {
        return ellipse;
    }

    /**
     * �\���Ώی��̗̈��\�����邩��Ԃ��܂��B
     * 
     * @return �\���Ώی��̗̈��\��������ꍇ��true
     */
    public boolean isMonthVisible() {
        return monthVisible;
    }

    /**
     * �w�����I�����Ă��邩��Ԃ��܂��B
     * 
     * @param cal �Ώۓ��t
     * @return �I�����Ă��邩
     */
    public boolean isSelected(Calendar cal) {

        if (getSelectMode() == SELECT_MULTI) {
            // �����I���ŁA�`�悷������I������Ă���
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
     * �T�}��̗̈��\�����邩��Ԃ��܂��B
     * 
     * @return �T�}��̗̈��\��������ꍇ��true
     */
    public boolean isWeekVisible() {
        return weekVisible;
    }

    /**
     * �\���Ώ۔N�̗̈��\�����邩��Ԃ��܂��B
     * 
     * @return �\���Ώ۔N�̗̈��\��������ꍇ��true
     */
    public boolean isYearVisible() {
        return yearVisible;
    }

    /**
     * �L�[�������ꂽ���ɌĂяo�����C�x���g�ł��B
     * <p>
     * �������ꂽ�{�^���ɉ����A�ĕ`����s���܂��B
     * </p>
     * 
     * @param e KeyEvent �L�[�{�[�h�C�x���g���
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
                    // �J�[�\���ȊO�͉������Ȃ��B
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
                    // �͈͑I������Shift�ƃJ�[�\������
                    int cmp = compareYMD(shiftPressedCalendar, cal);
                    if (cmp < 0) {
                        // �I������ύX
                        setBeginSelectCalendar(shiftPressedCalendar);
                        setEndSelectCalendar(cal);
                    } else if (cmp > 0) {
                        // �J�n����ύX
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
                    // �\�������߂�
                    setCurrentCalendar(getFocusCalendar());
                } else if (cmp < 0) {
                    cal = (Calendar) getCurrentCalendar().clone();
                    cal.add(Calendar.MONTH, getRows() * getCols() - 1);
                    if (compareYM(cal, getFocusCalendar()) < 0) {
                        // �\�������i��
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
     * �\���Ώۂ̌������݂̕\��������P������Ɉړ����܂��B
     * <p>
     * �ړ�����N��0 �` 9999�N�͈̔͂ɂȂ��ꍇ�A <br>
     * �������́A�ő�\���N���𒴂���ꍇ�͉������܂���B
     * </p>
     */
    public void nextMonth() {
        if (canMoveNextMonth()) {
            getCurrentCalendar().add(Calendar.MONTH, 1);
            // �\���N���̂P���Ƀt�H�[�J�X�g��ݒ�
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * �\���Ώۂ̔N�����݂̕\��������P�N��Ɉړ����܂��B
     * <p>
     * �ړ�����N��0 �` 9999�N�͈̔͂ɂȂ��ꍇ�A <br>
     * �������́A�ő�\���N���𒴂���ꍇ�͉������܂���B
     * </p>
     */
    public void nextYear() {
        if (canMoveNextYear()) {
            getCurrentCalendar().add(Calendar.YEAR, 1);
            // �\���N���̂P���Ƀt�H�[�J�X�g��ݒ�
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * �\���Ώۂ̌������݂̕\��������P�����O�Ɉړ����܂��B
     * <p>
     * �ړ�����N��0 �` 9999�N�͈̔͂ɂȂ��ꍇ�A <br>
     * �������́A�ŏ��\���N���𒴂���ꍇ�͉������܂���B
     * </p>
     */
    public void previewMonth() {
        if (canMovePreviewMonth()) {
            getCurrentCalendar().add(Calendar.MONTH, -1);
            // �\���N���̂P���Ƀt�H�[�J�X�g��ݒ�
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * �\���Ώۂ̔N�����݂̕\��������P�N�O�Ɉړ����܂��B
     * <p>
     * �ړ�����N��0 �` 9999�N�͈̔͂ɂȂ��ꍇ�A <br>
     * �������́A�ŏ��\���N���𒴂���ꍇ�͉������܂���B
     * </p>
     */
    public void previewYear() {
        if (canMovePreviewYear()) {
            getCurrentCalendar().add(Calendar.YEAR, -1);
            // �\���N���̂P���Ƀt�H�[�J�X�g��ݒ�
            setFocusCalendar(getCurrentCalendar());
            onDisplayMonthChange();
        }
    }

    /**
     * �w�肵���\�����X�V���X�i�[�����X�i�[�ꗗ����폜���܂��B
     * 
     * @param l ���X�i�[�ꗗ����폜����\�����X�V���X�i�[
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
     * �w�肵���j�����擾���X�i�[�����X�i�[�ꗗ����폜���܂��B
     * 
     * @param l ���X�i�[�ꗗ����폜����j�����擾���X�i�[
     */
    public synchronized void removeDayMouseListener(MouseListener l) {
        if (dayMouseListeners != null && dayMouseListeners.contains(l)) {
            Vector v = (Vector) dayMouseListeners.clone();
            v.removeElement(l);
            dayMouseListeners = v;
        }
    }

    /**
     * �w�肵���`�惊�X�i�[�����X�i�[�ꗗ����폜���܂��B
     * 
     * @param l ���X�i�[�ꗗ����폜����`�惊�X�i�[
     */
    public synchronized void removeCalendarDrawListener(ACCalendarDrawListener l) {
        if (calendarDrawListeners != null && calendarDrawListeners.contains(l)) {
            Vector v = (Vector) calendarDrawListeners.clone();
            v.removeElement(l);
            calendarDrawListeners = v;
        }
    }

    /**
     * �J�����_�[���č\�����܂��B
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
     * �I�𒆂̓��t��Date�^�Őݒ肵�܂��B
     * <p>
     * null��ݒ肷��ƁA�ǂ̓��t���I�����Ă��Ȃ���ԂɂȂ�܂��B <br>
     * �ݒ茋�ʂ𔽉f���邽�߁A�ĕ`����s���܂��B
     * </p>
     * 
     * @param date �I���J�n��
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
     * �I�𒆂̓��t��ݒ肵�܂��B
     * <p>
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�́A�I���J�n�����Ӗ����܂��B <br>
     * �ݒ茋�ʂ𔽉f���邽�߁A�ĕ`����s���܂��B
     * </p>
     * 
     * @param beginSelectCalendar �I���J�n��
     */
    public void setBeginSelectCalendar(Calendar beginSelectCalendar) {
        this.beginSelectCalendar = beginSelectCalendar;

        onSelectedChanged();
        repaint();
    }

    /**
     * �I���J�n���̓��t���͂ސ}�`�̕`��F��ݒ肵�܂��B
     * 
     * @param beginSelectColor �I���J�n���̐}�`�`��F
     */
    public void setBeginSelectColor(Color beginSelectColor) {
        if (beginSelectColor == null) {
            return;
        }
        this.beginSelectColor = beginSelectColor;
    }

    /**
     * �J�����_�[���s�����ɂ����`�悷�邩��ݒ肵�܂��B
     * <p>
     * ���݂̕\���Ώی�������ɕ`�悵�A����ȍ~�̖����̃J�����_�[�𑱂��ĕ`�悵�܂��B
     * </p>
     * 
     * @param cols �s�����̕`�搔
     */
    public void setCols(int cols) {
        this.cols = cols;
        if (this.cols <= 0) {
            this.cols = 1;
        }
        restracture();
    }

    /**
     * �\���Ώ۔N�� ��ݒ肵�܂��B
     * 
     * @param currentCalednar �\���Ώ۔N��
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
     * ���t�p�̃{�[�_�[ ��ݒ肵�܂��B
     * 
     * @param dayBorder ���t�p�̃{�[�_�[
     */
    public void setDayBorder(Border dayBorder) {
        this.dayBorder = dayBorder;
        restracture();
    }

    /**
     * ���t�p�̃t�H���g ��ݒ肵�܂��B
     * 
     * @param dayFont ���t�p�̃t�H���g
     */
    public void setDayFont(Font dayFont) {
        this.dayFont = dayFont;
        dayPreferedSize = null;
        restracture();
    }

    /**
     * ���t���͂ސ}�`�ɉ~���g�p���邩��Ԑݒ肵�܂��B
     * <p>
     * false�̏ꍇ�A��`�ň݂͂܂��B
     * </p>
     * 
     * @param ellipse ���t���͂ސ}�`�ɉ~���g�p����ꍇ��true
     */
    public void setEllipse(boolean ellipse) {
        this.ellipse = ellipse;
    }

    /**
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�Ɏg�p����I���I������Date�^�Őݒ肵�܂��B
     * <p>
     * null��ݒ肷��ƁA�I���J�n���̂ݑI�����Ă��邱�ƂɂȂ�܂��B <br>
     * �ݒ茋�ʂ𔽉f���邽�߁A�ĕ`����s���܂��B
     * </p>
     * 
     * @param endSelectDate �I���I����
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
     * �I�����[�h��SELECT_SPAN(�͈�)�̏ꍇ�Ɏg�p����A�I���I������ݒ肵�܂��B <br>
     * �ݒ茋�ʂ𔽉f���邽�߁A�ĕ`����s���܂��B
     * 
     * @param endSelectCalendar �I���I����
     */
    public void setEndSelectCalendar(Calendar endSelectCalendar) {
        this.endSelectCalendar = endSelectCalendar;
        onSelectedChanged();
        repaint();
    }

    /**
     * �I���I�����̓��t���͂ސ}�`�̕`��F��ݒ肵�܂��B
     * 
     * @param endSelectColor �I���I�����̐}�`�`��F
     */
    public void setEndSelectColor(Color endSelectColor) {
        if (endSelectColor == null) {
            return;
        }
        this.endSelectColor = endSelectColor;
    }

    /**
     * �t�H�[�J�X�̑Ώۓ� ��ݒ肵�܂��B
     * 
     * @param focusCalendar �t�H�[�J�X�̑Ώۓ�
     */
    public void setFocusCalendar(Calendar focusCalendar) {
        this.focusCalendar = focusCalendar;
        repaint();
    }

    /**
     * �ړ����̑I��͈͂̓��t���͂ސ}�`�̕`��F��ݒ肵�܂��B <br>
     * �I��͈͂̒[���h���b�O���Ĕ͈͂�ύX���Ă��鎞�ɂ��̓��t���͂ސ}�`�̕`��F�ł��B
     * 
     * @param focusColor �I��͈͈ړ����̐}�`�`��F
     */
    public void setFocusColor(Color focusColor) {
        if (focusColor == null) {
            return;
        }
        this.focusColor = focusColor;
    }

    /**
     * �x���ɊY��������t�̕`��F��ݒ肵�܂��B
     * 
     * @param holidayColor �x���̕`��F
     */
    public void setHolidayForeground(Color holidayColor) {
        if (holidayColor == null) {
            return;
        }
        this.holidayForeground = holidayColor;
    }

    /**
     * �ő�\���N����ݒ肵�܂��B
     * 
     * @param maximumCalendar �ő�\���N��
     */
    public void setMaximumCalendar(Calendar maximumCalendar) {
        if (maximumCalendar == null) {
            return;
        }
        this.maximumCalendar = maximumCalendar;
    }

    /**
     * �ŏ��\���N����ݒ肵�܂��B
     * 
     * @param minimumCalendar �ŏ��\���N��
     */
    public void setMinimumCalendar(Calendar minimumCalendar) {
        if (minimumCalendar == null) {
            return;
        }
        this.minimumCalendar = minimumCalendar;
    }

    /**
     * �\���Ώۂ̌���ݒ肵�܂��B <br>
     * �w��̒l��1-12�͈̔͂ɂȂ��ꍇ�́A�������܂���B <br>
     * �ݒ茋�ʂ𔽉f���邽�߂ɍĕ`����s���܂��B
     * 
     * @param month �\���Ώۂ̌�
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
     * �\���Ώی��̗̈��\�����邩��ݒ肵�܂��B
     * 
     * @param monthVisible �\���Ώی��̗̈��\��������ꍇ��true
     */
    public void setMonthVisible(boolean monthVisible) {
        this.monthVisible = monthVisible;
        restracture();
    }

    /**
     * �ʏ���̕����F ��ݒ肵�܂��B
     * 
     * @param normalDayColor �ʏ���̕����F
     */
    public void setNormalDayColor(Color normalDayColor) {
        this.normalDayColor = normalDayColor;
    }

    /**
     * ���t�����ȊO�̔w�i�F��ݒ肵�܂��B
     * 
     * @param outsideColor ���t�����ȊO�̔w�i�F
     */
    public void setOutsideColor(Color outsideColor) {
        this.outsideColor = outsideColor;
    }

    /**
     * �J�����_�[�������ɂ����`�悷�邩��ݒ肵�܂��B <br>
     * ���݂̕\���Ώی�������ɕ`�悵�A����ȍ~�̖����̃J�����_�[�𑱂��ĕ`�悵�܂��B
     * 
     * @param rows ������̕`�搔
     */
    public void setRows(int rows) {
        this.rows = rows;
        if (this.rows <= 0) {
            this.rows = 1;
        }
        restracture();
    }

    /**
     * �y�j���̓��t�`��F��ݒ肵�܂��B
     * 
     * @param saturdayColor �y�j���̓��t�`��F
     */
    public void setSaturdayForeground(Color saturdayColor) {
        if (saturdayColor == null) {
            return;
        }
        this.saturdayForeground = saturdayColor;
    }

    /**
     * �I����̓��t���͂ސ}�`�̕`��F��ݒ肵�܂��B
     * 
     * @param selectedColor �I����̐}�`�`��F
     */
    public void setSelectedBackground(Color selectedColor) {
        if (selectedColor == null) {
            return;
        }
        this.selectedBackground = selectedColor;
    }

    /**
     * ���t�̑I�����[�h��ݒ肵�܂��B <br>
     * �I�����[�h�́A���̂����ꂩ�ł��B
     * <p>
     * �I�����[�h <br>
     * SELECT_SINGLE�i�P��I���j�F0 <br>
     * SELECT_SPAN�i�͈͑I���j�F1 <br>
     * SELECT_MULTI�i�����I���j�F2
     * 
     * @param selectMode �I�����[�h
     */
    public void setSelectMode(int selectMode) {
        this.selectMode = selectMode;
    }

    /**
     * ���j���̓��t�`��F��ݒ肵�܂��B
     * 
     * @param sundayColor ���j���̓��t�`��F
     */
    public void setSundayForeground(Color sundayColor) {
        if (sundayColor == null) {
            return;
        }
        this.sundayForeground = sundayColor;
    }

    /**
     * �{�����t ��ݒ肵�܂��B
     * 
     * @param today �{�����t
     */
    public void setToday(Calendar today) {
        this.today = today;
        repaint();
    }

    /**
     * �����̓��t���͂ސ}�`�̕`��F��ݒ肵�܂��B
     * 
     * @param todayColor �����̐}�`�`��F
     */
    public void setTodayBackground(Color todayColor) {
        if (todayColor == null) {
            return;
        }
        this.todayBackground = todayColor;
        repaint();
    }

    /**
     * �T�}��̗̈��\�����邩��ݒ肵�܂��B
     * 
     * @param weekVisible �T�}��̗̈��\��������ꍇ��true
     */
    public void setWeekVisible(boolean weekVisible) {
        this.weekVisible = weekVisible;
        restracture();
    }

    /**
     * �\���Ώۂ̔N��ݒ肵�܂��B <br>
     * �w��̒l��0-9999�͈̔͂ɂȂ��ꍇ�́A�������܂���B <br>
     * �ݒ茋�ʂ𔽉f���邽�߂ɍĕ`����s���܂��B
     * 
     * @param year �\���Ώۂ̔N
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
     * �\���Ώ۔N�̗̈��\�����邩��ݒ肵�܂��B
     * 
     * @param yearVisible �\���Ώ۔N�̗̈��\��������ꍇ��true
     */
    public void setYearVisible(boolean yearVisible) {
        this.yearVisible = yearVisible;
        restracture();
    }

    /**
     * �\���Ώۂ̌��𓖌��Ɉړ����܂��B
     */
    public void thisMonth() {
        setCurrentCalendar(Calendar.getInstance());
    }

    /**
     * ���̔N�ɐi�߂邩��Ԃ��܂��B
     * 
     * @return ���̔N�ɐi�߂�ꍇ��true
     */
    private boolean canMoveNextYear() {
        return compareY(getMaximumCalendar(), getCurrentCalendar()) > 0;
    }

    /**
     * �O�̔N�ɖ߂�邩��Ԃ��܂��B
     * 
     * @return �O�̔N�ɖ߂��ꍇ��true
     */
    private boolean canMovePreviewYear() {
        return compareY(getMinimumCalendar(), getCurrentCalendar()) < 0;
    }

    /**
     * �Ώۓ��t���I���ł���N������Ԃ��܂��B
     * 
     * @param cr �Ώۓ��t
     * @return �I���ł���ꍇ��true
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
     * �v���p�e�B�ɂ��R���|�[�l���g�̏��������s���܂��B
     * 
     * @throws Exception �����ݒ肪�ł��Ȃ������ꍇ�B
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
     * ���t��N�݂̂Ŕ�r���܂��B
     * 
     * @param x ��r��1
     * @param y ��r��2
     * @return -1(��r��1�̕����O)�A0(��r��1�Ɣ�r��2�͓���)�A1(��r��1�̕�����)
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
     * ���t��N���݂̂Ŕ�r���܂��B
     * 
     * @param x ��r��1
     * @param y ��r��2
     * @return -1(��r��1�̕����O)�A0(��r��1�Ɣ�r��2�͓���)�A1(��r��1�̕�����)
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
     * ���t��N�����݂̂Ŕ�r���܂��B
     * 
     * @param x ��r��1
     * @param y ��r��2
     * @return -1(��r��1�̕����O)�A0(��r��1�Ɣ�r��2�͓���)�A1(��r��1�̕�����)
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
     * ����t���x���𐶐����ĕԂ��܂��B
     * 
     * @return ����t���x��
     */
    protected JComponent createBlankDate() {
        ACLabel blank = new ACLabel();
        blank.setPreferredSize(getDaySize());
        blank.setBorder(getDayBorder());
        return blank;
    }

    /**
     * ���ɂ��R���g���[���𐶐����ĕԂ��܂��B
     * 
     * @param cal �Ώۓ��t
     * @throws Exception ������O
     * @return ���ɂ��R���g���[��
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

        // �j���Ή�
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
     * �����x���𐶐����ĕԂ��܂��B
     * 
     * @param cal �Ώ۔N��
     * @return �����x��
     */
    protected JComponent createMonthTitle(Calendar cal) {

        ACLabel monthLabel = new ACLabel();
        monthLabel.setBackground(getMonthHeaderBackground());
        monthLabel.setForeground(getMonthHeaderForeground());
        monthLabel.setOpaque(true);
        monthLabel.setText((cal.get(Calendar.MONTH) + 1) + " ��");
        monthLabel.setHorizontalAlignment(SwingConstants.CENTER);

        return monthLabel;
    }

    /**
     * �j�����x���𐶐����ĕԂ��܂��B
     * 
     * @param wday �j��
     * @return �j�����x��
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
     * �N���x���𐶐����ĕԂ��܂��B
     * 
     * @return �N���x��
     */
    protected JComponent createYearTitle() {
        ACLabel yearLabel = new ACLabel();
        yearLabel.setBackground(getYearHeaderBackground());
        yearLabel.setForeground(getYearHeaderForeground());
        yearLabel.setOpaque(true);
        yearLabel.setText(getYear() + " �N");
        yearLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return yearLabel;
    }

    /**
     * �J�����_�[��`�悷��K�v�����鎖�����X�i�[�ɒʒm���܂��B<br>
     * ���t���̕`����s�����O�ɌĂяo����邽�߁A�w�i�`��Ȃǂɗp���܂��B
     * 
     * @param e �J�����_�[�`��C�x���g���
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
     * �\���Ώۂ̌����X�V�������Ƃ����X�i�[�ɒʒm���܂��B<br>
     * �Ǘ����̍X�V���ł��邩���m�F�����ɒʒm���邽�߁A<br>
     * ���ڂ��̊֐����Ă΂�onDisplayMonthChange���ĂԂ悤�ɂ��Ă��������B
     * 
     * @param e �J�����_�[�ύX�C�x���g���
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
     * ���t�̃N���b�N�����X�i�[�ɒʒm���܂��B
     * 
     * @param e �}�E�X�C�x���g���
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
     * ���t�̑I�������X�V�������Ƃ����X�i�[�ɒʒm���܂��B<br>
     * �Ǘ����̍X�V���ł��邩���m�F�����ɒʒm���邽�߁A<br>
     * ���ڂ��̊֐����Ă΂�onSelectedChanged���ĂԂ悤�ɂ��Ă��������B
     * 
     * @param e �J�����_�[�ύX�C�x���g���
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
     * ���t���x���̐����T�C�Y��Ԃ��܂��B
     * 
     * @return ���t���x���̐����T�C�Y
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
     * �t�H�[�J�X�Ώۓ��ł��邩��Ԃ��܂��B
     * 
     * @param label �Ώ�
     * @return �t�H�[�J�X�Ώۓ��ł��邩
     */
    protected boolean isFocusDay(NCCalendarLabel label) {
        return compareYMD(label.getCalendar(), getFocusCalendar()) == 0;
    }

    /**
     * �{�����t�ł��邩��Ԃ��܂��B
     * 
     * @param label �Ώ�
     * @return �{�����t�ł��邩
     */
    protected boolean isToday(NCCalendarLabel label) {
        return compareYMD(label.getCalendar(), getToday()) == 0;
    }

    /**
     * �\���Ώۂ̌����X�V�������ɌĂяo�����C�x���g�ł��B <br>
     * fireDisplayMonthChange���Ăяo���āA���X�i�[�ɕ\���Ώی��̍X�V��ʒm���܂��B
     */
    protected void onDisplayMonthChange() {
        restracture();
        fireDisplayMonthChanged(new ACCalendarChangeEvent(this, getYear(),
                getMonth(), getBeginSelectCalendar(), getEndSelectCalendar()));
    }

    /**
     * ���t�̑I�������X�V�������ɌĂяo�����C�x���g�ł��B <br>
     * fireSelectedChanged���Ăяo���āA���X�i�[�ɑI�����̍X�V��ʒm���܂��B
     */
    protected void onSelectedChanged() {
        fireSelectedChanged(new ACCalendarChangeEvent(this, getYear(),
                getMonth(), getBeginSelectCalendar(), getEndSelectCalendar()));
    }

    /**
     * �w�����`�悵�܂��B
     * 
     * @param g �`���
     * @param label �`��Ώ�
     */
    protected void paintDay(Graphics g, NCCalendarLabel label) {

        Graphics2D g2 = (Graphics2D) g;
        Rectangle bounds = g.getClipBounds();
        int w = (int) bounds.getWidth();
        int h = (int) bounds.getHeight();
        int x = (int) bounds.getX();
        int y = (int) bounds.getY();

        // �`��n�C�x���g����
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

        // �P�R�}���̔w�i�F
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

            // �����`��
            g2.setColor(getTodayBackground());
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

            g2.fillOval(x + 3, y + 3, w - 6, h - 6);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_OFF);
        }

        label.superPaintComponent(g);

        // �t�H�[�J�X�g�`��

        if (isFocusDay(label)) {
            g2.setColor(getFocusColor());
            if (isEllipse()) {
                // �ۂŕ`��
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_ON);
                g2.drawOval(x + 2, y + 2, w - 4, h - 4);
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                        RenderingHints.VALUE_ANTIALIAS_OFF);
            } else {
                // �l�p�ŕ`��
                Border db = getDayBorder();
                if (db != null) {
                    Insets is = db.getBorderInsets(label);
                    // �J�����_�[���g������ꍇ�́A�����߂ɕ`��
                    g2.drawRect(x + is.left, y + is.top,
                            w - is.left - is.right, h - is.top - is.bottom);
                } else {
                    g2.drawRect(x + 2, y + 2, w - 4, h - 4);
                }
            }
        }

    }

    /**
     * �w�茎�̃J�����_�𐶐����ĕԂ��܂��B
     * 
     * @param cal �Ώ۔N��
     * @throws Exception ������O
     * @return �J�����_
     */
    protected ACPanel createMonth(Calendar cal) throws Exception {
        ACPanel monthPanel = new ACPanel();
        monthPanel.setAutoWrap(false);
        monthPanel.setHgap(0);
        monthPanel.setVgap(0);
        monthPanel.setOpaque(false);
        monthPanel.setBorder(BorderFactory.createLineBorder(Color.lightGray));

        // ���w�b�_
        if (isMonthVisible()) {
            monthPanel.add(createMonthTitle(cal), VRLayout.NORTH);
        }

        // �T�w�b�_
        if (isWeekVisible()) {
            for (int i = Calendar.SUNDAY; i < Calendar.SATURDAY; i++) {
                monthPanel.add(createWeekTitle(i), VRLayout.FLOW);
            }
            monthPanel.add(createWeekTitle(Calendar.SATURDAY),
                    VRLayout.FLOW_RETURN);
        }

        // �����̋�j����ǉ�
        int begin = cal.get(Calendar.DAY_OF_WEEK);
        for (int i = Calendar.SUNDAY; i < begin; i++) {
            monthPanel.add(createBlankDate(), VRLayout.FLOW);
        }
        int end = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 1�����ǉ�
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

        // �����̋�j����ǉ�
        begin = c.get(Calendar.DAY_OF_WEEK);
        if (begin < Calendar.SATURDAY) {
            for (int i = begin; i < Calendar.SATURDAY - 1; i++) {
                monthPanel.add(createBlankDate(), VRLayout.FLOW);
            }
            monthPanel.add(createBlankDate(), VRLayout.FLOW_RETURN);
        }

        // �����̋�T��ǉ�
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
     * �����I�����[�h�ɂ����āA�w����t�̑I���󋵂𔽓]���܂��B
     * 
     * @param cal �Ώۓ��t
     */
    protected void swapMultiSelect(Calendar cal) {
        if (multiSelectedCalendarMap.contains(cal)) {
            multiSelectedCalendarMap.remove(cal);
        } else {
            multiSelectedCalendarMap.add(cal);
        }
    }

    /**
     * �J�����_�[���t�p�}�E�X���X�i�ł��B
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
                // �}�E�X������
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
         * �}�E�X���������Ă��邩��Ԃ��܂��B
         * 
         * @return �}�E�X���������Ă��邩
         */
        private boolean isPressed() {
            return pressedCalendar != null;
        }

    }

    /**
     * �J�����_�[���t�R���|�[�l���g�ł��B
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
         * �Ώۓ��t ��Ԃ��܂��B
         * 
         * @return date �Ώۓ��t
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
         * �Ώۓ��t ��ݒ肵�܂��B
         * 
         * @param date �Ώۓ��t
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
