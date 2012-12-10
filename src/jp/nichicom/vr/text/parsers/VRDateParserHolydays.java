/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

/**
 * VRDateParser���g�p����j�Փ��W���N���X�ł��B
 * <p>
 * �w����A�w��j�����j�Փ��Ƃ��ē���\�ł��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 * @see VRDateParserHolydayCalculatable
 * @see VRDateParserHolydayTerm
 */
public class VRDateParserHolydays implements VRDateParserHolydayCalculatable {
    private ArrayList calcHolydays;

    private ArrayList[][] dayHolydayTerms;

    private ArrayList[][][] weekHolydayTerms;

    /**
     * �R���X�g���N�^
     */
    public VRDateParserHolydays() {
        clearHolydays();
    }

    /**
     * �j�Փ��v�Z��`��ǉ����܂��B
     * 
     * @param calcuable �j�Փ��v�Z��`
     */
    public void addCalcHolyday(VRDateParserHolydayCalculatable calcuable) {
        getCalcHolydays().add(calcuable);
    }

    /**
     * �Œ���j�Փ�������`��ǉ����܂��B
     * 
     * @param term �Œ���j�Փ�������`
     * @param month ��
     * @param day ��
     */
    public void addDayHolydayTerm(VRDateParserHolydayTerm term, int month,
            int day) {
        ArrayList terms = getDayHolydayTerms()[month - 1][day - 1];
        if (terms == null) {
            terms = new ArrayList();
            getDayHolydayTerms()[month - 1][day - 1] = terms;
        }
        terms.add(term);
    }

    /**
     * �Œ�T�j�Փ�������`��ǉ����܂��B
     * 
     * @param term �Œ�T�j�Փ�������`
     * @param month ��
     * @param week �T
     * @param wday �j��
     */
    public void addWeekHolydayTerm(VRDateParserHolydayTerm term, int month,
            int week, int wday) {
        ArrayList terms = getWeekHolydayTerms()[month - 1][week - 1][wday - 1];
        if (terms == null) {
            terms = new ArrayList();
            getWeekHolydayTerms()[month - 1][week - 1][wday - 1] = terms;
        }
        terms.add(term);
    }

    /**
     * �j�Փ��v�Z��`�W�������������܂��B
     */
    public void clearCalcHolydays() {
        getCalcHolydays().clear();
    }

    /**
     * �j�Փ���`�����������܂��B
     */
    public void clearHolydays() {
        //12��6�T7��
        weekHolydayTerms = new ArrayList[12][6][7];
        //12��31��
        dayHolydayTerms = new ArrayList[12][31];
        calcHolydays = new ArrayList();
    }

    /**
     * �j�Փ��v�Z��`�W�� ��Ԃ��܂��B
     * 
     * @return �j�Փ��v�Z��`�W��
     */
    public ArrayList getCalcHolydays() {
        return calcHolydays;
    }

    /**
     * �Œ���j�Փ���`�W�� ��Ԃ��܂��B
     * 
     * @return �Œ���j�Փ�������`�W��
     */
    public ArrayList[][] getDayHolydayTerms() {
        return dayHolydayTerms;
    }

    /**
     * �Œ���j�Փ�������`�W����Ԃ��܂��B
     * 
     * @param month ��
     * @param day ��
     * @return �Œ���j�Փ�������`�W��
     */
    public ArrayList getDayHolydayTerms(int month, int day) {
        return getDayHolydayTerms()[month - 1][day - 1];
    }

    /**
     * �Œ�T�j�Փ���`�W�� ��Ԃ��܂��B
     * 
     * @return �Œ�T�j�Փ�������`�W��
     */
    public ArrayList[][][] getWeekHolydayTerms() {
        return weekHolydayTerms;
    }

    /**
     * �Œ�T�j�Փ�������`�W����Ԃ��܂��B
     * 
     * @param month ��
     * @param week �T
     * @param wday �j��
     * @return �Œ�T�j�Փ�������`�W��
     */
    public ArrayList getWeekHolydayTerms(int month, int week, int wday) {
        return getWeekHolydayTerms()[month - 1][week - 1][wday - 1];
    }

    /**
     * �j�Փ��v�Z��`�W�� ��ݒ肵�܂��B
     * 
     * @param calcHolydays �j�Փ��v�Z��`�W��
     */
    public void setCalcHolydays(ArrayList calcHolydays) {
        this.calcHolydays = calcHolydays;
    }

    /**
     * �Œ���j�Փ�������`�W�� ��ݒ肵�܂��B
     * 
     * @param dayHolydayTerms �Œ���j�Փ�������`�W��
     */
    public void setDayHolydayTerms(ArrayList[][] dayHolydayTerms) {
        this.dayHolydayTerms = dayHolydayTerms;
    }

    /**
     * �Œ�T�j�Փ���`�W�� ��ݒ肵�܂��B
     * 
     * @param weekHolydayTerms �Œ�T�j�Փ�������`�W��
     */
    public void setWeekHolydayTerms(ArrayList[][][] weekHolydayTerms) {
        this.weekHolydayTerms = weekHolydayTerms;
    }

    /**
     * ����Ăяo���p�̏j�Փ��擾�֐��ł��B
     * 
     * @param cal �Ώۓ��t
     * @param holydays ���ʊi�[�p�̋x���W��
     */
    public void stockHolyday(Calendar cal, List holydays) {
        stockHolyday(cal, holydays, null);
    }

    public void stockHolyday(Calendar cal, List holydays, Object parameter) {
        int m = cal.get(Calendar.MONTH);
        int d = cal.get(Calendar.DATE) - 1;
        int week = cal.get(Calendar.DAY_OF_WEEK_IN_MONTH) - 1;
        int wday = cal.get(Calendar.DAY_OF_WEEK) - 1;
        addMatchHoliday((List) getWeekHolydayTerms()[m][week][wday], cal,
                holydays, parameter);
        addMatchHoliday((List) getDayHolydayTerms()[m][d], cal, holydays,
                parameter);
        addMatchHoliday(getCalcHolydays(), cal, holydays, parameter);

    }

    /**
     * �j�Փ�����W���𑖍����ĊY������j�Փ���`��ǉ����܂��B
     * 
     * @param src �`�F�b�N�Ώۂ̏j�Փ�����W��
     * @param cal �Ώۓ�
     * @param dest �j�Փ���`�̒ǉ���
     * @param parameter �z�Q�Ɩh�~���ɗ��p���鎩�R�̈�
     */
    protected void addMatchHoliday(List src, Calendar cal, List dest,
            Object parameter) {
        if (src == null) {
            return;
        }
        Iterator it = src.iterator();
        while (it.hasNext()) {
            VRDateParserHolydayCalculatable calc = (VRDateParserHolydayCalculatable) it
                    .next();
            calc.stockHolyday(cal, dest, parameter);
        }
    }
}