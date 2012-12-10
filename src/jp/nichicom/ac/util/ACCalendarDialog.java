package jp.nichicom.ac.util;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;

import jp.nichicom.ac.ACConstants;
import jp.nichicom.ac.component.ACButton;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.component.ACLabel;
import jp.nichicom.ac.container.ACLabelContainer;
import jp.nichicom.ac.container.ACPanel;
import jp.nichicom.ac.core.ACDialogChaine;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.util.event.ACCalendarChangeEvent;
import jp.nichicom.ac.util.event.ACCalendarChangeListener;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.text.parsers.VRDateParser;

/**
 * 日付選択可能なカレンダダイアログです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Susumu Nakahara
 * @version 1.0 2005/12/01
 * @see ACCalendar
 * @see JDialog
 */
public class ACCalendarDialog extends JDialog {

    /**
     * カレンダーダイアログを表示し、選択した日付を返します。
     * <p>
     * 取消ボタン押下時はnullを返します。
     * </p>
     * 
     * @return 選択した日付、取消ボタン押下時はnull
     */
    public static Calendar showSingleDate() {
        return showSingleDate(ACFrame.getInstance(), "カレンダ", Calendar
                .getInstance());
    }

    /**
     * カレンダーダイアログを表示し、選択した日付を返します。
     * <p>
     * 取消ボタン押下時はnullを返します。
     * </p>
     * 
     * @param calendar 初期状態で選択させる日付
     * @return 選択した日付、取消ボタン押下時はnull
     */
    public static Calendar showSingleDate(Calendar calendar) {
        return showSingleDate(ACFrame.getInstance(), "カレンダ", calendar);
    }

    /**
     * カレンダーダイアログを表示し、選択した日付を返します。
     * <p>
     * 取消ボタン押下時はnullを返します。
     * </p>
     * 
     * @param frame 親ウィンドウ
     * @param title ダイアログのタイトル
     * @param calendar 初期状態で選択させる日付
     * @return 選択した日付、取消ボタン押下時はnull
     */
    public static Calendar showSingleDate(Frame frame, String title,
            Calendar calendar) {
        ACCalendarDialog calendarDialog = new ACCalendarDialog(frame, title,
                true);
        calendarDialog.calendar.setSelectMode(ACCalendar.SELECT_SINGLE);
        calendarDialog.calendar.setBeginSelectCalendar(calendar);
        calendarDialog.calendar.setEndSelectCalendar(calendar);
        if (calendar != null) {
            calendarDialog.calendar.setYear(calendar.get(Calendar.YEAR));
            calendarDialog.calendar.setMonth(calendar.get(Calendar.MONTH) + 1);
        }

        calendarDialog.reShow(1);

        calendarDialog.show();
        if (calendarDialog.isOk()) {
            return calendarDialog.calendar.getBeginSelectCalendar();
        }
        return null;
    }

    private ACPanel buttons = new ACPanel();
    private ACCalendar calendar = new ACCalendar();
    private ACLabel comment = new ACLabel();
    private ACComboBox month = new ACComboBox();
    private ACComboBox monthCount = new ACComboBox();
    private ACButton nextMonth = new ACButton();
    private ACButton nextYear = new ACButton();
    private boolean ok;
    private ACPanel contents = new ACPanel();
    private ACPanel currentInfomation = new ACPanel();
    private ACButton previeMonth = new ACButton();
    private ACButton previewYear = new ACButton();
    private ACButton selectCancel = new ACButton();
    private ACButton selectOK = new ACButton();
    private ACButton thisMonth = new ACButton();
    private ACPanel currentChangeButtons = new ACPanel();
    private ACLabelContainer showMonthCount = new ACLabelContainer();
    private ACLabelContainer currentYear = new ACLabelContainer();
    private ACLabelContainer currentMonth = new ACLabelContainer();

    private ACComboBox year = new ACComboBox();

    /**
     * コンストラクタです。
     * <p>
     * パラメータを全て自動設定し、メインコンストラクタを呼び出します。<br>
     * 【自動設定値】<br>
     * frame - 親ウィンドウ：システムフレーム<br>
     * title - ダイアログのタイトル："カレンダ"<br>
     * modal - モーダルダイアログの場合はtrue：true
     * </p>
     */
    public ACCalendarDialog() {
        this(ACFrame.getInstance(), "カレンダ", true);
    }

    /**
     * コンストラクタです。
     * <p>
     * 継承元のコンストラクタと初期化用メソッド（jbInit）を呼び出します。
     * </p>
     * 
     * @param frame 親ウィンドウ
     * @param title ダイアログのタイトル
     * @param modal モーダルダイアログの場合はtrue
     */
    public ACCalendarDialog(Frame frame, String title, boolean modal) {
        super(frame, title, modal);
        try {
            jbInit();
            pack();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        calendar.requestFocus();
        
        ACDialogChaine.getInstance().add(this);
    }

    public void dispose() {
        ACDialogChaine.getInstance().remove(this);
        super.dispose();
    }
    
    /**
     * 決定ボタンが押されたかを返します。
     * 
     * @return 決定ボタンが押された場合はtrue
     */
    public boolean isOk() {
        return ok;
    }

    /**
     * カレンダーを表示し、適正なサイズに変更します。
     * 
     * @param calendarCount カレンダー表示数
     */
    public void reShow(int calendarCount) {
        int w = this.getWidth();
        int h = this.getHeight();

        calendar.setYearVisible(true);
        calendar.setMonthVisible(true);

        switch (calendarCount) {
        case 1:
            calendar.setYearVisible(false);
            calendar.setMonthVisible(false);

            calendar.setCols(1);
            calendar.setRows(1);
            break;
        case 2:
            calendar.setCols(2);
            calendar.setRows(1);
            break;
        case 3:
            calendar.setCols(3);
            calendar.setRows(1);
            break;
        case 6:
            calendar.setCols(3);
            calendar.setRows(2);
            break;
        case 12:
            calendar.setCols(4);
            calendar.setRows(3);
            break;

        }

        pack();
        w = getWidth();
        h = getHeight();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        this.setBounds((screenSize.width - w) / 2, (screenSize.height - h) / 2,
                w, h);
    }

    /**
     * プロパティによるコンポーネントの初期化を行います。
     * 
     * @throws Exception 何らかの例外
     */
    private void jbInit() throws Exception {
        this.getContentPane().setLayout(new BorderLayout());
        this.setSize(new Dimension(400, 340));
        this.setTitle("カレンダ");
        selectOK.setIconPath(ACConstants.ICON_PATH_OK_24);
        selectOK.setText("OK");
        selectOK.setMnemonic('O');
        selectOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setOk(true);
                dispose();
            }

        });

        selectCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }

        });
        calendar.setMonthVisible(false);
        calendar.setOpaque(true);
        calendar.setSelectMode(1);
        calendar.setYearVisible(false);
        calendar.addDayMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    // ダブルクリックであれば決定
                    if (calendar.getBeginSelectCalendar() != null) {
                        setOk(true);
                        dispose();
                    }
                }
            }
        });
        calendar.addCalendarChangeListener(new ACCalendarChangeListener() {

            public void displayMonthChanged(ACCalendarChangeEvent e) {
                year.setSelectedItem(String.valueOf(calendar.getYear()));
                month.setSelectedItem(String.valueOf(calendar.getMonth()));
            }

            public void selectedChanged(ACCalendarChangeEvent e) {

                StringBuffer sb = new StringBuffer();
                try {
                    switch (calendar.getSelectMode()) {
                    case ACCalendar.SELECT_SINGLE:
                        sb.append(VRDateParser.format(calendar
                                .getBeginSelectCalendar(), "yyyy/M/d(E)N"));
                        break;
                    case ACCalendar.SELECT_SPAN:
                        sb.append(VRDateParser.format(calendar
                                .getBeginSelectCalendar(), "yyyy/M/d(E)N"));
                        sb.append("～");
                        sb.append(VRDateParser.format(calendar
                                .getEndSelectCalendar(), "yyyy/M/d(E)N"));
                        break;
                    case ACCalendar.SELECT_MULTI: {
                        ArrayList days = calendar.getMultiSelectCalendar();
                        int end = days.size();
                        if (end > 0) {
                            sb.append(VRDateParser.format((Calendar) days
                                    .get(0), "yyyy/M/d(E)N"));
                            for (int i = 1; i < end; i++) {
                                sb.append(", ");
                                sb.append(VRDateParser.format((Calendar) days
                                        .get(i), "yyyy/M/d(E)N"));
                            }
                        }

                    }
                        break;
                    }
                } catch (Exception ex) {
                }
                comment.setText(sb.toString());

            }
        });
        showMonthCount.setText("表示月数");
        monthCount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (monthCount.getSelectedIndex() >= 0) {
                    int cals = Integer.parseInt(monthCount.getSelectedItem()
                            .toString());
                    reShow(cals);
                }
            }
        });
        currentYear.setText("年");
        currentMonth.setText("月");
        year.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int y = calendar.getYear();

                try {
                    y = Integer.parseInt(year.getSelectedItem().toString());
                } catch (Exception ex) {
                    year.setSelectedItem(String.valueOf(calendar.getYear()));
                }

                calendar.setYear(y);
            }

        });

        month.setEditable(true);
        month.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int m;
                try {
                    m = Integer.parseInt(month.getSelectedItem().toString());
                } catch (Exception ex) {
                    month.setSelectedItem(String.valueOf(calendar.getMonth()));
                    return;
                }
                calendar.setMonth(m);
            }
        });

        year.setEditable(true);
        nextYear.setText(">>");
        nextYear.setToolTipText("次の年");
        nextYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.nextYear();
            }

        });
        nextMonth.setText("＞");
        nextMonth.setToolTipText("次の月");
        nextMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.nextMonth();
            }

        });
        thisMonth.setText("■");
        thisMonth.setToolTipText("今月");
        thisMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.thisMonth();
            }

        });
        previeMonth.setText("＜");
        previeMonth.setToolTipText("前の月");
        previeMonth.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.previewMonth();
            }

        });
        previewYear.setText("<<");
        previewYear.setToolTipText("前の年");
        previewYear.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                calendar.previewYear();
            }

        });

        monthCount.setColumns(2);
        comment.setOpaque(true);
        selectCancel.setIconPath(ACConstants.ICON_PATH_CANCEL_24);
        selectCancel.setText("キャンセル(C)");
        selectCancel.setMnemonic('C');
        currentInfomation.add(currentYear, null);
        currentYear.add(year, null);
        year.setColumns(4);
        year.setMaxLength(4);
        year.setCharType(VRCharType.ONLY_DIGIT);

        currentInfomation.add(currentMonth, null);
        currentMonth.add(month, null);
        month.setColumns(2);
        month.setMaxLength(2);
        month.setCharType(VRCharType.ONLY_DIGIT);

        currentChangeButtons.setHgap(0);
        currentChangeButtons.setAutoWrap(false);
        currentChangeButtons.add(showMonthCount, null);
        showMonthCount.add(monthCount, null);
        currentChangeButtons.add(previewYear, null);
        currentChangeButtons.add(previeMonth, null);
        currentChangeButtons.add(thisMonth, null);
        currentChangeButtons.add(nextMonth, null);
        currentChangeButtons.add(nextYear, null);
        contents.add(buttons, VRLayout.SOUTH);
        contents.add(comment, VRLayout.SOUTH);
        this.getContentPane().add(contents, BorderLayout.CENTER);
        contents.add(currentChangeButtons, VRLayout.NORTH);
        contents.add(calendar, VRLayout.CLIENT);
        contents.add(currentInfomation, VRLayout.NORTH);
        buttons.add(selectCancel, VRLayout.EAST);
        buttons.add(selectOK, VRLayout.EAST);

        monthCount.setModel(new DefaultComboBoxModel(new String[] { "1", "2",
                "3", "6", "12" }));
        String[] months = new String[12];
        for (int i = 0; i <= 11; i++) {
            months[i] = String.valueOf(i + 1);
        }
        month.setModel(new DefaultComboBoxModel(months));
        month.setSelectedItem(String.valueOf(calendar.getMonth()));

        String[] years = new String[5];
        Calendar ca = Calendar.getInstance();
        int y = ca.get(Calendar.YEAR);
        for (int i = 0; i <= 4; i++) {
            years[i] = String.valueOf(y + i - 2);
        }
        year.setModel(new DefaultComboBoxModel(years));
        year.setSelectedItem(String.valueOf(calendar.getYear()));

        setOk(false);

    }

    /**
     * 決定ボタンが押されたかを設定します。
     * 
     * @param ok 決定ボタンが押された場合はtrue
     */
    protected void setOk(boolean ok) {
        this.ok = ok;
    }

}
