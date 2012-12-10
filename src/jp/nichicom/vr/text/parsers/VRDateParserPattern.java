/** TODO <HEAD> */
package jp.nichicom.vr.text.parsers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Locale;


/**
 * VRDateParserが使用する構文パターン解析クラスです。
 * <p>
 * 文字列をパターン単位で解釈・翻訳し、対応する日時を返します。
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
     * 検査文字の連続数を返します。
     * 
     * @param target 走査対象
     * @param i カウンタ
     * @param len カウンタ終端
     * @param token 検査文字
     * @return 連続数(1..len-i)
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
        //超過分を戻す
        return cnt;
    }

    private String pattern;

    private ArrayList statements;

    /**
     * コンストラクタ
     */
    public VRDateParserPattern() {
        this.statements = new ArrayList();
    }

    /**
     * 文字列パターンを返します。
     * 
     * @return 文字列パターン
     */
    public String getPattern() {
        return pattern;
    }

    /**
     * 構文集合を返します。
     * 
     * @return 構文集合
     */
    public ArrayList getStatements() {
        return statements;
    }

    /**
     * 文字列を解析して日付として返します。
     * 
     * @param target 解析対象
     * @param eras 対称ロケールの元号集合
     * @param locale 現在日時取得に利用するロケール
     * @return 日付
     * @throws ParseException 解析失敗
     */
    public Calendar match(String target, ArrayList eras, Locale locale)
            throws ParseException {
        int len = target.length();
        if (len == 0) {
            throw new ParseException("長さ0の文字列解析に失敗", 0);
        }

        //現在日時
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
                //構文規則に適合しなかった
                return null;
            }
            //解析終了位置を次の解析位置として転記
            parseOption.setParseBeginIndex(parseOption.getParseEndIndex());
        }

        if (parseOption.getParseEndIndex() < len) {
            //対応付いていない文字列がある
            return null;
        }

        if ((parseOption.getMachedEra() == null) && (parseOption.getEra() > 0)) {
            //元号指定なしだが元号年は指定済み → 現在に対応する元号を選択

            Iterator eraIt = eras.iterator();
            while (eraIt.hasNext()) {
                VRDateParserEra eraItem = (VRDateParserEra) eraIt.next();
                long nowMilllis = parseOption.getNow().getTimeInMillis();
                if ((nowMilllis >= eraItem.getBegin().getTimeInMillis())
                        && (nowMilllis <= eraItem.getEnd().getTimeInMillis())) {
                    //現時点を範囲内とする元号を発見
                    parseOption.setMachedEra(eraItem);
                    break;
                }
            }
            if (parseOption.getMachedEra() == null) {
                //現在を表す元号が指定されていなかった
                throw new ParseException("現在の日付を含む元号が定義されていません。[ " + target
                        + " ]", 0);
            }
        }

        Calendar pos;
        if (parseOption.getMachedEra() != null) {
            //元号指定あり

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
                //明示的に元号年を指定しておきながらかつ元号年が0以下
                throw new ParseException("元号年に0以下が指定されています。[ " + target + " ]",
                        0);
            }
            parseOption.setYear(eraBeginYear);
            if (parseOption.getEra() > 0) {
                //元号年あり
                parseOption.setYear(parseOption.getYear()
                        + parseOption.getEra() - 1);
            }
            if (eraEndYear < parseOption.getYear()) {
                throw new ParseException("指定の日付が元号の範囲内にありません。[ " + target
                        + " ](Era=" + eraBegin + "-" + eraEnd + ")", 0);
            }

            pos = Calendar.getInstance();
            pos.setLenient(false);
//            pos.setTimeInMillis(0);
            pos.clear();
            if (parseOption.isUseMonth()) {
                //月指定あり

                if (parseOption.isUseDay()) {
                    //日指定あり
                    pos.set(parseOption.getYear(), parseOption.getMonth() - 1,
                            parseOption.getDay());
                } else {
                    //日指定なし
                    pos.set(parseOption.getYear(), parseOption.getMonth() - 1,
                            1);
                    if (pos.before(eraBeginCal)) {
                        //指定年月の1日は元号範囲を下回る
                        pos.set(parseOption.getYear(),
                                parseOption.getMonth() - 1, eraBeginDay);
                    }
                }
            } else {
                //月指定なし
                pos.set(parseOption.getYear(), eraBeginMonth, eraBeginDay);

                if (pos.after(eraEndCal)) {
                    //指定年に元号開始月日を合算すると、元号範囲を超える
                    pos.set(parseOption.getYear(), 1, 1);
                }
            }
            
//            return pos;
        } else {
            //元号指定なし

            if (!parseOption.isUseYear()) {
                //年指定なし
                parseOption.setYear(parseOption.getNow().get(Calendar.YEAR));
            }

            if (!parseOption.isUseMonth()) {
                //月指定なし
                parseOption.setMonth(1);
            }

            if (!parseOption.isUseDay()) {
                //日指定なし
                parseOption.setDay(1);
            }

            //日付範囲チェック
            parseOption.getNow().setLenient(false);
            parseOption.getNow().setTimeInMillis(0);
            parseOption.getNow().set(parseOption.getYear(), parseOption.getMonth() - 1,
                    parseOption.getDay());
//            return parseOption.getNow();
            pos = parseOption.getNow();
        }

        if (!parseOption.isUseHour()) {
            //時指定なし
            parseOption.setHour(0);
        }
        if (!parseOption.isUseMinute()) {
            //分指定なし
            parseOption.setMinute(0);
        }
        if (!parseOption.isUseSecond()) {
            //秒指定なし
            parseOption.setSecond(0);
        }
        pos.set(Calendar.HOUR_OF_DAY, parseOption.getHour());
        pos.set(Calendar.MINUTE, parseOption.getMinute());
        pos.set(Calendar.SECOND, parseOption.getSecond());
        
        return pos;
    }

    /**
     * 文字列パターンを設定します。
     * 
     * @param pattern 文字列パターン
     */
    public void setPattern(String pattern) throws ParseException {
        this.pattern = pattern;
        parse();
    }

    /**
     * 構文集合を設定します。
     * 
     * @param statements 構文集合
     */
    public void setStatements(ArrayList statements) {
        this.statements = statements;
    }

    /**
     * 文字列パターンを解析します。
     * 
     * @throws ParseException 書式の解析失敗
     */
    protected void parse() throws ParseException {

        //年と元号の混在
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
        //クォートでくくっているか
        final int NONE = 0;
        final int QUOTING = 1;
        final int QUOTED = 2;
        int quoteStatus = NONE;
        //クォートをエスケープしたか
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
                    //クォート以降が切り出し対象
                    cellBeginPos = i + 1;
                    quoteStatus = QUOTING;
                    quoteEscaped = false;
                    break;
                case QUOTING:
                    //クォート中のクォート → 直前までが切り出し対象
                    cellEndPos = i - 1;
                    quoteStatus = QUOTED;
                    break;
                case QUOTED:
                    //クォート終了と思ったのに続けてクォート → クォートエスケープ
                    cellEndPos = i - 1;
                    quoteStatus = QUOTING;
                    quoteEscaped = true;
                    break;
                }
                //switch後の処理は行わず、次の文字を走査する
                continue;
            }
            if (quoteStatus == QUOTING) {
                //クォート中 → スキップ
                continue;
            }

            VRDateParserStatementable nextStatement;
            int contCnt = 0;
            switch (line.charAt(i)) {
            case VRDateParserStatementNumber.YEAR:
                if (yearType == SET_ERA) {
                    //元号を設定済み
                    throw new ParseException("年と元号が混在しています。[ " + line
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
                    //元号を設定済み
                    throw new ParseException("年と元号が混在しています。[ " + line
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
                    //元号を設定済み
                    throw new ParseException("年と元号が混在しています。[ " + line
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
                //リテラル
                //switch後の処理は行わず、次の文字を走査する
                continue;
            }

            VRDateParserStatementLiteral literalStatement = null;
            switch (quoteStatus) {
            case NONE:
                //クォートなしの状態でリテラル終了 → 直前までが切り出し対象
                cellEndPos = i;
                if (cellBeginPos < cellEndPos) {
                    literalStatement = new VRDateParserStatementLiteral(line
                            .substring(cellBeginPos, cellEndPos));
                }
                break;
            case QUOTED:
                //クォート済みの状態でリテラル終了 → クォートの直前までが切り出し対象
                if (cellBeginPos < cellEndPos) {

                    if (quoteEscaped) {
                        //クォートエスケープを行った → [""]を["]に置換
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
            //プレースホルダの連続を除き、リテラル→プレースホルダの順で出現する

            if (literalStatement != null) {
                //リテラル→プレースホルダの順で検出
                String str = literalStatement.getText();
                if (statements.size() > 0) {
                    VRDateParserStatementable frontStatement = (VRDateParserStatementable) getStatements()
                            .get(statements.size() - 1);
                    if (frontStatement instanceof VRDateParserStatementNumber) {
                        //前が数値で次のリテラルの先頭が数値ならばException

                        char betweenChar = str.charAt(0);
                        if ((betweenChar >= '0') && (betweenChar <= '9')) {
                            throw new ParseException(
                                    "リテラル文字と数値プレースホルダが連続しています。[ " + line
                                            + " ](Pos=" + cellBeginPos + ")",
                                    cellBeginPos);
                        }
                    }

                }

                //次が数値で前のリテラルの末尾が数値ならばException
                if (nextStatement instanceof VRDateParserStatementNumber) {

                    char betweenChar = str.charAt(str.length() - 1);
                    if ((betweenChar >= '0') && (betweenChar <= '9')) {
                        throw new ParseException("リテラル文字と数値プレースホルダが連続しています。[ "
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
            //リテラル文字で終了した場合
            VRDateParserStatementLiteral literalStatement = null;
            switch (quoteStatus) {
            case NONE:
                //クォートなしの状態でリテラル終了 → 直前までが切り出し対象
                cellEndPos = len;
                if (cellBeginPos < cellEndPos) {
                    literalStatement = new VRDateParserStatementLiteral(line
                            .substring(cellBeginPos, cellEndPos));
                }
                break;
            case QUOTED:
                //クォート済みの状態でリテラル終了 → クォートの直前までが切り出し対象
                if (cellBeginPos < cellEndPos) {

                    if (quoteEscaped) {
                        //クォートエスケープを行った → [""]を["]に置換
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
                //リテラル→プレースホルダの順で検出
                String str = literalStatement.getText();
                if (statements.size() > 0) {
                    VRDateParserStatementable frontStatement = (VRDateParserStatementable) getStatements()
                            .get(statements.size() - 1);
                    if (frontStatement instanceof VRDateParserStatementNumber) {
                        //前が数値で次のリテラルの先頭が数値ならばException

                        char betweenChar = str.charAt(0);
                        if ((betweenChar >= '0') && (betweenChar <= '9')) {
                            throw new ParseException(
                                    "リテラル文字と数値プレースホルダが連続しています。[ " + line
                                            + " ](Pos=" + cellBeginPos + ")",
                                    cellBeginPos);
                        }
                    }

                }

                statements.add(literalStatement);

            }
        }

        if ((yearType != SET_NONE) && (!monthSeted) && daySeted) {
            //年と日の指定はあるのにつきの指定がない
            throw new ParseException("月プレースホルダを指定せずに、年と日のプレースホルダは同時指定できません。[ "
                    + line + " ])", 0);
        }
        if (hourSeted&& (!minuteSeted) && secondSeted) {
            //年と日の指定はあるのにつきの指定がない
            throw new ParseException("分プレースホルダを指定せずに、時と秒のプレースホルダは同時指定できません。[ "
                    + line + " ])", 0);
        }
    }

}