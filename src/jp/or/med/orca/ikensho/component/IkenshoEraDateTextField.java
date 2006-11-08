package jp.or.med.orca.ikensho.component;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.im.InputSubset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.TreeMap;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import jp.nichicom.ac.ACCommon;
import jp.nichicom.ac.component.ACAgeTextField;
import jp.nichicom.ac.component.ACComboBox;
import jp.nichicom.ac.core.ACFrame;
import jp.nichicom.ac.core.ACFrameEventProcesser;
import jp.nichicom.vr.bind.VRBindPathParser;
import jp.nichicom.vr.bind.VRBindSource;
import jp.nichicom.vr.bind.VRBindable;
import jp.nichicom.vr.bind.event.VRBindEvent;
import jp.nichicom.vr.bind.event.VRBindEventListener;
import jp.nichicom.vr.component.VRTextField;
import jp.nichicom.vr.container.VRLabelContainer;
import jp.nichicom.vr.layout.VRLayout;
import jp.nichicom.vr.text.VRCharType;
import jp.nichicom.vr.text.VRDateFormat;
import jp.nichicom.vr.text.parsers.VRDateParser;
import jp.nichicom.vr.text.parsers.VRDateParserEra;
import jp.nichicom.vr.util.VRArrayList;
import jp.nichicom.vr.util.adapter.VRComboBoxModelAdapter;
import jp.or.med.orca.ikensho.event.IkenshoEraDateTextFieldEvent;
import jp.or.med.orca.ikensho.event.IkenshoEraDateTextFieldListener;
import jp.or.med.orca.ikensho.event.IkenshoModifiedCheckTextFieldEvent;
import jp.or.med.orca.ikensho.event.IkenshoModifiedCheckTextFieldListener;

/** TODO <HEAD_IKENSYO> */
public class IkenshoEraDateTextField extends JPanel implements
        IkenshoModifiedCheckTextFieldListener, VRBindable {
    private static String[] ERA_LIST = new String[] { "" };
    private final String ERA_UNKNOWN = "不詳";

    protected ACComboBox era = new ACComboBox();
    protected IkenshoModifiedCheckTextField year = new IkenshoModifiedCheckTextField();
    protected JLabel yearUnit = new JLabel();
    protected IkenshoModifiedCheckTextField month = new IkenshoModifiedCheckTextField();
    protected JLabel monthUnit = new JLabel();
    protected IkenshoModifiedCheckTextField day = new IkenshoModifiedCheckTextField();
    protected JLabel dayUnit = new JLabel();
    protected ACAgeTextField age = new ACAgeTextField();

    private boolean allowedUnknown;
    private boolean allowedFutureDate;
    private boolean allowedBlank = true;
    private boolean valid;
    private String oldValue;

    // 入力値の状態
    public static final int STATE_EMPTY = 0;
    public static final int STATE_VALID = 1;
    public static final int STATE_ERR = 2;
    public static final int STATE_FUTURE = 3;
    public static final int STATE_PAST = 4;

    // 入力値の状態(内部用)
    private final int FLG_EMPTY = 0;
    private final int FLG_ERA_ERR = 1;
    private final int FLG_YEAR_ERR = 2;
    private final int FLG_MONTH_ERR = 3;
    private final int FLG_DAY_ERR = 4;
    private final int FLG_NO_ERR = 5;
    private final int FLG_UNKNOWN = 6;

    public int dateRangeErr;
    private final int DATE_RANGE_NO_ERR = 0;
    private final int DATE_RANGE_FUTURE_ERR = 1;
    private final int DATE_RANGE_PAST_ERR = 2;

    // 許可する範囲
    private int requestedRange;
    public static final int RNG_ERA = 0;
    public static final int RNG_YEAR = 1;
    public static final int RNG_MONTH = 2;
    public static final int RNG_DAY = 3;

    // 年号の範囲
    private int eraRange = 0;
    // 最小日付
    private Date minimumDate = new Date();

    protected boolean autoApplySource = false;
    protected String bindPath;
    protected ArrayList listeners = new ArrayList();
    protected VRBindSource source;

    /**
     * コンストラクタです。
     */
    public IkenshoEraDateTextField() {
        try {
            jbInit();
            reloadEras();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 元号設定を再読み込みします。
     * 
     * @throws Exception 処理例外
     */
    public void reloadEras() throws Exception {
        ArrayList array = VRDateParser.getEras();
        // VRArrayList vral;

        if (array == null) {
            ERA_LIST = new String[] {};
            // vral = new VRArrayList(Arrays.asList(ERA_LIST));
            minimumDate.setTime(Long.MIN_VALUE);
        } else {
            int end = array.size();
            TreeMap names = new TreeMap();
            for (int i = 0; i < end; i++) {
                VRDateParserEra era = (VRDateParserEra) array.get(i);
                String eraName = era.getAbbreviation(3);
                if ((eraName != null) && (!"".equals(eraName))) {
                    names.put(new Long(era.getBegin().getTimeInMillis()),
                            eraName);
                }
            }
            int size = names.size() - eraRange;
            ERA_LIST = new String[size + 1];
            ERA_LIST[0] = "";
            System.arraycopy(names.values().toArray(), eraRange, ERA_LIST, 1,
                    size);

            // 最小日付取得
            Iterator it = names.keySet().iterator();
            for (int i = 0; i < eraRange; i++) {
                it.next();
            }
            minimumDate.setTime(((Long) it.next()).longValue());
        }

        // 元号コンボに設定
        // vral = new VRArrayList(Arrays.asList(ERA_LIST));
        // era.setModel(new VRComboBoxModelAdapter(vral));

        // 「不詳」のため、再設定
        addEraUnknown();
    }

    private void jbInit() throws Exception {
        VRLayout datePanelLayout = new VRLayout();
        datePanelLayout.setVgap(0);
        datePanelLayout.setHgap(0);
        datePanelLayout.setAutoWrap(false);

        this.setLayout(datePanelLayout);
        this.setOpaque(false);
        this.add(era, VRLayout.FLOW);
        this.add(year, VRLayout.FLOW);
        this.add(yearUnit, VRLayout.FLOW);
        this.add(month, VRLayout.FLOW);
        this.add(monthUnit, VRLayout.FLOW);
        this.add(day, VRLayout.FLOW);
        this.add(dayUnit, VRLayout.FLOW);
        this.add(age, VRLayout.FLOW);

        yearUnit.setText("年");
        monthUnit.setText("月");
        dayUnit.setText("日");

        era.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent e) {
                checkEraItemState();
            }
        });
        era.setEditable(false);
        setRequestedRange(RNG_DAY);
        this.setAllowedUnknown(false);
        this.setAllowedFutureDate(false);

        year.setText("");
        year.addModiliedCheckTextFieldListener(this);
        year.setColumns(2);
        year.setMaxLength(2);
        year.setHorizontalAlignment(VRTextField.RIGHT);
        year.setIMEMode(InputSubset.LATIN_DIGITS);
        year.setCharType(VRCharType.ONLY_DIGIT);

        month.setText("");
        month.addModiliedCheckTextFieldListener(this);
        month.setColumns(2);
        month.setMaxLength(2);
        month.setHorizontalAlignment(VRTextField.RIGHT);
        month.setIMEMode(InputSubset.LATIN_DIGITS);
        month.setCharType(VRCharType.ONLY_DIGIT);

        day.setText("");
        day.addModiliedCheckTextFieldListener(this);
        day.setColumns(2);
        day.setMaxLength(2);
        day.setHorizontalAlignment(VRTextField.RIGHT);
        day.setIMEMode(InputSubset.LATIN_DIGITS);
        day.setCharType(VRCharType.ONLY_DIGIT);

        this.clear();
    }

    /**
     * 設定値を生年月日として年齢を計算する
     * 
     * @return 計算結果(年齢)
     */
    public String calcAge() {
        Date date = getDate();
        age.setAge("");

        // 日付が正しい設定なら年齢を設定
        if (date != null) {
            // 日付範囲エラーがなければ年齢を設定
            if (dateRangeErr == DATE_RANGE_NO_ERR) {
                age.setBaseDate(null);
                age.setBirthday(date);
                return age.getAge();
            }
        }
        return "";
    }

    /**
     * 年号を取得
     * 
     * @return 年号
     */
    public String getEra() {
        if(era.getSelectedItem()==null){
            return "";
        }
        return String.valueOf(era.getSelectedItem());
    }

    /**
     * 年号を設定
     * 
     * @param era 年号
     */
    public void setEra(String era) {
        int loopCnt = 0;
        if (isAllowedUnknown()) { // 不完全日付
            loopCnt = this.era.getItemCount() - 1; // 最後の"不詳"分を減らす
        } else { // 完全日付
            loopCnt = this.era.getItemCount();
        }

        // 突合
        for (int i = 0; i < loopCnt; i++) {
            if (era.equals(ERA_LIST[i])) {
                this.era.setSelectedIndex(i);
            }
        }
    }

    /**
     * 年を取得
     * 
     * @return 年
     */
    public String getYear() {
        String year = this.year.getText();
        if (year == null) {
            year = "";
        }
        return year.trim();
    }

    /**
     * 年の文字列を設定する
     * 
     * @param year String
     */
    public void setYear(String year) {
        this.year.setText(year);
    }

    /**
     * 月を取得
     * 
     * @return 月
     */
    public String getMonth() {
        String month = this.month.getText();
        if (month == null) {
            month = "";
        }
        return month.trim();
    }

    /**
     * 月の文字列を設定する
     * 
     * @param month 月
     */
    public void setMonth(String month) {

        this.month.setText(month);
    }

    /**
     * 日を取得
     * 
     * @return 日
     */
    public String getDay() {
        String day = this.day.getText();
        if (day == null) {
            day = "";
        }
        return day.trim();
    }

    /**
     * 日の文字列を設定する
     * 
     * @param day 日
     */
    public void setDay(String day) {
        this.day.setText(day);
    }

    /**
     * 年齢を取得
     * 
     * @return 年齢
     */
    public String getAge() {
        return age.getAge();
    }

    /**
     * 年齢の文字列を設定する
     * 
     * @param age 年齢
     */
    public void setAge(String age) {
        this.age.setAge(age);
    }

    /**
     * 日付をDate型で返します。
     * 
     * @return 日付
     */
    public Date getDate() {
        // 入力エラーチェック
        if (era.getSelectedIndex() == 0) {
            return null;
        }
        String y = getYear();
        if ("".equals(y)) {
            return null;
        }
        String m = getMonth();
        if ("".equals(m)) {
            m = "1";
            // return null;
        }
        String d = getDay();
        if ("".equals(d)) {
            d = "1";
            // return null;
        }

        // 処理
        String date = String.valueOf(era.getSelectedItem()) + y + "年" + m + "月"
                + d + "日";
        try {
            return VRDateParser.parse(date);
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * 日付を設定
     * 
     * @param date 日付
     */
    public void setDate(Date date) {
        if (date == null) {
            if (isAllowedUnknown()) {
                // "不詳"を持っている場合、"不詳"を設定する
                setDateUnknown();
            } else {
                // "不詳"を持っていない場合、クリアする
                clear();
            }
        } else {
            try {
                // 元号を取得し、comboに設定
                String era = VRDateParser.format(date, "ggg");
                for (int i = 0; i < this.era.getItemCount(); i++) {
                    if (era.equals(String.valueOf(this.era.getItemAt(i)))) {
                        this.era.setSelectedIndex(i);
                        break;
                    }
                }

                // 年を取得し、TextFieldに設定
                this.setYear(VRDateParser.format(date, "e"));

                // 月を取得し、TextFieldに設定
                this.setMonth(VRDateParser.format(date, "M"));

                // 日を取得し、TextFieldに設定
                this.setDay(VRDateParser.format(date, "d"));

                // 年齢を計算し、TextFieldに設定
                if (age.isVisible()) {
                    age.setBaseDate(null);
                    age.setBirthday(date);
                }
            } catch (Exception ex) {
                ACCommon.getInstance().showExceptionMessage(ex);
            }
        }
    }

    /**
     * 未来の日付を許可するかどうかを設定します。
     * 
     * @param allowedFutureDate 未来の日付を許可するかどうか
     */
    public void setAllowedFutureDate(boolean allowedFutureDate) {
        this.allowedFutureDate = allowedFutureDate;
    }

    /**
     * 日付を文字列で返します。
     * 
     * @return 文字列日付
     */
    public String getText() {
        String day = null;
        String month = null;
        String year = null;
        String era;
        // 元号
        if (this.era.getSelectedIndex() == 0) {
            era = "00";
            if (isYearVisible()) {
                year = "00年";
                if (isMonthVisible()) {
                    month = "00月";
                    if (isDayVisible()) {
                        day = "00日";
                    }
                }
            }
        } else if (isDateUnknown()) {
            era = "不詳";
            if (isYearVisible()) {
                year = "00年";
                if (isMonthVisible()) {
                    month = "00月";
                    if (isDayVisible()) {
                        day = "00日";
                    }
                }
            }
        } else {
            era = String.valueOf(this.era.getSelectedItem());
        }
        // 年
        if (year == null) {
            if (!isYearVisible()) {
                year = "";
            } else {
                year = getYear();
                if ("".equals(year)) {
                    year = "00";
                    if (isMonthVisible()) {
                        month = "00月";
                        if (isDayVisible()) {
                            day = "00日";
                        }
                    }
                } else if (year.length() < 2) {
                    year = "0" + year;
                }
                year = year + "年";
            }
        }
        // 月
        if (month == null) {
            if (!isMonthVisible()) {
                month = "";
            } else {
                month = getMonth();
                if ("".equals(month)) {
                    month = "00";
                    if (isDayVisible()) {
                        day = "00日";
                    }
                } else if (month.length() < 2) {
                    month = "0" + month;
                }
                month = month + "月";
            }
        }
        // 日
        if (day == null) {
            if (!isDayVisible()) {
                day = "";
            } else {
                day = getDay();
                if ("".equals(day)) {
                    day = "00";
                } else if (day.length() < 2) {
                    day = "0" + day;
                }
                day = day + "日";
            }
        }

        return era + year + month + day;
    }

    /**
     * 日付を文字列で設定します。
     * 
     * @param text 文字列日付
     */
    public void setText(String text) {
        int length = text.length();
        if ((length < 4) || (length > 11)) {
            clear();
            return;
        }
        if (text.indexOf("0000年") >= 0) {
            clear();
            return;
        }
        if (text.indexOf("不詳") >= 0) {
            if (isAllowedUnknown()) {
                setDateUnknown();
            } else {
                clear();
            }
            return;
        }
        char[] chars = text.toCharArray();
        int emptyYearPos = text.indexOf("00年");
        if (emptyYearPos >= 0) {
            chars[emptyYearPos + 1] = '1';
        } else if (text.indexOf("年") < 0) {
            emptyYearPos = 0;
        }

        int emptyMonthPos = text.indexOf("00月");
        if (emptyMonthPos >= 0) {
            chars[emptyMonthPos + 1] = '1';
        } else if (text.indexOf("月") < 0) {
            emptyMonthPos = 0;
        }

        int emptyDayPos = text.indexOf("00日");
        if (emptyDayPos >= 0) {
            chars[emptyDayPos + 1] = '1';
        } else if (text.indexOf("日") < 0) {
            emptyDayPos = 0;
        }

        try {
            setDate(VRDateParser.parse(new String(chars)));
        } catch (Exception ex) {
            clear();
            return;
        }

        if (emptyYearPos >= 0) {
            setYear("");
        }
        if (emptyMonthPos >= 0) {
            setMonth("");
        }
        if (emptyDayPos >= 0) {
            setDay("");
        }
        changedDateValue();
    }

    /**
     * 年齢項目の表示状態を取得
     * 
     * @return true:表示, false:非表示
     */
    public boolean isAgeVisible() {
        return age.isVisible();
    }

    /**
     * 年齢項目の表示/非表示を設定します。
     * 
     * @param ageVisible true:表示, false:非表示
     */
    public void setAgeVisible(boolean ageVisible) {
        age.setVisible(ageVisible);
    }

    /**
     * 年項目の表示状態を取得します。
     * 
     * @return true:表示, false:非表示
     */
    public boolean isYearVisible() {
        return year.isVisible() && year.isVisible();
    }

    /**
     * 年項目の表示/非表示を設定します。
     * 
     * @param yearVisible true:表示, false:非表示
     */
    public void setYearVisible(boolean yearVisible) {
        year.setVisible(yearVisible);
        yearUnit.setVisible(yearVisible);
    }

    /**
     * 月項目の表示状態を取得します。
     * 
     * @return true:表示, false:非表示
     */
    public boolean isMonthVisible() {
        return month.isVisible() && monthUnit.isVisible();
    }

    /**
     * 月項目の表示/非表示を設定します。
     * 
     * @param monthVisible true:表示, false:非表示
     */
    public void setMonthVisible(boolean monthVisible) {
        month.setVisible(monthVisible);
        monthUnit.setVisible(monthVisible);
    }

    /**
     * 日付項目の表示状態を取得します。
     * 
     * @return true:表示, false:非表示
     */
    public boolean isDayVisible() {
        return day.isVisible() && dayUnit.isVisible();
    }

    /**
     * 日付項目の表示/非表示を設定します。
     * 
     * @param dayVisible true:表示, false:非表示
     */
    public void setDayVisible(boolean dayVisible) {
        day.setVisible(dayVisible);
        dayUnit.setVisible(dayVisible);
    }

    /**
     * Enabledを取得
     * 
     * @return boolean
     */
    public boolean isEnabled() {
        return era.isEnabled();
    }

    /**
     * Enabledを設定
     * 
     * @param enabled boolean
     */
    public void setEnabled(boolean enabled) {
        era.setEnabled(enabled);
        year.setEnabled(enabled);
        yearUnit.setEnabled(enabled);
        month.setEnabled(enabled);
        monthUnit.setEnabled(enabled);
        day.setEnabled(enabled);
        dayUnit.setEnabled(enabled);
        age.setEnabled(enabled);
    }

    /**
     * TextBoxのEditableを取得
     * 
     * @return boolean
     */
    public boolean isEditable() {
        return era.isEditable();
    }

    /**
     * Editableを設定
     * 
     * @param editable Editable
     */
    public void setEditable(boolean editable) {
        era.setEditable(editable);
        era.setEnabled(editable);
        year.setEditable(editable);
        month.setEditable(editable);
        day.setEditable(editable);
    }

    public boolean isChildrenFocusable() {
        return era.isFocusable() && year.isFocusable() && month.isFocusable()
                && day.isFocusable();
    }

    public void setChildrenFocusable(boolean focusable) {
        era.setFocusable(focusable);
        year.setFocusable(focusable);
        month.setFocusable(focusable);
        day.setFocusable(focusable);
    }

    /**
     * 「不明」項目を持っているかを取得します。
     * 
     * @return true:持っている, false:持っていない
     */
    public boolean isAllowedUnknown() {
        return allowedUnknown;
    }

    /**
     * 「不明」項目を持たせるかどうかを設定します。
     * 
     * @param allowedUnknown true:持たせる, false:持たせない
     */
    public void setAllowedUnknown(boolean allowedUnknown) {
        this.allowedUnknown = allowedUnknown;
        addEraUnknown();
    }

    /**
     * 未選択を許す（エラーとみなさない）かを取得します。
     * 
     * @return true:未選択を許す, false:未選択を許さない
     */
    public boolean isAllowedBlank() {
        return allowedBlank;
    }

    /**
     * 未選択を許す（エラーとみなさない）かどうかを設定します。
     * 
     * @param allowedBlank true:未選択を許す, false:未選択を許さない
     */
    public void setAllowedBlank(boolean allowedBlank) {
        this.allowedBlank = allowedBlank;
    }

    /**
     * コンボに「不詳」を追加します。
     */
    public void addEraUnknown() {
        VRArrayList ar = new VRArrayList(Arrays.asList(ERA_LIST));
        if (isAllowedUnknown()) {
            ar.add(ERA_UNKNOWN);
        }
        era.setModel(new VRComboBoxModelAdapter(ar));
    }

    /**
     * 未来の日付を許可するかどうかの設定を取得します。
     * 
     * @return true:許可する, false:許可しない
     */
    public boolean isAllowedFutureDate() {
        return allowedFutureDate;
    }

    /**
     * 許可されている入力範囲を取得します。
     * 
     * @return int
     */
    public int getRequestedRange() {
        return this.requestedRange;
    }

    /**
     * 未入力を許可する範囲を設定します。<br />
     * RNG_ERA : 年号は必須選択となります。未入力時はチェックしませんので、実質的にはすべて許容します。<br />
     * RNG_YEAR : 年号、年は必須入力となります。（月の未入力は許可します。）<br />
     * RNG_MONTH : 年号、年、月は必須入力となります。（日の未入力は許可します。）<br />
     * RNG_DAY : 年号、年、月、日は必須入力となります。（すべての項目が必須となります。）<br />
     * 
     * @param requestedRange int
     */
    public void setRequestedRange(int requestedRange) {
        this.requestedRange = requestedRange;
        doValidCheck();
    }

    /**
     * 表示する年号の範囲を取得します。
     * 
     * @return int
     */
    public int getEraRange() {
        return eraRange;
    }

    /**
     * calendar.xmlで設定されている年号のうち、何番目以降を元号コンボに設定するのかを設定します。<br/>
     * 例えば、calendar.xmlに設定されているのが「明治」「大正」「昭和」「平成」であり、 設定したい項目が「昭和」「平成」のみである場合、
     * eraRange = 2とすることで、インデックス2番である「昭和」以降が元号コンボに設定されます。
     * 
     * @param eraRange 元号のインデックス
     */
    public void setEraRange(int eraRange) {
        this.eraRange = eraRange;

        try {
            reloadEras();
        } catch (Exception ex) {
            ACCommon.getInstance().showExceptionMessage(ex);
        }
    }

    /**
     * "不詳"に設定します。
     */
    public void setDateUnknown() {
        if (isAllowedUnknown()) {
            this.era.setSelectedIndex(era.getItemCount() - 1);
            setYear("");
            setMonth("");
            setDay("");
            setAge("");
        }
    }

    /**
     * 不詳を選択しているかを返します。
     * 
     * @return 不詳を選択しているか
     */
    public boolean isDateUnknown() {
        if (isAllowedUnknown()) {
            if (this.era.getSelectedIndex() == era.getItemCount() - 1) {
                return true;
            }
        }
        return false;
    }

    /**
     * @deprecated 推奨しません。setRequestedRangeを使ってください。
     * @param full boolean
     */
    public void setFull(boolean full) {
        if (full) {
            setRequestedRange(RNG_DAY);
        } else {
            setRequestedRange(RNG_YEAR);
        }
    }

    /**
     * 値の有効/無効の取得<br />
     * 入力されている日付の有効性をチェックする。
     * 
     * @return true:有効, false:無効
     */
    public int getInputStatus() {
        int status = checkDateValue();
        if (status == FLG_EMPTY) {
            return STATE_EMPTY;
        } else {
            boolean valid = checkValid(status);
            if (valid) {
                // 日付範囲エラー
                switch (dateRangeErr) {
                case DATE_RANGE_FUTURE_ERR:
                    return STATE_FUTURE;
                case DATE_RANGE_PAST_ERR:
                    return STATE_PAST;
                }
                return STATE_VALID;
            } else {
                return STATE_ERR;
            }
        }
    }

    /**
     * 元号コンボ変更時のイベント
     */
    public void checkEraItemState() {
        // "不詳"が選択されているかを判定
        boolean isUnknownDate;
        if (era.getSelectedItem().equals(ERA_UNKNOWN)) {
            isUnknownDate = true;
            year.setEnabled(false);
            month.setEnabled(false);
            day.setEnabled(false);
        } else {
            isUnknownDate = false;
        }

        // Labelと年齢部のEnabledを設定
        yearUnit.setEnabled(!isUnknownDate);
        monthUnit.setEnabled(!isUnknownDate);
        dayUnit.setEnabled(!isUnknownDate);
        age.setEnabled(!isUnknownDate);

        // TextFieldのEnabledを設定
        if (!isUnknownDate) {
            changedDateValue();
        } else {
            oldValue = null;
            setParentColor(true);
        }
    }

    public void textChanged(IkenshoModifiedCheckTextFieldEvent event) {
        changedDateValue();
    }

    /**
     * 日付形式の間違い箇所によってEnabledを変更し、イベントを発行する
     */
    private void changedDateValue() {
        // 更新チェック
        String newValue = era.getSelectedItem() + this.getYear() + "年"
                + this.getMonth() + "月" + this.getDay() + "日";
        if (newValue.equals(oldValue)) {
            return;
        }
        oldValue = newValue;

        // 状態判定
        int state = checkDateValue();

        // Enabled設定
        setComponentEnabled(state);

        // 完全日付なら年齢計算
        setAge("");
        if (state == FLG_NO_ERR) {
            if (getRequestedRange() == RNG_DAY) {
                calcAge();
            }
        }

        // 入力の有効・無効の判定
        boolean valid = checkValid(state);

        // 親VRLabelContainerの色変更
        setParentColor(valid);

        // イベント発火
        fireValueChanged(new IkenshoEraDateTextFieldEvent(this, valid, state));
    }

    public void doValidCheck() {
        // 状態判定
        int state = checkDateValue();

        // 入力の有効・無効の判定
        boolean valid = checkValid(state);

        // 親VRLabelContainerの色変更
        setParentColor(valid);
    }

    /**
     * 元号・年・月・日の組み合わせの妥当性の検証
     * 
     * @return int
     */
    private int checkDateValue() {
        String str = "";
        VRDateFormat vrdf = null;
        dateRangeErr = DATE_RANGE_NO_ERR;

        // 年号が設定されていない場合
        if (era.getSelectedItem() == null) {

            return FLG_EMPTY;
        }
        if (era.getSelectedIndex() == 0) {
            return FLG_EMPTY;
        }

        // 不詳
        if (era.getSelectedItem().equals(ERA_UNKNOWN)) {
            return FLG_UNKNOWN;
        }

        // 間違いなし
        try {
            str = era.getSelectedItem() + this.getYear() + "年"
                    + this.getMonth() + "月" + this.getDay() + "日";
            vrdf = new VRDateFormat("ggge年M月d日");
            Date val = vrdf.parse(str);
            // 日付範囲エラーチェック
            if (isFutureDateErr(val)) { // 未来日付
                dateRangeErr = DATE_RANGE_FUTURE_ERR;
            } else if (getMinimumDate().after(val)) { // 過去日付
                dateRangeErr = DATE_RANGE_PAST_ERR;
            }

            return FLG_NO_ERR;
        } catch (Exception ex) {
        }

        // 日が違う
        try {
            str = era.getSelectedItem() + this.getYear() + "年"
                    + this.getMonth() + "月";
            vrdf = new VRDateFormat("ggge年M月");
            Date val = vrdf.parse(str);
            // 日付範囲エラーチェック
            if (isFutureDateErr(val)) { // 未来日付
                dateRangeErr = DATE_RANGE_FUTURE_ERR;
            } else if (getMinimumDate().after(val)) { // 過去日付
                dateRangeErr = DATE_RANGE_PAST_ERR;
            }
            return FLG_DAY_ERR;
        } catch (Exception ex) {
        }

        // 月が違う
        try {
            str = era.getSelectedItem() + this.getYear() + "年";
            vrdf = new VRDateFormat("ggge年");
            Date val = vrdf.parse(str);
            // 日付範囲エラーチェック
            if (isFutureDateErr(val)) { // 未来日付
                dateRangeErr = DATE_RANGE_FUTURE_ERR;
            } else if (getMinimumDate().after(val)) { // 過去日付
                dateRangeErr = DATE_RANGE_PAST_ERR;
            }
            return FLG_MONTH_ERR;
        } catch (Exception ex) {
        }

        // 年が違う
        try {
            if (era.getSelectedIndex() >= 0) {
                return FLG_YEAR_ERR;
            }
        } catch (Exception ex) {
        }

        // 元号が違う
        return FLG_ERA_ERR;
    }

    /**
     * 未来日付エラーであるかどうかを判定します。
     * 
     * @param val 比較対象日付
     * @return true:エラーあり, false:エラーなし
     */
    private boolean isFutureDateErr(Date val) {
        // 未来日付を許可しているのなら、未来日付エラーではない
        if (isAllowedFutureDate()) {
            return false;
        }

        // 未来日付チェック用の本日日付+1
        Calendar nowCal = Calendar.getInstance();
        nowCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH),
                nowCal.get(Calendar.DAY_OF_MONTH) +1, 0, 0, 0);
        nowCal.add(Calendar.SECOND, -1);
        Date now = nowCal.getTime();

        // 比較
        if (now.before(val)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 最小日付を取得します。
     * 
     * @return 最小日付
     */
    public Date getMinimumDate() {
        return minimumDate;
    }

    /**
     * 最小日付を設定します。
     * 
     * @param minimumDate 最小日付
     */
    public void setMinimumDate(Date minimumDate) {
        this.minimumDate = minimumDate;
    }

    /**
     * 状態に応じて、設定されている日付が有効かどうかを判定する
     * 
     * @param state 状態
     * @return true:有効, false:無効
     */
    private boolean checkValid(int state) {
        switch (state) {
        case FLG_NO_ERR: // エラーなし
            valid = true;
            break;

        case FLG_DAY_ERR: // 日がおかしい
            valid = false;
            if (requestedRange < RNG_DAY) {
                if (ACCommon.getInstance().isNullText(getDay())) {
                    valid = true;
                }
            }
            break;

        case FLG_MONTH_ERR: // 月がおかしい
            valid = false;
            if (requestedRange < RNG_MONTH) {
                if (ACCommon.getInstance().isNullText(getMonth())) {
                    valid = true;
                }
            }
            break;

        case FLG_YEAR_ERR: // 年がおかしい
            valid = false;
            if (requestedRange < RNG_YEAR) {
                if (ACCommon.getInstance().isNullText(getYear())) {
                    valid = true;
                }
            }
            break;

        case FLG_EMPTY: // 未入力
        case FLG_UNKNOWN: // 「不詳」
            valid = isAllowedBlank();
            // valid = true;
            break;
        }
        return valid;
    }

    /**
     * 親VRLabelContainerの色変更
     * 
     * @param valid 日付は有効であるか
     */
    public void setParentColor(boolean valid) {
        if (!(getParent() instanceof Container)) {
            return;
        }
        Container tmp = getParent();
        VRLabelContainer parent = null;
        if (tmp instanceof VRLabelContainer) {
            parent = (VRLabelContainer) getParent();
        } else {
            parent = getParentVRLabelContainer(tmp);
        }

        if (parent != null) {
            // 日付範囲エラー
            valid &= (dateRangeErr == DATE_RANGE_NO_ERR);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            if (!valid) {
                // エラー色
                parent.setLabelFilled(true);
                if (processer != null) {
                    parent.setForeground(processer
                            .getContainerErrorForeground());
                    parent.setBackground(processer
                            .getContainerErrorBackground());
                }
            } else {
                boolean isEraWarning = false;
                try {
                    if (!getEra().equals(VRDateParser.format(getDate(), "ggg"))) {
                        isEraWarning = true;
                    }
                } catch (Exception ex) {

                }

                if (processer != null) {
                    Color fore;
                    Color back;
                    if (isEraWarning) {
                        // 警告色
                        fore = processer.getContainerWarningForeground();
                        back = processer.getContainerWarningBackground();
                        parent.setLabelFilled(true);
                    } else {
                        // デフォルト色
                        fore = processer.getContainerDefaultForeground();
                        back = processer.getContainerDefaultBackground();
                        parent.setLabelFilled(false);
                    }
                    parent.setForeground(fore);
                    parent.setBackground(back);
                }
            }
        }
    }

    /**
     * 年月日、個々のコンポーネントのEnabledを設定する
     * 
     * @param state 状態
     */
    private void setComponentEnabled(int state) {
        day.setEnabled(true);
        month.setEnabled(true);
        year.setEnabled(true);

        switch (state) {
        case FLG_NO_ERR:
            break;

        case FLG_DAY_ERR:
            break;

        case FLG_MONTH_ERR:
            day.setEnabled(false);
            break;

        case FLG_YEAR_ERR:
            day.setEnabled(false);
            month.setEnabled(false);
            break;

        case FLG_ERA_ERR:
            day.setEnabled(false);
            month.setEnabled(false);
            year.setEnabled(false);
            break;

        case FLG_EMPTY:
        case FLG_UNKNOWN:
            day.setEnabled(false);
            month.setEnabled(false);
            year.setEnabled(false);
            break;
        }
    }

    /**
     * 自身を格納しているコンテナを遡り、最初に現れたVRLabelContainerを返します。
     * 
     * @param child Container
     * @return VRLabelContainer
     */
    private VRLabelContainer getParentVRLabelContainer(Container child) {
        if (!(child.getParent() instanceof Container)) {
            return null;
        }
        Container parent = child.getParent();
        if (parent instanceof VRLabelContainer) {
            return (VRLabelContainer) parent;
        } else {
            return getParentVRLabelContainer(parent);
        }
    }

    /**
     * 値をクリアする
     */
    public void clear() {
        era.setSelectedItem("");
        if (era.getItemCount() > 0) {
            era.setSelectedIndex(0);
        }
        setYear("");
        setMonth("");
        setDay("");
        setAge("");
    }

    transient Vector datePanelListeners = new Vector();

    public synchronized void addDatePanelListener(
            IkenshoEraDateTextFieldListener l) {
        datePanelListeners.add(l);
    }

    public synchronized void removeDatePanelListener(
            IkenshoEraDateTextFieldListener l) {
        datePanelListeners.remove(l);
    }

    /**
     * 最初の子コントロールにフォーカスを移します。
     */
    public void requestChildFocus() {
        era.requestFocus();
    }

    /**
     * 元号・年・月・日のいずれかが変更された場合に発生
     * 
     * @param e DatePanelEvent
     */
    protected void fireValueChanged(IkenshoEraDateTextFieldEvent e) {
        if (datePanelListeners != null) {
            Vector listeners = datePanelListeners;
            int count = listeners.size();
            for (int i = 0; i < count; i++) {
                ((IkenshoEraDateTextFieldListener) listeners.elementAt(i))
                        .valueChanged(e);
            }
        }
    }

    public void addBindEventListener(VRBindEventListener listener) {
        listeners.add(listener);
    }

    public String getBindPath() {
        return bindPath;
    }

    public VRBindSource getSource() {
        return source;
    }

    public boolean isAutoApplySource() {
        return this.autoApplySource;
    }

    public void removeBindEventListener(VRBindEventListener listener) {
        listeners.remove(listener);
    }

    public void setAutoApplySource(boolean autoApplySource) {
        this.autoApplySource = autoApplySource;
    }

    public void setBindPath(String bindPath) {
        this.bindPath = bindPath;
    }

    public void setSource(VRBindSource source) {
        this.source = source;
    }

    protected void fireApplySource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ((VRBindEventListener) it.next()).applySource(e);
        }
    }

    protected void fireBindSource() {
        Iterator it = listeners.iterator();
        VRBindEvent e = new VRBindEvent(this);
        while (it.hasNext()) {
            ((VRBindEventListener) it.next()).bindSource(e);
        }
    }

    /**
     * Date型しか許容しないかを返します。
     * 
     * @return Date型しか許容しないか
     */
    protected boolean isMustDateType() {
        return (getRequestedRange() == RNG_DAY) && (!isAllowedUnknown());
    }

    public Object createSource() {

        if (isMustDateType()) {
            return new Object();
        } else {
            return "0000年00月00日";
        }
    }

    public void bindSource() throws ParseException {
        // nullチェック
        Object obj = VRBindPathParser.get(getBindPath(), source);
        if (obj == null) {
            this.clear();
            return;
        }
        try {
            if (obj instanceof String) {
                if (isMustDateType()) {
                    obj = VRDateParser.parse((String) obj);
                    setDate((Date) obj);
                } else {
                    setText((String) obj);
                }
            } else if (obj instanceof Date) {
                setDate((Date) obj);
            } else if (obj instanceof Calendar) {
                setDate(((Calendar) obj).getTime());
            }

            changedDateValue();
            fireBindSource();
        } catch (Exception ex) {
            ACCommon.getInstance().showExceptionMessage(ex);
        }
    }

    public void applySource() throws ParseException {
        Object obj;

        if (isMustDateType()) {
            obj = getDate();
        } else {
            obj = getText();
        }

        if (VRBindPathParser.set(getBindPath(), getSource(), obj)) {
            fireApplySource();
        }
    }

    /**
     * 「日」ラベルを返します。
     * 
     * @return 「日」ラベル
     */
    public JLabel getDayUnit() {
        return dayUnit;
    }
    
    /**
     * 不詳選択時の状態に設定します。
     */
    public void setEraUnknownState(){
        year.setEnabled(false);
        month.setEnabled(false);
        day.setEnabled(false);
        yearUnit.setEnabled(false);
        monthUnit.setEnabled(false);
        dayUnit.setEnabled(false);
        age.setEnabled(false);
    }
    
    /**
     * コンポーネントの状態に応じてEnableを変更します。
     */
    public void checkState(){
        // 状態判定
        int state = checkDateValue();
        // Enabled設定
        setComponentEnabled(state);
    }
    
}
