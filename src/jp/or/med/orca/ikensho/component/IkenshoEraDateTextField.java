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
    private final String ERA_UNKNOWN = "�s��";

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

    // ���͒l�̏��
    public static final int STATE_EMPTY = 0;
    public static final int STATE_VALID = 1;
    public static final int STATE_ERR = 2;
    public static final int STATE_FUTURE = 3;
    public static final int STATE_PAST = 4;

    // ���͒l�̏��(�����p)
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

    // ������͈�
    private int requestedRange;
    public static final int RNG_ERA = 0;
    public static final int RNG_YEAR = 1;
    public static final int RNG_MONTH = 2;
    public static final int RNG_DAY = 3;

    // �N���͈̔�
    private int eraRange = 0;
    // �ŏ����t
    private Date minimumDate = new Date();

    protected boolean autoApplySource = false;
    protected String bindPath;
    protected ArrayList listeners = new ArrayList();
    protected VRBindSource source;

    /**
     * �R���X�g���N�^�ł��B
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
     * �����ݒ���ēǂݍ��݂��܂��B
     * 
     * @throws Exception ������O
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

            // �ŏ����t�擾
            Iterator it = names.keySet().iterator();
            for (int i = 0; i < eraRange; i++) {
                it.next();
            }
            minimumDate.setTime(((Long) it.next()).longValue());
        }

        // �����R���{�ɐݒ�
        // vral = new VRArrayList(Arrays.asList(ERA_LIST));
        // era.setModel(new VRComboBoxModelAdapter(vral));

        // �u�s�ځv�̂��߁A�Đݒ�
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

        yearUnit.setText("�N");
        monthUnit.setText("��");
        dayUnit.setText("��");

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
     * �ݒ�l�𐶔N�����Ƃ��ĔN����v�Z����
     * 
     * @return �v�Z����(�N��)
     */
    public String calcAge() {
        Date date = getDate();
        age.setAge("");

        // ���t���������ݒ�Ȃ�N���ݒ�
        if (date != null) {
            // ���t�͈̓G���[���Ȃ���ΔN���ݒ�
            if (dateRangeErr == DATE_RANGE_NO_ERR) {
                age.setBaseDate(null);
                age.setBirthday(date);
                return age.getAge();
            }
        }
        return "";
    }

    /**
     * �N�����擾
     * 
     * @return �N��
     */
    public String getEra() {
        if(era.getSelectedItem()==null){
            return "";
        }
        return String.valueOf(era.getSelectedItem());
    }

    /**
     * �N����ݒ�
     * 
     * @param era �N��
     */
    public void setEra(String era) {
        int loopCnt = 0;
        if (isAllowedUnknown()) { // �s���S���t
            loopCnt = this.era.getItemCount() - 1; // �Ō��"�s��"�������炷
        } else { // ���S���t
            loopCnt = this.era.getItemCount();
        }

        // �ˍ�
        for (int i = 0; i < loopCnt; i++) {
            if (era.equals(ERA_LIST[i])) {
                this.era.setSelectedIndex(i);
            }
        }
    }

    /**
     * �N���擾
     * 
     * @return �N
     */
    public String getYear() {
        String year = this.year.getText();
        if (year == null) {
            year = "";
        }
        return year.trim();
    }

    /**
     * �N�̕������ݒ肷��
     * 
     * @param year String
     */
    public void setYear(String year) {
        this.year.setText(year);
    }

    /**
     * �����擾
     * 
     * @return ��
     */
    public String getMonth() {
        String month = this.month.getText();
        if (month == null) {
            month = "";
        }
        return month.trim();
    }

    /**
     * ���̕������ݒ肷��
     * 
     * @param month ��
     */
    public void setMonth(String month) {

        this.month.setText(month);
    }

    /**
     * �����擾
     * 
     * @return ��
     */
    public String getDay() {
        String day = this.day.getText();
        if (day == null) {
            day = "";
        }
        return day.trim();
    }

    /**
     * ���̕������ݒ肷��
     * 
     * @param day ��
     */
    public void setDay(String day) {
        this.day.setText(day);
    }

    /**
     * �N����擾
     * 
     * @return �N��
     */
    public String getAge() {
        return age.getAge();
    }

    /**
     * �N��̕������ݒ肷��
     * 
     * @param age �N��
     */
    public void setAge(String age) {
        this.age.setAge(age);
    }

    /**
     * ���t��Date�^�ŕԂ��܂��B
     * 
     * @return ���t
     */
    public Date getDate() {
        // ���̓G���[�`�F�b�N
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

        // ����
        String date = String.valueOf(era.getSelectedItem()) + y + "�N" + m + "��"
                + d + "��";
        try {
            return VRDateParser.parse(date);
        } catch (Exception ex) {
        }

        return null;
    }

    /**
     * ���t��ݒ�
     * 
     * @param date ���t
     */
    public void setDate(Date date) {
        if (date == null) {
            if (isAllowedUnknown()) {
                // "�s��"�������Ă���ꍇ�A"�s��"��ݒ肷��
                setDateUnknown();
            } else {
                // "�s��"�������Ă��Ȃ��ꍇ�A�N���A����
                clear();
            }
        } else {
            try {
                // �������擾���Acombo�ɐݒ�
                String era = VRDateParser.format(date, "ggg");
                for (int i = 0; i < this.era.getItemCount(); i++) {
                    if (era.equals(String.valueOf(this.era.getItemAt(i)))) {
                        this.era.setSelectedIndex(i);
                        break;
                    }
                }

                // �N���擾���ATextField�ɐݒ�
                this.setYear(VRDateParser.format(date, "e"));

                // �����擾���ATextField�ɐݒ�
                this.setMonth(VRDateParser.format(date, "M"));

                // �����擾���ATextField�ɐݒ�
                this.setDay(VRDateParser.format(date, "d"));

                // �N����v�Z���ATextField�ɐݒ�
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
     * �����̓��t�������邩�ǂ�����ݒ肵�܂��B
     * 
     * @param allowedFutureDate �����̓��t�������邩�ǂ���
     */
    public void setAllowedFutureDate(boolean allowedFutureDate) {
        this.allowedFutureDate = allowedFutureDate;
    }

    /**
     * ���t�𕶎���ŕԂ��܂��B
     * 
     * @return ��������t
     */
    public String getText() {
        String day = null;
        String month = null;
        String year = null;
        String era;
        // ����
        if (this.era.getSelectedIndex() == 0) {
            era = "00";
            if (isYearVisible()) {
                year = "00�N";
                if (isMonthVisible()) {
                    month = "00��";
                    if (isDayVisible()) {
                        day = "00��";
                    }
                }
            }
        } else if (isDateUnknown()) {
            era = "�s��";
            if (isYearVisible()) {
                year = "00�N";
                if (isMonthVisible()) {
                    month = "00��";
                    if (isDayVisible()) {
                        day = "00��";
                    }
                }
            }
        } else {
            era = String.valueOf(this.era.getSelectedItem());
        }
        // �N
        if (year == null) {
            if (!isYearVisible()) {
                year = "";
            } else {
                year = getYear();
                if ("".equals(year)) {
                    year = "00";
                    if (isMonthVisible()) {
                        month = "00��";
                        if (isDayVisible()) {
                            day = "00��";
                        }
                    }
                } else if (year.length() < 2) {
                    year = "0" + year;
                }
                year = year + "�N";
            }
        }
        // ��
        if (month == null) {
            if (!isMonthVisible()) {
                month = "";
            } else {
                month = getMonth();
                if ("".equals(month)) {
                    month = "00";
                    if (isDayVisible()) {
                        day = "00��";
                    }
                } else if (month.length() < 2) {
                    month = "0" + month;
                }
                month = month + "��";
            }
        }
        // ��
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
                day = day + "��";
            }
        }

        return era + year + month + day;
    }

    /**
     * ���t�𕶎���Őݒ肵�܂��B
     * 
     * @param text ��������t
     */
    public void setText(String text) {
        int length = text.length();
        if ((length < 4) || (length > 11)) {
            clear();
            return;
        }
        if (text.indexOf("0000�N") >= 0) {
            clear();
            return;
        }
        if (text.indexOf("�s��") >= 0) {
            if (isAllowedUnknown()) {
                setDateUnknown();
            } else {
                clear();
            }
            return;
        }
        char[] chars = text.toCharArray();
        int emptyYearPos = text.indexOf("00�N");
        if (emptyYearPos >= 0) {
            chars[emptyYearPos + 1] = '1';
        } else if (text.indexOf("�N") < 0) {
            emptyYearPos = 0;
        }

        int emptyMonthPos = text.indexOf("00��");
        if (emptyMonthPos >= 0) {
            chars[emptyMonthPos + 1] = '1';
        } else if (text.indexOf("��") < 0) {
            emptyMonthPos = 0;
        }

        int emptyDayPos = text.indexOf("00��");
        if (emptyDayPos >= 0) {
            chars[emptyDayPos + 1] = '1';
        } else if (text.indexOf("��") < 0) {
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
     * �N��ڂ̕\����Ԃ��擾
     * 
     * @return true:�\��, false:��\��
     */
    public boolean isAgeVisible() {
        return age.isVisible();
    }

    /**
     * �N��ڂ̕\��/��\����ݒ肵�܂��B
     * 
     * @param ageVisible true:�\��, false:��\��
     */
    public void setAgeVisible(boolean ageVisible) {
        age.setVisible(ageVisible);
    }

    /**
     * �N���ڂ̕\����Ԃ��擾���܂��B
     * 
     * @return true:�\��, false:��\��
     */
    public boolean isYearVisible() {
        return year.isVisible() && year.isVisible();
    }

    /**
     * �N���ڂ̕\��/��\����ݒ肵�܂��B
     * 
     * @param yearVisible true:�\��, false:��\��
     */
    public void setYearVisible(boolean yearVisible) {
        year.setVisible(yearVisible);
        yearUnit.setVisible(yearVisible);
    }

    /**
     * �����ڂ̕\����Ԃ��擾���܂��B
     * 
     * @return true:�\��, false:��\��
     */
    public boolean isMonthVisible() {
        return month.isVisible() && monthUnit.isVisible();
    }

    /**
     * �����ڂ̕\��/��\����ݒ肵�܂��B
     * 
     * @param monthVisible true:�\��, false:��\��
     */
    public void setMonthVisible(boolean monthVisible) {
        month.setVisible(monthVisible);
        monthUnit.setVisible(monthVisible);
    }

    /**
     * ���t���ڂ̕\����Ԃ��擾���܂��B
     * 
     * @return true:�\��, false:��\��
     */
    public boolean isDayVisible() {
        return day.isVisible() && dayUnit.isVisible();
    }

    /**
     * ���t���ڂ̕\��/��\����ݒ肵�܂��B
     * 
     * @param dayVisible true:�\��, false:��\��
     */
    public void setDayVisible(boolean dayVisible) {
        day.setVisible(dayVisible);
        dayUnit.setVisible(dayVisible);
    }

    /**
     * Enabled���擾
     * 
     * @return boolean
     */
    public boolean isEnabled() {
        return era.isEnabled();
    }

    /**
     * Enabled��ݒ�
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
     * TextBox��Editable���擾
     * 
     * @return boolean
     */
    public boolean isEditable() {
        return era.isEditable();
    }

    /**
     * Editable��ݒ�
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
     * �u�s���v���ڂ������Ă��邩���擾���܂��B
     * 
     * @return true:�����Ă���, false:�����Ă��Ȃ�
     */
    public boolean isAllowedUnknown() {
        return allowedUnknown;
    }

    /**
     * �u�s���v���ڂ��������邩�ǂ�����ݒ肵�܂��B
     * 
     * @param allowedUnknown true:��������, false:�������Ȃ�
     */
    public void setAllowedUnknown(boolean allowedUnknown) {
        this.allowedUnknown = allowedUnknown;
        addEraUnknown();
    }

    /**
     * ���I���������i�G���[�Ƃ݂Ȃ��Ȃ��j�����擾���܂��B
     * 
     * @return true:���I��������, false:���I���������Ȃ�
     */
    public boolean isAllowedBlank() {
        return allowedBlank;
    }

    /**
     * ���I���������i�G���[�Ƃ݂Ȃ��Ȃ��j���ǂ�����ݒ肵�܂��B
     * 
     * @param allowedBlank true:���I��������, false:���I���������Ȃ�
     */
    public void setAllowedBlank(boolean allowedBlank) {
        this.allowedBlank = allowedBlank;
    }

    /**
     * �R���{�Ɂu�s�ځv��ǉ����܂��B
     */
    public void addEraUnknown() {
        VRArrayList ar = new VRArrayList(Arrays.asList(ERA_LIST));
        if (isAllowedUnknown()) {
            ar.add(ERA_UNKNOWN);
        }
        era.setModel(new VRComboBoxModelAdapter(ar));
    }

    /**
     * �����̓��t�������邩�ǂ����̐ݒ���擾���܂��B
     * 
     * @return true:������, false:�����Ȃ�
     */
    public boolean isAllowedFutureDate() {
        return allowedFutureDate;
    }

    /**
     * ������Ă�����͔͈͂��擾���܂��B
     * 
     * @return int
     */
    public int getRequestedRange() {
        return this.requestedRange;
    }

    /**
     * �����͂�������͈͂�ݒ肵�܂��B<br />
     * RNG_ERA : �N���͕K�{�I���ƂȂ�܂��B�����͎��̓`�F�b�N���܂���̂ŁA�����I�ɂ͂��ׂċ��e���܂��B<br />
     * RNG_YEAR : �N���A�N�͕K�{���͂ƂȂ�܂��B�i���̖����͂͋����܂��B�j<br />
     * RNG_MONTH : �N���A�N�A���͕K�{���͂ƂȂ�܂��B�i���̖����͂͋����܂��B�j<br />
     * RNG_DAY : �N���A�N�A���A���͕K�{���͂ƂȂ�܂��B�i���ׂĂ̍��ڂ��K�{�ƂȂ�܂��B�j<br />
     * 
     * @param requestedRange int
     */
    public void setRequestedRange(int requestedRange) {
        this.requestedRange = requestedRange;
        doValidCheck();
    }

    /**
     * �\������N���͈̔͂��擾���܂��B
     * 
     * @return int
     */
    public int getEraRange() {
        return eraRange;
    }

    /**
     * calendar.xml�Őݒ肳��Ă���N���̂����A���Ԗڈȍ~�������R���{�ɐݒ肷��̂���ݒ肵�܂��B<br/>
     * �Ⴆ�΁Acalendar.xml�ɐݒ肳��Ă���̂��u�����v�u�吳�v�u���a�v�u�����v�ł���A �ݒ肵�������ڂ��u���a�v�u�����v�݂̂ł���ꍇ�A
     * eraRange = 2�Ƃ��邱�ƂŁA�C���f�b�N�X2�Ԃł���u���a�v�ȍ~�������R���{�ɐݒ肳��܂��B
     * 
     * @param eraRange �����̃C���f�b�N�X
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
     * "�s��"�ɐݒ肵�܂��B
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
     * �s�ڂ�I�����Ă��邩��Ԃ��܂��B
     * 
     * @return �s�ڂ�I�����Ă��邩
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
     * @deprecated �������܂���BsetRequestedRange���g���Ă��������B
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
     * �l�̗L��/�����̎擾<br />
     * ���͂���Ă�����t�̗L�������`�F�b�N����B
     * 
     * @return true:�L��, false:����
     */
    public int getInputStatus() {
        int status = checkDateValue();
        if (status == FLG_EMPTY) {
            return STATE_EMPTY;
        } else {
            boolean valid = checkValid(status);
            if (valid) {
                // ���t�͈̓G���[
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
     * �����R���{�ύX���̃C�x���g
     */
    public void checkEraItemState() {
        // "�s��"���I������Ă��邩�𔻒�
        boolean isUnknownDate;
        if (era.getSelectedItem().equals(ERA_UNKNOWN)) {
            isUnknownDate = true;
            year.setEnabled(false);
            month.setEnabled(false);
            day.setEnabled(false);
        } else {
            isUnknownDate = false;
        }

        // Label�ƔN���Enabled��ݒ�
        yearUnit.setEnabled(!isUnknownDate);
        monthUnit.setEnabled(!isUnknownDate);
        dayUnit.setEnabled(!isUnknownDate);
        age.setEnabled(!isUnknownDate);

        // TextField��Enabled��ݒ�
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
     * ���t�`���̊ԈႢ�ӏ��ɂ����Enabled��ύX���A�C�x���g�𔭍s����
     */
    private void changedDateValue() {
        // �X�V�`�F�b�N
        String newValue = era.getSelectedItem() + this.getYear() + "�N"
                + this.getMonth() + "��" + this.getDay() + "��";
        if (newValue.equals(oldValue)) {
            return;
        }
        oldValue = newValue;

        // ��Ԕ���
        int state = checkDateValue();

        // Enabled�ݒ�
        setComponentEnabled(state);

        // ���S���t�Ȃ�N��v�Z
        setAge("");
        if (state == FLG_NO_ERR) {
            if (getRequestedRange() == RNG_DAY) {
                calcAge();
            }
        }

        // ���̗͂L���E�����̔���
        boolean valid = checkValid(state);

        // �eVRLabelContainer�̐F�ύX
        setParentColor(valid);

        // �C�x���g����
        fireValueChanged(new IkenshoEraDateTextFieldEvent(this, valid, state));
    }

    public void doValidCheck() {
        // ��Ԕ���
        int state = checkDateValue();

        // ���̗͂L���E�����̔���
        boolean valid = checkValid(state);

        // �eVRLabelContainer�̐F�ύX
        setParentColor(valid);
    }

    /**
     * �����E�N�E���E���̑g�ݍ��킹�̑Ó����̌���
     * 
     * @return int
     */
    private int checkDateValue() {
        String str = "";
        VRDateFormat vrdf = null;
        dateRangeErr = DATE_RANGE_NO_ERR;

        // �N�����ݒ肳��Ă��Ȃ��ꍇ
        if (era.getSelectedItem() == null) {

            return FLG_EMPTY;
        }
        if (era.getSelectedIndex() == 0) {
            return FLG_EMPTY;
        }

        // �s��
        if (era.getSelectedItem().equals(ERA_UNKNOWN)) {
            return FLG_UNKNOWN;
        }

        // �ԈႢ�Ȃ�
        try {
            str = era.getSelectedItem() + this.getYear() + "�N"
                    + this.getMonth() + "��" + this.getDay() + "��";
            vrdf = new VRDateFormat("ggge�NM��d��");
            Date val = vrdf.parse(str);
            // ���t�͈̓G���[�`�F�b�N
            if (isFutureDateErr(val)) { // �������t
                dateRangeErr = DATE_RANGE_FUTURE_ERR;
            } else if (getMinimumDate().after(val)) { // �ߋ����t
                dateRangeErr = DATE_RANGE_PAST_ERR;
            }

            return FLG_NO_ERR;
        } catch (Exception ex) {
        }

        // �����Ⴄ
        try {
            str = era.getSelectedItem() + this.getYear() + "�N"
                    + this.getMonth() + "��";
            vrdf = new VRDateFormat("ggge�NM��");
            Date val = vrdf.parse(str);
            // ���t�͈̓G���[�`�F�b�N
            if (isFutureDateErr(val)) { // �������t
                dateRangeErr = DATE_RANGE_FUTURE_ERR;
            } else if (getMinimumDate().after(val)) { // �ߋ����t
                dateRangeErr = DATE_RANGE_PAST_ERR;
            }
            return FLG_DAY_ERR;
        } catch (Exception ex) {
        }

        // �����Ⴄ
        try {
            str = era.getSelectedItem() + this.getYear() + "�N";
            vrdf = new VRDateFormat("ggge�N");
            Date val = vrdf.parse(str);
            // ���t�͈̓G���[�`�F�b�N
            if (isFutureDateErr(val)) { // �������t
                dateRangeErr = DATE_RANGE_FUTURE_ERR;
            } else if (getMinimumDate().after(val)) { // �ߋ����t
                dateRangeErr = DATE_RANGE_PAST_ERR;
            }
            return FLG_MONTH_ERR;
        } catch (Exception ex) {
        }

        // �N���Ⴄ
        try {
            if (era.getSelectedIndex() >= 0) {
                return FLG_YEAR_ERR;
            }
        } catch (Exception ex) {
        }

        // �������Ⴄ
        return FLG_ERA_ERR;
    }

    /**
     * �������t�G���[�ł��邩�ǂ����𔻒肵�܂��B
     * 
     * @param val ��r�Ώۓ��t
     * @return true:�G���[����, false:�G���[�Ȃ�
     */
    private boolean isFutureDateErr(Date val) {
        // �������t�������Ă���̂Ȃ�A�������t�G���[�ł͂Ȃ�
        if (isAllowedFutureDate()) {
            return false;
        }

        // �������t�`�F�b�N�p�̖{�����t+1
        Calendar nowCal = Calendar.getInstance();
        nowCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH),
                nowCal.get(Calendar.DAY_OF_MONTH) +1, 0, 0, 0);
        nowCal.add(Calendar.SECOND, -1);
        Date now = nowCal.getTime();

        // ��r
        if (now.before(val)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * �ŏ����t���擾���܂��B
     * 
     * @return �ŏ����t
     */
    public Date getMinimumDate() {
        return minimumDate;
    }

    /**
     * �ŏ����t��ݒ肵�܂��B
     * 
     * @param minimumDate �ŏ����t
     */
    public void setMinimumDate(Date minimumDate) {
        this.minimumDate = minimumDate;
    }

    /**
     * ��Ԃɉ����āA�ݒ肳��Ă�����t���L�����ǂ����𔻒肷��
     * 
     * @param state ���
     * @return true:�L��, false:����
     */
    private boolean checkValid(int state) {
        switch (state) {
        case FLG_NO_ERR: // �G���[�Ȃ�
            valid = true;
            break;

        case FLG_DAY_ERR: // ������������
            valid = false;
            if (requestedRange < RNG_DAY) {
                if (ACCommon.getInstance().isNullText(getDay())) {
                    valid = true;
                }
            }
            break;

        case FLG_MONTH_ERR: // ������������
            valid = false;
            if (requestedRange < RNG_MONTH) {
                if (ACCommon.getInstance().isNullText(getMonth())) {
                    valid = true;
                }
            }
            break;

        case FLG_YEAR_ERR: // �N����������
            valid = false;
            if (requestedRange < RNG_YEAR) {
                if (ACCommon.getInstance().isNullText(getYear())) {
                    valid = true;
                }
            }
            break;

        case FLG_EMPTY: // ������
        case FLG_UNKNOWN: // �u�s�ځv
            valid = isAllowedBlank();
            // valid = true;
            break;
        }
        return valid;
    }

    /**
     * �eVRLabelContainer�̐F�ύX
     * 
     * @param valid ���t�͗L���ł��邩
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
            // ���t�͈̓G���[
            valid &= (dateRangeErr == DATE_RANGE_NO_ERR);
            ACFrameEventProcesser processer = ACFrame.getInstance()
                    .getFrameEventProcesser();
            if (!valid) {
                // �G���[�F
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
                        // �x���F
                        fore = processer.getContainerWarningForeground();
                        back = processer.getContainerWarningBackground();
                        parent.setLabelFilled(true);
                    } else {
                        // �f�t�H���g�F
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
     * �N�����A�X�̃R���|�[�l���g��Enabled��ݒ肷��
     * 
     * @param state ���
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
     * ���g���i�[���Ă���R���e�i��k��A�ŏ��Ɍ��ꂽVRLabelContainer��Ԃ��܂��B
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
     * �l���N���A����
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
     * �ŏ��̎q�R���g���[���Ƀt�H�[�J�X���ڂ��܂��B
     */
    public void requestChildFocus() {
        era.requestFocus();
    }

    /**
     * �����E�N�E���E���̂����ꂩ���ύX���ꂽ�ꍇ�ɔ���
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
     * Date�^�������e���Ȃ�����Ԃ��܂��B
     * 
     * @return Date�^�������e���Ȃ���
     */
    protected boolean isMustDateType() {
        return (getRequestedRange() == RNG_DAY) && (!isAllowedUnknown());
    }

    public Object createSource() {

        if (isMustDateType()) {
            return new Object();
        } else {
            return "0000�N00��00��";
        }
    }

    public void bindSource() throws ParseException {
        // null�`�F�b�N
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
     * �u���v���x����Ԃ��܂��B
     * 
     * @return �u���v���x��
     */
    public JLabel getDayUnit() {
        return dayUnit;
    }
    
    /**
     * �s�ڑI�����̏�Ԃɐݒ肵�܂��B
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
     * �R���|�[�l���g�̏�Ԃɉ�����Enable��ύX���܂��B
     */
    public void checkState(){
        // ��Ԕ���
        int state = checkDateValue();
        // Enabled�ݒ�
        setComponentEnabled(state);
    }
    
}
