package jp.nichicom.ac.text;

import java.util.ArrayList;

import javax.swing.text.JTextComponent;

import jp.nichicom.ac.ACConstants;

/**
 * テキスト関連の汎用メソッドを集めたクラスです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 */
public class ACTextUtilities {
    /**
     * バージョン情報における規定の区切り文字です。
     */
    public final static String DEFAULT_VERSION_SEPARATER = "\\.";

    private static ACTextUtilities singleton;

    /**
     * ドット(.)区切りのバージョン情報を比較し、比較結果を返します。
     * <p>
     * 比較演算子には、以下のいずれかを指定します。<br/>
     * &lt;　　：　より大きい<br/>
     * &gt;　　：　より小さい<br/>
     * &lt;=　：　以上<br/>
     * &gt;=　：　以下<br/>
     * ==　：　一致<br/>
     * !=　 ：　不一致
     * </p>
     * <p>
     * バージョンは区切りごとに数値に変換して大小比較を行います。
     * </p>
     * 
     * @param now 現在のバージョン情報
     * @param operation 比較演算子
     * @param value 比較対象のバージョン情報
     * @return 更新が必要であるか
     */
    public static boolean compareVersionText(String now, String operation, String value) {
        return compareVersionText(now, operation, value, DEFAULT_VERSION_SEPARATER);
    }

    /**
     * 指定文字区切りのバージョン情報を比較し、比較結果を返します。
     * <p>
     * 比較演算子には、以下のいずれかを指定します。<br/>
     * &lt;　　：　より大きい<br/>
     * &gt;　　：　より小さい<br/>
     * &lt;=　：　以上<br/>
     * &gt;=　：　以下<br/>
     * ==　：　一致<br/>
     * !=　 ：　不一致
     * </p>
     * <p>
     * バージョンの区切り文字には、正規表現形式で区切り文字を指定します。<br/>
     * （"."区切りの場合は、"\."を指定します。)
     * </p>
     * <p>
     * バージョンは区切りごとに数値に変換して大小比較を行います。
     * </p>
     * 
     * @param now 現在のバージョン情報
     * @param operation 比較演算子
     * @param value 比較対象のバージョン情報
     * @param versionSeparatoer バージョンの区切り文字
     * @return 更新が必要であるか
     */
    public static boolean compareVersionText(String now, String operation, String value, String versionSeparatoer) {
        if ((now == null) || "".equals(now) || (value == null)
                || "".equals(value)) {
            // NULLチェック
            return false;
        }

        String[] nows = now.split(versionSeparatoer);
        String[] values = value.split(versionSeparatoer);
        int nowLen = nows.length;
        int valLen = values.length;
        int maxLen = Math.max(nowLen, valLen);
        if (maxLen == 0) {
            return false;
        }

        // マイナーバージョンまでそろえる
        int[] nowVers = new int[maxLen];
        int[] valVers = new int[maxLen];
        for (int i = 0; i < nowLen; i++) {
            nowVers[i] = Integer.parseInt(nows[i]);
        }
        for (int i = 0; i < valLen; i++) {
            valVers[i] = Integer.parseInt(values[i]);
        }

        // 比較開始
        // 完全一致か検査
        boolean equal = true;
        for (int i = 0; i < maxLen; i++) {
            if (nowVers[i] != valVers[i]) {
                equal = false;
                break;
            }
        }

        if (operation.indexOf("!") >= 0) {
            // 一致しなければ更新対象
            if (!equal) {
                return true;
            }
        } else {
            if (operation.indexOf("=") >= 0) {
                // 一致したら更新対象
                if (equal) {
                    return true;
                }
            } else {
                // 一致は更新対象でないのに一致していた場合は対象外
                if (equal) {
                    return false;
                }
            }
            if (operation.indexOf("<") >= 0) {
                // 現在のバージョンのほうが低ければ更新対象
                boolean match = true;
                for (int i = 0; i < maxLen; i++) {
                    if (nowVers[i] < valVers[i]) {
                        break;
                    }
                    if (nowVers[i] > valVers[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
            if (operation.indexOf(">") >= 0) {
                // 現在のバージョンのほうが高ければ更新対象
                boolean match = true;
                for (int i = 0; i < maxLen; i++) {
                    if (nowVers[i] > valVers[i]) {
                        break;
                    }
                    if (nowVers[i] < valVers[i]) {
                        match = false;
                        break;
                    }
                }
                if (match) {
                    return true;
                }
            }
        }

        return false;
    }

//    /**
//     * インスタンスを取得します。
//     * 
//     * @deprecated 直接staticメソッドを呼んでください。
//     * @return インスタンス
//     */
//    public static ACTextUtilities getInstance() {
//        if (singleton == null) {
//            singleton = new ACTextUtilities();
//        }
//        return singleton;
//    }

    /**
     * 配列を改行文字で結合します。
     * 
     * @param array 配列
     * @return 整形した文字列
     */
    public static String concatLineWrap(String[] array) {
        int end = array.length;
        if (end > 1) {
            // 2行以上ならば改行文字で結合
            StringBuffer sb = new StringBuffer();
            sb.append(array[0]);
            for (int i = 1; i < end; i++) {
                sb.append(ACConstants.LINE_SEPARATOR);
                sb.append(array[i]);
            }
            // 改行コードで結合した文字列を返す
            return sb.toString();
        } else if (end == 1) {
            // 1行
            return array[0];
        } else {
            // 0行
            return "";
        }
    }

    /**
     * バイト単位で、改行文字と折り返し文字数を基準に切り分けた配列を再度改行文字で結合した結果を返します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @return 整形した文字列
     */
    public static String concatLineWrapOnByte(String text, int columns) {
        return concatLineWrap(separateLineWrapOnByte(text, columns));
    }

    /**
     * 文字単位で、改行文字と折り返し文字数を基準に切り分けた配列を再度改行文字で結合した結果を返します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @return 整形した文字列
     */
    public static String concatLineWrapOnChar(String text, int columns) {
        return concatLineWrap(separateLineWrapOnChar(text, columns));
    }

    /**
     * 文字列がNullまたは空文字であるかを返します。
     * 
     * @param comp 評価文字列をgetTextで取得可能なコンポーネント
     * @return 文字列がNullまたは空文字であるか
     */
    public static boolean isNullText(JTextComponent comp) {
        return isNullText(comp.getText());
    }

    /**
     * 文字列がNullまたは空文字であるかを返します。
     * 
     * @param obj 評価文字列
     * @return 文字列がNullまたは空文字であるか
     */
    public static boolean isNullText(Object obj) {
        if (obj == null) {
            return true;
        }
        String text = String.valueOf(obj);
        if (text == null) {
            return true;
        }
        final char HALF_SPACE = ' ';
        final char FULL_SPACE = '　';
        int i;
        int end = text.length();
        for (i = 0; i < end; i++) {
            char c = text.charAt(i);
            if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
                break;
            }
        }
        if (i >= end) {
            return true;
        }
        return false;
    }

    /**
     * 改行文字を基準に切り分けた配列を返します。
     * 
     * @param text 変換対象
     * @return 切り分けた配列
     */
    public static String[] separateLineWrap(String text) {
        return separateLineWrapImpl(text, Integer.MAX_VALUE);
    }
    /**
     * 改行文字を基準に切り分けた配列を返します。
     * 
     * @param text 変換対象
     * @param limit 最大分割数
     * @return 切り分けた配列
     */
    public static String[] separateLineWrapLimit(String text, int limit) {
        return separateLineWrapImpl(text, limit);
    }
    /**
     * バイト単位で、改行文字と折り返し文字数を基準に切り分けた配列を返します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @return 切り分けた配列
     */
    public static String[] separateLineWrapOnByte(String text, int columns) {
        ArrayList lines = new ArrayList();

        StringBuffer sb = new StringBuffer();
        int count = 0;
        int end = text.length();
        boolean wrap = false;
        boolean justColumnWrap = false;
        char lastWraper = 0;
        for (int i = 0; i < end; i++) {
            // 1文字ずつ取り出して走査
            char c = text.charAt(i);
            if ((c == '\r') || (c == '\n')) {
                // 改行文字
                if (wrap && (lastWraper != c)) {
                    // 改行状態かつ異なる改行文字（2文字からなる改行文字）
                    // →改行状態解除
                    wrap = false;
                } else {
                    if(!justColumnWrap){
                        lines.add(sb.toString());
                        sb = new StringBuffer();
                        count = 0;
                    }
                    wrap = true;
                    lastWraper = c;
                }
                justColumnWrap = false;
                continue;
            } else {
                // 改行文字以外→改行状態を解除
                wrap = false;
                justColumnWrap = false;
            }
            int byteSize =text.substring(i, i + 1).getBytes().length; 
            count += byteSize;
            if (columns >= count) {
                //合計バイト数が1行のバイト数以下であれば行内として追加
                sb.append(c);
            }
            if (columns <= count) {
                // 折り返し
                lines.add(sb.toString());
                sb = new StringBuffer();
                if (columns < count) {
                    //行のバイト数と同じではなく超過した場合(残り1バイトに対して2バイト文字を与えた)は次行に送る
                    sb.append(c);
                    count = byteSize;
                }else{
                    count = 0;
                }
                justColumnWrap = true;
            }
        }
        // 末行を追加
        String line = sb.toString();
        if (line.length() > 0) {
            lines.add(line);
        }

        Object[] array = lines.toArray();
        end = array.length;
        String[] result = new String[end];
        System.arraycopy(array, 0, result, 0, end);
        return result;
    }

    /**
     * 文字単位で、改行文字と折り返し文字数を基準に切り分けた配列を返します。
     * 
     * @param text 変換対象
     * @param columns 折り返し文字数
     * @return 切り分けた配列
     */
    public static String[] separateLineWrapOnChar(String text, int columns) {
        ArrayList lines = new ArrayList();

        StringBuffer sb = new StringBuffer();
        int count = 0;
        int end = text.length();
        boolean wrap = false;
        boolean justColumnWrap = false;
        char lastWraper = 0;
        for (int i = 0; i < end; i++) {
            // 1文字ずつ取り出して走査
            char c = text.charAt(i);
            if ((c == '\r') || (c == '\n')) {
                // 改行文字
                if (wrap && (lastWraper != c)) {
                    // 改行状態かつ異なる改行文字（2文字からなる改行文字）
                    // →改行状態解除
                    wrap = false;
                } else {
                    if(!justColumnWrap){
                        lines.add(sb.toString());
                        sb = new StringBuffer();
                        count = 0;
                    }
                    wrap = true;
                    lastWraper = c;
                }
                justColumnWrap = false;
                continue;
            } else {
                // 改行文字以外→改行状態を解除
                wrap = false;
                justColumnWrap = false;
            }
            sb.append(c);
            count++;
            if (columns <= count) {
                // 折り返し
                lines.add(sb.toString());
                sb = new StringBuffer();
                count = 0;
                justColumnWrap = true;
            }
        }
        // 末行を追加
        String line = sb.toString();
        if (line.length() > 0) {
            lines.add(line);
        }

        Object[] array = lines.toArray();
        end = array.length;
        String[] result = new String[end];
        System.arraycopy(array, 0, result, 0, end);
        return result;
    }

    /**
     * nullならば空文字を返し、それ以外はそのまま返します。
     * 
     * @param src 比較文字列
     * @return 変換結果
     */
    public static String toBlankIfNull(String src) {
        if (src == null) {
            return "";
        }
        return src;
    }

    /**
     * 前後の半角・全角空白を除去して返します。
     * 
     * @param src 変換元
     * @return 空白除去結果
     */
    public static String trim(String src) {
        if (src == null) {
            return null;
        }

        final char HALF_SPACE = ' ';
        final char FULL_SPACE = '　';
        int i;
        int end = src.length();
        for (i = 0; i < end; i++) {
            char c = src.charAt(i);
            if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
                for (int j = end - 1; i <= j; j--) {
                    c = src.charAt(j);
                    if ((c != HALF_SPACE) && (c != FULL_SPACE)) {
                        if (i == 0) {
                            if (j == end - 1) {
                                // 加工なし
                                return src;
                            }
                            // 後半trim
                            return src.substring(0, j + 1);
                        }
                        if (j == end - 1) {
                            // 前半trim
                            return src.substring(i);
                        }
                        // 前後のtrim
                        return src.substring(i, j + 1);
                    }
                }
                return src;
            }
        }
        //すべて空白
        return "";
    }

    
    /**
     * 改行文字を基準に切り分けた配列を返します。
     * 
     * @param text 変換対象
     * @param limit 最大分割数
     * @return 切り分けた配列
     */
    protected static String[] separateLineWrapImpl(String text, int limit) {
        ArrayList lines = new ArrayList();

        StringBuffer sb = new StringBuffer();
        int count = 0;
        int end = text.length();
        boolean wrap = false;
        boolean justColumnWrap = false;
        char lastWraper = 0;
        for (int i = 0; i < end; i++) {
            // 1文字ずつ取り出して走査
            char c = text.charAt(i);
            if ((c == '\r') || (c == '\n')) {
                // 改行文字
                if (wrap && (lastWraper != c)) {
                    // 改行状態かつ異なる改行文字（2文字からなる改行文字）
                    // →改行状態解除
                    wrap = false;
                } else {
                    if(!justColumnWrap){
                        lines.add(sb.toString());
                        sb = new StringBuffer();
                        count = 0;
                        if(lines.size()>=limit){
                            //分割限界
                            int lastIndex= lines.size()-1;
                            lines.set(lastIndex, lines.get(lastIndex)+text.substring(i));
                            break;
                        }
                    }
                    wrap = true;
                    lastWraper = c;
                }
                justColumnWrap = false;
                continue;
            } else {
                // 改行文字以外→改行状態を解除
                wrap = false;
                justColumnWrap = false;
            }
            sb.append(c);
            count++;
        }
        // 末行を追加
        String line = sb.toString();
        if (line.length() > 0) {
            lines.add(line);
        }

        Object[] array = lines.toArray();
        end = array.length;
        String[] result = new String[end];
        System.arraycopy(array, 0, result, 0, end);
        return result;
    }    
    /**
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected ACTextUtilities() {

    }

}
