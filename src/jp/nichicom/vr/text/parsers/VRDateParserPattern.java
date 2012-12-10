/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;


/**
 * VRDateParser���g�p����\���p�^�[����̓N���X�ł��B
 * <p>
 * ��������p�^�[���P�ʂŉ��߁E�|�󂵁A�Ή����������Ԃ��܂��B
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRDateParser
 */
public class VRDateParserPattern {

    /**
     * ���������̘A������Ԃ��܂��B
     * 
     * @param target �����Ώ�
     * @param i �J�E���^
     * @param len �J�E���^�I�[
     * @param token ��������
     * @return �A����(1..len-i)
     */
    protected static int getContinationCount(String target, int i, int len,
            char token) {
        int cnt = 1;
        for (; i < len; i++) {
            if (target.charAt(i) != token) {
                break;
            }
            cnt++;
        }
        //���ߕ���߂�
        return cnt;
    }

    private String pattern;

    private ArrayList statements;

    /**
     * �R���X�g���N�^
     */
    public VRDateParserPattern() {
        this.statements = new ArrayList();
    }

    /**
     * ������p�^�[����Ԃ��܂��B
     * 
     * @return ������p�^�[��
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * �\���W����Ԃ��܂��B
     * 
     * @return �\���W��
     */
    public ArrayList getStatements() {
        return statements;
    }

    /**
     * ���������͂��ē��t�Ƃ��ĕԂ��܂��B
     * 
     * @param target ��͑Ώ�
     * @param eras �Ώ̃��P�[���̌����W��
     * @param locale ���ݓ����擾�ɗ��p���郍�P�[��
     * @return ���t
     * @throws ParseException ��͎��s
     */
    public Calendar match(String target, ArrayList eras, Locale locale)
            throws ParseException {
        int len = target.length();
        if (len == 0) {
            throw new ParseException("����0�̕������͂Ɏ��s", 0);
        }

        //���ݓ���
        Calendar now;
        if (locale == null) {
            now = Calendar.getInstance();
        } else {
            now = Calendar.getInstance(locale);
        }

        VRDateParserStatementParseOption parseOption = new VRDateParserStatementParseOption(
                target, eras, locale, now);

        Iterator it = statements.iterator();
        while (it.hasNext()) {
            VRDateParserStatementable statement = (VRDateParserStatementable) it
                    .next();
            if (!statement.isMatched(parseOption)) {
                //�\���K���ɓK�����Ȃ�����
                return null;
            }
            //��͏I���ʒu�����̉�͈ʒu�Ƃ��ē]�L
            parseOption.setParseBeginIndex(parseOption.getParseEndIndex());
        }

        if (parseOption.getParseEndIndex() < len) {
            //�Ή��t���Ă��Ȃ������񂪂���
            return null;
        }

        if ((parseOption.getMachedEra() == null) && (parseOption.getEra() > 0)) {
            //�����w��Ȃ����������N�͎w��ς� �� ���݂ɑΉ����錳����I��

            Iterator eraIt = eras.iterator();
            while (eraIt.hasNext()) {
                VRDateParserEra eraItem = (VRDateParserEra) eraIt.next();
                long nowMilllis = parseOption.getNow().getTimeInMillis();
                if ((nowMilllis >= eraItem.getBegin().getTimeInMillis())
                        && (nowMilllis <= eraItem.getEnd().getTimeInMillis())) {
                    //�����_��͈͓��Ƃ��錳���𔭌�
                    parseOption.setMachedEra(eraItem);
                    break;
                }
            }
            if (parseOption.getMachedEra() == null) {
                //���݂�\���������w�肳��Ă��Ȃ�����
                throw new ParseException("���݂̓��t���܂ތ�������`����Ă��܂���B[ " + target
                        + " ]", 0);
            }
        }

        Calendar pos;
        if (parseOption.getMachedEra() != null) {
            //�����w�肠��

            Calendar eraBeginCal = parseOption.getMachedEra().getBegin();
            long eraBeginMillis = eraBeginCal.getTimeInMillis();
            int eraBeginYear = eraBeginCal.get(Calendar.YEAR);
            int eraBeginMonth = eraBeginCal.get(Calendar.MONTH);
            int eraBeginDay = eraBeginCal.get(Calendar.DATE);
            int eraBegin = eraBeginYear * 10000 + eraBeginMonth * 100
                    + eraBeginDay;
            Calendar eraEndCal = parseOption.getMachedEra().getEnd();
            long eraEndMillis = eraEndCal.getTimeInMillis();
            int eraEndYear = eraEndCal.get(Calendar.YEAR);
            int eraEndMonth = eraEndCal.get(Calendar.MONTH);
            int eraEndDay = eraEndCal.get(Calendar.DATE);
            int eraEnd = eraEndYear * 10000 + eraEndMonth * 100 + eraEndDay;

            if (parseOption.isUseEra() && (parseOption.getEra() <= 0)) {
                //�����I�Ɍ����N���w�肵�Ă����Ȃ��炩�����N��0�ȉ�
                throw new ParseException("�����N��0�ȉ����w�肳��Ă��܂��B[ " + target + " ]",
                        0);
            }
            parseOption.setYear(eraBeginYear);
            if (parseOption.getEra() > 0) {
                //�����N����
                parseOption.setYear(parseOption.getYear()
                        + parseOption.getEra() - 1);
            }
            if (eraEndYear < parseOption.getYear()) {
                throw new ParseException("�w��̓��t�������͈͓̔��ɂ���܂���B[ " + target
                        + " ](Era=" + eraBegin + "-" + eraEnd + ")", 0);
            }

            pos = Calendar.getInstance();
            pos.setLenient(false);
//            pos.setTimeInMillis(0);
            pos.clear();
            if (parseOption.isUseMonth()) {
                //���w�肠��

                if (parseOption.isUseDay()) {
                    //���w�肠��
                    pos.set(parseOption.getYear(), parseOption.getMonth() - 1,
                            parseOption.getDay());
                } else {
                    //���w��Ȃ�
                    pos.set(parseOption.getYear(), parseOption.getMonth() - 1,
                            1);
                    if (pos.before(eraBeginCal)) {
                        //�w��N����1���͌����͈͂������
                        pos.set(parseOption.getYear(),
                                parseOption.getMonth() - 1, eraBeginDay);
                    }
                }
            } else {
                //���w��Ȃ�
                pos.set(parseOption.getYear(), eraBeginMonth, eraBeginDay);

                if (pos.after(eraEndCal)) {
                    //�w��N�Ɍ����J�n���������Z����ƁA�����͈͂𒴂���
                    pos.set(parseOption.getYear(), 1, 1);
                }
            }
            
//            return pos;
        } else {
            //�����w��Ȃ�

            if (!parseOption.isUseYear()) {
                //�N�w��Ȃ�
                parseOption.setYear(parseOption.getNow().get(Calendar.YEAR));
            }

            if (!parseOption.isUseMonth()) {
                //���w��Ȃ�
                parseOption.setMonth(1);
            }

            if (!parseOption.isUseDay()) {
                //���w��Ȃ�
                parseOption.setDay(1);
            }

            //���t�͈̓`�F�b�N
            parseOption.getNow().setLenient(false);
            parseOption.getNow().setTimeInMillis(0);
            parseOption.getNow().set(parseOption.getYear(), parseOption.getMonth() - 1,
                    parseOption.getDay());
//            return parseOption.getNow();
            pos = parseOption.getNow();
        }

        if (!parseOption.isUseHour()) {
            //���w��Ȃ�
            parseOption.setHour(0);
        }
        if (!parseOption.isUseMinute()) {
            //���w��Ȃ�
            parseOption.setMinute(0);
        }
        if (!parseOption.isUseSecond()) {
            //�b�w��Ȃ�
            parseOption.setSecond(0);
        }
        pos.set(Calendar.HOUR_OF_DAY, parseOption.getHour());
        pos.set(Calendar.MINUTE, parseOption.getMinute());
        pos.set(Calendar.SECOND, parseOption.getSecond());
        
        return pos;
    }

    /**
     * ������p�^�[����ݒ肵�܂��B
     * 
     * @param pattern ������p�^�[��
     */
    public void setPattern(String pattern) throws ParseException {
        this.pattern = pattern;
        parse();
    }

    /**
     * �\���W����ݒ肵�܂��B
     * 
     * @param statements �\���W��
     */
    public void setStatements(ArrayList statements) {
        this.statements = statements;
    }

    /**
     * ������p�^�[������͂��܂��B
     * 
     * @throws ParseException �����̉�͎��s
     */
    protected void parse() throws ParseException {

        //�N�ƌ����̍���
        final int SET_NONE = 0;
        final int SET_YEAR = 1;
        final int SET_ERA = 2;
        int yearType = SET_NONE;
        boolean monthSeted = false;
        boolean daySeted = false;
        boolean hourSeted = false;
        boolean minuteSeted = false;
        boolean secondSeted = false;

        final char QUOTE = '\'';
        //�N�H�[�g�ł������Ă��邩
        final int NONE = 0;
        final int QUOTING = 1;
        final int QUOTED = 2;
        int quoteStatus = NONE;
        //�N�H�[�g���G�X�P�[�v������
        boolean quoteEscaped = false;

        statements.clear();

        //y,M,d
        int backRefIndex = 1;
        int cellBeginPos = 0;
        int cellEndPos = 0;

        String line = getPattern();
        int len = line.length();

        for (int i = 0; i < len; i++) {
            if (line.charAt(i) == QUOTE) {
                switch (quoteStatus) {
                case NONE:
                    //�N�H�[�g�ȍ~���؂�o���Ώ�
                    cellBeginPos = i + 1;
                    quoteStatus = QUOTING;
                    quoteEscaped = false;
                    break;
                case QUOTING:
                    //�N�H�[�g���̃N�H�[�g �� ���O�܂ł��؂�o���Ώ�
                    cellEndPos = i - 1;
                    quoteStatus = QUOTED;
                    break;
                case QUOTED:
                    //�N�H�[�g�I���Ǝv�����̂ɑ����ăN�H�[�g �� �N�H�[�g�G�X�P�[�v
                    cellEndPos = i - 1;
                    quoteStatus = QUOTING;
                    quoteEscaped = true;
                    break;
                }
                //switch��̏����͍s�킸�A���̕����𑖍�����
                continue;
            }
            if (quoteStatus == QUOTING) {
                //�N�H�[�g�� �� �X�L�b�v
                continue;
            }

            VRDateParserStatementable nextStatement;
            int contCnt = 0;
            switch (line.charAt(i)) {
            case VRDateParserStatementNumber.YEAR:
                if (yearType == SET_ERA) {
                    //������ݒ�ς�
                    throw new ParseException("�N�ƌ��������݂��Ă��܂��B[ " + line
                            + " ](Pos=" + cellBeginPos + ")", cellBeginPos);
                }
                yearType = SET_YEAR;

                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.YEAR);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.YEAR, contCnt);
                break;
            case VRDateParserStatementNumber.MONTH:
                monthSeted = true;
                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.MONTH);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.MONTH, contCnt);
                break;
            case VRDateParserStatementNumber.DAY:
                daySeted = true;
                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.DAY);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.DAY, contCnt);
                break;
            case VRDateParserStatementNumber.ERA:
                if (yearType == SET_YEAR) {
                    //������ݒ�ς�
                    throw new ParseException("�N�ƌ��������݂��Ă��܂��B[ " + line
                            + " ](Pos=" + cellBeginPos + ")", cellBeginPos);
                }
                yearType = SET_ERA;

                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.ERA);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.ERA, contCnt);
                break;
            case 'g':
                if (yearType == SET_YEAR) {
                    //������ݒ�ς�
                    throw new ParseException("�N�ƌ��������݂��Ă��܂��B[ " + line
                            + " ](Pos=" + cellBeginPos + ")", cellBeginPos);
                }
                yearType = SET_ERA;

                contCnt = getContinationCount(line, i + 1, len, 'g');
                nextStatement = new VRDateParserStatementEra(contCnt);
                break;
            case VRDateParserStatementNumber.HOUR:
                hourSeted = true;
                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.HOUR);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.HOUR, contCnt);
                break;
            case VRDateParserStatementNumber.MINUTE:
                minuteSeted = true;
                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.MINUTE);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.MINUTE, contCnt);
                break;
            case VRDateParserStatementNumber.SECOND:
                secondSeted = true;
                contCnt = getContinationCount(line, i + 1, len,
                        VRDateParserStatementNumber.SECOND);
                nextStatement = new VRDateParserStatementNumber(
                        VRDateParserStatementNumber.SECOND, contCnt);
                break;
            default:
                //���e����
                //switch��̏����͍s�킸�A���̕����𑖍�����
                continue;
            }

            VRDateParserStatementLiteral literalStatement = null;
            switch (quoteStatus) {
            case NONE:
                //�N�H�[�g�Ȃ��̏�ԂŃ��e�����I�� �� ���O�܂ł��؂�o���Ώ�
                cellEndPos = i;
                if (cellBeginPos < cellEndPos) {
                    literalStatement = new VRDateParserStatementLiteral(line
                            .substring(cellBeginPos, cellEndPos));
                }
                break;
            case QUOTED:
                //�N�H�[�g�ς݂̏�ԂŃ��e�����I�� �� �N�H�[�g�̒��O�܂ł��؂�o���Ώ�
                if (cellBeginPos < cellEndPos) {

                    if (quoteEscaped) {
                        //�N�H�[�g�G�X�P�[�v���s���� �� [""]��["]�ɒu��
                        literalStatement = new VRDateParserStatementLiteral(
                                line.substring(cellBeginPos, cellEndPos)
                                        .replaceAll("''", "'"));
                        quoteEscaped = false;
                    } else {
                        literalStatement = new VRDateParserStatementLiteral(
                                line.substring(cellBeginPos, cellEndPos));
                    }
                }
                quoteStatus = NONE;
                break;
            }
            //�v���[�X�z���_�̘A���������A���e�������v���[�X�z���_�̏��ŏo������

            if (literalStatement != null) {
                //���e�������v���[�X�z���_�̏��Ō��o
                String str = literalStatement.getText();
                if (statements.size() > 0) {
                    VRDateParserStatementable frontStatement = (VRDateParserStatementable) getStatements()
                            .get(statements.size() - 1);
                    if (frontStatement instanceof VRDateParserStatementNumber) {
                        //�O�����l�Ŏ��̃��e�����̐擪�����l�Ȃ��Exception

                        char betweenChar = str.charAt(0);
                        if ((betweenChar >= '0') && (betweenChar <= '9')) {
                            throw new ParseException(
                                    "���e���������Ɛ��l�v���[�X�z���_���A�����Ă��܂��B[ " + line
                                            + " ](Pos=" + cellBeginPos + ")",
                                    cellBeginPos);
                        }
                    }

                }

                //�������l�őO�̃��e�����̖��������l�Ȃ��Exception
                if (nextStatement instanceof VRDateParserStatementNumber) {

                    char betweenChar = str.charAt(str.length() - 1);
                    if ((betweenChar >= '0') && (betweenChar <= '9')) {
                        throw new ParseException("���e���������Ɛ��l�v���[�X�z���_���A�����Ă��܂��B[ "
                                + line + " ](Pos=" + cellBeginPos + ")",
                                cellBeginPos);
                    }
                }

                statements.add(literalStatement);

            }

            if (nextStatement != null) {
                statements.add(nextStatement);
            }

            i += contCnt - 1;
            cellBeginPos = i + 1;

        }

        if (cellBeginPos < len) {
            //���e���������ŏI�������ꍇ
            VRDateParserStatementLiteral literalStatement = null;
            switch (quoteStatus) {
            case NONE:
                //�N�H�[�g�Ȃ��̏�ԂŃ��e�����I�� �� ���O�܂ł��؂�o���Ώ�
                cellEndPos = len;
                if (cellBeginPos < cellEndPos) {
                    literalStatement = new VRDateParserStatementLiteral(line
                            .substring(cellBeginPos, cellEndPos));
                }
                break;
            case QUOTED:
                //�N�H�[�g�ς݂̏�ԂŃ��e�����I�� �� �N�H�[�g�̒��O�܂ł��؂�o���Ώ�
                if (cellBeginPos < cellEndPos) {

                    if (quoteEscaped) {
                        //�N�H�[�g�G�X�P�[�v���s���� �� [""]��["]�ɒu��
                        literalStatement = new VRDateParserStatementLiteral(
                                line.substring(cellBeginPos, cellEndPos)
                                        .replaceAll("''", "'"));
                    } else {
                        literalStatement = new VRDateParserStatementLiteral(
                                line.substring(cellBeginPos, cellEndPos));
                    }
                }
                break;
            }

            if (literalStatement != null) {
                //���e�������v���[�X�z���_�̏��Ō��o
                String str = literalStatement.getText();
                if (statements.size() > 0) {
                    VRDateParserStatementable frontStatement = (VRDateParserStatementable) getStatements()
                            .get(statements.size() - 1);
                    if (frontStatement instanceof VRDateParserStatementNumber) {
                        //�O�����l�Ŏ��̃��e�����̐擪�����l�Ȃ��Exception

                        char betweenChar = str.charAt(0);
                        if ((betweenChar >= '0') && (betweenChar <= '9')) {
                            throw new ParseException(
                                    "���e���������Ɛ��l�v���[�X�z���_���A�����Ă��܂��B[ " + line
                                            + " ](Pos=" + cellBeginPos + ")",
                                    cellBeginPos);
                        }
                    }

                }

                statements.add(literalStatement);

            }
        }

        if ((yearType != SET_NONE) && (!monthSeted) && daySeted) {
            //�N�Ɠ��̎w��͂���̂ɂ��̎w�肪�Ȃ�
            throw new ParseException("���v���[�X�z���_���w�肹���ɁA�N�Ɠ��̃v���[�X�z���_�͓����w��ł��܂���B[ "
                    + line + " ])", 0);
        }
        if (hourSeted&& (!minuteSeted) && secondSeted) {
            //�N�Ɠ��̎w��͂���̂ɂ��̎w�肪�Ȃ�
            throw new ParseException("���v���[�X�z���_���w�肹���ɁA���ƕb�̃v���[�X�z���_�͓����w��ł��܂���B[ "
                    + line + " ])", 0);
        }
    }

}