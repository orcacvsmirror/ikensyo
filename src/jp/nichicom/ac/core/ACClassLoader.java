package jp.nichicom.ac.core;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;
import java.security.AccessController;
import java.security.CodeSource;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.jar.Attributes;
import java.util.jar.Manifest;
import java.util.jar.Attributes.Name;

import sun.misc.Resource;
import sun.misc.URLClassPath;

/**
 * 暗号化クラスローダです。
 * <p>
 * Copyright (c) 2005 Nippon Computer Corporation. All Rights Reserved.
 * </p>
 * 
 * @author Tozo Tanaka
 * @version 1.0 2005/12/01
 * @see URLClassLoader
 */
public class ACClassLoader extends URLClassLoader {
    /**
     * カスタムロードを考慮しつつ指定クラスのメイン関数を実行します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @throws Throwable 処理例外
     */
    public static void executeMain(String className) throws Throwable {
        executeMain(className, new URL[] {}, new String[] {}, new String[] {});
    }

    /**
     * カスタムロードを考慮しつつ指定クラスのメイン関数を実行します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @param addClassPathes 追加するクラスパス
     * @throws Throwable 処理例外
     */
    public static void executeMain(String className, URL[] addClassPathes)
            throws Throwable {
        executeMain(className, addClassPathes, new String[] {}, new String[] {});
    }

    /**
     * カスタムロードを考慮しつつ指定クラスのメイン関数を実行します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @param addClassPathes 追加するクラスパス
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     * @throws Throwable 処理例外
     */
    public static void executeMain(String className, URL[] addClassPathes,
            String[] customLoadPackages) throws Throwable {
        executeMain(className, addClassPathes, customLoadPackages,
                new String[] {});
    }

    /**
     * カスタムロードを考慮しつつ指定クラスのメイン関数を実行します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @param args main実行引数
     * @param addClassPathes 追加するクラスパス
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     * @throws Throwable 処理例外
     */
    public static void executeMain(String className, URL[] addClassPathes,
            String[] customLoadPackages, String[] args) throws Throwable {
        Object obj = getClass(className, addClassPathes, customLoadPackages);

        if (obj instanceof Class) {
            Class cls = (Class) obj;
            obj = cls.newInstance();
            Method m = cls.getDeclaredMethod("main",
                    new Class[] { String[].class });
            m.invoke(m, new Object[] { args });
        }
    }

    /**
     * カスタムロードを考慮しつつクラス定義を取得します。
     * 
     * @param className フルクラス名
     * @return クラス定義
     * @throws ClassNotFoundException クラス定義取得に失敗
     */
    public static Class getClass(String className)
            throws ClassNotFoundException {
        return getClass(className, new URL[] {}, new String[] {});
    }

    /**
     * カスタムロードを考慮しつつクラス定義を取得します。
     * 
     * @param className フルクラス名
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     * @return クラス定義
     * @throws ClassNotFoundException クラス定義取得に失敗
     */
    public static Class getClass(String className, String[] customLoadPackages)
            throws ClassNotFoundException {
        return getClass(className, new URL[] {}, customLoadPackages);
    }

    /**
     * カスタムロードを考慮しつつクラス定義を取得します。
     * 
     * @param className フルクラス名
     * @param addClassPathes 追加するクラスパス
     * @return クラス定義
     * @throws ClassNotFoundException クラス定義取得に失敗
     */
    public static Class getClass(String className, URL[] addClassPathes)
            throws ClassNotFoundException {
        return getClass(className, addClassPathes, new String[] {});
    }

    /**
     * カスタムロードを考慮しつつクラス定義を取得します。
     * 
     * @param className フルクラス名
     * @param addClassPathes 追加するクラスパス
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     * @return クラス定義
     * @throws ClassNotFoundException クラス定義取得に失敗
     */
    public static Class getClass(String className, URL[] addClassPathes,
            String[] customLoadPackages) throws ClassNotFoundException {
        ClassLoader cl = Thread.currentThread().getContextClassLoader();

        ACClassLoader mcl;
        if (cl instanceof URLClassLoader) {
            mcl = new ACClassLoader(margeClassPath(((URLClassLoader) cl)
                    .getURLs(), addClassPathes), cl);
        } else {
            mcl = new ACClassLoader(addClassPathes, cl);
        }
        mcl.setCustomLoadPackages(customLoadPackages);

        Thread.currentThread().setContextClassLoader(mcl);

        return (Class) mcl.loadClass(className);
    }

    /**
     * カスタムロードを考慮しつつインスタンスを取得します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @return インスタンス
     * @throws ClassNotFoundException クラス定義取得に失敗
     * @throws InstantiationException インスタンス化失敗
     */
    public static Object getInstance(String className)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getInstance(className, new URL[] {}, new String[] {});
    }

    /**
     * カスタムロードを考慮しつつインスタンスを取得します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @return インスタンス
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     * @throws ClassNotFoundException クラス定義取得に失敗
     * @throws InstantiationException インスタンス化失敗
     */
    public static Object getInstance(String className,
            String[] customLoadPackages) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        return getInstance(className, new URL[] {}, customLoadPackages);
    }

    /**
     * カスタムロードを考慮しつつインスタンスを取得します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @return インスタンス
     * @param addClassPathes 追加するクラスパス
     * @throws ClassNotFoundException クラス定義取得に失敗
     * @throws InstantiationException インスタンス化失敗
     */
    public static Object getInstance(String className, URL[] addClassPathes)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getInstance(className, addClassPathes, new String[] {});
    }

    /**
     * カスタムロードを考慮しつつインスタンスを取得します。
     * <p>
     * 指定クラスはデフォルトコンストラクタを有していなくてはなりません。
     * </p>
     * 
     * @param className フルクラス名
     * @return インスタンス
     * @param addClassPathes 追加するクラスパス
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     * @throws ClassNotFoundException クラス定義取得に失敗
     * @throws InstantiationException インスタンス化失敗
     */
    public static Object getInstance(String className, URL[] addClassPathes,
            String[] customLoadPackages) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        Object obj = getClass(className, addClassPathes, customLoadPackages);

        if (obj instanceof Class) {
            Class cls = (Class) obj;
            return cls.newInstance();
        }
        return null;
    }

    /**
     * パスをURLに変換します。
     * 
     * @param path 変換対象
     * @return 変換結果
     */
    public static URL toURL(String path) {
        try {
            return (new URL(path));
        } catch (MalformedURLException e) {
        }
        try {
            char sep = File.separator.charAt(0);
            String file = path.replace(sep, '/');
            if (file.charAt(0) != '/') {
                String dir = System.getProperty("user.dir");
                dir = dir.replace(sep, '/') + '/';
                if (dir.charAt(0) != '/') {
                    dir = "/" + dir;
                }
                file = dir + file;
            }
            return (new URL("file", "", file));
        } catch (MalformedURLException e) {
            throw (new InternalError("can't convert from filename"));
        }
    }

    /**
     * パスをURLに変換します。
     * 
     * @param path 変換対象
     * @return 変換結果
     */
    public static URL[] toURL(String[] path) {
        int end = path.length;
        URL[] urls = new URL[end];
        for (int i = 0; i < end; i++) {
            urls[i] = toURL(path[i]);
        }
        return urls;
    }

    /**
     * 二つのクラスパス配列をマージした結果を返します。
     * 
     * @param src クラスパス配列1
     * @param add クラスパス配列2
     * @return マージ結果
     */
    private static URL[] margeClassPath(URL[] src, URL[] add) {
        ArrayList result = new ArrayList();
        ArrayList keys = new ArrayList();
        int end = src.length;
        for (int i = 0; i < end; i++) {
            Integer key = new Integer(src[i].hashCode());
            if (!keys.contains(key)) {
                keys.add(key);
                result.add(src[i]);
            }
        }
        end = add.length;
        for (int i = 0; i < end; i++) {
            Integer key = new Integer(add[i].hashCode());
            if (!keys.contains(key)) {
                keys.add(key);
                result.add(add[i]);
            }
        }
        Object[] objs = result.toArray();
        URL[] dest = new URL[result.size()];
        System.arraycopy(objs, 0, dest, 0, objs.length);
        return dest;
    }

    private String[] customLoadPackages = new String[] {};
    private String[] extentions = new String[] { ".class" };
    private String[] mustCustomLoadPackages = new String[] { "jp.nichicom.ac" };

    private boolean superLoadClass = false;

    public ACClassLoader(URL[] urls) {
        super(urls);
    }

    public ACClassLoader(URL[] urls, ClassLoader parent) {
        super(urls, parent);
    }

    public ACClassLoader(URL[] urls, ClassLoader parent,
            URLStreamHandlerFactory factory) {
        super(urls, parent, factory);
    }

    /**
     * カスタムロード対象のパッケージ集合を返します。
     * 
     * @return カスタムロード対象のパッケージ集合
     */
    public String[] getCustomLoadPackages() {
        return customLoadPackages;
    }

    /**
     * 許容する拡張子配列 を返します。
     * <p>
     * デフォルトでは".class"のみ
     * </p>
     * 
     * @return 許容する拡張子配列
     */
    public String[] getExtentions() {
        return extentions;
    }

    /**
     * カスタムロード対象のパッケージ集合を設定します。
     * 
     * @param customLoadPackages カスタムロード対象のパッケージ集合
     */
    public void setCustomLoadPackages(String[] customLoadPackages) {
        this.customLoadPackages = customLoadPackages;
    }

    /**
     * 許容する拡張子配列 を設定します。
     * <p>
     * デフォルトでは".class"のみ
     * </p>
     * 
     * @param extentions 許容する拡張子配列
     */
    public void setExtentions(String[] extentions) {
        this.extentions = extentions;
    }

    /*
     * Defines a Class using the class bytes obtained from the specified
     * Resource. The resulting Class must be resolved before it can be used.
     */
    private Class defineClass(String name, Resource res) throws IOException {
        int i = name.lastIndexOf('.');
        URL url = res.getCodeSourceURL();
        if (i != -1) {
            String pkgname = name.substring(0, i);
            // Check if package already loaded.
            Package pkg = getPackage(pkgname);
            Manifest man = res.getManifest();
            if (pkg != null) {
                // Package found, so check package sealing.
                if (pkg.isSealed()) {
                    // Verify that code source URL is the same.
                    if (!pkg.isSealed(url)) {
                        throw new SecurityException(
                                "sealing violation: package " + pkgname
                                        + " is sealed");
                    }

                } else {
                    // Make sure we are not attempting to seal the package
                    // at this code source URL.
                    if ((man != null) && isSealed(pkgname, man)) {
                        throw new SecurityException(
                                "sealing violation: can't seal package "
                                        + pkgname + ": already loaded");
                    }
                }
            } else {
                if (man != null) {
                    definePackage(pkgname, man, url);
                } else {
                    definePackage(pkgname, null, null, null, null, null, null,
                            null);
                }
            }
        }
        // Java 1.5はCodeSigneture式になる

        // Now read the class bytes and define the class
        byte[] b = res.getBytes();
        // カスタムロード
        b = toCustomBytes(b);

        java.security.cert.Certificate[] certs = res.getCertificates();
        CodeSource cs = new CodeSource(url, certs);
        return defineClass(name, b, 0, b.length, cs);
    }

    /**
     * 独自ロードを使用するパッケージに属するかを返します。
     * 
     * @param フルクラス名
     * @return 独自ロードを使用するパッケージに属するか
     */
    private boolean isCustomLoadPackge(String className) {
        int end;
        // 必須カスタムロード
        end = mustCustomLoadPackages.length;
        for (int i = 0; i < end; i++) {
            if (className.startsWith(mustCustomLoadPackages[i])) {
                return true;
            }
        }
        if (customLoadPackages != null) {
            // 追加カスタムロード
            end = customLoadPackages.length;
            for (int i = 0; i < end; i++) {
                if (className.startsWith(customLoadPackages[i])) {
                    return true;
                }
            }
        }
        return false;
    }

    /*
     * Returns true if the specified package name is sealed according to the
     * given manifest.
     */
    private boolean isSealed(String name, Manifest man) {
        String path = name.replace('.', '/').concat("/");
        Attributes attr = man.getAttributes(path);
        String sealed = null;
        if (attr != null) {
            sealed = attr.getValue(Name.SEALED);
        }
        if (sealed == null) {
            if ((attr = man.getMainAttributes()) != null) {
                sealed = attr.getValue(Name.SEALED);
            }
        }
        return "true".equalsIgnoreCase(sealed);
    }

    protected Class findClass(final String name) throws ClassNotFoundException {
        if (name != null) {
            //特殊なクラスは上位クラスに処理させる
            if ("default".equals(name) || name.endsWith(".default")
                    || name.startsWith("driver_property_info")) {
                return getParent().loadClass(name);
            }
        }

        try {
            superLoadClass = true;
            Class c = super.findClass(name);
            if (c != null) {
                return c;
            }
        } catch (Throwable ex) {
        } finally {
            superLoadClass = false;
        }
        try {
            return (Class) AccessController.doPrivileged(
                    new PrivilegedExceptionAction() {

                        public Object run() throws ClassNotFoundException {

                            if (!isCustomLoadPackge(name)) {
                                try {
                                    superLoadClass = true;
                                    return ACClassLoader.super.loadClass(name);
                                } catch (Throwable e) {
                                } finally {
                                    superLoadClass = false;
                                }
                            }
                            Resource res = getResource(name);
                            if (res != null) {
                                try {
                                    return defineClass(name, res);
                                } catch (IOException e) {
                                    throw new ClassNotFoundException(name, e);
                                }
                            } else {
                                throw new ClassNotFoundException(name);
                            }
                        }

                        private Resource getResource(String name) {
                            String replaced = name.replace('.', '/');
                            String[] exts = getExtentions();
                            int end = exts.length;
                            for (int i = 0; i < end; i++) {
                                String path = replaced.concat(exts[i]);
                                Resource res = new URLClassPath(getURLs())
                                        .getResource(path, false);
                                if (res != null) {
                                    return res;
                                }
                            }
                            return null;
                        }
                    }, AccessController.getContext());
        } catch (java.security.PrivilegedActionException pae) {
            throw (ClassNotFoundException) pae.getException();
        }
    }

    protected synchronized Class loadClass(String name, boolean resolve)
            throws ClassNotFoundException {
        if (superLoadClass) {
            return super.loadClass(name, resolve);
        }
        return findClass(name);
    }

    /**
     * カスタムロードにおけるバイト変換結果を返します。
     * 
     * @param b 変換元
     * @return バイト変換結果
     * @throws IOException 処理例外
     */
    protected byte[] toCustomBytes(byte[] b) throws IOException {
        if ((b != null) && (b.length > 5) && (b[0] == 'n') && (b[1] == 'c')
                && (b[2] == 'w')) {
            // CodeWall形式
            if ((b[3] == 1) && (b[4] == 0) && (b[5] == 0) && (b[6] == 0)) {
                // 暗号化ver1.0　：　コメント埋め込み+ビット反転
                
                //呼び飛ばすべきコメントサイズを検出
                int jump = b[7];
                for (int i = 1; i < 4; i++) {
                    jump += b[i + 7] << (i * 8);
                }
                
                //コメント以降を取得
                int totalJump = jump + 11;
                byte[] newB = new byte[b.length - totalJump];
                System.arraycopy(b, totalJump, newB, 0, b.length - totalJump);

                int end = newB.length;
                for(int i=0; i<end; i++){
                    //ビット反転
                    newB[i] ^= 0xFF;
                }
                b = newB;
            } else {
                throw new IOException("不正なクラスです。");
            }
        }
        return b;
    }
}
