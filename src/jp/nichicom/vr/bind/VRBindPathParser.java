/** TODO <HEAD> */
package jp.nichicom.vr.bind;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

/**
 * バインドパス解析クラスです。
 * <p>
 * XMLにおけるXPathのように、VRBindSource中の任意の位置を特定・解析する機能を有します。
 * </p>
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/10/31
 * @see VRBindSource
 */
public class VRBindPathParser {

    /**
     * 既存階層を示す要素定数です。
     */
    public static final Integer CURRENT_ELEMENT = new Integer(0);

    /**
     * エスケープシーケンスを表す文字定数です。
     */
    protected static final char ESCAPE = '\\';

    /**
     * 指定したキーに従って、ソースからデータを取り出します。
     * 
     * @param key キー
     * @param source ルートソース
     * @return 失敗した場合はfalse
     * @throws ParseException パス解析例外
     */
    public static Object get(Object key, VRBindSource source)
            throws ParseException {
        if (source == null) {
            return null;
        }
        String keyText=String.valueOf(key);
        if(has(keyText, source)){
            //文字列化して一致する場合は優先
            return get(keyText, source);
        }
        return source.getData(key);
    }

    /**
     * 指定したパスに従って、ソースからデータを取り出します。
     * <p>
     * パスは以下のトークンによって区切られます。 <br />
     * "/"：階層を一段階落とします。 <br />
     * "."：現在の要素を表します。 <br />
     * [n]：n番目の下位要素を表します。 <br />
     * その他任意の文字列：文字列をキーとする下位要素を表します。
     * </p>
     * <p>
     * 【バインドパスの例】 <br />
     * "Node/Leaf"：ソース内のキー「Node」で特定される要素内のキー「Leaf」で特定される要素 <br />
     * "[1]"：ソース内の添え字[1]で特定される要素 <br />
     * "."：ソースそれ自身
     * </p>
     * 
     * @param path 指定パス
     * @param source ルートソース
     * @return データ
     * @throws ParseException パス解析例外
     */
    public static Object get(String path, VRBindSource source)
            throws ParseException {
        return getSource(path, source);
    }

    /**
     * 指定したキーの値が存在するかを返します。
     * 
     * @param key キー
     * @param source ルートソース
     * @return 指定したキーの値が存在するか
     * @throws ParseException パス解析例外
     */
    public static boolean has(Object key, VRBindSource source)
            throws ParseException {
        if (source instanceof Map) {
            return ((Map) source).containsKey(key);
        }
        if (key instanceof Integer) {
            return ((Integer) key).intValue() < source.getDataSize();
        }

        return false;

    }

    /**
     * 指定したパスのデータが存在するかを返します。
     * <p>
     * パスは以下のトークンによって区切られます。 <br />
     * "/"：階層を一段階落とします。 <br />
     * "."：現在の要素を表します。 <br />
     * [n]：n番目の下位要素を表します。 <br />
     * その他任意の文字列：文字列をキーとする下位要素を表します。
     * </p>
     * <p>
     * 【バインドパスの例】 <br />
     * "Node/Leaf"：ソース内のキー「Node」で特定される要素内のキー「Leaf」で特定される要素 <br />
     * "[1]"：ソース内の添え字[1]で特定される要素 <br />
     * "."：ソースそれ自身
     * </p>
     * 
     * @param path 指定パス
     * @param source ルートソース
     * @return 指定したパスのデータが存在するか
     * @throws ParseException パス解析例外
     */
    public static boolean has(String path, VRBindSource source)
            throws ParseException {

        if (source == null) {
            return false;
        }

        ArrayList elements = parsePath(path);
        if (elements.isEmpty()) {
            return false;
        }

        // 解析完了
        VRBindSource currentSource = source;
        int end = elements.size();
        for (int i = 0; i < end; i++) {
            Object element = elements.get(i);

            if (element == CURRENT_ELEMENT) {
                continue;
            }

            if (element instanceof Integer) {
                element = currentSource.getData(((Integer) element).intValue());
            } else {
                if (currentSource instanceof Map) {
                    if (!((Map) currentSource).containsKey(element)) {
                        return false;
                    }
                }
                element = currentSource.getData(element);
            }

            if (element instanceof VRBindSource) {
                currentSource = (VRBindSource) element;
            } else {
                if (i + 1 >= end) {
                    // 最終要素であればtrue
                    return true;
                }
                return false;
            }
        }

        return true;
    }

    /**
     * 指定したキーに従って、ソースにデータを設定します。
     * 
     * @param key キー
     * @param source ルートソース
     * @param data データ
     * @return 失敗した場合はfalse
     * @throws ParseException パス解析例外
     */
    public static boolean set(Object key, VRBindSource source, Object data)
            throws ParseException {
        if (source == null) {
            return false;
        }
        source.setData(key, data);
        return true;
    }

    /**
     * 指定したパスに従って、ソースにデータを設定します。
     * <p>
     * 指定パスが存在しない場合、1階層までなら新たに作成します。<br />
     * ※2階層以上の場合、親階層を配列とすべきかハッシュとすべきか曖昧になるため自動作成せず例外を発行します。
     * </p>
     * <p>
     * パスは以下のトークンによって区切られます。 <br />
     * "/"：階層を一段階落とします。 <br />
     * "."：現在の要素を表します。 <br />
     * [n]：n番目の下位要素を表します。 <br />
     * その他任意の文字列：文字列をキーとする下位要素を表します。
     * </p>
     * <p>
     * 【バインドパスの例】 <br />
     * "Node/Leaf"：ソース内のキー「Node」で特定される要素内のキー「Leaf」で特定される要素 <br />
     * "[1]"：ソース内の添え字[1]で特定される要素 <br />
     * "."：ソースそれ自身
     * </p>
     * 
     * @param path 指定パス
     * @param source ルートソース
     * @param data データ
     * @return 失敗した場合はfalse
     * @throws ParseException パス解析例外
     */
    public static boolean set(String path, VRBindSource source, Object data)
            throws ParseException {

        ArrayList elements = parsePath(path);
        if ((elements == null) || elements.isEmpty()) {
            return false;
        }

        // 階層解析
        Object obj = getLastSource(path, source, elements);
        if (!(obj instanceof VRBindSource)) {
            return false;
        }

        source = (VRBindSource) obj;

        // 最終階層の要素に設定する
        Object element = elements.get(elements.size() - 1);
        if (element instanceof Integer) {
            source.setData(((Integer) element).intValue(), data);
        } else {
            source.setData(element, data);
        }

        return true;
    }

    /**
     * バインドパスを解析し、最終階層の要素を返します。
     * 
     * @param path 指定パス
     * @param source ルートソース
     * @param elements パス集合
     * @return 最終階層の要素
     * @throws ParseException パス解析例外
     */
    protected static VRBindSource getLastSource(String path,
            VRBindSource source, ArrayList elements) throws ParseException {

        int last = elements.size() - 1;
        if ((elements == null) || (last <= 0)) {
            return source;
        }

        if (!(source instanceof VRBindSource)) {
            return null;
        }

        // 解析
        VRBindSource currentSource = source;
        for (int i = 0; i < last; i++) {
            Object element = elements.get(i);

            if (element == CURRENT_ELEMENT) {
                continue;
            }

            if (element instanceof Integer) {
                element = currentSource.getData(((Integer) element).intValue());
            } else {
                element = currentSource.getData(element);
            }

            if (element instanceof VRBindSource) {
                currentSource = (VRBindSource) element;
            } else {
                throw new ParseException(path, i);
            }
        }
        return currentSource;
    }

    /**
     * 指定したパスが指すバインドソースを返します。
     * 
     * @param path 指定パス
     * @param source ルートソース
     * @return バインドソース
     * @throws ParseException パス解析例外
     */
    protected static Object getSource(String path, VRBindSource source)
            throws ParseException {

        ArrayList elements = parsePath(path);
        if ((elements == null) || elements.isEmpty()) {
            return null;
        }

        // 階層解析
        Object obj = getLastSource(path, source, elements);
        if (!(obj instanceof VRBindSource)) {
            return null;
        }

        source = (VRBindSource) obj;

        // 最終階層の要素から取得する
        Object element = elements.get(elements.size() - 1);
        if (element instanceof Integer) {
            return source.getData(((Integer) element).intValue());
        } else {
            return source.getData(element);
        }

    }

    /**
     * 文字列を解析してパス要素を数値か文字列で返します。
     * 
     * @param element 文字列
     * @return パス要素
     * @throws ParseException 解析例外
     */
    protected static Object parse(String element) throws ParseException {
        final char BEGIN_BRAKET = '[';
        final String CURRENT_DOT = ".";

        if ((element.length()==0)|| CURRENT_DOT.equals(element)) {
            // .
            return CURRENT_ELEMENT;
        }else if (element.charAt(0) == BEGIN_BRAKET) {
            // 数字
            return Integer.valueOf(element.substring(1, element.length() - 1));
        } else {
            // その他の文字列
            StringBuffer sb = new StringBuffer();

            int end = element.length();
            for (int i = 0; i < end; i++) {
                char current = element.charAt(i);
                if (current == ESCAPE) {
                    i++;
                    if (i >= end) {
                        // 末尾のエスケープは無視
                        continue;
                    }
                    current = element.charAt(i);
                }
                sb.append(current);
            }
            return sb.toString();

        }

    }

    /**
     * 指定したバインドパスを解析して要素集合として返します。
     * <p>
     * パスは以下のトークンによって区切られます。 <br />
     * "/"：階層を一段階落とします。 <br />
     * "."：現在の要素を表します。 <br />
     * [n]：n番目の下位要素を表します。 <br />
     * その他任意の文字列：文字列をキーとする下位要素を表します。
     * </p>
     * <p>
     * 【バインドパスの例】 <br />
     * "Node/Leaf"：ソース内のキー「Node」で特定される要素内のキー「Leaf」で特定される要素 <br />
     * "[1]"：ソース内の添え字[1]で特定される要素 <br />
     * "."：ソースそれ自身
     * </p>
     * 
     * @param path バインドパス
     * @return 要素集合
     * @throws ParseException パス解析例外
     */
    public static ArrayList parsePath(String path) throws ParseException {
        ArrayList elements = new ArrayList();
        if ((path == null) || ("".equals(path))) {
            return elements;
        }

        final char TOKEN = '/';

        StringBuffer sb = new StringBuffer();

        int end = path.length();
        for (int i = 0; i < end; i++) {
            char current = path.charAt(i);
            switch (current) {
            case ESCAPE:
                if (i + 1 >= end) {
                    // 末尾のエスケープは無視
                    continue;
                } else if (path.charAt(i + 1) == TOKEN) {
                    // トークンエスケープ
                    sb.append(path.substring(i, i + 2));
                    i++;
                    continue;
                }
                break;
            case TOKEN:
                // 切り出す
                elements.add(parse(sb.toString()));

                sb = new StringBuffer();
                continue;
            }
            sb.append(current);

        }

        String last = sb.toString();
        if (!("".equals(last))) {
            // 最後の要素を追加
            elements.add(parse(last));
        }
        return elements;
    }

    /**
     * コンストラクタです。
     * <p>
     * Singleton Pattern
     * </p>
     */
    protected VRBindPathParser() {

    }

}