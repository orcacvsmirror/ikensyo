/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * VRDateParser���g�p����\����͏󋵃N���X�ł��B
 * <p>
 * VRDateParserStatementable�����������\����̓N���X�ɉ�͂����s������ۂ̏�������ь��ʂ��i�[���܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2006/02/14
 */
public class VRDateParserStatementParseOption {
    private int day = -1;
    private int era = 0;
    private ArrayList eras;
    private int hour = -1;
    private Locale locale;
    private VRDateParserEra machedEra = null;
    private int minute = -1;
    private int month = -1;
    private Calendar now;
    private int parseBeginIndex = 0;
    private int parseEndIndex = 0;
    private int second = -1;

    private String target;
    private boolean useDay = false;
    private boolean useEra = false;
    private boolean useHour = false;
    private boolean useMinute = false;
    private boolean useMonth = false;
    private boolean useSecond = false;
    private boolean useYear = false;
    private int year = -1;

    /**
     * �R���X�g���N�^�ł��B
     */
    public VRDateParserStatementParseOption() {

    }

    /**
     * �R���X�g���N�^�ł��B
     * 
     * @param target ��͑Ώ�
     * @param eras �����W��
     * @param locale ���P�[��
     */
    public VRDateParserStatementParseOption(String target, ArrayList eras,
            Locale locale, Calendar now) {
        setTarget(target);
        setEras(eras);
        setLocale(locale);
        setNow(now);
    }

    /**
     * ��͌��ʂ̓� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ̓�
     */
    public int getDay() {
        return day;
    }

    /**
     * ��͌��ʂ̌����N ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ̌����N
     */
    public int getEra() {
        return era;
    }

    /**
     * ��͂ɗ��p���錳�����W�� ��Ԃ��܂��B
     * 
     * @return ��͂ɗ��p���錳�����W��
     */
    public ArrayList getEras() {
        return eras;
    }

    /**
     * ��͌��ʂ̎� ��Ԃ��܂��B
     * @return ��͌��ʂ̎�
     */
    public int getHour() {
        return hour;
    }

    /**
     * ��͂ɗ��p���錾�ꃍ�P�[�� ��Ԃ��܂��B
     * 
     * @return ��͂ɗ��p���錾�ꃍ�P�[��
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * ��͌��ʂ̌������ ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ̌������
     */
    public VRDateParserEra getMachedEra() {
        return machedEra;
    }

    /**
     * ��͌��ʂ̕� ��Ԃ��܂��B
     * @return ��͌��ʂ̕�
     */
    public int getMinute() {
        return minute;
    }

    /**
     * ��͌��ʂ̌� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ̌�
     */
    public int getMonth() {
        return month;
    }

    /**
     * ��͂ɗ��p���錻�ݓ��� ��Ԃ��܂��B
     * 
     * @return ��͂ɗ��p���錻�ݓ���
     */
    public Calendar getNow() {
        return now;
    }

    /**
     * ��͂ɗ��p�����͊J�n�ʒu ��Ԃ��܂��B
     * 
     * @return ��͂ɗ��p�����͊J�n�ʒu
     */
    public int getParseBeginIndex() {
        return parseBeginIndex;
    }

    /**
     * ��͌��ʂ̉�͏I���ʒu ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ̉�͏I���ʒu
     */
    public int getParseEndIndex() {
        return parseEndIndex;
    }

    /**
     * ��͌��ʂ̕b ��Ԃ��܂��B
     * @return ��͌��ʂ̕b
     */
    public int getSecond() {
        return second;
    }

    /**
     * ��͂ɗ��p�����͑Ώ� ��Ԃ��܂��B
     * 
     * @return ��͂ɗ��p�����͑Ώ�
     */
    public String getTarget() {
        return target;
    }

    /**
     * ��͌��ʂ̔N ��Ԃ��܂��B
     * 
     * @return ��͌��ʂ̔N
     */
    public int getYear() {
        return year;
    }

    /**
     * ��͌��ʂƂ��ē���ݒ肵���� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂƂ��ē���ݒ肵����
     */
    public boolean isUseDay() {
        return useDay;
    }

    /**
     * ��͌��ʂƂ��Č����N��ݒ肵���� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂƂ��Č����N��ݒ肵����
     */
    public boolean isUseEra() {
        return useEra;
    }

    /**
     * ��͌��ʂƂ��Ď���ݒ肵���� ��Ԃ��܂��B
     * @return ��͌��ʂƂ��Ď���ݒ肵����
     */
    public boolean isUseHour() {
        return useHour;
    }

    /**
     * ��͌��ʂƂ��ĕ���ݒ肵���� ��Ԃ��܂��B
     * @return ��͌��ʂƂ��ĕ���ݒ肵����
     */
    public boolean isUseMinute() {
        return useMinute;
    }

    /**
     * ��͌��ʂƂ��Č���ݒ肵���� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂƂ��Č���ݒ肵����
     */
    public boolean isUseMonth() {
        return useMonth;
    }

    /**
     * ��͌��ʂƂ��ĕb��ݒ肵���� ��Ԃ��܂��B
     * @return ��͌��ʂƂ��ĕb��ݒ肵����
     */
    public boolean isUseSecond() {
        return useSecond;
    }

    /**
     * ��͌��ʂƂ��ĔN��ݒ肵���� ��Ԃ��܂��B
     * 
     * @return ��͌��ʂƂ��ĔN��ݒ肵����
     */
    public boolean isUseYear() {
        return useYear;
    }

    /**
     * ��͌��ʂ̓� ��ݒ肵�܂��B
     * 
     * @param day ��͌��ʂ̓�
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * ��͌��ʂ̌����N ��ݒ肵�܂��B
     * 
     * @param era ��͌��ʂ̌����N
     */
    public void setEra(int era) {
        this.era = era;
    }

    /**
     * ��͂ɗ��p���錳�����W�� ��ݒ肵�܂��B
     * 
     * @param eras ��͂ɗ��p���錳�����W��
     */
    public void setEras(ArrayList eras) {
        this.eras = eras;
    }

    /**
     * ��͌��ʂ̎� ��ݒ肵�܂��B
     * @param hour ��͌��ʂ̎�
     */
    public void setHour(int hour) {
        this.hour = hour;
    }

    /**
     * ��͂ɗ��p���錾�ꃍ�P�[�� ��ݒ肵�܂��B
     * 
     * @param locale ��͂ɗ��p���錾�ꃍ�P�[��
     */
    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    /**
     * ��͌��ʂ̌������ ��ݒ肵�܂��B
     * 
     * @param machedEra ��͌��ʂ̌������
     */
    public void setMachedEra(VRDateParserEra machedEra) {
        this.machedEra = machedEra;
    }

    /**
     * ��͌��ʂ̕� ��ݒ肵�܂��B
     * @param minute ��͌��ʂ̕�
     */
    public void setMinute(int minute) {
        this.minute = minute;
    }

    /**
     * ��͌��ʂ̌� ��ݒ肵�܂��B
     * 
     * @param month ��͌��ʂ̌�
     */
    public void setMonth(int month) {
        this.month = month;
    }

    /**
     * ��͂ɗ��p���錻�ݓ��� ��ݒ肵�܂��B
     * 
     * @param now ��͂ɗ��p���錻�ݓ���
     */
    public void setNow(Calendar now) {
        this.now = now;
    }

    /**
     * ��͂ɗ��p�����͊J�n�ʒu ��ݒ肵�܂��B
     * 
     * @param parseBeginIndex ��͂ɗ��p�����͊J�n�ʒu
     */
    public void setParseBeginIndex(int parseBeginIndex) {
        this.parseBeginIndex = parseBeginIndex;
    }

    /**
     * ��͌��ʂ̉�͏I���ʒu ��ݒ肵�܂��B
     * 
     * @param parseEndIndex ��͌��ʂ̉�͏I���ʒu
     */
    public void setParseEndIndex(int parseEndIndex) {
        this.parseEndIndex = parseEndIndex;
    }

    /**
     * ��͌��ʂ̕b ��ݒ肵�܂��B
     * @param second ��͌��ʂ̕b
     */
    public void setSecond(int second) {
        this.second = second;
    }

    /**
     * ��͂ɗ��p�����͑Ώ� ��ݒ肵�܂��B
     * 
     * @param target ��͂ɗ��p�����͑Ώ�
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * ��͌��ʂƂ��ē���ݒ肵���� ��ݒ肵�܂��B
     * 
     * @param useDay ��͌��ʂƂ��ē���ݒ肵����
     */
    public void setUseDay(boolean useDay) {
        this.useDay = useDay;
    }

    /**
     * ��͌��ʂƂ��Č����N��ݒ肵���� ��ݒ肵�܂��B
     * 
     * @param useEra ��͌��ʂƂ��Č����N��ݒ肵����
     */
    public void setUseEra(boolean useEra) {
        this.useEra = useEra;
    }

    /**
     * ��͌��ʂƂ��Ď���ݒ肵���� ��ݒ肵�܂��B
     * @param useHour ��͌��ʂƂ��Ď���ݒ肵����
     */
    public void setUseHour(boolean useHour) {
        this.useHour = useHour;
    }

    /**
     * ��͌��ʂƂ��ĕ���ݒ肵���� ��ݒ肵�܂��B
     * @param useMinute ��͌��ʂƂ��ĕ���ݒ肵����
     */
    public void setUseMinute(boolean useMinute) {
        this.useMinute = useMinute;
    }

    /**
     * ��͌��ʂƂ��Č���ݒ肵���� ��ݒ肵�܂��B
     * 
     * @param useMonth ��͌��ʂƂ��Č���ݒ肵����
     */
    public void setUseMonth(boolean useMonth) {
        this.useMonth = useMonth;
    }

    /**
     * ��͌��ʂƂ��ĕb��ݒ肵���� ��ݒ肵�܂��B
     * @param useSecond ��͌��ʂƂ��ĕb��ݒ肵����
     */
    public void setUseSecond(boolean useSecond) {
        this.useSecond = useSecond;
    }

    /**
     * ��͌��ʂƂ��ĔN��ݒ肵���� ��ݒ肵�܂��B
     * 
     * @param useYear ��͌��ʂƂ��ĔN��ݒ肵����
     */
    public void setUseYear(boolean useYear) {
        this.useYear = useYear;
    }

    /**
     * ��͌��ʂ̔N ��ݒ肵�܂��B
     * 
     * @param year ��͌��ʂ̔N
     */
    public void setYear(int year) {
        this.year = year;
    }
}