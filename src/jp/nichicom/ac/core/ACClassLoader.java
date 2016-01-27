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
 * �Í����N���X���[�_�ł��B
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
     * �J�X�^�����[�h���l�����w��N���X�̃��C���֐������s���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @throws Throwable ������O
     */
    public static void executeMain(String className) throws Throwable {
        executeMain(className, new URL[] {}, new String[] {}, new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����w��N���X�̃��C���֐������s���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @param addClassPathes �ǉ�����N���X�p�X
     * @throws Throwable ������O
     */
    public static void executeMain(String className, URL[] addClassPathes)
            throws Throwable {
        executeMain(className, addClassPathes, new String[] {}, new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����w��N���X�̃��C���֐������s���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @param addClassPathes �ǉ�����N���X�p�X
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     * @throws Throwable ������O
     */
    public static void executeMain(String className, URL[] addClassPathes,
            String[] customLoadPackages) throws Throwable {
        executeMain(className, addClassPathes, customLoadPackages,
                new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����w��N���X�̃��C���֐������s���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @param args main���s����
     * @param addClassPathes �ǉ�����N���X�p�X
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     * @throws Throwable ������O
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
     * �J�X�^�����[�h���l�����N���X��`���擾���܂��B
     * 
     * @param className �t���N���X��
     * @return �N���X��`
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     */
    public static Class getClass(String className)
            throws ClassNotFoundException {
        return getClass(className, new URL[] {}, new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����N���X��`���擾���܂��B
     * 
     * @param className �t���N���X��
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     * @return �N���X��`
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     */
    public static Class getClass(String className, String[] customLoadPackages)
            throws ClassNotFoundException {
        return getClass(className, new URL[] {}, customLoadPackages);
    }

    /**
     * �J�X�^�����[�h���l�����N���X��`���擾���܂��B
     * 
     * @param className �t���N���X��
     * @param addClassPathes �ǉ�����N���X�p�X
     * @return �N���X��`
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     */
    public static Class getClass(String className, URL[] addClassPathes)
            throws ClassNotFoundException {
        return getClass(className, addClassPathes, new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����N���X��`���擾���܂��B
     * 
     * @param className �t���N���X��
     * @param addClassPathes �ǉ�����N���X�p�X
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     * @return �N���X��`
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
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
     * �J�X�^�����[�h���l�����C���X�^���X���擾���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @return �C���X�^���X
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     * @throws InstantiationException �C���X�^���X�����s
     */
    public static Object getInstance(String className)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getInstance(className, new URL[] {}, new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����C���X�^���X���擾���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @return �C���X�^���X
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     * @throws InstantiationException �C���X�^���X�����s
     */
    public static Object getInstance(String className,
            String[] customLoadPackages) throws ClassNotFoundException,
            InstantiationException, IllegalAccessException {
        return getInstance(className, new URL[] {}, customLoadPackages);
    }

    /**
     * �J�X�^�����[�h���l�����C���X�^���X���擾���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @return �C���X�^���X
     * @param addClassPathes �ǉ�����N���X�p�X
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     * @throws InstantiationException �C���X�^���X�����s
     */
    public static Object getInstance(String className, URL[] addClassPathes)
            throws ClassNotFoundException, InstantiationException,
            IllegalAccessException {
        return getInstance(className, addClassPathes, new String[] {});
    }

    /**
     * �J�X�^�����[�h���l�����C���X�^���X���擾���܂��B
     * <p>
     * �w��N���X�̓f�t�H���g�R���X�g���N�^��L���Ă��Ȃ��Ă͂Ȃ�܂���B
     * </p>
     * 
     * @param className �t���N���X��
     * @return �C���X�^���X
     * @param addClassPathes �ǉ�����N���X�p�X
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     * @throws ClassNotFoundException �N���X��`�擾�Ɏ��s
     * @throws InstantiationException �C���X�^���X�����s
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
     * �p�X��URL�ɕϊ����܂��B
     * 
     * @param path �ϊ��Ώ�
     * @return �ϊ�����
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
     * �p�X��URL�ɕϊ����܂��B
     * 
     * @param path �ϊ��Ώ�
     * @return �ϊ�����
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
     * ��̃N���X�p�X�z����}�[�W�������ʂ�Ԃ��܂��B
     * 
     * @param src �N���X�p�X�z��1
     * @param add �N���X�p�X�z��2
     * @return �}�[�W����
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
     * �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W����Ԃ��܂��B
     * 
     * @return �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     */
    public String[] getCustomLoadPackages() {
        return customLoadPackages;
    }

    /**
     * ���e����g���q�z�� ��Ԃ��܂��B
     * <p>
     * �f�t�H���g�ł�".class"�̂�
     * </p>
     * 
     * @return ���e����g���q�z��
     */
    public String[] getExtentions() {
        return extentions;
    }

    /**
     * �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W����ݒ肵�܂��B
     * 
     * @param customLoadPackages �J�X�^�����[�h�Ώۂ̃p�b�P�[�W�W��
     */
    public void setCustomLoadPackages(String[] customLoadPackages) {
        this.customLoadPackages = customLoadPackages;
    }

    /**
     * ���e����g���q�z�� ��ݒ肵�܂��B
     * <p>
     * �f�t�H���g�ł�".class"�̂�
     * </p>
     * 
     * @param extentions ���e����g���q�z��
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
        // Java 1.5��CodeSigneture���ɂȂ�

        // Now read the class bytes and define the class
        byte[] b = res.getBytes();
        // �J�X�^�����[�h
        b = toCustomBytes(b);

        java.security.cert.Certificate[] certs = res.getCertificates();
        CodeSource cs = new CodeSource(url, certs);
        return defineClass(name, b, 0, b.length, cs);
    }

    /**
     * �Ǝ����[�h���g�p����p�b�P�[�W�ɑ����邩��Ԃ��܂��B
     * 
     * @param �t���N���X��
     * @return �Ǝ����[�h���g�p����p�b�P�[�W�ɑ����邩
     */
    private boolean isCustomLoadPackge(String className) {
        int end;
        // �K�{�J�X�^�����[�h
        end = mustCustomLoadPackages.length;
        for (int i = 0; i < end; i++) {
            if (className.startsWith(mustCustomLoadPackages[i])) {
                return true;
            }
        }
        if (customLoadPackages != null) {
            // �ǉ��J�X�^�����[�h
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
            //����ȃN���X�͏�ʃN���X�ɏ���������
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
     * �J�X�^�����[�h�ɂ�����o�C�g�ϊ����ʂ�Ԃ��܂��B
     * 
     * @param b �ϊ���
     * @return �o�C�g�ϊ�����
     * @throws IOException ������O
     */
    protected byte[] toCustomBytes(byte[] b) throws IOException {
        if ((b != null) && (b.length > 5) && (b[0] == 'n') && (b[1] == 'c')
                && (b[2] == 'w')) {
            // CodeWall�`��
            if ((b[3] == 1) && (b[4] == 0) && (b[5] == 0) && (b[6] == 0)) {
                // �Í���ver1.0�@�F�@�R�����g���ߍ���+�r�b�g���]
                
                //�Ăє�΂��ׂ��R�����g�T�C�Y�����o
                int jump = b[7];
                for (int i = 1; i < 4; i++) {
                    jump += b[i + 7] << (i * 8);
                }
                
                //�R�����g�ȍ~���擾
                int totalJump = jump + 11;
                byte[] newB = new byte[b.length - totalJump];
                System.arraycopy(b, totalJump, newB, 0, b.length - totalJump);

                int end = newB.length;
                for(int i=0; i<end; i++){
                    //�r�b�g���]
                    newB[i] ^= 0xFF;
                }
                b = newB;
            } else {
                throw new IOException("�s���ȃN���X�ł��B");
            }
        }
        return b;
    }
}
